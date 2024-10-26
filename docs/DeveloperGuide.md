# TAHub Contacts Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* This project is built on the AddressBook-Level3 (AB3) project created by the [SE-EDU initiative](https://se-education.org).

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The _**Architecture Diagram**_ given above explains the high-level design of the App.

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

The _Sequence Diagram_ below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its _API_ in an `interface` with the same name as the Component.
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
* stores the enrollment data i.e: all `StudentCourseAssociation` objects contained in a `StudentCourseAssociationList` object
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

* can save both address book data, user preference data and enrollment data in JSON format, and read them back into corresponding objects.
* inherits from `AddressBookStorage`, `UserPrefStorage` and `StudentCourseAssociationListStorage`, which means it can be treated as any one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `tahub.contacts.commons` package.

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

#### Design considerations

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

### :fa-solid-compass: Product scope

**Target user profile**: **Undergraduate Computer Science Student** who is a **Teaching Assistant (TA)**.

* busy due to high CS workload
* has a need to manage a significant number of student contacts
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: manage student contacts faster than a typical mouse/GUI driven app

### :fa-solid-book: User stories

Priorities:
 <span style="color:#4CB140;">High (must have) - ★★★</span> |
 <span style="color:#F0AB00;">Medium (nice to have) - ★★</span> |
 <span style="color:#C9190B;">Low (unlikely to have) - ★</span>

| Priority                                       | As a ... User    | I want to ...                                         | So that I can ...                                              |
|------------------------------------------------|-------------------|-------------------------------------------------------|----------------------------------------------------------------|
| <span style="color:#4CB140;">★★★</span>      | Beginner          | Add contacts                                          | Track contact details for students joining the class           |
| <span style="color:#4CB140;">★★★</span>      | Beginner          | View contacts                                         | Get an overview of my students’ contact information            |
| <span style="color:#4CB140;">★★★</span>      | Beginner          | Delete contacts                                       | Remove students no longer in the class                         |
| <span style="color:#4CB140;">★★★</span>      | Beginner          | Edit contacts                                         | Update contact details when they change                        |
| <span style="color:#4CB140;">★★★</span>      | Beginner          | Add essential data about students                     | Track students’ progress and access it when needed             |
| <span style="color:#4CB140;">★★★</span>      | Beginner          | View essential data about students                    | Access key data for each student                               |
| <span style="color:#4CB140;">★★★</span>      | Beginner          | Delete essential data about students                  | Remove data for students who have left the class               |
| <span style="color:#4CB140;">★★★</span>      | Beginner          | Edit essential data about students                    | Update student information when changes occur                  |
| <span style="color:#F0AB00;">★★</span>       | Beginner          | Search for students                                   | Quickly find student contact details                           |
| <span style="color:#F0AB00;">★★</span>       | Intermediate      | Sort students by grades                               | Prioritize weaker students for follow-up                       |
| <span style="color:#F0AB00;">★★</span>       | Expert            | Delete students in bulk                               | Remove multiple students quickly, e.g., after class ends       |
| <span style="color:#C9190B;">★</span>        | Intermediate      | Add filters to searches                               | Narrow down search results to find specific students           |
| <span style="color:#F0AB00;">★★</span>       | Intermediate      | Get warnings before making major changes              | Avoid accidental changes to important student data             |
| <span style="color:#F0AB00;">★★</span>       | Beginner          | Explore app with sample student data                  | Test features without needing real data                        |
| <span style="color:#F0AB00;">★★</span>       | Beginner          | Access help for available commands                    | Learn how to use the app's functionality effectively           |
| <span style="color:#C9190B;">★</span>        | Intermediate      | Bulk import student data                              | Add multiple students at once                                  |
| <span style="color:#F0AB00;">★★</span>       | Beginner          | Export contact list to a CSV                          | Back up student contact information                            |
| <span style="color:#F0AB00;">★★</span>       | Intermediate      | Merge duplicate student entries                       | Reduce clutter in the contact list                             |
| <span style="color:#C9190B;">★</span>        | Beginner          | Filter students by attendance status                  | Track and follow up with absent students                       |
| <span style="color:#F0AB00;">★★</span>       | Intermediate      | Sort students alphabetically                          | Quickly locate students in the contact list                    |
| <span style="color:#C9190B;">★</span>        | Intermediate      | Add comments to student profiles                      | Record observations or important information about students    |
| <span style="color:#C9190B;">★</span>        | Beginner          | Assign preferred communication methods to parents     | Ensure efficient communication through preferred channels      |
| <span style="color:#C9190B;">★</span>        | Intermediate      | Flag students with missing contact details            | Ensure all required student information is complete            |
| <span style="color:#C9190B;">★</span>        | Beginner          | Set communication preferences for individual students | Communicate via their preferred method (Telegram, phone, etc.) |

