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

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI Component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic Component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`, `TagCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`, `TagCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model Component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage Component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

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

### Product Scope

**Target user profile**:

* has a need to manage a significant number (50-100) of wedding guests
* only needs to plan 1 wedding at a time
* prefer desktop apps over other types
* can type fast
* much prefers typing to mouse interactions
* is reasonably comfortable using Command Line Interface (CLI) applications

**Value proposition**:
Reduce the time taken to filter and contact guests, allowing planners to focus more time on other wedding needs.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                             | I want to …​                                      | So that I can…​                                                                           |
|----------|-----------------------------------------------------|---------------------------------------------------|-------------------------------------------------------------------------------------------|
| `* * *`  | wedding planner new to the app                      | see usage instructions and examples               | refer to instructions when I forget how to use the App                                    |
| `* * *`  | wedding planner                                     | add a new person to the guest list                | track the person using the guest list                                                     |
| `* * *`  | wedding planner                                     | delete a person                                   | remove someone that has been uninvited                                                    |
| `* * *`  | wedding planner                                     | view the entire guest list                        | get an view all the guests in my guest list                                               |
| `* * *`  | wedding planner                                     | find a guest by name                              | locate specific guests without having to go through the entire list                       |
| `* * *`  | wedding planner                                     | add customisable tags to guests                   | categorise my guests easily                                                               |
| `* * *`  | wedding planner                                     | delete tags associated with guests                | re-categorise my guests if needed                                                         |
| `* * *`  | wedding planner                                     | view the RSVP status of each guest                | see if each guest is attending, not attending or have not RSVP'ed to the wedding          |
| `* *`    | wedding planner                                     | filter guests according to their RSVP status      | view guests that are attending, not attending or have not RSVP'ed to the wedding only     |
| `* *`    | wedding planner managing different groups of guests | filter guests according to their tags             | view specific groups of guests without having to go through the entire list               |
| `* *`    | wedding planner managing many guests                | tag and untag multiple guests at once             | quickly tag or untag large groups of guests in the guest list                             |
| `* *`    | wedding planner managing many tags                  | tag and untag multiple tags on each guest at once | quickly tag or untag multiple tags onto each guest when required                          |
| `* *`    | wedding planner requiring many tags                 | create and delete multiple tags at once           | quickly create or delete multiple tags used in the guest list                             |
| `*`      | wedding planner flexible to changes                 | edit tags that were already created               | change the description of tags without altering any association to the guests in the list |
| `*`      | wedding planner                                     | apply multiple filters to the list                | view only a specific group of guests                                                      |
| `*`      | wedding planner                                     | have colour coded tags and fields                 | distinguish tags and different fields easily                                              |
| `*`      | wedding planner                                     | see the list of tags already created              | easily tag guests without checking if the tag has been already created                    |

### Use Cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

**Use case 1: Add Guest**

**MSS**

1.  User enters the command to add a guest.
2.  The system prompts the user for guest's name, contact number and email address.
3.  User inputs their name, contact number and email address.
4.  The system validates the input.
5.  The system saves the guest and display confirmation message.

    Use case ends.

**Extensions**

* 3a. User inputs invalid contact number (e.g. invalid spaces, more than 8 integers).
  * 3a1. System displays error message and prompts for contact number.
  * Use case resumes at step 3.


* 3b. User enters duplicate guest name.
    * 3b1. System displays an error message, showing user already exists and prompts for a new name.
    * Use case resumes at step 3.

**Use case 2: Delete Guest**

**MSS**

1.  User enters the command to delete a guest.
2.  The system prompts for guest's index in the list.
3.  User enters the index.
4.  The system verifies the index.
5.  The system deletes the guest and display confirmation message.

    Use case ends.

**Extensions**

* 3a. User inputs invalid index (e.g. index lesser than 1 or greater than list length).
    * 3a1. System displays error message and prompts for valid index.
    * Use case resumes at step 3.

**Use case 3: Find Guest By Name**

**MSS**

1.  User enters the command to find a guest.
2.  The system prompts for the guest's name.
3.  User enters the name.
4.  The system searches for the guest.
5.  The system displays a list of matching guest(s).

    Use case ends.
    
**Extensions**

* 3a. No guests match the keyword
    * 3a1. System displays a message indicating no guest found. 
    * Use case ends.

**Use case 4: Set RSVP status**

**MSS**

1.  User enters the command to set RSVP status.
2.  The system prompts for the guest's index in the list and the index representing the intended RSVP status.
3.  User enters the indexes.
4.  The system verifies the indexes.
5.  The system updates the list and shows confirmation message.

    Use case ends.

**Extensions**

* 3a. User enters an invalid guest or status index (e.g. outside valid range)
    * 3a1. System displays error message and prompt for indexes.
    * Use case resumes at step 3.

**Use case 5: Create new tag(s)**

**MSS**

1.  User enters the command to create new tag(s).
2.  The system prompts for the description of the new tag(s).
3.  User enters the description of the new tag(s).
4.  The system verifies the input.
5.  The system updates the tag list to show the new created tag and shows confirmation message.

    Use case ends.

