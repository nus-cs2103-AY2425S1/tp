---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# FindingbrUdders User Guide

FindingbrUdders is a **desktop app for managing contacts and scheduling meetings, optimized for use via a Command Line Interface (CLI)**. The Graphical User Interface (GUI) displays contact details systematically, in a more human-readable format, while still allowing for fast typists to utilise the CLI and type out the desired commands.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer. 

    You can check the version by running `java -version` in the command terminal. <br>
    **Windows**: You can access the command terminal by pressing `Win + R` and typing `cmd` in the dialog box that appears. Press `Enter` to open the command terminal. <br>
    **Mac**: You can access the command terminal by pressing `Cmd + Space` and typing `Terminal` in the search bar that appears. Press `Enter` to open the command terminal. <br>

    To run any commands, type them in the command terminal and press `Enter`.

2. If you do not have Java installed, you can download it from [here](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html). 

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-F08-3/tp/releases/).

    Scroll to the very bottom of the page to find the latest release. Under the `Assets` section, download the `.jar` file by clicking on it.

1. Copy the file to the folder you want to use as the _home folder_ for your FindingbrUdders app. This can be any folder on your computer, it can also be an empty folder.

1. Right click on the `.jar` file and select `Open with` and then `Java(TM) Platform SE binary`. 

   Alternatively, you can also run the `.jar` file by opening a command terminal, typing `cd` into the folder you put the jar file in, and using the `java -jar findingbrudders.jar` command to run the application.

   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data to help you get started.<br>

![Ui](images/Ui.png) 

1. Type the command in the command box at the bottom and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 r/mUdder m/cs` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all Udders.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info">

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME`

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help` 💡

Shows a message via a popup window explaining how to access the help page.

![help message](images/helpMessage.png)

**Format:** `help`


### Adding an Udder: `add` 🐄

Adds an Udder to the address book.

**Format:** `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS r/ROLE m/MAJOR [t/TAG]…​`

<box type="tip">

**Tip:** An Udder can have any number of tags (including 0)
</box>

**Examples:**
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 r/brUdder m/bza`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 r/mUdder m/ceg t/potential connection`

### Listing all Udders : `list` 📜

Shows a list of all Udders in the address book.

**Format:** `list`

### Listing meetings with all Udders : `meetings` 📅

Shows a list of all meetings with all Udders, arranged in chronological order.

**Format:** `meetings`

### Editing an Udder : `edit` ✏️

Edits an existing Udder in the address book.

**Format:** `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the Udder at the specified `INDEX`. The index refers to the index number shown in the displayed Udder list.
* When editing tags, the existing tags of the Udder will be removed i.e adding of tags is not cumulative.
* You can remove all the Udder’s tags by typing `t/` without
    specifying any tags after it.

**Examples:**
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st Udder to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd Udder to be `Betsy Crower` and clears all existing tags.

### Scheduling a meeting with an Udder : `schedule` 🗓️

Schedules a meeting with an Udder from the specified start time to end time, at the location.

**Format:** `schedule UDDER_INDEX st/YYYY-MM-DD HH:MM et/YYYY-MM-DD HH:MM l/LOCATION`

* Automatically detects any clash in meetings with other Udders.

**Examples:**
*  `schedule 10 st/2002-12-25 00:00 et/2002-12-25 23:59 l/Gardens of Eden` schedules a meeting with the 10th Udder starting from `25th December 2002, 00:00 a.m.` and ending at `25th December 2002, 11:59 p.m.`, at `Gardens of Eden`.

### Locating Udders by keywords: `find` 🔍

Finds Udders by specified keywords for each field.

**Format:** `find [n/]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* Udders matching all keywords will be returned. e.g. `bob` will match `bobby`

**Examples:**
* `find John` returns `johnny` and `John Doe`<br>

![result for 'find alex david'](images/findCommand.png)

### Deleting an Udder : `delete` ❌

Deletes the specified Udder from the address book.

**Format:** `delete INDEX`

* Deletes the Udder at the specified `INDEX`.
* The index refers to the index number shown in the displayed Udders list.

**Examples:**
* `list` followed by `delete 2` deletes the 2nd Udder in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st Udder in the results of the `find` command.

### Clearing all Udders : `clear` 🧹

Clears all Udders from the Udders List.

**Format:** `clear`

### Exiting the program : `exit` 👋

Exits the program.

**Format:** `exit`

### Saving the data

Udder data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

Udder data are saved automatically as a JSON file `[JAR file location]/data/findingbrudders.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning">

**Caution:**
If your changes to the data file makes its format invalid, FindingbrUdders will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it. You can do this by copying the `findingbrudders.json` file to a different folder.<br>
Furthermore, certain edits can cause the FindingbrUdders to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Finding Udders online via Cloud `[coming in v2.0]` ☁️

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my Udder data to anudder(pun intended) Computer?<br>
**A**: Install the app in the other computer as per the Quick Start guide. Copy the `findingbrudders.json` file from the previous computer to the new computer. Replace the empty `findingbrudders.json` file in new computer with the `findingbrudders.json` file from the previous computer. Your `findingbrudders.json` file is located in the `data` folder of the directory where the `findingbrudders.jar` file is located.

**Q**: Can I undo a command in FindingbrUdders?<br>
**A**: Unfortunately, there is no undo feature available at this time. Please double-check your inputs before executing any commands, especially for irreversible actions like deleting a contact.

**Q**: Can I change the location of the saved findingbrudders.json file?<br>
**A**: The location of the findingbrudders.json file is automatically set to the folder where the .jar file is stored. If you wish to change it, you need to move the .jar file to the desired folder, as the data file will follow.


--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. 
Solution: delete the `preferences.json` file created by the application before running the application again. 
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. 
Solution: manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS r/ROLE m/MAJOR [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 m/is r/brUdder t/friend`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [r/ROLE] [m/MAJOR] [t/TAG]…​`<br> e.g., `edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find [n/KEYWORD] [p/KEYWORD] [e/KEYWORD] [a/KEYWORD] [r/KEYWORD] [m/KEYWORD] [t/KEYWORD]…​`<br> e.g., `find n/James Jake`
**Schedule**   | `schedule UDDER_INDEX st/YYYY-MM-DD HH:MM et/YYYY-MM-DD HH:MM l/LOCATION`
**List**   | `list`
**Meetings**   | `meetings`
**Help**   | `help`
