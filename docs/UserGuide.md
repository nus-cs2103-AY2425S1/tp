
---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# EduLog User Guide

EduLog is a **desktop app for managing contacts, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, EduLog can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

2. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-W09-2/tp/releases).

3. Copy the file to the folder you want to use

4. Open a command terminal. Go to the folder where you saved the jar file by typing cd followed by the folder's path. Then, type `java -jar edulog.jar` and press Enter to start the application.
    * A path is the location of a file or folder on your computer. It shows how to find the file starting from the main storage area (like the C: drive on Windows). For example, if your file is in a folder called "MyApps" on your desktop, the path might look like this:
    * On Windows: `C:\Users\Your\Username\Desktop\MyApps`
    * On Mac/Linux: `/Users/YourUsername/Desktop/MyApps`



A screen (User Interface) similar to the below should appear in a few seconds. Note how the app contains some sample data.

![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing `help` and pressing Enter will open the help window.

   Some example commands you can try:

    * `list` : Lists all contacts.

    * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the EduLog.

    * `delete 3` : Deletes the 3rd contact shown in the current list.

    * `clear` : Deletes all contacts.

    * `exit` : Exits the app.



6. Refer to the [Features](#features) below for details of each command.
 

# **Features**

<box type="info" seamless> 

- **Notes about the command format:** <br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.

  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional. <br>

  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…` after them can be used multiple times including zero times.

  e.g. `[t/TAG]...` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.

  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.

  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

</box>

## **General commands**

### **Viewing help : `help`**

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### **Exiting the program : `exit`**

Exits the program.

Format: `exit`

## Student Commands

### Adding a student: `add`

Adds a student to the edulog.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<box type="tip" seamless>
**Tip:** A person can have any number of tags (including 0)
</box>

Examples:

* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all students : `list`

Shows a list of all students in the edulog.

Format: `list`

### Editing a student : `edit`

Edits an existing student in the edulog.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the student will be removed i.e adding of tags is not cumulative.
* You can remove all the student’s tags by typing `t/` without specifying any tags after it.

Examples:

* `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st student to be `91234567` and `johndoe@example.com` respectively.
* `edit 2 n/Betsy Crower t/` Edits the name of the 2nd student to be `Betsy Crower` and clears all existing tags.

### Locating students by name: `find`

Finds students whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Students matching at least one keyword will be returned (i.e. `OR` search). e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:

* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`

  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a student : `delete`

Deletes the specified student from the edulog.

Format: `delete INDEX`

* Deletes the student at the specified `INDEX`.
* The index refers to the index number shown in the displayed student list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:

* `list` followed by `delete 2` deletes the 2nd student in the edulog.
* `find Betsy` followed by `delete 1` deletes the 1st student in the results of the `find` command.

### Deleting a student by Name: `delete`

Deletes the specified student from the edulog.

Format: `delete Name`

* Deletes the student by the specified Name.
* The name refers to the name shown in the displayed student list.
* The name is case sensitive.

Examples:

* `list` followed by `delete Nikhil` deletes student named “Nikhil” in the edulog.
* `find Betsy` followed by `delete Betsy` deletes the student in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the edulog.

Format: `clear`

### Marking a student's attendance: `mark'

Denotes an existing student as paid. The student may either be identified by index number
in the edulog (starting from 1) or name (this is both case-sensitive and space-sensitive within the name provided).

Format: `mark <INDEX - must be a positive integer>` OR `mark <STUDENT NAME>`

Examples:
* `mark 3`
* `mark Alex Yeoh`

### Marking all students' attendance: `markall'

	@@ -171,39 +210,46 @@ Examples:
Denotes an existing student as unpaid. The student may either be identified by index number
in the edulog (starting from 1) or name (this is both case-sensitive and space-sensitive within the name provided).

Format: `unmark <INDEX - must be a positive integer>` OR `unmark <STUDENT NAME>`

Examples:
* `unmark 3`
* `unmark Alex Yeoh`

### Unmarking all students' attendance: `unmarkall'

Denotes all existing students as unpaid.

Format: `unmarkall`

Examples:
* `unmarkall`
* `unmarkall ofoeofn4334f30f04a3dr34r` (all subsequent inputs are ignored)

## Calendar Commands

### Adding a lesson: `addc`

Adds a lesson to the edulog.

Format: addc `d/DESCRIPTION day/DAY OF THE WEEK from/START TIME (24H FORMAT) to/END TIME (24H FORMAT)​`

Examples:

* `addc d/Sec 4 Math Class day/Monday from/2230 to/0030`

### Listing all lessons : `dc`

Shows a list of all lessons in the edulog.

Format: `dc`

### Deleting a lesson : `deletec`

Deletes the student identified by the description used in the displayed calendar.

Format: `deletec DESCRIPTION`

* Deletes the lesson with the given description.
* The description refers to the description shown in the displayed lesson list.

Examples:

* `deletec Secondary 4 Chemistry Class`

## Data Files

### Saving the data

EduLog data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

EduLog data are saved automatically as a JSON file `[JAR file location]/data/edulog.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>
**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

*Details coming soon ...*

## 

## FAQ

**Q**: How do I transfer my data to another Computer?

**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous EduLog home folder.

## 

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimise the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimised, and no new Help Window will appear. The remedy is to manually restore the minimised Help Window.

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**   | `list`
**Help**   | `help`

