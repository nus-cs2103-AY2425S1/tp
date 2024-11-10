---
layout: page
title: User Guide
---

Cher is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, Cher can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}
--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-W13-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for Cher.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar cher.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe r/student p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to Cher.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

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
</div>

### Viewing help : `help`

Shows the comprehensive User Guide page and displays a floating window allowing the user to copy the URL of the user guide page.

![help message](images/helpWindow.png)

Format: `help`


### Adding a person: `add`

Adds a person to Cher.

Format: `add n/NAME s/SEX r/ROLE p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

* Name can only contain alphabets and spaces. 

* Role can either be ```student``` or ```parent```, case-insensitive.

* Sex can either be ```f``` or ```m```, case-insensitive.

* Phone numbers can contain only numbers and should be exactly 8 digits long. 

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe s/m r/student p/987654321 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe s/f r/parent e/betsycrowe@example.com a/Newgate Street p/12345678`

### Listing all persons : `list`

Shows a list of all persons in Cher.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the Cher.

Format: `edit INDEX [n/NAME] [s/SEX] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.
* Note that role cannot be edited. 

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by attributes : `find`

Finds persons whose names contain any of the given keywords.

Format: `find [INDEX] [n/KEYWORDS] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

### General Search Rules
- **Case-insensitive**: Searches ignore case differences. For example, `hans` will match `Hans`.
- **Attribute-Specific Requirements**: Each attribute has specific search behaviors (see details below).

### Attribute-Specific Search Details

### Index (`INDEX`)
- Finds the person at the specified `INDEX` in the currently displayed list.
- **Index** must be a positive integer (e.g., `1`, `2`, `3`, …).

### Examples
- `list` followed by `find 2` shows the 2nd person in the displayed list.

### Name (`n/KEYWORDS`)
- **Case-insensitive** and **order-independent**: The order of keywords does not matter. For example, `find Hans Bo` will match both `Hans Gruber` and `Bo Hans`.
- **Partial Word Matching from the Start of Each Name Part**: You can search using a part of any name (first or last) as long as it starts with the given keywords. For example, `find jo ap` would return both `John Appleseed` and `Appleseed Johnny`.

### Examples
- `find n/John` → returns `John Doe`, `Johnny Appleseed`
- `find n/alex david` → returns `Alex Yeoh`, `David Alex`
- `find n/jo ap` → returns `John Appleseed`, `Appleseed Johnny`

#### Phone Number (`p/PHONE`)
- **Full Phone Number Required**: Partial matches will not be returned. You need to provide the complete phone number.

### Examples
- `find p/12345678` → returns only persons with the exact phone number `12345678`

#### Email (`e/EMAIL`)
- **Exact Match Required**: Only persons with the exact email provided will be matched.

### Examples
- `find e/johndoe@example.com` → returns only persons with the email `johndoe@example.com`

#### Address (`a/ADDRESS`)
- **Full Address Required**: The search requires the complete address; partial address matches are not supported.

### Examples
- `find a/123 Clementi Ave 3` → returns only persons with the exact address `123 Clementi Ave 3`

#### Tags (`t/TAG`)
- **Full Tag Name Required**: Only persons with tags that match the exact tag names provided will be returned.
- **Multiple Tags for Filtering**: You can specify multiple tags for a broader search.

### Examples
- `find t/friend t/colleague` → returns persons tagged as `friend` and `colleague`

### Examples

- `find n/John` → returns `John`, `John Doe`
- `find n/alex david` → returns `Alex Yeoh`, `David Li`
- `find p/12345678` → returns persons with phone number `12345678`
- `find a/123 Clementi Ave 3` → returns persons with address `123 Clementi Ave 3`
- `find t/friend t/colleague` → returns persons with tags `friend` or `colleague`

### Sorting persons by name: `sort`

Sorts list of persons alphabetically by specified predicate.

Format: `sort PREDICATE`

* Sorts the list alphabetically by given predicate.
* The predicate refers to attributes used to sort the list by.
* The predicate **must be a valid attribute** name, role, phone, email and address only

