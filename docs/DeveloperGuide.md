---
layout: page
title: Developer Guide
---

* Table of Contents
  {:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the
  original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [
_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create
and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of
classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java)
and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is
in charge of the app launch and shut down.

* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues
the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding
  API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using
the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component
through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the
implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified
in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts
e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`,
inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the
visible GUI.

The UI for the Single page person view is a standalone UI window that is called through the functionalities of the
application itself.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that
are in the `src/main/resources/view` folder. For example, the layout of
the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java)
is specified
in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API
** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API
call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates
   a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which
   is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take
   several interactions (between the command object and the `Model`) to achieve.
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:

* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a
  placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse
  the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as
  a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser`
  interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API
** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which
  is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to
  this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as
  a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they
  should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>

### Storage component

**API
** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,

* can save both address book data and user preference data in JSON format, and read them back into corresponding
  objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only
  the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects
  that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo
history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the
following operations:

* `VersionedAddressBook#commit()`— Saves the current address book state in its history.
* `VersionedAddressBook#undo()`— Restores the previous address book state from its history.
* `VersionedAddressBook#redo()`— Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()`
and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the
initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command
calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes
to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book
state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also
calls `Model#commitAddressBook()`, causing another modified address book state to be saved into
the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing
the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer`
once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once
to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such
as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`.
Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not
pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be
purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern
desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

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

**Target user profile**

* Real estate agents
* Need manage a large number of clients with varying details
* Need to record past engagement with clients
* Can type fast
* Prefers typing to mouse interactions
* Is reasonably comfortable using CLI apps

**Value proposition**: 
This app helps real estate agents efficiently manage client relationships by centralizing contact details and logging
interactions. Also, automated reminders and notes ensure that you never miss an opportunity to engage clients. It
simplifies client management but focuses only on handling moderate-sized contact lists, without support for financial
transactions, property details, or large-scale CRM functions.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                                                 | So that I can…​                                                                                |
|----------|--------------------------------------------|--------------------------------------------------------------|------------------------------------------------------------------------------------------------|
| `* * *`  | new user                                   | see usage instructions                                       | refer to instructions when I forget how to use the App                                         |
| `* * *`  | user                                       | add a new person                                             |                                                                                                |
| `* * *`  | user                                       | delete a person                                              | remove entries that I no longer need                                                           |
| `* * *`  | user with many persons in the address book | find a person by name                                        | locate details of persons without having to go through the entire list                         |
| `* * *`  | user with many persons in the address book | find people by category                                      | view a collection of persons in the same category without having to go through the entire list |
| `* * *`  | salesperson                                | add a new contact with details (name, phone, email)          | store client information in the address book                                                   |
| `* * *`  | salesperson                                | search for a contact by name                                 | quickly find the client without manually scrolling through the list                            |
| `* * *`  | user                                       | add history log to a person                                  | keep a record of past activities for future references                                         |
| `* *`    | user                                       | be reminded of important events                              | minimize chance of missing these important events                                              |
| `* *`    | salesperson                                | system to check the validity of a phone number               | avoid entering incorrect or incomplete data                                                    |
| `* *`    | salesperson                                | view the history of interactions with a contact              | keep track of past communication (e.g., notes, calls, meetings)                                |
| `* *`    | salesperson                                | mark certain contacts as favorites                           | easily access the most important clients at the top of the list                                |
| `* *`    | salesperson                                | store incomplete information                                 | later update it with more details or correct outdated information                              |
| `* *`    | user with many persons in the address book | sort persons by name                                         | locate a person easily                                                                         |
| `* *`    | salesperson                                | sort contacts alphabetically by their name                   | easily browse through the address book                                                         |
| `* *`    | salesperson                                | view all information about a contact on one page             | see all relevant details without clicking multiple times                                       |
| `*`      | salesperson                                | receive a confirmation prompt before clearing all contacts   | avoid accidental deletion of the entire address book                                           |
| `*`      | salesperson                                | receive a success notification after adding/editing/deleting | know the operation was completed correctly                                                     |
| `*`      | user                                       | hide private contact details                                 | minimize chance of someone else seeing them by accident                                        |

This table organizes the user stories based on priority and the specific tasks or functionalities that the salesperson or user would need in the address book system.


*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified
otherwise)

**Use case: Delete a person**

**MSS**

1. User requests to list persons
2. AddressBook shows a list of persons
3. User requests to delete a specific person in the list
4. AddressBook deletes the person

   Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. AddressBook shows an error message.

      Use case resumes at step 2.

