---
layout: page
title: User Guide
---
Hey SoC Students,

Are you meeting too many people during your internship grind? Struggling to manage your contacts?

Then NetBook is the application for you!

NetBook is a **desktop app for managing your contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, NetBook can get your contact management tasks done faster than traditional GUI apps.

### Table of Contents
* [Quick Start](#quick-start)
* [Features](#features)
* [Help Command](#viewing-help--help-or-h)
  * [Contact Management](#contact-management)
    * [Adding a contact](#adding-a-person-add-or-a)
    * [List all contacts](#listing-all-persons--list-or-l)
    * [Find individuals by name or organisation](#locating-persons-by-name-and-organization-find-or-f)
    * [Edit a contact](#editing-a-person--edit-or-ed)
    * [Sorting contacts](#sorting-persons--sort-or-s)
    * [Saving your sort preference](#save-sort-preference-save_sort-or-svp)
    * [Delete a contact](#deleting-a-person--delete-or-del)
    * [Add a remark to a contact](#add-a-remark-to-a-person--remark)
    * [Delete all contacts](#clearing-all-entries--clear-or-c)
  * [Reminder Management](#reminder-management)
    * [Create a reminder](#create-a-reminder-remind-or-rem)
    * [Delete a reminder](#deleting-a-reminder-delete_reminder-or-dr)
  * [Exit NetBook](#exiting-the-program--exit-or-ex)
* [FAQ](#faq)
* [Known Issues](#known-issues)
* [Command Summary](#command-summary)

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
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

<div markdown="block" class="alert alert-info">

**Notes about the command format:**<br>

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

### Viewing help : `help` or `h`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

## Contact Management

### Adding a person: `add` or `a`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL o/ORGANIZATION [d/LAST SEEN] [t/TAG]… [pr/PRIORITY] [r/REMARK]`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

* Last Seen defaults to today's date if not specified
* Priority defaults to low if not specified
* Remark defaults to "No remarks added yet" if not specified

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com o/nus`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com t/criminal`

### Listing all persons : `list` or `l`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit` or `ed`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name and organization: `find` or `f`

Finds persons by names and organisation.

Format: `find [n/NAME...] [o/ORGANIZATION...]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* Only full words will be matched e.g. `Han` will not match `Hans`
* At least 1 of the `n/` and `o/` fields must be provided
* If only 1 field is provided, Persons matching at least one keyword in the field will be returned e.g. `n/ Hans Yu` will return `Hans Gruber` and `Yu Beong`
* If both fields are provided, Persons matching at least one keyword in both fields will be returned e.g. `n/ Hans Yu o/NUS` will return `Hans Gruber from NUS` but will not return `Yu Beong from NTU`

Examples:
* `find n/John` returns `john` and `John Doe`
* `find n/alex david o/meta` returns `David Li from Meta`

### Sorting persons : `sort` or `s`

Sorts the list of persons according to the given criteria

Format: `sort PREFERENCE` or `s PREFERENCE`

Preference types: `high`, `low`, `recent`, `distant`

* `high`: Persons with high priority rise to the top, followed by medium then low
* `low`: Persons with low priority rise to the top, followed by medium then high
* `recent`: Persons with more recent "last seen" dates rise to the top
* `distant`: Persons with more distant "last seen" dates rise to the top

Examples:
* `sort high`
* `s recent`

### Save sort preference: `save_sort` or `svp`

Saves the sorting preference specified by the user.

Format: `save_sort PREFERENCE` or `svp PREFERENCE`

Preference types: `default`, `high`, `low`, `distant`, `recent`

* `high`, `low`, `recent`, `distant`: Sorts according to the preferences specified in section **Sorting persons**.
* `default`: Persons are sorted in chronological order according to when they are added to NetBook.
* Changes will only be reflected upon restarting NetBook.

Examples: 
* `save_sort high`
* `svp default`

### Deleting a person : `delete` or `del`

Deletes the specified person from the address book.

Format: `delete INDEX` or `del INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `del 1` deletes the 1st person in the results of the `find` command.

### Add a remark to a person : `remark`

Adds a remark to an existing person in the address book.

Format: `remark INDEX [r/REMARK]​`

* Remarks the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* Existing remark will be overwritten by the input.

Examples:
*  `remark 1 r/handsome` Remarks the 1st person with `handsome`.
*  `remark 2 r/pretty` Remarks the 2nd person with `pretty`.

### Clearing all entries : `clear` or `c`

Clears all entries from the address book.

Format: `clear` or `c`

## Reminder Management

### Create a reminder: `remind` or `rem`

Creates a reminder for the specified person in the address book.

Format: `remind INDEX d/DATE des/DESCRIPTION`

* Creates a reminder for the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* Expired reminders will display negative days

Examples:
* `remind 1 d/21-11-2024 des/Meet up for lunch` will create a reminder with the date 21-11-2024, about meeting the
person at index 1 for lunch
* `remind 2 d/25-12-2024 des/Christmas Date` will create a reminder with the date 25-12-2024, about having a 
Christmas date with the person at index 2

### Deleting a reminder: `delete_reminder` or `dr`

Deletes the specified reminder from the address book.

Format: `delete_reminder INDEX` or `dr INDEX`

* Deletes the reminder at the specified `INDEX`.
* The index refers to the index number shown in the displayed reminder list.
* The index **must be a positive integer** 1, 2, 3, …​
* **Expired reminders will display as negative days.** Be sure to delete expired reminders to keep your list up to date.

Examples:
* `delete_reminder 5` and `dr 5` deletes the 5th reminder in the reminder list

### Exiting the program : `exit` or `ex`

Exits the program.

Format: `exit`

### Saving the data

NetBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

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
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL o/ORGANIZATION [d/LAST SEEN] [t/TAG]… [pr/PRIORITY] [r/REMARK]​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find [n/NAME…] [o/ORGANIZATION…]`<br> e.g., `find n/James Jake o/TikTok`
**Remark** | `remark INDEX [r/REMARK]`<br> e.g., `remark 2 r/handsome`
**Sort** | `sort PREFERENCE` <br> e.g., `sort high`
**Save Sort** | `save_sort PREFERENCE` <br> e.g., `save_sort distant`
**Create Reminder** | `remind INDEX d/DATE des/DESCRIPTION` <br> e.g., `remind 3 d/22-12-2024 des/Meet up for dinner`
**Delete Reminder** | `delete_reminder INDEX` <br> e.g., `delete_reminder 4`
**List** | `list`
**Help** | `help`
