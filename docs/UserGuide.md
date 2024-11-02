---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# UniLink User Guide

UniLink is a desktop app that allows university students to manage their contacts. With UniLink, students can categorise
their contacts into 'work' contacts and 'personal' contacts to better manage their lives!

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

**1. Set up Java**
- Ensure you have Java `17` or above installed in your Computer.
- Not sure? Open up Command Prompt (for Windows) or Terminal (for Mac) and type `java -version` to check.
- Download Java [[here](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)] if needed.

**2. Download UniLink**
- Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-W12-3/tp/releases).

**3. Choose a Home Folder**
- Move the `.jar` file to where you want to store your UniLink data. This will be your 'home folder'.

**4. Launch the app!**
- Open up Command Prompt (for Windows) or Terminal (for Mac) and navigate to the home folder with `cd path/to/your/home/folder`
    - E.g. If my jar file is in a folder called 'Uni' on my Desktop, I can navigate to it with `cd Desktop/Uni`
- Run UniLink by typing `java -jar UniLink.jar`
- The app should open within a few seconds, showing some sample data that you can explore! 

![Ui](images/Ui.png)

**5. Try out some commands**
- Type commands in the command box and press Enter to execute it. Here are some commands to get you started:

   * `list` : Lists all contacts.

   * `add n/John Doe h/@johndoe ct/work` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. For the full list of commands, refer to [Command Summary](#command-summary) below for more details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the Command Format:**<br>

* Words in `UPPER_CASE` need to be provided by you.<br>
    * e.g. in `add n/NAME`: if you want to add someone named John Doe, you'd type `add n/John Doe`.

* Items in [Square Brackets] are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be repeated or omitted entirely<br>
  e.g. `[t/TAG]…​` can be left blank, or used like `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` will also work.

* Anything after certain commands such as `help`, `list`, `exit` and `clear` will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* PDF Users
  * If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
  </box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME h/TELEGRAM_HANDLE p/PHONE_NUMBER e/EMAIL ct/CONTACT_TYPE [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0)
</box>

Examples:
* `add n/John Doe h/@johndoe p/9988 7766 e/johndoh@gmail.com ct/work t/friend`
* `add n/Betsy Crowe t/friend h/@betsy_c p/87452451 e/betsyc@gmail.com ct/personal t/criminal`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [h/TELEGRAM_HANDLE] [p/PHONE] [e/EMAIL] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower h/@betsyyy t/` Edits the name and telegram handle of the 2nd person to be `Betsy Crower` and `@betsyyy` respectively and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose **names** or **telegram handle** contain any of the given keywords.

Format: <br>
`find NAME [MORE_NAME_KEYWORDS]`<br>
`find h/ TELEGRAM_HANDLE`

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

## Command Summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME t/TELEGRAM_HANDLE p/PHONE_NUMBER e/EMAIL ct/CONTACT_TYPE [t/TAG]…​` <br> e.g., `add n/James Ho h/@james_ho p/22224444 e/jamesho@example.com ct/work t/friend t/colleague`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [t/TELEGRAM_HANDLE] [p/PHONE_NUMBER] [e/EMAIL] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee h/@jamesss e/jameslee@example.com`
**Find**   | `find NAME [MORE_NAME_KEYWORDS]`<br> e.g., `find James Jake` <br> `find h/ TELEGRAM_HANDLE` e.g., `find h/ @james_lake`
**List**   | `list`
**Help**   | `help`
