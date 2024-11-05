---
layout: page
title: User Guide
---

BakeBuddy is an intuitive Command Line Interface (CLI)-based desktop application, tailor-made to empower home-based 
bakery owners to efficiently manage their customers, suppliers, pastries, ingredients, customer and supply orders. 
By combining the speed of a CLI with the user-friendliness of a simple Graphical User Interface (GUI), BakeBuddy offers 
the perfect balance between efficiency and usability. BakeBuddy is an address book solution designed for users who 
prefer quick and efficient commands over traditional GUI-based apps, making orders, customer and supplier management 
seamless for busy home-based bakery owners.

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

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

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
</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

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

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

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

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

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

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`

--------------------------------------------------------------------------------------------------------------------
## Key New Features
- Add functionality to add new customers and new suppliers to the BakeBuddy address book application
- Add a PastryCatalogue that stores pastry item names, including its price and ingredients used
- Add commands to add and remove pastries from catalogue
- Add an IngredientCatalogue that stores ingredient names, including their prices
- Add commands to add and remove ingredients from catalogue
- Add order tracking functionality such as adding a SupplyOrder, CustomerOrder and viewing a master list of current orders
- Create custom tags for customer and supplier for easy identification (red for customer, green for supplier)
- Add a stock inventory to keep track of pastries and ingredients
- Add commands to check the stock of pastries and ingredients from inventory
- Mark CustomerOrder and SupplyOrder as done when the order is completed

## New Commands Added

### 1. **Add Customer Command**
Adds a new customer to the bakery’s customer database.

```bash
addCustomer n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS i/INFORMATION [t/TAG] 
```
- **Parameters:**
    - `n/NAME`: The customer's name.
    - `p/PHONE_NUMBER`: The customer's phone number.
    - `e/EMAIL`: The customer's email address.
    - `a/ADDRESS`: The customer's address.
    - `i/INFORMATION`: The customer's information(e.g allergies, preferences etc.).
    - `t/TAG`: (Optional) Tags for additional customer information.

**Example:**
```bash
addCustomer n/John Doe p/12345678 e/john@example.com a/456 Pastry Street i/Allergic to dairy
```
<img width="1492" alt="Screenshot 2024-10-24 at 6 50 40 PM" src="https://github.com/user-attachments/assets/3bcacbc0-9002-492d-9519-5cbaa4d214ab">

### 2. **Add Supplier Command (WIP)**
Adds a new supplier to the bakery’s supplier database.

```bash
addSupplier n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS s/INGREDIENTS_SUPPLIED [t/TAG]
```
- **Parameters:**
    - `n/NAME`: The supplier's name.
    - `p/PHONE_NUMBER`: The supplier's phone number.
    - `e/EMAIL`: The supplier's email address.
    - `a/ADDRESS`: The supplier's address.
    - `s/INGREDIENTS_SUPPLIED`: List of ingredients supplied, comma separated
    - `t/TAG`: (Optional) Tags for additional supplier information.

**Example:**
```bash
addSupplier n/tim p/81234567 e/emily@example.com a/456 Cupcake Road, Block 123, #03-04 s/salt, chocolate
```
<img width="1503" alt="Screenshot 2024-10-24 at 6 53 16 PM" src="https://github.com/user-attachments/assets/76d7e905-4556-419f-8390-5b3e809db145">

### 3. **Add Ingredient Command**
Adds a new ingredient to the bakery's ingredient catalogue.

```bash
addIngredient NAME COST
```
- **Parameters:**
    - `NAME`: The name of the ingredient.
    - `COST`: The cost of the ingredient.

**Example:**
```bash
addIngredient Flour 1.50
```
<img width="1198" alt="Screenshot 2024-10-24 at 5 43 47 PM" src="https://github.com/user-attachments/assets/afc69bdd-b85e-4d9d-8880-2f557ac5720d">

### 4. **Add Pastry Command**
Adds a new pastry to the bakery's pastry catalogue, along with its ingredients.

```bash
addPastry NAME COST INGREDIENT [MORE_INGREDIENTS...]
```
- **Parameters:**
    - `NAME`: The name of the pastry.
    - `COST`: The cost of the pastry.
    - `INGREDIENT`: One or more ingredient names.

**Example:**
```bash
addPastry Croissant 3.50 Flour Cream Sugar
```
<img width="1184" alt="Screenshot 2024-10-24 at 5 48 02 PM" src="https://github.com/user-attachments/assets/f9272303-8ce3-442b-83d9-fbe6536a607e">


### 5. **Remove Ingredient Command**
Removes an existing ingredient from the bakery's ingredient catalogue.

```bash
removeIngredient NAME
```
- **Parameters:**
    - `NAME`: The name of the ingredient to remove.

**Example:**
```bash
removeIngredient Flour
```
<img width="1196" alt="Screenshot 2024-10-24 at 5 46 00 PM" src="https://github.com/user-attachments/assets/d2e5bece-dfb7-46c5-91ab-a666bf39a7e6">


### 6. **Remove Pastry Command**
Removes a pastry from the bakery's pastry catalogue.

```bash
removePastry NAME
```
- **Parameters:**
    - `NAME`: The name of the pastry to remove.

**Example:**
```bash
removePastry Croissant
```
<img width="1195" alt="Screenshot 2024-10-24 at 5 48 58 PM" src="https://github.com/user-attachments/assets/9fc0d387-db99-4dc0-af25-98b53e21ff26">


### 7. **Add CustomerOrder Command**
Adds a customer order by providing the customer's phone number and the product IDs from the catalogue.

```bash
addCustomerOrder p/PHONE_NUMBER r/REMARK o/PASTRYID [MORE_PASTRYIDs...] [n/NAME]
```
- **Parameters:**
    - `PHONE_NUMBER`: The phone number of the customer.
    - `REMARK`: Details of the order
    - `PASTRYID`: One or more pastry IDs for the items being ordered in the pastry catalogue.

**Example:**
```bash
addCustomerOrder p/98765432 r/Self-collection at 6pm o/1 1 2 3 n/John Doe
```
<img width="1191" alt="Screenshot 2024-10-24 at 5 50 18 PM" src="https://github.com/user-attachments/assets/ebedf898-8319-497e-8f89-e32a9f62c3f3">


### 8. **Delete CustomerOrder Command**
Removes an existing customer order from the system by using the index in the displayed customer orders.

```bash
deleteCustomerOrder INDEX
```
- **Parameters:**
    - `INDEX`: The index of the order to be deleted.

**Example:**
```bash
deleteCustomerOrder 1
```
<img width="1194" alt="Screenshot 2024-10-24 at 5 51 01 PM" src="https://github.com/user-attachments/assets/a30d3276-7a26-4c12-8df7-364d76e7c9dc">

### 9. **Add SupplyOrder Command**
Adds a supplier order by providing the supplier's phone number and the product IDs from the ingredient catalogue.

```bash
addSupplyOrder p/PHONE_NUMBER r/REMARK o/INGREDIENTID [more INGREDIENTIDs...] [n/NAME]
```
- **Parameters:**
  - `PHONE_NUMBER`: The phone number of the supplier.
  - `REMARK`: Details of the order
  - `INGREDIENTID`: One or more ingredient IDs for the items being supplied in the ingredient catalogue.

**Example:**
```bash
addSupplyOrder p/98765432 r/Delivery at 6pm o/1 2 3 n/John Doe
```
<img width="1194" alt="Screenshot 2024-10-24 at 5 51 43 PM" src="https://github.com/user-attachments/assets/59de6ead-e460-419a-807f-30fa5f17b39a">


### 10. **Delete SupplyOrder Command**
Removes an existing supplier order by using its index in the displayed supply orders.

```bash
deleteSupplyOrder INDEX
```
- **Parameters:**
    - `ORDER_ID`: The ID of the supplier order to be deleted.

**Example:**
```bash
deleteSupplyOrder 1
```
<img width="1187" alt="Screenshot 2024-10-24 at 5 52 54 PM" src="https://github.com/user-attachments/assets/c2f0d0a7-7179-4b1d-b967-f65b7791472d">


### 11. **View OrderList Command**
Displays the complete list of all existing customer and supplier orders in the system.

```bash
viewOrder
```

**Example:**
```bash
viewOrder
```
<img width="1194" alt="Screenshot 2024-10-24 at 5 59 59 PM" src="https://github.com/user-attachments/assets/d19884d9-f7ee-4248-9454-68a56b8e60cd">


### 12. **View IngredientCatalogue Command**
Displays the current list of ingredients available in the bakery’s ingredient catalogue.

```bash
viewIngredientCatalogue
```

**Example:**
```bash
viewIngredientCatalogue
```
<img width="1192" alt="Screenshot 2024-10-24 at 6 00 43 PM" src="https://github.com/user-attachments/assets/55d8aac3-f1e6-4b5c-ad69-f7a43cd7e042">


### 13. **View PastryCatalogue Command**
Displays the current list of pastries available in the bakery’s pastry catalogue.

```bash
viewPastryCatalogue
```

**Example:**
```bash
viewPastryCatalogue
```
<img width="1189" alt="Screenshot 2024-10-24 at 6 01 34 PM" src="https://github.com/user-attachments/assets/950a16e0-4d2b-4852-835a-80f9e54b7946">

### 14. **View Inventory Command**
Displays the current list of ingredients available in the bakery’s inventory.

```bash
viewInventory
```

**Example:**
```bash
viewInventory
```
<img width="1191" alt="Screenshot 2024-10-24 at 6 05 21 PM" src="https://github.com/user-attachments/assets/e6dfd68d-f05e-4f2d-8a36-ac8fb272af49">

### 15. **check Pastry Stock Command**
Check the stock in the inventory for a certain pastry.

```bash
checkPastryStock PASTRY
```
- **Parameters:**
    - `PASTRY`: The name of the pastry to be checked.

**Example:**
```bash
checkPastryStock Croissant
```
This command checks the inventory to confirm if enough pastries are available, 
helping ensure customer order fulfillment.

### 16. **check Ingredient Stock Command**
Check the stock in the inventory for a certain ingredient.

```bash
checkIngredientStock INGREDIENT
```
- **Parameters:**
    - `INGREDIENT`: The name of the ingredient to be checked.

**Example:**
```bash
checkIngredientStock Flour
```
<img width="1195" alt="Screenshot 2024-10-24 at 6 13 59 PM" src="https://github.com/user-attachments/assets/a0f1c909-fb2e-44ed-82dc-24b87f1eb432">

This command checks the inventory to confirm if sufficient ingredients are available to make a pastry, 
helping ensure supplier order fulfillment.

### 17. **Mark Customer Order Command**
Mark the customer order as done.

```bash
markCustomerOrder INDEX
```
- **Parameters:**
    - `INDEX`: The Order Index for CustomerOrder.

**Example:**
```bash
markCustomerOrder 1
```
<img width="1193" alt="Screenshot 2024-10-24 at 6 28 08 PM" src="https://github.com/user-attachments/assets/76bb74ac-0871-499f-b475-04a2f9bcdd14">

### 17. **Mark Supplier Order Command**
Mark the supplier order as done.

```bash
markSupplierOrder INDEX
```
- **Parameters:**
    - `INDEX`: The Order Index for SupplierOrder.
-----------------------------------------------------------------------------------------

## Key New Features Summary

Action | Format, Examples
-------|------------------
**Add Customer** | addCustomer n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS i/INFORMATION [t/TAG] <br> e.g., addCustomer n/John Doe p/12345678 e/john@example.com a/456 Pastry Street i/Allergic to dairy
**Add Supplier** | addSupplier n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS s/INGREDIENTS_SUPPLIED [t/TAG] <br> e.g., addSupplier n/Tim p/81234567 e=tim@example.com a/456 Cupcake Road, Block 123, #03-04 s/salt, chocolate
**Add Ingredient** | addIngredient NAME COST <br> e.g., addIngredient Flour 1.50
**Add Pastry** | addPastry NAME COST INGREDIENT [MORE_INGREDIENTS...] <br> e.g., addPastry Croissant 3.50 Flour Cream Sugar
**Remove Ingredient** | removeIngredient NAME <br> e.g., removeIngredient Flour
**Remove Pastry** | removePastry NAME <br> e.g., removePastry Croissant
**Add Customer Order** | addCustomerOrder PHONE_NUMBER PRODUCTID [MORE_PRODUCTIDs...] <br> e.g., addCustomerOrder 12345678 1 2 3
**Delete Customer Order** | deleteCustomerOrder INDEX <br> e.g., deleteCustomerOrder 1
**Add Supply Order** | addSupplyOrder PHONE_NUMBER PRODUCTID [MORE_PRODUCTIDs...] <br> e.g., addSupplyOrder 98765432 1 2 3
**Delete Supply Order** | deleteSupplyOrder INDEX <br> e.g., deleteSupplyOrder 1
**View Orders** | viewOrder
**View Ingredient Catalogue** | viewIngredientCatalogue
**View Pastry Catalogue** | viewPastryCatalogue
**View Inventory** | viewInventory
**Check Pastry Stock** | checkPastryStock PASTRY <br> e.g., checkPastryStock Croissant
**Check Ingredient Stock** | checkIngredientStock INGREDIENT <br> e.g., checkIngredientStock Flour
**Mark Customer Order** | markCustomerOrder INDEX <br> e.g., markCustomerOrder 1
**Mark Supplier Order** | markSupplierOrder INDEX <br> e.g., markSupplierOrder 1