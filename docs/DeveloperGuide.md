---
layout: default.md
title: "Developer Guide"
pageNav: 3
---

# EduContacts Developer Guide

<!-- * Table of Contents -->

1. [Acknowledgements](#acknowledgements)
2. [Setting up, getting started](#setting-up-getting-started)
3. [Design](#design)
    - [Architecture](#architecture)
    - [UI Component](#ui-component)
    - [Logic Component](#logic-component)
    - [Model Component](#model-component)
    - [Storage Component](#storage-component)
    - [Common Classes](#common-classes)
4. [Implementation](#implementation)
    - [Module Feature](#module-feature)
    - [Grade Feature](#grade-feature)
5. [Documentation, logging, testing, configuration, dev-ops](#documentation-logging-testing-configuration-dev-ops)
6. [Appendix](#appendix)
    - [Requirements](#appendix-requirements)
        - [Product Scope](#product-scope)
        - [User Stories](#user-stories)
        - [Use Cases](#use-cases)
        - [Non-Functional Requirements](#non-functional-requirements)
        - [Glossary](#glossary)
    - [Instructions for Manual Testing](#appendix-instructions-for-manual-testing)
        - [Launch and Shutdown](#launch-and-shutdown)
        - [Commands Testing](#commands-testing)
        - [Saving Data](#saving-data)
    - [Effort](#appendix-effort)
        - [Achievements](#achievements)
        - [Effort Required](#effort-required)
        - [Challenges Faced](#challenges-faced)
        - [Difficulty Level](#difficulty-level)
    - [Planned Enhancements](#appendix-planned-enhancements)
        - [1. Overly General Error Message for Duplicate Command Word](#1-overly-general-error-message-for-duplicate-command-word)
        - [2. Overly General Error Message for Duplicate IDs in Commands](#2-overly-general-error-message-for-duplicate-ids-in-commands)
        - [3. Address `help` Command Popup Window Stability Issue](#3-address-help-command-popup-window-stability-issue)
        - [4. StudentID as Duplicate Detection Mechanism](#4-studentid-as-duplicate-detection-mechanism)
        - [5. Support for Multiple Filter Conditions](#5-support-for-multiple-filter-conditions)
        - [6. Toggle for Partial vs Full Matching in Filter Command](#6-toggle-for-partial-vs-full-matching-in-filter-command)
        - [7. Support for Additional and Customizable Roles](#7-support-for-additional-and-customizable-roles)
        - [8. Support for Contacts Being Tagged with Multiple Roles](#8-support-for-contacts-being-tagged-with-multiple-roles)
        - [9. Enhanced Module Utility](#9-enhanced-module-utility)
        - [10. Module Not Applicable for Certain Contacts](#10-module-not-applicable-for-certain-contacts)
    - [New Features](#appendix-new-features)
        - [Undo Command](#undo-command)
        - [Data Archiving](#data-archiving)
        - [Importing Contact Data](#importing-contact-data)
        - [Exporting Contact Data](#exporting-contact-data)

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org) ([UG](https://se-education.org/addressbook-level3/UserGuide.html), [DG](https://se-education.org/addressbook-level3/DeveloperGuide.html), [GitHub Page](https://github.com/se-edu/addressbook-level3)).

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

<div style="page-break-after: always;"></div>

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

<div style="page-break-after: always;"></div>


**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 12345678`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

<div style="page-break-after: always;"></div>


### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI is managed by the `UiManager` class, which serves as the main controller for managing the UI in EduContacts.
It serves as the interface layer between the application's backend logic and the JavaFX UI components, ensuring a smooth
and consistent user experience.

The UI consists of a `MainWindow` that is made up of the following parts:
* `CommandBox`
    * Where the user types in his desired command
    * Integrates with [`CommandHistory`](#commandhistory-integration) to provide an efficient command-tracking mechanism, allowing users to navigate
      through previously entered commands using the `UP` and `DOWN` arrow keys.
* `ResultDisplay`
    * Where the resulting confirmation of the latest command sent or any corresponding error messages is shown to the user
* `PersonListPanel`
    * The panel which holds the list of persons in EduContacts, each person represented by a `PersonCard`
* `StatusBarFooter`
    * Designed to show the save location of EduContacts' data
* `PersonDetails`
    * A section of the UI that renders when a `FindCommand` is run, showing the resulting person's full details
* `PersonCard`
    * Shows simple and brief details about a person
* `HelpWindow`
    * Displayed by clicking the "Help" button at the top right hand of the screen

All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between
classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that
are in the `src/main/resources/view` folder. For example, the layout of the
[`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java)
is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

**The `UI` component,**

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

#### `CommandHistory` Integration

The `CommandHistory` class, located in `seedu.address.ui.util`, is responsible for tracking user-entered commands.
It enhances the `CommandBox` functionality by allowing users to navigate through their command history with the
`UP` and `DOWN` arrow keys. This design keeps the command history encapsulated and separate from other UI components,
promoting modularity and adhering to good OOP practices.

The stylesheet used for the UI can be found in `src/main/java/resources/view/LightTheme.css`.


### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="520"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 12345678")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `EduContactsParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `EduContactsParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `EduContactsParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

<div style="page-break-after: always;"></div>

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the contact data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
    * a `Person` object stores `StudentId`, `Name`, `Address`, `Phone`, `Email`, `Role`, `Course` objects.
    * contains an ArrayList of `Module` objects which is optional.
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* is intentionally designed to be independent of other components (e.g., UI, Logic, Storage) to maintain a clean separation of concerns. This ensures that the Model layer is solely responsible for managing data and that data structures make sense on their own. This independence enables easier maintenance, testing, and adaptability of the data structures, as changes in one component (e.g., UI) do not affect the Model.

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Role` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Role` object per unique role, instead of each `Person` needing their own `Role` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

<div style="page-break-after: always;"></div>

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component has a key role in persisting data across user sessions. Specifically, it,
* can save both EduContacts data and user preference data in JSON format. Upon application startup, it reads the saved JSON data back into the app, reconstructing it into the corresponding objects in the `Model` component.

* inherits from both `EduContactsStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).

* depends on some classes in the `Model` component to serialize and deserialize data, because the `Storage` component's job is to save/retrieve objects that belong to the `Model`. This dependency allows it to handle domain-specific structures, like `Person` and `UserPrefs`, ensuring the saved data aligns with the current application state and structure.

* uses exception handling to manage file I/O issues (e.g., missing or corrupted files) and provides feedback to the user if data loading or saving encounters an issue, ensuring that the application can gracefully handle storage-related errors.

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

<box type="info" seamless>

**Note:** The execution of commands mentioned in this section follows a similar path to that depicted in the sequence diagram under the [Logic component](#logic-component) section and will not be discussed in this section.

</box>

<div style="page-break-after: always;"></div>

### Module feature

The module feature allows users to keep track of modules a `Person` in `EduContacts` is taking. Each `Person` object has a `List<Module>` field that encapsulates the list of modules the Person is taking.

<br>

**Adding a `Module`:**

Users are able to add a `Module` to a `Person`'s list of modules using the `ModuleCommand`. Given below is an example usage scenario of the ModuleCommand.

Step 1. The user launches the application, which is populated with a list of their students. One of the students has `StudentId` of 12345678. Let's refer to this `Person` as Alex.

Step 2. The user executes `module 12345678 m/CS2103T` command to add the CS2103T `Module` to Alex's list of modules. Alex now has a CS2103T `Module` in their list of modules.

<br>

**Editing a `Module`:**

The user is able to edit a `Module` in a `Person`'s list of modules using the `EditCommand`. Given below is an example usage scenario of the EditCommand.

Step 1. The user launches the application, which is populated with a list of their students. One of the students has `StudentId` of 87654321 and a CS2103T `Module` in their list of modules. Let's refer to this `Person` as Bernice.

Step 2. The user executes `edit 12345678 m/CS2103T CS2103` command to edit the CS2103T `Module` to CS2103. Bernice now has a CS2103 `Module` in their list of modules instead of a CS2103T `Module`.

<br>

**Deleting a `Module`:**

The user is able to delete a `Module` from a `Person`'s list of modules using the `DeleteCommand`. Given below is an example usage scenario of the DeleteCommand.

Step 1. The user launches the application, which is populated with a list of their students. One of the students has `StudentId` of 87654321 and a CS2103T `Module` in their list of modules. Let's refer to this `Person` as Bernice.

Step 2. The user executes `delete 12345678 m/CS2103T` command to delete the CS2103T `Module` from Bernice's list of modules. Bernice now no longer has a CS2103T `Module` in their list of modules.

### Grade feature

The grade feature allows users to assign a `Grade` to a `Module` of a `Student` in `EduContacts`. Each `Module` object has a `Grade` field.

Users are able to assign a `Grade` to a `Module` using the `GradeCommand`. Given below is an example usage scenario of the GradeCommand.

Step 1. The user launches the application, which is populated with a list of their students. One of the students has `StudentId` of 87654321 and a CS2103T `Module` in their list of modules. Let's refer to this `Student` as Bernice.

Step 2. The user executes `grade 87654321 m/CS2103T g/A` command to assign an A `Grade` to Bernice's CS2103T `Module`. Bernice now has a CS2103T `Module` graded A in their list of modules.


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

<div style="page-break-after: always;"></div>

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* Teachers in tertiary institutions.
* Has a need to manage a significant number of contacts.
* Requires a tool to keep communication organised across large groups.
* Requires dedicated support for tracking of academic progress.
* Prefers typing and is familiar with command-line interfaces.

**Value proposition**: Provides teachers comfortable with CLI-based interfaces an efficient platform for contact management and student academic progress tracking.

<br>

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                    | I want to …​                                                        | So that I can…​                                                                                        |
|----------|----------------------------|---------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------|
| `* * *`  | teacher                    | add a contact                                                       | have easy access to contacts                                                                           |
| `* * *`  | teacher                    | delete a contact                                                    | remove contacts that I no longer need                                                                  |
| `* * *`  | teacher                    | add contact details like email, address, phone number, modules etc. | keep track of my contacts' details                                                                     |
| `* * *`  | teacher                    | assign a grade to a module that my student is taking                | keep track of a student's grades and academic progress                                                 |
| `* * *`  | teacher                    | view contact information                                            | have a clear visual reference for contact information                                                  |
| `* *`    | teacher                    | edit a contact                                                      | update contact information without having to delete it                                                 |
| `* *`    | teacher                    | search for a contact                                                | find the contact I am looking for without having to scroll through the list                            |
| `* *`    | teacher                    | add roles/tags to contacts                                          | group the many contacts that are in the application by their type (e.g. student, tutor etc.)           |
| `* *`    | teacher                    | filter contacts by labels or tags                                   | have an easy visual reference with irrelevant contacts filtered out                                    |
| `* *`    | new user                   | access a page that teaches me how to use the application            | familiarise myself with the application interface                                                      |
| `* *`    | efficient CLI user         | navigate command history (like in other CLI-based applications)     | easily load previously entered commands without having to re-type commands from scratch (to save time) |
| `*`      | new user                   | see the application populated with sample data                      | see what the application interface looks like                                                          |
| `*`      | mistake-prone user         | undo previous action                                                | undo a mistake without having to delete or edit any contacts                                           |
| `*`      | teacher with many contacts | perform mass operations on contacts                                 | perform operations (add, edit, delete etc.) on multiple contacts without having to do so one by one    |
| `*`      | organised teacher          | archive contacts                                                    | reduce clutter in the application without permanently deleting the contact                             |
| `*`      | careful teacher            | export contact data                                                 | have a backup data file in case anything happens to the application                                    |
| `*`      | careful teacher            | import contact data                                                 | load data from a file to restore lost or missing data                                                  |



<br>

### Use cases

(For all use cases below, the **System** is the `EduContacts` and the **Actor** is the `user`, unless specified otherwise)

---

#### UC01 - Add a contact

**MSS**

1. User adds a contact to the list of contacts.
2. EduContacts updates the list of contacts.

   Use case ends.

**Extensions**

* 1a. EduContacts detects an error in the user input.

    * 1a1. EduContacts provides an appropriate error message as feedback to user.

      Use case ends.

---

#### UC02 - Search for a student

**Preconditions:** The list of contacts is not empty.

**MSS**

1. User provides details of the student.
2. EduContacts displays the student and their details.

   Use case ends.

**Extensions**

* 1a. EduContacts detects an error in the user input (command format, student does not exist etc.).

    * 1a1. EduContacts provides an appropriate error message as feedback to user.

      Use case ends.

---

#### UC03 - Add a module to a student

**MSS**

1. User <u>searches for the student (UC02)</u> they wish to add a module for.
2. User adds a module to the student in the list.
3. EduContacts updates the list of contacts.

   Use case ends.

**Extensions**

* 1a. EduContacts detects an error in the user input (command format, student does not exist, duplicate module etc.).

    * 1a1. EduContacts provides an appropriate error message as feedback to user.

      Use case ends.

---

#### UC04 - Add a grade for a student

**Preconditions:** The student has a <u>module already added (UC03)</u>.

**MSS**
1. User <u>searches for the student (UC02)</u>  they wish to add a grade for.
2. User adds a grade for the student.
3. EduContacts updates the list of contacts.

   Use case ends.

**Extensions**

* 1a. EduContacts detects an error in the user input (command format, student or module does not exist etc.).

    * 1a1. EduContacts provides an appropriate error message as feedback to user.

      Use case ends.


* 2a. The module is already graded.

    * 2a1. EduContacts overwrites the old grade with the new grade.

      Use case ends.

---

#### Use case: UC05 - Delete a student

**MSS**

1. User <u>searches for the student</u> (UC02) they wish to delete from the list.
2. User deletes the student in the list.
3. EduContacts updates the list of contacts.

   Use case ends.

**Extensions**

* 1a. EduContacts detects an error in the user input (command format, student does not exist etc.).

    * 1a1. EduContacts provides an appropriate error message as feedback to user.

      Use case ends.

---

#### Use case: UC06 - Edit a student's details

**MSS**

1. User <u>searches for the student</u> (UC02) they wish to edit.
2. User edits the details of the student in the list.
3. EduContacts updates the list of contacts.

   Use case ends.

**Extensions**

* 1a. EduContacts detects an error in the user input (command format, student does not exist etc.).

    * 1a1. EduContacts provides an appropriate error message as feedback to user.

      Use case ends.

---

#### UC07 - Filter contacts based on properties

**MSS**

1. User provides a set of contact-related conditions.
2. EduContacts updates the list view to show contacts matching the provided conditions.

   Use case ends.

**Extensions**

* 1a. EduContacts detects an error in the user input (command format etc.).

    * 1a1. EduContacts provides an appropriate error message as feedback to user.

      Use case ends.

<br>

### Non-Functional Requirements
1.  **Data Requirements**
    - EduContacts must be capable of storing up to 1000 students’ contact details and academic data without significant performance degradation
    - Contact and student data will not change frequently, but updates must be handled seamlessly (e.g., adding, updating, or deleting student details).
2. **Portability**
    - Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
    - EduContacts must be packaged in a single JAR file (or ZIP if additional resources are required). It should not require installation, making it easy to use on any system that supports Java 17.
    - All data must be stored locally in human-readable text files, making it easy to back up or transfer data between systems.
3. **Scalability**
    - The system should be designed with extensibility in mind, allowing new features (e.g., tracking student behavior) to be added without significantly altering the existing codebase.
    - The system should be capable of supporting more than 1000 contacts if needed, with the ability to upgrade to handle larger datasets without performance degradation.
4. **Accessibility**
    - A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
    - The user interface should be accessible to users with disabilities, adhering to standard accessibility guidelines to accommodate screen readers and keyboard navigation.
    - The system should be easy to use for educators, with a simple CLI for fast input and an intuitive GUI for viewing contact information and reports.
5. **Efficiency**
    - The system should respond within 1 second for typical operations (e.g., adding a student, searching for a contact) with up to 1000 records. For larger datasets, response time should not exceed 3 seconds.
    - Operations such as adding, deleting, and modifying student details should not cause noticeable performance degradation, even as the dataset grows.
6. **Documentation**
    - The system should include comprehensive documentation for both command-line and GUI operations, along with access to user support for troubleshooting and guidance.
    - The codebase should be documented for future developers, making it easy to maintain, debug, and extend the system.
7. **Robustness**
    - The system must provide informative error messages for incorrect input or failures (e.g., invalid student ID).
    - The system should not crash unexpectedly, and all errors must be logged for debugging.
8. **Security**
    - A login page should be implemented in order to prevent unauthorised users from signing in to other accounts.
    - Student and parent data should be encrypted to prevent unauthorized access, while still being editable by advanced users.
9. **Testability**
    - The system should be compatible with automated testing frameworks, and all major features should be covered by unit and integration tests.
    - All features should be easy to test manually without requiring complex setups.
10. **Reliability**
    - The application must be reliable and available for use during critical hours, such as during class or exam periods, without crashing or losing data.
11. **Compliance**
    - EduContacts must comply with relevant data protection laws (e.g., GDPR, PDPA) to ensure student and parent information is stored securely and is accessible only by authorized users.
    - The system should provide options to manage and delete student data upon request, to comply with legal requirements.
12. **Interoperability**
    - EduContacts should be able to sync data with external platforms, such as Coursemology or Canvas, through API or manual import/export mechanisms.
    - The system should support exporting and importing data in common file formats (e.g., CSV) for ease of use and integration.
13. **Disaster Recovery**
    - The system should support manual and automatic backups to prevent data loss. In case of a critical failure, the data should be easily recoverable.
    - There should be clear steps for restoring data from a backup after a system failure, ensuring minimal downtime.<br>
14. **Fault Tolerance**
    - All critical errors should be logged, allowing developers to troubleshoot and resolve issues. Minor errors should not crash the system but allow users to continue their tasks.
    - In the event of a system fault, the system should continue operating in a degraded mode without losing functionality.

<br>

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Command-line Interface (CLI)**: A text-based interface used to interact with the software by typing commands. It is preferred by users who can type quickly and need to perform tasks efficiently without relying on graphical elements.
* **Graphical User Interface (GUI)**: The visual component of EduContacts where users can interact with the application through graphical elements (e.g., buttons, forms) rather than text-based commands.
* **JAR File**: A Java Archive (JAR) file that contains the EduContacts application. This single file allows the program to be executed on any machine that supports Java 17, without needing installation.
* **Human-readable File**: A text file that can be easily opened, read, and understood by a person. Typically, such files are plain text and do not require specialized software to interpret or modify.
* **JavaScript Object Notation (JSON)**: A lightweight, text-based format for representing structured data. It is used to exchange data between a server and a web application or store configuration settings.
* **Comma-Separated Values (CSV)**: A plain-text file format used to store tabular data (numbers and text) in a structured way. Each line of the file corresponds to a row in the table, and the values are separated by commas (or other delimiters like semicolons or tabs).
* **Duplicate Entry**: A situation where a student or contact already exists in the address book based on a matching unique identifier (e.g., Student ID). In such cases, EduContacts will reject the duplicate entry and display an error message.
* **Error Message**: A notification provided to the user when an invalid input or action is detected. For example, entering a student ID in the wrong format will prompt an error message such as "Please enter a valid student ID!"

<div style="page-break-after: always;"></div>

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

<br>

### Launch and shutdown

#### Initial launch

1. Ensure you have Java `17` or above installed in your computer.
    [Download Java here](https://www.oracle.com/sg/java/technologies/downloads/) if you haven't already.
    
    Note that Mac users should use the specific Azul JDK 17 distribution specified in [this guide](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-F15-2/tp/releases).

3. Copy the file to the folder you want to use as the home folder for your EduContacts.

4. To run EduContacts, open a command terminal.

   To navigate to the folder where you placed the `.jar` file, use the `cd` command. For example, if you placed the file in a folder named `EduContacts` on your desktop, you would enter:

   ```bash
   cd ~/Desktop/EduContacts
   ```

   and use the following command to run the application:

   ```bash
   java -jar educontacts.jar
   ```

   A GUI similar to the screenshot below should appear in a few seconds. Note how the app contains some sample data.<br>

   ![Ui](images/Ui.png)

---

#### Saving window preferences

1. Resize the window to an optimum size. Move the window to a different location. Close the window.

2. Re-launch the app by double-clicking the jar file.<br>
   Expected: The most recent window size and location is retained.

<br>

### Commands Testing

#### Help Command

**Opening the help window**
* **Test case:** `help`
* **Expected:** The help window opens, displaying a comprehensive list of available commands and their usage.

**Using the help button**
* **Test case:** Click the "Help" button in the application GUI.
* **Expected:** The help window opens, displaying a comprehensive list of available commands and their usage.

---

#### Add Command

**Adding a valid person**
* **Test case:** `add 12345678 n/John Doe p/91234567 e/johndoe@example.com a/123 Example Street c/Computer Science r/Student`
* **Expected:** A new contact is added with the specified details. The list now includes the new contact, and a success message is shown.

**Adding a duplicate person**
* **Prerequisites:** The person to add already exists in the list with the same Student ID `12345678`.
* **Test case:** `add 12345678 n/John Doe p/91234567 e/johndoe@example.com a/123 Example Street c/Computer Science r/Student`
* **Expected:** The addition fails, and an error message about the duplicate Student ID is shown.

**Adding an person with invalid Student ID**
* **Test case:** add 2345678 n/John Doe p/91234567 e/johndoe@example.com a/123 Example Street c/Computer Science r/Student
* **Expected:** The addition fails, and an error message about the invalid Student ID format is shown.

---

#### Edit Command

**Editing an existing person**
* **Prerequisites:** List contains a person with Student ID `12345678`.
* **Test case:** `edit 12345678 p/98765432`
* **Expected:** The contact's phone number is updated to `98765432`. The updated details are shown in the success message.

**Editing a non-existing person**
* **Prerequisites:** List does not contain a person with Student ID `00000000`
* **Test case:** `edit 00000000 p/98765432`
* **Expected:** The edit fails, and an error message about the non-existent Student ID is shown.

**Editing with invalid input**
* **Test case:** `edit 12345678 p/invalidPhoneNumber`
* **Expected:** The edit fails, and an error message about the invalid phone number format is shown.

---

#### Filter Command

**Filter by name**
* **Test case:** `filter n/John`
* **Expected:** The list shows all persons with "John" in their name. A success message is shown.

**Filter by course**
* **Test case:** `filter c/Computer Science`
* **Expected:** The list shows all persons enrolled in "Computer Science." A success message is shown.

**Filter by multiple attributes**
* **Test case:** `filter n/John c/Computer Science`
* **Expected:** An error message about invalid multiple filter conditions is shown.

---

#### Module Command

**Adding a module to an existing person**
* **Prerequisites:** List contains a person with Student ID `12345678`.
* **Test case:** `module 12345678 m/CS2103T`
* **Expected:** The module "CS2103T" is added to the person's module list. A success message is shown.

**Adding a duplicate module**
* **Prerequisites:** The person already has "CS2103T" as a module.
* **Test case:** `module 12345678 m/CS2103T`
* **Expected:** The addition fails, and an error message about the duplicate module is shown.

**Adding an invalid module**
* **Prerequisites:** List contains a person with Student ID 13131313.
* **Test case:** module 13131313 m/CS 2103T
* **Expected:** The addition fails, and an error message about the invalid module format is shown.

---

#### Grade Command

**Adding a grade to a module**
* **Prerequisites:** The person with Student ID `12345678` has "CS2103T" in their module list.
* **Test case:** `grade 12345678 m/CS2103T g/A`
* **Expected:** The grade "A" is assigned to "CS2103T." A success message is shown.

**Adding a grade to a non-existent module**
* **Test case:** `grade 12345678 m/CS9999 g/B`
* **Expected:** The addition fails, and an error message about the non-existent module is shown.

**Adding an invalid grade**
* **Test case:** `grade 12345678 m/CS2103T g/Z`
* **Expected:** The addition fails, and an error message about the invalid grade format is shown.

---

#### Find Command

**Finding an existing person**
* **Test case:** `find 12345678`
* **Expected:** The contact details of the person with Student ID `12345678` are shown in the result panel.

**Finding a non-existing person**
* **Test case:** `find 00000000`
* **Expected:** An error message about the non-existent Student ID is shown.

---

#### Clear Command

**Clearing all entries**
* **Test case:** `clear`
* **Expected:** All persons are removed from the list. A success message is shown.

---

#### Exit Command

**Exiting the application**
* **Test case:** `exit`
* **Expected:** The application closes successfully without errors.

---

#### Navigating Command History
**Navigating to previous commands**
* **Test case:** Execute several successful commands (e.g., `list`, `add 12345678 n/John Doe p/91234567 e/johndoe@example.com a/123 Example Street c/Computer Science r/Student`, `filter n/John`). 
Then, press the UP arrow key in the Command Box.
* **Expected:** Each press of the UP arrow key navigates backward through the previously executed commands, displaying them in the Command Box.

**Navigating to next commands**
* **Prerequisites:** Use the UP arrow key to navigate to a previous command in the Command Box.
* **Test case:** Press the DOWN arrow key.
* **Expected:** Each press of the DOWN arrow key navigates forward through the command history until the most recent command is displayed. 
If no newer commands exist, the Command Box becomes empty.


<br>

### Saving data

#### Dealing with missing data files

1. To simulate a missing file, in the same folder as the jar file, navigate to the `data` folder and delete the `address.json` file in the folder.

2. Launch EduContacts by double-clicking the jar file.<br>
   Expected: EduContacts is populated by a set of default list of persons. A new `address.json` file will be created in the `data` folder after closing the app or executing a command.

---

#### Dealing with corrupted data files

1. To simulate a corrupted file, navigate to the `data` folder and remove a curly brace at the end of the file.

2. Launch EduContacts by double-clicking the jar file.<br>
   Expected: EduContacts has a blank list of persons. A new `address.json` file will be created in the `data` folder after closing the app or executing a command.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Effort**

### Achievements
1. As EduContacts is a CLI-based application, we have added the ability for users to navigate through their previous commands using the up and down arrow keys. This helps users accomplish repetitive tasks, e.g. grading a module for multiple persons in EduContacts, way quicker than they would be able to without this functionality.<br><br>
2. We have decided to use a person's student ID as a unique identifier for persons in EduContacts, instead of index like in AB3. The usage of student ID to perform tasks is more intuitive for teachers, our target user group. This therefore optimises EduContacts for teachers.<br><br>
3. We have expanded on the original find command to filter based on course and modules, in addition to name. This command has been renamed to filter to better reflect the functionality of the command.<br><br>
4. We have also refactored the original find command to instead display the full details of a person in EduContacts in a separate panel in the GUI. This allows us to be more selective with what information we display in the PersonCard. We can therefore only display information we believe teachers should be able to retrieve at a glance, e.g. names, student IDs, thus further optimising EduContacts for teachers.<br><br>
5. We have also made various enhancements to the GUI:
   - The first enhancement made was the help page. AB3's help page was simply a link to the User Guide, which is not user-friendly. We have therefore taken the liberty of changing the help page to instead contain a summary of the commands supported by EduContacts, including their formats and example usages.
   - We then updated the GUI to include a separate panel to display student details when the find command is executed.

### Effort Required
We have put in a significant amount of effort in this project, with close to 2,000 commits over 15,000 lines of code contributed. We estimate that our effort for the project is about 50% of that spent on creating AB3.

### Challenges Faced
1. The first major roadblock in our project was storing modules in JSON format. Modules for a Person object is stored as a Collection, much like Tags in AB3. However, modules also have a Grade field, whereas Tags in AB3 do not have. This added a layer of complexity that we did not expect, and a longer amount of time had to be invested to work out the issue.<br><br>
2. The second challenge we faced was using student ID as a unique identifier for persons in EduContacts instead of index. This task was more complicated that we initially anticipated, as there were many unforeseen use cases that we had to take into consideration. This includes duplicate student ID handling, handling cases where a person with a specified student ID exists in EduContacts but not in the currently displayed list, and so on. A lot of scrutinising and attention to detail was required to ensure our commands were bug-free and the command error messages displayed were correct. <br><br>

### Difficulty Level
Our project requires a relative high level of understanding of the code base. Due to the change to make student ID the unique identifier for persons in EduContacts, we needed a high level of understanding of how the various command parsers and argument tokenisers work in order to implement bug-free code.<br><br>Additionally, we had to have a good understanding of our JSON and the Jackson library worked in order to implement modules in our project.<br><br>Finally, we have also made numerous updates to the GUI, which was tough as we were all relatively new to JavaFX. We therefore rate the difficulty level of our project as medium to hard.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

The following planned enhancements are proposed by the development team (team size of 5) to be implemented in the near future. 

These are current known issues or feature flaws related to the design of the application that have been identified. These
enhancements are not critical to the usability of the application for its intended use case, but rather highlight
potential mis-steps in the design process of the application by the development team.

The implementation for the planned enhancements required to improve these known issues/feature flaws have been deferred for future iterations as the
development team feels that the effort required to fix these known issues/feature flaws outweighs their value add
to the user at this current stage of development.

---

#### 1. Overly General Error Message for Duplicate Command Word

**Feature Flaw:** The application currently displays a generic "Invalid command format" error message when duplicate command words are entered. This can be confusing and unhelpful to users. For instance:
* Input: `edit edit xxx`
* Current Error Message: "Invalid command format"
* Suggested Error Message: "Duplicate command word: `edit`."

**Enhancement:** Implement a granular exception handling mechanism to specifically detect and report duplicate command words. This will involve:
* Parsing the user input to identify repeated command words.
* Providing an error message tailored to the specific issue.

**Justification for Deferral:**
* While improving error messages is valuable, the current placeholder messages are functional and convey basic information.
* Developing tailored error handling requires significant effort that could be redirected toward implementing higher-priority features.
* Addressing this issue after the application achieves a more stable feature set ensures that the error-handling logic does not require repeated revisions due to ongoing changes in the application.

---

#### 2. Overly General Error Message for Duplicate IDs in Commands

**Feature Flaw:** When duplicate IDs are entered in commands, the application currently provides a generic "Invalid command format" error message. This does not clearly communicate the issue to the user. For example:
* Input: `find ID ID`
* Current Error Message: "Invalid command format"
* Suggested Error Message: "Duplicate `ID` detected, only one allowed."

**Enhancement:** Introduce specific error handling to detect and report duplicate IDs in user commands. This will involve:
* Validating commands to identify duplicate ID arguments.
* Displaying a clear error message, e.g., "Duplicate `ID` detected, only one allowed."

**Justification for Deferral:**
* The current generic error messages fulfill their basic purpose, ensuring that invalid commands are rejected.
* Implementing fine-grained validation and error reporting for duplicate IDs requires significant effort, including modifying command parsing logic.
* Addressing this issue in a future iteration, when core features are complete and stable, avoids unnecessary rework of error-handling logic.

---

#### 3. Address `help` Command Popup Window Stability Issue

**Known Issue:** On certain platforms (notably MacOS), running the `help` command and closing the popup window repeatedly in quick succession while in full screen can cause the application to hang or crash.

**Proposed Enhancement:**
* Improve the robustness of the `help` command by enhancing resource management and concurrency handling to prevent hangs or crashes.
* Note: Consideration was given to displaying the help page within the main application window instead of a popup. However, the popup design was retained because it allows users to reference the help page alongside the main application, providing greater convenience and usability.

**Justification for Deferral:**
* The issue arises only under specific conditions (frequent execution of the `help` command in quick succession in full screen), making it less critical compared to other bugs or enhancements.
* Implementing a fix requires significant effort to revamp resource and concurrency management, which could detract from more pressing development priorities.
* The current implementation functions adequately for standard use cases, making this issue suitable for resolution in future iterations when higher-priority tasks are completed.

---

#### 4. StudentID as Duplicate Detection Mechanism

**Feature Flaw:** All contacts are currently identified by their StudentID. This approach is restrictive, especially for non-student contacts like tutors or colleagues, and relies on dummy IDs as a workaround for non-students.

**Enhancement:** Introduce a more robust identification mechanism outside of StudentID to distinguish between students and other contact types. For example:
* Add a flexible identification field applicable to both students and non-student contacts.
* Allow unique identifiers based on role type.

**Justification for Deferral:**
* Implementing this enhancement requires significant refactoring of the `Model`, `Storage`, and `Logic` components, including data representation, validation processes, and storage formats.
* The current system is sufficient for the primary use case (managing students and student tutors). Workarounds like dummy IDs enable limited handling of non-student contacts for now, making this a lower-priority enhancement.

---

#### 5. Support for Multiple Filter Conditions

**Feature Flaw:** The `filter` command currently has two limitations:
1. It only supports one filter condition, which restricts its flexibility and utility for complex searches.
2. The error message for multiple filter conditions is overly general and does not explain the limitation. For instance:
    * Input: `filter c/Computer Science n/Crowe`
    * Current Error Message: "Invalid command format"
    * Suggested Error Message: "Only one filter condition allowed."

**Enhancement:** Extend the `filter` command to support multiple filtering conditions. This will allow users to specify multiple conditions for filtering contacts with a toggle functionality to choose between:
* **AND** (strict search, matches all specified conditions).
* **OR** (broad search, matches any of the specified conditions).

**Justification for Deferral:**
* Implementing support for multiple filter conditions and adding toggling options requires substantial modifications to the `Logic` and `Parser` components to manage complex search constraints effectively.
* Enhancing error messages and expanding filter capabilities would also necessitate updates to input validation and testing.
* The current implementation provides basic filtering functionality and adequately meets the needs of most users, 
making this enhancement a lower priority compared to the resolution of critical bugs and the development of core features. 
By addressing this enhancement in a future iteration, the changes can better align with potential expansions to filtering capabilities.

---

#### 6. Toggle for Partial vs Full Matching in Filter Command

**Feature Flaw:** The current behavior of partial or full matching in the `filter` command depends on the field being filtered, which may confuse users.

**Enhancement:** Add a toggle feature to allow users to choose between:
* Partial matching: Matches substrings (e.g., `CS21` matches `CS2103T`).
* Full matching: Matches exact strings (e.g., `CS2103T` matches only `CS2103T`).

**Justification for Deferral:**
* Introducing toggles requires updates to the `Logic` and `Parser` components to interpret and handle matching options.
* The current partial matching behavior is sufficient for most searches and can be deferred until other higher-priority enhancements are completed.

---

#### 7. Support for Additional and Customizable Roles

**Feature Flaw:** The application currently supports only `Student` and `Tutor` roles, limiting its applicability for broader use cases.

**Enhancement:** Update the role types to include specific roles such as `Colleague` and customizable generic roles like `Other`. For example:
* Add predefined roles such as `Colleague`, and `IT`.
* Allow users to define custom role types to suit their specific needs.

**Justification for Deferral:**
* This enhancement requires significant modifications to the `Model`, `Logic`, and `Storage` components to handle additional and customizable roles.
* The current implementation meets the basic needs of most users by supporting the essential roles (`Student` and `Tutor`), making this enhancement a lower-priority task compared to core feature developments.

---

#### 8. Support for Contacts Being Tagged with Multiple Roles

**Feature Flaw:** Each contact can only be assigned one role, which limits flexibility in scenarios where a person fits into multiple categories (e.g., a student who is also a part-time teaching assistant/tutor).

**Enhancement:** Allow contacts to hold multiple roles where logically appropriate. For example:
* A contact can have the roles `Student` and `Tutor` simultaneously if they are a part-time teaching assistant.
* Validation logic should ensure logical combinations (e.g., `Student` and `Colleague` cannot co-exist).

**Justification for Deferral:**
* Supporting multiple roles per contact requires significant updates to the `Model` to support role combinations, as well as changes to `Logic` and `UI` for input validation and display.
* The current implementation, which enforces a single role per contact, is sufficient for the majority of use cases and avoids potential complexity or confusion in managing contacts, making this enhancement a lower-priority task at this stage.

---

#### 9. Enhanced Module Utility

**Feature Flaw:** The current module utility only supports tracking modules, limiting its value for users who might want to track additional details like assignment grades or attendance.

**Enhancement:** Expand the module utility to allow customization, such as the ability to rename `Module` to `Assignments`, for example. 

**Justification for Deferral:**
* Significant updates to the `Model`, `Storage`, and `UI` components are required to support flexible customization.
* The current utility is sufficient for basic module tracking, making this enhancement a lower priority.

---

#### 10. Module Not Applicable for Certain Contacts

**Feature Flaw:** Tutors and non-student contacts may not need a module field, but the current implementation does not allow this distinction.

**Enhancement:** Add an option to mark the module field as "Not Applicable" for specific roles (e.g., `Tutor`, `Colleague`).

**Justification for Deferral:**
* Implementing this feature requires structural changes to the `Model` and updates to validation logic.
* The current system works with placeholders, and this limitation does not significantly impact functionality, making it a lower-priority enhancement.


## **Appendix: New Features**

The following are new features planned that add additional functionality to our application. (Not to be confused with [Planned Enhancements](#appendix-planned-enhancements))

#### `Undo` command
* **Description:** Allows users to revert the most recent change made to the application’s data.
* **Benefits**
  * Provides users with the flexibility to correct mistakes quickly without manually re-entering data or commands.
  * Minimizes the impact of accidental commands (e.g., mistakenly deleting or editing a contact).
  * Reduces frustration and saves time for users by eliminating the need to redo lengthy or complex data modifications.

#### Data archiving

* **Description:** Allow users to move inactive or irrelevant entries to an archive.
* **Benefits:**
    * Reduces clutter in the main data set, making it easier to manage and navigate active records without losing historical data.
    * Lower the load on real-time data processing by isolating inactive records.
    * Retain archived data for historical records or compliance requirements.
    * Provide a safe way to store inactive data without risking deletion or loss.

#### Importing contact data

* **Description:** Allow users to import contact data from multiple formats (e.g. CSV, vCard)
* **Benefits:**
    * Saves time and effort by allowing users to easily populate the app with data, eliminating the need for manual data entry.
    * Maintain consistent contact records across different applications and devices.
    * Simplify the process for new users by letting them import contacts directly.
    * Enable users to restore contacts from external files in case of data loss.

#### Exporting contact data

* **Description:** Allow users to export contact data to multiple formats (e.g. CSV, vCard)
* **Benefits:**
    * Allow users to easily transfer their contact information to other applications or storage solutions.
    * Provide users with the ability to create backups of their contact data.
    * Enable users to share their contact lists with others.
    * Allow users to organize and manipulate their contact data externally.
    * Help users comply with data export regulations or organizational policies.
