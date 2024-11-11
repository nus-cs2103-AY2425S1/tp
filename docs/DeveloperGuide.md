---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# EduManage Developer Guide

## Table of Contents

[1. Setting Up, Getting Started](#1-setting-up-getting-started)

[2. Design](#2-design)
* [2.1 Architecture](#2-1-architecture)
* [2.2 UI Component](#2-2-ui-component)
* [2.3 Logic Component](#2-3-logic-component)
* [2.4 Model Component](#2-4-model-component)
* [2.5 Storage Component](#2-5-storage-component)
* [2.6 Common Classes](#2-6-common-classes)

[3. Implementation](#3-implementation)
* [3.1 Lesson Time Parameter](#3-1-lesson-time-parameter)
  * [3.1.1 Design Considerations](#3-1-1-design-considerations)
* [3.2 Add Feature](#3-2-add-feature)
  * [3.2.1 Implementation - Activity Diagram](#3-2-1-implementation-activity-diagram)
  * [3.2.2 Design Considerations](#3-2-2-design-considerations)
* [3.3 Delete Feature](#3-3-delete-feature)
  * [3.3.1 Implementation - Sequence Diagram](#3-3-1-implementation-sequence-diagram)
  * [3.3.2 Design Considerations](#3-3-2-design-considerations)
* [3.4 Tag Feature](#3-4-tag-feature)
  * [3.4.1 Implementation - Sequence Diagrams](#3-4-1-implementation-sequence-diagrams)
  * [3.4.2 Design Considerations](#3-4-2-design-considerations)
* [3.5 View Specific Student Feature](#3-5-view-specific-student-feature)
  * [3.5.1 Implementation - Sequence Diagram](#3-5-1-implementation-sequence-diagram)
  * [3.5.2 Design Considerations](#3-5-2-design-considerations)
* [3.6 Add Task Feature](#3-6-add-task-feature)
  * [3.6.1 Implementation - Sequence Diagram](#3-6-1-implementation-sequence-diagram)
  * [3.6.2 Design Considerations](#3-6-2-design-considerations)

[4. Documentation, Logging, Testing, Configuration, Dev-Ops](#4-documentation-logging-testing-configuration-dev-ops)

[5. Appendix: Requirements](#5-appendix-requirements)
* [5.1 Product Scope](#5-1-product-scope)
* [5.2 User Stories](#5-2-user-stories)
* [5.3 Use Cases](#5-3-use-cases)
* [5.4 Non-Functional Requirements](#5-4-non-functional-requirements)
* [5.5 Glossary](#5-5-glossary)

[6. Appendix: Instructions for Manual Testing](#6-appendix-instructions-for-manual-testing)
* [6.1 Launch and Shutdown](#6-1-launch-and-shutdown)
* [6.2 Adding a Student](#6-2-adding-a-student)
* [6.3 Deleting a Student](#6-3-deleting-a-student)
* [6.4 Finding Specific Students](#6-4-finding-specific-students)
* [6.5 Adding a Task to a Student](#6-5-adding-a-task-to-a-student)

[7. Appendix: Planned Enhancements](#7-appendix-planned-enhancements)
* [7.1 Update subject and lesson time cumulatively](#7-1-update-subject-and-lesson-time-cumulatively)
* [7.2 Names with special characters](#7-2-names-with-special-characters)
* [7.3 Multiple students with the same name](#7-3-multiple-students-with-the-same-name)
* [7.4 Usage of reserved prefixes in parameters](#7-4-usgae-of-reserved-prefixes-in-parameters)

--------------------------------------------------------------------------------------------------------------------

## 1. Setting Up, Getting Started

Refer to the guide [_Setting up and getting started_](SettingUp.md).

[(Back to Top)](#edumanage-developer-guide)

--------------------------------------------------------------------------------------------------------------------

## 2. Design

### 2.1 Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-W08-3/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-W08-3/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#3-2-ui-component): The UI of the App.
* [**`Logic`**](#3-3-logic-component): The command executor.
* [**`Model`**](#3-4-model-component): Holds the data of the App in memory.
* [**`Storage`**](#3-5-storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#3-6-common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

***

### 2.2 UI Component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-W08-3/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g., `CommandBox`, `ResultDisplay`, `StudentListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-W08-3/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-W08-3/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands and update the UI state accordingly.
* depends on some classes in the `Model` component, as it displays `Student` object residing in the `Model`.

The activity diagram below represents the flow of actions within the UI based on user commands, illustrating how the display is adjusted depending on the command's impact on the UI state.

<puml src="diagrams/UiActivityDiagram.puml" alt="Workflow of Updating the UI State"/>

***

### 2.3 Logic Component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-W08-3/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

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
1. The command can communicate with the `Model` when it is executed (e.g. to delete a student).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

***

### 2.4 Model Component

**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-W08-3/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="750" />

The `Model` component,

* stores the address book data i.e., all `Student` objects (which are contained in a `UniqueStudentList` object).
* stores the currently 'selected' `Student` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Student>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

***

### 2.5 Storage Component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-W08-3/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="750" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

***

### 2.6 Common Classes

Classes used by multiple components are in the `seedu.address.commons` package.

[(Back to Top)](#edumanage-developer-guide)

--------------------------------------------------------------------------------------------------------------------

## 3. Implementation

This section describes some noteworthy details on how certain features and parameters are implemented. For all examples
below, the user is the tuition teacher, unless specified otherwise.

### 3.1 Lesson Time Parameter
This parameter allows users to keep track of a student's lesson timings. Multiple lesson times can be added for a single student.

#### 3.1.1 Design Considerations
**Treatment of clashing timings**
- **Current Implementation (Alternative 1):**
    - **Description**: Clashing lesson timings are permitted across all students and within each student's schedule.
    - **Pros**: Provides greater user flexibility, allowing all specified timings to be stored in EduManage, even if they overlap.
      Suitable for accommodating different schedules for alternate weeks or varying lesson arrangements.
    - **Cons**: Incorrect or conflicting lesson timings are not flagged out to the user, which could lead to scheduling confusion.

- **Alternative 2:**
    - **Description**: Clashing lesson timings are rejected across all students and within each student's schedule.
    - **Pros**: Ensures that incorrect or conflicting timings are flagged, helping the user avoid scheduling errors.
    - **Cons**: Reduces flexibility for users who need to store complex schedules with occasional overlapping times.

**Association between lesson timings and subjects**
- **Current Implementation (Alternative 1):**
    - **Description**: Each lesson timing is not associated with any subject.
    - **Pros**: Promotes greater user flexibility, allowing users to adjust subject focus within each lesson as needed.
      Suitable for scenarios where multiple subjects may be covered in a single lesson.
    - **Cons**: Users might prefer to have the association made clear.

- **Alternative 2:**
    - **Description**: Associate each lesson timing with a specific subject.
    - **Pros**: Clarifies what subject is to be taught during each lesson, helping users stay organised.
    - **Cons**: Reduces user flexibility in arranging lesson times for different subjects.

***

### 3.2 Add Feature
The Add feature allows users to register a new student in EduManage with a range of details, such as name, phone number, emergency contact, address, level, subject(s), and lesson time(s). Users can specify a single level, multiple subjects, and multiple lesson timings per student to tailor profile details for academic tracking and scheduling.

- **Duplicate Prevention**: If a student with identical details already exists, EduManage will prevent the addition and
  notify the user with an error message.
- **Level and Subject Compatibility**: When specified, the level and subjects are validated to ensure compatibility,
  helping to avoid entry errors.
- **Flexible Optional Fields**: Fields like level, subject, and lesson time are optional, providing flexibility in the
  amount of detail added for each student.

#### 3.2.1 Implementation - Activity Diagram
The activity diagram below highlights the various decision points during the process of adding a student, including
checks for valid command formats, parameter validity, matching subjects and levels, and whether the student already
exists in EduManage. If all checks pass, the student is successfully added to the EduManage, and the user receives a
success message. If any check fails, the user is notified with an appropriate error message.

<puml src="diagrams/AddActivityDiagram.puml" alt="AddActivityDiagram" />

#### 3.2.2 Design Considerations
**Centralized Validation in Subject Class**
- **Current Implementation (Alternative 1)**:
    - **Description**: Validation logic within the Subject class ensures that the list of valid subjects by level remains consistent. This makes it straightforward to update validation rules or allowed subjects.
    - **Pros**: Modularizes validation, reducing the complexity of AddCommand and providing reusable validation logic within Subject. 
    - **Cons**: Slightly increases the dependency on Subject for the AddCommand, but this trade-off is mitigated by improved maintainability.

- **Alternative 2**
    - **Description**: If validation were split between the AddCommand and Subject, each class would handle part of the validation. 
    - **Pros**: Reduces coupling between classes. 
    - **Cons**: Results in code duplication and reduced cohesion, complicating future changes in validation rules.

***

### 3.3 Delete Feature
This feature allows users to delete a student from EduManage based on their index in the displayed student list. The
process involves identifying the student by their index and removing their details from the database.

- **Index Validation**: EduManage checks if the provided index is valid and within the bounds of the current student
  list. If the index is out of bounds or invalid, an error message is shown.
- **Student Deletion**: Upon valid index input, the student is deleted from the model and the filtered student list is
  updated to reflect this change and a success message is shown.

#### 3.3.1 Implementation - Sequence Diagram
The sequence diagram below illustrates the interaction between various components during the execution of the
`DeleteCommand`. This includes validation of the index, the deletion of the student from the model, and updating the
filtered student list.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="DeleteSequenceDiagram" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

#### 3.3.2 Design Considerations
**Name vs Index for Deletion**
- **Current Implementation (Alternative 1):**
    - **Description**: The user identifies the student by their index in the displayed student list.
    - **Pros**: Simple, fast, and unambiguous with unique indexes.
    - **Cons**: Users must visually track and remember the index number of the student, which can be cumbersome if the list is long or filtered.

- **Alternative 2**:
    - **Description**: The user specifies the student’s name for deletion.
    - **Pros**: Names are easier for users to remember and identify, making the deletion process more intuitive.
    - **Cons**: Searching by name can be slower with a large contact list, and misspelling the name could lead to
      the accidental deletion of the wrong student.

***

### 3.4 Tag Feature
This feature allows users to tag a student's profile with specific details related to school level (e.g., `S1 NA`) and
subject(s) (e.g., `MATH`). By entering the student's name and specifying tags for level or subject(s) (or both), users can
manage student profiles more efficiently.

- **Adding Multiple Tags**: Users can specify multiple subject tags and one level tag for a student. Each time tags are
  added, they will override any previously existing tags on the profile, ensuring that only the latest tags are retained.
- **Clearing of Tags**: Users can remove all tags from a student's profile by entering l/NONE s/NONE, which effectively
  resets both level and subject tags, leaving the profile with no active tags.
- **Invalid Input**: If an invalid student name, level, or subject is inputted, EduManage displays the constraints and
  guidelines for tag parameters. Additionally, if there is a mismatch between the tagged subjects and the specified level,
  an error message will highlight the incompatibility.
- **Case Insensitivity**: Tags are designed to be case-insensitive. If users add multiple tags that are equivalent in
  value (e.g., `Math` and `MATH`), only one instance of each unique tag will be added, preventing unnecessary
  duplication.

#### 3.4.1 Implementation - Sequence Diagrams
The sequence diagrams below depicts the interaction among various classes during the execution of a tag command.

<puml src="diagrams/TagSequenceDiagram-Logic.puml" alt="TagSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `TagCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

<puml src="diagrams/TagSequenceDiagram-Model.puml" alt="TagSequenceDiagram-Model" />

#### 3.4.2 Design Considerations
**Parsing Tag Input**
- **Current Implementation (Alternative 1):**
    - **Description**: Tag validation is managed by the `ParserUtil` class, centralizing validation logic for improved
      maintainability and modularity.
    - **Pros**: By isolating validation in `ParserUtil`, updates and modifications are easier to manage, promoting a
      consistent approach across commands.
    - **Cons**: Adds a layer of abstraction, which may slightly increase EduManage’s complexity.

- **Alternative 2**:
    - **Description**: Validation occurs directly within `TagCommandParser`.
    - **Pros**: Keeps validation localized within the tag command, reducing dependencies on external classes.
    - **Cons**: Creates inconsistency across the codebase, making validation logic less reusable and harder to maintain.

**Design of Tag Constraints**
- **Current Implementation (Alternative 1)**:
    - **Description**: Tags are restricted to pre-defined values, and they are case-insensitive (e.g., `S1 NA` and
      `s1 na` are treated as identical, and `math` and `MATH` are treated as identical).
    - **Pros**: Enforces a standardized format for tags, ensuring brevity and uniformity.
    - **Cons**: Reduces user flexibility in customizing tags.

- **Alternative 2**:
    - **Description**: Users can create tags without specific constraints.
    - **Pros**: Provides greater flexibility for users to create custom tags.
    - **Cons**: Increases complexity in managing and validating user input, potentially leading to errors and inconsistencies.

***

### 3.5 View Specific Student Feature

This feature allows users to view the details of an existing student by specifying their name. It enables quick access
to a student's profile for viewing key information.

- **Viewing a Student's Profile**: Users can enter a student's name to view the corresponding details. EduManage will
  search for the student by name and display their profile if found.
- **Invalid Input**: If an invalid or non-existent student name is entered, EduManage will show an error message
  indicating that the student was not found.
- **Case Insensitivity**: EduManage treats student names case-insensitively, ensuring that variations in capitalization
  do not affect the search result.

#### 3.5.1 Implementation - Sequence Diagram
The sequence diagram below depicts the interaction among various classes during the execution of a view command.

<puml src="diagrams/ViewSequenceDiagram.puml" alt="ViewSequenceDiagram" />

<box type="info" seamless>

**Note:** The lifeline for `ViewCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

#### 3.5.2 Design Considerations
**Case Insensitivity and Name Matching**
- **Current Implementation (Alternative 1)**:
    - **Description**: Ignore cases when matching student names, ensuring that capitalization does not affect
      the search.
    - **Pros**: More flexible and user-friendly, as users do not have to match the exact case of the student's name.
    - **Cons**: Slight overhead in handling case conversion for name matching.

- **Alternative 2**:
    - **Description**: Require exact capitalization of the student's name for a match.
    - **Pros**: Ensures that no unnecessary case conversion is needed, which may slightly improve performance.
    - **Cons**: Increases the likelihood of user errors if the name is not entered exactly as stored.

***

### 3.6 Add Task Feature

This feature allows users to add specific tasks to a student's profile, enhancing the ability to track individual
assignments, exams, or goals for each student. By entering the student's name, task description and due date, users
can manage and monitor students' progress more efficiently.

- **Adding Multiple Tasks**: Users can add multiple tasks to a student. If a task with identical parameters already
  exists in the student's task list, an error message will alert the user, avoiding duplicate entries.
- **Invalid Input**: If an invalid student name, task description or due date is provided, EduManage displays the
   constraints and guidelines for the incorrect parameter(s).
- **Fixed Date Format**: Due dates must be entered in a strict `YYYY-MM-DD` format. This format avoids ambiguity and
  enforces consistency, helping users easily interpret task deadlines.

#### 3.6.1 Implementation - Sequence Diagram
The sequence diagram below illustrate the interactions among various classes when an add task command is executed.

<puml src="diagrams/AddTaskSequenceDiagram-Logic.puml" alt="AddTaskSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `AddTaskCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

#### 3.6.2 Design Considerations
**Parsing Task Input**
- **Current Implementation (Alternative 1)**:
    - **Description**: The `ParserUtil` class manages validation for task attributes, including date format checking,
      centralizing the logic for better maintainability.
    - **Pros**: Centralized validation supports consistency and simplifies updates to validation logic across multiple
      commands. 
    - **Cons**: Adds a layer of abstraction, which can make the code slightly more complex to trace.

- **Alternative 2**:
    - **Description**: Validation occurs directly within `AddTaskCommandParser`. 
    - **Pros**: Reduces dependency on external utility classes, keeping the validation logic localized within the
      command parser. 
    - **Cons**: Leads to code duplication across commands, making maintenance and updates more challenging.
  
**Design of Task Constraints**
- **Current Implementation (Alternative 1)**:
    - **Description**: Tasks require a strict `YYYY-MM-DD` date format for due dates.
    - **Pros**: Enforces a standardized format, making dates easy to read, parse, and sort chronologically.
    - **Cons**: Limits flexibility, as users cannot input custom date formats.

- **Alternative 2**:
    - **Description**: Users may enter due dates in a flexible format. 
    - **Pros**: Increases flexibility, accommodating different date formats. 
    - **Cons**: Adds complexity in validation and interpretation, potentially leading to errors or inconsistencies in task display.

[(Back to Top)](#edumanage-developer-guide)

--------------------------------------------------------------------------------------------------------------------

## 4. Documentation, Logging, Testing, Configuration, Dev-Ops

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

[(Back to Top)](#edumanage-developer-guide)

--------------------------------------------------------------------------------------------------------------------

## 5. Appendix: Requirements

### 5.1 Product Scope

**Target user profile**: Tuition teachers

* teaches secondary school students
* teaches one-on-one sessions
* has a need to manage a significant number of students and student details
* prefers desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Manage students faster than a typical mouse/GUI driven app

***

### 5.2 User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​             | I want to …​                                                                                | So that I can…​                                                                                                      |
|----------|---------------------|---------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------|
| `* * *`  | tuition teacher     | add a student                                                                               |                                                                                                                      |
| `* * *`  | tuition teacher     | delete a student                                                                            | ensure that my address book remains clutter free                                                                     |
| `* * *`  | tuition teacher     | list all students                                                                           | have a quick overview of all my students                                                                             |
| `* * *`  | tuition teacher     | update a student's details                                                                  | maintain accurate and up-to-date records                                                                             |
| `* * *`  | tuition teacher     | tag students based by their school level and subject                                        | filter students based on their school level and subjects when preparing for lessons                                  |
| `* * *`  | tuition teacher     | quickly access my student's emergency contact                                               | contact them in an emergency                                                                                         |
| `* * *`  | tuition teacher     | record notes on each student's progress                                                     | provide personalised attention and address their specific learning needs in future lessons                           |
| `* * *`  | tuition teacher     | search for students based on their name, school level or subject                            | save time during lesson preparation and while teaching                                                               |
| `* *`    | tuition teacher     | add tasks for each student                                                                  | keep track of outstanding tasks that need to be done for that student (e.g., marking assignments)                    |
| `* *`    | tuition teacher     | view tasks for a specific student                                                           | have an overview of outstanding tasks that need to be done for that student                                          |
| `* *`    | tuition teacher     | view all tasks of all students                                                              | have a comprehensive overview of all tasks, ensuring I can prioritize effectively and manage my workload efficiently |
| `* *`    | tuition teacher     | delete tasks for a specific student                                                         | ensure only outstanding tasks are displayed                                                                          |
| `* *`    | tuition teacher     | update the details of a task for a specific student                                         | ensure that each task is accurate and reflects the latest progress or changes                                        |
| `* *`    | ~~tuition teacher~~ | ~~set reminders for follow-ups, assessments or feedback sessions with individual students~~ | ~~remember not miss important checkpoints in their learning journey~~                                                |
| `* *`    | ~~tuition teacher~~ | ~~receive notifications when a student's performance drops below a certain threshold~~      | ~~proactively address any learning challenges they might be facing~~                                                 |
| `*`      | ~~tuition teacher~~ | ~~be able to see a picture of them~~                                                        | ~~easily remember and identify them~~                                                                                |

***

### 5.3 Use Cases

(For all use cases below, the **System** is `EduManage` and the **Actor** is the `tuition teacher`, unless specified otherwise)

***

**Use case: Add a student**

**MSS**

1. Tuition teacher requests to add a student by inputting the student's name, phone number, emergency contact, address and any optional fields.
2. EduManage adds the student's information and indicates success.

   Use case ends.

**Extensions**
* 1a. The inputted student's name already exists in EduManage.
    * 1a1. EduManage shows an error message indicating that the student already exists in EduManage.

      Use case resumes at step 1.

* 1b. The inputted student's information is missing fields that are not optional.
  * 1b1. EduManage shows an error message and informs the user of the correct command format.

    Use case resumes at step 1.

***

**Use case: Delete a student**

**MSS**

1.  Tuition teacher requests to list students.
2.  EduManage shows a list of students.
3.  Tuition teacher requests to delete a specific student in the list by inputting the student's index.
4.  EduManage deletes the student and indicates success.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The inputted student index is invalid.

    * 3a1. EduManage shows an error message indicating that the inputted index is invalid.

      Use case resumes at step 3.

***

**Use case: Update a student's details**

**MSS**

1. Tuition teacher requests to list students.
2. EduManage shows a list of students.
3. Tuition teacher requests to update a specific student's information by inputting the student's name and the new information.
4. EduManage updates the student's information and indicates success.

   Use case ends.

**Extensions**
* 2a. The list is empty.

  Use case ends.

* 3a. The inputted student's name is invalid or cannot be found.
    * 3a1. EduManage shows an error message and informs the user of the correct command format or to first create a student.

      Use case resumes at step 3.

* 3b. New information details are invalid.
    * 3b1 EduManage shows an error message and informs the user of the constraints of the invalid details.

      Use case resumes at step 3.

***

**Use case: Find students with a common attribute (e.g., `Name`, `Level`, `Subject`)**

**MSS**

1. Tuition teacher requests to list students.
2. EduManage shows a list of all students.
3. Tuition teacher requests to view all students with a common attribute by inputting a common attribute (e.g., a partial name, level or subject).
4. EduManage displays all students that match the inputted attribute (students with names, levels, or subjects that match the search term).

   Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The inputted common attribute is invalid as it does not correspond to a valid attribute within EduManage.

    * 3a1. EduManage shows an error message and informs the user of the constraints of the invalid attribute.

      Use case resumes at step 3.

***

**Use case: List all students**

**MSS**

1. Tuition teacher requests to list students.
2. EduManage shows a list of students.

    Use case ends.

***

**Use case: Tag a student with a Level**

**MSS**

1.  Tuition teacher requests to list students.
2.  EduManage shows a list of all students.
3.  Tuition teacher requests to tag a specific student in the list with a Level by inputting the student's name and a Level.
4.  EduManage updates the corresponding Level of the student.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The inputted student's name is invalid or cannot be found.

    * 3a1. EduManage shows an error message and informs the user of the correct command format or to first create a student.

      Use case resumes at step 3.

* 3b. Multiple Levels were inputted.

    * 3b1. EduManage shows an error message and informs the user that a maximum of one Level per student is allowed.

      Use case resumes at step 3.

* 3c. No Level was inputted.

    * 3c1. EduManage shows an error message and informs the user of the correct command format.

      Use case resumes at step 3.

***

**Use case: Tag a student with Subject(s)**

**MSS**

1.  Tuition teacher requests to list students.
2.  EduManage shows a list of all students.
3.  Tuition teacher requests to tag a specific student in the list with Subject(s) by inputting the student's name and Subject(s).
4.  EduManage updates the corresponding Subject(s) of the student.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The inputted student's name is invalid or cannot be found.

    * 3a1. EduManage shows an error message and informs the user of the correct command format or to first create a student.

      Use case resumes at step 3.

* 3b. Tuition teacher attempts to tag a student with Subject(s) without first or simultaneously tagging them with a Level.

    * 3b1. EduManage shows an error message and informs the user to first tag a student with a Level or do so in the same command.

      Use case resumes at step 3.

* 3c. Subject(s) inputted does not correspond with the Level of the student.

    * 3c1. EduManage shows an error message and informs the user of the valid subjects associated with the Level of the student.

      Use case resumes at step 3.

* 3d. No Subject was inputted.

    * 3d1. EduManage shows an error message and informs the user of the correct command format.

      Use case resumes at step 3.

***

**Use case: Add a note to a student**

**MSS**

1.  Tuition teacher requests to list students.
2.  EduManage shows a list of students.
3.  Tuition teacher requests to update the note of a specific student by inputting the student's name and the new note.
4.  EduManage updates the student's note and indicates success.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The inputted student's name is invalid or cannot be found.

    * 3a1. EduManage shows an error message and informs the user of the correct command format or to first create a student.

      Use case resumes at step 3.

***

**Use case: Add a task for a student**

**MSS**

1.  Tuition teacher requests to view all tasks for all students.
2.  EduManage shows a list of students and their tasks.
3.  Tuition teacher requests to add a task to a specific student's task list by inputting the student's name, task description and task deadline.
4.  EduManage adds the task to the student’s task list and indicates success.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The inputted student's name is invalid or cannot be found.

    * 3a1. EduManage shows an error message and informs the user of the correct command format or to first create a student.

      Use case resumes at step 3.

* 3b. The inputted task deadline is invalid.

    * 3b1. EduManage shows an error message and informs the user of the correct task deadline format.

      Use case resumes at step 3.

* 3c. No task description was inputted.

    * 3c1. EduManage shows an error message and informs the user to input a task description.

      Use case resumes at step 3.

***

**Use case: Delete a task for a student**

**MSS**

1.  Tuition teacher requests to view all tasks for all students.
2.  EduManage shows a list of students and their tasks.
3.  Tuition teacher requests to delete a task from a specific student's task list by inputting the student's name and task index.
4.  EduManage deletes the task from the student’s task list and indicates success.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The inputted student's name is invalid or cannot be found.

    * 3a1. EduManage shows an error message and informs the user of the correct command format or to first create a student.

      Use case resumes at step 3.

* 3b. The inputted task index is invalid.

    * 3b1. EduManage shows an error message and informs the user that the task index is invalid.

      Use case resumes at step 3.

***

**Use case: Update a task for a student**

**MSS**

1.  Tuition teacher requests to view all tasks for all students.
2.  EduManage shows a list of students and their tasks.
3.  Tuition teacher requests to update a task from a specific student's task list by inputting the student's name, a task index and either a new task description or a new task deadline.
4.  EduManage updates the task from the student’s task list and indicates success.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The inputted student's name is invalid or cannot be found.

    * 3a1. EduManage shows an error message and informs the user of the correct command format or to first create a student.

      Use case resumes at step 3.

* 3b. The inputted task index is invalid.

    * 3b1. EduManage shows an error message and informs the user that the task index is invalid.

      Use case resumes at step 3.

* 3c. The inputted task deadline is invalid.

    * 3c1. EduManage shows an error message and informs the user of the correct task deadline format.

      Use case resumes at step 3.

* 3d. No task description and no task deadline was inputted.

    * 3d1. EduManage shows an error message and informs the user of the correct command format.

      Use case resumes at step 3.

***

**Use case: View all tasks for all students**

**MSS**
1.  Tuition teacher requests to view all tasks for all students.
2.  EduManage shows a list of students and their tasks.

    Use case ends.

***

**Use case: View details for a specific student**

**MSS**

1. Tuition teacher requests to list students.
2. EduManage shows a list of all students.
3. Tuition teacher requests to view details for a student by inputting the student's name.
4. EduManage displays all details about the student.

   Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The inputted student's name is invalid or cannot be found.

    * 3a1. EduManage shows an error message and informs the user of the correct command format or to first create a student.

      Use case resumes at step 3.

***

### 5.4 Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 students without a noticeable sluggishness in performance for typical usage.
3.  The response to any command should be visible within two seconds.
4.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
5.  Should be meant for a single user, not a multi-user product.
6.  Data should be stored locally in a human editable text file.
7.  Should have easy-to-read and detailed User and Developer Guides.
8.  Should have PDF-friendly User and Developer Guides.
9.  Should work without an installer.
10. Should be packaged into a single JAR file.
11. Should not depend on the developer's own remote server.
12. GUI should work well for standard screen resolutions 1920x1080 and higher and for screen scales 100% and 125%.

***

### 5.5 Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS.
* **Private contact detail**: A contact detail that is not meant to be shared with others.
* **Emergency contact**: The contact to use if an emergency happens during the tutoring period.
* **Level**: The year and track of study a student is currently at, e.g., Secondary 3 Normal (Academic) (`S3 NA`).
* **Subject**: The subject the student is receiving tuition for, e.g., `Math`, `English`, `Literature`. The Subject must correspond with the Level of the student.

[(Back to Top)](#edumanage-developer-guide)

--------------------------------------------------------------------------------------------------------------------

## 6. Appendix: Instructions for Manual Testing

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### 6.1 Launch and Shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder.

    2. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar EduManage.jar` command to run the application.
       Expected: A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
       ![Ui](images/Ui.png)
       <br>

2. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

***

### 6.2 Adding a Student

1. Prerequisites: Delete any student named `Alice Lee` before and between test cases. Ensure there is a student named `Alex Yeoh` in the list of students.

2. **Valid Test Cases**

    1. Add a student with only compulsory fields: `add n/alice lee p/91234567 e/91234567 a/123 Clementi`<br>
       Expected: New student with name `Alice Lee`, phone and emergency contact number `91234567`,
       and address `123 Clementi` is added. No level, subject(s) or lesson time(s) are specified.

    2. Add a student with compulsory fields, level, subject and lesson times:
       `add n/alice lee p/91234567 e/91234567 a/123 Clementi l/s1 na s/math s/physics lt/SUN-12:00-14:00 lt/WED-17:00-19:00`<br>
       Expected: New student with name `Alice Lee`, phone and emergency contact number `91234567`,
       address `123 Clementi`, subject tags `S1 NA MATH` and `S1 NA PHYSICS`, and lesson times `WED-17:00-19:00` and `SUN-12:00-14:00` is added.

    3. Other valid test cases: Change the capitalisation and length of existing spacing between words for the name, level, subject for
       any of the above test cases. Some examples:

       1. Multiple spaces in name: `add n/alice   lee p/91234567 e/91234567 a/123 Clementi l/s1 na s/math s/physics lt/SUN-12:00-14:00 lt/WED-17:00-19:00`<br>
          Expected: Similar to previous.

       2. Different casing for level and subject: `add n/alice lee p/91234567 e/91234567 a/123 Clementi l/S1 nA s/mATh s/PhySIcs lt/SUN-12:00-14:00 lt/WED-17:00-19:00`<br>
          Expected: Similar to previous.

3. **Invalid Test Cases**

    1. Add a student with subject without level: `add n/Alice Lee p/91234567 e/91234567 a/123 Clementi s/MATH`<br>
       Expected: No student is added. Error details shown in the status message. Status bar remains the same.
       
    2. Add an existing student: `add n/Alex Yeoh p/91234567 e/91234567 a/123 Clementi`<br>
       Expected: Similar to previous.

    3. Other invalid test cases: Add a student without compulsory fields or with invalid values for any field.
       The valid value range can be found in the User guide.<br>
       Expected: Similar to previous.

***

### 6.3 Deleting a Student

1. Prerequisites: List all students using the `list` command. Multiple students in the list.

2. Test case: `delete 1`<br>
   Expected: First student is deleted from the list. Details of the deleted student shown in the status message.

3. Test case: `delete 0`<br>
   Expected: No student is deleted. Error details shown in the status message. Status bar remains the same.

4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
   Expected: Similar to previous.

***

### 6.4 Finding Specific Students

1. Prerequisites: List all students using the `list` command. Multiple students in the list with varying names, levels and subjects.

2. **Valid Test Cases**

    1. Find by name: `find n/Alex`<br>
       Expected: Only students with the name `Alex` are displayed. Number of students found is reflected in the status message.

    2. Find by level: `find l/S1 NA`<br>
       Expected: Only students tagged with the level `S1 NA` are displayed. Number of students found is reflected in the status message.

    3. Find by subject: `find s/MATH`<br>
       Expected: Only students tagged with the subject `MATH` are displayed. Number of students found is reflected in the status message.

3. **Invalid Test Cases**

   1. Find by name: `find n/!@!`, `find n/1e12#>`, `...`<br>
      Expected: All students still listed. Error details shown in the status message. Status bar remains the same.

   2. Find by level and track: `find l/S1` (no track entered), `find l/IP` (no level entered), `...`<br>
      Expected: Similar to previous.

   3. Find by subject: `find s/ENGINEERING`, `find s/?@!@#a`, `...`<br>
      Expected: Similar to previous.

***

### 6.5 Adding a Task to a Student

1. Prerequisites: Ensure that student `Alex Yeoh` exists.

2. Test case: `addtask n/alex yeoh t/Mark homework d/2024-11-06`<br>
   Expected: New task with description `Mark homework` and deadline `2024-11-06` is added to student `Alex Yeoh`.

3. Test case: `addtask n/alex yeoh t/Mark homework d/tmr`<br>
   Expected: No task is added. Error details shown in the status message. Status bar remains the same.

4. Test case: `addtask n/alex yeoh t/  d/2024-11-06`<br>
   Expected: Similar to previous.

[(Back to Top)](#edumanage-developer-guide)

***

## 7. Appendix: Planned Enhancements

Given below are the planned enhancements for future versions of EduManage.

**Team size**: 5.

### 7.1 Update subject and lesson time cumulatively

Currently, both subjects and lesson times are not updated cumulatively; using the `update` command replaces all previous
entries with the new values provided. This behavior can be inconvenient, especially when users want to add additional
subjects or lesson times without removing existing ones. We plan to enhance this feature to allow cumulative updates to
both subjects and lesson times, enabling users to add new subjects and lesson times without overwriting existing entries
by changing the behaviour of the `update` command.

***

### 7.2 Names with special characters

Currently, only alphanumeric names are allowed. Users are advised to omit special characters (e.g., `,`, `-`, `/`) or replace them with spaces.
A future version will include support for names with special characters to accommodate a wider range of naming conventions.

***

### 7.3 Multiple students with the same name

Currently, EduManage does not support multiple students with identical names. As a workaround, users can differentiate these
students by adding numbering to their names (e.g., "John Doe 1," "John Doe 2"). We plan to accommodate this in the future by
relying on `INDEX` instead of `n/NAME` or `NAME` for commands.

***

### 7.4 Usage of reserved prefixes in parameters

Currently, EduManage does not check whether reserved prefixes are used within parameters. A future version will ensure that
all reserved prefixes are restricted from use in any parameters to prevent conflicts or unexpected behavior. This planned
enhancement aims to improve system reliability by reducing potential errors arising from misuse of reserved prefixes.

The reserved prefixes are: `a/`, `e/`, `lt/`, `l/`, `n/`, `nt/`, `p/`, `s/`, `d/`, `t/`, `ti/`.

[(Back to Top)](#edumanage-developer-guide)
