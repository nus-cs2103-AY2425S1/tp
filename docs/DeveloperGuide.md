---
layout: page
title: Developer Guide
---

**Team**: F11-04
**Name**: EZSTATES

**User Target Profile**:
This product is for freelance real estate agents who have to manage numerous property transactions and client interactions. It caters to those who need a fast, efficient tool to organize all their client data, track deals, and wish to streamline their workflow through command-line operations.

**Value Proposition**:
EZSTATES provides freelance real estate agents quick access to client details, categorized by their property’s needs and interests through a user-friendly CLI, streamlining operations by enabling swift and intuitive command-line interactions. This simplifies their workflow, boosts efficiency, and enhances their client service.

* Table of Contents
{:toc}


## Acknowledgements

This project is adapted from the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org/)


## Getting started

### Setting up

In order to develop for EZSTATES you can follow the steps we have provided below:

Ensure that the project is set up locally:

1. Create a fork of the GitHub repository
2. Clone the fork

    ```bash
    git clone https://github.com/<your Github username>/tp.git
    ```
3. Change to the fork local directory

    ```bash
    cd tp/
    ```
4. Build the project

    ```bash
    ./gradlew build
    ```
5. Run the project

    ```bash
    ./gradlew run
    ```


The Gradle instructions provided are for macOS and Linux users. If you are using Windows, please refer to the [official documentation](https://docs.gradle.org/current/userguide/gradle_wrapper.html#sec:using_wrapper) on using the Gradle Wrapper  (i.e. `./gradlew`) on Windows.

## Development Milestones

### Milestone v1.1
**General Update**
- Add individual photos
- Update AboutUs.md

### Milestone v1.2
**Adding Basic Functionalities**
- Create Client Profile
- List
- Add Property
- Delete Client Profile
- Add Appointment
- Delete Appointment

### Milestone v1.3
**Adding More Functionalities**
- Create Listing 
- Show All Listings
- Delete Listing 

### Milestone v1.4

### Milestone v1.5
**Product Refinement**

## Design Overview

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-F11-4/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-F11-4/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
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
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.)

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-F11-4/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-F11-4/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete n/Bob")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

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

### Model component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-F11-4/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-F11-4/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.


## Features and Implementation

### Client Management
Sub features
### Appointment Management
Sub features
### Utility
Sub features

**BELOW THIS IS KINDA JUST FOR REFERENCE**


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

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

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

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_



## Documentation, logging, testing, configuration, dev-ops

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)


## Appendix: Requirements

### Product scope

**Target user profile**:

* free-lance real estate agents
* has to manage a large number of clients with varying details
* has to make multiple appointments with various clients
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: manage client contacts faster than a typical mouse/GUI driven app


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority  | As a …​                     | I want to …​                                                       | So that I can…​                                         |
|-----------|-----------------------------|--------------------------------------------------------------------|---------------------------------------------------------|
| `* * *`   | tech-savvy property agent   | have a place to store my client details                            | manage my large number of client details                |
| `* * *`   | tech-savvy property agent   | create a new client profile                                        | store new clients                                       |
| `* * *`   | tech-savvy property agent   | delete a client profile                                            | remove clients I no longer serve                        |
| `* * *`   | tech-savvy property agent   | search for a client by name                                        | quickly access the profile of the client of interest    |
| `* * *`   | tech-savvy property agent   | be able to add my client's phone number and email to their profile | know how to contact him/her                             |
| `* * *`   | tech-savvy property agent   | create appointments with my clients                                | manage my appointments with my clients                  |
| `* * *`   | tech-savvy property agent   | delete appointments                                                | remove appointments I have already cleared              |
| `* *`     | tech-savvy property agent   | assign a status to a client                                        | easily categorise clients by priority                   |
| `* *`     | tech-savvy property agent   | search by appointments                                             | pinpoint which client I am serving for that appointment |
| `* *`     | tech-savvy property agent   | be able to keep track of all my appointments                       | plan out my schedule efficiently                        |
| `* *`     | tech-savvy property agent   | be able to know what appointments I have for the day               | make immediate changes to my schedule                   |


*{More to be added}*

### Use cases

(For all use cases below, the **System** is `EZSTATES` and the **Actor** is the `User`, unless specified otherwise)


#### Use Case: Add Buyer

