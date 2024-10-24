---
layout: page
title: User Guide
---

VolunSync is a **desktop app for keeping track of volunteers and volunteering events, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, VolunSync can get your event and personnel management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-W12-2/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your VolunSync application.

1. Open your computer's command terminal:
    - On Windows: Press `Windows + R`, type `cmd`, and hit Enter.
    - On Mac/Linux: Open the Terminal from your Applications.
1. In the terminal, type `cd` followed by the path to the folder with the .jar file, then run the following command:
   java -jar volunsync.jar
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the User Guide on your browser.<br>
   Some example commands you can try:

    * `list` : Lists all volunteers and events in the VolunSync database.

    * `/v new n/ John Doe p/ 91234567 em/ john@gmail.com d/ 2024-02-02` : Creates a new record for a volunteer named `John Doe` to the VolunSync database.

    * `/v del 3` : Deletes the third volunteer on the list of volunteers.

    * `/e new n/Food collection l/NTUC d/2024-11-29 s/00:00 e/23:59 des/Collecting unsold food from NTUC for distribution` : Creates a new record for an event named `Food collection` to the VolunSync database.

    * `/e del 3` : Deletes the third event from the list of events.

    * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Items in square brackets are optional.<br>
  e.g [des/ DESCRIPTION]

* Commands without parameters (like `help`) ignore any additional input.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* When copying multi-line commands from this guide, make sure the spaces between lines remain intact, especially if you're using a PDF version.
</div>

### General Commands

### Viewing help : `help`

Opens the webpage of the User Guide in your computer's default browser.

Format: `help`

### Listing all volunteers and events : `list`

Shows a list of all volunteers and events in the database.

Format: `list`

Examples:
* `list` would show the full list of events and volunteers

### Export database to a CSV file : `export`

Exports all records within the database to a CSV file.

Format : `export`

* After running the export command, check the `/output` folder located in the same directory as your .jar file. You’ll find two CSV files: events.csv and volunteers.csv.

### Exiting the program : `exit`

Exits the program.

Format: `exit`

## Volunteer-related Commands

### Adding a volunteer: `/v new`

Adds a volunteer to the database.

Format: `/v new n/ NAME p/ PHONE_NUMBER em/ EMAIL d/ AVAILABLE_DATE s/ START_TIME e/ END_TIME`

Examples:
* `/v new n/ John Doe p/ 91234567 em/ john@gmail.com d/ 2024-02-02 s/ 00:10 e/ 23:59`
* `/v new n/ Ellen Joe p/ 81234567 em/ ellen@gmail.com d/ 2024-05-23 s/ 12:00 e/ 15:59`

### Locating volunteers by name : `/v find`

Find volunteers whose name contains the given keyword.

Format: `/v find KEYWORD`

* The search is case-insensitive
* Only the name is searched
* The searching algorithm returns volunteers with any part of their name matching the `KEYWORD`
    * e.g. `Joe` will match `Joelle`, `Enjoeline` and `Joe`

Examples:
* `/v find A` returns `Alice Wong`, `Bob Tan` and `David Ng`

### Deleting a volunteer : `/v del`

Deletes the specified volunteer from the database.

Format: `/v del VOLUNTEER_INDEX`

* Deletes the volunteer at the specified `VOLUNTEER_INDEX`.
* The index refers to the number before the volunteer's name in the displayed volunteer list.
* The index **must be a non-negative integer** 1, 2, 3, …​

<div markdown="span" class="alert alert-primary">:bulb: **Note:**
Deleting a volunteer removes the volunteer from the list of participants of all events which the volunteer
is involved in.
</div>

Examples:
* `/v del 2` deletes the second volunteer in the volunteer list.

### Assigning a volunteer to event: `assign`

Adds a volunteer with the specified `VOLUNTEER_INDEX` to the list of participants of the event with the specified `EVENT_INDEX`.

Format: `assign v/ VOLUNTEER_INDEX e/ EVENT_INDEX`

* The index refers to the number on the left of the event and volunteer's name in their respective lists.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `assign v/ 1 e/ 2` assigns the first volunteer on the volunteer list to the list of participants of the second event on the event list.

### Removing a volunteer from an event: `unassign`

Removes a volunteer with the specified `VOLUNTEER_INDEX` to the list of participants of the event with the specified `EVENT_INDEX`.

Format: `unassign v/ VOLUNTEER_INDEX e/ EVENT_INDEX`

* The index refers to the number on the left of the event and volunteer's name in their respective lists.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `unassign v/ 1 e/ 2` removes the first volunteer on the volunteer list from the list of participants of the second event on the event list.


## Event-related Commands

### Adding an event: `/e new`

Adds an event to the database.

Format: `/e new n/ EVENT_NAME s/ START_TIME e/ END_TIME d/ DATE l/ LOCATION [des/ DESCRIPTION]`

