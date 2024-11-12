---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# StudentManagerPro Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the app.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103-F12-4/tp/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103-F12-4/tp/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the app.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the app in memory.
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

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103-F12-4/tp/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103-F12-4/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103-F12-4/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103-F12-4/tp/tree/master/src/main/java/seedu/address/logic/Logic.java)

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
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103-F12-4/tp/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" alt="Structure of the Model Component"/>


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" alt="Structure of the Better Model Component"/>

</box>

### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103-F12-4/tp/tree/master/src/main/java/seedu/address/storage/Storage.java)

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

| Priority | As a …​                                             | I want to …​                                    | So that I can…​                                                     |
|----------|-----------------------------------------------------|-------------------------------------------------|---------------------------------------------------------------------|
| `* * *`  | prepared teacher                                    | add student's email                             | email the student when I need to                                    |
| `* * *`  | caring teacher                                      | add student's name                              | call a student by his/her name                                      |
| `* * *`  | efficient teacher                                   | add student's register number                   | identify a student more quickly                                     |
| `* * *`  | teacher wanting to split students for group project | add student's sex                               | see how many students of each gender I have                         |
| `* * *`  | caring teacher                                      | add student's address                           | visit a student who may be sick at home                             |
| `* * *`  | prepared teacher                                    | add student's contact number                    | call up the student when I need to contact him/her                  |
| `* * *`  | prepared teacher                                    | add student's emergency contact name            | identify the person I am calling if there are emergencies           |
| `* * *`  | prepared teacher                                    | add student's emergency contact number          | notify the person in case of emergencies                            |
| `* * *`  | prepared teacher                                    | add student's class                             | identify which student is in which class                            |
| `* * *`  | diligent teacher                                    | remove a student from the app                   | ensure my records are accurate when they are no longer in the class |
| `* *`    | caring teacher                                      | add student's photo                             | know what my students look like                                     |
| `* *`    | lazy teacher                                        | mass add student information                    | save the trouble of adding them one by one                          |
| `* *`    | lazy teacher                                        | mass delete all dummy data                      | save the trouble of removing them one by one                        |
| `* *`    | diligent teacher                                    | assign roles to students                        | manage students with the specific roles                             |
| `* *`    | neat teacher                                        | group students by their class                   | manage and access information by class                              |
| `* *`    | teacher wanting to split students for group project | separate students into project groups           | manage their project work within the app                            |
| `* *`    | prepared teacher                                    | update a student's information                  | have the most current details when there is a change                |
| `* *`    | diligent teacher                                    | assign progress tags to individual students     | categorise their performance in class                               |
| `* *`    | efficient teacher                                   | sort the students by name                       | arrange the students lexicographically for exam conditions          |
| `* *`    | caring teacher                                      | add a comment for a student                     | take note of that student's particular trait                        |
| `* *`    | strict teacher                                      | track a student's submissions                   | see which students did not submit tasks on time                     |
| `* *`    | strict teacher                                      | track student attendance                        | address absenteeism and its impact on student performance           |
| `* *`    | diligent teacher                                    | add a new test for all my students              | keep track of all the students' results                             |
| `* *`    | diligent teacher                                    | add the scores of the students                  | have an overview of everyone's results                              |
| `*`      | picky teacher                                       | customize the app settings                      | align the configuration with my preferences                         |
| `*`      | teacher who likes to have everything in one app     | create a seating arrangement for the class      | edit the seating arrangement any time                               |
| `*`      | diligent teacher                                    | export information of all my graduated students | store them into the school database                                 |


### Use cases

(For all use cases below, the **System** is the `StudentManagerPro` and the **Actor** is the `user`, unless specified otherwise)

**System: StudentManagerPro**

**Use case: UC01 Add Student's Name**

**Actor: User**

**Preconditions:**
* StudentManagerPro is open.

**Guarantees:**
* If successful, the student's name is added to the system and can be used to track their academic progress.
* If an invalid name is given as input, a corresponding error message is displayed.

**MSS**

1.  User gives the command to add a student's name to the StudentManagerPro.
2.  System validates the input's format.
3.  System validates the input.
4.  System adds the student to the system.
5.  System adds the student name to the student profile.
6.  System confirms the success by displaying a success message.<br>
    Use case ends.

**Extensions**

* 2a. User enters invalid characters.
    * 2a1. System displays an error message to ask for a valid name command format, with no special characters.<br>
      Use case ends.

* 3a. User leaves the name field empty.
    * 3a1. System displays an error message to ask for a valid name.<br>
      Use case ends.

**System: StudentManagerPro**

**Use case: UC02 Add Student's Email**

