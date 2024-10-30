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

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

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
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
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


### Storage component

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

### Find feature

#### Implementation

The find command allows the user to filter the list of contacts by (at least one) criteria applied to data field(s) (`Name`, `Email`, `Gender`, `Age`, `Detail` and/or `StudyGroupTags`). Each field correspond to one criteria, and can consist of one or more keywords. The list of persons in the `AddressBook` is filtered, then displayed to the user.

<div markdown="span" class="alert alert-info">:information_source: **Note:** `n/Alice Bob` is a single criteria, while `n/Alice e/@example.com` is two criteria.

</div>

The user executes an find command with some inputs, for e.g. `find n/Alex Bernice t/1A`, which goes through the `Logic` component as shown by the following sequence diagram:

![FindSequenceDiagram](images/FindSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `FindCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

For each criteria, a `Predicate` is initialised in the `Model` component as shown below:

![FindSequenceDiagram](images/FindSequenceDiagram-PredicateModel.png)

The `PredicateGroup` consist of one or more `Predicate`. The following sequence diagram shows how a `PredicateGroup` is used to test a `Person` record:

![PredicateGroupSequenceDiagram](images/PredicateGroupSequenceDiagram.png)

#### Design Considerations:

**Aspect: Handling multiple criteria**<br>
Criteria are segregated by data field, and each criteria is independently evaluated.

* **Alternative 1 (current choice):**  For a person to be included, they must _satisfy all criteria_.
  * Pros: Allow the find command to filter very specific criteria that must all be fulfilled, enabling more accurate results.
  * Cons: May return fewer results, especially when using multiple criteria.

* **Alternative 2:** For a person to be included, they only need to _satisfy one criteria_.
  * Pros: Returns wider range of results, potentially finding more relevant person records.
  * Cons: Less specific when finding for exact match.

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

### Product scope

**Target user profile**:

Researchers who,
* have a need to manage a significant number of study participants
* need an easy way to contact different experimental groups
* need quick access to study participants' information
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using *CLI* apps

**Value proposition**:

As researchers have to handle large groups of participants across multiple studies, `ResearchRoster` allows them to
* have all participants consolidated in a single program
* consolidate a list of contact details based on **specific experimental criteria**
* export it to an easy-to-read format for better data organization

Thus, *ResearchRoster* allows researchers to save time, effort and energy whilst keeping their participant data well-organised.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​     | I want to …​                                                                               | So that I can …​                                                                     |
|----------|-------------|--------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|
| `* * *`  | user        | add a new person                                                                           | store a new person in my contact list                                                |
| `* * *`  | user        | add details to contacts                                                                    | store details of people in my contact list                                           |
| `* * *`  | researcher  | add multiple tags to participants                                                          | tag contacts to multiple study groups                                                |
| `* * *`  | user        | delete contacts                                                                            | remove old/ contacts that I no longer need                                           |
| `* *`    | user        | save my contact list                                                                       | keep my contacts between sessions                                                    |
| `*`      | user        | exit the program                                                                           | clear up my processes                                                                |
| `* * *`  | user        | use a program that is fast                                                                 | retrieve information quickly                                                         |
| `* * *`  | user        | work on a clean, user-friendly *UI*                                                        | navigate the platform with ease                                                      |
| `* * *`  | user        | list all contacts                                                                          | view my list of contacts                                                             |
| `* * *`  | user        | see usage instructions                                                                     | refer to instructions when I forget how to use the app                               |
| `* *`    | user        | be given a prompt on what format to enter details                                          | easily use commands without having to memorise the accepted format for the CLI entry |
| `* *`    | user        | edit contacts                                                                              | update details of my contacts                                                        |
| `* * *`  | researcher  | update participant information in bulk                                                     | quickly make changes to large groups of participants                                 |
| `* *`    | researcher  | archive participants who are no longer active                                              | keep my current participant list uncluttered                                         |
| `* *`    | user        | clear all entries                                                                          | efficiently restart my progress                                                      |
| `* * *`  | user        | search contacts by name                                                                    | locate contacts without having to go through the entire list                         |
| `* * *`  | researcher  | search for participants based on study                                                     | quickly locate specific groups of participants                                       |
| `* * *`  | researcher  | tag participants with specific attributes (e.g., age, gender, study criteria)              | quickly filter and sort participants                                                 |
| `* * *`  | researcher  | change tag details                                                                         | update the contacts with experimental details                                        |
| `* * *`  | user        | filter contacts by tags                                                                    | view my contacts by specific groups                                                  |
| `* * *`  | user        | sort my contact list by name                                                               | locate a person easily                                                               |
| `* * *`  | researcher  | categorize participants by their participation status (e.g., active, completed, withdrawn) | easily manage ongoing studies                                                        |
| `* *`    | user        | hide private contact details                                                               | prevent others from viewing them without permission                                  |
| `* *`    | researcher  | export contacts emails (or other details) into easy to copy-paste format                   | copy the details (like emails) into other places easily                              |
| `* *`    | researcher  | anonymize participant data when exporting or sharing                                       | ensure participant confidentiality and legal compliance                              |
| `* *`    | researcher  | assign participants randomly into sample groups                                            | easily obtain samples for experiments                                                |
| `* *`    | researcher  | get reminders about upcoming sessions                                                      | be reminded of the time without manually noting it down                              |
| `* *`    | researcher  | receive reminders when participant data is missing or needs updates                        | keep participant records thorough and current                                        |
| `* *`    | researcher  | track the progress and completion of tasks related to participants                         | ensure all administrative tasks are completed                                        |
| `* *`    | user        | undo the most recent command                                                               | easily undo commands if I key in the wrong information                               |

### Use cases

(For all use cases below, the **System** is the `ResearchRoster` and the **Actor** is the `user/researcher`, unless specified otherwise.)

#### **Use Case: UC01 - Add a new person**
* *Preconditions:* -
* *Guarantees:* new person record in the system

**MSS**

1.  User requests to add a new person with provided details.
2.  ResearchRoster adds a new person.

    Use case ends.

**Extension**

* 1a. The necessary details are missing from the given input.

    * 1a1. ResearchRoster shows an error message.

      Use case restarts.


#### **Use case: UC02 - Delete a person**
* *Preconditions:* user has added person(s) previously
* *Guarantees:* person to delete is removed from the system

**MSS**

1.  User requests a list of persons ([UC05](#use-case-uc05---list-all-persons), [UC06](#use-case-uc06---find-persons-by-criteria)).
2.  ResearchRoster shows the list of persons.
3.  User requests to delete a specific person in the list.
4.  ResearchRoster deletes the person.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. ResearchRoster shows an error message.

      Use case resumes at step 2.


#### **Use Case: UC03 - Edit a person's record**
* *Preconditions:* user has added person(s) previously
* *Guarantees:* person record is edited on the system

**MSS**

1.  User requests a list of persons ([UC05](#use-case-uc05---list-all-persons), [UC06](#use-case-uc06---find-persons-by-criteria)).
2.  ResearchRoster shows the list of persons.
3.  User requests to edit a person's record with provided details.
4.  ResearchRoster edits the person's record.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. ResearchRoster shows an error message.

      Use case resumes at step 2.

* 3b. The changes to make are missing from the given input.

    * 3b1. ResearchRoster shows an error message.

      Use case resumes at step 2.


#### **Use Case: UC04 - Edit records in bulk**
* *Preconditions:* user has added person(s) previously
* *Guarantees:* person record(s) is/are edited on the system

**MSS**

1.  User requests a filtered list of persons ([UC06](#use-case-uc06---find-persons-by-criteria)).
2.  ResearchRoster shows the filtered list of persons.
3.  User requests to apply a change to all records in the list.
4.  ResearchRoster applies the change to all records in the list.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given input is invalid.

    * 3a1. ResearchRoster shows an error message.

      Use case resumes at step 2.


#### **Use Case: UC05 - List all persons**
* *Preconditions:* user has added person(s) previously
* *Guarantees:* list of person record(s) is shown

**MSS**

1.  User requests to list persons.
2.  ResearchRoster shows the list of persons.

    Use case ends.

**Extensions**

* 2a. No existing person records.

    * 2a1. ResearchRoster shows a message indicating the list is empty.
    * 2a2. ResearchRoster shows an empty list.

      Use case ends.


#### **Use Case: UC06 - Find persons by criteria**
* *Preconditions:* user has added person(s) previously
* *Guarantees:* list of person record(s) that match the criteria is shown

**MSS**

1.  User requests to list persons that match the criteria.
2.  ResearchRoster shows the list of persons.

    Use case ends.

**Extensions**

* 1a. The criteria is missing from the given input.

    * 1a1. ResearchRoster shows a message indicating the filtered list is empty.
    * 1a2. ResearchRoster shows an empty filtered list.

      Use case restarts.

* 2a. No person records that matches the criteria.

    * 2a1. ResearchRoster shows an error message.

      Use case ends.


#### **Use Case: UC07 - Add study group tag(s) to person**
* *Preconditions:* user has added person(s) previously
* *Guarantees:* study group tag(s) is/are added to person record

**MSS**

1.  User requests a list of persons ([UC05](#use-case-uc05---list-all-persons), [UC06](#use-case-uc06---find-persons-by-criteria)).
2.  ResearchRoster shows the list of persons.
3.  User requests to add study group tag(s) to a person's record.
4.  ResearchRoster adds the tag(s) to the person's record.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. ResearchRoster shows an error message.

      Use case resumes at step 2.

* 3b. The tag(s) to add are missing from the given input.

    * 3b1. ResearchRoster shows an error message.

      Use case resumes at step 2.


#### **Use Case: UC08 - Remove study group tag(s) from person**
* *Preconditions:* user has added person(s) previously, user has added study group tag(s) to person record previously
* *Guarantees:* study group tag(s) is/are removed from person record

**MSS**

1.  User requests a list of persons ([UC05](#use-case-uc05---list-all-persons), [UC06](#use-case-uc06---find-persons-by-criteria)).
2.  ResearchRoster shows the list of persons.
3.  User requests to remove study group tag(s) from a person's record.
4.  ResearchRoster removes the tag(s) from the person's record.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. ResearchRoster shows an error message.

      Use case resumes at step 2.

* 3b. The tag(s) to remove are missing from the given input.

    * 3b1. ResearchRoster shows an error message.

      Use case resumes at step 2.


#### **Use Case: UC09 - Add progress status to person record**
* *Preconditions:* user has added person(s) previously
* *Guarantees:* progress status is added to person record

**MSS**

1.  User requests a list of persons ([UC05](#use-case-uc05---list-all-persons), [UC06](#use-case-uc06---find-persons-by-criteria)).
2.  ResearchRoster shows the list of persons.
3.  User requests to add progress status to a person's record.
4.  ResearchRoster adds the status to the person's record.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. ResearchRoster shows an error message.

      Use case resumes at step 2.

* 3b. The given status is invalid.

    * 3b1. ResearchRoster shows an error message.

      Use case resumes at step 2.


#### **Use Case: UC10 - Export contacts of list**
* *Preconditions:* user has added person(s) with email previously
* *Guarantees:* a text document with list of contacts in plain text

**MSS**

1.  User requests a list of persons ([UC05](#use-case-uc05---list-all-persons), [UC06](#use-case-uc06---find-persons-by-criteria)).
2.  ResearchRoster shows the list of persons.
3.  User requests to export contacts of persons in the list.
4.  ResearchRoster adds the list of persons' contacts to a text file for the user.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 4a. ResearchRoster is unable to write to the text file.

    * 4a1. ResearchRoster shows an error message.

      Use case resumes at step 2.


#### **Use Case: UC11 - Assign sample groups**
* *Preconditions:* user has added person(s) previously
* *Guarantees:* study group tags are randomly added to person records based on study parameters

**MSS**

1.  User requests a list of persons ([UC05](#use-case-uc05---list-all-persons), [UC06](#use-case-uc06---find-persons-by-criteria)).
2.  ResearchRoster shows the list of persons.
3.  User requests to randomly assign the list of persons into sample groups.
4.  ResearchRoster assigns the list of persons into sample groups by adding study group tags to each record.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The study parameters are missing from the given input.

    * 3a1. ResearchRoster shows an error message.

      Use case resumes at step 2.


#### **Use Case: UC12 - Undo last action**
* *Preconditions:* user has performed an action
* *Guarantees:* system state is restored to before last action performed

**MSS**

1.  User requests to undo the last action.
2.  ResearchRoster restores records to before the last action.

    Use case ends.


#### **Use Case: UC13 - Create a session reminder**
* *Preconditions:* -
* *Guarantees:* a reminder is set and triggered before the session

**MSS**

1.  User requests to create a session reminder.
2.  ResearchRoster shows a message confirming reminder is set.
2.  ResearchRoster prompts the user accordingly before the session.

    Use case ends.

**Extensions**

* 1a. The session details are missing from the given input.

    * 1a1. ResearchRoster shows an error message.

      Use case restarts.

* *a. At any time, User chooses to cancels the session reminder.

    * *a1. ResearchRoster shows a message confirming reminder is cancelled.

      Use case ends.


#### **Use Case: UC14 - Track study progress**
* *Preconditions:* user has added progress status to person(s) record previously
* *Guarantees:* progress status for study is shown

**MSS**

1.  User requests to track progress for a study.
2.  ResearchRoster shows the progress status of persons in the study.

    Use case ends.

**Extensions**

* 1a. The given input is invalid.

    * 1a1. ResearchRoster shows an error message.

      Use case restarts.


#### **Use Case: UC15 - Archive old records**
* *Preconditions:* user has added person(s) previously
* *Guarantees:* person records are archived

**MSS**

1.  User requests a list of persons ([UC05](#use-case-uc05---list-all-persons), [UC06](#use-case-uc06---find-persons-by-criteria)).
2.  ResearchRoster shows the list of persons.
3.  User requests to archive a person's record.
4.  ResearchRoster archives the person's record.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. ResearchRoster shows an error message.

      Use case resumes at step 2.


### Non-Functional Requirements

#### General requirements

* The software should be offered as a free product/service.
* The source code should be open source.

#### Constraints

* The data should be stored locally in a human-editable text file (allowing advanced users to modify data directly).
* The software should have minimal reliance on network connectivity and include fallback mechanisms for any network-dependent features.
* The software should not depend on the developer’s remote server.
* The software should be distributed as a single `.jar` file. If that is not feasible, the `.jar` file and any required files should be packaged into a single `.zip` file.
* The software should not exceed 100 MB in size, and document files (i.e., `PDF`s of documentation) should not exceed 15 MB each. Neither should be unnecessarily bloated.
* Any third-party frameworks, libraries or services used should be free, open-source (except for services) and permissively licensed. They should not require users to install additional software or create accounts.
* The developer and user guides should be `PDF`-friendly (by avoiding expandable panels, embedded videos, animated `GIF`s etc.).

#### Quality

* The software should be optimized for the target users: fast typists should be able to complete most tasks more efficiently using the _CLI_ than a _GUI_.
* The software should be intuitive and easy for first-time users to navigate (by providing sample data, a `help` command to access the user guide etc.).
* Multistep commands for onboarding (if any) should have a streamlined, one-step equivalent for regular or expert users.
* The _GUI_ should be intuitive for users who are not tech-savvy.

#### Performance and Efficiency

* The software should respond to user actions within 5 seconds.

#### Scalability

* The software should be able to accommodate up to 5,000 contacts, without a noticeable sluggishness in performance for typical usage.

#### Reliability

* The software should maintain stable operation (i.e., function as intended) at least 99.9% of the time.

#### Robustness

* **Disaster Recovery and Fault Tolerance:** The software should gracefully handle exceptional events (e.g., errors or failures) without losing data or compromising functionality (e.g., crashing).
* **Error Handling:** Clear, informative error messages should be provided, and errors logged to aid troubleshooting and support.

#### Compliance

* The software should protect research participants' confidentiality and comply with relevant legal standards (by safeguarding _private contact details_).

#### Portability

* The software should work without requiring an installer.
* The software should run on any _mainstream OS_ that has `Java 17` (and no other Java version installed).
* The _GUI_ should be free from resolution-related inconveniences for standard screen resolutions (1920x1080 or higher) and screen scales (100% and 125%).
* The _GUI_ should remain functional, though not necessarily optimized, for resolutions of 1280x720 or higher and screen scales of 150%.

#### Maintainability

* **Code Quality:** The code should be well-maintained and follow best practices.
* **Documentation:** Clear, consistent and accurate documentation is required.
* **Testability:** Implemented features should not impede testing or make the software difficult to test.

#### Process Requirements

* The project should follow a development schedule that delivers incremental versions on a weekly basis.

#### Notes about project scope

* Input should primarily be via the _CLI_, with the _GUI_ mainly providing visual feedback.
* The software does not need to support the printing of reports or contacting research participants.
* The software is intended for single-user operation, not multi-user functionality.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **UI**: User Interface - The medium through which users interact with a system, encompassing both graphical (*GUI*) and text-based (*CLI*) elements
* **GUI**: Graphical User Interface - A visual-based interface where users interact with the system through graphical elements like icons and windows
* **CLI**: Command Line Interface - A text-based interface where users interact with the system by typing commands

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
