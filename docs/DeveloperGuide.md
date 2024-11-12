---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# ClubConnect Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

This project was adapted from [AB-3's codebase](https://github.com/se-edu/addressbook-level3).

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

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-F09-1/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-F09-1/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
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

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-F09-1/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-F09-1/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-F09-1/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-F09-1/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

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
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).  
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-F09-1/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.  

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-F09-1/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

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

* **Alternative 2:** Individual command knows how to undo/redo by itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

This section is a list of fixes that we propose to add in the near future, to improve some of ClubConnect's known feature flaws.

1. **Allow events with duplicate names to be added**: The current `add_event` command is too restrictive as it only allows events of unique names to be added to ClubConnect's event list. However, we understand that our users often have recurring events, and such a restriction on the `add_event` command would ruin their user experience. We plan to allow events of the same name but non-overlapping duration window in the future, i.e. `Orbital Workshop from 1 Oct 2024 to 7 Oct 2024` and `Orbital Workshop from 10 Oct 2024 to 14 Oct 2024` can co-exist in ClubConnect's event list.
2. **Allow contacts / events to start with a non-alphabet**: Our app currently only allow contacts / events that start with an alphabet. However, we understand that users might want to add contacts / events that start with a non-alphabet, such as `2025 Orbital Workshop`. To improve user experience, we plan to allow contacts / events to start with any character (while still making other commands that uses indexes and names work).
3. **Display contact and event lists concurrently**: Our app currently only displays one list at a time (either contact or event list). Users would need to use `list` / `list_events` to display the contact / event list. However, we understand that this might be inconvenient for users, especially when using the `assign` and `unassign` commands, where users need to remember the index of the contact / event to use these commands using indices. To improve user experience, we plan to display the contact and event lists side-by-side, so that users do not need to type `list` / `list_events` to display the contacts / events, and make it easier to use `assign` and `unassign` commands. In addition, this would also fix the current GUI issue that we have, which shows an empty box at the bottom of the screen for contact list, and right below the status message for event list.
4. **Change EVENT_DESCRIPTION to be optional**: The current `add_event` command is designed such that the event's description is a compulsory field. However, we understand that not all events have a description to it. Some events' names are self-explanatory and do not require a description. To improve user experience, we plan to make the `EVENT_DESCRIPTION` field optional in the future to provide more convenience.
5. **Edit Event Start or End Date Independently**: Currently, the EditEventCommand requires users to specify both the start and end dates when they want to edit an event's duration. This can be restrictive if a user only needs to change one of these dates. To improve user experience, we plan to modify the EditEventCommand to allow editing the start date or end date independently. This change will involve updating the command's logic to handle partial updates to the event's duration.
6. **Display events assigned to each contact within contact details**: The current `assign` command does not cause any changes in the GUI with regard to the contact information of each person. This would make it difficult to see which events a person has been assigned to. To improve user experience, we plan to make the events that a person has been assigned to visible in the details of each contact in the GUI.
7. **Modify `search` command**: The current `search` command will return all contacts whose specified field matches any one of the keywords entered. This would make it inconvenient for users who have many contacts, and would like to do a more specific search for contacts whose specified field matches all of the keywords entered. The current `search` command also only searches one field at a time. This would make it inconvenient for user who would like to search for contacts using multiple fields at the same time. To improve user experience, we plan to support searches that can match multiple fields at the same time, or return only contacts that match with all specified keywords.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

Computing Club Committee members

* Tech-savvy leaders who organize events, manage activities, and foster community engagement.
* Connect members, sponsors, and industry partners, driving innovation and learning.

**Value proposition**: Streamline computing club's communication and organization with our address book app. Effortlessly manage member details, sponsor contacts, and event participants in one place. Enhance collaboration, boost engagement, and ensure seamless planning, all while saving time and reducing administrative hassle.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`, Exists - `EXISTS`, Not possible - `N.A.`

