---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/clinicconnectsystem-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/clinicconnectsystem-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
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

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/clinicconnectsystem-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PatientListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/clinicconnectsystem-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/clinicconnectsystem-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Patient` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/clinicconnectsystem-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `ClinicConnectSystemParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a patient).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `ClinicConnectSystemParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `ClinicConnectSystemParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/clinicconnectsystem-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Patient` objects (which are contained in a `UniquePatientList` object).
* stores the currently 'selected' `Patient` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Patient>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `ClinicConnectSystem`, which `Patient` references. This allows `ClinicConnectSystem` to only require one `Tag` object per unique tag, instead of each `Patient` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/clinicconnectsystem-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `ClinicConnectSystemStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedClinicConnectSystem`. It extends `ClinicConnectSystem` with an undo/redo history, stored internally as an `clinicConnectSystemStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedClinicConnectSystem#commit()` — Saves the current address book state in its history.
* `VersionedClinicConnectSystem#undo()` — Restores the previous address book state from its history.
* `VersionedClinicConnectSystem#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitClinicConnectSystem()`, `Model#undoClinicConnectSystem()` and `Model#redoClinicConnectSystem()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedClinicConnectSystem` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th patient in the address book. The `delete` command calls `Model#commitClinicConnectSystem()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `clinicConnectSystemStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new patient. The `add` command also calls `Model#commitClinicConnectSystem()`, causing another modified address book state to be saved into the `clinicConnectSystemStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitClinicConnectSystem()`, so the address book state will not be saved into the `clinicConnectSystemStateList`.

</div>

Step 4. The user now decides that adding the patient was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoClinicConnectSystem()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial ClinicConnectSystem state, then there are no previous ClinicConnectSystem states to restore. The `undo` command uses `Model#canUndoClinicConnectSystem()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redoClinicConnectSystem()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `clinicConnectSystemStateList.size() - 1`, pointing to the latest address book state, then there are no undone ClinicConnectSystem states to restore. The `redo` command uses `Model#canRedoClinicConnectSystem()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitClinicConnectSystem()`, `Model#undoClinicConnectSystem()` or `Model#redoClinicConnectSystem()`. Thus, the `clinicConnectSystemStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitClinicConnectSystem()`. Since the `currentStatePointer` is not pointing at the end of the `clinicConnectSystemStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

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
  * Pros: Will use less memory (e.g. for `delete`, just save the patient being deleted).
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

**Target user profile**:

* works in a private health screening clinic as a clinic assistant
* attends to new and existing patients who come to the clinic for health screening purposes
* has a need to manage a significant number of patient details
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**:
* allow clinic assistants to perform many clinic-related tasks in one place
* contain specific fields unique to healthcare screening clinics which clinic assistants can gain easy access to, such as patient screening dates, patient screening packages, payment dues, insurance providers, insurance processing status
* provide fast access to patient information through smart search and filter capabilities
* automate reminder messages for patient consultations and sending of patients' Test/Scan results
* provide different GUI views for different clinic concerns

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                     | I want to …​                                                                        | So that I can…​                                                         |
| -------- | ------------------------------------------ | ---------------------------------------------------------------------------------- | ---------------------------------------------------------------------- |
| `* * *`  | clinic assistant                           | view all patients' data                                                            | perform administrative tasks                                           |
| `* * *`  | clinic assistant                           | add a new patient                                                                  | register new patients                                                  |
| `* * *`  | clinic assistant                           | delete a patient                                                                   | remove patient details that I no longer need                           |
| `* * *`  | clinic assistant                           | have a user guide to orientate me around the platform                              | understand how to use the platform                                     |
| `* * *`  | clinic assistant                           | be stopped from creating a patient entry for a patient who has registered before   | there are no duplicate records                                         |
| `* * *`  | clinic assistant                           | have a standardized format for recording patient details and records               | variations in recording methods do not hinder administrative processes |
| `* * *`  | clinic assistant                           | get information on a patient's visits                                              | i can contact their insurance company for claims                       |
| `* * *`  | clinic assistant                           | get past existing conditions of a patient                                               | assist in the diagnosis of the patient during screening                |
| `* * *`  | clinic assistant                           | know what package a patient is coming in for                                       | prepare the patients for their tests                                   |
| `* * *`  | clinic assistant                           | know what tests a patient did                                                      | provide patients a tentative date for the release of their results     |
| `* *`    | clinic assistant                           | be able to input notes and information in patient's particulars                    | refer to the notes and settle administrative matters more smoothly     |
| `* *`    | clinic assistant                           | input a priority level for the patients at high risk                               | ensure that they will be attended to properly and in time              |
| `* *`    | clinic assistant                           | view list of patients with matching parts of names as what I typed in search bar   | locate details of patients without having to go through the entire list |
| `* *`    | clinic assistant                           | restrict access to sensitive patient details from part-time clinic staff           | minimize chance of someone else seeing them by accident                |
| `*`      | clinic assistant                           | check the payment balance of the customers                                         | remind them to pay their outstanding bills                             |
| `*`      | clinic assistant                           | see a summary of the patient when i click on the profile                           | save time scrolling through their profiles                             |

