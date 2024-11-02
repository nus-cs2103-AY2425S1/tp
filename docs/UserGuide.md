---
layout: page
title: User Guide
---

MedDict is a **desktop application designed for physiotherapists** who manage a large number of patients. It simplifies data entry and retrieval, allowing quick access to contact details, patient conditions, and the progress of recurring or long-term patients. The application supports a streamlined process for tracking medical history, patient appointments, and treatment progress.

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

### Creating and adding a doctor : `createDoctor`

Create and add doctor to the MedDict database in the address book.

Format: `createDoctor [n/Name] [p/Phone] [e/Email] [a/Address] `

* Create a doctor with given details and add the doctor to the MedDict database in the address book.
* Names should only contain alphanumeric characters and spaces, and it should not be blank.
* Phone numbers should only contain numbers, and it should be at least 3 digits long.
* Emails must follow the format 'local-part@domain', where the local part contains only alphanumeric characters and certain special characters,
  but cannot start or end with these special characters (+_.-). The domain consists of labels separated by periods,
  ending with a label at least two characters long, with each label starting and ending with an alphanumeric character and allowing hyphens in between.
* The doctors with same name and phone/email are considered as duplicated which is not allowed.
* A **notifying message** will be output if there is failure in creating doctor.

Examples:
* `createDoctor n/Dr Jane p/87654321 e/dr.jane.smith@hospital.com a/456 Elm Street r/physiotherapy` <br>
  Successfully created a new doctor Doctor#01 : Dr Jane Smith; Phone: 87654321; Email: dr.jane.smith@hospital.com; Address: 456 Elm Street; Tags: Specialist in physiotherapy

* `createDoctor n/Dr Jane p/87654321 e/dr.jane.smith@hospital.com a/456 Elm Street r/physiotherapy` <br>
  This doctor already exists


### Creating and adding a patient : `createPatient`

Create and add patient to the MedDict database in the address book.

Format: `createPatient [n/Name] [p/Phone] [e/Email] [a/Address] `

* Create a patient with given details and add the patient to the MedDict database in the address book.
* Names should only contain alphanumeric characters and spaces, and it should not be blank.
* Phone numbers should only contain numbers, and it should be at least 3 digits long.
* Emails must follow the format 'local-part@domain', where the local part contains only alphanumeric characters and certain special characters,
  but cannot start or end with these special characters (+_.-). The domain consists of labels separated by periods,
  ending with a label at least two characters long, with each label starting and ending with an alphanumeric character and allowing hyphens in between.
* The patient with same name and phone/email are considered as duplicated which is not allowed
* A **notifying message** will be output if there is failure in creating patient.

Examples:
* `createPatient n/Dr Jane p/87654321 e/dr.jane.smith@hospital.com a/456 Elm Street r/physiotherapy` <br>
  Successfully created a new patient Patient#00 : John Doe; Phone: 98765432; Email: johndoe@example.com; Address: 123 Baker Street; Tags: No known allergies

* `createPatient n/Dr Jane p/87654321 e/dr.jane.smith@hospital.com a/456 Elm Street r/physiotherapy` <br>
  This patient already exists

### Deleting a patient : `deletePatient`

Deletes the specified person from the MedDict database in the address book.

Format: `deletePatient [z/PatientId]`

* Delete the patient with the specified `PatientId`
* The patient id and doctor id **must be valid and present in MedDict database in address book**.
* The patient id **must be a positive integer and even number** 0, 2, 4, …​
* A **notifying message** will be output if there is failure in deleting patient.

Examples:
* `deletePatient 00` <br>
  Successfully deleted a patient.
* `deletePatient 02` <br>
  Unable to delete a patient, check the id entered!

### Add note to a patient : `addNotes`
 
Add notes to an existing patient in the MedDict database in address book.

Format: `addNotes [z/PatientId] [r/Notes]`

* Add notes to a patient with the specified `PatientId`.
* The patient id **must be valid and present in MedDict database in address book**.
* The patient Id **must be a positive integer and even number** 0, 2, 4, …​
* A **notifying message** will be output if there is failure in adding notes to the patient.

Examples:
*  `addNotes z/0 r/cancer` <br>
   Successfully added notes: cancer to patient of ID: 00.
