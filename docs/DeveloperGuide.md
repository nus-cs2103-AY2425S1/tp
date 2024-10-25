<!--
---
layout: default.md
title: "Developer Guide"
pageNav: 3
---
-->


# HallPointer Developer Guide

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

**`Main`** (consisting of classes [`Main`](https://ay2425s1-cs2103t-w14-3.github.io/tree/master/src/main/java/hallpointer/address/Main.java) and [`MainApp`](https://ay2425s1-cs2103t-w14-3.github.io/tree/master/src/main/java/hallpointer/address/MainApp.java)) is in charge of the app launch and shut down.

- At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
- At shut down, it shuts down the other components and invokes cleanup methods where necessary.

### Storage Component

The Storage component is responsible for reading and writing data to the local storage. It includes classes for handling user preferences and the address book data.

Here is the UML diagram for the Storage component:

<puml src="diagrams/StorageClassDiagram.puml" width="600" />

The bulk of the app's work is done by the following four components:

- [**`UI`**](#ui-component): The UI of the App.
- [**`Logic`**](#logic-component): The command executor.
- [**`Model`**](#model-component): Holds the data of the App in memory.
- [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The _Sequence Diagram_ below shows how the components interact with each other for the scenario where the user issues the command `delete_member 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

- defines its _API_ in an `interface` with the same name as the Component.
- implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://ay2425s1-cs2103t-w14-3.github.io/tree/master/src/main/java/hallpointer/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `MemberListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://ay2425s1-cs2103t-w14-3.github.io/tree/master/src/main/java/hallpointer/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://ay2425s1-cs2103t-w14-3.github.io/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

- executes user commands using the `Logic` component.
- listens for changes to `Model` data so that the UI can be updated with the modified data.
- keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
- depends on some classes in the `Model` component, as it displays `Member` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://ay2425s1-cs2103t-w14-3.github.io/tree/master/src/main/java/hallpointer/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete_member 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteMemberCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.

</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteMemberCommandParser`) and uses it to parse the command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to delete a member).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:

- When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddMemberCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddMemberCommand`) which the `AddressBookParser` returns back as a `Command` object.
- All `XYZCommandParser` classes (e.g., `AddMemberCommandParser`, `DeleteSessionCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [`Model.java`](https://ay2425s1-cs2103t-w14-3.github.io/tree/master/src/main/java/hallpointer/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />

The `Model` component,

- stores the address book data i.e., all `Member` objects (which are contained in a `UniqueMemberList` object).
- stores the currently 'selected' `Member` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Member>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
- stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
- does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Member` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Member` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

### Storage component

**API** : [`Storage.java`](https://ay2425s1-cs2103t-w14-3.github.io/tree/master/src/main/java/hallpointer/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,

- can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
- inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
- depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `hallpointer.address.commons` package.

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

Step 2. The user executes `delete 5` command to delete the 5th member in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new member. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the member was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

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
    - Pros: Will use less memory (e.g. for `delete`, just save the member being deleted).
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

- is a CCA leader in a NUS Hall
- is responsible for managing member participation, tracking attendance, and allocating points
- prefers streamlined solutions to minimize manual administrative work
- comfortable with both desktop and command-line interfaces (CLI)
- often managing multiple responsibilities, including academic workload and hall duties

**Value proposition**: Hall Pointer empowers CCA leaders in NUS Halls to efficiently manage member tracking by streamlining participation recording, points allocation, and member information (such as room addresses). It simplifies manual data management, allowing leaders to focus on organizing activities rather than administrative tasks. Ideal for small, close-knit CCAs, it ensures quick access to updated information, enabling accurate and easy sharing of participation records with hall management or other stakeholders.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​         | I want to …​                                                     | So that I can …​                                                        | Remarks/Notes                                                                         |
| -------- | --------------- |------------------------------------------------------------------| ----------------------------------------------------------------------- | ------------------------------------------------------------------------------------- |
| `* * *`  | First-time user | Explore the app using sample data                                | I can understand its features without manually entering data            |                                                                                       |
| `* * *`  | First-time user | See a guide on how to use the app                                | I can better understand its functionalities                             |                                                                                       |
| `* * *`  | First-time user | Save the changes I made                                          | I won’t have to redo my work after reopening the app                    |                                                                                       |
| `* * *`  | First-time user | See sample data with a predefined structure                      | I have a format to follow when inputting my own data                    |                                                                                       |
| `* * *`  | First-time user | Delete all data in the app                                       | I can start over when I make a mistake and remove sample data           |                                                                                       |
| `* * *`  | User            | Add new users to the app                                         | I can track points for new Hall members                                 |                                                                                       |
| `* * *`  | User            | Delete existing users from the app                               | I can stop tracking points for ex-Hall members                          |                                                                                       |
| `* * *`  | User            | Customize point allocation criteria                              | I can reward members based on different participation criteria          | E.g., different point weights for different activities                                |
| `* * *`  | Frequent user   | Add or delete points for each member                             | I can track the overall participation status in the CCA                 |                                                                                       |
| `* * *`  | Frequent user   | Adjust attendance records if there are any errors                | I can fix mistakes and maintain accurate records                        |                                                                                       |
| `* * *`  | Frequent user   | Filter the data to see members with low attendance               | I can identify which members need attention                             |                                                                                       |
| `* * *`  | User            | Update member details (e.g., name, contact)                      | I can keep the member database up to date                               |                                                                                       |
| `* *`    | First-time user | Import data from an existing Google Sheets document or csv file  | I can quickly upload my data without manual entry                       |                                                                                       |
| `* *`    | Frequent user   | Automatically track attendance at each session                   | I don't need to manually mark attendance for each session               | Using QR codes? That would need some kind of integration though, would be complicated |
| `* *`    | Frequent user   | See a breakdown of points for each member quickly                | I can monitor attendance records without navigating multiple screens    |                                                                                       |
| `* *`    | Frequent user   | Export attendance data                                           | I can share participation reports with other stakeholders if needed     |                                                                                       |
| `* *`    | User            | Bulk update attendance or points for multiple members            | I can efficiently manage large groups                                   |                                                                                       |
| `* *`    | User            | Set up custom attendance categories (e.g., Excused, Late)        | I can categorize different types of attendance                          |                                                                                       |
| `* *`    | User            | View analytics or visual reports of attendance and participation | I can see trends and member engagement at a glance                      | Charts or graphs to visualize data                                                    |
| `*`      | User            | Sort members by name                                             | I can locate a member easily                                            |                                                                                       |
| `*`      | Frequent user   | Automatically save changes without manual intervention           | I don’t lose progress if I forget to click save                         | Auto-save feature                                                                     |
| `*`      | Expert user     | Perform all actions using the CLI                                | I can interact with the app more efficiently without relying on the GUI |                                                                                       |
| `*`      | Expert user     | Automate repetitive tasks, such as attendance updates            | I can save time by reducing manual input                                |                                                                                       |
| `*`      | User            | Add notes for each member                                        | I can track special situations or reasons for absences                  |                                                                                       |

### Use cases

#### Use Case: UC01 - Add Member to CCA

**System**: Hall Pointer App

**Actor**: CCA Leader

**Description**: This use case allows a CCA leader to add a new member to the CCA for attendance tracking and point allocation.

**Preconditions**:
1. The CCA Leader must know the details of the new member.

**Main Success Scenario (MSS)**:
1. CCA Leader inputs the `add_member` command with required details (name, room number, and telegram).
    - Example: `add_member /name John Doe /room 4/3/301 /tele johndoe123`
2. Hall Pointer validates the entered details for the new member.
3. Hall Pointer adds the member to the system and displays a success message.
4. The new member is displayed in the GUI.
    - Use case ends.

**Extensions**:

- **2a. Hall Pointer detects an error in the entered data**.
    - 2a1. Hall Pointer requests for correct data with an error message indicating the invalid field.
    - 2a2. CCA Leader re-enters corrected data.
    - Steps 2a1-2a2 are repeated until all data is correct.
    - Use case resumes from step 3.

- **2b. Duplicate member is detected**.
    - 2b1. Hall Pointer displays an error message: `Error: Member John Doe already exists.`
        - Use case ends.

- **\*a. At any time, CCA Leader chooses to cancel the add member operation**.
    - \*a1. Hall Pointer requests confirmation of the cancellation.
    - \*a2. CCA Leader confirms the cancellation.
        - Use case ends.

---

#### Use Case: UC02 - Add Session to CCA

**System**: Hall Pointer App

**Actor**: CCA Leader

**Description**: This use case allows a CCA leader to add a new CCA session to the system for tracking attendance and point allocation.

**Preconditions**:
1. The CCA Leader must know the details of the session such as name, date, and points.

**Main Success Scenario (MSS)**:
1. CCA Leader inputs the `add_session` command with session details (name, date, and points).
    - Example: `add_session rehearsal /date 2024-09-19 /points 2`
2. Hall Pointer validates the entered session details.
3. Hall Pointer adds the session to the system and displays a success message.
4. The new session is displayed in the GUI.
    - Use case ends.

**Extensions**:

- **2a. Hall Pointer detects an error in the entered data**.
    - 2a1. Hall Pointer requests for correct data with an error message indicating the invalid field.
    - 2a2. CCA Leader re-enters corrected data.
    - Steps 2a1-2a2 are repeated until all data is correct.
    - Use case resumes from step 3.

- **2b. Duplicate session is detected**.
    - 2b1. Hall Pointer displays an error message: `Error: Session rehearsal already exists.`
        - Use case ends.

- **\*a. At any time, CCA Leader chooses to cancel the add session operation**.
    - \*a1. Hall Pointer requests confirmation of the cancellation.
    - \*a2. CCA Leader confirms the cancellation.
        - Use case ends.

---

#### Use Case: UC03 - Mark Member Present for a Session

**System**: Hall Pointer App

**Actor**: CCA Leader

**Description**: This use case allows a CCA leader to mark a member as present for a specific CCA session.

**Preconditions**:
1. The member and session must exist in the system.

**Main Success Scenario (MSS)**:
1. CCA Leader inputs the `mark_present` command with the member name and session name.
    - Example: `mark_present John Doe /session volleyball training`
2. Hall Pointer validates the member and session details.
3. Hall Pointer records the attendance and displays a success message:
    - `Attendance recorded for John Doe on volleyball training.`
4. The updated attendance is reflected in the GUI.
    - Use case ends.

**Extensions**:

- **2a. Member or session not found**.
    - 2a1. Hall Pointer displays an error message indicating the missing entity.
        - `Failed to log attendance: member or session does not exist.`
        - Use case ends.

- **\*a. At any time, CCA Leader chooses to cancel the mark present operation**.
    - \*a1. Hall Pointer requests confirmation of the cancellation.
    - \*a2. CCA Leader confirms the cancellation.
        - Use case ends.

---

#### Use Case: UC04 - Update Member Information

**System**: Hall Pointer App

**Actor**: CCA Leader

**Description**: This use case allows a CCA leader to update the details of an existing member, such as room number, telegram, or tags.

**Preconditions**:
1. The member to be updated must exist in the system.

**Main Success Scenario (MSS)**:
1. CCA Leader inputs the `update_member` command with the member name and new details.
    - Example: `update_member John Doe /room 9/10/203 /tag friend`
2. Hall Pointer validates the member and new details.
3. Hall Pointer updates the member information and displays a success message.
    - `Member John Doe's room updated to 9/10/203.`
4. The updated member information is displayed in the GUI.
    - Use case ends.

**Extensions**:

- **2a. Member not found**.
    - 2a1. Hall Pointer displays an error message:
        - `Error: Member with the given name could not be found.`
        - Use case ends.

- **\*a. At any time, CCA Leader chooses to cancel the update operation**.
    - \*a1. Hall Pointer requests confirmation of the cancellation.
    - \*a2. CCA Leader confirms the cancellation.
        - Use case ends.

---

#### Use Case: UC05 - View All Members

**System**: Hall Pointer App

**Actor**: CCA Leader

**Description**: This use case allows a CCA leader to view a list of all members in the system.

**Preconditions**:
None.

**Main Success Scenario (MSS)**:
1. CCA Leader inputs the `list_members` command.
2. Hall Pointer retrieves and displays all members in the GUI.
    - Use case ends.

**Extensions**:

- **2a. No members found**.
    - 2a1. Hall Pointer displays an error message:
        - `Error: No members found.`
        - Use case ends.

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should be able to hold up to 1000 members without a noticeable sluggishness in performance for typical usage.
3. A user with above-average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. The application should respond to user commands within 2 seconds under normal operating conditions.
5. The user interface should be intuitive enough for a first-time user to understand basic functionalities without external help.
6. While primarily designed for one user, the application should be able to handle up to 1000 members efficiently, with room for future enhancements.
7. The application should have a success rate of at least 95% for command executions, ensuring that most user actions are completed successfully without errors.
8. Code should be organized and documented to facilitate future updates or modifications.
9. The application should run seamlessly across different operating systems without requiring extensive configuration.

### Glossary

1.  **Hall Pointer:**\
    A desktop application used by CCA leaders in NUS Halls to track hall points, manage member participation, attendance, and allocate points. It is optimized for Command Line Interface (CLI) usage but also includes a Graphical User Interface (GUI).

2.  **CLI (Command Line Interface):**\
    A text-based interface where users interact with the application by typing commands, making it efficient for users comfortable with fast typing.

3.  **GUI (Graphical User Interface):**\
    A visual interface that allows users to interact with the application using graphical components like buttons and menus, in addition to CLI commands.

4.  **Member:**\
    A participant or member of a CCA (Co-Curricular Activity) in NUS Halls, whose details are tracked in the Hall Pointer system (e.g., name, telegram, points, and attendance).

5.  **Points Allocation:**\
    The process of awarding hall points to members based on their participation in activities. CCA leaders can customize the criteria for point allocation.

6.  **CCA (Co-Curricular Activity):**\
    A club or activity within an NUS Hall that tracks member participation and points. Hall Pointer helps CCA leaders manage their members more efficiently.

7.  **Gradle:**\
    A build automation tool used in Hall Pointer for compiling code, managing dependencies, and running tasks such as testing and creating JAR files.

8.  **JUnit:**\
    A testing framework for Java, used in Hall Pointer to run automated tests on individual units of the system and ensure code correctness.

9.  **ShadowJar:**\
    A Gradle task that generates a fat JAR file, which bundles the application and its dependencies into a single JAR for distribution.

10. **GitHub Actions:**\
    A Continuous Integration (CI) tool integrated with GitHub to automatically test and build the Hall Pointer application whenever new changes are pushed.

11. **Codecov:**\
    A tool that tracks code coverage during testing. It helps assess how much of the Hall Pointer code is covered by tests, encouraging improvements to the test suite.

12. **Fat JAR:**\
    A JAR file that contains the entire application along with all its dependencies, allowing Hall Pointer to run as a standalone application.

13. **POSIX-compliant OS:**\
    Operating systems like Linux and macOS that adhere to POSIX standards and are compatible with shell scripts used for CI tasks in Hall Pointer.

14. **Tag:**\
    Labels or categories assigned to members in the Hall Pointer system (e.g., `leader`, `active`, `inactive`). Tags help classify and manage members more easily.

15. **Undo/Redo Feature:**\
    A proposed feature that allows users to revert or redo changes in Hall Pointer, enabling easy correction of mistakes.

16. **Versioned AddressBook:**\
    Refers to a version of the Hall Pointer system where the state of member data is saved at specific intervals to allow undo/redo functionality.

17. **Test Coverage:**\
    A metric that measures how much of the Hall Pointer codebase is covered by tests, indicating the effectiveness and thoroughness of the test suite.

18. **User Preferences:**\
    Settings such as window size and logging levels that can be customized by users and saved in a configuration file (`config.json`) for Hall Pointer.

19. **Configuration File (`config.json`):**\
    A JSON file that stores user preferences and application settings for Hall Pointer, including file locations and logging levels.

20. **Build Automation:**\
    The process of automating the compilation, testing, and packaging of Hall Pointer using Gradle to ensure consistent builds across different environments.

21. **Continuous Integration (CI):**\
    A practice used in Hall Pointer, powered by GitHub Actions, where tests and checks are run automatically to verify the integrity of new code changes before merging.

22. **Unit Test:**\
    A test that targets individual components or methods in the Hall Pointer system to ensure they function correctly in isolation.

23. **Integration Test:**\
    A test that checks how different components in Hall Pointer interact with each other, ensuring they work together as expected.

24. **Hybrid Test:**\
    A combination of unit and integration testing that checks both the individual components and their interactions in the Hall Pointer system.

25. **Command:**\
    A typed instruction input by the user in the CLI to perform an action in Hall Pointer, such as adding members, updating details, or tracking points (e.g., `add`, `list`, `delete`).

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more _exploratory_ testing.

</box>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    2. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

3. _{ more test cases …​ }_

### Deleting a member

1. Deleting a member while all members are being shown

    1. Prerequisites: List all members using the `list` command. Multiple members in the list.

    2. Test case: `delete 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

    3. Test case: `delete 0`<br>
       Expected: No member is deleted. Error details shown in the status message. Status bar remains the same.

    4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

2. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

    1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

2. _{ more test cases …​ }_
