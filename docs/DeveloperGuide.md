---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# TrueRental Developer Guide

<!-- * Table of Contents -->
<a id="table-of-contents"/><page-nav-print />

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

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("cdelete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `cdelete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteClientCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("cadd n/John Doe p/91231231 e/john@example.com")` API call as an example.

<puml src="diagrams/AddClientSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `cadd n/John Doe p/91231231 e/john@example.com` Command" />

<box type="info" seamless>

**Note:** The lifeline for `AddClientCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteClientCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteClientCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a [_client_](#glossary-client)).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteClientCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

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

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `cdelete 5` command to delete the 5th [_client_](#glossary-client) in the address book. The `cdelete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `cdelete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new [_client_](#glossary-client). The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the [_client_](#glossary-client) was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `cdelete`, just save the [_client_](#glossary-client) being deleted).
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

**Target user profile**:

* are [_letting agents_](#glossary-letting-agent)
* has a need to manage a significant number of [_client_](#glossary-client)'s personal and [_rental information_](#glossary-rental-information)
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: manage many [_client_](#glossary-client)'s personal and [_rental information_](#glossary-rental-information), such as name, phone number, address, rental start date, rental end date, monthly rent amount, etc.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                 | I can …​                                       | So that I can…​                                                         |
|----------|----------------------------|---------------------------------------------------|----------------------------------------------------------------------------|
| `* * *`  | user                       | save a _client_'s personal information            | contact them easily                                                        |
| `* * *`  | user                       | save a _client_'s _rental information_            | view their respective properties                                           |
| `* * *`  | user                       | edit a _client_'s personal information            | modify their personal details                                              |
| `* * *`  | user                       | edit a _client_'s _rental information_            | modify their property's _rental information_                               |
| `* * * ` | user                       | delete a _client_'s personal information          | clear my application when he/she is no longer my _client_                  |
| `* * *`  | user                       | delete a _client_'s _rental information_          | clear my application when the property is not owned by my _client_ anymore |
| `* * *`  | user                       | find a _client_'s personal information            | find the _client_ easily                                                   |
| `* * *`  | user                       | find a _client_'s _rental information_            | find the _client_'s property easily                                        |
| `* *`    | user                       | colour code a _client_                            | differentiate more important _clients_                                     |
| `* *`    | user                       | attach files to a _client_                        | attach important contracts to the respective _clients_                     |
| `* *`    | user                       | assign tags to _clients_                          | differentiate _clients_ by any interesting factors                         |
| `* *`    | user                       | autofill CLI commands                             | easily assess the command line without typing the command again            |
| `* *`    | user                       | export all _client_'s personal information        | save it somewhere else                                                     |
| `* *`    | user                       | export a specific _client_'s _rental information_ | save it somewhere else                                                     |
| `*`      | user                       | send emails to a _client_                         | schedule meetings with them                                                |
| `*`      | user                       | set reminders for a _client_                      | remember my schedule with individual _client_                              |
| `*`      | user                       | lock my application                               | protect my data                                                            |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `TrueRental` system and the **Actor** is the `user`, unless specified otherwise)

**Use case: Add a [_client_](#glossary-client)**

**MSS**

1.  User chooses to add a _client_
2.  User enters _client_'s information
3.  System validates user input
4.  System adds new _client_ information
5.  System notifies user upon successful add operation

    Use case ends.

**Extensions**

* 3a. System detects error for invalid instruction

    * 3a1. System prompts error for invalid instruction
    * 3a2. User enters new instruction
    * Steps 3a1-3a2 are repeated until instruction is valid

    Use case continues from step 3.

* 3b. System detects error in _client_'s information

    * 3b1. System prompts error for invalid _client_'s information
    * 3b2. User enters new _client_'s information
    * Steps 3b1-3b2 are repeated until _client_'s information is valid

    Use case continues from step 3.

* 3c. System detects duplicated _client_'s information

    * 3c1. System prompts error for duplicated _client_'s information
    * 3c2. User enters new non-duplicated _client_'s information
    * Steps 3c1-3c2 are repeated until _client_'s information is valid

    Use case continues from step 4.

* *a. At any time, user chooses not proceed on with the operation.

    Use case ends.

**Use case: Add a [_client_](#glossary-client)'s [_rental information_](#glossary-rental-information)**

**MSS**

1.  User chooses to add _rental information_
2.  User selects _client_
3.  User enters _client_'s _rental information_
4.  System validates user input
5.  System updates new _client_ information
6.  System notifies user upon successful add operation

    Use case ends.

**Extensions**

* 4a. System detects error for invalid instruction

    * 4a1. System prompts error for invalid instruction
    * 4a2. User enters new instruction
    * Steps 4a1-4a2 are repeated until instruction is valid

    Use case continues from step 4.

* 4b. System detects error for invalid _client_

    * 4b1. System prompts error for invalid _client_
    * 4b2. User selects new _client_
    * Steps 4b1-4b2 are repeated until selected _client_ is valid

    Use case continues from step 4.

* 4c. System detects error in _client_'s _rental information_

    * 4c1. System prompts error for invalid _client_'s _rental information_
    * 4c2. User enters new _client_'s _rental information_
    * Steps 4c1-4c2 are repeated until _client_'s _rental information_ is valid

    Use case continues from step 4.

* 4d. System detects duplicated _client_'s _rental information_

    * 4d1. System prompts error for duplicated _client_'s _rental information_
    * 4d2. User enters new non-duplicated _client_'s _rental information_
    * Steps 4d1-4d2 are repeated until _client_'s _rental information_ is valid

    Use case continues from step 5.

* *a. At any time, user chooses not proceed on with the operation.

  Use case ends.

**Use case: Find a [_client_](#glossary-client)**

**MSS**

1.  User chooses to find a _client_
2.  User enters keyword
3.  System validates user input
4.  System filters list of _client_ based on keyword

    Use case ends.

**Extensions**

* 3a. System detects error for invalid instruction

    * 3a1. System prompts error for invalid instruction
    * 3a2. User enters new instruction
    * Steps 3a1-3a2 are repeated until instruction is valid

    Use case continues from step 3.

* 3b. System detects error for invalid keyword

    * 3b1. System prompts error for invalid keyword
    * 3b2. User enters new keyword
    * Steps 3b1-3b2 are repeated until keyword is valid

    Use case continues from step 4.

* 4a. The list is empty.

    Use case ends.

* *a. At any time, user chooses not proceed on with the operation.

  Use case ends.

**Use case: Edit a [_client_](#glossary-client)'s information**

**MSS**

1.  User chooses to edit a _client_'s information.
2.  User enters the _client_ information that he / she wants to update.
3.  System validates user input.
4.  System updates the _client_'s information as requested.
5.  System notifies user for successful modification.

    Use case ends.

**Extensions**

* 3a. System detects error for invalid instruction.

    * 3a1. System prompts error for invalid instruction.
    * 3a2. User enters new instruction.
    * Steps 3a1-3a2 are repeated until the instruction is valid.

    Use case resumes from step 3.

* 3b. System detects error for invalid _client_ information.

    * 3b1. System prompts error for invalid _client_ information.
    * 3b2. User enters new _client_ information.
    * Steps 3b1-3b2 are repeated until the _client_ information is valid.

    Use case resumes from step 3.

* 4a. System fails to update the _client_'s information.

    * 4a1. System prompts user that edit has failed.

    Use case resumes from step 1 or user choose not to proceed and use case ends.

* *a. At any time, User chooses not to proceed with the operation.

    Use case ends.

**Use case: Edit a [_client_](#glossary-client)'s [_rental information_](#glossary-rental-information)**

**MSS**

1.  User chooses to edit a _client_'s _rental information_.
2.  User enters the _rental information_ that he / she wants to update.
3.  System validates user input.
4.  System updates the _client_'s _rental information_ as requested.
5.  System notifies user for successful modification.

    Use case ends.

**Extensions**

* 3a. System detects error for invalid instruction.

    * 3a1. System prompts error for invalid instruction.
    * 3a2. User enters new instruction.
    * Steps 3a1-3a2 are repeated until the instruction is valid.

    Use case resumes from step 3.

* 3b. System detects error for invalid _rental information_.

    * 3b1. System prompts error for invalid _rental information_.
    * 3b2. User enters new _rental information_.
    * Steps 3b1-3b2 are repeated until the _rental information_ is valid.
  
    Use case resumes from step 3.

* 4a. System fails to update the _client_'s _rental information_.

    * 4a1. System prompts user that edit has failed.

    Use case resumes from step 1 or user choose not to proceed and use case ends.

* *a. At any time, User chooses not to proceed with the operation.

    Use case ends.


**Use case: Delete a [_client_](#glossary-client)**

**MSS**

1.  User chooses to delete a _client_ and all related _rental information_
2.  User types in a command consisting the index of the _client_
3.  System prompts the user for confirmation
4.  User confirms the deletion
5.  System deletes that _client_ and all related _rental information_

    Use case ends.

**Extensions**

* 2a. The provided index is not valid

    * 2a1. System prompts error for invalid index
    * 2a2. User enters new instruction
    * Steps 2a1-2a2 are repeated until the instruction is valid

  Use case resumes from step 3.

* 4a. User cancels the deletion

  Use case ends.


**Use case: Delete a [_rental information_](#glossary-rental-information) from a [_client_](#glossary-client)**

**MSS**

1.  User chooses to delete a specific _rental information_ from a _client_
2.  User types in a command consisting the index of the _client_ and _rental information_
3.  System prompts the user for confirmation
4.  User confirms the deletion
5.  System deletes that _rental information_

    Use case ends.

**Extensions**

* 2a. The provided index is not valid

    * 2a1. System prompts error for invalid index
    * 2a2. User enters new instruction
    * Steps 2a1-2a2 are repeated until the instruction is valid

  Use case resumes from step 3.

* 4a. User cancels the deletion

  Use case ends.


*{More to be added}*

### Non-Functional Requirements

1.  Should work on any [_mainstream OS_](#glossary-mainstream-os) as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 [_clients_](#glossary-client) without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  A user should be able to see clearly with reasonably large texts.
5.  The user interface should be simple, functional, and visually inoffensive to the majority of users.
6.  A new user should be able to view information intuitively, even if they do not know the commands used to perform tasks.
7.  Should be able to type up to 2000 characters without a noticeable lag.

*{More to be added}*

### Glossary

* <a id="glossary-mainstream-os"/>**Mainstream OS**: Windows, Linux, Unix, MacOS
* <a id="glossary-private-contact-detail"/>**Private contact detail**: A contact detail that is not meant to be shared with others
* <a id="glossary-user"/>**User**: A letting agent that is using TrueRental to manage the contact information of their clients
* <a id="glossary-client"/>**Client**: An individual that is renting a property from a letting agent
* <a id="glossary-letting-agent"/>**Letting agent**: An individual that facilitates a property rental agreement
* <a id="glossary-system"/>**System**: TrueRental desktop application
* <a id="glossary-clients-information"/>**Client's information**: A client's information containing name, phone number and email, not meant to be shared with others.
* <a id="glossary-rental-information"/>**Client's rental information**: A client's rental information containing address, rental start date, rental end date, rent due date, monthly rent amount, deposit amount, tenant list, not meant to be shared with others.
* <a id="glossary-mss"/>**MSS**: Main Success Scenario.

[Back to top](#table-of-contents)

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

### Deleting a [_client_](#glossary-client)

1. Deleting a _client_ while all _clients_ are being shown

   1. Prerequisites: List all _clients_ using the `list` command. Multiple _clients_ in the list.

   1. Test case: `cdelete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `cdelete 0`<br>
      Expected: No _client_ is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `cdelete`, `cdelete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
