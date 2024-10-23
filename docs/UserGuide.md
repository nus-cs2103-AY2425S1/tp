---
layout: page
title: User Guide
---

ConTActs is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). 
If you can type fast, ConTActs can get your contact management tasks done faster than traditional GUI apps.

  - [Command Summary](#command-summary)
  - [Installation Guide](#installation-guide)
  - [Features](#features)
  - [FAQ](#faq)
  - [Known Issues](#known-issues)

## Command summary
Click on each command to jump to their subsection.

| Action                                            | Format, Examples                                                                                                                                 |
|---------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------|
| **[Help](#viewing-help--help)**                   | `help`                                                                                                                                           |
| **[Add](#adding-a-person--add)**                  | `add n/NAME i/STUDENT_ID p/PHONE e/EMAIL [t/TAG]…​` <br> e.g., `add n/James Ho i/E0000001 p/22224444 e/jamesho@example.com t/friend t/colleague` |
| **[List](#listing-all-persons--list)**            | `list`                                                                                                                                           |
| **[Edit](#editing-a-person--edit)**               | `edit INDEX [n/NAME] [i/STUDENT_ID] [p/PHONE] [e/EMAIL] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                          |
| **[Mark](#marking-person-as-attended--mark)**     | `mark INDEX tut/TUTORIAL`<br> e.g., `mark 2 tut/1`                                                                                               |
| **[Unmark](#marking-person-as-absent--unmark)**   | `unmark INDEX tut/TUTORIAL`<br> e.g., `unmark 2 tut/1`                                                                                           |
| **[Reset](#resetting-persons-attendance--reset)** | `reset INDEX tut/TUTORIAL`<br> e.g., `reset 2 tut/1`                                                                                             |
| **[Find](#locating-persons-by-name--find)**       | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`                                                                                       |
| **[Sort](#sorting-persons--sort)**                | `sort ORDER [n/][i/][tut/]`<br> e.g., `sort -1 i/`                                                                                               |
| **[Delete](#deleting-a-person--delete)**          | `delete INDEX`<br> e.g., `delete 3`                                                                                                              |
| **[Clear](#clearing-all-entries--clear)**         | `clear`                                                                                                                                          |
| **[Exit](#exiting-the-program--exit)**            | `exit`                                                                                                                                           |

---

## Installation Guide

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   - `list` : Lists all contacts.

   - `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   - `delete 3` : Deletes the 3rd contact shown in the current list.

   - `clear` : Deletes all contacts.

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
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

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

### Adding a person : `add`

Adds a person to the address book.

Format: `add n/NAME i/STUDENT_ID p/PHONE e/EMAIL [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:

- `add n/John Doe i/E0000000 p/98765432 e/johnd@example.com`
- `add n/Betsy Crowe t/friend i/E1234567 e/betsycrowe@u.nus.edu p/1234567 t/CS1101S`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [i/STUDENT_ID] [p/PHONE] [e/EMAIL] [t/TAG]…​`

- Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
- At least one of the optional fields must be provided.
- Existing values will be updated to the input values.
- When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
- You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.

Examples:

- `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
- `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Marking person as attended : `mark`

Marks attendance as present of the person by the index number.

Format: `mark INDEX tut/TUTORIAL`

- Marks the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​ or a **wildcard** *.
- `TUTORIAL` can be in the format of:
  - A positive number between 1-12 `eg. 1`.
  - A list of numbers `eg. [1,3,5]`.
  - A range of two numbers `eg. 3-6`. 

Examples:

- `mark 1 tut/1` Marks the 1st person in the address book as attended for tutorial 1.
- `mark * tut/1` Marks all persons in the address book as attended for tutorial 1.
- `mark * tut/1-3` Marks all persons in the address book as attended for tutorials 1 to 3.
- `mark 1 tut/[2,4,12]` Marks the 1st person in the address book as attended for tutorials 2, 4 and 12.

### Marking person as absent : `unmark`

Marks attendance as absent of the person by the index number.

Format: `unmark INDEX tut/TUTORIAL`

- Marks the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​ or a **wildcard** *.
- `TUTORIAL` can be in the format of:
    - A positive number between 1-12 `eg. 1`.
    - A list of numbers `eg. [1,3,5]`.
    - A range of two numbers `eg. 3-6`.

Examples:

- `unmark 1 tut/1` Marks the 1st person in the address book as absent for tutorial 1.
- `unmark * tut/1` Marks all persons in the address book as absent for tutorial 1.
- `unmark * tut/1-3` Marks all persons in the address book as absent for tutorials 1 to 3.
- `unmark 1 tut/[2,4,12]` Marks the 1st person in the address book as absent for tutorials 2, 4 and 12.

### Resetting person's attendance : `reset`

Resets the attendance of the person by the index number.

Format: `reset INDEX tut/TUTORIAL`

- Resets the attendance of the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​ or a **wildcard** *.
- `TUTORIAL` can be in the format of:
    - A positive number between 1-12 `eg. 1`.
    - A list of numbers `eg. [1,3,5]`.
    - A range of two numbers `eg. 3-6`.

Examples:

- `reset 1 tut/1` Resets the attendance of the 1st person in the address book for tutorial 1.
- `reset * tut/1` Resets the attendance of all persons in the address book for tutorial 1.
- `reset * tut/1-3` Resets the attendance of all persons in the address book for tutorials 1 to 3.
- `reset 1 tut/[2,4,12]` Resets the attendance of the 1st person in the address book for tutorials 2, 4 and 12.

### Locating persons by name : `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

- The search is case-insensitive. e.g `hans` will match `Hans`
- The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
- Only the name is searched.
- Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:

- `find John` returns `john` and `John Doe`
- `find an` returns `armin` and `brian`
- `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Sorting persons : `sort`

Sorts the displayed list of persons by either name, student id or tutorial attendance.

Format: `sort ORDER [n/][i/][tut/]`

- `ORDER` indicates whether the sorted list is **ascending** or **descending**.
- **Ascending** is represented with integer 1.
- **Descending** is represented with integer -1.
- `n/` indicates sorting according to name.
- `i/` indicates sorting according to student id.
- `tut/` indicates sorting according to tutorial attendance.

Examples:

- `sort -1 i/` sorts the person list in descending order according to student id.
- `sort 1 n/` sorts the person list in ascending order according to name.

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

- Deletes the person at the specified `INDEX`.
- The index refers to the index number shown in the displayed person list.
- The index **must be a positive integer** 1, 2, 3, …​

Examples:

- `list` followed by `delete 2` deletes the 2nd person in the address book.
- `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

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
