---
layout: page
title: User Guide
---

ClientHub is a desktop app for **Tech-savvy Independent Financial Advisors who have more than 50 clients that are mainly Singaporean** to better manage their clients. Our product provides financial advisors with a streamlined tool to manage client details (eg. Track insurance policies), **optimized for use via a Command Line Interface** (CLI) while still **having the benefits of a Graphical user Interface (GUI)**. This product makes the lives of financial advisors easier by offering easier access to relevant information for their clients improving their efficiency. If you can type fast, ClientHub can get your contact management tasks done faster than traditional GUI apps.


* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-F10-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your ClientHub.

Example:

**For Mac**
1. If you have downloaded the jar file into a folder in your `Downloads`
2. Open Terminal
3. Type `cd Downloads/[FolderName]` and press Enter
4. Type `java -jar clienthub.jar` and press Enter

**For Windows**
1. If you have downloaded the jar file into a folder in your `Downloads`
2. Open PowerShell
3. Type `cd Downloads/[FolderName]` and press Enter
4. Type `java -jar clienthub.jar` and press Enter


   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 d/likes ramen` : Adds a contact named `John Doe` to the Client Hub.

   * `delete John Doe` : Deletes John Doe from the Client Hub.

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

* Items that are **not** in square brackets are compulsory.<br>
  e.g. `n/NAME` must be used as `n/John Doe`.

* Items with `…`​ after them can be used multiple times.<br>
  e.g. `[c/CLIENT_TYPE]…​` can be used as ` `, `c/Plan A`, `c/Plan A c/Plan B` etc. (i.e. 0 or more times, since it is a optional field) <br>
  e.g. `c/CLIENT_TYPE…​`can be used as `c/Plan A`, `c/Plan A c/Plan B` etc. (i.e. 1 or more times, since it is compulsory)

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Add `$` after the input name to indicate **exact** name.<br>
  * This is useful when a client's name is a prefix of another client's name.

  e.g. `delete John Doe$` will delete the contact with the name `John Doe`.<br>
  e.g. If there are two client named `David Li` and `David Lim`, typing `delete David Li$` will delete the client with the name `David Li`.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit`, `sort` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>


### Parameter Specifications
* Specific constraints for each parameter are specified in the table below.
* Commands that use any of these parameters will have to adhere to the constraints specified in the table.


| Command            | Constraints                                                                                                                                                                                                                                              | Examples                                                                                          |
|--------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------|
| `NAME`             | <br> - Must start with a letter <br> - Must contain more than 1 character <br> - **First** character: only letters <br> - **Last** character: only letters or parentheses <br> - **Middle** characters: letters, parentheses, or slashes                 | **Valid**: `John Doe`, `John (NUS)`, `John S/O Bob` <br> **Invalid**: `A`, `John 123`, `!John Doe` |
| `PHONE_NUMBER`     | - Only numbers <br> - Exactly 8 digits                                                                                                                                                                                                                   | **Valid**: `12345678` <br> **Invalid**: `123456789`, `abc`                                        |
| `EMAIL`            | <br> - Format: `local-part@domain` <br> - Local-part: alphanumeric and (+_.-) but cannot start/end with special characters <br> - Domain: ends with at least 2 characters; each label is alphanumeric, may contain hyphens but cannot start/end with them | **Valid**: `example@mail.com` <br> **Invalid**: `@mail.com`, `example@com`, `example@.com`        |
| `ADDRESS`          | <br> - Allowed characters: letters, numbers, `,#-():;`                                                                                                                                                                                                   | **Valid**: `John street, block 123, #01-01` <br> **Invalid**: `$Block 69`                         |
| `DESCRIPTION`      | <br> - Limited to 500 characters (including whitespace)                                                                                                                                                                                                  | **Valid**: `likes bubble tea`                                                                     |
| `CLIENT_TYPE`      | - Alphanumeric only <br> - Max length: 30 characters <br> - No duplicates; duplicate entries are combined <br> - Is case sensitive (`Health` is different from `health` while `Investment` and `Investment` are the same and is considered duplicates)   | **Valid**: `Plan A` <br> **Invalid**:  `Investment #1`                                            |
| `DATETIME`         | - Cannot be empty <br> - Format: `yyyy-MM-dd HH:mm` in 24-hour format                                                                                                                                                                                    | **Valid**: `2022-10-10 12:00` <br> **Invalid**: `2022-10-10 12:00 pm`                             |
| `REMINDER_DESCRIPTION` | <br> - Max length: 300 characters                                                                                                                                                                                                                        | **Valid**: `Meeting with John at 12pm`                                                            |
| `INDEX`            | <br> - Must be a positive integer                                                                                                                                                                                                                        | **Valid**: `1`, `2`, `3` <br> **Invalid**: `0`, `-1`                                              |

