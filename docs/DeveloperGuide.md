---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# BridalBuddy Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**
This project is evolved from [AddressBook-Level3](https://github.com/se-edu/addressbook-level3).

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

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete_guest 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point).

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of three smaller parts:`CommandBox`, `ResultDisplay` and `PersonListPanel`. All these parts, along with the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-F09-3/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Guest`, `Vendor` objects residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete_guest 1")` API call as an example.

<puml src="diagrams/DeleteGuestSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteGuestCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteGuestCommandParser`) and uses it to parse the command.
   1. Note that `EditVendorCommandParser` and `EditGuestCommandParser` parser classes require the `Model`, to check if the guest or vendor index provided by the user is valid or not. 


1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteGuestCommand`) which is executed by the `LogicManager`.

1. When the `Command` object is executed, it communicates with the `Model` (e.g., to delete a guest). <br>
   In the sequence diagram example above, the `Command` object first calls the `getFilteredGuestList` method, followed by the `deletePerson` method on the `Model` during its execution, illustrating one possible sequence of communication. 

1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddGuestCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddGuestCommand`) which the `AddressBookParser` returns back as a `Command` object.
  * As explained above, some `XYZCommandParser` classes require the `Model` for parsing 
  * Note that for `clear`, `list`, `exit`, `help` and `stats` user commands, the `AddressBookParser` directly returns the `Command` object, and no corresponding `XYZCommandParser` class is created (as there is no extra info to parse) 
  

* All `XYZCommandParser` classes (e.g., `AddGuestCommandParser`, `DeleteGuestCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />

Note: `Vendor` and `Guest` both extend from the abstract `Person` class, which defines common attributes and behaviors shared by both types of entities.

The `Model` component,
* stores the address book data i.e., all `Guest` and `Vendor` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Guest` / `Vendor` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* Can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
  * `Storage` saves `Guest` into JSON format and reads back into `Guest` object, using the `JsonAdapatedGuest` class
  * `Storage` saves `Vendor` into JSON format and reads back into `Vendor` object, using the `JsonAdaptedVendor` class
  

* Inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed)

* Depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Add Guest feature
The `add_guest` command creates and adds a new `Guest` object into the address book. The attributes of the `Guest` are specified through prefixes (n/, p/, e/, a/, rsvp/, r/ and t/) and their corresponding values

<div align="center">
    <img src="diagrams/AddGuestSequenceDiagramP1.puml" alt="AddGuestSequenceDiagram Part 1" />
</div>

<div align="center">
    <img src="diagrams/AddGuestSequenceDiagramP2.puml" alt="AddGuestSequenceDiagram Part 2" />
</div>

<div align="center">
    <img src="diagrams/AddGuestSequenceDiagramP3.puml" alt="AddGuestSequenceDiagram Part 3" />
</div>

<box type="info" seamless>

**Note:** The lifeline for `AddGuestCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

Explanation:
1. The `execute` method of `LogicManager` is called with the user input as the argument to begin the command execution
2. `AddressBookParser` parses the user input initially. If the user input is identified to be an `add_guest` command, it creates and return an `AddGuestCommandParser` for further parsing. 
3. `AddGuestCommandParser` parses the remaining user input (excluding the `add_guest` keyword) to extract the prefixes and their values, which are used to create a `Guest` object with the corresponding attributes
   1. Suppose the n/, p/, e/ and a/ prefixes and their values are provided (these are compulsory)
   2. Then, a `Guest` object with name, phone number, email and address attributes are created 


4. An `AddGuestCommand` is then created with the new `Guest` object and returned.
5. `LogicManager` executes the `AddGuestCommand`, which calls the `hasPerson` method of `Model` to check if the guest already exists in the address book. If the guest is not a duplicate (i.e. not same name and phone number as another guest), the `AddGuestCommand` then calls the `addPerson` method of the `Model` to add the guest into the address book.
6. A `CommandResult` containing the success message is then returned to the `LogicManager` (and then back to the `UI` component)

### Edit Guest feature
The `edit_guest` command updates the details of an existing guest in the address book. Users can specify the guest to be edited by providing the index number (positive, starting from 1) of that guest in the displayed guest list. New guest details are specified through prefixes (n/, p/, e/, a/, rsvp/, r/ and t/) and their corresponding values 

The sequence diagram below provides an overview for the execution flow of a `edit_guest` command:
<puml src="diagrams/EditGuestSequenceDiagramP1.puml" />
<puml src="diagrams/EditGuestSequenceDiagramP2.puml" />
<puml src="diagrams/EditGuestSequenceDiagramP3.puml" />

<box type="info" seamless>

**Note:** The lifeline for `EditGuestCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

