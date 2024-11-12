---
layout: page
title: Developer Guide
---

* Table of Contents
{:toc}
--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* Libraries used: [JavaFX](https://openjfx.io/), [Jackson](https://github.com/FasterXML/jackson), [JUnit5](https://github.com/junit-team/junit5)
* This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org). For the detailed documentation of this project, see the **[Address Book Product Website](https://se-education.org/addressbook-level3)**.

--------------------------------------------------------------------------------------------------------------------

## **Setting Up, Getting Started**

Refer to the guide [_Setting up and getting started_](https://github.com/AY2425S1-CS2103T-W14-4/tp/blob/master/docs/SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document are in the `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-W14-4/tp/blob/master/src/main/java/seedu/eventfulnus/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-W14-4/tp/blob/master/src/main/java/seedu/eventfulnus/MainApp.java)) is in charge of the app launch and shut down.
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

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI Component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-W14-4/tp/blob/master/src/main/java/seedu/eventfulnus/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `EventListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-W14-4/tp/blob/master/src/main/java/seedu/eventfulnus/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-W14-4/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` and `Event` objects residing in the `Model`.

### Logic Component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-W14-4/tp/blob/master/src/main/java/seedu/eventfulnus/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

And the sequence diagram below illustrates the interactions when `execute("deleteevent 1")` is called instead:
![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteEventSequenceDiagram.png)

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
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model Component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-W14-4/tp/blob/master/src/main/java/seedu/eventfulnus/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

Please note that a Person contains multiple Roles and an Event can contain multiple persons. The upper '*', lying on the Person-Role arrow, is for the Event-Person association.

### Role Component
**API** : [`Role.java`](https://github.com/AY2425S1-CS2103T-W14-4/tp/blob/master/src/main/java/seedu/eventfulnus/model/person/role/Role.java)

<img src="images/RoleClassDiagram.png" width="550" />

The `Role` component,

* helps to keep track of the different roles that a person can have in the application.



### Storage Component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-W14-4/tp/blob/master/src/main/java/seedu/eventfulnus/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.eventfulnus.commons` package.

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

MED*Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

MED*Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Other Helpful Resources**

* [Documentation guide](https://github.com/AY2425S1-CS2103T-W14-4/tp/blob/master/docs/Documentation.md)
* [Testing guide](https://github.com/AY2425S1-CS2103T-W14-4/tp/blob/master/docs/Testing.md)
* [Logging guide](https://github.com/AY2425S1-CS2103T-W14-4/tp/blob/master/docs/Logging.md)
* [Configuration guide](https://github.com/AY2425S1-CS2103T-W14-4/tp/blob/master/docs/Configuration.md)
* [DevOps guide](https://github.com/AY2425S1-CS2103T-W14-4/tp/blob/master/docs/DevOps.md)

--------------------------------------------------------------------------------------------------------------------
# Appendix
## **Requirements**

### Product Scope

**Target user profile**:

National University of Singapore's Inter-Faculty Games organisers 
(NUS Students' Sport Club) that need to manage and contact:
- Audience
- Athletes
- Committee Members
- Referees
- Sponsors
- Volunteers
and more.

This person prefers CLI over GUI and has many event participants to keep track of.

**Value proposition**:

All event organizers will be able to:
- View what kind of participant each contact is in the application is
without having to dig through paperwork / NUSync.
- View how participants are grouped by membership (e.g. faculty sports team)
without having to dig through paperwork / NUSync.
- Track the particular participant’s involvement in the event
(e.g. IFG has multiple events that stakeholders can take part in)
without having to dig through paperwork / NUSync.


### User stories

Priorities: High (must have) - `HIGH`, Medium (nice to have) - `MED`, Low (unlikely to have) - `LOW`

| Priority | As a …​          | I want to …​                                                                         | So that I can…​                                                             |
|----------|------------------|--------------------------------------------------------------------------------------|-----------------------------------------------------------------------------|
| `HIGH`   | first-time user  | view a user guide to understand CLI commands                                         | understand the basic functions and navigation of the app                    |
| `HIGH`   | first-time user  | add a new participant                                                                | start building my participant list from scratch                             |
| `HIGH`   | first-time user  | view a summary of all participants grouped by their sport categories                 | get an overview of participant distribution                                 |
| `HIGH`   | first-time user  | search for participants by name, phone number or email                               | locate details of participants without having to go through the entire list |
| `HIGH`   | familiar user    | update a participant's details                                                       | keep participant records current and accurate                               |
| `HIGH`   | familiar user    | assign participants to specific events                                               | track their involvement and ensure proper scheduling                        |
| `HIGH`   | familiar user    | group participants by their faculty membership                                       | manage and organize participants according to faculty membership            |
| `HIGH`   | familiar user    | delete a participant                                                                 | remove outdated or incorrect participant records                            |
| `HIGH`   | familiar user    | update an event's details                                                            | keep events records current and accurate                                    |
| `HIGH`   | familiar user    | delete an event                                                                      | remove outdated or incorrect event records                                  |
| `HIGH`   | familiar user    | search for events via keywords                                                       | locate details of events without having to go through the entire list       |
| `MED`    | first-time user  | view detailed information about a participant on a separate tab from main search     | ensure I have accurate and complete information on a participant            |
| `MED`    | first-time user  | view a sample event schedule with participants' relevant details shown               | understand how to manage and track participants' involvement                |
| `MED`    | familiar user    | search for participants based on multiple criteria                                   | find specific groups of participants efficiently                            |
| `MED`    | familiar user    | view a summary of a participant’s involvement in past events                         | understand their history and performance in previous events                 |
| `MED`    | expert user      | create custom participant fields specific to different types of events               | tailor the application to various event requirements                        |
| `MED`    | expert user      | customize CLI command shortcuts and aliases for frequently used actions              | enhance productivity and streamline workflows                               |
| `MED`    | expert user      | use advanced search filters to find participants                                     | find participants based on specific information with precision              |
| `LOW`    | familiar user    | view a report of participants based on their sport category or faculty membership    | understand how participants are distributed                                 |
| `LOW`    | familiar user    | view edit history of participant details                                             | track updates and maintain data integrity                                   |
| `LOW`    | expert user      | set up automated notifications for participants based on their roles                 | keep them informed without manual follow-up                                 |
| `LOW`    | expert user      | bulk update participant details or event assignments                                 | efficiently manage changes for large numbers of participants                |
| `LOW`    | expert user      | generate complex, customized reports combining multiple data points                  | gain detailed insights into event management                                |
| `LOW`    | expert user      | schedule regular backups of participant database                                     | ensure data is protected and recoverable in case of issues                  |


### Use cases
**System: EventfulNUS**\
**Use case: UC1 - Add participant**\
**Actor: User**\
**Guarantee: MSS → Participant details are successfully added**

MSS:
1. User requests to add a participant
2. System adds the participant and displays a message indicating no errors.

Extensions:\
1a. System detects an error in the entered data.\
1a1. System prompts user to re-enter data\
1a2. User enters new data\
Steps 1a1-1a2 are repeated until the data entered is correct.
Use case resumes from step 2.

<hr>

**System: EventfulNUS**\
**Use case: UC2 - Search for participant**\
**Actor: User**\
**Guarantee: MSS → The details of the participant being searched for will be displayed**

MSS:
1. User requests to search for participant
2. System finds the participant and displays the participant’s data

Extensions:\
1a. System detects an error in the entered data.\
1a1. System prompts user to re-enter data.\
1a2. User enters new data.\
Steps 1a1-1a2 are repeated until the data entered is correct.
Use case resumes from step 2.

1b. System does not find any matching participant in the system.\
1b1. System prompts user to re-enter data.\
1b2. User enters new data.\
Steps 1b1-1b2 are repeated until the data entered is correct.
Use case resumes from step 2.

<hr>

**System: EventfulNUS**\
**Use case: UC3 - See tutorial / list of commands**\
**Actor: User**\
**Guarantee: MSS → The tutorial / list of commands will be displayed**

MSS:
1. User requests to see tutorial / list of commands.
2. System displays the tutorial / list of commands

Extensions:\
1a. System detects an unknown command.\
1a1. System prompts user to re-enter the command.\
1a2. User enters new command.\
Steps 1a1-1a2 are repeated until the command is correctly entered.
Use case resumes from step 2.

<hr>

**System: EventfulNUS**\
**Use case: UC4 - Update participant’s details**\
**Actor: User**\
**Guarantee: MSS → The specified participant’s details will be updated to match the provided details**

MSS:
1. User requests to update a participant’s details
2. System updates and displays the specified participant’s data

Extensions:\
1a. System detects an error in the entered data.\
1a1. System prompts user to re-enter data.\
1a2. User enters new data.\
Steps 1a1-1a2 are repeated until the data entered is correct.\
Use case resumes from step 2.

1b. System does not find the specified participant in the system.\
1b1. System prompts user to re-enter data.\
1b2. User enters new data.\
Steps 1b1-1b2 are repeated until the data entered is correct.\
Use case resumes from step 2.

<hr>

**System: EventfulNUS**\
**Use case: UC5 - Assign participants to specific events**\
**Actor: User**\
**Guarantee: MSS → The specified participant will be assigned to a specified event**\

MSS:
1. User requests to assign a participant to a event
2. System updates and displays the specified participant’s event

Extensions:\
1a. System detects an error in the entered data.\
1a1. System prompts user to re-enter data\
1a2. User enters new data.\
Steps 1a1-1a2 are repeated until the data entered is correct.
Use case resumes from step 2.

1b. System does not find specified participant in the system.\
1b1. System prompts user to re-enter data.\
1b2. User enters new data.\
Steps 1b1-1b2 are repeated until the data entered is correct.\
Use case resumes from step 2.

<hr>

**System: EventfulNUS**\
**Use case: UC6 - Delete person**\
**Actor: User**\
**Guarantee: MSS → The person at the specified index will not be present in the system after the command is entered**

MSS:
1. User requests to delete a person
2. System deletes the person and displays a message indicating no errors.

Extensions:\
1a. System detects an error in the entered data.\
1a1. System prompts user to re-enter data.\
1a2. User enters new data\
Steps 1a1-1a2 are repeated until the data entered is correct.\
Use case resumes from step 2.

1b. System does not find specified person in the system.\
1b1. System prompts user to re-enter data.\
1b2. User enters new data.\
Steps 1b1-1b2 are repeated until the data entered is correct.\
Use case resumes from step 2.

<hr>

<hr>

**System: EventfulNUS**\
**Use case: UC8 - Add event**\
**Actor: User**

MSS:
1. User requests to add an event
2. System adds the event and displays a message indicating no errors.

Extensions:
1a. System detects an error in the entered data.
1a1. System prompts user to re-enter data.
1a2. User enters new data.
Steps 1a1-1a2 are repeated until the data entered is valid.
Use case resumes from step 2.

<hr>

**System: EventfulNUS**\
**Use case: UC9 - Delete event**\
**Actor: User**\
**Guarantee: MSS → The event at the specified index will not be present in the system after the command is entered**

MSS:
1. User requests to delete an event
2. System deletes the event and displays a message indicating no errors.

Extensions:\
1a. System detects an error in the entered data.\
1a1. System prompts user to re-enter data.\
1a2. User enters new data.\
Steps 1a1-1a2 are repeated until the data entered is valid.\
Use case resumes from step 2.

1b. System does not find specified event in the system.\
1b1. System prompts user to re-enter data.\
1b2. User enters new data.\
Steps 1b1-1b2 are repeated until an index corresponding to an existing event is entered.\
Use case resumes from step 2.

### Non-Functional Requirements

#### 1. Additional Requirements
1.1. The system should work on any **mainstream OS** as long as it has **Java 17** or above installed.
1.2. The system should be able to hold up to **1,000 persons** without noticeable sluggishness in performance for typical usage.
1.3. A user with **above average typing speed** for regular English text (i.e. not code, not system admin commands) should be able to accomplish most tasks faster using commands than using the mouse.

#### 2. Performance
2.1. The system should respond within **2 seconds** after the user types in a command.
2.2. The system should start up within **5 seconds**.

#### 3. Scalability
3.1. The system should be able to handle up to **10,000+ users** without degradation in performance.
3.2. An event should be able to handle **1,000+ participants**.
3.3. A participant should be able to keep track of **10+ events** at once.

#### 4. Portability
4.1. The system should run on **all OS systems** (e.g. Windows, Mac, Linux).
4.2. The system should work on both **32-bit and 64-bit environments**.

#### 5. Security
5.1. Sensitive data should be **safely encrypted**.

#### 6. Maintainability
6.1. Codebase should be easy to maintain and follow **industry-standard design practices**.
6.2. **Coupling** should be reduced to allow for easier testing of features.
6.3. Codebase should aim for **high cohesion** to increase the understandability of individual components.

#### 7. Usability
7.1. The system should be **easy-to-use** for a novice, who does not have a lot of experience using CLI-based apps.
7.2. The system should offer a **user-friendly** and **intuitive CLI syntax**, minimizing the learning curve for new users.

#### 8. Process
8.1. The project is expected to incrementally improve the app by adding **new features each week**.

#### 9. Reliability
9.1. The system should be able to **recover from failures** without losing data or interrupting service.


### Glossary

* **CLI**: Short for Command Line Interface, is a text-based interface where the user types commands for the system to pick up
* **GUI**: Short for Graphical User Interface, is an interface made up of a set of buttons/menus which the user can use to make the system run specific tasks.
* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Instructions for manual testing**

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


### Adding a person

1. Adding a person with all fields

   1. Prerequisites: No persons in the list.

      1. Test case: `add n/Alice p/91234567 e/alice@mail.com r/
         Expected: A person with the given details is added to the list. Details of new contact shown in the status message. Timestamp in the status bar is updated.
      1. Test case: `add n/Bob p/98765432
         Expected: No person is added. Error details shown in the status message. Status bar remains the same.


### Editing a person

1. Editing a person with all fields

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `edit 1 n/Alice Paul p/91234567
      Expected: First contact is updated with the new details. Details of the updated contact shown in the status message. Timestamp in the status bar is updated.
   2. Test case: `edit 0 n/Bob Paul
      Expected: No person is updated. Error details shown in the status message. Status bar remains the same.

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.


### Adding an event

1. Adding an event with all fields

   1. Prerequisites: No events in the list.

   1. Test case: `addevent sp/Chess t/COM t/BIZ d/2024 12 12 1800 v/USC`
      Expected: An event with the given details is added to the list. Details of new event shown in the status message. Timestamp in the status bar is updated.
   2. Test case: `addevent sp/Chess t/COM v/usc`
      Expected: No event is added. Error details shown in the status message. Status bar remains the same.


### Editing an event

1. Editing an event with all fields

   1. Prerequisites: List all events using the `listevent` command. Multiple events in the list.

   1. Test case: `editevent 1 sp/Chess d/2024 12 12 1800 v/USC`
      Expected: First event is updated with the new details. Details of the updated event shown in the status message. Timestamp in the status bar is updated.
   2. Test case: `editevent 0 sp/Chess t/COM v/usc`
      Expected: No event is updated. Error details shown in the status message. Status bar remains the same.


### Deleting an event

1. Deleting an event while all events are being shown

   1. Prerequisites: List all events using the `listevent` command. Multiple events in the list.

   1. Test case: `deleteevent 1`<br>
      Expected: First event is deleted from the list. Details of the deleted event shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `deleteevent 0`<br>
      Expected: No event is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `deleteevent`, `deleteevent x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.


--------------------------------------------------------------------------------------------------------------------

### Planned Enhancements
Team size: 5

1. Allow differentiation between Team 1 and Team 2 in AddEvent Command. 
For future versions, we plan to allow the user to specify Team 2 before Team 1.
This can be done by adding different prefixes for each team (e.g. such as t1/ and t2/).
Note that when adding or editing events, duplication checks would occur regardless of the order of team 1 and team 2.

2. Allow user to edit participant list without having to replace the entire list.
Currently, if a user edits the participant list, the entire list is replaced. 
We plan on fixing this through new command that allow appended edits to both events and persons.

3. Multi-word search
Currently, when a user searches for a multi-word keyword, it treats them like separate keywords. 
We plan to fix this, such that the search treats the multi-word keyword (e.g Swimming Men) as a single keyword. 
This can be done through the use of delimiters (e.g. commas) to separate the keywords.

4. Multiple faculties in one event
In events, such as swimming, relays, multiple faculties (i.e. more than 2) can compete against one another.
Currently, this feature is not supported by out app, as an event is limited to two.
Specific commands can be implemented for certain sports.
In future versions, we plan to allow multiple faculties to compete in one event.

5. Name Feature should accept multiple languages
Currently, our app only accepts standard English names.
We plan to allow the user to input names in multiple languages by making the name field less restrictive.

6. Import/Export data from/to CSV file
We plan to allow users to import/export their data from/to a CSV file for easy sharing and backup.
7. Indentation for Use Case Extensions
We plan to add indentation to the use case extensions to make it easier to read and more aesthetically pleasing.

