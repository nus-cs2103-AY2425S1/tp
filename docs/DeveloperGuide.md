---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# TutorEase Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

<div style="page-break-after: always;"></div>

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org)_.

Generative AI tools such as ChatGPT and Copilot were used for the purposes of creating and ideating Junit 
test cases and method names, writing detailed Javadocs and some code refactoring.

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

<div style="page-break-after: always;"></div>

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `contact delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

<div style="page-break-after: always;"></div>

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103-F11-2/tp/blob/master/src/main/java/tutorease/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component" />

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`,  
`LessonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class  
which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103-F11-2/tp/blob/master/src/main/java/tutorease/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103-F11-2/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` and `Lesson` object residing in the `Model`.

<div style="page-break-after: always;"></div>

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103-F11-2/tp/blob/master/src/main/java/tutorease/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("contact delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `contact delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteContactCommandParser` and `ContactCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.

</box>

<div style="page-break-after: always;"></div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, in this case `execute("contact delete 1)`, it is passed to an `TutorEaseParser` object which in turn creates a parser that matches the type of command. In this case, it is the `ContactCommandParser` as we are executing a command related to contacts. Commands related to lessons will use `LessonCommandParser`.
1. The `ContactCommandParser` will create the specific contact command parser corresponding to the execution. In this case, a `DeleteContactCommandParser` will be created to parse the command.
1. This results in a `Command` object, specifically a `DeleteContactCommand` in this case, which is executed by the`LogicManager`.<br>
   (Note that `DeleteContactCommand` is a subclass of `ContactCommand` which is a subclass of `Command`)
1. The command can communicate with the `Model` when it is executed, in this case, to delete a person.<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml"/>

How the parsing works:

* When called upon to parse a user command, the `TutorEaseParser` class creates a `ContactCommandParser` or `LessonCommandParser` based on the first word that the tutor keys in, which creates `XYZContactCommandParser`(e.g. `AddContactCommandParser`, `DeleteContactCommandParser`) and `XYZLessonCommandParser` (e.g. `AddLessonCommandParser`) respectively. `XYZContactCommandParser` and `XYZLessonCommandParser` use the other classes shown above to parse the user command and create a `XYZContactCommand` or `XYZLessonCommand` object (e.g., `AddContactCommand`) which the `TutorEaseParser` returns back as a `Command` object.
* All `XYZContactCommandParser` and `XYZLessonCommandParser` classes (e.g., `AddContactCommandParser`, `DeleteContactCommandParser`, ...) inherit from the`Parser` interface so that they can be treated similarly where possible e.g, during testing.

Extra Pointers about parsing:

* For basic one-word commands like `help`, `exit`, and `clear`, they are handled directly within the `parseCommand` function in `TutorEaseParser` without the need for a dedicated parser.
These simple commands are omitted in the Parser classes diagram to enhance clarity and reduce clutter.
* Various contact and lesson command parsers are represented as `XYZContactCommandParser` and `XYZLessonCommandParser`, respectively. 
However, their behaviour varies slightly depending on the function. For example, `ArgumentMultimap` is used exclusively in parsers for add, delete, and edit commands, 
while `ArgumentTokenizer` is only used in parsers for add and edit commands. Not specifying every parser reduces clutter and conveys the high-level message concisely.

<div style="page-break-after: always;"></div>

### Model component

**API** : [`Model.java`](https://github.com/AY2425S1-CS2103-F11-2/tp/blob/master/src/main/java/tutorease/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml"/>


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `TutorEase`, which `Person` references. This allows `TutorEase` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

<div style="page-break-after: always;"></div>

### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103-F11-2/tp/blob/master/src/main/java/tutorease/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml"/>

The `Storage` component,

* can save contact data, lesson data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from `TutorEaseStorage`, `LessonSchedule` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `tutorease.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

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

It enables seamless tutor coordination with primary to junior college level students and guardians, improving communication and organization, ultimately leading to a more effective and stress-free educational experience.

### User stories

Priorities: MVP (must have), 2 (nice to have), 3 (unlikely to have)

### First time user

| As a                         | I want to                                                    | So that I can                                                       | Priority |
|------------------------------|--------------------------------------------------------------|---------------------------------------------------------------------|----------|
| Potential user exploring app | Have a guided tour showing the functions upon first opening  | Have a better idea and <br/>navigate easily when using <br/>the app | 3        |
| Potential user exploring app | See the test data inside the app (i.e. address book release) | Easily see how the app functions <br/>when it is in use             | 3        |
| New user to the app          | Purge all sample data                                        | Start writing in my own data                                        | MVP      |

### Beginner to the software

| As a  | I want to                                                              | So that I can                                            | Priority |
|-------|------------------------------------------------------------------------|----------------------------------------------------------|----------|
| Tutor | Add and delete my students' contacts                                   | Keep track of my students                                | MVP      |
| Tutor | Add and delete my students' guardians' contacts                        | Keep track of my students’ guardians                     | MVP      |
| Tutor | Keep track of the house address of a student for home tuition          | Easily go to their house when their tuition starts       | MVP      |
| Tutor | Keep track of Zoom meeting link of a student for online tuition        | Easily go to the online meeting room when tuition starts | 2        |
| Tutor | Store what was done in the lessons                                     | Track what was done and plan for next lessons easier     | 2        |
| Tutor | Create and delete lesson slots in my schedule                          | Keep track of my lessons                                 | MVP      |
| Tutor | List all my lesson slots in my schedule                                | Keep track of my lessons                                 | MVP      |
| Tutor | List all my contacts                                                   | Keep track of my contacts                                | MVP      |
| Tutor | Categorize my students based on subjects or grade levels               | Know what type of lesson it is                           | 2        |
| Tutor | Keep track of my students' exam dates                                  | Prepare my students adequately by then                   | 2        |
| Tutor | Edit student details (name, phone number, email, address, tags, etc.)  | Ensure my students' details are up to date               | 2        |
| Tutor | Edit guardian details (name, phone number, email, address, tags, etc.) | Ensure guardians' details are up to date                 | 2        |

### A little bit familiar with the software

| As a  | I want to                                                                | So that I can                                                             | Priority |
|-------|--------------------------------------------------------------------------|---------------------------------------------------------------------------|----------|
| Tutor | Mark a lesson as completed or cancelled                                  | Maintain accurate records of attendance <br/>and lesson statuses          | 2        |
| Tutor | Be able to make my lesson slots repeat every week                        | Avoid creating the same lesson slot every week                            | 2        |
| Tutor | Keep track of my students' homework (i.e., done status, deadline)        | Track the progress of my students and <br/>keep them accountable          | 2        |
| Tutor | Change the lesson slot just for that week/all subsequent weeks           | Easily reschedule lessons                                                 | 2        |
| Tutor | Know if I have accidentally scheduled a class at a conflicting time slot | Avoid troubling students to reschedule after <br/>agreeing on a time slot | MVP      |
| Tutor | Keep track of when and how much each student/guardian needs to pay       | Collect my fees timely and accurately                                     | 2        |
| Tutor | Tag students under their guardian                                        | Track total fees to collect                                               | 2        |
| Tutor | Automatically update the amount of fee I collect after a lesson          | Avoid manually update and track fees                                      | 2        |
| Tutor | Batch delete all scheduled lessons with a student                        | Remove all students' classes                                              | 2        |
| Tutor | Find a student's or guardian's contact details quickly                   | Contact them quickly                                                      | 2        |
| Tutor | Find a student's lesson details quickly                                  | Know the location for their tuition                                       | 2        |

### Expert user

| As a  | I want to                                                                | So that I can                                                                                | Priority |
|-------|--------------------------------------------------------------------------|----------------------------------------------------------------------------------------------|----------|
| Tutor | Export student progress reports (compiled lesson descriptions)           | Provide detailed updates to their <br/>guardians every term/semester                         | 3        |
| Tutor | Set reminders for upcoming lessons                                       | Prepare for a lesson and will not miss any                                                   | 3        |
| Tutor | Set reminders to collect payment                                         | Collect my fees on time                                                                      | 3        |
| Tutor | View a history of all my previous lessons with each student              | Reference past lessons and track <br/>long-term progress                                     | 3        |
| Tutor | Autofill commands with what is expected next                             | Avoid re-typing long commands                                                                | 3        |
| Tutor | Export previous years' data into a file                                  | Manage each year separately and not <br/>overcrowd my data                                   | 3        |
| Tutor | Generate monthly or weekly reports of my hours worked/earnings           | Track my productivity and workload                                                           | 3        |
| Tutor | Know what I need to bring/prepare for all my lessons in the upcoming day | Adequately prepare for each lesson <br/>and ensure my students <br/>have necessary materials | 3        |
| Tutor | Tag various students under the same lesson slot for group lessons        | Cater to different lesson types <br/>and optimize time                                       | 3        |
| Tutor | Manage multiple locations for students                                   | Adjust if students have multiple locations for tuition                                  | 3        |

### Use cases

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

<div style="page-break-after: always;"></div>

**Use Case: UC04 - Edit contacts**

**MSS**:

1. Tutor keys in required fields to edit a contact.
1. TutorEase edits the contact.
   Use case ends.

**Extensions**:

* **1a**. TutorEase detects bad or wrongly formatted inputs.
    * **1a1**. TutorEase prompts Tutor with correct format.
    * **1a2**. Tutor enters new data.  
      Steps 1a1 to 1a2 are repeated until the data entered are correct.              
      Use case resumes from Step 2.

**Use Case: UC05 - Find contacts with a specific name keyword**

**MSS**:

1. Tutor keys in a keyword in the required field to find contacts whose names contain any of the keywords.
1. TutorEase lists the contacts with the given keyword.  
   Use case ends.

**Extensions**:

* **1a**. TutorEase detects bad or wrongly formatted inputs.
  * **1a1**. TutorEase prompts Tutor with correct format.  
  * **1a2**. Tutor enters new data.  
    Steps 1a1 to 1a2 are repeated until the data entered are correct.  
    Use case resumes from Step 2.  
<br>
* **1b**. No contacts found matching the entered keyword.
    * **1b1**. TutorEase displays a message letting Tutor know that no contacts were found with the given keyword(s).
    * **1b2**. Tutor enters a new keyword.  
      Use case resumes from Step 2.

**Use Case: UC06 - Add lesson for student**  

Precondition: Student exists in the system.  

**MSS:**

1. Tutor keys in required fields to add a lesson for a student contact.
1. TutorEase adds the lesson to the student.  
   Use case ends.

**Extensions**:

* **1a**. TutorEase detects bad or wrongly formatted inputs.
    * **1a1**. TutorEase prompts Tutor with correct format.
    * **1a2**. Tutor enters new data.  
      Steps 1a1 to 1a2 are repeated until the data entered are correct.  
      Use case resumes from Step 2.  
<br>
* **1b**. TutorEase detects that the student index is invalid.
    * **1b1**. TutorEase prompts Tutor to key in an index for a student that exists.
    * **1b2**. Tutor enters new data.  
      Steps 1b1 to 1b2 are repeated until the data entered are correct.  
      Use case resumes from Step 2.  
<br>
* **1c**. TutorEase detects there is an overlapping lesson at the specified date time.
    * **1c1**. TutorEase prompts Tutor that there is an overlapping lesson.
    * **1c2**. Tutor enters new data.  
      Steps 1c1 to 1c2 are repeated until the data entered are correct.  
      Use case resumes from Step 2.

**Use Case: UC07 - Delete lesson for student**  

**MSS:**  

1. Tutor keys in required fields to delete student contact.
1. TutorEase deletes the lesson for the student.  
   Use case ends.

**Extensions**:

* **1a**. TutorEase detects bad or wrongly formatted inputs.
    * **1a1**. TutorEase prompts Tutor with correct format.
    * **1a2**. Tutor enters new data.  
      Steps 1a1 to 1a2 are repeated until the data entered are correct.  
      Use case resumes from Step 2.  
<br>
* **1b.** TutorEase detects that the student does not exist.
    * **1b1.** TutorEase prompts Tutor to key in data for a student that exists.
    * **1b2.** Tutor enters new data.
      Steps 1b1 to 1b2 are repeated until the data entered are correct.  
      Use case resumes from Step 2.  

**Use Case: UC08 - List all lessons**

**MSS:**  

1. Tutor keys in required fields to list all lessons.
1. TutorEase lists all lessons.    
   Use case ends.

**Extensions**:

* **1a**. TutorEase detects bad or wrongly formatted inputs.
    * **1a1**. TutorEase prompts Tutor with correct format.
    * **1a2**. Tutor enters new data.  
      Steps 1a1 to 1a2 are repeated until the data entered are correct.  
      Use case resumes from Step 2.

**Use Case: UC09 - Find lessons by student names**  

**MSS:**

1. Tutor keys in a keyword in the required field, to find lessons with students whose names contain any of the keywords.
1. TutorEase lists lessons that match the requirement.    
   Use case ends.

**Extensions**:

* **1a**. TutorEase detects bad or wrongly formatted inputs.
    * **1a1**. TutorEase prompts Tutor with correct format.
    * **1a2**. Tutor enters new data.  
      Steps 1a1 to 1a2 are repeated until the data entered are correct.              
      Use case resumes from Step 2.  
<br>
* **1b**. No lessons found matching the entered keyword.
    * **1b1**. TutorEase displays a message letting Tutor know that no lessons were found with the given keyword(s).
    * **1b2**. Tutor enters a new keyword.  
      Use case resumes from Step 2.

<div style="page-break-after: always;"></div>

**Use Case: UC10 - Clear all entries**  

**MSS:**

1. Tutor keys in clear command.
1. TutorEase clears all entries about contacts and lessons.    
   Use case ends.

**Extensions**:

* **1a**. TutorEase detects bad or wrongly formatted inputs.
    * **1a1**. TutorEase prompts Tutor with correct format.
    * **1a2**. Tutor enters new data.  
      Steps 1a1 to 1a2 are repeated until the data entered are correct.  
      Use case resumes from Step 2.

### Non-Functional Requirements

1. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
1. Data Requirements:
    - Size: System must be able to handle at least 1,000 student records, with each containing personal information and lesson schedules.
    - Volatility: Contact information is not expected to be changed frequently, but lessons schedules may change frequently. System must allow quick updates without issues.
    - Data persistency: All students and lesson data should be stored and retrievable until entry has been deleted.
1. Environment Requirements:
    - Technical Compatability: System must be compatible with _Mainstream OS_ as long as it has Java `17` or above installed.
    - Server Requirements: Stored locally.
1. Capacity:
    - User Capacity: System is designed for local use and therefore for 1 local user.
    - Data Capacity: As mentioned above within Data Requirements.
1. Documentation:
    - User Guide: A complete user guide will be provided for tutor, detailing every command and cover common troubleshooting scenarios.
    - Developer Guide: Comprehensive developer guide will be available, to facilitate future development and maintenance.
1. Fault Tolerance:
    - Error handling: System should handle up to 90% of incorrect inputs (incorrect date formats, missing fields or etc) without crashing and should provide meaningful error messages to guide users to correct the input.
1. Portability:
    - System must be portable across devices with different operating systems, allowing tutors to install it easily.
1. Quality:
    - Ease of Use: System should be usable by tutors with minimal computer literacy and include intuitive CLI commands and user-friendly prompts.
    - Testing coverage: Unit tests should cover at least 60% of codebase, ensuring high reliability during future updates.
1. Testability:
    - Automated Testing: System should support automated unit and integration testing for continuous integration, allowing future updates to be tested without manual intervention.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Pre-U Home Tuition Teacher**: A teacher who offers Primary to Junior College level tuition at the student’s home.
* **Locale date time format**: The date time format the users’ computer uses.

<div style="page-break-after: always;"></div>

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on; testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder.
   
    1. Open a command terminal, `cd` into the folder you put the jar file in, and use the command `java -jar tutorease.jar`
       to run the application.<br>
       Expected: The app launches and shows the GUI with some sample data.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.
   
    1. Re-launch the app by using the command `java -jar tutorease.jar` in the command terminal.<br>
       Expected: The most recent window size and location is retained.

1. Exiting the app

    1. Type the command `exit` into the command box and press enter.<br>
       Expected: The app and GUI closes.

### Adding a contact

1. Adding a contact for a Student.

    1. Test case: `contact add n/Norbeast p/99243312 e/Norbeast12@beast.com a/Kent Ridge MRT r/Student t/Master`<br>
       Expected: A student is added to the contacts. Details of the added student are shown in the status message.

    1. Test case: `contact add n/NowBeast p/223 a/Kent Ridge MRT r/Student t/Master`<br>
       Expected: No contact is added. Error details are shown in the status message.

    1. Test case: `contact add n/CookBeast p/44627732 e/Norbea4st12@beast.com r/Student`<br>
       Expected: No contact is added. Error details are shown in the status message.

1. Adding a contact for a Guardian.

   1. Test case: `contact add n/ETAN p/2774213 e/etan@man.com a/Tampines MRT r/Guardian t/Father`<br>
      Expected: A guardian is added to the contacts. Details of the added guardian are shown in the status message.

   1. Test case: `contact add n/Mac p/44421367 a/Chicken MRT r/Guardian t/Master`<br>
      Expected: No contact is added. Error details are shown in the status message.

   1. Test case: `contact add n/Pizza p/556785 e/Cooking@beast.com r/Guardian`<br>
      Expected: No contact is added. Error details are shown in the status message.

### Listing all contacts

1. List all contacts in TutorEase.

    1. Prerequisite: At least one contact exists in TutorEase.

    1. Test case: `contact list`<br>
       Expected: All contacts are shown.

    1. Test case: `contact list 0`<br>
       Expected: All contacts are shown.

### Editing a contact

1. Editing a contact when there are contacts shown on the contact list.

   1. Prerequisites: There are multiple contacts shown on the contact list.
   
   1. Test case: `contact edit 1 n/Chicken`<br>
      Expected: First contact's name is edited. Details of the edited contact shown in the status message.

   1. Test case: `contact edit 1 p/992`<br>
      Expected: First contact's phone number is edited. Details of the edited contact is shown in the status message.

   1. Test case: `contact edit 1 r/Student`<br>
      Expected: Contact is not edited. Error details are shown in the status message.

1. Editing a contact when there are contacts shown on the filtered contact list.

    1. Prerequisites: At least one contact is shown on the contact list.

    1. Test case: `contact edit 1 n/Duck`<br>
       Expected: First contact's name is edited. Details of the edited contact are shown in the status message.

    1. Test case: `contact edit 1 p/8842`<br>
       Expected: First contact's phone number is edited. Details of the edited contact are shown in the status message.

    1. Test case: `contact edit 1 r/Student`<br>
       Expected: Contact is not edited. Error details are shown in the status message.

### Finding a contact

1. Finding a contact by name.

    1. Prerequisites: There are multiple contacts, with at least one contact whose name is `Alice` and at least one contact whose name is `Bob`. None of the
       contacts whose name is `Test` or contains the word `Test`. These names are case-insensitive.

    1. Test case: `contact find Alice`<br>
       Expected: Contacts whose name is `Alice` or contains the word `Alice` will be displayed. There should be at least one as per the prerequisites.

    1. Test case: `contact find Test`<br>
       Expected: No contacts is found. Details are shown in the status message.

    1. Other incorrect contact find commands to try: `contact find`, `contact find x` (where x is a name of a
       contact, a name that does not belong to any of the contacts or a word that is not in the names of any contact)<br>
       Expected: Similar to previous.

### Deleting a contact

1. Deleting a contact while all contact are being shown.

    1. Prerequisites: List all contact using the `contact list` command. Multiple contact in the list.
   
    1. Test case: `contact delete 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message.
   
    1. Test case: `contact delete 0`<br>
       Expected: No contact is deleted. Error details shown in the status message. Status bar remains the same.
   
    1. Other incorrect delete commands to try: `contact delete`, `contact delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

<div style="page-break-after: always;"></div>

### Adding a lesson

1. Adding a lesson when all students are shown.

   1. Prerequisite: At least one student exists in the contact list.

   1. Test case: `lesson add sid/1 f/10 d/11-11-2024 12:00 h/1`<br>
      Expected: A lesson is added to the student with index 1. Details of the added lesson shown in the status message.
   
   1. Test case: `lesson add sid/0 f/10 d/11-11-2024 12:00 h/1`<br>
      Expected: No lesson is added. Error details are shown in the status message.
   
   1. Test case: `lesson add sid/1 f/10 d/11-11-2024 12:00 h/1`<br>
      Expected: No lesson is added. Error details are shown in the status message.

1. Adding a lesson on filtered contact list.

   1. Prerequisite: At least one student exists in the contact list.

   1. Test case: `lesson add sid/1 f/10 d/12-11-2024 12:00 h/1`<br>
      Expected: A lesson is added to the student with index 1 in the filtered contact list. Details of the added lesson is shown in the status message.

### Listing all lessons

1. List all lessons in the schedule.

   1. Prerequisite: At least one lesson exists in the lesson schedule.

   1. Test case: `lesson list`<br>
      Expected: All lessons in the lesson schedule are shown.

   1. Test case: `lesson list 0`<br>
      Expected: All lessons in the lesson schedule are shown.

### Deleting a lesson

1. Deleting a lesson when there are lessons shown in the lesson schedule.

    1. Prerequisites: There are multiple lessons in the lesson schedule.

    1. Test case: `lesson delete 1`<br>
       Expected: First lesson is deleted from the lesson schedule. Details of the deleted lesson is shown in the status message.

    1. Test case: `lesson delete 0`<br>
       Expected: No lesson is deleted. Error details are shown in the status message.

    1. Other incorrect delete commands to try: `lesson delete`, `lesson delete x`, `...` (where x is larger 
       than the list size)<br>
       Expected: Similar to previous.

1. Deleting a lesson when there are lessons shown in the filtered lesson schedule.

    1. Prerequisites: Executed a lesson find command and there are multiple lessons in the filtered lesson 
       schedule.

    1. Test case: `lesson delete 1`<br>
       Expected: First lesson is deleted from the filtered lesson schedule. Details of the deleted lesson are
       shown in the status message.

    1. Test case: `lesson delete 0`<br>
       Expected: No lesson is deleted. Error details are shown in the status message.

    1. Other incorrect delete commands to try: `lesson delete`, `lesson delete x` (where x is larger
       than the filtered list size)<br>
       Expected: Similar to previous.

<div style="page-break-after: always;"></div>

### Finding a lesson

1. Finding a lesson when there are lessons shown in the lesson schedule.

    1. Prerequisites: There are multiple lessons in the lesson schedule, with at least one lesson with a 
       student whose name is `Alice` and at least one lesson with a student whose name is `Bob`. None of the 
       lessons have a student whose name is `Test` or contains the word `Test`. These names are 
       case-insensitive.

    1. Test case: `lesson find Alice`<br>
       Expected: Lessons with student whose name is `Alice` or contains the word `Alice` will be displayed 
       on the lesson panel. There should be at least one as per the prerequisites.

    1. Test case: `lesson find Test`<br>
       Expected: No lesson is found. Details are shown in the status message.

    1. Other incorrect lesson find commands to try: `lesson find`, `lesson find x` (where x is a name of a 
       student who does not have a lesson, a name that does not belong to any of the students that have 
       lessons or a word that is not in the names of the students that have lessons)<br>
       Expected: Similar to previous.

### Clearing TutorEase

1. Clearing TutorEase when there are contacts and lessons.

    1. Prerequisites: There are multiple contacts and lessons in the contact list and lesson schedule.

    1. Test case: `clear`<br>
       Expected: All contacts and lessons are cleared.

--------------------------------------------------------------------------------------------------------------------

## Appendix: Planned Enhancements

Team Size: 4

#### 1. **Unique Attributes and Relationship Modeling for Students and Guardians**
Recognizing the importance of capturing more details about different types of contacts, we plan to incorporate additional attributes for students, such as grade levels, subjects and exam dates.
To support clear organization, we’ll also implement a tagging system that links each student to their respective guardian, making it easier to manage relationships and access relevant information at a glance.
In the event that a student does not have contact information, they will be required to be linked to a guardian, ensuring that there exist a way to contact the student.

#### 2. **Overlapping Lesson Time Handling**
We recognize the need for clarity around overlapping lesson scheduling. Options for managing overlaps will include:

* Allowing overlapping sessions for group lessons or pair lessons.
* Enabling you to customize settings for overlap permissions based on the type of lesson.

<div style="page-break-after: always;"></div>

#### 3. **Enhanced Name Field Support**
To accommodate a more diverse range of names, such as names with commas, accented characters, or etc, we plan to update the application to allow names with these characters.
In the future, the system will support a wider variety of name formats, making it more inclusive and user-friendly.

Additionally, if extra whitespace is accidentally added between names (e.g., double space between first and last name), the system will show a warning to alert you of potential duplicate entries due to spacing inconsistencies.

#### 4. **Past Lesson Management**
To enhance lesson organization, we plan to introduce features that clearly distinguish past lessons. This will include options to automatically hide completed lessons or display them in a different color for easy identification.
You will also be able to toggle the visibility of past lessons, allowing them to focus on upcoming schedules while still being able to reference completed sessions if needed.

#### 5. **Support for Decimal Places in Fees**
We have acknowledged that it might be possible to charge an odd number (ie $25) per lesson for an even number of hours (ie 2 hours), resulting in the fee per hour not being an integer.
To provide more flexibility in fee management, we plan to introduce support for decimal places in fee amounts.
This will allow you to input and manage fees with greater precision, catering to scenarios where fees are calculated to fractional values (e.g., for hourly rates or partial payments).

#### 6. **Support for More Screen Resolutions**
An example of this issue is when a Tutor creates a contact with a name, address, tag, and email that are longer than expected. Part of the UI will be cut off if the screen resolution is not big enough. To ensure that the application is accessible to users with a wide range of screen sizes, we plan to optimize the user interface for additional screen resolutions so that none of the elements are cut off or hidden from view, regardless of the screen size.

#### 7. **Allow integer values above 2,147,483,647**
Although it is highly unlikely that an integer value would exceed 2,147,483,647 under normal usage, the system currently only supports integer values up to 2,147,483,647. To accommodate larger values, we plan to update the system to allow for integer values above this limit. This will ensure that you can input and manage larger values without encountering any issues related to the integer limit.
