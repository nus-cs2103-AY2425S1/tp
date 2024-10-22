---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

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

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("date 1 d/31/10/2024")` API call as an example.

![Interactions Inside the Logic Component for the `date 1 d/31/10/2024` Command](images/DateSequenceDiagram.png)



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

Home-based healthcare providers who
* Has a need to manage a significant number of patients information 
* Needs to see their daily schedule of patient appointments
* Needs to be reminded up upcoming appointments
* Tag patients based on allergies and other medical information
* Can type fast
* Prefers typing to mouse interactions
* Is reasonably comfortable using CLI apps
* Are based in Singapore


**Value proposition**: Our patient management system empowers home-based healthcare providers to efficiently retrieve and prioritise patient information, enabling them to provide personalized care and see their schedule for the day.


### User stories

Priorities: 
- High (must have): `***`
- Medium (nice to have) - `**`
- Low (unlikely to have) - `*`

| Priority | As a …​                        | I want to …​                                                           | So that I can…​                                                                              |
|----------|--------------------------------|------------------------------------------------------------------------|----------------------------------------------------------------------------------------------|
| `***`    | home-based healthcare provider | add the data of new clients                                            | register new clients in the system for tracking                                              |
| `**`     | home-based healthcare provider | tag patients based on their urgency                                    | prioritise higher-risk patients                                                              |
| `**`     | home-based healthcare provider | tag a client's entry or information                                    | keep track of special instructions, preferences, medical allergies or urgency                |
| `**`     | home-based healthcare provider | store prescription                                                     | add prescription records to keep track of which medications patients should take             |
| `**`     | home-based healthcare provider | add relevant reports such as X-rays                                    | access such details for reference when explaining the conditions to patients                 |
| `**`     | home-based healthcare provider | add notes for reference during future visits                           | recall important details upon next visit                                                     |
| `*`      | home-based healthcare provider | record patients' feedback                                              | address them in future visits                                                                |
| `*`      | home-based healthcare provider | be notified of overlapping names and addresses                         | avoid duplicate client entries                                                               |
| `***`    | home-based healthcare provider | see my patients' records                                               | understand how my patient is doing                                                           |
| `***`    | home-based healthcare provider | see my patients' allergies                                             | provide the correct prescription for my patients                                             |
| `***`    | home-based healthcare provider | view my patient's emergency contacts quickly                           | reach them in case of emergency                                                              |
| `*`      | home-based healthcare provider | view all clients sorted by their last visit date                       | priortise follow up visits                                                                   |
| `*`      | home-based healthcare provider | track medicine and medical equipment used for each patient's treatment | maintain an accurate log and ensure consistency in care plan                                 |
| `*`      | home-based healthcare provider | check number of visits for a particular patient in a given time period | ensure balance between patients' needs and my availability                                   |
| `***`    | home-based healthcare provider | delete the records of patients whom I am not seeing anymore            | keep my address book concise and clutter-free                                                |
| `***`    | home-based healthcare provider | remove old or inactive clients from the address book                   | keep the list relevant                                                                       |
| `***`    | home-based healthcare provider | edit the details of my clients' address                                | locate a person easily                                                                       |
| `***`    | home-based healthcare provider | have the contact details of my patients for easy access                | update the details accordingly if there are any changes                                      |
| `***`    | home-based healthcare provider | add new appointment details                                            | add appointments in my schedule for tracking later on                                        |
| `***`    | home-based healthcare provider | see my schedule for the day                                            | organise my time and ensure that there are no clashes in appointments                        |
| `**`     | home-based healthcare provider | be reminded of my clients' appointments nearer to the date             | organise my time                                                                             |
| `*`      | home-based healthcare provider | generate the route for the day                                         | efficiently travel to different locations and save time                                      |
| `*`      | home-based healthcare provider | send notifications to patients                                         | they can expect my arrival                                                                   |
| `*`      | home-based healthcare provider | schedule recurring visits for clients directly in the address book     | avoid re-entering their information each time                                                |
| `*`      | home-based healthcare provider | check last month's payment and visits summary                          | track my workload                                                                            |
| `*`      | home-based healthcare provider | group patients according to patient and priority                       | save travel time or focus on more urgent cases                                               |
| `*`      | home-based healthcare provider | get notifications in the event an emergency occurs                     | respond as quickly as possible and know whether there is a need to go to the patient's house |
| `**`     | home-based healthcare provider | see the services provided for each patient                             | know how much I should be charging my patients                                               |
| `**`     | home-based healthcare provider | see which of my patients have paid                                     | keep track of how much my patients owe me                                                    |
| `**`     | home-based healthcare provider | record when my patients pay for their appointment                      | keep track of which patients have paid for my services                                                                     
| `**`     | home-based healthcare provider | show insurance details for patients                                    | generate patient's bill accordingly

### Use cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Add a client**

**MSS**

1.  User requests to add a client and provides the required client details.
2.  AddressBook validates the input 
3.  AddressBook adds the client data.
4.  AddressBook confirms the successful addition of the client.

    Use case ends.

**Extensions**

* 2a.  Invalid Input Format
    * 2a1. AddressBook shows an error message.

  Use case ends.

* 2b. Duplicate Client

    * 2b1. AddressBook shows an error message.
      
  Use case ends.


**Use case: Delete client data**

**MSS**

1.  User requests to list clients
2.  AddressBook shows a list of clients
3.  User requests to delete a client’s data and inputs the required client details.
4.  AddressBook validates the input
5.  AddressBook deletes the person

    Use case ends.

**Extensions**

* 4a.  Invalid Input Format
    * 4a1. AddressBook shows an error message.

  Use case ends.

* 4b. Duplicate Client

    * 4b1. AddressBook shows an error message.

  Use case ends.

* 4c. Information mismatch

    * 4c1. AddressBook shows an error message.

  Use case ends.

**Use case: Record Client Payment**

**MSS**

1.  User requests to list clients
2.  AddressBook shows a list of clients
3.  User requests to record a payment by providing the client’s name, phone number, and amount paid.
4.  AddressBook validates the input.
5.  AddressBook records the payment.

    Use case ends.

**Extensions**

* 4a.  Invalid Input Format
    * 4a1. AddressBook shows an error message.

  Use case ends.

* 4b.  Name and Phone Number mismatch
    * 4b1. AddressBook shows an error message.

  Use case ends.

**Use case: Tag a client’s priority**

**MSS**

1.  User requests to list clients
2.  AddressBook shows a list of clients
3.  User requests to tag a specific person and specifies their name, number and the tag.
4.  AddressBook validates the input.
5.  AddressBook tags the client's priority.

    Use case ends.

**Extensions**

* 4a.  Invalid Input Format
    * 4a1. AddressBook shows an error message.

  Use case ends.

* 4b.  Name and Phone Number mismatch
    * 4b1. AddressBook shows an error message.

  Use case ends.

**Use case: Show schedule for the day**

**MSS**

1.  User requests to see the schedule for the day and inputs the date.
2.  AddressBook validates the input.
3.  AddressBook shows the schedule for the day.

    Use case ends.

**Extensions**

* 2a.  Invalid Input Format
    * 2a1. AddressBook shows an error message.

  Use case ends.

*{More to be added}*

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage. 
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse. 
4. The system should respond within 5 seconds for any commands given.
5. Command syntax should be consistent throughout the application to minimize the learning curve.
6. Users should be able to run the application from the downloaded JAR file without an additional installer.
7. The system should provide meaningful, yet concise error messages for the user to easily make the necessary changes.
8. The codebase should be modular to facilitate easy maintenance and future enhancements.
9. The code should be well-documented with clear comments to assist future developers.
10. The application should have a smooth user experience that is intuitive and easy to use.
11. The system should be customised for operations by a single user and need not handle multiple user-access.
12. Configuration and data files should use standard formats to ensure compatibility with text editors and other applications.
13. The system should validate all user inputs to prevent errors when saving the information.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Patient Record**: A collection of patient's personal and medical information. This includes, but is not limited to, name, contact number, email, address, allergies, injuries sustained.
* **Appointment**: A scheduled session between the healthcare provider and patient for medical consultation or treatment. This is marked in the AddressBook by the time, location and patient.
* **Schedule**: A list of all patients' appointments, displaying the date and time and location of the appointments.
* **Tag**: A label applied to a patient record, used to categorise and highlight specific medical information, such as allergies or conditions.
* **Medical History**: Documentation of patient's past illnesses, treatments, surgeries and other medical related information.
* **Inactive Client**: A client who is no longer receiving care or whose records have not been accessed in a significant amount of time
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

1. _{ more test cases …​ }_

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