| Priority | As a …​                            | I want to …​                                                                              | So that I can…​                                                                     |
|----------|------------------------------------|-------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------|
| `* * *`  | Committee president                | Search contacts by multiple criteria (e.g., job title, tags)                              | Find the right contacts even if I don’t remember their names                        |
| `* * *`  | Committee president                | Filter the contacts to different types of events                                          | Easily know who to contact for specific purposes, even with multiple ongoing events |
| `* * *`  | Committee member                   | Detect and merge duplicate contacts easily                                                | Keep my address book clean and well-organized                                       |
| `* * *`  | Committee president                | Mass delete contacts                                                                      | Easily remove all contacts related to one event after it's over                     |
| `* *`    | Committee president                | Assign tasks and responsibilities to committee members                                    | Ensure all activities are covered without confusion                                 |
| `* *`    | Committee member                   | Receive notifications for upcoming meetings and events                                    | Stay informed and participate on time                                               |
| `* *`    | Events coordinator                 | Send out event reminders and notifications to members                                     | Keep everyone informed and boost engagement                                         |
| `* *`    | Committee member                   | Import contacts from a CSV file                                                           | Quickly populate the address book                                                   |
| `* *`    | Committee member                   | Export contacts to a CSV file                                                             | Share the contact list with others                                                  |
| `* *`    | Committee member                   | Customize the app's interface                                                             | Tailor the app to my preferences                                                    |
| `* *`    | Club member                        | View a list of upcoming events                                                            | Stay informed about club activities                                                 |
| `* *`    | Committee member                   | Add a new event to the calendar                                                           | Plan club activities                                                                |
| `* *`    | Committee member                   | Edit an existing event in the calendar                                                    | Update event details to reflect changes in club activities                          |
| `*`      | Committee president                | Have a blacklist of participants                                                          | Keep track of people who are not allowed to join future events                      |
| `*`      | Committee member                   | Track event attendance                                                                    | See who participated                                                                |
| `*`      | Secretary                          | Track meeting attendance                                                                  | Maintain records of who participated in club activities                             |
| `*`      | Committee member                   | View a member's participation history                                                     | Recognize active members                                                            |
| `EXISTS` | Committee member organizing events | Label each of my contacts                                                                 | I can easily mass contact sponsors / participants / organizing committee, etc       |
| `EXISTS` | Committee member                   | Add a new member to the address book                                                      | Keep track of all members in the club                                               |
| `EXISTS` | Committee president                | Delete contacts                                                                           | Avoid contacting people no longer involved with the committee                       |
| `EXISTS` | Committee president                | Keep track of every member’s contact information, e.g., phone number, email address       | Contact them during an emergency                                                    |
| `N.A.`   | Committee member                   | Password-protect sensitive contact information                                            | Ensure my contacts remain private and secure                                        |
| `N.A.`   | Communication committee member     | Log all interactions with sponsors and partners                                           | Reference past conversations and ensure nothing is overlooked                       |
| `N.A.`   | Committee member                   | Send a group email to all members                                                         | Communicate important information quickly                                           |
| `N.A.`   | Committee member                   | Set reminders for upcoming events                                                         | Ensure I don’t miss important activities                                            |
| `N.A.`   | Committee member                   | Integrate the app with my calendar                                                        | Automatically sync important events and reminders                                   |

### Use cases

(For all use cases below, the **System** is the `ClubConnect` and the **Actor** is the `User`, unless specified otherwise)

---

**Use case: UC01 - Add contact**

**MSS:**
1. User requests to add a contact.
2. App adds the contact.  
   Use case ends.

**Extensions:**
* 1a. The given name is invalid (i.e. name is empty or does not start with an alphabet).
    * 1a1. App shows an error message to tell the user that the given name is invalid.  
      Use case ends.
* 1b. The given phone number is invalid (i.e., Phone numbers should only contain numbers, and it should be at least 3 digits long).
    * 1b1. App shows an error message to tell the user that the given phone number is invalid.  
      Use case ends.
* 1c. The given contact is a duplicate of another contact in the list.
    * 1c1. App shows an error message to tell the user that the contact already exists in the list.  
      Use case ends.

---

**Use case: UC02 - Edit contact**

**MSS:**
1. User requests to edit a contact by providing the index and the parameters to be changed.
2. App changes the contact.  
   Use case ends.

**Extensions:**
* 1a. User provides an invalid contact index (i.e. non-positive index or index exceeding size of list).
    * 1a1. App shows an error message to tell the user that the contact does not exist.  
      Use case ends.
* 1b. User provides an invalid name (i.e. name is empty or does not start with an alphabet).
    * 1b1. App shows an error message to tell the user that the contact name is not valid.  
      Use case ends.
* 1c. User provides an invalid phone number (i.e. phone number is not a number or is not at least 3 digits long).
    * 1c1. App shows an error message that the phone number is not valid.  
      Use case ends.
* 1d. User provides an invalid email address (i.e. email does not follow normal email address format).
    * 1d1. App shows an error message that the email address is not valid.  
      Use case ends.
* 1e. User provides a name that is the same as another contact's name.
    * 1e1. App shows an error message to tell the user that the contact already exists in the list.  
      Use case ends.

---

**Use case: UC03 - Delete contact by index**

**MSS:**
1. User requests to list contacts.
2. App shows a list of contacts.
3. User requests to delete a specific contact by index in the displayed list.
4. App deletes the contact at the specified index.  
   Use case ends.

