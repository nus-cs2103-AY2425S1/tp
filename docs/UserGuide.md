---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# UniVerse User Guide

UniVerse is a **desktop app for managing contacts**, optimized for use via a **Command Line Interface (CLI)** 
while incorporating a **Graphical User Interface (GUI)** for ease of use. UniVerse is designed to help you manage 
detailed contact information, including academic and professional details, quickly and efficiently.

<!-- * Table of Contents -->

<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

2. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-T17-1/tp/releases/tag/v1.4).

3. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command
to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will 
open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 u/NUS m/Computer Science b/13-12-2003` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

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


### Adding a person: `add`

Adds a person to the address book.

Format:
```plaintext
add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS u/UNIVERSITY m/MAJOR b/BIRTHDATE [w/WORK_EXPERIENCE] [i/INTEREST]... [t/TAG]...
```

<box type="tip" seamless>

**Tip:** A person can have any number of interests and tags (including 0)
</box>

Parameters:
* `n/NAME`: Full name of the contact.
* `p/PHONE_NUMBER`: 8-15 digit phone number.
* `e/EMAIL`: Email address in a valid format.
* `a/ADDRESS`: Contact's address.
* `u/UNIVERSITY`: University name.
* `m/MAJOR`: Major or field of study.
* `b/BIRTHDATE`: Date of birth in `dd-mm-yyyy` format.
* `[w/WORK_EXPERIENCE]`: Work experience in the format `ROLE,COMPANY,YEAR`.
* `[i/INTEREST]...`: Interests of the contact.
* `[t/TAG]...`: Tags for categorization.

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 w/Intern,Google,2024 u/NUS m/Computer Science t/friends t/owesMoney i/swimming i/reading b/13-12-2003`
* `add n/Betsy Crowe p/98765431 e/betsycrowe@example.com a/Bishan Street 22, #02-12 w/Intern,Meta,2024 u/NTU m/Computer Science t/classmate b/01-01-2001`

### Adding Interests: addi

Adds interest(s) to an existing contact.
Format:
```plaintext
addi in/INDEX i/INTEREST...
```


* in/INDEX: Contact's position in the list.
* i/INTEREST...: Interests to add. Can add multiple interests.

Example:
Format:
```plaintext
addi in/1 i/Swimming i/Cycling
```

### Adding Work Experience to existing contact: `addw`

Adds work experience to an existing contact.

Format:
```plaintext
addw in/INDEX w/WORK EXPERIENCE
```

* `in/INDEX`: Index of contact user wishes to add work experience to.
* `w/WORK EXPERIENCE` : Work Experience user wishes to add.
* Index has to be a number from 1 to the total number of existing contacts in the contact list.
* Work experience in the format `ROLE,COMPANY,YEAR`
* If existing contact has a current work experience, it will just be replaced by the user input.

Example:
*  `addw in/1 w/Intern,Google,2024` Adds the work experience `Intern,Google,2024` to the 1st person in the contact list.

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format:
```plaintext
list
```

### Editing a person : `edit`

Edits an existing person in the address book.

Format:
```plaintext
edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​
```

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

Format:
```plaintext
find KEYWORD [MORE_KEYWORDS]
```
<box type="tip" seamless>

**Tip:** Type `list` to view the full list of contacts again.
</box>


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

### Finding Contacts by Interest: `findi`

Finds contacts with specific interests.

Format:
```plaintext
findi i/INTEREST
```

* `i/INTEREST`: Interest to search for.

Example:
```plaintext
findi i/Swimming
```

### Deleting a person : `delete`

Deletes the specified person from the address book.

```plaintext
delete INDEX
```

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

```plaintext
clear
```

### Exiting the program : `exit`

Exits the program.

```plaintext
exit
```

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

## Command Summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS u/UNIVERSITY m/MAJOR b/BIRTHDATE [w/WORK_EXPERIENCE]... [i/INTEREST]... [t/TAG]...`<br> e.g., `add n/John Doe p/98765432 e/johnd@example.com a/123 Main St u/NUS m/Engineering b/13-12-2003`
**Add Interests**    | `addi in/INDEX i/INTEREST...` <br> e.g., `addi in/1 i/Swimming`
**Add Work Experience**    | `addw in/INDEX w/ROLE,COMPANY,YEAR` <br> e.g., `addw in/1 w/Software Engineer,Google,2023`
**Delete** | `delete INDEX` <br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [u/UNIVERSITY] [m/MAJOR] [b/BIRTHDATE] [w/WORK_EXPERIENCE]... [i/INTEREST]... [t/TAG]...`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Find by Interest** | `findi i/INTEREST` <br> e.g., `findi i/Swimming`
**Find by University** | `findu u/UNIVERSITY` <br> e.g., `findu u/NUS`
**List**   | `list`
**Help**   | `help`
