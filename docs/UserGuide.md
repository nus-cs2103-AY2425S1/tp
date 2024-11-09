<!--
layout: default.md
title: "User Guide"
pageNav: 3

---

-->

# HallPointer User Guide

HallPointer is a **dedicated desktop app designed specifically for CCA leaders in hall environments** to efficiently manage and track members' information with ease and accuracy. Unlike general-purpose applications like Excel, HallPointer offers a streamlined, single-step platform that is optimized for quick command-based input and offers a more intuitive experience through its integration of both CLI and GUI elements.

With HallPointer, you can easily track member details such as names, Telegram handles, room numbers, points earned, and session attendance, all in one organized space. The app maintains built-in logic for tracking and updating member information, reducing the risk of manual errors and eliminating the need for complex formulas or repetitive data entry. By automating key management tasks and ensuring data consistency, HallPointer frees up time for CCA leaders, allowing them to focus on what truly matters—building a vibrant and engaged community.

If you’re a CCA leader who values speed, organization, and reliability, HallPointer is the perfect tool to simplify your workflow and enhance your member tracking processes.

---

## Table of Contents

- [Quick start](#quick-start)
- [Features](#features)
  - [Viewing help : `help`](#viewing-help--help)
  - [Adding a member: `add_member`](#adding-a-member-add_member)
  - [Listing all members : `list`](#listing-all-members--list)
  - [Updating a member : `update_member`](#updating-a-member--update_member)
  - [Locating members by name: `find_members`](#locating-members-by-name-find_members)
  - [Deleting a member : `delete_member`](#deleting-a-member--delete_member)
  - [Adding a Session: `add_session`](#adding-a-session-add_session)
  - [Locating members with associated sessions by name: `find_sessions`](#locating-members-with-associated-sessions-by-name-find_sessions)
  - [Deleting a Session: `delete_session`](#deleting-a-session-delete_session)
  - [Clearing all entries : `clear`](#clearing-all-entries--clear)
  - [Exiting the program : `exit`](#exiting-the-program--exit)
  - [Saving the data](#saving-the-data)
  - [Editing the data file](#editing-the-data-file)
  - [Archiving data files `[coming in v2.0]`](#archiving-data-files-coming-in-v20)
- [FAQ](#faq)
- [Known issues](#known-issues)
- [Command summary](#command-summary)

---

## Quick start

1. Ensure you have Java `17` or above installed on your computer.

2. Download the latest `.jar` file from [here](https://github.com/ay2425s1-cs2103t-w14-3/tp/releases/latest).

3. Copy the file to the folder you want to use as the _home folder_ for your data.

4. Open a command terminal, `cd` into the folder where you placed the jar file, and use the command `java -jar hallpointer.jar` to run the application.<br>
   A GUI similar to the one below should appear in a few seconds. Note that the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type commands in the command box and press Enter to execute them. For example, typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   - `list` : Lists all members.
   - `add_member n/John Doe r/4-3-301 t/johndoe123 tag/logistics` : Adds a member named `John Doe` to HallPointer.
   - `delete_member 3` : Deletes the 3rd member shown in the current list.
   - `clear` : Deletes all members.
   - `exit` : Exits the app.

6. Refer to the [Features](#features) section below for details on each command.

---

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

- Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g., in `add_member n/NAME`, `NAME` is a parameter that can be used as `add_member n/John Doe`.
- Items in square brackets are optional.<br>
  e.g., `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.
- Items with `…`​ after them can be used multiple times, including zero times.<br>
  e.g., `[t/TAG]…​` can be used as ` ` (i.e., 0 times), `t/friend`, `t/friend t/family`, etc.
- Parameters can be in any order.<br>
  e.g., if the command specifies `n/NAME tg/TELEGRAM`, `tg/TELEGRAM n/NAME` is also acceptable.
- Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit`, and `clear`) will be ignored.<br>
  e.g., if the command specifies `help 123`, it will be interpreted as `help`.
- If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines, as space characters surrounding line-breaks may be omitted when copied over to the application.

</box>

---

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

**Format:** `help`

---

### Adding a member: `add_member`

Adds a member to Hall Pointer.

**Format:** `add_member n/NAME r/BLOCK/FLOOR/ROOM_NUMBER t/TELEGRAM_HANDLE [tag/TAG]…​​`

<box type="tip" seamless>

**Tip:** A member can have any number of tags (including 0).

</box>

<box type="warning" seamless>

**Constraints:**<br>

- **Unique Name**: Each member must have a unique name. This is necessary to prevent confusion between members and to ensure accurate tracking.
- **Unique Telegram Handle**: Each member must have a unique Telegram handle, as this is a personal identifier for each user.
- **Shared Rooms Allowed**: Multiple members can be assigned to the same room to accommodate shared living arrangements.

</box>

**Examples:**

- `add_member n/John Doe r/4-3-301 t/johndoe123`
- `add_member n/Betsy Crowe s/o Alice Crowe r/2-5-120 t/betsy_crowe tag/logistics`

---

### Listing all members : `list`

Shows a list of all members in Hall Pointer.

**Format:** `list`

---

### Updating a member : `update_member`

Updates an existing member in Hall Pointer.

**Format:** `update_member INDEX [n/NAME] [r/BLOCK-FLOOR-ROOM_NUMBER] [t/TELEGRAM_HANDLE] [tag/TAG]…​​`

<box type="tip" seamless>

**Tip:** At least one of the optional fields must be provided. Existing values will be updated to the input values.

</box>

- Updates the member at the specified `INDEX`. The index refers to the index number shown in the displayed member list. The index **must be a positive integer** 1, 2, 3, …​.
- When updating tags, the existing tags of the member will be removed; i.e., adding of tags is not cumulative.
- You can remove all the member’s tags by typing `tag/` without specifying any tags after it.

**Examples:**

- `update_member 1 t/johndoe123_updated n/Johnson Doe` updates the Telegram handle and name of the 1st member to be `johndoe123_updated` and `Johnson Doe`, respectively.
- `update_member 2 n/Betsy Crower tag/` updates the name of the 2nd member to be `Betsy Crower` and clears all existing tags.

---

### Locating members by name: `find_members`

Finds members whose names contain any of the given keywords.

**Format:** `find_members KEYWORD [MORE_KEYWORDS]`

- **Case-Insensitive Search**: The search is case-insensitive, so `hans` will match `Hans`.
- **Order of Keywords**: The order of the keywords does not matter, so `Hans Bo` will match both `Bo Hans` and `Hans Bo`.
- **Full Word Matching**: Only full words will be matched, so `Han` will not match `Hans`.
- **OR Search**: Members matching at least one keyword will be returned, using an OR search. For example, `Hans Bo` will return both `Hans Gruber` and `Bo Yang`.

<box type="info" seamless>

**Note:** The `find_members` command does not stack filters. Each use of `find_members` filters the **original full list** rather than further filtering any previously filtered list. If you need more specific results, combine keywords in a single `find_members` command.

</box>

**Examples:**

- `find_members John` returns `john` and `John Doe`
- `find_members alex david` returns `Alex Yeoh` and `David Li`<br>
  ![result for 'find_members alex david'](images/findAlexDavidResult.png)

---

### Deleting a member : `delete_member`

Deletes the specified member from Hall Pointer.

**Format:** `delete_member INDEX`

<box type="warning" seamless>

**Note:** The index refers to the index number shown in the displayed member list and **must be a positive integer** (1, 2, 3, …​).

</box>

**Examples:**

- `list_members` followed by `delete_member 2` deletes the 2nd member in Hall Pointer.
- `find_members Betsy` followed by `delete_member 1` deletes the 1st member in the results of the `find` command.

---

### Adding a Session: `add_session`

Adds a session to Hall Pointer and associates it with specified members.

**Format:** `add_session s/NAME d/DATE p/POINTS m/INDEX...`

<box type="tip" seamless>

**Tip:** **Points** should be an integer between 0 and 100. A maximum of 100 points can be awarded to any session.

</box>

<box type="warning" seamless>

**Constraints:**<br>

- **Unique Session Name**: Each session name must be unique. This ensures that each session is distinct and prevents duplicate records. If you have multiple similar sessions, consider naming them sequentially (e.g., "Rehearsal 1", "Rehearsal 2").

</box>

**Examples:**

- `list_members` followed by `add_session s/Rehearsal d/24 Oct 2024 p/2 m/1 m/3` Adds a session named "Rehearsal" on 24 Oct 2024 worth 2 points, associated with the members at indexes 1 and 3 in the displayed list.

---

### Locating members with associated sessions by name: `find_sessions`

This command finds members who have attended sessions with names that contain any of the specified keywords.

**Format:** `find_sessions KEYWORD [MORE_KEYWORDS]`

<box type="tip" seamless>

- **Case-Insensitive Search:** The search is case-insensitive. For example, `meeting` will match `Meeting`.
- **Order of Keywords:** The order of keywords does not affect the search results. For example, `AGM meeting` will match sessions with names containing either `AGM` or `meeting`.
- **Full Word Matching:** Only full words are matched; e.g., `team` will not match `tea`.
- **OR Search:** Members with sessions that match at least one keyword will be returned. For example, searching `AGM meeting` will return members with sessions named `AGM meeting` or `team meeting`.
- **Independence from Displayed Members:** The search includes all members in HallPointer, not just those currently displayed.

</box>

**Examples:**

- `find_sessions Team` – Returns any member associated with sessions named "Team meeting," "Team bonding," etc.
- `find_sessions AGM meeting` – Returns members associated with sessions such as "AGM meeting" or "team meeting."

  ![result for 'find_sessions team'](images/findTeamResult.png)

---

### Deleting a Session: `delete_session`

Deletes a session associated with one or more members in Hall Pointer.

**Format:** `delete_session s/NAME m/INDEX...`

**Examples:**

- `list_members` followed by `delete_session s/Rehearsal m/1 m/3` Deletes the session named "Rehearsal" for the members at indexes 1 and 3 in the displayed list.

---

### Clearing all entries : `clear`

Clears all entries from Hall Pointer.

**Format:** `clear`

---

### Exiting the program : `exit`

Exits the program.

**Format:** `exit`

---

### Saving the data

Data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

---

### Editing the data file

HallPointer data is saved automatically as a JSON file at `[JAR file location]/data/hallpointer.json`.

<box type="warning" seamless>

**Caution:** If your changes to the data file make its format invalid, HallPointer will discard all data and start with an empty data file at the next run. It is recommended to back up the file before editing it.

</box>

---

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

---

## FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Install the app on the other computer and overwrite the empty data file it creates with the file that contains the data from your previous HallPointer home folder.

---

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen and later switch to using only the primary screen, the GUI may open off-screen. The remedy is to delete the `preferences.json` file created by the application before running it again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu or keyboard shortcut `F1`) again, the original Help Window will remain minimized. Manually restore the minimized Help Window.

---

## Command summary

| Action             | Format, Examples                                                                                                                                                |
| ------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Add member**     | `add_member n/NAME r/BLOCK-FLOOR-ROOM_NUMBER t/TELEGRAM_HANDLE [tag/TAG]…​​` <br> e.g., `add_member n/James Ho r/4-3-301 t/jamesho123 tag/friend tag/colleague` |
| **Update member**  | `update_member INDEX [n/NAME] [r/BLOCK-FLOOR-ROOM_NUMBER] [t/TELEGRAM_HANDLE] [tag/TAG]…​…​`<br> e.g.,`update_member 2 n/James Lee r/5-2-203 t/jameslee99`      |
| **Delete member**  | `delete_member INDEX`<br> e.g., `delete_member 3`                                                                                                               |
| **Add session**    | `add_session s/NAME d/DATE p/POINTS m/INDEX...​` <br> e.g., `add_session s/Rehearsal d/24 Oct 2024 p/2 m/1 m/3`                                                 |
| **Delete session** | `delete_session s/NAME m/INDEX...`<br> e.g., `delete_session s/Rehearsal m/1 m/3`                                                                               |
| **Find members**   | `find_members KEYWORD [MORE_KEYWORDS]`<br> e.g., `find_members James Jake`                                                                                      |
| **Find sessions**  | `find_sessions KEYWORD [MORE_KEYWORDS]`<br> e.g., `find_sessions Team meeting`                                                                                  |
| **List**           | `list`                                                                                                                                                          |
| **Clear**          | `clear`                                                                                                                                                         |
| **Help**           | `help`                                                                                                                                                          |
| **Exit**           | `exit`                                                                                                                                                          |
