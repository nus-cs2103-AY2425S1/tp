---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to
* the original source as well}

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
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


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
Preconditions: Installed the application

**MSS:**

1. TA requests a list of students
2. TP shows a list of all student data

    Use case ends.

**Extensions:**

- 2a. There is no student data found in the list
  - 2a1. TP shows an empty list of students

    Use case ends.


**Use Case: UC4 - Find Student**

**MSS:**

1. TA enters the find command with at least one valid search parameter (e.g., /n <Name>, /id <Student ID>).
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

1. TA enters the `listattendance` command with a valid event name and status (`present` or `absent`).
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
### Non-functional requirements

1. The system shall be capable of handling an increase in users (up to 10,000) without requiring a complete redesign.
2. The system works on Linux, Mac and Windows.
3. The system shall support at least 100 concurrent users without degradation in performance.
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

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. 

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.


### Showing students in groups

1. Showing students in groups matching the keywords
   1. Prerequisites: There is one person in the list with the group: `group awesome`
  
   2. Test case: `show group`
      Expected: The person is listed
      
   3. Test case: `show group awesome`
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

---

## **Appendix: Planned Enhancements**

**Team size: 4**

- **Allow Partial Matching in Find Feature**

  Currently, the `find` command matches students based on exact names or IDs. To improve usability, we plan to enhance the `find` command to allow partial matching of names. This will enable TAs to search for students even if they only remember part of the student's name.

  **Example Usage:**

    - `find n/Alex` would match students with names like "Alex Yeoh", "Alexander Lee", or "Alexis Tan".

  **Implementation Considerations:**

    - Modify the `NameContainsKeywordsPredicate` to check for partial matches.
    - Ensure that the search remains case-insensitive.
    - Update unit tests and documentation accordingly.

- **Track attendance of students individually**

    Currently, the listattn feature shows status of all students for a given event. We plan to enhance this feature to allow TAs to track attendance of individual students across all events.
    