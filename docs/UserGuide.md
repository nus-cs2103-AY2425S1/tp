---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# KeyContacts User Guide

KeyContacts is a **desktop app for piano teachers to manager their students' information, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, KeyContacts can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-T08-2/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for KeyContacts.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar keycontacts.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 a/John street, block 123, #01-01 g/ABRSM 3` : Adds a student named `John Doe` to the student directory.

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
  e.g `[n/NAME]` can be used as ` ` or `n/John Doe`.

* Items with `…`​ after them can be used multiple times.<br>
  e.g. `pn/PIECE…​` can be used as `pn/Moonlight Sonata`, `pn/Moonlight Sonata pn/Ode to Joy` etc.

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


### Adding a student: `add`

Adds a student to the student directory.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS g/GRADE_LEVEL`

Examples:
* `add n/John Doe p/98765432 a/John street, block 123, #01-01 g/LCM 1`

### Listing all students : `list`

Shows a list of all student in the student directory.

Format: `list`

### Editing a student : `edit`

Edits an existing student in the student directory.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [g/GRADE_LEVEL]`

* Edits the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
*  `edit 1 n/Jane Doe p/91234567` Edits the name and phone number of the 1st student to be `Jane Doe` and `91234567` respectively.

### Assigning piano pieces to a student: `assign`

Assigns piano pieces to a student in the student directory.

Format: `assign INDEX pn/PIECE_NAME...`

* Assigns piano pieces to the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​
* Existing values will remain unchanged

Examples:
*  `assign 1 pn/Etude pn/Moonlight Sonata` Adds `Etude` and `Moonlight Sonata` to the 1st student's piano pieces.

### Unassigning piano pieces from a student: `unassign`

Unassigns piano pieces from a student in the student directory.

Format: `unassign INDEX [pn/PIECE_NAME]...`

* Unassigns piano pieces from the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​
* All piano pieces provided must be already assigned to the student
* If no piano pieces are provided, all piano pieces will be unassigned from the student

Examples:
*  `unassign 1 pn/Etude pn/Moonlight Sonata` Removes `Etude` and `Moonlight Sonata` from the 1st student's piano pieces.

### Scheduling a regular lesson : `schedule`

Schedules a regular lesson for the specified student in the student directory.

Format: `schedule INDEX d/DAY st/START_TIME et/END_TIME`

* Schedules the regular lesson for the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​
* If the student already has an existing regular lesson, it will be overwritten by the new regular lesson given.
* `DAY` must be a day of the week (e.g. Monday, Tuesday etc.) or its 3-letter abbreviation (e.g. Mon, Tue etc.). This parameter is case-insensitive.
* `START_TIME` and `END_TIME` must be in 24-hour format, and `START_TIME` must be before `END_TIME`

Example:
* `schedule 1 d/Tuesday st/16:00 et/18:00` Schedules a regular lesson on Tuesday, 4-6pm for the 1st student.

### Cancelling a regular lesson : `cancel`

Cancels a regular lesson at a specific date for the specified student in the student directory.

Format: `cancel INDEX dt/DATE st/START_TIME`

* Cancels a regular lesson for the student at the specified `INDEX`. The index refers to the index number showin in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​
* `DATE` must be written in the format `DD-MM-YYYY` for the command to parse the input properly.
* `DATE` must fall on the student's regular lesson `DAY`, and `START_TIME` must match the student's lesson `START_TIME`.

Example:
* `cancel 1 dt/15-10-2024 st/16:00` Cancels a regular lesson on 15-10-2024 (which is a Tuesday), starting at 4pm for the 1st student.

### Scheduling a makeup lesson : `makeup`

Schedules a makeup lesson for the specified student in the student directory.

Format: `makeup INDEX dt/DATE st/START_TIME et/END_TIME`

* Schedules the makeup lesson for the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​
* `DATE` must be in the format `DD-MM-YYYY`.
* `START_TIME` and `END_TIME` must be in 24-hour format, and `START_TIME` must be before `END_TIME`

Examples:
* `makeup 1 dt/25-12-2022 st/12:00 et/14:00` Schedules a makeup lesson on 25th December 2022, 12-2pm for the 1st student.

### Locating students: `find`

Finds students whose personal details match inputs

Format: `find [n/NAME_KEYWORD] [p/PHONE_KEYWORD] [a/ADDRESS_KEYWORD] [g/GRADE_LEVEL_KEYWORD]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `[n/Hans] [p/88197184]` will fetch same list as `[p/88197184] [n/Hans]`
* Prefixes other than `n/`, `p/`, `a/`, `g/` will be ignored.
* Any keyword before the first valid prefix will be ignored
* Only the name, phone number, address, and grade level is searched.
* Partial inclusion of keyword will be considered a match
* Students matching all field will be returned (i.e. `AND` search).
  e.g. `n/Hans p/88191784` will return `Hans Gruber; 88197184`, but not`Hans Goretzka; 88197188`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Sort students

Sorts students with personal details

Format: `sort [n/ASC or DESC] [p/ASC or DESC] [a/ASC or DESC] [g/ASC or DESC]`

* The order of prefixes DOES matter. If there are ties in first field, it will use later fields to tie-break.
* Prefixes other than `n/`, `/p`, `a/`, `g/` will be ignored.
* Only name, phone number, address, and grade level are valid fields to be sorted against
* The sorting order must be `ASC` or  `DESC`, and is case-insensitive

To clear the sorting conditions, use command `sort clear`

Examples:
* `sort n/ASC` sorts the students by name in ascending order.
* `sort g/DESC n/ASC` sorts the students by grade level in descending order, and tie-breaks with name in ascending order

### Deleting a student : `delete`

Deletes the specified student from the student directory.

Format: `delete INDEX`

* Deletes the student at the specified `INDEX`.
* The index refers to the index number shown in the displayed student list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd student in the student directory.
* `find Betsy` followed by `delete 1` deletes the 1st student in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the student directory.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

Student directory data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

Student directory data is saved automatically as a JSON file `[JAR file location]/data/studentdirectory.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, KeyContacts will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause KeyContacts to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous KeyContacts home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Help**   | `help`
**Add**    | `add n/NAME p/PHONE_NUMBER a/ADDRESS g/GRADE_LEVEL` <br> e.g., `add n/James Ho p/22224444 a/123, Clementi Rd, 1234665 g/LCM 1`
**List**   | `list`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [a/ADDRESS] [g/GRADE_LEVEL]`<br> e.g.,`edit 2 n/James Lee p/81234567`
**Assign** | `assign INDEX pn/PIECE_NAME...`<br> e.g,`assign 1 pn/Moonlight Sonata pn/Canon in D`
**Unassign** | `unassign INDEX [pn/PIECE_NAME]...`<br> e.g, `unassign 1 pn/Moonlight Sonata pn/Canon in D`
**Schedule** | `schedule INDEX d/DAY st/START_TIME et/END_TIME`<br> e.g.,`schedule 1 d/Monday st/12:00 et/14:00`
**Cancel** |  `cancel INDEX dt/DATE st/START_TIME` <br> e.g., `cancel 1 dt/14-10-2024 st/12:00`
**Makeup** | `makeup INDEX dt/DATE st/START_TIME et/END_TIME`<br> e.g.,`makeup 1 d/25-12-2022 st/12:00 et/14:00`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Clear**  | `clear`
**Exit**   | `exit`
