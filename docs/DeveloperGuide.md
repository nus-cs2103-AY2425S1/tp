---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* This software, User Guide (UG), and Developer Guide (DG) are built based on the [AB3](https://github.com/se-edu/addressbook-level3) project.

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

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-W13-1/tp/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-W13-1/tp/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
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
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.)

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-W13-1/tp/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-W13-1/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-W13-1/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-W13-1/tp/tree/master/src/main/java/seedu/address/logic/Logic.java)

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
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-W13-1/tp/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _sorted_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-W13-1/tp/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

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

**Target user**: Secondary School Tuition teachers.

**Value proposition**: Simplifies contact management by providing an all-in-one user-friendly interface for teachers in private education institutions. Ease their pain of manually tracking things like attendance and parents/students’ contact.

**Target user profile 1**: Independent Secondary School Tuition Teachers

* secondary school teachers, often working independently, managing their classes and responsibilities on their own.
* secondary school teachers who manage multiple classes of different levels
* have a need to track and organise details of students and parents (e.g. phone numbers, emails, addresses)
* have a need to efficiently access information regarding students and parents for communication during various situations (e.g. parent-teacher meetings, emergencies, administrative tasks, payment)
* have a need to edit data regarding students and parents in case of updates

**Target user profile 2**: Secondary School Tuition Teachers working under administrations

* secondary school tuition teachers who work alongside or under the supervision of education administrators or managers.
* secondary school tuition teachers who manage multiple classes across various subjects
* have a need to track and organise details of students (e.g. phone numbers, emails, addresses)
* Parent contacts are overseen by institution admin staff
* have a need to efficiently access information regarding students for communication during various situations (e.g. emergencies, administrative tasks, class schedules)
* have a need to edit data regarding students in case of updates

### User stories


