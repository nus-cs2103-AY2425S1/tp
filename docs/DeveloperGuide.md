---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# Teletutors Developer Guide

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

**Target user profile**: Tutors managing students

**Value proposition**:
* Easier management of administrative tasks
* Easier for users who are more proficient with _CLI - interface_
* Separate work and personal tasks / messages


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As …​                                                                       | I want to …​                                                                                                 | So that …​                                                                                                                                                     |
|----------|-----------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `* * *`  | a tutor who gives homework                                                  | easily keep track of my students' assignments                                                                | I know whether they have submitted them or not, and whether I need to grade them or remind them to submit work                                                 |
| `* * *`  | a tutor who wants to keep his personal and work life separate               | keep in contact with students and their parents without needing to give them my personal email or number     | my privacy is kept intact and my personal information is not shared                                                                                            |
| `* * *`  | a tutor who cares about his students' progress                              | easily keep track of my students' performance in school as well as his participation and learning in tuition | I am better equipped to help my students do well                                                                                                               |
| `* * *`  | a not so tech-savvy tutor                                                   | easily understand the appications' functions and uses, and how to navigate through the application smoothly  | I would not have any issues using the app to better organise my tasks and schedule as a tutor                                                                  |
| `* * *`  | a part-time tutor (student) with tight deadlines                            | take a glance through the app to see exactly what needs my attention                                         | I can instantly filter out the important tasks that I need to do, like marking and preparing for tutorials, so i can shift my focus back to my other deadlines |
| `* * *`  | a tutor ready to start using the app                                        | import the data of all my students                                                                           | I can use the app instantly                                                                                                                                    |
| `* * *`  | a first time user                                                           | get a list of commands that cover the main features of the app                                               | I can explore and get a feel for the functionalities                                                                                                           |
| `* * *`  | a tutor who prefers CLI to GUI                                              | use the keyboard for all purposes in the app instead of needing to scroll and click using a mouse            | I am comfortable and enjoy using the app                                                                                                                       |
| `* * *`  | a veteran tutor with a set of pre-existing students                         | synchronize list of contacts into the app                                                                    | I do not need to manually enter each contact which is a hassle                                                                                                 |
| `* * *`  | a private home tutor                                                        | access student address location using the app                                                                | I know where to travel to for a specific appointment                                                                                                           |
| `*`      | a tutor                                                                     | have timely reminders for upcoming deadlines                                                                 | I can make sure the students submit them on time                                                                                                               |
| `*`      | a tutor who wants to keep tabs on a student's grades                        | track the scores of students across several assessments                                                      | I can determine which student requires more attention                                                                                                          |
| `*`      | a full-time tutor                                                           | track the hours I've worked each week                                                                        | I can ensure accurate payment                                                                                                                                  |
| `*`      | a tutor who has different classes of students                               | sort my students by their class                                                                              | I can find the appropriate information quickly                                                                                                                 |
| `*`      | a tutor                                                                     | quickly search for tasks or appointments based on keywords                                                   | I can find specific tasks or sessions easily                                                                                                                   |
| `*`      | a tutor                                                                     | set recurring tasks (like weekly lesson planning)                                                            | I don't have to manually input them every time                                                                                                                 |
| `*`      | a forgetful tutor                                                           | receive notifications when new sessions or tasks are coming up                                               | the task of remembering these timings can be relegated to the app                                                                                              |
| `*`      | a 1-1 tutor                                                                 | keep track of individual student progress and test scores                                                    | I can better tailor tuition efforts to improve outcomes                                                                                                        |
| `*`      | an organised tutor                                                          | set task priorities (high, medium. low)                                                                      | I can focus on the most important tasks first                                                                                                                  |
| `*`      | a tutor                                                                     | update contact details                                                                                       | students information are up to date                                                                                                                            |
| `* *`    | a detailed tutor                                                            | add notes to contact details                                                                                 | I can track important information                                                                                                                              |
| `* *`    | a tutor of a class of students                                              | mark the attendance of students of the class I am currently teaching                                         | I can monitor which students did not attend the class for that week so that I can contact their parents                                                        |
| `* * *`  | a tutor                                                                     | add new students manually                                                                                    | new students that join the class midway can be added                                                                                                           |
| `* *`    | an organised tutor                                                          | color-code tasks and appointments based on their type (tutorials, marking, meetings)                         | I can visually differentiate between various commitments                                                                                                       |
| `* *`    | a tutor                                                                     | search students by certain attribute (e.g. name)                                                             | I can access all details about them                                                                                                                            |
| `* * *`  | a long time tutor                                                           | clear the data of my ex students                                                                             | I can keep my contacts organised                                                                                                                               |
| `* *`    | a tutor with a messy filing system for notes and other additional content   | use the app to organise these contents based on the class                                                    | it will be more efficient for finding the notes required for that class                                                                                        |
| `* *`    | a tutor                                                                     | be notified when an input task or session overlaps with another commitment                                   | I can avoid scheduling conflicts                                                                                                                               |
| `* *`    | a paranoid tutor                                                            | backup my contacts to a local file                                                                           | I do not accidentally lose all contacts                                                                                                                        |
| `* *`    | a tutor                                                                     | extend or adjust deadlines if needed                                                                         | I can manage unexpected delays without losing track                                                                                                            |
| `*`      | a tutor who would like the option of switching between light and dark theme | switch between the modes in the application                                                                  | I would get my preferred layout theme                                                                                                                          |
| `* *`    | a data-centric tutor                                                        | view a summary of all contacts                                                                               | I can analyze key information about my students                                                                                                                |
| `* *`    | a tutor who doesn't use a mouse                                             | use the keyboard shortcuts provided by the application                                                       | I can utilize and learn the features efficiently                                                                                                               |
| `* *`    | a tutor with a long history of students                                     | archive certain contacts                                                                                     | I only keep track of currently active students                                                                                                                 |
| `* *`    | a careless tutor                                                            | undo latest command                                                                                          | I can easily update contacts                                                                                                                                   |
| `* *`    | a tutor                                                                     | receive alerts when upcoming tasks and lessons are nearing                                                   | I can better stay on schedule                                                                                                                                  |
| `*`      | a tech savvy tutor                                                          | set up macros to quickly switch between commonly used features                                               | I can customise the application to my preferences and master the features of the application                                                                   |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `TeleTutors App` and the **Actor** is the `tutor`, unless specified otherwise)