Let's move on to the next use case based on **Feature 3: Alphabetical Sorting of Contact List by Name**. Following the structure from the document, this use case will also include references to related use cases where applicable.

---

### Use Case: **UC02 - Sort Contact List Alphabetically**
**Actor**: Salesperson  
**Precondition**: The system has a non-empty list of contacts.  
**Guarantees**: The contact list is sorted in the specified order (ascending or descending), and the Salesperson can view the sorted list.

#### Main Success Scenario (MSS):
1. The Salesperson issues the `sort` command with a specified order (ascending or descending).
2. The system validates the sorting parameter (`asc` or `desc`).
3. The system sorts the contact list based on the specified order.
4. The sorted contact list is displayed.
5. The system shows a success message: "Contacts sorted in `<ascending/descending>` order."

   **Use case ends.**

---

#### Extensions:
- **2a.** The entered sorting parameter is invalid (e.g., `ascending` instead of `asc`).
    - **2a1.** The system displays an error message: "Error: Invalid sorting order. Use 'asc' for ascending or 'desc' for descending."
    - **2a2.** The Salesperson corrects the sorting parameter and reissues the command.
    - **Use case resumes from Step 2.**

- **2b.** The Salesperson forgets to specify the sorting order.
    - **2b1.** The system displays an error message: "Error: No sorting order provided. Please specify 'asc' or 'desc'."
    - **2b2.** The Salesperson adds the correct sorting order and reissues the command.
    - **Use case resumes from Step 2.**

---

#### Including Related Use Cases:
This use case includes the following related use cases:
- **UC03 - Validate Command Parameters**: This use case is referenced in Step 2 for validating the sorting parameter entered by the Salesperson.

---

#### Variations:
- **1a.** The Salesperson issues the `sort asc` command to sort the contacts in ascending order.
    - **Use case proceeds normally from Step 2.**

- **1b.** The Salesperson issues the `sort desc` command to sort the contacts in descending order.
    - **Use case proceeds normally from Step 2.**

---

#### Postconditions:
- The contact list is displayed in the specified alphabetical order. The system does not change the underlying data, only the view of the contact list.

---

Let's proceed with the next few use cases, following the same detailed structure. I'll cover the **Interaction History Log for Each Contact**, **Find Function**, and **Add or Edit Remarks for a Contact** use cases.

---

### Use Case: **UC03 - Log Interaction with Contact**
**Actor**: Salesperson  
**Precondition**: The contact already exists in the contact list.  
**Guarantees**: The interaction details are logged under the specified contact's history for future reference.

#### Main Success Scenario (MSS):
1. The Salesperson issues the `log` command with the required `ContactID` and interaction details.
2. The system validates the `ContactID` and interaction details.
3. The system logs the interaction for the specified contact.
4. The system displays a success message: "Interaction logged for contact `<ContactID>`."
5. The interaction history is updated in the contact's profile.

   **Use case ends.**

---

#### Extensions:
- **2a.** The `ContactID` is invalid or does not exist.
    - **2a1.** The system displays an error message: "Error: Contact not found. Please check the `ContactID` and try again."
    - **2a2.** The Salesperson corrects the `ContactID` and reissues the command.
    - **Use case resumes from Step 2.**

- **2b.** The interaction details are missing.
    - **2b1.** The system displays an error message: "Error: Interaction details are required."
    - **2b2.** The Salesperson adds the interaction details and reissues the command.
    - **Use case resumes from Step 2.**

---

#### Including Related Use Cases:
- **UC05 - Validate ContactID**: This use case is referenced in Step 2 for verifying the existence and correctness of the `ContactID`.
- **UC06 - Display Contact History**: This use case allows the Salesperson to view the full history of interactions logged for a specific contact.

---

#### Variations:
- **1a.** The Salesperson includes an optional date in the interaction log.
    - **Use case proceeds normally from Step 2.**

- **1b.** The Salesperson only logs the interaction without specifying a date.
    - **Use case proceeds normally from Step 2.**

---

#### Postconditions:
- The interaction is saved in the contact’s history, and it can be reviewed in future interactions.

---

I see now! You want to modify the **use case** to match the **Find feature** you provided. Here's the corrected use case aligned with the modified **Find Contacts** feature:

---

### **Use Case: UC04 - Find Contacts by Name**

**Actor**: Salesperson  
**Precondition**: The system contains a list of contacts.  
**Guarantees**: The contacts matching the search criteria are displayed.

