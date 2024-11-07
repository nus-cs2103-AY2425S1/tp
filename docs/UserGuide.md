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

- Viewing help : [`help`](#help)
- Listing all students : [`list`](#list)
- Clearing all entries : [`clear`](#clear)
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

### Data Formats :fa-solid-warning:

<!-- markdownlint-disable MD013 -->

<box type="warning">

**All** input fields **must** follow the following data formats:
</box>

| Field | Format |
| :-----: | :----------------- |
| `MATRICULATION_NUMBER` | must be a valid **NUS** matriculation number in the form `AxxxxxxxB`, where `A` is the fixed as 'A', `B` is any *uppercase* character, and `xxxxxxx` is any 7 integers. |
| `NAME` | must only contain **alphanumeric characters and spaces**, and **not be blank**. |
| `PHONE_NUMBER` | must contain **numbers**, and it should be **at least 3 digits long**. |
| `EMAIL` | must be a [valid email format](https://help.xmatters.com/ondemand/trial/valid_email_format.htm) |
| `COURSE_CODE` | must be in the form `AAxxxxB` where `AA` is 2 *uppercase* letters, `xxxx` is a 4-digit number, `B` is an **optional** *uppercase* letter. |
| `COURSE_NAME` | must only contain **alphanumeric characters and spaces**, and **not be blank**. |

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

### Main

The main commands for TAHub Contacts.

<a name="help">
  <panel header="#### Viewing help : `help`" expanded no-close no-switch>

  Shows a message explaning how to access the help page.

  ![help message](images/helpMessage.png)

  <box type="definition" seamless><md>
  Format: **`help`**
  </md></box>

  </panel>
</a>

<a name="list">
  <panel header="#### Listing all students : `list`" expanded no-close no-switch>

  Shows a list of all students saved in TAHub Contacts in the GUI.

  <box type="definition" seamless><md>
  Format: **`list`**
  </md></box>

  </panel>
</a>

<a name="clear">
  <panel header="#### Clearing all entries : `clear`" expanded no-close no-switch>

  Clears all student entries from TAHub Contacts.

  <box type="definition" seamless><md>
  Format: **`clear`**
  </md></box>

  <box type="important" theme="danger"><md>
  **WARNING**: this operation is **irreversible**. You may lose all your data!
  </md></box>

  </panel>
</a>

<a name="exit">
  <panel header="#### Exiting the program : `exit`" expanded no-close no-switch>

  Exits the program.

  <box type="definition" seamless><md>
  Format: **`exit`**
  </md></box>

  </panel>
</a>

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

### Students

<a name="add-student">
<panel header="#### Adding a student : `add-student`" expanded no-close no-switch>

Shows a list of all students saved in TAHub Contacts in the GUI.

<box type="definition" seamless><md>
Format: **`add-student m/MATRICULATION_NUMBER /NAME p/PHONE_NUMBER e/EMAIL
a/ADDRESS [t/TAG]…​`**
</md></box>

<box type="tip" seamless><md>
**Tip:** A student can have any number of tags (including 0).
</md></box>

| **Examples** |
| :--- |
| `add-student m/A0296210X n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` |
| `add-student m/A0315310L n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal` |

</panel></a>

<a name="edit-student">
<panel header="#### Editing a student : `edit-student`" expanded no-close no-switch>

Edits an existing student in TAHub Contacts.

<box type="definition" seamless><md>
Format: **`edit-student m/MATRICULATION_NUMBER [n/NAME] [p/PHONE] [e/EMAIL]
[a/ADDRESS] [t/TAG]…​​`**
</md></box>

- Edits the student with the specified `MATRICULATION_NUMBER`. The `MATRICULATION_NUMBER` must be the **matriculation number** of an **existing student**.
- **At least one** of the optional fields must be provided.
- Existing values will be updated to the input values.
- When editing tags, the existing tags of the student will be removed i.e adding of tags is not cumulative.

<box type="tip" seamless><md>
**Tip:** You can remove all the student’s tags by typing `t/` without specifying any tags after it.
</md></box>

| **Examples** |
| :--- |
| `edit-student m/A0296210X p/91234567 e/johndoe@example.com` edits the phone number and email address of the student with `MATRICULATION_NUMBER` of `A0296210X` to be `91234567` and `johndoe@example.com` respectively. |
| `edit-student m/A0123467X n/Betsy Crower t/` edits the name of the the student with `MATRICULATION_NUMBER` of `A0123467X` to be `Betsy Crower` and *clears* all existing tags. |

</panel>
</a>

<a name="find-student">
<panel header="#### Finding a student : `find-student`" expanded no-close no-switch>

Finds students whose **names** contain any of the given keywords.

<box type="definition" seamless><md>
Format: **`find-student KEYWORD [MORE_KEYWORDS]​​`**
</md></box>

- The search is **case-insensitive**.
  - e.g `hans` will match `Hans`
- The order of the keywords does not matter.
  - e.g. `Hans` Bo will match Bo `Hans`
- Only the **name** is searched.
- Only **full words** will be **matched**.
  - e.g. `Han` will not match `Hans`
- **All** students matching **at least one keyword** will be returned (i.e. `OR` search).
  - e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

| **Examples** |
| :--- |
| `find-student` John returns `john` and `John Doe`. |
| `find-student alex david` returns `Alex Yeoh`, `David Li`. <br> ![result for 'find alex david'](images/findAlexDavidResult.png)|

</panel>
</a>

<a name="delete-student">
<panel header="#### Deleting a student : `delete-student`" expanded no-close no-switch>

Deletes the specified student from TAHub Contacts.

<box type="definition" seamless><md>
Format: **`delete-student m/MATRICULATION_NUMBER​​`**
</md></box>

- Deletes the student with the specified `MATRICULATION_NUMBER`.
- The `MATRICULATION_NUMBER` must be the **matriculation number** of an **existing student**.

| **Examples** |
| :--- |
| `delete-student m/A0296210X` deletes the student with `MATRICULATION_NUMBER` of `A0296210X` in TAHub Contacts. |

</panel>
</a>

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

### Course

<box typ="warning">
Reminder: course **code** and **name** must follow the [data format](#data-formats-fa-solid-warning)!
</box>

<a name="course-add">
<panel header="#### Adding a course: `course-add`" expanded no-close no-switch>

Adds a course to TAHub Contacts.

<box type="definition" seamless><md>
Format: **`course-add c/COURSE_CODE n/COURSE_NAME`**
</md></box>

| **Examples** |
| :--- |
| `course-add c/CS1101S n/Programming Methodology 1` |
| `course-add c/MA1522 n/Linear Algebra` |

</panel>
</a>

<a name="course-edit">
<panel header="#### Editing a course: `course-edit`" expanded no-close no-switch>

Edit by changing the name of a course in TAHub Contacts.

<box type="definition" seamless><md>
Format: **`course-edit c/COURSE_CODE n/COURSE_NAME`**
</md></box>

- `COURSE_CODE` must be an existing course code
- `COURSE_NAME` is the new course name and must only contain alphanumeric characters or spaces **and** follow the `COURSE_NAME` [format](#data-formats-fa-solid-warning).
- Note that it is **not possible** to **edit** the course code. If you want to do so, create a **new course** with the different code.

| **Examples** |
| :--- |
| `course-edit c/CS1101S n/Programming Methodology 2` |
| `course-edit c/MA1522 n/Linear Algebra 2` |

</panel>
</a>

<a name="course-delete">
<panel header="#### Deleting a course: `course-delete`" expanded no-close no-switch>

Deletes a course in TAHub Contacts.

<box type="definition" seamless><md>
Format: **`course-delete c/COURSE_CODE`**
</md></box>

- `COURSE_CODE` must be an existing course code

| **Examples** |
| :--- |
| `course-delete c/CS1101S` |

<box type="warning" seamless>

**Caution:** Deleting a course will also delete all tutorial groups, attendance and student associations related to the course.
</box>

</panel>
</a>

<a name="course-delete">
<panel header="#### Deleting a course: `course-delete`" expanded no-close no-switch>

Deletes a course in TAHub Contacts.

<box type="definition" seamless><md>
Format: **`course-delete c/COURSE_CODE`**
</md></box>

- `COURSE_CODE` must be an existing course code

| **Examples** |
| :--- |
| `course-delete c/CS1101S` |

<box type="warning" seamless>
**Caution:** Deleting a course will also delete all tutorial groups, attendance and student associations related to the course.
</box>

</panel>
</a>

<a name="course-delete">
<panel header="#### Deleting a course: `course-delete`" expanded no-close no-switch>

Deletes a course in TAHub Contacts.

<box type="definition" seamless><md>
Format: **`course-delete c/COURSE_CODE`**
</md></box>

- `COURSE_CODE` must be an existing course code

| **Examples** |
| :--- |
| `course-delete c/CS1101S` |

<box type="warning" seamless>
**Caution:** Deleting a course will also delete all tutorial groups, attendance and student associations related to the course.
</box>

</panel>
</a>

#### Enrolling a student : `enroll`

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

#### Unenrolling a student : `unenroll`

Unenrolls a student from a particular course and tutorial group that he/she is in

Format: `unenroll m/MATRICULATION_NUMBER c/COURSE_CODE tut/TUTORIAL_ID`

Examples:

- unenroll m/A1234567Y c/CS1101S tut/T12
- unenroll m/A1262929T c/CS2030 tut/T08

Notes:

- MATRICULATION_NUMBER must be a valid NUS matriculation number in the form AXXXXXXXB, where A is the fixed as A, B is any uppercase character, and XXXXXXX is any 7 integers.
- COURSE_CODE must be in the form XXYYYYZ where XX is 2 uppercase letters, YYYY is a 4 digit number, Z is an optional uppercase letter
- TUTORIAL_ID should be in the form TXX, where T is fixed as T, while XX is a 2 digit integer from 01 to 99.

### Data

#### Saving the data

TAHub data is saved in the hard disk automatically after any command that changes
the data. You don’t need to save manually!

#### Editing the data file

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

### Future

#### Archiving data files

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
| Exit                      | `exit`                                                                                                                                                                                                              |
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

<!-- markdownlint-enable MD013 -->