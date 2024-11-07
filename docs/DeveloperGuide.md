--
layout: page
title: Developer Guide
---
* Table of Contents
  {:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).

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

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

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

* stores the MedDict data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
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

### Feature

##  Create Doctor feature

The CreateDoctorCommand is implemented and used to add a new doctor to the MedDict database in address book.
This command focuses solely on successfully adding a doctor to the system.

Example Usage Scenario:

Step 1: The user launches the application, and the address book is initialized with the current data.
The AddressBookParser will handle the input command when the user types something like : <br>

`createDoctor n/Dr Jane Smith p/87654321 e/dr.jane.smith@hospital.com a/456 Elm Street r/physiotherapy` <br>


Step 2: The user executes the create doctor command to add a new doctor to the MedDict database 
in address book. The CreateDoctorCommand is generated by the AddressBookParser
and passed to the LogicManager for execution. The CreateDoctorCommand checks whether a doctor
with the same identity already exists in the MedDict database in address book.
If a duplicate is found, the command will not proceed and will return an error message.
If no duplicate is found, the doctor will be added to the MedDict database in address book.

Step 3: The command successfully adds the doctor to the MedDict database in address book.
After successfully adding the doctor, the system displays a success message confirming the addition of the new doctor.

`Successfully created a new doctor Doctor#XX : XXX ` <br>

The CreatePatientCommand is used to add a new doctor to the MedDict database in address book.
This command focuses solely on successfully adding a doctor to the system.

![CreateDoctorCommandSequenceDiagram](images/CreateDoctorCommand-Logic.png)

## Clear Feature

The ClearCommand is implemented to reset the MedDict database in the address book by clearing all existing data. 
This command is designed to clear the address book, removing all patients, doctors, 
and related information stored in the system.

Example Usage Scenario:

Step 1: The user launches the application, and the address book is initialized with the current data.
The AddressBookParser will handle the input command when the user types something like: <br>

`clear` <br> 

Step 2: The user executes the clear command to reset the MedDict database.
The ClearCommand is generated by the AddressBookParser and passed to the LogicManager for execution.
The ClearCommand then interacts with the model to replace the current address book with a new, empty instance.

Step 3: The command successfully clears the MedDict database.
After successfully clearing the address book, the system displays a confirmation message to the user: <br>

`MedDict has been cleared!` <br>

The ClearCommand feature is a straightforward command used to reset the entire MedDict database in the address book. 
This command does not involve checking for duplicates or specific conditions, as it simply clears all stored data.

![ClearCommandSequenceDiagram](images/ClearCommand-Logic.png)

### \[Proposed\] Sharing Patient Information Feature

#### Proposed Implementation

SharePatientInfoCommand <br>
Parameter 1 : patientId <br>
Parameter 2 : doctorId <br>
Format : `SharePatientInfoCommand z/patientId z/doctorId` <br>

The proposed SharePatientInfoCommand feature will allow secure sharing of patient information between doctors, 
enabling collaborative care while maintaining data privacy.

Example Usage Scenario for SharePatientInfoCommand: <br>

Step 1: The user initiates the sharePatientInfo command to share specific patient details with another doctor.
The AddressBookParser will handle the input command when the user types something like:

`sharePatientInfo z/1234 z/6789`

Step 2: The SharePatientInfoCommand verifies permission and patient existence.
The SharePatientInfoCommand is generated by the AddressBookParser and passed to the LogicManager for execution.
The command verifies the doctor’s permission to share the information and checks if the specified patient exists in the system.
If the patient exists and sharing is permitted, the command grants access to the specified doctor.

Step 3: The system confirms successful sharing. Upon successful sharing, the system displays a message confirming the action: <br>

`Patient information for Patient#XX has been successfully shared with Doctor#YY.`

Description:
The SharePatientInfoCommand feature enables secure sharing of patient information between doctors, 
allowing for collaborative care while maintaining data privacy. This command verifies permissions and patient existence before sharing any details, ensuring patient information is handled securely.

Security Considerations:

Ensure that patient data access complies with data privacy standards, with permissions and access controls implemented as needed. <br>
Example Usage Scenario: <br>
Step 1: Doctor A wants to share a patient’s information with Doctor B for coordinated treatment. <br>
Step 2: Doctor A executes the command with details, e.g., `sharePatientInfo z/1234 z/6789`. <br>
AddressBookParser parses the input and creates a SharePatientInfoCommand. <br>
Step 3: The command verifies the permission by checking identities of DoctorA and Doctor B 
and grants access, displaying a success message.

Design Considerations:

Aspect: Maintaining Patient Data Privacy in Collaborative Care <br>

