---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* This document is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).

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

Given below is a quick overview of the main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* During shut down, it shuts down the other components and invokes cleanup methods where necessary.

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

#### Navigating Previous Commands with Auto-Fill

Users can seamlessly browse through their previously entered commands using the arrow keys, making it easier to reuse or modify past inputs. This feature helps streamline interactions by reducing repetitive typing and enhancing productivity.

**How it works**:

1. **Detecting Arrow Keys and Retrieving Commands:**
    - Pressing **`UP`** or **`DOWN`** triggers `quickSwitchInputs()`, showing the previous or next command respectively, if available.

2. **Updating the Command Box:**
    - If a command is found, it is displayed in the **`TextField`** using `setText()`.
    - If no command is available, the textField remains unchanged.

The sequence diagram below illustrates how the system processes the arrow key presses:

<img src="images/AutoFillSequenceDiagram.png" width="550" />

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a student).<br>
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

### Mark Attendance feature

#### Current Implementation
The mark feature allows users to mark their student's attendance for a particular tutorial class.

Currently, there are 3 possible states a tutorial class' attendance can be displayed:
1. `PRESENT` (Green) 
2. `ABSENT` (Red)
3. `NOT_TAKEN_PLACE` (Grey)

![TutorialsAttended.png](images/TutorialsAttendedPlaceholder.png)

