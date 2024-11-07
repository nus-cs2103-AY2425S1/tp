---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# KeyContacts Developer Guide

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

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-T08-2/tp/tree/master/src/main/java/keycontacts/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-T08-2/tp/tree/master/src/main/java/keycontacts/MainApp.java)) is in charge of the app launch and shut down.
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

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-T08-2/tp/tree/master/src/main/java/keycontacts/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-T08-2/tp/tree/master/src/main/java/keycontacts/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-T08-2/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Student` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-T08-2/tp/tree/master/src/main/java/keycontacts/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `KeyContactsParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `KeyContactsParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `KeyContactsParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-T08-2/tp/tree/master/src/main/java/keycontacts/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="600" />


The `Model` component,

* stores the versioned student directory data i.e., all `StudentDirectory` objects (which each represent a single version of the student directory)  
* stores the currently 'selected' `Student` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Student>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-T08-2/tp/tree/master/src/main/java/keycontacts/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both student directory data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `StudentDirectoryStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `keycontacts.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Undo/redo feature

The undo/redo mechanism is facilitated by `VersionedStudentDirectory`. It extends `StudentDirectory` with an undo/redo history, stored internally as an `studentDirectoryStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedStudentDirectory#commit()` — Saves the current student directory state in its history.
* `VersionedStudentDirectory#undo()` — Restores the previous student directory state from its history.
* `VersionedStudentDirectory#redo()` — Restores a previously undone student directory state from its history.

These operations are exposed in the `Model` interface as `Model#commitStudentDirectory()`, `Model#undoStudentDirectory()` and `Model#redoStudentDirectory()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application. The `VersionedStudentDirectory` will be initialized with the initial student directory state, and the `currentStatePointer` pointing to that single student directory state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the student directory. The `delete` calls `Model#commitStudentDirectory()`, causing the modified state of the student directory after the `delete 5` command executes to be saved in the `studentDirectoryStateList`, and the `currentStatePointer` is shifted to the newly inserted student directory state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command calls `Model#commitStudentDirectory()`, causing another modified student directory state to be saved into the `studentDirectoryStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitStudentDirectory()`, so the student directory state will not be saved into the `studentDirectoryStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoStudentDirectory()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous student directory state, and restores the student directory to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial StudentDirectory state, then there are no previous StudentDirectory states to restore. The `undo` command uses `Model#canUndoStudentDirectory()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoStudentDirectory()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the student directory to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `studentDirectoryStateList.size() - 1`, pointing to the latest student directory state, then there are no undone StudentDirectory states to restore. The `redo` command uses `Model#canRedoStudentDirectory()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the student directory, such as `list`, will not call `Model#commitStudentDirectory()`. Thus, the `studentDirectoryStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitStudentDirectory()`. Since the `currentStatePointer` is not pointing at the end of the `studentDirectoryStateList`, all student directory states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Current:** Saves the entire student directory.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative:** Individual command knows how to undo/redo by
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

**Target user profile**:

* Piano teachers with a significant number of piano students
* wants to record information about students
* looking to organize their schedule and teaching activities
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**:
* Manage students' schedules faster than a typical mouse/GUI driven app
* Track income and the sale of learning materials, ensuring they get reimbursed while managing inventory effectively
* Accommodate students who need to reschedule, making for a flexible scheduling tool
* Track students' learning over time, enabling piano teachers to monitor students' grade and progress on piano pieces

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​          | I want to …​                                                | So that I can…​                              |
|----------|------------------|-------------------------------------------------------------|----------------------------------------------|
| `* * *`  | user             | cancel a particular lesson session                          | respond to special circumstances effectively |
| `* * *`  | user             | schedule a make-up lesson for students who missed           | manage lesson rescheduling efficiently       |
| `* * *`  | user             | save a student’s lesson timing                              | know when I will meet them                   |
| `* * *`  | user             | save the data and retrieve them after restarting the app    | ensure my data is persistent                 |
| `* * *`  | user             | view a list of all my students                              | keep track of all my students                |
| `* * *`  | user             | delete a student when they stop taking lessons              | keep my records clean                        |
| `* * *`  | new user         | view the list of commands                                   | know what commands I can run                 |
| `* * *`  | user             | add a piano piece to a student                              | track what piece they are working on         |
| `* * *`  | user             | save a student’s address                                    | know where to travel for tutoring            |
| `* * *`  | user             | see the grade level of a student                            | be more prepared for lessons                 |
| `* * *`  | user             | save a person's contact                                     | contact them easily for tutoring             |
| `* * *`  | user             | undo my last command                                        | revert the effects of a wrong command        |
| `* * *`  | user             | redo a command that I undid                                 | revert the effects of a wrong undo           |
| `* *`    | user             | modify the details of each record                           | change particulars when needed               |
| `* *`    | user             | sort the record by student name, lesson day, contact, etc.  | find specific records easily                 |
| `* *`    | user             | see students scheduled for a particular day                 | know my schedule for the day                 |
| `* *`    | user             | search students based on name, day of lesson, or category   | locate a student efficiently                 |
| `* *`    | user             | export my student data to a CSV file                        | back up my records or share them with others |
| `*`      | user             | track the purchase and sale of learning materials           | manage inventory and ensure reimbursement    |
| `*`      | user             | generate reports on each student’s progress                 | share them with parents or guardians         |
| `*`      | user             | view a timetable for the week                               | prepare my schedule                          |
| `*`      | new user         | receive prompts/suggestions when I type a command wrongly   | get help using the system                    |
| `*`      | experienced user | use shortcuts/aliases for commands                          | perform common tasks faster                  |
| `*`      | user             | group my students together if they are in the same class    | view their information easier                |
| `*`      | user             | track whether each student has paid for the month           | collect my fees on time                      |
| `*`      | user             | keep track of how much each student should pay for lessons  | manage fees easier                           |
| `*`      | user             | write down miscellaneous notes for each student             | recall them before each lesson               |
| `*`      | user             | view a summary of my income for the month                   | track my earnings                            |
| `*`      | user             | track attendance for each student                           | see how consistent they are with lessons     |
| `*`      | user             | track the progress of each student on their assigned pieces | monitor their improvement                    |