**Actor: User**

**Preconditions:**
* StudentManagerPro is open.
* Student profile has been created through the <u>addition of the student name(UC01).</u>

**Guarantees:**
* If successful, the student's email is added to the student profile and saved in the system.
* If an invalid email is given as input, a corresponding error message is displayed.

**MSS**

1.  User gives the command to add a student's email to a student's profile in the StudentManagerPro.
2.  System validates the input's format.
3.  System validates the input.
4.  System adds the email to the student profile in the system.
5.  System confirms the success by displaying a success message.<br>
    Use case ends.

**Extensions**

* 2a. User enters invalid characters.
    * 2a1. System displays an error message to ask for a valid email command format, with no special characters.<br>
      Use case ends.

* 3a. User leaves the email field empty.
    * 3a1. System displays an error message to ask for a valid email.<br>
      Use case ends.

* 3b. User enters a duplicate email.
    * 3b1. System displays an error message notifying that the email already exists in the system.<br>
      Use case ends.

**System: StudentManagerPro**

**Use case: UC03 Add Student's Register Number**

**Actor: User**

**Preconditions:**
* StudentManagerPro is open.
* Student profile has been created through the <u>addition of the student name(UC01).</u>

**Guarantees:**
* If successful, the student's register number is added to the student profile and saved in the system.
* If an invalid register number is given as input, a corresponding error message is displayed.

**MSS**

1.  User gives the command to add a student's register number to a student's profile in the StudentManagerPro.
2.  System validates the input's format.
3.  System validates the input.
4.  System adds the register number to the student profile in the system.
5.  System confirms the success by displaying a success message.<br>
    Use case ends.

**Extensions**

* 2a. User enters invalid characters.
    * 2a1. System displays an error message to ask for a valid register number command format, with no
      special characters.<br>
      Use case ends.

* 3a. User leaves the register number field empty.
    * 3a1. System displays an error message to ask for a valid register number.<br>
      Use case ends.

**System: StudentManagerPro**

**Use case: UC04 Add Student's Sex**

**Actor: User**

**Preconditions:**
* StudentManagerPro is open.
* Student profile has been created through the <u>addition of the student name(UC01).</u>

**Guarantees:**
* If successful, the student's sex is added to the student profile and saved in the system.
* If an invalid sex is given as input, a corresponding error message is displayed.

**MSS**

1.  User gives the command to add a student's sex to a student's profile in the StudentManagerPro.
2.  System validates the input's format.
3.  System validates the input.
4.  System adds the sex to the student profile in the system.
5.  System confirms the success by displaying a success message.<br>
    Use case ends.

**Extensions**

* 2a. User enters invalid characters.
    * 2a1. System displays an error message to ask for a valid sex command format, with no
      special characters.<br>
      Use case ends.

* 3a. User leaves the sex field empty.
    * 3a1. System displays an error message to ask for a valid sex.<br>
      Use case ends.

**System: StudentManagerPro**

**Use case: UC05 Add Student's Address**

**Actor: User**

**Preconditions:**
* StudentManagerPro is open.
* Student profile has been created through the <u>addition of the student name(UC01).</u>

**Guarantees:**
* If successful, the student's address is added to the student profile and saved in the system.
* If an invalid address is given as input, a corresponding error message is displayed.

**MSS**

1.  User gives the command to add a student's address to a student's profile in the StudentManagerPro.
2.  System validates the input's format.
3.  System validates the input.
4.  System adds the address to the student profile in the system.
5.  System confirms the success by displaying a success message.<br>
    Use case ends.

**Extensions**

* 2a. User enters invalid characters.
    * 2a1. System displays an error message to ask for a valid address command format, with only acceptable
      special characters.<br>
      Use case ends.

* 3a. User leaves the address field empty.
    * 3a1. System displays an error message to ask for a valid address.<br>
      Use case ends.

**System: StudentManagerPro**

**Use case: UC06 Add Student's Contact Number**

**Actor: User**

**Preconditions:**
* StudentManagerPro is open.
* Student profile has been created through the <u>addition of the student name(UC01).</u>

**Guarantees:**
* If successful, the student's contact number is added to the student profile and saved in the system.
* If an invalid contact number is given as input, a corresponding error message is displayed.

**MSS**

1.  User gives the command to add a student's contact number to a student's profile in the StudentManagerPro.
2.  System validates the input's format.
3.  System validates the input.
4.  System adds the contact number to the student profile in the system.
5.  System confirms the success by displaying a success message.<br>
    Use case ends.

**Extensions**

* 2a. User enters invalid characters.
    * 2a1. System displays an error message to ask for a valid contact number command format, with no
      special characters.<br>
      Use case ends.

