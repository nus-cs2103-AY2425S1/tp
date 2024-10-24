---
layout: page
title: User Guide
---

TalentHub is a desktop app designed for **Celebrity Talent Managers**, to help with the **management of celebrity and relevant industrial contacts**.

TalentHub is optimized for use via a **Command Line Interface** (CLI) while still having the benefits of a **Graphical User Interface** (GUI). 
<div markdown="block" class="alert alert-info">

**:information_source: CLI and GUI:**<br>
This means that you can do everything by simply typing commands into the command box, and you can easily view and navigate results on the screen. This way, you can type fast and manage your contacts efficiently, while still having the ease of viewing the contacts in a graphical format.
</div>

If you can type fast and you need to manage celebrities and their schedules, **TalentHub** is the app for you!
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your computer. You can view an installation guide [here](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-W12-4/tp/releases/tag/v1.3).

1. Copy the file to the folder you want to use as the _home folder_ for your TalentHub.

1. Open a command terminal, `cd` to change directory to the folder your TalentHub is in, and use the `java -jar talenthub.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list person` : Lists all persons.

   * `add person n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to TalentHub.

   * `delete person 3` : Deletes the 3rd person shown in the current list.

   * `clear all` : Deletes all events and persons.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

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

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

## Managing Contacts
### Adding a person: `add person`

Adds a person to the address book.

Format: `add person n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
* `add person n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add person n/Betsy Crowe e/betsycrowe@example.com a/Crowe Apartment p/1234567 t/Celebrity`

![result for 'add person'](images/add_person.png)

### Listing all persons : `list person`

Shows a list of all persons in the address book.

Format: `list person`

Example: Calling `list person` would yield the following output

![result for 'list person'](images/list_person.png)

### Editing a person : `edit person`

Edits an existing person in the address book.

Format: `edit person INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit person 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit person 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

![result for 'edit person'](images/edit_person.png)

### Locating persons by name: `find person`

Finds persons whose names contain any of the given keywords.

Format: `find person KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g. `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched. e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find person John` returns `john` and `John Doe`
* `find person john betsy` returns `John Doe`, `Betsy Crowe`

![result for 'find john betsy'](images/findJohnBetsyResult.png)

### Viewing person by name: `view person`

Views the comprehensive details, which includes address and email address, of a specific person 
whose name exactly matches the given keywords.

Format: `view person KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g. `john` will match `John`
* The order of the keywords matters. e.g. `John Doe` will match `John Doe` but not `Doe John`
* Only the **full name** is searched.
* Only full words will be matched. e.g. `John` will not match `Johnny`
* Persons matching all keywords exactly will be returned. e.g. `John Doe` will return `John Doe`

Examples:

* `view person Betsy Crowe` returns the details for `Betsy Crowe`
* `view person John Doe` returns the details for `John Doe`

![result for 'view john doe'](images/viewJohnDoeResult.png)

### Filtering person by tag: `filter person`

Filters persons based on the exact tag provided, ensuring only persons with that tag are displayed.

Format: `filter person TAG`

* The search is case-insensitive. e.g. `celebrity` will match `Celebrity`.
* Only exact full-word matches will return a result. 
  e.g. `Hair` will return `Hair` but not `Hairdresser`

Examples:

* `filter person Hairdresser` returns the person with tag `HairDresser`.
* `filter person Celebrity` returns the person with tag `Celebrity`.

![result for 'filter celebrity'](images/filterCelebrityResult.png)

### Deleting a person : `delete person`

Deletes the specified person from the address book.

Format: `delete person INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list person` followed by `delete person 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete person 1` deletes the 1st person in the results of the `find` command.

![result for 'delete person'](images/delete_person.png)

## Managing Events
### Adding an event: `add event`

Adds an event to TalentHub.

Format: `add event n/NAME t/TIME v/VENUE c/CELEBRITY [p/POINTS OF CONTACT]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
An event can have any number of points of contact (including 0)
</div>

Examples:
* `add event n/Oscars t/2022-03-27 v/Dolby Theatre c/Jim Bob p/John Doe, Jane Doe`
* `add event n/Hair Cut t/2022-03-27 v/John's Salon c/Betsy Crowe`

![result for 'add event'](images/add_event.png)

### Listing all events: `list event`

Shows a list of all events in the address book.

Format: `list event`

Example: Calling `list event` would yield the following output

![result for 'list event'](images/list_event.png)

### Editing an event : `edit event`

Edits an existing event in the address book.

Format: `edit event INDEX [n/NAME] [t/TIME] [v/VENUE] [c/CELEBRITY] [p/POINTS OF CONTACT]…​`

* Edits the event at the specified `INDEX`. The index refers to the index number shown in the displayed event list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* * When editing points of contact, the existing points of contact of the person will be removed i.e adding of points of contact is not cumulative.
* You can remove all the person’s points of contact by typing `p/` without
  specifying any points of contact after it.

Examples:
*  `edit event 1 t/2024-03-21 v/Jane's Salon` Edits the time and venue of the 1st event to be `2024-03-21` and `Jane's Salon` respectively.
*  `edit event 2 n/Movie Screening t/` Edits the name of the 2nd event to be `Movie Screening` and clears all existing points of contact.

![result for 'edit event'](images/edit_event.png)

