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

1. Ensure you have Java `17` or above installed in your computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-F09-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your ClubConnect.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar clubconnect.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `list` : Lists all contacts.

    * `add n/John Doe p/98765432 e/johnd@example.com a/John Street, block 123, #01-01` : Adds a contact named `John Doe` to ClubConnect's contact list.

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

<box type="warning" seamless>

**Caution:**
All commands that use the indices shown in the displayed contact list can also be used in the displayed event list but the displayed contact list in this case refers to the last viewed displayed contact list (and vice versa).
</box>

--------------------------------------------------------------------------------------------------------------------

## Person Commands

### Listing all persons : `list`

Shows a list of all persons stored in ClubConnect's contact list.

Format: `list`

![result for 'list'](images/list.png)


### Adding a person: `add`

Adds a person to ClubConnect's contact list.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

* `NAME` must start with an alphabet and should only contain alphabets and numbers.
* `PHONE_NUMBER` should only contain numbers, and needs to be at least 3 digits long.
* `EMAIL` should follow the structure `local-part@domain`.
* `ADDRESS` must not contain only white spaces.

<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0)
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`<br>
  ![result for 'adding john doe'](images/addJohnDoeResult.png)


### Editing a person : `edit`

Edits an existing person in ClubConnect's contact list.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`.
* `INDEX` refers to the index number shown in the displayed contact list.
* `INDEX` **must be a positive integer** 1, 2, 3, …​
* `NAME` must start with an alphabet and should only contain alphabets and numbers.
* `PHONE_NUMBER` should only contain numbers, and needs to be at least 3 digits long.
* `EMAIL` should follow the structure `local-part@domain`.
* `ADDRESS` must not contain only white spaces.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.

<box type="tip" seamless>

**Tip:** Displayed contact list refers to the contact list that is displayed on screen, and does not include contacts that are not shown on screen.
</box>

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.<br>
   ![result for 'edit alex yeoh email'](images/editAlexYeohResult.png)


### Deleting a person : `delete`

Deletes the specified person from ClubConnect's contact list.

Format: `delete INDEX` / `delete CONTACT_NAME`

* Deletes the person at the specified `INDEX` / with name `CONTACT_NAME`.
* `CONTACT_NAME` refers to the name of the contact (case-insensitive).
* `INDEX` refers to the index number shown in the displayed contact list.
* `INDEX` **must be a positive integer** 1, 2, 3, …​

<box type="tip" seamless>

**Tip:** Displayed contact list refers to the contact list that is displayed on screen, and does not include contacts that are not shown on screen.
</box>

Examples:
* `list` followed by `delete 2` deletes the 2nd person in ClubConnect's contact list.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.
* `delete john doe` will delete the contact with name `john doe` (case-insensitive).<br>
  ![result for 'delete john doe'](images/deleteJohnDoeResult.png)


### Deleting multiple people : `mass_delete`
Deletes multiple specified contacts from ClubConnect's contact list using their displayed indices.

Format: `mass_delete INDEX1 INDEX2 ... INDEXN`

* Deletes the persons at the specified indices.
* Each index refers to the index number shown in the displayed contact list.
* All indices **must be a positive integer** 1, 2, 3, …​
* Invalid indices will be filtered out.

<box type="tip" seamless>

**Tip:** Displayed contact list refers to the contact list that is displayed on screen, and does not include contacts that are not shown on screen.
</box>

Examples:
* `list` followed by `mass_delete 1 2` deletes the 1st and 2nd persons in ClubConnect's contact list.
* `find Betsy` followed by `mass_delete 1 3 a` deletes the 1st and 3rd persons in the results of the find command.<br>
  ![result for 'mass delete 1 and 2'](images/massDeleteResult.png)


### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Substrings will be matched e.g. `Alexander` will be shown with the keyword `Alex`
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
* e.g. The keywords `Hans Bo` will return `Hans Gruber`, `Bo Yang`

<box type="warning" seamless>

**Caution:**<br>
When multiple fields are specified, the order of the different fields in the command is ignored.<br>
Instead, the fields will be prioritised as follows(in decreasing order of priority):<br>
&nbsp;&nbsp;&nbsp;&nbsp;Address -> Email -> Event -> Name -> Phone -> Tags<br>
The field with the highest priority will be searched, and the rest will be ignored.
</box>

