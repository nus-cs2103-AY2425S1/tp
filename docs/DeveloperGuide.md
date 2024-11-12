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

VendorVault is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org/).

Icons used in this project are from [UXWing](https://www.uxwing.com/). All icons on this site can be used in personal, commercial, and client projects.

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

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `mark -s 1 active`.

<puml src="diagrams/ArchitectureSequenceDiagramMarkSupplier.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `SupplierListPanel`, `StatusBarFooter`, `DeliveryListPanel` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Supplier`  and `Delivery` object(s) residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete -s 1")` API call as an example.

<puml src="diagrams/DeleteSupplierSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete -s 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteSupplierCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteSupplierCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteSupplierCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a supplier).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `DeleteSupplierCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `DeleteSupplierCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddSupplierCommandParser`, `DeleteSupplierCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="500" />


The `Model` component,

* stores the address book data i.e., all `Supplier` objects (which are contained in a `UniqueSupplierList` object).
* stores the Supplier objects in 2 separate lists, filteredSuppliers and sortedSuppliers. These lists hold the 
filtered supplier list and sorted supplier list respectively, and the displayed list will be determined based on the 
last executed command. Deliveries are also stored and displayed in a similar manner.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="650" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

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

* has a need to manage a significant number of contacts
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**:
Manage and track deliveries faster than using a typical mouse/GUI driven app.
Plan and allocate manpower more effectively to prepare for future deliveries.
Match with the appropriate suppliers to find products correctly.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                | I want to …​                                      | So that …​                                                                                                 |
|----------|------------------------|---------------------------------------------------|------------------------------------------------------------------------------------------------------------|
| `* * *`  | first time shop owner  | learn how to use the application                  | I can better use the application to store and manage my supplier contacts and deliveries                   |
| `* * *`  | shop owner             | view all my supplier contacts                     |                                                                                                            |
| `* * *`  | shop owner             | add supplier contact                              |                                                                                                            |
| `* * *`  | shop owner             | delete supplier contact                           | I can remove suppliers from the past who are no longer of interest                                         |
| `* * *`  | shop owner             | have custom tags for my suppliers                 | I can keep track of special details about my suppliers                                                     |
| `* * *`  | shop owner             | have products linked to my suppliers              | I can keep track of products that are sold by my suppliers                                                 |
| `* * *`  | shop owner             | mark suppliers with active status                 | I can view which suppliers are currently active/inactive                                                   |
| `* * *`  | shop owner             | view all my deliveries                            |                                                                                                            |
| `* * *`  | shop owner             | add delivery information                          | I can track of the upcoming deliveries                                                                     |
| `* * *`  | shop owner             | delete delivery information                       | I can delete deliveries that are no longer happening                                                       |
| `* * *`  | shop owner             | mark delivery with completion status              | I know if a delivery has been delivered/pending/cancelled                                                  |
| `* *`    | shop owner             | find supplier contacts by name                    | I can find the contact information of the supplier I am looking for through names                          |
| `* *`    | shop owner             | find supplier contacts by product                 | I can find the contact information of the supplier I am looking for based on the product they supply       |
| `* *`    | shop owner             | find supplier contacts by company name            | I can find the contact information of the supplier I am looking for based on the company they are from     |
| `* *`    | shop owner             | sort list of suppliers by name                    | I can view the suppliers in ascending/descending order by name                                             |
| `* *`    | shop owner             | find deliveries by date                           | I can find the deliveries happening on a certain date                                                      |
| `* *`    | shop owner             | find deliveries by supplier                       | I can find the deliveries associated with a supplier                                                       |
| `* *`    | shop owner             | find deliveries by supplier status                | I can find the deliveries that are pending or have been delivered or cancelled                             |
| `* *`    | shop owner             | sort list of deliveries by delivery cost          | I can quickly identify high-cost or low-cost deliveries and manage my expenses more effectively            |
| `* *`    | shop owner             | sort list of deliveries by delivery date and time | I can easily find upcoming deliveries and focus on the urgent deliveries first                             |
| `* *`    | shop owner             | sort list of deliveries by delivery status        | I can maintain a clear overview of which deliveries have been delivered, cancelled or pending              |
| `* *`    | shop owner             | view deliveries that are upcoming                 | I can view the deliveries that are within a certain time period                                            |
| `* `     | shop owner             | edit supplier contact                             | I can easily alter contact information in the address book when suppliers change their contact information |
| `* `     | shop owner             | edit delivery information                         | I can edit upcoming deliveries without changing other fields                                               |
| `*`      | shop owner             | view list of products                             | I know what products I am currently selling                                                                |

### Use cases

**System**: Vendor Vault (VV)

**Use Case**: UC01 - Add Supplier Information

**Actor**: Shop Owner

**Main Success Scenario (MSS)**:
1. User requests to add a new supplier
2. VV successfully adds the supplier and displays the updated list of all suppliers.  
   Use case ends.

**Extensions**:
- **1a.** VV detects missing or incorrectly formatted data.
    - **1a1.** VV displays an appropriate error message.  
      Use case ends.

- **1b.** User enters duplicate supplier information.
    - **1b1.** VV displays an error message.  
      Use case ends.


___
**System**: Vendor Vault (VV)

**Use Case**: UC02 - Delete Supplier

**Actor**: Shop owner

**Main Success Scenario (MSS)**:
1. User requests to delete a specific supplier.
2. VV deletes the supplier and displays the updated list of all suppliers.  
   Use case ends.

**Extensions**:
- **1a.** VV detects that the supplier is missing or invalid.

    - **1a1.** VV displays an error message.  
      Use case ends.

___
**System**: Vendor Vault (VV)

**Use Case**: UC03 - Mark Supplier Status

**Actor**: Shop owner

**Main Success Scenario (MSS)**:
1. User requests to mark a specific supplier with a specific status.
2. VV updates the supplier's status and displays the updated list of all suppliers.  
   Use case ends.

**Extensions**:
- **1a.** VV detects an invalid command format.
    - **1a1.** VV displays an error message.  
      Use case ends.

- **1b.** VV detects that one or more parameters are missing or invalid.
    - **1b1.** VV displays an error message.  
      Use case ends.

- **1c.** VV detects that the current status of the specified supplier is the same as the requested status.
    - **1c1.** VV displays an error message.
      Use case ends.

___
**System**: Vendor Vault (VV)

**Use Case**: UC04 - Find Supplier by given parameters

**Actor**: Shop owner

**Main Success Scenario (MSS)**:
1. User requests to find suppliers by keyword in one or more parameters.
2. VV updates the supplier's list and displays the updated list of all suppliers that contain given keywords in all their respective parameters .  
   Use case ends.

**Extensions**:
- **1a.** VV detects an invalid command format.
    - **1a1.** VV displays an error message.  
      Use case ends.

- **1b.** VV detects that the keyword is missing.
    - **1b1.** VV displays an error message.  
      Use case ends.

___
**System**: Vendor Vault (VV)

**Use Case**: UC05 - Sort Suppliers

**Actor**: Shop owner

**Main Success Scenario (MSS)**:
1. User requests to sort suppliers by a specified field in a specified order.
2. VV sorts the suppliers and displays the sorted list of suppliers.  
   Use case ends.

   **Extensions**:
- **1a.** VV detects an invalid command format.
    - **1a1.** VV displays an error message.  
      Use case ends.

- **1b.** VV detects that one or more parameters is missing or invalid.
    - **1b1.** VV displays an error message.  
      Use case ends.

___
**System**: Vendor Vault (VV)

**Use Case**: UC06 - Add Deliveries

**Actor**: Shop owner

**Main Success Scenario (MSS)**:
1. User request to add a new delivery
2. VV adds the delivery entry to the list and displays the updated list of deliveries.  
   Use case ends.

**Extensions**:
- **1a.** VV detects an invalid command format.
    - **1a1.** VV displays an error message.  
      Use case ends.

- **1b.** VV detects that one or more parameters are missing or invalid.
    - **1b1.** VV displays an error message.  
      Use case ends.

- **1c.** VV detects a duplicate delivery.
    - **1c1.** VV displays an error message.  
      Use case ends.

___
**System**: Vendor Vault (VV)

**Use Case**: UC07 - Delete Delivery

**Actor**: Shop owner

**Main Success Scenario (MSS)**:
1. User requests to delete a delivery.
2. VV removes the delivery entry from the list and displays the updated list of all deliveries.
   Use case ends.

**Extensions**:
- **1a.** VV detects that the delivery index is missing or invalid.
    - **1a1.** VV displays an error message.  
      Use case ends.


- **1b.** VV detects invalid formatting.
    - **1b1.** VV displays an error message.  
      Use case ends.
___
**System**: Vendor Vault (VV)

**Use Case**: UC08 - Mark the Status of Deliveries

**Actor**: Shop Owner

**Main Success Scenario (MSS)**:
1. User requests to set the status of a delivery
2. VV displays a confirmation message and then shows an updated list of all deliveries.  
   Use case ends.

**Extensions**:
- **1a.** VV detects an invalid command format.
    - **1a1.** VV displays an error message.  
      Use case ends.

- **1b.** VV detects that one or more parameters are missing or invalid.
    - **1b1.** VV displays an error message.  
      Use case ends.

- **1c.** VV detects that the current status of the specified delivery is the same as the requested status.
    - **1c1.** VV displays an error message.
      Use case ends.


**System**: Vendor Vault (VV)

**Use Case**: UC09 - Find Deliveries by given parameters

**Actor**: Shop owner

**Main Success Scenario (MSS)**:
1. User requests to find deliveries by keyword in one or more parameters.
2. VV updates the delivery list and displays the updated list of all deliveries that contain given keywords in all their respective parameters .  
   Use case ends.

**Extensions**:
- **1a.** VV detects an invalid command format.
    - **1a1.** VV displays an error message.  
      Use case ends.

- **1b.** VV detects that the keyword is missing.
    - **1b1.** VV displays an error message.  
      Use case ends.

___
**System**: Vendor Vault (VV)

**Use Case**: UC10 - Sort Deliveries

**Actor**: Shop owner

**Main Success Scenario (MSS)**:
1. User requests to sort deliveries by a specified field in a specified order.
2. VV sorts the deliveries and displays the sorted list of deliveries.  
   Use case ends.

**Extensions**:
- **1a.** VV detects an invalid command format.
    - **1a1.** VV displays an error message.  
      Use case ends.

- **1b.** VV detects that one or more parameters is missing or invalid.
    - **1b1.** VV displays an error message.  
      Use case ends.

___
**System**: Vendor Vault (VV)

**Use Case**: UC10 - UpcomingDeliveries

**Actor**: Shop owner

**Main Success Scenario (MSS)**:
1. User requests to find all deliveries with a certain time frame
2. VV filters the deliveries and displays the filtered list of deliveries within the given time frame.  
   Use case ends.

**Extensions**:
- **1a.** VV detects an invalid command format.
    - **1a1.** VV displays an error message.  
      Use case ends.

- **1b.** VV detects that one or more parameters is missing or invalid.
    - **1b1.** VV displays an error message.  
      Use case ends.





### Non-Functional Requirements

#### Performance Requirements
1. The system should respond to user input within 2 seconds for all CRUD operations on contacts and deliveries (e.g., adding, deleting, searching).
2. The application should take no longer than 5 seconds to launch and load all necessary data (e.g., contacts, deliveries) on any supported platform.

#### Scalability Requirements
3. The system should be able to handle at least 100 contacts and 200 deliveries without noticeable degradation in performance.

#### Usability Requirements
4. A user with typing speed of more than 50 words per minute for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.

#### Compatibility / Portability Requirements
5. Should work on any mainstream OS as long as it has Java 17 or above installed without requiring platform-specific dependencies.
6. The product should work as a standalone JAR file, not exceeding 100MB in size without needing an installer.

#### Data Requirements
7. The system should not use any Database Management System (DBMS) for data storage. The contacts and deliveries data should be stored locally in a human-editable file.
8. The system should ensure that the data file remains consistent and free from corruption across system crashes or improper shutdowns.

#### Security Requirements
9. The system should ensure that user data (e.g., supplier contacts, delivery information) is only accessible by the user of the local machine.

#### Maintainability Requirements
10. The system should follow Object-Oriented Programming (OOP) principles to facilitate future maintenance and feature additions.
11. The project should be developed in a breadth-first incremental manner, with consistent delivery of features over the course of the development cycle.

#### Testability Requirements
12. The system should be designed to support unit and integration testing, with testable modules and clearly defined boundaries.
13. The application should not depend on any external remote servers for its core functionality, ensuring that the product can be tested and used offline without network dependencies.

*{More to be added}*

### Glossary

* **Breadth-first Incremental Manner**: An approach to software development where features are implemented across the entire scope of the project in shallow layers, gradually adding depth and complexity over time. This method ensures that all major components of the system are developed in parallel, allowing for early integration and testing of the overall system structure.
* **CLI (Command-Line Interface)**: A text-based interface where users interact with the system by typing commands, as opposed to using a graphical interface with mouse clicks.
* **CRUD Operations**: Refers to Create, Read, Update, and Delete operations. In this system, CRUD applies to managing supplier contacts and delivery information.
* **DBMS (Database Management System)**: A software system that enables users to define, create, maintain and control access to a database. It provides an organised way of managing, storing, and retrieving vast amounts of data.
* **Delivery Information**: Details about a specific delivery, including date, supplier, products, and status.
* **Human-Editable File**: A plain text file that can be easily opened, read, and modified by users, typically in formats such as JSON, CSV, or TXT, without needing specialised software.
* **JAR File**: A Java ARchive file, which is a package file format that aggregates many Java class files and associated resources (text, images, etc.) into one file for distribution.
* **Mainstream OS**: Windows, Linux, Unix, macOS.
* **OOP (Object-Oriented Programming)**: A programming paradigm based on the concept of objects, which can contain data and methods.
* **Supplier Contact**: A record containing information about a supplier, including name, contact details, and associated products.
* **Unit and Integration Testing**:
  * **Unit Testing**: A software testing method where individual units or components of the software are tested in isolation to ensure they work correctly. 
  * **Integration Testing**: A phase of software testing where individual software modules are combined and tested as a group to verify that they work correctly together.

  
*{More to be added}*

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

### Testing Supplier Commands 

#### Adding a supplier

1. Adding a supplier 

   1. Prerequisites: No "John Doe" with company "companyA" and "Jane Doe" with company "companyB" in the list of suppliers.

   1. Test case: `add -s n/John Doe p/98765432 e/johnd@example.com com/companyA t/friends t/owesMoney pro/rice pro/bread`<br>
     Expected: A new supplier is added to the list.

   2. Test case: `add -s n/Jane Doe p/87654321 e/jane@example.com com/companyB`<br>
     Expected: A new supplier is added to the list.
   
   3. Test case: `add -s n/John Doe p/98765432`<br> 
     Expected: Error message is shown. Supplier not added.
      
**Tips:** Check out the User Guide (UG) for more information on what it means to have duplicate suppliers.

### Finding a supplier

1. Finding a supplier by name

   1. Prerequisites: At least one supplier with the name "John" in the list.

   2. Test case: `find -s n/John`<br>
      Expected: The suppliers with name containing "John" is shown in the list. Other suppliers are not shown.

   3. Test case: `find -s n/John com/comp`<br>
      Expected: The suppliers with name containing "John" and company name containing "comp" is shown in the list. Other suppliers are not shown.

   4. Test case: `find -s n/John com/comp pro/rice`<br>
      Expected: The suppliers with name containing "John", company name containing "comp" and product containing "rice" is shown in the list. Other suppliers are not shown.
   
   6. Test case: `find -s n/John com/comp pro/rice pro/bread`<br>
      Expected: The suppliers with name containing "John", company name containing "comp" and product containing "rice" and "bread" is shown in the list. Other suppliers are not shown.

### Deleting a supplier

1. Deleting a supplier while all suppliers are being shown

   1. Prerequisites: List all suppliers using the `list -s` command. Multiple suppliers in the list.

   1. Test case: `delete 1`<br>
      Expected: First supplier is deleted from the list. Details of the deleted contact shown in the status message.

   1. Test case: `delete 0`<br>
      Expected: No supplier is deleted. Error details shown in the status message.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Sorting suppliers

1. Sorting suppliers by name

   1. Prerequisites: List all suppliers using the `list -s` command. Multiple suppliers in the list.

   1. Test case: `sort -s so/a sb/n`<br>
      Expected: Suppliers are sorted by name in ascending order.

   1. Test case: `sort -s so/d sb/n`<br>
      Expected: Suppliers are sorted by name in descending order.

   1. Test case: `sort -s sb/n`<br>
      Expected: Error message is shown. Suppliers are not sorted.

### Listing suppliers

1. Listing all suppliers

   1. Prerequisites: At least one supplier in the list.

   1. Test case: `list -s`<br>
      Expected: All suppliers are shown in the list.

### Mark supplier status

1. Marking a supplier as active/inactive

   1. Prerequisites: List all suppliers using the `list -s` command. Multiple suppliers in the list.

   1. Test case: `mark -s 1 active`<br>
      Expected: The status of the first supplier is changed to active.

   1. Test case: `mark -s 1 inactive`<br>
      Expected: The status of the first supplier is changed to inactive.

   1. Test case: `mark -s 0 active`<br>
      Expected: Error message is shown. Status of suppliers is not changed.

2. Marking an inactive supplier as inactive

   1. Prerequisites: Ensure that the first supplier is inactive by listing all suppliers using the `list -s` command.
   
    1. Test case: `mark -s 1 inactive`<br>
        Expected: The first supplier remains inactive. Error message showing that supplier already has INACTIVE as status.

### Testing Delivery Commands

### Adding a delivery

1. Adding a delivery with existing supplier

    1. Prerequisites: Ensure there is at least 1 supplier by listing all suppliers using the `list -s` command.

    1. Test case: `add -d on/18-01-2023 15:00 s/1 pro/bread q/500 g c/5.50`<br>
       Expected: A delivery paired to the first supplier is added. Details of the delivery shown in the status message.

2. Adding a delivery with invalid supplier
    1. Test case: `add -d on/18-01-2023 15:00 s/0 pro/bread q/500 g c/5.50`<br>
       Expected: No delivery is added. Error message indicating that SUPPLIER_INDEX should be a positive number greater than 0 and smaller than total number of suppliers shown in the status message.<br> </br>

3. Adding a delivery with invalid units for QUANTITY
    1. Prerequisites: Ensure there is at least 1 supplier by listing all suppliers using the `list -s` command.

    1. Test case: `add -d on/18-01-2023 15:00 s/1 pro/bread q/500 pounds c/105.50`<br>
       Expected: No delivery is added. Error message indicating that QUANTITY should be a positive number followed by a space and valid unit shown in the status message.

4. Adding a delivery with invalid parameter values
    1. Prerequisites: Ensure there is at least 1 supplier by listing all suppliers using the `list -s` command.

    1. Test case: `add -d on/18-01-2023 15:00 s/1 pro/###@@ q/500 pounds c/105.50`<br>
       Expected: No delivery is added. Error message indicating that PRODUCT should be alphanumeric shown in the status message.