* 3a. User leaves the contact number field empty.
    * 3a1. System displays an error message to ask for a valid contact number.<br>
      Use case ends.

* 3b. User enters a duplicate contact number.
    * 3b1. System displays an error message notifying that the contact number already exists in the system.<br>
      Use case ends.

**System: StudentManagerPro**

**Use case: UC07 Add Student's Emergency Contact Name**

**Actor: User**

**Preconditions:**
* StudentManagerPro is open.
* Student profile has been created through the <u>addition of the student name(UC01).</u>

**Guarantees:**
* If successful, the student's emergency contact name is added to the student profile and saved in the system.
* If an invalid name is given as input, a corresponding error message is displayed.

**MSS**

1.  User gives the command to add a student's emergency contact name to a student's profile in the StudentManagerPro.
2.  System validates the input's format.
3.  System validates the input.
4.  System adds the emergency contact name to the student profile in the system.
5.  System confirms the success by displaying a success message.<br>
    Use case ends.

**Extensions**

* 2a. User enters invalid characters.
    * 2a1. System displays an error message to ask for a valid emergency contact name command format, with no
      special characters.<br>
      Use case ends.

* 3a. User leaves the emergency contact name field empty.
    * 3a1. System displays an error message to ask for a valid emergency contact name.<br>
      Use case ends.

**System: StudentManagerPro**

**Use case: UC08 Add Student's Emergency Contact Number**

**Actor: User**

**Preconditions:**
* StudentManagerPro is open.
* Student profile has been created through the <u>addition of the student name(UC01).</u>

**Guarantees:**
* If successful, the student's emergency contact number is added to the student profile and saved in the system.
* If an invalid contact number is given as input, a corresponding error message is displayed.

**MSS**

1.  User gives the command to add a student's emergency contact number to a student's profile in the StudentManagerPro.
2.  System validates the input's format.
3.  System validates the input.
4.  System adds the emergency contact number to the student profile in the system.
5.  System confirms the success by displaying a success message.<br>
    Use case ends.

**Extensions**

* 2a. User enters invalid characters.
    * 2a1. System displays an error message to ask for a valid emergency contact number command format, with no
      special characters.<br>
      Use case ends.

* 3a. User leaves the emergency contact number field empty.
    * 3a1. System displays an error message to ask for a valid emergency contact number.<br>
      Use case ends.

**System: StudentManagerPro**

**Use case: UC09 Add Student's Class**

**Actor: User**

**Preconditions:**
* StudentManagerPro is open.
* Student profile has been created through the <u>addition of the student name(UC01).</u>

**Guarantees:**
* If successful, the student's class is added to the student profile and saved in the system.
* If an invalid class is given as input, a corresponding error message is displayed.

**MSS**

1.  User gives the command to add a student's class to a student's profile in the StudentManagerPro.
2.  System validates the input's format.
3.  System validates the input.
4.  System adds the class to the student profile in the system.
5.  System confirms the success by displaying a success message.<br>
    Use case ends.

**Extensions**

* 2a. User enters invalid characters.
    * 2a1. System displays an error message to ask for a valid class command format, with no
      special characters.<br>
      Use case ends.

* 3a. User leaves the class field empty.
    * 3a1. System displays an error message to ask for a valid class.<br>
      Use case ends.

**System: StudentManagerPro**

**Use case: UC10 Remove Student from the System**

**Actor: User**

**Preconditions:**
* StudentManagerPro is open.
* Student to be removed exists in the system.

**Guarantees:**
* If successful, the student will be removed from the system.
* If student does not exist or an invalid input is entered, a corresponding error message is displayed.

**MSS**

1.  User gives the command to remove a student from the StudentManagerPro.
2.  System validates the input's format.
3.  System validates the input.
4.  System removes the student from the system.
5.  System confirms the success by displaying a success message.<br>
    Use case ends.

**Extensions**

* 2a. User enters invalid characters.
    * 2a1. System displays an error message to ask for a valid index command format.<br>
      Use case ends.

* 3a. User leaves the index field empty.
    * 3a1. System displays an error message to ask for a valid index.<br>
      Use case ends.

* 3b. User enters an index that does not exist in the system.
    * 3b1. System displays an error message to ask for a valid index of a student in the system.<br>
      Use case ends.

**System: StudentManagerPro**

**Use case: UC11 Add Student's Attendance**

**Actor: User**

**Preconditions:**
* StudentManagerPro is open.
* Student is already added in the system.

**Guarantees:**
* If successful, the attendance record for the student is added to the system and can be used to track their attendance history.
* If invalid data or reason is given as input, a corresponding error message is displayed.

