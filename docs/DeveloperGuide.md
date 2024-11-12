---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* Initial project template:
    * AB3 - Utilised the overall AB3 code structure and some basic core functionalities of AB3 in the AgentConnect application.
* Third party libraries:
    * JUnit - For unit testing of the application
    * JavaFX - For creating user interface
--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

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

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

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

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete John Doe")` API call as an example.

![Interactions Inside the Logic Component for the `delete John` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

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

Here is the activity diagram when a user interacts with AgentConnect.

<img src="images/CommandSummaryActivityDiagram.png" width="600"/>


### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Undo/redo feature

#### Implementation

The undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

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

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

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

* **Current Implementation:** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 1:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.



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

* Insurance agents tracking their clients
* Has a need to manage and track a significant number of clients with detailed insurance-related information.
* Frequently engages with clients, requiring automated reminders for appointments, renewals, and follow-ups.
* Prefers desktop apps that support fast and efficient data management over mobile or web alternatives.
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**:  provides quick and efficient access to client details, tailored for insurance agents who need a streamlined interface to manage contacts, track policy updates, and schedule client follow-ups.



### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a ... | I want to ...                                      | So that I can ...                                                             |
|----------|----------|----------------------------------------------------|-------------------------------------------------------------------------------|
| `* * *`  | user     | add clients to my existing addressbook             | store their contacts and respective information                               |
| `* * *`  | user     | know if client has been added successfully         | so that I can proceed with the next steps or take corrective action if needed |
| `* * *`  | user     | mark a client’s insurance payment as paid          | keep their due date up to date and know when they’ve fully paid               |
| `* * *`  | user     | update existing client details                     | keep their information up to date.                                            |
| `* * *`  | user     | record client's email address                      | contact them through email                                                    |
| `* * *`  | user     | sort clients by renewal dates                      | prioritize my outreach efforts                                                |
| `* * *`  | user     | remove clients should they change insurance agents |                                                                               |
| `* * *`  | user     | know when is my client's next appointment          | track when to follow up                                                       |
| `* * *`  | user     | know when is my client's birthday                  | reach out to build rapport                                                    |
| `* * *`  | user     | know when is my client's next insurance payment    | so that I can keep track of client's payment                                  |


### Use cases

(For all use cases below, the **System** is the `AgentConnect` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Add a person**

**MSS**

1.  User adds a new person by entering the command with name, phone number, email, address, insurance type, and appointment dates.
2.  AgentConnect validates the input.
3.  AgentConnect adds the new person with all the details provided.
4.  AgentConnect shows a success message confirming the person has been added.

    Use case ends.


**Extensions**

* 2a. Some fields are invalid (e.g., name, phone, email).
    * 2a1. AgentConnect shows an error message for the invalid fields.
    * 2a2. User corrects the fields and resubmits the command.
    * Use case resumes from step 2.

* 2b. Duplicate person detected (same name + address).
    * 2b1. AgentConnect shows a warning message about the duplicate entry.
    * 2b2. User modifies either the name, address, or both fields, then resubmits the command
    * Use case resumes at step 2 if user decides to proceed


**Use case: Delete a person**

**MSS**

1. User enters the delete command with the Index or Name of the person to be deleted.
2. AgentConnect validates the input.
3. AgentConnect confirms the deletion request by showing a confirmation dialog with the Name of the person to be deleted.
4. AgentConnect deletes the contact and shows a success message.

    Use case ends.

**Extensions**
* 2a. Contact not found (Invalid Index or Name).
    * 2a1. AgentConnect shows an error message indicating Index or Name is invalid.
    * 2a2. User can retry with a valid Index or valid Name.
    * Use case resumes from step 2.
* 2b. Duplicate Person detected (same name)
    * 2b1. AgentConnect updates the list in the GUI with the duplicates and prompts the user to delete by index.
    * 2b2. User selects the index to delete the duplicate person.
    * 2b3. Use case resumes from step 3.

**Use case: Delete a policy**

**MSS**

1. User enters the delete command with the index of the client and the index of the policy to be deleted.
2. AgentConnect validates the input.
3. AgentConnect deletes the policy and shows a success message.

    Use case ends.

**Extensions**
* 2a. Client not found (Invalid Index).
    * 2a1. AgentConnect shows an error message indicating Index is invalid.
    * 2a2. User can retry with a valid Index.
    * Use case resumes from step 2.
* 2b. Policy not found (Invalid policy Index).
    * 2b1. AgentConnect shows an error message indicating policy Index is invalid.
    * 2b2. User can retry with a valid policy Index.
    * Use case resumes from step 2.

**Use case: Undo a Command**

**MSS**

1. User enters the undo command.
2. AgentConnect validates the input.
3. AgentConnect restores the previous state of the address book.
4. AgentConnect shows a success message confirming the undo operation.

    Use case ends.

**Extensions**
* 2a. No commands to undo.
    * 2a1. AgentConnect shows an error message indicating no commands to undo.
    * Use case ends.

**Use case: Redo a Command**

**MSS**

1. User enters the redo command.
2. AgentConnect validates the input.
3. AgentConnect restores the previously undone state of the address book.
4. AgentConnect shows a success message confirming the redo operation.

    Use case ends.

**Extensions**
* 2a. No commands to redo.
    * 2a1. AgentConnect shows an error message indicating no commands to redo.
    * Use case ends.

**Use case: Sort Clients**

**MSS**

1. User sort the clients by entering the sort command with a valid parameter and order.
2. AgentConnect validates the input.
3. AgentConnect retrieves the current client list from storage.
4. AgentConnect sorts the clients based on the specified parameter and order.
5. AgentConnect updates the client list in the GUI to reflect the new sorted order.
6. AgentConnect shows a success message confirming the clients have been sorted.

    Use case ends.

**Extensions**

* 2a. Sorting parameter are missing or invalid (e.g., name, birthday, appointment date, policy payment due date).
    * 2a1. AgentConnect shows an error message for the invalid sorting parameter.
    * 2a2. User corrects the sorting parameter and resubmits the command.
    * Use case resumes from step 2.

* 2b. Sorting order are missing or invalid (e.g., asc, desc).
    * 2a1. AgentConnect shows an error message for the invalid sorting order.
    * 2a2. User corrects the sorting order and resubmits the command.
    * Use case resumes from step 2.

**Use case: Mark a policy payment installment of client as paid**

**MSS**

1. User marks a policy payment installment as paid by entering the paid command with the index of the client and the index of the policy.
2. AgentConnect validates the input.
3. AgentConnect updates the next policy payment due date of the client.
4. AgentConnect shows a success message confirming the policy payment installment has been marked as paid.

    Use case ends.

**Extensions**

* 2a. Client not found (Invalid Index).
    * 2a1. AgentConnect shows an error message indicating Index is invalid.
    * 2a2. User can retry with a valid Index.
    * Use case resumes from step 2.

* 2b. Policy not found (Invalid policy Index).
    * 2b1. AgentConnect shows an error message indicating policy Index is invalid.
    * 2b2. User can retry with a valid policy Index.
    * Use case resumes from step 2.

**Use case: Edit Client Details**

**MSS**

1.  User edits some details for an existing person by entering the edit command with index of the person and new details.
2.  AgentConnect validates the input.
3.  AgentConnect update the corresponding details of the person with the new details provided.
4.  AgentConnect shows a success message confirming the details fo the person have been edited.

**Extensions**

* 2a. Some fields are invalid (e.g., name, phone, email).
    * 2a1. AgentConnect shows an error message for the invalid fields.
    * 2a2. User corrects the fields and resubmits the command.
    * Use case resumes from step 2.

* 2b. Person not found (Invalid index).
    * 2b1. AgentConnect shows a warning message indicating index is invalid.
    * 2b2. User resubmits the command with a valid index.
    * Use case resumes from step 2.

**Use case: Assign policy to client**

**MSS**

1.  User requests to list clients.
2.  User selects a client to assign a policy.
3.  AgentConnect assigns the policy to the client.
4.  AgentConnect shows updated client information.
    Use case ends.

**Extensions**

* 2a. Client does not exist.
    * 2a1. AgentConnect shows an error message for the invalid client.
    * 2a2. AgentConnect prompts the user to either enter a valid client name or add the client to AgentConnect.
    * Use case resumes from step 2.
  
* 2b. Duplicate Policy
    * 2b1. AgentConnect shows an error message "This policy already exist".
    * 2b2. User modifies the policy name and resubmits the command.
    * Use case resumes from step 2.

**Use case: Filter client by policy**

**MSS**

1.  User requests to list clients.
2.  User requests to view clients that hold a certain policy.
3.  AgentConnect retrieves and display all clients that holds the policy.
Use case ends.

**Extensions**

* 2a. Policy does not exist.
    * 2a1. AgentConnect shows an error message for the invalid policy.
    * 2a2. AgentConnect prompts the user to enter a valid policy.
    * Use case resumes from step 2.

**Use case: Retrieve appointment date**

**MSS**

1.  User requests to list clients.
2.  User requests to view a client’s appointment dates.
3.  AgentConnect retrieves and displays the requested appointment date.
Use case ends.

**Extensions**

* 2a. No appointment data available.
    * 2a1. AgentConnect shows a message indicating no appointment date available.
    * Use case ends.

* 2b. Invalid appointment format.
    * 2b1. AgentConnect shows an error message for the invalid appointment format.
    * 2b2. AgentConnect prompts the user to either enter a date or date range to AgentConnect.
    * Use case resumes from step 1.


**Use case: Retrieve client's birthday**

**MSS**

1.  User requests to list clients.
2.  User requests to view a specific client’s birthday.
3.  AgentConnect retrieves and displays the client’s birthday.
Use case ends.

**Extensions**

* 2a. No birthday data available.
    * 2a1. AgentConnect shows a message indicating no birthday date available.
    * Use case ends.

* 2b. Invalid birthday format.
    * 2b1. AgentConnect shows an error message for the invalid birthday input.
    * 2b2. AgentConnect prompts the user to either enter a valid date or date range to AgentConnect.
    * Use case resumes from step 1.

**Use case: Update next payment date**

**MSS**

1.  User requests to list clients.
2.  User requests to view a client's payment date and update the current payment as paid.
3.  AgentConnect retrieves and display the next payment date.
Use case ends.

**Extensions**

* 2a. No next payment available.
    * 2a1. AgentConnect shows a message indicating no next payment data available by displaying "Fully Paid".
    * Use case ends.

* 2b. Invalid client index.
    * 2b1. AgentConnect shows an error message for the invalid client index.
    * 2b2. AgentConnect prompts the user to enter a valid client index to AgentConnect.
    * Use case resumes from step 1.

* 2c. Invalid policy index.
    * 2c1. AgentConnect shows an error message for the invalid policy index.
    * 2c2. AgentConnect prompts the user to enter a valid policy index to AgentConnect.
    * Use case resumes from step 1.

*{More to be added}*

### Non-Functional Requirements

1.  Should be able to hold up to 1000 persons without noticeable lag for typical usage.
2.  A user with above-average typing speed for regular English text should be able to add a new contact (including insurance and appointment details) faster using commands than with the mouse.
3.  The system should provide real-time validation (e.g., when typing the phone number or email) to reduce error rates and ensure correct input formats.
4.  Novice users should be able to complete a typical workflow in under 5 minutes, without external help.
5.  The system codebase should allow for the introduction of new features with less than 10% of existing code modification.
6.  The system should validate all inputs (e.g., phone number, email, insurance details) according to predefined formats (e.g., email must follow a standard email format) to maintain data consistency and integrity.
7.  AgentConnect should detect and handle duplicate entries (based on client name + address) by prompting users to resolve conflicts before adding a new entry.
8.  If the system encounters an unexpected error, it should display a user-friendly error message without exposing technical details and allow the user to retry the action.
9.  The system should respond to common user actions (e.g., adding or deleting a person, sorting clients) within 1 second, ensuring a smooth and responsive experience.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Appointment**: A scheduled meeting or event between the user and a client, managed within AgentConnect, with details like date and purpose.
* **Policy**: An insurance or financial agreement purchased by a client, which can be categorized based on its type (e.g., Life Insurance, Health Insurance, Home Insurance).
* **Client**: A person whose details (e.g., contact information, insurance policies, appointments) are stored and managed within AgentConnect.
* **Duplicate Entry**: When a person with identical details (e.g., same name and address) already exists in the system, the system will flag this as a potential duplicate to avoid redundancy.


--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder
    2. Open up a terminal, and run the following command: java -jar AgentConnect.jar
       - Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.
   2. Re-launch the app by double-clicking the jar file.<br>
      - Expected: The most recent window size and location is retained.

### Adding a Client

1. Adding a client
   1. Prerequisites: None
   2. Test case: `add n/John Doe p/98765432 e/johnd@example.com addr/311, Clementi Ave 2, #02-25 b/1990-10-10 appt/2024-12-12 12:00`
   3. Expected: A new contact has been added into the list.
      - Status Message: "New person added: John Doe; Phone: 98765432; Email: johnd@example.com; Address: 311, Clementi Ave 2, #02-25; Birthday: 1990-10-10; Appointment: 2024-12-12 12:00; Tags:"
   4. The list view should now have the new contact inside.

### Assigning Policies
1. Creating and Assigning policies to client
   1. Prerequisites: There must be at least one client showing in the list with no Policy Name "PolicyOne".
   2. Test Case: `assign 1 pon/PolicyOne pos/2022-12-12 poe/2023-12-12 paydate/2023-11-01 amt/300.00`
      - Expected: "Policy successfully assigned to Alex Yeoh; Phone: 87438807; Email: alexyeoh@example.com; Address: Blk 30 Geylang Street 29, #06-40; Birthday: 1990-05-20; Appointment: 2024-10-15 14:00; Tags: [friends]"

   3. Test Case: `assign 1 pon/PolicyOne pos/2025-12-12 poe/2023-12-12 paydate/2023-11-01 amt/300.00`
      - Expected: "End date cannot be before start date!"
   4. Test Case: `assign 1 pon/PolicyOne pos/2022-12-12 poe/2023-12-12 paydate/2021-11-01 amt/300.00`
      - Expected: "Premium due date cannot be before start date!"
   5. Test Case: `assign 1 pon/PolicyOne pos/2022-12-12 poe/2022-12-12 paydate/2022-01-01 amt/300.00`
      - Expected: "Start date and end date cannot be the same!"

### Marking a policy as paid

1. Marking a policy as paid

    1. Prerequisites: There is at least one contact in the list with a policy.
    2. Test case: `paid 1 po/1`
    3. Expected: The first policy of the first person is marked as paid.
    - The status bar shows the following:
      The policy Life Insurance for `Alex Yeoh` will be fully paid after this payment.
    - The list of persons should now have the updated person with the policy marked as paid inside of it.


### Deleting a Client


1. Deleting a Client while all contacts are being shown

   1. Prerequisites: List all clients using the `list` command. Multiple persons in the list including a `John Doe`.

   2. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message.

   3. Test case: `delete Bernice Yu`<br>
        Expected: Contact with name `Bernice Yu` is deleted from the list. Details of the deleted contact shown in the status message.

   4. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   5. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Deleting a policy

1. Deleting a policy from a person
    1. Prerequisites: There is at least one contact in the list with a policy.
    2. Test case: `delete 1 po/1`
    3. Expected: The first policy of the first person is deleted.
       - The status bar shows the following:
         Deleted Policy 1 from "The first person in the list"
       - The list of persons should now have the updated person with the policy deleted inside of it.

### Editing a person

1. Editing a person with all details
   1. Prerequisites: There is at least one contact in the list.
   2. Test case: `edit 1 p/9999000`
   3. Expected: The phone number of the first person is updated to 9999000.
   - The status bar shows the following:
     Edited Person: Alex Yeoh; Phone: 99990000; Email...
   - The list of persons should now have the updated person inside of it.

### Finding a person

1. Finding a person
   1. Prerequisites: There is at least one contact in the list.
   2. Test case: `find alex`
   3. Expected: The list of persons should now only show persons with the names containing "alex" which is not case-sensitive.
   - The status bar shows the following:
     `n` persons listed! (where `n` is the number of persons found)
   - The list of persons should now only have the found persons inside of it.

### Sorting the list

1. Sorting the list by name
   1. Prerequisites: There is at least more than one contact in the list.
   2. Test case: `sort n/ desc`
   3. Expected: The list of persons should now be sorted by name in descending order.
   - The status bar shows the following:
     Contacts have been sorted by name in desc order.
   - The list of persons should now be sorted by name in descending order.

2. Sorting the list by appointment date
   1. Prerequisites: There is at least more than one contact in the list.
   2. Test case: `sort appt/ asc`
   3. Expected: The list of persons should now be sorted by appointment date in ascending order.
   - The status bar shows the following:
     Contacts have been sorted by appointment date in asc order.
   - The list of persons should now be sorted by appointment date in ascending order.

3. Sorting the list by birthday
   1. Prerequisites: There is at least more than one contact in the list.
   2. Test case: `sort bday/ desc`
   3. Expected: The list of persons should now be sorted by birthday in descending order.
   - The status bar shows the following:
     Contacts have been sorted by birthday in desc order.
   - The list of persons should now be sorted by birthday in descending order.

### Searching

1. Searching for appointments
   1. Prerequisites: There is at least one contact in the list.
   2. Test case: `search b/1990-05-20`
   3. Expected: The list of persons should now only show persons with the birthday on 1990-05-20.
   - The status bar shows the following:
     Listed all clients with birthdays on 1990-05-20
   - The list of persons should now only have the found persons inside of it.


### Saving data

1. Dealing with missing/corrupted data files

   1. Test case: missing data file
      1. Locate the `addressbook.json` file in `../data/` and delete it
      2. Relaunch the app
      3. Expected: The app should create a new `addressbook.json` file with default data


--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned enhancements**
Team size: 5

1. Delete 0 shows that invalid name is entered. We plan on on improving to show the correct error message in the future
2. Implement refresh feature to ensure UI is updated with the latest information after every command is executed.
3. Implement a more robust email validation feature. 
4. Implement a fix to prevent the application from crashing when attempting to close the program while the delete confirmation pop-up window is still present.
5. Implement robust input checks in search functions to ensure invalid date and datetime input will be flagged out as input error by the program.


--------------------------------------------------------------------------------------------------------------------

## **Appendix: Effort**
#### Difficulty Level
The project was moderately difficult due to the complexity of managing multiple entity types (e.g., `Person`, `Policy`, `Appointment`) and their relationships. The undo/redo feature was also challenging to implement due to the need to manage multiple states of the address book.

#### Challenges Faced
- Multiple Entity Management: Handling multiple entities such as clients, policies, and appointments required significant effort in designing and implementing the data models and their interactions.
- Undo/Redo Functionality: Implementing a robust undo/redo mechanism that works seamlessly across various commands was challenging and required careful state management.
- User Interface: Ensuring the UI updates correctly in response to changes in the model, especially with the addition of new features like policy management and appointment scheduling, was a significant challenge.
- Testing: Writing comprehensive tests for the new features and ensuring they integrate well with existing functionality required substantial effort.

#### Effort Required
The project required a considerable amount of effort in the following areas:
- **Design and Architecture**: Significant time was spent on designing the architecture to support multiple entities and their interactions.
- **Implementation**: Implementing the new features, especially the undo/redo functionality and policy management, required detailed coding and debugging.
- **Testing**: Writing unit tests, integration tests, and ensuring high test coverage was time-consuming but essential for maintaining code quality.
- **Documentation**: Updating the user guide, developer guide, and other documentation to reflect the new features and changes was necessary to ensure clarity and usability.

#### Achievements
- **Comprehensive Policy Management**: Successfully implemented a feature-rich policy management system that allows users to assign, delete, and mark policies as paid.
- **Robust Undo/Redo Mechanism**: Developed a reliable undo/redo mechanism that enhances user experience by allowing them to revert and reapply changes easily.
- **Enhanced User Interface**: Improved the UI to handle multiple entities and provide a seamless user experience.
- **Extensive Testing**: Achieved high test coverage and ensured the reliability of the application through rigorous testing.
