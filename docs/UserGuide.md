---
layout: page
title: User Guide
---

Welcome to the NomNomNotifier User Guide, your essential toolkit for mastering customer management in a fast-paced restaurant environment. This guide is crafted for all members of the restaurant team—from front-of-house staff to receptionists—equipping you with everything you need to manage customer information effortlessly. You don’t need any prior technical expertise; this guide is designed with simplicity and ease in mind, making it accessible even for those completely new to digital customer management tools.

NomNomNotifier brings powerful features right to your desktop, enabling quick and accurate access to customer data like names, addresses, contact details, and dietary preferences. Our app enhances the speed of customer service while helping teams manage VIPs and special requests with ease. Combining a Command Line Interface (CLI) with a user-friendly Graphical Interface (GUI), NomNomNotifier offers the flexibility to work however you prefer—type commands to streamline actions or navigate visually to find exactly what you need.


* Table of Contents
    * [Quick start](#quick-start)
    * [Command summary](#command-summary)
    * Features
        * [Add Customer: `add`](#adding-a-customer-add)
        * [List Customers: `list`](#listing-all-customers--list)
        * [Edit Customer: `edit`](#editing-a-customer--edit)
        * [Find Customers: `find`](#locating-customers-by-name-find)
        * [Delete Customer: `delete`](#deleting-a-customer--delete)
        * [Delete Customers by Postal Code: `deletePC`](#deleting-customers-by-postal-code-deletepc)
        * [Clear all Customers: `clear`](#clearing-all-entries--clear)
        * [Add Order: `order`](#adding-an-order-order)
        * [Delete Order: `deleteOrder`](#deleting-an-order-deleteorder)
        * [List Orders: `listOrder`](#listing-all-orders-listorder)
        * [Assign Order to Customer: `put`](#adding-an-order-history-to-a-customer-put)
        * [View Order History for Customer: `history`](#listing-all-order-histories-of-a-customer-history)
        * [Create Shortcut for Tags: `addShortCut`](#creating-shortcuts-for-tags-addshortcut)
        * [Delete Shortcut for Tags: `delShortCut`](#deleting-shortcuts-for-tags-delshortcut)
        * [List All Shortcuts: `listShortCut`](#listing-existing-shortcuts-listshortcut)
        * [Using Shortcuts for Tagging](#using-shortcuts-for-tagging)
        * [Filter Customers by Tags: `filter`](#filtering-by-tags-filter)
        * [Archive Customer: `archive`](#archive-customer-archive)
        * [View Archived Customers: `listarchive`](#list-archived-customer-listarchive)
        * [Unarchive Customer: `unarchive`](#unarchive-customer-unarchive)
        * [Download Customer Data: `download`](#downloading-customer-data-download)
        * [Exit Command: `exit`](#exiting-the-program--exit)
    * [FAQ](#faq)
    * [Known Issues](#known-issues)
  
---

## Quick start

1. Ensure you have Java `17` or above installed on your computer.
    - **To check:** Open a command terminal (Terminal for macOS, Command Prompt for Windows), and type `java -version` to check if Java is installed and which version is installed.
    - **To install:** Visit Oracle’s Java 17 download page. Download the version for your operating system (Windows, macOS, or Linux). Follow the instructions provided by Oracle to install Java 17.

2. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-T13-2/tp/releases/latest).

3. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

4. Open a command terminal, `cd` into the folder where you placed the jar file, and use the `java -jar NomNomNotifier.jar` command to run the application.<br>
   A GUI similar to the one below should appear in a few seconds, containing some sample data.


   <div style="text-align: center;">
    <img src="images/QuickStart.png" alt="Ui" width="350"/>
    <br>
    <em>Figure 1: Shows what the application should look like</em>
    </div>


5. Type a command in the command box and press Enter to execute it. For example, typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:
    * `list` : Lists all contacts.
    * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 pc/567333` : Adds a contact named `John Doe` to the contact list.
    * `delete 3` : Deletes the 3rd contact shown in the current list.
    * `clear` : Deletes all contacts.
    * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details on each command.

---

## Command summary

| Action                       | Format, Examples                                                                                                                                                                         |
|------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**                      | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS pc/POSTAL_CODE [t/TAG]…​`<br> e.g., `add n/James Ho p/88737204 e/jamesho@example.com a/Blk 310, Clementi Rd pc/123456 t/friend t/colleague` |
| **Add Order to Application** | `order ORDER`<br> e.g., `order pizza`                                                                                                                                                    |
| **Add Order to Customer**    | `put ORDER n/NAME`<br> e.g., `put cake n/Alex`                                                                                                                                           |
| **Add Shortcut**             | `addShortCut al/ALIAS tn/TAG_NAME`<br> e.g., `addShortCut al/v tn/Vegan`                                                                                                                 |
| **Archive**                  | `archive INDEX`<br> e.g., `archive 3`                                                                                                                                                    |
| **Clear**                    | `clear`                                                                                                                                                                                  |
| **Delete**                   | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                                      |
| **Delete Order**             | `deleteOrder ORDER`<br> e.g., `deleteOrder pizza`                                                                                                                                        |
| **Delete by postal code**    | `deletePC POSTAL_CODE`<br> e.g., `deletePC 118303`                                                                                                                                       |
| **Download**                 | `download [t/TAG1] [t/TAG2] …​`<br> e.g., `download t/vegan`                                                                                                                             |
| **Edit**                     | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [pc/POSTAL_CODE] [t/TAG]…`<br> e.g., `edit 2 n/James Lee e/jameslee@example.com`                                             |
| **Exit**                     | `exit`                                                                                                                                                                                   |
| **Find**                     | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James 81234567 S123456`                                                                                                                   |
| **Help**                     | `help`                                                                                                                                                                                   |
| **History of a Customer**    | `history NAME`<br> e.g., `history Alex`                                                                                                                                                  |
| **Delete Shortcut**          | `delShortCut al/ALIAS tn/TAG_NAME`<br> e.g., `delShortCut al/v tn/Vegan`                                                                                                                 |
| **List**                     | `list`                                                                                                                                                                                   |
| **List Order**               | `listOrder`                                                                                                                                                                              |
| **List Shortcuts**           | `listShortCut`                                                                                                                                                                           |
| **List Archived**            | `listarchive`                                                                                                                                                                            |
| **Filter**                   | `filter`<br> e.g., `filter Vegan Vegetarian`                                                                                                                                             |
| **Unarchive**                | `unarchive INDEX`<br> e.g., `unarchive 2`                                                                                                                                                |

> **Disclaimer**: NomNomNotifier only accepts inputs in english without any emojis, any inputs in other languages could cause unexpected behaviour

---

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are parameters to be supplied by the user.<br>
  e.g., in `add n/NAME`, `NAME` is a parameter that can be used as `add n/John Doe`.
* Items in square brackets are optional.<br>
  e.g., `n/NAME [t/TAG]` can be used as `n/John Doe t/Vegan` or simply as `n/John Doe`.
* Items with `…` after them can be used multiple times, including zero times.<br>
  e.g., `[t/TAG]…` can be used as ` ` (i.e., 0 times), `t/Vegan`, or `t/Vegetarian t/VIP`.
* Parameters can be in any order.<br>
  e.g., if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.
* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit`, and `clear`) will be ignored.<br>
  e.g., if the command specifies `help 123`, it will be interpreted as `help`.
* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines, as spaces surrounding line breaks may be omitted when copied over to the application.

</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

<div style="text-align: center; margin-top:20px">
    <img src="images/helpMessage.png" alt="adding customer" width="500"/>
    <br>
    <em>Figure 1: Shows help message displayed</em>
</div>

**Format:** `help`

---

### Adding a customer: `add`

Adds a customer to the contact list.

**Format:** `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS pc/POSTAL_CODE [t/TAG]…`

- Phone number (with `p/` prefix) should be an 8-digit mainstream Singaporean phone number starting with 9, 8, 7, or 6.
- Postal code (with `pc/` prefix) should be a 6-digit number.

> Note: Leading and trailing spaces in `NAME` inputs will be automatically removed.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:** Any further specification in ability to add or edit fields is described in the respective field's error message</div>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:** A person can have any number of tags (including 0)</div>

**Examples:**
- `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 pc/666234`
- `add n/Betsy Crowe t/Vegetarian e/betsycrowe@example.com a/Clementi Ave 5 #02-03 p/72345673 t/VIP pc/123123`

<div style="text-align: center;">
    <img src="images/add_customer.png" alt="adding customer" width="350"/>
    <br>
    <em>Figure 2: Shows customer named Sarah being added</em>
</div>

---

### Listing all customers : `list`

Shows a list of all customers in the contact list.

**Format:** `list`

<div style="text-align: center;">
    <img src="images/list.png" alt="list customers" width="350"/>
    <br>
    <em>Figure 3: Shows all customers listed</em>
</div>

---

### Editing a customer : `edit`

Edits an existing customer in the contact list.

**Format:** `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [pc/POSTAL_CODE] [t/TAG]…`

- Edits the person at the specified `INDEX`. The index refers to the number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …
- At least one of the optional fields must be provided.
- Each field still needs to follow the restrictions as specified in [Add Command](#adding-a-customer-add).
- Existing values will be updated to the new input values.
- When editing tags, all existing tags for the person will be removed; i.e., adding tags is not cumulative.
- You can remove all the person’s tags by typing `t/` without specifying any tags after it.

**Examples:**
- `edit 1 p/91234567 e/johndoe@example.com` — Edits the phone number and email address of the 1st person to `91234567` and `johndoe@example.com`, respectively.
- `edit 2 n/Betsy Crower t/` — Edits the name of the 2nd person to `Betsy Crower` and clears all existing tags.

<div style="text-align: center;">
    <img src="images/edit_customer.png" alt="edit customers" width="350"/>
    <br>
    <em>Figure 4: Shows customer details being edited</em>
</div>

---

### Locating customers by name: `find`

The `find` command allows users to search for customers by name, phone number, or postal code, with flexible prefix search and multi-criteria functionality.

**Format:** `find KEYWORD [MORE_KEYWORDS]`

- The search is case-insensitive.
- Users can search by **name**, **phone number**, and **postal code** simultaneously.
- Partial matches are allowed for names, phone numbers, and postal codes.
- Each keyword can represent a part of a name, phone number, or postal code, allowing flexible search criteria in any order.


#### Prefix Search by Name

Users can locate customers by entering the beginning letters (prefix) of their names. Multiple people can be located in a single search command by specifying additional prefixes.

**Examples:**
- `find al` — Finds all customers with names starting with "al".
- `find al ch` — Finds all customers with names starting with "al" or "ch".

#### Search by Phone Number

Users can search for customers by entering part or all of their phone number.

**Examples:**
- `find 9123` — Finds any customers with phone numbers containing "9123".
- `find 98124572` — Finds any customers with the exact phone number "98124572".

#### Search by Postal Code

Users can search for customers by entering part or all of their postal code. Postal code inputs for find command should follow the format prefixed by "S" followed by a number.

**Examples:**
- `find S560123` — Finds any customers with the postal code "560123".
- `find S560` — Finds any customers with postal codes containing "560".


#### Simultaneous Search with Multiple Criteria

Users can combine multiple criteria—name, phone number, and postal code—in a single search command for flexible and efficient searching.

**Examples:**
- `find Alice 9876` — Finds all customers with the name "Alice" **or** a phone number containing "9876".
- `find 9456 S630123` — Finds all customers with phone numbers containing "9456" **or** postal code "630123".
- `find S550 Bob` — Finds all customers with postal codes containing "550" **or** the name "Bob".
- `find S789123 Carl 97621010` — Finds all customers with postal code "789123" **or** name "Carl" **or** phone number "97621010".

<div style="text-align: center; margin-bottom:20px">
    <img src="images/find_name_and_number_postal.png" alt="find customers" width="350"/>
    <br>
    <em>Figure 5: Shows multiple people found using prefix search of name, number and postal code </em>
</div>

**Note:** The `find` command performs an `OR` search across the criteria, meaning that customers matching any of the provided keywords will be returned.

---

### Deleting a customer : `delete`

Deletes the specified customer from the contact list.

**Format:** `delete INDEX`

- Deletes the person at the specified `INDEX`.
- The index refers to the number shown in the displayed person list.
- The index **must be a positive integer** 1, 2, 3, …

**Examples:**
- `list` followed by `delete 2` deletes the 2nd person in the contact list.
- `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

<div style="text-align: center;">
    <img src="images/delete_customer.png" alt="delete customers" width="350"/>
    <br>
    <em>Figure 6: Shows customer being deleted using the delete command</em>
</div>

---

### Deleting customers by postal code: `deletePC`

* Delete all persons from the contact list with the given postal code.

Format: `deletePC POSTAL_CODE`

* Delete all persons with the specified `POSTAL_CODE`.
* The postal code **must be 6 digits** 

Examples:
* `list` followed by `deletePC 560102` deletes all persons with postal code `560102` in the contact list.

<div style="text-align: center;">
    <img src="images/delete_by_pc.png" alt="deletePC customers" width="350"/>
    <br>
    <em>Figure 7: Customer being deleted using deletePC command</em>
</div>

---

### Clearing all entries : `clear`

Clears all entries from the contact list.

**Format:** `clear`

---

### Adding an order: `order`

Adds an order to NomNomNotifier.

**Format:** `order ORDER`

- `ORDER` must be in lowercase.
- If `ORDER` contains uppercase characters, they will be converted to lowercase automatically.
- `ORDER` can only contain alphanumeric characters and whitespace.
  
> Note: Leading and trailing spaces in `ORDER` inputs will be automatically removed.

**Examples:**
- `order cake` — Adds an order called "cake" to NomNomNotifier.

<div style="text-align: center;">
    <img src="images/order.png" alt="order" width="350"/>
    <br>
    <em>Figure 8: Shows new order being added</em>
</div>

---

### Deleting an order: `deleteOrder`

Deletes an order from NomNomNotifier.

**Format:** `deleteOrder ORDER`

- `ORDER` must be in lowercase.
- If `ORDER` contains uppercase characters, they will be converted to lowercase automatically.
- `ORDER` can only contain alphanumeric characters and whitespace.
  
> Note: Leading and trailing spaces in `ORDER` inputs will be automatically removed.

**Examples:**
- `deleteOrder cake` — Deletes an order called "cake" from NomNomNotifier.

<div style="text-align: center;">
    <img src="images/deleteOrder.png" alt="delete order" width="350"/>
    <br>
    <em>Figure 9: Shows order being deleted</em>
</div>

---

### Listing all orders: `listOrder`

Shows a list of all orders in NomNomNotifier.

**Format:** `listOrder`

<div style="text-align: center;">
    <img src="images/listOrder.png" alt="list order" width="350"/>
    <br>
    <em>Figure 10: Shows all orders being listed</em>
</div>

---

### Adding an order history to a customer: `put`

Adds an order history to a customer.

**Format:** `put ORDER n/NAME`

- `ORDER` must be in lowercase.
- If `ORDER` contains uppercase characters, they will be converted to lowercase automatically.
- `ORDER` can only contain alphanumeric characters and whitespace.
- The time recorded by the order history is the time the `put` command is run.

> Note: Leading and trailing spaces in `ORDER` inputs will be automatically removed.

**Examples:**
- `put pizza n/Alex Yeoh` — Adds an order called "pizza" to a customer named "Alex Yeoh".

<div style="text-align: center;">
    <img src="images/put_order.png" alt="put order" width="350"/>
    <br>
    <em>Figure 11: Shows order being associated with customer</em>
</div>

---

### Listing all order histories of a customer: `history`

Lists all order histories of a customer, including the time of each order.

**Format:** `history NAME`

> Note: Leading and trailing spaces in `NAME` inputs will be automatically removed.

**Examples:**
- `history Alex Yeoh` — Lists order histories of a customer named `Alex Yeoh`.

<div style="text-align: center;">
    <img src="images/history.png" alt="history" width="350"/>
    <br>
    <em>Figure 12: Shows order history of John Doe</em>
</div>

---

### Creating Shortcuts for Tags: `addShortCut`

Tag shortcuts allow you to create aliases for commonly used tags, saving you time when tagging contacts.

**Format:** `addShortCut al/ALIAS tn/TAG_NAME`

- Aliases and Tag Names must be unique. You cannot create two shortcuts with the same alias or tag name for different tags.
- Aliases and Tag Names are not case-sensitive when adding shortcuts.
- Attempting to add an alias or tag name that already exists will display an error.

**Examples:**
- `addShortCut al/v tn/Vegan` — Adds a shortcut with the alias "v" for the tag name "Vegan".
- Following the previous example:
    - `addShortCut al/vn tn/VeGan` — Shows an error, as "Vegan" is already used.
    - `addShortCut al/v tn/Vegetarian` — Shows an error, as "v" is already an alias.

<div style="text-align: center;">
    <img src="images/add_shortcut.png" alt="adding shortcuts" width="350"/>
    <br>
    <em>Figure 13: Shows shortcut being added</em>
</div>

---

### Deleting Shortcuts for Tags: `delShortCut`

You can delete an existing shortcut by specifying its alias and tag name.

**Format:** `delShortCut al/ALIAS tn/TAG_NAME`

**Example:**
- `delShortCut al/v tn/Vegan` — Deletes the shortcut for alias "v" and tag name "Vegan".

<div style="text-align: center;">
    <img src="images/del_shortcut.png" alt="deleting shortcuts" width="350"/>
    <br>
    <em>Figure 14: Shows shortcut being deleted</em>
</div>

---

### Listing Existing Shortcuts: `listShortCut`

View all current shortcuts to see the mappings of aliases to tag names.

**Format:** `listShortCut`

<div style="text-align: center;">
    <img src="images/list_shortcut.png" alt="listing shortcuts" width="350"/>
    <br>
    <em>Figure 15: Shows shortcuts being listed</em>
</div>

---

#### Using Shortcuts for Tagging: 
After setting shortcuts, you can tag contacts using these aliases.

Usage: 
- **In Edit Command**: `edit INDEX t/ALIAS` — Edits the tag for the contact at the specified index with the tag name associated with specified alias.
- **In Add Command**: `add ... t/ALIAS` — Adds a new contact with the tag name associated with specified alias.

Examples: 
- Assuming "v" (Vegan) and "vg" (Vegetarian) shortcuts have been set:
    - `edit 1 t/vg` — Tags the contact at index 1 with "Vegetarian".
    - `edit 1 t/vg t/v` — Tags the contact at index 1 with "Vegetarian" and "Vegan".
    - `add n/John Doe p/98765432 e/johnd@example.com a/311 Clementi Ave 2, #02-25 pc/123456 t/v` — Creates a contact tagged as "Vegan".

<div style="text-align: center; margin-bottom:20px">
    <img src="images/using_shortcut.png" alt="using shortcuts" width="350"/>
    <br>
    <em>Figure 16: Shows shortcut being used to edit tags</em>
</div>

> **Note**: While adding shortcuts, aliases and tag names are case-insensitive. However, when using tags (`t/`) in commands, they are case-sensitive.
> 
> This means that when the shortcut, "v" (alias) is set to "Vegan" (tag name), the shortcut, "V" (alias) cannot be set "VEGAN". 
>
> However, when using the command `edit 1 t/Vegan t/VEGAN`, where the tags of contact at index 1 is being replaced with "Vegan" and "VEGAN", those two tags are considered different

Example:
- Assuming the shortcut "v" maps to "Vegan":
    - `add ... t/v` — Tags Person with "Vegan".
    - `add ... t/V` — Tags Person with "V".

---

### Filtering by Tags: `filter`
You can filter the customer list by tags or shortcuts to view only the relevant contacts. The filter command supports prefix searches, is case-insensitive, and allows multiple keywords.

Format: `filter [TAG1] [TAG2] ...`
* Filters the list by the specified tags.

Examples:
* `filter Vegan Vegetarian` — Shows all customers with tags containing keywords listed, "Vegan" or "Vegetarian" (e.g., "VeganPlus").

<div style="text-align: center;">
    <img src="images/filter.png" alt="filter" width="350"/>
    <br>
    <em>Figure 17: Shows filter based on tag: Vegan</em>
</div>

---
### Archive customer: `archive`

Archive a customer so that it doesn't show when we run `list`

**Format:**
`archive INDEX`

**Warning**
You must run `list` before running `archive`

**Details**
- `INDEX` must be positive integer

Assuming there's at least one person that is unarchived.

1. `list`
2. `archive 1`

---
### List archived customer: `listarchive`

Shows all archived customers only in contact list

**Format:**
`listarchive`

**How to run command**:

Assuming there's at least one person that is archived.

1. `listarchive`

---
### Unarchive customer: `unarchive`

Unarchive a customer so that it shows when we run `list`

**Format:**
`unarchive INDEX`

**Warning**
You must run `listarchive` before running `unarchive`

**Detail**
- `INDEX` must be positive integer 

Assuming there's at least one person that is archived.

1. `listarchive`
2. `unarchive 1`

---

### Downloading Customer Data: `download`
Exports the currently displayed contact list data as a CSV file with optional tag-based filtering.


**Format:**  
`download [t/TAG1] [t/TAG2] ...`

**Details:**
- The command will export only the currently displayed data in the contact list (based on active filters or views) and not the absolute data stored in the contact list.
- Creates a CSV file containing the people that match the specified tags.
- The exported file will be saved in the `./data` subdirectory.
- Tags can be specified using the `t/` prefix to filter the download results, allowing for a more customized data export.
- If no tags are specified, all currently displayed data will be exported.
- If the filtered result is empty (no matching entries), an error will be returned, and no file will be generated.

**Examples:**
- `download` — Exports all contacts that exists in displayed list.
- `download t/Vegan` — Exports only the contacts tagged as "Vegan" within the displayed list.
- `download t/Vegan t/Vegetarian` — Exports contacts tagged as "Vegan" and "Vegetarian" within the displayed list.


<div style="text-align: center;">
    <img src="images/dowload.png" alt="download" width="350"/>
    <br>
    <em>Figure 18: Shows entire contact list being downloaded</em>
</div>

<div style="text-align: center; margin-top: 20px">
    <img src="images/download_shortcuts.png" alt="download shortcuts" width="350"/>
    <br>
    <em>Figure 19: Shows only people with vegan tag being downloaded</em>
</div>

---

### Exiting the program : `exit`

Exits the program.

Format: `exit`


### Saving the data

NomNomNotifier data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

NomNomNotifier data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, NomNomNotifier will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the NoNomNotifier application to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the `addressbook.json` under `data` directory it creates with the `addressbook.json` file from the original computer.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

