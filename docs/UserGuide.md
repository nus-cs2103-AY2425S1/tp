---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# Tuteez User Guide

Tuteez is a **desktop address book app designed specifically for tech-savvy private tutors to manage student contacts**, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). Key features include **conflict-free scheduling**, **storing detailed contact information**, and the ability to **add personalized remarks** for each student. If you can type fast, Tuteez can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->

- [Tuteez User Guide](#tuteez-user-guide)
   * [Quick start](#quick-start)
   * [Features](#features)
      + [Viewing help : `help`](#viewing-help-help)
      + [Adding a person: `add`](#adding-a-person-add)
      + [Listing all persons : `list`](#listing-all-persons-list)
      + [Editing a person : `edit`](#editing-a-person-edit)
      + [Adding or Deleting a Remark: `remark`](#adding-or-deleting-a-remark-remark)
      + [Locating persons by name: `find`](#locating-persons-by-name-find)
      + [Deleting a person : `delete`](#deleting-a-person-delete)
      + [Displaying Student Information: `display`](#displaying-student-information-display)
      + [Clearing all entries : `clear`](#clearing-all-entries-clear)
      + [Exiting the program : `exit`](#exiting-the-program-exit)
   * [Key details for Users  ](#key-details-for-users)
      + [`Lesson` Constraints](#lesson-constraints)
      + [Saving the data](#saving-the-data)
      + [Editing the data file](#editing-the-data-file)
   * [Future Features](#future-features)
      + [Archiving data files `[coming in v2.0]`](#archiving-data-files-coming-in-v20)
   * [FAQ](#faq)
   * [Known issues](#known-issues)
   * [Command summary](#command-summary)

<!-- TOC end -->

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-F09-4/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for Tuteez.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar tuteez.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a student named `John Doe` to Tuteez.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## UI 

### Left panel

* Meant to showcase only important information such as student's phone number, address, telegram and next lesson based on your computer's current time
* If a lesson is currently ongoing it will show that lesson as the next lesson on the left panel

### Right panel

* Provides full view of students information when you call display
* This is where you can see all your student's lesson details and the remarks you have left them

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/Math` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/Secondary`, `t/Math t/Science` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a student to Tuteez.

Format: `add n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [tg/TELEGRAM_USERNAME] [t/TAG]…​ [l/LESSON]…​`

- Only the **name** and **phone number** are required fields. The other fields are optional.
- The **`l/` (lesson)** field should include the **day** of the week (case-insensitive) followed by the **time** in the **24-hour format** `HHMM-HHMM`, separated by a space.
    - Example: `l/monday 0900-1100` or `l/Wednesday 1400-1600`
    - Tutors cannot add lessons that clash, meaning lessons cannot be scheduled on the same day and overlap in timing. If a clash is detected, the app will notify the user with an error message.
    - To see more details for valid lessons, check out [lesson constraints](#lesson-constraints)

<box type="tip" seamless>

**Tip:** A student can have any number of tags and lessons (including 0)
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/Math l/ monday 0900-1100`

### Listing all persons : `list`

Shows a list of all students in Tuteez.

Format: `list`

### Editing a person : `edit`

Edits an existing student in Tuteez.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the student at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing **tags**, **all previous values will be replaced** by the new ones entered.
    - This means you must **retype all old tags** you wish to keep, as editing will overwrite them completely.
    - For example, if a student already has two lessons and you wish to add another, you need to re-enter the previous lessons along with the new one.
* Lessons cannot clash (i.e., scheduled on the same day and overlapping in timing). If a clash is detected, the app will notify the user with an error message.
* You can remove all optional fields as specified in the `add` command by typing its parameter prefix (e.g. `t/`) without specifying any values after them.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st student to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd student to be `Betsy Crower` and clears all existing tags.
*  `edit 3 l/tuesday 0900-1100` Edits the lesson of the 3rd student to be `MONDAY 0900-1100`.

### Adding or Deleting a Remark: `remark`

Allows the tutor to add or delete a remark for a specific student in Tuteez.

Format:
- `remark INDEX -a REMARK` to add a remark to the student at the specified `INDEX`.
- `remark INDEX -d REMARK_INDEX` to delete a remark at the specified `REMARK_INDEX` from the student at `INDEX`.

* Adds a new remark to the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​
* Deletes an existing remark from the student at the specified `REMARK_INDEX`. The remark index refers to the order in which the remarks were added.
* When deleting, if the `REMARK_INDEX` is not valid, an error will be shown.
* You can add any text as a remark, and remarks are displayed in the order they were added.

<box type="tip" seamless>

**Tip:** You can also use the abbreviated command `rmk` as a shortcut for `remark`.
</box>

Examples:
* `remark 1 -a Great progress in Math` Adds the remark "Great progress in Math" to the first student.
* `remark 2 -a Needs improvement in English` Adds the remark "Needs improvement in English" to the second student.
* `remark 1 -d 2` Deletes the second remark of the first student.

### Adding Lessons: `addlesson` or `addlsn`

Allows the tutor to add lesson(s) to a specific student in Tuteez.

Format: `addlesson INDEX l/LESSON [l/LESSON]…​`

* short form: `addlsn INDEX l/LESSON [l/LESSON]…​`
* Adds new lesson(s) to the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​
* You can add multiple lessons to a student at once.
* Lessons have to start with the **day** of the week (case-insensitive) followed by the **time** in the **24-hour format** `HHMM-HHMM`, separated by a space.
* Lessons cannot clash (i.e., scheduled on the same day and overlapping in timing). If a clash is detected, an error message will be shown.
* For more details on valid lessons, check out [lesson constraints](#lesson-constraints)

Examples:
* `addlesson 1 l/Monday 0900-1100` Adds a lesson on Monday from 9 am to 11 am to the first student.
* `addlesson 2 l/Tuesday 1400-1600 l/Thursday 1400-1600` Adds lessons on Tuesday and Thursday from 2 pm to 4 pm to the second student.

### Deleting Lessons: `deletelesson` or `dellsn`

Allows the tutor to delete lesson(s) from a specific student in Tuteez.

Format: `deletelesson INDEX li/LESSON_INDEX [li/LESSON_INDEX]…​`

* short form: `dellsn INDEX li/LESSON_INDEX [li/LESSON_INDEX]…​`
* Deletes lesson(s) from the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​
* Lessons are indexed based on the order they were added to the student, starting from 1.
* If the `LESSON_INDEX` is not valid, an error will be shown.

Examples:
* `deletelesson 1 li/1` Deletes the first lesson of the first student.
* `dellsn 2 li/2 li/3` Deletes the second and third lessons of the second student.

<box type="tip" seamless>

**Tip:** You can delete multiple lessons at once by specifying different lesson indices.
</box>

### Locating persons by name: `find`

Finds students whose names, addresses, tags or lessons contain any of the given keywords.

Format: `find [n/NAME_KEYWORDS] [a/ADDRESS_KEYWORDS] [t/TAG_KEYWORDS] [l/LESSON_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* For name keywords, only the name is searched. For address keywords, only the address is searched etc.
* For words, only full words will be matched e.g. `Han` will not match `Hans`
* For time-range, lessons with overlapping time-ranges will be matched e.g. `0900-1000` will overlap with `0800-0930`
* Persons with at least one parameter matching at least one of its keyword will be returned (i.e. `OR` search).
* `find n/John t/Science English` will can return students `John Doe` with tag `Math`, `Alice Richardson` with tag `Science` and `Mary Jane` with tag `English`

Examples:
* `find n/alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)
* `find a/jurong` returns students with address `Jurong Lake #09-11` and `jurong west #13-21`
* `find l/monday 1000-1100` returns students with lessons `monday 0800-0900` and `tuesday 0900-1030`

### Deleting a person : `delete`

Deletes the specified student from Tuteez.

Format: `delete INDEX` or `delete NAME`

* Deletes the student at the specified `INDEX` or by their full `NAME` (case-insensitive).
    - When using the `NAME` option, the full name of the student must be provided.
* The index refers to the index number shown in the displayed student list.
* The index **must be a positive integer** 1, 2, 3, …​

<box type="tip" seamless>

**Tip:** You can also use the abbreviated command `del` as a shortcut for `delete`.
</box>

Examples:
* `list` followed by `delete 2` deletes the 2nd student in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st student in the results of the `find` command.
* `delete John Doe` deletes the student with the full name "John Doe" from the address book, ignoring case sensitivity.

### Displaying Student Information: `display`

Displays specific information of a student in Tuteez.

Format: `display INDEX`

* Displays the details of the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​
* The displayed information includes the student's name, phone number, email, address, tags, lessons, and any remarks associated with the student.

Examples:
* `display 1` Shows the details of the first student in the list.
* `display 3` Shows the details of the third student in the list.

### Clearing all entries : `clear`

Clears all entries from Tuteez.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

--------------------------------------------------------------------------------------------------------------------

## Key details for Users  

### `Lesson` Constraints

Unfortunately, as of `V1.5` there are a few important constraints regarding lessons:  

  1. Lessons are not allowed to overflow into the next day
  1. Group tuition is currently not supported, so adding overlapping or clashing lessons is not available yet  

This means the following constraints apply:

  1. Lesson start time must be before end time  
  1. Lesson start and end time cannot be identical  
  1. The latest lesson start time is `2358`
  1. The latest lesson end time is `2359`, `0000` is treated as the start of a new day

Look forward to [future updates](#future-features) for group tuition support!!
### Saving the data

Tuteez data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

Tuteez data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, Tuteez will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause Tuteez to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

--------------------------------------------------------------------------------------------------------------------

## Future Features

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Tuteez home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​ [l/LESSON]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/Math t/monday 0900-1100`
**Clear**  | `clear`
**Delete** | `delete INDEX` or `delete NAME`<br> e.g., `delete 3` or `delete James Ho`
**Display**| `display INDEX` <br> e.g., `display 1`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [tg/TELEGRAM_USERNAME] [t/TAG]…​ [l/LESSON]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com t/Math l/sunday 1000-1100`
**Remark** | `remark INDEX -a REMARK` to add a remark to student at `INDEX`<br> e.g., `remark 1 -a Great progress in Math`<br> `remark INDEX -d REMARK_INDEX` to delete a specific remark from the student at `INDEX`<br> e.g., `remark 1 -d 2` to delete the second remark of student 1.
**Add Lesson** | `addlesson INDEX l/LESSON [l/LESSON]`<br> e.g., `addlesson 1 l/Monday 0900-1100`
**Delete Lesson** | `deletelesson INDEX li/LESSON_INDEX [li/LESSON_INDEX]`<br> e.g., `deletelesson 1 li/1`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**   | `list`
**Help**   | `help`
