---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# AB-3 User Guide

AddressBook Level 3 (AB3) is a **desktop app for managing contacts, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

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

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

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

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0)
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing Items : `list`

Displays the list of vendors and/or events in EventTory.

#### Format: `list [v/] [e/]`

* The list(s) displayed depends on whether the `v/` and/or `e/` prefix(es) is specified.
* If no prefixes are specified, both the vendor and event lists will be displayed.
* The prefixes can be specified in any order.
* If values are specified after the prefixes (e.g. `v/2`, `e/Party`), the value is ignored.

#### Examples
* `list v/` will display the list of vendors.
* `list e/` will display the list of events.
* `list v/ e/` and `list` will display both lists.

### Editing a Vendor or Event : `edit`

Edits an existing vendor or event in EventTory.

#### Format: 
* To edit a vendor: `edit v/INDEX [n/NAME] [p/PHONE] [d/DESCRIPTION] [t/TAG]…​`
* To edit an event: `edit e/INDEX [n/NAME] [on/DATE] [t/TAG]…​`

#### Notes:
* Edits the vendor/event at the specified `INDEX`.
  * The index refers to the index number shown in the vendor or event list.
  * The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
  * Editing an item but providing no new values is invalid.
* The existing values will be updated to the input values.
* When editing tags, the existing tags of the vendor/event will be **overridden**.
  * Tags cannot be added cumulatively.
  * You can remove all tags from a vendor/event by typing `t/` without specifying any tags after it.

#### Examples:
*  `edit v/1 p/58623042 ` : Edits the phone number of the 1st vendor to be `58623042`.
*  `edit e/2 n/Baby Shower t/` : Edits the name of the 2nd event to be `Baby Shower`, and clears all existing tags.

### Assigning Vendors & Events: `assign`

Assigns vendors to events.

#### Format: `assign INDEX`

* Assigns the vendor/event specified at `INDEX` to the current viewed event/vendor.
  * The index refers to the index number shown in the assignable vendor/event list.
  * The index **must be a positive integer** 1, 2, 3, ...
* The command only works when the user is viewing a vendor/event using the `view` command. Otherwise, the operation will fail.
* If the specified vendor-event pair are already associated (assigned to each other), the operation will fail.

#### Examples:
* `view v/2` then `assign 1` will assign the 1st event to the current viewed vendor, which is the 2nd vendor.
* `view e/1` then `assign 3` will assign the 3rd vendor to the current viewed event, which is the 1st event.

### Unassigning Vendors & Events: `unassign`

Unassigns vendors to events.

#### Format: `unassign INDEX`

* Unassigns the vendor/event specified at `INDEX` to the current viewed event/vendor.
  * The index refers to the index number shown in the assigned vendor/event list.
  * The index **must be a positive integer** 1, 2, 3, ...
* The command only works when the user is viewing a vendor/event using the `view` command. Otherwise, the operation will fail.
* If the specified vendor-event pair are not already associated (not assigned to each other), the operation will fail.

#### Examples:
* `view v/2` then `unassign 1` will unassign the 1st event from the current viewed vendor, which is the 2nd vendor.
* `view e/1` then `unassign 3` will unassign the 3rd vendor from the current viewed event, which is the 1st event.

### Searching for Vendors & Events: `find`

Finds vendors or events whose attributes contain any of the space-separated keywords provided.

#### Format: `find v/KEYWORD [MORE_KEYWORDS]` or `find e/KEYWORD [MORE_KEYWORDS]`

#### Notes:
* The search is case-insensitive. e.g. `party` will match `Party`
* Any partial matches will still be matched e.g. `par` will match `party`
* The order of the keywords does not matter. e.g. `party birthday` will match `birthday party`
* All attributes of the `Vendor` or `Event` are searched, i.e. name, phone number, date, descriptions and tags.
* Vendors and Events matching at least one keyword will be returned (i.e. `OR` search).
  * e.g. `party wedding` will return `Birthday Party`, `John's Wedding`
* If no matches are found, the user will be informed and the current view will remain unchanged.

#### Examples:
* `find v/catering` returns `catering` and `Catering Solutions`
* `find e/party wedding` returns `Birthday Party` and `John's Wedding`<br>

### Deleting Items : `delete`

Deletes a vendor or an event from EventTory.

#### Format: `delete [v/INDEX]` or `delete [e/INDEX]`

* Deletes the event or vendor at the specified `INDEX`.
    * The index refers to the index number shown in the displayed event/vendor list respectively.
    * The index **must be a positive integer** 1, 2, 3, ...
    * The index for each vendor/event is relative and can change depending on previous operations.
* The operation will succeed even if the specified vendor/event is not visible onscreen.
  * e.g. `delete v/1` is run after `view v/2`. Even though the 1st vendor will not be visible, it can still be specified for deletion.
* If the specified vendor/event is currently assigned to another event/vendor respectively, the operation will fail.
* If the current viewed vendor/event is deleted, the application will return you to the main list screen.

#### Examples:
* `list` followed by `delete v/2` deletes the 2nd vendor in EventTory.
* `find e/Wedding` followed by `delete e/1` deletes the 1st event shown in the results of the `find` command.

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

| Action     | Format, Examples                                                                                                                                                      |
|------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague` |
| **Clear**  | `clear`                                                                                                                                                               |
| **Delete** | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                   |
| **Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                           |
| **Find**   | `find v/KEYWORD [MORE_KEYWORDS] or e/KEYWORD [MORE_KEYWORDS]` <br> e.g., `find v/Catering Band`, `find e/Party Wedding`                                               |
| **List**   | `list`                                                                                                                                                                |
| **Help**   | `help`                                                                                                                                                                |
