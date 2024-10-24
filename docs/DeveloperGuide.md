---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# StudentManagerPro Developer Guide

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

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

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

* secondary school teacher
* many students to track
* many tests and submissions to track
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: track students’ test scores, submissions, progress and also, access their particulars with ease, all in one place!


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                             | I want to …​                                    | So that I can…​                                            |
|----------|-----------------------------------------------------|-------------------------------------------------|------------------------------------------------------------|
| `* * *`  | prepared teacher                                    | add student's email                             | email the student when I need to                           |
| `* * *`  | caring teacher                                      | add student's name                              | call a student by his/her name                             |
| `* * *`  | efficient teacher                                   | add student's register number                   | identify a student more quickly                            |
| `* * *`  | teacher wanting to split students for group project | add student's sex                               | see how many students of each gender I have                |
| `* * *`  | caring teacher                                      | add student's address                           | visit a student who may be sick at home                    |
| `* * *`  | prepared teacher                                    | add student's contact number                    | call up the student when I need to contact him/her         |
| `* * *`  | prepared teacher                                    | add student's emergency contact name            | identify the person I am calling if there are emergencies  |
| `* * *`  | prepared teacher                                    | add student's emergency contact number          | notify the person in case of emergencies                   |
| `* * *`  | prepared teacher                                    | add student's class                             | identify which student is in which class                   |
| `* * *`  | diligent teacher                                    | remove a student from the app                   | ensure my records are accurate when they drop out          |
| `* *`    | caring teacher                                      | add student's photo                             | know what my students look like                            |
| `* *`    | lazy teacher                                        | mass add student information                    | save the trouble of adding them one by one                 |
| `* *`    | lazy teacher                                        | mass delete all dummy data                      | save the trouble of removing them one by one               |
| `* *`    | diligent teacher                                    | assign roles to students                        | manage students with the specific roles                    |
| `* *`    | neat teacher                                        | group students by their class                   | manage and access information by class                     |
| `* *`    | teacher wanting to split students for group project | separate students into project groups           | manage their project work within the app                   |
| `* *`    | prepared teacher                                    | update a student's information                  | have the most current details when there is a change       |
| `* *`    | diligent teacher                                    | assign progress tags to individual students     | categorise their performance in class                      |
| `* *`    | efficient teacher                                   | sort the students by name                       | arrange the students lexicographically for exam conditions |
| `* *`    | caring teacher                                      | add a comment for a student                     | take note of that student's particular trait               |
| `* *`    | strict teacher                                      | track a student's submissions                   | see which students did not submit tasks on time            |
| `* *`    | strict teacher                                      | track student attendance                        | address absenteeism and its impact on student performance  |
| `* *`    | diligent teacher                                    | add a new test for all my students              | keep track of all the students' results                    |
| `* *`    | diligent teacher                                    | add the scores of the students                  | have an overview of everyone's results                     |
| `*`      | picky teacher                                       | customize the app settings                      | align the configuration with my preferences                |
| `*`      | teacher who likes to have everything in one app     | create a seating arrangement for the class      | edit the seating arrangement any time                      |
| `*`      | diligent teacher                                    | export information of all my graduated students | store them into the school database                        |


### Use cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

**System: StudentManagerPro**

**Use case: UC01 Add Student's Name**

**Actor: User**

**Preconditions: User is logged in.**

**Guarantees:**
* If successful, the student's name is added to the system and can be used to track their academic progress.
* If an invalid name is given as input, a corresponding error message is displayed.

**MSS**

1.  User gives the command to add a student's name to the StudentManagerPro.
2.  System validates the input's format.
3.  System validates the input.
4.  System adds the student to the system.
5.  System adds the student name to the student profile.
6.  System confirms the success by displaying a success message.
    Use case ends.

**Extensions**

* 2a. User enters invalid characters.
    * 2a1. System displays an error message to ask for a valid name command format, with no special characters.
      Use case ends.

* 3a. User leaves the name field empty.
    * 3a1. System displays an error message to ask for a valid name.
      Use case ends.

* 3b. User enters a duplicate name.
    * 3b1. System displays an error message notifying that the name already exists in the system.
      Use case ends.


**System: StudentManagerPro**

**Use case: UC02 Add Student's Email**

**Actor: User**

**Preconditions:**
* User is logged in.
* Student profile has been created through the <u>addition of the student name(UC01).</u>

**Guarantees:**
* If successful, the student's email is added to the student profile and saved in the system.
* If an invalid email is given as input, a corresponding error message is displayed.

**MSS**

1.  User gives the command to add a student's email to a student's profile in the StudentManagerPro.
2.  System validates the input's format.
3.  System validates the input.
4.  System adds the email to the student profile in the system.
5.  System confirms the success by displaying a success message.
    Use case ends.

**Extensions**

* 2a. User enters invalid characters.
    * 2a1. System displays an error message to ask for a valid email command format, with no special characters.
      Use case ends.