**Extensions:**
* 3a. The given index is invalid (i.e. non-positive index or index exceeding size of displayed list).
    * 3a1. App shows an error message to tell the user that the given index is invalid.  
      Use case ends.
* 3b. No index is provided.
    * 3b1. App shows an error message to tell the user that the command format is invalid.  
      Use case ends.

---

**Use case: UC04 - Delete contact by name**

**MSS:**
1. User requests to delete a specific contact by name in the list.
2. App deletes the contact with the specified name.  
   Use case ends.

**Extensions:**
* 1a. The given name does not exist.
    * 1a1. App shows an error message to tell the user that the given name does not exist.  
      Use case ends.
* 1b. No name is provided.
    * 1b1. App shows an error message to tell the user that the command format is invalid.  
      Use case ends.

---

**Use case: UC05 - Search for contact by criteria**

**MSS:**
1. User specifies criteria and keywords.
2. App shows a list of contacts that match the provided criteria and keywords.  
   Use case ends.

**Extensions:**
* 1a. No criteria is provided.
    * 1a1. App shows an error message to tell the user that no criteria has been provided.  
      Use case ends.
* 1b. Criteria provided does not exist.
    * 1b1. App shows an error message to tell the user that the criteria does not exist.  
      Use case ends.
* 1c. No keywords are provided.
    * 1c1. App shows an error message to tell the user that no keywords have been provided.  
      Use case ends.

---

**Use case: UC06 - Label a contact**

**MSS:**
1. User requests to label a contact with a specified tag by name or ID in the list.
2. App labels the specified contact with the specified tag.  
   Use case ends.

**Extensions:**
* 1a. The given name does not exist.
    * 1a1. App shows an error message to tell the user that the given name does not exist.  
      Use case ends.
* 1b. The given ID does not exist.
    * 1b1. App shows an error message to tell the user that the given ID does not exist.  
      Use case ends.
* 1c. The user inputs a negative integer as the ID.
    * 1c1. App shows an error message to tell the user to input a valid ID.  
      Use case ends.
* 1d. The user inputs a tag that has already been added to the specified contact.
    * 1d1. App shows an error message to tell the user that the new tag is a duplicate and would not be added to the contact.  
      Use case ends.
* 1e. There are multiple contacts with the same name.
    * 1e1. App shows an error message to tell the user that there are multiple contacts with the same name and to label by index instead.  
      Use case ends.

---

**Use case: UC07 - Mass Delete**

**MSS:**
1. User requests to mass delete contacts by providing a list of contact indices.
2. App validates the provided contact indices.
3. App deletes the contacts whose indices were provided by the user.
4. App tells the user the contacts that were successfully deleted.  
   Use case ends.

**Extensions:**
* 2a. No contact IDs provided.
    * 2a1. App shows an error message to tell the user that the given name does not exist.  
      Use case ends.
* 2b. Invalid contact index(s) provided.
    * 2b1. App shows an error message to tell the user that the contact index(s) is invalid and ask the user to provide valid contact indices.  
      Use case ends.
* 2c. Duplicate contact indices provided.
    * 2c1. App handles duplicates internally, ensuring each index is processed once.  
      Use case resumes at step 2.

---

**Use case: UC08 - Filter content by type**

**MSS:**
1. User requests to filter contacts by specifying an event type.
2. App validates the provided event type.
3. App retrieves and returns the list of contacts associated with the specified event type.
4. App logs the message indicating the number of contacts filtered.  
   Use case ends.

**Extensions:**
* 2a. Invalid event type provided.
    * 2a1. App tells the user that the event is invalid and asks the user to provide a valid event type.  
      Use case ends.
* 2b. No contacts associated with the specified event type.
    * 2b1. App returns an empty list.
    * 2b2. Logs the message "Filtered 0 contacts for event type: [eventType]."  
      Use case ends.

---

**Use case: UC09 - Add event**

**MSS:**
1. User requests to add an event by providing an event name, event description, event start date, and event end date.
2. App adds the event.  
   Use case ends.

**Extensions:**
* 1a. The given event name is invalid (i.e. name is empty or does not start with an alphabet).
    * 1a1. App shows an error message to tell the user that the given name is invalid.  
      Use case ends.
* 1b. The given event description is invalid (i.e. description is empty or consists of only whitespaces).
    * 1b1. App shows an error message to tell the user that the given description is invalid.  
      Use case ends.
* 1c. The given event duration is invalid (i.e. dates are not in the correct format `YYYY-MM-DD` or dates are not valid, e.g. `2024-02-30` or event end date is earlier than the start date).
    * 1c1. App shows an error message to tell the user that the given event dates are not valid.  
      Use case ends.
* 1d. The given event has the same name as another event in the list.
    * 1d1. App shows an error message to tell the user that the event already exists in the list.  
      Use case ends.

