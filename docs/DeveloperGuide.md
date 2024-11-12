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
- [Implementation](#implementation)
  - [Student Command](#1-student-command)
  - [Company Command](#2-company-command)
  - [View Command](#3-view-command)
  - [Find Command](#4-find-command)
  - [Filtertag Command](#5-filtertag-command)
  - [Import Command](#6-import-command)
- [Planned Enhancements](#planned-enhancements)
  - [Disallow Duplicate Phone Number Across Contacts](#1-disallow-duplicate-phone-numbers-across-contacts)
  - [Consistent Case-Insensitive Tag Handling](#2-consistent-case-insensitive-tag-handling)
  - [Make Error Message for View Command More Specific](#7-make-error-message-for-view-command-more-specific)
  - [`Deletetag all` command does not work as intended on an empty list](#8-deletetag-all-command-does-not-work-as-intended-on-an-empty-list)
  - [Restrict phone number field to 8 numbers](#9-restrict-phone-number-field-to-8-numbers)
  - [Improve Error Priority for Edit Command](#10-improve-error-priority-for-edit-command)
- [Documentation, logging, testing, configuration, dev-ops](#documentation-logging-testing-configuration-dev-ops)
- [Appendix: Requirements](#appendix-requirements)
  - [Product scope](#product-scope)
  - [User stories](#user-stories)
  - [Use cases](#use-cases)
  - [Non-Functional Requirements](#non-functional-requirements)
  - [Glossary](#glossary)
- [Appendix: Instructions for manual testing](#appendix-instructions-for-manual-testing)
  - [Launch and shutdown](#launch-and-shutdown)
  - [GUI Testing](#gui-testing)
  - [Viewing help](#viewing-help)
  - [Listing all contacts](#listing-all-contacts)
  - [Viewing a contact](#viewing-a-contact)
  - [Clearing all entries](#clearing-all-entries)
  - [Exiting the program](#exiting-the-program)
  - [Adding a student](#adding-a-student)
  - [Adding a company](#adding-a-company)
  - [Editing a contact](#editing-a-contact)
  - [Deleting contact(s)](#deleting-contacts)
  - [Locating persons by name](#locating-persons-by-name)
  - [Filtering contacts by tags](#filtering-contacts-by-tags)
  - [Tracking contacts by category](#tracking-contacts-by-category)
  - [Adding tag(s) to contact](#adding-tags-to-contact)
  - [Deleting tag(s) from contact](#deleting-tags-from-contact)
  - [Importing CSV files](#importing-csv-files)
  - [Exporting CSV files](#exporting-csv-files)

---

## **Acknowledgements**

- Adapted from: [AB3](https://se-education.org/addressbook-level3/)
- Libraries used: [JavaFX](https://openjfx.io/), [Jackson](https://github.com/FasterXML/jackson),
  [JUnit5](https://github.com/junit-team/junit5)

---

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

---

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

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

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter`, `HelpWindow` and `ContactDisplay`. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

- executes user commands using the `Logic` component.
- listens for changes to `Model` data so that the UI can be updated with the modified data.
- keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
- depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

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

- When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
- All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />

The `Model` component,

- stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
- stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
- stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
- does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,

- can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
- inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
- depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

---

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### 1. Student Command

The student command in AdmiNUS is used to add a new student entry to the list of contacts. This command requires specific attributes such as student ID, name, phone number, email, address, and optional tags. The system will validate each input and check for duplicate entries to prevent the addition of a student with identical details.

#### Current Implementation

This is a high-level view of what occurs when the `student` command is executed:

<img src="./images/StudentCommandSequenceDiagram.png">

Student command performs the following checks in order:

- **Flag Validation**: Ensures all mandatory flags (e.g., name, student ID, phone, email, address) are present, and no invalid flags are included.
- **Value Validation**: Confirms that each provided value for specified flags adheres to the expected format.
- **Duplicate Check**: Verifies that no existing student has the same student id.

### 2. Company Command

The `company` command is used to add a new company contact into the list. This command requires attributes such as the company name, phone number, email, address, industry, and optional tags. Similar to the student command, the system validates each input and checks for duplicates to ensure unique entries.

#### Current Implementation

This is a high-level view of what occurs when the `company` command is executed:

<img src="./images/CompanyCommandSequenceDiagram.png">

Company command performs the following checks in order:

- **Flag Validation**: Ensures all mandatory flags (e.g., name, phone, email, address, industry) are present, and no invalid flags are used.
- **Value Validation**: Confirms that each provided value for specified flags meets the required format.
- **Duplicate Check**: Verifies that no existing company has the same combination of name and industry

The following diagrams provides a high-level view of the logic flow for both `student` and `company` command:

<img src="./images/StudentCompanyCommandActivityDiagram-Add_Student_Company_Activity_Diagram.png">

_Note: The error messages will vary depending on which check fails._

### 3. View Command

The `view` command is used to view a specific contact on the contact display pane. To execute the command, users specify the `INDEX` of the contact to be viewed.

#### Current implementation

Here's an overview of what happens when the `view 1` command is being input by the user:
![ViewSequenceDiagram](images/ViewSequenceDiagram.png)
The activity diagram is as follows:
![ViewActivityDiagram](images/ViewActivityDiagram.png)

**The View Command checks:**

- that the specified index is positive and within the bounds of the contacts list.

### 4. Find Command

The `find` command is used to find contacts whose name match the specified keyword given by the user.

#### Current implementation

To execute the `find` command, users must enter a valid `KEYWORD` parameter, by ensuring that the `KEYWORD` parameter is not empty.

The sequence diagram below shows a high-level view of how the `find` operation works when the user inputs `find bob`.

<img src="images/FindSequenceDiagram.png" width="750" />

The activity diagram below shows the flow of the `find` operation.

<img src="images/FindActivityDiagram.png" width="750" />

### 5. Filtertag Command

The filtertag command is used to find contacts whose tags are the same as the specified keyword.

#### Current Implementation

To execute the `filtertag` command, users must enter a valid `KEYWORD` parameter, by ensuring that the `KEYWORD` parameter is not empty.

This is a high-level view of what occurs when user inputs `filtertag tagName`.

<img src="images/FiltertagSequenceDiagram.png"/>

### 6. Import Command

The `import` command allows you to bring data from a CSV file into the application, enabling seamless population of your contacts database from external sources.

#### Current Implementation

To execute the `import` command, users must enter a valid `FILE_PATH` parameter, by ensuring that the `FILE_PATH` parameter is not empty, is a valid file path and the file format is `.csv`.

The activity diagram below shows the flow of the `import` operation.

<img src="images/ImportActivityDiagram.png"/>

---

## Planned Enhancements

Group size: 5

### 1. Disallow Duplicate Phone Numbers Across Contacts

#### Current Issue:

Currently, the app allows users to add multiple contacts (both students and companies) with the same phone number. This can lead to potential confusion or data inconsistency, as users may unintentionally add duplicate contacts with identical phone numbers. In real-world scenarios, it is highly unlikely that two different individuals or entities would share the same phone number, making this behavior unnecessary and potentially misleading.

#### Proposed Enhancement:

Implement a restriction that prevents users from adding new contacts with phone numbers that already exist in the app. This restriction will apply to both student and company contacts, ensuring that each phone number is unique within the contact list. This change aims to enhance data integrity and user experience by reducing redundancy and aligning with real-world expectations where phone numbers are unique identifiers.

### 2. Consistent Case-Insensitive Tag Handling

#### Current Issue:

The current tag handling system in the app is inconsistent regarding case sensitivity across different commands. For example, when adding or deleting contacts, the `t/[TAG]` parameter is case-sensitive, meaning Paid and paid would be treated as separate tags. However, the `tag` command treats tags as case-insensitive, so attempting to add `paid` to a contact that already has the tag `Paid` will result in no action, as it recognises `paid` and `Paid` as the same tag. This inconsistency can lead to user confusion, as the system does not follow a unified approach to case sensitivity.

#### Proposed Enhancement:

Standardise the tag handling logic to be case-insensitive across all commands. This means that tags with the same letters but different capitalisations (e.g., `OwesMoney`, `owesmOney`, `OWESMONEY`) will be treated as identical tags in all scenarios, including adding contacts, adding tags, deleting tags and filtering tags. By making this adjustment, the program will align with standard user expectations of case-insensitivity, creating a more intuitive and consistent experience for users.

### 5. Specify t/ Prefix for Tag Inputs in filtertag

#### Current Issue: 

Currently, the filtertag command does not require a specific prefix for tag inputs, which can cause ambiguity or lead to misinterpretation of user input.Example: `filtertag friends` Users may inadvertently enter invalid data without realizing they did not need to specify tags explicitly, which can result in errors or unintended command behavior.

#### Proposed Enhancement: 

Introduce the t/ prefix for tag inputs in the filtertag command, requiring users to specify tags as t/<tag>. Example: `filtertag t/friends`. This enhancement clarifies user intent by indicating that they are filtering by a specific tag and aligns with other command syntax patterns that use prefixes for inputs. The t/ prefix will improve command consistency and reduce input errors, resulting in a more user-friendly experience.

### 6. More Specific Error Messages for Corrupted CSV Files in Import

#### Current Issue: 

The current error message for CSV file corruption in the import command is generic, providing minimal detail about the specific problem (e.g., missing fields or incorrect formatting). This lack of specificity may lead to user confusion, as they may be unsure of what exactly needs to be corrected in the CSV file to successfully import data.

#### Proposed Enhancement: 

Enhance the CSV import error message to specify which fields are missing or incorrectly formatted. For example, `“Error: Missing compulsory field ‘name’ in row 3”` or `“Invalid category in row 5; expected ‘student’ or ‘company’”`. By providing more precise feedback on CSV formatting issues, users will be better equipped to correct their files quickly, minimizing trial and error and streamlining the import process. This enhancement will improve user experience by making error messages actionable and informative.

### 7. Make Error Message for View Command More Specific

#### Current Issue:

The format for `view` command is `view INDEX`. The valid input for `INDEX` is a positive integer. The current error message to handle a non-integer input is `"Index must be a positive number!"`, which is too general. For example, if `view 3.5` is being input, the user will be confused as `3.5` is a positive number.

#### Proposed Enhancement:

We plan to change the error message into `"Index must be a positive integer!"`.

### 8. Allow `deletetag all` Command to Handle an Empty List 

#### Current Issue:

If the user manages to end up with an empty list, by entering the input `clear` for example, followed by the command `deletetag all t/TAG`, the user is shown a message `Deleted the tags TAG from all contacts in the list`, even though this did not actually happen. This could bring confusion to users.

#### Proposed Enhancement:

Instead, we plan to show an error message whenever the user tries to use the `deletetag all` command on an empty list. The error message will be similar to `Cannot delete tags from an empty list!`. This will give the user a clear idea and bring less confusion.

### 9. Restrict Phone Number Field to 8 Numbers

#### Current Issue:

Currently, the phone number field accepts inputs as long as they are numbers and at least 3 digits long. However, given that our target audience is NUS club administrators, it would be appropriate for phone numbers to have at least 8 digits.

#### Proposed Enhancement:

Perform input check for phone numbers to at least make sure it has 8 digits.

### 10. Improve Error Priority for Edit Command

#### Current Issue:

When attempting to edit fields restricted to specific contact types (such as `STUDENT_ID` and `INDUSTRY`), the application currently prioritises validating the input format before checking if the field is editable for the specified contact type. This results in a situation where users are first prompted to correct the format, even if they should ultimately be informed that the field cannot be edited for that contact type. For example, attempting to edit a student’s `INDUSTRY` field will first trigger a format error message rather than directly informing the user that the `INDUSTRY` field cannot be edited for students.

#### Proposed Enhancement:

Adjust the error-checking sequence in the edit command to prioritise checks on editability based on contact type before format validation. This means users will receive a direct message if they attempt to edit fields restricted for a contact type (e.g., "Industry field cannot be edited for a student contact"), reducing unnecessary steps and making the error feedback more user-friendly.

---

## **Documentation, logging, testing, configuration, dev-ops**

- [Documentation guide](Documentation.md)
- [Testing guide](Testing.md)
- [Logging guide](Logging.md)
- [Configuration guide](Configuration.md)
- [DevOps guide](DevOps.md)

---

## **Appendix: Requirements**

### Product scope

**Target user profile**:

- NUS club administrator responsible for managing contacts
- prefer desktop apps over other types
- can type fast
- prefers typing to mouse interactions
- is reasonably comfortable using CLI apps

**Value proposition**: Helps NUS club administrators manage and track different categories of contacts (e.g., students, companies) faster than a typical mouse/GUI-driven app.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                      | I want to …​                                                                       | So that I can…​                                                                    |
| -------- | -------------------------------------------- | ---------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------- |
| `* * *`  | new user                                     | see usage instructions                                                             | refer to instructions when I forget how to use the App                             |
| `* * *`  | user                                         | add new contacts                                                                   | manage contact information quickly                                                 |
| `* * *`  | user                                         | view all contacts                                                                  | see all my contacts saved in one screen                                            |
| `* * *`  | user                                         | delete a contact                                                                   | remove entries that I no longer need                                               |
| `* *`    | user                                         | edit existing contacts                                                             | amend mistakes/update new info on my contacts                                      |
| `* *`    | potential user                               | see the app populated with sample data                                             | easily try and see how the app will look like when it is in use                    |
| `* *`    | new user ready to use the app                | remove all current data                                                            | remove all sample data I used when exploring the app                               |
| `* *`    | admin user                                   | track contacts by category (e.g., students, companies)                             | quickly retrieve specific groups of contacts (e.g., all students or all companies) |
| `* *`    | admin user                                   | filter contacts by tag (e.g., "sponsor", "member")                                 | find contacts associated with specific events or groups                            |
| `* *`    | familiar user of the app                     | save the contacts under a favourites tab                                           | easily access the contacts that I frequently use                                   |
| `* *`    | familiar user of the app                     | tag certain contacts                                                               | remember where I know the contacts from                                            |
| `* *`    | new user                                     | learn how to use the app quickly                                                   | use the app frequently with other club admins                                      |
| `* *`    | impatient user                               | use shortcut commands instead of the full name of the commands                     | make minimal spelling mistakes when I am entering the commands                     |
| `* *`    | familiar user of the app                     | search contacts by name                                                            | easily find the contact person instead of scrolling                                |
| `* *`    | admin user with frequent changes in schedule | mark contacts as "high priority" or "low priority"                                 | focus on the most relevant people when my schedule is tight                        |
| `*`      | new user who is unfamiliar with English      | have suggestions on commands to enter                                              | enter the right commands if I am unsure on how to spell certain words              |
| `*`      | familiar user of the app                     | mass add a large list of contacts                                                  | avoid from entering repetitive commands                                            |
| `*`      | impatient user                               | experience reasonable response time while up to 1000 concurrent users are using it | use the app even when the traffic is at the maximum expected level                 |
| `*`      | impatient user                               | manage up to 1000 contacts with fast response time                                 | ensure smooth usage even when managing a large contact list                        |
| `*`      | busy user                                    | quickly import contacts from other platforms (e.g., phone, social media, email)    | avoid manually inputting every new contact into AdmiNUS                            |
| `*`      | busy admin user                              | easily export the contact list                                                     | share or back up the contact list for other admin team members or departments      |

### Use cases

(For all use cases below, the **System** is the `AdmiNUS` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - Add a student**

**MSS**

1. User requests to add a new student by entering the required information (name, student id, phone number, email, and address) and optional information (tag).
2. AdmiNUS adds the student and displays a success message.

   Use case ends.

**Extensions**

- 1a. The given arguments are invalid.

  - 1a1. AdmiNUS shows an error message for the specific invalid field.

    Use case resumes at step 1.

- 2a. Student with the same student id already exists.

  - 2a1. AdmiNUS shows an error message about duplicate student.

    Use case resumes at step 1.

**Use case: UC02 - Add a company**

**MSS**

1. User requests to add a new company by entering the required information (name, industry, phone number, email, and address) and optional information (tag).
2. AdmiNUS adds the company and displays a success message.

   Use case ends.

**Extensions**

- 1a. The given arguments are invalid.

  - 1a1. AdmiNUS shows an error message for the specific invalid field.

    Use case resumes at step 1.

- 2a. Company with the same name and industry already exists.

  - 2a1. AdmiNUS shows an error message about duplicate company.

    Use case resumes at step 1.

**Use case: UC03 - List the contacts**

**MSS**

1.  User requests to list contacts.
2.  AdmiNUS shows a list of contacts.

    Use case ends.

**Extensions**

- 2a. The list is empty.

  Use case ends.

**Use case: UC04 - Delete a contact**

**MSS**

1.  User <u>requests to list contacts(UC03)</u>.
2.  User requests to delete a specific contact in the list.
3.  AdmiNUS deletes the contact.

    Use case ends.

**Extensions**

- 2a. The given index is invalid.

  - 2a1. AdmiNUS shows an error message.

    Use case resumes at step 2.

**Use case: UC05 - Edit a contact**

**MSS**

1. User <u>requests to list contacts(UC03)</u>.
2. User requests to edit a specific contact and the updated information.
3. AdmiNUS updates the contact and displays a success message.

   Use case ends.

**Extensions**

- 2a. The given index is invalid.
  - 2a1. AdmiNUS shows an error message.
    Use case resumes at step 2.
- 2b. The given arguments are invalid.
  - 2b1. AdmiNUS shows an error message for the specific invalid field.
    Use case resumes at step 2.
- 2c. The contact at the given index is a student, and the user tries to edit the industry field.
  - 2c1. AdmiNUS displays an error message indicating that editing the industry field for a student is not allowed.
    Use case resumes at step 2.
- 2d. The contact at the given index is a company, and the user tries to edit the student-related field.
  - 2d1. AdmiNUS displays an error message indicating that editing a student-related field for a company is not allowed.
    Use case resumes at step 2.

**Use case: UC06 - View a contact**

**MSS**

1. User requests to view a specific contact.
2. AdmiNUS displays the details of the contact.
   Use case ends.

**Extensions**

- 2a. The given index is invalid.
  - 2a1. AdmiNUS shows an error message.
    Use case ends.

**Use case: UC07 - Find contacts by name**

**MSS**

1. User requests to find contacts by name.
2. The system finds and displays the list of contacts with the specified name.

   Use case ends.

**Extensions**

- 2a. No contacts match the specified name.

  - 2a1. AdmiNUS displays an empty list.

    Use case ends.

**Use case: UC08 - Filter contacts by category**

**MSS**

1. User requests to filter contacts by category (e.g., student, company).
2. The system filters and displays the list of contacts belonging to the specified category.

   Use case ends.

**Extensions**

- 2a. No contacts match the specified category.

  - 2a1. AdmiNUS displays an error message and an empty list.

    Use case ends.

**Use case: UC09 - Filter contacts by tag**

**MSS**

1. User requests to filter contacts by specifying a tag (e.g., "group A").
2. AdmiNUS filters and displays the list of contacts associated with the specified tag.

   Use case ends.

**Extensions**

- 2a. No contacts are associated with the specified tag.
    - 2a1. AdmiNUS displays an empty list.

      Use case ends.

**Use case: UC10 - Import contacts from a CSV file**

**MSS**

1. User requests to import contacts by providing the file path of the CSV file.
2. AdmiNUS reads the CSV file and imports the contacts.
3. AdmiNUS displays a success message indicating the number of contacts imported.

   Use case ends.

**Extensions**

- 1a. The specified file path is invalid or does not end with `.csv`.
    - 1a1. AdmiNUS shows an error message indicating an invalid file path or incorrect file format.

      Use case resumes at step 1.

- 2a. The CSV file is corrupted or missing compulsory fields.
    - 2a1. AdmiNUS displays an error message specifying the missing or invalid fields.

      Use case resumes at step 1.

**Use case: UC11 - Export contacts to a CSV file**

**MSS**

1. User requests to export contacts by specifying the file path for the CSV file.
2. AdmiNUS exports the current contact list to the specified CSV file.
3. AdmiNUS displays a success message indicating the file path where the contacts were exported.

   Use case ends.

**Extensions**

- 1a. The specified file path is invalid or does not end with `.csv`.
    - 1a1. AdmiNUS shows an error message indicating an invalid file path or incorrect file format.

      Use case resumes at step 1.

- 1b. The system does not have permission to write to the specified path.
    - 1b1. AdmiNUS displays an error message indicating insufficient write permissions.

      Use case resumes at step 1.

- 2a. The specified file already exists.
    - 2a1. AdmiNUS overwrites the file without warning.

      Use case ends.

---

### Non-Functional Requirements

1. **Platform Compatibility**:
   - The application should work on any mainstream OS as long as it has Java 17 or above installed.
2. **Performance Requirements**:
   - The application should be able to hold up to 1000 tasks without noticeable sluggishness for typical usage scenarios.
   - A user should be able to execute most commands (e.g., adding, deleting, or updating tasks) in under 1 second.
3. **Usability Requirements**:
   - The user interface should be intuitive and allow users to easily understand how to input commands without prior extensive training.
   - Error messages should be user-friendly and provide enough information to guide the user towards resolving the issue.
4. **Scalability Requirements**:
   - The system should support additional features or commands without major changes to the existing architecture.
   - The task list should scale efficiently to handle future use cases, such as storing significantly larger amounts of data.
5. **Reliability and Availability**:
   - The application should be reliable, ensuring data consistency even after unexpected shutdowns.
   - Data should be auto-saved periodically to minimize the risk of data loss during crashes.
6. **Security Requirements**:
   - The application should store user data securely, ensuring that unauthorized access is prevented.
   - User data should not be stored in plaintext, and sensitive data should be encrypted where applicable.
7. **Portability Requirements**:
   - The application should be executable without a complex installation process, ideally by running a standalone JAR file.
8. **Maintainability Requirements**:
   - The codebase should follow good software engineering principles, making it easy for new developers to add features or fix bugs.
   - The system should have high cohesion and low coupling between components to facilitate easier updates and maintenance.
9. **Extensibility Requirements**:
   - The architecture should allow for the addition of new task types (e.g., recurring tasks) with minimal changes.
   - The system should allow for integration with third-party tools (e.g., cloud-based storage) to extend its functionality.
10. **Backup and Recovery**:
    - The application should have a data backup mechanism, allowing users to recover from data corruption or loss.

---

### Glossary

- **NUS Admin**: An NUS club admin user responsible for managing contacts of students, companies, etc.
- **Mainstream OS**: Refers to commonly used operating systems, including Windows, macOS, and Linux distributions.
- **Category**: A label that represents the type of contact (e.g., student, company) used for filtering and sorting contacts.
- **Tag**: A keyword or label associated with a contact that allows for easy grouping and filtering.
- **Command**: A user input string that triggers a specific action within the Vinegar application.
- **User Interface (UI)**: The part of the application that users interact with, which includes graphical components like command boxes and task lists.
- **CLI (Command Line Interface)**: A text-based user interface through which users interact with the application by typing commands.
- **Contact Display**: A GUI feature that displays detailed information about a contact.
- **Scalability**: The capacity of the system to handle increasing amounts of data or user load without performance degradation.
- **JavaFX**: A software platform used for creating and delivering desktop applications with graphical user interfaces in Java.
- **Data Persistence**: The characteristic of data that outlives the execution of the process that created it, usually achieved through saving data to a file or database.
- **Parser**: A component that interprets user input (commands) and converts them into actions for the application.
- **Error Handling**: The process of identifying, diagnosing, and responding to errors or exceptions that occur during program execution.
- **Encryption**: The process of converting information or data into a code, especially to prevent unauthorized access.
- **Data Backup**: The process of copying and archiving data to prevent loss in case of system failure or data corruption.
- **Extensibility**: The ability of the software to be extended with new features or components with minimal impact on existing functionality.
- **Reliability**: The measure of the system’s ability to operate without failure and produce consistent results under specified conditions.

---

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. **Initial launch**

   1. Download the jar file and copy into an empty folder

   2. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. **Saving window preferences**

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
      Expected: The most recent window size and location is retained.

### GUI Testing

1. **Main window initialization**

   1. Launch the application.
      **Expected:**
      a. Window title is "AdmiNUS".
      b. AdmiNUS icon is displayed in title bar.
      c. Menu bar, command box, result display pane, person list pane, contact display pane and status bar footer are loaded correctly.

2. **Verify "Help" Menu item functionality**

   1. Click on the "Help" menu.
   2. Click the "Help" item.
      **Expected:** A list of all valid commands are shown on the contact display pane.

3. **Help window functionality**

   1. Type `helpwindow` on the command box.
      **Expected:** A help dialog opens displaying the link to AdmiNUS user guide.

4. **Result Display behavior and sizing**

   1. Rezise the result display pane vertically.
      **Expected:** Result display pane remains between 100 and 200 pixels.

5. **Contact Display behavior and sizing**

   1. Launch the application.
      **Expected:** Contact display pane takes up 45% of the main window width.
   2. Rezise the contact display pane vertically.
      **Expected:** Contact display pane remains between 300 and 1000 pixels.

6. **Verify "Exit" Menu item functionality**
   1. Click on the "Exit" menu.
   2. Click the "Exit" item.
      **Expected:** Application closes without errors.

### Viewing help

1. **Viewing help instructions**
   - **Prerequisites**: Ensure that AdmiNUS is running.
   - **Test case**: `help`  
     **Expected**: A message explaining the various commands available is shown. No changes to the contact list. Status message updates to indicate the help command was executed.
   - **Test case**: `help extra`  
     **Expected**: Command is interpreted as `help`, and the help message is shown. Status message updates as expected.

### Listing all contacts

1. **Listing contacts in AdmiNUS**
   - **Prerequisites**: Ensure that AdmiNUS is running and there are contacts present in the list.
   - **Test case**: `list`  
     **Expected**: All contacts are displayed in the list. Status message updates to indicate the command was executed.
   - **Test case**: `list 123`  
     **Expected**: Command is interpreted as `list`, and the contact list is shown. Status bar remains the same.

### Viewing a contact

1. **Viewing details of a specific contact**
   - **Prerequisites**: Ensure that AdmiNUS is running and contacts are listed.
   - **Test case**: `view 1`  
     **Expected**: Details of the first contact in the list are shown. Status message updates with relevant details.
   - **Test case**: `view 0`  
     **Expected**: No contact details shown. Error message displayed, status bar remains unchanged.
   - **Other incorrect commands to try**: `view`, `view x` (where x exceeds list size)  
     **Expected**: Error details shown in the status message. Status bar remains the same.

### Clearing all entries

1. **Clearing all contacts in AdmiNUS**
   - **Prerequisites**: Ensure that AdmiNUS is running and contacts are listed.
   - **Test case**: `clear`  
     **Expected**: All entries in AdmiNUS are cleared. Status message updates confirming the action, timestamp is updated.
   - **Test case**: `clear extra`  
     **Expected**: Command is interpreted as `clear`. Status message updates confirming the action, timestamp is updated.

### Exiting the program

1. **Exiting AdmiNUS**
   - **Prerequisites**: Ensure that AdmiNUS is running.
   - **Test case**: `exit`  
     **Expected**: Program closes gracefully.
   - **Test case**: `exit extra`  
     **Expected**: Command is interpreted as `exit`, and the program closes.

### Adding a student

1. **Adding a student contact**
   - **Prerequisites**: Ensure that AdmiNUS is running and the contact list is visible.
   - **Test case**: `student n/John Doe id/A0123456X p/98765432 e/johnd@example.com a/John street, block 123, #01-01`  
     **Expected**: A student named John Doe is added. Status message updates to show success. Timestamp in the status bar is updated.
   - **Test case**: `student n/John id/invalid_id p/98765432 e/john@example.com a/Some Address`  
     **Expected**: No contact is added. Error message shown in status. Status bar remains unchanged.

### Adding a company

1. **Adding a company contact**
   - **Prerequisites**: Ensure that AdmiNUS is running and the contact list is visible.
   - **Test case**: `company n/Newgate Prison i/Security p/1234567 e/newgateprison@example.com a/Newgate Prison t/prison t/facility`  
     **Expected**: Company contact added successfully. Status message updates with details, timestamp updated.
   - **Test case**: `company n/Newgate p/invalid_phone e/email@domain.com a/Address`  
     **Expected**: No contact added. Error message shown in the status message. Status bar remains unchanged.

### Editing a contact

1. **Editing an existing contact**
   - **Prerequisites**: Ensure that AdmiNUS is running and a contact list is visible.
   - **Test case**: `edit 1 p/91234567 e/johndoe@example.com`  
     **Expected**: First contact is updated. Status message confirms the update, timestamp updated.
   - **Test case**: `edit 0 p/12345678`  
     **Expected**: No contact edited. Error message shown. Status bar unchanged.

### Deleting contact(s)

1. **Deleting one or more contacts**
   - **Prerequisites**: Ensure that AdmiNUS is running and contacts are listed, where there are at least 3 contacts currently shown in the list.
   - **Test case**: `delete 2 3`  
     **Expected**: 2nd and 3rd contacts are deleted. Status message confirms the action, timestamp updated.
   - **Test case**: `delete 0`  
     **Expected**: No contact deleted. Error message shown. Status bar unchanged.
   - **Test case**: `delete all`  
     **Expected**: All contacts currently shown in the list are deleted. Status message confirms the action, timestamp updated.

### Locating persons by name

1. **Finding contacts by name**
   - **Prerequisites**: Ensure that AdmiNUS is running and contacts are listed.
   - **Test case**: `find John`  
     **Expected**: Contacts with "John" in their name are shown. Status message updates.
   - **Test case**: `find unknown`  
     **Expected**: No contacts found. Status message updates with no results.

### Filtering contacts by tags

1. **Filtering contacts by tag**
   - **Prerequisites**: Ensure that AdmiNUS is running and contacts are listed with tags.
   - **Test case**: `filtertag buddies`  
     **Expected**: Contacts with the tag "buddies" are shown. Status message updates.
   - **Test case**: `filtertag non-existent-tag`  
     **Expected**: No contacts found. Status message updates with no results.

### Tracking contacts by category

1. **Tracking contacts by category**
   - **Prerequisites**: Ensure that AdmiNUS is running and contacts are listed.
   - **Test case**: `track student`  
     **Expected**: All student contacts are shown. Status message updates.
   - **Test case**: `track company`  
     **Expected**: All company contacts are shown. Status message updates.
   - **Test case**: `track non-category`  
     **Expected**: No contacts found. Error message shown. Status bar unchanged.

### Adding tag(s) to contact

1. **Adding tags to an existing contact**
   - **Prerequisites**: Ensure that AdmiNUS is running and at least one contact(s) are listed.
   - **Test case**: `tag 1 t/Y2 t/computerScience`  
     **Expected**: Tags added to the first contact currently shown in the list. Status message updates. Timestamp updated.
   - **Test case**: `tag 0 t/invalid`  
     **Expected**: No tag added. Error message shown. Status bar unchanged.
   - **Test case**: `tag all t/contacted`  
     **Expected**: Tag added to all contacts currently shown in the list. Status message updates. Timestamp updated.

### Deleting tag(s) from contact

1. **Deleting tags from an existing contact**
   - **Prerequisites**: Ensure that AdmiNUS is running and at least one contact(s) are listed, where all contacts have the tag `contacted`.
   - **Test case**: `deletetag 1 t/Y2 t/computerScience`  
     **Expected**: Tags removed from the first contact currently shown in the list. Status message updates. Timestamp updated.
   - **Test case**: `deletetag 0 t/invalid`  
     **Expected**: No tags removed. Error message shown. Status bar unchanged.
   - **Test case**: `deletetag all t/contacted`  
     **Expected**: Tag removed from all contacts currently shown in the list. Status message updates. Timestamp updated.

### Importing CSV files

1. **Importing a CSV file with correct format**
   - **Prerequisites**: Ensure that AdmiNUS is running and the CSV file exists.
   - **Test case**: `import /path/to/data.csv`  
     **Expected**: Data imported successfully. Status message confirms import, timestamp updated.
   - **Test case**: `import /invalid/path.csv`  
     **Expected**: No data imported. Error message shown. Status bar unchanged.

### Exporting CSV files

1. **Exporting data to a CSV file**
   - **Prerequisites**: Ensure that AdmiNUS is running and there is data to export.
   - **Test case**: `export /path/to/output.csv`  
     **Expected**: Data exported successfully. Status message confirms export, timestamp updated.
   - **Test case**: `export /invalid/path/output.csv`  
     **Expected**: No data exported. Error message shown. Status bar unchanged.