* 3a. User leaves the email field empty.
    * 3a1. System displays an error message to ask for a valid email.
      Use case ends.

* 3b. User enters a duplicate email.
    * 3b1. System displays an error message notifying that the email already exists in the system.
      Use case ends.


**System: StudentManagerPro**

**Use case: UC03 Add Student's Register Number**

**Actor: User**

**Preconditions:**
* User is logged in.
* Student profile has been created through the <u>addition of the student name(UC01).</u>

**Guarantees:**
* If successful, the student's register number is added to the student profile and saved in the system.
* If an invalid register number is given as input, a corresponding error message is displayed.

**MSS**

1.  User gives the command to add a student's register number to a student's profile in the StudentManagerPro.
2.  System validates the input's format.
3.  System validates the input.
4.  System adds the register number to the student profile in the system.
5.  System confirms the success by displaying a success message.
    Use case ends.

**Extensions**

* 2a. User enters invalid characters.
    * 2a1. System displays an error message to ask for a valid register number command format, with no
      special characters.
      Use case ends.

* 3a. User leaves the register number field empty.
    * 3a1. System displays an error message to ask for a valid register number.
      Use case ends.

* 3b. User enters a duplicate register number.
    * 3b1. System displays an error message notifying that the register number already exists in the system.
      Use case ends.

**System: StudentManagerPro**

**Use case: UC04 Add Student's Sex**

**Actor: User**

**Preconditions:**
* User is logged in.
* Student profile has been created through the <u>addition of the student name(UC01).</u>

**Guarantees:**
* If successful, the student's sex is added to the student profile and saved in the system.
* If an invalid sex is given as input, a corresponding error message is displayed.

**MSS**

1.  User gives the command to add a student's sex to a student's profile in the StudentManagerPro.
2.  System validates the input's format.
3.  System validates the input.
4.  System adds the sex to the student profile in the system.
5.  System confirms the success by displaying a success message.
    Use case ends.

**Extensions**

* 2a. User enters invalid characters.
    * 2a1. System displays an error message to ask for a valid sex command format, with no
      special characters.
      Use case ends.

* 3a. User leaves the sex field empty.
    * 3a1. System displays an error message to ask for a valid sex.
      Use case ends.

**System: StudentManagerPro**

**Use case: UC05 Add Student's Address**

**Actor: User**

**Preconditions:**
* User is logged in.
* Student profile has been created through the <u>addition of the student name(UC01).</u>

**Guarantees:**
* If successful, the student's address is added to the student profile and saved in the system.
* If an invalid address is given as input, a corresponding error message is displayed.

**MSS**

1.  User gives the command to add a student's address to a student's profile in the StudentManagerPro.
2.  System validates the input's format.
3.  System validates the input.
4.  System adds the address to the student profile in the system.
5.  System confirms the success by displaying a success message.
    Use case ends.

**Extensions**

* 2a. User enters invalid characters.
    * 2a1. System displays an error message to ask for a valid address command format, with only acceptable
      special characters.
      Use case ends.

* 3a. User leaves the address field empty.
    * 3a1. System displays an error message to ask for a valid address.
      Use case ends.

**System: StudentManagerPro**

**Use case: UC06 Add Student's Contact Number**

**Actor: User**

**Preconditions:**
* User is logged in.
* Student profile has been created through the <u>addition of the student name(UC01).</u>

**Guarantees:**
* If successful, the student's contact number is added to the student profile and saved in the system.
* If an invalid contact number is given as input, a corresponding error message is displayed.

**MSS**

1.  User gives the command to add a student's contact number to a student's profile in the StudentManagerPro.
2.  System validates the input's format.
3.  System validates the input.
4.  System adds the contact number to the student profile in the system.
5.  System confirms the success by displaying a success message.
    Use case ends.

**Extensions**

* 2a. User enters invalid characters.
    * 2a1. System displays an error message to ask for a valid contact number command format, with no
      special characters.
      Use case ends.

* 3a. User leaves the contact number field empty.
    * 3a1. System displays an error message to ask for a valid contact number.
      Use case ends.

* 3b. User enters a duplicate contact number.
    * 3b1. System displays an error message notifying that the contact number already exists in the system.
      Use case ends.


**System: StudentManagerPro**

**Use case: UC07 Add Student's Emergency Contact Name**

**Actor: User**

**Preconditions:**
* User is logged in.
* Student profile has been created through the <u>addition of the student name(UC01).</u>

**Guarantees:**
* If successful, the student's emergency contact name is added to the student profile and saved in the system.
* If an invalid name is given as input, a corresponding error message is displayed.

**MSS**

1.  User gives the command to add a student's emergency contact name to a student's profile in the StudentManagerPro.
2.  System validates the input's format.
3.  System validates the input.
4.  System adds the emergency contact name to the student profile in the system.
5.  System confirms the success by displaying a success message.
    Use case ends.

**Extensions**

