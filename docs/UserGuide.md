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

## Quick Start

1. Ensure you have Java `17` or above installed in your Computer.

2. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-F13-3/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your DDD.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar ddd.jar` command to run the application.<br>

A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>

![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list -c` : Lists all clients.

   * `add -c n/Jane Doe p/91234567 e/jane.doe@example.com a/Blk 231 Sembawang St 4 d/2024-12-15 t/budget t/pets` : Adds a client named `Jane Doe` to DDD.
   
   * `add -v n/ABC Catering p/98765432 e/contact@abccatering.com a/Blk 123 Bukit Merah St 7 s/catering t/vegetarian t/budget` : Adds a vendor named `ABC Catering` to DDD.

   * `delete 2` : Deletes the 2nd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

**Notes about the command format:**<br>

Documentation style conventions are based on [Google's guide](https://developers.google.com/style/code-syntax). 

* Words in `UPPER_CASE` are the parameters that must be supplied by the user.<br>
  e.g. in `list n/NAME`, `NAME` is a parameter which can be used as `list n/NAME`.

* `-TYPE_FLAG` can be either `-v` or `-c` or `-e` for commands allowing specifying of type.
  e.g. in `list -TYPE_FLAG`, `-TYPE_FLAG` can allow for filtering all vendors with `-v` or clients with `-c`
or events with `-e`.

* Parameters wrapped in **square brackets** are optional arguments.<br>
  e.g. in `add n/NAME ... [t/TAG ...]`, `TAG` is an optional argument

* Parameters wrapped in **curly brackets** are mutually exclusive arguments (i.e. only 1 should be specified).<br>
  e.g. in `add {-c | -v s/SERVICE} ...`, `-c` and `-v s/SERVICE` are mutually exclusive arguments.

* `WEDDING_DATE` parameter will only accept the following date formats: `MM/dd/yyyy`, `yyyy-MM-dd` `d MMM yyyy`
  e.g. `MM/dd/yyyy`: 10/13/2024; `yyyy-MM-dd`: 2024-10-13; `d MMM yyyy`: 13 Oct 2024

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `t/TAG…​` can be used as ` ` (i.e. 0 times), `t/vegetarian`, `t/budget conscious t/small scale` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER -c`, `p/PHONE_NUMBER -c n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

### Viewing help: `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format:
```
help
```

### Create a new entity: `add`

Adds a new entity, of type specified by flag.

Format for adding **contact**:
```
add {-c | -v s/SERVICE} n/NAME p/PHONE e/EMAIL a/ADDRESS [t/TAG ...]
```

Parameters:
* `-c`: flag to create a client (only 1 of -c, -v or -e should be specified)
* `-v`: flag to create a vendor (only 1 of -c, -v or -e should be specified)
* `s/SERVICE`: service provided by vendor (must be specified if `-v` is specified)
* `n/NAME`: name of contact
* `p/PHONE`: phone number of contact
* `e/EMAIL`: email address of contact
* `a/ADDRESS`: address of contact
* `t/TAG`: tag(s) associated with the contact

> **Tip:** A contact can have any number of tags (including 0)

Examples:
* `add -c n/Jane Doe p/91234567 e/jd@gmail.com a/Blk 123 St 4 t/budget`
* `add -v n/ABC Catering p/98765432 e/abc@abc.com a/Blk 567 St 8 s/catering t/vegan t/budget`

Note:
* Contacts' name-phone number pair need to be unique.

___
Format for adding **event**:

```
add -e n/NAME des/DESCRIPTION d/DATE c/CLIENT_ID v/VENDOR_ID [c/CLIENT_ID ...] [v/VENDOR_ID ...]
```

Parameters:
* `n/NAME`: name of event
* `des/DESCRIPTION`: description of event
* `d/DATE`: date of event
* `c/CLIENT`: list of client IDs
* `v/VENDOR`: list of vendor IDs

Example:
* `add -e n/Sample Wedding des/Wedding reception d/2025-01-01 c/0 v/1 v/2`

Notes:
* Events are uniquely identified by their names and hence all event names must be unique.
* Each event must have minimal one client and one vendor.

### View contacts or events: `list`

List contacts or events (with optional filters).

Format:
```
list [{-c | -v}] [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG ...] [id/ID] { | [s/SERVICE] }
```
```
list [-e] [n/NAME] [d/DATE] [des/DESCRIPTION] [id/ID]
```

Parameters:
* `-c`: list clients (only 1 of -c, -v or -e should be specified)
* `-v`: list vendors (only 1 of -c, -v or -e should be specified)
* `-e`: list events (only 1 of -c, -v or -e should be specified)
* `n/NAME`: name of contact or event
* `p/PHONE`: phone number of contact
* `e/EMAIL`: email address of contact
* `a/ADDRESS`: address of contact
* `s/SERVICE`: service provided by vendor (can only be specified if `-v` is specified)
* `t/TAG`: tag(s) associated with the contact
* `id/ID`: ID of the contact or event
* `d/DATE`: date of event (can only be specified if `-e` is specified)
* `des/DESCRIPTION`: description of the event (should only be specified if `-e` is specified)

Example:
* `list -c n/Jane p/81234567`
* `list -v s/catering`
* `list -e des/wedding`

Notes:
* If no arguments are specified, `list` will list all contacts (clients and vendors).
* The `-c`, `-v` and `-e` flags can be used to decide what type of data to list. 
* If no flags are present, the default behaviour is to list all contacts. e.g. `list asiodhainsd` will be treated as `list` as there are no `-c`, `-v`, or `-e` flags.
* All user input in between flags are ignored. e.g. `list ajsdbnsad -c asjidna n/Jane` will be treated as `list -c n/jane`
* All parameters are optional. Leaving them out will list all contacts by default.
* The name keyword search is case-insensitive. e.g `hans` will match `Hans`.
* Only full words will be matched e.g. `Han` will not match `Hans`.
* Contacts matching all fields keyword will be returned (i.e. `AND` search). e.g. `list -c n/Jane p/91234567` will list all clients with name `Jane` **AND** phone number `91234567`.
* Searching by address will list all contacts with addresses that include the keywords. e.g. `list a/Blk 123` will list contacts with address `Blk 123` and `Blk 456` because`Blk 456` contains the word `Blk`.
* Likewise, searching by name will list all contacts and events with names that include the input keywords.

### Editing a contact

Edit an existing contact.

Format:
```
edit {INDEX | id/ID} [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [s/SERVICE] [t/TAG ...]
```

Parameters:
- `INDEX`: one-based index position of the contact
- `id/ID`: ID of the target contact
- `n/NAME`: edited name of contact
- `p/PHONE`: edited phone number of contact
- `e/EMAIL`: edited email address of contact
- `a/ADDRESS`: edited address of contact
- `s/SERVICE`: edited service provided by vendor (can only be specified if the contact is a vendor)
- `t/TAG`: edited tag(s) associated with the contact

Notes:
* Only one of `INDEX` or `id/ID` should be specified.

Examples:
* `edit 1 p/91234567`
* `edit id/0 p/91234567 e/johndoe@example.com`

### Deleting a contact or event : `delete`

Deletes a contact or an event.

Format: `delete INDEX`

Parameters:
- `INDEX`: one-based index position of the contact or event displayed on the screen

Examples:
* `delete 1`

Notes:
* The command is highly dependent on what is displayed on the screen, i.e., `delete 1` will have different results when preceded by different `list` options
* To delete an event, the user has to enter `list -e` (with optional filters) to ensure the screen displays events, before entering the `delete` command
* Similarly, to delete contacts, the user has to enter `list` (with optional filters) to ensure the screen displays contacts, before entering the `delete` command
* The user will not be allowed to delete clients that are the **sole** client of any event, i.e., if any event only has a single client, that client cannot be deleted
* The user must delete the corresponding event(s) before deleting the intended client
* Users are allowed to delete vendors that are the **sole** vendor of any event, i.e. if the event only has a single vendor, it can still be deleted. However, while adding an event, at least 1 client and 1 vendor must be input to create an event.

### Clearing all entries : `clear`

Clears all entries.

Format:
```
clear
```

### Exiting the program : `exit`

Exits the program.

Format:
```
exit
```

### Editing the data file

DDD data are saved automatically as a JSON file `[JAR file location]/data/ddd.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless><strong>Caution:</strong>
If your changes to the data file makes its format invalid, DDD will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the DDD to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.</box>

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

| Action            | Format                                                                                             | Example                                                                                     |
|-------------------|----------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------|
| **Create Client** | `add -c n/NAME p/PHONE e/EMAIL a/ADDRESS [t/TAG ...]`                                              | `add -c n/Jane Doe p/91234567 e/jd@gmail.com a/Blk 123 St 4 t/budget`                       |
| **Create Vendor** | `add -v n/NAME p/PHONE e/EMAIL a/ADDRESS s/SERVICE [t/TAG ...]`                                    | `add -v n/ABC Catering p/98765432 e/abc@abc.com a/Blk 567 St 8 s/catering t/vegan t/budget` |
| **Create Event**  | `add -e n/NAME des/DESCRIPTION d/DATE c/CLIENT_ID v/VENDOR_ID [c/CLIENT_ID ...] [v/VENDOR_ID ...]` | `add -e n/Sample Wedding des/Wedding reception d/2000-01-01 c/0 v/1 v/2`                    |
| **Clear**         | `clear`                                                                                            | `clear`                                                                                     |
| **Delete**        | `delete INDEX`                                                                                     | `delete 1`                                                                                  |
| **List Clients**  | `list -c [n/NAME] [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG ...] [id/ID]`                    | `list -c n/Jane`                                                                            |
| **List Vendors**  | `list -v [n/NAME] [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG ...] [id/ID] [s/SERVICE]`        | `list -v s/catering`                                                                        |
| **List Events**   | `list [-e] [n/NAME] [d/DATE] [des/DESCRIPTION] [id/ID]`                                            | `list -e des/wedding`                                                                       |
| **List Contacts** | `list`                                                                                             | `list`                                                                                      |
| **Edit by Index** | `edit INDEX [p/PHONE] [n/NAME] [e/EMAIL] [a/ADDRESS] [s/SERVICE] [t/TAG ...]`                      | `edit 1 p/91234567`                                                                         |
| **Edit by ID**    | `edit id/ID [p/PHONE] [n/NAME] [e/EMAIL] [a/ADDRESS] [s/SERVICE] [t/TAG ...]`                      | `edit id/0 p/91234567 e/johndoe@example.com`                                                |
| **Help**          | `help`                                                                                             | `help`                                                                                      |
