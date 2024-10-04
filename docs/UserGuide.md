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

### Feature 1: Ability to Save Current Data

**Purpose:**  
This feature ensures that any details you add to the app are saved automatically. When you close and reopen the app, all your data will still be there.

**How it Works:**  
- **Command Format and Example:** Not applicable, as this process is automatic.

#### Parameters
- **Flags and Parameters:** There are no parameters needed for this feature.

#### What to Expect
- **If Successful:** You can access all the data you've entered previously.
- **If There is an Error:** There's a chance that the data might not be saved due to an error, and you could lose information.

---

### Feature 2: Add New Customer

**Purpose:**  
This feature allows you to enter and save detailed records for new customers. Each customer's record includes their name, contact number, email, occupation, and income.

**How to Use It:**  
- **Command Format:** 
```
add --name <customer name> --contact <customer contact> --email <customer email> --job <job name> --income <customer income>
 ```
- **Example:** 
```
add --name TAN LESHEW --contact 66997788 --email mrtan@ntu.com --job doctor of computer science --income 1000000000
```

#### Parameters
| Parameter | Expected Format                              | Explanation                                                |
|-----------|----------------------------------------------|------------------------------------------------------------|
| name      | Alphanumeric, capitalized                    | Names are in block letters for clarity and consistency.    |
| contact   | 8-digit number, starts with 8 or 9           | Ensures the contact number is valid in Singapore.          |
| email     | Must include "@" and domain, case insensitive| Verifies that the email address is in a standard format.   |
| job       | Any text, case insensitive                   | Accepts all job titles without case sensitivity.           |
| income    | Non-negative integers                        | Only positive numbers or zero are valid for income fields. |

#### What to Expect
- **If Successful:** You'll see a message: "New Contact added. Their ID is `<new customer ID>`."
- **If There is an Error:** 
  - Missing name: "Customer requires a name."
  - Incorrect email format: "Please give a valid email address."
  - Invalid income entry: "Please use a non-negative number for income."

**Handling Duplicates:**  
If a customer with the same name, email, job, and income is already saved, you'll get a message: "This customer is already saved as a contact. Their ID is `<new customer ID>`."

---

### Feature 3: Remove Old Customer

**Purpose:**  
This feature allows you to remove records of customers who are no longer using your credit card services.

**How to Use It:**  
- **Command Format:** 
```
del <customer id>
```
- **Example:** 
```
del 69
```

#### Parameters
| Parameter | Expected Format             | Explanation                                         |
|-----------|-----------------------------|-----------------------------------------------------|
| ID        | Integer (0 to the last ID)  | The ID must be a valid integer within the registered range. |

#### What to Expect
- **If Successful:** You'll see a message: "Customer `<Customer ID>` has been deleted."
- **If There is an Error:** 
  - Invalid ID: "No customer with `<Customer ID>` exists. Please recheck the ID."

**Handling Duplicates:**  
Since customer IDs are unique identifiers:
- No two customers can have the same ID due to the uniqueness constraint on customer IDs.
- Even if a customer record appears duplicated due to data file modifications, the system assigns a unique ID upon loading the data, preventing actual duplicates in the database.

---

### Feature 4: View Details of a Customer

**Purpose:**  
Allows users to view detailed information about a specific customer.

**How to Use It:**  
- **Command Format:** 
```
view <customer id>
```
- **Example:** 
```
view 69
```

#### Parameters
| Parameter | Expected Format             | Explanation                                         |
|-----------|-----------------------------|-----------------------------------------------------|
| ID        | Integer (0 to the last ID)  | The ID must be a valid integer within the registered range. This ensures you can view details for an existing customer. |

#### What to Expect
- **If Successful:** The details of the customer with the specified ID will be displayed.
- **If There is an Error:** 
  - Invalid ID: "No customer with `<Customer ID>` exists. Please recheck the ID."

**Handling Duplicates:**  
- There can be no duplicate customer IDs due to system constraints on ID uniqueness.
- While other information like name and address can be duplicated, each customer ID is unique, ensuring you always retrieve the correct customer record.

---
### Feature 5: Find a Customer by Details

**Purpose:**  
Enables users to search for and display all customers who match the specified details such as name, job, or ID.

**How to Use It:**  
- **Command Format:** 
```
filter --<flag> <flag field>
```
- **Examples:** 
```
filter --name TAN LESHEW
filter --job doctor of computer science
filter --id 69
```

