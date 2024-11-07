---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# Talentcy User Guide

Talentcy is a **desktop app for managing job applicant contacts and monitoring their interview stages, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, Talentcy can get your contact management tasks done faster than traditional GUI apps.

The codebase of Talentcy originates from AddressBook Level 3 (AB3) developed by CS2103 team.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103-F09-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your Talentcy app.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar Talentcy.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `list` : Lists all contacts.

    * `add n/John Doe p/98765432 e/johnd@example.com j/SWE123 t/A r/good ethic` : Adds a contact named `John Doe` to the Address Book.

    * `delete 3` : Deletes the 3rd contact shown in the current list.

    * `clear` : Deletes all contacts.

    * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.
* Items in square brackets are optional.<br>
    e.g `n/NAME [r/REMARK]` can be used as `n/John Doe r/have pHD` or as `n/John Doe`.
* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE`, `p/PHONE n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
  </box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE e/EMAIL j/JOB_CODE t/TAG`

<box type="tip" seamless>

**Tip:** Only one interview stage tag will be attached to a contact at any point of time.

Please refer to this table
for list of valid tags:


| Tag | Interview Stage                 | Definition                                                                  |
|-----|---------------------------------|-----------------------------------------------------------------------------|
| N   | New                             | New applicant                                                               |
| TP  | Technical Interview in Progress | Technical interview is in the process of being scheduled for the applicant  |
| TC | Technical Interview Confirmed | Technical interview has been schedule for the applicant                     |                                           
| BP | Behavioral Interview in Progress | Behavioral interview is in the process of being scheduled for the applicant |
| BC | Behavioral Interview Confirmed | Behavioral interview has been scheduled for the applicant                   |
| A| Accepted| Applicant has been accepted by the company                                  |
| R | Rejected | Applicant has been rejected by the company                                  |


Examples:
* `add n/John Doe p/98765432 e/johnd@example.com j/XYZ1010 t/N`
* `add n/Betsy Crowe t/BP e/betsycrowe@example.com j/AB1301 p/1234567`

### Listing persons based on attribute : `list`

Shows a list of all persons in the address book.

Format:

`list`



### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [j/JOB_CODE] [t/TAG]`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be updated.
* Existing values will be updated to the input values.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.

### Locating persons by criteria given: `find`

Finds persons by criteria given

Format:
`find [n/NAME] [p/PHONE] [e/EMAIL] [j/JOB_CODE] [t/TAG] [r/REMARK]`

* The order of the words matter. e.g. `Hans Bo` will only match `Hans Bo` and `Hans Bobo`, but not `Bo Hans`
* All fields are case-insensitive
* Search will be done using partial match

Examples:
* `find n/alex yeoh` returns `Alex Yeoh` and `Alex Yeoh Bin Sheng`
* `find t/TP` returns the list of contacts with `TP` tag <br>
  ![result for 'findTp'](images/findTp.png)
* `find n/alex yeoh t/TP` return the list of contacts with name containing `alex yeoh` whose tag is `TP`

### Deleting a person : `delete`

Deletes the specified person from the address book by finding exact match of a field

Format:
`delete INDEX`
`delete n/NAME`
`delete e/EMAIL`
`delete p/PHONE`


* Deletes the person at the specified `INDEX`/`NAME`/`PHONE`/`EMAIL`

* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* If there are contacts with duplicate names, user must use `delete e/EMAIL` or `delete p/PHONE`

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find n/Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.
* `delete n/Betsy` will delete contact with the full name Betsy.
* `delete e/betsy@gmail.com` will delete the contact with the email betsy@gmail.com
* If there are two John Doe, one with `p/8834156` and another with `p/3810349`, type command`delete n/John Doe p/8834156` to delete the former.
work only` adds the remark "Available for part-time work only" to the 2nd person in the address book.


### Showing applicant statistics: `stats`

Format: 
`stats`

Shows the following statistics of the contact book at the time the command is called:
- Total number of applicants.
- Percentage of applicants in each interview stage regardless of job code.
- Number of applicants for each job code and each interview stage for that job code.

Examples:
`stats`
                           
### Bulk reject persons by criteria: `massreject`

Marks persons as "rejected" by updating their tags based on specified job code, tag, or a combination of both.

Format:
`massreject [j/JOB CODE] [t/TAG]`

* Updates contacts' tags to `r` (rejected) based on the specified criteria.
* You can filter by job code only, tag only, or a combination of both.
* If only a job code is provided, persons with the `a` (accepted) tag will be excluded from the update.

Examples:
* `massreject j/SWE2024 t/TP` marks all persons with the job code `SWE2024` and the tag `TP` as rejected.
* `massreject t/BP` marks all persons with the tag `BP` as rejected.
* `massreject j/SWE2024` marks all persons with the job code `SWE2024` as rejected, except those already tagged as `a` (accepted).

### Sorting persons by fields: `sort`

Sorts the list of persons based on specified fields in a case-insensitive manner. Sorting can be done by a single field or layered across multiple fields.

Format:
`sort [n/] [p/] [e/] [j/] [t/]`

* You can sort by any combination of name, phone, email, job code, and/or tag.
* Sorting is case-insensitive.
* Layered sorting is supported. If multiple fields are specified, the list will be sorted by the first field, then by the second within each group of the first, and so on.

Examples:
* `sort n/` and `sort` sorts all persons by name.
* `sort t/ j/` sorts all persons by tag first, and within each tag, sorts by job code.
* `sort j/ n/` sorts all persons by job code, and within each job code, sorts by name.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

Talentcy data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

Talentcy data are saved automatically as a JSON file `[JAR file location]/data/talentcy.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, Talentcy will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the Talentcy to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in the future]`

_Details coming soon ..._

-------------------------------------------------------------------------------------------------------------------- 

## Fields

<box type="info" seamless>  

**Notes about each valid input field:**<br>

### NAME
- Must be between 1 and 50 characters, excluding leading and trailing whitespaces.
- Must contain at least one letter.
- Can include letters, digits, spaces, hyphens (-), slashes (/), apostrophes ('), and periods (.).
- Cannot start or end with spaces.
- Input name will be converted to upper case. (e.g., "John Doe" will become "JOHN DOE")
- Symbols such as hyphens, slashes, apostrophes, and periods cannot have spaces directly before or after them (e.g., "JOHN -DOE" will be formatted to "JOHN-DOE").
- Consecutive spaces within the name are trimmed to one space. (e.g. "JOHN      DOE" will be trimmed to "JOHN DOE")

### PHONE
- Hyphens (-) are allowed in the input but will be trimmed away (e.g., "119-224-337" will be trimmed to "119224337").
- Only one leading plus sign (+) is allowed at the beginning of the phone number, but will be trimmed away. (e.g., "+999" will be trimmed to "999")
- Any whitespace between digits will be removed (e.g., "119 224 337" will be trimmed to "119224337").
- The phone number must consist of only numeric characters, with optional leading + and allowed hyphens.
- The length of the phone number (excluding leading + and hyphens) must be between 3 and 15 digits inclusive.
- Checking for valid country code is not in scope.

### EMAIL
- The email address must be in the format local-part@domain.
- The local part must contain only alphanumeric characters and the following special characters: +, _, ., and -. It cannot start or end with a special character.
- The domain part must consist of alphanumeric characters, with each domain label separated by periods.
- Each domain label must start and end with alphanumeric characters and may contain hyphens (-) but cannot start or end with them.
- The entire email address must not exceed 50 characters in length and cannot contain any spaces.
- All characters after the "@" symbol will be converted to lowercase (e.g., "hhh@GMAil.com" will become "hhh@gmail.com").
- Checking for valid email domain is not in scope.

### JOBCODE
- Must be at most 50 characters.
- Only alphanumeric characters (A-Z, a-z, 0-9) and single dashes (-) are allowed.
- The first and last character must be alphanumeric.
- Training spaces and spaces within jobcode will be removed. (e.g. " swe -201  " will become "swe-201")
- Two consecutive dashes (--) are not allowed (e.g., "HR--2023" is invalid).
- The input job code will be converted to uppercase (e.g., "swe2023-intern" will become "SWE2023-INTERN").

### TAG

Please refer to this table
for list of valid tags:

| Tag | Interview Stage                 | Definition                                                                  |
|-----|---------------------------------|-----------------------------------------------------------------------------|
| N   | New                             | New applicant                                                               |
| TP  | Technical Interview in Progress | Technical interview is in the process of being scheduled for the applicant  |
| TC | Technical Interview Confirmed | Technical interview has been schedule for the applicant                     |                                           
| BP | Behavioral Interview in Progress | Behavioral interview is in the process of being scheduled for the applicant |
| BC | Behavioral Interview Confirmed | Behavioral interview has been scheduled for the applicant                   |
| A| Accepted| Applicant has been accepted by the company                                  |
| R | Rejected | Applicant has been rejected by the company                                  |

- Input tag code is case-insensitive. (e.g., "bp" will be treated the same as "BP").
- Spaces within the tag will be removed (e.g., "T P" will become "TP"), and hence tags with spaces is still valid as long as it is one of the tags listed above. 


### REMARK
- Must be at most 50 characters, excluding leading and trailing whitespaces.
--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Talentcy home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the appli
2. cation to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE e/EMAIL j/JOB_CODE t/TAG r/REMARK` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com j/CS2103 t/R r/have-pHD`
**Clear**  | `clear`
**Delete** | `delete INDEX` e.g. `delete 3`<br>`delete n/NAME` e.g. `delete n/Alex Yeoh`<br> `delete n/NAME e/EMAIL` e.g. `delete n/Alex Yeoh e/alexyeoh@gmail.com` <br> `delete n/NAME p/PHONE` e.g. `delete n/Alex Yeoh p/88306733`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find n/FULL_NAME` `find j/JOB_CODE` `find t/TAG` `find n/FULL_NAME p/PHONE` `find n/FULL_NAME e/EMAIL`
**List**   | `list`
**Help**   | `help`
