---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# InvenTrack Developer Guide

<!-- * Table of Contents -->

## Table of contents

<page-nav-print />

1. [Acknowledgements](#acknowledgements)
2. [Setting up, getting started](#setting-up-getting-started)
3. [Design](#design)
    - [Architecture](#architecture)
    - [UI Component](#ui-component)
    - [Logic Component](#logic-component)
    - [Model Component](#model-component)
    - [Storage Component](#storage-component)
    - [Common classes](#common-classes)
4. [Implementation](#implementation)
5. [Documentations](#documentation-logging-testing-configuration-dev-ops)
6. [Appendix: Requirements](#appendix-requirements)
   - [Product Scope](#product-scope)
   - [User Stories](#user-stories)
   - [Use Cases](#use-cases)
   - [Non functional Requirements](#non-functional-requirements)
   - [Glossary](#glossary)
7. [Appendix: Instructions for manual testing](#appendix-instructions-for-manual-testing)

---

## **Acknowledgements**

Our code is just extended ideas of AB3 AddressBook. No third party library is used except plantUML for DG diagrams.

---

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

---

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

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete_supplier su/Hayley`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

---

### UI component

The **API** of this component is specified in [Ui.java](https://github.com/AY2425S1-CS2103T-T17-3/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `SupplierListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [MainWindow](https://github.com/AY2425S1-CS2103T-T17-3/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [MainWindow.fxml](https://github.com/AY2425S1-CS2103T-T17-3/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Supplier` object residing in the `Model`.

---

### Logic component

**API** : [Logic.java](https://github.com/AY2425S1-CS2103T-T17-3/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete_supplier su/Hayley")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete_supplier su/Hayley` Command" />

> **NOTE:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a supplier).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

---

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Supplier` and `Product` objects (which are contained in a `UniqueSupplierList` and `UniqueProductList`object respectively).
* stores the currently 'selected' `Supplier` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Supplier>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* Similarly, stores the 'selected' `Product` objects as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Product>`.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

> **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Supplier` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Supplier` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

---

### Storage component

**API** : [Storage.java](https://github.com/AY2425S1-CS2103T-T17-3/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

---

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

#### Assign product to supplier feature

The AssignProductCommand is responsible for assigning a specified product to a specified supplier in the inventory management application. This command involves several steps, primarily focused on validating input, checking assignment status, and updating the Model to reflect the new association between the product and supplier.

The AssignProductCommand follows these main steps when executed:
1. Find Product and Supplier
2. Check Assignment Status
3. Update Product and Supplier

<puml src="diagrams/AssignSequenceDiagram.puml" width="550" />

#### Proposed Implementation

TThe AssignProductCommand is responsible for assigning a specified Product to a specified Supplier in an address book model. Here’s a step-by-step breakdown of how it functions:

#### Command Initialization:
AssignProductCommand is instantiated with a ProductName (product to be assigned) and Name (supplier to whom the product will be assigned).

This command object holds these details for execution.
Execution (execute method): **When the command executes, it:**

1. Finds the Product: Calls findProductByName on the Model to retrieve the Product based on productName. If not found, it throws a CommandException with a message indicating the product was not found. 
2. Finds the Supplier: Calls findSupplierByName to retrieve the Supplier based on supplierName. If the supplier is not found, it throws a CommandException.
3. Checks Assignment Status: Verifies if the product is already assigned to any supplier. If assigned to a different supplier, it throws an exception. If already assigned to the target supplier, it prevents redundant assignments.
4. Assigns the Product to the Supplier: Calls assignProductToSupplier, which creates a new Product with updated supplier information and adds it to the supplier’s product list.

#### Updating the Model:

**After successful assignment:**

The model updates the supplier and product lists to reflect the new state.
It returns a success message, indicating the product and supplier names.

#### Helper Methods:
1. findProductByName: Looks up the product in the model by its name.
2. findSupplierByName: Looks up the supplier in the model by its name.
checkProductAssignmentStatus: Ensures that the product can be assigned to the supplier by checking if it’s already assigned.
3. assignProductToSupplier: Updates the product’s supplier reference and the supplier’s product list, then updates the model with these changes.

#### Design considerations:

**Aspect: Assign executes:**

* **Current choice:** Saves in the system after successful execution.
  * Pros: Easy to implement. Real time updates can be seen.
  * Cons: Allows a product to be supplied by only one Supplier.

---
## Planned Enhancements

1. Emails and phone numbers should be taken into account for duplicate handling, for the Add Supplier command as it currently takes only name into consideration.
2. Update can take in +/- stock level so user does not need to count current stock level just need to know the increase or decrease in stock level
3. Make the command names shorter for increasing easiness to the fast typing users.
4. We would want to implement edit features as for any information currently, the user to delete the existing one and adding new information in case of update of values. (especially in case of tags)
5. Number greater than Integer maximum will not give correct error, hence it is to be updated.
6. Supplier and Product should have different panels in UI to make it look better and for the ease of not having to switch panels with view commands unnecessarily.
7. Mac users might face some issues downloading jar file with the given instructions in UG, so the workaround shall be provided.
---

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

---

## **Appendix: Requirements**

### Product scope

**Target user profile**:

This application is aimed towards neighbourhood convenience store/inventory managers who often need to manage their inventories. This application will help convenience store managers who:

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

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                 | So that I can…​                                                       |
|----------|--------------------------------------------|------------------------------|-----------------------------------------------------------------------|
| `* * *`  | new user                                   | see usage instructions       | refer to instructions when I forget how to use the App                |
| `* * *`  | user                                       | add a new supplier             |                                                                       |
| `* * *`  | user                                       | delete a supplier              | remove entries that I no longer need                                  |
| `* * *`  | user                                       | find a supplier by name        | locate details of suppliers without having to go through the entire list |
| `* * *`  | user                                       | find a supplier by tags        | see only the details of suppliers with similar tags |
| `* *`    | user                                       | hide private contact details | minimize chance of someone else seeing them by accident               |
| `*`      | user with many suppliers in the address book | sort suppliers by name         | locate a supplier easily                                                |
| `* * *`  | inventory manager                          | add a new product           |                                                                       |
| `* * *`  | inventory manager                          | assign products to suppliers  | collectively manage products which corresponding suppliers supply               |
| `* * `  | inventory manager                          | remove suppliers/products              | prune obsolete data   |
| `* * `  | inventory manager                          | find products by name              | locate details of products without having to go through the entire list   |
| `* * `  | inventory manager                          | find products by tags              | see only the details of products with similar tags   |
| `* * `  | inventory manager                          | find products for a given supplier              | see all the products a certain supplier supplies   |
| `* * `  | inventory manager                          | set pre-defined max stock level for products              | determine quantity to request for an order   |
| `* * `  | experienced inventory manager                          | set pre-defined minimum stock level for a product              | ensure availability of the product at all times   |
| `* * `  | experienced inventory manager                          | request an order from a supplier | replenish stocks of the corresponding products |
| `* `     | inventory manager             | set up automatic reordering based on stock levels and pre-defined thresholds | prevent stockouts and overstocking efficiently |
| `* * `  | novice inventory manager                          | easily search suppliers/products      | browse large datasets quickly                            |
| `* `     | inventory manager             | receive automated re-order alerts | reduce the risk of stockouts or overstocking |
| `* *`    | inventory manager             | view payment/order history for a supplier | so that I have a clear understanding of past cash flow and plan future expenses |
| `* *`    | inventory manager             | get alerts in case of any supplier issues        | find a convenient replacement in time  |
| `* *`    | inventory manager             | use autocomplete        | easily type in longer names or commands  |
| `* *`    | inventory manager             | quickly send standardized communication to suppliers via email or messaging without leaving the interface        | streamline communication and reduce errors  |
| `* *`    | inventory manager             | I can store contracts and terms of agreements with suppliers in the system        | quickly refer to them when negotiating orders  |
| `* *`    | inventory manager             | be able to get printable txt of relevant information  |  get hard-copy of the information  |
| `*`      | experienced inventory manager             | sort suppliers by some criterion        | organize data based on my needs |
| `*`      | experienced inventory manager             | customize my dashboard to show only the relevant information        | work more efficiently based on my specific needs |
| `*`      | inventory manager             | weekly summaries of inventory levels, orders and supplier communications | stay on top of opertions without manually checking the system |
| `*`      | experienced inventory manager             | assign a product to multiple suppliers | have alternative options if one supplier is unable to fulfil the order |
| `*`      | experienced inventory manager             | create macros/shortcuts for frequently used commands | save time |
| `*`      | experienced inventory manager             | be able to see most used commands | make common processes faster |
| `* `     | inventory manager             | update supplier information        | organize data based on my needs      |
| `*`      | inventory manager             | be able to generate insights on supplier performance based on past interactions (e.g., delivery timeliness, order accuracy)  | make informed decisions about ongoing supplier relationships  |

### Use cases

(For all use cases below, the **System** is the `Inventory Records` and the **Actor** is the `Inventory Manager`, unless specified otherwise)

**Use case: Add Products/Suppliers**

**Preconditions:**
* The Inventory Manager is logged into the InvenTrack system.

**MSS**
1. Inventory Manager tries to add a product/supplier.
2. System validates that the product name/supplier name exists in the list.
3. The system adds the specified product/supplier.
4. System updates the product/supplier information in the system.
5. System confirms the assignment with a success message.

   Use case ends.

**Extensions**

* 1a. Product/Supplier name is in invalid format:
    * 1a1. System displays an error message.
    * 1a2. System prompts the Inventory Manager to re-enter the command.
    * 1a3. Use case resumes at step 1.

* 2a. Product/Supplier name exists:
    * 2a1. System displays an error message - duplicate product/supplier found.
    * 2a2. System prompts the Inventory Manager to re-enter the command.
    * 2a3. Use case resumes at step 1.

**Use case: Assign Products to Suppliers**

**Preconditions:**
* The Inventory Manager is logged into the InvenTrack system.

**MSS**
1. Inventory Manager tries to assign a product to a supplier.
2. System validates that the product name & supplier name exists in the list.
3. System validates the product is not assigned to any supplier.
4. The system assigns the specified product to the specified supplier.
5. System updates the product-supplier relationship in the list.
6. System confirms the assignment with a success message.

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

* 3a. Product is assigned to other supplier:
  * 3a1. System displays an error message
  * 2a2. System prompts the Inventory Manager to re-enter the command.
  * 2a3. Use case resumes at step 1.

**Use case: Unassign Products**

**Preconditions:**
* The Inventory Manager is logged into the InvenTrack system.
* Product must be already assigned to any supplier in the system.

**MSS**
1. Inventory Manager tries to unassign a product to a supplier.
2. System validates that the product name exists in the list.
3. System validates the product is assigned to any one of the supplier and retrieves the supplier.
4. The system unassigns the specified product.
5. System updates the product-supplier information in the system.
6. System confirms the unassignment with a success message.

   Use case ends.

**Extensions**

* 1a. Product/Supplier name is in invalid format:
    * 1a1. System displays an error message.
    * 1a2. System prompts the Inventory Manager to re-enter the command.
    * 1a3. Use case resumes at step 1.

* 2a. Product or Supplier name does not exist:
    * 2a1. System displays an error message.
    * 2a2. System prompts the Inventory Manager to re-enter the command.
    * 2a3. Use case resumes at step 1.

* 3a. Product is not assigned to other supplier:
    * 3a1. System displays an error message - not previously assigned.
    * 2a2. System prompts the Inventory Manager to re-enter the command.
    * 2a3. Use case resumes at step 1.

**Use case: Delete Suppliers/Products**

Preconditions:
* The Inventory Manager is logged into the InvenTrack system.

**MSS**

1. Inventory Manager tries to delete a particular product or supplier.
2. System checks if product or supplier exists which is to be deleted.
3. System removes the specified supplier or product from the list.
4. System removes any supplier-product relationship.
5. System confirms the deletion with a success message.

    Use case ends.

Additional notes:
* After performing a delete supplier operation, the Inventory Manager must manually refresh the view to see the changes
  to supplier-product relations. This can be done by <ins>viewing the products</ins>.

**Extensions**

* 1a. Product/Supplier name is in invalid format:
    * 1a1. System displays an error message.
    * 1a2. System prompts the Inventory Manager to re-enter the command.
    * 1a3. Use case resumes at step 1.

* 2a. Product/Supplier name does not exist:
    * 2a1. System displays an error message.
    * 2a2. System prompts the Inventory Manager to re-enter the command.
    * 2a3. Use case resumes at step 1.

**Use case: Setting Thresholds for Products Stock**

Preconditions:
* The Inventory Manager is logged into the InvenTrack system.
* The product for which the threshold is being set exists in the system.

**MSS**
1. Inventory Manager tries to set a threshold for a product.
2. System validates that the product name exists in the list.
3. System sets the specified threshold for the product as per the input prefixes.
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

**Use case: Updating stock levels for Products**

Preconditions:
* The Inventory Manager is logged into the InvenTrack system.
* The product for which the stock level is being updated exists in the system.

**MSS**
1. Inventory Manager tries to update stock level for a product.
2. System validates that the product name exists in the list.
3. System updates the specified stock level for the product.
4. System updates the product information in the list.
5. System confirms the threshold setting with a success message.

   Use case ends.

**Extensions**
* 1a. stock level is in invalid format(not numeric):
    * 1a1. System displays an error message.
    * 1a2. System prompts the Inventory Manager to re-enter the command.
    * 1a3. Use case resumes at step 1.

* 2a. Product name does not exist:
    * 2a1. System displays an error message.
    * 2a2. System prompts the Inventory Manager to re-enter the command.
    * 2a3. Use case resumes at step 1.

**Use case: Viewing Products/Suppliers**

Preconditions:
* The Inventory Manager is logged into the InvenTrack system.

**MSS**
1. Inventory Manager tries to view a product/supplier.
2. System checks whether a keyword or other optional prefixes are found.
3. System matches the keywords to product/supplier name and other data accordingly.
4. System displays the product/supplier names respectively.
5. System confirms the view with a result message.

   Use case ends.

**Extensions**

* 1b. Product name does not exist:
    * 2a1. System displays an error message.
    * 2a2. System prompts the Inventory Manager to re-enter the command.
    * 2a3. Use case resumes at step 1.

* 2a. No keyword or other prefixes found:
    * 1a1. System displays all the existing products/suppliers respectively.

**Use case: Access help guide**
* User - Any user with access to the system

**Preconditions:**
* The user is logged into the InvenTrack system.

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

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should be able to handle up to 50 suppliers and 3000 products without a noticeable sluggishness in performance for typical usage.
3. The system should respond to user commands within 500ms, even with large data.
4. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
5. The system should automatically back up critical data and support easy recovery in case of abrupt power or network loss.
6. Error messages should be clear, helping users understand and rectify their mistakes.
7. The system’s architecture must allow developers to easily update components without affecting the overall functionality.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Architecture Diagram**: A visual representation showing the structure of the system and how its components interact.
* **UI (User Interface)**: The visual part of the system that allows users to interact with the application (e.g., command box, result display).
* **CommandBox**: A UI element where users input commands to interact with the application.
* **ObservableList**: A list that the UI can listen to and update itself automatically when the data changes.
* **API (Application Programming Interface)**: A set of methods that different components use to interact with each other.
* **Parser**: Converts user commands (entered as text) into objects the system can understand and execute.
* **Tag**: A label associated with items in the inventory or suppliers to categorize them, stored centrally to avoid redundancy.
* **JavaFX**: A framework used to build the graphical user interface, managing visual elements like windows and buttons.
* **.fxml Files**: XML-based files used to define the layout of the UI components in JavaFX.
* **CommandResult**: Encapsulates the result of a command execution, such as success or error messages.
* **Supplier**: A contact responsible for supplying products for the store.
* **Stock Level**: Current inventory, or the number of items of that product SKU, in the store.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

> **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

### Launch and shutdown

1. Initial launch
   1. Download the jar file and copy into an empty folder

   2. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences
   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Viewing Help
1. Accessing the help guide

    1. Test case 1: `help`<br>
        * Description: Getting a list of all commands.
        * Expected:
            * A pop-up window appears containing a message to copy the link to the user guide.
            * The application remains responsive in the background.

    2. Test case 2: `help me`<br>
        * Description: Typing an invalid help command.
        * Expected:
            * No help window opens.
            * An error message is displayed indicating an unknown command.

### Autocomplete
1. Using autocomplete for commands

    1. Test case 1: Start typing `add_` and press the Tab key<br>
        * Description: Attempting to automatically add a product or supplier.
        * Expected:
            * he command auto-completes to add_supplier or add_product.
            * If multiple options are available, pressing Tab cycles through them.

    2. Test case 2: Start typing `xyz` and press the Tab key<br>
        * Description: Attempt to auto complete an invalid command
        * Expected:
            * No autocomplete suggestions are provided.
            * The command remains as typed.

    3. Test case 3: Type `assign pr/` and press the Tab key<br>
        * Description: Using autocomplete for supplier or product names
        * Expected:
            * A list of existing product names is suggested.
            * You can select a product name from the suggestions.
            * Negative test case: Autocomplete with non-existent name

### Adding a supplier
1. Adding a supplier while all suppliers are being shown

    1. Test case 1: `add_supplier n/John Doe p/98765432 e/johndoe@example.com a/123 Baker Street t/Reliable`<br>

        * Description: Add a supplier with all valid fields.
        * Expected:
            * The supplier "John Doe" is added to the supplier list.
            * A success message is displayed confirming the addition.
            *The supplier appears at the end of the supplier list.
    2. Test case 2: `add_supplier n/John Doe p/98765432 e/johndoe@example.com`<br>

        * Description: Attempt to add a supplier with missing compulsory fields (address is missing).
        * Expected:
            *No supplier is added.
            * An error message is displayed indicating that the address field is missing.

    3. Test case 3: `add_supplier n/John Doe p/abcd1234 e/johndoe@example.com a/123 Baker Street` <br>

        * Description: Attempt to add a supplier with invalid phone number format.
        * Expected:
            * No supplier is added.
            * An error message is displayed indicating that the phone number format is invalid.
2. Other incorrect add_supplier commands to try: `add_supplier`, `add_supplier n/`, `add_supplier p/12345678`, `add_supplier e/johndoe@example.com a/123 Baker Street`, ...<br> Expected: Similar to previous; no supplier is added, and appropriate error messages are displayed indicating missing compulsory fields or invalid command format.

### Adding a product
1. Adding a product while all products are being shown

    1. Test case 1: `add_product n/Chocolate Bar stk/100 su/John Doe t/Snack`<br>

        * Description: Add a product with all valid fields.
        * Expected:
            * The product "Chocolate Bar" is added to the product list.
            * The product is assigned to the supplier "John Doe".
            * A success message is displayed confirming the addition.
            * The product appears at the end of the product list with the correct details.

    2. Test case 2: `add_product n/Chocolate Bar su/Nonexistent Supplier`<br>

        * Description: Attempt to add a product with a non-existent supplier.
        * Expected:
            * No product is added.
            * An error message is displayed indicating that the supplier does not exist.

    3. Test case 3: `add_product n/Chocolate Bar stk/-10 su/John Doe`<br>

        * Description: Attempt to add a product with an invalid stock level (negative number).
        * Expected:
            * No product is added.
            * An error message is displayed indicating that the stock level must be zero or a positive integer.

2. Other incorrect add_product commands to try: `add_product`, `add_product n/`, `add_product stk/100`, `add_product su/John Doe`, ...<br> Expected: Similar to previous; no product is added, and appropriate error messages are displayed indicating missing compulsory fields or invalid command format.

### Assigning a product to supplier
1. Assigning a product to supplier

    1. Prerequisites:
        * Ensure both the product "Chocolate Bar" and supplier "John Doe" exist in the system.
        * The product "Chocolate Bar" is not already assigned to any supplier.

    2. Test case 1: Enter `assign pr/Chocolate Bar su/John Doe` and press Enter.<br>
        * Description: Assign a product to a supplier.
        * Expected:
            * The product "Chocolate Bar" is assigned to the supplier "John Doe".
            * The product details now show the assigned supplier.

    3. Test case 2: Enter `assign pr/Chocolate Bar su/John Doe` again and press Enter.<br>
        * Description: Assigning a product that is already assigned.
        * Expected:
            * No changes are made.
            * An error message is displayed indicating that the product is already assigned to that supplier.

    4. Test case 3: Enter `assign pr/Chocolate Bar su/Nonexistent Supplier` and press Enter.<br>
        * Description: Assigning a product to a non-existent supplier.
        * Expected:
            * No changes are made.
            * An error message is displayed indicating that the supplier does not exist.

### Unassigning product from a supplier
1. Unassigning a product from its supplier while all products are being shown

    1. Prerequisites:

        * Ensure that the product to be unassigned exists in the system and is currently assigned to a supplier.
        * View all products using the view_product command to confirm the current assignment.
    2. Test case 1: `unassign pr/Chocolate Bar`<br>

        * Description: Unassign the product "Chocolate Bar" from its supplier.
        * Expected:
            * The product "Chocolate Bar" is unassigned from its supplier.
            * A success message is displayed confirming the unassignment.
            The product details no longer show an assigned supplier.
    3. Test case 2: `unassign pr/Unassigned Product`<br>

        * Description: Attempt to unassign a product that is not assigned to any supplier.
        * Expected:
            * No changes are made.
            * An error message is displayed indicating that the product is not assigned to any supplier.
    4. Test case 3: `unassign pr/Nonexistent Product`<br>

        * Description: Attempt to unassign a product that does not exist in the system.
        * Expected:
            * No changes are made.
            * An error message is displayed indicating that the product does not exist.
2. Other incorrect unassign commands to try: `unassign`, `unassign pr/`, `unassign su/Supplier Name`, ...<br>

Expected: Similar to previous; no changes are made, and appropriate error messages are displayed indicating missing compulsory fields or invalid command format.

### Setting thresholds for products
1. Setting thresholds of a product while consecutively on the products view.
    1. Prerequisites:
        * View all products using the `view_product` command.

    2. Test case 1: `set_threshold pr/Bubble tea min/10 max/100`<br>
        * Description: Update a product's min stock as 10 and max stock as 100.
        * Expected:
            * The specified stock levels are updated for the product in list.
            * You can see the change right there, might have to scroll down to the product before executing command to see it change.
            * Details/Results are shown in the success message.
    3. Test case 2: `set_threshold pr/Milk packets min/20`
        * Description: Only min stock is to be set.
        * Expected:
            * Minimum stock Level is set for the product
            * You can see the change right there, might have to scroll down to the product before executing command to see it change.
            * System displays success message with results.
    4. Test case 3: `set_threshold pr/Yarn max/80`
        * Description: Only max stock is to be set.
        * Expected:
            * Maximum stock Level is set for the product.
            * You can see the change right there, might have to scroll down to the product before executing command to see it change.
            * System displays success message with results.
    5. Test case 4: `set_threshold pr/Sunscreen lotion min/-5`
       * Description: Invalid stock level in command.
       * Expected:
           * Minimum stock Level is not set for the product
           * Displays failure message with error being that the stock level has to be greater than 0(zero).

### Updating a stock Level
1. Updating stock levels of a product while consecutively on the products view.
    1. Prerequisites:
        * View all products using the `view_product` command.

    2. Test case 1: `update_stock pr/Eggs stk/100`<br>
        * Description: Update a product's stock with value 100.
        * Expected:
            * The specified stock level is updated for the product in list.
            * You can see the change right there, might have to scroll down to the product before executing command to see it change.
            * Details/Results are shown in the success message.
    3. Test case 2: `update_stock pr/Pasteurised/Skimmed Milk packets stk/20`
        * Description: Invalid product name in command.
        * Expected:
            * Stock Level is not updated for the product
            * System displays error message as names cannot have "/" in them.
            * Details are shown in the failure message.

### Locating all suppliers
1. Viewing suppliers using various filters
    1. Prerequisites:

        * Ensure that there are multiple suppliers in the system with varying names and tags.
For example, suppliers named "John Doe", "Jane Smith", tagged with "Reliable", "Fast", etc.
    2. Test case 1: `view_supplier`<br>

        * Description: View all suppliers without any filters.
        * Expected:
            * All suppliers in the system are displayed.
            * A message is displayed indicating the number of suppliers found.
    3. Test case 2: `view_supplier n/John`<br>

        * Description: View suppliers with names containing "John".
        * Expected:
            * Suppliers with names containing "John" are displayed.
            * A message is displayed indicating the number of suppliers found.
    4. Test case 3: `view_supplier t/Reliable`<br>

        * Description: View suppliers tagged with "Reliable".
        * Expected:
            * Suppliers with the tag "Reliable" are displayed.
            * A message is displayed indicating the number of suppliers found.
    5. Test case 4: `view_supplier n/John t/Reliable`<br>

        * Description: View suppliers with names containing "John" and tagged with "Reliable".
        * Expected:
            * Suppliers matching both criteria are displayed.
            * A message is displayed indicating the number of suppliers found.

2. Other incorrect view_supplier commands to try: `view_supplier x/`, `view_supplier n/`, `view_supplier t/`, ...<br>

Expected: Appropriate error messages are displayed indicating missing or invalid prefixes.

### Locating all products
1. Viewing products using various filters

    1. Prerequisites:

        * Ensure that there are multiple products in the system with varying names, suppliers, and tags.
        For example, products named "Chocolate Bar", "Candy Cane", tagged with "Snack", assigned to suppliers like "John Doe".
    2. Test case 1: `view_product`<br>

        * Description: View all products without any filters.
        * Expected:
            * All products in the system are displayed.
            * A message is displayed indicating the number of products found.
    3. Test case 2: `view_product n/Chocolate`<br>

        * Description: View products with names containing "Chocolate".
        * Expected:
            * Products with names containing "Chocolate" are displayed.
            * A message is displayed indicating the number of products found.
    4. Test case 3: `view_product t/Snack`<br>

        * Description: View products tagged with "Snack".
        * Expected:
            * Products with the tag "Snack" are displayed.
            * A message is displayed indicating the number of products found.
    5. Test case 4: `view_product su/John Doe`<br>

        * Description: View products supplied by "John Doe".
        * Expected:
            * Products assigned to the supplier "John Doe" are displayed.
            * A message is displayed indicating the number of products found.
    6. Test case 5: `view_product t/Snack su/John Doe`<br>

        * Description: View products tagged with "Snack" and supplied by "John Doe".
        * Expected:
            * Products matching both criteria are displayed.
            * A message is displayed indicating the number of products found.
    7. Test case 6: `view_product n/Chocolate t/Snack su/John Doe sort/d`<br>

        * Description: View products with multiple filters and sort in decreasing order of stock proximity to minimum threshold.
        * Expected:
            * Products matching all criteria are displayed, sorted accordingly.
            * A message is displayed indicating the number of products found.

### Deleting a supplier

1. Deleting a supplier while all suppliers are being shown

    1. Prerequisites:
        * View all suppliers using the `view_supplier` command.
        * Ensure that there are multiple suppliers in the list.

    2. Test case 1: `delete_supplier su/Supplier Name`<br>
        * Description: Delete a supplier using a valid unique name.
        * Expected:
            * The specified supplier is deleted from the supplier list.
            * Any supplier-product relationships involving this supplier are removed.
            * Use the `view_product` command to verify that products that were assigned the deleted supplier now have no assigned supplier
            * Details of the deleted supplier are shown in the success message.

    3. Test case 2: `delete_supplier su/R@chel`<br>
        * Description: Attempt to delete a supplier with an invalid name format containing special characters.
        * Expected:
            * No supplier is deleted.
            * An error message is displayed indicating the invalid supplier name format.

    4. Test case 3: `delete_supplier su/NonexistentSupplier`<br>
        * Description: Attempt to delete a supplier that does not exist in the system.
        * Expected:
            * No supplier is deleted.
            * An error message is displayed indicating that the supplier does not exist.

2. Other incorrect delete commands to try: `delete`, `delete_supplier x/`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

### Deleting a product
1. Deleting a product while all products are being shown

    1. Prerequisites:

        * Ensure that the product to be deleted exists in the system.
        * View all products using the view_product command.
    2. Test case 1: `delete_product pr/Chocolate Bar`<br>

        * Description: Delete an existing product "Chocolate Bar".
        * Expected:
            * The product "Chocolate Bar" is removed from the product list.
            * A success message is displayed confirming the deletion.
            * The product no longer appears in the product list.
    3. Test case 2: `delete_product pr/Nonexistent Product`<br>

        * Description: Attempt to delete a product that does not exist.
        * Expected:
            * No changes are made.
            * An error message is displayed indicating that the product does not exist. 
    4. Test case 3: `delete_product pr/Invalid/ProductName`<br>

        * Description: Attempt to delete a product using an invalid product name containing special characters.
        * Expected:
            * No product is deleted.
            * An error message is displayed indicating the invalid product name format. 
2. Other incorrect delete_product commands to try: delete_product, delete_product pr/, delete_product x/Chocolate Bar, ...<br>

Expected: Similar to previous; no product is deleted, and appropriate error messages are displayed indicating missing compulsory fields or invalid command format.

### Clearing all entries
1. Clearing all data from the application

    1. Prerequisites:

        * Ensure you have backed up your data before testing this command, as it will permanently delete all entries.
    2. Test case 1: `clear`<br>

        * Description: Clear all suppliers and products from the system.
        * Expected:
            * All suppliers and products are removed from the system.
            * A success message is displayed confirming that all entries have been cleared.
            * The supplier and product lists are empty upon using view_supplier and view_product commands.
    3. Test case 2: `clear extra`<br>

        * Description: Attempt to clear entries using an invalid command.
        * Expected:
            * No data is cleared.
            * An error message is displayed indicating an unknown command.

### Exiting the app
1. Exiting the application

    1. Test case 1: `exit`<br>

        * Description: Close the application using the correct command.
        * Expected:
            * The application closes gracefully.
            * No error messages are displayed.
    2. Test case 2: `quit` or `close`<br>

        * Description: Attempt to exit the application using invalid commands.
        * Expected:
            * The application does not close.
            * An error message is displayed indicating an unknown command.

### Saving data
1. Verifying automatic data saving

    1. Test case 1:
        * Tasks:
            * Make changes to the data (e.g., add a supplier or product).
            * Close the application using the exit command.
            * Re-launch the application.
        * Expected: 

            * The changes made before exiting are retained.
            * The new supplier or product appears in the respective list.

2. Dealing with missing/corrupted data files

    1. Test case 1: Simulate a missing data file

        * Tasks:
            * Navigate to the data file location specified at the bottom status bar (e.g., data/addressbook.json).
            * Move or delete the addressbook.json file.
            * Re-launch the application.
        * Expected:

            * The application starts with an empty data file.
            * A message may be displayed indicating that the data file was not found and a new one has been created.
    2. Test case 2: Simulate a corrupted data file
        * Tasks:
            * Open addressbook.json with a text editor.
            * Introduce invalid JSON syntax (e.g., delete a comma or bracket).
            * Save the file and re-launch the application.
        * Expected:
            * The application starts with an empty data file.
            * A message may be displayed indicating that the data file is corrupted and a new one has been created.
