---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# SocialBook Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

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

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1,2")` API call as an example.

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
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Undo feature


The undo mechanism is facilitated by `Command` and `LogicManager`. `LogicManager` stores previously executed commands using a `CommandHistory` object. `Command` has the following operation:

* `Command#undo()` — To be overridden by commands that we determine to be action commands.

Given below is an example usage scenario and how the undo mechanism behaves at each step.

Step 1. The user launches the application for the first time.

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command gets stored in `CommandHistory` object in `LogicManager`.

<box type="info" seamless>

**Note:** If a command fails its execution, it will not be added to the `CommandHistory` object in `LogicManager` to be saved as a past command.

</box>

Step 3. The user now decides that deleting the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will extract the latest command excluding `undo` and call the `undo()` of the latest command.

<box type="info" seamless>

**Note:** If there is no past command to undo, an error message will be thrown to the user that this is the case rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

</box>

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

### Data archiving

The data archiving feature allows users to mark contacts as archived rather than permanently deleting them.
Archiving can be used to manage inactive or unneeded contacts without losing historical information or
requiring deletion, which is irreversible after the app has been exited or closed.

#### Implementation Details
The archiving feature is implemented using the `ArchiveCommand` class, which uses a boolean flag `shouldArchive`
to handle both archiving and unarchiving actions. Instead of directly modifying the `Person` object, which is immutable,
a new `Person` instance is created with the updated archive status.

<puml src="diagrams/ArchiveSequenceDiagram.puml" width="650" />
----------------------------------------------------------------------------------------------------------------------------------------------

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

* is a social worker tasked with helping low-income families
* has a need to manage a significant number of people
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: streamlines the process of social workers contacting and assisting people

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​       | I can …​                                              | So that I can…​                                                   |
|----------|---------------|-------------------------------------------------------|-------------------------------------------------------------------|
| `* * *`  | new user      | view the help manual                                  | familiarise myself with the functionalities of the app.           |
| `* * *`  | social worker | add information of different people                   | keep track of people requiring assistance in one place.           |
| `* * *`  | social worker | view the information of different people I have added | retrieve their information more quickly.                          |
| `* * *`  | social worker | delete the information of people                      | remove the data of people that I no longer need to keep track of. |
| `* * *`  | social worker | edit a person's information                           | keep their information up-to-date for future use.                 |
| `* *`    | social worker | tag a person                                          | manage and organize people according to their needs.              |
| `* *`    | social worker | filter through the list of people                     | navigate the persons list more efficiently.                       |
| `* *`    | social worker | sort people                                           | view people in a more suitable order.                             |
| `* *`    | social worker | compile important personal details                    | quickly disseminate information to these people.                  |
| `* *`    | social worker | archive people that no longer need assistance         | keep my contact list focused on active cases.                     |
| `* *`    | social worker | see the overall statistics for people                 | better determine the progress I have or need to make.             |
| `* *`    | social worker | schedule appointments I have with these people        | view all my appointments in one place.                            |
| `* *`    | social worker | determine which schemes a person is eligible for      | help this person apply for said schemes.                          |


### Use cases

