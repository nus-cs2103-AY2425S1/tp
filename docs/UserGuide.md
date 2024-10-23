---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# CFG User Guide

ContactsForGood (CFG) is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, CFG can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

2. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

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

* Extraneous parameters for commands that do not take in parameters (such as `help`, `exit` and `clear`) will be ignored.<br>
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

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Sorting the displayed list: `sort`

Sorts the currently visible list of persons by a specified field. 

Format: `sort [s/SORT_OPTION]`

* You can use `sort` to arrange the contacts in the list you're currently viewing, whether it's the full list or a subset from commands like `search`.
* If no sort option is provided, `sort` will restore the list to its original order (i.e. the order in which contacts were added).
* The sort order remains active until you apply a new sort option.

<box type="tip" seamless>

**Tip:** Supported sort options include:
* `name`: 
  * Sorts contacts alphabetically by name.
* `hours`: 
  * Sorts volunteers by the number of hours they've contributed in ascending order. 
  * Non-volunteers are placed at the back.
</box>

Examples:
* `sort`
* `sort s/name`
* `sort s/hours`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Searching persons by field: `search`

Search persons whose fields match the keywords given.

Format: `search PREFIX/ KEYWORD [MORE_PREFIX/ KEYWORD ...]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* Search is restricted to the fields indicated by the provided prefixes.
* For name searches, the order of keywords does not matter (e.g., Hans Bo will match Bo Hans).
* The order of the prefixes provided does not affect search results. e.g. `search n/ alex t/ friends` will return the same result as `search t/ friends n/ alex`
* Tag inputs must be alphanumeric
* Only full words will be matched e.g. `friend` will not match `friends`
* For searches with multiple prefixes, only persons matching all keywords corresponding to the prefixes will be returned

Examples: <br>
1. **Person A:** `name`: John Doe `tag`: colleague `group`: blood drive <br>
2. **Person B:** `name`: Alex Yeoh `tag`: friends <br>
3. **Person C:** `name`: David Li `tag`: friends `group`: blood drive
* `search n/ john` returns persons with the name `john` like `John Doe`
* `search t/ friends` returns all persons tagged as friends like `Alex Yeoh` and `David Li`
* `search n/ david t/ friends g/ blood drive` returns all persons with name matching `david`, tag matching `friends` and in group `blood drive` like `David Li`. <br>
    ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `search n/Betsy` followed by `delete 1` deletes the 1st person in the results of the `search` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Creating a group : `createGroup`

Creates a new group with people as members.

Format: `createGroup g/GROUP_NAME m/[INDICES]`

* Creates a new group with name `GROUP_NAME`.
* Adds the persons at the specified `INDICES` to the group. 
  Indices refer to the index numbers shown in the displayed person list.
  There must be at least one index provided, and indices should be separated by a space.
* There cannot be two groups with the same name. If a group
  with the given `GROUP_NAME` currently exists, the command will fail.

### Deleting a group: `deleteGroup`

Deletes a group.

Format: `deleteGroup g/GROUP_NAME`

* Deletes group named `GROUP_NAME`
* Group named `GROUP_NAME` must exist.

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

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Search**   | `search PREFIX/ KEYWORD [MORE_PREFIX/ KEYWORD ...]`<br> e.g., `search n/ john`
**List**   | `list`
**Help**   | `help`
**Create Group** | `createGroup g/GROUP_NAME m/[INDICES]`
**View Group**   | `viewGroup g/GROUP_NAME`
**Delete Group** | `deleteGroup g/GROUP_NAME`
