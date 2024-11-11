---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# KnottyPlanners Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).

Libraries Used: [JavaFX](https://openjfx.io/), [Jackson](https://github.com/FasterXML/jackson), [JUnit5](https://github.com/junit-team/junit5)

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [<u>_Setting up and getting started_</u>](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [<u>`Main`</u>](https://github.com/AY2425S1-CS2103T-W13-4/tp/blob/master/src/main/java/seedu/address/Main.java) and [<u>`MainApp`</u>](https://github.com/AY2425S1-CS2103T-W13-4/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**<u>`UI`</u>**](#ui-component): The UI of the App.
* [**<u>`Logic`</u>**](#logic-component): The command executor.
* [**<u>`Model`</u>**](#model-component): Holds the data of the App in memory.
* [**<u>`Storage`</u>**](#storage-component): Reads data from, and writes data to, the hard disk.

[**<u>`Commons`</u>**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete n/Alex`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Another *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `add-wed w/John & Gina v/CHIJMES d/11/12/25`.

<puml src="diagrams/ArchitectureSequenceDiagram2.puml" />

The four main components (also shown in the diagram above, staticContext is not a main component),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point).

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [<u>`Ui.java`</u>](https://github.com/AY2425S1-CS2103T-W13-4/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [<u>`MainWindow`</u>](https://github.com/AY2425S1-CS2103T-W13-4/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [<u>`MainWindow.fxml`</u>](https://github.com/AY2425S1-CS2103T-W13-4/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [<u>`Logic.java`</u>](https://github.com/AY2425S1-CS2103T-W13-4/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("view-wed Alex & Mary")` API call as an example.

<puml src="diagrams/ViewWeddingSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `view-wed Alex & Mary` Command" />

<box type="info" seamless>

**Note:** The lifeline for `ViewWeddingCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.

</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `ViewWeddingCommandParser`) and uses it to parse the command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `ViewWeddingCommand`) which is executed by the `LogicManager`.
3. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [<u>`Model.java`</u>](https://github.com/AY2425S1-CS2103T-W13-4/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />

The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

### Storage component

**API** : [<u>`Storage.java`</u>](https://github.com/AY2425S1-CS2103T-W13-4/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage`, `WeddingBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

#### Implementation

This section describes some noteworthy feature implementation details of KnottyPlanners.

### [Wedding](https://github.com/AY2425S1-CS2103T-W13-4/tp/blob/master/src/main/java/seedu/address/model/wedding/Wedding.java) - Class for Wedding Events

The `Wedding` class represents a wedding event in KnottyPlanners. It includes details such as the wedding name, venue,
 date and participants. The `Wedding` class supports the following functions:

* `Add Wedding`: Creates a new `Wedding` in KnottyPlanners.
* `Delete Wedding`: Deletes an existing `Wedding` from KnottyPlanners and the associated tags.
* `List Weddings`: Lists all `Weddings` in KnottyPlanners.
* `View Wedding`: Displays participants of a specific `Wedding`.

Refer to our [**User Guide**](https://ay2425s1-cs2103t-w13-4.github.io/tp/UserGuide.html) for more information on how to use these functions associated with the Wedding Class.

### [StaticContext](https://github.com/AY2425S1-CS2103T-W13-4/tp/blob/master/src/main/java/seedu/address/logic/StaticContext.java) - Confirmation Prompting for Deletion and Clear Commands

`StaticContext` is a utility class used to manage the state of pending operations in KnottyPlanners. It helps in handling deletion and clear commands by maintaining boolean flags and references to the entities to be deleted or cleared. 

The `StaticContext` class supports the following functions:

| Function                          | Associated Command                           |
|-----------------------------------|----------------------------------------------|
| `setClearAddressBookPending`      | `clear-ab` / `cab`                           |
| `setClearWeddingBookPending`      | `clear-wb` / `cwb`                           |
| `setPersonToDelete`               | `del n/NAME & NAME`                          |
| `setWeddingToDelete`              | `del-wed n/NAME & NAME` / `dw n/NAME & NAME` |

Here is an example usage scenario and how `StaticContext` is used in KnottyPlanners.

1. The user requests to delete a `Wedding`. `StaticContext.setWeddingToDelete(wedding)` is called to set the `Wedding` to be deleted.

2. The user confirms the deletion. The `DeleteYCommand` checks `StaticContext.getWeddingToDelete()` and proceeds with the deletion.

3. The user requests to clear the wedding book. `StaticContext.setClearWeddingBookPending(true)` is called to set the clear operation as pending.

4. The user confirms the clear operation. The `DeleteYCommand` checks `StaticContext.isClearWeddingBookPending()` and proceeds with clearing the wedding book.

#### Design Considerations:

**Strict Command Guidelines:**

* Commands are designed to be strictly followed in their execution.
  * For example, any command that requires a confirmation prompt **must** be followed by a `y` or `n` to proceed or cancel the operation respectively.
  * This ensures that the user is aware of the operation being performed and prevents accidental deletions or clear operations.
  * Pros: Prevents accidental deletions or clear operations.
  * Cons: Executing other commands before `y` or `n` that modify the intended deletion or clear operation may lead to unexpected results. (We have implemented checks to prevent this)

**`tag-del` does not require confirmation:**

* The `tag-del` command does not require a confirmation prompt.
  * This is because the operation is reversible and does not have a significant impact on the data.
  * We akin it to adding a label to a contact, which can be removed without any significant consequences.
  * Pros: Saves time by not requiring a confirmation prompt for a reversible operation.
  * Cons: Users may not be satisfied that deletion operations are not consistent throughout with a confirmation prompt.

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

**1. Prefix Enhancements**

**Currently:** The view wedding command in KnottyPlanners does not require a need for a prefix to represent the wedding. Thus, when associating a wedding to a `Person`, it is done by _tagging_ them to it, making the prefix 't/'. 

**Plan:** To make the association of `Wedding` and `Person` more intuitive, we will change the prefix from 't/' to 'w/', representing the `Wedding` that a `Person` is associated with.

**2. Forced Deletion and Clearing**

**Currently:** To prevent accidental deletions and clearing of data in KnottyPlanners, a user confirmation is currently required to ensure that the action is intentional.

**Plan:** To include a force deletion or clearing command that enables the user to remove the data without KnottyPlanners requiring a confirmation from them.

**3. Improved Filtering**

**Currently:** The filter command in KnottyPlanners supports filtering by the name or job fields only.

**Plan:** To allow users to filter with other fields such as phone number, address and email. To allow users to filter with other fields such as phone number, address and email.

**4. Allow Copying of Information**

**Currently:** KnottyPlanners is optimised for keyboard usage, and direct copying of information from the GUI is not supported.

**Plan:** To allow the users of KnottyPlanners to copy information directly from the contact or wedding cards, in order to facilitate mouse usage.

**5. Support for Long Inputs**

**Currently:** KnottyPlanners only support inputs up till a reasonable amount of characters. As such, extreme inputs that exceed our limit can potentially hinder the viewing of information when the user does not resize the window.

**Plan:** To handle extreme inputs of all fields with a long character count, ensuring that the information can still remain visible.

**6. Index Referencing**

**Currently:** KnottyPlanner requires users to select the `Person` from their names in order to reduce ambiguity. However, this can result in inefficiency when handling extremely long names.

**Plan:** To allow users to reference the contact or wedding based on their index in the list, ensuring that the efficiency of KnottyPlanners is maintained.

**7. Further CLI Support**

**Currently:** Opening the help window creates an external popup that requires the user to close with a mouse or trackpad.

**Plan:** Enhance CLI optimisation by allowing the user to close the popup without using a mouse or trackpad.

**8. Contacts and Weddings Visibility**

**Currently:** The address and wedding lists are in two separate views to help the wedding planner to be more focused and reduce cluttering of information. This can lead to an additional need to toggle between the wedding book and address book to recall the names of the wedding.

**Plan:** To have an integrated view contains both the persons and weddings, allowing the association of persons to weddings to be done with less memory work.

**9. Comprehensive Language Support**

**Currently:** KnottyPlanners support names in English language without special characters such as "^" or non-English names.

**Plan:** To have a more comprehensive language allowance for names with special characters and non-English names, making KnottyPlanners more inclusive. To maintain checks for incorrect entry of data, KnottyPlanners will then prompt the user if special characters have been used.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* Wedding planners who organise multiple weddings concurrently
* Possesses a need to manage a significant number of contacts with various roles
* Requires efficient categorisation of all stakeholders in a wedding
* Can type fast and prefers typing to mouse interactions
* Is reasonably comfortable using CLI apps

**Value proposition**: Manage contacts of all stakeholders of multiple weddings with ease and convenience


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​         | I want to …​                                      | So that I can…​                                                                          |
|----------|-----------------|---------------------------------------------------|------------------------------------------------------------------------------------------|
| `* * *`  | Wedding Planner | Add contact details                               | I can store contacts in my address book                                                  |
| `* * *`  | Wedding Planner | Delete contacts                                   | I can remove contacts I no longer need                                                   |
| `* * *`  | Wedding Planner | List all contacts                                 | I can view all my clients and wedding vendors                                            |
| `* *`    | Wedding Planner | Edit contact details                              | I can keep information as up-to-date and accurate as possible.                           |
| `* *`    | Wedding Planner | Filter contacts by name and/or job                | I can quickly find the contact(s) I need                                                 |
| `* *`    | Wedding Planner | Create weddings                                   | I can manage all details specific to that wedding                                        |
| `* *`    | Wedding Planner | Delete weddings                                   | I can remove weddings once they have happened                                            |
| `* *`    | Wedding Planner | List all weddings                                 | I can view all my weddings that I am planning                                            |
| `* *`    | Wedding Planner | View all contacts associated with a wedding       | I can easily view which stakeholders are involved in that wedding                        |
| `* *`    | Wedding Planner | Tag my contacts to a wedding                      | I can group relevant stakeholders and attendees of a wedding together                    |
| `* *`    | Wedding Planner | Un-tag my contacts from a wedding                 | I can remove relevant stakeholders and attendees who will no associate with that wedding | |
| `*`      | Wedding Planner | Create and manage guest lists for each wedding    | I can track RSVPs and dietary preferences                                                |
| `*`      | Wedding Planner | Track vendor bookings for each wedding            | I can ensure all necessary services are confirmed                                        |
| `*`      | Wedding Planner | Rate or review vendors after each event           | I can assess their performance for future recommendations                                | |
| `*`      | Wedding Planner | Set reminders to contact specific vendors/clients | I can correspond with them on time without missing any important deadlines.              |

### Use cases

(For all use cases below, the **System** is the `KnottyPlanners` and the **Actor** is the `User`, unless specified otherwise)


**Use case: UC01 - Delete a contact**

**MSS**

1.  User requests to list contacts
2.  KnottyPlanners shows a list of contacts
3.  User requests to delete a specific contact in the list
4.  KnottyPlanners shows a confirmation prompt
5.  User confirms the deletion
6.  KnottyPlanners deletes the contact

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given contact name is invalid.

    * 3a1. KnottyPlanners shows an error message.

      Use case ends.

* 5a. The user cancels the deletion.

  Use case ends.

**Use case: UC02 - Add a contact**

**MSS**

1.  User requests to list contacts
2.  KnottyPlanners shows a list of contacts
3.  User requests to add a new contact
4.  KnottyPlanners adds the new contact

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given contact details are invalid.

    * 3a1. KnottyPlanners shows an error message.

      Use case resumes at step 2.

* 3b. The contact already exists.

    * 3b1. KnottyPlanners shows an error message.

      Use case resumes at step 2.

**Use case: UC03 - Add a tag to a contact**

**MSS**

1. User requests to tag a wedding to a person
2. KnottyPlanners tags the person to the wedding

   Use case ends.

**Extensions**

* 1a. The wedding is not yet created.

    * 1a1. KnottyPlanners shows an error message.

      Use case ends.

* 1b. The given wedding name is in an invalid format.

    * 1b1. KnottyPlanners shows an error message.

      Use case resumes at step 2.

* 1c. The given contact name is invalid.

    * 1c1. KnottyPlanners shows an error message.

      Use case resumes at step 2.

* 1d. The tag already exists for that contact.

    * 1d1. KnottyPlanners shows an error message.

      Use case resumes at step 2.

**Use case: UC04 - Delete a tag from a contact**

**MSS**

1.  User requests to list contacts
2.  KnottyPlanners shows a list of contacts
3.  User requests to delete a tag from a specific contact in the list
4.  KnottyPlanners deletes the tag from that contact

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given name is invalid.

    * 3a1. KnottyPlanners shows an error message.

      Use case resumes at step 2.

* 3b. The tag does not exist for that contact.

    * 3b1. KnottyPlanners shows an error message.

      Use case resumes at step 2.

* 3c. The tag is not associated with any wedding.

    * 3c1. KnottyPlanners shows an error message.

      Use case resumes at step 2.

**Use case: UC05 - View all Contacts associated with a Wedding**

**MSS**

1.  User requests to list weddings
2.  KnottyPlanners shows a list of weddings
3.  User requests to view all contacts for a specific wedding in the list
4.  KnottyPlanners shows a list of contacts associated with that wedding (if any)

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given wedding name is invalid or does not exist.

    * 3a1. KnottyPlanners shows an error message.

      Use case resumes at step 2.

**Use case: UC06 - Search for a specific contact by name**

**MSS**

1. User requests to search for contacts by name criteria
2. KnottyPlanners shows a list of contacts that matches the name criteria

    Use case ends.

**Extensions**

* 1a. The given criterion is invalid.

    * 1a1. KnottyPlanners shows an error message.

      Use case resumes at step 1.

* 2a. There are no contacts that matches the name.

    * 2a1. KnottyPlanners shows an empty list.

  Use case ends.

**Use case: UC07 - Search for a specific contact by job**

**MSS**

1. User requests to search for contacts by job criteria
2. KnottyPlanners shows a list of contacts that match the job criteria

   Use case ends.

**Extensions**

* 1a. The given criterion is invalid.

    * 1a1. KnottyPlanners shows an error message.

      Use case resumes at step 1.

* 2a. There are no contacts that matches the job.

    * 2a1. KnottyPlanners shows an empty list.

  Use case ends.

**Use case: UC08 - Edit a contact's details**

**MSS**

1. User requests to list contacts
2. KnottyPlanners shows a list of contacts
3. User requests to edit details of a specific contact
4. KnottyPlanners updates the details for that contact

   Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given contact name is invalid.

    * 3a1. KnottyPlanners shows an error message.

      Use case resumes at step 2.

* 3b. The new contact details given are invalid.

  * 3b1. KnottyPlanners shows an error message.

    Use case resumes at step 2.

**Use case: UC09 - Adding a new wedding**

**MSS**

1. User requests to list weddings
2. KnottyPlanners shows a list of weddings
3. User requests to add a new wedding
4. KnottyPlanners adds the new wedding

   Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given wedding name is invalid or fields are missing/invalid.

    * 3a1. KnottyPlanners shows an error message.

      Use case resumes at step 2.

* 3b. The wedding already exists.

    * 3b1. KnottyPlanners shows an error message.

      Use case resumes at step 2.

**Use case: UC10 - Deleting a wedding**

**MSS**

1. User requests to list weddings
2. KnottyPlanners shows a list of weddings
3. User requests to delete a specific wedding in the list
4. KnottyPlanners shows a confirmation prompt
5. User confirms the deletion
6. KnottyPlanners deletes the wedding

   Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given wedding name is invalid.

    * 3a1. KnottyPlanners shows an error message.

      Use case resumes at step 2.

* 3b. The wedding does not exist.

    * 3b1. KnottyPlanners shows an error message.

      Use case resumes at step 2.

* 5a. The user cancels the deletion.

  Use case ends.

**Use case: UC11 - Clearing Address or Wedding Book**

**MSS**

1. User requests to clear the address book or wedding book
2. KnottyPlanners shows a confirmation prompt
3. User confirms the deletion
4. KnottyPlanners clears the address book or wedding book

   Use case ends.

**Extensions**

* 2a. The address book or wedding book is already empty.

  Use case ends.

* 3a. The user cancels the deletion.

  Use case ends.

### Non-Functional Requirements

1.  The system should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  The system should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using keyboard commands than using the mouse.
4.  The system should return a response to commands that are inputted by the user within three seconds.
5.  The codebase should be compliant with KnottyPlanners architecture and logic to facilitate easier testing and debugging.
6.  The system should be operational and responsive 24/7.

### Glossary

* **Contact:** An individual or organization associated with wedding planning, such as a client or vendor.
* **Job:** Role of a person during the weddings, e.g., caterers, florists, or photographer.
* **Wedding Tag:** A label assigned to a contact to associate them with a specific wedding event, can be assigned to
multiple contacts involved in the same wedding event.
* **AddressBook:** The main data model that represents the collection of all 'Person' objects within KnottyPlanners.
* **WeddingBook:** The data model that represents the collection of all 'Wedding' objects within KnottyPlanners.
* **Priority:** The relative importance of a user story or task, often categorized as must-have, should-have,
or could-have.
* **Success/Failure Messages:** Feedback provided to the user indicating the outcome of a command
(e.g., contact successfully added or an error message).

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing using the User Guide.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   2. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Adding a person

1. Adding a person

    1. Test case: `add n/John Doe p/98765432 e/johnd@example.com a/John street, Blk 123, #01-04 j/Photographer`
       Expected: Success message with John Doe's details shown. Ui displays person list.
    2. Incorrect add command to try: `add n/John Doe p/98765432 e/johnd@example.com`<br>
       Expected: Error message due to invalid command shown.

### Deleting a person

1. Deleting a person that exists in AddressBook
   1. Test case: `del n/Alex` followed by `y`<br>
      Expected: Confirmation Prompt with Alex's details shown. Alex is then deleted. Ui displays person list.
   2. Test case: `del n/Jonus` followed by `n`<br>
      Expected: Delete operation cancelled and no person is deleted. Ui displays person list.
   3. Incorrect delete command to try: `del`, `del 123123`<br>
      Expected: Error message due to invalid command shown.

### Editing a person

1. Editing a person that exists in AddressBook

    1. Test case: `edit n/Alex new/John Doe` <br>
       Expected: Success message with edited details shown. Ui displays person list.
    2. Incorrect edit command to try: `edit n/Alex t/Alex & John Doe`<br>
       Expected: Error message stating tags cannot be edited shown. 

### Filtering a person

1. Filtering a person based on name and job

    1. Test case: `filter n/John j/Photographer` <br>
       Expected: Success message with number of persons filtered. Ui displays filtered person list with name `John` and job `Photographer`.
    2. Incorrect edit command to try: `filter John`<br>
       Expected: Error message due to invalid command shown

### Adding a wedding

1. Adding a wedding

    1. Test case: `add-wed w/James Hauw & Rachel Loh v/Pan Pacific Hotel d/11/03/2025` <br>
       Expected: Success message with wedding details shown. Ui displays wedding list.
    2. Incorrect add wedding command to try: `add-wed v/Pan Pacific Hotel d/11/03/2025`<br>
       Expected: Error message due to invalid command shown.
    3. Other incorrect add wedding to try: `add-wed w/James Hauw and Rachel Loh v/Pan Pacific Hotel d/11/03/2025`<br>
       Expected: Error message stating the correct wedding name format.

### Deleting a wedding

1. Deleting a wedding that exists in WeddingBook

    1. Test case: `del-wed  w/Jonus Ho & Izzat Syazani` followed by `y`<br>
       Expected: Confirmation Prompt with wedding details shown. Wedding is then deleted. Ui displays wedding list.
    2. Test case: `del-wed  w/Jonus Ho & Izzat Syazani` followed by `n`<br>
       Expected: Delete operation cancelled and no wedding is deleted. Ui displays wedding list.
    3. Incorrect delete command to try: `del-wed`, `del-wed Jonus & Izzat`<br>
       Expected: Error message due to invalid command shown.

### Adding a Tag

1. Adding a tag of an existing wedding in WeddingBook to a person

    1. Test case: `tag-add n/Jonus t/James Hauw & Rachel Loh` <br>
       Expected: Success message stating that person is added to a wedding. Ui displays person list.
    2. Incorrect add wedding command to try: `tag-add n/Jonus w/James Hauw & Rachel Loh`<br>
       Expected: Error message due to invalid command shown.
   
### Deleting a tag

1. Deleting a tag of an existing wedding in WeddingBook of a person

    1. Test case: `tag-del n/Jonus t/James Hauw & Rachel Loh` <br>
       Expected: Success message stating that person is removed from a wedding. Ui displays person list.
    2. Incorrect add wedding command to try: `tag-del n/Jonus w/James Hauw & Rachel Loh`<br>
       Expected: Error message due to invalid command shown.

### GUI

1. Showing person list

    1. Test case: `list` <br>
       Expected: Success message with total number of persons. Ui displays person list.
   
2. Showing wedding list

   1. Test case: `list-wed` <br>
      Expected: Success message with total number of weddings. Ui displays wedding list.

3. Showing help window

   1. Test case: `help` <br>
      Expected: Ui displays help window.


### Saving data

1. Dealing with corrupted data files

   1. Using the relevant `add` or `add-wed` commands, create dummy data that is to be stored in the storage.

   2. To simulate a corrupted file, locate the addressbook.json or weddingbook.json file in the data folder.

   3. Delete a random line in the .json file, and relaunch KnottyPlanners.
        Expected: KnottyPlanners will launch with all previous data wiped and cleared.