### Listing all deliveries

1. Listing all deliveries

    1. Test case: `list -d`<br>
       Expected: All added deliveries shown. Success message shown in the status message.

### Marking a delivery

1. Marking a delivery as CANCELLED

    1. Prerequisites: Ensure that the first delivery has either PENDING or DELIVERED status by listing all deliveries using the `list -d` command.

    1. Test case: `mark -d 1 CANCELLED`<br>
       Expected: The first delivery has status changed to CANCELLED. Details of the delivery shown in the status message.

2. Marking a delivery as DELIVERED

    1. Prerequisites: Ensure that the first delivery has either PENDING or CANCELLED status by listing all deliveries using the `list -d` command.

    1. Test case: `mark -d 1 DELIVERED`<br>
       Expected: The first delivery has status changed to DELIVERED. Details of the delivery shown in the status message.

3. Marking a delivery as PENDING

    1. Prerequisites: Ensure that the first delivery has either CANCELLED or DELIVERED status by listing all deliveries using the `list -d` command.

    1. Test case: `mark -d 1 PENDING`<br>
       Expected: The first delivery has status changed to PENDING. Details of the delivery shown in the status message.

4. Marking a PENDING delivery as PENDING

    1. Prerequisites: Ensure that the first delivery has PENDING status by listing all deliveries using the `list -d` command.

    1. Test case: `mark -d 1 PENDING`<br>
       Expected: The first delivery remains unchanged. Error message showing that delivery already has PENDING as status.

