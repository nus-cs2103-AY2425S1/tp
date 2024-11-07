---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

EduConnect was developed based on the project codebase of Address Book 3 (AB3). This project builds upon the foundational architecture and core functionalities established in AB3, while introducing new features and customizations tailored for managing student and teacher data.

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

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

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

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

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Undo/redo feature

#### Implementation

The undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. It also stores a `predicateStateList` for the Predicates used for each state. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `executeCommand` method in the Command class calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `student /name David …​` to add a new person. The `executeCommand` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command indirectly uses `VersionedAddressBook#undo()` which checks if the `currentStatePointer` is at index 0. If so, it will return an error to the user rather than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command indirectly uses `VersionedAddressBook#redo()` to check if `currentStatePointer` is at index `addressBookStateList.size() - 1`. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `help`. Commands that do not modify the address book, such as `help`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `student /name David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />


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
School teachers looking to manage the details of both students and other teachers
* needs to keep track of many students at a time
* can type fast
* prefers to type in a command line interface
* uses a small set of commands
* makes frequent typos but hates to backtrack with backspace
* likes to have an autocomplete suggestion

**Value proposition**: EduConnect will provide a faster and more convenient way to manage details of students and teachers than other apps.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority​ | As a …​                   | I want to …​                                                                                                  | So that I can…​                                                       |
|-----------|---------------------------|---------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------|
| `* * *`   | new user                  | see usage instructions                                                                                        | refer to instructions when I forget how to use the App                |
| `* * *`   | teacher                   | add a new student and their details                                                                           |                                                                       |
| `* * *`   | teacher                   | remove/delete a student from the app                                                                          | remove entries that I no longer need                                  |
| `* * *`   | teacher                   | add a new teacher and their details                                                                           | find information on other teachers if need be                         |
| `* * *`   | teacher                   | remove/delete a teacher from the app                                                                          | remove entries that I no longer need                                  |
| `* * *`   | teacher                   | edit or update information of a student                                                                       | keep the data stored accurate and up to date                          |
| `* *`     | teacher                   | clear all student/class data from the previous semester/year                                                  | reset the app for the new semester/year                               |
| `* *`     | teacher                   | search for students by some partial information                                                               | quickly find a list of students without recalling specific details    |
| `* *`     | teacher                   | tag and filter students based on specific attributes                                                          | access relevant groups without manually searching every time          |
| `*`       | teacher new to EduConnect | see a sample version of how the app will look with sample data                                                | better visualise the workflow or how the app will work or look        |
| `*`       | teacher who makes typos   | have flexibility in typos for the commands                                                                    | continue writing commands without needing to rewrite or backspace     |
| `*`       | teacher familiar with CLI | use shortcuts or linux-like commands                                                                          | enter commands faster and more familiar to me                         |
| `*`       | teacher                   | export student list and contact information to various formats                                                | share and archive data easily for administrative purposes             |
| `*`       | teacher                   | switch between different classes using keybinds                                                               | navigate between different groups of students efficiently             |
| `*`       | teacher                   | have an undo/redo command for recent actions                                                                  | quickly correct mistakes or revert changes                            |
| `*`       | teacher                   | create custom command aliases for frequently used commands                                                    | streamline my workflow and reduce the number of keystrokes needed     |
| `*`       | teacher                   | have built-in calendar integration that links student info with important dates (e.g. parent meetings, exams) | easily access all relevant student data when preparing for key events |
| `*`       | teacher                   | quickly generate printable class rosters with selected details (e.g. names, contact info, emergency contacts) | have a physical copy for field trips or offline use                   |
| `*`       | teacher                   | have a dark mode or customizable themes for the interface                                                     | reduce eye strain while managing student data at night                |
| `*`       | teacher                   | use natural language input for commands (e.g. "add student John Doe to class 5a")                             | enter commands more intuitively without memorising specific syntax    |
| `*`       | teacher                   | group students based on customizable criteria (e.g. performance level, participation)                         | easily view and manage students with similar needs                    |
| `*`       | teacher                   | get the contact details of a frequently searched contact                                                      | quickly use it to contact a student                                   |
| `*`       | teacher                   | systematically add the contact details of twins who share similar details                                     | have a smaller chance of having errors                                |

### Use cases

(For all use cases below, the System is EduConnect and the Actor is a Teacher (User), unless specified otherwise)

**Use case: UC01 - Add a student**

**Preconditions**
* User has the student’s details, i.e. name, gender, contact, classes, subject, email, address, attendance, next of kin and emergency contact

**MSS**

1. Teacher enters the add student command
2. EduConnect verifies the command inputs
3. EduConnect adds the student’s contact details to the address book
4. EduConnect displays a success message
    
    Use case ends.

**Extensions**

* 2a. Required parameter(s) missing in command format
  * 2a1. EduConnect displays an error message.
  
    Use case ends.

* 2b. Invalid/Unsupported parameter tag used
  * 2b1. EduConnect displays an error message.

    Use case ends.

* 2c. Invalid argument for a parameter given
  * 2c1. EduConnect displays an error message, e.g. “Names should only contain alphanumeric characters and spaces, and it should not be blank”

    Use case ends.

* 2d. Existing contact or email given
  * 2d1. EduConnect displays an error message, e.g. “This student already exists in the address book”
    
    Use case ends.

**Use case: UC02 - Add a teacher**

**Preconditions**
* User has the teacher’s details, i.e. name, gender, contact, classes, subject, email and address.

**MSS**
1. Teacher enters the add teacher command
2. EduConnect verifies the command inputs
3. EduConnect adds the teacher’s contact details to the address book
4. EduConnect displays a success message
   
    Use case ends.

**Extensions**
* 2a. Required parameter(s) missing in command format
  * 2a1. EduConnect displays an error message.
    
    Use case ends.

* 2b. Invalid/Unsupported parameter tag used
  * 2b1. EduConnect displays an error message
    
    Use case ends.

* 2c. Invalid argument for a parameter given 
  * 2c1. EduConnect displays an error message, e.g. “Names should only contain alphanumeric characters and spaces, and it should not be blank”
    
    Use case ends.

* 2d. Existing contact or email given
  * 2d1. EduConnect displays an error message, e.g. “This student already exists in the address book”
    
    Use case ends.

**Use case: UC-03 Delete a contact**

**Preconditions**
* The address book contains at least one contact 
* User knows the index of the contact to be deleted

**MSS**
1. Teacher enters the delete contact command
2. EduConnect verifies the index validity
3. EduConnect deletes the contact from the address book
4. EduConnect displays a success message
   
    Use case ends.

**Extensions**
* 2a. Invalid index provided
  * 2a1. EduConnect displays an error message, e.g. “The person index provided is invalid: 2”
    
    Use case ends.

**Use case: UC-04 List contacts**

**MSS**
1. Teacher enters the list command
2. EduConnect displays a list of all contacts in the address book 

    Use case ends.

**Use case: UC-05 Edit a contact**

**Preconditions**
* The address book contains at least one contact
* User knows the index of the contact to be edited

**MSS**
1. Teacher enters the edit command
2. EduConnect verifies the command inputs
3. EduConnect edits the specified contact in the address book
4. EduConnect displays a success message 

    Use case ends.

**Extensions**
* 2a. Invalid index provided
  * 2a1. EduConnect displays an error message, e.g. “Invalid index provided, enter an integer between [0, 10)”

    Use case ends.

* 2b. Invalid/Unsupported parameter tag used
  * 2b1. EduConnect displays an error message, e.g. “Invalid detail to edit! Please use the following options: name, gender, contact, classes, subject, email”
  
    Use case ends.
  
* 2c. Invalid new argument for a parameter given
  * 2c1. EduConnect displays an error message, e.g. “New name given is invalid! Please give a name that fits: First name and last name (with optional middle names)”
    
    Use case ends.

* 2d. Duplicate contact or email provided
  * 2d1. EduConnect displays an error message, e.g. “The email boydanderson@gmail.com is already in use”
  
    Use case ends.

**Use case: UC-06 Clear**

**Preconditions**
* User may optionally specify the occupation (teacher or student) and tags to filter which contacts are cleared

**MSS**
1. Teacher enters the clear command
2. EduConnect clears all contacts in the address book
3. EduConnect displays a success message

    Use case ends.

**Extensions**
* 1a. Teacher specifies an occupation to clear
  * 1a1. EduConnect clears all contacts of that occupation in the address book
  
    Use case ends.

* 1b. Teacher specifies an invalid occupation
  * 1b1. EduConnect displays an error message, e.g. “Invalid occupation to clear, please specify either teacher or student”
  
    Use case ends.

* 1c. Teacher specifies a tag to clear 
  * 1c1. EduConnect clears all contacts with that tag value in the address book

    Use case ends.

* 1d. Teacher specifies an invalid tag
  * 1d1. EduConnect displays an error message, e.g. “Invalid detail to clear with! Please use one of the following options: name, gender, contact, classes, subject, email”

    Use case ends.

* 1e. Teacher specifies an occupation or tag with no matching contacts
  * 1e1. EduConnect displays a warning, e.g. “No contacts matching the specified filter, no changes made to address book”

    Use case ends.

**Use case: UC-07 Find**

**MSS**
1. Teacher enters the find command with some specific criteria
2. EduConnect displays a list of all persons that fit that criteria in the address book

   Use case ends.

**Extensions**
* 1a. Teacher specifies some filter criteria using valid tags
    * 1a1. EduConnect displays a list of all contacts that fit that criteria in the address book

      Use case ends.

* 1b. Teacher uses invalid tags to filter
    * 1b1. EduConnect displays an error message, e.g. “Invalid detail to find with! Please use one of the following options: name, gender, contact, classes, subject, email”

      Use case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The system should handle errors gracefully, providing clear error messages for invalid inputs or operations.
5.  The system should be platform-independent and capable of running on any operating system that supports Java, ensuring that users across different platforms can use the program.
6.  The code should be well-documented, enabling developers to maintain and upgrade the system efficiently.
7.  The system should be modular, allowing for easy extension in the future (e.g., adding new fields for contacts or new types of commands).

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Subject**: The subject the student / teacher is taking
* **Class**: The class the student / teacher is taking
* **Command Line Interface (CLI)**: Text-based user interface that allows the user to input
* **Next-of-Kin**: The contact of the closest relative of the current contact
* **Database**: An organized collection of structured information or data, typically stored electronically.
* **GUI (Graphical User Interface)**: A visual user interface that allows users to interact with an application through graphical elements like buttons, icons, and menus, instead of typing commands.
* **Encryption**: The process of converting plain text data into a coded format to prevent unauthorized access.
* **Version Control**: A system that records changes to a file or set of files over time, allowing developers to track and manage revisions.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Open the “Command Prompt” (for Windows) or “Terminal” (for Mac/Linux).
   2. Type `cd` followed by the folder location where you saved the EduConnect file.
   3. Type and enter the command `java -jar educonnect.jar`
   
        Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by following the instructions from 1ii onwards.<br>
       Expected: The most recent window size and location is retained.

### Adding a Student
1. Adding a Student
    1. Prerequisities: There is no existing person (student or teacher) in EduConnect with the same contact or email as the student we're adding.
    1. Test case: `student /name John Doe /gender male /contact 98765432 /email johnd@example.com /address 311, Clementi Ave 2, #02-25 /subject Physics /classes 7A,7B /attendance 0 /nok Bob Doe /emergency 87654321`
   
        **Expected**: A student is added to EduConnect with the specified details. A new blue colored card is added to the GUI with the student's details.
   2. Test case: `student` (missing required fields like name, contact, etc. )
   
        **Expected**: No student is added. An error is thrown indicating the command given has an invalid format.
   3. Other incorrect `student` commands to try:
      - `student /name John Doe` (missing other required fields)
      - `student /name John Doe /contact 12345 ...` (invalid phone format)
   
      **Expected**: Similar to previous case. No student is added. If all required fields are provided but an invalid format was used, specific error details for that will be given. For example, "Phone numbers should only contain numbers, and it should be exactly 8 digits long".

