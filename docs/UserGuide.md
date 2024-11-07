<!--
---
layout: default.md
title: "User Guide"
pageNav: 3
---
-->

# HallPointer User Guide

HallPointer is a **desktop app for managing contacts, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, it can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://ay2425s1-cs2103t-w14-3.github.io/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your .

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar hallpointer.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list_members` : Lists all contacts.

   * `add_member n/John Doe r/4-3-301 t/johndoe123 tag/logistics` : Adds a contact named `John Doe` to Hall Pointer.

   * `delete_member 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features
<box type="info" seamless>

**Notes about the command format:**<br>
* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add_member n/NAME`, `NAME` is a parameter which can be used as `add_member n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME tg/TELEGRAM`, `tg/TELEGRAM n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

</box>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a member: `add_member`

Adds a member to Hall Pointer.

Format: `add_member n/NAME r/BLOCK/FLOOR/ROOM_NUMBER t/TELEGRAM_HANDLE [tag/TAG]…​​`

<box type="tip" seamless>

**Tip:** A member can have any number of tags (including 0)

</box>

Examples:
* `add_member n/John Doe r/4-3-301 t/johndoe123`
* `add_member n/Betsy Crowe r/2-5-120 t/betsy_crowe tag/logistics`

### Listing all members : ` list`

Shows a list of all members in Hall Pointer.

Format: `list`

### Updating a member : `update_member`

Updates an existing member in Hall Pointer.

Format: `update_member INDEX [n/NAME] [r/BLOCK-FLOOR-ROOM_NUMBER] [t/TELEGRAM_HANDLE] [tag/TAG]…​​`

* Updates the member at the specified `INDEX`. The index refers to the index number shown in the displayed member list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When updating tags, the existing tags of the member will be removed i.e adding of tags is not cumulative.
* You can remove all the member’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `update_member 1 t/johndoe123_updated n/Johnson Doe` Updates the telegram and name of the 1st member to be `johndoe123_updated` and `Johnson Doe` respectively.
*  `update_member 2 n/Betsy Crower tag/` Updates the name of the 2nd member to be `Betsy Crower` and clears all existing tags.

### Locating members by name: `find_members`

Finds members whose names contain any of the given keywords.

Format: `find_members KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Members matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find_members John` returns `john` and `John Doe`
* `find_members alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find_members alex david'](images/findAlexDavidResult.png)

### Deleting a member : `delete_member`

Deletes the specified member from Hall Pointer.

Format: `delete_member INDEX`

* Deletes the member at the specified `INDEX`.
* The index refers to the index number shown in the displayed member list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list_members` followed by `delete_member 2` deletes the 2nd member in Hall Pointer.
* `find_members Betsy` followed by `delete_member 1` deletes the 1st member in the results of the `find` command.

### Adding a Session: `add_session`

Adds a session to Hall Pointer and associates it with specified members.

Format: `add_session s/NAME d/DATE p/POINTS m/INDEX...`

* Adds a session with the specified `NAME`, `DATE`, and `POINTS`.
* **Points** should be between 0 and 10,000. A value too large may lead to integer overflow, resulting in unexpected negative values.
* The index (`INDEX`) refers to the index number shown in the displayed member list and **must be a positive integer** (1, 2, 3, …).
* If one or more `INDEX` values are provided, the session will be associated with those members.

Examples:
* `list_members` followed by `add_session s/Rehearsal d/24 Oct 2024 p/2 m/1 m/3` Adds a session named "Rehearsal" on 24 Oct 2024 worth 2 points, associated with the members at indexes 1 and 3 in the displayed list.

### Deleting a Session: `delete_session`

Deletes a session associated with one or more members in Hall Pointer.

Format: `delete_session s/NAME m/INDEX...`

* Deletes the session with the specified `NAME`.
* The index (`INDEX`) refers to the index number shown in the displayed member list and **must be a positive integer** (1, 2, 3, …).
* The session will be deleted only for those members whose `INDEX` values are provided.

#### Examples:
* `list_members` followed by `delete_session s/Rehearsal m/1 m/3` Deletes the session named "Rehearsal" for the members at indexes 1 and 3 in the displayed list.

### Clearing all entries : `clear`

Clears all entries from Hall Pointer.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

 data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

HallPointer data are saved automatically as a JSON file `[JAR file location]/data/hallpointer.json`. Advanced users are welcome to edit data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, HallPointer will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the Hall Pointer system to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.

</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous HallPointer home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add_member**    | `add_member n/NAME r/BLOCK-FLOOR-ROOM_NUMBER t/TELEGRAM_HANDLE [tag/TAG]…​​` <br> e.g., `add_member n/James Ho r/4-3-301 t/jamesho123 tag/friend tag/colleague`
**Clear**  | `clear`
**Delete_member** | `delete_member INDEX`<br> e.g., `delete_member 3`
**Update member**   | `update_member INDEX [n/NAME] [r/BLOCK-FLOOR-ROOM_NUMBER] [t/TELEGRAM_HANDLE] [tag/TAG]…​…​`<br> e.g.,`update_member 2 n/James Lee r/5-2-203 t/jameslee99`
**Find**   | `find_members KEYWORD [MORE_KEYWORDS]`<br> e.g., `find_members James Jake`
**List**   | `list_members`
**Help**   | `help`
