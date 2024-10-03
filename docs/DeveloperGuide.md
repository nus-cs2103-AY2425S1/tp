---
    layout: default.md
    title: "Developer Guide"
    pageNav: 3
---

# WedLinker Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
    * Pros: Easy to implement.
    * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
    * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
    * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**: Professional Wedding Planners

* has a need to manage a significant number of contacts
* has a need to manage multiple weddings happening at differing times
* has a need to manage contacts across multiple weddings
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: compilation of all contacts necessary in weddings in one location making management easier


### User stories

Priorities: 
High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

Those without any stars are user stories that were considered but will not be implemented at this time.

| Priority | As a …​                   | I want to …​                                                                                                         | So that…​                                                                                                                                                          |
|:--------:|---------------------------|----------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `* * *`  | user                      | add tag(s) to each contact based on created tags such as florist, musician etc.                                      | I can easily understand the group this person belongs to                                                                                                           |
| `* * *`  | user                      | add a phone number associated with each contact                                                                      | I can easily find the contact information for each contact.                                                                                                        |
| `* * *`  | user                      | add the address associated with each contact                                                                         | I can easily find the address of each contact.                                                                                                                     |
| `* * *`  | user                      | filter contacts by tag                                                                                               | I can quickly see all the groups under the same tag, and find the right vendor based on the type of services provided                                              |
| `* * *`  | user                      | add new contacts into WedLinker                                                                                      | I can store the contact details of new contacts                                                                                                                    |
| `* * *`  | user                      | delete contacts that are no longer needed                                                                            | I can remove unnecessary contacts and have a more organised address book                                                                                           |
| `* * *`  | user                      | search for contact by name                                                                                           | I can find specific contacts that I am looking for                                                                                                                 |
| `* * *`  | user                      | create tags                                                                                                          | I can have special categories for non traditional vendors                                                                                                          |
|  `* *`   | user                      | edit information such as the contact number and address of each contact                                              | all contacts have the most updated information                                                                                                                     |
|  `* *`   | user                      | clear all the contacts in the system                                                                                 | I can clear all my contacts quickly without having to individually delete them if I want to add in a completely new set of contacts                                |
|  `* *`   | careless user             | receive a prompt that requires me to key in a confirmation that I want to delete a contact or clear the address book | I will not lose all my contacts when I accidentally type delete/ clear                                                                                             |
|  `* *`   | user                      | assign each guest contact its dietary requirements status                                                            | I can track the dietary requirement of each guest.                                                                                                                 |
|  `* *`   | user                      | sort contacts by alphabetical order                                                                                  | I can easily find the contacts required in a large address book.                                                                                                   |
|  `* *`   | user                      | assign additional information for each contact                                                                       | I can include important notes that may not fit into other categories, such as reminders for what the contact might need                                            |
|  `* *`   | first-time user           | see some sample contacts already available in the app                                                                | I can try out the different features without needing to add my own data (e.g allocating people to wedding, allocating task to contacts)                            |
|  `* *`   | careless, first-time user | reload the sample contacts into the app                                                                              | I can continue trying out different features without needing to add my own data in case I accidentally cleared the contacts                                        |
|  `* *`   | first-time user           | see a help message showing all the commands/feature I can use                                                        | I can try out all the different features by referring to the message                                                                                               |
|   `*`    | user                      | assign tasks to contacts                                                                                             | I can track which tasks have been assigned to each contact.                                                                                                        |
|   `*`    | user                      | update the status of tasks of contacts                                                                               | I can track the status of completion of the tasks assigned to contacts                                                                                             |
|   `*`    | user                      | add a tag to each guest indicating their table number                                                                | track the table each guest is seated at                                                                                                                            |
|   `*`    | user                      | key in the table number and get the list of guests seated at that table                                              | I can quickly identify all the groups seated at one table                                                                                                          |
|   `*`    | user                      | assign a rating out of 5 to each vendor                                                                              | I can track the experience with this vendor for future reference                                                                                                   |
|   `*`    | busy user                 | add multiple wedding events                                                                                          | I can track contacts for multiple weddings at once                                                                                                                 |
|   `*`    | busy user                 | tag each contact to a wedding                                                                                        | I can easily see which contacts are relevant to which wedding                                                                                                      |
|   `*`    | user                      | assign dates to a wedding                                                                                            | I can keep track of when different weddings are scheduled                                                                                                          |
|   `*`    | user                      | assign dates to a wedding                                                                                            | I can keep track of when different weddings are scheduled                                                                                                          |
|   `*`    | user                      | filter contacts by wedding                                                                                           | I can keep track of which contacts are relevant for each wedding                                                                                                   |
|   `*`    | user                      | send out (standardised formatted) information (text/email) from the application                                      | I can efficiently send out information without any mistakes                                                                                                        |
|   `*`    | user                      | share the contact details to relevant third-parties for bookings (eg: venue bookings, suit/dress rental, etc.)       | I can easily send out all relevant information (including dietary restriction, and other tags) to all the third-parties                                            | 
|   `*`    | user                      | exclude tags from search and filter                                                                                  | I can focus on contacts that are relevant to certain events or requirements without being overwhelmed by unnecessary information                                   |
|   `*`    | busy user                 | autocomplete existing tags when user is inputting tag information                                                    | I can quickly assign roles for people that might be working with others I have already input into the system and not have to type the same roles in multiple times |
|   `*`    | user                      | assign availability to vendors                                                                                       | I can check who will be available for a particular wedding                                                                                                         |
|   `*`    | user                      | filter availability of vendors                                                                                       | I can easily find vendors that can cater to a wedding                                                                                                              |
|   `*`    | user                      | store multiple contact methods                                                                                       | I can contact the vendors through different means                                                                                                                  |
|   `*`    | user                      | re-assign tasks to another contact                                                                                   | I can account for vendors suddenly being unavailable                                                                                                               |
|   `*`    | user                      | set reminders for tasks to different contacts                                                                        | I can easily track and follow up with clients and vendors for deliverables                                                                                         |
|   `*`    | user                      | see a list of all tasks and reminders I have assigned to contacts in its own window                                  | I can quickly and easily see what my earliest priorities are and act on them quickly                                                                               |
|   `*`    | user                      | see a calendar view of tasks, reminders, and weddings I have assigned                                                | I can see the whole timelines of my planned weddings and see how much time there is between tasks                                                                  |
|   `*`    | user                      | set privacy setting for different contacts                                                                           | I can keep personal and sensitive information private when sharing address book                                                                                    |
|   `*`    | forgetful user            | create links between different contacts, such as assigning a vendor to a bride or groom in a wedding                 | I can easily navigate from key stakeholders in the wedding that I remember better to vendors who I might not remember as well                                      |
|   `*`    | user                      | add certain vendors as favorites                                                                                     | I can remember which vendors performed well and see if they are favorites                                                                                          |
|   `*`    | user                      | access a list of all my favorite vendors                                                                             | I can easily check who the best vendors were that I previously engaged with                                                                                        |
|          | user                      | generate a checklist of all the contacts for a particular wedding, grouped by roles                                  | I can keep track of who is meant to be present at the wedding                                                                                                      |
|          | user                      | assign a time for each contact for when they are meant to arrive                                                     | I can easily keep track of which people are on time and check who to contact in case they have not arrived yet                                                     |
|          | user                      | attach extra documents as a file to various contacts                                                                 | I can store all the information in one place, eg. Invoices from a vendor                                                                                           |
|          | user                      | categorize tasks based on its nature (e.g. procurement, arrangement)                                                 | I can view tasks in a more organised manner                                                                                                                        |

