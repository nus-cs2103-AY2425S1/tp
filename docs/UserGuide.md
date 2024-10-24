---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# PlanPerfect User Guide

PlanPerfect is a **desktop app for wedding planers to manage contacts, optimized for use via a  Line Interface** 
(CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, PlanPerfect can get 
your wedding contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-T12-2/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar PlanPerfect.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## General Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g. `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Parameter descriptions containing a `...` indicate that the parameter can take one or more inputs or no inputs at all (only if inside an optional bracket).<br>
  e.g. `[t/TAG1 TAG2 ...]` can be ignored (i.e. 0 tags) or replaced with `t/friend` (i.e. 1 tags) or `t/friend family` (i.e. 2 tags) etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Providing unexpected parameters for commands that do not take in parameters (such as `help`, `list`, `sort`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message with basic usage instructions for PlanPerfect. The link to this user guide can be copied to the clipboard for more advanced support.

Format: `help`

![help message](images/helpMessage.png)

<br><br/>

## Contact-related Features
### Adding a contact: `add`

Adds a contact to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG1 TAG2 ...]`

<box type="tip" seamless>

**Tip:** A contact can only have up to 6 tags (including 0). No two contacts can have the same phone number.
</box>

Examples:
* `add n/Homer Simpson p/98765432 e/homersimpson@example.com a/742 Evergreen Terrace, block 123, #01-01`
* `add n/Marge Simpson t/client wife e/margesimpson@example.com a/742 Evergreen Terrace p/1234567`
* `add n/Foutou Graffer  e/margesimpson@example.com a/123 Commons Studio p/1234567 t/photographer dancer caterer`

<br><br/>

### Listing all contacts : `list`

Format: `list`

Shows a list of all contacts in the address book.

<br><br/>

### Editing a contact : `edit`

Edits an existing contact in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS]`

* Edits the contact at the specified `INDEX`. The index refers to the index number shown next to a contact's name in the displayed contact list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values only for the fields for which a new value is provided.
* **Note**: Tagging & Untagging is done using the `tag` and `untag` commands, not the `edit` command!

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st contact to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower` Edits the name of the 2nd contact to be `Betsy Crower`.

<br><br/>

### Tagging a contact: `tag`

Adds one or more tags to a specific contact in the address book

Format: `tag INDEX t/TAG1 TAG2 ...`

* Tags the contact at the specified `INDEX`. The index refers to the index number shown next to the contact in the contact list. The index **must be a positive integer** 1, 2, 3, ...
* Tags specified in the command must be alphanumeric, and only one word long.
* The user can specify multiple tags in the same command by separating the tags with a space.
* Adding a tag to a contact who already has the tag will show an error message.

Examples:
* `tag 1 t/photographer` adds the tag 'photographer' to the contact at index 1
* `tag 2 t/baker florist friend` adds the tags 'baker', 'florist' and 'friend' to the contact at index 2

<br><br/>

### Untagging a contact : `untag`

Removes one or more tags from a specific contact in the address book.

Format: `untag INDEX t/TAG1 TAG2 ...` or `untag INDEX t/all`

* Untags the contact at the specified `INDEX`. The index refers to the index number shown next to the contact in the contact list. The index **must be a positive integer** 1, 2, 3, ...
* If the user only wants to remove specific tags from the contact, at least one tag to remove must be specified.
* The user can remove multiple tags from a contact by separating them with a space.
* The user can alternatively remove all tags associated with a contact by using 'untag INDEX t/all'.

Examples:
* `untag 1 t/friends buddies` if the contact at index 1 has the tags: 'friends', 'buddies'.
* `untag 2 t/all` to remove all tags from the contact at index 2.

<br><br/>

### Locating contacts by name: `find`

Finds contacts whose names contain any of the given keywords.

Format: `find KEYWORD1 KEYWORD2 ...`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Contacts matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

<br><br/>

### Filtering contacts by tag: `filter`

Filters contacts who are tagged with all of the given tags.

Format: `filter t/TAG1 TAG2 ...`


* Contacts matching all tags will be returned (i.e. `AND` search).

Examples:
* `filter t/foodCaterer venue` returns all contacts tagged with both `foodCaterer` and `venue`
* `filter t/foodCaterer` returns all contacts tagged with the tag `foodCaterer`<br>

<br><br/>

### Deleting a contact : `delete`

Deletes the specified contact from the address book.

Format: `delete INDEX`

* Deletes the contact at the specified `INDEX`.
* The index refers to the index number shown in the displayed contact list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd contact in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st contact in the results of the `find` command.

<br><br/>

### Sort all entries: `sort`

Format: `sort`

Sorts the contacts in the current view in alphabetical order.


<br><br/>
### Clearing all entries : `clear`

Format: `clear`

Confirms with the user whether they actually want to clear the address book, then clears all entries from the address 
book only if the follow-up input is `yes` or `y` (case-insensitive). Any other input will result in the address book not 
being cleared.

