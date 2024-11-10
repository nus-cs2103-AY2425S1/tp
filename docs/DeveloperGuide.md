---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* Utilized JavaFX for creating interactive and visual components such as charts (e.g., bar charts, pie charts).
* Use of ChatGPT and Co-pilot to improve code quality

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

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

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the javaFX UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

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
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

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

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

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

**Target user profile**: Tuition center administrative staff

* has a need to manage a significant number of students
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**:

* manage student contacts faster than a typical mouse/GUI driven app
* one-shot command focus for significantly quicker usage


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                              | I want to …​                                                                                | So that I can…​                                                |
|----------|--------------------------------------|---------------------------------------------------------------------------------------------|----------------------------------------------------------------|
| `* * *`  | Tuition Center Administrators        | keep different levels of access for contact information                                     | keep sensitive information not accessible to unauthorized users |
| `* * *`  | Tuition Center Administrators        | search for contacts based on multiple criteria (name, role, or ID)                          | find a specific person quickly without manually scrolling the list |
| `* * *`  | Tuition Center Administrators        | a user-friendly command-line interface with clear and concise command options               | quickly perform tasks without a usage guide                    |
| `* * *`  | Tuition Center Administrators        | manage, retrieve and update the contact information for teachers, students and parents      | smoothly communicate across all parties without any confusions or delays |
| `* * *`  | Tuition Center Administrators        | able to perform mutliple actions at once                                                    | boost efficiency by cutting down administrative time on repetitive tasks |
| `* * *`  | Tuition Center Administrators        | add note about individual students (e.g. learning preferences, special needs, etc)          | personalise learning experience                                |
| `* *`    | Tuition Center Administrators        | send automated payment notifications to parents                                             | ensure timely payments                                         |
| `* *`    | Tuition Center Administrators        | integrate a billing system that automatically calculates monthly fees and generate invoices | lower administrative burden by minimising manual billing tasks |
| `* *`    | Tuition Center Administrators        | log and track communication history with parents, teachers and students                     | ensure continuity in communication                             |




---
## Use cases

#### **Use Case UC01: Add Student**
**Actor**: Administrator

**Main Success Scenario (MSS)**:
1. Administrator types the command to add a new student with the required details in a single line.
2. EduTuTu validates the input details.
3. EduTuTu adds the new student to the system and logs the action.

Use case ends.

**Extensions**:

- **2a. Invalid or Missing Data**:
    - 2a1. EduTuTu logs an error message specifying the invalid fields.
    - Use case resumes from step 1.

- **2b. Duplicate Student Detected**:
    - 2b1. EduTuTu logs an error message.
    - Use case resumes from step 1.

---


#### **Use Case UC02: Delete Student**
**Actor**: Administrator

**Main Success Scenario (MSS)**:
1. Administrator types the command to delete a student using their unique index:
2. EduTuTu validates the index.
3. EduTuTu deletes the student and logs the details of the deleted student.

Use case ends.

**Extensions**:

- **2a. Invalid Index Entered**:
    - 2a1. EduTuTu logs an error message.
    - **Use case resumes from step 1.**

---

#### **Use Case UC03: List All Students**
**Actor**: Administrator

**Main Success Scenario (MSS)**:
1. Administrator types the command to list all students.
2. EduTuTu retrieves all student records and outputs the list in the terminal with unique indices.

Use case ends.

---

#### **Use Case UC04: Find Students**
**Actor**: Administrator

**Main Success Scenario (MSS)**:
1. Administrator types the command to search for students by any field.
2. EduTuTu validates the search criteria.
3. EduTuTu searches the system and outputs matching students with unique indices.

Use case ends.

**Extensions**:

- **2a. Invalid Search Criteria**:
    - 2a1.EduTuTu logs an error message specifying the invalid input.
    - **Use case resumes from step 1.**

---

#### **Use Case UC05: Mark Fees as Paid**
**Actor**: Administrator