5. Other incorrect mark commands to try: mark -d, mark -d x, ... (where x is larger or smaller than the list size)
   Expected: Delivery remains unchanged and error message is shown in the status message.

### Deleting a delivery

1. Deleting an existing delivery

    1. Prerequisites: Ensure there is at least 1 delivery by listing all deliveries using the `list -d` command.

    1. Test case: `delete -d 1`<br>
       Expected: The first delivery is deleted. Details of the deleted delivery shown in the status message.

2. Deleting a non-existent delivery

    1. Prerequisites: Ensure there is no delivery by listing all deliveries using the `list -d` command.

    1. Test case: `delete -d 1`<br>
       Expected: No delivery is deleted. An error message that states that the delivery index provided is invalid is shown in the status message.

3. Other incorrect delete commands to try: delete -d, delete -d x, ... (where x is larger than the delivery list size)
   Expected: No delivery is deleted and error message is shown in the status message.

### Finding a delivery

1. Finding an existing delivery by status

    1. Prerequisites: Ensure there is at least 1 delivery with status PENDING by listing all deliveries using the `list -d` command.

    1. Test case: `find -d stat/PENDING`<br>
       Expected: All deliveries with status PENDING are displayed. Success message indicating number of deliveries listed shown in the status message.

2. Finding an existing delivery by PRODUCT

    1. Prerequisites: Ensure there is at least 1 delivery with rice as product by listing all deliveries using the `list -d` command.

    1. Test case: `find -d pro/rice`<br>
       Expected: All deliveries with rice as product are displayed. Success message indicating number of deliveries listed shown in the status message.

