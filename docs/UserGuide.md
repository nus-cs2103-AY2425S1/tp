# ![icon](images/hireme_logo.png) HireMe User Guide
<br></br>
## Welcome to HireMe! 

Thank you for choosing **HireMe** to simplify your internship journey.
With so many positions to apply for, managing applications becomes a real challenge, on top of classes, projects, and exams.
**HireMe** is here to keep you organised and focused on landing your dream internship.
In this guide, new users will find everything you need to get started while experienced users can user the [command summary](#command-summary) as a quick reference. If you have some questions or doubts, refer to the [FAQ](#faq) section. Let's dive in and make your internship search a little easier!

<span style="color: #ff6978;">*Use HireMe to get hired now!*</span>
<br></br>

## Overview of User Guide

HireMe is a **free desktop application that helps you manage your extensive list of internship applications.** 

* _Easy to use_
  - **Type simple commands:** If you can type fast, HireMe can get your internship tracking tasks done faster than traditional apps.
* _User friendly interface_
  - **Simple design:** view all your internship applications and statuses at a glance.
* _Streamlining management_
  - **Core features:** Add, delete and find entries.
  - **Advanced features:** Update status, sort, filter by status and view chart. 
* _Detailed Insights_
  - **Tailored summary**: gain hidden insights from your list of internship applications.

<br></br>
### Table of Contents
<!-- TOC -->
* [![icon](images/hireme_logo.png) HireMe User Guide](#-hireme-user-guide)
  * [Welcome to HireMe!](#welcome-to-hireme)
  * [Overview of User Guide](#overview-of-user-guide)
  * [Overview of target users](#overview-of-target-users)
  * [How to use our User Guide](#how-to-use-our-user-guide)
  * [Quick start](#quick-start)
    * [Part 1: Setting up and downloading HireMe](#part-1-setting-up-and-downloading-hireme)
    * [Part 2: Using the HireMe application](#part-2-using-the-hireme-application)
      * [_Mac Users_](#mac-users)
      * [_Windows Users_](#windows-users)
      * [_Using the terminal_](#using-the-terminal)
      * [_Using the HireMe application_](#using-the-hireme-application)
  * [Features](#features)
    * [Viewing help : `/help`](#viewing-help-help)
    * [Adding an internship application: `/add`](#adding-an-internship-application-add)
    * [Listing all internship applications : `/list`](#listing-all-internship-applications-list)
    * [Deleting an internship application : `/delete`](#deleting-an-internship-application-delete)
    * [Finding applications by company name: `/find`](#finding-applications-by-company-name-find)
    * [Updating the Status of an Internship Application: `/accept`, `/pending`, `/reject`](#updating-the-status-of-an-internship-application-accept-pending-reject)
    * [Filtering internship applications by status: `/filter`](#filtering-internship-applications-by-status-filter)
    * [Sorting internship applications: `/sort`](#sorting-internship-applications-sort)
    * [Clearing all entries : `/clear`](#clearing-all-entries-clear)
    * [Viewing status chart: `/chart`](#viewing-status-chart-chart)
    * [Exiting the program : `/exit`](#exiting-the-program-exit)
    * [Saving the data](#saving-the-data)
  * [FAQ](#faq)
  * [Troubleshooting](#troubleshooting)
      * [HireMe application setup failed](#hireme-application-setup-failed)
      * [Getting errors when using the commands](#getting-errors-when-using-the-commands)
      * [Unable to see the full text on the screen](#unable-to-see-the-full-text-on-the-screen)
  * [Command summary](#command-summary)
  * [Glossary](#glossary)
<!-- TOC -->


--------------------------------------------------------------------------------------------------------------------

## Overview of target users

If you are a Computer Science undergraduate who **does not have much time**, can **type fast** and want to **better manage your internship applications**,
HireMe is the perfect solution for you! Sometimes, tracking internship applications can be troublesome and time-consuming. 
We understand the importance of tracking as it allows you to follow up on your applications. Hence, we came up with HireMe to help you
**stay organised without having to put in much effort** because we know that you may be busy with your coursework. Let's get you
started with HireMe!

--------------------------------------------------------------------------------------------------------------------
## How to use our User Guide

First time using **HireMe**? Don't worry! We've got you covered! This tutorial will help maximise your usage of **HireMe**.
* To get started, refer to the [Quick Start](#quick-start), which will walk you through getting **HireMe** all set up!
* Need a quick refresher of the commands available for you? Check out our [Command Summary](#command-summary)!
* Is there a burning question you want answers for? The [FAQ](#faq) might have the answer.

Before continuing, here are some important information you need to know about the User Guide:
1. There are 2 different kinds of boxes that provide extra information.
    * *Notice boxes* provide important information that you should take note of.

      <box type="info" seamless>
      
      **Notice:** This is a notice box.
      </box>

    * *Warning boxes* provide a warning about certain errors that might occur as a precaution for incorrect usage.

      <box type="warning" seamless>

      **Warning:** This is a warning box.
      </box>

2. Refer to [Features](#features) for detailed explanations of each feature of **HireMe**!

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

<br></br>
### Part 2: Using the HireMe application
For steps 5 and 6, refer to **either** [Mac Users](#mac-users) **or** [Windows Users](#windows-users) section that matches your computer system.
Continue to [Using the terminal](#using-the-terminal) after completing steps 5 and 6.
<br></br>
#### _Mac Users_
5. Use mac spotlight search (Command + Space bar) and type Terminal, click on open. 

    <img src="images/MacTerminal.png" alt="macsterminal" width="700"/>
   
    You will see a window like this.

    ![openmacterminal](images/OpenMacTerminal.png)
    

6. Click into the folder created in step 3. Left click on the `hireme.jar` file and select `Get Info`.
   ![macfilepathinfo](images/Macfilepathinfo.png)

    On the new open that pops up, left click on `Where` and copy the file path.
   <img src="images/Macfilepath.png" alt="macfilepath" width="300"/>
   <br></br>

#### _Windows Users_
7. Use windows search and type Terminal, click on open. 
   ![windowsterminal](images/WindowsTerminal.png)
<br></br>
You will see a window like this.
   ![openwindows](images/OpenWindowsTerminal.png)


8. Click into the folder created in step 3. Click on the empty space of the bar located at the top and copy the file path.
   ![windowsfilepath](images/Windowsfilepath.png)
    _file path here is highlighted in blue_
   <br></br>

#### _Using the terminal_
7. Type `cd FILEPATH`  (replacing FILEPATH with the actual file path copied in step 6) and press enter.


8. Type `java -jar hireme.jar` and press enter to run the HireMe application.
<br></br>
   - Windows Terminal:
      ![windowscd](images/Windowscd.png)
     <br></br>
   - Mac Terminal:
      ![maccd](images/Maccd.png)
     <br></br>
<br></br>


#### _Using the HireMe application_
A GUI similar to the below should appear in a few seconds. It is encouraged to expand the window to full screen for better viewability. Note how the app contains some sample data.<br>

   ![Ui](images/LabelledUi.png)

9. Type the command in the command box and press Enter to execute it. (e.g. typing **`/help`** and pressing Enter will open the help window.)<br></br>
   Some example commands you can try:

    * `/add n/Google r/Software Engineer Intern e/google@gmail.com d/16/09/24` : Adds an internship application at Google to HireMe.

    * `/delete 1` : Deletes the 1st internship application shown in the displayed list.

    * `/list` : Lists all your internship applications.

    * `/clear` : Deletes all your internship applications.

    * `/chart` : Shows a pie chart with your applications' statistics.

    * `/exit` : Exits the app.


Refer to the [Features](#features) below for more details of each command. Check [Troubleshooting](#troubleshooting) if you are facing problems for setting up.

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

<br></br>
### Viewing help : `/help`

Shows a message explaining how you can access the help page.

![help message](images/helpMessage.png)

Format: `/help`

<br></br>

### Adding an internship application: `/add`

Adds your internship application to HireMe with required details such as company's name, internship role, email and date of application.

Format: `/add n/COMPANY_NAME r/ROLE e/EMAIL d/DATE`

* `NAME` must be alphanumeric but these special characters `_`,`&`,`/`,`.`,`:`,`(`, and `)` can also be used.
* `ROLE` must be alphanumeric but these special characters `/` can also be used.
* `EMAIL` must be a valid email address.
* The `DATE` must be within the year 2000 and the current year. It also cannot be a future date which means that the date used must be before today or today's date.
* It must be in the format `dd/mm/yy`, and each field must be double digits (e.g. 01 is valid while 1 is not)

Examples:
* `/add r/Data Scientist Intern n/Facebook e/fb@example.com d/21/10/24`
* `/add n/Google r/Software Engineer Intern e/google@gmail.com d/01/01/24`

<p>These are the before and after images of the second example</p>
<div style="display: flex;">
  <img src="images/add-before.png" width="360" alt="Add-Before" style="margin-right: 20px;">
  <img src="images/add-after.png" width="360" alt="Add-After">
</div>

<box type="info" seamless>
<strong>Note:</strong>  HireMe considers an entry to be a duplicate of all of its fields match <strong>(case-insensitive)</strong>  an existing internship application entry. An attempt to add duplicates will result in an error.
</box>

<br></br>

### Listing all internship applications : `/list`

Shows a list of all of your internship applications in HireMe.

Format: `/list`

<br></br>

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
* `/list` followed by `/delete 4` when you only have 3 internship applications returns an error message.

<p>These are the before and after images of the first example</p>
<div style="display: flex;">
  <img src="images/delete-before.png" width="360" alt="Delete-Before" style="margin-right: 20px;">
  <img src="images/delete-after.png" width="360" alt="Delete-After">
</div>

<br></br>

### Finding applications by company name: `/find`

Finds all of your applications whose company names contains words that starts with the specified characters.

Format: `/find KEYWORD [ADDITIONAL_KEYWORDS]`

* The search is **case-insensitive**. e.g. `goo` will match `Google`.
* The search is **only for company names**.
* The search uses **prefix search**.
* The search allows for **more than 1 `KEYWORD`**
* If you search for `go`, then you will see a list of all the internship applications that you have applied for
  whose companies have words starting with `go` in their names.

Examples:
* `/find Goo` returns `Google` and `Google 2`
* `/find face` returns `Facebook`
* `/find app` returns `Apple`, but not `CashApp`
* `/find young` returns `Ernest & Young`
* `/find amaZoN NetFlix` returns `Amazon` and `Netflix`

<p>These are the before and after images of the last example</p>
<div style="display: flex;">
  <img src="images/find-before.png" width="360" alt="Find-Before" style="margin-right: 20px;">
  <img src="images/find-after.png" width="360" alt="Find-After">
</div>

<br></br>

### Updating the Status of an Internship Application: `/accept`, `/pending`, `/reject`

Use these commands to update the status of a specified internship application to `ACCEPTED`, `PENDING`, or `REJECTED`.

**Commands:**
- `/accept INDEX`: Changes the status of the application at index `INDEX` in the displayed list to `ACCEPTED`.
- `/pending INDEX`: Changes the status of the application at index `INDEX` in the displayed list to `PENDING`.
- `/reject INDEX`: Changes the status of the application at index `INDEX` in the displayed list to `REJECTED`.

**Details:**
- The `INDEX` refers to the position of the application in the current displayed list. The index is relative to the list shown on the screen after filtering or listing commands.
- `INDEX` **must be a positive integer** (e.g., 1, 2, 3, …).
- `INDEX` **must be a valid number** for the displayed list length (e.g., `/reject 5` is invalid if there are fewer than 5 applications in the displayed list).

**Important:** Commands like `/accept`, `/pending`, and `/reject` work on the displayed list, not the entire application database. For example, if you have 10 total applications, but after using `/filter ACCEPTED` only 5 applications are shown, then `/reject 5` will target the last item on this filtered list.

**Examples:**
1. `/list` followed by `/accept 2`: Changes the status of the 2nd application in the full list to `ACCEPTED`.
2. `/pending 3`: Changes the status of the 3rd application in the currently displayed list to `PENDING`.
3. `/reject 1`: Changes the status of the 1st application in the currently displayed list to `REJECTED`.
4. `/filter PENDING` followed by `/accept 1`: Updates the status of the 1st application on the filtered list (currently `PENDING`) to `ACCEPTED`, removing it from the list view as it no longer matches the `pending` filter.
5. `/list` followed by `/accept 4` when there are only 3 internship applications returns an error message.


<box type="info" seamless>
**Note:**<br> You can update an application’s status to its current state. For example, using `/accept INDEX` on an already accepted application will keep its status as `ACCEPTED`, `/pending INDEX` on a pending application will keep it as `PENDING`, and `/reject INDEX` on a rejected application will maintain its `REJECTED` status.
</box>

<p>These are the before and after images of the first example</p>
<div style="display: flex;">
  <img src="images/accept-status-before.png" width="360" alt="Accept-Status-Before" style="margin-right: 20px;">
  <img src="images/accept-status-after.png" width="360" alt="Accept-Status-After">
</div>

<box type="info" seamless>
<strong>Note:</strong><br> You can update an application’s status to its current state. For example, using `/accept INDEX` on an already accepted application will keep its status as `ACCEPTED`, `/pending INDEX` on a pending application will keep it as `PENDING`, and `/reject INDEX` on a rejected application will maintain its `REJECTED` status.
</box>


<br></br>

### Filtering internship applications by status: `/filter`

Filters all of your existing internship applications with specified status.

Format: `/filter STATUS`

* Valid statuses are `pending`, `accepted` or `rejected` only.
* The status is case-insensitive. (e.g. `"pending"`, `"PenDiNg"` and `"PENDING"` are all allowed.)

Examples:
* `/filter pending` displays all the internship applications that have a status of pending.
* `/filter accepted` displays all the internship applications that have a status of accepted.
* `/filter rejected` displays all the internship applications that have a status of rejected.

<p>These are the before and after images of the second example</p>
<div style="display: flex;">
  <img src="images/filter-before.png" width="360" alt="Filter-Before" style="margin-right: 20px;">
  <img src="images/filter-after.png" width="360" alt="Filter-After">
</div>

<br></br>

### Sorting internship applications: `/sort`

Sorts your internship applications in **ascending / descending** order based on the date of application.

Format: `/sort ORDER`

* There are only 2 orders that are valid: `earliest` or `latest` only.
* The order is case-insensitive. (e.g. `"earliest"`, `"eaRLiEsT"` and `"EARLIEST"` are all allowed.)
* Once you sort the list, even if you exit the application and reopen it, the list remains sorted unless you add more internship applications.
* Even if you find or filter the list, sorting it afterwards will also sort the entire main list.

Examples:
* `/sort earliest` displays the internship application list sorted according to the earliest applications first.
* `/sort latest` displays the internship application list sorted according to the latest applications first.

<p>These are the before and after images of the first example</p>
<div style="display: flex;">
  <img src="images/sort-before.png" width="360" alt="Sort-Before" style="margin-right: 20px;">
  <img src="images/sort-after.png" width="360" alt="Sort-After">
</div>

<br></br>

### Clearing all entries : `/clear`

Clears all of your internship application entries from the HireMe application.

<box type="warning" seamless>

**Caution:**
This removes all the internship applications that you have been tracking. You will not be able to revert and get back your saved data once this command is executed.
</box>

Format: `/clear`

<br></br>

### Viewing status chart: `/chart`

<img src="images/piechart.png" alt="drawing" width="360"></img>
<br></br>
Opens a new window that displays a pie chart with summary data of the statuses of all of your internship applications.

Format: `/chart`

<br></br>

### Exiting the program : `/exit`

Exits the HireMe application.

Format: `/exit`

<br></br>

### Saving the data

HireMe data are saved in the hard disk automatically after any command that changes the data. There is no need for you to save manually.

--------------------------------------------------------------------------------------------------------------------

## FAQ
Get your questions or doubts about HireMe's functionality and details answered here. If you have further questions, you can contact us through our email `hireme@gmail.com`.

#### Q1: Where is the data for the application stored?
Ans: HireMe data are saved automatically as a JSON file `[JAR file location]/data/hireme.json`. You can make a backup of the file if you wish to.
<br></br>

#### Q2: Can I edit the data file directly?
Ans: You are strongly encouraged to **not** edit the JSON data file directly. You can use the commands as mentioned above to augment any data.
Should the changes made to the data file causes the format to be invalid, HireMe will discard all data and start with an empty data file.
It is highly recommended to make a copy of the date file before editing it. <br>

Users should only edit the data file only if they are confident in updating it correctly.
<br></br>

#### Q3: What is considered valid, when editing the JSON data file directly?
Ans: Data entries are valid, if they are achievable through a sequence of commands. <br>

Examples: <br>
Having `30/02/24` is an invalid `dateString` field for an application, as there is no sequence of commands that will lead to an application having the respective date.<br>

Having `REJECTED` is a valid `statusString` field for an application, as the user can `/add` an internship and update the status via `/reject`.
<br></br>

#### Q4: Can I find followed by filter to filter out the applications with a specific keyword?
Ans: Currently, `/find` and `/filter` do not stack. These commands will find or filter based on all of your internship application entries.
<br></br>

#### Q5: After I sort my internship applications, will a new entry be added in the right order automatically?
Ans: New entries will not be inserted in the right order. They are inserted to the bottom of the list.
<br></br>

#### Q6: Will adding / deleting new entries or changing the statuses of existing entries be reflected in the pie chart?
Ans: Yes! You are not required to close the window. Any changes made will be reflected immediately in the pie chart.
<br></br>

#### Q7: What is considered a valid email address format?
Ans: If you have an existing email address account, it will be allowed by HireMe. You can check [here](https://verifalia.com/validate-email).

--------------------------------------------------------------------------------------------------------------------
## Troubleshooting
If you are facing problems while setting up or using HireMe, we want to help you. This section covers some of the common problems and ways to solve them. If you need further help, you can reach out to us through our email `hireme@gmail.com`.
<br></br>

#### HireMe application setup failed
Check that you have the hireme.jar in the correct folder and copied the correct file path into the terminal. Check that you have java 17 or above downloaded in your computer. Follow the correct set of instructions based on your computer system, see [Part 2](#part-2-using-the-hireme-application) of setting up.
<br></br>

#### Getting errors when using the commands
Check that you have the typed the correct [command format](#notes-about-the-command-format). Check that you have included all necessary parameters. Check that you have used valid inputs (refer to each command for valid inputs).
<br></br>

#### Unable to see the full text on the screen
Try opening the window to full screen or scroll to view the full text. Avoid inputting texts that are too lengthy.

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

--------------------------------------------------------------------------------------------------------------------

## Glossary

- **Application Status**:
    - **PENDING**: The internship application is currently in progress.
    - **REJECTED**: The user has rejected or been rejected from this internship application.
    - **ACCEPTED**: The user has accepted the offer for this internship.

- **Action**: The task carried out by the HireMe application such as Add, Delete, Update entries.

- **Command Line Interface (CLI)**: The user interacts with the computer by typing text commands instead of using a mouse to click on buttons or icons. As if giving instructions to execute a desired action.

- **Command**: The string the user types into the HireMe application’s command bar to carry out a particular action.

- **Command Bar**: The input bar at the top of the HireMe application which allows users to type in a string command.

- **Company Email**: The email of the company that the user is applying for an internship role at.

- **Company Name**: The name of the company that the user is applying for an internship role at.

- **Graphical User Interface (GUI)**: The user interacts with the computer using visual elements like buttons, icons and windows.

- **Role**: The role of the internship the user applied for.

- **Index**: The index of the internship application displayed in the list.