Proposed Choice: Implement access control within SharePatientInfoCommand, 
requiring verification before sharing data. <br>
Pros: Ensures data privacy while allowing collaboration. <br>
Cons: Adds complexity due to permission management. <br>

Alternative Approach: Develop a dedicated sharing module with access logs and permission requests. <br>
Pros: Allows a more sophisticated and controlled sharing environment. <br>
Cons: Increases system complexity, requiring additional security and privacy checks. <br>

### \[Proposed\] Recording Billing Information Feature

#### Proposed Implementation

RecordBillingInfoCommand
Parameter 1: patientId
Parameter 2: billingDetails
Format: `RecordBillingInfoCommand z/patientId b/billingDetails`

The proposed RecordBillingInfoCommand feature enables users to record billing information for each session, 
making it easier to manage payments and invoicing.

Example Usage Scenario for RecordBillingInfoCommand: <br>

Step 1: The user initiates the RecordBillingInfoCommand to log billing information for a session with a patient.
The AddressBookParser will handle the input command when the user types something like: <br>

`recordBillingInfo z/1234 b/Consultation fee: $100, Paid: Yes` <br>

Step 2: The RecordBillingInfoCommand verifies patient existence. The RecordBillingInfoCommand is generated 
by the AddressBookParser and passed to the LogicManager for execution. The command checks if the specified patient exists. 
If the patient is found, the billing information is added to their profile under a “Billing History” section. 
If not, an error message is returned.

Step 3: The system confirms successful billing record. Upon successful recording, the system displays a message:

`Billing information for Patient#XX has been recorded.` <br>

Description:
The RecordBillingInfoCommand allows doctors to keep track of billing and payment details for each patient session, 
aiding in financial management and invoicing.

Example Usage Scenario: <br>
Step 1: Doctor receives payment from a patient after a session and wants to record it. <br>
Step 2: Doctor executes the command with details, e.g., `recordBillingInfo z/1234 b/Consultation fee: $100, Paid: Yes`.
AddressBookParser parses the input and generates a RecordBillingInfoCommand.
Step 3: The command logs the billing information in the patient's profile, confirming successful record entry. <br>

Design Considerations:

Aspect: Storing Billing Information in Patient Profiles <br>

Proposed Choice: Add billing entries directly to the patient’s profile. <br>
Pros: Simple implementation, with easy access to billing records when viewing patient details. <br>
Cons: Profiles may become cluttered with billing information over time. <br>

Alternative Approach: Store billing information in a separate log or table linked to the patient profile. <br>
Pros: Keeps patient profiles focused on medical information while allowing detailed billing history to be retrieved as needed. <br>
Cons: Adds complexity in managing a separate billing data structure. <br>





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

* Physiotherapists who:
    * Has a significant number of patients to manage
    * Prefers a solution that minimizes data entry and retrieval time
    * Needs quick access to contact details and conditions of patients
    * Needs to monitor and track the progress of recurring / returning patients over a long period of time
    * Prefers typing to mouse interactions and reasonably comfortable using CLI apps

**Value proposition**: Provide a more specialised one-stop-for-all medical related information to their patients, schedules, appointments and to keep track of their medical information, progression, medical history etc. The simple CLI app is optimised for physiotherapists’ routine use during consultations, designed for physiotherapists who prioritise speed when accessing and updating patients’ information and details on a regular basis.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​ | I want to …​                                                          | So that I can…​                                                                       |
|----------|---------|-----------------------------------------------------------------------|---------------------------------------------------------------------------------------|
| `* * *`  | Doctor  | create a new patient profile                                          | I can record their details and track their progress                                   |
| `* * *`  | Doctor  | delete a patient profile                                              | get rid of patient's information that I no longer need /  no longer a patient of mine |
| `* * *`  | Doctor  | add recurring appointments for a patient                              | manage their treatment schedule efficiently                                           |
| `* * *`  | Doctor  | update patient details                                                | keep their medical information current                                                |
| `* * *`  | Doctor  | add notes to a patient’s record after each session                    | track their progress over time                                                        |
| `* * *`  | Doctor  | see which patients have upcoming appointments today                   | I can prepare in advance                                                              |
| `* * *`  | Doctor  | view the history of treatments for a patient                          | monitor their improvement                                                             |
| `* * `   | Doctor  | filter patients by condition or treatment type                        | search patients of a particular condition or treatment for more follow-up actions     |
| `* * `   | Doctor  | track my own schedule for the week                                    | I can plan my workload                                                                |
| `* * `   | Doctor  | automatically schedule follow-up appointments for recurring patients  | I don’t have to manually book each appointment                                        |
| `* * `   | Doctor  | set reminders for follow-up appointments                              | I don’t miss any important sessions                                                   | |
| `* * `   | Doctor  | assign different exercises or treatments to a patient                 | their care plan is personalized                                                       |
| `* * `   | Doctor  | mark a patient’s session as completed                                 | I can know which patients have been seen                                              |
| `* * `   | Doctor  | add vital signs data (e.g., blood pressure, heart rate) for a patient | I can monitor their health metrics                                                    |
| `* * `   | Doctor  | track patient feedback after each session                             | I can adjust their treatment plan if needed                                           |
| `* * `   | Doctor  | set goals for a patient’s treatment plan                              | I can measure their progress against these goals                                      |
| `* * `   | Doctor  | record billing information for each session                           | I can manage payments and invoicing                                                   |
| `* * `   | Doctor  | access a summary of all my patients                                   | I can get an overview of their conditions and treatments                              |
| `* * `   | Doctor  | view all the sessions a patient has missed                            | I can follow up with them and arrange for make-up session                             |
| `* `     | Doctor  | collaborate with other doctors by sharing patient information         | we can coordinate care                                                                |

