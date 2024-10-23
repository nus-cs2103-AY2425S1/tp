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

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute(find all n/John)` API call as an example.

<puml src="diagrams/FindSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `find all n/John` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

The sequence diagram below illustrates another interaction within the `Logic` component, taking `execute("demote 1")` API call as an example.

<puml src="diagrams/DemoteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `demote 1` Command" />

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

Step 3. The user executes `employee n/David …​` to add a new employee. The `employee` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

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

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `employee n/David …​` command. This is the behavior that most modern desktop applications follow.

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
3.  User requests to delete a specific person in the list
4.  StaffSync deletes the person

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. StaffSync shows an error message.

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

   2. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

3. _{ more test cases …​ }_

### Adding an employee

1. Add an employee

   1. Prerequisites: List all employees using the `list e` command.

   2. Test case: `employee n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE ced/2024-10-09`<br>
      Expected: John Doe is added into the list

   3. Test case: `employee n/John@Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE ced/2024-10-09`<br>
      Expected: No employee added, error details shown in the status message

   4. Test case: `employee n/John Doe p/98765a432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE ced/2024-10-09`<br>
      Expected: No employee added, error details shown in the status message

   5. Test case: `employee n/John Doe p/98765a432 e/johnd@example-.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE ced/2024-10-09`<br>
      Expected: No employee added, error details shown in the status message

   6. Test case: `employee n/John Doe p/98765a432 e/johnd@example.com a/ d/IT r/SWE ced/2024-10-09`<br>
      Expected: No employee added, error details shown in the status message

   7. Test case: `employee n/John Doe p/98765a432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/I@T r/SWE ced/2024-10-09`<br>
      Expected: No employee added, error details shown in the status message

   8. Test case: `employee n/John Doe p/98765a432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SW-E ced/2024-10-09`<br>
      Expected: No employee added, error details shown in the status message

   9. Test case: `employee n/John Doe p/98765a432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE ced/01-09-2023`<br>
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

    1. Prerequisites: List all potential hires using the `list ph` command.

    2. Test case: `potential n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE`<br>
       Expected: John Doe is added into the list

    3. Test case: `potential n/John@Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE`<br>
       Expected: No potential hire added, error details shown in the status message

    4. Test case: `potential n/John Doe p/98765a432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE`<br>
       Expected: No potential hire added, error details shown in the status message

    5. Test case: `potential n/John Doe p/98765a432 e/johnd@example-.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE`<br>
       Expected: No potential hire added, error details shown in the status message

    6. Test case: `potential n/John Doe p/98765a432 e/johnd@example.com a/ d/IT r/SWE`<br>
       Expected: No potential hire added, error details shown in the status message

    7. Test case: `potential n/John Doe p/98765a432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/I@T r/SWE`<br>
       Expected: No potential hire added, error details shown in the status message

    8. Test case: `potential n/John Doe p/98765a432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/S-WE`<br>
       Expected: No potential hire added, error details shown in the status message

2. Adding a duplicate potential hire

    1. Prerequisites: The potential hire `potential n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE ced/2024-10-09` should already be added

    2. Test case: `potential n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE ced/2024-10-09`<br>
       Expected: John Doe should not be added since he already exists

    3. Test case: `employee n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE`<br>
       Expected: John Doe should not be added since he already exists in potential hire

    4. Test case: `potential n/John Doe2 p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 d/IT r/SWE ced/2024-10-09`<br>
       Expected: John Doe2 should be added as he has a different name

### Listing the contents of StaffSync

1. Listing all persons

   1. Prerequisites: StaffSync is not empty. Persons have been added using either `employee` or `potential`.

   2. Test case: `list all` <br>
      Expected: All persons in the application are listed regardless of if they are employees or potential hires.

   3. Test case: `list` <br>
      Expected: No change to the current list. Error details shown in the status message.

   4. Test case `list all asdfg` <br>
      Expected: All persons in the application are listed regardless of if they are employees or potential hires.

   5. Test case `list asdfg` <br>
      Expected: No change to the current displayed list. Error details shown in the status message.

2. Listing all employees

   1. Prerequisites: StaffSync is not empty. Employees have been added using `employee`.

   2. Test case: `list e` <br>
      Expected: All employees in the application are listed.

   3. Test case: `list` <br>
      Expected: No change to the current list. Error details shown in the status message.

   4. Test case `list e asdfg` <br>
      Expected: All employees in the application are listed.

   5. Test case `list asdfg` <br>
      Expected: No change to the current displayed list. Error details shown in the status message.

2. Listing all potential hires

   1. Prerequisites: StaffSync is not empty. Potential hires have been added using `potential`.

   2. Test case: `list ph` <br>
      Expected: All potential hires in the application are listed.

   3. Test case: `list` <br>
      Expected: No change to the current list. Error details shown in the status message.

   4. Test case `list ph asdfg` <br>
      Expected: All potential hires in the application are listed.

   5. Test case `list asdfg` <br>
      Expected: No change to the current displayed list. Error details shown in the status message.

### Deleting a person

1. Deleting a person while all potential hires/employees are being shown

   1. Prerequisites: List all potential hires/employees using the `list ph` or `list e` command. potential hires/employees persons in the list.

   2. Test case: `delete ph 1`<br>
      Expected: First potential hire is deleted from the list. Details of the deleted potential hires/employees shown in the status message. Timestamp in the status bar is updated. The numbering system is 1 based indexing.

   3. Test case: `delete ph 0`<br>
      Expected: No potential hire is deleted. Error details shown in the status message.

   4. Test case: `Delete E 1`<br>
      Expected: Unrecognised command. Error is due to capitalisation of `Delete` and/or `E` instead of `delete` and/or `e`. Capitalisation matters.

   5. Test case: `delete e`<br>
      Expected: There are missing parameters. A guide on how to use the command will be shown in the status message.

   6. Other incorrect delete commands to try: `delete ph`, `delete e x`, `delete e 1 2`, `delete e    1     `,  `...` (where x is larger than the list size)<br>
      Expected: Similar to previous points. If the syntax is incorrect, the command is not recognised. Otherwise, the command is recognised but the action is invalid and a specific status message is shown.

2. Deleting a person with no potential hires/employees

   1. Prerequisites: List all potential hires/employees using the `list ph` or `list e` command. No potential hires/employees is shown.

   2. Test case: `delete ph 1`<br>
      Expected: No potential hires/employees are deleted. The error message will show that there are no potential hires/employees to delete.

### Finding a person

1. Finding a person
   1. Test case: `find all john`<br>
      Expected: Number of people listed found shown in the status message. Displays the list of people found.

   2. Test case: `find ph john`<br>
            Expected: Number of potential hires listed found shown in the status message. Displays the list of potential hires found.

   3. Test case: `find e john`<br>
            Expected: Number of employees listed shown in the status message. Displays the list of employees found.

   4. Test case: `Find all john`, `Find e john`, `Find ph john`<br>
            Expected: Unknown command. Error is due to capitalisation of `Find`. Capitalisation of command matters.

   5. Test case: `find all`, `find e`, `find ph`<br>
      Expected: Incorrect command format. Status message shows the correct usage of Find command.

   6. Test case: `find ALL john`, `find E john`, `find PH john`<br>
            Expected: Incorrect command format. Status message shows the correct usage of Find command.

   7. Other incorrect find commands to try: `find aLL john`, `find pH john`, `find a`, `...`<br>
            Expected: Similar to previous points. If the format is incorrect, the command is recognised but the action is invalid and a specific status message is shown.

### Demoting an employee

1. Demoting an employee while all employees are being shown

   Prerequisites: List all employees using the `list e` command. Employees are in the list.

    1. Test case: `demote 1`<br>
      Expected: First person in the list is demoted to a potential hire. Details of the demoted employee is shown in the status message.

    2. Test case: `demote 0`<br>
     Expected: Invalid index found. No employees demoted. Error details shown in the status message.

    3. Test case: `Demote 1`<br>
     Excepted: Unrecognised command. Error is due to capitalization of `Demote` instead of `demote`. Capitalisation matters.

    4. Test case: `demote`<br>
     Expected: There are missing parameters. A guide on how to use the command will be shown in the status message.

    5. Other incorrect demote commands to try: `demote randomstring`,`demote x`, `demote 1 2` (where x is larger than the list size)<br>
       Expected: Similar to previous points. If the syntax is incorrect, the command is not recognised. Otherwise, the command is recognised but the action is invalid and a specific status message is shown.

2. Demoting a person while no employees are being shown (due to having 0 entries or only potential hire entries)

    1. Test case: `demote 1`<br>
     Expected: No employees are demoted. Error details shown in the status message

### Promoting a potential hire

1. Promoting a potential hire while all potential hires are being shown

   Prerequisites: List all potential hire using the `list ph` command. Potential hires are in the list.

    1. Test case: `promote 1 2024-12-20`<br>
       Expected: First person in the list is promoted to an employee. Details of the promoted potential hire is shown in the status message.

    2. Test case: `promote 0 2024-12-20`<br>
       Expected: Invalid index found. No potential hire promoted. Error details shown in the status message.

    3. Test case: `Promote 1 2024-12-20`<br>
       Excepted: Unrecognised command. Error is due to capitalization of `Promote` instead of `promote`. Capitalisation matters.

    4. Test case: `promote 1`<br>
       Expected: There are missing parameters. A guide on how to use the command will be shown in the status message.

    5. Test case: `promote`<br>
      Expected: There are missing parameters. A guide on how to use the command will be shown in the status message.

    6. Test case: `promote 0 20-12-2024`<br>
      Expected: Invalid date format. No potential hire promoted. Error details shown in the status message.

    7. Test case: `promote 0 2024-20-12`<br>
      Expected: Invalid date format. No potential hire promoted. Error details shown in the status message.

       8. Other incorrect demote commands to try: `promote x 2024-12-20`, `promote 1 2`, `promote a b`  (where x is larger than the list size)<br>
          Expected: Similar to previous points. If the syntax is incorrect, the command is not recognised. Otherwise, the command is recognised but the action is invalid and a specific status message is shown.

2. Promoting a person while no potential hires are being shown (due to having 0 entries or only employee entries)

    1. Test case: `promote 1 2024-12-20`<br>
       Expected: No potential hires are promoted. Error details shown in the status message
