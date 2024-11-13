---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* Based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).
* Libraries used: [JavaFX](https://openjfx.io/), [Jackson](https://github.com/FasterXML/jackson), [JUnit5](https://github.com/junit-team/junit5)

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

Another (more concise) sequence diagram below illustrates some interactions within the `Logic` component when **arguments** are involved, taking `execute("mark 1 d/2024-10-25")` API call as an example.

![Interactions Inside the Logic Component for the `mark 1 d/2024-10-25` Command](images/MarkSequenceDiagram.png)
<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `MarkCommandParser` and `ArgumentMultimap` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. When a command is executed, it will communicate with `Model` to add the command to the command history.
1. The command will also communicate with `Model` to perform other operations (e.g. to delete an elderly).<br>
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

Step 2. The user executes `delete 5` command to delete the 5th elderly in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new elderly. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the elderly was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

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
  * Pros: Will use less memory (e.g. for `delete`, just save the elderly being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### Command history feature
#### Implementation
The command history feature is facilitated by `CommandTextHistory`. It has a `commandHistory` arraylist that stores the history of commands entered by the user as strings 
and a `currentCommandIndex` that is initialised to be `0` to keep track of the current command index in the command history.
The `CommandTextHistory` class implements the following operations:
* `CommandTextHistory#addCommandHistory(String commandText)` — Adds a command text to the command history.
* `CommandTextHistory#getPreviousCommand()` — Returns the previous command text in the command history.
* `CommandTextHistory#getNextCommand()` — Returns the next command text in the command history.

##### Getting previous/next command
The `getPreviousCommand()` method will first check if there are previous commands available by checking if `currentCommandIndex > 0 `. 
If there are previous commands, it will decrement the `currentCommandIndex`. 

The `getNextCommand()` method will first check if there are more commands available ahead in the history by checking if `currentCommandIndex < commandHistory.size() - 1`.
If there are following commands, it will increment the `currentCommandIndex`.

After decrementing or incrementing, the method checks if `currentCommandIndex` is still within the bounds of `commandHistory`.
If the index is out of bounds, the method returns an empty string `""`, indicating that there is no such command to be retrieved.
If `currentCommandIndex` is within bounds, it retrieves and returns the command at the updated index in `commandHistory`.

##### Adding command to history
The `addCommandHistory(String commandText)` method will append the command text to the `commandHistory` arraylist and set the `currentCommandIndex` to the size of the `commandHistory` arraylist.
This resets the `currentCommandIndex` to 1 position after the end of the `commandHistory` arraylist after a new command is added. We set the `currentCommandIndex` to `size` instead of `size - 1`
as the current command to be entered is not in the arraylist yet and the command that was just entered is at index `size - 1`.


Take a look at this example:

Currently, the `commandHistory` arraylist only contains 4 commands, and we are viewing the 2nd command that we entered.
We want to get the next command that we entered. So, after clicking the UP button, `CommandTextHistory#getNextCommand()` is called.

![CommandHistory0](images/CommandHistory0.png)

`currentCommandIndex` is not at the end of `commandHistory` so it will increment `currentCommandIndex` and return the command "list" to the TextField.

![CommandHistory1](images/CommandHistory1.png)

Now, let's say we enter in "list" command that is in the `TextField`. The new "list" command is added to the back of the `commandHistory` arraylist, taking up position at index 4.
Then, we set the `currentCommandIndex` to be the size of the arraylist, anticipating the next command to be added, as shown below.

![CommandHistory2](images/CommandHistory2.png)

:information_source: **Note:** CommandTextHistory will store commands that were unsuccessful as well.

These operations are exposed in the `Logic` interface as `Logic#addCommandTextToHistory(String commandText)`, `Logic#getPreviousCommandTextFromHistory()` and `Logic#getNextCommandTextFromHistory()` respectively.

The following sequence diagram shows how the user can get previous command:
![CommandTextHistorySequenceDiagram](images/CommandTextHistorySequenceDiagram.png)

The following activity diagram summarizes what happens when a user interacts with this feature:
![CommandTextHistoryActivityDiagram](images/CommandTextHistoryActivityDiagram.png)

#### Future Improvements:

* Implement a feature to clear the command history.

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

Priorities: High (must have) - `****`, Medium (nice to have) - `***`, Low (unlikely to have) - `**`, Super Low (highly unlikely to have) - `*`

| Priority | As a …​                | I want to …​                                                                 | So that…​                                                                                            |
| -------- |------------------------|------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------|
 | `****` | User                   | Mark elderly as called                                                       | I can easily keep track of who has been contacted                                                    |
 | `****` | User                   | List elderly contacts by priority (prioritised by last called date)          | I know whom to contact first                                                                         | 
 | `****` | Frequent User          | Remove elderly from the list                                                 | Any elderly who has passed away or left the program will no longer be on the list                    | 
 | `****` | User                   | Record details of the elderly (Name, NRIC etc.)                              | I know who I'm calling                                                                               | 
 | `****` | Frequent User          | Add new elderly who have joined the Befriending Program                      | I can keep track of these new elderly and call them regularly                                        | 
 | `****` | Expert User            | Take notes regarding the call                                                | I can keep track of things which should be followed up on                                            | 
 | `****` | Frequent User          | Update elderly details                                                       | I can keep the elderly details up to date                                                            | 
 | `****` | Frequent User          | Search elderly by name or NRIC                                               | I can more easily find elderly in the list                                                           |
 | `****` | Forgetful User         | Mark calls with a specific date                                              | I can mark a call that I previously forgot to mark                                                   | 
 | `****` | User                   | View the next contact date for each elderly                                  | I can quickly see which day the next call should be made to better plan my work                      |
 | `****` | Frequent User          | Know if the same elderly has been added before                               | I don't add the same elderly multiple times                                                          |
| `***` | Frequent User          | Use my up and down arrow keys to go back and forth between commands          | I don't have to retype the same commands                                                             | 
| `***` | New user               | Receive a list of commands that the address book uses                        | I can familiarise myself with the commands and shortcuts                                             | 
| `***` | Frequent User          | I can undo my actions                                                        | I can correct my mistakes                                                                            | 
| `***` | Expert User            | Quickly update the status of multiple elderly contacts                       | I can efficiently manage my tasks                                                                    | 
| `***` | New user               | Try out the app with sample data                                             | I can familiarise myself with it without worrying about the data I am playing around with            | 
| `***` | Onboarded user         | Purge all sample data                                                        | The app is ready to be used                                                                          | 
| `***` | Frequent User          | I can view a history of calls made to an elderly contact                     | I can track past interactions                                                                        | 
| `***` | Frequent User          | Change the frequency of calls needed to be made for each elderly             | I can track when I need to make calls                                                                | 
| `***` | Expert User            | Archive elderly contacts who are temporarily not part of the program         | I can keep my contact list organised                                                                 | 
| `***` | Frequent User          | Export/Import my data across computers                                       | If I would like to work using separate computers, e.g. a laptop and desktop, I can keep them updated | 
| `***` | Expert User            | Set multiple contacts as emergency contacts                                  | I can call their next of kin in the event the elderly do not pick up or needs help                   | 
| `***` | Frequent User          | Export a specific elderly's details and history (All information)            | I can share it with another employee                                                                 | 
 | `**` | Frequent User          | Make changes to the style of my address book                                 | I can be happy when I use the address book                                                           | 
 | `**` | Frequent user          | Mark contacted elderly quickly using shortcuts                               | I can work efficiently to contact the entire list                                                    | 
 | `**` | Detail-orientated user | Add tags to each elderly                                                     | I can keep track of details that are important when taking the call                                  | 
 | `**` | Expert User            | Add custom fields                                                            | I can add details of elderly which are not currently in the system                                   | 
 | `**` | Expert User            | Visualise how many and which elderly to be called in a calendar              | I can plan accordingly if any elderly needs to switch dates                                          | 
 | `**` | Expert User            | Filter through certain details added for the elderly                         | I can keep track of how the elderly are doing easily                                                 | 
 | `**` | Frequent User          | Receive notifications for the contacts that are due in the next hour         | I can prioritize and complete the calls on time                                                      | 
 | `**` | New User               | Receive feedback on the commands I have given if they are incorrect          | I can easily correct the errors in the commands given                                                |
 | `*` | Expert user            | Be notified by the app when too many elderly calls are scheduled on one day  | I will not overwork myself or spend too little time calling each elderly                             | 
 | `*` | Expert User            | I can see a dashboard of my weekly or monthly call stats                     | I can track my productivity and ensure that I hit my KPI                                             | 
 | `*` | Expert User            | Set up automated messages for elderly contacts that are not reachable        | I can have an alternative communication method                                                       | 
 | `*` | Expert User            | Use AI to calculate the priority list for elderly based on their information | The correct elderly are being prioritised                                                            | 

### Use cases

**System: ContactMate**  
**Use case: UC01 \- Mark elderly as called**  
**Actor: Staff**  
**Guarantees:** 

* Marks elderly’s details to contact book only if input has no errors.

**MSS:**

1. Staff <u>lists elderly contacts by priority (UC02)</u>.  
2. Staff calls the elderly at the top of the list.  
3. Staff requests to mark the elderly as called and takes notes of the call, selecting the elderly by INDEX or NRIC.
4. ContactMate updates the elderly as marked and displays a success message.

    Use case ends.

**Extensions:**  
* 1a. The list is empty.  
  * Use case ends.  
* 3a. ContactMate detects an incorrect command format.  
  * 3a1. ContactMate shows an error message with the correct command format.  
  * Use case resumes from step 3\.
* 3b. ContactMate detects an INDEX out of bounds of the list or an NRIC that does not exist in the list.
  * 3b1. ContactMate shows an error message, explaining that the elderly does not exist.  
  * Use case resumes from step 3\.
* 3c. ContactMate detects an invalid date format.
  * 3c1. ContactMate shows an error message with the correct date format.  
  * Use case resumes from step 3\.

<br/><br/>
**System: ContactMate**  
**Use case: UC02 \- List elderly contacts by priority**  
**Actor: Staff**  
**Guarantees:** 

* List of elderly sorted by priority of who to call next will be shown.

**MSS:**

1. Staff requests to view elderly contacts.  
2. ContactMate updates view to show contacts sorted based on priority (date to be called).

      Use case ends.

<br/><br/>
**System: ContactMate**  
**Use case: UC03 \- List individual elderly call history**  
**Actor: Staff**  
**Guarantees:** 

* Elderly call history will be listed only if the input has no errors.

**MSS:**

1. Staff <u>lists elderly contacts by priority (UC02)</u>.  
2. Staff requests for the call history of the elderly, selecting the elderly by INDEX or NRIC.
3. ContactMate updates view to show a list of calls along with their corresponding notes made to a specific elderly. 

	Use case ends.

**Extensions:**  
* 1a. The list is empty.  
    * Use case ends.  
* 2a. ContactMate detects an incorrect command format.
    * 2a1. ContactMate shows an error message with the correct command format.
    * Use case resumes from step 2\.
* 2b. ContactMate detects an INDEX out of bounds of the list or an NRIC that does not exist in the list.
    * 2b1. ContactMate shows an error message, explaining that the elderly does not exist.
    * Use case resumes from step 2\.

<br/><br/>
**System: ContactMate**  
**Use case: UC04 \- Delete elderly from the call list**  
**Actor: Staff**  
**Guarantees:** 

* Delete elderly from the contact list only if input has no errors.

**MSS:**

1. Staff <u>lists elderly contacts by priority (UC02)</u>.  
2. Staff requests to delete an elderly, selecting the elderly by INDEX or NRIC.
3. ContactMate deletes the elderly and shows the updated list with the elderly removed.

    Use case ends.

**Extensions:**  
* 1a. The list is empty.  
    * Use case ends.  
* 2a. ContactMate detects an incorrect command format.
    * 2a1. ContactMate shows an error message with the correct command format.
    * Use case resumes from step 2\.
* 2b. ContactMate detects an INDEX out of bounds of the list or an NRIC that does not exist in the list.
    * 2b1. ContactMate shows an error message, explaining that the elderly does not exist.
    * Use case resumes from step 2\.

<br/><br/>
**System: ContactMate**  
**Use case: UC05 \- Add new elderly who have joined the Befriending Program, with appropriate details and fields**  
**Actor: Staff**  
**Guarantees:** 

* Adds elderly to contact book only if input has no errors.

**MSS:**

1. Staff requests to add an elderly, entering the relevant details.  
1. ContactMate adds the new elderly and shows the updated list with the newly added elderly.

      Use case ends.

**Extensions:**  
* 1a. ContactMate detects an incorrect command format.  
	* 1a1. ContactMate shows an error message with the correct command format.  
	* Use case resumes from step 1\.  
* 1b. ContactMate detects an invalid format for one of the fields.  
    * 1b1. ContactMate shows an error message showing the correct format for the invalid field. 
    * Use case resumes from step 1\.
* 1c. ContactMate detects that the elderly being added has an NRIC matching someone in the contact book.  
	* 1c1. ContactMate shows an error message saying this elderly already exists.  
	* Use case resumes from step 1\.  
* 1d. ContactMate detects that the elderly being added has a matching name, phone number or email with someone in the contact book.  
	* 1d1. ContactMate shows a warning message that the elderly added has a matching name, phone number or email.  
	* Use case resumes from step 2\.

<br/><br/>
**System: ContactMate**     
**Use case: UC06 \- Edit an elderly who is in the system, with appropriate details and fields**      
**Actor: Staff**    
**Guarantees:**

* Edits the elderly in the contact book only if input has no errors.

**MSS:**

1. Staff <u>lists elderly contacts by priority (UC02)</u>.
1. Staff requests to edit an elderly, selecting the elderly by INDEX or NRIC, and entering the relevant details.
1. ContactMate edits the elderly and shows the updated list with the edited elderly.

   Use case ends.

**Extensions:**
* 1a. The list is empty.
    * Use case ends.
* 2a. ContactMate detects an incorrect command format.
    * 2a1. ContactMate shows an error message with the correct command format.
    * Use case resumes from step 2\.
* 2b. ContactMate detects an INDEX out of bounds of the list or NRIC (used to select the elderly) that does not exist in the list.
    * 2b1. ContactMate shows an error message, explaining that the elderly does not exist.
    * Use case resumes from step 2\.
* 2c. ContactMate detects an invalid format for one of the fields.
    * 2c1. ContactMate shows an error message showing the correct format for the invalid field.
    * Use case resumes from step 2\.
* 2d. ContactMate detects that the input of the NRIC field is matching someone in the contact book.
    * 2d1. ContactMate shows an error message saying this elderly already exists.
    * Use case resumes from step 2\.
* 2e. ContactMate detects that input of the name, phone number or email fields matches with someone in the contact book.
    * 2e1. ContactMate shows a warning message that the elderly has a matching name, phone number or email.
    * Use case resumes from step 3\.

<br/><br/>
**System: ContactMate**     
**Use case: UC07 \- Search elderly by name or NRIC**    
**Actor: Staff**  
**Guarantees:**

* Shows the filtered list of elderly that matches the name or NRIC.

**MSS:**    

1. Staff requests to search for an elderly, entering the elderly's name or NRIC.
2. ContactMate shows the filtered list of elderly  (if any) that matches the name or NRIC.

    Use case ends.

<br/><br/>
**System: ContactMate**      
**Use case: UC08 \- Mark an elderly as called, given that elderly's name**      
**Actor: Staff**        
**Guarantees:**     

* Marks the elderly as called only if the input has no errors.

**MSS:**

1. Staff <u>searches the elderly by name (UC07)</u>.
2. Staff calls that elderly.
3. Staff requests to mark the elderly as called and takes notes of the call, selecting the elderly by INDEX or NRIC.
4. ContactMate updates the elderly as marked and displays a success message.

    Use case ends.

**Extensions:**
* 1a. The filtered list is empty.
    * Use case resumes from step 1\.
* 3a. ContactMate detects an incorrect command format.
    * 3a1. ContactMate shows an error message with the correct command format.
    * Use case resumes from step 3\.
* 3b. ContactMate detects an INDEX out of bounds of the list or an NRIC that does not exist in the list.
    * 3b1. ContactMate shows an error message, explaining that the elderly does not exist.
    * Use case resumes from step 3\.
* 3c. ContactMate detects an invalid date format.
    * 3c1. ContactMate shows an error message with the correct date format.
    * Use case resumes from step 3\.

<br/><br/>
### Non-Functional Requirements

1. A user with above-average typing speed (> 40 Words Per Minute) for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
2. The product should be a single-user system.
3. The product should not rely on a remote server.
4. It should accommodate up to 250 elderly without performance slowdowns of more than 10 seconds during typical usage.
5. The product should generally respond within five seconds.
6. The product should work on Windows, Linux as long as they have **Java 17** installed.
7. The product should work on Mac as long as they have the specific version of **Java 17 JDK+FX Azul distribution** installed.
7. The product should not use a Database Management System.
8. The data should be stored locally and should be in a human editable text file.
8. The product needs to be developed in a breadth-first incremental manner over the project duration.
9. The software should follow the Object-oriented paradigm primarily.
10. The software should work without requiring an installer.
11. With respect to the GUI, for standard screen resolutions 1920x1080 and higher, and, for screen scales 100% and 125%, there should not be any resolution-related inconveniences to the user.
12. With respect to the GUI, for resolutions 1280x720 and higher, and, for screen scales 150%, all functions can be used even if the user experience is not optimal.
13. The entire application should be packaged into a single JAR file.
14. The file size of the JAR file should be under 100 MB and should not be unnecessarily bloated.
15. PDF files generated for documentation should have file sizes under 15 MB and should not be unnecessarily bloated.
16. The User Guide and the Developer Guide should be PDF-friendly. (Don't use expandable panels, embedded videos, animated GIFs etc.)


### Glossary

* **AAC**: Active Ageing Centre. A recreational centre that supports elderly in the area.
* **Befriending Program**: Program which elderly signs up for to receive support from an AAC.
* **AIC**: Agency for Integrated Care. A statutory board under the Ministry of Health in Singapore.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually. It is preferred to follow the order of the test cases as they are written as some test cases may depend on the state of the app after the previous test case.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Valid NRICs
Here is a list of valid NRICs issued by the Singapore Government that can be used for testing:
* S1486256J
* S0919929B
* S6516486H
* T0500222I
* T0251112B

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder
   2. Double-click the jar file. <br>
        Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.
   2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Adding an elderly
Common prerequisite: List all elderly using the `list` command.

1. Adding an elderly without a tag
   1. Prerequisite: There must **not** be an elderly with NRIC S6516486H in the list.
   2. Test case: `add i/S6516486H n/James Lim p/91234567 e/james.lim@hotmail.com a/432, Clementi East Ave 4, #13-42 c/7`<br>
      Expected: A new elderly is added to the list. The details of the elderly are shown in the list.

2. Adding an elderly with a tag
   1. Prerequisite: There must **not** be an elderly with NRIC S1486256J in the list.
   2. Test case: `add i/S1486256J n/Alice Tan p/98765432 e/alice.tan@gmail.com a/123, Jurong West Ave 6, #08-111 t/wheelchairBound c/7`<br>
      Expected: A new elderly is added to the list. The details of the elderly are shown in the list and the "wheelchairBound" tag is shown.

3. Adding an elderly with a duplicate NRIC
   1. Prerequisite: There must be an elderly with NRIC S6516486H in the list.
   2. Test case: `add i/S6516486H n/Bernard Lim p/98375489 e/b.lim@me.com a/80, Lorong 4 Toa Payoh, #12-34 c/7`<br>
      Expected: No new elderly is added. Error message is shown in the status bar.

4. Adding an elderly with duplicate name, phone number, email, address but different NRIC
   1. Prerequisite: There must be an elderly with `name: Alice Tan, phone number: 98765432, email: alice.tan@gmail.com, address: 123, Jurong West Ave 6, #08-111 & call frequency: 7` in the list.
   2. Test case: `add i/S3198054B n/Alice Tan p/98765432 e/alice.tan@gmail.com a/123, Jurong West Ave 6, #08-111 c/7`<br>
      Expected: Elderly is added but warning that duplicate elderly added is given.

### Editing an elderly's details
Common prerequisite: List all elderly using the `list` command.

1. Editing an elderly details using INDEX
   1. Prerequisite: There must be at least one elderly in the list.
   2. Test case: `edit 1 n/Jamus Lim p/97758933`<br>
   Expected: The elderly at index 1 is updated with the new name (Jamus Lim) and phone number (97758933). The updated details are shown in the list.

2. Editing an elderly details using INDEX but with a used NRIC
   1. Prerequisite: There must be at least one elderly in the list and an elderly with NRIC S1486256J in the list.
   2. Test case: `edit 1 i/S1486256J`<br>
   Expected: The elderly at index 1 fails to update as the NRIC is already in use. Error message is shown in the status bar.
   
3. Editing an elderly details using NRIC
   3. Prerequisite: There must be an elderly with NRIC S1486256J in the list.
   4. Test case: `edit S1486256J a/Lorong 3 Toa Payoh, #12-34`<br>
   Expected: The elderly with NRIC S1486256J is updated with the new address (Lorong 3 Toa Payoh, #12-34). The updated details are shown in the list.
   
4. Editing an elderly details using NRIC but with a non-existent NRIC
   1. Prerequisite: There must **not** be an elderly with NRIC S3916784J in the list.
   2. Test case: `edit S3916784J n/Tan Ah Kow`<br>
   Expected: The elderly with NRIC S3916784J is not found. Error message is shown in the status bar.
   

### Deleting an elderly
Common prerequisite: List all elderly using the `list` command.

1. Deleting an elderly while all elderly are listed in the `personList`.
   1. Prerequisite: There must be at least one elderly in the list.
   2. Test case: `delete 1`<br>
   Expected: First elderly shown in the list is deleted from the list. Details of the deleted elderly are displayed in the status message. Timestamp in the status bar is updated.
   
2. Deleting an elderly with an invalid INDEX
   1. Test case: `delete 0`<br>
   Expected: No elderly is deleted. Error details is displayed in the status message. The list remains the same.

3. Deleting an elderly using NRIC
   1. Prerequisite: There must be an elderly with NRIC S1486256J in the list.
   2. Test case: `delete S1486256J`<br>
   Expected: The elderly with NRIC S1486256J is deleted from the list. Details of the deleted elderly are displayed in the status message.

4. Deleting an elderly with a non-existent NRIC
   1. Prerequisite: There must **not** be an elderly with the NRIC S3916784J in the list.
   2. Test case: `delete S3916784J`<br>
   Expected: No elderly with the NRIC S3916784J is found. The list remains the same.

5. Other incorrect delete commands to try: 
   1. Prerequisite for `delete x`: There must be less than `x` elderly in the list.
   2. Test case: `delete`, `delete x`, `...` (where x is an integer larger than the size of the list)<br>
   Expected: Similar to the previous case.

### Finding an elderly
Common prerequisite: List all elderly using the `list` command.

1. Finding an elderly by NAME
    1. Prerequisite: There must be an elderly with the name `Alice` in the list.
    2. Test case: `find Alice`<br>
       Expected: Elderly with the name `Alice` are shown in the list. Other elderly are hidden.

2. Finding an elderly by NRIC
   1. Prerequisite: There must be an elderly with the NRIC `S1486256J` in the list.
   2. Test case: `find S6516486H`<br>
        Expected: The elderly with the NRIC `S6516486H` is shown in the list. Other elderly are hidden.
   
3. Finding an elderly with a non-existent NAME
   1. Prerequisite: There must **not** be an elderly with the name `John` in the list.
   2. Test case: `find John`<br>
      Expected: No elderly with the name `John` is found. The list is empty.
   
4. Finding an elderly with a non-existent NRIC
   1. Prerequisite: There must **not** be an elderly with the NRIC `S3916784J` in the list.
   2. Test case: `find S3916784J`<br>
      Expected: No elderly with the NRIC `S3916784J` is found. The list is empty.

### Marking an elderly as called
Common prerequisite: List all elderly using the `list` command.

1. Mark an elderly as called by INDEX
   1. Prerequisite: There must be at least one elderly in the list.
   2. Test case: `mark 1`<br>
   Expected: The elderly at index 1 is marked as called. The status message shows the details of the elderly marked as called. Their next call date should be updated.

2. Mark an elderly with an invalid INDEX
   1. Test case: `mark 0`<br>
   Expected: No elderly is marked as called. Error message is shown in the status bar. The list remains the same.

3. Mark an elderly as called by NRIC
   1. Prerequisite: There must be an elderly with NRIC `S1486256J` in the list.
   2. Test case: `mark S1486256J`<br>
      Expected: The elderly with NRIC `S1486256J` is marked as called. The status message shows the details of the elderly marked as called. Their next call date should be updated.

4. Mark an elderly with a non-existent NRIC
   1. Prerequisite: There must **not** be an elderly with the NRIC `S3916784J` in the list.
   2. Test case: `mark S3916784J`<br>
      Expected: No elderly with the NRIC `S3916784J` is marked as call. Error message is shown in the status bar. The list remains the same.

5. Mark an elderly using INDEX/NRIC with notes
   1. Prerequisite: There must be at least one elderly or elderly with NRIC `S1486256J` in the list.
   2. Test case: `mark 1 o/My test note`, `mark S1486256J o/My test note`<br>
      Expected: The elderly at index 1 is marked as called with the note "My test note". The status message shows the details of the elderly marked as called.

6. Other incorrect mark commands to try: 
   1. Prerequisite for `mark x`: There must be less than `x` elderly in the list.
   2. Test case: `mark`, `mark x`, `...` (where x is an integer larger than the size of the list)<br>
   Expected: Similar to the previous case.

### Call history of an elderly
Common prerequisite: List all elderly using the `list` command.

1. Viewing call history of an elderly by INDEX
   1. Prerequisite: There must be at least two elderly in the list.
   2. Test case: `history 2`<br>
      Expected: The list is updated to show the call history and profile view of the elderly at index 2.

2. Viewing call history of an elderly with an invalid INDEX
   1. Test case: `history 0`<br>
      Expected: Error message is shown in the status bar.

3. Viewing call history of an elderly by NRIC
   1. Prerequisite: There must be an elderly with NRIC `S1486256J` in the list.
   2. Test case: `history S1486256J`<br>
      Expected: The list is updated to show the call history and profile view of the elderly with NRIC `S1486256J`. One of the call dates contains the note "My test note".

4. Viewing call history of an elderly with a non-existent NRIC
   1. Prerequisite: There must **not** be an elderly with the NRIC `S3916784J` in the list.
   2. Test case: `history S3916784J`<br>
      Expected: Error message is shown in the status bar.

### Navigating through command history
1. Navigating to previous command
   1. Prerequisite: You have just entered the command `list`.
   2. Test case: Press the `UP` arrow key<br>
      Expected: The previous command (`list`) is shown in the command box.

2. Navigating to next command
   1. Prerequisite: You have entered the `help` command, followed by the `list` command.
   2. Test case: Press the `UP` arrow key twice, followed by the `DOWN` arrow key<br>
      Expected: You would see these commands in the command box, in this order: `list`, `help`, `list`.

### Data management

1. Data is saved after shutdown

   1. Clear data with the `clear` command.
   2. Add a new elderly. Refer to the [adding an elderly](#adding-an-elderly) section.
   3. Close the app.
   4. Re-launch the app.<br>
       Expected: The newly added elderly is still present.

2. Dealing with missing/corrupted data files

   1. Edit the `data/contactmate.json` file to have a person with an empty NRIC `""`.
   2. Launch the app.<br>
       Expected: The app should show an empty list and upon closing the app, the data file should be overwritten with an empty list.

3. Loading sample data

   1. Delete the `data/contactmate.json` file.
   2. Launch the app.<br>
       Expected: The app should show a list of sample persons.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**
Team size: 5

1. **Change validation of email**: Currently, the email field is validated using a simple regex pattern. It might not be able to catch all invalid email addresses, and might reject some valid emails. We plan to change the validation to use a more robust email validation algorithm, based on this [RFC 3696](https://www.rfc-editor.org/rfc/pdfrfc/rfc3696.txt.pdf) standard.
2. **Relax constraint for name field:** The current constraint for the name field is alphanumeric characters and spaces which could be too restrictive if users want to add elderly with names that include other special characters (e.g. `João da Silva` or `Arjun Singh s/o Vijay Singh`). We plan to relax this constraint so that other characters that are commonly used in names are allowed. A non-exhaustive list of characters that will be allowed after this enhancement consists of diacritics (e.g. `ç`, `ñ`, `é`, `ü`), punctuation and symbols (e.g. `-`, `'`, `.`, `/`),  extended Latin characters (e.g. `ø`, `å`, `ð`), Chinese characters (e.g. `陈`, `小`, `明`) and Tamil characters (e.g.`ர`, `ஜ`).
3. **Allow modification to elderly in the history view:** Currently, users can view the call history and profile view of the elderly in the history view together. This allows users to view the details of the elderly, which may give the wrong impression that they can make modifications to the elderly such as performing mark command using NRIC in the history view (e.g. `mark SXXXXXXXH`). However, users are unable to perform any modification to elderly in the history view. We plan to allow users to make modifications to the elderly, only when selecting by the `NRIC` of the elderly that is currently displayed in the history view (`mark NRIC`, `edit NRIC`, etc.).
4. **Maintain the filtered person list view after modification:** Currently, if a user uses the `find` command to filter the person list, and then proceeds to modify the person list (with `add`, `edit`, `mark`  etc.), the person list will update, **but will also reset to show all entries**. We plan to change this behaviour such that the current filtered person list will stay filtered (with the same filter as applied by the `find` command previously), even after the person list is updated after modifications. <br/><br/>
This is an example of the result of the enhancement:
   1. User uses the command `find John`.
   2. Only elderly with `John` in their name is shown in the filtered list.
   3. User uses the command `edit 1 p/99999999`, applying modifications to the person list.
   4. The `John` at `INDEX` 1 is edited AND **the display shown is still the person list filtered by elderly with the name `John`**.
5. **Make email field optional:** Currently, the email field (`e/`) is mandatory when adding an elderly. We plan to make the email field optional so that users can add elderly without an email address. This is useful for elderly who do not have an email address.
<br/><br/>
    After the change, this will be a valid command: `add i/S5087089H n/John Doe p/98765432 a/John street, block 123, #01-01 c/7`.
6. **Allow users to delete a call from the call history:** Currently, users can only add to the call history, using the `mark` command. We plan to allow users to delete a call from the call history. This is useful for users who have made a mistake in marking a call or have marked a call wrongly. The user can delete the call and mark the call again with the correct information. <br/><br/>

    How we plan to implement this is to allow users to use the `delete` command in the history view. The user can specify the index of the call to delete. For example, `delete 1` will delete the first call in the call history. Deleting by `NRIC` (e.g. `delete S1021013E`) will still be disabled in the call history view, except for the case as elaborated on in planned enhancement 3. The `delete` command will remain unchanged, if the user is on the person list view. 
7. **Place cursor at the back of the command text after navigating command history:** Currently, when the user presses the `UP` or `DOWN` arrow key to navigate to the previous/next command, the cursor is placed at the front of the command text. We plan to change this behaviour such that the cursor is placed at the back of the command text after navigating the command history. This will allow users to continue typing the command without having to move the cursor to the back of the command box. This also aligns with the behaviour of most command-line interfaces.
8. **Allow commands using the NRIC field to function independently of the displayed person list:** Currently, commands that use the NRIC field (e.g. `mark S5305394G`) require the elderly with that NRIC (e.g. `S5305394G`) to be in the displayed person list for command to execute. We plan to allow such commands to be executed directly on the full, unfiltered person list (regardless of the current display or filters) to improve efficiency of commands.
9. **Improve error messages:** Currently, the error messages inform users that a command is incorrect. We plan to improve the error messages to list the fields missing from a command. For example, if a user has missing/extra fields in the `add` command, the error message will specify which fields are causing the error.
10. **Improve error message ordering:** When in the history view, if a user enters an invalid command, the invalid command error message is shown. This is still the case if the command is disabled in the history view. We plan to change this behaviour such that the disabled command error message is shown first before the invalid command error message. As a result, the user will always see the disabled command error message (when trying to use disabled commands), even if the command format is wrong.