Examples:
* `search a/street avenue`
* `search e/gmail exampleEmail`
* `search n/alice bob charlie`
* `search p/98765432 12345678`

* `search t/friend colleague`<br>  
  ![result for search t/friends](images/searchTagsFriendsResult.png)

The `search` command can also be used to find contacts assigned to an event.

Format: `search ev/{EVENT_NAME}`

* Contacts who are assigned to the event entered will be displayed as the search result.
* However, the search for events is an exact search criteria, where all characters must match.
* Any number of words used with the event prefix `ev/` is treated as a single keyword.

Examples:
* `search ev/Orbital Workshop`<br>  
  ![result for search ev/Orbital Workshop](images/searchEventOrbitalWorkshop.png)


### Exporting persons: `export`

Exports all persons in ClubConnect into a csv file `[JAR file location]/data/ExportedContacts.csv`.

Format: `export`<br>
![result for 'export contacts'](images/exportContactsResult.png)


### Importing persons: `import`

Reads the specified file to import from and adds the persons to ClubConnect.

Format: `import FILENAME`

* The file must be located in the data folder of the application directory.
* The specified file name must exactly match the name of the file to import from.
* The file name must end with `.csv`.

<box type="tip" seamless>

**Tip:** The import command is designed to work specifically with files exported by ClubConnect. If you export data using the export command, you can re-import it as needed using the same file.
</box>

<box type="warning" seamless>

**Caution:**
Avoid editing or modifying the exported file. Any changes to its structure, format, or data may lead to errors during import, as ClubConnect expects the file to retain the exact format of the exported data.
</box>

Examples:
* `import contacts.csv` adds persons stored in `contacts.csv` to ClubConnect.<br>
  ![result for 'import contacts'](images/importContactsResult.png)

--------------------------------------------------------------------------------------------------------------------

## Event Commands

### Listing all events : `list_events`

Shows a list of all events stored in ClubConnect's event list.

Format: `list_events`<br>
![result for 'list events'](images/listEventsResult.png)


### Adding an event: `add_event`

Adds an event to ClubConnect's event list.

Format: `add_event n/EVENT_NAME d/EVENT_DESCRIPTION f/EVENT_START_DATE t/EVENT_END_DATE`

* The date inputs must be in the format `YYYY-MM-DD`.
* `EVENT_NAME` must start with an alphabet and should only contain alphabets and numbers.
* `EVENT_DESCRIPTION` cannot contain only white space.

<box type="info" seamless>