**MSS**

1.  User gives the command to add attendance for a student in StudentManagerPro.
2.  System validates the input’s format.
3.  System validates the attendance data.
4.  System adds the attendance record to the student’s profile.
5.  System confirms the success by displaying a success message.<br>
    Use case ends.

**Extensions**

* 2a. User enters invalid characters in the attendance data.
    * 2a1. System displays an error message asking for valid attendance format.<br>
      Use case ends.

* 3a. User leaves the absent reason blank (indicating deletion of attendance).
    * 3a1. System deletes the attendance record for that entry, and displays a confirmation message.<br>
      Use case ends.

* 3b. User enters absent date in an invalid format.
    * 3b1. System displays an error message asking for a valid absent date format.<br>
      Use case ends.

* 3c. User enters a date that does not exist (e.g., 30-02-2024).
    * 3c1. System displays an error message asking for a valid absent date.<br>
      Use case ends

* 3d. User enters absent reason in an invalid format.
    * 3d1. System displays an error message asking for a valid absent reason format.<br>
      Use case ends.

* 3e. User tries to add attendance for a student that does not exist.
    * 3e1. System displays an error message notifying that the student does not exist in the system.<br>
      Use case ends.

* 3f. User tries to add multiple attendances for a student at one go.
    * 3f1. System displays an error message notifying that input with multiple attendances is not allowed.<br>
      Use case ends.

**System: StudentManagerPro**

**Use case: UC12 Add Exam**

**Actor: User**

**Preconditions:**
* StudentManagerPro is open.
* Existing students in the system to add exams for.

**Guarantees:**
* If successful, the exam is added for all students currently in the system with a score of "NIL".
* If an invalid exam name is given as input, an error message is displayed.

**MSS**
1. User gives the command to add exam in StudentManagerPro.
2. System validates the input's format.
3. System validates the exam data.
4. System adds the exam to every student currently in the system.
5. System confirms the success by displaying a success message.<br>
   Use case ends.

**Extensions**
* 2a. User enters invalid characters in the exam name.
    * 2a1. System displays an error message asking for valid exam name format, with only alphanumeric characters and spaces.<br>
      Use case ends.

* 3a. User tries to add an exam that already exists in the system.
    * 3a1. System displays an error message telling the user that the exam already exists.<br>
      Use case ends.

**System: StudentManagerPro**

**Use case: UC13 Add Student's Exam Score**

**Actor: User**

**Preconditions:**
* StudentManagerPro is open.
* Student is already added in the system.
* Exam already added for student.

**Guarantees:**
* If successful, the student's exam score is added to the specified exam in the student's profile and saved in the system.
* If an invalid exam name or exam score is given as input, a corresponding error message is displayed.

**MSS**
1. User gives the command to add the score for a particular exam for a student in StudentManagerPro.
2. System validates the input's format.
3. System validates the exam data.
4. System adds the exam score to the specified exam in the student's profile.
5. System confirms the success by displaying a success message.<br>
   Use case ends.

**Extensions**
* 2a. User enters invalid characters in the exam name.
    * 2a1. System displays an error message asking for valid exam name format, with only alphanumeric characters and spaces.<br>
      Use case ends.

* 2b. User enters invalid characters in the exam score.
    * 2b1. System displays an error message asking for valid exam score format, an integer between 0 and 100.<br>
      Use case ends.

* 3a. User tries to add a score to an exam that does not exist.
    * 3a1. System displays an error message notifying that the exam does not exist in the system.<br>
      Use case ends.

* 3b. User tries to add a score to an exam for a student that does not exist.
    * 3b1. System displays an error message notifying that the student does not exist in the system.<br>
      Use case ends.

* 3c. User tries to add a score to an exam for a student that is the same as is already recorded in the system for the same exam of the same student.
    * 3b1. System displays an error message notifying that the exam score is not changed.<br>
      Use case ends.

**System: StudentManagerPro**

**Use case: UC14 Delete an Exam**

**Actor: User**

**Preconditions:**
* StudentManagerPro is open.
* Exam exists in the system for students.

Guarantees:
* If successful, the exam is deleted from all students currently in the system.
* If an invalid exam name is given as input, an error message is displayed.

MSS
1. User gives the command to delete exam in StudentManagerPro.
2. System validates the input's format.
3. System validates the exam data.
4. System deletes the exam from every student currently in the system.
5. System confirms the success by displaying a success message.<br>
   Use case ends.

**Extensions**
* 2a. User enters invalid characters in the exam name.
    * 2a1. System displays an error message asking for valid exam name format, with only alphanumeric characters and spaces.<br>
      Use case ends.