### :fa-solid-user: Use cases

(For all use cases below, the **System** is the `TAHub Contacts` and the **Actor** is the `user`, unless specified otherwise)

<panel header="#### Use case: Add a contact" expanded>

**Main Success Scenario (MSS):**

1. User requests to add a contact with a name, email, and optionally a phone number, address, and tags.
2. System validates the input fields:
    * a. Checks if the name is valid (alphabets and spaces only).
    * b. Checks if the email format is valid and unique.
    * c. (Optional) Checks if the phone number is valid (8 digits) and unique.
    * d. (Optional) Validates address and tags.
3. System adds the contact if all validations pass.
4. System displays a success message, "New contact added: [Contact details]."

   Use case ends.

**Extensions:**

* 2a. The name is in an invalid format.
  * 2a1. System shows an error message, "Invalid name format."
      Use case resumes at step 1.

* 2b. The email is invalid or already exists.
  * 2b1. System shows an error message, "Invalid email format." or "Contact already exists."
      Use case resumes at step 1.

* 2c. The phone number is invalid or already exists.
  * 2c1. System shows an error message, "Invalid phone number format." or "Phone number already exists."
      Use case resumes at step 1.</panel>

<panel header="#### Use case: View contacts" expanded>

**Main Success Scenario (MSS):**

1. User requests to view all contacts.
2. System retrieves and displays the list of all contacts with their details (name, phone number, email, address, tags).
3. User views the list to get an overview of student contact information.

   Use case ends.</panel>

<<<<<<< HEAD
**Use case: Delete a contact**
=======
<panel header="#### Use Case: Get Warnings Before Making Major Changes" expanded>

**Main Success Scenario (MSS):**

1. Tutor initiates a major change (e.g., deleting a student record or modifying multiple student details at once).
2. System detects the action as a major change.
3. System prompts the tutor with a warning message describing the potential consequences (e.g., "Warning: You are about to delete [Student's name]. This action cannot be undone. Do you wish to proceed?").
4. Tutor reviews the warning and confirms whether to proceed or cancel.
5. If confirmed, the system proceeds with the requested changes and displays a success message.

   Use case ends.

**Extensions:**

* 2a. The list is empty.
  * 2a1. System shows a message, "No contacts available."
      Use case ends.</panel>

<panel header="#### Use case: Delete a contact" expanded>
>>>>>>> 891c7d84d1563eaa7eb78100dd524da03f496d15

**Main Success Scenario (MSS):**

1. User requests to delete a contact by providing the index of the contact in the list.
2. System validates the provided index.
3. System deletes the contact if the index is valid.
4. System displays a success message, "Deleted contact: [Contact details]."

   Use case ends.

**Extensions:**

* 2a. The index is out of bounds.
  * 2a1. System shows an error message, "Invalid index."
      Use case resumes at step 1.</panel>

<panel header="#### Use case: Edit a contact" expanded>

**Main Success Scenario (MSS):**

1. User requests to edit the contact by providing the index of the contact and the fields to update (name, phone, email, address, tags).
2. System validates the provided index and input fields:
    * a. Checks if the contact at the given index exists.
    * b. Validates the new name, email, phone, address, and tags as per the same rules as in the add contact case.
3. System updates the contact with the new details if all validations pass.
4. System displays a success message, "Contact updated: [Updated contact details]."

   Use case ends.

**Extensions:**

* 2a. The index is out of bounds.
  * 2a1. System shows an error message, "Invalid index."
      Use case resumes at step 1.

* 2b. The new name is invalid.
  * 2b1. System shows an error message, "Invalid name format."
      Use case resumes at step 1.

* 2c. The new email is invalid or already exists.
  * 2c1. System shows an error message, "Invalid email format." or "Contact already exists."
      Use case resumes at step 1.

* 2d. The new phone number is invalid or already exists.
  * 2d1. System shows an error message, "Invalid phone number format." or "Phone number already exists."
      Use case resumes at step 1.</panel>

<panel header="#### Use case: Add essential data about students" expanded>

**Main Success Scenario (MSS):**

