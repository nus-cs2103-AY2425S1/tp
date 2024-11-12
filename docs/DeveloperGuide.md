---
  layout: default.md
  title: "Developer guide"
  pageNav: 3
---

# WedLinker Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

WedLinker is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org). <br>
The following java classes in the directory `tp\src\main\java\seedu\address\model\task` were taken from one of our team member's [CS2103T Individual Project (IP)](https://github.com/DanzaSeah/tp/tree/master/src/main/java/seedu/address/model/task). These classes were modified to align with the requirements of the project
- Task, Todo, Deadline, Event <br>

ChatGPT by OpenAI was used by DanzaSeah to:
- Rephrase the JavaDocs of Task and Tag related classes to be more concise and clear,
- Seek suggestions for organising and improving existing test cases,
- Come up with better or more appropriate class and method names.

External Java libraries were also used in the project for various functionalities:
- JavaFX: for the GUI
- JUnit5: for Unit testing
- Jackson: for processing JSON files
--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture
The ***Architecture Diagram*** given below explains the high-level design of the App, giving a quick overview of main components and how they interact with each other.
<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

**Main components of the architecture**
The bulk of the app's work is done by the `UI`, `Logic`, `Model`, and `Storage` components.

* **`Main`**:
    * Consists of classes 
<a href="https://github.com/AY2425S1-CS2103T-F15-4/tp/tree/master/src/main/java/seedu/address/Main.java" style="text-decoration: underline;">`Main`</a>
<a href="https://github.com/AY2425S1-CS2103T-F15-4/tp/tree/master/src/main/java/seedu/address/MainApp.java" style="text-decoration: underline;">`MainApp`</a>
    * Responsible for app launch and shutdown.
    * At launch, it initializes the other components in the correct sequence and connects them.
    * At shutdown, it terminates the components and invokes cleanup methods where necessary.


* <a href="#ui-component" style="text-decoration: underline;"><strong><code>UI</code></strong></a>:
    * The interface of the app (WedLinker) that the user primarily interacts with.
    * Displays relevant information (e.g., wedding details) from `Model`.
    * Receives commands from user input.


* <a href="#logic-component" style="text-decoration: underline;"><strong><code>Logic</code></strong></a>:
    * The command executor.


* <a href="#model-component" style="text-decoration: underline;"><strong><code>Model</code></strong></a>:
    * Holds the data of the app in memory, which can be altered by commands.
    * Some data is displayed to the user via the UI.


* <a href="#storage-component" style="text-decoration: underline;"><strong><code>Storage</code></strong></a>:
    * Reads data from, and writes data to, the locally stored `data/addressbook.json` file.


* <a href="#common-classes" style="text-decoration: underline;"><strong><code>Commons</code></strong></a>:
    * Represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as `{Component}`.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI Component
(<a href="https://github.com/AY2425S1-CS2103T-F15-4/tp/tree/master/src/main/java/seedu/address/ui/Ui.java" style="text-decoration: underline;"><strong><code>Ui.java</strong></code></a>)

The `UI` component handles interactions between the user and the app's graphical interface, leveraging JavaFX to define and display various UI elements.

1. **Structure**:
    * The `UI` consists of a `MainWindow` composed of several parts like `CommandBox`, `StatusBarFooter`, `ResultDisplay`, and various `*ListPanel`s.
    * Each UI part, including `MainWindow`, inherits from the abstract class
      (<a href="https://github.com/AY2425S1-CS2103T-F15-4/tp/tree/master/src/main/java/seedu/address/ui/UiPart.java#L15" style="text-decoration: underline;"><strong><code>UiPart</strong></code></a>),
   capturing commonalities among visible GUI components.


2. **Class Diagram**:
    * Diagram showing the `UI` structure:

      <puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

    
3. **JavaFX and Layout Files**:
    * The layout of each UI part is defined using `.fxml` files in the
      <a href="https://github.com/AY2425S1-CS2103T-F15-4/tp/tree/master/src/main/resources/view" style="text-decoration: underline;"><strong><code>src/main/resources/view</strong></code></a>,
    * For example, the structure of
      <a href="https://github.com/AY2425S1-CS2103T-F15-4/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java" style="text-decoration: underline;"><strong><code>MainWindow</strong></code></a>,
   is specified in
      <a href="https://github.com/AY2425S1-CS2103T-F15-4/tp/tree/master/src/main/resources/view/MainWindow.fxml" style="text-decoration: underline;"><strong><code>MainWindow.fxml</strong></code></a>,


4. **Responsibilities**:
    * Executes user commands by interfacing with the `Logic` component.
    * Listens for updates to `Model` data to refresh the UI with modified information.
    * Maintains a reference to `Logic`, relying on it for command execution.
    * Accesses specific `Model` classes to display `Person` objects managed within `Model`.

### Logic Component
(<a href="https://github.com/AY2425S1-CS2103T-F15-4/tp/tree/master/src/main/java/seedu/address/logic/Logic.java" style="text-decoration: underline;"><strong><code>Logic.java</strong></code></a>)


The `Logic` component processes user inputs passed through the UI, utilizing a series of parsers to generate `Command` instances, which are then executed. Here’s an overview:

1. **User Input Processing**:
    * User input is received from the UI and passed to `Logic`.
    * The input is parsed by `AddressBookParser`, which delegates to the appropriate parser (e.g., `DeleteCommandParser`), creating the relevant `Command` instance.
    * `LogicManager` executes the `Command`, interacting with the `Model` to make necessary updates (e.g., deleting a person).
    * Execution results are encapsulated in a `CommandResult` and returned to `Logic`, which relays the outcome back to the UI.


2. **Class Diagram**:
    * Partial diagram of the `Logic` component:

      <puml src="diagrams/LogicClassDiagram.puml" width="550"/>

    
3. **Sequence Diagram**:
    * Example interaction in the `Logic` component for `execute("delete 1")` (from user inputting delete 1 on UI):

      <puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

      <box type="info" seamless>
      
      **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X), but due to a PlantUML limitation, it continues to the end of the diagram.
      </box>

    
4. **Parser Classes**:
    * Other classes in `Logic` handle command parsing:

      <puml src="diagrams/ParserClasses.puml" width="600"/>

    * Parsing flow:
        * `AddressBookParser` identifies the specific command parser needed (e.g., `AddCommandParser`, `DeleteCommandParser`).
        * Each `XYZCommandParser` (e.g., `AddCommandParser`) implements the `Parser` interface, allowing for consistent handling and testing.
        * The parser processes the user command, creating the relevant `Command` object (e.g., `AddCommand`), which `AddressBookParser` returns to `Logic`.

### Model Component
(<a href="https://github.com/AY2425S1-CS2103T-F15-4/tp/tree/master/src/main/java/seedu/address/model/Model.java" style="text-decoration: underline;"><strong><code>Model.java</strong></code></a>)


The `Model` component handles the data and state management for the app, storing all entities and providing an interface for accessing and updating them.

1. **Structure**:
    * The `Model` stores various types of data:
        * `Person` objects within a `UniquePersonList`.
        * `Wedding` objects within a `UniqueWeddingList`.
        * `Tag` objects within a `UniqueTagList`.
        * `Task` objects within a `UniqueTaskList`.


2. **Class Diagram**:
    * Diagram of the `Model` structure:

      <puml src="diagrams/ModelClassDiagram.puml" width="700" />

    
3. **Responsibilities**:
    * **Data Storage**:
        * Maintains address book data, including lists of `Person`, `Wedding`, `Tag` and `Task` objects.
    * **Filtered Views**:
        * Provides filtered lists of `Person` objects (e.g., search results), exposing these as unmodifiable `ObservableList`s. This allows the UI to automatically update in response to changes.
    * **User Preferences**:
        * Stores user preferences in a `UserPref` object, which is accessible externally as `ReadOnlyUserPref`.
    * **Independence**:
        * The `Model` does not rely on other components, as it represents the app’s core data entities, which should be logically self-contained.


### Storage component
(<a href="https://github.com/AY2425S1-CS2103T-F15-4/tp/tree/master/src/main/java/seedu/address/storage/Storage.java" style="text-decoration: underline;"><strong><code>Storage.java</strong></code></a>)

1. **Functionality**:
    * Supports saving and loading both address book data and user preferences as JSON files.
    * Reads JSON data into corresponding in-memory objects for the app's use.


2. **Inheritance**:
    * Inherits from `AddressBookStorage` and `UserPrefStorage`, allowing it to be used interchangeably with either interface, depending on the functionality needed.


3. **Dependencies**:
    * Depends on classes within the `Model` component, as `Storage` is tasked with saving and retrieving `Model`-related objects.


4. **Class Diagram**:
    * Diagram illustrating `Storage` structure:

      <puml src="diagrams/StorageClassDiagram.puml" width="900" />
    
### Common classes

Classes used by multiple components are in the 
<a href="https://github.com/AY2425S1-CS2103T-F15-4/tp/tree/master/src/main/java/seedu/address/commons" style="text-decoration: underline;"><strong><code>seedu.address.commons</strong></code></a>
package.

Notable classes include `StringUtil` and `ToStringBuilder` which help to perform operations like checking whether a string
includes non-zero unsigned integer, or whether there is a partial match for a word. These can be found in
<a href="https://github.com/AY2425S1-CS2103T-F15-4/tp/tree/master/src/main/java/seedu/address/commons/util" style="text-decoration: underline;"><strong><code>seedu.address.commons.util</strong></code></a>.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Force Feature

#### Implementation

The Force feature is a quality of life addition for WedLinker. It enables users to bypass certain checks in the `Logic` in a controlled manner to make usage easier.
The force feature is applicable for the following commands:
* `Tag`: This creates a `Tag` if it does not exist in WedLinker before tagging the `Person`.
* `Delete Tag`: This unassigns the target `Tag` from all contacts before deleting it.
* `Assign Wedding`: This creates a `Wedding` if it does not exist in WedLinker before assigning the `Person` to the `Wedding`.
* `Delete Wedding`: This unassigns all `Person` from the `Wedding` before deleting it.

The force functionality can be used with the above functions by including f/ at the end of the command. Additional inputs following the f/ are extraneous and would be discarded.

Example usage: delete-tag t/Photographer f/

Step 1. The user executes `delete-tag t/Photographer f/`.

Step 2. The parser will search through the user input to find f/. f/ is implemented as a `Prefix`. User inputs after f/ will be ignored.

Step 3. The parser will construct the `DeleteTagCommand` differently based on the presence of the force flag.

Step 4. During `DeleteTagCommand#execute()`, the force flag is checked. If present, it unassigns all `Persons` with the `Tag` Photographer before deleting Photographer.

### Tag Feature

The Tag Feature allows users to store additional details of a Person in WedLinker.
Tags support the following functions:

