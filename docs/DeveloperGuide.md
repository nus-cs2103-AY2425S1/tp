---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# SellSavvy Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

- **Project Origin**: This project builds on the AddressBook-Level3 project, originally created by the [SE-EDU initiative](https://se-education.org).

- **Libraries Utilized**:
    - [JavaFX](https://openjfx.io/): Used for building a responsive graphical user interface.
    - [Jackson](https://github.com/FasterXML/jackson): Used for JSON data processing.
    - [JUnit5](https://github.com/junit-team/junit5): Used for testing to ensure code reliability.

<!-- Spacer for extra break -->

- **AI Assistance**: The *SellSavvy* logo was generated with ChatGPT 4.0.

- **References to Other Team Projects (TPs):**

  - **User Guide**: We referred to the [AY2425S1-CS2103T-F14a-1 User Guide](https://github.com/AY2425S1-CS2103T-F14a-1/tp/blob/master/docs/UserGuide.md) and adapted their Markbind layouts for constraints and tips boxes.
  - **Developer Guide**: For the *Saving Data* section under the appendix, we referred to the [AY2425S1-CS2103T-F14a-1 Developer Guide](https://ay2425s1-cs2103t-f14a-1.github.io/tp/DeveloperGuide.html#saving-data) on how to handle missing or corrupted data files.


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

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-F14a-2/tp/blob/master/src/main/java/seedu/sellsavvy/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-F14a-2/tp/blob/master/src/main/java/seedu/sellsavvy/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

<div style="page-break-after: always;"></div>

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `deletecustomer 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.
<div style="page-break-after: always;"></div>

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-F14a-2/tp/blob/master/src/main/java/seedu/sellsavvy/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `CustomerListPanel`, `OrderListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-F14a-2/tp/blob/master/src/main/java/seedu/sellsavvy/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-F14a-2/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Customer` and `Order` objects residing in the `Model`.
<div style="page-break-after: always;"></div>

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-F14a-2/tp/blob/master/src/main/java/seedu/sellsavvy/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("deletecustomer 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `deletecustomer 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCustomerCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

<div style="page-break-after: always;"></div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g. `DeleteCustomerCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g. `DeleteCustomerCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a customer).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g. `AddCustomerCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g. `AddCustomerCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g. `AddOrderCommandParser`, `DeleteCustomerCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.
<div style="page-break-after: always;"></div>

### Model component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-F14a-2/tp/blob/master/src/main/java/seedu/sellsavvy/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Customer` objects (which are contained in a `UniqueCustomerList` object).
* stores the currently 'selected' `Customer` objects (e.g. results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Customer>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a particular `Customer` whose orders will be displayed in a `ReadOnlyObjectWrapper<Customer>` which is exposed to outsiders as an unmodifiable `ReadOnlyObjectProperty<Customer>` that can be 'observed' e.g. the UI can be bound to this object property so that the UI automatically updates when selected customer change.
  * Each `Customer` stores the currently 'selected' `Order` objects (e.g. results of a filter query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Order>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)
<div style="page-break-after: always;"></div>

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Customer` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Customer` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-F14a-2/tp/blob/master/src/main/java/seedu/sellsavvy/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.sellsavvy.commons` package.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

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

Step 2. The user executes `deletecustomer 5` command to delete the 5th customer in the address book. The `deletecustomer` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `deletecustomer 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `addcustomer n/David …​` to add a new customer. The `addcustomer` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the customer was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

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

Step 5. The user then decides to execute the command `listcustomer`. Commands that do not modify the address book, such as `listcustomer`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `addcustomer n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.
  <p><p/>

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `deletecustomer`, just save the customer being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

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

* independent sellers/drop-shipping business owners selling on platforms like Carousell
* lack a central platform for drop-shipping and delivery order management
* has a need to manage a significant number of contacts
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: For small independent sellers, organizing customer lists can be challenging. SellSavvy offers a centralized platform to store orders and track deliveries, streamlining drop-shipping management. SellSavvy is optimized for tech-savvy fast-typing users through command-line interface and efficient functionalities.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                  | I want to …​                                              | So that I can…​                                            |
|----------|--------------------------|-----------------------------------------------------------|------------------------------------------------------------|
| `* * *`  | user                     | add new customers with details such as name and address   | remember details of customers for order deliveries         |
| `* * *`  | user                     | add orders made by a customer                             | keep track of orders made by each customer                 |
| `* * *`  | user                     | add details to orders, such as delivery date and quantity | remember details of orders when making deliveries          |
| `* * *`  | user                     | mark orders as completed                                  | keep track of orders that have been delivered              |
| `* * *`  | user with many customers | delete a customer from my address book                    | remove clients who I no longer need to be in contact with  |
| `* * *`  | user with many customers | view all my customer contacts                             | see an overview of all my customers' details               |
| `* * *`  | user with many orders    | view all orders under a specific customer                 | see an overview of all orders made by a customer           |
| `* * *`  | user with many orders    | delete an order under a customer                          | remove orders that I no longer need to track               |
| `* * *`  | user with many orders    | revert an order's completed status                        | keep track of erroneous or failed order deliveries         |
| `* *`    | tech-savvy user          | save data to local storage                                | keep my data even after exiting SellSavvy                  |
| `* *`    | tech-savvy user          | load data from local storage                              | access my local data using SellSavvy                       |
| `* *`    | experienced user         | edit a customer's details                                 | keep the customers' information up-to-date                 |
| `* *`    | experienced user         | edit an order's details                                   | keep the orders' information up-to-date                    |
| `*`      | experienced user         | find a customer by name                                   | search for a specific customer's details                   |
| `*`      | experienced user         | filter orders by their status                             | see which orders are completed or have yet to be delivered |
| `*`      | inexperienced user       | be informed a customer already made an identical order    | take note of duplicate orders made by the same customer    |
| `*`      | inexperienced user       | be informed if a new order's delivery date has passed     | take note of erroneous creation of historical orders       |


For **all** use cases, the system is **SellSavvy** and the actor is the **user**.

### Use cases

**Use case 1: View List of Customers**

* **Use Case**: UC01 - View List of Customers

**MSS**

1.  User chooses to view the list of customers.
2. SellSavvy displays all customers along with their details.

Use case ends.

**Use case 2: Add a Customer**

* **Use Case**: UC02 - Add Customer
* **Guarantees**:
    * Customer will be added to customer list if input parameters are valid.

**MSS**

1.  User chooses to add a new customer and specifies the customer details.
2.  SellSavvy adds the customer into the list.
3.  SellSavvy confirms the addition is successful by displaying the newly added customer's details.

Use case ends.

**Extensions**

* 1a. SellSavvy detects that there are required parameters missing.
  * 1a1. SellSavvy displays an error about the format and states the command format.

  Use case ends.


* 1b. SellSavvy detects that there is a parameter not satisfying its constraints.
  * 1b1. SellSavvy states the constraints of the invalid parameter.

  Use case ends


* 1c. SellSavvy detects that a customer with identical name already exists.
  * 1c1. SellSavvy displays an error that the customer already exists.

  Use case ends.


* 2a. SellSavvy detects that there is an existing customer with a similar name.
  * 2a1. SellSavvy gives a warning that a customer with a similar name already exists.

  Use case resumes from step 3.


* 2b. SellSavvy detects that the new customer has tags with similar names.
  * 2b1. SellSavvy gives a warning that there are similar tags on the new customer.

  Use case resumes from step 3.


**Use case 3: Delete Customer and All Orders Related to The Customer**

* **Use Case**: UC03 - Delete Customer and All Orders related to the customer
* **Preconditions**: There are customers displayed in the customer list.
* **Guarantees**:
    * Customer and all their orders will be deleted if input parameters are valid.

**MSS**

1. User finds the customer index from the list.
2. User deletes the customer by their index.
3. SellSavvy updates the displayed list of customers.
4. SellSavvy confirms that the deletion is successful by displaying the deleted order's details.

Use case ends.

**Extensions**

* 2a. SellSavvy detects customer index is missing or non-positive.
    * 2a1. SellSavvy displays an error about the format and states the command format.

  Use case ends.


* 2b. SellSavvy detects that there are no customers with the specified index.
  * 2b1. SellSavvy displays an error that the customer index is invalid.

  Use case ends.


* 3a. The deleted customer's order list is being displayed.
  * SellSavvy configures the order panel to not display anyone's orders.

  Use case resumes from step 4.

**Use case 4: Find the Customer by their Name**

* **Use Case**: UC04 - Find the Customer by their Name
* **Guarantees**:
    * All customers displayed will have at least one of the specified keywords in their name.

**MSS**

1. User specifies keyword(s) of the name of the customer(s) they want to find.
2. SellSavvy displays all customers whose names have at least one of the keywords.

Use case ends.

**Extensions**

* 1a. SellSavvy cannot find any customers with at least one matching keyword.
  * 1a1. SellSavvy tells the user that no related customers are found.

  Use case ends.

**Use case 5: Edit a Customer's Details**

* **Use Case**: UC05 - Edit a Customer's Details
* **Preconditions**: There are customers displayed in the customer list.
* **Guarantees**:
  * The specified customer's details will be overwritten if the input parameters are valid.
<div style="page-break-after: always;"></div>

**MSS**

1. User finds the index of the customer they want to edit.
2. User specifies the customer index along with modifications they want to make to the customer's details.
3. SellSavvy updates the customer list with the modifications made to the customer and lists all customers.
4. SellSavvy confirms the modification by stating the updated customer's details.

Use case ends.

**Extensions**

* 2a. The customer index is missing or non-positive.
  * 2a1. SellSavvy displays an error about the format and states the command format.

  Use case ends.


* 2b. SellSavvy detects that there are no customers with the specified index.
    * 2b1. SellSavvy displays an error that the customer index is invalid.

  Use case ends.


* 2c. There are no customer details specified for modification.
  * 2c1. SellSavvy tells the user that at least one field has to be edited.

  Use case ends.


* 2d. SellSavvy detects that the updated fields do not satisfy its constraints.
    * 2d1. SellSavvy states the constraints of the invalid parameter.

  Use case ends


* 2e. SellSavvy detects that a customer with identical name already exists.
    * 2e1. SellSavvy displays an error that the customer already exists.

  Use case ends.


* 3a. SellSavvy detects that the new name of the customer is similar to that of an existing customer.
    * 3a1. SellSavvy gives a warning that a customer with a similar name already exists.

  Use case resumes from step 4.


* 3b. SellSavvy detects that there are tags with similar names among the updated tags.
    * 3b1. SellSavvy gives a warning that there are similar tags on the customer.

  Use case resumes from step 4.

**Use case 6: Add an Order under a Customer**

* **Use Case**: UC06 - Add an Order under a Customer
* **Preconditions**: A customer's list of orders is being displayed.
* **Guarantees**:
    * A new pending order will be added under the specified customer, if input parameters are valid.

**MSS**

1. User finds the customer index from the list.
2. User adds the order using the customer index and details of the order.
3. SellSavvy adds the order under the customer.
4. SellSavvy confirms the addition is successful by displaying the newly added order's details and customer's list of orders.

Use case ends.
<div style="page-break-after: always;"></div>

**Extensions**

* 2a. SellSavvy detects that the required parameters are missing or the customer index is non-positive.
    * 2a1. SellSavvy displays an error about the format and states the command format.

  Use case ends.


* 2b. SellSavvy detects that there is a parameter not satisfying its constraints.
    * 2b1. SellSavvy states the constraints of the invalid parameter.

  Use case ends.


* 2c. SellSavvy detects that there are no customers with the specified index.
    * 2c1. SellSavvy displays an error that the customer index is invalid.

  Use case ends.


* 3a. SellSavvy detects that there is an existing pending order under the customer with similar details.
    * 3a1. SellSavvy gives a warning that an order with similar details already exists.

  Use case resumes from step 4.

**Use case 7: List a Customer's Orders**

* **Use Case**: UC07 - List a Customer's Orders
* **Preconditions**: There are customers displayed in the customer list.
* **Guarantees**:
    * Orders made by specific customer will be displayed as a list, if input parameters are valid.

**MSS**

1. User finds the index of the customer in the customer list whose orders they want to view.
2. User inputs command to list all orders under the index of customer.
3. SellSavvy displays the orders under the specified customer.

Use case ends.

**Extensions**

* 2a. SellSavvy detects that the customer index is missing or non-positive.
    * 2a1. SellSavvy displays an error about the format and states the command format.

  Use case ends.


* 2b. SellSavvy detects that there are no customers with the specified index.
    * 2b1. SellSavvy displays an error that the customer index is invalid.

    Use case ends.


* 2c. There are no orders under the specified customer.
  * SellSavvy displays that the customer does not have any orders currently.

  Use case ends.
<div style="page-break-after: always;"></div>

**Use case 8: Mark Order as Completed**

* **Use Case**: UC08 - Mark Order as Completed
* **Preconditions**: A customer's list of orders is being displayed.
* **Guarantees**:
    * Specified order will be marked as “Completed” if the input parameters are valid.

**MSS**

1. User finds the order they want to mark as completed.
2. User specifies the index of the order.
3. SellSavvy updates the status of the order and indicates that the action is successful.

Use case ends.

**Extensions**

* 2a. SellSavvy detects that the order index is missing or non-positive.
    * 2a1. SellSavvy displays an error about the format and states the command format.

  Use case ends.


* 2b.  The specified order is already marked as "Completed".
    * 2b1. SellSavvy displays a message stating that the order is already marked as completed.

    Use case ends.


* 2c. There are no orders with the specified index.
  * 2c1. SellSavvy displays an error that the order index is invalid.

  Use case ends.

**Use case 9: Remove "Completed" Marking from Order**

* **Use Case**: UC09 - Remove "Completed" Marking from Order
* **Preconditions**: A customer's list of orders is being displayed.
* **Guarantees**:
    * Specified order will be reverted to "Pending" status if the input parameters are valid.

**MSS**

1. User finds the order they want to mark as completed.
2. User specifies the index of the order.
3. SellSavvy updates the status of the order and indicates that the action is successful.

Use case ends.

**Extensions**

* 2a. SellSavvy detects that the order index is missing or non-positive.
    * 2a1. SellSavvy displays an error about the format and states the command format.

  Use case ends.


* 2b.  The specified order is not marked as “Completed” in the first place.
    * 2b1. SellSavvy displays a message stating that the order is not marked as completed in the first place.

  Use case ends.


* 2c. There are no orders with the specified index.
    * 2c1. SellSavvy displays an error that the order index is invalid.

  Use case ends.
<div style="page-break-after: always;"></div>

**Use case 10: Delete an order**

* **Use Case**: UC10 - Delete an Order
* **Preconditions**: A customer's list of orders is being displayed.
* **Guarantees**:
    * An order made by the customer will be deleted if input parameters are valid.

**MSS**

1. User finds the order they want to delete.
2. User deletes the order by its index.
3. SellSavvy updates the displayed list of orders.
4. SellSavvy confirms that the deletion is successful by displaying the deleted order's details.

Use case ends.

**Extensions**

* 2a. SellSavvy detects that the order index is missing or non-positive.
    * 2a1. SellSavvy displays an error about the format and states the command format.

  Use case ends.


* 2b. There are no orders with the specified index.
    * 2b1. SellSavvy displays an error that the order index is invalid.

  Use case ends.

**Use case 11: Edit an Order's Details**

* **Use Case**: UC11 - Edit a Order's Details
* **Preconditions**: A customer's list of orders is being displayed.
* **Guarantees**:
    * The specified order's details will be overwritten if the input parameters are valid.

**MSS**

1. User finds the index of the order they want to edit.
2. User specifies the order index along with modifications they want to make to the order's details.
3. SellSavvy updates the order list with the modifications made to the customer.
4. SellSavvy confirms the modification by stating the updated order's details.

Use case ends.

**Extensions**

* 2a. The order index is missing or non-positive.
    * 2a1. SellSavvy displays an error about the format and states the command format.

  Use case ends.


* 2b. SellSavvy detects that there are no orders with the specified index.
    * 2b1. SellSavvy displays an error that the order index is invalid.

  Use case ends.


* 2c. There are no order details specified for modification.
    * 2c1. SellSavvy tells the user that at least one field has to be edited.

  Use case ends.


* 2d. SellSavvy detects that the updated fields do not satisfy its constraints.
    * 2d1. SellSavvy states the constraints of the invalid parameter.

  Use case ends


* 3a. SellSavvy detects that the updated order has similar details to another existing order under the same customer.
    * 3a1. SellSavvy gives a warning that the customer already has an order with similar details.

  Use case resumes from step 4.

**Use case 12: Filter order list by order status**

* **Use Case**: UC12 - Filter an Order List by Order Status
* **Preconditions**: A customer's list of orders is being displayed.
* **Guarantees**:
    * Orders with specified status under the customer will be displayed as a list, if input parameters are valid.

**MSS**

1. User filters the order list of the customer by the status keyword.
2. SellSavvy displays the orders with specified status in a list in GUI.

Use case ends.

**Extensions**

* 1a. The status keyword is missing or invalid.
  * 1a1. SellSavvy displays an error and provides the available status keywords.

  Use case ends.


* 1a. There are no orders with the specified status.
  * 1a1. SellSavvy tells the user that no related orders are found.

  Use case ends.

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should be able to hold up to 100 customers and/or 1000 orders without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. Should be able to be used offline (i.e. without internet connection).
5. Should log user inputs and errors for analysis and debugging.
6. The system should respond within 2 seconds from any user input.

### Glossary

* **CLI**: Command Line Interface
* **Customer**: People who request for delivery order of product from user
* **GUI**: Graphical User Interface
* **JSON**: JavaScript Object Notation
* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Order**: Agreement made by customers with user on delivery of product
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Status**: The current fulfilment condition of the delivery of an order, namely completed or pending.
* **Similar names (for customers, orders and tags)**: Names which are identical if whitespaces and case sensitivity are ignored.
* **Similar details (orders)**: Orders with identical date, quantity and status along with similar item names.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>
<div style="page-break-after: always;"></div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder.

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimal.

1. Saving window preferences

   1. Resize the window to an optimal size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. Saving changes in data

   1. Prerequisites: Has at least 1 customer listed using `listcustomer` in the GUI.

   1.  Add an order under a customer using the `addorder` command.<br>
      Example: `addorder 1 i/Lamp d/20-11-2024 q/3`
      Expected: Order added under the first customer in the customer list and all his orders will be displayed.

   1.  Re-launch the app by double-clicking the jar file.<br>
       Expected: The newest order added is retained.

### Adding a customer

**Note:** Some of the test cases may depend on previous test cases, especially those on testing customers with duplicate/similar names. You are advised to follow the test cases in order.<br>

**Tips:** All the prerequisites below will be fulfilled if you start off with the default sample data and follow the test cases in sequence.

1. Adding a unique customer with all parameters specified.

    1. Prerequisites: Customer with name `John Doe` or other similar names does not already exist in the address book.

    2. Test case: `addcustomer n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 t/friends t/owesMoney`<br>
       Expected: The customer is successfully added. Details of the added customer shown in the status message.

2. Adding a unique customer with all parameters specified using the command alias.

    1. Prerequisites: Customer with name `Betsy Crowe` or other similar names does not already exist in the address book.

    2. Test case: `addc n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal` <br>
       Expected: The customer is successfully added. Details of the added customer shown in the status message.

3. Adding a customer with an identical name.

   1. Prerequisites: Customer with name `Betsy Crowe` already exists in the address book.

   2. Test case: `addcustomer n/Betsy Crowe t/friend e/betsycrowe@duplicate.com a/Newgate Prison p/12345678 t/criminal` <br>
      Expected: No customer is added. Error details shown in the status message. Status bar remains the same.

4. Adding a customer with a [similar name](#glossary) and without the optional `tag` field.

    1. Prerequisites: Customer with name `Betsy Crowe` but not `Betsy crowe` already exist in the address book.

    2. Test case: `addcustomer n/Betsy crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567` <br>
       Expected: The customer is successfully added. A warning and details of the added customer shown in the status message.

<div style="page-break-after: always;"></div>

5. Adding a customer with duplicate tags.

    1. Prerequisites: Customer with name `Yu Sutong` or other similar names does not already exist in the address book.

    2. Test case: `addcustomer n/Yu Sutong t/vvip t/vvip e/su@example.com a/Newgate Prison p/12345678` <br>
       Expected: The customer is successfully added with one of the duplicated tags ignored. Details of the added customer shown in the status message.

6. Adding a customer with [similar tags](#glossary).

    1. Prerequisites: Customer with name `Foo Chao` or other similar names does not already exist in the address book.

    2. Test case: `addcustomer n/Foo Chao t/VVIP t/vvip e/su@example.com a/69, Sembawang Road. #01-01  p/12345678` <br>
       Expected: The customer is successfully added with both similar tags accepted. A warning and details of the added customer shown in the status message.

7. Adding a customer with missing compulsory field.

   1. Test case: `addcustomer n/Lim Kai Xuan e/su@example.com a/69, Sembawang Road. #01-01` <br>
      Expected: No customer is added. Error details shown in the status message. Status bar remains the same.

   2. Test case: `addcustomer n/Lim Kai Xuan e/su@example.com p/12345678` <br>
      Expected: No customer is added. Error details shown in the status message. Status bar remains the same.

### Listing all customers

1. Listing all customers with or without aliasing.

    1. Test case: `listcustomer` <br>
       Expected: All customers are listed. A success message shown in the status message.

    2. Test case: `listc` <br>
       Expected: All customers are listed. A success message shown in the status message.

### Finding customers by name.

1. Finding customers with one keyword.

    1. Test case: `findcustomer bernice` <br>
       Expected:
       - All customers with `bernice` in their names are listed. A success message shown in the status message.
       - If you are using the default sample data, the customer `Bernice Yu` will be listed in the customer list.
       <p><p/>

2. Finding customers with multiple keywords using the command alias.

    1. Test case: `findc alex david` <br>
       Expected:
       - All customers with `alex` or `david` in their names are listed. A success message shown in the status message.
       - If you are using the default sample data, the customer `Alex Yeo` and `David Li` will be listed in the customer list.
       <p><p/>

3. Finding customer who does not exist in the address book.

    1. Prerequisites: Customer with name containing `khengyang` (case-insensitive) does not exist in the address book.

    2. Test case: `findcustomer khengyang` <br>
       Expected: No customers listed. A success message shown in the status message. A message informing user that no related customers are found is shown in the customer list.


### Editing an existing customer

1. Editing a customer while all customers are being shown.

    1. Prerequisites: All customers are listed using the `listcustomer` command with at least 1 customer listed.

    2. Test case: `editcustomer 1 p/91234567 e/johndoe@example.com` <br>
       Expected: The customer is successfully edited. Details of the edited customer shown in the status message.

2. Editing a customer in a filtered list using the command alias.

    1. Prerequisites: Customers filtered using `findcustomer` command with at least 1 customer listed.
       Example: `findcustomer john`

    2. Test case: `editc 1 n/Betsy Crower t/` <br>
       Expected: The customer is successfully edited with all tags removed. Details of the edited customer shown in the status message. The displayed customer list becomes unfiltered and all customers are displayed.

3. Editing a customer to an exact same name as an existing customer.

    1. Prerequisites:
       - Customer with name `Betsy Crowe` already exist in the address book.
       - At least 1 customer is listed.
       - The customer to be edited is not `Betsy Crowe`.
        <p><p/>

    2. Test case: `editcustomer 1 n/Betsy Crowe` <br>
       Expected: No customer is edited. Error details shown in the status message. Status bar remains the same.

4. Editing a customer to have a [name similar to an existing customer](#glossary).

    1. Prerequisites:
        - Customer with name `Betsy Crowe` but not `betsy crowe` already exist in the address book.
        - At least 1 customer is listed.
        - The customer to be edited is not `Betsy Crowe`.
       <p><p/>

    2. Test case: `editcustomer 1 n/betsy crowe` <br>
       Expected: The customer is successfully edited. A warning and details of the edited customer shown in the status message.

5. Editing a customer to have duplicate tags.

    1. Prerequisites: At least one customer is listed.

    2. Test case: `editcustomer 1 t/friends t/friends` <br>
       Expected: The customer's tags is successfully edited with one of the duplicated tags ignored. Details of the edited customer shown in the status message.

6. Editing a customer to have [similar tags](#glossary).

    1. Prerequisites: At least one customer is listed.

    2. Test case: `editcustomer 1 t/Friends t/friends` <br>
       Expected: The customer is successfully edited with both similar tags accepted. A warning and details of the added customer shown in the status message.

7. Editing a customer with invalid inputs.
    1. Prerequisites: At least one customer is listed.

    2. Test case: `editcustomer 1 n/@#$%` <br>
       Expected: No customer is edited. Error details shown in the status message. Status bar remains the same.

<div style="page-break-after: always;"></div>

### Deleting a customer

1. Deleting a customer while all customers are being shown.

   1. Prerequisites: List all customers using the `listcustomer` command with at least 1 customer listed.

   2. Test case: `deletecustomer 1`<br>
      Expected: First customer is deleted from the list. Details of the deleted customer shown in the status message.

   3. Test case: `deletecustomer 0`<br>
      Expected: No customer is deleted. Error details shown in the status message. Status bar remains the same.

   4. Other incorrect `deletecustomer` commands to try: `deletecustomer`, `deletecustomer x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

2. Deleting a customer from a filtered customer list using the command alias.

    1. Prerequisites: Prerequisites: Customers filtered using `findcustomer` command with at least 1 customer listed.<br>
       Example: `findcustomer john`

    2. Test case: `deletec 1`<br>
       Expected: First customer is deleted from the list. Details of the deleted customer shown in the status message.

    3. Test case: `deletec 0`<br>
       Expected: No customer is deleted. Error details shown in the status message. Status bar remains the same.

### Adding an order

**Note:** Some of the test cases may depend on previous test cases, especially those on testing orders with duplicate/similar names. You are advised to follow the test cases in order. <br>

1. Adding a unique order with all parameters specified while all customers is being shown.

    1. Prerequisites:
        - At least 1 customer is displayed in the customer list.
        - All orders under a customer are listed using the `listorder 1` command with at least 1 order listed.
        - There are no orders similar to the order to be added under the first customer.
       <p><p/>

    2. Test case: `addorder 1 i/Lamp d/20-11-2024 q/3`<br>
       Expected: The order is successfully added. Details of the added order shown in the status message. All orders associated with the customer are shown in the order list.

2. Adding a unique order with optional field omitted using the command alias when the customer list is filtered.

    1. Prerequisites: Prerequisites: Customers filtered using `findcustomer` command with at least 1 customer listed.<br>
       Example: `findcustomer bernice`

    2. Test case: `addo 1 i/Books d/02-03-2026` <br>
       Expected: The order is successfully added with default quantity of `1`. Details of the added order shown in the status message. All orders associated with the customer are shown in the order list.

3. Adding an order with missing compulsory field(s).

    1. Prerequisites: At least 1 customer is displayed in the customer list.

    2. Test case: `addorder 1 i/books` <br>
       Expected: No order is added. Error details shown in the status message. Status bar remains the same.

    3. Test case: `addo 1 q/100` <br>
        Expected: No order is added. Error details shown in the status message. Status bar remains the same.

<div style="page-break-after: always;"></div>

4. Adding a [similar order](#glossary).

    1. Prerequisites:
        - At least 1 customer is displayed in the customer list.
        - There is an existing order similar to the order to be added under the first customer.
       <p><p/>

    2. Test case: `addo 1 i/books d/02-03-2026` <br>
       Expected: The order is successfully added. A warning and details of the added order shown in the status message. All orders associated with the customer are shown in the order list.

5. Adding an order with delivery date elapsed.

    1. Prerequisites:
        - At least 1 customer is displayed in the customer list.
        - No order similar to order to be added under the first customer.
       <p><p/>

    2. Test case: `addo 1 i/phone d/02-03-2020` <br>
       Expected: The order is successfully added. A warning and details of the added order shown in the status message. All orders associated with the customer are shown in the order list

### Listing all orders under a customer.

1. Listing all orders with or without aliasing.

    1. Prerequisites: At least 2 customers and at most 99 customers is displayed in the customer list.

    2. Test case: `listorder 1` <br>
       Expected: All orders under the first customer are listed. A success message shown in the status message.

    3. Test case: `listo 2` <br>
       Expected: All orders under the second customer are listed. A success message shown in the status message.

    4. Test case: `listo 100` <br>
       Expected: No change to the order list. Error details shown in the status message. Status bar remains the same.

### Filter orders by status.

1. Filtering order list to display all `pending` orders.

    1. Prerequisites: All orders under a customer are listed using the `listorder` command with at least 1 order listed.<br>
       Example `listorder 1`.

    2. Test case: `filterorder pending` <br>
       Expected: Only pending orders remain in the order list. A success message shown in the status message.

2. Filtering order list to display all `completed` orders using the command alias.

    1. Prerequisites: All orders under a customer are listed using the `listorder` command with at least 1 order listed.<br>
       Example `listorder 1`.

    2. Test case: `filterorder completed` <br>
       Expected: Only completed orders remain in the order list. A success message shown in the status message.

### Editing an existing order

1. Editing an order while all orders under a customer are being shown.

    1. Prerequisites: All orders under a customer are listed using the `listorder` command with at least 1 order listed. <br>
       Example: `listorder 1`

    2. Test case: `editorder 1 i/Light bulb d/21-11-2025` <br>
       Expected: The order is successfully edited. Details of the edited order shown in the status message.

<div style="page-break-after: always;"></div>

2. Editing an order in a filtered order list using the command alias.

    1. Prerequisites: Orders filtered using `filterorder` command with at least 1 order listed. <br>
       Example: `filterorder pending`

    2. Test case: `edito 2 q/22` <br>
       Expected: The order is successfully edited. Details of the edited order shown in the status message.

3. Editing an order to a [similar order](#glossary).

    1. Prerequisites:
        - All orders under a customer are listed using the `listorder 1` command with at least 1 order listed.
        - The first order must be `pending` status.
       <p><p/>

    2. Adding the similar order: `addo 1 i/test d/21-11-2025 q/1`

    3. Test case: `edito 1 i/test d/21-11-2025 q/1` <br>
       Expected: The order is successfully edit. A warning and details of the edited order shown in the status message.

4. Editing an order with invalid inputs.
    1. Prerequisites: At least one order is listed.

    2. Test case: `editorder 1 q/1 2` <br>
       Expected: No order is edited. Error details shown in the status message. Status bar remains the same.

### Deleting an order

1. Deleting an order while all orders under a customer are being shown.

    1. Prerequisites: List all orders using the `listorder 1` command with at least 1 order listed.

    2. Test case: `deleteorder 1`<br>
       Expected: First order is deleted from the list. Details of the deleted order shown in the status message.

    3. Test case: `deleteorder 0`<br>
       Expected: No customer is deleted. Error details shown in the status message. Status bar remains the same.

    4. Other incorrect `deleteorder` commands to try: `deleteorder`, `deleteorder x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

2. Deleting an order from a filtered list using the command alias.

    1. Prerequisites: Orders filtered using `filterorder` command with at least 1 order listed. <br>
       Example: `filterorder pending`

    2. Test case: `deleteo 1`<br>
       Expected: First order is deleted from the list. Details of the deleted order shown in the status message.

    3. Test case: `deleteo 0`<br>
       Expected: No order is deleted. Error details shown in the status message. Status bar remains the same.

<div style="page-break-after: always;"></div>

### Marking an order as completed

1. Marking an order as completed while all orders under a customer are being shown.

    1. Prerequisites:
       - List all customers using the `listorder 1` command with at least 1 order listed.
       - The first order's status is not `Completed`.
       <p><p/>

    2. Test case: `markorder 1`<br>
       Expected: First order is marked as completed. Details of the marked order shown in the status message.

    3. Test case: `markorder 0`<br>
       Expected: No order is marked as completed. Error details shown in the status message. Status bar remains the same.

    4. Other incorrect `markorder` commands to try: `markorder`, `markorder x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

2. Marking an order from a filtered list as completed using the command alias.

   1. Prerequisites: Orders filtered using `filterorder pending` command with at least 1 order listed. <br>

   2. Test case: `marko 1`<br>
      Expected: First order is marked as completed. Details of the marked order shown in the status message. The marked order will disappear from the filtered order list as it is no longer `Pending`.

   3. Test case: `marko 0`<br>
      Expected: No order is marked as completed. Error details shown in the status message. Status bar remains the same.

3. Marking an already completed order as completed.

    1. Prerequisites:
        - At least 1 order listed. <br>
        - The first order's status is `Completed`.
       <p><p/>

    2. Test case: `marko 1`<br>
       Expected: No order is marked as completed. Error details shown in the status message. Status bar remains the same.

### Reverting an order to pending status.

1. Reverting an order to pending status while all orders under a customer are being shown.

    1. Prerequisites:
        - List all customers using the `listorder 1` command with at least 1 order listed.
        - The first order's status is not `Pending`.
       <p><p/>

    2. Test case: `unmarkorder 1`<br>
       Expected: First order is reverted to pending status. Details of the unmarked order shown in the status message.

    3. Test case: `unmarkorder 0`<br>
       Expected: No order is reverted to pending status. Error details shown in the status message. Status bar remains the same.

    4. Other incorrect `unmarkorder` commands to try: `unmarkorder`, `unmarkorder x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

<div style="page-break-after: always;"></div>

2. Reverting an order from a filtered list to pending status using the command alias.

    1. Prerequisites: Orders filtered using `filterorder completed` command with at least 1 order listed. <br>

    2. Test case: `unmarko 1`<br>
       Expected: First order is reverted to pending status. Details of the unmarked order shown in the status message. The unmarked order will disappear from the filtered order list as it is no longer `completed`.

    3. Test case: `unmarko 0`<br>
       Expected: No order is reverted to pending status. Error details shown in the status message. Status bar remains the same.

3. Attempting to revert a order which currently pending.

    1. Prerequisites:
        - At least 1 order listed. <br>
        - The first order's status is `Pending`.
       <p><p/>

    2. Test case: `unmarko 1`<br>
       Expected: No order is reverted to pending status. Error details shown in the status message. Status bar remains the same.

### Saving data

1. Dealing with missing/corrupted data files

    1. Prerequisite: You have not edited the `preferences.json` file. There is a folder named `data` in the same directory as the jar file, and there is a `addressbook.json` file in the `data` folder.

    2. Test case: Delete the `addressbook.json` file. Then, run SellSavvy and exit using the `exit` command.<br>
       Expected: SellSavvy should create a new `addressbook.json` file with default data.

    3. Test case: Delete the `data` folder together with the `addressbook.json` file. Then, run SellSavvy and exit using the `exit` command.<br>
       Expected: SellSavvy should create a new `data` folder and a new `addressbook.json` file inside the folder with default data.

    4. Test case: Corrupt the `addressbook.json` file by changing its contents to an invalid format, e.g. add a non-numeric character to one of the customer's phone number. Then, run SellSavvy and exit using the `exit` command.<br>
       Expected: SellSavvy should discard all data in the file and start with an `addressbook.json` file with an empty customer list.

## **Appendix: Planned Enhancements**

**Team size: 4**

1. **Change the `CUSTOMER_INDEX` of `addorder` command to be optional when a customer's order list is open** <br>
    - **Problem:** Currently, we made the customer's index needed to be specified for `addorder` when the customer's order list is open but related commands like `editorder` and `filterorder` do not require a customer's index to be supplied.
    - **Solution:** We planned to make the customer's index for `addorder` optional when a particular customer's order list is already displayed.
    - **Rationale:** Requiring the user to remember the customer's index may be unnecessary and would facilitate user convenience.
<p><p/>

2. **Allows detection of wrong prefix for commands**
    - **Problem:** Currently, we only detect the relevant prefix for each command and treat all other irrelevant prefixes as part of a parameter input.
    - **Example:** User tries to edit an existing order using `edito 1 d/01-12-2023 n/item`. An error message informs user that date is wrong.
    - **Solution:** Add the functionality to detect such errors and inform the user of the wrong prefix used, instead of treating it as part of the parameters. We also considered allowing users to key in prefixes such as `n/` or `a/` as string inputs via the use of special symbols, possibly using a symbol such as `\`.
    - **Rationale:** Keying in a wrong prefix is a fairly common user mistake and the existing error message does not seem to match the actual error happening. Detecting prefix may restrict users from typing inputs with prefix such as `n/` or `a/` as parameter string inputs, hence we will need to add the functionality to do it as well.
<p><p/>

<div style="page-break-after: always;"></div>

3. **Improve error messages related to handling of index**
    - **Problem:** Currently, having an invalid value (e.g. `0`, `1 2`) for `CUSTOMER_INDEX` and `ORDER_INDEX` will result in an error stating "Invalid command format!" along with the command format. While in the command format it states that said parameters must be a positive integer, the error message could be more concise.
    - **Example:** User tries to delete a customer using `deletec 0`, the following error message is displayed:
      ```
      Invalid command format! 
      deletecustomer: Deletes the customer identified by the index number used in the displayed customer list.
      Parameters: CUSTOMER_INDEX (must be a positive integer)
      Example: deletecustomer 1
      ```
    - **Solution:** Shorten the error message to state that the index should be a positive integer.
    - **Rationale:** Stating that the command format is invalid in the first line of the error message may confuse the user in a case where they type an invalid index such as `1a` by mistake, as it is only the parameters which are incorrect in this case. Making the error message more concise also provides users with a better experience.
<p><p/>

4. **Improve display of longer messages or entries**
   - **Problem:** Currently, longer messages or entries can be cut off, especially for users with smaller resolution/larger scale.
   - **Example:** ![longMessages](images/longMessages.png)
   - **Solution:** Improve text wrapping, allow customisable GUI and resizable UI parts.
   - **Rationale:** Longer messages/entries being cut off may be inconvenient for certain users (e.g. being required to scroll left/right to read an error message). Hence, providing text wrapping, allowing users to customise the GUI (e.g. whether they prefer to display an ellipsis, wrap text, etc.) and resize the UI parts will give them more freedom to adjust based on their preferences and for convenience.
<p><p/>

5. **Include a warning if there is only one domain label provided for email**
   - **Problem:** Currently, the `EMAIL` parameter accepts emails with only one domain label e.g. `johndoe@gmail`
   - **Solution:** Allow emails with a single domain label, but provide a warning for it.
   - **Rationale:** It is possible for some email address to have only one domain label, but we deem it unlikely to happen for the target users of this application. It is more likely that the user may forget to include a second domain label (e.g. missing `.com` in `johndoe@gmail.com`).
<p><p/>

6. **Make sample data more relevant/realistic to the context**
   - **Problem:** Currently, the loaded sample data has customers who have tags such as `friends`, `neighbours`, etc.
   - **Solution:** Change the tags (and any relevant sample data) to fit more with the context of the target user.
   - **Rationale:** It is unlikely that a target user will label their customers as the current tags in the sample data. Making the sample data more relevant will allow users trying out the application to better simulate the experience of a target user using the application.
<p><p/>

7. **Allows case-insensitivity for relationship indicator in customer's `NAME`**
   - **Problem:** Currently, we only allow the relationship indicator to be "S/O" or "D/O", which are in upper-case.
   - **Example:** User tries to add a customer using `addc n/john s/o doe p/98765432 e/johnd@example.com a/John street`. An error message informs user that name is wrong.
   - **Solution:** Add the functionality that allows the relationship indicator, namely "S/O" and "D/O", to be case-insensitive.
   - **Rationale:** It is normal for a user to enter a customer name in any casing, including relationship indicator.
<p><p/>

8. **Allow users to input customer name instead of index for commands with `CUSTOMER_INDEX` parameter**
   - **Problem:** Currently, we only allow using customer index to identify customers. This may take longer for some users who prefer to use customer names to refer to customers.
   - **Solution:** Allow customers to be referenced by both name or index. This should however come with a check where customers should not have a positive integer as their name.
   - **Rationale:** When taking actions (e.g. adding orders), some users may remember or are given the name of the customer. This allows them to directly take action for the customer instead of using the `findcustomer` command first if they have a lot of customers in their list.
<p><p/>

<div style="page-break-after: always;"></div>

## **Appendix: Effort**

This section documents the involved effort to evolve AB3 into SellSavvy.

### Creating `Order` class and handling

At this point, AB3 `Person` is refactored into `Customer`.

Order handling involved:
* Augmenting `Customer` into `Order` class and existing commands to handle `Order`.
  * E.g. `AddCustomerCommand` into `AddOrderCommand`.
* Augmenting `UniqueCustomerList` into `OrderList` class and existing methods to handle `OrderList`.
  * Updating parameter handling for orders.
* Enhance UI and Storage functionality to support `Order` and `OrderList`.

This was time and effort intensive as:
* Orders are handled differently from Customer because identical orders should be allowed.
* Orders requires additional levels of similarity checks.
* Further enhancements for order management by parameters is stated below.
* The `OrderListPanel` implementation is not a direct parallel to the `CustomerListPanel` because the `OrderList` is part of the `Customer` class instead of being directly within the `ModelManager` class. This distinction necessitates handling the selected `Customer` to ensure the `OrderListPanel` correctly displays the orders relevant to the currently chosen customer.

### Implementing `Order` parameters for order management

This involved:
* Creating parameters necessary for managing orders, namely `Item`, `Date`, `Quantity` and `Status`.
* Integrating parameters with order commands.

This was challenging as:
* `Date` involves additional checks to contextualise delivery dates to order management.
* Updating the command parsers to correctly handle order parameters.
* Deliberating `Status` limitations to manage order delivery completion.

### Implementing `filterOrder` command

This involved:
* Creating predicate class to support filtering by order `Status`.
* Enhancing model to support filtering for displayed order list.

This was challenging as:
* Interpreting expected filter of displayed list after commands such as `listOrder` and `addOrder`.
