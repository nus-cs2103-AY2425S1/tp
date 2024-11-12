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

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar GRUB.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui.png](images%2FUi.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all restaurants.

   * `add n/Swensens p/61234567 e/swensens@goreply.com a/NUS street, COM 5, #01-01 pr/$` : Adds a restaurant named `Swensens` to the Address Book.

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
Note: Any restaurant filters will be reset upon running this command.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS pr/PRICE [r/RATING] [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Names should only contain alphanumeric characters and spaces, and it should not be blank.
Note: Characters outside the English alphabet are currently not supported.
Please use an English transliteration.
</div>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
The rating is an integer value between 0 to 10. Rating can be empty.
A restaurant can have any number of tags (including 0)
</div>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
The price is a string value that can be one of the following: `$`, `$$`, `$$$`, `$$$$`.
</div>

Examples:
* `add n/Swensens p/61234567 e/swensens@goreply.com a/NUS street, COM 5, #01-01 pr/$`
* `add n/Mala Cold Pot t/Chinese e/foodsoyum@hotandspicy.com a/Changi Prison p/99991111 t/Halal pr/$$`

### Listing all restaurants : `list`

Shows a list of all restaurants in the address book.

Format: `list`

### Editing a restaurant : `edit`

Edits an existing restaurant in the address book.
Note: Any restaurant filters will be reset upon running this command.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [pr/PRICE] [r/RATING] [t/TAG]…​`

* Edits the restaurant at the specified `INDEX`. The index refers to the index number shown in the displayed restaurant list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the restaurant will be removed i.e adding of tags is not cumulative.
* You can remove all the restaurant’s tags by typing `t/` without
    specifying any tags after it.

Examples (Assuming the restaurant list has at least 2 restaurants):
*  `edit 1 p/98765432 e/swensens@plsreply.com` Edits the phone number and email address of the 1st restaurant to be `98765432` and `swensens@plsreply.com` respectively.
*  `edit 2 n/Mala Hot Pot t/` Edits the name of the 2nd restaurant to be `Mala Hot Pot` and clears all existing tags.


### Locating restaurants by tags: `tags`

Find and filters restaurants that contains the given list of tags

Format: `tags KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `halal` will match `Halal`
* The order of the keywords does not matter. e.g. `Chinese Halal` will match `Halal Chinese`
* Only the tags are searched.
* Only full words will be matched e.g. `Hal` will not match `Halal`
* Restaurants matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Chinese Western` will return `Swensens`, `Mala Hot Pot`

Examples (Assuming there are restaurants with the following tags):
* `tags Halal` returns `Swensens` and `Mala Hot Pot`
* `tags Chinese` returns `Mala Hot Pot`
* `tags Halal Chinese` returns `Swensens` and `Mala Hot Pot`<br>

### Locating restaurants by name: `find`

Finds restaurants whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `mala` will match `Mala`
* The order of the keywords does not matter. e.g. `Mala Hot Pot` will match `Hot Pot Mala`
* Only the name are searched.
* Only full words will be matched e.g. `Swen` will not match `Swensens`
* Restaurants matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Mala Swensens` will return `Swensens`, `Mala Hot Pot`

Examples (Assuming there are restaurants with the following names):
* `find Swensens Mala` returns `Swensens` and `Mala Hot Pot`
* `find mala` returns `Mala Hot Pot`<br>


### Deleting a restaurant : `delete`

Deletes the specified restaurant from the address book.
Note: Any restaurant filters will be reset upon running this command.

Format: `delete INDEX`

* Deletes the restaurant at the specified `INDEX`.
* The index refers to the index number shown in the displayed restaurant list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples (Assuming the restaurant list has at least 2 restaurants):
* `list` followed by `delete 2` deletes the 2nd restaurant in the address book.
* `find Mala` followed by `delete 1` deletes the 1st restaurant in the results of the `find` command.

### Rating a restaurant : `rate`

Rates the specified restaurant from the address book.

Format: `rate INDEX r/[RATING]`

* Edits the restaurant's rating at the specified `INDEX`. The index refers to the index number shown in the displayed restaurant list. The index **must be a positive integer** 1, 2, 3, …​
* Existing rating will be updated.
* The rating **should be an integer between 0 to 10**
* The rating can be empty

Examples:
*  `rate 1 r/2` Sets the rating of the 1st restaurant to be `2`.
*  `rate 2 r/` Sets the rating of the 2nd restaurant to be `No Rating`.

Alternative:
*  `edit 1 r/2` Edits the rating of the 2nd restaurant to be `2`.

### Searching for restaurants by price: `price`

Finds restaurants of a specific price label.

Format: `price PRICE_LABEL [MORE_PRICE_LABELS]`

* Multiple price labels can be entered, and the search will return restaurants that match any one of the labels (i.e. `OR` search).
  (e.g. `price $ $$` will return restaurants that are either `$` or `$$`)
* The order of the keywords does not matter. e.g. `$ $$` will match `$$ $`
* Refer to the glossary for details on the price labels.

Examples:
* `price $ $$` returns restaurants that are either `$` or `$$`
* `price $$` returns restaurants that are `$$`<br>

### Favourite a restaurant : `fav`

Set the specified restaurant from the address book as favourite.
Favourite items will be highlighted with a yellow outline and pushed to the top of the list.

Format: `fav INDEX`

* Favourites the restaurant at the specified `INDEX`.
* The index refers to the index number shown in the displayed restaurant list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples (Assuming the restaurant list has at least 1 restaurant):
*  `fav 1` Favourite the 1st restaurant.

### Un-favourite a restaurant : `unfav`

Remove the specified restaurant from the favourites list.
Un-favourite items will not be highlighted and will be re-added to the list.

Format: `unfav INDEX`

* Un-favourite's the restaurant at the specified `INDEX`.
* The index refers to the index number shown in the displayed restaurant list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples (Assuming the restaurant list has at least 1 restaurant):
*  `unfav 1` Un-favourites the 1st restaurant.

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
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with the initial sample data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
</div>

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
-----------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS pr/PRICE [r/RATING] [t/TAG]…​` <br> e.g., `add n/Mala Cold Pot t/Chinese e/foodsoyum@hotandspicy.com a/Changi Prison p/99991111 pr/$ t/Halal`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [pr/PRICE] [r/RATING] [t/TAG]…​`<br> e.g.,`edit 1 p/98765432 e/swensens@plsreply.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find Mala`
**Tag** | `tags KEYWORD [MORE_KEYWORDS]`<br> e.g., `tags Chinese`
**List** | `list`
**Help** | `help`
**Rating** | `rate INDEX r/[RATING]`<br> e.g.`rate 1 r/2`
**Price** | `price PRICE_LABEL [MORE_PRICE_LABELS]`<br> e.g., `price $ $$`
**Favourite** | `fav INDEX`<br> e.g., `fav 1`
**Un-Favourite** | `unfav INDEX`<br> e.g., `unfav 1`
