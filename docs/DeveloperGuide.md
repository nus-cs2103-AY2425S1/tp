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

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

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

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
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

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic Component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` Component works:

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

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` Component,

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

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

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

### \[Proposed\] Undo/Redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

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

#### Design Considerations:

**Aspect: How Undo & Redo Executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the patient being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

### \[Proposed\] Data Archiving

The data archiving feature allows for the backup and restoration of data by compressing it into a single archive file (e.g., `.zip`). This feature can be useful for storage and data retrieval.

The main steps to implement this feature are:

1. **Collect Data**: Identify and gather all necessary data files.
2. **Compress Data**: Compress these files into an archive.
3. **Store Archive**: Save the archive to a specified location.
4. **Retrieve Data**: Extract the archive when restoration is required.

In future releases, additional features may be implemented.


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


### Use cases

(For all use cases below, the **System** is the `NovaCare` and the **Actor** is the `nurses`, unless specified otherwise)

**Use Case: UC01 - Add Patient**

**MSS**

1. Nurse requests to add patient details to the system.
2. NovaCare adds the patient to the list.

   Use case ends.

**Extensions**

* 1a. The command format is incorrect or one or more compulsory fields (name, phone number, email or address) are omitted.

    * 1a1. NovaCare displays an error message for incorrect command format.<br>Use Case ends.

* 1b. One of the parameters are invalid.

    * 1b1. NovaCare displays an error message specific to the invalid field entry.<br>Use Case ends.

* 1c. A patient with identical name and phone number already exists in the list.

    * 1c1. NovaCare displays an error message specifying the existence of a duplicate patient.<br>Use Case ends.

**Use Case: UC02 - Edit Patient**
**Preconditions: Patient list is not empty**

**MSS**

1. Nurse requests to edit the patient details of a specific patient.
2. NovaCare edits the details of the specified person in the list.

   Use case ends.

**Extensions**

* 1a. The command format is incorrect.

    * 1a1. NovaCare displays an error message for incorrect command format.<br>Use Case ends.

* 1b. One of the parameters are invalid.

    * 1b1. NovaCare displays an error message specific to the invalid field entry.<br>Use Case ends.

* 1c. A patient with identical name and phone number already exists in the list.

    * 1c1. NovaCare displays an error message specifying the existence of a duplicate patient.<br>Use Case ends.

**Use Case: UC03 - Delete Patient**
**Preconditions: Patient list is not empty**

**MSS**

1. Nurse requests to edit the patient details of a specific patient.
2. NovaCare deletes the patient from the patient list and the tasks related to the patient in the task list.

   Use case ends.

**Extensions**

* 1a. The command format is incorrect or the index is missing.

    * 1a1. NovaCare displays an error message for incorrect command format.<br>Use Case ends.

* 1b. The given index is invalid.

    * 1b1. NovaCare displays an error message specific to the invalid index.<br>Use Case ends.

**Use Case: UC04 - List Patients**

**MSS**

1. Nurse requests to list all patient details.
2. NovaCare displays a list of all patient details.

   Use case ends.

**Use Case: UC05 - Find Patient(s) by Name**

**MSS**

1. Nurse requests to find patients whose name contains `keywords`.
2. NovaCare lists patient(s) whose name contains `keywords`.

   Use case ends.

**Extensions**

* 1a. The `keywords` are empty.

    * 1a1. NovaCare displays an error message for incorrect command format.<br>Use Case ends.

**Use case: UC06 - Add Emergency contact**
**Preconditions: Patient list is not empty**

**MSS**

1. Nurse requests to add emergency contact details to a selected patient.
2. NovaCare adds the emergency contact details to the selected patient.

   Use case ends.

**Extensions**

* 1a. The command format is incorrect.

    * 1a1. NovaCare displays an error message for incorrect command format.<br>Use Case ends.

* 1b. The given index is invalid.

    * 1b1. NovaCare displays an error message specific to the invalid index.<br>Use

* 1c. At least one of the parameters (name and/ or phone number) are invalid.

    * 1c1. NovaCare displays an error message specific to the invalid field entry.<br>Use Case ends.

* 1d. NovaCare detects that the patient already has a registered emergency contact.

    * 1d1. NovaCare displays an error message that the patient already has a registered emergency contact.

      Use case ends.

**Use case: UC07 - Delete Emergency contact**
**Preconditions: Patient list is not empty**

