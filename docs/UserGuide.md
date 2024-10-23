---
layout: page
title: User Guide
---

GRUB is a quick and efficient solution to search for local dining options, tailored to personal preferences.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103-F12-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all restaurants.

   * `add n/Swensens p/61234567 e/swensens@goreply.com a/NUS street, COM 5, #01-01` : Adds a restaurant named `Swensens` to the Address Book.

   * `delete 3` : Deletes the 3rd restaurant shown in the current list.

   * `clear` : Deletes all restaurants.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/Swensens`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/Swensens t/Western` or as `n/Swensens`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/Western`, `t/Western t/Halal` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

Format: `help`


### Adding a restaurant: `add`

Adds a restaurant to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [r/RATING] [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
The rating is an integer value between 0 to 10. Rating can be empty.
A restaurant can have any number of tags (including 0)
</div>

Examples:
* `add n/Swensens p/61234567 e/swensens@goreply.com a/NUS street, COM 5, #01-01`
* `add n/Mala Cold Pot t/Chinese e/foodsoyum@hotandspicy.com a/Changi Prison p/99991111 t/Halal`

### Listing all restaurants : `list`

Shows a list of all restaurants in the address book.

Format: `list`

### Editing a restaurant : `edit`

Edits an existing restaurant in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [r/RATING] [t/TAG]…​`

* Edits the restaurant at the specified `INDEX`. The index refers to the index number shown in the displayed restaurant list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the restaurant will be removed i.e adding of tags is not cumulative.
* You can remove all the restaurant’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/98765432 e/swensens@plsreply.com` Edits the phone number and email address of the 1st restaurant to be `98765432` and `swensens@plsreply.com` respectively.
*  `edit 2 n/Mala Hot Pot t/` Edits the name of the 2nd restaurant to be `Mala Hot Pot` and clears all existing tags.

### Locating restaurants by name: `find`

Finds restaurants whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `mala` will match `Mala`
* The order of the keywords does not matter. e.g. `Mala Hot Pot` will match `Hot Pot Mala`
* Only the name are searched.
* Only full words will be matched e.g. `Swen` will not match `Swensens`
* Restaurants matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Mala Swensens` will return `Swensens`, `Mala Hot Pot`

Examples:
* `find Swensens Mala` returns `Swensens` and `Mala Hot Pot`
* `find mala` returns `Mala Hot Pot`<br>

### Locating restaurants by tag: `tag`

Finds restaurants whose tags contain any of the given keywords.

Format: `tag KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `halal` will match `Halal`
* The order of the keywords does not matter. e.g. `Chinese Halal` will match `Halal Chinese`
* Only the tags are searched.
* Only full words will be matched e.g. `Hal` will not match `Halal`
* Restaurants matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Chinese Western` will return `Swensens`, `Mala Hot Pot`

Examples:
* `tag Halal` returns `Swensens` and `Mala Hot Pot`
* `tag Chinese` returns `Mala Hot Pot`<br>

### Deleting a restaurant : `delete`

Deletes the specified restaurant from the address book.

Format: `delete INDEX`

* Deletes the restaurant at the specified `INDEX`.
* The index refers to the index number shown in the displayed restaurant list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd restaurant in the address book.
* `find Mala` followed by `delete 1` deletes the 1st restaurant in the results of the `find` command.

### Rating a restaurant : `rate`

Rates the specified restaurant from the address book.

Format: `rate INDEX [r/RATING]`

* Edits the restaurant's rating at the specified `INDEX`. The index refers to the index number shown in the displayed restaurant list. The index **must be a positive integer** 1, 2, 3, …​
* Existing rating will be updated.
* The rating **should be an integer between 0 to 10**
* The rating can be empty

Examples:
*  `rate 1 r/2` Edits the rating of the 1st restaurant to be `2`.
*  `rate 2 r/2` Edits the rating of the 2nd restaurant to be `No Rating`.

### Favourite a restaurant : `fav`

Set the specified restaurant from the address book as favourite.

Format: `fav INDEX`

* Deletes the restaurant at the specified `INDEX`.
* The index refers to the index number shown in the displayed restaurant list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
*  `fav 1` Favourite the 1st restaurant.


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
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/Mala Cold Pot t/Chinese e/foodsoyum@hotandspicy.com a/Changi Prison p/99991111 t/Halal`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 1 p/98765432 e/swensens@plsreply.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find Mala`
**Tag** | `tag KEYWORD [MORE_KEYWORDS]`<br> e.g., `tag Chinese`
**List** | `list`
**Help** | `help`
