---
layout: page
title: Developer Guide
---

## Table of Contents
- [Acknowledgements](#acknowledgements)
- [Setting up, getting started](#setting-up-getting-started)
- [Design](#design)
  - [Architecture](#architecture)
  - [UI component](#ui-component)
  - [Logic component](#logic-component)
  - [Model component](#model-component)
  - [Storage component](#storage-component)
  - [Common classes](#common-classes)
- [Documentation, logging, testing, configuration, dev-ops](#documentation-logging-testing-configuration-dev-ops)
- [Planned Enhancements](#planned-enhancements)
- [Appendix: Requirements](#appendix-requirements)
  - [Product scope](#product-scope)
  - [User stories](#user-stories)
  - [Use cases](#use-cases)
  - [Non-Functional Requirements](#non-functional-requirements)
  - [Glossary](#glossary)
- [Appendix: Instructions for manual testing](#appendix-instructions-for-manual-testing)
- [Appendix: Effort](#appendix-effort)

---

## **Acknowledgements**

- HRConnect is a brownfield project based on [AddressBook Level-3](https://github.com/se-edu/addressbook-level3) ([UG](https://se-education.org/addressbook-level3/UserGuide.html), [DG](https://se-education.org/addressbook-level3/DeveloperGuide.html)).
- Certain parts of `Project` and `Assignment` related features contain altered code from the original [AddressBook Level-3](https://github.com/se-edu/addressbook-level3) ([UG](https://se-education.org/addressbook-level3/UserGuide.html), [DG](https://se-education.org/addressbook-level3/DeveloperGuide.html)).
- Parts of the [User Guide](https://ay2425s1-cs2103t-t15-4.github.io/tp/UserGuide.html) and Developer Guide of HRConnect are based on those for the original [AddressBook Level-3](https://github.com/se-edu/addressbook-level3) ([UG](https://se-education.org/addressbook-level3/UserGuide.html), [DG](https://se-education.org/addressbook-level3/DeveloperGuide.html)).


[Return to Top](#table-of-contents)

---

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

[Return to Top](#table-of-contents)

---

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document are located in the `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The **_Architecture Diagram_** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.

- At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
- At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

- [**`UI`**](#ui-component): The UI of the App.
- [**`Logic`**](#logic-component): The command executor.
- [**`Model`**](#model-component): Holds the data of the App in memory.
- [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The _Sequence Diagram_ below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

- defines its _API_ in an `interface` with the same name as the Component.
- implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

[Return to Top](#table-of-contents)

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-T15-4/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<img src="images/UiClassDiagram.png" width="800"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `EmployeeListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

- executes user commands using the `Logic` component.
- listens for changes to `Model` data so that the UI can be updated with the modified data.
- keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
- depends on some classes in the `Model` component, as it displays `Employee` object residing in the `Model`.

[Return to Top](#table-of-contents)

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-T15-4/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete an employee).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:

- When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
- All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

[Return to Top](#table-of-contents)

### Model component

**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="600" />

The `Model` component,

- stores the address book data i.e., all `Employee` objects (which are contained in a `UniqueEmployeeList` object).
- stores the currently 'selected' `Employee` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Employee>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
- stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
- does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list and a `Skill` list in the `AddressBook`, which `Employee` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Employee` needing their own `Tag` objects. Likewise, `AddressBook` only requires one `Skill` object per unique skill, instead of each `Employee` needing their own `Skill` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="600" />

</div>

[Return to Top](#table-of-contents)

### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-T15-4/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,

- can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
- inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
- depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

[Return to Top](#table-of-contents)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

[Return to Top](#table-of-contents)

---

## **Documentation, logging, testing, configuration, dev-ops**

- [Documentation guide](Documentation.md)
- [Testing guide](Testing.md)
- [Logging guide](Logging.md)
- [Configuration guide](Configuration.md)
- [DevOps guide](DevOps.md)

[Return to Top](#table-of-contents)

---

## **Planned Enhancements**

Team size: 5

[Return to Top](#table-of-contents)

---

## **Appendix: Requirements**

### Product scope

**Target user profile**:

- HR professional of a tech company with many employees with different skill sets and roles
- works in a team of HR professionals
- manages recruitment and manpower allocation for company projects
- has a need to manage a significant number of contacts
- prefer desktop apps over other types
- can type fast
- prefers typing and keyboard shortcuts to mouse interactions
- is reasonably comfortable using and prefers CLI apps

**Value proposition**:

HRConnect provides fast access to employee, project, and candidate contact details, optimized for HR professionals who prefer a CLI. It allows quick updates, talent pool organization, and candidate tracking, all through a streamlined, command-based interface designed for speed and efficiency. It also helps with assignment of HR staff to HR events and cases.

[Return to Top](#table-of-contents)

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                                      | I want to …​                                                                     | So that I can…​                                                                     |
| -------- | ------------------------------------------------------------ | -------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- |
| `* * *`  | new user                                                     | see usage instructions and tips                                                  | refer to instructions and tips when I forget how to use the App                     |
| `* * *`  | user                                                         | add a new employee                                                               |                                                                                     |
| `* * *`  | user                                                         | add a new project                                                                |                                                                                     |
| `* * *`  | user                                                         | add a new job candidate                                                          |                                                                                     |
| `* * *`  | user                                                         | remove an employee                                                               | remove entries that I no longer need                                                |
| `* * *`  | user                                                         | remove a project                                                                 | remove entries that I no longer need                                                |
| `* * *`  | user                                                         | remove a job candidate                                                           | remove entries that I no longer need                                                |
| `* * *`  | user                                                         | search for employee details by name or ID                                        | locate employee details without having to go through the entire list                |
| `* * *`  | user                                                         | search for project details by name or ID                                         | locate project details without having to go through the entire list                 |
| `* * *`  | user                                                         | search for job candidates by name or ID                                          | locate candidate details without having to go through the entire list               |
| `* * *`  | user                                                         | assign employees to projects                                                     | update manpower allocation                                                          |
| `* * *`  | user                                                         | un-assign employees to projects                                                  | update manpower allocation                                                          |
| `* *`    | user                                                         | search for employees by skill sets                                               | find employees with desired skills without having to go through the entire list     |
| `* *`    | user                                                         | search for projects by description                                               | find projects that match a description without having to go through the entire list |
| `* *`    | user                                                         | search for job candidates by interview status                                    | track the number of candidates at each stage of the hiring pipeline                 |
| `* *`    | user                                                         | update employee details                                                          | update an entry without having to delete and re-create it                           |
| `* *`    | user                                                         | update project details                                                           | update an entry without having to delete and re-create it                           |
| `* *`    | user                                                         | update job candidate details                                                     | update an entry without having to delete and re-create it                           |
| `* *`    | user                                                         | hide private contact details                                                     | minimize chance of someone else seeing them by accident                             |
| `* *`    | user managing many employees                                 | categorize employees by their departments                                        | organize employees based on their department                                        |
| `* *`    | user managing many employees                                 | categorize employees into talent pools by skill sets                             | organize employees based on their skills                                            |
| `* *`    | user managing many projects                                  | sort projects by their deadlines                                                 | focus on projects due earlier                                                       |
| `* *`    | user that tracks project progress                            | list employees with project deliverables due soon                                | remind employees of their upcoming deliverables                                     |
| `* *`    | user that tracks project progress                            | list employees with project deliverables overdue                                 | remind employees to complete overdue deliverables                                   |
| `* *`    | user doing manpower allocation                               | filter employees with certain skill sets                                         | assign suitable people to projects                                                  |
| `* *`    | user that recruits job candidates for projects               | filter job candidates with certain skill sets                                    | contact job candidates with skill sets sought after by the company                  |
| `* *`    | frequent user                                                | see a summary of upcoming interviews and project deadlines upon starting the App | stay updated without searching or filtering for details                             |
| `* *`    | busy user                                                    | have most responses return within 200 ms                                         | perform actions smoothly and quickly                                                |
| `*`      | user with many employees in the App                          | sort employees by name                                                           | locate an employee easily                                                           |
| `*`      | user that has entered a lot of wrong data                    | reset the data to its default state                                              | start over with fresh data                                                          |
| `*`      | user with a lot of old data                                  | archive outdated records                                                         | keep the database clutter-free and relevant                                         |
| `*`      | user that prefers short-form commands and keyboard shortcuts | use short-form commands and keyboard shortcuts to perform regular functions      | perform actions more efficiently                                                    |
| `*`      | user in-charge of employee up-skilling                       | track employees' learning of new skills                                          | monitor employees' progress in learning new skills                                  |
| `*`      | user in-charge many manpower allocations                     | assign multiple employees to different projects using batch commands             | manage manpower at scale                                                            |
| `*`      | user returning after a long break                            | see recent changes made to the records                                           | get back up to speed quickly                                                        |
| `*`      | HR team lead                                                 | delegate manpower allocation tasks to team members                               | manage the HR team efficiently                                                      |

[Return to Top](#table-of-contents)

### Use cases

(For all use cases below, the **System** is `HRConnect` and the **Actor** is the `user`, unless specified otherwise)

---

**Use case: Remove an employee**

**MSS**

1. User requests to list employees.
2. HRConnect shows a list of employees with their ids.
3. User requests to delete a specific employee in the list by their id.
4. HRConnect deletes the employee.

   Use case ends.

**Extensions**

- 2a. The list is empty.

  Use case ends.

- 3a. The given id is invalid.

  - 3a1. HRConnect shows an error message.

    Use case resumes at step 2.

---

**Use case: Remove a project**

**MSS**

1. User requests to list projects.
2. HRConnect shows a list of projects with their ids.
3. User requests to delete a specific project in the list by its id.
4. HRConnect deletes the project.

   Use case ends.

**Extensions**

- 2a. The list is empty.

  Use case ends.

- 3a. The given id is invalid.

  - 3a1. HRConnect shows an error message.

    Use case resumes at step 2.

---

**Use case: Assign an employee to a project**

**MSS**

1. User requests to list employees.
2. HRConnect shows a list of employees with their ids.
3. User requests to list projects.
4. HRConnect shows a list of projects with their ids.
5. User requests to assign a specific employee by their id to a specific project by its id.
6. HRConnect assigns the employee to the project.

   Use case ends.

**Extensions**

- 2a. The list is empty.

  Use case ends.

- 4a. The list is empty.

  Use case ends.

- 5a. Either of the given ids is invalid.

  - 5a1. HRConnect shows an error message.

    HRConnect displays the list of projects if the project id is invalid and the list of employees if the employee id is invalid. Use case resumes at step 4.

---

**Use case: Un-assign an employee from a project**

**MSS**

1. User requests to list projects.
2. HRConnect shows a list of projects with their ids.
3. User selects a project by its id.
4. HRConnect shows a list of employees assigned to the project.
5. User requests to un-assign a specific employee by their id from the selected project.
6. HRConnect un-assigns the employee from the project.

   Use case ends.

**Extensions**

- 2a. The list is empty.

  Use case ends.

- 4a. The list is empty.

  Use case ends.

- 5a. The given id is invalid.

  - 5a1. HRConnect shows an error message.

    Use case resumes at step 4.

---

**Use case: Filter employees with desired skills**

**MSS**

1. User requests to list skills the employees in the company have.
2. HRConnect shows a list of skills.
3. User selects a few skills.
4. User requests to list all employees with all of the skills selected.
5. HRConnect lists all employees with all of the skills selected.

   Use case ends.

**Extensions**

- 2a. The list of skills is empty.

  Use case ends.

- 3a. User selects invalid skills.

  - 3a1. HRConnect shows an error message.

    Use case resumes at step 2.

[Return to Top](#table-of-contents)

---

### Non-Functional Requirements

1.  _Technical Requirement_: The App should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  _Performance Requirement_: Should be able to hold up to 1000 employees without a noticeable sluggishness in performance for typical usage.
3.  _Performance Requirement_: The App should respond to most commands within 200 milliseconds.
4.  _Quality Requirement_: A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
5.  _Quality Requirement_: A HR professional who has never used software to manage manpower should be able to learn basic operations like adding, deleting and assigning employees to projects within the first 10 minutes of reading the user guide.
6.  _Fault Tolerance_: The App should be able to handle corrupted data without crashing.
7.  _Documentation_: The code should be well-documented so that maintainers new to the project can quickly understand and contribute to the codebase.
8.  _Constraints_: The App should be backward compatible with data produced by earlier versions of the App.
9.  _Privacy Requirement_: The App should comply with the Personal Data Protection Act (PDPA) in handling personal information.
10. _Notes about project scope_: The App is not required to handle the actual firing / hiring of an employee or the completion or termination of a project.

[Return to Top](#table-of-contents)

### Glossary

- **Mainstream OS**: Windows, Linux, Unix, MacOS.
- **CLI**: Command Line Interface - A way of interacting with a computer system by inputting lines of text. The keyboard is primarily used.
- **GUI**: Graphical User Interface - A way of interacting with a computer system where graphical elements such as windows, buttons, and menus are used.
- **Private contact detail**: A contact detail that is not meant to be shared with others.
- **HR**: Human Resources - A department responsible for finding, hiring, and training employees.
- **Job candidate**: An applicant who is being considered for a job. The applicant is not yet an employee.

[Return to Top](#table-of-contents)

---

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the .jar file and store it in an empty folder.

   1. Double-click the .jar file  
      - Expected: Shows the GUI with a set of sample contacts, projects, and assignments. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the .jar file.<br>
      - Expected: The most recent window size and location is retained.

### Deleting an employee

1. Deleting an employee while all employees are being shown

   1. Prerequisites: List all employees using the `listemployees` command. Multiple employees in the list.

   1. Test case: `delete 1`<br>
      - Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      - Expected: No employee is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      - Expected: Similar to previous.

### Other commands

1. Using / Testing other commands in HRConnect

   1. Refer to the [User Guide "Features" section](https://ay2425s1-cs2103t-t15-4.github.io/tp/UserGuide.html#features) for formats, examples, and expected outputs for each command.
   2. Enter the commands with the provided format and parameter limitations.
      - Expected: The output performs the same as the listed expected output.

### Saving data

1. Dealing with missing/corrupted data files

   1. With the app open or closed, find the data file in the folder you placed the .jar file, as `data/hrconnect.json`.
   2. Delete the file.
   3. If the app is not open, launch the app by double-clicking the jar file.
   4. Create or delete any Employee, Project, or Assignment. 
      - Expected: A new data file is created at `data/hrconnect.json` containing the information in the app at the time.

2. Transferring data

   1. Prerequisites: Make some changes to the sample data (so it is different from the default entries, e.g. `addproject`).
   3. Copy the data folder at `data` (including `hrconnect.json` inside) into an empty folder.
   3. Copy the .jar file into this same folder (not into `data`!).
   4. Run the .jar file.
      - Expected: The changes you made are still displayed in the app.

[Return to Top](#table-of-contents)

---

## Appendix: Effort

HRConnect is based on the [AddressBook Level-3 (AB3)](https://github.com/se-edu/addressbook-level3) ([UG](https://se-education.org/addressbook-level3/UserGuide.html), [DG](https://se-education.org/addressbook-level3/DeveloperGuide.html)).

### Challenges and Difficulty
Both AB3 and HRConnect deal with their stored data as entities (`Person` for AB3, `Employee`, `Project` and `Assignment` for HRConnect).  

However, HRConnect ended up as a significantly larger project due to the multiple entities involved (instead of only one). HRConnect also ended up more complicated in general due to the interactions between these entities. For instance, Assignment represents a link between an Employee and a Project, and we also had to deal with handling them in case of deletions to Employees or Projects.

Integration was also a challenge at the start, mainly due to some members' unfamiliarity with GitHub and Git. There were a few merge conflicts that we were able to resolve. However, we were able to mitigate much of the pain points by splitting the work up into relatively defined portions.

### Reuse
In the earlier stages of the project, some effort was saved by reusing certain parts of AB3 code. For instance, the code for adding and deleting projects was a heavily modified version of the original AB3 commands. 

Due to the increasing size of our project, we have since refactored certain parts (e.g. all three `list` commands) for better code structure.

### Achievements

We had a clear and defined vision of the requirements and features from the start, which helped with executing the implementation (coding) portion of the project.

We are happy to say we were able to match all of our milestones, even the earlier ones. 

We were also able to achieve a high level of test coverage for each Pull Request, and our features are confirmed to work well for the average target user so far.

[Return to Top](#table-of-contents)