### Adding a Teacher
1. Adding a Teacher
    1. Prerequisities: There is no existing person (student or teacher) in EduConnect with the same contact or email as the teacher we're adding.
    1. Test case: `teacher /name John Doe /gender male /contact 98765432 /email johnd@example.com /address 311, Clementi Ave 2, #02-25 /subject Physics /classes 7A,7B`

       **Expected**: A teacher is added to EduConnect with the specified details. A new green colored card is added to the GUI with the teacher's details.
    2. Test case: `teacher` (missing required fields like name, contact, etc. )

       **Expected**: No teacher is added. An error is thrown indicating the command given has an invalid format.
    3. Other incorrect `teacher` commands to try:
        - `teacher /name John Doe` (missing other required fields)
        - `teacher /name John Doe /contact 12345 ...` (invalid phone format)

       **Expected**: Similar to previous case. No teacher is added. If all required fields are provided but an invalid format was used, specific error details for that will be given. For example, "Phone numbers should only contain numbers, and it should be exactly 8 digits long".

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      **Expected**: First contact is deleted from the list. Details of the deleted contact shown in the status message.

   1. Test case: `delete 0`<br>
      **Expected**: No person is deleted. Error details shown in the status message.

   1. Other incorrect delete commands to try: `delete`, `delete x` (where x is larger than the list size, negative or a non-integer)<br>
      **Expected**: Similar to previous.
   
   1. Test case: `delete 1 2`<br>
        **Expected**: First and second contact is deleted from the list. Details of the deleted contacts are shown in the status message.

