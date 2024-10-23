---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# StudentManagerPro User Guide

StudentManagerPro (SMP) is a **desktop app for managing students, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, SMP can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all student contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 c/1A s/M r/1` : Adds a student named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd student contact shown in the current list.

   * `clear` : Deletes all student contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

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

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a student: `add`

Adds a student to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS c/CLASS s/SEX r/REGISTER_NUMBER [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A student can have any number of tags (including 0)
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 c/1A s/M r/1`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal c/2C s/F r/2`

### Listing all students : `list`

Shows a list of all students in the address book.

Format: `list`

### Editing a student : `edit`

Edits an existing student in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [c/CLASS] [s/SEX] [r/REGISTER_NUMBER] [en/ECNAME] [ep/ECNUMBER] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

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

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Adding an Emergency contact's name : `addEcName`

Adds an emergency contact's name to the specified person in the address book.

Format: `addEcName INDEX en/[ECNAME]`

<box type="tip" seamless>

**Tip:** You can delete the emergency contact's name by leaving the `ECNAME` field empty.
</box>

* Adds the emergency contact's name `ECNAME` to the person at the specified `INDEX`
* Deletes the emergency contact's name at the specified `INDEX`
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `addEcName 1 en/John Doe` to add the emergency contact's name "John Doe" to the 1st person in the list.
* `addEcName 2 en/` to delete the emergency contact's name from the 2nd person in the list.

### Adding an Emergency contact's number : `addEcNumber`

Adds an emergency contact's number to the specified person in the address book.

Format: `addEcNumber INDEX [ep/ECNUMBER]`

<box type="tip" seamless>

**Tip:** You can delete the emergency contact's number by leaving the `ECNUMBER` field empty.
</box>

* Adds the emergency contact's number `ECNUMBER` to the person at the specified `INDEX`
* Deletes the emergency contact's number at the specified `INDEX`
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `addEcNumber 1 ep/91234567` to add the emergency contact's number 91234567 to the 1st person in the list.
* `addEcNumber 2 ep/` to delete the emergency contact's number from the 2nd person in the list.

### Adding an Exam : `addExam`

Adds an exam to every person in the address book.

Format: `addExam ex/EXAMNAME`

<box type="tip" seamless>

**Tip:** If a new student is added after an exam is added, the exam has to be added again for it to be reflected for the new student.
</box>

* The exam name can only contain alphanumeric characters and spaces.

Examples:
* `addExam ex/Midterm`

### Adding an Exam Score: `addExamScore`

Adds an exam score for the specified exam for the person at the specified index.

Format: `addExamScore INDEX ex/EXAMNAME sc/EXAMSCORE`

* The exam score must be an integer between 0 and 100, or `NIL`.
* The exam score can be edited using the same command with a different exam score.
* The exam score can be deleted by entering the exam score as `NIL`.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `addExamScore 1 ex/Midterm sc/70`
* `addExamScore 1 ex/Midterm sc/NIL`

### Sorting the list : `sort`

Sorts the list of students based on the students attributes.

Format: `sort [ATTRIBUTE]`

<box type="tip" seamless>

**Tip:** Students attributes include: name, phone, email, address, sex, register number, student class, emergency contact name, emergency contact number.
</box>

* Sorts the list based on the ATTRIBUTE
* Unsort the list when the attribute is `none`

Examples:
* `sort name` to sort the list based on student's names
* `sort register number` to sort the list based on student's register numbers
* `sort none` to unsort the list

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

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS c/CLASS s/SEX r/REGISTER_NUMBER [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 c/1A s/M r/1 t/friend t/colleague`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [c/CLASS] [s/SEX] [r/REGISTER_NUMBER] [en/ECNAME] [ep/ECNUMBER] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**   | `list`
**Help**   | `help`
**Add Emergency Contact Name**  | `addEcName INDEX [en/EMERGENCY CONTACT NAME]` <br> e.g., `addEcName 1 en/John Doe`
**Add Emergency Contact Number**  | `EcNumber INDEX [ep/EMERGENCY_CONTACT_NUMBER]`<br> e.g., `EcNumber 2 ep/91231234`
**AddExam** | `addExam ex/EXAMNAME` <br> e.g., `addExam ex/Midterm`
**AddExamScore** | `addExamScore INDEX ex/EXAMNAME sc/SCORE` <br> e.g., `addExamScore 1 ex/Midterm sc/70`
**Sort**    |   `sort [ATTRIBUTE]` <br> e.g., `sort student class`
