---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# TutorEase Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org)_.

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The **_Architecture Diagram_** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103-F11-2/tp/blob/master/src/main/java/tutorease/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103-F11-2/tp/blob/master/src/main/java/tutorease/address/MainApp.java)) is in charge of the app launch and shut down.

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

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103-F11-2/tp/blob/master/src/main/java/tutorease/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `LessonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103-F11-2/tp/blob/master/src/main/java/tutorease/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103-F11-2/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` and `Lesson` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("contact delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `contact delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteContactCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.

</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `TutorEaseParser` object which in turn creates a parser that matches the command (e.g., `DeleteContactCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteContactCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:

* When called upon to parse a user command, the `TutorEaseParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddContactCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddContactCommand`) which the `TutorEaseParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddContactCommandParser`, `DeleteContactCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `TutorEase`, which `Person` references. This allows `TutorEase` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,

* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `TutorEaseStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `tutorease.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()`— Saves the current address book state in its history.
* `VersionedAddressBook#undo()`— Restores the previous address book state from its history.
* `VersionedAddressBook#redo()`— Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `contact delete 5` command to delete the 5th person in the address book. The `contact delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `contact delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `contact add /nDavid …​` to add a new person. The `contact add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
    * Pros: Easy to implement.
    * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by itself.
    * Pros: Will use less memory (e.g. for `contact delete`, just save the person being deleted).
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

* Freelance pre-university home tutors
* Manages a significant number of students
* Prefers desktop applications
* Prefers typing to mouse interactions
* Is reasonably comfortable using CLI apps

**Value proposition**: Our software enhances tutoring efficiency by

* Simplifying management tasks
* Reducing scheduling conflicts
* Providing a clear overview of classes and finances

It enables seamless tutor coordination with students and parents, improving communication and organization, ultimately leading to a more effective and stress-free educational experience.

### User stories

Priorities: MVP (must have), 2 (nice to have), 3 (unlikely to have)

### First time user

| As a                         | I want to                                                    | So that I can                                              | Priority |
|------------------------------|--------------------------------------------------------------|------------------------------------------------------------|----------|
| Potential user exploring app | Have a guided tour showing the functions upon first opening  | Have a better idea and navigate easily when using the app. | 3        |
| Potential user exploring app | See the test data inside the app (i.e. address book release) | Easily see how the app functions when it is in use.        | 3        |
| New user to the app          | Purge all test data                                          | Start writing in my own data                               | 3        |

### Beginner to the software

| As a  | I want to                                                              | So that I can                                            | Priority |
|-------|------------------------------------------------------------------------|----------------------------------------------------------|----------|
| Tutor | Add and delete my students' contacts                                   | Keep track of my students                                | MVP      |
| Tutor | Add and delete my students' guardians' contacts                        | Keep track of my students’ guardians                     | 2        |
| Tutor | Keep track of the house address of a student for home tuition          | Easily go to their house when their tuition starts       | MVP      |
| Tutor | Keep track of Zoom meeting link of a student for online tuition        | Easily go to the online meeting room when tuition starts | MVP      |
| Tutor | Store what was done in the lessons                                     | Track what was done and plan for next lessons easier     | 2        |
| Tutor | Create and delete lesson slots in my schedule                          | Keep track of my lessons                                 | MVP      |
| Tutor | List all my lesson slots in my schedule                                | Keep track of my lessons                                 | MVP      |
| Tutor | List all my contacts                                                   | Keep track of my contacts                                | MVP      |
| Tutor | Categorize my students based on subjects or grade levels               | Know what type of lesson it is                           | 2        |
| Tutor | Keep track of my students' exam dates                                  | Prepare my students adequately by then                   | 2        |
| Tutor | Edit student details (address, fees, exam, subject, grade level, etc.) | Ensure my students' details are up to date               | 2        |

### A little bit familiar with the software

| As a  | I want to                                                                | So that I can                                                        | Priority |
|-------|--------------------------------------------------------------------------|----------------------------------------------------------------------|----------|
| Tutor | Mark a lesson as completed or cancelled                                  | Maintain accurate records of attendance and lesson statuses.         | 2        |
| Tutor | Be able to make my lesson slots repeat every week                        | Avoid creating the same lesson slot every week                       | 2        |
| Tutor | Keep track of my students' homework (i.e., done status, deadline)        | Track the progress of my students and keep them accountable          | 2        |
| Tutor | Change the lesson slot just for that week/all subsequent weeks           | Easily reschedule lessons                                            | 2        |
| Tutor | Know if I have accidentally scheduled a class at a conflicting time slot | Avoid troubling students to reschedule after agreeing on a time slot | 2        |
| Tutor | Keep track of when and how much each student/guardian needs to pay       | Collect my fees timely and accurately                                | 2        |
| Tutor | Tag students under their guardian                                        | Track total fees to collect                                          | 2        |
| Tutor | Automatically update the amount of fee I collect after a lesson          | Avoid manually update and track fees                                 | 2        |
| Tutor | Batch delete all scheduled lessons with a student                        | Remove all students' classes                                         | 2        |

### Expert user

| As a  | I want to                                                                | So that I can                                                                      | Priority |
|-------|--------------------------------------------------------------------------|------------------------------------------------------------------------------------|----------|
| Tutor | Export student progress reports (compiled lesson descriptions)           | Provide detailed updates to their guardians every term/semester                    | 3        |
| Tutor | Set reminders for upcoming lessons                                       | Prepare for a lesson and will not miss any                                         | 3        |
| Tutor | Set reminders to collect payment                                         | Collect my fees on time                                                            | 3        |
| Tutor | View a history of all my previous lessons with each student              | Reference past lessons and track long-term progress                                | 3        |
| Tutor | Autofill commands with what is expected next                             | Avoid re-typing long commands                                                      | 3        |
| Tutor | Export previous years' data into a file                                  | Manage each year separately and not overcrowd my data                              | 3        |
| Tutor | Generate monthly or weekly reports of my hours worked/earnings           | Track my productivity and workload                                                 | 3        |
| Tutor | Know what I need to bring/prepare for all my lessons in the upcoming day | Adequately prepare for each lesson and ensure my students have necessary materials | 3        |
| Tutor | Tag various students under the same lesson slot for group lessons        | Cater to different lesson types and optimize time                                  | 3        |
| Tutor | Manage multiple locations for students                                   | Adjust if students have multiple locations for tuition                             | 3        |

### Use cases

(For all use cases below, the **System** is the `TutorEase` and the **Actor** is the `user`, unless specified otherwise)

**Use Case: UC01 - Add contact**

**MSS**:

1. Tutor keys in required fields to add a contact.
1. TutorEase adds the contact.  
   Use case ends.

**Extensions**:

* **1a.** TutorEase detects bad or wrongly formatted inputs.
    * **1a1.** TutorEase prompts Tutor with correct format.
    * **1a2.** Tutor enters new data.  
      Steps 1a1 to 1a2 are repeated until the data entered are correct.  
      Use case resumes from Step 2.

**Use Case: UC02 - Delete contact**

**MSS**:

1. Tutor keys in required fields to delete contact.
1. TutorEase deletes the contact.  
   Use case ends.

**Extensions**:

* **1a**. TutorEase detects bad or wrongly formatted inputs.
    * **1a1**. TutorEase prompts Tutor with correct format.
    * **1a2**. Tutor enters new data.  
      Steps 1a1 to 1a2 are repeated until the data entered are correct.  
      Use case resumes from Step 2.

**Use Case: UC03 - List contacts**

**MSS**:

1. Tutor keys in list contacts command.
1. TutorEase lists all the contacts stored.  
   Use case ends.

**Extensions**:

* **1a**. TutorEase detects bad or wrongly formatted inputs.
    * **1a1**. TutorEase prompts Tutor with correct format.
    * **1a2**. Tutor enters new data.  
      Steps 1a1 to 1a2 are repeated until the data entered are correct.              
      Use case resumes from Step 2.

**Use Case: UC04 - Find contacts with a specific name keyword**

**MSS**:

1. Tutor keys in a keyword in the required field to find contacts whose names contain this keyword.
1. TutorEase lists the contacts with the given keyword.  
   Use case ends.

**Extensions**:

* **1a**. TutorEase detects bad or wrongly formatted inputs.
    * **1a1**. TutorEase prompts Tutor with correct format.
    * **1a2**. Tutor enters new data.  
      Steps 1a1 to 1a2 are repeated until the data entered are correct.              
      Use case resumes from Step 2.

* **1b**. No contacts found matching the entered keyword.
    * **1b1**. TutorEase displays a message: "No contacts found with the given keyword(s)."  
    * **1b2**. Tutor enters a new keyword or cancels the search.  
      Use case resumes from Step 2 or ends if cancelled.  

**Use Case: UC05 - Add lesson for student**  
**MSS:**

1. Tutor keys in required fields to add student contact.
1. TutorEase adds the lesson to the student.  
   Use case ends.

**Extensions:**

* **1a**. TutorEase detects bad or wrongly formatted inputs.
    * **1a1**. TutorEase prompts Tutor with correct format.
    * **1a2**. Tutor enters new data.  
      Steps 1a1 to 1a2 are repeated until the data entered are correct.  
      Use case resumes from Step 2.

* **1b**. TutorEase detects that the student does not exist.
    * **1b1**. TutorEase prompts Tutor to key in data for a student that exists.
    * **1b2**. Tutor enters new data.  
      Steps 1b1 to 1b2 are repeated until the data entered are correct.  
      Use case resumes from Step 2.

**Use Case: UC06 - Delete lesson for student**  
**MSS:**

1. Tutor keys in required fields to delete student contact.
1. TutorEase deletes the lesson for the student.  
   Use case ends.

**Extensions:**

* **1a.** TutorEase detects bad or wrongly formatted inputs.
    * **1a1.** TutorEase prompts Tutor with correct format.
    * **1a2.** Tutor enters new data.  
      Steps 1a1 to 1a2 are repeated until the data entered are correct.  
      Use case resumes from Step 2.

* **1b.** TutorEase detects that the student does not exist.
    * **1b1.** TutorEase prompts Tutor to key in data for a student that exists.
    * **1b2.** Tutor enters new data.  
      Steps 1b1 to 1b2 are repeated until the data entered are correct.  
      Use case resumes from Step 2.

**Use Case: UC07 - List all lessons**  
**MSS:**

1. Tutor keys in required fields to list all lessons.
1. TutorEase lists all lessons.    
   Use case ends.

**Extensions:**

* **1a.** TutorEase detects bad or wrongly formatted inputs.
    * **1a1.** TutorEase prompts Tutor with correct format.
    * **1a2.** Tutor enters new data.  
      Steps 1a1 to 1a2 are repeated until the data entered are correct.  
      Use case resumes from Step 2.

### Non-Functional Requirements

1. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
1. Data Requirements:
    - Size: System must be able to handle at least 1,000 student records, with each containing personal information and lesson schedules.
    - Volatility: Contact information is not expected to be changed frequently, but lessons schedules may change frequently. System must allow quick updates without issues.
    - Data persistency: all students and lesson data should be stored and retrievable until entry has been deleted.
1. Environment Requirements:
    - Technical Compatability: System must be compatible with _Mainstream OS_ as long as it has Java `17` or above installed.
    - Server Requirements: stored locally.
1. Capacity:
    - User Capacity: System is designed for local use and therefore for 1 local user.
    - Data Capacity: as mentioned above within Data Requirements.
1. Documentation:
    - User Guide: A complete user guide will be provided for tutor, detailing every command and cover common troubleshooting scenarios.
    - Developer Guide: Comprehensive developer guide will be available, to facilitate future development and maintenance.
1. Fault Tolerance:
    - Error handling: System should handle up to 90% of incorrect inputs (incorrect date formats, missing fields or etc) without crashing and should provide meaningful error messages to guide users to correct the input.
1. Maintability:
    - System should have modular components that are easily replaceable or upgradable without affecting the application.
1. Portability:
    - System must be portable across devices with different operating systems, allowing tutors to install it easily.
1. Quality:
    - Ease of Use: System should be usable by tutors with minimal computer literacy and include intuitive CLI commands and user-friendly prompts.
    - Testing coverage: Unit tests should cover at least 60% of codebase, ensuring high reliability during future updates.
1. Testability:
    - Automated Testing: System should support automated unit and integration testing for continuous integration, allowing future updates to be tested without manual intervention.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Pre-U Home Tuition Teacher**: A teacher who offers Primary to Junior College level tuition at the student’s home.
* **Locale date time format**: The date time format the users’ computer uses.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on; testers are expected to do more *exploratory* testing.

</box>

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

    1. Prerequisites: List all persons using the `contact list` command. Multiple persons in the list.
   
    1. Test case: `contact delete 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.
   
    1. Test case: `contact delete 0`<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.
   
    1. Other incorrect delete commands to try: `contact delete`, `contact delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.
   
1. _{ more test cases …​ }_

### Deleting a lesson

1. Deleting a lesson when there are lessons shown in the lesson schedule

    1. Prerequisites: There are multiple lessons in the lesson schedule _{ may change when we can filter lessons}_

    1. Test case: `delete 1`<br>
       Expected: First lesson is deleted from the lesson schedule. Details of the deleted lesson shown in the status message.

    1. Test case: `delete 0`<br>
       Expected: No lesson is deleted. Error details shown in the status message.

    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files
    
   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
