---
layout: page
title: User Guide
---

SpleetWaise builds on [AddressBook Level 3 (AB3)](https://se-education.org/addressbook-level3/) – **a desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still offering the benefits of a Graphical User Interface (GUI). SpleetWaise makes it easy for students to record transactions with contacts saved in the address book. If you can type fast, SpleetWaise lets you handle your contact and transaction management tasks more efficiently than traditional GUI apps.

<div markdown="span" class="alert alert-info">
**Disclaimer:** Our app currently focuses on supporting _university students_ based in _Singapore_ and the _English language_ only. The app may not be suitable for other non-university students and are not based in Singapore or users who prefer other languages. If users choose to use the app outside of these parameters, it may behave unexpectedly or not as intended.
</div>

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

2. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103-F13-1/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for SpleetWaise.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar spleetwaise-[version].jar`
   command to run the application (*replace `[version]` with the release version you chose, for example, 1.5).<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will
   open the help window.<br>
   Some example commands you can try:

    * `list` : Lists all contacts.

    * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe`
      to the Address Book.

    * `delete 3` : Deletes the 3rd contact shown in the current list.

    * `clear` : Deletes all contacts.

    * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

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

* Parameters' input that collide with existing prefixes may cause error to be thrown.<br>
  e.g. `editTxn 1 desc/John owes me for our afternoon date/meeting` may not be accepted as `date/` is a prefix in editTxn command.<br>
  > :bulb: **Tip:** Use `\/` to add parameter inputs that contain `/` character. e.g. `editTxn 1 desc/John owes me for our afternoon date\/meeting`

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be
  ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines
  as space characters surrounding line-breaks may be omitted when copied over to the application.

</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [r/REMARK] [t/TAG]…​`

* The `NAME` input is case-sensitive and allows a wide variety of characters but has some restrictions. Below are examples of valid and invalid inputs (non-exhaustive).
  * Valid inputs include those that contain **letters, numbers, spaces, and certain special characters** such as apostrophes (`'`), hyphens (`-`), periods (`.`), comma (`,`), slashes (`/`), ampersands (`&`), quotation marks (`"`), and parentheses (`()`). 
  * Invalid inputs may include those that contain special characters such as `*`, `@`, `#`, `!`, `^`, `%`, `$`, or other characters that are not English, such as Arabic, Chinese or Latin scripts like `Æ`.
* The `PHONE_NUMBER` input should only contain numbers, and it should be at least 3 digits long.
* The `ADDRESS` input can take any values, and it should not be blank.
* The `REMARK` input should be a string of words with a limit of 120 characters.
* The `TAG` input should be alphanumeric.

<div markdown="span" class="alert alert-danger">
:exclamation: Important: Duplicate entries for a person cannot be added to the address book. A person is considered a duplicate if both the `NAME` and `PHONE_NUMBER` fields match an existing entry. Together, these fields must create a unique identifier for each person in the address book.
</div>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person with an Indian name containing "s/o" denoting "son of" can be added as `add n/John s\/o Jason p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
</div>
<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person with an Indian name containing "d/o" denoting "daughter of" can be added as `add n/Genevieve d\/o Jason p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
</div>
<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:

* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`
* `add n/Lim Yi Bo, Gavin p/98765433 e/lyb@u.nus.edu a/Blk 123, Jurong West Ave 6, #08-111 t/colleague t/classmate` 

### Adding a transaction: `addTxn`

Adds a transaction to the transaction book.

Format: `addTxn INDEX amt/AMOUNT desc/DESCRIPTION [date/DATE] [status/STATUS] [cat/CATEGORY]...`

* The `INDEX` refers to the index of the person currently displayed in the address book panel (as we are adding the transaction related to the person).
* The `AMOUNT` accepts a decimal number with up to 2 decimal places. A `-` symbol should be added before the number to indicate negative amount, indicating the transaction is one that the user owes the chosen person at the index.
  * Positive Amount Transaction indicates someone owes the user an amount.<br>
    e.g. `addTxn 1 amt/12.30 desc/John owes me for dinner` indicates that John owes the user S$12.30.
  * Negative Amount Transaction indicates the user owes someone an amount.<br>
    e.g. `addTxn 1 amt/-24.30 desc/I owe John for dinner` indicates that the user owes John S$24.30.
  * Amount with value equivalent of 0 is not allowed and will be considered as an invalid input.
* The `DESCRIPTION` is case-sensitive and accepts a string of words with a limit of 120 characters.
* The `DATE` accepts date formatted in the form `DDMMYYYY` i.e.`10102024`.
  * The date is optional. If the date is not provided, the current date will be used.
* The `STATUS` accepts case-sensitive string that is either `Done` or `Not Done`.
  * The status is optional. If the status is not provided, the default status is `Not Done`.
* The `CATEGORY` input should be alphanumeric. Category will be capitalised automatically.

<div markdown="block" class="alert alert-danger">
:exclamation: Important: Transactions with identical values in specific fields are considered duplicates and cannot be added to the transaction book. A transaction is identified as a duplicate if the following fields match exactly: `INDEX` (if it represents the same person at this index), `AMOUNT`, `DESCRIPTION`, and `DATE`. Together, these fields must create a unique combination for each transaction.

> If a similar transaction occurs on a different occasion in the same day with the same contact, we recommend users to tweak the desc field to reflect the specific context.<br>
>
> For example:<br> - `addTxn 1 amt/2.50 desc/sean owes me for morning latte`<br> - `addTxn 1 amt/2.50 desc/sean owes me for afternoon latte`
</div>

<div markdown="span" class="alert alert-primary">
:bulb: **Tip:** If the transaction happened on the current day, the date parameter can be omitted.<br>
</div>
<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A transaction can have any number of categories (including 0)
</div>

Examples:

* `addTxn 1 amt/12.3 desc/John owes me for dinner`
* `addTxn 1 amt/-24.3 desc/I owe John for dinner date/10102024`
* `addTxn 1 amt/-24.3 desc/I owe John for dinner date/10102024 cat/FOOD`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

<div markdown="span" class="alert alert-primary">
:bulb: **Tip:** This command can be used to reset the filter applied on the person list caused by a `find` command 
operation.
</div>

### Listing all transactions : `listTxn`

Shows a list of all transactions in the transaction book.

Format: `listTxn`

<div markdown="span" class="alert alert-primary">
:bulb: **Tip:** This command can be used to reset the filter applied on transaction list caused by a `fitlerTxn` 
command operation.
</div>

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [r/REMARK] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list.
  The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing remark, the existing remark of the person will be removed i.e adding of remark is not cumulative.
* You can remove the person’s remark by typing `r/` without specifying any remark after it.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without specifying any tags after it.

Examples:

* `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567`
  and `johndoe@example.com` respectively.
* `edit 2 r/` Edits the 2nd person by deleting the remark.
* `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Editing a transaction : `editTxn `

Edits an existing transaction in the transaction book.

Format: `editTxn INDEX [amt/AMOUNT] [desc/DESCRIPTION] [date/DATE] [cat/CATEGORY]...`

* Edits the transaction at the specified `INDEX`. The index refers to the index number shown in the displayed transaction list.
  The index **must be a positive integer** 1, 2, 3, …
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing categories, the existing categories of the transaction will be removed i.e adding of categories is not
  cumulative.
* You can remove all the person’s categories by typing `cat/` without specifying any categories after it.
* Other parameter specification, please refer to `addTxn` parameter specification section.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:** 
Status can be edited via `markDone` or `markUndone` command.
</div>

Examples:

* `editTxn 1 desc/Hello world` edits the description of the 1st transaction to be `Hello world`.
* `editTxn 2 cat/` Edits the 2nd transaction by removing all existing categories.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* Only the name is searched.
* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only full words will be matched e.g. `Han` will not match `Hans`, `Rick` will not match `(Rick)`
  * Names with special characters such as `John-lary` will be matched with `John-lary` but not `john` or `lary`.
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:

* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Filtering transactions: `filterTxn`

Filter transactions with any combination of the following parameters:
* the specified person identified by their index in the displayed person list
* and/or amount 
* and/or description 
* and/or date
* and/or status
* and/or positive/negative amount
* and/or category

Format: `filterTxn [INDEX] [amt/AMOUNT] [desc/DESCRIPTION] [date/DATE] [status/STATUS] [amtsign/AMOUNT_SIGN] [cat/CATEGORY]`

* The command requires at least one of the above optional prefixes to be provided.
* As more prefixes are provided, the filter becomes more specific.
* The `INDEX` refers to the index number shown in the displayed person list.
  The index **must be a positive integer** 1, 2, 3, …​
* The `AMOUNT` accepts a decimal number with up to 2 decimal places. A minus (`-`) symbol should be added before the number to indicate negative amount, indicating the transaction is one that the user owes the chosen person at the index. Results will display transactions with the exact amount if it exists.
  * Amount with value equivalent of 0 is not allowed and will be considered as invalid input.
* The `DATE` accepts date formatted in the form `DDMMYYYY` i.e.`10102024`.
* The `DESCRIPTION` accepts a string of words.
  * The description filter is case-insensitive and allows partial matching. e.g `hans` will match `Hansolo Danello`
* The `STATUS` accepts case-sensitive string that is either `Done` or `Not Done`. 
  * To indicate filtering for transactions that are done or not done.
* The `AMOUNT_SIGN` accepts case-sensitive string of either `Pos` or `Neg`
  * To indicate filtering for transactions with amount that are positive or negative respectively.
* The `CATEGORY` input should be alphanumeric.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:** 
The filtered condition remains active until another `filterTxn` command is issued or the transaction list is explicitly reset using `listTxn`.
</div>

Examples:<br>

* Given the example transaction book:<br>
  ![Given the example transaction book](images/filterTxnExample.png)
<div style="margin: 20px"></div>

* `filterTxn 1` returns all transactions with the person `Alex Yeoh`. Given that `1` is the index of `Alex Yeoh` in the displayed person list.<br>
  ![result fpr 'filterTxn 1'](images/filterTxnAlexYeohResult.png)
<div style="margin: 20px"></div>

* `filterTxn 2 amt/5.5` returns all transactions with the person `Bernice Yu` with amount `5.50`. Given that `2` is the index of `Bernice Yu` in the displayed person list.<br>
  ![result for 'filterTxn 2 amt/5.5'](images/filterTxnBerniceYuAmt55Result.png)

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
   * All transactions associated with the person will also be deleted.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:

* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Deleting a transaction : `deleteTxn`

Deletes the specified transaction from the transaction book.

Format: `deleteTxn INDEX`

* Deletes the transaction at the specified `INDEX`.
* The `INDEX` refers to the index number shown in the displayed transaction list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:

* `listTxn` followed by `deleteTxn 2` deletes the 2nd transaction in the transaction book.
* `filterTxn 1` followed by `delete 1` deletes the 1st transaction in the results of the `filterTxn` command.

### Clearing all entries of AddressBook and TransactionBook: `clear`

Clears all entries from the address book and transaction book.

Format: `clear`

### Clearing all entries of TransactionBook : `clearTxn`

Clears all entries from the transaction book.

Format: `clearTxn`

### Marking a transaction as done : `markDone`

Marks a specified transaction from the transaction book as done.

Format: `markDone INDEX`

* Marks a transaction at the specified `INDEX` as done. 
  * If an undone transaction is marked as done, a done icon <img src="images/done_icon.png" width="16" height="16">, appears for the transaction in GUI (transactions that are not done should not have the icon).
  * If a done transaction is marked as done again, the transaction remains done with no warning/alert.
* The index refers to the index number shown in the displayed transaction list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:

* `listTxn` followed by `markDone 2` marks the 2nd transaction in the transaction book as done.

### Marking a transaction as not done : `markUndone`

Marks a specified transaction from the transaction book as not done. (The terms "undone" and "not done" may be used interchangeably in Spleetwaise and its documents.)

Format: `markUndone INDEX`

* Marks a transaction at the specified `INDEX` as not done.
  * If a done transaction is marked as not done, the existing done icon for the transaction in GUI disappears.
  * if an undone transaction is marked as not done again, the transaction remains not done with no warning/alert.
* The index refers to the index number shown in the displayed transaction list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:

* `listTxn` followed by `markUndone 2` marks the 2nd transaction in the transaction book as not done.

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook and Transaction data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

- AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`
- TransactionBook data are saved automatically as a JSON file `[JAR file location]/data/transactionbook.json` 
- Advanced users are welcome to update data directly by editing that data file.

<div markdown="block" class="alert alert-warning" style="text-align: justify;">:exclamation: **Caution:**
If changes to the data files make its format invalid, SpleetWaise will discard corrupted data and start as usual. To avoid data loss, it’s recommended to back up the file before making edits. Person and Transactions with invalid fields will be discarded before the application starts.<br>

Furthermore, certain edits can cause the app to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data files only if you are confident that you can update it correctly.
</div>

<div markdown="block" class="alert alert-danger" style="text-align: justify;">:exclamation: **Important**: 
In addition to the stated criteria for duplicates, when editing through the data files, if a new person or transaction entry has the same `id` as an existing person or transaction in the addressbook.json or transactionbook.json respectively, it will be discarded. Additionally, the `personId` in transactions.json must match the `id` of a person in addressbook.json.

If you must edit existing entries' `id` or create new entries through the data files, please ensure their `id` fields follow the [UUID](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/UUID.html) format (Users may explore using [UUID generators](https://www.uuidgenerator.net/version7) to create unique `id` values for new entries). Failing to follow this guideline may cause the app to behave in unexpected ways.

The `id` field in both persons and transactions is essential for maintaining [referential integrity](https://www.sciencedirect.com/topics/computer-science/referential-integrity) within the address and transaction books, and are intended for internal reference as the devs aim to mimic databases' with referential integrity to ensure valid relationships between persons and transactions. These `id` link transactions to their respective persons and vice versa.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and before launching the app, copy the `data` folder over to its home folder from your previous SpleetWaise home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut`F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.
3. **Case Sensitivity in Names May Result in Perceived Duplicates in Address Book**: Currently, SpleetWaise treats names as case-sensitive, meaning that variations like "bob", "bOb", "boB", "Bob", "BOB", and "BoB" are considered distinct entries (even when all Bobs have the same phone number). This may lead to perceived duplicate entries if consistent capitalisation is not used. For example, "Alex Yeoh" and "alex yeoh" would be stored as separate contacts, even if both entries have the same phone number. The intended behavior in future releases is for case-insensitive handling, where entries like "Alex Yeoh" and "alex yeoh" with the same phone number would be considered potential duplicates and a warning or alert could be displayed to the user. Until this update, users are advised to maintain consistent capitalisation when adding contacts to avoid unintentional duplicates.
4. **Email Validation Allows Certain Invalid Formats**: The current email validation allows some entries that may not be valid email formats. For instance, "123@123" is accepted even though it lacks a fully qualified domain. This occurs because the validation does not strictly enforce the requirement for a complete domain structure (e.g., "123.com" instead of just "123"). Future improvements aim to refine this validation to align more closely with real-world email standards. Until then, users are advised to enter email addresses in standard formats (e.g., "username@example.com") to avoid unexpected behavior.
5. **Phone Number Validation Lacks Support for International Formats**: The current phone number validation requires that phone numbers only contain numeric characters and be at least 3 digits long. However, this limitation may not adequately support users with international contacts, as it lacks flexibility for common international number formats that include country codes (e.g., +65 91234567 or +1 123-456-7890). This may pose a problem for students who frequently communicate with international friends. Future improvements aim to support international phone number formats by allowing optional country codes and special characters such as + and -. In the meantime, users are advised to store international numbers in a consistent, numeric-only format (e.g., without + or spaces) to ensure compatibility with the current app constraints.
6. **Lack of Support for Multiple Phone Numbers per Contact**: Currently, each contact can only store a single phone number. This limitation may be inconvenient for users who need to save multiple numbers for a single person, such as a mobile and home number or alternative international contact numbers. Future enhancements may include support for storing multiple phone numbers for each contact. Until then, users who need to save additional phone numbers for a contact may consider creating a separate entry with a label in the contact’s name (e.g., "Alex Yeoh - Home" and "Alex Yeoh - Mobile")
7. **Find Command Does Not Persist Filtered State for Persons**: Currently, executing commands such as `add` or `edit` resets the person filter, causing the UI to refresh. This can be inconvenient for users wishing to execute commands to the existing filtered list of persons. Future enhancements aim to retain the person filter state for such commands.

--------------------------------------------------------------------------------------------------------------------

## Command Summary for Address Book

| Action     | Format, Examples                                                                                                                                                                                                              |
|------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [r/REMARK] [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 r/James is poor af, do not loan him money t/friend t/colleague`    |
| **Clear**  | `clear`                                                                                                                                                                                                                       |
| **Delete** | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                                                                           |
| **Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [r/REMARK] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                                                                        |
| **Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`                                                                                                                                                                    |
| **List**   | `list`                                                                                                                                                                                                                        |
| **Help**   | `help`                                                                                                                                                                                                                        |

--------------------------------------------------------------------------------------------------------------------

## Command Summary for Transactions

| Action          | Format, Examples                                                                                                                                                                                                       |
|-----------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**         | `addTxn INDEX amt/AMOUNT desc/DESCRIPTION [date/DATE] [status/STATUS] [cat/CATEGORY]` <br> e.g., `addTxn 1 amt/9999999999.99 desc/Sean owes me a lot for a plot of land in sentosa date/10102024 status/Done cat/LOAN` |
| **Edit**        | `editTxn INDEX [amt/AMOUNT] [desc/DESCRIPTION] [date/DATE] [cat/CATEGORY]` <br> e.g., `editTxn 1 amt/9999999999.99 desc/Sean owes me a lot for a plot of land in sentosa date/10102024 cat/LOAN`                       |
| **List**        | `listTxn`                                                                                                                                                                                                              |
| **Filter**      | `filterTxn [INDEX] [amt/AMOUNT] [desc/DESCRIPTION] [date/DATE] [status/STATUS] [amtsign/AMOUNT_SIGN]` <br> e.g. `filterTxn 1`                                                                                          |
| **Clear**       | `clearTxn`                                                                                                                                                                                                             |
| **Mark Done**   | `markDone INDEX` <br> e.g. `markDone 1`                                                                                                                                                                                |
| **Mark Undone** | `markUndone INDEX` <br> e.g. `markUndone 1`                                                                                                                                                                            |
