---
layout: page
title: User Guide
---

PlanPal is an **address book** designed for **student event planners** at NUS who need to manage contacts
(e.g., attendees, vendors, sponsors, and volunteers) for their events.

PlanPal offers a **centralized platform** to organize, track, and access contact information, ensuring 
efficient coordination and smooth communication throughout the event planning process.

This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).


* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar planpal.jar` command to run the application.<br>
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
  e.g `n/NAME [r/ROLE]` can be used as `n/John Doe r/attendee` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[r/ROLE]…​` can be used as ` ` (i.e. 0 times), `r/attendee`, `r/attendee r/sponsor` etc.

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

### Person Related Features

#### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE e/EMAIL a/ADDRESS [t/TELEGRAM_USERNAME] [r/ROLE]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of roles (including 0) and Telegram username is optional.
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe e/betsycrowe@example.com a/Newgate Prison p/1234567 t/betsyyy r/attendee r/sponsor`

#### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

#### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TELEGRAM_USERNAME] [r/ROLE]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When adding roles, only the following are valid roles: attendee, sponsor, vendor, volunteer

:bulb: **Tip:** You can remove all the person’s roles by typing `r/` without
    specifying any role after it. Likewise for telegram handle, typing `t/` will remove the person's telegram handle

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
Editing a contact's information in the address book will cause the information to be updated throughout all events!
</div>

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Jason Brown t/` Edits the name of the 2nd person to be `Jason Brown` and clears their telegram username.
*  `edit 3 n/Betsy Crower r/` Edits the name of the 3rd person to be `Betsy Crower` and clears all existing roles.

#### Locating persons, `find-name` and `find-role`

##### Find persons by keywords in name: `find-name`
Finds persons whose names contain any of the given keywords.

Format: `find-name KEYWORD [MORE_KEYWORDS]`

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

##### Find persons by role: `find-role`
Finds persons who have the specified role.

Format: `find-role ROLE`

* The search is case-insensitive. e.g `attendee` will match `Attendee`
* Only full words will be matched e.g. `attend` will not match `attendee`
* Roles should be one of the following: `attendee`, `sponsor`, `vendor`, `volunteer`
* Persons matching the role will be returned.
* If more than 1 role is specified, persons matching at least one role will be returned (i.e. `OR` search).
  e.g. `attendee sponsor` will return persons who are either attendees or sponsors.

Examples:
* `find-role attendee` returns all persons who are attendees.<br>
![FindRoleSingleRole.png](images%2FFindRoleSingleRole.png)



* `find-role attendee sponsor` returns all have roles of either attendee or sponsor (or both!).<br>
![FindRoleDoubleRole.png](images%2FFindRoleDoubleRole.png)


#### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
Once you delete a contact from the address book, the contact will automatically be removed from all events as well!
</div>

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Event related Features

#### Adding a new Event : `new`

Adds a new Event to address book.

Format: `new EVENT NAME`

* Event name cannot be blank

Examples:
* `new Sumo Bot Festival`
* `new RC Horror Night`

#### Find and view all Persons in an event: `find-event`
View the list of Persons in an Event.

Format: `find-event [EVENT INDEX]`

* Event name cannot be blank
* Event must exist in PlanPal.

Examples:
* `find-event Sumo Bot Festival`
* `find-event RC Horror Night`

#### Add a person to an event: `event-add`
Add a person with a specified role to an event.

Format: `event-add ei/EVENT INDEX [a/ or s/ or ve/ or vo/] CONTACT INDEX`

Guide for flag roles:
* `a/` - attendee
* `s/` - sponsor
* `ve/` - vendor
* `vo/` - volunteer

Note: At least one of the following prefixes is required—`a/`, `e/`, `ve/`, or `vo/`—each followed by one or more contact index/indices

Examples:
* `event-add ei/1 a/1,2,3`

#### Remove a person from an event: `remove`
Removes a person from an event.

Format: `remove ei/ [INDEX] pi/ [PERSON INDEX]`

Example:
* `remove ei/1 pi/1`

#### Delete an event: `erase`
Delete an event from the event list.

Format: `erase [EVENT INDEX]`

Example:
* `erase 1`

### Search and Add multiple people to an Event at once in a specialised searching mode: `searchmode`/`sm`
Searchmode allows you to search for persons based on multiple criteria. 
You can search for persons based on any criteria including:
- Name
- Phone Number
- Email
- Address
- Telegram Handle
- Role

To Enter Searchmode, type `searchmode` or `sm` and press Enter. The display will then change to show the Searchmode interface.

![SearchModeUI.png](images%2FSearchModeUI.png)
Searchmode will display the list of all Contacts in PlanPal in the right blue panel (in the same way as the `list` command).
<br> The persons that match the search criteria (if any) will be displayed in the center panel.

The following commands can be used in Searchmode:
- `search` : Search for persons based on the criteria you specify.
- `exclude` : Exclude persons from appearing in searchmode
- `clearexcluded` : Clear all excluded persons
- `checkexcluded` : Check the list of excluded persons
- `exitsearch`/`es` : Exit searchmode and return to the normal display
- `help` : Display the help message 
- `exit`: Exit the program (same as the `exit` command)