### Note
* `NAME` is case-insensitive. The first letter of every word will be automatically converted to capital letter and the rest to small letters. This is
to ensure that 2 names with the same letters but different cases are not considered as different names. e.g. `john doe` and `John Doe` are considered the same name and cannot be added as 2 different clients.



### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a client: `add`

Adds a client to Client Hub.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS d/DESCRIPTION c/CLIENT_TYPE…​`

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01  d/likes bubble tea c/Investment`
* `add n/Betsy Crowe p/12345678 e/betsycrowe@example.com a/Yishun Town d/Loves travelling c/Investment c/Healthcare `

* Clients cannot have the same name.
  * If a client with the same name already exists, an error will be thrown.
  * Example of an error: `add n/John Doe p/98765432 if a John Doe already exists in ClientHub
  * To add a client with the same name, the name must be differentiated through means such as `(NUS)` or `(S/O Bob)`.
* Clients can have the same phone number, email, address, description, and client type.

Result for `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 d/likes bubble tea c/Investment`:
![result for 'add'](images/result_for_add.png)


### Editing a client: `edit`

Edits an existing client in  Client Hub.

Format: `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [d/DESCRIPTION] [c/CLIENT_TYPE]…​`

* Edits the client at the specified `INDEX`. The index refers to the index number shown in the displayed client list. The index **must be a positive integer** 1, 2, 3, …​
* The fields constraints are the same as the `add` command.
* At least one of the optional fields must be provided.
  * At most 1 of each field can be edited at a time.(excluding CLIENT_TYPE)
* Existing values will be updated to the input values.
* When editing `CLIENT_TYPE`, the existing `CLIENT_TYPE` of the person will be removed i.e adding of `CLIENT_TYPE` is not cumulative.
* When editing `NAME`, the client with the associated `NAME` should have **no** reminders.
* Edited Name must be unique. ie no 2 clients can have the same name.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower c/investment c/healthcare` Edits the name of the 2nd person to be `Betsy Crower` and add 2 client types of `investment` and `healthcare`.

result for `edit 1 p/91234567`:
![result for 'edit 1 p/91234567'](images/result_for_edit.png)


### Deleting a client: `delete`

Deletes the specified person from ClientHub.

Format: `delete NAME` or `d NAME` or `delete NAME$`

* Deletes the client with specified `NAME`
* `$` is used to indicate specific name to delete
    * For eg. if 2 contacts have names such as "David Li" and "David Lim", typing `delete David Li$` will delete the contact with the name "David Li".
      * if not name not specified with `$`, an error will be thrown if there is more than 1 contact that consists of that name
      * Eg. if there are 2 contacts named `David Li` and `David Lim`, typing `delete David Li` will throw an error.
    * However, deleting David Lim does not require `$` as it is already the **MOST** specific name.
    * Name written before `$` must be **EXACT** name of the contact to be deleted.
    * Order matters when using `$` to delete a contact.

Examples:
* `delete John Doe` deletes the person named `John Doe`
* `delete John Doe$` deletes the person named `John Doe` and not `John Doey`

Result for `delete John Doe`:
![result for 'delete John Doe'](images/result_for_delete.png)


### Locating clients by key information: `find`

Finds clients by `NAME`, `PHONE_NUMBER`, `ADDRESS` or `CLIENT_TYPE`.

#### Locating by `NAME`
Format: `find n/NAME` or `fn NAME` or `find NAME$`
  * Only the name is searched.
  * The search is case-insensitive. e.g `hans` will match `Hans`
  * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
  * Prefix of words will be matched e.g. `Ha B` will match `Hans Bo`
  * `$` is used to indicate exact name to find
  * Clients matching all keyword prefix will be returned (i.e. `AND` search).
    e.g. `Hans Bo` will return `Hans Bo` but not `Hans Gruber`, `Bo Yang`


Examples:
* `find n/John` returns `john` and `John Doe`
* `find n/alex yeo` returns `Alex Yeoh`
* `fn John` returns `John`, `John Doe`, `Doe John`, `Doe John Eng`
* `fn Ale Yeo` returns `Alex Yeoh`
* `fn Yeoh Alex` returns `Alex Yeoh`
* `fn aLex yEOh` returns `Alex Yeoh`

