---
layout: page
title: User Guide
---

AddressBook Level 3 (AB3) is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features Overview

### Ability to Save Current Data

**Purpose:** When you close and open the app, the details you have added persist.

**Details:** Data is automatically saved without the need for manual intervention.

### Add New Customer

**Purpose:** To save the records and details of a new customer.

**Command:**
```
add --name <customer name> --contact <customer contact> --email <customer email> --job <job name> --income <customer income>
```

**Details:**
- **Name:** Should be in all caps; alphanumeric characters only.
- **Contact:** Must be an integer.
- **Email:** Must contain an "@" and a domain.
- **Occupation:** Describes the customer's job.
- **Income:** The customer's income level.

**Examples:**
- `add --name JOHN DOE --contact 1234567890 --email john.doe@example.com --job Engineer --income 50000`

### Remove Old Customer

**Purpose:** Remove a customer who no longer uses the credit card service.

**Command:**
```
del <customer id>
```

**Details:** 
- **ID:** Integer specifying the customer to delete.
  
**Example:** `del 69`

### View Details of a Customer

**Purpose:** See detailed information related to the customer.

**Command:**
```
view <customer id>
```

**Details:** 
- **ID:** Integer of the customer to view.

**Example:** `view 69`

### Find Customers by Details

**Purpose:** Find customers by their name or user ID, especially useful for returning customers.

**Command:**
```
filter --name <customer name>
filter --id <customer id>
filter --job <job name>
```

**Details:**
- **Name:** Case-sensitive, alphanumeric.
- **ID:** Must be an integer.
- **Job:** Case-insensitive, alphanumeric.

**Examples:**
- `filter --name John Doe`
- `filter --id 69`
- `filter --job doctor`

### Save Notes/Remarks About Customers

**Purpose:** To recall any particular notable details about the customer.

**Command:**
```
note <customer id> <note>
```

**Details:** Just a string input.

**Example:** `note 55 Mr. Tan is a problematic customer, complaining about scams for the 404th time. Just ignore him.`

### Add/Replace the Decision of Which Tier of Credit Card to Assign

**Purpose:** Assign a credit card tier to a new or returning customer.

**Command:**
```
tag <customer id> <tier>
```

**Details:**
- **Tier:** One of 'gold', 'silver', 'bronze', 'reject' (case-insensitive).

**Example:** `tag 69 gold`

### View the Help Menu

**Purpose:** Allow users to view a guide and list of commands.

**Command:**
```
help
```

### Exit the Program

**Purpose:** To close the program gracefully.

**Command:**
```
exit
```

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

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`