1. User requests to add essential data (such as progress or status) for a specific student.
2. System prompts the user to provide the student’s essential data, such as progress, performance, or notes.
3. System validates the input and associates the data with the correct student.
4. System confirms the addition of the data with a success message.

   Use case ends.

**Extensions:**

* 2a. The provided data is invalid.
  * 2a1. System shows an error message, "Invalid data format."
      Use case resumes at step 1.

* 3a. The student does not exist in the system.
  * 3a1. System shows an error message, "Student not found."
      Use case ends.</panel>

<panel header="#### Use case: View essential data about students" expanded>

**Main Success Scenario (MSS):**

1. User requests to view essential data for a specific student.
2. System retrieves and displays the student's essential data, such as progress, performance, or notes.
3. User views the essential data to track the student's progress.

   Use case ends.

**Extensions:**

* 2a. The student does not exist in the system.
  * 2a1. System shows an error message, "Student not found."
      Use case ends.

* 2b. No essential data has been recorded for the student.
  * 2b1. System shows a message, "No essential data available for this student."
      Use case ends.</panel>

<panel header="#### Use case: Delete essential data about students" expanded>

**Main Success Scenario (MSS):**

1. User requests to delete essential data for a specific student.
2. System prompts the user for confirmation to delete the essential data for the student.
3. User confirms the deletion.
4. System deletes the student’s essential data and shows a success message, "Essential data for [Student] has been deleted."

   Use case ends.

**Extensions:**

* 2a. The student does not exist in the system.
  * 2a1. System shows an error message, "Student not found."
      Use case ends.

* 3a. User cancels the deletion.
  * 3a1. System aborts the deletion process.
      Use case ends.

* 4a. No essential data exists for the student.
  * 4a1. System shows a message, "No essential data to delete for this student."
      Use case ends.</panel>

<panel header="#### Use case: Edit essential data about students" expanded>

**Main Success Scenario (MSS):**

1. User requests to edit essential data for a specific student.
2. System prompts the user to provide new or updated essential data for the student.
3. System validates the input and updates the student’s essential data.
4. System shows a success message, "Essential data for [Student] has been updated."

   Use case ends.

**Extensions:**

* 2a. The student does not exist in the system.
  * 2a1. System shows an error message, "Student not found."
      Use case ends.

* 2b. The provided data is invalid.
  * 2b1. System shows an error message, "Invalid data format."
      Use case resumes at step 1.

* 4a. No changes were made to the data.
  * 4a1. System shows a message, "No changes detected."
      Use case ends.</panel>

<panel header="#### Use case: Search students" expanded>

**Main Success Scenario (MSS):**

1. User requests to find students by providing keywords.
2. System searches for contacts whose names contain the given keywords (case-insensitive).
3. System displays the matching contacts if found.

   Use case ends.

**Extensions:**

* 2a. No contacts match the keywords.
  * 2a1. System shows a message, "No contacts found."
      Use case ends.</panel>

<panel header="#### Use case: Sort students by grades" expanded>

**Main Success Scenario (MSS):**

1. User requests to sort students by their grades.
2. System retrieves all students and their corresponding grades.
3. System sorts the students by grades in ascending order (prioritizing weaker students).
4. System displays the sorted list of students with their grades for follow-up.

   Use case ends.

**Extensions:**

* 3a. No students have grades recorded.
  * 3a1. System shows a message, "No grade data available for sorting."
      Use case ends.</panel>

<panel header="#### Use case: Delete students in bulk" expanded>

**Main Success Scenario (MSS):**

1. User requests to delete multiple students from the system.
2. System prompts the user to confirm the deletion of the selected students.
3. User confirms the bulk deletion.
4. System deletes the selected students and displays a success message for the deletion.

   Use case ends.

**Extensions:**

* 1a. User does not select any students for deletion.
  * 1a1. System shows a message, "No students selected for deletion."
      Use case ends.

* 2a. User cancels the deletion.
  * 2a1. System aborts the bulk deletion process.
      Use case ends.

* 4a. One or more of the selected students do not exist in the system.
  * 4a1. System skips deleting non-existent students and completes deletion for valid students.
  * 4a2. System shows a message, "Some students could not be deleted as they do not exist."</panel>

<panel header="#### Use case: Add filters to searches" expanded>

**Main Success Scenario (MSS):**

