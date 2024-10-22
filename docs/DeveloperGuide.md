---
layout: page
title: Developer Guide
---

* Table of Contents
  {:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the
  original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [
_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create
and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of
classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java)
and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is
in charge of the app launch and shut down.

* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues
the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding
  API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using
the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component
through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the
implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified
in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts
e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`,
inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the
visible GUI.

The UI for the Single page person view is a standalone UI window that is called through the functionalities of the
application itself.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that
are in the `src/main/resources/view` folder. For example, the layout of
the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java)
is specified
in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API
** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API
call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates
   a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which
   is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take
   several interactions (between the command object and the `Model`) to achieve.
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:

* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a
  placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse
  the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as
  a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser`
  interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API
** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which
  is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to
  this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as
  a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they
  should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>

### Storage component

**API
** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,

* can save both address book data and user preference data in JSON format, and read them back into corresponding
  objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only
  the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects
  that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo
history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the
following operations:

* `VersionedAddressBook#commit()`— Saves the current address book state in its history.
* `VersionedAddressBook#undo()`— Restores the previous address book state from its history.
* `VersionedAddressBook#redo()`— Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()`
and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the
initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command
calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes
to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book
state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also
calls `Model#commitAddressBook()`, causing another modified address book state to be saved into
the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing
the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer`
once to the left, pointing it to the previous address book state, and restores the address book to that state.

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

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once
to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such
as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`.
Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not
pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be
purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern
desktop applications follow.

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

**Target user profile**

* Real estate agents
* Need manage a large number of clients with varying details
* Need to record past engagement with clients
* Can type fast
* Prefers typing to mouse interactions
* Is reasonably comfortable using CLI apps

**Value proposition**: manage contacts faster than a typical mouse/GUI driven app

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                    | So that I can…​                                                                                |
|----------|--------------------------------------------|---------------------------------|------------------------------------------------------------------------------------------------|
| `* * *`  | new user                                   | see usage instructions          | refer to instructions when I forget how to use the App                                         |
| `* * *`  | user                                       | add a new person                |                                                                                                |
| `* * *`  | user                                       | delete a person                 | remove entries that I no longer need                                                           |
| `* * *`  | user with many persons in the address book | find a person by name           | locate details of persons without having to go through the entire list                         |
| `* * *`  | user with many persons in the address book | find people by catatory         | view a collection of persons in the same catagory without having to go through the entire list |
| `* *`    | user                                       | add history log to a person     | keep a record of past activities for future references                                         |
| `* *`    | user                                       | hide private contact details    | minimize chance of someone else seeing them by accident                                        |
| `* *`    | user                                       | be reminded of important events | minimize chance of missing these important events                                              |
| `*`      | user with many persons in the address book | sort persons by name            | locate a person easily                                                                         |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified
otherwise)

**Use case: Delete a person**

**MSS**

1. User requests to list persons
2. AddressBook shows a list of persons
3. User requests to delete a specific person in the list
4. AddressBook deletes the person

   Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. AddressBook shows an error message.

      Use case resumes at step 2.

### 1. **Feature: Add Contact**

**Purpose:**

The purpose of this feature is to allow users to add a new contact by specifying essential details such as name, phone
number, physical address, birthday, email address, and optional social media handles.

### **Command Format:**

`add n/<Full Name> p/<Phone Number> a/<Physical Address> [b/<Birthday>] e/<Email Address> [r/<Remark>] [t/<Tag>]`

*Note: Fields in `[]` are optional.*

**Example Commands:**

- `add n/John Smith p/+123456789 a/123 Main Street, City, Country b/1990-05-12 e/john.smith@email.com r/@johnsmith t/instagram`
- `add n/Emily Davis p/(555) 123-4567 a/987 Elm St, Apt 5B, City b/1985-07-22 e/emily.davis@email.com`
- `add n/Emily Davis p/(555) 123-4567 b/1985-07-22 a/Singapore e/emmm@gmail.com`