### Use cases

(For all use cases below, the **System** is the `WedLinker` and the **Actor** is the `user`, unless specified otherwise)

> Use Cases beginning with 'UC' cover core AddressBook functionality.
> 
> Use Cases beginning with 'UCSH' cover non-core AddressBook functionality.

**Use case: UC01 List all contacts**

**MSS**

1.  User issues the list command.
2.  The system retrieves and displays the list of all contacts to the user.
    Use case ends.


**Use case: UC02 Add a contact**

**MSS**

1.  User requests to add contact with the corresponding details.
2.  The system adds the contact and displays a success message.
3.  The system shows the new contact in the address book.

    Use case ends.

**Extensions**

* 1a. The system detects a name input error (duplicated or trailing whitespace).
    * 1a1. The system displays an error message.
  
        Use case ends.
  
  
* 1b. The system detects a phone number input error (invalid format).
    * 1b1. The system displays an error message stating the correct format.
  
        Use case ends.
  
  
* 1c. The system detects an address input error (too long).
    * 1c1. The system displays an error message stating the maximum length.
  
        Use case ends.
  
  
* 1d. The system detects an email input error (invalid format).
    * 1d1. The system displays an error message stating the correct format.
  
        Use case ends.
  
  
* 1e. The system detects a duplicate phone number error.
    * 1e1. The system displays an error message mentioning the existence of a duplicate phone number.
    
        Use case ends.
        

