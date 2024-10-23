---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# AB-3 Developer Guide

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

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

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
| * * * | new user | add customer contacts | I can store their information for easy access later. |
| * * * | user | delete customer contacts | I can get rid of contacts that are no longer customers |
| * * * | user  | view all customer contacts | I can have an overview of what contacts I have |
| * | first time user | view a demo shipment and contact data | I can understand how the app will look and function when fully populated. |
| * | new user | be guided through setting up my company's logistics data | I can start using the app immediately. |
| * | user ready to start | remove any demo/sample data | I can work with only my own company’s data. |
| * * * | logistics coordinator | input delivery schedules for each contact | I can plan and track upcoming shipments. |
| * * * | logistics coordinator | add information about each shipment’s ETA | I can easily inform clients of expected arrival times. |
| * * | logistics coordinator | assign products to a shipment | I can track what goods are being delivered. |
| * * | logistics coordinator | calculate and view the estimated cost of a shipment | I can provide accurate quotes to clients. |
| * * | regular user | filter my delivery list by date | I can focus on immediate shipments. |
| * | regular user | see a visual timeline of my delivery schedules | I can easily plan ahead. |
| * * | logistics coordinator | update the shipment status (e.g., in transit, delivered) for each delivery | I can track its progress. |
| * * | logistics coordinator | add notes to each contact | I can record special instructions or preferences for future deliveries. |
| * * | user | search for a specific client or contact using a search bar | I can find them quickly. |
| * | logistics manager | categorize customers by priority or frequency | I can manage key accounts effectively. |
| * | logistics coordinator | add multiple points of contact for a client | I can communicate with various stakeholders within the same company. |
| * * | logistics coordinator | deactivate contacts that are no longer active | my list remains organized without deleting old records. |
| * * | long-time user | archive past shipments that have been delivered | I can focus on current and future deliveries. |
| * | regular user | generate reports showing all shipments for a particular client | I can easily present the information when needed. |
| * | logistics coordinator | export customer and shipment data into a spreadsheet | I can share information with clients. |
| * * | logistic coordinator | schedule repeated deliveries | I can ensure consistency in service. |
| * | logistics manager | assign priority levels to deliveries | I can ensure critical deliveries are handled first. |
| * * | logistics manager | record any delays or issues with deliveries | I can review them later and work on improving delivery times. |
| * * | logistics manager | view the delivery history of individual worker | I can evaluate their performance and workload over time. |
| * * | logistics manager | edit delivery details | I can make adjustments when plans change or errors are identified. |
| * | logistics manager | print a hard copy of the daily or weekly delivery schedule | I have a physical reference for planning or sharing with others. |
| * | logistics manager | import and export delivery data in a standard file format | I can back up data or share it with other systems or stakeholders. |

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

* **Mainstream OS**: Windows, Linux, Unix, MacOS
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
