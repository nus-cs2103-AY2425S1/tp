---
layout: page
title: Developer Guide
---

Welcome to the **Developer Guide** for our project, a comprehensive resource designed to assist developers in navigating and contributing to our codebase. This guide is built upon the foundational principles of the AddressBook-Level3 project developed by the SE-EDU initiative, ensuring a robust framework for our application.

In this guide, you will find detailed instructions on setting up your development environment, getting started with the codebase, and understanding the design architecture that drives our application. Whether you are an experienced developer or new to our project, this guide aims to provide you with the necessary tools and insights to effectively engage with our work.

* Table of Contents
{:toc}
  
We encourage you to explore each section thoroughly to maximize your understanding and contribution to the project. Feel free to modify any sections or add specific details relevant to your project!

--------------------------------------------------------------------------------------------------------------------


## **Acknowledgements**

* This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------
<br><br>
<br><br>
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

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete z/1`.

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

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete z/2")` API call as an example.

![Interactions Inside the Logic Component for the `delete z/2` Command](images/DeleteSequenceDiagram.png)

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

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below.<br>

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

# **Implementation**

This section describes some noteworthy details on how certain features are implemented.

## Features

###  Create Doctor feature

The CreateDoctorCommand is implemented and used to add a new doctor to the MedDict database in address book.
This command focuses solely on successfully adding a doctor to the system.

**Example Usage Scenario:**

- Step 1: The user launches the application, and the address book is initialized with the current data. <br>

  - The AddressBookParser will handle the input command when the user types something like : <br>
  `createDoctor n/Dr Jane Smith p/87654321 e/dr.jane.smith@hospital.com a/456 Elm Street r/physiotherapy` <br>


- Step 2: The user executes the create doctor command to add a new doctor to the MedDict database 
in address book. <br>
  - The `CreateDoctorCommand` is generated by the AddressBookParser
    and passed to the LogicManager for execution. The `CreateDoctorCommand` checks whether a doctor
    with the same identity already exists in the MedDict database in address book.
    If a duplicate is found, the command will not proceed and will return an error message.
    If no duplicate is found, the doctor will be added to the MedDict database in address book.

- Step 3: The command successfully adds the doctor to the MedDict database in address book. <br>
  - After successfully adding the doctor, the system displays a success message confirming the addition of the new doctor. <br>
  `Successfully created a new doctor Doctor#XX : XXX ` <br>

The CreatePatientCommand is used to add a new doctor to the MedDict database in address book.
This command focuses solely on successfully adding a doctor to the system.

![CreateDoctorCommandSequenceDiagram](images/CreateDoctorCommand-Logic.png)

### Clear Feature

The ClearCommand is implemented to reset the MedDict database in the address book by clearing all existing data. 
This command is designed to clear the address book, removing all patients, doctors, 
and related information stored in the system.

**Example Usage Scenario:**

- Step 1: The user launches the application, and the address book is initialized with the current data. <br>
  - The AddressBookParser will handle the input command when the user types something like: <br>

    `clear` <br> 

- Step 2: The user executes the clear command to reset the MedDict database.
  - The ClearCommand is generated by the AddressBookParser and passed to the LogicManager for execution.
    The ClearCommand then interacts with the model to replace the current address book with a new, empty instance.

- Step 3: The command successfully clears the MedDict database.
After successfully clearing the address book, the system displays a confirmation message to the user: <br>

    `MedDict has been cleared!` <br>

The ClearCommand feature is a straightforward command used to reset the entire MedDict database in the address book. 
This command does not involve checking for duplicates or specific conditions, as it simply clears all stored data.

![ClearCommandSequenceDiagram](images/ClearCommand-Logic.png)

### \[Proposed\] Sharing Patient Information Feature

#### Proposed Implementation

<div markdown="block" class="alert alert-info">

**:information_source: SharePatientInfoCommand** <br>

* Parameter 1 : patientId <br>

* Parameter 2 : doctorId <br>

* Format : `SharePatientInfoCommand z/patientId z/doctorId` <br>

* The proposed SharePatientInfoCommand feature will allow secure sharing of patient information between doctors, 
enabling collaborative care while maintaining data privacy.
</div>

**Example Usage Scenario for SharePatientInfoCommand:** <br>

- Step 1: The user initiates the sharePatientInfo command to share specific patient details with another doctor. <br>
  - The AddressBookParser will handle the input command when the user types something like: <br> `sharePatientInfo z/1234 z/6789`


- Step 2: The SharePatientInfoCommand verifies permission and patient existence. <br>
  - The SharePatientInfoCommand is generated by the AddressBookParser and passed to the LogicManager for execution.
  The command verifies the doctor’s permission to share the information and checks if the specified patient exists in the system.
  If the patient exists and sharing is permitted, the command grants access to the specified doctor.


- Step 3: The system confirms successful sharing. Upon successful sharing, the system displays a message confirming the action: <br>

  - `Patient information for Patient#XX has been successfully shared with Doctor#YY.`