**Extensions**

* 3a. User enters an invalid description (e.g. containing non-ASCII characters)
    * 3a1. System displays error message and prompt tag description.
    * Use case resumes at step 3.

* 3b. User enters a duplicate tag description (i.e. tag already exists in the tag list)
    * 3b1. System displays a message indicating the tag already exists.
    * Use case ends.

* 3c. Tag list limit has been reached
    * 3c1. System displays a message indicating the tag list limit has been reached.
    * Use case ends.

**Use case 6: Delete tag(s)**

**MSS**

1.  User enters the command to delete tag(s).
2.  The system prompts for the description of the tag(s).
3.  User enters the description of the tag(s).
4.  The system verifies the input.
5.  The system updates the tag list to show that the tag(s) no longer exist and shows confirmation message.

    Use case ends.

**Extensions**

* 3a. User enters an invalid description (e.g. containing non-ASCII characters)
    * 3a1. System displays error message and prompt tag description.
    * Use case resumes at step 3.

* 3b. User enters a description not matching any existing tags (i.e. tag does not exist in the tag list)
    * 3b1. System displays a message indicating the tag does not exist.
    * Use case ends.

**Use case 7: Tag guest(s)**

**MSS**

1.  User enters the command to tag guest(s).
2.  The system prompts for the relevant index(es) in the list and the description(s) of the tag(s).
3.  User enters the index(es) and description(s) of the tag(s).
4.  The system verifies the input.
5.  The system updates the guest list to show the tag(s) on the target guest(s) and shows confirmation message.

    Use case ends.

**Extensions**

* 3a. User enters an invalid description (e.g. containing non-ASCII characters)
    * 3a1. System displays error message and prompt index(es) and tag description(s).
    * Use case resumes at step 3.

* 3b. Tag not in tag list
    * 3b1. System displays a message indicating the tag does not exist in tag list.
    * Use case ends.

* 3c. User enters invalid index(es) (e.g. outside valid range)
    * 3c1. System displays error message and prompt for index(es) and tag description(s).
    * Use case resumes at step 3.

**Use case 8: Untag guest(s)**

**MSS**

1.  User enters the command to untag guest(s).
2.  The system prompts for the relevant index(es) in the list and the description(s) of the tag(s).
3.  User enters the index(es) and description(s) of the tag(s).
4.  The system verifies the input.
5.  The system updates the guest list to show that the tag(s) on the target guest(s) are removed and shows confirmation message.

    Use case ends.

**Extensions**

* 3a. User enters an invalid description (e.g. containing non-ASCII characters)
    * 3a1. System displays error message and prompt index(es) and tag description(s).
    * Use case resumes at step 3.

* 3b. Tag not on guest
    * 3b1. System displays a message indicating the tag does not exist on the guest.
    * Use case ends.

* 3c. User enters invalid index(es) (e.g. outside valid range)
    * 3c1. System displays error message and prompt for index(es) and tag description(s).
    * Use case resumes at step 3.


**Use case 9: Filter guest list by tag or RSVP status**

**MSS**

1.  User enters the command to filter guest list.
2.  The system prompts for the desired predicate (e.g. a RSVP status or tag(s)).
3.  User enters the desired predicate.
4.  The system verifies the input.
5.  The system shows the list of guests matching the predicate and shows confirmation message.

    Use case ends.

**Extensions**

* 3a. User enters an invalid index representing the RSVP status
    * 3a1. System displays error message indicating invalid index and prompts for the desired predicate.
    * Use case resumes at step 3.

* 3b. User enters an invalid tag
    * 3b1. System displays error message indicating the tag does not exist in tag list and prompts for the desired predicate.
    * Use case resumes at step 3.

* 3c. User enters more than one index
    * 3c1. System displays error message indicating multiple fields and prompt for the desired predicate.
    * Use case resumes at step 3.


### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 100 guests without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Should only be able to support one wedding event at a time.
5.  Should load the list of guests in under 3 seconds, even when _filtering_ the list.
6.  GUI should be intuitive and easy to learn for wedding planners to manage the guest list.
7.  Guest list should be saved and updated throughout uses.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **GUI**: Graphical User Interface allows users to interact with the address book codebase through visual representation
* **CLI**: Command Line Interface accepts text input to execute functions of the application
* **JAR file**: A package file that contains many class files, associated metadata and resources that build the application
* **RSVP status**: A field on each guest that indicates if the guest has responded, attending or not attending the wedding, which can be used for filtering purposes
* **Customisable tags**: Tags on guests containing text written by user, which can be used for filtering purposes
* **Tag list**: A list of tags that can be used to classify guests
* **Delete**: Removes a guest and all details regarding the guest from the address book
* **Add**: Creates and saves a new guest into the address book
* **Filter**: Only show guests that contains the text input by the user
* **New Tag**: Creates a new tag and adds it to the tag list
* **Delete Tag**: Removes a tag from the tag list and all instances of it on all guests in the address book
* **Remove Tag**: Removes a tag from guest(s) but does not remove it from the tag list or other guests not specified

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
