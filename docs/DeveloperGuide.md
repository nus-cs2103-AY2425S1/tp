---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# KonTActs Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

---

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

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

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.

- At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
- At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

- [**`UI`**](#ui-component): The UI of the App.
- [**`Logic`**](#logic-component): The command executor.
- [**`Model`**](#model-component): Holds the data of the App in memory.
- [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The _Sequence Diagram_ below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

- defines its _API_ in an `interface` with the same name as the Component.
- implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

- executes user commands using the `Logic` component.
- listens for changes to `Model` data so that the UI can be updated with the modified data.
- keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
- depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete n/John")` API call as an example.

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

- When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
- All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />

The `Model` component,

- stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
- stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
- stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
- does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,

- can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
- inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
- depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

---

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

- `VersionedAddressBook#commit()` — Saves the current address book state in its history.
- `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
- `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

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

- **Alternative 1 (current choice):** Saves the entire address book.

  - Pros: Easy to implement.
  - Cons: May have performance issues in terms of memory usage.

- **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  - Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  - Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_

---

## **Documentation, logging, testing, configuration, dev-ops**

- [Documentation guide](Documentation.md)
- [Testing guide](Testing.md)
- [Logging guide](Logging.md)
- [Configuration guide](Configuration.md)
- [DevOps guide](DevOps.md)

---

## **Appendix: Requirements**

### Product scope

**Target user profile**:

- has a need to manage a significant number of contacts
- prefer desktop apps over other types
- can type fast
- prefers typing to mouse interactions
- is reasonably comfortable using CLI apps

**Value proposition**: manage contacts faster than a typical mouse/GUI driven app

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a/an …​       | I want to …​                                                           | So that …​                                                                                                            |
|-------|------------------|------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------|
| `* * *` | CS2030S TA       | store student's github username                                        | I can easily reference them when grading assignments.                                                                 |
| `* * *` | user             | add the student's contact number                                       | I can easily reference them when I need to contact my students.                                                       |
| `* * *` | CS2030S TA       | add the contact details of other TAs                                   | I can quickly reach out for help or collaboration.                                                                    |
| `* * *` | CS2030S TA       | add contact details of professors                                      | I can easily reach them for guidance or to pass on important information.                                             |
| `* * *` | CS2030S TA       | delete contacts easily                                                 | I dont clutter the list with unwanted contacts.                                                                       |
| `* * *` | CS2030S TA       | store the grades and progress of my students                           | I can keep track of which of my students need more guidance and follow up.                                            |
| `* * *` | CS2030S TA       | store student's telegram username                                      | I can easily reference them and contact them when needed to.                                                          |
| `* * *` | CS2030S TA       | see the student's MC or reasoning when they do not turn up for lessons | I can create make up lessons / check up on them.                                                                      |
| `* *` | CS2030S TA       | have a function to hide the details of students that I do not need     | I can only the the information that I want to see.                                                                    |
| `* *` | CS2030S TA       | view the last modification date of student contact details             | I can confirm the accuracy and recency of the information stored.                                                     |
| `* *` | CS2030S TA       | create contacts with optional fields                                   | I can resepct the privacy of my students.                                                                             |
| `*`  | CS2030S TA       | search for a student’s GitHub username                                 | I can quickly access their repository for grading and feedback.                                                       |
| `*`  | potential user   | see the application populated with sample data                         | I can see how the app looks like when it is in use.                                                                   |
| `*`  | CS2030S TA       | put the contacts into different tabs                                   | I can easily navigate between different types of contacts.                                                            |
| `*`  | CS2030S TA       | use the command line interface to search for contacts                  | I can integrate the tool smoothly into my existing workflow.                                                          |
| `*`  | CS2030S TA       | search for the contact details of professors/ other TAs                | I can quickly contact them for help if needed.                                                                        |
| `*`  | CS2030S TA       | find my students house in time                                         | I can offer them help in times of crisis.                                                                             |
| `*`  | CS2030S TA       | organise the contact of my students                                    | I can view the details of each student with greater ease.                                                             |
| `*`  | CS2030S TA       | import student contact information from a file                         | I can easily transfer data between devices.                                                                           |
| `*`  | CS2030S TA       | export student contact information to a file                           | I can backup or share contact details with other TAs or professors if needed.                                         |
| `*`  | CS2030S TA       | flag specific students for follow-up                                   | I can easily identify students who may need additional support or guidance.                                           |
| `*`  | CS2030S TA       | choose to sort my students                                             | I can group students based on their proficiency.                                                                      |
| `*`  | CS2030S TA       | filter the contact details that is shown                               | I can easily find the information of a particular group.                                                              |
| `*`  | CS2030S TA       | filter contacts based on a certain criteria                            | I can access a specific subset of students that I want.                                                               |
| `*`  | CS2030S TA       | tag students with custom labels                                        | I can categorize students based on their progress or needs.                                                           |
| `*`  | CS2030S TA       | use the command line to access my students work                        | have their work and contact and tags all tied together in one smooth workflow.                                        |
| `*`  | experienced user | create shortcuts for commands that I use frequently                    | I can access the frequently used information quickly.                                                                 |
| `*`  | new user         | use a help function to check what this app offers                      | I can easily have the details of the commands to use in my fingertips.                                                |
| `*`  | CS2030S TA       | create automatic flags to indicate if a student's work is marked       | I can monitor grading deadlines so that I can stay on top of my responsibilities without missing any critical dates.  |

### Use cases

(For all use cases below, the **System** is the `KonTActs` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - Add contacts**

**MSS**

1. User chooses to add a contact.
2. KonTActs requests for the contact details.
3. User enters the contact details.
4. KonTActs adds the new contact.

   Use case ends.

**Extensions**

- 3a. KonTActs detects an error in the input format.

  - 3a1.KonTActs requests for the corrected input.
  - 3b2. User enters a new input.
  - Steps 3a1 - 3a2 are repeated until input format is correct.

    Use cases resume from step 4.

<br>

**Use case: UC02 - Delete contacts**

**Precondition**

1. The task that the user wants to delete exists.

**MSS**

1. User indicates to delete a task.
2. KonTActs deletes the tasks and indicates success.

   Use case ends.

**Extensions**

- 1a. KonTActs detects an error in the input.

  - 1a1.KonTActs requests for the user to try again.
  - 1a2. User enters the command again
  - Steps 1a1 - 1a2 are repeated until the input entered is correct.

    Use case resumes from step 2.

<br>

**Use case: UC03 - Add grades of students**

**Precondition**

1. The student that the user wants to add grades exists.
2. The assignment that the user wants to add a grade to exists.

**MSS**

1. User chooses to add grades for a student.
2. KonTActs requests for details of the student alongside the assignment and grade.
3. User enters the requested details.
4. KonTActs updates the grade of the student.

   Use case ends.

**Extensions**

- 4a. KonTActs detects an error in the entered data.

  - 4a1. KonTActs requests for the correct data.
  - 4a2. User enters new data.
  - Steps 4a1-4a2 are repeated until the data entered are correct.

    Use case resumes from step 4.

    <br>

**Use case: UC04 - View Contact**

**MSS**

1. Current UI is not showing the entire contact list.
2. User chooses to view the entire contact list.
3. KonTActs displays the full list of contacts.\
   Use case ends.

**Extensions**

- 1a. User has previously filtered the contact list.

  - 1a1. KonTActs displays the full contact list, removing the previous filters.

    Use case ends.

- 1b. KonTActs detects an error (e.g., unable to retrieve contacts).

  - 1b1. KonTActs displays an error message.

    Use case ends.

<br>

**Use case: UC05 - Update contacts**

**MSS**

1. TA chooses to update a contact’s details.
2. KonTActs requests the contact’s identifier.
3. TA enters the identifier of the contact to update.
4. KonTActs displays the current details and requests the changes.
5. TA updates the relevant details.
6. KonTActs saves and displays the changes.

   Use case ends.

**Extensions**

- 3a. KonTActs identifies that there is no such contact.

  - 3a1. KonTActs requests for the correct data.
  - 3a2. TA enters the correct data.
  - Steps 3a1-3a2 are repeated until the data entered are correct.

    Use case resumes from step 4.

<br>

**Use case: UC06 - Filter Contact List**

**MSS**

1. User chooses to filter the contact list.
2. KonTActs requests the filter criteria (current overall grades, name, tele handle).
3. User enters the filter criteria.
4. KonTActs filters the contact list based on the entered criteria and displays the filtered list.

   Use case ends.

**Extensions**

- 3a. KonTActs detects an error in the entered filter criteria (e.g., incorrect input - invalid score input).

  - 3a1. KonTActs requests for the correct filter criteria.
  - 3a2. Users enter new criteria.
  - Steps 3a1-3a2 are repeated until the criteria entered are valid.

    Use case resumes from step 4.

- \*a. At any time, User chooses to cancel the filter action.
- \*a1. KonTActs stops the filter operation and returns to the unfiltered contact list by using list

<br>

**Use case: UC07 - Create shortcut for commands**

**MSS**

1. User indicates to create a shortcut.
2. KonTActs request for the command that is frequently used.
3. User enters the command to be shortened.
4. KonTActs request for the shortcut input to replace the command.
5. User enters the shortcut input.
6. KonTActs stores the shortcut and indicates success.

   Use case ends.

**Extensions**

- 3a. KonTActs detects that the command entered is not valid.

  - 3a1. KonTActs requests for the correct command.
  - 3a2. User enters a new command.
  - Steps 3a1 - 3a2 are repeated until the command entered is valid.

    Use case resumes from step 4.

- 5a. KonTActs detects that the shortcut is already in use.

  - 5a1. KonTActs request for a new shortcut.
  - 5a2. User enters a new shortcut.
  - Steps 5a1 - 5a2 are repeated until the shortcut entered is valid.

    Use case resumes from step 6.

- \*a. At any time, User chooses to cancel the creation of a shortcut.

  - \*a1. KonTActs stops the creation.

    Use case ends.

    <br>

**Use case: UC08 - Sort students based on proficiency**

**Precondition**

1. The address book contains a list of students/ contacts.
2. Contacts should have a proficiency rating associated to them.

**MSS**

1. User chooses to sort the students based on their proficiency.
2. User enters the sort command.
3. KonTActs returns the list of students in the sorted order.
   Use case ends.

   <br>

**Use case: UC09 - Export contacts**

**MSS**

1. User chooses to export the contact list.
2. KonTActs requests for the format.
3. User selects the desired format.
4. KonTActs exports the contact list to the specified format and shows successful import.

   Use case ends.

**Extensions**

- 2a. User selects an unsupported format.

  - 2a1. KonTActs displays an error message and provides the list of formats that are supported.

    Use case resumes from step 3.

    <br>

**Use case: UC10 - Request for help**

**MSS**

1. User inputs help command.
2. KonTActs shows a help page.

   Use case ends.

**Extensions**

- 1a. User inputs help for a specific command.

  - 1a1. KonTActs displays a help page for that command.

    Use case ends.

    <br>

**Use case: UC11 - Tag students with custom labels**

**Precondition**

1. The student that the user wants to tag exists.

**MSS**

1. User chooses to tag a student.
2. KonTActs requests for details of the student alongside the tag to label the student.
3. User enters the requested details.
4. KonTActs tags the student with the suggested label.

   Use case ends.

**Extensions**

- 3a. KonTActs detects an error in the entered data.

  - 3a1. KonTActs requests for the correct data.
  - 3a2. User enters new data.
  - Steps 3a1-3a2 are repeated until the data entered are correct.

    Use case resumes from step 3.

    <br>

**Use case: UC12 - View last modification date of contact details**

**MSS**

1. User requests for last modification date of contact.
2. KonTActs shows the last modification date for that contact.

   Use case ends.

**Extensions**

- 1a. User inputs a non-existing contact.

  - 1a1. KonTActs requests for corrected contact details.
  - 1a2. User inputs new contact details.
  - Steps 1a1 - 1a2 are repeated until the input contact is correct.

    Use case resumes from step 2.

    <br>

**Use case: UC13 - Import contacts**

**MSS**

1. User chooses to import the contact list.
2. KonTActs requests for the file.
3. User selects the desired file.
4. KonTActs import the contact list from the specified file and shows successful import.

   Use case ends.

**Extensions**

- 3a. KonTActs detects an unsupported or corrupted file.

  - 3a1. KonTActs indicates it is unable to import from that file and requests for a new file.
  - 3a2. User selects a new file.
  - Steps 3a1 - 3a2 are repeated until KonTActs is able to import contacts from the file.

    Use case resumes from step 4.

- \*a. At any time, User chooses to cancel the import.

  - \*a1. KonTActs stops the import.

    Use case ends.

    <br>

**Use case: UC14 - Create automatic flags for students’ work if marked**

Actor: TA

**MSS**

1. TA marks a student’s work.
2. KonTActs creates a flag to show the student’s work as marked.

   Use case ends.

### Non-Functional Requirements

1. Should work on any mainstream OS as long as it has Java 17 or above installed.
2. Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. Commands should be easy to remember.
5. Ui should be easy to navigate and intuitive.
6. KonTActs should be easy to use for new users.
7. The system should work on both 32 bit and 64 bit environments.
8. Contact details are securely stored.
9. The application should have an uptime of at least 99.9% to ensure constant availability for users.
10. The application should automatically save data after every change to avoid data loss in case of a crash.
11. There should be proper documentation for the code and application usage to assist developers in future updates.
12. Error messages should be descriptive, providing users with clear guidance on how to resolve the issue.
13. Searching for or filtering contacts should take less than 5 seconds.
14. Stored contacts are persisted between sessions.

### Glossary

* **Contact**: An individual (e.g. student) stored in the system, typically having details regarding them such as their name, github username and etc.

* **Easy to use for new users**: intuitive commands that are easy to understand and UI that is easy to navigate.

* **Mainstream OS**: Windows, Linux, Unix, MacOS.

* **Average typing speed**: about 40 words per minute.

* **Uptime**: The system should be operational during that period of time.

* **Between sessions**: Every opening and closing of the application.

* **Proper documentation**: A detailed user and developer guide which helps future users and developers to understand and use the code.

* **Typical usage**: Normal or expected usage patterns of the application, such as the frequency of adding, deleting, or viewing contacts during everyday use.

---

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

---