**MSS**

1. Nurse requests to delete the emergency contact details of a selected patient.
2. NovaCare deletes the emergency contact details of the selected patient.

   Use case ends.

**Extensions**

* 1a. The command format is incorrect.

    * 1a1. NovaCare displays an error message for incorrect command format.<br>Use Case ends.

* 1b. The given index is invalid.

    * 1b1. NovaCare displays an error message specific to the invalid index.<br>Use

* 1d. NovaCare detects that the patient already does not have a registered emergency contact.

    * 1d1. NovaCare displays an error message that the patient already does not have a registered emergency contact.

      Use case ends.

**Use Case: UC08 - Add Priority**
**Preconditions: Patient list is not empty**

**MSS**

1. Nurse requests to add a priority level to a specified patient.
2. NovaCare updates the given patient details with the entered priority level.

   Use case ends.

**Extensions**

* 1a. The command format is incorrect.

    * 1a1. NovaCare displays an error message for incorrect command format.<br>Use Case ends.

* 1b. The given index is invalid.

    * 1b1. NovaCare displays an error message specific to the invalid index.<br>Use

**Use Case: UC09 - Reset Priority**

**MSS**

1. Nurse requests to reset a priority level of a specified patient.
2. NovaCare resets the given patient details with the default priority level.

   Use case ends.


**Use case: UC10 - Add Task**
**Preconditions: Patient list is not empty**

**MSS**

1. Nurse requests to add a new task to a specific patient.
2. NovaCare adds the task to the task list.

    Use case ends.

Use case ends.

**Extensions**

* 1a. The command format is incorrect or the description field is empty.

    * 1a1. NovaCare displays an error message for incorrect command format.<br>Use Case ends.

* 1b. The given index is invalid.

    * 1b1. NovaCare displays an error message specific to the invalid index.<br>Use

**Use Case: UC11 - Delete Task**
**Preconditions: Patient list and task list is not empty**

**MSS**

1. Nurse requests to delete a new task from the task list.
2. NovaCare deletes the task from the task list.

   Use case ends.

**Extensions**

* 1a. The command format is incorrect.

    * 1a1. NovaCare displays an error message for incorrect command format.<br>Use Case ends.

* 1b. The given index is invalid.

    * 1b1. NovaCare displays an error message specific to the invalid index.<br>Use

**Use Case: UC12 - List Tasks**

**MSS**

1. Nurse requests to list all tasks.
2. NovaCare displays a list of all tasks.

   Use case ends.

**Use Case: UC13 - Find Task(s) by Patient Index**

**MSS**

1. Nurse requests to find tasks for a specified patient's index.
2. NovaCare lists task(s) under patient with the given index.

   Use case ends.

**Extensions**

* 1a. The command format is incorrect.

    * 1a1. NovaCare displays an error message for incorrect command format.<br>Use Case ends.

* 1b. The given index is invalid.

    * 1b1. NovaCare displays an error message specific to the invalid index.<br>Use

**Use Case: UC14 - Mark Task**
**Preconditions: Patient list and task list is not empty**

**MSS**

1. Nurse requests to mark a specified task as complete.
2. NovaCare marks given task to be complete.

   Use case ends.

**Extensions**

* 1a. The command format is incorrect.

    * 1a1. NovaCare displays an error message for incorrect command format.<br>Use Case ends.

* 1b. The given index is invalid.

    * 1b1. NovaCare displays an error message specific to the invalid index.<br>Use

**Use Case: UC15 - Unmark Task**
**Preconditions: Patient list and task list is not empty**

**MSS**

1. Nurse requests to unmark a specified task to be incomplete.
2. NovaCare unmarks given task to be incomplete.

   Use case ends.

**Extensions**

* 1a. The command format is incorrect.

    * 1a1. NovaCare displays an error message for incorrect command format.<br>Use Case ends.

* 1b. The given index is invalid.

    * 1b1. NovaCare displays an error message specific to the invalid index.<br>Use

**Use Case: UC16 - List Incomplete Tasks**

**MSS**

1. Nurse requests to list all incomplete tasks.
2. NovaCare displays a list of all incomplete tasks.

   Use case ends.

**Use Case: UC17 - Clear All Patients and Tasks**

**MSS**

1. Nurse requests to clear all patients and tasks in NovaCare.
2. NovaCare clears all patient and task data.

   Use case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 patients without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The GUI should work well for standard screen resolutions 1920x1080 and higher, and for screen scales 100% and 125%.
