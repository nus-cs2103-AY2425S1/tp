---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* This project is based on the [AddressBook Level 3](https://github.com/se-edu/addressbook-level3) project by SE-EDU.
It includes reused and adapted ideas, code, and documentation from the AddressBook Level 3 project.


* Reused/adapted components:
    * UI component structure
    * Command structure in Logic
    * Storage management
    * Project architecture and organization

* Additional Tooling used:
   * ChatGPT for help in understanding design patterns
   * GitHub Copilot for coding assistance
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

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete S1234567A`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.)

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

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

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete S1234567A")` API call as an example.

![Interactions Inside the Logic Component for the `delete S1234567A` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="600" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

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

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete S1234567A` command to delete the person with NRIC S1234567A in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete S1234567A` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

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

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

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
**Product Name:** Murphy's List

**Target user profile**: Administrative assistants for palliative care facilities
* needs to manage a significant number of patient details
* can type fast and prefers CLI to GUI

**Value proposition**: efficient text-based navigation and access to patient data, allowing quick retrieval and logging
of patient information


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​              | I want to …​                                                         | So that I can…​                                                                           |
|----------|----------------------|----------------------------------------------------------------------|-------------------------------------------------------------------------------------------|
| `* * *`  | new user             | view a help page with a list of available commands                   | refer to instructions to understand how to use the app                                    |
| `* * *`  | user                 | add the contact information of a patient                             | keep track of the patient base of the clinic                                              |
| `* * *`  | user                 | delete a patient                                                     | remove their data from the patient list after they leave the facility                     |
| `* * *`  | user                 | display a list of patients and their information                     |                                                                                           |
| `* * *`  | user                 | search for a patient's information using a command/keyword           | access a patient's details quickly without delay                                          |
| `* * *`  | user                 | add an appointment of a patient                                      | view the appointment activity of a patient                                                |
| `* *`    | user                 | edit the information of a patient                                    | update a patient's condition and contact details if there are changes                     |
| `* *`    | user                 | add notes to a patient                                               | be reminded of important updates, observations or instructions related to their care      |
| `* *`    | user                 | log the patient's treatment progress over time                       | understand how a patient is responding to his/her respective treatment meth               |
| `* *`    | user                 | edit the appointment of a patient                                    | reschedule an appointment for a patient easily                                            |
| `* *`    | user                 | view appointments in the form of a schedule                          | easily see all appointments on a specific day                                             |
| `* *`    | user                 | tag patients                                                         | categorise my patients based on keywords/conditions                                       |
| `* *`    | user                 | search for a patient's information even if keyword matches partially | find patients quicker without having to type full details (eg. full name)                 |
| `* *`    | user                 | sort list of patients                                                | view patient's details based on specified criteria                                        |
| `* *`    | user                 | filter patients based on medical condition                           | view patients based on certain conditions or severity                                     |
| `* *`    | user                 | see a popup alert on the day of a patient's appointment              | remind myself and prepare for a patient's appointment if needed                           |
| `* `     | user                 | export a patient's information as a file (eg. PDF, CSV)              | store or share the information externally, especially for offline access                  |
| `*`      | CLI experienced user | have access to command completion features                           | complete tasks faster without typing commands fully                                       |
| `*`      | CLI experienced user | customize command shortcuts                                          | access these commands quickly and more comfortably                                        |
| `*`      | user                 | import contact details from external sources                         | quickly populate the list without manually adding each patient                            |
| `*`      | user                 | archive patient information                                          | have a back up record of their information, even after they are no longer in the facility |
| `*`      | user                 | log when certain changes are made with a timestamp                   | revise my patient history with a reference to a time or date                              |
| `*`      | user                 | set recurring appointment details for patients                       | avoid repetitive tasks                                                                    |


### Use cases

### **Use case: Add a person**

**MSS**

1.  User enters appropriate command keyword to add a person.
2.  User enters the person's details (name, phone number, email, address, etc.) with the appropriate prefixes (n/, p/, e/, etc.).
3.  The system adds the person to the database.
4.  The system shows a success message.

    Use case ends.

**Extensions**

* 2a. The user does not provide all required details.

    * 2a1. The system shows an error message indicating missing fields.

      Use case resumes at step 2.

* 2b. User enters invalid information.

    * 2b1. The system shows an error message indicating invalid input.

      Use case resumes at step 2.

* 2c. A patient with the user input NRIC already exists in the address book.

    * 2c1. The system shows an error message indicating the existence of a duplicate patient.

      Use case resumes at step 2.

---

### **Use case: Edit a person's information**

**MSS**

1. User enters appropriate command keyword to edit a person.
2. User enters the NRIC of the person to be edited.
3. User enters updated details for the person with the appropriate prefixes.
4. The system updates the person's information.
5. The system shows a success message.

    Use case ends.

**Extensions**

* 2a. The given NRIC is invalid.

    * 2a1. The system shows an error message.

      Use case ends.

* 2b. The edited NRIC already exists in the address book.

    * 2b1. The system shows an error message.

      Use case ends.

---

### **Use case: Add appointment information**

**MSS**

1.  User enters appropriate command keyword to add appointment information to a person.
2.  User enters the NRIC of the person for whom the appointment is being added.
3.  User provides the appointment details.
4.  The system adds the appointment information.
5.  The system shows a success message.

    Use case ends.

**Extensions**

* 2a. The given NRIC is invalid.

    * 2a1. The system shows an error message that no person with the given NRIC is found.

      Use case ends.

* 3a. User provides invalid appointment details (e.g., invalid date/time format).

    * 3a1. The system shows an error message indicating invalid input.

      Use case ends.

* 4a. An appointment already exists on patient profile.

    * 4a1. The system overwrites the existing appointment with the new appointment details.

      Use case ends.

---

### **Use case: Add a remark to a patient's profile**

**MSS**

1.  User enters appropriate command keyword to add a remark to a patient's profile.
2.  User enters the NRIC of the patient.
3.  User provides the remark.
4.  The system adds the remark to the patient's profile.
5.  The system shows a success message.

    Use case ends.

**Extensions**

* 2a. The given NRIC is invalid.

    * 2a1. The system shows an error message that no person with the given NRIC is found.

      Use case ends.
  
* 3a. User provides an empty remark.

    * 3a1. The system shows an error message indicating that the remark cannot be empty.

      Use case ends.

* 3b. User provides an invalid remark (containing non-alpha-numeric characters).

    * 3b1. The system shows an error message indicating invalid input.

      Use case ends.

* 4a. The remark already exists on the patient's profile.

    * 4a1. The system overwrites the existing remark with the new remark.

      Use case ends.

---

### **Use case: Search for a patient**

**MSS**

1.  User enters appropriate command keyword to search for a patient.
2.  User enters the name or tag of a patient.
3.  The system displays all patients with a name that matches with the user's input.

    Use case ends.

**Extensions**

* 2a. The given name does not match any patient.

    * 2a1. The system shows an empty list.

      Use case ends.

* 2b. The given tag does not match any patient.

    * 2b1. The system shows an empty list.

      Use case ends.

---

### **Use case: Display a list of patients and information**

**MSS**

1.  User enters appropriate command keyword to request a list of patients.
2.  The system displays a list of patients with relevant information (name, contact details, appointments).
3.  The system shows a success message.

    Use case ends.

**Extensions**

* 2a. The list is empty.

    Use case ends.

---

### **Use case: Display a list of patients in a schedule by appointment dates**

**MSS**

1.  User enters appropriate command keyword to request a list of patients by appointment dates.
2.  The system displays a list of patients with relevant information (name, contact details, appointments) sorted by appointment dates.
3.  The system shows a success message.

    Use case ends.

**Extensions**

* 2a. The list is empty.

    Use case ends.

* 2b. There are no appointments scheduled.

    Use case ends.

---

### **Use case: Sort list of patients by name or appointment**

**MSS**

1.  User enters appropriate command keyword to sort the list of patients.
2.  User specifies the criteria for sorting (e.g., name | appointment).
3.  The system displays a list of patients sorted by the specified criteria.
4.  The system shows a success message.

    Use case ends.

**Extensions**

* 2a. User provides an invalid sorting criteria.
*
    * 2a1. The system shows an error message indicating invalid input.
    
       Use case ends.

### **Use case: Log information to a patient's profile**

**MSS**

1. User enters appropriate command keyword to log information to a patient's profile.
2. User enters the NRIC of the patient.
3. User provides the date, time and information to be logged.
4. The system logs the information to the patient's profile.
5. The system shows a success message.

    Use case ends.

**Extensions**

* 2a. The given NRIC is invalid.

    * 2a1. The system shows an error message that no person with given NRIC is found.

      Use case ends.
  
* 3a. User provides invalid information (e.g., invalid date/time format, empty log message).

    * 3a1. The system shows an error message indicating invalid input.

      Use case ends.

---

### **Use case: Delete a person**

**MSS**

1.  User enters appropriate command keyword to delete a person.
2.  User enters the NRIC of the person to be deleted.
3.  The system deletes the person.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 2b. The given NRIC is invalid.

    * 3a1. AddressBook shows an error message.

      Use case ends.

---

### **Use case: View a patient's full information**

**MSS**

1.  User enters appropriate command keyword to view a patient's full information.
2.  User enters the NRIC of the patient.
3.  The system opens a new window displaying the patient's full information.

    Use case ends.

**Extensions**

* 2a. The given NRIC is invalid.

    * 2a1. The system shows an error message that no person with the given NRIC is found.

      Use case ends.

---

### **Use case: Viewing the help page**

**MSS**

1.  User enters appropriate command keyword to view the help page.
2.  The system opens a new window displaying a list of available commands and their descriptions.

    Use case ends.

---

### **Use case: Appointment pop-up alert on app start-up**

**MSS**

1.  User launches the app.
2.  The system displays a pop-up alert for the day's appointments.

    Use case ends.

**Extensions**

* 2a. There are no appointments scheduled for the day, no pop-up alert.

    Use case ends.

---

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed. The app should hence not depend on any third-party software that is not available on all mainstream OS.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. Data should be stored in a local file in a format that is easy to read and edit manually.
5. The app should be able to recover from common errors (e.g. invalid user input) gracefully, without crashing.
6. The app should avoid very high usage of system resources (CPU, memory) to ensure it can run efficiently even on systems with limited hardware capacity.
7. The app should provide a consistent user interface experience across different screen sizes and resolutions.
8. The application should be highly modular and well-documented to facilitate easy modification and maintenance from new developers.

### Glossary

* **Mainstream OS**: Windows, Linux, MacOS
* **Medical record**: A collection of data about a patient’s health history
* **Description**: A textual summary associated with a appointment or medical record
* **Timestamp**: A record of the date and time an event occurred
* **Tag**: A medical condition or status assigned to a piece of information (e.g., diabetes, G6PD) to describe or categorize it
* **Command Line Interface**: A text-based interface for interacting with a computer program
* **Graphical User Interface**: A visual interface for interacting with a computer program
* **NRIC**: National Registration Identity Card, a unique identifier for Singapore residents

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

Team size: 5

1. Ability to add appointment and remark to a patient's profile using the add command.
2. Support whitespaces in tags. For example, `t/heart disease` should be a single valid tag.
3. Patient should be able to have multiple upcoming appointments and multiple remarks.
4. Ability to edit patient's remark in the edit command.
5. View window should automatically update after editing patient's details or logging a new entry.
6. Additional command shortcuts for `log` and `schedule` commands.
7. Make commands case-insensitive. For example, `Add` should be treated the same as `add`.
8. GUI for error messages should be more user-friendly as currently there is a need to scroll to see the full error message. For example, a pop-up window could be used to display the error message.
9. Triage should indicate level of severity of patient's condition on the GUI for new users.
10. Log success message should indicate that logged entries can be viewed using the`view` command.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   2. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Test case: `delete S1234567A`<br>
      Expected: Existing patient with NRIC S1234567A is deleted from the list. Details of the deleted contact shown in the status message. Status bar updated.

   2. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   3. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is not an NRIC)<br>
      Expected: Similar to previous.

### Saving data

1. Dealing with missing/corrupted data files

    1. Simulating a Missing Data File
       1. Locate the data file used by the application `data/addressbook.json`
       2. Move or delete this file before launching the application<br>
       Expected: The application should automatically create a new data file with default sample data loaded into the created file and app.
    2. Simulating a Corrupted Data File
       1. Open the data file `data/addressbook.json` in a text editor.
       2. Introduce invalid JSON syntax into the file.
       3. Save the corrupted file and launch the application.<br>
       Expected: The application should automatically create a new empty data file with no contacts loaded into the app.
