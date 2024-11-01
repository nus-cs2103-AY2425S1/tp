# HireMe User Guide

HireMe is a **desktop application for managing internship applications, optimized for use via a Command Line Interface** (CLI)
while still having the benefits of a Graphical User Interface (GUI).
If you can type fast, HireMe can get your internship tracking tasks done faster than traditional GUI apps.

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed on your computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-W09-3/tp/releases/tag/v1.5).

1. Copy the file to the folder you want to use as the _home folder_ for your HireMe.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar hireme.jar` command to run the application.<br>
   
    <br>A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. (e.g. typing **`/help`** and pressing Enter will open the help window.)<br>
   Some example commands you can try:

    * `/list` : Lists all your internship applications.

    * `/add n/Google r/Software Engineer Intern e/google@gmail.com d/16/09/24` : Adds an internship application at Google to HireMe.

    * `/delete 1` : Deletes the 1st internship application shown in the current list.

    * `/clear` : Deletes all your internship applications.

    * `/chart` : Shows a pie chart with your applications' statistics.

    * `/exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME r/ROLE`, `r/ROLE n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `/help`, `/list`, `/exit`, `/clear` and `/chart`) will be ignored.<br>
  (e.g. if the command specifies `/help 123`, it will be interpreted as `/help`.)

</box>

### Viewing help : `/help`

Shows a message explaining how you can access the help page.

![help message](images/helpMessage.png)

Format: `/help`

<br>

### Adding an internship application: `/add`

Adds your internship application to HireMe.

Format: `/add n/COMPANY_NAME r/ROLE e/EMAIL d/DATE`

* The `DATE` must be within the year 2000 and 2099. It also cannot be a future date which means that the date used must be before today or today's date.
* It must be in the format `dd/mm/yy`.

Examples:
* `/add n/Google r/Software Engineer Intern e/google@gmail.com d/16/09/24`
* `/add n/Facebook r/Data Scientist Intern e/fb@example.com d/21/10/24`

<br>

### Listing all internship applications : `/list`

Shows a list of all of your internship applications in HireMe.

Format: `/list`

<br>

### Deleting an internship application : `/delete`

Deletes the specified internship application from HireMe.

Format: `/delete INDEX`

* Deletes the internship application at the specified `INDEX`.
* The `INDEX` refers to the index number shown in the displayed internship application list.
* The `INDEX` **must be a positive integer** 1, 2, 3, …​
* The `INDEX` **must be a valid index number** (e.g. 5 is not valid when there is less than 5 applications).

Examples:
* `/list` followed by `/delete 2` deletes the 2nd application in the list.
* `/find Google` followed by `/delete 1` deletes the 1st application in the results of the `/find` command.
* `/list` followed by `/delete 4` when you only have 3 internship applications returns `The internship application index provided is invalid`.

<br>

### Finding applications by company name: `/find`

Finds all of your applications whose company names contains words that starts with the specified characters.

Format: `/find PATTERN`

* The search is **case-insensitive**. e.g. `goo` will match `Google`.
* The search is **only for company names**.
* The search uses **prefix search**.
* If you search for `go`, then you will see a list of all the internship applications that you have applied for
  whose companies have words starting with `go` in their names.

Examples:
* `/find Goo` returns `Google` and `Google 2`
* `/find face` returns `Facebook`
* `/find app` returns `Apple`, but not `CashApp`
* `/find young` returns `Ernest & Young`

<br>

### Updating the status of an internship application : `/accept`, `/pending`, `/reject`

Updates the status of the specified internship application to `ACCEPTED`, `PENDING`, or `REJECTED`.

Formats:
- `/accept INDEX`: Changes the status to `ACCEPTED`.
- `/pending INDEX`: Changes the status to `PENDING`.
- `/reject INDEX`: Changes the status to `REJECTED`.

* The `INDEX` refers to the index number shown in the displayed internship application list.
* The `INDEX` **must be a positive integer** (e.g. 1, 2, 3, …​)
* The `INDEX` **must be a valid index number** (e.g. 5 is not valid when there is less than 5 applications)

