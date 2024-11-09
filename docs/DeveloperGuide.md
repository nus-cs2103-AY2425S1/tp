---
layout: default.md
title: "Developer Guide"
pageNav: 3
---

# HiredFiredPro Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* The project has adapted an ongoing software project for a desktop application (called _AddressBook_) used for managing contact details.<br>
* It was created by created by the [SE-EDU initiative](https://se-education.org).
* The source code for the original project can be accessed [here](https://github.com/nus-cs2103-AY2425S1/tp).

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-W09-1/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-W09-1/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
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

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-W09-1/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-W09-1/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-W09-1/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

<div style="page-break-after: always;"></div>

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-W09-1/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

<div style="page-break-after: always;"></div>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `HiredFiredProParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `HiredFiredProParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `HiredFiredProParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-W09-1/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the hiredfiredpro data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `HiredFiredPro`, which `Person` references. This allows `HiredFiredPro` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-W09-1/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both HiredFiredPro data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `HiredFiredProStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.hiredfiredpro.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedHiredFiredPro`. It extends `HiredFiredPro` with an undo/redo history, stored internally as an `hiredFiredProStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedHiredFiredPro#commit()` — Saves the current hiredfiredpro state in its history.
* `VersionedHiredFiredPro#undo()` — Restores the previous hiredfiredpro state from its history.
* `VersionedHiredFiredPro#redo()` — Restores a previously undone hiredfiredpro state from its history.

These operations are exposed in the `Model` interface as `Model#commitHiredFiredPro()`, `Model#undoHiredFiredPro()` and `Model#redoHiredFiredPro()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedHiredFiredPro` will be initialized with the initial hiredfiredpro state, and the `currentStatePointer` pointing to that single hiredfiredpro state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the hiredfiredpro. The `delete` command calls `Model#commitHiredFiredPro()`, causing the modified state of the hiredfiredpro after the `delete 5` command executes to be saved in the `hiredFiredProStateList`, and the `currentStatePointer` is shifted to the newly inserted hiredfiredpro state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitHiredFiredPro()`, causing another modified hiredfiredpro state to be saved into the `hiredFiredProStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitHiredFiredPro()`, so the hiredfiredpro state will not be saved into the `hiredFiredProStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoHiredFiredPro()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous hiredfiredpro state, and restores the hiredfiredpro to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial HiredFiredPro state, then there are no previous HiredFiredPro states to restore. The `undo` command uses `Model#canUndoHiredFiredPro()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoHiredFiredPro()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the hiredfiredpro to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `hiredFiredProStateList.size() - 1`, pointing to the latest hiredfiredpro state, then there are no undone HiredFiredPro states to restore. The `redo` command uses `Model#canRedoHiredFiredPro()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the hiredfiredpro, such as `list`, will usually not call `Model#commitHiredFiredPro()`, `Model#undoHiredFiredPro()` or `Model#redoHiredFiredPro()`. Thus, the `hiredFiredProStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitHiredFiredPro()`. Since the `currentStatePointer` is not pointing at the end of the `hiredFiredProStateList`, all hiredfiredpro states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

<div style="page-break-after: always;"></div>

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire hiredfiredpro.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.


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

* has a need to manage a significant number of candidates across various job roles
* has a need to view and filter candidate lists quickly based on various categories
* requires tools to schedule interviews, assign interviewers and update the outcome of the interview
* requires tools to efficiently track candidate progress and make hiring decisions
* prefers desktop apps for recruitment tracking over web or mobile applications
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Provides streamlined access to candidate information allowing users to track their progress, 
compare candidates, and make informed hiring decisions with efficient CLI commands, faster than a typical 
mouse/GUI driven app.

<div style="page-break-after: always;"></div>

### User stories

Priorities: High (must have) - `Essential`, Medium (nice to have) - `Typical`, Low (unlikely to have) - `Novel`

| Priority  | As a …​        | I want to …​                                                     | So that I can…​                                                                       |
|-----------|----------------|------------------------------------------------------------------|---------------------------------------------------------------------------------------|
| Essential | Interviewer    | add candidates                                                   | keep track of current candidates                                                      |
| Essential | Interviewer    | delete candidates                                                | keep track of current candidates                                                      |
| Essential | Hiring Manager | view a list of candidates                                        | quickly assess who has applied for the position                                       |
| Essential | Hiring Manager | mark candidates as "hired" or "rejected"                         | finalize the hiring process                                                           |
| Essential | Hiring Manager | see the current status of a candidate (e.g. shortlisted, rejected) | track their progress                                                                  |
| Typical   | Interviewer    | see candidates’ programming languages                            | ensure that they have sufficient programming knowledge based on company needs         |
| Typical   | Interviewer    | assign an interview score to a specific candidate                | objectively evaluate their performance across multiple criteria                       |
| Typical   | Interviewer    | attach files (resumes, portfolios) to a candidate's profile      | have all relevant documents in one place                                              |
| Typical   | Hiring Manager | sort candidates according to alphabetical order                  | easily pick out the potential candidates                                              |
| Typical   | Hiring Manager | search for candidates based on keywords (skills, position etc.)  | quickly find the right profiles                                                       |
                                                   

## Use cases

### Use Case 1: Add Candidate to Shortlist
**Actor**: Hiring Manager  

**Main Success Scenario**:
1. Hiring Manager enters the command to add a candidate.
2. HiredFiredPro adds the candidate.

    Use case ends.


**Extensions**:
- 1a. HiredFiredPro detects an error in the entered data.
  - 1a1. HiredFiredPro requests for the correct data.
  - 1a2. Hiring Manager enters new data.
  - Steps 1a1-1a2 are repeated until the data entered are correct.
  - Use case resumes from step 2.

<div style="page-break-after: always;"></div>

---

### Use Case 2: Delete Candidate from Shortlist
**Actor**: Hiring Manager  

**Main Success Scenario**:
1. Hiring Manager enters the command to delete a candidate by specifying the candidate's index in the list.
2. HiredFiredPro deletes the candidate from the list.

    Use case ends.


**Extensions**:
- 1a. The list is empty 
  - 1a1. HiredFiredPro displays an error message
    Use case ends.

- 1b. An invalid index is entered: 
  - 1b1. HiredFiredPro displays an error message. 

    Use case ends.


---

### Use Case 3: View Candidate List
**Actor**: Hiring Manager  

**Main Success Scenario**:
1. Hiring Manager enters the command to list all candidates.
2. HiredFiredPro retrieves and displays the list of candidates in alphabetical order.

    Use case ends.


---

### Use Case 4: View Candidate Status
**Actor**: Hiring Manager  

**Main Success Scenario**:
1. Hiring Manager enters the command with the candidate's name and job title.
2. HiredFiredPro displays the status of the candidate.

    Use case ends.


**Extensions**:
- 1a. The specified candidate does not exist
  - 1a1. HiredFiredPro displays an error message.
  
    Use case ends.
- 1b. The job title or name is missing
  - 1b1. HiredFiredPro displays an error message requesting the missing parameter.
    
    Use case ends.


---

### Use Case 5: Mark Candidate Status as Hired or Rejected
**Actor**: Hiring Manager  

**Main Success Scenario**:
1. Hiring Manager enters the command with the candidate's name and job title.
2. HiredFiredPro changes the candidate's status to either "hired" or "rejected."

    Use case ends.


**Extensions**:
- 2a. The specified candidate does not exist
  - 2a1. HiredFiredPro displays an error message.

    Use case ends.

- 2b. The candidate is already marked with the specified status
  - 2b1. HiredFiredPro displays an error message.
    
    Use case ends.


---

### Use Case 6: Sort list of candidates in ascending/descending order
**Actor**: Hiring Manager

**Main Success Scenario**:
1. Hiring Manager enters the command with the either 'a' for ascending or 'd' for descending order.
2. HiredFiredPro sorts the list of candidates in the specified order.

   Use case ends.

**Extensions**:
- 1a. Hiring Manager does not input either 'a' or 'd'
    - 1a1. HiredFiredPro displays an error message.

      Use case ends.
- 1b. Hiring Manager inputs other characters other than 'a' or 'b' (eg 'sort x')
    - 1b1. HiredFiredPro displays an error message.

      Use case ends.

---

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Setup instructions should be clear and simple, allowing users to get the app running within 5 minutes without installations.
3. Should be able to hold up to 1000 candidates without a noticeable sluggishness in performance for typical usage.
4. Commands should be intuitive and easy to remember, minimizing the learning curve for new users.
5. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
6. Should respond to user input commands within 1 second for optimal usability.
7. Should provide clear error messages and feedback for invalid input, allowing easy debugging for non-technical users.
8. Sensitive information, like candidate emails and phone numbers, should be stored securely and not exposed in error logs or publicly accessible files.
9. The application interface should be adaptable to different screen sizes and resolutions, ensuring usability on both standard and high-definition displays.

<div style="page-break-after: always;"></div>

---

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Command-Line Interface (CLI)**: A text-based interface where users input typed commands to perform specific tasks
* **Graphical user interface (GUI)**: A graphical interface where users interact with graphical elements to perform specific tasks
* **Debugging**: The process of identifying and correcting errors or bugs to perform an intended function.

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

### Adding a candidate

1. Adding a candidate to HiredFiredPro

    1. Test case: `add n/Adib j/Developer p/12345678 e/example@example i/1`<br>
       Expected: New candidate will be added at the end of the list ie. if there are 6 people in the list, he will be 7<sup>th<sup>

    1. Test case: `add n/Adib p/12345678 e/example@example i/1`<br>
       Expected: Error details shown in the result panel.

    1. Test case: `add n/Adib j/Developer p/phone_number e/example@example i/100`, `...` (invalid parameters for certain fields)<br>
       Expected: Similar to previous.

    1. Other incorrect add commands to try: `add`, `add i/1`, `...`(missing one or more compulsory fields)<br>
       Expected: Similar to previous.

### Deleting a candidate

1. Deleting a candidate while all candidates are being shown

   1. Prerequisites: List all candidates using the `list` command. Multiple candidates in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No candidate is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.
1. Viewing a candidate while no candidates are being shown

   1. Prerequisites: List all candidates using the `find xxx` where xxx is a name not existing in current candidates list. Multiple candidates existing.

   1. Test case: `delete 1`<br>
      Expected: No candidate will be deleted. Error details shown in the result panel.

### Saving data

1. Dealing with missing data folder.

   1. Open the folder where HiredFiredPro.jar is located.

   1. Data folder containing json file does not exist.
 
   1. Upon making any changes to HiredFiredPro, data folder with json file will be created with updated information.

1. Deleting existing HiredFiredPro.json file in existing data folder.

   1. Open the folder where HiredFiredPro.jar is located. 
      
   1. Click into the data folder and delete the existing json file inside. There should only be one file in the data folder

   1. Upon launching HiredFiredPro, our sample data of 6 people will be shown. Changes made up to that point will be discarded.

1. Saving data after changes are made.

   1. Prerequisites: HiredFiredPro should be opened.

   1. Make any changes (e.g `delete 1`).

   1. Close the app.

   1. Open the HiredFiredPro.json file which would be in the same folder as your jar file. <br>
      
   1. All changes made are reflected in the json file.

### Viewing Candidates

   1. Viewing a candidate while all candidates are being shown

      1. Prerequisites: List all candidates using the `list` command. Multiple candidates in the list. 

      1. Test case: `view n/Adib j/Developer`<br>
         Expected: Candidate with their details will be shown on the display panel.

      1. Test case: `view n/Adib`, `...` (missing compulsory fields)<br>
         Expected: No candidate displayed on display panel. Error details shown in the result panel.

   1. Viewing a candidate while no candidates are being shown

       1. Prerequisites: List all candidates using the `find xxx` where xxx is a name not existing in current candidates list. Multiple candidates existing.

       1. Test case: `view n/Adib j/Developer`<br>
          Expected: No candidate displayed on display panel. Error details shown in the result panel.
