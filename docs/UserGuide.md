---
layout: default.md
title: "User Guide"
pageNav: 3
---

# AB-3 User Guide

AddressBook Level 3 (AB3) is a **desktop app for managing contacts, optimized for use via a Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed on your Computer.

2. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `list` : Lists all contacts.
    * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.
    * `delete 3` : Deletes the 3rd contact shown in the current list.
    * `clear` : Deletes all contacts.
    * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

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

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

**Format:** `help`

### Adding a student: `add`

Adds a student to the address book.

**Format:** `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A student can have any number of tags (including 0)
</box>

Examples:

* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all students : `list`

Shows a list of all students in the address book.

**Format:** `list`

### Editing a student : `edit`

Edits an existing student in the address book.

**Format:** `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the student will be removed (i.e. adding of tags is not cumulative).
* You can remove all the student’s tags by typing `t/` without specifying any tags after it.

Examples:

* `edit 1 p/91234567 e/johndoe@example.com` : Edits the phone number and email address of the 1st student to be `91234567` and `johndoe@example.com`, respectively.
* `edit 2 n/Betsy Crower t/` : Edits the name of the 2nd student to be `Betsy Crower` and clears all existing tags.

### Locating students by name: `find`

Finds students whose names contain any of the given keywords.

**Format:** `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g. `hans` will match `Hans`.
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`.
* Only the name is searched.
* Only full words will be matched. e.g. `Han` will not match `Hans`.
* Students matching at least one keyword will be returned (i.e. `OR` search). e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`.

Examples:

* `find John` returns `john` and `John Doe`.
* `find alex david` returns `Alex Yeoh`, `David Li`.  
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a student : `deleteStu`

Deletes the specified student from the address book.

**Format:** `deleteStu INDEX`

* Deletes the student at the specified `INDEX`.
* The index refers to the index number shown in the displayed student list.
* The index **must be a positive integer** 1, 2, 3, …​.

Examples:

* `list` followed by `delete 2` deletes the 2nd student in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st student in the results of the `find` command.

Easily configure tutorial sessions with simplified commands:

### Add Tutorial

Add a new tutorial session with a specified name and ID.

**Command format:** `addTut tn/TUTORIAL_NAME id/TUTORIAL_ID`  
**Example:** `addTut tn/CS2103T id/1001`

Maintain accurate records of student attendance across various tutorial classes:

### Record Attendance

Record a student's attendance in a specific tutorial class, with an optional attendance date (defaults to today if not specified).

**Command format:** `attend s/STUDENT_ID c/TUTORIAL_CLASS [d/ATTENDANCE_DATE]`


Streamline the handling of assignments from creation to tracking:

### Add Assignment

Create a new assignment with a defined name, due date, and time.

**Command format:** `addAsg n/ASSIGNMENT_NAME d/DUEDATETIME`

### List All Current Assignments

Display a list of all assignments.

**Command format:** `listAsg`

### Mark and Unmark Assignments

Toggle the completion status of an assignment.

**Command to mark as completed:** `markAsg <index> n/ASSIGNMENT_NAME`

**Command to unmark as completed:** `unmarkAsg <index> n/ASSIGNMENT_NAME`

### Check Assignment Completion Statuses

Verify the completion status of specific assignments across all students.

**Command format:** `checkAsg n/ASSIGNMENT_NAME`

### Delete Assignment

Remove an assignment from the system using its title name.

**Command format:** `deleteAsg [title name]`


### Clearing all entries : `clear`

Clears all entries from the address book.

**Format:** `clear`

### Exiting the program : `exit`

Exits the program.

**Format:** `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**  
If your changes to the data file make its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another computer?  
**A**: Install the app on the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action            | Format, Examples                                                                                                                                                       |
|-------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Student**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`<br> e.g. `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`    |
| **Clear**          | `clear`                                                                                                                                                               |
| **Delete Student**  | `deleteStu INDEX`<br> e.g., `deleteStu 3`                                                                                                                            |
| **Edit Student**    | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g., `edit 2 n/James Lee e/jameslee@example.com`                                          |
| **Find Student**    | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`                                                                                                            |
| **List Students**   | `listStu`                                                                                                                                                            |
| **Add Tutorial**    | `addTut tn/[TUTORIAL NAME] id/[TUTORIAL ID]`<br> e.g., `addTut tn/CS1010 id/1011`                                                                                     |
| **List Tutorials**  | `listTut`                                                                                                                                                            |
| **Delete Tutorial** | `deleteTut [TUTORIAL ID]`<br> e.g., `deleteTut 1011`                                                                                                                 |
| **Add Assignment**  | `addAsg n/[ASSIGNMENT TITLE] d/[DUE DATE]`<br> e.g., `addAsg n/Assignment 1 d/2024-10-23 1230`                                                                       |
| **Delete Assignment**| `deleteAsg [ASSIGNMENT TITLE]`<br> e.g., `deleteAsg Assignment 1`                                                                                                   |
| **List Assignments**| `listAsg`                                                                                                                                                            |
| **Mark Attendance** | `attend s/[STUDENT ID] c/[TUTORIAL ID] d/[TUTORIAL DATE]`<br> e.g., `attend s/1001 c/1001 d/2024/02/21`                                                               |
| **Unmark Attendance**| `unmarkAttend s/[STUDENT ID] c/[TUTORIAL ID] d/[TUTORIAL DATE]`<br> e.g., `unmarkAttend s/1001 c/1001 d/2024/02/21`                                                  |
| **Mark Assignment** | `markAsg [INDEX] n/[ASSIGNMENT TITLE]`<br> e.g., `markAsg 1 n/Assignment 1`                                                                                           |
| **Unmark Assignment**| `unmarkAsg [INDEX] n/[ASSIGNMENT TITLE]`<br> e.g., `unmarkAsg 1 n/Assignment 1`                                                                                      |
| **Check Assignment**| `checkAsg n/[ASSIGNMENT TITLE]`<br> e.g., `checkAsg n/Assignment 1`                                                                                                   |
| **Clear**          | `clear`                                                                                                                                                               |
| **Help**           | `help`                                                                                                                                                                |
| **Exit**           | `exit`                                                                                                                                                                |

--------------------------------------------------------------------------------------------------------------------

