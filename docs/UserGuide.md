---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# WedLinker User Guide

WedLinker is a **desktop app for managing contacts, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

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

# Features
## General Features

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

//Reminder to update the link of help
![help message](images/helpMessage.png)

Format: `help`

### Listing all Persons : `list`

Shows a list of all saved [Persons](#adding-a-person-add) in the WedLinker.

Format: `list`

### Listing all Weddings : `list-weddings`

Shows a list of all [Weddings](#adding-a-wedding--create-wedding) in the WedLinker.

### Listing all Tasks : `list-tasks`

Shows a list of all [Tasks](#creating-a-task--create-task) in the WedLinker

### Locating contacts by any field, similar to a search function: `find`

Finds all persons based on the specified keywords (case-insensitive) after the prefix representing the field, and displays them as a list with index numbers.

Format: `find PREFIX KEYWORD [KEYWORD]…​`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The search will return partial matches and full matches
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`

Examples of Prefixes:
* n/ Name
* a/ Address
* p/ Phone Number
* e/ Email
* t/ Tag
* w/ Wedding
* tk/ Task

Examples:
* `find n/John` returns `john` and `John Doe`
* `find n/alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)
* `find t/Friends` returns all Contacts tagged with Friends
* `find w/Amy's Wedding` returns all Contacts involved with Amy's Wedding

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

## Person Features

### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​ [w/WEDDING]…​`

<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0)
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS]`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower` Edits the name of the 2nd person to be `Betsy Crower`

### Deleting a person : `delete`

Deletes the specified person from WedLiker.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Tag Features

### Adding a tag : `create-tag`

Creates a `Tag` within WedLinker to be used on contacts.

Format: `create-tag t/TAGNAME`

* The `TAGNAME` is alphanumeric and can contain whitespaces.
* Tags are unique in WedLinker, there would not be any duplicated Tags.
* Contacts can share Tags.

### Assign tag to contact : `tag`

Assigns a `Tag` to the specified person in WedLinker

Format: `tag INDEX t/TAGNAME [f/]`

* Tag a specified contact based on the `INDEX` with a `Tag`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​.
* The `Tag` must exists in WedLinker before it can be assigned.
* If the `Tag` does not exist, you can use `f/` to force the creation and assignment of the `Tag`.

### Unassign tag to contacts : `untag`

Untags a `Tag` from a specified person in WedLinker

Format: `untag INDEX t/TAGNAME`

* Untag a specified contact based on their `INDEX` with a `Tag`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​.

### Deleting a tag : `delete-tag`

Deletes a `Tag` from WedLinker.

Format: `delete-tag t/TAGNAME [f/]`

* Deletes a `Tag` from WedLinker.
* The `Tag` must exists in WedLinker.
* The `Tag` cannot be assigned to any contacts.
* If the `Tag` is in used, you can use `f/` to force the deletion of the `Tag` and unassign this tag from all contacts.

### Wedding Features

### Adding a Wedding : `create-wedding`

Creates a `Wedding` within WedLinker to be with contacts.

Format: `create-wedding w/WEDDINGNAME`

* The `WEDDINGNAME` is alphanumeric and can contain whitespaces.
* Weddings are unique in WedLinker, there would not be any duplicated Weddings.
* Contacts can be assigned to the Wedding using the [assign-wedding](#assign-contact-to-a-wedding--assign-wedding) command.

### Assign contact to a Wedding : `assign-wedding`

Assigns a contact to a `Wedding`.

Format: `assign-wedding INDEX w/WEDDINGNAME`

* Assigns a specified contact to the `Wedding` based on their `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​.
* The `Wedding` must exists in WedLinker before it can be assigned.
* If the `Wedding` does not exist, you can use `f/` to force the creation and assignment of the `Wedding`.

### Edit Wedding details : `edit-wedding`

Edits the details of a `Wedding`.

Format: `edit-wedding INDEX [w/WEDDINGNAME] [a/ADDRESS]`

* Edits the specific `Wedding` at the INDEX when in [list-wedding](#listing-all-weddings-list-weddings) view.
* The index **must be a positive integer** 1, 2, 3, …​.
* Existing values in the specified fields will be overwritten with the specified values.

### Unassign contacts from a Wedding : `unassign-wedding`

Unassigns a contact from a `Wedding` in WedLinker.

Format: `unassign-wedding INDEX w/WEDDINGNAME`

* Unassign a contact that is assigned in a `Wedding`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​.

### Deleting a Wedding : `delete-wedding`

Deletes a `Wedding` from WedLinker.

Format: `delete-wedding w/WEDDINGNAME [f/]`

* Deletes a `Wedding ` from WedLinker.
* The no contacts should be assigned to the `Wedding` before it is deleted.
* If there are still contacts assigned, you can use `f/` to force the deletion of the `Wedding` and unassign all contacts.

### Task Features

### Creating a Task : `create-task`

Creates a `Task` in WedLinker

Format: `create-task tk/TASKTYPE TASKDESCRIPTION [REMARKS]`

### Delete a Task : `delete-task`

Deletes a `Task` from WedLinker

Format: `delete-task INDEX`


### Assigning a Task to a contact : `assign-task` **(WIP)**

### Unassigning a Task from a contact : `unassign-task` **(WIP)**

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
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**   | `list`
**Help**   | `help`
