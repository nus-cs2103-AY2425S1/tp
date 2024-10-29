---
layout: page
title: Developer Guide
---

- Table of Contents
  {:toc}

---

## **Acknowledgements**

- {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

---

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

---

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

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

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

- defines its _API_ in an `interface` with the same name as the Component.
- implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

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

- When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
- All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />

The `Model` component,

- stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
- stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
- stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
- does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

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

- NUS club administrator responsible for managing contacts
- prefer desktop apps over other types
- can type fast
- prefers typing to mouse interactions
- is reasonably comfortable using CLI apps

**Value proposition**: Helps NUS club administrators manage and track different categories of contacts (e.g., students, companies) faster than a typical mouse/GUI-driven app.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                      | I want to …​                                                                       | So that I can…​                                                                    |
| -------- |----------------------------------------------|------------------------------------------------------------------------------------|------------------------------------------------------------------------------------|
| `* * *`  | new user                                     | see usage instructions                                                             | refer to instructions when I forget how to use the App                             |
| `* * *`  | user                                         | add new contacts                                                                   | manage contact information quickly                                                 |
| `* * *`  | user                                         | view all contacts                                                                  | see all my contacts saved in one screen                                            |
| `* * *`  | user                                         | delete a contact                                                                   | remove entries that I no longer need                                               |
| `* *`    | user                                         | edit existing contacts                                                             | amend mistakes/update new info on my contacts                                      |
| `* *`    | potential user                               | see the app populated with sample data                                             | easily try and see how the app will look like when it is in use                    |
| `* *`    | new user ready to use the app                | remove all current data                                                            | remove all sample data I used when exploring the app                               |
| `* *`    | admin user                                   | track contacts by category (e.g., students, companies)                             | quickly retrieve specific groups of contacts (e.g., all students or all companies) |
| `* *`    | admin user                                   | filter contacts by tag (e.g., "sponsor", "member")                                 | find contacts associated with specific events or groups                            |
| `* *`    | familiar user of the app                     | save the contacts under a favourites tab                                           | easily access the contacts that I frequently use                                   |
| `* *`    | familiar user of the app                     | tag certain contacts                                                               | remember where I know the contacts from                                            |
| `* *`    | new user                                     | learn how to use the app quickly                                                   | use the app frequently with other club admins                                      |
| `* *`    | impatient user                               | use shortcut commands instead of the full name of the commands                     | make minimal spelling mistakes when I am entering the commands                     |
| `* *`    | familiar user of the app                     | search contacts by name                                                            | easily find the contact person instead of scrolling                                |
| `* *`    | admin user with frequent changes in schedule | mark contacts as "high priority" or "low priority"                                 | focus on the most relevant people when my schedule is tight                        |
| `*`      | new user who is unfamiliar with English      | have suggestions on commands to enter                                              | enter the right commands if I am unsure on how to spell certain words              |
| `*`      | familiar user of the app                     | mass add a large list of contacts                                                  | avoid from entering repetitive commands                                            |
| `*`      | impatient user                               | experience reasonable response time while up to 1000 concurrent users are using it | use the app even when the traffic is at the maximum expected level                 |
| `*`      | impatient user                               | manage up to 1000 contacts with fast response time                                 | ensure smooth usage even when managing a large contact list                        |
| `*`      | busy user                                    | quickly import contacts from other platforms (e.g., phone, social media, email)    | avoid manually inputting every new contact into AdmiNUS                            |
| `*`      | busy admin user                              | easily export the contact list                                                     | share or back up the contact list for other admin team members or departments      |

_{More to be added}_

### Use cases

(For all use cases below, the **System** is the `AdmiNUS` and the **Actor** is the `user`, unless specified otherwise)

**Use case 1: Add a contact**

**MSS**

1. User requests to add a new contact
2. User enters the required information (name, id, phone number, email, and address) and optional information (tag)
3. AdmiNUS adds the contact and displays a success message

   Use case ends.

**Extensions**

- 2a. The given arguments are invalid.

  - 2a1. AdmiNUS shows an error message for the specific invalid field.
  
    Use case resumes at step 2.

- 3a. Contact with the same phone number already exists.

  - 3a1. AdmiNUS shows an error message about duplicate contact.
  
    Use case resumes at step 2.

**Use case 2: Delete a contact**

**MSS**

1.  User requests to list contacts
2.  AdmiNUS shows a list of contacts
3.  User requests to delete a specific contact in the list
4.  AdmiNUS deletes the contact

    Use case ends.

**Extensions**

- 2a. The list is empty.

  Use case ends.

- 3a. The given index is invalid.

  - 3a1. AdmiNUS shows an error message.

    Use case resumes at step 2.

**Use case 3: Edit a contact**

**MSS**

1. User requests to list contacts.
2. AdmiNUS shows a list of contacts.
3. User selects a contact to edit.
4. User enters updated contact information.
5. AdmiNUS updates the contact and displays a success message.

    Use case ends.

**Use case 4: Filter contacts by category or tag**

**MSS**

1. User requests to filter contacts by category (e.g., student, company) or tag (eg. "group A")
2. The system filters and displays the list of contacts belonging to the specified category or tag

   Use case ends.

**Extensions**
- 2a. No contacts match the specified category.
  - 2a1. AdmiNUS displays an error message and an empty list.
  
    Use case ends.
---

### Non-Functional Requirements

1. **Platform Compatibility**:
   - The application should work on any mainstream OS as long as it has Java 17 or above installed.
2. **Performance Requirements**:
   - The application should be able to hold up to 1000 tasks without noticeable sluggishness for typical usage scenarios.
   - A user should be able to execute most commands (e.g., adding, deleting, or updating tasks) in under 1 second.
3. **Usability Requirements**:
   - The user interface should be intuitive and allow users to easily understand how to input commands without prior extensive training.
   - Error messages should be user-friendly and provide enough information to guide the user towards resolving the issue.
4. **Scalability Requirements**:
   - The system should support additional features or commands without major changes to the existing architecture.
   - The task list should scale efficiently to handle future use cases, such as storing significantly larger amounts of data.
5. **Reliability and Availability**:
   - The application should be reliable, ensuring data consistency even after unexpected shutdowns.
   - Data should be auto-saved periodically to minimize the risk of data loss during crashes.
6. **Security Requirements**:
   - The application should store user data securely, ensuring that unauthorized access is prevented.
   - User data should not be stored in plaintext, and sensitive data should be encrypted where applicable.
7. **Portability Requirements**:
   - The application should be executable without a complex installation process, ideally by running a standalone JAR file.
8. **Maintainability Requirements**:
   - The codebase should follow good software engineering principles, making it easy for new developers to add features or fix bugs.
   - The system should have high cohesion and low coupling between components to facilitate easier updates and maintenance.
9. **Extensibility Requirements**:
   - The architecture should allow for the addition of new task types (e.g., recurring tasks) with minimal changes.
   - The system should allow for integration with third-party tools (e.g., cloud-based storage) to extend its functionality.
10. **Backup and Recovery**:
    - The application should have a data backup mechanism, allowing users to recover from data corruption or loss.

---

### Glossary

- **NUS Admin**: An NUS club admin user responsible for managing contacts of students, companies, etc.
- **Mainstream OS**: Refers to commonly used operating systems, including Windows, macOS, and Linux distributions.
- **Category**: A label that represents the type of contact (e.g., student, company) used for filtering and sorting contacts.
- **Tag**: A keyword or label associated with a contact that allows for easy grouping and filtering.
- **Command**: A user input string that triggers a specific action within the Vinegar application.
- **User Interface (UI)**: The part of the application that users interact with, which includes graphical components like command boxes and task lists.
- **CLI (Command Line Interface)**: A text-based user interface through which users interact with the application by typing commands.
- **Profile Card**: A GUI feature that displays detailed information about a contact.
- **Scalability**: The capacity of the system to handle increasing amounts of data or user load without performance degradation.
- **JavaFX**: A software platform used for creating and delivering desktop applications with graphical user interfaces in Java.
- **Data Persistence**: The characteristic of data that outlives the execution of the process that created it, usually achieved through saving data to a file or database.
- **Parser**: A component that interprets user input (commands) and converts them into actions for the application.
- **Error Handling**: The process of identifying, diagnosing, and responding to errors or exceptions that occur during program execution.
- **Encryption**: The process of converting information or data into a code, especially to prevent unauthorized access.
- **Data Backup**: The process of copying and archiving data to prevent loss in case of system failure or data corruption.
- **Extensibility**: The ability of the software to be extended with new features or components with minimal impact on existing functionality.
- **Reliability**: The measure of the system’s ability to operate without failure and produce consistent results under specified conditions.

---

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

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
