---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* This project was built on the AddressBook-3 (AB3) application
* No additional third-party libraries were used

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

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-W12-1/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-W12-1/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
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

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-W12-1/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-W12-1/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-W12-1/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-W12-1/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

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

Here are more examples of how our commands work:

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("socialMedia 1 ig/username")` API call as an example.

![Interactions Inside the Logic Component for the `socialMedia 1 ig/username` Command](images/SocialMediaSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `SocialMediaCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("schedule 1 sd/2024-10-24")` API call as an example.

![Interactions Inside the Logic Component for the `schedule 1 sd/2024-10-24` Command](images/ScheduleSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `ScheduleCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("sort n/asc"")` API call as an example.
![Interactions Inside the Logic Component for the `sort n/asc` Command](images/SortSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `SortCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>



The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("backup"")` API call as an example.
![Interactions Inside the Logic Component for the `backup` Command](images/BackupSequenceDiagram.png)





### Model component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-W12-1/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />

The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:**
An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

Note that the Person class requires at least one of Phone, Address, Email or Social Media to be present. This cannot be captured by the UML. <br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-W12-1/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

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

* has a need to manage a significant number of contacts
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps
* small business owners
* sole proprietors

**Value proposition**: Since these businesses are single-individual teams, keeping track of different stakeholders alone is a hassle. These individuals work on their computers at least for their networking purposes, making the product compatible with their existing workflows. The product is an easy-to-use contact management app that manages customers, business partners and suppliers.



### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​       | I want to …​                                                                | So that I can…​                                                             |
|----------|---------------|-----------------------------------------------------------------------------|-----------------------------------------------------------------------------|
| `* * *`  | new user      | understand the interface easily                                             | start using the contact management app with little trouble                  |
| `* * *`  | new user      | add a new contact                                                           | start storing my existing contacts                                          |
| `* * *`  | new user      | find a contact by name                                                      | retrieve their contact information                                          |
| `* * *`  | familiar user | delete/archive a contact                                                    | remove those that are no longer working with me                             |
| `* * *`  | familiar user | group my contacts into categories                                           | group all users of one category (eg. just customers)                        |
| `* * *`  | familiar user | retrieve all contacts from a specific group                                 | find my customers, suppliers, business partners, etc easily                 |
| `* * `   | new user      | easily import my contact from my phone address book                         | set up faster                                                               |
| `* *`    | new user      | call/text my contacts                                                       | easily speak to them                                                        |
| `* * `   | new user      | retrieve all the emails (or some other info) of contacts in my current view | easily email all these contacts                                             |
| `* * `   | new user      | see my contact history                                                      | know when contacts were added                                               |
| `* *`    | familiar user | add any extra remarks to my contacts                                        | be aware of each contact's notable information                              |
| `* * `   | familiar user | update/edit contact details                                                 | update any recent changes to the contact                                    |
| `* * `   | familiar user | add additional information to each contact                                  | take note of special considerations from different customer                 |
| `* *`    | familiar user | sort the contacts by product deadlines                                      | easily know which products I have to send out                               |
| `* * `   | familiar user | filter contacts by criteria                                                 | search for specific types of people with certain characteristics            |
| `* *`    | familiar user | sort my contacts alphabetically                                             | find my contacts by their name                                              |
| `* * `   | familiar user | add profile photos to my contacts                                           | have a idea of how my contacts look like if I have not seen them for awhile |
| `* * `   | familiar user | export and share a contact with others                                      | share a contact with others potentially to discuss about                    |
| `* *`    | familiar user | pin contacts                                                                | quickly find those that are important to my business                        |
| `* * `   | familiar user | see alerts on duplicate contacts                                            | delete it and keep my address book clean                                    |
| `* * `   | familiar user | quickly retrieve my previous command                                        | not need to retype similar complex commands                                 |
| `* * `   | familiar user | link my contacts' social media accounts                                     | access their social media easily, especially if relevant to our deal        |
| `* *`    | familiar user | have a shortcut to user addresses                                           | easily determine where to send products to                                  |
| `* * `   | familiar user | undo deletions                                                              | restore contacts in case of accidental deletion                             |
| `* *`    | familiar user | rename tags                                                                 | change the group of names when my business needs change                     |
| `* * `   | familiar user | create temporary groups                                                     | group contacts based on projects, rather than just their affiliations       |
| `*`      | familiar user | colour code my contacts by their categories                                 | easily spot them                                                            |
| `* `     | expert user   | perform mass edits                                                          | change in the information of groups of contacts at one shot                 |
| `*`      | expert user   | add nicknames / aliases to contacts                                         | find them not just by their name, but what I remember them by               |
| `* `     | expert user   | add reminders                                                               | be reminded to follow up with specific contacts                             |
| `* `     | expert user   | create shortcut commands to add specific types of contacts                  | add people of similar profiles faster                                       |

### Use cases

(For all use cases below, the **System** is the `BlitzBiz` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - Delete a contact**

**MSS**

1.  User requests to list contacts.
2.  BlitzBiz shows a list of contacts.
3.  User requests to delete a specific person in the list.
4.  BlitzBiz deletes the person.

    Use case ends.

**Extensions**

* 2a. The list is empty.
  
   Use case ends.

* 3a. The given index is invalid.
    * 3a1. BlitzBiz shows an error message.
  
      Use case resumes at step 2.

**Use case: UC02 - Restore a contact**

Preconditions: User has deleted a contact

**MSS**

1. User restores the deleted contact.
2. BlitzBiz restores the most recently deleted contact.

    Use case ends.

**Extensions**

* 2a. The contact has been added back using the add command.
    * 2a1. BlitzBiz shows an error message.

    Use case ends.

**Use case: UC03 - Add a contact**

**MSS**

1.  User requests to add a contact by providing name, and at least one of the contact's phone number, email or address.
2.  BlitzBiz adds the contact to the list with the provided details.

    Use case ends.

**Extensions**

* 1a. The entered phone number, email or address do not follow the correct format.
   *  1a1. BlitzBiz displays an error message
   *  1a2. User enters new details.
   *  Steps 1a1-1a2 are repeated until the data entered are in the correct format.
      
     Use case resumes from step 2.
* 1b. The contact already exist
   *  1b1. BlitzBiz displays an error message
   
    Use case ends.
* 1c. User did not enter a phone number, email or address
    *  1c1. BlitzBiz displays an error message
    *  1c2. User enters new parameters.
    *  Steps 1c1-1c2 are repeated until at least one parameter is entered.
       
    Use case resumes from step 2.


**Use case: UC04 - Find contacts by name**

**MSS**

1.  User requests to find a contact by name.
2.  BlitzBiz displays contacts that matches the name provided.

    Use case ends.

**Extensions**

* 1a. The user did not enter a name.
   *  1a1. BlitzBiz displays an error message.
   *   1a2. User enters new name to find.
   *   Steps 1a1-1a2 are repeated until a name to find is entered.
      
   Use case resumes from step 2.

* 2a. There are no contacts containing the prefix in their name.
   *   2a1. BlitzBiz informs the user that there were no matches found.
    
    Use case ends.

**Use case: UC05 - Rename Tag**

**MSS**

1.  User requests to rename a tag by providing a new tag name to change the old tag name into.
2.  BlitzBiz renames all the tags with the old tag name to the new tag name and displays the updated list.

    Use case ends.

**Extensions**

* 1a. The user did provide all required fields or entered a wrong field.
    *  1a1. BlitzBiz displays an error message.
    *   1a2. User enters new command.
    *   Steps 1a1-1a2 are repeated until the command format entered is correct.
     
    Use case resumes from step 2.

* 1b. The new tag does not follow requirements.
    *  1a1. BlitzBiz displays an error message.
    *   1a2. User enters new command with new tag.
    *   Steps 1a1-1a2 are repeated until the new tag entered is valid.
     
    Use case resumes from step 2.

* 2a. There are no tags with the old tag name.
    *  2a1. BlitzBiz informs the user that there were no matches found.
 
   Use case ends.

* 2b. There are contacts with both the old tag and new tag.
   * 2b1. BlitzBiz informs the user that contacts which will result in duplicated tags will not be updated.

    Use case ends.

**Use case: UC06 - Social Media**

**MSS**

1.  User requests to add a social media handle to a contact by providing exactly one social media platform and handle name.
2.  BlitzBiz adds the social media to the contact.

    Use case ends.

**Extensions**

* 1a. The user did not provide exactly one social media platform or handle or entered an invalid field.
    *  1a1. BlitzBiz displays an error message.
    *   1a2. User enters new command.
    *   Steps 1a1-1a2 are repeated until the command format entered is correct.
       
    Use case resumes from step 2.

* 1b. The handle does not follow requirements.
    *  1a1. BlitzBiz displays an error message.
    *   1a2. User enters new command with new handle.
    *   Steps 1a1-1a2 are repeated until the handle entered is valid.
       
    Use case resumes from step 2.

**Use case: UC07 - Filter contact list by tag(s)**

**MSS**

1.  User requests to filter the contact list by providing tag(s) to filter by.
2.  BlitzBiz displays the filter list of contacts with the given tag(s).

    Use case ends.

**Extensions**

* 1a. The entered tag(s) do not follow the correct format.
    *  1a1. BlitzBiz requests for the correct format.
    *   1a2. User enters again in the new format.
    *   Steps 1a1-1a2 are repeated until the format entered is correct.
       
    Use case resumes from step 2.

* 2a. No contacts with the tag(s) are found.
    * 2a1. BlitzBiz informs the user that there were no matches found.
      
    Use case ends.

**Use case: UC08 - Sort list of contacts**

**MSS**

1.  User requests to sort contact list by name or schedules in ascending or descending order.
2.  BlitzBiz displays the sorted list.

   Use case ends.

**Extensions**

* 1a. User did not enter order to sort by.
    *  1a1. BlitzBiz will sort by ascending order by default.

    Use case ends.
* 1b. User requested to sort by both name or schedule or did not provide a field to sort by.
    * 1b1. BlitzBiz displays error message.
    * 1b2. User enters again with only one field to sort by.
    *  Steps 1b1-1b2 are repeated until user provides one field to sort by.
      
    Use case resumes from step 2.

**Use case: UC09 - Search for contacts by schedules**

**MSS**

1.  User requests to search the contact list for contacts by providing a starting datetime and an ending datetime.
2.  BlitzBiz displays the searched list of contacts with schedules within the start and end datetime.

    Use case ends.

**Extensions**

* 1a. User only entered start time but not end time.
    *  1a1. BlitzBiz searches for schedules that starts later than the given start time.

* 1b. User only entered end time.
    *  1b1. BlitzBiz searches for schedules that ends before the end time.

* 1c. User enters an end time before start time.
    * 1c1. BlitzBiz displays error message.
    * 1c2. User enters again with a later end time than start time.
    *  Steps 1c1-1c2 are repeated until the format entered is correct.
      
    Use case resumes from step 2.
* 1b. Some contacts only has a schedule date but not time.
    *  1b1. BlitzBiz searches under the assumption that the contacts without a schedule time information has a schedule time of 00:00
  
    Use case ends.

* 2a. No contacts with schedules in the given range are found.
    * 4a1. BlitzBiz informs the user that there were no matches found.
      
     Use case ends.

**Use case: UC10 - Add/Edit Schedule**

**MSS**

1.  User requests to add/edit a schedule of a contact by providing the schedule name, schedule date and schedule time.
2.  BlitzBiz displays list after updating the contact with the new schedule details.

    Use case ends.

**Extensions**

* 1a. The contact does not exist.
    *  1a1. BlitzBiz displays an error message.

    Use case ends.
* 1b. The user did not enter a schedule name or schedule time.
    *  1b1. BlitzBiz only adds/edits the schedule date.
  
     Use case ends.
* 1c. The user did not provide a schedule date.
    *  1c1. BlitzBiz displays an error message.
    *  1c2. User enters the parameters again.
    *  Steps 1c1-1c2 are repeated until a schedule date is entered.

    Use case ends.
*  1d. The user provided an invalid schedule date/ schedule time/ schedule name.
    *  1d1. BlitzBiz displays an error message.
    *  1d2. User enters the parameters again.
    *  Steps 1d1-1d2 are repeated until the format entered is correct.

  Use case resumes from step 2.

**Use case: UC11 - Backup data**

**MSS**

1.  User requests to back up the contact list.
2.  BlitzBiz creates an address.json file with the contact list.

    Use case ends.

**Extensions**

* 1a. The addressbook.json file already exist.
    *  1a1. BlitzBiz overwrites the old file. 
  
    Use case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to load within 4 seconds while holding up to 1000 contacts.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. The product must be intuitive to people without technical background.
5. For critical user actions such as searching for a contact or updating information, the system must respond within 1 second for up to 90% of operations.
6. The program should have a size of less than 100MB even with 1000 contacts.
7. The application should function fully when offline.
8. When a user executes a command there should be clear feedback on whether the task was successful.
9. The user should be notified on the reason why the command they give is not working(i.e. incorrect input, lack of contacts in system)

*{More to be added}*

### Glossary

* **API**: An application programming interface (API) is a way for computer programs to interact with one another by providing services
* **CLI**: The command line interface (CLI) is a text-based interface that interacts with the computer's operating system
* **GUI**: The graphical user interface (GUI) is an interface users interact with that provides visuals, potentially with some point-and-click functionalities
* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Point-of-Contact**: The person to contact for obtaining goods and/or services, usually from a larger organisation
* **Contact**: A contact information of a person, a company or a point-of-contact of a company.
* **Tag**: A label attached to a contact for grouping purposes. Common tags are `Supplier`, `Customer` and `Partner`, but can be flexible to suit the user's needs
* **UI**: The user interface (UI) is a form of interface that the user interacts with

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   2. Double-click the jar file 
       Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   2. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   3. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Adding a contact

1. Adding a contact with at least one of the contact fields (i.e. `phone`, `email`, `address`, or any of the social media handles) 

    1. Prerequisites: N/A

    2. Test case: `add n/testAddEmailOnly e/test@example.com`<br>
       Expected: Contact is added with name as `testAddEmailOnly` and email as `test@example.com`. No other fields should be present. <br>
       Note: This test case can be repeated for any of the contact fields.

    3. Test case: `add n/testAddNoContactPresent`<br>
       Expected: No person is added. An error message should be shown.

    4. Other incorrect add commands to try: `add`, `add n/testName sd/2024-12-12`, `add n/testName t/friend` <br>
       Expected: Similar to previous.
   
2. Add a valid contact with a schedule

   1. Prerequisites: N/A
   
   2. Test Case: `add n/testAddWithSchedule p/91234567 sd/2024-12-12`
      Expected: Contact is added with the given name and phone fields, and a schedule field with date `2024-12-12`. The schedule should be shown similar to a tag.
   
   3. Test Case: `add n/testAddWithInvalidSchedule p/91234567 st/16:00`
      Expected: Contact is added with the provided name and phone values, but the schedule is ignored as it is missing the schedule date field.
      Note: This test case can be repeated with only the schedule name provided, or both the schedule name and time provided, without the schedule date.

### Schedule

1. Clearing a schedule from a contact with a schedule

   1. Prerequisites: The target contact should have an existing schedule. Suppose the target contact is at index 1.
   
   2. Test case: `schedule 1`
      Expected: The schedule field is removed. The contact no longer has a schedule.
   
2. Editing a schedule from a contact with a schedule
    1. Prerequisites: The target contact should have an existing schedule. Suppose the target contact is at index 1.
   
   2. Test case: `schedule 1 sd/2024-12-13`
      Expected: The schedule field of contact at index 1 is now `2024-12-13`.
      Note: If the schedule date is already `2024-12-13` (i.e. same as that provided in the command), then the command fails as the schedule is unedited.
      Note: This test case can be repeated for other schedule fields, schedule name and schedule time.
   
3. Adding a schedule to a contact with no schedule
   1. Prerequisites: The target contact should not have an existing schedule. Suppose the target contact is at index 1.
   
   2. Test case: `schedule 1 sd/2024-12-12`
      Expected: The contact now has a schedule named `schedule`, scheduled on `2024-12-12`.
      
   3. Test case: `schedule 1 st/16:00`
      Expected: The schedule command fails as there is no schedule date provided.


### Sorting a contact

1. Sort contact list by name 

    1. Prerequisites: Contact list should contain contacts
    2. Test case: `sort n/asc` and `sort n/` <br>
       Expected: Contact list is sorted alphabetically by name in ascending order

    3. Test case: `sort n/desc`<br>
       Expected: Contact list is sorted alphabetically by name in descending order

2. Sort contact list by schedule

    1. Prerequisites: Contact list should contain contacts with schedules

    2. Test Case: `sort sch/asc` and `sort sch/` <br>
       Expected: Contact list is sorted by schedule datetime in ascending order.

    3. Test Case: `sort sch/desc`
       Expected: Contact list is sorted by schedule datetime in descending order.

### Filter by tags

1. Filter by a single tag

    1. Prerequisites: Some contacts should have a tag called `friends`.
    2. Test case: `filter t/friends` <br>
       Expected: Contact list displays only contacts with a tag called `friends`.

2. Filter by multiple tags

    1. Prerequisites: Contact list should contain contacts with both tags called `friends` and `classmastes`.

    2. Test Case: `filter t/friends t/classmates` <br>
       Expected: Contact list displays only contacts with both tags called `friends` and `classmastes`.

### Search by schedule

1. Search for schedules after a starting time

    1. Prerequisites: Some contacts should have schedules with datetime after `2024-09-09 18:00`.
    2. Test case: `search b/2024-09-09 18:00` <br>
       Expected: Contact list displays the contacts with schedules after `2024-09-09 18:00`.

2. Search for schedules before an end time

    1. Prerequisites: Some contacts should have schedules with datetime before `2024-11-11 18:00`.

    2. Test Case: `search en/2024-11-11 18:00` <br>
       Expected: Contact list displays the contacts with schedules before `2024-11-11 18:00`.

3. Search for schedules between a starting and an end time

    1. Prerequisites: Some contacts should have schedules between `2024-09-09 18:00` and `2024-11-11 18:00`.

    2. Test Case: `search b/2024-09-09 18:00 en/2024-11-11 18:00` <br>
       Expected: Contact list displays the contacts with schedules between `2024-09-09 18:00` and `2024-11-11 18:00`.

### renameTags

1. rename existing tags to a new tag

    1. Prerequisites: Some contacts should have tags called `friends`.
    2. Test case: `renameTag ot/friends nt/bestFriends` <br>
       Expected: Contact list updates the contact's tags to `bestFriends`.

2. rename tags that don't exist

    1. Prerequisites: None of the contacts has a tag called `classmates`.
    2. Test case: `renameTag ot/classmates nt/bestFriends` <br>
       Expected: The command fails with an error thrown.

### Adding social media

1. Adding a social media account to a contact

    1. Prerequisites: The list contains at least one contact.
    2. Test case: `socialMedia 1 fb/John` <br>
       Expected: The contact will be updated showing a facebook account named John.

2. Adding multiple social media account to a contact

    1. Prerequisites: The list contains at least one contact.
    2. Test case: `socialMedia 1 fb/John ig/John` <br>
       Expected: The contact will only be updated showing an instagram account named John but not facebook account.

### Restore

1. Restore the last person deleted

    1. Prerequisites: The person has to be deleted within the same session.
    2. Test case: `restore` <br>
       Expected: The deleted person is restored.

2. Deleted person is added back before using `restore`

    1. Prerequisites: NA
    2. Test case: <br>
         1.`add n/test p/12345678` <br>
         2.`delete` (added person index) <br>
         3.`add n/test p/12345678` <br>
         4.`restore` <br>
       Expected: `This person already exists in the address book` error is displayed

3. Another person is edited to have the same name as deleted person before using `restore`.

    1. Prerequisites: NA
    2. Test case: <br>
       1.`add n/test p/12345678` <br>
       2.`delete` (added person index) <br>
       3.`edit (another person index) n/test` <br>
       4.`restore` <br>
       Expected: `This person already exists in the address book` error is displayed

4. There is no deleted person.
    1. Prerequisites: No contacts are deleted.
    2. Test case: `restore` <br>
       Expected: `Person has to be deleted previously to be restored` error message is shown

### Backup

1. Create a backup file

    1. Prerequisites: A data file exists at the data storage location `[JAR file location]/data/addressbook.json` and it is not the first command after downloading BlitzBiz.
    2. Test case: `backup` <br>
       Expected: Creates a backup save of the current BlitzBiz data at `[JAR file location]/backup/addressbook.json`.



## **Appendix: Effort**

This section describes the difficulties and challenges faced while working on this Brownfield project.
We detail some achievements accomplished by completing our iteration of the project.

1. Our team has a smaller number of members (only 4 members, compared to the norm of 5), so more complex features could not have been implemented. <br>
   We instead focused on features that are useful to users, and thought about our key value proposition, whilst maintaining the fact that the app is primarily for contact management.
2. Testing was an aspect that we found tedious and required a lot of effort. <br>
   Relying on the testing code infrastructure used in AB3 greatly helped to make the testing process more modular. <br>
   For example, when creating the tests for the `schedule` command, it was greatly inspired by the existing test infrastructure of the `edit` command.
3. While our project still focuses on managing contacts like AB3, our project introduces more complexity by managing multiple more entity types like schedules and social media while also incorporating viewing, utility and data management features.
   This required careful planning and integration to ensure compatibility between functions while ensuring ease of use for the users

## **Appendix: Planned Enhancements**

Team size: 4

1.	**Option to remove social media handles**: Currently, users are only allowed to add and update a contact's social media.
We plan to allow users to have the option to remove the handle by a command as such: `socialMedia INDEX` while will delete
the social media handle for the contact at `INDEX`.
2.	**Duplicate detection for `name` field**: Currently, duplicate names are not allowed (case-specific) EG. `John Doe` and 
another `John Doe` is not allowed. In the future, we will allow duplicate names considering some names are very common
while also notify the user of the name duplication Eg. `New person added: John Doe; Phone: 12345678. Note that there is already
an existing contact with the name John Doe.`. Additionally, near-matches (same spelling, different cases) will be considered: Adding `john doe` when there is already
a `John Doe` will also result in the duplication notification.
3.	**Enhance `name` field to accept more special characters**: Currently, we only accept alphanumeric characters in a contact's name.
We plan to expand this restriction to include other characters such as `-`, `/`, and `'` to accommodate for a bigger variety of names.
4.	**Phone number validation to allow more lengths and country code**: Currently, there is a limit of 8 - 15 
numeric digits allowed for contacts' phone number, but some countries have phone numbers longer than 15. We plan to
expand the limit to 3 - 20, and allow the inclusion of the `+` character for users to indicate country codes if needed.
5.	**More specific error messages so that the messages are not too long to improve readability**: Some of our error messages
such as the one for `add` can get a little long and users have to scroll to read the entire message. This message is shown regardless
of the type of error (invalid input, format issues) which results in the error message having to include much information about `add`.
We plan to use more specific error messages like `Invalid email! Emails must have multiple domains` and `Invalid prefix! Valid prefixes: n/NAME [p/PHONE] [e/EMAIL] [a/ADDRESS] [sn/SCHEDULE_NAME] [sd/SCHEDULE_DATE] [st/SCHEDULE_TIME] 
[cs/SOCIAL_MEDIA_HANDLE] [fb/SOCIAL_MEDIA_HANDLE] [ig/SOCIAL_MEDIA_HANDLE] [t/TAG]...` to shorten each error message and
let the user know which specific problem he is facing.
6.	**Allow a contact to have multiple phone numbers, emails, addresses and social media handles**: Currently, each contact
can only have 1 phone number, email, address and social media handle. We plan to accept multiple fields. Here is an example 
in the context of `socialMedia`:
If the contact at index 2 already has the social media handle `[ig-alexyeoh]`, 
running `socialMedia 2 fb/alexyh` will just update the handle to `[fb-alexyh]` instead of adding a Facebook handle on top 
of the Instagram one. We plan to improve on this, such that running `socialMedia 2 ig/alexyeoh fb/alexyh` will add both handles
to the contact, and running `socialMedia 2 fb/alexyh` when the contact already has `[ig-alexyeoh]` will add the handle `[fb-alexyh]` 
instead of replacing `[ig-alexyeoh]`.
7.	**If `edit` command does not actually edit the contact, raise an error message to the user**: Currently, if the user uses
the `edit` command on a contact but is editing it to the original details anyway (basically no change to the contact),
the `edit` success message will be shown, which can be misleading. We plan to return an error message `There was no change to the contact!` 
to notify the user instead.
8.  **Additional validation checks for email address**: Currently, we accept single domain email addresses, but we have realised
that it can cause security issues. So, we plan to add the restriction where each email address must have more than one domain label,
else an error message will be shown. Eg. `johnd@example` will no longer be accepted and only those with multiple domains can be
accepted Eg. `johnd@example.com`.
