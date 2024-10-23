---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# Tuteez Developer Guide

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


**API** : [`LessonManager.java`]()

<puml src="diagrams/LessonManagerClassDiagram.puml" width="450" />

The `LessonManager` component, 
* stores all the info regarding `Lesson` objects.`LessonManager.dayLessonsMap` is a `HashMap` where keys are `Day`s, intuitively there are only 7 keys in the `HashMap`. One for each day of the week.
* uses a `TreeSet<Lesson>` as value of `LessonManager.dayLessonMap`. A `TreeSet` is used to maintain ordering of lessons. Lessons are ordered according to `Lesson.startTime`
* is the main class that determines any overlapping or clashing lessons. Refer to `LessonManager#isClashingWithExistingLesson`

<puml src="diagrams/AddSequenceDiagram.puml" width="1000" />

The diagram above is an example of how abstraction is used for the `AddCommand`. Some `opt` statements and other complexities have been removed but the general flow is clear. 
* In words, for every lesson a new student has. `LessonManager` checks against existing lessons on the same `Day`. It then calls `Lesson#isClashingWithOtherLesson` which takes two `Lesson` objects are arguments and returns `True` if they clash else `False`



<box type="info" seamless>

Note: As of `v1.4` clashing lessons are not allowed, hence when a `Person` is deleted, all of his/her lessons can be safely deleted as well. No other students will have the same lesson time. The team is considering allowing clashing lessons after warning users for a future release.

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

* tech-savvy full-time tuition teacher
* has a need to manage a significant number of students
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Our app is designed to help tech-savvy full-time tuition teachers manage the schedules and contact details of a small to medium number of students. The app focuses on preventing scheduling conflicts by automatically checking for overlapping lesson times and organising students into groups for easier lesson planning.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​              | I want to …​            | So that I can…​                                                                                                 |
|---------|----------------------|-------------------------|-----------------------------------------------------------------------------------------------------------------|
| `* * *` | new user             | add my students' contact details | easily access and communicate with them or their guardians                                                      
| `* * *` | new user             | search for a student's name        | find relevant student(s) easily                                                                                 |
| `* * *` | new user             | delete students' entries     | remove students that I am no longer teaching                                                                    |
| `* * *` | new user             | easily access my tutoring schedule with each student | stay organised and manage my records more effectively                                                           
| `* * *` | new user             | be automatically alerted if there are scheduling conflicts when adding a new student whose tuition time overlaps with another student | quickly adjust their schedule and avoid double-booking                                                          |
| `* * *` | new user             | organise my students' contact details                        | find my students' by certain categories easily                                                                  |
| `* * *` | new user             | have an option to store the address of the students                        | easily go to the student's house if the tuition session is in person                                            |
| `* * *` | impatient user       | be able to add a task within 15 seconds                        | use the app in a rush                                                                                           |
| `* * *` | impatient user       | be able to load up the app with the main user interface within 1-2 seconds                        | use the app seamlessly                                                                                          |
| `* * *` | intermediate user    | schedule classes that repeats every week                        | not  keep scheduling classes on the same day(s)                                                                 |
| `* * *` | intermediate user    | filter the entries by teaching date, such as “today” or “tomorrow” or "next 3 days"                        | quickly view and manage the students I am teaching on specific days                                             |
| `* * *` | intermediate user    | be able to prevent duplicate student entries                        | avoid confusion when managing students with similar names                                                       |
| `* * *` | expert user          | leave notes on a student's entry to track their learning progress                        | monitor and adjust my teaching strategies effectively                                                           |
| `* * *` | expert user          | keyboard shortcuts                         | quickly add, delete and update students information                                                             ||           |                                            | leave notes on a student's entry to track their learning progress                             |                                                                      |
| `* *`   | new user             | have a guided tour or input guide                        | know how to add students to the address book                                                                    |
| `* *`   | new user             | clear all the app data quickly                        | delete data that was used when experimenting with the app.                                                      |
| `* *`   | new user             | customise my own style of formatting by choosing from a few different options                        | not  follow a single formatting option instructed by the product                                                |
| `* *`   | long screentime user | filter away data that I deem unnecessary                         | stay undistracted from other data                                                                               |
| `* *`   | intermediate user    | copy information of my students to my clipboard                        | I can send them their invoice or notes quickly                                                                  |
| `* *`   | intermediate user    |  filter entries by the payment status for the month                       | easily identify who has paid and who has not, enabling me to send invoices only to the students who are overdue |
| `* *`   | intermediate user    | export student data in various formats (CSV, Excel, WhatsApp message etc.)                        | students can view their progress easily, and it is easy for students to view it on their end                    |
| `* *`   | intermediate user    | record and track any special needs or accommodations required by my students                        | adapt my teaching style or lesson content appropriately                                                         |
| `* *`   | intermediate user    | customise the user interface (color scheme, font size) of the app                        | make it visually appealing and easy to use according to my preferences                                          |
| `* *`   | intermediate user    | track each student’s performance in specific subjects (e.g. Math, Science)                        | identify their strengths and weaknesses in different areas                                                      |
| `* *`   | intermediate user    | record whether each student prefers online or in-person tutoring sessions | plan for travelling if needed                                                                                   |
| `* *`   | impatient user       | quickly search for a student using autocomplete or suggestions as I type the student’s name | find a student quickly                                                                                          |
| `* *`   | forgetful user       | see students that I am teaching today once I open the address book | reminded of who I need to teach today                                                                           |
| `* *`   | expect user          | track students' exam results and view their progress over time | update my teaching style to better meet their needs                                                             |
| `* *`   | expert user          | quickly filter by important dates, such as upcoming exams | effectively tailor my lessons to prepare students in a timely manner                                            |
| `*  `   | new user             | undo an operation | undo a command if it was an mistake                                                                             |
| `* `    | new user             | import student data in various formats (e.g., CSV, Excel) | quickly see how the product will look when populated                                                            |
| `*`     | intermediate user    | set up automated reminders for upcoming lessons | adequately prepare for my lessons                                                                               |
| `*`     | intermediate user    | attach lesson materials, homework assignments, or additional resources to each student's profile | easily share and track what I’ve assigned to each student                                                       |
| `*`     | intermediate user    | filter and view students who have upcoming exams | prioritise revision sessions and prepare them effectively                                                       |
| `*`     | forgetful user       |add a profile picture to my students recognise my student by picture  | recognise my student by picture and contact them without remembering their name                                 |
| `*`     | expert user          | have some tips of the day / updates on more advanced features  | use the app seamlessly                                                                                          |
| `*`     | expert user          | automate the process of sending invoice reminders to students or parents | manage payments efficiently and eliminate the need for manual tracking and individual messaging                 |
| `*`     | expert user          | not type the exact command, just something like it  | not just adhere to a specific format                                                                                                       |


