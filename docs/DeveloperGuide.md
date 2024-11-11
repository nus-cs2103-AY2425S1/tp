---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# EventTory Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

Adapted from [_Address Book Level 3_](https://github.com/se-edu/addressbook-level3).

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the application.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103-F13-2/tp/blob/master/src/main/java/seedu/eventtory/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103-F13-2/tp/blob/master/src/main/java/seedu/eventtory/MainApp.java)) is in charge of the application launch and shut down.
* At application launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the application's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the application.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the application in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete v/1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103-F13-2/tp/blob/master/src/main/java/seedu/eventtory/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component" width="800" />

<box type="info" seamless>

**Note:** The `UiPart` class that UI components inherit from was removed from the class diagram to reduce clutter.

</box>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `VendorListPanel`, `StatusBarFooter`, etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts is defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103-F13-2/tp/blob/master/src/main/java/seedu/eventtory/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103-F13-2/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Vendor` and `Event` objects residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103-F13-2/tp/blob/master/src/main/java/seedu/eventtory/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete e/1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete e/1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of the diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `EventToryParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteEventCommand`) which is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to delete an event).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `EventToryParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `CreateCommandParser`) which uses the other classes shown above to parse the user command and create an `XYZCommand` object (e.g., `CreateVendorCommand`) which the `EventToryParser` returns as a `Command` object.
* All `XYZCommandParser` classes (e.g., `CreateCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g., during testing.

How commands work:
* `XYZEventCommand` and `XYZVendorCommand` classes (e.g., `CreateEventCommand`, `CreateVendorCommand`, ...) inherit from their respective abstract classes (e.g., `CreateCommand`).
* `ClearCommand`, `HelpCommand`, `ExitCommand`, `AssignCommand`, and `UnassignCommand` do not have corresponding `XYZEventCommand` and `XYZVendorCommand` classes.
* `ListCommand` is not an abstract class.

### Model component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103-F13-2/tp/blob/master/src/main/java/seedu/eventtory/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="640" />

<box type="info" seamless>

**Note** To reduce visual clutter, auxiliary classes within the `Vendor`, `Event`, and `Association` classes have been omitted from the diagram.

</box>


The `Model` component,

* stores EventTory data i.e., all `Vendor`, `Model`, and `Association` objects
* contains observables that can be 'observed' by outsiders e.g. the UI can be bound to a `UiState` state so that the UI automatically changes the screen when the data in `UiState` changes, such as:
    * the current displayed `UiState` as an `ObjectProperty<UiState>` object
    * the current selected (if any) `Event`, `Vendor` objects for viewing as separate `ObjectProperty<Event>`, `ObjectProperty<Vendor>` objects
    * the current selected `Event`, `Vendor` objects (e.g., results of a search query) as a separate _filtered_ list
    * the current trailing index offset for assigned vendors/events in view mode as an `ObservableIntegerValue` object
* stores a `UserPref` object that represents the user’s preferences. This is exposed as a `ReadOnlyUserPref` object.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103-F13-2/tp/blob/master/src/main/java/seedu/eventtory/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both EventTory data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `EventToryStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`, for examples, see the JsonAdapted classes eg. `JsonAdaptedVendor`, `JsonAdapterEvent`)

### Common classes

Classes used by multiple components are in the `seedu.eventtory.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Assign and Unassign Commands

The assign command adds the ability for the user to assign a vendor to an event and vice versa when inside the detailed view for either an `Event` or `Vendor`.

Given below is a usage scenario for the `assign` and `unassign` commands:

Step 1. Enter the details view for either an `Event` or a `Vendor`. For example, `view v/1` to view the first vendor.

Step 2. Assign the first `Event` from the list of assignable events to the currently viewed `Vendor`, using the command `assign 1`. The `Event` should now be shifted from the list of assignable events to the list of assigned events.

Step 3. To unassign the `Event` from the vendor, use the command `unassign 1`. The `Event` should now be shifted from the list of assigned events to the list of assignable events.

The `AssignCommand` and `UnassignCommand` classes were introduced to represent these commands. To support parsing the arguments to both commands, the `AssignCommandParser` and `UnassignCommandParser` classes were added.

For better understanding, refer to the sequence diagram below which illustrates the execution of the `assign` command. The first sequence diagram shows what happens when a user enters the assign command:

<puml src="diagrams/AssignParserSequenceDiagram.puml" width="800" />

<box type="info" seamless>

**Note:** The lifeline for `AssignCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of the diagram.

</box>

During the execution of the command itself, the behaviour of the command depends on the current view of the user, as shown in the following sequence diagram:

<puml src="diagrams/AssignSequenceDiagram.puml" width="600" />

#### Changes to Model

Implementing these commands required significant changes to the `Model` of the application, details of which are covered in this section:

* A unique identifier for both the `Event` and `Vendor` classes was added. This is represented by the new `UniqueId` class, which makes use of Java's built-in UUID class.
* The `Association` class represents pairs of assigned events and vendors, which is done using a pair of `UniqueId` classes, taken from the respective `Event` and `Vendor` instances.
* The list of associations in the current application's state are stored in the `UniqueAssociationList` class, which enforces a unique constraint on the `Association` instances stored within it.

The following methods to support the `assign` and `unassign` commands were implemented in `EventTory`:

* `EventTory#getAssociatedVendors(Event)` — Get the list of vendors associated to the provided event.
* `EventTory#getAssociatedEvents(Vendor)` — Get the list of events associated to the provided vendor.
* `EventTory#assignVendorToEvent(Vendor, Event)` — Create an association between the given `Vendor` and `Event`, and updates the list of associations.
* `EventTory#unassignVendorFromEvent(Vendor, Event)` — Remove the association between the given `Vendor` and `Event`, and updates the list of associations.
* `EventTory#getAssociationList()` — Get the full list of associations.
* `EventTory#isVendorAssignedToEvent(Vendor, Event)` — Returns a boolean indicating whether an association exists between a given `Vendor` and `Event`.
* `EventTory#setAssociations(List<Association>)` — Sets the state of the association list to the provided list.

We also expose the following methods in the `Model` interface, exposing the same operations as the `EventTory` class:

* `Model#getAssociatedVendors(Event)`
* `Model#getAssociatedEvents(Vendor)`
* `Model#assignVendorToEvent(Vendor, Event)`
* `Model#unassignVendorFromEvent(Vendor, Event)`
* `Model#getAssociationList()`
* `Model#isVendorAssignedToEvent(Vendor, Event)`

#### Changes to Storage

To support data persistence, `JsonAdaptedAssociation` was implemented to allow storing the list of associations alongside `Vendor` and `Event` information. In storage data, the list of associations are represented in the following form:

```
"associations": [
  {
    "vendorId": "a1e2c3d4-5f67-4890-8a1b-123456789abc",
    "eventId": "a1e2c3d4-5f67-4890-8a1b-123456789abd"
  },
  {
    "vendorId": "b2f3d4e5-6a78-491a-9b2c-23456789abcd",
    "eventId": "b2e3c4d5-6f78-49a1-9b2c-23456789abcd"
  }
]
```

In the case where the UUID strings are not valid UUID strings or do not correspond to real vendors or events, the whole JSON document representing the data will be treated as invalid.

#### Exception Handling

Adding this command into EventTory introduced new edge cases that had to be handled as well. Their details are covered in this section:

When deleting a `Vendor` or `Event` using the `delete` command, we have to make sure that it is not currently assigned to any other item. Otherwise, this would lead to the application storing associations between items that no longer exist. To handle this, the `AssociationDeleteException` was added to inform the user if they are trying to delete an item that is currently assigned to another item.

Since associations are meant to be unique, the `DuplicateAssociationException` was also added to inform the user, if they attempt to assign a pair of events and vendors that have already been assigned to each other.

To handle the event where the user attempts to unassign a vendor from an event when there is no existing association between the 2 items, the `AssociationNotFoundException` is used.

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

Individuals responsible for planning and managing events who need to communicate with multiple vendors concurrently.

Examples:
* corporate event managers
* wedding planners
* social event coordinators.

User Characteristics:
* has a need to manage a significant number of contacts and events
* prefers typing to mouse interactions
* can type fast
* is comfortable using Command-Line Interface (CLI) applications

**Value proposition**:

EventTory simplifies the process of organizing events, from small gatherings to large-scale corporate functions, and is optimized for users who prefer a CLI-like interface.

With the ability to track event details and contact information for various vendors, EventTory allows users to manage the status of multiple events with ease.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                     | I want to …​                                                      | So that I can…​                                                                                |
|----------|-----------------------------|-------------------------------------------------------------------|------------------------------------------------------------------------------------------------|
| `* * *`  | event planner               | create a new event with a name and date                             | easily track upcoming events                                                                   |
| `* * *`  | event planner               | create a new vendor with a name, phone number, and description      | keep track of vendors' data easily                                                             |
| `* * *`  | event planner               | delete an event                                                   | remove entries I no longer need and keep EventTory less cluttered                       |
| `* * *`  | event planner               | delete a vendor                                                   | remove entries I no longer need and keep EventTory less cluttered                       |
| `* * *`  | event planner               | assign a vendor to an event                                       | keep track of which vendors have been hired for an event                                       |
| `* * *`  | event planner               | unassign a vendor from an event                                   | remove vendors assigned from events when they are no longer need for an event                  |
| `* *`    | event planner               | find a vendor by name and tags                                    | choose a suitable vendor quickly for a new event                                               |
| `* *`    | event planner               | find an event by name and tags                                    | search for an event of interest                                                                |
| `* *`    | event planner               | view information related to a vendor                              | lookup vendor information                                                                      |
| `* *`    | event planner               | view information related to an event                              | lookup event information                                                                       |
| `* *`    | computer user with no mouse | navigate EventTory using only a keyboard                   | use the application without a mouse                                                                    |
| `* *`    | event planner               | modify event and vendor details                                   | correct any mistakes or changes made to an event or vendor                                     |
| `* *`    | forgetful event planner     | write additional notes for an event or vendor                     | keep track of miscellaneous information regarding each event or remarks regarding a vendor    |
| `*`      | event planner               | categorise vendors                                                | easily see what services a vendor provides                                                     |
| `*`      | event planner               | rate vendors in the system                                        | keep track of how good past experiences of working with the vendor were                        |
| `*`      | event planner               | send Whatsapp or Telegram messages from within the application            | easily contact vendors without 'leaving' EventTory                                      |
| `*`      | fast typer                  | chain multiple commands together before entering                  | accomplish multiple actions without worrying about hitting the 'Enter' key after every command |
| `*`      | event planner               | set progress statuses for vendors                                 | keep track of completed vendor deliverables                                                    |
| `*`      | event planner               | archive events                                                    | clear events that are completed                                                                |
| `*`      | event planner               | filter vendors by rating                                          | avoid working with less reputable vendors                                                      |
| `*`      | event planner               | indicate the types of vendors required for an event               | know what manpower or vendor I am missing for an event                                         |
| `*`      | fast typer                  | map commands to (shorter) aliases                                 | customise commands that I use often into more convenient phrases                               |
| `*`      | fast typer                  | autocomplete half-typed commands                                  | reduce the number of keystrokes required per command                                           |
| `*`      | event planner               | view all my events on a calendar                                  | easily monitor the events that I have across the week/month/year                               |
| `*`      | event planner               | tag a cost range for each vendor                                  | easily find appropriate vendors according to the budget of specific events                     |

### Use cases

For all use cases below, the **System** is the `EventTory` unless specified otherwise.

**Use case: UC01 - Create an event**

**MSS**

1. User enters command to create a new event.
2. System creates the event.
3. System displays a success message.

    Use case ends.

**Extensions**

* 1a. The command format entered by the user is invalid.
    * 1a1. System shows an error message and displays the correct command format.

      Use case ends.

* 1a. The event name is invalid.
    * 1a1. System shows an error message indicating the name format is incorrect.

      Use case ends.

* 1b. The date format is invalid.
    * 1b1. System shows an error message indicating the date format is incorrect.

      Use case ends.

* 1c. The event already exists in the system.
    * 1c1. System shows an error message indicating a duplicate event.

      Use case ends.

---

**Use case: UC02 - Assign a vendor to an event**

Preconditions: User is viewing an item.

**MSS**

1. User enters command to assign a vendor to an event.
2. System assigns the vendor to the event.
3. System displays a success message.

    Use case ends.

**Extensions**

* 1a. The command format entered by the user is invalid.

  * 1a1. System shows an error message and displays the correct command format.

      Use case ends.

* 2a. The selected item does not exist.

  * 2a1. System shows an error message.

      Use case ends.

* 2b. The vendor has already been assigned to the event.

  * 2b1. System shows an error message.

      Use case ends.

---

**Use case: UC03 - Unassign a vendor from an event**

Preconditions: User is viewing an item.

**MSS**

1. User enters command to unassign a vendor from an event.
2. System unassigns the vendor from the event.
3. System displays a success message.

   Use case ends.

**Extensions**

* 1a. The command format entered by the user is invalid.

    * 1a1. System shows an error message and displays the correct command format.

      Use case ends.

* 2a. The selected item is invalid.

    * 2a1. System shows an error message.

      Use case ends.

* 2b. The vendor is not assigned to the event.

    * 2b1. System shows an error message.

      Use case ends.

---

**Use case: UC04 - View an item**

**MSS**
1. User enters command to view an item.
2. System displays success message and switches page to the details of the item.

    Use case ends.

**Extensions**

* 1a. The command format entered by the user is invalid.

  * 1a1. System shows an error message and displays the correct command format.

      Use case ends.

* 1b. The item does not exist.

  * 1b1. System shows an error message.

      Use case ends.

---

**Use case: UC05 - Delete an item**

**MSS**
1. User enters command to delete an item.
2. System deletes the item.
3. System displays a success message.

    Use case ends.

**Extensions**

* 1a. The command format entered by the user is invalid.

  * 1a1. System shows an error message and displays the correct command format.

    Use case ends.

* 1b. The item does not exist.

  * 1b1. System shows an error message.

    Use case ends.

* 1c. The event has associated entities assigned to it.

  * 1c1. System shows an error message.

    Use case ends.

* 2a. The item is currently being viewed.

  * 2a1. System returns to the main page.

     Use case ends.

---

**Use case: UC05 - Edit an item**

**MSS**
1. User enters command to edit an item.
2. System updates the item with the new details provided.
3. System displays a success message.

    Use case ends.

**Extensions**
* 1a. The command format entered by the user is invalid.

  * 1a1. System shows an error message and displays the correct command format.

      Use case ends.
* 1b. The item does not exist.

  * 1b1. System shows an error message.

      Use case ends.

* 1c. The updated details conflict with an existing item.

  * 1c1. System shows an error message.

      Use case ends.

* 1d. The item is currently being viewed.

  * 1d1. System updates the displayed item with the new details.

      Use case ends.

---

**Use case: UC06 - List items**

**MSS**
1. User inputs the lists to display.
2. System displays chosen lists.

    Use case ends.

**Extensions**
* 1a. The command format entered by the user is invalid.

  * 1a1. System shows an error message and displays the correct command format.

      Use case ends.

**Use case: UC07 - Find items**

**Preconditions**: User is in list view.

**MSS**

1. User enters the command to find items with specific keywords.
2. System filters and displays items (e.g., vendors or events) that match the keywords.
3. System displays a success message.

    Use case ends.

**Extensions**

* 1a. The command format entered by the user is invalid.
    * 1a1. System shows an error message and displays the correct command format.

      Use case ends.

* 2a. No items match the search keywords.
    * 2a1. System displays a message indicating no items were found.

      Use case ends.

**Use case: UC08 - Create a vendor**

**MSS**

1. User enters command to create a new vendor.
2. System creates the vendor.
3. System displays a success message.

    Use case ends.

**Extensions**

* 1a. The command format entered by the user is invalid.
    * 1a1. System shows an error message and displays the correct command format.

      Use case ends.

* 1a. The vendor name is invalid.
    * 1a1. System shows an error message indicating the name format is incorrect.

      Use case ends.

* 1b. The phone number format is invalid.
    * 1b1. System shows an error message indicating the phone number format is incorrect.

      Use case ends.

* 1c. The vendor already exists in the system.
    * 1c1. System shows an error message indicating a duplicate vendor.

      Use case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 100 events and 100 vendors without a noticeable sluggishness in performance for typical usage.
3.  Should be able to assign up to 100 vendors to an event without any issues.
4.  A user with an above-average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
5.  Persistent data stored by the system should be in a human-readable format.

### Glossary

1. **Alias:** An alternate version of a command that can be used in place of the original. Is likely shorter than the original command.

2. **Autocomplete:** A feature that suggests possible completions of a partially typed command.

3. **Command:** A text input entered by the user to perform a specific function/action (e.g., create an event, assign a vendor).

4. **Command-Line Interface (CLI):** A text-based user interface where users interact with the system by typing commands instead of using graphical elements.

5. **Event:** A scheduled occasion or activity, such as a wedding, corporate function, or social gathering.

6. **Mainstream OS:** Commonly used operating systems including Windows, Linux, Unix, and macOS.

7. **Progress Status:** An indicator that tracks the completion level of a vendor's deliverables for an event.

8. **Tag:** A label or keyword assigned to vendors or events to facilitate filtering and searching.

9. **Vendor:** A service provider, such as a caterer, photographer, or decorator, who is employed for events.

10. **Item**: Refers to any entity or object that can be viewed or deleted within the system, such as an event or vendor.

11. **Associated Entities**: Refers to any related items or dependencies connected to the main item. For example, for an event, associated entities include assigned vendors; for a vendor, associated entities include assigned events.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the application manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch:

   1. Download the jar file and copy into an empty folder.

   2. Open a command window.

   3. Launch the jar file using the `java -jar` command. Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences:

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the application by using the `java -jar` command.<br>
      Expected: The most recent window size and location is retained.

### Create Command

1.  Create a Vendor:

    1. Type `create v/ n/Hong Lim Trading Pte. Ltd. p/67412943 d/Specialises in lighting effects. t/stage-crew` <br>
       Expected: A new vendor, "Hong Lim Trading Pte. Ltd.," appears in the vendor list with the provided details.

2.  Create an Event:
    1. Type `create e/ n/Jubilee Concert on/2025-01-24 t/annual` <br>
       Expected: A new event, "Jubilee Concert," appears in the event list with the provided date and tag.

### Edit Command

1.  Edit Vendor Details:

    1. Type `edit v/1 p/58623042` <br>
       Expected: The phone number of the first vendor in the list updates to `58623042`.

2.  Edit Event Name and Tags:

    1.  Type `edit e/2 n/Baby Shower t/` <br>
        Expected: The event name changes to "Baby Shower," and any existing tags are removed.

### Delete Command

1.  Delete a Vendor:

    1.  Type `delete v/1` <br>
        Expected: The first vendor in the list is removed from EventTory if it is not assigned to any event. Else, the deletion will fail.

2.  Delete an Event:

    1. Type `delete e/2` <br>
       Expected: The second event in the list is removed from EventTory if it is not assigned to any vendor. Else, the deletion will fail.

### List Command

1.  List Vendors Only:

    1. Type `list v/` <br>
       Expected: Only the list of vendors is displayed.

2.  List Events Only:

    1. Type `list e/` <br>
       Expected: Only the list of events is displayed.

3.  List All Vendors and Events:

    1. Type `list` <br>
       Expected: Both vendors and events lists are displayed together.

### View Command

1.  View Vendor Details:

    1. Type `view v/1` <br>
       Expected: Details of the first vendor, including assigned events, are displayed.

2.  View Event Details:

    1. Type `view e/1` <br>
       Expected: Details of the first event, including assigned vendors, are displayed.

### Assign Command

1.  Assign a Vendor to an Event:

    1.  Type `view e/1` to view an event.
    2.  Type `assign 1` to assign the first vendor to this event. <br>
        Expected: The selected vendor appears as assigned to the event.

### Unassign Command

1.  Unassign a Vendor from an Event:

    1.  While viewing an event with an assigned vendor, type `unassign 1`. <br>
        Expected: The vendor is unassigned and no longer appears under the event's assigned vendors list.

### Find Command

1.  Search for a Vendor by Keyword:

    1. Type `find v/ catering` <br>
       Expected: Vendors with names, descriptions, or tags matching "catering" are displayed.

2.  Search for an Event by Keyword:

    1. Type `find e/ wedding` <br>
       Expected: Events with names or tags matching "wedding" are displayed.

### Clear Command

1.  Clear All Entries:

    1. Type `clear` <br>
       Expected: All vendors and events are deleted, resulting in an empty list.

### Data Persistence

1.  Confirm Data Saving:

    1. Create a new vendor or event, close the application, reopen it, and check if the new entries are still present. <br>
       Expected: The added entries persist after restarting the application.

### Miscellaneous Commands

1. Getting Help:

   1. Type `help` in the command box and press Enter.
      Expected: A help window appears with a link to the user guide.

### Editing Data File (Advanced Users)

1.  Manually Edit JSON Data:

    1. Open the JSON data file located in `[JAR file location]/data/eventTory.json`, make changes, and restart EventTory. <br>
       Expected: Changes are reflected in the application, but incorrect formatting (e.g., invalid JSON) should prompt the application to reset to an empty state.

## **Appendix: Planned Enhancements**
### Name similarity warning
Notifies users when creating vendors or events with names similar to existing ones. If a user attempts to add a new vendor or event with a potentially matching name, the system alerts them to existing similar entries, prompting confirmation or suggesting alternative naming options.

### Description fields for events
Introduce a description field for each event, allowing users to add more detailed notes about the event's context, objectives, special requirements, or unique features.

### Association note
Allow users to add a short note to an association between a vendor and an event. This note can serve as a custom message or reminder specific to the event-vendor relationship, such as special instructions, deadlines, or contract details.

### Fine-grained find command
Enhance the find command to support searching within specific fields. Users can specify particular fields to search within, such as name, tag, or description. This feature will make finding items more precise and adaptable to different user needs.

### Bulk unassign
Enable users to unassign multiple vendors from an event or multiple events from a vendor in one command. This feature is especially helpful for clearing out old associations after an event concludes or for quickly reassigning vendors to different events.

### Enhanced Support for Foreign Phone Numbers
Improve the system’s ability to handle phone numbers in various international formats and with non-standard characters. This includes recognizing and storing diverse formats such as country codes, extensions, and special characters to accommodate a wider range of contact details. The current approach relies on users adding these specifiers without symbols or using tags to identify them, which can be confusing and cumbersome.

### Case Sensitivity for Commands
Make commands case-insensitive to allow users more input flexibility e.g. accidentally hitting the shift key midway through typing a command word.
* Current command words e.g. `create`, `list` require user inputs to be strictly lowercase.
* `lISt`, `List` or `LIST` would not be recognised as valid commands.
