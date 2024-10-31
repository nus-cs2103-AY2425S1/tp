---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# LogiLink User Guide

LogiLink allows you to manage your contacts on your desktop with keyboard commands. If you type fast, you can complete your contact management tasks faster with LogiLink than with mouse-based apps.

<!-- * Table of Contents -->
* [Quick start](#quick-start)
* [Features](#features)
  * [Viewing help: `help`](#viewing-help--help)
  * [Adding a contact or delivery: `add`](#adding-a-contact-or-delivery-add)
  * [Listing all contacts: `list`](#listing-all-contacts--list)
  * [Editing a contact or delivery: `edit`](#editing-a-contact-or-delivery-edit)
  * [Locating contacts or deliveries by name: `find`](#locating-contacts-or-deliveries-by-name-find)
  * [Deleting a contact or delivery: `delete`](#deleting-a-contact-or-delivery--delete)
  * [Inspecting a contact: `inspect`](#inspecting-a-contact--inspect)
  * [Clearing all entries: `clear`](#clearing-all-entries--clear)
  * [Exiting the program: `exit`](#exiting-the-program--exit)
* [FAQ](#faq)
* [Known issues](#known-issues)
* [Command summary](#command-summary)

<page-nav-print />

--------------------------------------------------------------------------------------------------------------------
## Quick start

1. Ensure you have Java `17` or above installed in your Computer.
   - If you do not, you can download the Java `17` installer from [here](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-T12-3/tp/releases).

1. Copy the `.jar` file to the folder you want to use as the _home folder_ for LogiLink.

1. Within this _home folder_, open a command terminal (Right-click > Open in Terminal) and enter `java -jar addressbook.jar` to run LogiLink.<br>
   - A window similar to the diagram should appear, and the program should contain some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com r/Client a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the contacts list.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**
* There are two windows in this program:
  - Main window: the default window you see when opening LogiLink.
  - Inspect window: the window you see when inspecting a contact.<br></br>

* Words in `UPPER_CASE` are parameters to be supplied by you.<br>
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
**<ins>When in the main or inspect window**

Shows a message explaning how to access the help page.

Format: `help`

![help message](images/helpMessage.png)

### Adding a contact or delivery: `add`
**<ins>When in the main window**

Adds a contact to the contacts list.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL r/ROLE a/ADDRESS [t/TAG]…​`

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com r/Client a/John street, block 123, #01-01, S123456`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com r/Worker a/Newgate Prison, S123456 p/1234567 t/criminal`

**<ins>When in the inspect window**

Adds a delivery to the delivery list of a contact.

Format: `add i/ITEM…​ e/ETA a/ADDRESS c/COST s/STATUS [t/TAG]…​`

Examples:
* `add i/Chair e/2025-04-04 a/John street, block 123, #01-01, S123456 c/$20 s/delivered`
* `add i/Monitor i/Mouse e/2020-02-02 a/311, Clementi Ave 2, #02-25, S120300 c/$100 s/not delivered t/Difficult address to deliver t/Best before Wednesday`

<box type="tip" seamless>

**Tip:** A contact or delivery can have any number of tags (including 0)
</box>

### Listing all contacts : `list`

**<ins>When in the main or inspect window**

Shows a list of all contacts added to the contacts list. When you enter this command in either window, you will end up in the main window after this command.

Format: `list`

### Editing a contact or delivery: `edit`
**<ins>When in the main window**

Edits an existing contact in the contacts list.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [r/ROLE] [a/ADDRESS] [t/TAG]…​`

* Edits the contact at the specified `INDEX`. The index refers to the index number shown in the displayed contacts list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st contact to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd contact to be `Betsy Crower` and clears all existing tags.

**<ins>When in the inspect window**

Edits an existing delivery in the delivery list of a contact.

Format: `edit INDEX [i/ITEM]…​ [e/ETA] [a/ADDRESS] [c/COST] [s/STATUS] [t/TAG]…​`

* Same parameter constraints as mentioned in the main window section of this command.
* You can not remove all the delivery's items by typing `i/` without specifying any items after it. One item must be present at the least.

Examples:
*  `edit 1 i/Speaker c/$50` Edits the items and cost of the 1st delivery to be `Speaker` and `$50` respectively.
*  `edit 2 s/delivered t/` Edits the status of the 2nd delivery to be `delivered` and clears all existing tags.

### Locating contacts or deliveries by name: `find`
**<ins>When in the main window**

Finds contacts whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`

![result for 'find alex david'](images/findAlexDavidResult.png)

**<ins>When in the inspect window**

Find command does not work in the inspect window.

### Deleting a contact or delivery : `delete`
**<ins>When in the main window**

Deletes the specified contact from the contacts list.

Format: `delete [INDEXES]...` 

* Deletes the contact(s) at the specified `INDEXES`.
* The indexes refer to the indexes shown in the displayed contacts list.
* The indexes **must be positive integers** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2 3` deletes the 2nd and 3rd contact in the contacts list.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

**<ins>When in the inspect window**

Deletes the specified delivery from the delivery list of a contact. Everything else is the same as mentioned in the main window section of this command.

Examples:
* `delete 2` deletes the 2nd delivery in the delivery list of the inspected contact.
* `delete 2 3` deletes the 2nd and 3rd deliveries in the delivery list of the inspected contact.

### Inspecting a contact : `inspect`
**<ins>When in the main window**

Inspects a specified contact from the contacts list to see their delivery list.

Format: `inspect [INDEX]`

* Inspects the contact at the specified `INDEX`.
* The index refers to the index number shown in the displayed contacts list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `inspect 1` inspects the 1st contact in the contacts list.

**<ins>When in the inspect window**

inspect command does not work in the inspect window.

### Clearing all entries : `clear`
**<ins>When in the main or inspect window**

Clears all entries from the contacts list.

Format: `clear`

**<ins>When in the main or inspect window**

clear command does not work in the inspect window.

### Exiting the program : `exit`
**<ins>When in the main or inspect window**

Exits the program.

Format: `exit`

### Saving the data

LogiLink data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

LogiLink data is saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, LogiLink will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the LogiLink to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous LogiLink home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL r/ROLE a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com r/Client a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear**  | `clear`
**Delete** | `delete INDEXES`<br> e.g., `delete 3`, `delete 3 4`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Inspect**| `inspect INDEX`<br> e.g., `inspect 2`
**List**   | `list`
**Help**   | `help`
