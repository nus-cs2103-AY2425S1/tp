---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# TAchy User Guide

TAchy is a **desktop app for managing students, optimized for use via a  Line Interface** (CLI) while
still having the benefits of a Graphical User Interface (GUI). If you can type fast, TAchy can get your student
management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar TAchy.jar` command to
   run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all students.

   * `add_student n/John Doe p/98765432 e/johnd@example.com ` : Adds a student named
     `John Doe` to the app.

   * `view_student 2 ` : Displays the details of the 2nd student shown in the current list.

   * `delete 3` : Deletes the 3rd student shown in the current list.

   * `clear` : Deletes all students.

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


### Adding a student: `add_student`

Adds a student to the app.

Format: `add_student n/NAME p/PHONE_NUMBER e/EMAIL [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A student can have any number of tags (including 0)
</box>

Examples:
* `add_student n/John Doe p/98765432 e/johnd@example.com`
* `add_student n/Betsy Crowe t/friend e/betsycrowe@example.com p/1234567 t/likesMath`

### Viewing a student: `view_student`

Displays all the details of a student in the details panel.

Format: `view_student INDEX`

* Displays the details of a student at the specified `INDEX`.
* The index refers to the index number shown in the displayed student list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `view_student 2` displays the 2nd student in the list.
* `find Betsy` followed by `view_student 1` displays the 1st student in the results of the `find` command.

### Listing all students : `list`

Shows a list of all students in the app.

Format: `list`

### Editing a student : `edit`

Edits an existing student in the app.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [t/TAG]…​`

* Edits the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the student will be removed i.e adding of tags is not cumulative.
* You can remove all the student’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st student to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd student to be `Betsy Crower` and clears all existing tags.

### Locating students by name: `find`

Finds students whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Students matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a student : `delete`

Deletes the specified student from the app.

Format: `delete INDEX`

* Deletes the student at the specified `INDEX`.
* The index refers to the index number shown in the displayed student list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd student in the app.
* `find Betsy` followed by `delete 1` deletes the 1st student in the results of the `find` command.

### Adding an assignment: `add_assignment`

Adds an Assignment to the app.

Format: `add_assignment studentIndex/STUDENT_INDEX assignmentName/ASSIGNMENT_NAME maxScore/MAX_SCORE`

Examples:
* `list` followed by `add_assignment studentIndex/3 assignmentName/Assignment 1 maxScore/100` adds an assignment to the
  3rd student in the app.
* `add_assignment studentIndex/1 assignmentName/Assignment 1 maxScore/100`

### Deleting an assignment: `delete_assignment`

Deletes an assignment belonging to a student based on the student's index number and the assignment's index.

Format: `delete_assignment studentIndex/INDEX assignmentIndex/INDEX`

Examples:
* `find John` followed by `delete_assignment studentIndex/1 assignmentIndex/1` deletes the 1st assignment of the 1st student in the results of the `find` command.
* `delete_assignment studentIndex/1 assignmentIndex/1`

### Marking an assignment as submitted: `mark`

Marks an existing assignment belonging to a student as submitted, based on the student's index and the assignment's index.

Format: `mark studentIndex/INDEX assignmentIndex/INDEX`

Examples:
* `list` followed by `mark studentIndex/3 assignmentIndex/1` marks the 1st assignment of the 3rd student in the app.
* `mark studentIndex/1 assignmentIndex/1`

### Unmarking an assignment: `unmark`

Unmarks an existing assignment belonging to a student, resetting its status to not submitted.

Format: `unmark studentIndex/INDEX assignmentIndex/INDEX`

Examples:
* `unmark studentIndex/1 assignmentIndex/1`

### Grading an assignment: `grade`

Edits the score of an assignment belonging to a student and marks it as submitted.

Format: `grade studentIndex/INDEX assignmentIndex/INDEX score/ASSIGNMENT_SCORE`

Examples:
* `grade studentIndex/1 assignmentIndex/1 score/100`


### Clearing all entries : `clear`

Clears all entries from the app.

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

Action            | Format, Examples
------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**           | `add n/NAME p/PHONE_NUMBER e/EMAIL [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com t/likesMath`
**View**          | `view INDEX`<br> e.g., `view_student 3`
**Clear**         | `clear`
**Delete**        | `delete INDEX`<br> e.g., `delete 3`
**Edit**          | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**          | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**          | `list`
**Help**          | `help`
**Add Assignment**| `add_assignment studentIndex/STUDENT_INDEX assignmentName/ASSIGNMENT_NAME maxScore/MAX_SCORE`<br> e.g., `add_assignment studentIndex/1 assignmentName/Assignment 1 maxScore/100`
**Delete Assignment** | `delete_assignment studentIndex/INDEX assignmentIndex/INDEX`<br> e.g., `delete_assignment studentIndex/1 assignmentIndex/1`
**Mark Assignment** | `mark studentIndex/INDEX assignmentIndex/INDEX`<br> e.g., `mark studentIndex/1 assignmentIndex/1`
**Unmark Assignment** | `unmark studentIndex/INDEX assignmentIndex/INDEX`<br> e.g., `unmark studentIndex/1 assignmentIndex/1`
**Grade Assignment** | `grade studentIndex/INDEX assignmentIndex/INDEX score/ASSIGNMENT_SCORE`<br> e.g., `grade studentIndex/1 assignmentIndex/1 score/100`