### **Main Success Scenario (MSS)**

1. The user issues the `add` command, specifying a full name and phone number as mandatory fields, with optional fields
   such as physical address, birthday, email, and Instagram handle.
2. The system validates the input parameters (e.g., name, phone number, etc.) for correctness.
3. The system adds the new contact to the contact list.
4. A success message is shown to the user: `Contact successfully added: <Name>`.
5. The newly added contact is displayed in the contact list.

   Use case ends.

---

### **Command Parameters:**

#### **Command “add”:**

**Acceptable Values:**

- The command must match the case exactly (i.e., no "Add", "ADD", etc.).

**Error Message:**

- Invalid Command: `Error: Invalid Command entered. Please provide a valid command.`

---

#### **Name:**

**Acceptable Values:**

- Must be a string containing alphabetic characters, with allowed spaces, hyphens, and apostrophes.
- Examples: `John Smith`, `Mary-Jane O'Neil`.
- Case-insensitive (e.g., `JOHN SMITH` is the same as `John Smith`).
- Leading/trailing spaces are ignored.

**Error Message:**

- If invalid: `Error: Name must only contain alphabetic characters, spaces, hyphens, or apostrophes.`

**Rationale:**

- This format accommodates common name variations while preventing the use of numeric or special characters.

---

#### **Phone Number:**

**Acceptable Values:**

- Must be in a valid phone number format, which includes numbers and may have spaces, parentheses, dashes, and a
  leading `+` for international codes.
- Examples: `+123456789`, `(555) 123-4567`, `555-123-4567`.
- Leading/trailing spaces are ignored.

**Error Message:**

- If invalid: `Error: Invalid phone number format.`

**Rationale:**

- This allows flexibility in formatting phone numbers while accounting for international and regional variations.

---

#### **Physical Address:**

**Acceptable Values:**

- A string containing alphanumeric characters, spaces, and common punctuation (e.g., commas, periods, hyphens).
- Examples: `123 Main Street, City, Country`, `987 Elm St., Apt 5B`.
- Case-insensitive and leading/trailing spaces are ignored.

**Error Message:**

- If invalid: `Error: Invalid address format. Only alphanumeric characters and basic punctuation are allowed.`

**Rationale:**

- This format supports common address structures while maintaining simplicity.

---

#### **Birthday (Optional):**

**Acceptable Values:**

- A valid date in the format `YYYY-MM-DD`.
- Examples: `1990-05-12`, `1985-07-22`.
- Leading/trailing spaces are ignored.

**Error Message:**

- If invalid: `Error: Invalid birthday format. Please use YYYY-MM-DD.`

**Rationale:**

- The standardized date format avoids ambiguity and ensures consistency.

---

#### **Email Address:**

**Acceptable Values:**

- Must be in a valid email address format following `name@domain.tld`.
- Examples: `john.smith@email.com`, `emily.davis@gmail.com`.
- Case-insensitive for the domain part, though the local part (before `@`) may be case-sensitive depending on the mail
  server.
- Leading/trailing spaces are ignored.

**Error Message:**

- If invalid: `Error: Invalid email address format.`

**Rationale:**

- Ensures that the email address is correctly formatted for communication.

---

#### **Social Media Handle (Optional):**

**Acceptable Values:**

- A string starting with `@`, followed by alphanumeric characters, with optional underscores or periods.
- Examples: `@johnsmith`, `@emily.davis`.
- Case-insensitive, and leading/trailing spaces are ignored.

**Error Message:**

- If invalid: `Error: Invalid social media handle. Use @ followed by alphanumeric characters.`

**Rationale:**

- Standardizing the input ensures consistency and recognizes the format of common social media handles.

---

### **Outputs:**

#### **Success:**

- Message: `Contact successfully added: <Name>`
- GUI Change: The contact's details are displayed in the contact list.

