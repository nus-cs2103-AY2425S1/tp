---
layout: page
title: User Guide
---

CareConnect is a **CLI-first** **case management application** that enables social workers to efficiently manage client details, appointments, and priorities. Repeated chores including data entry and search will be streamlined via simple CLI inputs, easing the mental load of the social workers, allowing them to focus more on delivering high-quality care and support for the clients.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-W13-2/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your CareConnect application.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar careconnect.jar`
   command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box.

   - Note that the command entered will be coloured red until it a valid command is entered.<br>
       ![incomplete command](images/incompleteCommand.png)
   - Once the completed, valid command is entered, the command will return to colour black.<br>
       ![complete command](images/completedCommand.png)
   - Press Enter to execute command. e.g. typing **`help`** and pressing Enter
     will open the help window.<br>

4. Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Case Management System.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

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
</div>

### Viewing help : `help`

Opens up your default browser and displays the CareConnect user guide webpage.

Format: `help`


### Adding a client: `add`

Adds a client to the case management system.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A client can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all beneficiaries : `list`

Shows a list of all beneficiaries in the case management system.

Format: `list`

### Viewing a client's details: `view`

View the details of the specified client in the case management system.

Format: `view INDEX`

* Opens up record of the client at the specified `INDEX`.
* The index refers to the index number shown in the displayed client list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `view 2` opens up the record of the 2nd client in the case management system.
* `find Betsy` followed by `delete 1` opens up the record of the 1st client in the results of the `find` command.

### Editing a client : `edit`

Edits an existing client in the case management system.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the client at the specified `INDEX`. The index refers to the index number shown in the displayed client list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the client will be removed i.e adding of tags is not cumulative.
* You can remove all the client’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st client to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd client to be `Betsy Crower` and clears all existing tags.

### Tagging a client: `tag`

Tags a client in the case management system.

Format: `tag INDEX t/TAG_NAME`

* Adds the tag to the client at the specific `INDEX`.
* The tag must not contain any spaces
* Only one tag can be added at once

Example:
- `tag 1 t/urgent`
- 
### Tagging a client: `untag`

Tags a client in the case management system.

Format: `untag INDEX t/TAG`

* Removes the tag from the client at the specific `INDEX`.
* Only one tag can be removed at once
* If the tag is not found, a warining will be displayed

Example:
- `untag 1 t/urgent`


### Locating beneficiaries by name: `find`

Finds beneficiaries whose names and address contain any of the given keywords.

Format: `find n/KEYWORD [MORE_KEYWORDS] a/KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Partial names will also be matched e.g. `Han` will match `Hans`
* Beneficiaries matching at least one keyword will be returned (i.e. `OR` search).
  - e.g. `find n/Hans Bo` will return `Hans Gruber`, `Bo Yang`
  - e.g. `find n/Hans a/serangoon` will return `Hans Gruber` who has the address `Serangoon street 2, blk 111`


Examples:
* `find n/ John` returns `johnny` and `John Doe`
* `find n/ benson carl` returns `Benson Meier`, `Carl Kurz`<br>
* `find a/ serangoon` will return `Bernice Yu` with address `Blk 30 Lorong Serangoon Gardens, #07-18` and `David Li` with address `Blk 436 Serangoon Gardens 26, #16-43`
  ![result for 'find benson carl'](images/findBensonCarlResult.png)


### Deleting a client : `delete`

Deletes the specified client from the case management system.

Format: `delete INDEX`

* Deletes the client at the specified `INDEX`.
* The index refers to the index number shown in the displayed client list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd client in the case management system.
* `find Betsy` followed by `delete 1` deletes the 1st client in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the case management system.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

CareConnect data are saved in the hard disk automatically after any command that changes the data. There is no need to
save
manually.

### Autocomplete commands

CareConnect provides command autocompletion when pressing the Tab key. For example, typing `f` and pressing Tab will auto complete the command to `find`.

### Editing the data file

CareConnect data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are
welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, CareConnect will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the CareConnect to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous CareConnect home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**View** | `view INDEX` <br> e.g., `delete 3`
**Help** | `help`