* `Create Tag` — Creates a `Tag` in WedLinker to be used as additional information of any `Person`.
* `Tag` — Assigns `Tag` to a `Person.
* `Untag` — Unassigns a `Tag` from a `Person`.
* `Delete Tag` — Deletes the `Tag` from WedLinker.

#### Implementation

Given below is an example usage scenario and how Tags are used in WedLinker.

Step 1. The user launches the application, `Tags` are loaded into the `Model`.

Step 2. The user executes `create-tag t/Photographer`. WedLinker will check if `Photographer` already exist. 
WedLinker will create a Tag based on the name provided if the `Tag` does not exist. In this case: `Photographer`.

Step 3. The user executes `tag 1 t/Photographer` to tag the first `Person` with `Photographer`.

Step 4. The user executes `list-tags` to view all `Tags` in WedLinker. This will switch the right panel to a Tag view.

Step 5. The user executes `find t/Photographer` to find all `Person` that has the tag of `Photographer`.

Step 6. The user executes `untag 1 t/Photographer`. WedLinker removes the `Photographer` tag from the first person.

Step 7. The user executes `delete-tag t/Photographer`. WedLinker deletes the `Photographer` tag.

<box type="info" seamless>
Tag supports the force functionality for easier usage.

Force is supported for the following functions:
- `Tag` (Creates the Tag if it does not already exist in WedLinker.)
- `Delete Tag` (Unassigns all Person from the Tag before it is deleted.)
  </box>

<box type="warning" seamless>
Known bugs:
- Tag names with very long names overflow in the People view. No issues to functionality. 
  Longer tag names can be viewed as truncated in the Tag view. Tag view can be accessed using the list-tags command.
  Alternatively, use `Task` to write longer information.
</box>

### Wedding Feature

#### Implementation

The Wedding Feature allows users to store details of a Wedding in WedLinker. Wedding contains the Contacts involved to facilitate easy planning and consolidation for wedding planners.
Wedding would support the following functions:

* `Create Wedding` — Creates a Wedding in WedLinker to allow compilation of information.
* `Assign Wedding` — Assigns `Person` to a Wedding. This can be the `Partners` or `Guest`.
* `Edit Wedding` — Edits the information for a Wedding. Editable information includes the `Wedding Name`, `Address` and `Date`.
* `Unassign Wedding` — Unassigns a `Person` from a Wedding.
* `Delete Wedding` — Deletes the Wedding from WedLinker.

Given below is an example usage scenario and how Weddings are used in WedLinker.

Step 1. The user launches the application, `Weddings` are loaded into the `Model`.

Step 2. The user executes `create-wedding w/Test Wedding 1`. WedLinker will create a Wedding based on the name provided. In this case: `Test Wedding 1`.

Step 3. The user executes `assign-wedding 1 w/Test Wedding 1` to assign a `Person` to `Test Wedding 1`. WedLinker includes the `Person` in the `Guest List`.

Step 4. If the user executes `assign-wedding 1 w/Test Wedding 1 p1/`, WedLinker will assign the `Person` as a partner instead.

Step 5. To view the Weddings in WedLinker, use `list-weddings`. This will switch the right panel to a Wedding view.

Step 6. The user executes `edit-wedding 1 w/Test Wedding 2 a/Fullerton Hotel`. `Test Wedding 1` will have its name edited to `Test Wedding 2` and its address be changed to `Fullerton Hotel`.
        If the right panel is still in the Wedding view, the changes will be reflected.

Step 7. The user executes `unassign-wedding 1 w/Test Wedding 2`. WedLinker would unassign the `Person` from `Test Wedding 2`.

Step 8. The user executes `delete-wedding w/Test Wedding 2`. WedLinker would delete `Test Wedding 2`.

<box type="info" seamless>
Wedding supports the force functionality for easier usage.

Force is supported for the following functions:
- `Assign Wedding` (Creates the Wedding if it does not already exist in WedLinker.)
- `Delete Wedding` (Unassigns all Person from the Wedding before it is deleted.)
</box>

<box type="warning" seamless>
Known bugs:
- Wedding names with very long names overflow in the People view. No issues to functionality. 
  Longer Wedding names can be viewed as truncated in the Wedding view. Wedding view can be accessed using the list-weddings command.
</box>

### Vendors

#### Implementation

The `Vendor` Feature allows users to track which `Person` are `Vendors` and assign `Tasks` to them. Only `Vendors` can be assigned `Tasks`.
Vendor would support the following functions:

* `Assign Vendor` — Assigns an existing `Person` in WedLinker to become a `Vendor`. 
* `Unassign Vendor` — Unassigns a `Vendor` to become a non-vendor `Person` contact. 

Given below is an example usage scenario and how Vendors are used in WedLinker.

Step 1. The user launches the application, `Persons` and `Vendors` are loaded into the `Model`.

Step 2. The user executes `assign-vendor 1`. WedLinker will assign the `Person` at index 1 of the current displayed person list to become a `Vendor`. The user can now assign `Tasks` to this contact.

Step 3. The user executes `unassign-vendor 1`. The contact is no longer a `Vendor` and exists as a non-`Vendor` `Person` in WedLinker.

<box type="info" seamless>
Vendor supports the force functionality for easier usage.

Force is supported for the following functions:
- `Unassign Vendor` (Unassigns all existing `Task` from the Vendor before downgrading to a `Person`.)

</box>

### Task

The `Task` Feature allows users to track `Tasks` when planning for a Wedding. `Tasks` can only be assigned to `Vendors`.
There are different types of `Task` to support different requirements for the users.

`Task` type: `Todo`
`Todo` is the simplest kind of `Task` which has a description to provide information about the `Task`.

`Task` type: `Deadline`
`Deadline` is a type of `Task` which has a description to provide information and supports a due `Date`.

`Task` type: `Event`
`Event` is a type of `Task` which has a description to provide information and supports a start and end `Date`.

`Task` supports the following functions:
* `Create Task` —  Creates a new `Task` in WedLinker.
* `Assign Task` —  Assigns an existing `Task` to a `Vendor`. Tasks can only be assigned to `Vendors`.
* `Unassign Task` —  Unassigns a `Task` from a `Vendor`. 
* `Delete Task` —  Deletes a `Task` from WedLinker. 

Given below is an example usage scenario and how Tasks are used in WedLinker.

Step 1. The user launches the application, `Tasks` are loaded into the `Model`.

Step 2. The user executes `create-task Order Wedding Cake`. WedLinker will create a new `Task` in the `Task List`.

Step 3. The user executes `assign-task 1 1` to assign the `Task` at index 1 of the `Task List` to the `Vendor` at index 1 on the `Person List`. 

Step 4. The user executes `unassign-task 1 1` to assign the `Task` at index 1 of the `Task List` from the `Vendor` at index 1 on the `Person List`.

Step 5. The user executes `unassign-task 1 1` to assign the `Task` at index 1 of the `Task List` from the `Vendor` at index 1 on the `Person List`.

Step 6. The user executes `delete-task 1 ` to delete the `Task` at index 1 of the `Task List` from the WedLinker.

### Switch Views
WedLinker features a split-view interface designed to display different lists side by side. The left side of the screen consistently shows the `Person List`, while the right side dynamically displays one of the following lists based on user input: `Wedding List`, `Task List`, or `Tag List`.
This functionality is managed through an enumeration that defines the available views: `WEDDING`, `TASK`, and `TAG`. The system switches between these views based on the user's actions, ensuring a flexible and intuitive user experience.


`View` supports the following functions: 
`List Weddings` —  displays a list of all weddings.
`List Tasks` —  displays a list of all tasks.
`List Tags` —  displays a list of all tags.

Given below is an example usage scenario and how Tasks are used in WedLinker.

Step 1. The user launches the application, `Weddings`, `Tasks` and `Tags` are loaded into the `Model`.

Step 2. The user executes `list-weddings`. WedLinker display a list of all `Weddings` on the right half of the screen.

Step 3. The user executes `list-tasks`. WedLinker display a list of all `Tasks` on the right half of the screen.

Step 4. The user executes `list-tags`. WedLinker display a list of all `Tags` on the right half of the screen.


#### Design considerations:

**Aspect: Force Functionality:**

* **Alternative 1:** Users have to strictly follow the restrictions in running commands in WedLinker.
    * Pros: This is easier to implement and would be more stable
    * Cons: This might be restrictive and could slow down the usage.

* **Alternative 2:** Users have the ability to bypass some restrictions in a controlled manner.
    * Pros: This would improve the ease of use.
    * Cons: Additional code is required to ensure controlled execution.


_{more aspects and alternatives to be added}_

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

**Target user profile**: Professional Wedding Planners

* needs to manage a significant number of contacts
* needs to manage multiple weddings happening at differing times
* needs to manage contacts across multiple weddings
* prefers desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Simplifies managing wedding-related contacts through text-based commands by providing a centralized wedding planning management system designed to efficiently 
handle the complexities of coordinating with multiple vendors, venues, and clients, while streamlining communication and event planning tasks.


### User stories

Priorities:
High (must have) - `* * *`, Medium (nice to have) -  `* *`, Low (unlikely to have) - `*`

Those without any stars are user stories that were considered but will not be implemented at this time.

| Priority | As a …​                   | I want to …​                                                                                                         | So that…​                                                                                                                                                           |
|:--------:|---------------------------|----------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `* * *`  | user                      | add tag(s) to each contact based on created tags such as florist, musician etc.                                      | I can easily understand the group this person belongs to.                                                                                                           |
| `* * *`  | user                      | add a phone number associated with each contact                                                                      | I can easily find the contact information for each contact.                                                                                                         |
| `* * *`  | user                      | add the address associated with each contact                                                                         | I can easily find the address of each contact.                                                                                                                      |
| `* * *`  | user                      | search contacts by tag                                                                                               | I can quickly see all the groups under the same tag, and find the right vendor based on the type of services provided.                                              |
| `* * *`  | user                      | add new contacts into WedLinker                                                                                      | I can store the contact details of new contacts.                                                                                                                    |
| `* * *`  | user                      | delete contacts that are no longer needed                                                                            | I can remove unnecessary contacts and have a more organised address book.                                                                                           |
| `* * *`  | user                      | search for contacts by name, address, phone number, email, wedding and task                                          | I can find specific contacts that I am looking for.                                                                                                                 |
| `* * *`  | user                      | create tags                                                                                                          | I can have special categories for non traditional clients.                                                                                                          |
|  `* *`   | user                      | edit information such as the contact number and address of each contact                                              | I can ensure all contacts have the most updated information.                                                                                                        |
|  `* *`   | user                      | clear all the contacts in the system                                                                                 | I can clear all my contacts quickly without having to individually delete them if I want to add in a completely new set of contacts.                                |
|  `* *`   | user                      | assign each guest contact its dietary requirements status                                                            | I can track the dietary requirement of each guest.                                                                                                                  |
|  `* *`   | user                      | sort contacts by alphabetical order                                                                                  | I can easily find the contacts required in a large address book.                                                                                                    |
|  `* *`   | user                      | assign additional information for each contact                                                                       | I can include important notes that may not fit into other categories, such as reminders for what the contact might need.                                            |
|  `* *`   | first-time user           | see some sample contacts already available in the app                                                                | I can try out the different features without needing to add my own data (e.g allocating people to wedding, allocating task to contacts).                            |
|  `* *`   | careless, first-time user | reload the sample contacts into the app                                                                              | I can continue trying out different features without needing to add my own data in case I accidentally cleared the contacts.                                        |
|  `* *`   | first-time user           | see a help message linking to a guide on the features and how the features can be used                               | I can easily try out all the different features with correct formats by referring to the linked guide.                                                              |
|  `* *`   | lazy user                 | want to use shortcuts for the existing commands                                                                      | I can quickly access features without needing to type the full command word.                                                                                        |
|  `* *`   | lazy user                 | want to bypass certain restraints on commands                                                                        | I can quickly access features while bypassing controls in a safe manner.                                                                                            |
|  `* *`   | user                      | assign tasks to certain contacts                                                                                     | I can track which tasks have been assigned to each contact.                                                                                                         |
|  `* *`   | user                      | update the status of tasks of contacts                                                                               | I can track whether tasks have been completed or not.                                                                                                               |
|  `* *`   | busy user                 | add multiple wedding events                                                                                          | I can track contacts for multiple weddings at once.                                                                                                                 |
|  `* *`   | busy user                 | assign each contact to a wedding                                                                                     | I can easily see which contacts are relevant to each wedding.                                                                                                       |
|  `* *`   | user                      | assign dates to a wedding                                                                                            | I can keep track of when different weddings are scheduled.                                                                                                          |
|   `*`    | forgetful user            | create links between different contacts, such as assigning a vendor to a bride or groom in a wedding                 | I can easily navigate from key stakeholders in the wedding that I remember better to vendors who I might not remember as well.                                      |
|   `*`    | user                      | re-assign tasks to another contact                                                                                   | I can account for vendors suddenly being unavailable.                                                                                                               |
|   `*`    | user                      | store multiple contact methods                                                                                       | I can contact the vendors through different means.                                                                                                                  |
|          | user                      | exclude tags from search and filter                                                                                  | I can focus on contacts that are relevant to certain events or requirements without being overwhelmed by unnecessary information.                                   |
|          | user                      | add a tag to each guest indicating their table number                                                                | I can track the table each guest is seated at.                                                                                                                      |
|          | user                      | assign a rating out of 5 to each vendor                                                                              | I can track the experience with this vendor for future reference.                                                                                                   | 
|          | user                      | send out information in standardised formatting by text or email from the application                                | I can efficiently send out information without any mistakes.                                                                                                        |
|          | user                      | share the contact details to relevant third-parties for bookings (eg: venue bookings, suit/dress rental, etc.)       | I can easily send out all relevant information (including dietary restriction, and other tags) to all the third-parties.                                            | 
|          | busy user                 | autocomplete existing tags when user is inputting tag information                                                    | I can quickly assign roles for people that might be working with others I have already input into the system and not have to type the same roles in multiple times. |
|          | user                      | assign availability to vendors                                                                                       | I can check who will be available for a particular wedding.                                                                                                         |
|          | user                      | filter availability of vendors                                                                                       | I can easily find vendors that can cater to a wedding.                                                                                                              |
|          | user                      | set reminders for tasks to different contacts                                                                        | I can easily track and follow up with clients and vendors for deliverables.                                                                                         |
|          | user                      | see a list of all tasks and reminders I have assigned to contacts in its own window                                  | I can quickly and easily see what my earliest priorities are and act on them quickly.                                                                               |
|          | user                      | see a calendar view of tasks, reminders, and weddings I have assigned                                                | I can see the whole timelines of my planned weddings and see how much time there is between tasks.                                                                  |
|          | user                      | set privacy setting for different contacts                                                                           | I can keep personal and sensitive information private when sharing address book.                                                                                    |
|          | careless user             | receive a prompt that requires me to key in a confirmation that I want to delete a contact or clear the address book | I can avoid losing all my contacts when I accidentally type delete/ clear.                                                                                          |
|          | user                      | add certain vendors as favorites                                                                                     | I can remember which vendors performed well and see if they are favorites.                                                                                          |
|          | user                      | access a list of all my favorite vendors                                                                             | I can easily check who the best vendors were that I previously engaged with.                                                                                        |
|          | user                      | generate a checklist of all the contacts for a particular wedding, grouped by roles                                  | I can keep track of who is meant to be present at the wedding.                                                                                                      |
|          | user                      | assign a time for each contact for when they are meant to arrive                                                     | I can easily keep track of which people are on time and check who to contact in case they have not arrived yet.                                                     |
|          | user                      | attach extra documents as a file to various contacts                                                                 | I can store all the information in one place, eg. Invoices from a vendor.                                                                                           |
|          | user                      | categorize tasks based on its nature (e.g. procurement, arrangement)                                                 | I can view tasks in a more organised manner.                                                                                                                        |
|          | user                      | key in the table number and get the list of guests seated at that table                                              | I can quickly identify all the groups seated at one table.                                                                                                          |

### Use cases

(For all use cases below, the **System** is the `WedLinker` and the **Actor** is the `user`, unless specified otherwise)

> Use Cases beginning with 'UC' cover core WedLinker functionalities.
>
> Use Cases beginning with 'UCSH' cover non-core WedLinker functionalities.
---
### **Use case: UC01 — List all Contacts**

**MSS**

1.  User requests to view all contacts.
2.  The system retrieves and displays the list of all contacts to the user.

    Use case ends.

### **Use case: UC02 — List all Weddings**
Similar to [<ins>UC01](#use-case-uc01list-all-contacts) except to view weddings instead of contacts.

### **Use case: UC03 — List all Tasks**
Similar to [<ins>UC01](#use-case-uc01list-all-contacts) except to view tasks instead of contacts.

### **Use case: UC04 — List all Tags**
Similar to [<ins>UC01](#use-case-uc01list-all-contacts) except to view tags instead of contacts.

---

### **Use case: UC05 — Create a Contact**

**MSS**

1.  User requests to create a contact with the corresponding details.
2.  The system adds the contact and displays a success message.
3.  The system shows the new contact in WedLinker.

    Use case ends.

**Extensions**

* 1a. The contact already exists.

    * 1a1. System does not create a new contact.
    * 1a2. System informs the user the contact already exists.

      Use case ends.

### **Use case: UC06 — Create a Wedding**
Similar to [<ins>UC05](#use-case-uc05create-a-contact) except adding a wedding to WedLinker instead of a Contact

### **Use case: UC07 — Create a Task**
Similar to [<ins>UC05](#use-case-uc05create-a-contact) except adding a task to WedLinker instead of a Contact

### **Use case: UC08 — Create a Tag**
Similar to [<ins>UC05](#use-case-uc05create-a-contact) except adding a tag to WedLinker instead of a Contact

### **Use case: UC09 — Add Phone Number to Contact**

**Guarantees:**
* No duplicate phone numbers will be stored in two different contacts.

**MSS**

1. User <ins>lists all contacts [(UC01)](#use-case-uc01list-all-contacts)</ins>.
2. User requests to add phone number for a contact with the corresponding details.
3. The system adds the phone number to the contact and displays a success message.
4. The system displays the updated contact information in the address book.

   Use case ends.

**Extensions**

* 1a. The list is empty.
  Use case ends.

* 2a. The system detects an error in the entered data.
    * 2a1. The system displays an error message

      Use case resumes at step 1.

### **Use case: UC10 — Add Address to Contact** 
Similar to [<ins>UC09](#use-case-uc09add-phone-number-to-contact) except duplicated addresses are allowed

### **Use case: UC11 — Add Email to Contact**
Similar to [<ins>UC09](#use-case-uc09add-phone-number-to-contact) except duplicated email addresses are allowed

### **Use case: UC12 — Tag a Contact**

**MSS**

1. User <ins>lists all contacts [(UC01)](#use-case-uc01list-all-contacts)</ins>.
2. User <ins>lists all tags [(UC04)](#use-case-uc04list-all-tags)</ins>.
3. User requests to tag a contact with a specified tag.
4. System displays the updated contact with the new tag assigned to them, in the list of contacts.

   Use case ends.

**Extensions**

* 1a. The contact list is empty.
  Use case ends.

* 2a.The tag list is empty.
  Use case ends.

* 3a. The contact already has the specified tag assigned to them.
    * 3a1. System displays an error message.
  
      Use case ends.

* 3b. User specified to force the tagging of a contact.
    * 3b1. System displays the specified tag, in the tag list. 
      
      Use case resumes at step 4.

* 3c. The specified tag name does not exist.
    * 3c1. System displays an error message.

      Use case resumes at step 2.

### **Use case: UC13 — Untag a Contact**

**MSS**

1. User <ins>lists all contacts [(UC01)](#use-case-uc01list-all-contacts)</ins>.
2. User <ins>lists all tags [(UC04)](#use-case-uc04list-all-tags)</ins>.
3. User requests to untag a contact with a specified tag.
4. System displays the updated contact with the tag unassigned from them, in the list of contacts.

   Use case ends.

**Extensions**

* 1a. The contact list is empty.
  Use case ends.

* 2a.The tag list is empty.
  Use case ends.

* 3a. The specified tag name does not exist.
    * 3a1. System displays an error message.

      Use case resumes at step 2.

* 3b. The contact does not have the specified tag assigned to them.
    * 3b1. System displays an error message.
  
      Use case resumes at step 2.

---

### **Use case: UC14 — Search for Contacts by Name**

**MSS**

1.  User searches for the contact by name.
2.  System shows a list of contacts containing the name.

    Use case ends.

### **Use case: UC15 — Search for Contacts by Tag**
Similar to [<ins>UC14](#use-case-uc14search-for-contacts-by-name) except searching for contacts by tag.

### **Use case: UC16 — Search for Contacts by Wedding**
Similar to [<ins>UC14](#use-case-uc14search-for-contacts-by-name) except searching for contacts by wedding.

### **Use case: UC17 — Search for Contacts by Phone Number**
Similar to [<ins>UC14](#use-case-uc14search-for-contacts-by-name) except searching for contacts by phone number.

### **Use case: UC18 — Search for Contacts by Address**
Similar to [<ins>UC14](#use-case-uc14search-for-contacts-by-name) except searching for contacts by address.

### **Use case: UC19 — Search for Contacts by Email Address**
Similar to [<ins>UC14](#use-case-uc14search-for-contacts-by-name) except searching for contacts by email address.

---

### **Use case: UC20 — Search for Wedding by Wedding Name**
Similar to [<ins>UC14](#use-case-uc14search-for-contacts-by-name) except searching for wedding by wedding name.

### **Use case: UC21 — Search for Task by Task Name**
Similar to [<ins>UC14](#use-case-uc14search-for-contacts-by-name) except searching for task by task name.

---

### **Use case: UC22 — Assign a Contact to one or more Weddings**

**MSS**

1. User <ins>lists all contacts [(UC01)](#use-case-uc01list-all-contacts)</ins>.
2. User <ins>lists all weddings [(UC02)](#use-case-uc02list-all-weddings)</ins>.
3. User requests to assign a contact to a wedding.
4. System displays the contact being associated with the specified wedding, in the list of contacts.

   Use case ends.

**Extensions**

* 1a. The contact list is empty.
  Use case ends.

* 2a. The wedding list is empty.
  Use case ends.

* 3a. The contact is already assigned to the specified wedding.
    * 3a1. System displays an error message.

      Use case resumes at step 2.

* 3b. User specified to force the assigning of a contact to a wedding
  * 3b1. System displays the specified Wedding, in the Wedding list.

    Use case resumes at step 4.

* 3c. A specified wedding does not exist.
    * 3c1. System displays an error message.

      Use case resumes at step 2.


### **Use case: UC23 — Unassign a Contact from one or more Weddings**

**MSS**

1. User <ins>lists all contacts [(UC01)](#use-case-uc01list-all-contacts)</ins>.
2. User <ins>lists all weddings [(UC02)](#use-case-uc02list-all-weddings)</ins>.
3. User requests to unassign a contact from a wedding.
4. System displays the contact no longer being associated with the specified wedding, in the list of contacts.

   Use case ends.

**Extensions**

* 1a. The contact list is empty.
  Use case ends.

* 2a. The wedding list is empty.
  Use case ends.

* 3a. A specified wedding does not exist.
    * 3a1. System displays an error message.

      Use case resumes at step 2.

* 3b. The contact is not assigned to the specified wedding.
    * 3a1. System displays an error message.

      Use case resumes at step 2.

---

### **Use case: UC24 — Assigning a Vendor**

**MSS**

1. User <ins>lists all contacts [(UC01)](#use-case-uc01list-all-contacts)</ins>.
2. User requests to assign a contact as a Vendor.
3. System displays the contact being designated as a vendor, in the list of contacts.

   Use case ends.

**Extensions**

* 1a. The contact list is empty.
  Use case ends.

* 2a. The given index is invalid.
    * 2a1. System shows an error message prompting the user to put in a valid index.

      Use case resumes at step 1.
  
* 2b. The contact is already a Vendor.
    * 2a1. System displays an error message.

      Use case resumes at step 1.


### **Use case: UC25 — Unassigning a Vendor**

**MSS**

1. User <ins>lists all contacts [(UC01)](#use-case-uc01list-all-contacts)</ins>.
2. User requests to unassign the contact as a vendor.
3. System shows the contact is no longer a vendor in the list of contacts.

   Use case ends.

**Extensions**

* 1a. The contact list is empty.
  Use case ends.

* 2a. The given index is invalid.
    * 2a1. System shows an error message prompting the user to put in a valid index.

      Use case resumes at step 1.

* 2b. The contact specified is not a Vendor.
    * 2b1. System displays an error message.

      Use case resumes at step 1.
  
* 2c. The user specified to force the unassigning of a Vendor.
    * 2c1. System displays the contact with no tasks assigned to them, in the list of contacts.

      Use case resumes at step 3.

* 2d. The Vendor still has tasks assigned to them.
    * 2d1. System displays an error message.

      Use case resumes at step 1.

---

### **Use case: UC26 — Assign one or more Tasks to a Contact**

**MSS**

1. User <ins>lists all tasks [(UC03)](#use-case-uc03list-all-tasks)</ins>.
2. User requests to assign a task to a contact.
3. System displays the contact being assigned with the specified task, in the list of contacts.

   Use case ends.

**Extensions**

* 1a. The contact list is empty.
  Use case ends.

* 1b. The task list is empty.
  Use case ends.

* 2a. The given index is invalid.
    * 2a1. System shows an error message prompting the user to put in a valid index.

      Use case resumes at step 1.

* 2b. A specified task does not exist in the System.
    * 2b1. System displays an error message.

      Use case resumes at step 1.

### **Use case: UC27 — Unassign one or more Tasks from a Contact**
Similar to [<ins>UC26](#use-case-uc26assign-one-or-more-tasks-to-a-contact) except un-assigning tasks from a contact and an additional extension. 

* 2b. A specified task is not assigned to the contact.
    * 2b1. System displays an error message.

      Use case resumes at step 1.

### **Use case: UC28 — Mark one or more Tasks as Completed**

**MSS**

1. User <ins>lists all tasks [(UC03)](#use-case-uc03list-all-tasks)</ins>.
2. User requests to mark specified task(s) as completed.
3. System displays the updated task status of the specified task(s) in the list of tasks.

   Use case ends.

**Extensions**
* 1a. The list is empty.
  Use case ends.

* 2a. A specified task index is invalid.
    * 2a1. System shows an error message prompting the user to put in a valid index.

      Use case resumes at step 1.

### **Use case: UC29 — Mark one or more Tasks as not Completed**
Similar to [<ins>UC28](#use-case-uc28mark-one-or-more-tasks-as-completed) except marking tasks as not completed.

---

### **Use case: UC30 — Delete Contact**

**MSS**

1. User <ins>lists all contacts [(UC01)](#use-case-uc01list-all-contacts)</ins>.
2. User requests to delete a specific person in the list.
3. System deletes the contact.

   Use case ends.

**Extensions**

* 1a. The list is empty.
  Use case ends.

* 2a. The to-be-deleted contact is invalid.
    * 2a1. System shows an error message prompting the user to delete a valid contact.
      Use case resumes at step 1.

### **Use case: UC31 — Delete Wedding**
Similar to [<ins>UC30](#use-case-uc30delete-contact) except deleting wedding and two additional extensions.

* 2b. User specified to force the deletion of the wedding.
    * 2b1. System displays an updated list of contacts with the specified wedding not assigned to any contacts.

      Use case resumes at step 3.
  
* 2c. There are one or more contacts still assigned to the specified wedding
    * 2c1. System displays an error message.

      Use case resumes at step 1.

### **Use case: UC32 — Delete Task**
Similar to [<ins>UC30](#use-case-uc30delete-contact) except deleting task.

### **Use case: UC33 — Delete Tag**
Similar to [<ins>UC30](#use-case-uc30delete-contact) except deleting tag and two additional extensions.

* 2b. User specified to force the deletion of the tag.
    * 2b1. System displays an updated list of contacts with the specified tag absent from all contacts.

      Use case resumes at step 3.

* 2c. A specified tag is still assigned to one or more contacts.
    * 2c1. System displays an error message.

      Use case resumes at step 1.

---

### **Use case: UCSH01 — Edit details for a Contact**

**MSS**

1. User <ins>lists all contacts (UC01)</ins>.
2. User requests to edit the details of a person and specifies what they want to change the details to.
3. System changes the existing details to the specified details and shows list of persons with new details.

   Use case ends.

**Extensions**

* 1a. The list is empty.
  Use case ends.

* 2a. The given index is invalid.
    * 2a1. System shows an error message prompting the user to put in a valid index.

      Use case resumes at step 1.

* 2b. The user does not specify what type of details they want to change.
    * 2b1. System shows an error message prompting the user to put in the type of details they want to edit.

      Use case resumes at step 1.

* 2c. The user does not specify what the new details should be.
    * 2c1. System shows an error message prompting the user to put in the new details.

      Use case resumes at step 1.

* 2d. The user specifies details that do not meet the requirements of the detail type.
    * 2d1. System shows an error message prompting the user with the correct detail type format and requirements.

      Use case resumes at step 1.

### **Use case: UCSH02 — Edit details for a Wedding**
Similar to [<ins>UCSH01](#use-case-ucsh01edit-details-for-a-contact) except editing for wedding.


### **Use case: UCSH03 — Clear all contacts**

**Guarantees:**
* No persons will be left in the system.

**MSS**

1. User requests to clear all contacts.
2. System deletes all contacts and shows a blank list of persons.

   Use case ends.

### **Use case: UCSH04 — See sample contacts in the system before starting to modify it**

Preconditions: User has not added or edited contacts previously.

**MSS**

1.  User opens the application.
2.  System shows a list of sample contacts.

    Use case ends.

### **Use case: UCSH05 — Reload sample contacts in the system**

**MSS**

1.  User deletes save file of WedLinker.
2.  System shows a list of sample contacts upon restarting application.

    Use case ends.



### **Use case: UCSH06 — See a list of all possible commands**

**MSS**

1.  User requests to see a list of all possible commands they can use in the system.
2.  System shows a link to the list of commands with their corresponding input format.

    Use case ends.

---

### Non-Functional Requirements

**Performance Requirements**
- P1: The system should be able to hold up to 1000 persons without noticeable sluggishness in performance for typical usage.
- P2: The application should respond within two seconds for most user operations.

**Usability Requirements**
- U1: A user with above-average typing speed for regular English text (not code, not system admin commands) should be able to accomplish most tasks faster using keyboard commands than using the mouse.
- U2: The application should target users who can type fast and prefer typing over other means of input, such as clicking buttons, selecting dropdowns and drag-and-drop means.
- U3: The GUI should provide clear and user-friendly error messages when operations fail to assist users in correcting mistakes.
- U4: The GUI should not cause any resolution-related inconveniences for standard screen resolutions of 1920x1080 and higher, and for screen scales of 100% and 125%.
- U5: The GUI should be usable for resolutions of 1280x720 and higher, and for screen scales of 150%.

**Compatibility Requirements**
- C1: The application should work on any _mainstream OS_ as long as it has Java `17` or above installed.
- C2: The application should be platform-independent and work on Windows, Linux, and OS-X without relying on OS-dependent libraries or features.
- C3: The application should not depend on the developer’s own remote server.

**Data Requirements**
- D1: The application should not use a Database Management System (DBMS) for data storage.
- D2: Data stored in the address book should be stored locally, in a human-editable text file.
- D2: The entire application should be packaged into a single JAR file.
- D3: The JAR file size should be within 100 MB and should not be unnecessarily bloated.
- D4: PDF documents generated for documentation should have a file size within 15 MB and should not be unnecessarily bloated.

**Documentation Requirements**
- Doc1: Documentation should be saved in PDF format using Chrome, not any other browser, and should be PDF-friendly (no expandable panels, embedded videos, or animated GIFs).
- Doc2: The Developer Guide and User Guide should be well-structured and easily navigable in PDF format. A new user should be able to quickly locate relevant information for using the product.

**Development Process Requirements**
- DP1: The software should be developed in a breadth-first incremental manner over the project duration.
- DP2: The project is expected to adhere to the milestone deadlines set for every week.

**Reliability and Stability Requirements**
- R1: The application should not crash under normal operations and should handle errors gracefully without data loss.
- R2: The application should maintain a stable performance over extended usage periods.

**Maintainability Requirements**
- M1: The software should primarily follow the Object-Oriented programming paradigm. Having a modular structure allows for the addition of new features with minimal disruption to existing functionality.

**Quality Requirements**
- Q1: The software should be usable by a novice who has never used it before.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Contact**: Contact details of an entry which includes minimally a name. Phone number, address, email address and tags are optional.
* **Tag**: An custom-made field that can be associated to contacts for ease of grouping/filtering
* **Vendor**: Businesses or persons who offer wedding services, like florists, musicians, venue in-charge, etc.
* **User**: The wedding planner who is using WedLinker

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

    1. Download the [jar file](https://github.com/AY2425S1-CS2103T-F15-4/tp/releases) and copy into an empty folder.

    2. Open the terminal and change into the directory where the jar file is stored.

    3. Enter `java -jar WedLinker.jar` into the terminal to run the WedLinker application.<br>
       Expected: Shows the GUI with a set of sample contacts, weddings, tasks, and tags. The window size may not be optimum.

2. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    2. Re-launch the app by enter `java -jar WedLinker.jar` into the terminal again.<br>
       Expected: The most recent window size and location is retained.

3. Resetting sample data

   1. Open the folder where the `WedLinker.jar` file is.

   2. Directly delete the `data` folder in the same directory.

   3. Alternatively, open the `data` folder and delete the `addressbook.json` file stored there.

   4. Re-launch the app by entering `java -jar WedLinker.jar` into the terminal again.<br>
      Expected: Shows the GUI with a set of sample contacts, weddings, tasks, and tags.

   5. Refer to [saving data](#saving-data) to understand how data is saved during operation.

<br>

### Quick Guide to Prefixes and Commands

1. WedLinker uses prefixes to parse the required fields for the commands.
   - name: `n/`
   - phone: `p/`
   - email: `e/`
   - address: `a/`
   - tag: `t/`
   - date: `d/`
   - task: `tk/`
   - wedding: `w/`
   - force: `f/`
<br><br>

2. WedLinker has the following functions
   - find a person: `find`
   - add a person: `add`
   - delete a person: `delete`
   - list all persons: `list`
   - create a tag: `create-tag` or `ctag`
   - delete a tag: `delete-tag` or `dtag`
   - tag a person: `tag`
   - untag a person: `untag`
   - list all tags: `list-tags` or `ltags`
   - create a task: `create-task` or `ctask`
   - delete a task: `delete-task` or `dtask`
   - assign a task to a vendor: `assign-task` or `atask`
   - unassign a task from a vendor: `unassign-task` or `unatask`
   - mark a task as done: `mark-task` or `mtask`
   - unmark a task as done: `unmark-task` or `untask`
   - list all task: `list-tasks` or `ltasks`
   - assign a person as a vendor: `assign-vendor` or `asv`
   - unassign a person as a vendor: `unassign-vendor` or `uv`
   - create a wedding: `create-wedding` or `cw`
   - delete a wedding: `delete-wedding` or `dw`
   - assign a person to a wedding: `assign-wedding` or `asw`
   - unassign a person from a wedding: `unassign-wedding` or `uw`
   - edit a wedding: `edit-wedding` or `ew`
   - list all wedding: `list-weddings` or `lw`


<box type="important">
    Note the difference between similar command shortcuts. 'untask' unmarks a task as done, while 'unatask' unassigns a task from a vendor.
    Similarly, 'ltags' can be used to list all tags, while 'ltasks' can be used to list all tasks (note the plural for both commands, 'tags' and 'tasks').
</box>

<br>

---

<h3 class="features">Person Features</h3>

#### Finding a person

<box type="info" seamless>

**Note:** The guide for the find command will be based on the sample address book created on application launch, assuming no persons have been deleted. The commands can be similarly
applied to edited data, but specific expected results will differ. <br><br>
**Suggestion:** Refer to User Guide for additional test cases, such as multiple prefixes or blank keywords.
</box>

1. **Finding a person by name**

   1. **Prerequisites**: List all persons using the `list` command. Multiple persons in the list. <br>

   2. **Test case**: `find n/alex` <br>
      **Expected**: The contact `Alex Yeoh` is shown in the `Person` view. If this contact has been deleted and no other contacts whose
      names have the keyword `Alex` are in the system, an empty `Person` list is shown. The status message reflects the success of the find command.

   3. **Test case**:  `find n/B` <br>
      **Expected**: The contacts `Bernice Yu`, `Irfan Ibrahim`, and `Roy Balakrishnan` are shown in the `Person` view. If any of these contacts
      have been deleted, they are not shown in the `Person` list. The status message reflects the success of the find command.

   4. **Test case**:  `find n/roy n/Irf` <br>
      **Expected**: The contacts `Irfan Ibrahim` and `Roy Balakrishnan` are shown in the `Person` view. If any of these contacts
      have been deleted, they are not shown in the `Person` list. The status message reflects the success of the find command.

   5. **Test case**:  `find n/NAMETHATDOESNOTEXIST` (where NAMETHATDOESNOTEXIST is not part of the names of any `Person` in the system) <br>
      **Expected**: The system shows a blank list of `Persons`. The status message reflects that no contacts were found to match the keyword.

   6. **Test case**: `find n/X` (where X is a character unsupported in `Person` names) <br>
      **Expected**: The system shows a blank list of `Persons`. The status message reflects that no contacts were found to match the keyword.

   7. Other incorrect find name commands to try:
      * `find`
      * `find n/` (where name keyword is blank)
      * `find n/XXX e/` (where XXX is any name keyword, and e/ is any other valid prefix in WedLinker)
        <br>
      **Expected**: `Person` list remains unchanged. System displays error describing source of problem with the command.

<br>

2. **Finding a person by address**

    1. **Prerequisites**: List all persons using the `list` command. Multiple persons in the list. <br>

    2. **Test case**: `find a/30` <br>
       **Expected**: The contacts `Alex Yeoh` and `Bernice Yu` are shown in the `Person` view. The status message reflects the success of the find command.

    3. **Test case**: `find a/serangoon a/Aljunied` <br>
       **Expected**: The contacts `Bernice Yu`, `David Li`, and `Roy Balakrishnan` are shown in the `Person` view. The status message reflects the success of the find command.

    4. **Test case**:  `find a/%%%` (where %%%  is not part of the address of any addresses of any `Person` in the system) <br>
       **Expected**: The system shows a blank list of `Persons`. The status message reflects that no contacts were found to match the keyword.

    5. Other incorrect find address commands to try:
        * `find`
        * `find a/` (where name keyword is blank)
        * `find a/XXX p/` (where XXX is any address keyword, and p/ is any other valid prefix in WedLinker)
          <br>
        **Expected**: `Person` list remains unchanged. System displays error describing source of problem with the command.

<br>

3. **Finding a person by phone**

    1. **Prerequisites**: List all persons using the `list` command. Multiple persons in the list. <br>

    2. **Test case**: `find p/88` <br>
       **Expected**: The contact `Alex Yeoh` is shown in the `Person` view. The status message reflects the success of the find command.

    3. **Test case**: `find p/92` <br>
       **Expected**: The contacts `Bernice Yu`, `Irfan Ibrahim`, and `Roy Balakrishnan` are shown in the `Person` view. The status message reflects the success of the find command.

    4. **Test case**:  `find p/XXXXXXXXXX` (where XXXXXXXXXX is not part of the phone number of any phone numbers of any `Person` in the system) <br>
       **Expected**: The system shows a blank list of `Persons`. The status message reflects that no contacts were found to match the keyword.

    5. Other incorrect find phone commands to try:
        * `find`
        * `find p/` (where name keyword is blank)
        * `find p/XXX t/` (where XXX is any phone keyword, and t/ is any other valid prefix in WedLinker)
          <br>
        **Expected**: `Person` list remains unchanged. System displays error describing source of problem with the command.

<br>

4. **Finding a person by email**

    1. **Prerequisites**: List all persons using the `list` command. Multiple persons in the list. <br>

    2. **Test case**: `find e/LI` <br>
       **Expected**: The contact `David Li` is shown in the `Person` view. The status message reflects the success of the find command.

    3. **Test case**: `find e/er e/ro` <br>
       **Expected**: The contacts `Bernice Yu` and `Roy Balakrishnan` are shown in the `Person` view. The status message reflects the success of the find command.

    4. **Test case**: `find e/XXXX@XXX.XXX` (where XXXX@XXX.XXX is not part of the email of any emails of any `Person` in the system) <br>
       **Expected**: The system shows a blank list of `Persons`. The status message reflects that no contacts were found to match the keyword.

    5. Other incorrect find email commands to try:
        * `find`
        * `find e/` (where email keyword is blank)
        * `find e/XXX a/` (where XXX is any email keyword, and a/ is any other valid prefix in WedLinker)
          <br>
        **Expected**: `Person` list remains unchanged. System displays error describing source of problem with the command.

<br>

5. **Finding a person by tag**

    1. **Prerequisites**: List all persons using the `list` command. Multiple persons in the list. <br>
       1. **Tip**: You can also enter the `list-tags` or `ltags` command to list all tags in the system. <br> <br>

    2. **Test case**: `find t/guest` <br>
       **Expected**: The contacts `Bernice Yu` and `Irfan Ibrahim` are shown in the `Person` view. The status message reflects the success of the find command.

    3. **Test case**: `find t/photographer t/HOTEL MANAGER` <br>
       **Expected**: The contacts `Alex Yeoh` and `Irfan Ibrahim` are shown in the `Person` view. The status message reflects the success of the find command.

    4. **Test case**: `find t/NONEXISTENTTAG` (where NONEXISTENTTAG is not a `Tag` in the system) <br>
       **Expected**: The system shows a blank list of `Persons`. The status message reflects that no contacts were found to match the keyword.

    5. **Test case**:  `find t/UNASSIGNEDTAG` (where UNASSIGNEDTAG is a `Tag` in the system, but there are no `Persons` tagged with it) <br>
       **Expected**: The system shows a blank list of `Persons`. The status message reflects that no contacts were found to match the keyword.

    6. Other incorrect find tag commands to try:
        * `find`
        * `find t/` (where tag keyword is blank)
        * `find t/XXX a/` (where XXX is any tag keyword, and a/ is any other valid prefix in WedLinker)
          <br>
        **Expected**: `Person` list remains unchanged. System displays error describing source of problem with the command.

<br>

6. Finding a person by wedding

    1. **Prerequisites**: List all persons using the `list` command. Multiple persons in the list. <br>
        1. **Tip**: You can also enter the `list-weddings` or `lw` command to list all weddings in the system. <br> <br>

    2. **Test case**: `find w/wedding 2` <br>
       **Expected**: The contact `Charlotte Oliveiro` is shown in the `Person` view. The status message reflects the success of the find command.

    3. **Test case**: `find w/August w/Case` <br>
       **Expected**: The contacts `Alex Yeoh`, `Charlotte Oliveiro`, and `Irfan Ibrahim` are shown in the `Person` view. The status message reflects the success of the find command.

    4. **Test case**: `find w/NONEXISTENTWEDDING` (where NONEXISTENTWEDDING is not a `Wedding` in the system) <br>
       **Expected**: The system shows a blank list of `Persons`. The status message reflects that no contacts were found to match the keyword.

    5. **Test case**:  `find w/UNASSIGNEDWEDDING` (where UNASSIGNEDWEDDING is a `Wedding` in the system, but there are no `Persons` assigned to it) <br>
       **Expected**: The system shows a blank list of `Persons`. The status message reflects that no contacts were found to match the keyword.

    6. **Test case**:  `find w/` <br>
       **Expected**: The currently shown `Person` list is unchanged. Error details shown in the status message, as blank keywords cannot be searched.

    7. **Test case**:  `find w/tom's wedding t/` <br>
       **Expected**: The currently shown `Person` list is unchanged. Error details shown in the status message, as only one prefix can be searched at a time.

    8. Other incorrect find wedding commands to try:
        * `find`
        * `find w/` (where wedding keyword is blank)
        * `find w/XXX p/` (where XXX is any wedding keyword, and a/ is any other valid prefix in WedLinker)
          <br>
        **Expected**: `Person` list remains unchanged. System displays error describing source of problem with the command.