#### **Failure:**

**Error Message Examples:**

- Missing required parameters: `Error: Missing required parameters. Please provide name and phone number.`
- Invalid phone number format: `Error: Invalid phone number format.`
- Invalid birthday format: `Error: Invalid birthday format. Please use YYYY-MM-DD.`
- Multiple errors: `Error: Invalid name and phone number. Please check the format and try again.`

**Duplicate Handling:**

- Duplicate entries are allowed except for the phone number.
- If a duplicate name and address are detected, a warning message is shown: `Warning: Possible duplicate contact.`

---

### 2. **Feature: Search Contacts by Name**

**Purpose:**
To allow users to search through their contact list by entering part or all of a contact's name, helping filter and
display relevant contacts based on the input.

#### **Command Format:**

`search <Name>`

**Example Commands:**

- `search John`
- `search Jane Doe`
- `search Emily`

### **Main Success Scenario (MSS):**

1. The user issues the `search` command with a name or partial name as the input.
2. The system filters the contact list to display all contacts matching the full or partial name.
3. The system displays a list of matching contacts.
4. The filtered contact list is shown in the GUI.

   Use case ends.

---

### **Parameters:**

#### **Name:**

**Acceptable Values:**

- Must be a string containing alphabetic characters, with optional spaces, hyphens, and apostrophes.
- Allows partial or full matches.
- Case-insensitive (e.g., `JOHN` is treated the same as `John`).
- Leading/trailing spaces are ignored.

**Error Message:**

- If invalid: `Error: Name must contain only alphabetic characters, spaces, hyphens, or apostrophes.`

**Rationale:**

- This format allows flexible searching, supporting both partial and full name searches while preventing invalid inputs.

---

### **Outputs:**

#### **Success:**

- Message: A list of matching contacts is displayed.
  Example:
  ```
  Search results for "John":
  1. John Smith - +123456789, john.smith@email.com
  2. Mary Johnson - +987654321, mary.johnson@email.com
  ```
- GUI Change: The filtered contact list is displayed based on the search input.

#### **Failure:**

- No matching contacts: `Error: No contacts found for "John".`
- Invalid input: `Error: Invalid name format. Please enter alphabetic characters only.`
- Empty input: `Error: No name provided. Please enter a name to search.`

---

### **Error Scenarios:**

- **Invalid Characters in Name:**
  Example: `search John123`
  `Error: Name must contain only alphabetic characters, spaces, hyphens, or apostrophes.`

- **No Matching Contacts:**
  Example: `search Zyx`
  `Error: No contacts found for "Zyx".`

- **Empty Search Input:**
  Example: `search` (with no name provided)
  `Error: No name provided. Please enter a name to search.`

---

### 3. **Feature: Alphabetical Sorting of Contact List by Name**

**Purpose:**
To allow users to sort their contact list in alphabetical order (A-Z or Z-A) based on the contact's name.

#### **Command Format:**

`sort <order>`
*Default: Ascending order*

**Example Commands:**

- `sort asc` (to sort contacts in ascending order, A-Z)
- `sort desc` (to sort contacts in descending order, Z-A)

### **Main Success Scenario (MSS):**

1. The user issues the `sort` command, specifying either ascending or descending order.
2. The system reorders the contact list based on the specified order.
3. The sorted contact list is displayed.

   Use case ends.

---

### **Parameters:**

#### **Order:**

**Acceptable Values:**

- Must be either `asc` (ascending) or `desc` (descending).
- Case-insensitive (e.g., `ASC`, `Asc`, and `asc` are treated the same).
- Leading/trailing spaces are ignored.

**Error Message:**

- If invalid: `Error: Invalid sorting order. Use 'asc' for ascending or 'desc' for descending.`

**Rationale:**

- These sorting options are standard and user-friendly, making it easy for users to organize contacts.

---

### **Outputs:**

#### **Success:**

