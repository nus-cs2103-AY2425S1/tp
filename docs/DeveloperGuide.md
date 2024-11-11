---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# LegacyLink Developer Guide

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

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-T10-4/tp/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-T10-4/tp/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
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

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-T10-4/tp/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-T10-4/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-T10-4/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-T10-4/tp/tree/master/src/main/java/seedu/address/logic/Logic.java)

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
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-T10-4/tp/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-T10-4/tp/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

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

* Has a large extended family with numerous contacts to manage
* Values maintaining connections with various family branches (direct family, paternal side, maternal side, in-laws)
* Organizes or participates in frequent family gatherings and events
* Desires an efficient way to keep family contact information up-to-date
* Appreciates tools that help navigate complex family dynamics
* Is comfortable using digital tools for personal organization
* Ranges from young adults to seniors who want to stay connected with their extended family

**Value proposition**: LegacyLink offers a comprehensive family contact management system that simplifies the organization of large family trees, streamlines event planning, and helps maintain family connections more effectively than traditional contact management methods.
It revolutionizes the "family experience"!
### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​         | I want to …​                                                 | So that I can…​                                                       |
|----------|-----------------|--------------------------------------------------------------|-----------------------------------------------------------------------|
| `* * *`  | As a user       | add contact information of the family member                 | I can retrieve their contact information                              |
| `* * *`  | As a user       | add the relationship of the family members                   | I can know the relationship between people                            |
| `* * *`  | As a user       | update the information of family members in the contact list | I can keep the latest information of my family                        |
| `* * *`  | As a user       | add events tied to family members (e.g birthdays)            | I can set reminders on that date so that I don't ever forget about it |
| `* * *`  | As an organizer | track RSVPs and attendance for each event                    | I know who is attending the event and can plan accordingly            |
| `* * *`  | As an organizer | schedule family events                                       | I can plan and coordinate events                                      |
| `* * *`  | As an organizer | see the contact list of family members                       | I know whose contacts that I have not added yet and add them          |
| `* * *`  | As an organizer | update the event's information after creating it             | attendees can see the updated event details                           |
| `* * *`  | As an organizer | delete an event                                              | I can cancel an event                                                 |

### Use cases

(For all use cases below, the **System** is the `LegacyLink` and the **Actor** is the `user`, unless specified otherwise)

### Use case 1: Add contact

**MSS**
1. User enters name, phone number, email and relationship of the contact.
2. User confirms details of the contact.
3. System adds the contact.

    Use case ends.

**Extensions**

* 1a. User enters an invalid name, phone number, email, and/or relationship.

    Use case resumes at step 1.

* 3a. The addition of contact causes duplicate contacts.

    * 3a1. System shows an error message. 
  
    Use case resumes at step 2.

* *a. At any time, User chooses to cancel adding a contact Use case ends.

### Use case 2: Delete contact

**MSS**

