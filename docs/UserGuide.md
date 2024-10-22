---
layout: page
title: User Guide
---

ClientHub is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, ClientHub can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-F10-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your ClientHub.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar clienthub.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 d/likes ramen` : Adds a contact named `John Doe` to the Client Hub.

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
  e.g `n/NAME [c/CLIENT_TYPE]` can be used as `n/John Doe c/Plan A` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[c/CLIENT_TYPE]…​` can be used as ` ` (i.e. 0 times), `c/Plan A`, `c/Plan A c/Plan B` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to the Client Hub.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS d/DESCRIPTION [c/CLIENT_TYPE]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A contact can have any number of Client Type (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe c/Plan A c/Plan B e/betsycrowe@example.com a/Newgate Prison p/1234567 d/criminal`

### Listing all persons : `list`

Shows a list of all persons in the Client Hub.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the Client Hub.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [d/DESCRIPTION] [c/CLIENT_TYPE]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing client types, the existing client types of the person will be removed i.e adding of client type is not cumulative.
* You can remove all the contact’s client types by typing `c/` without
    specifying any client typess after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower c/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing client types.

### Locating persons by key information: `find`

Finds persons by name, phone number, address or client type.

Format: `find KEYWORD`

* `find n/KEYWORD` will search for persons by name.
* `find p/KEYWORD` will search for persons by phone number.
* `find a/KEYWORD` will search for persons by address.
* `find c/KEYWORD` will search for persons by client type.


Examples:
* `find n/John` returns `john` and `John Doe`
* `find n/alex yeo` returns `Alex Yeoh`
* `find p/8433` returns `8433 4567`
* `find a/Blk 47` returns `Blk 47 Tampines Street 20`
* `find c/Investment` returns `Investment Plan`

Result for `find alex david`:
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Locating persons by name: `fn`

Finds persons whose names contain any of the given keywords.

Format: `fn KEYWORD`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Prefix of words will be matched e.g. `Ha B` will match `Hans Bo`
* Persons matching all keyword prefix will be returned (i.e. `AND` search).
  e.g. `Hans Bo` will return `Hans Bo` but not `Hans Gruber`, `Bo Yang`

Examples:
* `fn John` returns `john` and `John Doe`
* `fn Ale Yeo` returns `Alex Yeoh`
* `fn Yeoh Alex` returns `Alex Yeoh`

Result for `fn alex david`:
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Locating persons by phone number: `fp`

Finds persons whose phone number begins with the given keyword.

Format: `fp KEYWORD`

* Only numbers are allowed.
* Only the phone number is searched.
* Only numbers that begin with keyword will be matched e.g. `8765432` will not match `98765432`

Examples:
* `fp 9` returns every contact that has phone number beginning with `9`
* `fp 9123` returns every contact that has phone number beginning with `9123`
* `fp 98765432` returns every contact that has phone number `98765432`

Result for `fp 9234`:
  ![result for 'find 9234'](images/find9234Result.png)


### Locating persons by address : `fa`

Finds persons whose address matches any part of the given keyword(s).

Format `fa KEYWORD(s)`

* The search is case-insensitive. e.g `tampines` will match `Tampines`
* Only the address of the contact is searched.
* Persons with address with any matching substring to the keyword will be returned.

Examples:
* `fa Blk` returns `Blk 45` and `Blk 35`
* `fa tampines` returns `Blk 47 Tampines Street 20`

Result for `fa tampines`:
  ![result for 'fa tampines`](images/findTampines.png)

### Locating persons by client type: `fc`

Finds persons whose address matches any part of the given keyword(s).

Format `fc KEYWORD [MORE_KEYWORDS]`
* The search is case-insensitive. e.g `investment` will match `Investment`
* Only the `client_type` of the person is searched.
* Persons whose `client_type` contains a substring that matches the provided `KEYWORD` will be returned.
* Person with `client_type` that has a prefix matching the keyword will be returned (i.e. `AND` search).

 A **valid** `KEYWORD` should:
  * Only be alphanumeric. Special Characters are not valid. (eg. Investment #1 is invalid)
    * `client_type` will always be in alphanumeric format.
  * Not be empty. 
    * For eg. Just typing `fc` without providing any `KEYWORD` will throw an error.

Examples:
* `fc Investment` returns every contact that has a `client_type` beginning with `Investment`
* `fc Invest` returns every contact that has `client_type` beginning with `Invest`
* `fc Investment Healthcare` returns every contact that has `client_type` beginning with `Investment` AND `Healthcare`

Result for `fc Investment Plan`:
  ![result for 'fc Investment Plan`](images/FindClientType.png)

### Deleting a person : `delete`

Deletes the specified person from ClientHub.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in ClientHub.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from ClientHub.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

ClientHub data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

ClientHub data is saved automatically as a JSON file `[JAR file location]/data/clienthub.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, ClientHub will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the ClientHub to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous ClientHub home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action                | Format, Examples                                                                                                                                                                                          |
|-----------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**               | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [c/CLIENT_TYPE]…​ [d/DESCRIPTION]` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 c/Plan A c/Plan A d/crimefighter` | 
| **Clear**             | `clear`                                                                                                                                                                                                   |
| **Delete**            | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                                                       |
| **Edit**              | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [c/CLIENT_TYPE]…​ [d/DESCRIPTION]`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                                       | 
| **Find**              | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`                                                                                                                                                |
| **Find Phone Number** | `fp KEYWORD`                                                                                                                                                                                              |
| **Find Address**      | `fa KEYWORD(s)`                                                                                                                                                                                           |
| **List**              | `list`                                                                                                                                                                                                    |
| **Help**              | `help`                                                                                                                                                                                                    |
