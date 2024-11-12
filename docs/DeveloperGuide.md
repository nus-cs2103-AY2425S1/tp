---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# T_Assistant Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

---

## **Acknowledgements**

- Codebase adapted from [AddressBook3](https://se-education.org/addressbook-level3/)
- Solution for Undo/Redo function adapted
  from [AddressBook3 Developer Guide](https://se-education.org/addressbook-level3/DeveloperGuide.html#proposed-undoredo-feature)
- Libraries
  used: [JavaFX](https://openjfx.io/), [Jackson](https://github.com/FasterXML/jackson), [JUnit5](https://github.com/junit-team/junit5)

---

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

---

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The **_Architecture Diagram_** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [
`Main`](https://github.com/AY2425S1-CS2103-F12-2/tp/tree/master/src/main/java/seedu/address/Main.java) and [
`MainApp`](https://github.com/AY2425S1-CS2103-F12-2/tp/tree/master/src/main/java/seedu/address/MainApp.java)) is in
charge of the app launch and shut down.

- At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
- At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

- [**`UI`**](#ui-component): The UI of the App.
- [**`Logic`**](#logic-component): The command executor.
- [**`Model`**](#model-component): Holds the data of the App in memory.
- [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The _Sequence Diagram_ below shows how the components interact with each other for the scenario where the user issues
the command `del_s sno/A0123456A`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

- defines its _API_ in an `interface` with the same name as the Component.
- implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API
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

- executes user commands using the `Logic` component.
- listens for changes to `Model` data so that the UI can be updated with the modified data.
- keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
- depends on some classes in the `Model` component, as it displays `Student`, `Group` and `Task` objects residing in the
  `Model`.

<div style="page-break-after: always;"></div>

### Logic component

**API** : [
`Logic.java`](https://github.com/AY2425S1-CS2103-F12-2/tp/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking
`execute("dg gn/CS2103-F12-2")` API
call as an example.

<puml src="diagrams/DeleteGroupSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `dg gn/CS2103-F12-2` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteGroupCommandParser`, `DeleteGroupCommand` and `CommandResult` should end at the
destroy marker (X) but due to a limitation of
PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates
   a parser that matches the command (e.g., `DeleteGroupCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteGroupCommand`)
   which
   is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a student).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take
   several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:

- When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a
  placeholder for the specific command name e.g., `AddStudentCommandParser`) which uses the other classes shown above to
  parse
  the user command and create a `XYZCommand` object (e.g., `AddStudentCommand`) which the `AddressBookParser` returns
  back as a
  `Command` object.
- All `XYZCommandParser` classes (e.g., `AddStudentCommandParser`, `DeleteStudentCommandParser`, ...) inherit from the
  `Parser`
  interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [
`Model.java`](https://github.com/AY2425S1-CS2103-F12-2/tp/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="800" />

The `Model` component,

- stores the address book data e.g., all `Student` objects (which are contained in a `UniqueStudentList` object).
- stores the currently 'selected' `Student` objects (e.g., results of a search query) as a separate _filtered_ list
  which
  is exposed to outsiders as an unmodifiable `ObservableList<Student>` that can be 'observed' e.g. the UI can be bound
  to
  this list so that the UI automatically updates when the data in the list change.
- the same is applied to `Group` and `Task` objects
- stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a
  `ReadOnlyUserPref` objects.
- does not depend on any of the other three components (as the `Model` represents data entities of the domain, they
  should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which
`Student` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each
`Student`
needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

#### Interaction between entities

<puml src="diagrams/EntityClassDiagram.puml" width="300" />

The interaction between our three entities - `Student`, `Group` and `Task` can be seen in the diagram above.

The diagram has been simplified by omitting their attributes.

### Storage component

**API** : [
`Storage.java`](https://github.com/AY2425S1-CS2103-F12-2/tp/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="800" />

The `Storage` component,

- can save both address book data and user preference data in JSON format, and read them back into corresponding
  objects.
- inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only
  the functionality of only one is needed).
- depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects
  that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

---

<div style="page-break-after: always;"></div>

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

<box type="info">

**Note:** For simplicity, certain details such as conditional checks, parsing
and more detailed implementation on model changes have been omitted.

</box>

---

### Add Student Feature

The `Add Student` feature allows users to add a new student in the address book given a student's student
number `sno`, email `e`, and name `sn`.

The following shows the activity diagram when the user executes the `add_s` command:
<puml src="diagrams/AddStudentActivityDiagram.puml" alt="AddStudentCommandAD" />

#### Usage

**Syntax:** `add_s/as sno/STUDENT_NUMBER sn/NAME e/EMAIL`

**Example:** `as sno/A0123456K sn/Bob Smith e/bobsmith@u.nus.edu`

#### Implementation details

1. The user executes `as sno/A0123456K sn/Bob Smith e/bobsmith@u.nus.edu` to add the student with student number
   `A0123456K`, name `Bob Smith`, and email `bobsmith@u.nus.edu`.
   The command is parsed in the `AddressBookParser`.
2. `AddStudentCommandParser` is created and gets the student number, name and email to create a Student object. The
   Student object is
   then used to construct an `AddStudentCommand` object.
3. The `AddStudentCommand` object then calls `addPerson(student)` in the `ModelManager` with the specified student
   to be added. This method adds the specified `Student` in the model.
4. Finally, the `AddStudentCommand` returns the `CommandResult`.

##### Note

This feature will also check if there already exists a Student with the same student number or email.

**Sequence Diagram:** The following sequence diagram shows how the above steps for add student works:

For readability, `as sno/A0123456K sn/Bob Smith e/bobsmith@u.nus.edu` has been replaced with `command` and
`sno/A0123456K sn/Bob Smith e/bobsmith@u.nus.edu` with `args`.
<puml src="diagrams/AddStudentSequenceDiagram.puml" alt="AddStudentCommandSD"/>

<box type="info" seamless>

**Note:** The lifelines for `AddStudentCommandParser`, `AddStudentCommand`, and
`CommandResult` should end at the destroy marker (X) but due to a limitation of
PlantUML, the lifeline continues till the end of diagram.

</box>

### Delete Student Feature

The `Delete Student` feature allows users to delete an existing student in the address book given a student's student
number `sno`.

The following shows the activity diagram when the user executes the `del_s` command:
<puml src="diagrams/DeleteStudentActivityDiagram.puml" alt="DeleteStudentCommandAD" />

#### Usage

**Syntax:** `del_s/ds sno/STUDENT_NUMBER`

**Example:** `ds sno/A0123456K`

#### Implementation details

1. User has the application launched with at least 1 student added.
2. User executes `ls` to view all students. For this example, the user wishes to delete a student with student number
   `A0234567H`.
3. The user executes `ds sno/A0234567H` to delete the student with a student number `A0234567H`. The command is parsed
   in the `AddressBookParser`.
4. `DeleteStudentCommandParser` is created and gets the student number of the student to be deleted. The student number
   is used to construct a `DeleteStudentCommand` object.
5. The `DeleteStudentCommand` object then calls `deletePerson(student)` in the `ModelManager` with the specified student
   to be
   deleted. This method deletes the specified `Student` in the model.
6. Finally, the `DeleteStudentCommand` returns the `CommandResult`.

##### Note

This feature will also check if the deleted `Student` belongs to any `Group` and remove the `Student` from that `Group`,
resetting the affected `Group` affiliation.

**Sequence Diagram:** The following sequence diagram shows how the above steps for delete student works:
<puml src="diagrams/DeleteStudentSequenceDiagram.puml" alt="DeleteStudentCommandSD"/>

<box type="info" seamless>

**Note:** The lifelines for `DeleteStudentCommandParser`, `DeleteStudentCommand`, and
`CommandResult` should end at the destroy marker (X) but due to a limitation of
PlantUML, the lifeline continues till the end of diagram.

</box>

#### Design considerations

**Aspect 1:** Usage of StudentNumber as identifier

1. **Design #1: Use StudentNumber (current choice)**

- Pro: More deliberate and since StudentNumber are more complex, the user will be more aware of their decision
- Con: More typing is required

2. **Design #2:** Use Index

- Pro: Easy and quick
- Con: Possible for user to mistype the wrong number

---

<div style="page-break-after: always;"></div>

### Delete Group feature

The `Delete Group` feature allows users to delete an existing group in the address book given a group's name.

The following shows the activity diagram when the user executes the `del_g` command:
<puml src="diagrams/DeleteGroupActivityDiagram.puml" alt="DeleteGroupCommandAD" />

#### Usage

**Syntax:** `del_g/dg gn/GROUP_NAME`

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
6. Finally, the `DeleteGroupCommand` returns the `CommandResult`.

##### Note

This feature will also remove `Students` in the `Group` and reset their `Group`, and delete all `Tasks` related to the
`Group`, but the details are omitted.

#### Sequence diagram

The following sequence diagram shows how the above steps for delete group works:
<puml src="diagrams/DeleteGroupSequenceDiagram.puml" alt="DeleteGroupCommand"/>

<box type="info" seamless>

**Note:** The lifelines for `DeleteGroupCommandParser`, `DeleteGroupCommand` and `CommandResult` should end at the
destroy marker (X) but due to a limitation of
PlantUML, the lifeline continues till the end of diagram.
</box>

#### Design considerations

**Aspect 1:** Usage of GroupName as identifier

1. **Design #1: Use GroupName (current choice)**

- Pro: More deliberate and since GroupNames are more complex, the user will be more aware of their decision
- Con: Must type a lot

2. **Design #2:** Use Index

- Pro: Easy and quick
- Con: Possible for user to mistype the wrong number

---

<div style="page-break-after: always;"></div>

### Edit Task for Group feature

The `Edit Task for Group` feature allows users to edit the properties of a specific task within a group, given the
group's name and the task index in
the specified group's task list.

The following shows the activity diagram when the user executes the `edit_t_g` command:
<puml src="diagrams/EditTaskCommandAD.puml" alt="EdiTaskAD" />

#### Usage

**Syntax:** `edit_t_g/etg gn/GROUP_NAME i/INDEX [tn/TASK_NAME] [td/TASK_DEADLINE]`

**Example:** `edit_t_g gn/CS2103-F12-2 i/1 td/2024-12-12 1800`

#### Implementation details

1. User has the application launched with at least 1 group added and at least 1 task added to that group.
2. User executes `lt gn/GROUP_NAME` to view the group's task list. For this example, the user wishes to edit the first
   task for `CS2103-F12-2`.
3. The user executes `edit_t_g gn/CS2103-F12-2 i/1 td/2024-12-12 1800` to edit the task's deadline to `2024-12-12 1800`.
   The command is parsed in the `AddressBookParser`.
4. `EditTaskCommandParser` is created and gets the group name and task index of the task to be edited. The group name
   and task index is used to
   construct a `EditTaskCommand` object.
5. The `EditTaskCommand` object then calls `model.setTask(taskToEdit, editedTask, group)` in the `ModelManager` with the
   specified group's name, task to be
   edited, and the edited task. This method edits the specified `Task` in the model.
6. Finally, the `EditTaskCommand` returns the `CommandResult`.

#### Sequence diagram

The following sequence diagram shows how the above steps for delete group works:
<puml src="diagrams/EditTaskCommandSequence.puml" alt="EditTaskCommand"/>

<box type="info" seamless>

**Note:** The lifelines for `EditTaskCommandParser`, `CommandResult`, and `EditTaskCommand` should end at the destroy
marker (X) but due to a limitation of
PlantUML, the lifeline continues till the end of diagram.
</box>

### Undo/redo feature

The undo/redo mechanism is facilitated by `VersionHistory`. It stores an ArrayList `versions` of ReadOnlyAddressBook.

Whenever there are changes made to the AddressBook, a defensive copy of the AddressBook is created and stored in the
ArrayList. `VersionHistory` also stores a pointer to the current version of the addressbook.

If newly initialized, this pointer is set to -1. We have set a maximum number of versions to be able to stored at 100.
Once this limit is reached, the earliest entry in the ArrayList will be deleted by `VersionHistory` so that a new
version can be stored.
Additionally, it implements the following operations:

- `VersionHistory#addVersion(Model model)`— Saves the current state to its history.
- `VersionedAddressBook#undoVersion()`— Restores the previous version from its history.
- `VersionedAddressBook#redoVersion()`— Restores a previously undone version from its history.

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

<box type="info" seamless>

**Note:** The lifelines for `UndoCommandParser`, `UndoCommand`, and
`CommandResult`, and `AddressBook` should end at the destroy marker (X) but due to a limitation of
PlantUML, the lifeline continues till the end of diagram.

</box>

---

## **Documentation, logging, testing, configuration, dev-ops**

- [Documentation guide](Documentation.md)
- [Testing guide](Testing.md)
- [Logging guide](Logging.md)
- [Configuration guide](Configuration.md)
- [DevOps guide](DevOps.md)

---

<div style="page-break-after: always;"></div>

## **Appendix: Requirements**

### Product scope

**Target user profile**:

- CS2103 tutor
- has a need to manage a significant number of students
- prefer desktop apps over other types
- can type fast
- prefers typing to mouse interactions
- is reasonably comfortable using CLI apps

**Value proposition**:

- Manage contacts faster than a typical mouse/GUI driven app
- Helps to track the following:
    - Students
    - Their groups
    - Groups’ progress
    - TA will create Groups and assign tasks
        - Mark the tasks as the groups complete them

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

<div style="page-break-after: always;"></div>

### Use cases

(For all use cases below, the **System** is the `T_Assistant` and the **Actor** is the `user`, unless specified
otherwise)

**Use case: List Students**

**MSS**

1. User requests to list students.
2. T_Assistant shows a list of students.

Use case ends.

**Extensions**

- 1a. The list is empty.

  Use case ends.

**Use case: Add a Student**

**MSS**

1. User requests to list students.
2. T_Assistant shows a list of students.
3. User requests to add a new Student into the list.
4. T_Assistant adds the new Student to the list.

Use case ends.

**Extensions**

- 3a. The Student parameters are invalid.

    - 3a1. T_Assistant shows an error message.

      Use case resumes at step 2.

- 3b. The Student already exists.

    - 3b1. T_Assistant shows an error message.

      Use case resumes at step 2.

**Use case: Delete a Student**

**MSS**

1. User requests to list students.
2. T_Assistant shows a list of students.
3. User requests to delete a Student from the list.
4. T_Assistant deletes the Student from the list.

Use case ends.

**Extensions**

- 1a. The list is empty.

  Use case ends.

- 3a. The Student parameters are invalid.

    - 3a1. T_Assistant shows an error message.

      Use case resumes at step 2.

- 3b. The selected Student does not exist.

    - 3b1. T_Assistant shows an error message.

      Use case resumes at step 2.

**Use case: Edit a Student**

**MSS**

1. User requests to list students.
2. T_Assistant shows a list of students.
3. User requests to edit a Student's information from the list.
4. T_Assistant updates the Student's information as per user's input.

**Extensions**

- 1a. The list is empty.

  Use case ends.

- 3a. The selected Student does not exist.

    - 3a1. T_Assistant shows an error message.

      Use case resumes at step 2.

- 3b. The input Student parameters to edit are invalid.

    - 3b1. T_Assistant shows an error message.

      Use case resumes at step 2.

- 3c. No changes are made to the Student’s information.

    - 3c1. T_Assistant shows an error message.

      Use case resumes at step 2.

**Use case: Find Student**

**MSS**

1. User requests to find students with specific keywords.
2. T_Assistant processes the input and searches for students matching the keywords.
3. T_Assistant displays a list of students who match the search criteria.

Use case ends.

**Extensions**

- 1a. The input format is incorrect or missing keywords.

    - 1a1. T_Assistant shows an error message indicating the correct format.

      Use case ends.

**Use case: Sort Students**

**MSS**

1. User requests to list students.
2. T_Assistant shows a list of students.
3. User requests to sort students.
4. T_Assistant sorts students in ASCII order.

Use case ends.

**Extensions**

- 1a. The list is empty.

  Use case ends.

**Use case: Add a Group**

**MSS**

1. User requests to list groups.
2. T_Assistant shows a list of groups.
3. User requests to add a new Group into the list.
4. T_Assistant adds the new Group to the list.

Use case ends.

**Extensions**

- 3a. The Group parameters are invalid.

    - 3a1. T_Assistant shows an error message.

      Use case resumes at step 2.

- 3b. The Group already exists.

    - 3b1. T_Assistant shows an error message.

      Use case resumes at step 2.

**Use case: Delete a Group**

**MSS**

1. User requests to list Groups.
2. T_Assistant shows a list of Groups.
3. User requests to delete a Group from the list.
4. T_Assistant deletes the Group from the list.

Use case ends.

**Extensions**

- 1a. The list is empty.

  Use case ends.

- 3a. The Group parameters are invalid.

    - 3a1. T_Assistant shows an error message.

      Use case resumes at step 2.

- 3b. The selected Group does not exist.

    - 3b1. T_Assistant shows an error message.

      Use case resumes at step 2.

**Use case: Edit a Group**

**MSS**

1. User requests to list groups.
2. T_Assistant shows a list of groups.
3. User requests to edit a Group's information from the list.
4. T_Assistant updates the Group's information as per user's input.

Use case ends.

**Extensions**

- 1a. The list is empty.

  Use case ends.

- 3a. The selected Group does not exist.

    - 3a1. T_Assistant shows an error message.

      Use case resumes at step 2.

- 3b. The input Group parameters to edit are invalid.

    - 3b1. T_Assistant shows an error message.

      Use case resumes at step 2.

- 3c. No changes are made to the Group’s information.

    - 3c1. T_Assistant shows an error message.

      Use case resumes at step 2.

**Use case: Add a Student to a Group**

**MSS**

1. User requests to list Students.
2. T_Assistant shows a list of Students.
3. User requests to add a Student to a Group.
4. T_Assistant adds the Student to the Group.

Use case ends.

**Extensions**

- 1a. The list is empty.

  Use case ends.

- 3a. The Student/Group parameters are invalid.

    - 3a1. T_Assistant shows an error message.

      Use case resumes at step 2.

- 3b. The Student is already in a different Group.

    - 3b1. T_Assistant shows an error message.

      Use case resumes at step 2.

- 3c. The Group has hit max limit.

    - 3c1. T_Assistant shows an error message.

      Use case resumes at step 2.

**Use case: Delete Student from Group**

**MSS**

1. User requests to list all students.
2. T_Assistant shows all students.
3. User deletes a student from a specified group.
4. T_Assistant adds student to the group.

Use case ends.

**Extensions**

- 1a. The list is empty.

  Use case ends.

- 3a. The Student/Group parameters are invalid.

    - 3a1. T_Assistant shows an error message.

      Use case resumes at step 2.

- 3b. The selected Student does not exist.

    - 3b1. T_Assistant shows an error message.

      Use case resumes at step 2.

**Use case: List all Groups**

**MSS**

1. User requests to list all groups.
2. T_Assistant shows a list of all groups.

Use case ends.

**Extensions**

- 1a. The list is empty.

  Use case ends.

**Use case: Find Group**

**MSS**

1. User requests to find groups with specific keywords
2. T_Assistant processes the input and searches for groups matching the keywords.
3. T_Assistant displays a list of groups who match the search criteria.

Use case ends.

**Extensions**

- 1a. The input format is incorrect or missing keywords.

    - 1a1. T_Assistant shows an error message indicating the correct format.

      Use case ends.

**Use case: Sort Groups**

**MSS**

1. User requests to list all groups.
2. T_Assistant displays all groups.
3. User requests to sort groups.
4. T_Assistant sorts groups by ASCII order.

Use case ends.

**Extensions**

- 1a. The list is empty.

  Use case ends.

**Use case: List all Tasks**

**MSS**

1. User requests to list all tasks.
2. T_Assistant shows a list of all tasks.

Use case ends.

**Extensions**

- 1a. The list is empty.

  Use case ends.

**Use case: Add Task to Group**

**MSS**

1. User requests to list tasks of a specified group.
2. T_Assistant displays all current tasks for the group.
3. User requests to add task to group.
4. T_Assistant adds task to group.

Use case ends.

**Extensions**

- 1a. The Group/Task parameters are invalid.

    - 1a1. T_Assistant shows an error message.

      Use case ends.

- 1a. The list is empty.

  Use case ends.

- 3b. A duplicate task is entered.

    - 3b1. T_Assistant informs user that the task already exists.

      Use case resumes at step 2.

**Use case: Delete Task from Group**

**MSS**

1. User requests to remove a task from a specified group.
2. T_Assistant removes a task from the group displays all current tasks for the group.

Use case ends.

**Extensions**

- 1a. The Group/Task parameters are invalid.

    - 1a1. T_Assistant shows an error message.

      Use case ends.

**Use case: Edit a Task for all Groups having the task**

**MSS**

1. User requests to list tasks.
2. T_Assistant shows a list of tasks.
3. User requests to edit a Task's information from the list.
4. T_Assistant updates the Task's information as per user's input.

Use case ends.

**Extensions**

- 1a. The list is empty.

  Use case ends.

- 3a. The selected Task does not exist.

    - 3a1. T_Assistant shows an error message.

      Use case resumes at step 2.

- 3b. The input Task parameters to edit are invalid.

    - 3b1. T_Assistant shows an error message.

      Use case resumes at step 2.

- 3c. No changes are made to the Task’s information.

    - 3c1. T_Assistant shows an error message.

      Use case resumes at step 2.

**Use case: Edit a Task for a Group**

**MSS**

1. User requests to list a group's tasks.
2. T_Assistant shows a list of tasks.
3. User requests to edit a Task's information from the list.
4. T_Assistant updates the Task's information as per user's input.

Use case ends.

**Extensions**

- 1a. The list is empty.

  Use case ends.

- 3a. The selected Task does not exist.

    - 3a1. T_Assistant shows an error message.

      Use case resumes at step 2.

- 3b. The input Task parameters to edit are invalid.

    - 3b1. T_Assistant shows an error message.

      Use case resumes at step 2.

- 3c. No changes are made to the Task’s information.

    - 3c1. T_Assistant shows an error message.

      Use case resumes at step 2.

**Use case: Mark Group's Task as Complete**

**MSS**

1. User requests to list a group's tasks.
2. T_Assistant shows a list of tasks.
3. User requests to mark task.
4. T_Assistant marks the task accordingly.

Use case ends.

**Extensions**

- 1a. The list is empty.

  Use case ends.

- 2a. The Group/Task parameters are invalid.

    - 2a1. T_Assistant shows an error message.

      Use case ends.

**Use case: Find Task**

**MSS**

1. User requests to find tasks with specific keywords.
2. T_Assistant processes the input and searches for tasks matching the keywords.
3. T_Assistant displays a list of tasks who match the search criteria.

Use case ends.

**Extensions**

- 1a. The input format is incorrect or missing keywords.

    - 1a1. T_Assistant shows an error message indicating the correct format.

      Use case ends.

**Use case: Sort Tasks**

**MSS**

1. User requests to list all tasks.
2. T_Assistant displays all tasks.
3. User requests to sort tasks.
4. T_Assistant sorts tasks by order of the deadline, from earliest to latest.

Use case ends.

**Extensions**

- 1a. There are currently no tasks.

Use case ends.

**Use case: Undo**

**MSS**

1. User requests to undo.
2. T_Assistant undoes previously executed command.

Use case ends.

**Extensions**

- 1a. There is nothing to undo.

    - 1a1. T_Assistant shows an error message.

      Use case ends.

**Use case: Redo**

**MSS**

1. User requests to redo.
2. T_Assistant redoes previously executed undo command.

Use case ends.

**Extensions**

- 1a. There was no previously executed undo command.

    - 1a1. T_Assistant shows an error message.

      Use case ends.

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should be able to hold up to 500 students, 100 groups and 100 tasks without a noticeable sluggishness in performance
   for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be
   able to accomplish most of the tasks faster using commands than using the mouse.
4. Should be for a single user only.
5. Data should be stored in a human editable text file.
6. Should be able to store up to 100 version histories.
7. Should not depend on any remote server.
8. Should be packaged into a single JAR file

### Glossary

| Key Terms      | Definition                                                   |
|----------------|--------------------------------------------------------------|
| Mainstream OS  | Operating Systems (i.e. Windows, Linux, MacOS                |
| JAR            | Executable file containing Java classes and other resources. |
| Prefix         | Keyword used in commands to specify the parameter type       |
| Student Number | Unique identifier for a student                              |

---

<div style="page-break-after: always;"></div>

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more _exploratory_ testing.

</box>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    2. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be
       optimum.

2. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Finding a student

1. Find a student by querying name

    1. Prerequisites: For this test, we shall use one of the groups provided by the sample data. Hence, you should do
       this test on a freshly opened T_Assistant

    2. Test case: `fs q/Alex Ye`<br>
       Expected: T_Assistant displays `Alex Yeoh` only.

### Adding a student

1. Adding a student while all students are being shown

    1. Prerequisites: List all students using the `ls` command. Start from a fresh T_Assistant model.

    2. Test case: `add_s sno/A0123456X sn/John Doe e/e0000000@u.nus.edu`<br>
       Expected: The student with the given name, email, and student number is added into the student list and updated.

    3. Test case: `add_s sno/A0123456X sn/John Doe e/e0000000@u.nus.edu`<br>
       Expected: Student already exists, hence an error message will be displayed.

    4. Test case: `add_s sno/A0000000X`<br>
       Expected: No student is added. The student list remains the same.

    5. Other incorrect add commands to try: `add_s sno/helloworld`,
       `add_s sno/helloworld sn/helloworld e/helloworld`,
       `...` (incorrect inputs, missing inputs, or incorrect prefixes used, or adding a student with the same student
       number or email as an existing student)<br>
       Expected: Similar to previous.

1. Adding a student while the application is on another panel other than the student list

    1. Prerequisites: List groups using `lg` command or list tasks using `lt` command.

    2. Test case: `add_s sno/A0000000X sn/Alice e/e0000001@u.nus.edu`<br>
       Expected: The student with the given name, email, and student number is added into the student list and updated.
       The application will automatically jump to the student list panel.

    3. Repeating the other test cases as stated above will give the same results.

### Deleting a student

1. Deleting a student while all students are being shown

    1. Prerequisites: List all students using the `ls` command. Multiple students in the list. Start from a fresh
       T_Assistant model.

    2. Test case: `ds sno/A0737935G`<br>
       Expected: The student with the corresponding student number is deleted from the student list. If you are testing
       this using the sample data given, this student was in the group `CS2103-F12-1`. Using the `lg` command, observe
       that the group no longer contains this student.

    3. Test case: `ds sno/A0737935G`<br> (This test case is to be done after the above test case has been executed.)
       Expected: After doing the above step, repeating this command again will yield an error message.

    4. Test case: `ds sno/helloworld`<br>
       Expected: This will yield an error message for not following the student number format.

    5. Test case: `ds`<br>
       Expected: This will yield an error message for not following the command format.

    6. Other incorrect delete commands to try: `ds A0000000X`, `...` (incorrect inputs, missing inputs, or incorrect
       prefixes used)<br>
       Expected: Similar to previous.

2. Deleting a student while the application is on another panel other than the student list

    1. Prerequisites: List groups using `lg` command or list tasks using `lt` command. Start from a fresh T_Assistant
       model.

    2. Test case: `ds sno/A0597991H`<br>
       Expected: The student with the corresponding student number is deleted from the student list. The application
       will
       automatically jump to the student list. If you are testing this using the sample data given, this student was in
       the group `CS2103-F12-1`. Using the `lg` command, observe that the group no longer contains this student.

    3. Repeating the other test cases as stated above will give the same results.

### Editing a student

1. Edit an existing student's name

    1. Prerequisites: List students using `ls`. Have at least 1 student in the list and name is not
       `Clark Kent`.

    2. Test case: `es i/1 sn/Clark Kent`<br>
       Expected: Student Name is updated to Clark Kent

2. Editing an existing student's name to the same name

    1. Prerequisites: Ran the above test case.

    2. Test case: `es i/1 sn/Clark Kent`<br>
       Expected: Error given due to no change detected.

### Adding a student(s) to a group

1. Adding a single student into a group

    1. Prerequisites: Group must have less than 5 students currently. If the group has 5 students already then executing
       the command will yield an error message. The student must not also already be in a group, if not another error
       message will be displayed.

        1. Execute command `add_s sno/A0654321X sn/Jack e/e0000002@u.nus.edu` to add a new student
           into the application.
        2. Execute command `add_s sno/A0111111X sn/John e/e0000003@u.nus.edu` to add another student
           into the application.
        3. Execute command `add_s sno/A0222222X sn/John e/e0000004@u.nus.edu` to add
           another student into the application.
        4. Lastly, execute command `add_s sno/A0333333X sn/Jill e/e0000005@u.nus.edu`
           to add another student into the application.

    2. Test case: `asg sno/A0654321X gn/cs2103-f12-1`<br>
       Expected: The student with the corresponding student number is added into the group.

    3. Test case: `asg sno/A0654321X gn/cs2103-f12-1`<br> (Add the same student into the group again)
       Expected: After doing the above step, repeating this command again will yield an error message.

    4. Test case: `asg sno/A0654321X gn/cs2103-f11-1`<br>
       Expected: This will yield an error message as the student is already in another group.

2. Adding multiple students into a single group

    1. Prerequisites: Same as stated above.

    2. Test case: `asg sno/A0222222X sno/A0111111X gn/cs2103-f11-1`<br>
       Expected: Both students are added into the group list and you will see the group being updated with the new
       additions.

    3. Test case: `asg sno/A0333333X sno/A0333333X sno/A0111111X gn/cs2103-f12-1`<br>
       Expected: Only the first student is added into the group as the other student already belongs to a group. This
       will be reflected to you via a warning message. Also, observe that two instances of the first student are entered
       as inputs. This will be ignored by the application. Only one instance of the duplicated input is added into the
       group. You will see a warning message regarding duplicates here too.

    4. Test case: `asg sno/A0333333X sno/A0333333X sno/A0111111X gn/cs2103-f12-1`<br>
       Expected: An error message will be yielded because both students already belong to a group each.

    5. Test case: `asg sno/A0999999X sno/A0333333X sno/A0454545X gn/cs2103-f12-1`<br>
       Expected: An error message will be yielded because both student numbers do not exist in the application.

    6. Test case: `asg sno/A0333333X sno/A0333333X sno/A0333333X gn/cs2103-f11-1`<br>
       Expected: An error message will be thrown as the student already belongs to a group. Here, observe that the
       duplicated
       instance does not matter at all.

    7. Other incorrect commands to try: Try mixing around different invalid inputs. The application will handle these
       via warning messages, and at times, error messages.

### Deleting a student from a group

1. It does not matter which panel you are currently at. The previous few examples were to illustrate our application's
   ability to switch panels depending on which command is executed.

    1. Test case: `dsg sno/A0737935G`<br>
       Expected: The student with the corresponding student number will be deleted from the group. The application will
       jump to the group list panel and reflect this change accordingly.

    2. Test case: `dsg sno/A0737935G`<br> (This test case is to be done after the above test case has been executed.)
       Expected: After doing the above step, repeating this command again will yield an error message as the student no
       longer belongs to a group.

    3. Other incorrect delete commands to try: `dsg A0000000X`, `dsg`, `dsg sno/`, `...` (incorrect student number
       format,
       student does not exist, missing inputs, or incorrect prefixes used)<br>
       Expected: Similar to previous.

### Finding a group

1. Find a group by tutorial group

    1. Prerequisites: For this test, we shall use one of the groups provided by the sample data. Hence, you should do
       this test on a freshly opened T_Assistant

    2. Test case: `fs q/F12`<br>
       Expected: T_Assistant displays `CS2103-F12-1` only.

### Adding a group

1. Adding a single group into the application.

    1. Prerequisites: The group must not currently exist.

    2. Test case: `ag gn/cs2103-f20-1`<br>
       Expected: The group is added into the group list.

    3. Test case: `ag gn/cs2103-f20-1`<br> (Add the same group again)
       Expected: After doing the above step, repeating this command again will yield an error message.

    4. Test case: `ag gn/Team 1`<br>
       Expected: This will yield an error message as the group name format is wrong.

    5. Other incorrect commands to try: `ag`, `ag gn/`, `...` (incorrect command format, incorrect prefixes etc.)<br>
       Expected: Similar to previous inputs.

2. Adding multiple groups into the application.

    1. Prerequisites: Same as stated above.

    2. Test case: `ag gn/cs2103-f21-1 gn/cs2103-f21-2`<br>
       Expected: Both groups are added into the group list and this change is reflected to the user.

    3. Test case: `ag gn/cs2103-f21-3 gn/cs2103-f21-2`<br>
       Expected: Only the first group will be added into the application as the second group already exists. You will
       see
       this change reflected along with the warning message.

    4. Test case: `ag gn/helloworld gn/cs2103-f30-2`<br>
       Expected: An error message will be displayed because the first group name provided is invalid. Hence, no groups
       will be added.

    5. Test case: `ag gn/cs2103-f15-1 gn/cs2103-f15-1 gn/cs2103-f15-2`<br>
       Expected: Here, observe that the first two instances of group inputs are duplicates. In such a case, the
       application
       ignores the duplicated instance and adds one instance into its group data, displaying a warning message regarding
       duplicated groups. The other group is also added into the application, and the result of this change is shown to
       you
       in the group list.

    6. Test case: `ag gn/cs2103-f16-1 gn/cs2103-f16-1 gn/cs2103-f15-2` (Execute this command only after the previous one
       has been executed)
       Expected: Here, observe that the first two instances of group inputs are duplicates. In such a case, the
       application
       ignores the duplicated instance and adds one instance into its group data, displaying a warning message regarding
       duplicated groups. The other group also cannot be added as it is already in the application. This will be
       reflected
       as a warning message to you.<br>

    7. Test case: `ag gn/cs2103-f16-1 gn/cs2103-f16-1 gn/cs2103-f15-2`<br> (Execute this command only after the previous
       one has been executed)
       Expected: An error message will be yielded as all groups already exist in the model.

    8. Other incorrect commands to try: Try mixing around different invalid inputs. The application will handle these
       via
       warning messages, and at times, error messages.

### Deleting a group

1. Deleting a group without any student in it.

    1. Prerequisites: Execute the command `ag gn/cs2103-f21-1`.

    1. Test case: `dg gn/cs2103-f21-1`<br>
       Expected: The group will be deleted from the application and this change will be reflected to you in the group
       list.
       In this example, 0 students will be affected as no students have been added into the group.

    1. Test case: `dg gn/cs2103-f21-1`<br> (This test case is to be done after the above test case has been executed.)
       Expected: After doing the above step, repeating this command again will yield an error message.

    1. Test case: dg gn/helloworld`<br>
       Expected: This will yield an error message for not following the group name format.

    1. Test case: `dg`<br>
       Expected: This will yield an error message for not following the command format.

    1. Other incorrect delete commands to try: `dg cs2103-f21-1`, `...` (incorrect inputs, missing inputs, or incorrect
       prefixes used)<br>
       Expected: Similar to previous.<br>

2. Deleting a group with students in it.

    1. Prerequisites: For this test, we shall use one of the groups provided by the sample data. Hence, you should do
       this
       test on a freshly open T_Assistant.

    2. Test case: `dg gn/cs2103-f12-1`<br>
       Expected: The group will be deleted from the application and this change will be reflected to you in the group
       list.
       In this example, 3 students will be affected as no students have been added into the group. They will no longer
       be part of any group and can be added to any other existing group.

    3. Other incorrect delete commands to try: `dg cs2103-f12-1`, `...` (incorrect inputs, missing inputs, or incorrect
       prefixes used)<br>
       Expected: Similar to previous.<br>

### Editing a group

1. Editing a group's name

    1. Prerequisites: For this test, we shall use one of the groups provided by the sample data. Hence, you should do
       this test on a freshly opened T_Assistant. Run `lg` to see list of groups.

    2. Test case: `eg i/1 gn/CS2103-F12-2`<br>
       Expected: `CS2103-F12-1` edited to `CS2130-F12-2`

### Finding a task

1. Find a task by task name

    1. Prerequisites: For this test, we shall use one of the groups provided by the sample data. Hence, you should do
       this test on a freshly opened T_Assistant

    2. Test case: `ft q/post`<br>
       Expected: T_Assistant displays `Add postmortem to team docs` only.

### Adding a task to a group

1. Adding a task to a single group

    1. Test case: `atg gn/cs2103-f12-1 tn/test td/2024-09-09 1900`<br>
       Expected: The task with the corresponding name and deadline is added to the group.

    1. Test case: `atg gn/cs2103-f12-1 tn/test td/2024-09-09 1900` (Execute this command after executing the above
       test)<br>
       Expected: After doing the above step, repeating this command again will yield an error message because the task
       already exists.

    1. Test case: `atg gn/cs2103-f50-1 tn/test td/2024-09-09 1900`<br>
       Expected: This will yield an error message because the group does not exist.

    1. Other incorrect commands to try: Try inputs containing invalid group names and/or task deadlines. Error messages
       should be displayed to you.

1. Adding a task to multiple groups

    1. Prerequisites: Add a few dummy groups into the application by executing the command:
       `ag gn/cs2103-f19-1`

    1. Test case: `atg gn/cs2103-f12-1 gn/cs2103-f11-1 tn/tasktest td/2024-09-09 1900`<br>
       Expected: The task with the corresponding name and deadline is added to both groups.

    1. Test case: `atg gn/cs2103-f12-1 gn/cs2103-f19-1 tn/tasktest td/2024-09-09 1900`<br>
       Expected: There will be a warning message shown to you because `CS2103-F12-1` already has the task. The system
       will add the task to the other group.

    1. Test case: `atg gn/cs2103-f12-1 gn/cs2103-f12-1 gn/cs2103-f19-1 tn/helloworld td/2024-09-09 1900`<br>
       Expected: There will be a warning message shown to you because `CS2103-F12-1` is entered twice. However, the
       system will ignore this duplicated instance and add the task into both groups that are entered.

    1. Test case: `atg gn/cs2103-f11-1 gn cs2103-f11-1 gn/cs2103-f19-1 tn/helloworld td/2024-09-09 1900` (Execute this
       command after executing the command above)<br>
       Expected: There will be an error message because the task already exists.

    1. Test case: `atg gn/cs2103-f100-1 gn cs2103-f100-1 gn/cs2103-f101-1 tn/helloworld td/2024-09-09 1900`<br>
       Expected: Notice the duplicated instance of the group `CS2103-F100-1`. However, in this instance the duplication
       does not matter as the groups entered both do not exist. This is a more dire mistake, hence an error message will
       be displayed.

    1. Other incorrect commands to try: Try inputs containing invalid group names, task names, and/or task deadlines.
       Try also to mix different invalid inputs to trigger errors/warnings.

### Adding an existing task to a group

1. Adding an existing task to a single group

    1. Test case: `aetg gn/cs2103-f12-1 i/1`<br>
       Expected: The task with the corresponding name and deadline is added to the group.

    1. Test case: `aetg gn/cs2103-f12-1 i/1` (Execute this command after executing the above test)<br>
       Expected: After doing the above step, repeating this command again will yield an error message because the task
       has already been added to the group.

    1. Test case: `aetg gn/cs2103-f50-1 i/100`<br>
       Expected: This will yield an error message because the task does not exist.

    1. Other incorrect commands to try: Try inputs containing invalid group names and/or task deadlines. Error messages
       should be displayed to you.

1. Adding an existing task to multiple groups

    1. Test case: `aetg gn/cs2103-f12-1 gn/cs2103-f11-1 i/1`<br>
       Expected: The task with the corresponding name and deadline is added to both groups.

    1. Test case: `aetg gn/cs2103-f12-1 gn/cs2103-f19-1 i/1`<br>
       Expected: There will be a warning message shown to you because `CS2103-F12-1` already has the task. The system
       will add the task to the other group.

    1. Test case: `atg gn/cs2103-f12-1 gn/cs2103-f12-1 gn/cs2103-f19-1 i/2`<br>
       Expected: There will be a warning message shown to you because `CS2103-F12-1` is entered twice. However, the
       system will ignore this duplicated instance and add the task into both groups that are entered.

    1. Test case: `atg gn/cs2103-f11-1 gn cs2103-f11-1 gn/cs2103-f19-1 i/2` (Execute this
       command after executing the command above)<br>
       Expected: There will be a warning message for the duplicated instance of `CS2103-F11-1` entered, as well as a
       warning message for the `CS2103-F19-1` because it already contains the task. The system will ignore the
       duplicated
       instance and add the task to `CS2103-F11-1` only.

### Adding a task to all groups

1. Adding an existing task

    1. Prerequisites: Execute command `atg gn/cs2103-f12-1 tn/dummytest td/2024-09-09 1900`, which adds the given task
       into the group.

    1. Test case: `at tn/dummytest td/2024-09-09 1900`<br>
       Expected: The given task will be added into all groups. The system will reflect this change to you on the task
       list.

    1. Test case: `at tn/dummytest` or `at td/2024-09-09 1900`<br>
       Expected: This will yield an error message because of the invalid command format.

    1. Test case: `at td/2024-09-09`<br>
       Expected: This will yield an error message because of the invalid deadline format.

1. Adding a new task
    1. Test case: `at tn/dummytest2 td/2024-09-09 1900`<br>
       Expected: The given task will be added into all groups. The system will reflect this change to you on the task
       list.

### Deleting a task from all groups

1. Deleting task from all groups which have this task

    1. Prerequisites: This command works either for tasks which all groups have, or tasks which all groups have. In any
       case, the specified task will be removed from the application and all the groups that have it. To undergo this
       test, we will start with the sample data. Execute command `at tn/newTaskTest td/2024-09-09 1900`.<br>

    1. Test case: `dt i/1`<br>
       Expected: The given task will be deleted from all groups which have it. The task will also be deleted from the
       task list.

    1. Test case: `dt i/2` (Execute this command after the first test case above has been executed)<br>
       Expected: This command will delete the newly added dummy task created as a prerequisite. It will have the same
       behavior as the above test case.

    1. Test case: `dt i/100`<br>
       Expected: There will be an error message displayed to the user because the task index provided is invalid.

    1. Other incorrect commands to try: `dt`, `dt tn/...`, and any other commands with incorrect format or prefixes.
       Expected: Similar to previous

### Deleting a task from a single group

1. Deleting task from a single group.

    1. Prerequisites: We will work with the sample data given.

    1. Test case: `dtg gn/cs2103-f12-1 i/1`<br>
       Expected: The specified task given by the index will be deleted from the group. This change will be reflected in
       the group task list.

    1. Test case: `dtg gn/cs2103-f12-1 i/100`<br>
       Expected: This command will yield an error message because of the invalid task index.

    1. Test case: `dtg i/100`<br>
       Expected: This command will yield an error message displayed to the user because the group name is not specified.

    1. Test case: `dtg gn/cs2103-f12-1`<br>
       Expected: This command will yield an error message displayed to the user because the task index is not specified.

    1. Other incorrect commands to try: Any other commands with incorrect format or prefixes.
       Expected: Similar to previous

### Editing a task

1. Editing name for a group's task

    1. Prerequisites: For this test, we shall use one of the groups provided by the sample data. Hence, you should do
       this test on a freshly opened T_Assistant. Run `lt gn/CS2103-F12-1` to see list of tasks for `CS2103-F12-1`.

    2. Test case: `etg i/1 gn/CS2103-F12-1 tn/Add postmortem to team docs and report`<br>
       Expected: `Add postmortem to team docs` edited to `Add postmortem to team docs and report`

### Marking a task

1. Marking a group's task

    1. Prerequisites: For this test, we shall use one of the groups provided by the sample data. Hence, you should do
       this test on a freshly opened T_Assistant. Run `lt gn/CS2103-F12-1` to see list of tasks for `CS2103-F12-1`.

    2. Test case: `mt i/1 gn/CS2103-F12-1`<br>
       Expected: Task at index 1 for `CS2103-F12-1` is updated to `COMPLETED`

### Undo

1. Undoes a previously executed command.

    1. Prerequisites: We will work with the sample data given. It is important to open a fresh T_Assistant application.

    1. Test case: `undo`<br>
       Expected: This command will yield an error message because there is nothing to undo.

    1. Test case: `ag cs2103-f13-1`, then `undo`<br>
       Expected: The add command will add a group which is reflected in the group list. The undo command then restores
       the previous data, removing this group. The removal is also reflected in the group list shown to you. After
       executing the command you will be brought back to the default panel of the application, which is the panel
       displaying the student list.

### Redo

1. Redoes a previously undone command.

    1. Prerequisites: We will work with the sample data given. It is important to open a fresh T_Assistant application.

    1. Test case: `redo`<br>
       Expected: This command will yield an error message because there is nothing to redo.

    1. Test case: `ag cs2103-f13-1`, `undo`, then `redo`<br>
       Expected: The add command will add a group which is reflected in the group list. The undo command then restores
       the previous data, removing this group. Redoing this will reverse the undo command, bringing the group back into
       the group list shown to you. After executing the command you will be brought back to the default panel of the
       application, which is the panel displaying the student list.

---

<div style="page-break-after: always;"></div>

## **Appendix: Planned Enhancements**

**Group size:** 4

### 1. Update Email Constraints

Currently, our system only validates the `local-part` of an email is alphanumerical, the following special characters
`_`, `.`
and does not start or end with special characters.

##### Enhancement

We plan to enhance the validation such that it follows NUS' email constraints, i.e. in the format of a student's NUS ID
or friendly email.

### 2. Improve UI

Currently, our system only supports showing a single display panel at one time.

##### Enhancement

We plan to split display panel into 3 that will display students, groups and tasks panels respectively.
This will allow users to better see all information rather than toggling through each display panel.

### 3. Improve granularity of Find Student command

Currently, our system returns all results that match any of the queries.

#### Enhancement

We plan to include other parameters such as `sno`, `sn`, `e` to allow users to search specific fields of a student.

Flags will be introduced to fine-tune the search such that the user can choose for the system to search if the fields
contain the queries or if the fields start with the queries.
Additionally, we will also change the command to return only results that match ALL queries given.

### 4. Enhance Sort Group

Currently, our system sorts `Groups` by ASCII order.

#### Enhancement

We plan to improve the sorting feature to sort by alphabetical and numerical order.

Below is a simplified process of how the sort will work:

A reminder that this is the format accepted for `Group Name`: `[Module]-[Tutorial Group]-[Group Number]`

1. For `Module`, `CS2103` groups will come first, followed by `CS2103T` groups.
2. For `Tutorial Group`, the letter will be compared first and sorted by alphabetical order. If there is a tie, the
   numerical part will be compared and sorted in descending order.
3. For `Group Number`,if the sort feature reaches this section, it will be sorted by descending order of numerical
   value.

### 5. Enhance Sort Student

Currently, our system sorts `Students` by ASCII order.

#### Enhancement

We plan to update the sorting such that it sorts by alphabetical order instead.

### 6. Better Parameter Mapping

Currently, the behaviour for handling extraneous parameters is unclear and inconsistent.

#### Enhancement

We plan to make the behaviour more consistent and improve the parameter matching such that warnings will be given to the
user.

### 7. Enhance Undo/Redo

Currently, our system brings users to the `list_s` panel whenever the commands are ran.

#### Enhancement

We plan to improve the versionHistory system to remember what command was ran such that when either commands were ran,
the user is informed of what was the action that was last carried out.

The user will also be redirected to the respective panel of said action.

i.e. if user undoes a `add_g` command, the user will be and informed that they are undoing a `add_g` command and will be
redirected to the `list_g` panel.

Similarly, if the user then runs `redo`, they will be informed that they are redoing the `add_g` command.

### 8. Bug fix - Edit Task for All Groups

Currently, the command will reset the status of the task for all groups to PENDING/OVERDUE.

#### Fix

We will fix it such that the command respects the status of the task for each group.
