---
layout: page
title: User Guide
---

MediContacts is a **desktop app for managing contacts of patients and doctors**, as well as **keeping track of their appointments.**  

It is **optimised for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, MediContacts can not only get your contact management tasks done faster than traditional systems adopted by clinics, but also efficiently keep track of appointments between registered doctors and patients.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format (with examples)
--------|------------------
[**Help**](#viewing-help--help) | `help`
[**Add Doctor**](#adding-a-doctor--add-doctor) | `add-doctor n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS s/SPECIALTY [t/TAG]…​` <br><br> e.g. `add-doctor n/Jane Doe p/91234567 e/janedoe@example.com a/456 Clementi Ave 3 s/Cardiology t/colleague`
[**Add Patient**](#adding-a-patient--add-patient) | `add-patient n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS d/DATE_OF_BIRTH g/GENDER [t/TAG]…​` <br><br> e.g. `add-patient n/John Doe p/98765432 e/johndoe@example.com a/123 Sengkang Drive 4 d/23-04-1987 g/M`
[**Add Appointment**](#adding-an-appointment--add-appt) | `add-appt pn/PATIENT_NAME dn/DOCTOR_NAME d/DATE t/TIME` <br><br> e.g. `add-appt pn/John Doe dn/Jane Doe d/23-04-1987 t/1100`
[**List**](#listing-all-persons--list) | `list`
[**List Doctors**](#listing-all-doctors--list-doctor) | `list-doctor`
[**List Patients**](#listing-all-patients--list-patient) | `list-patient`
[**Find**](#finding-a-person-by-name--find) | `find KEYWORD [MORE_KEYWORDS]` <br><br> e.g. `find James Jake`
[**Find Doctor**](#finding-a-doctor-by-name--find-doctor) | `find-doctor KEYWORD [MORE_KEYWORDS]` <br><br> e.g. `find-doctor James Jake`
[**Find Patient**](#finding-a-patient-by-name--find-patient) | `find-patient KEYWORD [MORE_KEYWORDS]` <br><br> e.g. `find-patient James Jake`
[**Delete**](#deleting-a-person--delete) | `delete INDEX` <br><br> e.g. `delete 3`
[**Clear**](#clearing-all-entries--clear) | `clear`
[**Exit**](#exiting-the-program--exit) | `exit`

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-T09-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your MediContacts App.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar MediContacts.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `list-doctor` : Lists all doctors.
   
   * `list-patient` : Lists all patients.

   * `add-doctor n/John Doe p/98765432 e/johndoe@example.com a/123 Clementi Ave 3 s/Orthopedics` : Adds a doctor named John Doe to the Address Book.
   
   * `add-patient n/John Doe p/98765432 e/johndoe@example.com a/123 Clementi Ave 3 d/23-04-1987 g/M` : Adds a patient named John Doe to the Address Book.

   * `find John` : Finds contacts whose names contain the keyword `John`.

   * `find-doctor John` : Finds doctors whose names contain the keyword `John`.

   * `find-patient John` : Finds patients whose names contain the keyword `John`.

   * `delete 3` : Deletes the 3rd contact shown in the current list.
   
   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add-patient n/NAME`, `NAME` is a parameter which can be used as `add-patient n/John Doe`.

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

### Adding a doctor: `add-doctor`

Adds a doctor to the address book.

Format: `add-doctor n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS s/SPECIALTY [t/TAG]…​`

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
The parameter `NAME` only accepts alphabets and spaces. The app will reject the input if it contains any other characters.
</div>

**Examples:**
* `add-doctor n/John Doe p/98765432 e/johndoe@example.com a/123 Clementi Ave 3 s/Orthopedics`
* `add-doctor n/Jane Doe p/91234567 e/janedoe@example.com a/456 Clementi Ave 3 s/Cardiology t/colleague`

### Adding a patient: `add-patient`

Adds a patient to the address book.

Format: `add-patient n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS d/DATE_OF_BIRTH g/GENDER [t/TAG]…​`

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
The parameter `NAME` only accepts alphabets and spaces. The app will reject the input if it contains any other characters.
</div>

**Examples:**
* `add-patient n/John Doe p/98765432 e/johndoe@example.com a/123 Clementi Ave 3 d/23-04-1987 g/M`
* `add-patient n/Jane Doe p/91234567 e/janedoe@example.com a/456 Clementi Ave 3 d/01-05-1990 g/F t/friend`


### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Listing all doctors: `list-doctor`

Shows a list of all doctors in the address book.

Format: `list-doctor`

### Listing all patients: `list-patient`

Shows a list of all patients in the address book.

Format: `list-patient`

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
* Keywords provided must only consist of alphabets. Any other characters provided will give an invalid argument error.

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Locating doctors by name: `find-doctor`

Finds doctors whose names contain any of the given keywords.

Format: `find-doctor KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords matter. e.g. `Hans Bo` will not match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Only doctors matching all keywords provided will be returned (i.e. `AND` search).
  e.g. `Hans Bo` will not return `Hans Gruber`, `Bo Yang`
* Keywords provided must only consist of alphabets. Any other characters provided will give an invalid argument error.

**Examples:**
* `find-doctor John` returns `John Doe`.
* `find-doctor Alex Yeoh` returns `Alex Yeoh`.

### Locating patients by name: `find-patient`

Finds patients whose names contain any of the given keywords.

Format: `find-patient KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords matter. e.g. `Hans Bo` will not match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Patients matching all keywords provided will be returned (i.e. `AND` search).
  e.g. `Hans Bo` will not return `Hans Gruber`, `Bo Yang`
* Keywords provided must only consist of alphabets. Any other characters provided will give an invalid argument error.

**Examples:**
* `find-patient John` returns `John Doe`.
* `find-patient Alex Yeoh` returns `Alex Yeoh`.

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list-patient` followed by `delete 2` deletes the 2nd patient in the address book.
* `find-doctor Betsy` followed by `delete 1` deletes the 1st doctor in the results of the `find-doctor` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

MediContacts data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

MediContacts data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, MediContacts will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the app to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous MediContacts home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------


