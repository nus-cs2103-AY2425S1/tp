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

3. Copy the file to the folder you want to use as the _home folder_ for your TalentConnect.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar talentconnect.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list contact` : Lists all contacts.

   * `add contact n/John Doe p/98765432 e/johnd@example.com r/Software Engineer` : Adds a contact named `John Doe` to the Address Book.

   * `delete contact 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contents in the address book.

   * `exit` : Exits the app.

6. Refer to the sections below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Overview
TalentConnect offers management of multiple entities that work together to better help our users. To facilitate this, the commands in app can be categorized into two types, entity specific and non-entity specific. For entity specific commands, it is required to supply an entity after the action in the command. e.g. `add contact ...` command has an entity `contact` after the action `add`. 

The valid entities in the system are `contact`, `job`, `company` and `all`. The first three of those refers to a specific entity, while `all` is used in certain commands to refer to all three entities. Do note that not all commands/actions work on all valid entities, kindly refer to [Command Summary](#command-summary) for the summary of what entities are supported in each command/action.

Entities `contact`, `job`, `company` are displayed in their corresponding lists, as shown below. 
![Overview](images/Overview.png)

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add contact n/NAME`, `NAME` is a parameter which can be used as `add contact n/John Doe`.

* Commands, not including their parameters, are case-insensitive.<br>
  e.g. if the command specifies `adD CONTact ...`, it will be interpreted as `add contact ...`. 
 
* `lower_case` items in square brackets are mandatory keyword, separated by slashes. <br>
  e.g. `add [contact/job/company]` can be used as  `add contact n/...` or as `add job n/...`, but not `add n/...`.

* `UPPER_CASE` items in square brackets are optional parameters.<br>
  e.g. `n/NAME [s/SKILL]` can be used as `n/John Doe s/Python` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[s/SKILL]…​` can be used as ` ` (i.e. 0 times), `s/Python`, `s/Python s/Cuda` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE`, `p/PHONE n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

## Contact Management
Contacts represent candidates applying for job positions. Each contact can have details such as name, phone, email, and relevant skills, aiding in recruitment processes. A contact's role represents the job that they are applying for, and is used when screening contacts with a certain job. A contact is uniquely identified by their phone number and email, therefore no two contacts can share the same phone number or email. 

* Add Contact: `add contact n/NAME p/PHONE e/EMAIL r/ROLE [s/SKILL]…​`
* Edit Contact: `edit contact INDEX [n/NAME] [p/PHONE] [e/EMAIL] [s/SKILL]…​`
* Find Contact: `find contact KEYWORD [MORE_KEYWORDS]`
* List Contacts: `list contact`
* Delete Contact: `delete contact INDEX`

### Adding a contact : `add contact`

Adds a contact to the address book.

Format: `add contact n/NAME p/PHONE e/EMAIL r/ROLE [s/SKILL]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A contact can have any number of skills (including 0)
</div>

Examples:
* `add contact n/John Doe p/98765432 e/johnd@example.com r/Software Engineer`
* `add contact n/Betsy Crowe s/Python e/betsycrowe@example.com r/Data Scientist p/1234567 s/Excel`

### Editing a contact : `edit contact`

Edits an existing contact in the address book.

Format: `edit contact INDEX [n/NAME] [p/PHONE] [e/EMAIL] [s/SKILL]…​`

* Edits the contact at the specified `INDEX`. The index refers to the index number shown in the displayed contact list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing skills, the existing skills of the contact will be removed i.e. adding of skills is not cumulative.
* You can remove all the contact’s skills by typing `s/` without
  specifying any skills after it.

Examples:
*  `edit contact 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st contact to be `91234567` and `johndoe@example.com` respectively.
*  `edit contact 2 n/Betsy Crower s/` Edits the name of the 2nd contact to be `Betsy Crower` and clears all existing skills.

### Locating contacts by name : `find contact`

Finds all contacts whose names contain any of the given keywords.

Format: `find contact KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g. `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Contacts matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find contact John` returns `john` and `John Doe`
* `find contact alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Listing all contacts : `list contact`

Shows all contacts in the address book in the contact list.

Format: `list contact`

<div markdown="span" class="alert alert-primary">:information_source: **Note:** 
If all contacts are already displayed, nothing will appear to be changed. 
</div>

### Deleting a contact : `delete contact`

Deletes the specified contact from the address book.

Format: `delete contact INDEX`

* Deletes the contact at the specified `INDEX`.
* The index refers to the index number shown in the displayed contact list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list contact` followed by `delete contact 2` deletes the 2nd contact in the address book.
* `find contact Betsy` followed by `delete contact 1` deletes the 1st contact in the results of the `find` command.


## Company Management 
Companies are organizations that provide job positions. Each company can have attributes such as address, billing date, and contact details, facilitating job creation and matching. A company is uniquely identified by its name (case-insensitive). 

* Add Company: `add company n/NAME a/ADDRESS b/BILLING_DATE p/PHONE`
* View Company: `view company INDEX`
* List Companies: `list company`
* Delete Company: `delete company INDEX`

### Adding a company : `add company`

Adds a company to the address book.

Format: `add company n/NAME a/ADDRESS b/BILLING_DATE p/PHONE`

* The billing date is a number from 1 to 28 that signifies the day of the month
  (with reference to the start of the month) that a company will pay their
  employees' salaries.
* Future enhancement: accommodate for more billing date formats (reference from end of the month).

Examples:
* `add company n/Google a/70 Pasir Panjang Rd, #03-71 Mapletree Business City II, Singapore 117371 b/5 p/65218000`
* `add company n/Apple a/12 Ang Mo Kio Street 64, Singapore 569088 b/25 p/64815511`

### Viewing a company's details : `view company`

Views all jobs and contacts currently associated with the specified company, as well as information about the company.

![ViewCompanyImage](images/ViewCompanyImage.png)

Format: `view company INDEX`

* Views the information of a company at the specified `INDEX`.
* Views all contacts and jobs associated with a company at the specified `INDEX`.
* The index refers to the index number shown in the displayed company list.
* The index **must be a positive integer** 1, 2, 3, …​
* Note: It is not necessary to display all contacts and jobs before using this command; the command will work regardless of whether lists are fully shown.

Examples:
* If a job is from a company at index 1, `view company 1` will show the job
  in the job list.
* If a job is from a company at index 2 and the job is matched with a contact,
  `view company 2` will show the job in the job list
  and the contact in the contact list.

### Listing all companies : `list company`

Shows all companies in the address book in the company list.

Format: `list company`

<div markdown="span" class="alert alert-primary">:information_source: **Note:** 
If all companies are already displayed, nothing will appear to be changed. 
</div>


### Deleting a company : `delete company`

Deletes the specified company from the address book.

Format: `delete company INDEX`

* Deletes the company at the specified `INDEX`.
* The index refers to the index number shown in the displayed company list.
* The index **must be a positive integer** 1, 2, 3, …​
* Deleting a company with jobs attributed to it also causes those jobs to be deleted. Any contacts that are matched to those jobs will not be deleted, however they will be unmatched.

Examples:
* `list company` followed by `delete company 2` deletes the 2nd company in the address book.
* If the company at index 1 has a job attributed to it, `delete company 1` will also delete the job.

## Job Management
Jobs are positions offered by companies that contacts can apply for. Jobs contain details like monthly salary, description, and requirements, enabling precise candidate matching. A job is uniquely identified by its name and company. Multiple openings for the same job position at a company can be represented using a single job, hence duplicates are not allowed.

* Add Job: `add job n/NAME c/COMPANY s/MONTHLY_SALARY d/DESCRIPTION [r/REQUIREMENT]…​`
* List Jobs: `list job`
* Delete Job: `delete job INDEX`
* Screen Contacts by Job: `screen job INDEX`

### Adding a job : `add job`

Adds a job to the address book.

Format: `add job n/NAME c/COMPANY s/MONTHLY_SALARY d/DESCRIPTION [r/REQUIREMENT]…​`

* `COMPANY` must match the name of an existing company in the address book, ignoring case sensitivity.
* A job with the same `NAME` and `COMPANY` as an existing job is considered a duplicate and cannot be added.
* It is acceptable for a job to have the same `NAME` if it has a different `COMPANY` and vice versa.

Examples:
* `add job n/Software Engineer c/Google s/100000 d/Looking for an exceptional individual`
* `add job n/Data Scientist c/Apple s/90000 d/Needs to know AI r/Python`

### Listing all jobs : `list job`

Shows all jobs in the address book in the job list.

Format: `list job`

<div markdown="span" class="alert alert-primary">:information_source: **Note:** 
If all jobs are already displayed, nothing will appear to be changed. 
</div>


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

### Screening contacts by a job : `screen job`

Screens the list of contacts in the address book with the job specified.  

Format: `screen job INDEX`

* Uses the name of the job at the specified `INDEX` to filter the
  list of contacts to contacts with role matching the name.
* Contacts that are already matched will not be shown.
* This allows the user to narrow down a list of contacts that are currently eligible or fit the job.
* The index refers to the index number shown in the displayed job list.
* The index **must be a positive integer** 1, 2, 3, …​
* The filter is case-insensitive and can be partial. e.g. Role `software engineer` will match to job name `Junior Java Software Engineer, Depart...`

Examples:
* If the job at index 1 has name `Software Engineer`, `screen job 1` will
  show a contact with role `Software Engineer`.
* If the job at index 2 has name `Data Scientist`, `screen job 2` will
  show a contact with role `data scientist`.

## Other Commands
Additional commands offer general functionality to manage and analyze data across TalentConnect, including clearing data, generating statistics, and accessing help.

* Match Contact to Job: `match CONTACT_INDEX JOB_INDEX`
* Unmatch Contact from Job: `unmatch CONTACT_INDEX JOB_INDEX`
* List All Entities: `list all`
* View Statistics: `stats`
* Clear Data: `clear`
* Exit Application: `exit`
* Help: `help`

### Matching a contact with a job : `match`

Matches a contact to a job.

Format: `match CONTACT_INDEX JOB_INDEX`

* Matches the contact at the specified `CONTACT_INDEX` and the job
  at the specified `JOB_INDEX` together.
* The contact index refers to the index number shown in the displayed contact list.
* The job index refers to the index number shown in the displayed job list.
* Both indices **must be positive integers** 1, 2, 3, …​
* The contact **must not already be matched**.
* Each contact is only allowed to match with one job.
* A job is allowed to be matched by multiple contacts, as some jobs have multiple openings.

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

### Listing all entities : `list all`

Show all contact, jobs and companies in the address book.

Format: `list all`

<div markdown="span" class="alert alert-primary">:information_source: **Note:** 
If all contacts, jobs and companies are already displayed, nothing will appear to be changed. 
</div>

### Viewing statistics : `stats`

Shows a summary of the data, including the total number of contacts, jobs, and companies in the address book.
Additionally, it provides a breakdown of matched and unmatched contacts, offering insights
into recruitment activity and data utilization.

Format: `stats`

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

## Miscellaneous

### Saving the data

TalentConnect data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

TalentConnect data are saved automatically as a JSON file:<br>

`[JAR file location]/data/talentconnect.json`.<br>
Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, TalentConnect will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the TalentConnect to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous TalentConnect home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action          | Format, Examples                                                                                                                                                                 |
|-----------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Contact** | `add contact n/NAME p/PHONE e/EMAIL r/ROLE [s/SKILL]…​` <br> e.g., `add contact n/James Ho p/22224444 e/jamesho@example.com r/copywriter s/MSWord`                               |
| **Add Job**     | `add job n/NAME c/COMPANY s/MONTHLY_SALARY d/DESCRIPTION [r/REQUIREMENT]…​` <br> e.g., `add job n/Software Engineer c/Google s/2000 d/Needs to know AI r/Python`                 |
| **Add Company** | `add company n/NAME a/ADDRESS b/BILLING_DATE p/PHONE` <br> e.g., `add company n/Google a/70 Pasir Panjang Rd #03-71 Mapletree Business City II, Singapore 117371 b/5 p/65218000` |
| **List**        | `list [contact/job/company/all]`                                                                                                                                                 |
| **Clear**       | `clear`                                                                                                                                                                          |
| **Delete**      | `delete [contact/job/company] INDEX`<br> e.g., `delete contact 3`, `delete job 2`, `delete company 2`                                                                            |
| **Screen**      | `screen [job] INDEX` <br> e.g., `screen job 1`                                                                                                                                   |
| **Match**       | `match CONTACT_INDEX JOB_INDEX` <br> e.g., `match 1 2`                                                                                                                           |
| **Unmatch**     | `unmatch CONTACT_INDEX JOB_INDEX` <br> e.g., `unmatch 3 2`                                                                                                                       |
| **View**        | `view [company] INDEX` <br> e.g., `view company 1`                                                                                                                               |
| **Edit**        | `edit [contact] INDEX [n/NAME] [p/PHONE] [e/EMAIL] [s/SKILL]…​`<br> e.g.,`edit contact 2 n/James Lee e/jameslee@example.com`                                                     |
| **Find**        | `find [contact] KEYWORD [MORE_KEYWORDS]`<br> e.g., `find contact James Jake`                                                                                                     |
| **Help**        | `help`                                                                                                                                                                           |
| **Exit**        | `exit`                                                                                                                                                                           |
| **Stats**       | `stats`                                                                                                                                                                          |

