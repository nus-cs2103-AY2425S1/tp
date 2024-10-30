---
layout: default.md
title: "Developer Guide"
pageNav: 3
---

# Bridal Boss Developer Guide

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

## Product Scope

### Target User Profile

A **wedding organizer** responsible for planning and managing weddings. They coordinate with vendors, clients (brides, grooms, and their families), and participants (guests, photographers, caterers, etc.). Their work involves juggling multiple tasks and deadlines to ensure that each wedding runs smoothly. They may need to manage multiple weddings simultaneously and prefer efficient tools that help streamline their workflow. They are comfortable using desktop applications and can type quickly, preferring typing over mouse interactions.

### Value Proposition

**Bridal Boss** enables wedding organizers to manage multiple weddings simultaneously while maintaining detailed vendor and client records. It offers fast and efficient access to information, helping organizers categorize and update contacts related to each wedding easily. By providing streamlined management of vendor contacts, client preferences, and event timelines, Bridal Boss helps wedding organizers accommodate last-minute changes effectively and keep everything up to date.

### User Stories

Priorities:

- High (must have) - `* * *`
- Medium (nice to have) - `* *`
- Low (unlikely to have) - `*`

| Priority | As a...                    | I want to...                                       | So that I can...                                                        |
|----------|----------------------------|----------------------------------------------------|-------------------------------------------------------------------------|
| `* * *`  | Wedding organizer          | create separate profiles for each wedding          | manage multiple weddings without confusion                              |
| `* * *`  | Wedding organizer          | view an overview of all ongoing weddings           | manage multiple events at once without losing track                     |
| `* * *`  | Wedding organizer          | add and categorize vendors                         | keep track of service providers for each wedding                        |
| `* * *`  | Wedding organizer          | view stakeholders related to each specific wedding | easily check their schedules                                            |
| `* * *`  | Wedding organizer          | update client preferences easily                   | accommodate last-minute changes and keep everything up to date          |
| `* * *`  | Wedding organizer          | access previous client details                     | refer back to past weddings when planning new ones                      |
| `* * *`  | Wedding organizer          | delete profiles and tags                           | remove outdated or incorrect information                                |
| `* * *`  | Wedding organizer          | quickly search for specific contacts or vendors    | access critical information without delays                              |
| `* * *`  | Wedding organizer          | filter contacts based on tags                      | retrieve contacts of stakeholders involved in a wedding or of a specific type |
| `* *`    | Wedding organizer          | archive completed weddings                         | focus on current and upcoming events without clutter                    |
| `* *`    | Wedding organizer          | track expenses for each wedding                    | manage the overall wedding budget efficiently                           |
| `* *`    | Wedding organizer          | set reminders for important tasks or deadlines     | ensure critical milestones are not missed                               |
| `* *`    | Wedding organizer          | track RSVPs from clients                           | keep an accurate guest count for each wedding                           |
| `* *`    | Wedding organizer          | generate reports on completed tasks                | review progress and share updates with clients                          |
| `* *`    | Wedding organizer          | assign tasks to team members                       | delegate responsibilities and track progress efficiently                |
| `*`      | Wedding organizer          | upload important documents (e.g., contracts)       | access them quickly during planning                                     |
| `*`      | Wedding organizer          | send automated reminders to vendors and clients    | ensure they stay informed of upcoming deadlines                         |
| `*`      | Wedding organizer          | set up recurring tasks for common preparations     | avoid manually creating the same tasks for each event                   |

### Use Cases

(For all use cases below, the **System** is *Bridal Boss* and the **Actor** is the *Wedding Organizer*.)

#### **Use Case: Add a New Vendor to a Wedding**

**MSS:**

1. Wedding Organizer selects the wedding they are managing.
2. Bridal Boss displays the details of the selected wedding.
3. Wedding Organizer requests to add a new vendor.
4. Bridal Boss prompts for vendor details.
5. Wedding Organizer enters the vendor's details (e.g., name, contact information, services provided).
6. Bridal Boss saves the vendor information and associates it with the selected wedding.
7. Bridal Boss confirms that the vendor has been added.

   Use case ends.

**Extensions:**

- **4a.** Wedding Organizer cancels the operation.
    - **4a1.** Bridal Boss returns to the wedding details view.
        - Use case ends.

