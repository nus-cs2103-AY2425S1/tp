---
layout: page
title: User Guide
---

HRConnect is a desktop app designed to streamline the allocation of human resources to projects within a company.

It is optimized for rapid use by project managers and office professionals who are skilled at typing, while still providing the benefits of a [*Graphical User Interface*](#graphical-user-interface) (GUI). If you prefer typing, HRConnect allows you to perform human resource management tasks much faster than other mouse-reliant apps.

---

<div markdown="block" class="alert alert-info">

**:information_source: Note on copying commands from PDF version of User Guide:**<br>

If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines, as space characters surrounding line-breaks may be omitted when copied over to the application.

</div>

<div markdown="block" class="alert alert-info">

**:information_source: Notes on User Guide formatting:**<br>

The user guide contains formatting to highlight important info. The standards used are as follows:

* `code typeface`: Commands or command formats
* **Bold:** Essential information
* <span style="color:red">RED COLOR</span>: Caution, take note
* [Hyperlink](#note-user-guide-formatting): Links to another section of the user guide, or a relevant page.
* [*Italics + Hyperlink*](#note-user-guide-formatting): Technical terms available in glossary

</div>

---

## Table of Contents

- [Table of Contents](#table-of-contents)
- [Quick Start](#quick-start)
  - [Some commands to get started](#some-commands-to-get-started)
- [Features](#features)
  - [Viewing help : `help`](#viewing-help--help)
  - [Employee commands](#employee-commands)
    - [Adding an employee : `add`](#adding-an-employee--add)
    - [Listing all employees : `listemployees`](#listing-all-employees--listemployees)
    - [Editing an employee : `edit`](#editing-an-employee--edit)
    - [Filtering employees by skills : `filter`](#filtering-employees-by-skills--filter)
    - [Locating employees by name : `find`](#locating-employees-by-name--find)
    - [Deleting an employee : `delete`](#deleting-an-employee--delete)
    - [Clearing all employee entries : `clear`](#clearing-all-employee-entries--clear)
  - [Project commands](#project-commands)
    - [Adding a project : `addproject`](#adding-a-project--addproject)
    - [Listing all projects : `listprojects`](#listing-all-projects--listprojects)
    - [Listing all members of a project : `listprojectmembers`](#listing-all-members-of-a-project--listprojectmembers)
    - [Editing a project : `editproject`](#editing-a-project--editproject)
    - [Finding projects by name : `findproject`](#finding-projects-by-name--findproject)
    - [Deleting a project : `deleteproject`](#deleting-a-project--deleteproject)
    - [Clear all project entries : `clearproject`](#clear-all-project-entries--clearproject)
  - [Assignment commands](#assignment-commands)
    - [Create assignment : `assign`](#create-assignment--assign)
    - [List assignments : `listassignments`](#list-assignments--listassignments)
    - [Delete assignment : `unassign`](#delete-assignment--unassign)
  - [Exiting the program : `exit`](#exiting-the-program--exit)
  - [Traversing command history](#traversing-command-history)
  - [Saving the data](#saving-the-data)
  - [Editing the data file](#editing-the-data-file)
  - [Editing the command history save file](#editing-the-command-history-save-file)
- [FAQ](#faq)
- [Known issues](#known-issues)
- [Command Summary](#command-summary)
  - [Summary of employee commands](#summary-of-employee-commands)
  - [Summary of project commands](#summary-of-project-commands)
  - [Summary of assignment commands](#summary-of-assignment-commands)
  - [Summary of other commands](#summary-of-other-commands)
- [Glossary](#glossary)
  - [Alphanumeric](#alphanumeric)
  - [Command word](#command-word)
  - [Graphical User Interface](#graphical-user-interface)
  - [Java](#java)
  - [JSON](#json)
  - [Numeric](#numeric)
  - [Prefix](#prefix)

---

## Quick Start

Start here if you are new to HRConnect.

1. Ensure you have **[*Java*](#java) 17 or above** installed in your computer.

2. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-T15-4/tp/releases).

3. Copy the file to the folder you want to use as the home folder for your HRConnect. **This folder will be used to store saved data and preferences.**

4. **Double-click the `.jar` file** in the folder you placed.
   - (If this does not work: Open a command terminal, use the command `cd [folder path]` to navigate into the folder you put the `.jar` file in, and use the command `java -jar HRConnect.jar` to run the application.)

A GUI similar to the screenshot below should appear in a few seconds. Note how the app contains some sample data.

![ui example](images/uiExample.png)

**HRConnect keeps track of Employees, Projects and the assignments between them.** HRConnect commands are tailored for managing these three items easily.

Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>

### Some commands to get started

   - `listemployees` : Lists all employees.

   - `add id/6 n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   - `delete 3` : Deletes the 3rd contact in the displayed list.

   - `clear` : Deletes all contacts.

   - `listprojects`: Lists all projects.

   - `addproject pid/3 pn/Project Charlie s/Backend`: Adds a project named `Project Charlie` with skill `Backend`.

   - `deleteproject 3`: Deletes the 3rd project in the displayed project list.

   - `clearproject` : Deletes all projects.

   - `assign aid/1 pid/3 id/1` : Assigns `Bernice Yu` to `Project Charlie`.

   - `listassignments` : Lists all assignments.

   - `unassign aid/1` : Undoes the assignment with Assignment ID 1.

   - `exit` : Exits the app.

Refer to the [Features](#features) below for details of each command.

[Return to Top](#table-of-contents)

---

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

- Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  - e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

- Items in square brackets are optional.<br>
  - e.g. `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

- Items with `…`​ after them can be used multiple times including zero times.<br>
  - e.g. `[t/TAG]…​` can be left out (i.e. used 0 times), used as `t/friend`, `t/friend t/family` etc.

- Items can be in any order.<br>
  e.g. `n/NAME p/PHONE_NUMBER` and `p/PHONE_NUMBER n/NAME` are the same.

- Unnecessary parameters (i.e. not mentioned in command format) will be ignored.<br>

- [*Command words*](#command-word) and [*prefixes*](#prefix) are case-sensitive.<br>
  e.g. `EDIT 1 n/John` and `edit 1 N/John` will raise an error.

</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

[Return to Top](#table-of-contents)

### Employee commands

#### Adding an employee : `add`

**Adds an employee** to the address book.

Format: `add id/EMPLOYEE_ID n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]… [s/SKILL]…​`

- Employee Id must be: [*Numeric*](#numeric), no spaces, unique amongst employees
  - Employee IDs uniquely identify each employee.
  - Employee IDs are compared numerically. `0001` is treated the same as `1`.

- Name must be: [*Alphanumeric*](#alphanumeric), spaces allowed
  - Names which require special characters should be spelt out in full. e.g. `Nagaratnam s/o Suppiah` should be spelt out as `Nagaratnam son of Suppiah`

- Phone Number must be: [*Numeric*](#numeric), no spaces, at least 3 digits long
- Email must be: of the format `local-part@domain` and follow these constraints:
  - The local-part should only contain alphanumeric characters and the special characters +_.-
  - The local-part may not start or end with special characters.
  - This is followed by a '@' and then a domain name. The domain name is made up of domain labels separated by periods (e.g. `u.nus.edu`).
- Address must be: Any characters are valid (except for `/`)
- Skills and tags must be: [*Alphanumeric*](#alphanumeric), no spaces, each should be 50 characters or fewer

<div markdown="block" class="alert alert-info">

**:information_source: Note employee details:**<br>

Since each employee is uniquely identified by their employee ID, users can add employees who share the same name, or who have a common department-wide phone number, email, or address.

</div>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
An employee can have any number of tags (including 0) and any number of skills (including 0)
</div>

Examples:

- `add id/1 n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
- `add id/2 n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal s/lockpicking`

Expected output:
- System message noting success and details of employee edited

[Return to Top](#table-of-contents)

#### Listing all employees : `listemployees`

Shows a **list of all employees** in the address book.

Format: `listemployees`

Expected output:
- System message noting success
- All employees shown in the displayed employee list
- Result for `listemployees` with sample data:
![result for `listemployees`](images/listEmployees.png)

[Return to Top](#table-of-contents)

#### Editing an employee : `edit`

**Edits an existing employee** in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]… [s/SKILL]…​`

- Edits the employee at the specified `INDEX`. The index refers to the index number shown in the displayed employee list. The index **must be a positive integer** 1, 2, 3, …​
- At least one of the optional fields must be provided.
- Existing values will be updated to the input values.
- When editing tags, the existing tags of the employee will be removed i.e. adding of tags is not cumulative.
- When editing skills, the existing skills of the employee will be removed i.e. adding of skills is not cumulative.
- You can remove all the employee’s tags by typing `t/` without
  specifying any tags after it.
- You can remove all the employee’s skills by typing `s/` without
  specifying any skills after it.
- You cannot edit an employee's employee id. More specifically, you are not allowed to specify `id/EMPLOYEEID` in the `edit` command.

Examples:

- `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st employee to be `91234567` and `johndoe@example.com` respectively.
- `edit 2 n/Betsy Crower t/` Edits the name of the 2nd employee to be `Betsy Crower` and clears all existing tags.

Expected output:
- System message noting success and details of employee edited

[Return to Top](#table-of-contents)

#### Filtering employees by skills : `filter`

**Finds employees who have at least one skill or tag matching** at least one of the search items.

Format: `filter [s/SKILL]... [t/TAG]...`

- The search is case-insensitive. e.g. `s/webdev` will match `s/WebDev`.
- The order of the search items does not matter. e.g. `s/frontend s/backend t/swe t/devops` will also match contacts with `t/swe s/frontend t/devops s/backend`.
- Only the skills and tags are searched.
- Only full words will be matched. e.g. `s/database` will not match the skill `databases`.
- All employees who have at least one skill or tag matching any one search item will be returned.

Examples:

- `filter s/frontend` returns all employees with the skill `frontend`.
- `filter s/frontend t/swe` returns all employees who have either the skill `frontend`, the tag `swe`, or both.

Expected output:
- System message noting success
- Employees with skills above shown in the displayed employee list
- Result for `filter t/colleagues`:
![result for 'filter t/colleagues'](images/filterColleagues.png)

[Return to Top](#table-of-contents)

#### Locating employees by name : `find`

**Finds employees whose names contain any of the given keywords.**

Format: `find KEYWORD [MORE_KEYWORDS]`

- The search is case-insensitive. e.g. `hans` will match `Hans`
- The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
- Only the name is searched.
- Only full words will be matched. e.g. `Han` will not match `Hans`
- Employees matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:

- `find John` returns `john` and `John Doe`
- `find alex david` returns `Alex Yeoh`, `David Li`<br>

Expected output:
- System message noting success
- Employees matching criteria above shown in the displayed employee list
- Result for `find alex david` with sample data:
![result for 'find alex david'](images/findAlexDavidResult.png)

[Return to Top](#table-of-contents)

#### Deleting an employee : `delete`

**Deletes the specified employee <span style="color:red">and all its assignments</span>** from the address book.

Format: `delete INDEX`

- Deletes the employee at the specified `INDEX`.
- The index refers to the index number shown in the displayed employee list.
- The index **must be a positive integer** 1, 2, 3, …​

Examples:

- `listemployees` followed by `delete 2` deletes the 2nd employee in the address book.
- `find Betsy` followed by `delete 1` deletes the 1st employee in the results of the `find` command.

Expected output:
- System message noting success
- Deleted employee vanishes from displayed employee list

[Return to Top](#table-of-contents)

#### Clearing all employee entries : `clear`

**Clears all employee entries** from the address book.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
This deletes all employee data AND their assignments!
</div>

Format: `clear`

Expected output:
- System message noting success
- Displayed employee list becomes empty

[Return to Top](#table-of-contents)

### Project commands

#### Adding a project : `addproject`

**Adds a new project** to HRConnect.

Format: `addproject pid/PROJECT_ID pn/PROJECT_NAME [s/SKILL]...`

- Project Id must be: [*Numeric*](#numeric), no spaces, unique amongst projects
  - Project IDs uniquely identify each project.
  - Project IDs are compared numerically. `0001` is treated the same as `1`.

- Project Name must be: [*Alphanumeric*](#alphanumeric), spaces allowed
- Skills must be: [*Alphanumeric*](#alphanumeric), no spaces, each should be 50 characters or fewer

Examples:

- `addproject pid/1 pn/Project Alpha`
- `addproject pid/2 pn/Website UI Overhaul s/Frontend s/React`

Expected output:
- System message noting success and id \+ name of project added

[Return to Top](#table-of-contents)

#### Listing all projects : `listprojects`

Shows a list of **all stored projects**.

Format: `listprojects`

Expected output:
- System message noting success, including number of projects listed
- All projects shown in the displayed project list
- Result of `listprojects` with sample data:
![result for `listprojects`](images/listProjects.png)

[Return to Top](#table-of-contents)

#### Listing all members of a project : `listprojectmembers`

Shows a list of **all project members of the specified project**.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Users can use the `listemployees` and `listassignments` commands to reset the display to show all employees and assignments again.
</div>

Format: `listprojectmembers pn/PROJECT_NAME`

- PROJECT_NAME is case-sensitive. e.g. `Project` will not match `project`
- Only the full project name will be matched. e.g. `Project` or `Alpha` will not match `Project Alpha`

Expected output:
- System message noting success, including number of members listed
- All members shown in the displayed member list

[Return to Top](#table-of-contents)

#### Editing a project : `editproject`

**Edits an existing project** in the address book.

Format: `editproject INDEX [pn/NAME] [s/SKILL]…​`

- Edits the project at the specified `INDEX`. The index refers to the index number shown in the displayed project list. The index **must be a positive integer** 1, 2, 3, …​
- At least one of the optional fields must be provided.
- Existing values will be updated to the input values.
- When editing skills, the existing skills of the project will be removed i.e. adding of skills is not cumulative.
- You can remove all the project’s skills by typing `s/` without
  specifying any skills after it.
- You cannot edit a project's id. More specifically, you are not allowed to specify `pid/PROJECTID` in the `edit` command.

Examples:

- `editproject 1 pn/ALPHA ` Edits the project name of the 1st project to be `ALPHA`.
- `editproject 2 s/Cybersecurity` Edits the skill of the 2nd project to be `Cybersecurity`.

Expected output:

- System message success and details of project edited

[Return to Top](#table-of-contents)

#### Finding projects by name : `findproject`

**Finds projects whose names contain any of the given keywords**.

Format: `findproject KEYWORD [MORE_KEYWORDS]`

- The search is case-insensitive. e.g. `Project` will match `project`
- The order of the keywords does not matter. e.g. `Project Alpha` will match `Alpha Project`
- Only the project name is searched.
- Only full words will be matched. e.g. `Proj` does not match `Project`
- Any project matching at least one keyword will be returned. e.g. `Project` will return `Project Alpha` and `Project Beta`, etc

Examples:

- `findproject project`
- `findproject Alpha Beta`

Expected output:
- System message noting success
- Projects matching criteria above shown in the displayed project list

[Return to Top](#table-of-contents)

#### Deleting a project : `deleteproject`

**Deletes the specified project <span style="color:red">and all its assignments</span>** from HRConnect.

Format: `deleteproject INDEX`

- Deletes the project at the specified `INDEX`
- The index refers to the **index number shown in the displayed project list.**
- The index must be a positive integer 1, 2, 3, …​

Examples:

- `listprojects` followed by `deleteproject 2` deletes the **2nd project shown**.
- `findproject Alpha` followed by `deleteproject 1` deletes the **1st project in the results** of the `findproject` command.

Expected output:

- System message noting success
- Deleted project vanishes from displayed project list

[Return to Top](#table-of-contents)

#### Clear all project entries : `clearproject`

**Clears all project entries** from HRConnect.<br>

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
This will delete all project records AND their assignments!
</div>

Format: `clearproject`

Expected output:
- System message noting success
- Displayed project list becomes empty

[Return to Top](#table-of-contents)

### Assignment commands

#### Create assignment : `assign`

**Creates an assignment** between an Employee and a Project.

Format: `assign aid/ASSIGNMENT_ID pid/PROJECT_ID id/EMPLOYEE_ID`

- Assignment Id must be: [*Numeric*](#numeric), no spaces, unique amongst assignments
  - Assignment IDs uniquely identify each assignment.
  - Assignment IDs are compared numerically. `0001` is treated the same as `1`.

- The `PROJECT_ID` must belong to an existing project.
- The `EMPLOYEE_ID` must belong to an existing employee.
- There must not be an existing assignment with the same `PROJECT_ID` and `EMPLOYEE_ID`.

Examples:

- `assign aid/1 pid/1 id/1`

Expected output:

- System message noting success and information about assignment added

[Return to Top](#table-of-contents)

#### List assignments : `listassignments`

Shows a list of **all stored assignments**.

Format: `listassignments`

Expected output:

- System message noting success
- All assignments shown in the assignment display list
- Result of `listassignments` with sample data:
![result of `listassignments`](images/listAssignments.png)

[Return to Top](#table-of-contents)

#### Delete assignment : `unassign`

**Deletes an assignment** between an Employee and a Project.<br>
This does not delete the Employee nor the Project.

Format: `unassign aid/ASSIGNMENT_ID`

- The `ASSIGNMENT_ID` must belong to an existing assignment.

Examples:

- `unassign aid/1`

Expected output:

- System message noting the `ASSIGNMENT_ID` of the assignment removed.

[Return to Top](#table-of-contents)

### Exiting the program : `exit`

**Exits the program.**

Format: `exit`

[Return to Top](#table-of-contents)

### Traversing command history

HRConnect keeps a log of past commands entered, up to a maximum of the 50 latest commands. To access past commands, make sure that your cursor is in the command box (indicated within the red box below), then press the up (↑) and down (↓) arrow keys.

![cursorInCommandBox.png](images/cursorInCommandBox.png)

- Press the up arrow key (↑) to navigate to the previous command.
- Press the down arrow key (↓) to navigate to the next command.

Previously entered commands are saved to disk (up to a maximum of 50 commands). Users can navigate to previous commands from earlier sessions as commands are saved across exit(s) of the application.

[Return to Top](#table-of-contents)

### Saving the data

HRConnect data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

[Return to Top](#table-of-contents)

### Editing the data file

![rootFileStructure.png](images/rootFileStructure.png)

HRConnect data is saved automatically as a [*JSON*](#json) file `[JAR file location]/data/hrconnect.json`.  
Advanced users are welcome to update data directly by editing this data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, HRConnect will discard all data and start with an empty data file at the next run. Hence, it is recommended to backup the file before editing it.<br>

Furthermore, certain edits can cause HRConnect to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly. <br>

If you decide to edit employee and project IDs directly in the data file, take extra caution and make sure that relevant assignments are edited to reflect the updated IDs.
</div>

[Return to Top](#table-of-contents)

### Editing the command history save file

Similar to [Editing the data file](#editing-the-data-file), advanced users are welcome to update their command history directly by editing `[JAR file location]/data/commandtexthistory.json`.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to this file makes its format invalid, HRConnect will discard all data and start with an empty file at the next run. Hence, it is recommended to backup the file before editing it.<br>

Like the data file, certain edits to this file can cause HRConnect to behave in unexpected ways. Therefore, edit the command history file only if you are confident of updating it correctly. <br>

</div>

---

## FAQ

**Q**: How do I install Java?<br>
**A**: Download the installer (.exe or .msi) from [here](https://www.oracle.com/sg/java/technologies/downloads/#java17). Click on the downloaded file and follow the instructions to install.

**Q**: Where is my data stored?<br>
**A**: It is stored in `hrconnect.json`. This is located in the `data` subfolder, in the folder you put `HRConnect.jar` in.
![rootFileStructure.png](images/rootFileStructure.png)

**Q**: How do I transfer my data to another computer?<br>
**A**: Install the app in the other computer and overwrite the data file it creates with the file that contains the data of your previous HRConnect home folder.

**Q**: I don't see the data files anywhere.<br>
**A**: You may need to run the app for the first time and run any command (such as `exit`) to generate these files.

[Return to Top](#table-of-contents)

---

## Known issues

1. **If your computer has multiple displays**, if you move the application to a secondary display, and later switch to using only the primary display, the GUI will open off-screen. The user should delete the `preferences.json` file created by the application (in the same folder as the `HRConnect.jar` file) before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.
3. **Running and using multiple instances of HRConnect at the same time** will result in unpredictable behavior, including inconsistent data saving and displaying.

[Return to Top](#table-of-contents)

---

## Command Summary

### Summary of employee commands

| Action                          | Format, Examples                                                                                                                                                                                                         |
|---------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Employee**                | `add id/EMPLOYEEID n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]… [s/SKILL]…​` <br> e.g., `add id/6 n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague s/database s/backend` |
| **Clear Employees**             | `clear`                                                                                                                                                                                                                  |
| **Delete Employee**             | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                                                                      |
| **Edit Employee**               | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]… [s/SKILL]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                                                                   |
| **Filter Employees (by skill)** | `filter [s/SKILL]... [t/TAG]...`<br> e.g., `filter s/frontend t/swe`                                                                                                                                                     |
| **Find Employees (by name)**    | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`                                                                                                                                                               |
| **List Employees**              | `listemployees`                                                                                                                                                                                                          |

[Return to Top](#table-of-contents)

### Summary of project commands

| Action                          | Format, Examples                                                                                                  |
|---------------------------------|-------------------------------------------------------------------------------------------------------------------|
| **Add Project**                 | `addproject pid/PROJECT_ID pn/PROJECT_NAME [s/SKILL]...`<br> e.g., `addproject pid/1 pn/Project Alpha s/Frontend` |
| **Clear Projects**              | `clearproject`                                                                                                    |
| **Delete Project**              | `deleteproject INDEX`<br> e.g., `deleteproject 2`                                                                 |
| **Edit Project**                | `editproject INDEX [pn/NAME] [s/SKILL]…​`<br> e.g.,`editproject 1 pn/Project Alpha s/Cybersecurity`               |
| **Find Projects**               | `findproject KEYWORD [MORE_KEYWORDS]`<br> e.g., `findproject Alpha Beta`                                          |
| **List Projects**               | `listprojects`                                                                                                    |
| **List All Members of Project** | `listprojectmembers pn/PROJECT_NAME` <br> e.g., `listprojectmembers pn/Project Alpha`                             |

[Return to Top](#table-of-contents)

### Summary of assignment commands

| Action                          | Format, Examples                                                                                                                                                                                                         |
|---------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Assignment**              | `assign aid/ASSIGNMENT_ID pid/PROJECT_ID id/EMPLOYEE_ID`<br> e.g., `assign aid/1 pid/1 id/1`                                                                                                                             |
| **Delete Assignment**           | `unassign aid/ASSIGNMENT_ID`<br> e.g., `unassign aid/1`                                                                                                                                                                  |
| **List Assignments**            | `listassignments`                                                                                                                                                                                                        |

[Return to Top](#table-of-contents)

### Summary of other commands

| Action                          | Format, Examples                                                                                                                                                                                                         |
|---------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Help**                        | `help`                                                                                                                                                                                                                   |
| **Exit Program**                | `exit`                                                                                                                                                                                                                   |

[Return to Top](#table-of-contents)

---

## Glossary

List of certain technical terms / uncommon words used in this user guide.

### Alphanumeric
- Consisting of the letters A to Z (upper/lowercase) and the digits 0 to 9.

### Command word
- Word used at the beginning of each command denoting the action performed. Examples: `add`, `listemployees`, `filter`.

### Graphical User Interface
- A digital interface in which a user interacts with graphical components such as icons, buttons, and menus. Example: Windows 11, most websites

### Java
- The programming language HRConnect is primarily developed with.
- It can be downloaded from [here](https://www.oracle.com/sg/java/technologies/downloads/#java17) (scroll down to get to the Java 17 downloads) and installed before attempting to run HRConnect.

### JSON
- JavaScript Object Notation, a format for storing and transferring data.
- It can be opened in Notepad, or other text or code editors.

### Numeric
- Consisting only of the digits 0 to 9.
- Does not include the negative sign (-), the decimal point (.), spaces, or other symbols.

### Prefix
- 1 or more characters, followed by a `/`.
- Used in commands to indicate a value supplied by the user. Examples: `n/`, `id/`, `pid/`.

[Return to Top](#table-of-contents)