- Message: Contacts are sorted in the specified order.
  Example for ascending order:
  ```
  Contacts sorted A-Z:
  1. Emily Davis - +123456789, emily.davis@email.com
  2. John Smith - +987654321, john.smith@email.com
  ```
- GUI Change: The contact list is reordered alphabetically based on the specified order (A-Z or Z-A).

#### **Failure:**

- Invalid order parameter: `Error: Invalid sorting order. Use 'asc' for ascending or 'desc' for descending.`
- Missing order parameter: `Error: No sorting order provided. Please specify 'asc' or 'desc'.`

---

### **Error Scenarios:**

- **Invalid Sorting Order:**
  Example: `sort ascending`
  `Error: Invalid sorting order. Use 'asc' for ascending or 'desc' for descending.`

- **Missing Sorting Order:**
  Example: `sort`
  `Error: No sorting order provided. Please specify 'asc' or 'desc'.`

---

### 4. **Feature: Undo Contact Deletion**

**Purpose:**
To allow users to recover a contact that was recently deleted, providing a grace period during which deletions can be
reversed.

#### **Command Format:**

`undo <ContactID>`

**Example Commands:**

- `undo 123`
- `undo 4567`

### **Main Success Scenario (MSS):**

1. The user issues the `undo` command with a valid ContactID for a recently deleted contact.
2. The system restores the contact to the contact list.
3. A success message is displayed confirming the contact has been restored.

   Use case ends.

---

### **Parameters:**

#### **ContactID:**

**Acceptable Values:**

- Must be a numeric identifier (integer) assigned to the contact.
- Must reference a contact that was recently deleted.

**Error Message:**

- If invalid: `Error: Invalid ContactID. Please provide a valid numeric identifier.`
- If no recently deleted contact found: `Error: No recently deleted contact found with ID <ContactID>.`

**Rationale:**

- Ensures that only valid, recently deleted contacts are restored, preventing accidental recovery of the wrong contact.

---

### **Outputs:**

#### **Success:**

- Message: `Contact <ContactID> has been successfully restored.`
- GUI Change: The contact reappears in the contact list.

#### **Failure:**

- Invalid ContactID: `Error: Invalid ContactID. Please provide a valid numeric identifier.`
- No Recently Deleted Contact: `Error: No recently deleted contact found with ID <ContactID>.`
- Undo Not Possible: `Error: Cannot undo deletion. The grace period has expired or the contact does not exist.`

---

### **Error Scenarios:**

- **Invalid ContactID:**
  Example: `undo abc`
  `Error: Invalid ContactID. Please provide a valid numeric identifier.`

- **No Recently Deleted Contact:**
  Example: `undo 9999` (assuming 9999 is not in the recent deletion history)
  `Error: No recently deleted contact found with ID 9999.`

- **Undo Operation Not Possible:**
  Example: `undo 123` (if the grace period has expired or the contact is no longer recoverable)
  `Error: Cannot undo deletion. The grace period has expired or the contact does not exist.`

---

### 5. **Feature: Interaction History Log for Each Contact**

**Purpose:**
To maintain a log of interactions (such as calls, messages, meetings) with each contact, allowing users to track and
review their communication history.

#### **Command Format:**

`log <ContactID> [d/<Event date>] l/<InteractionDetails>`

**Example Commands:**

- `log 123 l/Called today, discussed project updates`
- `log 4567 l/Meeting today, reviewed quarterly report`
- `log 4567 d/2024-09-16 l/Meeting on 2024-09-16, reviewed quarterly report`

### **Main Success Scenario (MSS):**

1. The user issues the `log` command with a valid ContactID and interaction details.
2. The system saves the interaction details to the contact’s history.
3. A success message is displayed confirming that the interaction has been logged.
4. The interaction details are added to the contact's profile.

   Use case ends.

---

### **Parameters:**

#### **ContactID:**

**Acceptable Values:**

