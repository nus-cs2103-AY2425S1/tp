---
layout: page
title: MediBase3 User Guide
---

MediBase3 (MB3) is a **desktop app for doctors to manage their patients and appointments, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, MB3 can get your tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

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
|`NAME` | Name of the patient | - Only alphanumeric characters are allowed.<br> - Special characters are not allowed as `/` is used as a command delimiter. In the case where `s/o` should be used in a name, a simple workaround would be to use alternatives such as `s o` or `son of`| :white_check_mark:`John Doe`<br>:x:`$ally`|
|`NRIC` | Singapore National Registration Identity Card (NRIC) number of the patient. It is unique for all patients. | - Case-insensitive. <br> - Should start with a letter (S, F, G or M), followed by 7 digits, and end with a letter. | :white_check_mark:`S1234567A` <br> :white_check_mark:`t1234567b` <br> :x: `1234567A` |
|`DOB` | Date of birth (DOB) of the patient. | - Must be in the format `YYYY-MM-DD`. <br> - Cannot be a date in the future. | :white_check_mark:`2002-12-12` <br> :x:`2002/11/32` |
|`GENDER` | Gender of the patient. | - Case-insensitive. <br> - Should only be either `M` (Male) or `F` (Female). | :white_check_mark:`m`<br>:white_check_mark:`F`<br>:x:`Male` |
|`EMAIL` | Email address of the patient. | Must be in the format `local-part@domain`. | :white_check_mark:`techraj@gmail.com`<br>:x:`techraj@gmail` |
|`ADDRESS` | Address of the patient. | Both alphanumeric and special characters are allowed. | :white_check_mark:`Orchard Road, Block 124, #02-01` |
|`PHONE_NUMBER` | Phone number of the patient. | - Should only contain numbers.<br> - Spaces and symbols are not allowed. | :white_check_mark:`98765432`<br>:x:`+65 9876 5432` |

