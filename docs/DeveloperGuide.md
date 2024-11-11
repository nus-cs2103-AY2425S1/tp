---
layout: page
title: Developer Guide
---

* Table of Contents
  {:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

- [PlantUML](https://plantuml.com) - used for creating diagrams.
- [JavaFX](https://openjfx.io) - framework for UI development.
- [JUnit](https://junit.org/junit5/) - for unit testing framework.


--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

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
classes [`Main`](https://github.com/AY2425S1-CS2103T-F11-3/tp/blob/master/src/main/java/seedu/address/Main.java)
and [`MainApp`](https://github.com/AY2425S1-CS2103T-F11-3/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is
in charge of the app launch and shut down.

* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

<div style="page-break-after: always;"></div>

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

<div style="page-break-after: always;"></div>

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues
the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding
  API `interface` mentioned in the previous point.

<div style="page-break-after: always;"></div>

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using
the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component
through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the
implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

<div style="page-break-after: always;"></div>

### UI component

The **API** of this component is specified
in [`Ui.java`](https://github.com/AY2425S1-CS2103T-F11-3/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts
e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`,
inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the
visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that
are in the `src/main/resources/view` folder. For example, the layout of
the [`MainWindow`](https://github.com/AY2425S1-CS2103T-F11-3/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java)
is specified
in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-F11-3/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.

* listens for changes to `Model` data so that the UI can be updated with the modified data.

* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.

* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

<div style="page-break-after: always;"></div>

### Logic component

**API
** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

<div style="page-break-after: always;"></div>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API
call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates
   a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.

1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which
   is executed by the `LogicManager`.

1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take

   several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

<div style="page-break-after: always;"></div>

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:

* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a
  placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse
  the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as
  a `Command` object.

* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser`
  interface so that they can be treated similarly where possible e.g, during testing.

<div style="page-break-after: always;"></div>

### Model component

**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-F11-3/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).

* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to
  this list so that the UI automatically updates when the data in the list change.

* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.

* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div style="page-break-after: always;"></div>

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>

<div style="page-break-after: always;"></div>

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
<div style="page-break-after: always;"></div>

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

<div style="page-break-after: always;"></div>

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

<div style="page-break-after: always;"></div>

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
<div style="page-break-after: always;"></div>

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

<div style="page-break-after: always;"></div>

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not
pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be
purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern
desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

<div style="page-break-after: always;"></div>

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

<div style="page-break-after: always;"></div>

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

* Administrative staff in small clinics
* has a need for an organized system for managing patient data
* has a need to manage a significant number of patient data entries
* prefers desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**:

Small, private clinics do not have access to advanced administration systems to manage patients as they are expensive
and meant for bigger hospitals.
ClinicBuddy enhances the patient management process for small clinics, creating a platform to track patient information
such as contact information, address and other basic personal data.

<div style="page-break-after: always;"></div>

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`


| Priority | As a …​              | I want to …​                       | So that …​                                                                                                         |
|----------|----------------------|------------------------------------|--------------------------------------------------------------------------------------------------------------------|
| `* * *`  | user                 | add a new patient                  | I can create new patient records.                                                                                  |
| `* * *`  | user                 | edit a record after submitting it  | I can make modifications to erroneous records which may have been submitted after keying a command in too quickly. |
| `* * *`  | user                 | delete a patient's record          | I can remove entries that I no longer need.                                                                        |
| `* * *`  | user                 | search for a patient by name or ID | I can quickly access their information when needed.                                                                |
| `* * *`  | user                 | backup data 	                      | patients data can be restored should the records be corrupted.                                                     |
| `* * *`  | user                 | schedule a patient's appointment   | I can plan out the schedule of the clinic efficiently.                                                             |
| `* * *`  | user                 | find out today's appointments      | the clinic knows which timing is occupied.                                                                         |
| `* * *`  | user                 | cancel a patient’s appointment     | the slot becomes available for other patients.                                                                     |
| `* *`    | user                 | adjust my operating hours          | I do not accidentally create an appointment past operating hours.                                                  |
| `* *`    | user                 | have my blood type stored          | in an emergency, I can get the correct blood transfusion.                                                          |
| `* *`    | user                 | mark appointments as completed     | the clinic has accurate records of missed and completed appointments for future reference.                         |
| `* * `   | first-time user      | get help within the application    | I can understand how to use the commands.                                                                          |
| `* `     | user who is careless | undo commands                      | I can reverse recently executed commands if the command should not have been executed.                             |

<div style="page-break-after: always;"></div>

### Use cases

(For all use cases below, the **System** is `ClinicBuddy` and the **Actor** is the `user`, who is a receptionist, unless specified otherwise)

<div style="page-break-after: always;"></div>

**Use case: Add a new patient**

**MSS**

1. User chooses to add a patient record.
2. ClinicBuddy adds a record.

Use case ends.

Extensions:

- 1a. Patient information contains fields with incorrect format.
    - 1a1. ClinicBuddy provides an error message informing the user of the error.
    - Use case ends.
  
- 2a. The NRIC of the patient already exist.
    - 2a1. ClinicBuddy provides an error message informing the user that the NRIC exists.
    - Use case ends.
  
<div style="page-break-after: always;"></div>

**Use case: Delete a person**

**MSS**

1. User inputs the NRIC of the patient whose record they want to delete to request for ClinicBuddy to delete that
   record.
2. ClinicBuddy deletes the record.

Use case ends.

**Extensions**

* 1a. The NRIC is not of the correct format
    - 1a1. ClinicBuddy shows an error message.
    - Use case ends.
* 2a. The NRIC is not in the records.
    - 2a1. ClinicBuddy provides an error message informing the user of the error.
    - Use case ends.

<div style="page-break-after: always;"></div>

**Use case: Edit a patient record**

**MSS**

1. User inputs the NRIC of the patient and the parameters to update.
2. ClinicBuddy update the record.

Use case ends.

**Extensions**

* 1a. The NRIC is not of the correct format
    - 1a1. ClinicBuddy shows an error message.
    - Use case ends.
* 1b. The NRIC is not in the records.
    - 1b1. ClinicBuddy provides an error message informing the user of the error.
    - Use case ends.
* 1c. The parameter format is invalid.
    - 1c1. ClinicBuddy provides an error message informing the user of the error.
    - Use case ends.

<div style="page-break-after: always;"></div>

**Use case: Search a record**

**MSS**

1. User inputs the NRIC of a patient to search for OR the names of the patients to search for, but not both.
2. ClinicBuddy searches and returns the record(s).

Use case ends.

**Extensions**

* 1a. The NRIC is not of the correct format
    - 1a1. ClinicBuddy shows an error message.
    - Use case ends.
* 1b. No matching patient records are found.
    - 1b1. ClinicBuddy displays a message indicating no matches were found.
    - Use case ends.

<div style="page-break-after: always;"></div>

**Use case: Backup data**

**MSS**

1. User inputs the command with the destination path to store the file.
2. ClinicBuddy creates a backup file in the destination path.

Use case ends.

**Extensions**

* 1a. The destination path is invalid
    - 1a1. ClinicBuddy displays an error message to inform the user.
    - Use case ends.
* 1b. Backup failure due to system issues
    - 1b1. ClinicBuddy displays an error message to inform the user.
    - Use case ends.
* 2a. Backup file with the same name already exists
    - 2a1. ClinicBuddy prompts the user to confirm if they want to overwrite the existing file.
      If Yes: The backup file is overwritten.
      If No: The user is prompted to provide a new destination path or filename.
    - Use case ends.

<div style="page-break-after: always;"></div>

**Use case: Help command**

**MSS**

1. User requests for help.
2. ClinicBuddy provides the user a list of available commands, along with infomation on each command.

Use case ends.

<div style="page-break-after: always;"></div>

**Use case: Undo a command**

**MSS**

1. User requests to undo the last command.
2. ClinicBuddy reverts the system state to before the last command.

Use case ends.

**Extensions**

* 1a. There are no commands to undo.
    - 1a1. ClinicBuddy informs the user that there is no action to undo.
    - Use case ends.


<div style="page-break-after: always;"></div>

**Use case: Search for bookings**

**MSS**

1. User requests for appointments on the specified date to be shown.
2. ClinicBuddy displays a list of patients whose appointment lies on the specified date.

Use case ends.

**Extensions**

* 1a. The date input is invalid
    - 1a1. ClinicBuddy displays an error message informing the user that the date is invalid.
    - Use case ends.


<div style="page-break-after: always;"></div>

### **Non-Functional Requirements**

1. **Compatibility**:
    - Should work on any _mainstream OS_ (e.g., **Windows, macOS, Linux**) as long as it has **Java 17** or above
      installed.

2. **Performance**:
    - Should perform patient data retrieval and UI update operations within **2 seconds** with up to **1,000 patient records** under normal operating conditions.
    - The system must support up to **10,000 patient records** without performance degradation under heavy load.

3. **User Efficiency**:
    - A typical user should be able to perform all primary functions (e.g., add, delete, search, backup) via commands at least **20% faster** than using a graphical interface.

4. **Scalability**:
    - The system should handle up to **10,000 patient records** with retrieval operations completing within **5 seconds**.

5. **Security**:
    - **Sensitive patient information** (e.g., medical records, contact details) must be accessible only by **authorized
      personnel** (e.g., clinic staff, administrators).

6. **Data Processing**:
    - The system must process and display patient information (e.g., patient records) within **2 seconds** of a user
      request under normal operating conditions (up to **1,000 records**).

7. **Usability**:
    - New users, such as clinic staff, should be able to learn basic operations (e.g., create, update, search, delete
      records) within **6 hours of hands-on training**.
    - The system must be accessible to users with basic computer skills, ensuring that text and interface elements are *
      *clear and easy to understand**.

8. **Error Handling**:
    - The system must display specific error messages for invalid inputs and revert to the last saved state on critical errors.

9. **Data Recovery**:
    - In the event of a system crash, patient data should not be lost.
    - Automatic backups should occur every **30 minutes**, and the system should recover data from the latest backup, with data loss **not exceeding 5%** in a system crash.

10. **Maintainability and Extensibility**:
    - The system should be designed in a way that future updates (e.g., adding new features or fixing bugs) can be made
      **easily** and **without extensive rewrites** to existing code.
    - The codebase should follow **industry-standard practices** such as **modular design**, **consistent naming
      conventions**, and **proper documentation** to facilitate maintenance and future feature additions.

<div style="page-break-after: always;"></div>

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **API (Application Programming Interface)**: A set of rules and protocols for building and interacting with software
  applications, specifying how components communicate
* **Parser**: A component that interprets user commands and translates them into Command objects for execution
* **AddressBook**: A digital record that stores contact information for individuals including names, addresses, phone
  numbers, and other relevant details
* **Command**: A directive issued by a user to a software application to perform a specific action or operation
* **CommandResult**: An object that encapsulates the result of executing a command, including success status and any
  output messages

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar clinicbuddy.jar` command  Expected: Shows the GUI with a set of sample patients. The window size may not be optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.


<div style="page-break-after: always;"></div>

### Deleting a patient

1. Deleting a patient while all patients are being shown

    1. Prerequisites: List all patient using the `list` command. Multiple patients in the list.

    1. Test case: `delete 1`<br>
       Expected: First patient is deleted from the list. Details of the deleted patient shown in the status message.
       Timestamp in the status bar is updated.

    1. Test case: `delete 0`<br>
       Expected: No patient is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

<div style="page-break-after: always;"></div>

2. Deleting a patient after filtering the list

    1. Prerequisites: There should be a patient with an `NRIC` of `S1234567Z` in ClinicBuddy, else enter `add n/John Doe a/36 g/M i/S1234567Z p/98765432 e/johnd@example.com h/311, Clementi Ave 2, #02-25 apt/02/10/2024 18:30 t/Patient` to add the patient. Filter the list using the `find S1234567Z`.

    1. Test case: `delete S1234567Z`<br>
       Expected: Patient with NRIC `S1234567Z` is deleted from the list. Details of the deleted patient shown in the status message.
       Timestamp in the status bar is updated.

    1. Test case: `delete S9999999Z`<br>
       Expected: No patient is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size), `delete y`(where y is any other NRIC) <br>
   Expected: Similar to previous.

<div style="page-break-after: always;"></div>

### Updating an appointment

1. Updating an appointment of a patient while all patients are being shown

    1. Prerequisites: List all patients using the `list` command. At least 2 patients are in the list.

    1. Test case: `update 1 apt/10/12/2024 10:30`<br>
       Expected: First patient in the list is assigned an appointment for `10 December 2024 10:30`. Details of the  patient is shown in the status message.
       Timestamp in the status bar is updated.
   
       1. Test case `update 2 apt/10/12/2024 10:35`<br>
          Expected: Second patient in the list will not be given the appointment as the appointment is taken by another. Error details shown in the status message. Status bar remains the same.
       
       1. Test case: `update 1 apt/10/12/2024 10:45`<br>
          Expected: First patient in the list is assigned an appointment for `10 December 2024 10:45`. Details of the  patient is shown in the status message.
          Timestamp in the status bar is updated.

    1. Test case: `Update 0 apt/10/12/2024 11:30`<br>
       Expected: No patient is given the appointment. Error details shown in the status message. Status bar remains the same.
   
    1. Test case: `Update 1 apt/10/12/2024 23:50`<br>
       Expected: Patient 1 is not given the appointment as it is past opening hours. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

<div style="page-break-after: always;"></div>

### Updating Operating Hours

1. Updating Operating Hours 

    1. Prerequisites: At least one patient with an appointment at `18:30` in the list. Use `add n/John Doe a/36 g/M i/S1234567Z p/98765432 e/johnd@example.com h/311, Clementi Ave 2, #02-25 apt/02/10/2024 18:30 t/Patient` to add the patient if there are no patients in the list or `update 1 apt/02/10/2024 18:30` to update the first patient.

    1. Test case 1: `hours o/09:30 c/19:00`
       Expected: Operating Hours changed. Details of the new operating hours shown in the status message. Operating hours display updated to new hours.
   
   1. Test case 2: `hours o/09:30 c/18:00`
      Expected: Operating Hours not updated. Error details shown in the status message. Status bar remains the same.

<div style="page-break-after: always;"></div>

### Deleting Appointments

1. Delete Appointment of a patient

    1. Prerequisites: A patient with an appointment at `2 October 2024 18:30` and an NRIC of `S1234567Z` in the list. Use `add n/John Doe a/36 g/M i/S1234567Z p/98765432 e/johnd@example.com h/311, Clementi Ave 2, #02-25 apt/02/10/2024 18:30 t/Patient` to add the patient if there are no patients in the list or `update 1 apt/02/10/2024 18:30` to update the appointment first patient and `update 1 i/S1234567Z` to update the NRIC.

    1. Test case 1: `deleteappt S1234567Z 02/10/2024 18:30`
       Expected: Appointment deleted from patient. Success message shown. Operating hours display updated to new hours. Status bar remains the same.

    1. Test case 2: `deleteappt S1234567Z 02/10/2024 17:30`
       Expected: Appointment not deleted. Error details shown in the status message. Status bar remains the same.

<div style="page-break-after: always;"></div>

### Find patients by appointment

1. Find patients by appointment

    1. Prerequisites: A patient with an appointment at `2 October 2024 18:30` in the list. Use `add n/John Doe a/36 g/M i/S1234567Z p/98765432 e/johnd@example.com h/311, Clementi Ave 2, #02-25 apt/02/10/2024 18:30 t/Patient` to add the patient if there are no patients in the list or `update 1 apt/02/10/2024 18:30` to update the appointment first patient.

    1. Test case 1: `bookings 02/10/2024`
       Expected: Patients whose appointments are in `02/10/2024` are displayed. Number of persons with bookings shown in status message.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## **Appendix: Planned Enhancements**

Team size : 5

1. **Make invalid command message more specific** : The current error message when one of the fields entered is empty or incorrect is `Invalid Command Format!`, which is too general. We plan to make the error message point out the incorrect field: `Gender Field is missing.` when adding a patient without specifying their gender.

2. **Better Helpbox** : Currently ClinicBuddy's helpbox is too lengthy and difficult to read for users. We intend to improve on this by simplifying the commands and using better formatting such as bolding the command words and padding the command syntax :
![betterHelpBox](images/betterHelpBox.png)

3. **Set appointment length** : Currently appointments are in 15 minute blocks and unable to be changed. We plan to allow the user to set the appointment time of a patient: `update 1 apttime/20` sets the appointment time of the patient in index `1` to `20` minutes. The new appointment must not clash with another existing appointment before updating the appointment duration.

4. **Set multiple appointments** : ClinicBuddy only supports one appointment per patient, which is not suitable for patients that have multiple appointments for their different conditions. We plan to change the Appointment class such that it can accommodate multiple appointments: `update 1 apt/10/10/2024 10:30 10/12/2024 10:30` sets two appointments at 10 October 2024 10:30 and 10 December 2024 10:30 respectively.

5. **Allow multiple appointments in same timings** : ClinicBuddy disallows clash in appointment timings, which is not suitable for clinics with multiple doctors present. We plan to let users set the number of doctors in the clinic and allow up to the number of doctors present for appointments to overlap at any given time: `doctors 2` sets the number of doctors available to `2`and there can only be up to `2` appointments overlap at any given time.

<div style="page-break-after: always;"></div>

6. **Proper NRIC Checksum** : ClinicBuddy validates an NRIC by checking if it follows the basic format as shown in the User Guide. We plan to implement the [NRIC checksum validation algorithm](https://userapps.support.sap.com/sap/support/knowledge/en/2572734) to ensure that the NRIC provided is properly verified.

7. **Allow Text Overflow in Patient Details** : ClinicBuddy does not support text overflow when displaying patient details. Too long `Name`,`Tag`, `Address` and `Email` will cause the respective text to be truncated. We intend to set a hard limit to the length of the above-mentioned fields with text-wrapping to ensure that they are fully displayed.

8. **Sort Patient Details by Chronological Order** : ClinicBuddy currently displays patient details in order of details added. We plan to display patients who have appointments on the day itself to appear first, in chronological order, followed by patients with upcoming appointments in chronological order, and then patients whose appointments have passed: Suppose it is `12 November 2024`. Patient `John` whose appointment is on `12 November 2024 12:30` will appear first in the list, followed by `Jane` whose appointment is on `14 November 2024 13:30` and lastly `Dane` whose appointment was on `10 October 2024 10:30`.

9. **Allow Commands to be case-insensitive** : Commands are currently lower-case sensitive, meaning that `add` is recognised as an `AddCommand` while `Add` is not recognised.

10. **Patient History** : ClinicBuddy only stores the latest appointment for each patient, any updates to appointment will cause it to be overwritten and the original appointment deleted permanently. We plan to track appointment history by making the appointment display in the patient details to have a dropdown list showing their previous appointment.

