---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# KeyContacts Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

<div style="page-break-after: always;"></div>

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

This project is based on the AddressBook-Level3 project by the [SE-EDU initiative](https://se-education.org/).
KeyContacts uses the following libraries: [JavaFX](https://openjfx.io/), [Jackson](https://github.com/FasterXML/jackson), [JUnit5](https://github.com/junit-team/junit5).

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

<div style="page-break-after: always;"></div>

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

<div style="page-break-after: always;"></div>

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-T08-2/tp/tree/master/src/main/java/keycontacts/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `StudentListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-T08-2/tp/tree/master/src/main/java/keycontacts/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-T08-2/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Student` object residing in the `Model`.

<div style="page-break-after: always;"></div>

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-T08-2/tp/tree/master/src/main/java/keycontacts/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="500"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `KeyContactsParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a student).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `KeyContactsParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `KeyContactsParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

<div style="page-break-after: always;"></div>

### Model component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-T08-2/tp/tree/master/src/main/java/keycontacts/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="900" />


The `Model` component,

* stores the versioned student directory data i.e., a list of `StudentDirectory` objects (which each represent a single version of the student directory)
* stores the currently 'selected' `Student` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Student>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div style="page-break-after: always;"></div>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-T08-2/tp/tree/master/src/main/java/keycontacts/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="1200" />

The `Storage` component,
* can save both student directory data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `StudentDirectoryStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

<br>
<br>

### Common classes

Classes used by multiple components are in the `keycontacts.commons` package.

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

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

Step 2. The user executes `delete 5` command to delete the 5th student in the student directory. The `delete` calls `Model#commitStudentDirectory()`, causing the modified state of the student directory after the `delete 5` command executes to be saved in the `studentDirectoryStateList`, and the `currentStatePointer` is shifted to the newly inserted student directory state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new student. The `add` command calls `Model#commitStudentDirectory()`, causing another modified student directory state to be saved into the `studentDirectoryStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitStudentDirectory()`, so the student directory state will not be saved into the `studentDirectoryStateList`.

</box>

<div style="page-break-after: always;"></div>

Step 4. The user now decides that adding the student was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoStudentDirectory()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous student directory state, and restores the student directory to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial StudentDirectory state, then there are no previous StudentDirectory states to restore. The `undo` command uses `Model#canUndoStudentDirectory()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" width="500" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoStudentDirectory()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the student directory to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `studentDirectoryStateList.size() - 1`, pointing to the latest student directory state, then there are no undone StudentDirectory states to restore. The `redo` command uses `Model#canRedoStudentDirectory()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

<div style="page-break-after: always;"></div>

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
  * Pros: Will use less memory (e.g. for `delete`, just save the student being deleted).
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

* Piano teachers with a significant number of piano students
* wants to record information about students
* looking to organize their schedule and teaching activities
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**:
* Manage students' information and schedules faster than a typical mouse/GUI driven app
* Accommodate students who need to reschedule, making for a flexible scheduling tool
* Display grade level and piano pieces, enabling piano teachers to prepare for lessons.

<br>
<br>

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​          | I want to …​                                                | So that I can…​                              |
|--------|------------------|-------------------------------------------------------------|----------------------------------------------|
| `* * *` | user             | cancel a particular lesson session                          | account for student availability             |
| `* * *` | user             | schedule a make-up lesson for students who missed           | manage lesson rescheduling efficiently       |
| `* * *` | user             | save a student’s lesson timing                              | know when I will meet them                   |
| `* * *` | user             | save the data and retrieve them after restarting the app    | ensure my data is persistent                 |
| `* * *` | user             | view a list of all my students                              | keep track of all my students                |
| `* * *` | user             | delete a student when they stop taking lessons              | keep my records clean                        |
| `* * *` | new user         | view the list of commands                                   | know what commands I can run                 |
| `* * *` | user             | add a piano piece to a student                              | track what piece they are working on         |
| `* * *` | user             | save a student’s address                                    | know where to travel for tutoring            |
| `* * *` | user             | see the grade level of a student                            | tailor the lesson to their proficiency       |
| `* * *` | user             | save a student's contact                                    | easily contact them for tutoring             |
| `* * *` | user             | undo my last command                                        | revert the effects of a wrong command        |
| `* * *` | user             | redo a command that I undid                                 | revert the effects of a wrong undo           |
| `* *`  | user             | modify the details of each record                           | change particulars when needed               |
| `* *`  | user             | sort the students by personal particulars                   | find specific records easily                 |
| `* *`  | user             | see students scheduled for a particular day                 | know my schedule for the day                 |
| `* *`  | user             | search students based on personal particulars               | locate a student efficiently                 |
| `*`    | user             | export my student data to a CSV file                        | back up my records or share them with others |
| `*`    | user             | track the purchase and sale of learning materials           | manage inventory and ensure reimbursement    |
| `*`    | user             | generate reports on each student’s progress                 | share them with parents or guardians         |
| `*`    | user             | view a timetable for the week                               | prepare my schedule                          |
| `*`    | new user         | receive prompts/suggestions when I type a command wrongly   | get help using the system                    |
| `*`    | experienced user | use shortcuts/aliases for commands                          | perform common tasks faster                  |
| `*`    | user             | group my students together if they are in the same class    | view their information together              |
| `*`    | user             | track whether each student has paid for the month           | collect my fees on time                      |
| `*`    | user             | keep track of how much each student should pay for lessons  | manage fees easier                           |
| `*`    | user             | write down miscellaneous notes for each student             | recall them before each lesson               |
| `*`    | user             | view a summary of my income for the month                   | track my earnings                            |
| `*`    | user             | track attendance for each student                           | see how consistent they are with lessons     |
| `*`    | user             | track the progress of each student on their assigned pieces | monitor their improvement                    |

<br>
<br>

### Use cases

(For all use cases below, the **System** is `KeyContacts` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Add a student (UC01)

**MSS**

1. User requests to add a student, including their details.
2. KeyContacts adds the student and notifies the user.

   Use case ends.

**Extensions**

* 1a. KeyContacts detects an error in the entered data.
  * 1a1. KeyContacts requests for the correct data.
  * 1a2. User enters new data.
  * Steps 1a1-1a2 are repeated until the data entered are correct.
  * Use case resumes from step 2.

<br>

* 1b. KeyContacts detects an existing student that is a duplicate of the student the user is trying to add.
  * 1b1. KeyContacts alerts the user that the student already exists.
  * Use case ends.

---

#### Use case: Edit a student (UC02)

**MSS**

1. User requests to edit a student with new data.
2. KeyContacts edits the student and notifies the user.

   Use case ends.

**Extensions**

* 1a. KeyContacts detects an error in the entered data.
    * 1a1. KeyContacts requests for the correct data.
    * 1a2. User enters new data.
    * Steps 1a1-1a2 are repeated until the data entered are correct.
    * Use case resumes from step 2.

---

#### Use case: Delete a student (UC03)

**MSS**

1. User requests to delete a student.
2. KeyContacts deletes the student from the list and notifies the user.

   Use case ends.

**Extensions**

* 1a. KeyContacts detects an error in the entered data.
    * 1a1. KeyContacts requests for the correct data.
    * 1a2. User enters new data.
    * Steps 1a1-1a2 are repeated until the data entered are correct.
    * Use case resumes from step 2.

---

#### Use case: Clear the student directory (UC04)

**MSS**

1. User requests clear the student directory.
2. KeyContacts clears the student directory.

   Use case ends.

---

#### Use case: View a list of all students (UC05)

**MSS**

1. User requests to view a list of all students.
2. KeyContacts displays the list of students to the user.

   Use case ends.

---

<div style="page-break-after: always;"></div>

#### Use case: Find a student (UC06)

**MSS**

1. User requests to find a student, providing one or more search terms.
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

#### Use case: Sort students (UC07)

**MSS**

1. User requests to sort the students, providing the sorting details. 
2. KeyContacts displays the students sorted in the given order.

   Use case ends.

**Extensions**

* 1a. KeyContacts detects an error in the entered data.
    * 1a1. KeyContacts requests for the correct data.
    * 1a2. User enters new data.
    * Steps 1a1-1a2 are repeated until the data entered are correct.
    * Use case resumes from step 2.

---

#### Use case: Schedule a student's regular lesson (UC08)

**MSS**

1. User requests to schedule a student's regular lesson, including the day of the week, starting time and ending time.
2. KeyContacts schedules the regular lesson at the given day and time period for the student with the associated index and notifies the user.

   Use case ends.

**Extensions**

* 1a. KeyContacts detects an error in the entered data.
  * 1a1. KeyContacts requests for the correct data.
  * 1a2. User enters new data.
  * Steps 1a1-1a2 are repeated until the data entered are correct.
  * Use case resumes from step 2.

<br>

* 1b. KeyContacts detects a lesson that clashes with the regular lesson.
    * 1b1. KeyContacts informs the user of the clash.
    * 1b2. User enters new data.
    * Steps 1b1-1b2 are repeated until a clash is no longer detected.
    * Use case resumes from step 2.

---

#### Use case: Assign piano piece to a student (UC09)

**MSS**

1. User requests to assign piano pieces to a student.
2. KeyContacts assigns the piano pieces to the student and notifies the user.

   Use case ends.

**Extensions**

* 1a. KeyContacts detects an error in the entered data.
  * 1a1. KeyContacts requests for the correct data.
  * 1a2. User enters new data.
  * Steps 1a1-1a2 are repeated until the data entered are correct.
  * Use case resumes from step 2.
---

#### Use case: Unassign piano pieces from a student (UC10)

**MSS**

1. User requests to unassign piano pieces from a student.
2. KeyContacts unassigns the piano pieces from the student and notifies the user.

   Use case ends.

**Extensions**

* 1a. KeyContacts detects an error in the entered data.
    * 1a1. KeyContacts requests for the correct data.
    * 1a2. User enters a new day, start time and or end time.
    * Steps 1a1-1a2 are repeated until the data entered are correct.
    * Use case resumes from step 2.

---

#### Use case: View the calendar (UC11)

**MSS**

1. User requests to view the calendar for a specified week.
2. KeyContacts displays the calendar view for the given week.

   Use case ends.

**Extensions**

* 1a. KeyContacts detects an error in the entered data.
    * 1a1. KeyContacts requests for the correct data.
    * 1a2. User enters new data.
    * Steps 1a1-1a2 are repeated until the data entered are correct.
    * Use case resumes from step 2.

---

<div style="page-break-after: always;"></div>

#### Use case: Schedule a make-up lesson (UC12)

**MSS**

1. User requests to schedule a make-up lesson.
2. KeyContacts schedules the make-up lesson and notifies the user.

   Use case ends.

**Extensions**

* 1a. KeyContacts detects an error in the entered data.
    * 1a1. KeyContacts requests for the correct data.
    * 1a2. User enters new data.
    * Steps 1a1-1a2 are repeated until the data entered are correct.
    * Use case resumes from step 2.

<br>

* 1b. KeyContacts detects a clash with the make-up lesson.
  * 1b1. KeyContacts informs the user of the clash.
  * 1b2. User enters a new date and or time.
  * Steps 1b1-1b2 are repeated until no clashes are detected.
  * Use case resumes from step 2.

---

#### Use case: Cancel a lesson session (UC13)

**MSS**

1. User requests to cancel a lesson session.
2. KeyContacts cancels the lesson session and notifies the user.

   Use case ends.

**Extensions**

* 1a. KeyContacts detects an error in the entered data.
  * 1a1. KeyContacts requests for the correct data.
  * 1a2. User enters new data.
  * Steps 1a1-1a2 are repeated until the data entered are correct.
  * Use case resumes from step 2.

---

<div style="page-break-after: always;"></div>

#### Use case: Uncancel a lesson session (UC14)

**MSS**

1. User requests to uncancel a lesson session.
2. KeyContacts uncancels the lesson session and notifies the user.

   Use case ends.

**Extensions**

* 1a. KeyContacts detects an error in the entered data.
    * 1a1. KeyContacts requests for the correct data.
    * 1a2. User enters new data.
    * Steps 1a1-1a2 are repeated until the data entered are correct.
    * Use case resumes from step 2.

<br>

* 1b. KeyContacts detects a make-up lesson that clashes with the uncancelled lesson.
    * 1b1. KeyContacts informs the user of the clash.
    * 1b2. ==!!User requests to cancel the clashing make-up lesson (UC13)!!==.
    * Use case resumes from step 1.

---

#### Use case: View the list of commands (UC15)

**MSS**

1. User requests to view the list of commands.
2. KeyContacts displays the dialog box with a link to the user guide.

   Use case ends.

---

#### Use case: Undo the last command (UC16)

**MSS**

1. User requests to undo the last command.
2. KeyContacts undoes the last command.

   Use case ends.

* 1a. KeyContacts detects that there are no prior versions to undo to.
    * 1a1. KeyContacts alerts the user that they are at the earliest version.
    * Use case ends.

---

#### Use case: Redo the last undone command (UC17)

**MSS**

1. User requests to redo the last undone command.
2. KeyContacts redoes the last undone command.

   Use case ends.

* 1a. KeyContacts detects that there are no undone commands to redo.
    * 1a1. KeyContacts alerts the user that they are at the latest version.
    * Use case ends.

---

#### Use case: Exit (UC18)

**MSS**

1. User requests to exit KeyContacts.
2. KeyContacts closes.

   Use case ends.

---

<br>
<br>

### Non-Functional Requirements

1.  **Cross-Platform Compatibility**: Should work on any _Mainstream OS_ as long as it has Java `17` or above installed.
2.  **Optimised for CLI Users**: A user with above average typing speed (50 words per minute) for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
3.  **CLI Responsiveness**: Commands executed through the CLI should respond within 1 second under normal load (e.g. with 100 contacts).
4.  **UI Responsiveness**: The UI must remain responsive when updating large datasets, such as when displaying a list of contacts, without causing delays of more than 1 second in interactions.
5.  **Data Persistence**: Data must persist between sessions, even during unexpected shutdowns and crashes.
6.  **Error Recovery**: In cases of missing or corrupted data files, the app should seamlessly handle errors without crashing.
7.  **Data Security**: The application should ensure that private contact details are not accidentally exposed or shared without the user’s consent.
8.  **Minimal Learning Curve**: The system should be easy to learn for users familiar with basic command-line applications, providing clear error messages and help documentation for new users.
9.  **Scalability**: The system design should allow for future fields or features without major architectural changes.

<br>
<br>

### Glossary

* **CLI**: Stands for Command-Line Interface, a text-based interface where you can input commands that interact with the program.
* **GUI**: Stands for Graphical User Interface, a user interface where you interact with a program through graphical icons and visual indicators.
* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **PlantUML**: A tool that allows users to create UML diagrams (such as sequence diagrams and class diagrams) using simple text descriptions. The app uses `.puml` files for generating diagrams in the documentation.
* **User preferences**: The GUI settings and student directory data file path, stored in the `preferences.json` data file.

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file <br>
      **Expected**: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       **Expected**: The most recent window size and location is retained.

### Adding a student
1. Adding a student with basic details

    1. Test case (standard): `add n/John Doe p/83143234 a/74 Acorn Street 11 653203 #02-02 gl/abrsm 1 g/` <br>
       **Expected**: Student "John Doe" is added to the student directory with correct details.

    2. Test case (with group): `add n/Mary Sue p/9732123 a/51 Mangrove Drive 11 642371 #01-03 gl/abrsm 2 g/Group one` <br>
       **Expected**: Student "Mary Sue" is added to the student directory, assigned to "Group one".

2. Adding a duplicate student
    1. Test case (name and phone clashing): `add n/john doe p/83143234 a/Acorn Street #02-02 gl/abrsm 1 g/` <br>
       **Expected**: Error is thrown as student already exists (note the lower case)
   
    2. Test case (only name is clashing): `add n/John Doe p/8923921 a/Acorn Street #03-01 gl/abrsm 1 g/` <br>
       **Expected**: Student "John Doe" is added to the student directory, as the duplicate criteria is name and phone number

### Editing a student
1. Editing a student’s details

   1. Prerequisites: List all students using the list command. Ensure there is at least one student in the list.

   2. Test case: `edit 1 n/Jane Doe p/91234567` <br>
      **Expected**: The first student's name and phone number are updated to "Jane Doe" and "91234567", respectively.

   3. Test case: `edit 1 g/Group one`<br>
      **Expected**: The first student's group is updated to "Group one".

### Assigning Piano Pieces to a Student
1. Assign piano pieces

    1. Test case: `assign 1 pn/Moonlight Sonata pn/Fur Elise` <br>
       **Expected**: "Moonlight Sonata" and "Fur Elise" are assigned to the first student.
   
    2. Test case: `assign 1 pn/Moonlight Sonata pn/Claire de Lune`<br>
       **Expected**: Throws an error not allowing you to assign the same piece twice
   
    3. Test case: `assign 2 pn/Waltz pn/Etude`<br>
       **Expected**: "Waltz" and "Etude" are assigned to the second student.

### Unassigning Piano Pieces from a Student
1. Unassign piano pieces

    1. Test case: `unassign 1 pn/Moonlight Sonata pn/Fur Elise` <br>
       **Expected**: "Moonlight Sonata" and "Fur Elise" are unassigned from the first student.
   
    2. Test case: `unassign 2`<br>
       **Expected**: All pieces are unassigned from the second student

### Scheduling a Regular Lesson
1. Schedule a lesson

    1. Test case: `schedule 1 d/Monday st/12:00 et/14:00` <br>
       **Expected**: A regular lesson is scheduled for the first student on Monday from 12:00 to 14:00.

    1. Test case: `schedule 2 d/Friday st/16:00 et/18:00`<br>
       **Expected**: A regular lesson is scheduled for the second student on Friday from 16:00 to 18:00.

### Cancelling a Regular Lesson
1. Cancel a scheduled lesson

    1. Test case: `cancel 1 dt/04-11-2024 st/12:00` <br>
       **Expected**: The first student's lesson on November 04, 2024, at 12:00 is canceled.

    1. Test case: `cancel 2 dt/18-10-2024 st/16:00`<br>
       **Expected**: The second student's lesson on October 18, 2024, at 16:00 is canceled.

### Scheduling a Makeup Lesson
1. Schedule a makeup lesson

    1. Test case: `makeup 1 dt/25-12-2024 st/12:00 et/14:00` <br>
       **Expected**: A makeup lesson is scheduled for the first student on December 25, 2024, from 12:00 to 14:00.

    1. Test case: `makeup 2 dt/26-12-2024 st/10:00 et/11:30`<br>
       **Expected**: A makeup lesson is scheduled for the second student on December 26, 2024, from 10:00 to 11:30.

    1. Test case: `makeup 1 dt/31-02-2024 st/10:00 et/12:00` <br>
       **Expected**: An error is thrown as 31st Feb is not a valid date.

### Viewing the Schedule
1. View current week’s schedule

    1. Test case: `view` <br>
       **Expected**: The schedule for the current week is displayed, showing all lessons and makeup lessons.

2. View a specific week’s schedule

   1. Test case: `view dt/01-12-2024` <br>
      **Expected**: The schedule for the week containing December 1, 2024, is displayed, showing all lessons for that period.

### Finding Students
1. Find by name

    1. Test case: `find n/John` <br>
       **Expected**: Students with "John" in their name are displayed.

2. Find by multiple details

    1. Test case: `find n/John gl/ABRSM`<br>
    **Expected**: Students with "John" in their name and "ABRSM" in their grade level are displayed.

<div style="page-break-after: always;"></div>

### Sorting Students
1. Sort by name in ascending order

    1. Test case: `sort n/ASC` <br>
       **Expected**: The list of students is sorted alphabetically by name in ascending order.

2. Sort by grade level in descending order and name in ascending order

    1. Test case: `sort gl/DESC n/ASC` <br>
       **Expected**: Students are first sorted by grade level in descending order, and those with the same grade level are sorted by name in ascending order.

### Undoing the Last Command
1. Undo previous modification

    1. Test case: `undo` after performing any modification (e.g., adding a student). <br>
       **Expected**: The last modification is reverted, and the student directory reflects this change.

### Redoing the Last Undo Command
1. Redo last undone action

    1. Test case: `redo` after an undo command. <br>
       **Expected**: The previously undone action is reapplied, and the student directory reflects the re-application of the change.

### Saving data

1. Dealing with corrupted data files

   1. Files are corrupted in the following cases:
      1. Invalid values
      2. Clashing lessons
      3. Duplicate students
      4. Incorrect group syncing
      5. Invalid file format
      6. Invalid JSON object structure
   <br><br>
   1. **Expected**: KeyContacts will load an empty list. Upon performing any command, a new data file will be written to override the corrupted data file. 

---

<div style="page-break-after: always;"></div>

## **Appendix: Planned Enhancements**

**Note:** Team size 5

1. **Update the checking for clashing lessons such that past make-up lessons do not affect the scheduling of regular lessons.** Currently, make-up lessons in the past cause clashes when scheduling regular lessons. The planned enhancement will cause clashes to only be detected with lessons that occur in the future.

<br>

2. **Allow regular lessons to stretch across multiple days (e.g 22:00 to 24:00).** Currently, lessons are unable to span multiple dates as they store a date/day, start time and end time. This can be expanded to allow lessons that stretch across multiple days by splitting the date/day field into start date/day and end date/day fields.

<br>

3. **Improve sorting to compare alphabetically instead of by ASCII value.** Sorting is currently done by ASCII values but this may be unintuitive behaviour. The planned enhancement will have lower-case and upper-case letters treated the same, meaning the sort will be case-insensitive.

<br>

4. **Allow the editing of existing group names.** Currently, there is no way to update the name of a group across an entire group of students. The planned enhancement would be to add a new command to perform this.

<br>

5. **Improve the clarity of the undo feature by specifying the command that was undone.** The undo feature currently has no indication of what command was undone. The planned enhancement is to show the exact command that was undone in the returned message from running the command.

<br>

6. **Improve the calendar view to display long names more clearly.** The calendar view currently struggles to display long names, resulting in ellipses and an inability to view the entire student name or group name. The planned enhancement is to make the student or group name more clearly visible by wrapping the text.

<br>

7. **Improve calendar view to indicate if a regular lesson was cancelled for that week.** The calendar view currently has no indication of when a student's regular lesson is if it is cancelled on the week that is being viewed. This can cause confusion among the tutors as they may observe a clash with the lesson despite not seeing it in the schedule. The planned enhancement is to show all regular lessons but with an indication of its cancellation status.

<br>

8. **Increase minimum width of application.** Currently, the minimum width causes the group tag to be cut off. The planned enhancement is to increase this minimum size to a point that will not have this issue.

<br>

9. **Gracefully handle long group names.** Currently, a long group name will obscure the student's name. The planned enhancement is to restrict the group name to 20 characters long.

<br>

10. **Improve the clarity of the redo feature by specifying the command that was redone.** Currently, the redo command return result is unclear. The planned enhancement will be to include the command being redone in the redo command result.

---

<div style="page-break-after: always;"></div>

## **Appendix: Effort**

### Difficulty Level
Our project was significantly more complex when compared to AB3. We have introduced various new commands to support new functionalities. Our student objects involve more fields, with certain fields (lessons) requiring syncing across multiple students. Our UI is also more complicated, with the introduction of a new calendar view that updates based on students' lesson data.

### Challenges Faced
Command Planning: Planning for multiple commands, which enhance the product, while fitting together cohesively, required careful thought and deliberation.
Lesson-related Logic: Logic for syncing lessons across students in the same group, as well as checking for lesson clashes, needed to be developed.
UI Refactoring: Designing a UI which could show all existing information, while also displaying a calendar view required multiple drafts and discussions.

### Effort Required
Our project involved substantial effort in several key areas:

Refactoring: Modifying existing AB3 features to suit our product (e.g. removing extra fields)
Command Implementation: Implementing multiple new commands
Model Implementation: Updating the model with new fields, and implementing lesson-related logic.
Testing and Debugging: Testing and debugging the new features, as well as performing regression testing

### Achievements
We have extended AB3 with multiple new features and a better UI, to make the app more specialised and suitable for piano tutors.