* 1f. The system detects an invalid tag input.
    * 1f1. The system displays an error message stating the tag is invalid.
    
        Use case ends.



**Use case: UC03 Add Phone Number to Contact**

**Guarantees:**
* No duplicate phone numbers will be stored in two different contacts.


**MSS**

1. User <ins>lists all contacts (UC01)</ins>.
2. User requests to add phone number for a contact with the corresponding details.
3. The system adds the phone number to the contact and displays a success message.
4. The system displays the updated contact information in the address book.

    Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.


* 2a. The system detects an invalid contact index.
    * 2a1. The system displays an error message stating the contact index is invalid.

        Use case resumes at step 1.
  
  
* 2b. The system detects a phone number input error (invalid format).
    * 2b1. The system displays an error message stating the correct format.
    
        Use case resumes at step 1.
  

* 2c. The system detects a duplicate phone number error.
    * 2c1. The system displays an error message mentioning the existence of a duplicate phone number.
    
        Use case resumes at step 1.



**Use case: UC04 Add Address to Contact**

**MSS**

1. User <ins>lists all contacts (UC01)</ins>.
2. User requests to add address for a contact with the corresponding details.
3. The system adds the address to the contact and displays a success message
4. The system displays the updated contact information in the address book.

    Use case ends.

**Extensions**

* 1a. The list is empty.

    Use case ends.


* 2a. The system detects an invalid contact index.
    * 2a1. The system displays an error message stating the contact index is invalid.

        Use case resumes at step 1.
  

* 2b. The system detects an address input error (too long).
    * 2b1. The system displays an error message stating the maximum length

        Use case resumes at step 1.



**Use case: UC05 Add Email to Contact**

**MSS**

1. User <ins>lists all contacts (UC01)</ins>.
2. User requests to add email address for a contact with the corresponding details. 
3. The system adds the email address to the contact and displays a success message
4. The system displays the updated contact information in the address book.

    Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.
  

* 2a. The system detects an invalid contact index.
    * 2a1. The system displays an error message stating the contact index is invalid.

        Use case resumes at step 1.


* 2b. The system detects an email input error (invalid format).
    * 2b1. The system displays an error message stating the correct format.
  
        Use case resumes at step 1.  
  

**Use case: UC06 Search for contacts by Name**

**MSS**

1.  User searches for the contact by name.
2.  System shows a list of contacts containing the name.

**Use case: UC07 Filter by Tag**

**MSS**

1. User filters for contacts with a specified tag.
2. System only shows a list of contact with the specified tag.

   Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.


**Use case: UC08 Create Tags**

**MSS**

1. User requests to create a tag.
2. System displays the successful creation of tag.

   Use case ends.

**Extensions**

* 1a. The tag already exists.
    * 1a1. System does not create a new tag.
    * 1a2. System informs the user the tag already exists.

      Use case ends.


