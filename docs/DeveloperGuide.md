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

### Add Transaction `addt INDEX d/DESCRIPTION amt/AMOUNT o/OTHER_PARTY dt/DATE`

#### Implementation

The add transaction mechanism is facilitated by the creation of a new `Person` with the updated transaction list, 
followed by replacing the target `Person` in the Model with the newly created person by calling `Model#setPerson(Person, Person))`.
`Model#updateFilteredList()` is then called to update the person list to contain all people, including the newly replaced Person.

The following sequence diagram shows an example execution of command `addt 1 ...`.

<puml src="diagrams/AddTransactionSequenceDiagram.puml" width="550" />

### List Transactions `listt INDEX`

#### Implementation

The list transactions command allows for users to view all transactions for the specified person. Notably, when the command is used, `Model#updateFilteredPersonList()` is called to update the person list to just contain that specified person. It also implements the following operations:

* `Model#setIsViewTransactions(boolean)` — Displays the person list in the GUI when false, and displays the transactions list in the GUI when true.
* `Model#updateTransactionList(ObservableList<Transaction>)` — Updates the transaction list to contain transactions for the specified person.

The following sequence diagram shows an example execution of command `listt 1`.

<puml src="diagrams/ListTransactionsDiagram.puml" width="550" />

#### Side Effects

As a result of `listt INDEX` changing the person list, operations on the transactions (e.g. `deletet` and `summary`) can now be performed on the transactions list, without specifying the person index.

The following activity diagram shows how the user should use some of our transaction-related commands.

<puml src="diagrams/ListTransactionsActivityDiagram.puml" width="550" />

### Find Transactions `findt INDEX KEYWORD [KEYWORDS]`

#### Implementation

The find transactions command allows for users to find transactions for the specified person whose descriptions match one of the keywords. 

When the command is used, `Model#updateTransactionList()` is called to update the transaction list to the transactions of the specified person. 
`Model#updateFilteredPersonList()` is called to update the person list to just contain that specified person. 

It also implements the following operations:

* `Model#updateTransactionListPredicate(Predicate<Transaction>)` — Updates the transaction list to contain transactions that match any of the keywords.

The following sequence diagram shows an example execution of command `findt 1 ...`, where `...` represents any number of keywords.

<puml src="diagrams/FindTransactionsDiagram.puml" width="600" />

#### Side Effects

Same as `listt`, `findt` also changes the person list to preserve the setting that the only person in the person list is the specified person whose transactions are currently shown.

As a result, operations on the transactions (e.g. `deletet` and `summary`) can be performed on the transactions list, without specifying the person index, regardless of the transaction list being generated from `listt` or `findt`.

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
* Financial consultants who manage a large number of clients (potentially 1000s)
* Each client has a complex transaction history (potentially 100s of transactions)
* Prefers command-line interfaces for quick and efficient data entry and retrieval
* Can type fast and is comfortable with text-based interfaces
* Requires quick access to financial data and transaction histories
* May need to work with the system for extended periods, necessitating efficiency and ease of use

**Value proposition**: Manage client contacts and transaction history/info faster than a typical mouse/GUI driven app, tailored specifically for financial consultants dealing with numerous clients and their associated transactions.

### User stories

Priorities: High (must have) - `***`, Medium (nice to have) - `**`, Low (unlikely to have) - `*`

| Priority | As a … | I want to … | So that I can… |
|----------|--------|-------------|----------------|
| `***`    | financial consultant | add a new client (add) | track and store client details |
| `***`    | financial consultant with fast turnaround on clients | delete a client (delete) | remove contacts I no longer need |
| `***`    | financial consultant with many clients | view a list of all clients (list) | quickly glance all clients' broad information |
| `***`    | financial consultant with many clients | search for a client by name (find) | quickly find their information |
| `***`    | financial consultant | add transactions to a client's record (addt) | keep track of financial activities for each client |
| `***`    | financial consultant with clients having complex transaction histories | view a list of transactions for a specific client (listt) | assess their financial history at a glance |
| `***`    | financial consultant | delete a transaction from a client's record (deletet) | correct errors or remove outdated information |
| `***`    | financial consultant dealing with many transactions | search transactions by description (findt) | quickly locate specific financial activities |
| `**`     | financial consultant prone to making typos | use fuzzy search | find clients even when I'm not sure of the exact spelling |
| `**`     | financial consultant with clients having complex transaction histories | calculate the balance for a client | quickly assess their overall financial standing |
| `**`     | financial consultant with clients from various industries | tag clients based on industry or other characteristics | easily group and categorise my client base |
| `**`     | financial consultant with volatile clients | edit an existing client's details | update their information when needed |
| `**`     | financial consultant managing clients with interrelated businesses | use nested tags | simulate relationships between clients more accurately |
| `*`      | financial consultant with a growing client base | import and export client data | easily transfer information between systems or share with colleagues |

