---
layout: page
title: User Guide 
---

<h1> <b>Welcome to BakeBuddy 🧁 🍰</b> </h1>

BakeBuddy is your perfect companion if you're running a home-based bakery business or planning to start one. Whether 
you're managing a small cupcake operation or a medium-sized pastry business, we've designed BakeBuddy with you in mind. 
If you're comfortable typing messages (like in WhatsApp) and prefer quick keyboard commands over clicking through 
menus, you'll feel right at home with our interface. BakeBuddy helps you manage your:
- 🧁 Pastries and recipes
- 👥 Customers and their orders
- 📦 Suppliers and ingredients
- 📋 Order tracking and fulfillment

<h2> <b>What We Assume About You 👨‍🍳 👩‍🍳</b> </h2>

We've designed BakeBuddy assuming you have basic computer skills - enough to download and run applications, and type 
comfortably in English. Don't worry if you're not tech-savvy; this comprehensive user guide will walk you through 
every step, and no technical knowledge is needed.

<h2> <b>Why Choose BakeBuddy?</b> </h2>

- **Speed First**: Execute commands quickly through our Command Line Interface (CLI), perfect for busy bakers
- **User-Friendly**: Simple Graphical User Interface (GUI) elements complement the CLI for enhanced usability
- **All-in-One Solution**: Manage every aspect of your bakery business from a single application
- **Efficiency Focused**: Designed specifically for home-based bakery owners who value their time

<div style="page-break-after: always;"></div>

<h1> <b>Table of Contents</b> </h1>
* Table of Contents
{:toc}

<div style="page-break-after: always;"></div>

# **Quick Start**
## **Step 1: Check if Your Computer is Ready**
First, we need to make sure your computer has Java 17 installed. Here's how to check:

1. Open your computer's terminal:
  - **For Windows**: Press the Windows key + R, type `cmd`, and press Enter
  - **For Mac**: Press Command + Space, type `terminal`, and press Enter 

2. In the black window that appears, type exactly and press Enter:
   ```
   java --version
   ```