**Description:**
The SharePatientInfoCommand feature enables secure sharing of patient information between doctors, 
allowing for collaborative care while maintaining data privacy. This command verifies permissions and patient 
existence before sharing any details, ensuring patient information is handled securely.

**Security Considerations:**

Ensure that patient data access complies with data privacy standards, with permissions and access controls implemented as needed. <br>

**Example Usage Scenario:** <br>
- Step 1: Doctor A wants to share a patient’s information with Doctor B for coordinated treatment. <br>
- Step 2: Doctor A executes the command with details, e.g., `sharePatientInfo z/1234 z/6789`. <br>
AddressBookParser parses the input and creates a SharePatientInfoCommand. <br>
- Step 3: The command verifies the permission by checking identities of DoctorA and Doctor B 
and grants access, displaying a success message.

**Design Considerations:**

- Aspect: Maintaining Patient Data Privacy in Collaborative Care <br>

- Proposed Choice: Implement access control within SharePatientInfoCommand, 
requiring verification before sharing data. <br>

- **Pros**: Ensures data privacy while allowing collaboration. <br>
- **Cons**: Adds complexity due to permission management. <br>


- **Alternative Approach**: Develop a dedicated sharing module with access logs and permission requests. <br>
  - **Pros**: Allows a more sophisticated and controlled sharing environment. <br>
  - **Cons**: Increases system complexity, requiring additional security and privacy checks. <br>

### \[Proposed\] Recording Billing Information Feature

#### Proposed Implementation

<div markdown="block" class="alert alert-info">

**:information_source: RecordBillingInfoCommand** <br>

* Parameter 1: patientId
* Parameter 2: billingDetails
* Format: `RecordBillingInfoCommand z/patientId b/billingDetails`

* The proposed RecordBillingInfoCommand feature enables users to record billing information for each session, 
making it easier to manage payments and invoicing.

</div>

**Example Usage Scenario for RecordBillingInfoCommand:** <br>

- Step 1: The user initiates the RecordBillingInfoCommand to log billing information for a session with a patient.
The AddressBookParser will handle the input command when the user types something like: <br>

    `recordBillingInfo z/1234 b/Consultation fee: $100, Paid: Yes` <br>


- Step 2: The RecordBillingInfoCommand verifies patient existence. The RecordBillingInfoCommand is generated 
by the AddressBookParser and passed to the LogicManager for execution. The command checks if the specified patient exists. 
If the patient is found, the billing information is added to their profile under a “Billing History” section. 
If not, an error message is returned.


- Step 3: The system confirms successful billing record. Upon successful recording, the system displays a message:

    `Billing information for Patient#XX has been recorded.` <br>

**Description**:
The RecordBillingInfoCommand allows doctors to keep track of billing and payment details for each patient session, 
aiding in financial management and invoicing.

**Example Usage Scenario**: <br>
- Step 1: Doctor receives payment from a patient after a session and wants to record it. <br>


- Step 2: Doctor executes the command with details, e.g., `recordBillingInfo z/1234 b/Consultation fee: $100, Paid: Yes`.
AddressBookParser parses the input and generates a RecordBillingInfoCommand.


- Step 3: The command logs the billing information in the patient's profile, confirming successful record entry. <br>

**Design Considerations:**

- Aspect: Storing Billing Information in Patient Profiles <br>

- Proposed Choice: Add billing entries directly to the patient’s profile. <br>
- **Pros**: Simple implementation, with easy access to billing records when viewing patient details. <br>
- **Cons**: Profiles may become cluttered with billing information over time. <br>


- **Alternative Approach**: Store billing information in a separate log or table linked to the patient profile. <br>
    - **Pros**: Keeps patient profiles focused on medical information while allowing detailed billing history to be retrieved as needed. <br>
    - **Cons**: Adds complexity in managing a separate billing data structure. <br>
  
--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

_Team size: 5_

1. **Ensure that the remarks field integrates well in the application.** Currently the `Edit` remark doesn't work for the remarks of a person and there is no command to delete remarks as well. It seems that there is no way for user to edit the remarks for now. We plan to include the remarks field for the edit command and also design a separate `delete remark` command.


2. **Modify the `Find` and `Get` commands**. Currently both find and get commands are only able to search for one word. The commands are unable to differentiate between `Dr A` and `Dr B` as both entries have the same first word. For future changes, we are going to modify the `Find` and `Get` commands such that they will be responsive to an input of strings e.g. `Find Dr A`.


3. **Improvement can be done to restrictive name format**. For the name field of a person, currently special characters are not allowed. Moving ahead, we would be showing a warning to the user but still allows for the inclusion of any special characters to a name.


4. **Increase the command result box's size**. Currently the command box and command result box are relatively small and it will be very hard for users to view the result of the command that they have entered. Hence, to improve on this, we have planned to increase the size of the command result box and also make it more responsive for better readability.


