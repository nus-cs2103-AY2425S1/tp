---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# UniLink Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* The methods for the addition of remark, module and telegram handle fields were adapted from AB3 Tutorial 2.

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

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-W12-3/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-W12-3/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
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

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-W12-3/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-W12-3/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-W12-3/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-W12-3/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

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
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-W12-3/tp/blob/master/src/main/java/seedu/address/model/Model.java)


<puml src="diagrams/ModelClassDiagram.puml" width="100%" />



The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="750" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-W12-3/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="700" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Add feature

#### Implementation

The `add` command allows users to add a new contact to the addressbook with required and optional fields.
The command format is as follows:<br>
`add n/NAME ct/CONTACT_TYPE [h/TELEGRAM_HANDLE] [p/PHONE_NUMBER] [e/EMAIL] [m/MODULE] [r/REMARK] [t/TAG]…​`

* **Required fields**: `NAME`, `CONTACT_TYPE`, at least one of `TELEGRAM_HANDLE`, `PHONE_NUMBER` or `EMAIL`
* **Optional fields**: `TELEGRAM_HANDLE`, `PHONE_NUMBER`, `EMAIL`, `MODULE`, `REMARK`, `TAG`

The command parser identifies each prefix (e.g. `n/`, `ct/`, `h/`) and stores the associated data in a new contact.
The optional fields allow users to include more detailed information, making the contact record customizable.

#### Key Components and Operations

* **Parser**
  * The `Parser` component processes the `add` command string to extract required and optional fields.
  * It uses prefixes (`n/`, `ct/`, `h/`, etc.) to correctly identify each piece of data and verify that required fields
  (`NAME`, `CONTACT_TYPE` and one of `TELEGRAM_HANDLE`, `PHONE_NUMBER`, `EMAIL`) are present. If any required field is missing,
  an error is raised, prompting the user to provide the necessary information.
* **Logic Manager**
  * The parsed information is passed to `LogicManager#execute()`, which transfers control to the `Storage` component
* **Storage**
  * The information is stored using the `JsonAddressBookStorage#saveAddressBook()` method which calls the `JsonSerializableAddressBook`
    constructor, to create an object that can be serialized in JSON format.

<puml src="diagrams/AddSequenceDiagram.puml" width="100%" />


#### Design Considerations

* **Field Flexibility**
  * Optional fields allow users to store as much or as little information as needed.
  * The design enables future expansion if additional fields are required, with only a need of minor adjustments to the parser.
* **Error Handling**
  * Required fields are validated to prevent incomplete records.
  * Invalid formats (e.g. incorrectly formatted phone numbers or emails) are checked and validated to maintain data consistency.

### Switch Theme feature

#### Implementation

The `switch` theme command allows users to change the display to "light" mode or "dark" mode according to their preference. The preferred theme will be stored and displayed every time the user opens the app.<br>
The command format is as follows:<br>
`switch THEME​`

* `THEME` can be either `LIGHT` or `DARK`

#### Key Components and Operations

* **Parser (SwitchThemeCommandParser)**
    * The `Parser` component processes the `switch` command string to extract the requested theme ('light' or 'dark'), creating a new `SwitchThemeCommand` with the specified theme.
    * If the input format is invalid, a `ParseException` is raised with an error message, prompting users to enter a valid input.
* **Logic (SwitchThemeCommand)**
    * After parsing, `SwitchThemeCommand#execute` calls `ThemeController.switchTheme()` to apply the new theme.
    * A message is returned to the user to confirm the theme change.
* **UI (ThemeController)**
    * The `ThemeController` clears current stylesheets, applies the specified stylesheet and logs the theme change.
    * The selected theme is stored via `ThemePreference.setTheme()`, which calls `saveThemePreference()` to update the JSON file.
* **Storage (ThemePreference)**
    * `ThemePreference` loads the saved theme from `themePreference.json`, or defaults to `LIGHT` if none is found.

<puml src="diagrams/SwitchThemeSequenceDiagram.puml" width="100%" />

#### Design Considerations

* **User experience**
    * After executing `SwitchThemeCommand`, users receive immediate confirmation of the theme change, providing clarity and an improved experience.
    * Saving the user's theme preference ensures a consistent experience each time the application is launched, enhancing usability.
