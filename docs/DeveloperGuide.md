---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# PlanPerfect Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

This app is based on AddressBook-Level3, courtesy of [SE-EDU](https://se-education.org/)

Libraries used: [JavaFX](https://openjfx.io/), [JUnit5](https://github.com/junit-team/junit5), [Jackson](https://github.com/FasterXML/jackson)

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

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `WeddingListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-T12-2/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-T12-2/tp/blob/master/src/main/resources/view/MainWindow.fxml)

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
1. The command can communicate with the `Model` when it is executed (e.g. to delete a contact).<br>
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

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object), as well as Wedding objects (contained in a `UniqueWeddingList` Object)
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* stores a `ActiveTags` object that represents a collection of the current active tags in the AddressBook.
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

Step 2. The user executes `delete 5` command to delete the 5th contact in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new contact. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the contact was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

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
    * Pros: Will use less memory (e.g. for `delete`, just save the contact being deleted).
    * Cons: We must ensure that the implementation of each individual command are correct.


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

**Target user profile**:

Wedding planners who:
* have a need to manage a significant number of contacts (e.g. clients, caterers, photographers)
* prefer desktop apps over other types for better organization
* can type fast and efficiently
* prefer typing to mouse interactions for quicker navigation
* are comfortable using CLI apps for managing tasks such as adding, deleting, tagging, and finding contacts
* require flexible contact management, including categorization by tags

**Value proposition**: Wedding planners frequently manage a large number of contacts which can become overwhelming. PlanPerfect simplifies this process through 2 main core functionalities:
* **Tags**: Allows users to categorize contacts using tags, making it easy to organise and retrieve important contacts
* **Weddings**: Allows users to categorize contacts around wedding events, allowing for easy tracking of who is involved in which wedding

With its fast and efficient Command Line Interface (CLI), PlanPerfect enables users to manage their contacts significantly faster than traditional mouse/GUI-driven apps, providing greater flexibility and speed for busy wedding planners.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​         | I want to …​                                                 | So that I can…​                                        |
|----------|-----------------|--------------------------------------------------------------|--------------------------------------------------------|
| `* * *`  | User            | Add new contacts                                             | Expand my wedding planner network                      |
| `* * *`  | User            | Delete contacts                                              | Get rid of unneeded contacts                           |
| `* * *`  | User            | Tag contacts                                                 | Organise my contacts                                   |
| `* * *`  | User            | Untag contacts                                               | Organise my contacts                                   |
| `* * *`  | User            | View a list of all contacts                                  | Retrieve the contact information                       |
| `* *`    | User            | Edit contacts                                                | Update my contacts' information upon changes           |
| `* *`    | User            | Filter contacts by tag                                       | Find specific profiles of interest quickly             |
| `* *`    | User            | Archive contacts                                             | Prevent clutter in my contacts without a full deletion |
| `* *`    | First-time user | See help messages                                            | Learn how to use the app                               |
| `* *`    | User            | See a confirmation message before I clear all data           | Avoid accidentally losing all of my data               |
| `*`      | User            | View which wedding service providers a client is paired with | Easily find contacts associated with a wedding         |
| `*`      | User            | Add notes for a specific contact                             | Add my own details                                     |
| `*`      | User            | Sort contacts alphabetically                                 | Organize and access contacts easily                    |
| `*`      | Forgetful user  | Set reminders to talk to contacts                            | Remember to communicate with contacts                  |
| `*`      | User            | Import contacts from an existing CSV/text file               | Add new contacts quickly                               |
| `*`      | Busy user       | Schedule calls with contacts                                 | Remember when to call people                           |
| `*`      | Organised user  | Create folders                                               | Organise my contacts into categories                   |
| `*`      | Efficient user  | Make mass operations                                         | Add/delete/archive numerous contacts in one go         |
| `*`      | User            | Create wedding events with associated contacts               | Park relevant contacts for that wedding in one place   |
| `*`      | User            | Create todo lists for each wedding                           | Manage tasks efficiently                               |
| `*`      | User            | Send my contacts to other users                              | Allow my colleagues to contact the same people         |
| `*`      | User            | Add descriptions to contacts                                 | See more clarifying details about the contact          |
| `*`      | User            | Search contacts by name or tag                               | Quickly access a contact                               |
| `*`      | Curious user    | See statistics                                               | See how many contacts are added                        |
| `*`      | User            | Export all my data as a spreadsheet/CSV                      | Transfer it to other softwares if needed               |
| `*`      | Clumsy user     | Undo the latest action                                       | So that wrong actions can be undone                    |
| `*`      | User            | Copy the email of the contacts to clipboard                  | Email contacts conveniently                            |

<br></br>

### Use cases

<box type="info" seamless>

**Note:** For all the use cases below, the **System** is PlanPerfect and the **Actor** is an arbitrary Wedding Planner.

</box>

<br/><br/>
**Use case: UC01 - List all contacts**

**MSS**

1. Wedding planner requests to list all contacts
2. PlanPerfect shows a list of all contacts (clients, vendors, venues, etc.).

   Use case ends.

<br/><br/>

**Use case: UC02 - Delete a contact**

**MSS**

1. Wedding planner <u>views the list of all contacts (UC01)</u>.
2. Wedding planner requests to delete a specific contact in the list.
3. PlanPerfect deletes the contact.
4. PlanPerfect shows a success message.

   Use case ends.

**Extensions**

* 2a. The given contact index is invalid or the contact list is empty.

    * 2a1. PlanPerfect shows an error message.
      Use case resumes at step 2.

<br/><br/>

**Use case: UC03 - Add a contact**

**MSS**

1. Wedding planner requests to add a contact (e.g., a new client, vendor, or wedding service provider).
2. PlanPerfect adds the new specified contact.
3. PlanPerfect shows a success message to the wedding planner.

   Use case ends.

**Extensions**

* 1a. The input format for adding the contact is invalid.

    * 1a1. PlanPerfect shows an error message requesting for the correct input format.

      Use case ends.
* 1b. PlanPerfect detects a duplicate contact entry (by phone number) in the addressbook.

    * 1b1. PlanPerfect displays an error message showing the duplicated contact.

      Use case ends.
      <br/><br/>

**Use case: UC04 - Edit a contact**

**MSS**

1. Wedding planner requests to edit the personal details of an existing contact
2. PlanPerfect edits the contact's as specified by the wedding planner.
3. PlanPerfect shows a success message to the wedding planner.

**Extensions**
* 1a. The input format for adding the contact is invalid.

    * 1a1. PlanPerfect shows an error message requesting for the correct input format.

      Use case ends.
* 1b. Wedding planner requests to edit a contact's phone number to one that is held by another contact.

    *  1b1.  PlanPerfect shows an error message that the input phone number already exists in PlanPerfect.

       Use case ends.
       <br/><br/>

**Use case: UC05 - Tag a contact**

**MSS**

1. Wedding planner <u>views the list of all contacts (UC01)</u>.
2. Wedding planner requests to attach a tag (e.g., vendor type, client status) to a specific contact.
3. PlanPerfect adds a tag to the contact.
4. PlanPerfect shows a success message to the wedding planner.

   Use case ends.

**Extensions**

* 3a. Contact does not exist in PlanPerfect

    * 3a1. PlanPerfect shows an error message

      Use case ends.

<br/><br/>

**Use case: UC06 - Untag a contact**

**MSS**

1. Wedding planner <u>views the list of all contacts (UC01)</u>.
2. Wedding planner requests to remove a tag from a specific contact.
3. PlanPerfect removes the tag from the specified contact.
4. PlanPerfect shows a success message.

   Use case ends.

**Extensions**

* 3a. Contact does not exist in PlanPerfect

    * 3a1. PlanPerfect shows an error message

      Use case ends.

<br/><br/>

**Use case: UC07 - List contacts by tag**

**MSS**

1. Wedding planner requests to view a list of contacts that have specific tag(s) (e.g., photographers, caterers, clients in progress).
2. PlanPerfect shows a list of contacts who have the specified tag(s).

   Use case ends.

**Extensions**

* 1a. The list of contacts with the specified tag is empty.

  Use case ends.

<br/><br/>

**Use case: UC08 - Find contact by name**

**MSS**

1. Wedding planner requests to retrieve a contact's details by name.
2. PlanPerfect shows the specified contact's details.

   Use case ends.

**Extensions**

* 1a. Contact does not exist in PlanPerfect.

    * 1a1. PlanPerfect shows a message that the contact could not be found.

      Use case ends.

<br/><br/>

**Use case: UC09 - Getting help**

**MSS**

1. Wedding planner asks for help.
2. PlanPerfect shows a list of valid commands with examples.

   Use case ends.

<br/><br/>

**Use case: UC10 - Sort contacts alphabetically**

**MSS**

1. Wedding planner requests to sort contacts alphabetically.
2. PlanPerfect sorts and displays the contacts in alphabetical order.

   Use case ends.

<br/><br/>

**Use case: UC11 - Filter contacts by tag**

**MSS**

1. Wedding planner requests to view contacts tagged with one or more tag.
2. PlanPerfect shows the list of contacts tagged with the tags input by the user.

   Use case ends.

**Extensions**

* 1a. The input format is invalid.

    * 1a1. PlanPerfect shows an error message.

      Use case ends.

<br/><br/>

**Use case: UC12 - Clear all wedding and contact data**

**MSS**

1. Wedding planner requests to clear all contacts and weddings.
2. PlanPerfect asks for confirmation before clearing data.
3. Wedding planner confirms.
4. PlanPerfect clears all contacts and weddings, showing a success message.

Use case ends.

**Extensions**

* 1a. The wedding planner enters input that cancels the action.
    * 1a1. PlanPerfect retains all data.
    * 1a2. PlanPerfect shows a message indicating that the wedding and contact data has not been cleared.

      Use case ends.

<br/><br/>

**Use case: UC13 - View contacts of a specified wedding**

**MSS**

1. Wedding planner requests to view contacts stored in a specified wedding.
2. PlanPerfect shows the list of all contacts involved in a specified wedding.

   Use case ends.

**Extensions**

* 1a. The given wedding index is invalid.

    * 1a1. PlanPerfect shows an error message requesting for a valid wedding index.

      Use case ends.

<br/><br/>


**Use case: UC14 - Add a wedding**

**MSS**

1. Wedding planner requests to add a new wedding.
2. PlanPerfect adds the wedding with the specified details to PlanPerfect.

   Use case ends.

**Extensions**
* 1a. User provides invalid details about a wedding to PlanPerfect.
    * 1a1. PlanPerfect shows an error message detailing why the details provided are invalid.

      Use case ends.

<br/><br/>

**Use case: UC15 - Delete a wedding**

**MSS**

1. User requests to delete a wedding with specified details.
2. PlanPerfect deletes the wedding.

   Use case ends.

**Extensions**

* 1a. The wedding contact index provided is invalid or missing.
    * 1a1. PlanPerfect shows an error message.

      Use case ends.

<br/><br/>

**Use case: UC16 - Edit a wedding**

**MSS**

1. User requests to edit the name and/or date of the wedding.
2. PlanPerfect edits the wedding as specified.

   Use case ends.

**Extensions**

* 1a. The input format for editing the wedding is invalid.
    * 1a1. PlanPerfect shows an error message.

      Use case ends.

* 1b. PlanPerfect detects a duplicate wedding entry (by wedding name) in the addressbook.
    * 1b1. PlanPerfect displays an error message showing the duplicated wedding details.

      Use case ends.




<br/><br/>

### Non-Functional Requirements

1. Should work on any **mainstream OS** (e.g., Windows, macOS, Linux) as long as it has **Java 17** installed.
2. Should be able to hold and manage up to **1000 contacts** (e.g., clients, vendors, weddings) with no more than 2 seconds of delay.
3. A wedding planner with **above average typing speed** should be able to accomplish most tasks faster using **command-line inputs** rather than relying on graphical user interfaces or the mouse.
4. The system should be designed for **single-user** operation, where one wedding planner uses the application on their local machine.
5. The software should work **offline** and require no internet connection after the **initial download** of the application's jar file.
6. The software should be **portable**, not requiring an installer, and should run as a standalone executable jar file.
7. The software should follow the **Object-Oriented Programming (OOP)** paradigm, ensuring code modularity, maintainability, and extensibility.
8. The **Command-Line Interface (CLI)** should remain responsive for standard usage and offer a user-friendly experience while allowing quick navigation and execution of commands.
9. The **Graphical User Interface (GUI)** (if used) should work seamlessly for screens with standard resolutions of **1920x1080** and higher, and support scaling at **100%** and **125%**, ensuring that users on both laptops and desktop displays have an optimal experience.
10. The system should be designed to run efficiently on **low-resource systems**, ensuring smooth performance even on devices with lower computational power or limited memory.


### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Contact Details**: Info about a contact: name, email, address, phone number etc.
* **Contact/Person**: Used Interchangeably. Contact is used for contextual descriptions in the User/Developer Guides. A contact is modelled as a Person class in code.
* **Active tag**: Refers to the tags which are currently in use. Once the last occurrence of a tag is deleted, that tag is no longer active.
* **CLI**: Command Line Interface
* **GUI**: Graphic User Interface

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

<br></br>

### Contact Management

1. Adding a contact
    - **Recommended:** Enter the all contacts view using the `list` command. You can add contacts while in a wedding view, but upon the contact being added, you will be redirected to the all contacts view.
    - Test case: `add n/John Doe p/91234567 e/johnd@example.com a/123 John St.`<br>
      **Expected:** A new contact with the specified details is added to the all contacts list. Confirmation is shown in the status message.
      <br></br>

2. Deleting a contact

    - **Prerequisites:** For optimal testing, multiple contacts should already be present in the contact list.
    - Test case: `delete 1`<br>
      **Expected:** First contact is deleted from the list in view. Details of the deleted contact are shown in the status message.
    - Test case: `delete 0`<br>
      **Expected:** No contact is deleted. Error details are shown in the status message.
    - Other incorrect delete commands to try: `delete`, `delete x` (where x is larger than the list size)<br>
      **Expected:** Similar to the previous case, an error message appears.
      <br></br>

3. Editing a contact
    - **Prerequisites:** Ensure the contact you want to edit is in the current view.
    - Test case: `edit 2 n/Jane Smith p/98765432`<br>
      **Expected:** The name and phone number of the second contact in view are updated. Confirmation is shown in the status message.
      <br></br>

4. Tagging a contact
    - **Prerequisites:** The contact should not already have the tag you want to add. You may add multiple tags at once.
    - Test case: `tag 2 t/florist designer`<br>
      **Expected:** The contact at index 2 in the current contacts view will now have the florist and designer tags.
      <br></br>
5. Untagging a contact
    - **Prerequisites:** The contact should currently have the tag you want to remove. You may remove multiple tags at once.
    - Test case: `untag 2 t/photographer videographer`<br>
      **Expected:** The contact at index 2 will no longer have the photographer and videographer tags.
      <br></br>

### Wedding Management

1. Adding a wedding
    - **Recommendation:** Enter the all contacts view using the `list` command. You can add a wedding while in a wedding view, but if you intend assigning contacts as you add a wedding, you will only be able to assign contacts from the current wedding view into the wedding being added, which may not be optimal.
    - Test case: `addw n/Emily and John Wedding d/25/12/2024 c/1 2`<br>
      **Expected:** A new wedding is added with the specified contacts and date. Confirmation message is shown.
      <br></br>

2. Deleting a wedding
   - Test case: `deletew 1` <br>
   **Expected**: The wedding's details at index 1 has been updated with the name and date provided
   <br></br>


3. Editing a wedding's details
   - Test case: `editw 1 n/Emmy and Johnny d/29/12/2024` <br>
  **Expected**: The wedding's details at index 1 has been updated with the name and date provided
  <br></br>

4. Viewing wedding details
    - Test case: `view 1`<br>
      **Expected:** Displays the contacts assigned to the wedding at index 1.
      <br></br>

      
5. Assigning and unassigning contacts to/from a wedding
    - Test case: `assign 1 c/3`<br>
      **Expected:** Contact at index 3 is assigned to wedding 1. A confirmation message is displayed.
    - Test case: `unassign c/3`<br>
      **Expected:** Contact at index 3 is removed from the wedding in view. A confirmation message is displayed.
      <br></br>

### Error Handling and Data Integrity

1. Handling invalid commands
    - Test case: Enter invalid commands, such as `edit`, `delete abc`, `add n/` (missing parameters).<br>
      **Expected:** Clear error message appears, with no unintended changes to the data.
      <br></br>
2. Testing data persistence
    - After making several changes, close and reopen the application.<br>
      **Expected:** All recent changes persist, confirming successful data saving.
      <br></br>
3. Dealing with missing/corrupted data files
    - To simulate a missing data file, go into the folder where your jar file is stored, and delete the folder named 'data'.
    - To simulate a corrupted data file, go into the folder where you jar file is stored, click into the data folder, and edit the addressbook.json in a way that it contains syntax error(s). <br>
      **Expected:** The application will start with an empty contact list there is no data available or if the data has been corrupted.
      <br></br>

--------------------------------------------------------------------------------------------------------------------
## **Appendix: Effort**

To summarise, PlanPerfect extends the AB3 framework considerably by not only enhancing and adding new
contact-management features (like `tag`, `untag`, `filter`)  but also a list of weddings as a new entity type with its
own attributes and a new set of wedding commands. It has also considerably
altered some of the AB3's pre-existing contact management features, especially regarding the nature of tag
management and changed the UI to increase its complexity and better reflect an integrated wedding and contacts view.

**Difficulty level:**
- Moderate difficulty: While numerous newly added commands and tests did leverage existing AB3 code in some aspects
  when creating some wedding commands, adapting it to handle dual entities (contacts and wedding) and augmenting the
  UI to display the wedding list and current wedding viewed required a significant amount of effort.

**Challenges faced:**
- Ensuring robust test coverage for new and adapted features, which required a lot of consideration for edge cases.
- Modifying the UI which required considerable additions to the FXML code and utilising JavaFX (which has a fairly
  steep learning curve) to support a split wedding list and contact list view.
- Managing whether new commands (eg. `assign`/`filter`) operate on the current view or main view for newly added
  commands dependent on whether they were in the main/filtered/wedding view, which created more complexity for the team.

**Achievements of the project:**
- Adding significant enhancements to the UI via a new wedding list pane with the live-updating current wedding
  viewed name on top, a more appealing layout of the contact card with tags on the right, and multicolored tags
- Enhanced tags management and filtering capabilities, alongside additional wedding-specific commands that handle
  wedding management.
- Achieving a passing code coverage of 76% reflecting thorough testing of core features.

--------------------------------------------------------------------------------------------------------------------
## **Appendix: Planned Enhancements**

1. **Enhanced duplicate detection handling for Wedding Names and Contacts**

   Wedding Names should be compared on a case-insensitive basis ('john' and 'jOhN' are considered the same
   wedding names) and contacts should be determined to be duplicates if both emails and/or both phone numbers of a
   contact are identical, rather than just by the basis of an identical phone number.

2. **More advanced date validation for invalid dates**

   Implement stricter date validation to handle invalid dates like February 29 on non-leap years or days that exceed the
   maximum for a given month (e.g., 31st in months with only 30 days). This enhancement will ensure that users are
   notified of invalid dates instead of automatically adjusting them to the nearest valid date when using `addw` or
   `editw`.

3. **More accurate error messages for extreme numerical inputs**

   Improve error handling to provide an "invalid index" message instead of a "wrong format error' for contact indexes
   provided which are larger than the MAX_INT or equal to or below 0 (negative numbers).

4. **Improve parsing for address fields with text resembling flags (eg. `a/` or `t/`)**

   Update address parsing logic to differentiate between actual command flags and address text that may resemble these
   flags to allow users to store addresses like `1 Street e/b Building` or `Frenk t/t road` without triggering errors
   that multiple values have been specified for single-valued fields.

5. **Add support for names with non-alphanumeric characters (e.g., -, @, .).**

    Special characters like hyphens (-), at symbols (@), and periods (.), are common in some names, thus there will be an 
    update to allow the addition of names with special characters. This update will ensure that names with such characters can be added without errors thrown.