**Use case: Add a new Task**

**MSS**

1.  Tutor requests to add a new task.
2.  Teletutors App prompts for the task details.
3.  Tutor provides the required task details.
4.  Teletutors App confirms the task has been successfully added.

    Use case ends.

**Extensions**

* 3a. Tutor enters an invalid deadline
    * 3a1. Teletutors App displays an error message: `Invalid date format. Please use 'YYYY-MM-DD'.`
    * 3a2. Tutor re-enters the correct date.

      Use case resumes from Step 4.

**Use case: Mark student Attendance**

**MSS**
1. Tutor requests to mark attendance for a student in a selected tutorial session.

2. Teletutors App displays the list of students for the session.

3. Tutor selects the student and specifies whether they are present or absent.

4. Teletutors App updates the attendance record and confirms the action.

   Use Case Ends.

**Extensions**
* 3a. Student is not found in the session list
<<<<<<< HEAD
    * 3a1. Teletutors App displays an default placeholder message: `No students found.`

      Use case resumes from Step 2
=======
  * 3a1. Teletutors App displays an default placeholder message: `No students found.`

    Use case resumes from Step 2
>>>>>>> 178988f591551519cd55cd6c8d5910c737f0143a


**Use case: Delete student details**

**MSS**

1.  Tutor requests to delete a student's details.
2.  Teletutors App prompts for confirmation
3.  Tutor confirms the deletion.
4.  Teletutors App deletes the student and associated records.

    Use case ends.

**Extensions**

* 1a. The student does not exist in the system
    * 1a1. Teletutors App displays an error message: `Student not found. Please check the name and try again`

      Use case resumes from Step 1.




### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Codebase should be _modular_ and easy to update, so that future developers can easily add new features or fix bugs.
5.  App should have the ability to gracefully handle a data file that is corrupted without crashing, and inform the user of the issue.
6.  System should _gracefully recover_ from minor errors (e.g., incorrect or incomplete input) by either prompting the user to correct the issue, or using default values where applicable.
7.  System should have well-written and accessible user and developer documentation to support both end-users and future developers in using and maintaining the application.
8.  System should have a comprehensive suite of automated tests to ensure that new changes do not break existing functionality.
9.  System should implement a _data retention policy_, ensuring that data and records older than a certain period are archived or deleted to optimize performance and comply with legal requirements.
10. System should have easy-to-use backup and restore functionality, enabling users to create backups of their data, and restore their data in the event of data loss or corruption.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Modular**: Codebase is divided into separate parts that can be updated without affecting other parts.
* **Gracefully recover**: Provides an appropriate solution without crashing the application.
* **Data retention policy**: Guidelines regarding what data should be kept, how long data should be kept and more.
* **CLI - interface**: Any application that mainly takes in input via text, not necessarily through a command console.
* **Privacy**: Personal details that are not meant to be shared with others.

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

<<<<<<< HEAD
    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.
=======
   1. Re-launch the app by double-clicking the jar file.<br>
      Expected: The most recent window size and location is retained.
>>>>>>> 178988f591551519cd55cd6c8d5910c737f0143a

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