**MSS**:
1. User chooses to add a new buyer.
2. System requests buyer details (name, contact info, property interest, etc.).
3. User enters the required information.
4. System confirms the details and creates the profile.
5. System displays a success message.
    - Use case ends.

**Extensions**:
- **3a.** System detects missing or incorrect data:
    - **3a1.** System displays am error and requests valid inputs.
    - **3a2.** User enters the correct data.
    - Steps 3a1–3a2 are repeated until all data is valid.
    - Use case resumes from step 4.


#### Use Case: Add Seller

**MSS**:
1. User chooses to add a new seller profile.
2. System requests seller details (name, contact info, property interest, etc.).
3. User enters the required information.
4. System confirms the details and creates the profile.
5. System displays a success message.
    - Use case ends.

**Extensions**:
- **3a.** System detects missing or incorrect data:
    - **3a1.** System displays am error and requests valid inputs.
    - **3a2.** User enters the correct data.
    - Steps 3a1–3a2 are repeated until all data is valid.
    - Use case resumes from step 4.


#### Use Case: Edit Client Profile

**MSS**:
1. User selects an existing client profile to edit.
2. System displays the client’s current information.
3. User updates the desired fields.
4. System confirms the changes and updates the profile.
5. System displays a success message.
    - Use case ends.

**Extensions**:
- **3a.** User tries to edit a field with invalid data:
    - **3a1.** System displays an error and requests valid data.
    - **3a2.** User corrects the data.
    - Steps 3a1–3a2 repeat until all data is valid.
    - Use case resumes from step 4.


#### Use Case: Delete Client Profile

**MSS**:
1. User chooses to delete a client profile.
2. System asks for confirmation.
3. User confirms the deletion.
4. System removes the client profile from the database.
5. System displays a success message.
    - Use case ends.

**Extensions**:
- **2a.** User cancels the deletion:
    - **2a1.** System aborts the deletion process.
    - Use case ends.


#### Use Case: Add Appointment

**MSS**:
1. User chooses to schedule an appointment for a client.
2. System requests appointment details (date, time, location).
3. User enters the requested details.
4. System confirms the details and saves the appointment.
5. System displays a success message.
    - Use case ends.

**Extensions**:
- **3a.** System detects a scheduling conflict:
    - **3a1.** System notifies the user of the conflict.
    - **3a2.** User chooses a new date/time.
    - Use case resumes from step 3.


#### Use Case: Delete Appointment

**MSS**:
1. User selects an appointment to delete.
2. System requests confirmation.
3. User confirms deletion.
4. System removes the appointment from the schedule.
5. System displays a success message.
    - Use case ends.

**Extensions**:
- **2a.** User cancels the deletion:
    - **2a1.** System stops the deletion process.
    - Use case ends.


#### Use Case: List Clients

**MSS**:
1. User chooses to view a list of all clients.
2. System retrieves and displays the client list.
    - Use case ends.


#### Use Case: Find Client by Name

**MSS**:
1. User chooses to search for a client by name.
2. System requests the client’s name.
3. User enters the name.
4. System retrieves and displays matching client profiles.
    - Use case ends.

**Extensions**:
- **4a.** System finds no clients matching the entered name:
    - **4a1.** System displays a “No clients found” message.
    - Use case ends.


#### Use Case: List Appointments

**MSS**:
1. User chooses to view all appointments.
2. System retrieves and displays the list of appointments.
    - Use case ends.


#### Use Case: Add Listing

**MSS**:
1. User chooses to add a new property listing.
2. System requests details for the listing (name, price, area, address, region, etc.).
3. User enters the required information.
4. System confirms the details and creates the listing.
5. System displays a success message.
    - Use case ends.

**Extensions**:
- **3a.** System detects missing or incorrect data:
    - **3a1.** System prompts for the correct information.
    - **3a2.** User enters the correct data.
    - Steps 3a1–3a2 repeat until all data is valid.
    - Use case resumes from step 4.


#### Use Case: Show Listings

**MSS**:
1. User chooses to view all available listings.
2. System retrieves and displays the list of property listings.
    - Use case ends.


#### Use Case: Find Listings

**MSS**:
1. User chooses to search for listings by keyword.
2. System requests keywords for the search.
3. User enters the keyword(s).
4. System retrieves and displays matching listings.
    - Use case ends.

**Extensions**:
- **4a.** System finds no listings matching the entered keywords:
    - **4a1.** System displays a “No listings found” message.
    - Use case ends.


