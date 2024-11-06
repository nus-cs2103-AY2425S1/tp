---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# Prudy Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

--------------------------------------------------------------------------------------------------------------------

## **Setting Up, Getting Started**

Refer to the guide [_Setting Up and Getting Started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-T14-1/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-T14-1/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete-client 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-T14-1/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `ClientListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-T14-1/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-T14-1/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Client` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-T14-1/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete-client 1")` API call as an example.

<puml src="diagrams/DeleteClientSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete-client 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteClientCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `PrudyParser` object which in turn creates a parser that matches the command (e.g., `DeleteClientCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteClientCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a client).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `PrudyParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddClientCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddClientCommand`) which the `PrudyParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddClientCommandParser`, `DeleteClientCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-T14-1/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores Prudy's data i.e., all `Client` objects (which are contained in a `UniqueClientList` object).
* stores the currently 'selected' `Client` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Client>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `Prudy`, which `Client` references. This allows `Prudy` to only require one `Tag` object per unique tag, instead of each `Client` needing their own `Tag` objects. (Note that the details of PolicySet has been omitted from this diagram as they are not relevant)<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-T14-1/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both Prudy's data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `PrudyStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

### Command Parsing and Execution Flow

The following UML activity diagram illustrates the command parsing and execution process within Prudy.
It depicts how commands are parsed, validated, and executed, along with the corresponding UI interactions for success or error messages.
<puml src="diagrams/FlowActivityDiagram.puml" width="550" />

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedPrudy`. It extends `Prudy` with an undo/redo history, stored internally as an `prudyStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedPrudy#commit()` — Saves the current Prudy state in its history.
* `VersionedPrudy#undo()` — Restores the previous Prudy state from its history.
* `VersionedPrudy#redo()` — Restores a previously undone Prudy state from its history.

These operations are exposed in the `Model` interface as `Model#commitPrudy()`, `Model#undoPrudy()` and `Model#redoPrudy()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedPrudy` will be initialized with the initial Prudy state, and the `currentStatePointer` pointing to that single Prudy state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete-client 5` command to delete the 5th client in Prudy. The `delete-client` command calls `Model#commitPrudy()`, causing the modified state of Prudy after the `delete-client 5` command executes to be saved in the `prudyStateList`, and the `currentStatePointer` is shifted to the newly inserted Prudy state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add-client n/David …​` to add a new client. The `add-client` command also calls `Model#commitPrudy()`, causing another modified Prudy state to be saved into the `prudyStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitPrudy()`, so the Prudy state will not be saved into the `prudyStateList`.

</box>

Step 4. The user now decides that adding the client was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoPrudy()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous Prudy state, and restores Prudy to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial Prudy state, then there are no previous Prudy states to restore. The `undo` command uses `Model#canUndoPrudy()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoPrudy()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores Prudy to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `prudyStateList.size() - 1`, pointing to the latest Prudy state, then there are no undone Prudy states to restore. The `redo` command uses `Model#canRedoPrudy()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list-client`. Commands that do not modify Prudy, such as `list-client`, will usually not call `Model#commitPrudy()`, `Model#undoPrudy()` or `Model#redoPrudy()`. Thus, the `prudyStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitPrudy()`. Since the `currentStatePointer` is not pointing at the end of the `prudyStateList`, all Prudy states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add-client n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves entire Prudy.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete-client`, just save the client being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, Logging, Testing, Configuration, Dev-Ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:
The target user is an insurance agent working with Prudential, responsible for managing a substantial portfolio of clients. This user has specific needs and preferences for effectively managing clients, policies, and claims in a high-volume environment.

Key attributes of the target user include:
* **Role and Responsibilities:** The user is a Prudential insurance agent who needs to efficiently oversee and update client records, including individual policies and claims
* **Client Management Requirements:** The user regularly manages a large number of clients and requires an organized and efficient system to track and access client information swiftly
* **Application Preference:** The user prefers desktop applications due to their stability and reliability in handling client data and other sensitive information in a local environment
* **Interaction Style:** As a proficient typist, the user prefers a command-line interface (CLI) for its speed and efficiency over traditional mouse-based interactions.
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: We firstly improve efficiency through easy client management. Agents can quickly retrieve key information such as a client's insurance policy. Next, also improve client's relationships. Ensures agents never miss a client policy renewal. Manage clients' pending claims effectively.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                        | I want to …​                                      | So that I can…​                                                                 |
|----------|--------------------------------|--------------------------------------------------|---------------------------------------------------------------------------------|
| `* * *`  | Insurance agent                | quickly retrieve a client’s insurance policy details | efficiently provide relevant information during meetings or calls               |
| `* * *`  | Responsible insurance agent    | receive timely reminders for clients' policies which are expiring soon | remind individual clients to renew their policies           |
| `* * *`  | Forgetful insurance agent      | track the status of my clients' claims           | keep track of pending claims that require my attention                          |
| `* * *`  | Insurance agent                | store all my client contact information in one place | easily access their details and communicate with them without searching across multiple platforms |
| `* * *`  | Insurance agent                | track clients' claims and their status    | ensure all client issues are addressed promptly and effectively                 |
| `* * *`  | Insurance agent                | Remove clients who are no longer insured under my policies | I can declutter Prudy. |
| `* * *`  | New user                       | see a list of available commands                 | have a brief idea of the app capabilities                                       |
| `* * *`  | Insurance agent                        | add new claims or resolve old ones                | I can organize my actionables. |
| `* *`    | Busy insurance agent           | have a powerful search function that allows me to quickly find clients by name, policy type, or claims | respond promptly to inquiries                                                   |
| `* *`    | Busy insurance agent           | receive notifications when a client’s claim has not been resolved after a long time | be reminded to prioritize actioning these claims                                 |
| `* *`    | Organized insurance agent      | have a visual dashboard that shows me a summary of my client portfolio at a glance, including policy types, claims, and follow-up tasks | plan my activities without having to navigate too much                           |
| `* *`    | Insurance agent                | Filter my client list by policy type, claim status | I can quickly create a targeted list for campaigns or follow-up actions.|
| `*`      | Eager insurance agent          | receive reminders for key client milestones (e.g., birthdays, policy anniversaries) | send personalized messages or offers, strengthening client relationships         |
| `*`      | Responsible insurance agent    | Track the status of any claims my clients have made | I can provide updates and assistance throughout the claim process. |
| `*`      | Insurance agent                | import and export client data                    | work with the data outside of the platform when necessary                       |
| `*`      | Forgetful insurance agent      | set reminders for client meetings                | never miss important meetings or calls                                          |
| `*`      | Insurance agent                | view detailed information about client referrals and their referees | know which of my clients are related and show my appreciation to clients with large referrals |
| `*`      | Time-efficient insurance agent | assign different priority levels to my clients based on factors like policy value or renewal date | prioritize my work and focus on the most important or time-sensitive client needs |



*{More to be added}*

### Use cases

<box type="info" seamless>
**Note:** For all use cases below, the **System** is `Prudy` and the **Actor** is the `user`.
</box>

<box type="info" seamless>
**Note:** For all use cases below, if the user enters an invalid input, or there is an error when executing the command, the use case simply ends.
</box>

**Use case: UC1 - List clients (Find client has a similar use case)**

**MSS**

1. User requests to list clients
1. Prudy shows a list of clients

   Use case ends.

**Use case: UC2 - Add a client**

**MSS**

1. User requests to add client
1. Prudy adds the client

   Use case ends.

**Use case: UC3 - Delete a client**

**MSS**

1. User <u>list clients (UC1)</u> to look for index of client
1. User requests to delete a specific client in the list
1. Prudy deletes the client

   Use case ends.

**Use case: UC4 - Add a policy to client**

**MSS**

1. User <u>list clients (UC1)</u> to look for index of client
1. User requests to add a policy to a specific client in the list
1. Prudy adds the policy under the client

   Use case ends.

**Use case: UC5 - Add a claim to a policy**

**MSS**

1. User <u>list clients (UC1)</u> to look for index of client
1. User requests to add a claim to a specific policy for a client in the list
1. Prudy adds the claim under the specified policy for the client

   Use case ends.

**Use case: UC6 - List claims for a policy**

**MSS**

1. User <u>list clients (UC1)</u> to look for index of client
1. User requests to list claims for a specific policy under the client
1. Prudy lists the claims under the specified policy

   Use case ends.

**Use case: UC7 - Resolve a claim**

1. User <u>list clients (UC1)</u> to look for index of client
1. User <u>list claims for a policy (UC6)</u>
1. User requests to edit a claim to mark its status as resolved
1. Prudy edits the claim under the specified policy for the client

   Use case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 clients without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e., not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Should be intuitive, agents can perform core functions with 30 minutes training
5.  The system should provide feedback for user inputs for basic commands (eg. add client, update client) within 1 second.
6.  The app should not exceed 500MB of memory.
7.  Should give clear actionable error messages that would guide the user in correcting invalid inputs
8.  Input data should have checks to prevent wrong entries.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Client**: A client who purchased or about to purchase a policy from the agent
* **Policy**: An insurance policy that contains the type, premium and coverage
* **Claim**: A request for payment based on the policy
* **Commands**: A text instruction that triggers a defined function in the app (e.g., add-client, list-policies).
* **Command Prefix**: A marker used in commands to specify the type of input (e.g., n/ for name, p/ for phone number).
* **Premium**: Amount of money paid for an insurance policy
* **Coverage**: Maximum amount an insurance company will pay under a policy.
* **Claim Status**: The current state of a claim, such as Pending, Approved, or Rejected.
* **Error Message**: Message displayed to the user providing guidance on how to correct the issue.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for Manual Testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

#### Initial Launch

1. **Step**: Download the jar file and copy it into an empty folder.
2. **Step**: Double-click the jar file to launch the application.
3. **Expected**: The GUI should display with a set of sample contacts. Note that the window size may need adjustment.

### Saving Window Preferences

1. **Step**: Resize the window to a comfortable size.
2. **Step**: Move the window to a different location on your screen.
3. **Step**: Close the window.
4. **Step**: Re-open the app by double-clicking the jar file.
5. **Expected**: The window should open in the last used size and location.

---

### Client Management

#### Deleting a Client

1. **Prerequisites**: Ensure there are multiple clients listed using `list-client`.
2. **Test Case**: `delete-client 1`
    - **Expected**: Deletes the first client in the list. The status message displays details of the deletion, and the timestamp updates.
3. **Test Case**: `delete-client 0`
    - **Expected**: No client is deleted. An error message is shown, and the status bar remains unchanged.
4. **Incorrect Commands**:
    - `delete-client` (no index specified)
    - `delete-client x` (where `x` is greater than the number of clients)
    - **Expected**: An error message for invalid input or index.

---

### Policy and Claim Management

#### Listing Policies

1. **Prerequisites**: Ensure `list-client` is used to verify that the client has policies.
2. **Test Case**: `list-policies 1`
    - **Expected**: Lists all policies for the first client. If there are no policies, an appropriate message is displayed.

---

### Claim Management

#### Listing Claims

1. **Prerequisites**: Ensure policies with claims are present by using `list-client` and `list-policies`.
2. **Test Case**: `list-claims 1 pt/health`
    - **Expected**: Lists all claims under the health policy for the first client. If no claims are found, an appropriate message is shown.
3. **Invalid Policy Type**: `list-claims 2 pt/lifee`
    - **Expected**: An error indicating an invalid policy type, not a generic format error.

#### Adding a Claim

1. **Prerequisites**: Ensure there is a valid policy for the client using `list-policies`.
2. **Test Case**: `add-claim 1 pt/health s/PENDING d/Medical Expenses`
    - **Expected**: Adds a new claim to the health policy. Success message is displayed.
3. **Invalid Inputs**: Test with incorrect claim statuses, policy types, or invalid claim descriptions.
    - **Expected**: Validation error messages are displayed.

---

### Data Persistence

#### Saving Data

1. **Step**: Perform various add/delete operations on clients or policies.
2. **Expected**: Data should save automatically and persist when the application is closed and reopened.

---

### Handling Missing/Corrupted Data Files

1. **Simulating Missing Data File**:
    - **Step**: Navigate to the app's data directory and remove the data file.
    - **Step**: Re-launch the app.
    - **Expected**: The app should load with default data and display a warning about missing data.

2. **Simulating Corrupted Data File**:
    - **Step**: Open the data file and corrupt the contents (e.g., remove part of the JSON structure).
    - **Step**: Re-launch the app.
    - **Expected**: The app should show an error message and initialize with default data.

---

### Error Handling and Edge Cases

1. **Extreme Input Values**:
    - **Test Case**: Enter long strings or special characters in client or claim fields.
    - **Expected**: Proper validation errors and handling without app crashes.
2. **Invalid Command Inputs**:
    - **Test Case**: Commands like `add-client` with missing parameters.
    - **Expected**: Informative error messages are displayed.

---

### Exploratory Testing Tips

- Try boundary values for indices and numeric inputs.
- Ensure the app remains responsive when handling multiple sequential commands.
- Check how the app handles rapid commands and actions in succession.

---
