---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# UniVerse Developer Guide

<!-- * Table of Contents -->

<page-nav-print />

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## **Acknowledgements**

Generative AI tools like ChatGPT were heavily used for creating detailed Javadocs, commit messages, test creation, and occasional code refactoring throughout the development of UniVerse. These tools provided valuable assistance in streamlining documentation and improving code quality.

Additionally, we acknowledge the following resources and tools that contributed to the success of this project:

* **PlantUML**: Used for designing UML diagrams, including class and sequence diagrams, to visualize the architecture and interactions within UniVerse.
* **JavaFX**: Utilized for building the Graphical User Interface (GUI) components to ensure a user-friendly experience.
* **JUnit 5**: Leveraged for comprehensive unit and integration testing, helping us maintain code reliability and robustness.
* **Gradle**: Employed as the build automation tool for dependency management and project builds.
* **IntelliJ IDEA**: The primary Integrated Development Environment (IDE) used for writing and debugging the codebase.
* **MarkBind**: Used for generating and maintaining the project’s documentation website, ensuring an organized and accessible layout.
* **GitHub Actions**: Implemented for continuous integration and automated testing to catch potential issues early in the development cycle.

Special thanks to the **CS2103/T teaching team** for their structured guidance, feedback, and provision of relevant learning materials that helped shape this project.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-T17-1/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-T17-1/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

<div style="page-break-after: always;"></div>

**How the architecture components interact with each other**


The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

<div style="page-break-after: always;"></div>


### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-T17-1/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

<div style="page-break-after: always;"></div>

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-T17-1/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

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


In addition to the original AB3 features, the `Logic` component has been extended to support new commands such as adding interests, work experience, and finding contacts based on various criteria. The class diagram below showcases these new features integrated into the `Command` structure:

The diagram below shows the original AB3 features.
<puml src="diagrams/AB3Commands.puml" width="1000"/>


The diagram below shows the newly added features for UniVerse.
<puml src="diagrams/UniVerseNewCommands.puml" width="1000"/>

<br> 

**Explanation**:
- **AB3 Features**: These are the original commands provided by AddressBook Level 3, which include basic CRUD (Create, Read, Update, Delete) operations, help, and navigation commands.
- **New Features**: These extend the command capabilities in `UniVerse` to allow users to add specific details like interests and work experience, and search contacts based on university, major, interest, or work experience.

<div style="page-break-after: always;"></div>

### Model component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-T17-1/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />

The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

<div style="page-break-after: always;"></div>

### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-T17-1/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

<div style="page-break-after: always;"></div>

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

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
    * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
    * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_



--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>


## **Appendix: Requirements**

### Product scope

**Target user profile**: University students who:

- Want a central platform to manage and grow their professional and social networks, consolidating essential contact details like interests, academic backgrounds, and work experience.
- Prefer desktop applications and are comfortable with typing commands.
- Can type quickly and are familiar with CLI (Command Line Interface) applications, preferring keyboard interactions over mouse usage.

**Value proposition**: UniVerse provides university students with an all-in-one platform to manage and expand their social and professional connections. It allows students to easily build, organize, and maintain contacts, keeping track of both personal and professional information to facilitate networking and relationship-building.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                     | I want to …​                                                                                            | So that I can…​                                                            |
|----------|-----------------------------|---------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------|
| `* * *`  | university student          | add a new contact with basic details like name, university, major, birthday, work experience, interests | start building my professional network from school                         |
| `* * *`  | university student          | delete a contact from my network                                                                        | keep my contact list clean and up-to-date                                  |
| `* * *`  | university student          | search for contacts by their university                                                                 | quickly find peers from the same university                                |
| `* * *`  | university student          | search for contacts by their major                                                                      | connect with peers in the same academic field                              |
| `* * *`  | aspiring university student | add internship or work experience to a contact                                                          | network with people who have relevant industry experience                  |
| `* * *`  | aspiring university student | search for contacts by their internship or work experience                                              | network with people who have relevant industry experience                  |
| `* * *`  | lonely university student   | add interests and hobbies to a contact                                                                  | remember what we have in common                                            |
| `* * *`  | sociable university student | search for contacts by their interests                                                                  | connect with people with similar interests                                 |
| `* * *`  | university student          | edit the contact details of an existing contact                                                         | keep their information current as they progress in their studies or career |
| `* * *`  | university student          | update interests, work experience, university, or major of a contact                                    | reflect their latest accomplishments and interests                         |
| `* *`    | university student          | view a contact’s birthday                                                                               | stay informed of important dates and maintain personal connections         |

