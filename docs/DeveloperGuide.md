---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# Tuteez Developer Guide

<!-- TOC start -->

- [**Acknowledgements**](#acknowledgements)
- [**Setting up, getting started**](#setting-up-getting-started)
- [**Design**](#design)
   * [Architecture](#architecture)
   * [UI component](#ui-component)
      + [Command Box Component](#command-box-component)
   * [Logic component](#logic-component)
   * [Model component](#model-component)
   * [Storage component](#storage-component)
   * [Common classes](#common-classes)
- [**Implementation**](#implementation)
   * [Find feature](#find-feature)
      + [Implementation details](#implementation-details)
   * [Design Considerations](#design-considerations)
   * [Detection of next lesson](#detection-of-next-lesson)
   * [Display feature](#display-feature)
- [**Documentation, logging, testing, configuration, dev-ops**](#documentation-logging-testing-configuration-dev-ops)
- [**Appendix: Requirements**](#appendix-requirements)
   * [Product scope](#product-scope)
   * [User stories](#user-stories)
   * [Use cases](#use-cases)
   * [Non-Functional Requirements](#non-functional-requirements)
   * [Glossary](#glossary)
- [**Appendix: Effort**](#appendix-effort)
- [**Appendix: Planned Enhancements**](#appendix-planned-enhancements)
- [**Appendix: Instructions for manual testing**](#appendix-instructions-for-manual-testing)
   * [Launch and shutdown](#launch-and-shutdown)
   * [Deleting a student](#deleting-a-student)
   * [Adding a student](#adding-a-student)
   * [Editing a student](#editing-a-student)
   * [Adding a lesson to a student](#adding-a-lesson-to-a-student)
   * [Deleting a lesson from a student](#deleting-a-lesson-from-a-student)
   * [Adding a remark to a student](#adding-a-remark-to-a-student)
   * [Deleting a remark from a student](#deleting-a-remark-from-a-student)
   * [Displaying a student](#displaying-a-student)
   * [Navigating through command history](#navigating-through-command-history)
   * [Saving data](#saving-data)

<!-- TOC end -->

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).
* Libraries used: [JavaFX](https://openjfx.io/), [Jackson](https://github.com/FasterXML/jackson), [JUnit5](https://github.com/junit-team/junit5)

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

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-F09-4/tp/blob/master/src/main/java/tuteez/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-F09-4/tp/blob/master/src/main/java/tuteez/MainApp.java)) is in charge of the app launch and shut down.
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

#### Command Box Component

The `CommandBox` component is responsible for handling user command inputs and managing command history navigation.

**Handling user inputs:**
Below is the sequence diagram illustrating how the `CommandBox` processes user inputs:

<puml src="diagrams/CommandBoxSequenceDiagram.puml" alt="Sequence Diagram for CommandBox command execution"/>

How command execution works:
1. When the user types a command and presses Enter, the `CommandBox` retrieves the command text from the `TextField`.
2. If the command is not empty, it is passed to the `CommandExecutor` for execution.
3. Upon successful execution:
   - The command is added to the `CommandHistory` for tracking previous commands.
   - The text field is cleared.
4. If a `CommandException` or `ParseException` occurs:
   - The error style class is added to the text field to indicate error.
   - The command text will not be added to the `CommandHistory`.

**Command History Navigation:**
Below is the sequence diagram showing the command history navigation:
<puml src="diagrams/CommandBoxSequenceDiagram2.puml" alt="Sequence Diagram for CommandBox history navigation"/>

- UP Arrow Key: Retrieves the previous command from the `CommandHistory` when pressed.
- DOWN Arrow Key: Retrieves the next command from the `CommandHistory` when pressed.
- After retrieving a command (previous or next), the `CommandBox` updates the `TextField` with this command and moves the cursor to the end of the text.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-F09-4/tp/blob/master/src/main/java/tuteez/logic/Logic.java)

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
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-F09-4/tp/blob/master/src/main/java/tuteez/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="550" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="550" />

</box>

<box type="info" seamless>

**Note:** While TelegramUsername, Email, and Address are optional fields in the application, their multiplicities associated to the `Person` class in the diagram remains `1`. This is implemented through a design pattern where these attributes are represented by non-null wrapper objects that can internally contain null values.

</box>

**API** : [`LessonManager.java`](https://github.com/AY2425S1-CS2103T-F09-4/tp/blob/master/src/main/java/tuteez/model/person/lesson/LessonManager.java)

<puml src="diagrams/LessonClassDiagram.puml" width="450" />

The `LessonManager` component,
* stores all the info regarding `Lesson` objects.`LessonManager.dayLessonsMap` is a `HashMap` where keys are `Day`s, intuitively there are only 7 keys in the `HashMap`. One for each day of the week.
* uses a `TreeSet<Lesson>` as value of `LessonManager.dayLessonMap`. A `TreeSet` is used to maintain ordering of lessons. Lessons are ordered according to `Lesson.startTime`
* is the main class that determines any overlapping or clashing lessons for lessons within the memory.

<puml src="diagrams/LessonManagerDetectClashingLessons.puml" width="1000" />

The diagram above shows how clashing lessons are detected,

* `clashingLessonsMap` is a `Map` where each key is a student whose lessons conflict with a newly proposed lesson. The value associated with each key is an array of that student’s specific lessons that clash with the proposed lesson.

<box type="info" seamless>

Note: As of `v1.5` clashing lessons are not allowed, hence when a `Person` is deleted, all of his/her lessons can be safely deleted as well. No other students will have the same lesson time. The team is considering allowing clashing lessons after warning users for a future release.

</box>

### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-F09-4/tp/blob/master/src/main/java/tuteez/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)



### Common classes

Classes used by multiple components are in the `tuteez.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Find feature

#### Implementation details

The `find` command involves searching for keywords that match the student's details.

It uses `FindCommandParser` to parse the user input and create a `FindCommand` object, which modifies the `Person` object in the `Model`.

The following sequence diagram demonstrates the flow for the `find` command:

<puml src="diagrams/FindSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `find n/john` Command" />

<box type="info" seamless>

**Note:** The lifeline for `FindCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

### Design Considerations

Aspect: Search target differentiated by prefixes
- Alternative 1 (Current choice): Prefixes (e.g., n/ for name) specify the field within the studet's details to search, followed by the desired search target (e.g. n/Alice)
    - Pros:
        - Allows for more accurate searches
    - Cons
        - Users must remember prefixes
- Alternative 2: No differentiation of search targets
    - Pros:
        - More straightforward to type out
    - Cons:
        - May result in ambiguous commands, leading to incorrect or incomplete searches.

### Detection of next lesson

On the left panel, which displays only the most important information, we show each student’s upcoming lesson based on the computer’s current time.

High level idea:

* For each student, calculate the duration remaining until the start of each of their lessons based on devices current time.
* Identify the lesson with the shortest remaining time and display it as their next scheduled lesson.
* Returns null if a student has no lessons scheduled.

How this feature works:

1. Every minute, UI calls `Person#nextLessonBasedOnCurrentTime` on every student to consistently keep track of a students next lesson
1. `Person#nextLessonBasedOnCurrentTime` calls `Lesson#durationTillLesson` for all of student's lessons which returns `Duration` objects
1. Then leverage `Duration#compareTo` to get the lesson with the shortest duration
1. Return lesson with shortest duration or `null` if student has no lessons


### Display feature
The `DisplayCommand` allows users to display a specified person in the addressbook.
It uses `DisplayCommandParser` to parse the user input and create an `DisplayCommand` object, which modifies the `lastViewedPerson` object in the `Model`.

The following sequence diagram illustrates the interactions that take place within the `Logic` component when the user executes the `addRemarkCommand`, taking `execute("addremark 1 r/Good progress")` API call as an example.

<puml src="diagrams/DisplaySequenceDiagram.puml" alt="Interactions Inside the Logic Component when a display command is called" />


How this feature works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command `display` (i.e., `DisplayCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DisplayCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed.<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it takes several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

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

* tech-savvy full-time tuition teacher
* exclusively teach 1-1 tuition (no group lessons)
* Teaches a regular weekly schedule
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Our app serves as an address book tailored for tech-savvy full-time tuition teachers who prefer to use CLI, streamlining the management of student schedules and contact details. It effectively prevents scheduling conflicts and allows for quick access to essential information, enabling teachers to prioritise teaching over administration.

**Note:**

* It is possible that the target user changes or its scope is widened in the future

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​              | I want to …​                                                                                                                          | So that I can…​                                                                                                 |
|---------|----------------------|---------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------|
| `* * *` | new user             | add my students' contact details                                                                                                      | easily access and communicate with them or their guardians                                                      
| `* * *` | new user             | search for a student's name                                                                                                           | find relevant student(s) easily                                                                                 |
| `* * *` | new user             | delete students' entries                                                                                                              | remove students that I am no longer teaching                                                                    |
| `* * *` | new user             | easily access my tutoring schedule with each student                                                                                  | stay organised and manage my records more effectively                                                           
| `* * *` | new user             | be automatically alerted if there are scheduling conflicts when adding a new student whose tuition time overlaps with another student | quickly adjust their schedule and avoid double-booking                                                          |
| `* * *` | new user             | organise my students' contact details                                                                                                 | find my students' by certain categories easily                                                                  |
| `* * *` | new user             | have an option to store the address of the students                                                                                   | easily go to the student's house if the tuition session is in person                                            |
| `* * *` | impatient user       | be able to add a task within 15 seconds                                                                                               | use the app in a rush                                                                                           |
| `* * *` | impatient user       | be able to load up the app with the main user interface within 1-2 seconds                                                            | use the app seamlessly                                                                                          |
| `* * *` | intermediate user    | schedule classes that repeats every week                                                                                              | not  keep scheduling classes on the same day(s)                                                                 |
| `* * *` | intermediate user    | filter the entries by teaching date, such as “today” or “tomorrow” or "next 3 days"                                                   | quickly view and manage the students I am teaching on specific days                                             |
| `* * *` | intermediate user    | be able to prevent duplicate student entries                                                                                          | avoid confusion when managing students with similar names                                                       |
| `* * *` | expert user          | leave notes on a student's entry to track their learning progress                                                                     | monitor and adjust my teaching strategies effectively                                                           |
| `* * *` | expert user          | be able to use keyboard shortcuts                                                                                                     | quickly add, delete and update students information                                                             ||           |                                            | leave notes on a student's entry to track their learning progress                             |                                                                      |
| `* *`   | new user             | have a guided tour or input guide                                                                                                     | know how to add students to the address book                                                                    |
| `* *`   | new user             | clear all the app data quickly                                                                                                        | delete data that was used when experimenting with the app.                                                      |
| `* *`   | new user             | customise my own style of formatting by choosing from a few different options                                                         | not  follow a single formatting option instructed by the product                                                |
| `* *`   | long screentime user | filter away data that I deem unnecessary                                                                                              | stay undistracted from other data                                                                               |
| `* *`   | intermediate user    | copy information of my students to my clipboard                                                                                       | I can send them their invoice or notes quickly                                                                  |
| `* *`   | intermediate user    | filter entries by the payment status for the month                                                                                    | easily identify who has paid and who has not, enabling me to send invoices only to the students who are overdue |
| `* *`   | intermediate user    | export student data in various formats (CSV, Excel, WhatsApp message etc.)                                                            | students can view their progress easily, and it is easy for students to view it on their end                    |
| `* *`   | intermediate user    | record and track any special needs or accommodations required by my students                                                          | adapt my teaching style or lesson content appropriately                                                         |
| `* *`   | intermediate user    | customise the user interface (color scheme, font size) of the app                                                                     | make it visually appealing and easy to use according to my preferences                                          |
| `* *`   | intermediate user    | track each student’s performance in specific subjects (e.g. Math, Science)                                                            | identify their strengths and weaknesses in different areas                                                      |
| `* *`   | intermediate user    | record whether each student prefers online or in-person tutoring sessions                                                             | plan for travelling if needed                                                                                   |
| `* *`   | impatient user       | quickly search for a student using autocomplete or suggestions as I type the student’s name                                           | find a student quickly                                                                                          |
| `* *`   | forgetful user       | see students that I am teaching today once I open the address book                                                                    | reminded of who I need to teach today                                                                           |
| `* *`   | expect user          | track students' exam results and view their progress over time                                                                        | update my teaching style to better meet their needs                                                             |
| `* *`   | expert user          | quickly filter by important dates, such as upcoming exams                                                                             | effectively tailor my lessons to prepare students in a timely manner                                            |
| `*  `   | new user             | undo an operation                                                                                                                     | undo a command if it was an mistake                                                                             |
| `* `    | new user             | import student data in various formats (e.g., CSV, Excel)                                                                             | quickly see how the product will look when populated                                                            |
| `*`     | intermediate user    | set up automated reminders for upcoming lessons                                                                                       | adequately prepare for my lessons                                                                               |
| `*`     | intermediate user    | attach lesson materials, homework assignments, or additional resources to each student's profile                                      | easily share and track what I’ve assigned to each student                                                       |
| `*`     | intermediate user    | filter and view students who have upcoming exams                                                                                      | prioritise revision sessions and prepare them effectively                                                       |
| `*`     | forgetful user       | add a profile picture to my students recognise my student by picture                                                                  | recognise my student by picture and contact them without remembering their name                                 |
| `*`     | expert user          | have some tips of the day / updates on more advanced features                                                                         | use the app seamlessly                                                                                          |
| `*`     | expert user          | automate the process of sending invoice reminders to students or parents                                                              | manage payments efficiently and eliminate the need for manual tracking and individual messaging                 |
| `*`     | expert user          | type a similar command to an actual one and get it to work                                                                            | not just adhere to a specific format restricted by the app                                                      |


### Use cases

(For all use cases below, the **System** is `Tuteez` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC1 - Add a student**

**MSS**

1. User requests to add a student, entering relevant student details.
2. System acknowledges that a new student has been added.
3. Use case ends.

**Extensions**

* 1a. System detects an identical name.

    * 1a1. System rejects the new addition and informs user about the error.

    Use case resumes at step 1.

* 1b. System detects clashing lesson.

    * 1b1. System rejects the new addition and informs user about the clashing lessons.

    Use case resumes at step 1.

* 1c. The given command format is invalid.

    * 1c1. System rejects the new addition and informs user about the invalid command format.

  Use case resumes at step 1.


**Use case: UC2 - List all students**

**MSS**

1. User requests to list all students.
2. System displays a list of all students.
3. Use case ends.

**Extensions**

* 1a. The list of all students is empty.

    Use case ends.

**Use case: UC3 - Delete a student**

**MSS**

1.  User requests to list students.
2.  System shows a list of students <u>(UC2)</u>.
3.  User requests to delete student using index or name.
4.  System deletes the person.
5.  Use case ends.


**Extensions**

* 1a. The list is empty.

  Use case ends.

* 3a. The given student index is invalid.

  * 3a1. System informs user of invalid student index.

      Use case resumes at step 3.
  
* 3b. The given name does not exist.

  * 3b1. System informs user that student name is not found.
        
      Use case resumes at step 3.

**Use case: UC4 - Add a lesson to a student**

**MSS**

1. User requests to list students.
1. System shows a list of students <u>(UC2)</u>.
1. User requests to add lesson, selecting student by index and entering lesson day and time.
1. System adds lesson to student.
1. Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

* 3a. The lesson clashes with an existing lesson(s).

   * 3a1. System informs user of the clashing lesson(s).

   Use case resumes at step 3.

* 3b. The lesson has invalid day or time.

   * 3b1. System informs user of invalid day or time.

   Use case resumes at step 3.

* 3c. The given command format is invalid.

    * 3c1. System informs user of invalid command format.

    Use case resumes at step 3.

* 3d. The given student index is invalid.

   * 3d1. System informs user of invalid student index.

   Use case resumes at step 3.

**Use case: UC5 - Delete a lesson from a student**

**MSS**

1. User requests to list students.
2. System shows a list of students <u>(UC2)</u>.
3. User requests to delete lesson, selecting student by index and specifying the lesson index to delete.
4. System deletes the lesson from the student.
5. Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

* 3a. The given student index is invalid.
  
    * 3a1. System informs user of invalid student index.
  
    Use case resumes at step 3.

* 3b. The given lesson index is invalid.

    * 3b1. System informs user of invalid lesson index.
  
    Use case resumes at step 3.

* 3c. The given command format is invalid.

    * 3c1. System informs user of invalid command format.

    Use case resumes at step 3.

**Use case: UC6 - Add a remark to a student**

**MSS**

1. User requests to list students.
2. System shows a list of students <u>(UC2)</u>.
3. User requests to add remark, selecting student by index and specifying the remark to be added.
4. System adds the remark to the student.
5. Use case ends.

**Extensions**

* 1a. The list is empty.

    Use case ends.

* 3a. The given student index is invalid.

    * 3a1. System informs user of invalid student index
  
    Use case resumes at step 3.

* 3b. The given remark is invalid.

    * 3b1. System informs user of invalid remark.

    Use case resumes at step 3.

* 3c. The given command format is invalid.

    * 3c1. System informs user of invalid command format.

    Use case resumes at step 3.

**Use case: UC7 - Delete a remark from a student**

**MSS**

1. User requests to list students.
2. System shows a list of students <u>(UC2)</u>.
3. User requests to delete remark, selecting student by index and specifying the remark index to delete.
4. System deletes the remark from the student.
5. Use case ends.

**Extensions**

* 1a. The list is empty.

    Use case ends.

* 3a. The given student index is invalid.

    * 3a1. System informs user of invalid student index.
  
    Use case resumes at step 3.

* 3b. The given remark index is invalid.

    * 3b1. System informs user of invalid remark index.
  
    Use case resumes at step 3.

* 3c. The given command format is invalid.

    * 3c1. System informs user of invalid command format.
    
    Use case resumes at step 3.

**Use case: UC8 - Editing a student**

**MSS**

1. User requests to list students.
2. System shows a list of students <u>(UC2)</u>.
3. User requests to edit, selecting student by index and specifying the details to delete.
4. System edits the student's details.
5. Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

* 3a. The given student index is invalid.

    * 3a1. System informs user of invalid student index.

    Use case resumes at step 3.

* 3b. The given command format is invalid (e.g., missing name or phone number field).

    * 3b1. System informs user of invalid command format.

    Use case resumes at step 3.

* 3c. The edited name matches the name of another student in the list.

    * 3c1. System informs user of naming conflict.
  
    Use case resumes at step 3.

* 3d. The field specified for editing contains invalid input.

    * 3d1. System informs user of the problematic field.

    Use case resumes at step 3.

**Use case: UC8 - Displaying a student**

**MSS**

1. User requests to list students.
2. System shows a list of students <u>(UC2)</u>.
3. User requests to display a student, selecting the student by name or index.
4. System displays student details in the right panel.
5. Use case ends.

* 1a. The list is empty.

  Use case ends.

* 3a. The given student index is invalid.

    * 3a1. System informs user of invalid student index.

  Use case resumes at step 3.

* 3b. The given student name is invalid.

    * 3b1. System informs user of invalid student name.

  Use case resumes at step 3.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 contacts without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The system should respond to user commands within 15 seconds.
5.  The application should launch within 2 seconds.
6.  The application should not store sensitive information (e.g., NRIC numbers) without explicit user consent.
7.  The application should demonstrate stability during long-term usage like after a few hours without crashing or freezing.
8.  The application should be usable by novice users without extensive training.
9.  The application should handle errors gracefully, providing meaningful feedback to users in case of failures.
10. The user guide and the developer guide should be PDF-friendly. (No expandable panels, embedded videos, animated GIFs etc.)


### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Student**: A person who is taking lessons from the tutor
* **Tutor**: The user of the application
* **addlsn**: An abbreviation for the addlesson command
* **dellsn**: An abbreviation for the deletelesson command
* **addrmk**: An abbreviation for the addremark command
* **delrmk**: An abbreviation for the deleteremark command
* **Scheduling conflicts**: Overlapping lesson times when a tutor has more than one lesson at a specific time
* **Tags**: Labels that can be assigned to students to group them based on common characteristics
* **Remarks**: Longer texts that can be added to students
* **CLI**: Command Line Interface, a type of text-based user interface that allows users to interact with the application by typing commands
* **GUI**: Graphical User Interface, a type of visual user interface that allows users to interact with the application through graphical elements like buttons and menus
--------------------------------------------------------------------------------------------------------------------
## **Appendix: Effort**

This project presented a significant challenge due to the complexity and precision required at each development stage. Building a responsive and visually cohesive UI was one of the main hurdles. Ensuring that all elements fit together seamlessly took considerable time and required constant adjustments. Achieving a layout that is both user-friendly and adaptable to different screen sizes was especially challenging, as even minor alignment issues could disrupt the overall flow of the interface.

Implementing specific features, such as displaying the next lesson on the student card, added another layer of difficulty. This feature necessitated setting up a timer that refreshes every minute, which required careful attention to performance so that the app wouldn’t slow down or consume excessive resources. Managing this timer involved designing an efficient refresh mechanism that updates only what’s necessary, minimizing memory use while providing real-time updates.

Handling clashing lesson schedules introduced further complexity. We had to account for numerous edge cases—students with overlapping or consecutive classes. Designing a robust conflict-checking system meant anticipating all potential scheduling scenarios and validating inputs carefully, adding to the development time and requiring extensive testing.

In addition, adding custom features like remarks and lesson tracking posed challenges in both design and functionality. Error handling had to be highly specific and informative, meaning that we could not rely on generic error messages. Each action, whether adding a lesson or a remark, needed unique error handling to guide users effectively without overwhelming them with unnecessary information. Crafting these targeted error messages involved considering possible user errors and ensuring that each message communicated the issue clearly while remaining concise.

The effort invested in this project was substantial, as we went beyond the base features of AB3, adding numerous enhancements and custom functionalities. Specifically, we added a **lessons** feature to track and manage student schedules, developed a system to handle **clashing lessons** and ensure no overlaps, integrated a **remarks** feature for notes on students, and created a **display** command to quickly pull up detailed student information. We overhauled the UI to create a more intuitive and visually appealing interface, which required meticulous attention to design and usability. Additionally, we modified many existing commands in AB3 like add and edit.

We also put significant focus on refining error handling. Every error message was crafted to be clear, informative, and specific to each potential issue, guiding users more effectively through the app. Balancing all these improvements while maintaining a high standard of quality and consistency demanded extensive effort, but it enabled us to deliver a more robust and user-friendly experience.

## **Appendix: Planned Enhancements**
Group size: 5 members

1. Student identification: 
   - Our app uniquely identifies students by name. In future updates, we plan to use a combination of name **and** phone number for unique identification.
      - Two students cannot share the same name and the same phone number.
      - Allowing different names with the same phone number accommodates cases where tutors of younger students may store siblings under different names but with a shared contact number, typically a parent’s.
1. Optimise add command:
   - Our app does not support adding remarks while adding new student. 
   - In future updates, we can add this feature to grant our users more flexibility.
1. Optimise edit command:
   - Our app does not support direct editing of lessons or remarks. 
   - In future updates, we plan to enable users to edit existing lessons and remarks without needing to delete and re-add them.
1. Find parameter checking:
   - For name, tag and address keywords, the parser only checks that they are not blank.
   - In future updates, we plan to alert users when the keywords are invalid (ie valid when only contains certain characters).
1. Find command Behavior:
   - After using the find command to locate student(s), executing most other commands will reset the left panel to the default view, as if the `list` command was called. 
   - In future updates, we will rectify this issue.
1. Remark Constraints:
   - We do not allow remarks to contain `r/`. 
   - In future updates, allow `r/`.
1. Find behavior:
   - The find command uses the `or` constraint, while this can provide more flexibility there are cases where users want to search for long sequence of `Strings` such as "Serangoon Gardens Rd".
   - In future updates, we can provide users some way to indicate certain target words as a group and apply `and` constraint.
1. Remarks added one at a time:
   - `addrmk` only allows users to add one remark at a time.
   - In future updates, allow users to add multiple.
1. Remarks deleted one at a time:
   - `delrmk` only allows users to delete one remark at a time.
   - In future updates, allow users to delete multiple.
1. Right panel enhancement:
   - Currently, the right panel automatically scrolls to the top each time the user edits any student details.
   - In the future, we plan to refine this feature so that the panel scrolls directly to the section where the user made edits, providing a more seamless and user-friendly experience.


## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder.

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.


### Deleting a student

1. Deleting a student by index

   1. Prerequisites: List all students using the `list` command. Multiple students in the list.

   1. Test case: `delete 1`.<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message.

   1. Test case: `delete`.<br>
      Expected: No student is deleted. Error details shown in the status message.

   1. Other incorrect delete commands to try: `delete 0`, `delete x` (where x is larger than the list size) <br>
      Expected: Invalid student index error is shown in the status message.

1. Deleting a student by name

    1. Prerequisites: The student list contains only a student with the name "John".

    1. Test case: `delete john`.<br>
       Expected: John will be deleted from the list. Details of john will be shown in the status message.
   
    1. Test case: `delete Alice`.<br>
       Expected: No student is deleted. An error will be displayed in the status message.

### Adding a student

1. Adding a student with just name and phone number

   1. Prerequisites: List all students using the `list` command. No student with the name "John" in the list.

   1. Test case: `add n/john p/82223238`.<br>
      Expected: A new student will be added to the list. The details of the student will be shown in the status message.

1. Adding a student with a duplicate name
    
    1. Prerequisites: The student list contains only a student with the name "John".
    
    1. Test case: `add n/john p/82223238`.<br>
        Expected: No person is added. A duplicate student error will be displayed in the status message.

1. Adding a student with a clashing lesson
    
    1. Prerequisites: The student list contains only a student with the name "John" with only a lesson on "MONDAY 0900-1000".
    
    1. Test case: `add n/Alice p/82223938 l/monday 0900-1000`.<br>
        Expected: No person is added. A clashing lesson error will be displayed in the status message.

### Editing a student

1. Editing a student with index
   
    1. Prerequisites: List all students using the `list` command. There are already students in the student list. No student with the name "Alice" in the list.

    1. Test case: `edit 1 n/Alice`.<br>
        Expected: Name of the first student in the list will be updated to "Alice".
   
    1. Test case: `edit`.<br>
       Expected: No student is edited. Error details shown in the status message.
   
    1. Other incorrect edit commands to try: `edit 0 n/Alice`, `edit x n/Alice` (where x is larger than the list size) <br>
       Expected: Invalid student index error is shown in the status message.

### Adding a lesson to a student

1. Adding a lesson to a student using index

    1. Prerequisites: List all students using the `list` command. There is only one student in the student list.
    
    1. Test case: `addlesson 1 l/monday 0900-1100`. <br>
        Expected: A lesson on "MONDAY 0900-1100" will be added to the student. The student's details should be displayed on the right panel. Lesson successfully added is shown in the status message.
    
    3. Test case: `addlesson 1 l/monday 1200-1300 l/monday 1200-1300`. <br>
        Expected: No lesson is added. A clashing lesson error will be displayed in the status message.

    4. Test case: `addlesson 1 l/tuesday 1000-1100 l/tuesday 1100-1200`. <br>
        Expected: Lessons on "TUESDAY 1000-1100" and "TUESDAY 1100-1200" will be added to the student. The student's details should be displayed on the right panel. Lessons successfully added are shown in the status message.

### Deleting a lesson from a student

1. Deleting a lesson from a student using index
    
    1. Prerequisites: List all students using the `list` command. There is only one student in the student list with only one lesson on "MONDAY 0900-1000".
    
    1. Test case: `deletelesson 1 li/1`.<br>
       Expected: The lesson "MONDAY 0900-1000" will be deleted from the student. The student's details should be displayed on the right panel. Lesson successfully deleted is shown in the status message.

    3. Test case: `deletelesson`. <br>
       Expected: No lesson is deleted. Error details shown in the status message.

    4. Other incorrect deletelesson commands to try: `deletelesson 0 li/1`, `deletelesson x li/1` (where x is larger than the list size) <br>
       Expected: Invalid lesson index error is shown in the status message.

### Adding a remark to a student

1. Adding a remark to a student using index

    1. Prerequisites: List all students using the `list` command. There is only one student in the student list.
    
    1. Test case: `addremark 1 r/Midterms coming up` <br>
       Expected: The remark "Midterms coming up" will be added to the student. The student's details should be displayed on the right panel. Remark successfully added is shown in status message.

### Deleting a remark from a student

1. Deleting a remark from a student using index

    1. Prerequisites: List all students using the `list` command. There is only one student in the student list with only one remark "Midterms coming up".

    1. Test case: `deleteremark 1 ri/1` <br>
       Expected: The remark "Midterms coming up" will be deleted from the student. The student's details should be displayed on the right panel. Remark successfully deleted is shown in the status message.
    
### Displaying a student

1. Displaying a student using index

1. Prerequisites: List all students using the `list` command. Multiple students in the list.

    1. Test case: `display 1`<br>
       Expected: Details of first student is displayed on the right panel. A message confirming successful display of the student is shown in the status message.
    
    1. Test case: `display`<br>
       Expected: No student is displayed. Error details shown in the status message.
   
    1. Test case: `display x` (where x is larger than the list size) <br>
       Expected: No student is displayed. Invalid student index is shown in the status message.

### Navigating through command history

1. Navigating to your previous command

    1. Prerequisite: You have just entered the command `list`.

    1. Test case: Press the `UP` arrow key. <br>
       Expected: The previous command `list` is shown in the command box.

1. Navigating to your next command

    1. Prerequisite: You have entered the `list` command followed by the `help` command. Close the help window.

    1. Test case: Press the `UP` arrow key once. <br>
       Expected: You would see the `help` command in the command box.
   
    1. Test case: Press the `UP` arrow key twice. <br>
        Expected: You would see the `list` command in the command box.

    1. Test case: Press the `UP` arrow key twice then press the `DOWN` arrow key once. <br>
       Expected: You would see the `help` command in the command box.

    1. Test case: Press the `UP` arrow key once then press the `DOWN` arrow key once. <br>
       Expected: The command box should be cleared.
   
### Saving data

1. Dealing with missing/corrupted data files

   1. Test case: Delete the data file from the directory containing tuteez.jar to simulate missing file. <br>
      Expected: A new data file will be automatically created with default set of "dummy" students when tuteez.jar is run.