**Use case: UC09 Tagging a contact with a specified tag**

**MSS**

1. User <ins>lists all contacts (UC01)</ins>.
2. User adds the tag to the contact.
3. System informs the user the contact is tagged.
4. System shows the user the final result of the contact.

   Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

* 2a. System detects that the tag does not exist.

    * 2a1. System <ins>creates a new tag (UC08)</ins>

      Use case resumes at step 3.

* 2b. The given index is invalid.

    * 2b1. System shows an error message prompting the user to enter a valid index.

      Use case resumes at step 1.

**Use case: UC10 Delete Contact**

**MSS**

1. User <ins>lists all contacts (UC01)</ins>.
2. User requests to delete a specific person in the list.
3. System deletes the contact.

   Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.


* 2a. The given index is invalid.

    * 2a1. System shows an error message prompting the user to enter a valid index.

      Use case resumes at step 1.


**Use case: UCSH01 Edit details for a contact**

**MSS**

1. User <ins>lists all contacts (UC01)</ins>
2. User requests to edit the details of a person and specifies what they want to change the details to
3. AddressBook changes the existing details to the specified details and shows list of persons with new details

**Extensions**

* 1a. The list is empty.

  Use case ends.


* 2a. The given index is invalid.

    * 2a1. System shows an error message prompting the user to put in a valid index.

      Use case resumes at step 1.


* 2b. The user does not specify what type of details they want to change.

    * 2b1. System shows an error message prompting the user to put in the type of details they want to edit.

      Use case resumes at step 1.


* 2c. The user does not specify what the new details should be.

    * 2c1. System shows an error message prompting the user to put in the new details.

      Use case resumes at step 1.


* 2d. The user specifies details that do not meet the requirements of the detail type.

    * 2d1. System shows an error message prompting the user with the correct detail type format and requirements.

      Use case resumes at step 1.


**Use case: UCSH02 Clear all contacts from the system**

**Guarantees:** 
* No persons will be left in the system.

**MSS**

1. User requests to clear all contact
2. System deletes all contacts and shows a blank list of persons

    Use case ends.

**Use case: UCSH03 Receive a prompt when deleting a contact**

**MSS**

1. User <ins>lists all contacts (UC01)</ins>
2. User requests to delete a contact
3. System gives a prompt to confirm whether the user wants to delete the contact
4. User confirms they want to delete the contact
5. System deletes the contact and shows the updated list of persons

    Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.


* 2a. The given index is invalid.

    * 2a1. System shows an error message prompting the user to put in a valid index.

      Use case resumes at step 1.


* 3a. User says they do not want to delete the contact.

    * 3a1. System shows a message indicating the contact was not deleted.

      Use case ends.


**Use case: UCSH04 Receive a prompt when clearing the system**

**MSS**

1. User requests to clear the system of all persons
2. System gives a prompt to confirm whether the user wants to clear all contacts
3. User confirms they want to clear all contacts
4. System deletes all contacts and shows a blank list of persons

    Use case ends.

**Extensions**

* 2a. User says they do not want to clear all their contacts.

    * 2a1. System shows a message indicating the system was not cleared.

      Use case ends.


**Use case: UCSH05 Assign dietary requirement to contact**

**MSS**

1. User <ins>lists all contacts (UC01)</ins>
2. User requests to add a dietary status to the person
3. System adds the dietary status to the contact and shows list of persons with new details
    Use case ends.

**Extensions**

* 1a. The list is empty.
  Use case ends.


* 2a. The given index is invalid.

    * 2a1. System shows an error message prompting the user to put in a valid index.

      Use case resumes at step 1.


**Use case: UCSH06 Sort contacts in alphabetical order**

**MSS**

1.  User requests to show a list of persons sorted alphabetically
2.  System shows the list of persons sorted in alphabetical order

    Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

**Use case: UCSH07 Add additional information for a person**

**MSS**