---

**Use case: UC10 - Delete event by index**

**MSS:**
1. User requests to list events.
2. App shows a list of events.
3. User requests to delete a specific event by index in the displayed list.
4. App deletes the event at the specified index.  
   Use case ends.

**Extensions:**
* 3a. The given index is invalid (i.e. non-positive index or index exceeding size of list).
    * 3a1. App shows an error message to tell the user that the given index is invalid.  
      Use case ends.
* 3b. No index is provided.
    * 3b1. App shows an error message to tell the user that the command format is invalid.  
      Use case ends.

---

**Use case: UC11 - Delete event by name**

**MSS:**
1. User requests to delete a specific event by name in the list.
2. App deletes the event with the specified event name.  
   Use case ends.

**Extensions:**
* 1a. The given event name does not exist.
    * 1a1. App shows an error message to tell the user that the given event name does not exist.  
      Use case ends.
* 1b. No event name is provided.
    * 1b1. App shows an error message to tell the user that the command format is invalid.  
      Use case ends.

---

**Use case: UC12 - Edit event**

**MSS:**

1. User requests to edit an existing event by providing the index of the event in the list and optionally providing new details such as an event name, event description, event duration (start and end date must be provided together).
2. App updates the event with the provided details.  
   Use case ends.

**Extensions:**
* 1a. The given index is invalid (i.e., index is out of bounds).
    * 1a1. App shows an error message to tell the user that the specified event index is invalid.  
      Use case ends.
* 1b. No changes are specified for the event. 
    * 1b1. App shows an error message to tell the user that no changes were made to the event.  
      Use case ends.
* 1c. The updated event name is invalid (i.e., name is empty or does not start with an alphabet).  
    * 1c1. App shows an error message to tell the user that the given name is invalid.  
      Use case ends.
* 1d. The updated event description is invalid (i.e., description is empty or consists of only whitespaces).
    * 1d1. App shows an error message to tell the user that the given description is invalid.  
      Use case ends.
* 1e. The updated event duration is invalid (i.e., dates are not in the correct format YYYY-MM-DD or dates are not valid, e.g. 30 Feb 2024 or event end date is earlier than the start date). 
    * 1e1. App shows an error message to tell the user that the given event dates are not valid.  
      Use case ends.
* 1f. The updated event has the same name as another event in the list. 
    * 1f1. App shows an error message to tell the user that an event with the same name already exists in the list.  
      Use case ends.

---

**Use Case: UC13 - Assign Event to Person**

**MSS:**
1. User requests to assign an event to a person by providing an event name or an event index, and a person name or a person index.
2. App assigns the specified event to the specified person.  
   Use case ends.

**Extensions:**
* 1a. The given event name does not match any of the existing events.
    * 1a1. App shows an error message to tell the user that the given event name does not exist.  
      Use case ends.
* 1b. The given event index does not exist, i.e., there are only 3 events in the displayed list, but the user inputs an index of 4.
    * 1b1. App shows an error message to tell the user that the given event index is invalid.  
      Use case ends.
* 1c. The given person name does not match any of the existing persons.
    * 1c1. App shows an error message to tell the user that the given person name does not exist.  
      Use case ends.
* 1d. The given person index does not exist, i.e., there are only 3 persons in the displayed list, but the user inputs an index of 4.
    * 1d1. App shows an error message to tell the user that the given person index is invalid.  
      Use case ends.
* 1e. The given person is already assigned to the given event.
    * 1e1. App shows an error message to tell the user that the given person is already assigned to the given event.  
      Use case ends.

---

**Use case: UC14 - Unassign event from person**

**MSS:**
1. User requests to unassign an event from a person by providing an event name or an event index, and a person name or a person index.
2. App unassigns the event specified from the person specified.  
   Use case ends.

**Extensions:**
* 1a. The given event name does not match any of the existing events.
    * 1a1. App shows an error message to tell the user that the given event name does not exist.  
      Use case ends.
* 1b. The given event index does not exist, i.e. there are only 3 events in the displayed list, but the user inputs an index of 4.
    * 1b1. App shows an error message to tell the user that the given event index is invalid.  
      Use case ends.
* 1c. The given person name does not match any of the existing persons.
    * 1c1. App shows an error message to tell the user that the given person name does not exist.  
      Use case ends.
* 1d. The given person index does not exist, i.e. there are only 3 persons in the displayed list, but the user inputs an index of 4.
    * 1d1. App shows an error message to tell the user that the given person index is invalid.  
      Use case ends.
* 1e. The given person was not previously assigned to the given event.
    * 1e1. App shows an error message to tell the user that the given person is not assigned to the given event.  
      Use case ends.

---

**Use Case: UC15 - Export Contacts**

