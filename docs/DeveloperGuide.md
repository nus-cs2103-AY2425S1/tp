---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# BA€ Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

- Some tests and functional code was written with the help of [Github Copilot](https://github.com/features/copilot) autocomplete
- Address book app built upon [AB3](https://se-education.org/addressbook-level3/)

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

<puml src="diagrams/ParserClasses.puml" width="700"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="700" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="650" />

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

### Filter feature

#### Implementation

The filter mechanism is facilitated by `FilterCommand`. It allows users to filter the contact list based on names and/or tags. The feature is implemented using predicates that check for matches in both the contact names and tags.

The filter feature implements the following operations:

* `FilterCommand#execute()` — Applies the filter criteria to the address book's contact list
* `FilterCommandParser#parse()` — Parses the user input into name and tag criteria
* `Model#updateFilteredPersonList()` — Updates the displayed list based on the filter predicate

Given below is an example usage scenario and how the filter mechanism behaves at each step.

Step 1. The user launches the application. The contact list shows all contacts without any filters applied.

Step 2. The user executes `filter n\John t\friend t\client` command to show only contacts named "John" who are tagged as both "friend" and "client". The filter mechanism works as follows:

1. `FilterCommandParser` tokenizes the input and extracts:
    * Names: ["John"]
    * Tags: ["friend", "client"]

2. A new `FilterCommand` is created with these criteria.

3. The `execute()` method creates a predicate that:
    * Checks if the contact's name contains "John" (case-insensitive)
    * Verifies the contact has both the "friend" and "client" tags
    * Only displays contacts meeting all criteria

4. The filtered list is updated through `Model#updateFilteredPersonList()`

Step 3. The user executes `filter t\work` to show only work contacts. This creates a new filter that:
* Clears the previous name filter
* Shows only contacts tagged as "work"

Step 4. The user executes `list` to show all contacts again, removing any active filters.

The following sequence diagram shows how the filter operation works through the `Logic` component:

<puml src="diagrams/FilterSequenceDiagram.puml" alt="Filter Sequence Diagram" />

The following activity diagram summarizes what happens when a user executes a filter command:

<puml src="diagrams/FilterCommandActivityDiagram.puml" alt="FilterCommand Activity Diagram" />

#### Design Considerations

##### Aspect: How filter matching is performed

* **Alternative 1 (current choice)**: Matches name by containment and tags by exact match
    * Pros: More flexible name matching allows partial matches
    * Cons: May return unintended matches for short name queries

* **Alternative 2**: Exact matching for both names and tags
    * Pros: More precise results
    * Cons: Users need to type exact names, which is less convenient

##### Aspect: Handling multiple filter criteria

* **Alternative 1 (current choice)**: AND operation between name and tags, AND between multiple tags
    * Pros: Returns more focused results
    * Cons: May return empty results if criteria are too strict

* **Alternative 2**: OR operation between all criteria
    * Pros: More likely to return results
    * Cons: May return too many unrelated results

### Tagging Duplicate Phone Numbers

#### Implementation

The detection and tagging of duplicate phone numbers mechanism is facilitated by `DuplicatePhoneTagger`. It tracks duplicate phone numbers across all persons in the address book and automatically tags persons who share the same phone number with a "DuplicatePhone" tag. This mechanism is integrated into commands that can modify a person's phone number or the entire list, such as `add`, `edit` and `delete`.

The `DuplicatePhoneTagger` implements the following key operations:
* `DuplicatePhoneTagger#updateFrequenciesOfPhones()` — Counts and stores the frequency of each phone number
* `DuplicatePhoneTagger#updatePersonsList()` — Updates the tags of all persons based on whether their phone numbers are duplicates
* `DuplicatePhoneTagger#tagPhoneDuplicates()` — Main method that coordinates the duplicate phone tagging process

Given below is an example usage scenario and how the duplicate phone tagging mechanism behaves when executing an edit command.

1. Assume the address book has two persons: Alex (phone: 87654321) and Bob (phone: 91234567). With Alex being the 1st entry in the address book and Bob being the 2nd.

2. The user executes `edit 2 p/87654321` to change Bob's phone number to match Alex's.

3. The `edit` command first creates the modified person with the new phone number.

4. After the person is edited, `DuplicatePhoneTagger#tagPhoneDuplicates()` is called. This updates the frequency count of all phone numbers and identifies that "87654321" appears twice.

5. Finally, both Alex and Bob are tagged with the "DuplicatePhone" tag since they share the same phone number.

The following sequence diagrams show how the detection of duplicate phone numbers is carried out in the case of the `edit` operation being executed.

<puml src="diagrams/EditSequenceDiagram.puml" alt="Edit Sequence Diagram" />

<puml src="diagrams/DuplicatePhoneTaggerSequenceDiagram.puml" alt="Duplicate Phone Tagger Sequence Diagram" />

<box type="info" seamless>

**Note:** If a command fails its execution, the duplicate phone tagging process will not be triggered, so the tags will remain unchanged.

</box>

#### Design considerations

##### Aspect: When to perform duplicate phone tagging

* **Alternative 1 (current choice):** Perform tagging after any command that could modify phone numbers
    * Pros: Ensures tags are always up-to-date
    * Cons: May have slight performance impact for commands that modify multiple persons

* **Alternative 2:** Only check for duplicates when explicitly requested
    * Pros: Better performance as checking is done on-demand
    * Cons: Tags could become outdated if users forget to run the check

##### Aspect: Storage of phone number frequencies

* **Alternative 1 (current choice):** Use a HashMap to store phone frequencies
    * Pros: O(1) lookup time for checking duplicates
    * Cons: Additional memory usage to maintain the HashMap

* **Alternative 2:** Check for duplicates by scanning the person list
    * Pros: No additional data structures needed
    * Cons: O(n²) time complexity for checking duplicates
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

* salespeople who make recurring sales
* has a need to manage a significant number of contacts
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps
* frequently uses contacts for work (business)
* has a need to manage multiple pieces of information about each contact


**Value proposition**:

* manage contacts faster than a typical mouse/GUI driven app
* prioritise contacts for scheduling and work efficiency
* consolidates many different pieces of information about contacts
* get an overview of all contacts easily
* can search and filter for specific types or pieces of information quickly


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                 | So that I can…​                                            |
|----------|--------------------------------------------|------------------------------|-------------------------------------------------------------------|
| `* * *`  | new user                                   | see usage instructions       | refer to instructions when I forget how to use the App            |
| `* * *`  | user                                       | add a new person             | keep my contact list up to date                                   |
| `* * *`  | user                                       | delete a person              | remove entries that I no longer need                              |
| `* * *`  | user                                       | find a person by name        | locate details of persons without having to go through the entire list |
| `* * *`  | user with clients who need prioritization  | filter clients by contract value or last contact | identify clients that need attention to hit my KPIs|
| `* *`    | user with many clients                     | filter persons by tags       | locate persons by category without having to go through the entire list |
| `* *`    | user who handles client renewals           | filter clients by renewal date | focus on those whose contracts are renewing soon                  |
| `* *`    | user with many clients                     | export contacts to csv       | share address books                                        |
| `* *`    | user with busy schedules                   | sort persons by priority metrics | prioritize the most critical clients easily                       |
| `*`      | user with many persons in the address book | sort persons by name         | locate a person easily                                            |
| `* *`    | stressed admin personnel                   | access quick help            | get command assistance without losing time when I forget the commands |


### Use cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Delete a person**

**MSS**

1.  User requests to list persons
2.  AddressBook shows a list of persons
3.  User requests to delete a specific person in the list
4.  AddressBook deletes the person

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. AddressBook shows an error message.

      Use case resumes at step 2.

**Use case: Add a person**

**MSS**

1.  User requests to add person
2.  AddressBook shows a input form for person's details
3.  User enters person details like name, phone number, email, tags etc
4.  AddressBook validates user input
5.  AddressBook saves the person

    Use case ends.

**Extensions**

* 4a. The user input is invalid.

   * 4a1. AddressBook shows an error message.
   * 4a2. AddressBook requests for the correct data.
   * 4a3. User enters new data.
   * Steps 4a1-4a2 are repeated until the data entered are correct.
   * Use case resumes at step 5.

**Use case: Filter by tag**

**MSS**

1.  User requests to filter persons by tag
2.  AddressBook shows a input search bar
3.  User enters input related to key of a tag
4.  AddressBook shows a list of persons with matching tag key
    Use case ends.

**Extensions**

* 4a. The list is empty.

  Use case ends.

**Use case: Advanced Filter by Tag**

**MSS**
1.  User requests to advanced filter persons by tag
2.  AddressBook shows a input search bar
3.  User enters input related to a key, an operator and a value of a tag
4.  AddressBook shows a list of persons with tags matching the criterion

    Use case ends.

**Extensions**

* 4a. The list is empty.

  Use case ends.

**Use case: Export to CSV**

**MSS**

1.  User requests to export file to CSV
2.  AddressBook opens file manager for user to choose destination directory
3.  User enters destination directory and file name
4.  AddressBook outputs list of persons to CSV file in corresponding destination
5.  AddressBook opens destination directory containing CSV file

    Use case ends.

**Extensions**

* 2a. AddressBook is unable to open file manager.

   * 2a1. AddressBook shows an error message.
     Use case ends.



### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should work on any _mainstream OS_ without requiring any further installation or additional platform-specific dependencies
3.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
4.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
5.  Should automatically save changes to the local data file, and it should be able to recover from unexpected shutdowns or power failures without data loss.
6.  Should have a responsive and intuitive user interface that works well across different screen resolutions (1920 x 1080 or higher).
7.  Should be easy to use and provide clear instructions and feedback to the user for common tasks like adding, editing, filtering, and exporting contacts.
8.  Should be designed to accommodate future growth in the number of persons and additional features without major rewriting of code.
9.  Should be modular and easy to test.

### Glossary

* **Mainstream OS**: Windows, Linux, MacOS
* **Command-line Interface (CLI)**: A text-based user interface where the user interacts with the application by typing commands, as opposed to a Graphical User Interface (GUI).
* **Typical Usage**: The expected day-to-day usage of the application by the target user, which should not cause significant performance degradation.
* **Above-average Typing Speed**: A typing speed that is higher than the average user (40 words per minute), allowing the user to input commands and data more efficiently.
* **Platform-specific dependencies**:  Additional software required to run the application due to differences in Mainstream OS.
* **Local data file**: File stored on and only on the user's computer/device running the application.
* **Sensitive Data**: Data in any form (often text) that may harm the user or persons if exposed to unwanted parties (e.g. financial data, credit card info)
* **File Manager**: OS-specific software that allows user to view and manage (create, delete etc) files on their computer/device. Often comes pre-installed as part of the OS.
* **CSV**: Type of format of file that stores data in an ordered fashion using rows and columns. Often used in third-party spreadsheet software such as Microsoft Excel.
* **Tag**: Form of text-based labelling to categorise persons or data for organisation.

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

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Clearing all entries

1. **Clear all contacts**
   1. Prerequisites: Add at least one person to the address book.
   2. Test case: `clear`
   3. Expected: All contacts are removed from the list

### Adding a person (including a duplicate phone case)

1. **Add a person normally**
   1. **Test case:** `add n\Jane Doe p\98765432 e\janedoe@example.com a\123 Maple Street fi\middleClass s\@janeDoe t\friends:4 t\priority:3 t\client`
   2. **Expected:** Jane Doe is added to the address book with specified details and tags. The UI shows the updated contact list.

2. **Add a person with the same phone number (duplicatePhoneTagger)**
   1. **Prerequisites:** Jane Doe already exists in the address book.
   2. **Test case:** `add n\John Smith p\98765432 e\johnsmith@example.com a\45 Elm Road fi\highIncome s\@johnSmith t\friends:5 t\priority:2`
   3. **Expected:** John Smith is added, but a label (duplicatePhoneTagger) appears in the UI indicating that a duplicate phone number is present.

3. **Add an additional person normally, for the later parts of testing**
   1. **Test case:**: `add n\Tim Jobs p\98222432 e\timjo@example.com a\1 Infinity Loop fi\billionaire s\@tJobs t\friends:7 t\priority:1 t\client`
   2. **Expected:** Tim Jobs is added to the address book with specified details and tags. The UI shows the updated contact list.

### Filtering contacts

1. **Filter by a single tag**
   1. **Prerequisites:** Add at least two contacts with different tags.
   2. **Test case:** `filter t\client`
   3. **Expected:** The list shows only contacts tagged as `client`.

2. **Filter by name and tag**
   1. **Test case:** `filter n\Jane t\client`
   2. **Expected:** The list shows contacts whose name contains "Jane" and who have the `client` tag.

### Advanced Filtering of contacts (advfilter)

1. **Filter with a comparison operator on tags with values**
   1. **Prerequisites:** At least one contact with a tag that has a numeric value (e.g., `t\friends:4`).
   2. **Test case:** `advfilter t\friends > 4`
   3. **Expected:** The list displays only contacts with a `friends` tag greater than 4.

2. **Filter with equality on tags with specific values**
   1. **Test case:** `advfilter t\priority = 3`
   2. **Expected:** Only contacts with a `priority` tag set to `3` are displayed.

### Sorting contacts

1. **Sort contacts by tag values in ascending order**
   1. **Prerequisites:** At least two contacts with a tag that has a value (e.g., `t\friends:5`).
   2. **Test case:** `sort t\friends asc`
   3. **Expected:** Contacts are sorted in ascending order by the value of the `friends` tag.

2. **Sort contacts by tag values in descending order**
   1. **Test case:** `sort t\priority desc`
   2. **Expected:** Contacts are sorted in descending order by the value of the `priority` tag.

### Exporting contacts

1. **Export contact list to a file**
   1. **Prerequisites:** Have a list of contacts available to export.
   2. **Test case:** Run the `export` command
   3. **Expected:** Contacts are exported to a file in CSV format. Verify that all contact details are present in the file.

### Saving data

1. **Dealing with missing/corrupted data files**
   1. **Prerequisites:** BA€ is not running
   2. **Test case:**
      1. To simulate a corrupted file, navigate to the data folder, and find addressbook.json
      2. Delete addressbook.json
      3. Launch BA€.
   3. **Expected:** A new addressbook.json file will be created and it will be empty.