1. User <ins>lists all contacts (UC01)</ins>
2. User requests to add additional information for a person
3. System adds the additional information to the contact and shows list of persons with new details

    Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.


* 2a. The given index is invalid.

    * 2a1. System shows an error message prompting the user to put in a valid index.

      Use case resumes at step 1.


* 2a. The additional information is blank.

    * 2a1. System shows an error message prompting the user to type in the additional information.

      Use case resumes at step 1.


**Use case: UCSH08 See sample contacts in the system before starting to modify it**

Preconditions: User has not added or edited contacts previously

**MSS**

1.  User opens the application
2.  System shows a list of sample contacts

    Use case ends.

**Use case: UCSH09 Reload sample contacts in the system**

**MSS**

1.  User requests to reload sample contacts into the system
2.  System deletes all current persons in the system and shows a list of sample contacts

    Use case ends.

**Use case: UCSH10 See a list of all possible commands**

**MSS**

1.  User requests to see a list of all possible commands they can use in the system
2.  System shows a list of commands with their corresponding input format

    Use case ends.


### Non-Functional Requirements

**Performance Requirements**
- P1: The system should be able to hold up to 1000 persons without noticeable sluggishness in performance for typical usage.
- P2: The application should respond within two seconds for most user operations.

**Usability Requirements**
- U1: A user with above-average typing speed for regular English text (not code, not system admin commands) should be able to accomplish most tasks faster using keyboard commands than using the mouse.
- U2: The application should target users who can type fast and prefer typing over other means of input, such as clicking buttons, selecting dropdowns and drag-and-drop means.
- U3: The GUI should provide clear and user-friendly error messages when operations fail to assist users in correcting mistakes.
- U4: The GUI should not cause any resolution-related inconveniences for standard screen resolutions of 1920x1080 and higher, and for screen scales of 100% and 125%.
- U5: The GUI should be usable for resolutions of 1280x720 and higher, and for screen scales of 150%.

**Compatibility Requirements**
- C1: The application should work on any _mainstream OS_ as long as it has Java `17` or above installed.
- C2: The application should be platform-independent and work on Windows, Linux, and OS-X without relying on OS-dependent libraries or features.
- C3: The application should not depend on the developer’s own remote server.

**Data Requirements**
- D1: The application should not use a Database Management System (DBMS) for data storage.
- D2: Data stored in the address book should be stored locally, in a human-editable text file.
- D2: The entire application should be packaged into a single JAR file.
- D3: The JAR file size should be within 100 MB and should not be unnecessarily bloated.
- D4: PDF documents generated for documentation should have a file size within 15 MB and should not be unnecessarily bloated.

**Documentation Requirements**
- Doc1: Documentation should be saved in PDF format using Chrome, not any other browser, and should be PDF-friendly (no expandable panels, embedded videos, or animated GIFs).
- Doc2: The Developer Guide and User Guide should be well-structured and easily navigable in PDF format. A new user should be able to quickly locate relevant information for using the product. 

**Development Process Requirements**
- DP1: The software should be developed in a breadth-first incremental manner over the project duration.
- DP2: The project is expected to adhere to the milestone deadlines set for every week.

**Reliability and Stability Requirements**
- R1: The application should not crash under normal operations and should handle errors gracefully without data loss.
- R2: The application should maintain a stable performance over extended usage periods.

**Maintainability Requirements**
- M1: The software should primarily follow the Object-Oriented programming paradigm. Having a modular structure allows for the addition of new features with minimal disruption to existing functionality.

**Quality Requirements**
- Q1: The software should be usable by a novice who has never used it before. 

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Contact**: Contact details of an entry which includes minimally a name. Phone number, address, email address and tags are optional.
* **Tag**: An custom-made field that can be associated to contacts for ease of grouping/filtering
* **Vendor**: Businesses or persons who offer wedding services, like florists, musicians, venue in-charge, etc.
* **User**: The wedding planner who is using WedLinker

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    1. Test case: `delete 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

    1. Test case: `delete 0`<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

    1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