Explanation:
1. The `execute` method of `LogicManager` is called with the user input as the argument to begin the command execution
2. `AddressBookParser` parses the user input initially. If the user input is identified to be an `edit_guest` command, it creates and return an `EditGuestCommandParser` for further parsing.
3. `EditGuestCommandParser` parses the remaining user input (excluding the `edit_guest` keyword) to extract the prefixes and their values, which are used to create an `EditGuestDescriptor` object that captures the updated information
4. An `EditGuestCommand` is then created with the guest index provided, as well as the new `EditGuestDescriptor` object
5. `LogicManager` executes the `EditGuestCommand`, which retrieves the guest list from `Model`. The guest index is used to access the target guest to edit. An edited guest with the updated name (from the above example) is then created using the existing target guest and the `EditGuestDescriptor`. The `setPerson` method is then called to replace the existing target guest with the edited guest. Subsequently, the `updateFilteredPersonList` method from `Model` is called to update the filtered list.
6. A `CommandResult` containing the success message is then returned to the `LogicManager` (and then back to the `UI` component)

### Find feature
The `find` command searches for all guests and vendors that match any of the given keyword(s) and displays them. The prefix specified in the command indicates the attribute to be searched. Do note that only one type of prefix should be used for each find command.

The sequence diagram below provides an overview for the execution flow of a `find` command:
<puml src="diagrams/FindSequenceDiagramP1.puml" />
<puml src="diagrams/FindSequenceDiagramP2.puml" />
<puml src="diagrams/FindSequenceDiagramP3.puml" />

<box type="info" seamless>