3. What you should see:
  - ✅ If you see "Java version 17", you're ready to go!
  - ❌ If you see "command not found" or a number that is not 17, visit [Java's download page](https://www.oracle.com/java/technologies/downloads/#java17) to install Java 17

## **Step 2: Install BakeBuddy**

1. Download BakeBuddy:
  - Click [this link](https://github.com/AY2425S1-CS2103T-T11-1/tp/releases) to download the latest BakeBuddy
  - Look for the file named `BakeBuddy.jar`
  - Click on it to download
2. Create a home for BakeBuddy:
  - Create a new folder on your computer named `BakeBuddy`
  - Move the downloaded `BakeBuddy.jar` file into this folder
3. Start BakeBuddy:
  - Open your terminal (like in Step 1) and navigate to the BakeBuddy folder
  - Type `cd ` (with a space after cd) 
  - Drag your BakeBuddy folder into the terminal window (this fills in the location automatically!)
  - Press Enter
  - Type and press Enter:
    ```
    java -jar BakeBuddy.jar
    ```
   You should see the BakeBuddy window appear as follows in the next page!

<div style="page-break-after: always;"></div>

## **Step 3: Try Your First Commands**

Now that BakeBuddy is running, let's add your first items. In the BakeBuddy window, you'll see a space to type commands at the top.

1. First, check out the [List of Commands](#list-of-commands) section to understand what BakeBuddy can do
2. Review the [FAQ](#faq) and [Glossary](#glossary-and-command-formats) to clear any initial doubts
3. For detailed command information, refer to the [Features](#features) section which contains comprehensive documentation for each command

![screenshot](images/Screenshot-5.png)

<div style="page-break-after: always;"></div>

# **Glossary and Command Formats**

## **Contact Types**

<div markdown="block" class="alert alert-info">
- **Contact**: Any person or entity stored in BakeBuddy's database, including customers, suppliers, and other business contacts
- **Customer**: A person or business who purchases pastries from your bakery
- **Supplier**: A person or business who provides ingredients to your bakery
</div>

## **Order-Related Terms**

<div markdown="block" class="alert alert-info">
- **Customer Order**: A record of pastries ordered by a customer, including status and other details
- **Supply Order**: A record of ingredients ordered from a supplier, including status and other details
- **Order Status**:
  - *Pending*: Order that has not been fulfilled yet
  - *Done*: Order that has been completed and delivered/received
</div>


## **Product Management**

<div markdown="block" class="alert alert-info">
- **Pastry Catalogue**: Complete list of pastries your bakery offers, including prices and required ingredients
- **Ingredient Catalogue**: Complete list of ingredients used in your bakery, including costs 
- **Product ID**: Unique identifier assigned to each pastry or ingredient in the catalogues
- **Cost**:
  - For ingredients: Purchase price from supplier
  - For pastries: Selling price to customer
</div>

<div style="page-break-after: always;"></div>

## **UI Elements**

<div markdown="block" class="alert alert-info">
- **Index**: The number shown beside each item in a list, used for referencing items in commands
</div>

## **Command Format**

<div markdown="block" class="alert alert-info">

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `addContact n/NAME`, `NAME` is a parameter which can be used as `addContact n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…` after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

<div style="page-break-after: always;"></div>

## **Contact Management**

<div markdown="block" class="alert alert-info">
- **Filter**: Process of showing only contacts with specific tags
- **Tag**: Label that can be applied to contacts for easy categorization (e.g., "VIP", "Wholesale", "Regular")
- **Remark**: Additional notes about a contact 
</div>

## **Order Processing**

<div markdown="block" class="alert alert-info">
- **Mark/Unmark**: Commands to toggle the completion status of an order
- **Clear**: Command to remove all data from the system
</div>

## **Parameter Reference**

<div markdown="block" class="alert alert-info">

* `INDEX`: Positive integer (1, 2, 3, ...)
* `NAME`: Name of person/item (Only alphanumerical characters)
* `PHONE_NUMBER`: Phone number (Unique in the contacts list and be between 3 and 15 digits)
* `EMAIL`: Email address 
* `ADDRESS`: Physical address
* `INFORMATION`: Additional customer details (Only alphanumerical characters)
* `INGREDIENTS_SUPPLIED`: List of ingredients' name (comma-separated)
* `COST`: Numeric value
* `PASTRYID`: Product identification numbers from the pastry catalogue
* `INGREDIENTID`: Product identification numbers from the ingredient catalogue
* `TAG`: Category or label (Only a single word can be used as a tag)
* `REMARK`: Additional comments

**Format Notes:**
* `[parameter]`: Optional parameter
* `parameter...`: Parameter can be repeated
* Parameters can be entered in any order
* Commands and parameter prefixes are case-sensitive

</div>

<div style="page-break-after: always;"></div>

# **List of Commands**

## **General Contact Management**

| Command                                   | Format                                                                      | Example                                                              |
|-------------------------------------------|-----------------------------------------------------------------------------|----------------------------------------------------------------------|
| [Add Contact](#add-contact-command)       | `addContact n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [t/TAG]...`         | `addContact n/Tim p/81234567 e/emily@example.com a/456 Cupcake Road` |
| [Delete Contact](#delete-contact-command) | `deleteContact INDEX`                                                       | `deleteContact 1`                                                    |
| [Edit Contact](#edit-contact-command)     | `editContact INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]` | `editContact 1 p/91150335`                                           |
| [Filter Contact](#filter-contact-command) | `filterContact t/TAG [MORE_TAGS]...`                                        | `filterContact t/Customer`                                           |
| [Find](#find-contact-command)             | `find KEYWORD [MORE_KEYWORDS]...`                                           | `find Charlotte Bernice`                                             |
| [List](#list-command)                     | `list`                                                                      | `list`                                                               |
| [Remark](#remark-command)                 | `remark INDEX r/REMARK`                                                     | `remark 1 r/Regular customer`                                        |

## **Customer and Supplier Contact Management**

| Command | Format                                                                                        | Example |
| --- |-----------------------------------------------------------------------------------------------| --- |
| [Add Customer](#add-customer-command) | `addCustomer n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [i/INFORMATION] [t/TAG]...`          | `addCustomer n/Tim p/81234567 i/Allergic to peanuts` |
| [Add Supplier](#add-supplier-command) | `addSupplier n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [s/INGREDIENTS_SUPPLIED] [t/TAG]...` | `addSupplier n/tim p/81234567 s/salt, chocolate` |

## **Customer Order Management**

| Command | Format                                                                                | Example |
| --- |---------------------------------------------------------------------------------------| --- |
| [Add Customer Order](#add-customer-order-command) | `addCustomerOrder [n/NAME] p/PHONE_NUMBER o/PASTRYID [MORE_PASTRYIDSs]... [r/REMARK]` | `addCustomerOrder n/John p/98765432 o/1 2 3 r/Delivery at 6pm` |
| [Delete Customer Order](#delete-customer-order-command) | `deleteCustomerOrder INDEX`                                                           | `deleteCustomerOrder 2` |
| [Mark Customer Order](#mark-customer-order-command) | `markCustomerOrder INDEX`                                                             | `markCustomerOrder 1` |
| [Unmark Customer Order](#unmark-customer-order-command) | `unmarkCustomerOrder INDEX`                                                           | `unmarkCustomerOrder 1` |

## **Supply Order Management**

| Command | Format                                                                           | Example |
| --- |----------------------------------------------------------------------------------| --- |
| [Add Supply Order](#add-supply-order-command) | `addSupplyOrder [n/NAME] p/PHONE_NUMBER o/INGREDIENTID [MORE_INGREDIENTIDs]... [r/REMARK]` | `addSupplyOrder n/John p/98765432 o/1 2 3` |
| [Delete Supply Order](#delete-supply-order-command) | `deleteSupplyOrder INDEX`                                                        | `deleteSupplyOrder 1` |
| [Mark Supply Order](#mark-supply-order-command) | `markSupplyOrder INDEX`                                                          | `markSupplyOrder 1` |
| [Unmark Supply Order](#unmark-supply-order-command) | `unmarkSupplyOrder INDEX`                                                        | `unmarkSupplyOrder 1` |

<div style="page-break-after: always;"></div>

## **Ingredient Catalogue Management**

| Command                                                         | Format | Example |
|-----------------------------------------------------------------| --- | --- |
| [Add Ingredient](#add-ingredient-command)                       | `addIngredient NAME COST` | `addIngredient Syrup 3.50` |
| [Remove Ingredient](#remove-ingredient-command)                 | `removeIngredient NAME` | `removeIngredient Flour` |
| [View Ingredient Catalogue](#view-ingredient-catalogue-command) | `viewIngredientCatalogue` | `viewIngredientCatalogue` |

## **Pastry Catalogue Management**

| Command                                                 | Format                                                 | Example |
|---------------------------------------------------------|--------------------------------------------------------| --- |
| [Add Pastry](#add-pastry-command)                       | `addPastry NAME COST INGREDIENT [MORE_INGREDIENTS]...` | `addPastry Croissant 3.50 Flour Cream Sugar` |
| [Remove Pastry](#remove-pastry-command)                 | `removePastry NAME`                                    | `removePastry Croissant` |
| [View Pastry Catalogue](#view-pastry-catalogue-command) | `viewPastryCatalogue`                                  | `viewPastryCatalogue` |

## **General Commands**

| Command                     | Format | Example |
|-----------------------------| --- | --- |
| [Help](#help-command)       | `help` | `help` |
| [Clear](#clear-all-command) | `clear` | `clear` |
| [Exit](#exit-command)       | `exit` | `exit` |

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

# **FAQ**

## **Q1: How can I easily find the correct Product IDs when adding orders?**
**A:** When adding orders, you can use these tips to find the correct Product IDs:

For Customer Orders:
1. First run `viewPastryCatalogue` to see all available pastries and their IDs
2. Keep the catalogue visible while typing your `addCustomerOrder` command
3. Reference the IDs from the catalogue for the `o/PASTRYID` parameter
4. Example workflow:
   ```
   > viewPastryCatalogue
   [catalogue appears showing pastries and their IDs]
   > addCustomerOrder n/John p/98765432 o/1 2 3
   ```

For Supply Orders:
1. First run `viewIngredientCatalogue` to see all ingredients and their IDs
2. Keep the catalogue visible while typing your `addSupplyOrder` command
3. Reference the IDs from the catalogue for the `o/INGREDIENTID` parameter
4. Example workflow:
   ```
   > viewIngredientCatalogue
   [catalogue appears showing ingredients and their IDs]
   > addSupplyOrder n/Tim p/81234567 o/1 2
   ```

## **Q2: How do I view all customers/suppliers easily?**
**A:** We can use the filter command to view all customers/suppliers.
   ```
   filterContact t/Customer
   ```

<div style="page-break-after: always;"></div>

## **Q3: How do I add collection dates and large quantities to orders?**
**A:** You can include collection dates and quantities in the order's remark field:
```
addCustomerOrder n/John p/91234567 o/1 2 r/Collection: 25/12/2024 6pm, Qty: 2x 
Croissant, 3x Muffin
```

Best practices for remarks:
- Start with collection date/time
- List quantities clearly
- Add any special instructions last
- Use consistent format for dates

## **Q4: Can I add multiple products with the same ID in one order?**
**A:** Yes, you can repeat Product IDs in your order if the customer wants multiple of the same item. For example:
```
addCustomerOrder n/John p/98765432 o/1 1 1
```
This would add three units of ingredient ID 1 to the order.

## **Q5: How are orders sorted in the list?**
**A:** Orders are automatically sorted in this priority:
1. Pending orders (shown first)
- Sorted by date added (newest first)
2. Completed orders (shown after pending)
- Sorted by completion date (newest first)

This sorting helps you:
- Focus on pending orders that need attention
- Keep track of recent completions
- Maintain an organized workflow

<div style="page-break-after: always;"></div>

## **Q6: How do I tag contacts with multiple tags?**
**A:** You can add multiple tags to contacts in several ways:
1. When creating a new contact:
   ```
   addContact n/John p/91234567 t/VIP t/Regular
   ```
2. When editing an existing contact:
   ```
   editContact 1 t/Wholesale t/Priority
   ```

Tags are useful for:
- Marking special status (e.g., t/VIP, t/Priority)
- Noting preferences (e.g., t/NutFree, t/Halal)

## **Q7: Are the commands case-sensitive?**
**A:** Yes, commands and parameter prefixes are case-sensitive, but parameter values are not. For example:
- ✅ `addContact n/John p/12345678` (correct)
- ❌ `AddContact n/John p/12345678` (wrong - command must be lowercase)
- ❌ `addcontact N/John P/12345678` (wrong - prefixes must be lowercase)
- ✅ `addContact n/JOHN p/12345678` (correct - name can be any case)

## **Q8: Why is my table not aligned properly in the UI?**
**A:** The table alignment may appear off if:
1. The window is too narrow - try widening your application window
2. There are very long entries - try using shorter entries or abbreviations

To ensure optimal viewing:
- Keep the window width as wide as possible
- Use reasonable length entries

<div style="page-break-after: always;"></div>

## **Q9: Why are some UI elements hidden when I minimize the screen?**
**A:** BakeBuddy is optimized for a minimum window size of 800x600 pixels. When the window is smaller:
1. Some UI elements may be hidden to prevent overlap
2. Use the scroll bars to navigate hidden content
3. Resize the window larger to see all elements simultaneously

For the best experience:
- Keep the window at recommended size (1024x768 or larger)
- Use full screen mode for optimal viewing
- Avoid resizing to minimum dimensions

## **Q10: Are there limits to email and phone number length?**
**A:** There are several constraints to email and phone number.

- Phone numbers:
  - Minimum: 3 digits
  - Maximum: 15 digits
  - Must be unique in the contact list
- Email addresses:
  - Maximum: No limit. Not advisable to input long phone number as it may disrupt the UI display.
  - Must follow standard email format (xxx@xxx.xxx)
  - Special characters allowed: . _ - @


## **Q11: Can I add two contacts with the same phone number?**
**A:** No, phone number is the unique identification number in the contact book. When a different name is attached to an 
order with a phone number that is already in the address book, the person's name in the address book will be treated as 
the entity who places the order.

<div style="page-break-after: always;"></div>

## **Q12: Can I place a customer order from a supplier or vice versa?**
**A:** Yes, you can. You can feel free to add customer orders from a supplier (or less frequently, place supply orders 
to a customer). They will be distinguished by the order type under the person's contact card. However, the additional 
information of suppliers and customers will remain the same - ingredients supplied and information respectively. For 
additional remarks, please use the remark feature.

## **Q13: What is the point of having an addCustomer and addSupplier command?**
**A:** Customers and suppliers have unique tags. We can record additional information such as dietary preference for 
customers and record the ingredients supplied for suppliers. A general contact does not have this feature.

## **Q14: Why doesn't the name I provide in addCustomerOrder/addSupplyOrder match what's displayed?**
**A:** When adding a order, the system prioritizes the contact information already in your contact list:
- If the phone number exists in your contacts:
  - The order will be linked to the existing contact name, ignoring any new name provided
  - This ensures consistency and prevents duplicate contacts with the same phone number
- If the phone number is new:
  - A new contact will be created with the provided name and phone number

For example, to handle orders being collected by someone else, use the remarks field to specify the collector:
```
addCustomerOrder n/John p/91234567 o/1 2 r/Collector: Mary (sister), Collection: 25/12/2024 6pm
```
This system helps maintain clean contact records while still allowing flexibility in order collection arrangements.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

# **Features**

## **Add Contact Command**
Adds a new contact to the bakery’s database. This will be useful for other important contacts such as the delivery men and bakery assistants.

```bash
addContact n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [t/TAG]...
```
- **Parameters:**
  - `n/NAME`: The person's name. This has to be alphanumerical.
  - `p/PHONE_NUMBER`: The person's phone number. This has to be unique in the contacts list and have more than 3 digits.
  - `e/EMAIL`: (Optional) The person's email address.
  - `a/ADDRESS`: (Optional) The person's address.
  - `t/TAG`: (Optional) Tags for additional person information. Only a single word can be used as a tag. Can have multiple prefixes.

**Example:**
```bash
addContact n/Tim p/81234567 e/emily@example.com a/456 Cupcake Road, Block 123, 03-04 
```

![screenshot](images/Screenshot-2.png)

**Note:** Phone number must be unique for each user (i.e. if you run the above command, but the number "81234567" already exists in the address book, the add contact command will throw an error)
![screenshot](images/Screenshot-6.png)

<div style="page-break-after: always;"></div>

## **Add Customer Command**
Adds a new customer to the bakery’s customer database.

```bash
addCustomer n/NAME p/PHONE_NUMBER [i/INFORMATION] [e/EMAIL] [a/ADDRESS] 
[t/TAG]...
```
- **Parameters:**
  - `n/NAME`: The customer's name. This has to be alphanumerical.
  - `p/PHONE_NUMBER`: The customer's phone number. This has to be unique in the contacts list and have more than 3 digits.
  - `i/INFORMATION`: (Optional) Additional information of the customer such as dietary preference (Alphanumerical).
  - `e/EMAIL`: (Optional) The customer's email address.
  - `a/ADDRESS`: (Optional) The customer's address.
  - `t/TAG`: (Optional) Tags for additional customer information. Only a single word can be used as a tag. Can have multiple prefixes.

**Example:**
```bash
addCustomer n/Tim p/81234567 e/emily@example.com a/456 Cupcake Road, Block 123, 
03-04 i/Allergic to peanuts
```
![screenshot](images/Screenshot-3.png)

<div style="page-break-after: always;"></div>

## **Add Customer Order Command**
Adds a customer order by providing the customer's name, phone number and the pastry IDs from the pastry catalogue.
New customer will be added with the name if phone number not found in contacts.

```bash
addCustomerOrder [n/NAME] p/PHONE_NUMBER o/PASTRYID [MORE_PASTRYID]... 
[r/REMARK]
```
- **Parameters:**
  - `n/NAME`: (Optional) The customer's name. This has to be alphanumerical.
  - `p/PHONE_NUMBER`: The customer's phone number. This has to be unique in the contacts list and have more than 3 digits.
  - `o/PASTRYID`: One or more pastry IDs for the items being ordered.
  - `r/REMARK`: (Optional) Information about the customer order.
  
A resulting customer order will be added to Customer Order list, tagged to the date which the order enters our system.

**Example:**
```bash
addCustomerOrder n/John Doe p/98765432 o/1 2 3 r/Delivery at 6pm 
```
![screenshot](images/Screenshot-4.png)

<div style="page-break-after: always;"></div>

## **Add Ingredient Command**
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

<div style="page-break-after: always;"></div>

## **Add Pastry Command**
Adds a new pastry to the bakery's pastry catalogue.

```bash
addPastry NAME COST INGREDIENT [MORE_INGREDIENTS]...
```
- **Parameters:**
    - `NAME`: The name of the pastry.
    - `COST`: The cost of the pastry.
    - `INGREDIENT`: One or more ingredient names.

**Example:**
```bash
addPastry Croissant 3.50 Flour Cream Sugar
```
![Add Pastry.png](images%2FAdd%20Pastry.png)

The Add Pastry Command allows bakery owners to add a new pastry item to their pastry catalogue, specifying the name, 
cost and ingredients for each pastry. This command accepts the pastry's name, a numeric value for how much the 
bakery owner will sell it for, and a list of ingredients required to make it. 

For example, typing **addPastry Croissant 3.50 Flour Cream Sugar** would add a pastry named "Croissant" priced at $3.50, 
with "Flour," "Cream," and "Sugar" listed as its ingredients. By using this command, bakery owners can easily track of 
all their pastries and ingredient needs, making it more convenient for bakery owners to manage their list of pastries.

<div style="page-break-after: always;"></div>

## **Add Supplier Command**
Adds a new supplier to the bakery’s supplier database.

```bash
addSupplier n/NAME p/PHONE_NUMBER [s/INGREDIENTS_SUPPLIED] [e/EMAIL] [a/ADDRESS] 
[t/TAG]...
```
- **Parameters:**
    - `n/NAME`: The supplier's name. This has to be alphanumerical.
    - `p/PHONE_NUMBER`: The supplier's phone number. This has to be unique in the contacts list and have more than 3 digits.
    - `s/INGREDIENTS_SUPPLIED`: (Optional) List of ingredients supplied, comma separated. This must exist in the catalogue.
    - `e/EMAIL`: (Optional) The supplier's email address.
    - `a/ADDRESS`: (Optional) The supplier's address.
    - `t/TAG`: (Optional) Tags for additional supplier information. Only a single word can be used as a tag. Can have multiple prefixes.

**Example:**
```bash
addSupplier n/tim p/81234567 e/emily@example.com a/456 Cupcake Road, Block 123, 
03-04 s/chocolate
```
![Add Supplier.png](images%2FAdd%20Supplier.png)

<div style="page-break-after: always;"></div>

## **Add Supply Order Command**
Adds a supplier order by providing the supplier's name, phone number and the ingredient IDs from the ingredient catalogue.

```bash
addSupplyOrder [n/NAME] p/PHONE_NUMBER o/INGREDIENTID [MORE_INGREDIENTID]... 
[r/REMARK]
```
- **Parameters:**
    - `n/NAME`: (Optional) The supplier's name. This has to be alphanumerical.
    - `p/PHONE_NUMBER`: The phone number of the supplier. This has to be unique in the contacts list and have more than 3 digits.
    - `o/INGREDIENTID`: One or more ingredient IDs for the items being supplied.
    - `r/REMARK`: (Optional) Information about the customer order.

A resulting supply order will be added to Supply Order list, tagged to the date which the order enters our system.

**Example:**
```bash
addSupplyOrder n/John Doe p/98765432 o/1 2 3
```
![Add Supply Order.png](images%2FAdd%20Supply%20Order.png)
<div style="page-break-after: always;"></div>

## **Clear All Command**
Removes all entries from the bakery’s address book, including customers, suppliers, and any related information.

```bash
clear
```

## **Delete Contact Command**
Deletes the contact details of specified person from the address book.

```bash
deleteContact INDEX
```
- **Parameters:**
    - `INDEX`: The index number shown in the displayed Contact List. Must be a positive integer.

**Example:**
```bash
deleteContact 1
```
![screenshot](images/ss deletecontact.png)

<div style="page-break-after: always;"></div>

## **Delete Customer Order Command**
Deletes a customer order from the customer order list at the specified index.

```bash
deleteCustomerOrder INDEX
```
- **Parameters:**
    - `INDEX`: The index of the order to delete, as displayed in the Customer Order List. Must be a positive integer.

**Example:**
```bash
deleteCustomerOrder 2
```
![screenshot](images/ss deletecustomerorder.png)

<div style="page-break-after: always;"></div>

## **Delete Supply Order Command**
Deletes a supply order from the supply order list at the specified index.

```bash
deleteSupplyOrder INDEX
```
- **Parameters:**
    - `INDEX`: The index of the order to delete, as displayed in the Supply Order List. Must be a positive integer.

**Example:**
```bash
deleteSupplyOrder 1
```
![screenshot](images/ss deletesupplyorder.png)

<div style="page-break-after: always;"></div>

## **Edit Contact Command**
Update the contact details of an existing contact in the address book, including persons, customers, and suppliers.

```bash
editContact INDEX PARAMETER [MORE_PARAMETERS]...
```
- **Parameters:**
    - `n/NAME`: (optional) The person's name. This has to be alphanumerical.
    - `p/PHONE_NUMBER`: (optional) This has to be unique in the contacts list and have more than 3 digits.
    - `e/EMAIL`: (Optional) The person's email address.
    - `a/ADDRESS`: (Optional) The person's address.
    - `i/INFORMATION`: (Optional, for customers only) Additional information of the customer such as dietary preference.
    - `s/INGREDIENTS SUPPLIED`: (Optional, for suppliers only) Ingredients supplied of a supplier.
    - `t/TAG`: (Optional) Tags for additional customer information. Only a single word can be used as a tag. Can have multiple prefixes.

**Example:**
```bash
editContact 1 p/91150335
```
![screenshot](images/ss editcontact.png)    

<div style="page-break-after: always;"></div>

## **Exit Command**
Exits the program.

```bash
exit
```
## **Filter Contact Command**
Filter and list contacts in the address book based on specified tags.

```bash
filterContact t/TAG [MORE_TAGS]...
```
- **Parameters:**
   - `t/TAG`: A tag to filter contacts by. Multiple tags can be specified by repeating the "t/" prefix with different tag values.

**Example:**
```bash
filterContact t/Customer
```
![screenshot](images/ss filter.png)

<div style="page-break-after: always;"></div>

## **Find Contact Command**
Finds persons whose names contain any of the given keywords.

```bash
find KEYWORD [MORE_KEYWORDS]...
```
- **Parameters:**
    - `KEYWORD`: The keyword to search for.
    - `[MORE_KEYWORDS]`: Additional keywords to search for.
* Use the list command to return to the full contact `list`
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

<div style="page-break-after: always;"></div>

## **List Command**
Lists all contacts in the bakery's address book.

***Example:***
```bash
list
````
![listcommandui.png](images/listcommandui.png)

## **Help Command**
Displays a link to our user guide that provides detailed information on how to use the application.

***Example:***
```bash
help
````

<div style="page-break-after: always;"></div>

## **Mark Customer Order Command**

**Note:** Orders are arranged from top (pending orders) to bottom (completed orders), hence marking an order will cause it to go the bottom of the customer order list.
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

<div style="page-break-after: always;"></div>

## **Mark Supply Order Command**

**Note:** Orders are arranged from top (pending orders) to bottom (completed orders), hence marking an order will cause it to go the bottom of the supply order list.
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

<div style="page-break-after: always;"></div>

## **Remark Command**
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

<div style="page-break-after: always;"></div>

## **Remove Ingredient Command**
Removes an existing ingredient from the bakery's ingredient catalogue.

```bash
removeIngredient NAME
```
- **Parameters:**
    - `NAME`: The name of the ingredient to remove. This is a case-insensitive match for an existing ingredient in the ingredient catalogue.

**Example:**
```bash
removeIngredient Flour
```
![removeIngredient.png](images%2FremoveIngredient.png)
The Remove Ingredient Command enables bakery owners to delete an ingredient from their catalogue. This is useful for removing ingredients no longer used or mistakenly added. After execution, the system confirms the removal by displaying a success message.

<div style="page-break-after: always;"></div>

## **Remove Pastry Command**
Removes an existing pastry from the bakery's pastry catalogue.

```bash
removePastry NAME
```
- **Parameters:**
    - `NAME`: The name of the pastry to remove. This is a case-insensitive match for an existing pastry in the pastry catalogue.

**Example:**
```bash
removePastry Croissant
```
![removePastry.png](images%2FremovePastry.png)
The Remove Pastry Command allows the bakery to maintain an up-to-date catalogue by removing pastries that are discontinued or incorrectly added. On successful removal, the system displays a confirmation message.

<div style="page-break-after: always;"></div>

## **Unmark Customer Order Command**
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

<div style="page-break-after: always;"></div>

## **Unmark Supply Order Command**
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

<div style="page-break-after: always;"></div>

## **View Ingredient Catalogue Command**
Displays the current list of ingredients available in the bakery’s ingredient catalogue.

```bash
viewIngredientCatalogue
```

**Example:**
```bash
viewIngredientCatalogue
```
![viewIngredientCatalogue.png](images%2FviewIngredientCatalogue.png)
The View Ingredient Catalogue Command provides a detailed list of all ingredients, including their IDs, names, and costs. This command ensures that bakery owners have a complete overview of the ingredients, helping in efficient decision-making.

For instance, typing viewIngredientCatalogue displays the full catalogue for ingredient, enabling the bakery to verify all available ingredients in the catalogue.

<div style="page-break-after: always;"></div>

## **View Pastry Catalogue Command**
Displays the current list of pastries available in the bakery’s pastry catalogue.

```bash
viewPastryCatalogue
```

**Example:**
```bash
viewPastryCatalogue
```
![viewPastryCatalogue.png](images%2FviewPastryCatalogue.png)
The View Pastry Catalogue Command provides a comprehensive view of all pastries in the bakery in one-go, including their IDs, names, price and ingredients with the costs. This command is vital for keeping track of the pastries offered to customers and ingredients needed to produce them. 

For example, entering viewPastryCatalogue shows the complete list of pastries, ensuring that bakery owners are aware of the available pastries.
