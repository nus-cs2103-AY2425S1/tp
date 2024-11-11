---
layout: page
title: Developer Guide
---

## Table of Contents

* Table of Contents
{:toc}

<div style="page-break-after: always;"></div>

## **Acknowledgements**
MediBase3 is designed based on the AddressBook-Level3 project created by [SE-EDU](https://se-education.org).

Generative AI tools (ChatGPT, Copilot) were used for creating test cases, writing detailed Javadocs and some code refactoring. 

The following Java libraries were also used in the development of MediBase3:
- [JavaFX](https://openjfx.io/) for the GUI
- [JUnit5](https://junit.org/junit5/) for testing
- [Jackson](https://github.com/FasterXML/jackson) for processing JSON files

[Back to Table of Contents](#table-of-contents)
## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

[Back to Table of Contents](#table-of-contents)
## **Design**

{: .alert .alert-primary}
:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

[Back to Table of Contents](#table-of-contents)

<div style="page-break-after: always;"></div>

### Architecture

![](images/ArchitectureDiagram.png){:width="280"}

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-F12-3/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-F12-3/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete T1234567A`.

![](images/ArchitectureSequenceDiagram.png){:width="574"}

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

![](images/ComponentManagers.png){:width="300"}

The sections below give more details of each component.

[Back to Table of Contents](#table-of-contents)
### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-F12-3/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png){:width="740"}

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-F12-3/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-F12-3/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` and `OwnedAppointment` objects residing in the `Model`.

[Back to Table of Contents](#table-of-contents)
### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-F12-3/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

![](images/LogicClassDiagram.png){:width="550"}

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete S1234567A")` API call as an example.

![Interactions Inside the Logic Component for the `delete S1234567A` Command](images/DeleteSequenceDiagram.png)

{: .alert .alert-info}
:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.


How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

![](images/ParserClasses.png){:width="600"}

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

[Back to Table of Contents](#table-of-contents)

<div style="page-break-after: always;"></div>

### Model component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-F12-3/tp/blob/master/src/main/java/seedu/address/model/Model.java)

![](images/ModelClassDiagram.png){:width="740"}


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

{: .alert .alert-info}
:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has an `Allergy` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Allergy` object per unique Allergy, instead of each `Person` needing their own `Allergy` objects.

![](images/BetterModelClassDiagram.png){:width="740"}

[Back to Table of Contents](#table-of-contents)

<div style="page-break-after: always;"></div>

### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-F12-3/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

![](images/StorageClassDiagram.png){:width="740"}

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

[Back to Table of Contents](#table-of-contents)
### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

[Back to Table of Contents](#table-of-contents)
## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### `addAppt` - Add Appointment

This command enables the addition of an `Appointment` for a specified `Person` in the `Model`. Its implementation involves coordinated updates between the `Model` and UI.

As a refresher, this is the addAppt command as described in the User Guide:

> **Format**: 
> `addAppt APPOINTMENT_NAME i/NRIC @d/APPOINTMENT_DATE @t/APPOINTMENT_TIME`
> 
> **Example**:
> `addAppt Dental i/S1234567A @d/2024-10-27 @t/1100-1200` schedules a `Dental` appointment for the patient with `NRIC` `S1234567A` on `2024-10-27`, from `1100` to `1200`.
> 


#### Overview

When executed, this command parses user input and creates an internal representation of the appointment data. The sequence proceeds as follows:

1. **Parse Command and Target Person:**  
   The input command text is parsed to identify the type of command and the target `Person` for the appointment. If the `Person` is found in the model, the process continues with creating an appointment.

2. **Create and Add Appointment:**  
   A new `Appointment` is created with the provided details, and an updated `Person` object is prepared, associating this appointment with the target individual.

3. **Model Update:**  
   The model replaces the old `Person` with this modified version in the address book, which then updates the internal list of appointments to include the new entry.

4. **Automatic UI Refresh:**  
   The `AppointmentListPanel` UI component, which observes changes in the list of appointments, detects the addition and refreshes its display. The UI then reflects this change by showing a new `AppointmentCard` for the recently added appointment.

{: .alert .alert-primary}
> :bulb: **Tip:**
>
> Immutability in `Person` objects prevents data conflicts by ensuring the `AddressBook` only stores updated versions, eliminating orphaned data. Each update cycle refreshes the relevant list views, automatically redrawing the necessary UI elements in the correct order.

<div style="page-break-after: always;"></div>

#### Sequence Diagram

When `addAppt` command is keyed in by the user, `AddApptCommandParser#parse()` generates the a new `AddApptCommand` with the arguments `AppointmentName`, `AppointmentTime`, `AppointmentDate`, and `Nric` retrieved from the user command string. This diagram shows a high-level sequence of what happens when a valid `AddApptCommand` is executed:

![AddApptCommandSequence](images/AddApptCommandSequenceDiagram.png)

{: .alert .alert-info}
:information_source: **Note:** The lifeline for `AddApptCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

[Back to Table of Contents](#table-of-contents)

<div style="page-break-after: always;"></div>

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

[Back to Table of Contents](#table-of-contents)

<div style="page-break-after: always;"></div>

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* doctors who are busy managing their patients and appointments
* doctors who need to know their patient's status
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Our app empowers doctors to focus on what matters most-their patients-by automating laborious administrative tasks. From managing patient records, appointment, and priorities to tracking medical conditions and allergies, Medibase3 stores and manages all vital information into one accessible application. All while having the perks of being faster than a typical mouse/GUI driven app. 


[Back to Table of Contents](#table-of-contents)
### User stories

Priorities: High (must have) - :star: :star: :star:, Medium (nice to have) - :star: :star:, Low (unlikely to have) - :star:

| Priority                                           | As a …            | I want to …                                                                 | So that I can…                                                                                                                                                        |
|----------------------------------------------------|-------------------|-----------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| *:star::star::star:*{: style="white-space:nowrap"} | doctor            | add new records                                                             | keep track of my existing patients' details                                                                                                                           |
| *:star::star::star:*{: style="white-space:nowrap"} | doctor            | delete records                                                              | remove entries of patients no longer existing                                                                                                                         |
| *:star::star::star:*{: style="white-space:nowrap"} | doctor            | edit records                                                                | amend outdated information in the patients' record                                                                                                                    |
| *:star::star::star:*{: style="white-space:nowrap"} | busy doctor       | search for a patient by name                                                | quickly access their records                                                                                                                                          |
| *:star::star::star:*{: style="white-space:nowrap"} | busy doctor       | search for a patient by NRIC                                                | quickly access their records                                                                                                                                          |
| *:star::star::star:*{: style="white-space:nowrap"} | doctor            | schedule an appointment with a patient                                      | manage my daily workload effectively                                                                                                                                  |
| *:star::star::star:*{: style="white-space:nowrap"} | doctor            | delete an appointment with a patient                                        | cancel an appointment                                                                                                                                                 |
| *:star::star::star:*{: style="white-space:nowrap"} | doctor            | list all records                                                            | look through all contacts                                                                                                                                             |
| :star::star:                                       | doctor            | view all my appointments                                                    | know the appointments I have on a certain day                                                                                                                         |
| :star::star:                                       | meticulous doctor | remove a medical condition from a patient's record                          | retrieve the most accurate and up-to-date version of my patient's information, reflecting their current health status                                                 |
| :star::star:                                       | meticulous doctor | remove an allergy from a patient's record                                   | ensure my patient's medical information is current and accurate, which helps me make better decisions when prescribing medication and avoid unnecessary complications |
| :star::star:                                       | meticulous doctor | assign a specific condition to a patient                                    | pay extra care to it during consultation and diagnosis                                                                                                                |
| :star::star:                                       | meticulous doctor | assign a specific allergy to a patient                                      | pay extra care when prescribing medication                                                                                                                            |
| :star:                                             | focused doctor    | want to search patients by medical condition                                | focus on those with similar treatment plans                                                                                                                           |
| :star:                                             | busy doctor       | assign priority level to a patient                                          | manage urgent cases more effectively                                                                                                                                  |
| :star:                                             | busy doctor       | view all my urgent cases                                                    | attend to those with urgent needs first                                                                                                                               |
| :star:                                             | doctor            | press [↑] to fill the command-line-box with the previous command I keyed in | amend errors in the last command I typed easily                                                                                                                       |
| :star:                                             | doctor            | clear all sample data                                                       | insert my own patient details into MediBase3                                                                                                                          |
| :star:                                             | doctor            | access the user guide easily                                                | quickly understand how to use the application's feature                                                                                                               |

[Back to Table of Contents](#table-of-contents)
### Use cases

(For all use cases below, the **System** is the `Medibase3` and the **Actor** is the `user`, unless specified otherwise)

**Use case:** UC1 - Add Patient

**MSS:**
1. User requests to add patient detail
2. User provides the patient detail
3. MediBase3 adds the patient detail

    Use case ends

**Extensions:**

* 2a. The user provides an existing patient in MediBase3.

    * 2a1. MediBase3 informs user of the error.
  
        Use case resumes at step 2.


* 2b. The user provides patient details that is not in the expected format.

    * 2b1. MediBase3 informs user of the error.
  
        Use case resume at step 2.

---

**Use case:** UC2 - Edit Patient

**MSS:**
1. User requests MediBase3 to edit the patient data
2. User provides the new patient detail
3. MediBase3 updates the patient detail
   
   Use case ends

**Extensions:**

* 2a. User provides a non-existing patient detail.

    * 2a1. MediBase3 informs user of the error.
  
        Use case resumes at step 2.


* 2b. User provides a field that is not in the expected format.

    * 2b1. MediBase3 informs user of the error.
  
        Use case resumes at step 2.


* 2c. User provides multiple instances of the same field for the patient.

    * 2c1. MediBase3 informs user of the error.
  
        Use case resumes at step 2.

---

**Use case:** UC3 - Find Patient by Name

**MSS:**
1. User requests to find a patient by with a specific keyword in their name
2. MediBase3 checks each patient's name in the list that contains the keyword
3. MediBase3 shows the selected patient information that match the criteria

   Use case ends

**Extensions:**

* 2a. No patient found with the given name.

    * 2a1. MediBase3 informs user of the error.
  
        Use case ends.

---

**Use case:** UC4 - Find Patient by NRIC

**MSS:**
1. User requests to find a patient by NRIC
2. User provides the details required to search for the patient
3. MediBase3 shows the selected patient information

   Use case ends

**Extensions:**

* 2a. User provides a non-existing patient detail.

    * 2a1. MediBase3 informs user of the error.

        Use case ends.

---

**Use case:** UC5 - Find Patient by Medical Condition

**MSS:**
1. User requests to find a patient by with a specific keyword in their medical condition
2. MediBase3 checks each patient's medical condition in the list that contains the keyword
3. MediBase3 shows the selected patient information that match the criteria

   Use case ends

**Extensions:**

* 2a. No patient found with the given medical condition.

    * 2a1. MediBase3 informs user of the error.

      Use case ends.

---
 
**Use case:** UC6 - List Patients

**MSS:**
1. User requests MediBase3 to list patients detail
2. MediBase3 lists the patient detail sequentially

   Use case ends

**Extensions:**

* 2a. Medibase is unable to list patient details

    * 2a1. MediBase3 does not show any patients.

      Use case ends.

---

**Use case:** UC7 - List Patients By Priority

**MSS:**
1. User requests to list patients by priority
2. User provides the details required to list patients by priority
3. MediBase3 lists patients' details by priority

   Use case ends

**Extensions:**

* 2a. User provides invalid patient details.

    * 2a1. MediBase3 does not show any patients.

      Use case ends.


---
 
**Use case:** UC8 - Add Appointment

**MSS:**
1. User requests to add appointment to the patient detail
2. User provides the appointment detail
3. MediBase3 adds the appointment to the patient detail

   Use case ends

**Extensions:**

* 2a. User provides a field that is not in the expected format.

    * 2a1. MediBase3 informs the user of the error.
  
      Use case resumes at step 2.

* 2b. User provides multiple instances of the same field for the appointment.

    * 2b1. MediBase3 informs the user of the error.
  
      Use case resumes at step 2.

---
 
**Use case:** UC9 - Add Medical Condition

**MSS:**
1. User requests to add medical condition to the patient detail
2. User provides the medical condition
3. MediBase3 adds the medical condition to the patient detail

   Use case ends

**Extensions:**

* 2a. User provides a field that is not in the expected format.

    * 2a1. MediBase3 informs the user of the error.

      Use case resumes at step 2.

* 2b. User provides multiple instances of the same field for the medical condition.

    * 2b1. MediBase3 informs the user of the error.

      Use case resumes at step 2.

---
 
**Use case:** UC10 - Set Patient’s Priority

**MSS:**
1. User requests to set patient's priority 
2. User provides the patient’s priority details
3. MediBase3 sets the patient’s priority

   Use case ends

**Extensions:**

* 2a. User provides a field that is not in the expected format.

    * 2a1. MediBase3 informs the user of the error.
  
      Use case ends.

* 2b. User provides multiple instances of the same field for the medical condition.

    * 2b1. MediBase3 informs the user of the error.

      Use case ends.

---
 
**Use case:** UC11 - Add Allergies to Patients

**MSS:**
1. User requests to add patient’s allergies to the patient detail
2. User provides the allergies details
3. MediBase3 adds the allergies to the patient detail

   Use case ends

**Extensions:**

* 3a. User provides a field that is not in the expected format.

  * 3a1. MediBase3 informs the user of the error.

    Use case ends.

* 3b. User provides multiple instances of the same field for the medical condition.

  * 3b1. MediBase3 informs the user of the error.

    Use case ends.

---

**Use case:** UC12 - Delete Patient Contact

**MSS:**
1. User requests to delete patient contact 
2. User provides the details required to delete the patient contact
3. MediBase3 deletes the patient’s contact

   Use case ends

**Extensions:**

* 2a. User provides a non-existing patient detail.

  * 2a1. MediBase3 informs user of the error.

    Use case resumes at step 2.


* 2b. User provides a field that is not in the expected format.

  * 2b1. MediBase3 informs user of the error.

    Use case resumes at step 2.


* 2c. User provides multiple instances of the same field for the patient.

  * 2c1. MediBase3 informs user of the error.

    Use case resumes at step 2.

---
 
**Use case:** UC13 - Delete Patient Medical Condition   

**MSS:**
1. User requests to delete a patient medical condition
2. User provides the details required to delete the patient’s condition
3. MediBase3 deletes the patient’s condition

   Use case ends.

**Extensions:**

* 2a. User provides a non-existing patient detail.

  * 2a1. MediBase3 informs user of the error.

    Use case resumes at step 2.


* 2b. User provides a field that is not in the expected format.

  * 2b1. MediBase3 informs user of the error.

    Use case resumes at step 2.


* 2c. User provides multiple instances of the same field for the patient.

  * 2c1. MediBase3 informs user of the error.

    Use case resumes at step 2.

---
 
**Use case:** UC14 - Delete Patient Allergies

**MSS:**
1. User requests to delete a patient’s allergies
2. User provides the details required to delete the patient’s allergies
3. MediBase3 deletes the patient’s allergies

   Use case ends

**Extensions:**

* 2a. User provides a non-existing patient detail.

  * 2a1. MediBase3 informs user of the error.

    Use case resumes at step 2.


* 2b. User provides a field that is not in the expected format.

  * 2b1. MediBase3 informs user of the error.

    Use case resumes at step 2.


* 2c. User provides multiple instances of the same field for the patient.

  * 2c1. MediBase3 informs user of the error.

    Use case resumes at step 2.

---

**Use case:** UC15 - Delete Patient Appointment

**MSS:**
1. User requests to delete a patient’s appointment
2. User provides the details required to delete the patient’s appointment
3. MediBase3 deletes the appointment

   Use case ends.

**Extensions:**

* 2a. User provides a non-existing patient detail.

    * 2a1. MediBase3 informs user of the error.

      Use case resumes at step 2.


* 2b. User provides a field that is not in the expected format.

    * 2b1. MediBase3 informs user of the error.

      Use case resumes at step 2.


* 2c. User provides multiple instances of the same field for the patient.

    * 2c1. MediBase3 informs user of the error.

      Use case resumes at step 2.


[Back to Table of Contents](#table-of-contents)
### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  For under 1000 patient details, the application should be able to start up within 3s.
5.  For under 1000 patient details, the application should be able to respond to user commands within 1s.
6.  Error messages and prompts should be clear and easy to understand for users of all technical skill levels.
7.  The user interface should be easy for users to navigate and understand.
8.  The application should be able to function without an internet connection.

[Back to Table of Contents](#table-of-contents)
### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS

* **Allergy**: A specific substance or condition that a patient has a sensitivity or adverse reaction to, such as "Peanuts" or "Lactose".

* **Appointment**: A scheduled meeting between a patient and a doctor, encompassing a specific date, time period and description.

* **Medical Condition**: A diagnosis or health issue assigned to a patient, such as "Diabetes Type 2" or "Hypertension." This helps track and manage a patient's health status.

* **NRIC**: National Registration Identity Card, a unique 9-character identifier used to distinguish each patient. It should start with a letter (S, T, G, F or M), followed by 7 digits, and end with a letter.

* **Priority**: Indicates the urgency of a patient’s condition, with values like none, low, medium, or high to assist doctors in managing urgent cases.

* **Person**: The base object that represents each patient.

* **AddressBook**: The underlying class that holds the all `Person` records.

* **Manager**: Any implementation of the following:
    
    * **Model**: The interface that controls the changes to `AddressBook`.

    * **Logic**: The interface that controls the creation and dispatch of a `Command`.

    * **Storage**: The interface that controls the reading and saving of any data to disk.

* **Parser**: Any class that parses a given input into appropriate arguments.

* **Panel**: A section of the graphical user interface that displays a certain item such as:

    * **Patient List**: A list of patients and their details displayed on the left hand side of the application.

    * **Appointment List**: A list of appointments of all patients, displayed chronologically on the right hand side of the application.

    * **Command Box:** Where you can type and enter commands.

    * **Result Display:** Shows the result of the command you entered.

    * **Menu (File/Help):** Provides additional options for managing the app (e.g., exit, access help).

    * **Data Storage Location Footer:** Displays the location where patient and appointment data are stored.

* **Card**: An entry in a List Panel.

[Back to Table of Contents](#table-of-contents)

<div style="page-break-after: always;"></div>

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

{: .alert .alert-info}
> :information_source: **Note:** These instructions only provide a starting point for testers to work on;
> testers are expected to do more *exploratory* testing.

[Back to Table of Contents](#table-of-contents)

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Open a terminal or command prompt, depending on your OS, and navigate to the folder where the jar file is located.

   1. Run the command `java -jar medibase3.jar`
        
        Expected: Shows the GUI with a set of sample contacts. The window size may not be optimal.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by running the command `java -jar medibase3.jar`
 
       Expected: The most recent window size and location is retained.

[Back to Table of Contents](#table-of-contents)

### Adding a patient

Adding a patient while all patients are being shown

   1. Prerequisites: List all patients using the `list` command. Multiple patients in the patient list. 

   2. Test case: `add n/John Doe i/S1234567Z g/M d/2002-12-12 p/98765432 e/johnd@example.com a/Orchard Road, Block 124, #02-01`
       
      Expected: A new patient with the details provided will be added to the patient list. A success message is shown with the added patient's details.
    
   3. Test case: `add n/John Doe i/S1234567Z`

      Expected: No patient is added to the patient list. An error message is shown with details of the error.

   4. Other incorrect add commands to try: `add`, `add S1234567Z`
      
      Expected: Similar to previous.

[Back to Table of Contents](#table-of-contents)

### Editing a patient

Editing an existing patient while all patients are being shown

   1. Pre-requisites: List all patients using the `list` command. Ensure there is at least one patient in the list.

   2. Test case `edit S1234567A p/98765432`

      Expected: The patient with the NRIC `S1234567A` will have its phone number updated to `98765432`. A success message is shown with the updated patient's details.

   3. Test case `edit S1234567 p/98765432`

      Expected: No patient is updated. An error message is shown with details of the error.

   4. Other incorrect edit commands to try: `edit`, `edit i/S1234567A` 
      
      Expected: Similar to previous.

[Back to Table of Contents](#table-of-contents)

### Deleting a patient

Deleting a patient while all patients are being shown

   1. Prerequisites: List all patients using the `list` command. Multiple patients in the list.

   2. Test case: `delete S1234567A`
   
      Expected: Patient with the NRIC `S1234567A` will be deleted and removed from the patient list. A success message is shown with the deleted patient's details.

   3. Test case: `delete S1234567`
   
      Expected: No patient is deleted from the patient list. An error message is shown with details of the error.

   4. Other incorrect delete commands to try: `delete`, `delete x`(where x is an `NRIC` that does not exist in the patient list)

      Expected: Similar to previous.

[Back to Table of Contents](#table-of-contents)

### Setting Priority for a patient

Setting a specified Priority for a patient

   1. Prerequisites: Ensure that a patient with the NRIC `S1234567A` is in the patient list.
    
   2. Test case: `setPriority i/S1234567A !/HIGH`

      Expected: Patient with the NRIC `S1234567A` will have its Priority Level set to `HIGH`. A success message is shown with the patient's NRIC.

   3. Test case: `setPriority`

      Expected: No Priority is set to any patient. An error message is shown with details of the error.

   4. Other incorrect setPriority command to try: `setPriority x` (where x is neither `NONE`, `LOW`, `MEDIUM` OR `HIGH`)

      Expected: Similar to previous.

[Back to Table of Contents](#table-of-contents)

### Finding a patient by their name

Finding a patient by providing keyword(s) from their name

   1. Prerequisites: List all patients using the `list` command. Multiple patients in the list.

   2. Test case: `find John`
   
      Expected: Patient(s) with the name containing the keyword `John` will be shown. A success message is shown with the patient(s) details.

   3. Test case: `find`

      Expected: No patient is found. An error message is shown with details of the error.

   4. Other incorrect find commands to try: `find i/x` (where x is a keyword that does not exist in any patient's name) 

      Expected: Similar to previous.

[Back to Table of Contents](#table-of-contents)

### Finding a patient by their NRIC

Finding a patient by providing their `NRIC`

   1. Prerequisites: List all patients using the `list` command. Ensure there is at least one patient in the list.

   2. Test case: `findNric S1234567A`

      Expected: Patient with the NRIC `S1234567A` will be shown. A success message is shown with the patient's details.

   3. Test case: `findNric S9999999Z`

      Expected: No patient is found. An error message is shown with details of the error.

   4. Other incorrect find commands to try: `findNric`, `findNric i/x` (where x is a patient nric) Expected: Similar to previous.

[Back to Table of Contents](#table-of-contents)

### Finding a patient by their medical condition

Finding a patient by providing keyword(s) from their medical condition

   1. Prerequisites: List all patients using the `list` command. Multiple patients in the list.

   2. Test case: `findMedCon diabetes`
   
      Expected: Patient(s) with the medical condition containing the keyword `diabetes` will be shown. A success message is shown with the patient(s) details.

   3. Test case: `findMedCon`
   
      Expected: No patient is found. An error message is shown with details of the error.

   4. Other incorrect find commands to try: `findMedCon x` (where x is a keyword that does not exist in any patient's medical condition)
      
      Expected: Similar to previous.

[Back to Table of Contents](#table-of-contents)

### Adding a medical condition to a patient

Adding a medical condition to an existing patient

   1. Prerequisites: List all patients using the `list` command. Ensure there is at least one patient in the list.

   2. Test case: `addMedCon i/S1234567A c/Diabetes`  
      
      Expected: The medical condition `Diabetes` is added to the patient with NRIC `S1234567A`. A success message is shown summarising which medical condition(s) have been added to which patient.

   3. Test case: `addMedCon c/Diabetes`  
      
      Expected: No medical condition is added. An error message is shown, indicating that the command format is incorrect.

   4. Other incorrect commands to try: `addMedCon`, `addMedCon i/S1234567A` 

      Expected: Similar to previous.

[Back to Table of Contents](#table-of-contents)

### Deleting a medical condition from a patient

Deleting an existing medical condition from a patient

   1. Prerequisites: List all patients using the `list` command. Ensure the patient has the added medical condition.

   2. Test case: `delMedCon i/S1234567A c/Diabetes`  
      
      Expected: The medical condition `Diabetes` is removed from the patient with NRIC `S1234567A`. A success message is shown summarising which medical condition(s) have been removed from which patient.

   3. Test case: `delMedCon i/S1234567A c/Hypertension`  
      
      Expected: No medical condition is deleted. An error message is shown, indicating that the specified medical condition does not exist for the patient.

   4. Other incorrect commands to try**: `delMedCon`, `delMedCon i/S1234567A` 

      Expected: Similar to previous.

[Back to Table of Contents](#table-of-contents)

### Adding an allergy to a patient

Adding an allergy to an existing patient

   1. Prerequisites: List all patients using the `list` command. Ensure there is at least one patient in the list.

   2. Test case: `addAllergy i/S1234567A al/Peanut`  
      
      Expected: The allergy `Peanut` is added to the existing patient with NRIC `S1234567A`. A success message is shown summarising which allergy/allergies have been added to which patient.

   3. Test case: `addAllergy i/S9999999Z al/Peanut`
      
      Expected: No allergy is added. An error message is shown, indicating that the specified patient does not exist.

   4. Other incorrect `addAllergy` commands to try: `addAllergy`, `addAllergy al/Peanut` 

      Expected: Similar to previous.

[Back to Table of Contents](#table-of-contents)

### Deleting an allergy from a patient

Deleting an existing allergy from a patient

   1. Prerequisites: List all patients using the `list` command. Ensure the patient has the added allergy.

   2. Test case: `delAllergy i/S1234567A al/Peanut`  
      
      Expected: The allergy `Peanut` is removed from the patient with NRIC `S1234567A`. A success message is shown summarising which allergy/allergies have been removed from which patient.

   3. Test case: `delAllergy i/S1234567A al/Dust`  
      
      Expected: No allergy is deleted. An error message is shown, indicating that the specified allergy does not exist for the patient.

   4. Other incorrect commands to try: `delAllergy`, `delAllergy i/S1234567A`  
      
      Expected: An error message is shown, indicating that the command format is incorrect.

[Back to Table of Contents](#table-of-contents)

### Listing patients by Priority

Listing patients with specified Priority

1. Test case: `listPrio !/high`

   Expected: Patient(s) with the Priority Level `HIGH` will be shown. A message is shown with the number of patients listed.

2. Test case: `listPrio`

   Expected: No patient is listed. An error message is shown with details of the error.

3. Other incorrect listPrio command to try: `listPrio x` (where x is neither `NONE`, `LOW`, `MEDIUM` OR `HIGH`)

   Expected: Similar to previous.

[Back to Table of Contents](#table-of-contents)

### Adding an appointment to a patient

Adding an appointment to an existing patient

1. Prerequisites: List all patients using the `list` command. Ensure there is at least one patient in the list.

2. Test case: `addAppt Physio i/S1234567A @t/1100-1230 @d/2024-05-19`

   Expected: The appointment `Physio` is added to the existing patient with NRIC `S1234567A`. A success message is shown summarising which appointment has been added to which patient. The new appointment details can be seen on both under the target patient in the patient list, and on the right in the appointment list.

3. Test case: `addAppt Physio i/S9999999A @t/1100-1230 @d/2024-05-19`

   Expected: No appointment is added. An error message is shown, indicating that the specified patient does not exist.

4. Test case: `addAppt i/S1234567A @t/1100-1230 @d/2024-05-19`

   Expected: Similar to previous, with error message related to the blank appointment name being invalid.

5. Test case: Enter `addAppt Physio i/S1234567A @t/1100-1230 @d/2024-05-19` twice. 

   Expected: First time successful, similar to (1), but subsequent tries similar to previous with error message related to the clashing appointment timings.

6. Other incorrect commands to try: `addAppt`, `addAppt Physio`, `addAppt i/S1234567A`, `addAppt @t/1100-1230`, `addAppt @d/2024-05-19`, `addAppt Physio @t/1100-1230 @d/2024-05-19`, `addAppt @t/1100-1230 @d/2024-05-19`, `addAppt Physio i/S1234567A @d/2024-05-19`, `addAppt Physio i/S1234567A @t/1100-1230`, `addAppt W i/X @t/Y @d/Z` where `W`, `X`, `Y` and `Z` are invalid parameter values.

    Expected: Similar to previous.

[Back to Table of Contents](#table-of-contents)

### Deleting an appointment from a patient

Deleting an existing appointment from a patient

1. Prerequisites: List all patients using the `list` command. Ensure the patient has an appointment for `2024-05-19` during `1100-1230` already added.

2. Test case: `delAppt i/S1234567A @t/1100-1230 @d/2024-05-19`

   Expected: The appointment at `2024-05-19` during `1100-1230` is removed from the patient with NRIC `S1234567A`. A success message is shown summarising which appointment has been removed from which patient. The appointment no longer shows up under the target patient in the patient list, as well as in the appointment list. 

3. Test case: `delAppt i/S1234567A @t/0000-1234 @d/2024-05-19`

   Expected: No appointment is deleted. An error message is shown, indicating that the specified appointment does not exist for the patient.

4. Other incorrect commands to try: `delAppt`, `delAppt i/S1234567A`, `delAppt i/S1234567A @t/1100-1230`, `delAppt i/S1234567A @d/2024-05-19`, , `delAppt @t/0000-1234 @d/2024-05-19`

   Expected: An error message is shown, indicating that the command format is incorrect.

[Back to Table of Contents](#table-of-contents)

<div style="page-break-after: always;"></div>

## **Appendix: Planned Enhancements**

Team size: 5

1\. **Enhance Name Validation**

   Currently, MediBase3 restricts patient names to alphanumerics and spaces only, preventing the inclusion of common symbols, hyphens, and accented characters that are often found in legal names (e.g., 'Nagaratnam s/o Suppiah', 'Anya Taylor-Joy', 'Sergio Pérez'). Additionally, the app allows leading, trailing, and multiple consecutive spaces, which can result in inconsistent formatting.

   This enhancement will:
   - Loosen restrictions to allow all other special and accented characters, enabling the accurate entry of a wider range of legitimate names.
   - Automatically trim any leading or trailing spaces and reduce multiple consecutive spaces to a single space, ensuring consistent formatting and reducing errors from accidental spacing during data entry.

   These changes will improve the inclusivity and data consistency of MediBase3's patient records.

2\. **Improve `addAppt` command to allow users to add multiple appointments at once**

  Currently, users can only add one appointment at a time using the `addAppt` command. We plan to enhance the `addAppt` command to allow users to add multiple appointments at once.
  This will be useful as it allows doctors to save time by adding multiple appointments for a patient in one go, instead of typing the same command multiple times.

3\. **Improve Email Validation**

  MediBase3 does not currently check if the email ends with a top-level domain (TLD) such as `.com` or `.org`. We plan to enhance the validation for email addresses to also check if the domain provided contains an actual top-level domain apart from the other existing constraints.

4\. **Allow partial addition and deletion of medical conditions and allergies**

  Currently, when adding or deleting multiple medical conditions or allergies with the `addMedCon`, `addAllergy`, `delMedCon`, or `delAllergy` commands, the entire command is rejected if any of the specified conditions or allergies already exist (in the case of `addMedCon` and `addAllergy`) or do not exist (in the case of `delMedCon` and `delAllergy`) for the patient. 
  This means that none of the conditions or allergies are processed, even if some are valid. 
  In the future, we plan to enhance these features by partially accepting the command—only rejecting the invalid entries and successfully adding or deleting the valid ones.

5\. **Enhance `find`, `findNric`, `findMedCon`, `listPrio` and `list` commands to update the Appointment List panel as well**

  Currently, commands that modify the Patient List panel do not update the Appointment List panel to show only the appointments of the currently listed patients. 
  Adding this feature would allow doctors to view the appointments of the visible patients in chronological order, rather than grouped under each patient's details.

6\. **Allow for foreign patients to be added to Medibase3**

  Currently, MediBase3 uses the patient's NRIC or FIN number as their unique identifier. However, this prevents doctors from adding foreign patients, who do not have a NRIC or FIN number, into Medibase3.
  As such, we plan on improving MediBase3 to allow doctors to add foreign patients by using other forms of identification, such as their passport number.

7\. **Make certain fields optional**

  Currently, all fields are mandatory when adding a patient. However, there could be cases where the patient does not have an email address or phone number.
  It would be ideal to make certain fields optional as it allows for more flexibility when adding patient details. However, the NRIC field should remain mandatory since it is the unique identifier for each patient which ensures that duplicate patients cannot be added.

8\. **Include Command Overview in Help Pop-Up**

  Currently, the pop-up that appears after using the `help` command only provides a link to the detailed user guide. Users are also required to copy the URL given, open a new browser and navigate away from the application to view the commands available.
  We plan to include a Command Overview in the pop-up that will display a list of all available commands and their respective constraints. This will allow users to quickly view the commands available without having to navigate away from the application.

9\. **Improve NRIC validation**

  The current NRIC validation used in MediBase3 does not align with Singapore's checksum algorithm for NRICs. As such, MediBase3 does not check if the starting letter and starting 2 digits of the NRIC aligns with the given date of birth.
  This allows users to enter either incorrect date of births or NRICs that do not align with each other. We plan to enhance the NRIC validation to check for such discrepancies, specifically for patients born on or after 1 January 1968 where [this](https://en.wikipedia.org/wiki/National_Registration_Identity_Card#Structure_of_the_NRIC_number/FIN) rule applies.