* 3a. User tries to delete a exam that does not exist in the system.
    * 3a1. System displays an error message telling the user that the exam does not exist.<br>
      Use case ends.

**System: StudentManagerPro**

**Use case: UC15 Add a Submission**

**Actor: User**

**Preconditions:**
* StudentManagerPro is open.
* Existing students in the system to add exams for.

**Guarantees:**
* If successful, the submission is added for all students currently in the system with a status of "NIL".
* If an invalid submission name is given as input, an error message is displayed.

**MSS**
1. User gives the command to add submission in StudentManagerPro.
2. System validates the input's format.
3. System validates the submission data.
4. System adds the submission to every student currently in the system.
5. System confirms the success by displaying a success message.<br>
   Use case ends.

**Extensions**
* 2a. User enters invalid characters in the submission name.
    * 2a1. System displays an error message asking for valid submission name format, with only alphanumeric characters and spaces.<br>
      Use case ends.

* 3a. User tries to add a submission that already exists in the system.
    * 3a1. System displays an error message telling the user that the submission already exists.<br>
      Use case ends.

**System: StudentManagerPro**

**Use case: UC16 Add a Student's Submission Status**

**Actor: User**

**Preconditions:**
* StudentManagerPro is open.
* Student is already added in the system.
* Submission already added for student.

**Guarantees:**
* If successful, the student's submission status is added to the specified submission in the student's profile and saved in the system.
* If an invalid submission name or submission status is given as input, a corresponding error message is displayed.

**MSS**
1. User gives the command to add the status for a particular submission for a student in StudentManagerPro.
2. System validates the input's format.
3. System validates the submission data.
4. System adds the submission status to the specified submission in the student's profile.
5. System confirms the success by displaying a success message.<br>
   Use case ends.

**Extensions**
* 2a. User enters invalid characters in the submission name.
    * 2a1. System displays an error message asking for valid submission name format, with only alphanumeric characters and spaces.<br>
      Use case ends.

* 2b. User enters invalid characters in the submission status.
    * 2b1. System displays an error message asking for valid submission status format, "Y", "N" or "NIL".<br>
      Use case ends.

* 3a. User tries to add a status to a submission that does not exist.
    * 3a1. System displays an error message notifying that the submission does not exist in the system.<br>
      Use case ends.

* 3b. User tries to add a status to a submission for a student that does not exist.
    * 3b1. System displays an error message notifying that the student does not exist in the system.<br>
      Use case ends.

* 3c. User tries to add a status to a submission for a student that is the same as is already recorded in the system for the same submission of the same student.
    * 3b1. System displays an error message notifying that the submission status is not changed.<br>
      Use case ends.

**System: StudentManagerPro**

**Use case: UC17 Delete a Submission**

**Actor: User**

**Preconditions:**
* StudentManagerPro is open.
* Submission exists in the system for students.

**Guarantees:**
* If successful, the submission is deleted from all students currently in the system.
* If an invalid submission name is given as input, an error message is displayed.

**MSS**
1. User gives the command to delete submission in StudentManagerPro.
2. System validates the input's format.
3. System validates the submission data.
4. System deletes the submission from every student currently in the system.
5. System confirms the success by displaying a success message.<br>
   Use case ends.

**Extensions**
* 2a. User enters invalid characters in the submission name.
    * 2a1. System displays an error message asking for valid submission name format, with only alphanumeric characters and spaces.<br>
      Use case ends.

* 3a. User tries to delete a submission that does not exist in the system.
    * 3a1. System displays an error message telling the user that the submission does not exist.<br>
      Use case ends.

**System: StudentManagerPro**

**Use case: UC18 Sort Students**

**Actor: User**

**Preconditions:**
* StudentManagerPro is open.

**Guarantees:**
* If successful, student list displayed will be sorted.
* If an invalid attribute is given, a corresponding error message is displayed.

**MSS**
1. User gives the command to sort students based on a particular attribute.
2. System validates the input attribute.
3. System sorts the displayed list based on the attribute.
4. System displays the sorted list.
5. System confirms the success by displaying a success message.<br>
   Use case ends.

**Extensions**
* 2a. System detects an invalid attribute.
    * 2a1. System displays an error message asking for valid input.<br>
      Use case ends.

**System: StudentManagerPro**

**Use case: UC19 Filter Students**

**Actor: User**

**Preconditions:**
* StudentManagerPro is open.

**Guarantees:**
* If successful, student list displayed will be filtered according to the predicate provided.
* If an invalid predicate or format is given, a corresponding error message is displayed.