### Use cases

(For all use cases below, the **System** is the `ClinicConnect` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Add a patient**

**MSS**

1.  User types command to add patient
2.  ClinicConnect requests relevant information
3.  User keys in relevant information
4.  ClinicConnect adds patient to the system
5.  ClinicConnect shows a success message

    Use case ends.

**Extensions**

* 2a. The given information is invalid.

  * 2a1. ClinicConnect shows an error message
  * 2a2. ClinicConnect requests for the information again

    Steps 2a1-2a2 are repeated until the information entered is valid.

    Use case resumes from step 2.

* 3a. The given patient's NRIC already exists in the system.

    * 3a1. ClinicConnect shows an error message

        Use case ends.

**Use case: Book appointment for patient**

**MSS**

1.  User types command to book appointment time with date, time and patient's NRIC
2.  ClinicConnect creates a new appointment in the system
3.  ClinicConnect shows a success message

    Use case ends.

**Extensions**

* 2a. The given information is invalid.

    * 2a1. ClinicConnect shows an error message

        Use case ends.

* 3a. The given appointment time already exists for the patient.

    * 3a1. ClinicConnect shows an error message

        Use case ends.


**Use case: Delete a patient**

**MSS**

1.  User types command to delete with patient's NRIC
2.  ClinicConnect removes the patient from the system
3.  ClinicConnect shows a success message

    Use case ends.

**Extensions**

* 2a. The given NRIC is invalid.

    * 2a1. ClinicConnect shows an error message

        Use case ends.

* 3a. The given NRIC does not exist in the system.

    * 3a1. ClinicConnect shows an error message

      Use case ends.


### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 patients without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Should be able to perform all of its functions without depending on external APIs.
5.  Should start up in less than 5 seconds on an average device running any _mainstream OS_ with Java `17` or above installed.
6.  Should have automated backups of patient data every 6 hours. Daily full backups should be stored offsite.
7.  In the event of data loss, the system must be able to recover from the latest backup with minimal data loss (less than 30 minutes of data).
8.  A first-time user should be able to navigate and perform basic tasks (e.g. adding a patient, searching for records) within 10 minutes of using the platform without prior training.
9.  The system should maintain comprehensive logs of all user actions, especially concerning patient record access, modifications, and deletions. These logs should be easily accessible for auditing purposes and stored for at least 5 years.

### Glossary

* **API (Application Programming Interface)**: A set of rules and tools that allows one piece of software to communicate with another.
* **Component**: A modular part of a software system that has a well-defined purpose.
* **Command**: A specific instruction given to the system to perform an action.
* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a patient

1. Deleting a patient while all patients are being shown

   1. Prerequisites: List all patients using the `list` command. Multiple patients in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No patient is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
