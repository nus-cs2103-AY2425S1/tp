---
layout: page
title: Developer Guide
---

- Table of Contents
{:toc}

---

## **Acknowledgements**

- Libraries
  used: [JavaFX](https://openjfx.io/), [Jackson](https://github.com/FasterXML/jackson), [JUnit5](https://github.com/junit-team/junit5), [Mockito](https://github.com/mockito/mockito), [TestFX](https://github.com/TestFX/TestFX)
- References
  used: [SE-EDU initiative](https://se-education.org/), [AB4](https://github.com/se-edu/addressbook-level4)

---

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

---

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

</div>

### Architecture

{% include DeveloperGuide/Design/architecture.md %}

### UI component

{% include DeveloperGuide/Design/ui.md %}

### Logic component

{% include DeveloperGuide/Design/logic.md %}

### Model component

{% include DeveloperGuide/Design/model.md %}

### Storage component

{% include DeveloperGuide/Design/storage.md %}

---

## **Documentation, logging, testing, configuration, dev-ops**

- [Documentation guide](Documentation.md)
- [Testing guide](Testing.md)
- [Logging guide](Logging.md)
- [Configuration guide](Configuration.md)
- [DevOps guide](DevOps.md)

---

{% include DeveloperGuide/Requirements/index.md %}

---

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">
:information_source: **Note:** These instructions only provide a starting point for testers to work on; testers are expected to do more *exploratory* testing.
</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   2. Launch the app<br>
      Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app<br>
      Expected: The most recent window size and location is retained.

### Local GUI testing

The available gradle tasks are: guiTests, nonGuiTests, allTests.

- guiTests: all tests under `systemtests` package
- nonGuiTests: all tests under `spleetwaise.address`, `spleetwaise.common`, `spleetwaise.transaction` packages
- allTests: guiTests and nonGuiTests, nonGuiTests will be run before guiTests

As an example, you can run `gradle nonGuiTests` in the gradle terminal for all tests excluding GUI related tests.
You can navigate the gradle terminal by clicking on elephant icon _(Gradle)_ > terminal icon _(Execute Gradle tasks)_.

### Adding a person

1. Adding a person while all persons are being shown

   1. Prerequisites: List all persons using the list command. Multiple persons in the list.

   2. Test case: `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`<br>
      Expected: Assuming no duplicates, a new contact named John Doe is added to the list. Details of the added contact shown in the status message.

   3. Test case: `add n/ p/ e/ a/`<br>
      Expected: No person is added. Error details shown in the status message. Status bar remains the same.

   4. Other incorrect add commands to try: `add n/John Doe`, `add p/98765432`, `add e/johnd@example.com`, `add a/John street, block 123, #01-01`<br>
      Expected: Similar to previous.

### Editing a person

1. Editing a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   2. Test case: `edit 1 n/Jane Doe`<br>
      Expected: Assuming no duplicates, first contact's name is changed to Jane Doe. Details of the edited contact shown in the status message.

   3. Test case: `edit 0 n/Jane Doe`<br>
      Expected: No person is edited. Error details shown in the status message.

   4. Other incorrect edit commands to try: `edit`, `edit x n/Jane Doe` (where x is larger than the list size),
      `edit 1`<br>
      Expected: Similar to previous.

### Person Synchronization in transaction list

1. Ensuring person details changed are accurately reflected in all views and models.

   1. Prerequisites: List all persons and transactions using the `list` and `listTxn` command. At least one person and one transaction with that person in the list.

   2. Test case: `edit 1 n/New Name` (where `New Name` is the updated name for the person)<br>
      Expected: The person's name updates in both the address and transaction lists and the UI (assuming no duplicates).

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   2. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message.

   3. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message.

   4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Adding a transaction

1. Adding a transaction while all transactions are being shown with minimally 1 person in address book

   1. Prerequisites: List all persons and transactions using the `list` and `listTxn` command. At least one person in the address book list and multiple transactions in the list

   2. Test cases: `addTxn 1 amt/12.3 desc/John owes me for dinner`<br>
      Expected: Assuming no duplicates, a new transaction related to the person in the first index of the address book is added to the list along with description of it. The amount reflected in the transaction is displayed green in the transaction panel to signify that _the user_ is owed. The date of the transaction displays the current day. No categories to be displayed. Details of the added transaction is shown in the status message.

   3. Test cases: `addTxn 1 amt/-12.3 desc/I owe John for dinner date/10102024`<br>
      Expected: Assuming no duplicates, a new transaction related to the person in the first index of the address book is added to the list along with description of it. The amount reflected in the transaction is displayed red in the transaction panel to signify that _the user_ owes. The date of the transaction displays `10 Oct 2024`. No categories to be displayed. Details of the added transaction is shown in the status message.

   4. Test cases: `addTxn 1 amt/12.3 desc/John owes me for dinner cat/FOOD`<br>
      Expected: Assuming no duplicates, a new transaction related to the person in the first index of the address book is added to the list along with description of it. The amount reflected in the transaction is displayed green in the transaction panel to signify that you are owed. The date of the transaction displays the current day. Category of `FOOD` is displayed. Details of the added transaction is shown in the status message.

   5. Test cases: `addTxn 1 amt/-12.3 desc/I owe John for dinner date/10102024` when identical transaction exists <br> 
      Expected: No transaction is added. Error details shown in the status message with `Transaction already exists in the transaction book`. Status bar remains the same.

   6. Test cases: `addTxn 0 amt/ desc/ date/ cat/`<br>
      Expected: No transaction is added. Error details shown in the status message. Status bar remains the same.

   7. Other incorrect `addTxn` commands to try: `addTxn 1`, `addTxn amt/1.234`, `addTxn desc/dinner`, `addTxn date/10102024`, `addTxn cat/FOOD`<br>
      Expected: Similar to previous.

### Marking a transaction as done

1. Marking a transaction as done while all transactions are being shown.

   1. Prerequisites: List all transactions using the `listTxn` command. One transaction is in the list.

   2. Test cases: `markDone 1`<br>
      Expected: The first transaction is marked as done. A "done" icon appears next to the person's name for that transaction. Details of the updated transaction shown in the status message.

   3. Test cases: `markDone 1` (Assumes transaction 1 is already marked)<br>
      Expected: No change in transaction status. The "done" icon remains. A status message confirms that the transaction is already marked.

   4. Test cases: `markDone 0`<br>
      Expected: No transaction is marked. Error details shown in the status message.

   5. Other incorrect `markDone` commands to try: `markDone`, `markDone x` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Reverting a done transaction back to undone

1. Reverting a done transaction back to undone while all transactions are being shown.

   1. Prerequisites: List all transactions using the `listTxn` command. One transaction is in the list.

   2. Test cases: `markUndone 1`<br>
      Expected: The first transaction is reverted to undone. The existing "done" icon disappears for that transaction. Details of the updated transaction shown in the status message.

   3. Test cases: `markUndone 1` (Assumes transaction 1 is already undone)<br>
      Expected: No change in transaction status. The transaction remains to have no "done" icon. A status message confirms that the transaction is already undone.

   4. Test cases: `markUndone 0`<br>
      Expected: No transaction is marked. Error details shown in the status message.

   5. Other incorrect `markUndone` commands to try: `markUndone`, `markUndone x` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Editing a transaction

1. Editing a transaction while all persons are being shown

   1. Prerequisites: List all persons and transactions using the `list` and `listTxn` command respectively. Multiple
      persons in the list on the left pane. Multiple transactions in the list on the right pane.

   2. Test case: `editTxn 1 amt/1.23`<br>
      Expected: Assuming no duplicates, first contact's amount is changed to $1.23. Details of the edited transaction shown in the status message.

   3. Test case: `editTxn 0 desc/Updated description`<br>
      Expected: No transaction is edited. Error details shown in the status message.

   4. Other incorrect edit commands to try: `editTxn`, `edit x amt/1.23` (where x is larger than the list size), `editTxn 1`<br>
      Expected: Similar to previous.

### Filter Reuse in Transaction List

1. Maintaining the current filter state when transactions are modified.

   1. Prerequisites: List all transactions using the `listTxn` command. Apply a filter via `filterTxn` command to the list (e.g., filtering by description containing "mac").

   2. Test cases: `addTxn 1 amt/12.3 desc/John owes me for dinner`<br>
      Expected: The new transaction appears in the filtered list while preserving the existing filter. Details of the new transaction shown in the status message.

   3. Test cases: `editTxn 1 d/happy meal at mac` (Assumes transaction 1 description is "KFC")<br>
      Expected: The updated transaction appears in the filtered list while preserving the existing filter. Details of the updated transaction shown in the status message.

   4. Test cases: `editTxn 1 d/KFC` (Assumes transaction 1 description is "fries at mac")<br>
      Expected: The updated transaction disappears in the filtered list while preserving the existing filter. Details of the updated transaction shown in the status message.

   5. Test cases: `markDone 1`, `markUndone 1` (Assumes transaction 1 description is "fries at mac")<br>
      Expected: The transaction done icon updated in the filtered list while preserving the existing filter. Details of the updated transaction shown in the status message.

### Filtering the transaction list.

1. Filtering the transaction list while all persons are being shown

   1. Prerequisites: List all persons and transactions using the `list` and `listTxn` command respectively. Multiple
      persons in the list on the left pane. Multiple transactions in the list on the right pane.

   2. Test case: `filterTxn 1`<br>
      Expected: Transaction list will be filtered by the person corresponding to the displayed index 1 in the person
      list.

   3. Test case: `filterTxn 1 amt/1.23`<br>
      Expected: Transaction list will show transactions related to the person corresponding to the displayed index 1
      in the person list with amount $1.23.

   4. Test case: `filterTxn 0`<br>
      Expected: Current displayed transaction list will remain the same. Error details shown in the status message.

   5. Other incorrect edit commands to try: `filterTxn desc/`, `filterTxn x` (where x is larger than the list size),
      `filterTxn amt/1.222`<br>
      Expected: Similar to previous.

### Default Behavior on App Startup

1. Verifying filter state upon app initialization.

   1. Prerequisites: At least one done transaction and one undone transaction in the list.

   2. Test cases: Initial Filter on App Startup<br>
      Expected: The list displays all transactions (both done and undone) by default when the app starts.

### Saving data

1. Dealing with missing/corrupted data files

   1. Prerequisites: Multiple persons in `addressbook.json` and multiple transactions in `transactionbook.json` data files.<br>
   
   2. Test cases: Missing `name` field in `addressbook.json` data file<br>
      Simulation: Remove the `name` field from a person entry in the JSON file, then start the app.<br>
      Expected: The person is discarded and not loaded to the app, with log message `WARNING: Address book is possibly corrupted: Person's Name field is missing! Ignoring corrupted person.`. If the corrupted person has any related transactions in the transaction book, they will be discarded accordingly with log message `WARNING: Transaction book is possibly corrupted: Person with id [Person-ID] not found! Ignoring corrupted transactions.` - `[Person-ID]` will be the respective person ID of the corrupted person.

   3. Test cases: Missing `isDone` field in `transactionbook.json` data file<br>
      Simulation: Remove the `isDone` field from a transaction entry in the JSON file, then start the app.<br>
      Expected: The transaction loads as undone by default. Upon closing the app, the transaction is saved as undone in the JSON file.