**MSS:**
1. User requests to export contacts.
2. App retrieves the list of contacts.
3. App prepares the export file.
4. App saves the contact information to the file.
5. App displays a success message confirming the export.  
   Use case ends.

**Extensions:**
* 1a. An error occurs while preparing or saving the file.
    * 1a1. App displays an error message indicating the export failed.  
      Use case ends.

---

**Use Case: UC16 - Import Contacts from CSV**

**MSS:**
1. User requests to import contacts by providing a file name.
2. App checks if the specified file exists.
3. App checks if the file format is correct.
4. App reads the contacts from the file.
5. App adds the contacts to the address book.
6. App displays a success message confirming the import of contacts.  
   Use case ends.

**Extensions:**
* 2a. The specified file does not exist.
    * 2a1. App displays an error message indicating that the file does not exist.  
      Use case ends.
* 3a. The file format is incorrect (e.g., missing or incorrect headers, wrong file extension).
    * 3a1. App displays an error message indicating that the file format is incorrect.  
      Use case ends.
* 4a. An error occurs while reading the file.
    * 4a1. App displays an error message indicating there was an issue reading the file.  
      Use case ends.

---

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. System should respond within two seconds.
5. System should be usable by a novice who has not used a command line interface before.
6. Final product should be a result of evolving/enhancing/morphing the given codebase.
7. Should be for a single user i.e. (not a multi-user product).
8. Needs to be developed in a breadth-first incremental manner over the project duration.
9. Should be stored locally and should be in a human editable text file.
10. Should follow the Object-oriented paradigm primarily.
11. Software should work without requiring an installer.
12. Software should not depend on a remote server.
13. The GUI should work well (i.e., should not cause any resolution-related inconveniences to the user) for,
    - standard screen resolutions 1920x1080 and higher, and,
    - for screen scales 100% and 125%.
14. In addition, the GUI should be usable (i.e., all functions can be used even if the user experience is not optimal) for,
    - resolutions 1280x720 and higher, and,
    - for screen scales 150%.
15. JAR / ZIP file should not exceed 100MB.
16. Documents, such as PDF Files, should not exceed 15MB/file.
17. DG and UG should be PDF-friendly. Don't use expandable panels, embedded videos, animated GIFs etc.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **CLI**: Command Line Interface - a text-based interface where you can input commands that interact with a computer's operating system
* **GUI**: Graphical User Interface - a type of user interface through which users interact with electronic devices via visual indicator representations

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

   2. Open your favourite CLI (Command Prompt, Windows Powershell, Terminal).
   
   3. `cd` into the folder containing the jar file. 
   
   4. Run `java -jar clubconnect.jar`.  
      Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.  
      Expected: The most recent window size and location is retained.

### Deleting a person

1. Deleting a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    2. Test case: `delete 1`  
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message.

    3. Test case: `delete 0`  
       Expected: No contact is deleted. Error details shown in the status message. Status bar remains the same.

    4. Other incorrect delete commands to try: `delete`, `delete x` (where x is larger than the list size)  
       Expected: Similar to previous.

2. Deleting a person while all persons are being shown using the sample address book given.

    1. Prerequisites: No `clubconnect.json` file in the data folder (To populate the app with a sample address book).

    2. Test case: `delete David Li`  
       Expected: Contact with name `David Li` is deleted from the list. Details of the deleted contact shown in the status message. This contact is provided by the sample address book when you first open the app.

    3. Test case: `delete irfan ibrahim`  
       Expected: Contact with name `Irfan Ibrahim` is deleted from the list. Details of the deleted contact shown in the status message. This contact is provided by the sample address book when you first open the app.

    4. Test case: `delete roy`  
       Expected: No contact is deleted. Error details shown in the status message. Status bar remains the same.

3. Deleting a person while in a filtered displayed list.

    1. Prerequisites: Start with the sample address book and use a command that filters the current displayed contact list (Eg `search n/charlotte`).

    2. Test case: `delete 2`  
       Expected: No contact is deleted. Error details shown in the status message. Status bar remains the same.

    3. Test case: `delete 1`  
       Expected: Contact with name `Charlotte Oliveiro` is deleted from the list. Details of the deleted contact shown in the status message. This contact is provided by the sample address book when you first open the app.

### Deleting multiple persons
1. Deleting multiple valid contacts by indices.

   1. Test case: `mass_delete 1 3 5`
      Expected: Contacts at indices `1`, `3`, and `5` are deleted from the address book. A success message is shown indicating that contacts with these indices have been successfully deleted.

1. Deleting contacts with a mix of valid and invalid indices.

   1. Test case: `mass_delete 1 10 2` 
      Expected: Contacts at indices `1` and `2` are deleted. Index `10` is invalid (assuming the list has fewer than 10 contacts). A success message is shown, indicating deletion of valid indices, followed by a message listing the invalid input `10`.

