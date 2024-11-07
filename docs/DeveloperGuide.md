---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# LogiLink Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org/addressbook-level3/)._

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

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-T12-3/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-T12-3/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
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

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-T12-3/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` and `InspectWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow` and `InspectWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `MainWindow` and `InspectWindow` are toggleable, meaning that if `MainWindow` is currently being displayed, then the `InspectWindow` will be hidden, and vice versa.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder.

For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-T12-3/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-T12-3/tp/blob/master/src/main/resources/view/MainWindow.fxml), while the layout of the [`InspectWindow`](https://github.com/AY2425S1-CS2103T-T12-3/tp/blob/master/src/main/java/seedu/address/ui/InspectWindow.java) is specified in [`InspectWindow.fxml`](https://github.com/AY2425S1-CS2103T-T12-3/tp/blob/master/src/main/resources/view/InspectWindow.fxml).

The `CSS` files that style the `UI` are also stored in the same folder.

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-T12-3/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

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
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-T12-3/tp/blob/master/src/main/java/seedu/address/model/Model.java)

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


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-T12-3/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

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

### Switching window views

#### Implementation
The `UI` consists of toggleable window views, `MainWindow` and `InspectWindow`. The `MainWindow` is used for displaying the information of each contact (i.e. `Person`), while the `InspectWindow` is used for displaying the `DeliveryList` of
each inspected contact. User inputs parsed by certain `XYZCommandParser` classes (e.g., `AddCommandParser`, `SortCommandParser`, ...) differ based on the window view the `UI` is currently in.

For example, while in `MainWindow`, `AddCommandParser` parses the following user inputs: `n/NAME p/PHONE e/EMAIL r/ROLE a/ADDRESS [t/TAG]...`. <br>However, while in `InspectWindow`, `AddCommandParser` parses the following user inputs:
`i/ITEM... e/ETA a/ADDRESS c/COST s/STATUS [t/TAG]...`.

Given below is an example usage scenario of switching from the `MainWindow` to `InspectWindow`:
1. The user inputs `inspect [INDEX]` into the command box, where the index refers to the index number of a contact shown in the displayed contacts list.
2. `AddressBookParser` then creates an `InspectCommandParser`, which parses the user input to check for a valid index. If the parsing is successful, `InspectCommandParser` returns an `InspectCommand`.
3. The `LogicManager` then executes this `InspectCommand` to return a `CommandResult`. This `CommandResult` stores the boolean `isInspect = true` to indicate that it was created by executing an `InspectCommand`, as well as the contact
to inspect.
4. The `executeCommand()` method within `MainWindow.java` checks if `isInspect = true`, and changes the window view from `MainWindow` to `InspectWindow` using the `handleInspect()` method if so.
5. Additionally, within the `handleInspect()` method, a separate `isInspect` boolean within `AddressBookParser` is set to `true`. Certain `XYZCommandParser` classes use this boolean to decide what user inputs are valid during parsing. This
allows the same commands to have different functionality depending on the current window view.
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