**Main Success Scenario (MSS)**:
1. Administrator first searches for the student using the find command.
2. EduTuTu displays a list of students with unique indices.
3. Administrator types the command to mark a student's fees as paid, including the student index and date.
4. EduTuTu validates the student index and date format.
5. EduTuTu updates the student's record and logs the payment confirmation.

Use case ends.

**Extensions**:

- **3a. Invalid Index Entered**:
    - 3a1. EduTuTu logs an error message.
    - **Use case resumes from step 1.**

- **3b.  Invalid Date Format or Month**:
    - 3b1. EduTuTu logs an error message.
    - **Use case resumes from step 1.**

---


#### **Use Case UC06: Unmark Fees as Paid**
**Actor**: Administrator

**Main Success Scenario (MSS)**:
1. Administrator first searches for the student using the `find` command.
2. EduTuTu displays a list of students with unique indices.
3. Administrator types the command to unmark a student's fees as paid, including the student index and date.
4. EduTuTu validates the student index and month format.
5. EduTuTu updates the student’s record, removing the payment record for the specified month, and logs the removal confirmation.

Use case ends.

**Extensions**:

- **3a. Invalid Index Entered**:
    - 3a1. EduTuTu logs an error message.
    - **Use case resumes from step 1.**

- **3b. Invalid Date Format or Month**:
    - 3b1. EduTuTu logs an error message.
    - **Use case resumes from step 1.**

---

#### **Use Case UC07: Edit Student Record**
**Actor**: Administrator

**Main Success Scenario (MSS)**:
1. Administrator first searches for the student using the `find` command.
2. EduTuTu displays a list of students with unique indices.
3. Administrator types the command to edit a student's record, including the student index.
4. EduTuTu validates the student index.
5. Administrator edits the selected the field with new value.
6. EduTuTu updates the student’s record with the new information and logs a confirmation of the successful edit.

Use case ends.

**Extensions**:

- **3a. Invalid Index Entered**:
    - 3a1. EduTuTu logs an error message.
    - **Use case resumes from step 1.**

- **5a. Invalid Field Value Entered**:
    - 5a1. EduTuTu logs an error message.
    - **Use case resumes from step 1.**

---

#### **Use Case UC08: Display Class Pie Chart**
**Actor**: Administrator

**Main Success Scenario (MSS)**:
1. Administrator types the command `pie` to view the distribution of students across classes.
2. EduTuTu generates and displays a pie chart, representing the number of students per class, with each class labeled clearly.
3. Administrator reviews the chart for insights into class sizes.

Use case ends.

**Extensions**:

- **2a. No Students Enrolled in Classes**:
    - 2a1. EduTuTu logs a message indicating that no data is available for display.
    - **Use case ends.**

---

#### **Use Case UC09: Display Monthly Payment Bar Chart**
**Actor**: Administrator

**Main Success Scenario (MSS)**:
1. Administrator types the command `bar` to view the monthly payment data.
2. EduTuTu generates and displays a bar chart with the y-axis representing the number of students and the x-axis representing each month.
3. Administrator reviews the chart for insights into monthly payment patterns.

Use case ends.

**Extensions**:

- **2a. No Student Data Available**:
    - 2a1. EduTuTu logs a message indicating that no student data is available for display.
    - **Use case ends.**
  

---
### Non-Functional Requirements

---

**1. Performance Requirements**

- **Response Time**: The system should respond to any command within **1 second** under normal operating conditions.
- **Throughput**: Capable of processing **concurrent commands** from multiple administrators without significant delay.
- **Capacity**: Should handle up to **1,000 students** without noticeable sluggishness in performance for typical usage.

**2. Maintainability Requirements**

- **Code Quality**: The codebase should be **modular**, well-documented, and adhere to standard coding conventions to facilitate maintenance.
- **Documentation**: Provide **comprehensive technical documentation** for future developers and maintainers.
- **Automated Testing**: Implement **unit tests** and **integration tests** to ensure that new changes do not break existing functionality.

