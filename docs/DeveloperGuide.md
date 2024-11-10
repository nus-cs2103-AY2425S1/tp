---
layout: page
title: Developer Guide
---
# **Table of Contents**
{:toc}
1. [Introduction to TalentSG](#talentsg)
2. [Purpose of this guide](#purpose-of-this-guide)
3. [How to use this guide](#how-to-use-this-guide)
4. [Acknowledgements](#acknowledgements)
5. [Setting up and getting started](#setting-up-getting-started)
6. [Design](#design)
    1. [Architecture](#architecture)
    2. [UI Component](#ui-component)
    3. [Logic Component](#logic-component)
    4. [Model Component](#model-component)
    5. [Storage Component](#storage-component)
7. [Implementation](#implementation)
    1. [Add Person Feature](#add-person-feature)
    2. [Delete Person Feature](#delete-person-feature)
    3. [Edit Person Feature](#edit-person-feature)
    4. [List Feature](#list-feature)
    5. [Find Person Feature](#find-person-feature)
    6. [Filter Status Feature](#filter-status-feature)
    7. [View Person Feature](#view-person-feature)
    8. [Summary Feature](#summary-feature)
    9. [Help Feature](#help-feature)
8. [Documentation, logging, testing, configuration, dev-ops](#documentation-logging-testing-configuration-dev-ops)
9. [Appendix: Requirements](#appendix-requirements)
    1. [Product Scope](#product-scope)
    2. [User Stories](#user-stories)
    3. [Use Cases](#use-cases)
    4. [Non-functional requirements](#non-functional-requirements)
    5. [Glossary](#glossary)
10. [Appendix: Instructions for manual testing](#appendix-instructions-for-manual-testing)
    1. [Launch and Shut Down](#launch-and-shutdown)
    2. [Adding Person](#adding-a-person)
    3. [Deleting Person](#deleting-a-person)
    4. [Editing Person](#editing-a-person)
    5. [Listing People](#listing-all-people)
    6. [Find a Person](#finding-a-person)
    7. [Filter People](#filtering-persons)
    8. [View a Person](#viewing-a-person)
    9. [Show Summary Details](#summary-of-application-statuses)
    10. [Saving Data](#saving-data)
11. [Appendix: Planned Enhancements](#appendix-planned-enhancements)
12. [Appendix: Effort](#appendix-effort)

---

## **TalentSG**

TalentSG is an easy-to-use recruitment management desktop application designed for HR professionals and recruiters working
in mid-sized companies to streamline and speed up the hiring process. With a combination of a Command Line Interface
(CLI) and Graphical User Interface (GUI), our app provides a user-friendly and efficient way manage candidate and
employee information in fast-paced environments.

---

## **Purpose of this guide**

The purpose of this guide is to give you an overarching insight into developing and maintaining TalentSG.

If you are a developer, this guide will give you an overview of the high-level architecture and design of TalentSG.
It also delves into the implementation details and design considerations of TalentSG's features, allowing you to
become well-versed in the functionality and structure the of the application.

To learn more about TalentSG's user experience considerations, take a look at the [requirements](#appendix-requirements)
section where we cover the product scope, user stories, as well as use cases.

If you are lost, please refer to the [set-up](#setting-up-getting-started) section of the guide.

---

## How to use this guide

Here are some notations used in this guide.

### Format

- `Command` is used to label commands and components.
- {Placeholder} are used to label placeholders.
- [Optional], square brackets are used to notate optional fields.
- :information_source: **Note** is used to provide additional information that you should know.

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* TalentSG is based on the existing AddressBook3 (AB3) project created by the [SE-EDU Initiative](https://se-education.org).
* Libraries used:
  * [JavaFX](https://se-education.org)
  * [JUnit5](https://se-education.org)

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

This section gives an overview of the different components of TalentSG and how they interact with one another.

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

The ***Architecture Diagram*** given below explains the high-level design of TalentSG and how the components work
together.

<img src="images/ArchitectureDiagram.png" width="280" />

**Main components of the architecture**

**`Main`** consists of two classes: [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)). It is in charge of the app launch and shut down.
* At app launch: it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down: it shuts down the other components and invokes cleanup methods where necessary.

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

This component is responsible for displaying the graphical elements of TalentSG's GUI to the user.

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of the following parts:

- `Help Window`
- `OverviewPanel`
  - `OverviewListCard`
- `ReportBugWindow`
- `ResultDisplay`
- `PersonListPanel`
  - `PersonCard`
- `CommandBox`

All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

This component is responsible for processing and executing user commands

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component

This component is responsible for storing and managing TalentSG’s data

**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="600" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="550" />

</div>


### Storage component

This component is responsible for saving and retrieving TalentSG's data

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

### Add Person Feature

#### **Command Feature**

`add n/NAME p/PHONE NUMBER e/EMAIL ADDRESS a/ADDRESS s/SKILLS st/STATUS ex/EXPERIENCE dr/DESIRED ROLE note/[NOTE] t/[TAG]`,
where note and tag are optional fields

#### **Command Feature Purpose**

The `add` command allows users to add a `Person` to the `AddressBook`.

#### Key Components
- `AddCommand`: Executes the addition operation based on the user's input.
- `AddCommandParser`: Parses user input to create an `AddCommand` object.
- `LogicManager`: Invokes the `AddCommand` to execute the addition operation.
- `ModelManager`: Implements the `Model` interface and contains the internal list of persons.
- `Person`: Represents a person in TalentSG, encapsulating their personal information.
- `AddressBookParser`: Creates an `AddCommand` object based on the user input.

### **Sequence of action**

To help you understand how the `add` command works, here is a list of steps illustrating what occurs when [`LogicManager#execute()` is invoked](#logic-component):

We will be using the user input `add n/John Doe p/98765432 e/johnd@example.com a/123 Main St s/Java,Python st/Active note/Great candidate ex/5 years in HR dr/Software Engineer` as an example.

1. The user inputs the command `add n/John Doe p/98765432 e/johnd@example.com a/123 Main St s/Java,Python st/Active note/Great candidate ex/5 years in HR dr/Software Engineer`, intending to add a person with the specified details.
2. The `AddCommandParser` interprets the input.
3. An `AddCommand` object is created.
4. The `LogicManager` invokes the execute method of AddCommand.
5. The execute method of `AddCommand` invokes the `addPerson` method in `Model` property to create new contact with the new `Person` object.
6. The execute method of `AddCommand` returns a `CommandResult` object which stores the data regarding the completion of the `AddCommand`.
7. The UI reflects this new list with added `Person`.

<div markdown="span" class="alert alert-info">:information_source: **Note**:

- For step 2, if the user does not have any arguments, the `AddCommand` object will NOT be created!
</div>

#### `AddCommand` Implementation Sequence Diagram
The sequence diagram below illustrates the above process of adding a person into TalentSG.

<img src="images/AddCommandSequenceDiagram.png" width="550" />

---

### Delete Person Feature

#### **Command Feature**

`delete INDEX`

#### **Command Feature Purpose**

The `delete` command allows users to delete a `Person` from the `AddressBook`.

#### Key Components
- `DeleteCommand`: Executes the deletion operation based on the user's input.
- `AddCommandParser`: Parses user input to create a `DeleteCommand` object.
- `LogicManager`: Invokes the `DeleteCommand` to execute the deletion operation.
- `ModelManager`: Implements the `Model` interface and contains the internal list of persons.
- `Person`: Represents a person in TalentSG, encapsulating their personal information.
- `AddressBookParser`: Creates an `DeleteCommand` object based on the user input.

### **Sequence of action**

To help you understand how the `delete` command works, here is a list of steps illustrating what occurs when [`LogicManager#execute()` is invoked](#logic-component):

We will be using the user input `delete 1` as an example.

1. The user inputs the command `delete 2`, intending to delete a person with index 2 in the contact list.
2. The `DeleteCommandParser` interprets the input.
3. A `DeleteCommand` object is created.
4. The `LogicManager` invokes the execute method of DeleteCommand.
5. The execute method of `DeleteCommand` invokes the `deletePerson` method in `Model` property to delete the contact of the `Person` object.
6. The execute method of `DeleteCommand` returns a `CommandResult` object which stores the data regarding the completion of the `DeleteCommand`.
7. The UI reflects this new list with deleted `Person`.

<div markdown="span" class="alert alert-info">:information_source: **Note**:

- At step 2, if input is detected as invalid, an error will be shown on the screen and the sequence of action is terminated.
</div>

#### `Delete Command` Implementation Sequence Diagram
The sequence diagram below illustrates the above process of deleting a person from TalentSG.

<img src="images/DeleteCommandSequenceDiagram.png" width="800" />

---

### Edit Person Feature

#### **Command Feature**

`edit INDEX n/[NAME] p/[PHONE NUMBER] e/[EMAIL ADDRESS] a/[ADDRESS] s/[SKILLS] st/[STATUS] ex/[EXPERIENCE] dr/[DESIRED ROLE] note/[NOTE] t/[TAG]`
where all the fields are optional.

#### **Command Feature Purpose**

The `edit` command allows users to edit a `Person` in the `AddressBook`.

#### Key Components
- `EditCommand`: Executes the edit operation based on the user's input.
- `EditCommandParser`: Parses user input to create an `EditCommand` object.
- `LogicManager`: Invokes the `EditCommand` to execute the edit operation.
- `ModelManager`: Implements the `Model` interface and contains the internal list of persons.
- `Person`: Represents a person in TalentSG, encapsulating their personal information.
- `AddressBookParser`: Creates an `EditCommand` object based on the user input.

### **Sequence of action**

To help you understand how the `delete` command works, here is a list of steps illustrating what occurs when [`LogicManager#execute()` is invoked](#logic-component):

We will be using the user input `edit 1 n/John Doe p/98765432 e/johnd@example.com a/123 Main St s/Java,Python st/Active note/Great candidate ex/5 years in HR dr/Software Engineer` as an example, whereby the original `Expense` object has a `EXPENSE_NAME` of `Milk`.

1. The user executes the command `edit 1 n/John Doe p/98765432 e/johnd@example.com a/123 Main St s/Java,Python st/Active note/Great candidate ex/5 years in HR dr/Software Engineer`, intending to edit the details of the person at index 1.
2. The `EditCommandParser` interprets the input.
3. An `EditCommand` object is created.
4. The `LogicManager` invokes the execute method of `EditCommand`.
5. The execute method of `EditCommand` invokes the `setPerson` method in the `Model` to update the details of the existing `Person` object with the new values.
6. The execute method of `EditCommand` returns a `CommandResult` object which stores the data regarding the completion of the `EditCommand`.
7. The UI reflects this updated list with the edited `Person`.

<div markdown="span" class="alert alert-info">:information_source: **Note**:

- At step 2, if the input is detected as invalid (either index is invalid or no arguments provided other than index), a matching error will be shown on the screen and the sequence of action is terminated.
- At step 3, if the user provides a category to edit to, and it is found that there is no such category in FastTrack, an error will be shown and the sequence of action is terminated.
</div>

#### `EditCommand` Implementation Sequence Diagram
The sequence diagram below illustrates the above process of editing a person's details in TalentSG.

<img src="images/EditCommandSequenceDiagram.png" width="800" />

---

### List Feature

#### **Command Feature**

`list`

#### **Command Feature Purpose**

The `list` command allows users to view all people in the `AddressBook`.

#### Key Components
- **`ListCommand`**: Executes the listing operation to show all persons in the address book.
- **`AddressBookParser`**: Parses user input to create a `ListCommand` object.
- **`LogicManager`**: Invokes the `ListCommand` to execute the list operation.
- **`ModelManager`**: Implements the `Model` interface and contains the internal list of persons.
- **`CommandResult`**: Encapsulates the result of the command execution, including any feedback to the user.

### **Sequence of action**

To help you understand how the `list` command works, here is a list of steps illustrating what occurs when [`LogicManager#execute()` is invoked](#logic-component):

We will be using the user input `list` as an example.

1. The user executes the command `list`, intending to list all persons in the address book.
2. The `AddressBookParser` interprets the input and creates a `ListCommand` object.
3. The `LogicManager` invokes the execute method of `ListCommand`.
4. The execute method of `ListCommand` calls `updateFilteredPersonList` in the `Model` to apply a filter to show all persons.
5. The execute method of `ListCommand` returns a `CommandResult` object, indicating the command was successful with the message "Listed all persons".
6. The UI reflects the updated list of persons.

<div markdown="span" class="alert alert-info">:information_source: **Note**:

- At step 2, if an invalid input is detected after `list` (e.g. `list xxxxxx`), an error will be shown and the sequence of action is terminated.
</div>

#### `ListCommand` Implementation Sequence Diagram
The sequence diagram below illustrates the above process of executing the `list` command in TalentSG, which lists all persons in the address book.

![ListCommandSequenceDiagram.png](images%2FListCommandSequenceDiagram.png)

---

### Find Person Feature

#### **Command Feature**

`find KEYWORD`

#### **Command Feature Purpose**

The `find` command allows users to find specific people in the `AddressBook` based on a keyword.

#### Key Components
- `FindCommand`: Executes the find operation based on the user's input.
- `FindCommandParser`: Parses user input to create an `FindCommand` object.
- `LogicManager`: Invokes the `FindCommand` to execute the find operation.
- `ModelManager`: Implements the `Model` interface and contains the internal list of persons.
- `Predicate`: Represents the keyword for finding persons whose name contains any of the argument keyword in TalentSG.
- `AddressBookParser`: Creates an `FindCommand` object based on the user input.

### **Sequence of action**

To help you understand how the `find` command works, here is a list of steps illustrating what occurs when [`LogicManager#execute()` is invoked](#logic-component):

We will be using the user input `find John` as an example.

1. The user executes the command `find john`, intending to find all persons whose name contains the keyword.
2. The `FindCommandParser` interprets the input.
3. An `FindCommand` object is created.
4. The `LogicManager` invokes the execute method of FindCommand.
5. The execute method of `FindCommand` invokes the `updateFilteredPersonList` method in `Model` property to update the filter of the filtered person list.
6. The execute method of `FindCommand` returns a `CommandResult` object which stores the data regarding the completion of the `FindCommand`.
7. The UI reflects this updated filtered `Person` list.

<div markdown="span" class="alert alert-info">:information_source: **Note**:

- At step 2, if an invalid input is detected after `list` (e.g. `list xxxxxx`), an error will be shown and the sequence of action is terminated.
</div>

#### `FindCommand` Implementation Sequence Diagram
The sequence diagram below illustrates the process of finding all persons based on keyword in TalentSG.

<img src="images/FindCommandSequenceDiagram.png" width="800" />

---

### Filter Status Feature

#### **Command Feature**

`filter STATUS`

<div markdown="span" class="alert alert-info">:information_source: **Note**:

- **Case-Insensitive**: The search is case-insensitive.
- **Available Statuses**:
    - Applied
    - Screening
    - Interview Scheduled
    - Interviewed
    - Offer
    - Onboarding
    - Hired
    - Rejected
</div>

#### **Command Feature Purpose**

The `filter` command allows users to filter people in the `AddressBook` based on their application status.

#### Key Components
- `FilterStatusCommand`: Executes the filter operation based on the user's input.
- `FilterStatusCommandParser`: Parses user input to create an `FilterStatusCommand` object.
- `LogicManager`: Invokes the `FilterStatusCommand` to execute the filter operation.
- `ModelManager`: Implements the `Model` interface and contains the internal list of persons.
- `Predicate`: Represents the status for finding persons whose status matches in TalentSG.
- `AddressBookParser`: Creates an `FilterStatusCommand` object based on the user input.

### **Sequence of action**

To help you understand how the `filter` command works, here is a list of steps illustrating what occurs when [`LogicManager#execute()` is invoked](#logic-component):

We will be using the user input `filter Interviewed` as an example.

1. The user executes the command `filter Interviewed`, intending to find all persons whose name contains the keyword.
2. The `FilterStatusCommandParser` interprets the input.
3. An `FilterStatusCommand` object is created.
4. The `LogicManager` invokes the execute method of FindCommand.
5. The execute method of `FilterStatusCommand` invokes the `updateFilteredPersonList` method in `Model` property to update the filter of the filtered person list.
6. The execute method of `FilterStatusCommand` returns a `CommandResult` object which stores the data regarding the completion of the `FilterStatusCommand`.
7. The UI reflects this updated filtered `Person` list.

<div markdown="span" class="alert alert-info">:information_source: **Note**:

- At step 2, if an invalid status is detected after `filter` (e.g. `filter Helloo`), an error will be shown and the sequence of action is terminated.
</div>

#### `FilterStatusCommand` Implementation Sequence Diagram
The sequence diagram below illustrates the process of finding all persons based on keyword in TalentSG.

<img src="images/FilterStatusCommandSequenceDiagram.png" width="800" />

---

### View Person Feature

#### **Command Feature**

`view INDEX`

#### **Command Feature Purpose**

The `view` command allows users to view a `Person` in the `AddressBook`.

#### Key Components
- `ViewCommand`: Executes the viewing operation based on the user's input.
- `ViewCommandParser`: Parses user input to create a `ViewCommand` object.
- `PersonDataReceiver`: Updates the overview panel with the correct `Person` data
- `Person`: Represents a person in TalentSG, encapsulating their personal information.
- `AddressBookParser`: Creates an `DeleteCommand` object based on the user input.

### **Sequence of action**

To help you understand how the `view` command works, here is a list of steps illustrating what occurs when [`LogicManager#execute()` is invoked](#logic-component):

We will be using the user input `delete 2` as an example.

1. The user inputs the command `view 2`, intending to view a person with index 2 in the contact list.
2. The `ViewCommandParser` interprets the input.
3. A `ViewCommand` object is created.
4. The execute method of `ViewCommand` causes the `MainWindow` to invoke the `receivePersonData` method in `PersonDataReceiver` property to view the contact of the `Person` object.
5. The execute method of `ViewCommand` returns a `CommandResult` object which stores the data regarding the completion of the `ViewCommand`.
6. The UI reflects the `OverviewPanel` with the selected `Person`.

<div markdown="span" class="alert alert-info">:information_source: **Note**:

- At step 2, if input is detected as invalid, an error will be shown on the screen and the sequence of action is terminated.
</div>

#### `ViewCommand` Implementation Sequence Diagram
The sequence diagram below illustrates the above process of deleting a person from TalentSG.

<img src="images/ViewCommandSequenceDiagram.png" width="800" />

---

### Summary Feature

#### **Command Feature**

`summary`

#### **Command Feature Purpose**

The `summary` command provides a breakdown of the application statuses of all the people in the `AddressBook`.

#### Key Components
- **`SummaryCommand`**: Executes the summarization operation to display the counts for each application status in the `AddressBook`.
- **`AddressBookParser`**: Parses user input to create a `SummaryCommand` object.
- **`LogicManager`**: Invokes the `SummaryCommand` to execute the summary operation.
- **`ModelManager`**: Implements the `Model` interface and contains the internal list of persons.
- **`CommandResult`**: Encapsulates the result of the command execution, including any feedback to the user.

### **Sequence of action**

To help you understand how the `summary` command works, here is a list of steps illustrating what occurs when [`LogicManager#execute()` is invoked](#logic-component):

We will be using the user input `summary` as an example:

1. The user executes the command `summary`, intending to view a breakdown of all application statuses for candidates in the `AddressBook`
2. The `AddressBookParser` interprets the input and creates a `SummaryCommand` object.
3. The `LogicManager` invokes the execute method of `SummaryCommand`.
4. The execute method of `SummaryCommand` initializes a map of all possible statuses with counts set to 0.
5. `SummaryCommand` collects the list of all candidates in the address book via `Model` and computes the count for each application status using a grouping and counting operation.
6. The execute method of `SummaryCommand` formats these counts into a summary message, displaying each status count and the total number of applicants.
7. The execute method of `SummaryCommand` returns a `CommandResult` object, encapsulating the summary message for display to the user.
8. The UI then reflects this summarized breakdown of application statuses.

<div markdown="span" class="alert alert-info">:information_source: **Note**:

- At step 2, if an invalid input is detected after `summary` (e.g. `summary abc`), an error will be shown and the sequence of action is terminated.
</div>

#### `SummaryCommand` Implementation Sequence Diagram
The sequence diagram below illustrates the above process of executing the `summary` command in TalentSG, which lists all persons in the address book.

![SummaryCommandSequenceDiagram.png](images%2FSummaryCommandSequenceDiagram.png)

---

### Help Feature

#### **Command Feature**

`help`

#### **Command Feature Purpose**

The `help` command provides a breakdown of the application statuses of all the people in the `AddressBook`.

#### Key Components
- **`HelpCommand`**: Executes the operation to display the help instructions.
- **`AddressBookParser`**: Parses user input to create a `HelpCommand` object.
- **`CommandResult`**: Opens the help window and encapsulates the result of the command execution

### **Sequence of action**

To help you understand how the `help` command works, here is a list of steps illustrating what occurs when [`LogicManager#execute()` is invoked](#logic-component):

We will be using the user input `help` as an example:

1. The user executes the command `help`, intending to view usage instructions for the `AddressBook`
2. The `AddressBookParser` interprets the input and creates a `HelpCommand` object.
3. The execute method of `HelpCommand` returns a `CommandResult` object, triggering the opening of the help window in the UI
4. The UI then reflects this summarized breakdown of application statuses.

The CommandResult object is then passed to the UI, which opens a panel displaying the help content.

<div markdown="span" class="alert alert-info">:information_source: **Note**:

- At step 2, if an invalid input is detected after `help` (e.g. `help a123`), an error will be shown and the sequence of action is terminated.
</div>

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

Our primary target users are Recruiters and HR professionals who are responsible for managing job candidates and employee information in mid-size companies.

**Value proposition**:

Simplify and enhance the recruitment process for HR professionals and recruiters.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​          | I want to …​                                                      | So that I can…​                                                             |
| ------ | ---------------- | ----------------------------------------------------------------- | --------------------------------------------------------------------------- |
| `* * *` | recruiter         | add new candidate profiles                                        | keep track of all candidates applying for positions                         |
| `* * *` | recruiter         | edit candidate profiles                                           | update candidate information as new details become available                |
| `* * *` | recruiter         | delete candidate profiles                                         | remove candidates who are no longer considered for positions                |
| `* * *` | HR professional   | view a list of all candidates                                     | easily access any candidate’s details on demand                             |
| `* * *` | recruiter         | search for candidates by specific criteria (e.g., skills)         | quickly find suitable candidates for various roles                          |
| `* *`  | recruiter         | track the status of a candidate through different recruitment stages | maintain an organised overview of the recruitment pipeline                  |
| `* *`  | HR professional   | import candidate data from external sources                       | streamline the process of adding new candidates                             |
| `* *`  | recruiter         | export data on candidates                                         | prepare reports or share data with colleagues                               |
| `* *`  | recruiter         | record notes during or after interviews                           | have detailed records and observations to refer back to                     |
| `* *`  | recruiter         | see a dashboard of recruitment activities                         | get a quick overview of all current recruitment efforts                     |
| `* *`  | HR professional   | manage and view employment details for hired candidates           | keep track of all employment-related information in one place               |
| `*`    | recruiter         | receive notifications about upcoming tasks                       | stay on top of all recruitment-related tasks without having to constantly check the app |
| `*`    | HR professional   | customise the fields in candidate profiles                        | tailor the application to fit the specific needs and focus areas of my organisation |
| `*`    | recruiter         | archive candidate profiles                                        | keep our current database up-to-date without losing past data               |
| `*`    | recruiter         | view analytics on recruitment efforts (e.g., time to hire)        | assess the effectiveness of current recruitment strategies                  |
| `*`    | HR professional   | undo/redo changes in the application                              | correct mistakes without needing to manually revert changes                 |
| `*`    | recruiter         | create and manage job postings                                    | advertise new job openings directly from the application                    |
| `*`    | recruiter         | receive automated suggestions for potential candidates            | speed up the process of candidate selection                                 |
| `*`    | recruiter         | categorise candidates into different job pools                    | organize candidates based on their skill sets and roles                     |
| `*`    | HR professional   | bulk upload candidate profiles via a CSV or Excel file            | quickly import a large number of candidate profiles                         |
| `*`    | recruiter         | assign tags/labels to candidates                                  | quickly identify candidates based on specific characteristics               |
| `*`    | HR professional   | generate candidate summary reports for hiring managers            | provide concise and relevant candidate data to stakeholders                 |
| `*`    | recruiter         | log communication history with candidates                         | track all interactions with candidates throughout the recruitment process   |
| `*`    | recruiter         | set priorities for candidates in the pipeline                     | focus on high-priority candidates first                                     |
| `*`    | recruiter         | track the reason for rejecting a candidate                        | maintain clear records of why candidates were not selected                  |
| `*`    | recruiter         | add links to candidates’ online profiles (e.g., LinkedIn, GitHub) | have quick access to additional candidate information                       |
| `*`    | HR professional   | integrate the app with job portals or LinkedIn                    | streamline candidate sourcing from multiple platforms                       |
| `*`    | recruiter         | send automated follow-up emails to candidates                     | save time by automating routine communication tasks                         |


### Use cases

#### Use Case 1 - Add a Person

**Actor**: Recruiter

**Preconditions**: TalentSG has been launched, and the recruiter is logged into the application.

**Main Success Scenario**:
1. Recruiter requests to add a new person to TalentSG
2. Recruiter inputs candidate details (name, phone, email, address, desired role, experience, skills, status) with the add command.
3. TalentSG confirms that the command is valid and adds the person.
4. TalentSG responds with a success message indicating that the person was added successfully

    Use case ends.

**Extensions**:

- 2a. Recruiter does not input all required person details.
    - 2a1. TalentSG responds with an error message telling the recruiter to input all the required fields and that the
      command is invalid
    - 2a2. Recruiter inputs add command with the required person details
    - 2a3. Steps 2a1-2a2 are repeated until the data entered is correct

      Use case resumes at step 3.

- 2b. Recruiter inputs invalid field.
    - 2b1. TalentSG responds with an error message telling the recruiter that one of the inputted fields is improperly
      formatted and that the command is invalid
    - 2b2. Recruiter inputs add command with the proper format for all fields
    - 2b3. Steps 2b1-2b2 are repeated until the data entered is correct

      Use case resumes at step 3.

- 2c. A duplicate candidate profile is detected.
    - 2c1. TalentSG responds with an error message telling the recruiter that the person has already been added and
      prompts the recruiter to input a new person
    - 2c2a. Recruiter decides to end the process
    
        Use case ends.

    - 2c2b. Recruiter inputs add command with new person details
    - 2c3. Steps 2c1-2c2 are repeated until the data entered is correct

      Use case resumes at step 3.


#### Use Case 2 - Delete a Person

**Actor**: Recruiter

**Preconditions**: TalentSG has been launched, and the recruiter is logged into the application.

**Main Success Scenario**:
1. Recruiter requests to delete a person from TalentSG.
2. Recruiter inputs the delete command with the index i of the person to be deleted.
3. TalentSG confirms that the command is valid and deletes the person.
4. TalentSG responds with a success message indicating that the person was deleted successfully

   Use case ends.

**Extensions**:

- 2a. Recruiter does not input index of person to be deleted.
    - 2a1. TalentSG responds with an error message telling the recruiter to input an index for the person to be deleted
      and that the command is invalid.
    - 2a2. Recruiter inputs deleted command with the index of the person to be deleted
    - 2a3. Steps 2a1-2a2 are repeated until the data entered is correct

      Use case resumes at step 3.

- 2b. Recruiter inputs invalid index (the index is out of bounds of the person list).
    - 2b1. TalentSG responds with an error message telling the recruiter that the inputted index is invalid because the
      person of that index doesn't exist and that the command is invalid
    - 2b2. Recruiter inputs delete command with a valid index
    - 2b3. Steps 2b1-2b2 are repeated until the data entered is correct

      Use case resumes at step 3.

#### Use Case 3 - Edit a Person

**Actor**: Recruiter

**Preconditions**: TalentSG has been launched, and the recruiter is logged into the application.

**Main Success Scenario**:
1. Recruiter requests to edit a person in TalentSG.
2. Recruiter inputs the edit command with the index i of the person to be edited along with the fields that the recruiter
   would like to edit.
3. TalentSG confirms that the command is valid and edits the specified fields of the person.
4. TalentSG responds with a success message indicating that the person was edited successfully

   Use case ends.

**Extensions**:

- 2a. Recruiter does not input index of person to be edited.
    - 2a1. TalentSG responds with an error message telling the recruiter to input an index for the person to be deleted
      and that the command is invalid.
    - 2a2. Recruiter inputs deleted command with the index of the person to be deleted
    - 2a3. Steps 2a1-2a2 are repeated until the data entered is correct

      Use case resumes at step 3.

- 2b. Recruiter inputs invalid index (the index is out of bounds of the person list).
    - 2b1. TalentSG responds with an error message telling the recruiter that the inputted index is invalid because the
      person of that index doesn't exist and that the command is invalid
    - 2b2. Recruiter inputs edit command with a valid index
    - 2b3. Steps 2b1-2b2 are repeated until the data entered is correct

      Use case resumes at step 3.

- 2c. Recruiter inputs invalid field(s).
    - 2c1. TalentSG responds with an error message telling the recruiter that one of the inputted fields is improperly
      formatted and that the command is invalid
    - 2c2. Recruiter inputs edit command with the proper format for all fields
    - 2c3. Steps 2c2-2c3 are repeated until the data entered is correct

      Use case resumes at step 3.

#### Use Case 4 - List all People

**Actor**: Recruiter

**Preconditions**: TalentSG has been launched, and the recruiter is logged into the application.

**Main Success Scenario**:
1. Recruiter requests to list all the people in TalentSG.
2. Recruiter inputs the list command
3. TalentSG confirms that the command is valid and lists all the people in the application
4. TalentSG responds with a success message indicating that list was displayed successfully.


#### Use Case 5 - Find a Person

**Actor**: Recruiter

**Preconditions**: TalentSG has been launched, and the recruiter is logged into the application.

**Main Success Scenario**:
1. Recruiter requests to find a person in TalentSG.
2. Recruiter inputs the find command with keywords that the application will use match the people.
3. TalentSG confirms that the command is valid and finds the person/people by matching the keywords with every person's
   name.
4. TalentSG responds with a success message indicating that the person/people were either found, or no one matched the keyword.

**Extensions**:
- 2a. Recruiter does not input a keyword.
    - 2a1. TalentSG responds with an error message telling the recruiter to input a keyword and that the command is invalid.
    - 2a2. Recruiter inputs find command with the required keyword.
    - 2a3. Steps 2a1-2a2 are repeated until the data entered is correct

      Use case resumes at step 3.


#### Use Case 6 - Filter people based on application status

**Actor**: Recruiter

**Preconditions**: TalentSG has been launched, and the recruiter is logged into the application.

**Main Success Scenario**:
1. Recruiter requests to filter the list of people in TalentSG by application status.
2. Recruiter inputs the filter command with one of the valid application statuses.
3. TalentSG confirms that the command is valid and filters the person list to display the people with the inputted application status.
4. TalentSG responds with a success message indicating that the person list was filtered successfully.

**Extensions**:
- 2a. Recruiter inputs an empty or invalid application status.
    - 2a1. TalentSG responds with an error message telling the recruiter to input a valid application status and that the command is invalid.
    - 2a2. Recruiter inputs filter command with a valid application status.
    - 2a3. Steps 2a1-2a2 are repeated until the data entered is correct

      Use case resumes at step 3.

#### Use Case 7 - View a Person

**Actor**: Recruiter

**Preconditions**: TalentSG has been launched, and the recruiter is logged into the application.

**Main Success Scenario**:
1. Recruiter requests to view a person in TalentSG.
2. Recruiter inputs the view command with the index i of the person to be viewed
3. TalentSG confirms that the command is valid and displays the specified fields of the person in the `Overview Panel`
4. TalentSG responds with a success message indicating that the person was viewed successfully

**Extensions**:
- 2a. Recruiter does not input index of person to be viewed.
    - 2a1. TalentSG responds with an error message telling the recruiter to input an index for the person to be viewed
      and that the command is invalid.
    - 2a2. Recruiter inputs view command with the index of the person to be viewed
    - 2a3. Steps 2a1-2a2 are repeated until the data entered is correct

      Use case resumes at step 3.

- 2b. Recruiter inputs invalid index (the index is out of bounds of the person list).
    - 2b1. TalentSG responds with an error message telling the recruiter that the inputted index is invalid because the
      person of that index doesn't exist and that the command is invalid
    - 2b2. Recruiter inputs view command with a valid index
    - 2b3. Steps 2b1-2b2 are repeated until the data entered is correct

      Use case resumes at step 3.


#### Use Case 8 - Get summary of application statuses of People

**Actor**: Recruiter

**Preconditions**: TalentSG has been launched, and the recruiter is logged into the application.

**Main Success Scenario**:
1. Recruiter requests to list all the people in TalentSG.
2. Recruiter inputs the list command.
3. TalentSG confirms that the command is valid and lists all the people in the application.
4. TalentSG responds with a success message indicating that list was displayed successfully.


#### Use Case 9 - Get help within TalentSG

**Actor**: Recruiter

**Preconditions**: TalentSG has been launched, and the recruiter is logged into the application.

**Main Success Scenario**:
1. Recruiter requests to get help regarding the acceptable commands in TalentSG.
2. Recruiter inputs the help command.
3. TalentSG confirms that the command is valid and displays the help pop-up.
4. TalentSG responds with a success message indicating that help pop-up was displayed successfully.


#### Use Case 10 - Exit TalentSG

**Actor**: Recruiter

**Preconditions**: TalentSG has been launched, and the recruiter is logged into the application.

**Main Success Scenario**:
1. Recruiter requests to exit TalentSG.
2. Recruiter clicks the exit button.
3. TalentSG confirms that the command is valid and exits the application


### Non-Functional Requirements

1. The application should work on any mainstream operating system (Windows, Linux, Unix, OS-X) as long as it has
Java `11` or above installed.

2. TalentSG should be able to support up to 1000 candidate profiles without any noticeable performance degradation.

3. TalentSG user interface should be intuitive for users with basic computer skills, providing clear navigation with minimal need for user training.

4. TalentSG should have a 99.9% uptime, ensuring availability for recruiters and HR professionals during business hours.

5. TalentSG must comply with GDPR regulations and ensure that candidate data is securely stored and handled.

6. The code should be well-documented and organized to ensure proper maintenance and debugging of code.

7. The system should comply with WCAG 2.1 standards for web accessibility, ensuring that users with disabilities can use the system effectively.


### Glossary

1. **Recruiter**:
- A professional responsible for managing job applicants and their application process.

2. **HR Professional**:
- A Human Resources professional responsible for managing employee information and candidate data during the recruitment process.

3. **Candidate Profile**:
- A record containing all relevant details about a job applicant, including contact information, skills, experience, and interview notes.

4. **Interview Schedule**:
- The process of setting a date and time for a job applicant to be interviewed by the recruiter or hiring manager.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   2. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Adding a Person

1. **Adding a person while all persons are being shown**

    1. **Prerequisites**: List all persons using the `list` command. Multiple persons in the list.

    2. **Test case**: `add n/John Doe p/12345678 e/johndoe@example.com a/123 Main St s/Java, C++ st/Active ex/2 dr/Software Engineer note/First contact`<br>
       **Expected**: New contact "John Doe" is added to the list. Details of the added contact shown in the status message. Timestamp in the status bar is updated.

    3. **Test case**: `add`<br>
       **Expected**: No person is added. Error details shown in the status message. Status bar remains the same.

    4. **Test case**: `add n/John Doe p/12345678`<br>
       **Expected**: No person is added. Error details about missing email and other required fields shown in the status message. Status bar remains the same.

    5. **Test case**: `add n/John Doe p/abcdefgh e/johndoe@example.com a/123 Main St s/Java, C++ st/Active ex/2 dr/Software Engineer note/Invalid phone`<br>
       **Expected**: No person is added. Error details about invalid phone number format shown in the status message. Status bar remains the same.

    6. **Test case**: `add n/Jane Doe p/87654321 e/janedoe@example.com a/456 Elm St s/Python, HTML st/Active ex/3 dr/Web Developer note/Second contact`<br>
       **Expected**: New contact "Jane Doe" is added to the list. Details of the added contact shown in the status message. Timestamp in the status bar is updated.

### Deleting a person

1. Deleting a person while all persons are being shown

    1. **Prerequisites**: List all persons using the `list` command. Multiple persons in the list.

    2. **Test case**: `delete 1`<br>
       **Expected**: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

    3. **Test case**: `delete 0`<br>
       **Expected**: No person is deleted. Error details shown in the status message. Status bar remains the same.

    4. **Other incorrect delete commands to try**: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       **Expected**: Similar to previous.

### Editing a Person

1. **Editing a person while all persons are being shown**

    1. **Prerequisites**: List all persons using the `list` command. Multiple persons in the list.

    2. **Test case**: `edit 1 n/John Smith p/12345678 e/johnsmith@example.com a/789 Maple Ave s/C#, SQL st/Active ex/4 dr/Project Manager note/Updated contact`<br>
       **Expected**: The first contact is updated to "John Smith". Details of the updated contact shown in the status message. Timestamp in the status bar is updated.

    3. **Test case**: `edit 0 n/John Smith p/12345678 e/johnsmith@example.com a/789 Maple Ave s/C#, SQL st/Active ex/4 dr/Project Manager note/Invalid index`<br>
       **Expected**: No person is edited. Error details shown in the status message. Status bar remains the same.

    4. **Test case**: `edit 1`<br>
       **Expected**: No person is edited. Error details about missing new details shown in the status message. Status bar remains the same.

    5. **Test case**: `edit 1 n/John Smith p/abcdefgh e/johnsmith@example.com a/789 Maple Ave s/C#, SQL st/Active ex/4 dr/Project Manager note/Invalid phone`<br>
       **Expected**: No person is edited. Error details about invalid phone number format shown in the status message. Status bar remains the same.

    6. **Test case**: `edit 3 n/Jane Doe p/23456789 e/janedoe@example.com a/456 Elm St s/Python, HTML st/Active ex/3 dr/Web Developer note/Invalid index`<br>
       **Expected**: No person is edited. Error details shown in the status message. Status bar remains the same.

### Listing All People

1. **Listing all persons while the application is running**

    1. **Prerequisites**: Ensure there are multiple persons added to the address book using the `add` command.

    2. **Test case**: `list`<br>
       **Expected**: All persons in the address book are displayed in a numbered list format. The status message confirms the action, and the timestamp in the status bar is updated.

    3. **Test case**: `list` (when no persons are in the list)<br>
       **Expected**: A message indicates that the address book is empty. The status bar remains unchanged.

    4. **Test case**: `list` (after deleting a person)<br>
       **Expected**: All remaining persons are displayed in a numbered list format, reflecting the deletion. The status message confirms the action, and the timestamp in the status bar is updated.

    5. **Test case**: `list` (after editing a person)<br>
       **Expected**: All persons are displayed in a numbered list format, reflecting the changes made to the edited person. The status message confirms the action, and the timestamp in the status bar is updated.

    6. **Test case**: `list` (with special characters in names)<br>
       **Expected**: Persons with special characters in their names are displayed correctly without errors. The status message confirms the action, and the timestamp in the status bar is updated.

### Finding a Person

1. **Finding a person while all persons are being shown**

    1. **Prerequisites**: List all persons using the `list` command. Ensure there are multiple persons in the list.

    2. **Test case**: `find John`<br>
       **Expected**: Only persons matching the name "John" are displayed. The status message confirms the action, and the timestamp in the status bar is updated.

    3. **Test case**: `find` (no search criteria provided)<br>
       **Expected**: An error message is displayed indicating that no search criteria were provided. The status bar remains unchanged.

    4. **Test case**: `find aj;sdlkfjasdkas`<br>
       **Expected**: A message indicates that no matching persons were found. The status bar remains unchanged.

### Filtering Persons

1. **Filtering persons based on certain criteria**

    1. **Prerequisites**: Ensure multiple persons are added to the address book with varying statuses.

    2. **Test case**: `filter applied`<br>
       **Expected**: Only persons with the status "applied" are displayed. The status message confirms the action, and the timestamp in the status bar is updated.

    4. **Test case**: `filter` (no filter criteria provided)<br>
       **Expected**: An error message is displayed indicating that no filter criteria were provided. The status bar remains unchanged.

    5. **Test case**: `filter java`<br>
       **Expected**: An error message indicates that "java" isn't a valid status and prompts the user to input a valid status. The status bar remains unchanged.

### Viewing a Person

1. **Viewing details of a specific person**

    1. **Prerequisites**: List all persons using the `list` command. Ensure there are multiple persons in the list.

    2. **Test case**: `view 1`<br>
       **Expected**: Details of the first person are displayed. The status message confirms the action, and the timestamp in the status bar is updated.

    3. **Test case**: `view 0`<br>
       **Expected**: An error message is displayed indicating that the index is out of range. The status bar remains unchanged.

    4. **Test case**: `view x` (where x is larger than the list size)<br>
       **Expected**: An error message is displayed indicating that the index is out of range. The status bar remains unchanged.

    5. **Test case**: `view` (no index provided)<br>
       **Expected**: An error message is displayed indicating that no index was provided. The status bar remains unchanged.

### Summary of Application Statuses

1. **Displaying a summary of application statuses**

    1. **Prerequisites**: Ensure multiple persons with various statuses are added to the address book.

    2. **Test case**: `summary`<br>
       **Expected**: A summary of all application statuses is displayed, including counts for each status. The total number of applicants is shown, and the status message confirms the action, along with an updated timestamp in the status bar.

    3. **Test case**: `summary` (with no persons in the list)<br>
       **Expected**: A message indicates that there are no applicants to summarize. The status bar remains unchanged.


### Saving data

1. Dealing with missing/corrupted data files

   1. In the unfortunate event that your data files end up corrupted, TalentSG will wipe all data and allow you to start over
      from a clean slate.

---

## **Appendix: Planned Enhancements**

Team size: 5

We intend to accommodate interview scheduling in the future.

---

## **Appendix: Effort**

Working on TalentSG was both a challenging and rewarding experience for our entire team. As a few members in team had
prior experience working in properly-structured teams, we were able to effectively divide tasks and collaborate efficiently.
However, during the initial stages of our team project, we found it difficult to understand the structure of
AddressBook Level 3 (AB3) and navigate through the codebase. In turn, we spent a lot of time understanding the existing
application and helping each other when we needed it.

We faced a few challenges while working on TalentSG:

1. Becoming familiar with the existing AB3 codebase
2. How to integrate the view command such that it worked with both the Logic and UI components
3. Maintaining the abstraction principles when implementing new classes and methods
4. Quality code testing of code

Our group reused a lot of what AB3 had in place - we reused AB3's parsing, command handling, and storage structure for
the newly implemented commands (summary, view, filter) as we found it to be modular and organized.

Overall, our team put in a significant amount of effort to understand the existing codebase and implement the new features
to successfully transform AB3 into TalentSG. Our hard work paid off, and we are proud that we ended up with a solid,
user-friendly platform that meets our users needs.

---