- Must be a numeric identifier (integer) assigned to the contact.
- Case-insensitive.
- Leading/trailing spaces are ignored.
- Format: Must be a positive integer.

**Error Message:**

- If invalid: `Error: Invalid ContactID. Please provide a valid numeric identifier.`
- If contact not found: `Error: Contact not found. Please check the ContactID and try again.`

**Rationale:**

- Ensures the correct contact is updated with interaction details, preventing errors.

#### **Date: (Optional)**
**Acceptable Values:**

- The date must be in the format yyyy-mm-dd, and must be no earlier than the date of creation of the contact,
- and no later than today (System time)
- Examples: `2024-02-01`, `2021-12-31`, `1999-09-23`.

**Rationale:**

- Ensure no event happening before the contact is added, or in the future is logged.

#### **Logging message:**

**Acceptable Values:**

- A free-form text field describing the interaction, including date, time, and nature of the interaction.
- Leading/trailing spaces are ignored.

**Error Message:**

- If missing: `Error: Interaction details are required. Please provide a description of the interaction.`

**Rationale:**

- Provides flexibility in recording different types of interactions.

---

### **Outputs:**

#### **Success:**

- Message: `Interaction logged for contact <ContactID>: "<InteractionDetails>"`
- GUI Change: The interaction details are added to the contact's interaction history log, which can be viewed or edited.

#### **Failure:**

- Invalid ContactID: `Error: Invalid ContactID. Please provide a valid numeric identifier.`
- Contact Not Found: `Error: Contact not found. Please check the ContactID and try again.`
- Missing Interaction
  Details: `Error: Interaction details are required. Please provide a description of the interaction.`

---

### **Error Scenarios:**

- **Invalid ContactID:**
  Example: `log abc l/Called on 2024-09-15`
  `Error: Invalid ContactID. Please provide a valid numeric identifier.`

- **Contact Not Found:**
  Example: `log 9999 l/Meeting on 2024-09-16`
  `Error: Contact not found. Please check the ContactID and try again.`

- **Missing Interaction Details:**
  Example: `log 123`
  `Error: Interaction details are required. Please provide a description of the interaction.`

---

### 6. **Feature: Create Tags for Contact Categorization**

**Purpose:**
To allow users to create custom tags for contacts, categorizing them based on certain characteristics, such as "high net
worth," "first-time buyer," etc., for better organization and personalization of client interactions.

#### **Command Format:**

`ct <TagName> [create tag]`

**Example Commands:**

- `ct hnw`
- `ct ftb`

### **Main Success Scenario (MSS):**

1. The user issues the `ct` command with a tag name.
2. The system verifies if the tag already exists.
3. If the tag does not exist, it creates the new tag and displays a success message.
4. The new tag is added to the list of available tags for future use.

   Use case ends.

---

### **Parameters:**

#### **Tag Name:**

**Acceptable Values:**

- The tag must not already exist in the system.
- Must be a valid string with alphabetic characters, optionally including hyphens.

**Error Message:**

- If missing: `Error: Tag name is required. Please provide one from the pre-existing list.`
- If the tag already exists: `Error: The tag already exists.`

**Rationale:**

- Allows flexible creation of tags for categorizing contacts. Ensures tags are unique and useful.

---

### **Outputs:**

#### **Success:**

- Message: `The tag was successfully created.`
- GUI Change: The new tag appears in the tag list for future use.

#### **Failure:**

- Duplicate Tag Creation: `Error: The tag already exists.`

---

### **Error Scenarios:**

- **Duplicate Tag Creation:**
  Example: User tries to create a tag that already exists:
  `ct hnw`
  `Error: The tag already exists.`

---

### 7. **Feature: Find Function**

**Purpose:**
To enable users to search for clients by matching on specific fields (e.g., name, address, tag), allowing for efficient
retrieval of client information based on various criteria.

#### **Command Format:**

`find [n/NAME] [a/ADDRESS] [t/TAG]...`