**3. Portability Requirements**

- **Cross-Platform Compatibility**: The application must run on any mainstream operating system (**Windows, macOS, Linux**) with **Java 17** or above installed.
- **Minimal Dependencies**: Avoid platform-specific dependencies to ensure **ease of deployment** across different environments.

**4. Ethical Requirements**

- **Non-Discrimination**: The system should be designed to avoid biases, especially in features like **sorting** or **filtering**.
- **Transparency**: Actions performed by the system should be **transparent** to users, avoiding hidden processes that could cause confusion.

---

This version uses clear formatting with bullet points for better readability and structure.
### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Tuition Center Administrator**: The person responsible for overseeing the operations of the tuition center, managing contacts, scheduling, and communication with students, parents, and teachers.
* **Data Migration**: The process of transferring data from one system to another. This feature ensures that contacts, communication history, and other data can be shared across different platforms or stakeholders.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

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


### Adding a person

1. Adding a person with all fields

   1. Prerequisites: No persons in the list.

   1. Test case: `add n/Mario St p/92645273 a/222 greenwood ave e/ben@yahoo.com f/200 c/CS2105`<br>
      Expected: A person is added with all fields shown in the list. Details of the person added will be shown in the status message. 
   2. Test case: `add n/Mario  St p/92645273 a/222 greenwood ave e/ben@yahoo.com f/200 c/CS2106`<br>
      Expected: No person added. Error details shown in the status message. Status bar remains the same.
   3. Test case: `add n/Alice d/o Sally p/92645273 a/222 greenwood ave e/ben@yahoo.com f/200 c/CS2107`<br>
      Expected: A person is added with all fields shown in the list. Details of the person added will be shown in the status message.

### Editing a person

1. Editing a person with all fields

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `edit 1 c/CS2106`<br>
      Expected: The first contact is updated with the new course code. Details of the updated contact shown in the status message.
   2. Test case: `edit 0 c/CS2106`<br>
      Expected: No person is updated. Error details shown in the status message. Status bar remains the same.
   3. Test case: `edit 1 f/200`<br>
      Expected: The second contact is updated with the new fee. Details of the updated contact shown in the status message.
   4. Test case: `edit 2 f/200 c/CS2106`<br>
      Expected: The second contact is updated with the new fee and course code. Details of the updated contact shown in the status message.
   5. Other incorrect edit commands to try: `edit`, `edit x`, `edit x f/200` (where x is larger than the list size)<br>
      Expected: Similar to 2nd test case above.

### Finding a person

1. Finding a person by name

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `find n/Mario`<br>
      Expected: The person with the name Mario is shown in the list. The person card in the UI is highlighted.
   2. Test case: `find n/Alice`<br>
      Expected: The person with the name Alice is shown in the list. The person card in the UI is highlighted.
   3. Test case: `find n/`
      Expected: Error message shown in the status bar. 

2. Finding a person by ClassID
   
   1. Pre-requisites: List all persons using the `list` command. Multiple persons in the list.
   
   1. Test case: `find c/CS2105`<br>
      Expected: The person with the class ID CS2105 is shown in the list. 
   2. Test case: `find c/` <br>
      Expected: Error message shown in the status bar. 

3. Finding a person by name and ClassID
   1. Prerequisites: List all person using the `list` command. Multiple persons in the list.
    
   1. Test case: `find n/Mario c/CS2105`<br>
     Expected: The person with the name Mario and class ID CS2105 is shown in the list. 
   2. Test case: `find n/Alice c/`<br>
     Expected: Error message shown in status bar.
   3. Test case: `find n/ c/CS2105`<br>
     Expected: Error message showin in status bad.
   4. Test case: `find n/ c/`<br>
     Expected: Error message shown in the status bar. 


### Displaying a pie chart of the number of students in each class

1. Displaying a pie chart

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `pie`<br>
      Expected: A pie chart is displayed showing the number of students in each class. The pie chart is displayed in a new window.
   
   2. Test case: `bar` with multiple months but varying numbers of students  
      Expected: A bar chart is displayed with multiple bars, each representing a different month. The bars vary in height according to the number of students who paid in each month.

      
