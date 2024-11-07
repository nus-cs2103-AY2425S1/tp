---
## Quick start
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# InvenTrack User Guide

InvenTrack is a simple desktop app designed to help you manage the products and suppliers for your convenience store. The app is fast and easy to use, especially if you’re comfortable typing.

With InvenTrack, you can:

- Add and manage suppliers and products in your inventory.
- Keep track of which suppliers provide which products.
- Monitor and update stock levels.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## QuickStart
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

---

## Features

> **Notes about the command format:**<br>
> * **Words in UPPER_CASE**: Words like `PRODUCT_NAME` and `SUPPLIER_NAME` are placeholders for information you need to provide. <br>
 e.g For `add_product n/PRODUCT_NAME`, you would replace these placeholders with actual values, such as `add_product n/Apples`
> * **Items in square brackets are optional.**<br>
 e.g `add_product n/PRODUCT_NAME [st/STOCK_LEVEL]` can be used as `add_product n/Apples st/50` or as `add_product n/Apples`.
> * Items with **`…`** ​ after them can be **used multiple times including zero times.**<br>
 e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/beverage`, `t/beverage t/important` etc.
> * Parameters can be in any order.<br>
  e.g. if the command specifies `n/SUPPLIER_NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/SUPPLIER_NAME` is also acceptable.
> * Extraneous parameters for commands that do not take in parameters (such as `help`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.
> * **Copying Commands from PDFs:** If you’re copying commands from a PDF, be careful that spaces at line breaks may be omitted when pasted, so double-check the spacing.
> * Prefixes Glossary: <br>
> `n/` is for the name of supplier/product <br> `pr/` is for product <br> `su/` is for supplier <br> `p/` is for phone number of supplier <br> `e/` is for email of supplier <br> `a/` is for address of supplier <br> `t/` is for tags added to suppliers/products <br> `stk/` is for stock level of product <br> `min/` is for minimum stock level of product <br> `max/` is for maximum stock level of product <br> 
</box>
<box type="info" seamless><box>

**Notes about the command format:**<br>

### Viewing help : `help`

Stuck on how to use the app?

The Help command simplifies the process for you!

It opens a pop-up window containing a message to copy the link to the user guide, allowing you to learn more about the application.

Format: `help`

![help message](images/helpWindow.png)

### Adding a supplier: `add_supplier`

Let's add all of your suppliers into the system.

Format: `add_supplier n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

> **Tip:** A supplier can have any number of tags (including 0)

Examples:
* `add_supplier n/Fresh Farms Ltd p/98765432 e/contact@freshfarms.com a/23 Orchard Street, Suite 5`
* `add_supplier n/Global Produce Inc e/globalproduce@example.com p/1234567 a/789 Harvest Ave, Level 2 t/International t/Organic`

### Deleting a supplier : `delete`

If any supplier stops supplying goods, this command will help you delete their information from the system.

Format: `delete n/SUPPLIER_NAME`

* Deletes the supplier of the specified `SUPPLIER_NAME`.
* Supplier must exist in tracker for command to work.

Examples:
*  `delete n/Global Produce` deletes the supplier named Global Produce from the tracker.
* `find Betsy` followed by `delete 1` deletes the 1st supplier in the results of the `find` command.

### Adding a product : `add_product`

Now, let us add the products supplied in your store. 
This command will help you add the product information into the system.

Format: `add_product n/NAME [stk/STOCK_LEVEL] [su/SUPPLIER_NAME] [t/TAG]…`

Examples: 
- `add_product n/sweaters` Adds product named sweaters
- `add_product n/eggs stk/120` Adds product named eggs with current stock level as 120
- `add_product n/hakka noodles stk/90 su/Rachel Geller` Adds product named hakka noodles with current stock level as 90 and is assigned to supplier Rachel Geller


### Assigning a product to supplier: `assign`

This feature allows you to connect products with their current supplier. This makes it easier to track which supplier is responsible for each product in your store.

Format: `assign pr/PRODUCT_NAME su/SUPPLIER_NAME`

Example:
- `assign pr/Tissue Paper su/Jacob Smith` assigns product named Tissue paper to supplier named Jacob Smith

> **Important**:
> The product and supplier must already exist in the system before you can assign them.

> **Note:**
> If product has already been assigned to supplier, the system will notify you.

### Un-assigning a product to supplier: `unassign`

This feature allows you to remove the assigned supplier from the previously connected product. Useful if the store manager decides to stop sourcing a particular product from a supplier or switch to a new one.

Format: `unassign pr/PRODUCT_NAME`

Example:
- `unassign pr/Tissue Paper` Unassigns product named `Tissue paper`.

> **Important**:
> The product must already exist in the system before you can assign them.

> **Note:**
> If product was not assigned to supplier, the system will notify you.

