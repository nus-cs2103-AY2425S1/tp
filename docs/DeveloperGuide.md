---
layout: default.md
title: "Developer Guide"
pageNav: 3
---

# TechConnect Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

---

## **Acknowledgements**

This application reuses/adapts ideas, code, and documentation from various sources. Below is a list of external libraries and references used in the development of TechConnect:

- **JavaFX** for the graphical interface.
- **PlantUML** for generating UML diagrams in documentation.
- **JUnit** for unit testing.
- **GSON** for JSON serialization and deserialization.

---

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md) for installation and setup instructions.

---

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the app.

Given below is an overview of the main components and their interactions.

**Main components of the architecture**

- **`Main`**: This component consists of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java). It handles the launch and shutdown of the app.
    - At app launch, it initializes other components in the correct sequence and connects them.
    - At shutdown, it triggers cleanup operations for other components.

- **Core Components**:
    - **[UI](#ui-component)**: Provides the graphical interface and command line interface for user interactions.
    - **[Logic](#logic-component)**: Responsible for parsing and executing commands.
    - **[Model](#model-component)**: Manages the application data and state.
    - **[Storage](#storage-component)**: Handles data storage and retrieval.

- **Commons**: A set of utility classes used by multiple components.

**Component Interactions**

The *Sequence Diagram* below shows the interactions among components for the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each main component defines its API through an interface, with a `Manager` class implementing each API.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements it in the `LogicManager.java` class. Other components interact with a component via its interface to reduce coupling with specific implementations.

The sections below provide more details of each component.

### UI Component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-F10-4/tp/blob/master/src/main/java/seedu/address/ui/Ui.java).

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of multiple components, including the `MainWindow`, `CommandBox`, `ResultDisplay`, `CompanyListPanel`, and `StatusBarFooter`. Each of these components inherits from `UiPart`.

- The `UI` component interacts with `Logic` to execute user commands.
- It also listens for changes in the `Model` to update the UI accordingly.
- The UI component is built using JavaFX, and layout files are stored as `.fxml` files in the `view` directory.

### Logic Component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-F10-4/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The following sequence diagram demonstrates interactions within `Logic` when executing `delete 1`.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g., to delete a company).
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g., during testing.

### Model Component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-F10-4/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="600" />


The `Model` component,

* stores the address book data i.e., all `Company` objects (which are contained in a `UniqueCompanyList` object).
* stores the currently 'selected' `Company` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Company>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Company` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Company` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="600" />

</box>


### Storage Component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-F10-4/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common Classes

Classes used by multiple components are in the `seedu.address.commons` package.

---

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### TagBuilder Class

The `TagBuilder` class acts as a single entry point for creating different types of tags based on user input. It ensures backward compatibility by maintaining a centralized `build` method for tag creation. This approach allows for:
- **Backward Compatibility**: The `TagBuilder` preserves the original design where tags were treated as a single entity with one entry point.
- **Extensibility**: New specialized tags can be introduced without modifying the other parts of the codebase's logic, as all tag creation logic is encapsulated in `TagBuilder`.
- **Centralized Logic**: The logic to determine tag types (e.g., `DifficultyTag`, `SalaryTag`, etc.) based on user inputs is managed in one place, making the code more maintainable. This is particularly useful
    in our CLI app where the user inputs could be anything where tag types are determined during run time.

Below is a sample sequence diagram

<puml src="diagrams/TagBuilder.puml" width="600" />


---

Each feature is implemented with careful consideration of compatibility and extensibility, allowing the codebase to evolve while supporting existing functionality.


**[Developer Notes]** For a more complete integration with the User Guide commands and example use cases, developers may wish to review the parsing logic in `XYZCommandParser` classes, specifically how tags and parameters are handled. This includes cases for special tags (e.g., `t/salary_HIGH`, `t/wlb_MEDIUM`), as outlined in the User Guide.

---

## **Documentation, logging, testing, configuration, dev-ops**

- [Documentation guide](Documentation.md)
- [Testing guide](Testing.md)
- [Logging guide](Logging.md)
- [Configuration guide](Configuration.md)
- [DevOps guide](DevOps.md)

---

## **Appendix: Requirements**

### Product Scope

**Target User Profile**:

* Students or recent graduates who are actively looking for job opportunities, specifically internships in the tech industry.
* Users who prefer using a desktop app over mobile or web applications.
* Users comfortable with typing commands, benefiting from a Command Line Interface (CLI) for faster data entry.
* Users looking for an organized way to manage a list of companies, contact statuses, and related job application data.

**Value Proposition**:
TechConnect assists students in managing and organizing their internship and job applications efficiently. The app enables users to bookmark, tag, and retrieve company information with ease, all through a simple, efficient command-based interface.

---

### User Stories

| Priority | As a …​                                                 | I want to …​                                    | So that I can…​                                                                   |
|----------|---------------------------------------------------------|-------------------------------------------------|-----------------------------------------------------------------------------------|
| `* * *`  | new user                                                | view usage instructions                         | quickly get started with the app and learn available commands                     |
| `* * *`  | user                                                    | add a new company                               | keep a record of companies I'm interested in applying to                          |
| `* * *`  | user                                                    | delete a company                                | remove outdated or irrelevant entries from my list                                |
| `* * *`  | user                                                    | find a company by name or tag                   | quickly locate specific company details without searching manually                |
| `* * *`  | user                                                    | add tags to companies                           | organize companies by categories like salary or work-life balance                 |
| `* *`    | user                                                    | bookmark a company                              | prioritize companies that I’m particularly interested in                          |
| `* *`    | user                                                    | edit information of a company                   | keep company data up-to-date                                                      |
| `* *`    | user                                                    | list all bookmarked companies                   | easily access my top-priority companies                                           |
| `*`      | user                                                    | update application status for a company         | track my progress with each company                                               |
| `*`      | user                                                    | clear all entries                               | start over with a fresh list when needed                                          |

---

### Use Cases

#### **System: TechConnect (TC)**

---

**Use Case: UC1 - Add a Company**

**Actor**: User

**MSS**:

1. User enters the details of a company (e.g., name, contact information, tags) into the system.
2. TC adds the company to the contact list and confirms successful addition.

   Use case ends.

**Extensions**:

* 1a. The input format is incorrect.

    * 1a1. TC displays an error message.
  
    * Use case resumes at step 1.

* 2a. The company already exists in the list.

    * 2a1. TC notifies the user and does not add a duplicate entry.
  
    * Use case ends.

---

**Use Case: UC2 - Edit a Company**

**Actor**: User

**MSS**:

1. User selects a company to edit from the list.
2. TC updates the company details and confirms successful modification.

   Use case ends.

**Extensions**:

* 1a. The specified company does not exist.

    * 1a1. TC displays an error message.
  
    * Use case ends.

* 1b. The input format is incorrect.

    * 1b1. TC displays an error message.
  
    * Use case resumes at step 1.

* 1c. User tries to edit the name of the company which matches another existing company.

    * 1c1. TC displays an error message as no duplicate company names allowed.

    * Use case resumes at step 1.
    

---

**Use Case: UC3 - Bookmark a Company**

**Actor**: User

**MSS**:

1. User selects a company to bookmark.
2. TC adds the company to the bookmarked list and confirms the addition.

   Use case ends.

**Extensions**:

* 1a. The company is already bookmarked.

    * 1a1. TC notifies the user that the company is already bookmarked.
  
    * Use case ends.

---

**Use Case: UC4 - Find Companies by Tag or Name**

**Actor**: User

**MSS**:

1. User enters a tag or name keyword.
2. TC filters the list and displays all matching companies.

   Use case ends.

**Extensions**:

* 1a. No companies match the search criteria.

    * 1a1. TC displays a message indicating no matches found.
  
    * Use case ends.

---

### Non-Functional Requirements

1. Should work on all mainstream operating systems (Windows, macOS, Linux) with Java 17 or later installed.
2. The application should run efficiently with up to 100 company entries and 100 tags without a noticeable performance degradation.
3. Application data should be saved locally in JSON format, ensuring user privacy.
4. The application should have a user-friendly interface with clear command structure and informative error messages.
5. Modifications should be automatically saved, preventing data loss if the app closes unexpectedly.
6. Should be modular, following Java coding standards, for ease of maintenance and updates.
7. TechConnect should work offline to allow users to manage applications without an internet connection.

---

### Glossary

| Term                      | Definition                                                                                                 |
|---------------------------|------------------------------------------------------------------------------------------------------------|
| **Mainstream OS**         | Refers to operating systems like Windows, macOS, and Linux.                                                |
| **Bookmark**              | A feature to mark a company for priority access or future reference.                                       |
| **Command Line Interface (CLI)** | A text-based interface that allows users to type commands to interact with the app.              |
| **Tag**                   | A label for categorizing and filtering companies (e.g., salary_HIGH, wlb_LOW).                             |
| **Company List**          | The main list in TechConnect where company information is stored and displayed.                            |
| **Bookmark List**         | A separate list within TechConnect to store high-priority or favorited companies.                          |

---

## **Appendix: Instructions for Manual Testing**

<box type="info" seamless>

**Note:** These instructions provide a starting point for exploratory testing. Testers should expand beyond the provided cases to ensure robust coverage.

</box>

### Launch and Shutdown

1. **Initial Launch**:

   - Download the latest `.jar` file and save it to a new folder.

   - Run the app by double-clicking the `.jar` file.

   - **Expected Result**: The GUI opens with sample data displayed.

2. **Saving Window Preferences**:

   - Resize the application window and move it to a preferred location.
   
   - Close and re-open the app by double-clicking the `.jar` file.
   
   - **Expected Result**: Window size and position are retained.

### Adding and Deleting Companies

1. **Adding a Company**:

    - Use the `add` command with company details (e.g., `add n/Google p/98765432 ...`).
   
    - **Expected Result**: New company is added and displayed in the list.

2. **Deleting a Company**:

    - Use `delete INDEX` to remove a specific company.
   
    - **Expected Result**: Company is removed, and a success message is shown.
   
    - **Edge Cases**:
        - `delete 0`: Displays an error message.

        - `delete x` (where `x` exceeds list size): Displays an error message.

3. **Editing a Company**:

    - Use `edit INDEX` with updated information (e.g., `edit 1 p/91234567`).
   
    - **Expected Result**: Company details are updated in the list.

### Bookmarking and Finding Companies

1. **Bookmarking a Company**:

    - Use `bookmark INDEX` to mark a company as bookmarked.
   
    - **Expected Result**: Company appears in the bookmarked list.
   
    - **Edge Case**: Attempting to bookmark an already bookmarked company should display a message informing users that the company is already bookmarked.

2. **Finding Companies**:

    - Use `find NAME` or `find TAG` to search.
   
    - **Expected Result**: Only companies matching the search criteria are displayed.
   
    - **Edge Case**: Searching for non-existent terms should display a "There is no company that suits your keyword!" message.
---
