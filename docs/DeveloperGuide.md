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

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete n/Alex`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

The four main components (also shown in the diagram above, staticContext is not a main component),

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

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

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

Step 2. The user executes `delete n/Alex` command to delete the person named Alex in the address book. The `delete` command calls `StaticContext#setPersonToDelete(person)`, storing the person to be deleted in a static context. The user then confirms the deletion with `delete-y`, causing the person to be removed from the address book and the modified state to be saved.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

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

**Target user profile**:

* Wedding planners who organise multiple weddings concurrently
* has a need to manage a significant number of contacts with various roles
* need to categorise all stakeholders in a wedding efficiently
* can type fast and prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Manage contacts of all stakeholders of multiple weddings with ease and convenience


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​         | I want to …​                                                           | So that I can…​                                                              |
|----------|-----------------|------------------------------------------------------------------------|------------------------------------------------------------------------------|
| `* * *`  | Wedding Planner | Add contact details                                                    | I can find contacts easily                                                   |
| `* * *`  | Wedding Planner | Delete contacts                                                        | I can keep information organized.                                            |
| `* * *`  | Wedding Planner | List my contacts easily                                                | I can be view my clients and wedding vendors                                 |
| `* * *`  | Wedding Planner | Tag my contacts to a wedding                                           | I can be categories relevant stakeholders of a wedding together              |
| `* *`    | Wedding Planner | Filter based on location                                               | Depending on the wedding venue, which is more suitable                       |
| `* *`    | Wedding Planner | List the weddings I am planning                                        | I can access relevant information about particular wedding easily            |
| `* *`    | Wedding Planner | Edit contact details                                                   | I can keep information as up-to-date and accurate as possible.               |
| `* *`    | Wedding Planner | Search for contacts by name, tags, or number                           | I can quickly find the contact I need                                        |
| `* *`    | Wedding Planner | Store different methods of contact (phone number, email address, etc.) | I can reach them in more than one way                                        |
| `* *`    | Wedding Planner | Tag contacts with custom labels                                        | I can categorize vendors based on unique criteria for my clients.            |
| `* *`    | Wedding Planner | Filter based on type of contractor                                     | It is easier to find the right people for the right job.                     |
| `* *`    | Wedding Planner | Create an event for each wedding                                       | I can manage all details specific to that event.                             |
| `*`      | Wedding Planner | Show my clients all the contacts they need to know about the wedding   | My clients don't need to worry about finding the contacts themselves         |
| `*`      | Wedding Planner | Assign contacts to specific events                                     | I can keep track of all parties involved in a wedding                        |
| `*`      | Wedding Planner | Create and manage guest lists for each wedding                         | I can track RSVPs and dietary preferences                                    |
| `*`      | Wedding Planner | Track vendor bookings for each wedding                                 | I can ensure all necessary services are confirmed                            |
| `*`      | Wedding Planner | Rate or review vendors after each event                                | I can assess their performance for future recommendations                    |
| `*`      | Wedding Planner | Separate my contacts into categories                                   | I can locate their contact easily and boost my efficiency                    |
| `*`      | Wedding Planner | Export guest lists and vendor details                                  | I can share them with clients or print them for use during events            |
| `*`      | Wedding Planner | Set reminders to contact specific vendors/clients                      | I can correspond with them on time without missing any important deadlines.  |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `KnottyPlanner` and the **Actor** is the `User`, unless specified otherwise)


**Use case: UC01 - Delete a contact**

**MSS**

1.  User requests to list contacts
2.  KnottyPlanner shows a list of contacts
3.  User requests to delete a specific contact in the list and receives a confirmation prompt
4.  KnottyPlanner deletes the contact

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given contact name is invalid.

    * 3a1. KnottyPlanner shows an error message.

      Use case resumes at step 2.

**Use case: UC02 - Add a tag to a contact**

**MSS**

1.  User requests to list contact
2.  KnottyPlaner shows a list of contact
3.  User requests to add a wedding tag to a specific contact in the list
4.  KnottyPlanner adds the wedding tag to that contact

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given name is invalid.

    * 3a1. KnottyPlanner shows an error message.

      Use case resumes at step 2.

**Use case: UC03 - Delete a tag from a contact**

**MSS**

1.  User requests to list contacts
2.  KnottyPlanner shows a list of contacts
3.  User requests to delete a tag from a specific contact in the list
4.  KnottyPlanner deletes the tag from that contact

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given name is invalid.

    * 3a1. KnottyPlanner shows an error message.

      Use case resumes at step 2.

**Use case: UC04 - View all event tags for a contact**

**MSS**

1.  User requests to list contacts
2.  KnottyPlanner shows a list of contacts
3.  User requests to view all tags for a specific contact
4.  KnottyPlanner shows all tags for that contact

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given name is invalid.

    * 3a1. KnottyPlanner shows an error message.

      Use case resumes at step 2.

**Use case: UC05 - Search for a specific contact**

**MSS**

1. User requests to list contacts
2. KnottyPlanner shows a list of contacts
3. User requests to search for contacts using a specific criteria
4. KnottyPlanner shows a list of contacts that match the criteria

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given criterion is invalid.

    * 3a1. KnottyPlanner shows an error message.

      Use case resumes at step 2.

* 4a. The list is empty.

  Use case ends.

**Use case: UC06 - Edit a contact's details**

**MSS**

1. User requests to list contacts
2. KnottyPlanner shows a list of contacts
3. User requests to edit details of a specific contact
4. KnottyPlanner updates the details for that contact

   Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given contact name is invalid.

    * 3a1. KnottyPlanner shows an error message.

      Use case resumes at step 2.

* 3b. The new contact details given are invalid.

  * 3b1. KnottyPlanner shows an error message.

    Use case resumes at step 2.


### Non-Functional Requirements

1.  The system should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  The system should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using keyboard commands than using the mouse.
4.  The system should return a response to commands that are inputted by the user within three seconds.
5.  The codebase should be compliant with AB3 architecture and logic to faclilitate easier testing and debugging.
6.  The system should be operational and responsive 24/7.

### Glossary

* **Contact**: An individual or organization associated with wedding planning, such as a client or vendor.
* **Vendor:**: A service provider for weddings, e.g., caterers, florists, or photographers.
* **Event Tag:** A label assigned to a contact to associate them with a specific wedding event, can be assigned to
multiple contacts involved in the same wedding event.
* **Priority:** The relative importance of a user story or task, often categorized as must-have, should-have,
or could-have.
* **Success/Failure Messages:** Feedback provided to the user indicating the outcome of a command
(e.g., contact successfully added or an error message).

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


   1. Test case: `delete n/Alex` followed by `delete-y`<br>
      Expected: Confirmation Prompt with Alex's details shown. Alex is  then deleted.
   1. Test case: `delete n/Jonus` followed by `delete-n`<br>
      Expected: No person is deleted.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
