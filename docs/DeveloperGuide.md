---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# Talentcy Developer Guide

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
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components).


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

### Find feature
The Find feature follows the sequence diagram here:
<puml src="diagrams/FindSequenceDiagram.puml" width="550" />

### Mass Reject feature
The Mass Reject feature follows the sequence diagram here:
<puml src="diagrams/MassRejectSequenceDiagram.puml" width="550" />

### Delete feature
Deleting by index follows this sequence diagram:
<puml src="diagrams/DeleteSequenceDiagram.puml" width="550" />

### Sort feature
The Sort feature follows the sequence diagram here:
<puml src="diagrams/SortSequenceDiagram.puml" width="550" />

### Statistics feature
The Statistics feature follows the sequence diagram here:
<puml src="diagrams/StatisticsSequenceDiagram.puml" width="550" />

Like `ListCommand`, it does not require the use of its own parser, as it only calls upon the FilteredList of the
address book and processes it within the statistics feature.
It uses a helper class called JobCodeStatistics that stores the number of applicants in each interview stage for that
job code.

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


--------------------------------------------------------------------------------------------------------------------
## **Planned Enhancements**
Team size: 4

1. **Enhanced Find Command with Multiple Keywords**  
   The current `find` command supports only one keyword at a time for each field. We plan to extend this functionality to accept multiple keywords (e.g., `find n/Hans or n/Hera`), making it easier for users to locate multiple contacts in a single search.

2. **Extended Tagging Functionality**  
   Presently, only a limited set of tags is supported. We plan to expand this feature to support additional types of tags, enabling more flexible categorization and better organization of contacts.

3. **Resizable Feedback Box**  
   Users are currently unable to adjust the feedback box size. To improve usability, we plan to make the feedback box resizable, allowing users to expand or contract it based on their preferences and screen size.

4. **Restrictive Phone Number Validation**  
   The existing phone number validation allows entries as short as three digits. We plan to tighten validation criteria to ensure that phone numbers meet a more realistic length requirement, improving data accuracy and reliability.

5. **Appendable Remarks**  
   Currently, remarks can only be overwritten, not appended to. We plan to enhance the remark feature to support appending, so users can add additional notes without replacing existing remarks.

6. **Advanced Statistics Filtering Options**  
   To support more detailed insights, we plan to extend statistics functionality by offering additional predicate filters. This enhancement will allow users to specify criteria (e.g., `Stats [j/] [t/]`) for more targeted statistical analysis.

7. **Mass Actions Command**  
   At present, the `massReject` command allows only batch rejections. We plan to rename this command to `mass` and introduce a predicate/parameter option to enable mass deletions, edits, or rejections. This will streamline bulk actions, making management of large applicant pools more efficient.

8. **Unique Listing with Job Predicate**  
   The `list` command currently lacks options for filtering by unique attributes. We plan to enhance it by adding the `list [j/]` parameter to allow listing of unique items, such as distinct jobs or tags, providing users with a clearer overview.


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

* employees of a company's talent recruitment departments who need to manage contacts of job applicants.
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: This app streamlines the process of managing talent contact information by centralizing essential
contact details, making it easier to organise, search, and update information on potential candidates.
Its search and filtering capabilities help recruiters quickly find profiles based on specific criteria,
improving efficiency and reducing time spent on administrative tasks.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                              | I want to …​                                                                 | So that I can…​                                                                                   |
|----------|--------------------------------------|------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------|
| `* * *`  | user                                 | see the list of all contacts                                                 | see the contact details of every applicants                                                       |
| `* * *`  | user                                 | add a new contact                                                            | keep track of applicant's contact details                                                         |
| `* * *`  | user                                 | delete a contact                                                             | remove contact of applicants who is no longer in the recruitment process                          |
| `* * *`  | user                                 | find a contact by his/her name                                               | see particular applicant's contact detail without having to go through the entire list            |
| `* * *`  | user                                 | tag applicants based on their stage in the recruitment process               | track their progress and determine the next steps in the recruitment process.                     |
| `* *`    | efficient user                       | filter contacts which fulfil several criteria                                | quickly access the list of contacts that matches my needs                                         |
| `* *`    | user                                 | sort the contacts based on certain criteria                                  | quickly access the contacts that I need to prioritize                                             |
| `* *`    | user with a high volume of applicants | batch update applicants stage in one action                                  | manage and progress multiple contacts efficiently without repetitive tasks                        |
| `* *`    | user                                 | change any detail of a contact                                               | update the contact details when needed                                                            |
| `* *`    | newbie user                          | get help on how to start using the program                                   | familiarize myself with how to use the program                                                    |
| `* *`    | senior user                          | know proportion of contacts who pass/fail in different recruitment stages    | get insightful data to help me adjust how much more/less to accept in the next recruitment period |
| `* *`    | user                                 | filter contacts with fuzzy match                                             | find relevant contacts easily without typing the exact keywords                                   |
| `*`      | user                                 | highlight contact of applicant with criminal record or conflicts of interest | easily locate applicants who needs to be investigated further                                     |
| `*`      | organized user                       | view visual timeline of each contact's recruitment stage                     | easily keep track of the position of each contact in the recruitment process                      |
| `*`      | user | store applicant's resume                                                     | easily access and refer to their resume                                                           |


