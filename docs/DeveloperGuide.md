---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---


# Medicontact


<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).

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
* provides confirmation prompts to some classes in `Logic` component for user approval.

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
1. The command can communicate with the `Ui` to show an alert dialog and obtain confirmation from user.
1. When user confirms the action (selects "OK"), the command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

The class diagram below demonstrates the subclasses of the CommandParser class:

<puml src="diagrams/ParserSubClasses.puml" width="600"/>

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="600" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)




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

* Administrative staff at a GP clinic responsible for patient management.
* should be apt with technology and trained to be familiar with the software as their primary job

**Value proposition**:
Patient / Contact management systems might be outdated in GP clinics, introducing MediContact might improve user-friendliness. 
MediContact also centralizes the details of patients at the clinic with a command line interface to enable efficient contact between patient and clinic. 
Furthermore, it can provide easy categorisation and filtering of patients.



### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​         | I want to …​                                                  | So that I can…​                                               |
| -------- | -------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| `* * *`  | New user       | add a new contact with multiple phone numbers (e.g. home, mobile, email address) | manage patient contact information and have multiple options for reaching them in an emergency. |
| `* * *`  | User           | delete a contact                                             | remove outdated or incorrect contact information             |
| `* * *`  | User           | list all contacts in one dashboard                           | easily view all the contacts that I have added.              |
| `* * *`  | User           | find the patient contact by a keyword                        | I can search the patients’ contact instantly.                |
| `* *`    | User           | edit an existing patient contact                             | I can update their details when necessary.                   |
| `* *`    | User           | click on the patient contact in a dashboard                  | I can view more details and retrieve information faster during busy hours. |
| `* *`    | User           | see all the texts and UI clearly                             | I don’t have to squint my eyes                               |
| `* *`    | User           | import contacts from a file                                  | I can quickly fill in the address book with existing contact information. |
| `* *`    | User           | export contacts to a file securely                           | I can share them with other authorized personnel or have a backup. |
| `* *`    | Silly user     | receive confirmation before deleting a contact               | I don’t accidentally delete important information            |
| `* *`    | User           | sort the patients according to appointment dates             | I can easily know which are the latest upcoming appointments |
| `* *`    | User           | automatically see my frequently or recently accessed contacts in the dashboard | I can find them more easily.                                 |
| `* *`    | User           | add a contact to my favorite list                            | I can quickly access important contacts                      |
| `* *`    | User           | see alerts of duplicate contacts in the app                  | I can keep the contact list clean and avoid redundancy and confusion |
| `* *`    | User           | tag patients with recurring appointments                     | I know those who need regular follow ups                     |
| `* *`    | Expert User    | categorize patients based on medical conditions, assigned doctor(s) and/or treatment plan | I can prioritize urgent conditions and streamline patient management. |
| `* *`    | User           | add notes to a contact                                       | I can remember important information about that person.      |
| `*`      | User           | filter patients based on appointment dates                   | I can contact and remind them.                               |
| `*`      | Potential user | see the app populated with some sample commands              | I can easily learn how to use the app.                       |
| `*`      | User           | use the app to work offline                                  | I can use it even when there is no internet connection.      |
| `*`      | User           | save addresses                                               | I have their location readily available.                     |
| `*`      | User           | filter patients based on age groups                          | I can prioritize certain medical procedures                  |
| `*`      | User           | export details of patients filtered by different criterias   | I can share them easily                                      |
| `*`      | User           | view when each contact was added or last updated             | I know how up-to-date the information is                     |
| `*`      | User           | set reminders to follow-up with certain contacts             | I can ensure good and punctual communication with patients.  |
| `*`      | User           | print patient contact information directly from my address book | I can have a physical record if needed.                      |
| `*`      | User           | archive inactive patient contacts rather than delete them    | I can keep their records without cluttering my contact list  |
| `*`      | Expert user    | organise the patients into different albums                  | I can search for them in an organized way based on certain categories. |
| `*`      | User           | manually log contact history with patients                   | I have a record of all communications and their corresponding dates |
| `*`      | User           | receive alerts when a patient’s contact information hasn’t been updated in 5 years | I can reach out to check if it is updated                    |
| `*`      | User           | set privacy preferences for each patient                     | their personal data is protected and only able to be accessed by authorized staff |

### Use cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Add a contact**

**MSS**

1.  User requests to add contact
2.  User inputs the contact details
3.  User requests to add the contact
4.  Medicontact adds the contact

    Use case ends.

**Extensions**

* 2a. Necessary field is missing

    * 2a1. Medicontact shows an error message indicating which field is missing
    * 2a2. Use case ends.

* 2b. Wrong format in input

    * 2b1.Medicontact shows an error message specifying the incorrect format.
    * 2b2. Use case ends.

