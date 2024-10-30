---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# EduContacts User Guide

EduContacts is a **desktop app for Educators in Tertiary Institution to manage contacts, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.
[Download Java here](https://www.oracle.com/sg/java/technologies/downloads/) if you haven't already.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-F15-2/tp/releases).

1. Copy the file to the folder you want to use as the home folder for your EduContacts.

1. Open a command terminal.

   To navigate to the folder where you placed the `.jar` file, use the `cd` command. For example, if you placed the file in a folder named `EduContacts` on your Desktop, you would enter:

   ```bash
   cd ~/Desktop/EduContacts
   ```
   
   and use the following command to run the application:

   ```bash
   java -jar educontacts.jar
   ```

   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>

   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   ```bash 
   list 
      ```
   Lists all contacts.

   ```bash 
   add 12345678 n/John Doe p/99999999 e/johndoe@example.com a/123 Jane Doe Road c/Computer Science t/Student
   ``` 
   Adds a contact named `John Doe` to EduContacts.
   
   ```bash 
   delete 12345678
   ```
   Deletes a student contact with StudentID `12345678`.

   ```bash
   clear
   ```
   Deletes all contacts.

   ```bash
   exit
   ```
   Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.


* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.


* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.


* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaning how to access the help page.

Format
```bash 
help
```
![help message](images/helpMessage.png)



### Adding a person: `add`

Adds a person to the address book.

Format: 
```bash
add ID n/NAME p/PHONE e/EMAIL a/ADDRESS c/COURSE t/TAG
```

Examples:
* `add 12345678 n/John Doe p/98981212 e/johndoe@example.com a/123 Jane Doe Road c/Computer Science t/Student`
* `add 87654321 n/Betsy Crowe t/ Student e/betsycrowe@example.com a/Blk 30 Geylang Street 29, #06-40 p/1234567 c/Business Analytics`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: 
```bash
list
```

### Editing a person : `edit`

Edits an existing person in the address book.

Format:
```bash
edit ID [FIELD_TO_EDIT_PREFIX] [NEW_VALUE]
```

* Edits a student's details according to the fields specified.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.


Examples:
*  `edit 12345678 m/CS2103T CS2101`

### Listing students by certain attributes : `filter`

Filter students based on their Names, Courses and Modules.

Format:
```bash
filter [KEYWORD_PREFIX] [MORE_KEYWORDS]
```

* **For filtering by Names:**
  * Only full words will be matched e.g. `Han` will not match `Hans`
  * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
  * Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

<!-- -->

* **For filtering by Module:**

  * Partial matching is supported, allowing users to input parts of module codes. e.g. `CS21` will return modules like "CS2103T" and "CS2101."

<!-- -->

* **For filtering by Course:**

  * Partial matching is supported, but the first keyword must match the beginning of the course name. 
    e.g `Engineer` will match courses like "Engineering" but not "Civil Engineering".

<box type="tip" seamless>

**Tip:**  The search is case-insensitive. e.g `cs1231s` will match `CS1231S`.
</box>

Examples:
* ```filter n/John``` returns `john` and `John Doe`
* `filter m/CS2103T` returns a list of all students with module CS2103T. 
* `filter c/Computer Science` returns a list of all students with course Computer Science.<br>
* `filter n/alex david` returns `Alex Yeoh`, `David Li`

  ![result for 'find alex david'](images/filterAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: 
```bash
delete ID
```

* Deletes student with the specified `ID`.

Examples:
* `delete 12345678` will delete student contact with `ID: 12345678`.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: 
```bash
clear
```

### Exiting the program : `exit`

Exits the program.

Format:
```bash
exit
```

### Saving the data

EduContacts data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

EduContacts data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, EduContacts will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the EduContacts to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
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
**Add**    | `add ID n/NAME p/PHONE e/EMAIL a/ADDRESS c/COURSE t/TAG` <br> e.g., `add 12345678 n/John Doe p/99999999 e/johndoe@example.com a/123 Jane Doe Road c/Computer Science t/Student`
**Clear**  | `clear`
**Delete** | `delete ID`<br> e.g., `delete 12345678`
**Edit**   | `edit ID [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [c/COURSE] [t/TAG]…​`<br> e.g.,`edit 12345678 p/91234567 e/johndoe@example.com`
**Filter**   | `find [n/NAME] [c/COURSE] [m/MODULE]`<br> e.g., `find n/James Jake`
**List**   | `list`
**Help**   | `help`
