---
layout: page
title: Developer Guide
---

# Table of Contents

1. [Acknowledgements](#acknowledgements)
2. [Setting up, getting started](#setting-up-getting-started)
3. [Design](#design)<br>
   3.1. [Architecture](#architecture)<br>
   3.2. [UI component](#ui-component)<br>
   3.3. [Logic component](#logic-component)<br>
   3.4. [Model component](#model-component)<br>
   3.5. [Storage component](#storage-component)<br>
   3.6. [Common classes](#common-classes)<br>
4. [Implementation](#implementation)<br>
   4.1. [Undo/redo feature](#undoredo-feature)<br>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.1.1. [Implementation](#implementation-1)<br>
5. [Documentation, logging, testing, configuration, dev-ops](#documentation-logging-testing-configuration-dev-ops)
6. [Appendix: Planned Enhancements](#appendix-planned-enhancements)
7. [Appendix: Requirements](#appendix-requirements)<br>
   7.1. [Product scope](#product-scope)<br>
   7.2. [User stories](#user-stories)<br>
   7.3. [Use cases](#use-cases)<br>
   7.4. [Non-Functional Requirements](#non-functional-requirements)<br>
   7.5. [Product scope](#product-scope)<br>
   7.6. [Glossary](#glossary)<br>
8. [Appendix: Instructions for manual testing](#appendix-instructions-for-manual-testing)<br>
   8.1. [Launch and shutdown](#launch-and-shutdown)<br>
   8.2. [Deleting a person](#deleting-a-person)<br>
   8.3. [Saving data](#saving-data)<br>

---

<div style="page-break-after: always;"></div>

## **Acknowledgements**

- [ez-vcard](https://github.com/mangstadt/ez-vcard) by [mangstadt](https://github.com/mangstadt/)
- [mockito](https://github.com/mockito/mockito)

---

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

---

<div style="page-break-after: always;"></div>

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The **_Architecture Diagram_** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103-F10-3/tp/blob/master/src/main/java/bizbook/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103-F10-3/tp/blob/master/src/main/java/bizbook/MainApp.java)) is in charge of the app launch and shut down.

- At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
- At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

- [**`UI`**](#ui-component): The UI of the App.
- [**`Logic`**](#logic-component): The command executor.
- [**`Model`**](#model-component): Holds the data of the App in memory.
- [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The _Sequence Diagram_ below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

- defines its _API_ in an `interface` with the same name as the Component.
- implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

<div style="page-break-after: always;"></div>

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103-F10-3/tp/blob/master/src/main/java/bizbook/ui/Ui.java)

<img src="images/UiClassDiagram.png" width="1960"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103-F10-3/tp/blob/master/src/main/java/bizbook/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103-F10-3/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

- executes user commands using the `Logic` component.
- listens for changes to `Model` data so that the UI can be updated with the modified data.
- keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
- depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

<div style="page-break-after: always;"></div>

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103-F10-3/tp/blob/master/src/main/java/bizbook/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">

:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.

</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:

- When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
- All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

<div style="page-break-after: always;"></div>

### Model component

**API** : [`Model.java`](https://github.com/AY2425S1-CS2103-F10-3/tp/blob/master/src/main/java/bizbook/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />

The `Model` component,

- stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
- stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
- stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
- does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div style="page-break-after: always;"></div>

<div markdown="span" class="alert alert-info">

:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>

<div style="page-break-after: always;"></div>

### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103-F10-3/tp/blob/master/src/main/java/bizbook/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,

- can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
- inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
- depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `bizbook.commons` package.

---

<div style="page-break-after: always;"></div>

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Undo/redo feature

#### Implementation

The undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally in an `undoStateList` for the undo history and `redoStateList` for the redo history. Additionally, it implements the following operations:

- `VersionedAddressBook#canRedo()` — Checks if there is a version to redo to.
- `VersionedAddressBook#canUndo()` — Checks if there is a version to undo to.
- `VersionedAddressBook#commit()` — Saves the current address book state in its history.
- `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
- `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#canRedo()`, `Model#canUndo()`, `Model#saveAddressBookVersion()`, `Model#revertAddressBookVersion()` and `Model#redoAddressBookVersion()` respectively.

The `undoStateList` will only hold up to 5 seperate unique versions of the address book, therefore BizBook will only remember up till 5 previously executed commands that in some way have modified the address book.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `undoStateList` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state. While the `redoStateList` will be empty.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#saveAddressBookVersion()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `undoStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#saveAddressBookVersion()`, causing another modified address book state to be saved into the `undoStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

<div style="page-break-after: always;"></div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#revertAddressBookVersion()`, which remove the item at the `currentStatePointer` and add it into the `redoStateList`. In the process the `currentStatePointer` moves left, points to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div style="page-break-after: always;"></div>

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redoAddressBookVersion()`, which removes the item at the `redoPointer` and adds it to the back of the `undoStateList`, it also shifts the `currentStatePointer` to the right as well as restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `redoPointer` is at index `redoStateList.size() - 1` or the list is empty, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedo()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#saveAddressBookVersion()`, `Model#revertAddressBookVersion()` or `Model#redoAddressBookVersion()`. Thus, the `undoStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#saveAddressBookVersion()`. Since the `redoPointer` is pointing at an item in the `redoStateList`, all address book states in `redoStateList` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

<div style="page-break-after: always;"></div>

## **Documentation, logging, testing, configuration, dev-ops**

- [Documentation guide](Documentation.md)
- [Testing guide](Testing.md)
- [Logging guide](Logging.md)
- [Configuration guide](Configuration.md)
- [DevOps guide](DevOps.md)

---

<div style="page-break-after: always;"></div>

## **Appendix: Planned Enhancements**

Team Size: 5

1. **Detailed errors for importing**<br/>
   When the user imports a file with the wrong extension and the importer fails to import the contents of the file, add
   a check to see whether the file has the right file extension.
   <br/><br/>
2. **Warn the user when exporting will cause a file to be overwritten**<br/>
   When the export file location already has a file, warn and ask for permission from the user before overwriting the
   file.
   <br/><br/>
3. **Maintaining focus on currently selected person after any command execution**<br/>
   The currently focused person should remain even after commands like `addnotes` or even `delete` as much as possible.
   <br/><br/>
4. **Limit number of tags that can be displayed**<br/>
   The person list panel and the pinned person list panel should only show a summary of tags. If there are too many
   tags or the tag names are too long, they should be hidden and only shown in the contact details panel when focused.
   <br/><br/>
5. **Dynamic message box sizing**<br/>
   The message box should increase in height and wrap the text within it as needed so that the user can view the error
   message or other command messages easily.
   <br/><br/>
6. **International phone numbers**<br/>
   Allow the application to accept international phone numbers on top of Singapore phone numbers.
   <br/><br/>
7. **Email validation**<br/>
   Update the email validation of the `Email` model to be more strict and check for a period in the domain. Currently,
   the validation permits `abc@aa`.
   <br/><br/>
8. **Long note content support**<br/>
   Update the UI elements of notes to support text wrapping so extra long notes do not trail off with `...` but display
   hidden content in the next line.
   <br/><br/>

9. **Allow special characters**<br/>
   Update text field inputs, such as name and notes to allow special characters and symbols.
   <br/><br/>

10. **Change of unique identifier**<br/>
    Update the uniqueness of contacts to handphone number to allow for contacts of the same name.
    <br/><br/>

<div style="page-break-after: always;"></div>

## **Appendix: Requirements**

### Product scope

**Target user profile**:

A sales and customer relations representative working in the F&B industry. In
particular, this representative works with B2B sales.

- has a need to manage a significant number of business contacts
- prefer desktop apps over other types
- can type fast
- prefers typing to mouse interactions
- is reasonably comfortable using CLI apps

**Value proposition**: This product aims to streamline and simplify sales management for Food and Beverage outlets. By providing an organized, easy-to-use platform for managing business contacts, it helps sales representatives save time and improve efficiency.

<div style="page-break-after: always;"></div>

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …           | I want to …                                                                 | So that I can …                                                        |
| -------- | ---------------- | --------------------------------------------------------------------------- | ---------------------------------------------------------------------- |
| `* * *`  | user             | add a new contact                                                           | save the contact information of people                                 |
| `* * *`  | user             | delete a contact                                                            | free up space in my app                                                |
| `* * *`  | user             | view all contact                                                            | see the full list of contacts                                          |
| `* * *`  | user             | view a contact                                                              | retrieve contact information of a person                               |
| `* * *`  | user             | save all contact                                                            | retain all information for when i reopen the app                       |
| `* * *`  | sales rep        | have a low query time                                                       | avoid wasting much time querying my desired contact                    |
| `* *`    | user             | find a person by name                                                       | locate details of persons without having to go through the entire list |
| `* *`    | user             | search through my contacts                                                  | find a specific person                                                 |
| `* *`    | new user         | see usage instructions                                                      | know how to use the app                                                |
| `* *`    | user             | edit contact                                                                | update contact with new information                                    |
| `* *`    | user             | delete a tag from a person                                                  | remove tags that are invalid or are not applicable to the person       |
| `* *`    | user             | sort contact by name                                                        | see whose contact I have saved                                         |
| `* *`    | user             | pin a specific contact                                                      | view them on a separate list                                           |
| `* *`    | user             | unpin a specific contact                                                    | clear the pin that is no longer needed                                 |
| `* *`    | user             | archive contact                                                             | hide less frequently used contacts without deleting them               |
| `* *`    | user             | be alerted when a contact already exist                                     | avoid accidentally creating a duplicate                                |
| `* *`    | user             | hide private contact details                                                | minimize chance of someone else seeing them by accident                |
| `* *`    | user             | undo a command                                                              | fix a mistake I made                                                   |
| `* *`    | user             | redo a command                                                              | revert back the changes I undid                                        |
| `* *`    | new user         | import all contact details into the app                                     | start using without manual setup                                       |
| `* *`    | sales rep        | keep track of clients I have contacted by seeing when I last contacted them | avoid wasting time calling them again about the same product           |
| `* *`    | sales rep        | view my most popular/active clients                                         | promote the new product                                                |
| `* *`    | sales rep        | remember the client's preferred products                                    | recommend related products                                             |
| `* *`    | sales rep        | add notes to client's contact                                               | keep track of my conversation with them                                |
| `* *`    | sales rep        | edit notes saved to client's contact                                        | keep track of my conversation with them                                |
| `* *`    | sales rep        | delete notes from a client's contact                                        | remove incorrect or outdated notes                                     |
| `* *`    | sales rep        | group my clients by industry                                                | tell if sales are doing well in that industry among other metrics      |
| `* *`    | sales rep        | add tags to clients                                                         | categorize them                                                        |
| `* *`    | sales rep        | keep note of my client's email addresses                                    | potentially send promotions or survey forms                            |
| `* *`    | sales rep        | export a list of contact emails                                             | add them to a mailing list                                             |
| `* *`    | sales rep        | export my contacts                                                          | send it to my coworker who needs it for his work                       |
| `* *`    | sales rep        | add a tag to multiple clients                                               | tag the clients more easily                                            |
| `*`      | user             | sort contacts by name                                                       | locate a person easily                                                 |
| `*`      | experienced user | use keyboard shortcuts                                                      | navigate the app faster                                                |
| `*`      | sales rep        | contact my client quickly from the app                                      | avoid typing numbers repeatedly on my _device_                         |
| `*`      | user             | use my previous command quickly                                             | avoid retyping a command                                               |
| `*`      | user             | toggle my application between light and dark mode                           | see the application in my preferred theme                              |

<div style="page-break-after: always;"></div>

### Use cases

(For all use cases below, the **System** is the `Bizbook` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC1 - Add a person**

**MSS**

1.  Actor requests to add a new person.
2.  System shows details of the newly added person.

    Use case ends.

**Extensions**

- 1a. The details entered about the new person are invalid.

  - 1a1. System shows an error message.

    Use case ends.

- 1b. A person with the same identifier is already in contact list.

  - 1b1. System shows duplicate person message.

    Use case ends.

**Use case: UC2 - List all people**

**MSS**

1.  Actor requests to list all people saved in the System.
2.  System shows a list of people.

    Use case ends.

**Extensions**

- 1a. No contacts stored in the System.

  Use case ends.

<div style="page-break-after: always;"></div>

**Use case: UC3 - Delete a person**

**MSS**

1.  Actor performs <u>list all people (UC2)</u>.
2.  Actor requests to delete a specific person in the list.
3.  System shows details of deleted person.

    Use case ends.

**Extensions**

- 2a. The specified person is invalid.

  - 2a1. System shows an error message.

    Use case ends.

**Use case: UC4 - Delete a tag from a person contact**

**MSS**

1.  Actor performs <u>list all people (UC2)</u>.
2.  Actor requests to delete a specific tag from a person.
3.  System remove the tag from the person.

    Use case ends.

**Extensions**

- 2a. The specified person is invalid.

  - 2a1. System shows an error message.

    Use case ends.

- 2b. The specified tag does not exist.

  - 2b1. System shows an error message.

    Use case ends.

<div style="page-break-after: always;"></div>

**Use case: UC5 - View person contact**

**MSS**

1.  Actor requests to see specific person's detail.
2.  System shows person's contact details.

    Use case ends.

**Extensions**

- 1a. The specified person is invalid.

  - 1a1. System shows an error message.

    Use case ends.

**Use case: UC6 - Find people**

**MSS**

1.  Actor requests to find specific people.
2.  System shows a filtered list of people.

    Use case ends.

**Extensions**

- 1a. No contacts match keywords.

  Use case ends.

<div style="page-break-after: always;"></div>

**Use case: UC7 - Add note to a person contact**

**MSS**

1.  Actor performs <u>list all people (UC2)</u>.
2.  Actor requests to add a note to a specific person.
3.  System shows details of the newly added note to that person.

    Use case ends.

**Extensions**

- 2a. The specified person is invalid.

  - 2a1. System shows an error message.

    Use case ends.

- 2b. The note content is invalid.

  - 2b1. System shows an error message.

    Use case ends.

<div style="page-break-after: always;"></div>

**Use case: UC8 - Edit a note of a person contact**

**MSS**

1.  Actor performs <u>list all people (UC2)</u>.
2.  Actor requests to edit a note to a specific person.
3.  System shows details of the newly edited note of that person.

    Use case ends.

**Extensions**

- 2a. The specified person is invalid.

  - 2a1. System shows an error message.

    Use case ends.

- 2b. The note index is invalid.

  - 2b1. System shows an error message.

    Use case ends.

- 2c. The note content is invalid.

  - 2c1. System shows an error message.

    Use case ends.

<div style="page-break-after: always;"></div>

**Use case: UC9 - Delete note from a person contact**

**MSS**

1.  Actor performs <u>list all people (UC2)</u>.
2.  Actor requests to delete a specific note from a specific person.
3.  System shows updated details excluding deleted note from that person.

    Use case ends.

**Extensions**

- 2a. The specified person is invalid.

  - 2a1. System shows an error message.

    Use case ends.

- 2b. The note index is invalid.

  - 2b1. System shows an error message.

    Use case ends.

**Use case: UC10 - Pin a person**

**MSS**

1.  Actor performs <u>list all people (UC2)</u>.
2.  Actor requests to pin a specific person.
3.  System shows details of newly pinned person.

**Extension**

- 2a. The specified person is invalid.

  - 2a1. System shows an error message.

    Use case ends.

- 2b. The person is already pinned.

  - 2b1. System shows duplicated pin message.

    Use case ends.

<div style="page-break-after: always;"></div>

**Use case: UC11 - Unpin a person**

_Similar to UC10 except without extension 2b._

**Use case: UC12 - Undo a command**

**MSS**

1.  Actor performs a command that updates the System.
2.  System executes the command.
3.  Actor requests to undo the recently executed command.
4.  System reverts changes made by the actor.

    Use case ends.

**Extensions**

- 3a. There is no version to revert to.

  - 3a1. System shows an error message.

    Use case ends.

**Use case: UC13 - Redo a command**

**MSS**

1.  Actor <u>undos a command (UC12)</u>.
2.  Actor requests to redo the recently executed undo command.
3.  System reverts changes made by the actor.

    Use case ends.

**Extensions**

- 2a. There is no version to revert to.

  - 2a1. System shows an error message.

    Use case ends.

<div style="page-break-after: always;"></div>

**Use case: UC14 - Export contact list**

**MSS**

1.  Actor requests to export contact list to a specific file.
2.  System exports the contact information into the file.

    Use case ends.

**Extensions**

- 1a. System detects that the directory does not exist.

  - 1a1. System creates the directory.

    Use case resumes from step 2.

- 1b. System detects that the file is used by another process.

  - 1b1. System shows an error message.

    Use case ends.

**Use case: UC15 - Import a contact list**

**MSS**

1.  Actor requests to import a contact list from a specific file.
2.  System imports the contact information into the file.

    Use case ends.

**Extensions**

- 1a. System detects that the file does not exist.

  - 1a1. System shows an error message

    Use case ends.

- 1b. System detects that the file is not supported by the program

  - 1b1. System shows an error message

    Use case ends.

<div style="page-break-after: always;"></div>

**Use case: UC16 - Toggle application's theme**

**MSS**

1.  Actor toggle the system's theme.
2.  System changes theme.

    Use case ends.

**Use case: UC17 - Command History**

**MSS**

1. Actor inputs a command into the System.
2. System processes the command and confirms its success.
3. Actor presses the "Up" arrow key to retrieve and re-populate the previous command in the input field.

   Use case ends.

**Extensions**

- 2a. Command fails.

  - 2a1. System displays an error message indicating the failure reason.

    Use case resumes from step 1.

- 3a. Multiple previous commands available.

  - 3a1. Actor presses the "Up" arrow key multiple times to cycle through the command history.
  - 3a2. System displays each previous command in sequence.

    Use case ends.

<div style="page-break-after: always;"></div>

**Use case: UC18 - Edit a person's information**

**MSS**

1.  Actor performs <u>list all people (UC2)</u>.
2.  Actor requests to edit the details of a specific person.
3.  System shows details of the newly edited person.

    Use case ends.

**Extensions**

- 2a. The specified person is invalid.

  - 2a1. System shows an error message.

    Use case ends.

- 2b. The details entered are invalid or are in the wrong format.

  - 2b1. System shows an error message.

    Use case ends.

<div style="page-break-after: always;"></div>

### Non-Functional Requirements

1.  The system should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  The system should be able to hold up to 1000 contacts without a noticeable sluggishness in performance for typical usage.
3.  The system should be developed in a modular way for easier updates and bug fixes.
4.  The system should ensure data consistency across all instances.
5.  The system should continue functioning in the event of a missing or corrupted save file.
6.  The system should encrypt sensitive data to follow data protection laws.
7.  The interface should be intuitive and easy to use.

_{More to be added}_

### Glossary

- **Mainstream OS**: Windows, Linux, Unix, MacOS
- **Private contact detail**: A contact detail that is not meant to be shared with others
- **Device**: system with dialing and calling capabilities

---

<div style="page-break-after: always;"></div>

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1a. Download the jar file and copy into an empty folder

   2b. Execute the jar file by running `java -jar bizbook.jar` in command prompt.<br>
   Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   2a. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2b. Re-launch the app by running `java -jar bizbook.jar` in command prompt.<br>
   Expected: The most recent window size and location is retained.

3. Automatic help page launch

   3a. Open the help window, and click the "Open URL" button.<br>
   Expected: The user guide page should automatically open in the browser.

<div style="page-break-after: always;"></div>

### Deleting a person

1. Deleting a person while all persons are being shown

   1a. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1b. Test case: `delete 1`<br>
   Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1c. Test case: `delete 0`<br>
   Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1d. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
   Expected: Similar to previous.

### Saving data

1. Dealing with missing/corrupted data files

   1a. Prerequisites: Prepare a valid `bizbook.json` data file with at least 3-4 persons.

   1b. Manually edit the data file so have 2 persons with the same name.

   1c. Re-launch the app by running `java -jar bizbook.jar` in command prompt.<br>
   Expected: App starts with an empty database.
