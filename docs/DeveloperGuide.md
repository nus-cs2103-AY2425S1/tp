---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# CampusConnect Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* Our name, **CampusConnect**, was inspired by the NUS internship portal [**TalentConnect**](https://nus-csm.symplicity.com/).
* Our **CampusConnect** logo reuses the [**NUS logo**](https://nus.edu.sg/identity/guidelines/logo-colour-and-background)
* Our help window icon uses a cartoon representation of the [**NUS mascot on the NUS main reddit page**](https://www.reddit.com/r/nus/)

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture
<div style="text-align: center;">
    <puml src="diagrams/ArchitectureDiagram.puml" width="280"/>
</div>

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-F14a-4/tp/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-F14a-4/tp/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.

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
<div style="text-align: center;">
    <puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574"/>
</div>

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.
<div style="text-align: center;">
    <puml src="diagrams/ComponentManagers.puml" width="300"/>
</div>

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-F14a-4/tp/tree/master/src/main/java/seedu/address/ui/Ui.java)
<div style="text-align: center;">
    <puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>
</div>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-F14a-4/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-F14a-4/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-F14a-4/tp/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:
<div style="text-align: center;">
    <puml src="diagrams/LogicClassDiagram.puml" width="550"/>
</div>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<div style="text-align: center;">
    <puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />
</div>

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `CampusConnectParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:
<div style="text-align: center;">
    <puml src="diagrams/ParserClasses.puml" width="600"/>
</div>

How the parsing works:
* When called upon to parse a user command, the `CampusConnectParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `CampusConnectParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

Finally, the `Logic` contains the important `Command` classes. Some command classes from AB3 have been retained:
<div style="text-align: center;">
    <puml src="diagrams/CommandClassesOriginal.puml" width="600"/>
</div>

However, there are new classes implemented for CampusConnect as well:
<div style="text-align: center;">
    <puml src="diagrams/CommandClasses.puml" width="600"/>
</div>

The structure is simple:
* Each `Command` class (old and new) extends from the abstract `Command` class, which enforces the implementation of the `execute()` method.
* Each `Command` class contains the respective `COMMAND_WORD` representing the name of the command and a `MESSAGE_USAGE` string to demonstrate how to use the respective command.
* They also contain their own respective error messages.

### Model component

**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-F14a-4/tp/tree/master/src/main/java/seedu/address/model/Model.java)
<div style="text-align: center;">
    <puml src="diagrams/ModelClassDiagram.puml" width="450" />
</div>

The `Model` component,

* stores the CampusConnect data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `CampusConnect`, which `Person` references. This allows `CampusConnect` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>
<div style="text-align: center;">
    <puml src="diagrams/BetterModelClassDiagram.puml" width="450" />
</div>

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-F14a-4/tp/tree/master/src/main/java/seedu/address/storage/Storage.java)
<div style="text-align: center;">
    <puml src="diagrams/StorageClassDiagram.puml" width="550" />
</div>

The `Storage` component,
* can save both CampusConnect data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `CampusConnectStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedCampusConnect`. It extends `CampusConnect` with an undo/redo history, stored internally as an `history` and `future`. Additionally, it implements the following operations:

* `VersionedCampusConnect#saveCurrentData()` — Saves the current CampusConnect state in its future.
*  `VersionedCampusConnect#saveOldData()` — Saves the current CampusConnect state in its history.
* `VersionedCampusConnect#extractOldData()` — Restores the previous CampusConnect state from its history.
* `VersionedCampusConnect#extractUndoneData()` — Restores a previously undone CampusConnect state from its history.

These operations are exposed in the `Model` interface as `Model#saveCurrentCampusConnect()`, `Model#undoCampusConnect()` and `Model#redoCampusConnect()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedCampusConnect` will be initialized with two stacks.
<div style="text-align: center;">
    <puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />
</div>

Step 2. The user executes `delete 5` command to delete the 5th person in the CampusConnect. The `delete` command calls `Model#saveCurrentCampusConnect()`, causing the modified state of the CampusConnect after the `delete 5` command executes to be displayed and the old state of CampusConnect to be saved to the history.
<div style="text-align: center;">
    <puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />
</div>

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#saveCurrentCampusConnect()`, causing the modified state of the CampusConnect after the `delete 5` command executes to be displayed and the old state of CampusConnect to be saved to the history.
<div style="text-align: center;">
    <puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />
</div>

<box type="info" seamless>

**Note:** If a command fails its execution, it will call `Model#undoCampusConnect()`, so the CampusConnect state will not be saved into the `history`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoCampusConnect()`, which will save the current CampusConnect state into `future` and pop the latest saved CampusConnect state from the `history`.
<div style="text-align: center;">
    <puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />
</div>

<box type="info" seamless>

**Note:** If the `history` is empty, then there are no previous CampusConnect states to restore. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:
<div style="text-align: center;">
    <puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />
</div>

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:
<div style="text-align: center;">
    <puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />
</div>

The `redo` command does the opposite — it calls `Model#redoCampusConnect()`, which save current state into `history` and restores the CampusConnect to that state popped from the top of `future`.

<box type="info" seamless>

**Note:** If the `future` stack is empty, then there are no undone CampusConnect states to restore. The `redo` command uses `Model#canRedoCampusConnect()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the CampusConnect, such as `list`, will usually not call `Model#saveCurrentCampusConnect()`, `Model#undoCampusConnect()` or `Model#redoCampusConnect()`. Thus, the `history` and `future` remain unchanged.
<div style="text-align: center;">
    <puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />
</div>

Step 6. The user executes `clear`, which calls `Model#commitCampusConnect()`. All CampusConnectState in the future will be removed. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.
<div style="text-align: center;">
    <puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />
</div>

The following activity diagram summarizes what happens when a user executes a new command:
<div style="text-align: center;">
    <puml src="diagrams/CommitActivityDiagram.puml" width="250" />
</div>

#### Design considerations:

**Aspect: How undo & redo executes**

* **Alternative 1 (current choice):** Saves the entire CampusConnect.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Each command that changes the state stores the change that it has made.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: Difficult and tedious to implement.

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

**Target user profile**:  NUS undergraduate students
   
* has a need to manage a significant number of contacts  
* prefer desktop apps over other types   
* can type fast  
* prefers typing to mouse interactions  
* is reasonably comfortable using CLI apps  

**Value proposition**: 
* manage contacts faster than a typical mouse/GUI driven app  
* can connect people in the same modules/class/clubs/hobby, creating an active environment.  
* make it easier for users to look for contacts of profs and teaching staff.  


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                 | I want to …​                                                    | So that I can…​                                                                    |
|----------|-------------------------|-----------------------------------------------------------------|------------------------------------------------------------------------------------|
| `* * *`  | new user                | see usage instructions                                          | refer to instructions when I forget how to use the App                             |
| `* * *`  | user                    | add a new contact                                               | easily connect with them                                                           |
| `* * *`  | user                    | delete a contact                                                | remove entries that I no longer need                                               |
| `* * *`  | user                    | find a person by name                                           | locate details of persons without having to go through the entire list             |
| `* *`    | user                    | update my contacts information                                  | always keep an updated version of contact information                              |
| `* *`    | user                    | undo my last action                                             | prevent the accidental deletion of all my contacts                                 |
| `* *`    | user                    | redo my latest undone action                                    | prevent the accidental undoing of certain actions                                  |    
| `*`      | user with many contacts | search contacts by name                                         | locate a contact easily                                                            |
| `*`      | user                    | add a tag information to contacts                               | easily locate and connect with individuals such as classmates or club members      |
| `*`      | student                 | filter contacts by tags such as "group project" or "internship" | easily access related contacts                                                     |
| `*`      | user with many tags     | categorize tags into different groups                           | easily organize contacts and locate individuals such as classmates or club members |

### Use cases

(For all use cases below, the **System** is `CampusConnect` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - Add a person's contact**

**MSS**
1. User requests to add contact.
2. CampusConnect adds new contact to contact list.
3. CampusConnect displays success message.

   Use case ends.

**Extensions**
* 1a. Input format is invalid.
    * 1a1. CampusConnect shows error message.
    * 1b1. User enters input again.

      Steps 1a1-1a2 repeat until input format is valid.

      Use case ends.


* 1b. Another contact with the same name and contact number exists in the list.
    * 1b1. CampusConnect shows error message.
    * 1b2. User enters input again.

      Steps 1b1-1b2 repeat until input format is valid.

      Use case ends.


**Use case: UC02 - Delete a person's contact**

**MSS**
1. User requests to delete contact.
2. CampusConnect finds and deletes contact.
3. CampusConnect displays success message.

   Use case ends.

**Extensions**
* 1a. Input format is invalid.
    * 1a1. CampusConnect shows error message.
    * 1a2. User enters input again.

      Steps 1a1-1a2 repeat until input format is valid.

      Use case ends.


* 1b. Contact to delete does not exist.
    * 1b1. CampusConnect shows error message.

      Use case ends.

**Use case: UC03 - Find a person's contact**

**MSS**
1. User requests to find contact.
2. CampusConnect searches the contact list and displays the details of the contact found.

   Use case ends.

**Extensions**
* 1a. Input format is invalid.
    * 1a1. CampusConnect shows error message.
    * 1a2. User enters input again.

      Steps 1a1-1a2 repeat until input format is valid.

      Use case ends.


* 1b. Contact to find does not exist.
    * 1b1. CampusConnect shows empty contact list.

      Use case ends.

**Use case: UC04 - Add tags to a contact**  

**Precondition**: Contact to add tags to already exists

**MSS**
1. User requests to add tags to a contact.
2. CampusConnect searches the contact list and finds the correct contact.
3. CampusConnect adds tags to the contact.
4. CampusConnect displays success message.

   Use case ends.

**Extensions**
* 1a. Input format is invalid.
    * 1a1. CampusConnect shows error message.
    * 1a2 User enters input again.

      Steps 1a1-1a2 repeat until input format is valid.

      Use case ends.


* 3a. Tag already exists for the contact
    * 3a1. CampusConnect shows error message.
      
      Use case ends.

**Use case: UC05 - Delete a tag from a contact**  

**Precondition**: Contact to delete a tag from already exists

**MSS**
1. User requests to delete a specific tag from a contact
2. CampusConnect searches the contact list and finds the correct contact.
3. CampusConnect deletes the specific tag from the contact
4. CampusConnect displays success message

   Use case ends

**Extensions**
* 1a. Input format is invalid
    * 1a1. CampusConnect shows error message.
    * 1a2 User enters input again.

      Steps 1a1-1a2 repeat until input format is valid.

      Use case ends.

* 3a. The contact does not contain the tag user wants to delete
    * 3a1. CampusConnect shows error message.
  
      Use case ends.

**Use case: UC06 - Undo an execution of command**
**Precondition**: At least one valid command has been executed by the user.

**MSS**
1. User requests to undo the most recent command execution.
2. CampusConnect reverts the most recent command, restoring the data to its previous state 
before the command was executed.

   Use case ends

**Extensions**
* 1a. No earlier data to revert.
    * 1a1. CampusConnect shows error message.

      Use case ends.

**Use Case: UC07 - Redo Command Execution**

**Precondition: The user has previously undone at least one command.**

**MSS:**
1. The user requests to redo the most recently undone command.
2. CampusConnect restores the data to the state it was in immediately before the undo.

   Use case ends.

**Extensions:**
* 1a. No More Commands to Redo:
    * 1a1. CampusConnect displays an error message indicating that there are no more commands to redo.

      Use case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The application should respond within two seconds after user input commands.
5.  The application is not required to interact any other online system or applications.
6.  The application should not use offensive and obscene images or visuals.
7.  The record should bot be lost when a system fault occurs.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **GUI**: The *Graphical User Interface*, through which the user can input commands and view contacts and tags.
* **Field**: An attribute possessed by a contact, namely Phone number, Tags, Name and Email.
* **Prefix**: An identifier used in commands to indicate which field is referred to. For the 4 fields Phone, Name, Tags and Email,
    the *prefixes* would be `p/`, `t/`, `n/` and `e/` respectively.
* **Duplicate Contact**: A contact is considered a duplicate if it shares the same phone number, email, or name with another contact. 
    Phone numbers and emails are unique identifiers, so allowing duplicates could cause confusion, such as accidentally contacting the wrong person. 
    Unique names also help maintain organization, with small variations (e.g., capitalization or adding a number) used to distinguish individuals with the same name.
* **Tag List**: The scrollable list in the GUI displaying all unique tags and their colour-coded categories. The execution of `find` commands do not change the content of tag lists. 
* **Person List**: The scrollable list of contacts in the GUI displaying all contacts and the respective values for their fields.
* **Commands affected by `undo` and `redo`**: These refer to all commands that affect the *state* of the Tag List and Contact List
  in **CampusConnect** and exclude `list` and `find`, as they do not alter the state of the contact or tag list.

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

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Deleting a person

1. Deleting a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    1. Test case: `delete 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. 

    1. Test case: `delete 0`<br>
       Expected: No person is deleted. Error details shown in the status message.

1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
   Expected: Similar to previous.

### Finding a person

1. Finding a person with tags

    1. Assumption: Pick any 2 tags (or substring of the tags) present in any contact in the contact list. Call these x and y.

    1. Test case: `find t/x` where `x` is the substring/tag chosen<br>
       Expected: All contacts with tags containing x will be displayed with a success message.

    1. Test case: `find t/x t/y` where `x` and `y` are the substrings/tags chosen<br>
       Expected: The contact(s) with tags containing x or y will be displayed with a success message.

1. Finding a person with multiple fields

    1. Prerequisites: There are contacts with tags in the contact list. Add some if this is not the case.

    1. Assumption: Pick any name and tag within the same contact. Call these name x and tag y.

    1. Test case: `find n/x t/y` where `x` and `y` are the name and tag chosen<br>
       Expected: The contact(s) with name containing x and tags containing y will be displayed with a success message.

1. Other incorrect find commands to try: `find`, `find x` (with no prefix)<br>
   Expected: No filtering of contacts will occur and an error message will be displayed.

### Undoing the last operation

1. Undoing an execution that modifies the CampusConnect data

   1. Prerequisites: Perform any operation that modifies the state (all commands except for list and find) to ensure there is an action to undo.

   1. Test case: undo 
      Expected: The last operation is undone, restoring the previous state. The list updates accordingly, and a status message confirms the undo action.

1. Undo immediately after starting the application
    
    1. Prerequisites: CampusConnect has been booted and no command has been input yet.
    1. Test case: undo
       Expected: No undo operation is performed. An error message appears in the status message, indicating there is no action to undo.

### Redoing the last operation

1. Redoing an execution that modifies the CampusConnect data

    1. Prerequisites: Perform any operation that modifies the state (all commands except for list and find) and undo that action.

    1. Test case: redo
       Expected: The last undone operation is redone, restoring the previous state. The list updates accordingly, and a status message confirms the redo action.

1. Redo immediately after starting the application
    1. Prerequisites: CampusConnect has been booted and no command has been input yet.

    1. Test case: redo
          Expected: No redo operation is performed. An error message appears in the status message, indicating there is no action to redo.

1. Redo when no operation has been undone
    1. Prerequisites: Some commands that affect the state of CampusConnect have been entered but none of them have been redone.

    1. Test case: redo
       Expected: No redo operation is performed. An error message appears in the status message, indicating there is no action to redo.

### Adding a tag
1. Adding a tag while all tags are being shown

   1. Prerequisites: There are 2 contacts in the list. First contact on the list has tag `CS2100`, second contact has tags `floortball` and `friends`. 

   1. Test case: `addtag 1 t/CS2040S`<br>
      Expected: The first contact now has 2 tags `CS2100` and `CS2040S`. The tag list is updated accordingly.
      
   1. Test case: `addtag 2 t/homie t/homie`
      Expected: The second contact now has 3 tags `floortball`, `friends` and `homie`. The tag list is updated accordingly.
      
   1. With the following test cases:
      1. Test case: `addtag 0 t/volleyball` <br>
      1. Test case: `addtag 3 t/homie` <br>
      1. Test case: `addtag 2` <br>
         Expected: No new tags are added. Error message is shown.

### Deleting a tag from a person

1. Deleting a tag.

    1. Prerequisites: There are contacts with tags in the contact list. Add some if this is not the case.

    1. Assumption: Pick any contact with at least one tag. Let `i` be the index (one-based) of this contact and `x` be the name of the tag.

    1. Test case: `deltag i t/x` where `i` is the index and `x` is the tag chosen<br>
       Expected: The tag x will be deleted from person i and the tag will also disappear from the Tag List. A success message will be displayed.

1. Other incorrect delete tag commands to try: `deltag`, `deltag M t/x` (where M is larger than the list size or smaller than 0), `deltag 1 x`<br>
   Expected: No deleting of tags will occur and an error message will be displayed.

### Categorizing a tag

1. Categorizing an existing tag

    1. Prerequisites: Ensure that the tag `CS2103` exists and is under a category other than `Academics` (Gold).
   
    2. Test case: `cattag t/CS2103 acads` </br>
       Expected: Success message is shown. All occurrences of the tag `CS2103` in the person list on the bottom left and tag list on the bottom right are set to `Academics` category. Colour of tag `CS2103` set to Gold.

2. Attempting to categorize a non-existent tag

    1. Prerequisites: Ensure that tag `A` does not exist yet.
   
    2. Test case: `cattag t/A activity` </br>
       Expected: Error message "`Tag not found: [A]`" is shown, indicating that tag `A` does not exist.
   
3. Attempting to categorize to an invalid category

    1. Prerequisites: Ensure that tag `CS2103` is still present.
   
    2. Test case: `cattag t/CS2103 foo` </br>
       Expected: Error message "`Invalid category: foo`" is shown.
   
4. Attempting to categorize an **invalid tag** to an **invalid category**

    1. Prerequisites: Ensure that tag `A` does not exist yet.
   
    2. Test case: `cattag t/A foo` </br>
       Expected: Error message "`Invalid category: foo`" is shown. Message for invalid tag is not shown for this case.

--------------------------------------------------------------------------------------------------------------------
## **Appendix: Planned enhancements**

Team size: 5

1. Change the font color for tags: Currently, the font color for `GENERAL` tags is grey, making them less noticeable. We plan to use a higher-contrast font color to make tags more prominent and easier to read.
2. Allow adding overseas phone number: The app currently supports only Singaporean phone number. We aim to expand functionality to include valid international numbers, complete with country codes.
3. Allow certain special characters: We currently only allow alphanumeric characters and whitespaces. We plan to support additional characters, such as hyphens, to better accommodate real-world naming conventions.
4. Make duplicate contact error message more specific: The current error message for duplicate contacts, “This person already exists in CampusConnect,” is too general. We plan to enhance it by specifying the name of the existing contact that duplicates the one the user is attempting to add.
5. Make `find n/`, `find p/` and `find e/` throw an error while `find t/` finds all users with not tags.
6. Make the result display box slightly larger to accommodate longer error messages.

--------------------------------------------------------------------------------------------------------------------
## **Appendix: Future features**
Below is a list of features that we feel would further enhance the user experience.

  |              Feature               | Description                                                                      |
  |:----------------------------------:|----------------------------------------------------------------------------------|
  |         Clustering of tags         | Group tags of the same categories together in the UI's display of the tags list. |
  |            Pin contacts            | Keep selected contacts constantly shown at the top of the contacts list.         |
  |     Customize category colors      | Change the colors of the categories to the user's preference.                    |
  |    Multiple numbers per contact    | Allow more than one number per contact to accommodate multiple contact numbers.  |
  |     Custom fields for contacts     | Add custom fields to the contacts added.                                         |
  |      Custom shortcut commands      | Add custom shortcut commands to streamline actions within the application.       |
  |    Delete tag from all contacts    | Remove a specific tag from all contacts at once.                                 |
  |             Dark mode              | Include a dark mode theme for easier viewing in low light conditions.            |
  |      Copy contact information      | Enable copying of contact information to reduce errors from manual copying.      |
  |          Export contacts           | Provide an option to export contact information for easier sharing.              |
  
--------------------------------------------------------------------------------------------------------------------

## **Appendix: Effort**
Our goal was to improve AB3 in terms of contact organisation, finding and tagging to allow for greater functionality and flexibility.

Our first major change was to modify the `find` command to accept any field as a parameter and allow multiple parameters. This was a moderate effort
that required us to change how the `FindCommand` class worked by creating new predicate classes and processing the logic for that as well,
which was aided by the given predicate classes that we used as a template, but it was not trivial.

Our next major change was the `undo` and `redo` commands, which were quite extensive to implement. We had to create the `VersionedCampusConnect` (a 
variation on the `VersionedAddressBook`) and resolve serious issues related to the undo and redo state, such as logic to process
when the `undo` and `redo` commands failed and whether non-state affecting commands (like `find`) would affect the `undo` and `redo` result. Overall,
this was quite difficult.

Finally, our last major change was the tag management and categorisation system, which was more difficult as the `undo` and `redo`. We added a tag management 
component and several commands, different types of tags, and a tag list component in the UI. Figuring out how to dynamically update the tags and the tag list 
in the GUI required a restructuring of our GUI files (under the `ui` folder) and we had faced many issues with the tag categorisation system. All in all, 
implementing this system was not easy but it did provide better tag customisation and control than AB3.

Most commands implemented used the given `Command` classes as a reference, but modified them to adapt the respective `execute()` methods for the command.

On top of all these, we had also modified the GUI, which required us to familiarise and work through the 
quirks of JavaFX.