*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC1 - Add a student**

**MSS**

1. User types keyword followed by student details into textbox
2. App acknowledges that a new user has been added
3. Use case ends

**Extensions**

- 2a. App detects similar/identical name or phone number in records

    - 2a1. Asks user to confirm action
    - 2a2. User confirms/denies
    - 2a3. App adds new entry and acknowledges / returns to home screen
    - 2a4. Use case ends


**Use case: UC2 - List all students**

**MSS**

1. User types keyword 
2. App displays all students address book in alphabetical order 
3. Use case ends

**Use case: UC3 - Delete a person**

**MSS**

1.  User requests to list persons
2.  AddressBook shows a list of persons <u>(UC2)</u>
3.  User types keyword followed by delete index or name
4.  AddressBook deletes the person
5. Use case ends


**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. AddressBook shows an error message.

      Use case resumes at step 2.
- 3b. The given name does not exist
  - 3b1 AddressBook shows an error message. 
  - 3b2. Use case resumes from step 2


### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 contacts without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The system should respond to user commands within 15 seconds.
5.  The application should launch within 2 seconds.
6.  The application should not store sensitive information (e.g., NRIC numbers) without explicit user consent.
7.  The application should demonstrate stability during long-term usage without crashing or freezing.
8.  The application should be usable by novice users without extensive training.
9.  The application should handle errors gracefully, providing meaningful feedback to users in case of failures.
10. Comprehensive user and technical documentation should be provided


### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Student**: A person who is taking lessons from the tutor
* **Tutor**: The user of the application
* **Parent**: A person who is the parent or guardian of the student
* **Add student**: A feature that allows users to create a new student entry along with their information in the application
* **Delete student**: A feature that allows users to remove a student entry and their information from the application
* **Search student**: A feature that allows users to find a particular student by entering their name
* **Private student details**: Student details and contact information that are not meant to be shared with others
* **Private parent details**: Parent details and contact information that are not meant to be shared with others
* **Notes tab**: A section within the application where users can record additional information about their students
* **Tutoring schedule**: A timetable that shows the dates and times of lessons with students
* **Scheduling conflicts**: Overlapping lesson times when a tutor has more than one lesson at a specific time
* **Tags**: Labels that can be assigned to students to group them based on common characteristics
* **Filtering**: A feature that allows users to view specific groups of students based on their tags or specific criteria

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