3. Finding a delivery that does not exist

    1. Prerequisites: Ensure there are no deliveries with 20-10-1999 12:00 as DELIVERY_DATE_TIME by listing all deliveries using the `list -d` command.

    1. Test case: `find -d on/20-10-1999 12:00`<br>
       Expected: No deliveries are displayed. Message indicating 0 deliveries listed shown in the status message.

4. Other incorrect find commands to try: find -d, find -d pro/@@## ...
   Expected: No delivery is displayed and error message is shown in the status message.

### Sorting deliveries

1. Sorting deliveries with valid Parameter in ascending order

    1. Prerequisites: Ensure there is at least 1 delivery with DELIVERED status and 1 delivery with PENDING status by listing all deliveries using the `list -d` command.

    1. Test case: `sort -d so/a sb/s`<br>
       Expected: All deliveries with status Delivered are displayed before deliveries with status PENDING. Success message indicating number of deliveries sorted and the sorting conditions shown in the status message.

2. Sorting deliveries with invalid parameters in ascending order

    1. Test case: `sort -d so/a sb/q`<br>
       Expected: No deliveries are displayed. Error message indicating possible parameters for sort order shown in the status message.<br></br>

3. Sorting an empty list of deliveries

    1. Prerequisites: Ensure there are no deliveries displayed by listing all deliveries using the `list -d` command.

    1. Test case: `sort -d so/a sb/c`<br>
       Expected: No deliveries are displayed. Message indicating 0 deliveries sorted by cost in ascending order is shown in the status message.

