---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# NovaCare Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

NovaCare is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).

The project simulates an ongoing software project for a desktop application (called AddressBook) used for managing contact details.


The following libraries are used for this project:
* [JavaFX](https://openjfx.io/) for GUI
* [JUnit](https://junit.org/junit5/) for testing

--------------------------------------------------------------------------------------------------------------------

## **Setting Up, Getting Started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-F15-1/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-F15-1/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.
* [**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI Component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-F15-1/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-F15-1/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-F15-1/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic Component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-F15-1/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to delete a patient).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model Component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-F15-1/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the address book data i.e., all `Task` objects (which are contained in a `UniqueTaskList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores the currently 'selected' `Task` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Task>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage Component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-F15-1/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common Classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------
## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Add Task feature
#### Implementation
The `addtask` command is used to add a new task to a patient in NovaCare. It is executed when a user adds a task to a patient. This command operates on the `Model` component, updating the target patient by appending a new task to their list of tasks.

The following methods are involved:
* `AddTaskCommand#execute(Model model)` — Adds a new `Task` to the specified `Patient`.
* `Model#addTask(Task task)`  — Adds a new `Task` to the model.
* `Task`  — Represents a task to be added.

#### Example usage scenario:
1. The user enters the `addTask` command with the patient index and task description.
2. The command fetches the selected patient and create a new `Task`.
3. A check is performed to ensure the task does not already exist for the patient.
4. The AddTaskCommand calls Model#addTask(Task task) to add the task to the model.
5. The changes are committed to the model and a success message is displayed to the user.

<puml src="diagrams/AddTaskSequenceDiagram.puml" />
<box type="info" seamless>

**Note:** The lifeline for `AddTaskCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues further down.
</box>

Below is an activity diagram that explains what happens when a user tries to add a task:
<puml src="diagrams/AddTaskActivityDiagram.puml" />

### Find Task feature
#### Implementation
The `findtask` command is used to find all tasks from a specified patient index in NovaCare. It is executed when a user wants to view all tasks for a specific patient. This command operates on the `Model` component, searching for all tasks that match the specified patient index.

The following methods are involved:
* `FindTaskCommand#execute(Model model)` — Finds all tasks for the specified `Patient` index.
* `Model#updateFilteredTaskList(Predicate<Task> predicate)`  — Updates the filtered task list to show only tasks that match the predicate.
* `Task#getPatient()`  — Returns the patient index of the task.

#### Example usage scenario:
1. The user selects the patient index and executes the `findtask` command. The command fetches the selected patient and filter the task list to show tasks related to that particular patient.
2. The model applies the predicate to the task list, showing only tasks that match the predicate.
3. The GUI displays the filtered task list to the user.

<puml src="diagrams/FindTaskSequenceDiagram.puml" />
<box type="info" seamless>

**Note:** The lifeline for `FindTaskCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues further down.
</box>

Below is an activity diagram that explains what happens when a user tries to find tasks:
<puml src="diagrams/FindTaskActivityDiagram.puml" />
<br>

#### Proposed Implementation

The proposed undo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` class with an undo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()` and `Model#undoAddressBook()` respectively.

Given below is an example usage scenario and how the undo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th patient in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new patient. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the patient was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

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

#### Design Considerations:

**Aspect: How Undo Executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the patient being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

--------------------------------------------------------------------------------------------------------------------

## **Documentation, Logging, Testing, Configuration, Dev-Ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product Scope

**Target User Profile**:

* Private nurse in the healthcare industry managing patients
* Has many patients across different wards with varying care levels (priority levels)
  * where each patient has varying tasks
* Mainly done in desktop app
* Used to typing to fill out user information
* Is comfortable using CLI

**Value proposition**:
provides a patient management system for nurses to use which is faster than GUI/mouse driven apps


### User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​  | I want to …​                                       | So that I can…​                                                                |
|----------|----------|----------------------------------------------------|--------------------------------------------------------------------------------|
| `* * *`  | Nurse    | add in emergency contact numbers                   | refer to them during an emergency                                              |
| `* * *`  | Nurse    | delete emergency contact numbers                   | remove irrelevant emergency contacts                                           |
| `* * *`  | Nurse    | assign multiple categories to a patient            | quickly identify the care level                                                |
| `* * *`  | Nurse    | add tasks                                          | track what task needs to be done                                               |
| `* * *`  | Nurse    | delete tasks                                       | remove irrelevant tasks                                                        |
| `* * *`  | Nurse    | assign priority levels to patients                 | recognize which patients need more immediate attention                         |
| `* * *`  | Nurse    | add tags to patients                               | recognize the allergies for each patient                                       |
| `* * `   | Nurse    | change the priority level for each patient         | reflect changes in their care status                                           |
| `* * `   | Nurse    | reset the priority level for each patient          | reflect changes in their care status                                           |
| `* *`    | Nurse    | edit the name for each patient                     | reflect changes in the event that the information was keyed in wrongly         |
| `* *`    | Nurse    | edit the phone number for each patient             | reflect changes in the event that the information was keyed in wrongly         |
| `* *`    | Nurse    | edit the emergency contact for each patient        | reflect changes in the event that the information was keyed in wrongly         |
| `* *`    | Nurse    | edit tags for each patient                         | reflect changes in their allergies                                             |
| `* *`    | Nurse    | find a patient by their name                       | access their details quickly without scrolling through the list                |
| `* *`    | Nurse    | find tasks by their description                    | access patients with the same task quickly without scrolling through the list  |
| `* *`    | Nurse    | list tasks that are incomplete                     | prioritize my work during the shift                                            |
| `* *`    | Nurse    | list tasks that are complete                       | double check that my the tasks that I have marked complete are indeed complete |
| `* *`    | Nurse    | mark tasks as complete                             | update the tasks that I have completed                                         |
| `* *`    | Nurse    | unmark tasks                                       | to unmark the tasks that I have accidentally marked as completed               |
| `*`      | Nurse    | have the ability to clear all data from the system | reset the system for a new set of patients efficiently                         |
| `*`      | Nurse    | be able to exit the program using a command        | quickly close the application when done                                        |
| `*`      | Nurse    | edit the email for each patient                    | reflect changes in the event that the information was keyed in wrongly         |
| `*`      | Nurse    | edit the address for each patient                  | reflect changes in the event that the information was keyed in wrongly         |


### Use Cases

(For all Use Cases below, the **System** is the `NovaCare` and the **Actor** is the `nurses`, unless specified otherwise)

**Use Case: UC01 - Add Patient**

**MSS**

1. Nurse requests to add patient details to the system.
2. NovaCare adds the patient to the list.

   Use Case ends.

**Extensions**

* 1a. The command format is incorrect or one or more compulsory fields (name, phone number, email or address) are omitted.

    * 1a1. NovaCare displays an error message for incorrect command format.<br>Use Case ends.<br></br>

* 1b. One of the parameters are invalid.

    * 1b1. NovaCare displays an error message specific to the invalid field entry.<br>Use Case ends.<br></br>

* 1c. A patient with identical name and phone number already exists in the list.

    * 1c1. NovaCare displays an error message specifying the existence of a duplicate patient.<br>Use Case ends.

**Use Case: UC02 - Edit Patient**
**Preconditions: Patient list is not empty**

**MSS**

1. Nurse requests to edit the patient details of a specific patient.
2. NovaCare edits the details of the specified patient in the list.

   Use Case ends.

**Extensions**

* 1a. The command format is incorrect.

    * 1a1. NovaCare displays an error message for incorrect command format.<br>Use Case ends.<br></br>

* 1b. One of the parameters are invalid.

    * 1b1. NovaCare displays an error message specific to the invalid field entry.<br>Use Case ends.<br></br>

* 1c. A patient with identical name and phone number already exists in the list.

    * 1c1. NovaCare displays an error message specifying the existence of a duplicate patient.<br>Use Case ends.

**Use Case: UC03 - Delete Patient**
**Preconditions: Patient list is not empty**

**MSS**

1. Nurse requests to edit the patient details of a specific patient.
2. NovaCare deletes the patient from the patient list and the tasks related to the patient in the task list.

   Use Case ends.

**Extensions**

* 1a. The command format is incorrect or the index is missing.

    * 1a1. NovaCare displays an error message for incorrect command format.<br>Use Case ends.<br></br>

* 1b. The given index is invalid.

    * 1b1. NovaCare displays an error message specific to the invalid index.<br>Use Case ends.

**Use Case: UC04 - List Patients**

**MSS**

1. Nurse requests to list all patient details.
2. NovaCare displays a list of all patient details.

   Use Case ends.

**Use Case: UC05 - Find Patient(s) by Name**

**MSS**

1. Nurse requests to find patients whose name contains `keywords`.
2. NovaCare lists patient(s) whose name contains `keywords`.

   Use Case ends.

**Extensions**

* 1a. The `keywords` are empty.

    * 1a1. NovaCare displays an error message for incorrect command format.<br>Use Case ends.

**Use Case: UC06 - Add Emergency contact**
**Preconditions: Patient list is not empty**

**MSS**

1. Nurse requests to add emergency contact details to a selected patient.
2. NovaCare adds the emergency contact details to the selected patient.

   Use Case ends.

**Extensions**

* 1a. The command format is incorrect.

    * 1a1. NovaCare displays an error message for incorrect command format.<br>Use Case ends.<br></br>

* 1b. The given index is invalid.

    * 1b1. NovaCare displays an error message specific to the invalid index.<br>Use Case ends.<br></br>

* 1c. At least one of the parameters (name and/ or phone number) are invalid.

    * 1c1. NovaCare displays an error message specific to the invalid field entry.<br>Use Case ends.<br></br>

* 1d. NovaCare detects that the patient already has a registered emergency contact.

    * 1d1. NovaCare displays an error message that the patient already has a registered emergency contact.<br>Use Case ends.

**Use Case: UC07 - Delete Emergency contact**
**Preconditions: Patient list is not empty**

**MSS**

1. Nurse requests to delete the emergency contact details of a selected patient.
2. NovaCare deletes the emergency contact details of the selected patient.

   Use Case ends.

**Extensions**

* 1a. The command format is incorrect.

    * 1a1. NovaCare displays an error message for incorrect command format.<br>Use Case ends.<br></br>

* 1b. The given index is invalid.

    * 1b1. NovaCare displays an error message specific to the invalid index.<br>Use Case ends.<br></br>

* 1d. NovaCare detects that the patient already does not have a registered emergency contact.

    * 1d1. NovaCare displays an error message that the patient already does not have a registered emergency contact.<br>Use Case ends.

**Use Case: UC08 - Add Priority**
**Preconditions: Patient list is not empty**

**MSS**

1. Nurse requests to add a priority level to a specified patient.
2. NovaCare updates the given patient details with the entered priority level.

   Use Case ends.

**Extensions**

* 1a. The command format is incorrect.

    * 1a1. NovaCare displays an error message for incorrect command format.<br>Use Case ends.<br></br>

* 1b. The given index is invalid.

    * 1b1. NovaCare displays an error message specific to the invalid index.<br>Use Case ends.

**Use Case: UC09 - Reset Priority**

**MSS**

1. Nurse requests to reset a priority level of a specified patient.
2. NovaCare resets the given patient details with the default priority level.

   Use Case ends.


**Use Case: UC10 - Add Task**
**Preconditions: Patient list is not empty**

**MSS**

1. Nurse requests to add a new task to a specific patient.
2. NovaCare adds the task to the task list.

    Use Case ends.

Use Case ends.

**Extensions**

* 1a. The command format is incorrect or the description field is empty.

    * 1a1. NovaCare displays an error message for incorrect command format.<br>Use Case ends.<br></br>

* 1b. The given index is invalid.

    * 1b1. NovaCare displays an error message specific to the invalid index.<br>Use Case ends.

**Use Case: UC11 - Delete Task**
**Preconditions: Patient list and task list is not empty**

**MSS**

1. Nurse requests to delete a new task from the task list.
2. NovaCare deletes the task from the task list.

   Use Case ends.

**Extensions**

* 1a. The command format is incorrect.

    * 1a1. NovaCare displays an error message for incorrect command format.<br>Use Case ends.<br></br>

* 1b. The given index is invalid.

    * 1b1. NovaCare displays an error message specific to the invalid index.<br>Use Case ends.

**Use Case: UC12 - List Tasks**

**MSS**

1. Nurse requests to list all tasks.
2. NovaCare displays a list of all tasks.

   Use Case ends.

**Use Case: UC13 - Find Task(s) by Patient Index**

**MSS**

1. Nurse requests to find tasks for a specified patient's index.
2. NovaCare lists task(s) under patient with the given index.

   Use Case ends.

**Extensions**

* 1a. The command format is incorrect.

    * 1a1. NovaCare displays an error message for incorrect command format.<br>Use Case ends.<br></br>

* 1b. The given index is invalid.

    * 1b1. NovaCare displays an error message specific to the invalid index.<br>Use Case ends.

**Use Case: UC14 - Mark Task**
**Preconditions: Patient list and task list is not empty**

**MSS**

1. Nurse requests to mark a specified task as complete.
2. NovaCare marks given task to be complete.

   Use Case ends.

**Extensions**

* 1a. The command format is incorrect.

    * 1a1. NovaCare displays an error message for incorrect command format.<br>Use Case ends.<br></br>

* 1b. The given index is invalid.

    * 1b1. NovaCare displays an error message specific to the invalid index.<br>Use Case ends.

**Use Case: UC15 - Unmark Task**
**Preconditions: Patient list and task list is not empty**

**MSS**

1. Nurse requests to unmark a specified task to be incomplete.
2. NovaCare unmarks given task to be incomplete.

   Use Case ends.

**Extensions**

* 1a. The command format is incorrect.

    * 1a1. NovaCare displays an error message for incorrect command format.<br>Use Case ends.<br></br>

* 1b. The given index is invalid.

    * 1b1. NovaCare displays an error message specific to the invalid index.<br>Use Case ends.

**Use Case: UC16 - List Incomplete Tasks**

**MSS**

1. Nurse requests to list all incomplete tasks.
2. NovaCare displays a list of all incomplete tasks.

   Use Case ends.

**Use Case: UC17 - Clear All Patients and Tasks**

**MSS**

1. Nurse requests to clear all patients and tasks in NovaCare.
2. NovaCare clears all patient and task data.

   Use Case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 patients without a noticeable sluggishness in performance for typical usage.
3.  Should be able to hold up to 1000 tasks without a noticeable sluggishness in performance for typical usage.
4.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
5.  The GUI should work well for standard screen resolutions 1920x1080 and higher, and for screen scales 100% and 125%.
6.  The codebase should be modular, allowing easy updates or feature additions without affecting other parts of the system.
7.  The application should be packaged as a single jar file for easy deployment.
8.  The User Guide and Developer Guide should be PDF-friendly. No animated GIFs, embbedded videos, etc.
9.  The data should be stored in a local file in an editable text file that is easy to read and edit manually.
10. The data should not be stored in a Database Management System. 
11. The application should function without internet access as it is an offline tool. 
12. The application should run for extended periods without crashing.


### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Priority level**: A priority level is a value assigned to a patient to indicate the urgency of their medical condition. A priority level of 1 indicates a critical condition, 2 indicates a serious but non-critical condition, while 3 indicates a non-critical condition.
* **Emergency contact**: A person who is preferably close to the patient and is designated to receive information in case of an emergency
* **Task**: A task to be done for a specific patient
* **Database Management System**: A software system that uses a standard method to store and organize data.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for Manual Testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and Shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder.
   2. Open a terminal and navigate to the folder containing the jar file.
   3. Run the command `java -jar NovaCare.jar`<br>Expected: The app launches with a set of sample patients. The window size may not be optimal.<br></br>

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.
   2. Re-launch the app by running the command `java -jar NovaCare.jar`.<br>Expected: The most recent window size and location is retained.

### Adding a Patient

Adding a patient to NovaCare

   1. Test case: `add n/John Doe p/11111111 e/johndoe@example.com a/Blk 2 Bishan St 11 #06-230 t/Diabetic`
      * Expected: New patient added with confirmation message<br>`New patient added: John Doe; Phone: 11111111; Email: johndoe@example.com; Address: Blk 2 Bishan St 11 #06-230; Tags: [Diabetic]`
   2. Test case: Add the same patient as above
      * Expected: Error message<br>`This patient already exists in the NovaCare`
   3. Test case: `add n/John Doe e/johndoe@example.com a/Blk 2 Bishan St 11 #06-230 t/Diabetic`
      * Expected: Error message<br>`Invalid command format!`<br>`add: Adds a patient to the NovaCare. Parameters: n/NAME p/PHONE e/EMAIL a/ADDRESS [t/TAG]...`<br>`Example: add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 t/Diabetic`
   4. Test case: `add n/John Doe p/invalid e/johndoe@example.com a/Blk 2 Bishan St 11 #06-230 t/Diabetic`
      * Expected: Error message specific to invalid field<br>`Phone numbers should only contain numbers, and it should be at least 3 digits long`
   5. Other incorrect add commands to try: Any inputs with missing or invalid compulsory fields (name, phone number, email and address)
      * Expected: Similar to test cases 3 and 4

### Deleting a Patient

Deleting a patient while all patients are being shown<br>

Prerequisites: List all patients using the `list` command. Multiple patients in the list.

   1. Test case: `delete 1`
      * Expected: First contact is deleted from the list. Confirmation details of the deleted patient shown in the status message.
   2. Test case: `delete 0`
      * Expected: Error message<br>`Invalid command format!`<br>`delete: Deletes the patient identified by the index number used in the displayed patient list.`<br>`Parameters: INDEX (must be a positive integer)`<br>`Example: delete 1`
   3. Test case: `delete x`, where `x` is a number larger than the size of the patient list
      * Expected: Error message<br>`The patient index provided is invalid`
   4. Other incorrect delete commands to try: `delete`, `delete test`
      * Expected: Similar to test case 2

### Editing a Patient

Editing a patient's details while all patients are being shown<br>

Prerequisites: List all patients using the `list` command. At least 1 patient in the list.

1. Test case: `edit 1 p/22222222 e/john.doe@example.com`
    * Expected: Second patient in the list has their phone number and email edited. Confirmation details of the deleted patient shown in the status message.
2. Test case: `edit 0 n/Test`
    * Expected: Error message<br>`Invalid command format!`<br>`edit: Edits the details of the patient identified by the index number used in the displayed patient list. Existing values will be overwritten by the input values.`<br>`Parameters: INDEX (must be a positive integer) [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]...`<br>`Example: edit 1 p/91234567 e/johndoe@example.com`
3. Test case: `edit x n/Test`, where `x` is a number larger than the size of the patient list
    * Expected: Error message<br>`The patient index provided is invalid`
4. Test case: `edit 1`
    * Expected: Error message<br>`At least one field to edit must be provided.`
5. Test case: `edit 1 n/`
    * Expected: Error message specific to invalid field<br>`Names should only contain alphanumeric characters and spaces, and it should not be blank`
6. Test case: Edit patient to have the same name and phone number as another patient in the patient list
    * Expected: Error message<br>`This patient already exists in the NovaCare`
7. Other correct edit commands to try: Any inputs with valid fields
    * Expected: Simmilar to test case 1
8. Other incorrect delete commands to try: Any inputs with invalid fields
    * Expected: Similar to test case 5

### Finding a Patient

Finding a patient while all patients are being shown<br>

Prerequisites: List all patients using the `list` command. Multiple patients in the list. One patient with first name `John`, another patient with last name `Doe`, no patients with first or last name `Richard`.

1. Test case: `find John Doe`
    * Expected: NovaCare lists all patients with first or last name as `John` or `Doe`, with a success message<br>`2 patients listed!`
2. Test case: `find david`
    * Expected: No patients listed, with message<br>`The patient list is empty.`
3. Test case: `find`
    * Expected: Error message<br>`Invalid command format!`<br>`find: Finds all patients whose names contain any of the specified keywords (case-insensitive) and displays them as a list with index numbers.`<br>`Parameters: KEYWORD [MORE_KEYWORDS]...`<br>`Example: find alice bob charlie`
4. Other incorrect delete commands to try: Any other inputs with patient names not in the patient list
    * Expected: Similar to test case 2

### Changing/ Resetting Priority Level

Changing a priority level of a patient

Prerequisites: List all patients using the `list` command. At least 1 patient in the list.

1. Test case: `priority 1 l/1`
    * Expected: Priority level 1 is assigned to the patient with index 1. Details of the priority update are displayed in the status message<br>`Priority level 1 successfully set for John Doe`
2. Test case: `priority 1 l/reset`
    * Expected: Priority level reset to 3 for the patient with index 1. Details of the priority update are displayed in the status message<br>`Priority level reset to default for John Doe`
3. Test case: `deletelevel 1`
    * Expected: Priority level reset to 3 for the patient with index 1. Details of the priority update are displayed in the status message<br>`Priority level reset to default for John Doe`
4. Test case: `priority x l/2` where `x` is less than or equals to 0 or a number larger than the size of the patient list
    * Expected: Error message<br>`Invalid patient ID. Please enter a valid patient identifier.`
5. Test case: `priority` 
    * Expected: Error message<br>`Invalid command format!`<br>`priority: Sets the priority level for a patient identified by the index number used in the displayed patient list.`<br>`Parameters: INDEX l/LEVEL (LEVEL must be 1, 2, or 3 or 'reset' for default level)`<br>`Example: priority 1 l/2`<br>`Example: priority 2 l/reset`
6. Test case: `priority 1 l/x` where `x` is not `1`, `2`, `3` or `reset`
    * Expected: Error message<br>`Invalid priority level. Please enter a valid integer (1, 2, or 3) or 'reset'.`
7. Other incorrect priority commands to try: `priority 1 l/`
    * Expected: Similar to test case 6

### Adding Emergency Contact

Adding an emergency contact of a patient

Prerequisites: List all patients using the `list` command. At least 1 patient in the list.

1. Test case: `emergency 1 n/Charlotte Doe p/33333333`
    * Expected: Emergency contact with name `Charlotte Doe` and number `33333333` added to patient with index 1. Details of the emergency contact update are displayed in the status message<br>`Added emergency contact to John Doe: Charlotte Doe, 33333333`
2. Test case: Add another emergency contact to the same patient as above
    * Expected: Error message<br>`John Doe already has a saved emergency contact`
3. Test case: `emergency`
    * Expected: Error message<br>`Invalid command format!`<br>`emergency: Edits the emergency contact details of the patient identified by the index number used in the last patient listing.`<br>`Parameters: INDEX (must be a positive integer) n/[EMERGENCY CONTACT NAME]p/[EMERGENCY CONTACT NUMBER`<br>`Example: emergency 1 n/Richard Ng p/82943718`
4. Test case: `emergency x n/Charlotte Doe p/33333333` where `x` a number larger than the size of the patient list
    * Expected: Error message<br>`The patient index provided is invalid`
5. Test case: `emergency 1 n/Charlotte Doe`
    * Expected: Error message<br>`Please make sure both name and phone number is filled! Command details:`<br>`emergency: Edits the emergency contact details of the patient identified by the index number used in the last patient listing.`<br>`Parameters: INDEX (must be a positive integer) n/[EMERGENCY CONTACT NAME]p/[EMERGENCY CONTACT NUMBER`<br>`Example: emergency 1 n/Richard Ng p/82943718`
6. Test case: `emergency 1 n/Charlotte Doe p/1`
    * Expected: Error message specific to invalid field<br>`Phone numbers should only contain numbers, and it should be at least 3 digits long`
7. Other incorrect priority commands to try: Any other inputs with either missing or invalid name or phone number fields
    * Expected: Similar to test cases 5 and 6

### Deleting Emergency Contact

Deleting the emergency contact of a patient

Prerequisites: List all patients using the `list` command. At least 1 patient in the list.

1. Test case: `deleteemergency 1`
    * Expected: Emergency contact of patient with index 1 is deleted. Details of the emergency contact deletion are displayed in the status message<br>`Removed emergency contact (Charlotte Doe, 33333333) from John Doe`
2. Test case: Delete emergency contact for the same patient as above
    * Expected: Error message<br>`John Doe does not have a saved emergency contact`
3. Test case: `deleteemergency`
    * Expected: Error message<br>`Invalid command format!`<br>`deleteemergency: Deletes the emergency contact details of the patient identified by the index number used in the last patient listing.`<br>`Parameters: INDEX (must be a positive integer)`<br>`Example: deleteemergency 1`
4. Test case: `deleteemergency x` where `x` a number larger than the size of the patient list
    * Expected: Error message<br>`The patient index provided is invalid`
5Other incorrect priority commands to try: `deleteemergency 0`, `deleteemergency test`
    * Expected: Similar to test case 3

### Adding a Task

Adding a task to a patient while all patients and tasks are being shown

Prerequisites: List all patients using the `list` command. At least 1 patient in the list.

1. Test case: `addtask 1 d/Check blood pressure`
    * Expected: Task "Check blood pressure" is added to the task list for the patient with index 1. Details of the added task are displayed in the status message<br>`New task added: Check blood pressure`
2. Test case: Add the same task as above
    * Expected: Error message<br>`This task already exists in the task list`
3. Test case: `addtask`
    * Expected: Error message<br>`Invalid command format!`<br>`addtask: Adds a task to the task list. Parameters: INDEX d/DESCRIPTION`<br>`Example: addtask 1 d/Buy medication`
4. Test case: `addtask x d/Check blood pressure` where `x` a number larger than the size of the patient list
    * Expected: Error message<br>`The patient index provided is invalid`
5. Other incorrect add task commands to try: `addtask 1 d/`, `addtask 0 d/Check blood pressure`
    * Expected: Similar to test case 3

### Deleting a Task

Deleting a task for a patient while all patients and tasks are being shown

Prerequisites: List all patients using the `list` command. At least 1 patient in the list.

1. Test case: `deletetask 1`
    * Expected: Deletes task with index 1 in the task list. Details of the deleted task are displayed in the status message<br>`Deleted Task: Check blood pressure for John Doe`
2. Test case: `deletetask`
    * Expected: Error message<br>`Invalid command format!`<br>`deletetask: Deletes the task identified by the index number used in the displayed task list.`<br>`Parameters: INDEX (must be a positive integer)`<br>`Example: deletetask 1`
3. Test case: `deletetask x` where `x` a number larger than the size of the task list
    * Expected: Error message<br>`The task index provided is invalid`
4. Other incorrect add task commands to try: `deletetask 0`, `deletetask test`
    * Expected: Similar to test case 2

### Finding a Task

Finding a task for a patient while all patients are being shown

Prerequisites: List all patients using the `list` command. At least 1 patient in the list and a task for patient with index 1.

1. Test case: `findtask 1`
    * Expected: Task(s) for the patient with index 1 is/ are listed in the task list, with a success message<br>`1 tasks listed!`
2. Test case: `findtask`
    * Expected: Error message<br>`Invalid command format!`<br>`findtask: Finds all tasks related to the patient identified by the index number used in the displayed patient list and displays them as a list with index numbers.`<br>`Parameters: PATIENT_INDEX (must be a positive integer)`<br>`Example: findtask 1`
3. Test case: `findtask x` where `x` a number larger than the size of the patient list
    * Expected: Error message<br>`The patient index provided is invalid`
4. Other incorrect add task commands to try: `findtask 0`, `findtask test`
    * Expected: Similar to test case 2

### Marking a Task

Marking a task for a patient while all patients and tasks are being shown

Prerequisites: List all patients using the `list` command. At least 1 patient in the list and an unmarked task for patient with index 1.

1. Test case: `marktask 1`
    * Expected: Task for the patient with index 1 is marked in the task list, with success message<br>`Marked task as complete: Check blood pressure for John Doe`
2. Test case: `marktask`
    * Expected: Error message<br>`Invalid command format!`<br>`marktask: Marks the task identified by the index number used in the displayed task list as complete.`<br>`Parameters: INDEX (must be a positive integer)`<br>`Example: marktask 1`
3. Test case: `marktask x` where `x` a number larger than the size of the task list
    * Expected: Error message<br>`The task index provided is invalid`
4. Other incorrect add task commands to try: `marktask 0`, `marktask test`
    * Expected: Similar to test case 2

### Unmarking a Task

Unmarking a task for a patient while all patients and tasks are being shown

Prerequisites: List all patients using the `list` command. At least 1 patient in the list and a marked task for patient with index 1.

1. Test case: `unmarktask 1`
    * Expected: Task for the patient with index 1 is unmarked in the task list, with success message<br>`Marked task as incomplete: Check blood pressure for John Doe`
2. Test case: `unmarktask`
    * Expected: Error message<br>`Invalid command format!`<br>`unmarktask: Marks the task identified by the index number used in the displayed task list as incomplete.`<br>`Parameters: INDEX (must be a positive integer)`<br>`Example: unmarktask 1`
3. Test case: `unmarktask x` where `x` a number larger than the size of the task list
    * Expected: Error message<br>`The task index provided is invalid`
4. Other incorrect add task commands to try: `unmarktask 0`, `unmarktask test`
    * Expected: Similar to test case 2

### Listing all Incomplete Tasks

Listing all tasks that are incomplete while all patients are being shown

Prerequisites: List all patients using the `list` command. At least 1 patient in the list and one marked and unmarked task each for the patient with index 1.

1. Test case: `listincomplete`
    * Expected: All incomplete tasks in the task list are displayed, with success message<br>`Listed all incomplete tasks`

### Saving data

1. All data is written to file called `addressbook.json`

   1. By default, the data is saved in the file `"addressbook.json"`, found in the `data` folder.
   2. Whenever a command is used to modify the data (e.g., `add`, `delete`, `edit`), the data is saved into the file.

2. Dealing with missing data file

   1. To simulate a missing file, in the same folder as the `NovaCare.jar` file, navigate to the `data` folder and delete the addressbook.json file in the folder. 
   2. Re-launch NovaCare. 
       * Expected: NovaCare will be re-populated with a default list of patients. A new addressbook.json file will be created in the data folder after closing the app or executing a command.

3. Dealing with corrupted data entries

   1. To simulate a corrupted data entry, in the same folder as the `NovaCare.jar` file, navigate to the `data` folder and open the addressbook.json file in the folder. Edit one of the compulsory fields such as phone number to be invalid (e.g. change the phone number to "d"). Save the addressbook.json file.
   2. Re-launch NovaCare.
       * Expected: NovaCare will launch, making sure to skip the invalid entry. The invalid entry will be removed from the addressbook.json file after executing a command.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

Team size: 5

This section outlines proposed improvements to address known feature flaws identified during testing and user feedback. Each planned enhancement targets a specific limitation in the current implementation, aiming to improve data accuracy, user experience, and overall system reliability. Below, we detail the issues and outline precise changes, including examples, to demonstrate how each improvement will better meet user needs.

1. **Implement stricter email validation:**
   * Currently, NovaCare's email validation only requires two alphanumeric characters for the `DOMAIN`, allowing invalid email formats such as `username@example`. This flaw compromises data accuracy and can lead to incorrect contact details, which is critical in a healthcare setting where reliable communication is essential.
   * We plan to enhance the email validation logic to enforce a domain format of `<subdomain>.<top-level domain>`, in which the top-level domain label must be at least 2 characters long. Each domain label must start and end with alphanumeric characters and consist of alphanumeric characters, separated only by hyphens, if any.
   * For example:
     * :white_check_mark: `username1@example.sg`
     * :white_check_mark: `username-2@example.com`
     * :white_check_mark: `username-3@example.com`
     * :white_check_mark: `username4@test.example.org"`
     * :x: `username1@example`<br></br>
2. **Implement more robust phone number validation:**
   * Currently, NovaCare's phone number validation only requires for entries to be numeric, and at least 3 digits long. This means that the system accepts phone number entries that exceed the upper limit of possible phone number lengths, and also disallows entries that try and input country code, i.e. `+6512345678`, since phone numbers must be strictly numeric. This flaw could cause data entry errors, especially for international contacts.
   * As phone numbers are both used in a patient's phone field and in the emergency contact number field, this flaw affects two fields in the NovaCare.
   * We plan to enhance the phone number validation logic to allow the usage of the `+` character only as the first character of the phone number, and to limit the length of the phone number entered.
   * For example:
     * :white_check_mark: `+6512345678`
     * :white_check_mark: `+601123456789`
     * :x: `111+111`
     * :x: `11111111111111111111`<br></br>
3. **Implement more robust name validation:**
    * Currently, names in NovaCare can only contain alphanumeric characters and spaces, limiting inclusivity by excluding names with special characters such as hyphens or slashes. This flaw may reduce inclusivity, which is essential for a healthcare application.
    * As names are both used in a patient's name field and in the emergency contact name field, this flaw affects two fields in the NovaCare.
    * We plan to enhance the name validation logic to allow the usage of the `-` and `/` character, but not as the first or lsat characters of any word in the name.
    * For example:
      * :white_check_mark: `Simon Andy-Fletcher`
      * :white_check_mark: `Ravi s/o Indra`
      * :x: `Timothy /Ng`
      * :x: `-Lim En An`<br></br>
4. **Implement stricter validation for duplicate description prefixes (`d/`) in task commands:**
    * Currently, NovaCare allows multiple `d/` prefixes in the `addtask` command without flagging it as an error, as NovaCare will accept any task descriptions after the last prefix `d/`. This can lead to unintended behavior, where only the last `d/` prefix is considered as the task description, while earlier prefixes and text are ignored. 
    * Although unlikely that users will enter an additional `d/`, we are aware that this behavior can result in data inconsistencies and confusion for users, especially if they unintentionally use multiple description prefixes, thinking all information will be stored.
    * For example:
        * `addtask 1 d/first d/second` will only store "second" as the task description.
        * `addtask 1 d/ p/task` will store "p/task" as the description.
        * `addtask 1 d/ d/first` will store "first" as the description.
    * We plan to enhance the command parsing logic to detect and disallow multiple occurrences of the `d/` prefix within a single command. If duplicate prefixes are detected, the system will throw an error message explaining the issue to the user, helping to ensure command clarity and data integrity.
    * For example:
        * :white_check_mark: `addtask 1 d/task description`
        * :white_check_mark: `addtask 1 d/Doctor appointment at p/2 physiotherapy room`
        * :x: `addtask 1 d/first d/second` (Error: Duplicate `d/` prefix detected)
        * :x: `addtask 1 d/    d/first` (Error: Duplicate `d/` prefix detected)
        * :x: `addtask 1 d/d/first` (Error: Duplicate `d/` prefix detected)<br></br>
5. **Edit displayed error message for priority command when index is invalid:**
    * Currently, when an invalid index (i.e. an index greater than the size of the patient list) is entered with the priority command, The error message `Invalid patient ID. Please enter a valid patient identifier.` is displayed.
    * However, for other commands when an invalid index is used, the error message `The patient index provided is invalid` is displayed instead.
    * We plan to edit the displayed error message for the priority command, to ensure consistency in error messages by using similar terminology throughout the system.<br></br>
6. **Change `deletelevel` command to `resetpriority`:**
    * Currently, the `deletelevel` command is used to reset a patient's given priority level to level 3, which is the default level.
    * As `deletelevel` actually resets a patient's priority level, we plan to change the command to `resetpriority` which is more indicative of the command's usage, improving clarity.<br></br>
7. **Add `sortpriority` feature:**
    * Currently, nurses can use the `priority` command to label each patient with a priority level from 1 to 3, with 1 being of the highest priority.
    * To add on to the priority feature, we plan to implement a `sortpriority` feature, in which calling `sortpriority` will sort the displayed patient list by priority level, with patient's that are priority level 1 at the top of the list.
    * This enhancement will allow nurses to sort their patient list based on highest priority patients, helping them pay more attention to the patients who require more care.<br></br>
8. **Implement an "Edit Emergency Contact" command:**
    * Currently, NovaCare does not provide a way to edit an existing emergency contact directly. Users must delete the emergency contact and add a new one to make any updates, which is inconvenient and time-consuming, especially for minor edits such as updating a phone number or correcting a name spelling.
    * We propose introducing a new `editemergency` command that allows users to edit specific fields of an emergency contact (e.g., name, phone number) without the need to delete and re-add the contact. This enhancement will improve user experience by streamlining contact management.
    * For example:
        * `editemergency 1 n/New Name` - Edits the name of the emergency contact for patient with index 1.
        * `editemergency 2 p/98765432` - Updates the phone number for the emergency contact associated with patient with index 2.
        * `editemergency 3 n/Updated Name p/98765431` - Allows multiple fields to be edited in one command.
    * This new command will check for valid input in each field and ensure that the specified emergency contact exists for the given patient ID before making any changes. Error messages will be displayed for any invalid input or if the specified emergency contact does not exist.
    * For example:
        * :white_check_mark: `editemergency 1 n/John Doe`
        * :white_check_mark: `editemergency 2 n/John Doe p/91234567`
        * :white_check_mark: `editemergency 3 p/91234567`
        * :x: `editemergency 1 n/` (Error: Name cannot be empty)
        * :x: `editemergency 3` (Error: Command requires at least one field to edit)<br></br>
9. **Implement Grammar Adjustment for `list`, `listtask`, `findtask` and `find` Commands:**
    * Currently, NovaCare displays messages that are grammatically incorrect if only a single patient or task is in the result.
        * For `list` or `listtask` commands, the output currently displays "Listed all patients" or "Listed all tasks", regardless of the number of results.
        * For `find` and `findtask` commands, if there is only one result, the output displays "1 patients listed!" or "1 tasks listed!", which is grammatically incorrect.
    * We propose implementing dynamic grammar adjustment based on the count of listed or found patients or tasks, improving the user experience by ensuring correct grammar in the displayed messages.
        * When only one patient or task is listed, the output should display "Listed 1 patient" or "Listed 1 task."
        * When multiple patients or tasks are listed, the output should display "Listed X patients" or "Listed X tasks," where `X` is the count.
    * For example:
        * :white_check_mark: `list` with 1 patient - Output: `Listed 1 patient`
        * :white_check_mark: `listtask` with multiple tasks - Output: `Listed 3 tasks`
        * :white_check_mark: `find John` with 1 result - Output: `Listed 1 patient`
        * :white_check_mark: `findtask 1` with 4 results - Output: `Listed 4 tasks`
        * :x: `list` with 1 patient - Current Output: `Listed all patients` (incorrect grammar)
        * :x: `find` with 1 result - Current Output: `1 patients listed!` (incorrect grammar)
