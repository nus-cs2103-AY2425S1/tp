---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# Dream Day Designer User Guide

Dream Day Designer (DDD) is a **desktop app for wedding planners to keep track of clients' requirements and vendor services, optimized for use via a Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, DDD can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-F13-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your DDD.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar dreamdaydesigner.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list -c` : Lists all clients.

   * `add -c n/Jane Doe p/91234567 e/jane.doe@example.com a/Blk 231 Sembawang St 4 d/2024-12-15 t/budget t/pets` : Adds a client named `Jane Doe` to DDD.
   
   * `add -v n/ABC Catering p/98765432 e/contact@abccatering.com a/Blk 123 Bukit Merah St 7 s/catering t/vegetarian t/budget` : Adds a vendor named `ABC Catering` to DDD.

   * `delete 2` : Deletes the 2nd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `list n/NAME`, `NAME` is a parameter which can be used as `list n/NAME`.

* `-CONTACT_FLAG` can be either `-v` or `-c` for commands allowing specifying of contact type.
  e.g. in `list -CONTACT_FLAG`, `-CONTACT_FLAG` can allow for filtering all vendors with `-v` or clients with `-c`.

* `WEDDING_DATE` parameter will only accept the following date formats: `MM/dd/yyyy`, `yyyy-MM-dd` `d MMM yyyy`
  e.g. `MM/dd/yyyy`: 10/13/2024m; `yyyy-MM-dd`: 2024-10-13; `d MMM yyyy`: 13 Oct 2024

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `t/TAG…​` can be used as ` ` (i.e. 0 times), `t/vegetarian`, `t/budget conscious t/small scale` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Create a new contact: `add`

Add new contact (client or vendor) to contact list.

Format:

`add -CONTACT_FLAG n/NAME p/PHONE e/EMAIL a/ADDRESS s/SERVICE (only if adding vendor) d/WEDDING_DATE (only if adding client) [t/TAG]...`

<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0)
</box>

Examples:
* `add -v n/ABC Catering p/98765432 e/contact@abccatering.com a/Blk 123 Bukit Merah St 7 s/catering t/vegetarian t/budget`
* `add -c n/Jane Doe p/91234567 e/jane.doe@example.com a/Blk 231 Sembawang St 4 d/2024-12-15 t/budget t/pets`

### Locating contacts by field: `list`

View all contacts based on field input.

Format:
`list -CONTACT_FLAG n/[NAME] id/[CONTACT_ID]`

* The `-CONTACT_FLAG` is optional. Both vendors and clients will be listed if not specified.
* Both the `n/[NAME]` and `id/[CONTACT_ID]` are optional. Leaving both out will list all.
* The name keyword search is case-sensitive. e.g `hans` will not match `Hans`
* Only full words will be matched e.g. `Han` will not match `Hans`
* Contacts matching all fields keyword will be returned (i.e. `AND` search).

Example: `list -c n/Alice`

* Lists all clients with the name field 'Alice'.

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `list -c n/Jane` followed by `delete 1` deletes the 1st person in the results of the `list` command.

### Clearing all entries : `clear`

Clears all entries from DDD.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Editing the data file

DDD data are saved automatically as a JSON file `[JAR file location]/data/ddd.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, DDD will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the DDD to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous DDD home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action                    | Format, Examples                                                                                                                                                                                   |
|---------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Create Client Contact** | `add -c n/NAME p/PHONE e/EMAIL a/ADDRESS d/WEDDING_DATE [t/TAG]...` <br> e.g., `add -c n/Jane Doe p/91234567 e/jane.doe@example.com a/Blk 231 Sembawang St 4 d/2024-12-15 t/budget t/pets`         |
| **Create Vendor Contact** | `add -v n/NAME p/PHONE e/EMAIL a/ADDRESS s/SERVICE [t/TAG]...` <br> e.g., `add -v n/ABC Catering p/98765432 e/contact@abccatering.com a/Blk 123 Bukit Merah St 7 s/catering t/vegetarian t/budget` |
| **Clear**                 | `clear`                                                                                                                                                                                            |
| **Delete**                | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                                                |
| **List**                  | `list -CONTACT_FLAG n/[NAME] id/[CONTACT_ID]` <br> e.g., `list -c n/Jane`                                                                                                                          |
| **List All**              | `list`                                                                                                                                                                                             |
| **Help**                  | `help`                                                                                                                                                                                             |