4. Other incorrect sort -d commands to try: sort -d, sort -d so/a ...
   Expected: No delivery is displayed and invalid command error message is shown in the status message.

### Upcoming deliveries

1. View upcoming deliveries that are within a specified date range.

    1. Prerequisites: Ensure there is at least 1 delivery with PENDING status and between 20-10-1999 12:00 and 12-10-2024 13:00 by listing all deliveries using the `list -d` command.

    1. Test case: `upcoming aft/20-10-1999 12:00 bef/12-10-2024 13:00`<br>
       Expected: All PENDING deliveries between 20-10-1999 12:00 and 12-10-2024 13:00 are displayed. Success message indicating number of upcoming deliveries shown in the status message.

2. View upcoming deliveries before a specified date.

    1. Prerequisites: Ensure there is at least 1 delivery with PENDING status and before d 12-10-2024 12:00 by listing all deliveries using the `list -d` command.

    1. Test case: `upcoming bef/12-10-2024 12:00`<br>
       Expected: All Pending deliveries before 12-10-2024 12:00 are displayed. Success message indicating number of upcoming deliveries shown in the status message.

3. Invalid parameter values.

    1. Prerequisites: Ensure there is at least 1 delivery displayed by listing all deliveries using the `list -d` command.

    1. Test case: `upcoming aft/20-10-101010`<br>
       Expected: No deliveries is displayed. Message indicating DELIVERY_DATE_TIME should be in the format dd-MM-yyyy HH:mm is shown in the status message.