<br>

7. **Finding a person by task**

    1. **Prerequisites**: List all persons using the `list` command. Multiple persons in the list. <br>
        1. **Tip**: You can also enter the `list-tasks` or `ltasks` command to list all tasks in the system. <br> <br>

    2. **Test case**: `find tk/invitations` <br>
       **Expected**: The contact `David Li` is shown in the `Person` view. The status message reflects the success of the find command.

    3. **Test case**: `find tk/send tk/hair` <br>
       **Expected**: The contacts `David Li` and `Irfan Ibrahim` are shown in the `Person` view. The status message reflects the success of the find command.

    4. **Test case**: `find tk/NONEXISTENTTASK` (where NONEXISTENTTASK is not a `Task` in the system) <br>
       **Expected**: The system shows a blank list of `Persons`. The status message reflects that no contacts were found to match the keyword.

    5. **Test case**:  `find tk/UNASSIGNEDTASK` (where UNASSIGNEDTASK is a `Task` in the system, but there are no `Persons` assigned to it) <br>
       **Expected**: The system shows a blank list of `Persons`. The status message reflects that no contacts were found to match the keyword.

    6. Other incorrect find task commands to try:
        * `find`
        * `find tk/` (where task keyword is blank)
        * `find tk/XXX e/` (where XXX is any task keyword, and a/ is any other valid prefix in WedLinker)
          <br>
        **Expected**: `Person` list remains unchanged. System displays error describing source of problem with the command. 