**Note:** The lifeline for `FindCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

Explanation:
1. The `execute` method of `LogicManager` is called with the user input as the argument to begin the command execution
2. `AddressBookParser` parses the user input initially. If the user input is identified to be an `find` command, it creates and return an `FindCommandParser` for further parsing.
3. `FindCommandParser` parses the remaining user input (excluding the `find` keyword) to extract the prefix and its corresponding value. Then, it calls the corresponding parse predicate method to create the corresponding predicate to be used. In the above example, since the name prefix is specified, the `parseNamePredicate` method is called to create `NameContainsKeywordsPredicate`. 
4. A `FindCommand` is then created with the new `NameContainsKeywordsPredicate` object and returned.
4. `LogicManager` executes the `FindCommand`, which calls the `updateFilteredPersonList` method of the `Model` with the `NameContainsKeywordsPredicate` object as the argument. This method filters the common list of guests and vendors based on the predicate. Guests and vendors whose name (from the above example) matches the given keyword `John` will be kept. Subsequently, the `FindCommand` calls `getFilteredGuestListCount` and `getFilteredVendorListCount` methods from `Model` to respectively obtain the number of remaining guest(s) and vendor(s). 
6. A `CommandResult` containing the success message is then returned to the `LogicManager` (and then back to the `UI` component)


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix A: Requirements**

### Product scope

**Target user profile**:

A wedding planner who:
* Has a need to manage a significant number of contacts
* Has a need to manage different types of contacts (e.g. vendors, guests)
* Favours desktop applications over other alternatives
* Prefers keyboard commands over mouse interactions
* Adequately proficient with command-line applications

**Value proposition**:
* Streamlined, all-in-one wedding planning tool
* Intuitive organisation and customisation
* Stress-free and efficient planning process
* One-stop companion for wedding planners
* Simplifies time-intensive and tedious tasks of managing guest lists, schedules, and vendor contacts
* Enhances efficiency by reducing time spent navigating complex GUIs



### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                     | I want to …​                                                     | So that I can…​                                                                  |
|----------|-----------------------------|------------------------------------------------------------------|----------------------------------------------------------------------------------|
| `* * *`  | forgetful wedding planner   | add a new guest/vendor into the contact list                     | easily track and manage guests/vendors for the wedding                           |
| `* * *`  | organized wedding planner   | view a list of guests/vendors                                    | easily access and reference their details                                        |
| `* * *`  | organized wedding planner   | delete a guest/vendor contact that I no longer need              | keep my contact list organised and clutter-free                                  |
| `* * *`  | meticulous wedding planner  | edit the details of an existing guest/vendor in the contact list | correct mistakes and ensure that all information remains accurate and up-to-date |
| `* *`    | organized wedding planner   | find guests/vendors based on a specific attribute                | efficiently retrieve guest/vendor details when required                          |
| `* *`    | data-driven wedding planner | view wedding statistics (number of guests/vendors/total people)  | quickly assess the current status of wedding planning                            |
| `* *`    | organized wedding planner   | clear all guest/vendor data in the contact list                  | start fresh with a clean slate and easily reset the contact list if needed       |

### Use cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - Add a Guest**

**MSS**

1. User requests to add a new Guest with the required details (e.g. name, email, etc.).
2. System adds the entry.

   Use case ends.

**Extensions**

* 1a. The provided details are incomplete or invalid.
    * 1a1. System shows an error message and requests the user to re-enter the details.

      Use case resumes at step 1.

* 1b. The input command was invalid (i.e. spelling error, etc.).
    * 1b1. System tells the user the command is unrecognised.

      Use case ends.

* 1c. A Guest with the same name and phone number already exists
    * 1c1. System tells the user the Guest already exists

      Use case ends.

**Use case: UC02 - Add a Vendor**

**MSS**

1. User requests to add a new Vendor with the required details (e.g. name, email, etc.).
2. System adds the entry.

   Use case ends.

**Extensions**

* 1a. The provided details are incomplete or invalid.
    * 1a1. System shows an error message and requests the user to re-enter the details.

      Use case resumes at step 1.

* 1b. The input command was invalid (i.e. spelling error, etc.).
    * 1b1. System tells the user the command is unrecognised.

      Use case ends.

* 1c. A Vendor with the same name and phone number already exists
    * 1c1. System tells the user the Vendor already exists

      Use case ends.

**Use Case: UC03 - Update Details of Person**

**MSS**
1. User selects an entry to update together with the fields to be updated.
2. System saves the changes and notifies user of success.

   Use case ends.

**Extensions**
* 1a. The input command was invalid (i.e. spelling error, etc.).
    * 1a1. System tells the user the command is unrecognised.

      Use case ends.

* 1b. The new details are incomplete or invalid.
    * 1b1. System shows an error message and requests the user to re-enter the details.

      Use case resumes at step 1.

* 1c. The new details result in a duplicate Person (i.e. same name, phone number and type)
    * 1c1. System tells the user the Person already exists

      Use case ends.

**Use Case: UC04 - Delete a Guest**

**MSS**
1. User requests to delete a Guest entry.
2. System deletes the Guest entry.

   Use case ends.

**Extensions**
* 1a. The provided Guest entry does not exist.
    * 1a1. System shows an error message.

      Use case resumes at step 1.

* 1b. The input command was invalid (i.e. spelling error, etc.).
    * 1b1. System tells the user the command is unrecognised.

      Use case ends.

**Use Case: UC05 - Delete a Vendor**

**MSS**
1. User requests to delete a Vendor entry.
2. System deletes the Vendor entry.

   Use case ends.

**Extensions**
* 1a. The provided Vendor entry does not exist.
    * 1a1. System shows an error message.

      Use case resumes at step 1.

* 1b. The input command was invalid (i.e. spelling error, etc.).
    * 1b1. System tells the user the command is unrecognised.

      Use case ends.

**Use Case: UC06 - Find Guests and Vendor with a particular field**

**MSS**
1. User requests to find Guest and Vendor entries with a specified field (e.g., by category, name, RSVP status).
2. System displays the matching Guest and Vendor entries.

   Use case ends.

**Extensions**
* 1a. The input command was invalid (i.e. spelling error, etc.).
    * 1a1. System tells the user the command is unrecognised.

      Use case ends.

* 2a. No matching entries are found.
    * 2a1. System shows a message indicating no results.

      Use case ends.

**Use Case: UC07 - Get statistics**

**MSS**
1. User requests to get statistics of entries.
2. System displays the statistics.

   Use case ends.

**Extensions**
* 1a. The input command was invalid (i.e. spelling error, etc.).
    * 1a1. System tells the user the command is unrecognised.

      Use case ends.

### Non-Functional Requirements

**Data Requirements**
1. Size: The system should support storage for up to 300 guests and 300 vendors per wedding.
1. Volatility: Guest lists and vendor details may change frequently, especially closer to the event date. Therefore, the system must accommodate dynamic data updates and edits.
1. Persistency: All guest and vendor information must be saved persistently in a JSON file and remain accessible even after system shutdown or failure.

**Environment Requirements**
1. Operating System: The system must be compatible with Windows, macOS, and Linux operating systems.
1. Dependencies: Java 17 should be the core language.

**Accessibility**
1. Provide help documentation that can be accessed at any time with a simple command (help).

**Fault Tolerance**
1. The system should handle errors such as missing commands or invalid input gracefully, providing clear error messages without causing system crashes.
1. Ensure that invalid input (e.g., incorrect email format) does not result in data corruption.

**Performance Requirements**
1. The system should respond to user input within ten seconds, even for lists of up to 300 guests and 300 vendors.
1. System startup time should not exceed ten seconds on standard hardware.

### Glossary
**Technical**
* **Java**: Java is a programming language that BridalBuddy is written in. It is required to run the application.
*  **CLI**: Command-Line Interface, a text-based interface used to interact with software by typing commands.
*  **GUI**: Graphical User Interface, a user interface that allows users to interact with the app through graphical elements such as buttons, text fields, and menus.
*  **JAR**: Java Archive, A file format used to package Java applications and libraries into a single, compressed file
*  **JSON**: JavaScript Object Notation, a lightweight data-interchange format for structuring and exchanging data

**Non-technical**
*  **Guest**: A person invited to the wedding.
*  **Vendor**: A supplier providing services or goods for a wedding, such as photographers, florists, caterers, etc.
*  **RSVP Status**: The attendance response status of a guest. It can be either accepted, pending, or declined.
*  **Tag**: A keyword or label assigned to an entry to categorize and easily filter it within the list.
*  **Index**: A numerical value representing the position of an entry in a list, used to reference and perform operations on the contact.
*  **Prefix**: Characters preceding details you input on the command line. E.g. n/ for name and e/ for email.

--------------------------------------------------------------------------------------------------------------------

## **Appendix B: Planned Enhancements**

In developing BridalBuddy, we've identified areas that can benefit from enhancement. We believe in transparency and keeping our users informed, so we want to acknowledge these feature limitations.

### Enhance name to accept special characters

**Feature Flaw in Current Implementation**

Currently, names do not accept `.` and `/` and other special characters which might appear in a person's legal name.

**Proposed Enhancement**

We should remove strict alphanumeric checks for names to support special characters.

### Make Guest/Vendor phone, email and address fields optional

**Feature Flaw in Current Implementation**

Currently, the phone, email and address are mandatory inputs for `add_guest` and `add_vendor` commands. However, not all contacts require full details.

A planner might only have a name without contact information for certain guests, especially in cases where a plus-one or placeholder name is added.
Similarly, a planner might also not have complete contact information of a vendor.

**Proposed Enhancement**

The phone, email and address fields should be updated to optional parameters in the `add_guest` and `add_vendor` commands.

### Enhance Flexibility in Phone Number Parameter Input

**Feature Flaw in Current Implementation**

Currently, the phone number field only accepts integers as valid input.
However, planners might encounter scenarios, such as keeping track of overseas guests, where symbols like `()`, `+`, `-` and `.` are needed.
The current restriction prevents users from indicating country codes, and might cause confusion about the origin of the number.

**Proposed Enhancement**

We can relax validation on the phone number field to allow symbols such as `()`, `+`, `-` and `.`.

### Enhance Find Feature to Allow for Partial and Substring Search

**Feature Flaw in Current Implementation**

Currently, the `find` feature requires an exact match or a specific keyword for searching guests or vendors.
This limits the flexibility of the search functionality, as users cannot retrieve entries that partially match the search term.

**Proposed Enhancement**

Enhance the `find` feature to allow partial and substring searches.
With this enhancement, a search query would return all results containing the specified character(s) or substring, regardless of its position in the name or other fields.

--------------------------------------------------------------------------------------------------------------------

## **Appendix C: Instructions for manual testing**

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

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

--------------------------------------------------------------------------------------------------------------------

## **Appendix D: Efforts**

### Adapting AB3 for Multiple Entity Types

**Difficulty Level**: Very high

**Challenges Faced**: AB3 was designed to manage a single entity type—contacts.
Our project, however, required managing two distinct entities, guests and vendors, each with unique fields and functionalities
(e.g., RSVP status for guests, company name for vendors).

**Effort Required**: Modifying the data model to accommodate multiple entities required extensive refactoring of the underlying structure.
This included updates to storage, command parsing, and validation logic, to support both entity types consistently while maintaining functionality for existing commands.
We also spent significant time adjusting current commands to recognize and process both guests and vendors without errors.

**Achievements**: We successfully integrated both entity types while maintaining a clean architecture, enabling BridalBuddy to
manage multiple types of contacts with specific fields and functionalities, all without compromising compatibility with existing commands.

### Introducing Optional and Custom Fields

**Difficulty Level**: Medium

**Challenges Faced**: We added new optional fields, such as budget for vendors, and relationship type for guests.
This required additional validations and command adjustments to handle partial data inputs, ensuring user flexibility.

**Effort Required**: Implementing and validating optional fields involved command parsing, updates to storage,
and ensuring proper input validation (e.g. budgets specified to two decimal places).

**Achievements**: The added flexibility in fields has made BridalBuddy adaptable to a wider variety of planning needs, while the validation ensures high data reliability.

### UI Customization

**Difficulty Level**: High

**Challenges Faced**: Customising the UI to clearly distinguish between guests and vendors required significant modifications to AB3's existing UI structure,
which proved more complex than backend coding. Editing the UI relied heavily on visual adjustments, involving considerable trial and error to achieve the right
layout and style.

**Effort Required**: We restructured the layout to visually separate guest and vendor lists, allowing users to easily differentiate and manage each contact type.

**Achievements**: The customized UI offers a more intuitive experience, allowing users to quickly access and manage information specific to guests and vendors.

### Statistics Display

**Difficulty Level**: Medium

**Challenges Faced**: Implementing the statistics command required adapting AB3’s structure to dynamically calculate and display counts of guests and vendors.

**Effort Required**: We developed a new `stats` command that calculates and returns the total counts of guests and vendors.

**Achievements**: The `stats` command offers users a quick overview of the total number of guests and vendors, providing planners with valuable insights at a glance.