4. Other incorrect upcoming commands to try: upcoming, upcoming aft/
   Expected: No delivery is displayed and error message is shown in the status message.

### Saving data

1. Dealing with missing/corrupted data files
   1. Prerequisites: Delete the data file.
   2. Test case: Launch the app<br>
      Expected: The app should create a new data file with default data.

### Exiting the app

1. Test case: `exit`<br>
       Expected: The app closes.

## **Appendix: Effort**

### Difficulty Level
VendorVault is significantly more complex than Address Book 3 (AB3). While AB3 deals with a single entity (Person), 
VendorVault deals with two entities (Supplier and Delivery), thus more effort is required to manage and integrate the two entities seamlessly. 
Apart from introducing new commands, the UI was also improved to support displaying both entities in a user-friendly manner.

### Challenges Faced

#### New Commands
New commands, such as sorting, had to be integrated with existing commands to ensure the expected and accurate behaviour was achieved.

#### Data Storage
JSON storage architecture had to be updated to accommodate both suppliers and deliveries, while ensuring format consistency and ease of retrieval.

#### UI Design
Designing and implementing an intuitive user interface that displays all required information in a non-cluttered way took considerable time. 
Ensuring a smooth user experience for all user actions required additional logic to ensure commands work seamlessly together.

### Effort Required
VendorVault is estimated to have taken 1.5x the expected effort. This was due to:
- Improving existing commands and implementing new commands from scratch.
- Providing comprehensive test coverage across all commands.
- Performing extensive manual testing to ensure a smooth user experience and cohesive user interface.

