AdmiNUS is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AdmiNUS can get your contact management tasks done faster than traditional GUI apps.

* [Quick start](#Quick-start)
* [Features](#Features)
  * [Viewing help: `help`](#viewing-help-help)
  * [Adding a contact: `student` or `company`](#adding-a-contact-student-or-company)
  * [Listing all contacts: `list`](#listing-all-contacts-list)
  * [Viewing a contact: `view`](#viewing-a-contact-view)
  * [Editing a contact: `edit`](#editing-a-contact-edit)
  * [Locating persons by name: `find`](#locating-persons-by-name-find)
  * [Filtering contacts by tag: `filtertag`](#filtering-contacts-by-tags-filtertag)
  * [Tracking contacts by category: `track`](#tracking-contacts-by-category-track)
  * [Deleting contact(s): `delete`](#deleting-contacts-delete)
  * [Adding tag(s) to contact: `tag`](#adding-tags-to-contact-tag)
  * [Deleting tag(s) from contact: `deletetag`](#deleting-tags-from-contact-deletetag)
  * [Importing CSV files: `import`](#importing-csv-files-import)
  * [Exporting CSV files: `export`](#exporting-csv-files)
  * [Clearing all entries: `clear`](#clearing-all-entries-clear)
  * [Exiting the program: `exit`](#exiting-the-program-exit)

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-T14-2/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar adminus.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `student n/John Doe s/A0123456Z p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**Notes about the command format**<br>

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

### Viewing help: `help`

Shows a message explaining the various commands available. In addition, there is also a link to the user guide if the user deems necessary.

![help message](images/help_window.png)

Format: `help`

### Adding a contact: `student` or `company`

Adds a contact to the address book.

Format 1: `student n/NAME s/STUDENT ID p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

Format 2: `company n/NAME i/INDUSTRY p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<div markdown="span" class="alert alert-primary">**Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
* `student n/John Doe s/A0123456X p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `company n/Newgate Prison i/Security e/newgateprison@example.com a/Newgate Prison p/1234567 t/prison facility`

### Listing all contacts: `list`

Shows a list of all persons in the address book.

Format: `list`

### Viewing a contact: `view`

Show the details of a contact.

Format: `view INDEX`

* View the details of the contact at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​

Example: 

* `view 1` shows the details of the first contact in the list.

//add screenshot here

### Editing a contact: `edit`

Edits an existing contact in the address book.

Format: `edit INDEX [n/NAME] [s/STUDENT ID] [i/INDUSTRY] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the contact at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* For student contact, editing industry field is prohibited.
* For company contact, editing student id field is prohibited.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st contact to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

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

### Filtering contacts by tags: `filtertag`

Finds contacts whose tags are the same as the specified keyword.

Format: `filtertag KEYWORD`
* Filters through the list of contacts by the specified `KEYWORD`

Examples:
* `filtertag buddies` finds contacts who have tags saved as `buddies`

### Tracking contacts by category: `track`

Tracks and lists all contacts who are in the category of the specified keywords.

Format: `track CATEGORY`
* Allows users to filter or sort contacts by `CATEGORY`, such as students or companies, making it easier to track specific groups

Examples:
* `track student` finds contacts with category `student` 
* `track company` finds contacts with category `company`

### Deleting contact(s): `delete`

Deletes the specified contact from the address book.

Format: `delete INDEX [MORE_INDEX]`

* Deletes the contact(s) at the specified `INDEX`.
* Split the indices by spaces. 
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Adding tag(s) to contact: `tag`
Adds additional tags to the specified contact.

Format: `tag INDEX t/TAG [t/MORE_TAG]
 * Add specoified `TAG` or more `TAG` to the contact at specified `INDEX`
 * The index refers to the index number shown in the displayed person list.
 * The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `tag 1 t/year2 t/computerScience` adds the tags `year2` and `computerScience` to the first contact in the list

### Deleting tag(s) from contact: `deletetag`

Deletes the specified tag(s) from the specified contact.

Format:  `deletetag INDEX t/TAG [t/MORE_TAG]`

* Deletes specified `TAG` or more `TAG` from the contact at the specified `INDEX` provided the tag already exists.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `deletetag 1 t/senior t/Y2` deletes the senior and Y2 tags from the first contact in the list

### Importing CSV files: `import`

Imports data from a CSV file, use the import command followed by the path to your CSV file. This allows you to quickly load structured data into your application or script for further analysis and processing.

Format: `import /path/to/data/File.csv`

* `/path/to/data/File.csv` should be the full path to the CSV file you wish to import. Replace `/path/to/data/File.csv` with the actual path to your file. Ensure that the file path is correct and accessible to avoid errors.

Example:

* `import /home/user/data/hackers_2022.csv` imports the CSV file named hackers_2022 from the data directory located in the user's home directory. Once imported, the data can be manipulated or used within the program

Important Notes:

* Make sure to use the correct file path. For Windows, use backslashes or double backslashes, e.g., `C:\Users\username\data\File.csv`.
* The CSV file should be properly formatted, with each value separated by commas and each row ending with a newline character.
* Ensure you have the necessary read permissions for the file. If you encounter a permission denied error, you may need to adjust the file permissions or run the command with the required privileges.

Troubleshooting:

* If you receive an error indicating that the file is not found, double-check the file path and ensure that the CSV file exists in the specified location.
* If the CSV is not properly formatted, importing may fail. Make sure the file follows the standard CSV format (e.g., no extra commas).

### Exporting CSV Files
Export data to a CSV file, use the export command followed by the desired path and filename for your CSV. This command allows you to save the current data in a structured format that can be easily shared or used by other applications.

Format: `export /path/to/data/File.csv`

* `/path/to/data/File.csv` should be the full path where you want to save the CSV file. Replace `/path/to/data/File.csv` with the actual path and filename for your exported file. Ensure that the directory exists and you have the correct permissions to write to it.

Example:

* `export /home/user/data/output_data.csv` exports the current dataset to a CSV file named `output_data.csv` in the data directory located in the user's home directory. This file can then be opened or shared for further use.
* `export ./data/Output.csv` exports the data to a CSV file named Output.csv in the data subdirectory of the current working directory. This approach is useful when you want to save data relative to your current location.

Important Notes:

* Ensure that the directory you are exporting to exists. For Windows, use backslashes or double backslashes, e.g., `C:\Users\username\data\File.csv`.
* If the specified file already exists, it may be overwritten without warning. Ensure you use a unique filename or double-check if overwriting is acceptable.
* Make sure you have the necessary write permissions for the directory you are exporting to. If you encounter a permission denied error, adjust the file permissions or run the command with the required privileges.

Troubleshooting:

* If the directory does not exist, you will receive an error. Create the directory first or specify a valid path.
* If you do not have the necessary write permissions, you will need to adjust them or choose a different directory.

### Clearing all entries: `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program: `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AdminNUS will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
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

| Action            | Format                                                       | Example                                                                                   |
|-------------------|--------------------------------------------------------------|-------------------------------------------------------------------------------------------|
| **Add a student** | `student n/NAME s/STUDENT ID p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` | `student n/James Ho s/A0123456X p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague` |
| **Add a company** | `company n/NAME i/INDUSTRY p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` | `company n/Newgate Prison i/Security e/newgateprison@example.com a/Newgate Prison p/1234567 t/prison facility` |
| **Clear**         | `clear`                                                      | `clear`                                                                                    |
| **Delete**        | `delete INDEX [MORE_INDEX]`                                  | `delete 3` or `delete 2 4`                                                                 |
| **Edit**          | `edit INDEX [n/NAME] [s/STUDENT ID] [i/INDUSTRY] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​` | `edit 2 n/James Lee e/jameslee@example.com`                                                |
| **Filtertag**     | `filtertag KEYWORD`                                          | `filtertag bestie`                                                                         |
| **Find**          | `find KEYWORD [MORE_KEYWORDS]`                               | `find James Jake`                                                                          |
| **List**          | `list`                                                       | `list`                                                                                     |
| **Help**          | `help`                                                       | `help`                                                                                     |
| **Track**         | `track CATEGORY`                                             | `track student`                                                                            |
| **View**          | `view INDEX`                                                 | `view 1`                                                                                   |
| **Add tag**       | `tag INDEX t/TAG [t/MORE_TAG]`                               | `tag 1 t/year2 t/computerScience`                                                          |
| **Delete tag**    | `deletetag INDEX t/TAG [t/MORE_TAG]`                         | `deletetag 1 t/senior t/Y2`                                                                |
| **Import CSV**    | `import /path/to/data/File.csv`                              | `import /home/user/data/hackers_2022.csv`                                                  |
| **Export CSV**    | `export /path/to/data/File.csv`                              | `export /home/user/data/output_data.csv`                                                   |
| **Exit**          | `exit`                                                       | `exit`                                                                                     |