<br><br/>

## Wedding-related Features
### Add wedding: `addw`

Format: `addw n/WEDDING_NAME d/DATE (in DD/MM/YYYY format) [c/CONTACT1_INDEX CONTACT2_INDEX ...]`

Adds a wedding to PlanPerfect with the specified date. Optionally allows users to pre-assign contacts to the wedding.

* Running this command will create a new wedding in the wedding panel, allowing you to use its wedding index to execute relevant commands on that wedding.
* Contact indexes must be valid in the context of the current view
  * IMPORTANT: If you want to pre-assign contacts, be sure to use `list` to view all contacts BEFORE adding a new wedding. If you are in a wedding view, and you do not use the `list` command to exit from wedding view to the all contacts view, you will only be able to add contacts from the current wedding being viewed into the new wedding.

Examples:
* `addw n/Arif and Sonali Wedding d/30/04/2025`
* `addw n/Daniel and Jane Wedding d/23/09/2025 c/1 3 4`

<br><br/>

### View wedding : `view`

Format: `view WEDDING_INDEX`

* Displays contacts involved in the wedding at the specified `WEDDING_INDEX`.
* The index refers to the index number shown in the displayed wedding list on the left of the screen.
* The index **must be a positive integer** 1, 2, 3, ...

Examples:
* `view 2` displays a list of all contacts involved in the second wedding shown on the displayed wedding list.
<br><br/>

### Edit wedding: `editw`

Format: `editw WEDDING_INDEX [n/WEDDING_NAME] [d/WEDDING_DATE]`

* Edits the name and/or date in the wedding at the specified `WEDDING_INDEX`.
* The index refers to the index number shown in the displayed wedding list on the left of the screen.
* The index **must be a positive integer** 1, 2, 3, ...

<box type="warning" seamless>

**Caution:**
The edited wedding name provided must not be the name of a pre-existing wedding in the addressbook

</box>

Examples:
* `editw 1 d/12/11/2025` edits the date of the first wedding shown on the displayed wedding list.

<br><br/>

### Assign person to wedding : `assign`

<br><br/>

### Unassign person from wedding : `unassign`

<br><br/>

### Delete wedding: `deletew`

Format: `deletew WEDDING_INDEX`

* Deletes the wedding at the specified `WEDDING_INDEX`.
* The index refers to the index number shown in the displayed wedding list on the left of the screen.
* The index **must be a positive integer** 1, 2, 3, ...

Examples:
* `deletew 4` deletes the fourth wedding shown on the displayed wedding list.

<br><br/>
## Other Features

### Listing all active tags : `taglist`

Format: `taglist`

* Lists all the tags in active use within PlanPerfect.

<br><br/>

### Exiting the program : `exit`

Exits the program.

Format: `exit`

<br><br/>

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

<br><br/>

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, PlanPerfect will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause PlanPerfect to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.

</box>

<br><br/>

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
**Help**   | `help`
**Add Contact**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG1 TAG2 ...]` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend colleague`
**List All Contacts**   | `list`
**Edit Contact**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Tag Contact**    | `tag INDEX t/TAG1 [TAG2] ...` <br> e.g., `tag 1 t/photographer`
**Untag Contact**  | `untag INDEX t/TAG1 [TAG2] ...` or `untag INDEX t/all` <br> e.g., `untag 1 t/friends buddies`
**Find Contacts (by Keyword)**   | `find KEYWORD1 KEYWORD2 ...`<br> e.g., `find James Jake`
**Filter Contacts (by Tag)** | `filter INDEX t/TAG1 [TAG2] ...` <br> e.g., `filter 2 t/friends colleagues`
**Delete Contact** | `delete INDEX`<br> e.g., `delete 3`
**Sort Contacts**   | `sort`
**Clear All Contacts**  | `clear`
**Add Wedding** | `addw n/WEDDING_NAME d/DATE (in DD/MM/YYYY format) [c/CONTACT1_INDEX CONTACT2_INDEX ...]`<br> e.g., `addw n/Daniel and Jane Wedding d/23/09/2025 c/1 3 4`
**View Wedding** | `view WEDDING_INDEX`<br> e.g., `view 3`
**Edit Wedding** | `editw WEDDING_INDEX [n/WEDDING_NAME] [d/WEDDING_DATE]`<br> e.g., `editw 1 d/12/11/2025`
**Assign Contact to Wedding** | `assign WEDDING_INDEX c/CONTACT1_INDEX CONTACT2_INDEX ...`<br> e.g., `assign 2 c/1 2 3`
**Unassign Contact from Wedding** | `unassign WEDDING_INDEX c/CONTACT1_INDEX CONTACT2_INDEX ...`<br> e.g., `unassign 4 c/3 5`
**Delete Wedding** | `deletew WEDDING_INDEX`<br> e.g., `deleteW 3`
**Get List of (Active) Tags** | `taglist`
**Exit**   | `exit`