<br>

#### Listing persons

1. **Listing persons when viewing all persons.**

   1. **Test case**: `list` <br>
      **Expected**: `Person` list displayed will remain unchanged, and all persons in the `Person` list will be shown.

 <br>

2. **Listing persons when viewing a filtered view of persons.**

   1. **Test case**: `list` <br>
      **Expected**: `Person` list displayed will change to show all persons in the `Person` list.

<br>

#### Adding a person

1. **Adding a person**

   1. **Prerequisites**: List all persons using the `list` command. At least one person in the list.

   2. **Test case**: `add n/Nancy Drew p/9911882 e/nancy.drew@hotmail.com` <br>
      **Expected**: A new contact with name `Nancy Drew`, phone `9911882`, and email `nancy.drew@hotmail.com` is added to the `Person` list.

   3. **Test case**: `add n/Tatiana Komarova w/NEW WEDDING t/NEW TAG` (where NEW WEDDING and NEW TAG are a wedding and tag, respectively, that do not yet exist in WedLinker) <br>
      **Expected**: A new contact with name `Tatiana Komarova` is added to the `Person` list. A new tag with name `NEW TAG` is added to the `Tag` list.
      A new wedding with name `NEW WEDDING` is added to the `Wedding` list. The new contact, `Tatiana Komarova`, will be tagged with `NEW TAG` and
      will be assigned to the wedding `NEW WEDDING`.

   4. **Test case (following Test case 4)**: `add n/Tatiana Komarova` <br>
      **Expected**: The `Person` list remains unchanged. System displays an error indicating that the person already exists in WedLinker.

   5. **Test case**: `add n/Felicia D'Alimaty w/EXISTING WEDDING t/EXISTING TAG` (where EXISTING WEDDING and EXISTING TAG are a wedding and tag, respectively, that exist in WedLinker) <br>
      **Expected**: A new contact with name `Felicia D'Alimaty` is added to the `Person` list. No new tags or weddings will be created. The new contact, `Felicia D'Alimaty`, will be tagged with `EXISTING TAG` and
      will be assigned to the wedding `EXISTING WEDDING`.

   6. Other incorrect add commands to try:
      * `add`
      * `add n/NAME e/EMAIL@email.com tk/TASK` (where tk/ is the task prefix, and the email field restricts input values according to the User Guide)
      * `add n/` (with a blank name keyword)
        <br>
      **Expected**: `Person` list remains unchanged. System displays error describing source of problem with the command.