1. Deleting a person while only some persons are shown
    1. Prerequisites: Possibly only some persons are shown, using the `find` command. Not all persons may be shown.
   2. Test case: `delete 1`<br>
   **Expected**: First contact in the filtered list is deleted from EduConnect. Details of the deleted contact shown in the status message.
   3. Similar test cases as before, but now relative to the current filtered shown list.

### Editing a person
1. Editing a person while all persons are shown
    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.
   2. Test case: `edit 1 /name Bob`<br>
        **Expected**: First contact's name is edited to "Bob". Details of the edited contact shown in the status message.
   3. Test case: `edit 3 /name Bob /contact 12345678`<br>
        **Expected**: Third contact's name is edited to "Bob" and contact number is edited to 12345678. Details of the edited contact shown in the status message.
   4. Test case: `edit 0 /name Bob`<br>
        **Expected**: No person is edited. Error details shown in the status message. 
   5. Other incorrect edit commands to try: `edit`, `edit x` (where x is larger than the list size, negative or non-integer), `edit 1 /contact 111` (invalid phone format)<br>
        **Expected**: Similar to previous case. No person is edited. If an invalid format was used, specific error details for that will be given. For example, "Phone numbers should only contain numbers, and it should be exactly 8 digits long".

### Clearing EduConnect
1. Clearing data from EduConnect with at least one person
    1. Prerequisites: There exists at least one person in EduConnect.
   2. Test case: `clear`<br>
        **Expected**: All contacts are cleared from EduConnect.
   3. Test case: `clear /name John`<br>
        **Expected**: All contacts that have "John" in their name will be cleared from EduConnect. If there are no existing contacts with "John" in their name, an error will be thrown.
   4. Test case: `clear /name John Doe`<br>
        **Expected**: All contacts that have _either_ "John" _or_ "Doe" in their name will be cleared from EduConnect. As before, if there are no existing contacts that fit that criteria, an error will be thrown.
   5. Test case: `clear /name John /subject Physics`<br>
        **Expected**: All contacts that either have "John" in their name or "Physics" in their subjects will be cleared from EduConnect. As before, if there are no existing contacts that fit that criteria, an error will be thrown.
   6. Test case: `clear /x` (where x is an invalid TAG)<br>
        **Expected**: No contacts are deleted. Error details shown in the status message.

