---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# AB-3 User Guide

AddressBook Level 3 (AB3) is a **desktop app for managing contacts, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------
# Table of Contents
1. [Quick start](#quick-start)
2. [Features](#features)
   1. [Viewing help : `help`](#viewing-help--help)
   2. [Adding a company: `add`](#adding-a-company-add)
   3. [Listing all companies : `list`](#listing-all-companies--list)
   4. [Editing a company : `edit`](#editing-a-company--edit)
   5. [Locating companies by name: `find`](#locating-companies-by-name-find)
   6. [Deleting a company : `delete`](#deleting-a-company--delete)
   7. [Clearing all entries : `clear`](#clearing-all-entries--clear)
   8. [Exiting the program : `exit`](#exiting-the-program--exit)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)
## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all companies.

   * `add n/Google p/98765432 e/google@example.com a/John street, block 123, #01-01 cp/www.google-career-url.com` : Adds a company named `Google` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all companies.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/Google`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/Google t/bigTech` or as `n/Google`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/bigTech`, `t/bigTech t/BigCompany` etc.

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


### Adding a company: `add`

Adds a company to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A company can have any number of tags (including 0)
</box>

Examples:
* `add n/Google p/98765432 e/google@example.com a/John street, block 123, #01-01 cp/www.goole-career-url.com`
* `add n/Meta t/bigTech e/meta@example.com a/Newgate Prison p/1234567 t/salary_high cp/www.meta-career-url.com`

### Special Tag Values for the `add` Command

When using the `add` command, certain **tags** have special values. 
> > **Note**: If no valid value is provided, the tag will be displayed as it is without special handling. 
> For example t/randomtag tag will appear as "randomtag"

Below are the supported categories:

#### 1. Salary, Work-Life Balance, and Interview Difficulty Tags

These tags represent qualitative levels. The valid values for these tags are:

- **LOW**
- **MEDIUM**
- **HIGH**

These tags must be written using the following format (note the underscore):

Salary tag : `t/salary_[value]`

Difficulty tag : `t/difficulty_[value]`

Work Life Balance tag : `t/wlb_[value]`


**Example:**
` add n/Google p/98765432 e/google@example.com a/John street, block 123, #01-01 cp/www.goole-career-url.com
t/salary_HIGH t/wlb_MEDIUM t/difficulty_MEDIUM`

---

#### 2. **Period Tag**

The **`Period`** tag is used to specify:
- **Season**:
    - **Summer**
    - **Winter**
    - **Part-time**

- **Year**: Any year between **2000 and 2500**

Tag format: Salary tag : `t/period_[Season]_[Year]`
**Example:**
` add n/Google p/98765432 e/google@example.com a/John street, block 123, #01-01 cp/www.goole-career-url.com
t/period_summer_2025`


### Listing all companies : `list`

Shows a list of all companies in the address book.

Format: `list`

### Editing a company : `edit`

Edits an existing company in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the company at the specified `INDEX`. The index refers to the index number shown in the displayed company list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the company will be removed i.e adding of tags is not cumulative.
* You can remove all the company’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/grab@example.com` Edits the phone number and email address of the 1st company to be `91234567` and `grab@example.com` respectively.
*  `edit 2 n/Grab t/` Edits the name of the 2nd company to be `Grab` and clears all existing tags.

### Locating companies by name: `find`

Finds companies whose names or tags contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find Salary:HIGH` returns companies with Salary tags and value HIGH
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a company : `delete`

Deletes the specified company from the address book.

Format: `delete INDEX`

* Deletes the company at the specified `INDEX`.
* The index refers to the index number shown in the displayed company list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd company in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st company in the results of the `find` command.

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

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**   | `list`
**Help**   | `help`