---
<div style="page-break-after: always;"></div>

### Use cases

(For all use cases below, the **System** is the `UniVerse` and the **Actor** is the `User`, unless specified otherwise)

<br>

#### **Use Case: Add a Contact**
**System**: UniVerse  
**Actor**: User  
**Use Case ID**: UC01 - Add a Contact

**MSS**
1. User requests to add a new contact.
2. UniVerse validates all fields provided.
3. UniVerse creates the contact and provides confirmation details including the added contact's information.
4. Use case ends.

**Extensions**
- **1a**: The Name/Email/Phone Number/Address/University/Major/Birthday/Work Experience/Interests/Tags of contact has an invalid format.
  - **1a1**: UniVerse displays an appropriate error message (e.g., for email: "Emails should be of the format local-part@domain...").
  - **1a2**: User rectifies the erroneous fields and re-submits the request.
  - **1a3**: Use case resumes at step 2.

<br>

#### **Use Case: Delete a Contact**
**System**: UniVerse  
**Actor**: User  
**Use Case ID**: UC02 - Delete a Contact

**MSS**
1. User requests to delete a contact by specifying an index.
2. UniVerse locates the contact at the given index and deletes it.
3. UniVerse displays confirmation showing the deleted contact's details.
4. Use case ends.

**Extensions**:
- **1a**: The contact list is empty.
  - **1a1**: System displays a message indicating no contacts are available.
  - **1a2**: Use case ends.
- **2a**: The specified index is invalid.
  - **2a1**: System shows an error message (e.g., "Invalid index provided").
  - **2a2**: Use case resumes at step 1.

<box type="warning" seamless>

**Caution**: There is no undo operation for deletions.
</box>

<br>


#### **Use Case: Search for Contacts by University**

**System**: UniVerse  
**Actor**: User  
**Use Case ID**: UC03 - Find Contacts by University

**MSS**:
1. User requests to search for contacts by a specific university.
2. UniVerse searches the current displayed contact list for matches.
3. UniVerse displays all contacts matching the entered university (e.g., `findu u/N` returns partial matches such as "NTU" and "NUS").
4. Use case ends.

**Extensions**:
- **1a**: User enters an improperly formatted `findu` command.
  - **1a1**: UniVerse shows an "Invalid command format" error.
  - **1a2**: User re-enters the command with corrected input.
  - **1a3**: Use case resumes at step 1.
- **3a**: No matches found.
  - **3a1**: UniVerse displays a "0 persons listed!" message.
  - **3a2**: Use case ends.

<box type="info" seamless>

**Notes**:
- The **UNIVERSITY** field is case-insensitive.
- Partial matches are supported, allowing users to find contacts with university names that contain the specified keyword.
</box>

<br>
  
#### **Use Case: Search for Contacts by Major**
**System**: UniVerse  
**Actor**: User  
**Use Case ID**: UC04 - Search for Contacts by Major

**MSS**
1. User requests to search for contacts by a specific major.
2. UniVerse searches the current displayed contact list for matches.
3. UniVerse displays all contacts matching the entered major (e.g., `findm m/Computer` returns partial matches such as "Computer Engineering" and "Computer Science").
4. Use case ends.

**Extensions**:
- **1a**: User enters an improperly formatted `findm` command.
  - **1a1**: UniVerse shows an "Invalid command format" error.
  - **1a2**: User re-enters the command with corrected input.
  - **1a3**: Use case resumes at step 1.
- **3a**: No matches found.
  - **3a1**: UniVerse displays a "0 persons listed!" message.
  - **3a2**: Use case ends.

<box type="info" seamless>

**Notes**:
- Partial matches are allowed. `findm m/comp` will return contacts studying `Computer Science` and `Computer Engineering`etc.
- The search is case-insensitive.
  </box>

<br>

#### **Use Case: Add Internship or Work Experience**
**System**: UniVerse  
**Actor**: User  
**Use Case ID**: UC05 - Add Internship/Work Experience to a Contact

**MSS**
1. User requests to add work experience to an existing contact by specifying an index and work details.
2. UniVerse validates the contact and checks the format of the work experience.
3. UniVerse adds the work experience (e.g., `addw in/1 w/Intern,Google,2024`).
4. UniVerse displays confirmation that the work experience has been added.
5. Use case ends.


