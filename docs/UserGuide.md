---
layout: page
title: User Guide
---

# AgentConnect User Guide

Welcome to the **AgentConnect User Guide**!

AgentConnect is a desktop application designed to help you manage your contacts efficiently. It's especially useful for insurance agents or anyone who needs to keep track of clients, appointments, and policies. AgentConnect combines the speed of typing commands with the ease of a visual interface, making it both powerful and user-friendly.

Whether you prefer clicking buttons or typing commands, AgentConnect will help you manage your contacts faster than traditional apps.

---

## Table of Contents

1. [Quick Start](#quick-start)
2. [Features](#features)
    - [Viewing Help](#viewing-help)
    - [Adding a Client](#adding-a-client)
    - [Assigning a Policy](#assigning-a-policy)
    - [Listing All Clients](#listing-all-clients)
    - [Editing a Client](#editing-a-client)
    - [Finding Clients by Name](#finding-clients-by-name)
    - [Search for Appointment](#searching-appointment-by-date)
    - [Search for Birthday](#searching-birthday-by-date)
    - [Sorting Client List](#sorting-client-data)
    - [Deleting a Client](#deleting-a-client)
    - [Clearing All Entries](#clearing-all-entries)
    - [Exiting the Program](#exiting-the-program)
    - [Saving Your Data](#saving-your-data)
3. [Frequently Asked Questions (FAQ)](#frequently-asked-questions-faq)
4. [Known Issues](#known-issues)
5. [Command Summary](#command-summary)

--------------------------------------------------------------------------------------------------------------------

## Quick start


1. **Ensure Java is Installed**
   Make sure you have **Java 17 or above** installed on your computer. If you're not sure, you can download it from [here](https://www.oracle.com/java/technologies/downloads/).

2. **Download AgentConnect**
   Get the latest version of AgentConnect from [this link](https://github.com/se-edu/addressbook-level3/releases). Look for a file named `AgentConnect.jar`.

3. **Place the File**
   Move the `AgentConnect.jar` file to a folder where you want to store the application and its data.

4. **Run the Application**
    - **Option 1: Double-Click**
      Simply double-click the `AgentConnect.jar` file to start the application.
    - **Option 2: Use Command Prompt**
      Open the Command Prompt (Windows) or Terminal (Mac/Linux), navigate to the folder containing the `AgentConnect.jar` file, and type:
      ```
      java -jar AgentConnect.jar
      ```
      Then press **Enter**.

6. **Try It Out**
    - **Enter a Command**
      Type a command into the command box at the bottom and press **Enter**. For example, type `help` to see the help message.
    - **Explore**
      Feel free to experiment with different commands.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------
## Features

AgentConnect offers a range of features to help you manage your clients effectively.

### Understanding Command Format

- **Words in UPPER_CASE** are placeholders for information you need to provide.
  Example: In `add n/NAME`, replace `NAME` with the client's name.

- **Square brackets [ ]** indicate optional items.
  Example: `n/NAME [t/TAG]` means the `t/TAG` part is optional.

- **Ellipsis ...** means you can include the item multiple times or not at all.
  Example: `[t/TAG]...` allows for multiple tags or none.

- **Order Doesn't Matter**
  You can enter the parameters in any order.
  Example: `n/John p/123` is the same as `p/123 n/John`.

- **Extra Parameters are Ignored**
  For commands that don't require additional input, any extra text will be ignored.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

---

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

---

### Adding a person: `add`

Adds a client to the address book.

Format: `add n/<name> p/<phone number> e/<email address> addr/<address> b/<birthday >appt/<appointment and time>`

- Birthday should be in `yyyy-mm-dd`
- Appointment should be in `yyyy-mm-dd HH:mm`

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 b/2000-12-12 appt/2024-12-12 12:00`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 b/2001-10-10 appt/2024-12-01 09:00`

---

### Adding a person: `assign`

Assign policies to a client

Format: `assign <Index> pon/<Policy Name> /pos<Policy start date> /poe <Policy end date> /paydate <Insurance due date> /amt <Amount Due>`

- Policy start date and end date refers to the duration of coverage
- Policy start date and Policy end date should be in `yyyy-mm-dd`
- Pay date should be in `yyyy-mm-dd` format
- Amount Due only can be Big Decimal format


Examples:
* `assign 1 pon/PolicyOne pos/2022-12-12 poe/2023-12-12 paydate/2023-11-01 amt/300.00`
* This command assigns the Policy object to the person listed at index 1 with the following information
  * Policy Name: PolicyOne
  * Policy start date: 2022-12-12
  * Policy end date: 2023-12-12
  * Premium due date: 2023-11-01
  * Amount Due: $300.00

  ![result for 'assign policy to first client'](images/assignpolicyUI.png)

---

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

---

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG] [b/BIRTHDAY] [appt/APPOINTMENT] [po/POLICY_INDEX pon/POLICY_NAME pos/START_DATE poe/END_DATE paydate/PAY_DATE amt/AMOUNT]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.
* When editing birthday and policy, the date format should be `yyyy-mm-dd`
* When editing appointment, the date and time format should be `yyyy-mm-dd hh:mm` in 24-hour notation
* When editing policy, all fields of policy must be included

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.
*  `edit 3 po/1 pon/Health Insurance pos/2024-10-19 poe/2025-10-19 paydate/2024-11-19 amt/200` Edits the 1st policy of
     the 3rd person to be `Health Insurance` which valid from `2024-10-19` to `2025-10-19` which pay date on `2024-11-19`
     and amount of `200`
   ![result for 'edit first index client'](images/editUI.png)

---

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

---

### Searching Appointments : `search a/`

Find clients with appointments on a specific date or within a date range.

- **How to Use**:

- **Parameters**:
- `DATETIME`: A specific date and time in `YYYY-MM-DD HH:mm` format.

- **What It Does**:
- **Single Datetime Search**: Lists all clients with appointments on the specified date and time.

- **Examples**:
- **Search by Specific Datetime**:
  ```
  search a/ 2024-12-12 23:00
  ```
  *Finds all clients with appointments on December 12, 2024, 11PM.*

  ![Search Appointment by Date](images/searchapptUI.png)

---

### Searching Birthdays : `search b/`

Find clients who have birthdays on a specific date or within a date range.

- **How to Use**:

- **Parameters**:
- `DATE`: A specific date in `YYYY-MM-DD` format.

- **What It Does**:
- **Single Date Search**: Lists all clients whose birthdays fall on the specified date.

- **Examples**:
- **Search by Specific Date**:
  ```
  search b/ 1990-01-01
  ```
  *Finds all clients with a birthday on January 1, 1990.*

  ![Search Birthday by Date](images/searchbirthdayUI.png)

---

### Sorting Clients : `sort`

Organize your client list based on different criteria for easier management and viewing.

- **How to Use**:

- **Parameters**:
- `CRITERIA`: The attribute by which you want to sort the clients. Available criteria include:
    - `name`: Sort by client's name (A-Z).
    - `birthday`: Sort by client's birthday (earliest to latest).
    - `appointment`: Sort by upcoming appointment dates (soonest to latest).

- **What It Does**:
- **Name Sorting**: Orders the client list alphabetically by name.
- **Birthday Sorting**: Orders the client list by birthday dates.
- **Appointment Sorting**: Orders the client list by the dates of upcoming appointments.
- **Policy Sorting**: Orders the client list based on policy details.

- **Examples**:
- **Sort by Name**:
  ```
  sort name asc/dsc
  ```
  *Sorts the client list alphabetically by each client's name.*

  ![Sort by Name](images/sortnameUI.png)

- **Sort by Birthday**:
  ```
  sort birthday asc/dsc
  ```
  *Sorts the client list from the earliest to the latest birthday.*

  ![Sort by Birthday](images/sortUI.png)

- **Sort by Appointment**:
  ```
  sort appointment_date asc/dsc
  ```
  *Sorts the client list based on the soonest upcoming appointments.*

  ![Sort by Appointment](images/sortapptUI.png)

---

### Deleting a person or policy: `delete`
#### Person:
Deletes the specified client from the application. There is a confirmation message before the deletion is executed. Type `y` to confirm deletion.

Format: `delete INDEX` or `delete NAME`

* Deletes the client at the specified `INDEX` or the specific `NAME`.
* The index and name refers to the index number or name shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* The name of the person is case-sensitive and must be an exact match.

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.
* `delete Betsy Crowe` deletes the person named `Betsy Crowe` from the list of the address book.
  ![result for 'delete david li'](images/deleteUI.png)

#### Policy:

Deletes the specified policy from the specified person using INDEX of the person and policy.

Format: `delete INDEX po/POLICY_INDEX`

* Deletes the policy at the specified `POLICY_INDEX` of the person at the specified `INDEX`.

Examples:
* `delete 1 po/1` deletes the 1st policy of the 1st person in the address book.

---
### Undo a delete command: `undo`
Undo the last delete command. Works for both person and policy deletion.

Format: `undo` after a successful delete command.

* Undo the last delete person or policy command to restore back the deletion
* Only the last delete command can be undone.

Examples:
* `delete 1` followed by `undo` will restore back the deleted person at index 1.
* `delete 1 po/1` followed by `undo` will restore back the deleted policy at index 1 of the person at index 1.
* `delete John Doe` followed by undo will restore back John Doe.

---

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

---

### Exiting the program : `exit`

Exits the program.

Format: `exit`

---

### Saving the data

AgentConnect data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

---

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

---

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## Frequently Asked Questions (FAQ)

**Q:** How do I check if I have Java installed?

**A:** Open the Command Prompt or Terminal and type `java -version`. If Java is installed, it will show the version number.

---

**Q:** Can I run AgentConnect on a Mac or Linux computer?

**A:** Yes! As long as you have Java 17 or above installed, AgentConnect will work on Windows, Mac, and Linux.

---

**Q:** How do I back up my data?

**A:** Your data is stored in the `addressbook.json` file in the `data` folder. Copy this file to a safe location to back up your data.

---

**Q:** What happens if I enter the wrong command?

**A:** AgentConnect will show an error message explaining what went wrong. You can then try the command again.

---

**Q:** Can I assign multiple policies to one client?

**A:** Yes, you can assign multiple policies by using the `assign` command multiple times for the same client.



--------------------------------------------------------------------------------------------------------------------

## Known Issues

1. **Multiple Screens**: If you use multiple monitors and move AgentConnect to a secondary screen, it may open off-screen if that monitor is disconnected.
   **Solution**: Delete the `preferences.json` file in the application folder before restarting AgentConnect.

2. **Help Window Minimization**: If you minimize the Help window and try to open it again, it may stay minimized.  
   **Solution**: Restore the Help window manually.

--------------------------------------------------------------------------------------------------------------------

## Command Summary

| Action                 | Command Format                                                                                                                                                                                                                 |
|------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add a Client**       | `add n/NAME p/PHONE_NUMBER e/EMAIL addr/ADDRESS b/BIRTHDAY appt/APPOINTMENT`<br>Example: `add n/John Doe p/91234567 e/john@example.com addr/123 Street b/1990-01-01 appt/2024-12-12 14:00`                                     |
| **Assign a Policy**    | `assign INDEX pon/POLICY_NAME pos/START_DATE poe/END_DATE paydate/PAYMENT_DUE_DATE amt/AMOUNT_DUE`<br>Example: `assign 1 pon/Health Insurance pos/2022-01-01 poe/2023-01-01 paydate/2022-12-01 amt/300.00`                     |
| **List Clients**       | `list`                                                                                                                                                                                                                         |
| **Edit a Client**      | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [addr/ADDRESS] [b/BIRTHDAY] [appt/APPOINTMENT] [po/POLICY_INDEX pon/POLICY_NAME pos/START_DATE poe/END_DATE paydate/PAYMENT_DUE_DATE amt/AMOUNT_DUE]`<br>Example: `edit 2 p/98765432` |
| **Find Clients**       | `find KEYWORD [MORE_KEYWORDS]`<br>Example: `find Alice`                                                                                                                                                                        |
| **Search Appointment** | `search a/ [DATETIME]`<br>Example: `search a/ 2024-10-10 23:00`                                                                                                                                                                |
| **Search Birthday**    | `search b/ [DATE]`<br>Example: `search b/ 2000-03-15`                                                                                                                                                                          |
| **Sort Client Data**   | `sort KEYWORD [ORDER]`<br>Example: `sort birthday asc`                                                                                                                                                                         |
| **Delete a Client**    | `delete INDEX` or `delete NAME`<br>Example: `delete 3` or `delete John Doe`                                                                                                                                                    |
| **Clear All Entries**  | `clear`                                                                                                                                                                                                                        |
| **Exit Application**   | `exit`                                                                                                                                                                                                                         |
| **View Help**          | `help`                                                                                                                                                                                                                         |
