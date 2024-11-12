---
layout: default.md
title: "Developer Guide"
pageNav: 3
---

# DLTbook Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

---

## **Acknowledgements**

Github Copilot/Claude/ChatGPT was used generally on a whole to either generate test cases, add comments, autocomplete
parts of code.
AI generated content was verified by a human.

We referenced the general format of the User Guide and Developer Guide from this repo:
https://github.com/AY2324S1-CS2103T-T11-2/tp/blob/master/docs/

---

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

---

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The **_Architecture Diagram_** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [
`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [
`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in
charge of the app launch and shut down.

- At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
- At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

- [**`UI`**](#ui-component): The UI of the App.
- [**`Logic`**](#logic-component): The command executor.
- [**`Model`**](#model-component): Holds the data of the App in memory.
- [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The _Sequence Diagram_ below shows how the components interact with each other for the scenario where the user issues
the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

- defines its _API_ in an `interface` with the same name as the Component.
- implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API
  `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using
the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component
through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the
implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [
`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`,
`StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures
the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that
are in the `src/main/resources/view` folder. For example, the layout of the [
`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java)
is specified in [
`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

- executes user commands using the `Logic` component.
- listens for changes to `Model` data so that the UI can be updated with the modified data.
- keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
- depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [
`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API
call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of
PlantUML, the lifeline continues till the end of diagram.

</box>

Other commands share the similarity of editing a person, so an abstract `AbstractEditCommand` class is created to
handle these commands:

Here is a class diagram showing the classes related to the `AbstractEditCommand`:

<puml src="diagrams/AbstractEditCommandClassDiagram.puml" width="550"/>

The parameters of a few methods in the `AbstractEditCommand` class are omitted for brevity. For example, the `createEditedPerson, saveEditedPerson` methods have additional parameters that are not shown in the diagram. They can be found below.

<puml src="diagrams/AbstractEditCommandClassDiagramMethods.puml" width="550"/>


The snippet below shows how the "execute" method is implemented in the `AbstractEditCommand` class:

```java
@Override
public CommandResult execute(Model model) throws CommandException {
    Person personToEdit = getPersonToEdit(model);
    Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
    return saveEditedPerson(model, personToEdit, editedPerson, MESSAGE_EDIT_PERSON_SUCCESS);
}
```

Pertinently, the method `createEditedPerson` will merge a `personToEdit` object with an `editPersonDescriptor` object by optionally replacing the fields in `personToEdit` with the fields in `editPersonDescriptor` only if they exist. As such, all fields will be entirely overwritten if they are non-empty in `editPersonDescriptor`.

```java
private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
    assert personToEdit != null;

    Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
    Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
    Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
    Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
    PublicAddressesComposition updatedPublicAddresses =
        editPersonDescriptor.getPublicAddresses().orElse(personToEdit.getPublicAddressesComposition());
    Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

    return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedPublicAddresses, updatedTags);
}
```

`makeEditedPerson` is a `BiFunction` that will allow the `personToEdit` and `editPersonDescriptor` objects to be merged in a manner defined by the child methods using `BiFunction`. This is useful for commands that require different ways of merging the two objects.

`successMessage` allows a custom success message to be passed to the `saveEditedPerson` method. This is useful for commands that require different success messages.

```java
CommandResult execute(
    Model model,
    BiFunction<? super Person, ? super EditPersonDescriptor, ? extends Person> makeEditedPerson,
    String successMessage
) throws CommandException {
    Person personToEdit = getPersonToEdit(model);
    Person editedPerson = makeEditedPerson.apply(personToEdit, editPersonDescriptor);
    return saveEditedPerson(model, personToEdit, editedPerson, successMessage);
}
```

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

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:

- When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a
  placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse
  the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a
  `Command` object.
- All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser`
  interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [
`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />

The `Model` component,

- stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
- stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which
  is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to
  this list so that the UI automatically updates when the data in the list change.
- stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a
  `ReadOnlyUserPref` objects.
- does not depend on any of the other three components (as the `Model` represents data entities of the domain, they
  should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which
`Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person`
needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

### Storage component

**API** : [
`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,

- can save both address book data and user preference data in JSON format, and read them back into corresponding
  objects.
- inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only
  the functionality of only one is needed).
- depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects
  that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

---

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Search Public Address feature

The `searchpa` command is a feature that allows users who have a public address to quickly find the contact within the
DLTbook which this public address is associated to. An example is
`searchpa pa/bc1q5y5960gr9vnjlmwfst232z07surun7rey5svu9`

#### Implementation

The `searchpa` command is facilitated by `SearchPublicAddressCommand`,`SearchPublicAddressCommandParser` and
`PublicAddressComposition`. It uses `Model#getFilteredPersonList()` to get the list of filtered persons currently
displayed and searches each of the persons for the presence of the public address being searched for using
Person#hasPublicAddressStringAmongAllNetworks(String publicAddressString).

#### How to execute the command

1. The `searchpa` command is executed by typing `searchpa` followed by the public address to be searched for.
2. The `AddressBookParser` class creates a new `SearchPublicAddressCommandParser` object.
3. If there is no string of Public Address entered or more than 1 string of public address entered, a `ParseException
   will be thrown.
4. The `SearchPublicAddressCommandParser` class creates a new `SearchPublicAddressCommand` object with the parsed public
   address string.
5. The `SearchPublicAddressCommand` object calls the PublicAddressComposition#validatePublicAddress(String
   publicAddressString) method to validate the public address string, and throws a `ParseException` if the public
   address is invalid.
6. The `SearchPublicAddressCommand` object then calls the `Model#getFilteredPersonList()` method
   to get the list of filtered persons currently displayed.
7. The `SearchPublicAddressCommand` object then searches each of the persons for the presence of the public address
   being
   searched for using `Person#hasPublicAddressStringAmongAllNetworks(String publicAddressString)`.
8. `Person#hasPublicAddressStringAmongAllNetworks(String publicAddressString)` calls
   `PublicAddressComposition#hasPublicAddress(String publicAddressString)`.
9. The boolean value returned from `PublicAddressComposition#hasPublicAddress(String publicAddressString)` is then used
   to
   create a `List<Persons>` object that have the public address being searched for in the `SearchPublicAddressCommand`
   object.
10. The `SearchPublicAddressCommand` object then calls the
    `SearchPublicAddressCommand#generateResult(List<Person> personsWithPublicAddressMatch)` to generate the success or
    error message.
    The sequence diagram below shows how the `searchpa` command works from steps 1-10.

<puml src="diagrams/SearchPublicAddressSequenceDiagram.puml" />

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo
history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the
following operations:

- `VersionedAddressBook#commit()`— Saves the current address book state in its history.
- `VersionedAddressBook#undo()`— Restores the previous address book state from its history.
- `VersionedAddressBook#redo()`— Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and
`Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the
initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls
`Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be
saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls
`Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will
not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the
`undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once
to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no
previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the
case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the
lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once
to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address
book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()`
to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as
`list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus,
the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4"></puml>

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not
pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be
purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern
desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5"></puml>

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250"></puml>

#### Design considerations:

**Aspect: How undo & redo executes:**

- **Alternative 1 (current choice):** Saves the entire address book.

    - Pros: Easy to implement.
    - Cons: May have performance issues in terms of memory usage.

- **Alternative 2:** Individual command knows how to undo/redo by
  itself.
    - Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
    - Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_

---

## **Documentation, logging, testing, configuration, dev-ops**

- [Documentation guide](Documentation.md)
- [Testing guide](Testing.md)
- [Logging guide](Logging.md)
- [Configuration guide](Configuration.md)
- [DevOps guide](DevOps.md)

---

## **Appendix: Requirements**

### Product scope

**Target user profile**:

- has a need to manage a significant number of contacts
- prefer desktop apps over other types
- can type fast
- prefers typing to mouse interactions
- is reasonably comfortable using CLI apps
- is a user of DLTs
- has many contacts in address book with many DLT public addresses to manage

**Value proposition**: 

- Manage DLT public address of contacts faster than a typical mouse/GUI driven app
- Easy storage and access of public addresses of contacts

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                                                                               | So that I can…​                                                        |
|----------|--------------------------------------------|--------------------------------------------------------------------------------------------|------------------------------------------------------------------------|
| `* * *`  | user                                       | add a Crypto public address to a user's contacts                                           | easily get their public address                                        |
| `* * *`  | user                                       | easily select the public address of each user according to the blockchain network          | manage addresses by network                                            |
| `* * *`  | user                                       | specify which blockchain network each address belongs to (e.g., Bitcoin, Ethereum, Solana) | organize my addresses by network                                       |
| `* * *`  | user                                       | retrieve addresses in my address book by wallet label, network and contact's name          | quickly find the information I need                                    |
| `* * *`  | user                                       | search for addresses in my address book by the public address value                        | verify that the address I am sending to is of the intended recipient   |
| `* * *`  | user                                       | edit existing address entries                                                              | update information or correct mistakes                                 |
| `* * *`  | user                                       | delete addresses from my address book                                                      | remove addresses I no longer need                                      |
| `* * *`  | user                                       | copy an address to my clipboard with a single click                                        | easily paste it elsewhere                                              |
| `* * *`  | user                                       | export my address book as a file                                                           | back it up or transfer it to another device                            |
| `* * *`  | user                                       | import an address book file                                                                | restore my data or add multiple addresses at once                      |
| `* * *`  | user                                       | group related addresses together (e.g., multiple addresses for the same network)           | maintain a cleaner address book structure                              |
| `* * *`  | new user                                   | see usage instructions                                                                     | refer to instructions when I forget how to use the App                 |
| `* * *`  | user                                       | add a new person                                                                           |                                                                        |
| `* * *`  | user                                       | delete a person                                                                            | remove entries that I no longer need                                   |
| `* * *`  | user                                       | find a person by name                                                                      | locate details of persons without having to go through the entire list |
| `* *`    | user                                       | hide private contact details                                                               | minimize chance of someone else seeing them by accident                |
| `*`      | user with many persons in the address book | sort persons by name                                                                       | locate a person easily                                                 |

### Use cases

(For all use cases below, the **System** is the `DLTbook` and the **Actor** is the `user`, unless specified
otherwise)

**Use case: Filter persons by network**

**MSS**

1. User requests to filter persons by a specific network.
2. DLTbook shows a list of persons with the specified network.

**Extensions**

- 2a. No persons have the specified network.
    - 2a1. DLTbook shows an error message.

**Use case: Delete a person**

**MSS**

1. User requests to list persons
2. DLTbook shows a list of persons
3. User requests to delete a specific person in the list
4. DLTbook deletes the person

**Extensions**

- 2a. The list is empty.

  Use case ends.

- 3a. The given index is invalid.

  - 3a1. DLTbook shows an error message.

    Use case resumes at step 2.

**Use case: Edit a public address**

**Preconditions**: List of contacts currently displayed

**MSS**
1. User requests to edit public address, specifying details of the public address to edit and the new public address.
2. DLTbook updates the public address to the new value.

Use case ends.

**Extensions**

* 1a. DLTbook detects an error in the entered command / data.

    * 1a1. DLTbook informs user of error.

  Use case ends.

* 1b. DLTbook cannot find a matching public address based on user's provided details.

    * 1b1. DLTbook informs user that there is no matching public address.

  Use case ends.

**Use case: Retrieve public addresses based on label**

**MSS**
1. User requests to retrieve public address, specifying (part of) the label of public addresses to retrieve.
2. DLTbook shows a list of public addresses whose labels includes the provided label.

Use case ends.

**Extensions**

* 1a. DLTbook detects an error in the entered command / data.

    * 1a1. DLTbook informs user of error.

  Use case ends.

* 1b. DLTbook cannot find matching public addresses based on user's provided details.

    * 1b1. DLTbook informs user that there ate no matching public addresses.

  Use case ends.

**Use case: Retrieve public addresses based on label and network type**

**MSS**
1. User requests to retrieve public address, specifying (part of) the label and network of public addresses to retrieve.
2. DLTbook shows a list of public addresses whose labels includes the provided label, and network type matches the provided network.

Use case ends.

**Extensions**

* 1a. DLTbook detects an error in the entered command / data.

    * 1a1. DLTbook informs user of error.

  Use case ends.

* 1b. DLTbook cannot find matching public addresses based on user's provided details.

    * 1b1. DLTbook informs user that there ate no matching public addresses.

  Use case ends.

**Use case: Retrieve public addresses based on label and contact's name**

**MSS**
1. User requests to retrieve public address, specifying (part of) the label and (part of) the contact's name.
2. DLTbook shows a list of public addresses whose labels includes the provided label, and contact's name includes the provided name.

Use case ends.

**Extensions**

* 1a. DLTbook detects an error in the entered command / data.

    * 1a1. DLTbook informs user of error.

  Use case ends.

* 1b. DLTbook cannot find matching public addresses based on user's provided details.

    * 1b1. DLTbook informs user that there ate no matching public addresses.

  Use case ends.

**Use case: Retrieve public addresses based on label, network type and contact's name**

**MSS**
1. User requests to retrieve public address, specifying (part of) the label, network of public addresses to retrieve and (part of) the contact's name.
2. DLTbook shows a list of public addresses whose labels includes the provided label, network type matches the provided network and contact's name includes the provided name.

Use case ends.

**Extensions**

* 1a. DLTbook detects an error in the entered command / data.

    * 1a1. DLTbook informs user of error.

  Use case ends.

* 1b. DLTbook cannot find matching public addresses based on user's provided details.

    * 1b1. DLTbook informs user that there ate no matching public addresses.

  Use case ends.



### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be
   able to accomplish most of the tasks faster using commands than using the mouse.


### Glossary

| Term                               | Definition                                                                                                                                                                                           |
|------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Blockchain**                     | A system in which a record of transactions made in bitcoin or another cryptocurrency is maintained across several computers that are linked in a peer-to-peer network.                               |
| **Blockchain Network/DLT Network** | A decentralized digital ledger that records transactions across multiple computers. Acts like a currency, but it's digital and secure. (e.g., Bitcoin, Ethereum, Solana).                            |
| **BTC**                            | Bitcoin, a decentralized digital currency without a central bank or single administrator that can be sent from user to user on the peer-to-peer bitcoin network without the need for intermediaries. |
| **CLI**                            | Command Line Interface, a text-based interface used to interact with software and operating systems by typing commands.                                                                              |
| **DLT**                            | Distributed Ledger Technology, the technological infrastructure and protocols that allow simultaneous access, validation, and record updating across a networked database.                           |
| **ETH**                            | Ethereum, a decentralized, open-source blockchain with smart contract functionality, Ether (ETH) is the native cryptocurrency.                                                                       |
| **GUI**                            | Graphical User Interface, allows users to interact with electronic devices through graphical icons and visual indicators.                                                                            |
| **JAR**                            | Java Archive, a package file format typically used to aggregate many Java class files and associated metadata and resources into one file for distribution.                                          |
| **JSON**                           | JavaScript Object Notation, a lightweight data-interchange format that is easy for humans to read and write, and easy for machines to parse and generate.                                            |
| **Public Address**                 | A unique series of alphanumerical characters that is shared with others to receive cryptocurrencies (similar to an account number).                                                                  |
| **SOL**                            | Solana, a high-performance blockchain supporting builders around the world creating crypto apps that scale, Solana (SOL) native cryptocurrency.                                                      |                                                                                                                                                                    |
| **Mainstream OS**                  | Windows, Linux, Unix, MacOS                                                                                                                                                                          |
| **Private contact detail**         | A contact detail that is not meant to be shared with others                                                                                                                                          |


---

## **Appendix: Planned Enhancements**


1. In the current state, if a very long name is entered, the command box will be very long, text wrapping should be
   implemented.

2. In the current state, for the searchpa command if users enter extraneous inputs according to user guide, Dltbook will
   serve a error message like but this error message does not accurately reflect the error caused by extraneous input.
   for example if the command "searchpa pa/bc1q5y5960gr9vnjlmwfst232z07surun7rey5svu9 w/main" is entered but the prefix
   w/
   is not recognized globally, a error of "Public Address for length BTC/ETH/SOL should be less than 44 characters"
   occurs.
   of if the command "searchpa pa/bc1q5y5960gr9vnjlmwfst232z07surun7rey5sv n/s" is entered, the error message of "Public
   Address contains only alphanumeric characters" occurs.

3. In the current state, for the add/edit command, only alphanumeric character names are allowed. We shall add support
   for more with special characters in the future.

4. In the current state, for the add/edit command, contacts in DLTbook have no support for phone numbers with symbols
   such as "+"
   and "-" in the phone number field which may be useful for saving international phone numbers

5. In the current state, for the add/edit command, Duplicates allowed The email field of contacts in DLTbook have no
   restrictions and can be duplicated across contacts.


## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more _exploratory_ testing.

</box>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

   2. Open a terminal, and navigate to the folder you put the JAR file in.

   3. Launch the app by using the command `java -jar DLTbook.jar` in your terminal.
   Expected: Shows the GUI with a set of sample applicants and interviews. The window size may not be optimum.

2. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by using the command `java -jar DLTbook.jar` in your terminal.<br>
      Expected: The most recent window size and location is retained.

### Adding a Public Address

1. Prerequisites: Persons exist in the list.

2. Try `addpa 1 c/ETH l/default pa/0x0b1c9e1fb5e13c797c7f0134641810e9a7ca14d2`<br>
   Expected: Public Address is added to the person. Details of the added public address shown in the status message. The list is updated with the new public address inside contacts at INDEX 1.

### Retrieving a Public Address

1. Prerequisites: Persons and its relevant public addresses exist in the list.

2. Try `retrievepa l/default`<br>
   Expected: Retrieves all public addresses with labels containing "default" for all contacts and networks.

3. Try `retrievepa l/default n/Alex`<br>
     Expected:  Retrieves all public addresses with labels containing "default" for contacts whose names contain "Alex".

4. Try `retrievepa l/main c/btc n/bernice`<br>
        Expected:  Retrieves all BTC public addresses with labels containing "main" for contacts whose names contain "bernice".

### Editing a Public Address

1. Prerequisites: Persons and its relevant public addresses and labels exist in the list.

2. Try `editpa 1 c/BTC l/Main wallet pa/bc1phkt4pgl42lad3mm2srne73y8a7zgam3cumrzmc`<br>
    Expected: Edits the contact at index 1's BTC public address labelled as "Main wallet" to the new value bc1phkt4pgl42lad3mm2srne73y8a7zgam3cumrzmc.<br>

### Search for a Public Address

1. Prerequisites: Persons and its relevant public addresses and labels exist in the list.

2. Try `searchpa pa/bc1q5y5960gr9vnjlmwfst232z07surun7rey5svu9 `<br>
   Expected: Searches for a public address bc1q5y5960gr9vnjlmwfst232z07surun7rey5svu9 and displays the contacts and wallets to which it belongs.

### Filter for contacts by network

1. Prerequisites: Persons and its relevant public addresses and labels exist in the list.

2. Try `filterpa c/BTC`<br>
   Expected: Filters a list of contacts with the public addresses of BTC and displays it with their respective list number.

### Deleting a Public Address

1. Prerequisites: Persons and its relevant public addresses and labels exist in the list.

3. Try `deletepa 1 c/BTC l/Main wallet`<br>
   Expected: Deletes the public address of the contact at index 1 with the label "Main wallet".

3. Try `deletepa 1 c/BTC`<br>
    Expected: Deletes all the BTC public addresses of the contact at index 1.

<box type = "success">

Good job!🥳 You have tested the basic functionalities of the app. We recommend you to explore the app further to test
more functionalities.

</box>