5. **UI improvements for long names, remarks and addresses**. When dealing with long names, remarks and addresses, the app now truncates the value. For future improvement, in order for users to view fully, we would make the UI responsive to long input values.


6. **Make the `Find` and `Get` commands more distinct**. It seems that the `Find` and `Get` commands now are quite similar in their functionality. Both could be used to retrieve a person in the model. To improve on this, we will expand on each command.

   E.g. For the `Find` command, the user could not only find based on a segment of names, but also based on phone, email, appointment etc.


7. **Improve on the appointment field**. Currently when adding an appointment to patient and doctor, only the starting appointment time is needed. However, in the real life, it would be better if the doctor/user could also specify the end time of the appointment to ensure better planning. Hence, we plan to allow the users to input the end time when adding appointment.


8. **Fix the known bug with the sample dataset**. On some devices, there were occasional occurrences of a bug where while trying to add remarks to a person, it would add the remarks to all people in the sample database. This is a bug that could only be replicated at times, upon downloading the .jar file for the first time. As it only affects the sample dataset and the function works fine for user-added patients and doctors, our team has not yet fixed this issue, prioritizing other matters over this as this issue is unlikely to affect the typical and actual use of the application.

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
| `* * *`  | Doctor  | create a new patient profile                                          | record their details and track their progress                                         |
| `* * *`  | Doctor  | delete a patient profile                                              | get rid of patient's information that I no longer need /  no longer a patient of mine |
| `* * *`  | Doctor  | add recurring appointments for a patient                              | manage their treatment schedule efficiently                                           |
| `* * *`  | Doctor  | update patient details                                                | keep their medical information current                                                |
| `* * *`  | Doctor  | add remarks to a patient’s record after each session                  | track their progress over time                                                        |
| `* * *`  | Doctor  | see which patients have upcoming appointments today                   | prepare in advance                                                                    |
| `* * *`  | Doctor  | view the history of treatments for a patient                          | monitor their improvement                                                             |
| `* * *`  | Doctor  | mark a patient’s session as completed                                 | know which patients have been seen                                                    |
| `* * `   | Doctor  | filter patients by condition or treatment type                        | search patients of a particular condition or treatment for more follow-up actions     |
| `* * `   | Doctor  | track my own schedule for the week                                    | plan my workload                                                                      |
| `* * `   | Doctor  | automatically schedule follow-up appointments for recurring patients  | I don’t have to manually book each appointment                                        |
| `* * `   | Doctor  | set reminders for follow-up appointments                              | I don’t miss any important sessions                                                   | |
| `* * `   | Doctor  | assign different exercises or treatments to a patient                 | their care plan is personalized                                                       |
| `* * `   | Doctor  | add vital signs data (e.g., blood pressure, heart rate) for a patient | monitor their health metrics                                                          |
| `* * `   | Doctor  | track patient feedback after each session                             | adjust their treatment plan if needed                                                 |
| `* * `   | Doctor  | set goals for a patient’s treatment plan                              | measure their progress against these goals                                            |
| `* * `   | Doctor  | record billing information for each session                           | manage payments and invoicing                                                         |
| `* * `   | Doctor  | access a summary of all my patients                                   | get an overview of their conditions and treatments                                    |
| `* * `   | Doctor  | view all the sessions a patient has missed                            | follow up with them and arrange for make-up session                                   |
| `* `     | Doctor  | collaborate with other doctors by sharing patient information         | we can coordinate care                                                                |

### Use cases

(For all use cases below, the **System** is the `MedDict` and the **Actor** is the `physiotherapist`, unless specified otherwise)

**Use case - UC01: Delete a person**

**MSS**

1.  The user searches for the person in the list by name
2.  MedDict displays all matching persons
3.  The user selects the specific person profile to delete
4.  The user requests to delete a specific person in the list by entering the ID
5.  MedDict deletes the person profile from the system and notifies the user that the profile has been successfully deleted

    Use case ends.

**Extensions**

* 2a: The list of persons is empty.

    * 2a1. MedDict informs the user that no persons are available for deletion
    * Use case ends

* 3a. The given index or person selection is invalid.

    * 3a1. MedDict shows an error message indicating that the selected person ID is invalid or does not exist

      Use case resumes from step 2


**Use case - UC02: Add Appointment**

**MSS**

1.  User requests to make a new appointment
2.  MedDict displays the user's schedule
3.  User selects a specific time slot
4.  User searches for the patient by the ID
5.  User requests to assign the time slot to a person
6.  MedDict assigns the selected time slot to the chosen person and confirms the appointment

    Use case ends.

**Extensions**

* 2a: No available time slots.

    * 2a1. MedDict notifies the user that no time slots are available
    * 2a2. The user chooses to modify the time slot of the appointment
    * Use case ends

