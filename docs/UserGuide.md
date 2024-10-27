---
layout: page
title: User Guide
---

TalentConnect is a **desktop app catered to third-party recruiters, optimized for use via a Command Line Interface** (CLI) 
while still having the benefits of a Graphical User Interface (GUI). It boasts specialised features for 
managing your candidates, jobs, and companies, to help elevate your work in recruitment.
If you can type fast, TalentConnect can help you complete your recruitment management tasks 
faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

2. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103-F13-4/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list contact` : Lists all contacts.

   * `add contact n/John Doe p/98765432 e/johnd@example.com r/Software Engineer` : Adds a contact named `John Doe` to the Address Book.

   * `delete contact 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contents in the address book.

   * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add contact n/NAME`, `NAME` is a parameter which can be used as `add contact n/John Doe`.

* Items in square brackets are optional.<br>
  e.g. `n/NAME [s/SKILL]` can be used as `n/John Doe s/Python` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[s/SKILL]…​` can be used as ` ` (i.e. 0 times), `s/Python`, `s/Python s/Cuda` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a contact : `add contact`

Adds a contact to the address book.

Format: `add contact n/NAME p/PHONE_NUMBER e/EMAIL r/ROLE [s/SKILL]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A contact can have any number of skills (including 0)
</div>

Examples:
* `add contact n/John Doe p/98765432 e/johnd@example.com r/Software Engineer`
* `add contact n/Betsy Crowe s/Python e/betsycrowe@example.com r/Data Scientist p/1234567 s/Excel`

### Adding a job : `add job`

Adds a job to the address book. 
The company attributed to the job being added must already 
be in the address book.

Format: `add job n/NAME c/COMPANY s/SALARY d/DESCRIPTION [r/REQUIREMENT]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Make sure to match job names and requirements to contact roles and skills to better utilise other features
</div>

Examples:
* `add job n/Software Engineer c/Google s/100000 d/Looking for an exceptional individual`
* `add job n/Data Scientist c/Apple s/90000 d/Needs to know AI s/Python`

### Adding a company : `add company`

Adds a company to the address book.

Format: `add company n/NAME a/ADDRESS b/BILLING_DATE p/PHONE`

Examples:
* `add company n/Google a/70 Pasir Panjang Rd, #03-71 Mapletree Business City II, Singapore 117371 b/5 p/65218000`
* `add company n/Apple a/12 Ang Mo Kio Street 64, Singapore 569088 b/25 p/64815511`

### Listing all contacts : `list contact`

Shows a list of all contacts in the address book.

Format: `list contact`

### Listing all jobs : `list job`

Shows a list of all jobs in the address book.

Format: `list job`

### Listing all companies : `list company`

Shows a list of all companies in the address book.

Format: `list company`

### Editing a contact : `edit`

Edits an existing contact in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [s/SKILL]…​`

* Edits the contact at the specified `INDEX`. The index refers to the index number shown in the displayed contact list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing skills, the existing skills of the contact will be removed i.e. adding of skills is not cumulative.
* You can remove all the contact’s skills by typing `s/` without
    specifying any skills after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st contact to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower s/` Edits the name of the 2nd contact to be `Betsy Crower` and clears all existing skills.

### Locating contacts by name : `find`

Finds all contacts whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g. `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Contacts matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a contact : `delete contact`

Deletes the specified contact from the address book.

Format: `delete contact INDEX`

* Deletes the contact at the specified `INDEX`.
* The index refers to the index number shown in the displayed contact list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list contact` followed by `delete contact 2` deletes the 2nd contact in the address book.
* `find Betsy` followed by `delete contact 1` deletes the 1st contact in the results of the `find` command.

### Deleting a job : `delete job`

Deletes the specified job from the address book.

Format: `delete job INDEX`

