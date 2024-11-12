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

Image resource: star.png adapted from https://www.pngegg.com/en/png-wzqhl
git 
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

The `UI` consists of `MainWindow` and its parts like `CommandBox`, `ResultDisplay`, `PersonListPanel`, and `StatusBarFooter`. All these, including `MainWindow`, inherit from the abstract `UiPart` class, capturing common traits among the visible GUI classes.

The `CommandBox` integrates with the `commandpopup` component to provide auto-suggestion functionality. It monitors changes in the `AutoSuggestionTextField` and dynamically generates `CommandTextFlows` that are displayed to the user.

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
| `*`      | gamer    | add custom fields to each contact     | add various details to each contact, such as timezone, skill level, etc.     |


### Use cases

#### Use Case: UC1 - Add a Contact
**MSS**
1. User requests to add contact with corresponding details.
2. GamerBook adds the contact to the list of all contacts.  
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

-->

### Non-Functional Requirements

#### Compatibility
- The application should work on any _mainstream OS_ with Java `17` or above installed, ensuring that it is accessible across Windows, Linux, Unix, and macOS platforms.
- The application should avoid any OS-dependent libraries, ensuring full functionality on each supported OS without requiring additional setup or dependencies.
- The system should work across different screen sizes and resolutions without causing layout issues or compromising usability.

#### Performance
- The system should handle up to 100 persons without noticeable sluggishness in **typical usage** scenarios such as **addition**, **deletion**, and **search operations**.
- Response times should still remain within acceptable limits (under two seconds for most commands).
- Memory usage should be optimized to prevent excessive consumption and ensure smooth operation, especially on systems with limited resources.

#### Usability
- The application should provide clear, concise **error messages** and **usage instructions** to assist users in understanding and correcting mistakes.
- The **user interface (UI)** should be intuitive and should not require a steep learning curve, allowing new users to understand core functionalities with minimal guidance.
- The system should be optimized for users who prefer **keyboard shortcuts** and **commands** over mouse-based interactions, ensuring quick and efficient navigation.

#### Efficiency
- A user with above-average typing speed for regular English text (i.e., not code or system admin commands) should be able to complete most tasks faster using **commands** than the mouse.
- The application should have a minimal delay between user input and system response, promoting a seamless and responsive experience.

#### Security
- Access to the address book data should be restricted to each local device running the application, preventing others from reading or modifying the data without user consent.

#### Data Integrity
- Contacts and other critical data should be backed up regularly to prevent accidental loss.
- The system should ensure data consistency, even in cases of unexpected shutdowns or crashes, allowing data recovery or restoration.

#### Scalability
- The system should be designed to accommodate future enhancements without requiring significant rework, such as additional fields for contacts (e.g., birthday, favorite game) or new functionalities (e.g., contact grouping, tags).
- New commands and features should be easy to integrate, following a **modular structure** to allow for efficient code maintenance and expansion.

#### Accessibility
- The **user interface** should be intuitive, user-friendly, and support accessibility features such as keyboard navigation.
- The application should function effectively across a range of screen resolutions, from 1280x720 up to 4K, without causing usability or layout issues.
- The application should be compatible with standard screen readers and other assistive technologies to ensure usability for visually impaired users.

#### Maintainability
- The codebase should primarily follow the **Object-Oriented programming paradigm**, allowing for clear and organized code structure.
- A **modular structure** should be maintained, with components organized in a way that allows new features to be added without impacting existing functionality.
- Documentation should be thorough, including clear guidelines for developers to maintain or extend the application, and should cover typical maintenance tasks.

#### Reliability and Stability
- The application should not crash under normal operations and should handle errors gracefully, providing users with options to recover from issues without data loss.
- Backup and restore functionality should be available to ensure data recovery in the event of unexpected failures.

#### Quality
- The application should be user-friendly enough for novices while still providing advanced functionalities for experienced users.
- All features should undergo usability testing to confirm that they meet user expectations and support effective, efficient interactions.
- The design should prioritize clarity and simplicity to prevent user frustration or confusion.

-->

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Command**: A text-based instruction entered by the user to perform a specific action (e.g., adding, deleting, or searching for contacts).
* **User Interface (UI)**: The visual part of the application through which the user interacts, including menus, buttons, and input fields.
* **Error Message**: A message displayed to inform the user about an incorrect action or input, providing guidance on corrective steps.
* **Java `17`**: The required Java Development Kit (JDK) version to run the application, ensuring compatibility with essential libraries and dependencies.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for Manual Testing**

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

   2. Re-launch the app by running `java -jar gamerbook.jar` again.<br>
       Expected: The most recent window size and location is retained.


### Commands Testing

1. Refer to the [User Guide](UserGuide.md) for a list of commands and their expected inputs.

2. Test each command by trying a variety of inputs, including:
    1. Valid inputs as specified in the user guide to verify correct behavior.

    2. Edge cases or unusual inputs (e.g., long strings, special characters) to assess how the application handles unexpected data.

    3. Invalid inputs (e.g., incorrect syntax or missing arguments) to confirm that the application displays helpful error messages.

3. Document any unexpected behavior or issues encountered during testing, including steps to reproduce the issue if applicable.