**Use case: Delete a person**

**MSS**

1.  User requests to list persons
2.  AddressBook shows a list of persons
3.  User requests to delete a specific person in the list
4.  AddressBook deletes the person

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. AddressBook shows an error message.

      Use case resumes at step 2.

**Use case: Find a person**

**MSS**

1. User requests to find contact
2. User inputs the find command with contact details
3. Medicontact shows a list of persons matching details
   Use case ends.

**Extensions**

- 2a. Necessary field is missing
  - 2a1. Medicontact shows an error message indicating which field is missing
  - 2a2. Use case ends.
- 2b. Wrong format in input
  - 2b1.Medicontact shows an error message specifying the incorrect format.
  - 2b2. Use case ends.
- 2c. No match in users
  - 2b1.Medicontact shows an error message specifying that there are no users that match the query.
  - 2b2. Use case ends.

**Use case: List contacts**

**MSS**

1. User requests to list contacts
2. User inputs the list command
3. Medicontact shows a list of contacts
   Use case ends.

### Non-Functional Requirements

1. Technical: 
   - Should work on any *mainstream OS* as long as it has Java 17 or above installed.
   - Should work on both 32-bit and 64-bit environments.
2. Performance:
   - Should be able to hold up to 1000 contacts without a noticeable sluggishness in performance for typical usage.
   - The response to any use action should become visible within 3 seconds.
   - Should not crash when the input is too long.
3. Quality: 
   - Should be easy to learn for a novice who has never used CLI before.
   - The product is offered as a free downloadable application.

Note to project: Security measures like encryption will not be implemented in this project 

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Encryption**: The process of converting data into a code to prevent unauthorized access, typically using algorithms and keys.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder.

    1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. Ensure data is saved

   1. Launch and close the app without carrying out any commands.
    
   1. Re-launch the app.<br>
      Expected: List of contacts are the same as before.

### Adding a person
1. Adding a person

   1. Prerequisites: List all persons using `list` command. Multiple persons in the list.

   2. Test case: `add n/Bryan Lim p/98765432 e/bryan@example.com a/5 Hilly Road b/23 s/Male`<br>
      Expected: Bryan Lim is added to the list.

   3. Test case: `add n/Bryan_Lim p/98765432 e/bryan@example.com a/5 Hilly Road b/23 s/Male`<br>
      Expected: No contact is added to the list, error message regarding name should be shown.

   4. Test case: `add n/Bryan Lim p/000 e/bryan@example.com a/5 Hilly Road b/23 s/Male`<br>
      Expected: No contact is added to the list, error message regarding phone number should be shown.
   
   5. Test case: `add n/Bryan Lim p/98765432 e/bryan a/5 Hilly Road b/23 s/Male`<br>
      Expected: No contact is added to the list, error message regarding email should be shown.

   6. Test case: `add n/Bryan Lim p/98765432 e/bryan@example.com a/ b/23 s/Male`<br>
      Expected: No contact is added to the list, error message regarding address should be shown.

   7. Test case: `add n/Bryan Lim p/98765432 e/bryan@example.com a/5 Hilly Road b/twelve s/Male`<br>
      Expected: No contact is added to the list, error message regarding age should be shown.

   8. Test case: `add n/Bryan Lim p/98765432 e/bryan@example.com a/5 Hilly Road b/23 s/`<br>
      Expected: No contact is added to the list, error message regarding gender should be shown.

2. Adding a duplicate person
    1. Prerequisites: List all persons using `list` command. Multiple persons in the list.<br>
       Person with name `Bryan Lim` should already be in list.

    2. Test case: `add n/Bryan Lim p/00000000 e/different_bryan@example.com a/5 Hilly Road b/23 s/Male`<br>
       Expected: No contact is added to the list, error message regarding person already existing should be shown.

    3. Test case: `add n/Bryan Sim p/00000000 e/different_bryan@example.com a/5 Hilly Road b/23 s/Male`<br>
       Expected: Bryan Lim is added to the list.

### Clearing the address book
1. Clearing the address book

    1. Test case: `clear`<br>
       Expected: Alert pop up confirming whether you want to clear the address book.
    
    2. Test case: Click on `OKAY`<br>
       Expected: Address book is cleared
 
    3. Test case: Click on `Cancel`<br>
       Expected: Address book is unchanged

### Deleting a person

1. Deleting a person via index while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: Confirmation dialog is triggered. <br><br>If confirmed, first contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.
      <br><br>If cancelled, no person is deleted, message reflecting delete action being cancelled is shown.
   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. Deleting a person via name while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.<br>
       John Doe is an existing contact

    1. Test case: `delete John Doe`<br>
       Expected: Confirmation dialog is triggered. <br><br>If confirmed, John Doe is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.
       <br><br>If cancelled, no person is deleted, message reflecting delete action being cancelled is shown.

    1. Test case: `delete Jane Doe`<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