- **5a.** Wedding Organizer enters incomplete or invalid vendor details.
    - **5a1.** Bridal Boss displays an error message indicating the problem.
    - **5a2.** Wedding Organizer re-enters the vendor's details.
        - Steps 5a1-5a2 are repeated until the data entered are valid.
        - Use case resumes from step 6.

#### **Use Case: Update Client Preferences**

**MSS:**

1. Wedding Organizer selects the client's wedding profile.
2. Bridal Boss displays the client's current preferences.
3. Wedding Organizer requests to update client preferences.
4. Bridal Boss prompts for the updated preferences.
5. Wedding Organizer enters the updated preferences.
6. Bridal Boss saves the updated preferences.
7. Bridal Boss confirms that the preferences have been updated.

   Use case ends.

**Extensions:**

- **4a.** Wedding Organizer cancels the operation.
    - **4a1.** Bridal Boss returns to the client's wedding profile without making changes.
        - Use case ends.

- **5a.** Wedding Organizer enters invalid preference data.
    - **5a1.** Bridal Boss displays an error message indicating the issue.
    - **5a2.** Wedding Organizer re-enters the updated preferences.
        - Steps 5a1-5a2 are repeated until the data entered are valid.
        - Use case resumes from step 6.

#### **Use Case: Filter Contacts by Tags**

**MSS:**

1. Wedding Organizer requests to filter contacts by specific tag(s).
2. Bridal Boss prompts for the tag(s) to filter by.
3. Wedding Organizer enters the desired tag(s), prefixed with t/ (e.g., "t/Florist", "t/Wedding A").
4. Bridal Boss displays a list of contacts that match the specified tag(s).

   Use case ends.

**Extensions:**

- **1a.** Wedding Organizer provides multiple tags to filter by.
    - Bridal Boss will display contacts that match all specified tags.
        - Use case resumes from step 4.

- **3a.** No contacts match the specified tag(s).
    - **3a1.** Bridal Boss displays a message indicating that no contacts were found.
        - Use case ends.

- **3b.** Wedding Organizer enters an invalid or nonexistent tag.
    - **3b1.** Bridal Boss displays an error message indicating the tag is invalid.
    - **3b2.** Wedding Organizer re-enters the tag(s).
        - Steps 3b1-3b2 are repeated until valid tag(s) are entered.
        - Use case resumes from step 4.

### Non-Functional Requirements

1. **Performance Requirements:**
    - **Response Time:** The system should respond to user commands within 2 seconds under normal operating conditions (e.g., managing up to 100 weddings and 1,000 contacts). This should ensure that the system performs well for fast typists, complying with the typing-preferred constraint.
    - **Startup Time:** The system should start up and be fully usable within 5 seconds on modern machines (with at least 8GB RAM and SSD storage), ensuring it is portable and doesn't rely on any installer or external dependencies.

2. **Scalability:**
    - The system should be designed for future scalability to support new features like advanced budgeting tools, contact management enhancements, and more, while maintaining object-oriented principles.
    - The application should scale to handle up to 500 weddings and 10,000 contacts without significant performance degradation.

3. **Portability:**
    - The system must work cross-platform on Windows, macOS, and Linux running Java 17 and above, avoiding the use of platform-specific libraries or features. This ensures that it can be used by a wide range of users across different environments.
    - The product must be packaged in a single JAR file to comply with the single-file packaging constraint, avoiding external dependencies that could complicate installation or distribution.

4. **Usability:**
    - The system should offer a CLI-first experience optimized for fast typists, with a user interface that caters to wedding organizers who prefer text-based commands. The GUI should primarily provide feedback but allow command-based input to be the primary interaction method.
    - The system must comply with the CLI-first recommendation, ensuring that typing tasks (e.g., adding vendors, managing profiles) is faster than using a GUI-only interface.

5. **Data Security and Privacy:**
    - All sensitive information should be stored in a human-editable text file and should not require a DBMS, per the human-editable and no-DBMS constraints. Encryption should only be used if the system provides a simple way to decrypt and edit manually, to preserve the human-editability of the data file.
    - The system must comply with data protection regulations such as PDPA, ensuring that data is stored and processed securely. However, storing data locally in a text file should assume that the system is used in a secure, password-protected environment.