------------------------------------------------------------

## **Appendix: Planned Enhancements**

Team size: 5 people  

### Preferred Time: Supporting Overnight Ranges

Currently, the **Preferred Time** feature does not support overnight ranges, such as "2300-0100". This limitation prevents users from adding or editing overnight gaming times for contacts, and using the `findtime` command with overnight ranges results in an "invalid command" error.

**Enhancement Plan**:  
We aim to modify the Preferred Time feature to accept overnight ranges, allowing users to specify late-night to early-morning availability conveniently. This change is particularly beneficial for gamers who prefer playing during nighttime hours when they have more free time.

**Current Workaround**:  
For users needing an overnight range (e.g., `2300-0100`), a workaround is to split the range into two separate intervals, such as `2300-2359` and `0000-0100`. These intervals are valid inputs and will allow the `add`, `edit`, and `findtime` commands to function as intended.

### FindTime Command: Boundary Overlap Adjustment

The `findtime` command currently considers ranges that touch at boundaries as overlapping, which leads to unintended results. For instance:

- Executing `findtime 2200-2300` currently returns all contacts whose preferred times overlap with `2200-2300`, including contacts with times `2100-2200` or `2300-2330`. This inclusion is inaccurate, as there is only a boundary overlap and not an actual time overlap.

**Enhancement Plan**:  
We plan to adjust the overlap detection mechanism in the `findtime` command so that ranges touching only at boundaries are not considered overlapping. This change will improve the accuracy of search results, ensuring that only times with actual overlap are included.

### Enhanced Error Messaging for FindTime Command

When users enter an incorrect, overnight time format in the `findtime` command, the application currently displays a generic "invalid format" message. This can be unclear for users, as it does not specify that the issue is specifically with the overnight time format.

**Enhancement Plan**:  
We plan to improve the error message for the `findtime` command to specify that the error is due to an overnight time. This will make the feedback more consistent with error messages shown in other commands.

**Benefit**:  
This adjustment will improve usability by providing more informative feedback, allowing users to quickly identify and correct the formatting issue, reducing potential frustration and increasing overall command clarity.

### Contact Card Title Overflow Handling

Currently, when a contact card contains a title or name with an excessively long string, it extends beyond the available width, which may lead to display issues and poor user experience, especially in narrow or non-optimal window sizes. This issue is unlikely to arise with typical names, but could occur with unusually long titles.

**Enhancement Plan**:  
To improve the display of contact cards, we plan to implement one of the following strategies:
- **Truncating long titles**: Set a fixed upper width limit for contact card titles. Any title exceeding this limit will be truncated and appended with "..." (ellipsis) to indicate there is more content.
- **Text wrapping**: Alternatively, we could apply CSS text wrapping to allow long titles to break into multiple lines, ensuring that the text is fully visible and does not extend beyond the card.

**Benefit**:  
This enhancement will prevent titles from overflowing and maintain a clean, consistent look for the contact cards. While the likelihood of encountering titles with such long names is low, this fix will improve usability and aesthetic appeal, especially for titles with long character names.

------------------------------------------------------------

## **Appendix: Efforts**

GamerBook Pro Max is a project built on the foundation of the original AddressBook, tailored specifically for gamers and their gaming friends. Our team of five embarked on this project to create an efficient and user-friendly application with advanced functionalities that address the unique needs of gamers.

### Difficulty Level

Adapting from the AddressBook to GamerBook Pro Max presented several challenges. While we retained core functionalities, we needed to significantly customize the app to better serve gamers. This included adding features like preferred game times, favorite games, and stateful command history, as well as implementing a powerful search feature.

The main difficulty lay in the number of advanced features we had to develop and integrate, as each new feature introduced additional complexity. As expected, more features resulted in more potential bugs, making debugging and testing a crucial part of the development process.

### Challenges Faced

1. **Forking Workflow**:  
   Using the Forking Workflow allowed us to work on multiple features in parallel, which was vital given the size of the project. However, it also introduced the overhead of managing forks and dealing with frequent merge conflicts, especially as we worked on overlapping components such as the command history and address book state features.

2. **Feature Complexity**:  
   Many of the new features, like saving and loading address book states or command suggestions, required a careful design to ensure compatibility and stability across the system. We also had to ensure that stateful features, such as undoing and redoing commands, were correctly implemented and tested.

3. **Testing and Regression**:  
   With the introduction of so many new commands and features, the effort required to write and maintain tests grew exponentially. Writing unit tests for every new feature, along with performing comprehensive regression testing to ensure that existing functionalities were not broken, added significant effort to the development cycle.

4. **Design Decisions**:  
   Making design decisions, such as implementing a robust time scheduling feature for gaming sessions, required balancing the user experience with technical constraints. We also had to consider scalability and future enhancements, which led to some complex decisions around data storage and retrieval.

### Effort Required

The effort required to develop GamerBook Pro Max was substantial due to the large number of features we implemented and the inevitable bugs that emerged with each new iteration. The effort to resolve issues and fine-tune the system was greater than if we had stuck to the original AddressBook functionality, but it was necessary to achieve the advanced feature set and quality of user experience we envisioned.
