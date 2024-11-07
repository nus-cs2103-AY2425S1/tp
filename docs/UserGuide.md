---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# SocialBook User Guide

SocialBook is a **desktop app for managing contacts, optimized for use via a  Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, SocialBook can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103-F10-2/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your SocialBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar socialbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `view 2` : Toggles the view on the 2nd contact shown in the current list with more/less information.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 d/06-01-2024` : Adds a contact named `John Doe` to the SocialBook.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.<br>
  e.g `n/NAME [a/ADDRESS]` can be used as `n/Jane Smith a/123 Hollywood Street 55` or as `n/Jane Smith`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters will result in an error for the `list` and `view` commands. Other commands like `help`, `exit`, and `clear` will ignore any additional parameters and execute as intended.
  e.g. if the command specifies `help 123`, `exit please` or `clear 5`, it will be interpreted as help, exit or clear respectively. However, if the command specifies `list something` or `view 2 3`, it will throw an error.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a table of commands with their respective descriptions and a link to the user guide.

![help message](images/helpMessage.png =700x)

Format: `help`


### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [t/TAG]…​ [d/DATE_OF_LAST_VISIT] [ec/EMERGENCY_CONTACT]`

<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0).<br>

</box>
<box type="tip" seamless>

**Tip:** The only required fields for a person are a name and a phone number, so you can create a contact with just those 2 fields. Providing an email, address, date of last visit, emergency contact or tags is optional.

</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 d/02-01-2024`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/62345678 t/criminal d/03-28-2024`
* `add p/92345678 n/Jane Smith d/01-01-2024 ec/98765432`
* `add p/92345678 n/Jane Smith d/01-01-2024`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​ [d/DATE_OF_LAST_VISIT] [ec/EMERGENCY_CONTACT] [r/REMARK]`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.
* For optional fields (email, emergency contact, address, date of last visit, remark) you can delete them by entering the prefix without specifying any value after.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/ e/` Edits the name of the 2nd person to be `Betsy Crower`, clears all existing tags and deletes the stored email.


### Locating specific persons: `find`

Finds contacts whose names or/and phone numbers or/and address or/and tags contain any of the given field keywords.

Format: `find [n/NAMEKEYWORDS] [p/PHONEKEYWORDS] [a/ADDRESSKEYWORDS] [t/TAGKEYWORDS]`

**NOTE:** At least one field MUST be provided
  e.g. `find n/Hans` or `find p/82345678` or `find a/wall street` will work
  e.g. `find Hans` or `find wall street` or `find` will fail
* The search is case-insensitive. e.g `hans` will match `Hans` or `wall Street` will match `Wall Street`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Partial words will be matched e.g. `Han` will match `Hans`
* Contacts matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
* If more than one fields are specified, contacts will be matched by multiple fields (i.e. `AND` search).
* For multiple address or tag keywords, they are separated by "_". e.g `find t/friends_colleague_owes money` or `find a/wall street_michigan`
* For multiple name or phone keywords, they are separated by " ". e.g `find n/andy ben carl` or `find p/98233211 81212899`

<box type="tip" seamless>

**Take Note:** using an `edit` command on a contact after a `find` operation may remove them from the displayed list, if the contact is edited to no longer match the `find` requirements. Use the `list` command to return to the view of all contacts, or `find` them again with new parameters.

</box>

Examples:
* `find n/John` returns `john` and `John Doe`
* `find n/alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find n/alex david'](images/findAlexDavidResult.png)
* `find p/87438807 91031282` returns `Alex Yeoh`, `David Li`
* `find a/serangoon` returns `David Li`
* `find n/alex p/87438807 a/geylang` returns `Alex Yeoh`

### Deleting a person : `delete`

Deletes the specified person or persons from the address book.

Format: `delete INDEX` `delete INDEX INDEX ...`

* Deletes the person or persons at the specified `INDEX` or `INDEX INDEX ...`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.
* `list` followed by `delete 1 2` deletes the 1st and 2nd person in the address book.

### Adding remarks to person : `remark`

Add remarks to an existing person in the address book.

Format: `remark INDEX r/REMARK`

* Adds remarks to the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `remark 1 r/Financial Issues` Adds the remark of the 1st person to be `Financial Issues`.
* `remark 1 r/` Clears remarks (if any) of the 1st person.

### Sorting the person list : `sort`

Sorts the list of persons being viewed by name or date of last visit in ascending or descending order.

Format: `sort PARAMETER_PREFIX/ORDER`

* Sorts the contacts according to the parameter, in the specified order.
* By default, if `ORDER` is omitted, contacts will be sorted in ascending order based on the `PARAMETER`.
* An ascending order can be specified by replacing `ORDER` with `ascending` or its short form `asc`.
* A descending order can be specified by replacing `ORDER` with `descending` or its short form `desc`.

Examples:
* `sort n/` sorts by name in ascending order.
* `sort d/`, `sort d/asc`, `sort d/ascending` are all equivalent, and they sort the date of last visit in ascending order. 
* `sort d/desc` sorts by date of last visit in descending order.

### Viewing a person : `view`

Toggles the contact card on the specified person, switching between more and less information. <br>
The default view for all contact cards will display less information to avoid visually overwhelming users, but users may decide to toggle the `view` for all information on one or more persons.

Format: `view INDEX`

* This command permits the user to `view` multiple contacts at once. Using the `view` command on a contact that's already expanded will collapse it back to its default view.
* Viewing is done by index, and **not** the person's name or any other field. Attempting to `view` by name, address, or any other fields will result in an error.
* View is intended for short term ad-hoc usage, and the view states of contact cards will not persist between sessions.

Examples:
* `view 2` will expand the contact card for the second person in the contact list. <br>
  ![result of `view 2`](images/viewTwoResult.png =600x)
* Using `view 2` again will collapse the contact card back down to its default view. <br>
  ![result of second `view 2`](images/secondViewTwoResult.png =600x)

<box type="tip" seamless>

**Take Note:** Viewing is executed based on the currently displayed list. 
Executing any commands that alter the displayed list (such as `delete`, `sort`, or `find`) may change the person being viewed.
For this reason, it is recommended to execute `view` commands after the displayed list has been modified as intended. 

</box>

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Populating with dummy data : `seed`

Adds dummy data to the address book.

Format: `seed`

**NOTE:** There are 6 contacts in the dummy data. `seed` works in a way that would be similar to if the user were to iteratively issue `add` command on the 6 person. This means that,

- `seed` will add them to the contact list if they are not presently inside. 
- `seed` does not clear or reset the list.
- If your exisitng contact list has a person with the same name and phone number, it will **not** be overwritten.  

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

SocialBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

SocialBook data are saved automatically as a JSON file `[JAR file location]/data/socialbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If a person's data values are changed to an invalid format, Socialbook will discard that particular person's data while keeping the rest. However, if your changes to the data file makes the file format invalid, SocialBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the SocialBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## Contact field requirements

