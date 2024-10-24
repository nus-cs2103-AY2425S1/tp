# HireMe User Guide

HireMe is a **desktop app for managing internship applications, optimized for use via a Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, HireMe can get your internship tracking tasks done faster than traditional GUI apps.

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed on your computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-W09-3/tp/releases/tag/v1.3).

1. Copy the file to the folder you want to use as the _home folder_ for your HireMe.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar hireme.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`/help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `/list` : Lists all internship applications.

    * `/a n/Google r/Software Engineer Intern e/google@gmail.com d/16/09/24` : Adds an internship application at Google to HireMe.

    * `/d 1` : Deletes the 1st internship application shown in the current list.

    * `/clear` : Deletes all internship applications.

    * `/exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME r/ROLE`, `r/ROLE n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `/help`, `/list`, `/exit`, and `/clear`) will be ignored.<br>
  e.g. if the command specifies `/help 123`, it will be interpreted as `/help`.

</box>

### Viewing help : `/help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `/help`

### Adding an internship application: `/a`

Adds an internship application to HireMe.

Format: `/a n/COMPANY_NAME r/ROLE e/EMAIL d/DATE`

Examples:
* `/a n/Google r/Software Engineer Intern e/google@gmail.com d/16/09/24`
* `/a n/Facebook r/Data Scientist Intern t/high_priority e/fb@example.com d/21/10/24`

### Listing all internship applications : `/list`

Shows a list of all internship applications in HireMe.

Format: `/list`

### Locating applications by company name: `/f`

Finds applications whose company names contain the given pattern of characters.

Format: `/f PATTERN`

* The search is case-insensitive. e.g. `goo` will match `Google`.
* The searches are made only for company names.
* If you search for `oo`, then you will see a list of all the internship applications that you have applied for
  whose companies have `oo` in their names.

Examples:
* `/f Goo` returns `Google` and `Google 2`
* `/f face` returns `Facebook`
* `/f oO` returns `Google`, `Google 2`, `Facebook`

### Changing the status of an internship application : `/accept`, `/pending`, `/reject`

Updates the status of the specified internship application to `ACCEPTED`, `PENDING`, or `REJECTED`.

Formats:
- `/accept INDEX`: Changes the status to `ACCEPTED`.
- `/pending INDEX`: Changes the status to `PENDING`.
- `/reject INDEX`: Changes the status to `REJECTED`.

* The `INDEX` refers to the index number shown in the displayed internship application list.
* The `INDEX` **must be a positive integer** 1, 2, 3, …​

Examples:
* `/list` followed by `/accept 2` marks the 2nd application in the list as accepted.
* `/pending 3` changes the 3rd application in the current list to pending.
* `/reject 1` rejects the 1st application in the list.

### Deleting an internship application : `/d`

Deletes the specified internship application from HireMe.

Format: `/d INDEX`

* Deletes the internship application at the specified `INDEX`.
* The index refers to the index number shown in the displayed internship application list.
* The index **must be a positive integer** 1, 2, 3, …​
* You cannot delete an internship application whose index number does not exist. If you only have 3 internship
applications then the last internship application has an index number of 3. Thus, you are not able to delete the fourth
internship application as it does not exist.

Examples:
* `/list` followed by `/d 2` deletes the 2nd application in the list.
* `/f Google` followed by `/d 1` deletes the 1st application in the results of the `/f` command.
* `/list` followed by `/d 4` when you only have 3 internship applications returns `The internship application index provided is invalid`

### Clearing all entries : `/clear`

[!WARNING]
Clears all entries from HireMe. This removes all the internship applications that you have been tracking. You will not
be able to revert and get back your saved data once this command is executed.

Format: `/clear`

### Exiting the program : `/exit`

Exits the program.

Format: `/exit`

### Saving the data

HireMe data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

HireMe data are saved automatically as a JSON file `[JAR file location]/data/hireme.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file make its format invalid, HireMe will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause HireMe to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `/a n/COMPANY_NAME r/ROLE e/EMAIL d/DATE` <br> e.g., `/a n/Google r/Software Engineer Intern e/google@gmail.com d/16/09/24`
**Clear**  | `/clear`
**Delete** | `/d INDEX`<br> e.g., `/d 3`
**Find**   | `/f KEYWORD [MORE_KEYWORDS]`<br> e.g., `/f Google Facebook`
**List**   | `/list`
**Help**   | `/help`
**Accept** | `/accept INDEX`<br> e.g., `/accept 2`
**Pending**| `/pending INDEX`<br> e.g., `/pending 3`
**Reject** | `/reject INDEX`<br> e.g., `/reject 1`
