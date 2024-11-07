---
layout: page
title: User Guide
---

EventfulNUS is a **desktop app for managing contacts and events specifically for the Inter-Faculty Games hosted annually
at the National University of Singapore. While optimised for use via a Command Line Interface** (CLI), it also has the benefits of a Graphical User Interface (GUI). If you can type fast, you will certainly benefit from event organisation tasks being done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for EventfulNUS.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar eventfulnus.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com s/IFG r/friends r/owesMoney` : Adds a person named `John Doe` to the local database, with the given details.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

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
  e.g `n/NAME [r/ROLE]` can be used as `n/John Doe r/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[r/ROLE]…​` can be used as ` ` (i.e. 0 times), `r/friend`, `r/friend r/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

## Persons

### Viewing help : `help`

Shows a message explaining how to use some of the commands. Also contains a link to this guide.

![help message](images/helpMessage.png)

Format: `help`

### Adding a person: `add`

Adds a person to the database.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL [r/ROLE]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have zero or more roles.
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 r/criminal`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [r/ROLE]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing roles, the existing roles of the person will be removed i.e adding of roles is not cumulative.
* You can remove all the person’s roles by typing `r/` without
    specifying any roles after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower r/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing roles.

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


### Deleting a person : `delete`

Deletes the specified person from the database.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

## Events

### Adds an event to the database.

Format: `addevent sp/SPORT t/Faculty 1 t/Faculty 2 d/LocalDateTime v/Venue [pa/PARTICIPANTS]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
An event can have zero or more participants.
Note that the participants must be valid persons in the database.
</div>

Examples:
* `addevent sp/Chess t/COM t/BIZ d/2024 12 12 1800 v/USC pa/John`

### Edits an event in the database.

Format: `editevent INDEX sp/SPORT t/Faculty 1 t/Faculty 2 d/LocalDateTime v/Venue [pa/PARTICIPANTS]…​`

* Edits the event at the specified `INDEX`. The index refers to the index number shown in the displayed event list.
* The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing participants, the existing participants of the events will be removed i.e adding of participants is not cumulative.
* You can remove all the event's participants by typing `pa/` without
  specifying any participants after it.

Examples:
*  `edit 1 sp/Chess` Edits the sport of the event to be `Chess`.
*  `edit 2 sp/Basketball Women pa/` Edits the sport of the event to be `Basketball Women` and clears all existing participants.

### Deletes an event from the database.

Deletes the specified event from the database.

Format: `deleteevent INDEX`

* Deletes the event at the specified `INDEX`.
* The index refers to the index number shown in the displayed event list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `listevent` followed by `delete 2` deletes the 2nd event in the address book.

### Lists all events in the database.

Shows a list of all events in the address book.

Format: `listevent`

### Finds events by keywords.

Finds all events whose names or attributes contain any of the specified keywords (case-insensitive) and displays them as a list with index numbers.

Format: `findevent KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `usc` will match `Usc`
* The order of the keywords does not matter. e.g. `USC Chess` will match `Chess USC`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Utown Usc` will return Events containing `Utown Chess`, `Usc Table Tennis`

## More Features
### Clearing all entries : `clear`

Clears all entries from the database. But remember, they're gone forever.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

EventfulNUS data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

EventfulNUS data is saved automatically as a JSON file `[JAR file location]/data/eventfulnus.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Using Shortcuts `[coming in v2.0]`

To use EventfulNUS faster, you may find these shortcuts useful.

WARNING: For parsing of faculties / teams, only the shortcuts are accepted, not the full faculty names.
Faculties (Code - Faculty Name):
1. BIZ - Business
2. CDE - Design and Engineering
3. COM - Computing
4. DEN - Dentistry
5. FASS - Arts and Social Sciences
6. LAW - Law
7. MED - Medicine
8. NUSC - NUS College
9. SCI - Science
10. YNC - Yale-NUS College

INFO: For parsing of sports, both the shortcuts and full sport names are accepted.
Sports (Code - Sport Name):
1. BMT - Badminton
2. BBM - Basketball Men
3. BBW - Basketball Women
4. BDM - Bouldering Men
5. BDW - Bouldering Women
6. CHE - Chess
7. COB - Contact Bridge
8. DGB - Dodgeball
9. FBM - Floorball Men
10. FBW - Floorball Women
11. HBM - Handball Men
12. HBW - Handball Women
13. LOL - League of Legends
14. NET - Netball
15. REV - Reversi
16. SCM - Soccer Men
17. SCW - Soccer Women
18. SQH - Squash
19. SWM - Swimming Men
20. SMW - Swimming Women
21. TBT - Table Tennis
22. TCB - Tchoukball
23. TEN - Tennis
24. RUG - Touch Rugby
25. TKM - Track Men
26. TKW - Track Women
27. ULT - Ultimate Frisbee
28. VAL - Valorant
29. VBM - Volleyball Men
30. VBW - Volleyball Women

Branches of Committee Members (Code - Branch Name):
1. SPO - Sports
2. MKT - Marketing
3. PUB - Publicity

Positions of Committee Members (Code - Branch Name)
1. PD - Project Director
2. VPD - Vice Project Director
3. SD - Sports Director
4. VSD - Vice Sports Director
5. MEM - Member

Volunteer Roles (Code - Role Name):
1. PHOTO - Photographer
2. MC - Emcee
3. USH - Usher
4. LOG - Logistics
5. FA - First Aid
6. BMA - Booth Manner

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous EventfulNUS home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`