* Logistic coordinator for logistic companies
* has a need to manage a significant number of contacts
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Enables the company to have an organized way to track all their shipments, plan their deliveries, calculate shipping, etc., as well as an easy and fast way to access information of all their customers and clients

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​ | I want to …​ | So that I can…​ |
|---|---------|--------------|----------------|
| *** | new user | add customer contacts | store their information for easy access later. |
| *** | user | delete customer contacts | get rid of contacts that are no longer customers. |
| *** | user | view all customer contacts | have an overview of what contacts are available. |
| *** | user | input delivery schedules for each contact | plan and track upcoming shipments. |
| *** | user | add information about each shipment’s ETA | easily inform clients of expected arrival times. |
| ** | user | assign products to a shipment | track what goods are being delivered. |
| ** | user | calculate and view the estimated cost of a shipment | provide accurate quotes to clients. |
| ** | regular user | filter my delivery list by date | focus on immediate shipments. |
| ** | user | update the shipment status (e.g., in transit, delivered) for each delivery | track its progress. |
| ** | user | add notes to each contact | record special instructions or preferences for future deliveries. |
| ** | user | search for a specific client or contact using a search bar | find them quickly. |
| ** | user | deactivate contacts that are no longer active | keep the list organized without deleting old records. |
| ** | long-time user | archive past shipments that have been delivered | focus on current and future deliveries. |
| ** | user | schedule repeated deliveries | ensure consistency in service. |
| ** | user | record any delays or issues with deliveries | review them later and work on improving delivery times. |
| ** | user | view the delivery history of individual worker | evaluate their performance and workload over time. |
| ** | user | edit delivery details | make adjustments when plans change or errors are identified. |
| * | first-time user | view a demo shipment and contact data | understand how the app will look and function when fully populated. |
| * | new user | be guided through setting up my company's logistics data | start using the app immediately. |
| * | user ready to start | remove any demo/sample data | work with only my own company’s data. |
| * | regular user | see a visual timeline of my delivery schedules | easily plan ahead. |
| * | user | categorize customers by priority or frequency | manage key accounts effectively. |
| * | user | add multiple points of contact for a client | communicate with various stakeholders within the same company. |
| * | regular user | generate reports showing all shipments for a particular client | easily present the information when needed. |
| * | user | export customer and shipment data into a spreadsheet | share information with clients. |
| * | user | assign priority levels to deliveries | ensure critical deliveries are handled first. |
| * | user | print a hard copy of the daily or weekly delivery schedule | have a physical reference for planning or sharing with others. |
| * | user | import and export delivery data in a standard file format | back up data or share it with other systems or stakeholders. |

### Use cases

(For all use cases below, the **System** is the `LogiLink` and the **Actor** is the `user`, unless specified otherwise)

**Use Case 1: Add Contact**

**Preconditions:** User in default page.

**Postconditions:** The contact is successfully added to the LogiLink with the provided details.

**MSS**

1.  User requests to add a new contact by providing the necessary details (name, phone number, email, address, and tags).
2.  LogiLink validates the input details (e.g., ensuring name is not empty, phone number and email are in valid formats, etc.).
3.  User confirms the addition.
4.  LogiLink adds the contact and displays a success message.

    Use case ends.

**Extensions**

* 2a. Invalid input format provided.
    * 2a1. LogiLink shows an error message indicating which field has invalid input.
    * 2a2. Use case resumes at step 1.

      Use case resumes at step 1.

* 2b. Duplicate contact detected.
    * 2b1. LogiLink warns about duplicate name, phone number, or email.
    * 2b2. User can modify the input or cancel the action.

      Use case resumes at step 1.

**Use Case 2: List All Contacts**

**Preconditions:** User in default page.

**Postconditions:** All contacts are displayed.

**MSS**

1.  User requests to list all contacts.
2.  LogiLink retrieves and displays all contacts on the GUI.

    Use case ends.

**Extensions**

* 2a. No contacts available.
    * 2a1. LogiLink shows a message stating that no contacts are available.

      Use case ends.

**Use Case 3: Delete Contact**

**Preconditions:** User in default page.

**Postconditions:** The specified contact is deleted.

**MSS**

1.  User requests to list contacts.
2.  LogiLink displays the list of contacts.
3.  User requests to delete one or more contacts by ID.
4.  LogiLink deletes the selected contacts and confirms deletion.

    Use case ends.

**Extensions**

* 2a. No contacts available.
    * 2a1. LogiLink informs the user that there are no contacts to delete.

      Use case ends.

* 3a. Invalid or missing ID.
    * 3a1. LogiLink shows an error message indicating the ID is invalid or missing.

      Use case resumes at step 2.

**Use Case 4: Inspect Contact**

**Preconditions:** User in default page.

**Postconditions:** The LogiLink displays the contact details, and the user enters inspection mode.

**MSS**

