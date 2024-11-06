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
</div>

### **Add Pastry Command**
Adds a new pastry to the bakery's pastry catalogue.

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

The Add Pastry Command allows bakery owners to add a new pastry item to their pastry catalogue, specifying the name, 
cost and ingredients for each pastry. This command accepts the pastry's name, a numeric value for how much the 
bakery owner will sell it for, and a list of ingredients required to make it. 

For example, typing **addPastry Croissant 3.50 Flour Cream Sugar** would add a pastry named "Croissant" priced at $3.50, 
with "Flour," "Cream," and "Sugar" listed as its ingredients. By using this command, bakery owners can easily track of 
all their pastries and ingredient needs, making it more convenient for bakery owners to manage their inventory.

### **Add Supplier Command**
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

The Add Supplier Command allows bakery owners to add a new supplier to the address book records. The supplier is specified
with all relevant contact details and ingredients supplied to the bakery. This command accepts the supplier's name, 
phone number, email address, residential address, and a list of ingredients the supplier provides, with an option to
include a tag for additional information. 

For example, typing **addSupplier n/tim p/81234567 e/emily@example.com a/456 Cupcake Road, Block 123, #03-04 s/salt, chocolate** 
will add a supplier named "Tim" with phone number "81234567," email "emily@example.com," located at "456 Cupcake Road, Block 123, #03-04," 
and supplying "salt" and "chocolate" as the supplied ingredients for the bakery. This command simplifies the tracking 
of supplier contacts and ingredient sources, helping bakery owners efficiently manage supplier relationships and inventory.

### **Add Supply Order Command**
Adds a supplier order by providing the supplier's name, phone number and the product IDs from the ingredient catalogue.

```bash
addSupplyOrder n/NAME p/PHONE_NUMBER o/PRODUCTID [MORE_PRODUCTIDs...]
```
- **Parameters:**
    - `n/NAME`: The supplier's name.
    - `p/PHONE_NUMBER`: The phone number of the supplier.
    - `o/PRODUCTID`: One or more product IDs for the items being supplied.

**Example:**
```bash
addSupplyOrder p/98765432 r/Delivery at 6pm o/1 2 3 n/John Doe
```
<img width="1194" alt="Screenshot 2024-10-24 at 5 51 43 PM" src="https://github.com/user-attachments/assets/59de6ead-e460-419a-807f-30fa5f17b39a">

The Add Supply Order Command enables bakery owners to add a new order from a supplier by specifying the supplier's name,
phone number, and a list of product IDs from the ingredient catalogue, identifying which items are included in the order.

For example, typing **addSupplyOrder n/John Doe p/98765432 o/1 2 3** creates a supply order for a supplier named
"John Doe" with phone number "98765432," ordering products with IDs "1," "2," and "3" from the ingredient catalogue.
This command helps bakery owners efficiently manage incoming supplies, track supply orders by product ID, and streamline 
the ordering process for necessary ingredients.

### **Check Ingredient Stock Command**
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

The Check Ingredient Stock Command allows bakery owners to verify the current stock level of a specified ingredient in 
their inventory. This command requires the ingredient's name and will display the available quantity for that ingredient 
if it is in stock. 

For example, typing **checkIngredientStock Flour** checks the inventory for the "Flour" ingredient and returns the 
quantity available if there is sufficient stock. This command helps bakery owners keep track of ingredient levels, 
ensuring they have sufficient ingredients to make pastries for customers and can plan pastry orders when needed.

### **Check Pastry Stock Command**
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
![Pastry Stock.png](images%2FPastry%20Stock.png)

The Check Pastry Stock Command allows bakery owners to verify the availability of a specific pastry in their inventory.
This command requires only the pastry's name and will display whether there is sufficient stock to meet potential 
customer demand. 

For example, typing **checkPastryStock Croissant** checks the inventory for "Croissant" pastry and returns the quantity 
available if there are sufficient pastries. This command helps bakery owners ensure they can fulfill customer orders 
by maintaining the right stock levels for popular pastries.


### **Clear All Command**
Removes all entries from the bakery’s address book, including customers, suppliers, and any related information.

```bash
clear
```
**Example:**
```bash
clear
```
![Clear.png](images%2FClear.png)

The Clear All Command clears all data in the address book, removing all stored records such as customer and supplier 
details and inventory items.

For example, typing **clear** will remove all customer, supplier and inventory information from the address book.
This command provides a quick way to delete all existing records in one go, allowing bakery owners to reset their 
address book fully. It is helpful when bakery owners want a fresh start or data needs to be purged for any reason.
This command ensures the address book is empty and ready for new entries.

### **Find persons by name**
Finds persons whose names contain any of the given keywords.

```bash
find KEYWORD [MORE_KEYWORDS]
```
- **Parameters:**
    - `KEYWORD`: The keyword to search for.
    - `[MORE_KEYWORDS]`: Additional keywords to search for.
    - 
* Use the list command to return to the full contact listlist
* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

**Example:**
```bash
find Charlotte Bernice
```
![findcommand.png](images/findcommand.png)

### **List Command**
Lists all contacts in the bakery's address book.

***Example:***
```bash
list
````
![listcommandui.png](images/listcommandui.png)

### **Help Command**
Displays a link to our user guide that provides detailed information on how to use the application.

***Example:***
```bash
help
````
![helpcommandui.png](images/helpcommandui.png)

### **Mark Customer Order Command**
Mark the customer order status as done.

```bash
markCustomerOrder INDEX
```
- **Parameters:**
    - `INDEX`: The index number for the CustomerOrder displayed in the list.

**Example:**
```bash
markCustomerOrder 1
```
![markcustomerorderui.png](images/markcustomerorderui.png)

### **Mark Supply Order Command**
Mark the supplier order status as done.

```bash
markSupplyOrder INDEX
```
- **Parameters:**
    - `INDEX`: The index for theSupplyOrder displayed in the list.

**Example:**
```bash
markSupplyOrder 1
```
![marksupplyorderui.png](images/marksupplyorderui.png)

### **Unmark Customer Order Command**
Unmark the customer order status as pending.

```bash
unmarkCustomerOrder INDEX
```
- **Parameters:**
    - `INDEX`: The index number for the CustomerOrder displayed in the list.

**Example:**
```bash
unmarkCustomerOrder 1
```
![unmarkcustomerorderui.png](images/unmarkcustomerorderui.png)

### **Unmark Supply Order Command**
Unmark the supply order status as pending.

```bash
unmarkSupplyOrder INDEX
```
- **Parameters:**
    - `INDEX`: The index for theSupplyOrder displayed in the list.

**Example:**
```bash
umarkSupplyOrder 1
```
![unmarksupplyorderui.png](images/unmarksupplyorderui.png)

### ***Remark Command***
Edits the remark of the person identified by the index number used in the last person listing. 
The existing remark will be overwritten.

```bash
remark INDEX r/REMARK
```

- **Parameters:**
    - `INDEX`: The index number of the person in the last listing.
    - `r/REMARK`: The new remark to be updated.

**Example:**
```bash
remark 1 r/Regular customer
```
![remarkcommandui.png](images/remarkcommandui.png)