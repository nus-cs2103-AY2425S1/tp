---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# TAchy Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

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

* Tech-savvy Tutors/Tuition Teachers

**Value proposition**:

* Provide a fast CLI for tutors to manage and track students' work and submissions.
* Provides an efficient CLI for tutors to plan and manage classes
* Provides some interface for arranging classes


### User stories

Priorities: Very high (must have) - `****`,  High (good to have) - `***`, Medium (should have) - `**`, Low (unlikely to have) -  `*`,

| Priority | As a …​                                    | I want to …​                                           | So that I can…​                                                       |
|----------|--------------------------------------------|--------------------------------------------------------|-----------------------------------------------------------------------|
| `****`   | TA with multiple students                  | add a new student                                      | store important data about my students                                |
| `****`   | TA with multiple students                  | view my students                                       | see who is in my class                                                |
| `****`   | TA                                         | delete a student's information                         | remove students who are no longer part of my class                    |
| `****`   | TA having many classes                     | add assignments                                        | track assignments for students                                        |
| `****`   | TA having many classes                     | add an assignment score to a student                   | grade my students                                                     |
| `****`   | TA having many classes                     | view assignments                                       | have an overview of the assignments                                   |
| `****`   | TA having many classes                     | delete assignments                                     | remove old assignments                                                |
| `****`   | TA                                         | save data locally                                      | access them at a later time                                           |
| `****`   | TA who grades assignments                  | edit status of submitted assignments                   | keep track if a student has submitted an assignment                   |
| `***`    | TA                                         | create remark for individual students                  | not forget any special consideration for certain students             |
| `***`    | TA having many classes                     | add a class                                            | tag students to be part of a class                                    |
| `***`    | TA having many classes                     | delete a class                                         | remove old/expired classes                                            |
| `***`    | TA with multiple classes                   | tag students within the same class                     | sort deliverables and progress by tags                                |
| `***`    | TA                                         | edit class groups                                      | keep an up-to-date list of students in each group                     |
| `***`    | TA                                         | edit assignments                                       | ensure that the assignments are up to date                            |
| `***`    | TA having multiple classes                 | tag an assignment to a class                           | know which classes have which assignments                             |
| `***`    | TA                                         | view my student's contact information                  | know how I can contact them if there are any issues                   |
| `***`    | TA                                         | view student submissions for a class                   | know who has submitted                                                |
| `***`    | TA                                         | view my class timetable                                | know when my classes are                                              |
| `***`    | TA                                         | view the submission deadlines                          | know when to remind my students                                       |
| `***`    | forgetful TA                               | be reminded on deadlines due in 2 days                 | prioritize which assignments to mark                                  |
| `***`    | TA                                         | sort an assignment by score                            | see the best and worst performers                                     |
| `**`     | TA                                         | link scanned PDF files (URL) to a student              | keep track of previous assignments for each student                   |
| `**`     | TA                                         | view all the files (or just file names) submitted by the student before | not waste time finding them somewhere else           |
| `**`     | TA who wants to improve grades             | compare average student performance between sections or classes | know which teaching methods are most effective         |
| `**`     | TA who wants to improve grades             | create a priority list of students who need the most attention | allocate my time effectively                           |
| `*`      | TA                                         | view a student's attendance history                    | mark their attendance scores throughout the whole semester            |
| `*`      | TA                                         | mark attendance and manage participation marks for different students in real time | not need to update from paper every time              |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `TAchy` and the **Actor** is the `TA` (Teaching Assistant),
unless specified otherwise)

**Use case: Add a student**

**MSS**

1. TA requests to add a student
2. TAchy adds the student to the student list

    Use case ends.

**Extensions**

* 1a. The student name is invalid.
    * 1a1. TAchy shows an error message.
  
      Use case ends.

* 1b. The student name already exists.
    * 1b1. TAchy shows a list of students with the same name in order of adding time.
    * 1b2. TA adds a note for the student to distinguish them.

      Use case resumes at step 2.

---

**Use case: Delete a student**

**MSS**

1. TA requests to delete a student
2. TAchy deletes the student from the student list

    Use case ends.

**Extensions**

* 1a. The student name is not in the list.
    * 1a1. TAchy displays a "no students found" message.

      Use case ends.

* 1a. There are multiple students with the name.
    * 1a1. TAchy shows a list of students with the same name with indices.
    * 1a2. TA deletes one of them by index.

      Use case resumes at step 2.

---

**Use case: List students**

**MSS**

1. TA requests to list students.
2. TAchy displays the list of students in the current class.

   Use case ends.

**Extensions**

* 1a. No students found in the class.
    * 1a1. TAchy displays a "no students" message.

      Use case ends.

