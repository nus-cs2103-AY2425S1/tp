# AcademyAssist User Guide

AcademyAssist is a **desktop app for managing student contacts in a tuition center, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AcademyAssist can get your contact management tasks done faster than traditional GUI apps.

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

2. Download the latest `AcademyAssist.jar` file from the official website.

3. Copy the file to the folder you want to use as the _home folder_ for your AcademyAssist.

4. Double-click the file to start the app. The GUI should appear in a few seconds.

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">
**:information_source: Notes about the command format:**

* Words in `UPPER_CASE` are the parameters to be supplied by the user.
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Parameters can be in any order.
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

</div>

### Adding a student : `add`

Adds a new student to the tuition center management system.

Format: `add n/NAME ic/IC_NUMBER e/EMAIL p/PHONE_NUMBER a/ADDRESS c/CLASS y/ACADEMIC_YEAR`

* `NAME` should be 1-100 characters long and contain only alphabets and spaces.
* `IC_NUMBER` should follow the format of Singaporean IC and FIN numbers (e.g., S1234567A).
* `PHONE_NUMBER` should be an 8-digit number.
* `EMAIL` is optional and should follow the format username@domain.
* `ADDRESS` is optional.
* `CLASS` should be a combination of Subject and Number (e.g., Science1).
* `ACADEMIC_YEAR` should be in the format Standard[Number] (e.g., Standard1).

Examples:
* `add n/John Doe ic/T384859A e/johndoe@gmail.com p/81003999 a/9 Smith Street c/Science1 y/Standard1`

### Deleting a student : `del`

Removes a student from the tuition center management system.

Format: `del STUDENT_ID`

* `STUDENT_ID` should be a 5-digit number.

Examples:
* `del 12345`

### Editing a student : `edit`

Edits an existing student's details in the system.

Format: `edit STUDENT_ID FIELD:NEW_VALUE`

* `STUDENT_ID` should be a 5-digit number.
* `FIELD` can be one of: Phone Number, Address, Class Taken, or Academic Year.
* `NEW_VALUE` should follow the format for the respective field.

Examples:
* `edit 12345 Address:New Address`
* `edit 12345 Phone:91234567`

### Viewing all students : `view`

Shows a list of all students in the system.

Format: `view`

### Finding a student : `find`

Finds students whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive.
* Only the name is searched.
* Students matching at least one keyword will be returned.

Examples:
* `find John` returns `John` and `John Doe`
* `find John Jane` returns any student having names `John` or `Jane`

### Adding a class to a student : `addc`

Adds a class to an existing student's record.

Format: `addc STUDENT_ID CLASS_NAME`

* `STUDENT_ID` should be a 5-digit number.
* `CLASS_NAME` should be a combination of Subject and Number (e.g., Science1).

Examples:
* `addc 12345 Science1`

### Sorting students : `sort`

Sorts the list of students based on a specified field.

Format: `sort FIELD`

* `FIELD` can be either `name` or `class`.

Examples:
* `sort name`
* `sort class`

### Clearing all entries : `clear`

Clears all student entries from the system.

Format: `clear`

### Viewing help : `help`

Shows a message explaining how to access the help page.

Format: `help`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AcademyAssist data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AcademyAssist data are saved automatically as a JSON file `[JAR file location]/data/academyassist.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">
:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AcademyAssist will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AcademyAssist to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AcademyAssist folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action | Format, Examples |
|--------|-------------------|
| **Add** | `add n/NAME ic/IC_NUMBER e/EMAIL p/PHONE_NUMBER a/ADDRESS c/CLASS y/ACADEMIC_YEAR` <br> e.g., `add n/John Doe ic/T384859A e/johndoe@gmail.com p/81003999 a/9 Smith Street c/Science1 y/Standard1` |
| **Delete** | `del STUDENT_ID`<br> e.g., `del 12345` |
| **Edit** | `edit STUDENT_ID FIELD:NEW_VALUE`<br> e.g.,`edit 12345 Address:New Address` |
| **View** | `view` |
| **Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find John Jane` |
| **Add Class** | `addc STUDENT_ID CLASS_NAME`<br> e.g., `addc 12345 Science1` |
| **Sort** | `sort FIELD`<br> e.g., `sort name` |
| **Clear** | `clear` |
| **Help** | `help` |
| **Exit** | `exit` |