**Extensions**:
- **2a**: The specified contact index is invalid.
  - **2a1**: UniVerse displays an error message.
  - **2a2**: Use case resumes at step 1.
- **3a**: Work experience format is incorrect (e.g., "Multiple words or incorrect capitalization").
  - **3a1**: UniVerse shows an "Invalid command format" error.
  - **3a2**: User rectifies input and re-enters details.
  - **3a3**: Use case resumes at step 2.

<box type="info" seamless>

**Notes**:
- Only one work experience can be stored for a contact.
</box>

<br>

#### **Use Case: Add Interests or Hobbies**
**System**: UniVerse  
**Actor**: User  
**Use Case ID**: UC06 - Add Interest/Hobby to a Contact

**MSS**:
1. User requests to add interests to a contact by specifying an index and interest(s).
2. UniVerse validates the contact and interests.
3. UniVerse updates the contact with the provided interests (e.g., `addi in/1 i/Swimming i/Reading`).
4. UniVerse displays confirmation of added interests.
5. Use case ends.

**Extensions**:
- **2a**: The contact does not exist.
  - **2a1**: UniVerse shows an error message.
  - **2a2**: Use case resumes at step 1.
- **3a**: Interest field is empty.
  - **3a1**: UniVerse shows "Interest should not be blank" error.
  - **3a2**: User re-enters valid interests.
  - **3a3**: Use case resumes at step 2.

<box type="info" seamless>

**Notes**:
- Multiple interests can be added in one command but only to one contact at a time.
</box>

<br>

#### **Use Case: Search for Contacts by Work Experience**  
**System**: UniVerse  
**Actor**: User  
**Use Case ID**: UC07 - Search for Contacts by Work Experience

**MSS**
1. User requests to search for contacts by a specific work experience by entering the required `COMPANY` and optional `ROLE` and/or `YEAR`.
2. UniVerse searches the current displayed contact list for matches.
3. UniVerse displays all contacts matching the criteria (e.g., `findw w/Google` finds all contacts with work experience at "Google").
4. Use case ends.

**Extensions**:
- **1a**: User enters an incomplete or improperly formatted `findw` command.
  - **1a1**: UniVerse shows an "Invalid command format" error.
  - **1a2**: User re-enters the command with corrected input.
  - **1a3**: Use case resumes at step 1.
- **2a**: No contacts match the specified work experience.
  - **2a1**: UniVerse displays a "0 persons listed!" message.
  - **2a2**: Use case ends.

<box type="info" seamless>

**Note**:
- **COMPANY** is required, while **ROLE** and **YEAR** are optional.
- The **ROLE** and **COMPANY** fields should be a single word, formatted with the first letter capitalized.
- The search does **not** support partial matching for **ROLE**, **COMPANY**, or **YEAR**; full input must match exactly for these fields.
</box>


<br>

#### **Use Case: Search for Contacts by Interest**
**System**: UniVerse  
**Actor**: User  
**Use Case ID**: UC08 - Search for Contacts by Interest

**MSS**
1. User requests to search for contacts by a specific interest.
2. UniVerse searches the current displayed contact list for matches.
3. UniVerse displays all contacts with the specified interest.
4. Use case ends.

**Extensions**:
- **1a**: User enters an improperly formatted `findi` command.
  - **1a1**: UniVerse shows an "Invalid command format" error.
  - **1a2**: User re-enters the command with corrected input.
  - **1a3**: Use case resumes at step 1.

- **2a**: No contacts match the specified interest.
  - **2a1**: UniVerse displays a "0 persons listed!" message.
  - **2a2**: Use case ends.

<box type="info" seamless>

**Notes**:
- Only single-word interests are supported.
- Partial matches and case insensitivity apply to interest searches.
  </box>
<br>

#### **Use Case: Edit a Contact**
**System**: UniVerse  
**Actor**: User  
**Use Case ID**: UC09 - Edit a Contact

**MSS**:
1. User requests to edit an existing contact by specifying an index and updated fields.
2. UniVerse validates the contact index and checks the format of the updated fields.
3. UniVerse applies the changes to the contact.
4. UniVerse displays confirmation that the contact has been updated with the new information.
5. Use case ends.

