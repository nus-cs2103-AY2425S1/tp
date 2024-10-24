---
## Quick start
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# InvenTrack User Guide

InvenTrack is a simple desktop app designed to help you manage the products and suppliers for your convenience store. The app is fast and easy to use, especially if you’re comfortable typing.

With InvenTrack, you can:

- Add products and suppliers to your inventory system.
- Easily keep track of which suppliers provide which products.
- Monitor stock levels and get alerts when a product is running low.

InvenTrack simulates an ongoing software project for a desktop application dedicated to managing suppliers, product, and stock details within a convenience store setting. It is developed using object-oriented programming (OOP) principles, providing a robust and maintainable code base. The application also includes comprehensive user and developer documentation for ease of use and understanding.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Getting Started
### Step 1: Install Java
Before you can use InvenTrack, ensure you have Java `17` or above installed in your Computer.
- To check if Java is installed:
  1. Open a command terminal (Command Prompt on Windows, Terminal on macOS/Linux).
  2. Type the following command and press Enter:
    ```
    java -version
    ```
     If Java is installed, you should see the version number. If Java is not installed, download it from the [official website](https://www.oracle.com/java/technologies/downloads/#java17?er=221886).

### Step 2: Download InvenTrack
1. Get the latest version of InvenTrack by downloading the `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).
2. Move the .jar file into the folder where you want to store your InvenTrack data (this will be your "home folder").

### Step 3: Navigate to the Folder
To run InvenTrack, you need to open your command terminal and navigate to the folder where you saved the .jar file:
1. Open a terminal (Command Prompt on Windows or Terminal on macOS/Linux).
2. Use the `cd` command to change to the folder containing the `.jar` file. For example:
   - On **Windows:**
     ```
     cd Desktop\InvenTrack
     ```
   - On **macOS/Linux:**
     ```
     cd ~/Desktop/InvenTrack
     ```
   Verify that your terminal is now pointing to the correct directory. The terminal prompt should show the folder name `(e.g. InvenTrack)`

### Step 4: Run the Application
To launch the application:
1. In the terminal, type the following command and press Enter:
    ```
    java -jar addressbook.jar
    ```
2. After a few seconds, the InvenTrack application should open. A window similar to the one below should appear (note that some sample data may be pre-loaded in the app):
   ![Ui](images/Ui.png)

### Step 5: Start Using InvenTrack
You can now start using the application by typing commands into the command box. For example:
   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a supplier named `John Doe` to the InvenTrack.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

Refer to the [Features](#features) below for details of each command.

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

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Stuck on how to use the app?

The Help command simplifies the process for you!

It opens a pop-up window containing a message to copy the link to the user guide, allowing you to learn more about the application.

Format: `help`

![help message](images/helpWindow.png)

### Adding a supplier: `add_supplier`

Adds a supplier to the address book.

Format: `add_supplier n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A supplier can have any number of tags (including 0)
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all suppliers : `list`

Shows a list of all suppliers in the address book.

Format: `list`

### Editing a supplier : `edit`

Edits an existing supplier in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the supplier at the specified `INDEX`. The index refers to the index number shown in the displayed supplier list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the supplier will be removed i.e adding of tags is not cumulative.
* You can remove all the supplier’s tags by typing `t/` without
    specifying any tags after it.


Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st supplier to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd supplier to be `Betsy Crower` and clears all existing tags.

### Deleting a supplier : `delete`
Deletes the specified supplier from the address book.

Format: `delete INDEX`

* Deletes the supplier at the specified `INDEX`.
* The index refers to the index number shown in the displayed supplier list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd supplier in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st supplier in the results of the `find` command.

### Adding a product : `add_product`

Add a product.

Format: `add_product n/NAME [st/STOCK_LEVEL] [su/SUPPLIER_NAME] [t/TAG]…`

### Assigning a product to supplier: `assign`
The **Assign** feature allows you to connect products with their current supplier. This makes it easier to track which supplier is responsible for each product in your store.

Format: `assign pr/PRODUCT_NAME su/SUPPLIER_NAME`

Example Commands:
- `assign Tissue Paper Jacob Smith` assigns product named `Tissue paper` to supplier named `Jacob Smith`

> **Important**:  
> The product and supplier must already exist in the system before you can assign them.  
> Make sure the product was created using the `NEW_PRODUCT` command and the supplier was created using the `NEW_SUPPLIER` command.

> **Note:**
> If product has already been assigned to supplier, the system will notify you.

### Un-assigning a product to supplier: `unassign`
Allows the user to remove or "unassign" products from their current supplier, useful if the store manager decides to stop sourcing a particular product from a supplier or switch to a new one.

Format: `unassign pr/PRODUCT_NAME su/SUPPLIER_NAME`

Example Commands:
- `unassign Tissue Paper Jacob Smith` Unassigns product named `Tissue paper` to supplier named `Jacob Smith`

> **Important**:  
> The product and supplier must already exist in the system before you can assign them.  
> Make sure the product was created using the `NEW_PRODUCT` command and the supplier was created using the `NEW_SUPPLIER` command.

> **Note:**
> If product was not assigned to supplier, the system will notify you.

### Setting threshold for a product: `threshold`

Updates the minimum stock level for a product.

Format: `threshold pr/PRODUCT_NAME stk/STOCK_LEVEL`

Examples:
* `threshold pr/sweater stk/1000`
* `threshold apr/chocolates stk/2623900`

### Updating stock level for a product: `update_stock`

Updates the current stock level for a product.

Format: `update_stock pr/PRODUCT_NAME stk/STOCK_LEVEL`

Examples:
* `update_stock pr/sweater stk/1000`
* `update_stock apr/chocolates stk/2623900`

### Updating maximum stock level for a product: `update_maxstock`

Updates the maximum stock level for a product.

Format: `update_maxstock pr/PRODUCT_NAME stk/STOCK_LEVEL`

Examples:
* `update_maxstock pr/sweater stk/1000`
* `update_maxstock apr/chocolates stk/2623900`

### Locating all suppliers: `view_suppliers`

Displays all the suppliers currently present in the supplier list.

Format: `view_suppliers`

Examples: `view_suppliers`

### Locating all suppliers: `view_products`

Displays all the products currently present in the product list.

Format: `view_products`

Examples: `view_suppliers`

### Deleting a supplier: `delete_supplier`

Deletes the specified supplier from the Supplier List.

Format: `delete_supplier pr/PRODUCT_NAME stk/STOCK_LEVEL`

Examples:
* `delete_supplier n/Jack Molly`

### Deleting a product: `delete_product`

Deletes the specified product from the Product List.

Format: `delete_product pr/PRODUCT_NAME stk/STOCK_LEVEL`

Examples:
* `delete_product n/sweater`

### Locating suppliers and products by name: `view`

### Locating suppliers and products by name: `view`

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

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**   | `list`
**Help**   | `help`
