---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# UniLink User Guide

UniLink is a desktop app that allows National University of Singapore (NUS) students to manage their contacts. With UniLink, students can categorise
their contacts into 'work', 'personal' and 'school' contacts to better manage their lives!

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
- Open Command Prompt (for Windows) or Terminal (for Mac) and navigate to the folder where your JAR file is located. Use the cd command followed by the path to the folder. 
    - E.g. If my jar file is in a folder called 'Contacts', which is inside another folder called 'Uni' on my Desktop, I can navigate to it with `cd Desktop/Uni/Contacts`
- Run UniLink by typing `java -jar UniLink.jar`
- The app should open within a few seconds, showing some sample data that you can explore!

- **Using the Command Line**: New to the command line? Check out this [Command Line for Beginners](https://www.freecodecamp.org/news/command-line-for-beginners/) guide!

![Ui](images/Ui.png)

**5. Try out some commands**
- Type commands in the command box and press Enter to execute it. Here are some commands to get you started:

   * `list` : Lists all contacts.

   * `add n/John Doe h/@johndoe ct/work` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `exit` : Exits the app.
- For the full list of commands, refer to [Command Summary](#command-summary) below for more details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the Command Format:**<br>

* All command words (e.g. `add`, `list`) and prefixes (e.g. `n/`, `ct/`) are to be written in lowercase 

* Certain fields used in commands have specific constraints (e.g., format, length). For a full list of field requirements, please refer to the [Field Constraints](#field-constraints) section.

* Words in `UPPER_CASE` need to be provided by you.<br>
    * e.g. in `add n/NAME`: if you want to add someone named John Doe, you would type `add n/John Doe`.

* Items in [Square Brackets] are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be repeated or omitted entirely<br>
  e.g. `[t/TAG]…​` can be left blank, or used like `t/friend`, `t/friend t/family` etc.

* Fields can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE`, `p/PHONE n/NAME` will also work.

* Anything after certain commands such as `help`, `list`, `exit` and `clear` will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* Some fields don't have a character limit, but it's best to keep entries concise. Longer entries may make some details harder to view.

* PDF Users
  * If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
  </box>


### Viewing help : `help`

Shows a message and brings you to the help page when you click 'open URL'.

![help message](images/HelpWindow.png)

Format: `help`


### Adding a person : `add`

Adds a person to the address book.

Format: `add n/NAME ct/CONTACT_TYPE [h/TELEGRAM_HANDLE] [p/PHONE] [e/EMAIL] [m/MODULE] [r/REMARK] [t/TAG]…​`

* At least one of `TELEGRAM_HANDLE`, `PHONE` or `EMAIL` must be provided to add a contact.

<box type="info" seamless>

**Note:** 
* You can add multiple tags (or none at all).
* To avoid duplicate entries, ensure each contact has a unique Telegram Handle, phone number, or email address. You can add multiple contacts with the same name, as long as their contact details (Telegram Handle, phone number, or email) are different.
</box>

Examples:
* `add n/John Doe ct/work h/@johndoe p/99887766 e/johndoh@gmail.com m/CS2030S t/friend`
* `add n/Betsy Crowe ct/personal t/friend h/@betsy_c r/likes cats t/criminal`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Updates details for an existing person in the address book.

Format: `edit INDEX [n/NAME] [ct/CONTACT_TYPE] [h/TELEGRAM_HANDLE] [p/PHONE] [e/EMAIL] [m/MODULE] [r/REMARK] [t/TAG]…​`

* `INDEX` refers to the person's position in the displayed list (i.e. must be a positive integer 1, 2, 3...)
* At least one optional field must be provided.
* Existing values are replaced by the new input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower h/@betsyyy t/` Edits the name and Telegram Handle of the 2nd person to be `Betsy Crower` and `@betsyyy` respectively and clears all existing tags.

### Finding persons by name : `find`

Finds persons whose **names** contain any of the specified keywords.

Format: <br>
`find NAME [MORE_NAME_KEYWORDS]`<br>


* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`
  ![result for 'find alex david'](images/findAlexDavid.png)

### Finding persons by Telegram Handle : `findtele`

Finds persons whose **Telegram Handles** contain any of the specified keywords.

Format: <br>
`findtele TELEGRAM_HANDLE [MORE_TELEGRAM_HANDLE_KEYWORDS]`<br>


* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Partial matches are allowed, and the '@' symbol may be omitted, meaning searching part of a Telegram Handle will still return contacts with the full Telegram Handle e.g. `Hans` will match `@Hansgruber`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `findtele @john` returns `john` and `John Doe`
* `findtele alex david` returns `Alex Yeoh`, `David Li`
  ![result for 'findtele alex david'](images/findteleAlexDavid.png)

### Finding persons by tags : `findtag`

Finds persons whose **tags** contain any of the specified keywords.

Format: <br>
`findtag TAG [MORE_TAG_KEYWORDS]`<br>


* The search is case-insensitive. e.g `friend` will match `Friend`
* The order of the keywords does not matter. e.g. `Friend Family` will match `Family Friend`
* Partial matches are allowed, meaning searching part of a tag will still return contacts with the full tag (e.g. `fam` will match `family`)
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `friend family` will return all contacts with `friend` OR `family` as a tag

Examples:
* `findtag friend` returns `John Doe` and `Betsy Crowe`
* `findtag neighbour family` returns `Charlotte Oliveiro`, `David Li`
![result for 'findtag neighbour family'](images/findtagNeighbourFamily.png)

### Filtering persons by contact type : `filter`

Filters persons by their contact type (i.e. work, school or personal).

Format: `filter CONTACT_TYPE`

* Displays a list of persons tagged with that `CONTACT_TYPE`
* `CONTACT_TYPE` can be either 'WORK', 'SCHOOL', or 'PERSONAL'
* The search is case-insensitive. e.g. 'work' is the same as 'WORK'

Examples:
* `filter WORK` returns all the 'work' contacts
* `filter SCHOOL` returns all the 'school' contacts
* `filter PERSONAL` returns all the 'personal' contacts

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

### Switching display theme : `switch`

Changes the display to 'light' mode or 'dark' mode.

Format: `switch THEME`

* Displays the `THEME` specified
* `THEME` can be `light` or `dark`
* `THEME` is case-insensitive. e.g. 'light' is the same as 'LIGHT'

Examples:
* `switch LIGHT` switches to the 'light' display theme
  ![result for 'switch light'](images/switchLight.png)
* `switch DARK` switches to the 'dark' display theme
  ![result for 'switch dark'](images/switchDark.png)

### Importing data : `import`

Imports contacts from a spreadsheet to the program.

Format: `import`

* Upon first loading UniLink you will find an Import folder
* Download a spreadsheet (`.csv`) file containing important contact info
* Move the spreadsheet(s) to the Import folder
* Running the import command will import all valid contacts to the program's addressbook
  ![.csv file in Import folder](images/csvInImport.png)
  ![Successful import command](images/ImportCommand.png)

<box type="tip" seamless>

**Tip:** You can import multiple contact spreadsheets at a time if you put them all in the Import folder!
</box>

**Note:**
* If the program comes across a contact with an empty or invalid optional field (e.g., `remark` or `address`), the contact will still be imported, but those specific fields will remain blank after importing.
* Take note that the same constraints apply as when trying to add new contacts using the `add` command.
* Contacts with critical issues such as missing required fields (e.g., `contactType`) or invalid formats will be skipped during the import process, and no feedback will currently be provided for skipped entries. Additionally, if a contact already exists and a new import contains the same contact with more fields, it will be skipped as a duplicate without notification.
* For more information on making a `.csv` file to use with the `import` command, please refer to the section: [Preparing a .csv file](#preparing-a-csv-file-)

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

UniLink data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

UniLink data are saved automatically as a JSON file `[JAR file location]/data/unilink.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, UniLink will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the UniLink to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

---
## Preparing a `.csv` file 

A `.csv` file is simply one of the ways that you can save a spreadsheet file. Common ways to edit or prepare a spreadsheet and save it as a `.csv` file include [Google Sheets](https://www.supportyourtech.com/google/sheets/how-to-save-as-a-csv-from-google-sheets-step-by-step-guide/), and [Microsoft Sheets](https://www.exceldemy.com/save-excel-file-as-csv/). UniLink uses spreadsheets and `.csv` files to help you easily compile, and distribute a large number of contacts quickly. Once you prepare one `.csv` file or spreadsheet, you can send it out so that many people can import contacts to their own copy of UniLink.

For this guide, we'll be giving you a sample spreadsheet to easily convert into a `.csv` file, as well as some things to note when preparing your spreadsheet!

To start, here is a [sample spreadsheet](https://docs.google.com/spreadsheets/d/1hWybja47YMeKs3-mmOts-QkJANj2WcHPVLlYJ-lfUro/edit?usp=sharing) for you to copy! There is already some sample data inside the sheet, feel free to edit that.

### Constraints
Do take note of the following when preparing your contacts,
1. Do not try to import a `.csv` file that is empty.
2. Do not change the capitalisation of the sections at the top, if you do, UniLink will not be able to import your data correctly.
3. Note that the contact type can only be either Work, Personal or School. If that contact contains any other contact type, or no contact type, it will fail to import. 
4. UniLink requires at least one of the following fields to be filled for it to import your contact: Email, Phone Number or Telegram Handle.
5. All the information needs to follow the constraints laid out in the [Field Constraints section](#field-constraints), if you do not fill in the information as per the constraints, UniLink will not be able to import the contact at all!
6. UniLink will skip over duplicate contacts! Contacts are considered duplicate when they share the same name AND they have the same email, phone number or Telegram Handle
   1. Example: If I have a contact called Amy, whose Telegram Handle is @Amy, and I try to import another contact, Amy, whose Telegram Handle is @Amy1, this will work.
   2. Similarly, trying to add Ben with Telegram Handle @Amy will not work.
   3. This is the case with emails, phone numbers and Telegram Handles
7. Do not import contacts from any other source (E.g Google contacts)

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous UniLink home folder.<br>
**Q**: When adding contacts, do I **HAVE** to add a Telegram Handle, Phone Number or Email?<br>
**A**: UniLink does require at least ONE of these fields to be filled before adding a contact.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.
3. **[For macOS users] If you open the application in fullscreen** and then use the `help` command, the Help Window may sometimes display visual glitches (such as a black background). Despite this, the application will continue to function normally after closing the Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command Summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME ct/CONTACT_TYPE [t/TELEGRAM_HANDLE] [p/PHONE] [e/EMAIL] [m/MODULE] [r/REMARK] [t/TAG]…​` <br> e.g., `add n/James Ho h/@james_ho p/22224444 e/jamesho@example.com ct/work t/friend t/colleague`<br>Note: At least one of `TELEGRAM_HANDLE`, `PHONE` or `EMAIL` must be provided
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [ct/CONTACT TYPE] [t/TELEGRAM_HANDLE] [p/PHONE] [e/EMAIL] [m/MODULE] [r/REMARK] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee h/@jamesss e/jameslee@example.com`
**Find**   | `find NAME [MORE_NAME_KEYWORDS]`<br> e.g., `find James Jake` <br><br> `findtele TELEGRAM_HANDLE [MORE_TELEGRAM_HANDLE_KEYWORDS]`<br> e.g., `findtele @james_lake`<br><br> `findtag TAG [MORE_TAG_KEYWORDS]`<br> e.g., `findtag friend`
**Filter** | `filter [CONTACT_TYPE]`<br> e.g., `filter WORK`, `filter PERSONAL`
**Import** | `import`
**List**   | `list`
**Switch** | `switch THEME`<br> e.g., `switch LIGHT`, `switch DARK`
**Help**   | `help`
**Exit**   | `exit`

--------------------------------------------------------------------------------------------------------------------

## Field Constraints

To ensure your commands work as expected, refer to the table below for guidance on each field’s requirements, including format and length. This way, you’ll know exactly how to input information like names, phone numbers, and other fields correctly. We’ve also included examples to make things easier!

| **Field**       | **Constraints**                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     | **Examples**                                           |
|-----------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------|
| NAME            | - Should only contain alphabets and spaces                                                                                                                                                                                                                                                                                                                                                                                                                                                          | Alice Tan <br> Hubert Koh                              |
| CONTACT TYPE    | - Should be 'WORK', 'PERSONAL' or 'SCHOOL' <br> - Not case sensitive                                                                                                                                                                                                                                                                                                                                                                                                                                | Work <br> personal                                     |
| TELEGRAM_HANDLE | - Should begin with '@' <br> - Can contain alphabets, numbers, or underscores <br> - Between 5 and 32 characters (excluding '@')                                                                                                                                                                                                                                                                                                                                                                    | @kacey_lim <br> @Maxine123                             |
| PHONE           | - May start with optional '+' followed by a country code <br> - Should contain only digits <br> - Between 3 and 15 digits long                                                                                                                                                                                                                                                                                                                                                                      | 999 <br> +6598765432                                   |
| EMAIL           | - Must follow the 'local-part@domain' format <br> <br> **Local Part:** <br> - Can include letters, numbers, underscores (_), periods (.), and dashes (-) <br> - Must not start or end with a special character <br> <br> **Domain:** <br> - Must begin with '@', followed by domain labels separated by periods and dashes <br> - Each domain label must start and end with an alphabet or number <br> - Must end with a domain label of at least 2 characters following a period (e.g. .com, .edu) | ryan_teo@example.com <br> caleb-sim@email.edu.sg       |
| MODULE          | - Should start with 2 to 4 letters, followed by 4 numbers <br> - May end with optional single letter or a dash and one letter                                                                                                                                                                                                                                                                                                                                                                       | CS2103-T <br> HSI1000                                  |
| REMARK          | - Should be between 1 and 200 characters long                                                                                                                                                                                                                                                                                                                                                                                                                                                       | likes coffee in the morning <br> stays at Temasek Hall |
| TAG             | - Should be a single word - Can contain letters and numbers                                                                                                                                                                                                                                                                                                                                                                                                                                         | friend <br> 24h                                        |