Examples:
* `sort name` sorts the list alphabetically by name.
  ![result for 'sort name'](images/sortMessage.png)
* `sort role` sorts the list alphabetically by role.
* `sort phone` sorts the list alphabetically by phone.
* `sort email` sorts the list alphabetically by email.
* `sort address` sorts the list alphabetically by address.

### Deleting a person : `delete`

Deletes the specified person from Cher.

Format: `delete [INDEX] [n/KEYWORDS] [p/PHONE] [a/ADDRESS] [t/TAG]…​`

### General Rules
- **Case-insensitive**: The search for names and tags is not case-sensitive, so `john` will match `John`.
- **Behavior with Duplicates**: For attributes where duplicates may exist (such as names, addresses, and tags), if multiple matches are found, a list of possible matches will be displayed, allowing the user to choose.
- **Direct Deletion**: For unique attributes (like index and phone number), Cher will directly delete the matching person.

### Attribute-Specific Deletion Rules

### Index (`INDEX`)
- Deletes the person at the specified `INDEX` in the currently displayed list.
- **Index** must be a positive integer (e.g., `1`, `2`, `3`, …).
- **Unique Match**: The index is unique in the displayed list, so Cher directly deletes the person at that position.

### Examples
- `list` followed by `delete 2` deletes the 2nd person in the displayed list.
- `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Partial Name (`n/KEYWORDS`)
- Deletes the person with a name that **matches partially** with the specified `PARTIAL_NAME`.
- **Case-insensitive** and **order-independent**: The order of keywords does not matter, and case is ignored (e.g., `delete John` matches both `john doe` and `Doe John`).
- **Partial Word Matching from the Start of Each Name Part**: You can search using a part of any name (first or last) as long as it starts with the given keywords. For example, `find jo ap` would return both `John Appleseed` and `Appleseed Johnny`.
- **Multiple Matches**: If multiple persons match the partial name, Cher will display a list of matches for the user to choose from.

### Examples
- `list` followed by `delete John` deletes the person with the name `John`.
- If multiple persons have the name `John`, a list of these persons is displayed for selection.

### Phone Number (`p/PHONE`)
- Deletes the person with the specified **full** `PHONE_NUMBER`.
- **Exact Match Required**: Partial phone numbers will not be matched.
- **Unique Match**: Since phone numbers are unique, Cher will directly delete the person with that phone number.

### Examples
- `list` followed by `delete 98765432` deletes the person with the phone number `98765432` in the list.

### Address (`a/ADDRESS`)
- Deletes the person with the specified **full** `ADDRESS`.
- **Exact Match Required**: The address must be complete; partial matches are not supported.
- **Multiple Matches**: If multiple persons share the same address, Cher will display a list of matching persons for the user to choose from.

### Examples
- `delete a/123 Clementi Ave 3` deletes the person with the exact address `123 Clementi Ave 3`.

### Tags (`delete t/TAG…`)
- Deletes persons based on specified tags. Multiple tags can be used for broader filtering.
- **Full Tag Name Required**: Only persons with tags that match the exact names provided will be considered.
- **Multiple Matches**: If multiple persons match the provided tags, Cher will display a list of matches for selection.

### Examples
- `delete t/friend t/colleague` deletes persons tagged as both `friend` and `colleague` if they are unique; otherwise, Cher will show a list of matching persons.

### Deleting in a batch : `batch-delete`

Delete all contacts from Cher that contain **all** the specified tags.

Format: `batch-delete t/TAG [t/TAG]...`

Examples:

![Batch delete example data](images/ForBatchDeleteExampleData.png)
* `batch-delete t/friends` will delete both `Alex Yeoh` and `Bernice Yu`.
* `batch-delete t/friends t/colleagues` will delete only `Bernice Yu`.

### Mark attendance for a single student: `mark`

Marks the attendance of a specified student.

Format: `mark INDEX`

* Marks the attendance for the student at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Example:
* `list` followed by `mark 2` marks the attendance of the the 2nd person in the Cher.

### Unmark attendance for a single student: `unmark`

Unmarks the attendance of a specified student.

Format: `unmark INDEX`

* Unmarks the attendance for the student at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Example:
* `list` followed by `unmark 3` unmarks the attendance of the 3rd person in Cher.

### Reset attendance: `reset-att`

Resets the attendance count of all students in displayed list to 0.

Format: `reset-att`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
To reset the attendance count of all students to 0, enter `list` to get a list of all contacts, then enter `reset-att`!
</div>

### Mark attendance for a group of students: `batch-mark`

Marks attendance for all students in the displayed list.

Format: `batch-mark`

Example:
* Enter `select 1 2 3` and then `batch-mark` marks the attendance of entries 1, 2 and 3


### Unmark attendance for a group of students: `batch-unmark`

Unmarks attendance for all students in the displayed list.

Format: `batch-unmark`

Example:
* Enter `select 1 2 3` and then `batch-unmark` unmarks the attendance of entries 1, 2 and 3

### Selecting persons by index: `select`
The `select` command allows users to select one or more persons from the displayed list of persons in the address book. Once a person is selected, the application highlights the selected person(s) in the UI and displays their names in the feedback box.

Format: select INDEX [MORE_INDEXES]...

Examples:

* `select 1 2` will select the contacts at index `1` and `2` in the displayed list, showing only those contacts. The person at index 2 and 4 will be selected. The feedback box will display:
  "Selected Person(s): [Name of person at index 2 and 4]" (e.g., "Selected Person(s): John Doe, Alice Tan").
* `select 3 5 7` will select the contacts at indexes `3`, `5`, and `7` in the displayed list, filtering to show
  only these selected contacts. The person at index 3, 5 and 7 will be selected. The feedback box will display:
  "Selected Person(s): [Name of person at index 3, 5 and 7]" (e.g., "Selected Person(s): John Doe, Alice Tan, Joshua Chou").

Where INDEX refers to the position of the person in the currently displayed list of persons.
- INDEX is a positive integer that refers to the position of a person in the list (starting from 1).
- You can specify multiple indexes separated by spaces to select more than one person at a time.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:** To increase efficiency when performing actions on multiple persons,
consider combining the `select` command with other commands like `delete`, `mark`, or `batch-mark` for group operations.
For example: - `select 1 2 3` followed by `delete` will delete persons at indexes 1, 2, and 3.
- `select 4 5 6` followed by `batch-mark` will mark attendance for all selected persons. </div>

### Editing tag in a batch: `batch-edit`
Changes all contacts from cher with containing the specified tags with a new tag. After successful execution,
Cher will show all the contacts containing the new tag.

Format: `batch-edit t/OLDTAG t/NEWTAG`

Examples:
![Batch delete example data](images/ForBatchDeleteExampleData.png)
* `batch-edit t/friends t/fren` will change the `friends` tag of both `Alex Yeoh` and `Bernice Yu` to `fren`.
* After successful execution, the contact list will show `Alex Yeoh`, `Bernice Yu` and `Charlotte Oliveiro`
  as they all have the `fren` tag.


### Clearing all entries : `clear`

Clears all entries from Cher.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

Data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

Data are saved automatically as a JSON file `[JAR file location]/data/cher.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, Cher will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the Cher to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Cher home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME r/ROLE p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho r/student p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/Secondary 1`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Batch-Delete**| `batch-delete t/TAG [t/TAG]...`<br> e.g. `batch-delete t/friends t/colleagues t/owesmoney t/...`
**Batch-Edit**| `batch-edit t/OLDTAG t/NEWTAG`<br> e.g. `batch-delete t/friends t/frens`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]...`<br> e.g., `find James Jake`
**Select** | `select INDEX [MORE_INDEXES]...`<br> e.g., `select 1 2`
**Mark** | `mark INDEX` <br> e.g., `mark 2`
**Unmark** | `unmark INDEX` <br> e.g., `unmark 3`
**Batch Mark** | `batch-mark`
**Batch Unmark** | `batch-unmark`
**Reset Attendance** | `reset-att`
**List** | `list`
**Help** | `help`
