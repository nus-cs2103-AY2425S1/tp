---
layout: page
title: User Guide
---

Address Book Command Line Interface (ABCLI) is a **desktop app made specially for Real Estate Agents to manage contacts and is optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you are a real estate agent and can type fast, ABCLI can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

2. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-F13-2/tp/releases/).

3. Copy the file to the folder you want to use as the _home folder_ for your application.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar abcli.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 u/buyer` : Adds a contact named `John Doe` to the Address Book.

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

* Items in angle brackets represent input choices.<br>
    e.g `t/<buyer,seller>` can be used as `t/buyer` or as `t/seller` only.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

## General

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Clearing all entries : `clear`

Clears all entries from the buyer list.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

BuyerList data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

All data is saved automatically as a JSON file within the storage folder. Advanced users are welcome to update data directly by editing that data file.

Note: By default, the storage folder is set to a folder named `package` in the home folder.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, the data files will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the JSON files to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Switching parser modes : `switch`

Switches the parser mode to the specified parser mode.

Format: `switch PARSER_MODE`

* Switches the parser mode to the specified `PARSER_MODE`.
* The parser mode takes 3 types:
* `b` for buyers
* `m` for meet-ups
* `p` for properties
* The default parser mode is set to `b`.

Examples:
* Upon entering the application, the parser mode is set to `b`.
* `switch` followed by `m` switches the parser mode to meet-ups.

## Buyers
### Adding a buyer: `add`

Adds a buyer to the buyer list.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A buyer can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 u/buyer`
* `add n/Betsy Crowe t/friend u/seller e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Viewing all buyers : `view`

Shows a list of all buyers in the buyer list.

Format: `view`

### Editing a buyer : `edit`

Edits an existing buyer in the buyer list.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the buyer at the specified `INDEX`. The index refers to the index number shown in the displayed buyer list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the buyer will be removed i.e adding of tags is not cumulative.
* You can remove all the buyer’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email budget of the 1st buyer to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd buyer to be `Betsy Crower` and clears all existing tags.

### Locating buyer contacts: `find`

Finds buyers whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Buyers matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a buyer : `delete`

Deletes the specified buyer from the buyer list.

Format: `delete INDEX`

* Deletes the buyer at the specified `INDEX`.
* The index refers to the index number shown in the displayed buyer list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd buyer in the buyer list.
* `find Betsy` followed by `delete 1` deletes the 1st buyer in the results of the `find` command.

## Meet Up
### Adding a meetup: `add`

Adds a meet-up to the meet-up list.

Format: `add n/MEETUP_NAME i/MEETUP_INFO from/MEETUP_FROM to/MEETUP_TO`

<div markdown="span" class="alert alert-primary">

`MEETUP_FROM` and `MEETUP_TO` fields should follow the format `YYYY-MM-DD HH:MM`
</div>

Examples:
* `add n/Product Pitch i/Product pitch for James at MBS from/2024-10-31 13:00 to/2024-10-31 15:30`
* `add n/Show buyer 3 houses i/Meet with James Jimes to show him 3 houses that fit his expectations from/2024-10-30 09:30 to/2024-10-30 10:30`

### Viewing all meet-ups : `view`

Shows a list of all meet-ups in the meet-up list.

Format: `view`

<div>

Examples:
* `view` will show you all meet-ups in the meet-up list.
</div>

### Editing a meet-up : `edit`

Edits an existing meet-up in the meet-up list.

Format: `edit INDEX i/MEETUP_INFO from/MEETUP_FROM to/MEETUP_FROM`

<div markdown="span" class="alert alert-primary">

`MEETUP_FROM` and `MEETUP_TO` fields should follow the format  `YYYY-MM-DD HH:MM`
</div>

* Edits the meet-up at the specified `INDEX`. The index refers to the index number shown in the displayed meet-up list. The index **must be a positive integer** 1, 2, 3, …​
* All fields need to be provided.
* Existing values will be updated to the input values.

Examples:
*  `edit 1 i/Meet with Johnny to show him houses. from/2024-10-28 10:00 to/2024-10-28 12:00` Edits the info, meet-up time of the 1st meet-up to be `Meet with Johnny to show him houses.`, `2024-10-28 10:00` and `2024-10-28 12:00` respectively.

### Locating meet-ups by name: `find`

Finds meet-ups whose meet-up names contain any of the given keywords.

Format: `find KEYWORD`

* The search is case-insensitive. e.g `meet` will match `Meet`
* Only the meet-up name is searched.
* Keyword will be matched to full words and sentences e.g. `meet` will match `meetup` and `meet with Jack`

Examples:
* `find Meet` returns `Meet up with Jack to discuss property prices` and `Go to MBS for meeting with Jane`

### Deleting a meet-up : `delete`

Deletes the specified meet-up from the buyer list.

Format: `delete INDEX`

* Deletes the meet-up at the specified `INDEX`.
* The index refers to the index number shown in the displayed meet-up list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd meet-up in the meet-up list.
* `find meet` followed by `delete 1` deletes the 1st meet-up in the results of the `find` command.

### Filtering meetups : `filter`

Finds all meet-up whose date matches the given date and displays them as a list with index numbers.

Format: `filter KEYWORD`

* The date should be written in the format `yyyy-mm-dd`

Examples:

* `filter 2022-12-12` will find all meetups that are scheduled for 12 December 2022 and show them as a list.

## Properties
### Adding a property: `add`

Adds a property to the property list.

Format: `add n/LANDLORD_NAME p/PHONE_NUMBER l/LOCATION a/ASKING_PRICE t/PROPERTY_TYPE`

Examples:
* `add n/John p/87152433 l/Paya Lebar a/200,000 t/Condominium`

### Viewing all properties : `view`

Shows a list of all properties in the property list.

Format: `view`

### Editing a property : `edit`

Edits an existing property in the property list.

Format: `edit INDEX [n/LANDLORD_NAME] [p/PHONE_NUMBER] [l/LOCATION] [a/ASKING_PRICE] [t/PROPERTY_TYPE]`

* Edits the buyer at the specified `INDEX`. The index refers to the index number shown in the displayed property list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email of the 1st buyer to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower` Edits the name of the 2nd buyer to be `Betsy Crower`.

### Locating Properties: `find`

Find property based on its attributes.

Format: `find [n/LANDLORD_NAME] [p/PHONE_NUMBER] [l/LOCATION] [a/ASKING_PRICE] [t/PROPERTY_TYPE]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only full words will be matched e.g. `Han` will not match `Hans`
* Buyers matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `n/Hans Bo` will return property linked with `Hans Gruber`, `Bo Yang`.

Examples:
* `find n/John t/HDB 5 Room` returns `john` and `John Doe`



<br>_More features coming soon ..._
--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous BuyerList home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary (To be Updated)

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`