* **Error Handling**
    * Invalid theme inputs raises clear errors, guiding users on valid options.
    * `ThemePreference` manages file I/O errors with warnings, defaulting to `LIGHT` if any issues arise with loading or saving preferences.

### Import feature

#### Implementation

The `import` command allows users to import multiple contacts from a .csv file. The command allows for convenient distribution and importing of contacts. Contact distributors (e.g. course coordinators) can compile many contacts at a time (e.g. course TAs), with appropriate contact information and distributte them to users.<br>

The command format is as follows:<br>
`import​`

#### Key Components and Operations

* **Converter (CsvToJsonConverter)**
    * The `Converter` component processes the `.csv` files to convert each file into a `.json` file. It first requests all the fields that compose a `Person` class. It then reads the `.csv` file headers for headers that match these fields (case-insensitive). 
    * It will then read the `.csv` file line by line, to parse each line under a valid header into a properly formatted `.json` object, which is then added to a jsonFile. 
    * It writes the contents of the `.csv` file into a `.json` file with the same name.
    * After converting all `.csv` files, these individual `.json` files are put into an `ArrayList` of `.json` files and returned
    * If the input format is invalid, a `ConverterException` is raised with an error message.
      * This can occur in the case that the `.csv` is empty, or invalid, or the Import folder is empty or missing
* **Importer (jsonImporter)**
    * The importer constructor takes in a `List<File>` that should contain the `.json` files to be imported.
    * Upon calling `importAllJsonFiles()`, the importer will loop through each `.json` file in the list, parse them, then convert them to `AddressBook.class`, and add each `.json` file to the `model`.

<puml src="diagrams/ImportCommandSequenceDiagram.puml" width="1100" />
<puml src="diagrams/ConversionSequenceDiagram.puml" width="400" />
<puml src="diagrams/ImportSequenceDiagram.puml" width="1100" />

#### Design Considerations

* **Error Handling**
    * Empty/Invalid contact information should be skipped or left empty, depending on whether the missing/invalid information is compulsory
      * Non-compulsory fields such as `phone`, `email`, and `telegramHandle` require at least one entry, the rest can be left empty
      * Upon encountering empty/invalid compulsory fields, such as `name` or `contactType`, these entries will be skipped by the `Converter`
    * Missing Import folder should be re-initialised everytime the app is restarted
    * Empty Import folder will result in an error being thrown


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

* National University of Singapore (NUS) student who
  * meets people from many different places (e.g. different classes, CCAs, student accommodation, etc.)
  * has a need to manage a significant number of contacts
  * prefers desktop apps over other types
  * can type fast
  * prefers typing to mouse interactions
  * is reasonably comfortable using CLI apps

**Value proposition**:
University students meet people from many different places (e.g. different classes, CCAs, student accommodation, etc). As such, they often have too many contacts that are hard to keep track of. Thus, we hope to make it easier to categorise and find contacts when they need them.



### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​  | I want to …​                                                                          | So that I can…​                                                         |
|----------|----------|---------------------------------------------------------------------------------------|-------------------------------------------------------------------------|
| `* * *`  | new user | easily access usage instructions                                                      | refer to instructions when I forget how to use the App                  |
| `* * *`  | student  | add new contacts with their details (eg. name, telegram handle, contact type, module) | keep track of my university contacts in the app                         |
| `* * *`  | student  | delete a contact                                                                      | remove entries that I no longer need for university                     |
| `* * *`  | student  | find a person by name, telegram handle or tag                                         | locate details of persons without having to go through the entire list  |
| `* * *`  | student  | edit contact details (e.g. phone number, email)                                       | so that I can keep the information of my university contacts up to date |
| `* *`    | student  | add a new contact with multiple tags (e.g., CCA, classmate)                           | categorise them based on different associations                         |
| `* *`    | student  | categorise contacts into different contact types (eg. work, personal)                 | organise people according to my needs                                   |
| `* *`    | student  | be able to filter my contacts based off different contact types                       | find my contacts faster                                                 |
| `* *`    | student  | tag contacts with multiple categories                                                 | find them easily in different contexts                                  |
| `* *`    | student  | import contacts from csv files                                                        | quickly add a large number of contacts without entering them manually   |
| `*`      | student  | receive reminders to reach out to contacts I haven’t communicated with in a while     | maintain my connections                                                 |