### Setting threshold for a product: `set_threshold`

To allocate specified space for your products,you would want to update the minimum and maximum stock level for a product, this feature does that for you!

Format: `set_threshold pr/PRODUCT_NAME min/MIN_STOCK_LEVEL max/MAX_STOCK_LEVEL`

> **Note:**
> At least one of the prefixes is mandatory: min/ OR max/ <br>
> (Prefixes min/ AND max/ can be used together also, see examples)

Examples:
* `set_threshold pr/potato chips min/100`
* `set_threshold pr/chocolates max/230`
* `set_threshold pr/sweater min/380 max/900`

### Updating stock level for a product: `update_stock`

Stock level always seem to change after the purchases are done for the day, hence this feature lets you update the current stock level for the product.

Format: `update_stock pr/PRODUCT_NAME stk/STOCK_LEVEL`

> **Note:**
> Stock levels must be a number equal or above 0(zero).<br>
> Products should already exist in he system, otherwise errors are displayed.

Examples:
* `update_stock pr/sweater stk/1000`
* `update_stock apr/chocolates stk/2623900`

### Locating all suppliers: `view_supplier`

If you want to look through your current list of suppliers, view feature helps you to do it in two ways - 
1. See all the suppliers currently present in the supplier list
2. See the specified suppliers with the keyword provided

Format:

` view_supplier ` (For displaying details of all suppliers)

` view_supplier [KEYWORD] ` (For displaying details about specified supplier)

Examples: 
- `view_supplier`
- `view_supplier Sussane `

### Locating all products: `view_product`

If you want to look through your current list of products, view feature helps you to do it in two ways -
1. See all the products currently present in the supplier list
2. See the specified products with the keyword provided

Format:

` view_product ` (For displaying details of all products)

` view_product [KEYWORD] ` (For displaying details about specified product)

Examples: 
- `view_product`
- `view_product Socks `

### Deleting a supplier: `delete_supplier`

If a supplier does not supply products anymore, you can delete their information from the Supplier List, using this delete feature.

Format: `delete_supplier n/SUPPLIER_NAME`

Examples:
* `delete_supplier n/Jack Molly`

### Deleting a product: `delete_product`

If you want to delete a product from the Product List, this command will help you do that.

Format: `delete_product n/PRODUCT_NAME`

Examples:
* `delete_product n/sweater`

### Clearing all entries : `clear`

Clears all entries from the system.

Format: `clear`

### Exiting the program : `exit`

Exits from the program once all your tasks related to updating stocks, editing suppliers and product info etc. are done!

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after executing any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

> **Caution:**
> If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
> Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.

### Update Max Stock Level for a particular supplier's products
`[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action               | Format, Examples                                                                                                                                               |
|----------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Supplier**     | `add_supplier n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`<br> e.g., `add_supplier n/Fresh Farms Ltd p/98765432 e/contact@freshfarms.com a/Orchard St, Suite 5` |
| **Add Product**      | `add_product n/NAME [st/STOCK_LEVEL] [su/SUPPLIER_NAME] [t/TAG]…`<br> e.g., `add_product n/Tissue Paper st/500 su/Global Produce`                            |
| **Assign Product**   | `assign pr/PRODUCT_NAME su/SUPPLIER_NAME`<br> e.g., `assign pr/Tissue Paper su/Fresh Farms Ltd`                                                               |
| **Unassign Product** | `unassign pr/PRODUCT_NAME su/SUPPLIER_NAME`<br> e.g., `unassign pr/Tissue Paper su/Fresh Farms Ltd`                                                           |
| **Set Threshold**    | `set_threshold pr/PRODUCT_NAME min/MIN_STOCK_LEVEL max/MAX_STOCK_LEVEL`<br> e.g., `set_threshold pr/Tissue Paper min/100 max/1000`                           |
| **Update Stock**     | `update_stock pr/PRODUCT_NAME stk/STOCK_LEVEL`<br> e.g., `update_stock pr/Tissue Paper stk/300`                                                               |
| **View Suppliers**   | `view_supplier [KEYWORD]`<br> e.g., `view_supplier Fresh` or `view_supplier`                                                                                  |
| **View Products**    | `view_product [KEYWORD]`<br> e.g., `view_product Tissue` or `view_product`                                                                                    |
| **Delete Supplier**  | `delete_supplier n/SUPPLIER_NAME`<br> e.g., `delete_supplier n/Global Produce`                                                                                 |
| **Delete Product**   | `delete_product pr/PRODUCT_NAME`<br> e.g., `delete_product pr/Tissue Paper`                                                                                   |
| **Clear All**        | `clear`                                                                                                                                                       |
| **Help**             | `help`                                                                                                                                                        |
| **Exit**             | `exit`