1. Deleting contacts with all invalid indices.

   1. Test case: `mass_delete 10 20 30` 
      Expected: No contacts are deleted. An error message is shown indicating that no valid contact indices were provided for deletion.

1. Deleting contacts with non-integer inputs.

   1. Test case: `mass_delete 1 two 3`
      Expected: Contacts at indices `1` and `3` are deleted. The input two is invalid. A success message is shown for valid deletions, followed by a message listing the invalid input `two`.

1. Deleting contacts with duplicate indices.

   1. Test case: `mass_delete 2 2 4`
      Expected: Contacts at indices `2` and `4` are deleted. A success message is shown indicating successful deletion of contacts at these indices. Duplicate indices do not affect the operation beyond the first valid occurrence.

1. Deleting contacts with no indices provided.

   1. Test case: `mass_delete` 
      Expected: No contacts are deleted. An error message is shown indicating that no valid contact indices were provided for deletion.

### Searching for contacts
1. Searching with zero keywords.

    1. Test case: `search a/`  
       Expected: All contacts will be listed.
    2. Test case: `search e/`  
       Expected: All contacts will be listed. 
    3. Test case: `search n/`  
       Expected: All contacts will be listed.
    4. Test case: `search p/`  
       Expected: All contacts will be listed.
    5. Test case: `search t/`  
       Expected: All contacts will be listed.

2. Searching with one keyword.

    1. Test case: `search a/street`  
       Expected: Contacts with the substring street(case-insensitive) in their address will be listed.
    2. Test case: `search e/example`  
       Expected: Contacts with the substring `example` (case-insensitive) in their email address will be listed. 
    3. Test case: `search n/John`  
       Expected: Contacts with the substring `John` (case-insensitive) in their name will be listed. 
    4. Test case: `search p/123`  
       Expected: Contacts with the substring `123` in their phone number will be listed. 
    5. Test case: `search t/friend`  
       Expected: Contacts with the tag `friend` (case-insensitive) will be listed.

3. Searching with multiple keywords.

    1. Test case: `search a/street ave`  
       Expected: Contacts with the substring street or ave(case-insensitive) in their address will be listed.
    2. Test case: `search e/example domain`  
       Expected: Contacts with the substring `example` or `domain` (case-insensitive) in their email address will be listed. 
    3. Test case: `search n/John Doe`  
       Expected: Contacts with the substring `John` or `Doe` (case-insensitive) in their name will be listed. 
    4. Test case: `search p/123 456`  
       Expected: Contacts with the substring `123` or `456` in their phone number will be listed. 
    5. Test case: `search t/friend colleague`  
       Expected: Contacts with the tag `friend` or `colleague` (case-insensitive) will be listed.

4. Searching with keyword that yield no results.

    1. Test case: `search a/gibberish`  
       Expected: No person is found. Status message shows "0 persons listed."

### Adding an event

1. Adding a new unique event.

   1. Test case: `add_event n/Meeting d/Monday Meeting f/2024-01-01 t/2024-01-01`  
      Expected: An event with the name `Meeting` will be added to the event list. Details of added event shown in the status message.

   2. Test case: `add_event n/ d/Monday Meeting f/2024-01-01 t/2024-01-01`  
      Expected: No event is added. Error details (Invalid Name) shown in the status message.

   3. Test case: `add_event d/Monday Meeting f/2024-01-01 t/2024-01-01`  
      Expected: No event is added. Error details (Invalid Command Format) shown in the status message.

   4. Test case: `add_event n/Meeting d/  f/2024-01-01 t/2024-01-01`  
      Expected: No event is added. Error details (Invalid Description) shown in the status message.

   5. Test case: `add_event n/Meeting d/Monday Meeting f/2024-0101 t/2024-01-01`  
      Expected: No event is added. Error details (Invalid Date Format) shown in the status message.

   6. Test case: `add_event n/Meeting d/Monday Meeting f/2024-01-01 t/2023-01-01`  
      Expected: No event is added. Error details (End date cannot be earlier than start date) shown in the status message.

   7. Test case: `add_event n/Meeting d/Monday Meeting f/2024-01-01 t/2024-02-30`  
      Expected: No event is added. Error details (Invalid Date Format) shown in the status message.

2. Adding an event with the same name as an existing event.

   1. Test case: `add_event n/Meeting d/Duplicate Meeting f/2024-01-01 t/2024-01-01`  
      Expected: No event is added. Error details (Duplicate Event) shown in the status message.

### Deleting an event

