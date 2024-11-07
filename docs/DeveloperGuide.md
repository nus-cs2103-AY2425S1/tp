---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# StaffSync Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).

This project uses [JavaFX](https://openjfx.io/) for the GUI and [JUnit5](https://junit.org/junit5/) for testing. The project is built using [Gradle](https://gradle.org/).

This project uses Github Copilot extensively for code completion throughout the project by [@InfinityTwo](http://github.com/infinitytwo), [@KiKusaurus](https://github.com/kikuasaurus) and [@thortol](http://github.com/thortol).

This project also makes use of [Google's Material Icons](https://fonts.google.com) and Fonts for styling under the [SIL Open Font License](https://developers.google.com/fonts/faq#can_i_use_any_font_in_a_commercial_product) for Fonts and [Apache License 2.0](https://fonts.google.com/icons) for Icons.

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

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete e 1`.

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

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete e 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete e 1` Command" />

The sequence diagram below illustrates the interactions within the `Logic` component, taking `edit 1 n/tom` API call as an example.

<puml src="diagrams/EditSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `edit 1 n/tom` Command" />

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute(find all n/John)` API call as an example.

<puml src="diagrams/FindSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `find all n/John` Command" />

The sequence diagram below illustrates another interaction within the `Logic` component, taking `execute("demote 1")` API call as an example.

<puml src="diagrams/DemoteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `demote 1` Command" />

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteEmployeeCommand`) which is executed by the `LogicManager`.
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

* has a need to manage a significant number of employees and potential hires
* has a need to match potential hires with available job openings
* has a need to find details about an employee or potential hire quickly
* has a need to search for employees or potential hires with relevant details
* forgets commands and requires a list of commands to use the application
* prefer desktop applications over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**:
* manage a significant number of employees and potential hires faster than a typical mouse/_GUI_ driven app
* find details about an employee or potential hire faster than a spreadsheet
* matches potential hires with available job openings faster than a spreadsheet
* for organizations seeking to manage employees and potential hires, our application offers a more specialized solution than an address book application


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority             | As a …​    | I want to …​                                                     | So that I can…​                                                   |
|----------------------|------------|------------------------------------------------------------------|-------------------------------------------------------------------|
| `* * *`              | HR Manager | View phone number of my employees/potential hire                 | I can easily contact them if required                             |
| `* * *`              | HR Manager | Insert phone number of my employees/potential hire               | I can retrieve their phone number if required                     |
| `* * *`              | HR Manager | View email addresses of employees/potential hire                 | I can contact them if its not an emergency                        |
| `* * *`              | HR Manager | Insert phone number of my employees/potential hire               | I can retrieve their email address if required                    |
| `* * *`              | HR Manager | Delete data through the UI                                       | I can delete users who are incorrectly added                      |
| `* * *`              | HR Manager | View address of employees/potential hire                         | I can view the address of the user to decide where to deploy them |
| `* *`                | New user   | Be shown some basic functions                                    | I can learn the basic functions of the product                    |
| `* *`                | New user   | View the user guide easily                                       | I can learn more functions of the product whenever I want         |
| `* *`                | New user   | Purge the sample data in the tutorial                            | I can input my own data to use                                    |
| `*`                  | HR Manager | View the emergency contact details of employees                  | I can quickly respond in case of an emergency                     |
| `*`                  | HR Manager | Sort the employee information by when their contract will expire | I can better plan out when to resign contracts                    |
*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `StaffSync` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Delete a person**

**MSS**

1.  User requests to list persons
2.  StaffSync shows a list of persons
3.  User requests to delete a specific person in the list of the correct shown type
4.  StaffSync deletes the person

   Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

   * 3a1. StaffSync shows an error message.

      Use case resumes at step 2.

* 3b. The given index is not of the correct person type.

   * 3b1. StaffSync shows an error message.

      Use case resumes at step 2.

**Use Case: Add an employee**

**MSS**

1. User requests to add an employee.
2. StaffSync saves the employee's information.

    Use case ends.

**Extensions**

*1a. The input syntax is invalid.

    * 1a1. StaffSync shows an error message.

    Use case resumes at step 1.

*1b. The user requests to add a potential hire.

    *1b1. StaffSync saves the potential hire's information.

    Use case ends.

**Use case: Find a person**

**MSS**

1. User requests to find based on name.
2. StaffSync displays a list of people who have the name.

Use case ends.

**Extensions**

*1a. The input syntax is invalid

    *1a1. StaffSync shows an error message.

    Use case resumes at step 1.

*1b. There is no name that fits the search.

    *1b1. The list is empty.

    Use case ends.

**Use case: Ask for help**

**MSS**

1. User requests for help.
2. StaffSync gives a list of commands.

Use case ends.

**Use case: Exit the program**

**MSS**

1. User requests to exit the program.
2. StaffSync closes.


### Non-Functional Requirements

1. Should work on any _mainstream OS_ on either 32-bit or 64-bit systems as long as it has Java `17` or above installed.
2. Should be able to hold up and deal with up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. A user should be able to run the software without the need to install libraries or other dependencies. They should only need Java to run the program and the program should be self contained.
5. The software should be resizable and by default, be of a size that is usable on a 1920x1080 screen.
6. The software should be backward compatible for version changes and previous saved data should be able to be loaded without any issues.
7. Our software should have an _MVP_ by the end of v1.3, around the end of week 9.
8. Our software should have an _Alpha Release_ by the end of v1.4, around the end of week 10.
9. Our software should have a _Release Candidate_ by the end of v1.5, around the end of week 11.
10. Our software should have a _Public Release_ by the end of v1.6, around the end of week 12.
11. Our software will NOT handle any security or privacy related to data inserted into the software. It is the user's responsibility to ensure that the data is not sensitive or private and that it will not be leaked.
12. Our software should not have any memory leaks and should not consume more memory than necessary.
13. Our software should not crash with any user input.
14. A user should not need to use their mouse for over 50% of the time when using the software.
15. Our software should be usable 100% of the time without an internet connection.
16. Our software should be able to process all commands under 1 second within our limitations above.

### Glossary

* **GUI**: Graphical User Interface - User interface which allows users interact with the application through components such as icons, buttons and menus
* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **MVP**: Minimum Viable Product - The minimum set of features that is required to make the product usable by the target user
* **Alpha Release**: A version of the software that is feature complete but may have bugs
* **Release Candidate**: A version of the software that is feature complete and has no known bugs
* **Public Release**: A version of the software that is released to the public with any bugs squashed after Release Candidate

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

   2. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar staffSync.jar` command to run the application. Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

3. Closing the application
   
   1. Click the close button on the top right of the window.<br>
      Expected: The window closes and the application terminates.
   
   2. Alternatively, type `exit` in the command box and press Enter.<br>
      Expected: The window closes and the application terminates.

### Adding an employee

1. Add an employee

   1. Prerequisites: The application should be empty. List all employees using the `list e` command.

   2. Test case: `employee n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE ced/2024-10-09`<br>
      Expected: John Doe is added into the list

   3. Test case: `employee n/John@Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE ced/2024-10-09`<br>
      Expected: No employee added, error details shown in the status message

   4. Test case: `employee n/John Doe p/98765a432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE ced/2024-10-09`<br>
      Expected: No employee added, error details shown in the status message

   5. Test case: `employee n/John Doe p/98765432 e/johnd@example-.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE ced/2024-10-09`<br>
      Expected: No employee added, error details shown in the status message

   6. Test case: `employee n/John Doe p/98765432 e/johnd@example.com a/ d/IT r/SWE ced/2024-10-09`<br>
      Expected: No employee added, error details shown in the status message

   7. Test case: `employee n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/ r/SWE ced/2024-10-09`<br>
      Expected: No employee added, error details shown in the status message

   8. Test case: `employee n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/ ced/2024-10-09`<br>
      Expected: No employee added, error details shown in the status message

   9. Test case: `employee n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE ced/01-09-2023`<br>
      Expected: No employee added, error details shown in the status message

2. Adding a duplicate employee

    1. Prerequisites: The employee `employee n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE ced/2024-10-09` should already be added

    2. Test case: `employee n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE ced/2024-10-09`<br>
       Expected: John Doe should not be added since he already exists

    3. Test case: `potential n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE`<br>
       Expected: John Doe should not be added since he already exists in employee

    4. Test case: `employee n/John Doe2 p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE ced/2024-10-09`<br>
       Expected: John Doe2 should be added as he has a different name

### Adding a potential hire

1. Add a potential hire

    1. Prerequisites: The application should be empty. List all potential hires using the `list ph` command.

    2. Test case: `potential n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE`<br>
       Expected: John Doe is added into the list

    3. Test case: `potential n/John@Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE`<br>
       Expected: No potential hire added, error details shown in the status message

    4. Test case: `potential n/John Doe p/98765a432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE`<br>
       Expected: No potential hire added, error details shown in the status message

    5. Test case: `potential n/John Doe p/98765432 e/johnd@example-.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE`<br>
       Expected: No potential hire added, error details shown in the status message

    6. Test case: `potential n/John Doe p/98765432 e/johnd@example.com a/ d/IT r/SWE`<br>
       Expected: No potential hire added, error details shown in the status message

    7. Test case: `potential n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/ r/SWE`<br>
       Expected: No potential hire added, error details shown in the status message

    8. Test case: `potential n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/`<br>
       Expected: No potential hire added, error details shown in the status message

2. Adding a duplicate potential hire

    1. Prerequisites: The potential hire `potential n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE` should already be added

    2. Test case: `potential n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE`<br>
       Expected: John Doe should not be added since he already exists

    3. Test case: `employee n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE ced/2024-10-09`<br>
       Expected: John Doe should not be added since he already exists in potential hire

    4. Test case: `potential n/John Doe2 p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE ced/2024-10-09`<br>
       Expected: John Doe2 should be added as he has a different name

### Editing an employee or potential hire

1. Editing an employee

   1. Prerequisites: An employee has to be added and list all employees using the `list e` command. There should be an employee at index 1.

   2. Test case: `edit 1 n/John Doe3 p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE ced/2024-10-09`<br>
      Expected: The employee at index 1 should be edited into John Doe3

   3. Test case: `edit 1 n/John@Doe`<br>
      Expected: Edit failed, error details shown in the status message

2. Editing a potential hire

   1. Prerequisites: A potential hire has to be added and list all potential hires using the `list ph` command. There should be a potential hire at index 1.

   2. Test case: `edit 1 n/John Doe4 p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE`<br>
      Expected: The employee at index 1 should be edited into John Doe4

   3. Test case: `edit 1 n/John@Doe`<br>
      Expected: Edit failed, error details shown in the status message

   4. Test case: `edit 1 ced/2020-01-01`<br>
      Expected: Edit failed, error details shown in the status message

### Listing the contents of StaffSync

1. Listing all persons

   1. Prerequisites: StaffSync is not empty. Persons have been added using either `employee` or `potential`.

   2. Test case: `list all` <br>
      Expected: All persons in the application are listed regardless of if they are employees or potential hires.

   3. Test case: `list` <br>
      Expected: No change to the current list. Error details shown in the status message.

   4. Test case `list all asdfg` <br>
      Expected: No change to the current list. Error details shown in the status message.

   5. Test case `list asdfg` <br>
      Expected: No change to the current displayed list. Error details shown in the status message.

2. Listing all employees

   1. Prerequisites: StaffSync is not empty. Employees have been added using `employee`.

   2. Test case: `list e` <br>
      Expected: All employees in the application are listed.

   3. Test case: `list` <br>
      Expected: No change to the current list. Error details shown in the status message.

   4. Test case `list e asdfg` <br>
      Expected: No change to the current list. Error details shown in the status message.

   5. Test case `list asdfg` <br>
      Expected: No change to the current displayed list. Error details shown in the status message.

2. Listing all potential hires

   1. Prerequisites: StaffSync is not empty. Potential hires have been added using `potential`.

   2. Test case: `list ph` <br>
      Expected: All potential hires in the application are listed.

   3. Test case: `list` <br>
      Expected: No change to the current list. Error details shown in the status message.

   4. Test case `list ph asdfg` <br>
      Expected: No change to the current list. Error details shown in the status message.

   5. Test case `list asdfg` <br>
      Expected: No change to the current displayed list. Error details shown in the status message.

### Deleting a person

1. Deleting a person while all potential hires/employees are being shown

   1. Prerequisites: List all potential hires/employees using the `list ph` or `list e` command. potential hires/employees persons in the list. At least one potential hire/employee is shown.

   2. Test case: `delete ph 1`<br>
      Expected: First potential hire is deleted from the list, if it is a potential hire. Details of the deleted potential hires/employees shown in the status message. The numbering system is 1 based indexing.

   3. Test case: `delete ph 0`<br>
      Expected: Invalid command format. No potential hire is deleted as the minimum index is 1. A guide on how to use the command will be shown in the status message.

   4. Test case: `Delete E 1`<br>
      Expected: Invalid command format. Error is due to capitalisation of `Delete` and/or `E` instead of `delete` and/or `e`. Capitalisation matters. A guide on how to use the command will be shown in the status message.

   5. Test case: `delete e`<br>
      Expected: Invalid command format. There are missing parameters. A guide on how to use the command will be shown in the status message.

   6. Test case: `delete e 1`<br>
      Expected: If the index is valid but the first index is not an employee, the command is recognised but the action is invalid and a specific status message is shown.

   7. Other incorrect delete commands to try: `delete ph`, `delete e x`, `delete e 1 2` (where x is larger than the list size)<br>
      Expected: Similar to previous points. If the syntax is incorrect, the command is not recognised. Otherwise, the command is recognised but the action is invalid and a specific status message is shown.

2. Deleting a person with no potential hires/employees

   1. Prerequisites: List all potential hires/employees using the `list ph` or `list e` command. No potential hires/employees is shown.

   2. Test case: `delete ph 1`<br>
      Expected: No potential hires/employees are deleted. The error message will show that there are no potential hires/employees to delete.

### Finding a person

1. Finding a person

   1. Test case: `find e n/John`<br>
   Expected: Number of employees with name `John` listed found shown in the status message.
   Displays the list of employees found.

   2. Test case: `find ph p/12345678`<br>
   Expected: Number of potential hires with phone number `12345678` listed found shown in the status message.
   Displays the list of potential hires found.

   3. Test case: `find all e/john@example.com`<br>
   Expected: Number of employees and potential hires with email `john@example.com` listed shown in the status message.
   Displays the list of persons found.

   4. Test case: `find e d/IT r/SWE`<br>
   Expected: Number of employees with department `IT` and role `SWE` listed shown in the status message.
   Displays the list of employees found

   5. Test case: `Find e n/john`, `Find ph p/12345678`, `Find all john@example.com`<br>
   Expected: Unknown command. Error is due to capitalisation of `Find`. Capitalisation of command matters.

   6. Test case: `find e`, `find ph`, `find all`<br>
   Expected: Incorrect command format due to missing keywords. Status message shows the correct usage of Find command.

   7. Test case: `find E n/john`, `find PH n/john`, `find ALL n/john`<br>
   Expected: Incorrect command format due to capitalisation of parameter.
   Status message shows the correct usage of Find command.

   8. Other incorrect find commands to try: `find aLL n/john`, `find pH n/john`, `find a`<br>
   Expected: Similar to previous points. If the format is incorrect, the command is recognised but the action is invalid and a specific status message is shown.

### Demoting an employee

1. Demoting an employee while all employees are being shown

    1. Prerequisites: List all employees using the `list e` command. Employees are in the list.

    2. Test case: `demote 1`<br>
      Expected: First person in the list is demoted to a potential hire. Details of the demoted employee is shown in the status message.

    3. Test case: `demote 0`<br>
     Expected: Invalid index found. No employees demoted. Error details shown in the status message.

    4. Test case: `Demote 1`<br>
     Excepted: Unrecognised command. Error is due to capitalization of `Demote` instead of `demote`. Capitalisation matters.

    5. Test case: `demote`<br>
     Expected: There are missing parameters. A guide on how to use the command will be shown in the status message.

    6. Other incorrect demote commands to try: `demote randomstring`,`demote x`, `demote 1 2` (where x is larger than the list size)<br>
       Expected: Similar to previous points. If the syntax is incorrect, the command is not recognised. Otherwise, the command is recognised but the action is invalid and a specific status message is shown.

2. Demoting a person while no employees are being shown (due to having 0 entries or only potential hire entries)

    1. Test case: `demote 1`<br>
     Expected: No employees are demoted. Error details shown in the status message

### Promoting a potential hire

1. Promoting a potential hire while all potential hires are being shown

    1. Prerequisites: List all potential hire using the `list ph` command. Potential hires are in the list.

    2. Test case: `promote 1 2024-12-20`<br>
       Expected: First person in the list is promoted to an employee. Details of the promoted potential hire is shown in the status message.

    3. Test case: `promote 0 2024-12-20`<br>
       Expected: Invalid index found. No potential hire promoted. Error details shown in the status message.

    4. Test case: `Promote 1 2024-12-20`<br>
       Excepted: Unrecognised command. Error is due to capitalization of `Promote` instead of `promote`. Capitalisation matters.

    5. Test case: `promote 1`<br>
       Expected: There are missing parameters. A guide on how to use the command will be shown in the status message.

    6. Test case: `promote`<br>
      Expected: There are missing parameters. A guide on how to use the command will be shown in the status message.

    7. Test case: `promote 1 20-12-2024`<br>
      Expected: Invalid date format. No potential hire promoted. Error details shown in the status message.

    8. Test case: `promote 1 2024-20-12`<br>
      Expected: Invalid date format. No potential hire promoted. Error details shown in the status message.

    9. Other incorrect demote commands to try: `promote x 2024-12-20`, `promote 1 2`, `promote a b`  (where x is larger than the list size)<br>
       Expected: Similar to previous points. If the syntax is incorrect, the command is not recognised. Otherwise, the command is recognised but the action is invalid and a specific status message is shown.

2. Promoting a person while no potential hires are being shown (due to having 0 entries or only employee entries)

    1. Test case: `promote 1 2024-12-20`<br>
       Expected: No potential hires are promoted. Error details shown in the status message

### Sorting the contents of StaffSync

1. Sorting with all contacts shown

   1. Prerequisites: List all contacts using the `list all` command. The list is not empty and not already in order.

   2. Test case: `sort name`<br>
      Expected: All contacts in StaffSync are sorted by name in ascending order.

   3. Test case: `sort date`<br>
      Expected: Potential hires are placed at the bottom of the list. Employees are sorted by contract end date in ascending order.

   4. Test case: `sort role desc`<br>
      Expected: All contacts in StaffSync are sorted by role in descending order.

   5. Test case: `sort dept asc`<br>
      Expected: All contacts in StaffSync are sorted by department in ascending order.

   6. Test case: `sort`<br>
      Expected: There are missing parameters. A guide on how to use the command will be shown in the status message.

   6. Test case: `sort 1`<br>
      Expected: Invalid parameter. A guide on how to use the command will be shown in the status message.

   6. Test case: `sort name 1`
      Expected: Invalid parameter. A guide on how to use the command will be shown in the status message.

2. Sorting with an already filtered list

   1. Prerequisites: List has been filtered using a `find` or `list` command. The list is not empty and not already in order.

   2. Test case: `sort name`<br>
      Expected: The shown contacts are sorted by name in ascending order. Contacts that were filtered out will not be shown.

   3. Test case: `sort date`<br>
      Expected: The shown employees are sorted by contract end date in ascending order. The shown potential hires will be at the bottom of the list.
      Contacts that were filtered out will not be shown

   4. Test case: `sort role desc`<br>
      Expected: The shown contacts are sorted by role in descending order. Contacts that were filtered out will not be shown.

   5. Test case: `sort dept asc`<br>
      Expected: The shown contacts are sorted by department in ascending order. Contacts that were filtered out will not be shown. 

   4. Test case: `sort`<br>
      Expected: There are missing parameters. A guide on how to use the command will be shown in the status message.

   5. Test case: `sort 1`<br>
      Expected: Invalid parameter. A guide on how to use the command will be shown in the status message.

   6. Test case: `sort name 1`
      Expected: Invalid parameter. A guide on how to use the command will be shown in the status message.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Additional uml diagrams for other cases**

<puml src="diagrams/ListSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `list all` Command" />
Diagram for interactions inside the logic component for the `list all` command.
</box>

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Effort**

### Difficulty Level: 10/10

### Challenges Faced:
1. The original AB3 has many different classes that are all coupled together so making a single change would require an 
understanding of how the different classes work together, making tracing long codes essential

2. JavaFX is weirdly restrictive in the things that can be done, such as when it becomes scrollable and no animations 
allowed, whereas AB3 seem to have done it seamlessly (except animation) for each component.

3. The way that the frontend pulls data from the backend makes it hard to perform operations on the list.

4. Understanding how AB3 code works from the start to implement our features.

5. The way AB3 handled Person felt like it had a lot of dependency on person. I was in charge of adding new fields to 
Person and I had to create the new fields (Department, Role, Contract End Date), update the parser to parse the new 
fields, change the JsonAdaptedPerson to convert a json to the new person, update json test cases to have the new fields,
update personBuilder in the testutil to generate new persons, update TypicalPersons to have new persons with department, 
role, contract end date and update SampleDataUtil.java to have people with the right sample data. This was extremely 
time consuming as just to update Person with new fields you had to update a lot of files with the updated tests.

6. As someone who is inexperienced with Git, there was a very steep learning curve, especially for fixing merge conflicts.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancement**

Team Size: 5

1. Make the scrolling to commands in helpWindow more accurate: The current dropdown jumps to the correct command, but 
the command box is not standardized. We plan to make it more accurate ensuring that it jumps to most command boxes with 
it at the very top of the helpWindow screen.

2. We will separate potential hires and employees into two different columns in the UI. This makes delete more sensible 
as right now, the inclusion of ph or e parameter is not as relevant.

3. We plan to add colours to the help and command output text to improve readability.
