---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---
# GOATS Developer Guide

<!-- * Table of Contents -->

<page-nav-print />

---

## **Acknowledgements**

This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).

Libraries used in this project:

- [JavaFX](https://openjfx.io/)
- [JUnit5](https://github.com/junit-team/junit5)

---

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

<br>

---

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-T09-4/tp/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-T09-4/tp/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.

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

<br>

---

<div style="page-break-after: always;"></div>

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-T09-4/tp/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component" width="850" />

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-T09-4/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-T09-4/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

<br>

---

<div style="page-break-after: always;"></div>

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-T09-4/tp/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="500"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1 2 3")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:

* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

<br>

---

<div style="page-break-after: always;"></div>

### Model component

**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-T09-4/tp/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="600" />

The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<br>

---

<div style="page-break-after: always;"></div>

### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-T09-4/tp/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="700" />

The `Storage` component,

* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

<br>

---

## **Implementation**

This section describes some noteworthy details on how certain features and commands are implemented.

* [Add Student Command](#add-student-command)
* [Add Parent Command](#add-parent-command)
* [Edit Person Command](#edit-person-command)
* [Delete Person Command](#delete-person-command)
* [Link Command](#link-command)
* [Find Command](#find-command)
* [Pin Command](#pin-command)
* [[Proposed] Undo/redo feature](#proposed-undoredo-feature)

<br>

---

### Add Student Command

#### Overview
The `addstudent` command is used to add a student to the address book with the information fields `Name`, `Phone`,
`Email`, `Address`, `LessonTime` and `Education`.

The format for the `addstudent` command can be found [here](https://ay2425s1-cs2103t-t09-4.github.io/tp/UserGuide.html#adding-a-student-addstudent)

#### Feature details
1. The user executes the `addstudent` command.
2. If any of the fields are not provided, an error message with the appropriate command usage will be displayed.
3. If any of the command parameters are invalid, an error message with the appropriate parameter format will be displayed.
4. The `Student` is then searched in the `model` to see if it is an existing person that the address book contains. If the person does
   exist, an error message will be displayed.
5. If all the previous stages complete without exceptions or errors, the `Student` will be added to the `AddressBook`

The activity diagram for adding a `Student` into the address book

<puml src="diagrams/AddStudentActivityDiagram.puml" width="400" />

The sequence of the `addstudent` command is as follows:

1. The user inputs the `addstudent` command.<br>
   e.g. `addstudent n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 lt/mon:12:00 edu/Primary t/focused`
2. The `LogicManager` calls the `AddressBookParser#parseCommand` to parse the command.
3. The `AddressBookParser` then creates a new `AddStudentCommandParser` to parse the fields provided by the user and
   a new `Student` and `AddStudentCommand` is created.
4. The `AddStudentCommand` checks if the `Student` is valid by calling `Model#hasPerson` and retrieves
   the module if the person exists in Address Book.
6. `AddStudentCommand` then attempts to add the module into the Model via `Model#addPerson`.
7. If `Student`, the user inputs are valid, and the Address Book does not contain the module, `AddStudentCommand` will
   successfully add the new `Student` into Address Book.

The following two *Sequence Diagrams* shows how the `addstudent` command works:

<div style="page-break-after: always;"></div>

Here is the *Sequence Diagram* showing how the parser works:

<puml src="diagrams/AddStudentParseSequenceDiagram.puml" width="600" />


And here is the *Sequence Diagram* showing the command being executed:

<puml src="diagrams/AddStudentExecuteSequenceDiagram.puml" width="450" />


<br>

---

### Add Parent Command

#### Overview
The `addparent` command is used to add a student to the address book with the information fields `Name`, `Phone`,
`Email`, `Address`.

The format for the `addparent` command can be found [here](https://ay2425s1-cs2103t-t09-4.github.io/tp/UserGuide.html#adding-a-parent-addparent)

#### Feature details
1. The user executes the `addparent` command.
2. If any of the fields are not provided, an error message with the appropriate command usage will be displayed.
3. If any of the command parameters are invalid, an error message with the appropriate parameter format will be displayed.
4. The `Parent` is then searched in the `model` to see if it is an existing person that the address book contains. If the person does
   exist, an error message will be displayed.
5. If all the previous stages complete without exceptions or errors, the `Parent` will be added to the `AddressBook`

<div style="page-break-after: always;"></div>

The activity diagram for adding a `Parent` into the address book

<puml src="diagrams/AddParentActivityDiagram.puml" width="400" />

The sequence of the `addparent` command is as follows:

1. The user inputs the `addparent` command.<br>
   e.g. `addparent n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
2. The `LogicManager` calls the `AddressBookParser#parseCommand` to parse the command.
3. The `AddressBookParser` then creates a new `AddParentCommandParser` to parse the fields provided by the user and
   a new `Parent` and `AddParentCommand` is created.
4. The `AddParentCommand` checks if the `Parent` is valid by calling `Model#hasPerson` and retrieves
   the module if the person exists in Address Book.
6. `AddParentCommand` then attempts to add the module into the Model via `Model#addPerson`.
7. If `Parent`, the user inputs are valid, and the Address Book does not contain the module, `AddParentCommand` will
   successfully add the new `Parent` into Address Book.


The following two *Sequence Diagrams* shows how the `addparent` command works:

<div style="page-break-after: always;"></div>

Here is the *Sequence Diagram* showing how the parser works:

<puml src="diagrams/AddParentParseSequenceDiagram.puml" width="600" />

And here is the *Sequence Diagram* showing the command being executed:

<puml src="diagrams/AddParentExecuteSequenceDiagram.puml" width="450" />


<br>

---

### Edit Person Command

#### Overview

The `edit` command is used to change the information fields (e.g. name, email and address) for person in the address book.

The format for the `edit` command can be found [here](https://ay2425s1-cs2103t-t09-4.github.io/tp/UserGuide.html#editing-a-person-edit).

#### Feature details

The edit mechanism uses `EditPersonDescriptor` to abstract out the fields to edit. It can be found as a publicly accessible class within `EditCommand`. Currently, it contains fields for `name`, `phone`, `email`, `address`, `tags`, `lessonTime`, `education`, which are the only attributes of `Parent` or `Student` that can be edited for now. Besides this, it largely follows the parser and command structure as described in [Logic](#logic-component).

Below illustrates how `EditPersonDescriptor` is used.

<div style="page-break-after: always;"></div>

Here is a *Sequence Diagram* showing the parser in action:

<puml src="diagrams/EditParseSequenceDiagram.puml" width="500" />

And here is a *Sequence Diagram* showing the command being executed:

<puml src="diagrams/EditExecuteSequenceDiagram.puml" width="600" />


<br>

---

### Delete Person Command

#### Overview

The `delete` command is used to delete a person from the address book. The person can only be deleted if it is already present in the address book.<br>

The format of the `delete` command can be found [here](https://ay2425s1-cs2103t-t09-4.github.io/tp/UserGuide.html#deleting-a-person-delete).<br>

#### Feature details

1. The user executes the `delete` command. <br>
2. If the index field is not provided, an error message with the correct command usage will be shown.
3. If invalid index field is provided, an error message with the correct command usage will be shown. 
4. If all previous steps are completed without exceptions, the `Person` will be successfully deleted from the address book.

<br>

The sequence of the `delete` command is as follows:<br>

1. The user inputs the `delete` command.<br>
   e.g. `delete 1`
2. The `LogicManager` calls the `AddressBookParser#parseCommand` to parse the command.
3. The `AddressBookParser` then creates a new `DeleteCommandParser` to parse the fields provided by the user and a new `DeleteCommand` is created.
4. The `DeleteCommand` checks if the `index` is valid
5. If the `index` is valid and `Model` contains the persons, the `DeleteCommand` calls `Model#deletePerson` to delete the person from the address book.

The following sequence diagram shows how the `delete` command works:

<puml src="diagrams/DeleteParseSequenceDiagram.puml" width="700" />

<div style="page-break-after: always;"></div>

<puml src="diagrams/DeleteExecuteSequenceDiagram.puml" width="450" />

<br>

---

### Link Command

#### Overview

The `link` command is used to delete a person from the address book. The person can only be deleted if it is already present in the address book.<br>

The format of the `link` command can be found [here](https://ay2425s1-cs2103t-t09-4.github.io/tp/UserGuide.html#link-a-parent-to-a-student-link).<br>

#### Feature details

1. The user executes the `link` command.
2. If the `index` field is not provided, an error message with the correct command usage will be shown.
3. If invalid `index` field is provided, an error message with the correct command usage will be shown.
4. If all previous steps are completed without exceptions, the new `Person` will be successfully deleted from the address book.

<br>

The sequence of the `link` command is as follows:<br>

1. The user inputs the `link` command.<br>
   e.g. `link ch/Alex Yeoh pa/Bernice Yu`
2. The `LogicManager` calls the `AddressBookParser#parseCommand` to parse the command.
3. The `AddressBookParser` then creates a new `LinkCommandParser` to parse the fields provided by the user and a new `LinkCommand` is created.
4. The `LinkCommand` checks if the Student and Parent `name` is in the address book by calling `Model#personFromName`
5. If the Student `name` is valid and `Model` contains the person, the `LinkCommand` creates a new Student by calling `LinkCommand#createLinkedChild`.
6. If the Parent `name` is valid and `Model` contains the person, the `LinkCommand` creates a new Parent by calling `LinkCommand#createLinkedParent`.
7. `LinkCommand` updates the Student and Parent in the address book by calling `Model:setPerson`.

<div style="page-break-after: always;"></div>

The following sequence diagram shows how the `link` command works:

<puml src="diagrams/LinkParseSequenceDiagram.puml" width="450" />

<puml src="diagrams/LinkExecuteSequenceDiagram.puml" width="450" />

<br>

---

### Find Command

#### Overview

The `find` command is used to find a person from the address book. The person can only be found if it is already present in the address book.<br>

The format of the `find` command can be found [here](https://ay2425s1-cs2103t-t09-4.github.io/tp/UserGuide.html#locating-persons-by-name-find).<br>

#### Feature details

1. The user executes the `find` command.
2. If the `keyword` field is not provided, an error message with the correct command usage will be shown. 
3. If all previous steps are completed without exceptions, all `Person` matching at least one keyword will be successfully returned from the address book.

<br>

The sequence of the `find` command is as follows:<br>

1. The user inputs the `find` command.<br>
   e.g. `find Alex Bernice`
2. The `LogicManager` calls the `AddressBookParser#parseCommand` to parse the command.
3. The `AddressBookParser` then creates a new `FindCommandParser` to parse the fields provided by the user and a new `FindCommand` and `NameContainsKeywordsPredicate` is created.
4. The `FindCommand` updates the filter of the filtered person list in the address book by calling `Model#updateFilteredPersonList`.
5. The `FindCommand` sorts the filtered person list in the address book by calling `Model#sortByPin`.

The following sequence diagram shows how the `find` command works:

<puml src="diagrams/FindParseSequenceDiagram.puml" width="600" />

<puml src="diagrams/FindExecuteSequenceDiagram.puml" width="500" />

<br>

---

### Pin Command

#### Overview

The `pin` command is used to pin a person from the address book. The person pinned will be shifted to the top of the filtered list.<br>

The format of the `find` command can be found [here](https://ay2425s1-cs2103t-t09-4.github.io/tp/UserGuide.html#pinning-a-person-pin).<br>

#### Feature details

1. The user executes the `pin` command.
2. If the `index` field is not provided, an error message with the correct command usage will be shown.
3. If all previous steps are completed without exceptions, all `Person` pinned will be successfully shifted to the top of the filtered list in the address book.

<br>

The sequence of the `pin` command is as follows:<br>

1. The user inputs the `pin` command.<br>
   e.g. `pin 1 2`
2. The `LogicManager` calls the `AddressBookParser#parseCommand` to parse the command.
3. The `AddressBookParser` then creates a new `PinCommandParser` to parse the fields provided by the user and a new `PinCommand` is created.
4. The `PinCommand` pins all `Person` in the filtered person list in the address book by calling `Model#pinPerson`.
5. The `PinCommand` sorts the filtered person list in the address book by calling `Model#sortByPin`.

The following sequence diagram shows how the `pin` command works:

<puml src="diagrams/PinParseSequenceDiagram.puml" width="450" />

<puml src="diagrams/PinExecuteSequenceDiagram.puml" width="700" />

<br>

---

### [Proposed] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" width="400" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" width="400" />

Step 3. The user executes `add n/David …` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" width="400" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" width="400" />

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

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" width="400" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" width="450" />

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


## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

---

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* Private Tutor
* Teaches many students
* Prefer desktop apps over other types
* Can type fast
* Prefers typing to mouse interactions
* Is reasonably comfortable using CLI apps

**Value proposition**: Tutors find it challenging to keep track of student information if they are teaching multiple students. They could be juggling additional administrative duties with their teaching duties. GOATS can enhance their efficiency by managing student and parent data, freeing up their time and allowing them to focus on other tasks.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`


| Priority | As a …                                                                     | I want to …                                                                                 | So that I can…                                                                                                                     |
| -------- | --------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------- |
| `* * *`  | detail-oriented private tutor                                               | add my students' contact details, educational levels and subjects taught to the address book | I can keep track of all these details effectively                                                                                   |
| `* * *`  | tutor                                                                       | view all my students                                                                         | I know who are my students                                                                                                          |
| `* * *`  | long-time private tutor                                                     | delete contacts                                                                              | I can keep my address book concise and remove all unneeded contacts                                                                 |
| `* *`    | concerned private tutor                                                     | add my students' parents' contact details to the address book                                | I am able to contact them regarding their child’s academic progress                                                                |
| `* *`    | organised private tutor                                                     | link each student to their parents in the address book                                       | I can contact related users at once                                                                                                 |
| `* *`    | private tutor with multiple students                                        | tag students based on characteristics                                                        | I can keep track of additional information on students if required                                                                  |
| `* *`    | non-tech savvy private tutor                                                | have a help sheet with the provided commands                                                 | I do not have to spend too much time memorising commands in order to use the app                                                    |
| `* *`    | private tutor                                                               | filter the contact details in the address book by name and tag                               | I can find the relevant contact details more quickly                                                                                |
| `* *`    | organised private tutor                                                     | view all related users (e.g. students and parents) in one page                               | I do not have to manually search or filter for them                                                                                 |
| `* *`    | user                                                                        | find my students by name                                                                     | I will be able to quickly find their contact information                                                                            |
| `*`      | busy private tutor with too many students                                   | have a prompt for non-clients to be deleted automatically                                    | I do not have to manually keep track and delete them                                                                                |
| `*`      | user concerned with aesthetics                                              | edit the look of the GUI and select from several themes                                      | I can customise the look of the address book to suit my aesthetic preferences                                                       |
| `*`      | user who prefers CLI                                                        | customise the format of inputting contact data in the command line                           | I can use the app in a way that suits my workflow                                                                                   |
| `*`      | potential user exploring the address book                                   | try out the app's features with sample data                                                  | I can easily see how the app will look when it is in use                                                                            |
| `*`      | user ready to start using the app                                           | delete all existing data                                                                     | I can get rid of sample/experimental data I used for exploring the app                                                              |
| `*`      | private tutor who is juggling multiple subjects                             | filter students and parents by subject                                                       | I can contact all parties easily and at once when I need to make an announcement that concerns all students of a particular subject |
| `*`      | private tutor who has to schedule lessons manually by myself                | filter students by availability                                                              | I can keep track of when students are available when planning make-up or additional lessons                                         |
| `*`      | impatient user                                                              | execute tasks at low latency                                                                 | I do not spend unnecessary time waiting for the address book to load and handle my commands and can thus work more efficiently      |
| `*`      | user who prefers pictures over words                                        | attach a picture with the contact                                                            | I can have a more enjoyable GUI experience                                                                                          |
| `*`      | tutor who teaches students across different time zones                      | have a tag that tracks the difference in timezones                                           | I do not have to manually keep track of time zones and potentially make mistakes                                                    |
| `*`      | private tutor who may expand my business                                    | download all my data into a .csv file                                                        | I can transfer data across different digital platforms                                                                              |
| `*`      | private tutor who is starting to use the app                                | upload data of a collection of clients into the app                                          | It seamlessly stores all the previous clients data with minimal setup steps                                                         |
| `*`      | private tutor that specializes in special needs education                   | search, filter and categorize students by their needs                                        | I can better tailor my teaching to each individual needs                                                                            |
| `*`      | private tutor who contacts certain students or parents more frequently      | pin certain students to the top of the contact list                                          | I can access frequently contacted students or parents immediately                                                                   |
| `*`      | private tutor performing a task on my contact list (e.g. deleting contacts) | select multiple users and perform a single task on them                                      | I do not have to perform each task individually                                                                                     |
| `*`      | private tutor who wishes to organise my students more effectively           | add multiple users to a list and save it                                                     | I can keep track of them better and I do not need to perform tasks on them individually                                             |
| `*`      | tutor with compromised eyesight                                             | zoom in to the address book GUI                                                              | I do not have to strain my eyes to read what is on the screen                                                                       |
| `*`      | colorblind tutor                                                            | use a colorblind mode                                                                        | I would not confuse colors of the tags                                                                                              |
| `*`      | private tutor offering both online and in-person sessions                   | mark the preferred mode of learning for each student                                         | I can plan my schedule and resources accordingly                                                                                    |
| `*`      | tutor who receives payments from parents                                    | add payment statuses to each student                                                         | I can manage my finance-related tasks efficiently                                                                                   |
| `*`      | private tutor involved in long-term academic planning                       | keep a history of each student's progress and achievements                                   | I can monitor their growth over time and adjust my teaching strategies accordingly                                                  |
| `*`      | expert user of the addressbook                                              | set macros for filters/sort                                                                  | I would be able to quickly organise without typing long commands                                                                    |

### Use cases

(For all use cases below, the **System** is the `GOATS` application and the **Actor** is the `user`, unless specified otherwise)

#### **Use case: UC1 - Add a contact**

Guarantees: The new contact will be added to the GOATS Address Book, provided the command format is valid, all required information is present and valid, and the contact does not already exist in the GOATS Address Book.

**MSS**

1. User enters the new contact's details.
2. GOATS adds the contact.
3. GOATS displays the list of all unarchived contacts.
4. GOATS shows success message.

   Use case ends.

**Extensions**

* 1a. User enters an invalid command or invalid/missing details.
  * 1a1. GOATS shows an error message.
  
    Use case ends.

* 1b. User enters a contact with a name that already exists in the GOATS Address Book.
  * 1b1. GOATS shows an error message.
    
    Use case ends.

#### **Use case: UC2 - Delete contacts**

Preconditions: The contact(s) to delete exist in the displayed list of contacts.
Guarantees: The specified contact(s) will be removed from the GOATS Address Book, provided the command format is valid.

**MSS**

1. User requests to delete specific contact(s) in the displayed list of contacts by passing in the displayed indices of the contacts to be deleted.
2. GOATS deletes the specified contact(s).
3. GOATS removes the deleted contact(s) from the displayed list of contacts.
4. GOATS shows success message.

   Use case ends.

**Extensions**

* 1a. User enters an invalid command or invalid indices.

  * 1a1. GOATS shows an error message.

    Use case ends.

#### **Use case: UC3 - List contacts**

Guarantees: The displayed list be updated to show all unarchived contacts.

**MSS**

1. User requests to list contacts.
2. GOATS displays the list of all unarchived contacts.

   Use case ends.

**Extensions**

* 1a. User enters an invalid command.

  * 1a1. GOATS shows an error message.

    Use case ends.

#### **Use case: UC4 - Sort contacts**

Guarantees: The displayed list will be sorted in alphabetical order.

**MSS**

1. User requests to sort the displayed list of contacts.
2. GOATS sorts the list and displays the sorted list.

   Use case ends.

**Extensions**

* 1a. User enters an invalid command.

    * 1a1. GOATS shows an error message.

      Use case ends.

#### **Use case: UC5 - Edit a contact**

Preconditions: The contact to edit exists in the displayed list of contacts.
Guarantees: The specified contact will be updated with the new information, provided the command format and all information provided is valid.

**MSS**

1. User requests to edit a specific contact in the displayed list of contacts by entering the displayed index of the contact to be edited and the updated details.
2. GOATS edits the person.
3. GOATS displays the list of all unarchived contacts.
4. GOATS shows success message.

   Use case ends.

**Extensions**

* 1a. User enters an invalid command or invalid index/details.
    * 1a1. GOATS shows an error message.

      Use case ends.

* 1b. User attempts to update the contact with a name that already exists in the GOATS Address Book.
    * 1b1. GOATS shows an error message.

      Use case ends.

#### **Use case: UC6 - Find contacts by name**

Guarantees: The displayed list will be updated to show all contacts whose names contain any of the specified keywords.

**MSS**

1. User requests to find contacts by entering one or more keywords.
2. GOATS displays the list of all contacts whose names contain one or more of the specified keywords.

   Use case ends.

**Extensions**

* 1a. User enters an invalid command or does not provide any keywords.

    * 1a1. GOATS shows an error message.

      Use case ends.

#### **Use case: UC7 - Find contacts by tag**

Guarantees: The displayed list will be updated to show all contacts who has one or more of the specified keywords as tags.

**MSS**

1. User requests to find contacts by entering one or more keywords.
2. GOATS displays the list of all contacts who have a tag that matches any of the specified keywords.

   Use case ends.

**Extensions**

* 1a. User enters an invalid command or does not provide any keywords.

    * 1a1. GOATS shows an error message.

      Use case ends.

#### **Use case: UC8 - Find contacts by day of lesson**

Guarantees: The displayed list will be updated to show all students who have a lesson on the specified day of the week.

**MSS**

1. User requests to find contacts by entering one or more keywords.
2. GOATS displays the list of all students whose lesson day matches any of the specified keywords.

   Use case ends.

**Extensions**

* 1a. User enters an invalid command or does not provide any keywords.

    * 1a1. GOATS shows an error message.

      Use case ends.

#### **Use case: UC9 - Show user guide**

**MSS**

1. User requests to show user guide.
2. GOATS displays a link to the user guide.

   Use case ends.

**Extensions**

* 1a. User enters an invalid command.

    * 1a1. GOATS shows an error message.

      Use case ends.

#### **Use case: UC10 - Clearing all contacts**

Guarantees: All contacts in the list of contacts will be deleted from the GOATS Address Book.

**MSS**

1. User requests to clear all contacts.
2. GOATS deletes all contacts.
3. GOATS displays an empty list of contacts.
4. GOATS shows success message.

   Use case ends.

**Extensions**

* 1a. User enters an invalid command.

    * 1a1. GOATS shows an error message.

      Use case ends.

#### **Use case: UC11 - Archive a contact**

Preconditions: The contact(s) to archive exist in the displayed list of contacts.
Guarantees: The specified contact(s) will be archived, provided the command format is valid.

**MSS**

1. User requests to archive specific contact(s) in the displayed list of contacts by passing in the displayed indices of the contacts to be archived.
2. GOATS archives the specified contact(s).
3. GOATS shows success message.

   Use case ends.

**Extensions**

* 1a. User enters an invalid command or invalid indices.

    * 1a1. GOATS shows an error message.

      Use case ends.

#### **Use case: UC12 - Unarchive a contact**

Preconditions: The contact(s) to unarchive exist in the displayed list of contacts.
Guarantees: The specified contact(s) will be unarchived, provided the command format is valid.

**MSS**

1. User requests to unarchive specific contact(s) in the displayed list of contacts by passing in the displayed indices of the contacts to be unarchived.
2. GOATS unarchives the specified contact(s).
3. GOATS shows success message.

   Use case ends.

**Extensions**

* 1a. User enters an invalid command or invalid indices.

    * 1a1. GOATS shows an error message.

      Use case ends.

#### **Use case: UC13 - List archived contacts**

Guarantees: The displayed list will be updated to show all archived contacts.

**MSS**

1. User requests to list archived contacts.
2. GOATS displays the list of all archived contacts.

   Use case ends.

**Extensions**

* 1a. User enters an invalid command.

    * 1a1. GOATS shows an error message.

      Use case ends.

#### **Use case: UC14 - Pin a contact**

Preconditions: The contact(s) to pin exist in the displayed list of contacts.
Guarantees: The specified contact(s) will be pinned, and the displayed list will be updated to show all pinned contacts at the top of the list, provided the command format is valid.

**MSS**

1. User requests to pin specific contact(s) in the displayed list of contacts by passing in the displayed indices of the contacts to be pinned.
2. GOATS pins the specified contact(s).
3. GOATS updates the displayed list of contacts to show all pinned contacts at the top of the list.
4. GOATS shows success message.

   Use case ends.

**Extensions**

* 1a. User enters an invalid command or invalid indices.

    * 1a1. GOATS shows an error message.

      Use case ends.

#### **Use case: UC15 - Unpin a contact**

Preconditions: The contact(s) to unpin exist in the displayed list of contacts.
Guarantees: The specified contact(s) will be unpinned, and the displayed list will be updated to show all pinned contacts at the top of the list, provided the command format is valid.

**MSS**

1. User requests to unpin specific contact(s) in the displayed list of contacts by passing in the displayed indices of the contacts to be unpinned.
2. GOATS unpins the specified contact(s).
3. GOATS updates the displayed list of contacts to show all pinned contacts at the top of the list.
4. GOATS shows success message.

   Use case ends.

**Extensions**

* 1a. User enters an invalid command or invalid indices.

    * 1a1. GOATS shows an error message.

      Use case ends.

#### **Use case: UC16 - Update a student's grade**

Preconditions: The student whose grade is to be updated exists in the displayed list of contacts.
Guarantees: The grade of the specified contact will be updated, provided the command format is valid and the specified contact is a student.

**MSS**

1. User requests to update the grade of a specific contact in the displayed list of contacts by entering the displayed index of the contact to be edited and the updated grade.
2. GOATS edits the contact.
3. GOATS displays the list of all unarchived contacts.
4. GOATS shows success message.

   Use case ends.

**Extensions**

* 1a. User enters an invalid command, an invalid index or an invalid grade value.
    * 1a1. GOATS shows an error message.

      Use case ends.

* 1b. GOATS detects that the specified contact is not a student.
  * 1b1. GOATS shows an error message.

    Use case ends.

#### **Use case: UC17 - Link a student to a parent**

Preconditions: The student and parent to be linked both exist in the GOATS Address Book.
Guarantees: The student and the parent will be linked in a parent-child relationship, provided the command format and all information provided is valid.

**MSS**

1. User requests to link a parent and a child by entering the names of both the parent and the child.
2. GOATS links the parent and the child.
3. GOATS updates the displayed list of contacts to show the link.
4. GOATS shows success message.

**Extensions**

* 1a. User enters an invalid command.
    * 1a1. GOATS shows an error message.

      Use case ends.

* 1b. GOATS detects that the specified contacts do not consist of a student and a parent.
    * 1b1. GOATS shows an error message.

      Use case ends.

* 1c. User enters a name that does not exist in the GOATS Address Book.
    * 1c1. GOATS shows an error message.

      Use case ends.

#### **Use case: UC18 - Unlink a student from a parent**

Preconditions: The student to be unlinked exists in the GOATS Address Book, and has a link to a parent.
Guarantees: The student and the parent will be linked in a parent-child relationship, provided the command format and all information provided is valid.

**MSS**

1. User requests to unlink a child from a parent by entering the name of the child.
2. GOATS unlinks the parent from the child.
3. GOATS updates the displayed list of contacts to remove the link.
4. GOATS shows success message.

**Extensions**

* 1a. User enters an invalid command.
    * 1a1. GOATS shows an error message.

      Use case ends.

* 1b. GOATS detects that the specified contact is not a student.
    * 1b1. GOATS shows an error message.

      Use case ends.

* 1c. User enters a name that does not exist in the GOATS Address Book.
    * 1c1. GOATS shows an error message.

      Use case ends.

* 1d. GOATS detects that the specified student has no links to any parent.
    * 1d1. GOATS shows an error message.

      Use case ends.

#### **Use case: UC19 - Exit application**

**MSS**

1. User requests to exit application.
2. GOATS exits and closes.

   Use case ends.

**Extensions**

* 1a. User enters an invalid command.

    * 1a1. GOATS shows an error message.

      Use case ends.

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. Should work without internet connection
5. Saved data should be kept in a single file to allow for easy transfer to a different device.
6. The system should respond within two seconds.
7. Should work without having to use an installer or compiler.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private Contact Details**: A contact detail that is to be kept confidential.
* **Graphical User Interface (GUI)**: A GUI is a form of user interface through which users interact with electronic devices via visual indicator representations.
* **API**: The Application Programming Interface specifies the interface through which software and other programs interact.
* **Main Success Scenario (MSS)**: The most straightforward interaction for a given use case, which assumes that nothing goes wrong.
* **JSON**: JavaScript Object Notation, is a common file format which stores data in key-value pairs and arrays.
* **Command**: A command is a specific instruction to be provided to `GOATS` in order to perform a certain action, like adding a new contact to the GOATS Address Book. Commands will be the primary way of interacting with `GOATS`.
* **Parameter**: Parameters are pieces of data that must be passed to certain commands to tell `GOATS` which actions to perform. For example, the delete command requires one or more integers as a parameters, which will specify the contacts to be deleted from the GOATS Address Book.
* **Prefix**: Prefixes are unique identifiers in front of parameters so that `GOATS` understands what kind of values they are. For example, the prefix "n/" lets `GOATS` know that a name is expected to follow behind it, while the prefix "d/" lets `GOATS` know that a date is expected.
* **Archive**: A command that stores all unused contacts for later use, which can be retrieved by un-archiving.
* **Parent**: A person that is responsible for a Student.
* **Student**: A person studying under `user`.
* **GOATS Address Book**: The list of all contacts stored by GOATS.
* **Education**: The education level of the Student
* **Grade**: The grade of the Student
* **LessonTime**: The tuition lesson time of the Student.

---

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file
      Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Deleting a person

1. Deleting a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons should be displayed in the list.

    1. Test case: `delete 1`<br>
       Expected: The first contact is deleted from the list. Details of the deleted contact are shown in the status message.

    1. Test case with multiple indices: `delete 1 2 3`<br>
       Expected: The first, second, and third contacts are deleted from the list. Status message reflects successful deletion of multiple contacts.

    1. Test case: `delete 0`<br>
       Expected: No contact is deleted. Error details are shown in the status message.

    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous; no contact is deleted, and an error message is displayed in the status message.


### Finding Contacts by Name, Tag, and Lesson Day

1. Finding contacts by name while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list with diverse names.

    1. Test case: `find John`<br>
       Expected: All contacts with names containing "John" are displayed in the list. Number of matching contacts are shown in the status message.

    1. Test case with multiple keywords: `find Alex David`<br>
       Expected: All contacts with names containing "Alex" or "David" appear in the list. Number of matching contacts are shown in the status message.

    1. Test case: `find {NON-EXISTENT NAME]`<br>
       Expected: No contacts are shown. Status message indicates 0 matches found.

2. Finding contacts by tag while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Some contacts should have tags such as "friend" or "tutor".

    1. Test case: `findtag friend`<br>
       Expected: All contacts tagged with "friend" are displayed in the list. Status message reflects the number of matching contacts.

    1. Test case with multiple tags: `findtag friend colleague`<br>
       Expected: All contacts tagged with either "friend" or "colleague" appear in the list. Status message reflects the number of matching contacts.

    1. Test case: `findtag [NON-EXISTENT TAG]`<br>
       Expected: No contacts are shown. Status message indicates 0 matches found.

3. Finding students by lesson day while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Some students should have lesson days like "Tuesday" or "Wednesday".

    1. Test case: `findday tuesday`<br>
       Expected: All students with lessons on Tuesday are displayed in the list. Status message reflects the number of matching contacts.

    1. Test case with multiple days: `findday tuesday wednesday`<br>
       Expected: All students with lessons on either Tuesday or Wednesday are displayed. Status message reflects the number of matching contacts.

    1. Test case: `findday [NON-EXISTENT DAY]`<br>
       Expected: No contacts are shown. Status message indicates no matches found.

### Archiving and Unarchiving Contacts

1. Archiving contacts while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. All contacts should be displayed in the list.

    1. Test case: `archive 1`<br>
       Expected: The first contact is archived and removed from the main contact list. A status message confirms the archiving.

    1. Test case with multiple indices: `archive 1 2 3`<br>
       Expected: The first, second, and third contacts are archived and removed from the main contact list. Status message reflects successful archiving of multiple contacts.

    1. Test case: `archive 0`<br>
       Expected: No contact is archived. Error details are shown in the status message.

    1. Other incorrect archive commands to try: `archive`, `archive x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous; no contact is archived, and an error message is displayed in the status message.

2. Unarchiving contacts while viewing archived contacts

    1. Prerequisites: Archive at least one contact as above. List all archived contacts using the `listarchive` command. Archived contacts should be displayed.

    1. Test case: `unarchive 1`<br>
       Expected: The first archived contact is unarchived and moved back to the main contact list. A status message confirms the unarchiving.

    1. Test case with multiple indices: `unarchive 1 2 3`<br>
       Expected: The first, second, and third archived contacts are unarchived and returned to the main contact list. Status message reflects successful unarchiving of multiple contacts.

    1. Test case: `unarchive 0`<br>
       Expected: No contact is unarchived. Error details are shown in the status message.

    1. Other incorrect unarchive commands to try: `unarchive`, `unarchive x`, `...` (where x is larger than the archived list size)<br>
       Expected: Similar to previous; no contact is unarchived, and an error message is displayed

### Pinning and Unpinning Contacts

1. Pinning contacts while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons should be displayed in the list.

    1. Test case: `pin 1`<br>
       Expected: The first contact is pinned and moved to the top of the contact list. A status message confirms the pinning.

    1. Test case with multiple indices: `pin 1 2 3`<br>
       Expected: The first, second, and third contacts are pinned and appear at the top of the list. Status message reflects successful pinning of multiple contacts.

    1. Test case: `pin 0`<br>
       Expected: No contact is pinned. Error details are shown in the status message.

    1. Other incorrect pin commands to try: `pin`, `pin x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous; no contact is pinned, and an error message is displayed in the status message.

2. Unpinning contacts while pinned contacts are at the top of the list

    1. Prerequisites: Pin at least one contact using the `pin` command, then use `list` to verify the contact is pinned and at the top.

    1. Test case: `unpin 1`<br>
       Expected: The first pinned contact is unpinned and placed back in the sorted order. A status message confirms the unpinning.

    1. Test case with multiple indices: `unpin 1 2 3`<br>
       Expected: The first, second, and third pinned contacts are unpinned and returned to their original sorted order. Status message reflects successful unpinning of multiple contacts.

    1. Test case: `unpin 0`<br>
       Expected: No contact is unpinned. Error details are shown in the status message.

    1. Other incorrect unpin commands to try: `unpin`, `unpin x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous; no contact is unpinned, and an error message is displayed in the status message.

### Linking and Unlinking Contacts

1. Linking a parent to a student

    1. Prerequisites: Ensure that both a student (e.g., "John Doe") and a parent (e.g., "Jane Doe") contact exist in the contact list. Use `find` to verify both contacts are present.

    1. Test case: `link ch/John Doe pa/Jane Doe`<br>
       Expected: The specified student "John Doe" and parent "Jane Doe" are linked, establishing a parent-child relationship. A status message confirms the successful linking.

    1. Test case: `link ch/[NON-EXISTENT STUDENT NAME] pa/Jane Doe`<br>
       Expected: No link is created. Error details are shown in the status message, indicating that the student contact does not exist.

    1. Test case: `link ch/John Doe pa/[NON-EXISTENT PARENT NAME]`<br>
       Expected: No link is created. Error details are shown in the status message, indicating that the parent contact does not exist.

    1. Test case: `link ch/[NON-EXISTENT STUDENT NAME] pa/[NON-EXISTENT PARENT NAME]`<br>
       Expected: No link is created. Error details are shown in the status message.

2. Unlinking a parent from a student

    1. Prerequisites: Ensure a parent (e.g., "Jane Doe") is already linked to a student (e.g., "John Doe"). Use `list` to verify the linked relationship.

    1. Test case: `unlink ch/John Doe`<br>
       Expected: The parent-child relationship between "John Doe" and "Jane Doe" is removed, and "John Doe" is no longer linked to "Jane Doe". A status message confirms the successful unlinking.

    1. Test case: `unlink ch/[NON-EXISTENT STUDENT NAME]`<br>
       Expected: No unlinking occurs. Error details are shown in the status message, indicating that the student contact does not exist.

    1. Test case: `unlink ch/John Doe ch/Alice Smith pa/Jane Doe`<br>
       Expected: No unlinking occurs. Error details are shown in the status message.

    1. Test case: `unlink ch/John Doe` when no parent is linked<br>
       Expected: No change occurs. An error message indicates that "John Doe" does not have a linked parent.

### Loading data

1. Dealing with corrupted data files

   1. Corrupted data files can be simulated by editing data/addressbook.json file

   2. Test case: Start GOATS app
      Expected: GOATS should clear all data from the corrupted file and user is presented with an empty addressbook

1. Dealing with missing data files

   1. Missing data files can be simulated by deleting data/addressbook.json file

   2. Test case: Start GOATS app
      Expected: GOATS should initialise a new data/addressbok.json file with sample data and user is presented an addressbook with that data

## Appendix: Effort

### Difficulty Level

The project was a step-up in difficulty as compared to AddressBook Level3 (AB3), due to the number of commands added, and the need to support multiple entity types.
Contacts in AB3 only constituted one single entity type, but in GOATS, they are split it into two different entities: **Student** and **Parent**, both with differing functionalities and representation in the UI.
In addition, a large number of commands were added, with a high level of interaction between them, which made it difficult to keep track of and fix all the unintended behaviours and interactions that inevitably arose.

### Challenges Faced

* **Mitigating Unintended Behaviours and Interactions Between Commands**: Some of our commands initially had unintended behaviours when used after one another, which proved difficult to detect and to rectify. 
* **Testing and Coverage**: Given the large number of commands added, it was difficult to maintain the same level of test coverage (roughly 75% in AB3).
* **Implementation of Multiple Entity Types and Relationships Between Entities**: A lot of effort was required to implement the Student and Parent classes without excessive code duplication, and to implement the Link between Student and Parent.

### Effort Required

The project required significant effort due to the implementation of the following features on top of AB3:

* Addition of two distinct entity types: **Student** and **Parent**, each with different functionalities and both storing references to each other
* Addition and Modification of multiple additional commands and fields to support Tutor Use Cases: Grade, Education Level, Lesson Time
* Addition of an Archive Feature

Each member of the group wrote an average of 1,400 lines of code.

### Achievements

Over the course of the project, the group achieved the following:

* Built upon AB3 to create a contact management system specifically tailored to the needs of private tutors
* Gained familiarity with the software development process, e.g. forking workflow, documentation, sprints etc.
* Improved technical proficiency with tools such as IntelliJ IDE, JUnit, GitHub etc.

## Appendix: Planned Enhancements
Team Size: 5. Hence, maximum number of planned enhancements is **10**.

### 1. Hide archived contacts when using find commands.
* **Current Situation**: When using find commands, all contacts that match the given keywords will be displayed, even if they were archived.
* **Proposed Improvement**: Move archived contacts to a separate list, so that they do not show up when using find commands.

### 2. Prevent archiving of already archived contacts.
* **Current Situation**: Archiving already archived contacts is allowed, and displays a success message.
* **Proposed Improvement**: Add an error message to inform the user that the contact is already archived.

### 3. Prevent unarchiving of already unarchived contacts.
* **Current Situation**: Unarchiving already unarchived contacts is allowed, and displays a success message.
* **Proposed Improvement**: Add an error message to inform the user that the contact is already unarchived.

### 4. Prevent pinning of already pinned contacts.
* **Current Situation**: Pinning already pinned contacts is allowed, and displays a success message.
* **Proposed Improvement**: Add an error message to inform the user that the contact is already pinned.

### 5. Prevent unpinning of already unpinned contacts.
* **Current Situation**: Unpinning already unpinned contacts is allowed, and displays a success message.
* **Proposed Improvement**: Add an error message to inform the user that the contact is already unpinned.

### 6. Improve name validation to allow for special characters.
* **Current Situation**: Names must be alphanumeric, hence characters such as '/', ',' or '-' are not allowed to be input into the Address Book despite their prevalence in names in real life.
* **Proposed Improvement**: Update the name validation to allow for special characters to be included in names.

### 7. Maintain the same displayed list when editing contacts.
* **Current Situation**: Upon a successful edit of a contact, the displayed list will update to show the list of all unarchived contacts.
* **Proposed Improvement**: Maintain the same list of contacts from before the edit command was executed, and display the updated information of the edited person.

### 8. Maintain the same displayed list when editing contacts' grades.
* **Current Situation**: Upon a successful edit of the grade a contact, the displayed list will update to show the list of all unarchived contacts.
* **Proposed Improvement**: Maintain the same list of contacts from before the grade command was executed, and display the updated information of the edited person.

### 9. Improve input validation for `findday` command to only take in days of the week.
* **Current Situation**: Users can input any value for the `DAY` parameter of the `findday` command.
* **Proposed Improvement**: Create an enum of valid `DAY` inputs (i.e. the days of the week), and display an error message to the user if the value input to the `DAY` parameter does not match any of the valid inputs.

### 10. Improve UI to view contacts in a separate window
* **Current Situation**: If the name of the contact exceeds 14 characters, users can only view the name via commands. (pin etc.)
* **Proposed Improvement**: Create a new command that displays full details of the contact in a new window.

