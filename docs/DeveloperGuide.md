---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-F14b-3/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-F14b-3/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
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

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-F14b-3/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-F14b-3/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-F14b-3/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands and to navigate the command history.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-F14b-3/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

Another sequence diagram below illustrates the interactions within the `Logic` component when **arguments** are involved, taking `execute("mark 1 d/2020-01-01")` API call as an example.

![Interactions Inside the Logic Component for the `mark 1 d/2020-01-01` Command](images/MarkSequenceDiagram.png)
<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `MarkCommandParser` and `ArgumentMultimap` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. When a command is executed, it will communicate with `Model` to add the command to the command history.
1. The command will also communicate with `Model` to perform other operations (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-F14b-3/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="550" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* stores a `CommandTextHistory` object that represents the history of commands entered by the user.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="550" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-F14b-3/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

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

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_


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

* employee at Active Ageing Centre (AAC)
* has a need to manage a significant number of contacts
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Easy way to manage regular checkups. Fast, convenient and reliable way to organise contacts and ensure that everyone is checked up on a regular basis. It solves the problem of having to manually manage all the elderly just to keep up with the government’s (AIC) requirements.


### User stories

Priorities: High (must have) - `****`, Medium (nice to have) - `***`, Low (unlikely to have) - `**`, Super Low (won't have) - `*`

| Priority | As a …​                                    | I want to …​                     | So that I can…​                                                        |
| -------- | ------------------------------------------ | ------------------------------ | ---------------------------------------------------------------------- |
 | `****` | User | Mark elderly as called | I can easily keep track of who has been contacted |
 | `****` | New user | list elderly contacts by priority (prioritised by last called date) | I know whom to contact first | 
 | `****` | Frequent User | Remove elderly from the call list | Any elderly who has passed away or left the program will no longer be on the list | 
 | `****` | User | Record details of the elderly (NRIC etc.) | I know who I'm calling | 
 | `****` | Frequent User | Add new elderly who have joined the Befriending Program | I can keep track of these new elderly and call them regularly | 
 | `****` | Expert User | Take notes regarding the call | I can keep track of things which should be followed up on | 
 | `****` | Frequent User | Update elderly contact information | I can keep the elderly information up to date | 
 | `****` | Frequent User | Search elderly by name | I can find this specific person if they were to call me and I can log it as a call | 
 | `****` | Expert User | Generate a monthly report to AIC / excel sheet | show the progress and outcome of the Befriending Program | 
 | `****` | Frequent User | Check if the same elderly has been added multiple times | I don't call the same person multiple times | 
 | `**` | Frequent User | Make changes to the style of my address book | I can be happy when I use the address book :) | 
 | `**` | Frequent user | Mark contacted elderly quickly using shortcuts | I can work efficiently to contact the entire list | 
 | `**` | Detail-orientated user | Add tags to each elderly | I can keep track of details that are important when taking the call. | 
 | `**` | Expert User | Add custom fields | I can add details of elderly which are not currently in the system | 
 | `**` | Expert User | Visualise how many and which elderly to be called in a calendar | I can plan accordingly if any elderly needs to switch dates | 
 | `**` | Expert User | Filter through certain details added for the elderly | I can keep track of how the elderly are doing easily | 
 | `**` | Frequent User | receive notifications for the contacts that are due in the next hour | I can prioritize and complete the calls on time | 
 | `**` | New User | Receive feedback on the commands I have given if they are incorrect | I can easily correct the errors in the commands given | 
 | `***` | Frequent User | Use my up and down arrow keys to go back and forth between commands | I don't have to retype the same commands | 
 | `***` | New user | Receive a list of commands that the address book uses | I can familiarise myself with the commands and shortcuts | 
 | `***` | Frequent User | I can undo my actions | I can correct my mistakes | 
 | `***` | Expert User | Quickly update the status of multiple elderly contacts | I can efficiently manage my tasks | 
 | `***` | New user | Try out the app with sample data | I can familiarise myself with it without worrying about the data I am playing around with | 
 | `***` | Onboarded user | Purge all sample data | The app is ready to be used | 
 | `***` | Frequent User | I can view a history of calls made to an elderly contact | I can track past interactions | 
 | `***` | Frequent User | Change the frequency of calls needed to be made for each elderly | I can track when I need to make calls | 
 | `***` | Expert User | Archive elderly contacts who are temporarily not part of the program | Keep my contact list organised | 
 | `***` | Frequent User | Export/Import my data across computers | If I would like to work using separate computers, e.g. a laptop and desktop, I can keep them updated | 
 | `***` | Expert User | Set multiple contacts as emergency contacts | I can call their next of kin in the event the elderly do not pick up or needs help | 
 | `***` | Frequent User | Export a specific elderly's details and history (All information) | So I can share it with another employee | 
 | `*` | Expert user | Be notified by the app when too many elderly calls are scheduled on one day | I will not overwork myself or spend too little time calling each elderly | 
 | `*` | Expert User | I can see a dashboard of my weekly or monthly call stats | I can track my productivity and ensure that I hit my KPI | 
 | `*` | Expert User | Set up automated messages for elderly contacts that are not reachable | I can have an alternative communication method | 
 | `*` | Expert User | Use AI to calculate the priority list for elderly based on their information | The correct eldelry are being prioritised  | 

### Use cases

**System: ContactMate**  
**Use case: UC01 \- Mark elderly as called**  
**Actor: Staff**  
**Guarantees:** 

* Marks elderly’s details to contact book only if input has no errors.

**MSS:**

1. Staff <u>lists elderly contacts by priority (UC02)</u>.  
2. Staff calls the elderly at the top of the list.  
3. Staff marks the elderly as called and takes notes of the call with mark command.  
4. ContactMate updates the elderly as marked and displays a success message.

    Use case ends.

**Extensions:**  
* 1a. The list is empty.  
  * Use case ends.  
* 3a. ContactMate detects an invalid INDEX or incorrect command syntax.  
  * 3a1. ContactMate shows an error message.  
  * Use case resumes from step 3\.

**System: ContactMate**  
**Use case: UC02 \- List elderly contacts by priority**  
**Actor: Staff**  
**Guarantees:** 

* List of elderly sorted by priority will be shown.

**MSS:**

1. Staff inputs list command to view elderly contacts.  
2. ContactMate updates view to show contacts sorted based on priority (date to be called).

      Use case ends.

**System: ContactMate**  
**Use case: UC03 \- List individual elderly call history**  
**Actor: Staff**  
**Guarantees:** 

* Elderly call history will be listed only if the input has no errors.

**MSS:**

1. Staff <u>lists elderly contacts by priority (UC02)</u>.  
2. Staff inputs INDEX or NRIC of elderly they want to know the call history of.
3. ContactMate updates view to show a list of calls along with their corresponding notes made to a specific elderly. 

	Use case ends.

**Extensions:**  
* 1a. The list is empty.  
    * Use case ends.  
* 2a. ContactMate detects an invalid INDEX or NRIC or incorrect command syntax.  
	* 2a1. ContactMate shows an error message.  
	* Use case resumes from step 2\.

**System: ContactMate**  
**Use case: UC04 \- Delete elderly from the call list**  
**Actor: Staff**  
**Guarantees:** 

* Delete elderly from the contact list only if input has no errors.

**MSS:**

1. Staff <u>lists elderly contacts by priority (UC02)</u>.  
2. Staff inputs the NRIC or INDEX of elderly they want to delete.  
3. ContactMate deletes the elderly and confirms the successful deletion for Staff.

    Use case ends.

**Extensions:**  
* 1a. The list is empty.  
    * Use case ends.  
* 2a. ContactMate detects an invalid INDEX or invalid NRIC or incorrect command syntax.  
	* 2a1. ContactMate shows an error message.  
	* Use case resumes from step 2\.

**System: ContactMate**  
**Use case: UC05 \- Add new elderly who have joined the Befriending Program, with appropriate details and fields**  
**Actor: Staff**  
**Guarantees:** 

* Adds elderly to contact book only if input has no errors.

**MSS:**

1. Staff inputs details for the elderly they want to add to the system.  
2. ContactMate adds the new elderly and shows the updated list with the newly added elderly.

      Use case ends.

**Extensions:**  
* 1a. ContactMate detects an invalid INDEX or invalid NRIC or incorrect command syntax.  
	* 1a1. ContactMate shows an error message.  
	* Use case resumes from step 1\.  
* 1b. ContactMate detects that the elderly being added has an NRIC matching someone in the contact book.  
	* 1b1. ContactMate shows an error message saying this elderly already exists.  
	* Use case resumes from step 1\.  
* 1c. ContactMate detects that the elderly being added has a matching name, phone number or email with someone in the contact book.  
	* 1c1. ContactMate shows a warning message that the elderly added has a matching field.  
	* Use case resumes from step 2\.

### Non-Functional Requirements

1. A user with above-average typing speed (> 40 WPM) for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
2. The product should be a single-user system.
3. The product should not rely on a remote server.
4. It should accommodate up to 250 elderly without noticeable performance slowdowns during typical usage.
5. The product should respond within two seconds.
6. The product should work on Windows, Linux and Mac as long as they have `Java 17` installed.


### Glossary

* **AAC**: Active Ageing Centre. A recreational centre that supports elderly in the area.
* **Befriending** Program: Program which elderly signs up for to receive support from an AAC.


--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

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

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