**Extensions**:
- **1a**: The specified contact index is invalid.
  - **1a1**: UniVerse displays an error message.
  - **1a2**: Use case resumes at step 1.
- **2a**: One or more fields have an invalid format (e.g., incorrect date format in `b/BIRTHDATE`).
  - **2a1**: UniVerse shows an "Invalid command format" error.
  - **2a2**: User re-enters valid fields and resumes the command.

<br>

#### **Use Case: View Help**
**System**: UniVerse  
**Actor**: User  
**Use Case ID**: UC10 - View Help

**MSS**:
1. User requests to view help information using the `help` command.
2. UniVerse displays a message explaining how to access help topics or opens the help window.
3. Use case ends.

**Extensions**:
- **1a**: User minimizes or closes the help window.
  - **1a1**: The help command will reopen or restore the help window.

<br>

#### **Use Case: Exit the Application**
**System**: UniVerse  
**Actor**: User  
**Use Case ID**: UC11 - Exit the Application

**MSS**:
1. User requests to exit the application by using the `exit` command.
2. UniVerse performs cleanup operations if necessary.
3. UniVerse closes the application window.
4. Use case ends.

<br>

#### **Use Case: List All Contacts**
**System**: UniVerse  
**Actor**: User  
**Use Case ID**: UC12 - List All Contacts

**MSS**:
1. User requests to list all contacts using the `list` command.
2. UniVerse retrieves all contacts from the stored data and displays them in the contact list.
3. Use case ends.

**Extensions**:
- **1a**: Contact list is empty.
  - **1a1**: UniVerse displays a message indicating that there are no contacts to display.


---
<div style="page-break-after: always;"></div>



### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The system should be easy to use for **university students**, even those with minimal experience in professional networking tools.
5.  Users should be able to achieve common tasks (e.g., **adding a contact, updating information, or deleting a contact**) through a **command interface** faster than using a mouse, provided they type at an **average typing speed**.
6.  Error messages should be clear and instructive, providing enough information for users to correct mistakes (e.g., improper data formatting or missing required fields).
7.  The system must provide **auto-save functionality** so that user data is never lost in case of an unexpected shutdown.

--- 

### Glossary

* **Alphanumeric**: Refers to a string consisting of both letters (A-Z, a-z) and numbers (0-9). It may not include special characters or symbols.

* **CLI (Command Line Interface)**: A text-based user interface where commands are input to perform specific functions in a program.

* **GUI (Graphical User Interface)**: A visual interface where users interact with the application using graphical elements such as buttons and icons.

* **Predicate**: A function or expression that evaluates to `true` or `false`, used in the application to filter data based on specified criteria.

* **JSON (JavaScript Object Notation)**: A lightweight data format used to store and exchange data, readable by both humans and machines.

* **API (Application Programming Interface)**: A set of protocols and tools for building and interacting with software applications.

* **Mainstream OS**: Includes widely used operating systems like Windows, Linux, Unix, and MacOS.

* **Partial Match**: A search capability that returns results matching part of the query rather than the exact full query.

* **Error Message**: A notification provided to users when input or commands do not meet the required format or conditions.

* **Auto-Save**: A feature that ensures data is automatically saved without user intervention to prevent data loss.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    2. Double-click the jar file. Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Commands to Test

This section includes commands specific to the additional features implemented in this app, along with some example test inputs. Copy-paste these inputs to verify command functionality.


#### 1. Adding Interests: `addi`

This command allows you to add one or more interests to an existing contact.

<box type="info" seamless>

**Note:** Only interests that were not part of the contact's interest list will be shown on the display message.
</box>

**Test case 1:** `addi in/1 i/Swimming i/Cycling`

***Expected:*** Adds "Swimming" and "Cycling" as interests to the contact at index 1. 


<br>

**Test case 2:** `addi in/2 i/Reading`

***Expected:*** Adds "Reading" as an interest to the contact at index 2.


<br>

**Test case 3:** `addi in/3 i/`

***Expected:*** Error message due to missing interest input.

<br>

#### 2. Adding Work Experience: `addw`

This command adds work experience to a contact, replacing any existing work experience if present.

**Test case 1:** `addw in/1 w/SoftwareEngineer,Google,2023`

***Expected:*** Adds the work experience "SoftwareEngineer,Google,2023" to the contact at index 1.

<br>

**Test case 2:** `addw in/2 w/DataScientist,Meta,2022`

