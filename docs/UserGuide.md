---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# TrueRental User Guide

TrueRental is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, TrueRental can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-T15-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your TrueRental application.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar truerental.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](https://github.com/user-attachments/assets/a0260908-89a7-45ae-9e0c-e27a61c86d36)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all clients.

   * `cadd n/Steven Tan e/steventan@abc.com p/98765432` : Adds a client named `Steven Tan` to the TrueRental application.

   * `cdelete 3` : Deletes the 3rd client shown in the current list.

   * `clear` : Deletes all clients.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `cadd n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items without brackets are mandatory, you must provide a value.<br>
  e.g. `n/NAME` for `cadd`, `c/CLIENT_INDEX` and `a/ADDRESS` for `radd`.

* Items in curly brackets "{}" are optional, you may choose not to have them in your command.<br>
  e.g `radd` can be used as `radd c/1 a/BLK 123 Bishan` or as `radd c/1 a/BLK 123 Bishan m/3500`.

* Items in square brackets "[]" are mandatory to have at least one of them, you should not use none of them in a command.<br>
  e.g `cedit` can be used as `cedit 1 n/Steven Tan e/steventan@abc.com p/98765432` or as `cedit 1 n/Steven Tan p/98765432`.

* Items with `...`​ after them can be used multiple times.<br>
  e.g. `k/KEYWORDS` for `find` can be used as `find k/Steven Tan k/98765432` (2 times), `find k/98765432` (1 time), etc.

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


### Adding a client: `cadd`

Adds a client to the address book.

Format: `add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [t/TAG]…​`
Note: To add a client, the client must have at least one **phone number** or **email address**.

<box type="tip" seamless>
**Tip:** A client can have any number of tags (including 0)
</box>

Examples:
* `cadd n/John Doe p/98765432 e/johnd@example.com`
* `cadd n/Betsy Crowe e/betsycrowe@example.com t/criminal t/friend `

### Adding a rental information for a client: `radd`

Adds a rental information for the specific client to the address book.

Format: `radd CLIENT_INDEX a/ADDRESS [s/RENTAL_START_DATE] [e/RENTAL_END_DATE] [dd/RENT_DUE_DATE] [m/MONTHLY_RENT] [d/DEPOSIT] [cl/CUSTOMER_LIST]`

Examples:
* `radd 1 a/65 Anderson Road m/3000 d/0`
* `radd 1 a/65 Berkeley Road e/2024-10-31`
* `radd 3 a/65 Den Road s/2024-09-01 e/2025-08-30 cl/Steven Lim dd/20 m/2750`

### Listing all clients : `list`

Shows a list of all clients in the address book.

Format: `list`


### Listing rental information of a client : `rview`

Shows a list of all rental information related to a specific client in the address book.

Format: `rview CLIENT_INDEX`

* Lists all rental information related to the client at the specified `CLIENT_INDEX`.
* The index refers to the index number shown in the displayed client list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `rview 1` <br>

### Editing a client : `cedit`

Edits an existing client in the address book.

Format: `cedit CLIENT_INDEX [n/NAME] [p/PHONE] [e/EMAIL] [t/TAG]…​`

Note: 
* To edit a client's information, it must have at least **one** of the optional fields.
* Edits the client at the specified `CLIENT_INDEX`. The index refers to the index number shown in the displayed client list. The index **must be a positive integer** 1, 2, 3, …​
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the client will be removed i.e adding of tags is not cumulative.
* You can remove all the client’s tags by typing `t/` without
    specifying any tags after it.
* You will not be able to remove the name of the client.
* You will not be able to remove both the phone and email of the client.

Examples:
*  `cedit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st client to be `91234567` and `johndoe@example.com` respectively.
*  `cedit 2 n/Betsy Crower t/` Edits the name of the 2nd client to be `Betsy Crower` and clears all existing tags.

### Editing a client's rental information: `redit`

Edits a specific client's rental information in the address book.

Format: `redit c/CLIENT_INDEX r/RENTAL_INDEX [a/ADDRESS] [s/RENTAL_START_DATE] [e/RENTAL_END_DATE] [dd/RENT_DUE_DATE] [m/MONTHLY_RENT] [d/DEPOSIT] [cl/CUSTOMER_LIST]`

Note: 
* To edit a client's rental information, it must have at least **one** of the optional fields.
* Edits the client's rental information at the specified `CLIENT_INDEX` and `RENTAL_INDEX`. The `CLIENT_INDEX` refers to the client index number shown in the displayed client list and the `RENTAL_INDEX` refers to the client's rental index number shown in the displayed rental information list. All indexes **must be a positive integer** 1, 2, 3, …​
* Existing values will be updated to the input values.

Examples:
*  `redit c/1 r/1 a/65 Anderson Road m/3000 d/0` Edits the first client, first rental information, address, monthly rent, and deposit amount to `Anderson Road` and `3000` respectively.
*  `redit c/1 r/2 e/2024-10-31` Edits the first client, second rental information, rental end date to `2024-10-31`.

### Locating clients by name: `find`

Finds clients whose names contain any of the given keywords.

Format: `find [k/KEYWORD]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a client : `cdelete`

Deletes the specified client from the address book.

Format: `cdelete CLIENT_INDEX`

* Deletes the client at the specified `CLIENT_INDEX` and all related rental information.
* The index refers to the index number shown in the displayed client list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `cdelete 2` deletes the 2nd client in the address book.
* `find Betsy` followed by `cdelete 1` deletes the 1st client in the results of the `find` command.

### Deleting a rental information : `rdelete`

Deletes the specified rental information from the specified client

Format: `rdelete c/CLIENT_INDEX r/RENTAL_INDEX`

* Deletes the rental information at the specified `RENTAL_INDEX` from the client at the specified `CLIENT_INDEX`.
* `CLIENT_INDEX` refers to the index number shown in the displayed client list.
* `RENTAL_INDEX` refers to the index number shown in the displayed rental information list when the `rview` command is run. (i.e. `rview CLIENT_INDEX`)
* `CLIENT_INDEX` and `RENTAL_INDEX` **must be positive integers** 1, 2, 3, …​

Examples:
* `list` followed by `rdelete c/2 r/1` deletes the 1st rental information from the 2nd client in the address book.
* `find Betsy` followed by `rdelete c/1 r/2` deletes the 2nd rental information from the 1st client in the results of the `find` command.

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

| Action                                      | Format, Examples                                                                                                                                                                                                                    |
|---------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **List all clients**                        | `list`                                                                                                                                                                                                                              |
| **Show manual (help)**                      | `help`                                                                                                                                                                                                                              |
| **Clear**                                   | `clear`                                                                                                                                                                                                                             |
| **Add a client**                            | `cadd n/NAME [p/PHONE_NUMBER] [e/EMAIL]` <br> e.g., `cadd n/Steven Tan e/steventan@abc.com p/98765432`                                                                                                                              |
| **Add rental information for a client**     | `radd c/CLIENT_INDEX a/ADDRESS {s/RENTAL_START_DATE} {e/RENTAL_END_DATE} {dd/RENT_DUE_DATE} {m/MONTHLY_RENT} {d/DEPOSIT} {c/CUSTOMER_LIST}` <br> e.g., `radd c/3 a/65 Den Road s/2024-09-01 e/2025-08-30 c/Steven Lim dd/20 m/2750` |
| **Find information**                        | `find k/KEYWORDS...`<br> e.g., `find k/Steven Tan k/98765432`                                                                                                                                                                       |
| **View rental information of a client**     | `rview CLIENT_INDEX` <br> e.g., `rview 1`                                                                                                                                                                                           |
| **Edit a client**                           | `cedit CLIENT_INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL]` <br> e.g.,`cedit 1 n/Steven Tan e/steventan@abc.com p/98765432`                                                                                                            |
| **Edit rental information for a client**    | `redit c/CLIENT_INDEX r/RENTAL_INDEX [a/ADDRESS] [s/RENTAL_START_DATE] [e/RENTAL_END_DATE] [dd/RENT_DUE_DATE] [m/MONTHLY_RENT] [d/DEPOSIT] [c/CUSTOMER_LIST]` <br> e.g.,`redit c/1 r/1 a/65 Anderson Road m/3000 d/0`               |
| **Delete a client**                         | `cdelete CLIENT_INDEX` <br> e.g., `cdelete 3`                                                                                                                                                                                       |
| **Delete rental information from a client** | `rdelete c/CLIENT_INDEX r/RENTAL_INDEX` <br> e.g., `rdelete c/1 r/2`                                                                                                                                                                |
