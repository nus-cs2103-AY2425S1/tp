---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# Sellsavvy User Guide

Sellsavvy is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, SellSavvy can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

2. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-F14a-2/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for SellSavvy.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `listcustomer`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

* Command aliases are alternative words you can use to execute the same command for convenience.<br>
  e.g. `listcustomer` has the same function as `listc`
</box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `addcustomer`

Adds a person to the address book.

Command aliases: `addc`
Format: `addcustomer n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`
Examples:
* `addcustomer n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `addc n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

<box type="important">

##### Constraints

* `NAME` can only contain alphanumeric characters, spaces, and one of the following symbols: hyphen, comma, and apostrophe.
* `NAME` should have alphanumeric characters before and after the symbol, and it should not be blank. Relationship indicator preceded "S/O" or "D/O" can be included, but must be followed with the name of person with stated relationship.
* Duplicated `NAME` is not allowed.
* If there is already a person with similar `NAME` (same name excluding space and casing), a warning will be given.
* `PHONE_NUMBER` should only contains numbers, and it should at least be 3 digits long.
* `EMAIL` should be of the format `local-part`@`domain` and adhere to the following constraints:
  * The `local-part` should only contain alphanumeric characters and these special characters, excluding the parentheses, (+_.-). 
  * The `local-part` may not start or end with any special characters.
  * This is followed by a '@' and then a `domain` name.
  * The `domain` name is made up of domain labels separated by periods.
  * The `domain` name must:
      - end with a domain label at least 2 characters long
      - have each domain label start and end with alphanumeric characters
      - have each domain label consist of alphanumeric characters, separated only by hyphens, if any
* `ADDRESS` can take any values, and it should not be blank.
* `TAG` should be alphanumeric.
* Duplicated `TAG` will be ignored.
* If similar `TAG` (same name excluding space and casing) are added, a warning will be given.

</box>

<box type="tip">

#### Tips

* A person can have any number of tags (including 0).

</box>

### Listing all persons : `listcustomer`

Shows a list of all persons in the address book.

Command aliases: `listc`
Format: `listcustomer`

### Editing a person : `editcustomer`

Edits an existing person in the address book.

Command aliases: `editc`
Format: `editcustomer INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `editcustomer 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `editc 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `findcustomer`

Finds persons whose names contain any of the given keywords.

Command aliases: `findc`
Format: `findcustomer KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `findcustomer John` returns `john` and `John Doe`
* `findc alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `deletecustomer`

Deletes the specified person from the address book.

Command aliases: `deletec`
Format: `deletecustomer INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `listcustomer` followed by `deletecustomer 2` deletes the 2nd person in the address book.
* `findcustomer Betsy` followed by `deletec 1` deletes the 1st person in the results of the `find` command.

### Adding an order under a Person : `addorder`

Adds an order under a specified person from the address book.

Command aliases: `addo`
Format: `addorder INDEX i/ITEM d/DATE [q/QUANTITY]`

* Add an order under the person at the specified `INDEX`, with a default `pending` status.
* The index refers to the index number shown in the displayed person list.
* The index and quantity **must be a positive integer** 1, 2, 3, …​
* Date must follow the following format: `DD-MM-YYYY`
* If the quantity is not provided, the quantity will be set to a default value of **1**.
* If there already exists a pending order with identical parameters under that person, a warning will be given.
* If the order `DATE` has elapsed the current date, a warning will be given.

Examples:
* `addorder 2 i/Lamp d/20-11-2024 q/3` adds the order with item `Lamp`, quantity of **3** and delivery date `20-11-2024`, to the 2nd person in the address book.
* `addo 1 i/Books d/02-03-2026` adds the order with item `Books`, quantity of **1** and delivery date `02-03-2026`, to the first person in the address book.
* `findcustomer Betsy` followed by `addorder 1 i/Bottles d/12-12-2002 q/1` adds an order under the 1st person in the results of the `find` command.

### Listing all orders under a Person : `listorder`

List all orders of a specified person from the address book.

Command aliases: `listo`
Format: `listorder INDEX`

* List all orders of the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `listcustomer` followed by `listorder 2` lists all orders of the 2nd person in the address book.
* `findcustomer Betsy` followed by `listo 1` lists all orders of the 1st person from the list of persons found with "Betsy".

### Deleting an order under a Person : `deleteorder`

Deletes an order from the selected person's displayed order list.

Command aliases: `deleteo`
Format: `deleteorder ORDER_INDEX`

* A person's order list must first be displayed before deleting an order from that person.
* Deletes an order under the selected person at the specified `ORDER_INDEX`.
* The order index refers to the index number shown in the displayed **order** list of the selected person.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `deleteorder 1` deletes the order with index 1 from the selected person.
* `listorder 1` followed by `deleteo 2` selects the 1st person in the address book and deletes the 2nd order under the 1st person.

### Editing an order : `editorder`

Edits an order from the selected person's displayed order list.

Command aliases: `edito`
Format: `editorder ORDER_INDEX [i/ITEM] [d/DATE] [q/QUANTITY]`

