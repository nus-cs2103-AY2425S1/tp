---
layout: page
title: User Guide
---

**ConTActs** is a **desktop app for teaching assistants (TAs) to manage student contact information, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI).
If you can type fast, ConTActs can get your contact management tasks done faster than traditional GUI apps.

  - [Installation Guide](#installation-guide)
  - [Features](#features)
  - [Command Summary](#command-summary)
  - [FAQ](#faq)
  - [Known Issues](#known-issues)

---

## Installation Guide

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-T10-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your **ConTActs**.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar ConTActs.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   - `list` : Lists all students in the contact list.

   - `add n/John Doe i/E0000000 p/98765432 e/johnd@example.com` : Adds a student named `John Doe` to the contact list.

   - `delete 3` : Deletes the 3rd student shown in the current list.

   - `clear` : Deletes all students in the contact list.

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
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/CS1101S` etc.

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

### Adding a student : `add`

Adds a student to the contact list.

Format: `add n/NAME i/STUDENT_ID p/PHONE e/EMAIL [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A student can have any number of tags (including 0)
</div>

Examples:

- `add n/John Doe i/E0000000 p/98765432 e/johnd@example.com`
- `add n/Betsy Crowe t/friend i/E1234567 e/betsycrowe@u.nus.edu p/1234567 t/CS1101S`

### Listing all students : `list`

Shows a list of all students in the contact list.

Format: `list`

### Editing a student : `edit`

Edits an existing student in the contact list.

Format: `edit INDEX [n/NAME] [i/STUDENT_ID] [p/PHONE] [e/EMAIL] [t/TAG]…​`

- Edits the student at the specified `INDEX`. The index refers to the index number shown in the displayed contact list. The index **must be a positive integer** (1, 2, 3, …​).
- At least one of the optional fields must be provided.
- Existing values will be updated to the input values.
- When editing tags, the existing tags of the student will be removed i.e adding of tags is not cumulative.
- You can remove all the student’s tags by typing `t/` without
  specifying any tags after it.

Examples:

- `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st student to be `91234567` and `johndoe@example.com` respectively.
- `edit 2 n/Betsy Crower t/` Edits the name of the 2nd student to be `Betsy Crower` and clears all existing tags.

### Locating students by name or tag : `find`

Finds students whose names or tags contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS] [t/TAG]` or `find [KEYWORDS] t/TAG`

- At least one field of `KEYWORD` or `TAG` is required for the search.
- The search is case-insensitive. e.g `hans` will match `Hans`
- The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
- The name as well as the tags will be searched.
- students matching at least one keyword or tag will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
- Searching for both name and tags will return all results that have either the name or the respective tag, which is basically the union of both groups.

Examples:

- `find John` returns `john` and `John Doe`
- `find an` returns `armin` (characters a and n are present in **a**rmi**n**) and `brian`
- `find alex david` returns `Alex Yeoh`, `David Li`<br> 
  ![result for 'find alex david'](images/findAlexDavidResult.png)
- `find t/ri` returns all contacts marked with tag `friends` and `isRich` etc. <br>
- `find alex t/colleagues` returns `Alex Yeoh` and all contacts marked with tag `colleagues`<br>
  ![result for 'find alex t/colleagues'](images/findAlexColleaguesResult.png)

<div markdown="block" class="alert alert-info">

**:information_source: Updated index:**<br>
After a `find` command, the **next** command's `INDEX` will refer to the index in the displayed list.
</div>

### Marking student as present : `mark`

Marks specified tutorial attendance as present of the student by the index number.

Format: `mark INDEX tut/TUTORIAL`

- Marks the student at the specified `INDEX`. The index refers to the index number shown in the displayed contact list. The index **must be a positive integer** (1, 2, 3, …​).
- `TUTORIAL` can be in the format of:
  - A positive number between 1 - 12 (inclusive) e.g. `1`.
  - A list of numbers e.g. `[1,3,5]`.
  - A range of two numbers e.g. `3-6`. 

Examples:

- `mark 1 tut/1` Marks the 1st student in the contact list as attended for tutorial 1.
- `mark 1 tut/1-3` Marks the 1st student in the contact list as attended for tutorials 1 to 3.
- `mark 1 tut/[2,4,12]` Marks the 1st student in the contact list as attended for tutorials 2, 4 and 12.

Visual Effect:

- After the command `mark 1 tut/1`, tutorial box 1 of the 1st student will turn <span style="color:green">green</span>.

  |Before|After|
  |---|---|
  |![Before](images/MarkBefore.png)|![After](images/MarkAfter.png)|

### Marking student as absent : `unmark`

Marks specified tutorial attendance as absent of the student by the index number.

Format: `unmark INDEX tut/TUTORIAL`

- Marks the student at the specified `INDEX`. The index refers to the index number shown in the displayed contact list. The index **must be a positive integer** (1, 2, 3, ...).
- `TUTORIAL` can be in the format of:
    - A positive number between 1 - 12 (inclusive) e.g. `1`.
    - A list of numbers e.g. `[1,3,5]`.
    - A range of two numbers e.g. `3-6`.

Examples:

- `unmark 1 tut/1` Marks the 1st student in the contact list as absent for tutorial 1.
- `unmark 1 tut/1-3` Marks the 1st student in the contact list as absent for tutorials 1 to 3.
- `unmark 1 tut/[2,4,12]` Marks the 1st student in the contact list as absent for tutorials 2, 4 and 12.

Visual Effect:

The specified tutorial box of the specified student will turn <span style="color:red">red</span>.

|:exclamation: *To reduce visual clutter, an image will not be provided*.|
---

### Resetting student's attendance : `reset`

Resets specified tutorial attendance of the student by the index number.

Format: `reset INDEX tut/TUTORIAL`

- Resets the attendance of the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** (1, 2, 3, ...).
- `TUTORIAL` can be in the format of:
    - A positive number between 1 - 12 (inclusive) e.g. `1`.
    - A list of numbers e.g. `[1,3,5]`.
    - A range of two numbers e.g. `3-6`.

Examples:

- `reset 1 tut/1` Resets the attendance of the 1st student in the contact list for tutorial 1.
- `reset 1 tut/1-3` Resets the attendance of the 1st student in the contact list for tutorials 1 to 3.
- `reset 1 tut/[2,4,12]` Resets the attendance of the 1st student in the contact list for tutorials 2, 4 and 12.

Visual Effect:

The specified tutorial box of the specified student will turn <span style="color:grey">grey</span>.

|:exclamation: *To reduce visual clutter, an image will not be provided*.|
---

<div markdown="block" class="alert alert-info">

**:information_source: Faster attendance updating:**<br>
You can use the `mark`, `unmark` and `reset` commands with the wildcard `*` to update the attendance of all currently displayed students in the contact list at once.
- `mark * tut/1` marks all students as attended for tutorial 1.
- `unmark * tut/1` marks all students as absent for tutorial 1.
- `reset * tut/1` resets the attendance of all students for tutorial 1.
</div>

### Sorting students : `sort`

Sorts the displayed list of students by either name, student id or tutorial attendance. The sorting order will be maintained while edits to the list are made.

Format: `sort ORDER [n/] [i/] [tut/TUTORIAL]`

- `ORDER` indicates whether the sorted list is **ascending** or **descending**.
- **Ascending** is represented with integer 1.
- **Descending** is represented with integer -1.
- `n/` indicates sorting according to name.
- `i/` indicates sorting according to student id.
- `tut/TUTORIAL` indicates sorting according to tutorial attendance for a specific tutorial.
- At least one sorting criterion `[n/]`, `[i/]`, `[tut/TUTORIAL]` must be present. `sort ORDER` is an invalid command.

Examples:

- `sort -1 i/` sorts the student list in descending order according to student id.
- `sort 1 tut/3` sorts the student list in ascending order according to tutorial 3 attendance.

<div markdown="block" class="alert alert-info">

**:information_source: Updated index:**<br>
After a `sort` command, the **next** command's `INDEX` will refer to the index in the displayed list.
</div>

### Deleting a student : `delete`

Deletes the specified student from the contact list.

Format: `delete INDEX`

- Deletes the student at the specified `INDEX`.
- The index refers to the index number shown in the current displayed contact list.
- The index **must be a positive integer** e.g. 1, 2, 3, …​

Examples:

- `list` followed by `delete 2` deletes the 2nd student in the current displayed contact list.
- `find Betsy` followed by `delete 1` deletes the 1st student in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the contact list.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

ConTActs data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

ConTActs data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, ConTActs will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the ConTActs to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

---

## Command summary
Click on each command to jump to their subsection.

| Action                                            | Format, Examples                                                                                                                                                            |
|---------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **[Help](#viewing-help--help)**                   | `help`                                                                                                                                                                      |
| **[Add](#adding-a-student--add)**                  | `add n/NAME i/STUDENT_ID p/PHONE e/EMAIL [t/TAG]…​` <br> e.g., `add n/John Doe i/E0000000 p/98765432 e/johnd@example.com t/CS1101S`                            |
| **[List](#listing-all-students--list)**            | `list`                                                                                                                                                                      |
| **[Edit](#editing-a-student--edit)**               | `edit INDEX [n/NAME] [i/STUDENT_ID] [p/PHONE] [e/EMAIL] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                                     |
| **[Find](#locating-students-by-name-or-tag--find)**       | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`                                                                                                                  |
| **[Mark](#marking-student-as-present--mark)**     | `mark INDEX tut/TUTORIAL` <br> - `INDEX`: integer or `*` for all <br> - `TUTORIAL`: integer, list (e.g.,`tut/[1,3,7]`) or range (e.g.,`tut/1-12`) <br> e.g., `mark 2 tut/1` |
| **[Unmark](#marking-student-as-absent--unmark)**   | `unmark INDEX tut/TUTORIAL` <br> - `INDEX`: integer or `*` for all <br> - `TUTORIAL`: integer, list or range<br> e.g., `unmark 2 tut/1`                                     |
| **[Reset](#resetting-students-attendance--reset)** | `reset INDEX tut/TUTORIAL`<br> - `INDEX`: integer or `*` for all <br> - `TUTORIAL`: integer, list or range <br> e.g., `reset 2 tut/1`                                       |
| **[Sort](#sorting-students--sort)**                | `sort ORDER [n/] [i/] [tut/]`<br> e.g., `sort -1 i/`                                                                                                                          |
| **[Delete](#deleting-a-student--delete)**          | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                         |
| **[Clear](#clearing-all-entries--clear)**         | `clear`                                                                                                                                                                     |
| **[Exit](#exiting-the-program--exit)**            | `exit`                                                                                                                                                                      |

---

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

---

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

---
[Back to Top](#command-summary)