#### **Main Success Scenario (MSS):**
1. The Salesperson issues the `find` command with a name as the input.
2. The system searches the contact list for names matching the search input (either full or partial).
3. The system displays a list of matching contacts.
4. The system shows a success message: "Search results for `<Name>` displayed."

   **Use case ends.**

---

### **Extensions:**
- **2a.** The search input does not match any contacts.
  - **2a1.** The system displays an error message: "Error: No contacts found for `<Name>`."
  - **Use case ends.**

- **2b.** The search input is invalid (e.g., contains non-alphabetic characters).
  - **2b1.** The system displays an error message: "Error: Invalid name format. Please enter alphabetic characters only."
  - **Use case resumes from Step 1.**

---

### **Including Related Use Cases:**
- **UC05 - Add a New Contact**: If no contacts are found, the user may be prompted to add a new contact.

---

### **Variations:**
- **1a.** The Salesperson enters only part of a name for the search (e.g., `find John`).
  - **Use case proceeds normally from Step 2.**

- **1b.** The Salesperson searches by full name (e.g., `find John Smith`).
  - **Use case proceeds normally from Step 2.**

---

### **Postconditions:**
- A list of contacts matching the search input is displayed. If no matches are found, an error message is shown.

---

### Use Case: **UC05 - Add or Edit Remarks for a Contact**
**Actor**: Salesperson  
**Precondition**: The contact already exists in the contact list.  
**Guarantees**: The remark is added or updated for the specified contact.

#### Main Success Scenario (MSS):
1. The Salesperson issues the `remark` command with the required contact index and a remark message.
2. The system validates the contact index.
3. The system adds or updates the remark for the specified contact.
4. The system displays a success message: "Remark updated for contact `<ContactID>`."

   **Use case ends.**

---

#### Extensions:
- **2a.** The contact index is invalid or does not exist.
    - **2a1.** The system displays an error message: "Error: Invalid contact index. Please provide a valid index."
    - **2a2.** The Salesperson corrects the index and reissues the command.
    - **Use case resumes from Step 2.**

- **3a.** The remark message is missing.
    - **3a1.** The system displays an error message: "Error: Remark message is required."
    - **3a2.** The Salesperson adds the remark message and reissues the command.
    - **Use case resumes from Step 3.**

---

#### Including Related Use Cases:
- **UC06 - View Contact Details**: This use case is referenced when viewing the contact to verify that the remark was correctly added.
- **UC07 - Edit Contact Details**: This use case is often used if the Salesperson wants to edit other details of the contact after adding a remark.

---

#### Variations:
- **1a.** The Salesperson issues the `remark` command with a new remark for the contact.
    - **Use case proceeds normally from Step 2.**

- **1b.** The Salesperson edits an existing remark.
    - **Use case proceeds normally from Step 2.**

---

#### Postconditions:
- The remark is saved or updated for the contact, and it is displayed in the contact's profile.

---

Continuing from the previous use cases, I will now cover the **Single Page View for Full Contact Details**, **Favorite Functionality**, and **Reminder Notifications for Contact’s Birthday**.

---

### Use Case: **UC06 - View Full Contact Details**
**Actor**: Salesperson  
**Precondition**: The contact exists in the contact list.  
**Guarantees**: The full details of the contact are displayed in a single page view for the Salesperson.

#### Main Success Scenario (MSS):
1. The Salesperson issues the `view` command with a valid `ContactID`.
2. The system validates the `ContactID`.
3. The system retrieves and displays all the details of the contact in a dedicated view.
4. The system shows a success message: "Contact details displayed for: `<Name>`."

   **Use case ends.**

---

#### Extensions:
- **2a.** The `ContactID` is invalid or does not exist.
    - **2a1.** The system displays an error message: "Error: Invalid `ContactID`. Please provide a valid numeric identifier."
    - **2a2.** The Salesperson corrects the `ContactID` and reissues the command.
    - **Use case resumes from Step 2.**

- **3a.** The contact is missing some optional information (e.g., birthday, social media handle).
    - **3a1.** The system displays a message: "Some details are missing for this contact."
    - **Use case resumes from Step 4.**

---

#### Including Related Use Cases:
- **UC01 - Add a New Contact**: This use case is referenced if the Salesperson wants to add a new contact after viewing an incomplete or incorrect profile.
- **UC05 - Add or Edit Remarks for a Contact**: This use case is often referenced if the Salesperson decides to add or edit a remark after viewing a contact's full details.

---

