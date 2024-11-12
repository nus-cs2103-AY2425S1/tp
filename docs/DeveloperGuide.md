---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# Clientell Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

This project was built on the [AddressBook Level-3](https://se-education.org/addressbook-level3/) as part of a student software engineering project. 

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------
## **Minor definitions**

The documentation uses the terms "client list view" and "transaction list view" to refer to the environment displayed on the UI when the respective list is shown. This table informs you how to switch between these views, which will be useful for later parts of this Developer Guide, such as in some test cases.

Switching between... | Command | Format
---------------|---------------|------------
Client to transaction list view | List Transactions | `listt INDEX`
Transaction to client list view | List Clients | `list`

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

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `ClientListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFX UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Client` object residing in the `Model`.

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
1. The command can communicate with the `Model` when it is executed (e.g. to delete a client).<br>
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

* stores the address book data i.e., all `Client` objects (which are contained in a `UniqueClientList` object).
* stores the currently 'selected' `Client` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Client>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Client` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Client` needing their own `Tag` objects. Note that the `Transaction` list cannot be refactored in the same way, because all transactions are semantically distinct (e.g two copies of a tag are the same, but two transactions with the same descriptions, amounts, date, and parties are separate because they may simply be two transactions on the same day).<br>

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

### Add Transaction 

#### Implementation

The execution of the add transaction command creates a new `Client` with an updated transaction list consisting of the newly added transaction, 
followed by replacing the target `Client` in the Model with the newly created client by calling `Model#setClient(Client, Client)`.

The following sequence diagram shows an example execution of command `addt 1 ...` focusing on interactions within the`Logic` component.

<puml src="diagrams/AddTransactionSequenceDiagram.puml" />

**Note:** The lifeline for `AddTransactionCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

### List Transactions 

#### Implementation

The list transactions command allows for users to view all transactions for the specified client. Notably, when the command is used, `Model#updateFilteredClientList()` is called to update the client list to just contain that specified client. It also implements the following operations:

* `Model#setIsViewTransactions(boolean)` — Displays the client list in the GUI when false, and displays the transactions list in the GUI when true.
* `Model#updateTransactionList(ObservableList<Transaction>)` — Updates the transaction list to contain transactions for the specified client.

The following sequence diagram shows an example execution of command `listt 1`.

<puml src="diagrams/ListTransactionsDiagram.puml" width="550" />

#### Side Effects

As a result of `listt INDEX` changing the client list, operations on the transactions (e.g. `deletet` and `summary`) can now be performed on the transactions list, without specifying the client index.

The following activity diagram shows how the user should use some of our transaction-related commands.

<puml src="diagrams/ListTransactionsActivityDiagram.puml" width="550" />

### Find Transactions

#### Implementation

The find transactions command allows for users to find transactions whose descriptions match one of the keywords. 
The search space is the current transaction list. Therefore, it can only be used in transaction view. 

It implements the following operations:

* `Model#updateTransactionListPredicate(Predicate<Transaction>)` — Updates the transaction list to contain transactions that match any of the keywords.

The following sequence diagram shows an example execution of command `findt keys`, where `keys` represents any number of keywords.

<puml src="diagrams/FindTransactionsDiagram.puml" />

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

**Preconditions: Financial consultant is in client list view**

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

**Preconditions: Financial consultant is in client list view**

**MSS**

1. Financial consultant enters the find command with the client name or search criteria
2. Clientell processes the search and returns a list of the matching clients
Use case ends.

**Extensions**

* 2a. No matching clients found.
    * 2a1. Clientell informs the financial consultant that no matches were found.
    * Use case ends.

**Use case: Add a transaction to a client's record**

**Preconditions: Financial consultant is in client list view**

**MSS**

1. Financial consultant enters the command to add a transaction with all required details
2. Clientell validates the input and adds the new transaction to the specified client's record
3. Clientell displays a confirmation message
Use case ends.

**Extensions**

* 2a. The financial consultant enters invalid transaction details or an invalid client index.
    * 2a1. Clientell shows an error message.
    * Use case ends.
* 2c. The financial consultant inputs an amount that is beyond the range that can be handled by Clientell.
    * 2c1. Clientell shows an error message.
    * Use case ends.

**Use case: View list of transactions for a specific client**

**Preconditions: Financial consultant is in client list view**

**MSS**

1. Financial consultant enters the listt command with the client index
2. Clientell displays a list of all transactions for the specified client
Use case ends.

**Extensions**

* 2a. The financial consultant enters an invalid client index.
    * 2a1. Clientell shows an error message.
    * Use case ends.

**Use case: Delete a transaction from a client's record**

**Preconditions: Financial consultant is in transaction list view**

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

**Preconditions: Financial consultant is in transaction list view**

**MSS**

1. Financial consultant enters the findt command with description keywords
2. Clientell processes the search and displays matching transactions
Use case ends.

**Extensions**

* 2a. No matching transactions found.
    * 2a1. Clientell informs the financial consultant that no matches were found.
    * Use case ends.

**Use case: Summarise transactions in given range**

**Preconditions: Financial consultant is in transaction list view**

**MSS**

1. Financial consultant enters the summary command with the start and end months.
2. Clientell calculates the total amount of transactions in the specified range.
3. Clientell displays the transactions in the specified range and the total amount.
Use case ends.

**Extensions**

* 2a. The financial consultant enters an invalid month.
    * 2a1. Clientell shows an error message.
    * Use case ends.
* 2b. The financial consultant enters an incorrect month format.
    * 2b1. Clientell shows an error message.
    * Use case ends.
* 2c. No transactions found in the specified range.
    * 2c1. Clientell displays no transactions.
    * 2c2. Clientell displays the total amount as 0.
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

   2. Double-click the jar file
      Expected: Shows the GUI with a set of sample contacts. The window size may not be optimal.

2. Saving window preferences

   1. Resize the window to an optimal size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

3. Shutting down
   1. Test case: `exit`<br>
      Expected: The app shuts down and the window closes. A JSON file `clientell.json` is generated in the data file directory if there previously wasn't; otherwise, it updates with any new changes.
   2. Test case: Click the `X` button on the window<br>
      Expected: Identical to above.

### Deleting a client

1. Deleting a client in the client list view

   1. Prerequisites: List all clients using the `list` command. Multiple clients in the list.

   2. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. 

   3. Test case: `delete 0`<br>
      Expected: No client is deleted. Error details informing of invalid command shown in the status message.
  
    4. Test case: `delete x`, where `x` is exactly 1 more than client list size<br>
      Expected: No client is deleted. Error detail informing out of range index shown in the status message.

   5. Test case: `delete`<br>
      Expected: No client is deleted. Error details informing of missing index parameter shown in the status message.

2. Deleting a client in the transaction list view
   1. Prerequisites: List all transactions of a client, such as the first, using the `listt 1` command.
  
    2. Test case: `delete 0`<br>
      Expected: No client is deleted. Error detail informing of invalid command shown in the status message.

    3. Test case: `delete 1`<br>
      Expected: No client is deleted. Error detail informing of environment discrepancy shown in the status message.


### Finding clients

1. Finding a client in the client list view

   1. Prerequisites: List all clients using the `list` command. Multiple clients in the list.

   2. Test case: `find Alex`<br>
      Expected: All clients whose name or company contain Alex are shown. Details of the search shown in the status message.

   3. Test case: `find`<br>
      Expected: Error details informing of invalid command format shown in the status message.

   4. Test case: `find Alex innovative`<br>
      Expected: All clients whose name or company contain Alex or innovative are shown. Details of the search shown in the status message.


2. Finding a client in the transaction list view

   1. Prerequisites: List transactions for a client using the `listt INDEX` command.

   2. Test case: `find Alex`<br>
      Expected: Error details informing of environment discrepancy shown in the status message.


### Adding a transaction to a client 

1. Adding a transaction in the client list view.

    1. Prerequisites: List all clients using the `list` command. Multiple clients in the list.

    2. Test case: `addt 1 d/buy new equipment amt/-1000 o/ABC Motor Group dt/2024-11-17`<br>
       Expected: Transaction is added to first client. Details of transaction and the client transaction was added to shown in status message.

    3. Test case: `addt 0 d/buy new equipment amt/-1000 o/ABC Motor Group dt/2024-11-17`<br>
       Expected: No transaction is added to any client. UI still shows the full client list. Error details informing of invalid command format shown in the status message.

   4. Test case: `addt x d/buy new equipment amt/-1000 o/ABC Motor Group dt/2024-11-17` (where x is larger than list size)<br>
       Expected: Expected: No transaction is added to any client. UI still shows the full client list. Error details informing of invalid index shown in the status message.

2. Adding a transaction in transaction list view.

    1. Prerequisites: List transactions for a client using the `listt INDEX` command.

    2. Test case: `addt 1 d/buy new equipment amt/-1000 o/ABC Motor Group dt/2024-11-17`<br>
       Expected: UI still shows the transactions list. Error details informing of environment discrepancy shown in the status message. 


### Listing transactions for a client

1. Listing transactions in the client list view.

    1. Prerequisites: List all clients using the `list` command. Multiple clients in the list.

    2. Test case: `listt 1`<br>
       Expected: Transactions for the first client are shown. Details of the selected client shown in the status message. 

    3. Test case: `listt 0`<br>
       Expected: UI still shows the full client list. Error details shown in the status message. Status bar remains the same.

    4. Other incorrect listt commands to try: `listt`, `listt x` (where x is larger than the list size), `listt hello`<br>
       Expected: Similar to previous.

2. Listing transactions in the transaction list view.

    1. Prerequisites: List transactions for a client using the `listt INDEX` command.

    2. Test case: `listt x`<br>
       Expected: UI still shows the transactions list. Error details shown in the status message. Status bar remains the same.

### Finding transactions

1. Finding transactions in the client list view.

    1. Prerequisites: List all clients using the `list` command. Multiple clients in the list.

    2. Test case: `findt invest`<br>
       Expected: Error details informing of environment discrepancy shown in the status message.

2. Finding transactions in the transaction list view.

    1. Prerequisites: List transactions for a client using the `listt INDEX` command.

    2. Test case: `findt invest`<br>
       Expected: All transactions whose description contain "invest" are shown. Details of the search shown in the status message.

    3. Test case: `findt`<br>
       Expected: Error details informing of invalid command format shown in the status message. 
   
    4. Test case: `findt invest stocks`<br>
        Expected: All transactions whose description contain "invest" or "stocks" are shown. Details of the search shown in the status message.

### Summarising transactions

1. Summarising transactions in the client list view.

    1. Prerequisites: List all clients using the `list` command. Multiple clients in the list.

    2. Test case: `summary s/2024-11 e/2024-12`<br>
       Expected: Error details informing of environment discrepancy shown in the status message.
   

2. Summarising transactions in the transaction list view.

   1. Prerequisites: List transactions for a client using the `listt INDEX` command.

   2. Test case: `summary s/2024-12 e/2024-12`<br>
      Expected: The transactions from `2024-12-01` to `2024-12-31` are shown. The total amount of these transactions is shown in the status message.

   3. Test case: `summary s/2024-11 e/2025-01`<br>
      Expected: The transactions from `2024-11-01` to `2025-01-31` are shown. The total amount of these transactions is shown in the status message.

   4. Test case: `summary s/2024-11 e/2024-10`<br>
      Expected: Error details informing of invalid date range shown in the status message.

   5. Test case: `summary s/2024-11 e/2024-13`<br>
      Expected: Error details informing of invalid month or incorrect format shown in the status message.

   6. Test case: `summary s/11-2024 e/12-2024`<br>
      Expected: Error details informing of invalid month or incorrect format shown in the status message.


### Saving data

1. Missing data file

    1. Test case: Delete `clientell.json` from the data file directory. If there is no such file, do nothing.<br>
       Expected: The app launches correctly with a client list populated with sample clients.

2. Corrupted data file

    1. Test case: Add irrelevant key-value pairs in `clientell.json`.<br>
       Expected: The app populates data, ignoring all irrelevant key-value pairs.

    2. Test case: Add duplicate key-value pairs in `clientell.json`.<br>
       Expected: The app populates data, ignoring all duplicates except the final pair.

    3. Test case: Modify an existing value to be illegal in `clientell.json`, such as `phone: (+1234)`.<br>
       Expected: The app launches with an empty UI and no data.

3. Editing while app is active

    1. Prerequisite: The app is active.

    2. Test case: Make a legal edit anywhere in `clientell.json`, such as changing the first client's name to `name: "John"`. Then close the app.<br>
       Expected: The legal edit is overwritten by the new data from the app's most recent session.




--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

Team size: 5

1. Overload `listt` to not take in an index in transaction list view, to view the whole transactions list for the selected client. Currently, `listt INDEX` can only be used in the client list view, and will display the transaction list view. This is inconvenient when the user wants to stay in the transaction list and still want to view the full list, perhaps after searching for some transactions (because he/she would have to return to the client list using `list`, then `listt INDEX` to get back to the transaction list in full. Future implementation allows `listt INDEX` to operate as currently, but also accept `listt` without `INDEX` to work in the transaction list view and show the full transaction list of the current client.

2. Improve `find` to employ fuzzy search via regular expression ([regex](https://en.wikipedia.org/wiki/Regular_expression)). Existing implementation strictly searches for full word, case-insensitively. Future implementation uses regex to allow far more flexible searching, such as `(?i)^cla.*r.*ce$` to match all strings starting with `cla`, containing `r` somewhere in the middle, ending with `ce`, case-insensitively (so it might match `Clarance`, `clairice`, `Clarice`, etc.)

3. Improve `findt` to employ fuzzy search via regular expression ([regex](https://en.wikipedia.org/wiki/Regular_expression)). Existing implementation strictly searches for full word, case-insensitively. Future implementation uses regex to allow far more flexible searching, such as `(?i)^cla.*r.*ce$` to match all strings starting with `cla`, containing `r` somewhere in the middle, ending with `ce`, case-insensitively (so it might match `Clarance`, `clairice`, `Clarice`, etc.)

4. Improve `find` to take in additional logic info to specify what fields to search for. Existing implementation searches for name OR company. Future enhancement will allow user to specify the search with greater granularity e.g company OR address, (name AND email AND company) OR tags, etc.

5. Improve data saving/loading of corrupted files. Currently, corruption in data file causes app to launch with empty book, and any updates in this session, upon the session's termination, will override the existing corrupted data file. Future enhancement will launch the app with a specified error to inform that the data file is corrupted, and disables usage until the corrupted data file is corrected/recovered. This is to ensure the corrupted data file (which may be important and only suffering from a minor typo) is not permanently lost.<br>

6. Improve implementation of `tags` to support more complex tag relationships. Currently, tags are simply in a list. Future enhancement allows more intricate relationship between tags to better model real world relationships via hierachial structure, likely implemented with nested lists and some global info. For example, it can be specified that the tags `bakery` `hawker` inherent from tag `FnB`, so any tag with `bakery` would also be auto-tagged with `FnB` for other purposes like searching.

7. Overload `add` to add transactions if the relevant fields for adding a transaction is given. Currently, `add` and `addt` are separate commands. In future implementation, `addt` will be subsumed and overloaded in `add` for a more intuitive user experience. For example, if `add` is supplied with `n/NAME` (and other client fields), it adds a client; if supplied with `INDEX` (and other transaction fields), it adds a transaction to the specified indexed client.

8. Overload `find` to find clients and transactions depending on the environment the command is used in. Currently, `find` and `findt` are separate commands. In future implementation, `findt` will be subsumed and overloaded in `find` for a more intuitive user experience. This will also work in conjunction with enhancement 4, in which `find` is enhanced with more customisable, granular logic. For example, if `find` is supplied with usual keywords and used in the client list view, it performs a search in the client list; if supplied with other keywords and used in the transaction list view, it performs a serach on the indexed client's transaction list.

9. Overload `delete` to delete clients and transactions depending on the environment the command is used in. Currently, `delete` and `deletet` are separate commands. In future implementation, `deletet` will be subsumed and overloaded in `delete` for a more intuitive user experience. For example, `delete INDEX` deletes the indexed client while in the client list view, and deletes the indexed transaction while in the transaction list view.