* 4a. The selected person is not found in the system.

    * 4a1. MedDict informs the user that the person is not found
    * 4a2. The user could create a new person profile or re-enter the person's name

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
7. All sensitive contact information, including patient names, phone numbers, and addresses, must be encrypted when stored on disk.
8. Only authorized users (e.g., licensed physiotherapists) should be able to access or modify patient details.
9. should also be scalable to accommodate larger clinics that may need to store 10,000+ patient records


### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Command prefixes
 
| Prefix Constant              | Abbreviation |
|------------------------------|--------------|
| `PREFIX_NAME`                | `n/`         |
| `PREFIX_PHONE`               | `p/`         |
| `PREFIX_EMAIL`               | `e/`         |
| `PREFIX_ADDRESS`             | `a/`         |
| `PREFIX_REMARK`              | `r/`         |
| `PREFIX_DATE`                | `x/`         |
| `PREFIX_DAY_DATE`            | `y/`         |
| `PREFIX_ID`                  | `z/`         |
    
     
### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    1. Copy or move the .jar file to the folder you want to use as the home folder for your MedDict application. We recommend creating a separate folder for the application as MedDict will create files in the directory it is saved in for storage purposes.
   2. Open a command terminal, cd into the folder you put the .jar file in, and use the java -jar addressbook.jar command to run the application.
      After completing the above, the application should open with a Graphical User Interface (GUI) should appear in a few seconds.


2. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.


3. Launch with missing/corrupted data file

    1. Open the data/addressbook.json file and intentionally introduce invalid JSON syntax. 
    1. Launch the application.  
   Expected: The app should detect the corrupted data file and display an error message, prompting the user to either reset the data or attempt to recover it. The application should not crash.


4. Abrupt shutdown and recovery

    1. Open the application and make several modifications to doctors and patients list (e.g., add a few entries).
    1. Forcefully close the application (e.g., by using Task Manager on Windows or `kill`<br> command on Mac/Linux).
    1. Re-launch the application.  
   Expected: The app should handle the previous abrupt shutdown gracefully. No data corruption should occur and the app should start normally.

### Deleting a person (patient or doctor)

1. Deleting a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    1. Test case: `delete z/1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

    1. Test case: `delete z/0`<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `delete`, `delete z/x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.


2. Deleting a person when the list is empty

    1. Prerequisites: Clear the list using the `clear` command.

    1. Test case: `delete z/x` for any x<br>
       Expected: No person is deleted. Error message is shown indicating that there are no persons to delete. Status bar remains unchanged.


3. Deleting multiple persons consecutively

    1. Prerequisites: List all persons using the `list` command. Ensure there are at least three persons in the list.

    1. Test case: `delete z/1`, `delete z/1`, `delete z/1`  
       Expected: The first three persons in the list are deleted consecutively. After each deletion, the status message displays details of the deleted person, and the timestamp in the status bar is updated.


4. Deleting with mixed case command

    1. Test case: `DELETE z/1`  
       Expected: Command is not recognized due to incorrect capitalization. Error message is displayed indicating an unrecognized command. Status bar remains unchanged.


5. Deleting a person by name instead of index

    1. Test case: `delete z/John`  
    Expected: No person is deleted. Error message is shown, indicating that the delete command expects an index, not a name. Status bar remains unchanged.



### Deleting an appointment

1. Deleting an existing appointment

   1. Test case: `deleteA z/1234 z/5679 x/2024-12-31 15:23`  
   Expected: The appointment between the doctor with ID 5679 and patient with ID 1234 scheduled on 2024-12-31 15:23 is deleted successfully. A confirmation message "Successfully deleted appointment to a patient" is displayed.

   2. Test case: `deleteA z/9999 z/5679 x/2024-12-31 15:23` (non-existent patient ID)  
   Expected: No appointment is deleted. An error message "The appointment doesn't exist!" is displayed, as there is no patient with ID 9999.

   3. Test case: `deleteA z/1234 z/9998 x/2024-12-31 15:23` (non-existent doctor ID)  
   Expected: No appointment is deleted. An error message "The appointment doesn't exist!" is displayed, as there is no doctor with ID 9998.


2. Attempting to delete an appointment that does not exist

   1. Prerequisites: Ensure there is no appointment scheduled between the specified doctor and patient at the specified date and time.

   2. Test cases: `deleteA z/1234 z/5679 x/2024-12-31 16:00`  
   Expected: No appointment is deleted. An error message "The appointment doesn't exist!" is displayed, as there is no appointment scheduled at 2024-12-31 16:00 between the specified doctor and patient.


3. Deleting an appointment with incorrect date format

   1. Test case: `deleteA z/1234 z/5679 x/31-12-2024 15:23`  
   Expected: No appointment is deleted. An error message is displayed, indicating that the date format is incorrect. The command should specify the correct format (e.g., YYYY-MM-DD HH:MM).
   
   2. Test case: `deleteA z/1234 z/5679 x/2024/12/31 15:23`  
   Expected: No appointment is deleted. An error message is displayed, indicating the invalid date format.
   

