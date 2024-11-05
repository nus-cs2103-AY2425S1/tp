---
layout: page
title: User Guide
---

ClinicConnectSystem is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

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

   * `home` : Lists all patients.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the ClinicConnect system.

   * `delete T0123456A` : Deletes the patient with the NRIC "T0123456A" shown in the current list.

   * `clear` : Deletes all patients.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## ClinicConnect System Features

These features are designed to help you manage patient info seamlessly. Appointment info, patient details are centralized this platform.

### Legend
These boxes might offer some additional information of different types:

<box type="info" seamless>

**Important:**
Highlights important information that you should know.

</box>

<box type="tip" seamless>

**Tip:**
Provides you with tips to use our system more effectively.

</box>

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

* Extraneous parameters for commands that do not take in parameters (such as `help`, `home`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help [COMMAND_NAME]`
* Displays a small window showing more information
* Command name is optional as typing help alone will display the commands and their usages
* Typing command name will display the usages, parameter information and examples 

Examples: 
* `help`
* `help addf`

### Adding a patient: `add`

Adds a patient to the address book.

Format: `add n/NAME i/NRIC d/BIRTHDATE s/SEX p/PHONE_NUMBER`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A patient can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe i/S0123456A s/M d/1990-04-12 p/98765432`
* `add n/Betsy Crowe i/T1234567Z s/F d/2002-09-19 p/90914567`

### Listing all patients : `home`

Shows a list of all patients in the ClinicConnect System.

Format: `home`

### Editing a patient : `edit`

Edits an existing patient in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the patient at the specified `INDEX`. The index refers to the index number shown in the displayed patient list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the patient will be removed i.e adding of tags is not cumulative.
* You can remove all the patient’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st patient to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd patient to be `Betsy Crower` and clears all existing tags.


### Delete Appointment : `deleteappt`

Identifies the specific patient using NRIC and deletes the appointment specified.

Format: `deleteappt NRIC dt/APPOINTMENT DATE-TIME`

<box type="info" seamless>

**Important:** </br>

* NRIC provided must be a valid NRIC currently in the system.
* All parameters are compulsory.

</box>

Examples:
* `deleteappt T01234567A dt/2024-11-05 16:00`

### Filter appointments : `filter`

Filters existing patient records based on the specified parameters.

Format: `filter [sd/START DATE] ed/END DATE [h/HEALTH SERVICE]`

Start date and health service parameters are optional. 
End date parameter is compulsory.

When all parameters are specified, it returns all appointments from start-date to end-date which matches the specified health service.
When start date and end date are specified, it returns all appointments from start date to end date.
When end date and health service is specified, it returns all appointments from today's date to end date which matches the specified health service.
When end date is specified, it returns all appointments from today's date to end-date.

<box type="tip" seamless>

**Tip:** <br>

To retrieve appointments on a single date e.g. 2024/10/20, the user can input the same start and end date
`filter sd/2024-10-20 ed/2024-10-10`

</box>

Examples:
*  `filter sd/2012-10-01 ed/2012-11-01 h/blood test` filters the blood test appointments of patients from Oct 01 2012 to Nov 01 2012. 
*  `filter ed/2024-12-12 h/vaccination` filters the vaccination appointments from today's date to Dec 12 2024.
*  `filter sd/2012-10-01 ed/2012-11-01` filters all appointments from Oct 01 2012 to Nov 01 2012.

### View patients full profile: `view`

Identifies the specific patient using NRIC and shows the full profile of the patient.

Format: `view NRIC`

<box type="info" seamless>

**Important:** </br>

* NRIC provided must be a valid NRIC currently in the system.

</box>

Examples:
* `view T0123456A`

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

Deletes the patient with the specified NRIC from the address book.

Format: `delete NRIC`

* Deletes the patient at the specified `NRIC`.
* The NRIC refers to the nric of the patient shown in the displayed patient list.
* The NRIC **must be a valid NRIC number present in the system** 

### Clearing all entries : `clear`

Clears all patient entries and data in the current system.

Format: `clear`

### Exiting the program : `exit`

Exits the system and closes the window.

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
**Delete** | `delete S0123456Z`<br> e.g., `delete S0123456Z`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Home** | `home`
**Help** | `help`
