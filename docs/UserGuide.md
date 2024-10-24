---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# SocialBook User Guide

SocialBook is a **desktop app for managing contacts, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, SocialBook can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103-F10-2/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your SocialBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar socialbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 d/06-01-2024` : Adds a contact named `John Doe` to the SocialBook.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.<br>
  e.g `n/NAME [a/ADDRESS]` can be used as `n/Jane Smith a/123 Hollywood Street 55` or as `n/Jane Smith`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters will result in an error only for the `list` command. Other commands like `help`, `exit`, and `clear` will ignore any additional parameters and execute as intended.
  e.g. if the command specifies `help 123`, `exit please` or `clear 5`, it will be interpreted as help, exit or clear respectively. However, if the command specifies `list something`, it will throw an error.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [t/TAG]…​ [d/DATE_OF_LAST_VISIT] [ec/EMERGENCY_CONTACT]`

<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0).<br>

</box>
<box type="tip" seamless>

**Tip:** The only required fields for a person are a name and a phone number, so you can create a contact with just those 2 fields. Providing an email, address, date of last visit, emergency contact or tags is optional.

</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 d/02-01-2024`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/12345678 t/criminal d/03-28-2024`
* `add p/12345678 n/Jane Smith d/01-01-2024 ec/98765432`
* `add p/12345678 n/Jane Smith d/01-01-2024`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​ [d/DATE_OF_LAST_VISIT] [ec/EMERGENCY_CONTACT]`

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

Finds contacts whose names or/and phone numbers or/and address contain any of the given field keywords.

Format: `find [n/NAMEKEYWORDS] [p/PHONEKEYWORDS] [a/ADDRESSKEYWORDS]`

**NOTE:** At least one field MUST be provided  
  e.g. `find n/Hans` or `find p/12345678` or `find a/wall street` will work  
  e.g. `find Hans` or `find wall street` or `find` will fail
* The search is case-insensitive. e.g `hans` will match `Hans` or `wall Street` will match `Wall Street`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Partial words will be matched e.g. `Han` will match `Hans`
* Contacts matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
* If more than one fields are specified, contacts will be matched by multiple fields (i.e. `AND` search).

Examples:
* `find n/John` returns `john` and `John Doe`
* `find n/alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png) 
* `find p/87438807 91031282` returns `Alex Yeoh`, `David Li`
* `find a/serangoon` returns `David Li`
* `find n/alex p/87438807 a/geylang` returns `Alex Yeoh`

### Deleting a person : `delete`

Deletes the specified person or persons from the address book.

Format: `delete INDEX` `delete INDEX INDEX ...`

* Deletes the person or persons at the specified `INDEX` or `INDEX INDEX ...`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.
* `list` followed by `delete 1 2` deletes the 1st and 2nd person in the address book.

### Adding remarks to person : `remark`

Add remarks to an existing person in the address book. 

Format: `remark INDEX r/REMARK`

* Adds remarks to the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `remark 1 r/Financial Issues` Adds the remark of the 1st person to be `Financial Issues`.
* `remark 1 r/` Clears remarks (if any) of the 1st person.

### Sorting the person list : `sort`

Sorts the list of persons being viewed by name or date of last visit in ascending or descending order.

Format: `sort paramater/order`

* Sorts the displayed list of persons according to the specified order.
* Order can be specified as ascending by leaving the order blank or **asc** **ascending**
* Order can be specified as descending by **descending** or **desc**

Examples:
* `sort n/` sorts name in ascending order.
* `sort d/descending` sorts by date of last visit in descending order.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Populating with dummy data : `seed`

Adds dummy data to the address book.

Format: `seed`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

SocialBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

SocialBook data are saved automatically as a JSON file `[JAR file location]/data/socialbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If a person's data values are changed to an invalid format, Socialbook will discard that particular person's data while keeping the rest. However, if your changes to the data file makes the file format invalid, SocialBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the SocialBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous SocialBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [t/TAG]…​ [d/DATE_OF_LAST_VISIT] [ec/EMERGENCY_CONTACT]` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague d/07-23-2024`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​ [d/DATE_OF_LAST_VISIT]`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find [n/NAMEKEYWORD] [p/PHONEKEYWORD] [a/ADDRESSKEYWORD]`<br> e.g., `find n/James Jake a/clementi street_woodlands`
**List**   | `list`
**Help**   | `help`
**Seed**   | `seed`
**Sort**   | `sort parameter/order` <br> e.g., `sort n/ascending`
**Remark** | `remark INDEX r/REMARK`
