---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# InvenTrack Developer Guide

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

This application is aimed towards convenience store managers who often need to manage their inventories. This application will help convenience store managers who:

* Manage a significant number of information about their suppliers and products
* Monitor the performance of their suppliers
* Maintain a streamline communication, ensuring smooth operations and minimizing errors in stock replenishment
* Regularly order products and prefer a user-friendly interface
* prefer a fast, efficient typing interface over mouse or voice commands

**Value proposition**: 

InvenTrack enables inventory managers to efficiently manage large number of supplier contact information and streamline communication, ensuring smooth operations and minimizing errors in stock replenishment, all through a typing-optimized interface
The three main aspects of this product are:

* **Streamlined Supplier Management**: The product simplifies managing relationships with multiple suppliers, providing tools to track orders, and monitor supplier performance.
* **Efficient Inventory Control**: It offers features for real-time inventory tracking and stock alerts based on predefined thresholds, helping prevent stockouts and overstocking.
* **Performance Insights**: The product provides performance metrics to evaluate supplier reliability and order accuracy, facilitating better decision-making.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                 | So that I can…​                                                        |
|----------|--------------------------------------------|------------------------------|------------------------------------------------------------------------|
| `* * *`  | new user                                   | see usage instructions       | refer to instructions when I forget how to use the App                 |
| `* * *`  | inventory manager                          | add a new supplier           |                                                                        |
| `* * *`  | inventory manager                          | add a new product           |                                                                        |
| `* * *`  | inventory manager                          | assign products to suppliers  | collectively manage products which corresponding suppliers supply                |
| `* * `  | inventory manager                          | remove suppliers/products              | prune obsolete data   |
| `* * `  | inventory manager                          | track order status for a supplier              | avoid delays and ensure timely stock replenishment   |
| `* * `  | inventory manager                          | set pre-defined max stock level for products              | determine quantity to request for an order   |
| `* * `  | experienced inventory manager                          | set pre-defined minimum stock level for a product              | ensure availability of the product at all times   |
| `* * `  | experienced inventory manager                          | request an order from a supplier | replenish stocks of the corresponding products |
| `* *`      | inventory manager             | set up automatic reordering based on stock levels and pre-defined thresholds | prevent stockouts and overstocking efficiently |
| `* * `  | novice inventory manager                          | easily search suppliers/products      | browse large datasets quickly                            |
| `* *`      | inventory manager             | receive automated re-order alerts | reduce the risk of stockouts or overstocking |
| `* *`      | inventory manager             | view payment/order history for a supplier | so that I have a clear understanding of past cash flow and plan future expenses |
| `* *`      | inventory manager             | get alerts in case of any supplier issues        | find a convenient replacement in time  |
| `* *`      | inventory manager             | quickly send standardized communication to suppliers via email or messaging without leaving the interface        | streamline communication and reduce errors  |
| `* *`      | inventory manager             | I can store contracts and terms of agreements with suppliers in the system        | quickly refer to them when negotiating orders  |
| `* *`      | inventory manager             | be able to get printable txt of relevant information  |  get hard-copy of the information  |
| `*`      | experienced inventory manager             | sort suppliers by some criterion        | organize data based on my needs |
| `*`      | experienced inventory manager             | customize my dashboard to show only the relevant information        | work more efficiently based on my specific needs |
| `*`      | inventory manager             | weekly summaries of inventory levels, orders and supplier communications | stay on top of opertions without manually checking the system |
| `*`      | experienced inventory manager             | assign a product to multiple suppliers | have alternative options if one supplier is unable to fulfil the order |
| `*`      | experienced inventory manager             | create macros/shortcuts for frequently used commands | save time |
| `*`      | experienced inventory manager             | be able to see most used commands | make common processes faster |
| `* `      | inventory manager             | update supplier information        | organize data based on my needs      |
| `*`      | inventory manager             | be able to generate insights on supplier performance based on past interactions (e.g., delivery timeliness, order accuracy)  | make informed decisions about ongoing supplier relationships.  |


### Use cases

(For all use cases below, the **System** is the `Inventory Records` and the **Actor** is the `Inventory Manager`, unless specified otherwise)

**Use case: Assign Products to Suppliers**

**Preconditions:**
* The Inventory Manager is logged into the InvenTrack system.

**MSS**
1. Inventory Manager tries to assign a product to a supplier.
2. System validates that the product name & supplier name exists in the list. 
3. The system assigns the specified product to the specified supplier. 
4. System updates the product-supplier relationship in the list.
5. System confirms the assignment with a success message.
   
    Use case ends.

**Extensions**

* 1a. Product/Supplier name is in invalid format:
  * 1a1. System displays an error message.
  * 1a2. System prompts the Inventory Manager to re-enter the command.
  * 1a3. Use case resumes at step 1.

