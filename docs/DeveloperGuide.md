---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

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

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

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
* User has the student’s details, i.e. name, gender, contact, classes, subject and email

**MSS**

1. Teacher enters the add student command
2. EduConnect verifies the command inputs
3. EduConnect adds the student’s contact details to the address book
4. EduConnect displays a success message
    
    Use case ends.

**Extensions**

* 2a. Parameter(s) missing in command format
  * 2a1. EduConnect displays an error message, e.g. “Email parameter is missing! Ensure that you give values for /name /gender /contact /classes /subject /email”
  
    Use case ends.

* 2b. Invalid/Unsupported parameter tag used
  * 2b1. EduConnect displays an error message, e.g. “Invalid detail to add! Please use the following options: name, gender, contact, classes, subject, email”

    Use case ends.

* 2c. Invalid argument for a parameter given
  * 2c1. EduConnect displays an error message, e.g. “Name given is invalid! Please give a name that fits: First name and last name (with optional middle names)”

    Use case ends.

* 2d. Existing contact or email given
  * 2d1. EduConnect displays an error message, e.g. “The email boydanderson@gmail.com is already in use”
    
    Use case ends.

**Use case: UC02 - Add a teacher**

**Preconditions**
* User has the teacher’s details, i.e. name, gender, contact, classes, subject and email.

**MSS**
1. Teacher enters the add teacher command
2. EduConnect verifies the command inputs
3. EduConnect adds the teacher’s contact details to the address book
4. EduConnect displays a success message
   
    Use case ends.

**Extensions**
* 2a. Parameter(s) missing in command format
  * 2a1. EduConnect displays an error message, e.g. “Email parameter is missing! Ensure that you give values for /name /gender /contact /classes /subject /email”
    
    Use case ends.

* 2b. Invalid/Unsupported parameter tag used
  * 2b1. EduConnect displays an error message, e.g. “Invalid detail to add! Please use the following options: name, gender, contact, classes, subject, email”
    
    Use case ends.

* 2c. Invalid argument for a parameter given 
  * 2c1. EduConnect displays an error message, e.g. “Name given is invalid! Please give a name that fits: First name and last name (with optional middle names)”
    
    Use case ends.

* 2d. Existing contact or email given
  * 2d1. EduConnect displays an error message, e.g. “The email boydanderson@gmail.com is already in use”
    
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
  * 2a1. EduConnect displays an error message, e.g. “Invalid index provided, enter an integer between [0, 10)”
    
    Use case ends.

**Use case: UC-04 List contacts**

**Preconditions**
* User may optionally specify filter criteria using tags

**MSS**
1. Teacher enters the list command
2. EduConnect displays a list of all contacts in the address book 

    Use case ends.

**Extensions**
* 1a. Teacher specifies some filter criteria using valid tags
  * 1a1. EduConnect displays a list of all contacts that fit that criteria in the address book
    
    Use case ends.
  
* 1b. Teacher uses invalid tags to filter
  * 1b1. EduConnect displays an error message, e.g. “Invalid detail to find with! Please use one of the following options: name, gender, contact, classes, subject, email”
    
    Use case ends.

**Use case: UC-05 Edit a contact**

**Preconditions**
* The address book contains at least one contact
* User knows the index of the contact to be edited

**MSS**
* Teacher enters the edit command
* EduConnect verifies the command inputs
* EduConnect edits the specified contact in the address book
* EduConnect displays a success message 

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