* Deletes the job at the specified `INDEX`.
* The index refers to the index number shown in the displayed job list.
* The index **must be a positive integer** 1, 2, 3, …​
* Deleting a job already matched with a contact removes the match from the contact as well.

Examples:
* `list job` followed by `delete job 2` deletes the 2nd job in the address book.
* If the job at index 2 is matched with contact at index 1, `delete job 2` will remove the 
match from the contact at index 1.

### Deleting a company : `delete company`

Deletes the specified company from the address book.

Format: `delete company INDEX`

* Deletes the company at the specified `INDEX`.
* The index refers to the index number shown in the displayed company list.
* The index **must be a positive integer** 1, 2, 3, …​
* Deleting a company with jobs attributed to it also causes those jobs to be deleted.

Examples:
* `list company` followed by `delete company 2` deletes the 2nd company in the address book.
* If the company at index 1 has a job attributed to it, `delete company 1` will also delete the job.

### Screening contacts by a job's name : `screen job`

Screens the list of contacts in the address book with the name of the
job specified.

Format: `screen job INDEX`

* Uses the name of the job at the specified `INDEX` to filter the 
list of contacts to contacts with role matching the name.
* The index refers to the index number shown in the displayed job list.
* The index **must be a positive integer** 1, 2, 3, …​
* The filter is case-insensitive i.e. `Cleaner` will match `cleaner`.

Examples:
* If the job at index 1 has name `Software Engineer`, `screen job 1` will
show a contact with role `Software Engineer`.
* If the job at index 2 has name `Data Scientist`, `screen job 2` will 
show a contact with role `data scientist`.

### Matching a contact with a job : `match`

Matches a contact to a job.

Format: `match CONTACT_INDEX JOB_INDEX`

* Matches the contact at the specified `CONTACT_INDEX` and the job
at the specified `JOB_INDEX` together.
* The contact index refers to the index number shown in the displayed contact list.
* The job index refers to the index number shown in the displayed job list.
* Both indices **must be positive integers** 1, 2, 3, …​
* Both the contact and job specified **cannot already be matched**.

Examples:
* `match 1 2` will match the contact at index 1 and job at index 2 together.
* `match 2 3` will match the contact at index 2 and job at index 3 together.

### Undoing a matched contact and job : `unmatch`

Undoes a matching between a contact and job.

Format: `unmatch CONTACT_INDEX JOB_INDEX`

* Undoes the matching of a contact at the specified `CONTACT_INDEX` and a job
at the specified `JOB_INDEX`.
* The contact index refers to the index number shown in the displayed contact list.
* The job index refers to the index number shown in the displayed job list.
* Both indices **must be positive integers** 1, 2, 3, …​
* The contact and job **must already be matched together**.

Examples:
* `unmatch 2 2` will undo the matching between the contact at index 2 and
the job at index 2 if they were matched beforehand.
* If `match 1 2` was called previously, calling `unmatch 1 2` will undo the matching
if the shown list was in the same condition as when the previous command was called.

### Viewing jobs and contacts associated with a company : `view company`

Views all jobs and contacts currently associated with the specified company.

Format: `view company INDEX`

* Views all contacts and jobs associated with a company at the specified `INDEX`.
* The index refers to the index number shown in the displayed company list.
* The index **must be a positive integer** 1, 2, 3, …​
* Using this command **requires all lists to be fully shown beforehand**.

Examples:
* If a job is from a company at index 1, `view company 1` will show the job
in the job list.
* If a job is from a company at index 2 and the job is matched with a contact,
`view company 2` will show the job in the job list 
and the contact in the contact list.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file:<br>
`[JAR file location]/data/addressbook.json`.<br>
Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action     | Format, Examples                                                                                                                                                      |
|------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague` |
| **Clear**  | `clear`                                                                                                                                                               |
| **Delete** | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                   |
| **Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                           |
| **Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`                                                                                                            |
| **List**   | `list`                                                                                                                                                                |
| **Help**   | `help`                                                                                                                                                                |