* A person's order list must first be displayed before editing an order from that person.
* Edits the order at the specified `ORDER_INDEX`. The order index refers to the index number shown in the displayed order list.
* The order index and quantity **must be a positive integer** 1, 2, 3, …​
* Date must follow the following format: `DD-MM-YYYY`
* At least one of the optional fields must be provided, and order status cannot be edited by this command.
* Existing values will be updated to the input values. 
* If there already exists an order with all parameters identical under that person, a warning will be given.
* If the order `DATE` has elapsed the current date, a warning will be given.

Examples:
*  `editorder 1 i/Light bulb d/21-11-2025` edits the item and delivery date of the 1st order to be `Light bulb` and `21-11-2025` respectively.
*  `edito 2 q/22` edits the quantity of the 2nd order to be `22`.
*  `listorder 1` followed by `editorder 3 i/Wallet` selects the 1st person in the address book and edits the item of the 3rd order under the 1st person to be `Wallet`.

### Mark an order as completed : `markorder`

Marks an order from the selected person's displayed order list as completed.

Command aliases: `marko`
Format: `markorder ORDER_INDEX`

* A person's order list must first be displayed before marking an order from that person.
* Marks a pending order under the selected person at the specified `ORDER_INDEX` as completed.
* The order index refers to the index number shown in the **displayed order list**.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `markorder 1` marks the order with index 1 from the displayed order list as completed.
* `listorder 1` followed by `marko 2` selects the 1st person in the address book and marks the 2nd order under the 1st person as completed.

### Reverts an order to pending status : `unmarkorder`

Reverts an order from the selected person's displayed order list to pending.

Command aliases: `unmarko`
Format: `unmarkorder ORDER_INDEX`

* A person's order list must first be displayed before unmarking an order from that person.
* Reverts a completed order under the selected person at the specified `ORDER_INDEX` to pending.
* The order index refers to the index number shown in the **displayed order list**.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `unmarkorder 1` reverts the order with index 1 from the displayed order list to pending.
* `listorder 1` followed by `unmarko 2` selects the 1st person in the address book and reverts the 2nd order under the 1st person to pending.

### Filter orders by order status : `filterorder`

Filters orders by the specified order status, under a selected person from their displayed order list.

Command aliases: `filtero`
Format: `filterorder ORDER_STATUS`

* A person's order list must first be displayed before filtering their order list.
* Filters the selected person's order list for orders that match the `ORDER_STATUS`.
* The order status **must be a valid status**. e.g. Completed, Pending.
* `ORDER_STATUS` is case-insensitive. e.g. `completed` is the same as `Completed`.

Examples:
* `filterorder Pending` filters the currently displayed order list for all orders with the `Pending` status, resulting in a list of pending orders under the selected person being displayed.
* `listorder 1` followed by `filtero completed` displays the list of all completed orders under the 1st person in the address book.
  ![result for filtering completed order'](images/filterCompletedOrders.png)

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

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

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

| Action                                | Command                     | Format and Examples                                                                                                                                                                 |
|---------------------------------------|-----------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add a person**                      | `addcustomer`, `addc`       | `addcustomer n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`<br> e.g. `addcustomer n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague` |
| **Clear**                             | `clear`                     |                                                                                                                                                                                     |
| **Delete a person**                   | `deletecustomer`, `deletec` | `deletecustomer INDEX`<br> e.g., `deletecustomer 3`                                                                                                                                 |
| **Edit a person**                     | `editcustomer`, `editc`     | `editcustomer INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g. `editcustomer 2 n/James Lee e/jameslee@example.com`                                         |
| **Find person(s)**                    | `findcustomer`, `findc`     | `findcustomer KEYWORD [MORE_KEYWORDS]`<br> e.g. `findcustomer James Jake`                                                                                                           |
| **List all persons**                  | `listcustomer`, `listc`     |                                                                                                                                                                                     |
| **Add an order**                      | `addorder`, `addo`          | `addorder INDEX i/ITEM d/DATE [q/QUANTITY]`<br> e.g. `addorder 2 i/Lamp d/20-11-2024 q/3`                                                                                           |
| **List all orders**                   | `listorder`, `listo`        | `listorder INDEX`<br> e.g. `listorder 3`                                                                                                                                            |
| **Delete an order**                   | `deleteorder`, `deleteo`    | `deleteorder ORDER_INDEX`<br> e.g. `deleteorder 2`                                                                                                                                  |
| **Edit an order**                     | `editorder`, `edito`        | `editorder ORDER_INDEX [i/ITEM] [d/DATE] [q/QUANTITY]` <br> e.g. `editorder 1 i/Light bulb d/21-11-2025`                                                                            |
| **Mark an order as completed**        | `markorder`, `marko`        | `markorder ORDER_INDEX`<br> e.g. `markorder 2`                                                                                                                                      |                                                                                                                                                                                                                                           
| **Revert an order to pending status** | `unmarkorder`, `unmarko`    | `unmarkorder ORDER_INDEX`<br> e.g., `unmarkorder 2`                                                                                                                                 |
| **Filter orders by status**           | `filterorder`, `filtero`    | `filterorder ORDER_STATUS`<br> e.g. `filterorder Completed`                                                                                                                         |
| **Help**                              | `help`                      |                                                                                                                                                                                     |
| **Exit**                              | `exit`                       |                                                                                                                                                                                     |