2. Displaying a pie chart with data

    1. Prerequisites: List all students in the address book, ensuring there are students in multiple classes.

    1. Test case: `pie`  
       Expected: A pie chart is displayed, showing the distribution of students across different classes. Each class is represented as a segment, with the segment size proportional to the number of students in that class. The chart is displayed in a new window.

   
### Displaying a bar chart of the distribution of students against the number of months paid

1. Displaying a bar chart

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `bar`<br>
      Expected: A bar chart is displayed showing the distribution of students against the number of months paid. The bar chart is displayed in a new window.
   
2. Displaying a bar chart error
    
  1. Prerequisites: Empty list
        
  1. Test case: `bar`<br>
      Expected: Error message shown in the status bar. No bar chart displayed.

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.



### Marking a Payment Date

1. Marking a payment date

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `markpaid 1 m/2024-12`<br>
      Expected: The first person is marked as paid for 2024-12. Details of the updated contact shown in the status message.
   1. Test case: `markpaid 0 m/2023-10`<br>
      Expected: No person is updated. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect mark as paid commands to try: `markpaid`, `markpaid x`, `markpaid x m/1` (where x is larger than the list size)<br>
      Expected: Similar to previous.
   2. Other incorrect date format to try: `markpaid 1 m/2024-13`, `markpaid 1 m/13`, `markpaid 1 m/abc`<br>
      Expected: Similar to previous.

### Unmarking MonthsPaid for a Specific Month

1. Unmarking a Month as Paid

    1. **Prerequisites**: Ensure there is a student record with a marked payment for the specified month. Use the `find` command to retrieve the student’s index.

    2. **Test Case**: `unmarkpaid 1 m/2020-10`
        - **Expected**: The payment record for the specified month is removed from the student’s record. A confirmation message logs the successful update, and the student's payment history no longer includes that month.

    3. **Test Case**: `unmarkpaid 1 m/2020-13`
        - **Expected**: An error message is logged, indicating an invalid date format. No changes are made to the student’s record, and the use case restarts from the search step.

    4. **Test Case**: `unmarkpaid -10 m/2020-10`
        - **Expected**: An error message is logged, indicating an invalid student index. No changes are made to any student records, and the use case restarts from the search step.
       
### Viewing Command History

1. Viewing command history

   1. Prerequisites: Multiple commands have been executed.

   1. Test case: up arrow key<br>
      Expected: A previous command is shown in the status message.

### Viewing Student Details

1. Viewing student details

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `info 1` <br>
      Expected: The first person's details are shown in the status message. The person card in the UI is highlighted.
   1. Test case: `info 0` <br>
      Expected: No person is viewed. Error details shown in the status message. Status bar remains the same.
   1. Other incorrect view commands to try: `info`, `info x` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Clearing all entries

1. Clearing all entries while all entries are being shown

    1. Prerequisites: List all entries using the `list` command. Ensure there is at least one entry on the list.

    1. Test case: `clear`  
       Expected: All entries are removed from the address book. A message "Address book has been cleared!" is displayed in the command box. UI updates to show an empty address book.

    1. Test case: `clear` on an already empty address book  
       Expected: No change, as there are no entries to delete. Message "Address book has been cleared!" is displayed again. Status bar remains the same.

    1. Other clear commands to try: `clear x`, `clear 123`, `clear all`  
       Expected: Address book will ignore parameters after the "clear" keyword, hence the address book will still be cleared.

### Undoing and Redoing changes

1. Undoing the most recent change

    1. Prerequisites: Perform a command that modifies the address book (e.g., `add`, `delete`, `edit`).

    1. Test case: `undo`  
       Expected: The most recent change is reversed. The address book reverts to the state before the last modification. A message indicating the undo action is displayed in the command box. Status bar timestamp is updated.

    1. Test case: `undo` with no prior changes  
       Expected: No change, as there are no actions to undo. A message "No Command to undo" is shown in the command box. 

