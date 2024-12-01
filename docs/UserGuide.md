---
layout: page
title: User Guide
---

Murphy's List is a **desktop app for managing patient contact info for institutes providing palliative care, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI).
If you can type fast, Murphy's List can get your **healthcare administrative tasks** done faster than other traditional GUI apps.

## Table of Contents
1. [Quick Start](#quick-start)
2. [Features](#features)
    1. [Viewing Help](#viewing-help--help)
    2. [Adding a Patient Profile](#adding-a-patient-profile--add)
    3. [Adding a Remark](#adding-a-remark-to-a-patient-profile--remark)
    4. [Adding an Appointment](#adding-an-appointment--appointment)
    5. [Changing Triage Stage](#changing-triage-stage--triage)
    6. [Editing a patient profile](#editing-a-patient-profile--edit)
    7. [Listing all profiles](#listing-all-patient-profiles--list)
    8. [Listing profiles by schedule](#listing-profiles-by-schedule--schedule)
    9. [Sort list of patients](#sort-list-of-patients-sort)
    10. [Locating patients by name](#locating-patients-by-name-find)
    11. [Logging patient information](#logging-patient-information--log)
    12. [Viewing patient information](#viewing-patient-information--view)
    13. [Deleting patient profile](#deleting-a-patient-profile--delete)
    14. [Clear all entries](#clearing-all-entries--clear)
    15. [Exiting the program](#exiting-the-program--exit)
    16. [Appointment Popup](#appointment-popup-on-start-up)
3. [FAQ](#faq)
4. [Known Issues](#known-issues)
5. [Command Summary](#command-summary)
6. [Command Shortcuts](#command-shortcuts-table)

--------------------------------------------------------------------------------------------------------------------

## Quick start
Welcome! This short guide will help you launch the **Murphy's List** application on your computer. Don't worry if you're not familiar with technical terms—we'll walk you through each step in simple language.


1. Ensure you have Java `17` or above installed in your Computer.

2. Download the latest `murphys_list.jar` file from [here](https://github.com/AY2425S1-CS2103T-W11-1a/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for Murphy's List.

4. Open a command terminal (Known as "Command Prompt" on Windows or "Terminal on Mac).

6. In the command terminal, type the following command and press **Enter**:
    ```bash
    cd path_to_your_folder
    ```
- Replace `path_to_your_folder` with the actual path to your folder. For example:
    - **Windows**:
      ```bash
      cd C:\Users\YourName\Downloads
      ```
    - **Mac/Linux**:
      ```bash
      cd /Users/YourName/Downloads
      ```


5. A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

6. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com i/S1231231A a/John Street, Block 123, #01-01 t/2` : Adds a patient profile of a patient named `John Doe` to Murphy's List.

   * `delete S1231231A` : Deletes the patient profile of the patient with `Nric S1231231A`.

   * `clear` : Deletes all patient profiles in the database.

   * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**Notes about the command format:**<br>

* All commands are case-sensitive and to be only used in lowercase.<br>
  e.g. `help` is a valid command, but `Help` and `HELP` is not.

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g. `n/NAME [tag/TAG]` can be used as `n/John Doe tag/Parkinsons` or as `n/John Doe`.

* Items with `…` after them can be used multiple times, including zero times.
  For example, `[tag/TAG]…` can be used as follows:

  - Not at all (i.e., ` ` )
  - With one tag: `tag/Parkinsons`
  - With multiple tags: `tag/Parkinsons tag/Diabetic`

* Parameters MUST be in order unless specified otherwise.<br>

  * Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `schedule`, `exit` and `clear`) will be ignored.<br>
    e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
Murphy's List does not support whitespaces in **tags**; each tag must be a single alphanumeric word.
</div>


### Viewing help : `help`

Displays a list of accepted commands.

Format: `help`

![help page](images/HelpPage.png)

### Adding a patient profile : `add`

Adds a patient profile to the database.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL i/NRIC a/ADDRESS t/TRIAGE [tag/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A patient profile can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com i/S1234123A a/John Street, Block 123, #01-01 t/2`
* `add n/Betsy Crowe p/24681357 e/betsycrowe@example.com i/T1234567D a/Clementi Ave 1, Block 230 t/1 tag/Diabetic tag/G6PD`

> **Note:** `add` **does not support** the addition of appointments and remarks. Use the [appointment](#adding-an-appointment--appointment) and [remark](#adding-a-remark-to-a-patient-profile--remark) command to do so.

### Adding a remark to a patient profile : `remark`

Adds a remark to a specified patient profile.

Format: `remark NRIC r/REMARK`

Parameters: `NRIC` must be a valid NRIC in the database. `REMARK` must be a non-empty string consisting of only alphanumeric characters or spaces.

Examples:
* `remark S1234567A r/allergic to dogs`
* `remark T1231231D r/keep away from flashing lights`

> **Note:** You can only specify **one** remark to add, and patients can only have one remark. If the patient already has a remark, the original remark will be overwritten.


### Adding an appointment : `appointment`

Adds the appointment date (in format DD-MM-YYYY HH:MM) of a patient to the patient profile.

Format: `appointment NRIC app/DD-MM-YYYY HH:MM`

* If the patient already has an appointment, the new appointment will overwrite the existing one.
* Invalid dates and times (eg. 30th February, 24:59) will not be accepted.

> **Note:** Patients can only have one appointment at any one time.

### Changing Triage Stage : `triage`

Changes the existing triage stage of a patient to another stage. Triage stage refers to the severity of patient's condition.

Stages are categorised from 1 to 5. Triaging stages follows the Phase of Illness Model:

1 - Stable

2 - Unstable

3 - Deteriorating

4 - Terminal

5 - Bereaved

Format: `triage NRIC t/TRIAGE`

Example:
* `triage S1234567A t/1`
* `triage T1231231D t/3`

### Editing a patient profile : `edit`

Edits the details of the patient identified by their NRIC. **Existing information will be overwritten by the input values.**

Format: `edit NRIC [n/NAME] [p/PHONE] [e/EMAIL] [i/NRIC] [a/ADDRESS] [t/TRIAGE] [app/APPOINTMENT] [tag/TAG]…​`

* Edits the patient profile with the specified `NRIC`.
* At least one of the optional fields must be provided.
* Apart from `NRIC`, all other fields can be in any order.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the patient will be removed i.e. adding of tags is not cumulative.
* You can remove all the patient’s tags by typing `tag/` without
  specifying any tags after it.

Examples:
*  `edit S1234567A p/91234567 e/johndoe@example.com` Edits the phone number and email address of the patient with `NRIC: S1234567A` to be `91234567` and `johndoe@example.com` respectively.
*  `edit S9876543D n/Betsy Crower tag/` Edits the name of the patient with specified `NRIC: S9876543D` displayed to be `Betsy Crower` and clears all existing tags.

### Listing all patient profiles : `list`

Shows a list of all patient profiles in the database.

Format: `list`

### Listing profiles by schedule : `schedule`

Shows a list of all patient profiles in the database, sorted by appointment date.

Format: `schedule`

### Sort list of patients: `sort`

Sorts the list of patients based on the specified criteria.

Format: `sort [name | appointment]`

* If the list is already sorted by the specified criteria, the command will maintain the current order.
* Sorting by `appointment` will only consider patients with scheduled appointments. Patients without appointments will appear at the end of the list.

Examples:
* `sort name` sorts the list of patients in alphabetical order by their names.
* `sort appointment` sorts the list of patients by their upcoming appointment dates in chronological order.

### Locating patients by name: `find`

Search for patients by their names or by tags (i.e. medical conditions).

#### To search by **name**:

Format: `find KEYWORD [MORE_KEYWORDS]`

* KEYWORD is a alphanumeric string.
* The search is case-insensitive. e.g. `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Profiles matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>


  ![result for 'find alex david'](images/searchResult.png)

#### To search by **tags**:

Format: `find tag/TAG [MORE_TAGS]`

* TAG is an alphanumeric string.
* Tags are case-insensitive e.g `tag/diabetic` will match `tag/Diabetic`
* Only full words will be matched e.g `tag/g6` will not match `tag/g6pd`
* If you specify `tag/` at the start of your search, the command interprets the input as a tag search.
* If no `tag/` prefix is provided, it defaults to a name search.

Examples:
- `find tag/diabetic` finds all people with the "diabetic" tag.
- `find tag/diabetic hypertensive` finds all people with either the "diabetic" or "hypertensive" tags.

> **Note**: `find` will only search for either names or tags but not both at the same time.

### Logging patient information : `log`

Logs information to a patient's profile for tracking patient activity and condition.

Format: `log NRIC DD-MM-YYYY HH:MM INFO`

* Logs the information to the patient with the specified `NRIC`.
* Date and time inputs refer to log date and time.
* Invalid dates and times (eg. 30th February, 24:59) will not be accepted.
* Logged information must be non-empty.
* Note that when the View Window is opened when adding log entries, the window will not display the new log entries until the view command is executed by the user again.

Examples:
* `log S1234567A 25-12-2024 14:30 Patient has been discharged` logs the information `Patient has been discharged` to the patient with `NRIC S1234567A` at `25-12-2024 14:30`.

### Viewing patient information : `view`

* Views full information of the patient with the specified `NRIC` not displayed on the Main Window (e.g., Patient logs).

**Format:** `view NRIC`

![view page](images/ViewWindow.png)

> **Note:** The view window will not automatically update after editing the patient's information unless you close the window and call the `view` command again. Additionally, it will not close automatically if you delete the patient.

### Deleting a patient profile : `delete`

Deletes the specified patient profile from the database.

Format: `delete NRIC`

* Deletes the patient with the specified `NRIC`.

Examples:
* `delete S1234567A` deletes the patient profile of the patient with `NRIC S1234567A`.

### Clearing all entries : `clear`

Clears all entries from the database.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Command Shortcuts

For quick access, here is a [table of command shortcuts](#command-shortcuts-table) summarizing the command formats.

### Appointment Popup on Start-Up

On application start-up, if a patient has an appointment scheduled for the current day, a popup window will appear to notify you of the upcoming appointment(s).

![appointment popup](images/AppointmentPopup.png)

This popup will only display once at start-up and will not reappear unless the application is restarted with a patient having an appointment on the current day.


### Saving the data

Murphy's List data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

Murphy's List data are saved automatically as a JSON file `[JAR file location]/data/murphyslist.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, Murphy's List will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause Murphy's List to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Murphy's List home folder.
**Q**: What should I do if the application does not start?<br>
**A**: Ensure you have Java `17` or above installed in your Computer. If the problem persists, contact the developers.
You can check your Java version by running `java -version` in the command terminal.
**Q**: How do I recover data if I accidentally delete a patient profile?
**A**: Unfortunately, there is no built-in undo feature for deleted data. It is recommended to back up your data by making a copy of the data file periodically.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action              | Format, Examples                                                                                                                                                                                               |
|---------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**             | `add n/NAME p/PHONE_NUMBER e/EMAIL i/NRIC a/ADDRESS t/TRIAGE [tag/TAG]…​` <br> e.g., `add n/Betsy Crowe p/24681357 e/betsycrowe@example.com i/T1234567D a/Clementi Ave 1, Block 230 t/1 tag/Diabetic tag/G6PD` |
| **Add Appointment** | `appointment NRIC app/DD-MM-YYYY HH:MM` <br> e.g., `appointment S1234567A app/25-12-2024 14:30`                                                                                                                |
| **Add Remark**      | `remark NRIC r/REMARK` <br> e.g., `remark S1231231D r/allergic to seafood`                                                                                                                                     |
| **Change Triage**   | `triage NRIC t/TRIAGE` <br> e.g., `triage S1234567A t/1`                                                                                                                                                       |
| **Clear**           | `clear`                                                                                                                                                                                                        |
| **Delete**          | `delete NRIC`<br> e.g., `delete S1234567A`                                                                                                                                                                     |
| **Edit**            | `edit NRIC [n/NAME] [p/PHONE] [e/EMAIL] [i/NRIC] [a/ADDRESS] [t/TRIAGE] [app/APPOINTMENT] [tag/TAG]…​`<br> e.g.,`edit S1234567A n/James Lee e/jameslee@example.com`                                            |
| **Find**            | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`<br/> `find tag/TAG [MORE_TAGS]` <br> e.g., `find tag/diabetic`                                                                                      |
| **List**            | `list`                                                                                                                                                                                                         |
| **Log**             | `log NRIC DD-MM-YYYY HH:MM INFO(non-empty)` <br> e.g., `log S1234567A 25-12-2024 14:30 Patient has been discharged`                                                                                            |                                             |
| **Sort**            | `sort name`, `sort appointment`                                                                                                                                                                                |
| **Schedule**        | `schedule`                                                                                                                                                                                                     |
| **View**            | `view NRIC`<br> e.g `view S1234567A`                                                                                                                                                                           |
| **Help**            | `help`                                                                                                                                                                                                         |
| **Exit**            | `exit`                                                                                                                                                                                                         |

## Command Shortcuts Table

| Command       | Shortcut | Description                              |
|---------------|----------|------------------------------------------|
| `add`         | `a`      | Adds a patient profile to the database   |
| `appointment` | `appt`   | Adds an appointment to a patient profile |
| `clear`       | `c`      | Clears all entries from the database     |
| `delete`      | `d`      | Deletes a specified patient profile      |
| `edit`        | `ed`     | Edits details of a patient profile       |
| `exit`        | `ex`     | Exits the application                    |
| `find`        | `f`      | Finds patients by name or tags           |
| `help`        | `h`      | Displays a list of accepted commands     |
| `list`        | `l`      | Shows a list of all patient profiles     |
| `remark`      | `r`      | Add a remark to a patient profile        |
| `sort`        | `s`      | Sorts the list of patients               |
| `triage`      | `t`      | Change triage stage of a patient         |
| `view`        | `v`      | Views detailed patient information       |

> **Note:** Shortcuts for `log`and `schedule` are not included but will be available in a future update.
