---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# Teletutors User Guide

Teletutors is a **desktop app for managing contacts, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, Teletutors can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-T16-4/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your Teletutors.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar teletutors.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `adds n/John Doe p/98765432 tg/G17 sn/A1234567Z` : Adds a student named `John Doe` to the Teletutor contact list.

   * `deletes n/John Doe` : Deletes the contact with the name John Doe.

   * `deleteall` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `adds n/NAME`, `NAME` is a parameter which can be used as `adds n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [sn/STUDENT_NUMBER]` can be used as `n/John Doe sn/A1234567Z` or as `n/John Doe`.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit`, `deleteall`, `undo`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a student: `adds`

Adds a student to the contact list.

Format: `adds n/NAME p/PHONE_NUMBER tg/TUTORIAL_GROUP sn/STUDENT_NUMBER`

<box type="tip" seamless>

**Tip:** A student must have a unique student number, so if the same student number is used for a new student, the user will be informed that the student already exists in the list.
</box>

Examples:
* `adds n/John Doe p/98765432 tg/G69 sn/E1234567I`
* `adds n/Betsy Crowe tg/G16 p/23456789 sn/A1234567Z`

### Listing all persons : `list`

Shows a list of all students in the contact list.

Format: `list`

### Editing a person : `edits`

Edits an existing student in the address book.

Format: `edits INDEX [n/NAME] [p/PHONE] [tg/TUTORIAL_GROUP] [sn/STUDENT_NUMBER]`

* Edits the student at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
*  `edits 1 p/91234567 tg/Z19` Edits the phone number and tutorial group of the 1st person to be `91234567` and `Z19` respectively.
*  `edits 2 n/Betsy Crower` Edits the name of the 2nd person to be `Betsy Crower`.

### Undoing the previous command : `undo`

Undoes the previous command that changed the data.

Format: `undo`

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

### Deleting a specific student : `deletes`

Deletes the specified student from the address book.

Format: `deletes n/STUDENT_NAME`
Format: `deletes n/STUDENT_NAME sn/STUDENT_NUMBER`

* Deletes the student with the specified details.
* The student name refers to the student name shown in the displayed list of students.
* The student number refers to the student number shown in the displayed list of students.
* The student number **must be in the following format** A1234567B …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

## Attendance Commands

<box type="tip" seamless>

**Tip:** Any command that has the optional field `sn/STUDENT_NUMBER` can be used without the student number if the student number is not known. However, if there are multiple students with the same name, the student number is required to differentiate between them.
</box>


### Marking Attendance : `markat`

* Marks attendance of student for a particular date with the specified details.

* Format: `markat n/STUDENT_NAME d/DATE pr/ATTENDANCE sn/STUDENT_NUMBER (Optional)`

### Marking Tutorial Group Attendance : `markpresentall`

* Marks all students in a tutorial group as present for a particular date

* Format: `markpresentall tg/TUTORIAL_GROUP d/DATE`

### Unmarking Tutorial Group Attendance : `Unmarkpresentall`

* Marks all students in a tutorial group as absent for a particular date

* Format: `unmarkpresentall tg/TUTORIAL_GROUP d/DATE`

### Deleting Attendance : `deleteat`

* Deletes attendance of student for a particular date with the specified details.

* Format : `deleteat n/STUDENT_NAME d/DATE sn/STUDENT_NUMBER (Optional)`

### Deleting Tutorial Group Attendance : `deleteatall`

* Deletes attendance of all students in a tutorial group for a particular date

* Format : `deleteatall tg/TUTORIAL_GROUP d/DATE`

### Getting Attendance : `getat`

* Gets attendance of student for a particular date

* Format: `getat n/STUDENT_NAME d/DATE sn/STUDENT_NUMBER (Optional)`

### Getting Tutorial Group Attendance : `getattg`

* Opens an attendance window for all students in a tutorial group for a particular date

* Format: `getattg tg/TUTORIAL_GROUP d/DATE`


### Saving the data

Teletutors data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

Teletutors data are saved automatically as a JSON file `[JAR file location]/data/teletutors.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, Teletutors will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the Teletutors to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Teletutors home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `adds n/NAME p/PHONE_NUMBER tg/TUTORIAL_GROUP sn/A1234567J` <br> e.g., `adds n/P Diddy p/22224444 tg/G17 sn/A1234567J`
**Clear**   | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edits INDEX [n/NAME] [p/PHONE_NUMBER] [tg/TUTORIAL_GROUP] [sn/STUDENT_NUMBER]`<br> e.g.,`edits 2 n/James Lee p/12345678`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**   | `list`
**Help**   | `help`
**Undo**   | `undo`
