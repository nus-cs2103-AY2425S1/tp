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

- Sample data for testing purpose developed by [Wu Zengfu](AboutUs.md#wu-zengfu): https://github.com/wuzengfu/tp_util
- JavaFX Pagination documentation: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Pagination.html
- Code for responsive design : ChatGPT (Chua Tse Hui uses it)
- Inspired by Code given for camel Case conversion (Chua Tse Hui uses it): https://www.baeldung.com/java-string-to-camel-case 

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<img src="diagrams/ArchitectureDiagram.svg" width="280" alt="Architecture Diagram"/>

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

<img src="diagrams/ArchitectureSequenceDiagram.svg" width="574" alt="ArchitectureSequenceDiagram"/>

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="diagrams/ComponentManagers.svg" width="300" alt="ComponentManagers"/>

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<img src="diagrams/UiClassDiagram.svg" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `ContactListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Contact` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="diagrams/LogicClassDiagram.svg" width="550" alt="LogicClassDiagram"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<img src="diagrams/DeleteSequenceDiagram.svg" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a contact).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="diagrams/ParserClasses.svg" width="600" alt="ParserClasses"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="diagrams/ModelClassDiagram.svg" width="450" alt="ModelClassDiagram"/>


The `Model` component,

* stores the address book data i.e., all `Contact` objects (which are contained in a `UniqueContactList` object).
* stores the currently 'selected' `Contact` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Contact>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
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

### Pagination

The pagination makes use of `javafx.scene.control.Pagination` component which is inherited by `PaginationPanel`.
The `PaginationPanel.java` stores all logic and attributes related to Pagination.

The `PaginationPanel` contains the following member/class variables:

* `ROWS_PER_PAGE`: Represents the number of items to display in a single page.
* `currentPageIndex`: Represents the index (0-indexed) of the current page, it is shared among all instances and hence `static`.
* `personList`: A **reference** of `ObservableList<Person>` from `Logic` during the initialization of UI.

#### Implementation of constructor ####

The constructor of `PaginationPanel` takes in a reference of `ObservableList<Person>` and stores it as a member variable.
Since it is _observable_, the pagination listens to the event when there is an update of `personList`, this is implemented by
`this.personList.addListener(this::onListItemsChanged)`. Then the constructor initializes the pagination component.


#### Steps to update the list when there is a change ####

Since the constructor adds a listener that listens to `onListItemsChanged` event on `personList`.
The `onListItemsChanged` simply invokes `initPagination` to re-render the list displayed.
It takes the following steps to make the update:

1. Calculate the number of pages by `personList.size()` and `ROWS_PER_PAGE`. The `Math::max` ensures that there is **at least one** page
even when there is no item. Then it updates the page count of the pagination.
2. Calculate the starting index of sublist from `personList`.
3. Calculate the end index of sublist from `personList`. The `Math::min` makes sure that the index does not go beyond the list size. Hence,
when there is less than the default `ROWS_PER_PAGE` number of items to render, it can correctly render all remaining items without the risk of
triggering `ArrayIndexOutOfBoundException`.
4. Get the sublist to be rendered based on the calculated `fromIndex` and `endIndex`.
5. Render the updated sublist.

### Status bar footer -- how to reflect the total number of contacts 
The `ModelManager` class now also stores `private final ObservableList<Contact> allContacts` on top of the `FilteredList`. This `allContacts` can be obtained subsequently by the `LogicManager` with a method provided by the ModelManager. A listener is added so that the statusbarFotoer will listen for any changes made to the `allContacts` and if so update the number accordingly.

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

Administrative Directors of CCAs in NUS who wish to manage a database of the CCA members more effectively.
The database size is around 50 students. These directors are tech savvy who can type fast.


**Value proposition**: Enables users to efficiently and easily manage large CCA membership database
(including creation, edition, and deletion of data entries).