1. Redoing the most recent undone change

    1. Prerequisites: Perform an `undo` action to revert a change.

    1. Test case: `redo`  
       Expected: The most recently undone change is reapplied. The address book reflects the state as if the original command was executed again. A message indicating the redo action is displayed in the command box. Status bar timestamp is updated.

    1. Test case: `redo` with no prior `undo`  
       Expected: No change, as there are no actions to redo. An error message is shown in the command box "No command to redo".

    1. Other incorrect undo/redo commands to try: `undo x`, `redo x` (where `x` is any additional parameter)  
       Expected: Error message indicating an unrecognized command format. Address book remains unaffected.

### Exiting the Program

1. Exiting the program using the `exit` command

    1. Prerequisites: Ensure the program is running with the address book open.

    1. Test case: `exit`  
       Expected: The program closes immediately. Data written to EduTuTu should be saved. No further interaction is possible as the program window closes.

1. Exiting the program using the GUI

    1. Prerequisites: Ensure the program is running with the address book open.

    1. Test case: Click `File` > `Exit` in the GUI toolbar  
       Expected: The program closes immediately. Similar to the `exit` command, data written to EduTuTu should be saved.

    1. Other exit commands to try: `exit now`, `quit`, `close`  
       Expected: Program will ignore arguments after the exit command and close immediately.


### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_



## Appendix: Effort

This project, EduTuTu, required a considerable amount of effort to design and implement due to its functionality and the combination of both Command Line Interface (CLI) and Graphical User Interface (GUI) components. Unlike simpler applications like AB3, which deals with only one entity type, EduTuTu manages multiple entities such as students, payments, and classes. Each entity has unique properties and relationships, adding complexity to the data model, command handling, and user interface design.

### Difficulty Level and Challenges
The development of EduTuTu presented several key challenges:
- **Multi-Entity Management:** Managing multiple types of entities (students, payments, classes) required a flexible data structure and sophisticated command parsing to handle diverse commands like `add`, `markpaid`, and `pie`.
- **Data Visualization:** Creating dynamic visualizations (pie and bar charts) to track class distributions and monthly payments required integration with a charting library and customization to suit EduTuTu’s specific data needs.
- **Error Handling and User Experience:** Ensuring robust error handling and clear user feedback was essential, particularly for commands related to data visualization and file manipulation (e.g., handling missing or corrupted files).

### Effort Required
Significant effort went into:
- **Designing a Scalable Data Model:** We created a model that supports various entity types, allowing for easy future expansion.
- **Extensive Testing:** Due to the multi-entity structure and data visualization, thorough testing was necessary to handle a wide range of user inputs and edge cases, particularly for commands such as `clear`, `undo`, and `redo`.
- **User Interface Improvements:** Balancing the CLI with GUI elements required additional effort to ensure that commands were intuitive and visualizations were accessible, providing a seamless user experience.

### Reuse of Libraries and Components
To streamline development, we leveraged certain libraries and reused components where feasible:
- **Charting Library for Visualizations:** The `javaFX` library was used to implement the `pie` and `bar` chart commands. This allowed us to focus on integrating visualization rather than building charting functionality from scratch. Our work on adapting `javaFX` to fit EduTuTu’s data structure is encapsulated in the `ChartAdapter.java` class.
- **Command Framework from AB3:** We adapted AB3’s command framework to accommodate EduTuTu’s expanded command set, allowing us to save development time while maintaining a consistent structure. Additional commands such as `markpaid` and `info` were added to extend functionality for the specific needs of a tuition center.

### Achievements
Despite the complexity, we achieved several milestones:
- Designed a flexible and scalable multi-entity data model that can be expanded easily for future needs.
- Successfully implemented user-friendly data visualization commands (`pie`, `bar`), providing tuition center administrators with valuable insights at a glance.
- Developed a robust system for error handling and user feedback, enhancing usability and reliability.

