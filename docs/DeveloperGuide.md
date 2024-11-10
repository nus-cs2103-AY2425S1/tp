---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# AB-3 Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

<!-- Acknowledgements -->
## **Acknowledgements**

This project is based on AddressBook-Level3 created by the [SE-EDU initiative](https://se-education.org/).
The appendixes are referenced from [AY2425S1-CS2103T-W12-2](https://ay2425s1-cs2103t-w12-2.github.io/tp/DeveloperGuide.html).

Libraries used: [JavaFX](https://openjfx.io/), [Jackson](https://github.com/FasterXML/jackson), [JUnit5](https://github.com/junit-team/junit5)

--------------------------------------------------------------------------------------------------------------------

<!-- Setting up, getting started -->
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

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete p 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point).

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

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete p 1")` API call as an example.

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

* User is a event organiser / planner
* has a need to manage a significant number of contacts
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**:

Eventory is an application that allows you to plan events easily, whether you’re planning by yourself or collaborating with others. With Eventory, you’ll keep crucial details always at your fingertips and ensure that everyone is on the same page.

Here are some of the features of Eventory:

* **Add**, **Edit**, and **Delete** contacts and events
* **Find** contacts and events by name and tag
* **Link** contacts to events

By enhancing management and data processing, Eventory reduces stress and helps you execute events smoothly and efficiently.

### User stories
Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`
#### Important Note:
* Not all features in the user stories have been implemented
* For features that have been implemented, they may not work exactly as described in user stories
* Features are not implemented strictly based on priority

| Priority | As a …​                                          | I want to …​                                               | So that I can…​                                                            |
|----------|--------------------------------------------------|------------------------------------------------------------|----------------------------------------------------------------------------|
| `* * *`  | new user                                         | see a list of commands                                     | quickly use it as reference                                                |
| `* * *`  | meticulous planner                               | add, edit, and delete contacts                             | maintain only a list of essential contacts                                 |
| `* * *`  | efficiency-focused user                          | search by name                                             | save time in looking for specific contacts                                 |
| `* * *`  | frequent user                                    | save and load all my data                                  | use the application across multiple sessions                               |
| `* *`    | easily overwhelmed planner                       | see priorities of work to be done                          | better manage my time                                                      |
| `* *`    | team member                                      | share contacts with others                                 | work with others more effectively                                          |
| `* *`    | frequent user                                    | import and export contacts                                 | migrate between working platforms                                          |
| `* *`    | detail-oriented planner                          | add custom notes to each contact                           | keep track of specific details                                             |
| `*`      | tech-savvy user                                  | use keyboard shortcuts                                     | achieve my goals more efficiently                                          |
| `*`      | impatient person                                 | easily use commands                                        | do my work quickly and without frustration                                 |
| `*`      | user with many clients                           | share my schedule                                          | share my availability with clients                                         |
| `*`      | bilingual user                                   | translate notes                                            | work in different languages with different clients                         |
| `*`      | event planner                                    | see who is in charge of a venue                            | quickly contact them for bookings                                          |
| `*`      | planning supervisor                              | set permissions for team members                           | so that we can collaborate at any level                                    |
| `*`      | planner of multiple events                       | separate contacts based on event                           | contact relevant people more quickly                                       |
| `*`      | event planner                                    | see contact's occupations                                  | know who may be relevant to my event                                       |
| `*`      | safety-conscious event organiser                 | store emergency contact details for team members           | quickly reach them in case of an emergency                                 |
| `*`      | large event organiser                            | send bulk messages to multiple contacts at once            | save time when sending updates or reminders                                |
| `*`      | organiser                                        | schedule messages in advance                               | send reminders to vendors                                                  |
| `*`      | busy planner                                     | receive delivery confirmations and read receipts           | be sure my contacts have received important information                    |
| `*`      | detailed planner                                 | assign tasks to individual contacts                        | know who is responsible for each task                                      |
| `*`      | event organiser                                  | track the history of events a contact has been involved in | have a reference of their past contributions                               |
| `*`      | organiser with many contacts                     | sort contact by their latest interaction or by tags        | quickly access the most relevant contacts                                  |
| `* `     | frequent event organiser                         | view available venues for hosting my event                 | save time searching for suitable venues                                    |
| `*`      | event planner                                    | view contacts on a map                                     | plan location based events                                                 |
| `*`      | large scale event planner                        | create relationship mappings between contacts              | understand and leverage connections within my network                      |
| `*`      | planner who does not check the application often | customise alerts and notifications                         | differentiate between notifications easily                                 |
| `*`      | long time event organiser                        | archive inactive contacts without deleting them            | maintain a record of past interactions while keeping my active list clean. |

### Use Cases

(For all use cases below, the **System** is the `Eventory` and the **Actor** is the `user`, unless specified otherwise)

---

#### **Use Case 1: Add a person or event**

**Main Success Scenario (MSS)**

1. User requests to add a person or event.
2. Eventory adds the person or event.

    *Use case ends.*

**Extensions**

- **2a.** The given format is invalid.
    - **2a1.** Eventory shows an error message.
      *Use case resumes at step 1.*

  *Use case ends.*

---

#### **Use Case 2: Edit a person or event**

**Main Success Scenario (MSS)**

1. User requests to list persons and events.
2. Eventory shows a list of persons and events.
3. User requests to edit a person or event.
4. Eventory edits the person or event.

   *Use case ends.*

**Extensions**

- **2a.** The list is empty.

  *Use case ends.*

- **3a.** The given index is invalid.
    - **3a1.** Eventory shows an error message.
      *Use case resumes at step 2.*

  *Use case ends.*

---

#### **Use Case 3: Delete a person or event**

**Main Success Scenario (MSS)**

1. User requests to list persons and events.
2. Eventory shows a list of persons and events.
3. User requests to delete a specific person or event in the list.
4. Eventory deletes the person or event.

   *Use case ends.*

**Extensions**

- **2a.** The list is empty.
  *Use case ends.*

- **3a.** The given index is invalid.
    - **3a1.** Eventory shows an error message.
      *Use case resumes at step 2.*

  *Use case ends.*

---

#### **Use Case 4: Find a person or event by name**

**Main Success Scenario (MSS)**

1. User requests to find a person or event by name.
2. Eventory returns a list of relevant people and events.

   *Use case ends.*

**Extensions**

- **2a.** The list is empty.

  *Use case ends.*

---

#### **Use Case 5: Find a person or event by tag**

**Main Success Scenario (MSS)**

1. User requests to find a person or event by tag.
2. Eventory returns a list of relevant people and events.

   *Use case ends.*

**Extensions**

- **2a.** The list is empty.

  *Use case ends.*

---

#### **Use Case 6: View events happening in the schedule**

**Main Success Scenario (MSS)**

1. User requests to see their schedule.
2. Eventory returns a list of events happening in a time period.

   *Use case ends.*

**Extensions**

- **2a.** The list is empty.

  *Use case ends.*

---

#### **Use Case 7: Link person to event**

**Main Success Scenario (MSS)**

1. User requests to link person to event.
2. Eventory does the linking.

   *Use case ends.*

**Extensions**

- **2a.** The given index is invalid.
    - **2a1.** Eventory shows an error message.
      *Use case resumes at step 1.*

- **2b.** The event does not exist.
    - **2b1.** Eventory shows an error message.
      *Use case resumes at step 1.*

  *Use case ends.*

---

#### **Use Case 8: Unlink person to event**

**Main Success Scenario (MSS)**

1. User requests to unlink person from event.
2. Eventory does the unlinking.

   *Use case ends.*

**Extensions**

- **2a.** The given index is invalid.
    - **2a1.** Eventory shows an error message.
      *Use case resumes at step 1.*

- **2b.** The event does not exist.
    - **2b1.** Eventory shows an error message.
      *Use case resumes at step 1.*

  *Use case ends.*

---

#### **Use Case 9: Clear Eventory**

**Main Success Scenario (MSS)**

1. User requests to clear data in Eventory by specifying one of the following options:
    - Clear all contacts and events.
    - Clear only contacts.
    - Clear only events.
2. Eventory prompts the user with a confirmation message.
3. User confirms the action:
    - If the user confirms, Eventory executes the clear command and removes the selected data (contacts, events, or both) from memory.
    - If the user declines, Eventory aborts the clear command without removing any data.

    *Use case ends.*

---

#### **Use Case 10: List contacts and events**

**Main Success Scenario (MSS)**

1. User requests for list of contacts and events in Eventory.
2. Eventory shows all contacts and events.

    *Use case ends.*

**Extensions**

- **2a.** The list is empty.

    *Use case ends.*

---

#### **Use Case 11: Request for help**

**Main Success Scenario (MSS)**

1. User requests help in Eventory.
2. Eventory displays all commands.

   *Use case ends.*

---

#### **Use Case 12: Exit programme**

**Main Success Scenario (MSS)**

1. User exits the programme.
2. Eventory closes.

   *Use case ends.*

---

### Non-Functional Requirements

**1. Domain Rules**
- 1a. `Essential` The number of contacts in a single event should not exceed 1000, ensuring efficient management of contacts for each event.

**2. Technical Requirements**
- 2a. `Essential` Must be compatible with Java 17 or higher, ensuring that the application runs on modern environments.
- 2b. `Typical` The system should work on both 32-bit and 64-bit environments, making it accessible to a broader range of users.
- 2c. `Novel` The system must support running offline and sync data when the internet is available, allowing event planners to manage contacts without internet access.

**3. Performance Requirements**
- 3a. `Essential` The system should respond to user input within 2 seconds under normal load conditions, providing a fast and efficient user experience for frequent tasks.
- 3b. `Typical` Event-based contact searches should return results within 3 seconds for up to 1000 contacts, facilitating quick access to relevant information.
- 3c. `Typical` Bulk messages up to 500 contacts should be queued for delivery within 5 seconds, enabling event organizers to send updates quickly.

**4. Quality Requirements**
- 4a. `Essential` The system should be usable by event planners with no prior experience in using CLI applications, ensuring accessibility for all users.
- 4b. `Typical` All command-line options should have detailed help documentation accessible from within the application, providing guidance for users.
- 4c. `Novel` The interface must support fast-typing users, minimizing mouse interactions to enhance productivity.

**5. Project Scope**
- 5a. `Typical` The product is not required to handle the printing of physical contact lists, focusing instead on digital management.
- 5b. `Novel` Integration with third-party calendar apps (e.g. Google Calendar) is planned for future versions but is out of scope for this release, allowing for future enhancements.

**6. Others**
- 6a. `Essential` The system should avoid any discriminatory language or culturally sensitive imagery in user messages or templates, ensuring a respectful environment for all users.
- 6b. `Novel` The contact mapping feature should not use any personal data without user consent, ensuring compliance with privacy regulations like GDPR.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, macOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **MSS:** Also known as Main Success Story. It is the scenario that a user should abide by when using the programme
* **API:** Also known as Application Programming Interface. It is the set of rules that allow different software to communicate with each other.

---

### Requirements yet to be implemented

**1. Keyboard Shortcut for Quick View Switching**
- Currently, the app requires users to rely on GUI mouse clicks to switch between "Contacts" and "Events" views, which slows down navigation in a CLI environment. 
- To improve the efficiency of the interface, a keyboard shortcut or CLI command should be added to allow seamless switching between these views. 
- This will enhance the CLI-friendliness of the app and speed up user navigation.

**2. Automated Communication for Event Contacts**
- Currently, there is no option for users to send messages directly to contacts linked to an event, making communication difficult when managing events. 
- A messaging feature should be implemented to allow users to send bulk messages to all contacts associated with a particular event. 
- Additionally, the feature should support both instant and scheduled messaging, so users can plan communications in advance and keep participants informed easily.

**3. Batch Import and Export for Contacts and Events**
- Currently, the system does not support batch import or export of contacts and events, limiting its scalability for users who need to manage large data sets. 
- A feature should be introduced to allow users to import and export contacts and events using formats like CSV or JSON. 
- This would streamline data management and make it easier for users to transfer large quantities of data between systems.

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

    1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts and events. The window size may not be optimum.
    2. Should double-clicking the jar file not launch the application, you may try running the jar file from the command line, instructions can be found in the [User Guide](UserGuide.md) under Quick Start.
1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. Exiting the application
    1.  Click the "X" button located in the top right corner of the window or type the `exit` command to exit the application.

### Adding an event

* Add an event while all events are being shown

    1. Prerequisites: None

    1. Test case: `add e n/Tech Fair a/Suntec City s/2024-10-15 14:30 t/fun`<br>
       Expected: Event named Tech Fair is added to the list under Events. The status message shows the successful creation of the event and its details.

    1. Test case: `add e n/fail`<br>
       Expected: No event is added. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect add event commands to try: `add e`, `add e n/name s/2025-12-02 20:00`, `...`<br>
       Expected: Similar to previous.

### Deleting an event

* Deleting an event while all events are being shown

    1. Prerequisites: List all events using the `list` command. Multiple events in the list.

    1. Test case: `delete e 1`<br>
       Expected: First event is deleted from the list. Details of the deleted event shown in the status message.

    1. Test case: `delete e 0`<br>
       Expected: No event is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `delete e`, `delete e x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

### Finding an event by name

* Finding an event by name while all events are being shown

    1. Prerequisites: List all events using the `list` command. Multiple events in the list.

    1. Test case: `find e party`<br>
       Expected: All events with the word "party" in their name are displayed in the list. Status message shows how many events were found.
       The search is case-insensitive and is not partial.

    1. Test case: `find e beach barbeque`<br>
       Expected: All events with the word "party" and or "barbeque" in their name are displayed in the list. Status message shows how many events were found.

   1. Test case: `find e`<br>
      Expected: No event is found. Error details shown in the status message. Status bar remains the same.

### Searching a contact by tag

* Searching a contact while all contacts are being shown

    1. Prerequisites: List all contacts using the `list` command. Multiple contacts in the list.

    1. Test case: `search p friend`<br>
       Expected: All contacts with the word "friend" in their tags are displayed in the list. Status message shows how many contacts were found.
       The search is case-insensitive and is not partial.

    1. Test case: `search p friend colleague`<br>
       Expected: All contacts with the word "friend" and or "colleague" in their tags are displayed in the list. Status message shows how many contacts were found.

   1. Test case: `search p`<br>
      Expected: No contact is found. Error details shown in the status message. Status bar remains the same.

### Searching an event by tag

* Searching an event while all events are being shown

    1. Prerequisites: List all events using the `list` command. Multiple events in the list.

    1. Test case: `search e fun`<br>
       Expected: All events with the word "fun" in their tags are displayed in the list. Status message shows how many events were found.
       The search is case-insensitive and is not partial.

    1. Test case: `search e fun food`<br>
       Expected: All events with the word "fun" and or "food" in their tags are displayed in the list. Status message shows how many events were found.

    1. Test case: `search e`<br>
       Expected: No event is found. Error details shown in the status message. Status bar remains the same.

### Linking a contact to an event

1. Linking a contact to an event while all contacts and events are being shown

    1. Prerequisites: List all contacts and events using the `list` command. One contact and one event in the list. The event in the event list is named Company Meeting.

    1. Test case: `link 1 ev/Company Meeting`<br>
       Expected: Links the first contact to the event named Company Meeting. Details of the linked event shown in the status message.
       Status bar updates to show the event under the first contact.

    1. Test case: `link 1 ev/Not Company Meeting`<br>
       Expected: No link is made between the contact and event. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `link`, `link x ev/Company Meeting`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Unlinking a contact to an event

1. Unlinking a contact to an event while all contacts and events are being shown

    1. Prerequisites: List all contacts and events using the `list` command. One contact and one event in the list. The event in the event list is named Company Meeting and is linked to the contact.

    1. Test case: `unlink 1 ev/Company Meeting`<br>
       Expected: Unlinks the first contact to the event named Company Meeting. Details of the unlinked event shown in the status message.
       Status bar updates to remove the event under the first contact.

    1. Test case: `unlink 1 ev/Not Company Meeting`<br>
       Expected: No link is destroyed between the contact and event. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `unlink`, `unlink x ev/Company Meeting`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Effort**

### Project Scope and Difficulty
This project was significantly more demanding than the Address Book 3 (AB3) reference project due to the difficulty of
managing multiple entity types and broader scope. While AB3 focuses on a single entity type (Persons),
Eventory expands upon this through the addition of Events and linking between the two types. This increases the 
difficulty of implementation of features such as deleting due to dependencies between entities, needing a more solid
data model and careful planning surrounding the logic parts of the application.

### Challenges Faced

* **Entity Linking:** Creating relationships between the entities of Contacts and Events through the linking raised
significant issues, especially in maintaining data consistency
* **Testing and Code Coverage:** Maintaining high test coverage in such a highly abstracted and complicated system proved
challenging, adding to the workload of the project
* **Clear Communication:** Maintaining clear communication added to the difficulty of the project due to differences in
perspective, methodologies and context between each team member that could lead to misunderstandings.

### Effort Required
The project required approximately **1.4x** the effort used in AB3, largely due to the aforementioned challenges faced.

### Achievements
In spite of the difficulties, we have successfully done the following:
* Create a scalable and systemic structure that meets the needs of event planning organizations
* Attain a respectable test coverage level for key functionalities
* Implemented an intuitive and user-friendly interface that maintains the fast operation time
* Incorporated new features such as linking and searching by tag name, extending the basic features of AB3

### Reuse and Libraries
This project uses several libraries which supports critical functionality:
1.  **JUnit 5:** Utilised in unit testing, speeding up the efficiency of the testing process, improving code quality
2.  **JavaFX:** Utilised in building the Graphical User Interface (GUI), improving the time taken to implement a user interface

For example, the tests folder uses JUnit 5 heavily to conduct tests. Our work involving tests consists of a significant
part of the workload. By using JUnit 5, we are able to save an estimated **15-20%** of effort and time as compared to building
test classes from scratch.

### Conclusion
The project called for a lot of cooperation and work. Despite this, it results in a robust application
that addresses many issues faced by event planning companies. It reflects the teams strength in being able to deliver
even when there are significant challenges.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

**Team Size: 5**

The following planned enhancements address known feature flaws identified during the PE-D phase. Each enhancement
specifically describes the feature flaw and the proposed solution, providing details on how the feature will be improved.
This section lists 9 planned enhancements, adhering to the team size x 2 limit.

### 1. **Enhance Error Handling**
   1. **Feature Flaw:** Current error messages are generic, with no specifics on the issue.
   2. **Proposed Fix:** Improve the parser to be able to identify specific issues.
   3. **Expected Outcome:** Improved ease of usage for users.

### 2. **Auto-Switch Views After Modifications**
   1. **Feature Flaw:** After adding, editing, or deleting an event or contact, the view does not automatically switch to the relevant tab.
   2. **Proposed Fix:** Implement logic to automatically toggle to the appropriate view after executing an event- or contact-related command.
   3. **Expected Outcome:** Users will have immediate visual feedback after actions, reducing confusion and enhancing workflow.

### 3. **Enhanced Duplicate Handling for Contacts and Events**
1. **Feature Flaw:** Contacts with the same name are flagged as duplicates, even if they have different phone numbers or emails. Similarly, events with the same name are considered duplicates, even if they have different locations or start times.
2. **Proposed Fix:** Allow contacts with the same name to be distinguished by phone number and/or email. For events, allow different locations and/or start times for events with the same name.
3. **Expected Outcome:** This will improve organization by allowing contacts and events with the same name but different details. It will accommodate real-world scenarios more effectively.

### 4. **Case-Insensitive Matching for Names and Tags**
   1. **Feature Flaw:** Names and tags are case-sensitive, leading to issues when searching, adding, or editing entries with different cases.
   2. **Proposed Fix:** Make the handling of names and tags case-insensitive throughout the app.
   3. **Expected Outcome:** Users will have a more intuitive experience, as names and tags will be recognized regardless of letter case, minimizing errors.

### 5. **Simplified Tagging for Multiple Tags**
   1. **Feature Flaw:** Multiple tags require individual tag flags, which is cumbersome.
   2. **Proposed Fix:** Allow users to enter multiple tags with a single flag, separated by spaces (e.g., `t/tag1 tag2 tag3`).
   3. **Expected Outcome:** This will streamline the process of adding multiple tags, enhancing usability and reducing redundant typing.

### 6. **Support for Hyphenated Names**
   1. **Feature Flaw:** Names currently only accept alphanumeric characters and spaces, excluding hyphenated names.
   2. **Proposed Fix:** Update validation to allow hyphens in names, enabling entry of more varied name formats.
   3. **Expected Outcome:** This will accommodate real-world name formats, making the app more versatile for users.

### 7. **Partial Matching for Find and Search**
   1. **Feature Flaw:** The current find and search feature only supports exact word matching for names and tags.
   2. **Proposed Fix:** Extend the find and search functionality to allow partial matching within words (e.g., "sun" for "sundown race").
   3. **Expected Outcome:** This improvement will make find and search results more flexible and relevant, especially for users who may not remember the exact wording.

These planned enhancements aim to address known issues and improve the overall usability, reliability, and user experience of **Eventory**.