### Use Cases

(For all use cases below, the **System** is the `System` and the **Actor** is the `User`, unless specified otherwise.)

---

#### **UC001: Add a contact**

**MSS**

1. User requests to add a new contact by providing a name, phone number, email, job code, tag, and optional remark.
2. System validates the provided input.
3.  System checks if the contact already exists by verifying the uniqueness of the phone number and email. 
4. System adds the contact to the system and displays a confirmation message.

    Use case ends.

**Extensions**

* 2a. The provided input is invalid (e.g., invalid phone number format, name too long).

    * 2a1. System shows an error message and prompts the user to correct the input.

      Use case ends.

* 3a. A duplicate contact is found (same phone number or email).

    * 3a1. System displays a duplicate contact error message.

      Use case ends.

---

#### **UC002: Delete a contact**

**MSS**

1. User requests to delete a specific contact by providing a valid identifier, which can be one of the following:  
    Index (positive integer), Name (n/NAME), Email (e/EMAIL), Phone (p/PHONE).
2. System validates the provided input:
Checks if the identifier matches the valid format (e.g., positive integer, name, email, or phone).

3. System checks if the contact exists (using the provided identifier, which could be a name, email, or phone number).

4. System deletes the contact and displays a confirmation message.

    Use case ends.

**Extensions**

- 2a. The provided input is invalid (e.g., invalid phone number format, name too long, invalid command format).

    - 2a1. System shows an error message indicating invalid command format or invalid parameter and prompts the user to correct the input.

        Use case ends.

- 3a. No contact matches the provided identifier.

    - 3a1. System shows an error message stating that no contact matches the provided identifier and prompts the user to correct the input.

        Use case ends.

- 3b. Multiple contacts with the same name are found, and the user has not provided a phone number or email.

    - 3b1. System asks the user to provide other details (phone number or email) to specify the contact to delete.  
        Use case ends.

---

#### **UC003: List all contacts**

**MSS**

1. User requests to list all contacts.
2. System retrieves all stored contacts from the system.
3. System displays the list of contacts.

   Use case ends.

---

#### **UC004: Find contacts**

**MSS**

1. User requests to find contacts by name, phone number, email, job code, tag, remark or a combination of some of them.
2. System validates the find criteria.
3. System retrieves and displays the contacts matching the criteria.

   Use case ends.

**Extensions**

* 2a. The find criteria are invalid (e.g., tag not recognized).

    * 2a1. System shows an error message.

      Use case ends.

---

#### **UC005: Viewing Help**

**MSS**

1. User requests help to use the system.
2. System displays a link to website containing more information (user guide).
3. User goes to the provided link to see more information.

   Use case ends.

---

#### **UC006: Showing Applicant Statistics**

**MSS**

1. User requests to view applicant statistics.
2. System retrieves statistics data (e.g., counts of applicants by job code and tags).
3. System displays the statistics in a summary view.

   Use case ends.

---

#### **UC007: Bulk Rejecting Contacts**

**MSS**

1. User requests to bulk reject contacts by providing a job code, tag, or both.
2. System validates the input and checks if any contacts match the criteria.
3. System updates the matching contacts' tags to `r` (rejected) and displays a confirmation message.

   Use case ends.