---

**Use case: Find a student**

**MSS**

1. TA requests to search for a student by name.
2. TAchy displays the student(s) matching the search query.

   Use case ends.

**Extensions**

* 1a. No student matches the query.
    * 1a1. TAchy displays a "no students found" message.

      Use case ends.

---

**Use case: Add assignment**

**MSS**

1. TA requests to add an assignment to a class.
2. TAchy adds the assignment to the class assignment list.

   Use case ends.

**Extensions**

* 1a. The assignment name is invalid.
    * 1a1. TAchy shows an error message.

      Use case ends.

---

**Use case: Delete assignment**

**MSS**

1. TA requests to delete an assignment.
2. TAchy removes the assignment from the class assignment list.

   Use case ends.

**Extensions**

* 1a. No assignment matched the name.
    * 1a1. TAchy displays a "this assignment does not exist" message.

      Use case ends.

---

**Use case: View assignments**

**MSS**

1. TA requests to view the list of assignments.
2. TAchy displays the list of assignments for the class.

   Use case ends.

**Extensions**

* 1a. No assignments are found for the class.
    * 1a1. TAchy displays a "no assignments found" message.

      Use case ends.

---

**Use case: Grade assignment**

**MSS**

1. TA requests to add a grade for a student’s assignment.
2. TAchy records the grade for the student’s assignment.

   Use case ends.

**Extensions**

* 1a. The assignment is not found.
    * 1a1. TAchy shows an error message.

      Use case ends.
  
* 1b. The student is not found.
    * 1b1. TAchy shows an error message.

      Use case ends.

* 1c. The grade is invalid.
    * 1c1. TAchy shows an error message.

      Use case ends.

* 1d. The student has already been graded for the assignment.
    * 1d1. TAchy shows a warning message.
    * 1d2. TAchy asks if the TA wants to overwrite the grade.
    * 1d3. TA confirms the overwrite.
    * 1d4. TAchy records the new grade.

      Use case resumes at step 2.

---

**Use case: View student submissions for an assignment**

**MSS**

1. TA requests to list all assignments.
2. TAchy displays the list of assignments. 
3. TA requests to view submission status for an assignment by index.
4. TAchy displays the list of submissions for the selected assignment.

   Use case ends.

**Extensions**

* 1a. No assignments are found for the class.
    * 1a1. TAchy displays a "no assignments found" message.

      Use case ends.
  
* 3a. The assignment index is invalid.
    * 3a1. TAchy shows an error message.

      Use case ends.
  
* 4a. No submissions are found for the assignment.
    * 4a1. TAchy displays a "no submissions found" message.

      Use case ends.

---

**Use case: Add class**

**MSS**

1. TA requests to add a new class.
2. TAchy adds the class to the course list.

   Use case ends.

**Extensions**

* 1a. The class already exists.
    * 1a1. TAchy shows an error message.

      Use case ends.

---

**Use case: Delete class**

**MSS**

1. TA requests to delete a class.
2. TAchy removes the class from the course list.

   Use case ends.

**Extensions**

* 1a. The class does not exist.
    * 1a1. TAchy shows an error message.

      Use case ends.

**Use case: View class timetable**

**MSS**

1. TA requests to view the class timetable.
2. TAchy displays the class timetable for the current term.

   Use case ends.

---

**Use case: Tag students in a class**

**MSS**

1. TA requests to list all students.
2. TAchy displays the list of students.
3. TA requests to tag a student or group of students by index with the current class.
4. TAchy adds the tags to the students.

   Use case ends.

**Extensions**

* 1a. No students found in the class.
    * 1a1. TAchy displays a "no students" message.

      Use case ends.
    * 
* 3a. The student index is invalid.
    * 3a1. TAchy shows an error message.

      Use case ends.

---

**Use case: Sort students by an assignment score**

**MSS**

1. TA requests to sort students by the score of an assignment.
2. TAchy sorts the assignment results by score in ascending or descending order.

   Use case ends.

**Extensions**
* 1a. The assignment does not exist.
    * 1a1. TAchy shows an error message.

      Use case ends.
* 1b. The assignment has not been graded for all students.
    * 1b1. TAchy shows a warning message.
    * 1b2. TAchy asks if the TA wants to proceed.
    * 1b3. TA confirms the action.

      Use case resumes at step 2.
* 1c. The assignment has not been graded for any student.
    * 1c1. TAchy shows an error message.

      Use case ends.

   
---

**Use case: Link scanned PDF to a student**

**MSS**

1. TA requests to list all documents stored in a file directory.
2. TAchy displays the list of documents.
3. TA requests to link a document to a student by index by name.
4. TAchy records the link.

   Use case ends.

