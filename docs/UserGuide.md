---
layout: page
title: User Guide
---

VolunSync is a **desktop app for keeping track of volunteers and volunteering events, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the User Guide on your browser.<br>
   Some example commands you can try:

   * `???` : Lists all contacts.

   * `???` : Creates a new record for a volunteer named `John Doe` to the VolunSync database.
   
   * `???` : Deletes the volunteer with id `3` on the list of volunteers.

   * `???` : Creates a new record for an event named `Birthday Party` to the VolunSync database.
   
   * `???` : Deletes the event with id `3` from the list of events.

   * `reset` : Displays all volunteers and events in the database.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `???`

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `???` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `reset` and `exit`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Opens the webpage of the User Guide in your computer's default browser.

Should the browser fail to launch, displays a message explaining how to access the help page instead.

![help message](images/helpMessage.png)

Format: `help`


### Adding a volunteer: `???`

Adds a volunteer to the database.

Format: `???`

Examples:
* `???`
* `???`

### Adding an event: `???`

Adds an event to the database.

Format: `???`

Examples:
* `???`
* `???`

### Listing all volunteers and events : `reset`

Shows a list of all volunteers and events in the database.

Format: `reset`

### Deleting a volunteer : `???`

Deletes the specified volunteer from the database.

Format: `???`

* Deletes the volunteer with the specified `ID`.
* The index refers to the id number (shown in brackets) in the displayed volunteer list.
* The index **must be a non-negative integer** 0, 1, 2, …​
* Deleting a volunteer removes the volunteer from the list of participants of all events which the volunteer is involved in.

Examples:
* `reset` followed by `???` deletes the 2nd person in the address book.

[//]: # (* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.)

### Deleting an event : `???`

Deletes the specified event from the database.

Format: `???`

* Deletes the event with the specified `ID`.
* * The index refers to the id number (shown in brackets) in the displayed event list.
* The id **must be a non-negative integer** 0, 1, 2, …​
* Deleting an event causes that event to be removed from all participants' list of events which they are involved in.

Examples:
* `reset` followed by `???` deletes the 2nd event in the address book.

[//]: # (* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.)

### Adding a volunteer to an event's list of participants `???`

Adds a volunteer with the specified `ID` to the list of participants of the event with the specified `ID`.

Format: `???`

* The index refers to the id number (shown in brackets) in the displayed volunteer and event lists.
* The id **must be a non-negative integer** 0, 1, 2, …​

### Removing a volunteer from an event's list of participants `???`

Removes a volunteer with the specified `ID` to the list of participants of the event with the specified `ID`.

Format: `???`

* The index refers to the id number (shown in brackets) in the displayed volunteer and event lists.
* The id **must be a non-negative integer** 0, 1, 2, …​

### Listing all volunteers participating in an event `???`

Displays all volunteers participating in the event with the specified `ID` under the `Volunteers` display.

Format: `???`

* The index refers to the id number (shown in brackets) in the displayed event list.
* The index **must be a positive integer** 1, 2, 3, …​)

### Export database to a CSV file : `export`

Exports all records within the database to a CSV file.

Format : `export`


### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**:

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action                             | Format, Examples |
|------------------------------------|------------------|
| **Add Volunteer**                  | `???`            |
| **Delete Volunteer**               | `???`            |
| **Add Event**                      | `???`            |
| **Delete Event**                   | `???`            |
| **Add Volunteer to Event**         | `???`            |
| **Remove Volunteer from Event**    | `???`            |
| **List all Volunteers and Events** | `reset`          |
| **Export database to csv file**    | `export`         |
| **Help**                           | `help`           |
