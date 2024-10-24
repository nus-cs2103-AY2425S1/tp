---
layout: page
title: User Guide
---

Clientele+ seamlessly combines client contacts, payment tracking and more in one efficient package, tailored specifically for freelance software developers.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `clientele+.jar` file from [here](https://github.com/AY2425S1-CS2103T-F14A-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for Clientele+.

1.  Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar clientele+.jar` command to run the application. <br>
    A GUI similar to the below should appear in a few seconds
   ![Ui](images/Ui.png)

1. Type the command in the command box and press `Enter` to execute it. e.g. typing **`help`** and pressing `Enter` will open the help window.<br>
   Some example commands you can try:

    * `list` : Lists all clients' contacts.

    * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a client contact named `John Doe` to Clientele+.

    * `delete id/3` : Deletes the 3rd client contact shown in the current list.

    * `clear` : Deletes all client contacts.

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

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Add Client Details: `add`

Allows the user to add a new client with details about payment status, client status, and project status.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​
[ps/PROJECT_STATUS] [py/PAYMENT_STATUS] [cs/CLIENT_STATUS]`

* Clients with the **same** `NAME`, `EMAIL` and `PHONE NUMBER` are considered duplicates and will not be added
* A person can have any number of tags (including 0)
* `NAME` must be **alphanumeric**, may contain **spaces** and **dashes**, and should not be blank.
* `NAME` is case-insensitive. `John Doe` and `joHN dOE` are considered same clients, but name is stored in the same case as the input (so `John Doe` is stored as `John Doe` and `JOHN Doe` is stored as `JOHN Doe`
* `NAME` must not exist in Clientele+ already.
* `PHONE_NUMBER` should be **Numeric** digits,may include “-” or spaces. Example: `555-1234` or `555 1234`.
* `EMAIL`  Standard email format “user@example.com”
* `PAYMENT STATUS` Acceptable values are `paid`, `unpaid`, `p`, `u`, `0` for **paid**, `1` for **unpaid**. Case insensitive.
* `CLIENT STATUS`  Acceptable values are `active`, `unresponsive`, `potential`, `old`. Case sensitive.
* `PROJECT STATUS` Acceptable values are `in progress`, `completed`. Case insensitive.

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01
t/friends ps/in progress py/unpaid cs/active`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### View Client List : `list`

Displays a list of all clients and their details.

Format: `list`

### Update client details : `edit`

Allows updating of various statuses of an existing client.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​
[ps/PROJECT_STATUS] [py/PAYMENT_STATUS] [cs/CLIENT_STATUS]`

* `NAME` Acceptable values are same as in add command
* `PHONE_NUMBER` Acceptable values are same as in add command
* `EMAIL` Acceptable values are same as in add command
* `PAYMENT STATUS` Acceptable values are same as in add command
* `CLIENT STATUS` Acceptable values are same as in add command
* `PROJECT STATUS` Acceptable values are same as in add command
* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Updates the first person’s phone number to `91234567` and email address to `johndoe@example.com`
*  `edit 2 n/Betsy Crower t/` Updates the name of the 2nd person to be `Betsy Crower` and clears all existing tags.
*  `edit 1 ps/completed py/paid cs/old` Updates the project status, payment status and client status of the 1st person to be `completed`, `paid` and `old` respectively.

### Locating clients by name: `find`

Finds persons in address book who match parameters specified. Values matched are case insensitive.

Format: `find [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]… [ps/PROJECT_STATUS] [py/PAYMENT_STATUS] [cs/CLIENT_STATUS] [d/deadline]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
* Names only need to match the start of a word. e.g. `find n/Han` OR `find n/B` 
    matches `Hans Bo`
* Phone number, email, address, project status, payment status, client status must match the exact string
  e.g. `cs/in progress` will not match `cs/in prog`
* Only 1 tag needs to be matched for person to be found

Examples:
* `find n/John` returns `john` and `John Doe`
* `find n/John ps/completed` returns all names with John as a starting word in a name
  if they have a completed project status
* `find n/alex david` returns `Alex Yeoh`, `David Li`<br>

  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Delete Client Details : `delete`

Deletes the specified person from Clientele+.

Format: `delete [n/NAME] [id/ID]`

* Deletes clients identified by the specified `ID` or `NAME`.
* `NAME` Acceptable values are same as in add command
* `ID` refers to the index number shown in the displayed person list.
* `ID` **must be a positive integer** 1, 2, 3, …​

Examples:
* `delete n/John` deletes clients with the name `John`.
* `list` followed by `delete id/2` deletes the 2nd person in the list.
* `find Betsy` followed by `delete id/1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from Clientele+.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

Clientele+ data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

Clientele+ data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, Clientele+ will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the Clientele+ to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
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

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​ [ps/PROJECT_STATUS] [py/PAYMENT_STATUS] [cs/CLIENT_STATUS]` <br> e.g., `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 t/friends ps/in progress py/unpaid cs/active`
**Clear** | `clear`
**Delete** | `delete [n/NAME] [id/ID]`<br> e.g., `delete n/John Doe` or `delete id/4`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​ [ps/PROJECT_STATUS] [py/PAYMENT_STATUS] [cs/CLIENT_STATUS]`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]… [ps/PROJECT_STATUS] [py/PAYMENT_STATUS] [cs/CLIENT_STATUS] [d/deadline]`<br> e.g., `find n/James Jake ps/completed py/paid`
**List** | `list`
**Help** | `help`
