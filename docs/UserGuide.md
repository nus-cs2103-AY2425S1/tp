---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# ClubConnect User Guide

Welcome to ClubConnect!

ClubConnect is your go-to desktop application for keeping your contacts and events organised and connected. 
With ClubConnect, you can quickly access and manage all your member details, sponsor contacts, 
and event participant lists in one convenient place. 
This guide will show you how to use ClubConnect’s features to enhance collaboration, boost member engagement, 
and make planning a breeze — so you can focus more on what matters and spend less time on administrative work.



<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-F09-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your ClubConnect.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar clubconnect.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to ClubConnect's contact list.

   * `delete 3` : Deletes the 3rd contact shown in the current contact list.

   * `clear` : Deletes all contacts and events.

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

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to ClubConnect's contact list.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0)
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all persons : `list`

Shows a list of all persons stored in ClubConnect's contact list.

Format: `list`

### Editing a person : `edit`

Edits an existing person in ClubConnect's contact list.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. 
* `INDEX` refers to the index number shown in the displayed person list. 
* `INDEX` **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
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

### Searching by a specified field : `search` 

Finds all persons whose specified field contains any of the specified keywords and displays them as a list.

Format: `search {FIELD_PREFIX}/KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* Only the specified field is searched.
* Substrings will be matched e.g. `Alexander` will be shown with the keyword `Alex`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. The keywords `Hans Bo` will return `Hans Gruber`, `Bo Yang`
* However, the search for events is an exact search criteria, where all characters must match.
* Any number of words used with the event prefix `ev/` is treated as a single keyword.

Examples:
* `search a/street avenue`
* `search e/gmail exampleEmail`
* `search n/alice bob charlie`
* `search p/98765432 12345678`
* `search t/friend colleague`
* `search ev/Orbital Workshop`

### Deleting a person : `delete`

Deletes the specified person from ClubConnect's contact list.

Format: `delete INDEX` / `delete CONTACT_NAME`

* Deletes the person at the specified `INDEX` / with name `CONTACT_NAME`.
* `CONTACT_NAME` refers to the name of the contact (case-insensitive).
* `INDEX` refers to the index number shown in the displayed contact list.
* `INDEX` **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in ClubConnect's contact list.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.
* `delete john doe` will delete the contact with name `john doe` (case-insensitive).

### Deleting multiple people : `mass_delete`
Deletes multiple specified contacts from ClubConnect's contact list using their displayed indices.

Format: `mass_delete INDEX1 INDEX2 ... INDEXN`

* Deletes the persons at the specified indices.
* Each index refers to the index number shown in the displayed person list.
* All indices must be positive integers 1, 2, 3, … 
* Invalid indices will be filtered out.

Examples:
* `list` followed by `mass_delete 1 2` deletes the 1st and 2nd persons in ClubConnect's contact list.
* `find Betsy` followed by `mass_delete 1 3 a` deletes the 1st and 3rd persons in the results of the find command.

### Exporting all contacts: `export`

Exports all persons in ClubConnect into a csv file named `ExportedContacts.csv` located in the data folder.

Format: `export`

### Importing persons: `import`

Reads the specified file to import from and adds the persons to ClubConnect.

Format: `import FILENAME`

Examples:
* `import contacts.csv` adds persons stored in `contacts.csv` to the ClubConnect.
* The file has to be located in the data folder.
* The specified file name has to exactly match the name of the file to import from.
* Name of file to import contacts from must end with `.csv`.

### Adding an event: `add_event`

Adds an event to ClubConnect's event list.

Format: `add_event n/EVENT_NAME d/EVENT_DESCRIPTION f/EVENT_START_DATE t/EVENT_END_DATE`

* The date inputs must be in the format `YYYY-MM-DD`.

Examples:
* `add_event n/Meeting d/CS2103T Meeting f/2024-09-09 t/2024-09-10`
* `add_event n/Workshop d/Orbital Workshop f/2024-10-01 t/2024-10-10`

### Listing all events : `list_events`

Shows a list of all events stored in ClubConnect's event list.

Format: `list_events`

### Deleting an event: `delete_event`

Deletes the specified event from ClubConnect's event list.

Format: `delete_event INDEX` / `delete_event EVENT_NAME`

* Deletes the event at the specified `INDEX` / with event name `EVENT_NAME`.
* `EVENT_NAME` refers to the name of the event (case-insensitive).
* `INDEX` refers to the index number shown in the displayed event list.
* `INDEX` **must be a positive integer** 1, 2, 3, …​

Examples:
* `list_events` followed by `delete_event 2` deletes the 2nd event in ClubConnect's event list.
* `delete_event meeting` will delete the event with event name `meeting` (case-insensitive).

### Assigning an event: `assign_event`

Assigns a specified event to a specified person stored in ClubConnect's contact list.

Format: `assign_event p/PERSON_INDEX ev/EVENT_INDEX` / `assign_event p/PERSON_NAME ev/EVENT_INDEX` / `assign_event p/PERSON_INDEX ev/EVENT_NAME` / `assign_event p/PERSON_NAME ev/EVENT_NAME`

* Assigns the event specified by `EVENT_INDEX` or `EVENT_NAME` to the person specified by `PERSON_INDEX` or `PERSON_NAME`.
* `EVENT_INDEX` refers to the index number shown in the displayed event list.
* `EVENT_INDEX` **must be a positive integer** 1, 2, 3, …​
* `PERSON_INDEX` refers to the index number shown in the displayed contact list.
* `PERSON_INDEX` **must be a positive integer** 1, 2, 3, …​
* `EVENT_NAME` refers to the name of the event (case-insensitive).
* `PERSON_NAME` refers to the name of the person (case-insensitive).

Examples:
* `assign_event p/1 ev/2` will assign the 2nd event in ClubConnect's event list to the 1st person in ClubConnect's contact list.
* `assign_event p/Alice ev/2` will assign the 2nd event in ClubConnect's event list to a person named `Alice` (case-insensitive) in ClubConnect's contact list.
* `assign_event p/1 ev/Meeting` will assign an event named `Meeting` (case-insensitive) in ClubConnect's event list to the 1st person in ClubConnect's contact list.
* `assign_event p/Alice ev/Meeting` will assign an event named `Meeting` (case-insensitive) in ClubConnect's event list to a person named `Alice` (case-insensitive) in ClubConnect's contact list.


### Unassigning an event: `unassign_event`

Unassigns a specified event from a specified person stored in ClubConnect's contact list.

Format: `unassign_event p/PERSON_INDEX ev/EVENT_INDEX` / `unassign_event p/PERSON_NAME ev/EVENT_INDEX` / `unassign_event p/PERSON_INDEX ev/EVENT_NAME` / `unassign_event p/PERSON_NAME ev/EVENT_NAME`

* Unassigns the event specified by `EVENT_INDEX` or `EVENT_NAME` from the person specified by `PERSON_INDEX` or `PERSON_NAME`.
* `EVENT_INDEX` refers to the index number shown in the displayed event list.
* `EVENT_INDEX` **must be a positive integer** 1, 2, 3, …​
* `PERSON_INDEX` refers to the index number shown in the displayed contact list.
* `PERSON_INDEX` **must be a positive integer** 1, 2, 3, …​
* `EVENT_NAME` refers to the name of the event (case-insensitive).
* `PERSON_NAME` refers to the name of the person (case-insensitive).

Examples:
* `unassign_event p/1 ev/2` will unassign the 2nd event in ClubConnect's event list from the 1st person in ClubConnect's contact list.
* `unassign_event p/Alice ev/2` will unassign the 2nd event in ClubConnect's event list from a person named `Alice` (case-insensitive) in ClubConnect's contact list.
* `unassign_event p/1 ev/Meeting` will unassign an event named `Meeting` (case-insensitive) in ClubConnect's event list from the 1st person in ClubConnect's contact list.
* `unassign_event p/Alice ev/Meeting` will unassign an event named `Meeting` (case-insensitive) in ClubConnect's event list from a person named `Alice` (case-insensitive) in ClubConnect's contact list.

### Clearing all entries : `clear`

Clears all entries from ClubConnect.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

ClubConnect data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

ClubConnect data are saved automatically as a JSON file `[JAR file location]/data/clubconnect.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, ClubConnect will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause ClubConnect to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous ClubConnect home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action             | Format, Examples
-------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**            | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear**          | `clear`
**Delete**         | `delete INDEX` or `delete CONTACT_NAME`<br> e.g., `delete 3`, `delete john doe`
**Mass Delete**    | `mass_delete INDEX1 INDEX2 ... INDEXN`<br> e.g., `mass_delete 1 2 a`
**Edit**           | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**           | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Search**         | `search by/FIELD KEYWORD [MORE_KEYWORDS]`<br> e.g., `search by/Name Jake`
**List**           | `list`
**Add Event**      | `add_event n/EVENT_NAME d/EVENT_DESCRIPTION f/EVENT_START_DATE t/EVENT_END_DATE` <br> e.g., `add_event n/Meeting d/CS2103T Meeting f/2024-09-09 t/2024-09-10` 
**List Events**    | `list_events`
**Delete Event**   | `delete_event INDEX` or `delete_event EVENT_NAME`<br> e.g., `delete_event 1` or `delete_event meeting`
**Assign Event**   | `assign_event p/PERSON_INDEX ev/EVENT_INDEX` or `assign_event p/PERSON_NAME ev/EVENT_INDEX` or `assign_event p/PERSON_INDEX ev/EVENT_NAME` or `assign_event p/PERSON_NAME ev/EVENT_NAME` <br> e.g., `assign_event p/1 ev/2` or `assign_event p/Alice ev/2` or `assign_event p/1 ev/Meeting` or `assign_event p/Alice ev/Meeting`
**Unassign Event** | `unassign_event p/PERSON_INDEX ev/EVENT_INDEX` or `unassign_event p/PERSON_NAME ev/EVENT_INDEX` or `unassign_event p/PERSON_INDEX ev/EVENT_NAME` or `unassign_event p/PERSON_NAME ev/EVENT_NAME` <br> e.g., `unassign_event p/1 ev/2` or `unassign_event p/Alice ev/2` or `unassign_event p/1 ev/Meeting` or `unassign_event p/Alice ev/Meeting`
**Help**           | `help`
**Export**         | `export`
**Import**         | `import FILENAME`<br> e.g., `import contacts.csv`