--------------------------------------------------------------------------------------------------------------------

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                     | I want to …​                                                                                                                                                                             | So that I can…​                             -                                                                                    |
|----------|-----------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------|
| `* * *`  | new user                    | search the contact information by his/her name                                                                                                                                           | find the information of this member quickly.                                                                                     |
| `* * *`  | user                        | remove a member from the address book                                                                                                                                                    | update the address book accordingly if he is no longer in the club                                                               |
| `* * *`  | user                        | use this app to populate the member’s details into the app database                                                                                                                      | save their details (name, year, tele handle, email address) into the database for future reference                               |
| `* *`    | user                        | see a demonstration or tutorial for me to get started                                                                                                                                    | know how to use the app                                                                                                          |
| `*`      | user that values efficiency | “manipulate” member’s data entries in batches                                                                                                                                            | can do things efficiently (Manipulate includes: Edit, Delete and Add)                                                            |
| `*`      | intermediate user           | generate a list of selected member information easily                                                                                                                                    | observe only the information I need                                                                                              |
| `*`      | user                        | archive data from past members / alumni to somewhere else                                                                                                                                | use them in the future when the need arises, such as passing the details to comapnies who may only wish to invite graduates only |
| `*`      | expert user                 | train another new user to take over my role and responsibility in the CCA                                                                                                                | hand over the attendance tracking process easily                                                                                 |
| `*`      | frequent user               | indicate whether a member has attended a training/event                                                                                                                                  | keep track of the participation status (how active it is) of each member                                                         |
| `*`      | frequent user               | search for member's details based on active participation status                                                                                                                         | have a better sensing on member's involvement to choose for the next ExCo                                                        |
| `*`      | intermediate user           | understand / be guided on the automation capabilities of this tool such as automatically updating particulars (year of study, graduation status, on exchange) when a new semester begins | use the app with ease                                                                                                            |
| `*`      | user                        | view the PDPA stance the members have in terms of sending of info the 3rd parties                                                                                                        | know whether I am allowed to send member’s personal details to 3rd parties                                                       |
| `* *`      | user                    | collate a list of Telegram handles of members under a particular group when I specify a category                                                                           | quickly send relevant messages, such as instructions or job opportunities.                                  |
| `*`          | user                    | sort the members' details                                                                                                                                                  | view the full list of details with ease if needed.                                                          |
| `* *`      | user who values efficiency | gather all the emails of the members                                                                                                                                       | quickly send semester newsletters or updates to all members via email.                                      |
| `* *`        | efficient user          | retrieve contact details of a selected group of members interested in a specific industry (e.g., semicon company)                                                          | provide these details to companies.                                                                         |
| `* *`        | user                    | verify whether the member's details in the database are the most updated                                                                                                   | confidently send them to third parties knowing that the data sent is correct.                               |
| `*`        | user                    | archive data from past members or alumni to another location                                                                                                              | use them in the future, such as for passing alumni details to companies that wish to invite graduates only. |
| `*`          | user                    | search for the relevant contact details of a particular past member                                                                                                        | contact them.                                                                                               |
| `*`        | user                    | toggle between viewing current members' data and alumni data                                                                                                               | access the correct set of information when needed.                                                          |
| `*`          | intermediate user       | find out about advanced functions in managing tools                                                                                                                        | explore additional features for managing members.                                                           |
| `* *`      | intermediate user       | automatically register members' details into the database when the academic year starts                                                                                    | maintain efficient processes.                                                                               |
| `* *`      | intermediate user       | automatically update particulars (year of study, graduation status, on exchange) when necessary, especially at the beginning of a new semester                             | ensure data accuracy with minimal manual effort.                                                            |
| `* *`        | intermediate user       | generate a custom list of selected member information with only the needed details                                                                                         | easily retrieve the specific data required.                                                                 |
| `* * *`      | user who values data privacy | set a password for the app                                                                                                                                                 | restrict access to only authorised individuals.                                                             |


--------------------------------------------------------------------------------------------------------------------

### Use Cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)


**Use case: Add a contact**

**MSS**

1.  User requests to add contact
2.  AddressBook adds the contact

    Use case ends.

**Extensions**

* 1a. Given contact has invalid or duplicate fields.
    * 1a1. AddressBook shows an error message.

      Use case ends.

* 1b. Given contact has duplicate 'name' field.
    * 1a1. AddressBook shows an error message and prompts user to re-enter with nickname.

      Use case ends.

**Use case: Delete a contact**

**MSS**

1.  User requests to delete contact
2.  AddressBook deletes the contact

    Use case ends.

**Extensions**