1. Deleting an event while all events are being shown

    1. Prerequisites: List all events using the `list_events` command. Multiple events in the list.

    2. Test case: `delete_event 1`  
       Expected: First event is deleted from the list. Details of the deleted event shown in the status message.

    3. Test case: `delete_event 0`  
       Expected: No event is deleted. Error details shown in the status message. Status bar remains the same.

    4. Other incorrect `delete_event` commands to try: `delete_event`, `delete_event x` (where x is larger than the list size)  
       Expected: Similar to previous.

2. Deleting an event while all events are being shown using the sample address book given.

    1. Prerequisites: No `clubconnect.json` file in the data folder (To populate the app with a sample address book). List all events using the `list_events` command.

    2. Test case: `delete_event CS2103T Project Meeting`  
       Expected: Event with name `CS2103T Project Meeting` is deleted from the list. Details of the deleted event shown in the status message. This event is provided by the sample address book when you first open the app.

    3. Test case: `delete_event orbital`  
       Expected: No event is deleted. Error details shown in the status message. Status bar remains the same.

    4. Test case: `delete_event orbital workshop`  
       Expected: Event with name `Orbital Workshop` is deleted from the list. Details of the deleted event shown in the status message. This event is provided by the sample address book when you first open the app.

### Editing an event

1. Editing an existing event.

   1. Test case: `edit_event 1 n/Updated Meeting d/Updated description f/2024-10-02 t/2024-10-11`
      Expected: The event at index `1` is updated with the new name `Updated Meeting`, description `Updated description`, start date `2024-10-02`, and end date `2024-10-11`. Details of the edited event are shown in the status message. 
   
   1. Test case: `edit_event 1 n/ d/Updated description f/2024-10-02 t/2024-10-11` 
      Expected: No changes are made. Error details (Invalid Name) shown in the status message.

   1. Test case: `edit_event 1 d/ f/2024-10-02 t/2024-10-11` 
      Expected: No changes are made. Error details (Invalid Description) shown in the status message.

   1. Test case: `edit_event 1 n/Updated Meeting d/Updated description f/2024-1002 t/2024-10-11` 
      Expected: No changes are made. Error details (Invalid Date Format) shown in the status message.

   1. Test case: `edit_event 1 n/Updated Meeting d/Updated description f/2024-10-11 t/2024-10-02` 
      Expected: No changes are made. Error details (End date cannot be earlier than start date) shown in the status message.

   1. Test case: `edit_event 1 n/Updated Meeting d/Updated description f/2024-10-02 t/2024-02-30` 
   Expected: No changes are made. Error details (Invalid Date Format) shown in the status message.

1. Editing an event with an invalid index.

   1. Test case: `edit_event 10 n/Updated Meeting d/Updated description f/2024-10-02 t/2024-10-11` 
      Expected: No changes are made. Error details (Invalid Event Index) shown in the status message.

1. Editing an event without specifying any changes. 

   1. Test case: `edit_event 1` 
      Expected: No changes are made. Error details (No changes specified for the event) shown in the status message.

1. Editing an event to have the same name as another existing event. 

   1. Test case: `edit_event 1 n/Existing Event Name d/Updated description f/2024-10-02 t/2024-10-11` 
      Expected: No changes are made. Error details (Duplicate Event) shown in the status message.

### Assigning an Event to a Person

1. Assigning an existing event to an existing person

    1. Prerequisites: Ensure that the first person in the contact list is named `Alice` and the first event in the event list is named `Meeting`. Ensure that `Alice` is not already assigned to `Meeting` before each test case. More than 1 person and event should be present.

    2. Test case: `assign_event p/1 ev/1`  
       Expected: `Alice` is assigned to `Meeting`, and a confirmation message displays the successful assignment.

    3. Test case: `assign_event p/1 ev/meeting`  
       Expected: `Alice` is assigned to `Meeting`, and a confirmation message displays the successful assignment.

    4. Test case: `assign_event p/alice ev/1`  
       Expected: `Alice` is assigned to `Meeting`, and a confirmation message displays the successful assignment.

    5. Test case: `assign_event p/alice ev/meeting`  
       Expected: `Alice` is assigned to `Meeting`, and a confirmation message displays the successful assignment.

    6. Test case: `assign_event p/2 ev/1`  
       Expected: The person at index 2 is assigned to `Meeting`, and a confirmation message displays the successful assignment.

