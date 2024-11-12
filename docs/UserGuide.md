---
layout: page
title: User Guide
---

PROperty is a **desktop app for property agents managing clients' contact details and their property listings,
optimized for use via a Command Line Interface** [(CLI)](#technical-terms) while still having the benefits of a Graphical User Interface [(GUI)](#technical-terms).
If you can type fast, PROperty can get your client and property management tasks done faster than traditional GUI apps.

PROperty is useful for property agents because it saves their time by allowing easy tracking of contacts,
and easily filtering them according to tailor-made categories relevant to property agents in Singapore. It is much
simpler to use while being even more functional than alternatives on the market.

<div class="page-break"></div>

## Table of Contents
* [User Guide Essentials](#user-guide-essentials)
    * [Icon Legend](#icon-legend)
    * [Locating Information Quickly](#locating-information-quickly)
* [Quick Start](#quick-start)
  * [Step 1: Check if Your Computer is Ready](#step-1-check-if-your-computer-is-ready)
  * [Step 2: Install PROperty](#step-2-install-property)
  * [Step 3: Try Your First Commands](#step-3-try-your-first-commands)
* [Features](#features)
    * [Adding a client: `add`](#adding-a-client-add)
    * [Editing a client: `edit`](#editing-a-client--edit)    
    * [Deleting a client: `delete`](#deleting-a-client--delete)
    * [Listing all clients: `list`](#listing-all-clients--list)
    * [Sorting all clients: `sort`](#sorting-all-clients--sort)
    * [Showing property listings of a client: `show`](#showing-property-listings-of-a-client--show)
    * [Locating clients by name: `find`](#locating-clients-by-name-find)
    * [Locating clients by tag: `findtag`](#locating-clients-by-tag-findtag)
    * [Clearing all entries: `clear`](#clearing-all-entries--clear)
    * [Managing Remarks: `remark`](#managing-remarks--remark)
    * [Adding a property listing: `listing add`](#adding-a-property-listing--listing-add)
    * [Deleting a property listing: `listing delete`](#deleting-a-property-listing--listing-delete)
    * [Exporting your contacts: `export`](#exporting-your-contacts--export)
    * [Exiting the program: `exit`](#exiting-the-program--exit)
    * [Open help menu: `help`](#open-help-menu-help)
* [Saving the data](#saving-the-data)
* [Editing the data file](#editing-the-data-file)
* [FAQ](#faq)
* [Known Issues](#known-issues)
* [Command Summary](#command-summary)
* [Tag Table](#tag-table)
* [Glossary](#glossary)
    * [Technical Terms](#technical-terms)
    * [Property Tags](#property-tags)
    * [Client Tags](#client-tags)

--------------------------------------------------------------------------------------------------------------------
<div class="page-break"></div>

## User Guide Essentials

### Icon Legend

This section helps you understand the icons you’ll encounter in the User Guide

| **Icons**               | **Meaning**                                      |
|-------------------------|--------------------------------------------------|
| :information_source:    | Key details to be aware of.                      |
| :bulb:                  | Helpful tips for better use.                     |
| :exclamation:           | Crucial information! Please read carefully.      | 



### Locating Information Quickly
This section outlines the topics covered in each part of the guide, helping you quickly find the information you need. Feel free to jump to the sections that are most relevant to you.

1. [Quick start](#quick-start) – Learn how to set up PROperty in a few simple steps. 
2. [Features](#features) – Understand the full range of capabilities PROperty offers. 
3. [FAQ](#faq) – Find answers to common questions from other users. 
4. [Command Summary](#command-summary) – A quick reference to all commands available for easy access.
5. [Tag Table](#tag-table) – View a comprehensive list of tags and their descriptions to enhance your understanding of PROperty’s categorisation.
6. [Glossary](#glossary) – Get familiar with key terms and definitions used throughout the guide.
7. [_Back to Top_](#table-of-contents) – Easily return to the table of contents with this shortcut.

[_Back to Top_](#table-of-contents)


--------------------------------------------------------------------------------------------------------------------
<div class="page-break"></div>

## Quick Start

### Step 1: Check if Your Computer is Ready
First, we need to make sure your computer has [Java](#technical-terms) 17 installed. Here's how to check:


1. Open your computer's terminal:
   - **For Windows**: Press the Windows key + R, type `cmd`, and press Enter.
   - **For Mac**: Press Command + Space, type `terminal`, and press Enter.
2. In the black terminal window that appears, type the following command:
   ```
   java --version
   ```
   and press Enter.
3. What you should see:
   - ✅ If you see "java 17" or "openjdk 17" (or any number above 17), you're ready to go!
   - ❌ If you see "command not found" or a java version below 17, visit [Java's download page](https://www.oracle.com/java/technologies/downloads/#java17) to install Java 17

[_Back to Top_](#table-of-contents)


### Step 2: Install PROperty


1. Download PROperty:
   - Click [here](https://github.com/AY2425S1-CS2103T-F15-3/tp/releases) to download the latest PROperty.
   - Look for a [JAR](#technical-terms) file named `PROperty.jar` and click on it to download.
2. Create a home folder for PROperty:
   - Create a new folder on your computer named `PROperty`.
   - Copy the downloaded `PROperty.jar` file into this folder.
3. Start PROperty:
   - Open your computer's terminal in the folder where PROperty is by either:
     - **For Windows:** <kbd>Shift</kbd> + <kbd>Right-Click</kbd> in the folder where PROperty is and clicking on "Open PowerShell Window Here".
     - **For Mac:** <kbd>Control</kbd> + <kbd>Left-Click</kbd> on the folder where PROperty is and clicking on "New Terminal at Folder".
   - Type `java -jar PROperty.jar` into your computer's terminal and press "Enter".
   
     ![Annotated_Ui](images/user-guide-images/Annotated_Ui.png)
   
   - The image above showcases the various components of PROperty.

[_Back to Top_](#table-of-contents)


### Step 3: Try Your First Commands

Now that PROperty is running, let's try using the different commands available.
All you have to do is to type the command in the command box and press Enter to execute it. 
Some example commands you can try are:

* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a client named `John Doe` to PROperty.


* `edit 1 n/Mary Jane p/12345678 e/maryj@example.com a/Mary street, block 321, #02-02` : Edits the client with index 1 in PROperty.


* `listing add 2 t/HDB a/Adam street, block 456, #03-03` : Adds a listing for the client with index 2 in PROperty.

<div markdown="block" class="alert alert-primary">
:bulb: Recommended Window Size

For the best experience, use PROperty at full screen on your PC.
</div>

Refer to the [Features](#features) below for details of each command.

[_Back to Top_](#table-of-contents)


--------------------------------------------------------------------------------------------------------------------
<div class="page-break"></div>

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about PROperty's command format:**<br>

* PROperty currently only supports English.

* Parameters in `UPPER_CASE` are the parameters to be supplied by you.<br>
  e.g. for `add n/NAME`, `NAME` is a parameter which you input, such as `add n/John Doe`.

* Parameters in square brackets are optional.<br>
  e.g. for `n/NAME [t/TAG]`, you can either input the parameters `n/John Doe t/seller` or `n/John Doe`.

* Parameters proceeded by `…`​ can be input any number of times.<br>
  e.g. `[t/TAG]…​` can be either have no input, or be input as `t/seller`, or `t/seller t/landlord` etc.

* Parameters can be input in any order.<br>
  e.g. you can either input `n/NAME p/PHONE_NUMBER` or `p/PHONE_NUMBER n/NAME`. Both are acceptable formats.

* For commands that do not require parameters (e.g. `help`, `list`, `exit` etc), any additional parameters that are input will be ignored.<br>
  e.g. if you input `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, please exercise caution when copying and pasting commands that span multiple lines, as space characters adjacent to line breaks may be omitted when pasted into PROperty's CLI.  

</div>

[_Back to Top_](#table-of-contents)


<div style="page-break-after: always;"></div>

### Adding a client: `add`

Adds a client to PROperty.

Format: `add n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [t/client_TAG] [r/REMARKS]…​`

- `NAME` and `PHONE_NUMBER` fields must be provided.
  - `NAME` can only contain **alphanumeric characters and spaces**.
-  Client tags are added in a case-insensitive manner. e.g `t/buyer` or `t/BUYER` will both add the `Buyer` tag.
-  Refer to the [Tag Table](#tag-table) for a complete list of client tags.
- A client is considered the "same" as another client based on matching values in: `NAME` and `PHONE_NUMBER`.
  - Note that values are **case-sensitive**.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A client can have any number of tags (including no tags)
</div>

Examples:

1. `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 r/looking for HDB` adds a client named `John Doe` with a phone number of `98765432`, an email of `johnd@example.com`, an address of `John street, block 123, #01-01` and a remark of `looking for HDB`.


2. `add n/Betsy Crowe t/landlord e/betsycrowe@example.com a/Flatbush Avenue, block 81, #02-02 p/1234567` adds a client named `Betsy Crowe` with a tag of `Landlord`, and email of `betsycrowe@example.com`, an address of `Flatbush Avenue, block 81, #02-02` and a phone number of `1234567`.


Visual example of correct output (Example 1):

![AddCommandShowcase.png](images/user-guide-images/AddCommandShowcase.png)

[_Back to Top_](#table-of-contents)


### Editing a client : `edit`

Edits an existing client in the PROperty.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/client_TAG] [dt/client_TAG] [r/REMARK]…​`

* Edits the client at the specified `INDEX`. The index refers to the index number shown in the displayed client list. The index **must be a positive integer** 1, 2, 3, …​
* `client_TAG` can be `Buyer`, `Seller`, `Landlord`, `Tenant`
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the tags specified using `t/` will be added to the contact (cumulatively).
* Tags can also be removed using the delete tag `dt/` prefix, followed by the tag name.
  * The overall change of all tags is computed together. However, any `dt/` arguments will **take priority** over any `t/` options, so `t/seller dt/seller` will **delete the `seller` tag**.
  * If you specify a tag that does not exist, it will do nothing (e.g `dt/missing_tag`).
* You can remove all the client’s tags by typing `t/` without
    specifying any tags after it.
  * This option **can not be used** with any other tag options (i.e `t/seller dt/buyer`).
* `NAME` can only contain **alphanumeric characters and spaces**.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:** 
Use `t/` to add new tags and `dt/` to delete specific tags from a client. 
</div>

Examples:

1. `edit 1 p/87438807 e/alexyeoh@example.com` edits the phone number and email address of the `1st` client to be `87438807` and `alexyeoh@example.com` respectively.


2. `edit 2 n/Betsy Crower t/` edits the name of the client at index `2` to be `Betsy Crower` and clears all existing tags.


3. `edit 2 t/condo` edits the tag of the client at index `2` to be `condo`.

Visual example of correct output (Example 1):

![EditCommandShowcase.png](images/user-guide-images/EditCommandShowcase.png)

[_Back to Top_](#table-of-contents)


### Deleting a client : `delete`

Deletes the specified client from the PROperty.

Format: `delete INDEX`

* Deletes the client at the specified `INDEX`.
* The index refers to the index number shown in the displayed client list.
* The index **must be a positive integer** 1, 2, 3, …​

<div markdown="span" class="alert alert-primary">:bulb: **Tip:** 
Use the `list` or `find` command to determine the `INDEX` of the client you want to delete. 
</div>

Examples:

1. `list` followed by `delete 2` deletes the client at index `2` listed in PROperty.


2. `find Betsy` followed by `delete 1` deletes the client at index `1` in the **results of the `find` command**.

Visual example of correct output (Example 1):

![DeleteCommandShowcase.png](images/user-guide-images/DeleteCommandShowcase.png)

[_Back to Top_](#table-of-contents)


### Listing all clients : `list`

Shows a list of all clients in the PROperty.

Format: `list`

- `list` shows the clients in the order they were added into PROperty.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:** 
Use `list` for a quick overview of all your contacts. 
</div>

Example:

1. `list` shows your full client listing in the order they were added in PROperty.

Visual example of correct output:

![ListCommandShowcase.png](images/user-guide-images/ListCommandShowcase.png)

[_Back to Top_](#table-of-contents)


### Sorting all clients : `sort`

Sorts the list of all clients in PROperty by name in alphabetical order.

Format: `sort`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:** 
Sorting is helpful after adding or editing many contacts so that your data remains neat. 
</div>

Example:

1. `sort` sorts all your client list in PROperty in alphabetical order.

Visual example of correct output [Example `1.`]:

![SortCommandShowcase.png](images/user-guide-images/SortCommandShowcase.png)

[_Back to Top_](#table-of-contents)


### Showing property listings of a client : `show`

Shows the full details of the specified client, including their property listings.

Format: `show INDEX`

- Shows the client at the specified `INDEX`
- The `INDEX` refers to the index number shown in the displayed client list.
- The `INDEX` **must be a positive integer** 1, 2, 3, …​

<div markdown="span" class="alert alert-primary">:bulb: **Tip:** 
Use `show` to view a client in-depth. 
</div>

Examples:

1. `show 2` shows the name, client information, tags, and property listings of the client at index `2` in the PROperty.


2. `show 7` shows the name, client information, tags, and property listings of the client at index `7` in the PROperty.

Visual example of correct output (Example 1):

![Show Command](images/user-guide-images/ShowCommandShowcase.png)

[_Back to Top_](#table-of-contents)


### Locating clients by name: `find`

Finds clients whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`.
* By default, the find command conduct a general search for the individual. Hence, 
the order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`.
* A client's name, phone number, address, email and tag can be searched.
* Only full words will be matched e.g. `Han` will not match `Hans`.
* clients matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
* If a more specific search is required, utilise the `s/`.
  * Format: `find s/KEYWORD [s/MORE_KEYWORDS]`
  * Only individuals who match the keyword(s) one-to-one will be returned. e.g. `find s/Hans Bo` will not match `Bo Hans`. `find s/Hans Bo` will only match `Hans Bo`.
  * Especially useful if there are multiple clients with the same name in PROperty and you require a more specific search.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:** 
Use `find s/KEYWORD` if you have contacts with very similar names. 
</div>

Examples:

1. `find David` is a general find which returns `David Low` and `David Li`.


2. `find s/David Low` is a specific find which only returns `David Low`.


3. `find alex david` is a general find which returns `Alex Yeoh`, `David Li`.


4. `find s/Alex Yeoh s/23 Smith Street` is a specific find which only returns `Alex Yeoh` who has `23 Smith Street` as his address.

Visual example of correct output (General Find) (Example 1):

![GeneralFindCommandShowcase.png](images/user-guide-images/GeneralFindCommandShowcase.png)

Visual example of correct output (Specific Find) (Example 2):

![SpecificFindCommandShowcase.png](images/user-guide-images/SpecificFindCommandShowcase.png)

[_Back to Top_](#table-of-contents)


### Locating clients by tag: `findtag`

Finds clients whose tags contain any of the given words.

Format: `findtag TAG [MORE_TAGS]`

* The search is case-insensitive. e.g.`HDB` will match `hdb`. 
* The order of the tags does not matter. 
* clients with at least one matching tag will be returned (i.e., an `OR` search). 
* List of possible tags you can search for are found in the [Tag Table](#tag-table)

<div markdown="span" class="alert alert-primary">:bulb: **Tip:** 
Tags make it easy for you to categorise your contacts into different groups.
</div>

Examples:

1. `findtag HDB` returns clients tagged with `HDB`.


2. `findtag HDB buyer` returns clients tagged with either `HDB` or `buyer`.

Visual example of correct output (Example 1):

![Find tag command](images/user-guide-images/FindtagCommandShowcase.png)

[_Back to Top_](#table-of-contents)


### Clearing all entries : `clear`

Clears all entries from the PROperty.

Format: `clear`

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
`clear` cannot be undone! 
</div>

Example:

1. `clear` clears all clients and their data from PROperty.

[_Back to Top_](#table-of-contents)


### Managing Remarks : `remark`

Adds/removes a remark from a client in PROperty.

Format: `remark INDEX r/[REMARKS]`

* Adds a remark `REMARKS` to the client at `INDEX`
* The index refers to the index number shown in the displayed client list.
* The index **must be a positive integer** 1, 2, 3, …​
* If there are multiple remark inputs, only the last remark will be added. See example 3 below for a better illustration.


<div markdown="span" class="alert alert-primary">:bulb: **Tip:** 
If `[REMARKS]` of `r/` is left blank (e.g `remark 1 r/`), the remark will be deleted entirely
</div>

Examples:

1. `remark 1 r/Prefers a higher floor apartment` adds a remark "Prefers a higher floor apartment" to the client at index `1`.


2. `remark 1 r/` deletes the remark of client at index `1`.


3. `remark 1 r/Prefers Bukit Timah area r/Prefers Jurong area` only adds remark "Prefers Jurong area" to the client at index `1`.

Visual example of correct output (Example 1):

![RemarkAddCommandShowcase](images/user-guide-images/RemarkAddCommandShowcase.png)

Visual example of correct output (Example 2):

![RemarkRemoveCommandShowcase](images/user-guide-images/RemarkRemoveCommandShowcase.png)

Visual example of correct output (Example 3):

![MultipleRemarkAddCommandShowcase](images/user-guide-images/MultipleRemarkAddCommandShowcase.png)

[_Back to Top_](#table-of-contents)


### Adding a property listing : `listing add`

Adds a property listing to the client specified by `INDEX`

Format: `listing add INDEX t/PROPERTY_TAG a/LISTING_ADDRESS`

- Adds a property listing to the client specified by `INDEX`
- The `INDEX` refers to the index number shown in the displayed client list.
- The `INDEX` **must be a positive integer** 1, 2, 3, …​ 
- Property tags are added in a case-insensitive manner. e.g `t/condo` or `t/CONDO` will both add the `CONDO` tag.
- Refer to the [Tag Table](#tag-table) for a complete list of property tags.
- If there are multiple tag inputs, the last tag will be used. See example 3 below for a better illustration.

Examples:

1. `listing add 1 t/condo a/NUS street 123` adds a property listing to the client at index `1` with a listing type of `condo` and address of `NUS street 123`.


2. `listing add 2 t/HDB a/Clementi Road 321` adds a property listing to the client at index `2` with a listing type of `HDB` and address of `Clementi Road 321`. 


3. `listing add 1 t/HDB t/condo a/NTU Road 321` adds a property listing to the client at index `1` with a listing type of `condo` and address of `NTU Road 321`.

Visual example of correct output (Example 1):

![ListingAddCommandShowcase.png](images/user-guide-images/ListingAddCommandShowcase.png)

Visual example of correct output (Example 3):

![ListingAddMultipleTagShowcase.png](images/user-guide-images/ListingAddMultipleTagShowcase.png)

[_Back to Top_](#table-of-contents)


### Deleting a property listing : `listing delete`

Deletes the property listing with index `LISTING_INDEX` from the client specified by `INDEX` 

Format: `listing delete INDEX LISTING_INDEX`

* Adds the property listing with index `LISTING_INDEX` to the client specified by `INDEX`
* The `INDEX` refers to the index number shown in the displayed client list.
* The `LISTING_INDEX` refers to the index number shown in the property listing displayed by the `show` command
* The `INDEX`/`LISTING_INDEX` **must be a positive integer** 1, 2, 3, …​

Examples:

- `listing delete 1 1` deletes the 1st property listing from the client with index `1`.


- `listing delete 2 3` deletes the 3rd property listing from the client with index `2`.


Visual example of correct output (Example 1):

![ListingDeleteCommandShowcase.png](images/user-guide-images/ListingDeleteCommandShowcase.png)

[_Back to Top_](#table-of-contents)


### Exporting your contacts : `export`

Exports your contacts and their relevant data to a Comma-Separated Value (CSV) file format.

Format: `export`

* Headings of the CSV file will be the attributes of a client (ie Name, Address, Phone number etc).
* If an individual listings and/or tags attributed to them, the listings and/or tags are separated by a semicolon.
* By default, the exported CSV file is at `[JAR file location]/data/property.csv`.
* Ensure that you do not have any other programmes utilising the `property.csv` file while you run the `export` command. Otherwise, PROperty may not correctly export your client data.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:** 
`export` is useful if you want to view your contacts in Excel.
</div>

Example:

1. `export` exports your current client data in PROperty into a CSV file.

[_Back to Top_](#table-of-contents)


### Exiting the program : `exit`

Exits the program.

Format: `exit`

* You should **only** utilise the `exit` command to close the application. Unintended issues or bugs may occur if you utilize other methods to close the application.
* There is no visual example as the programme will close after inputting the `exit` command.

Example:

1. `exit` exits PROperty.

[_Back to Top_](#table-of-contents)


### Open help menu: `help`

Shows commands in a help menu for quick reference during use of PROperty.

Format: `help`

Example:

1. `help` results in a popup window which contains all the commands in PROperty for easy reference during use.

[_Back to Top_](#table-of-contents)


### Saving the data

PROperty data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

[_Back to Top_](#table-of-contents)


### Editing the data file

PROperty data are saved automatically as a [JSON](#technical-terms) file `[JAR file location]/data/property.json`. Advanced users are welcome to update data directly by editing that data file with valid inputs only.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file results in an invalid format, PROperty may not load the data and will reset the `property.json` file after you exit the application. Hence, it is recommended to make a backup of the file before editing it.<br>
Furthermore, certain edits can cause the PROperty to behave in unexpected ways (e.g. if a value entered is outside of the acceptable range, or manually add tags not stated in the glossary etc). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

[_Back to Top_](#table-of-contents)


--------------------------------------------------------------------------------------------------------------------
<div class="page-break"></div>

## FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: To transfer your data to another computer, follow these steps:

1. **Install PROperty**: Place the `PROperty.jar` file in your preferred location on the new computer.
2. **Set Up the Data Folder**: If you have already run the application, skip to step 3. Otherwise, manually create a `data` folder in the same directory as `PROperty.jar`.
3. **Transfer Your Data**: If the application has been run previously, simply replace the existing `property.json` file in the `data` folder with your data-containing `property.json` file from the original computer. Otherwise, add your `property.json` file directly into the `data` folder you just created.
4. **Launch PROperty**: Run the app to load your transferred data, giving you access to all your previous clients and property listings.


**Q**: Can I customise the tags or categories for clients and properties?<br>
**A**: Customising tags is not currently supported. Users can only use the predefined tags listed in the table provided in this guide.

**Q**: Is there a way to restore a deleted client?<br>
**A**: Unfortunately, it is not possible to restore a deleted client. We strongly recommend that you regularly save a backup of your clients to prevent accidental data loss.

[_Back to Top_](#table-of-contents)


--------------------------------------------------------------------------------------------------------------------

## Known Issues

1. In a multi-screen setup, should you move PROperty to a secondary screen and subsequently revert to using only the primary screen, the PROperty GUI may open off-screen. To resolve this issue, you should delete the `preferences.json` file created by the PROperty before launching it again. 
2. If you minimise the Help Window and subsequently execute the `help` command, the original Help Window will remain minimised, and no new Help Window will appear. To resolve this issue, you should manually restore the minimised Help Window.

[_Back to Top_](#table-of-contents)


--------------------------------------------------------------------------------------------------------------------
<div class="page-break"></div>

## Command Summary

| Action             | Format, Examples                                                                                                                                                    |
|--------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**            | `add n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [t/client_TAG] [r/REMARKS]…​` <br> e.g. `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/buyer` |
| **List**           | `list`                                                                                                                                                              |
| **Edit**           | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/client_TAG] [dt/client_TAG] [r/REMARK]…​`<br> e.g. `edit 2 n/James Lee e/jameslee@example.com`              |
| **Find**           | `find KEYWORD [MORE_KEYWORDS]`<br/>`find s/KEYWORD [s/MORE_KEYWORDS]`<br> e.g. `find James Jake`, `find s/James Jake s/23 Philip Street`                            |
| **Findtag**        | `findtag TAG [MORE_TAGS]`<br> e.g. `findtag hdb buyer`                                                                                                              |
| **Delete**         | `delete INDEX`<br> e.g. `delete 3`                                                                                                                                  |
| **Clear**          | `clear`                                                                                                                                                             |
| **Exit**           | `exit`                                                                                                                                                              |
| **Help**           | `help`                                                                                                                                                              |
| **Remark**         | `remark INDEX r/[REMARKS]`<br> e.g. `remark 1 r/Prefers a higher floor apartment`, `remark 1 r/` (to delete the remark)                                             |
| **Show**           | `show INDEX`<br> e.g. `show 2`                                                                                                                                      |
| **Add Listing**    | `listing add INDEX t/PROPERTY_TAG a/LISTING_ADDRESS`<br> e.g. `listing add 1 t/condo a/123 NUS Street`                                                              |
| **Delete Listing** | `listing delete INDEX LISTING_INDEX `<br> e.g. `listing delete 1 1`                                                                                                 |
| **Export**         | `export`                                                                                                                                                            |
| **Sort**           | `sort`                                                                                                                                                              |

[_Back to Top_](#table-of-contents)


--------------------------------------------------------------------------------------------------------------------

## Tag Table

| Tag Type | Tags                                                                                                                                                                      |
|----------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Property | HDB, CONDO, RESIDENTIAL, LANDED, EC, COMMERCIAL, RETAIL, INDUSTRIAL, OFFICE, WAREHOUSE, SHOPHOUSE, TERRACE, SEMIDET, BUNGALOW, DETACHED, GCB, PENTHOUSE, MIXED, SERVAPT, DORM |
| Client   | BUYER, SELLER, LANDLORD, TENANT, DEVELOPER, INVESTOR, MANAGER, CONTRACTOR                                                                                                 |

[_Back to Top_](#table-of-contents)


--------------------------------------------------------------------------------------------------------------------
<div class="page-break"></div>

## Glossary

### Technical Terms
- **CLI**: Command-Line Interface, a text-based interface used to interact with software by typing commands.
- **CSV**: Comma-Separated Values, a simple file format used to store tabular data, where each line of the file is a data record with fields separated by commas.
- **GUI**: Graphical User Interface, a user interface that allows users to interact with the application through graphical elements such as buttons, text fields, and menus.
- **JAR**: Java ARchive, a package file format that aggregates many Java class files and associated resources (text, images) into one file to distribute application software or libraries on the Java platform.
- **Java**: A high-level, object-oriented programming language used for building cross-platform applications.
- **JSON**: JavaScript Object Notation, a lightweight data-interchange format that is easy for humans to read and write, and easy for machines to parse and generate. JSON is commonly used for transmitting data in web applications.

[_Back to Top_](#table-of-contents)


### Property Tags
- **HDB**: Public housing flats governed by the Housing & Development Board.
- **CONDO**: Private residential units with shared amenities.
- **RESIDENTIAL**: Properties intended for residential use.
- **LANDED**: Homes built on their own land, without shared walls (e.g. bungalows, terraces).
- **EC**: Executive Condominium – a subsidised option bridging public and private property.
- **COMMERCIAL**: Properties zoned for business operations.
- **RETAIL**: Space designated for shops and consumer-facing businesses.
- **INDUSTRIAL**: Sites for manufacturing, logistics, and heavy industry activities.
- **OFFICE**: Office spaces designed for corporate or business use.
- **WAREHOUSE**: Dedicated to storage and logistical operations.
- **SHOPHOUSE**: Multi-use buildings with commercial space on the ground floor, residential above.
- **TERRACE**: Row houses sharing side walls, commonly in clusters.
- **SEMIDET**: Semi-detached houses sharing one side wall.
- **BUNGALOW**: Single-story, standalone homes, often with private land.
- **DETACHED**: Fully standalone houses, no shared walls.
- **GCB**: Good Class Bungalows – high-end, exclusive bungalows with strict land requirements.
- **PENTHOUSE**: Premium top-floor units, typically in condos, with added privacy and amenities.
- **MIXED**: Properties combining multiple zoning uses, like residential and commercial.
- **SERVAPT**: Furnished serviced apartments, typically with extended-stay services.
- **DORM**: Dormitories, often used as shared housing for students or workers.


[_Back to Top_](#table-of-contents)


### Client Tags
- **BUYER**: Prospective property purchasers.
- **SELLER**: Property owners seeking to sell.
- **LANDLORD**: Property owners leasing to tenants.
- **TENANT**: Individuals or entities renting properties.
- **DEVELOPER**: Companies or individuals creating or renovating properties.
- **INVESTOR**: Individuals or groups acquiring property for financial gain.
- **MANAGER**: Those managing property operations, tenant relations, or maintenance.
- **CONTRACTOR**: Professionals providing construction, renovation, or repair services.


[_Back to Top_](#table-of-contents)


--------------------------------------------------------------------------------------------------------------------