4. Deleting an appointment without providing required IDs

   1. Test case: `deleteA x/2024-12-31 15:23` (missing patient and doctor IDs)  
   Expected: No appointment is deleted. An error message is displayed, indicating that both patient and doctor IDs are required.
   
   2. Test case: `deleteA z/1234 x/2024-12-31 15:23` (missing doctor ID)  
   Expected: No appointment is deleted. An error message is displayed, indicating that both patient and doctor IDs are required.
   

5. Deleting an appointment with non-numeric IDs

   1. Test case: `deleteA z/abc z/5679 x/2024-12-31 15:23`  
   Expected: No appointment is deleted. An error message is displayed, indicating that the patient ID must be a numeric value.
   2. Test case: `deleteA z/1234 z/xyz x/2024-12-31 15:23`  
   Expected: No appointment is deleted. An error message is displayed, indicating that the doctor ID must be a numeric value.

      
### Creating a patient

1. Creating a new patient with valid details
   1. Prerequisites: Ensure the patient's name, phone, email, and address are unique (not already in the system).

   2. Test cases: `createP n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25`  
   Expected: A new patient profile is created with the specified details. A confirmation message "Successfully created a new patient Patient#<ID> : John Doe" is displayed, where <ID> is the unique ID assigned to the new patient.


2. Attempting to create a duplicate patient
   1. Prerequisites: Ensure a patient profile with the same name, phone, email, and address already exists in the system.

   2. Test cases: `createP n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25`  
   Expected: No new patient is created. An error message "This patient already exists" is displayed, indicating a duplicate profile.


3. Creating a patient with missing required fields
   1. Test case: `createP p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25` (missing name)  
   Expected: No patient is created. An error message is displayed, indicating that the name field is required.
   
   2. Test case: `createP n/John Doe e/johnd@example.com a/311, Clementi Ave 2, #02-25` (missing phone)  
   Expected: No patient is created. An error message is displayed, indicating that the phone field is required.
   
   3. Test case: `createP n/John Doe p/98765432 a/311, Clementi Ave 2, #02-25` (missing email)  
   Expected: No patient is created. An error message is displayed, indicating that the email field is required.
   
   4. Test case: `createP n/John Doe p/98765432 e/johnd@example.com` (missing address)  
   Expected: No patient is created. An error message is displayed, indicating that the address field is required.
   

4. Creating multiple patients consecutively 
   1. Test case: `createP n/Patient A p/91234567 e/patientA@example.com a/123, Baker Street` Followed by: `createP n/Patient B p/81234567 e/patientB@example.com a/456, River Valley Road`  
   Expected: Both patients are created successfully, each with a unique ID. Confirmation messages are displayed for each creation, indicating successful creation of "Patient#<ID> : Patient A" and "Patient#<ID> : Patient B".

### Creating a doctor

1. Creating a new doctor with valid details
   1. Prerequisites: Ensure the doctor's name, phone, email, and address are unique (not already in the system).

   2. Test case: `createD n/Dr. John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25`  
   Expected: A new doctor profile is created with the specified details. A confirmation message "Successfully created a new doctor Doctor#<ID> : Dr. John Doe" is displayed, where <ID> is the unique ID assigned to the new doctor.


2. Attempting to create a duplicate doctor
   1. Prerequisites: Ensure a doctor profile with the same name, phone, email, and address already exists in the system.

   2. Test case: `createD n/Dr. John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25`  
   Expected: No new doctor is created. An error message "This doctor already exists" is displayed, indicating a duplicate profile.


3. Creating a doctor with missing required fields

   1. Test case: `createD p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25` (missing name)  
   Expected: No doctor is created. An error message is displayed, indicating that the name field is required.
      
   2. Test case: `createD n/Dr. John Doe e/johnd@example.com a/311, Clementi Ave 2, #02-25` (missing phone)  
   Expected: No doctor is created. An error message is displayed, indicating that the phone field is required.
   
   3. Test case: `createD n/Dr. John Doe p/98765432 a/311, Clementi Ave 2, #02-25` (missing email)  
   Expected: No doctor is created. An error message is displayed, indicating that the email field is required.
   
   4. Test case: `createD n/Dr. John Doe p/98765432 e/johnd@example.com` (missing address)  
   Expected: No doctor is created. An error message is displayed, indicating that the address field is required.
    
  
4. Creating multiple doctors consecutively

   1. Test case: `createD n/Dr. A p/91234567 e/drA@example.com a/123, Baker Street`
   Followed by: `createD n/Dr. B p/81234567 e/drB@example.com a/456, River Valley Road`   
   Expected: Both doctors are created successfully, each with a unique ID. Confirmation messages are displayed for each creation, indicating successful creation of "Doctor#<ID> : Dr. A" and "Doctor#<ID> : Dr. B".

### Adding an appointment

