---
layout: default.md
title: "User Guide"
pageNav: 3
---

# Bridal Boss User Guide

Bridal Boss is a **desktop app for managing contacts, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, Bridal Boss can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
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

### General Command Format

- **Parameters** in `UPPER_CASE` are to be supplied by the user.
    - e.g., `add n/NAME` can be used as `add n/John Doe`.
- **Optional parameters** are enclosed in square brackets `[]`.
    - e.g., `n/NAME [r/ROLE]` can be `n/John Doe r/florist` or `n/John Doe`.
- **Multiple entries** for a parameter are indicated by `...` after the parameter.
    - e.g., `[w/WEDDING_INDEX]...` can be omitted or used multiple times: `w/1`, `w/1 w/2`, etc.
- **Order of parameters** is flexible.
    - e.g., `n/NAME p/PHONE_NUMBER` is the same as `p/PHONE_NUMBER n/NAME`.
- **Extraneous parameters** for commands without parameters (e.g., `help`, `list`) are ignored.
    - e.g., `help 123` is interpreted as `help`.
- **Client parameter (`c/`)** in wedding commands accepts either an index or a name.
    - e.g., `c/1` or `c/John Doe`.
- **Dates** must be in `YYYY-MM-DD` format.
    - e.g., `d/2024-12-31`.
- **Roles (`r/`)** must be single-word alphanumeric.
    - e.g., `r/photographer` (valid), `r/wedding planner` (invalid).
- **Copying commands**: Be cautious when copying multi-line commands, as line-breaks may affect spacing.

### Validation Rules

#### Names

- **Allowed characters**: Alphabets, spaces, apostrophes, hyphens.
- **Restrictions**: Cannot be blank; max 70 characters.
- **Examples**: `John Doe`, `Mary-Jane`, `O'Connor`.

#### Phone Numbers

- **Format**: Starts with 8 or 9; exactly 8 digits; numbers only.
- **Uniqueness**: Each number must be unique.
- **Examples**: `91234567`, `82345678`.

#### Email Addresses

- **Format**: `local-part@domain`.
- **Local-part**: Alphanumeric and `+ _ . -`; cannot start/end with special characters.
- **Domain**: At least two characters; cannot start/end with hyphens.
- **Uniqueness**: Each email must be unique.
- **Examples**: `john@example.com`, `user.name+tag@domain.com`.

#### Roles

- **Format**: Single-word alphanumeric; no spaces or special characters.
- **Case-insensitive** for matching.
- **Examples**: `photographer`, `florist`, `coordinator`.

#### Wedding Fields

- **Wedding Name**: Cannot be blank.
- **Date**: `YYYY-MM-DD` format.
- **Venue**: Cannot be blank or whitespace if provided.
- **Client**: One wedding per client.

#### Addresses

- **Restrictions**: Cannot be blank; any characters except leading/trailing spaces.

### Index vs. Name-based Commands

Commands like `edit`, `delete`, `view`, and `assign` support both index and name-based formats.

- **Index Format**: Uses list position number.
    - e.g., `edit 1`, `delete 1`.
- **Name Format**: Uses the person's name.
    - e.g., `edit John Doe`, `delete John`.

**Using Name-based Commands**:

- **Case-insensitive** matching.
- **Full name matching**: Searches for names containing the entire keyword.
- **Single Match**: Command executes immediately.
- **Multiple Matches**:
    - System displays matching entries with indices.
    - User must re-enter the command using the index. ***INSERT PICTURE***
    - **Example**:
      ```
      > delete John
      Multiple matches found:
      1. John Doe
      2. Johnny Lee
      3. John Smith
      Please specify the index of the contact you want to delete.
  
      > delete 1
      Deleted Person: John Doe
      ```
- **No Matches**: Displays "No matches found" message.

### Cross-Reference Validations

- **Client-Wedding Relationship**:
    - One wedding per client.
    - Cannot delete a client with an active wedding.
    - Cannot assign a client as a vendor to their own wedding.
- **Person-Wedding Relationships**:
    - A person can be a vendor for multiple weddings.
    - Cannot assign the same person to the same wedding multiple times.
    - Vendor assignments are removed when a wedding is deleted.
- **Role-Person Relationship**:
    - One role per person.
    - New role assignments replace existing roles.

---

### Viewing Help: `help`

Displays instructions on accessing the help page.

Format: `help`

---

### Adding a Person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [r/ROLE] [w/WEDDING_INDEX]...`

