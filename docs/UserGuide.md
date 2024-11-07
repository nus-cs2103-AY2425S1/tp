---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# TAHub Contacts User Guide

If you are a busy Computer Science student juggling Teaching Assistant roles and
struggling to keep track of your many
students, *TAHub Contacts* is a desktop application for you to easily manage your
student contact details!

<box type="tip" theme="light">
<md>
    While it has a *GUI* (Graphical User Interface), most of the user interactions
    happen using a *CLI* (Command Line Interface) with typed commands. A perfect
    fit if you’re already a wizard at the keyboard.
</md>
<br>
<md>
    (_Or if you've already taken/suffered/enjoyed `CS2030S`_)
</md>
</box>

--------------------------------------------------------------------------------------------------------------------

## Table of Contents

- [Quick Start :rocket:](#quick-start :rocket:)
- [Features :computer:](#features :computer:)

- Viewing help : [`help`](#viewing-help--help)
- Listing all students : [`list`](#listing-all-persons--list)
- Clearing all entries : [`clear`](#clearing-all-entries--clear)
- Adding a student : [`add-student`](#adding-a-person-add)
- Editing a student : [`edit-student`](#editing-a-person--edit)
- Finding a student: [`find-student`](#locating-persons-by-name-find)
- Deleting a student : [`delete-student`](#deleting-a-person--delete)
- Adding a course: [`add-course`](#)
- Editing a course: [`edit-course`](#)
- Deleting a course : [`delete-course`](#)
- Adding a course tutorial : [`add-tutorial`](#)
- Deleting a course tutorial : [`delete-tutorial`](#)
- Enrolling a student : [`enroll`](#)
- Unenrolling a student : [`unenroll`](#)
- Marking attendance : [`attend-present`](#)
- Marking absence : [`attend-absent`](#)
- Clear attendance : [`attend-clear`](#)
- Exiting the program : [`exit`](#exiting-the-program--exit)
- [Saving the data 💾](#saving-the-data)
- ADVANCED: Editing the data file
- [FAQ :grey_question:](#faq :grey_question:)
- [Known Issues :bug:](#known-issues :bug:)
- [Command Summary :ledger:](#command-summary :ledger:)

## Quick start :rocket:

1. Ensure you have Java `17` or above installed in your Computer.
2. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).
3. Copy the file to the folder you want to use as the *home folder* for your AddressBook.
   - Make sure that this folder is **empty**.
4. Open a command terminal
   ([Windows](https://www.lifewire.com/how-to-open-command-prompt-2618089) |
   [MacOS](https://support.apple.com/en-sg/guide/terminal/apd5265185d-f365-44cb-8b09-71a064a42125/mac) |
   [Linux](https://www.youtube.com/watch?v=dQw4w9WgXcQ)), `cd` into the folder
   you put the jar file in, and
   use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app
   contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g.
   typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   - `list` : Lists all contacts.

   - `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   - `delete 3` : Deletes the 3rd contact shown in the current list.

   - `clear` : Deletes all contacts.

   - `exit` : Exits the app.

6. Refer to the [Features](#features :computer:) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features :computer:

<box type="info" seamless>

**Notes about the command format: :fa-solid-lightbulb:**<br>

- Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

- Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

- Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as `` (i.e. 0 times), `t/friend`, `t/friend t/family`
  etc.

- Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME`
  is also acceptable.

- Extraneous parameters for commands that do not take in parameters (such as
  `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

- If you are using a PDF version of this document, be careful when copying and
  pasting commands that span multiple lines as space characters surrounding
  line-breaks may be omitted when copied over to the application.
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

- `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
- `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

- Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
- At least one of the optional fields must be provided.
- Existing values will be updated to the input values.
- When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
- You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:

- `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
- `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

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

### Adding a course: `course-add`

Adds a course to TAHub Contacts.

Format: `course-add c/COURSE_CODE n/COURSE_NAME`

Examples:

- course-add c/CS1101S n/Programming Methodology 1
- course-add c/MA1522 n/Linear Algebra

Notes:

- COURSE_CODE must be in the form XXYYYYZ where XX is 2 uppercase letters, YYYY is a 4 digit number, Z is an optional uppercase letter
- COURSE_NAME must only contain alphanumeric characters or spaces

### Editing a course: `course-add`

Edit by changing the name of a course in TAHub Contacts.

Format: `course-edit c/COURSE_CODE n/COURSE_NAME`

Examples:

- course-edit c/CS1101S n/Programming Methodology 2
- course-edit c/MA1522 n/Linear Algebra 2

Notes:

- COURSE_CODE must be an existing course code
- COURSE_NAME is the new course name and must only contain alphanumeric characters or spaces
- Note that it is not possible to edit the course code

### Deleting a course: `course-delete`

Deletes a course in TAHub Contacts.

Format: `course-delete c/COURSE_CODE`

Examples:

- course-delete c/CS1101S

Notes:

- COURSE_CODE must be an existing course code

<box type="warning" seamless>
**Caution:** Deleting a course will also delete all tutorial groups, attendance and student associations related to the course.
</box>

### Enrolling a student : `enroll`

Enrolls a student in a particular course and tutorial group.

Format: `enroll m/MATRICULATION_NUMBER c/COURSE_CODE tut/TUTORIAL_ID`

Examples:

- enroll m/A1234567Y c/CS1101S tut/T12
- enroll m/A1262929T c/CS2030 tut/T08

Notes:

- MATRICULATION_NUMBER must be a valid NUS matriculation number in the form AXXXXXXXB, where A is the fixed as A, B is any uppercase character, and XXXXXXX is any 7 integers.
- COURSE_CODE must be in the form XXYYYYZ where XX is 2 uppercase letters, YYYY is a 4 digit number, Z is an optional uppercase letter
- TUTORIAL_ID should be in the form TXX, where T is fixed as T, while XX is a 2 digit integer from 01 to 99.
- Students already enrolled in this course and tutorial cannot be enrolled again

### Unenrolling a student : `unenroll`

Unenrolls a student from a particular course and tutorial group that he/she is in

Format: `unenroll m/MATRICULATION_NUMBER c/COURSE_CODE tut/TUTORIAL_ID`

Examples:

- unenroll m/A1234567Y c/CS1101S tut/T12
- unenroll m/A1262929T c/CS2030 tut/T08

Notes:

- MATRICULATION_NUMBER must be a valid NUS matriculation number in the form AXXXXXXXB, where A is the fixed as A, B is any uppercase character, and XXXXXXX is any 7 integers.
- COURSE_CODE must be in the form XXYYYYZ where XX is 2 uppercase letters, YYYY is a 4 digit number, Z is an optional uppercase letter
- TUTORIAL_ID should be in the form TXX, where T is fixed as T, while XX is a 2 digit integer from 01 to 99.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

TAHub data is saved in the hard disk automatically after any command that changes
the data. You don’t need to save manually!

### Editing the data file

Data are saved automatically as a JSON file `[JAR file location]/data/TAHub.json`.
and `[JAR file location]/data/courselist.json`
Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, TAHub Contacts will
discard all data and start with an empty data file at the next run. Hence, it is
recommended to take a backup of the file before editing it.
<br>
<br>
Furthermore, certain edits can cause TAHub Contacts to behave in unexpected ways
(e.g., if a value entered is outside the acceptable range). Therefore, edit the
data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[Future]`

*TBC :D*

--------------------------------------------------------------------------------------------------------------------

## FAQ :grey_question:

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file
it creates with the file that contains the data of your previous TAHub Contacts home
folder.

**Q**: I accidentally performed the wrong command. Is there a way for me to
revert the change?<br>
**A**: Unfortunately, we have not implemented an undo command for TAHub contacts.
:pensive:

--------------------------------------------------------------------------------------------------------------------

## Known issues :bug:

1. **When using multiple screens**, if you move the application to a secondary
   screen, and later switch to using only the primary screen, the GUI will open
   off-screen. The remedy is to delete the `preferences.json` file created by
   the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use
   the `Help` menu, or the keyboard shortcut `F1`) again, the original Help
   Window will remain minimized, and no new Help Window will appear. The
   remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary :ledger:

<!-- markdownlint-disable MD013 -->

|           Action          |                                                                                                  Format, Examples                                                                                                   |
|:--------------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Help                      | `help`                                                                                                                                                                                                              |
| List Students             | `list`                                                                                                                                                                                                              |
| Clear                     | `clear`                                                                                                                                                                                                             |
| Add Student               | `add-student m/MATRICULATION_NUMBER n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…`<br>e.g.`add-student m/A0177310M n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague` |
| Find Students by Name     | `find KEYWORD [MORE_KEYWORDS]`<br>e.g.`find James Jake`                                                                                                                                                             |
| Edit Student              | `edit-student m/MATRICULATION_NUMBER [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…`<br>e.g.`edit-student m/A0296210X n/James Lee e/jameslee@example.com`                                                        |
| Delete Student            | `delete-student m/MATRICULATION_NUMBER`<br>e.g.`delete-student m/A0296210X`                                                                                                                                         |
| Add Course                | `add-course c/COURSE_CODE n/COURSE_NAME`<br>e.g.`add c/CS1101S n/Programming Methodology 1`                                                                                                                         |
| Edit Course               | `edit-course c/COURSE_CODE n/NAME`<br>e.g.`edit-course c/CS1101S n/Programming Basics`                                                                                                                              |
| Delete Course             | `delete-course c/COURSE_CODE n/NAME`<br>e.g.`delete-course c/CS3230`                                                                                                                                                |
| Add Course Tutorial       | `add-tutorial c/COURSE_CODE t/TUTORIAL_ID`<br>e.g.`add c/CS1101S t/T23`                                                                                                                                             |
| Delete Course Tutorial    | `delete-tutorial c/COURSE_CODE t/TUTORIAL_ID`<br>e.g.`delete-tutorial c/CS1101S t/T23`                                                                                                                              |
| Enroll Student            | `enroll m/MATRICULATION_NUMBER c/COURSE_CODE t/TUTORIAL_ID`<br>e.g.`enroll m/A1234567Y c/CS1101S  t/T10A`                                                                                                           |
| Unenroll Student          | `unenroll m/MATRICULATION_NUMBER c/COURSE_CODE t/TUTORIAL_ID`<br>e.g.`unenroll m/A1234567Y c/CS1101S  t/T10A`                                                                                                       |
| Mark Attendance           | `attend attend m/MATRICULATION_NUMBER c/COURSE_CODE t/TUTORIAL_ID`<br>e.g.`attend attend m/A1234567Y c/CS1101S  t/T10A`                                                                                             |
| Mark Absence              | `attend-absent m/MATRICULATION_NUMBER c/COURSE_CODE t/TUTORIAL_ID`<br>e.g.`attend-absent m/A1234567Y c/CS1101S  t/T10A`                                                                                             |
| Remove Attendance Session | `attend-remove m/MATRICULATION_NUMBER c/COURSE_CODE t/TUTORIAL_ID`<br>e.g.`attend-remove m/A1234567Y c/CS1101S  t/T10A`                                                                                             |
| Clear Attendance          | `attend-clear m/MATRICULATION_NUMBER c/COURSE_CODE t/TUTORIAL_ID`<br>e.g.`attend-clear m/A1234567Y c/CS1101S  t/T10A`                                                                                               |
| Exit                      | `exit`                                                                                                                                                                                                              |

<!-- markdownlint-enable MD013 -->