### Locating events by name: `find event`

Finds events whose names contain any of the given keywords.

Format: `find person KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g. `hiking` will match `Hiking`
* The order of the keywords does not matter. e.g. `Hair Cut` will match `Cut Hair`
* Only the name is searched.
* Only full words will be matched. e.g. `Oscar` will not match `Oscars`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hair Hiking` will return `Hair Cut`, `Park Hiking`, `Hiking`

Examples:
* `find event Hiking` returns `Hiking` and `Park Hiking`
* `find event Hair Oscars` returns `Hair Cut`, `Oscars`

![result for 'find hair oscars'](images/findHairOscarsResult.png)

### Viewing event by name: `view event`

Views the comprehensive details, which includes points of contact, of a specific event whose
name exactly matches the given keywords.

Format: `view event KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g. `hiking` will match `Hiking`
* The order of the keywords matters. e.g. `Hair Cut` will match `Hair Cut` but not `Cut Hair`
* Only the **full name** is searched.
* Only full words will be matched. e.g. `Oscar` will not match `Oscars`.
* Events matching all keywords exactly will be returned.
  e.g. `Hiking` will not match `Park Hiking`

Examples:

* `view event Oscars` returns the details for `Oscars`
* `view event Hair Cut` returns the details for `Hair Cut`

![result for 'view hair cut'](images/viewHairCutResult.png)

### Filtering events by celebrity name: `filter event`

Filters events based on the exact celebrity name provided,
ensuring that only events associated with that celebrity are displayed.

Format: `filter event CELEBRITY_NAME`

* The search is case-insensitive. e.g. `betsy crowe` will match `Betsy Crowe`
* The order of the keywords matters. e.g. `Betsy Crowe` will not match `Crowe Betsy`
* Only the **full celebrity name** is searched.
* Only full name will be matched. e.g. `Bet` will not match `Betsy`
* Persons matching all keywords exactly will be returned.
  e.g. `Betsy` will match `Betsy` but not `Betsy Crowe`

Examples:

* `filter event Jim Bob` returns the events for celebrity `Jim Bob`
* `filter event Betsy Crowe` returns the events for celebrity `Betsy Crowe`

![result for 'filter betsy crowe'](images/filterBetsyCroweResult.png)

### Deleting an event : `delete event`

Deletes the specified event from the address book.

Format: `delete event INDEX`

* Deletes the event at the specified `INDEX`.
* The index refers to the index number shown in the displayed event list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list event` followed by `delete event 2` deletes the 2nd event in the address book.
* `find Oscars` followed by `delete event 1` deletes the 1st event in the results of the `find` command.

![result for 'delete event'](images/delete_event.png)

## Utility Commands
### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### More information on Commands : `help`

Provides users with a link to the application's user guide. This link can be copied using the copy URL button to access
the user guide online.

Format: `help`

Example:

![result for 'help'](images/help.png)

### Saving the data

TalentHub data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
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
2. **When adding Persons or Events**, most fields are mandatory. An update to allow for optional fields is in progress.
3. **When adding Points of Contact to an Event**, the application does not check for duplicate entries. The remedy is to either use the Edit Event command or manually remove the duplicate Points of Contact. An update to prevent duplicate Points of Contact is in progress.
3. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

### Managing Contacts
| Action     | Format, Examples|
|------------|------------------|
| **Add**    | `add person n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`<br> e.g., `add person n/Jake Doe p/98765432 e/jaked@example.com a/123, Clementi Rd, 1234665 t/Celebrity`|
| **Delete** | `delete person INDEX`<br> e.g., `delete person 3`|
| **Edit**   | `edit person INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit person 1 n/James Lee e/jameslee@example.com`|
| **Filter** | `filter person TAG`<br> e.g., `filter person Celebrity`|
| **Find**   | `find person KEYWORD [MORE_KEYWORDS]`<br> e.g., `find person James Jake`|
| **List**   | `list person`|
|  **View**  | `view person KEYWORD [MORE_KEYWORDS]`<br> e.g., `view person Jake Doe`|

### Managing Events
| Action     | Format, Examples|
|------------|------------------|
| **Add**    | `add event n/NAME t/TIME v/VENUE c/CELEBRITY [p/POINTS OF CONTACT]…​`<br> e.g., `add event n/Oscars t/2022-03-27 v/Dolby Theatre c/Jim Bob p/John Doe, Jane Doe`|
| **Delete** | `delete event INDEX`<br> e.g., `delete event 3`|
| **Edit**   | `edit event INDEX [n/NAME] [t/TIME] [v/VENUE] [c/CELEBRITY] [p/POINTS OF CONTACT]…​`<br> e.g.,`edit event 1 t/2024-03-21 v/Jane's Salon`|
| **Filter** | `filter event CELEBRITY_NAME`<br> e.g., `filter event Jim Bob`|
| **Find**   | `find event KEYWORD [MORE_KEYWORDS]`<br> e.g., `find event Hair Oscars`|
| **List**   | `list event`|
|  **View**  | `view event KEYWORD [MORE_KEYWORDS]`<br> e.g., `view event Oscars`|

### Utility Commands
| Action    | Format, Examples|
|-----------|------------------|
| **Clear** | `clear`|
| **Exit**  | `exit`|
|  **Help** | `help`|