### Editing a person

1. Editing a person's fields

    1. Prerequisites: Person named `John Doe` must exist.

    2. Test case: `edit John Doe n/Jane Doe`<br>
       Expected: Contact with name `John Doe` is changed to `Jane Doe`

    3. Other test cases to try: `edit John Doe a/6 Sunny Road`, `edit John Doe b/35`, etc<br>
       Expected: Similar to previous results with relevant fields changed

2. Editing a non-existent person's field

    1. Prerequisites: Person named `John Doe` must not exist.

    2. Test case: `edit John Doe n/Jane Doe`<br>
       Expected: Error message reflecting provided name is invalid
 
    3. Other test cases to try: `edit John Doe a/6 Sunny Road`, `edit John Doe b/35`, etc<br>
       Expected: Similar to previous results.

3. Editing a person with no fields

    1. Test case: `edit John Doe`<br>
       Expected: Error message reflecting at least one field must be provided.

    2. Test case: `edit Non Existent Person`<br>
       Expected: Similar to previous results.

4. Clearing a persons appointments and tags

    1. Prerequisites: Person named `John Doe` must exist.

    2. Test case: `edit John Doe t/`<br>
       Expected: Contact with name `John Doe` has all existing tags cleared

    3. Test case: `edit John Doe ap/`<br>
       Expected: Contact with name `John Doe` has all existing appointments cleared

### Exporting the address book

1. Exporting Data
    1. Test case: `export`<br>
       Expected: A .json file under `data` folder should be created in the folder where the MediContact.jar file is stored

### Filter the contacts list

1. Filtering by Age range

    1. Prerequisites: Address book should have a number of contacts in order to make testing meaningful

    2. Test case: `filter b/20 - 30`<br>Expected: All contacts with ages 20 - 30 inclusive should be displayed

    3. Test case: `filter b/30 - 20`<br>
       Expected: Error reflecting how upper bound should be at least equal or greater than lower bound is displayed

    4. Test case: `filter b/200-200` (Assuming no contact has age 200)<br>
       Expected: Message will display no contact fits the criteria, list of people remain the same

2. Filtering by Appointment range

    1. Test cases to try: `filter ap/01/01/2024 - 01/01/2025`, `filter ap/01/01/2025 - 01/01/2024`, `filter ap/01/01/2024 - 01/01/2024`<br>
       Expected: Similar results to filtering by age

3. Filtering by Tags

    1. Prerequisites: Address book should have a number of contacts in order to make testing meaningful.

    2. Test case: `filter t/patients`<br>Expected: All contacts with tag `patients` should be displayed.

    3. Test case: `filter t/`<br>
       Expected: Error reflecting how input should be alphanumerical.

    4. Test case: `filter b/son` (Assuming no contact has tag `son`)<br>
       Expected: Message will display no contact fits the criteria, list of people remain the same.

4. Filtering using multiple criteria

    1. Test cases to try: `filter b/20 - 30 t/patient`, `filter ap/01/01/2025 - 01/01/2024 t/diabetes`
       Expected: Only contacts that satisfy all criteria are shown. If no matches, list is not changed and message will display no contact fits the criteria.

### Finding a contact

1. Finding someone that exists

    1. Prerequisites: List should contain contacts with the names and phone numbers that you want to find.

    2. Test case: `find bryan`
       <br>Expected: All contacts with `bryan` in their name is displayed.

    3. Test case: `find 987654321`
       <br>Expected: All contacts with phone number `987654321` is displayed.

    4. Test case: `find bry 9876`
       <br>Expected: All contacts with `bry` in their name or `9876` in their phone number is displayed.

2. Finding someone that does not exist

    1. Prerequisites: List should not contain contacts with the names and phone numbers that you want to find.

    2. Test case to try: `find somebody`, `find somebody 99999999`
       <br>Expected: Message displays that no one matches the criteria and list of contacts remain the same.

### Importing an address book

### Listing contacts

1. List all contacts

    1. Test case: `list`<br>
       Expected: All contacts are listed

2. List all starred contacts (Contacts have been Starred)

    1. Test case: `list *`<br>
       Expected: All Starred contacts are listed

3. List all starred contacts (No contacts have been Starred)

    1. Test case: `list *`<br>
       Expected: Message reflects that no contacts have been starred

### Editing a contact's notes

