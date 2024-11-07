---
layout: default.md
title: "Developer Guide"
pageNav: 3
---

# Bridal Boss Developer Guide

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

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `WeddingListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

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

* stores the address book data i.e., all `Person` and `Wedding` objects (which are contained in a `UniquePersonList` and `UniqueWeddingList` object).
* stores the currently 'selected' `Person` and/or `Wedding` objects (e.g., results of a search query) as separate _filtered_ lists which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

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

## Product Scope

### Target User Profile

A small to medium scale **wedding organizer** responsible for planning and managing weddings. They coordinate with vendors, clients (brides, grooms, and their families), and participants (guests, photographers, caterers, etc.). Their work involves juggling multiple tasks and deadlines to ensure that each wedding runs smoothly. They may need to manage multiple weddings simultaneously and prefer efficient tools that help streamline their workflow. They are comfortable using desktop applications and can type quickly, preferring typing over mouse interactions.

### Value Proposition

**Bridal Boss** enables wedding organizers to manage multiple weddings simultaneously while maintaining detailed vendor and client records. It offers fast and efficient access to information, helping organizers categorize and update contacts related to each wedding easily. By providing streamlined management of vendor contacts, client preferences, and event timelines, Bridal Boss helps wedding organizers accommodate last-minute changes effectively and keep everything up to date.

### User Stories

Priorities:

- High (must have) - `* * *`
- Medium (nice to have) - `* *`
- Low (unlikely to have) - `*`

| Priority | As a...                    | I want to...                                       | So that I can...                                                              |
|----------|----------------------------|----------------------------------------------------|-------------------------------------------------------------------------------|
| `* * *`  | Wedding organizer          | create separate profiles for each wedding          | manage multiple weddings without confusion                                    |
| `* * *`  | Wedding organizer          | view an overview of all ongoing weddings           | manage multiple events at once without losing track                           |
| `* * *`  | Wedding organizer          | add and categorize vendors                         | keep track of service providers for each wedding                              |
| `* * *`  | Wedding organizer          | view stakeholders related to each specific wedding | easily check their schedules                                                  |
| `* * *`  | Wedding organizer          | update client preferences easily                   | accommodate last-minute changes and keep everything up to date                |
| `* * *`  | Wedding organizer          | delete profiles and roles                          | remove outdated or incorrect information                                      |
| `* * *`  | Wedding organizer          | quickly search for specific contacts or vendors    | access critical information without delays                                    |
| `* * *`  | Wedding organizer          | filter contacts based on roles                     | retrieve contacts of stakeholders involved in a wedding or of a specific role |
| `* *`    | Wedding organizer          | archive completed weddings                         | focus on current and upcoming events without clutter                          |
| `* *`    | Wedding organizer          | track expenses for each wedding                    | manage the overall wedding budget efficiently                                 |
| `* *`    | Wedding organizer          | set reminders for important tasks or deadlines     | ensure critical milestones are not missed                                     |
| `* *`    | Wedding organizer          | track RSVPs from clients                           | keep an accurate guest count for each wedding                                 |
| `* *`    | Wedding organizer          | generate reports on completed tasks                | review progress and share updates with clients                                |
| `* *`    | Wedding organizer          | assign tasks to team members                       | delegate responsibilities and track progress efficiently                      |
| `*`      | Wedding organizer          | upload important documents (e.g., contracts)       | access them quickly during planning                                           |
| `*`      | Wedding organizer          | send automated reminders to vendors and clients    | ensure they stay informed of upcoming deadlines                               |
| `*`      | Wedding organizer          | set up recurring tasks for common preparations     | avoid manually creating the same tasks for each event                         |

### Use Cases

For all use cases below, refer to the [User Guide](UserGuide.md) for more details.

#### **Use Case: Add a Contact**

**MSS:**

1. Wedding Organizer adds a contact using the add command.
   Use case ends.

**Extensions:**

- **1a.** Name, phone, email, address prefixes not inputed
    - **1a1.** Error message displayed in result display.
        - Use case ends.
- **1b.** Name is blank, contains invalid symbol or beyond 70 characters
    - **1b1.** Error message displayed in result display.
        - Use case ends.
- **1c.** Phone is not 8 digits, and does not begin with '8' or '9'
    - **1c1.** Error message displayed in result display.
        - Use case ends.
- **1d.** Email is blank, or does not contain '@'
    - **1d1.** Error message displayed in result display.
        - Use case ends.
- **1e.** Address is blank
    - **1e1.** Error message displayed in result display.
        - Use case ends.
- **1f.** Role is not one word
    - **1f1.** Error message is displayed in result display.  
- **1g.** Wedding is blank when prefix is inputted, or is not one word
    - **1g1.** Error message is displayed in result display.
- **1h.** Wedding field is not an index.
    - **1h1.** Error message is displayed in result display.
- **1i.** Wedding index inputted is not in the list.
    - **1i1.** Error message is displayed in result display.

#### **Use Case: View a Contact**
1. Wedding Organizer views a contact using the view command.

**Extensions:**

- **1a.** No prefixes inputted.
    - **1a1.** Error message displayed in result display.
        - Use case ends.
- **1b.** Person name does not exist in the address book
    - **1b1.** Error message displayed in result display.
        - Use case ends.
- **1c.** Person index is not in the displayed list
    - **1c1.** Error message displayed in result display.
        - Use case ends.
- **1d.** Duplicate contacts with name containing inputted name
    - **1d1.** Bridal Boss filters the person list to show contacts containing inputted name.
    - **1d2.** Person list filtered to contain contacts with names containing inputted name.
    - **1d3.** Error message is displayed in result display.
        - Use case ends.
- **1e.** Person is a client of a wedding
    - **1e1.** Error message is displayed in result display.
        - Use case ends.

#### **Use Case: Filter Contacts**
1. Wedding Organizer filters contacts using the filter command.

**Extensions:**

- **1a.** No prefixes inputted.
    - **1a1.** Error message displayed in result display.
        - Use case ends

#### **Use Case: Delete a Contact**
1. Wedding Organizer deletes a contact using the delete command.

**Extensions:**

- **1a.** Person name does not exist in the address book
    - **1a1.** Error message displayed in result display.
        - Use case ends.
- **1b.** Person index is not in the displayed list
    - **1b1.** Error message displayed in result display.
        - Use case ends.
- **1c.** Duplicate contacts with name containing inputted name
    - **1c1.** Bridal Boss filters the person list to show contacts containing inputted name.
    - **1c2.** Use case resumes from step 1.
- **1d.** Person is a client of a wedding
    - **1d1.** Error message is displayed in result display.
        - Use case ends.

#### **Use Case: Add a Wedding**

**MSS:**

1. Wedding Organizer adds a wedding using the addw command.
   Use case ends.

**Extensions:**

- **1a.** Contact name does not exist in the address book
    - **1a1.** Error message displayed in result display.
        - Use case ends.
- **1b.** Contact/Wedding index is not in the displayed list
    - **1b1.** Error message displayed in result display.
        - Use case ends.
- **1c.** Duplicate contacts with name containing inputted name
    - **1c1.** Bridal Boss filters the person list to show contacts containing inputted name.
    - **1c2.** Use case resumes from step 1.
  - **1d.** Contact is already a client of another wedding
    - **1d1.** Error message displayed in result display.
        - Use case ends.

#### **Use Case: Assign a contact to a Wedding**

**MSS:**

1. Wedding Organizer assigns the person (by name or index) to the wedding (by index) using the assign command.
   Use case ends.

**Extensions:**
- **1a.** Contact name does not exist in the address book
    - **1a1.** Error message displayed in result display.
        - Use case ends.
- **1b.** Contact/Wedding index is not in the displayed list
    - **1b1.** Error message displayed in result display.
        - Use case ends.
- **1c.** Duplicate contacts with name containing inputted name
    - **1c1.** Bridal Boss filters the person list to show contacts containing inputted name.
    - **1c2.** Use case resumes from step 1.
- **1d.** Contact is already assigned to the wedding
    - **1d1.** Error message displayed in result display.
        - Use case ends.
- **1e.** Contact is the client of the wedding
    - **1e1.** Error message displayed in result display.
        - Use case ends.

---

## **Appendix: Requirements**

### Non-Functional Requirements

1. **Performance Requirements:**
    - **Response Time:** The system should respond to user commands within **1 second** under normal operating conditions (e.g., managing up to **50 weddings** and **500 contacts**). This ensures the application remains efficient for fast typists and small to medium-sized wedding organizers.
    - **Startup Time:** The system should start up and be fully usable within **5 seconds** on modern machines (e.g., with at least 4GB RAM), ensuring quick access without reliance on any installer or external dependencies.

2. **Portability:**
    - The system must work cross-platform on **Windows 10/11** and **macOS 10.15 (Catalina)** or later, running **Java 17** and above. It should avoid the use of platform-specific libraries or features, ensuring it can be used by a wide range of users across different environments.
    - The product must be packaged as a single **JAR file** to comply with the single-file packaging constraint, avoiding external dependencies that could complicate installation or distribution.

3. **Usability:**
    - The system should offer a **CLI-first experience** optimized for fast typists, with a user interface that caters to wedding organizers who prefer text-based commands.
    - The GUI should primarily provide feedback and display information, while command-based input remains the primary interaction method.

4. **Data Storage:**
    - All data should be stored locally in a human-readable format (e.g., JSON), allowing users to back up and edit data manually if necessary.
    - The system should not require a database management system (DBMS), ensuring ease of setup and maintenance.

5. **Reliability and Fault Tolerance:**
    - The system should have robust error handling that gives users clear feedback when errors occur.
    - In case of errors, the system should allow users to retry operations or resolve issues without losing data.

6. **Maintainability:**
    - The codebase should follow **Object-Oriented Programming (OOP) principles** and be modular to facilitate future maintenance and extension.
    - Each component should be well-documented and follow best practices to ensure maintainability.
    - Incremental updates are recommended to align with the **Incremental Delivery** approach, ensuring that the product evolves gradually.

7. **Testability:**
    - The system should include unit and integration tests to ensure code quality.
    - Manual testing should be straightforward, with clear feedback provided to testers. Detailed instructions for manual testing should be provided in the developer documentation.

8. **Documentation:**
    - Both **User Guide** and **Developer Guide** must be provided.
    - The User Guide should explain how to interact with the CLI and manage wedding data.
    - The Developer Guide should detail system architecture, design decisions, and instructions for extending the application.

9. **Compliance:**
    - The system must operate entirely offline without dependence on a remote server, ensuring data privacy and usability without internet access.
    - Legal compliance should be considered regarding data storage and user privacy, but given the local nature of data storage, extensive data security measures are not required.

10. **Graphical Interface:**
    - The GUI should be responsive and display correctly on common screen resolutions, including when the application window is maximized.
    - The interface should be usable on screens with resolutions of **1280x720** and higher.

---

### Glossary

- **Wedding Organizer:** A professional responsible for planning and managing wedding events, coordinating with clients and vendors.
- **Client:** The individuals who have hired the wedding organizer, typically the bride and groom.
- **Stakeholders:** All parties involved in the wedding event, including clients, vendors, and participants.
- **Role:** A role assigned to contacts to categorize and filter them (e.g., "Florist", "Photographer").
- **Contact:** An entry in the system containing information about a person or vendor.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>
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

### Editing a person

Success action: Details of edited contact shown in the status message, person in person list is edited.

#### Editing by INDEX

1. Editing a person while all persons are being shown.

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `edit`<br>
      Test case: `edit 1`<br>
      Expected: No person is edited. `edit` command format is shown in the status message.
    
   1. Test case: `edit 0 [n/NEW NAME] [p/NEW PHONE] [e/NEW EMAIL] [a/NEW ADDRESS]`<br>
      Test case: `edit x [n/NEW NAME] [p/NEW PHONE] [e/NEW EMAIL] [a/NEW ADDRESS]` (where x is a negative number) <br>
      Expected: No wedding added. `edit` command format is shown in the status message.

   1. Test case: `edit x [n/NEW NAME] [p/NEW PHONE] [e/NEW EMAIL] [a/NEW ADDRESS]` (where x is larger than the size of the wedding list)<br>
      Expected: No wedding added. Error message prompting the user to choose an index within the range shown.

    1. Test case: `edit 1 n/NEW NAME`<br>
       Expected (Valid Name): First person has name field edited to NEW NAME. Success action will be carried out.<br>
       Expected (Invalid Name): No person is edited. Error message with name restrictions shown in status message.

   1. Test case: `edit 1 p/NEW PHONE`<br>
      Expected (Valid Phone): First contact has phone field edited to NEW PHONE. Success action will be carried out.<br>
      Expected (Invalid Phone): No person is edited. Error message with phone restrictions shown in status message.

   1. Test case: `edit 1 e/NEW EMAIL`<br>
      Expected (Valid Email): First contact has email field edited to NEW EMAIL. Success action will be carried out.<br>
      Expected (Invalid Email): No person is edited. Error message with email restrictions shown in status message.

   1. Test case: `edit 1 a/NEW ADDRESS`<br>
      Expected (Valid Address): First contact has address field edited to NEW ADDRESS. Success action will be carried out.<br>
      Expected (Invalid Address): No person is edited. Error message with address restrictions shown in status message.

   1. Test case: `edit 1 n/EXISTING NAME p/EXITING PHONE e/EXISTING EMAIL a/EXISTING ADDRESS`<br>
      Expected: No person is edited. Error message shows person already exist in status message.

   1. Test case: `edit 1 p/EXITING PHONE`<br>
      Expected: No person is edited. Error message shows phone already exist in status message.

   1. Test case: `edit 1 e/EXISTING EMAIL`<br>
      Expected: No person is edited. Error message shows email already exist in status message.

#### Editing by NAME

1. Since `edit NAME ...` searches from the entire list of contacts, rather than only the partial list, it works either way.

   1. Test case: `edit Alice n/Alice Teo`<br>
       Expected (No duplicated Alice): Person with name field containing Alice has name field edited to Alice Teo. Success action will be carried out.<br>
       Expected (Duplicated Alice): No person edited. Person list is filtered to show only contacts with name field containing Alice. Status message shows message to input person by indexing.<br>
       Expected (No Alice): No person edited. Error message shows this person do not exist in the address book.


### Viewing a person

Success action: When a person is successfully viewed, the details of the viewed contact is shown in the person list. Status message shows that that contact is viewed. 
The weddings involved of the person will be reflected in the wedding list on the right.

#### Viewing with INDEX

1. Viewing a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    1. Test case: `view 1` <br>
       Expected: First contact from the list is viewed. Success action will be carried out for that contact.

    1. Test case: `view 0`<br>
       Expected: No person is viewed. `view` command format is shown in the status message.

    1. Test case: `view x` (where x is larger than the size of person list)<br>
       Expected: Error message prompting the user to choose an index within the range shown.

    1. Other incorrect view commands to try: `view`, `view x` (where x is a negative integer)<br>
       Expected: Similar to point #1(iii). 

1. Viewing a person while a partial list of contacts is shown.

    1. Prerequisite: List some of the contacts using `view NAME` or `filter` command, where a partial list of contacts that matches NAME will be shown, assuming there are multiple of such contacts.

    1. Test cases used are similar, except that the index will follow that of the shown list.

#### Viewing with NAME

1. Since `view NAME` searches from the entire list of contacts, rather than only the partial list, it works either way.

    1. Test case: `view Alice Tan` <br>
       Expected (Unique Alice Tan): The contact of `Alice Tan` is viewed. Success action will be carried out for that contact.<br>
       Expected (Duplicated Alice Tan): Contacts with name field containing `Alice Tan` exactly will be shown. Status message shows number of contacts shown and prompts user to re-input using index according to the newly filtered list to specify which `Alice Tan` they want to view. <br>
       Expected (No Alice Tan): No person is viewed. Error details is shown in the status message, as the NAME does not belong to anyone in the address book.


### Deleting a person

#### Deleting with INDEX

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. <br><br>

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. `delete` command format is shown in the status message.

   1. Test case: `delete x` (where x is larger than the size of person list)<br>
      Expected: Error message prompting the user to choose an index within the range shown.

   1. Other incorrect delete commands to try: `delete`, `delete x` (where x is a negative integer)<br>
      Expected: Similar to point #1(iii).

1. Deleting a person while a partial list of contacts is shown.

    1. Prerequisite: List some of the contacts using `view NAME`, `delete NAME` or `filter` command, where a partial list of contacts that matches NAME will be shown, assuming there are multiple of such contacts.

    1. Test cases used are similar, except that the index will follow that of the shown list.

#### Deleting with NAME

1. Since `delete NAME` searches from the entire list of contacts, rather than only the partial list, it works either way.

   1. Test case: `delete Alice Tan` <br>
      Expected (Unique Alice Tan): The contact of `Alice Tan` will be deleted. Details of the deleted contact shown in the status message. <br>
      Expected (Duplicated Alice Tan): Contacts with name field containing `Alice Tan` exactly will be shown. Status message prompts user to re-input using index according to the newly filtered list to specify which `Alice Tan` they want to delete. <br>
      Expected (No Alice Tan): No person is deleted. Error details is shown in the status message, as the NAME does not belong to anyone in the address book.


### Filtering persons

1. Filtering while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    2. Test case: `filter n/John`<br>
       Expected: All the contacts with the exact name match "John" (case-insensitive matching) are shown.

    3. Test case: `filter r/vendor e/gmail`<br>
       Expected: All the contacts who have the role "vendor" OR have "gmail" in their email are shown.

    4. Test case: `filter n/Alex Tan`<br>
       Expected: Error message is shown as the name field must be a single word. Error details shown in the status message.

2. Invalid filter commands to try:

    1. Test case: `filter`<br>
       Expected: `filter` command format is shown in the status message, since at least one filter criteria must be provided.

    2. Test case: `filter n/`<br>
       Expected: `filter` command format is shown in the status message, since parameter cannot be left empty.

    3. Test case: `filter x/value`<br>
       Expected: `filter` command format is shown in the status message, since there is an unknown prefix.

3. Edge cases to test:

    1. Test case: `filter n/john`<br>
       Expected: The names of contacts will be matched and shown regardless of case (e.g., "John", "JOHN", "JoHn").

    2. Test case: `filter e/gmail a/street`<br>
       Expected: All the contacts with either "gmail" in email OR "street" in address will be shown.

    3. Test case: `filter p/91234567`<br>
       Expected: Only the contacts with the exact phone number will be shown.

4. Filtering a person while a filtered list of contacts is shown

    1. Prerequisite: A partial list of contacts is shown.

    1. Test cases used can be the same since `filter` searches from the entire list of contacts, rather than only the partial list.

    
### Adding a Wedding
Success action: When wedding is successfully added, the details of the added wedding is shown in the status message and reflected in the wedding list.

#### Inputting CLIENT using INDEX
1. Adding a wedding while all persons and weddings are shown.

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    1. Test case: `addw n/Church Wedding c/1 d/2024-12-12 v/Church of the Holy Spirit`<br>
       Expected: Wedding added with first person in the persons list set as client, with given date and venue. Success action will be carried out.

    1. Test case: `addw n/Church Wedding c/1`<br>
       Expected: Wedding added with first person in the persons list set as client, with no date and venue. Success action will be carried out.

    1. Test case: `addw`<br>
       Test case: `addw n/Church Wedding`<br>
       Test case: `addw c/1`<br>
       Expected: No wedding added. `addw` command format is shown in the status message.

    1. Test case: `addw n/Wedding 1 c/1`<br>
       Expected: No wedding added. Error message with restrictions on WEDDING_NAME shown in status message.

    1. Test case: `addw n/Wedding c/1.5`<br>
       Expected: No wedding added. Error message with CLIENT input options shown in status message.

    1. Test case: `addw n/Wedding c/1 d/2024-13-50`<br>
       Expected: No wedding added. Error message with restrictions on DATE shown in status message.

    1. Test case: `addw n/Wedding c/1 v/`<br>
       Expected: No wedding added. Error message with restrictions on VENUE shown in status message.

    1. Test case: `addw n/Church Wedding c/0`<br>
       Test case: `addw n/Church Wedding c/x` (where x is a negative number) <br>
       Expected: No wedding added. Error message about invalid index shown, as x is not a non-zero unsigned integer.

    1. Test case: `addw n/Church Wedding c/x` (where x is larger than the size of the wedding list)<br>
       Expected: No wedding added. Error message prompting the user to choose an index within the range shown.

#### Inputting CLIENT using NAME
1. Since `addw` using `c/NAME` searches for the client from the entire list of contacts, rather than only the partial list, it is works either way.

    1. Test case: `addw n/Church Wedding c/Alice`
       Expected (No duplicated Alice): Wedding added with contact having name field containing Alice set to be client. Details of the added wedding is displayed on the status message.<br>
       Expected (Duplicated Alice): No wedding added. Person list is filtered to show contacts with names containing Alice. Status message prompts user to re-input CLIENT using index according to the newly filtered list.<br>
       Expected (No Alice): No wedding added. Error message shown in status message, as the NAME does not belong to anyone in the address book.


### Editing a Wedding
Success action: When wedding is successfully edited, the details of the updated wedding is shown in the status message and reflected in the wedding list.

1. Editing a Wedding with all persons and weddings shown.<br>

    1. Prerequisites: List all contacts and weddings using the `list` command. Multiple persons in the list.

    1. Test case: `editw w/1 [n/NAME] [d/DATE] [v/VENUE]`<br>
       Expected: First wedding in list is edited with the given inputs. Success action will be carried out.

    1. Test case: `editw`<br>
       Test case: `editw w/1`<br>
       Test case: `editw w/1 c/1` <br>
       Expected: No wedding edited. `editw` command format is shown in the status message.

    1. Test case: `editw w/0 [n/NEW WEDDING NAME] [d/NEW DATE] [v/NEW VENUE]`<br>
       Test case: `editw w/x [n/NEW WEDDING NAME] [d/NEW DATE] [v/NEW VENUE]` (where x is a negative number) <br>
       Expected: No wedding edited. `editw` command format is shown in the status message.

    1. Test case: `addw w/x [n/NEW WEDDING NAME] [d/NEW DATE] [v/NEW VENUE]` (where x is outside of the range of wedding list or a non-numeric character)<br>
       Expected: No wedding edited. Error message prompting the user to choose an index within the range shown.


### Viewing weddings

Success action: When wedding is successfully viewed, the details of the viewed wedding is shown in the status message and reflected in wedding list.
The persons involved in the viewed wedding will be shown in the person list. 

#### Viewing weddings using INDEX

1. Viewing of a wedding while all weddings are being shown

    1. Prerequisites: List all weddings using the `list` command. Multiple weddings in the list.

    1. Test case: `vieww 1` <br>
       Expected: First wedding from the wedding list is viewed. Success action will be carried out for that wedding.

    1. Test case: `vieww 0`<br>
       Expected: No wedding is viewed. `vieww` command format is shown in the status message.

    1. Test case: `vieww x` (where x is larger than the size of wedding list)<br>
       Expected: Error message prompting the user to choose an index within the range shown.

    1. Other incorrect vieww commands to try: `vieww`, `vieww x` (where x is a negative integer)<br>
       Expected: Similar to point #1(iii).

1. Viewing of a wedding while a partial list of weddings is shown. 

    1. Prerequisite: List some of the weddings using `vieww NAME` command, where a partial list of weddings that matches NAME will be shown, assuming there are multiple of such weddings.

    1. Test cases used are similar, except that the index will follow that of the shown list.

#### Viewing weddings using WEDDING_NAME

1. Since `vieww WEDDING_NAME` searches from the entire list of contacts, rather than only the partial list, it works either way.

    1. Test case: `vieww Alice` <br>
       Expected (Unique Alice): The wedding of `Alice` will be shown. Success action will be carried out for that wedding.<br>
       Expected (Duplicated Alice): Weddings with name field matching `Alice` exactly will be shown. Status message prompts user to re-input using index according to the newly filtered list to specify which wedding of `Alice` they want to view.<br>
       Expected (No Alice): No wedding is viewed. Error details is shown in the status message, as the NAME does not belong to any wedding in the address book.


### Deleting weddings

Success action: When a wedding is successfully deleted, the details of the deleted wedding is shown in the status message. The client of the wedding will have their wedding status reset.
Persons who are involved in the wedding will also be unassigned.

- To verify this: view the contact itself using `view` command, which will show the weddings the person is involved in. The deleted wedding should not be included.

#### Deleting wedding using INDEX

1. Deleting a wedding while all weddings are being shown

    1. Prerequisites: List all weddings using the `list` command. Multiple weddings in the list.

    1. Test case: `deletew 1`<br>
       Expected: First wedding is deleted from the list. Success action will be carried out for that wedding.

    1. Test case: `deletew 0`<br>
       Expected: No wedding is deleted. `deletew` command format is shown in the status message.

    1. Test case: `deletew x` (where x is larger than the size of wedding list)<br>
       Expected: Error message prompting the user to choose an index within the range shown.

    1. Other incorrect deletew commands to try: `deletew`, `deletew x` (where x is a negative integer)<br>
       Expected: Similar to point #1(iii).

1. Deleting of a wedding while a partial list of weddings is shown.

    1. Prerequisite: List some of the weddings using `vieww NAME` or `deletew NAME` command, where a partial list of weddings that matches NAME will be shown, assuming there are multiple of such weddings.

    1. Test cases used are similar, except that the index will follow that of the shown list.

#### Deleting wedding using WEDDING_NAME

1. Since `deletew WEDDING_NAME` searches from the entire list of contacts, rather than only the partial list, it works either way.

    1. Test case: `deletew Alice` <br>
       Expected (Unique Alice): The wedding of `Alice` will be deleted. Success action will be carried out for that wedding.<br>
       Expected (Duplicated Alice): Weddings with name field containing `Alice` exactly will be shown. Status message prompts user to re-input using index according to the newly filtered list to specify which wedding of `Alice` they want to delete. <br>
       Expected (No Alice): No wedding is deleted. Error details is shown in the status message, as the NAME does not belong to any wedding in the address book.
       Expected: No wedding added. Error message states that the index is invalid, and prompts user to key indexes from within a specified range.<br>

### Assigning roles and weddings

Success action: When a person is successfully assigned:
- If role assigned: Person's role is updated and shown in person list
- If wedding assigned: Person is added to wedding's participants list
- Status message shows successful assignment

#### Assigning by INDEX

1. Assigning a role

    1. Prerequisites: List all persons using `list` command. Multiple persons in the list.

    1. Test case: `assign 1 r/photographer`<br>
       Expected: First person in list is assigned photographer role. Success action carried out.

    1. Test case: `assign 1 r/invalid role name`<br>
       Expected: Error message shown about invalid role format (must be one word).

    1. Test case: `assign 0 r/photographer`<br>
       Expected: Error message shown about invalid person index.

    1. Test case: `assign x r/photographer` (where x is an invalid index (< 0, 0 , larger than list size)))<br>
       Expected: Error message shown about invalid person index.
   
    1. Test case: `assign 1 r/`<br>
       Expected: Role of first person in list is removed. Success action carried out

2. Assigning to weddings

    1. Prerequisites: Multiple weddings exist in wedding list.

    1. Test case: `assign 1 w/1 w/2`<br>
       Expected: First person assigned to both wedding 1 and 2. Success action carried out.

    1. Test case: `assign 1 w/0 w/2`<br>
       Expected: Error message shown about invalid wedding index.

    1. Test case: `assign 1 w/-1 w/2`<br>
       Expected: Error message shown about invalid wedding index.

    1. Test case: `assign 1 w/999 w/2` (where 999 > wedding list size)<br>
       Expected: Error message shown about invalid wedding index.

3. Assigning both role and weddings

    1. Test case: `assign 1 r/photographer w/1 w/2`<br>
       Expected: First person gets photographer role and is assigned to weddings 1 and 2. Success action carried out.

4. Invalid commands

    1. Test case: `assign`<br>
       Expected: Error message showing command format.

    1. Test case: `assign 1`<br>
       Expected: Error message showing need for role and/or wedding assignment.

    1. Test case: `assign -1 r/photographer`<br>
       Expected: Error message about invalid person index.

#### Assigning by NAME

1. Using contact name

    1. Test case: `assign Alice r/photographer`<br>
       Expected (Unique Alice): Alice is assigned photographer role. Success action carried out.<br>
       Expected (Multiple matches): Person list filtered to show matches. Message prompts to use index.<br>
       Expected (No matches): Error message that no such person exists.

    1. Test case: `assign Al!ce r/photographer` (name with special characters)<br>
       Expected: Error message that no such person exists.

    1. Test case: `assign Alice r/`<br>
       Expected (Unique Alice): Removes role from Alice. Success action carried out.<br>
       Expected (Multiple matches): Person list filtered to show matches. Message prompts to use index.<br>
       Expected (No matches): Error message that no such person exists.

2. Complex name assignments

    1. Test case: `assign Alice r/photographer w/1 w/2`<br>
       Expected (Unique Alice): Alice gets role and both wedding assignments. Success action carried out.<br>
       Expected (Multiple matches): Person list filtered to show matches. Message prompts to use index.

#### Special Cases

1. Client assignments

    1. Prerequisites: Person is client of wedding 1

    1. Test case: `assign 1 w/1`<br>
       Expected: Error message that client cannot be assigned to their own wedding.

2. Multiple wedding assignments

    1. Prerequisites: Person already assigned to wedding 1

    1. Test case: `assign 1 w/1 w/2`<br>
       Expected: Error message about already being assigned to wedding 1.

### Removing wedding jobs assigned to a person 

#### Removing with INDEX of person list and wedding list

1. Removing wedding jobs assigned to a person while all weddings are being shown

    1. Prerequisites: List all persons and weddings using the `list` command. Multiple persons and weddings in the list.<br><br>

    1. Test case: `delete 1 w/1`<br>
       Expected: First wedding is unassigned from first person. Details of the unassigned contact shown in the status message. <br><br>

    1. Test case: `delete 0 w/1`<br>
       Expected: No person to unassign wedding job. `delete` command format is shown in the status message.<br><br>

    1. Test case: `delete 1 w/x` (where x is outside of the range of wedding list or a non-numeric character)<br>
       Expected: Error message prompting the user to choose an index within the range shown.<br><br>

1. Removing wedding jobs assigned to a person while a partial list of weddings is shown.

    1. Prerequisite: List some of the weddings using `vieww NAME`, where a partial list of weddings that matches NAME will be shown, assuming there are multiple of such weddings.

    1. Test cases used are similar, except that the index will follow that of the shown list.

#### Removing with NAME of person and INDEX of wedding list

1. Since `delete NAME w/1` searches from the entire list of contacts, rather than only the partial list, it works either way.

    1. Test case: `delete Alice Tan w/1` <br>
       Expected (Unique Alice Tan): The contact of `Alice Tan` will be unassigned from wedding at index 1. Details of the unassigned contact shown in the status message. <br>
       Expected (Duplicated Alice Tan): Contacts with name field containing `Alice Tan` exactly will be shown. Status message prompts user to re-input using index according to the newly filtered list to specify which `Alice Tan` they want to unassign. <br>
       Expected (No Alice Tan): No person to unassign. Error details is shown in the status message, as the NAME does not belong to anyone in the address book.



### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