#### Parameters
| Parameter | Expected Format       | Explanation                                                                 |
|-----------|-----------------------|-----------------------------------------------------------------------------|
| name      | Any string            | Input will be converted to block letters for consistent and accurate search.|
| job       | Any string            | Searches are case-insensitive, reflecting the non-sensitive nature of job titles.|
| id        | Integer number        | Must be an integer as IDs are unique identifiers for each customer.         |

#### What to Expect
- **If Successful:** 
  - Message: "Here are all the customers that match your search: (List of customers)."
- **If There is an Error:** 
  - Missing field: "Please provide a name/job/id." (Specific to the flag used)
  - Invalid or unsupported flag: "Please provide a valid flag."
  - No matching customers: "No customers were found. Please check the name/job/id." (Specific to the flag)

**Handling Duplicates:**  
- For `name` and `job` fields: Multiple customers can have the same names or jobs. The command will list all matching entries.
- For the `id` field: Since customer IDs are intended to be unique, finding multiple customers with the same ID will
  trigger a warning: 
  - "There seems to be multiple customers tagged to the same ID, use the delete command to remove all customers with the affected ID, and re-add customers accordingly."
---

### Feature 6: Save Remarks About Customers

**Purpose:**  
Allows users to save specific notes or remarks about a customer, which can be viewed later to recall notable details.

**How to Use It:**  
- **Command Format:** 
```
note <customer id> <note here>
```
- **Example:** 
```
note 55 He is a problematic customer.
```

#### Parameters
| Parameter | Expected Format       | Explanation                                                              |
|-----------|-----------------------|--------------------------------------------------------------------------|
| ID        | Integer (0 to the last ID) | Ensures the note is attached to a valid customer ID.                     |
| Note      | Any string            | Notes are case-insensitive and can include any textual information.      |

#### What to Expect
- **If Successful:** 
  - Message: "Note has been added to Customer `<Customer ID>`."
- **If There is an Error:** 
  - Invalid ID: "No customer with `<Customer ID>` exists. Please input a valid ID."

**Handling Duplicates:**  
- Although customer IDs should be unique, in the rare case where duplicates are detected, the following error message will be shown:
  - "Sorry, it appears that multiple customers with id: `<Customer ID>` exist. Please use the delete command to remove the duplicated customer ID."

---

### Feature 7: Add/Replace Credit Card Tier

**Purpose:**  
Allows users to assign or update the credit card tier for a customer. This is particularly useful for managing new, existing, or returning customers who may not have been assigned a credit card tier initially or who need their current tier updated.

**How to Use It:**  
- **Command Format:** 
```
tag <customer id> <tier>
```
- **Example:** 
```
tag 69 reject
```

#### Parameters
| Parameter | Expected Format       | Explanation                                                              |
|-----------|-----------------------|--------------------------------------------------------------------------|
| ID        | Integer (0 to the last ID) | Ensures the command targets a valid customer ID.                        |
| Tier      | String (gold, silver, bronze, reject) | Defines the specific credit card tier to be assigned or updated.        |

#### What to Expect
- **If Successful:** 
  - Message: "Customer `<Customer ID>` has been tagged with <tier> tier."
- **If There is an Error:** 
  - Invalid ID: "No customer with `<Customer ID>` exists. Please recheck the ID."
  - Invalid tier: "No tier named `<tier>` exists. Please recheck the tier name."

**Handling Duplicates:**  
- If a customer already has a tier assigned and a new `tag` command is issued, the existing tier will be updated to the new tier specified. This ensures that customers always have the most appropriate tier based on their current status or eligibility.

---
### Feature 8: Help

**Purpose:**  
This feature provides users with quick access to the user guide for the application, helping them understand how to use various features effectively.

**How to Use It:**  
- **Command Format:** 
```
help
```
- **Example:** 
```
help
```

#### Parameters
- **Flag:** N/A
  - There are no parameters required for this command.

#### What to Expect
- **If Successful:** 
  - No immediate message is displayed in the command interface.
  - Opens up a dialog box that provides a link to the user guide markdown file, allowing users to easily access detailed instructions and information.

**Handling Duplicates:**  
- N/A as this command does not involve processing or displaying data that could involve duplicates.

---
### Feature 9: Exit

**Purpose:**  
Allows users to exit the application through a simple command, eliminating the need to use the window's close button or external controls.

**How to Use It:**  
- **Command Format:** 
```
exit
```
- **Example:** 
```
exit
```

#### Parameters
- **Flag:** N/A
  - No parameters are needed to execute this command.

#### What to Expect
- **If Successful:** 
  - The message "Terminating program…" is displayed.
  - The program will then exit after a short delay, effectively closing the application.

**Handling Duplicates:**  
- N/A as this command is unique and does not process data that could involve duplicates.

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
