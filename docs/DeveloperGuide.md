---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# MATER Developer Guide

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

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-T14-3/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-T14-3/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete-client 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-T14-3/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter`, `HelpWindow`, `ViewClientWindow` . All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-T14-3/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-T14-3/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-T14-3/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete-client 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete-client 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteClientCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteClientCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddClientCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddClientCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddClientCommandParser`, `DeleteClientCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-T14-3/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)
* Each `Person` object may contain 1 `Car` object, which in turn contains a `Vrn`, `Vin`, `CarMake`, `CarModel` Object.
* If a `Person` object has a `Car` object, it may also contain 1 or more `Issue` objects.


</box>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-T14-3/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

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

### View Client feature

#### Implementation

The View Client mechanism is facilitated by `ViewClientWindow`. It extends `UiPart<Stage>`. Additionally, it requires the following operations:

* `ViewClientCommandPasrser#parse()` given the arguments succeeding the `view-client` command, parse the appropriate index to ViewClientCommand.
* `ViewClientCommand#execute()` given the list of Clients, identify the indexed Client which would be displayed on the ViewClientWindow.

Given below is an example usage scenario and how the View Client mechanism behaves at each step.

Step 1. The user launches the application, all clients will be listed by default. **OR** The user calls a List/ Find command.

Step 2. The user executes `view-client 1` command to view the first person in the Client list.

<box type="info" seamless>

**Note:** Changes to Client details (via `edit-client`, `add-car`, `delete-car` etc.) while the `MATER - View Client` Window is open will not be immediately reflected (see `Correct as of` for the timestamp). `view-client` must be called again to reflect these changes.

</box>

Step 3. Before closing the existing `MATER - View Client` Window, the user executes `view-client 2` command to view the second person in the Client list.

<box type="info" seamless>

**Note:** Only one `MATER - View Client` Window is to be displayed at all times. Calling `view-client` again while the window is open will refresh the window for the latest request.

</box>

Step 4. The user closes the `MATER - View Client` Window by either pressing the Close button (bottom) or Terminate button (top).

The following sequence diagram shows how a `view-client` operation goes through the `UI` and `Logic` component:

<puml src="diagrams/ViewClientSequenceDiagram.puml" alt="ViewClientSequenceDiagram" />

<box type="info" seamless>

**Note:** The lifeline for `ViewClientCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

The following activity diagram summarizes what happens when a user executes a `view-client` command:

<puml src="diagrams/ViewClientActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How View Client executes:**

* **Alternative 1 (current choice):** Opens a new `MATER - View Client` Window
  * Pros: Easy to implement, possible to extend the feature to open multiple windows for multiple different clients in the future.
  * Cons: Fussy users may prefer to view client details within one `MATER` Main Window.


* **Alternative 2:** Displays Client details on the `MATER` Main Window.
  * Pros: Will only ever have one window for the whole application.
  * Cons: List of clients will be replaced by the Client details, extra effort to list Clients again.


### Check in/out feature

#### Implementation

The **Check Client** mechanism is facilitated by `CheckClientCommand` extending the `Command` family of classes, as elaborated in the Logic component of MATER. Additionally, it requires the following operations:

* `CheckClientCommandParser#parse()` - Given the argument succeeding the `check-client` command, parses the appropriate index to `CheckClientCommand`.
* `CheckClientCommand#execute()` - Given the list of Clients, identifies the indexed Client and toggles the "checked-in" status of the Client's car. A car is either 'checked-in' or 'checked-out'.

Given below is an example usage scenario and details on how the **Check Client** mechanism behaves at each step.

**Step 1**: The user launches the application, where all clients are listed by default.

**Step 2**: The user executes `check-client 1` command to toggle the "checked-in" status of the car for the first Client in the list.

**Step 3**: If the Client has a car associated, their car's "checked-in" status is toggled, and the success message indicating the action is displayed. If the Client does not have a car associated, an error message indicating there is **"No Car associated to Client to Check In"** is displayed.

**Step 4**: The user may toggle the "checked-in" the status of other clients by providing the relevant index of the client they wish to edit the status of.

The following sequence diagram shows how a `check-client` operation goes through the `Logic` component:

<puml src="diagrams/CheckClientSequenceDiagram.puml" alt="CheckClientSequenceDiagram" />

The following activity diagram summarizes what happens when a user executes a `check-client` command:

<puml src="diagrams/CheckClientActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How Check Client executes:**

* **Alternative 1 (current choice):** Toggles the "checked-in" status of the car for the given Client and displays a success or error message.
    * Pros: Easy to implement and understand. The success message clearly indicates whether a Client’s car has been checked in or out.
    * Cons: Does not allow for more detailed tracking or history of each check-in/out event for a Client's car.

* **Alternative 2:** Records every check-in/out event with a timestamp.
    * Pros: Provides more detailed tracking and a history of all check-in/out events.
    * Cons: More complex to implement and may require additional UI elements to display the history of actions for each Client.


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

Mater, (yes, eponymously named), works as a mechanic at a car workshop. He is not the sole mechanic, and works shifts
with other mechanics. Without the use of technology, storing customer information, as well as information about their
cars and repair work performed, would be stored in pen and paper. This is obviously inconvenient. Mater (and his coworkers)
believes in the advent of technology, and thus dreams of leaving behind tedious paperwork, like how he often
(unknowingly) leaves oil prints on the dossiers of their customers.

Mater can be described as so:

* has a need to manage a significant number of contacts of customers
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* would appreciate being able to share details relevant to cars with other coworkers who are not working concurrently with him

MATER (the CLI in development), shall help Mater and his colleagues by implementing a CLI to keep track of the workshop's customers,
as well as other details about their cars. It will be hosted locally on one machine, with no servers and remote access
to the CLI, for simplicity, to fit budget constraints and for security. (and the scope of the CS2103T course. Haha.)

**Value proposition**: Manage customer/car-related details easily on a shared workshop computer
as compared to the pen and paper alternative.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​           | I want to …​                                                                          | So that I can…​                                                        |
|----------|-------------------|---------------------------------------------------------------------------------------|------------------------------------------------------------------------|
| `* * *`  | new user          | have a quick tutorial of the CLI commands                                             | refer to instructions when I forget how to use the App                 |
| `* * *`  | receptionist      | quickly retrieve the contact details of clients using simple CLI commands             | contact them promptly about updates or issues                          |
| `* * *`  | mechanic          | be able to add new cars to the list                                                   | accommodate new clients                                                |
| `* * *`  | mechanic          | edit the client's details                                                             | make changes if needed                                                 |
| `* * *`  | mechanic          | delete clients data                                                                   | remove unneeded data                                                   |
| `* *`    | frequent CLI user | be able to customize commands and shortcuts                                           | tweak my experience/improve productivity                               |
| `* *`    | mechanic          | generate reports on the most common issues faced by cars of a specific model          |                                                                        |
| `* *`    | user              | have a good user interface                                                            | use the app with greater ease                                          |
| `* *`    | mechanic          | track how long I have taken for each issue                                            | not waste too much time on a single issue                              |
| `* *`    | mechanic          | receive the most urgent issues                                                        | focus on the issues from the most important to the least               |
| `* *`    | mechanic          | be notified when a returning client's car is brought in                               | check their car's history before starting any new work                 |
| `* *`    | admin             | flag vehicles as priority                                                             | inform the mechanic on which vehicles to work on first                 |
| `* *`    | mechanic          | know who did work on what car                                                         | pinpoint the working staff if there were issues with a repair job      |
| `* *`    | mechanic          | categorize issues based on severity                                                   | prioritize the most critical repairs and address urgent problems first |
| `* *`    | mechanic          | add notes/statements belonging to certain cars as they may be important               |                                                                        |
| `*`      | mechanic          | list out all cars which are "unserviced"                                              | to know how much workload I have left                                  |
| `*`      | mechanic          | be able to see when marks like "serviced" or "unserviced" were added                  | cover an absent/on-leave coworker if needed                            |
| `*`      | mechanic          | have access to the full history log of each car that includes all visits and services | quickly check on the logs of cars                                      |
| `*`      | admin             | track vehicles whose service dates are coming soon                                    | recommend and schedule servicing for car owners                        |
*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `Mechanical Assistance Tracker for Efficient Repairs (MATER)` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC1 - Add a Client**

**MSS**

1.  User requests to add a Client.
2.  MATER adds the Client and displays a confirmation message.

    Use case ends.

**Extensions**

* 1a. The given Client's particulars is invalid.
    * 1a1. MATER shows an error message.

      Use case resumes from step 1.

**Use case: UC2 - List Clients**