#### Variations:
- **1a.** The Salesperson views a contact with all the details filled in (name, phone, email, address, birthday, and social media handle).
    - **Use case proceeds normally from Step 2.**

- **1b.** The Salesperson views a contact with only mandatory fields filled (name and phone).
    - **Use case proceeds normally from Step 2.**

---

#### Postconditions:
- The contact details are displayed in full, allowing the Salesperson to review or edit information as needed.

---

### Use Case: **UC07 - Mark Contact as Favorite**
**Actor**: Salesperson  
**Precondition**: The contact exists in the contact list.  
**Guarantees**: The contact is marked as a favorite, and the Salesperson can easily access it from the favorite list.

#### Main Success Scenario (MSS):
1. The Salesperson issues the `favorite` command with a valid `ContactID`.
2. The system validates the `ContactID`.
3. The system marks the contact as a favorite.
4. The system displays a success message: "Contact `<Name>` marked as a favorite."

   **Use case ends.**

---

#### Extensions:
- **2a.** The `ContactID` is invalid or does not exist.
    - **2a1.** The system displays an error message: "Error: Invalid `ContactID`. Please provide a valid numeric identifier."
    - **2a2.** The Salesperson corrects the `ContactID` and reissues the command.
    - **Use case resumes from Step 2.**

- **3a.** The contact is already marked as a favorite.
    - **3a1.** The system displays a message: "Contact is already marked as a favorite."
    - **Use case ends.**

---

#### Including Related Use Cases:
- **UC06 - View Full Contact Details**: This use case may be referenced if the Salesperson wants to mark a contact as favorite after reviewing its full details.
- **UC08 - View Favorite Contacts**: This use case allows the Salesperson to view all the favorite contacts in a dedicated section.

---

#### Variations:
- **1a.** The Salesperson marks a new contact as favorite.
    - **Use case proceeds normally from Step 2.**

- **1b.** The Salesperson marks an existing contact that was not previously marked as favorite.
    - **Use case proceeds normally from Step 2.**

---

#### Postconditions:
- The contact is marked as a favorite, and it will appear at the top of the contact list or in a separate "Favorites" section for easier access.

---

### Use Case: **UC08 - Add Birthday Reminder for a Contact**
**Actor**: Salesperson  
**Precondition**: The contact exists in the contact list and has a valid birthday.  
**Guarantees**: The birthday is saved for the contact, and the Salesperson will receive a reminder notification before the birthday.

#### Main Success Scenario (MSS):
1. The Salesperson issues the `birthday` command with a valid `ContactID` and birthday date.
2. The system validates the `ContactID` and birthday format.
3. The system stores the birthday for the contact.
4. The system displays a success message: "Birthday for `<Name>` logged as `<BirthdayDate>`."

   **Use case ends.**

---

#### Extensions:
- **2a.** The `ContactID` is invalid or does not exist.
    - **2a1.** The system displays an error message: "Error: Invalid `ContactID`. Please provide a valid numeric identifier."
    - **2a2.** The Salesperson corrects the `ContactID` and reissues the command.
    - **Use case resumes from Step 2.**

- **2b.** The birthday format is incorrect.
    - **2b1.** The system displays an error message: "Error: Invalid birthday format. Please use YYYY-MM-DD or MM-DD."
    - **2b2.** The Salesperson corrects the birthday format and reissues the command.
    - **Use case resumes from Step 2.**

---

#### Including Related Use Cases:
- **UC03 - Log Interaction with Contact**: This use case may be referenced when logging a reminder notification as an interaction.
- **UC06 - View Full Contact Details**: The Salesperson can view the saved birthday in the full contact profile.

---

#### Variations:
- **1a.** The Salesperson logs a birthday in the format `YYYY-MM-DD` (e.g., `2024-10-15`).
    - **Use case proceeds normally from Step 2.**

- **1b.** The Salesperson logs a birthday in the format `MM-DD` (e.g., `10-15`).
    - **Use case proceeds normally from Step 2.**

---

#### Postconditions:
- The birthday is stored in the contact’s profile, and a reminder will be triggered for the Salesperson close to the date.

---

I will now cover use cases related to the **Help Command**, **Delete Contact**, and **Clear All Contacts** features, continuing the detailed format and referencing other use cases where applicable.

---

### Use Case: **UC09 - Display Help Information**

**Actor**: Salesperson  
**Precondition**: The system is running, and the Salesperson needs assistance with commands.  
**Guarantees**: The system displays basic help information.