1. Adding new notes to an existing person

    1. Test cases to try: `note Bryan ap/01/01/2025 r/Allergic to Ibuprofen`, `note John m/10mg Panadol`<br>
       Expected: Notes are added to person (Behaviour is very similar to Edit Command's appointments and tags)

2. Removing note's fields of an existing person

    1. Test cases to try: `note Bryan ap/ r/`, `note John m/`<br>
       Expected: Respective Note fields are removed (Behaviour is very similar to Edit Command's appointments and tags)

### Sorting the list

1. Sorting list by appointment dates

    1. Test case: `sort`<br>
       Expected: List is sorted by appointment dates. Contacts with no appointments are at the end, sorted alphabetically.

### Starring a contact

1. Starring an unstarred person

    1. Prerequisite: Address book is populated with contacts.

    2. Test case: `star 1`<br>
       Expected: Contact at index 1 is starred

    3. Test case: `star John`<br>
       Expected: Contact with exact name `John` is starred
   
    4. Test case: `star 0`, `star -1`, `star x` (where x is larger than the list size)<br>
       Expected: Error message reflecting index provided is invalid

    5. Test case: `star somebody`<br>
       Expected: Error message reflecting name provided is invalid

2. Starring a starred person
    1. Prerequisite: First contact is starred

    2. Test case: `star 1`, `star x` (where x is the name of the first contact)<br>
       Expected: Message reflects that contact has already been starred

### Unstarring a contact

1. Unstarring a starred person

    1. Prerequisite: Address book is populated with contacts.

    2. Test case: `unstar 1`<br>
       Expected: Contact at index 1 is unstarred

    3. Test case: `unstar John`<br>
       Expected: Contact with exact name `John` is unstarred

    4. Test case: `unstar 0`, `unstar -1`, `unstar x` (where x is larger than the list size)<br>
       Expected: Error message reflecting index provided is invalid

    5. Test case: `unstar somebody`<br>
       Expected: Error message reflecting name provided is invalid

2. Starring a starred person

    1. Prerequisite: First contact is unstarred

    2. Test case: `unstar 1`, `unstar x` (where x is the name of the first contact)<br>
       Expected: Message reflects that contact is not starred

### Viewing a contacts details

1. Viewing an existent person

    1. Test case: `view 1`, `view John`<br>
       Expected: Person's details are shown

2. Viewing a non-existent person

    1. Test case: `view 0`, `view -1`, `view x` (where x is larger than the list size)<br>
       Expected: Error message reflecting index provided is invalid.

    2. Test case: `view somebody`<br>
       Expected: Error message reflecting name provided is invalid.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Effort**

### Difficulty Level
The project was moderately challenging due to the complexity of managing multiple entity types and integrating various functionalities. Unlike AB3, which deals with a single entity type (Person), our project handles multiple entities such as Appointments, and Notes, increasing the complexity of the data model and interactions.

### Challenges Faced
1. **Data Management**: Handling multiple entity types required a robust data model and efficient data handling mechanisms.
2. **User Interface**: Designing a user-friendly interface that integrates seamlessly with the underlying logic and data models.
3. **Integration with JavaFX**: Ensuring smooth integration with JavaFX for the UI components, especially given the non-modular setup.
4. **Performance Optimization**: Ensuring the application performs efficiently with a large dataset (up to 1000 contacts) without noticeable sluggishness.
5. **Testing**: Comprehensive testing to ensure all functionalities work as expected and the application is robust against invalid inputs.

### Effort Required
The project required significant effort in the following areas:
1. **Design and Architecture**: Planning the architecture to handle multiple entities and their interactions.
2. **Implementation**: Coding the functionalities, ensuring they work together seamlessly.
3. **Testing**: Writing and executing test cases to ensure the application is bug-free and performs well.
4. **Documentation**: Creating detailed documentation to help future developers understand the system and its components.

### Achievements
1. **Robust Data Model**: Successfully designed and implemented a data model that handles multiple entity types efficiently.
2. **User-Friendly Interface**: Developed a user-friendly interface that integrates well with the underlying logic.
3. **Performance**: Optimized the application to handle large datasets without performance issues.
4. **Comprehensive Testing**: Ensured the application is robust and handles invalid inputs gracefully.

### Reuse and Effort Savings
A significant part of the effort was saved through the reuse of components from the AB3 project:
1. **UI Components**: Reused and adapted UI components from AB3, saving time on designing and implementing new UI elements.
2. **Command Parsing**: Leveraged the command parsing logic from AB3, adapting it to handle the additional commands required for our project.
3. **Storage**: Utilized the storage mechanisms from AB3, modifying them to handle the additional data types.<br>

The reuse of these components allowed us to focus more on the unique aspects of our project, such as handling multiple entity types and optimizing performance, thereby saving approximately 20% of the total effort.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

### Team size: 5

1. Ritvi
2. Ritvi
3. Lynette
4. Lynette
5. Nasya
6. Nasya
7. Kelly
8. Kelly
9. Otto
10. Otto