***Expected:***  Adds "DataScientist,Meta,2022" to the contact at index 2.

<br>

**Test case 3:** `addw in/3 w/`

***Expected:*** Error message due to incomplete work experience details.

<br>


#### 3. Finding Contacts by Interest: `findi`

This command finds contacts by specific interest. The command supports searching for one interest only.  Interest is case-insensitive.

**Test case 1:** `findi i/Swimming`

***Expected:*** Lists all contacts with "Swimming" as their interests.

<br>

**Test case 2:** `findi i/Swimming i/Cycling`

***Expected:***  Error message due to more than one interests in input.

<br>

**Test case 3:** `findi i/UnknownInterest`

***Expected:*** Empty list or message indicating no matches found.

<br>

#### 4. Finding Contacts by Work Experience: `findw`

This command finds contacts by work experience. It requires a company name and allows optional role and year fields.

**Test case 1:** `findw w/Amazon`

***Expected:*** Lists all contacts who have worked at Amazon.

<br>

**Test case 2:** `findw w/Intern,Meta`

***Expected:***   Lists contacts who interned at Meta.

<br>

**Test case 3:** `findw w/Intern,Google,2023`

***Expected:*** Lists contacts who interned at Google in 2023.

<br>

**Test case 4:** `findw w/`

***Expected:*** Error message due to missing company name.

<br>

#### 5. Finding Contacts by Major: `findm`

This command finds contacts by major. It requires a major. Major is case-insensitive.

**Test case 1:** `findm m/Business`

***Expected:*** Lists all contacts who have major as Business.

<br>

**Test case 2:** `findm m/`

***Expected:*** Error message due to missing major name.

<br>

#### 6. Finding Contacts by University: `findu`

This command finds contacts by university. It requires a university. University is case insensitive.

**Test case 1:** `findu u/NUS`

***Expected:*** Lists all contacts who have university as NUS.

<br>

**Test case 2:** `findu u/`

***Expected:*** Error message due to missing university name.

---
<div style="page-break-after: always;"></div>

## **Appendix: Effort**

Our goal was to enhance AB3 by introducing new commands and improving functionality for better contact management, specifically tailored for university students.

### Key Efforts and Challenges

**1. Adding Work Experience and Interests (`addw` and `addi` commands)**:
- **Effort**: These commands were developed to allow users to add work experiences and interests to existing contacts. The `Command` classes were extended to handle new parameters while ensuring that the `Model` and `Storage` components were updated for data integrity and compatibility.
- **Challenges**:
    - Ensuring seamless integration with the `Logic` and `Parser` components.
    - Implementing robust validation and data handling mechanisms.
    - Adjusting the UI to reflect changes dynamically.
- **Outcome**: Successful integration with moderate complexity due to the restructuring required for data validation and UI updates.

**2. Enhanced Search Capabilities (`findu`, `findm`, `findi`, and `findw` commands)**:
- **Effort**: Implementing these commands expanded the app's ability to search for contacts based on university, major, interests, or work experience. This required creating new predicate classes and extending the `FindCommand` logic to handle various input types.
- **Challenges**:
    - Ensuring compatibility across all search fields.
    - Maintaining search efficiency while handling multiple parameters and criteria.
- **Outcome**: Enhanced functionality that allows users to search and filter contacts more effectively, with significant modifications needed to achieve this.

**3. Refactoring and Integration**:
- **Effort**: Most new commands and features leveraged the existing `Command` structure, but required significant modifications to ensure that new logic paths were integrated without disrupting current system functionality.
- **Challenges**:
    - Addressing edge cases and integrating unit tests to verify the reliability of new features.
    - Managing dependencies between various components to avoid coupling.
- **Outcome**: A cohesive set of features that improved user experience while maintaining system robustness.

**Achievements**:
- The project successfully extended the original AB3 with functionality focused on university students' needs, balancing ease of use and powerful data organization.
- Careful planning and rigorous testing ensured the new features did not introduce regressions or instability.

---

<div style="page-break-after: always;"></div>

## **Appendix: Planned Enhancements**

**Team size**: 5

---

### 1. Relax Formatting Restrictions and Support Multi-Word Input for Work Experience in `addw` Command

- **Current Behavior**:  
  The `addw` command (e.g., `addw in/1 w/Intern,Google,2024`) requires strict formatting. The role and company names must be single words, with the first letter capitalized, and there must be no spaces after commas.