1.  User requests to inspect a contact by providing the contact index.
2.  LogiLink retrieves the details of the specified contact and enters inspection mode.

    Use case ends.

**Extensions**

* 2a. Invalid contact index.
    * 2a1. LogiLink shows an error message indicating an invalid index.

      Use case resumes at step 1.

**Use Case 5: Add Delivery for Contact**

**Preconditions:** User is in inspection mode for a contact and wants to add a delivery.

**Postconditions:** A new delivery is successfully added to the contact.

**MSS**

1.  User requests to add a delivery with specific details (time, date, ETA, address, cost).
2.  LogiLink validates the delivery details.
3.  User confirms the addition.
4.  LogiLink adds the delivery to the contact and displays a success message.

    Use case ends.

**Extensions**

* 2a. Invalid input format.
    * 2a1. LogiLink shows an error message indicating which delivery field is invalid.

      Use case resumes at step 1.

**Use Case 6: Delete Delivery for Contact**

**Preconditions:** User is in inspection mode and wants to delete a delivery for a contact.

**Postconditions:** The specified delivery is deleted from the contact.

**MSS**

1.  User requests to delete a delivery by providing the delivery number.
2.  LogiLink deletes the selected delivery and confirms deletion.

    Use case ends.

**Extensions**

* 2a. Invalid or missing delivery number.
    * 2a1. LogiLink shows an error message indicating an invalid or missing delivery number.

      Use case resumes at step 1.

**Use Case 7: View Deliveries for Contact**

**Preconditions:** User is in inspection mode and wants to view the deliveries for a contact.

**Postconditions:** The specified deliveries are displayed.

**MSS**

1.  User requests to view deliveries by providing the delivery number(s).
2.  LogiLink retrieves and displays the specified deliveries.

    Use case ends.

**Extensions**

* 2a. Invalid delivery number.
    * 2a1. LogiLink shows an error message indicating an invalid delivery number.

      Use case resumes at step 1.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The system should support horizontal and vertical scaling to handle increasing users and data as the business grows, ensuring seamless performance.
5.  Should have 99.9% uptime with robust disaster recovery mechanisms and failover systems to prevent data loss and maintain system availability.
6.  Should provide an intuitive, user-friendly interface that supports accessibility.

### Glossary

* **Mainstream OS**: Windows, Linux, MacOS
* **Inspection Mode**: A feature that enables the user to enter a specific contact and view detailed information. In this mode, the user can also manage deliveries, such as adding or deleting them, for the selected contact.
* **Default Page**: The main page that the user is presented with upon launching the application. It serves as the starting point for all primary actions, including adding, viewing, and deleting contacts.
* **ETA (Estimated Time of Arrival)**: The estimated time when a delivery is expected to reach its destination. This is one of the key delivery details used in planning and tracking shipments.
* **Tag**: A label that can be assigned to a contact for organizational purposes. Tags help categorize and filter contacts, making it easier to manage them.
* **Contact**: An individual or business entity stored in the LogiLink system. A contact holds personal or business information, such as name, phone number, email, address, and associated deliveries.
* **Delivery**: A shipment or consignment associated with a specific contact. Each delivery includes details such as the time, date, ETA, delivery address, and cost.
* **Shipment**: A package or group of packages sent from one location to another. In the context of the LogiLink app, shipments are tracked within deliveries linked to contacts.
* **Logistics Coordinator**: A user of the LogiLink system responsible for managing shipments, deliveries, and customer information. This role typically oversees the planning and execution of transportation processes.

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

   1. Double-click the jar file. Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. Close the program
   1. Type `exit` in the command box

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Saving data

1. Simulate corrupted data files

   1. Assuming data present in the program. Find the `addressbook.json` file in the `data` folder
   2. Delete any line. An empty address book will be loaded.
   3. IF any command is used the `addressbook.json` would be wiped clean. Save the data in another file if necessary.
   4. Delete the corrupted `addressbook.json` file. The program will create a new file.