### Achievements
The team managed to successfully implement various essential and helpful commands in VendorVault to meet the needs of small convenience/grocery stores. 
The wide variety of commands and intuitive user interface serves to benefit the target users greatly.

## **Appendix: Planned Enhancements**

Team size: 5

1. **Enhancement 1**: Make deletion of suppliers with pending deliveries throw a warning and error message.
   * **Description**: The current implementation allows users to delete suppliers with pending deliveries without any warning. 
   * **Tasks**:
     * We plan to add a warning message when a user tries to delete a supplier with pending deliveries.
     * e.g. "Warning: Unable to delete supplier. There are pending deliveries associated with this supplier."

<br></br>
2. **Enhancement 2**: Make marking an active supplier with pending deliveries inactive throw a warning and error message.
   * **Description**: The current implementation allows users to mark active suppliers with pending deliveries as inactive without any warning.
   * **Tasks**:
     * We plan to add a warning message when a user tries to mark an active supplier with pending deliveries as inactive.
     * e.g. "Warning: Unable to mark supplier as inactive. There are pending deliveries associated with this supplier."

<br></br>
3. **Enhancement 3**: Make improvements to detect duplicate suppliers. 
   * **Description**: The current implementation defines duplicate suppliers as suppliers with the same name and company name.
   However, it allows users to add duplicate suppliers with names with different case (e.g. "John Doe" and "john doe")
   and multiple spacings (e.g. "John Doe" and "John   Doe").
   * **Tasks**:
     * We plan to redefine duplicate suppliers as suppliers with the same name and phone number. 
     For the supplier name, we plan to make improvements to detect duplicate suppliers case-insensitively.
     Supplier names with multiple spacings will also be considered as the same supplier.
     Under this new implementation, suppliers are not allowed to have the same name and phone number. 
     * e.g. "Warning: Duplicate supplier 'John Doe' already exists. Please use a different name."
     * e.g. "Warning: Duplicate supplier with the same phone number '98765432' already exists. Please use a different phone number."

