---
layout: page
title: User Guide
---

## ConcertPhonebook

ConcertPhonebook is a **desktop app** for **Concert Organisers** to manage your Concert contacts, optimized for use via
a Command Line Interface (CLI) with also a Graphical User Interface (GUI) in place to assist with information display.
Manage your _persons_ and _concerts_ in the book, finding contact information faster than your regular GUI contact
management applications.

1. [Quick start](#quick-start)
2. [Features](#features)
   1. [Viewing help](#viewing-help--help)
   2. [Listing all persons](#listing-all-persons--listp)
   3. [Listing all concerts](#listing-all-concerts--listc)
   4. [Listing all persons and concerts](#listing-all-persons-and-concerts--list)
   5. [Listing all concert contacts](#listing-all-concert-contacts--listcc)
   6. [Adding a person](#adding-a-person-addp)
   7. [Adding a concert](#adding-a-concert-addc)
   8. [Adding a concert contact](#adding-a-concert-contact-addcc)
   9. [Deleting a person](#deleting-a-person--deletep)
   10. [Deleting a concert](#deleting-a-concert--deletec)
   11. [Deleting a concert contact](#deleting-a-concert-contact--deletecc)
   12. [Clearing All Entries](#clearing-all-entries--clear)
   13. [Finding a person](#finding-a-person-findp)
   14. [Finding a concert](#finding-a-concert-findc)
   15. [Finding a concert contact](#finding-a-concert-contact-findcc)
   16. [Editing a person](#editing-a-person--editp)
   17. [Editing a concert](#editing-a-concert--editc)
   18. [Exiting program](#exiting-the-program--exit)
3. [FAQ](#faq)
4. [Known Issues](#known-issues)
5. [Command Summary](#command-summary)

---

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

2. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-F11-1/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your ConcertAddressBook.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar concertphonebook.jar`
   command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will
   open the help window.<br>
   Some example commands you can try:

   - `help` : Shows a message explaining how to access the help page.

   - `listp` : Lists all persons in the ConcertPhonebook.

   - `listc` : Lists all concerts in the ConcertPhonebook.

   - `list` : Lists all persons and concerts in the ConcertPhonebook.

   - `listcc` : Lists all concert contacts in the ConcertPhonebook.

   - `addp n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 r/organiser t/friends` : Adds a
     person named `John Doe` to the ConcertPhonebook.

   - `addc n/Coachella a/81800 51st Ave, Indio, Southern California, United States d/2024-12-20 1010` : Adds a concert
     named `Coachella` to the ConcertPhonebook.

   - `addcc pi/1 ci/1` : Links the 1st person to the 1st concert.

   - `deletep 1` : Deletes the 1st person shown in the current person list.

   - `deletec 1` : Deletes the 1st concert shown in the current concert list.

   - `deletecc 1` : Deletes the 1st concert contact shown in the current concert contact list.

   - `clear` : Deletes all entries from ConcertPhonebook.

   - `findp n/alice bob charlie r/organiser` : Finds person(s) named either `Alice`, `Bob` or `Charlie` with
     an `organiser` role from the ConcertPhonebook.

   - `findc n/coachella glastonbury` : Finds concert(s) named either `Coachella` or `Glastonbury` from the
     ConcertPhonebook.

   - `findcc pi/1 ci/1` : Finds the concert contact between the 1st person and 1st concert.

   - `editp 1 n/John Doe` : Edits the name of the 1st person shown in the current person list to `John Doe`.

   - `editc 1 n/Coachella` : Edits the name of the 1st concert shown in the current concert list to `Coachella`.

   - `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

---

## Features

<div markdown="block" class="alert alert-info">

**:information_source: General Notes on Commands:**<br>

- Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `addp n/NAME`, `NAME` is a parameter which can be used as `addp n/John Doe`.

- Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

- Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

- Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

- Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be
  ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

- If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines
  as space characters surrounding line-breaks may be omitted when copied over to the application.

- Only alphanumeric characters, spaces and the terms <code>&nbsp;s/o&nbsp;</code> and <code>&nbsp;d/o&nbsp;</code> (case
  insensitive) are allowed. <br>
  e.g. `n/John Doe s/o Alexis` or `n/John Doe d/O Jane Doe`

- Only english characters are allowed in commands, other languages may lead to unpredictable behaviour.

- `TAG` is limited to about 45 characters (when the GUI is at the minimum allowed size), any longer may lead to
  truncated view of the `TAG`. It is recommended to keep within these bounds for the best experience.

- `TAG` only allows single words and does not allow duplicates.

- The application's view will switch to persons and concerts when commands involving concerts or persons are executed.

- The application's view will switch to concert contacts when commands involving concert contacts are executed.

- In this user guide, the terms _contact_ and _person_ are used interchangeably.

- The term _concert contact_ refers to an association between a _person_ and a _concert_.

</div>

---

### Viewing help : `help`

Shows a message explaining how to access the help page (User Guide).

![help message](images/helpMessage.png)

Format: `help`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**

`F1` key can be used to display the help message. <br>

</div>

### Listing all persons : `listp`

Shows a list of all persons in the ConcertPhonebook.

Format: `listp`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**

`FIND` commands can be used to display any truncated information. <br>

</div>

### Listing all concerts : `listc`

Shows a list of all concerts in the ConcertPhonebook.

Format: `listc`

### Listing all persons and concerts : `list`

Shows a list of all persons and concerts in the ConcertPhonebook.

Format: `list`

### Listing all concert contacts : `listcc`

Shows a list of all concert contacts in the ConcertPhonebook.

Format: `listcc`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**

`F2` key can be used to toggle the concert contact view. <br>

</div>

### Adding a person: `addp`

Adds a person to the ConcertPhonebook.

Format: `addp n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS r/ROLE [t/TAG]…​`

- `ROLE` Roles that can be assigned are: STAGE_MANAGER, SOUND_TECHNICIAN, ORGANISER,
  ARTIST, PROMOTER, VENUE_COORDINATOR, SECURITY, LOGISTICS, MEDIA.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:

- `addp n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 r/Organiser`
- `addp n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison r/Artist p/1234567 t/criminal`

### Adding a concert: `addc`

Adds a concert to the ConcertPhonebook.

Format: `addc n/NAME a/ADDRESS d/DATE`

- Date must be in the 'YYYY-MM-DD hhmm' format e.g `d/2025-01-21 1010`
- **FYI**: If user were to add a date that does not exist where the day is less than 32, closest date in the same month
  will be added
  e.g. `2024-04-31 1159` will be stored as `2024-04-30 1159`

Examples:

- `addc n/Coachella a/81800 51st Ave, Indio, Southern California, United States d/2024-12-20 1010`

### Adding a concert contact: `addcc`

Adds an association between a person and concert, to the ConcertPhonebook.

Format: `addcc pi/PERSON_INDEX ci/CONCERT_INDEX`

- Adds an association between the person at the specified `PERSON_INDEX` to the concert at the specified `CONCERT_INDEX`
- The index refers to the index number shown in the displayed person / concert list. The index **must be a positive
  integer** 1, 2, 3, …​

### Deleting a person : `deletep`

Deletes the specified person from the ConcertPhonebook.

Format: `deletep INDEX`

- Deletes the person at the specified `INDEX`.
- The index refers to the index number shown in the displayed person list.
- The index **must be a positive integer** 1, 2, 3, …​

Examples:

- `list` followed by `deletep 2` deletes the 2nd person in the ConcertPhonebook.
- `findp n/Betsy` followed by `deletep 1` deletes the 1st person in the results of the `find` command.

### Deleting a concert : `deletec`

Deletes the specified concert from the ConcertPhonebook.

Format: `deletec INDEX`

- Deletes the concert at the specified `INDEX`.
- The index refers to the index number shown in the displayed concert list.
- The index **must be a positive integer** 1, 2, 3, …​

### Deleting a concert contact : `deletecc`

Deletes the specified concert contact from the ConcertPhonebook.

Format: `deletecc INDEX`

- Deletes the concert contact at the specified `INDEX`
- The index refers to the index number shown in the displayed concert contacts list.
- The index **must be a positive integer** 1, 2, 3, …​

### Clearing all entries : `clear`

Clears all entries from the ConcertPhonebook.

<div markdown="span" class="alert alert-warning">:warning: Warning: This action is not reversible.
</div>

Format: `clear`

### Finding a person: `findp`

Finds persons whose names contain any of the given keywords.

Format: `findp [n/NAME_KEYWORDS] [r/ROLE]`

- The search is case-insensitive. e.g `hans` will match `Hans`
- The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
- Only full words will be matched e.g. `Han` will not match `Hans`
- Persons matching both name and role from the command entry will be returned (i.e. `AND` search).
  e.g. `findp n/Hans Bo r/Artist` will return `Hans Gruber, Artist` and `Bo Yang, Artist`
- At least one of the 2 keywords must be present

Examples:

- `findp n/Alex Roy` finds person(s) with Alex or Roy in their names.
- `findp r/Artist` finds person(s) with a role of Artist.
- `findp n/Alex Roy r/Artist` finds person(s) with Alex or Roy in their names and has a role of Artist.

### Finding a concert: `findc`

Finds concerts whose names contain any of the given keywords.

Format: `findc n/NAME_KEYWORDS`

- The search is case-insensitive. e.g `coachella` will match `Coachella`
- The order of the keywords does not matter. e.g. `Coachella Glastonbury` will match `Glastonbury Coachella`
- Only full words will be matched e.g. `Coachell` will not match `Coachella`

Examples:

- `findc n/Coachella` finds concert(s) with Coachella in their names.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**

Use a combination of `findp`, `findc`, `listp` and `listc` to view specific persons and concerts.<br>

- `findp` and `listp` preserves the current displayed concert list.<br>
- `findc` and `listc` preserves the current displayed person list.<br>

</div>

### Finding a concert contact: `findcc`

Finds all concert contacts that are associated to the person and the concert at the specified index.

Format: `findcc [pi/PERSON_INDEX] [ci/CONCERT_INDEX]`

- The order of the keywords does not matter e.g. `findcc pi/1 ci/1` is equivalent to `findcc ci/1 pi/1`
- At least one of the 2 keywords must be present

Examples:

- `findcc pi/1` finds concert contact(s) associated with the first person in the phone book.
- `findcc ci/1` finds concert contact(s) associated with the first concert in the phone book.
- `findcc pi/1 ci/1` finds the concert contact associated with the first concert and the first person in the phone book.

### Editing a person : `editp`

Edits an existing person in the ConcertPhonebook.

Format: `editp INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [r/ROLE] [t/TAG]…​`

- Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list.
  The index **must be a positive integer** 1, 2, 3, …​
- At least one of the optional keywords must be provided.
- Existing values will be updated to the input values.
- When editing tags, the existing tags of the person will be **removed** i.e adding of tags is not cumulative.
- You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.

Examples:

- `editp 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567`
  and `johndoe@example.com` respectively.
- `editp 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Editing a concert : `editc`

Edits an existing concert in the ConcertPhonebook.

Format: `editc INDEX [n/NAME] [a/ADDRESS] [d/DATE]`

- Edits the concert at the specified `INDEX`. The index refers to the index number shown in the displayed concert list.
  The index **must be a positive integer** 1, 2, 3, …​
- At least one of the optional keywords must be provided.
- Existing values will be updated to the input values.
- Date must be in the 'YYYY-MM-DD hhmm' format e.g `d/2025-01-21 1010`
- FYI: If user were to add a date that does not exist, closest date in the same month will be added
  e.g. `2024-04-31 1159` will be stored as `2024-04-30 1159`

Examples:

- `editc 1 a/2 Stadium Drive d/2024-10-11 2200` Edits the address and date of the 1st concert to be `2 Stadium Drive`
  and `2024-10-11 2200` respectively.
- `editc 2 n/Rajini` Edits the name of the 2nd concert to be `Rajini`.

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

ConcertPhonebook data is saved in the hard disk automatically after any command is successfully executed. There is no need
to save manually.

### Editing the data file

ConcertPhonebook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users
are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, ConcertPhonebook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause ConcertPhonebook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

---

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer. Copy the data directory of your previous ConcertPhonebook home folder into the home folder of your new installation, and overwrite the data directory of the new installation if any.

---

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only
   the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the
   application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard
   shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy
   is to manually restore the minimized Help Window.
3. **Concert name constraints allow for s/o and d/o**, and the return message for invalid concert names states that s/o
   and d/o are allowed. This behavior was unintended, and will be fixed in later versions.

---

## Command summary

| Action                       | Format, Examples                                                                                                                                                             |
| ---------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Help**                     | `help`                                                                                                                                                                       |
| **List Person**              | `listp`                                                                                                                                                                      |
| **List Concerts**            | `listc`                                                                                                                                                                      |
| **List Person and Concerts** | `list`                                                                                                                                                                       |
| **List Concert Contacts**    | `listcc`                                                                                                                                                                     |
| **Add Person**               | `addp n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS r/ROLE [t/TAG]…​` <br> e.g., `add n/Alex Yeoh p/22224444 e/alexyeoh@example.com a/123, Clementi Rd, 1234665 r/Artist t/friend` |
| **Add Concert**              | `addc n/NAME a/ADDRESS d/DATE `<br> e.g. `addc n/Coachella a/81800 51st Ave, Indio, Southern California, United States d/2024-12-20 1010`                                    |
| **Add Concert Contact**      | `addcc pi/PERSON_INDEX ci/CONCERT_INDEX`<br> e.g. `addcc pi/1 ci/1`                                                                                                          |
| **Delete Person**            | `deletep INDEX`<br> e.g., `deletep 3`                                                                                                                                        |
| **Delete Concert**           | `deletec INDEX`<br> e.g., `deletec 3`                                                                                                                                        |
| **Delete Concert Contact**   | `deletecc INDEX`<br> e.g., `deletecc 1`                                                                                                                                      |
| **Clear**                    | `clear`                                                                                                                                                                      |
| **Find Person**              | `findp [n/NAME_KEYWORDS] [r/ROLE]`<br> e.g., `findp n/James Jake r/organiser`                                                                                                |
| **Find Concert**             | `findc [n/NAME_KEYWORDS]`<br> e.g., `findc n/Coachella Glastonbury`                                                                                                          |
| **Find Concert Contact**     | `findcc [pi/PERSON_INDEX] [ci/CONCERT_INDEX]`<br> e.g., `findcc pi/1 ci/1`                                                                                                   |
| **Edit Person**              | `editp INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                                 |
| **Edit Concert**             | `editc INDEX [n/NAME] [a/ADDRESS] [d/DATE]`<br> e.g.,`editc 1 a/2 Stadium Drive d/2024-10-11 2200`                                                                           |
| **Exit**                     | `exit`                                                                                                                                                                       |