Examples:
* `/e new n/ Buffet Lunch s/ 12:00 e/ 15:00 d/ 2024-08-09 l/ Blk 123 Woodlands Avenue 12`
* `/e new n/ Coding Exam s/ 19:00 e/ 21:00 d/ 2024-12-12 l/ LT 28 des/ Final Exam`

### Locating events by name : `/e find`

Find events with names containing the given keyword.

Format: `/e find KEYWORD`

* The search is case-insensitive.
* Only the name is searched.
* The searching algorithm returns events with any part of their name matching the `KEYWORD`
    * e.g. `Group` will match `Group Project`, `Grouping Task` and `Group`.

Examples:
* `/v find e` returns `Beach Cleanup` and `Tree Planting`

### Deleting an event : `/e del `

Deletes the specified event from the database.

Format: `/e del EVENT_INDEX`

* Deletes the event with the specified `EVENT_INDEX`.
* The index refers to the number before the event's name in the displayed event list.
* The index **must be a non-negative integer** 1, 2, 3, …​

<div markdown="span" class="alert alert-primary">:bulb: **Note:**
Deleting an event causes that event to be removed from all participants' list of events which they are involved in.
</div>

Examples:
* `/e del 2` deletes the second event in the event list.

### Listing all volunteers participating in an event: `/e view`

Displays all volunteers participating in the event at the specified `INDEX` under the `Volunteers` display.

Format: `/e view EVENT_INDEX`

* The index refers to the number on the left of the event and volunteer's name in their respective lists.
* The index **must be a positive integer** 1, 2, 3, …​

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: Can I undo an action that I just made?<br>
**A**: Currently VolunSync does not support the `undo` function, but we are planning to implement it in the future so stay tuned for the update!

--------------------------------------------------------------------------------------------------------------------

## Known Issues

1. **Multi-Screen Setup Issue**  
   **Problem:** If you move the application to a secondary screen and later switch to using only the primary screen, the GUI may open off-screen when you restart the app.  
   **Solution:**
    - To fix this, locate and delete the `preferences.json` file, which stores the app's window position.
    - After deleting the file, restart the application, and the GUI will open correctly on the primary screen.

2. **Help Window Remaining Minimized**  
   **Problem:** If you minimize the Help Window and run the `help` command (or use the `Help` menu or press the `F1` key), the Help Window will remain minimized instead of reopening.  
   **Solution:**
    - Manually restore the Help Window from your taskbar or dock.
    - Alternatively, close the minimized window and run the `help` command again to open a new Help Window.

These known issues are actively being worked on, and we are continuously improving VolunSync to provide a better experience. Please stay tuned for future updates!

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action                                       | Format, Examples                                                                                                                                                                                                                                                                   |
|----------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Volunteer**                            | `/v new n/ NAME p/ PHONE_NUMBER em/ EMAIL d/ AVAILABLE_DATE s/ START_TIME e/ END_TIME`<br/>`e.g. /v new n/ John Doe p/ 91234567 em/ john@gmail.com d/ 2024-02-02 s/ 00:10 e/ 23:59`                                                                                                |
| **Delete Volunteer**                         | `/v del VOLUNTEER_INDEX`<br/>`e.g. /v del 1`                                                                                                                                                                                                                                           |
| **Find Volunteer**                           | `/v find KEYWORD`<br/>`e.g. /v find Yeoh`                                                                                                                                                                                                                                          |
| **Add Event**                                | `/e new n/ EVENT_NAME s/ START_TIME e/ END_TIME d/ DATE l/ LOCATION [des/ DESCRIPTION]`<br/>`e.g. /e new n/ Coding Exam s/ 19:00 e/ 21:00 d/ 2024-12-12 l/ LT 28 des/ Final Exam`<br/>`e.g. /e new n/ Buffet Lunch s/ 12:00 e/ 15:00 d/ 2024-08-09 l/ Blk 123 Woodlands Avenue 12` |
| **Delete Event**                             | `/e del EVENT_INDEX`<br/>`e.g. /e del 1`                                                                                                                                                                                                                                           |
| **Find Event**                               | `/e find KEYWORD`<br/>`e.g. /e find Forest`                                                                                                                                                                                                                                        |
| **Add Volunteer to Event**                   | `assign v/ VOLUNTEER_INDEX e/ EVENT_INDEX`<br/>`e.g. assign v/ 1 e/ 2`                                                                                                                                                                                                             |
| **Remove Volunteer from Event**              | `unassign v/ VOLUNTEER_INDEX e/ EVENT_INDEX`<br/>`e.g. unassign v/ 1 e/ 2`                                                                                                                                                                                                         |
| **View all Volunteers involved in an Event** | `view e/ EVENT_INDEX`<br/>`e.g. view e/ 1`                                                                                                                                                                                                                                         |
| **List all Volunteers and Events**           | `list`                                                                                                                                                                                                                                                                             |
| **Export database to csv file**              | `export`                                                                                                                                                                                                                                                                           |
| **Help**                                     | `help`                                                                                                                                                                                                                                                                             |
| **Exit**                                     | `exit`                                                                                                                                                                                                                                                                             |