* 1a. The given contact is not in the AddressBook
    * 1a1. AddressBook shows an error message.

      Use case ends.

* 1b. Multiple matching contacts in the AddressBook
    * 1b1. AddressBook shows list of all matching contacts.
    * 1b2. User enters index of contact to delete

      Use case resumes at step 2.


**Use case: Search for contact**

**MSS**

1.  User requests to search for contact
2.  AddressBook shows list of all matching contacts

    Use case ends.

**Extensions**

* 1a. User did not provide the search input
    * 1a1. AddressBook shows an error message.

      Use case ends.
* 1b. Given search input has invalid or duplicate fields.
    * 1b1. AddressBook shows an error message.

      Use case ends.
* 1c. Search input does not match any contact in the AddressBook
    * 1c1. AddressBook shows an error message and prompts user to try searching under another field

      Use case ends.

**Use case: List all contacts**

**MSS**

1.  User requests to list all contacts
2.  AddressBook shows list of all contacts

    Use case ends.


**Use case: Help to find list of contacts**

**MSS**

1.  New user requests to see the list of commands
2.  Data_coNdUctorS shows help list and URL to our User Guide
3.  User goes to the User Guide link

    Use case ends.

--------------------------------------------------------------------------------------------------------------------

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 contacts without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Data changes should be automatically saved to avoid loss of data.
5.  Should validate data entries to ensure consistency and correctness (eg. check that email format is correct etc.)
6.  Should be able to accommodate increasing member data over time.
7.  Should be able to retrieve and display member data within 2 seconds.
8.  User Interface should be intuitive even for new users.

--------------------------------------------------------------------------------------------------------------------

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

This section provides a guide for performing manual testing on the data_coNdUctorS application. Each test case includes expected results and possible error messages for invalid inputs.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

---

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

---

### Adding a Contact

1. Adding a valid contact

   1. Prerequisites: Application is open, and user is on the main contact list screen.
   
   1. Test case: `add n/John Doe th/johnd123 e/john.doe@example.com ss/undergraduate 1 r/Admin nn/johny`<br>
      Expected: A new contact named "John Doe" is added to the contact list. Status message shows "Contact added successfully."
      
   1. Test case: `add n/John Doe th/johnd123 e/john.doe@example.com ss/undergraduate 1 r/Admin nn/johny` executed twice <br>
      Expected: No contact is added. Error message will be displayed due to duplicate fields

   1. Test case: `add n/John Doe th/johnd123 e/john.doe(at)example.com ss/undergraduate 1 r/Admin`<br>
      Expected: No contact is added. Error message displayed due to incorrect email format

   1. Other incorrect add commands to try: `add`, `add n/`, `add th/username e/email.com`<br>
      Expected: Error message displayed indicating missing or incorrect parameters.

---

### Listing All Contacts

1. Listing contacts with no additional parameters

   1. Prerequisites: Application is open with multiple contacts in the list.
   
   1. Test case: `list`<br>
      Expected: All contacts are displayed in the list view. Status message shows "Listed all contacts."

   1. Other variations to try: `list all`, `list contacts` or any variation of `all` and/or `contacts` after `list`<br>
      Expected: Similar outcome to `list` command. All contacts are displayed.

---

### Deleting a contact

1. Deleting a contact while **ALL** contacts are being shown

   1. Prerequisites: List all contacts using the `list` command. Multiple contacts in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No contact is deleted. Error details shown in the status message. Status bar remains the same.

   1. Test case: `delete UNIQUE_FULL_NAME`<br>
      Expected: If UNIQUE_FULL_NAME is in the addressbook and there is only one FULL_NAME, deletes that contact.

   1. Test case: `delete NOT_UNIQUE_FULL_NAME`<br>
      Expected: If NOT_UNIQUE_FULL_NAME is in the addressbook but not unique, no contact is deleted and error details shown in the status message for user to delete via index.

   1. Test case: `delete FULL_NAME_NOT_IN_LIST`<br>
      Expected: If FULL_NAME_NOT_IN_LIST is not in addressbook, no contact is deleted even if the name is present in the addressbook and error details shown in the status message.

   1. Test case: `delete NOT_FULL_NAME`<br>
      Expected: No contact is deleted and error details shown to delete by the full name of delete by index.

   1. Other incorrect delete commands to try: `delete`, `delete x` (where x is larger than the list size)<br>
      Expected: No contact is deleted. Error details shown in the status message for missing or invalid fields