In summary, EduTuTu required significant effort due to its multi-entity structure, visualizations, and user interface improvements. By reusing existing libraries and frameworks strategically, we maintained high functionality and usability while optimizing development time.

## Appendix: Planned Enhancement

**Team size:** 5  
**Total planned enhancements:** 10

This section lists planned enhancements to address known feature flaws. These enhancements will be implemented after PE-D testing is completed and feedback is received.


1. **Add Confirmation for ‘Clear’ Command**  
   **Current Issue:** The `clear` command deletes all entries immediately without a confirmation prompt, which can lead to accidental data loss.  
   **Planned Enhancement:** Add a confirmation dialog that appears when the `clear` command is entered. Users must confirm the action by typing “yes” to proceed with clearing all data. Example prompt: "Are you sure you want to clear all entries? Type 'yes' to confirm."

   
2. **Make 'Undo' and 'Redo' Command Limit Clear**  
   **Current Issue:** Users are unaware of the limit to the `undo` and `redo` command history, leading to potential confusion.  
   **Planned Enhancement:** Display a warning when the maximum undo/redo history has been reached. For example, "No further undo actions are available."


3. **Emoji Handling in Input Fields**  
   **Current Issue:** Input fields currently do not handle emojis correctly due to a regex limitation, which may cause unexpected behavior.  
   **Planned Enhancement:** Update the regex used for validation to allow emojis in names, addresses, and other text fields, or provide an informative error message if emojis are not supported.


4. **Display List Sorting Options**  
   **Current Issue:** The `list` command displays entries in a default order, with no options for sorting.  
   **Planned Enhancement:** Add sorting options to the `list` command, allowing users to sort by name, class, or payment status. For example, `list sortby/name` or `list sortby/class`.


5. **Improved Multi-Monitor Support**  
   **Current Issue:** When switching between multiple monitors, the application may open off-screen if moved to a secondary screen and later switched back to a single-monitor setup.  
   **Planned Enhancement:** Implement better positioning handling to ensure the application opens within the primary screen’s bounds if multiple monitors are disconnected.


6. **Allow Multiple Class Allocations for a Single Student**  
   **Current Issue:** Currently, there are restrictions preventing a student from being assigned to more than one class, which can cause issues in managing class rosters.  
   **Planned Enhancement:** Remove restrictions each student to a single class.


7. **Enable Multi-Word Search for Accurate Matching**  
   **Current Issue:** Currently, the find command interprets multi-word entries with spaces (e.g., names or addresses) as separate search terms. For example, searching `n/Kim Woo Bin` returns any entries that match "Kim," "Woo," or "Bin" individually rather than finding the exact phrase "Kim Woo Bin." Similarly, searching for an address like "222 Greenwood Ave" matches "222," "Greenwood," and "Ave" separately, which could yield inaccurate results.  
   **Planned Enhancement:** Modify the find command to recognize multi-word search terms as complete phrases, enabling accurate matching for entries that include spaces. This enhancement will help avoid potential edge cases due to whitespace handling.


8. **Enable distinction between office and hp numbers**  
   **Current Issue:** Currently, each student in EduTuTu can only store up to one phone number.  
   **Planned Enhancement:** Modify EduTuTu to allow storage of multiple phone numbers (eg., Office Hp and Personal Phone Numbers)


9. **Enable handling of larger fees**  
   **Current Issue:** Currently, the fees field only accepts up to 9 integers to prevent integer overflow.  
   **Planned Enhancement:** Modify EduTuTu to allow the storage of larger fees & fees in the form of floats.

10. **Enable handling for more special characters in names**  
    **Current Issue:** Users may encounter issues when entering names with comma.  
    **Planned Enhancement:** Modify EduTuTu to allow the storage of names with more special characters.
 
These planned enhancements aim to improve usability, data validation, and user feedback within EduTuTu, addressing known issues while maintaining a smooth user experience.