- **Limitation**:  
  This strict formatting limits flexibility, as it prevents users from entering more realistic job titles or multi-word company names (e.g., `software engineer, Jane Street`).

- **Planned Enhancement**:  
  Allow spaces after commas, optional capitalization for the first word, and multi-word entries for both roles and companies, enabling entries like `software engineer, Jane Street, 2024`.

---

### 2. Allow Multiple Work Experiences per Contact

- **Current Behavior**:  
  Each contact can only have one work experience entry.

- **Limitation**:  
  This setup does not reflect real-life scenarios where individuals have multiple job experiences. It restricts users from creating a complete professional history for each contact.

- **Planned Enhancement**:  
  Enable multiple work experiences per contact, storing each as a separate entry within a list. This will allow users to manage a comprehensive job history for each contact.

---

### 3. Expanded Multi-Value Search Capability with Stricter Input Validation for `find` Commands

- **Current Behavior**:  
  The `find` commands (`findi`, `findu`, `findm`, and `findw`) exhibit inconsistencies in handling multiple values:
  - The `findi` command only allows users to specify a single interest at a time (e.g., `findi i/swimming`), limiting users' ability to search for contacts with multiple shared interests.
  - The `findu`, `findm`, and `findw` commands allow multiple inputs for the same parameter (e.g., `findu u/NUS u/SMU`), but currently, this results in a "0 persons listed!" message because the application does not support searches across multiple values for these fields.

- **Limitation**:
  - **Single Interest Restriction in `findi`**: Users cannot perform more refined searches by specifying multiple interests (e.g., finding contacts with both "swimming" and "reading" interests).
  - **Multi-Value Inconsistency for `findu`, `findm`, and `findw`**: Users may expect to retrieve contacts matching any of the specified values, but the current functionality does not support such multi-value searches. This can lead to confusion. For example:
    - `findu u/NUS u/SMU` might reasonably be expected to show contacts from either "NUS" or "SMU."
    - `findm m/Computer Science m/Accounting` could logically display contacts studying either "Computer Science" or "Accounting," which is particularly useful for double major programs.
    - `findw w/Google w/Facebook` may be expected to return contacts with work experience at either "Google" or "Facebook."

- **Planned Enhancement**:
  - **Enhance Input Validation**: Implement stricter validation to ensure that only one instance of each parameter (`i/`, `u/`, `m/`, `w/`) is accepted per command. If multiple instances are provided, an error message should guide the user to use only one value per parameter, until multi-value support is fully implemented.

  - **Extend Search Functionality for Multi-Value Support**:
    - **Multiple Interests in `findi`**: Enable `findi` to accept multiple interests (e.g., `findi i/swimming i/reading`). This would allow users to find contacts who have **all** specified interests, improving the specificity of searches.
    - **Multiple Universities in `findu`**: Allow `findu` to support multiple universities, enabling users to search across several institutions (e.g., `findu u/NUS u/SMU` to find contacts from both "NUS" and "SMU"). This feature could be especially valuable for students networking across multiple universities.
    - **Multiple Majors in `findm`**: Update `findm` to support multiple majors, useful for cases where students have double majors or where users wish to connect with contacts across various academic fields (e.g., `findm m/Computer Science m/Accounting`).
    - **Multiple Work Experiences in `findw`**: Extend `findw` to allow multiple companies in a single search, helping users find contacts with experience at any specified company (e.g., `findw w/Google w/Facebook` to find contacts who have worked at either company).

---

### 4. Support `m/` Substring in Names

- **Current Behavior**:  
  If a name contains the substring `m/`, it may be mistakenly interpreted as the `major` prefix, leading to parsing errors (e.g., `add n/John m/Doe`).

- **Limitation**:  
  This parsing error prevents users from adding names with `m/` within them.

- **Planned Enhancement**:  
  Update the parser to differentiate between `m/` used within names and `m/` as the major prefix. This will allow names with the substring `m/` to be entered without issues.

---

### 5. Enhanced Input Validation for Search Commands

- **Current Behavior**:  
  Search commands such as `findm` and `findu` currently do not enforce strict input validation. Invalid inputs (e.g., `findm m/###`) result in "No users listed," even if the input format is incorrect.

- **Limitation**:  
  Lack of strict validation may lead to misleading results and errors that are not user-friendly.