1. Deleting a contact while **SOME** contacts are being shown

   1. Prerequisites: Find some contacts using the `find` command. Multiple contacts in the filtered list.

   1. Test case: `delete 1`<br>
      Expected: Similar to above.

   1. Test case: `delete 0`<br>
      Expected: Similar to above.
      
   1. Test case: `delete UNIQUE_FULL_NAME`<br>
      Expected: If UNIQUE_FULL_NAME is in the filtered list and there is only one FULL_NAME, deletes that contact.

   1. Test case: `delete NOT_UNIQUE_FULL_NAME`<br>
      Expected: If NOT_UNIQUE_FULL_NAME is in the filtered list but not unique, no contact is deleted and error details shown in the status message for user to delete via index.

   1. Test case: `delete FULL_NAME_NOT_IN_LIST`<br>
      Expected: If FULL_NAME_NOT_IN_LIST is not in filtered list, no contact is deleted even if the name is present in the addressbook and error details shown in the status message.

   1. Test case: `delete NOT_FULL_NAME`<br>
      Expected: No contact is deleted and error details shown to delete by the full name of delete by index.

   1. Other incorrect delete commands to try: `delete`, `delete x` (where x is larger than the list size), `delete NOT_FULL_NAME`<br>
      Expected: Similar to above.

---

### Editing a Contact

1. Editing a contact while **ALL** contacts are being shown

   1. Prerequisites: List all contacts using the `list` command. Multiple contacts in the list.
   
   1. Test case: `edit 1 n/Jane Smith th/jane_smith`<br>
      Expected: First contact’s name changes to "Jane Smith" and Telegram handle to "jane_smith." Status message shows "Contact edited successfully."

   1. Test case: `edit 99 n/Jane Smith`<br>
      Expected: No contact is edited. Error message displayed: "Error: Contact not found. Please provide a valid index."

   1. Test case: `edit UNIQUE_FULL_NAME th/john_doe`<br>
      Expected: If UNIQUE_FULL_NAME is in the addressbook and there is only one FULL_NAME, edits that contact.

   1. Test case: `edit NOT_UNIQUE_FULL_NAME th/john_doe`<br>
      Expected: If NOT_UNIQUE_FULL_NAME is in the addressbook but not unique, no contact is edited and error details shown in the status message for user to edit via index.

   1. Test case: `edit FULL_NAME_NOT_IN_LIST th/john_doe`<br>
      Expected: If FULL_NAME_NOT_IN_LIST is not in addressbook, no contact is edited even if the name is present in the addressbook and error details shown in the status message.

   1. Test case: `edit NOT_FULL_NAME th/john_doe`<br>
      Expected: No contact is edited and error details shown to edit by the full name of edit by index.

   1. Other incorrect edit commands to try: `edit`, `edit 1`, `edit 1 e/invalidemail.com`<br>
      Expected: Error message displayed for missing or invalid fields.
      
1. Editing a contact while **SOME** contacts are being shown

   1. Prerequisites: Find some contacts using the `find` command. Multiple contacts in the filtered list.
   
   1. Test case: `edit 1 n/Jane Smith th/jane_smith`<br>
      Expected: Similar to above.

   1. Test case: `edit 99 n/Jane Smith`<br>
      Expected: Similar to above.

   1. Test case: `edit UNIQUE_FULL_NAME th/john_doe`<br>
      Expected: If UNIQUE_FULL_NAME is in the filtered list and there is only one FULL_NAME, edits that contact.

   1. Test case: `edit NOT_UNIQUE_FULL_NAME th/john_doe`<br>
      Expected: If NOT_UNIQUE_FULL_NAME is in the filtered list but not unique, no contact is edited and error details shown in the status message for user to edit via index.

   1. Test case: `edit FULL_NAME_NOT_IN_LIST th/john_doe`<br>
      Expected: If FULL_NAME_NOT_IN_LIST is not in filtered list, no contact is edited even if the name is present in the addressbook and error details shown in the status message.

   1. Test case: `edit NOT_FULL_NAME th/john_doe`<br>
      Expected: No contact is edited and error details shown to edit by the full name of edit by index. 

   1. Other incorrect edit commands to try: `edit`, `edit 1`, `edit 1 e/invalidemail.com`<br>
      Expected: Similar to above.

