---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---


<!-- no toc -->

# TAHub Contacts User Guide

## Introduction

Hi there! :wave:

If *you* are a busy Computer Science student juggling Teaching Assistant roles and
struggling to keep track of *your* many
students, *TAHub Contacts* is a desktop application for *you* to easily manage *your*
student contact details!

Think about *TAHub Contacts* as a little helper, residing on *your* computer,
made just for *you*, the Computing TA.

<box type="tip" theme="light">

While it has a *GUI* (Graphical User Interface), most of the user interactions
happen using a *CLI* (Command Line Interface) with typed commands. A perfect
fit if you’re already a wizard at the keyboard.

Also preferable would be being comfortable with the concept of entering
configurable, text-based commands into a terminal.

(*Or if you've already taken/suffered/enjoyed `CS2030S`*)

</box>

### Overview

*TAHub Contacts* allows you to easily:

- Manage your students
- Organise them by course and tutorial group
- Mark attendance

Interested? Head down to [quick start](#QUICKSTART) to get started!

<!-- This section exists to correct a Markbind rendering bug. Don't worry about it-->
<a name="TEMPFIX"><md>
![](404.png)
</md></a>

--------------------------------------------------------------------------------------------------------------------

## Table of Contents

- [Quick Start :rocket:](#QUICKSTART)
  - [Example TA Workflow](#example-ta-workflow)
- [Features :computer:](#FEATURES)
  - [Data Formats](#data-formats)
  - [MAIN Commands](#main-commands)
    - [Viewing help : `help`](#help)
    - [Listing all students : `list`](#list)
    - [Clearing all entries : `clear`](#clear)
    - [Exiting the program : `exit`](#exit)
  - [STUDENT](#student-commands)
    - [Adding a student : `person-add`](#person-add)
    - [Editing a student : `person-edit`](#person-edit)
    - [Finding a student: `person-find`](#person-find)
    - [Deleting a student : `person-delete`](#person-delete)
  - [COURSE](#course-commands)
    - [Adding a course: `course-add`](#course-add)
    - [Editing a course: `course-edit`](#course-edit)
    - [Deleting a course : `course-delete`](#course-edit)
  - [ENROLLMENT](#enrollment-commands)
    - [Enrolling a student : `enroll`](#enroll)
    - [Unenrolling a student : `unenroll`](#unenroll)
  - [ATTENDANCE](#attendance-commands)
    - [Marking presence : `attend-present`](#attend-present)
    - [Marking absence : `attend-absent`](#attend-absent)
    - [Removing last attendance : `attend-remove`](#attend-remove)
    - [Clearing attendance : `attend-clear`](#attend-clear)
  - [DATA](#data)
    - [Saving the data](#saving-the-data)
    - [Editing data file](#editing-the-data-file)
- [FAQ :grey_question:](#FAQ)
- [Known Issues :bug:](#KNOWN-ISSUES)
- [Command Summary :ledger:](#CMD-SUMMARY)

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

<a name="QUICKSTART">

## Quick start :rocket:

1. Ensure you have Java `17` or above installed in your Computer.
   - How to check your Java version? Run `java -version` in your terminal.
   - Don't have Java `17` or above, download [here](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).
2. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-F14B-2/tp/releases).
3. Copy the file to the folder you want to use as the *home folder* for the program.
   - Make sure that this folder is **empty**. (Program files will be saved in it!)
4. Open a command terminal
   ([Windows](https://www.lifewire.com/how-to-open-command-prompt-2618089) |
   [MacOS](https://support.apple.com/en-sg/guide/terminal/apd5265185d-f365-44cb-8b09-71a064a42125/mac) |
   [Linux](https://www.youtube.com/watch?v=dQw4w9WgXcQ)), `cd` into the folder
   you put the jar file in, and
   run `java -jar tahub-contacts.jar` to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app
   contains some sample data.<br>
   ![img_1.png](img_1.png)

5. Type the command in the command box and press **Enter** to execute it. e.g.
   typing **`help`** and pressing **Enter** will open the help window,
   which links you back to [this page](https://ay2425s1-cs2103t-f14b-2.github.io/tp/UserGuide.html).<br>
   Some example commands you can try:

   - `list` : Lists all students.
   - `person-add m/A0296210X n/John Tan p/98765432 e/johnt@email.com a/John street, block 123, #01-01`
      : Adds a contact named `John Tan` to TAHub Contacts.
   - `person-delete  m/A0296210X` : Deletes the student with the matriculation number `A0296210X`.
      In this case, the `John Tan` that we just added.
   - `clear` : Deletes all contacts.
   - `exit` : Exits the app.

6. Refer to the [Features](#FEATURES) below for the *juicy* details of each command.
   Alternatively check the [Command Summary](#CMD-SUMMARY) for a quick
   list of the available commands. Have fun!

</a>

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

### Example TA Workflow

<box type="info" seamless>

Check this section out for an example on how you might use *TAHub Contacts* as a TA!

</box>

<box type="tip" theme="light">

The specific commands in this section, e.g. [`clear`](#clear), are
[hyperlinked](https://www.youtube.com/watch?v=dQw4w9WgXcQ) to their respective
command feature sections. Use them to learn more!
</box>

1. Make sure you're starting from an **empty** student list. Use the command
   <u>[`clear`](#clear)</u>
   if it isn't.
2. Let's say you have two students from your **T05** class for the course
   **CS2005, Computer Examples**,
   **Jane**, and **John**.
3. Add the two students into TAHub Contacts using
   <u>[`person-add`](#person-add)</u>
   :
   - `person-add m/A1234567Q n/Jane p/99871234 e/jahn@email.com a/12 AMK Ave`
   - `person-add m/A2233445X n/John p/88112233 e/john@email.com a/200 Bedok Road`
4. Now add the course `CS2005` using
   <u>[`course-add`](#course-add)</u>
   - `course-add c/CS2005 n/Computer Examples`
5. Then, add your students to their class using
   <u>[`enroll`](#enroll)</u>
   .
   - **Jane**: `enroll m/A1234567Q c/CS2005 tut/T05`
   - **John**: `enroll m/A2233445X c/CS2005 tut/T05`
6. You should now see the following in *TAHub Contacts*.

  <div style="text-align:center">
    <img src="images/exampleWorkflow1.png" alt="Example Workflow 1" width="70%">
  </div>

7. Fast forward a bit, you just had your first tutorial session with this class.
   **Jane** showed up, but **John** didn't. So it's time to mark attendance!
8. Use the
   <u>[`attend-present`](#attend-present)</u>
   and
   <u>[`attend-absent`](#attend-absent)</u>
   commands:
   - **Jane**: `attend-present m/A1234567Q c/CS2005 tut/T05`
   - **John**: `attend-absent m/A2233445X c/CS2005 tut/T05`
9. You just recorded their attendance!
   Click on the `Attendance` button to see their attendance. For instance,
   here's **Jane**'s:

   <div style="text-align:center">
      <img src="images/exampleWorkflow2.png" alt="Example Workflow 2" width="70%">
    </div>

This is just a simple example. For more details, check the [Features](#FEATURES)
section below.

<br>

:fa-solid-arrow-up: Back to [Table of Contents](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

<a name="FEATURES">

## Features :computer:

<box type="info" seamless>

**Notes about the command format: :fa-solid-lightbulb:**<br>

- Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `person-add m/MATRICULATION_NUMBER`, `MATRICULATION_NUMBER` is a
  parameter which can be used as `person-add m/A1234567L`.

- Items in square brackets are optional. e.g.
  - `n/NAME [a/ADDRESS]` can be used as `n/John Doe a/Nasi Lemak Street` or as `n/John Doe`.

- Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME`
  is also acceptable.

- Address Length: If the `a/ADDRESS` parameter is too long, it may overflow in the user interface. To avoid display issues, keep addresses concise.

- Extraneous parameters for commands that do not take in parameters (such as
  `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

- If you are using a PDF version of this document, be careful when copying and
  pasting commands that span multiple lines as space characters surrounding
  line-breaks may be omitted when copied over to the application.
</box>

</a>

### Data Formats

<!-- markdownlint-disable MD013 -->

<box type="warning">

**All** input fields in the table below **must** follow the specified data formats. Input fields not listed may accept any non-blank value.
</box>

| Field | Format                                                                                                                                                                  |
| :-----: |:------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `MATRICULATION_NUMBER` | must be a valid **NUS** matriculation number in the form `AxxxxxxxB`, where `A` is the fixed as 'A', `B` is any *uppercase* character, and `xxxxxxx` is any 7 integers. |
| `NAME` | must only contain **alphanumeric characters and spaces**, and **not be blank**.                                                                                         |
| `PHONE_NUMBER` | must only contain **numbers**, and it should be **at least 3 digits long**.                                                                                             |
| `EMAIL` | must be a [valid email format](https://help.xmatters.com/ondemand/trial/valid_email_format.htm).                                                                        |
| `COURSE_CODE` | must be in the form `A+xxxxB` where `A+` is 1 or more *uppercase* letters, `xxxx` is a 4-digit number, `B` is an **optional** *uppercase* letter.                       |
| `COURSE_NAME` | must only contain **alphanumeric characters and spaces**, and **not be blank**.                                                                                         |
| `TUTORIAL_ID` | should be in the form `Txx`, where `T` is fixed as 'T', while `xx` is a 2 digit integer from 01 to 99.                                                                  |

:fa-solid-arrow-up: Back to [Table of Contents](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

### Main Commands

The main basic commands for *TAHub Contacts*.

<a name="help">
  <panel header="#### Viewing help : `help`" expanded no-close no-switch>

  Shows a message explaining how to access the help page.

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

<br>

:fa-solid-arrow-up: Back to [Table of Contents](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

### Student Commands

This is for managing your individual **students**,
modifying their attributes and information.

See [Course Commands](#course-commands) or [Enrollment Commands](#enrollment-commands)
regarding your students' **courses** and **tutorial groups**.

<box type="warning">

Reminder: follow the [:fa-solid-link: data formats](#data-formats)!
</box>

<a name="person-add">
<panel header="#### Adding a student : `person-add`" expanded no-close no-switch>

Adds a student into TAHub Contacts.

<box type="definition" seamless><md>
Format: **`person-add m/MATRICULATION_NUMBER /NAME p/PHONE_NUMBER e/EMAIL
a/ADDRESS`**
</md></box>

| **Examples**                                                                                        |
|:----------------------------------------------------------------------------------------------------|
| `person-add m/A0296210X n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` |
| `person-add m/A0315310L n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567` |

</panel></a>

<a name="person-edit">
<panel header="#### Editing a student : `person-edit`" expanded no-close no-switch>

Edits an existing student in TAHub Contacts.

<box type="definition" seamless><md>
Format: **`person-edit m/MATRICULATION_NUMBER [n/NAME] [p/PHONE] [e/EMAIL]
[a/ADDRESS]`**
</md></box>

- Edits the student with the specified `MATRICULATION_NUMBER`. The `MATRICULATION_NUMBER` must be the **matriculation number** of an **existing student**.
- **At least one** of the optional fields must be provided.
- Existing values will be updated to the input values.

| **Examples** |
| :--- |
| `person-edit m/A0296210X p/91234567 e/johndoe@example.com` edits the phone number and email address of the student with `MATRICULATION_NUMBER` of `A0296210X` to be `91234567` and `johndoe@example.com` respectively. |

</panel>
</a>

<a name="person-find">
<panel header="#### Finding a student : `person-find`" expanded no-close no-switch>

Finds students whose **names** contain any of the given keywords.

<box type="definition" seamless><md>
Format: **`person-find KEYWORD [MORE_KEYWORDS]​​`**
</md></box>

- The search is **case-insensitive**.
  - e.g `hans` will match `Hans`
- The order of the keywords does not matter.
  - e.g. `Hans Bo` will match `Bo Hans`
- Only the **name** is searched.
- Only **full words** will be **matched**.
  - e.g. `Han` will not match `Hans`
- **All** students matching **at least one keyword** will be returned (i.e. `OR` search).
  - e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

| **Examples** |
| :--- |
| `person-find` John returns `john` and `John Doe`. |
| `person-find alex david` returns `Alex Yeoh`, `David Li`. <br> ![result for 'find alex david'](images/findAlexDavidResult.png)|

</panel>
</a>

<a name="person-delete">
<panel header="#### Deleting a student : `person-delete`" expanded no-close no-switch>

Deletes the specified student from TAHub Contacts.

<box type="definition" seamless><md>
Format: **`person-delete m/MATRICULATION_NUMBER​​`**
</md></box>

- Deletes the student with the specified `MATRICULATION_NUMBER`.
- The `MATRICULATION_NUMBER` must be the **matriculation number** of an **existing student**.

| **Examples** |
| :--- |
| `person-delete m/A0296210X` deletes the student with `MATRICULATION_NUMBER` of `A0296210X` in TAHub Contacts. |

</panel>
</a>

<br>

:fa-solid-arrow-up: Back to [Table of Contents](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

<a name="course-commands">

### Course Commands

This is for managing the **courses** you are (or previously were, *cuz why not*)
teaching.

<box type="warning">

Reminder: where used, parameters must follow the [:fa-solid-link: data format](#data-formats)!
</box>

<a name="course-add">
<panel header="#### Adding a course: `course-add`" expanded no-close no-switch>

Adds a course to TAHub Contacts.

<box type="definition" seamless><md>
Format: **`course-add c/COURSE_CODE n/COURSE_NAME`**
</md></box>

- `COURSE_CODE` must be unique and follow the `COURSE_CODE` [format](#data-formats)
- `COURSE_NAME` must follow the `COURSE_NAME` [format](#data-formats)

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

- `COURSE_CODE` must be an existing course code.
- `COURSE_NAME` is the new course name and must follow the `COURSE_NAME` [format](#data-formats).
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

- `COURSE_CODE` must be an existing course code.

| **Examples** |
| :--- |
| `course-delete c/CS1101S` |

<box type="important">

**Caution:** Deleting a course will also delete all tutorial groups, attendance and student associations related to the course.
</box>

The courses are stored in file `data/courselist.json` for easy import/export.

<box type="important">

**Caution:** If any of the fields in `courselist.json` are invalid, no courses will be loaded and may result in data loss.
</box>

</panel>
</a>

<br>

:fa-solid-arrow-up: Back to [Table of Contents](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

### Enrollment Commands

This is for you to attach ([enroll](#enroll)) and unattach ([unenroll](#unenroll)) students from
specific courses and tutorials for better management.

<a name="enroll">
<panel header="#### Enrolling a student : `enroll`" expanded no-close no-switch>

Enrolls a student in a particular course and tutorial group.

<box type="definition" seamless><md>
Format: **`enroll m/MATRICULATION_NUMBER c/COURSE_CODE tutut/TUTORIAL_ID`**
</md></box>

- `MATRICULATION_NUMBER` must be the matriculation number of an existing person.
- `COURSE_CODE` must be the course code of an existing course.
- `TUTORIAL_ID` is the tutorial that the tutor wishes to enroll the person in.
- Students **already enrolled** in this course and tutorial **cannot** be enrolled again in the same course-tutorial combination.

| **Examples** |
| :--- |
| `enroll m/A1234567Y c/CS1101S tut/T12` |
| `enroll m/A1262929T c/CS2030 tut/T08` |

</panel>
</a>

<a name="unenroll">
<panel header="#### Unenrolling a student : `unenroll`" expanded no-close no-switch>

Unenrolls a student from a particular course and tutorial group that he/she is in.

<box type="definition" seamless><md>
Format: **`unenroll m/MATRICULATION_NUMBER c/COURSE_CODE tut/TUTORIAL_ID`**
</md></box>

- `MATRICULATION_NUMBER` must be the matriculation number of an existing person.
- `COURSE_CODE` must be the course code of an existing course.
- `TUTORIAL_ID` is the tutorial that the tutor wishes to enroll the person in.
- The combination of `MATRICULATION_NUMBER`, `COURSE_CODE` and `TUTORIAL_ID` must represent an existing enrollment.

| **Examples** |
| :--- |
| `unenroll m/A1234567Y c/CS1101S tut/T12` |
| `unenroll m/A1262929T c/CS2030 tut/T08` |

</panel>
</a>

<br>

:fa-solid-arrow-up: Back to [Table of Contents](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

### Attendance Commands

This is for you to conveniently keep track of each one of your students' **attendance records**.

Each of your students will have an attendance record associated with each unique course and tutorial group.
This record is to be edited over the duration of a semester (or longer), where you can
mark ([present :)](#attend-present) or [absent :(](#attend-absent)) and [unmark](#attend-remove)
attendance for for **each** consecutive session.

To view the attendance record of a student,

<div style="text-align:center">

  Click on the `Attendance` button:

  <img src="images/attendance1.png" alt="Attendance Button" width="30%">

  Then the following Attendance window will be shown:

  <img src="images/attendance2.png" alt="Attendance Window" width="30%">

</div>

<br>

<box type="tip" seamless>

Note: the course name shown in the message window will be `COURSE QUERY`, rather than 
the *actual course name* of the course, even for **valid** course codes. This is *intended 
behavior* and is simply a result of how student-course-tutorial matching is done :)
</box>

<box type="warning">

Reminder: where used, course **code** and **name**, and tutorial **code** must exist
and follow the [:fa-solid-link: data format](#data-formats)!

The particular student **must** also be enrolled in that course and corresponding tutorial.
</box>

<a name="attend-present">
<panel header="#### Marking attendance : `attend-present`" expanded no-close no-switch>

Marks a student in a particular course and tutorial group as having attended a session (**present**).

<box type="definition" seamless><md>
Format: **`attend-present m/MATRICULATION_NUMBER c/COURSE_CODE tut/TUTORIAL_ID`**
</md></box>

| **Examples** |
| :--- |
| `attend-present m/A1234567Y c/CS1101S tut/T10` |

</panel>
</a>

<a name="attend-absent">
<panel header="#### Marking absence : `attend-absent`" expanded no-close no-switch>

Marks a student in a particular course and tutorial group as having missed a session (was **absent**).

<box type="info" seamless>

*Tsk tsk tsk, remember to check with the student why they were absent, and
whether they have an official valid reason or MC~*
</box>

<box type="definition" seamless><md>
Format: **`attend-absent m/MATRICULATION_NUMBER c/COURSE_CODE tut/TUTORIAL_ID`**
</md></box>

| **Examples** |
| :--- |
| `attend-absent m/A1234567Y c/CS1101S tut/T10` |

</panel>
</a>

<a name="attend-remove">
<panel header="#### Marking attendance : `attend-remove`" expanded no-close no-switch>

Removes the last attendance session record of a student in a particular course and tutorial group.

<box type="info" seamless>

For when you accidentally marked a truant king as `present`. Oops!
</box>

<box type="definition" seamless><md>
Format: **`attend-remove m/MATRICULATION_NUMBER c/COURSE_CODE tut/TUTORIAL_ID`**
</md></box>

| **Examples** |
| :--- |
| `attend-remove m/A1234567Y c/CS1101S tut/T10` |

</panel>
</a>

<div style="page-break-after: always;"></div>

<a name="attend-clear">
<panel header="#### Marking attendance : `attend-clear`" expanded no-close no-switch>

Clears the attendance of a student in a particular course and tutorial group.

<box type="definition" seamless><md>
Format: **`attend-clear m/MATRICULATION_NUMBER c/COURSE_CODE tut/TUTORIAL_ID`**
</md></box>

| **Examples** |
| :--- |
| `attend-clear m/A1234567Y c/CS1101S tut/T10` |

<box type="important" theme="danger"><md>
**Destructive** irreversible command. Be careful!
</md></box>

</panel>
</a>

<br>

:fa-solid-arrow-up: Back to [Table of Contents](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

### Data

#### Saving the data

Ever experience the stomach-churning sinking feeling of being petrified when
you *forgot* to `Ctrl/Cmd-S` something important?

Don't worry!

*TAHub* data is saved in your drive **automatically** after any command that **changes
the data**. You don’t need to save manually!

<box type="info" seamless>

*This already makes us unambiguously superior to Microsoft Office (when saving locally).*
</box>

#### Editing the data file

Data is saved automatically in the [JSON](https://www.json.org/json-en.html)
files `[JAR file location]/data/addressbook.json`, storing the list of your students
and `[JAR file location]/data/courselist.json`, storing the list of courses.

Advanced users are *welcome* to update data directly by editing that data file.
The file structure *should* be decently self-explanatory.

<box type="warning" seamless>

**Caution: :skull:**

If your changes to the data file makes its format invalid, TAHub Contacts will
discard all data and start with an empty data file at the next run. Hence, it is
recommended to take a backup of the file before editing it.

Furthermore, certain edits can cause TAHub Contacts to behave in unexpected ways
(e.g., if a value entered is outside the acceptable range). Therefore, edit the
data file only if you are confident that you can update it correctly.

You have been duly warned.
</box>

### Coming Soon

<br>

#### Added Support for more Tutorial IDs

- Currently, we only support tutorial IDs of a specific format, as mentioned [here](#data-formats). However, we are aware that there are valid but rarer tutorial IDs with an additional character at the back like `T01A`. Due to time limitations, we are not able to support **all** potential tutorial IDs and this could be fixed in a future iteration.
<br>

#### Quick Refresh of Edited Course Name

- Currently, on editing the name of a particular course using the [course-edit](#course-edit) command, the user(TA) 
will have to wait for sometime before it is visible in the UI or will have to reopen the Attendance Window to 
refresh the screen to view the new Course Name. 
However, we are aware that this will hinder the efficiency of a fast working TA and so 
this will be fixed in a future iteration.
<br>

:fa-solid-arrow-up: Back to [Table of Contents](#table-of-contents)

</a>

--------------------------------------------------------------------------------------------------------------------

<a name="FAQ">

## FAQ :grey_question:

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file
it creates with the file that contains the data of your previous TAHub Contacts home
folder.

**Q**: I accidentally performed the wrong command. Is there a way for me to...
revert the change?<br>
**A**: Unfortunately, we have **not** implemented an undo command for TAHub contacts.
:pensive:
<br>
So be careful especially when you're using destructive (deletion) commands!

</a>

--------------------------------------------------------------------------------------------------------------------

<a name="KNOWN-ISSUES">

## Known issues :bug:

1. **When using multiple screens**, if you move the application to a secondary
   screen, and later switch to using only the primary screen, the GUI will open
   off-screen. The remedy is to delete the `preferences.json` file created by
   the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use
   the `Help` menu, or the keyboard shortcut `F1`) again, the original Help
   Window will remain minimized, and no new Help Window will appear. The
   remedy is to manually restore the minimized Help Window.

</a>

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

<a name="CMD-SUMMARY">

## Command summary :ledger:

<!-- markdownlint-disable MD013 -->

| Action                    | Format, Examples                                                                                                                                                                                                       |
|:--------------------------|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Help                      | `help`                                                                                                                                                                                                                 |
| List Students             | `list`                                                                                                                                                                                                                 |
| Clear                     | `clear`                                                                                                                                                                                                                |
| Exit                      | `exit`                                                                                                                                                                                                                 |
| Add Student               | `person-add m/MATRICULATION_NUMBER n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS`<br>e.g.`person-add m/A0177310M n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665`      |
| Find Students by Name     | `person-find KEYWORD [MORE_KEYWORDS]`<br>e.g.`person-find James Jake`                                                                                                                                                  |
| Edit Student              | `person-edit m/MATRICULATION_NUMBER [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS]`<br>e.g.`person-edit m/A0296210X n/James Lee e/jameslee@example.com`                                                             |
| Delete Student            | `person-delete m/MATRICULATION_NUMBER`<br>e.g.`person-delete m/A0296210X`                                                                                                                                              |
| Add Course                | `course-add c/COURSE_CODE n/COURSE_NAME`<br>e.g.`add c/CS1101S n/Programming Methodology 1`                                                                                                                            |
| Edit Course               | `course-edit c/COURSE_CODE n/NAME`<br>e.g.`course-edit c/CS1101S n/Programming Basics`                                                                                                                                 |
| Delete Course             | `course-delete c/COURSE_CODE`<br>e.g.`course-delete c/CS3230`                                                                                                                                                          |
| Enroll Student            | `enroll m/MATRICULATION_NUMBER c/COURSE_CODE tut/TUTORIAL_ID`<br>e.g.`enroll m/A1234567Y c/CS1101S  tut/T10`                                                                                                           |
| Unenroll Student          | `unenroll m/MATRICULATION_NUMBER c/COURSE_CODE tut/TUTORIAL_ID`<br>e.g.`unenroll m/A1234567Y c/CS1101S  tut/T10`                                                                                                       |
| Mark Present              | `attend-present m/MATRICULATION_NUMBER c/COURSE_CODE tut/TUTORIAL_ID`<br>e.g.`attend-present m/A1234567Y c/CS1101S  tut/T10`                                                                                           |
| Mark Absence              | `attend-absent m/MATRICULATION_NUMBER c/COURSE_CODE tut/TUTORIAL_ID`<br>e.g.`attend-absent m/A1234567Y c/CS1101S  tut/T10`                                                                                             |
| Remove Attendance Session | `attend-remove m/MATRICULATION_NUMBER c/COURSE_CODE tut/TUTORIAL_ID`<br>e.g.`attend-remove m/A1234567Y c/CS1101S  tut/T10`                                                                                             |
| Clear Attendance          | `attend-clear m/MATRICULATION_NUMBER c/COURSE_CODE tut/TUTORIAL_ID`<br>e.g.`attend-clear m/A1234567Y c/CS1101S  tut/T10`                                                                                               |

<!-- markdownlint-enable MD013 -->

<br>

:fa-solid-arrow-up: Back to [Table of Contents](#table-of-contents)

</a>
