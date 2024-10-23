---
layout: page
title: MediBase3 User Guide
---

MediBase3 (MB3) is a **desktop app for doctors to manage their patients and appointments, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, MB3 can get your tasks done faster than traditional GUI apps.

## Table of Contents

* Table of Contents
{:toc}

## How to use this User Guide
Our user guide is tailored to ensure you can fully unlock the potential of MediBase3, whether you're new to MediBase3 or have been using the application for quite some time.
In this section, we aim to provide you with the necessary tools to effectively navigate and utilize the guide.

### Navigating the User Guide
Each aspect of MediBase3 is split into different sections, which are accessible via the Table of Contents above.

**For new MediBase3 users:**
1. We **recommend** that you finish reading this section to better understand the format of the user guide.
1. Once you are done, do head to the [Quick Start](#quick-start) section to get started with setting up MediBase3.

**For experienced MediBase3 users:**
1. If you need a quick refresher on how to use a specific feature in MediBase3, you can navigate to the [Features](#features) section.
1. You may also skip to the [Command Summary](#command-summary) section for a quick overview of all the commands available in MediBase3 and their formats.

For any additional information or queries, you can refer to the [FAQ](#faq) section or the [Known Issues](#known-issues) section.

### Glossary
As you read the user guide, you might encounter some unfamiliar technical terms. The table below provides the definitions for the key terms we will be using throughout the guide.

Term | Definition
---- | ----------
Command |An input that is given to the application to perform a specific action. Commands are typically entered via the command box in the application.
CLI | Command Line Interface(CLI) is a type of text-based interface that is used to interact with software via commands.
GUI | Graphical User Interface(GUI) is a type of interface that allows users to interact with electronic devices through graphical icons and visual indicators.
JAR | Java ARchive(JAR) is a package file format typically used to aggregate many Java class files and associated metadata and resources into one file for distribution.
JSON | JavaScript Object Notation(JSON) is a lightweight data-interchange format that is easy for humans to read and write and easy for machines to parse and generate.
Parameter | A parameter refers to a field that requires input from the user. For example, in the command `delete i/S1234567A`, `i/S1234567A` is a parameter.
Prefix | A prefix is a keyword that is used to identify the type of parameter that follows it. For example, in the command `delete i/S1234567A`, `i/` is the prefix for the NRIC parameter.


### Text Formatting Conventions
We will be using different text formatting styles to help you better understand the content of the user guide.

Format | Description
------ | -----------
[hyperlink](#how-to-use-this-user-guide) | Blue hyperlinks are used to bring you to external websites or another section of the User Guide for more information.
**Bold** | Bold text is used to highlight important information or key points.
`Monospace` | Text with a monospace font and a gray background is used to represent commands, parameters, code snippets and other technical terms.

### Annotated Text-Box Conventions
The following annotated text-boxes are used throught this guide to provide useful insights on MediBase3 and its features:

{: .alert .alert-info}
> :information_source: **Note:**
> 
> Provides additional information about MediBase3 that you should be aware of.

{: .alert .alert-success}
> :bulb: **Tip:**
> 
> Provides helpful tips or suggestions to improve your experience with MediBase3.

{: .alert .alert-warning}
> :exclamation: **Caution:**
> 
> Warns you about potential issues or errors that you might encounter while using MediBase3.

[Back to Table of Contents](#table-of-contents)

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.
1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).
1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.
1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` 
   command to run the application.asd

    {: .alert .alert-secondary}
    > A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.
    > 
    > ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will 
   open the help window.

    {: .alert .alert-secondary}
   > Some example commands you can try:
   > * `list` : Lists all contacts.
   > * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` tothe Address Book.
   > * `delete 3` : Deletes the 3rd contact shown in the current list.
   > * `clear` : Deletes all contacts.
   > * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

[Back to Table of Contents](#table-of-contents)
## Features

### Parameter Details
The table below provides a brief explanation of each parameter associated with a patient in MediBase3. It also details
the constraints of each parameter when used in a command.

{: .alert .alert-warning}
> :exclamation: **Caution:** 
>
> Ensure that all parameters adhere to the constraints mentioned below. 
> Otherwise, the command will not be executed, and an error message will be displayed.

|Parameter | Definition | Constraints | Examples |
|-|-|-|
|`NAME` | Name of the patient | - Only alphanumeric characters are allowed.<br> - Should not be blank. <br> - Special characters are not allowed as `/` is used as a command delimiter. In the case where `s/o` should be used in a name, a simple workaround would be to use alternatives such as `s o` or `son of`| :white_check_mark:`John Doe`<br>:x:`$ally`|
|`NRIC` | Singapore National Registration Identity Card (NRIC) number of the patient. It is unique for all patients. | - Case-insensitive. <br> - Should not be blank. <br> - Should start with a letter (S, F, G or M), followed by 7 digits, and end with a letter. | :white_check_mark:`S1234567A` <br> :white_check_mark:`t1234567b` <br> :x: `1234567A` |
|`DOB` | Date of birth (DOB) of the patient. | - Should be in the format `YYYY-MM-DD`. <br> - Should not be blank. <br> - Cannot be a date in the future. | :white_check_mark:`2002-12-12` <br> :x:`2002/11/32` |
|`GENDER` | Gender of the patient. | - Case-insensitive. <br> - Should only be either `M` (Male) or `F` (Female). | :white_check_mark:`m`<br>:white_check_mark:`F`<br>:x:`Male` |
|`EMAIL` | Email address of the patient. | - Should be in the format `local-part@domain`. <br> - Should not be blank. | :white_check_mark:`raj@gmail.com`<br>:x:`raj@gmail` |
|`ADDRESS` | Address of the patient. | - Any value is allowed. <br> - Should not be blank. | :white_check_mark:`Orchard Road, Block 124, #02-01` |
|`PHONE_NUMBER` | Phone number of the patient. | - Should only contain numbers.<br> - Should be at least 3 digits long <br> - Should not be blank. <br> - Spaces and symbols are not allowed. | :white_check_mark:`98765432`<br>:x:`+65 9876 5432` |
|`PRIORITY`  | Priority of the patient. | - Should only contain `NONE`, `LOW`, `MEDIUM` or `HIGH`. <br> - Case-insensitive. | :white_check_mark:`NONE` <br> :white_check_mark:`high` <br> :x: `Highpriority` |
|`CONDITION`| Medical Condition of the patient. | - Should contain only alphabets or alphanumerics. <br> - It must be no more than 30 characters. | :white_check_mark: `Arthritis` <br> :x: `@highbloodpressure` <br> :x: `abcdefghijklmnopqrstuvwxyzabcde` |

[Back to Table of Contents](#table-of-contents)

{: .alert .alert-info}
> **:information_source: Notes about the command format:**
> 
> * Words in `UPPER_CASE` are the parameters to be supplied by the user.
> e.g. in `add n/NAME i/NRIC g/GENDER d/DOB p/PHONE_NUMBER e/EMAIL a/ADDRESS`, `NAME` is a parameter which can be used as `n/John Doe`.
>
> * Items in square brackets are optional.
>  e.g `edit NRIC [n/NAME] [i/NRIC] [g/GENDER] [d/DOB] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]` can be used as `edit S1234567A n/John Lim g/M` or as `edit S1234567A g/M`.
>
> * Items with `…` after them can be used multiple times.
>  e.g. `c/CONDITION…` can be used as, `c/Knee Pain`, `c/Flu c/Fever` etc.
>
> * Parameters that have a prefix can be in any order.
> e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.
>
> * Parameters that have no prefix must follow the specified order in the command format.
> e.g. if the command specifies `NRIC n/NAME p/PHONE_NUMBER`, `NRIC` must take precedence over `n/NAME` and `p/PHONE_NUMBER`.
> 
> * Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.
> e.g. if the command specifies `help 123`, it will be interpreted as `help`.
>
> * The command name and prefixes are case-sensitive.
> e.g. `add` is not the same as `Add`, `c/` is not the same as `C/`.
>
> * If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

### Managing Patient

#### Adding a patient: `add`

Adds a patient and their relevant details to MediBase3.

Format: `add n/NAME i/NRIC g/GENDER d/DOB p/PHONE_NUMBER e/EMAIL a/ADDRESS`

{: .alert .alert-info}
> :information_source: **Note:**
> 
> * All fields are compulsory and must be non-empty.
> * A patient will not be added if the NRIC given is already associated with another patient in MediBase3. An error message will be displayed in this case.
> * The new patient will be added to the end of the Patient List Panel.
> * Refer to the [Parameter Details](#parameter-details) section for more information on the purpose and constraints of each parameter.

Examples:
* `add n/John Doe i/S1234567A g/M d/2002-12-12 p/98765432 e/johnd@example.com a/Orchard Road, Block 124, #02-01`
* `add n/Betsy Crowe i/s1234567b g/F e/betsycrowe@example.com a/Bukit Merah, Block 123, #01-01 p/1234567 d/2002-11-10`

{: .alert .alert-success}
> :bulb: **Tip:**
> 
> * Remember that `NRIC` and `GENDER` are case-insensitive!
> E.g. `i/s1234567a` and `i/S1234567A` are both equivalent.
> * Made a mistake or a typo? You can use the [`edit` command](#editing-a-person--edit) to update the patient's details.

[Back to Table of Contents](#table-of-contents)

#### Deleting a patient : `delete`

Deletes the specified patient and their details from MediBase3.

Format: `delete NRIC`

{: .alert .alert-info}
> :information_source: **Note:**
> 
> * Deletes the patient with the specified `NRIC` in MediBase3.
> * The `NRIC` provided must be the **full NRIC** of the patient to be deleted. e.g. `S1234567A` and not `S123`.
> * You can delete a patient even if they are not being currently displayed in the Patient List Panel.
> * `NRIC` must adhere to the constraints mentioned in the [Parameter Details](#Parameter-Details) section.

Example:
* `delete S1234567A` will delete the patient with the NRIC `S1234567A`.

{: .alert .alert-warning}
> :exclamation: **Caution:**
> 
> * Once a patient is deleted from MediBase3, you will be **unable to recover their information**.
> Please ensure that you have provided the correct `NRIC` of the patient that you want to delete.
> * Deleting a patient will also remove all their associated appointments from the Appointment List Panel.

[Back to Table of Contents](#table-of-contents)

#### Editing a patient : `edit`

Edits an existing patient details in MediBase3.

Format: `edit NRIC [n/NAME] [i/NRIC] [g/GENDER] [d/DOB] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]`

{: .alert .alert-info}
> :information_source: **Note:**
> 
> * Edits the patient with the specified `NRIC` in MediBase3.
> * **At least one** of the optional fields must be provided. e.g. `edit S1234567A` is invalid.
> * Existing values will be updated to the given input values.
> * You can edit a patient's details even if they are not being currently displayed in the Patient List Panel.
> * Refer to the [Parameter Details](#parameter-details) section for more information on the purpose and constraints of each parameter.

Example:
*  `edit S1234567A p/91234567 e/johndoe@example.com` will edit the phone number and email address of the patient with the NRIC`S1234567A`
to `91234567` and `johndoe@example.com` respectively.

{: .alert .alert-success}
> :bulb: **Tip:**
> 
> Editing the patient's `NAME` or `NRIC` will also update their associated appointments in the Appointment List Panel to reflect the new change.

[Back to Table of Contents](#table-of-contents)


### Managing Appointments
[To be filled up]
### Managing Medical Conditions

#### Adding Medical Conditions : `addMedCon`

Adds medical condition to an existing patient in MediBase3.

Format: `addMedCon i/NRIC c/CONDITION...`

{: .alert .alert-info}
> :information_source: **Note:**
>
> * Adds Medical Condition to the patient with the specified `NRIC` in MediBase3.
> * You can add a medical condition to a patient even if they are not being currently displayed in the Patient List Panel but doing so will refresh the panel to display all patients after medical condition has been added.
> * Refer to the [Parameter Details](#parameter-details) section for more information on the purpose and constraints of each parameter.

Example: 
* `addMedCon i/S1234567A c/High Blood Pressure` will add medical condition `High Blood Pressure` to patient with NRIC `S1234567A`.   

{: .alert .alert-success}
> :bulb: **Tip:**
>
> User can add more than 1 Medical Condition through using `c/CONDITION` multiple times:
> `addMedCon i/S1234567C c/High Blood Pressure c/Osteoporosis`

[Back to Table of Contents](#table-of-contents)

#### Deleting Medical Conditions : `delMedCon`

Deletes Medical Condition from an existing patient in MediBase3.

Format: `delMedCon i/NRIC c/CONDITION...`

{: .alert .alert-info}
> :information_source: **Note:**
>
> * Deletes Medical Condition from the patient with the specified `NRIC` in MediBase3.
> * You can delete a Medical Condition from a patient even if they are not being currently displayed in the Patient List Panel but doing so will refresh the panel to display all patients after medical condition has been removed.
> * Patient must have the Medical Condition in order to be able to be deleted, else an error message will show.
> * Refer to the [Parameter Details](#parameter-details) section for more information on the purpose and constraints of each parameter.

Example:
* `delMedCon i/S1234567A c/High Blood Pressure` will delete medical condition `High Blood Pressure` from patient with NRIC `S1234567A`.

{: .alert .alert-success}
> :bulb: **Tip:**
>
> User can delete more than 1 Medical Condition through using `c/CONDITION` multiple times:
> `delMedCon i/S1234567C c/High Blood Pressure c/Osteoporosis`

[Back to Table of Contents](#table-of-contents)

### Managing Allergies
[To be filled up]
### Managing Priority

#### Setting Priority : `setPriority`

Sets Priority to an existing patient in MediBase3.

Format: `setPriority i/NRIC !/PRIORITY`

{: .alert .alert-info}
> :information_source: **Note:**
>
> * Sets Priority to the patient with the specified `NRIC` in MediBase3.
> * You can set Priority to a patient even if they are not being currently displayed in the Patient List Panel but doing so will refresh the panel to display all patients after Priority has been set.
> * On default Patient has been set to `NONE` Priority level.
> * Refer to the [Parameter Details](#parameter-details) section for more information on the purpose and constraints of each parameter.

Example: 
* `setPriority i/S1234567A !/HIGH` will set the Priority of patient with NRIC `S1234567A` to `HIGH`.

[Back to Table of Contents](#table-of-contents)

### Finding Patients

{: .alert .alert-info}
> :information_source: **Note:**
> 
> * The following commands in this section will alter the view of the Patient List Panel to display only patients that match the given criteria.
> * They will not alter the view of the Appointment List Panel.

#### Listing all patients : `list`

Shows a list of all patients in MediBase3.

Format: `list`

{: .alert .alert-success}
> :bulb: **Tip:**
>
> If you have used any other commands under the [Finding Patients](#finding-patients) section to alter the view of the Patient List Panel, 
> you can use this command to reset to the default view to view all patients!

[Back to Table of Contents](#table-of-contents)

#### Listing all patients by their priority: `list`

Shows a list of all patients with a specific `PRIORITY` in MediBase3.

Format: `listPrio !/PRIORITY`

{: .alert .alert-info}
> :information_source: **Note:**
> 
> * An empty Patient List Panel will be displayed if no patients with the given `PRIORITY` are found.
> * `PRIORITY` must adhere to the constraints mentioned in the [Parameter Details](#Parameter-Details) section. 

Example: 
* `listPrio !/High` will display all patients with the priority `HIGH`.

[Back to Table of Contents](#table-of-contents)

#### Locating patients by name: `find`

Finds patients whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

{: .alert .alert-info} 
> :information_source: **Note:**
> 
> * The search is case-insensitive. e.g `hans` will match `Hans`
> * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
> * Only the name is searched.
> * Only **full words** will be matched e.g. `Han` will not match `Hans`
> * Patients matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
> * Returns an empty patient list panel if no matching patients with the given keywords are found.

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`

![result for 'find alex david'](images/findAlexDavidResult.png)

[Back to Table of Contents](#table-of-contents)

#### Locating patients by medical condition: `findMedCon`

Finds patients whose medical condition(s) contain the given keywords.

Format: `findMedCon KEYWORD [MORE_KEYWORDS]`

{: .alert .alert-info}
> :information_source: **Note:**
>
> * The search is case-insensitive. e.g `diabetes` will match `Diabetes`
> * The order of the keywords does not matter. e.g. `diabetes arthritis` will match `arthritis` and `diabetes` 
> * Only the medical condition is searched.
> * Only **full words** will be matched e.g. `diabetes` will not match `diabete`
> * Patients matching at least one keyword will be returned (i.e. `OR` search).
>    e.g. `diabetes` will return `Alex Yeoh`, `David Li`
> * Returns an empty Patient List Panel if no matching patients with the given keywords are found.

Example:
 * `findMedCon diabetes arthritis` returns `Alex Yeoh` and `David Li`

[Back to Table of Contents](#table-of-contents)

#### Locating patient by NRIC: `findNric`

Finds patients based on their NRIC.

Format: `find NRIC`

{: .alert .alert-info}
> :information_source: **Note:**
> 
> * The search is case-insensitive. e.g `S1234567a` will match `S1234567A`
> * Only the `NRIC` is searched.
> * Only **full NRIC** will be matched e.g. `S1234567a` will not match `S12345`
> * Returns an empty Patient List Panel if no matching patients with the given `NRIC` are found.
> * `NRIC` must adhere to the constraints mentioned in the [Parameter Details](#Parameter-Details) section.

Example:
* `findNric S1234567A` returns `Alex Yeoh`

[Back to Table of Contents](#table-of-contents)

### General Features

#### Command History

You can navigate between previous successful commands and your current command by pressing the `UP` and `DOWN` arrow keys.

#### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

![help message](images/helpMessage.png)

{: .alert .alert-success}
> :bulb: **Tip:**
> 
> You can also access this message by clicking on the `Help` button or by pressing `F1` on your keyboard.

[Back to Table of Contents](#table-of-contents)

#### Clearing all entries : `clear`

Clears all entries from the MediBase3 database. 

Format: `clear`

{: .alert .alert-warning}
> :exclamation: **Caution:**
>
> Using the clear command will **REMOVE** all your patient and appointment data from MediBase3. This action is irreversible.

[Back to Table of Contents](#table-of-contents)

#### Exiting the program : `exit`

Exits the program.

Format: `exit`

[Back to Table of Contents](#table-of-contents)

#### Saving the data

MediBase3 data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

[Back to Table of Contents](#table-of-contents)

#### Editing the data file

MediBase3 data are saved automatically as a JSON file `[JAR file location]/data/MediBase3.json`. Advanced users are welcome to update data directly by editing that data file.

{: .alert .alert-warning}
> :exclamation: **Caution:**
> 
> If your changes to the data file makes its format invalid, MediBase3 will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.
>
> Furthermore, certain edits can cause the MediBase3 to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.

[Back to Table of Contents](#table-of-contents)

## FAQ

#### **Q**: How do I transfer my data to another Computer?
 - **A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous MediBase3 home folder.<br>

#### **Q**: How do we open the command terminal?
 - **A**: Windows - Press `Win + R`, type `cmd` and press `Enter`.
 - **A**: Mac - Press `Cmd + Space`, type `terminal` and press `Enter`.
 - **A**: Linux - Press `Ctrl + Alt + T` to open the command terminal.

#### **Q**: How to install Java 17
 - **A**: Download the Java 17 [here](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html). Then follow the [installation guide](https://docs.oracle.com/en/java/javase/17/install/overview-jdk-installation.html).

#### **Q**: How to know if you have Java 17
 - **A**: Open your command terminal and enter `java --version`. The first line should display `java` followed by the `version number`.

#### **Q**: Is there a limit to the number of patients/appointments we can add?
 - **A**: As of right now, We do not have a limit to the number of patients/appointments but is dependent on the hardware specification.

#### **Q**: Do you support non-English inputs?
 - **A**: We do not support non-English inputs, but we are working on it!

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.


## Command summary


| Action     | Format                                                                | Examples                                                                                                       |
|------------|-----------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------|
| **Add**    | `add n/NAME i/NRIC g/GENDER d/DOB p/PHONE_NUMBER e/EMAIL a/ADDRESS`   | `add n/John Doe i/S1234567A g/M d/2002-12-12 p/98765432 e/johnd@example.com a/Orchard Road, Block 124, #02-01` |
| **Clear**  | `clear`                                                               | -                                                                                                              |
| **Delete** | `delete NRIC`                                                         | `delete S1234567A`                                                                                             |
| **Edit**   | `edit NRIC [n/NAME] [i/NRIC] [g/GENDER] [d/DOB] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]` | `edit S1234567A p/91234567 e/johndoe@example.com`                                                              |
| **Find**   | `find KEYWORD [MORE_KEYWORDS]`                                        | `find James Jake`                                                                                              |
| **FindNric**| `findNric NRIC`| `findNric S1234567A`                                                                                           |                                                                                                                                              |
| **FindMedCon**| `findMedCon KEYWORD [MORE_KEYWORDS]` | `findMedCon diabetes arthritis`                                                                                |                                                                                                           |
| **List**   | `list`                                                                | -                                                                                                              |
| **ListPrio**| `listPrio !/PRIORITY` | `listPrio !/High`                                                                                              |
| **Help**   | `help`                                                                | -                                                                                                              |