**Extensions**

* 2a. The criteria are invalid (e.g., tag not recognized).

    * 2a1. System shows an error message.

      Use case ends.

---

#### **UC008: Sorting Contacts**

**MSS**

1. User requests to sort contacts by specifying zero or more sorting criteria (e.g., name, phone, email, job code, tag).
2. System validates the sorting criteria.
3. System sorts the contacts based on the specified criteria in the given order.
4. System displays the sorted list of contacts.

   Use case ends.

**Extensions**

- 2a. The sorting criteria are invalid (e.g., unsupported field for sorting).

    - 2a1. System shows an error message indicating invalid sorting criteria.

      Use case ends.

---

#### **UC009: Clearing All Contacts**

**MSS**

1. User requests to clear all contacts from the address book.
2. System deletes all contacts and displays a confirmation message.

   Use case ends.

---

#### **UC010: Exiting the Program**

**MSS**

1. User requests to exit the program.
2. System terminates and closes the application.

   Use case ends.

---

#### **UC011: Edit a contact**

**MSS**

1. User requests to edit a specific contact by providing an index and one or more fields to update (name, phone number, email, job code, tag).
2. System validates the provided input.
3. System checks if the contact exists at the specified index.
4. System checks if the edited details causes duplicate.
5. System updates the contact’s details with the provided information and displays a confirmation message.

   Use case ends.

**Extensions**

- 2a. The provided input is invalid (e.g. invalid phone number format).

    - 2a1. System shows an error message indicating invalid input and prompts the user to correct it.

      Use case ends.

- 3a. No contact is found at the specified index.

    - 3a1. System shows an error message indicating that no contact exists at the specified index.

      Use case ends.

- 4a. The edited details causes duplicate contacts.

    - 4a1. System shows an error message indicating that the contact already exists.

      Use case ends.


### Non-Functional Requirements

