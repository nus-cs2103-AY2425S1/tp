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

This project is based on the AddressBook-Level3 project created by the  [SE-EDU initiative](https://se-education.org)., whose resources and documentation provided a valuable foundation. We would like to express our gratitude to the SE-EDU team and contributors for their well-designed framework, which facilitated our extensions and customizations, allowing us to build on a robust foundation.

We would also like to acknowledge the inspiration we received from other teams during the development of this project:
1.	Team KonTacts (T11-2):
We drew inspiration from their User Guide, particularly their use of icons and boxes for styling. While we did not reuse any code, their design influenced the way we structured our own User Guide.
2.	Team Financial Assurance Revolutionary Telemarketer (F14b-4):
Our implementation of the confirmation window was inspired by a specific [commit](https://github.com/AY2425S1-CS2103T-F14b-4/tp/commit/de8ef71a6e48fc2fce6bd93d3b12ec45c7623b02) in their project. We developed our own implementation and further refined it during v1.5 (Week 12) after learning about additional design patterns.

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

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-T10-1/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-T10-1/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
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

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-T10-1/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

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

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-T10-1/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
3. The command can communicate with the `Ui` to show an alert dialog and obtain confirmation from user.
4. When user confirms the action (selects "OK"), the command can communicate with the `Model` when it is executed (e.g. to delete a patient).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
5. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

The following activity diagram details the step-by-step workflow of the FilterCommand, from receiving filter input to returning filtered results.

<puml src="diagrams/FilterActivityDiagram.puml" width="600"/>

### Model component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-T10-1/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="700" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)




### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-T10-1/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="700" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

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

| Priority | As a …​        | I want to …​                                                                     | So that I can…​                                                                           |
|----------|----------------|----------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------|
| `* * *`  | New user       | add a new contact with multiple phone numbers (e.g. home, mobile, email address) | manage patient contact information and have multiple options for reaching them in an emergency. |
| `* * *`  | User           | save addresses                                                                   | have their location readily available.                                                    |
| `* * *`  | User           | delete a contact                                                                 | remove outdated or incorrect contact information                                          |
| `* * *`  | User           | list all contacts in one dashboard                                               | easily view all the contacts that I have added.                                           |
| `* * *`  | User           | find the patient contact by a keyword (e.g. name, phone numbers)                 | search the patients’ contact instantly.                                                   |
| `* *`    | User           | edit an existing patient contact                                                 | update their details when necessary.                                                      |
| `* *`    | User           | see all the texts and UI clearly                                                 | don’t have to squint my eyes                                                              |
| `* *`    | User           | import contacts from a file                                                      | quickly fill in the address book with existing contact information.                       |
| `* *`    | User           | export contacts to a file securely                                               | share them with other authorized personnel or have a backup.                              |
| `* *`    | Silly user     | receive confirmation before deleting a contact                                   | prevent accidentally deleting important information                                       |
| `* *`    | User           | sort the patients according to appointment dates                                 | easily know which are the latest upcoming appointments                                    |
| `* *`    | User           | add a contact to my favorite list                                                | quickly access important contacts                                                         |
| `* *`    | User           | see alerts of duplicate contacts in the app                                      | keep the contact list clean and avoid redundancy and confusion                            |
| `* *`    | User           | tag patients with recurring appointments                                         | know those who need regular follow ups                                                    |
| `* *`    | Expert User    | record patients' medical conditions, assigned doctor(s) and/or treatment plan    | prioritize urgent conditions and streamline patient management.                           |
| `* *`    | User           | add notes to a contact                                                           | remember important information about that person.                                         |
| `*`      | User           | filter patients based on appointment dates                                       | contact and remind them.                                                                  |
| `*`      | User           | filter patients based on age groups                                              | prioritize patients who fall within a certain age group.                                  |
| `*`      | Potential user | see the app populated with some sample commands                                  | easily learn how to use the app.                                                          |
| `*`      | User           | use the app to work offline                                                      | use it even when there is no internet connection.                                         |


### Use cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Add a patient's contact**

**MSS**

1. User requests to add patient's contact
2. User inputs the patient's contact details
3. Medicontact adds the patient's contact

    Use case ends.

**Extensions**

* 2a. Necessary field is missing.
    * 2a1. MediContact shows an error message indicating which field is missing.
  
      Use case ends.

* 2b. Wrong format in input.
    * 2b1. MediContact shows an error message specifying the incorrect format.
  
      Use case ends.

**Use case: Delete a patient's contact**

**MSS**

1.  User requests to list patient contacts. 
2.  MediContact shows a list of patient contacts. 
3.  User requests to delete a specific patient's contact in the list. 
4.  MediContact requests for confirmation. 
5.  User confirms to delete. 
6.  MediContact deletes the patient's contact.

    Use case ends.

**Extensions**

* 2a. The patient contact list is empty.

  Use case ends.

* 3a. The given patient's index or patient's name is invalid.
  - 3a1. MediContact shows an error message.
  
    Use case resumes at step 2.
  
* 4a. User chooses to cancel the deletion.

  Use case ends.

**Use case: Clear all patient contacts**

**MSS**

1.  User requests to clear address book. 
2.  MediContact requests for confirmation. 
3.  User confirms to clear. 
4.  MediContact clears all contacts in MediContact.

    Use case ends.

**Extensions**

* 2a. User chooses to cancel the clear action.

  Use case ends.

**Use case: Edit a patient**

**MSS**

1. User requests to list patient contacts.
2. MediContact shows a list of patient contacts.
3. User requests to edit a specific patient's contact and specifies edited fields. 
4. MediContact updates the patient's contact. 

    Use case ends.

**Extensions**

* 2a. The patient contact list is empty.

  Use case ends.

* 3a. The given patient name is invalid. 
  - 3a1. MediContact shows an error message.

    Use case ends.

* 3b. No specified field or any field specified is in the wrong format.
  - 3b1. MediContact shows an error message.

    Use case ends.


**Use case: Filter a patient's contact**

**MSS**

1. User requests to filter patient contacts and specifies the criteria.
2. Medication shows a list of patients matching details.

   Use case ends.

**Extensions**

- 1a. Criterion is missing.
    - 1a1. MediContact shows an error message.

      Use case ends.

- 1b. Wrong format in input
    - 1b1. MediContact shows an error message specifying the incorrect format.
  
      Use case ends.

**Use case: Find a patient's contact**

**MSS**

1. User requests to find patient's contact
2. User inputs the find command with patient's contact details
3. MediContact shows a list of patients matching details

   Use case ends.

**Extensions**

- 2a. Necessary field is missing
  - 2a1. MediContact shows an error message indicating which field is missing.  
  
    Use case ends.
  
- 2b. Wrong format in input
  - 2b1. MediContact shows an error message specifying the incorrect format.
  
    Use case ends.
  
**Use case: Import patient contacts**

**MSS**

1. User requests to import patient contacts.
2. User inputs the import command with new address book file name.
3. MediContact imports the patient contacts.
4. MediContact replaces original patient contacts data with new patient contacts data.

   Use case ends.

**Extensions**

- 2a. File is not in the same folder as application JAR file.
    - 2a1. MediContact shows an error message indicating file not found.

      Use case ends.

- 2b. File is not in JSON format.
    - 2b1. MediContact shows an error message indicating wrong file format.

      Use case ends.
  
- 2c. File is not in expected format of MediContact data.
    - 2c1. MediContact shows an error message indicating invalid JSON format.

      Use case ends.


**Use case: List patient contacts**

**MSS**

1. User requests to list patient contacts.
2. User inputs the list command.
3. MediContact shows a list of patient contacts.

   Use case ends.

**Use case: List starred patient contacts**

**MSS**

1. User requests to list starred patient contacts.
2. User inputs the list star command.
3. MediContact shows a list of starred patient contacts.

   Use case ends.

**Use case: Star a patient's contact**

**MSS**

1. User requests to list patient contacts.
2. MediContact shows a list of patient contacts.
3. User requests to add a patient's contact into starred list.
4. User inputs the star command with index or full name of the patient.
5. MediContact adds the patient's contact into starred list.

   Use case ends.

**Extensions**

- 2a. The patient contact list is empty.

  Use case ends.

- 4a. Name or index given is invalid
    - 4a1. MediContact shows an error message indicating invalid name or index.

      Use case ends.

**Use case: Sort patient contacts**

**MSS**

1. User requests to sort patient contacts.
2. User inputs the sort command.
3. MediContact sorts the displayed list of patient contacts by appointments.

**Extensions**

- 2a. No patient contact with appointment dates found.

    - 2a1. MediContact shows an error message.
    - 2a2. MediContact sorts the patient contacts alphabetically.
  
      Use case ends.

- 2b. More than one patient contact have the same appointment dates.
    
    - 2b1. MediContact sorts the patient contacts having same appointment dates alphabetically.
    
    Use case ends.

**Use case: View note**

**MSS**

1. User requests to list patient contacts.
2. MediContact shows a list of patient contacts.
3. User requests to view a patient's note.
4. User inputs the view command with index or full name of the patient.
5. MediContact shows the patient's contact with the note.

   Use case ends.

**Extensions**

- 2a. The patient contacts list is empty.

  Use case ends.

- 4a. Name or index given is invalid
    - 4a1. MediContact shows an error message indicating invalid name or index.

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

Note to project: Security measures like encryption will not be implemented in this project.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Encryption**: The process of converting data into a code to prevent unauthorized access, typically using algorithms and keys.
* **Java**: It is a programming language and computing platform that's used to build applications and websites.
* **Directory**: Also known as folder is a location for storing files or other folders on your computer.
* **Alphanumeric**: Any collection of number characters and letters. E.g. A–Z, a-z and 0–9.
* **Integer**: A number that is not a fraction; a whole number.

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

### Adding a patient
1. Adding a patient

   1. Prerequisites: List all patients using `list` command. Multiple patients in the list.

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

2. Adding a duplicate patient
    1. Prerequisites: List all patients using `list` command. Multiple patients in the list.<br>
       Person with name `Bryan Lim` should already be in list.

    2. Test case: `add n/Bryan Lim p/00000000 e/different_bryan@example.com a/5 Hilly Road b/23 s/Male`<br>
       Expected: No contact is added to the list, error message regarding patient already existing should be shown.

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

### Deleting a patient

1. Deleting a patient via index while all patients are being shown

   1. Prerequisites: List all patients using the `list` command. Multiple patients in the list.

   1. Test case: `delete 1`<br>
      Expected: Confirmation dialog is triggered. <br><br>If confirmed, first contact is deleted from the list. Message reflecting delete action successful is shown.
      <br><br>If cancelled, no patient is deleted, message reflecting delete action being cancelled is shown.
   1. Test case: `delete 0`<br>
      Expected: No patient is deleted. Error details shown in the status message.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. Deleting a patient via name while all patients are being shown

    1. Prerequisites: List all patients using the `list` command. Multiple patients in the list.<br>
       John Doe is an existing contact

    1. Test case: `delete John Doe`<br>
       Expected: Confirmation dialog is triggered. <br><br>If confirmed, John Doe is deleted from the list. Message reflecting delete action successful is shown.
       <br><br>If cancelled, no patient is deleted, message reflecting delete action being cancelled is shown.

    1. Test case: `delete Jane Doe`<br>
       Expected: No patient is deleted. Error details shown in the status message.

### Editing a patient

1. Editing a patient's fields

    1. Prerequisites: Person named `John Doe` must exist.

    2. Test case: `edit John Doe n/Jane Doe`<br>
       Expected: Contact with name `John Doe` is changed to `Jane Doe`

    3. Other test cases to try: `edit John Doe a/6 Sunny Road`, `edit John Doe b/35`, etc<br>
       Expected: Similar to previous results with relevant fields changed

    4. Test case: `edit John Doe n/John Doe`<br>
       Expected: Error message reflecting no changes detected

    5. Test case: `edit John Doe n/John Doe b/35` (given that John Doe's age is not 35) <br>
       Expected: Contact with name `John Doe` has age updated to 35

2. Editing a non-existent patient's field

    1. Prerequisites: Person named `John Doe` must not exist.

    2. Test case: `edit John Doe n/Jane Doe`<br>
       Expected: Error message reflecting provided name is invalid

    3. Other test cases to try: `edit John Doe a/6 Sunny Road`, `edit John Doe b/35`, etc<br>
       Expected: Similar to previous results.

3. Editing a patient with no fields

    1. Test case: `edit John Doe`<br>
       Expected: Error message reflecting at least one field must be provided.

    2. Test case: `edit Non Existent Person`<br>
       Expected: Similar to previous results.

4. Clearing a patients appointments and tags

    1. Prerequisites: Person named `John Doe` must exist.

    2. Test case: `edit John Doe t/`<br>
       Expected: Contact with name `John Doe` has all existing tags cleared

    3. Test case: `edit John Doe ap/`<br>
       Expected: Contact with name `John Doe` has all existing appointments cleared

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

1. Adding new notes to an existing patient

    1. Test cases: `note Bryan ap/01/01/2025 r/Allergic to Ibuprofen`, `note John m/10mg Panadol`<br>
       Expected: Notes are added to patient (Behaviour is very similar to Edit Command's appointments and tags)

2. Removing note's fields of an existing patient

    1. Test cases: `note Bryan ap/ r/`, `note John m/`<br>
       Expected: Respective Note fields are removed (Behaviour is very similar to Edit Command's appointments and tags)

### Sorting the list

1. Sorting list by appointment dates

    1. Test case: `sort`<br>
       Expected: List is sorted by appointment dates. Contacts with no appointments are at the end, sorted alphabetically.

### Starring a contact

1. Starring an unstarred patient

    1. Prerequisite: Address book is populated with contacts.

    2. Test case: `star 1`<br>
       Expected: Contact at index 1 is starred

    3. Test case: `star John`<br>
       Expected: Contact with exact name `John` is starred

    4. Test case: `star 0`, `star -1`, `star x` (where x is larger than the list size)<br>
       Expected: Error message reflecting index provided is invalid

    5. Test case: `star somebody`<br>
       Expected: Error message reflecting name provided is invalid

2. Starring a starred patient
    1. Prerequisite: First contact is starred

    2. Test case: `star 1`, `star x` (where x is the name of the first contact)<br>
       Expected: Message reflects that contact has already been starred

### Unstarring a contact

1. Unstarring a starred patient

    1. Prerequisite: Address book is populated with contacts.

    2. Test case: `unstar 1`<br>
       Expected: Contact at index 1 is unstarred

    3. Test case: `unstar John`<br>
       Expected: Contact with exact name `John` is unstarred

    4. Test case: `unstar 0`, `unstar -1`, `unstar x` (where x is larger than the list size)<br>
       Expected: Error message reflecting index provided is invalid

    5. Test case: `unstar somebody`<br>
       Expected: Error message reflecting name provided is invalid

2. Starring a starred patient

    1. Prerequisite: First contact is unstarred

    2. Test case: `unstar 1`, `unstar x` (where x is the name of the first contact)<br>
       Expected: Message reflects that contact is not starred

### Viewing a contacts details

1. Viewing an existent patient

    1. Test case: `view 1`, `view John`<br>
       Expected: Person's details are shown

2. Viewing a non-existent patient

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
6. **Complex Codebase**: Starting from a brownfield project meant working with a complex, pre-existing codebase. Understanding and adapting to others' coding styles and logic added a significant challenge, requiring time and careful analysis to ensure seamless integration of new features.

### Effort Required
The project required significant effort in the following areas:
1. **Familiarizing with existing code**: Understanding and adapting to others' coding styles and logic added a significant challenge, requiring time and careful analysis to ensure seamless integration of new features.
2. **Design and Architecture**: Planning the architecture to handle multiple entities and their interactions.
2. **Implementation**: Coding the functionalities, ensuring they work together seamlessly.
3. **Testing**: Writing and executing test cases to ensure the application is bug-free and performs well.
4. **Documentation**: Producing comprehensive documentation for both developers and users, including a developer guide to explain the system's components and a user guide to ensure understanding of the application's features.

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
4. **Architecture Design**: Built upon the existing architecture design from AB3, extending classes on top of the current structure.

The reuse of these components allowed us to focus more on the unique aspects of our project, such as handling multiple entity types and optimizing performance, thereby saving approximately 20% of the total effort.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

### Team size: 5

1. **Improve handling of name inputs (`add` and `edit` commands)**: Currently names accepted are only alphanumeric. 
To improve inclusivity, we plan to accept special characters (e.g. "s/o", "d/o"), accents (e.g. é, è) as well as other
languages. However, we will restrict names from including numbers, due to their lack of use.<br><br>
An example of a newly accepted command will be:`edit Javier n/Javiér`<br><br>
An example of a command that will trigger an invalid error will be: `edit Javier n/J4vier`<br>

2. **Improve our date package (Used by all commands that require a date)**: Currently, it is possible to input invalid
dates such as `29/02/2025 1200` (Invalid leap year) or `31/06/2025 2400` (31st of June does not exist). With our intended
improvement, an error message will be displayed if any instance of an invalid date is inputted.<br><br>
A few examples of commands that will throw invalid date errors will be:<br>
`edit Javier ap/31/06/2025 2400`<br>
`filter ap/31/06/2025 - 29/02/2026`<br>

3. **Introduce stricter constraints for `age`**: Currently our Age field accepts any value ranging from 0-999, which seems
a tad too high. We plan to decrease the upper limit of the range to a more reasonable 150<br><br>
An example of a command that will trigger invalid date errors will be:`edit Javier b/151`<br>

4. **Set age boundaries for `filter age`**: Currently, the `filter` command allows any age range to be inputted, which 
can lead to invalid or nonsensical ranges. We plan to set boundaries for the age filter to ensure that only valid age 
ranges are accepted. The valid age range will be from 0 to 150. We will also check for string length to prevent any 
overflow issues.<br><br>
An example of a command that will trigger an invalid age range error will be: `filter b/151-200`

5. **Introduce verification of phone numbers**: Currently, the application does not properly 
verify the format of phone numbers and email addresses. We plan to introduce validation checks to ensure that phone 
numbers exists.<br><br>
An examples of an invalid input that will trigger an error: `edit Javier p/00000000` (phone number should exist)<br>

6. **Introduce verification of emails**: Currently, the application does not properly verify the format email addresses 
as invalid emails such as `example@example` are still accepted, which is missing the TLD portion of the email. We plan 
to improve validation checks to ensure that email addresses are in the correct format.<br><br>
An examples of an invalid input that will trigger an error: `edit Javier e/example@example` (should follow the format `local-part@domain.tld`)<br>

7. **View any patient’s note without needing to return to the patient list view**: Currently, to view the details or 
notes of a different patient after viewing a specific patient, users must first input the list command to return to the 
full patient list before specifying another patient (e.g. `view SECONDPATIENT`).<br><br>
The enhanced view command will allow the user to switch directly to another patient’s information without the need to 
re-list all patients first.

8. **Introduce stricter constraints for `sex`**: Currently, we accept any alphanumerical input. However, this may not be reflective of 
real-life behavior, despite the freedom it provides the user. Therefore, we plan to restrict the input for the sex field 
to predefined values such as "Male", "Female", and "Other".<br><br>
An example of a command that will trigger an invalid sex error will be: `edit Javier s/Unknown`

9. **Improve UI experience for users**: Currently, the UI may not be as user-friendly as desired. We plan to enhance the 
UI to provide a better user experience. This includes making the UI more responsive, improving the layout, and adding 
visual cues to guide users.<br><br>
Examples of improvements:<br>
**Responsive Design**: Ensure the UI adapts to different screen sizes, providing a consistent experience across 
monitors of different sizes and ensure that there are no text cut-offs for any font size or style used.<br>
**Improved Layout**: Organize the UI elements more logically, making it easier for users to find and use features.<br>
**Visual Cues**: Add star icons for starred contacts, and other visual indicators to help users understand the functionality of different UI elements.

10. **Introduce more detailed error messages**: Currently, the error messages may not provide enough information to help 
users understand what went wrong and how to fix it. We plan to enhance the error messages to be more descriptive and 
helpful.<br><br>
An example of an improvement will be: **Invalid Date**<br>
Current: `Invalid date!`<br>
Improved: `Invalid date! The date '31/06/2025' is invalid because June has only 30 days. Please use the format dd/mm/yyyy. Example: 30/06/2025`