1.  User views all contacts [UC-3](#use-case-3-view-all-contacts).
2.  System shows a list of persons.
3.  User requests to delete a specific contact in the list.
4.  System deletes the person.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.


* 3a. The given index is invalid.

    * 3a1. System shows an error message.

      Use case resumes at step 2.

### Use case 3: View all contacts

**MSS**

1. User requests to view the list of contacts.
2. The system displays a list of all contacts.
3. User can scroll through the list to see all the contact listed.
4. User can click on a contact to view more details.

    Use case ends.

### Use case 4: Update information of contact

**MSS**

1. User lists all contacts [UC-3](#use-case-3-view-all-contacts).
2. User selects contact to update.
3. User can edit name / phone number / email / relationship of contact.
4. System registers the changes.

    Use case ends.

**Extensions**

* 2a. The given index is invalid.

    * 1a1. System shows an error message.

      Use case resumes at step 2.

* 3a. The changed details are invalid.

  * 3a1. System shows an error message.

    Use case resumes at step 3.

* 3b. User does not change contact details.

  * 3b1. System shows an error message.
  
    Use case resumes at step 3.

* 4a. The edited contact causes duplicate contacts.

  * 4a1. System shows an error message.

    Use case resumes at step 3.

* *a. At anytime, User can choose to cancel updating the contact.

    * a1. System does not update any contact details.
      Use case ends.


### Use case 5: Add event

**MSS**

1. User enters name, start date, end date, location of the event.
2. User confirms the details of the event.
3. System adds the event.
4. User is given feedback that the event is added successfully.

    Use case ends.

**Extensions**

* 1a. User enters index of persons attending.

    Use case resumes at step 2.


* 1b. The inputs are invalid

    * 1b1. System shows an error message

        Use case resumes at step 1.


* *a At any time, Users chooses to cancel the adding.


### Use case 6: Delete an event

**Preconditions:**

1. Event list must have at least one event.


**MSS**

1. User lists all events [UC-7](#use-case-7-view-all-events).
2. System shows a list of events.
3. User selects an event to delete.
4. User confirms their intention and the event is deleted.
5. User is given feedback that the event is deleted successfully.
6. User no longer sees the event in the event list.

    Use case ends.

**Extensions**

* 3a. The given index is invalid.

    * 3a1. System shows an error message.

      Use case resumes at step 3.


* 4a. If the User cancels deleting the event, the event is kept and the use case ends.

* *a. If the user exits the application without confirming, the event is kept and the use case ends.

### Use Case 7: View All Events
**MSS**

1. User lists all events.
2. The system displays a list of all events.
3. User can scroll through the list to see all the events listed.
4. User can click on an event to view more details.

    Use case ends

### Use case 8: Update event information

**MSS**

1. User views all events [UC-7](#use-case-7-view-all-events).
2. User selects event to edit.
3. User changes the relevant event details.
4. User saves the information.

    Use case ends.

**Extensions**

* 2a. The given index is invalid.

    * 2a1. System shows an error message.

      Use case resumes at step 2.


* 3a. User does not change event details.

    Use case resumes at step 2.


* 3b. The changed details are invalid.

    * 3b1. System shows an error message.

        Use case resumes at step 3.

* 4a. The edited event causes duplicate events.

    * 4a1. System shows an error message.

      Use case resumes at step 3.

* *a At any time, User chooses to cancel the edit.


### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch & Shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file 
      * **Expected:** Shows the GUI with a set of sample contacts and events. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
      * **Expected:** The most recent window size and location is retained.



### Listing all persons

1. List all persons

    1. Test case: `list -p` (must be an exact match)
        Expected: `Listed all person` is shown in the status message. Tabs switched to Contacts.

### Adding a person

1. Adding a person

    1. Prerequisites: List all persons using the `list -p` command. Multiple persons in the list.

    1. Test case: `add -n John Doe -p 98765432 -e johnd@example.com -rs Brother`<br>
       Expected: Person with name `John Doe` with phone number `98765432`, email `johnd@example.com` and relationship of `Brother` is added. `New person added: John Doe; Phone: 98765432; Email: johnd@example.com; Relationship: Brother` is shown in the status message.

    1. Test case: `add`<br>
       Expected: No person is added to the contact. Error details shown in the status message. Status bar remains the same.

    1. Test case: `add 0` <br>
       Expected: No person is added to the contact. Error details shown in the status message. Status bar remains the same.

   1. Test case (Invalid name): `add -n John_Doe -p 98765432 -e johnd@example.com -rs Brother` <br>
      Expected: No person is added to the contact. Error details shown in the status message. Status bar remains the same.

   1. Test case (Invalid phone number): `add -n John Doe -p 1 -e johnd@example.com -rs Brother` <br>
      Expected: No person is added to the contact. Error details shown in the status message. Status bar remains the same.
   2. Test case (Invalid phone number): `add -n John Doe -p +6512345678 -e johnd@example.com -rs Brother` <br>
      Expected: No person is added to the contact. Error details shown in the status message. Status bar remains the same.

      1. Test case (Invalid email): `add -n John Doe -p 98765432 -e johndexample -rs Brother` <br>
         Expected: No person is added to the contact. Error details shown in the status message. Status bar remains the same.

      1. Test case (Invalid relationship): `add -n John Doe -p 98765432 -e johnd@example.com -rs Brother333` <br>
         Expected: No person is added to the contact. Error details shown in the status message. Status bar remains the same.

      1. Test case (Multiple values for fields): `add -n John Doe -p 98765432 -e johnd@example.com -rs Brother -rs Brother` <br>
         Expected: No person is added to the contact. Error details shown in the status message. Status bar remains the same.

      1. Test case (Missing fields): `add -n John Doe -e johnd@example.com -rs Brother` <br>
         Expected: No person is added to the contact. Error details shown in the status message. Status bar remains the same.

2. Adding a duplicate person

    1. Prerequisites: List all persons using the `list -p` command. Person with name `John Doe` with phone number `98765432`, email `johnd@example.com` and relationship of `Brother` already exists.

    1. Test case: `add -n John Doe -p 98765432 -e johnd@example.com -rs Brother`
        Expected: `This person already exists in the address book` is displayed.

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list -p` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First person is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

### Editing a person

1. Editing a person, where result does not cause duplicate persons.

    1. Prerequisites: List all persons using the `list -p` command. Multiple persons in the list.

    1. Test case: `edit 1 -n Bernice Tan`<br>
       Expected: The name of the first person is edited to `Bernice Tan`. Details of the added person shown in the status message.

   1. Test case: `edit 1 -p 123123123`<br>
      Expected: The phone number of the first person is edited to `123123123`. Details of the added person shown in the status message.

   1. Test case: `edit 1 -e johnd@example.com`<br>
      Expected: The email of the first person is edited to `johnd@example.com`. Details of the added person shown in the status message.

   1. Test case: `edit 1 -rs Sister`<br>
      Expected: The name of the first person is edited to `Sister`. Details of the added person shown in the status message. Timestamp in the status bar is updated.

   1. Other correct edit commands to try (more than 1 field edited): `edit 1 -p 12345678 -rs Sister` <br>
       Expected: The phone number and relationship of the first person is edited to `12345678` and `Sister`. Details of the added person shown in the status message.

   1. Test case (No fields provided): `edit 1`<br>
      Expected: No person is edited in the contact. Error details shown in the status message. Status bar remains the same.

   1. Test case (Multiple values for fields): `edit 1 -n John -n Doe`<br>
      Expected: No person is edited in the contact. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect add commands to try: `edit`, `edit x` (where x is larger than the list size), <br>
      Expected: No person is edited in the contact. Error details shown in the status message. Status bar remains the same.

2. Editing a person, where result causes duplicate persons.

   1. Prerequisites: `add -n Johnny Doe -p 98765432 -e johnd@example.com -rs Brother` followed by `add -n John Doe -p 98765432 -e johnd@example.com -rs Brother`

    1. Test case: `edit 1 -n John Doe`<br>
       Expected: `This person already exists in the address book.` is shown in the status message.

    1. Other test cases to try: editing 1 or more fields to cause a person to have the same `name`, `phone number`, `email` and `relationship` as another person in the list.
       Expected: `This person already exists in the address book.` is shown in the status message.

### Listing all events

1. Listing all events

   1. Test case: `list -e` (must be an exact match)
        Expected: `Listed all events` is shown in the status message. Tabs switched to Events.

### Adding an event

1. Adding an event

    1. Prerequisites: List all events using the `list -e` command. Multiple events in the list. If adding attendees, attendees must exist in the address book.

    1. Test case (No attendees): `event -n Study -sd 2025-01-01 -ed 2025-01-01 -l School`<br>
       Expected: Event with name `Study` with start date `Jan 01 2025`, end date `Jan 01 2025`, location `School` is added. `New event added: Study; Date: 2025-01-01 - 2025-01-01; Location: School; No Attendees.` is shown in the status message.

    1. Test case: `event`<br>
       Expected: No event is added to the contact. Error details shown in the status message. Status bar remains the same.

    1. Test case: `event -n abc` <br>
       Expected: No event is added to the contact. Error details shown in the status message. Status bar remains the same.

    1. Test case (Invalid dates): `event -n Study -sd 2030-01-01 -ed 2025-01-01 -l School` <br>
       Expected: No event is added to the contact. Error details shown in the status message. Status bar remains the same.

    1. Test case (Invalid attendees): `event -n Study -sd 2025-01-01 -ed 2025-01-01 -l School -a -10` <br>
       Expected: No event is added to the contact. Error details shown in the status message. Status bar remains the same.
   
    1. Test case (Multiple values for fields): `event -n Study -n Event -sd 2025-01-01 -ed 2025-01-01 -l School` <br>
       Expected: No event is added to the contact. Error details shown in the status message. Status bar remains the same.

    1. Test case (Missing fields): `event -n Study -sd 2025-01-01 -l School` <br>
       Expected: No event is added to the contact. Error details shown in the status message. Status bar remains the same.

2. Adding a duplicate event

    1. Prerequisites: List all events using the `list -e` command. Event with name `Study` with start date `Jan 01 2025`, end date `Jan 01 2025`, location `School` already exists.

    1. Test case: `event -n Study -sd 2025-01-01 -ed 2025-01-01 -l School`
       Expected: `This event already exists in the event book` is displayed.

### Saving data

**Dealing with missing/corrupted data files**

1. **Scenario:** Missing data files (i.e. `addressbook.json` and `eventbook.json`).
   * This can be simulated by deleting the data files.
   * **Expected Behavior:** LegacyLink starts with the sample contacts and sample event, with the new data files being automatically created.
2. **Scenario:** Corrupted data files (i.e. `addressbook.json` and `eventbook.json`).
   * This can be simulated by modifying the data files to contain malformed data.
   * **Expected Behavior:** LegacyLink starts with empty event and contact books.


## **Appendix: Effort**

### Difficulty Level
Compared to AddressBook 3 (AB3), LegacyLink introduces events creation and management. Our project integrates both Person and Event, each with unique attributes and interdependencies, which requires more complex data models and more flexible UI components to them. 

### Challenges Faced
1. **UI Flexibility:** We required a UI that could dynamically adapt based on the results of user commands, allowing for contextually relevant displays that update in real time. For instance, when updating the events attended by a given person, we had to ensure that the updated details are also reflected on the detailed view of the person. This required a deeper understanding of JavaFX’s features, such as dynamic pane switching, conditional rendering, and event-driven updates, which many of us are unfamiliar with.

2. **Brownfield development**: Since AB3 is already a functional product with a range of features, we had to understand the codebase and build upon it. This means that all of us have to have a good understanding of each component and its dependencies which adds a layer of complexity. Furthermore, we also had to refactor parts of the code in order to make our system accommodate different entity types.

### Effort Required
1. **UI Design for CLI and GUI Integration:** This integration was challenging as it required the UI to respond dynamically to command inputs, display error messages, and present updated detailed views for both persons and events.

2. **Multi-Entity Management:** Expanding from a single-entity system to a dual-entity system required restructuring the codebase to accommodate flexible handling and interactions between entities.

3. **Documentation:** Comprehensive user guide and developer guide for all commands, data handling procedures, and error cases to assist users and developers.

### Achievements of the Project
1. **Comprehensive Family Contact and Event Manager:** LegacyLink provides users with an all-in-one solution to manage both family contacts and family events. Its unique CLI + GUI interface allows users to manage contacts and events efficiently with keyboard-based commands while benefiting from a visual interface for clarity.

2. **Clear and User-Friendly Documentation:** LegacyLink’s comprehensive guide covers every feature, command, and tips, supporting users of all technical levels.


### Reuse and Effort Savings
1. **Data Storage**: We used the same storage component to handle storing of Person and Event into a JSON file.


## **Appendix: Planned Enhancements**

Team size: 5

1. **Support for events to have identical fields**: Allow events A and B to have identical fields with A's attendees being a subset of B's attendees or vice-versa, this will allow more flexibility regarding events the user wishes to add.
<br/>
<br/>
2. **Modify commands to have consistent formatting**: Currently commands like cancel and delete have inconsistent formatting, as they require an index parameter but do not have the index flag. We can perhaps force all commands to have the index flag to ensure more consistent formatting and less confusion for users.
<br/>
<br/>
3. **Generate more helpful error messages**: In some instances the error messages are not informative enough, examples include:
   <br/>
   <br/>
   a) The `update` command for attendees when used with an invalid index just returns a message saying "Attendee index is invalid" instead of showing exactly which index is invalid.<br/>It also shows the error message "This event already exists in the event book" instead of a more helpful message such as "No changes were made".

   b) The `edit` command when used with negative indices, 0 or integer overflow shows 'invalid command' instead of 'invalid index'.
   <br/>
   <br/>
   We plan to improve this by providing more information to the user on where they went wrong (e.g by showing the exact index that is invalid).
<br/>
<br/>
4.  **Support for Special Characters in Contact Names**: The current address book functionality does not support certain special characters, such as "d/o" (daughter of). We plan to enhance the address book to allow the use of special characters in person names to better accommodate a wider range of user inputs. This will ensure that users from diverse cultural backgrounds, can accurately input and save their names without encountering errors.
<br/>
<br/>
5. **Support for recurring events**: The current event book is not able to account for recurring events (e.g: Weekly tuition). We plan to enhance the event book to accommodate for recurring events, ensuring that users who wish to use the app as a daily/weekly schedule can do so with ease.
<br/>
<br/>
6. **Error Handling for Invalid Contact Names in Search**: Currently, when a user attempts to find a contact using an invalid name (e.g., one containing special characters), the search is executed without displaying an error message. This can lead to confusion as no feedback is provided regarding the invalid input. We plan to improve this behavior by implementing a validation check for invalid contact names during the search operation. If a user attempts to search with an invalid name (such as "find -/Bob"), an error message will be displayed to inform the user that the name contains invalid characters and the list will not be filtered.
<br/>
<br/>
7. **Sort the Attendee list in Event view**: Currently, an event in the event detail view does not show its attendees in alphabetically sorted order, this can lead to confusion for users as they find it harder to navigate the list of attendees to find whether some target person to attending the event or not. We plan to improve this by sorting the list of attendees in alphabetically increasing order under the Event detail view.
<br/>
<br/>
8. **Support for deleting multiple indices at a time**: Currently, users may only delete one index at a time, which can be too inefficient if the user wishes to delete a large number of indices. We plan to improve this by implementing an extended version of delete in the following way:
   <br/>
   <br/>
   a. Single index deleting - delete 1
  
   b. Multiple index deleting - delete 1 2

   c. Range index deleting - delete 1-10
  
   d. Combination deleting - delete 1 2 3-10
<br/>
<br/>
9. **Support for event start and end time in addition to just the date**: Currently Events only store the start date and end date using day, month and year. This can pose a problem for users who wish to also know what time in that date the events starts/ends. We plan to improve this by extending Events to also optionally store a start time and an end time.
<br/>
<br/>
10. **Support for non-digit characters in phone numbers**: Currently, users are not able to store non-digit characters in phone numbers which can pose a problem as sometimes people may use the '+' character to denote a country code, or even '-' characters in the phone number. Users may also want to add additional information after the phone number, e.g: 1234-5678 999 (HP) where the '(Home Number)' indicates that the phone number is a home number and not a personal handphone number. We plan to improve this by allowing users to include non-digit characters in a person's phone number field.