* 2a. Product/Supplier name does not exist:
  * 2a1. System displays an error message.
  * 2a2. System prompts the Inventory Manager to re-enter the command. 
  * 2a3. Use case resumes at step 1.

**Use case: Delete Suppliers/Products**

Preconditions:
* The Inventory Manager is logged into the InvenTrack system.

**MSS**

1. Inventory Manager tries to delete a particular product or supplier.
2. System checks if product or supplier exists which is to be deleted.
3. System removes the specified supplier or product from the list.
4. System confirms the deletion with a success message.
   
    Use case ends.

**Extensions**

* 1a. Product/Supplier name is in invalid format:
    * 1a1. System displays an error message.
    * 1a2. System prompts the Inventory Manager to re-enter the command.
    * 1a3. Use case resumes at step 1.

* 2a. Product/Supplier name does not exist:
    * 2a1. System displays an error message.
    * 2a2. System prompts the Inventory Manager to re-enter the command.
    * 2a3. Use case resumes at step 1.

**Use case: Set Minimum Thresholds for Products Stock**

Preconditions:
* The Inventory Manager is logged into the InvenTrack system.

**MSS**
1. Inventory Manager tries to set a threshold for a product.
2. System validates that the product name exists in the list.
3. System sets the specified threshold for the product. 
4. System updates the product information in the list.
5. System confirms the threshold setting with a success message.
   
    Use case ends.

**Extensions**
* 1a. Threshold amount is in invalid format(not numeric):
    * 1a1. System displays an error message.
    * 1a2. System prompts the Inventory Manager to re-enter the command.
    * 1a3. Use case resumes at step 1.

* 2a. Product name does not exist:
    * 2a1. System displays an error message.
    * 2a2. System prompts the Inventory Manager to re-enter the command.
    * 2a3. Use case resumes at step 1.

**Use case: Access help guide**
* User - Any user with access to the system

**Preconditions:**
* The user is logged into the InvenTrack system.
* The product for which the threshold is being set exists in the system.

**MSS**
1. User requests for help guide.
2. System opens a new window or panel displaying the user guide. 
3. User reviews the guide. 
4. User closes the guide when finished.
   
    Use case ends.

**Extensions**
* 1a. System unable to open user guide:
    * 1a1. System displays an error message.
    * 1a2. System prompts the Inventory Manager to re-enter the command.
    * 1a3. Use case resumes at step 1.
  
  Use case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to handle up to 50 suppliers and 3000 products without a noticeable sluggishness in performance for typical usage.
3.  The system should respond to user commands within 500ms, even with large data.
4.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
5.  All private supplier and store data must be encrypted to protect sensitive information.
6.  The system should automatically back up critical data and support easy recovery in case of abrupt power or network loss.
7.  Error messages should be clear, helping users understand and rectify their mistakes.
8.  The system’s architecture must allow developers to easily update components without affecting the overall functionality.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Architecture Diagram**: A visual representation showing the structure of the system and how its components interact.
* **Main Component**: Refers to the core functional modules of the app, including UI, Logic, Model, and Storage.
* **UI (User Interface)**: The visual part of the system that allows users to interact with the application (e.g., command box, result display).
* **CommandBox**: A UI element where users input commands to interact with the application.
* **Logic Component**: This handles command execution and interacts with the model to perform operations like adding, deleting, or listing suppliers and inventory items.
* **Model Component**: Holds and manages in-memory data, such as supplier and inventory information.
* **Storage Component**: Responsible for reading from and writing to the disk, including saving user preferences and inventory data in JSON format.
* **ObservableList**: A list that the UI can listen to and update itself automatically when the data changes.
* **API (Application Programming Interface)**: A set of methods that different components use to interact with each other.
* **Parser**: Converts user commands (entered as text) into objects the system can understand and execute.
* **Undo/Redo**: A feature allowing users to reverse or reapply previous actions, supported by a history-tracking mechanism in the system.
* **Tag**: A label associated with items in the inventory or suppliers to categorize them, stored centrally to avoid redundancy.
* **VersionedAddressBook**: A data structure used to support the undo/redo functionality, storing multiple states of the address book.
* **JavaFX**: A framework used to build the graphical user interface, managing visual elements like windows and buttons.
* **.fxml Files**: XML-based files used to define the layout of the UI components in JavaFX.
* **CommandResult**: Encapsulates the result of a command execution, such as success or error messages.
* **Sequence Diagram**: A type of diagram that shows how different components of the system interact over time during a specific process (e.g., command execution).
* **Supplier**: A contact responsible for supplying products for the store.
* **Stock Level**: Current inventory, or the number of items of that product SKU, in the store.

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

