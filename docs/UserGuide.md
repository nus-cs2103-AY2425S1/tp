---
layout: page
title: User Guide
---

PROperty is a **desktop app for property agents managing contacts and their property listings,
optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI).
If you can type fast, PROperty can get your contact and property management tasks done faster than traditional GUI apps.

* Table of Contents
  {:toc}

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

### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [t/TAG] [r/REMARKS]…​`

* NAME and PHONE NUMBER fields must be provided.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:

* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 r/looking for HDB`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Flatbush Avenue, block 81, #02-02 p/1234567 t/Condominium`


### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`


### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG] [dt/TAG] [r/REMARK]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the tags specified using `t/` will be added to the contact (cumulatively).
* Tags can also be removed using the delete tag `dt/` prefix, followed by the tag name.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:

* `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
* `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.


### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`.
* By default, the find command conduct a general search for the individual. Hence, 
the order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`.
* A person's name, phone nunmber, address, email and tag can be searched.
* Only full words will be matched e.g. `Han` will not match `Hans`.
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
* If a more specific search is required, utilise the `s/`.
  * Format: `find s/KEYWORD [s/MORE_KEYWORDS]`
  * Only individuals who match the keyword(s) one-to-one will be returned. e.g. `find s/Hans Bo` will not match `Bo Hans`. `find s/Hans Bo` will only match `Hans Bo`.
  * Especially useful if there are multiple people with the same name in PROperty address book and you require a more specific search.

Examples:

* `find John` returns `John` and `John Doe`.
* `find s/John` returns only `John`.
* `find alex david` returns `Alex Yeoh`, `David Li`.
* `find s/Alex Yeoh s/23 Smith Street` returns `Alex Yeoh` who has `23 Smith Street` as his address.


### Locating persons by tag: `findtag`

Finds persons whose tags contain any of the given words.

Format: `findtag TAG [MORE_TAGS]`

- The search is case-insensitive. e.g., `HDB` will match `hdb`.
- The order of the tags does not matter.
- Persons with at least one matching tag will be returned (i.e., an `OR` search).

Examples:

- `findtag HDB` returns persons tagged with `HDB`.
- `findtag HDB colleague` returns persons tagged with either `HDB` or `colleague`.


### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:

* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.


### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`


### Exiting the program : `exit`

Exits the program.

Format: `exit`


### Quick reference help: `help`

Shows commands in a help menu for quick reference during use of PROperty.

![help message](images/helpMessage.png)

Format: `help`


### Managing Remarks : `remark`

Adds/removes a remark from a person in PROperty.

Format: `remark INDEX r/[REMARKS]`

- Adds a remark `REMARKS` to the person at `INDEX`

- The index refers to the index number shown in the displayed person list.

- The index **must be a positive integer** 1, 2, 3, …​

- **Note:** `[REMARKS]` will **delete the remark if left blank**

Examples:

- `remark 1 r/Prefers a higher floor apartment`
  
  - Adds a remark "Prefers a higher floor apartment" to the person at index `1`

- `remark 1 r/`
  
  - Deletes the remark of person at index `1`


### Showing property listings of a person : `show`

Shows the full details of the specified person, including their property listings.

Format: `show INDEX`

- Shows the person at the specified `INDEX`
- The index refers to the index number shown in the displayed person list.
- The index **must be a positive integer** 1, 2, 3, …​

Examples: 

- `show 2` shows the name, contact information, tags, and property listings of the second person in the address book.


### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.


### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>


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

| Action      | Format, Examples                                                                                                                                                                  |
|-------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**     | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG] [r/REMARKS]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague` |
| **Clear**   | `clear`                                                                                                                                                                           |
| **Delete**  | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                               |
| **Edit**    | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG] [r/REMARKS]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                           |
| **Find**    | `find KEYWORD [MORE_KEYWORDS]`<br/>`find s/KEYWORD [s/MORE_KEYWORDS]`<br> e.g., `find James Jake`, `find s/James Jake s/23 Philip Street`                                         |
| **Findtag** | `findtag TAG [MORE_TAGS]`<br> e.g., `findtag friend colleague`                                                                                                                    |
| **List**    | `list`                                                                                                                                                                            |
| **Show**    | `show INDEX`<br> e.g., `show 1`                                                                                                                                                   |
| **Help**    | `help`                                                                                                                                                                            |
| **Remark**  | `remark INDEX r/[REMARKS]`                                                                                                                                                        |