### Name
* Names are compulsory for all contacts, and are denoted with the `n/` prefix.
* Names can contain any characters at all, including spaces, hyphens, and other special characters.
* Names will be stored in their case-sensitive form, but capitalisation will be ignored when checking for duplicate names.
  * Eg. Adding a contact as "john Doe" will save them as such, but trying to add a "John Doe" with the same phone number will be marked as a duplicate person and rejected.
  * Other instances of possible duplicates such as identical names except for extra spaces are not handled.

### Phone
* Phones are compulsory for all contacts, and are denoted with the `p/` prefix.
* Phone numbers can only contain 8 numbers, and must begin with a 6, 8, or 9.
* Spaces in the middle of a phone number are accepted (eg. 9123 4523), as are phone numbers without spaces (eg. 91234523).
* Spaces in unusual locations will render the phone number invalid (eg. 912 34523).

### Address
* Addresses are optional for contacts, and are denoted by the `a/` prefix.
* Addresses can contain any characters, including spaces, commas, hyphens, etc.
* To indicate no address for a contact, you can `add` a contact without the `a/` prefix, or with a `a/` followed by whitespace.

### Email
* Emails are optional for contacts, and are denoted by the `e/` prefix.
* Email addresses are confined to the limits of the traditional email format: **`localPart@domain.label`**. This includes a few restrictions:
  * The `localPart` and `domain` components of the email must be alphanumeric, with no special characters.
  * The `localPart` and `domain` components of the email must be separated by a `@`.
  * The `label` component must be alphanumeric, and contain at least 2 characters.
  * The `domain` and `label` component must be separated by a `.`.
* To indicate no email for a contact, you can `add` a contact without the `e/` prefix, or with a `e/` followed by whitespace.

### Date of Last Visit
* Dates of last visit are optional for contacts, and are denoted by the `d/` prefix.
* Dates of last visit are confined to the `DD-MM-YYYY` format.
* The date provided must be valid, and before the current date. This prevents accidental entering of future dates.
* To indicate no date of last visit for a contact, you can `add` a contact without the `d/` prefix, or with a `d/` followed by whitespace.

### Emergency Contact
* Emergency contacts are optional fields, and are denoted by the `ec/` prefix.
* Emergency contacts are subject to the same formatting requirements as `Phone`.
* To indicate no emergency contact for a person, you can `add` a contact without the `ec/` prefix, or with a `ec/` followed by whitespace.

### Tags
* Tags are optional for contacts, and are denoted by the `t/` prefix.
* More than one tag can be added to a contact.
* Tags can contain any characters, but they should not begin with whitespace.
* You can include hyphens and spaces as necessary between words for tags that are multiple words long!
* To indicate no tags for a contact, you can `add` a contact without any `t/` prefixes.
  * Take note that `add`ing a contact with a `t/` prefix followed by whitespace is not supported. Omit the `t/` tag for contacts without tags.

### Remarks
* Remarks are optional for contacts, and are denoted by the `r/` prefix.
* It is recommended that long-form notes about a particular contact should be saved in remarks.
* Remarks can contain any characters, as they allow long-form writing with multiple sentences.<br>
* **IMPORTANT:** Only one `r/` prefix can be used when adding remarks. 
  * Adding another `r/` prefix will cause the first part of the `r/` prefix to be lost.
  * If needed to add the prefix `r/` to remark, enclose the prefix with " ". e.g. `remark 1 r/ use "r/" to add remark`

<box type="tip" seamless>

**Take note:** contacts will always be created without remarks. To write a remark about a contact, you can do this with the `remark` command, or with the `edit` command by specifying an `r/` prefix.

</box>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous SocialBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [t/TAG]…​ [d/DATE_OF_LAST_VISIT] [ec/EMERGENCY_CONTACT]` <br> e.g., `add n/James Ho p/82224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague d/07-23-2024`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​ [d/DATE_OF_LAST_VISIT] [ec/EMERGENCY_CONTACT] [r/REMARK]` <br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find [n/NAMEKEYWORD] [p/PHONEKEYWORD] [a/ADDRESSKEYWORD] [t/TAGKEYWORD]`<br> e.g., `find n/James Jake a/clementi street_woodlands`
**List**   | `list`
**View**   | `view INDEX`<br> e.g.,`view 1`
**Help**   | `help`
**Seed**   | `seed`
**Sort**   | `sort PARAMETER_PREFIX/ORDER` <br> e.g., `sort n/ascending`
**Remark** | `remark INDEX r/REMARK`
