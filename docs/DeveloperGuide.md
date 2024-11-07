---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# T_Assistant Developer Guide

<!-- * Table of Contents -->
<page-nav-print /> 

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* Libraries
  used: [JavaFX](https://openjfx.io/), [Jackson](https://github.com/FasterXML/jackson), [JUnit5](https://github.com/junit-team/junit5)

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

**`Main`** (consisting of classes [
`Main`](https://github.com/AY2425S1-CS2103-F12-2/tp/tree/master/src/main/java/seedu/address/Main.java) and [
`MainApp`](https://github.com/AY2425S1-CS2103-F12-2/tp/tree/master/src/main/java/seedu/address/MainApp.java)) is in
charge of the app launch and shut down.

* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues
the command `del_s sno/A0123456A`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API
  `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using
the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component
through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the
implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [
`Ui.java`](https://github.com/AY2425S1-CS2103-F12-2/tp/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`,
`StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures
the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that
are in the `src/main/resources/view` folder. For example, the layout of the [
`MainWindow`](https://github.com/AY2425S1-CS2103-F12-2/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java)
is specified in [
`MainWindow.fxml`](https://github.com/AY2425S1-CS2103-F12-2/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Student` object residing in the `Model`.

### Logic component

**API** : [
`Logic.java`](https://github.com/AY2425S1-CS2103-F12-2/tp/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("dg gn/CS2103-F12-2")` API
call as an example.

<puml src="diagrams/DeleteGroupSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `dg gn/CS2103-F12-2` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteGroupCommandParser`, `DeleteGroupCommand` and `CommandResult` should end at the destroy marker (X) but due to a limitation of
PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates
   a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which
   is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a student).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take
   several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:

* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a
  placeholder for the specific command name e.g., `AddStudentCommandParser`) which uses the other classes shown above to parse
  the user command and create a `XYZCommand` object (e.g., `AddStudentCommand`) which the `AddressBookParser` returns back as a
  `Command` object.
* All `XYZCommandParser` classes (e.g., `AddStudentCommandParser`, `DeleteStudentCommandParser`, ...) inherit from the `Parser`
  interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [
`Model.java`](https://github.com/AY2425S1-CS2103-F12-2/tp/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Student` objects (which are contained in a `UniqueStudentList` object).
* stores the currently 'selected' `Student` objects (e.g., results of a search query) as a separate _filtered_ list which
  is exposed to outsiders as an unmodifiable `ObservableList<Student>` that can be 'observed' e.g. the UI can be bound to
  this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a
  `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they
  should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which
`Student` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Student`
needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

### Storage component

**API** : [
`Storage.java`](https://github.com/AY2425S1-CS2103-F12-2/tp/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="800" />

The `Storage` component,

* can save both address book data and user preference data in JSON format, and read them back into corresponding
  objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only
  the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects
  that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

<box type="info">

**Note:** For simplicity, certain details such as conditional checks, parsing
and more detailed implementation on model changes have been omitted.

</box>

--------------------------------------------------------------------------------------------------------------------

### Delete Student Feature

The `Delete Student` feature allows users to delete an existing student in the address book given a student's student number `sno`.

The following shows the activity diagram when the user executes the `del_s` command:
<puml src="diagrams/DeleteStudentActivityDiagram.puml" alt="DeleteGroupCommandAD" />

#### Usage

**Syntax:** `del_s/ds sno/STUDENT_NUMBER.`

**Example:** `ds sno/A0123456K`

#### Implementation details

1. User has the application launched with at least 1 student added.
2. User executes `ls` to view all students. For this example, the user wishes to delete a student with student number `A0234567H`.
3. The user executes `ds sno/A0234567H` to delete the student with a student number `A0234567H`. The command is parsed in
   the
   `AddressBookParser`.
4. `DeleteStudentCommandParser` is created and gets the student number of the student to be deleted. The student number is used to
   construct a `DeleteStudentCommand` object.
5. The `DeleteStudentCommand` object then calls `deletePerson(student)` in the `ModelManager` with the specified student to be
   deleted. This method deletes the specified `Student` in the model.
7. Finally, the `DeleteStudentCommand` returns the `CommandResult`.

##### Note

This feature will also check if the deleted `Student` belongs to any `Group` and remove the `Student` from that `Group`, 
resetting the affected `Group` affiliation.

**Sequence Diagram:** The following sequence diagram shows how the above steps for delete group works:
<puml src="diagrams/DeleteStudentSequenceDiagram.puml" alt="DeleteGroupCommand"/>

<box type="info" seamless>

**Note:** The lifelines for `DeleteStudentCommandParser`, `DeleteStudentCommand`, and
`CommandResult` should end at the destroy marker (X) but due to a limitation of
PlantUML, the lifeline continues till the end of diagram.

</box>

#### Design considerations

**Aspect 1:** Usage of StudentNumber as identifier

1. **Design #1: Use StudentNumber**

* Pro: More deliberate and since StudentNumber are more complex, the user will be more aware of their decision
* Con: More typing is required

2. **Design #2:** Use Index

* Pro: Easy and quick
* Con: Possible for user to mistype the wrong number
--------------------------------------------------------------------------------------------------------------------

### Delete Group feature

The `Delete Group` feature allows users to delete an existing group in the address book given a group's name.

The following shows the activity diagram when the user executes the `del_g` command:
<puml src="diagrams/DeleteGroupActivityDiagram.puml" alt="DeleteGroupCommandAD" />

#### Usage

**Syntax:** `del_g/dg gn/GROUP_NAME [gn/GROUP_NAME]...`

**Example:** `dg gn/CS2103-F12-2`

#### Implementation details

1. User has the application launched with at least 1 group added.
2. User executes `lg` to view all groups. For this example, the user wishes to delete `CS2103-F12-2`.
3. The user executes `dg gn/CS2103-F12-2` to delete the group with a group name `CS2103-F12-2`. The command is parsed in
   the
   `AddressBookParser`.
4. `DeleteGroupCommandParser` is created and gets the group name of the group to be deleted. The group name is used to
   construct a `DeleteGroupCommand` object.
5. The `DeleteGroupCommand` object then calls `deleteGroup(group)` in the `ModelManager` with the specified group to be
   deleted. This method deletes the specified `Group` in the model.
7. Finally, the `DeleteGroupCommand` returns the `CommandResult`.

##### Note

This feature will also remove `Students` in the `Group` and reset their `Group`, and delete all `Tasks` related to the
`Group`, but the details are omitted.

#### Sequence diagram

The following sequence diagram shows how the above steps for delete group works:
<puml src="diagrams/DeleteGroupSequenceDiagram.puml" alt="DeleteGroupCommand"/>

<box type="info" seamless>

**Note:** The lifeline for `DeleteGroupCommandParser` should end at the destroy marker (X) but due to a limitation of
PlantUML, the lifeline continues till the end of diagram.
</box>

#### Design considerations

**Aspect 1:** Usage of GroupName as identifier

1. **Design #1: Use GroupName**

* Pro: More deliberate and since GroupNames are more complex, the user will be more aware of their decision
* Con: Must type a lot

2. **Design #2:** Use Index

* Pro: Easy and quick
* Con: Possible for user to mistype the wrong number

--------------------------------------------------------------------------------------------------------------------

### Undo/redo feature

The undo/redo mechanism is facilitated by `VersionHistory`. It stores an ArrayList `versions` of ReadOnlyAddressBook.

Whenever there are changes made to the AddressBook, a defensive copy of the AddressBook is created and stored in the
ArrayList. `VersionHistory` also stores a pointer to the current version of the addressbook.

If newly initialized, this pointer is set to -1. We have set a maximum number of versions to be able to stored at 100.
Once this limit is reached, the earliest entry in the ArrayList will be deleted by `VersionHistory` so that a new
version can be stored.
Additionally, it implements the following operations:

* `VersionHistory#addVersion(Model model)`— Saves the current state to its history.
* `VersionedAddressBook#undoVersion()`— Restores the previous version from its history.
* `VersionedAddressBook#redoVersion()`— Restores a previously undone version from its history.

These operations are exposed in the `Command` abstract class: `Command#addVersion()`, `Command#undoVersion()`
and `Command#redoVersion()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

#### Implementation details

1. User has the application launched and has made at least 1 change to the system.
2. For our example, user executes 'asg' and adds a student to a specific group. However, this was done incorrectly and
   the student is added into an incorrect group.
3. The user executes `undo`. This command is parsed into the `AddressBookParser`.
4. An `UndoCommandParser` object is constructed and it constructs a `UndoCommand` object.
5. The `UndoCommand` object then calls `updateVersionHistory(versionHistory, model)`. This in turn calls `undoVersion()`
   of `VersionHistory`.
6. This updates the pointer in `VersionHistory` to point to the previous version. If the pointer is already set to 0,
   there is nothing to undo and an exception will be thrown.
7. The new addressbook is then saved in the model.
8. Finally, the `UndoCommand` returns the `CommandResult`.
9. The `RedoCommand` follows the exact same workflow as above, but its `updateVersionHistory(versionHistory, model)`
   calls upon `redoVersion` instead, updating the pointer to the previously undone version.

#### Sequence diagram

The following sequence diagram shows how the above steps for how undo works:
<puml src="diagrams/UndoSequenceDiagram.puml"/>

--------------------------------------------------------------------------------------------------------------------

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

* CS2103 tutor
* has a need to manage a significant number of students
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**:

* Manage contacts faster than a typical mouse/GUI driven app
* Helps to track the following:
    * Students
    * Their groups
    * Groups’ progress
    * TA will create Groups and assign tasks
        * Mark the tasks as the groups complete them

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a ...        | I want to...                                  | So that I can...                                    |
|----------|-----------------|-----------------------------------------------|-----------------------------------------------------|
| `* * *`  | disorganised TA | mark tasks                                    | keep track of what a group has completed            |
| `* * *`  | disorganised TA | remove tasks after I wrongly added them       | correct my mistake                                  |
| `* * *`  | new TA          | add tasks to groups                           | keep track of what task each group has              |
| `* * *`  | TA              | see all the tasks my groups have              |                                                     |
| `* * *`  | disorganised TA | remove groups that have completed the course  | keep the assistant relevant                         |
| `* * *`  | new TA          | add groups into the assistant                 |                                                     |
| `* * *`  | TA              | see all my groups                             | keep track of how many groups are under my tutelage |
| `* * *`  | clumsy TA       | remove students from their mis-assigned group | correct my mistake                                  |
| `* * *`  | TA              | add students to their respective groups       |                                                     |
| `* * *`  | disorganised TA | remove students no longer taking course       | keep the assistant relevant                         |
| `* * *`  | new TA          | add students into the assistant               |                                                     |
| `* * *`  | TA              | see all my students                           | keep track of who is in my tutorial classes         |
| `* *`    | messy TA        | sort my tasks                                 | keep the assistant organised                        |
| `* *`    | clumsy TA       | edit tasks with wrong information             | correct my mistake                                  |
| `* *`    | messy TA        | sort my groups                                | keep the assistant organised                        |
| `* *`    | disorganised TA | find groups in my tutorials                   |                                                     |
| `* *`    | clumsy TA       | edit group particulars                        | correct my mistake                                  |
| `* *`    | messy TA        | sort my students                              | keep the assistant organised                        |
| `* *`    | disorganised TA | find students in my tutorials                 |                                                     |
| `* *`    | clumsy TA       | edit student particulars                      | correct my mistakes                                 |
| `*`      | clumsy TA       | undo my actions                               | correct my mistake                                  |
| `*`      | clumsy TA       | redo my actions                               |                                                     |

### Use cases

(For all use cases below, the **System** is the `T_Assistant` and the **Actor** is the `user`, unless specified
otherwise)

**Use case: List Students**

**MSS**

1. User requests to list students.
2. T_Assistant shows a list of students.

Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

**Use case: Add a Student**

**MSS**

1. User requests to list students.
2. T_Assistant shows a list of students.
3. User requests to add a new Student into the list.
4. T_Assistant adds the new Student to the list.

Use case ends.

**Extensions**

* 3a. The Student parameters are invalid.

    * 3a1. T_Assistant shows an error message.

      Use case resumes at step 2.

* 3b. The Student already exits.

    * 3b1. T_Assistant shows an error message.

      Use case resumes at step 2.

**Use case: Delete a Student**

**MSS**

1. User requests to list students.
2. T_Assistant shows a list of students.
3. User requests to delete a Student from the list.
4. T_Assistant deletes the Student from the list.

Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

* 3a. The Student parameters are invalid.

    * 3a1. T_Assistant shows an error message.

      Use case resumes at step 2.

**Use case: Add a Group**

**MSS**

1. User requests to list groups.
2. T_Assistant shows a list of groups.
3. User requests to add a new Group into the list.
4. T_Assistant adds the new Group to the list.

Use case ends.

**Extensions**

* 3a. The Group parameters are invalid.

    * 3a1. T_Assistant shows an error message.

      Use case resumes at step 2.

* 3b. The Group already exits.

    * 3b1. T_Assistant shows an error message.

      Use case resumes at step 2.

* 3c. The Group has hit max limit.

    * 3c1. T_Assistant shows an error message.

      Use case resumes at step 2.

* 3d. Student is in another Group.

    * 3d1. T_Assistant shows an error message.

      Use case resumes at step 2.

**Use case: Delete a Group**

**MSS**

1. User requests to list Groups.
2. T_Assistant shows a list of Groups.
3. User requests to delete a Group from the list.
4. T_Assistant deletes the Group from the list.

Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

* 3a. The Group parameters are invalid.

    * 3a1. T_Assistant shows an error message.

      Use case resumes at step 2.

**Use case: Add a Student to a Group**

**MSS**

1. User requests to list Students.
2. T_Assistant shows a list of Students.
3. User requests to add a Student to a Group.
4. T_Assistant adds the Student to the Group.

Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

* 3a. The Student/Group parameters are invalid.

    * 3a1. T_Assistant shows an error message.

      Use case resumes at step 2.

* 3b. The Student is already in a different Group.

    * 3b1. T_Assistant shows an error message.

      Use case resumes at step 2.

* 3b. The Student is already in a different Group.

    * 3a1. T_Assistant shows an error message.

      Use case resumes at step 2.

* 3c. The Group has hit max limit.

    * 3c1. T_Assistant shows an error message.

      Use case resumes at step 2.

**Use case: Mark Team's task as Complete**

**MSS**

1. User marks task as complete.
2. T_Assistant marks the task accordingly.

Use case ends.

**Extensions**

* 1a. The Group/Task parameters are invalid.

    * 1a1. T_Assistant shows an error message.

      Use case ends.

* 1b. The user marks an already complete task.

    * 1b1. T_Assistant shows an error message.

      Use case ends.

**Use case: Delete Student from Group**

**MSS**

1. User requests to list all students.
2. T_Assistant shows all students.
3. User deletes a student from a specified group.
4. T_Assistant adds student to the group.

Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

* 3a. The Student/Group parameters are invalid.

    * 3a1. T_Assistant shows an error message.

      Use case resumes at step 2.

* 3b. The Student is already in a different Group.

    * 3b1. T_Assistant shows an error message.

      Use case resumes at step 2.

**Use case: List all Groups**

**MSS**

1. User lists all groups.
2. T_Assistant displays all groups.

Use case ends.

**Extensions**

* 1a. There are currently no groups.

    * 1a1. T_Assistant shows an error message.

      Use case ends.

**Use case: List all Tasks**

**MSS**

1. User lists all tasks.
2. T_Assistant displays all groups.

Use case ends.

**Extensions**

* 1a. There are currently no tasks.

    * 1a1. T_Assistant shows an error message.

      Use case ends.

**Use case: Add Task to Group**

**MSS**

1. User adds a task to a specified group.
2. T_Assistant displays all current tasks for the group.

Use case ends.

**Extensions**

* 1a. The Group/Task parameters are invalid.

    * 1a1. T_Assistant shows an error message.

      Use case ends.

* 1b. A duplicate task is entered.

    * 1b1. T_Assistant informs user that the task already exists.

      Use case resumes at step 2.

**Use case: Delete Task from Group**

**MSS**

1. User removes a task from a specified group.
2. T_Assistant displays all current tasks for the group.

Use case ends.

**Extensions**

* 1a. The Group/Task parameters are invalid.

    * 1a1. T_Assistant shows an error message.

      Use case ends.

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should be able to hold up to 1000 students without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be
   able to accomplish most of the tasks faster using commands than using the mouse.
4. Should be for a single user only.
5. Data should be stored in a human editable text file.
6. Should not depend on any remote server.
7. Should be packaged into a single JAR file

*{More to be added}*

### Glossary

| Key Terms      | Definition                                                   |
|----------------|--------------------------------------------------------------|
| Mainstream OS  | Operating Systems (i.e. Windows, Linux, MacOS                |
| JAR            | Executable file containing Java classes and other resources. |
| Prefix         | Keyword used in commands to specify the parameter type       |
| Student Number | Unique identifier for a student                              |

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

    1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be
       optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a student

1. Deleting a student while all students are being shown

    1. Prerequisites: List all students using the `list` command. Multiple students in the list.

    1. Test case: `delete 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message.
       Timestamp in the status bar is updated.

    1. Test case: `delete 0`<br>
       Expected: No student is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

    1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Effort**

[To be updated.]

### Design Choices

### Challenges Faced

### Achievements

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

**Group size:** 4

**Total Enhancements:** x/8 (`2x4`)

[To be updated.]