**MSS**
1. User gives the command to filter students with specific predicates by one or multiple attributes.
2. System validates the input predicates.
3. System filters the student list based on the provided predicates.
4. System displays the filtered list.
5. System confirms the success by displaying a success message stating the number of filtered students.<br>
   Use case ends.

**Extensions**
* 1a. User inputs a filter command that is incomplete with no prefixes mentioned. 
    * 1a1. System displays an error message showing the correct format of the filter command.<br>
      Use case ends.

* 1b. User tries to filter by a predicate that is not supported by the filter functionality. 
    * 1b1. System displays an error message that shows the attributes supported by the filter command and corresponding prefixes.<br>
      Use case ends.

* 2a. System detects an empty predicate value after the attribute prefix.
    * 2a1. System displays an error message stating that predicates cannot be empty.<br>
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
* **EcName**: Emergency contact name of a student
* **EcNumber**: Emergency contact number of a student
* **SortAttribute**: Particular attribute of a student that can be compared for sorting purposes
* **Predicate**: A condition that evaluates to true or false, used to filter or match specific items

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

   2. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Adding a student

1. Adding a student into the list

    1. Test case: `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 r/1 s/M c/2A`<br>
       Expected: Student is added to the list. Details of the new contact shown in the status message.

    2. Test case: `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 r/1 s/L c/2A`<br>
       Expected: No student is added. Error detail regarding sex is shown in the status message.

    3. Test case: `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 r/41 s/M c/2A`<br>
       Expected: No student is added. Error detail regarding register number is shown in the status message.

    4. Test case: `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 r/1 s/M c/A`<br>
       Expected: No student is added. Error detail regarding class is shown in the status message.

    5. Other incorrect add commands to try:<br>
       `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 r/1 s/ c/2A`<br>
       `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 r/ s/M c/2A`<br>
       `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 r/1 s/M c/`<br>
       Expected: Similar to previous.

### Deleting a student

1. Deleting a student while all students are being shown

   1. Prerequisites: List all students using the `list` command. Multiple students in the list.

   2. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message.

   3. Test case: `delete 0`<br>
      Expected: No student is deleted. Error details shown in the status message.

   4. Other incorrect delete commands to try:<br>
      `delete`<br>
      `delete x`<br>
      `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Editing a student

1. Editing a student while all students are being shown

    1. Prerequisites: List all students using the `list` command. Multiple students in the list.

    2. Test case: `edit 1 c/1A`<br>
       Expected: First student's class is changed. Details of the edited student shown in the status message.

    3. Test case: `edit 1 c/A1`<br>
       Expected: No student's detailed are changed. Error details shown in the status message.

    4. Other incorrect edit commands to try:<br>
       `edit`<br>
       `edit 0`<br>
       `edit c/1A c/2A`<br>
       `edit x c/1A` (where x is larger than the list size)<br>
       Expected: Similar to previous.

### Adding EcName for a student

1. Adding EcName for a student in the list

    1. Prerequisites: List must not be empty, student for which EcName is added should already be in the list.

    2. Test case: `addEcName 1 en/Sally Ho`<br>
       Expected: First student will have his emergency contact name added. Name and emergency contact name will be shown in the status message.

    3. Test case: `addEcName 1 en/`<br>
       Expected: First student will have his emergency contact name deleted. Name of student with the emergency contact name deleted will be shown in the status message.

    4. Test case: `addEcName 1 en/John Doe`<br>
       Expected: First student's emergency contact name will be updated to "John Doe". Name and emergency contact name will be shown in the status message.

    5. Other incorrect addEcName commands to try:<br>
       `addEcName`<br>
       `addEcName hhhh en/Jack`<br>
       Expected: An error message is shown which includes the correct format of the addEcName command to follow.

### Adding EcNumber for a student

1. Adding EcNumber for a student in the list

    1. Prerequisites: List must not be empty, student for which EcNumber is added should already be in the list.

    2. Test case: `addEcNumber 1 ep/91234567`<br>
       Expected: First student will have his emergency contact number added. Name and emergency contact number will be shown in the status message.

    3. Test case: `addEcNumber 1 ep/`<br>
       Expected: First student will have his emergency contact number deleted. Name of student with the emergency contact number deleted will be shown in the status message.
    
    4. Test case: `addEcNumber 1 ep/123 456`<br>
       Expected: No emergency contact number is changed. Error details shown in the status message.

    5. Other incorrect addEcNumber commands to try:<br> 
       `addEcNumber`<br>
       `addEcNumber abc ep/91234567`<br>
       `addEcNumber x ep/91234567` (where x is larger than the list size)<br>
       Expected: Similar to previous.

### Adding attendance for a student

1. Adding attendance for a student in the list

    1. Prerequisites: List all students using the `list` command. Multiple students in the list.

    2. Test case: `addAttendance 1 ad/24-09-2024 ar/MC`<br>
       Expected: Attendance record with date 24-09-2024 and reason MC is added to the first student. Confirmation message shown in the status message.
   
    3. Test case: `addAttendance 0 ad/24-09-2024 ar/MC`<br>
       Expected: No attendance is added. Error details shown in the status message.

    4. Other incorrect addAttendance commands to try:<br>
       `addAttendance`<br>
       `addAttendance 1 ad/24-09-24 ar/MC`<br>
       `addAttendance 1 ad/24-09-2024 ar/!@#`<br>
       Expected: Similar to previous.