<br></br>
4. **Enhancement 4**: Make adding a delivery of a product that is not supplied by the supplier throw a warning and error message.
   * **Description**: The current implementation allows users to add a delivery of a product that is not supplied by the supplier without any warning.
   * **Tasks**:
     * We plan to add a warning message when a user tries to add a delivery of a product that is not supplied by the supplier.
     * e.g. "Warning: Unable to add delivery. The product is not supplied by the selected supplier."

<br></br>
5. **Enhancement 5**: Make adding supplier tags check for duplicates case-insensitively and throw a warning message.
   * **Description**: The current implementation allows users to add duplicate tags with different case (e.g. "friends" and "Friends").
   * **Tasks**:
     * We plan to check for duplicate tags case-insensitively and throw a warning message.
     * e.g. "Warning: Duplicate tag 'friends' already exists. Please use a different tag."

<br></br>
6. **Enhancement 6**: Make improvements to detect duplicate deliveries. 
   * **Description**: The current implementation defines duplicate deliveries as deliveries
   with the same supplier, product, delivery date and time, cost, and quantity.
   However, it allows users to add duplicate deliveries with different case for the product name (e.g. "bread" and "Bread").
   * **Tasks**:
     * We plan to check for duplicate deliveries case-insensitively for the product name and throw a warning message.
     * "Warning: Duplicate supplier detected. Please use a different name/phone number."

<br></br>
7. **Enhancement 7**: Make upcoming command throw an error when the date and time input for the `aft/DELIVERY_DATE_TIME` parameter is later than the date and time input for parameter `bef/DELIVERY_DATE_TIME`.
   * **Description**: The current implementation allows users to key in an invalid date range for PENDING deliveries for the upcoming command without any warning.
   * **Tasks**:
     * We plan to add an error message when a user tries to use the upcoming command with an invalid date range where the `bef/DELIVERY_DATE_TIME` parameter has an earlier date and time than the `aft/DELIVERY_DATE_TIME` parameter.
     * e.g. "Warning: `The DELIVERY_DATE_TIME` value for the `aft/ DELIVERY_DATE_TIME` parameter should be earlier than the value for `bef/DELIVERY_DATE_TIME`."

<br></br>
8. **Enhancement 8**: Allow for adding a supplier name with special characters.
   * **Description**: The current implementation only allows for alphanumeric characters in the supplier name.
   * **Tasks**:
     * We plan to allow for adding a supplier name with special characters.
     * e.g. "add -s n/John Doe & Sons p/98765432
     * e.g. "add -s n/John Doe s/o connor p/98765432

<br></br>
9. **Enhancement 9**: Better input checks for invalid date in commands that require date input.
   * **Description**: The current implementation allows for certain invalid dates
    because the LocalDateTime module automatically converts the date to the first previous valid date
   * e.g. "add -d on/31-04-2023 15:00 s/1 pro/bread q/500 g c/5.50" (For months with less than 31 days,
   the date is converted to the last day of the previous month)
   * e.g. "add -d on/29-02-2023 15:00 s/1 pro/bread q/500 g c/5.50" (For non-leap years, the date is converted to 28-02-2023)
   * No error message is shown.
   * **Tasks**:
     * We plan to add better input checks for invalid dates in commands that require date input. <br>

<br></br>
10. **Enhancement 10**: Allow finding deliveries by date and time range.
   * **Description**: The current implementation only allows finding deliveries by a single date and time.
   * **Tasks**:
     * We plan to allow users to find deliveries within a specified date and time range.
     * e.g. "find -d aft/18-01-2023 12:00 bef/18-01-2023 18:00 stat/DELIVERED"