### Use cases

(For all use cases below, the **System** is `Clientell` and the **Actor** is the `financial consultant`, unless specified otherwise)

**Use case: Add a new client**

**MSS**

1. Financial consultant enters the command to add a new client with all required details
2. Clientell validates the input and adds the new client
3. Clientell displays a confirmation message
Use case ends.

**Extensions**

* 2a. The financial consultant enters invalid client details.
    * 2a1. Clientell shows an error message.
    * Use case ends.

**Use case: Delete a client**

**MSS**

1. Financial consultant enters the delete command with the client index
2. Clientell removes the specified client from the system
3. Clientell displays a confirmation message
Use case ends.

**Extensions**

* 2a. The financial consultant enters an invalid client index.
    * 2a1. Clientell shows an error message.
    * Use case ends.

**Use case: List all clients**

**MSS**

1. Financial consultant enters the list command
2. Clientell displays a list of all clients
Use case ends.

**Use case: Search for a client by name**

**MSS**

1. Financial consultant enters the find command with the client name or search criteria
2. Clientell processes the search and returns a list of the matching clients
Use case ends.

**Extensions**

* 2a. No matching clients found.
    * 2a1. Clientell informs the financial consultant that no matches were found.
    * Use case ends.

**Use case: Add a transaction to a client's record**

**MSS**

1. Financial consultant enters the command to add a transaction with all required details
2. Clientell validates the input and adds the new transaction to the specified client's record
3. Clientell displays a confirmation message
Use case ends.

**Extensions**

* 2a. The financial consultant enters invalid transaction details or an invalid client index.
    * 2a1. Clientell shows an error message.
    * Use case ends.

**Use case: View list of transactions for a specific client**

**MSS**

1. Financial consultant enters the listt command with the client index
2. Clientell displays a list of all transactions for the specified client
Use case ends.

**Extensions**

* 2a. The financial consultant enters an invalid client index.
    * 2a1. Clientell shows an error message.
    * Use case ends.

**Use case: Delete a transaction from a client's record**

**MSS**

1. Financial consultant enters the deletet command with the client index and transaction index
2. Clientell removes the specified transaction from the client's record
3. Clientell displays a confirmation message
Use case ends.

**Extensions**

* 2a. The financial consultant enters an invalid client index or transaction index.
    * 2a1. Clientell shows an error message.
    * Use case ends.

**Use case: Search transactions by description**

**MSS**

1. Financial consultant enters the findt command with description keywords
2. Clientell processes the search and displays matching transactions
Use case ends.

**Extensions**

* 2a. No matching transactions found.
    * 2a1. Clientell informs the financial consultant that no matches were found.
    * Use case ends.

### Non-Functional Requirements

1. Should work on any mainstream OS as long as it has Java `17` or above installed.
2. Should be able to hold up to 1000 clients, each with hundreds of transactions, without a noticeable sluggishness in performance for typical usage.
3. A financial consultant with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. Should respond to commands within 2 seconds for operations not involving complex calculations or large data retrieval.
5. Should be usable by financial consultants with minimal training, leveraging intuitive CLI commands.
6. All client and transaction data should be encrypted at rest to ensure confidentiality.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, macOS
* **Transaction**: A record of a financial activity associated with a client, including details such as amount, description, and date
* **Tag**: A label used to categorise and group clients based on various characteristics (e.g., industry, importance, company)
* **Fuzzy search**: A search technique that finds matches even when the search query is misspelled or only partially correct

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