Priorities: High (Must-Have), Medium (Nice-to-Have), Low (Could-Have), Trivial (Won't-Have))


| Priority      | As a/an           | I want to …                                                      | So that I can…                                                     |
|---------------|-------------------|------------------------------------------------------------------|--------------------------------------------------------------------|
| **Must-Have** | Novice user       | add new contact information (phone-number, email)                | keep the contact information in one place                          |
| **Must-Have** | Novice user       | view contact information                                         | view the contact information of my student, Peter                  |
| **Must-Have** | Novice user       | delete existing contact                                          | ensure all contacts stored are relevant                            |
| **Must-Have** | Any user          | have my data automatically saved                                 | access my information seamlessly the next time I open the software |
| Nice-to-Have  | New user          | view the help guide easily                                       | learn how to use the app                                           |
| Nice-to-Have  | Novice user       | edit the existing contact of Peter                               | correct the contact typo                                           |
| Nice-to-Have  | Any user          | filter the students by lesson day and time                       | find all students in a class at once                               |
| Nice-to-Have  | Any user          | sort students by last name                                       | have the contacts sorted                                           |
| Nice-to-Have  | Any user          | group students by class timings                                  | send class cancellations out quicker                               |
| Nice-to-Have  | Any user          | find Peter by his last name                                      | inform Peter of homework-related matters                           |
| Nice-to-Have  | Any user          | filter the students by subjects                                  | view the students taking the subject                               |
| Nice-to-Have  | Any user          | edit timing of lessons of students                               | allow students to change their lesson timing to fit their schedule |
| Nice-to-Have  | Any user          | find Peter's parents' contact info by Peter's name               | retrieve parents' contact information quicker                      |
| Nice-to-Have  | Elderly user      | zoom in on the smaller texts                                     | see the contact information easily                                 |
| Nice-to-Have  | Intermediate user | tag certain contacts                                             | know the contacts' roles (e.g., admin)                             |
| Nice-to-Have  | Any user          | find the admin user by the role                                  | discuss matters with him/her                                       |
| Nice-to-Have  | Any user          | group some students                                              | contact students having classes at the same time slot easily       |
| Nice-to-Have  | Any user          | find exact student based on NRIC                                 | find the correct person with many similar names                    |
| Nice-to-Have  | Intermediate user | retrieve students' profile information (pic, name, school, etc.) | verify/track them for security purposes                            |
| Nice-to-Have  | Intermediate user | retrieve staffs' profile information (pic, name, etc.)           | verify/track them for security purposes                            |
| Nice-to-Have  | Admin user        | import data from my previous system                              | use this new app quickly without loss of data                      |
| Nice-to-Have  | Admin user        | export data from the app                                         | switch over to another app or view in Excel                        |
| Nice-to-Have  | Teacher           | create a note of student performance                             | discuss it with parents later                                      |
| Could-Have    | Conservative user | set up an access password                                        | safeguard the information                                          |
| Could-Have    | Advance user      | provide feedback to the developer                                | add desired features                                               |
| Could-Have    | Intermediate user | use shortcuts (del instead of delete)                            | navigate the app quicker                                           |
| Could-Have    | Teacher           | take note of a temporary class change                            | prepare accordingly                                                |
| Could-Have    | Any user          | find a student's parents                                         | discuss the student's performance with his/her parents             |
| Could-Have    | Admin user        | contact the vendors working for the tuition center               | settle admin matters                                               |
| Won't-Have    | new user          | create notes for the features                                    | understand and remind myself what each feature does                |
| Won't-Have    | Teacher           | swap class lesson with my colleagues                             | ensure someone can cover in my absence                             |

### Use cases

(For all use cases below, the **System** is the `Cher` and the **Actor** is the `User`, unless specified otherwise)

#### Use case: UC1 - Add contact
**MSS**
1. User enters add contact command with the contact details.
2. Cher add contact to memory.
3. Cher [<u>Save to disk</u>](#use-case-uc4---save-to-disk).
4. Cher show success message. <br>
   Use case ends.

**Extensions**
* 1a. Cher detects error in user input.
    - 1a1. Cher raises error.
    - 1a2. Cher shows correct input format. <br>
      Use case ends.
* 1b. Cher detects duplicate contacts.
    - 1b1. Cher raises error. <br>
      Use case ends.

#### Use case: UC2 - Delete a contact

**MSS**
1. User enters delete contact command with a specified attribute.
2. Cher shows list of contacts that matches user input.
3. User re-enters delete command with the full attribute of the desired contact to delete.
4. Cher deletes the contact from memory.
5. Cher [<u>Save to disk</u>](#use-case-uc4---save-to-disk).
6. Cher shows a success message. <br>
   Use case ends.

**Extensions**
* 1a. Cher detects error in user input.
    - 1a1. Cher shows correct input format.<br>
      Use case ends.
* 1b. Cher detects only 1 contact
    - Use Case jumps to step 4
* 3a. Cher detects error in user input.
    - 3a1. Cher shows error message. <br>
      Use case ends.

#### Use case: UC3 - List contacts
**MSS**
1. User enters List command.
2. Cher displays all contacts in memory to user. <br>
   Use case ends.

**Extensions**
* 1a. Cher detects error in user input.
    - 1a1. Cher shows correct input format. <br>
      Use case ends.

#### Use case: UC4 - Save to disk
**MSS**
1. Cher opens a local file.
2. Cher saves contacts in memory into local file. <br>
   Use case ends.

#### Use case: UC5 - Batch delete
**MSS**
1. User enters a command to delete all contacts with specific tags.
2. Cher removes all contacts containing specified tags.
3. Cher [<u>Save to disk</u>](#use-case-uc4---save-to-disk).
4. Cher shows the contacts that have been removed.<br>
   Use case ends.

**Extensions**
* 1a. Cher detects error in user input.
    - 1a1. Cher shows correct input format. <br>
      Use case ends.

#### Use case: UC6 - Sort
**MSS**
1. User enters sort contact command with the field name.
2. Cher shows list of contacts sorted alphabetically by name.
3. Cher shows success message that list has been sorted by name.<br>
   Use case ends.

**Extensions**
* 1a. User leaves the sort field empty.
    - 1a1. Cher detects error in user input and shows correct input format. <br>
      Use case ends.
* 1b. User enters invalid field.
    - 1b1. Cher detects error in user input and shows correct input format. <br>
      Use case ends.

#### Use case: UC7 - Batch edit
**MSS**
1. User enters a command to edit all contacts with specific tags to new tag.
2. Cher changes all contacts containing specified tags to the new tag.
3. Cher [<u>Save to disk</u>](#use-case-uc4---save-to-disk).
4. Cher shows all the contacts that have the new tag.<br>
   Use case ends.

**Extensions**
* 1a. Cher detects error in user input.
    - 1a1. Cher shows correct input format. <br>
      Use case ends.


#### Use case: UC8 - Find Contacts

**MSS**
1. User enters find contacts command with specified attribute.
2. Cher shows list of contacts that matches user input.
   Use case ends.

**Extensions**
* 1a. Cher detects error in user input.
    - 1a1. Cher shows correct input format.<br>
      Use case ends.

#### Use case: UC9 - Select contacts by index

**MSS**
1. User enters the select command with a list of indexes.
2. Cher verifies that each index corresponds to a valid contact in the current list.
3. Cher highlights or marks the selected contacts.
4. Cher displays the selected contacts to the user.
   Use case ends.

**Extensions**
* 1a. Cher detects a command format error.
    - 1a1. Cher shows an error message and shows the correct input format.<br>
      Use case ends.
* 1b. User enters an index that is not displayed in the current displayed list.
    - 1b1. Cher shows an error message and shows the invalid indexes.<br>
      Use case ends.

#### Use case: UC10 - Filter and then select contacts

**MSS**
1. User enters a filter command with criteria to narrow down the displayed list of contacts (e.g., by tags or names).
2. Cher filters and displays only the contacts that match the specified criteria.
3. User enters the select command with specific indexes from the filtered list.
4. Cher highlights and displays the selected contacts to the user.
   Use case ends.

**Extensions**
* 1a. Cher detects an error in user input for the filter criteria.
    - 1a1. Cher shows an error message and shows the correct input format.<br>
      Use case ends.
* 3a. Cher detects an invalid index or indexes outside the filtered list.
    - 3a1. Cher shows an error message and shows the correct input format.<br>
      Use case ends.

#### Use case: UG11 - Mark attendance
**MSS**
1. User enters mark command with the index of a specific contact.
2. Cher increases the attendance count of the specified contact by 1.
3. Cher [<u>Save to disk</u>](#use-case-uc4---save-to-disk).
4. Cher shows success message that the attendance of the specified contact have been marked.
5. Cher shows the list of all contacts.<br>
   Use case ends. 

**Extensions**
* 1a. Cher detects an error in the input.
   - 1a1. Cher shows the correct input format. <br>
     Use case ends.
* 1b. Cher detects that the specified contact is not a student.
   - 1b1. Cher shows the error message that attendance for the specified contact cannot be marked. <br>
     Use case ends.

#### Use case: UG12 - Unmark attendance
**MSS**
1. User enters unmark command with the index of a specific contact.
2. Cher decreases the attendance count of the specified contact by 1.
3. Cher [<u>Save to disk</u>](#use-case-uc4---save-to-disk).
4. Cher shows success message that the attendance of the specified contact have been unmarked.
5. Cher shows the list of all contacts. <br>
   User case ends.

**Extensions**
* 1a. Cher detects an error in the input.
  - 1a1. Cher shows the correct inout format. <br>
    Use case ends. 
* 1b. Cher detects that the specified contact is not a student.
  - 1b1. Cher shows error message that the attendance of the specified contact cannot be unmarked. <br>
    Use case ends. 
<<<<<<< HEAD
* 1c. Cher detects that the attendance count of the specified contact is already at 0.
  - 1c1. Cher shows error message that the attebdance count is already at 0. <br>
=======
* 2c. Cher detects that the attendance count of the specified contact is already at 0.
  - 2c1. Cher shows error message that the attendance count is already at 0. <br>
>>>>>>> 3ae51315ef8f83e2218e3560fa54197cd5e386f3
    Use case ends.

#### Use case: UG13 - Reset attendance 
**MSS**
1. User enters the reset attendance command.
2. Cher resets the attendance count of all students in list to 0.
3. Cher [<u>Save to disk</u>](#use-case-uc4---save-to-disk).
4. Cher shows the success message with the names of students whose attendance have been reset.
5. Cher shows the list of all contacts. <br>
   Use case ends.

**Extensions**
* 1a. Cher detects that there is no student in the list.
  - 1a1. Cher shows error message that there is no student in the list. <br>
    Use case ends.

#### Use case: UG14 - Mark group attendance
**MSS**
1. User enters the batch-mark command.
2. Cher increases the attendance count of all students in the list by 1.
3. Cher [<u>Save to disk</u>](#use-case-uc4---save-to-disk).
4. Cher shows the success message with the names of all students whose attendance have been marked.
5. Cher shows the list of all contacts. <br>
   Use case ends.

**Extensions**
* 1a. Cher detects that there is no student in the list.
  - 1a1. Cher shows error message that there is no student in the list. <br>
    Use case ends.

#### Use case: UG15 - Unmark group attendance
**MSS**
1. User enters the batch-unmark command.
2. Cher ignores students whose attendance count is already 0 and decreases the attendance count of other students by 1.
3. Cher [<u>Save to disk</u>](#use-case-uc4---save-to-disk).
4. Cher shows the success message with the names of students whose attendance have been unmarked, including those whose attendance count is initially 0. <br>
   Use case ends.

**Extensions**
* 1a. Cher detects that there is no student in the list.
  - 1a1. Cher shows error message that there is no student in the list. <br>
    Use case ends. 


### Non-Functional Requirements
1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed (>50 words per minute) for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The user interface should be intuitive for users with minimal technical expertise.
5.  Commands should be intuitive and simple.
6.  Command structures should be logical, consistent and memorable.
7.  All texts should be of appropriate size and easily-readable for users of all ages.
8.  The color scheme of the user interface should be high-contrast to accommodate for users of all accessibility levels.
9.  Invalid inputs should be handled gracefully, and the user should be informed clearly of their mistake.
10.  The user should be promptly informed of the outcome of their action (success/failure) via the feedback box.

### Glossary
* **API (Application Programming Interface)**: A set of defined interfaces that allow different components or systems to interact with each other. In the context of the architecture, each component (e.g., UI, Logic, Model, Storage) defines its own API to enable communication with other components without exposing implementation details.
* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Command-Line Interface (CLI)**: A text-based interface that allows users to interact with the system by typing commands
* **Case-insensitive**: Refers to functionality where uppercase and lowercase letters are treated as the same (e.g., "Peter Tan" is the same as "peter tan")
* **Novice user**: A user with limited experience or familiarity with the system, requiring guidance and simple, intuitive interfaces to perform tasks effectively
* **Intermediate user**: A user with some experience and familiarity with the system, capable of performing tasks with minimal guidance but not yet an expert
* **JavaFX**: A framework used for building rich graphical user interfaces (GUIs) in Java. The UI component of the app is built using JavaFX to handle the user interface elements and interactions.
* **Model**: The part of the application responsible for managing data and business logic. It is independent of the UI and controls the application's data. The Model component stores and manipulates data like user preferences and contact details.
* **Use case**: A description of a sequences of actions that the user or system performs, resulting in an observable outcome
* **Actor**: In the context of use cases, actor refers to the role played by the user
* **Main Success Scenario (MSS)**: The sequence of interactions that is the most straightforward and assumes that nothing goes wrong
* **Non-Functional Requirements**: A set of specifications that describes the system's operation capabilities, instead of its function


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

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   2. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   3. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Sorting persons

1. Prerequisites: <br>
Execute the following:
   - add n/Charlie s/m r/student p/56789312 a/Bukit batok e/nus@dfsh.dsfvc  t/tag1 
   - add n/anna s/f r/parent p/23456121 a/Jurong e/sutd@dfsh.dsfvc  t/tag1 
   - add n/Benet s/m r/student p/34682621 a/Pungol e/rjc@dfsh.dsfvc  t/tag1 t/tag2 
   - List all persons using the `list` command.
2. Sorting list by name <br>

   1. Test case: `sort name` <br>
      Expected: Feedback box will show message: `Sorted by name` <br>
      Contact entries will show list sorted in case-insensitive order based on ASCII value of each character in 
      the name, in the following order: anna, Benet, Charlie <br>
   
3. Sorting list by role <br>

   1. Prerequisites: <br>
      List all persons using the `list` command. <br>
   
   2. Test case: `sort role` <br>
      Expected: Feedback box will show message: `Sorted by role` <br>
      Contact entries will show list sorted in case-insensitive order based on ASCII value of each character in
      the role, (parents followed by students) in the following order: anna, Charlie, Benet <br>
   
4. Sorting list by phone
    1. Prerequisites: <br>
       List all persons using the `list` command. <br>
   
    2. Test case: `sort phone` <br>
       Expected: Feedback box will show message: `Sorted by phone` <br>
       Contact entries will show list sorted in case-insensitive order based on ASCII value of each character in
       the phone, in the following order: anna, Benet, Charlie <br>
   
5. Sorting list by email
    1. Prerequisites: <br>
       List all persons using the `list` command. <br>
   
    2. Test case: `sort email` <br>
       Expected: Feedback box will show message: `Sorted by email` <br>
       Contact entries will show list sorted in case-insensitive order based on ASCII value of each character in
       the email, in the following order: Charlie, Benet, anna <br>
   
6. Sorting list by address
    1. Prerequisites: <br>
       List all persons using the `list` command. <br>
   
    2. Test case: `sort address` <br>
       Expected: Feedback box will show message: `Sorted by address` <br>
       Contact entries will show list sorted in case-insensitive order based on ASCII value of each character in
       the address, in the following order: Charlie, anna, Benet <br>
   
7. Sorting list by invalid field
    1. Prerequisites: <br>
       List all persons using the `list` command. <br>
   
    2. Test case: `sort k` <br>
       Expected: Feedback box will show error: `Invalid command format! sort: Sorts the list by given predicate. 
       Parameters: [name] [role] [phone] [email] [address] Example: sort name` <br>
       Contact entries will remain unsorted.

### Marking attendance for a person 

1. Marking the attendance of a selected student while all persons are being shown

   1. Prerequisites:
      Execute the following in a blank Cher:
         - `add n/Alex Yeoh s/m r/student p/12345678 a/Alex Street e/alexy@example.com`
         - `add n/Bernuce Yu s/f r/parent p/98765432 a/Bernice Street e/bernice@example.com`
         - List all persons with the `list` command  
   1. Test case: `mark 1`<br>
      Expected: The attendance field of Alex Yeoh is incremented by 1. Name of the contact, Alex Yeoh, is shown in the status message. The list of all contacts is shown in the contact list. 

   1. Test case: `mark 0`<br>
      Expected: Error details shown in the status message.

   1. Test case: `mark 2`<br>
      Expected: Error details shown in the status message.

### Unmarking attendance for a person

1. Unmarking the attendance of a selected student while all persons are shown

   1. Prerequisites:
      Execute the following in a blank Cher:
         - `add n/Alex Yeoh s/m r/student p/12345678 a/Alex Street e/alexy@example.com`
         - `add n/Bernice Yu s/f r/student p/98765432 a/Bernice Street e/bernice@example.com`
         - `add n/Charlotte Oliveiro s/f r/parent p/99998888 a/ Charlotte Street e/charlotte@example.com`
         - `mark 1`
         - List all persons with the `list` command

   1. Test case: `unmark 1`<br>
   Expected: The attendance field of Alex Yeoh is decremented by 1. Name of the contact, Alex Yeoh, is shown in the status message. The list of all contacts is shown. 

   1. Test case: `unmark 2` <br>
   Expected: Attendance field of Bernice Yu remains at 0. Error details shown in the status message.

   1. Test case: `unmark 3`<br>
   Expected: Error details shown in the status message.

   1. Test case: `unmark 0` <br>
   Expected: Error details shown in the status message. 

### Resetting attendance

1. Resetting the attendance count of all students in list while all persons are shown

   1. Prerequisites: 
      Execute the following in a blank Cher:
         - `add n/Alex Yeoh s/m r/student p/12345678 a/Alex Street e/alexy@example.com`
         - `add n/Bernice Yu s/f r/student p/98765432 a/Bernice Street e/bernice@example.com`
         - `add n/Charlotte Oliveiro s/f r/parent p/99998888 a/ Charlotte Street e/charlotte@example.com`
         - `mark 1`
         - `mark 2`
         - List all persons with the `list` command
  
   1. Test case: `reset-att` <br>
      Expected: The attendance field of Alex Yeoh and Bernice Yu resets to 0. Names of Alex Yeoh and Bernice Yu are shown in the status message. The list of all contacts is shown.

### Marking attendance for a group of persons

1. Marking the attendance of all students in list while all persons are shown

   1. Prerequisites:
      Execute the following in a blank Cher:
      - `add n/Alex Yeoh s/m r/student p/12345678 a/Alex Street e/alexy@example.com`
      - `add n/Bernice Yu s/f r/student p/98765432 a/Bernice Street e/bernice@example.com`
      - `add n/Charlotte Oliveiro s/f r/parent p/99998888 a/ Charlotte Street e/charlotte@example.com`
      - List all persons with the `list` command

   1. Test case: `batch-mark`<br>
      Expected: The attendance count of Alex Yeoh and Bernice Yu in the list increases by 1. Names Alex Yeoh and Bernice Yu are shown in the status message. The list of all contacts is shown. 

### Unmarking attendance for a group of persons

1. Unmarking the attendance of all students in list while all persons are shown

   1. Prerequisites:
      Execute the following in a blank Cher:
      - `add n/Alex Yeoh s/m r/student p/12345678 a/Alex Street e/alexy@example.com`
      - `add n/Bernice Yu s/f r/student p/98765432 a/Bernice Street e/bernice@example.com`
      - `add n/Charlotte Oliveiro s/f r/parent p/99998888 a/ Charlotte Street e/charlotte@example.com`
      - `mark 1`
      - List all persons with the `list` command
     
   1. Test case: `batch-unmark`<br>
      Expected: The attendance count of Alex Yeoh decrements to 0, attendance count of Bernice remains at 0. Names of Alex Yeoh and Bernice Yu are shown in the status message. The list of all contacts is shown.


### Batch deleting a group of people

1. Deleting a group of people.
   
   1. Prerequisites: <br>
   Execute the following:
      - add n/test person 1 s/m r/student p/12345678 a/address e/sdgs@dfsh.dsfvc  t/tag1
      - add n/test person 2 s/m r/student p/12543789 a/address e/sdgs@dfsh.dsfvc  t/tag1
      - add n/test person 3 s/m r/student p/26343642 a/address e/sdgs@dfsh.dsfvc  t/tag1 t/tag2
      - List all persons using the `list` command.
        
   2. Test case: `batch-delete t/tag3` <br>
      Expected: Feedback box will show error: `No person with Tag= [[tag3]] is found`
   
   4. Test case: `batch-delete t/tag1 t/tag2` <br>
      Expected: `test person 3` will be deleted. Feedback box will show detail of deleted person.
      
   6. Test case: `batch-delete t/tag1` <br>
      Expected: `test person 1`, `test person 2` will be deleted as `test person 3` is already deleted from the
      previous test case `iii`. Feedback box will show detail of deleted person.

### Batch editing a group of people's tag

1. Changing a common tag among group of people.
    1. Prerequisites: <br>
       Execute the following:
        - add n/test person 1 s/m r/student p/12345678 a/address e/sdgs@dfsh.dsfvc  t/tag1
        - add n/test person 2 s/m r/student p/12543789 a/address e/sdgs@dfsh.dsfvc  t/tag1
        - add n/test person 2 s/m r/student p/12543579 a/address e/sdgs@dfsh.dsfvc  t/tag1
        - add n/test person 3 s/m r/student p/26343642 a/address e/sdgs@dfsh.dsfvc  t/tag1 t/tag2
        - List all persons using the `list` command.
          
    2. Test case: `batch-edit t/tag3 t/tag4` <br>
       Expected: Feedback box will show error: `No person with Tag= [[tag3]] is found`
   
    4. Test case: `batch-edit t/tag1 t/tag3` <br>
       Expected: Feedback box will show message: `Tag Changed: [tag1] -> [tag3]`. 

    3. Test case: `batch-edit t/tag1 t/tag3` <br>
       Expected: Feedback box will show message: `Tag Changed: [tag1] -> [tag3]`.

       Contact entries will show a list of contacts that currently has `[tag3]`; `test person 1`, `test person 2`,
       `test person 3`, for this test assuming other contacts does not have the `[tag1]` as their tag.
       
    6. Test case: `batch-edit t/tag2 t/tag4` <br>
       Expected: Feedback box will show message: `Tag Changed: [tag2] -> [tag4]` <br>
       Contact entries will show a list of contacts that currently has `[tag4]`; `test person 3`.

### Selecting Persons

1. Select one person while all persons are being shown:
    1. Prerequisites: List N persons using the `list` command. Multiple persons are displayed in the list.
    2. Test case: `select 2` where N > 2
        - Expected: Contacts at indexes 2 is selected and displayed in the contact list. The feedback box displays: "Selected Person(s): [Names of contacts at index 2]" (e.g., "Selected Person(s): John Doe"). No errors are shown.

2. Select multiple persons while all persons are being shown:
   1. Prerequisites: List N persons using the `list` command. Multiple persons are displayed in the list.
   2. Test case: `select 1 2 4` where N > 4
       - Expected: Contacts at indexes 1, 2, and 3 are selected and displayed in the contact list. The feedback box displays: "Selected Person(s): [Names of contacts at indexes 1, 2, and 4]" (e.g., "Selected Person(s): John Doe, Jane Smith, Bob Lee"). No errors are shown.
   3. Test case: `select 1 7` where N > 7
       - Expected: Similar to the previous. Contacts at indexes 1, 2 are selected and displayed in the contact list. The feedback box displays: "Selected Person(s): [Names of contacts at indexes 1 and 2]" (e.g., "Selected Person(s): John Doe, Bob Lee"). No errors are shown.

3. Select with invalid indexes that do not match the currently displayed person list
    1. Prerequisites: List N persons using the `list` command. Multiple persons are displayed in the list.
    2. Test case: `select 7` where N < 6
       - Expected: The feedback box will display an error message: "The following indexes are invalid: 7." No changes are made to the contact list, and it remains as originally displayed.
    3. Test case: `select 6 20 40` where N < 6
       - Expected: Similar to the previous. The feedback box will display an error message: "The following indexes are invalid: 6, 20, 40." No changes are made to the contact list, and it remains as originally displayed.

### Testing the Main Window

1. Open the Main Window:
   1. Prerequisites: Ensure that the application has started and the main window is initialized.
   2. Test case: Launch the application and wait for the main window to appear.
      - Expected: The main window should appear on the screen, showing the application layout with the menu bar, the person list panel, result display area (Feedback Box), command box, and status bar footer. No errors should be shown.
   3. Test case: Try the functionalities of the UI, follow Selecting Persons 2.2
      - Expected: Same as Selecting Persons 2.2 Expected. The Ui components should function without errors. The corresponding persons should be highlighted or indicated as selected in the UI.

### Testing the Help Window

1. Open the Help Window:
   1. Prerequisites: Ensure that the main window is open and the application is running.
   2. Test case: Press F1 or click the "Help" menu item, then click the dropdown menu button.
      - Expected: The help window should appear, displaying the message: "Refer to the user guide: https://ay2425s1-cs2103t-w13-1.github.io/tp/UserGuide.html". No errors should be shown, and the user guide should be loaded in the web view.

2. Focus on an already opened Help Window:
   1. Prerequisites: Ensure that the help window is open.
   2. Test case: Press F1 again or click the "Help" menu item while the help window is already showing.
      - Expected: The help window should gain focus and bring itself to the front. The URL "Refer to the user guide: https://ay2425s1-cs2103t-w13-1.github.io/tp/UserGuide.html" should remain displayed in the help message label.

### Saving data

1. Dealing with missing/corrupted data files

   1. Delete all generated save file if there is any.
   2. Re-start the Cher application which will automatically regenerate the basic data.
   3. It will result in loss of data.


### Finding persons

1. Finding persons by tag
      1. Prerequisites: <br>
         Execute the following:
         - add n/test person 1 s/m r/student p/12345678 a/address e/sdgs@dfsh.dsfvc  t/tag1
         - add n/test person 2 s/m r/student p/12543579 a/address e/sdgs@dfsh.dsfvc  t/tag1
         - add n/test person 3 s/m r/student p/26343642 a/address e/sdgs@dfsh.dsfvc  t/tag1 t/tag2
         - List all persons using the `list` command.
           
      2. Test case: `find t/tag1`<br>
         Expected: test person 1, test person 2, and test person 3 will be shown in the list of persons.
         Feedback box will show number of persons listed.

      3. Test case: `find t/tag2`<br>
         Expected: test person 3 will be shown in the list of persons.
         Feedback box will show number of persons listed.

      4. Other incorrect find commands to try: `find`, `find x`, `...` (where x is larger than the list size)<br>
         Expected: List of persons shown remains unchanged.
         Feedback box shows error details regarding incorrect input format.

## **Appendix: Planned Enhancements**

### Select Command
* We plan on updating select command such that it can select a range of people. For example, `select 5-10` will select the 5th to 10th persons.


### Sort Command
* In the future, we plan on implementing sorting by multiple attributes rather than just one.


### Find Command
* We plan on adding functionality for partial matches for phone number, address, and email, following the same format as the current partial name search.
* In the future, we will also implement finding by multiple attributes rather than just one.

### Delete Command
* We plan on adding a confirmation prompt before Cher deletes the contact.
* If no matches are found, rather than returning an empty list, we plan on displaying the current list along with an error message indicating no users were found. 
* We also plan on implementing deleting by multiple attributes rather than just one.

### Attendance Commands
* Currently, after executing the attendance commands, the list of all contacts is shown. This may be inconvenient if the user would like to continue working on a filtered list.
* In the future, we plan to make it such that the filtered list is shown instead of the full contact list. 
* We also plan to add a confirmation prompt for `reset-att`.