#### Use Case: Edit Listing

**MSS**:
1. User selects an existing listing to edit.
2. System displays the listing’s current details.
3. User updates the desired fields.
4. System confirms the changes and updates the listing.
5. System displays a success message.
    - Use case ends.

**Extensions**:
- **3a.** User tries to edit a field with invalid data:
    - **3a1.** System displays an error and requests valid data.
    - **3a2.** User corrects the data.
    - Steps 3a1–3a2 repeat until all data is valid.
    - Use case resumes from step 4.


#### Use Case: Add Buyers to Listing

**MSS**:
1. User chooses to add buyer(s) to an existing listing.
2. System requests the buyer index for the listing.
3. User enters the buyer index.
4. System adds the buyer(s) to the listing.
5. System displays a success message.
    - Use case ends.

**Extensions**:
- **3a.** System detects invalid buyer index:
    - **3a1.** System prompts for a valid index.
    - **3a2.** User enters a valid index.
    - Use case resumes from step 4.


#### Use Case: Remove Buyers from Listing

**MSS**:
1. User chooses to remove buyer(s) from a listing.
2. System requests the buyer index for the listing.
3. User enters the buyer index.
4. System removes the buyer(s) from the listing.
5. System displays a success message.
    - Use case ends.

**Extensions**:
- **3a.** System detects invalid buyer index:
    - **3a1.** System prompts for a valid index.
    - **3a2.** User enters a valid index.
    - Use case resumes from step 4.


#### Use Case: Delete Listing

**MSS**:
1. User selects a listing to delete.
2. System requests confirmation.
3. User confirms deletion.
4. System removes the listing.
5. System displays a success message.
    - Use case ends.

**Extensions**:
- **2a.** User cancels the deletion:
    - **2a1.** System stops the deletion process.
    - Use case ends.


#### Use Case: Clear Listings

**MSS**:
1. User chooses to clear all listings.
2. System requests confirmation.
3. User confirms the action.
4. System clears all listings from the system.
5. System displays a success message.
    - Use case ends.


#### Use Case: More Info

**MSS**:
1. User requests more information on a specific listing.
2. System requests the listing index.
3. User enters the index of the listing.
4. System displays detailed information for the listing.
    - Use case ends.


#### Use Case: Chat Window

**MSS**:
1. User chooses to open the chat window.
2. System displays the chat interface.
3. User can interact with the chatbot for assistance with commands and inquiries.
    - Use case ends.


#### Use Case: Help

**MSS**:
1. User requests help.
2. System displays general instructions for using the application.
    - Use case ends.


#### Use Case: Exit Application

**MSS**:
1. User chooses to exit the application.
2. System prompts for confirmation.
3. User confirms the exit.
4. System closes the application.
    - Use case ends.

**Extensions**:
- **2a.** User cancels the exit:
    - **2a1.** System returns to the previous screen.
    - Use case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The app should be intuitive and require no more than three user actions to perform any major task.
5.  The codebase should adhere to clear coding principles to allow easy updates and bug fixes.
6.  The app should ensure that all client and listing data is securely stored and handled, with data validation to prevent unauthorized access.
7.  The app should handle errors gracefully, providing clear error messages and suggestions for corrective action.
8.  The app should provide immediate feedback (within 200 ms) to user actions, confirming that commands are being processed.
9.  The app should log significant actions and errors for debugging purposes, with options to adjust log levels (e.g., error-only, debug, info).
10.  The codebase should support automated testing, with unit and integration tests to ensure reliability and early detection of issues.

## Appendix

### Appendix: Instructions for manual testing

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Command Summary


1. **Adding a Buyer Profile**  
   **Use**: `buyer n/John Doe p/91234567 e/johndoe@example.com`  
   **Expected output**: Buyer profile is added, and a success message is displayed.


2. **Adding a Seller Profile**  
   **Use**: `seller n/Jane Smith p/98765432 e/janesmith@example.com a/456 Oak Ave`  
   **Expected output**: Seller profile is added, and a success message is displayed.


3. **Editing a Client Profile**  
   **Use**: `editclient 1 n/Jane Doe p/98765432`  
   **Expected output**: Client profile is edited, and a success message is displayed.


4. **Deleting a Client Profile**  
   **Use**: `deleteclient 1`  
   **Expected output**: Client profile is deleted, and a success message is displayed.