1. User requests to search for students with additional filters (e.g., by grade, tag, or other criteria).
2. System prompts the user to specify the filters (e.g., grade range, specific tag, etc.).
3. User specifies the filters and submits the search request.
4. System retrieves students that match the specified filters and displays the filtered list.

   Use case ends.

**Extensions:**

* 2a. The provided filter is invalid.
  * 2a1. System shows an error message, "Invalid filter. Please provide valid criteria."
      Use case resumes at step 2.

* 4a. No students match the specified filters.
  * 4a1. System shows a message, "No students found matching the filters."
      Use case ends.

* **4a. Tutor cancels the operation.**
  * System aborts the change and returns to the previous state.
  * System displays a message: "Operation cancelled."
  * **Use case ends.**</panel>

<<<<<<< HEAD
**Use Case: Get Warnings Before Making Major Changes**

1. Tutor initiates a major change (e.g., deleting a student record or modifying multiple student details at once).
2. System detects the action as a major change.
3. System prompts the tutor with a warning message describing the potential consequences (e.g., "Warning: You are about to delete [Student's name]. This action cannot be undone. Do you wish to proceed?").
4. Tutor reviews the warning and confirms whether to proceed or cancel.
5. If confirmed, the system proceeds with the requested changes and displays a success message.

   **Use case ends.**

**Extensions:**

* 2a. The list is empty.
    * 2a1. System shows a message, "No contacts available."
      Use case ends.

**Use Case: Explore App with Sample Student Data**
=======
<panel header="#### Use Case: Explore App with Sample Student Data" expanded>
>>>>>>> 891c7d84d1563eaa7eb78100dd524da03f496d15

**Main Success Scenario (MSS):**

1. Tutor selects an option to load the app with sample student data.
2. System loads pre-populated sample student data into the app.
3. Tutor interacts with the sample data, testing features such as adding, editing, and deleting students, without affecting any real data.
4. System processes tutor actions using the sample data.
5. Tutor completes the exploration of the app and returns to normal mode.
6. System displays a message confirming that all changes made in the sample mode are not saved.

   **Use case ends.**</panel>

<panel header="#### Use Case: Access Help for Available Commands" expanded>

**Main Success Scenario (MSS):**

1. Tutor requests help (e.g., by typing a help command or selecting a help option from the menu).
2. System displays a list of available commands and their descriptions.
3. Tutor reviews the commands and selects one for further clarification.
4. System provides detailed usage instructions and examples for the selected command.
5. Tutor follows the instructions to learn how to use the specific functionality.

   **Use case ends.**

**Extensions:**

* **3a. Tutor requests help for an unsupported command.**
  * System shows an error message: "Command not recognized. Please review the available commands."
  * **Use case resumes at step 3.**</panel>

<panel header="#### Use Case: Bulk Import Student Data" expanded>

**Main Success Scenario (MSS):**

1. Tutor selects the bulk import option in the system.
2. System prompts the tutor to upload a file containing student data in the required format (e.g., CSV).
3. Tutor uploads the file.
4. System validates the file format and student data based on predefined rules (e.g., required fields such as name, email, and valid formats).
5. If validation passes, system imports the students into the app.
6. System displays a success message: "Successfully imported [number] students."
7. Tutor reviews the imported student data.

   **Use case ends.**

**Extensions:**

* **4a. File format is invalid.**
  * System displays an error message: "Invalid file format. Please use the correct format (e.g., CSV)."
  * **Use case resumes at step 2.**

* **4b. Some student data is invalid.**
  * System displays an error message: "Error importing [X] students due to invalid data (e.g., missing fields, invalid email format). Please correct the data and try again."
  * System provides the option to retry the import after corrections.
  * **Use case resumes at step 2.**</panel>

<panel header="#### Use Case: Export Contact List to a CSV" expanded>

**Main Success Scenario (MSS):**

1. Tutor selects the option to export the contact list.
2. System prompts the tutor to specify the file name and location for the CSV file.
3. Tutor confirms the file name and location.
4. System generates the CSV file with the contact list, including details such as name, phone number, email, and address.
5. System displays a success message: "Contact list successfully exported to [file location]."

   **Use case ends.**

**Extensions:**

* **2a. Export fails due to file system error (e.g., permission denied).**
  * System displays an error message: "Export failed. Please check your file permissions and try again."
  * **Use case resumes at step 2.**</panel>

<panel header="#### Use Case: Merge Duplicate Student Entries" expanded>

**Main Success Scenario (MSS):**