- **Planned Enhancement**:  
  Add strict validation for search commands, so that invalid input prompts a clear error message, improving feedback reliability.

---

### 6. Consistent Scope Selection for `find` Commands

- **Current Behavior**:  
  Some `find` commands operate on the entire contact list, while others search only within the currently displayed list.

- **Limitation**:  
  This inconsistency can confuse users who are unsure about which list is being searched.

- **Planned Enhancement**:  
  Introduce a scope option (e.g., `#e` for entire list, `#c` for current list) that allows users to specify the scope of the search, ensuring consistency and clarity in search behavior across commands.

---

### 7. Validate `birthday` Field to Prevent Future Dates

- **Current Behavior**:  
  Users can enter a future date in the `birthday` field (e.g., `b/13-12-2030`), and the command will be accepted.

- **Limitation**:  
  Accepting future dates for the `birthday` field can lead to inaccurate and unrealistic data entries.

- **Planned Enhancement**:  
  Implement validation to ensure that the `birthday` field only accepts past or present dates, preventing future dates from being entered and improving data accuracy.

---

### 8. Validate Year in Work Experience to Prevent Unrealistic Future Dates

- **Current Behavior**:  
  Users can set a work experience date far in the future (e.g., `addw in/1 w/Intern,Google,2035`), which will be accepted.

- **Limitation**:  
  Accepting unrealistic future dates can lead to inaccurate work history information.

- **Planned Enhancement**:  
  Add validation to restrict the `year` field in `work experience` to within 2 years of the current date. This will prevent unrealistic entries and ensure relevant and accurate data.

---

### 9. Limit Length of Interest Field to Improve UI Display

- **Current Behavior**:  
  Users can enter interests longer than 20 characters, causing the UI to cut off text if it exceeds display limits.

- **Limitation**:  
  Overly long interest strings may not display properly, leading to a poor user experience.

- **Planned Enhancement**:  
  Restrict the `interest` field to a maximum of 20 characters, ensuring all entries display clearly in the UI and improving readability.

---

### 10. Improved Error Messages for `findi`, `findu`, and `findm` Commands

- **Current Behavior**:  
  The error messages for `findi`, `findu`, and `findm` display the parameter label generically as `KEYWORD`, which lacks specificity.

  - **Example Error Messages**:
    - **findi**:
      ```plaintext
      Invalid command format! 
      findi: Finds all persons whose interests contain the specified keyword (case-insensitive) and displays them as a list with index numbers. 
      Parameters: i/KEYWORD 
      Example: findi i/reading
      ```
    - **findu**:
      ```plaintext
      Invalid command format! 
      findu: Finds all persons whose universities contain the specified keyword (case-insensitive) and displays them as a list. 
      Parameters: u/KEYWORD 
      Example: findu u/NUS
      ```
    - **findm**:
      ```plaintext
      Invalid command format! 
      findm: Finds all persons whose major or course contains the specified keyword (case-insensitive) and displays them as a list with index numbers. 
      Parameters: KEYWORD 
      Example: findm m/Computer Science
      ```

- **Limitation**:  
  The use of `KEYWORD` in parameter descriptions is ambiguous and does not clarify the specific input type required (e.g., `INTEREST` for `findi`, `UNIVERSITY` for `findu`, `MAJOR` for `findm`). This lack of clarity may lead to user confusion about what input is expected.

<div style="page-break-after: always;"></div>

- **Planned Enhancement**:  
  Update error messages to display specific parameter names, similar to the formatting used in the `add` command. This change will provide users with clearer guidance on the expected input for each command.

  - **Updated Error Messages**:
    - **findi**:
      ```plaintext
      Invalid command format! 
      findi: Finds all persons whose interests contain the specified keyword (case-insensitive) and displays them as a list with index numbers. 
      Parameters: i/INTEREST 
      Example: findi i/reading
      ```
    - **findu**:
      ```plaintext
      Invalid command format! 
      findu: Finds all persons whose universities contain the specified keyword (case-insensitive) and displays them as a list. 
      Parameters: u/UNIVERSITY 
      Example: findu u/NUS
      ```
    - **findm**:
      ```plaintext
      Invalid command format! 
      findm: Finds all persons whose major or course contains the specified keyword (case-insensitive) and displays them as a list with index numbers. 
      Parameters: m/MAJOR 
      Example: findm m/Computer Science
      ```
