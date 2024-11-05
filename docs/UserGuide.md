---
layout: page
title: User Guide
---

ClinicConnectSystem Level 3 (AB3) is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your ClinicConnectSystem.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar clinicconnectsystem.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the ClinicConnect system.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a patient: `add`

Adds a new patient record into the system.

Format: `add n/NAME i/NRIC s/SEX d/DATE_OF_BIRTH p/PHONE_NO`


<div markdown="block" class="alert alert-info">
**:information_source: Important:**<br>
Each person should have a unique NRIC.
ClinicConnect does not allow two patients with the same NRIC to exist in the system.
</div>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Use this command to quickly add a patient with only the required information.
</div>


Example: `add n/Abraham Tan i/S9758366N s/M d/1997-10-27 p/87596666`
<br>
Adds a patient Abraham Tan with his NRIC, Sex, Date-of-Birth and Phone.

For more information on each individual parameter click here

### Adding a patient with additional information: `addf`

Adds a new patient record into the system with additional information.

Format: `add n/NAME i/NRIC s/SEX d/DATE_OF_BIRTH p/PHONE_NO [e/EMAIL] [a/ADDRESS] [b/BLOOD_TYPE] 
[nokn/NEXT_OF_KIN_NAME] [nokp/NEXT_OF_KIN_PHONE] [al/ALLERGY]... [rl/RISK_LEVEL] [ec/EXISTING_CONDITIONS] [no/NOTES]`


<div markdown="block" class="alert alert-info">
**:information_source: Important:**<br>
Each person should have a unique NRIC.
ClinicConnect does not allow two patients with the same NRIC to exist in the system.
</div>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Use this command if you want to add a patient with additional information in addition to the required fields
(NAME, NRIC, SEX, DATE_OF_BIRTH, PHONE_NO)
</div>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Multiple allergies can be added using amultiple "al/" prefix
</div>

Examples:
<ol>
<li>`addf n/Abraham Tan i/S9758366N s/M d/1997-10-27 p/87596666
e/abramhamtan@gmail.com a/Blk 123, NUS Road, S123123 b/A+ nokn/Licoln Tan nokp/91234567
al/nuts al/shellfish rl/HIGH ec/Diabetes no/Patient needs extra care`
<ul>
<li>
Adds a patient Abraham Tan with his NRIC, Sex, Date-of-Birth, Phone, Email, Address, Blood Type,
Next-of-Kin Name, Next-of-Kin Phone, Risk Level, Existing Conditions, Notes and his two allergies.
</li>
</ul>
</li>
</ol>
<ol start="2">
<li>
`addf n/Lincoln Park i/S9751269M s/M d/1980-04-01 p/87296619
  e/linkinpark@gmail.com a/Blk 516, Clementi Road, S661836 b/AB- al/wheat`
</li>
<ul>
<li>
Adds a patient Lincoln Park with his NRIC, Sex, Date-of-Birth, Phone, Email, Address, Blood Type,
and his allergy.
</li>
</ul>
</ol>
For more information on each individual parameter click here

### Editing a patient: `edit`

Edit the information of an existing patient in the system.

Format: `edit NRIC [n/NAME] [i/NRIC] [s/SEX] [d/DATE_OF_BIRTH] [p/PHONE_NO] [e/EMAIL] [a/ADDRESS] [b/BLOOD_TYPE]
[nokn/NEXT_OF_KIN_NAME] [nokp/NEXT_OF_KIN_PHONE] [al/ALLERGY]... [rmal/ALLERGY_TO_BE_REMOVED]... [rl/RISK_LEVEL]
[ec/EXISTING_CONDITIONS] [no/NOTES]`


<div markdown="block" class="alert alert-info">
**:information_source: Important:**<br>
<ul>
<li>
NRIC provided must be a valid NRIC currently in the system
</li>
<li>
Input must contain at least one parameter to be edited
</li>
<li>
Not all parameters are compulsory
</li>
</ul>
</div>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Multiple allergies can be removed using multiple "rmal" prefixes. Ensure allergies to be removed
are current allergies of the patient
</div>


Examples:
<ol>
<li>`edit S9758366N n/Abraham Lee d/1997-10-28
<ul>
<li>
Edits the name and date-of-birth of the patient with NRIC S9758366N.
</li>
</ul>
</li>
</ol>
<ol start="2">
<li>
`edit S9758366N p/91234123 a/Blk 918A, Pasir Ris Drive, #13-102, Singapore 911918`
</li>
<ul>
<li>
Edits the phone and address of the patient with NRIC S9758366N.
</li>
</ul>
</ol>
For more information on each individual parameter click here

### Listing all patients : `list`

Shows a list of all patients in the address book.

Format: `list`

### Locating patients by name: `find`

Finds patients whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Patients matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a patient : `delete`

Deletes the specified patient from the address book.

Format: `delete INDEX`

* Deletes the patient at the specified `INDEX`.
* The index refers to the index number shown in the displayed patient list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd patient in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st patient in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

ClinicConnectSystem data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

ClinicConnectSystem data are saved automatically as a JSON file `[JAR file location]/data/clinicconnectsystem.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, ClinicConnectSystem will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the ClinicConnectSystem to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous ClinicConnectSystem home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`
