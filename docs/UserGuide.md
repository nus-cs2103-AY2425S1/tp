---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# AB-3 User Guide

AddressBook Level 3 (AB3) is a **desktop app for managing contacts, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

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

<box type="info" seamless>

**Notes about the command format:**<br>

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
</box>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0)
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

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

### Locating persons by name and tag: `find`

Finds persons whose names or tags contain any of the given keywords.

Format: `find n/NAME [n/ANOTHER_NAME] ... [t/TAG]...`

* Persons matching at least field will be returned (i.e. `OR` search). e.g. `n/Hans t/family` will return `Hans Gruber`, `Bo Yang`, and any other persons with `family` tag.
* For `n/`:
    * The search is case-insensitive. e.g `n/hans` will match `Hans`
    * Partial words will be matched e.g. `n/Han` will match `Hans`
* For `t/`:
    * The search is case-insensitive. e.g `t/FAMILY` will match any contacts with `family` tag
    * Full words will be matched e.g. `t/Han` will not match any contacts with `Hans` tag

Examples:
* `find n/John` returns `john` and `John Doe`
* `find n/alex t/classmates` returns `Alex Yeoh`, `alex tan`, `Irfan` (who has the `classmates` tag)<br>
  ![result for 'find n/alex t/classmates'](images/findAlexclassmatesResult.png)

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

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

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

### See all meetings

Views all of the user's meetings.

Format: `list-schedule`

### See weekly schedule

View schedule for the week of user date input. Sunday is considered the start of the week.

Format: `see d/dd-MM-YYYY`

Examples:
- `see d/17-10-2024` shows all meetings within the week range of `13-10-2024` to `19-10-2024`

### Add meetings to schedule

Add meetings to user's schedule.

Format: `add-schedule c/CONTACT n/NAME d/DATE t/TIME`
- all fields must be present
- `c/CONTACT` the contact's index with respect to the currently displayed list of contacts.
- `n/NAME` description of the meeting.
- `d/DATE` date must be in the format of dd-MM-YYYY.
- `t/TIME` time must be in the format of hhmm (24 hours notation) .

Example:
- `add-schedule c/1 n/Dinner d/10-10-2024 t/1800`

### Delete meeting from schedule

Delete events from user's schedule.

Format: `delete-schedule INDEX`
- `INDEX` is based on the current displayed schedule list.

Example:
- `delete-schedule 2` will delete the second meeting from the current schedule view.

### Edit Meeting in Schedule

Edit the existing meeting within the schedule.

Format: `edit-schedule INDEX [n/NAME] [d/DATE] [t/TIME] [c/INDEX]...`
- `INDEX`: Refers to the schedule you want to edit. The index is based on the current schedule view and **must** be specified.
- `n/NAME` (optional): The new description or name of the meeting. If not provided, the name remains unchanged.
- `d/DATE` (optional): The new date of the meeting. Must be in the format `DD-MM-YYYY`. If not provided, the date remains unchanged.
- `t/TIME` (optional): The new time of the meeting. Must be in 24-hour format `hhmm`. If not provided, the time remains unchanged.
- `c/INDEX` (optional): Refers to the contact's index in the current address book view. You can specify multiple `c/INDEX` values:
    - If the contact is **already in** the meeting, specifying `c/INDEX` will **remove** the contact.
    - If the contact is **not in** the meeting, specifying `c/INDEX` will **add** the contact.
- At least one of `n/NAME`, `d/DATE`, `t/TIME`, or `c/INDEX` must be specified. If a field is not provided, the current value for that field remains unchanged.

Example:
- `edit-schedule 1 n/Dinner d/10-10-2024 t/1800 c/2 c/3 c/4`

- **Before**: Schedule 1 contains contacts `1, 2, 3`.
- **After**: Contacts `2` and `3` are removed (as they were already in the meeting), and contact `4` is added. The final list of contacts for the meeting is `1, 4`.

Notes:
- You must always specify the `INDEX` of the schedule to be edited.
- At least one other field (`n/NAME`, `d/DATE`, `t/TIME`, or `c/INDEX`) must be provided; otherwise, the command will not execute.
- If a field (name, date, time, or contacts) is not specified, the existing value for that field remains unchanged.

This ensures flexibility by allowing you to only modify the fields you need while keeping the others intact.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**   | `list`
**Help**   | `help`