### Use cases

(For all use cases below, the **System** is the `MedDict` and the **Actor** is the `physiotherapist`, unless specified otherwise)

**Use case - UC01: Delete a person**

**MSS**

1.  The user searches for the patient in the list by name
2.  MedDict displays all matching patients
3.  The user selects the specific patient profile to delete
4.  The user requests to delete a specific patient in the list
5.  MedDict deletes the patient profile from the system and notifies the user that the profile has been successfully deleted

    Use case ends.

**Extensions**

* 2a: The list of patients is empty.

    * 2a1. MedDict informs the doctor that no patients are available for deletion
    * Use case ends

* 3a. The given index or patient selection is invalid.

    * 3a1. MedDict shows an error message indicating that the selected patient profile is invalid or does not exist

      Use case resumes from step 2


**Use case - UC02: Add Appointment**

**MSS**

1.  User requests to make a new appointment
2.  MedDict displays the list of available time slots for appointments
3.  User selects a specific time slot
4.  User searches for the patient by name or the ID
5.  User requests to assign the time slot to a patient
6.  MedDict assigns the selected time slot to the chosen patient and confirms the appointment

    Use case ends.

**Extensions**

* 2a: No available time slots.

    * 2a1. MedDict notifies the doctor that no time slots are available
    * 2a2. The doctor chooses to wait for a cancellation or manually opens additional time slots
    * Use case ends

* 4a. The selected patient is not found in the system.

    * 4a1. MedDict informs the doctor that the patient is not found
    * 4a2. The doctor is given the option to create a new patient profile or re-enter the patient's name

      Use case resumes from step 4


**Use case - UC03: Mark patient’s appointment as completed**

**MSS**

1.  User requests to view all his appointments for the day
2.  MedDict displays all appointments that the user has for the day
3.  User selects the patient that he has just viewed
4.  User requests to mark the patient’s appointment status as completed
5.  System marks the patient’s appointment for the day as completed

    Use case ends.

**Extensions**

* 2a: The user has no patients for that day.

    * 2a1. MedDict displays an empty appointment list for the day
    * Use case ends

* 3a. The patient selected is invalid / doesn't exist.

    * 3a1. MedDict alerts the user that the selected patient profile is invalid or does not exist

      Use case resumes from step 2

* 4a. The patient’s status has already been marked as completed

    * 4a1. MedDict alerts to the user that the appointment has already been marked as completed
    * Use case ends



### Non-Functional Requirements

1. Should work on any mainstream OS as long as it has Java 17 or above installed.
2. Should run efficiently on low-powered machines, such as older clinic computers, without consuming excessive CPU or memory resources (less than 100 MB of RAM usage)
3. The system should allow physiotherapists to find a contact by name or ID within 1-2 seconds to ensure minimal disruption during patient interactions.
4. Command structures should be simple enough for physiotherapists to memorize frequently used operations
5. The searching functionality should not be case-sensitive.
6. The system must be able to handle unexpected errors (e.g., incorrect input, unavailable commands) gracefully by providing clear, actionable error messages
7. The system should automatically back up patient contact data at least once per day to ensure data integrity. Backups should be encrypted and stored in a secure location
8. All sensitive contact information, including patient names, phone numbers, and addresses, must be encrypted when stored on disk.
9. Only authorized users (e.g., licensed physiotherapists) should be able to access or modify patient details.
10. Authorized users will only have access to the patients under their patient-doctor bond (PDB).
11. should also be scalable to accommodate larger clinics that may need to store 10,000+ patient records


### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS

* **Patient-Doctor Bond (PDB)**: A relationship between a doctor and a patient where the doctor is assigned to the patient for physiotherapy treatment

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

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    1. Test case: `deletePatient 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

    1. Test case: `deletePatient 0`<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

    1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