*  `addNotes z/0 r/cancer` <br>
   Unable to add notes! Check the id entered!

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit Id [n/Name] [p/Phone] [e/Email] [a/Address] [t/tag]…​`

* Edits the person with the specified `Id`.
* The patient id and doctor id **must be valid and present in MedDict database in address book**.
* The patient id **must be a positive integer and even number** 0, 2, 4, …​
* The doctor id **must be a positive integer and odd number** 1, 3, 5, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without specifying any tags after it.
* Editing the person with the detail same as the existing detail is not allowed.
* A **notifying message** will be output if there is failure in editing person in MedDict database in address book.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` <br>
   Edited Person: John Doe; Phone: 98765432; Email: johndoe@example.com; Address: 123 Baker Street; Tags: No known allergies
*  `edit 2 n/Betsy Crower`
   At least one field to edit must be provided.
* `edit 1 p/91234567 e/johndoe@example.com` <br>
  This person already exists in the address book.

### Getting id of doctor or patient by name: `getId`

Get id of a doctor or patient whose names contain any of the given keywords.

Format: `getId [Keyword]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
* A **notifying message** will be output if there is failure in retrieving id from MedDict database in address book.

Examples:
* `getId john` <br>
  The id of the person that you are finding is: 00
* `getId john` <br>
  Invalid name entered! Check the name that you want to search id for! Key in 'list' to view all patients


### Adding appointment : `addAppointment`

Add appointment to an existing patient and doctor in the MedDict database in address book.

Format: `addAppointment [x/DateTime] [z/PatientId] [z/DoctorId] …​`

* Add appointments to patient with the specified `PatientId` and doctor with the specified `DoctorId`. 
* The patient id and doctor id **must be valid and present in MedDict database in address book**. 
* The patient id **must be a positive integer and even number** 0, 2, 4, …​
* The doctor id **must be a positive integer and odd number** 1, 3, 5, …​
* `Remark` is an optional field. User can add remark detail by adding `[r/Remark]` when calling the command.
  Empty remark will be added to the appointment if remark is not specified.
* When adding appointment, the appointment detail will be added to the appointments list in both patient and doctor class.
* Each appointment must be scheduled at a unique time to prevent overlap for both the patient and the doctor.
* A **notifying message** will be output if there is failure in adding the appointments.

Examples:
*  `addAppointment x/2024-12-31 15:23 z/0 z/1 r/Third physiotherapy session` <br>
   Successfully added appointment to a patient.
*  `addAppointment x/2024-12-31 15:23 z/0 z/1` <br>
   Successfully added appointment to a patient.
*  `addAppointment x/2024-12-31 15:23 z/0 z/1` <br>
   The patient already has another appointment!
*  `addAppointment x/2024-12-31 15:23 z/0 z/1` <br>
   The doctor already has another appointment!

### View history of a patient : `viewHistory`

View history of an existing patient in the MedDict database in address book.

Format: `viewHistory [z/PatientId]  …​`

* View history of patient with the specified `PatientId`.
* The patient id **must be valid and present in MedDict database in address book**.
* The patient Id **must be a positive integer and even number** 0, 2, 4, …​
* `DateTime` is an optional field. User can view history of the patient on a specific date by adding `[x/DateTime]` when calling the command.
* A **notifying message** will be output if there are no histories found for the doctor.

Examples:
*  `viewhistory z/0 x/2024-12-31 15:23` <br>
   Appointment: `2024-12-31 15:23` for `00` (patient id) with `01` (doctor id). Remarks: `Third physiotherapy session`.
*  `viewHBistory z/0` <br>
   Appointment: `2024-12-31 15:23` for `00` (patient id) with `01` (doctor id). Remarks: `Third physiotherapy session`. <br>
   Appointment: `2024-12-31 16:23` for `00` (patient id) with `01` (doctor id). Remarks: `Fourth physiotherapy session`.
*  `checkAppointment z/1 x/2024-12-31` <br>
   No history found for Patient.

### Check appointment of a patient : `checkAppointment`

Check appointment of an existing doctor in the MedDict database in address book.

Format: `checkAppointment [z/DoctorId] [y/Date]`

* Check appointment of patient with the specified `Doctorb Id`.
* The doctor id **must be valid and present in MedDict database in address book**.
* The doctor Id **must be a positive integer and odd number** 1, 3, 5, …​
* A **notifying message** will be output if there are no appointments found for the doctor.

Examples:
*  `checkAppointment z/1 x/2024-12-31` <br>
   Appointment: `2024-12-31 15:23` for `00` (patient id) with `01` (doctor id). Remarks: `Third physiotherapy session`.
   Appointment: `2024-12-31 16:23` for `00` (patient id) with `01` (doctor id). Remarks: `Fourth physiotherapy session`.
*  `checkAppointment z/1 x/2024-12-31` <br>
   No appointment found for Doctor: `Amy Bee`

### Mark appointment of a doctor : `markAppointment`

Mark appointment of an existing doctor in the MedDict database in address book.

Format: `markAppointment [z/DateTime] [z/PatientId] [z/DoctorId] `

* Mark appointment of doctor with the specified `Doctorb Id`.
* The doctor id **must be valid and present in MedDict database in address book**.
* The doctor Id **must be a positive integer and odd number** 1, 3, 5, …​
* A **notifying message** will be output if there is failure in marking appointments.

Examples:
*  `markAppointment x/2024-12-31 15.23 z/00 z/01` <br>
   Successfully marked appointment as complete
*  `checkAppointment x/2024-12-31 15.23 z/01 z/03` <br>
   The appointment doesn't exist!

### Delete appointment of a doctor : `deleteAppointment`

Delete appointment of a existing patient for both patient and doctor in the MedDict database in address book.

Format: `deleteAppointment [z/DateTime] [z/PatientId] [z/DoctorId] `

* Mark appointment of doctor with the specified `Doctorb Id`.
* The patient id and doctor id **must be valid and present in MedDict database in address book**.
* The patient id **must be a positive integer and even number** 0, 2, 4, …​
* The doctor id **must be a positive integer and odd number** 1, 3, 5, …​
* A **notifying message** will be output if there is failure in deleting appointment.

Examples:
*  `deleteAppointment x/2024-12-31 15.23 z/00 z/01` <br>
   Successfully deleted appointment to a patient
*  `deleteAppointment x/2024-12-31 15.23 z/01 z/03` <br>
   The appointment doesn't exist!

### Add remark to appointment for patient : `deleteAppointment`

Delete appointment of a existing patient for both patient and doctor in the MedDict database in address book.

Format: `deleteAppointment [z/DateTime] [z/PatientId] [z/DoctorId] `

* Mark appointment of doctor with the specified `Doctorb Id`.
* The patient id and doctor id **must be valid and present in MedDict database in address book**.
* The patient id **must be a positive integer and even number** 0, 2, 4, …​
* The doctor id **must be a positive integer and odd number** 1, 3, 5, …​
* A **notifying message** will be output if there is failure in deleting appointment.

Examples:
*  `deleteAppointment x/2024-12-31 15.23 z/00 z/01` <br>
   Successfully deleted appointment to a patient
*  `deleteAppointment x/2024-12-31 15.23 z/01 z/03` <br>
   The appointment doesn't exist!


### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

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
**Help** | `help` <br> Shows help page
**Create Doctor** | `createDoctor [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS]` <br> e.g., `createDoctor n/Dr Jane p/87654321 e/dr.jane.smith@hospital.com a/456 Elm Street r/physiotherapy`
**Create Patient** | `createPatient [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS]` <br> e.g., `createPatient n/John Doe p/98765432 e/johndoe@example.com a/123 Baker Street`
**Delete Patient** | `deletePatient [z/PatientId]` <br> e.g., `deletePatient 00`
**Add Notes** | `addNotes [z/PatientId] [r/Notes]` <br> e.g., `addNotes z/0 r/cancer`
**List** | `list` <br> Shows all persons in address book
**Edit** | `edit Id [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​` <br> e.g., `edit 1 p/91234567 e/johndoe@example.com`
**Get ID** | `getId [Keyword]` <br> e.g., `getId john`
**Add Appointment** | `addAppointment [x/DateTime] [z/PatientId] [z/DoctorId] [r/Remark]` <br> e.g., `addAppointment x/2024-12-31 15:23 z/0 z/1 r/Third physiotherapy session`
**View History** | `viewHistory [z/PatientId] [x/DateTime]` <br> e.g., `viewhistory z/0 x/2024-12-31 15:23`
**Check Appointment** | `checkAppointment [z/DoctorId] [y/Date]` <br> e.g., `checkAppointment z/1 x/2024-12-31`
**Mark Appointment** | `markAppointment [z/DateTime] [z/PatientId] [z/DoctorId]` <br> e.g., `markAppointment x/2024-12-31 15.23 z/00 z/01`
**Delete Appointment** | `deleteAppointment [z/DateTime] [z/PatientId] [z/DoctorId]` <br> e.g., `deleteAppointment x/2024-12-31 15.23 z/00 z/01`
**Clear** | `clear` <br> Clears all entries
**Exit** | `exit` <br> Exits the program
