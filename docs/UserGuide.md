---
layout: page
title: User Guide
---

HRConnect is a desktop app for **managing human resources in relation to projects** within a company.

It is optimized for use via typing while still having the benefits of a Graphical User Interface (GUI). If you can type fast, HRConnect can get your HR management tasks done faster than traditional GUI apps.

---

## Note: User Guide Formatting

The user guide contains formatting to highlight important info. The standards used are as follows:

* `code typeface`: Commands or command formats
* **Bold:** Essential information
* **Underline \+ Bold**: Keywords
* <span style="color:red">RED COLOR</span>: Caution, take note
* *Italics*: Technical terms available in glossary
* Hyperlink: Links to another section of the user guide, or a relevant page.

---

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   - `list` : Lists all contacts.

   - `add id/1 n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   - `delete 3` : Deletes the 3rd contact shown in the current list.

   - `clear` : Deletes all contacts.

   - `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

---

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

- Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

- Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

- Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

- Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

- Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

- If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding a person: `add`

Adds a person to the address book.

Format: `add id/EMPLOYEE_ID n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]… [s/SKILL]…​`
* Skills and tags must be alphanumeric and cannot contain spaces.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0) and any number of skills (including 0)
</div>

Examples:

- `add id/1 n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
- `add id/2 n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal s/lockpicking`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]… [s/SKILL]…​`

- Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
- At least one of the optional fields must be provided.
- Existing values will be updated to the input values.
- When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
- When editing skills, the existing skills of the person will be removed i.e adding of skills is not cumulative.
- You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.
- You can remove all the person’s skills by typing `s/` without
  specifying any skills after it.
- You cannot edit a person's employee id. More specifically, you are not allowed to specify `id/EMPLOYEEID` in the `edit` command.

Examples:

- `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
- `edit 2 n/Betsy Crower t/` Edits the projectName of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Filtering employees persons by skills: `filter`

Finds employees who has at least one skill or tag matching at least one of the search items.

Format: `filter [s/SKILL]... [t/TAG]...`

- The search is case-insensitive. e.g `s/webdev` will match `s/WebDev`.
- The order of the search items does not matter. e.g. `s/frontend s/backend t/swe t/devops` will also match contacts with `skills={backend, frontend}` or `tags={devops, swe}`.
- Only the skills and tags are searched.
- Only full words will be matched e.g. `s/database` will not match `skills={databases}`.
- Employees who have at least one skill or tag matching at least one search item will be returned (i.e. `OR` search).
  e.g. `s/frontend t/swe` will return `skills={frontend, uiux}, tags={designer}`, `skills={backend, database, api}, tags={swe, devops}`, and `skills={frontend, backend}, tags={swe}`

Examples:

- `filter s/frontend` returns `skills={frontend}, tags={designer}` and `skills={frontend, uiux}, tags={designer, pm}`
- `filter s/frontend t/swe` returns `skills={Frontend, UIUX}, tags={designer}`, `skills={Backend}, tags={swe}`<br>
  ![result for 'filter s/frontend t/swe'](images/filterAlexCharlotte.png)

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

- The search is case-insensitive. e.g `hans` will match `Hans`
- The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
- Only the name is searched.
- Only full words will be matched e.g. `Han` will not match `Hans`
- Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:

- `find John` returns `john` and `John Doe`
- `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

- Deletes the person at the specified `INDEX`.
- The index refers to the index number shown in the displayed person list.
- The index **must be a positive integer** 1, 2, 3, …​

Examples:

- `list` followed by `delete 2` deletes the 2nd person in the address book.
- `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

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

---

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

---

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

---

## Command summary

| Action     | Format, Examples                                                                                                                                                                                                         |
| ---------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **Add**    | `add id/EMPLOYEEID n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]… [s/SKILL]…​` <br> e.g., `add id/1 n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague s/database s/backend` |
| **Clear**  | `clear`                                                                                                                                                                                                                  |
| **Delete** | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                                                                      |
| **Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]… [s/SKILL]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                                                                   |
| **Filter** | `filter [s/SKILL]... [t/TAG]...`<br> e.g., `filter s/frontend t/swe`                                                                                                                                                     |
| **Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`                                                                                                                                                               |
| **List**   | `list`                                                                                                                                                                                                                   |
| **Help**   | `help`                                                                                                                                                                                                                   |
