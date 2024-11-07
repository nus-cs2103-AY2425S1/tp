---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# AB-3 Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* Our name, **CampusConnect**, was inspired by the NUS internship portal [**TalentConnect**](https://nus-csm.symplicity.com/).

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

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/CampusConnect/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/CampusConnect/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
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

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/CampusConnect/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/CampusConnect/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/CampusConnect/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/CampusConnect/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

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

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `CampusConnectParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `CampusConnectParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

Finally, the `Logic` contains the important `Command` classes. Some command classes from AB3 have been retained:
<puml src="diagrams/CommandClassesOriginal.puml" width="600"/>

However, there are new classes implemented for CampusConnect as well:
<puml src="diagrams/CommandClasses.puml" width="600"/>

The structure is simple:
* Each `Command` class (old and new) extends from the abstract `Command` class, which enforces the implementation of the `execute()` method.
* Each `Command` class contains the respective `COMMAND_WORD` representing the name of the command and a `MESSAGE_USAGE` string to demonstrate how to use the respective command.
* They also contain their own respective error messages.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/CampusConnect/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the CampusConnect data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `CampusConnect`, which `Person` references. This allows `CampusConnect` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/CampusConnect/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

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

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the CampusConnect. The `delete` command calls `Model#saveCurrentCampusConnect()`, causing the modified state of the CampusConnect after the `delete 5` command executes to be displayed and the old state of CampusConnect to be saved to the history.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#saveCurrentCampusConnect()`, causing the modified state of the CampusConnect after the `delete 5` command executes to be displayed and the old state of CampusConnect to be saved to the history.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will call `Model#undoCampusConnect()`, so the CampusConnect state will not be saved into the `history`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoCampusConnect()`, which will save the current CampusConnect state into `future` and pop the latest saved CampusConnect state from the `history`.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `history` is empty, then there are no previous CampusConnect states to restore. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoCampusConnect()`, which save current state into `history` and restores the CampusConnect to that state popped from the top of `future`.

<box type="info" seamless>

**Note:** If the `future` stack is empty, then there are no undone CampusConnect states to restore. The `redo` command uses `Model#canRedoCampusConnect()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the CampusConnect, such as `list`, will usually not call `Model#saveCurrentCampusConnect()`, `Model#undoCampusConnect()` or `Model#redoCampusConnect()`. Thus, the `history` and `future` remain unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitCampusConnect()`. All CampusConnectState in the future will be removed. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire CampusConnect.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


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

**Target user profile**:  university students   
   
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
    * 1b1. CampusConnect shows error message.
    * 1b2. User enters input again.

      Steps 1b1-1b2 repeat until input format is valid.

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

**Use cases: UC05 - Delete a tag from a contact**
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

**Use cases: UC06 - Undo an execution of command**
**Precondition**: At least one valid command has been executed by the user.

**MSS**
1. User requests to undo the most recent command execution.
2. CampusConnect reverts the most recent command, restoring the data to its previous state 
before the command was executed.

   Use case ends

**Extensions**
* 1a. Input format is invalid.
    * 1a1. CampusConnect shows error message.
    * 1a2. User enters input again.

      Steps 1a1-1a2 repeat until input format is valid.

      Use case ends.

* 1b. No earlier data to revert.
    * 1b1. CampusConnect shows error message.

      Use cases ends.

**Use Case: UC07 - Redo Command Execution**

**Precondition: The user has previously undone at least one command.**

**MSS:**
1. The user requests to redo the most recently undone command.
2. CampusConnect restores the data to the state it was in immediately before the undo.

   Use case ends.

**Extensions:**
* 1a. Invalid Input Format:
    * 1a1. CampusConnect displays an error message indicating the input format is invalid.
    * 1a2. The user re-enters the input.

      Steps 1a1-1a2 repeat until the input format is valid.

      Use case ends.

* 1b. No More Commands to Redo:
    * 1b1. CampusConnect displays an error message indicating that there are no more commands to redo.

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

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Adding a tag
1. Deleting a tag while all tags are being shown
   1. Prerequisites: There are 2 person in the list. First person on the list has tag `CS2100`, second person has tags `floortball` and `friends`. 
   1. Test case: `addtag 1 t/CS2040S`<br>
      Expected: The first person now has 2 tags `CS2100` and `CS2040S`. The tag lists are updated accordingly.
   1. Test case: `addtag 2 t/homie t/homie`
      Expected: The second person now has 3 tags `floortball`, `friends` and `homie`. 
   1. With the following test case:
      1. `addtag 1 t/CS2040s`
      1. Test case: `addtag 1 t/CS2030s t/CS2040S`
      1. Test case: `addtag 0 t/volleyball` <br>
      1. Test case: `addtag 3 t/homie` <br>
      1. Test case: `addtag 2` <br>
         Expected: No new tag added. Error details shown in the status message. All status bar remain the same.


### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_

--------------------------------------------------------------------------------------------------------------------
## **Future features**
Below is a list of features that we feel would further enhance the user experience.

  | Feature                                                                         | Description                                                                         |
  |---------------------------------------------------------------------------------|-------------------------------------------------------------------------------------|
  | Clustering of tags                                                              | Group tags of the same categories together in the UI's display of the tags list.    |
  | Pin contacts                                                                    | Keep selected contacts constantly shown at the top of the contacts list.            |
  | Customize category colors                                                       | Change the colors of the categories to the user's preference.                       |
  | Multiple numbers per contact                                                    | Allow more than one number per contact to accommodate multiple contact numbers.     |
  | Custom fields for contacts                                                      | Add custom fields to the contacts added.                                            |
  | Custom shortcut commands                                                        | Add custom shortcut commands to streamline actions within the application.          |
  | Delete tag from all contacts                                                    | Remove a specific tag from all contacts at once.                                    |
  | Dark mode                                                                       | Include a dark mode theme for easier viewing in low light conditions.               |
  | Copy contact information                                                        | Enable copying of contact information to reduce errors from manual copying.         |
  | Export contacts                                                                 | Provide an option to export contact information for easier sharing.                 |

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