2. Person or Event does not exist

    1. Prerequisites: Ensure that person `Bob` and event `Workshop` do not exist in the app. Ensure that person `Alice` and event `Meeting` exist in the app.

    2. Test case: `assign_event p/bob ev/meeting`  
       Expected: No person is assigned to `Meeting`. Error details (Person does not exist) shown in the status message.

    3. Test case: `assign_event p/alice ev/workshop`  
       Expected: No person is assigned to any event. Error details (Event does not exist) shown in the status message.

    4. Test case: `assign_event p/0 ev/1`  
       Expected: No person is assigned to the first event. Error details (Invalid Command Format) shown in the status message.

    5. Test case: `assign_event p/1 ev/0`  
       Expected: No person is assigned to any event. Error details (Invalid Command Format) shown in the status message.

    6. Test case: `assign_event p/1 ev/999999`  
       Expected: No person is assigned to any event. Error details (Invalid Event Index) shown in the status message.

3. Person already assigned to the Event

    1. Prerequisites: Ensure that `Alice` is already assigned to `Meeting`.

    2. Test case: `assign_event p/1 ev/1`  
       Expected: No new assignment is made. Error details (Person already assigned to Event) shown in the status message.

    3. Test case: `assign_event p/alice ev/meeting`  
       Expected: No new assignment is made. Error details (Person already assigned to Event) shown in the status message.

### Unassigning an event from a person

1. Unassigning an existing event from an existing person

    1. Prerequisites: Ensure that the first person in the contact list is named `Alice` and the first event in the event list is named `Meeting`. Ensure that only `Alice` is assigned to `Meeting` before each test case. More than 1 person and event should be present.

    2. Test case: `unassign_event p/1 ev/1`  
       Expected: `Alice` is unassigned from `Meeting` in the status message.

    3. Test case: `unassign_event p/1 ev/meeting`  
       Expected: `Alice` is unassigned from `Meeting` in the status message.

    4. Test case: `unassign_event p/alice ev/1`  
       Expected: `Alice` is unassigned from `Meeting` in the status message.

    5. Test case: `unassign_event p/alice ev/meeting`  
       Expected: `Alice` is unassigned from `Meeting` in the status message.

    6. Test case: `unassign_event p/2 ev/1`  
       Expected: No person is unassigned from `Meeting`. Error details (Person not assigned to Event) shown in the status message.

2. Event / Person does not exist

    1. Prerequisites: Ensure that person `Bob` and event `Workshop` do not exist in the app. Ensure that person `Alice` and event `Meeting` exists in the app.

    2. Test case: `unassign_event p/bob ev/meeting`  
       Expected: No person is unassigned from `Meeting`. Error details (Person does not exist) shown in the status message.

    3. Test case: `unassign_event p/alice ev/workshop`  
       Expected: No person is unassigned from any event. Error details (Event does not exist) shown in the status message.

    4. Test case: `unassign_event p/0 ev/1`  
       Expected: No person is unassigned from the first event. Error details (Invalid Command Format) shown in the status message.

    5. Test case: `unassign_event p/1 ev/0`  
       Expected: No person is unassigned from any event. Error details (Invalid Command Format) shown in the status message.

    6. Test case: `unassign_event p/1 ev/999999`  
       Expected: No person is unassigned from any event. Error details (Invalid Event Index) shown in the status message.

### Exporting Contacts to CSV

1. Exporting when the export file does not exist

    1. Prerequisites: Ensure the `data` directory exists. Ensure that `ExportedContacts.csv` does not exist in the `data` directory before each test case.

    2. Test case: `export`
        - Expected: A success message confirms that contacts have been exported. Verify that `ExportedContacts.csv` now exists in the `data` directory.

### Importing Contacts from CSV

1. Importing from an existing, correctly formatted file

    1. Prerequisites: Ensure the `data` directory contains a file named `contacts.csv` with correctly formatted contact information (headers: `Name,Phone Number,Email Address,Address,Tags`). Ensure that some of the contacts in `contacts.csv` are not already in the address book.

    2. Test case: `import contacts.csv`
        - Expected: A success message confirms that contacts from `contacts.csv` have been imported. Verify that the new contacts are now added to the address book, and existing contacts remain unchanged.

2. Importing from a non-existent file

    1. Prerequisites: Ensure that the `data` directory exists but does not contain a file named `nonexistent.csv`.

    2. Test case: `import nonexistent.csv`
        - Expected: No contacts are imported. An error message displays: "The specified file does not exist."

3. Importing from a file with an incorrect format

    1. Prerequisites: Ensure the `data` directory contains a file named `incorrect.csv` with improperly formatted content (e.g., missing headers or incorrect number of columns).

    2. Test case: `import incorrect.csv`
        - Expected: No contacts are imported. An error message displays: "The format of the specified file is incorrect."

### Saving data

1. Dealing with missing/corrupted data files

   1. Go to the folder you put the `clubconnect.jar` file. There should be a `data` folder in the same folder.
   2. Double-click the `data` folder to go into the folder. There should be a `clubconnect.json` file inside.
   3. Delete the `clubconnect.json` file.

