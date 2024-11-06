layout: default.md
title: "User Guide"
pageNav: 3
---

# TechConnect User Guide

TechConnect is a **desktop app for managing internship applications, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, TechConnect can help you manage your applications more efficiently than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

---

# Table of Contents

1. [Quick start](#quick-start)
2. [Features](#features)
    1. [Viewing help : `help`](#viewing-help--help)
    2. [Adding a company: `add`](#adding-a-company-add)
    3. [Listing all companies : `list`](#listing-all-companies--list)
    4. [Bookmarking a company : `bookmark`](#bookmarking-a-company--bookmark)
    5. [Removing a bookmark from a bookmarked company : `removebm`](#removing-a-bookmark-from-a-bookmarked-company--removebm)
    6. [Listing all bookmarked companies : `bmlist`](#listing-all-bookmarked-companies--bmlist)
    7. [Editing a company : `edit`](#editing-a-company--edit)
    8. [Editing a company's remark : `remark`](#editing-a-companys-remark--remark)
    9. [Locating companies by name: `find`](#locating-companies-by-name-find)
    10. [Deleting a company : `delete`](#deleting-a-company--delete)
    11. [Editing an application status: `status`](#editing-an-application-status-status)
    12. [Clearing all entries : `clear`](#clearing-all-entries--clear)
    13. [Exiting the program : `exit`](#exiting-the-program--exit)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

2. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g., typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    - `list` : Lists all companies.

    - `add n/Google p/98765432 e/google@example.com a/John street, block 123, #01-01 cp/www.google-career-url.com` : Adds a company named `Google` to the Address Book.

    - `delete 3` : Deletes the 3rd company shown in the current list.

    - `clear` : Deletes all companies.

    - `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

---

## Features

**Notes about the command format:**<br>

- Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g., in `add n/NAME`, `NAME` is a parameter which can be used as `add n/Google`.

- Items in square brackets are optional.<br>
  e.g., `n/NAME [t/TAG]` can be used as `n/Google t/bigTech` or as `n/Google`.

- Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g., `[t/TAG]…​` can be used as ` ` (i.e., 0 times), `t/bigTech`, `t/bigTech t/BigCompany` etc.

- Parameters can be in any order.<br>
  e.g., if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

- Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit`, and `clear`) will be ignored.<br>
  e.g., if the command specifies `help 123`, it will be interpreted as `help`.

- If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding a company: `add`

Adds a company to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS cp/CAREER_PAGE_URL [r/REMARK] [t/TAG]…​`

**Tip:** A company can have any number of tags (including 0)

Examples:

- `add n/Google p/98765432 e/google@example.com a/John street, block 123, #01-01 cp/www.google-career-url.com`
- `add n/Meta t/bigTech e/meta@example.com a/Newgate Prison p/1234567 t/salary_high cp/www.meta-career-url.com r=Leading tech company`

---

#### Special Tag Values for the `add` Command

When using the `add` command, certain **tags** have special values.

**Note**: If no valid value is provided, the tag will be displayed as it is without special handling. For example, `t/randomtag` will appear as "randomtag".

Below are the supported categories:

##### 1. Salary, Work-Life Balance, and Interview Difficulty Tags

These tags represent qualitative levels. The valid values for these tags are:

- **LOW**
- **MEDIUM**
- **HIGH**

These tags must be written using the following format (note the underscore):

- Salary tag: `t/salary_[value]`
- Difficulty tag: `t/difficulty_[value]`
- Work-Life Balance tag: `t/wlb_[value]`

**Example:**
add n/Google p/98765432 e/google@example.com a/John street, block 123, #01-01 cp/www.google-career-url.com
t/salary_HIGH t/wlb_MEDIUM t/difficulty_MEDIUM
---

##### 2. **Period Tag**

The **`Period`** tag is used to specify:

- **Season**:

    - **Summer**
    - **Winter**
    - **Part-time**

- **Year**: Any year between **2000 and 2500**

Tag format: `t/period_[Season]_[Year]`

**Example:**
add n/Google p/98765432 e/google@example.com a/John street, block 123, #01-01 cp/www.google-career-url.com
t/period_summer_2025

### Listing all companies : `list`

Shows a list of all companies in the address book.

Format: `list`

### Bookmarking a company : `bookmark`

Bookmarks a company in the address book.

Format: `bookmark INDEX`

- Bookmarks the company at the specified `INDEX`.
- The index refers to the index number shown in the displayed company list.
- The index **must be a positive integer** 1, 2, 3, …​

### Removing a bookmark from a bookmarked company : `removebm`

Removes a company from the list of bookmarked companies in the address book.

Format: `removebm INDEX`

- Removes a bookmark from a bookmarked company at the specified `INDEX`.
- The index refers to the index number shown in the displayed company list.
- The index **must be a positive integer** 1, 2, 3, …​

### Listing all bookmarked companies : `bmlist`

Shows a list of all the bookmarked companies in the address book.

Format: `bmlist`

### Editing a company : `edit`

Edits an existing company in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [cp/CAREER_PAGE_URL] [t/TAG]…​`

- Edits the company at the specified `INDEX`. The index refers to the index number shown in the displayed company list. The index **must be a positive integer** 1, 2, 3, …​
- At least one of the optional fields must be provided.
- Existing values will be updated to the input values.
- When editing tags, the existing tags of the company will be removed; i.e., adding of tags is not cumulative.
- You can remove all the company’s tags by typing `t/` without specifying any tags after it.

Examples:

- `edit 1 p/91234567 e/grab@example.com` Edits the phone number and email address of the 1st company to be `91234567` and `grab@example.com` respectively.
- `edit 2 n/Grab t/` Edits the name of the 2nd company to be `Grab` and clears all existing tags.

### Editing a company's remark : `remark`

Adds or edits the remark of an existing company in the address book.

Format: `remark INDEX r/REMARK`

- Edits the remark of the company at the specified `INDEX`.
- The index refers to the index number shown in the displayed company list.
- The index **must be a positive integer** 1, 2, 3, …​
- Existing remark will be overwritten by the input.
- You can remove the company's remark by typing `remark INDEX r/` without specifying any text after `r/`.

Examples:

- `remark 2 r/Has good internship opportunities` Edits the remark of the 2nd company to `Has good internship opportunities`.
- `remark 3 r/` Removes the remark from the 3rd company.

### Locating companies by name: `find`

Finds companies whose names or tags contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

- The search is case-insensitive. e.g., `hans` will match `Hans`
- The order of the keywords does not matter. e.g., `Hans Bo` will match `Bo Hans`
- Only the name and tags are searched.
- Only full words will be matched e.g., `Han` will not match `Hans`
- Companies matching at least one keyword will be returned (i.e., `OR` search).
  e.g., `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:

- `find Salary:HIGH` returns companies with Salary tags and value HIGH
- `find John` returns `John` and `John Doe`
- `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a company : `delete`

Deletes the specified company from the address book.

Format: `delete INDEX`

- Deletes the company at the specified `INDEX`.
- The index refers to the index number shown in the displayed company list.
- The index **must be a positive integer** 1, 2, 3, …​

Examples:

- `list` followed by `delete 2` deletes the 2nd company in the address book.
- `find Betsy` followed by `delete 1` deletes the 1st company in the results of the `find` command.

### Editing an application status: `status`

Edits the application status of a company.

Format: `status INDEX as/STATUS`

- Modifies the company at the specified `INDEX`.
- The index refers to the index number shown in the displayed company list.
- The index **must be a positive integer** 1, 2, 3, …​
- The `STATUS` can be any text that describes the current application status.

Examples:

- `status 1 as/Applied` modifies the application status of the company at index 1 to `Applied`.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

**Caution:**
If your changes to the data file make its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

---

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app on the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

---

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

---

## Command summary

| Action                        | Format, Examples                                                                                                                                                                 |
| ----------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Add**                       | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS cp/CAREER_PAGE_URL [r/REMARK] [t/TAG]…​`<br>e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 cp/www.jamesho-career.com r/Looking forward to applying t/friend t/colleague` |
| **Bookmark**                  | `bookmark INDEX`<br>e.g., `bookmark 2`                                                                                                                                           |
| **Remove bookmark**           | `removebm INDEX`<br>e.g., `removebm 2`                                                                                                                                           |
| **Clear**                     | `clear`                                                                                                                                                                          |
| **Delete**                    | `delete INDEX`<br>e.g., `delete 3`                                                                                                                                               |
| **Edit**                      | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [cp/CAREER_PAGE_URL] [t/TAG]…​`<br>e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                  |
| **Remark**                    | `remark INDEX r/REMARK`<br>e.g., `remark 2 r/Has good internship opportunities`                                                                                                  |
| **Find**                      | `find KEYWORD [MORE_KEYWORDS]`<br>e.g., `find James Jake`                                                                                                                        |
| **List**                      | `list`                                                                                                                                                                           |
| **List bookmarked companies** | `bmlist`                                                                                                                                                                         |
| **Help**                      | `help`                                                                                                                                                                           |