**Example Commands:**

- `find n/John p/65353535 a/Changi t/hnw`
- `find a/New York`

### **Main Success Scenario (MSS):**

1. The user issues the `find` command with specific search parameters (e.g., name, address, or tag).
2. The system retrieves contacts that match the search criteria.
3. A list of matching contacts is displayed.

   Use case ends.

---

### **Parameters:**

#### **Find Command:**

**Acceptable Values:**

- Command syntax must follow the exact format (e.g., `n/`, `a/`, `t/`).

**Error Message:**

- If incorrect format: `Error: Command format is incorrect. Please look at the User Guide for the appropriate format.`

**Rationale:**

- Allows flexible searches based on multiple fields for better filtering and retrieval of contact information.

---

### **Outputs:**

#### **Success:**

- Message: `3 contacts were found that match the inputted parameters:`
    - `ContactID: 23 Name: John Greene`
    - `ContactID: 42 Name: John Paul Sartre`
    - `ContactID: 88 Name: John Constantine`
- If no matches found: `No contacts were found matching those exact parameters.`

#### **Failure:**

- Invalid Command: `Error: Invalid command entered. Please provide a valid command.`
- Invalid Search
  Parameter: `Error: Invalid search parameter entered. Please ensure that the search parameters match the syntax requirements.`

---

### **Error Scenarios:**

- **Invalid Command:**
  Example: `Find`
  `Error: Invalid command entered. Please provide a valid command.`

- **Invalid Search Parameter:**
  Example: `N/`
  `Error: Invalid search parameter entered. Please ensure that the search parameters match the syntax requirements.`

---

### 8. **Feature: Add or Edit Remarks for a Contact**

**Purpose:**
To allow users to add or update a remark for a contact, providing additional notes or information that may not be captured in other fields (e.g., preferences, additional details).

#### **Command Format:**

`remark <index> r/<remark message>`

**Example Commands:**

- `remark 1 r/Interested in buying a condo`
- `remark 3 r/Prefers communication via email`
- `remark 5 r/Follow up in June regarding new listings`

### **Main Success Scenario (MSS):**

1. The user issues the `remark` command with a valid index and a remark message.
2. The system updates the contact at the given index with the provided remark.
3. A success message is displayed confirming that the remark has been added or updated.
4. The contact’s remark field is updated and displayed in the contact list.

   Use case ends.

---

### **Parameters:**

#### **Index:**

**Acceptable Values:**

- Must be a valid index number referencing a contact in the current contact list.
- Must be a positive integer.

**Error Message:**

- If invalid: `Error: Invalid index. Please provide a valid contact index.`

**Rationale:**

- Ensures the correct contact is being updated with the new remark, preventing errors.

#### **Remark Message:**

**Acceptable Values:**

- Any string can be entered as a remark (e.g., client notes, preferences, etc.).
- Leading/trailing spaces are ignored.

**Error Message:**

- If missing: `Error: Remark message is required.`

**Rationale:**

- Allows flexibility in adding any kind of additional notes or information for the contact.

---

### **Outputs:**

#### **Success:**

- Message: `Remark updated for contact at index <index>: "<remark message>"`
- GUI Change: The remark is updated and shown in the contact list.

#### **Failure:**

- Invalid Index: `Error: Invalid index. Please provide a valid contact index.`
- Missing Remark: `Error: Remark message is required.`

---

### **Error Scenarios:**

- **Invalid Index:**
  Example: `remark abc r/Important client`
  `Error: Invalid index. Please provide a valid contact index.`

- **Missing Remark:**
  Example: `remark 1`
  `Error: Remark message is required.`


*{More to be added}*

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be
   able to accomplish most of the tasks faster using commands than using the mouse.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be
       optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    2. Test case: `delete 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message.
       Timestamp in the status bar is updated.

    3. Test case: `delete 0`<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

    4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

    1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