### Use cases

(For all use cases below, the **System** is `UniLink` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC001 - Add a new contact**

**MSS**

1.  User requests to add a new contact
2.  User enters the required contact details
3.  UniLink validates the entered details
4.  UniLink adds the new contact
5.  UniLink displays the updated contact list

    Use case ends.

**Extensions**

* 3a. The entered data is invalid
  * 3a1. UniLink shows an error message indicating fields that could be incorrect.
  * 3a2. User re-enters the new data

    Steps 3a1-3a2 are repeated until the data entered is correct.

    Use case resumes from step 4.

* 3b. User enters a duplicate contact.

    * 3b1. UniLink shows an error message indicating the contact already exists.

      Use case ends.


**Use case: UC002 - Delete a person**

**MSS**

1.  User requests to list persons
2.  UniLink shows a list of persons
3.  User requests to delete a specific person in the list
4.  UniLink deletes the specified person from the list

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. UniLink shows an error message indicating the index is invalid.

      Use case resumes at step 2.

**Use case: UC003 - Edit a contact**

**MSS**

1.  User requests to list persons
2.  UniLink shows a list of persons
3.  User requests to edit a specific person in the list
4.  User enters the new details for the contact to be updated
5.  UniLink updates the contact
6.  UniLink displays the updated contact list

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. UniLink shows an error message indicating the index is invalid.

      Use case resumes at step 2.

* 4a. The entered data is invalid

    * 4a1. UniLink shows an error message indicating fields that could be invalid.
    * 4a2. User re-enters the new data


  Steps 4a1-4a2 are repeated until the data entered is correct.

  Use case resumes from step 5.

* 4b. The edited contact results in a duplicate
  * 4b1. UniLink shows an error message indicating that a duplicate contact already exists.

  Use case ends.

**Use case: UC004 - View contact list**

**MSS**

1.  User requests to view the list of persons
2.  UniLink shows the full list of persons with basic contact details.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

**Use case: UC005 - Switch Theme**

**MSS**

1. User requests to switch theme
2. UniLink changes the theme to the specified option (e.g., light or dark mode)

    Use case ends.

**Extensions**

* 1a. The given theme is invalid.

    * 1a1. UniLink shows an error message indicating that the theme is invalid.

      Use case resumes at step 1.

**Use case: UC006 - Find contacts by name**

**MSS**

1.  User requests to find contacts by name
2.  User enters a keyword representing part or all of a contact’s name
3.  UniLink shows a list of persons with names containing the keyword

    Use case ends.

**Extensions**

* 2a. The entered data is invalid
    * 2a1. UniLink shows an error message
    * 2a2. User re-enters a new keyword

      Steps 2a1-2a2 are repeated until the data entered is correct.

      Use case resumes from step 3.

  Use case ends.

**Use case: UC007 - Filter contacts by contact type**

**MSS**

1. User requests to filter contacts by contact type
2. User specifies the contact type they want to filter by
3. UniLink shows a list of contacts that match the specified contact type

   Use case ends.

**Extensions**

* 2a. The specified contact type is invalid

    * 2a1. UniLink shows an error message indicating the contact type is invalid

      Use case resumes from step 1.

* 3a. No contacts match the specified contact type

    Use case ends.

**Use case: UC008 - Import contacts from .csv file**

**MSS**
1. User adds one or more .csv file(s) to Import folder
2. User requests to import contacts from .csv file(s)
3. UniLink imports contacts

**Extensions**

* 1a. There is no Import folder
  * 1a1. UniLink shows error message
  * 1a2. User restarts program to re-initialise Import folder

    Use case resumes from step 1


* 1b. The .csv file is empty 
  * 1b1. UniLink shows error message
  * 1b2. User attempts to import another .csv file

    Use case resumes from step 1

* 3a. One (or more) of the contacts are invalid (Do not have valid contact info/ missing name/ missing contact type)
  * 3a1. UniLink skips over invalid contacts

    Use case resumes from step 3


* 3b. There are duplicate contacts/ contacts in .csv file already exist in addressbook
  * 3b1. UniLink skips over duplicate contacts

    Use case resumes from step 3

### Non-Functional Requirements

1. Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage. (Efficiency and performance)
2. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse. (Accessibility)
3. The system should work on common desktop operating systems, including Windows, macOS, and Linux. (Environment)
4. The system must include comprehensive user and developer documentation to support easy onboarding for both end-users and future developers. (Documentation)
5. The system should be modular and follow an OOP (Object-Oriented Programming) approach, allowing for easy feature extensions or modifications. (maintainability)
6. The system’s user interface should be intuitive and require no more than 10 minutes of learning for a new user. (UX)
7. The system should undergo regular code reviews to maintain high code quality. (Quality)

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Architecture Diagram**: A visual representation of the system's structure, showing components and their relationships.
* **JSON**: JavaScript Object Notation, /    A lightweight data-interchange format used to represent structured data, often in key-value pairs.

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

   2. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

3. Saving theme preferences

    1. Enter the command `switch light` or `switch dark` to set the theme of the app. Close the window.

    2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent theme is retained.

### Adding a person

1. Adding a person with minimum fields (name, contact type, telegram handle)

    1. Test case: `add n/Nicole Lee ct/work h/@nicole_lee`<br>
       Expected: New contact added to the list with the details provided. Details of the added contact are shown in the status message.

    2. Test case: `add n/Nicole 333 ct/work h/@nicole_lee`<br>
       Expected: No person added. Error details displayed in the status message.

    3. Other incorrect add commands to try: `add`, `add n/Nicole Lee`, `add Nicole Lee`, `...`<br>
       Expected: Similar to previous.

2. Adding a person with all fields (name, contact type, telegram handle, phone number, email, module, remark, tags)

    1. Test case: `add n/Nicole Lee ct/work h/@nicole_lee p/98765432 e/nicolelee@example.com m/CS2103T r/likes coding t/friend`<br>
       Expected: New contact added to the list with the details provided. Details of the added contact are shown in the status message.

    2. Test case: `add n/Nicole Lee ct/work h/@nicole_lee p/98765432 e/nicolelee@example.com m/CS2103T r/likes coding t/friend t/colleague t/student`<br>
       Expected: New contact added to the list with the details provided. Details of the added contact are shown in the status message.

    3. Test case: `add Nicole Lee work @nicole_lee 98765432 nicole@example.com CS2103T likes coding friend`
       Expected: No person added. Error details displayed in the status message.

### Editing a person's contact details

1. Editing a person's telegram handle from an existing contact

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    2. Test case: `edit 1 h/@ashley_`<br>
       Expected: Telegram handle of first contact updated to `@ashley_`. Details of the edited contact are shown in the status message.

    3. Test case: `edit 0 h/@ashley_`<br>
       Expected: No person is edited. Error details shown in the status message.

    4. Other incorrect edit commands to try: `edit h/@ashley_`, `edit 1 @ashley_`, `edit x @ashley_`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

2. Editing multiple fields for an existing contact (e.g. email, telegram handle)

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    2. Test case: `edit 1 e/ashley@example.com h/@ashley_`<br>
       Expected: Email and telegram handle of first contact is updated to `ashley@example.com` and `@ashley_` respectively. Details of the edited contact are shown in the status message.

    3. Test case `edit 0 e/ashley@example.com h/@ashley_`<br>
       Expected: No person is edited. Error details shown in the status message.

    4. Other incorrect edit commands to try: `edit e/ashley@example.com h/@ashley_`, `edit 1 e/ashley@example.com @ashley_`, `edit e/ashley@example.com h/@ashley_`, `...`<br>
       Expected: Similar to previous.

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   2. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact are shown in the status message.

   3. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message.

   4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

2. Deleting a person when the list is empty

    1. Prerequisite: Ensure that there are no contacts in the app.

    2. Test case: `delete 1`<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

    3. Other incorrect delete commands to try: `delete`, `delete x` , `...` (where x can be any number)<br>
       Expected: Similar to previous.


### Saving data

1. Dealing with missing data file

    1. Navigate to `./data/addressbook.json` and delete the `addressbook.json` file.

    2. Launch the app by double-clicking the jar file.<br>
       Expected: The default list of contacts is loaded.

2. Dealing with corrupted data file

    1. Navigate to `./data/addressbook.json`. Right click the `addressbook.json` file and open in TextEdit.

    2. Delete all the contents of the file and type some symbols (e.g. `&*$@`).

    3. Launch the app by double-clicking the jar file.<br>
       Expected: The app loads with no contacts.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Effort**
This project required substantial effort due to the complexity of expanding beyond the initial structure of AddressBook Level 3 (AB3).
The following describes the challenges, difficulty level, and effort involved, as well as achievements attained through enhancements and
additional features.

### Difficulty Level and Challenges

* **Additional Fields**: Our project manages additional fields such as Telegram handle, module, and contact type. Integrating these fields required modifying both data structures and UI components, as well as adapting backend logic to manage and validate these new attributes.
* **Enhanced Search Functionality**: We implemented additional search functionalities, allowing users to find contacts by tags and Telegram handles, in addition to names. This required integration of the find command with additional fields.
* **UI Enhancements**: Significant effort went into redesigning the user interface to make it aesthetically pleasing. In addition to improving layout and visual styling, we introduced both light and dark modes, ensuring each element was clearly visible in both themes.

### Effort

* **Custom Implementation and Adaptation** The majority of the codebase was custom-developed to meet the specific needs of our project, with very limited reuse of existing libraries or tools beyond those provided in AB3. For example, the additional fields, search functionality, and UI customization were built from scratch, significantly increasing the project’s scope and difficulty level.

### Achievement

* Our team successfully extended the basic functionality of AB3, transforming it into a more versatile and feature-rich application. The enhanced search capabilities and the addition of new fields significantly improved the usability of the application, allowing for more efficient contact management. The aesthetically refined UI, with support for both light and dark modes, further elevated the user experience and set our project apart from AB3.

In summary, this project required considerable effort due to the added functionality, custom UI work, and enhanced search features. The limited reuse of existing libraries meant most of the code had to be written and adapted by our team, adding to the overall effort invested.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

**Team Size: 5**

1. **Prevent duplicate entries when using the `edit` command:** 
* Current Issue: When using the edit command, users can edit contact details such that two contacts can have the same Telegram handle, phone number and/or email. 
* Example: When users do the following commands in order, both contacts will have the same Telegram handle.
  * `add n/Amy One ct/personal t/@amyone`
  * `add n/Amy Two ct/personal t/@amytwo`
  * `edit 2 t/@amyone`
* Proposed Change: Ensure that duplicate entries cannot be added if it already exists in the address book.

2. **Long inputs make it hard to read certain contact details**
* Current Issue: When users type in fields with long character lengths, some contact details may be truncated in the display panel, making it hard for users to view.
* Example: The command `edit r/` with 200 characters will show truncated text up to window size
* Proposed Change: Introduce horizontal scrolling within the contact panel, allowing users to scroll to view all contact details.

3. **Prevent duplicate fields with case insensitivity**
* Current Issue: It is possible for two different contacts to have the same email address or telegram handle if the fields are typed in different cases. This leads to duplicate entries.
* Example: The following commands will not lead to any error despite the two contacts having the same email address (in different cases)
  * `add n/Amy ct/work e/amy123@example.com`

  * `add n/Bob ct/work e/AMY123@EXAMPLE.COM`
* Proposed Change: Introduce case-insensitive validation when contacts are added or edited. If a duplicate email or telegram handle is detected, the action will be prevented with an accompanying error message.

4. **Add labels for each field for better readability**
* Current Issue: When fields are left blank, the space where the data is supposed to be will be empty. It may be hard to differentiate certain fields as well, such as a phone number and a remark with a string of numbers. 
* Example: This is how the contact is displayed when the following command is entered:<br>
`add n/Amy ct/work p/87654321 r/12345`<br>
![img.png](images/emptySpaceUiBug.png)
* Proposed Change: Add labels next to each field in the contact, so that contact fields are clear and empty fields are made obvious. This will help to improve readability and reduce confusion. 

5. **Change background colour of UI from white to theme colour** 
* Current Issue: For the current UI, when there are only a few contacts (contacts do not fill the screen), the background of the contact list is shown in white. This can be especially disconcerting for the 'dark' mode, as it looks very bright in contrast to the dark theme.
* Example: `find alex` returns only 1 person when using the default addressbook.json, resulting in a white background in the contact list.
* Proposed Change: Change the contact list background to match the background colour of the app.

6. **Allow for deletion of optional fields**
* Current Issue: The only way to remove optional fields of a contact currently is to delete the entire contact and re-add the contact with all the fields except the one being deleted. There is no way of simply deleting an optional field.
* Example: A user wants to remove the remark field from a contact named John Doe who has the details: `ct/Work n/John Doe p/98765432 e/johndoe@example.com r/Met at conference`. Currently, the only way to remove the remark field is to delete the entire contact and re-add it without the remark field.
* Proposed Change: Allow for deletion of optional fields of a contact using the `edit` command (e.g. `edit 1 r/` can delete the remark of the contact)

7. **Provide feedback for skipped contacts during import**
* Current Issue: Currently, when the import function encounters any invalid contacts, it skips over these unimported contacts without providing feedback. This leaves users unaware of which contacts were not imported and why they were skipped. Additionally, there is a related issue where if a contact in an initial `.csv` file has only 4 fields filled in and is successfully imported, but a later `.csv` file contains the same contact with more fields (e.g., 6 fields), the import function skips the contact as a duplicate without notifying the user.
* Example: 
  * If a contact in the `.csv` file is missing the `contactType` or if the `contactType` is invalid, the import function will skip over that contact without any notifications or reasons provided.
  * If a contact such as `John Doe` is imported from the first `.csv` file with 4 fields filled (`ct/Work n/John Doe p/12345678 e/johndoe@example.com`) and a subsequent `.csv file` has `John Doe` with 6 fields filled, the import function will skip it as a duplicate without any indication or feedback.
* Proposed Change: Enhance the import function to provide detailed feedback for skipped contacts. This feedback should specify which contacts could not be imported and include the reasons for each case. This includes indicating when a contact is skipped due to being a duplicate, even if it contains additional or different information compared to a previously imported contact.

8. **Make error messages more specific and standardised**
* Current Issue: For most errors in formatting, the app shows a generic error message: Invalid command format! This may be hard for users to find out exactly what is wrong with their format.
* Example: When a user enters the command `add n/Amy ct/work t/colleague`, the following error message is displayed.<br>
  `Invalid command format!`<br>
  `add: Adds a person to the address book.`<br>
  `Parameters: n/NAME ct/CONTACT TYPE [h/TELEGRAM HANDLE] [p/PHONE] [e/EMAIL] [m/MODULE NAME] [r/REMARK] [t/TAG]...`<br>
  `Example: add ct/work n/John Doe h/@johndoe m/CS1101S p/98765432 e/johnd@example.com r/likes to eat chocolate t/friends t/owesMoney`<br>
  `Note: At least one field out of phone, email and telegram handle must be provided`<br>
  This response is both lengthy and lacks specific guidance. Users may have difficulty identifying the exact issue, such as the missing contact field (i.e.telegram handle, phone or email) requirement.
* Proposed Change: Introduce more specific error messages for different command format errors.

9. **Allow special characters in names**
* Current Issue: UniLink's current system may not fully recognize or handle names with special characters, potentially limiting the accurate representation of names that use hyphens, cultural identifiers, or symbols.
* Example: Users may need to input names like "Aubree-Rose," "Aishah d/o Rahman," or names with unique characters such as "John Smith!" but encounter limitations or rejections.
* Proposed Change: Enhance the `add` and `edit` commands to accommodate names with special characters, including hyphens, cultural identifiers (e.g., "d/o," "s/o"), and symbols. This update will allow users to input and maintain detailed and accurate records, ensuring all details are captured precisely.

10. **Change `find` to `findname` for clarity**
* Current Issue: The `find` command may cause confusion, as it only searches by names. This can be unclear because there are also separate `findtele` and `findtag` commands that search for Telegram handles and tags respectively.
* Example: `find alex`, this find command for names is inconsistent compared to findtele and findtag.
* Proposed Change: Change the name of the `find` command to `findname` to standardise with the other find commands.