* 2a. User enters invalid characters.
    * 2a1. System displays an error message to ask for a valid emergency contact name command format, with no
      special characters.
      Use case ends.

* 3a. User leaves the emergency contact name field empty.
    * 3a1. System displays an error message to ask for a valid emergency contact name.
      Use case ends.


**System: StudentManagerPro**

**Use case: UC08 Add Student's Emergency Contact Number**

**Actor: User**

**Preconditions:**
* User is logged in.
* Student profile has been created through the <u>addition of the student name(UC01).</u>

**Guarantees:**
* If successful, the student's emergency contact number is added to the student profile and saved in the system.
* If an invalid contact number is given as input, a corresponding error message is displayed.

**MSS**

1.  User gives the command to add a student's emergency contact number to a student's profile in the StudentManagerPro.
2.  System validates the input's format.
3.  System validates the input.
4.  System adds the emergency contact number to the student profile in the system.
5.  System confirms the success by displaying a success message.
    Use case ends.

**Extensions**

* 2a. User enters invalid characters.
    * 2a1. System displays an error message to ask for a valid emergency contact number command format, with no
      special characters.
      Use case ends.

* 3a. User leaves the emergency contact number field empty.
    * 3a1. System displays an error message to ask for a valid emergency contact number.
      Use case ends.

**System: StudentManagerPro**

**Use case: UC09 Add Student's Class**

**Actor: User**

**Preconditions:**
* User is logged in.
* Student profile has been created through the <u>addition of the student name(UC01).</u>

**Guarantees:**
* If successful, the student's class is added to the student profile and saved in the system.
* If an invalid class is given as input, a corresponding error message is displayed.

**MSS**

1.  User gives the command to add a student's class to a student's profile in the StudentManagerPro.
2.  System validates the input's format.
3.  System validates the input.
4.  System adds the class to the student profile in the system.
5.  System confirms the success by displaying a success message.
    Use case ends.

**Extensions**

* 2a. User enters invalid characters.
    * 2a1. System displays an error message to ask for a valid class command format, with no
      special characters.
      Use case ends.

* 3a. User leaves the class field empty.
    * 3a1. System displays an error message to ask for a valid class.
      Use case ends.


**System: StudentManagerPro**

**Use case: UC10 Remove Student from the System**

**Actor: User**

**Preconditions:**
* User is logged in.
* Student to be removed exists in the system.

**Guarantees:**
* If successful, the student will be removed from the system.
* If student does not exist or an invalid input is entered, a corresponding error message is displayed.

**MSS**

1.  User gives the command to remove a student from the StudentManagerPro.
2.  System validates the input's format.
3.  System validates the input.
4.  System removes the student from the system.
5.  System confirms the success by displaying a success message.
    Use case ends.

**Extensions**

* 2a. User enters invalid characters.
    * 2a1. System displays an error message to ask for a valid name command format, with no
      special characters.
      Use case ends.

* 3a. User leaves the name field empty.
    * 3a1. System displays an error message to ask for a valid name.
      Use case ends.

* 3b. User enters a name that does not exist in the system.
    * 3b1. System displays an error message to ask for a valid name of a student in the system.
      Use case ends.


### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Should be for a single user (i.e. not a multi-user product).
5.  Data should be stored locally and should be in a human editable text file.
6.  Should not use DBMS to store data.
7.  Should follow the Object-oriented paradigm primarily.
8.  Should work without requiring an installer.
9.  Should not depend on any remote server.
10. Use of third-party frameworks/libraries/services is subject to approval by the teaching team.
11. GUI should work well (i.e., should not cause any resolution-related inconveniences to the user) for standard screen resolutions 1920x1080 and higher and for screen scales 100% and 125%. In addition, the GUI should be usable (i.e., all functions can be used even if the user experience is not optimal) for resolutions 1280x720 and higher and for screen scales 150%.
12. Should be packaged into a single JAR file not exceeding 100MB.
13. Documents should not exceed 15MB/file.
14. DG and UG should be PDF-friendly (no expandable panels, embedded videos, animated GIFs etc.).
15. Should primarily take in command line interface (CLI) inputs (i.e. input should primarily be text commands with minimal non-CLI inputs such as clicking).
16. All user operations should complete within 100 ms.
17. Product should be developed in a breadth-first incremental manner over the project duration.
18. Final product should be a result of evolving/enhancing/morphing the given codebase.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **DBMS (Database Management System)**: Software for storing, retrieving, and managing data
* **Developer Guide (DG)**: A document that provides technical details about the product's design and implementation
* **User Guide (UG)**: A document that provides instructions for users on how to use the product's features
* **CLI (Command Line Interface)**: An interface where the user types commands to interact with the software
* **Class Grouping**: The division of students into their respective classes, which can be managed and organized in the app for easy access
* **Project Grouping**: The arrangement of students into smaller groups for project purposes
* **Tags**: Labels assigned to students to indicate specific roles or characteristics
* **Student Profile**: A record within the system that contains information about a specific student

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

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