### Listing
1. Listing all persons in EduConnect
    1. Test case: `list`<br>
    **Expected**: All persons in EduConnect are listed out in the GUI.
   2. Test case: `list x` (where x is some other random input)<br>
        **Expected**: Same as case before. Random input `x` is ignored.

### Sorting
1. Sorting EduConnect while all persons are shown
    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.
   2. Test case: `sort name`<br>
        **Expected**: All the persons in EduConnect are sorted by their name in alphabetical order.
   3. Test case: `sort subject`<br>
      **Expected**: All the persons in EduConnect are sorted by their subjects in alphabetical order.
   4. Test case: `sort class`<br>
      **Expected**: All the persons in EduConnect are sorted by their classes in alphabetical order.
   5. Test case: `sort attendance`<br>
      **Expected**: All the persons in EduConnect are sorted by their attendance in alphabetical order. Teachers (who don't have attendance) are pushed to the end.
   6. Test case: `sort x` (where x is some random input that isn't any of the earlier test cases)<br>
        **Expected**: EduConnect is not sorted. Error details shown in the status message.
2. Sorting EduConnect while only some persons are shown
   1. Prerequisites: Possibly only some persons are shown, using the `find` command. Not all persons may be shown.
   2. Similar test cases as before but only the filtered persons are sorted and shown in the GUI.

### Finding people in EduConnect
1. Finding people in EduConnect while all persons are shown
   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.
   2. Test case: `find /name John`<br>
   **Expected**: All persons who have "John" in their name are shown in the GUI and the rest are hidden.
   3. Test case: `find /name John Doe`<br>
   **Expected**: All persons who have either John or Doe in their name are shown in the GUI and the rest are hidden.
   4. Test case: `find /name John /subject Physics`<br>
   **Expected**: All persons who have either John in their name or Physics among their subjects are shown in the GUI and the rest are hidden.
   5. Test case: `find`<br>
   **Expected**: EduConnect remains the same. Error details shown in the status message.
   6. Other incorrect `find` commands to try:
      - `find John` (where the command is missing a TAG to find with)
   
      **Expected**: Similar to previous case. EduConnect remains the same. Error details shown in the status message.

2. Finding people in EduConnect while only some persons are shown
    1. Prerequisites: Possibly only some persons are shown, using the `find` command. Not all persons may be shown.
   2. Similar test cases as before. `find` does not take into account the current state of EduConnect, i.e. if a person isn't currently displayed on the GUI but fits the next `find` command's criteria, it will still be displayed.

### Undoing
1. Undoing a previous command
   1. Prerequisites: At least one undo-able command has been executed in EduConnect.
   2. Test case: `undo` <br>
   **Expected**: The previous command is undone and EduConnect returns to its previous state.
   3. Test case: `undo x` (where x is some random input)<br>
   **Expected**: Similar to previous case. The random input x is ignored.

### Redoing
1. Redoing a previously undone command
    1. Prerequisites: At least one command has been undone in EduConnect.
   2. Test case: `redo`<br>
   **Expected**: The previously undone command is redone and EduConnect returns to its previously original state.
   3. Test case: `redo x` (where x is some random input)<br>
      **Expected**: Similar to previous case. The random input x is ignored.

### Marking Attendance
1. Marking attendance in EduConnect
   1. Prerequisites: None.
   2. Test case: `mark`<br>
   **Expected**: All students in EduConnect have their attendance incremented by 1.
   3. Test case: `mark x` (where x is some random input)<br>
   **Expected**: Similar to previous case. The random input x is ignored.

### Unmarking Attendance
1. Unmarking attendance while all persons are shown
   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.
   2. Test case: `unmark 1`<br>
   **Expected**: The student at index 1 has their attendance decremented by 1.
   3. Test case: `unmark 0` <br>
   **Expected**: No students' attendance are affected. Error details shown in the status message.
   4. Other incorrect `unmark` commands to try:
      - `unmark`, `unmark x` (where x is larger than the list size, negative or a non-integer)
   
      **Expected**: Similar to previous case. Error details shown in the status message.
   5. Test case: `unmark 1` (where index 1 is a teacher) <br>
   **Expected**: Similar to previous case. Error details shown in the status message.

2. Unmarking attendance while only some persons are shown
    1. Prerequisites: Possibly only some persons are shown, using the find command. Not all persons may be shown.
   2. Similar test cases as before, but now relative to the current filtered shown list.

### Resetting Attendance
1. Resetting attendance while all persons are shown
    1. Prerequisites: List all persons using the list command. Multiple persons in the list.
   2. Test case: `resetAttendance`<br>
   **Expected**: All students' attendance are reset to 0.
   3. Test case: `resetAttendance x` (where x is some random input)<br>
   **Expected**: Similar to previous case. The random input x is ignored.
2. Resetting attendance while only some persons are shown
   1. Prerequisites: Possibly only some persons are shown, using the find command. Not all persons may be shown.
   2. Similar test cases as before. `resetAttendance` does not take into account the current state of EduConnect, i.e. if a student isn't currently displayed on the GUI and `resetAttendance` is executed, their attendance is also reset to 0.

### Help
1. Executing the `help` command
   1. Prerequisites: None.
   2. Test case: `help`<br>
   **Expected**: A separate window is opened with a URL to the user guide. Users can click "Copy URL" to copy the URL to their clipboard.
   3. Test case: `help x` (where x is some random input)<br>
   **Expected**: Similar to previous case. The random input x is ignored.

### Exiting EduConnect
1. Executing the `exit` command
    1. Prerequisites: None.
    2. Test case: `exit`<br>
       **Expected**: The current EduConnect window is closed.
    3. Test case: `exit x` (where x is some random input)<br>
       **Expected**: Similar to previous case. The random input x is ignored.