(For all use cases below, the **System** is the `SocialBook` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - Add new person**

**MSS:**

1. User enters the command to add a person with their specified details.
2. SocialBook adds the person and displays the newly added person with the rest of the unarchived people.

    Use case ends.

**Extensions:**

* 1a. SocialBook detects missing or invalid input.

    * 1a1. SocialBook displays an error message that suggests what a correct input should look like.

    * 1a2. User corrects the input and enters the command again.
    
        Steps 1a1-1a2 are repeated until the user enters a correct input.

        Use case resumes from step 2.

* 1b. SocialBook detects a duplicate person entry.

    * 1b1. SocialBook displays an error message indicating such a person already exists.
  
    * 1b2. User corrects the input and enters the command again.

      Steps 1b1-1b2 are repeated until the user enters a correct input.
  
        Use case resumes from step 2.


**Use case: UC02 - View information of all people**

**MSS:**

1. User enters the command to view all people.
2. SocialBook displays all people currently stored and their information.

    Use case ends.


**Use case: UC03 - Delete information of different people**

**MSS:**

1. User indicates people they want to delete.
2. SocialBook removes these people’s details from the display.

    Use case ends.
	
**Extensions:**

* 1a. SocialBook detects missing or invalid input.

    * 1a1. SocialBook displays an error message that suggest what a correct input should look like.

    * 1a2. User corrects the input and enters the command again.

      Steps 1a1-1a2 are repeated until the user enters a correct input.

      Use case resumes from step 2.

* 1b. SocialBook detects person indicated by input does not exist. 
  
    * 1b1. SocialBook informs user that at least one such person indicated to be deleted does not exist in the list.
	    
    * 1b2. User corrects the input and enters the command again.

      Steps 1b1-1b2 are repeated until the user enters a correct input.

      Use case resumes from step 2.


**Use case: UC04 - Display help manual** 

**MSS:**

1. User keys in command to open help manual.
2. SocialBook displays help manual.
3. User closes help manual.
	
    Use case ends.

**Extensions:**

* 1a. User chooses more detailed manual for specific command.

    * 1a1. SocialBook displays detailed command instructions.

    Use case ends.

	
**Use case: UC05 - Edit existing information of a person**

**MSS:**

1. User indicates what to edit for the specified information fields and for whom.
2. SocialBook updates the fields for that person with user's input.

    Use case ends.

**Extensions:**

* 1a. SocialBook detects an error in the entered data.

    * 1a1. SocialBook should not update any fields.
  
    * 1a2. SocialBook displays errors encountered with respect to the field.
  
    * 1a3. User corrects the input and enters the command again.

      Steps 1a1-1a3 are repeated until the user enters a correct input.

      Use case resumes from step 2.


### Non-Functional Requirements

1.  Should work on most _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The system should be usable by a novice and does not require prior training.
5.  Data that is to be deleted from the system is removed completely and not stored elsewhere.
6.  Each command should take at most 10 seconds to executed.

### Glossary

* **API**: Application programming interfaces, which defines the standards and protocols that allow different software components to communicate with one another.
* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **CLI**: Command line interface
--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder.
   2. Change directory to the folder the jar file is in. 
   3. Run the jar file using `java -jar socialbook.jar`.  
       Expected: Shows the GUI with a set of sample contacts on the left side of the screen and a day calender on the right side of the screen. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. Shutting down app
   1. Type in `exit` as the input or click the x at the top of app.
        Expected: App stops and closes.

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list all/` command. Multiple persons in the list.

   1. Test case: `delete 1,2`<br>
      Expected: First and second contact is deleted from the list. Names of the deleted people shown in the display message.
   2. Test case: `delete 1,1,1,2`<br>
      Expected: First and second contact is deleted from the list. Names of the deleted people shown in the display message. 
   3. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details about invalid format shown in the display message.
   4. Test case: `delete 1,10000`<br>
      Expected: No person is deleted. Error details about invalid index shown in the display message.

### Archiving a person

1. Archiving a person while all persons are being shown

    1. Prerequisites: List persons using the `list all/` command. Ensure there are multiple persons in the list, including both archived and current (i.e. not archived) persons.

    2. Test case: `archive <index_of_current_person>`<br>
       Expected: The specified person is marked as archived in the list. His/her name is shown in the display message.
    3. Test case: `archive 0`<br>
       Expected: No person is archived. Error details about invalid format shown in the display message.
    4. Test case: `archive john`<br>
       Expected: No person is archived. Error details about invalid format shown in the display message.
    5. Test case: `archive`<br>
         Expected: No person is archived. Error details about invalid format shown in the display message.

2. Archiving an archived person

    1. Prerequisites: At least one person in the list is already archived.

    2. Test case: `archive <index_of_archived_person>`<br>
       Expected: No change in the list. Error message is displayed indicating that the person is currently archived.

### Unarchiving a person

1. Unarchiving a person while all persons are being shown

   1. Prerequisites: List all persons using the `list all/` command. Ensure there are multiple persons in the list, including both archived and current (i.e. not archived) persons.

   2. Test case: `unarchive <index_of_archived_person>`<br>
     Expected: The specified person is marked as not archived in the list. His/her name is shown in the display message.
   3. Test case: `unarchive 0`<br>
     Expected: No person is unarchived. Error details about invalid format shown in the display message.
   4. Test case: `unarchive john`<br>
     Expected: No person is unarchived. Error details about invalid format shown in the display message.
   5. Test case: `unarchive`<br>
     Expected: No person is unarchived. Error details about invalid format shown in the display message.

2. Unarchiving a current person

    1. Prerequisites: At least one person in the list is currently not archived.

    2. Test case: `unarchive <index_of_current_person>`<br>
       Expected: No change in the list. Error message is displayed indicating that the person is currently not archived.

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