1. Adding a new appointment with valid details

   1. Prerequisites: Ensure that both the patient and doctor IDs exist in the system and are available at the specified appointment time.
   2. Test case: `addA z/1234 z/5679 x/2024-12-31 15:23 r/physiotherapy session`   
   Expected: A new appointment is created successfully for the specified patient and doctor at the specified time. A confirmation message "Successfully added appointment to a patient" is displayed.


2. Adding an appointment with unavailable time slot

   1. Prerequisites: Ensure that either the patient or doctor already has an appointment at the specified time.

   2. Test case: `addA z/1234 z/5679 x/2024-12-31 15:23 r/follow-up`   
   Expected: No appointment is added. An error message "The patient or doctor already has another appointment!" is displayed, indicating that the slot is unavailable.


3. Adding an appointment with invalid IDs

   1. Prerequisites: Use non-existent patient or doctor IDs.

   2. Test case: `addA z/9999 z/5679 x/2024-12-31 15:23 r/consultation` (non-existent patient ID)  
   Expected: No appointment is added. An error message "The doctor or the patient ID that you have entered doesn't exist. Please enter again!" is displayed.

   3. Test case: `addA z/1234 z/9998 x/2024-12-31 15:23 r/consultation` (non-existent doctor ID)  
   Expected: No appointment is added. The same error message as above is displayed.


4. Adding an appointment with missing required fields

   1. Test case: `addA z/1234 z/5679 r/check-up` (missing date)  
   Expected: No appointment is added. An error message is displayed indicating that the date field is required.
   
   2. Test case: `addA z/1234 x/2024-12-31 15:23 r/check-up` (missing doctor ID)  
   Expected: No appointment is added. An error message is displayed indicating that the doctor ID field is required.
   

5. Adding an appointment with invalid date format

   1. Test case: `addA z/1234 z/5679 x/31-12-2024 15:23 r/check-up` (incorrect date format)  
   Expected: No appointment is added. An error message is displayed indicating the date format should be YYYY-MM-DD HH:MM.
   

6. Adding multiple appointments consecutively

   1. Test case: `addA z/1234 z/5679 x/2024-12-31 14:00 r/check-up`
   Followed by: `addA z/1234 z/5679 x/2024-12-31 16:00 r/follow-up`   
   Expected: Both appointments are added successfully, each at the specified time. A confirmation message is displayed for each appointment, indicating successful addition.



### Adding a remark

1. Adding remarks with valid patient ID

   1. Prerequisites: Ensure that the patient with the specified ID exists in the system.

   2. Test case: `addR z/1234 r/Much better than previous appointment.`  
   Expected: The remark is successfully added to the patient with ID 1234. A confirmation message is displayed: "Successfully added remarks: Much better than previous appointment to patient of ID: 1234."


2. Adding multiple remarks to the same patient consecutively

   1. Prerequisites: Ensure that the patient with the specified ID exists in the system.

   2. Test case: `addR z/1234 r/Needs more rest.` followed by `addR z/1234 r/Monitor blood pressure.`  
   Expected: Both remarks are added to the patient with ID 1234, with each remark appended to the existing ones. A confirmation message is displayed for each addition.


3. Adding remarks with non-existent patient ID

   1. Test case: `addR z/9999 r/Condition improving.` (where patient ID 9999 does not exist)  
   Expected: No remarks are added. An error message "Unable to add remarks! Check the ID entered!" is displayed, indicating that the patient ID is invalid.
   

4. Adding empty remarks

   1. Prerequisites: Ensure that the patient with the specified ID exists in the system.

   2. Test case: `addR z/1234 r/` (no text or only spaces after the remarks prefix)  
   Expected: No remarks are added. An error message is displayed, indicating that remarks cannot be empty.


5. Adding remarks with a very long text 

   1. Prerequisites: Ensure that the patient with the specified ID exists in the system.

   2. Test case: `addR z/1234 r/Patient's condition improved dramatically after treatment. Long-term prognosis looks positive.` (long remark)  
   Expected: The entire text of the remark is successfully added to the patient with ID 1234. A confirmation message is displayed with the full added remark.


### Checking an appointment

1. Check appointments with valid doctor ID and date

   1. Prerequisites: Ensure that the doctor with the specified ID exists in the system and has appointments scheduled on the specified date.

   2. Test case: `checkA z/01 y/2023-09-25`  
   Expected: The list of appointments for Doctor with ID 01 on 2023-09-25 is displayed. If appointments are present, details of each appointment are shown.


2. Check appointments for a doctor with no appointments on the specified date

   1. Prerequisites: Ensure that the doctor with the specified ID exists in the system but has no appointments on the specified date.

   2. Test case: `checkA z/01 y/2023-11-15`  
   Expected: No appointments are found for Doctor with ID 01 on 2023-11-15. A message "No appointment found for Doctor: [Doctor Name]" is displayed.