**MSS**

1.  User requests to list Clients.
2.  MATER shows the list of Clients and displays a confirmation message.

    Use case ends.

**Use case: UC3 - Delete a Client**

**MSS**

1.  User <u>list Clients (UC2)</u>.
2.  User requests to delete a specific Client in the list.
3.  MATER deletes the Client and displays a confirmation message.

    Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.


* 2a. The given index is invalid.
    * 2a1. MATER shows an error message.

      Use case resumes from step 2.

**Use case: UC4 - Edit a Client**

**MSS**

1.  User <u>list Clients (UC2)</u>.
2.  User requests to edit a specific field of a specific Client in the list.
3.  MATER edits the field of the Client and displays a confirmation message.

    Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.


* 2a. The given index is invalid.
    * 2a1. MATER shows an error message.

      Use case resumes from step 2.


* 2b. The given field tag is invalid.
    * 2b1. MATER shows an error message.

      Use case resumes from step 2.


* 2c. The given new value is invalid.
    * 2c1. MATER shows an error message.

      Use case resumes from step 2.

**Use case: UC5 - View a Client**

**MSS**

1.  User <u>list Clients (UC2)</u>.
2.  User requests to view a specific Client in the list.
3.  MATER shows the Client's particulars and Car details and displays a confirmation message.

    Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.


* 2a. The given index is invalid.
    * 2a1. MATER shows an error message.

      Use case resumes from step 2.

**Use case: UC6 - Add Car to Client**

**MSS**

1.  User <u>list Clients (UC2)</u>.
2.  User requests to add a Car to a specific Client in the list.
3.  MATER adds the Car to the Client and displays a confirmation message.

    Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.


* 2a. The given index is invalid.
    * 2a1. MATER shows an error message.

      Use case resumes from step 2.


* 2b. The given Car details is invalid.
    * 2b1. MATER shows an error message.

      Use case resumes from step 2.


* 2c. The Client already has a Car associated to them.
    * 2c1. MATER shows an error message.

      Use case resumes from step 2.

**Use case: UC7 - Delete Car from Client**

**MSS**

1.  User <u>list Clients (UC2)</u>.
2.  User requests to delete the Car from a specific Client in the list.
3.  MATER removes the Car from the Client and displays a confirmation message.

    Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.


* 2a. The Client does not have a Car associated to them.
    * 2a1. MATER shows an error message.

      Use case resumes from step 2.


* 2b. The given index is invalid.
    * 2b1. MATER shows an error message.

      Use case resumes from step 2.

**Use case: UC8 - Check in/out Client**

**MSS**

1.  User <u>list Clients (UC2)</u>.
2.  User requests to check in/out a specific Client in the list.
3.  MATER checks in/out Client and displays a confirmation message.

    Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

**Use case: UC9 - List Clients which are checked in/out**

**MSS**

1.  User requests to list all Clients which are checked in/out
2.  MATER shows the list of Clients which are checked in/out and displays a confirmation message.

    Use case ends.

**Use case: UC10 - Filter Clients by keywords**

**MSS**

1.  User requests to filter Clients by keywords
2.  MATER shows the list of filtered Clients and displays a confirmation message.

    Use case ends.

### Non-Functional Requirements

1.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
2.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
3. Operations like adding, deleting, or editing clients shall complete in less than 1 second for a single transaction.
4. The Command-Line Interface (CLI) and any Graphical User Interface (GUI) components shall be intuitive and easy to navigate.
5. The codebase shall follow established coding standards for readability and ease of maintenance.
6. The application shall be compatible with major operating systems, including Windows, macOS, and Linux.
7. The system shall handle errors gracefully without crashing.
8. All exceptions shall be caught and managed appropriately to maintain system stability.


### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **GUI (Graphical User Interface)**: A visual-based interface involving icons, buttons, and other graphical elements, as opposed to text commands. Although not prioritized, some users may prefer a GUI for ease of use.
* **CLI (Command Line Interface)**: A text-based interface where users interact with the system by typing in commands. This is the primary interface for the App.
* **Client**: A customer whose personal details and associated car information are stored in the system. This refers to anyone bringing a vehicle in for servicing.
* **Car**: A vehicle brought in by a client for servicing. Each car is associated with a client.
* **Issue**: A problem or task that needs to be addressed for a specific car with its status and details. Each client can have multiple issues associated with them.


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