### Use cases

(For all use cases below, the **System** is `KeyContacts` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Add a student

**MSS**

1. User enters the command to add a student, including their details.
2. KeyContacts adds the student and notifies the user.

   Use case ends.

**Extensions**

* 1a. KeyContacts detects an error in the entered data.
  * 1a1. KeyContacts requests for the correct data.
  * 1a2. User enters new data.
  * Steps 1a1-1a2 are repeated until the data entered are correct.
  * Use case resumes from step 2.

* 1b. KeyContacts detects an existing student that is a duplicate of the student the user is trying to add.
  * 1b1. KeyContacts alerts the user that the student already exists
  * Use case ends.

---

#### Use case: Edit a student

**MSS**

1. User enters the command to edit a student with new data.
2. KeyContacts edits the student and notifies the user.

   Use case ends.

**Extensions**

* 1a. KeyContacts detects an error in the entered data.
    * 1a1. KeyContacts requests for the correct data.
    * 1a2. User enters new data.
    * Steps 1a1-1a2 are repeated until the data entered are correct.
    * Use case resumes from step 2.

---

#### Use case: Delete a student

**MSS**

1. User enters command to delete a student.
2. KeyContacts deletes the student from the list and notifies the user.

   Use case ends.

**Extensions**

* 1a. KeyContacts detects an error in the entered data.
    * 1a1. KeyContacts requests for the correct data.
    * 1a2. User enters new data.
    * Steps 1a1-1a2 are repeated until the data entered are correct.
    * Use case resumes from step 2.

---

#### Use case: Clear the student directory

**MSS**

1. User enters command to clear the student directory.
2. KeyContacts clears the student directory.

   Use case ends.

---

#### Use case: View a list of all students

**MSS**

1. User enters command to view a list of all students.
2. KeyContacts displays the list of students to the user.

   Use case ends.

**Extensions**

* 1a. KeyContacts detects an error while retrieving the list.
    * 1a1. KeyContacts shows an error message.
    * Use case ends.
    *
---

#### Use case: Find a student

**MSS**

1. User enters the command to find a student, providing one or more search terms.
2. KeyContacts displays the students that fit the search term.
3. User scrolls through the displayed list to find the student they are looking for. 

   Use case ends.

**Extensions**

* 1a. KeyContacts detects an error in the entered data.
    * 1a1. KeyContacts requests for the correct data.
    * 1a2. User enters new data.
    * Steps 1a1-1a2 are repeated until the data entered are correct.
    * Use case resumes from step 2.

---

#### Use case: Sort students

**MSS**

1. User enters the command to sort, providing the sorting details. 
2. KeyContacts displays the students sorted in the given order.

   Use case ends.

**Extensions**

* 1a. KeyContacts detects an error in the entered data.
    * 1a1. KeyContacts requests for the correct data.
    * 1a2. User enters new data.
    * Steps 1a1-1a2 are repeated until the data entered are correct.
    * Use case resumes from step 2.

---

#### Use case: Schedule a student's regular lesson

**MSS**

1. User enters command to schedule a student's regular lesson, including the day of the week, starting time and ending time.
2. KeyContacts schedules the regular lesson at the given day and time period for the student with the associated index and notifies the user.

   Use case ends.

**Extensions**

* 1a. KeyContacts detects an error in the entered data.
  * 1a1. KeyContacts requests for the correct data.
  * 1a2. User enters new data.
  * Steps 1a1-1a2 are repeated until the data entered are correct.
  * Use case resumes from step 2.

---

#### Use case: Assign piano piece to a student

**MSS**

1. User enters command to assign piano pieces to a student.
2. KeyContacts assigns the piano pieces to the student and notifies the user.

   Use case ends.

**Extensions**

* 1a. KeyContacts detects an error in the entered data.
  * 1a1. KeyContacts requests for the correct data.
  * 1a2. User enters new data.
  * Steps 1a1-1a2 are repeated until the data entered are correct.
  * Use case resumes from step 2.
---

#### Use case: Unassign piano pieces from a student

**MSS**

1. User enters command to unassign piano pieces from a student.
2. KeyContacts unassigns the piano pieces from the student and notifies the user.

   Use case ends.

**Extensions**

* 1a. KeyContacts detects an error in the entered data.
    * 1a1. KeyContacts requests for the correct data.
    * 1a2. User enters new data.
    * Steps 1a1-1a2 are repeated until the data entered are correct.
    * Use case resumes from step 2.

---

#### Use case: View the calendar

**MSS**

1. User enters command to view the calendar for a specified week.
2. KeyContacts displays the calendar view for the given week.

   Use case ends.

**Extensions**

* 1a. KeyContacts detects an error in the entered data.
    * 1a1. KeyContacts requests for the correct data.
    * 1a2. User enters new data.
    * Steps 1a1-1a2 are repeated until the data entered are correct.
    * Use case resumes from step 2.

---

#### Use case: Schedule a make-up lesson

**MSS**

1. User enters command to schedule a make-up lesson.
2. KeyContacts schedules the make-up lesson and notifies the user.

   Use case ends.

**Extensions**

* 1a. KeyContacts detects an error in the entered data.
    * 1a1. KeyContacts requests for the correct data.
    * 1a2. User enters new data.
    * Steps 1a1-1a2 are repeated until the data entered are correct.
    * Use case resumes from step 2.
---

#### Use case: Cancel a lesson session

**MSS**

1. User enters command to cancel a lesson session.
2. KeyContacts cancels the lesson session and notifies the user.

   Use case ends.

**Extensions**

* 1a. KeyContacts detects an error in the entered data.
  * 1a1. KeyContacts requests for the correct data.
  * 1a2. User enters new data.
  * Steps 1a1-1a2 are repeated until the data entered are correct.
  * Use case resumes from step 2.

---

#### Use case: Uncancel a lesson session

**MSS**

1. User enters command to uncancel a lesson session.
2. KeyContacts uncancels the lesson session and notifies the user.

   Use case ends.

**Extensions**

* 1a. KeyContacts detects an error in the entered data.
    * 1a1. KeyContacts requests for the correct data.
    * 1a2. User enters new data.
    * Steps 1a1-1a2 are repeated until the data entered are correct.
    * Use case resumes from step 2.

---

#### Use case: View the list of commands

**MSS**

1. User enters command to view the list of commands.
2. KeyContacts displays the dialog box with a link to the user guide.

   Use case ends.

---

#### Use case: Undo the last command

**MSS**

1. User enters command to undo the last command.
2. KeyContacts undoes the last command.

   Use case ends.

* 1a. KeyContacts detects that there are no prior versions to undo to.
    * 1a1. KeyContacts alerts the user that they are at the earliest version.
    * Use case ends.

---

#### Use case: Redo the last undone command

**MSS**

1. User enters command to redo the last undone command.
2. KeyContacts redoes the last undone command.

   Use case ends.

* 1a. KeyContacts detects that there are no undone commands to redo.
    * 1a1. KeyContacts alerts the user that they are at the latest version.
    * Use case ends.

---

#### Use case: Exit

**MSS**

1. User enters command to exit KeyContacts.
2. KeyContacts closes.

   Use case ends.

### Non-Functional Requirements

1.  **Cross-Platform Compatibility**: Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  **Performance**: Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  **Optimised for CLI Users**: A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  **CLI Responsiveness**: Commands executed through the CLI should respond within 1 second under normal load (e.g. with 100 contacts).
5.  **UI Responsiveness**: The UI must remain responsive when updating large datasets, such as when displaying a list of contacts, without causing significant delays in interactions.
6.  **Data Persistence**: Data must persist between sessions, even during unexpected shutdowns and crashes.
7.  **Error Recovery**: In cases of missing or corrupted data files, the app should gracefully handle errors, offering the user the option to restore defaults or attempt recovery without crashing.
8.  **Data Security**: The application should ensure that private contact details are not accidentally exposed or shared without the user’s consent.
9.  **Minimal Learning Curve**: The system should be easy to learn for users familiar with basic command-line applications, providing clear error messages and help documentation for new users.
10. **Scalability**: The system design should allow for future fields or features without major architectural changes.

### Glossary

* **CLI**: Stands for Command-Line Interface, a text-based interface where you can input commands that interact with the program.
* **GUI**: Stands for Graphical User Interface, a user interface where you interact with a program through graphical icons and visual indicators.
* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **PlantUML**: A tool that allows users to create UML diagrams (such as sequence diagrams and class diagrams) using simple text descriptions. The app uses `.puml` files for generating diagrams in the documentation.
* **User preferences**: The GUI settings and student directory data file path, stored in the `preferences.json` data file.

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

### Deleting a student

1. Deleting a student while all students are being shown

   1. Prerequisites: List all students using the `list` command. Multiple students in the list.

   1. Test case: `delete 1`<br>
      Expected: First student is deleted from the list. Details of the deleted student shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No student is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
