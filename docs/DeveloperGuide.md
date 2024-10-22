---
layout: default.md
title: "Developer Guide"
pageNav: 3
---

# EduContacts Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org) ([UG](https://se-education.org/addressbook-level3/UserGuide.html), [DG](https://se-education.org/addressbook-level3/DeveloperGuide.html), [GitHub Page](https://github.com/se-edu/addressbook-level3)).

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

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 12345678")` API call as an example.

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

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Role` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Role` object per unique tag, instead of each `Person` needing their own `Role` objects.<br>

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

Step 2. The user executes `delete 12345678` command to delete the person with Student ID of `12345678` in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 12345678` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add id/12345678 …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

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

**Target user profile**:

* Tertiary Teacher/Educator
* has a need to manage a significant number of contacts
* requires a tool to keep communication organised across large groups
* requires support for efficient tracking of academic progress

**Value proposition**: save important time through simplification of student-parent contact management, enhancement in communication tracking and integrated progress reports

<br>

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​            | I want to …​                                   | So that I can…​                                                             |
|----------|--------------------|------------------------------------------------|-----------------------------------------------------------------------------|
| `* * *`  | new teacher        | add a contact                                  | keep track of them                                                          |
| `* * *`  | teacher            | delete a contact                               | remove contacts that I no longer need                                       |
| `* * *`  | teacher            | add a grade to a contact                       | keep track of a student's grades                                            |
| `* * *`  | teacher            | add a student's details                        | keep track of the students under me                                         |
| `* *`    | teacher            | edit a contact                                 | update contact information without having to delete it                      |
| `* *`    | teacher            | search for a contact                           | find the contact I am looking for without having to scroll through the list |
| `* *`    | frequent teacher   | add tags or labels to contacts                 | group the many contacts that are in the application by a commonality        |
| `* *`    | frequent teacher   | filter contacts by labels or tags              | filter out irrelevant contacts                                              |
| `* *`    | frequent teacher   | mass add contacts                              | add multiple contacts without having to do so one by one                    |
| `* *`    | frequent teacher   | mass delete contacts                           | delete multiple contacts without having to do so one by one                 |
| `* *`    | teacher            | add next of kins' contacts                     | contact the relevant individual in case of emergencies                      |
| `*`      | frequent teacher   | do custom sorts for contacts                   | shift relevant contacts near the top of the list of contacts                |
| `*`      | new teacher        | see guided tours and tooltips                  | familiarise myself with the application interface                           |
| `*`      | long-time teacher  | archive contacts                               | reduce clutter in the application without permanently deleting the contact  |
| `*`      | long-time teacher  | refactor tags or labels                        | mass edit tags or labels if necessary                                       |
| `*`      | new teacher        | see the application populated with sample data | see what the application interface looks like                               |
| `*`      | frequent teacher   | add descriptions to contacts                   | be reminded of various traits a particular individual might have            |
| `*`      | frequent teacher   | undo previous action                           | undo a mistake without having to delete or edit any contacts                |
| `*`      | long-time teacher  | export contact data                            | have a backup data file in case anything happens to the application         |
| `*`      | long-time teacher  | import contact data                            | load data from a file to restore lost or missing data                       |
| `*`      | long-time teacher  | access communication history                   | be well-prepared for upcoming meetings                                      |

<br>

### Use cases

(For all use cases below, the **System** is the `EduContacts` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - Add a student**

**MSS**

1. User adds a student to the list of contacts.
2. EduContacts updates the list of contacts.

   Use case ends.

**Extensions**

* 1a. EduContacts detects an error in the given data.

    * 1a1. EduContacts shows an error message.

      Use case ends.

**Use case: UC02 - Find a student**

**Preconditions: The list of contacts is not empty**

**MSS**

1. User provides details of the student.
2. EduContacts displays the student and their details.

   Use case ends.

**Extensions**

* 1a. EduContacts detects an error in the given data.

    * 1a1. EduContacts shows an error message.

      Use case ends.

* 2a. EduContacts is unable to find the student.

  Use case ends.

**Use case: UC03 - Add a grade for a student**

**MSS**
1. User <u>finds the student</u> (UC02) they wish to add a grade for.
2. User adds a grade for the student.
3. EduContacts updates the list of contacts.

   Use case ends.

**Extensions**

* 1a. User is unable to find the student.

  Use case ends.

* 2a. EduContacts detects an error in the given data.

    * 2a1. EduContacts shows an error message.

      Use case ends.

**Use case: UC04 - Delete a student**

**MSS**

1. User <u>finds the student</u> (UC02) they wish to delete from the list.
2. User deletes the student in the list.
3. EduContacts updates the list of contacts.

   Use case ends.

**Extensions**

* 1a. User is unable to find the student.

  Use case ends.

* 2a. EduContacts detects an error in the given data.

    * 2a1. EduContacts shows an error message.

      Use case ends.

**Use case: UC05 - Edit a student's details**

**MSS**

1. User <u>finds the student</u> (UC02) they wish to edit.
2. User edits the details of the student in the list.
3. EduContacts updates the list of contacts.

   Use case ends.

**Extensions**

* 1a. User is unable to find the student.

  Use case ends.

* 2a. EduContacts detects an error in the given data.

    * 2a1. EduContacts shows an error message.

      Use case ends.

**Use case: UC06 - Add a module to a student**

**MSS**

1. User <u>finds the student</u> (UC02) they wish to add a module for.
2. User adds a module to the student in the list.
3. EduContacts updates the list of contacts.

   Use case ends.

**Extensions**

* 1a. User is unable to find the student.

  Use case ends.

* 2a. EduContacts detects an error in the given data.

    * 2a1. EduContacts shows an error message.

      Use case ends.

* 2b. Student already has the module.

    * 2b1. EduContacts shows an error message.

      Use case ends.

**Use case: UC07 - Grade a student**

**MSS**

1. User <u>finds the student</u> (UC02) they wish to grade.
2. User grades a module the student is taking.
3. EduContacts updates the list of contacts.

   Use case ends.

**Extensions**

* 1a. User is unable to find the student.

  Use case ends.

* 2a. EduContacts detects an error in the given data.

    * 2a1. EduContacts shows an error message.

      Use case ends.

* 2b. The module is already graded.

    * 2b1. EduContacts overwrites the old grade with the new grade.

      Use case ends.
  
**Use case: UC08 - Add contacts of next-of-kins of a student**

**MSS**

1. User <u>finds the student</u> (UC02) they wish to add contacts of next-of-kins for.
2. User adds contacts of next-of-kins of the student in the list.
3. EduContacts updates the list of contacts.

   Use case ends.

**Extensions**

* 1a. User is unable to find the student.

  Use case ends.

* 2a. EduContacts detects an error in the given data.

    * 2a1. EduContacts shows an error message.

      Use case ends.

<br>

### Non-Functional Requirements
1.  **Data Requirements**
    - EduContacts must be capable of storing up to 1000 students’ contact details and academic data without significant performance degradation
    - Contact and student data will not change frequently, but updates must be handled seamlessly (e.g., adding, updating, or deleting student details).
2. **Portability**
    - Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
    - EduContacts must be packaged in a single JAR file (or ZIP if additional resources are required). It should not require installation, making it easy to use on any system that supports Java 17.
    - All data must be stored locally in human-readable text files, making it easy to back up or transfer data between systems.
3. **Scalability**
    - The system should be designed with extensibility in mind, allowing new features (e.g., tracking student behavior) to be added without significantly altering the existing codebase.
    - The system should be capable of supporting more than 1000 contacts if needed, with the ability to upgrade to handle larger datasets without performance degradation.
4. **Accessibility**
    - A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
    - The user interface should be accessible to users with disabilities, adhering to standard accessibility guidelines to accommodate screen readers and keyboard navigation.
    - The system should be easy to use for educators, with a simple CLI for fast input and an intuitive GUI for viewing contact information and reports.
5. **Efficiency**
    - The system should respond within 1 second for typical operations (e.g., adding a student, searching for a contact) with up to 1000 records. For larger datasets, response time should not exceed 3 seconds.
    - Operations such as adding, deleting, and modifying student details should not cause noticeable performance degradation, even as the dataset grows.
6. **Documentation**
    - The system should include comprehensive documentation for both command-line and GUI operations, along with access to user support for troubleshooting and guidance.
    - The codebase should be documented for future developers, making it easy to maintain, debug, and extend the system.
7. **Robustness**
    - The system must provide informative error messages for incorrect input or failures (e.g., invalid student ID).
    - The system should not crash unexpectedly, and all errors must be logged for debugging.
8. **Security**
    - A login page should be implemented in order to prevent unauthorised users from signing in to other accounts.
    - Student and parent data should be encrypted to prevent unauthorized access, while still being editable by advanced users.
9. **Testability**
    - The system should be compatible with automated testing frameworks, and all major features should be covered by unit and integration tests.
    - All features should be easy to test manually without requiring complex setups.
10. **Reliability**
    - The application must be reliable and available for use during critical hours, such as during class or exam periods, without crashing or losing data.
11. **Compliance**
    - EduContacts must comply with relevant data protection laws (e.g., GDPR, PDPA) to ensure student and parent information is stored securely and is accessible only by authorized users.
    - The system should provide options to manage and delete student data upon request, to comply with legal requirements.
12. **Interoperability**
    - EduContacts should be able to sync data with external platforms, such as Coursemology or Canvas, through API or manual import/export mechanisms.
    - The system should support exporting and importing data in common file formats (e.g., CSV) for ease of use and integration.
13. **Disaster Recovery**
    - The system should support manual and automatic backups to prevent data loss. In case of a critical failure, the data should be easily recoverable.
    - There should be clear steps for restoring data from a backup after a system failure, ensuring minimal downtime.<br>
14. **Fault Tolerance**
    - All critical errors should be logged, allowing developers to troubleshoot and resolve issues. Minor errors should not crash the system but allow users to continue their tasks.
    - In the event of a system fault, the system should continue operating in a degraded mode without losing functionality.

<br>

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Command-line Interface (CLI)**: A text-based interface used to interact with the software by typing commands. It is preferred by users who can type quickly and need to perform tasks efficiently without relying on graphical elements.
* **Graphical User Interface (GUI)**: The visual component of EduContacts where users can interact with the application through graphical elements (e.g., buttons, forms) rather than text-based commands.
* **JAR File**: A Java Archive (JAR) file that contains the EduContacts application. This single file allows the program to be executed on any machine that supports Java 17, without needing installation.
* **Human-readable File**: A text file that can be easily opened, read, and understood by a person. Typically, such files are plain text and do not require specialized software to interpret or modify.
* **JavaScript Object Notation (JSON)**: A lightweight, text-based format for representing structured data. It is used to exchange data between a server and a web application or store configuration settings.
* **Comma-Separated Values (CSV)**: A plain-text file format used to store tabular data (numbers and text) in a structured way. Each line of the file corresponds to a row in the table, and the values are separated by commas (or other delimiters like semicolons or tabs).
* **Duplicate Entry**: A situation where a student or contact already exists in the address book based on a matching unique identifier (e.g., Student ID). In such cases, EduContacts will reject the duplicate entry and display an error message.
* **Error Message**: A notification provided to the user when an invalid input or action is detected. For example, entering a student ID in the wrong format will prompt an error message such as "Please enter a valid student ID!"

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

<br>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder.

    2. Double-click the jar file.<br> 
       Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

<br>

### Deleting a person

1. Deleting a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list. One person in the list has Student ID `12345678`.

    2. Test case: `delete 12345678`<br>
       Expected: Person with Student ID `12345678` is deleted. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

    3. Test case: `delete 1234 5678`<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

    4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is a Student ID that no student in the list has)<br>
       Expected: Similar to previous.

2. Deleting a person while only one person is being shown

    1. Prerequisites: Filter persons using the `filter` command until only one person remains. Multiple persons in the list. Person that remains has Student ID `12345678`. One person in the list has Student ID `11111111`
   
    2. Test case: `delete 12345678`<br>
       Expected: Person with Student ID `12345678` is deleted. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated. List of persons shown is now blank.
   
    3. Test case: `delete 11111111`<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

3. Deleting a person while no persons are in the list

    1. Prerequisites: Delete all persons in the list using the `clear` command.
   
    2. Test case: `delete 12345678`<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

<br>

### Saving data

1. Dealing with missing data files

    1. To simulate a missing file, in the same folder as the jar file, navigate to the `data` folder and delete the `address.json` file in the folder.
   
    2. Launch EduContacts by double-clicking the jar file.<br>
       Expected: EduContacts is populated by a set of default list of persons. A new `address.json` file will be created in the `data` folder after closing the app or executing a command.

2. Dealing with corrupted data files

    1. To simulate a corrupted file, navigate to the `data` folder and remove a curly brace at the end of the file.

    2. Launch EduContacts by double-clicking the jar file.<br>
       Expected: EduContacts has a blank list of persons. A new `address.json` file will be created in the `data` folder after closing the app or executing a command.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Effort**

_{to work on in the future}_

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

_{to work on in the future}_