**Extensions**
* 1a. No documents found in the directory.
    * 1a1. TAchy displays a "no documents found" message.

      Use case ends.
* 3a. The student name is not in the list.
    * 3a1. TAchy displays a "no students found" message.

      Use case ends.
* 3b. There are multiple students with the name.
    * 3b1. TAchy shows a list of students with the same name with indices.
    * 3b2. TA links the document to one of them by index.

      Use case resumes at step 4.
---

**Use case: View professor contact information**

**MSS**

1. TA requests to view a professor’s contact information.
2. TAchy displays the professor’s contact details.

   Use case ends.

---

**Use case: View professor’s office hours**

**MSS**

1. TA requests to view the time and location of a professor’s office hours.
2. TAchy displays the professor’s office hours and location.

   Use case ends.

---

**Use case: Compare class performance for an assignment**

**MSS**

1. TA requests to list all assignments.
2. TAchy displays the list of assignments.
3. TA selects one assignment by index.
4. TAchy displays the class in order of performance for the selected assignment.

   Use case ends.

**Extensions**

* 1a. No assignments are found.
    * 1a1. TAchy displays a "no assignments found" message.

      Use case ends.

* 3a. The assignment index is invalid.
    * 3a1. TAchy shows an error message.

      Use case ends.

---

**Use case: Create student priority list**

**MSS**

1. TA requests to list students
2. TA requests to create a list of students who need the most attention by indices.
3. TAchy generates a list of selected students.

   Use case ends.

**Extensions**

* 2a. The student index is invalid.
    * 2a1. TAchy shows an error message.

      Use case ends.

---

**Use case: View or add files posted by professor**

**MSS**

1. TA requests to view or add files posted by the professor for a class.
2. TAchy displays or allows files to be added to the class.

   Use case ends.

---

**Use case: Mark attendance and manage participation**

**MSS**

1. TA requests to list all classes.
2. TAchy displays the list of classes.
3. TA selects one class by index.
4. TAchy displays the list of students in the class.
5. TA requests to mark attendance for a student by index.
6. TAchy records the attendance.

   Use case ends.

**Extensions**

* 1a. No classes are found.
    * 1a1. TAchy displays a "no classes found" message.

      Use case ends.
  
* 3a. The class index is invalid.
    * 3a1. TAchy shows an error message.

      Use case ends.
  
* 5a. The student index is invalid.
    * 5a1. TAchy shows an error message.

      Use case ends.

---

**Use case: Add remark for individual student**

**MSS**

1. TA requests to add a remark for a student.
2. TAchy records the remark in the student’s profile.

   Use case ends.

**Extensions**

* 1a. The student is not found.
    * 1a1. TAchy shows an error message.

      Use case ends.

* 1b. The student already has a remark.
    * 1b1. TAchy shows a warning message.
    * 1b2. TAchy asks if the TA wants to overwrite the remark.
    * 1b3. TA confirms the overwrite.
    * 1b4. TAchy records the new remark.

      Use case resumes at step 2.

*{More to be added}*

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.

- Business/domain rules: Each student must be uniquely identifiable by their student ID, Assignments must have deadlines that  cannot be set on a date that has passed, Each class should not exceed 50 students, Assignments must be submitted by students up to the deadline set by the TA
- Constraints: The system must be backward compatible with data produced by earlier versions of the system, The total project cost should be $0, The project is offered as a free service, TAs are only allowed to store up to 5 GB of data 
- Technical requirements: The system should work on both 32-bit and 64-bit environment, The system should be compatible with Windows, macOS and Linux operating systems.
- Performance requirements: The system should respond to user inputs within five seconds, The system should be able to handle a large number of students, classes, and assignments without degradation in performance, Data retrieval should not take longer than 2 seconds.
- Quality requirements: The system should be usable by a novice who has never used AB3 before, The system should have clear user documentation to guide users through its features, Intuitive error messages will be displayed to the user so that they know what is the correct method of using the system
- Process requirements: The project is expected to adhere to the milestones which are added every week
- Notes about project scope: The system is not required to integrate with third-party systems (e.g. Canvas), The system is not required to generate or print detailed reports of class performance or assignment scores, The system will only support English as the user interface language, The system will not be deployed on the cloud and will only run locally
- Any other noteworthy points: The system must ensure data privary by adhering to relevant data protection regulations, The system should not use any langauge or imagery that may be offensive to students or faculty members from different cultural backgrounds.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **TA** : Abbreviation of Teaching Assistant, a person responsible for assisting instructors in managing courses
* **Class**: A group of students taking the same course assigned to a specific tutorial, sectional, laboratory or recitation which a TA is responsible for.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

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