#### Main Success Scenario (MSS):
1. The Salesperson issues the `help` command.
2. The system displays help information with a list of commands.

   **Use case ends.**

---

#### Extensions:
- There are no extensions for this use case as the `help` command always succeeds.

---

#### Postconditions:
- Help information is displayed to assist the Salesperson with command usage.

---

### Use Case: **UC10 - Delete a Contact**
**Actor**: Salesperson  
**Precondition**: The contact exists in the contact list.  
**Guarantees**: The specified contact is removed from the system, and the Salesperson receives confirmation of the deletion.

#### Main Success Scenario (MSS):
1. The Salesperson issues the `delete` command with the contact index.
2. The system validates the contact index.
3. The system removes the specified contact from the contact list.
4. The system shows a success message: "Contact `<Name>` deleted."

   **Use case ends.**

---

#### Extensions:
- **2a.** The contact index is invalid or does not exist.
    - **2a1.** The system displays an error message: "Error: Invalid contact index. Please provide a valid contact index."
    - **2a2.** The Salesperson corrects the contact index and reissues the command.
    - **Use case resumes from Step 2.**

- **3a.** The Salesperson mistakenly deletes the wrong contact.
    - **3a1.** The system asks for confirmation before deletion: "Are you sure you want to delete this contact?"
    - **3a2.** The Salesperson confirms the deletion.
    - **Use case resumes from Step 3.**

---

#### Including Related Use Cases:
- **UC06 - View Full Contact Details**: The Salesperson may use this use case to view the contact’s full details before confirming the deletion.
- **UC09 - Display Help Information**: The `help` command will include instructions on how to use the `delete` command.

---

#### Variations:
- **1a.** The Salesperson deletes a contact by issuing the `delete` command with a valid index.
    - **Use case proceeds normally from Step 2.**

- **1b.** The Salesperson deletes a contact after viewing the contact details.
    - **Use case proceeds normally from Step 2.**

---

#### Postconditions:
- The contact is successfully deleted, and the Salesperson is notified of the deletion.

---

### Use Case: **UC11 - Clear All Contacts**
**Actor**: Salesperson  
**Precondition**: The system contains a list of contacts.  
**Guarantees**: All contacts are deleted from the system, and the Salesperson receives confirmation.

#### Main Success Scenario (MSS):
1. The Salesperson issues the `clear` command.
2. The system deletes all contacts from the contact list.
3. The system shows a success message: "All contacts have been deleted."

   **Use case ends.**

---

#### Extensions:
- There are no extensions for this use case as the `clear` command always succeeds.

---

#### Including Related Use Cases:
- **UC09 - Display Help Information**: The `help` command will include information on how to use the `clear` command.
- **UC10 - Delete a Contact**: This use case is related to clearing all contacts, as it handles individual deletion.

---

#### Variations:
- **1a.** The Salesperson clears all contacts after reviewing them using the `list` command.
    - **Use case proceeds normally from Step 2.**

---

#### Postconditions:
- All contacts are removed from the system, and the contact list is now empty.

---

### Use Case: **UC12 - Exit Application**
**Actor**: Salesperson  
**Precondition**: The Salesperson is interacting with the system and wants to close the application.  
**Guarantees**: The application closes safely, and any unsaved data is automatically saved.

#### Main Success Scenario (MSS):
1. The Salesperson issues the `exit` command.
2. The system checks for any unsaved changes and automatically saves them if needed.
3. The system closes the application.

   **Use case ends.**

---

#### Extensions:
- There are no extensions for this use case as the `exit` command always succeeds.

---

#### Including Related Use Cases:
- **UC13 - Saving Data Automatically**: This use case is directly referenced during Step 2 when the system attempts to automatically save data before closing the application.
- **UC09 - Display Help Information**: The `help` command will include information on how to use the `exit` command.

---

#### Variations:
- **1a.** The Salesperson exits the application without unsaved changes.
    - **Use case proceeds normally from Step 3.**

- **1b.** The Salesperson exits the application with unsaved changes, and the system successfully saves the data.
    - **Use case proceeds normally from Step 2.**

---

#### Postconditions:
- The application is closed, and all data is saved automatically if needed.

---

### Use Case: **UC13 - Saving Data Automatically**
**Actor**: System (implicitly triggered by the Salesperson’s actions)  
**Precondition**: The Salesperson performs any action that modifies the contact list.  
**Guarantees**: The system automatically saves the modified contact list to the storage file after each operation.

