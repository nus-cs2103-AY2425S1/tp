---
layout: default.md
title: "Developer Guide"
pageNav: 3
---

# VolunTier Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).
* The feature Undo, Redo and History (including the code) was reused with minimal changes from [AddressBook-Level4](https://github.com/se-edu/addressbook-level4.git) ([UG](https://se-education.org/addressbook-level4/UserGuide.html), [DG](https://se-education.org/addressbook-level4/DeveloperGuide.html)).
* The feature Import was implemented using the third-party library [OpenCSV](https://opencsv.sourceforge.net).
* GitHub CoPilot was used by Ivan Jerrick Koh, Li Mingyang and Siah Wee Keat, Evan to write trivial test cases in test files and JavaDocs for trivial methods.

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

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-F08-1a/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-F08-1a/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
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

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-F08-1a/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter`, `ChartDisplay` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-F08-1a/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-F08-1a/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-F08-1a/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

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
1. The command also commits the address book to the model.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.
1. The CommandHistory.add() method is called to add the command string entered to the command history.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:


<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-F08-1a/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object), and all `Lesson` objects (which are contained in a `UniqueLessonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-F08-1a/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### CSV import feature

The ImportCommand class in [`ImportCommand.java`](https://github.com/AY2425S1-CS2103T-F08-1a/tp/blob/master/src/main/java/seedu/address/logic/commands/ImportCommand.java)
allows users to import data from a CSV file and add multiple persons at once to the address book.

The import process reads the CSV file, validates the data, and converts each row into a JsonAdaptedPerson object,
which is then added to the model if it meets specified constraints. If any rows fail validation or contain duplicates,
they are skipped, and detailed feedback is provided to the user.

The `ImportCommand` makes use of the `CsvImport` helper class, which is responsible for parsing and validating CSV data.
Here's how the command works:

Step 1. File Path Input: The command accepts a file path as an argument, specifying the location of the CSV file to import.

Step 2. CSV Parsing: The CsvImport class reads and processes the CSV file, verifying headers, formatting, and constraints.

Step 3. Duplicate and Constraint Validation: Rows with duplicate entries or invalid values (e.g., empty fields for required information) are tracked and not added to the address book.

Step 4. Result Messaging: After import, the command returns a message indicating how many records were successfully added, along with details about any failed rows or duplicates.

The Import mechanism is facilitated by `CsvImport` which is responsible for parsing and validating CSV data.

* `CsvImport#read()` - Reads the CSV file and imports the data into the provided model.

* `CsvImport#validateHeaders(FileReader reader)`: Ensures that the CSV file has the correct headers: name, phone, email, address, hours, tags, role, and subjects.

* `CsvImport#validateCsv(FileReader reader)`: Validates the format of each row, checking that each row has the expected number of columns.

* Tracking Duplicates and Failures: The class maintains duplicates and failedRows lists to track any rows that fail due to duplicate entries or constraint violations.

### View Tutor Chart feature

<puml src="diagrams/VTCSequenceDiagram.puml" width="550" />


The View Tutor Chart (VTC) feature in [`ViewTutorChartCommand`](https://github.com/AY2425S1-CS2103T-F08-1a/tp/blob/master/src/main/java/seedu/address/logic/commands/ViewTutorChartCommand.java)
displays tutors in a bar chart, sorted by their tutoring hours in ascending order.

The ViewTutorChartCommand class retrieves all tutors from the Model, sorts them in ascending order by tutoring hours, and generates a message alongside a bar chart displaying this data in the UI.

<box type="info" seamless>

**Note:** The VTC does not have a parser class, and ViewTutorChart is returned directly to AddressBookParser.

</box>

* Command Execution: Upon calling the vtc command, the execute method of ViewTutorChartCommand is invoked.
* Data Retrieval: The command fetches the list of all Person instances in the model, filters out non-tutor entities, and sorts the list of Tutor objects based on their hours.
* Output Display: A CommandResult object is returned, containing the sorted list of tutors as an array for the UI component to render.


### Undo/redo feature

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

Step 3. The user executes `add \n David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

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

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add \n David …​` command. This is the behavior that most modern desktop applications follow.

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
    * Cons: We must ensure that the implementation of each individual command is correct.

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


* has a need to manage a significant number of volunteer tutoring records
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Manage volunteering _tutor_ and _tutee_ records and scheduling faster than a
typical mouse/GUI-driven app, while efficiently tracking volunteer engagement.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​    | I want to …​                                                   | So that I can…​                                                           |
|----------|------------|----------------------------------------------------------------|---------------------------------------------------------------------------|
| `* * *`  | new user   | see usage instructions                                         | refer to instructions when I forget how to use the App                    |
| `* * *`  | supervisor | view the contact details of _tutors_                           | reach out to them for administrative matters                              |
| `* * *`  | supervisor | view the contact details of _tutees_                           | reach out to them for administrative matters                              |
| `* * *`  | supervisor | add a new tutor and their details                              | keep track of the tutors and their personal information                   |
| `* * *`  | supervisor | add a new tutee and their details                              | keep track of the tutees and their personal information                   |
| `* * *`  | supervisor | add a tutor's tutoring subject                                 | match them with tutees who need help in that subject                      |
| `* * *`  | supervisor | add a tutee's required subject for tutoring                    | match them with a suitable volunteering tutor                             |
| `* * *`  | supervisor | update a tutor's contact information                           | keep the details accurate                                                 |
| `* * *`  | supervisor | update a tutee's contact information                           | keep the details accurate                                                 |
| `* * *`  | supervisor | delete details of an inactive tutor                            | keep the database up-to-date                                              |
| `* * *`  | supervisor | delete details of a former tutee                               | keep the database up-to-date                                              |
| `* * *`  | supervisor | view the address of a tutor                                    | arrange tutoring sessions in locations that are convenient for them       |
| `* * *`  | supervisor | view the address of a tutee                                    | arrange tutoring sessions by tutors located near them                     |
| `* * *`  | supervisor | update a tutor's address                                       | keep the details accurate                                                 |
| `* * *`  | supervisor | update a tutee's address                                       | keep the details accurate                                                 |
| `* * *`  | supervisor | update a tutor's total volunteer hours                         | keep track of the number of hours they have put into volunteering         |
| `* * *`  | supervisor | view a tutor's total volunteer hours                           | track their productivity and contributions over time                      |
| `* * *`  | supervisor | interact with the application's GUI easily                     | enjoy the application and use it intuitively                              |
| `* * *`  | supervisor | find a tutor or tutee by name                                  | locate details of tutors or tutees without going through the entire list  |
| `* * *`  | supervisor | undo the last command executed                                 | easily correct mistakes without re-entering the data manually             |
| `* * *`  | supervisor | redo a previously undone command                               | reapply actions if undone by mistake                                      |
| `* * *`  | supervisor | view the history of commands from most recent to least recent  | track the actions performed and verify changes                            |
| `* *`    | supervisor | filter persons by subject                                      | locate a tutor and tutee easily for matching purposes                     |
| `* *`    | supervisor | view a tutor's total hours in graphs or charts                 | easily analyze tutor engagement and allocate resources efficiently        |
| `*`      | supervisor | add the available timeslots of tutors                          | allow tutoring sessions to be arranged                                    |
| `*`      | supervisor | update the available timeslots of tutors                       | ensure tutors can conduct tutoring sessions as scheduled                  |
| `*`      | supervisor | view the availability of tutors                                | schedule tutoring sessions that matches their availability                |
| `*`      | supervisor | view the total number of tutors and tutees in graphs or charts | identify any inefficient allocation of resources and adjust accordingly   |
| `*`      | supervisor | sort tutors in different orders                                | locate a tutor easily                                                     |
| `*`      | supervisor | read large amount of data at once by importing an excel file   | quickly populate the system without manually entering each data point     |
| `*`      | supervisor | have an autocomplete feature when typing commands              | save time and reduce errors while entering commands or data               |
| `*`      | supervisor | utilize keyboard shortcuts                                     | navigate to a different page quickly and conveniently                     |

### Use cases

(For all use cases below, the **System** is the `VolunTier` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC1 - View list of _persons_**

**MSS**

1.  User requests to list persons
2.  VolunTier shows a list of persons

    Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

**Use case: UC2 - Delete a person**

**MSS**

1.  User <u>views list of persons (UC1)</u>
2.  User requests to delete a specific person in the list
3.  VolunTier deletes the person

    Use case ends.

**Extensions**

* 2a. The given index is invalid.

    * 2a1. VolunTier shows an error message.

      Use case resumes at step 2.

**Use case: UC3 - Add a new person**

**MSS**

1.  User requests to add a new person
2.  VolunTier prompts the user to input the person's details (e.g., name, contact details, address)
3.  User provides the relevant details
4.  VolunTier saves the new person in the system
5.  VolunTier confirms that the person has been successfully added

    Use case ends.

**Extensions**

* 3a. User inputs a name and address that already exists.

    * 3a1. VolunTier notifies the user that the person already exists.

  Use case resumes at step 2.

* 3b. User inputs invalid or incomplete details.

    * 3b1. VolunTier shows an error message indicating the missing or invalid fields.

  Use case resumes at step 2.

**Use case: UC4 - Update a person's details**

**MSS**

1.  User <u>views list of persons (UC1)</u>
2.  User requests to update the details (e.g., contact details, address) of a specific person on the list
3.  VolunTier displays the person's current detailsx
4.  User provides the updated details
5.  VolunTier updates the person's details in the database
6.  VolunTier confirms that the details has been successfully updated

    Use case ends.

**Extensions**

* 2a. The given index is invalid.

    * 2a1. VolunTier shows an error message.

  Use case resumes at step 2.

* 4a. User inputs invalid or incomplete details.

    * 4a1. VolunTier shows an error message specifying the mistake.

  Use case resumes at step 4.


**Use case: UC5 - Import a list of persons**

**MSS**

1.  User requests to import a list of persons from a CSV file
2.  VolunTier prompts the user to input the file path of the CSV file
3.  User provides the file path
4.  VolunTier reads the CSV file and imports the list of persons
5.  VolunTier confirms that the persons have been successfully imported

    Use case ends.

**Extensions**

* 3a. The file path is invalid.

    * 3a1. VolunTier shows an error message.

  Use case resumes at step 2.

* 3b. The format of the file is incorrect.

    * 3b1. VolunTier shows an error message.

* 4a. The CSV file has some invalid entries.

    * 4a1. VolunTier confirms that the valid entries have been successfully imported.

    * 4a2. VolunTier shows an error message specifying the invalid entries.

  Use case resumes at step 2.



### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The command syntax should follow a consistent pattern, so users can easily learn new commands without extensive documentation.
5.  The codebase should be modular, ensuring that future features can be implemented without major refactoring.
6.  The app must ensure data accuracy by validating inputs (e.g., checking valid phone numbers or dates for site visits) before updating the database.


### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Tutor**: A volunteer; A person who provides tutoring services to tutees
* **Tutee**: A client; A person receiving tutoring services from a tutor
* **Person**: A tutor or tutee

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1a. Download the jar file and copy into an empty folder

   1b. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   2a. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2b. Re-launch the app by double-clicking the jar file.<br>
   Expected: The most recent window size and location is retained.

### Adding a person

1. Adding a tutor with no email
   1a. Test case: “addTutor \n John Lim \p 81234578 \a ADDRESS, 123456”
   Expected: No tutor is added. Error is thrown, saying invalid command format.

### Deleting a person

1. Deleting a person while all persons are being shown

   1a. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1b. Test case: `delete 1`<br>
   Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1c. Test case: `delete 0`<br>
   Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1d. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
   Expected: Similar to previous.


### Adding a tutor

1. Adding a tutor with all fields filled

   1a. Test case: `addTutor \n Alice \p 81234567 \e alice@gmail.com \a Block 123, Alice Street, 123456 \h 20 \s math`<br>
   Expected: New contact is added to the list. Details of the new contact shown in the status message.

   1b. Test case: `addTutor \n Bob \p 98765432 \e invalid \a Block 123, Bob Street, 223456` <br>
   Expected: No contact is added. Error details shown in the status message for invalid email.

   1c. Other incorrect add tutor commands to try: `addTutor \x y` (where x is a tag and y is an invalid value for that field)<br>
   Expected: Similar to previous.

### Editing a tutor

1. Editing a tutor with all fields filled

   1a. Prerequisites: Add a tutor with the command `addTutor \n Alice \p 81234567 \e alice@gmail.com \a Block 123, Alice Street, 123456 \h 20 \s math`. <br>
   Add another tutor with the command `addTutor \n Bob \p 98765432 \e bob@gmail.com \a Block 123, Bob Street, 223456 \h 20 \s math`.

   1b. Test case: `edit 1 \n Alicia`<br>
   Expected: Contact is updated in the list. Details of the updated contact shown in the status message.

   1c. Test case: `edit 2 \n Alice`<br>
   Expected: No contact is updated. Error details shown in the status message for duplicate name.

   1d. Other incorrect edit tutor commands to try: `edit 1 \x y` (where x is a tag and y is an invalid value for that field)<br>
   Expected: Similar to previous.


### Find subjects

1. Finding tutors by subject

   1a. Prerequisites: Add a tutor with the command `addTutor \n Alice \p 81234567 \e alice@gmail.com \a Block 123, Alice Street, 123456 \h 20 \s math`. <br>
   Add another tutor with the command `addTutor \n Bob \p 98765432 \e bob@gmail.com \a Block 123, Bob Street, 223456 \h 20 \s science`.

   1b. Test case: `findSubject math`<br>
   Expected: List of tutors (only Alice) with the subject `math` is shown.

   1c. Test case: `findSubject chinese`<br>
   Expected: No tutor is found. Error details shown in the status message.

### Adding a lesson

1. Adding a lesson with all fields filled

   1a. Prerequisites: Clear the list with the command `clear` <br>
   Add a tutor with the command `addTutor \n Alice \p 81234567 \e alice@gmail.com \a Block 123, Alice Street, 123456 \h 20 \s math`. <br>
   Add a tutee with the command `addTutee \n Bob \p 98765432 \e bob@gmail.com \a Block 123, Bob Street, 223456 \h 20 \s math`.

   1b. Test case: `addLesson 1 2 \s math`<br>
   Expected: New lesson is added to the list. Details of the new lesson shown in the status message.

   1c. Test case: `addLesson 1 2 \s math`<br>
   Expected: No lesson is added. Error details shown in the status message for duplicate lessons.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

Team size: 5

1. **Customizable Subject Management**
    - Allow users to add and manage subjects beyond the current options of Math, Science, and English. This feature would provide flexibility for organizations that tutor in other subjects or wish to customize subject names.

2. **Enhanced `find` for Role-Specific Searches**
    - Expand the `find` command to allow searching by role, making it easier to locate either tutors or tutees for creating a lesson. For example, `find \r tutor` would list only tutors. This enhancement streamlines the process of identifying suitable tutors or tutees based.to

3. **Timetable Function for Adding Lessons**
    - Enhance the addLesson feature to allow date and time scheduling, and display a timetable when a person is viewed. The timetable would be updated with lessons scheduled, enabling users to view available slots and assign lessons to specific times, ensuring efficient use of tutors’ and tutees’ time. The timetable could also display conflicts or availability to simplify the scheduling process and avoid overlaps.

4. **Improve `vtc` Chart UI for Large Hour Discrepancies**
    - Adjust the chart scaling in the `vtc` command to accommodate tutors with significantly different hour totals. For example, when one tutor has very high hours (e.g., 100,000) and another has low hours (e.g., 15), the chart should still visibly differentiate each tutor's hours. This enhancement ensures that tutors with fewer hours are accurately represented, avoiding the appearance of zero hours due to chart stretching.

5. **Improve `vtc` Tutor Hours Chart Rendering**
    - Enhance the tutor hours chart in `vtc` to address JavaFX’s limitation with duplicate names. Currently, random index numbers are added to each tutor entry to ensure uniqueness. In future updates, we aim to explore more effective solutions to clearly differentiate tutors with identical names, maintaining clarity in the chart.

6. **Improve Chart UI for Long Names in `vtc` Command**
    - Enhance the chart UI in the `vtc` command to accommodate very long names, ensuring they display correctly without disrupting chart formatting. This could involve wrapping or truncating names within the chart view to maintain readability and avoid overlap or distortion.

7. **Fix Disappearing Text on Hover in Help and File Tabs**
    - Resolve the issue where hovering over and then moving the cursor away from the Help and File tabs in the top UI causes text to disappear. This enhancement will ensure that text remains visible and accessible when interacting with these tabs, improving overall usability and interface consistency.

8. **Person with no subject check hours**
    - When a new tutor or tutee is added, if there are no subjects hours must be 0, or else an error message “person with no subjects cannot start with more than 0 hours” will be shown.
9. **Improve UI in personCard to wrap long fields**
    - Ensure that long fields in the personCard UI (e.g., address, email) wrap correctly to display the full content without truncation. This enhancement will improve readability and user experience when viewing detailed information for tutors and tutees.



