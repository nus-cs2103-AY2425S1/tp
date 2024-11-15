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

We would like to acknowledge the following:

* Libraries used:
  * [JavaFX](https://openjfx.io/) - An open source client application platform for desktop, mobile and embedded systems built on Java
  * [Jackson](https://github.com/FasterXML/jackson) - A suite of data-processing tools for Java
  * [JUnit5](https://github.com/junit-team/junit5) - The programmer-friendly testing framework for Java and the JVM

* This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org/)

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

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: We are making use of the "facade" design pattern to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />


The sections below give more details of each component.

---

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

---

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-T14-1/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete-client 1")` API call as an example.

<puml src="diagrams/DeleteClientSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete-client 1` Command" />

<div style="border: 1px solid #e0e0e0; padding: 16px; border-radius: 8px; background-color: #D6EAF8; margin-bottom: 16px;">

🔔
**Note:** The lifeline for `DeleteClientCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.

</div>

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

---

### Model component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-T14-1/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores Prudy's data i.e., all `Client` objects (which are contained in a `UniqueClientList` object).
* stores the currently 'selected' `Client` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Client>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div style="border: 1px solid #e0e0e0; padding: 16px; border-radius: 8px; background-color: #D6EAF8; margin-bottom: 16px;">

🔔
**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `Prudy`, which `Client` references. This allows `Prudy` to only require one `Tag` object per unique tag, instead of each `Client` needing their own `Tag` objects. (Note that the details of PolicySet has been omitted from this diagram as they are not relevant)<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</div>

---

### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-T14-1/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both Prudy's data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `PrudyStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

---

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

**Step 1.** The user launches the application for the first time. The `VersionedPrudy` will be initialized with the initial Prudy state, and the `currentStatePointer` pointing to that single Prudy state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

**Step 2.** The user executes `delete-client 5` command to delete the 5th client in Prudy. The `delete-client` command calls `Model#commitPrudy()`, causing the modified state of Prudy after the `delete-client 5` command executes to be saved in the `prudyStateList`, and the `currentStatePointer` is shifted to the newly inserted Prudy state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

**Step 3.** The user executes `add-client n/David …​` to add a new client. The `add-client` command also calls `Model#commitPrudy()`, causing another modified Prudy state to be saved into the `prudyStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<div style="border: 1px solid #e0e0e0; padding: 16px; border-radius: 8px; background-color: #D6EAF8; margin-bottom: 16px;">

🔔
**Note:** If a command fails its execution, it will not call `Model#commitPrudy()`, so the Prudy state will not be saved into the `prudyStateList`.

</div>

**Step 4.** The user now decides that adding the client was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoPrudy()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous Prudy state, and restores Prudy to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<div style="border: 1px solid #e0e0e0; padding: 16px; border-radius: 8px; background-color: #D6EAF8; margin-bottom: 16px;">

🔔
**Note:** If the `currentStatePointer` is at index 0, pointing to the initial Prudy state, then there are no previous Prudy states to restore. The `undo` command uses `Model#canUndoPrudy()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<div style="border: 1px solid #e0e0e0; padding: 16px; border-radius: 8px; background-color: #D6EAF8; margin-bottom: 16px;">

🔔
**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoPrudy()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores Prudy to that state.

<div style="border: 1px solid #e0e0e0; padding: 16px; border-radius: 8px; background-color: #D6EAF8; margin-bottom: 16px;">

🔔
**Note:** If the `currentStatePointer` is at index `prudyStateList.size() - 1`, pointing to the latest Prudy state, then there are no undone Prudy states to restore. The `redo` command uses `Model#canRedoPrudy()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

**Step 5.** The user then decides to execute the command `list-client`. Commands that do not modify Prudy, such as `list-client`, will usually not call `Model#commitPrudy()`, `Model#undoPrudy()` or `Model#redoPrudy()`. Thus, the `prudyStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

**Step 6.** The user executes `clear`, which calls `Model#commitPrudy()`. Since the `currentStatePointer` is not pointing at the end of the `prudyStateList`, all Prudy states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add-client n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

---

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves entire Prudy.
  * Pros: Easy to implement. Each state is a complete snapshot of `Prudy`, making it simple to revert to previous states by just moving the `currentStatePointer`.
  * Cons: May lead to memory issues, especially if the `Prudy` state is large and changes frequently, as each modification requires storing a full snapshot.


* **Alternative 2:** Command-specific undo/redo (Individual command knows how to undo/redo by itself.)
  * Pros: Uses less memory, since each command only saves the minimum required data to revert the operation. For example, `delete-client` might only store the specific client that was deleted rather than a full snapshot of the state.
  * Cons: Adds complexity, as each command must have its own undo/redo implementation, making it necessary to ensure each command’s undo/redo logic is correctly implemented and tested.

**Aspect: User Experience and Usability:**

* **Consideration 1:** Implement a history panel showing recent changes or a list of actions that can be undone or redone.

* Pros: Provides users with greater transparency and control, allowing them to see the list of actions and potentially select a specific state to revert to.
* Cons: Requires additional UI and data management, adding complexity to the design and potential memory overhead to maintain descriptions for each action.

* **Consideration 2:** Display a feedback message in the main UI area after performing undo/redo, indicating the action that was reverted or reapplied (e.g., "Undo: deleted client 5").

* Pros: Provides immediate feedback without requiring extra navigation, allowing users to stay focused on the main UI area.
* Cons: Only shows the most recent undo/redo action, so it may be limited in providing historical context.


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

<div style="border: 1px solid #e0e0e0; padding: 16px; border-radius: 8px; background-color: #D6EAF8; margin-bottom: 16px;">

🔔
**Note:** Prudential insurance agents were chosen as the target users for Prudy because the development team has extensive experience working with Prudential agents in the past. This background allowed the team to design Prudy with a deep understanding of the agents' specific needs and daily challenges, ensuring the tool aligns closely with their workflow.

</div>

Key attributes of the target user include:
* **Role and Responsibilities:** The user is a Prudential insurance agent who needs to efficiently oversee and update client records, including individual policies and claims
* **Client Management Requirements:** The user regularly manages a large number of clients and requires an organized and efficient system to track and access client information swiftly
* **Application Preference:** The user prefers desktop applications due to their stability and reliability in handling client data and other sensitive information in a local environment
* **Interaction Style:** The user is able to type fast and prefers a command-line interface (CLI) for its speed and efficiency over traditional mouse-based interactions.


**Value proposition**: We firstly improve efficiency through easy client management. Agents can quickly retrieve key information such as a client's insurance policy. Next, also improve client's relationships. Ensures agents never miss a client policy renewal. Manage clients' pending claims effectively.

---

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                        | I want to …​                                                                                                                            | So that I can…​                                                                                   |
|----------|--------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------|
| `* * *`  | Insurance agent                | quickly retrieve a client’s insurance policy details                                                                                    | efficiently provide relevant information during meetings or calls                                 |
| `* * *`  | Insurance agent                | store all my client contact information in one place                                                                                    | easily access their details and communicate with them without searching across multiple platforms |
| `* * *`  | Insurance agent                | track the status of client's claims                                                                                                     | ensure all client issues are addressed promptly and effectively                                   |
| `* * *`  | Insurance agent                | remove clients who are no longer insured under my policies                                                                              | declutter Prudy                                                                                   |
| `* * *`  | New user                       | see a list of available commands                                                                                                        | have a brief idea of the app capabilities                                                         |
| `* * *`  | Insurance agent                | add new claims or resolve old ones                                                                                                      | organize my actionables.                                                                          |
| `* * *`  | Insurance agent                | see a list of policies held by a particular client                                                                                      | provide tailored service and information relevant to the client's portfolio                       |
| `* * *`  | Insurance agent                | delete policies that are outdated                                                                                                       | keep the records up to date and relevant                                                          |
| `* *`    | Busy insurance agent           | have a powerful search function that allows me to quickly find clients by name, policy type, or claims                                  | respond promptly to inquiries                                                                     |
| `* *`    | Organized insurance agent      | have a visual dashboard that shows me a summary of my client portfolio at a glance, including policy types, claims, and follow-up tasks | plan my activities without having to navigate too much                                            |
| `* *`    | Insurance agent                | filter my client list by policy type, claim status                                                                                      | quickly create a targeted list for campaigns or follow-up actions.                                |
| `* *`    | Insurance agent                | filter policies expiring within a certain time frame                                                                                    | prioritize outreach to clients whose policies are up for renewal soon                             |
| `* *`    | Insurance agent                | edit my clients' existing policies                                                                                                      | update policy information as needed to keep records accurate                                      |
| `* *`    | Insurance agent                | edit my client's existing claims                                                                                                        | update claim information as needed to keep records accurate                                       |
| `*`      | Responsible insurance agent    | track the status of any claims my clients have made                                                                                     | provide updates and assistance throughout the claim process.                                      |
| `*`      | Insurance agent                | import and export client data                                                                                                           | work with the data outside of the platform when necessary                                         |
| `*`      | Time-efficient insurance agent | assign different priority levels to my clients based on factors like policy value or renewal date                                       | prioritize my work and focus on the most important or time-sensitive client needs                 |

---

### Use cases

<div style="border: 1px solid #e0e0e0; padding: 16px; border-radius: 8px; background-color: #D6EAF8; margin-bottom: 16px;">

🔔
**Note:** For all use cases below, the System is <code>Prudy</code> and the Actor is the user.

</div>

**Use case: UC1 - List clients**

**MSS**

1. User requests to list clients.
1. Prudy shows a list of clients.

   Use case ends.

**Use case: UC2 - Add a client**

**MSS**

1. User requests to add client by entering the details (name, phone, email, address, and optional tags).
1. Prudy adds the client.

   Use case ends.

**Extensions**

- 1a. The name is invalid.
   - 1a1. Prudy shows an error message.

      Use case ends.

- 1b. The phone is invalid.
   - 1b1. Prudy shows an error message.

      Use case ends.

- 1c. The email is invalid.
   - 1b1. Prudy shows an error message.

      Use case ends.

- 1d. The address is invalid.
   - 1d1. Prudy shows an error message.

      Use case ends.

- 1e. The tags provided are invalid.
   - 1e1. Prudy shows an error message.

      Use case ends.

- 1f. The client already exists in Prudy.
   - 1f1. Prudy shows an error message.

      Use case ends.

**Use case: UC3 - Delete a client**

**MSS**

1. User <u>list clients (UC1)</u> to look for index of client.
1. User requests to delete a specific client in the list.
1. Prudy deletes the client.

   Use case ends.

**Extensions**

- 2a. The provided index is invalid.
   - 2a1. Prudy shows an error message.

      Use case ends.

**Use case: UC4 - List policies of a client**

**MSS**

1. User <u>list clients (UC1)</u> to look for index of client.
1. User request to list policies of client.
1. Prudy shows a list of the client's policies.

   Use case ends.

**Extensions**

- 2a. The provided index is invalid.
   - 2a1. Prudy shows an error message.

      Use case ends.

**Use case: UC5 - Add a policy to client**

**MSS**

1. User <u>list clients (UC1)</u> to look for index of client.
1. User requests to add a policy to a specific client in the list (optionally providing the premium amount, coverage amount, and expiry date).
1. Prudy adds the policy under the client.

   Use case ends.

**Extensions**

- 2a. The provided policy type is invalid.
   - 2a1. Prudy shows an error message.

      Use case ends.

- 2b. The provided premium amount is invalid.
   - 2b1. Prudy shows an error message.

      Use case ends.

- 2c. The provided coverage amount is invalid.
   - 2c1. Prudy shows an error message.

      Use case ends.

- 2d. The provided expiry date is invalid.
   - 2d1. Prudy shows an error message.

      Use case ends.

- 2e. Client already has a policy of the same policy type.
   - 2e1. Prudy shows an error message.

      Use case ends.

**Use case: UC6 - List claims for a client's policy**

**MSS**

1. User <u>list clients (UC1)</u> to look for index of client.
1. User request to list all claims of client's policy.
1. Prudy shows a list of claims under the specified client's policies.

   Use case ends.

**Extensions**

- 1a. User wants to list all client's policies.
   - 1a1. User <u>list policies of a client (UC4)</u>.

      Use case resumes at step 2.

- 2a. The provided index is invalid.
   - 2a1. Prudy shows an error message.

      Use case ends.

- 2b. The provided policy type is invalid.
   - 2b1. Prudy shows an error message.

      Use case ends.

- 2c. Client does not has the specified policy.
   - 2c1. Prudy shows an error message.

      Use case ends.

**Use case: UC7 - Add a claim to a policy**

**MSS**

1. User <u>list clients (UC1)</u> to look for index of client.
1. User requests to add a claim to a specific policy for a client in the list by providing the claim status and claim description.
1. Prudy adds the claim under the specified policy for the client.

   Use case ends.

**Extensions**

- 1a. User wants to list all client's policies.
   - 1a1. User <u>list policies of a client (UC4)</u>.

      Use case resumes at step 2.

- 2a. The provided index is invalid.
   - 2a1. Prudy shows an error message.

      Use case ends.

- 2b. The provided policy type is invalid.
   - 2b1. Prudy shows an error message.

      Use case ends.

- 2c. Client does not have the specified policy.
   - 2c1. Prudy shows an error message.

      Use case ends.

- 2d. The provided claim status is invalid.
   - 2d1. Prudy shows an error message.

      Use case ends.

- 2e. The provided claim description is invalid.
   - 2e1. Prudy shows an error message.

      Use case ends.

- 2f. The claim to be added is a duplicate.
   - 2f1. Prudy shows an error message.

      Use case ends.

**Use case: UC8 - Edit a claim**

**MSS**

1. User <u>list clients (UC1)</u> to look for index of client.
1. User requests to edit the claim's status or description or both.
1. Prudy edits the claim under the specified policy for the client.

   Use case ends.

**Extensions**

- 1a. User wants to list all client's policies.
   - 1a1. User <u>list policies of a client (UC4)</u>.

      Use case resumes at step 2.

- 1b. User wants to list all claim's of a policy.
   - 1b1. User <u>list claims for a client's policy (UC6)</u>.

      Use case resumes at step 3.

- 2b. The provided index is invalid.
   - 2b1. Prudy shows an error message.

      Use case ends.

- 2c. The provided policy type is invalid.
   - 2c1. Prudy shows an error message.

      Use case ends.

- 2d. Client does not have the specified policy.
   - 2d1. Prudy shows an error message.

      Use case ends.

- 2e. The provided claim index is invalid.
   - 2e1. Prudy shows an error message.

      Use case ends.

- 2f. The provided claim status is invalid.
   - 2f1. Prudy shows an error message.

      Use case ends.

- 2g. The provided claim description is invalid.
   - 2g1. Prudy shows an error message.

      Use case ends.

- 2h. The result of the command will lead to a duplicate claim.
   - 2h1. Prudy shows an error message.

      Use case ends.

**Use case: UC9 - List expiring policies**

**MSS**

1. User requests to list expiring policies, optionally indicating the number of days to expiry.
1. Prudy list all policies expiring in 30 days if not specified by user, or policies expiring in however many days as specified by user.

   Use case ends.

**Extensions**

- 1a. The number of days specified is invalid.
   - 1a1. Prudy shows an error message.

      Use case ends.

**Use case: UC10 - Filter clients**

**MSS**

1. User requests to filter clients by search criteria.
1. Prudy list all clients that match the search criteria.

   Use case ends.

**Extensions**

- 1a. The provided name is invalid.
   - 1a1. Prudy shows an error message.

      Use case ends.

- 1b. The provided phone is invalid.
   - 1b1. Prudy shows an error message.

      Use case ends.

- 1c. The provided email is invalid.
   - 1c1. Prudy shows an error message.

      Use case ends.

- 1d. The provided address is invalid.
   - 1d1. Prudy shows an error message.

      Use case ends.

- 1e. The provided policy type is invalid.
   - 1e1. Prudy shows an error message.

      Use case ends.

---

### Non-Functional Requirements

1. **Platform Compatibility**: Should work on any mainstream OS with Java 17 or above installed.

1. **Performance**: Should be able to hold up to 1000 clients without noticeable sluggishness in typical usage.

1. **Efficiency**: A user with average typing speed should be able to perform tasks faster using commands than using a mouse-based interface.

1. **Ease of Use**: Core functions should be intuitive enough for a new user to learn within 30 minutes.

1. **Response Time**: Basic commands (e.g., add client, update client) should provide feedback within 1 second.

1. **Memory Usage**: The app should not exceed 500MB of memory usage.

1. **Data Persistence**: All client data should be saved and remain intact between application sessions to prevent data loss.

1. **Reliability**: The application should handle typical user errors gracefully without crashing or requiring a restart.

1. **Documentation**: Basic user instructions and command descriptions should be available to help new users understand and use core features effectively.

1. **Backup**: Users should be able to easily save a copy of all data manually to prevent accidental loss.

---

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
* **Sequence Diagram**: A visual representation showing how different components of the app interact in a specific order during a process
* **Class Diagram**: A type of UML (Unified Modeling Language) diagram that visually represents the structure of the app by showing its classes, their attributes, methods, and relationships to one another.
* **Activity Diagram**: A UML diagram that represents workflows or processes in the app, illustrating the sequence of actions and decision points for a particular operation.
* **Facade Pattern**: A design pattern used to simplify complex system interactions by providing a unified interface that acts as a "facade" for multiple components, reducing dependencies between components.

__________________________________

## **Appendix: Planned Enhancements**

Team size: 5

1. **Change policy from FlowPane to Label in the UI:** Currently, when the window is widened, the next FlowPane container will be placed beside instead of below, 2 rows of policies to appear on the same row as shown below.
We plan to make it a label to solve the issue.
 
<img src="images/img.png" width="1000px">

2. **Include more policy types:** Currently there are only 3 policy types. We plan to include all the policies that Prudential offers in our app.
3. **Add feature to identify missing parameters:** Currently, if a user enters a command with missing parameters, it will tell the user that the command is invalid.
We plan to make Prudy inform the user which parameters they left out instead of throwing a general error message.
4. **Add feature to ensure emails contain a valid domain:** Currently Prudy only checks if email contains both local part and domain.
We plan to enhance the checks by checking if the domain is valid i.e. contains both domain name and top level domain.
5. **Enhance the visuals for policy in the UI:** We plan to enhance the presentation of information for policy and claims.
6. **Make emails case-insensitive:** Currently emails is case-sensitive which does not make sense as capital letters do not affect email addresses in any way. Therefore, we plan to make emails case-insensitive.
7. **Make claims case-insensitive:** Currently, claims are case-sensitive and therefore do not detect duplicates when descriptions differ only in letter casing (e.g., Accident Claim vs. accident claim). We plan to convert claims to lowercase before storing.
8. **Make client names case-insensitive:** Currently, client names are case-sensitive, which can lead to inconsistencies such as treating `John Tan` and `john Tan` as separate clients. We plan to make names case-insensitive by standardizing them to a consistent format (e.g., capitalizing the first letter of each word) upon entry. This change will prevent duplicate entries caused by variations in capitalization, ensuring a more consistent and reliable client database.
9. **Make Prudy check for all prefixes:** Currently Prudy only checks the prefix used in the command and does not detect wrong prefixes.
   For example, `add-client n/John Doe c/claim p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 t/friends t/owesMoney` will throw `Names should only contain alphanumeric characters and spaces, and it should not be blank`
   instead of recognizing that the wrong prefix is used. We plan to make Prudy check for all prefixes and throw an error if wrong prefix is detected.
10. **Make `find-client` handle multiple duplicate parameters:** Currently the `find-client` command does not check for duplicate prefixes. We plan to make Prudy throw an error when duplicate parameters are passed in.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for Manual Testing**

Given below are instructions to test the app manually.

<div style="border: 1px solid #e0e0e0; padding: 16px; border-radius: 8px; background-color: #D6EAF8; margin-bottom: 16px;">

🔔
**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

#### Initial Launch

1. Refer to the guide [_Setting Up and Getting Started_](SettingUp.md).
2. **Expected**: The GUI should display with a set of sample contacts. Note that the window size may need adjustment.

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