#### Main Success Scenario (MSS):
1. The Salesperson performs an operation that modifies the contact list (e.g., `add`, `delete`, `edit`).
2. The system automatically saves the modified contact list to the storage file.
3. The system ensures that the changes are successfully saved without explicit user intervention.

   **Use case ends.**

---

#### Extensions:
- **2a.** The system encounters an error while saving the data.
    - **2a1.** The system displays an error message: "Error: Unable to save data automatically. Please save manually."
    - **2a2.** The system retries saving automatically after the next operation or prompts the Salesperson to manually save the data.
    - **Use case ends.**

---

#### Including Related Use Cases:
- **UC12 - Exit Application**: This use case references the automatic saving mechanism during Step 2 to ensure data is saved before exiting the application.
- **UC14 - Edit Data File Manually**: This use case is referenced if the automatic save fails, and the Salesperson may choose to manually edit the data file.

---

#### Variations:
- **1a.** The Salesperson performs a valid operation that modifies the contact list.
    - **Use case proceeds normally from Step 2.**

- **1b.** The system automatically saves data after each operation without explicit user interaction.
    - **Use case proceeds normally from Step 2.**

---

#### Postconditions:
- The contact list is saved automatically after each operation, ensuring data consistency.

---

### Use Case: **UC14 - Edit Data File Manually**
**Actor**: Salesperson  
**Precondition**: The Salesperson has access to the data file and is familiar with the required format for making changes.  
**Guarantees**: The Salesperson can manually edit the data file, and the system loads the updated data during the next session.

#### Main Success Scenario (MSS):
1. The Salesperson manually opens the data file and edits the contents (e.g., adds, edits, or removes contacts) while adhering to the correct format.
2. The system loads the updated data file when the application is restarted.
3. The system verifies that the format is valid and updates the contact list accordingly.

   **Use case ends.**

---

#### Extensions:
- **3a.** The format of the data file is incorrect.
    - **3a1.** The system displays an error message: "Error: Data file format is incorrect. Please adhere to the correct format."
    - **3a2.** The Salesperson corrects the format and restarts the application.
    - **Use case resumes from Step 2.**

---

#### Including Related Use Cases:
- **UC13 - Saving Data Automatically**: This use case is referenced if the Salesperson manually edits the data file after automatic saving fails.
- **UC09 - Display Help Information**: The `help` command will provide guidance on how to ensure the data file is correctly formatted for manual edits.

---

#### Variations:
- **1a.** The Salesperson successfully edits the data file and restarts the application to load the changes.
    - **Use case proceeds normally from Step 3.**

- **1b.** The Salesperson makes an error in the format, and the system rejects the data file.
    - **Use case proceeds with Extension 3a.**

---

#### Postconditions:
- The updated data from the file is successfully loaded, provided the format is valid.

---

*{More to be added}*

### Non-Functional Requirements

1. **Portability:**
- The app should work on any _mainstream OS_ as long as it has Java `17` or above installed.

2. **Performance:**
  - The app should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
  - The search and sort functions should return results within 2 seconds for the average number of contacts.

3. **Efficiency**
  - A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be
    able to accomplish most of the tasks faster using commands than using the mouse.

4. **Usability:**
  - The interface should be intuitive, requiring less than 15 minutes of training for a real estate agent to become proficient.
  - The app should provide clear error messages for incorrect inputs (e.g., invalid phone numbers, dates) and guide the user in correcting them.

5. **Reliability:**
  - The app must have an uptime of at least 99.5% during business hours.
  - Data, such as contacts and interaction logs, should be autosaved after each modification, ensuring no loss of data during usage.

6. **Scalability:**
  - While optimized for a moderate number of contacts (up to 1000), the app should still function with reduced performance for up to 2,000 contacts.

7. **Maintainability:**
  - The codebase should follow standard code style and be modular, ensuring ease of updates and bug fixes.
  - The app should allow easy export and import of contact data for backup or migration purposes.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS

* **Real Estate Agent**: A professional who manages the buying, selling, and renting of properties, and maintains relationships with clients.

* **Contact List**: A digital repository where client details such as name, phone number, address, and email are stored.

* **Interaction Log**: A chronological record of communication or meetings with a client, typically used to track past engagements.

* **CLI (Command Line Interface)**: A user interface where commands are typed, offering a faster alternative to graphical interface interactions.

* **Favorite**: A contact marked as important for easy access.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be
       optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    2. Test case: `delete 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message.
       Timestamp in the status bar is updated.

    3. Test case: `delete 0`<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

    4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

    1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