5.  The codebase should be modular, allowing easy updates or feature additions without affecting other parts of the system.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Priority level**: A priority level is a value assigned to a patient to indicate the urgency of their medical condition. A priority level of 1 indicates a critical condition, 2 indicates a serious but non-critical condition, while 3 indicates a non-critical condition.
* **Emergency contact**: A person who is preferably close to the patient and is designated to receive information in case of an emergency
* **Task**: A task to be done for a specific patient

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
   3. Run the command `java -jar NovaCare.jar`<br>Expected: The app launches with a set of sample patients. The window size may not be optimal.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.
   2. Re-launch the app by running the command `java -jar NovaCare.jar`.<br>Expected: The most recent window size and location is retained.

3._{ more test cases …​ }_

### Deleting a Patient

1. Deleting a patient while all patients are being shown

   1. Prerequisites: List all patients using the `list` command. Multiple patients in the list.
   2. Test case: `delete 1`<br>Expected: First contact is deleted from the list. Details of the deleted patient shown in the status message. Timestamp in the status bar is updated.
   3. Test case: `delete 0`<br>Expected: No patient is deleted. Error details shown in the status message. Status bar remains the same.
   4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>Expected: Similar to previous.

### Saving data

1. All data is written to file specified in `preferences.json`

   1. By default, the data is saved in the file `"addressBookFilePath" : "data\\addressbook.json"`.
   2. Whenever a command is used to modify the data (e.g., `add`, `delete`, `edit`), the data is saved into the file indicated in `preferences.json`.

### Adding a Task

1. Adding a task to a patient while all patients are being shown

   1. Prerequisites: Patient should already exist in the list.
   2. Test case: `addtask 1 d/Eat Medication`<br>Expected: Task "Eat Medication" is added to the task list for the patient with ID 1. Details of the added task are shown in the status message. Timestamp in the status bar is updated.
   3. Test case: `addtask 0 d/Eat Medication`<br>Expected: No task is added. Error details are shown in the status message indicating an invalid patient ID. Status bar remains the same.
   4. Other incorrect add task commands to try: `addtask`, `addtask x d/`, `addtask 1 d/` (where x is an invalid patient ID or description is missing)<br>Expected: Similar to previous. Error details are shown in the status message explaining the issue with the command input.

### Changing Priority Level

1. Changing a priority level of a patient

   1. Prerequisites: List all patients using the `list` command. Patient should already exist in the list.
   2. Test case: `priority 1 l/2`<br>Expected: Priority level 2 is assigned to the patient with ID 1. Details of the priority update are shown in the status message. Timestamp in the status bar is updated.
   3. Test case: `priority 0 l/2`<br>Expected: No priority is assigned. Error details are shown in the status message indicating an invalid patient ID. Status bar remains the same.
   4. Other incorrect priority commands to try: `priority`, `priority x l/`, `priority 1 l/` (where x is an invalid patient ID or the priority level is missing or invalid)<br>Expected: Similar to previous. Error details are shown in the status message explaining the issue with the command input.

For additional commands and further testing guidelines, refer to Help section in NovaCare.

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
   * As phone numbers are both used in a patient's phone field and in the emergency contact number field, this flaw affects two fields in the addressbook.
   * We plan to enhance the phone number validation logic to allow the usage of the `+` character only as the first character of the phone number, and to limit the length of the phone number entered.
   * For example:
     * :white_check_mark: `+6512345678`
     * :white_check_mark: `+601123456789`
     * :x: `111+111`
     * :x: `11111111111111111111`<br></br>
3. **Implement more robust name validation:**
    * Currently, names in NovaCare can only contain alphanumeric characters and spaces, limiting inclusivity by excluding names with special characters such as hyphens or slashes. This flaw may reduce inclusivity, which is essential for a healthcare application.
    * As names are both used in a patient's name field and in the emergency contact name field, this flaw affects two fields in the addressbook.
    * We plan to enhance the name validation logic to allow the usage of the `-` and `/` character, but not as the first or lsat characters of any word in the name.
    * For example:
      * :white_check_mark: `Simon Andy-Fletcher`
      * :white_check_mark: `Ravi s/o Indra`
      * :x: `Timothy /Ng`
      * :x: `-Lim En An`<br></br>