Each Person in the address book holds a `LinkedHashMap<Tutorial, AttendanceStatus>`. 
Upon using one of the mark attendance commands (eg: `mark`, `unmark`, `reset`), the `AttendanceStatus` 
of the tutorial indices provided will be modified to one of the enumerations accordingly.
![PersonClassDiagram.png](images/PersonClassDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: 
**Note:** The default state for tutorials is `NOT_TAKEN_PLACE`<br>
</div>

### Sort feature

#### Current Implementation

The sort mechanism allows for sorting by one of three fields, the contact's name, contact's student ID and the contact's tutorial attendance. Sorting can also be done in ascending or descending order.

- Name: Sorted by alphabetical order
- Student ID: Sorted by numerical order
- Tutorials: Sorted by attendance status. 
  - Ascending order: `PRESENT`, `ABSENT`, `NOT_TAKEN_PLACE`
  - Descending Order: `ABSENT`, `PRESENT`, `NOT_TAKEN_PLACE`

After parsing a sort command from the UI, the SortCommandParser will then decide on which `Comparator<Person>` to use based on the given prefixes of the command.

The activity diagram below shows how the correct Comparator is selected:

<img src="images/SortCommandActivityDiagram.png" width="400" />

<div markdown="span" class="alert alert-info">:information_source: **Note:** The rake symbol used in "Check for prefixes" is not officially supported by PlantUML. Credits go to an anonymous poster in this <a href="https://forum.plantuml.net/195/is-there-any-support-for-subactivity-or-the-rake-symbol">forum</a>.
</div>

Further details on how prefixes are checked can be seen here in this activity diagram:

<img src="images/SortCommandCheckForPrefixesRake.png" width="800" />


### Wildcard Indexing feature

#### Current Implementation

Having a Wildcard Index (represented by `*`) allows users to execute commands on all contacts. This is especially useful when users want to perform the same action on multiple contacts without having to type out each index individually.

Commands where the `*` character is supported:
- `mark *`
- `unmark *`
- `reset *`

The respective parsers will look out for the `*` character and retrieve a list of contacts to execute the command on. The Wildcard Index is implemented as a singleton accessible through `Index`.

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

**Target user profile**: NUS CS TAs who are adept and prefer CLI to GUI, 
and have to keep track of their tutorial students’ contact and progress.

**Value proposition**: offers a streamlined tool for TAs to efficiently manage student contacts and work progress, 
optimized for users who are fast typers, it’s portable, battery-efficient(light-weight), easy to learn and use.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                              | I want to …​                                                        | So that I can…​                                        |
|----------|------------------------------------------------------|---------------------------------------------------------------------|--------------------------------------------------------|
| `* * *`  | new user                                             | be able to see the list of commands I can use                       | learn the CLI much faster                              |
| `* * *`  | new user                                             | be able to see a sample command                                     | easily follow the format to test out how it works      |
| `* * *`  | user                                                 | add contacts                                                        | save the trouble of remembering every contact          |
| `* * *`  | user                                                 | delete contacts                                                     | remove incorrect or unnecessary entries                |
| `* * *`  | user                                                 | view contacts                                                       | access the contacts' details                           |
| `* * *`  | user                                                 | mark attendance for each tutorial session                           | easily keep a record of student participation          |
| `* *`    | user with many contacts in my address book           | search for a contact by keywords (e.g. name/student ID)             | quickly access their details                           |
| `* *`    | user with a large address book with diverse contacts | be able to sort my contacts using specific attributes               | find contacts based on the attributes more easily      |
| `* *`    | user                                                 | filter students by their performance                                | identify those that need additional help               |
| `* *`    | user                                                 | update a student's contact information                              | maintain accurate records                              |
| `* *`    | user                                                 | flag students that have missed several tutorials                    | follow up on their well-being                          |
| `* *`    | user                                                 | keep track of class participation                                   | award marks accordingly easily                         |
| `* *`    | user                                                 | add additional notes to each contact                                | write important information and remarks I might forget |
| `*`      | user                                                 | add multiple phone numbers to a contact                             | accommodate people who have more than 1 contact number |
| `*`      | user                                                 | assign and manage student project groups                            | track group work and collaboration effectively         |
| `*`      | user                                                 | generate weekly or monthly reports of student attendance and grades | review their progress over time                        |
| `*`      | frequent user                                        | have shorter commands                                               | type faster and execute more commands                  |
| `*`      | user                                                 | track each student’s individual attributes                          | provide personalised feedback                          |
| `*`      | forgetful user                                       | set reminders for upcoming tutorials or deadlines                   | stay on track with my schedule                         |
| `*`      | user                                                 | export attendance and grades data to a CSV file                     | share or analyze student data further                  |
| `*`      | new user                                             | customize the CLI interface                                         | use it according to my personal preferences            |

### Use cases

(For all use cases below, the **System** is `ConTActs` and the **Actor** is the `user`, unless specified otherwise)

**UC01: Add student**

**MSS**
1. User requests to add a student
2. ConTActs adds the student to the list

    Use case ends.

**Extensions**

* 1a. Parameters contain unacceptable values.

    * 1a1. ConTActs shows an error message.

      Use case resumes at step 1.

* 1b. Student already exists.

    * 1b1. ConTActs shows an error message.

      Use case resumes at step 1.

**UC02: Delete student**

**MSS**

1.  User requests to list students
2.  ConTActs shows a list of students
3.  User requests to delete a specific student in the list
4.  ConTActs deletes the student

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. ConTActs shows an error message.

      Use case resumes at step 2.

**UC03: Find students**


**MSS**

1. User requests to list students with parameters e.g. by name, by tag
2. ConTActs displays the list of students

    Use case ends.

**Extensions**

* 1a. Parameters contain unacceptable values.

    * 1a1. ConTActs shows an error message.

      Use case resumes at step 1.

* 2a. The list is empty.
    
  * 2a1. ConTActs shows a message that the list is empty.
    
    Use case ends.

**UC04: Mark/Unmark/Reset student's tutorial**

**MSS**

1.  User requests to list students
2.  ConTActs shows a list of students
3.  User requests to modify the tutorial attendance for a student in the list
4.  ConTActs modifies the student's tutorial attendance accordingly
5.  ConTActs shows the unfiltered list after modification 

    Use case ends.

**Extensions**

* 3a. Parameters contain unacceptable values.

    * 3a1. ConTActs shows an error message.

      Use case resumes at step 3.

* 3b. The list is empty.

    * 3b1. ConTActs shows a message that the list is empty.

      Use case ends.

* 3c. User tries to modify tutorial state to itself e.g. mark someone present as present.

    * 3c1. ConTActs shows a message that the modification is unnecessary.
  
      Use case ends.

* 3d. User uses wildcard(*) index.

    * 3d1. The list is empty.

        ConTActs shows error message that there are no students to modify.
  
        Use case resumes at step 5.

    * 3d2. List is not empty.

        ConTActs will modify all tutorials for every student.

**UC05: Edit student details**

**MSS**

1.  User requests to list students.
2.  ConTActs shows a list of students. 
3.  User requests to edit the details of a student in the list by specifying the index and the fields to update. 
4.  ConTActs verifies that the index is valid and that the fields provided contain acceptable values. 
5.  ConTActs modifies the student’s details as requested. 
6.  ConTActs shows the updated unfiltered list after modification.

    Use case ends.

**Extensions**

* 3a.  The index provided does not correspond to any student in the list.

    * 3a1. ConTActs shows an error message indicating the index is invalid.

      Use case resumes at step 3.

* 3b. No fields are specified for editing.

    * 3b1. ConTActs shows an error message indicating that at least one field must be provided for editing.

      Use case resumes at step 3.

* 3c. The provided field values are invalid (e.g., email without an @ symbol, phone with non-numeric characters).

    * 3c1. ConTActs shows an error message indicating which fields contain unacceptable values.

      Use case resumes at step 3.

* 3d. The updated values do not result in any actual changes (e.g., trying to set the same phone number or email as before).

    * 3d1. ConTActs shows a message that the modification is unnecessary because the edited fields are identical to the old values.

      Use case resumes at step 3.

* 3e. The edited student would duplicate an existing student in the address book (e.g., same student ID or email as another student).

    * 3e1. ConTActs shows an error message indicating that such a student already exists.

      Use case resumes at step 3.

**UC06: Sort student list**

**MSS**

1.  User requests to list students.
2.  ConTActs shows a list of students.
3.  User requests to sort the list by specifying the order and field to sort by.
4.  ConTActs shows the list of students in sorted order accordingly.

    Use case ends.

**Extensions**

* 3a.  No order or field is provided.

    * 3a1. ConTActs shows an error message indicating the command format is incorrect.

      Use case resumes at step 3.

* 3b.  The order provided is not an acceptable value (i.e: 1 or -1).
  
    * 3b1. ConTActs shows an error message indicating the order is invalid.

      Use case resumes at step 3.

* 3c.  The field provided is not an acceptable value.

    * 3c1. ConTActs shows an error message indicating the field to sort by is invalid.

      Use case resumes at step 3.

* 3d.  More than 1 field to sort by is provided.

    * 3d1. ConTActs shows an error message indicating the sort command takes only 1 field to sort by.

      Use case resumes at step 3.
  
### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 contacts without a noticeable sluggishness in performance for typical usage.
3.  The user interface should be optimized for CLI interaction. Users should be able to accomplish tasks more efficiently using commands than using the mouse.
4.  Should provide clear, informative error messages in the event of invalid inputs or commands to provide sufficient guidance on how to correct it.
5.  ConTActs' data, such as student details and tutorial attendance, should be stored in a durable format that supports easy retrieval.
6. Should work without internet connection.
7. All commands should run under 2 seconds.
8. Student data stored should be secure and adhere to local laws such as PDPA.

### Glossary

* **TA**: Teaching Assistant, a student who provides teaching support for a course.
* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **CLI**: Command Line Interface, a text-based user interface used to interact with the app by typing commands.
* **Contact**: A student's information stored in the system, including name, NUS Net ID, phone number, email address and tutorial attendance.
* **NUS Net ID**: A unique username and identifier for NUS students associated with most of NUS platforms such as Canvas, EduRec. It should follow the format “EXXXXXXX” (capital letter 'E' followed by seven digits).
* **Invalid Commands**: Commands entered into the CLI that do not match any recognized system commands.
* **Invalid Inputs**: Data provided by the user that does not meet the required format or validation criteria for the specific command.
* **Corrupted Data**: Data stored in the wrong format or with missing/invalid mandatory information.

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

### Deleting a contact

1. Deleting a contact while all contacts are being shown

   1. Prerequisites: List all contacts using the `list` command. Multiple contacts in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No contact is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Editing attendance status
Prerequisites: Ensure more than one contact is present. These tests can and should be done sequentially.
1. Marking attendance as present for a contact
   1. Test case: `mark 1 tut/1`<br>
   Expected: First contact's tutorial box 1 turns green.
   
   2. Test case: `mark 1 tut/1-2`<br>
   Expected: First contact's tutorial box 1 remains green and box 2 turns green.
   
   3. Test case: `unmark 2 tut/[1,3]`<br>
   Expected: Second contact's tutorial box 1 and 3 turns red.
   
   4. Test case: `reset * tut/1`<br>
   Expected: All contact's tutorial box 1 turns grey. First contact's tutorial box 2 remains green and second contact's tutorial box 3 remains red.
   
   5. Test case: `mark 1 tut/2`<br>
   Expected: No change will occur. Details shown in the status message.

### Saving data

1. Dealing with missing data files

   1. Steps to Simulate
      1. Locate the directory where you saved the `ConTActs.jar` file.
      2. Locate the `data` folder in this directory.
      3. Locate the file named `addressbook.json`.
      4. Delete or temporarily move the data file to simulate a missing file.
      5. Launch the application once more.
   2. Expected Behavior
      1. The application should detect the missing data file.
      2. A new data file with sample data should be created upon launching the application.

2. Dealing with corrupted data files

   1. Steps to Simulate
      1. Locate the directory where you saved the `ConTActs.jar` file.
      2. Locate the `data` folder in this directory.
      3. Locate the file named `addressbook.json`.
      4. Modify the file to make it invalid JSON by deleting a closing bracket `}`.
      5. Save the file and close the editor.
      6. Launch the application once more.
   2. Expected Behavior
       1. The application should detect the file is corrupted.
       2. Application launches with no contacts displayed in the GUI.

---
## **Appendix: Planned Enhancements**

Team size: 5

1. We plan to support the use of special characters within names (e.g: `/`, `-`) to allow for TAs to input names as precisely as needed.


2. We plan to support to addition of multiple duplicate names into ConTActs. i.e: 2 different people can have the same name. 
This is sensible as names are non-unique, and we have other unique fields such as STUDENT_ID to check for duplicates.


3. We plan to make the EMAIL field case-insensitive.


4. We plan to increase the flexibility of email validation, for example, to allow for emails with local-part ending with special characters as they are valid emails. (e.g: user-@example.com)


5. We plan to add a warning prompt for `clear` command as it is potentially destructive if accidentally typed.


6. We plan to allow TAs to flexibly change the number of tutorials for each contact. Current implementation assumes there to be exactly 12 tutorials, which could be visually unpleasant for mods with less than 12 tutorials, and unusable for mods with more than 12 tutorials.


7. We plan to allow INDEX to accept list and range, just like TUTORIAL for `mark`/`unmark`/`reset` commands, to further improve efficiency of marking attendance.   


8. We plan to add an option that TAs can enable/disable to choose whether they would like the currently visible contact list to refresh after each list-altering command. For example,
the filtered list displayed after using the `find` command will not reset after using `add`, depending on user preference.


9. We plan to improve the `find` command such that it displays the matching contacts in a more useful manner. 
Current implementation displays matching contacts by index (i.e: chronologically when they were added to ConTActs), resulting in exact matches potentially being shown at the bottom rather than at the top because they were added later. 
This is makes the `find` command meaningless for large data sets.
Possible improvement is to implement a "match score" to rank the contacts by, and display them in that order instead.

10. We plan to allow sorting by more than 1 fields (e.g: by name AND tutorial), to further improve the usefulness of sort.

---

## **Appendix: Effort**

### Difficulty Level
The beginning of the project was difficult since the AB3 implementation was rather unknown and required some time to trace through to fully understand.
However, once we managed to get an intuitive understanding of the AB3 code, the modification of existing features and addition of new ones were pretty straightforward.

### Challenges Faced
1. Data Storing: This required extensive planning to ensure we don't break currently existing commands, especially when adding the 3-state tutorials.
2. JavaFX GUI: The lack of familiarity with JavaFX required additional time to fully learn and utilise it in our project.
3. Testing and CodeCov: Ensuring that testcases used were effective and efficient, whilst covering as much of the code as possible was a cumbersome task.
4. Exception Handling: Finding every possible exception, and throwing the appropriate meaningful error message required time and testing.

### Effort Required
1. Understanding AB3: Time was needed to understand the inner working of AB3, so that we can modify it with minimal errors.
2. Implementing Features: The logic and code needed for each new feature required deep understanding of the program flow and the dependent classes.
3. Overall Design: Extensive planning for each new feature was required to ensure that it works, whilst minimising coupling and dependency.
4. Testing: Testcases were added for each new line of code to improve the likelihood of catching bugs. Alpha testing was also done to catch as many bugs as possible.
5. Documentation: Ensuring that documentation stayed consistent with the functionality of the app, whilst written in an easily understood manner.

### Achievements
1. Graceful Exception Handling: Program is able to catch different types of invalid inputs without crashing on the user.
2. Smooth Performance: Program is able to handle all commands quickly and smoothly.

### Code Reuse
1. AB3 Storage Component
2. AB3 UI Component
3. AB3 Logic and Parsing Component
4. Overall AB3 Architecture
5. Rake SVG for PlantUML from this [forum](https://forum.plantuml.net/195/is-there-any-support-for-subactivity-or-the-rake-symbol)