[Back to Table of Contents](#table-of-contents)

{: .alert .alert-info}
> **:information_source: Notes about the command format:**<br>
>
> * Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
> e.g. in `add n/NAME i/NRIC g/GENDER d/DOB p/PHONE_NUMBER e/EMAIL a/ADDRESS`, `NAME` is a parameter which can be used as `n/John Doe`.
> * Items in square brackets are optional.<br>
>  e.g `edit NRIC [n/NAME] [i/NRIC] [g/GENDER] [d/DOB] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]` can be used as `edit S1234567A n/John Lim g/M` or as `edit S1234567A g/M`.
>
> * Items with `…`​ after them can be used multiple times.<br>
>  e.g. `c/CONDITION…​` can be used as, `c/Knee Pain`, `c/Flu c/Fever` etc.
>
> * Parameters can be in any order.<br>
> e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.
>
> * Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
> e.g. if the command specifies `help 123`, it will be interpreted as `help`.
>
> * The command name and prefixes are case-sensitive.<br>
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
> * All fields are compulsory.
> * A patient will not be added if the NRIC given is already associated with another patient in MediBase3. An error message will be displayed in this case. 
> * The new patient will be added to the end of the patient list in the GUI.
> * Refer to the [Parameter Details](#Parameter-Details) section for more information on the constraints of each parameter.

Examples:
* `add n/John Doe i/S1234567A g/M d/2002-12-12 p/98765432 e/johnd@example.com a/Orchard Road, Block 124, #02-01`
* `add n/Betsy Crowe i/s1234567b g/F e/betsycrowe@example.com a/Bukit Merah, Block 123, #01-01 p/1234567 d/2002-11-10`

{: .alert .alert-success}
> :bulb: **Tip:**
> 
> Made a mistake or a typo? You can use the [`edit` command](#editing-a-person--edit) to update the patient's details.

[Back to Table of Contents](#table-of-contents)

#### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

[Back to Table of Contents](#table-of-contents)

#### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

[Back to Table of Contents](#table-of-contents)

### Managing Appointments
[To be filled up]
### Managing Medical Conditions
[To be filled up]
### Managing Allergies
[To be filled up]
### Managing Priority
[To be filled up]
### Finding Patients

#### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

[Back to Table of Contents](#table-of-contents)

#### Locating patients by name: `find`

Finds patients whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

{: .alert .alert-info} 
> **:information_source: **Note:**
> 
> * The search is case-insensitive. e.g `hans` will match `Hans`
> * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
> * Only the name is searched.
> * Only full words will be matched e.g. `Han` will not match `Hans`
> * Patients matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
> * Returns an empty patient list panel if no matching patients with the given keywords are found.
>
> Examples:
> * `find John` returns `john` and `John Doe`
> * `find alex david` returns `Alex Yeoh`, `David Li`
>  ![result for 'find alex david'](images/findAlexDavidResult.png)

[Back to Table of Contents](#table-of-contents)

#### Locating patients by medical condition: `findMedCon`

Finds patients whose medical condition contain the given keywords.

Format: `findMedCon KEYWORD [MORE_KEYWORDS]`

{: .alert .alert-info}
> **:information_source: **Note:**
>
> * The search is case-insensitive. e.g `diabetes` will match `Diabetes`
> * The order of the keywords does not matter. e.g. `diabetes arthritis` will match `arthritis` and `diabetes` 
> * Only the medical condition is searched.
> * Only full medical condition will be matched e.g. `diabetes` will not match `diabete`
> * Patients matching at least one keyword will be returned (i.e. `OR` search).
>    e.g. `diabetes` will return `Alex Yeoh`, `David Li`
> * Returns an empty patient list panel if no matching patients with the given keywords are found.
>
> Examples:
> * `findMedCon diabetes arthritis` returns `Alex Yeoh` and `David Li`

[Back to Table of Contents](#table-of-contents)

#### Locating patients by NRIC: `findNric`

Finds patients whose names contain the given NRIC.

Format: `find NRIC`

{: .alert .alert-info}
> **:information_source: **Note:**
> * The search is case-insensitive. e.g `S1234567a` will match `S1234567A`
> * Only the NRIC is searched.
> * Only full NRIC will be matched e.g. `S1234567a` will not match `T12345`
> * Returns an empty patient list panel if no matching patients with the given `NRIC` are found.
> * Patients NRIC follows the NRIC constraint mentioned in the [Parameter Details](#Parameter-Details) section.
>
> Examples:
> * `findNric S1234567A` returns `Alex Yeoh`

[Back to Table of Contents](#table-of-contents)

### General Features

#### Command History

You can navigate between previous successful commands and your current command by pressing the `UP` and `DOWN` arrow keys.

#### Viewing help : `help`

Shows a message explaining how to access the help page.

Format: `help`

![help message](images/helpMessage.png)

[Back to Table of Contents](#table-of-contents)

#### Clearing all entries : `clear`

Clears all entries from the MediBase3. 

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

--------------------------------------------------------------------------------------------------------------------

## FAQ

### **Q**: How do I transfer my data to another Computer?
 - **A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous MediBase3 home folder.<br>

### **Q**: How do we open the command terminal?
 - **A**: Windows - Press `Win + R`, type `cmd` and press `Enter`.
 - **A**: Mac - Press `Cmd + Space`, type `terminal` and press `Enter`.
 - **A**: Linux - Press `Ctrl + Alt + T` to open the command terminal.

### **Q**: How to install Java 17
 - **A**: Download the Java 17 [here](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html). Then follow the [installation guide](https://docs.oracle.com/en/java/javase/17/install/overview-jdk-installation.html).

### **Q**: How to know if you have Java 17
 - **A**: Open your command terminal and enter `java --version`. The first line should display `java` followed by the `version number`.

### **Q**: Is there a limit to the number of patients/appointments we can add?
 - **A**: As of right now, We do not have a limit to the number of patients/appointments but is dependent on the hardware specification.

### **Q**: Do you support non-English inputs?
 - **A**: We do not support non-English inputs, but we are working on it!

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME i/NRIC g/GENDER d/DOB p/PHONE_NUMBER e/EMAIL a/ADDRESS` <br> e.g., `add n/John Doe i/S1234567A g/M d/2002-12-12 p/98765432 e/johnd@example.com a/Orchard Road, Block 124, #02-01`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`
