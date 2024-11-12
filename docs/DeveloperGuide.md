---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# SocialBook Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* Co-pilot for code completion, comments and documentations.

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

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103-F10-2/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103-F10-2/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initialises the other components in the correct sequence, and connects them up with each other.
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

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103-F10-2/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103-F10-2/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103-F10-2/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103-F10-2/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

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
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103-F10-2/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="650" height="1000"/>


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

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103-F10-2/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

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

### Seed feature

#### Implementation

The seed feature seeds dummy data into SocialBook. In the event that the user clears all the contacts in SocialBook, the user can execute the seed command to seed sample data into the SocialBook and continue trying out its features.

The seed command works just like any other `Command` object and how a `Command` object communicates with the `Model` is explained in the [Logic component](#logic-component).

The seed command adds a `Person` object, in `SampleDataUtil` to the `Model`, if the `Person` object is not already inside SocialBook.

The following activity diagram summarises what happens when a user executes the seed command:

<puml src="diagrams/SeedActivityDiagram.puml" alt="SeedActivityDiagram" />

### View feature

#### Implementation

The view feature toggles the card status of a contact in SocialBook to either show all of their information or an abridged version of it.

The view command works just like any other `Command` object and how a `Command` object communicates with the `Model` is explained in the [Logic component](#logic-component).

The activity diagram below shows how a view command toggles the card status of a contact:

<puml src="diagrams/ViewActivityDiagram.puml" alt="ViewActivityDiagram" />


### \[Proposed\] Visit History

The visit history feature allows users to save a record of visits to contacts with optional remarks. This allows social workers to better keep track of their interactions with beneficiaries. 

The Model component would be altered such that each person would now store a visit history object:

<puml src="diagrams/VisitHistory.puml" />

It is optional to include a remark when adding a visit to the visit history. Given this implementation the person's date of last visit can be obtained from visit history.


### \[Proposed\] Emergency Contact Expansion

The emergency contact was designed to only be a phone number in the current implementation. It is intended to extend this so that the emergency contact includes a name and phone number.

A current contact can be assigned as another person's emergency contact. It is also possible to create a person who is only an emergency contact. To generalize we create a class called contactable person which has all the necessary methods and values for an emergency contact:

<puml src="diagrams/EmergencyContact.puml" />

A person will then store a ContactablePerson as their emergency contact.

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

* social worker in Singapore
* need to manage around 100 beneficiaries
* job scope is to befriend beneficiaries and provide emotional support and assistance where required
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: the address book acts as an easy way to locate/contact families and keep track of how long it has been since their last visit.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                   | I want to …​                                                               | So that I can…​                                                                                        |
|----------|-------------------------------------------|----------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------|
| `* * *`  | social worker                             | delete a contact                                                           | remove the contact when I no longer serve them so that the contact list does not get too long          |
| `* * *`  | social worker/new user                    | add contact with phone number                                              | remember the person I serve                                                                            |
| `* * *`  | social worker/new user                    | add address                                                                | easily look up without needed to look up database/files                                                |
| `* * *`  | social worker                             | view my list of contacts                                                   | see the beneficiaries I serve                                                                          |
| `* *`    | overwhelmed with many households          | take note of how long it has been since I visited each house               | ensure that no house gets forgotten in my scheduling                                                   |
| `* *`    | new user learning the interface           | check out basic commands that are frequently used with a help command      | learn the basic usage of the program more quickly                                                      |
| `* *`    | user with multiple contacts per household | tag multiple users to be from the same household                           | simplify the process of contacting other household members                                             |
| `* *`    | social worker                             | prioritize contacts                                                        | allow myself to be able to prioritise household with special needs                                     |
| `* *`    | social worker                             | add alternative contact method                                             | add a contact for elderly who do not have a phone                                                      |
| `* *`    | social worker/expert user                 | look for emergency contact quickly                                         | inform next-of-kin when something happen                                                               |
| `* *`    | social worker/expert user                 | add family contact                                                         | know how many people in the household                                                                  |
| `* *`    | social worker/expert user                 | add notes                                                                  | remember if I need to take precaution before visiting the person/family or preparations i need to make |
| `* *`    | social worker/expert user                 | sort contacts by lexicographical order                                     | find contact quickly if I remember the name                                                            |
| `* *`    | social worker/expert user                 | search through contacts using keywords                                     | find contact quickly based on key words within the contact information                                 |
| `* *`    | social worker/expert user                 | add alternative phone number                                               | have another way of reaching the beneficiary                                                                   |
| `* *`    | social worker/new user                    | edit contact                                                               | edit the contact without the need to delete and create a new one                                       |
| `* *`    | forgetful individual                      | add tags which explain what service the person needs                       | remember and be able to meet the needs of beneficiaries                                                |
| `* *`    | holder of sensitive information           | lock the SocialBook behind a password                                      | avoid having unsolicited sharing of personal information                                               |
| `* *`    | max/expert user                           | add the medicinal information of beneficiaries                             | to know when to follow up on critical medicines                                                        |
| `* *`    | new user                                  | see a sample of the product features on display                            | so that I know how a feature can be used to maximise the value                                         |
| `*`      | has rotating beneficiaries                | remove several contacts at once, when beneficiaries no longer require care | so that I can make space for new beneficiaries and not keep track of old ones                          |
| `*`      | max/expert user                           | know the households I need to visit on a certain day                       | so that I can schedule my day and not forget to visit any households                                   |
| `*`      | user short on time                        | visit households that are geographically close in the same day/visit       | minimise the travelling time for myself                                                                |
| `*`      | user switching devices                    | transfer the SocialBook contacts from one device to another                | avoid having to manually re-enter all the current contacts                                             |
| `*`      | max/expert user                           | export all contacts to a txt file/excel file                               | so that I have a copy of all beneficiaries, past and present, in a larger database                     |
| `*`      | max/expert user                           | record social media credentials of a contact, if they have any             | so that I can get to know their lives better and establish a closer bond                               |
| `*`      | max/expert user                           | send reminder to my email a day before my visit                            | so that I will not forget to visit a household                                                         |
| `*`      | max/expert user                           | upload pictures of my beneficiaries                                        | so that I will not forget how they look like                                                           |
| `*`      | max/expert user                           | view contacts on google maps                                               | so that I can directly see where and how to go to the household                                        |
| `*`      | visual rememberer                         | add photos for each contact                                                | to match beneficiary names to their faces more easily                                                  |
| `*`      | loyal user                                | save contact information into a shareable form                             | share contacts with colleagues as need be                                                              |
| `*`      | experienced social worker                 | locate a link where I can share my views and suggestions on the app        | help the app be more user centered and helpful over time                                               |
| `*`      | efficient person                          | create visit paths based on proximity of beneficiaries                     | to be able to serve the most beneficiaries a day                                                       |
| `*`      | social person                             | add notes of on conversations with beneficiaries                           | develop stronger relationships by building rapport                                                     |

### Use cases

(For all use cases below, the **System** is the `SocialBook` and the **Actor** is the `user`, unless specified otherwise)

##### **Use case: UC01 - View all contacts**

**MSS**

1.  User requests to list contacts
2.  SocialBook shows a list of contacts

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 2b. The given list command has extraneous parameters.

  * 2b1. SocialBook shows an error message.
    
    Use case ends.

##### **Use case: UC02 - Add a contact**

**MSS**

1.  User requests to add a contact into the list with the specified details
2.  SocialBook adds the contact and displays the newly added contact

    Use case ends.

**Extensions**

* 2a. The given specified details is empty.

    * 2a1. SocialBook shows an error message.

      Use case ends.

* 2b. The given name and phone number is detected as duplicate.

    * 2b1. SocialBook shows an error message.

      Use case ends.

* 2c. There is an error in any of the specified details.

    * 2c1. SocialBook shows an error message.

      Use case ends.

##### **Use case: UC03 - View all information about a contact**

**MSS**

1. User requests to list contacts
2. SocialBook shows the list of all contacts
3. User requests to view all the information of a specific contact in the list
4. SocialBook displays the requested information to the user

    Use case ends.

**Extensions**

* 2a. There are no contacts available.

  Use case ends.

* 3a. The given index is non-numerical.

    * 3a1. SocialBook shows an error message.

      Use case resumes at step 2.

* 3b. The given index is out of bounds.

    * 3b1. SocialBook shows an error message.

      Use case resumes at step 2.

##### **Use case: UC04 - Delete a contact**

**MSS**

1.  User requests to list contacts
2.  SocialBook shows a list of contacts
3.  User requests to delete a specific contact in the list
4.  SocialBook deletes the contact

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. SocialBook shows an error message.

      Use case resumes at step 2.

##### **Use case: UC05 - Edit a contact**

**MSS**

1. User requests to list contacts
2. SocialBook shows a list of contacts
3. User requests to edit a specific contact with specified field(s)
4. SocialBook edits the contact

* 2a. The list is empty.

  Use case ends.

* 3a. The given specified field is/are empty.

  * 3a1. SocialBook shows an error message.

    Use case resumes at step 2.

* 3b. The given index is invalid.

    * 3b1. SocialBook shows an error message.

      Use case resumes at step 2.

* 3c. There is an error in any of the specified fields.

  * 3c1. SocialBook shows an error message.

    Use case resumes at step 2.

##### **Use case: UC06 - Find a contact**

**MSS**

1. User requests to list contacts
2. SocialBook shows a list of contacts
3. User requests to find a specific contact with specified field(s)
4. SocialBook displays the contact that matches the specified field(s)

* 2a. The list is empty.

  Use case ends.

* 3a. The given specified field is/are empty.

    * 3a1. SocialBook shows an error message.

      Use case resumes at step 2.

* 3b. There is an error in any of the specified fields.

    * 3b1. SocialBook shows an error message.

      Use case resumes at step 2.

##### **Use case: UC07 - Add remarks to a contact**

**MSS**

1. User requests to list contacts
2. SocialBook shows a list of contacts
3. User requests to add remarks to a specific contact
4. SocialBook adds the remark to the specified contact

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. SocialBook shows an error message.

      Use case resumes at step 2.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 200 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. Should not require an additional installer to use.
5. Should be packaged entirely into a single JAR file (i.e. the user will only need to download a single JAR file to use the product).
6. The size of the JAR file should not exceed 100MB.
7. Additional files such as DG (Developer Guide) and UG (User Guide) should not exceed 15MB in size per file and should be PDF-friendly.
8. The user interface should be intuitive enough for users who are not IT-savvy.
9. User interface should be displayed in British english.
10. Time zones and dates displayed are in Singapore Standard Time.
11. Should be used only by a single user (i.e. the data file created by a user should not be accessed by another user and the product should not be used on a shared computer accessible to multiple users).
12. The data file is stored locally into the user's hard disk.
13. The data file is stored in an editable, .JSON, format.
14. The information of a contact detail (address, contact number and date last visited) should be clear and visible.


### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Additional installer**: A file, program, driver that needs to be installed first, which in turn installs the product.
* **JAR file**: A Java ARchive file.
* **Singapore Standard Time**: Singapore time, which is 8 hours ahead of Coordinated Universal Time (UTC + 08:00)
* **Hard disk**: data storage device in laptops and computers.
* **.JSON file**: JavaScript Object Notation, stores and transports data using a human-readable text format.
* **SocialBook**: SocialBook is an address book hence SocialBook and AddressBook are interchangeable. SocialBook is used to reference the product while AddressBook refers to the Class object in source code.
* **Social worker**: refers to full-time employees of the [Ministry of Social and Family Development (MSF) or its subsidiaries](https://www.msf.gov.sg)

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

   1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar socialbook.jar` command to run the application. Expected: Shows the GUI with a set of sample contacts. The window size may not be optimal.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Deleting a person or persons

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

2. Deleting a person while found persons are being shown

    1. Prerequisites: Find people with names matching a particular keyword using the `find` command.

    1. Test case: `delete x`<br> (where x is less than or equal to the number of found persons)
       Expected: Contact x is deleted from the list. Details of the deleted contact shown in the status message.

    1. Test case: `delete 0`<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

3. Deleting persons while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    1. Test case: `delete 1 2`<br>
       Expected: First and second contact are deleted from the list. Names of the deleted contacts are shown in the status message.

    1. Test case: `delete x y`<br> (where x,y are greater than the number of listed persons)
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

    1. Test case: `delete 0 1`<br>
       Expected: First contact is deleted from the list. Name of the deleted contact and invalidity of the index 0 is shown in the status message.

### Saving data

1. Dealing with missing files

   1. Delete `config.json` and re-launch the app. 
      Expected: New `config.json` created. Existing data is not affected.

   2. Delete `preferences.json` and re-launch the app. 
      Expected: New `preferences.json` created. Existing data is not affected.

   3. Edit the line `"addressBookFilePath" : "data/socialbook.json"` to `"addressBookFilePath" : "data/data.json"` and re-launch the app. 
      Expected: App starts on clean slate (i.e. with sample data only).

   4. Delete `data/` or `data/socialbook.json`. 
      Expected:  New `data/socialbook.json` created. App starts on clean slate (i.e. with sample data only).

2. Dealing with corrupted data files

   1. Add `"p"` to a `"phone"` field in data file. 
      Expected: The person with the `"p"` in `"phone"` field is lost. The rest of the contacts still exist in the contact list.

   2. Add a new field `"newField" : "newField"` to a person. 
      Expected: The person with the new field is lost. The rest of the contacts still exist in the contact list.

   3. Remove `"remark"` field from a person. 
      Expected: The person with the missing `"remark"` field is lost. The rest of the contacts still exist in the contact list.

   4. Add a `","` to the `"remark"` field of a person. 
      Expected: The file data format is invalid. All data is lost. The app starts on clean slate.

### Sorting of person list

1. Sorting entries while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    1. Test case: `sort n/asc`<br> 
       Expected: Persons are sorted in ascending order according to ASCII. A message saying the list has been sorted by name in ascending order is displayed. 

    1. Test case: `sort d/desc`<br>
       Expected: Persons are sorted in descending order according to date of last visit. Where a person doesn't have a date of last visit they are at the end of the list. A message saying the list has been sorted by date of last visit in descending order is displayed.

2. Editing a parameter being sorted while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list. Sort using `sort n/asc`

    1. Test case: `edit 1 n/"name starting with a different letter"`
       Expected: A message is displayed showing the information of the updated person. The person moves in the display list according to the position of their new name (given ascending name order).

    1. Test case: `add n/hunter p/61234578`
       Expected: A message is displayed showing the information of the added person. The person is added to the list according to the position of their name (given ascending name order).

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

For reference, our group has 5 members.

1. Make `list` command with miscellaneous parameters error message more helpful. Currently, typing `list` with miscellaneous parameters, ex. `list 123`, will result in a message that states "Please ensure your command is valid!". To improve specificity this will be changed to ask user to remove miscellaneous parameters.
2. Prevent tags from allowing underscores. When searching for tags with underscores in the find command, the underscores are interpreted as an `or`. This means that searching for the tag "low_income" will bring up all tags containing "low" and "income" instead of just "low_income".
3. Remove `remark` command while keeping remark fields. Currently, remarks can be added via the edit command or the remark command but not the add command. Instead of having a dedicated `remark` command, we will allow users to use the `add` command to create a contact with remarks, or the `edit` command to modify the remark of a contact. This change solves an associated issue whereby the remark command currently collapses the detailed view on the contact.
4. Restrict the validation regex for emails to require at least 2 domain labels in the `domainName` region of the email. Traditionally, emails require at least 2 domain labels separated by a `.`, but the current implementation only requires one domain label. Something like `johnsmith@yahoo` is accepted as an email address when it should be rejected.
5. Update the error message displayed to the user by the `edit` command upon attempt to index with 0. The current error message for `edit 0 n/Bob` states "invalid command format" when it should mention that the index provided is invalid.
6. Update the error message displayed to the user by the `remark` command upon attempt to index with 0. The current error message for `remark 0 r/Hates frisbees` states "invalid command format" when it should mention that the index provided is invalid.
7. Update the error message displayed to the user by the `delete` command upon attempt to index with 0. The current error message for `delete 0` states "invalid command format" when it should mention that the index provided is invalid.
8. Update the error message displayed to the user by the `view` command upon attempt to index with 0. The current error message for `view 0` states "invalid command format" when it should mention that the index provided is invalid.
9. Improve clarity of phone number parsing message to give users clearer direction on the issue with the entered phone number (ex. to remove accidental extra spaces).
10. Improve clarity of emergency contact error message for invalid input. Currently, entering an invalid emergency contact will give an error message about phone numbers. This can be confusing when editing a phone number and emergency contact simultaneously. The message will be edited to more explicitly mention emergency contact and the specific issue. 
