---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="281" />

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

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `/e delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="544"  alt=""/>

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="314" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

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

<img src="images/LogicClassDiagram.png" width="534" alt=""/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteVolunteerSequenceDiagram.png)
![Supporting sd Frame](images/DeleteVolunteerSequenceDiagramSdFrame.png)
<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `VolunteerDeleteCommandParser`) and uses it to parse the command.
   - However, if it is a command that creates a new event (e.g. /v new), AddressBookParser creates an instance of `VolunteerCommandParser`, which then creates the `VolunteerNewCommandParser` to parse the command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="668"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `{Command Name}CommandParser` which uses the other classes shown above to parse the user command and create a `{Command Name}Command` object (e.g., `AssignCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `{Command Name}CommandParser` classes (e.g., `AssignCommandParser`, `EventNewCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="488"  alt=""/>


The `Model` component,

* stores the address book data i.e., all `Event` and `Volunteer` objects (which are contained in a `UniqueEventList` and `UniqueVolunteerList` object respectively).
* stores the currently 'selected' `Event` and `Volunteer` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Event>` and `ObservableList<Volunteer` respectively, that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="627"  alt=""/>

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the [`seedu.address.commons`](https://github.com/AY2425S1-CS2103T-W12-2/tp/tree/master/src/main/java/seedu/address/commons) package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

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

* requires efficient management of extensive databases for volunteers and events.
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: This application serves to streamline volunteer and for volunteer organisations.
It provides essential tools to track volunteers and events efficiently, enabling organisations to
maintain accurate records and enhance their operational capabilities.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                  | I want to …​                           | So that I can…​                                                                      |
|----------|------------------------------------------|----------------------------------------|--------------------------------------------------------------------------------------|
| `* * *`  | HR department employee                   | Remove volunteers                      | Keep volunteer records up to date                                                    |
| `* * *`  | HR department employee                   | View volunteers                        | Quickly access and review the list of all volunteers                                 |
| `* * *`  | HR department employee                   | Export volunteer information           | Generate reports for internal use                                                    |
| `* * *`  | Events director                          | Create events                          | Organize new events to engage volunteers                                             |
| `* * *`  | Events director                          | Remove events                          | Keep the events list clean and up to date                                            |
| `* * *`  | Events director                          | View events                            | Get an overview of upcoming and past events                                          |
| `* * *`  | Events director                          | Add volunteer to event                 | Assign volunteers to specific events                                                 |
| `* * *`  | HR department employee                   | Add event to volunteer                 | Track the events a volunteer has participated in                                     |
| `* * *`  | Events director                          | Remove volunteer from event            | Keep the list of volunteers attending the event updated                              |
| `* * *`  | HR department employee                   | Remove event from volunteer            | Keep the events list for the volunteer clean and up to date                          |
| `* *`    | HR department employee                   | Edit volunteer information             | Update volunteer details such as availability, hours, etc.                           |
| `* *`    | Events director                          | Filter volunteers by availability      | Find available volunteers for a particular event                                     |
| `* *`    | Events director, HR department employee  | Search/filter event by name            | Locate specific events quickly                                                       |
| `* *`    | Events director                          | View volunteers for a particular event | Find out how many volunteers have signed up for the event                            |
| `* *`    | Events director, HR department employee  | Search volunteers by name              | Find a specific volunteer by their name                                              |
| `* *`    | HR department employee                   | Track volunteer hours                  | Monitor and log the hours each volunteer has worked                                  |
| `*`      | General user                             | View event details per volunteer       | See which events a volunteer participated in                                         |
| `*`      | HR department employee                   | View volunteer participation history   | Track volunteer engagement with past events                                          |
| `*`      | General user                             | Toggle view options for events         | Customize how events are displayed in the app                                        |
| `*`      | General user                             | Dark mode                              | Enhance the app's user experience for those who prefer a darker interface            |
| `*`      | General user                             | Accessibility features                 | Improve usability for visually impaired users through larger fonts and color changes |

*{More to be added}*

### Use cases

# UML Use Cases: Contact Management Application for Volunteer Organizations
<br/>
For the following use cases, the `Actors` are defined as the Management Staff of Volunteer Organisations, and the `System` is defined as VolunSync, unless specified otherwise.

### UC01. Create Event

**Description**: Create a new event in the system.

**Preconditions**: NA

**MSS**:
1. User enters event's details.
2. User submits the event's details to the system.
3. System checks if all required information is present, and that all information is valid.
4. System creates the new event and confirms creation to the user.

**Extensions**:
- 3a. Information provided is incomplete or invalid.
  - 3ai. System displays error and returns to step 1.
    Use Case Ends.
- 4a. Event creation fails.
  - 4ai. System notifies user and the user can edit the event details, returning to step 2 afterward.
    Use Case Ends.

**Postconditions**:
- New event is stored in the system.

### UC02. Create Volunteer

**Description**: Create a new Volunteer in the system.

**Preconditions**: NA

**MSS**:
1. User enters the volunteer's details.
2. User submits the volunteer's details to the system.
3. System checks if all required information is present, and that all information is valid.
4. System creates the new event and confirms creation to the user.
   Use Case Ends.

**Extensions**:
- 3a. Information provided is incomplete or invalid.
   - 3ai. System displays error and returns to step 1.
     Use Case Ends.
- 4a. Volunteer creation fails.
   - 4ai. System notifies user and the user can edit the event details, returning to step 2 afterward.
     Use Case Ends.

**Postconditions**:
- New volunteer is stored in the system.

### UC03. Assign Volunteer to Event

**Description**: Assign a volunteer to a specific event.

**Preconditions**:
- Event exists in the system.
- Volunteer is registered in the system.

**MSS**:
1. User queries all volunteers and events.
2. System displays list of all volunteers and events.
3. User selects the desired volunteer and event to assign the volunteer to.
4. User submits the information to the system.
5. System adds the volunteer to the event and confirms addition.
   Use Case Ends.

**Extensions**:
- 2a. No volunteers and/or events are found.
  - 2ai. System notifies user and prompts user to create a new volunteer ([UC02 - Create Volunteer](#uc02-create-volunteer)) and / or event ([UC01 - Create Event](#uc01-create-event)).
   Use Case Ends.
- 5a. Volunteer is already assigned to the event.
  - 5ai. System notifies user.
  - 5aii. Volunteer remains assigned to the event.
    Use Case Ends.
-5b. Volunteer is assigned to another event occurring at the same time.
  - 5bi. System notifies user.
  - 5bii. Volunteer is not assigned to the event.
    Use Case Ends.

**Guarantees**:
- Volunteer is associated with the event in the system if the volunteer is not assigned to another event occurring at the same time.

### UC04. Find Volunteer by Name

**Description**: Search for a volunteer by their name.

**Preconditions**: NA

**MSS**:
1. User enters a keyword to search for.
2. System looks up all volunteers whose names contain the keyword.
3. System notifies the number of matches found and displays the list of volunteers whose names contains the keyword.
   Use Case Ends.

**Extensions**:
- 2a. No volunteers whose names contains the keyword are found.
  - 2ai. System notifies user and displays all volunteers.
    Use Case Ends.

## UML Use Case Diagram

```mermaid
graph TD
    A[Management Staff] -->|Creates| B(Create Volunteer Event)
    A -->|Assigns| C(Add Volunteer to Event)
    B -->|Enables| C
    B -->|Enables| D
```

This diagram shows the main actor (Management Staff) and their interactions with the three primary use cases we've defined. The arrows indicate the relationships between the actor and the use cases, as well as dependencies between use cases.


### Non-Functional Requirements

1. The system should work on any mainstream OS with Java 17 or above.
2. The system should be able to handle up to 1000 volunteers, events, and donors without noticeable performance degradation.
3. A user should be able to perform common tasks (add, delete, view) within 5 seconds for typical usage.
4. The system should have a simple and intuitive command-line interface that minimizes the learning curve for new users.
5. System response time for any action should be less than 1 second for all operations.
6. The system should be able to support concurrent users without data corruption or errors.
7. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.

*{More to be added}*

### Glossary

* **Volunteer**: An individual who participates in a community event without monetary compensation.
* **Event**: A planned activity organised by a community or non-profit organisation, requiring volunteer coordination.
* **Donor**: An individual or organization that contributes funds or resources to support community events.
* **Recurring Events**: Events that occur repeatedly on a set schedule.
* **CLI (Command-Line Interface)**: A text-based interface where users input commands to interact with the application.
* **NFR (Non-Functional Requirement)**: System attributes like performance, scalability, and usability that don’t affect specific functional behaviors.
* **Duplicate Handling**: A system feature that prevents the creation of identical entries.
* **MSS**: Main Success Scenario, the primary flow of events in a use case.
--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**
## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   2. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

3. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   2. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   3. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

2. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

2. _{ more test cases …​ }_