### Deleting attendance for a student

1. Deleting attendance for a student in the list

    1. Prerequisites: List all students using the `list` command. Multiple students in the list. The test for adding attendance should be done first as the student must have an existing attendance to be deleted.

    2. Test case: `addAttendance 1 ad/24-09-2024 ar/`<br>
       Expected: Attendance record with date 24-09-2024 is deleted from the first student. Confirmation message shown in the status message.

    3. Test case: `addAttendance 0 ad/24-09-2024 ar/`<br>
       Expected: No attendance is deleted. Error details shown in the status message.

    4. Other incorrect addAttendance commands to try:<br>
       `addAttendance 1 ad/24-09-2024`<br>
       `addAttendance 1 ad/2024-12-12 ar/`<br>
       `addAttendance x ad/24-09-2024 ar/` (where x is larger than the list size)<br>
       Expected: Similar to previous.

### Adding exam for students

1. Adding exam for all students currently in the list

    1. Test case: `addExam ex/Midterm`<br>
       Expected: Exam with exam name Midterm is added to all students in the student list. Confirmation message shown in the status message.

    2. Test case: `addExam ex/Midterm#`<br>
       Expected: No exam is added. Error details shown in the status message.

    3. Other incorrect addExam commands to try:<br>
       `addExam`<br>
       `addExam ex/`<br>
       `addExam ex/#@*`<br>
       Expected: Similar to previous.

### Adding exam score for a student

1. Adding exam score for a student in the list

    1. Prerequisites: List all students using the `list` command. Multiple students in the list. The test for adding exam should be done first as the student must have an existing exam to add an exam score to.

    2. Test case: `addExamScore 1 ex/Midterm sc/70.0`<br>
       Expected: Exam with exam name Midterm is updated with a exam score of 70.0 for the first student. Confirmation message shown in the status message.

    3. Test case: `addExamScore 1 ex/Midterm sc/101.0`<br>
       Expected: No exam score is added. Error details shown in the status message.

    4. Other incorrect addExamScore commands to try:<br>
       `addExamScore 1 ex/Midterm`<br>
       `addExamScore 1 ex/Midterm sc/`<br>
       `addExamScore x ex/Midterm sc/70.0` (where x is larger than the list size)<br>
       Expected: Similar to previous.

### Deleting exam for students

1. Deleting exam for all students currently in the list

    1. Prerequisites: The test for adding exam should be done first as the students must have an existing exam to be deleted.

    2. Test case: `deleteExam ex/Midterm`<br>
       Expected: Exam with exam name Midterm is deleted from all students in the student list. Confirmation message shown in the status message.

    3. Test case: `deleteExam ex/Midterm#`<br>
       Expected: No exam is deleted. Error details shown in the status message.

    4. Other incorrect deleteExam commands to try:<br>
       `deleteExam`<br>
       `deleteExam ex/`<br>
       `deleteExam ex/#@*`<br>
       Expected: Similar to previous.

### Adding submission for students

1. Adding submission for all students currently in the list

    1. Test case: `addSubmission sm/Assignment 1`<br>
       Expected: Submission with submission name Assignment 1 is added to all students in the student list. Confirmation message shown in the status message.

    2. Test case: `addSubmission sm/Assignment #1`<br>
       Expected: No submission is added. Error details shown in the status message.

    3. Other incorrect addSubmission commands to try:<br>
       `addSubmission`<br>
       `addSubmission sm/`<br>
       `addSubmission sm/#@*`<br>
       Expected: Similar to previous.

### Adding submission status for a student

