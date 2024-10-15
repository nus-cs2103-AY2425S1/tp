---
layout: page
title: User Guide
---

ClinicBuddy aims to enhance the patient management process for small clinics, creating a platform to track patient information such as their treatment, contact information, visit records and future appointments while still having the benefits of a Graphical User Interface (GUI).

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-F11-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar tp.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe a/36 g/M i/S1234567Z p/98765432 e/johnd@example.com h/311, Clementi Ave 2, #02-25 t/Patient` : Adds a patient named `John Doe` to ClinicBuddy.

   * `delete S1234567Z` : Deletes the patient with the NRIC 'S1234567Z' in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.
   
   * `backup` : Creates a backup of the current patient records.
    
   * `find S1234567Z` : Finds the patient that has the NRIC
   
   * `find John` : Finds the patient named 'John'

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


### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME a/AGE g/GENDER i/NRIC c/CONTACT_NUMBER e/EMAIL h/ADDRESS [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe a/36 g/M i/S1234567Z p/98765432 e/johnd@example.com h/311, Clementi Ave 2, #02-25 t/Patient`
* `add n/Betsy Crowe a/42 g/F i/T1235678E t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/BloodDonor`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [a/AGE] [g/GENDER] [i/NRIC] [p/PHONE] [e/EMAIL] [h/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

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

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete NRIC`

* Deletes the person that has the specified `NRIC`.
* The NRIC refers to the NRIC shown in the displayed person list.
* The NRIC **must start with 'S', 'T', 'F','G' or 'M', have 7 digits, and end with a letter.** 

Examples:
* `list` followed by `delete S1234567Z` deletes the patient that has NRIC of 'S1234567Z' in the list.
* `find Betsy` followed by `delete S2345678E` deletes the person with 'S2345678E' in the results of the `find` command.

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

### Backup the records : `backup`
The `backup` feature ensures your data is safe by **automatically creating backups** whenever patient records are modified. Backups are stored in the `backups` folder located in the root directory of the application.
In addition to automatic backups, the system also provides a **manual backup option** for users who want more control over their data management.

Format: `backup`

#### **How Backups works:**
1. **Automatic Backups:**
    - Each time the patient records are modified (e.g., adding, editing, or deleting a record), the system **automatically creates a backup**.
    - These backups are stored in:
      ```
      [Application Directory]/backups/
      ```
    - Naming Format:
      ```
      addressbook-backup-[timestamp].json
      ```
    - Example: 
      ```
      addressbook-backup-1697380298000.json
      ```
    - The program retains **only the 10 most recent backups** to manage storage effectively. Older backups are automatically deleted.
2. **Manual Backups:**
   - In addition to automatic backups, you can **manually trigger a backup** using the `backup` command.
   - Command Format:
     ```
     backup
     ```
   - **Storage Location:**  
     Manual backups are saved in the same directory:
     ```
     [Application Directory]/backups/
     ```
   - **Naming Format:**  
     The manual backup will follow the same naming convention as automatic backups:
     ```
     addressbook-backup-[timestamp].json
     ```
#### **Accessing Backup Files:**
   1. Navigate to the backup directory:
      ```
      [Application Directory]/backups/
      ```
   2. Identify the backup files using their **timestamp-based naming convention**.
   3. **Move or copy** the files if needed for external storage or manual restoration.

⚠ **Note:** While the system handles automatic backups, manual backups provide additional flexibility and control when needed.

_Note: A restore function will be introduced soon to recover patient records from the latest backup._

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

**Q**: Why Use Manual Backups?<br>
**A**:
- **Extra Control:** Save your data at any moment, even without making changes.
- **Checkpointing:** Use manual backups before importing new data or making major changes.
- **Peace of Mind:** Ensure you have a backup exactly when you need it.

**Q**: Can I Save Backups to a Custom Path?<br>
**A**: Currently, **manual backups are saved to the default path** (`/backups` folder). There is no option to specify a **custom path** within the application. However, you can **move the backup files** manually from the `/backups` folder to any desired location if needed.


--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/John Doe a/36 g/M i/S1234567Z p/98765432 e/johnd@example.com h/311, Clementi Ave 2, #02-25 t/Patient`
**Clear** | `clear`
**Delete** | `delete NRIC`<br> e.g., `delete S1234567Z`
**Edit** | `edit INDEX [n/NAME] [a/AGE] [g/GENDER] [i/NRIC] [p/PHONE] [e/EMAIL] [h/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`
| **Backup**    | `backup` <br> e.g., `backup` creates a new backup of the patient records. |
