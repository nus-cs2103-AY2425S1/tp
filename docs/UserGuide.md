---
layout: page
title: User Guide
---

# Welcome to BakeBuddy

BakeBuddy is your all-in-one command-line companion for managing your home bakery business. Designed with speed and
efficiency in mind, it combines the power of a Command Line Interface (CLI) with intuitive features to help you focus
on what matters most - creating delicious baked goods.

BakeBuddy is a desktop application that streamlines your bakery operations by helping you manage:
- 🧁 Pastries and recipes
- 👥 Customers and their orders
- 📦 Suppliers and ingredients
- 📋 Order tracking and fulfillment
- 🗄️ Inventory management

## Why Choose BakeBuddy?

- **Speed First**: Execute commands quickly through our CLI, perfect for busy bakers
- **User-Friendly**: Simple GUI elements complement the CLI for enhanced usability
- **All-in-One Solution**: Manage every aspect of your bakery business from a single application
- **Efficiency Focused**: Designed specifically for home-based bakery owners who value their time

## Getting Started

This guide will walk you through everything you need to know about BakeBuddy, from basic commands to advanced features.

* Table of Contents
  {:toc}

--------------------------------------------------------------------------------------------------------------------

## Before You Begin ✔️

### Step 1: Check if Your Computer is Ready
First, we need to make sure your computer has Java 17 installed. Here's how to check:

1. Open your computer's terminal:
    - **For Windows**: Press the Windows key + R, type `cmd`, and press Enter
    - **For Mac**: Press Command + Space, type `terminal`, and press Enter

2. In the black window that appears, type exactly:
   ```
   java --version
   ```
   and press Enter

3. What you should see:
    - ✅ If you see "Java version 17" (or any number above 17), you're ready to go!
    - ❌ If you see "command not found" or a number below 17, visit [Java's download page](https://www.oracle.com/java/technologies/downloads/#java17) to install Java 17

### Step 2: Install BakeBuddy

1. Download BakeBuddy:
    - Click [this link](https://github.com/AY2425S1-CS2103T-T11-1/tp/releases) to download the latest BakeBuddy
    - Look for the file named `bakebuddy.jar`
    - Click on it to download

2. Create a home for BakeBuddy:
    - Create a new folder on your computer named `BakeBuddy`
    - Move the downloaded `bakebuddy.jar` file into this folder

3. Start BakeBuddy:
    - Open your terminal (like in Step 1)
    - Type `cd ` (with a space after cd)
    - Drag your BakeBuddy folder into the terminal window (this fills in the location automatically!)
    - Press Enter
    - Type:
      ```
      java -jar bakebuddy.jar
      ```
    - Press Enter

   You should see the BakeBuddy window appear!

### Step 3: Try Your First Commands

Now that BakeBuddy is running, let's add your first items. In the BakeBuddy window, you'll see a space to type commands at the top.

Refer to the [Features](#features) below for details of each command.

## Features


### **Add Contact Command**
Adds a new contact to the bakery’s database.

```bash
addContact n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [t/TAG]
```
- **Parameters:**
    - `n/NAME`: The person's name.
    - `p/PHONE_NUMBER`: The person's phone number.
    - `e/EMAIL`: (Optional) The person's email address.
    - `a/ADDRESS`: (Optional) The person's address.
    - `t/TAG`: (Optional) Tags for additional person information.

**Example:**
```bash
addContact n/Tim p/81234567 e/emily@example.com a/456 Cupcake Road, Block 123, 03-04 
```
![screenshot](images/Screenshot-2.png)

### **Add Customer Command**
Adds a new customer to the bakery’s customer database.

```bash
addContact n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [i/INFORMATION] [t/TAG]
```
- **Parameters:**
    - `n/NAME`: The customer's name.
    - `p/PHONE_NUMBER`: The customer's phone number.
    - `e/EMAIL`: (Optional) The customer's email address.
    - `a/ADDRESS`: (Optional) The customer's address.
    - `s/INFORMATION`: (Optional) Additional information of the customer such as dietary preference.
    - `t/TAG`: (Optional) Tags for additional customer information.

**Example:**
```bash
addCustomer n/Tim p/81234567 e/emily@example.com a/456 Cupcake Road, Block 123, 03-04 i/Allergic to peanuts
```
![screenshot](images/Screenshot-3.png)

### **Add Ingredient Command**
Adds a new ingredient to the bakery's ingredient catalogue, along with its cost.

```bash
addIngredient NAME COST
```
- **Parameters:**
    - `NAME`: The name of the ingredient.
    - `COST`: The cost of the ingredient.

**Example:**
```bash
addIngredient Syrup 3.50 
```
![screenshot](images/Screenshot-1.png)

### **Add Customer Order Command**
Adds a customer order by providing the customer's name, phone number and the pastry IDs from the pastry catalogue.

```bash
addCustomerOrder [n/NAME] p/PHONE_NUMBER o/PASTRYIDS [MORE_PASTRYIDSs...] [r/REMARK]
```
- **Parameters:**
    - `n/NAME`: (Optional) The customer's name.
    - `p/PHONE_NUMBER`: The phone number of the customer (new customer will be added with the name if phone number not found in contacts).
    - `o/PRODUCTID`: One or more pastry IDs for the items being ordered.
    - `r/REMARK`: (Optional) Information about the customer order.

**Example:**
```bash
addCustomerOrder n/John Doe p/98765432 o/1 2 3 r/Delivery at 6pm 
```
![screenshot](images/Screenshot-1.png)

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