3. Check appointments with a non-existent doctor ID

   1. Test case: `checkA z/999 y/2023-09-25` (where Doctor ID 999 does not exist in the system)  
   Expected: No appointments are found. An error message is displayed.
 
  
4. Check appointments with a missing date parameter

   1. Prerequisites: Ensure that the doctor with the specified ID exists in the system.

   2. Test case: `checkA z/01` (no date specified)  
   Expected: An error message "No date time is given for Doctor appointment: [Doctor Name]" is displayed, indicating that the date parameter is missing.


5. Check appointments with a doctor having multiple patients on the same day

   1. Prerequisites: Ensure that the doctor with the specified ID exists and has multiple appointments scheduled with different patients on the same date.

   2. Test case: `checkA z/01 y/2023-10-20`  
   Expected: A list of all appointments for Doctor with ID 01 on 2023-10-20 is displayed. Each appointment includes details of the patient, appointment time, and any remarks.


6. Check appointments with an invalid date format

   1. Test case: `checkA z/01 y/25-09-2023` (incorrect date format)   
   Expected: An error message is displayed, indicating that the date format is invalid. The command should prompt the user to enter the date in the correct format (YYYY-MM-DD).

7. Check appointments with future date (no appointments yet)

   1. Prerequisites: Ensure that the doctor has no appointments scheduled for a future date.

   2. Test case: `checkA z/01 y/2030-01-01`  
   Expected: No appointments are found. A message "No appointment found for Doctor: [Doctor Name]" is displayed.


### Marking an appointment

1. Mark an existing appointment as complete with valid doctor and patient IDs

   1. Prerequisites: Ensure that an appointment exists between the specified doctor and patient at the specified time.

   2. Test case: `mark z/1234 z/5679 x/2024-12-31 15:23`  
   Expected: The appointment between Patient with ID 1234 and Doctor with ID 5679 on 2024-12-31 at 15:23 is marked as complete. A message "Successfully marked appointment as complete" is displayed.


2. Mark a non-existent appointment as complete

   1. Prerequisites: Ensure that no appointment exists between the specified doctor and patient at the specified time.

   2. Test case: `mark z/1234 z/5679 x/2025-01-01 10:00`  
   Expected: An error message "The appointment doesn't exist!" is displayed, as there is no appointment to mark as complete.


3. Mark an appointment with a non-existent ID

   1. Test case: `mark z/1234 z/9999 x/2024-12-31 15:23` (where Doctor ID 9999 does not exist)  
   Expected: An error message is displayed, indicating that the doctor ID is invalid.

   2. Test case: `mark z/9999 z/5679 x/2024-12-31 15:23` (where Patient ID 9999 does not exist)  
   Expected: An error message is displayed, indicating that the patient ID is invalid.


4. Mark an appointment with a missing appointment time parameter

   1. Prerequisites: Ensure that the doctor and patient IDs are valid and that there is an existing appointment.

   2. Test case: `mark z/1234 z/5679` (no date specified)  
   Expected: An error message indicating that the date/time parameter is missing and required for marking an appointment as complete.


5. 1. Mark an appointment with an invalid date format 
   1. Test case: `mark z/1234 z/5679 x/31-12-2024 15:23` (incorrect date format)  
   Expected: An error message indicating that the date format is invalid. The command should prompt the user to enter the date in the correct format (YYYY-MM-DD HH
   ).
   

6. Mark an appointment for a doctor and patient with an appointment in the past

   1. Prerequisites: Ensure that an appointment exists in the past between the doctor and patient.

   2. Test case: `mark z/1234 z/5679 x/2023-01-01 10:00`  
   Expected: The appointment is marked as complete if it exists. If marked, a success message is shown. If the appointment does not exist, an error message is displayed.

### Getting ID of a person

1. Get ID with an exact match of the name
   1. Prerequisites: Ensure a person with the name "John Philips" exists in the address book.

   2. Test case: `get John Philips`  
   Expected: The ID of "John Philips" is returned. A message like "ID: [ID]" is displayed.


2. Get ID with a partial match of the name

   1. Prerequisites: Ensure a person with the name "John Philips" exists in the address book. 
   
   2. Test case: `get John`   
   Expected: The ID of "John Philips" is returned if there is only one match. If there are multiple matches, the ID of the first match is returned.


3. Get ID when the name does not exist

   1. Test case: `get Nhoj Eod`  
   Expected: An error message  is displayed, as there is no match for "Nhoj Eod".


4. Get ID when there is no input provided

   1. Test case: `get`  
   Expected: An error message prompting for input. Command should not execute without keywords.
   

5. Get ID with numeric characters in the name

   1. Prerequisites: Ensure a person with the name "John123" exists in the address book.

   2. Test case: `get John123`  
   Expected: The ID of "John123" is returned.

### Viewing a person's history

1. View full history of a patient by ID

   1. Prerequisites: Ensure the patient with ID 2 has a recorded medical history.

   2. Test case: `view z/2`  
   Expected: All recorded history for patient ID 2 is displayed.