- **Roles**: Optional; one role per person.
- **Weddings**: Optional; can assign to multiple weddings.
- **Validation**: Refer to [Validation Rules](#validation-rules).

**Examples**:

- `add n/John Doe p/98765432 e/johnd@example.com a/123 Street`
- `add n/Betsy Crowe p/91234567 e/betsycrowe@example.com a/Tanglin Mall r/florist`
- `add n/Betsy Crowe p/91234567 e/betsycrowe@example.com a/Tanglin Mall w/1`

**Common Errors**:

- Duplicate phone: "This number already exists in the address book."
- Duplicate email: "This email already exists in the address book."
- Duplicate contact: "This contact already exists in the address book."
- Invalid wedding index: "Wedding [index] is not in the list."

---

### Listing All Persons: `list`

Displays all persons in the address book.

Format: `list`

---

### Editing a Person: `edit`

Edits an existing person's details.

Format:

- **By Index**: `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]`
- **By Name**: `edit NAME [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]`

- **At least one field** must be provided.
- **Existing values** are updated.
- **Name-based**:
    - If multiple matches, system displays options; re-enter command with index.

**Examples**:

- `edit 1 p/91234567 e/johndoe@example.com`
- `edit John Doe n/John Smith`
- If multiple "Alex", `edit Alex n/Alex Tan` will prompt for index.

---

### Finding Persons by Name: `find`

Searches for persons whose names contain the keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

- **Case-insensitive**.
- **Full-word matching**.
- **Returns** persons matching any keyword.

**Examples**:

- `find John` returns `John`, `John Doe`.
- `find alex david` returns `Alex Yeoh`, `David Li`.

---

### Viewing a Contact: `view`

Displays details of a specified person.

Format: `view NAME`

- **Case-insensitive** matching.
- **Multiple Matches**: Displays options; re-enter command with index.

**Examples**:

- `view John` shows details for `John` or prompts if multiple.
- `view Alex Yeo` shows details for `Alex Yeo`.

**Displays**:

- Personal details.
- Current role.
- Own wedding (if client).
- Assigned weddings as vendor.
- Related contacts through shared weddings.

---

### Deleting a Person: `delete`

Removes a person from the address book.

Format:

- **By Index**: `delete INDEX`
- **By Name**: `delete NAME`

- **Cannot delete** a client with an active wedding.
- **Name-based**:
    - Multiple matches prompt for index.

**Examples**:

- `delete 2` removes the second person.
- `delete Betsy` removes Betsy if only one match.

---

### Clearing All Entries: `clear`

Removes all entries from the address book.

Format: `clear`

---

### Filtering Persons: `filter`

Lists persons matching specified fields.

Format: `filter [n/NAME] [r/ROLE] [e/EMAIL] [p/PHONE] [a/ADDRESS]`

- **At least one field** must be provided.
- **Case-insensitive**.
- **Field-specific matching**:
    - **Name**: Exact full-word match.
    - **Role**: Exact match.
    - **Email/Address**: Partial match.
    - **Phone**: Exact number match.
- **Returns** persons matching any field.

**Examples**:

- `filter n/John` returns persons named `John`.
- `filter r/vendor` returns all vendors.
- `filter e/gmail a/jurong` returns persons with "gmail" in email or "jurong" in address.

---

### Managing Weddings

#### Adding a Wedding: `addw`

Adds a wedding to the address book.

Format: `addw n/WEDDING_NAME c/CLIENT [d/DATE] [v/VENUE]`

- **Client**: Specified by index or name.
- **Date**: Optional; `YYYY-MM-DD`.
- **Venue**: Optional; cannot be blank if provided.
- **Client Restrictions**: One wedding per client.

**Examples**:

- `addw n/Beach Wedding c/1 d/2024-12-31 v/Sentosa Beach`
- `addw n/Garden Wedding c/John Doe v/Botanical Gardens`

**Notes**:

- **Duplicate Client**: Error if client already has a wedding.
- **Multiple Matches**: Client name prompts for index.

---

#### Editing a Wedding: `editw`

Modifies wedding details.

Format: `editw w/INDEX [n/NAME] [d/DATE] [v/VENUE]`

- **At least one field** must be provided.
- **Client cannot be changed** after creation.

**Examples**:

- `editw w/1 n/Sunset Wedding`
- `editw w/2 d/2025-01-01 v/Grand Hotel`

---

#### Viewing Wedding Details: `vieww`

Displays wedding details.

Format:

- **By Index**: `vieww INDEX`
- **By Keyword**: `vieww KEYWORD`

- **Keyword**: Can be wedding name or client name.
- **Multiple Matches**: Prompts for index.

**Examples**:

- `vieww 1` shows the first wedding.
- `vieww John` shows John's wedding if unique.

---

#### Deleting a Wedding: `deletew`

Removes a wedding.

Format:

- **By Index**: `deletew INDEX`
- **By Keyword**: `deletew KEYWORD`

- **Removes** client relationship and vendor assignments.

**Examples**:

- `deletew 2` deletes the second wedding.
- `deletew Beach Wedding` deletes if unique match.

---

### Assigning a Person: `assign`

Assigns a role and/or weddings to a person.

Format:

- **By Index**: `assign INDEX [r/ROLE] [w/WEDDING_INDEX]...`
- **By Name**: `assign NAME [r/ROLE] [w/WEDDING_INDEX]...`

- **At least one field** must be provided.
- **Role Assignment**:
    - Replaces existing role.
    - Must be non-blank, single-word alphanumeric.
- **Wedding Assignment**:
    - Assigns person to one or more weddings.
    - Cannot assign to the same wedding multiple times.
- **Restrictions**:
    - Cannot assign a client to their own wedding.

**Examples**:

- `assign 1 r/florist`
- `assign John Doe w/1 w/2`
- `assign 2 r/vendor w/1 w/2`

---

### Exiting the Program: `exit`

Closes the application.

Format: `exit`

---

### Saving the Data

Data is automatically saved after any changes. No manual saving required.

---

### Editing the Data File

Data is stored as a JSON file at `[JAR file location]/data/addressbook.json`.

**Caution**:

- **Backup** before editing.
- **Invalid formats** may cause data loss.
- **Out-of-range values** may cause unexpected behavior.

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

**Q**: How do I add a wedding for an existing client?<br>
**A**: First use `list` to see all contacts. Then use either `addw n/WEDDING_NAME c/INDEX` using the client's index number, or `addw n/WEDDING_NAME c/CLIENT_NAME` using the client's name.

**Q**: What happens if I try to delete a client who has a wedding?<br>
**A**: The system will prevent you from deleting the client and show an error message. You must first delete the client's wedding before deleting the contact.

**Q**: Can I change a wedding's client after creation?<br>
**A**: No, a wedding's client cannot be changed after creation. You would need to create a new wedding for the different client.

**Q**: Can a client have multiple weddings?<br>
**A**: No, each client can only have one wedding at a time.

**Q**: Can I assign multiple roles to a person?<br>
**A**: No, each person can only have one role at a time. Assigning a new role will replace the existing one.

**Q**: What happens when I delete a wedding?<br>
**A**: Deleting a wedding will remove all vendor assignments to that wedding and remove the client-wedding relationship. The contacts themselves are not deleted.

**Q**: Can I use the same phone number or email for different contacts?<br>
**A**: No, phone numbers and email addresses must be unique in the system. You'll receive an error message if you try to add or edit a contact with duplicate information.

**Q**: What happens if I find multiple contacts with the same name?<br>
**A**: When using name-based commands, if multiple matches are found, the system will show you a list of matching contacts with their indices. You'll need to use the index number to specify which contact you want to work with.

**Q**: How can I see all weddings a vendor is assigned to?<br>
**A**: Use the `view` command with the vendor's name or index. The system will show all weddings they are assigned to as part of their contact details.

**Q**: Can I search for contacts by partial name match?<br>
**A**: Yes, use the `find` command which matches partial names. However, note that it matches whole words only (e.g., "John" will match "John Doe" but not "Johnny").

**Q**: What's the difference between `find` and `filter` commands?<br>
**A**: `find` searches only names and supports partial word matches. `filter` can search across multiple fields (name, role, email, phone, address) but requires exact word matches for names and roles.

**Q**: How do I remove a role from a contact?<br>
**A**: Currently, roles cannot be removed once assigned. You can only change them to a different role using the `assign` command.

**Q**: What happens to wedding assignments if I edit a contact's details?<br>
**A**: Editing a contact's basic details (name, phone, email, address) does not affect their wedding assignments or role. These relationships remain intact.

**Q**: Can I export my contact and wedding data?<br>
**A**: While there's no direct export command, you can copy the data file (addressbook.json) which contains all your data. This file is located in the same folder as the application.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [r/ROLE] [w/WEDDING_INDEX]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 r/florist w/1 w/2`
**Clear**  | `clear`
**Delete** | #1: `delete INDEX` or <br> #2: `delete NAME`<br> e.g., `delete 1`, `delete Alex`, `delete Alex Tan`
**Edit**   | #1: `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]` or <br> #2: `edit NAME [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`, `edit James n/James Lee e/jameslee@example.com`
**View**   | `view NAME`<br> e.g., `view Alex`, `view Alex Tan`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Filter** | `filter [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [r/ROLE]`<br> e.g., `filter r/friends`
**View**   | `view KEYWORD`<br> e.g., `view Alex`, `view Alex Tan`
**List**   | `list`
**Addw**   | `addw n/WEDDING_NAME c/CLIENT [d/DATE] [v/VENUE]` <br> e.g., `addw n/Beach Wedding c/1 d/2024-12-31 v/Sentosa Beach`
**Editw**  | `editw w/INDEX [n/NAME] [d/DATE] [v/VENUE]`<br> e.g., `editw w/1 d/2024-12-31 v/Garden Venue`
**Vieww**  | `vieww INDEX` or `vieww KEYWORD`<br> e.g., `vieww 1`, `vieww John`
**Deletew**| #1: `deletew INDEX` or <br> #2: `deletew KEYWORD`<br> e.g., `deletew 1`, `deletew Beach Wedding`
**Assign** | `assign INDEX r/ROLE w/WEDDING...` or `assign NAME r/ROLE w/WEDDING...`<br> e.g., `assign 1 r/vendor`, `assign John Doe r/photographer w/2`
**Help**   | `help`
**Exit**   | `exit`
