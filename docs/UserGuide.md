---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# ServiceTrack User Guide

ServiceTrack is a **desktop app for managing customer contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, ServiceTrack can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start


1. Ensure you have Java `17` or above installed in your Computer. <br>(If you are  a mac user, you will need to download 
javaFX first via the following commands) <br>
   `curl -s "https://get.sdkman.io" | bashsource "$HOME/.sdkman/bin/sdkman-init.sh"` <br>
   `sdk install java 17.0.11.fx-zulu` <br>
   `sdk default java 17.0.11.fx-zulu`

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-T17-2/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, [navigate](https://riptutorial.com/cmd/example/8646/navigating-in-cmd) into the folder you put the jar file in, then enter `java -jar ServiceTrack.jar` into the command line to run the application.<br>
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

* Extraneous parameters for some commands that do not take in parameters (such as `help`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>


### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​ [c/COMMENT] [vip/IS_VIP]…​`

* Name should consist of alphanumeric characters and spaces, and should not be blank or contain ONLY numeric characters.
* Phone numbers should only contain digits, and should have at least 3 digits.
* Emails should be of the format local-part@domain and adhere to the following constraints:<br>
  1. The local-part should only contain alphanumeric characters and these special characters, excluding the parentheses, (``). The local-part may not start or end with any special characters.
  2. This is followed by a '@' and then a domain name. The domain name is made up of domain labels separated by periods.<br>
  The domain name must:
      - end with a domain label at least 2 characters long,
      - have each domain label start and end with alphanumeric characters,
      - have each domain label consist of alphanumeric characters, separated only by hyphens, if any.
* Tags are case-insensitive (e.g. `FRIEND` and `friend` are not the same tag)
* (if supplied) `IS_VIP` should either be `true` or `false`, corresponding to whether the person being added is initialized as VIP.
* If multiple `vip/` commands are supplied, the later one will be registered.<br>(e.g. `add n/kelvin p/98765432 e/kelv@example.com a/klev street, block 123, #01-01, vip/true, vip/false` will register kelvin as non-VIP.)
* Person added is by default a non-VIP if the `vip/` command is omitted.
<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0)
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal c/life sentence vip/false`

![img.png](images/AddCommandDemo.png)

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Adding comments to a person: `comment`

Adds a comment to the specified person from the address book.

Format: `comment INDEX c/ [COMMENT]`

* Affects the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* if COMMENT is not supplied, this will delete any existing comment on the person, otherwise it will add the supplied comment.

Examples:
* `list` followed by `comment 2 c/ Prefers to communicate in chinese` adds the sentence "Prefers to communicate in chinese" as a comment to the second person on the list.
* `search Betsy` followed by `comment 1 c/` removes the comment from the 1st person in the results of the `search` command.

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `search Betsy` followed by `delete 1` deletes the 1st person in the results of the `search` command.

Format: `delete NAME`

* Deletes the person with the specified `NAME`.
* The name refers to the exact name shown in the displayed person list.
* The name is **case insensitive**. ​

Example:
* `list` followed by `delete Betsy` deletes the person with the name `Betsy` in the address book.

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​ [c/COMMENT]`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Constraints regarding each of the fields are the same as those when [adding a person](#adding-a-person-add).
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.
*  `edit 3 c/Working t/` Edits the comment of the 3rd person to be `Working` and clears all existing tags.

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Viewing help : `help`

Shows a message explaning how to access the help page.

![img.png](images/HelpWindow.png)

Format: `help`

### Listing all persons : `list`

Shows a list of all persons in the address book. Vips will be shown on top of the list.

Format: `list`

### Listing all VIPs : `list vip`

Shows a list of all VIPs in the address book.

Format: `list vip`

### Locating persons by name: `search`

Finds persons whose names contain any of the given keywords.

Format: `search KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `search John` returns `john` and `John Doe`
* `search alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'search alex david'](images/findAlexDavidResult.png)

### Locating person by tag: `searchtag`

Finds all customers whose any tag contain any of the specified keywords (case-insensitive) and displays them as a list with index numbers.

Format: `searchtag TAG [MORE TAGS]…​`

* The search is case-insensitive. e.g `FRIENDS` will match `friends`
* Only the tags are searched.
* Only full words will be matched e.g. `fri` will not match `friends`
* Persons have at least one matching tag of any tags will be returned.
  e.g. If `searchtag friends` is the input, a person with tags `friends` and `colleagues` will be included in the search result.

Examples:
* `searchtag friends` returns all persons containing the tag `friends`<br>
  ![result for 'searchtag friends'](images/Searchtag%20Friends.png)
* `searchtag friends colleagues` returns all persons containing the tag `friends` or `colleagues`<br>
  ![result for 'searchtag friends colleagues'](images/SearchtagFriendsColleagues.png)

### Marking whether a person is a VIP : `vip`

Marks the specified person from the address book as a VIP or removes said label.<br>
Persons marked as a VIP will appear at the top of the list when using list, search, or searchtag.

Format: `vip INDEX IS_VIP`

* Affects the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* IS_VIP should either be `true` or `false`, corresponding to whether you intend to mark the target as a VIP or remove such a mark.

Examples:
* `list` followed by `vip 2 true` marks the 2nd person in the address book as a VIP.
* `search Betsy` followed by `vip 1 false` removes VIP status from the 1st person in the results of the `search` command.<br>
  ![result for 'vip 1 false'](images/unmark%20Bernice%20as%20non%20vip.png)

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>


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

Action       | Format, Examples
-------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**      | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​ [c/COMMENT] [vip/IS_VIP]` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague c/5'11 tall`
**Clear**    | `clear`
**Comment**  | `comment INDEX c/[COMMENT]`<br> e.g., `comment 2 c/Calls too often`
**Delete**   | `delete INDEX` `delete NAME` <br> e.g., `delete 3` `delete Bernice`
**Edit**     | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​ [c/COMMENT]`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com c/change comment`
**Exit**     | `exit`
**Help**     | `help`
**List**     | `list` `list vip`
**Search**   | `search KEYWORD [MORE_KEYWORDS]`<br> e.g., `search James Jake`
**SearchTag**| `searchtag TAG [MORE TAGS]`<br> e.g., `searchtag friends`
**Vip**      | `vip INDEX IS_VIP`<br> e.g., `vip 3 true`
