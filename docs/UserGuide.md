---
layout: page
title: MediBase3 User Guide
---

MediBase3 (MB3) is a **desktop app for doctors to manage their patients and appointments, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, MB3 can get your tasks done faster than traditional GUI apps.

## Table of Contents

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
|`NAME` | Name of the patient | - Only alphanumeric characters are allowed.<br> - Should not be blank. <br> - Special characters are not allowed as `/` is used as a command delimiter. In the case where `s/o` should be used in a name, a simple workaround would be to use alternatives such as `s o` or `son of`| :white_check_mark:`John Doe`<br>:x:`$ally`|
|`NRIC` | Singapore National Registration Identity Card (NRIC) number of the patient. It is unique for all patients. | - Case-insensitive. <br> - Should not be blank. <br> - Should start with a letter (S, F, G or M), followed by 7 digits, and end with a letter. | :white_check_mark:`S1234567A` <br> :white_check_mark:`t1234567b` <br> :x: `1234567A` |
|`DOB` | Date of birth (DOB) of the patient. | - Should be in the format `YYYY-MM-DD`. <br> - Should not be blank. <br> - Cannot be a date in the future. | :white_check_mark:`2002-12-12` <br> :x:`2002/11/32` |
|`GENDER` | Gender of the patient. | - Case-insensitive. <br> - Should only be either `M` (Male) or `F` (Female). | :white_check_mark:`m`<br>:white_check_mark:`F`<br>:x:`Male` |
|`EMAIL` | Email address of the patient. | - Should be in the format `local-part@domain`. <br> - Should not be blank. | :white_check_mark:`techraj@gmail.com`<br>:x:`techraj@gmail` |
|`ADDRESS` | Address of the patient. | - Any value is allowed. <br> - Should not be blank. | :white_check_mark:`Orchard Road, Block 124, #02-01` |
|`PHONE_NUMBER` | Phone number of the patient. | - Should only contain numbers.<br> - Should be at least 3 digits long <br> - Should not be blank. <br> - Spaces and symbols are not allowed. | :white_check_mark:`98765432`<br>:x:`+65 9876 5432` |

[Back to Table of Contents](#table-of-contents)

{: .alert .alert-info}
> **:information_source: Notes about the command format:**<br>
>
> * Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
> e.g. in `add n/NAME i/NRIC g/GENDER d/DOB p/PHONE_NUMBER e/EMAIL a/ADDRESS`, `NAME` is a parameter which can be used as `n/John Doe`.
> 
> * Items in square brackets are optional.<br>
>  e.g `edit NRIC [n/NAME] [i/NRIC] [g/GENDER] [d/DOB] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]` can be used as `edit S1234567A n/John Lim g/M` or as `edit S1234567A g/M`.
>
> * Items with `…`​ after them can be used multiple times.<br>
>  e.g. `c/CONDITION…​` can be used as, `c/Knee Pain`, `c/Flu c/Fever` etc.
>
> * Parameters can be in any order for some commands.<br>
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
> * All fields are compulsory and must be non-empty.
> * A patient will not be added if the NRIC given is already associated with another patient in MediBase3. An error message will be displayed in this case. 
> * The new patient will be added to the end of the patient list in the GUI.
> * Refer to the [Parameter Details](#parameter-details) section for more information on the constraints of each parameter.

Examples:
* `add n/John Doe i/S1234567A g/M d/2002-12-12 p/98765432 e/johnd@example.com a/Orchard Road, Block 124, #02-01`
* `add n/Betsy Crowe i/s1234567b g/F e/betsycrowe@example.com a/Bukit Merah, Block 123, #01-01 p/1234567 d/2002-11-10`

{: .alert .alert-success}
> :bulb: **Tip:**
> 
> Made a mistake or a typo? You can use the [`edit` command](#editing-a-person--edit) to update the patient's details.

[Back to Table of Contents](#table-of-contents)

#### Deleting a patient : `delete`

Deletes the specified patient and their details from MediBase3.

Format: `delete NRIC`

{: .alert .alert-info}
> :information_source: **Note:**
> * Deletes the patient with the specified `NRIC`.
> * The `NRIC` provided must be the **full NRIC** of the patient to be deleted. E.g. `S1234567A` and not `S123`. 
> * You can only delete one patient at a time.
> * Refer to the [Parameter Details](#parameter-details) section for more information on the constraints for the `NRIC` parameter.

Examples:
* `delete S1234567A` will delete the patient with the NRIC `S1234567A`.

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

#### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

[Back to Table of Contents](#table-of-contents)

### General Features

#### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

[Back to Table of Contents](#table-of-contents)

#### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

[Back to Table of Contents](#table-of-contents)

#### Exiting the program : `exit`

Exits the program.

Format: `exit`

[Back to Table of Contents](#table-of-contents)

#### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

[Back to Table of Contents](#table-of-contents)

#### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

{: .alert .alert-warning}
> :exclamation: **Caution:**
> 
> If your changes to the data file makes its format invalid, MediBase3 will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.
>
> Furthermore, certain edits can cause the MediBase3 to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.

[Back to Table of Contents](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

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
