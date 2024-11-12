---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

Clientele+ is developed based on the AddressBook Level-3 application. Learn more about that project [here](https://se-education.org/addressbook-level3/).

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document are in the `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-F14A-3/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-F14A-3/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete id/1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-F14A-3/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-F14A-3/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-F14A-3/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-F14A-3/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete id/1")` API call as an example.

![Interactions Inside the Logic Component for the `delete id/1` Command](images/DeleteSequenceDiagram.png)


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
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-F14A-3/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the archived address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g. results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores the currently 'selected' `Person` objects (e.g. after an archive command) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI is bound to this list when the command to access the archived list is entered.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects. This is the same for `ArchivedAddressBook` as well.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-F14A-3/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save address book data, archived address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from `AddressBookStorage`, `ArchivedAddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Sort feature
The following sequence diagram shows how a sort command goes through the `Model` component:
![ArchiveSortSequenceDiagram](images/ArchiveSortSequenceDiagram.png)

### Find feature
The following sequence diagram shows how a find command goes through the `Model` component:
![FindSequenceDiagram](images/FindSequenceDiagram.png)

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

Step 2. The user executes `delete id/5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete id/5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

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


* **Alternative 2:** Individual command knows how to undo/redo by itself.
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

* Needs to manage a variety of sections, such as client names, email, payment status and more.
* Desire to track all client statuses in one place.
* Wants to avoid tracking clients using multiple applications.
* Wants to easily change statuses of a client upon completion of project.
* Can type fast (≥80 wpm).
* Prefers typing over mouse interactions.
* Familiar with the workings of CLI and commands.

**Value proposition**: Clientele+ seamlessly combines client contacts,
payment tracking and more in one efficient package, tailored specifically for freelance software developers.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​            | I want to …​                                 | So that I can…​                                                    |
|----------|--------------------|----------------------------------------------|--------------------------------------------------------------------|
| `* * *`  | software developer | add clients                                  | update my client list                                              |
| `* * *`  | software developer | remove clients                               | ensure my client list is not cluttered                             |
| `* * *`  | software developer | view my clients and their details            | access important client information                                |
| `* * *`  | software developer | update my clients' details                   | ensure my client list details are updated                          |
| `* * *`  | software developer | view a client's payment status               | track who has paid and who still needs to make a payment           |
| `* * *`  | software developer | view a client's project status               | monitor the progress of each client's project                      |
| `* * *`  | software developer | view a client's status                       | easily identify active clients                                     |
| `* *`    | software developer | filter my clients based on sections          | easily find specific groups of clients                             |
| `* *`    | software developer | sort clients by category                     | keep my client list organised                                      |
| `* *`    | software developer | set deadlines                                | ensure projects are completed before deadlines                     |
| `* *`    | software developer | blacklist a client                           | avoid working with certain clients in future                       |
| `* *`    | software developer | view clients on the blacklist                | view all the clients I intend to avoid                             |
| `* *`    | software developer | whitelist a blacklisted client               | consider working with said client again                            |
| `* *`    | software developer | view clients on the whitelist                | view all clients that I have no issues working with                |
| `* *`    | software developer | archive a client                             | store information of old clients without cluttering my client list |
| `* *`    | software developer | unarchive a client                           | bring the information of archived clients back to the client list  |
| `* *`    | software developer | see reminders for the next earliest deadline | focus on the most urgent task first                                |
| `*`      | software developer | keep notes for each client                   | take note of important details from meetings                       |
| `*`      | software developer | undo previous commands                       | easily undo errors                                                 |

### Use cases

(For all use cases below, the **System** is the `Clientele+` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Add a client**

**MSS**

1.  User provides client details to add.
2.  Clientele+ adds the client.

    Use case ends.

**Extensions**

* 1a. The client's name, phone, email, address, or deadline is not provided.

   * 1a1. Clientele+ shows an error message.
   * 1a2. User reenters new command.

        Steps 1a1-1a2 are repeated until the command entered is correct.
        
        Use case resumes from step 1.

* 1b. Input fields are incorrectly formatted.

    * 1b1. Clientele+ shows an error message.
    * 1b2. User reenters command.

      Steps 1b1-1b2 are repeated until the command entered is correct.
      
      Use case resumes from step 1.

* *a. At any time, user decides to stop adding the client, or exits the application.

    * *a1. Nothing is added to the client list.
       
      Use case ends.

<br>

**Use case: Delete a client**

**MSS**

1.  User requests to list persons.
2.  Clientele+ shows a list of persons.
3.  User requests to delete a specific client in the list.
4.  Clientele+ deletes the client.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  * 2a1. Clientele+ shows an error message.
        
    Use case ends.

* 3a. The given index or name is invalid.

    * 3a1. Clientele+ shows an error message.

      Use case resumes from step 2.

* 3b. Multiple names matching the given name exist.

    * 3b1. Clientele+ shows list of clients with matching names.
    * 3b2. Clientele+ prompts user to delete by index.

      Use case resumes from step 3.

* *a. At any time, user decides to stop deleting the client, or exits the application.

    * *a1. Nothing is deleted from client list.  
        
        Use case ends.

<br>

**Use case: Modify a client's details**

**MSS**

1.  User requests to list persons.
2.  Clientele+ shows a list of persons.
3.  User selects the client they wish to modify and provides the updated details.
4.  Clientele+ updates that client's details.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  * 2a1. Clientele+ shows an error message. 
    
    Use case ends.

* 3a. The selected index is invalid.

    * 3a1. Clientele+ shows an error message.

      Use case resumes from step 2.

* 3b. The given details are invalid.

    * 3b1. Clientele+ shows an error message.

       Use case resumes from step 2.

<br>

**Use case: Find a client**

**MSS**

1.  User provides search parameters.
2.  Clientele+ shows a list of persons matching the parameters.

    Use case ends.

**Extensions**

* 1a. Search parameters are incorrectly formatted.

    * 1a1. Clientele+ shows an error message.
    * 1a2. User reenters command.

      Steps 1a1-1a2 are repeated until the command entered is correct.
      
      Use case resumes from step 1.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  A user should be able to accomplish all tasks just through typing commands (so no clickable buttons for instance).
5.  Command usage and help messages should be clear enough to a user with at least some CLI experience.
6.  The window must look good on a screen that has a resolution of 980x720 or higher (i.e. lines must not bleed out of the window edge).
7.  A more technical user must be able to interpret the contents of the stored JSON file.
8.  A user must be updated on the status of their command within (at most) 2 seconds.

### Glossary

* **API**: A set of functions that enable communication between software components.
* **fxml**: File format used to format/structure a JavaFX application's UI.
* **JavaFX**: A third-party software for Java, used to run our builds and tests.
* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Parser**: The software component responsible for interpreting user input into commands.
* **PlantUML**: A third-party tool that facilitates the creation of various software diagrams.
* **Versioned AddressBook**: An extension to the AddressBook class that allows undoing/redoing actions.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**
**Note:** Our team size is 5

1. **Make the GUI wrap large addresses to the next line:** With the current implementation, if you type in an address which is longer than the width of the screen, it stretches the person card (for all contacts), forcing the user to scroll to the right to see the rest of the address, the project status and deadline. In future updates, we plan to enable `textWrap` and set a `maxWidth` for the address label to enhance the UI and improve readability.<br>
2. **Add support for patronymics, like 's/o' or 'd/o', in the name field:** The current implementation only allows alphanumeric characters, spaces, and dashes, and so users have to work around this restriction by using "s o" or "son of". In the future, we will adjust the regex to something like this `[\\p{Alnum}]+(([ -][\\p{Alnum}]+)*|(([ -](s|d)/o\\s)[\\p{Alnum}]+))\\s*` to allow special strings like "s/o" and "d/o".<br>
3. **Make prefix for deadlines `dl/`:** This is in conjunction with point 2. Our current implementation of deadline is implemented with `d/DEADLINE`. If we implement a regex to accept "d/o", this would trigger an error since it would match "d/". In the future, we will change the prefix to something like `PREFIX_DEADLINE = new Prefix("dl/")`.<br>
4. **Allow users to clear current sorting scheme:** Currently, users cannot 'undo' a sort command. In the future, we plan to add an undo and redo feature, which will complete this enhancement as well. Do refer to [the proposed undo and redo feature](#proposed-undoredo-feature).<br>
5. **Adjust sorting function to maintain order for identical names/deadlines:** Currently, when multiple entries share the same name or deadline, sorting them may cause reordering if a different field (i.e project status) is changed afterward. The reason for this is due to a bug with the way SortedList is implemented. For details on this bug, and how we plan to enhance it in the future, refer to [Appendix: Sorted List Bug](#appendix-sorted-list-bug).<br>
6. **Make `add` and `edit` commands accept blacklisted client status:** The current implementation throws an error of invalid command format for the parameter `cs/blacklisted`. Users can work around this by using the blacklist command implemented. In the future, we plan to remove the restriction on blacklisted client status from both `AddCommandParser` and `EditCommandParser`.<br>
7. **Make rejection of blacklisted client status for add and edit command more specific:** This is an alternative to point 6 above. The current error message thrown is `"Invalid command format!` which does not accurately explain to the user that the issue lies with the blacklisted parameter. We plan to make the error message mention that `cs/blacklisted cannot be used for this command`. **It is important to note that this enhancement will be deemed redundant if point 6 is implemented, where `cs/blacklisted` is indeed accepted.**<br>
8. **Reject email inputs without a period between alphanumeric characters for the domain portion:** Currently, an input like `e/john@example` is accepted, however, this should not be the case as a valid domain format would require at least 1 period for the top level domain (ie.john@example.com). We plan to adjust the regex for the domain portion of the email from `"(" + DOMAIN_PART_REGEX + "\\.)*"` to `"(" + DOMAIN_PART_REGEX + "\\.)+"` to reject such an input.<br>
9. **Make `INDEX` 0 and negative `INDEX` error messages more specific:** Currently, when 0 or negative numbers are passed for the `INDEX` parameter, the error message thrown is `Invalid command format!`. In the future, we will change this error message to `The person index provided is invalid`.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Sorted List Bug**

Initially reported [here](https://bugs.openjdk.org/browse/JDK-8301761)

Description of SortedList bug:

An issue in `SortedList.sourceChanged()` arises when an element in the source list is replaced using `set(index, element)`. Normally, `ObservableList` creates a `ListChangeEvent` with a remove and an add event, but `SortedList` may misplace elements with a comparator result of 0(i.e if they're being sorted by name and have the same name), adding the new element in the wrong position. This becomes problematic in tables(such as how our clients are displayed).

**Note:** This bug is an issue with all `sort` command combinations: sorting by name or deadline in either ascending or descending order can be used to reproduce the bug.

Steps to reproduce:

1. Add multiple people (at least 3) with the same name (i.e `John`) and make the phone numbers `1111 1111`, `2222 2222`, `3333 3333`, ...
2. Observe the initial order
3. Do a sort command (`sort name ascending`)
4. Start editing other fields of these John's (i.e edit their project status)
5. After doing some of these edits, you will most likely see the list order change among Johns

In the future, we will firstly modify the program, such that it no longer uses `SortedList`.

1. In `ModelManager`, delete the `sortedPersons` variable
2. This will include modifying the `getFilteredPersonList()` method, to return `filteredPersons`, instead of `sortedPersons`.
3. Replace the `sortByComparator` method to use the `FXCollections.sort() method`. This is sample code that can be used (modification may be necessary)

```java
@Override
public void sortByComparator(Comparator<Person> comparator) {
    currentComparator = comparator;
    int initialSize = addressBook.getPersonList().size();

    ObservableList<Person> sortedList = FXCollections.observableArrayList(addressBook.getPersonList());
    FXCollections.sort(sortedList, comparator);

    addressBook.setPersons(sortedList);
    assert addressBook.getPersonList().size() == initialSize;
}
```

**Note:** This will make the current implementation such that the list only sorts when a sort command is executed. For instance, if the client list becomes A, J, J, Z after a `sort name ascending` command, then a client B is added, the list will become A, J, J, Z, B.

If you wish for the list to automatically sort as clients are added / edited, you will need to modify the `addPerson` and `setPerson` command in `ModelManager`. Simply add a check to test if a sort is activated, and if so, after the modification of the list, perform a sort. The following code can be used as an example:

```java
if (currentComparator != null && !isArchivedList) {
    sortByComparator(currentComparator);
}
```


--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   2. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Adding a person
1. Adding a client while the client list is shown

   1. Prerequisites: List all clients using the `list` command.

   2. Test case: `add n/Michael Jackson p/12345678 e/Michael@gmail.com a/Block 17 d/10-10-2024`<br>
      Expected: The client is added. Details of the added client is shown in the status message with<br> 
      in progress project status, pending payment status and active client status

### Editing a person
1. Editing a client while the client list is shown

    1. Prerequisites: Ensure you have at least 2 clients in the list, and that the client in the first position of the list has the following parameters, `NAME`: Jack, `PHONE_NUMBER`: 999, `EMAIL`: Jack@gmail.com

    2. Test case: `edit 2 n/Jack p/999 e/Jack@gmail.com`<br>
       Expected: The error message `This person already exists in either the address book or the archived address book.` is shown.

### Finding a person
1. Finding a client while the client list is shown

    1. Prerequisites: Delete your data folder to start with the sample client list

    2. Test case: `find n/b`<br>
       Expected: The list has a size of 2 and displays the clients with `NAME` Bernice Yu and Roy Balakrishnan

### Deleting a person

1. Deleting a client while the client list is shown

   1. Prerequisites: List all clients using the `list` command. Multiple clients with the same name exist.

   2. Test case: `delete id/1`<br>
      Expected: First client is deleted from the list. Details of the deleted client shown in the status message.

   3. Test case: `delete id/0`<br>
      Expected: No client is deleted. Error details shown in the status message.

   4. Test case: `delete n/John Doe` <br>
      Expected: Client with name `John Doe` is deleted. Details of the deleted client shown in the status message. <br>
      If multiple `John Doe`s exist, list of all `John Doe`s is displayed. <br>
      User prompted to delete by index in status message.

   5. Other incorrect delete commands to try: `delete`, `delete p/x` (incorrect prefixes), `delete id/y`, `...` (where y is larger than the list size)<br>
      Expected: Similar to previous.

### Saving data

1. Dealing with missing or corrupted data files

   1. `preferences.json`
      1. Move `preferences.json` to another folder.
      2. Relaunch the app.

      Expected: Application launches with the default window size.

   2.  `config.json`
       1. Move `config.json` to another folder.
       2. Relaunch the app.

       Expected: Application launches, config file is re-created with default configuration.

   3.  `addressbook.json`
       1. Corrupted:
          1. Manually modify `addressbook.json`
          2. Relaunch the app

          Expected: Application fails to read from the file and loads without any data.

       2. Missing:
          1. Move `data` folder to another directory.
          2. Relaunch the app.

          Expected: Application loads with the sample data.