Examples:
* `/list` followed by `/accept 2` marks the status of the 2nd application in the list as accepted.
* `/pending 3` changes the status of the 3rd application in the current list to pending.
* `/reject 1` changes the status of the 1st application in the current list to rejected.
* `/list` followed by `/accept 4` when you only have 3 internship applications returns `The internship application index provided is invalid`.

<br>

### Filtering internship applications by status: `/filter`

Filters all of your existing internship applications with specified status. Using /filter after /find resets the search and are not stackable.

Format: `/filter STATUS`

* Valid statuses are `pending`, `accepted` or `rejected` only.
* The status is case-insensitive. (e.g. `"pending"`, `"PenDiNg"` and `"PENDING"` are all allowed.)

Examples:
* `/filter pending` displays all the internship applications that have a status of pending.
* `/filter accepted` displays all the internship applications that have a status of accepted.
* `/filter rejected` displays all the internship applications that have a status of rejected.

<br>

### Sorting internship applications: `/sort`

Sorts your internship applications in **ascending / descending** order based on the date of application.

Format: `/sort ORDER`

* There are only 2 orders that are valid: `earliest` or `latest` only.
* The order is case-insensitive. (e.g. `"earliest"`, `"eaRLiEsT"` and `"EARLIEST"` are all allowed.)
* Once you sort the list, even if you exit the application and reopen it, the list remains sorted unless you add more internship applications.

Examples:
* `/sort earliest` displays the internship application list sorted according to the earliest applications first.
* `/sort latest` displays the internship application list sorted according to the latest applications first.

<br>

### Clearing all entries : `/clear`

Clears all of your internship application entries from the HireMe application.

<box type="warning" seamless>

**Caution:**
This removes all the internship applications that you have been tracking. You will not be able to revert and get back your saved data once this command is executed.
</box>

Format: `/clear`

<br>

### Viewing status chart: `/chart`

Opens a new window that displays a pie chart with summary data of the statuses of all of your internship applications.

Format: `/chart`

<br>

### Exiting the program : `/exit`

Exits the HireMe application.

Format: `/exit`

<br>

### Saving the data

HireMe data are saved in the hard disk automatically after any command that changes the data. There is no need for you to save manually.

--------------------------------------------------------------------------------------------------------------------

## FAQ
#### Q1: Where are the data for the application stored?
###### Ans: HireMe data are saved automatically as a JSON file `[JAR file location]/data/hireme.json`. You can make a backup of the file if you wish to.

#### Q2: Can I edit the data file directly?
###### Ans: You should not be editing the JSON data file directly. You should be using the commands as mentioned above to augment any data. 
**Caution:** Should the data file be corrupted, HireMe will discard all data and start with an empty data file. </box>

#### Q3: Can I `/find` followed by `/filter` to filter out the applications with a specific keyword?
###### Ans: Currently, `/find` and `/filter` do not stack. These commands will find or filter based on all of your internship application entries.

#### Q4: After I sort my internship applications, will a new entry be added in the right order?
###### Ans: New entries will not be inserted in the right order.

#### Q5: Will adding / deleting new entries or changing the statuses of existing entries be reflected in the pie chart?
###### Ans: Yes! You are not required to close the window. Any changes made will be reflected immediately in the pie chart.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Help**   | `/help`
**Add**    | `/add n/COMPANY_NAME r/ROLE e/EMAIL d/DATE` <br> e.g., `/add n/Google r/Software Engineer Intern e/google@gmail.com d/16/09/24`
**List**   | `/list`
**Delete** | `/delete INDEX`<br> e.g., `/delete 3`
**Find**   | `/find KEYWORD [MORE_KEYWORDS]`<br> e.g., `/find Google Facebook`
**Accept** | `/accept INDEX`<br> e.g., `/accept 2`
**Pending**| `/pending INDEX`<br> e.g., `/pending 3`
**Reject** | `/reject INDEX`<br> e.g., `/reject 1`
**Filter** | `/filter STATUS`<br> e.g., `/filter pending`
**Sort**   | `/sort ORDER`<br> e.g., `/sort earliest`
**Clear**  | `/clear`
**Chart**  | `/chart`
**Exit**   | `/exit`
