---
layout: page
title: User Guide
---

ClinicBuddy aims to enhance the patient management process for small clinics, creating a platform to track patient
information such as their treatment, contact information, visit records and future appointments while still having the
benefits of a Graphical User Interface (GUI).

* Table of Contents
  {:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-F11-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar clinicbuddy.jar` command to run
   the application.<br>

   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will
   open the help window.<br>
   Some example commands you can try:

    * `list` : Lists all contacts.

    * `add n/John Doe a/36 g/M i/S1234567Z p/98765432 e/johnd@example.com h/311, Clementi Ave 2, #02-25 apt/02/10/2024 18:30 t/Patient` :
      Adds a patient named `John Doe` whose appointment is at `02 October 2024 18:30` to ClinicBuddy.

    * `delete S1234567Z` : Deletes the patient with the NRIC 'S1234567Z' in the current list.

    * `clear` : Deletes all contacts.

    * `exit` : Exits the app.

    * `backup update John's age` : Creates a backup with a specific descriptive naming.
   
    * `backup` : Creates a backup by default naming.
   
    * `restore 0` : Restores backup file from the /backups/ path with the naming starts with index 0.
   
    * `listbackups` : Lists all available backup files in the /backups/ path.

    * `find S1234567Z` : Finds the patient that has the NRIC

    * `find John` : Finds the patient named 'John'

    * `update S1234567Z p/91234567 e/johndoe@example.com` : Updates the email address of the patient with provided NRIC.

    * `update 1 p/91234567 e/johndoe@example.com` : Updates the email address of the first patient in the list.
   
    *  `hours o/08:30 c/18:30` : Updates the Operating Hours to 8:30 to 18:30.

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

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be
  ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines
  as space characters surrounding line-breaks may be omitted when copied over to the application.

</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME a/AGE g/GENDER i/NRIC c/CONTACT_NUMBER e/EMAIL h/ADDRESS [apt/APPOINTMENT] [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>
<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
There is a unique tag for Blood Type, Try putting a tag named 'A+'
</div>

Examples:

* `add n/John Doe a/36 g/M i/S1234567Z p/98765432 e/johnd@example.com h/311, Clementi Ave 2, #02-25 apt/02/10/2024 18:30 t/Patient`
* `add n/Betsy Crowe a/42 g/F i/T1235678E apt/02-10-2024 18:30 t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/BloodDonor`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
An appointment can be entered in the following formats : '-' , '02 10 2024 12:30' , '02/10/2024 12:30' , '02-10-2024 12:30' and timings must fall within the Operating Hours displayed above
</div>
<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Appointments can only be made between '08:30' to '21:30' and only one appointment can be made per timeslot
</div>

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Updating a person : `update`

Updates an existing person in the address book by searching for their index or NRIC.

Format: 
`update INDEX [n/NAME] [a/AGE] [g/GENDER] [i/NRIC] [p/PHONE] [e/EMAIL] [h/ADDRESS] [apt/APPOINTMENT] [t/TAG]…​ `  
OR `update NRIC [n/NAME] [a/AGE] [g/GENDER] [i/NRIC] [p/PHONE] [e/EMAIL] [h/ADDRESS] [apt/APPOINTMENT] [t/TAG]…​`

* Edits the person at the specified `INDEX` or `NRIC`. The index and NRIC refers to the index number and NRIC shown in the displayed person list respectively.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* If NRIC is being updated, the updated NRIC must be unique.
* You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.

Examples:
* `update 1 p/91234567 e/johndoe@example.com` Updates the phone number and email address of the 1st person to be `91234567`
  and `johndoe@example.com` respectively.
* `update 2 n/Betsy Crower t/` Updates the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

* `update S1234567Z p/91234567 e/johndoe@example.com` Updates the phone number and email address of the person whose NRIC is 'S1234567Z' to be `91234567`
  and `johndoe@example.com` respectively.

### Locating persons: `find`

`find` allows you to find patient records by NRIC or name.

#### Finding a single record by its NRIC

Format: `find NRIC`

* The search is case-insensitive. e.g `s1234567z` will match `S1234567Z`
* The NRIC must start with 'S', 'T', 'F','G' or 'M', have 7 digits, and end with a letter.
* In a single command, only one record with the given NRIC can be found. e.g. `find S1234567Z T7654321Z` does not work
  as it
  attempts to find 2 records containing the given NRICs.
* Only full NRICs will be matched e.g. `S12345` will not match `S1234567Z`

Example:

* `find S1234567Z` returns the patient record whose NRIC is `S1234567Z`

  ![result for 'find S1234567Z'](./images/findS1234567Zresult.png)

#### Finding multiple records by their names

Finds patient records whose names contain any of the given keywords.

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

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete NRIC`

* Deletes the person that has the specified `NRIC`.
* The NRIC refers to the NRIC shown in the displayed person list.
* The NRIC **must start with 'S', 'T', 'F','G' or 'M', have 7 digits, and end with a letter.**

Examples:

* `list` followed by `delete S1234567Z` deletes the patient that has NRIC of 'S1234567Z' in the list.
* `find Betsy` followed by `delete S2345678E` deletes the person with 'S2345678E' in the results of the `find` command.

### Finding all persons with appointments on a specific date : `bookings`

Finds all persons with appointments on the specified date.

Format: `bookings DATE`

* Finds all persons with appointments on `DATE`
* `DATE` has to be of the format `dd/MM/yyyy` OR `dd-MM-yyyy` OR `dd MM yyyy`

Examples:
* `bookings 01/02/2024`
* `bookings 01-02-2024`

  ![result for 'bookings 01/02/2024'](./images/bookings01-02-2024.png)

### Updating Operating Hours : `hours`

Updates Operating Hours 

Format: `hours [o/OPENINGHOURS] [c/CLOSINGHOURS]`

* Opening & Closing hours have to be of the format `HH:mm`.
* Default Opening & Closing hours are 00:00 & 23:59 respectively.
* If an argument is empty, it will set the hours to default.
* All current appointments must fall within operating hours for update to take effect.

Examples:
* `hours`
* `hours o/09:30 c/18:00`
* `hours c/18:00`

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to
save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are
welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Backup the records : `backup`

The `backup` feature allows you to manually create a backup of your current patient records with a specific descriptive naming.
This is useful if you want to save the state of your current data at a specific point in time.

Format: `backup [DESCRIPTION]`

#### **How Manual Backup Works:**

- Creates a manual backup of the current patient records.
- An optional `DESCRIPTION` can be provided for clarity of the content in the backup file to help user to identify the backup file effectively.
- Without `DESCRIPTION`, the system will name the backup file using the default name: `manual_backup`.
- Each backup file is assigned an index number from `0` to `9` and the index cycles back to `0` after reaching `9`.
- Each backup file is also assigned the timestamp of the creation time.
- The system retains only the 10 most recent backups, older backups are automatically overwritten.
- These backups are stored in:
  ```
  [Application Directory]/backups/
  ```
- Naming Format:
  ```
  <INDEX>_<DESCRIPTION>_<TIMESTAMP>.json
  ```
- Example:
  ```
  3_After updating John's contact info_2024-10-30_15-45-00-000.json
  ```
#### **Automated Backup:** 🚨
- Whenever a patient record is deleted, ClinicBuddy automatically creates a backup of all patient records before the deletion. 
- This helps to ensure that no data is permanently lost in case of an accidental deletion.
- Backups are stored in the same location as the manual backup whicch is the /backups/ folder within the application directory.
  ```
  0_delete_John Doe_2024-10-30_18-05-29-745.json
  ```

### Restoring data from backups : `restore`

The `restore` feature allows you to recover patient records from a specific backup file by the index in its name.
This is useful in case of unintended data loss or errors.

Format: `restore <INDEX>`

#### **How Restore Works:**

- Restores the patient records from the backup with the specified `INDEX`.
- The `INDEX` must correspond to a valid backup file in the /backups/ directory.
- Providing an invalid `INDEX` will result in an error message.
- Use the `listbackups` command to view available backups and their indices.
- Example:
  ```
  restore 2
  ```
- This will restore the patient records from the backup file with index 2.

**Important Notes:**
- Restoring from a backup will **overwrite** the current patient records in ClinicBuddy.
- Ensure that you want to discard any changes made since the backup before performing a restore.
- The `restore` command will restore the entire set of patient records from the backup.
- It is safe to create a backup for current data before performing a restore.

### Listing all backups : `listbackups`
Displays a list of all available backups along with their details.

Format: `listbackups`

#### **How ListBackups works:**
- Shows all backups stored in the /backups/ directory.
- Each backup is displayed with its index, description, and creation timestamp in a specific format.
- Example:
  ```
  Available backups:
  0 [delete_John Doe] Created on: 30 Jan 2024 18:05:29
  1 [manual_backup] Created on: 13 May 2024 09:52:10
  2 [clinicbuddy] Created on: 21 Jul 2024 13:20:07
  ```
- The date format used is dd MMM yyyy HH:mm:ss.
- Only the 10 most recent backups are available for storage efficiency.

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains
the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only
   the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the
   application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard
   shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy
   is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

 Action     | Format, Examples                                                                                                                                                                                                                                 
------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 **Add**    | `add n/NAME a/AGE g/GENDER i/NRIC c/CONTACT_NUMBER e/EMAIL h/ADDRESS [apt/APPOINTMENT] [t/TAG]…​` <br> e.g., `add n/John Doe a/36 g/M i/S1234567Z p/98765432 e/johnd@example.com h/311, Clementi Ave 2, #02-25 apt/12/10/2024 15:30 t/Patient`   
 **Clear**  | `clear`                                                                                                                                                                                                                                          
 **Delete** | `delete NRIC`<br> e.g., `delete S1234567Z`                                                                                                                                                                                                       
 **Update**   | `update INDEX/NRIC [n/NAME] [a/AGE] [g/GENDER] [i/NRIC] [p/PHONE] [e/EMAIL] [h/ADDRESS] [apt/APPOINTMENT] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                                                        
 **Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`                                                                                                                                                                                       
 **List**   | `list`                                                                                                                                                                                                                                           
 **Help**   | `help`                                                                                                                                                                                                                                           
| **Backup** | `backup`  <br/>  `backup <DESCRIPTION>` <br/> e.g., `backup After updating John's contact info`  
**Restore** | `restore <INDEX>`<br> e.g., `restore 1`       
**ListBackups** | `listbackups`                                                                                                                                                         


