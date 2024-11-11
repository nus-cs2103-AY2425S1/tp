---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# HireMe Developer Guide

<!-- TOC -->
* [HireMe Developer Guide](#hireme-developer-guide)
  * [**Setting up, getting started**](#setting-up-getting-started)
  * [**Design**](#design)
    * [Architecture](#architecture)
    * [UI component](#ui-component)
    * [Logic component](#logic-component)
    * [Model component](#model-component)
    * [Storage component](#storage-component)
    * [Common classes](#common-classes)
  * [**Implementation**](#implementation)
    * [Getting help](#getting-help)
    * [Create new internship application](#create-new-internship-application)
    * [List all internship applications](#list-all-internship-applications)
    * [Delete an internship application](#delete-an-internship-application)
    * [Update the status of an internship application](#update-the-status-of-an-internship-application)
    * [Find internship applications](#find-internship-applications)
    * [Filter internship applications](#filter-internship-applications)
    * [Sort internship application list](#sort-internship-application-list)
    * [Clear](#clear)
    * [View chart](#view-chart)
    * [Close the application](#close-the-application)
  * [**Documentation, logging, testing, configuration, dev-ops**](#documentation-logging-testing-configuration-dev-ops)
  * [**Appendix: Requirements**](#appendix-requirements)
    * [Product scope](#product-scope)
    * [User stories](#user-stories)
    * [Use cases](#use-cases)
    * [Non-Functional Requirements](#non-functional-requirements)
    * [Glossary](#glossary)
  * [**Appendix: Instructions for manual testing**](#appendix-instructions-for-manual-testing)
    * [Launch](#launch)
    * [Help Window](#help-window)
    * [Adding an internship application](#adding-an-internship-application)
    * [Deleting an internship application](#deleting-an-internship-application)
    * [Sorting the list of internship applications](#sorting-the-list-of-internship-applications)
    * [Finding internship applications](#finding-internship-applications)
    * [Updating the status of an internship application](#updating-the-status-of-an-internship-application)
    * [Saving data](#saving-data)
    * [Exit HireMe](#exit-hireme-application)
<!-- TOC -->
<page-nav-print />

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

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `/delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

<br></br>

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `InternshipApplicationListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `InternshipApplication` object residing in the `Model`.

The `HelpWindow` component is shown when you execute a help command. It contains a link to the detailed user and developer guide on this HireMe documentation website.

The `ChartWindow` component is shown when you execute a chart command. It contains a visual representation of the various statuses of your internship applications, in the form of a pie chart.

<br></br>

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a internship application).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

<br></br>

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component:

- **Stores HireMe application data**, which includes all `InternshipApplication` objects. These objects are stored in a UniqueList, ensuring that each application is unique.
- **Manages a filtered list** of currently 'selected' `InternshipApplication` objects (e.g., search results) as a separate, _filtered_ list. This filtered list is exposed as an unmodifiable `ObservableList<InternshipApplication>`, allowing the UI to automatically reflect any changes in the data, as the list is observable.
- **Stores a `UserPrefs` object**, representing the user’s preferences. This object is exposed externally as a `ReadOnlyUserPrefs`, ensuring that the preferences can be accessed but not modified directly.
- **Is self-contained**, meaning that the `Model` does not depend on any other external components. As it represents the core domain entities, it maintains logical independence to ensure modularity and encapsulation.

<br></br>

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

<br></br>

### Common classes

Classes used by multiple components are in the `seedu.hireme.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Getting help
The implementation of the command follows the convention of a normal command, where `AddressBookParser` is responsible for parsing the user input string into an executable command.
In this case, since there are no additional parameters for the help command, `AddressBookParser` does not create any parser object.

<puml src="diagrams/HelpSequenceDiagram.puml" alt="HelpSequenceDiagram" />

`AddressBookParser` ensures that there are no additional keywords provided. If there are keywords found, `AddressBookParser` throws a ParseException.
Otherwise, it creates a new instance of `HelpCommand`.

Upon execution, `HelpCommand` returns an instance of `CommandResult` which contains the help message.

> **_NOTE:_** `Model` is not invoked here but included for the sake of clarity.

<br></br>

### Create new internship application
The implementation of the create command follows the convention of a normal command, where `AddressBookParser` is responsible for parsing the user input string into an executable command.
In this case, `AddressBookParser` creates `AddCommandParser` to parse user input string.

<puml src="diagrams/AddSequenceDiagram.puml" alt="AddSequenceDiagram" />

`AddressBookParser` first obtains the values corresponding to the prefixes `n/`, `r/`, `e/` and `d/`.
`AddressBookParser` ensures that:
- All values corresponding to the prefixes are valid
  If any of the above constraints are violated, `AddressBookParser` throws a ParseException. Otherwise, it creates a new instance of `AddCommand` that corresponds to the user input.
`AddCommand` comprises of the internship application to be added, which is an instance of `InternshipApplication`.

Upon execution, `AddCommand` first queries the supplied model if it contains a duplicate internship application. If no duplicate internship application exists, then `AddCommand` adds the internship application into the data.

> **_NOTE:_** HireMe identifies an entry as a duplicate if its `NAME`, `ROLE`, `EMAIL` and `DATE` match **(case-insensitive)** with those of an existing internship application entry. Attempting to add a duplicate will result in an error.

<br></br>

### List all internship applications
The implementation of the list command follows the convention of a normal command, where `AddressBookParser` is responsible for parsing the user input string into an executable command.

<puml src="diagrams/ListSequenceDiagram.puml" alt="ListSequenceDiagram" />

`AddressBookParser` creates `ListCommand`
Upon execution, `ListCommand` calls on `model::updateFilteredList` to show all internship applications.

<br></br>

### Delete an internship application

The implementation of the delete command follows the convention of a normal command, where `AddressBookParser` is responsible for parsing the user input string into an executable command.

In this case, `AddressBookParser` creates `DeleteCommandParser` to parse user input string.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="DeleteSequenceDiagram" />

`AddressBookParser` first obtains the index from the user's input.
`AddressBookParser` ensures that there is only 1 keyword found which is a number. If there is no valid keyword found, `AddressBookParser` throws a ParseException.
Otherwise, it creates a new instance of `DeleteCommand` that corresponds to the user input.
`DeleteCommand` comprises of a targetIndex which is the zero based index number of the internship application to be deleted.

Upon execution, `DeleteCommand` gets the internship application to be deleted and calls on `model::deleteItem` which deletes it from the list.
> **_NOTE:_** The sequence diagram shows a simplified execution of the DeleteCommand.

<br></br>

### Update the status of an internship application
The `StatusCommand` updates the status of an internship application to `PENDING`, `ACCEPTED`, or `REJECTED`, triggered by commands `/pending`, `/accept`, or `/reject` respectively. The implementation of the status command follows the convention of a normal command, where `AddressBookParser` is responsible for parsing the user input string into an executable command.

In this case, `AddressBookParser` creates `StatusCommandParser` to parse user input string.

![StatusSequenceDiagram](diagrams/StatusSequenceDiagram.puml)

The sequence diagram above illustrates the flow for the `/accept` command. Similar flows apply for `/reject` and `/pending`.

`AddressBookParser` first obtains the index from the user's input.  
`AddressBookParser` ensures that there is only one keyword found, which is a number. If no valid keyword is found, `AddressBookParser` throws a `ParseException`. Otherwise, it creates a new instance of `StatusCommand` based on the user input, with the `StatusCommand` containing the target index and specified status.

Upon execution, `StatusCommand` retrieves the internship application to be updated and calls `model::setItem` to update the status within the list.
<puml src="diagrams/StatusActivityDiagram.puml" alt="StatusActivityDiagram" />
> **_NOTE:_** The sequence diagram shows a simplified execution of the StatusCommand.

The activity diagram above outlines the detailed flow for the `StatusCommand`, showing the decision points and actions taken during the command execution.

<br></br>

### Find internship applications
The implementation of the find command follows the convention of a normal command, where `AddressBookParser` is responsible for parsing the user input string into an executable command.
In this case, `AddressBookParser` creates `FindCommandParser` to parse user input string.

<puml src="diagrams/FindSequenceDiagram.puml" alt="FindSequenceDiagram" />

`AddressBookParser` first obtains the keyword from the user's input.
`AddressBookParser` ensures that there is at least 1 keyword found. If there is no keyword found, `AddressBookParser` throws a ParseException.
Otherwise, it creates a new instance of `FindCommand` that corresponds to the user input.
  `FindCommand` comprises of a `NameContainsKeywordsPredicate`.

Upon execution, `FindCommand` calls on `model::updateFilteredList` which in turns calls on `filteredList::setPredicate`.
`setPredicate` updates the `filteredList` in `model` to contain all the internship applications that contain the keyword.

<br></br>

### Filter internship applications

The implementation of the filter command follows the convention of a normal command, where `AddressBookParser` is responsible for parsing the user input string into an executable command.
`AddressBookParser` creates `FilterCommandParser` to parse user input string.

<puml src="diagrams/FilterSequenceDiagram.puml" alt="FilterSequenceDiagram"/>

`AddressBookParser` first obtains the status from the user's input.
`AddressBookParser` ensures that the status is found. If there is no status found, `AddressBookParser` throws a ParseException.

Otherwise, it creates a new instance of `FilterCommand` that corresponds to the user input.
`FilterCommand` comprises of a `StatusPredicate`.

Upon execution, `FilterCommand` passes the instance of `StatusPredicate` to the model through the method `model::updateFilteredList`. The model then uses the predicate internally to update the displayed list of internship applications.

<br></br>

### Sort internship application list
The implementation of the command follows the convention of a normal command, where `AddressBookParser` is responsible for parsing the user input string into an executable command.
In this case, `AddressBookParser` creates `SortCommandParser` to parse user input string.

<puml src="diagrams/SortSequenceDiagram.puml" alt="SortSequenceDiagram" />

`AddressBookParser` first obtains the order from the user's input.
`AddressBookParser` ensures that there is only 1 keyword found which is the sorting order. If there is no valid keyword found, `AddressBookParser` throws a ParseException.
Otherwise, it creates a new instance of `SortCommand` that corresponds to the user input.
`SortCommand` comprises of a DateComparator which contains the sorting order, according to date of application, that the internship application list should be sorted by.

Upon execution, `SortCommand` calls on `model::sortFilteredList` which in turns calls on `addressBook::sortItems`.
`sortItems` updates the `filteredList` in `model` to sort the internship applications in the list according to the order specified by the user.

<br></br>

### Clear
The implementation of the command follows the convention of a normal command, where `AddressBookParser` is responsible for parsing the user input string into an executable command.

<puml src="diagrams/ClearSequenceDiagram.puml" alt="ClearSequenceDiagram" />

`AddressBookParser` creates `ClearCommand`. 
Upon execution, `ClearCommand` calls on `model::setAddressBook` with a new address book that has zero internship applications. Finally, `ClearCommand` generates a `CommandResult` with a confirmation message.
> **_NOTE:_** The sequence diagram shows a simplified execution of the ClearCommand.

<br></br>

### View chart
The implementation of the chart command follows the convention of a normal command, where `AddressBookParser` is responsible for parsing the user input string into an executable command.

<puml src="diagrams/ChartSequenceDiagram.puml" alt="ChartSequenceDiagram" />

`AddressBookParser` creates `ChartCommand`.
Upon execution, `ChartCommand` gets the chart data which is encapsulated in `CommandResult`

<br></br>

### Close the application
The implementation of the command follows the convention of a normal command, where `AddressBookParser` is responsible for parsing the user input string into an executable command.

<puml src="diagrams/ExitSequenceDiagram.puml" alt="ExitSequenceDiagram" />

`AddressBookParser` creates `ExitCommand`
Upon execution, `ExitCommand` encapsulates the intent to close the application in `CommandResult`.

> **_NOTE:_** `Model` is not invoked here but included for the sake of clarity.

<br></br>

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

* studies at School of Computing in NUS
* is constantly applying for internships
* has a need to keep track of significant number of internships
* can type fast
* prefers typing to mouse interaction
* is reasonably comfortable using CLI apps

**Value proposition**: manage internships faster than a typical mouse/GUI driven app

<br></br>

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (future plans) - `*`


| Priority | As a …​                       | I want to …​                                                                     | So that …​                                                               |
|----------|-------------------------------|----------------------------------------------------------------------------------|--------------------------------------------------------------------------|
| `* * *`  | CS Undergraduate              | list all the internship applications                                             | I can view all my past applications                                      |
| `* * *`  | Forgetful CS Undergraduate    | have a link to HireMe's help page                                                | I can see all the different commands that I can use                      |
| `* * *`  | An efficient CS Undergraduate | type the commands                                                                | I do not have to lift my fingers off the keyboard                        |
| `* * *`  | CS Undergraduate              | add an internship application                                                    | I can add on to the records of all the internships I have applied to     |
| `* * *`  | CS Undergraduate              | delete an internship application                                                 | I can remove invalid or irrelevant applications                          |
| `* * *`  | CS Undergraduate              | save the internship application data locally                                     | I will not lose my data when I exit the application                      |
| `* * *`  | CS Undergraduate              | load the internship from a saved file                                            | I can get back my data when I open the application                       |
| `* * *`  | CS Undergraduate              | clear the list of internship application I have saved                            | I can restart a new list in the next internship application cycle        |
| `* * *`  | CS Undergraduate              | find internship applications by company name                                     | I can quickly locate specific applications for review or updates         |
| `* * *`  | CS Undergraduate              | update the status of an internship application to accepted, pending, or rejected | I can update the status of each application accurately                   |
| `* *`    | Meticulous CS Undergraduate   | sort the list of internship applications by date of application                  | I can prioritize follow-ups with older applications                      |
| `* *`    | Curious CS Undergraduate      | see a chart that summarises the statuses of all my applications                  | I know know the breakdown of each status                                 |
| `*`      | Organised CS Undergraduate    | view the interview dates for different internships applications                  | I can update my schedule accordingly                                     |
| `*`      | Efficient CS Undergraduate    | view my most desired internship applications by favouriting them                 | I can prioritize my time on checking up on these internship applications |
| `*`      | Forgetful CS Undergraduate    | remind myself of acceptance deadline                                             | I will not miss the deadline to accept                                   |

*{More to be added}*

<br></br>

### Use cases

**System**: HireMe application

**Use Case: UC01 - Add a new internship entry**

**Actor**: User

**MSS (Main Success Scenario)**

1. The user requests to add a new internship entry.
2. HireMe creates a new entry.

   Use case ends.

**Extensions**

* 1a. The user has missing fields in input.
    * 1a1. HireMe shows an error message.

      Use case ends.

* 1b. The user provided some invalid input for field.
    * 1b1. HireMe shows an error message.

      Use case ends.
  
* 1c. The user provided multiple fields of the same type.
    * 1c1. HireMe shows an error message.

      Use case ends.

    

<br></br>
**System**: HireMe application

**Use Case: UC02 - List all internship entries**

**Actor**: User

**MSS (Main Success Scenario)**

1. The user requests to list all internship entries.
2. HireMe shows all internship entries.

   Use case ends.

**Extensions**

* 1a. There are no internship entries.
    * 1a1. HireMe shows an empty list.

      Use case ends.

<br></br>
**System**: HireMe application

**Use Case: UC03 - Delete an internship entry**

**Actor**: User

**MSS (Main Success Scenario)**

1. The user requests to delete a particular internship entry.
2. HireMe deletes the entry.

   Use case ends.

**Extensions**

* 1a. The given index is invalid.
    * 1a1. HireMe shows an error message.

      Use case ends.

<br></br>
**System**: HireMe application

**Use Case: UC04 - Sort all internship applications list**

**Actor**: User

**MSS (Main Success Scenario)**

1. The user requests to sort the internship applications list.
2. HireMe shows all the sorted list of internship applications.

   Use case ends.

**Extensions**

* 1a. User enters an invalid number of parameters.
    * 1a1. HireMe shows an error message 
  
    Use case ends.


* 1b. User enters an invalid order.
    * 1b1. HireMe shows an error message.
  
      Use case ends.


* 1c. User sorts an empty list.
    * 1c1. HireMe shows an empty list.

      Use case ends.
  
<br></br>
**System**: HireMe application

**Use Case: UC05 - Find internship applications by company name**

**Actor**: User

**MSS (Main Success Scenario)**

1. The user requests to find internship applications by entering a search pattern (e.g., `/find Goo`).
2. HireMe searches for internship applications with company names that contain words starting with the specified pattern.
3. HireMe displays a list of all matching internship applications.

   Use case ends.

**Extensions**

* 1a. The user provides an empty search pattern.
    * 1a1. HireMe displays an error message that explains how to use the find command and what parameters are valid.

      Use case ends.


* 1b. The provided search pattern matches no company names.
    * 1b1. HireMe shows a message indicating that no matching internship applications were found.

      Use case ends.

<br></br>
**System**: HireMe application

**Use Case: UC06 - Update the status of an internship application**

**Actor**: User

**MSS (Main Success Scenario)**

1. The user requests to change the status of an internship application by specifying an index and the desired status (e.g., `/accept 2`, `/reject 3`, `/pending 4`).
2. HireMe updates the status of the specified internship application to `ACCEPTED`, `REJECTED`, or `PENDING`.
3. HireMe displays a confirmation message indicating that the status has been successfully updated.

   Use case ends.

**Extensions**

* 1a. The user provides an invalid index (e.g., non-positive or non-integer value or integer out of range).
    * 1a1. HireMe displays an error message that explains how to use the status command and what parameters are valid.

      Use case ends.

<br></br>
**System**: HireMe application

**Use Case: UC07 - Load saved internship applications**

**Actor**: User

**MSS (Main Success Scenario)**

1. The user starts the application.
2. HireMe loads the previously saved internship applications.

   Use case ends.

**Extensions**

* 1a. No save file is found.
    * 1a1. HireMe creates a new empty save file.

      Use case ends.


* 1b. A file with an invalid format is found.
    * 1b1. HireMe shows an error message.

      Use case ends.

<br></br>
**System**: HireMe application

**Use Case: UC08 - Auto-save the current state of the internship list**

**Actor**: User

**MSS (Main Success Scenario)**

1. The user performs an action that changes the internship list (e.g., adding, editing, or deleting an entry).
2. The system automatically saves the updated internship list to `hireme.json`.
3. The file is saved successfully without displaying a confirmation message.

   Use case ends.

**Extensions**

* 1a. The file cannot be saved due to an error.
    * 1a1. The system shows the error message: "Error! Unable to save file."
    * 1a2. The system retries the auto-save after a short delay.
    * 1a3. If the save operation still fails, the system logs the error and informs the user that changes might not have been saved.

      Use case ends.

<br></br>
**System**: HireMe application

**Use Case: UC09 - List all internship entries**

**Actor**: User

**MSS (Main Success Scenario)**

1. The user requests to list all internship entries.
2. HireMe shows all internship entries.

   Use case ends.

**Extensions**

* 1a. The user provided extra parameter(s) in command format.
    * 1a1. HireMe shows an error message.

      Use case ends.


<br></br>
**System**: HireMe application

**Use Case: UC10 - Summarise all internship entries**

**Actor**: User

**MSS (Main Success Scenario)**

1. The user requests to view a summary of all internship entries.
2. HireMe shows a summary chart of all internship entries.

   Use case ends.

**Extensions**

* 1a. The user provided extra parameter(s) in command format.
    * 1a1. HireMe shows an error message.

      Use case ends.


<br></br>
**System**: HireMe application

**Use Case: UC11 - Filter internship entries by status**

**Actor**: User

**MSS (Main Success Scenario)**

1. The user provides a status to filter internship entries.
2. HireMe shows all internship entries with the given status.

   Use case ends.

**Extensions**
* 1a. The user provided an invalid status.
    * 1a1. HireMe shows an error message.

      Use case ends.


<br></br>
**System**: HireMe application

**Use Case: UC12 - Clear all internship entries**

**Actor**: User

**MSS (Main Success Scenario)**

1. The user requests to clear all internship entries.
2. HireMe clears all internship entries.

   Use case ends.

**Extensions**

* 1a. The user provided extra parameter(s) in command format.
    * 1a1. HireMe shows an error message.

      Use case ends.


<br></br>
**System**: HireMe application

**Use Case: UC13 - Exit HireMe application**

**Actor**: User

**MSS (Main Success Scenario)**

1. The user requests to exit the application.
2. HireMe application closes and auto-saves the file.

   Use case ends.

**Extensions**

* 1a. The user provided extra parameter(s) in command format.
    * 1a1. HireMe shows an error message.

      Use case ends.


<br></br>

### Non-Functional Requirements

1. **Performance**: The application should respond to user actions within **two seconds**.
2. **Scalability**: The application should handle **at least 500 internship applications** without any performance issues (e.g., lag or slowness).
3. **Cross-Platform Compatibility**: The application should run on any operating system that has **Java 17** installed.
4. **User Accessibility**: The system should be usable by a **novice** with no prior experience using a CLI application, without much difficulty.
5. **Project Scheduling**: The project should follow a **weekly delivery schedule**, releasing a set of features every week.
6. **Data Persistence**: The application should ensure that data **persists** after the user closes the application.
7. **Data Integrity**: Upon reopening the application, the **loaded data** should be identical to the **last saved state** and should not be volatile.

<br></br>

### Glossary

- **Application Status**:
    - **PENDING**: The internship application is currently in progress.
    - **REJECTED**: The user has rejected or been rejected from this internship application.
    - **ACCEPTED**: The user has accepted the offer for this internship.

- **Action**: The task carried out by the HireMe application such as Add, Delete, Update entries.

- **Command Line Interface (CLI)**: The user interacts with the computer by typing text commands instead of using a mouse to click on buttons or icons. As if giving instructions to execute a desired action.

- **Command**: The string the user types into the HireMe application’s command bar to carry out a particular action.

- **Command Bar**: The input bar at the top of the HireMe application which allows users to type in a string command.

- **Company Email**: The email of the company that the user is applying for an internship role at.

- **Company Name**: The name of the company that the user is applying for an internship role at.

- **Graphical User Interface (GUI)**: The user interacts with the computer using visual elements like buttons, icons and windows. 

- **Role**: The role of the internship the user applied for.

- **Index**: The index of the internship application displayed in the list.


--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

<br></br>

### Launch

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.
      
<br></br>

### Help Window
1. Opening Help window via Command Line

   1. Prerequisite: Help window is not open.

   2. Test case: `/help` <br>
   Expected: Help window opens.

1. Opening Help window via `F1`

    1. Prerequisite: Help window is not open.

    2. Test case: Click the `F1` key on your keyboard. <br>
       Expected: Help window opens.

1. Opening Help window via Tool Bar

    1. Prerequisite: Help window is not open.

    2. Test case: Click on the `Help` button on the Tool Bar, and then click on the `Help F1` button on the drop down.<br>
       Expected: Help window opens.

1. Minimising the Help window

   1. Prerequisite: Help window is not open.

   2. Test case: `/help` <br>
      Expected: Help window opens.

   3. Test case: Click on the minimise button of the Help window.<br>
   Expected: Help window minimises.

   4. Test case: `/help` after the Help window is minimised.<br>
   Expected: Help window does not pop open.

1. Closing the Help window

   1. Prerequisite: Help window is open.

   2. Test case: Click on the close button on the Help window. <br>
   Expected: Help window closes.

<br></br>

### Clearing all internship applications
1. Clear all internship applications
    1. Prerequisites: Ensure that the list is not empty. 

    2. Test case: `/clear`<br>
       Expected: A success message stating that HireMe has been cleared.
   
    3. Test case: `/list`<br>
       Expected: The list of internship application remains empty.

   4. Test case: `/clear x`<br>
       Expected: An error message stating the valid use of the `/clear` command.

<br></br>

### Adding an internship application
1. Adding a valid internship application

   1. Prerequisite: The exact internship application should not already be in the list.

   2. Test case: `/add n/Google r/Software Engineer Intern e/google@gmail.com d/31/10/24`<br>
   Expected: An internship application is successfully added, with the company name, company email, role and date of application being `Google`, `google@gmail.com`, `Software Engineer Intern`, `31/10/24`, respectively. The status of newly added internship application would be `Pending`.

2. Adding another valid internship application

   1. Prerequisite: The exact internship application should not already be in the list.

   2. Test case: `/add n/Yahoo r/Clerk e/yahoo@yahoo.com d/31/10/24`<br>
   Expected: An internship application is successfully added, with the company name, company email, role and date of application being `Yahoo`, `yahoo@yahoo.com`, `Clerk`, `31/10/24`, respectively. The status of newly added internship application would be `Pending`.

3. Adding duplicated internship application

   1. Prerequisite: The exact internship application should already be in the list.

   2. Test case: `/add n/Yahoo r/Clerk e/yahoo@yahoo.com d/31/10/24`<br>
   Expected: An error message stating that the internship application already exists in the list.

4. Adding internship application with invalid fields

   1. Missing/Invalid Company Name test case: `/add n/ r/Software Engineer Intern e/google@gmail.com d/31/10/24` <br>
   Expected: An error message stating what is considered a valid Company Name.<br>

      1. Other Invalid Company Names include: `<oding lab`, `|-|appy Days`, `@pple`.<br>

   2. Missing/Invalid Role test case: `/add n/Google r/ e/google@gmail.com d/31/10/24` <br>
   Expected: An error message stating what is considered a valid Role.<br>

      1. Other invalid Roles include: `Software_Engineer_Intern`, `Cl-erk`.<br>

   3. Missing/Invalid Email test case: `/add n/Google r/Software Engineer Intern e/ d/31/10/24`<br>
   Expected: An error message stating what is considered a valid Email.

      1. Other invalid Emails include: `@gmail.com`, `google.com`, `domainLabelTooShort@gmail.x`.<br>

   4. Missing/Invalid Date test case: `/add n/Google r/Software Engineer Intern e/google@gmail.com d/`<br>
   Expected: An error message stating what is considered a valid Date.
      1. Other invalid Dates include: Dates in the future (Relative to device's clock), `30/02/2024`, `31/04/2024`.<br>

5. Adding internship application with missing field(s)
   1. Test case: `/add n/Google r/Software Engineer Intern e/google@gmail.com`<br>
   Expected: An error message stating the valid use of the `/add` command.

<br></br>

### List
1. List all internship applications
    1. Prerequisites: Ensure that the list is not empty. 
   
    2. Test case: `/list`<br>
       Expected: The list should display all internship applications.

    3. Test case: `/list x`<br>
       Expected: An error message stating the valid use of the `/list` command.

<br></br>

### Deleting an internship application
1. Deleting an internship application while all internship applications are being shown

   1. Prerequisites: List all internship applications using the `/list` command. Multiple internship applications in the list.

   1. Test case: `/delete 1`<br>
      Expected: First application is deleted from the list. Details of the deleted application shown.

   1. Test case: `/delete 0`<br>
      Expected: No internship application is deleted. An error message indicating that the index is invalid.

   1. Other incorrect delete commands to try: `/delete`, `/delete x`, `...`, `/delete a` (where x is larger than the list size)<br>
      Expected: An error message should be shown which explains how to use the delete command and what parameters are valid.

<br></br>

### Sorting the list of internship applications

1. Sort the entire list of internship applications

    1. Prerequisites: List all internship applications using the `/list` command. Multiple (at least 2) internship applications in the list with different date of applications.

    1. Test case: `/sort earliest`<br>
       Expected: The list of internship applications should now be sorted in ascending order by the date of application. The earliest internship applications should be at the top
       of the list and as you go down the list, the date of applications should be at later dates.

    1. Test case: `/sort latest`<br>
       Expected: The list of internship applications should now be sorted in descending order by the date of application. The latest internship applications should be at the top
       of the list and as you go down the list, the date of applications should be at earlier dates.

    1. Other incorrect sort commands to try: `/sort`, `/sort test`, `/sort earliest latest`, `/sort 1`<br>
       Expected: An error message should be shown which explains how to use the sort command and what parameters are valid.

<br></br>

### Finding internship applications
1. Find using an exact match

    1. Prerequisites: The list should have internship applications added with company names such as "Google" and "Yahoo".
   
    2. Test case: `/find Google`<br>
       Expected: The application with the company name "Google" is displayed.

2. Find using a case-insensitive pattern

    1. Prerequisites: The list should have applications with company names like "Google" and "Yahoo".
   
    2. Test case: `/find google`<br>
       Expected: The application with the company name "Google" is displayed, showing that the search is case-insensitive.
   
    3. Test case: `/find YAHOO`<br>
       Expected: The application with the company name "Yahoo" is displayed.

3. Find with partial matches

    1. Prerequisites: The list should include applications with company names like "Google" and "Yahoo".
   
    2. Test case: `/find Goo`<br>
       Expected: The application with the company name "Google" is displayed.
   
    3. Test case: `/find Y`<br>
       Expected: The application with the company name "Yahoo" is displayed.

4. Find when no matches exist

    1. Prerequisites: The list contains only 2 applications, with company names "Google" and "Yahoo".
   
    2. Test case: `/find Microsoft`<br>
       Expected: A message stating that no matching internship applications were found is shown.

5. Find with an empty pattern

    1. Prerequisites: The application should be running.
   
    2. Test case: `/find`<br>
       Expected: An error message should be shown which explains how to use the find command and what parameters are valid.

<br></br>

### Updating the status of an internship application
1. Update status to `ACCEPTED`

    1. Prerequisites: Display all internship applications using the `/list` command. Ensure that applications for "Google" and "Yahoo" are present in the list.
   
    2. Test case: `/accept 1`<br>
       Expected: The status of the 1st application (e.g., "Google") is updated to `ACCEPTED`.
   
    3. Test case: `/accept 0`<br>
       Expected: An error message should be shown which explains how to use the status command and what parameters are valid.

2. Update status to `PENDING`

    1. Prerequisites: Display all internship applications using the `/list` command. Ensure that applications for "Google" and "Yahoo" are present in the list.
   
    2. Test case: `/pending 2`<br>
       Expected: The status of the 2nd application (e.g., "Yahoo") is updated to `PENDING`.

3. Update status to `REJECTED`

    1. Prerequisites: Display all internship applications using the `/list` command. Ensure that applications for "Google" and "Yahoo" are present in the list.
   
    2. Test case: `/reject 1`<br>
       Expected: The status of the 1st application (e.g., "Google") is updated to `REJECTED`.

<br></br>

### Chart Window
1. Open Chart window

    1. Prerequisites: List all internship applications using the `/list` command. Ensure that there is are at least two internship applications with different statuses and the Chart window is not opened.
   
    2. Test case `/chart`<br>
       Expected: Chart window opens.

2. Open Chart window with invalid command format

    1. Prerequisites: List all internship applications using the `/list` command. Ensure that there is are at least two internship applications with different statuses and the Chart window is not opened.
   
    2. Test case: `/chart x`<br>
       Expected: An error message stating the valid use of the `/chart` command.

3. Update Chart window by updating status

   1. Prerequisites: List all internship applications using the `/list` command. Ensure that there is are the internship application at index 1 is of `PENDING` status and the Chart window is already opened.
   
   2. Test case: `/accept 1`<br>
      Expected: Pie chart on Chart window to update accordingly.

4. Update Chart window by adding an internship application

   1. Prerequisites: List all internship applications using the `/list` command. Ensure that there is at least one internship application, `Google` is not in list, and the Chart window is already opened.
   
   2. Test case: `/add n/Google r/Software Engineer Intern e/google@gmail.com d/31/10/24`<br>
      Expected: Pie chart on Chart window to update accordingly.

5. Update Chart window by deleting an internship application

   1. Prerequisites: List all internship applications using the `/list` command. Ensure that there is at are at least two internship applications and the Chart window is already opened.
   
   2. Test case: `/delete 1`<br>
      Expected: Pie chart on Chart window to update accordingly.

6. Close Chart window

   1. Prerequisites: Chart window is already opened.
   
   2. Test case: Click on the close button on the Chart window. <br>
      Expected: Chart window closes.

<br></br>

### Filtering internship applications
1. Filter with a valid status in uppercase

   1. Prerequisites: List all internship applications using the `/list` command. Ensure that there is at least one internship application with `PENDING` status.
   
   2. Test case `/filter PENDING`<br>
      Expected: The list of internship applications should only display entries with `PENDING` status.

2. Filter with a valid status in mixed case

   1. Prerequisites: List all internship applications using the `/list` command. Ensure that there is at least one internship application with `PENDING` status.
       Expected: The list of internship applications remains empty.
    
   2. Test case `/filter Pending`<br>
      Expected: The list of internship applications should only display entries with `PENDING` status.

3. Filter an empty list

    1. Prerequisites: Clear all internship applications using the `/clear` command.

    2. Test case `/filter PENDING`<br>
       Expected: The list of internship applications remains empty.
       
4. Filter with an invalid status

   1. Prerequisites: List all internship applications using the `/list` command. Ensure that the list is not empty.
   
   2. Test case `/filter approved`<br>
      Expected: An error message indicating that the status is invalid.

5. Filter with an empty status

   1. Prerequisites: Clear all internship applications using the `/list` command. Ensure that the list is not empty.
   
   2. Test case `/filter`<br>
      Expected: An error message indicating that the status is invalid.

<br></br>

### Saving data
1. Auto saves file

   1. Prerequisites: List all internship applications using the `/list` command. Ensure that the list is not empty.

   2. Test case: `/exit` and open the HireMe application again<br>
      Expected: The list of internship applications previously saved are displayed.

<br></br>

### Exit HireMe Application
1. Exit via Window's close button

   1. Test case: Close the window by clicking on the Window's close button.<br>
   Expected: The window should close.

2. Exit via exit command

   1. Test case: `/exit` to close the window.<br>
   Expected: The window should close.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**
The team consists of 5 members.
Given below are enhancements planned for future versions. <br>

1. **Make 'Roles' and 'Company Names' less restrictive:** The current validator is too restrictive on what is allowed as `Role` and `Company Name`. 
Valid roles such as: C++ Developer, C# Developer, R&D Specialist are currently flagged as invalid by the validator. 
Similarly, valid company names such as: A*STAR, SK-II, Yahoo!, John's Bakery are also flagged as invalid by the validator. 
We plan to loosen the restrictions for roles and company names, to be more inclusive of the possible roles and company names in the real world. <br>

2. **Improve consistency in `find` feature:** Currently, while we prevent special characters in the `Company Name` (such as ~\`!@#),  
we did not prevent the same characters from being used as keywords for the `find` feature. This leads to an inconsistent user experience, 
since these characters would never be found in company names. We plan to be more consistent, 
and check whether the keywords provided to the `find` command are valid characters that are allowed in `Company Name`. <br>

3. **Make error message for `add` command more specific:** Currently, the error message provided when the user inputs an invalid `add` command is too generic. 
For example, `/add n/Google r/SWE d/01/01/24` will provide an error message stating `Invalid command format!`. It does not provide additional information to the user, 
on why the command is invalid. It could be improved to state the email field is missing. 
We plan to improve the validator to be able to detect specifically why the command is invalid, and provide a more specific error message. <br>

4. **Improve UI to deal with long texts:** The current application does not allow the user to scroll the list displayed on the application. 
If there is a very long text, the text will be cut off and the use would have to maximise the application's window in order to see the full text. 
We plan to implement scroll bars within the list displayed in the application, to allow the user to scroll and see any long texts. <br>

5. **Improve the validator for `email`:** The current email validator flags valid emails as invalid, such as `faceb__k@fb.com.sg`. This could cause some inconvenience to the users. 
We plan to fix the validator for email to allow for more valid emails. <br>