<br>

#### Editing a person

1. **Editing a person while all persons are being shown**

    1. **Prerequisites**: List all persons using the `list` command. Multiple persons in the list.

    2. **Test case**: `edit 1 p/9927364`<br>
       **Expected**: First contact has their phone number updated to `9927364`. Details of the edited contact shown in the status message.
       List of `Persons` is updated to show the first contact's new details.

    3. **Test case**: `edit 2 a/Blk 99, Adams Avenue e/newemail@gmail.com` (assuming there are at least 2 people in the current `Person` list displayed) <br>
       **Expected**: Second contact has their address updated to `Blk 99, Adams Avenue` and their email updated to `newemail@gmail.com`. Details of the edited contact shown in the status message.
       List of `Persons` is updated to show the second contact's new details.

    4. Incorrect delete commands to try:
        * `edit`
        * `edit 1` (where no prefixes are specified)
        * `edit x a/Blk 99` (where x is an integer larger than the list size)
        * `edit 1 tk/XXX` (where tk/ is the task prefix, which the edit command does not support - similarly, can test w/ and t/ prefixes)
          <br>
        **Expected**: `Person` list remains unchanged. System displays error describing source of problem with the command.

<br>

2. **Editing a person when a filtered list is being shown.**

    1. **Prerequisites**: List all persons using the `list` command. Use the `find` command to filter the list by either name, phone, address, email, tag, wedding, or task.
    Multiple persons in the list, but not the same number as the list of all contacts.

       1. **Tip:** If the sample data is loaded, this can be done by entering the `find n/c` command or the `find t/guest` command.
       Refer to the [find command](#finding-a-person) for more details.  <br>

    2. Valid test cases similar to 1.2 and 1.3 of [Editing a person](#editing-a-person) can be tested in filtered view.

    3. Incorrect test cases similar to 1.4 of [Editing a person](#editing-a-person) can be tested in filtered view, accounting for the lesser
       number of `Persons` in the currently displayed list.


<br>

#### Deleting a person

1. **Deleting a person while all persons are being shown**

    1. **Prerequisites**: List all persons using the `list` command. Multiple persons in the list.

    2. **Test case**: `delete 1`<br>
       **Expected**: First contact is deleted from the list. Details of the deleted contact shown in the status message.

    3. **Test case**: `delete 0`<br>
       **Expected**: No person is deleted. Error details shown in the status message. Status bar remains the same.

    4. Other incorrect delete commands to try:
       * `delete`
       * `delete x` (where x is an integer larger than the list size)
       * `delete e` (where e is a string, a non-integer, or any other data type)
       * `delete 1 random` (where the list of persons has at least 1 person, and random is an extraneous input of any data type) <br>
         <br>
       **Expected**: Similar to previous.

<br>

2. **Deleting a person when a filtered list is being shown**

    1. **Prerequisites**: List all persons using the `list` command. Use the `find` command to filter the list by either name, phone, address, email, tag, wedding, or task.
    Multiple persons in the list, but not the same number as the list of all contacts.

       1. **Tip:** If the sample data is loaded, this can be done by entering the `find n/c` command or the `find t/guest` command.
       Refer to the [find command](#finding-a-person) for more details.  <br>

    2.  **Test case**: `delete 1` <br>
        **Expected**: First contact in the filtered list is deleted. Details of the deleted contact shown in the status message. When listing all contacts,
        contact that was first in the filtered list is deleted from the list.

    3. **Test case**: `delete x` (where x is an integer greater than the number of persons shown in the current filtered list, but smaller than the number of all contacts)
       **Expected**: No person is deleted. Error details shown in the status message. Status bar remains the same.

    4. Other incorrect test cases to try can be found at point 1.iv of [Deleting a person](#deleting-a-person)

<br>

---
<h3 class="features">Vendor Features</h3>

#### Assigning vendors
   
<box type="info" seamless>

**Suggestion:** You can tell which contacts are `Vendors` and which are not by looking for the red VENDOR label in the `Person` view on the left.
</box>

1. **Assigning a `Person` as a vendor**

   1. **Prerequisites**: List all persons using the `list` command. Multiple persons in the list, not all of whom are `Vendors`.

   2. **Test case**: `assign-vendor 2` (assuming the second `Person` **is not** a `Vendor`) <br>
      **Expected**: Contact 2 is updated to be a `Vendor` and the `Person` list is updated to show this. Status message shows successful vendor assignment.

   3. **Test case (following Test case 2)**: `assign-vendor 2` (assuming the second `Person` **is now** a `Vendor`) <br>
      **Expected**: No contacts are updated and the `Person` list remains unchanged. Error details shown in status message indicating that contact 2 is already a `Vendor`.

   4. **Test case**: `assign-vendor` <br>
      **Expected**: No contacts are updated and the `Person` list remains unchanged. Error details shown in status message.

<br>

#### Unassigning vendors

<box type="info" seamless>

**Suggestion:** You can tell which contacts are `Vendors` and which are not by looking for the red VENDOR label in the `Person` view on the left.
</box>

1. **Unassigning a `Person` as a vendor**

    1. **Prerequisites**: List all persons using the `list` command. Multiple persons in the list, some of whom are `Vendors`.

   2. **Test case**: `unassign-vendor 1` (assuming the first `Person` **is** a `Vendor` with no tasks assigned) <br>
      **Expected**: Contact 1 is updated to no longer be a `Vendor` and the `Person` list is updated to show this. Status message shows successful vendor unassignment.

   3. **Test case (following Test case 2)**: `unassign-vendor 1` (assuming the first `Person` is now **no longer** a `Vendor`) <br>
      **Expected**: No contacts are updated and the `Person` list remains unchanged. Error details shown in status message indicating that contact 1 is not a `Vendor`.

   4. **Test case**: `unassign-vendor 4` (assuming the fourth `Person` **is** a `Vendor` with tasks assigned) <br>
      **Expected**: No contacts are updated and the `Person` list remains unchanged. Error details shown in status message indicating that contact 1 is a `Vendor` with tasks still assigned.

   5. **Test case**: `unassign-vendor 4 f/` (assuming the fourth `Person` **is** a `Vendor` with tasks assigned) <br>
      **Expected**: Contact 4 is updated to no longer be a `Vendor` and the `Person` list is updated to show this. All previously assigned tasks are removed from contact 4.
      Status message shows successful vendor unassignment.

   6. **Test case**: `assign-vendor` <br>
      **Expected**: No contacts are updated and the `Person` list remains unchanged. Error details shown in status message.

<br>

---
<h3 class="features">Wedding Features</h3>

#### Creating Wedding

1. **Creating a new `Wedding`**
    
    1. **Prerequisites**: List all weddings using the `list-weddings` command.

    2. **Test case**: `create-wedding w/John's Wedding`<br>
       **Expected**: `John's Wedding` is added to the list of weddings. Details of the added wedding are shown in the status message.

<br>

2. **Creating a `Wedding` already in the list of `Weddings`**

    1. **Test case**: `create-wedding w/John's Wedding` (assuming `John's Wedding` is in the list of `Weddings` already) <br>
       **Expected**: No weddings are added to list of weddings. Error details shown indicating that the `Wedding` already exists.

    2. **Test case**: `create-wedding w/JOHN'S WEDDING` (assuming `John's Wedding` is in the list of `Weddings` already) <br>
       **Expected**: No weddings are added to list of weddings. Error details shown indicating that the `Wedding` already exists.

<br>

#### Deleting Wedding

1. **Deleting a `Wedding` already in the list of `Weddings`**

    1. **Test case**: `delete-wedding w/John's Wedding` (assuming `John's Wedding` is in the list of `Weddings` already and has **no people** assigned to it) <br>
       **Expected**: `John's Wedding` is removed from the list of `Weddings`. Details of the removed wedding are shown in the status message.

   2. **Test case**: `delete-wedding w/John's Wedding` (assuming `John's Wedding` is in the list of `Weddings` already and **does have people** assigned to it) <br>
      **Expected**: No weddings are removed from list of weddings. Error details shown indicating that `John's Wedding` is still used.

   3. **Test case**: `delete-wedding w/John's Wedding f/` (assuming `John's Wedding` is in the list of `Weddings` already and **does have people** assigned to it) <br>
      **Expected**: `John's Wedding` is removed from the list of `Weddings`. Details of the removed wedding are shown in the status message.

<br>

2. **Deleting a `Wedding` not in the list of `Weddings`**

    1. **Test case**: `delete-wedding w/Maddy's Wedding` (assuming `Maddy's Wedding` is not in the list of `Weddings`)
       **Expected**: No weddings are removed from list of weddings. Error details shown indicating that the `Wedding` does not exist.

<br>

#### Editing Wedding

1. **Editing a `Wedding` in the `Wedding` list**

    1. **Prerequisites**: List all weddings using the `list-weddings` command. At least 2 weddings exist in the system.

    2. **Test case**: `edit-wedding 1 w/John's Wedding a/Address1 d/2024-12-31 ` (assuming `John's Wedding` **does not exist** in the system) <br>
       **Expected**: The first wedding's name is set to `John's Wedding`, with address `Address1` and date `2024-12-31`. The new details of the wedding are shown in the `Wedding` list.
       Details of edited wedding are shown in the status message.

    3.  **Test case**: `edit-wedding 2 w/John's Wedding a/Address1 d/2024-12-31 ` (assuming `John's Wedding` **exists** in the system) <br>
        **Expected**: No weddings are altered in the list of weddings. Error details shown indicating that `John's Wedding` already exists.

    4. **Test case**: `edit-wedding X w/Carla's Wedding` (where X is greater than the number of weddings in the `Wedding` list) <br>
       **Expected**: No weddings are altered in the list of weddings. Error details shown indicating the wedding number is too high.
   
<br>

#### Assigning Wedding

1. **Assigning a `Wedding` in the `Wedding list` to a `Person` not assigned to the `Wedding`**

    1. **Prerequisites**: List all weddings using the `list-weddings` command. At least 1 wedding exists in the system.
       List all persons using the `list` command. At least 1 `Person` exists in the list.

    2. **Test case**: `assign-wedding 1 w/John's Wedding`<br>
       **Expected**: The first contact is put in `John's Wedding`'s guest list and the first contact is assigned `John's Wedding` on its contact card.

    3. **Test case**: `assign-wedding 1 w/John's Wedding p1/` (assuming `John's Wedding` does not yet have a partner 1 assigned) <br>
       **Expected**: The first contact is set as `John's Wedding`'s `Partner 1` and the first contact is assigned `John's Wedding` on its contact card.

    4. **Test case**: `assign-wedding 2 w/John's Wedding p1/` (assuming `John's Wedding` **does** have a partner 1 assigned) <br>
       **Expected**: The first contact is set as `John's Wedding`'s `Partner 1` and the first contact is assigned `John's Wedding` on its contact card.
        `John's Wedding` is unassigned from the contact card of the contact that was previously assigned as `Partner 1`.

    5. Similar test cases to 3 and 4 can be attempted with the `[p2/]` prefix instead to test assigning weddings to contacts as `Partner 2`.
       **Expected**: Similar to expected results for 3 and 4.

    6. **Test case**: `assign-wedding 1 w/Wedding August 19th 2029` (assuming that `Wedding August 19th 2029` is not in the `Wedding` list) <br>
       **Expected**: No weddings are assigned to any contacts. Error details shown in status message indicating that the `Wedding` does not exist.

    7. **Test case**: `assign-wedding 1 w/Wedding August 19th 2029 f/` (assuming that `Wedding August 19th 2029` is not in the `Wedding` list) <br>
       **Expected**: The wedding `Wedding August 19th 2029` is created and shown in the `Wedding` list. The first contact is put in `Wedding August 19th 2029`'s guest
       list and the first contact is assigned `Wedding August 19th 2029` on its contact card.

<br>

2. **Assigning a `Wedding` in the `Wedding list` to a `Person` assigned to the `Wedding`**

   1. **Prerequisites**: List all weddings using the `list-weddings` command. At least 1 wedding exists in the system.
      List all persons using the `list` command. At least 1 `Person` exists in the list that does not have one `Wedding` assigned to them.

   2. **Test case**: `assign-wedding 1 w/John's Wedding p1/` (assuming `John's Wedding` already has person 1 assigned either in guest list, partner 1, or partner 2) <br>
      **Expected**: No weddings are assigned to any contacts. Error details shown in status message indicating that the `Wedding` is already assigned to the `Person`.

<br>


3. **Assigning a `Wedding` in the `Wedding List` to a `Person` when the `Wedding` is not in the `Wedding List`**

   1. List all persons using the `list` command. At least 1 `Person` exists in the list. 
   
   2. **Test case**: `assign-wedding 1 w/John's Wedding` (assuming that `John's Wedding` is not in the `Wedding` list)<br>
      **Expected**: No contact is assigned. Error details shown.

   3. **Test case**: `assign-wedding 1 w/John's Wedding f/` (assuming that `John's Wedding` is not in the `Wedding` list)<br>
      **Expected**: 1st contact is put in `John's Wedding`'s guest list and 1st contact has `John's Wedding` on its contact card.

<br>


#### Unassigning Wedding

1. **Unassigning a `Wedding` in the `Wedding list` from a `Person` assigned to the `Wedding`**

   1. **Prerequisites**: List all weddings using the `list-weddings` command. At least 1 wedding exists in the system.
      List all persons using the `list` command. At least 1 `Person` exists in the list that has 1 wedding assigned.
   
   2. **Test case**: `unassign-wedding 1 w/John's Wedding` (assuming the first contact is assigned to `John's Wedding`)<br>
      **Expected**: 1st contact is removed from `John's Wedding` and `John's Wedding` is removed from their contact card.

<br>

2. **Unassigning a `Wedding` in the `Wedding list` from a `Person` not assigned to the `Wedding`**

   1. **Prerequisites**: List all weddings using the `list-weddings` command. At least 1 wedding exists in the system.
      List all persons using the `list` command. At least 1 `Person` exists in the list.
   
   2. **Test case**: `unassign-wedding 1 w/John's Wedding` (assuming the first contact is not assigned to `John's Wedding`)<br>
      **Expected**: No weddings are unassigned. Error details shown.

<br>

---
<h3 class="features">Task Features</h3>

#### Creating Task

1. **Creating a new `Task`**

   1. **Prerequisites**: List all tasks using the `list-tasks` command.
   
   2. **Test case**: `create-task tk/Task1`<br>
         **Expected**: `Task1` is added to list of tasks. Details of the added task are shown in the status message.

<br>

2. **Creating a `Task` already in the list of `Tasks`**

   1. **Test case**: `create-task tk/Task1` (assuming `Task1` is in the list of `Tasks` already)<br>
      **Expected**: No tasks are added to list of tasks. Error details shown indicating that the `Task` already exists.
   
   2. **Test case**: `create-task tk/task1` (assuming `Task1` is in the list of `Tasks` already)<br>
      **Expected**: No tasks are added to list of tasks. Error details shown indicating that the `Task` already exists.

<br>

#### Deleting Task

1. **Deleting a `Task` already in the list of `Tasks`, if there is only 1 task in the list.**

   1. **Test case**: `delete-task 1`(assuming the first task is in the list of `Tasks` already) <br>
      **Expected**: 1st task is removed from the list of `Tasks`. Details of the removed task are shown in the status message.

   2. **Test case**: `delete-task 2`<br>
      **Expected**: No tasks are removed from list of tasks. Error details shown indicating that the index is invalid.

<br>

#### Assigning Tasks

1. **Assigning a `Task` to a `Person`**

    1. **Prerequisites**: List all tasks using the `list-tasks` command. There is at least one `Task` in the list of `Tasks`. List all contacts using the
       `list` command. There is are at least two contacts in the `Person` list, one of whom is a `Vendor`.

    2. **Test case**: `assign-task 1 1` (assuming contact 1 is a `Vendor` that does not have `Task` 1 assigned to them yet) <br>
       **Expected**: The first contact has `Task` on their contact card. Details of the assigned task are shown in the status message.

    3. **Test case (following Test case 2)**: `assign-task 1 1` (assuming contact 1 is a `Vendor` who now has `Task` 1 assigned to them) <br>
       **Expected**: No tasks are assigned. Error details shown in the status message indicating that the `Task` is already assigned.

    4. **Test case**: `assign-task 2 1` (assuming contact 2 is **not** a `Vendor`) <br>
       **Expected**: No tasks are assigned. Error details shown in the status message indicating that `Person` 2 is not a `Vendor`.

    5. **Test case**: `assign-task 1 X` (assuming contact 1 is a `Vendor`, and X is greater than the number of tasks in the `Task` list) <br>
       **Expected**: No tasks are assigned. Error details shown in the status message indicating that the `Task` is not a valid one.

<br>

#### Unassigning Tasks

1. **Unassigning a `Task` from a `Person`**

    1. **Prerequisites**: List all tasks using the `list-tasks` command. There is at least one `Task` in the list of `Tasks`. List all contacts using the
       `list` command. There is are at least two contacts in the `Person` list, one of whom is a `Vendor`.

    2. **Test case**: `unassign-task 1 2` (assuming contact 1 is a `Vendor` that has one `Task` assigned to them) <br>
       **Expected**: No tasks are unassigned. Error details shown in the status message indicating that the `Vendor` does not have the `Task`.

    3. **Test case (following Test case 2)**: `assign-task 1 1` (assuming contact 1 is a `Vendor` that has one `Task` assigned to them) <br>
       **Expected**: The first contact is unassigned their first `Task` on their contact card. Details of the unassigned task are shown in the status message.

    4. **Test case**: `assign-task 2 1` (assuming contact 2 is **not** a `Vendor`) <br>
       **Expected**: No tasks are unassigned. Error details shown in the status message indicating that `Person` 2 does not have any `Task` assigned.

<br>

#### Marking Task

1. **Marking a `Task` as complete**

    1. **Prerequisites**: List all tasks using the `list-tasks` command. There is at least one `Task` in the list of `Tasks`.

    2. **Test case**: `mark-task 1` <br>
       **Expected**: `Task` 1 is marked as complete. Details of complete task are shown in the status message.

    3. **Test case**: `mark-task X` (where X is greater than the number of tasks in the `Task` list) <br>
       **Expected**: No tasks are marked as complete. Error details shown in the status message indicating that the `Task` is not a valid one.

    4. **Test case**: `mark-task 1 2 3` (where there are at least three tasks in the `Task` list) <br>
       **Expected**: `Tasks` 1, 2, and 3 are marked as complete. Details of complete tasks are shown in the status message.

    5. **Test case**: `mark-task 1 2 3 4 5` (where there are only three tasks in the `Task` list) <br>
       **Expected**: `Tasks` 1, 2, and 3 are marked as complete. Error details shown in the status message indicating that other `Tasks` are not valid ones.

<br>

#### Unmarking Task

1. **Unmarking a `Task` as complete**

    1. **Prerequisites**: List all tasks using the `list-tasks` command. There is at least one `Task` in the list of `Tasks`.

    2. **Test case**: `unmark-task 1` <br>
       **Expected**: `Task` 1 is marked as incomplete. Details of incomplete task are shown in the status message.

    3. Similar test cases to Test cases 3, 4, and 5 in marking a `Task` as complete can be tested for unmark-task. Expected results are similar. 

<br>

---
<h3 class="features">Tag Features</h3>

#### Creating Tag
1. **Creating a tag when `Tag 1` is not in the list of `Tags`.**

   1. **Test case**: `create-tag w/Tag1`<br>
      **Expected**: `Tag1` is added to list of `Tags`. Details of the added tag are shown.

2. **Creating a tag when `Tag 1` is in the list of `Tags`.**

   1. **Test case**: `create-tag w/Tag1`<br>
      **Expected**: No tags are added to list of `Tags`. Error details shown.

   2. **Test case**: `create-tag w/tag1`<br>
      **Expected**: No tags are added to list of `Tags`. Error details shown.

#### Deleting Tag
1. **Deleting a tag when `Tag 1` is in the list of `Tags`.**

   1. **Test case**: `delete-tag w/Tag1`<br>
      **Expected**: `Tag1` is removed from list of `Tags`. Details of the removed tag are shown.

2. **Deleting a tag when `Tag 1` is not in the list of `Tags`.**
   1. **Test case**: `delete-tag w/Tag1`<br>
      **Expected**: No tags are removed from list of `Tags`. Error details shown.

#### Tagging contact
1. **Tagging the first contact when `Tag 1` is in the list of `Tags` and the first contact is not tagged with `Tag1`.**

    1. **Test case**: `tag 1 t/Tag1`<br>
       **Expected**: 1st contact is tagged with `Tag1`. Details of the updated contact are shown.

2. **Tagging the first contact when `Tag 1` is in the list of `Tags` and the first contact is already tagged with `Tag1`.**
    1. **Test case**: `tag 1 t/Tag1`<br>
       **Expected**: No contacts are tagged. Error details shown.

3. **Tagging the first contact when `Tag1` is in the list of `Tags` and the first contact is already tagged with `Tag1`.**
   1. **Test case**: `tag 1 t/Tag1`<br>
      **Expected**: No contacts are tagged. Error details shown.
   
4. **Tagging the first contact when `Tag1` is not in the list of `Tags`.**

   1. **Test case**: `tag 1 t/Tag1`<br>
      **Expected**: No contact is tagged. Error details shown.

   2. **Test case**: `tag 1 t/Tag1 f/`<br>
      **Expected**: 1st contact is tagged with newly created `Tag1`. Details of the updated contact are shown.

#### Untagging contact
1. **Untagging the first contact when the first contact is tagged with `Tag1`.**

   1. **Test case**: `untag 1 w/Tag1`<br>
      **Expected**: `Tag1` is removed from their contact card.

2. **Untagging the first contact when the first contact is not tagged with `Tag1`.**

   1. **Test case**: `untag 1 w/Tag1`<br>
      **Expected**: No contacts are untagged. Error details shown.

---

<h3 class="features">General Features</h3>

### Saving data

1. Saving data altered during application use <br>

    1. WedLinker automatically saves data to the hard disk during operation. Any operations that change details of contacts,
    weddings, tags, or tasks will be saved immediately following the operations is successfully executed.


2. Dealing with missing/corrupted data files

    1. Upon booting up WedLinker and the contact/wedding/tag/task list is not as per expected, open the `data` folder where WedLinker stores data.
   
    2. Within the folder, open `addressbook.json` and identify any mistakes with stored data.<br>
       The terminal from where `WedLinker.jar` is launched should log where the file is corrupted.

    3. If the data is beyond repair, delete the entire `data` folder or the `addressbook.json` file to start afresh with sample data.

<br>

---

## Appendix: Efforts

WedLinker is a project adapted from the original [AddressBook3 (AB3)](https://se-education.org/addressbook-level3/) customized specifically for wedding planners by our team of five.
Throughout this project, we split the work evenly and adhered to the principles and concepts that were taught by the
CS2103T teaching team, including:
- [Forking Workflow](https://nus-cs2103-ay2425s1.github.io/website/schedule/week7/topics.html#project-management-revision-control-forking-flow)
- [Feature Branch Flow](https://nus-cs2103-ay2425s1.github.io/website/schedule/week7/topics.html#project-management-revision-control-feature-branch-flow)
- [UML Class Diagrams](https://nus-cs2103-ay2425s1.github.io/website/se-book-adapted/chapters/uml.html#class-diagrams)
- [Testing](https://nus-cs2103-ay2425s1.github.io/website/se-book-adapted/chapters/testing.html)

As we envisioned an application for wedding planners, we concocted multiple features based on feedback from the wedding planners we interviewed.
These are the features that shape the WedLinker you see today.

#### Difficulty Level:
Adapting from the original AB3 to the WedLinker has been difficult as we opted for a more daring approach to implement 
many features that wedding planners wanted. As with any software, the more features implemented, the more bugs there are,
and WedLinker was no exception to it. 

As a result, the difficulty of developing WedLinker came not only from the
myriad of features we had to implement (and alter from the original AB3), but to also ensure that there were minimal bugs created.

#### Challenges Faced:
With the number of features that we wanted to implement, we had used the [Forking Workflow](https://nus-cs2103-ay2425s1.github.io/website/schedule/week7/topics.html#project-management-revision-control-forking-flow)
that enabled us to work on numerous features, debugs, or minor adjustments concurrently. However, with the Forking Workflow came some drawbacks that we personally experienced, such as
the extra overhead of sending everything through forks, as well as the increased possibility of merge conflicts.

The numerous number of features also meant that our test coverage had to be extensive. This was a challenge as we had to
write new test codes for every single new feature implemented and perform regression testing with all previous features that might be potentially affected.

We also faced having to make design choices such as bidirectional navigability between `Person` and `Wedding`,
resulting in further design choices in storage of these data that relies on each other whilst maintaining validity checks.

#### Effort Required:
As we had chosen to implement numerous features and inevitably introduced bugs per iteration, the effort required to
resolve these issues were higher than if we had opted to be safe and tweak AB3 just a little.

#### Achievements:
As of 12 Nov 2024, WedLinker has:

- [Most LoC in AY2425 S1 CS2103T](https://nus-cs2103-ay2425s1.github.io/tp-dashboard/?search=&sort=totalCommits%20dsc&sortWithin=title&timeframe=commit&mergegroup=AY2425S1-CS2103T-F15-4%2Ftp%5Bmaster%5D&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20)
- **20** new commands as compared to the original AB3

---

## Appendix: Planned Enhancements

Team size: 5

Based on the current implementation of WedLinker, there are known bugs and limitations that we are unable to resolve due to the 
feature freeze. The plans to improve our features are as follows: 

1. **Resolve name overflow for Tags and Weddings**: Currently, there is a small GUI bug that occurs when the names of Tags and Weddings are excessively long, causing them to overflow, which causes cosmetic flaws.
The **planned enhancement** would be to truncate the name with an ellipses `...`, particularly by limiting the children of the list panels (e.g., the left pane that shows the list of People is a list panel) to adhere
to the width restrictions of the parent.

2. **Duplicate validation for Person, Wedding, Task and Tag entities**: Currently, there is a duplicate validation bug that allows the creation of certain "duplicate" Person, Wedding, Task and Tag objects.
For example, "John Doe" and "John  Doe" (the same name but with an extra space), are not recognised as duplicates in WedLinker although they are likely to refer to the same entity in the real world.
The **planned enhancement** would be to update the parser to normalise input by stripping all extra whitespace, leaving only a single space between keywords, before creating the respective command objects.
This will ensure that entries with excessive spaces are treated as duplicates where appropriate.

3. **Vendor validation when unassigning Tasks from Person**: Currently, there is a missing validation in `unassign-task` command that negates the check of whether a Person is a Vendor, resulting 
in an incorrect error message to be shown. When a user tries to execute the `unassign-task` command on a Person who is not a Vendor and thus cannot even have tasks assigned to it, 
the error message indicates that there are no tasks in the person's list, rather than indicating that the person is not a Vendor.
However, there is no functionality flaw and the application runs as intended.
The **planned enhancements** would be to add validation to ensure the target person is a Vendor and show a more indicative error
message.

4. **Make `unassign-wedding` case-insensitive**: The `unassign-wedding PERSON_INDEX w/WEDDING_NAME` command is case-sensitive for the `WEDDING_NAME`. This means that the command will only unassign a Wedding if the case of the `WEDDING_NAME` exactly matches case of the word as stored in WedLinker.
This limits the speed with which users can use the application, and does not 
align with the case sensitivity defined for Weddings in the `Wedding::isSameWedding(Wedding)` function, nor does it reflect real-world case-sensitivity of wedding names.
The **planned enhancement** would be to ensure that when unassigning weddings, case is ignored and the `Wedding::isSameWedding(Wedding)` function is used
to check for ensure proper matching of Wedding regardless of case.

5. **Make `untag` case-insensitive**: The `untag PERSON_INDEX t/TAG_NAME` command is case-sensitive for the `TAG_NAME`.
This means that the command will only untag a Tag if the case of the `TAG_NAME` exactly matches case of the word as stored in WedLinker.
This limits the speed with which users can use the application and does not
align with the case sensitivity defined for Tag in the `Tag::isSameTag(Tag)` function, nor does it reflect real-world case-sensitivity of tag names.
The **planned enhancement** would be to ensure that when untagging Person objects, case is ignored and the `Tag::isSameTag(Tag)` function is used
to check for ensure proper matching of Tag regardless of case.

6. **Allow searching contacts with blank fields**: Currently, the `find` command does not support searching for contacts with blank field values.
For example, using the command `find a/`, where the address field is empty, will display an error on the GUI telling the user that the field to be searched cannot be empty. 
However, contacts can currently be added with all fields, other from name, blank. Thus, there may be valid cases where users want to search for contacts with certain blank fields. 
The **planned enhancement** would be to modify the `find` command to allow users to search for contacts with blank fields.
When no keywords are provided after a prefix (e.g., `find a/`), the application should return a list of contacts where the corresponding field is blank.

7. **Allow processing of multiple partner tags for `assign-wedding`**: The `assign-wedding` command accepts the parameters `w/WEDDING_NAME [p1/] [p2/]`, where the partner tags
optionally assign the person being assigned as a partner for the wedding specified by `WEDDING_NAME`. Currently, if multiple partner tags are entered, regardless of their order,
the person is assigned as the first partner in the given wedding. However, users might want to be notified of a mistake they are making in assigning a partner for a wedding
if they accidentally include both tags, especially as this might overwrite an existing partner for the wedding.
The **planned enhancement** would be to add processing that checks for two partner tags and either considers the orders of the tag (and assigns the person as whichever partner tag is specified
first), or that signals an error to the user in the command they enter.

8. **Allow automatic update of User Interface upon changing data values**: For some commands, such as `unassign-wedding`, the User Interface will update
when it is clicked on to show the updated data but, sometimes, will not automatically update when the command is run.
The **planned enhancement** would be to either add more listeners for all commands in the UI components to ensure they are always updated when a command is entered
or to make sure each command modifies the underlying lists in a way that will cause the UI to automatically update.

