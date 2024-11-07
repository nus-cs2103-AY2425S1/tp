---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# Teletutors User Guide

Teletutors is a **desktop app for managing contacts, optimized for use via a  Line Interface** (CLI) while still having 
the benefits of a Graphical User Interface (GUI). If you can type fast, Teletutors can get your contact management tasks 
done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

2. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-T16-4/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your Teletutors.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar teletutors.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   <img src="images/Ui.png" alt="Ui" />

5. Pressing the **up arrow key (↑)** will recall the last valid command entered, allowing for modification of previous commands, similar to a command terminal

6. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>

   Some example commands you can try:

   * `list` : Lists all contacts.

   * `adds n/John Doe p/98765432 tg/G17 sn/A1234567Z` : Adds a student named `John Doe` to the Teletutor contact list.

   * `deletes n/John Doe` : Deletes the contact with the name John Doe.

   * `deleteall` : Deletes all contacts.

   * `exit` : Exits the app.

7. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

# Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `adds n/NAME`, `NAME` is a parameter which can be used as `adds n/John Doe`.

* Items in square brackets are optional.<br>
  e.g. `n/NAME [sn/STUDENT_NUMBER]` can be used as `n/John Doe sn/A1234567Z` or as `n/John Doe`.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit`, `deleteall`, `undo`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

* Name arguments with extra whitespaces between words will be reduced to a single whitespace. 
  e.g. `n/John    Doe` will be treated as `n/John Doe`.

* Tutorial group and student number arguments are not case-sensitive. 
  e.g. `tg/G17` is the same as `tg/g17` and `sn/A1234567Z` is the same as `sn/a1234567z`.

</box>

### Viewing help : `help`

Shows a message explaning how to access the help page.

<img src="images/helpMessage.png" alt="Help" />

Format: `help`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Undoing the previous command : `undo`

Undoes the previous command. <br>
The closeat command is currently not undoable (planned for future versions).

Format: `undo`

<div style="page-break-after: always;"></div>

## Student Commands

### Adding a student: `adds`

Adds a student to the contact list.

Format: `adds n/NAME p/PHONE_NUMBER tg/TUTORIAL_GROUP sn/STUDENT_NUMBER`

<box type="tip" seamless>

**Tip:** A student must have a unique student number, so if the same student number is used for a new student, the user will be informed that the student already exists in the list.

</box>

Examples:
* `adds n/John Doe p/98765432 tg/G69 sn/E1234567I`
* `adds n/Betsy Crowe tg/G16 p/23456789 sn/a1234567z`

### Listing all students : `list`

Shows a list of all students in the contact list.

Format: `list`

### Editing a student : `edits`

Edits an existing student in the contact list.

Format: `edits INDEX [n/NAME] [p/PHONE] [tg/TUTORIAL_GROUP] [sn/STUDENT_NUMBER]`

* Edits the student at the specified `INDEX`. The index refers to the index number shown in the displayed contact list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
*  `edits 1 p/91234567 tg/Z19` Edits the phone number and tutorial group of the 1st person in the displayed list to be `91234567` and `Z19` respectively.
*  `edits 2 n/Betsy Crower` Edits the name of the 2nd person in the displayed list to be `Betsy Crower`.

### Locating student by name: `view`

View student(s) whose name matches the specified name exactly.

Format: `view NAME`

* The given name must match the student's name exactly. e.g `view John` will not return `John Doe`'s details.
* Name is case-sensitive. e.g. `view mary` will not return `Mary`'s details.
* Name is space-sensitive. e.g. `view John Doe` will not return `JohnDoe`'s details, and `view JohnNg` will not return `John Ng`'s details.
* If more than one student share the exact same name, all the students with that name will be displayed.

Examples: 
* `view Alex Yeoh` Displays the details of all students with the exact name `Alex Yeoh`.
* `view Lynette` Displays the details of all students with the exact name `Lynette`.

### Deleting a specific student : `deletes`

Deletes the specified student from the contact list.

Format: `deletes n/NAME [sn/STUDENT_NUMBER]`

* Deletes the student with the specified details.
* The student name refers to the student name shown in the displayed list of students.
* The student number refers to the student number shown in the displayed list of students.
* If a student number is provided, it **must be in the following format** A1234567B, a1234567B, A1234567b, a1234567b, ...​
* If there is more than one student with the exact same name, the student number must be provided to differentiate between them when deleting.

Examples:
* `deletes n/John Tan` deletes the student with the exact name `John Tan` from the contact list.
* `deletes n/Betsy sn/A0123456X` deletes the student with the exact name `Betsy` and student number `A0123456X` from the contact list.

<div style="page-break-after: always;"></div>

## Attendance Commands

<box type="tip" seamless>

**Tip:** Any command that has the optional field `sn/STUDENT_NUMBER` can be used without the student number if the student number is not known. However, if there are multiple students with the same name, the student number is required to differentiate between them.

</box>

### Marking Attendance : `markat`

Marks attendance of student for a particular date with the specified details.

Format: `markat n/NAME dt/DATE pr/ATTENDANCE [sn/STUDENT_NUMBER]`

### Marking Tutorial Group Attendance to Present : `markpresentall`

Marks all students in a tutorial group as present for a particular date

Format: `markpresentall tg/TUTORIAL_GROUP dt/DATE`

### Marking Tutorial Group Attendance to Absent: `markabsentall`

Marks all students in a tutorial group as absent for a particular date

Format: `markabsentall tg/TUTORIAL_GROUP dt/DATE`

### Deleting Attendance : `deleteat`

Deletes attendance of student for a particular date with the specified details.

Format : `deleteat n/NAME dt/DATE [sn/STUDENT_NUMBER]`

### Deleting Tutorial Group Attendance : `deleteatall`

Deletes attendance of all students in a tutorial group for a particular date

Format : `deleteatall tg/TUTORIAL_GROUP dt/DATE`

### Getting Attendance : `getat`

Gets attendance of student for a particular date

Format: `getat n/NAME dt/DATE [sn/STUDENT_NUMBER]`

### Getting Tutorial Group Attendance : `getattg`

Opens an attendance window for all students in a tutorial group for all dates that are currently known.
* Executing `undo` will close the last window opened by this command 

Format: `getattg tg/TUTORIAL_GROUP`

### Closing Attendance Window : `closeat`
Closes all attendance windows if any is currently open.

<div style="page-break-after: always;"></div>

## Assignment Commands

### Adding Assignments : `adda`

Adds an assignment to the specified student

Format: `adda n/NAME a/ASSIGNMENT_NAME d/DEADLINE [s/SUBMISSION_STATUS] [g/GRADE] [sn/STUDENT_NUMBER]`
  * Adds an assignment to the student with `NAME`. If student number is provided, this adds an assignment to the student
with `STUDENT_NUMBER` and `NAME`.
  * If there is more than one student with `NAME`, a student number must be provided.
  * Assignment names must be unique.
  * If not specified, `SUBMISSION_STATUS` defaults to `N` (i.e. not submitted).
  * If not specified, `GRADE` defaults to `NULL` (i.e. not graded).

### Deleting Assignments : `deletea`

Deletes an assignment for the specified student

Format: `adda n/NAME a/ASSIGNMENT_NAME [sn/STUDENT_NUMBER]`
* Deletes an assignment matching `ASSIGNMENT_NAME` to the student with `NAME`. If student number is provided, 
this deletes the assignment for the student with `STUDENT_NUMBER` and `NAME`.
* If there is more than one student with `NAME`, a student number must be provided.

### Editing Assignments : `edita`

Edits an assignment for the specified student

Format: `edita n/NAME a/ASSIGNMENT_NAME [d/DEADLINE] [s/SUBMISSION_STATUS] [g/GRADE] [sn/STUDENT_NUMBER]`
* Edits an assignment matching `ASSIGNMENT_NAME` to the student with `NAME`. If student number is provided,
  this edits the assignment for the student with `STUDENT_NUMBER` and `NAME`.
* If there is more than one student with `NAME`, a student number must be provided.
* Fields which are not specified will remain unchanged after the operation.

### Adding Assignments by Tutorial Group : `addatg`

Adds an assignment to all students from the specified tutorial group

Format: `attattg tg/TUTORIAL_GROUP a/ASSIGNMENT_NAME d/DEADLINE`
* Adds an assignment to the students with `TUTORIAL_GROUP`.
* Assignments default to not submitted and ungraded.

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
<div style="page-break-after: always;"></div>

## Command summary

| Action                                          | Format, Examples                                                                                                                                                             |
|-------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**                                         | `adds n/NAME p/PHONE_NUMBER tg/TUTORIAL_GROUP sn/A1234567J` <br> e.g., `adds n/P Diddy p/22224444 tg/G17 sn/A1234567J`                                                       |
| **Clear**                                       | `deleteall`                                                                                                                                                                  |
| **Delete**                                      | `deletes n/NAME [sn/STUDENT_NUMBER]`<br> e.g., `deletes n/John Doe sn/A1234567Z`                                                                                             |
| **Edit**                                        | `edits INDEX [n/NAME] [p/PHONE_NUMBER] [tg/TUTORIAL_GROUP] [sn/STUDENT_NUMBER]`<br> e.g.,`edits 2 n/James Lee p/12345678`                                                    |
| **View**                                        | `view KEYWORD [MORE_KEYWORDS]`<br> e.g., `view James Jake`                                                                                                                   |
| **List**                                        | `list`                                                                                                                                                                       |
| **Help**                                        | `help`                                                                                                                                                                       |
| **Undo**                                        | `undo`                                                                                                                                                                       
| **Mark Attendance**                             | `markat n/NAME dt/DATE pr/ATTENDANCE [sn/STUDENT_NUMBER]`<br> e.g., `markat n/John Doe d/2021-10-10 pr/P sn/A1234567Z`                                                       |
| **Mark Present for Tutorial Group**             | `markpresentall tg/TUTORIAL_GROUP dt/DATE`<br> e.g., `markpresentall tg/G17 d/2021-10-10`                                                                                    |
| **Mark Absent for Tutorial Group**              | `markabsentall tg/TUTORIAL_GROUP dt/DATE`<br> e.g., `markabsentall tg/G17 d/2021-10-10`                                                                                      |
| **Delete Student's Attendance**                 | `deleteat n/NAME dt/DATE [sn/STUDENT_NUMBER]`<br> e.g., `deleteat n/John Doe d/2021-10-10 sn/A1234567Z`                                                                      |
| **Delete Tutorial Group Attendance**            | `deleteatall tg/TUTORIAL_GROUP dt/DATE`<br> e.g., `deleteatall tg/G17 d/2021-10-10`                                                                                          |
| **Get Attendance**                              | `getat n/NAME dt/DATE [sn/STUDENT_NUMBER]`<br> e.g., `getat n/John Doe d/2021-10-10 sn/A1234567Z`                                                                            |
| **Get Tutorial Group Attendance**               | `getattg tg/TUTORIAL_GROUP`<br> e.g., `getattg tg/G17`                                                                                                                       |
| **Close All Tutorial Group Attendance Windows** | `closeat`                                                                                                                                                                    |
| **Add Assignment**                              | `adda n/NAME a/ASSIGNMENT_NAME d/DEADLINE [s/SUBMISSION_STATUS] [g/GRADE] [sn/STUDENT_NUMBER]`<br> e.g., `adda n/John Doe a/Assignment 1 d/2021-10-10 s/N g/100 sn/A1234567Z` |
| **Delete Assignment**                           | `deletea n/NAME a/ASSIGNMENT_NAME [sn/STUDENT_NUMBER]`<br> e.g., `deletea n/John Doe a/Assignment 1 sn/A1234567Z`                                                            |
| **Edit Assignment**                             | `edita n/NAME a/ASSIGNMENT_NAME [d/DEADLINE] [s/SUBMISSION_STATUS] [g/GRADE]`<br> e.g., `edita n/John Doe a/Assignment 1 d/2021-10-10 s/Y g/90`                              |
| **Add Assignment by Tutorial Group**            | `addatg tg/TUTORIAL_GROUP a/ASSIGNMENT_NAME d/DEADLINE `<br> e.g., `addatg tg/T15 a/Assignment 1 d/2021-10-10`                                                       |
