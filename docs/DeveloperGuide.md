---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* Our codebase was built off the [AB3 code base](https://github.com/se-edu/addressbook-level3)
* **Use of AI Tools:** Below, each team member outlines their specific usage of AI tools in this project.
   - **AGARWAL ISHAN:** 
     - **GitHub Copilot:** Utilized extensively for code completion and generation for attendance tracking commands. Particularly effective when entering class names, as it often auto-filled the correct code for entire classes. GitHub Copilot also assisted in generating Javadocs.
     - **ChatGPT-4:** Consulted for assistance with code implementation, writing tests, and resolving specific programming challenges.

    - **GABRIELLE GIANNA TAN-WININGS:** 
      - **ChatGPT:** Consulted for generation of javadocs. Was used minimally in the generation of some test cases and functional code. Code done with the help of AI is written in comments near said parts.
    - **NICHOLAS TANG BOON KEAT:** 
      - No usage of AI tools.
    - **WILLIAM ALEXANDER DAVID NAYAR:**
        - **ChatGPT:** used for generation of javadocs. It Was used minimally in the generation of functional code. There are a total of 3 instances of using CHATGPT for functional code. 2 instances were for generating regex, and one instance was for simplifying an override equals method. Functional code done with the help of AI is written in comments near said parts.

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

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in `Ui.java`

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the `MainWindow` is specified in `MainWindow.fxml`

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : `Logic.java`

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

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
**API** : `Model.java`

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

### Storage component

**API** : `Storage.java`

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

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

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

**Target user profile**: Teaching assistants (NUS)

Teaching assistants in the National University of Singapore (NUS) who manage multiple students' information and need an
efficient way to track academic progress, contact details, and other relevant data for each student.

**Value proposition**:

Teacher’s Pet streamlines the management of student information by consolidating contact details, grades,
and additional pertinent data into one accessible platform. This efficient tool simplifies tracking student
performance and communication, enhancing organisational efficiency for teaching assistants.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                  | I want to …​                  | So that I can…​                                                                    |
|----------|------------------------------------------|-------------------------------|------------------------------------------------------------------------------------|
| `* * *`  | TA                                       | add students to the list      | handle new students and have a virtual representation of them                      |
| `* * *`  | TA                                       | delete students from the list | remove students who dropped out of the class                                       |
| `* * *`  | TA                                       | modify a student's details    | rectify any mistakes found in the student's details                                |
| `* * *`  | TA                                       | find a student by name or ID  | locate details of the student without having to go through the entire list         |
| `* * *`  | TA                                       | view a student's details      | view the student's details to understand him/her better and contact him/her        |
| `* * *`  | TA                                       | create attendance events      | track attendance for different classes or sessions                                 |
| `* * *`  | TA                                       | mark students' attendance     | record students who are present                                                    |
| `* * *`  | TA                                       | unmark students' attendance   | correct attendance records if mistakes are made                                    |
| `* * *`  | TA                                       | list attendance for an event  | see which students attended a particular event                                     |
| `* * *`  | TA                                       | list all attendance events    | get an overview of all events                                                      |
| `* *`    | TA                                       | tag groups to a student       | see which group(s) a student is in                                                 |
| `* *`    | TA                                       | find students by groups       | get an overview of which student is in the group(s)                                |
| `* *`    | TA                                       | delete attendance events      | get rid of events that are no longer needed or if some duplicate events is created |
| `* *`    | TA                                       | track student's attendance    | award credit appropriately                                                         |
| `*`      | TA with many persons in the address book | sort persons by name          | locate a person easily                                                             |

### Use cases

(For all use cases below, the System is Teacher's Pet (TP) and the Actor is the Teaching Assistant (TA), unless specified otherwise)


**Use Case: UC1 - Add a Student**

**MSS:**

1. TA enters student details
2. TP shows the new student record

    Use case ends.

**Extensions:**

- 1a. TA does not enter enough data for TP to add a student
  - 1a1. TP informs TA of the missing data required

    Use case resumes from step 1.


- 1b. TP detects that data is entered in an incorrect format
  - 1b1. TP informs TA of the correct format

    Use case resumes from step 1.


**Use Case: UC2 - Delete student**  
Preconditions: There is some student data available

**MSS:**

1. TA <ins> views students (UC3 or UC4) <ins/>
2. TP shows all students based on specified view
3. TA deletes students
4. TP shows new list with the student deleted

    Use case ends.

**Extensions:**

- 3a. TA does not enter enough information for TP to delete a student
  - 3a1. TP informs TA of the missing data required
  
    Use case resumes from step 1.


- 3b. TP detects that data is entered in an incorrect format
  - 3b1. TP informs TA of the correct format

    Use case resumes from step 1.


**Use Case: UC3 - List Student**

**MSS:**

1. TA enters the `list` command
2. TP validates the input
3. TP shows a list of all student data

    Use case ends.

**Extensions:**

- 2a. The input validation detects that the input is not valid (e.g. extra argument typed in after `list`)
  - 2a1. TP alerts user of the proper way to type the command.

    Use case resumes from step 1.

- 3a. There is no student data found in the list
    - 3a1. TP shows an empty list of students

      Use case ends.


**Use Case: UC4 - Find Student**

**MSS:**

1. TA enters the `find` command with at least one valid search parameter (e.g., /n <Name>, /id <Student ID>).
2. TP validates the input parameters.
3. TP searches the student database for records matching all the specified criteria.
4. TP displays a list of students matching the search criteria.

    Use case ends.

**Extensions:**

- 1a. TA does not provide any search parameters.
  - 1a1. TP displays an error message.
  
    Use case resumes at step 1.


- 2a. TA provides search parameters in a wrong format
  - 2a1. TP displays an error message and informs TA of the correct format
  
    Use case resumes at step 1.


- 4a. No students match the criteria.
  - 4a1. TP displays a message.
  
    Use case ends.

**Use Case: UC5 - Create Attendance Event**

**MSS:**

1. TA enters the `createattn` command with one or more valid event names.
2. TP validates the event names.
3. TP creates the attendance event(s).
4. TP confirms the creation of the attendance event(s).

   Use case ends.

**Extensions:**

- 1a. TA does not provide any event names.
    - 1a1. TP displays an error message indicating that event names are required.

      Use case resumes from step 1.

- 2a. TA provides event names in an incorrect format or containing invalid characters.
    - 2a1. TP displays an error message indicating the correct format and restrictions.

      Use case resumes from step 1.

- 2b. TA provides duplicate event names within the same command (case-insensitive).
    - 2b1. TP displays an error message indicating duplicate event names.

      Use case resumes from step 1.

- 3a. Any of the event names already exist in the system (case-insensitive).
    - 3a1. TP displays an error message indicating which event(s) already exist.

      Use case ends.

**Use Case: UC6 - Delete Attendance Event**

**MSS:**

1. TA enters the `deleteevent` command with one or more valid event names.
2. TP validates the event names.
3. TP deletes the attendance event(s).
4. TP confirms the deletion of the attendance event(s).

   Use case ends.

**Extensions:**

- 1a. TA does not provide any event names.
    - 1a1. TP displays an error message indicating that event names are required.

      Use case resumes from step 1.

- 2a. TA provides event names in an incorrect format or containing invalid characters.
    - 2a1. TP displays an error message indicating the correct format and restrictions.

      Use case resumes from step 1.

- 2b. TA provides duplicate event names within the same command (case-insensitive).
    - 2b1. TP displays an error message indicating duplicate event names.

      Use case resumes from step 1.

- 3a. Any of the event names do not exist in the system (case-insensitive).
    - 3a1. TP displays an error message indicating which event(s) do not exist.

      Use case ends.


**Use Case: UC7 - List Attendance Events**

**MSS:**

1. TA enters the `listevents` command.
2. TP displays a list of all attendance events.

   Use case ends.

**Extensions:**

- 1a. TA includes additional input after the command word.
    - 1a1. TP displays an error message indicating invalid command format.

      Use case resumes at step 1.

- 2a. There are no attendance events in the system.
    - 2a1. TP displays a message indicating that there are no events to display.

      Use case ends.

**Use Case: UC8 - Mark Attendance**

**MSS:**

1. TA enters the `mark` command with a valid event name and one or more student indices.
2. TP validates the event name and student indices.
3. TP marks the specified students as present for the event.
4. TP confirms that attendance has been marked.

   Use case ends.

**Extensions:**

- 1a. TA does not provide an event name or student indices.
    - 1a1. TP displays an error message indicating that both event name and student indices are required.

      Use case resumes at step 1.

- 2a. Event name does not exist in the system.
    - 2a1. TP displays an error message indicating that the event does not exist.

      Use case ends.

- 2b. TA provides invalid student indices (e.g., non-integer, out of bounds, duplicates).
    - 2b1. TP displays an error message indicating the invalid indices.

      Use case resumes at step 1.

- 2c. TA provides an event name with invalid format or containing invalid characters.
    - 2c1. TP displays an error message indicating the correct format.

      Use case resumes at step 1.

- 3a. Some students are already marked as present for the event.
    - 3a1. TP skips marking those students or informs TA.

      Use case continues.


**Use Case: UC9 - Unmark Attendance**

**MSS:**

1. TA enters the `unmark` command with a valid event name and one or more student indices.
2. TP validates the event name and student indices.
3. TP marks the specified students as absent for the event.
4. TP confirms that attendance has been unmarked.

   Use case ends.

**Extensions:**

- Similar to UC8, with appropriate changes for unmarking.

**Use Case: UC10 - List Attendance**

**MSS:**

1. TA enters the `listattn` command with a valid event name and status (`present` or `absent`).
2. TP validates the event name and status.
3. TP retrieves the list of students matching the specified attendance status for the event.
4. TP displays the list of students.

   Use case ends.

**Extensions:**

- 1a. TA does not provide an event name or status.
    - 1a1. TP displays an error message indicating that both event name and status are required.

      Use case resumes at step 1.

- 2a. Event name does not exist in the system.
    - 2a1. TP displays an error message indicating that the event does not exist.

      Use case ends.

- 2b. Status provided is invalid (not `present` or `absent`).
    - 2b1. TP displays an error message indicating valid statuses.

      Use case resumes at step 1.

- 2c. TA provides an event name with invalid format or containing invalid characters.
    - 2c1. TP displays an error message indicating the correct format.

      Use case resumes at step 1.

- 3a. No students match the specified attendance status.
    - 3a1. TP displays a message indicating no students found.

      Use case ends.

**Use Case: UC11 - Show Groups**

**MSS:**

1. TA enters the `show` command with at least one keyword (e.g., "group 1").
2. TP searches the student database for students with groups matching the keyword(s).
3. TP displays a list of students matching the search criteria.

   Use case ends.

**Extensions:**

- 1a. TA does not provide any keywords.
    - 1a1. TP displays an error message.

      Use case resumes at step 1.


- 2a. No students match the criteria.
    - 2a1. TP displays a message that no students are found.
    - 2a2. TP displays empty list

      Use case ends.

- 2b. Student database is empty
    - 2b1. TP displays a message that no students are found.
    - 2b2. TP displays empty list

      Use case ends.

**Use Case: UC12 - Edit a Student**

**MSS:**

1. TA enters the `edit` command together with the `INDEX` of the student to be edited and the updated student details (e.g. `n/ Mary Tan`)
2. TP updates the student record with the new details (e.g. `Name` is changed to `Mary Tan`)

   Use case ends.

**Extensions:**

- 1a. TP detects that data is entered in an incorrect format (e.g. Student ID not keyed in correctly)
    - 1a1. TP informs TA of the correct format

      Use case resumes from step 1.

- 1b. The `INDEX` keyed in is invalid (eg. out of bounds, invalid character)
    - 1b1. TP informs TA of the correct format for `INDEX`

      Use case resumes from step 1.

- 1c. The database of students is empty
    - 1c1. TP informs TA that an empty list cannot be edited

      Use case ends.

### Non-functional requirements

1. The system should not require users to amend or modify the data file in any way for it to function.
2. The system works on Linux, Mac and Windows.
3. The system should be able to store up to 100 students without a noticeable drop in performance under normal load conditions (i.e. should perform the same as when 10 students are stored in the system)
4. The system shall respond to user queries within 2 seconds under normal load conditions.

### Glossary

- **Teaching Assistant (TA)**: An individual, sometimes also a student, in NUS who is in charge of instructing students for
a particular module, course or program, usually in small classes of less than 30 students.

- **Student**: An individual enrolled in a module, course or program in NUS who is receiving instruction and evaluation
from educators or teaching assistants.

- **Partial Matching**: A search technique where the system matches records that contain parts of the search query, not necessarily the entire query exactly.

---

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file 
   
        Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Adding a student

1. Adding a student with minimal information
   1. Test case: `add n/ Bob id/ A1234567L`
    
        Expected: Details of student added shown in the status message. List updates to show the new student.
   2. Test case: `add n/ Bob`
        
        Expected: Not allowed. Error message on correct format shown in status message.

2. Adding a student with all information
   1. Test case: `add n/ Bob id/ A1234567P nid/ e1234567 m/ Computer Science y/ 2 g/ Group 1`
   
        Expected: Details of student added shown in the status message. List updates to show the new student.

### Editing a student

1. Adding information to a student
   1. Prerequisites: List all persons using the `list` command. List contains a student.
   1. Test case: `edit 1 y/ 5`
   
        Expected: First student on the list now has a field showing 'Year 5'

2. Removing information from a student
   1. Prerequisites: First student in the list has a year field (Use above case to achieve this). List all persons using the `list` command. List contains a student.
   
   2. Test case: `edit 1 y/`
   
        Expected: First student on the list has the year field removed
   
### Deleting a student

1. Deleting a student while all students are being shown

   1. Prerequisites: List all students using the `list` command. Multiple students in the list.

   2. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. 

   3. Test case: `delete 0`<br>
      Expected: No student is deleted. Error details shown in the status message. 

   4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: No student is deleted. Specific error details shown in the status message.

### Leave a comment on a student

1. Leave a comment on a student while all students are being shown

    1. Prerequisites: List all students using the `list` command. Multiple students in the list.

    2. Test case: `comment 1 c/Is always late to class`<br>
       Expected: First contact from the list will show the new comment. 
       Details of the comment and person is shown in the status message.

    3. Test case: `comment 0 c/Is always late to class`<br>
       Expected: No comment is added to any student in the list . Error details shown in the status message.

    4. Other incorrect delete commands to try: `comment 0 c/Is always late to class c/Falls asleep`,
       `comment x c/Is always late to class`, `...` (where x is larger than the list size)<br>
       Expected: No comment is added to any student in the list . Error details shown in the status message.
       Currently multiple comments per student is not supported. However, you are allowed to have /c as part of
       your string for your comment so long as it is not preceded with blank space.

### Showing students in groups

1. Showing students in groups matching the keywords
    1. Prerequisites: There is one person in the list with the group: `group awesome`

    2. Test case: `show group`
       Expected: The person is listed

    3. Test case: `show group awesome`
       Expected: The person is listed
   
    4. Test case: `show gro`
       Expected: The person is listed

2. Showing that there are no students found
    1. Prerequisites: There is one person in the list with the group: `group awesome`

    2. Test case: `show team`
       Expected: No student is listed

### Listing students in groups

1. Showing all students in the list
    1. Prerequisites: There is at least one student in the list

    2. Test case: `list`
       Expected: All students are listed

2. Showing that there are no students found
    1. Prerequisites: There are no students in the list

    2. Test case: `list`
       Expected: No student is listed

### Randomly selecting a student

1. Randomly displaying a student
    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    2. Test case: `random`

       Expected: Success message shown in the status message. List updates to display only the random student.

### Managing Attendance Events

1. **Creating attendance events**

    - Test case: `createattn e/Tutorial 1 e/Lab Session`

      Expected: Attendance events "Tutorial 1" and "Lab Session" are created. Confirmation message shown.

    - Test case: `createattn e/`

      Expected: Error message indicating event name cannot be empty.

2. **Deleting attendance events**

    - Test case: `deleteevent e/Tutorial 1`

      Expected: Attendance event "Tutorial 1" is deleted. Confirmation message shown.

    - Test case: `deleteevent e/Nonexistent Event`

      Expected: Error message indicating event does not exist.

3. **Listing attendance events**

    - Test case: `listevents`

      Expected: Displays a list of all attendance events.

    - Test case: `listevents extra`

      Expected: Error message indicating invalid command format.

### Marking and Unmarking Attendance

1. **Marking attendance for multiple students**

    - Prerequisites: At least five students listed. Event "Lab Session" exists.

    - Test case: `mark e/Lab Session i/1 i/2 i/3`

      Expected: Students at indices 1, 2, and 3 are marked as present for "Lab Session". Confirmation message shown.

    - Test case: `mark e/Lab Session i/1 i/1`

      Expected: Error message indicating duplicate indices.

2. **Unmarking attendance for multiple students**

    - Prerequisites: Students at indices 1 and 2 are marked as present for "Lab Session".

    - Test case: `unmark e/Lab Session i/1 i/2`

      Expected: Students at indices 1 and 2 are marked as absent for "Lab Session". Confirmation message shown.

    - Test case: `unmark e/Lab Session i/a`

      Expected: Error message indicating invalid index.

### Listing Attendance

1. **Listing present students for an event**

    - Test case: `listattn e/Lab Session s/present`

      Expected: Displays a list of students marked as present for "Lab Session".

2. **Listing absent students for an event**

    - Test case: `listattn e/Lab Session s/absent`

      Expected: Displays a list of students marked as absent for "Lab Session".

3. **Invalid status**

    - Test case: `listattn e/Lab Session s/late`

      Expected: Error message indicating valid statuses are 'present' or 'absent'.

### Exit the application

1. Exit the application
    1. Test case: `exit`


   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

       Expected: Application closes
    2. Test case: `exit 2`

       Expected: Error message on correct format shown in status message.
       This loose formatting is not allowed as exit is considered a critical action and requires exact formatting.

### Clear all data

1. Delete all data
    1. Test case: `clear`

       Expected: All data is cleared from the application
    2. Test case: `clear 1`

       Expected: Error message on correct format shown in status message.
       This loose formatting is not allowed as clear is considered a critical action and requires exact formatting.

### Get help

1. Get help
    1. Test case: `help`

       Expected: A success notification and a pop-up with a link to the user guide will appear.
    2. Test case: `help 1`

       Expected: A success notification stating additional parameters supplied have been ignored 
       is shown in status message and a pop-up with a link to the user guide will appear.
       This is allowed as help is not considered a critical action does not require exact formatting.

### Saving data

1. Dealing with missing/corrupted data files

   1. Prerequisites: data file is not empty.
   2. Delete the name in the `name` of the first student (Should be left with `"name" : ""`)
   3. Re-launch the app by double-clicking the jar file or running from the terminal

        Expected: The list is empty.

---

## **Appendix: Effort**

- Commands are made more flexible in general, requiring less strict command syntaxes as compared to AB3. This enhanced the ease of using the app.
- There were significant challenges in adjusting validation for the various commands. What inputs to allow and reject was constantly being discussed with an end result of prioritising user freedom while ensuring inputs remain relatively reasonable.
- There were difficulties agreeing on the UI which went through multiple iterations of tweaking before resulting in the current NUS-themed color palette and which is in line with the app's intended usage.
- Fields in the application were tweaked to suit NUS students' needs (eg. refactoring variables, different input validation)
- New functions added such as attendance taking function, comment
- Implementing the attendance feature required significant modifications to the existing architecture, including updates to the `Model`, `Storage`, and `Logic` components.
- Ensuring data integrity and proper synchronization between student data and attendance records was a challenge.
- Comprehensive testing was conducted to handle various edge cases, such as duplicate events, invalid inputs, and concurrent modifications.
- Documentation was updated extensively to reflect the new features, including user and developer guides.
- New function added such as attendance taking function.
- New function added such as comment.
- New function added such as multiple groups.
- New function added such as find by groups.
- New function which allows to add student by certain optional and required fields related to NUS and students.
- New default function to show offline help list when a command is wrongly typed.

---

## **Appendix: Planned Enhancements**

**Team size: 4**


1. **Enhanced Find Command with Partial Matching and Group Filtering**

    Currently, the `find` command supports exact matches for names and student IDs, which may limit its usability when TAs want to locate students based on partial names. Additionally, `find` does not work well when used after `show`, as it displays students from all groups, rather than only those from the filtered group list. To improve functionality, we plan to enhance the `find` command to support both partial matching and group filtering.

     **Enhancement Goals:**

      - **Partial Matching:** Allow the `find` command to match partial names, enabling TAs to locate students even if they remember only part of a student's name.
      - **Group Filtering:** Enable `find` to respect any active `show` filter, so it only searches within the displayed subset of students. This ensures that if a TA has filtered by group using `show`, the `find` command will only search within that group rather than across all students.

2. **Enhance Grouping Functionality for Attendance Tracking**

    Currently, the `listattn` command lists all students for a given attendance event without segmentation by groups, which may cause confusion when managing multiple classes or sections. We plan to enhance the `listattn` command to allow filtering by groups, so TAs can view attendance specifically for each group within an event.

    **Implementation Considerations:**

    - Integrate the existing `group` field with the `listattn` command to support group-based filtering.
    - Update the UI to display group-specific attendance more clearly.
    - Ensure compatibility with other group and attendance features.

3. **Rename `createattn` Command to `createevent` for Consistency**

    Currently, the command for creating attendance events (`createattn`) is inconsistent with the naming of other event-related commands such as `deleteevent` and `listevents`. To improve usability and consistency, we plan to rename the `createattn` command to `createevent`.

4. **Warning for when edited fields are the same as previous fields**

    Currently, when users use the `edit` feature to update a field that is exactly the same as before (eg. editing name from `Mary Tan` to `Mary Tan`), 
  there is no warning shown. A message could be shown to warn the user in case this action was not intended.

5. **Comments will show a preview and be expandable if they are long to see the full text**

    Currently, when users use the `comment` feature there is no limit on how long comments can be. Thus, the full comment appears in the UI, which instead be expandable component to prevent overloading of information in the interface.

6. **Enhance delete to support deleting of multiple students at once**

    Currently, when users use the `delete` feature they can only delete one student at a time for example `delete 1` deletes the first student if available. In the future `delete 1 2 5` for example will be able to delete students at the index of 1, 2 and 5 on the list shown to improve efficiency.

7.  **Add specific error messages for the use of wrong prefixes in commands**
    
    Currently, a general error message appears when users enter a wrong prefix when using commands (eg. 'c/'). In future iterations, we are planning to add specific error messages for when wrong prefixes are used anywhere in the commands.

    Example Input: `add n/Mary id/A1234567H c/comment` or `edit 1 c/comment`

    Example Output: `Some prefixes entered are not supported in this command: c/`

8. **Add warnings for Year inputs that may be mistakes**

   Currently, there is no warning if a year such as '15' is inputted which is likely a user mistake since it is unlikely a student has been studying in NUS for 15 years. We plan to add in warnings for when the input to Year is >9. (Accounts for PhD students as well). The warning would apply for `add` and `edit` commands and would be appended to the end of the respective success messages on a new line. Note that the commands are allowed to execute, just with an additional warning to alert users to their possible mistake.

    Example Input: `add n/ John id/ A1234567U y/ 10` or `edit 1 y/ 11`

    Example Output: [SUCCESS_MESSAGE] + `Year specified is larger than 9. Please check that this is not a mistake` (Warning appears on a new line)
