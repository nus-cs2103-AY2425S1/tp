---
layout: page
title: User Guide
---

Welcome to TalentHub, an all-in-one desktop application designed to help you, a **Celebrity Talent Manager**, with the management of your celebrities and their relevant industrial contacts.

TalentHub is optimized for use via a **Command Line Interface** (CLI) while still having the benefits of a **Graphical User Interface** (GUI).

<div markdown="block" class="alert alert-info">

**:information_source: CLI and GUI:**<br>
Having access to both a Command Line Interface (CLI) and a Graphical User Interface (GUI) means that you can do everything by simply typing commands into the command box, and you can easily view and navigate results on the screen. This way, you can type fast and manage your contacts and your celebrities' events efficiently, while still having the ease of viewing all of them in a graphical format.

</div>

* Table of Contents
{:toc}

---

## Quick start

1. Ensure you have Java `17` or above installed in your computer. You can view an installation guide [here](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html).

2. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-W12-4/tp/releases/tag/v1.5).

3. Copy the file to the folder you want to use as the _home folder_ for your TalentHub.

4. Open a command terminal, `cd` to change directory to the folder your TalentHub is in, and use the `java -jar talenthub.jar` command to run the application.
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
<img src="images/Ui.png" alt="Ui" style="display: block; margin: 0 auto; border-radius: 10px;">

5. Type the command in the command box and press Enter to execute it. e.g. typing `help` and pressing Enter will open the help window.<br>
   Some example commands you can try:

   - `list person` : Lists all persons.

   - `add person n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to TalentHub.

   - `delete person 3` : Deletes the 3rd person shown in the current list after receiving confirmation from you.

   - `clear all` : Deletes all events and persons after receiving confirmation from you.

    <div markdown="span" class="alert alert-warning">:warning: **Caution!** <br>
    Running this command will clear all your data in TalentHub. This action is irreversible after confirmation.
    </div>

    - `exit` : Exits the app.<br><br>

6. Refer to the [Features](#features) below for details of each command.

---

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

- Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

- Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/Celebrity` or as `n/John Doe`.

- Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/Celebrity`, `t/Stylist t/Hairdresser` etc.

- Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

- Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

- If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

---

## Managing Contacts

### Adding a person: `add person`

This command allows you to add a person to a list of persons on TalentHub!

Format: `add person n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

<div markdown="block" class="alert alert-warning">

**:warning: Person constraints**<br>

- A person's name must be unique (case-sensitive).

- A person's name can only contain alphanumeric characters and spaces.

- A person's phone number must be unique.

- A person's tag must not contain any spaces e.g `Twitch Streamer` is not a valid tag.
</div>

<div markdown="block" class="alert alert-info">

**:information_source: Optionals** <br>
A person can have any number of tags or none at all!
The address and email address fields are also optional! 
</div>

Examples:

- `add person n/John Doe p/98765432 e/johnd@example.com t/Celebrity` adds a person named `John Doe` with the phone number `98765432`, email address `johnd@example.com` and tag `Celebrity`.

- `add person n/Bernice Yu e/berniceyu@example.com a/Yu Apartment p/99272758 t/Hairdresser` adds a person named `Bernice Yu` and the email address `berniceyu@example.com`, address `Yu Apartment`, phone number `99272758` and tag `Hairdresser`.

<figure style="text-align: center;">
  <img src="images/add_person.png" alt="result for 'add person'" style="display: block; margin: 0 auto; border-radius: 10px;">
  <figcaption>command: <code>add person n/John Doe p/98765432 e/johnd@example.com t/Celebrity</code></figcaption>
</figure>

### Listing all persons : `list person`

This command allows you to view a list of all persons on TalentHub!

Format: `list person`

Example:

<figure style="text-align: center;">
  <img src="images/add_person.png" alt="list person" style="display: block; margin: 0 auto; border-radius: 10px;">
  <figcaption>command: <code>list person</code></figcaption>
</figure>

### Editing a person : `edit person`

This command allows you to edit an existing person in TalentHub!