Result for `find n/roy`:
![result for 'find n/roy'](images/result_for_find_name.png)


#### Locating by `PHONE_NUMBER`
Format: `find p/PHONE_NUMBER` or `fp PHONE_NUMBER`
  *  Only numbers that begin with keyword will be matched e.g. `8765432` will not match `98765432`

Examples:
* `find p/9103` returns `91031282`
* `fp 8433` returns `8433 4567`

Result for `find p/9103`:
![result for 'find 9103'](images/result_for_find_phone.png)


#### Locating by `ADDRESS`
Format: `find a/ADDRESS` or `fa ADDRESS`
  * The search is case-insensitive. e.g `tampines` will match `Tampines`
  * Only the address of the contact is searched.
  * Clients with address with any matching substring to the keyword will be returned.

Examples:
* `find a/Blk 47` returns `Blk 47 Tampines Street 20`
* `fa Blk 47` returns `Blk 47 Tampines Street 20`

Result for `find a/tampines`:
![result for 'fa tampines`](images/result_for_find_address.png)


#### Locating by `CLIENT_TYPE`
Format: `find c/CLIENT_TYPE…​` or `fc CLIENT_TYPE…​`
* The search is case-insensitive. e.g `investment` will match `Investment`
* Only the `CLIENT_TYPE` of the person is searched.
* Clients whose `CLIENT_TYPE` contains a substring that matches the provided `CLIENT_TYPE` will be returned.
* Client with `CLIENT_TYPE` that has a prefix matching the input `CLIENT_TYPE` will be returned (i.e. `AND` search).
* Duplicate `CLIENT_TYPE` will be combined into 1 (No way to have duplicate client types showing)

Examples:
* `find c/Investment` returns every contact that has a `client_type` beginning with `Investment`
* `find c/Invest` returns every contact that has `client_type` beginning with `Invest`
* `find c/Investment Healthcare` returns every contact that has `client_type` beginning with `Investment` and `Healthcare`
* `fc Investment` returns `Investment Plan`
* `fc Investment Healthcare` returns `Investment Plan` and `Healthcare Plan`

Result for `find c/Investment`:
![result for 'find c/Investment`](images/result_for_find_clienttypes.png)


### Reminder Features
ClientHub has a basic reminder list that keeps track of a users commitments to
specific clients. The reminder list is a list of reminders that can be added,
deleted, and edited.


#### Adding Reminder: `radd`

Adds a reminder to the reminder list.

Format:
`radd n/NAME dt/DATETIME r/REMINDER_DESCRIPTION` or
`ra n/NAME dt/DATETIME r/REMINDER_DESCRIPTION`

* If specified datetime is invalid, the reminder will be update to the **NEAREST VALID** datetime
  * Eg. If datetime is made to be `2024-02-30 16:00` which is invalid as 30th February does not exist, the reminder will be added with dt:`2024-02-29 16:00`

Result for `radd n/John Doe dt/2022-10-10 12:00 d/lunch`:
![result for 'radd`](images/result_for_add_reminder.png)


#### Editing a Reminder: `redit`

Edits an existing reminder in the reminder list.

Format:
`redit INDEX [dt/DATETIME] [d/REMINDER_DESCRIPTION]` or
`re INDEX [dt/DATETIME] [d/REMINDER_DESCRIPTION]`

* Edits the reminder at the specified `INDEX`. The index refers to the index number shown in the displayed reminder list. The index **must be a positive integer** 1, 2, 3, …​
* Have least one of the optional fields must be provided.
  * At most 1 of each field can be edited at a time.
* Existing values will be updated to the input values.
* If edited datetime is invalid, the reminder will be update to the **NEAREST VALID** datetime
  * Eg. If datetime is made to be `2024-02-30 16:00` which is invalid as 30th February does not exist, the reminder will be updated to `2024-02-29 16:00`

Examples:
* `redit 1 dt/2022-10-10 12:00 d/Meeting for lunch` Edits the date and time and description of the 1st reminder to be `2022-10-10 12:00` and `Meeting for lunch` respectively.
* `re 2 dt/2022-10-10 12:00` Edits the date and time of the 2nd reminder to be `2022-10-10 12:00`

Result for `redit 1 dt/2022-10-10 12:00 d/Meeting for lunch`:
![result for 'redit`](images/result_for_edit_reminder.png)


#### Deleting a Reminder: `rdelete`

Deletes a reminder from the reminder list.

Format:
`rdelete INDEX` or
`rd INDEX`

* Deletes the person with specified INDEX. The index refers to the index number shown in the displayed reminder list. The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `rdelete 1` deletes the person at index 1 of the list
* `rd 2` will delete the person at index 2 of the list

Result for `rdelete 1`:
![result for 'rdelete`](images/result_for_delete_reminder.png)


### Viewing a client: `view`

Creates a single popup view of the specified client from ClientHub.

Format: `view NAME` or `v NAME` or `view NAME$`
* The command is case-insensitive. eg. `alice` will match `Alice`
* The command does a `find` and displays the popup view only if the no. of clients found is exactly 1.
* If duplicates are found, `view` will throw an error telling user to specify the name further.
    * For eg. if 2 contacts have names such as "David Li" and "David Lim", typing `view David` will throw an error.
* Executing another `view` command will change the contents of the popup view to the new specified client.

Examples:
* `view John Doe` shows the contact named `John Doe`
* `view John` will throw an error if there is `John Doe` and `John Doey` in the list of contacts.

Result for `view jeremy`:
![result for 'view jeremy`](images/result_for_view.png)


### Sort by name : `sort`
Sort the current list of clients on Client Hub according to their `NAME`.

Format: `sort`

* The list of clients is sorted alphabetically by their full names in ascending order, where no duplicate names are allowed.

Examples:
* `sort` sorts the list


### Listing all persons : `list`
Shows a list of all persons in the Client Hub.

Format: `list`


### Clearing all entries : `clear`
Clears all entries from ClientHub.

Format: `clear`

### Exiting the program : `exit`
Exits the program.

Format: `exit`

--------------------------------------------------------------------------------------------------------------------

## Additional Features
### Command History Navigation
The command history feature allows users to easily navigate through previously entered commands using the **Up** and **Down** arrow keys. This is helpful for quickly accessing past commands without needing to retype them.

* **Up** Arrow: Moves to the previous command in the history.
* **Down** Arrow: Moves to the next command in the history.

This feature enables efficient command recall, streamlining the process of repeating or editing previous inputs.

### Saving the data

ClientHub data is saved in the hard disk automatically after any command that changes
the data. There is no need to save manually.

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

| Action                | Format, Examples                                                                                                                                                                                     |
|-----------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Help                  | `help`                                                                                                                                                                                               |
| **Add**               | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS c/CLIENT_TYPE d/DESCRIPTION…​ ` <br> e.g `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 c/Plan A c/Plan A d/crimefighter` |
| **Delete**            | `delete NAME`<br> e.g `delete JAMES`                                                                                                                                                                 |
| **Edit**              | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [c/CLIENT_TYPE] [d/DESCRIPTION]`<br> e.g `edit 2 n/James Lee e/jameslee@example.com`                                                     |
| **Find Name**         | `find n/NAME` or `fn NAME` <br> e.g `find n/John` or `fn John`                                                                                                                                       |
| **Find Phone Number** | `find p/PHONE_NUMBER` or `fp PHONE_NUMBER` <br> e.g `find p/98765432` or `fp 987654432`                                                                                                              |
| **Find Address**      | `find a/ADDRESS` or `fa ADDRESS` <br> e.g `find a/Blk 30` or `fa Blk 30`                                                                                                                             |
| **Find Client Type**  | `find c/CLIENT_TYPE` or `fc CLIENT_TYPE`<br/> e.g `find c/Investment` or `fc Investment`                                                                                                             |
| **Add Reminder**      | `radd n/NAME dt/DATETIME d/DESCRIPTION` or `ra /NAME dt/DATETIME d/DESCRIPTION` <br> e.g `radd n/John dt/2024-01-01 12:00 d/Appointment` or `ra n/John dt/2024-01-01 12:00 d/Appointment`            |
| **Delete Reminder**   | `rdelete INDEX` or `rd INDEX` <br> e.g `rdelete 1` or `rd INDEX`                                                                                                                                     |
| **Edit Reminder**     | `redit INDEX [dt/DATETIME] [d/DESCRIPTION]` or `re INDEX [dt/DATETIME] [d/DESCRIPTION]` <br> e.g `redit 1 [dt/2024-02-02 13:00] [d/Review]` or `re 1 [dt/2024-02-02 13:00] [d/Review]`               |
| **View**              | `view NAME`<br> e.g., `view James`                                                                                                                                                                   |
| **Sort**              | `sort`                                                                                                                                                                                               |
| **List**              | `list`                                                                                                                                                                                               |
| **CLear**             | `clear`                                                                                                                                                                                              |
| **Exit**              | `exit`                                                                                                                                                                                               |