2. View history on a specific date and time

   1. Prerequisites: Ensure the patient with ID 2 has a recorded appointment on 2023-09-25 10:15.

   2. Test case: `view z/2 x/2023-09-25 10:15`  
   Expected: Only the history of the specific appointment on 2023-09-25 10:15 for patient ID 2 is displayed.


3. View history for a date and time with no records

   1. Prerequisites: Ensure the patient with ID 2 does not have an appointment on 2024-01-01 12:00.

   2. Test case: `view z/2 x/2024-01-01 12:00 `  
   Expected: An error message is displayed, as there is no history for that date and time.


4. View history for a non-existent patient ID

   1. Test case: `view z/9999`
   Expected: An error  is displayed, as there is no patient with ID 9999.
   

5. View history with invalid date format
   1. Test case: `view z/2 x/2023/09/25 10:15` (incorrect date format)  
   Expected: An error message is shown indicating invalid date format. Command should not execute due to improper input.
   

6. View history when there are multiple history entries on the same date but different times

   1. Prerequisites: Ensure patient ID 2 has multiple history entries on 2023-09-25 at different times (e.g., 10:15 and 14:30).

   2. Test case: `view z/2 x/2023-09-25 10:15`  
   Expected: Only the history of the specific appointment at 10:15 on 2023-09-25 is displayed.

   3. Test case: `view z/2 x/2023-09-25`  
   Expected: If the command is adjusted to accept dates without time, it should display all history entries for 2023-09-25.


7. View history for a patient with no history at all

   1. Prerequisites: Ensure the patient with ID 2 has no recorded history.

   2. Test case: `view z/2`  
   Expected: An error message  is displayed, indicating no history entries for patient ID 2.


8. View history by specifying both patient ID and invalid date-time combination

   1. Prerequisites: Ensure the patient with ID 2 does not have an appointment at the provided date-time. 
   2. Test case: `view z/2 x/2024-12-31 15:23`  
   Expected: An error message is displayed, as no appointment or history exists for this date-time combination.

### Saving data

1. Dealing with missing/corrupted data files

    1. Prerequisites: Have a valid `addressbook.json` data file in the expected location. 
    2. Test case: Delete or rename the `addressbook.json` file and then launch the application.  
    Expected: The application should detect the missing data file and create a new, empty `addressbook.json` file upon startup. The user should see a message indicating that the data file was missing and a new one has been created. 
    3. Test case: Corrupt the `addressbook.json` file by introducing invalid JSON syntax (e.g., remove a closing bracket or add an extra comma). Then, launch the application.  
    Expected: The application should detect the corrupted data file and display an error message. 
    4. Test case: Replace `addressbook.json` with a non-JSON file (e.g., rename a text file to `addressbook.json`) and then launch the application.  
    Expected: The application should detect the invalid format and notify the user of the issue. The application should not crash and may prompt the user to reset or repair the data file.


2. Restoring from backup when data file is missing

    1. Prerequisites: Delete the `addressbook.json` file. Ensure a backup file is present in the backup folder. 
    2. Test case: Launch the application.  
    Expected: The application should detect the missing data file and offer to restore from the most recent backup. If the user agrees, the backup file is copied to `addressbook.json`, and the application starts with the restored data.

--------------------------------------------------------------------------------------------------------------------
## **Appendix: Effort**

### Difficulty Level and Challenges

The project’s complexity far surpassed typical single-entity systems like AB3, as it dealt with multiple interrelated entities such as doctors, patients, and appointments. Each entity had distinct attributes, behaviors, and interactions, making data management and system design more complicated. One of the major challenges was ensuring consistency in handling various entity types, while maintaining an intuitive and responsive user experience. The increased complexity required more sophisticated logic for tasks like filtering, and linking entities, making debugging and testing far more intensive than in simpler systems.

We also faced challenges when we want to test the different entities and commands when doing unit and integration testing as it gets more complicated to build stubs that contain entities in a correct format.

### Effort Required

Substantial effort was required to design and implement the core system architecture, which needed to handle the complex relationships between entities without becoming overly rigid or error-prone. A considerable amount of time was invested in designing an extensible data model that could accommodate future requirements and scalability.

The development process involved careful coordination between different aspects of the project, including data storage, UI, and logic. Ensuring that these components interacted seamlessly required numerous iterations and thorough testing, further increasing the overall effort.

### Achievements

Despite the challenges, the project achieved several important milestones. We successfully built a robust system capable of managing and processing doctors' appointements efficiently. The user interface was designed to be simple and efficient while allowing for advanced operations like filtering through complex data sets.

Additionally, we integrated external libraries like JUnit for our testing and JavaFx for our UI components.

Overall, the project successfully provides a specialised one-stop-for-all platform for doctors to their patients, schedules, appointments and to prioritise speed when accessing and updating patients’ information and details on a regular basis.