Format: `edit person INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

- Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** and no greater than `Integer.MAX_VALUE` (2147483647) e.g. 1, 2, 3, …​
- At least one of the optional fields must be provided.
- Existing values will be updated to the input values.
- When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.

<div markdown="block" class="alert alert-warning">

**:warning: Person constraints**<br>

- A person's name must be unique (case-sensitive).

- A person's name can only contain alphanumeric characters and spaces.

- A person's phone number must be unique.
</div>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:** 
You can delete a person's tags, email or address by leaving their fields empty, like `t/`, `e/` or `a/` respectively!
</div>

Examples:

- `edit person 7 p/91234567 e/ a/123 Clementi Rd` Edits the phone number and address of the 7th person to be `91234567` and `123 Clementi Rd` respectively and clears the email address.

- `edit person 2 n/Bernice Yu a/ t/` Edits the name of the 2nd person to be `Bernice Yu` and clears the address and all existing tags.

<figure style="text-align: center;">
  <img src="images/edit_person.png" alt="edit person" style="display: block; margin: 0 auto; border-radius: 10px;">
  <figcaption>command: <code>edit person 7 p/91234567 e/ a/123 Clementi Rd</code></figcaption>
</figure>

### Finding persons by name: `find person`

This command allows you to find persons whose names contain any of the given keywords on TalentHub!

Format: `find person KEYWORD [MORE_KEYWORDS]`

- The search is case-insensitive. e.g. `hans` will match `Hans`
- The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
- Only full words will be matched. e.g. `Han` will not match `Hans`
- Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
- Exact spacing must be used `Bernice Yu` is not the same as `Bernice  Yu`.

Examples:

- `find person John` returns `john` and `John Doe`

- `find person john bernice` returns `John Doe`, `Bernice Yu`

<figure style="text-align: center;">
  <img src="images/find_person.png" alt="find person" style="display: block; margin: 0 auto; border-radius: 10px;">
  <figcaption>command: <code>find person John Bernice</code></figcaption>
</figure>

### Viewing person by name: `view person`

This command allows you to view the comprehensive details, which includes address and email address, of a specific person
whose name exactly matches the given keywords on TalentHub!

Format: `view person KEYWORD [MORE_KEYWORDS]`

- The search is case-sensitive. e.g. `john` will not match `John`
- The order of the keywords matters. e.g. `John Doe` will match `John Doe` but not `Doe John`
- Only the **full name** is searched.
- Only full words will be matched. e.g. `John` will not match `Johnny`
- Persons matching all keywords exactly will be returned. e.g. `John Doe` will return `John Doe`

Examples:

- `view person Bernice Yu` returns the details for `Bernice Yu`

- `view person John Doe` returns the details for `John Doe`

<figure style="text-align: center;">
  <img src="images/view_person.png" alt="view person" style="display: block; margin: 0 auto; border-radius: 10px;">
  <figcaption>command: <code>view person John Doe</code></figcaption>
</figure>

### Filtering person by tag: `filter person`

This command allows you to filter persons based on the exact tag provided, ensuring only persons with that tag are displayed on TalentHub!

Format: `filter person TAG`

- The search is case-insensitive. e.g. `celebrity` will match `Celebrity`.
- Only exact full-word matches will return a result.
  e.g. `Hair` will return `Hair` but not `Hairdresser`

Examples:

- `filter person Hairdresser` returns the person with tag `HairDresser`.

- `filter person Celebrity` returns the person with tag `Celebrity`.

<figure style="text-align: center;">
  <img src="images/filter_person.png" alt="filter person" style="display: block; margin: 0 auto; border-radius: 10px;">
  <figcaption>command: <code>filter person Celebrity</code></figcaption>
</figure>

### Deleting a person : `delete person`

This command allows you to delete the specified person from the lists of persons on TalentHub!

Format: `delete person INDEX`

- Deletes the person at the specified `INDEX`.
- The index refers to the index number shown in the displayed person list.
- The index **must be a positive integer** and no greater than `Integer.MAX_VALUE` (2147483647) e.g. 1, 2, 3 …​

<div markdown="span" class="alert alert-warning">:warning: **Caution!** <br>
When you delete a person, you will also delete events which the person is the celebrity for, and you remove the person from **all** events' contact lists. This action is irreversible after confirmation.
</div>

Examples:

- `list person` followed by `delete person 2` deletes the 2nd person in the TalentHub.

- `find person Bernice` followed by `delete person 1` deletes the 1st person in the results of the `find person` command.

<figure style="text-align: center;">
  <img src="images/delete_person_confirmation.png" alt="delete person confirmation" style="display: block; margin: 0 auto; border-radius: 10px;">
  <figcaption>command: <code>delete person 7</code></figcaption>
</figure>

<figure style="text-align: center;">
  <img src="images/pending_cancelled.png" alt="delete person cancelled" style="display: block; margin: 0 auto; border-radius: 10px;">
  <figcaption>Typing <code>N</code> or <code>n</code> and pressing enter would return the following message.</figcaption>
</figure>

<figure style="text-align: center;">
  <img src="images/delete_person.png" alt="delete person" style="display: block; margin: 0 auto; border-radius: 10px;">
  <figcaption>Typing <code>Y</code> or <code>y</code> and pressing enter would return the following message.</figcaption>
</figure>

---

## Managing Events

### Adding an event: `add event`

This command allows you to add an event to a list of events on TalentHub!

Format: `add event n/NAME t/TIME [v/VENUE] c/CELEBRITY [p/POINTS OF CONTACT]…​`

<div markdown="block" class="alert alert-warning">

**:warning: Event constraints**<br>

- The celebrity and all points of contacts must be existing persons in TalentHub.
- Current implementation does not require the person to be added to have the celebrity tag attached to their name.
- You cannot add an event with the same `Celebrity` and overlapping `Time` as an existing event.
- When adding a person to the point of contacts list, only the first tag will be displayed.
</div>

<div markdown="block" class="alert alert-info">

**:information_source: Optionals** <br>
An event can have any number of points of contact or none at all!
The venue field is also optional! 
</div>

Examples:

- `add event n/Oscars t/2022-03-27 10:00 to 2022-03-27 18:00 v/Dolby Theatre c/Alex Yeoh p/Charlotte Oliveiro p/David Li` adds an event named `Oscars` with the time `2022-03-27 10:00 to 2022-03-27 18:00`, venue `Dolby Theatre`, celebrity `Alex Yeoh` and points of contact `Charlotte Oliveiro` and `David Li`.

- `add event n/Hair Cut t/2022-03-27 16:00 to 2022-03-27 18:00 v/Salon c/Bernice Yu` adds an event named `Hair Cut` with the time `2022-03-27 16:00 to 2022-03-27 18:00`, venue `Salon`, celebrity `Bernice Yu` and no points of contact.

<figure style="text-align: center;">
  <img src="images/add_event.png" alt="result for 'add event'" style="display: block; margin: 0 auto; border-radius: 10px;">
  <figcaption>command: <code>add event n/Hair Cut t/2022-03-27 16:00 to 2022-03-27 18:00 v/Salon  c/Bernice Yu</code></figcaption>
</figure>

### Listing all events: `list event`

This command allows you to view a list of all events on TalentHub!

Format: `list event`

Example:

<figure style="text-align: center;">
  <img src="images/list_event.png" alt="result for 'list event'" style="display: block; margin: 0 auto; border-radius: 10px;">
  <figcaption>command: <code>list event</code></figcaption>
</figure>

### Editing an event : `edit event`

This command allows you to edit an existing event on TalentHub!

Format: `edit event INDEX [n/NAME] [t/TIME] [v/VENUE] [c/CELEBRITY] [p/POINTS OF CONTACT]…​`

- Edits the event at the specified `INDEX`. The index refers to the index number shown in the displayed event list. The index **must be a positive integer** and no greater than `Integer.MAX_VALUE` (2147483647) e.g. 1, 2, 3, …​
- At least one of the optional fields must be provided.
- Existing values will be updated to the input values.

<div markdown="block" class="alert alert-warning">
**:warning: Event constraints**<br>
- The celebrity and all points of contacts must be existing persons in TalentHub.
- You cannot add an event with the same `Celebrity` and overlapping `Time` as an existing event.
</div>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
You can delete an event's venue or points of contact by leaving their fields empty, like `v/`, `p/` respectively!
</div>
  
Examples:

- `edit event 1 t/2024-03-21 v/Jane's Salon` Edits the time and venue of the 1st event to be `2024-03-21` and `Jane's Salon` respectively.

- `edit event 2 n/Movie Screening v/ p/` Edits the name of the 2nd event to be `Movie Screening` and clears the venue and points of contact.

<figure style="text-align: center;">
  <img src="images/edit_event.png" alt="result for 'edit event'" style="display: block; margin: 0 auto; border-radius: 10px;">
  <figcaption>command: <code>edit event 2 n/Movie Screening v/ p/</code></figcaption>
</figure>

### Finding events by name: `find event`

This command allows you to find events whose names contain any of the given keywords on TalentHub!

Format: `find event KEYWORD [MORE_KEYWORDS]`

- The search is case-insensitive. e.g. `hiking` will match `Hiking`
- The order of the keywords does not matter. e.g. `Hair Cut` will match `Cut Hair`
- Only full words will be matched. e.g. `Oscar` will not match `Oscars`
- Persons matching at least one keyword will be returned.
  e.g. `Hair Hiking` will return `Hair Cut`, `Park Hiking`, `Hiking`

Examples:

- `find event Hiking` returns `Hiking` and `Park Hiking`

- `find event Hair Oscars` returns `Hair Cut`, `Oscars`

<figure style="text-align: center;">
  <img src="images/find_event.png" alt="result for 'find event'" style="display: block; margin: 0 auto; border-radius: 10px;">
  <figcaption>command: <code>find event Hair Oscars</code></figcaption>
</figure>

### Viewing event by name: `view event`

This command allows you to view the comprehensive details, which includes points of contact, of a specific event whose
name exactly matches the given keywords on TalentHub!

Format: `view event KEYWORD [MORE_KEYWORDS]`

- The search is case-insensitive. e.g. `hiking` will match `Hiking`
- The order of the keywords matters. e.g. `Hair Cut` will match `Hair Cut` but not `Cut Hair`
- Only the **full name** is searched.
- Only full words will be matched. e.g. `Oscar` will not match `Oscars`.
- Events matching all keywords exactly will be returned.
  e.g. `Hiking` will not match `Park Hiking`
- Only the first tag of every person in the Point of Contact list is displayed.

Examples:

- `view event Oscars` returns the details for `Oscars`

- `view event Hair Cut` returns the details for `Hair Cut`

<figure style="text-align: center;">
  <img src="images/view_event.png" alt="result for 'view event'" style="display: block; margin: 0 auto; border-radius: 10px;">
  <figcaption>command: <code>view event Hair Cut</code></figcaption>
</figure>

### Filtering events by celebrity name: `filter event`

This command allows you to filter events based on the exact celebrity name provided, ensuring that only events associated with that celebrity are displayed on TalentHub!

Format: `filter event CELEBRITY_NAME`

- The search is case-sensitive. e.g. `bernice yu` will not match `Bernice Yu`
- The order of the keywords matters. e.g. `Bernice Yu` will not match `Yu Bernice`
- Only the **full celebrity name** is searched.
- Only full name will be matched. e.g. `Ber` will not match `Bernice`
- Persons matching all keywords exactly will be returned.
  e.g. `Bernice` will match `Bernice` but not `Bernice Yu`

Examples:

- `filter event Alex Yeoh` returns the events for celebrity `Alex Yeoh`

- `filter event Bernice Yu` returns the events for celebrity `Bernice Yu`

<figure style="text-align: center;">
  <img src="images/filter_event.png" alt="result for 'filter event'" style="display: block; margin: 0 auto; border-radius: 10px;">
  <figcaption>command: <code>filter event Bernice Yu</code></figcaption>
</figure>

### Deleting an event : `delete event`

This command allows you to delete the specified event from the list of events on TalentHub!

Format: `delete event INDEX`
- Deletes the event at the specified `INDEX`.
- The index refers to the index number shown in the displayed event list.
- The index **must be a positive integer** and no greater than `Integer.MAX_VALUE` (2147483647)
  e.g. 1, 2, 3, …​

<div markdown="span" class="alert alert-primary">
  :bulb: **Tip:** 
  If you would like to delete all events at once, you can use the <a href="#clearing-all-events--clear-event">clear event</a> command.
</div>

Examples:

- `list event` followed by `delete event 2` deletes the 2nd event in the TalentHub.

- `find event Oscars` followed by `delete event 1` deletes the 1st event in the results of the `find event` command.

<figure style="text-align: center;">
  <img src="images/delete_event.png" alt="result for 'delete event'" style="display: block; margin: 0 auto; border-radius: 10px;">
  <figcaption>command: <code>delete event 4</code></figcaption>
</figure>

### Clearing all events : `clear event`

This command allows you to clear all event entries from the TalentHub after receiving confirmation from you!

Format: `clear event`

<div markdown="span" class="alert alert-warning">:warning: **Caution!** <br>
Running this command will clear **all** events in TalentHub. This action is irreversible after confirmation.
</div>

Example:

<figure style="text-align: center;">
  <img src="images/clear_event_confirmation.png" alt="clear person confirmation" style="display: block; margin: 0 auto; border-radius: 10px;">
  <figcaption>command: <code>clear event</code></figcaption>
</figure>

<figure style="text-align: center;">
  <img src="images/pending_cancelled.png" alt="clear event cancelled" style="display: block; margin: 0 auto; border-radius: 10px;">
  <figcaption>Typing <code>N</code> or <code>n</code> and pressing enter would return the following message.</figcaption>
</figure>

<figure style="text-align: center;">
  <img src="images/clear_event.png" alt="clear person" style="display: block; margin: 0 auto; border-radius: 10px;">
  <figcaption>Typing <code>Y</code> or <code>y</code> and pressing enter would return the following message.</figcaption>
</figure>

---

## Utility Commands

### Clearing all entries : `clear all`

This command allows you to clear all entries from TalentHub!

<div markdown="span" class="alert alert-warning">:warning: **Caution!** <br>
Running this command will clear **all** persons and events in TalentHub. This action is irreversible after confirmation.
</div>

Format: `clear all`

Example:

<figure style="text-align: center;">
  <img src="images/clear_all_confirmation.png" alt="clear all confirmation" style="display: block; margin: 0 auto; border-radius: 10px;">
  <figcaption>command: <code>clear all</code></figcaption>
</figure>

<figure style="text-align: center;">
  <img src="images/pending_cancelled.png" alt="clear all cancelled" style="display: block; margin: 0 auto; border-radius: 10px;">
  <figcaption>Typing <code>N</code> or <code>n</code> and pressing enter would return the following message.</figcaption>
</figure>

<figure style="text-align: center;">
  <img src="images/clear_all.png" alt="clear person" style="display: block; margin: 0 auto; border-radius: 10px;">
  <figcaption>Typing <code>Y</code> or <code>y</code> and pressing enter would return the following message.</figcaption>
</figure>

### Exiting the program : `exit`

This command allows you to exit TalentHub!

Format: `exit`

### More information on Commands : `help`

This command provides you with a link to the application's user guide in a new window. This link can be copied using the copy URL button to access
the user guide online!

Format: `help`

Example:

<figure style="text-align: center;">
  <img src="images/help_popup.png" alt="help" style="display: block; margin: 0 auto; border-radius: 10px;">
  <figcaption>command: <code>help</code></figcaption>
</figure>

---

## Saving the data

The data that you store into TalentHub is saved in the hard disk automatically after any command that changes the data. There is no need for you to save your data manually!

---

## Editing the data file

Your data is saved automatically as a JSON file `[JAR file location]/data/talenthub.json`! If you are experienced with handling such files, you are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-danger">:exclamation: **Warning!**<br>
If your changes to the data file makes its format invalid, TalentHub will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br><br>
Furthermore, certain edits can cause TalentHub to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

---

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the data file (talent.json) it creates with the file that contains the data of your previous TalentHub home folder.

**Q**: How do I add multiple tags to a contact?<br>
**A**: When adding a person, use t/TAG multiple times. For example, `add person n/John Doe p/98765432 t/CameraMan t/Producer` will add "CameraMan" and "Producer" as tags.

**Q**: How can I delete an optional field in Person or Event I have added?<br>
**A**: You can delete the field using the `edit` command by specifying a blank prefix for the field you want to delete. For example, `edit event v/` deletes the venue field.

**Q**: How do I undo a mistake?<br>
**A**: An update to provide an undo feature is currently in progress.

---

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **When editing Person or Event name in View mode**, the Person or Event being viewed will be exited and a blank screen will be displayed. The remedy is to use the `view` command to view the new Contact or Event name.
3. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.
4. **When using the `add person` and `add event` commands**, if you enter an empty prefix for tags and contacts to set as no tags or points of contacts (e.g., `t/` or `p/`), the application will not recognize the empty prefix. The remedy is to remove the prefix entirely if you are not adding any tags or points of contacts.
5. **When inputting indexes for commands**, if you enter a large integer (greater than 2147483647), the application will not recognize the index as it is too large.

---

## Command summary

### Managing Contacts

| Action     | Format                                                                                 | Examples                                                                                             |
| ---------- |----------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------|
| **Add**    | `add person n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br>                 | e.g., `add person n/Jake Doe p/98765432 e/jaked@example.com a/123, Clementi Rd, 1234665 t/Celebrity` |
| **Delete** | `delete person INDEX`<br>                                                              | e.g., `delete person 3`                                                                              |
| **Edit**   | `edit person INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br>      | e.g., `edit person 1 n/James Lee e/jameslee@example.com`                                             |
| **Filter** | `filter person TAG`<br>                                                                | e.g., `filter person Celebrity`                                                                      |
| **Find**   | `find person KEYWORD [MORE_KEYWORDS]`<br>                                              | e.g., `find person James Jake`                                                                       |
| **List**   | `list person`                                                                          |                                                                                                      |
| **View**   | `view person KEYWORD [MORE_KEYWORDS]`<br>                                              | e.g., `view person Jake Doe`                                                                         |

### Managing Events

| Action      | Format                                                                                         | Examples                                                                                                         |
|-------------|------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------|
| **Add**     | `add event n/NAME t/TIME [v/VENUE] c/CELEBRITY [p/POINTS OF CONTACT]…​`<br>                    | e.g., `add event n/Oscars t/2024-03-01 12:10 to 2024-03-01 18:30 v/Dolby Theatre c/Jim Bob p/John Doe, Jane Doe` |
| **Clear**   | `clear event`                                                                                  |                                                                                                                  |
| **Delete**  | `delete event INDEX`<br>                                                                       | e.g., `delete event 3`                                                                                           |
| **Edit**    | `edit event INDEX [n/NAME] [t/TIME] [v/VENUE] [c/CELEBRITY] [p/POINTS OF CONTACT]…​`<br>       | e.g., `edit event 1 t/2024-03-21 10:00 to 2024-03-21 12:30 v/Jane's Salon`                                       |
| **Filter**  | `filter event CELEBRITY_NAME`<br>                                                              | e.g., `filter event Jim Bob`                                                                                     |
| **Find**    | `find event KEYWORD [MORE_KEYWORDS]`<br>                                                       | e.g., `find event Hair Oscars`                                                                                   |
| **List**    | `list event`                                                                                   |                                                                                                                  |
| **View**    | `view event KEYWORD [MORE_KEYWORDS]`<br>                                                       | e.g., `view event Oscars`                                                                                        |


### Utility Commands

| Action    | Format      |
| --------- |-------------|
| **Clear** | `clear all` |
| **Exit**  | `exit`      |
| **Help**  | `help`      |