1. Adding submission status for a student in the list

    1. Prerequisites: List all students using the `list` command. Multiple students in the list. The test for adding submission should be done first as the student must have an existing submission to add a submission status to.

    2. Test case: `addSubmissionStatus 1 sm/Assignment 1 ss/Y`<br>
       Expected: Submission with submission name Assignment 1 is updated with a submission status of Y for the first student. Confirmation message shown in the status message.

    3. Test case: `addSubmissionStatus 1 sm/Assignment 1 ss/A`<br>
       Expected: No submission status is added. Error details shown in the status message.

    4. Other incorrect addSubmissionStatus commands to try:<br>
       `addSubmissionStatus 1 sm/Assignment 1`<br>
       `addSubmissionStatus 1 sm/Assignment 1 ss/`<br>
       `addSubmissionStatus x sm/Assignment 1 ss/Y` (where x is larger than the list size)<br>
       Expected: Similar to previous.

### Deleting submission for students

1. Deleting submission for all students currently in the list

    1. Prerequisites: The test for adding submission should be done first as the students must have an existing submission to be deleted.

    2. Test case: `deleteSubmission sm/Assignment 1`<br>
       Expected: Submission with submission name Assignment 1 is deleted from all students currently in the list. Confirmation message shown in the status message.

    3. Test case: `deleteSubmission sm/Assignment #1`<br>
       Expected: No submission is deleted. Error details shown in the status message.

    4. Other incorrect deleteSubmission commands to try:<br>
       `deleteSubmission`<br>
       `deleteSubmission sm/`<br>
       `deleteSubmission sm/#@*`<br>
       Expected: Similar to previous.

### Filtering students

1. Filters all students currently in the list based on the specified attribute and predicate 

    1. Prerequisites: List all students using the `list` command. Multiple students in the list.

    2. Test case: `filter n/John`<br>
       Expected: Returns the students in the list who have John in their name. Confirmation message is shown in the status message, including the number of persons filtered.

    3. Test case: `filter n/John Park`<br>
       Expected: Returns the students in the list who have John OR Park in their name. Confirmation message is shown in the status message, including the number of persons filtered.

    4. Test case: `filter n/John Park p/99999999 92929292`<br>
       Expected: Returns the students in the list who match at least one name and one phone number. Confirmation message is shown in the status message, including the number of persons filtered.

    5. Test case: `filter hhh`<br>
       Expected: Filtering does not occur and an error message is depicted to show the correct format of the filter command.

    6. Test case: `filter n/`<br>
       Expected: Filtering does not occur and an error message is depicted to assert that the predicate values cannot be empty.

    7. Other incorrect sort commands to try:<br>
       `filter n`<br>
       `filter n/ p/`<br>
       Expected: Similar to previous

### Sorting students

1. Sorts all students currently in the list based on the specified attribute

   1. Prerequisites: List all students using the `list` command. Multiple students in the list.

   2. Test case: `sort register number`<br>
      Expected: List of students is sorted according to register number. Confirmation message shown in the status message.

   3. Test case: `sort abc`<br>
      Expected: List is not sorted, Error details shown in the status message.

   4. Other incorrect sort commands to try:<br> 
      `sort`<br>
      `sort 1`<br>
      Expected: Similar to previous

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

Team size: 5

### Ui
* Ui can be adjusted such that long names and phone numbers will not be truncated when added into StudentManagerPro.

### Sex
* Sex attribute can be adjusted to be case insensitive in the future to make it more convenient for users.

### Class
* Class attribute can be made more flexible by allowing different formats for identifying and distinguishing between classes.

### EcName / EcNumber
* Combine `addEcName` and `addEcNumber` into one command.

### Filter
* Allow filtering by phone numbers using predicates less than 3 digits long.
* Allow filtering for exact multi-word addresses. For example, using `filter a/Block 30 Geylang` will return only students with an address that exactly matches `Block 30 Geylang` rather than returning partial matches for individual words like `Block`, `30`, or `Geylang`

### Sort
* Sort in descending order.

### Exam / Submission
* Include the ability to assign specific exams and submissions to selected groups and not automatically to all students, allowing for customized tests and assignments for different sets of students.
* Exams and Submissions can be automatically added for new students being added to the list, instead of having the user add them manually.

### Attendance
* As only dates in the current year are allowed when adding attendance, the command can be adjusted such that it only requires users to enter the date in the form of DD-MM.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Effort**

* The project required a significant amount of effort and focus to implement the new features.
* There were some challenges along the way when we did not know how to implement specific features such as filter and sort.
* It was also quite challenging to add new attributes and represent them as tables in the GUI, such as exams, submissions, and attendance.
* While AB3 deals with simple attributes of a person, we had to include attributes that were more complex and tailored for our application use and needs, thus requiring us to store more non-trivial data for every student.
* Some achievements of the project include learning the workflows of having multiple developers work on the same product and managing each other's changes.
