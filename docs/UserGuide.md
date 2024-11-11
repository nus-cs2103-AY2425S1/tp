---
## Quick start
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# InvenTrack User Guide

Your Journey to Easier Inventory Management Starts Here! 📦

InvenTrack is a simple desktop app specially designed for Inventory managers and local convenience store owners helps you manage the products and suppliers for your convenience store. It empowers you to manage your inventory and suppliers without restrictions. 

🎯 The app is fast and easy to use, especially if you’re comfortable typing!

With InvenTrack, you can:

- Add and manage suppliers and products in your inventory.
- Keep track of which suppliers provide which products.
- Monitor and update stock levels of the products in the system.

---

## Table of Contents
1. [Quick Start](#quick-start)
2. [Notes about command format](#notes-about-the-command-format-and-other-information)
3. [Features](#features)
   - [Viewing Help](#viewing-help--help-)
   - [Autocomplete](#autocomplete--)
   - [Adding a supplier](#adding-a-supplier-add_supplier-)
   - [Adding a product](#adding-a-product--add_product-)
   - [Assigning a product to supplier](#assigning-a-product-to-supplier-assign---)
   - [Unassigning product from a supplier](#un-assigning-a-product-to-supplier-unassign---)
   - [Setting threshold for a product](#setting-threshold-for-a-product-set_threshold--)
   - [Updating stock level of a product](#updating-stock-level-for-a-product-update_stock-)
   - [Locating all suppliers](#locating-all-suppliers-view_supplier-)
   - [Locating all products](#locating-all-products-view_product-)
   - [Deleting a supplier](#deleting-a-supplier-delete_supplier-)
   - [Deleting a product](#deleting-a-product-delete_product-)
   - [Clearing all entries](#clearing-all-entries--clear-)
   - [Exiting the app](#exiting-the-program--exit-)
4. [Saving the data](#saving-the-data-)
5. [Editing the data file](#editing-the-data-file-)
6. [Upcoming features](#upcoming-features)
7. [FAQ](#faq)
8. [Known Issues](#known-issues)
9. [Command Summary](#command-summary-)

<page-nav-print />

---

## Quick Start
### Step 1: Install Java
Before you can use InvenTrack, ensure you have Java version `17` or above installed in your Computer. Java helps you to run our app in your desktop.
- To check if Java is installed:
  1. Open a command terminal using `cmd` in search (Command Prompt on Windows, Terminal on macOS/Linux). <br> Alternate way to open terminal: right-click on the opened folder or desktop, select `Open in terminal` option.
  2. Type the following command and press Enter:
    ```
    java -version
    ```
     If Java is installed, you should see the version number. <br> If Java is not installed, download it from the [official website](https://www.oracle.com/java/technologies/downloads/#java17?er=221886).

### Step 2: Download InvenTrack
1. Get the latest version of InvenTrack by downloading the `.jar` file from [here](https://github.com/AY2425S1-CS2103T-T17-3/tp/releases).
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

   * `add_supplier n/Kayla Beauty p/98136450 e/kaylab@hotmail.com a/Phoenix Cross Road, Hilton Avenue 6` : Adds a supplier named Kayla Beauty with respective phone, email and address.

   * `add_product n/Corn flour stk/50` Adds product named Corn flour with current stock level as 50

   * `view_product` : Helps you to view the current products in the system.

   * `view_supplier` : Lets you see the list of suppliers currently in the system.

   * `delete_supplier n/Kayla Beauty` : Deletes the supplier with the name 'Kayla Beauty'

   * `clear` : Deletes all the information about the suppliers and products in the system completely.

   * `exit` : Helps you to exit the app.

Refer to the [Features](#features) below to know more about the commands you could use in this app.

## **Notes about the command format and other information:**

> * **Words in UPPER_CASE**: Words like `PRODUCT_NAME` and `SUPPLIER_NAME` are placeholders for information you need to provide. <br>
 e.g For `add_product n/PRODUCT_NAME`, you would replace these placeholders with actual values, such as `add_product n/Apples`
> * **Items in square brackets are optional.**<br>
 e.g `add_product n/PRODUCT_NAME [stk/STOCK_LEVEL]` can be used as `add_product n/Apples` or as `add_product n/Apples stk/50`.
> * Items with **`…`** ​ after them can be **used multiple times including zero times.**<br>
 e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/beverage`, `t/beverage t/important` etc.
> * Parameters can be in any order.<br>
  e.g. if the command specifies `n/SUPPLIER_NAME p/PHONE_NUMBER e/SUPPLIER_EMAIL`, `p/PHONE_NUMBER e/SUPPLIER_EMAIL n/SUPPLIER_NAME` is also acceptable.
> * Extraneous parameters for commands that do not take in parameters (such as `help`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123` or `help -10`, it will be interpreted as `help`.
> * If duplicate tags are found for product or supplier, the system takes only one copy of it, others are ignored. But it is case-sensitive, hence `reliable` and `RELIABLE` will be treated as DIFFERENT TAGS.
> * Command words are case-insensitive. eg: `help` and `HELP` are the same.
> * **Copying Commands from PDFs:** If you’re copying commands from a PDF, be careful that spaces at line breaks may be omitted when pasted, so double-check the spacing.
> * Prefixes Glossary: <br>
> `n/` is for the name of supplier/product <br> `pr/` is for product <br> `su/` is for supplier <br> `p/` is for phone number of supplier <br> `e/` is for email of supplier <br> `a/` is for address of supplier <br> `t/` is for tags added to suppliers/products <br> `stk/` is for stock level of product <br> `min/` is for minimum stock level of product <br> `max/` is for maximum stock level of product <br> 
</box>
<box type="info" seamless><box>
---

## Features

### Viewing help : `help` 🤝

Stuck on how to use the app?

The Help command simplifies the process for you!

It opens a pop-up window containing a message to copy the link to the user guide, allowing you to learn more about the application.

Format: `help`

![help message](images/helpWindow.png)

---

### Autocomplete : 🤖

Want the app to automatically complete the commands, supplier name or the product names you want?

- Just press tab button on your keyboard to see the information!

> **NOTE:** 
> It works only if you press tab button from your keyboard and only for the commands, supplier name or the product names currently.

![Autocomplete feature](images/autocomplete_feature.png)

---

### Adding a supplier: `add_supplier` 🙋‍♂️🙋‍♀️

First let us add your suppliers into the system. 
<br> To do this, use the given command format:

Format: `add_supplier n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

> **NOTE:**
> - A supplier can have any number of tags (including 0) <br>
> - Currently, there is no restriction in phone number format, so you can have (+65) 97136544 or even +91-97664 23668, according to any country specific numbers you want to use.
> - No value allows usage of "/", so if you want to use S/O in names, it is better to use alternatives like "son of" as there is no such limit for name length

Here are a few examples to help you:
* `add_supplier n/Fresh Farms Ltd p/98765432 e/contact@freshfarms.com a/23 Orchard Street, Suite 5`
* `add_supplier n/Global Produce Inc e/globalproduce@example.com p/1234567 a/789 Harvest Ave, Level 2 t/Organic`

---

### Adding a product : `add_product` 🧋🧶

Now, let us add the products supplied in your store. 
This command will help you add the product information into the system.

Format: `add_product n/NAME [stk/STOCK_LEVEL] [su/SUPPLIER_NAME] [t/TAG]…`

Here are a few examples to help you:
- `add_product n/sweaters` Adds product named sweaters
- `add_product n/eggs stk/120` Adds product named eggs with current stock level as 120
- `add_product n/hakka noodles stk/90 su/Rachel Geller` Adds product named hakka noodles with current stock level as 90 and is assigned to supplier Rachel Geller

> **IMPORTANT**:
> The product and supplier must already exist in the system before you can assign them.

> **NOTE:**
> MAX_STOCK_LEVEL is the maximum number of products you would like to have in your store for a particular product, so it might happen that CURRENT_STOCK_LEVEL maybe greater than the former because of greater number of purchase order. This helps to suit real world implementation.

---

### Assigning a product to supplier: `assign` 🧋 ➡️ 🙋‍♀️

This feature allows you to connect products with their current supplier. This makes it easier to track which supplier is responsible for supplying each product in your store.

Format: `assign pr/PRODUCT_NAME su/SUPPLIER_NAME`

Here are a few examples to help you:
- `assign pr/Tissue Paper su/Jacob Smith` assigns product named Tissue paper to supplier named Jacob Smith

> **IMPORTANT**:
> The product and supplier must already exist in the system before you can assign them.

> **NOTE:**
> If product has already been assigned to supplier, the system will notify you.

- After executing the command you can scroll down to see the supplier for product has been assigned <br> <br>
![Assign feature](images/assign_feature.png)
---

### Un-assigning a product to supplier: `unassign` 🧋 ❌ 🙋‍♀️

This feature allows you to remove the assigned supplier from the previously connected product. Useful if the store manager decides to stop sourcing a particular product from a supplier or switch to a new one.

Format: `unassign pr/PRODUCT_NAME`

Here are a few examples to help you:
- `unassign pr/Tissue Paper` Unassigns product named `Tissue paper`.

> **IMPORTANT**:
> The product and supplier which is to be unassigned from each other must exist in the system. <br>
> The product must already be assigned before you can unassign them.

> **NOTE:**
> If product was not assigned to supplier, the system will notify you.

---

### Setting threshold for a product: `set_threshold` ➖➕ 

To allocate specified space for your products,you would want to update the minimum and maximum stock level for a product, this feature does that for you!

Format: `set_threshold pr/PRODUCT_NAME [min/MIN_STOCK_LEVEL] max/MAX_STOCK_LEVEL` OR `set_threshold pr/PRODUCT_NAME min/MIN_STOCK_LEVEL [max/MAX_STOCK_LEVEL]`

Here are a few examples to help you:
* `set_threshold pr/potato chips min/100`
* `set_threshold pr/chocolates max/230`
* `set_threshold pr/sweater min/380 max/900`

> **IMPORTANT:**
> At least one of the prefixes is mandatory: min/ OR max/ <br>
> (Prefixes min/ AND max/ can be used together also, see examples)

> **NOTE:**
> MAX_STOCK_LEVEL is the maximum number of products you would like to have in your store for a particular product, so it might happen that CURRENT_STOCK_LEVEL maybe greater than the former because of greater number of purchase order. This helps to suit real world implementation.

- Before executing the command
![Before set threshold](images/before_setthreshold.png)


- After executing the command successfully
![Before set threshold](images/after_setthreshold.png)
---

### Updating stock level for a product: `update_stock` 📈📦📉

Stock level always seem to change after the purchases are done for the day, hence this feature lets you update the current stock level for the product.

Format: `update_stock pr/PRODUCT_NAME stk/STOCK_LEVEL`

Here are a few examples to help you:
* `update_stock pr/sweater stk/1000`
* `update_stock pr/chocolates stk/2623900`

> **NOTE:**
> Stock levels must be a number equal or above 0(zero).<br>
> Products should already exist in the system, otherwise errors are displayed.

> **NOTE:**
> MAX_STOCK_LEVEL is the maximum number of products you would like to have in your store for a particular product, so it might happen that CURRENT_STOCK_LEVEL maybe greater than the former because of greater number of purchase order. This helps to suit real world implementation.
---

### Locating all suppliers: `view_supplier` 🔎🙋‍♀️

If you want to look through your current list of suppliers, view feature helps you to do it in 3-4 ways - 
1. See All the suppliers currently present in the supplier list.
2. See the specified suppliers with the keyword provided.
3. Suppliers filtered by tags.

Format:

` view_supplier ` (For displaying details of all suppliers)

`view_supplier n/KEYWORD` (For displaying specified suppliers matched with the keyword)

` view_supplier t/TAG... ` (For displaying details about filtered suppliers)

` view_supplier n/KEYWORD t/TAG... ` (For displaying details about filtered/sorted suppliers)

Here are a few examples to help you:
- `view_supplier`
- `view_supplier n/Sussane`
- `view_supplier t/reliable`
- `view_supplier n/Sussane t/reliable`

---

### Locating all products: `view_product` 🔎📦

If you want to look through your current list of products, view feature helps you to do it in 5 ways -
1. See All the products currently present in the product list.
2. See the specified products with the keyword provided.
3. Products filtered by tags.
4. Products filtered by supplier.
5. Products sorted by proximity of stock level from minimum threshold (stockLevel - minStockLevel) in increasing or decreasing order.

Format:

` view_product ` (For displaying details of all products)

` view_product [n/NAME] [t/TAG]... [su/SUPPLIER_NAME] [sort/i|sort/d] ` (For displaying details about specified products)

Here are a few examples to help you:
- `view_product`
- `view_product su/Best Supplier`
- `view_product n/chocolate`
- `view_product n/chocolate t/dessert`
- `view_product n/chocolate t/dessert su/Best Supplier`
- `view_product n/chocolate t/dessert su/Best Supplier sort/d`
- `view_product t/dessert`
- `view_product t/dessert sort/i`
- `view_product t/dessert su/Best Supplier`

---

### Deleting a supplier: `delete_supplier` ⛔🙋‍♀️

If a supplier does not supply products anymore, you can delete their information from the Supplier List, using this delete feature.

Format: `delete_supplier su/SUPPLIER_NAME`

Examples:
* `delete_supplier su/Jack Molly`
* `delete_supplier su/Kayla Beauty`

> **NOTE:**
> Suppliers should already exist in the system, otherwise errors are displayed.

---

### Deleting a product: `delete_product` ⛔🧶

If you want to delete a product from the Product List, this command will help you do that.

Format: `delete_product pr/PRODUCT_NAME`

Here are a few examples to help you:
* `delete_product pr/sweater`
* `delete_product pr/ramen noodles`

> **NOTE:**
> Products should already exist in the system, otherwise errors are displayed.

---

### Clearing all entries : `clear` 🧹🗂️

Clears all information about products and suppliers from the system permanently.

Format: `clear`

> **IMPORTANT:**
> There is no way to restore the information once cleared, so kindly think twice before executing this action!

---

### Exiting the program : `exit` 🚪👋

Exits from the program once all your tasks related to updating stocks, editing suppliers and product info etc. are done!

Format: `exit`

---

### Saving the data 🔐

InvenTrack data are saved in the hard disk automatically after executing any command that changes the data. There is no need to save manually. <br>

> **NOTE:**
> The location of where the data is stored is specified at the bottom status bar of the app.

---

### Editing the data file 📝

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

> **Caution:**
> If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
> Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.

---

### Upcoming Features

- Update Max Stock Level for a particular supplier's products
`[coming in v2.0]`

_Details coming soon ..._

---

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates the file that contains the data of your previous AddressBook home folder.

---

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

---

## Command summary 📋

| Action               | Format, Examples                                                                                                                                                      |
|----------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Supplier**     | `add_supplier n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`<br> e.g., `add_supplier n/Fresh Farms Ltd p/98765432 e/contact@freshfarms.com a/Orchard St, Suite 5` |
| **Add Product**      | `add_product n/NAME [st/STOCK_LEVEL] [su/SUPPLIER_NAME] [t/TAG]…`<br> e.g., `add_product n/Tissue Paper st/500 su/Global Produce`                                     |
| **Assign Product**   | `assign pr/PRODUCT_NAME su/SUPPLIER_NAME`<br> e.g., `assign pr/Tissue Paper su/Fresh Farms Ltd`                                                                       |
| **Unassign Product** | `unassign pr/PRODUCT_NAME su/SUPPLIER_NAME`<br> e.g., `unassign pr/Tissue Paper su/Fresh Farms Ltd`                                                                   |
| **Set Threshold**    | `set_threshold pr/PRODUCT_NAME min/MIN_STOCK_LEVEL max/MAX_STOCK_LEVEL`<br> e.g., `set_threshold pr/Tissue Paper min/100 max/1000`                                    |
| **Update Stock**     | `update_stock pr/PRODUCT_NAME stk/STOCK_LEVEL`<br> e.g., `update_stock pr/Tissue Paper stk/300`                                                                       |
| **View Suppliers**   | `view_supplier [KEYWORD]`<br> e.g., `view_supplier Fresh` or `view_supplier`                                                                                          |
| **View Products**    | `view_product [KEYWORD]`<br> e.g., `view_product Tissue` or `view_product`                                                                                            |
| **Delete Supplier**  | `delete_supplier n/SUPPLIER_NAME`<br> e.g., `delete_supplier n/Global Produce`                                                                                        |
| **Delete Product**   | `delete_product pr/PRODUCT_NAME`<br> e.g., `delete_product pr/Tissue Paper`                                                                                           |
| **Autocomplete**     | Press tab to execute this feature while writing commands                                                                                                              |
| **Clear All**        | `clear`                                                                                                                                                               |
| **Help**             | `help`                                                                                                                                                                |
| **Exit**             | `exit`                                                                                                                                                                
