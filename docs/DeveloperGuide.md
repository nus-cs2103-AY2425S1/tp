---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# Dream Day Designer Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

This project is a fork of [`AddressBook Level-3`](https://github.com/se-edu/addressbook-level3).

The icon image for the app was found on [Flaticon.com](https://www.flaticon.com/free-icon/wedding-rings_531864?term=wedding&page=1&position=1&origin=tag&related_id=531864) and was created by [Freepik](https://www.flaticon.com/authors/freepik).

* Libraries used: [JavaFX](https://openjfx.io/), [Jackson](https://github.com/FasterXML/jackson), [JUnit5](https://github.com/junit-team/junit5)

--------------------------------------------------------------------------------------------------------------------

## **Getting Started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

This architecture diagram gives a high-level overview of the app's design.

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

**Architecture Components**

**`Main`** consists of [`Main`](https://github.com/AY2425S1-CS2103T-F13-3/tp/blob/master/src/main/java/seedu/ddd/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-F13-3/tp/blob/master/src/main/java/seedu/ddd/MainApp.java), and is in charge of the app launch and shut down.

* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The app consists of the following components:

* [**`UI`**](#ui-component): Renders user interface (UI) components of the app.
* [**`Logic`**](#logic-component): Exexcutes commands.
* [**`Model`**](#model-component): In-memory representation of the app's data.
* [**`Storage`**](#storage-component): Stores data locally.
* [**`Commons`**](#common-classes): A collection of classes used by multiple components.

**Component Interactions**

This sequence diagram shows how the components interact with each other for a scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

With the exception of `Commons`, each component is structured as such:

* Component API is defined in an `interface` named `XYZ`, where XYZ is the name of the component.
* Implements its functionality using a concrete `XYZManager` class.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

### UI Component

**API** : [`Ui.java`](https://github.com/AY2425S1-CS2103T-F13-3/tp/blob/master/src/main/java/seedu/ddd/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `DisplayedListPanel` etc. Each UI component inherits from the abstract `UiPart` class, which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-F13-3/tp/blob/master/src/main/java/seedu/ddd/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-F13-3/tp/blob/master/src/main/resources/view/MainWindow.fxml).

The `UI` component:

* Takes user input and pass it to the `Logic` component.
* Listens for changes to `Model` data so that the UI can be updated with the modified data.
* Depends on `Model` (specifically `Client`, `Vendor` and `Event`) since it render data from the items inside `Model`.

When a user executes a command, the results of their command will be rendered within a single `DisplayedListPanel`. `DisplayedListPanel` contains some number of `DisplayedCard`, which could either be a `ClientCard`, `VendorCard` or an `EventCard`.

The items that need to be displayed on the GUI is stored in an `ObservableList<Displayable>`, and are retrieved from the `Model` using `Model::getDisplayedList`.

### Logic Component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-F13-3/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

The `Logic` component:

* Receives user input and parses it.
* Interacts with the `Model` component to modify the underlying data.
* Returns feedback to the user through a `CommandResult`.

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to delete a person). Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to complete execution.
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the `AddressBookParser` works:

* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser`, where `XYZ` is a placeholder for the specific command name (e.g `DeleteCommandParser`).
* The newly created parser parses the user command and creates a `XYZCommand` object (e.g `DeleteCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g. `DeleteCommandParser`) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model Component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-F13-3/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />

The `Model` component:

* Stores the address book data (i.e. all `Contact` objects inside a `UniqueContactList` and all `Event` objects inside a `UniqueEventList`).
* Stores the currently 'selected' `Contact` objects (e.g., results of a search query) as a separate filtered list.
* Stores the currently 'selected' `Event` objects (e.g., results of a search query) as a separate filtered list.
* Stores an `ObservableList<Displayable>` to represent the objects which need to be displayed by `UI`. When either the filtered contact list or filtered event list gets updated, this list of displayed items will be updated as well.
* Stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* Does not depend on `Ui`, `Logic` or `Storage`.

There are 2 main types of objects in Dream Day Designer: `Contact` and `Event`.

#### `Contact`

Represents a contact within the address book. `Contact` can be either:

* **`Client`**: Clients are contacts who are hosting the weddings.
* **`Vendor`**: Vendors provide services for the wedding (e.g. catering).

#### `Event`

Represents a wedding event.

### Storage Component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-F13-3/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component:

* Saves both address book data and user preference data in JSON format, and read them back into corresponding objects.
* Inherits from both `DreamDayDesignerStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* Depends on `Model` since the data model of `Contact` and `Event` are defined within `Model`.

### Common Classes

Classes used by multiple components are in the `seedu.ddd.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

--------------------------------------------------------------------------------------------------------------------

## **Specific Guides**

* [Documentation Guide](Documentation.md)
* [Setting Up Guide](SettingUp.md)
* [Testing Guide](Testing.md)
* [Logging Guide](Logging.md)
* [Configuration Guide](Configuration.md)
* [DevOps Guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product Scope

**Target user profile**:

Freelance wedding planners with many client and vendor contacts
* Reasonably comfortable with CLI apps
* Likes flexibility in scheduling
* Works alone

**Value proposition**: Provide a way to easily select suitable vendors for a wedding event given specific parameters such as budget, time, commission, client needs (e.g. culture, style), location.


### User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                            | I want to …​                                                | So that I can…​                                                                       |
|-----|------------------------------------|-------------------------------------------------------------|---------------------------------------------------------------------------------------|
| `***` | forgetful user                     | see a list of all upcoming events                           | plan ahead and allocate my time effectively                                           |
| `***` | normal user                        | create a new event                                          | keep track of which vendors and clients are associated with an event                  |
| `***` | normal user                        | view all current events                                     | quickly see what events are coming up soon                                            |
| `***` | normal user                        | search for a specific vendor                                | quickly access vendor details without manually looking through the whole address book |
| `***` | normal user                        | tag vendors by categories                                   | quickly filter clients who require a specific service                                 | |
| `***` | user with erratic work schedule    | close the app and return later to continue where I left off | resume planning without disruption                                                    |
| `***` | user with many clients             | view all clients                                            | get a quick summary of my clients' details                                            |
| `***` | normal user                        | view contacts by their tags                                 | quickly access client or vendor details related to a particular category              |
| `***` | user with many vendors             | view all vendor details from a single dashboard             | have a comprehensive overview of all available services                               |
| `**` | forgetful user                     | assign dates for an event                                   | manage timelines more effectively                                                     |
| `**` | meticulous user                    | add tags to each vendor                                     | track special considerations or preferences for future references                     |
| `**` | user                        | delete an existing event                                    | remove events when my clients cancel on me                                            |
| `**` | normal user                        | tag clients by event type or size                           | quickly filter clients who require similar services                                   |
| `**` | normal user                        | add new clients/vendors on the go                           | input information immediately after meeting them                                      |
| `**` | user with many clients             | assign multiple clients to the same event type              | group similar wedding themes or sizes together                                        |
| `**` | user with many clients and vendors | add notes to each client                                    | remember specific requirements for their event                                        |
| `**` | user with many vendors             | edit existing vendor details                                | update contact information or service offerings as needed                             |
| `**` | user with many vendors             | assign multiple vendors to the same category                | compare and choose vendors more easily                                                |
| `*` | busy user                          | see an overview of my workload for the week/month           | better manage my time and commitments                                                 |
| `*` | normal user                        | update vendors of an existing event                         | change the vendors when there are changes in requirements or circumstances            |
| `*` | normal user                        | attach files (contracts, proposals) to vendors/clients      | have all necessary documents in one place                                             |
| `*` | normal user                        | customize how data is displayed (list view, card view)      | organize information in a way that suits my preferred workflow                        |
| `*` | user with many events              | archive old events                                          | keep my dashboard uncluttered with only active events displayed                       |
| `*` | user with many events              | quickly restore archived events                             | revisit previous event details if needed for reference                                |
| `*` | user with many vendors/client data | quickly access old data for vendors and clients             | avoid re-entering details when planning similar events                                |
| `*` | seasoned user                      | use keyboard shortcuts                                      | work more quickly                                                                     |

### Use Cases

(For all use cases below, the **System** is `Dream Day Designer` and the **Actor** is the `Wedding planner`, unless specified otherwise)

**Name: UC01 - Add Contact (Vendor/Client)**

**Main Success Scenario (MSS):**
1. Wedding planner selects the option to create a new contact.
2. System requests for the details of the contact (client or vendor).
3. Wedding planner enters the required details (name, phone number, etc.).
4. System validates the entered details (check for format, duplication, etc.).
5. System creates the contact and assigns a unique ID to the contact.
6. System displays a success message confirming the creation of the contact.

    Use case ends.

**Extensions:**

* 3a. System detects an error in the entered data (e.g., invalid phone number).

  * 3a1. System requests the correct data.
  * 3a2. Wedding planner enters new data.

    Use case resumes from step 4.

* 5a. The contact already exists in the system (duplicate contact).

  * 5a1. System displays a message that the contact already exists.

    Use case ends.

**Guarantees:**

The contact is successfully created and stored in the system if all input data is valid.
Duplicate contacts will not be created.

___
**Name: UC02 - Delete Contact (Vendor/Client)**

**Main Success Scenario (MSS):**
1. Wedding planner requests to list all contacts.
2. System displays a list of contacts.
3. Wedding planner specifies which contact he wishes to delete.
4. System displays a message for the successful deletion of contact.

   Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. System is unable to locate the contact to be deleted.

  * 3a1. System displays an error message.

    Use case ends.
  
* 3b. System checks for association and find out this vendor/client is the last one related to a event.

  * 3b1. System displays a message about the association.

    Use case ends.



**Guarantees**
* The contact is successfully deleted from the system, and any persistent storage.

**Name: UC03 - List all Contacts**

**Preconditions:**
1. Contacts are saved properly.

**Guarantees:**
1. The user's previously saved contacts will be listed with their details.
2. Contacts are sorted based on when it was added.

**Main Success Scenario (MSS):**
1.  User requests for previously saved contacts.
2.  System displays the saved contacts to the user.

    Use case ends.

**Extensions**

* 2a. System is unable to get saved contacts.

  * 2a1. System informs the user that the file is corrupted.

    Use case ends.

* 2b. The list is empty.

  * 2b1. System informs the user that there are no saved contacts.

    Use case ends.

**Name: UC04 - Add Event**

**Preconditions:**
1.  Have at least one valid Client and one valid Vendor saved in System

**Main Success Scenario (MSS):**
1. Wedding planner selects the option to create a new event.
2. System requests for details of the event.
3. Wedding planner enters the required details (related client, related vendor, etc.).
4. System validates the entered details (check for the format, duplication, etc.).
5. System creates the event and assigns a unique ID to the event.
6. System displays a success message confirming the creation of the event.

    Use case ends.

**Extensions**

* 3a. System detects an error in the entered data (e.g., invalid client ID).

  * 3a1. System request the correct data.
  * 3a2. Wedding planner enters the new data.

    Use case resumes from step 4.
  
* 5a. The event already exists in the system (duplicate event).

  * 5a1. System displays a message that the event already exists.
    
    Use case ends.

**Name: UC05 - Delete Event**

**Main Success Scenario (MSS):**
1. Wedding planner requests to list all events.
2. System displays a list of events.
3. Wedding planner specifies which event he wished to delete.
4. System displays a message for the successful deletion of the event.

    Use case ends.

**Extensions**

* 2a. The list is empty.

    Use case ends.

* 3a. System is unable to locate the event to be deleted.

  * 3a1. System displays an error message.

    Use case ends.

**Guarantees:**
1. The event is successfully deleted from the system, and any persistent storage.

**Name: UC06 - List all Events**

**Preconditions:**
1. Events are saved properly.

**Main Success Scenario (MSS):**
1. User requests for previously saved events.
2. System displays the saved events to the user.

    Use case ends.

**Extensions**

* 2a. System is unable to get saved events.

  * 2a1. System informs the user that the file is corrupted.
  
    Use case ends.

* 2b. The list is empty.

  * 2b1. System informs the user that there are no saved events.

    Use case ends.

**Guarantees:**
1. The user’s previously saved events will be listed with their details.
2. Events are sorted based on when it was added.

**Name: UC07 - Edit contact**

**Preconditions:**
Have at least valid contact type (client/vendor) stored in the system.

**Main Success Scenario (MSS):**
1. Wedding planner requests to list all contacts.
2. System displays a list of contacts.
3. Wedding planner specifies which contact he wishes to edit and enters the respective details (name, phone number etc.) he requests to change.
4. System validates the entered detail (check for format, duplication, etc.).
5. System updates the contact with the new provided details.
6. System displays a success message confirming the creation of the contact.

    Use case ends.

**Extensions**

* 3a. System detects an error in the entered data (e.g. invalid phone number).

  * 3a1. System requests the correct data.
  * 3a2. Wedding planner enters new data.

    Use case resumes from step 4.

* 5a. The contact already exists in the system (duplicate contact).

  * 5a1. System displays a message that the contact already exists.

    Use case ends.

**Guarantees:**
1. The contact is successfully edited and the system and any persistent storage have updated this contact. Duplicate contact will not happen.

**Name: UC08 - Help**

**Main Success Scenario (MSS):**
1. Wedding planners want to see product related material.
2. System pops out a new window with the hyperlink to the product website.

    Use case ends.

**Name: UC09 - Clear**

**Main Success Scenario (MSS):**
1. Wedding planner decides to clear the database.
2. System clear the whole database and update the local JSON file.

    Use case ends.

**Name: UC10 - Exit**

**Main Success Scenario (MSS):**
1. Wedding planner decides to exit the system.
2. System saves the data to the local JSON file and terminates the program.

    Use case ends.

### Non-Functional Requirements

1. Compatibility: Should work on any mainstream OS (Windows/macOS/Linux) as long as Java `17` or above is installed.
2. Compatibility: The system should work on both 32-bit and 64-bit environments.
3. User Experience: Should be able to hold up to 1000 contacts and events without a noticeable sluggishness in performance for typical usage.
4. User Experience: The system should respond within two seconds.
5. User Experience: A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
6. Privacy: User data must remain on the local machine and not be shared or transmitted to any external services unless explicitly requested by the user (e.g., exporting contacts).

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for Manual Testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and Shutdown

1. Initial launch

   1. Download the jar file `ddd.jar` and copy into an empty folder 
   2. Open a terminal and navigate to the directory containing to the jar file.
   3. Run `java -jar ddd.jar` in the terminal. <br>
      Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.
   2. Re-launch the app by running `java -jar ddd.jar` again.<br>
       Expected: The most recent window size and location is retained.

### Adding a Contact
1. Adding a client to the existing contact list.
    1. Prerequisites: Address book is empty or does not contain `Contacts` that have the same `Name` or `Phone` as those used below.
    2. Test case: `add -c n/Alice p/91234567 e/alice@gmail.com a/1 Clementi Street Blk 25 t/vegan t/urgent`<br>
       Expected: Client will be added to the contact list. Details of the newly created client shown in the status message.
    3. Test case: `add n/Alice p/91234567 e/alice@gmail.com a/1 Clementi Street Blk 25 t/vegan t/urgent`<br>
       Expected: Add is unsuccessful since `-c` is not specified. Error details shown in the status message. Status bar remains the same.
    4. Test case: `add -c n/Alice e/alice@gmail.com a/1 Clementi Street Blk 25 t/vegan t/urgent`<br>
       Expected: Add is unsuccessful since `p/` is not specified. Error details shown in the status message. Status bar remains the same.
       1. Similar commands to try: Missing `n/`, `e/` or `a/`. <br> Expected: Same as above. 
    5. Test case: `add -c n/Alice p/91234567 e/alice@gmail.com a/1 Clementi Street Blk 25 t/vegan t/urgent` performed twice.<br>
       Expected: Second add is unsuccessful due to duplicate name and phone. Error details shown in the status message. Status bar remains the same.
    6. Test case: `add -c n/Alice (Yeo) p/91234567 e/alice@gmail.com a/1 Clementi Street Blk 25 t/vegan t/urgent` <br>
       Expected: Add is unsuccessful due to invalid `Name` input. Error details shown in the status message. Status bar remains the same.
       1. Similar commands to try: Invalid inputs for `p/`, `e/`, `a/` or `t/`. <br> Expected: Same as above.


2. Adding a vendor to the existing contact list.
    1. Prerequisites: Address book is empty or does not contain `Contacts` that have the same `Name` or `Phone` as those used below.
    2. Test case: `add -v n/Alice p/91234567 e/alice@gmail.com a/1 Clementi Street Blk 25 s/Catering t/budget`<br>
       Expected: Vendor will be added to the contact list. Details of the newly created vendor shown in the status message.
    3. Test case: `add n/Alice p/91234567 e/alice@gmail.com a/1 Clementi Street Blk 25 s/Catering t/budget`<br>
       Expected: Add is unsuccessful since `-v` is not specified. Error details shown in the status message. Status bar remains the same.
    4. Test case: `add -v n/Alice p/91234567 e/alice@gmail.com a/1 Clementi Street Blk 25 t/budget`<br>
       Expected: Add is unsuccessful since `p/` is not specified. Error details shown in the status message. Status bar remains the same.
        1. Similar commands to try: Missing `n/`, `p/`, `e/` or `a/`. <br> Expected: Same as above.
    5. Test case: `add -c n/Alice p/98765432 e/alice@gmail.com a/1 Clementi Street Blk 25 t/budget` followed by 
       `add -v n/Alice p/91234567 e/alice@gmail.com a/1 Clementi Street Blk 25 s/Catering t/budget`<br>
        Expected: Second add is unsuccessful due to duplicate phone. Error details shown in the status message. Status bar remains the same.
    6. Test case: `add -v n/Alice (Yeo) p/91234567 e/alice@gmail.com a/1 Clementi Street Blk 25 s/Catering t/budget` <br>
       Expected: Add is unsuccessful due to invalid `Name` input. Error details shown in the status message. Status bar remains the same.
        1. Similar commands to try: Invalid inputs for `p/`, `e/`, `a/`, `s/` or `t/`. <br> Expected: Same as above.

### Adding an Event
1. Adding an event to the existing event list.
    1. Prerequisites: Address book is empty or does not contain `Events` that have the same `Name` as those used below.
    2. Test case: `add -e n/Sample Wedding des/Wedding reception d/2000-01-01 c/0 v/1`<br>
       Expected: Event will be added to the event list. Details of the newly created event shown in the status message.
    3. Test case: `add n/Sample Wedding des/Wedding reception d/2000-01-01 c/0 v/1`<br>
       Expected: Add is unsuccessful since `-e` is not specified. Error details shown in the status message. Status bar remains the same.
    4. Test case: `add -e n/Sample Wedding des/Wedding reception d/2000-01-01 v/1`<br>
       Expected: Add is unsuccessful since `c/` is not specified. Error details shown in the status message. Status bar remains the same.
        1. Similar commands to try: Missing `n/`, `des/`, `d/` or `v/`. <br> Expected: Same as above.
    5. Test case: `add -e n/Sample Wedding des/Wedding reception d/2000-01-01 c/0 v/1` followed by
       `add -e n/Sample Wedding des/Wedding reception 2 d/2000-02-01 c/1 v/2`<br>
       Expected: Second add is unsuccessful due to duplicate name. Error details shown in the status message. Status bar remains the same.
    6. Test case: `add -e n/Sample (Wedding) des/Wedding reception d/2000-01-01 c/0 v/1` <br>
       Expected: Add is unsuccessful due to invalid `Name` input. Error details shown in the status message. Status bar remains the same.
        1. Similar commands to try: Invalid inputs for `des/`, `d/`, `c/` or `v/`. <br> Expected: Same as above.
    7. Test case: `add -e n/Sample (Wedding) des/Wedding reception d/2000-01-01 c/0 v/1` where there is no `Client` with `Id` 0.<br>
       Expected: Add is unsuccessful due to invalid `Client` index. Error details shown in the status message. Status bar remains the same.
        1. Similar commands to try: Invalid index for `v/`. <br> Expected: Same as above.

### Editing a Contact
1. Editing an existing contact in the contact list
    1. Prerequisites: Address book contains the `Contact` specified below.
    2. Test case: `edit id/1 n/Bob`<br>
       Expected: Contact will be edited accordingly. Details of the newly edited contact will be shown in the status message.
    3. Test case: `edit 1 n/Bob` where there is at least 1 `Contact` displayed on the list. <br>
     Expected: Contact will be edited accordingly. Details of the newly edited contact will be shown in the status message.
    4. Test case: `edit n/Bob` <br>
       Expected: Edit is unsuccessful as `Index` or `Id` is unspecified. Error details shown in the status message. Status bar remains the same.
    5. Test case: `edit id/1 n/Bob` and there is another `Contact` with the same `Name` (Bob).<br>
       Expected: Edit is unsuccessful due to duplicate `Name`. Error details shown in the status message. Status bar remains the same.
       1. Similar commands to try: Duplicate `Phone`. <br> Expected: Same as above.
    6. Test case: `edit id/1 n/(Bob)`<br>
       Expected: Edit is unsuccessful due to invalid `Name` input. Error details shown in the status message. Status bar remains the same.
       1. Similar commands to try: Invalid inputs for `id/`, `p/`, `e/`, `a/`, `s/` or `t/`. <br> Expected: Same as above.
    7. Test case: `edit id/1 s/Catering` where `Id` 1 corresponds to a `Client`. <br>
       Expected: Edit is unsuccessful since `Client` does not have a `Service`. Error details shown in the status message. Status bar remains the same.
    8. Test case: `edit x ...` or `edit id/y ...` where x is larger than display size or non-positive, and y does not correspond to any `Contact` <br>
       Expected: Edit is unsuccessful as `Index` or `Id` is invalid. Error details shown in the status message. Status bar remains the same.

### Listing
1. Listing contacts
    1. Prerequisites: Address book contains at least one `Client` and `Vendor`.
    2. Test case: `list`<br>
       Expected: All contacts will be displayed. The number of contacts displayed will be shown in the status message.
    3. Test case: `list ...` where no flags or parameters like `n/` or `-c` are included in ... <br>
       Expected: All contacts will be displayed since everything after `list` is ignored. The number of contacts displayed will be shown in the status message.
    4. Test case: `list n/Bob Choo`<br>
       Expected: All contacts that have `Name` matching any of `Bob` or `Choo` will be displayed. The number of contacts displayed will be shown in the status message.
       1. Similar commands to try: `list` with `id/`, `n/`, `p/`, `e/`, `a/`, `t/`. <br> Expected: Same as above.
    5. Test case: `list n/Bob Choo a/Blk 555`<br>
       Expected: All contacts that have `Name` matching **any** of `Bob` or `Choo` **and** `Address` matching **any** of `Blk` or `555` will be displayed. The number of contacts displayed will be shown in the status message.
       1. Similar commands to try: `list` with any combination of `id/`, `n/`, `p/`, `e/`, `a/`, `t/`. <br> Expected: Same as above.
    6. Test case: `list -c -v -e` <br>
       Expected: Displayed list is not updated. Error details shown in the status message. Status bar remains the same. 

2. Listing clients
    1. Prerequisites: Address book contains at least one `Client`.
    2. Test case: `list -c`<br>
       Expected: All clients will be displayed. The number of clients displayed will be shown in the status message.
    3. Other test cases specified above for `list` are applicable for `list -c`. <br>
       Expected: Only relevant clients will be displayed. The rest of the expected behaviour is the same as above.

3. Listing vendors
    1. Prerequisites: Address book contains at least one `Vendor`.
    2. Test case: `list -v`<br>
       Expected: All vendors will be displayed. The number of vendors displayed will be shown in the status message.
    3. Other test cases specified above for `list` are applicable for `list -v`. The `s/` parameter can be used here. <br>
       Expected: Only relevant vendors will be displayed. The rest of the expected behaviour is the same as above.

4. Listing events
    1. Prerequisites: Address book contains at least one `Event`.
    2. Test case: `list -e`<br>
       Expected: All events will be displayed. The number of events displayed will be shown in the status message.
    3. Other test cases specified above for `list` are applicable for `list -e`, and the relevant parameters include `n/`, `des/`, `d/`, `id/`. <br>
       Expected: Relevant events will be displayed. The rest of the expected behaviour is the same as above.

### Deleting a Record

1. Deleting a record (`Contact` or `Event`) while multiple records are displayed
   1. Prerequisites: List contacts/events using the `list` command. Multiple records in the list.
   2. Test case: `delete 1`<br>
      Expected: First record is deleted from the list. Details of the deleted record shown in the status message.
   3. Test case: `delete 0`<br>
      Expected: No record is deleted. Error details shown in the status message. Status bar remains the same.
   4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size or non-positive)<br>
      Expected: Similar to previous.

### Saving Data

1. Missing `ddd.json` file in the `data` folder
   1. Expected: `ddd.json` will be created in the `data` folder and populated with some sample data.

2. Corrupted `ddd.json` file
   1. Test case: Modify the json file such that the parser cannot read it properly. For example, have duplicate `Clients`
   2. Expected: `ddd.json` will be cleared upon initialisation of the application, i.e. there is no data left in the address book
