---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

---

## **Acknowledgements**

* This project is based on the AddressBook-Level3 project created by the SE-EDU initiative.
* Libraries used: [JavaFX](https://openjfx.io/), [Jackson](https://github.com/FasterXML/jackson), [JUnit5](https://github.com/junit-team/junit5)

---

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

---

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [
_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create
and edit diagrams.

</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The **_Architecture Diagram_** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [
`Main`](https://github.com/AY2425S1-CS2103T-T13-3/tp/tree/master/src/main/java/seedu/address/Main.java) and [
`MainApp`](https://github.com/AY2425S1-CS2103T-T13-3/tp/tree/master/src/main/java/seedu/address/MainApp.java)) is in
charge of the app launch and shut down.

- At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
- At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

- [**`UI`**](#ui-component): The UI of the App.
- [**`Logic`**](#logic-component): The command executor.
- [**`Model`**](#model-component): Holds the data of the App in memory.
- [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The _Sequence Diagram_ below shows how the components interact with each other for the scenario where the user issues
the command `delete o1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

- defines its _API_ in an `interface` with the same name as the Component.
- implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API
  `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using
the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component
through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the
implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [
`Ui.java`](https://github.com/AY2425S1-CS2103T-T13-3/tp/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagramUpdated.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PetListPanel`,
`OwnerListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart`
class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that
are in the `src/main/resources/view` folder. For example, the layout of the [
`MainWindow`](https://github.com/AY2425S1-CS2103T-T13-3/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java)
is specified in [
`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-T13-3/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

- executes user commands using the `Logic` component.
- listens for changes to `Model` data so that the UI can be updated with the modified data.
- keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
- depends on some classes in the `Model` component, as it displays `Pet` or `Owner` object residing in the `Model`.

### Logic component

**API** : [
`Logic.java`](https://github.com/AY2425S1-CS2103T-T13-3/tp/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete o1")` API
call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `PawPatrolParser` object which in turn creates
   a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which
   is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take
   several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:

- When called upon to parse a user command, the `PawPatrolParser` class creates an `XYZCommandParser` (`XYZ` is a
  placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse
  the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `PawPatrolParser` returns back as a
  `Command` object.
- All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser`
  interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [
`Model.java`](https://github.com/AY2425S1-CS2103T-T13-3/tp/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagramUpdated.png" width="450" />

The `Model` component,

- stores the PawPatrol data i.e., all `Pet` and `Owner` objects (which are contained in a `UniquePetList` and
`UniqueOwnerList` object respectively).
- stores the currently 'selected' `Pet` or `Owner` objects (e.g., results of a search query) as a separate _filtered_ list which
  is exposed to outsiders as an unmodifiable `ObservableList<Pet/Owner>` that can be 'observed' e.g. the UI can be bound to
  this list so that the UI automatically updates when the data in the list change.
- stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a
  `ReadOnlyUserPref` objects.
- does not depend on any of the other three components (as the `Model` represents data entities of the domain, they
  should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `PawPatrol` class, which `Pet` references. This allows the `PawPatrol` class to only require one `Tag` object per unique tag, instead of each `Pet` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagramUpdated.png" width="450" />

</div>

### Storage component

**API** : [
`Storage.java`](https://github.com/AY2425S1-CS2103T-T13-3/tp/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagramUpdated.png" width="550" />

The `Storage` component,

- can save both PawPatrol data and user preference data in JSON format, and read them back into corresponding
  objects.
- inherits from both `PawPatrolStorage` and `UserPrefStorage`, which means it can be treated as either one (if only
  the functionality of only one is needed).
- depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects
  that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

---

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Link/Unlink Feature
#### Current Implementation
The link feature mechanism allows linking of `Owner` to `Pet`. Links can be created and entities to be linked can be determined by implementing the `Linkable` interface. In the current implementation, `LinkCommand` only allows linking of Owners to Pets using the indices as per other commands e.g. `link o1 t/p1 t/p2` would link owner at index 1 to pets at index 1 and 2.

A `LinkCommand` is only executed in its entirety when all `Link`s are non-duplicate and having valid indices. Should any single `Link` fail this criteria, no links will be created.

The sequence diagram below illustrates the interactions between the `Logic` and `Model` components, taking `execute("link o1 t/p1 t/p2")` as an example.

![LinkSequenceDiagram](images/LinkSequenceDiagram.png)

#### Unlinking
The `UnlinkCommand` works similarly, finding the unique ID from the `Linkable` interface and removing the associated `Link` from `Model` instead.

#### Handling of Deletes
When an `Owner` or `Pet` is deleted, all associated `Link`s are deleted from the application.

#### Future Improvements
The `LinkCommand` can be generalized for other entity-entity links. For example, linking health records to pets.


---

## **Documentation, logging, testing, configuration, dev-ops**

- [Documentation guide](Documentation.md)
- [Testing guide](Testing.md)
- [Logging guide](Logging.md)
- [Configuration guide](Configuration.md)
- [DevOps guide](DevOps.md)

---

## **Appendix: Requirements**

### Product scope

**Target user profile**: A veterinarian running a private veterinary clinic

- **Needs**:

  - Managing a large number of clients and pets efficiently.
  - Keeping track of client information, including contact details and pets' records.
  - The ability to quickly search, update, and manage records.

- **Skills & Preferences**:

  - Prefers desktop applications to web or mobile apps.
  - Comfortable with fast typing and more inclined toward keyboard-centric workflows.
  - Prefers typing commands over using a mouse for navigation and interaction, reducing time spent on manual GUI tasks.
  - Familiar with using CLI apps, or open to adopting them given their efficiency and speed.

- **Pain Points**:
  - Frustration with slow, clunky, or over-complicated GUI-based systems.
  - Loss of time due to extensive clicks and navigation through multiple screens.
  - Difficulty managing and organizing client and pet records using existing software.

**Value proposition**: Manage clients and their pets faster than a typical mouse/GUI driven app

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​      | I want to …​                             | So that I can…​                                        |
|----------|--------------|------------------------------------------|--------------------------------------------------------|
| `* * *`  | veterinarian | create and store profiles for pet owners | quickly access their information                       |
| `* * *`  | veterinarian | create and store profiles for pets       | quickly access their information                       |
| `* * *`  | veterinarian | search for pet owners by key details     | quickly find the information I need                    |
| `* * *`  | veterinarian | search for pets by key details           | quickly find the information I need                    |
| `* * *`  | veterinarian | list all pet owners and pets             | quickly access and review my client base               |
| `* * *`  | veterinarian | delete profiles for pet owners and pets  | keep my records up to date                             |
| `* * *`  | veterinarian | save PawPatrol                           | save my data                                           |
| `* * *`  | veterinarian | exit PawPatrol                           | close my session safely                                |
| `* *`    | veterinarian | link each pet owner to their pet(s)      | easily manage owners and their associated pet(s)       |
| `* *`    | veterinarian | unlink each pet owner and their pet(s)   | easily manage owners and their associated pet(s)       |
| `* *`    | veterinarian | edit profiles for pet owners             | update profile information and ensure accurate records |
| `* *`    | veterinarian | edit profiles for pets                   | update profile information and ensure accurate records |
| `* *`    | veterinarian | sort pet owners alphabetically by name   | easily manage all pet owners in the database           |
| `* *`    | veterinarian | sort pets alphabetically by name         | easily manage all pets in the database                 |
| `*`      | veterinarian | clear all pet owners' and pets' profiles | reset the system by removing all records at once       |
| `*`      | veterinarian | view all the commands available to me    | utilize PawPatrols features effectively                |

### Use cases

(For all use cases below, the **System** is the `PawPatrol` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Add a pet owner**

**MSS**

1.  User requests to add a new pet owner by providing the owner's IC number, name, phone, address, and email.
2.  PawPatrol validates input.
3.  PawPatrol successfully adds the new pet owner to the list.

    Use case ends.

**Extensions**

- 2a. Invalid owner name format.

  - 2a1. PawPatrol shows an error message:

    Use case resumes at step 1.

- 2b. Invalid phone number format.

  - 2b1. PawPatrol shows an error message:

    Use case resumes at step 1.

- 2c. Duplicate IC Number.

  - 2c1. PawPatrol shows an error message:

    Use case resumes at step 1.

- 2d Invalid email format.

  - 2d1. PawPatrol shows an error message:

    Use case resumes at step 1.

- 2e Invalid IC number format.

  - 2e1. PawPatrol shows an error message:

    Use case resumes at step 1.

**Use case: Delete a pet**

**MSS**

1.  User keys in "delete pPET_INDEX"
2. PawPatrol deletes the pet at the specified index(one-indexed)

    Use case ends.

**Extensions**

- 2a. The list is empty.

  Use case ends.

- 3a. The given index is invalid.

  - 3a1. PawPatrol shows an error message.

    Use case resumes at step 1.

**Use Case: Search for pets**

**MSS**

1. User requests to search for a pet with the relevant search parameter.
2. PawPatrol validates the input.
3. PawPatrol performs the search and retrieves matching records.
4. PawPatrol displays the list of contacts matching the `dataType` and `searchValue`.

   Use case ends.

**Extensions**

- 2a. Invalid data type.

  - 2a1. PawPatrol shows an error message:

    Use case resumes at step 1.

- 2b. No matching pet contacts found.

  - 2b1. PawPatrol shows an error message:

    Use case resumes at step 1.

- 2c. Empty search value.

  - 2c1. PawPatrol shows an error message:

    Use case resumes at step 1.

- 2d. Invalid characters in search value.

  - 2d1. PawPatrol shows an error message:

    Use case resumes at step 1.

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should be able to hold up to 1000 owner and pet without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be
   able to accomplish most of the tasks faster using commands than using the mouse.
4. The search result should be able to search for both the owners and the pets and return back a list.
5. The source code should be open source.
6. The source code should adhere to coding standards and include documentation to facilitate future modifications.
7. The User Interface should be intuitive enough for users who are not familiar with technology.

### Glossary

- **API**: An API (Application Programming Interface) defines a set of rules and protocols that allow different software applications to communicate and interact with each other.
- **Architecture Diagram**: An architecture diagram visually represents the structure, components, and relationships within a system, providing a clear overview of its design and interactions.
- **CLI**: CLI (Command Line Interface) is a text-based interface that allows users to interact with a computer system by typing commands, rather than using a graphical interface.
- **Client**: A pet owner who seeks services from the veterinary clinic for their pet(s).
- **JAR**: JAR (Java Archive) is a file format used to package multiple Java class files, metadata, and resources into a single archive, often for distribution or deployment.
- **JSON**: JSON (JavaScript Object Notation) is a lightweight, text-based format for representing structured data, commonly used for data exchange between a server and a client.
- **Mainstream OS**: Windows, Linux, Unix, MacOS.
- **Pet Owners**: An individual who has registered their pet with the veterinary clinic for treatment or checkups.
- **Private contact detail**: A contact detail that is not meant to be shared with others.
- **Profile**: A detailed record containing information about a pet or pet owner, including the owner's name and contact details, as well as the pet's name, species, breed, age, and sex.
- **Sequence Diagram**: A sequence diagram shows the sequence of interactions between objects or components over time, highlighting the order of messages exchanged in a process or system.
- **UI**: A UI diagram illustrates the layout, elements, and flow of a user interface, showcasing how users interact with the system.
- **Vaccination**: The administration of a vaccine to a pet to stimulate an immune response against specific diseases.
- **Veterinarian**: A licensed medical professional who provides care and treatment to animals.
- **Workflow**: The sequence of processes involved in providing veterinary care, including appointment scheduling, examination, treatment, and follow-up.
---

# **Appendix:**
## **Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be
      optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
      Expected: The most recent window size and location is retained.

### Adding an owner

1. Adding a new owner

   1. Test case: `owner i/S0000001I n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25`
   2. Expected: A new owner is added to the list. The owner’s details are shown in the list.
   3. Test case: `owner i/S0000001I n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25`
   4. Expected: No new owner is added. Error details shown in the status message.
   5. Other incorrect add commands to try: `owner`, `owner x`, `...`<br>
      Expected: Similar to previous.

### Adding a pet

1. Adding a new pet

   1. Test case: `pet n/Fluffy s/Dog b/Golden Retriever a/7 x/F`
   2. Expected: A new pet is added to the list. The pet’s details are shown in the list.
   3. Test case: `pet n/Fluffy s/Dog b/Golden Retriever a/7 x/F`
   4. Expected: No new pet is added. Error details shown in the status message. Status bar remains the same.
   5. Other incorrect add commands to try: `pet`, `pet x`, `...`<br>
      Expected: Similar to previous.

### Linking

1. Link owners and pets
   1. Pre-requisite: Add owners and pets using the `owner` and `pet` commands.
   2. Test case: `link o1 t/p1 t/p2`
   3. Expected: Owners and pets are linked. The link details are shown in the status message.
   4. Test case: `link o1 t/p1 t/p2`
   5. Expected: No new links are added. Error details shown in the status message. Status bar remains the same.
   6. Other incorrect link commands to try: `link`, `link x`, `...`<br>
      Expected: Similar to previous.
   7. Test case: `link o2 t/p1`
   8. Expected: Error details shown in the status message due to missing owner.

### Unlinking

1. Unlinking owners and pets
   1. Pre-requisite: Link owners and pets using the `link` command.
   2. Test case: `unlink o1 t/p1`
   3. Expected: Owners and pets are unlinked. The unlink details are shown in the status message.
   4. Test case: `unlink o1 t/p1`
   5. Expected: No links are removed. Error details shown in the status message. Status bar remains the same.
   6. Other incorrect unlink commands to try: `unlink`, `unlink x`, `...`<br>
      Expected: Similar to previous.

### Listing

1. Listing data

   1. Test case: `list`<br>
      Expected: All owners and pets are shown in the list.
   2. Test case: `list owners`<br>
      Expected: All owners are shown in the list. Pets are not shown.
   3. Test case: `list pets`<br>
      Expected: All pets are shown in the list. Owners are not shown.
   4. Test case: `list x`<br>
      Expected: Error details shown in the status message. Status bar remains the same.

### Editing

1. Editing owner details

   1. Pre-requisite: Add owners using the `owner` command.
   2. Test case: `edit o1 n/John Doe p/98765432
   3. Expected: Owner details are updated. Updated owner details are shown in the list.
   4. Test case: `edit o0 n/John Doe p/98765432
   5. Expected: No owner is updated. Error details shown in the status message.
   6. Other incorrect edit commands to try: `edit`, `edit x`, `...`<br>
      Expected: Similar to previous.

2. Editing pet details

   1. Pre-requisite: Add pets using the `pet` command.
   2. Test case: `edit p1 n/Fluffy s/Dog b/Golden Retriever a/7 x/F`
   3. Expected: Pet details are updated. Updated pet details are shown in the list.
   4. Test case: `edit p0 n/Fluffy s/Dog b/Golden Retriever a/7 x/F`
   5. Expected: No pet is updated. Error details shown in the status message.
   6. Other incorrect edit commands to try: `edit`, `edit x`, `...`<br>
      Expected: Similar to previous.

### Finding

1. Finding owners
   1. Test case: `find owner John`<br>
      Expected: All persons with the name John are shown in the list.

2. Finding pets
   1. Test case: `find pet Fluffy`<br>
      Expected: All pets with the name Fluffy are shown in the list.

### Deleting an entity

1. Deleting an owner while all owners are being shown

   1. Prerequisites: List all owners using the `list owner` command. Multiple owners in the list.

   1. Test case: `delete o1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message.

   1. Test case: `delete o0`<br>
      Expected: No owner is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete ox`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

2. Deleting a pet while all pets are being shown

   1. Prerequisites: List all pets using the `list pet` command. Multiple pets in the list.

   1. Test case: `delete p1`<br>
      Expected: First pet is deleted from the list. Details of the deleted pet shown in the status message.

   1. Test case: `delete p0`<br>
      Expected: No pet is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete px`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Sorting

1. Sorting owners

   1. Test case: `sort owner`<br>
      Expected: All owners are shown in the list, sorted by name.

2. Sorting pets

    1. Test case: `sort pet`<br>
        Expected: All pets are shown in the list, sorted by name.

### Clearing data

1. Clearing all entity

   1. Test case: `clear`<br>
      Expected: All entity are removed from the list.

### Exiting the app

1. Exiting the app

   1. Test case: `exit`<br>
      Expected: The app closes.

## **Planned Enhancements**

Team size: 5
1. **Enhance search functionality**: Users can search for pets and owners based on other attributes. For owners, attributes such as ID, contact number, email and address. For pets, attributes such as species, breed, age, sex and tags.
2. **Improve error messages**: Make them standardized, consistent and clearer.
3. **Improve text validation and formatting**: Make data more consistent such as by removing extra spaces in names. (e.g. the input `john    doe` will be saved as `john doe`)
4. **UI updates**: To fix occasional irregular white lines that appear in output box, wrap text that is currently truncated in the UI and enlarge the output box to remove the gap that appears when using the `list owners` or `list pets` command.
5. **Improve the intuitiveness of command formats**: Commands will be made more intuitive for users. For example, the `link` command would be simpler for users without the use of the `t/` prefix.
6. **Automatically make back-ups of data file**: This will prevent data from being accidentally lost when using the `clear` command. A copy of the JSON file will be made frequently and saved in the user's computer.
7. **Allow multiple pets with similar characteristics**: (exactly the same name, species, breed, age and sex) as long as they are linked to different owners. The user will be prompted to link an existing pet to its owner before a duplicate pet can be added. 
8. **Truncate zeroes at the start of a pet's age**: for single-digit pet ages (01, 02, 03 years) could lead to duplicate entries if another pet exits in the database with all characteristics the same and their age keyed in without a zero at the start (1, 2 or 3 years). Future versions will rectify this behaviour by ignoring the zero if the pet's age is between 0 and 9 years.
9. **Improve link/unlink commands**: When an invalid `link` or `unlink` command is entered, it will specify the exact reason why that command failed. (e.g. a multiple `link` where 1 or more pairs is already linked or doing an `unlink` where 1 or more pairs is missing from the database)
