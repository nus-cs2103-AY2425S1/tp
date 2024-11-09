---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# TrackMate Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

We follow the project design and documentation structure of AB3.

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280"/>

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

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `deleteStu 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-W08-1/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `StudentListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Student` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("deleteStu 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `deleteStu 1` Command" />

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

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Student` objects (which are contained in a `UniqueStudentList` object).
* stores the currently 'selected' `Student` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Student>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. This model introduces a more streamlined structure where `AddressBook` holds a `TutorialList` and an `AssignmentList`, linking directly to `Tutorial` and `Assignment` entities. Each `Student` is associated with a `TutorialId`, and `Tutorial` entities maintain a list of `Student` objects, making it easier to manage relationships and reducing redundancy. This design allows `AddressBook` to manage tutorials and assignments without requiring each student to hold separate `Tutorial` or `Assignment` objects, simplifying interactions and data management.

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* can save both tutorial data and assignment data as well in JSON format, and read them back into corresponding objects.
* inherits from `AddressBookStorage`, `UserPrefStorage`, `TutorialStorage` and `AssignmentStorage`, which means it can be treated as one of these (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

> Please note that certain aspects, such as UML classes, may have been simplified to fit within the diagram's constraints and maintain readability.

### Add feature

Users can seamlessly add tutorials, students and assignments into the TrackMate application. On top of that, users can also add student's tutorial
attendance to maintain student's accurate attendance record.

#### Feature's Architecture Design

1. **Centralized Parsing with AddCommandParser**:
   The parsing logic is centralized in `AddCommandParser` to ensure that input arguments are consistently handled across commands. This prevents each command from having redundant logic and promotes modularity by isolating parsing responsibilities.

    * **Benefit**: This structure simplifies the command classes, making them more focused on executing logic rather than parsing input.
    * **Challenge**: It requires careful handling of the parsing rules to ensure correctness since the parser is a crucial layer between user input and the system’s execution.

2. **Avoiding Duplicates with Tutorial and Student Validation**:
   We ensure that duplicate students or non-existent tutorials are caught early in `ModelManager`. This provides immediate feedback and prevents inconsistent states in the data model.

    * **Benefit**: Validation at the model level ensures the integrity of the data.
    * **Challenge**: This requires checks across multiple classes (such as `TutorialList` and `Student`) to ensure consistency without impacting performance.


#### Add Student

To ensure data integrity and completeness, the system necessitates the inclusion of parameters such as Name and Student ID. The activity diagram below
shows the sequence of action users will have to take to add a new Student Profile into the TrackMate Application.

<puml src="diagrams/AddFeatureActivityDiagram.puml" />

Besides, a class diagram of add student command is given below to demonstrate the interactions among classes.

<puml src="diagrams/AddFeatureClassDiagram.puml" />

#### Add Tutorial

Similar to adding student, the system requires parameters such as Tutorial Name and Tutorial ID. The sequence diagram below demonstrates the interaction
among various classes to add a new Tutorial into the TrackMate Application.

<puml src="diagrams/AddTutorialSequenceDiagram.puml" />

#### Add Attendance

The AttendCommand is responsible for marking the attendance of a student for a specific tutorial session in the TrackMate Application. This command interacts
with the model to update the attendance record of a given student for a particular tutorial. The sequence diagram below shows how the command
interact with other classes.

<puml src="diagrams/AttendCommandSequenceDiagram.puml" />

#### Add Assignment

Users can also add assignments to the TrackMate Application that are shared among all the students in every tutorials. The activity diagram below will
demonstrate what the users need to do to add assignment for students.

<puml src="diagrams/CreateAssignmentActivityDiagram.puml" />

#### Implementation - Design Considerations:

**Alternative 1 (Current Implementation)**:

**Description**: The current implementation uses a dedicated ParserUtil class to handle input parsing and data validation. This ensures that common validation logic (e.g., parsing names, student IDs, tutorial IDs or dates) is reusable across different components.

**Pros**:
* Code Modularity and Maintainability: By isolating validation logic from other components, we promote modularity, making updates and modifications easier.
* Consistency: Validation logic is standardized across different commands (e.g., AddCommand), ensuring that rules are applied uniformly.
* Ease of Testing: Having all parsing logic in one place makes it easier to unit test and debug issues related to input validation.

**Cons**:
* Additional Abstraction Layer: This approach introduces an extra layer between the raw input and command execution, which may increase the complexity of the system.

**Alternative 2**: Inline Validation in Domain Classes

**Description**: An alternative design is to incorporate validation logic directly within each relevant class (e.g., Student, TutorialId, Name) to enforce constraints close to where the data is used.

**Pros**:
* Context-Specific Validation: Each class can enforce rules that are specific to its context, ensuring validation aligns with the class's intended behavior.
* Simplifies Parsing Layer: Reduces the complexity of the parser classes, making them more lightweight.

**Cons**:
* Code Duplication: If similar validation logic (like checking valid names or student IDs) is required across multiple classes, this can lead to code duplication and inconsistency.
* Higher Maintenance Overhead: Changes to validation rules would need to be reflected across multiple classes, increasing the risk of inconsistent logic.

We chose Alternative 1 due to the modularity, maintainability, and consistency it provides. By centralizing the validation logic in ParserUtil, we ensure
it aligns with the modular nature of our design, where commands and models interact through a well-defined interface. It also simplifies the commands by offloading validation and parsing responsibilities,
promoting cleaner, more maintainable code.

### Edit feature

The TrackMate application empowers users to update the details of existing students easily. With the Edit feature, users can modify a student's name, student ID, or assigned tutorial
ID. This functionality ensures that student records remain accurate and up-to-date within the application, reflecting any changes in student information or tutorial assignments.

#### Feature's Architecture Design

**Use of EditStudentDescriptor**: The EditStudentDescriptor is a static inner class within EditCommand that stores the details of the fields to edit. It allows for optional
fields, meaning users can choose to update any combination of the student's attributes without affecting others.

* **Benefit**: This descriptor pattern provides a clean and flexible way to handle optional edits. It encapsulates changes,
making it straightforward to create a new Student object with updated values. It also promotes code reuse and maintainability by
isolating the edit details from the command execution logic.

* **Challenge**: Managing optional values and ensuring that the descriptor accurately represents the intended changes without introducing
errors requires careful implementation. Ensuring that at least one field is edited and providing appropriate error messages when none are
requires precise checks.

**Validation Checks in EditCommand**: Before applying edits, several validation checks are performed such that we can ensure that the edited student does not duplicate an
existing student in the address book unless it's the same student. Besides, it also validates that the new student ID, if changed, does not
already exist in the system.

* **Benefit**: These checks maintain data integrity, preventing inconsistent states and ensuring the uniqueness of critical identifiers like student IDs.

* **Challenge**: These validations must be efficient to avoid performance degradation. They also need to be comprehensive to prevent any edge cases where data integrity could be compromised.

### Edit Student
To maintain accurate and up-to-date student records, the Edit feature allows users to modify existing student information. The sequence diagram below shows
how it involves specifying the student's index in the displayed list and providing any new values for the student's attributes.

<puml src="diagrams/EditSequenceDiagram.puml" />

#### Implementation - Design Considerations

**Alternative 1 (Current Implementation)**:

**Description**:

The current implementation employs an EditStudentDescriptor to encapsulate the fields to be edited. The EditCommandParser handles the parsing of user input,
populating this descriptor with provided values. The EditCommand then uses this descriptor to create a new Student object with the updated values, replacing
the old one in the model.

**Pros**:
* Modularity: Separates parsing, validation, and execution logic, enhancing code maintainability.
* Flexibility: Handles optional fields gracefully, allowing users to edit any combination of a student's attributes.
* Immutability: Maintains the immutability of Student objects by creating new instances rather than modifying existing ones.
* Reusability: The EditStudentDescriptor can be reused for other commands requiring similar functionality.

**Cons**:
* Complexity: Introduces additional classes and layers of abstraction, which can increase the learning curve for new developers.

**Alternative 2: Inline Editing with Mutable Student Objects**

**Description**:

An alternative design could involve making the Student class mutable, allowing direct modification of its fields. The EditCommand would
modify the fields of the existing Student object directly without creating a new instance.

**Pros**:
* Simplicity: Reduces the number of classes and abstractions, potentially making the codebase easier to understand.
* Performance: May be more efficient as it avoids creating new objects.

**Cons**:
* Data Integrity Risks: Mutable objects can lead to unintended side effects, making the system more prone to bugs.
* Loss of Immutability Benefits: Immutable objects are easier to reason about, especially in concurrent contexts.
* Inconsistency with Design Patterns: Deviates from the application's existing design principles, potentially causing confusion.

**Decision**:

We chose Alternative 1 due to its alignment with the application's overall design philosophy of using immutable data objects. This approach
promotes safer code by avoiding side effects, making it easier to maintain and less error-prone. Although it introduces additional classes,
the benefits of modularity, maintainability, and data integrity outweigh the added complexity. It also adheres to the Single Responsibility Principle,
keeping parsing, data encapsulation, and execution concerns separate.

### Delete Feature

The TrackMate application allows users to remove students, tutorials, assignments, and attendance records efficiently. The Delete feature ensures
that outdated or incorrect records can be cleaned up, maintaining the integrity and relevance of the data within the application.

#### Feature's Architecture Design

1. **Centralized Parsing with DeleteCommandParser**: The parsing logic for delete commands is centralized in their respective parser classes (e.g., DeleteCommandParser, DeleteTutorialCommandParser, etc.). Each parser is responsible for interpreting
the user's input, extracting necessary identifiers (like student index, tutorial ID, assignment title), and creating the appropriate delete command object.
* Benefit: Centralizing parsing logic in dedicated parser classes promotes modularity and reusability. It ensures consistent input handling and reduces redundancy across different delete commands.
* Challenge: Each parser must handle specific validation rules and error messages, requiring careful implementation to provide meaningful feedback to the user.

2. **Model Updates and Data Consistency**: The model (ModelManager) handles the actual deletion of entities:

Students: Deleting a student removes them from the address book, unassigns them from tutorials, and updates assignments and attendance records.

Tutorials: Deleting a tutorial removes it from the tutorial list and unassigns all associated students.

Assignments: Deleting an assignment removes it from the assignment list.

Attendance Records: Deleting attendance marks the student as absent for that date and tutorial.
* Benefit: Centralizing deletion logic in the model ensures consistent updates across the application and maintains data integrity.
* Challenge: Ensuring that all related entities are correctly updated without introducing bugs requires thorough testing and careful implementation.

#### Delete Student
To remove a student from the application, the user specifies the student's index in the displayed student list. The system ensures
that the index is valid and then proceeds to delete the student, updating all related records. Below is the sequence diagram regarding deleting student.

<puml src="diagrams/DeleteSequenceDiagram.puml" />

#### Delete Tutorial
To delete a tutorial, the user provides the tutorial ID. The system verifies the existence of the tutorial and then removes it, updating any students assigned to it.
The sequence diagram of deleting tuorial is similar to deleting student.

#### Delete Assignment
To delete an assignment, the user specifies the assignment title. The system ensures that the assignment exists before deleting it from the model. The
activity diagram below illustrates what the series of actions the user should do to delete assignment.

<puml src="diagrams/DeleteAssignmentActivityDiagram.puml" />

#### Delete Attendance
To delete a student's attendance record for a specific date and tutorial, the user provides the student ID, tutorial ID, and date. The diagram is
also similar to adding attendance, thus is not given.

#### Implementation - Design Considerations:
Alternative 1 (Current Implementation):

**Description**:

The current implementation utilizes specific delete commands for different entities (DeleteCommand for students, DeleteTutorialCommand for tutorials, etc.). Each command class handles the deletion logic pertinent to its entity type. Parsing and validation are handled in dedicated parser classes, and the model updates are performed through methods in ModelManager.

**Pros**:
* Modularity: Separation of concerns is maintained, with each command and parser responsible for a specific entity.
* Clarity: Code is organized, making it easier to understand and maintain.
* Reusability: Common validation and parsing logic can be reused across different delete commands.

**Cons**:
* Code Duplication: Similar validation logic may exist across different delete commands and parsers.
* Scalability: Adding new delete commands for additional entities requires creating new command and parser classes.

**Alternative 2: Unified Delete Command with Type Specification**

*Description*:

An alternative design is to implement a single DeleteCommand that can handle deletion of different entity types based on additional arguments specifying the type (e.g., student, tutorial, assignment).

**Pros**:
* Reduced Class Count: Fewer command and parser classes simplify the codebase.
* Single Entry Point: Easier for users to remember a single delete command.

**Cons**:
* Complex Parsing Logic: The parser must handle multiple entity types and validation rules, increasing complexity.
* Reduced Clarity: Mixing deletion logic for different entities in one command can make the code harder to maintain.
* Error-Prone: Higher risk of bugs due to the increased complexity in parsing and execution logic.

**Decision**:

We chose Alternative 1 because it promotes modularity, clarity, and maintainability. By having separate commands and parsers for each
entity type, we can encapsulate the specific logic and validation required for each. This separation makes the codebase more organized and easier to extend or
modify. Although there is some code duplication, the benefits of clarity and reduced complexity outweigh the drawbacks.

--------------------------------------------------------------------------------------------------------------------

## **Future Enhancements**

1. In the current implementation of the attendance marking feature, the system does not validate the dates entered by users. This allows users to mark attendance
for tutorial dates that have not yet occurred. In future updates, the system will include a date validity check to ensure attendance can only be marked for past or current dates.

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

* NUS SOC tutors who want to track their student's progress

**Value proposition**: manage students' tasks and attendance faster than a typical mouse/GUI driven app


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority  | As a …​                         | I want to …​                                                                                       | So that I can…​                                                                          |
|-----------|---------------------------------|----------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------|
| `* * *`   | tutor                           | add student data                                                                                   | add student information efficiently                                                      |
| `* * *`   | tutor                           | delete student data                                                                                | delete student information efficiently                                                   |
| `* * *`   | tutor                           | categorise students based on tutorial classes                                                      | find out which students are in my class                                                  |
| `* * *`   | tutor                           | record student attendance daily through a CLI command                                              | track student participation                                                              |
| `* * *`   | tutor                           | create assignments for students                                                                    | assign tasks to students                                                                 |
| `* *`     | tutor                           | edit student data                                                                                  | manage student information efficiently                                                   |
| `* *`     | tutor                           | list all students                                                                                  | see all students enrolled in the course                                                  |
| `* *`     | tutor                           | add tutorials                                                                                      | organize students into specific sessions or groups                                       |
| `* *`     | tutor                           | list all tutorials                                                                                 | review all tutorial sessions currently available                                         |
| `* *`     | tutor                           | delete a tutorial                                                                                  | manage tutorials efficiently by removing sessions that are no longer needed              |
| `* *`     | tutor                           | add assignments to a tutorial                                                                      | assign tasks to students and manage their workload                                       |
| `* *`     | tutor                           | delete assignments                                                                                 | manage assignments efficiently by removing tasks that are no longer relevant             |
| `* *`     | tutor                           | list all assignments                                                                               | see all assignments I have created for the students                                      |
| `* *`     | tutor                           | mark assignments as completed or pending                                                           | track the progress of each student                                                       |
| `* *`     | tutor                           | unmark assignments as completed                                                                    | correct any mistakes in assignment status updates                                        |
| `* *`     | tutor                           | check assignment status for a student                                                              | monitor individual student progress on given assignments                                 |
| `* *`     | tutor                           | mark student attendance                                                                            | keep track of each student's participation in the tutorials                              |
| `* *`     | tutor                           | unmark student attendance                                                                          | correct mistakes in attendance marking                                                   |
| `* *`     | cross-platform user             | run the application on any platform (Windows, Linux, OS X) without any OS-specific dependencies    | use it anywhere                                                                          |
| `* *`     | convenience-seeking educator    | use the application without an installer                                                           | use it directly from the downloaded JAR file                                             |
| `*`       | self-learning user              | use interactive help commands                                                                      | understand how to use the application without referring to external documentation        |


### Use cases

(For all use cases below, the System is the TrackMate and the Actor is the National University of Singapore teaching assistants, unless specified otherwise)

#### Use Case: Add Student
#### Main Success Scenario (MSS)

1. User requests to add a new student.
2. User inputs the required details.
3. TrackMate confirms that the student details are valid and adds the student to the list.
4. The new student record is successfully created.

Use case ends.

#### Extensions

2a. User enters incomplete or invalid student details:
* 2a1. TrackMate informs the user of the specific constraints for each field.
* 2a2. User re-enters the correct information. Steps 3a1–3a2 are repeated until all required fields are valid.

Use case resumes at Step 3.

2b. Duplicate student id found:
* 2b1. TrackMate informs the user that a student with the same id already exists.
* 2b2. User re-enters the new student id. Steps 3b1–3b2 are repeated until a new student id is provided.

Use case resumes at Step 3.

#### Use Case: Add Tutorial
#### Main Success Scenario (MSS)

1. User requests to add a tutorial.
2. User inputs the tutorial information.
3. TrackMate validates the input and confirms the addition.
4. The new tutorial session is successfully created.

Use case ends.

#### Extensions

2a. Invalid tutorial id or name format entered:
* 2a1. TrackMate informs the user of the constraints for tutorial id or name.
* 2a2. User re-enters the correct tutorial id or name. Steps 3a1–3a2 are repeated until a valid code is provided.

Use case resumes at Step 3.

2b. Duplicate tutorial id found:
* 2b1. TrackMate informs the user that a tutorial with the same id already exists.
* 2b2. User re-enters the new tutorial id. Steps 3b1–3b2 are repeated until a new tutorial id is provided.

Use case resumes at Step 3.

#### Use Case: Add Attendance
#### Main Success Scenario (MSS)

1. User requests to record attendance for a tutorial session.
2. User inputs the student information. 
3. TrackMate validates the input and confirms the attendance.
4. Attendance data is successfully saved.

Use case ends.

#### Extensions

2a. User enters an invalid attendance date (incorrect date format: yyyy-MM-dd)
* 2a1. TrackMate informs the user of the accepted date format for attendance.
* 2a2. User re-enters the date. Steps 2a1–2a2 are repeated until a valid status is given.

Use case resumes at Step 3.

2b. User enters the student information wrongly.
* 2b1. TrackMate informs the user that the student information is incorrect.
* 2b2. User re-enters the student information. Steps 2a1–2a2 are repeated until a valid status is given.

Use case resumes at Step 3.

#### Use Case: Add Assignment
#### Main Success Scenario (MSS)

1. User requests to add a new assignment.
2. User inputs the assignment information.
3. TrackMate validates the assignment details.

Use case ends.

#### Extensions

2a. User enters an invalid due date (incorrect date format: yyyy-MM-dd)
* 2a1. TrackMate informs the user of the accepted date format for due date.
* 2a2. User re-enters the due date in the correct format. Steps 2a1–2a2 are repeated until a valid date is entered.

Use case resumes at Step 3.

2b. Duplicate assignment name found:
* 2b1. TrackMate informs the user that an assignment with the same name already exists.
* 2b2. User re-enters the new assignment name. Steps 2a1–2a2 are repeated until a new assignment name is entered.

Use case resumes at Step 3.

#### Use Case: Edit the Information of a Student
#### Main Success Scenario (MSS)

1. User request to edit student details.
2. User inputs the student index and updated information.
3. The student's information is successfully changed.

Use case ends.

#### Extensions

2a. Invalid student index is entered
* 2a1. TrackMate informs user that the index is invalid.
* 2a2. User enters the correct student index and updated information. Steps 2a1 - 2a2 are repeated till a valid student index is given.

Use case resumes at step 3.

2b. detect error in any updated information entered (invalid format)
* 2b1. TrackMate informs the user of the specific constraints for each field.
* 2b2. User re-enters the correct information. Steps 3a1–3a2 are repeated until all required fields are valid.

Use case resumes at step 3.

2c. updated information is the same as current information
* 2c1. No changes made to information

Use case ends.

#### Use Case: Delete Student
#### Main Success Scenario (MSS)

1. User request to delete student details.
2. User inputs the student's index.
3. TrackMate successfully deletes the student record.

Use case ends.

#### Extensions

2a. Invalid student index is entered
* 2a1. TrackMate informs user that the index is invalid.
* 2a2. User enters the correct student index and updated information. Steps 2a1 - 2a2 are repeated till a valid student index is given.

Use case resumes at step 3.

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should be able to hold up to 1000 students without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. The application should start up in under 3 seconds on a standard machine with Java 17 installed.
5. Automated backups should be encrypted to secure data during storage and transfer.
6. Customizable themes should include options for high contrast and font size adjustments for visual accessibility.
7. The system is optimized for single-user operation and does not need to handle multi-user access.
8. The application should be optimized for low CPU and memory usage to run smoothly on standard hardware.
9. The application should be reliable enough for continuous use during working hours without the need for frequent restarts.
10. The application size should be less than 50mb to facilitate easy distribution and storage.

### Glossary

* **AB-3**: The code name for the AddressBook Level 3 application, which serves as the base framework for the student management system being developed.
* **NUS SOC**: National University of Singapore, School of Computing. The application is designed for tutors within this institution to manage student progress effectively.
* **Tutor**: The primary user of the application—an NUS School of Computing tutor responsible for tracking students' progress, attendance, and assignments.
* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **CLI (Command Line Interface)**: A text-based interface where users interact with the application by typing commands rather than using graphical elements like buttons.
* **GUI (Graphical User Interface)**: A visual interface that allows users to interact with the application through graphical elements like windows, buttons, and menus.
* **JSON (JavaScript Object Notation)**: A lightweight data format used for data storage and transmission, typically for configuration or file storage.
* **Parser**: A component that interprets user commands and converts them into actions or objects that the system can process.
* **MSS (Main Success Scenario)**: The primary sequence of steps in a use case where everything proceeds as expected without any errors.
* **JAR File**: Java ARchive file; a package file format that bundles Java class files and associated metadata for distribution.

--------------------------------------------------------------------------------------------------------------------


## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Prerequisites: Download the jar file and copy into an empty folder

   2. Test case: Double-click the jar file
      Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Prerequisites: Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Test case: Re-launch the app by double-clicking the jar file.<br>
      Expected: The most recent window size and location is retained.

### Adding a student

1. Adding a student with all required details
    1. Prerequisites: Ensure that the tutorial T1001 exists in the system.

    2. Test case: `addStu n/John Doe s/A1234567X t/T1001`<br>
       Expected: A new student named "John Doe" with student ID "A1234567X" is added to tutorial "T1001". Confirmation message is displayed with the student's details.

2. Adding a student with missing compulsory fields

    1. Test case: `addStu n/John Doe t/T1001`<br>
       Expected: Error message indicating that the student ID is missing.

    2. Test case: `addStu s/A1234567X t/T1001`<br>
       Expected: Error message indicating that the student's name is missing.

3. Adding a student with invalid data

    1. Test case: `addStu n/John Doe s/INVALID_ID t/T1001`<br>
       Expected: Error message indicating that the student ID format is invalid.

    2. Test case: `addStu n/John Doe s/A1234567X t/INVALID_TUT`<br>
       Expected: Error message indicating that the tutorial ID does not exist.

### Editing a student

1. Editing a student's details

    1. Prerequisites: At least one student exists in the list. For example, a student at index 1.

    2. Test case: `edit 1 n/Jane Smith s/A7654321X t/T2001`<br>
       Expected: Student at index 1 is updated with the new name "Jane Smith", student ID "A7654321X", and assigned to tutorial "T2001". Confirmation message is displayed with the updated details.

2. Editing a student with some fields missing

    1. Test case: `edit 1 n/Jane Smith`<br>
       Expected: Only the name of the student at index 1 is updated to "Jane Smith". Other details remain unchanged.

3. Editing a student with invalid index

    1. Test case: `edit 0 n/Jane Smith`<br>
       Expected: Error message indicating that the student index provided is invalid.

    2. Test case: `edit 999 n/Jane Smith` (assuming there are fewer than 999 students)<br>
       Expected: Error message indicating that the student index provided is invalid.

### Deleting a student

1. Deleting a student while all students are being shown

    1. Prerequisites: List all students using the `list` command. Multiple students in the list.

    2. Test case: `deleteStu 1`<br>
     Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

    3. Test case: `deleteStu 0`<br>
     Expected: No student is deleted. Error details shown in the status message. Status bar remains the same.

2. Other incorrect delete commands to try: `deleteStu`, `deleteStu x`, `...` (where x is larger than the list size)<br>
   Expected: Similar to previous.
    1. Test case: `deleteStu 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

    2. Test case: `deleteStu 0`<br>
       Expected: No student is deleted. Error details shown in the status message. Status bar remains the same.

    3. Other incorrect delete commands to try: `deleteStu`, `deleteStu x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

### Marking attendance

1. Marking a student's attendance for a tutorial session

    1. Prerequisites: Student with ID "A1234567X" exists and is assigned to tutorial "T1001".

    2. Test case: `markAtt s/A1234567X t/T1001 d/2023-10-21`<br>
       Expected: Attendance for student "A1234567X" on "2023-10-21" is marked as present in tutorial "T1001". Confirmation message is displayed.

2. Marking attendance for a student not in the tutorial

    1. Test case: `markAtt s/A1234567X t/T2001 d/2023-10-21`<br>
       Expected: Error message indicating that the student is not enrolled in tutorial "T2001".

3. Marking attendance with invalid date format

    1. Test case: `markAtt s/A1234567X t/T1001 d/21-10-2023`<br>
       Expected: Error message indicating that the date format is invalid. Correct format should be "YYYY-MM-DD".

### Saving data

1. Dealing with missing data files

    1. Simulate a missing data file:
        * Close the application.
        * Navigate to the data directory where the application stores its data files.
        * Delete the data file (e.g., addressbook.json).
    2. Re-launch the application.<br>
       Expected: The application starts with an empty data set. A new data file is created automatically.

2. Dealing with corrupted data files

    1. Simulate a corrupted data file:
        * Close the application.
        * Open the data file (e.g., addressbook.json) with a text editor.
        * Introduce invalid JSON syntax (e.g., delete a closing brace or add random text).
        * Save the file.
    2. Re-launch the application.<br>
       Expected: The application detects the corrupted data file and displays an error message in the terminal. It will then start with an empty data set.