5. **Adding a Listing**  
   **Use**: `listing n/Greenwood House pr/500000 ar/1200 add/456 Elm St reg/central sel/3 buy/2`  
   **Expected output**: Listing is added, and a success message is displayed.


6. **Editing a Listing**  
   **Use**: `d`  
   **Expected output**: Listing is edited, and a success message is displayed.


7. **Deleting a Listing**  
   **Use**: `deletelisting 1`  
   **Expected output**: Listing is deleted, and a success message is displayed.


8. **Adding Buyers to Listing**  
   **Use**: `addlistingbuyers 1 buy/2 buy/3`  
   **Expected output**: Buyers are added to the listing, and a success message is displayed.


9. **Removing Buyers from Listing**  
   **Use**: `removelistingbuyers 1 buy/2`  
   **Expected output**: Buyers are removed from the listing, and a success message is displayed.


10. **Listing All Clients**  
    **Use**: `showclients`  
    **Expected output**: All clients are displayed.


11. **Listing All Listings**  
    **Use**: `showlistings`  
    **Expected output**: All listings are displayed.


12. **Finding Clients by Name**  
    **Use**: `find David`  
    **Expected output**: All clients matching the specified name are displayed.


13. **Finding Listings by Keyword**  
    **Use**: `findlisting Greenwood`  
    **Expected output**: All listings matching the specified keyword are displayed.


14. **Getting More Information on a Listing**  
    **Use**: `moreinfo 1`  
    **Expected output**: Detailed information about the listing is displayed.


15. **Clearing All Listings**  
    **Use**: `clearlistings`  
    **Expected output**: All listings are cleared, and a success message is displayed.


16. **Clearing All Data**  
    **Use**: `clear`  
    **Expected output**: All data (clients, listings, etc.) is cleared, and a success message is displayed.


17. **Help**  
    **Use**: `help`  
    **Expected output**: General instructions for using the application are displayed.


18. **Opening the Chat Window**  
    **Use**: `chatbot`  
    **Expected output**: The chat window is opened, allowing interaction with the chatbot.


19. **Exiting the Application**  
    **Use**: `exit`  
    **Expected output**: The application is closed.


### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

### Future Enhancements

1. **Customizable Command Aliases**: Allow users to define their own aliases for commands, making it easier to personalize the command-line experience and speed up common tasks.

2. **AI-Driven Insights**: Introduce AI-driven analytics accessible through CLI commands to help agents identify property trends, set optimal pricing, and predict client needs. This would turn EZSTATES into a strategic assistant, offering data insights directly within the app.

3. **Command Auto-Completion**: Add an auto-completion feature that suggests commands and arguments as users type, reducing typing time and minimizing errors.

4. **Automated Follow-Up Reminders**: Implement a feature to set reminders for client follow-ups. Agents could schedule reminders directly within the CLI to stay on top of leads without needing separate reminders.

5. **Dual Role for Clients**: Allow a client to be designated as both a buyer and a seller. This would enable agents to manage clients with multiple roles within a single profile, simplifying interactions and reducing redundancy.

6. **Calendar Integration**: Provide a command to sync appointments with external calendar apps (e.g., Google Calendar), so agents can manage schedules within EZSTATES while keeping appointments visible across platforms.

7. **In-App Reporting Tools**: Develop simple CLI commands to generate reports on client activities, property listings, and market insights. Reports would be displayed within the CLI, offering agents a quick overview of key metrics without leaving the app.


### Appendix: Known Bugs

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

2. **If you minimize the Help Window** and then run the `help` command (or use the Help menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

3. **For the n/ prefix**, users are not able to put slashes in their names (e.g., Kumar S/O Navareen). A temporary workaround is to input `son of` or `so` or `s o` until this issue has been rectified.

4. **For the add/ prefix**, addresses are case-sensitive. As a result, `add/123 Clementi Ave` and `add/123 clementi ave` will be treated as distinct addresses, which may lead to unintentional duplicates.

5. **For the Edit Listing command**, users can modify listings to assign a buyer as the seller of a listing.

6. **For the Add Buyers to Listing command**, the Result Display displays an incorrect error message.

7. **For the MoreInfo command**, the `Output` for User Error #2 has a missing `parameters` line where the CLIENT_INDEX should be. Refer to the Special Comments section under More Info for more clarification.

8. **For the Chatbot**, the text input field appears in black, which does not contrast well with its gray background.