#### Searching in Searchmode `search`

Format: `search [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TELEGRAM_USERNAME] [r/ROLE]…​`


<Br>For each field, you can either specify multiple keywords _or_ multiple partial keywords.
<Br>Persons whose name that match at least one of the keywords or contains ALL the partial keywords will be returned.
<Br>For example:
- `search n/Alex Yeoh` will return all persons whose name matches `Alex Yeoh` OR persons whose name contains `"Alex"`or 
`"Yeoh"`
![NameMatchCriteriaExample.png](images%2FNameMatchCriteriaExample.png)


<Br>With multiple fields search command will return all persons that match **ALL** the criteria you specify.
<Br>
Example:
- `search n/Alex Yeoh t/alexyeoh a/Blk` will return all persons 
whose:
  - name contains `Alex Yeoh`
  - telegram handle contains `alexyeoh` 
  - address contains `Blk`.
    ![SpecificSearchCriteria.png](images%2FSpecificSearchCriteria.png)

You can chain multiple searches to get the full list of persons you are looking for!
Example:
- Following up on the previous example, you can add more to the search list with more searches:
  - `search n/Char` will add people who have `Char` in their name to the list!
![ChainSearchExample1.png](images%2FChainSearchExample1.png)


Note: A flag parameter should not be empty (e.g. `n/` or `t/`), as it will not return any results.
Flag parameters should also not be repeated (e.g. `n/Alex n/John`).

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
If looking for a specific person, try to be as specific as possible to get the best results!
</div>

#### Excluding persons from Searchmode `exclude`

Format: `exclude pi/INDEX [MORE_INDEXES]`

To get rid of persons from the search results, you can exclude them using the `exclude` command.
Note that excluded persons will not be displayed in all subsequent search results until they are cleared from the excluded list.

Example:
- `search n/Alex` returns a list of persons with the name `Alex`
  ![NameMatchCriteriaExample.png](images%2FNameMatchCriteriaExample.png)
- `exclude pi/2` will exclude the second person in the list from the search results
![ExcludeExample.png](images%2FExcludeExample.png)
- Subsequent searches will not include the excluded person in the search results
  - `search p/9234512` matches previously excluded person but will not reappear in l ist
![ExcludedSearchExample.png](images%2FExcludedSearchExample.png)

#### Check excluded persons `checkexcluded`
To check the currently excluded persons in the search results, use the `checkexcluded` command.

Format: `checkexcluded`

Example:
- `checkexcluded` will display the list of persons that have been excluded from the search results
![CheckExcluded.png](images%2FCheckExcluded.png)

#### Clear Excluded persons `clearexcluded`
To remove all persons from the excluded list, use the `clearexcluded` command.
Removed persons will be immediately added back to the list

Format: `clearexcluded`

Example:
![ClearExcludedExample.png](images%2FClearExcludedExample.png)

#### Add all selected persons to an Event `add-all`
To add all persons selected in searchmode to an event, use the `add-all` command.

Format: `add-all EVENT-INDEX`

Example: `add-all 2`

#### Exiting Searchmode `exitsearch`/`es`
To exit searchmode and return to the normal display, use the `exitsearch` or `es` command.

Format: `exitsearch` or `es`

Example:
![ExitSearch.png](images%2FExitSearch.png)

_This is the end of the Searchmode feature commands. The following commands can be used in the normal display mode._


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

### Navigating through command history
The `up` and `down` arrow keys can be used to navigate through your command history. When you press the up arrow, the command box displays the previous commands you have executed, allowing you to re-run or edit past commands 
without retyping them. Pressing the down arrow lets you move forward through the command history, returning to more recent commands.

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
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TELEGRAM_USERNAME] [r/ROLE]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/james_ho`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TELEGRAM_USERNAME] [r/ROLE]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find-Name** | `find-name KEYWORD [MORE_KEYWORDS]`<br> e.g., `find-name James Jake`
**Find-Role** | `find-role ROLE [MORE_ROLES]`<br> e.g., `find-role sponsor`
**New** | `new EVENT NAME` <br> e.g. `new Sumo Bot Festival`
**Event-add** | `event-add ei/[EVENT INDEX] pi/[PERSON INDEX]` <br> e.g. `event-add ei/1 pi/1`
**Find-Event** | `find-event [EVENT INDEX]` <br> e.g. `find-event 1`
**Remove** | `remove ei/[EVENT INDEX] pi/[PERSON INDEX]` <br> e.g. `remove ei/1 pi/1`
**Erase** | `erase [EVENT INDEX]`
**List** | `list`
**Help** | `help`


### Searchmode Summary

Action | Format, Examples
--------|------------------
**searchmode** | `searchmode` / `sm`
**search** | `search`
**exclude** | `exclude pi/INDEX [MORE_INDEXES]` <br> e.g. `search n/Alex`
**checkexcluded** | `checkexcluded`
**clearexcluded** | `clearexcluded`
**exitsearch** | `exitsearch` / `es`