---

### Finding a Contact

1. Finding a contact by name and role

   1. Prerequisites: Multiple contacts with different names and roles are present in the contact list.
   
   1. Test case: `find n/John r/Admin`<br>
      Expected: Contacts matching "John" with role "Admin" are displayed in the list. Status message shows "Found contacts matching criteria."

   1. Test case: `find n/FULL_NAME_NOT_IN_LIST r/Admin`<br>
      Expected: No contacts are displayed. Status message shows "No contacts found matching the criteria."

   1. Other incorrect find commands to try `find`, `find n/NOT_FULL_NAME r/ROLE_NOT_IN_LIST`<br>
      Expected: If no contacts match, a message appears indicating no contacts found. If an invalid role is entered, an error message displays: "Error: Invalid role specified."

---
 
### Saving data

1. Automatic Data Saving

   1. Prerequisites: Application is running, and a contact has been added, edited, or deleted.
   
   1. Test case: Close and reopen the application.<br>
      Expected: The changes made to contacts persist after reopening. The saved contacts appear as they were before closing the app.

2. Data File Integrity

   1. Prerequisites: The data file (`addressbook.json`) is accessible for manual editing.
   
   1. Test case: Manually edit the JSON file to include an invalid format or structure (e.g., remove a required field).<br>
      Expected: Upon launching, the application detects the corrupted data and either resets to an empty state or prompts an error message like "Error: Invalid data format detected. Data reset to empty state."

---

### GUI Components

1. Pagination

   1. Prerequisites: Add enough contacts to exceed a single page (e.g., 11 or more contacts as each page displays 10).
   
   1. Test case: Use pagination controls (next, previous) or page navigation to view contacts on multiple pages.<br>
      Expected: Pagination controls work as expected, allowing the user to navigate through the contact list. The status bar reflects the current page and total contacts.

2. Footer Status Bar

   1. Prerequisites: Add or delete a contact to observe changes in the footer.
   
   1. Test case: Add a contact, then delete a different contact.<br>
      Expected: The footer updates in real-time, displaying the current total contacts and the path of the data file. Status message shows the results of the last action.

### Additional Notes

1. Testing Edge Cases

   1. Prerequisites: Application is running with an open contact list.
   
   1. Test case: Add or edit a contact with special characters in fields, very long inputs, or boundary values (e.g., maximum allowed length for fields).<br>
      Expected: The application handles special characters, lengthy inputs, and boundary values without crashing or error. If any input exceeds limits, a message such as "Error: Input exceeds allowed length" is shown.

2. Common Mistakes

   1. Prerequisites: Application is open and ready for input.
   
   1. Test case: Enter commonly mistaken commands or leave required fields blank (e.g., `delete` with no index, `find` with no criteria).<br>
      Expected: Error messages are clear and descriptive, guiding the user to correct the input format. Status bar remains unaffected by failed commands.

3. Data Reset

   1. Prerequisites: The application has saved data that the user wishes to reset.
   
   1. Test case: Delete or replace the data JSON file and restart the application.<br>
      Expected: The app initialises with an empty contact list, and a message indicates that no saved data was found, prompting a fresh start.

---

This appendix provides a comprehensive approach to testing the data_coNdUctorS app, ensuring each core feature and edge case is addressed. Error messages will appear on the screen if incorrect inputs are entered, helping users to correct their input efficiently.



## Appendix: Planned Enhancements

This section outlines the planned future enhancements for the data_coNdUctorS application to improve functionality, usability, and scalability.

---

### Advanced Error Messages

1. Informing the User what is their specific issue with the command that they have inputted 

   1. Goal: Quality of Life for users to understand which part of the command they inputted wrongly so they don't have to waste time
  
   1. Planned Implementation example: Editing a contact with the same `NAME` and `NICKNAME` as a current contact will result in an error message: "This Contact already exists in the address book". This is not very clear and so in the future, we could show that current contact to the user and show them which fields are duplicated
      
   1. Expected Outcome: Users will waste less time debugging their inputs, espeically for large address books.

---


