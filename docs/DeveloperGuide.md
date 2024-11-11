---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* Icon used: [Google Font](https://fonts.google.com/)
* GitHub Copilot was used in this project for efficiency in writing code.

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

The sequence diagrams below illustrate the interactions within the `Logic` component, taking `execute("delete 1")` and `execute("filter age/>20 <60")` API call as examples.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

![Interactions Inside the Logic Component for the `filter age/>20 <60` Command](images/FilterSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifelines for `DeleteCommandParser` and `FilterCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifelines continue till the end of diagram.
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

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object where duplicated person with both same Name and Phone are not allowed).
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

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

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

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_


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

Tech-savvy sales representatives in the insurance industry who
- Manage 200+ leads and customers
- Work remotely or in fast-paced office environments
- Prioritize speed and efficiency in their daily operations
- Are comfortable with command-line interfaces and prefer keyboard input
- Need quick access to contact information during calls or email communications
- Require efficient follow-up management and interaction tracking

**Value proposition**: Empowers insurance sales reps with lightning-fast, CLI-driven contact management.
It offers instant access to lead details, interaction histories, and follow-up schedules, maximizing call and email productivity for high-volume, rapid-paced sales environments.



### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                     | I want to …​                                                                 | So that I can…​                                                                  |
|----------|-----------------------------|------------------------------------------------------------------------------|----------------------------------------------------------------------------------|
| `* * *`  | new user                    | see usage instructions                                                       | refer to instructions when I forget how to use the App                           |
| `* * *`  | sales rep                   | add a new contact with minimal keystrokes                                    | quickly capture lead information during a call without losing focus              |
| `* * *`  | sales rep                   | delete contacts                                                              | remove outdated leads                                                            |
| `* * *`  | sales rep                   | view an overview of key details with a single command                        | quickly refresh my memory before speaking with the contact                       |
| `* *`    | sales rep                   | search for contacts using partial name matches or aliases                    | quickly find the right person even if I don't remember their full name           |
| `* *`    | sales rep                   | assign and update sales stages for each contact                              | assign and update sales stages for each contact                                  |
| `* *`    | sales rep                   | use intuitive CLI commands with aliases and shortcuts                        | perform frequent operations quickly and efficiently                              |
| `* *`    | sales rep                   | attach quick notes to contact profiles during calls                          | capture important information without interrupting the conversation flow         |
| `* *`    | sales rep                   | view recent contact activity with a single command                           | quickly reference past interactions before making a new outreach                 |
| `* *`    | sales rep                   | tag contacts with custom labels                                              | prioritize my interactions more effectively                                      |
| `* *`    | mobile sales rep            | access and update contact information while on the go                        | stay productive even outside the office                                          |
| `* *`    | sales rep                   | filter my contact list by custom fields                                      | focus on the most relevant contacts for a particular outreach                    |
| `* *`    | sales rep                   | sort contacts by custom fields using text commands                           | organize leads according to specific criteria                                    |
| `* *`    | sales rep                   | save business cards or website links to the client's background              | read up on their information at a later date or to refresh my memory             |
| `* *`    | analytical sales rep        | track how much I've been able to sell to each specific customer              | keep track of the most profitable customers and sell more to them                |
| `* *`    | sales rep                   | keep track of scheduled meetings and calls                                   | resolve schedule clashing                                                        |
| `* *`    | sales rep                   | find free spaces in my schedule                                              | quickly find time to make appointments with clients                              |
| `* *`    | sales rep                   | edit my client's details                                                     | fix errors or update outdated information without deleting and reading           |
| `*`      | sales rep                   | view log calls, emails, and meetings with timestamps and brief notes         | keep track of all interactions with a contact in one place                       |
| `*`      | sales rep                   | import contacts from a CSV file                                              | quickly populate my contact list without manual data entry                       |
| `*`      | sales rep working in a team | collaborate by sharing specific contact details with colleagues              | collaborate by sharing specific contact details with colleagues                  |
| `*`      | organized sales rep         | archive inactive contacts automatically after a set period of no interaction | maintain a clean and focused contact list                                        |
| `*`      | sales rep                   | able to view my notes/reports on contact interactions                        | assess outreach effectiveness                                                    |
| `*`      | sales rep                   | attach documents to contact profiles                                         | easily access relevant files                                                     |
| `*`      | sales rep                   | create custom keyboard shortcuts for frequent actions                        | streamline my workflow                                                           |
| `*`      | sales rep                   | export contact information using a command                                   | easily backup or share my contacts list with other colleagues                    |
| `*`      | analytical sales rep        | analyze statistics of frequency of client responses and profitability data   | find central tendencies of the statistics and choose which customers to sell to  |



*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Delete a person**

**MSS**

1.  User requests to list persons
2.  AddressBook shows a list of persons
3.  User requests to delete a specific person in the list using name or index
4.  AddressBook deletes the person

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. AddressBook shows an error message.

      Use case resumes at step 2.

* 3b. The given name have no exact match.

  * 3b1. AddressBook filters based on given name.

  * 3b2. AddressBook prompts user to input exact name or use index.

    Use case resumes at step 2.

* 3c. The given name have more than 1 exact match.

    * 3c1. AddressBook filters based on given name.

    * 3c2. AddressBook prompts user to use index instead.

      Use case resumes at step 2.

* 3d. The given name have no exact match or partial match.

    * 3d1. AddressBook shows an error message.

      Use case resumes at step 2.

**Use case: Add a Contact**

**MSS**

1.  User requests to add a new contact
2.  AddressBook prompts the user to provide contact details (name, phone number, email, etc.)
3.  User provides the required contact information
4.  AddressBook adds the new contact and confirms successful addition

    Use case ends.

**Extensions**

* 2a. The user provides incomplete contact information

  * 2a1. AddressBook shows an error message and prompts for the missing details

    Use case resumes at step 3

* 3a. The contact already exists

    * 2a1. AddressBook shows an error message indicating a duplicate contact

      Use case ends

**Use case: View an Overview of a Contact**

**MSS**

1. User requests to view details of a specific contact
2. AddressBook displays the contact’s detailed information (name, phone, email, address, etc.)

    Use case ends.

**Extensions**

* 1a. The requested contact does not exist

    * 1a1. AddressBook shows an error message

      Use case ends

* 2a. AddressBook is unable to retrieve the contact details due to a technical error

    * 2a1. AddressBook shows an error message indicating the technical issue

      Use case ends

**Use case: Find a person**

**MSS**

1.  User requests to find persons with a case-insensitive keyword
2.  AddressBook shows a list of all persons that includes the keyword in its `contact name`

    Use case ends.

**Extensions**

* 1a. The given keyword contains illegal characters

    * 1a1. AddressBook shows an error message.

      Use case resumes at step 1.

* 2a. The search result or list is empty.

  Use case ends.

**Use case: Filter persons**

**MSS**

1. User requests to filter persons by multiple criteria.
2. AddressBook shows a list of persons that match the criteria.

    Use case ends.

**Extensions**

* 1a. The given criteria is invalid.

    * 1a1. AddressBook shows an error message.

      Use case resumes at step 1.

* 2a. The filter result or list is empty.

    Use case ends.

**Use case: Command History**

**MSS**

1. User requests to get the previous command in history.
2. AddressBook retrieves the previous command from the command history and enters it in command bar.

   Use case ends.

**Extensions**

* 1a. The command history is empty.

  * 1a1. AddressBook shows an empty string or a message indicating no previous commands.

  Use case ends.

* 2a. User reaches the earliest command in the history.

  * 2a1. AddressBook stops navigating further back and continues displaying the first command.

  Use case ends.

* 2b. User requests to view the next command but is at the latest command.

    * 2b1. AddressBook shows an empty string or a message indicating no further commands.

    Use case ends.


### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. Should be lightweight and use low resources (i.e. minimum 1GB RAM, CPU 2.0 GHz) for low-end systems.
5. The system should be usable by a novice who has never used a CLI.
6. Code should be well-commented and follow clean coding standards to facilitate easy understanding and future scalability.
7. Should be compliant with _data retention policies_, highlighting and aiding in removing information that violates policies.
8. Should be able to handle invalid inputs and errors gracefully without crashing, and providing actionable error reports.
9. Should be able to log and track user actions, for monitoring.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **CLI**: Command-line Interface, where user input is primarily done through the terminal prompt
* **Data Retention Policies**: Includes policies set by user's company, PDPA, and privacy laws
* **Invalid Input**: Non-alphanumeric characters, and non-english characters
* **Logs**: A text file output in the application directory holding information about the app usage

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Adding a person
1. Adding people
    1. Test case: `add n/NAME p/PHONE`<br>
       Expected: Contact with the given NAME and PHONE are added into the contact list.
    3. Test case: `add n/NAME p/PHONE ...` with other fields like email, income, age, notes, tags<br>
       Expected: Contact with the given fields are added into the contact list.
    4. Other incorrect add commands to try: `add`, `add n/NAME`, `...` (where the parameters are invalid, or NAME or PHONE are missing)<br>
       Expected: No person is added. Error details shown in the status message. Status bar remains the same.

### Editing a person
1. Editing people
    1. Prerequisites: List all persons using the `list` command. At least 1 person in the list.
    2. Test case: `edit INDEX n/NAME`<br>
       Expected: Contact at the given INDEX will have their NAME updated, provided it doesn't create a duplicate.
    3. Test case: `edit INDEX x/y...` (where x is any valid parameter and y any valid value satisfying the parameter's constraints)<br>
       Expected: Contact at the given INDEX will have their fields updated, provided it doesn't create a duplicate.
    4. Other incorrect add commands to try: `edit`, `edit x n/NAME`, `...` (where x is an invalid index, or where the parameters are invalid)<br>
       Expected: No person is edited. Error details shown in the status message. Status bar remains the same.

### Deleting a person

1. Deleting a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    1. Test case: `delete 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

    1. Test case: `delete 0`<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.
    1. Test case: `delete NAME` with non-similar names<br>
       Expected: Contact whose name matches exactly is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.
    1. Test case: `delete NAME` with similar names<br>
       Expected: Contact whose name matches similarly are displayed. Status message prompts user to delete by index.
    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

### Finding a person
1. Finding people while all persons are being shown
    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list, with one or more contacts containing the word `Roy`
    2. Test case: `find Roy`<br>
       Expected: Contacts containing `Roy` is displayed.
    3. Test case: `find roy`<br>
       Expected: Similar to previous.
    4. Test case: `find royt`<br>
       Expected: Contacts similar to `royt` is displayed.
    5. Other find commands to try: `find x`, `...` (where x is any sequence of characters)<br>
       Expected: Similar to previous.
    5. Test case: `find`<br>
       Expected: No person is found. Error details shown in the status message. Status bar remains the same.

### Filtering a person
1. Filtering people while all persons are being shown
    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.
    2. Test case: `filter p/+65`<br>
       Expected: Contacts containing in their phone `+65` is displayed.
    3. Test case: `filter p/+++`<br>
       Expected: No person is found. Error details shown in the status message. Status bar remains the same.
    4. Other incorrect filter commands to try: `filter p/x`, `...` (where x is any invalid phone number)<br>
       Expected: Similar to previous.
    5. Other incorrect filter commands to try: `filter y/x`, `...` (where x is any invalid field, and y is any valid field format)<br>
       Expected: Similar to previous.

### Listing people
1. Listing people while all persons are being shown
    1. Prerequisites: Multiple persons in the list.
    2. Test case: `list`<br>
       Expected: Contacts displayed sorted by Name in ascending order.
    3. Test case: `list s/name r/`<br>
       Expected: Contacts displayed sorted by Name in descending order.
    4. Test case: `list s/x` (where x is any valid sorting field of name, email, age, income)<br>
       Expected: Contacts displayed sorted by x in ascending order.
    5. Other incorrect list commands to try: `list s/x`, `...` (where x is any invalid sorting field)<br>
       Expected: No person is found. Error details shown in the status message. Status bar remains the same.

### Adding Notes
1. Adding notes to people while all persons are being shown
    1. Prerequisites: List all persons using the `list` command. At least 1 person in the list.
    2. Test case: `notes add/NAME nt/NOTES`<br>
       Expected: Contact matching NAME has notes added successfully as NOTES.
    3. Test case: `notes view/NAME`<br>
       Expected: Displays the notes saved on the contact matching NAME.
    4. Test case: `notes edit/NAME`<br>
       Expected: Shows a pop-up window to edit notes for the contact matching NAME and saves upon user confirmation.
    5. Test case: `notes del/NAME`<br>
       Expected: Removes the notes on the contact matching NAME.
    6. Test case: `notes x/y` (where x is any valid PARAMETER, y any valid INDEX)<br>
       Expected: Similar to above for NAME, but works with INDEX too.
    7. Other incorrect filter commands to try: `notes x/y`, `...` (where x is any invalid PARAMETER, and y is any invalid INDEX or NAME)<br>
       Expected: No person is found. Error details shown in the status message. Status bar remains the same.

### Saving data

1. Data is stored automatically

### Planned Enhancements

Team Size: 4

1. **Improve Help Window content**: The current Help Window content is quite wordy, and some command formats are cut off halfway. We will fix this by allowing resizing of the help window, so users are better able to read the content.
2. **Improve Help Window behavior**: The Help Window remains minimized when the `help` command is run again. We will fix this by automatically restoring the minimized Help Window when the `help` command is run, or when the `Help` menu or `F1` keyboard shortcut is used.
3. **Add horizontal scroll bar**: The fields UI is cut off for any contacts with lengthy fields. We will fix this issue by adding a horizontal scroll bar to cells, so everything can be seen even for contacts with lengthy fields.
4. **Allow special characters in tag names**: Tag names currently only accept alphanumeric characters. We will fix this by modifying tag name validation to accept special characters like hyphens.
5. **Improve phone number error message**: The error message currently only states that "+" is allowed, but does not mention that it should be at the beginning. We will fix this by updating the error message to clearly indicate that the "+" symbol should be at the beginning of the phone number.
6. **Enable filtering for empty fields**: The application cannot filter for empty fields. We will fix this by adding functionality to filter for empty fields.
7. **Improve edit command parameter handling**: The `edit` command processes multiple INCOME or AGE parameters incorrectly, using only the last value. We will fix this by ensuring the `edit` command does not allow multiple INCOME or AGE parameters.
8. **Normalize contact name case sensitivity**: Contact names are case-sensitive, potentially allowing duplicate entries with different capitalization. We will fix this by implementing case-insensitive contact name handling to prevent duplicate entries with different capitalization.  
