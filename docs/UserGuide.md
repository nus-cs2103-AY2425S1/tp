# ![icon](images/hireme_logo.png) HireMe User Guide

## Welcome to HireMe! 

Thank you for choosing **HireMe** to simplify your internship journey.
With so many positions to apply for, managing applications becomes a real challenge, on top of classes, projects, and exams.
**HireMe** is here to keep you organised and focused on landing your dream internship.
In this guide, new users will find everything you need to get started while experienced users can user the [command summary](#command-summary) as a quick reference. If you have some questions or doubts, refer to the [FAQ](#faq) section. Let's dive in and make your internship search a little easier!

<span style="color: #ff6978;">*Use HireMe to get hired now!*</span>

## Overview

HireMe is a **free desktop application that helps you manage your extensive list of internship applications.** 

* [Easy to use]
  - **Type simple commands:** If you can type fast, HireMe can get your internship tracking tasks done faster than traditional apps.
* [User friendly interface]
  - **Simple design:** view all your internship applications and statuses at a glance.
* [Streamlining management]
  - **Core features:** Add, delete and find entries.
  - **Advanced features:** Update status, sort, filter by status and view chart. 
* [Insights]
  - **Tailored summary**: gain hidden insights from your list of internship applications.

### Table of Contents
<!-- TOC -->
* [HireMe User Guide](#-hireme-user-guide)
  * [Welcome to HireMe!](#welcome-to-hireme-)
  * [Overview](#overview)
    * [Table of Contents](#table-of-contents)
  * [Quick start](#quick-start)
    * [Part 1: Setting up and downloading HireMe](#part-1-setting-up-and-downloading-hireme)
    * [Part 2: Using the HireMe application](#part-2-using-the-hireme-application)
      * [_Mac Users_](#_mac-users_)
      * [_Windows Users_](#_windows-users_)
      * [_Using the terminal_](#_using-the-terminal_)
      * [_Using the HireMe application_](#_using-the-hireme-application_)
  * [Features](#features)
    * [Viewing help : `/help`](#viewing-help--help)
    * [Adding an internship application: `/add`](#adding-an-internship-application-add)
    * [Listing all internship applications : `/list`](#listing-all-internship-applications--list)
    * [Deleting an internship application : `/delete`](#deleting-an-internship-application--delete)
    * [Finding applications by company name: `/find`](#finding-applications-by-company-name-find)
    * [Updating the status of an internship application : `/accept`, `/pending`, `/reject`](#updating-the-status-of-an-internship-application--accept-pending-reject)
    * [Filtering internship applications by status: `/filter`](#filtering-internship-applications-by-status-filter)
    * [Sorting internship applications: `/sort`](#sorting-internship-applications-sort)
    * [Clearing all entries : `/clear`](#clearing-all-entries--clear)
    * [Viewing status chart: `/chart`](#viewing-status-chart-chart)
    * [Exiting the program : `/exit`](#exiting-the-program--exit)
    * [Saving the data](#saving-the-data)
  * [FAQ](#faq)
  * [Troubleshooting](#troubleshooting)
  * [Command summary](#command-summary)
<!-- TOC -->

--------------------------------------------------------------------------------------------------------------------

## Quick start
This section has two parts: complete [Part 1](#part-1-setting-up-and-downloading-hireme) **once**, and **repeat** [Part 2](#part-2-using-the-hireme-application) **each time** you wish to use HireMe.

### Part 1: Setting up and downloading HireMe
1. Ensure that you have Java `17` or above installed on your computer for the hireme application to run. If not, download the latest version of Java from their website [here](https://www.oracle.com/sg/java/technologies/downloads/).

2. Download `hireme.jar` file from our github [here](https://github.com/AY2425S1-CS2103T-W09-3/tp/releases/tag/v1.5).

    ![downloadhireme](images/DownloadHireMe.png)

3. Create a new folder and name it any name you want (e.g. HireMe).
4. Find the downloaded `hireme.jar` file and drag it into the folder you created in step 3.
    ![hiremejar](images/HireMeJar.png)

### Part 2: Using the HireMe application
For steps 5 and 6, refer to **either** [Mac Users](#_mac-users_) **or** [Windows Users](#_windows-users_) section that matches your computer system.
Continue to [Using the terminal](#_using-the-terminal_) after completing steps 5 and 6. 

#### _Mac Users_
5. Use mac search and type Terminal, click on open. 
    You will see a window like this.
6. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar hireme.jar` command to run the application.<br>

#### _Windows Users_
5. Use windows search and type Terminal, click on open. 
   ![windowsterminal](images/WindowsTerminal.png)
You will see a window like this.
   ![openwindows](images/OpenWindowsTerminal.png)
6. Click into the folder created in step 3. Click on the empty space of the bar located at the top and copy the file path.
   ![windowsfilepath](images/Windowsfilepath.png)
    _file path here is highlighted in blue_

#### _Using the terminal_
7. Type `cd FILEPATH`  (replacing FILEPATH with the actual file path copied in step 6) and press enter. 
8. Type `java -jar hireme.jar` and press enter to run the HireMe application.
   - Windows Terminal:
      ![windowscd](images/Windowscd.png)
   - Mac Terminal:
   <br>

#### _Using the HireMe application_
A GUI similar to the below should appear in a few seconds. It is encouraged to expand the window to full screen for better viewability. Note how the app contains some sample data.<br>

   ![Ui](images/LabelledUi.png)

9. Type the command in the command box and press Enter to execute it. (e.g. typing **`/help`** and pressing Enter will open the help window.)<br>
   Some example commands you can try:

    * `/add n/Google r/Software Engineer Intern e/google@gmail.com d/16/09/24` : Adds an internship application at Google to HireMe.

    * `/delete 1` : Deletes the 1st internship application shown in the current list.

    * `/list` : Lists all your internship applications.

    * `/clear` : Deletes all your internship applications.

    * `/chart` : Shows a pie chart with your applications' statistics.

    * `/exit` : Exits the app.


Refer to the [Features](#features) below for more details of each command. 
Check [troubleshooting](#troubleshooting) if you are facing problems for setting up.
--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>


#### Notes about the command format:

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME r/ROLE`, `r/ROLE n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `/help`, `/list`, `/exit`, `/clear` and `/chart`) are not permitted.<br>
  e.g. if the command specifies `/help 123` instead of `/help`, an "Invalid command format!" error message will be shown.

* Command names must be in **lower case only**.<br>
  e.g. `/list` is acceptable but `/List` and `/LIST` are not.

</box>

### Viewing help : `/help`

Shows a message explaining how you can access the help page.

![help message](images/helpMessage.png)

Format: `/help`

<br>

### Adding an internship application: `/add`

Adds your internship application to HireMe with required details such as company's name, internship role, email and date of application.

Format: `/add n/COMPANY_NAME r/ROLE e/EMAIL d/DATE`

* `NAME` must be alphanumeric but these special characters `_`,`&`,`/`,`.`,`:`,`(`, and `)` can also be used.
* `ROLE` must be alphanumeric but these special characters `/` can also be used.
* `EMAIL` must be a valid email address.
* The `DATE` must be within the year 2000 and 2099. It also cannot be a future date which means that the date used must be before today or today's date.
* It must be in the format `dd/mm/yy`, and each field must be double digits (e.g. 01 is valid while 1 is not)

Examples:
* `/add n/Google r/Software Engineer Intern e/google@gmail.com d/16/09/24`
* `/add r/Data Scientist Intern n/Facebook e/fb@example.com d/21/10/24`

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

Filters all of your existing internship applications with specified status.

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

<img src="images/piechart.png" alt="drawing" width="500"/>

Opens a new window that displays a pie chart with summary data of the statuses of all of your internship applications.

Format: `/chart`

<br></br>

### Exiting the program : `/exit`

Exits the HireMe application.

Format: `/exit`

<br></br>

### Saving the data

HireMe data are saved in the hard disk automatically after any command that changes the data. There is no need for you to save manually.

<br></br>

--------------------------------------------------------------------------------------------------------------------

## FAQ
Get your questions or doubts about HireMe's functionality and details answered here. If you have further questions, you can contact us through our email `hireme@gmail.com`.

#### Q1: Where is the data for the application stored?
Ans: HireMe data are saved automatically as a JSON file `[JAR file location]/data/hireme.json`. You can make a backup of the file if you wish to.

#### Q2: Can I edit the data file directly?
Ans: You should not be editing the JSON data file directly. You should be using the commands as mentioned above to augment any data.
Should the data file be corrupted, HireMe will discard all data and start with an empty data file.

#### Q3: Can I find followed by filter to filter out the applications with a specific keyword?
Ans: Currently, the effects of `/find` and `/filter` do not stack. These commands will find or filter based on all of your internship application entries.

#### Q4: After I sort my internship applications, will a new entry be added in the right order automatically?
Ans: New entries will not be inserted in the right order. They are inserted to the bottom of the list.

#### Q5: Will adding / deleting new entries or changing the statuses of existing entries be reflected in the pie chart?
Ans: Yes! You are not required to close the window. Any changes made will be reflected immediately in the pie chart.

--------------------------------------------------------------------------------------------------------------------
## Troubleshooting
If you are facing problems while setting up or using HireMe, we want to help you. This section covers some of the common problems and ways to solve them. If you need futher help, you can reach out to us through our email `hireme@gmail.com`.

#### HireMe application setup failed
Check that you have the hireme.jar in the correct folder and copied the correct file path into the terminal. Check that you have java 17 or above downloaded in your computer. Follow the correct set of instructions based on your computer system, see [Part 2](#part-2-using-the-hireme-application) of setting up. 

#### I got errors using the commands
Check that you have the typed the correct [command format](#notes-about-the-command-format). Check that you have included all necessary parameters. Check that you have used valid inputs (refer to each command for valid inputs).

#### I can't see the full text on the screen
Try opening the window to full screen or scroll to view the full text. Avoid inputing too texts.

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