Currently, new events must have unique event names. Events that have the same name as existing ones cannot be added. <br>
Look out for future updates where we will fix this issue! More information can be found in [Planned Enhancement 1](DeveloperGuide.md#appendix-planned-enhancements).
</box>

Examples:
* `add_event n/Meeting d/CS2103T Meeting f/2024-09-09 t/2024-09-10`
* `add_event n/Workshop d/Orbital Workshop f/2024-10-01 t/2024-10-10`<br>
  ![result for 'add event orbital workshop'](images/addEventResult.png)


### Editing an event: `edit_event`

Edits the details of an existing event in the address book.

Format: `edit_event INDEX n/EVENT_NAME d/EVENT_DESCRIPTION f/EVENT_START_DATE t/EVENT_END_DATE`

* `INDEX` refers to the index number shown in the displayed event list.
* `INDEX` **must be a positive integer** 1, 2, 3, …​
* `EVENT_NAME` must start with an alphabet and should only contain alphabets and numbers.
* `EVENT_DESCRIPTION` cannot contain only white space.
* The date inputs must be in the format `YYYY-MM-DD`.
* At least one of the optional fields (`EVENT_NAME`, `EVENT_DESCRIPTION`, `EVENT_START_DATE`, `EVENT_END_DATE`) must be provided to make changes.

<box type="tip" seamless>

**Tip:** Displayed event list refers to the event list that is displayed on screen, and does not include events that are not shown on screen.
</box>

Examples:
* `edit_event 1 n/Updated Meeting d/Updated description f/2024-10-02 t/2024-10-11`
* `edit_event 2 d/Changed description`
* `edit_event 3 f/2024-11-01 t/2024-11-05`<br>
  ![result for 'edit event 1'](images/editEventResult.png)


### Deleting an event: `delete_event`

Deletes the specified event from ClubConnect's event list.

Format: `delete_event INDEX` / `delete_event EVENT_NAME`

* Deletes the event at the specified `INDEX` / with event name `EVENT_NAME`.
* `EVENT_NAME` refers to the name of the event (case-insensitive).
* `INDEX` refers to the index number shown in the displayed event list.
* `INDEX` **must be a positive integer** 1, 2, 3, …​

<box type="tip" seamless>

**Tip:** Displayed event list refers to the event list that is displayed on screen, and does not include events that are not shown on screen.
</box>

Examples:
* `list_events` followed by `delete_event 2` deletes the 2nd event in ClubConnect's event list.
* `delete_event meeting` will delete the event with event name `meeting` (case-insensitive).<br>
  ![result for 'delete event 2'](images/deleteEventResult.png)


### Assigning an event: `assign_event`

Assigns a specified event to a specified person stored in ClubConnect's contact list.

Format: `assign_event p/PERSON_INDEX ev/EVENT_INDEX` / `assign_event p/PERSON_NAME ev/EVENT_INDEX` / `assign_event p/PERSON_INDEX ev/EVENT_NAME` / `assign_event p/PERSON_NAME ev/EVENT_NAME`

* Assigns the event specified by `EVENT_INDEX` or `EVENT_NAME` to the person specified by `PERSON_INDEX` or `PERSON_NAME`.
* `EVENT_INDEX` / `PERSON_INDEX` refers to the index number shown in the displayed event / contact list.
* `EVENT_INDEX` and `PERSON_INDEX` **must be a positive integer** 1, 2, 3, …​
* `EVENT_NAME` / `PERSON_NAME` refers to the name of the event / person (case-insensitive).

<box type="tip" seamless>

**Tip:** Displayed contact / event list refers to the contact / event list that is displayed on screen, and does not include contacts / events that are not shown on screen.
</box>

Examples:
* `assign_event p/1 ev/2` will assign the 2nd event in ClubConnect's event list to the 1st person in ClubConnect's contact list.
* `assign_event p/Alice ev/2` will assign the 2nd event in ClubConnect's event list to a person named `Alice` (case-insensitive) in ClubConnect's contact list.
* `assign_event p/1 ev/Meeting` will assign an event named `Meeting` (case-insensitive) in ClubConnect's event list to the 1st person in ClubConnect's contact list.
* `assign_event p/Alice ev/Meeting` will assign an event named `Meeting` (case-insensitive) in ClubConnect's event list to a person named `Alice` (case-insensitive) in ClubConnect's contact list.<br>
  ![result for 'assign event 2 to person 1'](images/assignEventResult.png)


### Unassigning an event: `unassign_event`

Unassigns a specified event from a specified person stored in ClubConnect's contact list.

Format: `unassign_event p/PERSON_INDEX ev/EVENT_INDEX` / `unassign_event p/PERSON_NAME ev/EVENT_INDEX` / `unassign_event p/PERSON_INDEX ev/EVENT_NAME` / `unassign_event p/PERSON_NAME ev/EVENT_NAME`

* Unassigns the event specified by `EVENT_INDEX` or `EVENT_NAME` from the person specified by `PERSON_INDEX` or `PERSON_NAME`.
* `EVENT_INDEX` / `PERSON_INDEX` refers to the index number shown in the displayed event / contact list.
* `EVENT_INDEX` and `PERSON_INDEX` **must be a positive integer** 1, 2, 3, …​
* `EVENT_NAME` / `PERSON_NAME` refers to the name of the event / person (case-insensitive).

<box type="tip" seamless>

**Tip:** Displayed contact / event list refers to the contact / event list that is displayed on screen, and does not include contacts / events that are not shown on screen.
</box>

Examples:
* `unassign_event p/1 ev/2` will unassign the 2nd event in ClubConnect's event list from the 1st person in ClubConnect's contact list.
* `unassign_event p/Alice ev/2` will unassign the 2nd event in ClubConnect's event list from a person named `Alice` (case-insensitive) in ClubConnect's contact list.
* `unassign_event p/1 ev/Meeting` will unassign an event named `Meeting` (case-insensitive) in ClubConnect's event list from the 1st person in ClubConnect's contact list.
* `unassign_event p/Alice ev/Meeting` will unassign an event named `Meeting` (case-insensitive) in ClubConnect's event list from a person named `Alice` (case-insensitive) in ClubConnect's contact list.<br>
  ![result for 'unassign event 2 from person 1'](images/unassignEventResult.png)


--------------------------------------------------------------------------------------------------------------------

## General Commands

### Viewing help : `help`

Shows a message explaining how to access the help page.

Format: `help`<br>
![help message](images/helpMessage.png)


### Clearing all entries : `clear`

Clears all entries from ClubConnect.

Format: `clear`<br>
![clear message](images/clearMessage.png)


### Exiting the program : `exit`

Exits the program.

Format: `exit`


--------------------------------------------------------------------------------------------------------------------

### Saving the data

ClubConnect data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.


### Editing the data file

ClubConnect data is saved automatically as a JSON file `[JAR file location]/data/clubconnect.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, ClubConnect will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause ClubConnect to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Install the app on another computer and overwrite the empty data file it creates with the file that contains the data of your previous ClubConnect home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again. The `preferences.json` file can be found in the same folder that you put the `clubconnect.jar` file in.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.
3. **When displaying the contact list**, there appears to be an empty box at the bottom of the app. **When displaying the event list**, there appears to be an empty box directly below the status message box. We plan to fix this issue in future releases.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action             | Format, Examples
-------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**List**           | `list`
**Add**            | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Edit**           | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Delete**         | `delete INDEX` or `delete CONTACT_NAME`<br> e.g., `delete 3`, `delete john doe`
**Mass Delete**    | `mass_delete INDEX1 INDEX2 ... INDEXN`<br> e.g., `mass_delete 1 2`
**Find**           | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Search**         | `search {FIELD_PREFIX}/KEYWORD [MORE_KEYWORDS]`<br> e.g., `search n/ Jake`
**Export**         | `export`
**Import**         | `import FILENAME`<br> e.g., `import contacts.csv`
**List Events**    | `list_events`
**Add Event**      | `add_event n/EVENT_NAME d/EVENT_DESCRIPTION f/EVENT_START_DATE t/EVENT_END_DATE` <br> e.g., `add_event n/Meeting d/CS2103T Meeting f/2024-09-09 t/2024-09-10`
**Edit Event**     | `edit_event INDEX n/EVENT_NAME d/EVENT_DESCRIPTION f/EVENT_START_DATE t/EVENT_END_DATE` <br> e.g., `edit_event 1 n/Updated Meeting d/Updated description f/2024-10-02 t/2024-10-11`
**Delete Event**   | `delete_event INDEX` or `delete_event EVENT_NAME`<br> e.g., `delete_event 1` or `delete_event meeting`
**Assign Event**   | `assign_event p/PERSON_INDEX ev/EVENT_INDEX` or `assign_event p/PERSON_NAME ev/EVENT_INDEX` or `assign_event p/PERSON_INDEX ev/EVENT_NAME` or `assign_event p/PERSON_NAME ev/EVENT_NAME` <br> e.g., `assign_event p/1 ev/2` or `assign_event p/Alice ev/2` or `assign_event p/1 ev/Meeting` or `assign_event p/Alice ev/Meeting`
**Unassign Event** | `unassign_event p/PERSON_INDEX ev/EVENT_INDEX` or `unassign_event p/PERSON_NAME ev/EVENT_INDEX` or `unassign_event p/PERSON_INDEX ev/EVENT_NAME` or `unassign_event p/PERSON_NAME ev/EVENT_NAME` <br> e.g., `unassign_event p/1 ev/2` or `unassign_event p/Alice ev/2` or `unassign_event p/1 ev/Meeting` or `unassign_event p/Alice ev/Meeting`
**Help**           | `help`
**Clear**          | `clear`
**Exit**           | `exit`