1. Tutor selects the option to merge duplicate student entries.
2. System scans the contact list for potential duplicates (based on name, email, or phone number).
3. System presents the tutor with a list of duplicate entries and prompts for confirmation of the merge.
4. Tutor confirms which entries to merge.
5. System merges the selected entries and updates the contact list accordingly.
6. System displays a success message: "Duplicates successfully merged."

   **Use case ends.**

**Extensions:**

* **3a. No duplicates found.**
  * System displays a message: "No duplicates found in the contact list."
  * **Use case ends.**

* **4a. Tutor cancels the merge.**
  * System aborts the merge operation and returns to the previous screen.
  * **Use case ends.**</panel>

<panel header="#### Use Case: Filter Students by Attendance Status" expanded>

**Main Success Scenario (MSS):**

1. Tutor selects the option to filter students by attendance status.
2. System prompts the tutor to choose a specific attendance status (e.g., present, absent, late).
3. Tutor selects the desired attendance status.
4. System filters and displays the list of students matching the selected attendance status.
5. Tutor reviews the filtered list to track or follow up with the absent students.

   **Use case ends.**

**Extensions:**

* **4a. No students match the selected attendance status.**
  * System displays a message: "No students match the selected attendance status."
  * **Use case ends.**</panel>

<panel header="#### Use Case: Sort Students Alphabetically" expanded>

**Main Success Scenario (MSS):**

1. Tutor selects the option to sort students alphabetically.
2. System prompts the tutor to choose a sorting criterion (e.g., by first name, by last name).
3. Tutor selects the desired criterion.
4. System sorts the students based on the selected criterion.
5. Tutor reviews the sorted contact list for quick reference or location.

   **Use case ends.**

**Extensions:**

* **4a. Contact list is empty.**
  * System displays a message: "No students available to sort."
  * **Use case ends.**</panel>

<panel header="#### Use Case: Add Comments to Student Profiles" expanded>

**Main Success Scenario (MSS):**

1. Tutor selects a student profile.
2. Tutor chooses the option to add a comment to the student's profile.
3. System prompts the tutor to enter the comment.
4. Tutor enters the desired observation or important information about the student as a comment.
5. System saves the comment and associates it with the student's profile.
6. System displays a success message: "Comment added to [Student's name] profile."

   **Use case ends.**

**Extensions:**

* **4a. Comment is empty or invalid.**
  * System displays an error message: "Invalid comment. Please enter a valid comment."
  * **Use case resumes at step 3.**</panel>

<panel header="#### Use Case: Assign Preferred Communication Methods to Parents" expanded>

**Main Success Scenario (MSS):**

1. Tutor selects a student profile.
2. Tutor chooses the option to assign a preferred communication method for the student's parents.
3. System displays available communication methods (e.g., phone, email, messaging apps).
4. Tutor selects one or more preferred methods.
5. System saves the selected preferences for the student's parents.
6. System displays a success message: "Preferred communication method(s) saved for [Student's name] parents."

   **Use case ends.**

**Extensions:**

* **4a. No communication method selected.**
  * System displays an error message: "Please select at least one communication method."
  * **Use case resumes at step 3.**</panel>

<panel header="#### Use Case: Flag Students with Missing Contact Details" expanded>

**Main Success Scenario (MSS):**

1. Tutor selects the option to check for missing contact details.
2. System scans the contact list for students with incomplete or missing contact information (e.g., missing phone number or email).
3. System displays a list of students with incomplete contact details.
4. Tutor chooses to review the flagged students and updates the missing information as needed.

   **Use case ends.**

**Extensions:**

* **3a. No students with missing contact details.**
  * System displays a message: "All student contact details are complete."
  * **Use case ends.**</panel>

<panel header="#### Use Case: Set Communication Preferences for Individual Students" expanded>

**Main Success Scenario (MSS):**

1. Tutor selects a student profile.
2. Tutor chooses the option to set a preferred communication method for the student.
3. System displays available communication methods (e.g., Telegram, phone, email).
4. Tutor selects the student's preferred method.
5. System saves the selected preference.
6. System displays a success message: "Preferred communication method saved for [Student's name]."

   **Use case ends.**

**Extensions:**

* **4a. No communication method selected.**
  * System displays an error message: "Please select a communication method."
  * **Use case resumes at step 3.**</panel>

<br>

### :fa-solid-clipboard-list: Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.

_{More to be added}_

### :fa-solid-circle-question: Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more _exploratory_ testing.

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