6. **Data Backup and Recovery:**
    - Users should be able to back up and restore their data easily. Since data is stored locally in human-editable text files, users should be able to create backups manually by copying the text file, ensuring minimal disruption in the event of failure.

7. **Interoperability:**
    - The system should be able to import/export data in common formats such as CSV or PDF, making it compatible with other wedding management software or services without relying on a remote server.
    - The system should not depend on external APIs, avoiding dependency on unreliable networks (following the recommendation to minimize network reliance).

8. **Reliability and Fault Tolerance:**
    - The system should have robust error handling that gives users clear feedback when errors occur.
    - In case of errors, the system should allow users to retry operations or resolve issues without losing data.

9. **Maintainability:**
    - The codebase should follow **OOP principles** and be modular to facilitate future maintenance and extension. Each component should be documented and follow best practices to ensure maintainability.
    - Incremental updates are recommended to align with the **Incremental Delivery** constraint, ensuring that the product evolves gradually rather than in big, risky jumps.

10. **Testability:**
    - The system should include unit and integration tests for at least **80%** of the codebase to ensure high test coverage. Testability must be a priority, avoiding features that make testing difficult (e.g., account-based logins, reliance on external APIs).
    - Manual testing should also be easy to perform, with clear feedback provided to testers.

11. **Documentation:**
    - Both **user documentation** and **developer documentation** must be provided. User documentation should explain how to interact with the CLI and manage wedding data, while developer documentation should detail system architecture and extensions.

12. **Compliance:**
    - The system must follow the **no-remote-server** constraint, ensuring it can operate entirely offline without dependence on a remote server.
    - Legal compliance should include industry standards for data encryption and storage, even though it stores data locally.

13. **Environmental Requirements:**
    - The system should operate efficiently across different environments, such as machines with varying processing power, ensuring that the product runs smoothly on modern systems (with at least 8GB RAM).
    - It should also work offline by default, only relying on internet access for optional features like calendar syncing, but without any dependency on continuous network access.

14. **Graphical Interface:**
    - The GUI must be designed to work on **common screen resolutions** (1920x1080 and higher) and should remain usable at lower resolutions like 1280x720 and higher, following the **screen resolution** constraints. The interface should be scalable to accommodate different screen sizes without compromising usability.

---

These non-functional requirements ensure that **Bridal Boss** remains a reliable, secure, and scalable application tailored to the needs of wedding organizers. They address critical aspects like performance, security, usability, and maintainability, ensuring the system meets both current and future needs.
### Glossary

- **Wedding Organizer:** A professional responsible for planning and managing wedding events, coordinating with clients and vendors.
- **Vendor:** A service provider involved in the wedding (e.g., florist, caterer, photographer).
- **Client:** The individuals who have hired the wedding organizer, typically the bride and groom.
- **Stakeholders:** All parties involved in the wedding event, including clients, vendors, and participants.
- **Tag:** A label assigned to contacts or events to categorize and filter them (e.g., "Florist", "Wedding A").
- **Contact:** An entry in the system containing information about a person or vendor.
- **Event Timeline:** A schedule outlining all tasks and deadlines related to a wedding event.
- **RSVP:** A confirmation from an invited guest about their attendance at the wedding.

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

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Editing a person

1. Editing a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    1. Test case: `edit 1 n/NAME`<br>
       Expected: First contact has name field edited to NAME. Details of edited contact shown in the status message. Timestamp in the status bar is updated.
   
    1. Test case: `edit Alice n/Alice Teo`<br>
       Expected (No duplicated Alice): Contact with name field containing Alice has name field edited to Alice Teo. Details of edited contact shown in the status message. Timestamp in the status bar is updated.
       Expected (Duplicated Alice): No contact edited. Person list is filtered to show only contacts with name field containing Alice. Status message shows message to input person by indexing.

### Viewing a person
1. Viewing a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    1. Test case: `view Alice` <br>
       Expected: Contacts with name field containing Alice shown. Status message shows number of contacts shown.
   
    1. Test case: `view Alice Pauline` <br>
       Expected: Contacts with name field containing Alice and Pauline shown. Status message shows number of contacts shown.

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
