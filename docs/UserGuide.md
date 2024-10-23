---
layout: page
title: User Guide
---

Welcome to EduConnect – a tool designed to help teachers manage student and teacher details with speed and simplicity. EduConnect streamlines your everyday tasks, allowing you to organize important information in a fast, efficient way. By typing commands, you can quickly update, search, and handle details with minimal effort, making it a valuable companion for busy school environments.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Check that Java is installed:
   - EduConnect requires **Java 17 or higher** to run. If you're not sure if you have the correct Java version installed, you can click [here for the relevant instructions]().

2. Download EduConnect onto your computer:
   - Download the latest version of EduConnect by clicking [here](https://github.com/AY2425S1-CS2103T-F12-2/tp/releases).

3. Move the file to a folder:
   - After downloading, place the `educonnect.jar` file in any folder on your computer where you'd like to store the app.

4. Open EduConnect:
   - Open the "Command Prompt" (for Windows) or "Terminal" (for Mac/Linux).
   - Type `cd` followed by the folder location where you saved the EduConnect file. For example:
     - On Windows: `cd C:\Users\YourName\Documents\EduConnect`
     - On Mac/Linux: `cd /Users/YourName/Documents/EduConnect`
   - Then type this command to start the app:
   
        `java -jar educonnect.jar`
   - After a few seconds, the EduConnect window will appear with some sample data to help you get started.
   ![Ui](images/Ui.png)

5. Enter a command:
   - Type a command in the command box and press **Enter** to send it. For example, typing `help` and pressing Enter will open a help window.

    Here are some commands you can try:
    - `list`: Displays all the student and teacher details.

    - `student /name John Doe /gender male /contact 98765432 /email johnd@example.com /address 311, Clementi Ave 2, #02-25 /subject Physics /classes 7A`: Adds a new student with the specified details

    - `delete 3`: Deletes the 3rd contact shown in the current list.

    - `clear`: Deletes all contacts.

    - `exit`: Exits the app.

    For more detailed explanations of each command, see the [Features](#features) section below.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add /name NAME`, `NAME` is a parameter which can be used as `add /name John Doe`.

* Items in square brackets are optional.<br>
  e.g `edit [/name NAME] [/contact PHONE_NUMBER]` can be used as `edit /name John` or as `edit /contact 94567732` or as `edit /name John /contact 94567732`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `/name NAME /contact PHONE_NUMBER`, `/contact PHONE_NUMBER /name NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a student: `student`

Adds a student to EduConnect.

Format: `student /name NAME /gender GENDER /contact PHONE_NUMBER /email EMAIL /address ADDRESS /subject SUBJECT /classes CLASSES`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
The parameters need not be specified in that order 
</div>

Example:
* `student /name John Doe /gender male /contact 98765432 /email johnd@gmail.com /address 311, Clementi Ave 2, #02-25 /subject Physics /classes 7A`


### Adding a teacher: `teacher`

Adds a teacher to EduConnect.

Format: `teacher /name NAME /gender GENDER /contact PHONE_NUMBER /email EMAIL /address ADDRESS /subject SUBJECT /classes CLASSES`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
The parameters need not be specified in that order 
</div>

Example:
* `teacher /name Elizabeth Chua /gender female /contact 95673211 /email elizchua@yahoo.com /address Blk 30 Lorong 3 Serangoon Gardens, #07-18 /subject English /classes 5A, 8C`


### Listing all persons : `list`

Shows a list of all students and teachers in EduConnect.

Format: `list`

### Editing a person : `edit`

Edits an existing student or teacher in EduConnect.

Format: `edit INDEX [/name NAME] [/gender GENDER] [/contact PHONE] [/email EMAIL] [/address ADDRESS] [/subject SUBJECT] [/classes CLASSES]`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person in the displayed list to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower` Edits the name of the 2nd person in the displayed list to be `Betsy Crower`.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find [/name NAME] [/gender GENDER] [/contact PHONE] [/email EMAIL] [/address ADDRESS] [/subject SUBJECT] [/classes CLASSES]`

* At least one of the optional fields must be provided.
* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `/name Hans Bo` will match `Bo Hans`
* Only full words will be matched e.g. `/name Han` will not match `Hans`
* Persons matching at least one field will be returned (i.e. `OR` search).
  e.g. `/name Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find /name John` returns `John` and `John Doe`
* `find /name Mary /classes 7A` returns `Mary Tan` (who has the name "Mary") and `David Lee` (who teaches or is in class 7A) <br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Sorting the results : `sort`

Sorts the results by name / subject / classes.

Format: `sort [ATTRIBUTE]`

* Sorts the results based on the specified `ATTRIBUTE`. The available attributes are `name`, `subject`, `class`.
* `sort name` sorts the results by name in alphabetical order
* `sort subject` sorts the results by the subjects they take in alphabetical order
* `sort class` sorts the results by class in alphanumerical order.
* The command applies to the current list of displayed results. 
* Sorting is case-insensitive.

Examples:
* `list` followed by `sort name` sorts all students in the address book by name.

### Undoing the last command : `undo`

Reverts the most recent change made to the address book.

Format: `undo`

* Reverts the last modification command, restoring the address book to its previous state.
* Only commands that modify the data (e.g., add, delete, edit, clear) can be undone.
* Multiple undo commands can be used consecutively to revert multiple changes, one step at a time.

Examples:
* `add John Doe` followed by `undo` removes John Doe from the address book.
* `delete 3` followed by `undo` restores the deleted student back to the list.

### Deleting a person : `delete`

Deletes the specified person or persons from EduConnect.

Format: `delete INDEX…​`

* You can delete one or more people by specifying their `INDEX`(es) in the list.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in EduConnect.
* `find /name Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.
* `delete 1 2 3` deletes the 1st, 2nd and 3rd persons in the list in one command.

### Clearing entries : `clear`

Clears all entries from EduConnect or specific entries based on the provided criteria.

Format: `clear [/name NAME] [/gender GENDER] [/contact PHONE] [/email EMAIL] [/address ADDRESS] [/subject SUBJECT] [/classes CLASSES]`

* If no fields are provided, all entries will be cleared.
* If one or more optional fields are provided, only entries matching **at least one** of those fields will be cleared.

Examples:
* `clear` clears all entries in EduConnect.
* `clear /classes 7A` clears all entries related to class 7A (students or teachers).
* `clear /name John /subject Physics` clears entries for all persons named John or anyone associated with the subject Physics.

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

EduConnect data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

EduConnect data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, EduConnect will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the EduConnect to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: First, install EduConnect on the new computer. Then, copy the data file from your old computer and replace the empty data file in the new installation folder with your existing one. This will transfer all your previous data to the new computer.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action          | Format, Examples                                                                                                                                                                                                                                                                                                    |
|-----------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Student** | `student n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `student /name John Doe /gender male /contact 98765432 /email johnd@gmail.com /address 311, Clementi Ave 2, #02-25 /subject Physics /classes 7A`                                                                                             |
| **Add Teacher** | `teacher /name NAME /gender GENDER /contact PHONE_NUMBER /email EMAIL /address ADDRESS /subject SUBJECT /classes CLASSES` <br/> e.g., `teacher /name Elizabeth Chua /gender female /contact 95673211 /email elizchua@yahoo.com /address Blk 30 Lorong 3 Serangoon Gardens, #07-18 /subject English /classes 5A, 8C` |
| **Clear**       | `clear`                                                                                                                                                                                                                                                                                                             |
| **Delete**      | `delete INDEX...`<br> e.g., `delete 3`, `delete 1, 2, 4`                                                                                                                                                                                                                                                            |
| **Sort**        | `sort ATTRIBUTE`<br/>e.g.,`sort name`,`sort subject`                                                                                                                                                                                                                                                                |
| **Undo**        | `undo`                                                                                                                                                                                                                                                                                                              |
| **Edit**        | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                                                                                                                                                                                 |
| **Find**        | `find [/name NAME] [/gender GENDER] [/contact PHONE] [/email EMAIL] [/address ADDRESS] [/subject SUBJECT] [/classes CLASSES]`<br> e.g., `find /name James /classes 8B`                                                                                                                                              |
| **List**        | `list`                                                                                                                                                                                                                                                                                                              |
| **Help**        | `help`                                                                                                                                                                                                                                                                                                              |
