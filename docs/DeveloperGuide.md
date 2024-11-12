---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# GamerBook Pro Max Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

This project is an extension of [_AddressBook-Level3 by se-edu_](https://se-education.org/addressbook-level3/).

Libraries used include: [_JavaFX_](https://openjfx.io/), [_Jackson_](https://github.com/FasterXML/jackson), [_JUnit5_](https://github.com/junit-team/junit5)

Graphical Interface (GUI) Testing was adapted from [_AddressBook-Level4 by se-edu_](https://se-education.org/addressbook-level4/)


--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The **Architecture Diagram** above presents a high-level view of the GamerBook app’s design.  

Below is a brief overview of the main components and their interactions.

**Main Components of the Architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes and connects all other components in the correct order.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The core functionality is managed by the following four components:

* [**`UI`**](#ui-component): Manages the app’s user interface.
* [**`Logic`**](#logic-component): Processes commands.
* [**`Model`**](#model-component): Holds app data in memory.
* [**`Storage`**](#storage-component): Handles data persistence (read/write) on the disk.

[**`Commons`**](#common-classes) section includes helper classes shared across multiple components.

**Interaction between Components**

The *Sequence Diagram* below shows the interaction flow for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a `{Component Name}Manager` which implements the corresponding API `interface`.

The class diagram below shows a partial view of these component relationships.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give detailed descriptions of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of `MainWindow` and its parts like `CommandBox`, `ResultDisplay`, `PersonListPanel`, and `StatusBarFooter`. All these, including `MainWindow`, inherit from the abstract `UiPart` class, capturing common traits among the visible GUI classes.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* relies on the `Logic` component to execute commands and keeps a reference to the `Logic` component.
* depends on `Model` classes (e.g., `Person`) to display data in the UI.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below details the interactions within the `Logic` component for the `delete 1` command.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

#### Workflow in the Logic Component

1. **Execution Request**: `Logic` receives a command to execute.
2. **Parsing**: The command is parsed by `AddressBookParser`, which generates an appropriate command object (e.g., `DeleteCommand`).
3. **Execution**: The command object interacts with the `Model` to execute (e.g., deleting a person).
4. **Result**: A `CommandResult` object encapsulates the execution outcome, which is returned by `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

#### Parsing Flow

* **AddressBookParser**: This component identifies the appropriate command parser based on the user input (e.g., `AddCommandParser`, `DeleteCommandParser`).
* **Command Parsers**: Each `XYZCommandParser` (e.g., `AddCommandParser`) implements the `Parser` interface, ensuring a uniform approach for handling and testing commands.
* **Command Processing**: The parser processes the user's command, generating a corresponding `Command` object (e.g., `AddCommand`). This object is then passed back to `Logic` by the `AddressBookParser`.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores data (`Person` objects) in a `UniquePersonList`.
* manages a filtered list of currently 'selected' `Person` objects, observable by the UI.
* stores a `UserPref` object that represents the user’s preferences, exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Alternative Model:** A more OOP design could use a `Tag` list in `AddressBook`, where each `Person` references a single `Tag`, minimizing redundancy.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* saves and reads both address book data and user preference data in JSON format
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Implemented Undo feature

Each `Command` has its `undo()` method that undoes what the command executed.
Each command stores the necessary information to undo the execution.
All successful commands done by the user is stored in a `CommandLog`.

When `undo` is executed, the latest command in `CommandLog` is retrieved and that command's `undo()` method is called.

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagramNew-Logic.puml" alt="UndoSequenceDiagramNew-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` and `Command` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>


#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1:** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2 (current choice):** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

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

* has a need to manage a large number of gaming contacts
* prefer desktop apps over web apps or mobile apps
* is proficient in typing and prefers keyboard inputs over mouse interactions
* feels comfortable using CLI applications

**Value proposition**: 

* manage contacts more quickly than a typical mouse/GUI driven app
* enable gamers to track the games played by friends and find people to game with more easily


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​  | I want to …​                          | So that I can…​                                                              |
|----------|----------|---------------------------------------|------------------------------------------------------------------------------|
| `* * *`  | new user | see usage instructions                | refer to instructions when I forget how to use the App                       |
| `* * *`  | gamer    | add a new gaming contact              | keep track of my gaming friends and their details.                           |
| `* * *`  | gamer    | delete a gaming contact               | keep my address book up-to-date by removing inactive or irrelevant contacts. |
| `* * *`  | gamer    | view my contact list                  | see who I have contact with                                                  |
| `* * *`  | gamer    | tag my contacts with specific games   | easily find friends who play specific games                                  |
| `* * *`  | gamer    | find a contact by name                | locate details of persons without having to go through the entire list       |
| `* *`    | gamer    | find a contact by game                | quickly find contacts who play a specific game                               |
| `* *`    | gamer    | tag my contacts with different groups | manage my contacts into teams                                                |
| `* *`    | gamer    | tag contacts as inactive              | hide inactive contacts without deleting their  information                   |
| `* *`    | gamer    | add links to contacts                 | quickly find a contact's socials and other links                             |
| `*`      | gamer    | aff custom fields to each contact     | add various details to each contact, such as timezone, skill level, etc.     |

<!--
*{More to be added}*
-->

### Use cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

#### Use Case: UC1 - Add a Contact
**MSS**
1. User requests to add contact
2. GamerBook adds the contact
Use case ends.

**Extensions**
* 1a. User adds contact incorrectly.
  * 1a1. GamerBook notifies user that the command was entered incorrectly.
  
    Use case ends.
* 1b. User attempts to add a duplicate contact.
    * 1b1. GamerBook notifies the user that the contact already exists.
  
      Use case ends.

* 1c. User leaves mandatory fields empty.
    * 1c1. GamerBook prompts the user to fill in the required fields.

      Use case ends.

#### Use Case: UC2 - Delete a Contact
**MSS**
1. User list the contacts.
2. User requests to delete a contact.
3. GamerBook deletes the contact.
Use case ends.

**Extensions**
* 2a. User attempts to delete a contact that does not exist.
    * 2a1. GamerBook notifies the user that the contact does not exist.

      Use case ends.
* 2b. User cancels the delete operation.
    * 2b1. GamerBook returns to the contact list without making any changes.

      Use case ends.

#### Use Case: UC3 - Find a Contact by Name
**MSS**
1. User finds a contact by name.
2. GamerBook shows the corresponding contact.
Use case ends.

**Extensions**
* 1a.  User enters a name that does not exist.
    * 1a1. GamerBook notifies the user that no contacts were found.

      Use case ends.
* 1b. User types a partial name.
    * 1b1. GamerBook returns all contacts that match the partial name.

      Use case ends.

#### Use Case: UC4 - Tag a Contact
**MSS**
1. User request to tag a contact.
2. GamerBook tags the contact.
Use case ends.

**Extensions**
* 1a. User attempts to tag a contact that does not exist.
    * 1a1. GamerBook notifies the user that the contact cannot be found.

      Use case ends.
* 1b. User attempts to tag a contact with an invalid game name.
    * 1b1. GamerBook shows an error message indicating the game name is invalid.

      Use case ends.

#### Use Case: UC5 - List All Contacts
**MSS**
1. User requests to list all contacts.
2. GamerBook displays all contacts in the system.
   Use case ends.

**Extensions**
* 1a. User attempts to list contacts when none exist.
    * 1a1. GamerBook notifies the user that no contacts are available.

      Use case ends.

#### Use Case: UC6 - Edit a Game Details
**MSS**
1. User requests to edit a game's details (username, rank, skill level).
2. GamerBook updates the game's details accordingly.
   Use case ends.

**Extensions**
* 1a. User attempts to edit a game that does not exist.
    * 1a1. GamerBook notifies the user that the game could not be found.

      Use case ends.

#### Use Case: UC7 - Find Contacts by Game
**MSS**
1. User requests to find contacts associated with a specific game.
2. GamerBook shows a list of contacts that match the specified game.
   Use case ends.

**Extensions**
* 1a. User enters a game that does not exist in GamerBook.
    * 1a1. GamerBook notifies the user that no contacts were found for the specified game.

      Use case ends.
* 1b. User enters a partial game name.
    * 1b1. GamerBook returns all contacts that match the partial game name.

      Use case ends.
* 1c. User enters an invalid or misspelled game name.
    * 1c1. GamerBook notifies the user that no contacts were found for the specified game.

      Use case ends.

#### Use Case: UC8 - Find Contacts by Time
**MSS**
1. User requests to find contacts that play games in a specific time range.
2. GamerBook shows a list of contacts that play games in the specified time.
   Use case ends.

**Extensions**
* 1a. User enters an invalid or wrongly-formatted time.
    * 1a1. GamerBook notifies user the correct input format.

      Use case ends.
  
<!-- 
TEMPLATE FOR ADDING USE CASE

#### Use Case: 
**MSS**
1. 
2. 
Use case ends.

**Extensions**
* 1a. 
  * 1a1. 
  
    Use case ends.
* 1b. 
    * 1b1. 
  
      Use case ends.

*{More to be added}*
-->

### Non-Functional Requirements
#### Compatibility
Should work on any _mainstream OS_ as long as it has Java `17` or above installed.

#### Performance
Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
   (eg. addition, deletion, and search operations)

#### Usability
The application should provide clear and concise error messages and usage instructions to assist the user in operating the system effectively.

#### Efficiency
A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.

#### Security
The application should ensure that user data is securely stored and that sensitive information (like social media links) is not exposed unintentionally.

#### Data Integrity
The application should implement mechanisms to prevent data corruption and ensure that contacts are not lost during operations.

#### Scalability
The system should be designed to accommodate future enhancements, such as additional fields for contacts or new functionalities without requiring significant rework.

#### Accessibility
The user interface should be intuitive, user-friendly, and support accessibility features to cater to users with diverse needs and abilities.
<!--
*{More to be added}*
-->

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   2. Run `java -jar gamerbook.jar` in the Terminal in the _home folder_u Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

3. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   2. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   3. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

2. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

2. _{ more test cases …​ }_

------------------------------------------------------------

## **Appendix: Planned Enhancement**

### Preferred Time: Make Overnight Ranges Functional

Preferred Time currently doesn't accept overnight range, e.g. "2300-0100" is currently not accepted.
So users cannot add, edit overnight ranges to contacts. Using `findtime` command with overnight ranges
also triggers "invalid command..." error.

We plan to adjust the requirement so that overnight ranges will be accepted, which offers users more convenience
as many gamers play late night to early hours in the morning. People usually don't have time during the day to game.

Current workaround with the issue:    
For range input `2300-0100`, users can break down to `2300-2359` and `0000-0100`. This shall work as valid inputs and `add`, 
`edit`, and `findtime` should be able to work properly with this.

### FindTime Command: Adjustment on Boundary Overlapping

FindTime Command `findtime` is currently border sensitive.   
e.g.:   
`findtime 2200-2300` will return you all the person cards whose preferred times overlap with `2200-2300`,
with range `2100-2200` and `2300-2330` counted as overlapping. Which should not be the case, as there is 
only overlap at the boundary.

We plan to adjust the overlapping mechanism so that ranges meet only at the boundary will not be counted as overlapping.

