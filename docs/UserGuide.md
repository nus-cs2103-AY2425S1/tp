---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---
# GOATS User Guide

## Introduction

Are you a private tutor who has trouble keeping track of your student's details? Ever wanted to have an easier way to keep track of students and their parents? <br><br>
The Greatest Offline Addressbook for Teaching Students (GOATS) is an **offline desktop app** designed for your administrative needs for private tutoring. It not only helps you keep track of students grades and education level, it can also help you keep track of their parent's contact details as well.
<br><br>
It is optimised for fast typists using Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI) allowing to scan through information quickly.
<br><br>
If you can type fast, GOATS can get your student management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->

<page-nav-print />

---

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.
2. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-T09-4/tp/releases).
3. Copy the file to the folder you want to use as the _home folder_ for your GOATS.
4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar GOATS.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)
5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.
   * `addstudent n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 edu/primary lt/wed:13:00 t/friend t/colleague` : Adds a student named James Ho in GOATS.
   * `delete 3` : Deletes the 3rd contact shown in the current list.
   * `clear` : Deletes all contacts.
   * `exit` : Exits the app.
6. Refer to the [Features](#features) below for details of each command.

---

## Command summary


| Action                         | Format (Example)                                                                                                                                                                                                               |
| ------------------------------ |--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Parent**                 | `addparent n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…` (`addparent n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`)                                                                          |
| **Add Student**                | `addstudent n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS lt/LESSON_TIME edu/EDUCATION [t/TAG]…` (`addstudent n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 edu/primary lt/wed:13:00 t/friend t/colleague`) |
| **Archive**                    | `archive INDEX [MORE_INDICES]` (`archive 1 2 3 4`)                                                                                                                                                                             |
| **Assign Grade**               | `grade INDEX g/GRADE_index` (`grade 1 g/1`)                                                                                                                                                                                    |
| **Clear**                      | `clear`                                                                                                                                                                                                                        |
| **Delete**                     | `delete INDEX [MORE_INDICES]` (`delete 1 2 3 4`)                                                                                                                                                                               |
| **Edit**                       | `INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [lt/LESSON_TIME] [edu/EDUCATION] [t/TAG]` (`edit 1 p/91234567 e/johndoe@example.com`)                                                                                          |
| **Exit**                       | `exit`                                                                                                                                                                                                                         |
| **Find**                       | `find KEYWORD [MORE_KEYWORDS]` (`find James Jake`)                                                                                                                                                                             |
| **Find By Lesson Day**         | `findday KEYWORD [MORE_KEYWORDS]` (`findday Tuesday`)                                                                                                                                                                          |
| **Find By Tag**                | `findtag KEYWORD [MORE_KEYWORDS]` (`findtag math science`)                                                                                                                                                                     |
| **Help**                       | `help`                                                                                                                                                                                                                         |
| **Link Student To Parent**     | `link ch/STUDENT_NAME pa/PARENT_NAME` (`link ch/James Ho pa/Jane Doe`)                                                                                                                                                         |
| **List**                       | `list`                                                                                                                                                                                                                         |
| **List Archive**               | `listarchive`                                                                                                                                                                                                                  |
| **List Students**              | `liststudents`                                                                                                                                                                                                                 |
| **List Parents**               | `listparents`                                                                                                                                                                                                                  |
| **Pin**                        | `pin INDEX [MORE_INDICES]` (`pin 1 2 3 4`)                                                                                                                                                                                     |    
| **Sort**                       | `sort`                                                                                                                                                                                                                         |
| **Unarchive**                  | `unarchive INDEX [MORE_INDICES]` (`unarchive 1 2 3 4`)                                                                                                                                                                         |
| **Unlink Student from Parent** | `unlink ch/STUDENT_NAME` (`unlink ch/James Ho`)                                                                                                                                                                                |
| **Unpin**                      | `unpin INDEX [MORE_INDICES]` (`unpin 1 2 3 4`)                                                                                                                                                                                 |


## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `addstudent n/NAME`, `NAME` is a parameter which can be used as `addstudent n/John Doe`.
* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.
* Items with `…` after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…` can be used as `NBSP` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.
* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.
* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.
* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

</box>

<box type="warning" seamless>

**Some warnings about the command format:**<br>

* Commands cannot be strung together<br>
  e.g. Entering the input `archive 1 pin 2 list` instead of entering commands separately, `archive 1`, `pin 2`, `list`

</box>

### Adding a student: `addstudent`

Adds a student to the address book.

Format: `addstudent n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS lt/LESSON_TIME edu/EDUCATION [t/TAG]…`

<box type="tip" seamless>

**Tip:** A student can have any number of tags (including 0)
</box>

Examples:

* `addstudent n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 edu/primary lt/wed:13:00 t/friend t/colleague`
* `addstudent n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal edu/Secondary lt/tue:00:03`

### Adding a parent: `addparent`

Adds a parent to the address book.

Format: `addparent n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…`

<box type="tip" seamless>

**Tip:** A parent can also have any number of tags (including 0)
</box>

Examples:

* `addparent n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `addparent n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Assigning grade to students: `grade`

Assigns grade to students in the address book.

Format: `grade INDEX g/GRADE_INDEX`

* The grade index ranges from 0 to 4
* 0: `Unknown`
* 1: `Failing`
* 2: `Satisfactory`
* 3: `Good`
* 4: `Excellent`

Examples:

* `grade 1 g/1` changes grade of first person on list to `Failing`
* `grade 2 g/4` changes grade of second person on list to `Excellent`<br>
  ![result for 'grade 2 g/4'](images/addGradeResult.png)

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Deleting a person : `delete`

Deletes the specified people from the address book.

Format: `delete INDEX [MORE_INDICES]`

* Deletes the person at the specified `INDICES`.
* The indices refer to the index numbers shown in the displayed person list.
* The indices **must be positive integer** 1, 2, 3, …

Examples:

* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.
* `list` followed by `delete 2 3 4 5` deletes the 2nd, 3rd, 4th and 5th people in the address book.

<box type="warning" seamless>
**Caution**:When deleting a person, people linked to that person will be unlinked.
</box>

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [lt/LESSON_TIME] [edu/EDUCATION] [t/TAG]...`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.

Examples:

* `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
* `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:

* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Locating persons by tag: `findtag`

Finds persons whose tags contain any of the given keywords.

Format: `findtag KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `dyslexic` will match `Dyslexic`
* The order of the keywords does not matter. e.g. `dyslexic vegetarian` will match `vegetarian dyslexic`
* Only full words will be matched e.g. `veg` will not match `vegetarian`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `vegetarian dyslexic` will return all users with tags containing `vegetarian` or `dyslexic`

Examples:

* `findtag hyperactive` returns `Alex Yeoh`
* `findtag hyperactive ambitious fierce` returns `Alex Yeoh`, `David Li` and `Charlotte Li`<br>
  ![result for 'findtag friends colleagues'](images/findTagHyperactiveAmbitiousFierce.png)

### Locating students by lesson day: `findday`

Finds students who have lessons on specific days.

Format: `findday DAY [MORE_DAYS]`

* The search is case-insensitive. e.g `tuesday` will match `Tuesday`
* The order of the keywords does not matter. e.g. `tuesday wednesday` will match `wednesday tuesday`
* Only full words will be matched e.g. `tues` will not match `tuesday`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `tuesday wednesday` will return all students with lessons on Tuesday and students with lessons on Wednesday.

Examples:

* `findday tuesday` returns `Alex Yeoh` and `Bernice Yu`
* `findtag friends colleagues` returns `Alex Yeoh`, `Bernice Yu` and `Roy Balakrishnan`<br>
  ![result for 'findday tuesday wednesday'](images/findDayTuesdayWednesday.png)

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Pinning a person: `pin`

Pins the specified people to the top of the list in the address book.

Format: `pin INDEX [MORE_INDICES]`

* Pins the person at the specified `INDICES`.
* The indices refer to the index numbers shown in the displayed person list.
* The indices **must be positive integer** 1, 2, 3, …

Examples:

* `list` followed by `pin 2` pins the 2nd person in the address book.
* `find Betsy` followed by `pin 1` pins the 1st person in the results of the `find` command.
* `list` followed by `pin 2 3 4 5` pins the 2nd, 3rd, 4th and 5th people in the address book.

### Unpinning a person : `unpin`

Unpins the specified people in the address book.

Format: `unpin INDEX [MORE_INDICES]`

* Unpins the person at the specified `INDICES`.
* The indices refer to the index numbers shown in the displayed person list.
* The indices **must be positive integer** 1, 2, 3, …

Examples:

Assuming a list of 7 contacts in the main list, inclusive of a contact named `Betsy`,

* `list` followed by `unpin 2` unpins the 2nd person in the address book.
* `find Betsy` followed by `unpin 1` unpins the 1st person in the results of the `find` command.
* `list` followed by `unpin 2 3 4 5` unpins the 2nd, 3rd, 4th and 5th people in the address book.

### Archive a person : `archive`

Archives the specified people in the address book, hiding them from the main list.

Format: `archive INDEX [MORE_INDICES]`

* Archives the person at the specified `INDICES`.
* The indices refer to the index numbers shown in the displayed person list.
* The indices **must be positive integer** 1, 2, 3, …

Examples:

Assuming a list of 7 contacts in the main list, inclusive of a contact named `Betsy`,

* `list` followed by `archive 2` archives the 2nd person in the address book.
* `find Betsy` followed by `archive 1` archives the 1st person in the results of the `find` command.
* `list` followed by `archive 2 3 4 5` archives the 2nd, 3rd, 4th and 5th people in the address book.

### Unarchive a person : `unarchive`

Unarchives the specified people in the address book, unhiding and displaying the contact in the main list.

Format: `unarchive INDEX [MORE_INDICES]`

* Unarchives the person at the specified `INDICES`.
* The indices refer to the index numbers shown in the displayed person list.
* The indices **must be positive integer** 1, 2, 3, …

Examples:

Assuming a list of 7 contacts in the archive list, inclusive of a contact named `Betsy`,

* `listarchive` followed by `unarchive 2` unarchives the 2nd person in the address book archives.
* `listarchive` followed by `find Betsy` followed by `unarchive 1` unarchives the 1st person in the results of the `find` command.
* `listarchive` followed by `unarchive 2 3 4 5` unarchives the 2nd, 3rd, 4th and 5th people in the address book.

### Listing all persons : `list`

Shows a list of all persons, except for archived persons, in the address book.

Format: `list`

### Listing all archived persons : `listarchive`

Shows a list of all archived persons in the address book.

Format: `listarchive`

### Listing students : `liststudents`

Lists all students, except for archived students, in the address book.

Format: `liststudents`

### Listing parents : `listparents`

Lists all parents, except for archived parents, in the address book.

Format: `listparents`

### Link a parent to a student : `link`

Links a parent to a student in a parent-child relationship.

Format: `link ch/STUDENT_NAME pa/PARENT_NAME`

* Links the specified student with name `STUDENT_NAME` with the specified parent with name `PARENT_NAME`.
* The names provided are case-sensitive.
* The names provided must match exactly the names displayed in the address book.

Examples:

* `link ch/John Doe pa/Jane Doe` links the student with name `John Doe` with the parent with name `Jane Doe`, assuming both exist in the address book.

### Unlink a parent from a student : `unlink`

Removes the parent-child relationship from the specified student.

Format: `unlink ch/STUDENT_NAME`

* Removes the parent-child relationship from the specified student with name `STUDENT_NAME`.
* The name provided is case-sensitive.
* The name provided must match exactly the name displayed in the address book.

Examples:

* `unlink ch/John Doe` removes the parent-child-relationship from `John Doe`, assuming `John Doe` exists in the address book and has a parent-child relationship.

### Sorting all persons alphabetically: `sort`

Sorts all person in the address book, keeping pinned persons on top.

Format: `sort`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

The GOATS data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

The GOATS data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`.

<box type="warning" seamless>
**Caution:**
Do NOT edit the contents in the file. Changes made in JSON file can cause GOATS to behave in unexpected ways.
</box>

---

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous GOATS home folder.

---

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

---