1. The system should work seamlessly across macOS, Windows, and Linux operating systems.
2. The system should support both 32-bit and 64-bit environments.
3. Should work on any mainstream OS as long as it has Java `17` or above installed.
4. The system should execute commands (such as adding, deleting, or listing contacts) within 1 second under normal loads (e.g., up to 1,000 contacts).
5. Should be able to hold up to 1,000 persons without noticeable sluggishness in performance for typical usage.
6. A user with above-average typing speed for regular English text (i.e., not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
7. The system must be user-friendly and usable by individuals with no prior experience with contact management systems. 
8. Volatility: Transaction data should be stored persistently and remain available for a minimum of 10 years. 
9. Complete user documentation, including installation and setup instructions, must be provided. 
10. The system should be designed to allow for the addition of new modules without requiring a full redesign. 
11. The system should gracefully handle incorrect or incomplete inputs by providing meaningful error messages without crashing.


### Glossary

* **Applicant**: An individual who has submitted job application to the company. 
* **Job Code** : A code that represents the company's specific job role. 
* **Tag** : Interview stages that applicants will go through for a job before they could get hired. The following are the default tags and abbreviations in the address book:

| Tag | Interview Stage                 | Definition                                                                  |
|-----|---------------------------------|-----------------------------------------------------------------------------|
| N   | New                             | New applicant                                                               |
| TP  | Technical Interview in Progress | Technical interview is in the process of being scheduled for the applicant  |
| TC | Technical Interview Confirmed | Technical interview has been schedule for the applicant                     |                                           
| BP | Behavioral Interview in Progress | Behavioral interview is in the process of being scheduled for the applicant |
| BC | Behavioral Interview Confirmed | Behavioral interview has been scheduled for the applicant                   |
| A| Accepted| Applicant has been accepted by the company                                  |
| R | Rejected | Applicant has been rejected by the company                                  |

* **Person**: A single entry in Talency address book that contains information about a particular applicant such as name, phone number, email address, and any other relevant details.
* **Command**: A specific text-based instruction given by the user to the system to perform a particular action (e.g., add NAME p/PHONE e/EMAIL j/JOB_CODE t/TAG is a command to add a contact to the address book).

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

   1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar Talentcy.jar` command to run the application. <br>
       Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by opening command terminal in the folder containing the jar file, then use `java -jar Talentcy.jar`.<br>
       Expected: The most recent window size and location is retained.


### Adding a Person

1. Adding a Person
    1. Test case: `add n/John Doe p/98765432 e/johnd@example.com j/SWE123 t/N r/Good skills`  
       Expected: Assuming no duplicates, a new contact named John Doe is added to the list. The status message displays details of the added contact.
    2. Test case: `add n/ p/ e/ j/ t/`  
       Expected: No person is added. An error message displays in the status, indicating missing fields.
    3. Other incorrect add commands to try: `add n/John`, `add p/98765432`, `add e/notemail`, `add t/Went to NUS`  
       Expected: Error messages display for each incomplete command, and no contact is added.

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. At least one person in the list.

   1. Test case: `delete 1`<br>
      Expected: First person is deleted from the list. Details of the deleted person is shown in the status message.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x` (where x is larger than the list size)<br>
      Expected: Error messages displayed, and no person is deleted.

2. Deleting a person based on its attribute

    1. Prerequisites: At least one person stored in the app.

   2. Test case: `delete n/John Doe`
        Expected: Assuming there are only one person with the name `John Doe` in the list, that person will be deleted from the list. Details of the deleted person is shown in status message.

   3. Test case: `delete p/80981234`
        Expected: Assuming a person with phone number 80981234 exists in the list, that person will be deleted. Details of the deleted person is shown in status message.
   4. Test case: `delete e/pu09com`
        Expected: No person is deleted. Error message is shown in the status bar.
   5. Other incorrect delete commands to try: `delete John Doe`, `delete p/x` (where a person with phone number x doesn't exist in the list).
        Expected: No person is deleted. Error message is shown in the status bar.

### Finding Persons by Criteria

1. Finding Persons with Specific Criteria
    1. Prerequisites: Ensure there are persons with various entry of fields.
    2. Test case:`find n/John`  
       Expected: Displays a list of person(s) with "John" in their names, case-insensitive. Empty list will be displayed if a person with that specific criteria(s) is not found.
    3. Test case: `find t/TP n/Jane`  
       Expected: Shows a list of person(s) with "Jane" in their name and tagged as `Technical Interview in Progress`. Empty list will be displayed if a person with that specific criteria(s) is not found. 
    4. Incorrect add commands to try: `find`, `find John`, `find p/notphone`  
       Expected: Error message is shown in the status bar. List of persons shown will remain the same.

### Sorting Persons by Fields

1. Sorting by Fields
    1. Prerequisites: Ensure there are multiple persons with various entry of fields.
    2. Test case: `sort n/`  
       Expected: Sorts the persons alphabetically by name.
    3. Test case: `sort t/ j/`  
       Expected: Sorts first by tag, then by job code within each tag group.
    4. Test case: `sort j/ n/`  
       Expected: Sorts first by job code, then by name within each job code group.

#### Bulk Rejecting by Criteria

1. Bulk Rejecting Contacts
    1. Prerequisites: Ensure there are persons with various job codes and tags, including the `Accepted` tag.
    2. Test case: `massreject j/SWE2024 t/TP`  
       Expected: Marks all persons with job code `SWE2024` and tag `TP` (if any) as rejected, displaying a confirmation status.
    3. Test case: `massreject j/AWE2023`  
       Expected: Marks all persons with job code `AWE2023` as rejected, except for the person(s) that has an `Accepted` tag.`.

---

#### Viewing Applicant Statistics

1. Viewing Statistics
    1. Prerequisites: Ensure there are multiple persons with diverse tags and job codes.
    2. Test case: `stats`  
       Expected: Displays the total number of applicants, the percentage of applicants in each interview stage, and the breakdown of applicants by job code.


#### Saving Data with Missing/Corrupted Files

1. Handling Missing or Corrupted Data Files**
   1. Prerequisites: Ensure `talentcy.json` has multiple entries, and back up the file before testing.
    2. Test Case: Invalid Data Format
       Simulation: Edit one field from a person entry in the JSON file (e.g. change the phone field to non-numeric), then launch the app.
       Expected: The app discards **all** data and starts with an empty list.

   3. Test Case: Missing `talentcy.json` File
      Simulation: Delete `talentcy.json`, then launch the app.
      Expected: The app creates a new `talentcy.json`, the app starts on clean slate (i.e. with sample data only).

      

