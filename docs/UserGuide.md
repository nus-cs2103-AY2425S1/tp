---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# Dream Day Designer User Guide

Are you a freelance **wedding planner**? Then DDD is just the right tool for you! Managing hundreds of clients and vendors can be troublesome, and **Dream Day Designer (DDD)** is our **answer** to your problems!

DDD is a desktop app for **wedding planners** to keep track of their **clients**, **vendors** and **events**, optimized for use via a **Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI).

DDD is tailored specifically to the needs of **wedding planners**, and is designed to streamline your workflows. If you can type fast, DDD can even help you manage your contacts faster than traditional GUI apps! Furthermore, DDD offers features designed to alleviate the hassle of managing disparate sets of contacts!

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick Start

1. Ensure you have Java `17` or above installed in your Computer.

2. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-F13-3/tp/releases).

3. Copy the file to the folder you want to use as the `HOME_FOLDER` for your DDD.

4. Open a command terminal, use `cd HOME_FOLDER` command to access the folder you put the jar file in, and use the `java -jar ddd.jar` command to run the application.<br>

A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>

![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   </br>
   Here are some example commands you can try with the sample data:

  * `add -c n/Jane Doe p/91234567 e/jane.doe@example.com a/Blk 231 Sembawang St 4 t/budget t/pets` : Adds a client named `Jane Doe` to DDD.

  * `add -v n/ABC Catering p/98765432 e/contact@abccatering.com a/Blk 123 Bukit Merah St 7 s/catering t/vegetarian t/budget` : Adds a vendor named `ABC Catering` to DDD.

  * `add -e n/Amazing Wedding des/by the beach d/2025-01-01 c/0 v/1` : Adds an event named `Amazing Wedding` to DDD.

  * `list` : Lists all contacts.

  * `exit` : Exits the app.

<box type="tip" header="Tip:" >
  Refer to the [Features](#features) below for details of each command.
</box>

--------------------------------------------------------------------------------------------------------------------

## Introduction

Before diving deeper into DDD's features, let's run you through what DDD can do for you! In short, DDD offers a comprehensive system for managing your contacts. Contacts can be also be tagged or tied to events to streamline your workflows!

### Contacts

Each contact is a record of fields associated with a particular person/company. For each contact, DDD will track the following fields:

- Name
- Phone number
- Email
- Address

DDD also allows you to tag contacts with your own tags. This is quite handy if you want to organize your contacts. For example, when sourcing suitable vendors for an upcoming wedding, you might want to keep track of which vendors are more budget-friendly:

![Budget Tag Demo](images/budgetTagDemo.png)

### Clients

`Client` entries represent a generic contact with no additional fields. These are contacts who have contracted your event planning service.

### Vendors

`Vendor` entries contain an extra `service` field. These are contacts who provide a particular type of service for events (e.g. catering, flowers, etc).

<box type="tip" header="Tip:" >
DDD does not currently support vendors providing multiple services. Instead, you can add multiple contacts to represent each service. These entries should be named differently to prevent duplication.
</box>

### Events

`Event` entries represent wedding events. DDD will track the following fields:

- Name
- Description
- Date
- Relevant clients
- Relevant vendors

Events allow you to group contacts together in a sensible and seamless manner like so:

![Event Demo](images/eventDemo.png)

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" header="**Notes about the command format:**">
  Documentation style conventions are based on [Google's guide for CLI syntax](https://developers.google.com/style/code-syntax).

  <md>
    * Words in `UPPER_CASE` are the parameters that must be supplied by the user.<br>
      e.g. in `list n/NAME`, `NAME` is a parameter which can be used as `list n/NAME`.

    * `-TYPE_FLAG` can be either `-c`, `v` or `-e` for commands allowing specifying of type.
      e.g. in `list -TYPE_FLAG`, `-TYPE_FLAG` can allow for filtering all clients with `-c` or vendor with `-v`
    or events with `-e`.

    * Parameters wrapped in **square brackets** are optional arguments.<br>
      e.g. in `add n/NAME ... [t/TAG ...]`, `TAG` is an optional argument

    * Parameters wrapped in **curly brackets** are mutually exclusive arguments (i.e. only 1 should be specified).<br>
      e.g. in `add {-c | -v s/SERVICE} ...`, `-c` and `-v s/SERVICE` are mutually exclusive arguments.

    * Items with `...`​ after them can be used multiple times including zero times.<br>
      e.g. `t/TAG ...​` can be used as ` ` (i.e. 0 times), `t/vegetarian`, `t/budget conscious t/small scale` etc.

    * Parameters can be in any order.<br>
      e.g. if the command specifies `n/NAME p/PHONE_NUMBER -c`, `p/PHONE_NUMBER -c n/NAME` is also acceptable.

    * Extraneous parameters for commands that do not take in parameters (such as `list`, `help`, `exit` and `clear`) will be ignored.<br>
      e.g. if the command specifies `list abc`, it will be interpreted as `list`.

    * If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
  </md>
</box>

### Create a New Record: `add`

Adds a new entity, of type specified by flag.

Format for adding **contact**:
```
add {-c | -v s/SERVICE} n/NAME p/PHONE e/EMAIL a/ADDRESS [t/TAG ...]
```

Examples:
* `add -c n/Jane Doe p/91234567 e/jd@gmail.com a/Blk 123 St 4 t/budget`
* `add -v n/ABC Catering p/98765432 e/abc@abc.com a/Blk 567 St 8 s/catering t/vegan t/budget`

Note:
* Contacts' name and phone number pair need to be unique.
* A contact can have any number of tags (including 0)

___
Format for adding **event**:

```
add -e n/NAME des/DESCRIPTION d/DATE c/CLIENT_ID v/VENDOR_ID [c/CLIENT_ID ...] [v/VENDOR_ID ...]
```

Example:
* `add -e n/Sample Wedding des/Wedding reception d/2025-01-01 c/0 v/1 v/2`

Notes:
* Events are uniquely identified by their names and hence all event names must be unique.
* Each event must have minimal one client and one vendor.

### View Contacts/Events: `list`

List contacts or events (with optional filters).

Format:
```
list [{-c | -v}] [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG ...] [id/ID] { | [s/SERVICE] }
```
```
list [-e] [n/NAME] [d/DATE] [des/DESCRIPTION] [id/ID]
```

Example:
* `list -c n/Jane p/81234567`
* `list -v s/catering`
* `list -e des/wedding`

Notes:
* All parameters are optional. Leaving them out will list all contacts (clients and vendors) by default.
* The `-c`, `-v` and `-e` flags can be used to decide what type of data to list.
* `s/SERVICE` should only be specified if `v` is specified.
* `d/DATE` should only be specified if `-e` is specified.
* `des/DESCRIPTION` should only be specified if `-e` is specified.
* If no flags are present, the default behaviour is to list all contacts. e.g. `list asiodhainsd` will be treated as `list` as there are no `-c`, `-v`, or `-e` flags.
* All user input in between flags are ignored. e.g. `list ajsdbnsad -c asjidna n/Jane` will be treated as `list -c n/jane`
* The name keyword search is case-insensitive. e.g. `hans` will match `Hans`.
* Only full words will be matched e.g. `Han` will not match `Hans`.
* Contacts matching all fields keyword will be returned (i.e. `AND` search). e.g. `list -c n/Jane p/91234567` will list all clients with name `Jane` **AND** phone number `91234567`.
* Searching by address will list all contacts with addresses that include the keywords. e.g. `list a/Blk 123` will list contacts with address `Blk 123` and `Blk 456` because`Blk 456` contains the word `Blk`.
* Likewise, searching by name will list all contacts and events with names that include the input keywords.

### Editing a Contact

Edit an existing contact.

Format:
```
edit {INDEX | id/ID} [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [s/SERVICE] [t/TAG ...]
```

Examples:
* `edit 1 p/91234567`
* `edit id/0 p/91234567 e/johndoe@example.com`

Notes:
* Only one of `INDEX` or `id/ID` should be specified.
* `s/SERVICE` should only be specified if the contact is a vendor.

### Deleting a Contact/Event : `delete`

Deletes a contact or an event.

Format:

```
delete INDEX
```

Examples:
* `delete 1`

Notes:
* `INDEX` should be the one-based index position of the contact or event displayed on the screen.
* The command is highly dependent on what is displayed on the screen, i.e., `delete 1` will have different results when preceded by different `list` options.
* To delete an event, the user has to enter `list -e` (with optional filters) to ensure the screen displays events, before entering the `delete` command.
* Similarly, to delete contacts, the user has to enter `list` (with optional filters) to ensure the screen displays contacts, before entering the `delete` command.
* The user will not be allowed to delete clients and vendors that are the **sole** client/vendor of any event, i.e., if any event only has a single client/vendor, that client/vendor cannot be deleted.
* The user must delete the corresponding event(s) before deleting the intended client.

### Clearing All Entries : `clear`

Clears all contacts and events.

Format:
```
clear
```

### Viewing Help: `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format:
```
help
```

### Exiting the App : `exit`

Exits the program.

Format:
```
exit
```

### Editing the Data File

DDD data are saved automatically as a JSON file `[JAR file location]/data/ddd.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" header="**Caution**">
If your changes to the data file makes its format invalid, DDD will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>

Furthermore, certain edits can cause the DDD to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

--------------------------------------------------------------------------------------------------------------------

## FAQs

<box type="tip" icon=":question:" seamless>
  <strong>Q</strong>: I accidentally deleted a contact. Is there an undo feature?<br>
  <strong>A</strong>: Nope. Unforunately, undo has not been implemented.
</box>

<box type="tip" icon=":question:" seamless>
  <strong>Q</strong>: How do I edit events?<br>
  <strong>A</strong>: Unforunately, editing events has not been implemented. You will have to delete the existing event and create a new one with your desired details.
</box>

<box type="tip" icon=":question:" seamless>
  <strong>Q</strong>: I have a vendor that provides multiple services, but I can only indicate 1 service per vendor entry. What should I do?<br>
  <strong>A</strong>: In such a scenario, you can create a second entry which is named differently to store the contact. The reason each vendor can only provde 1 single service is so that searches via the <code>list</code> command can be more precise.
</box>

<box type="tip" icon=":question:" seamless>
  <strong>Q</strong>: Can DDD be used by users who are not wedding planners (i.e. other event planners)?<br>
  <strong>A</strong>: Yes! While DDD is targetted at wedding planners, its features can be adapted to store contacts related to planning events, not just limited to weddings.
</box>

<box type="tip" icon=":question:" seamless>
  <strong>Q</strong>: How do I transfer my data to another Computer?<br>
  <strong>A</strong>: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous DDD home folder.
</box>

--------------------------------------------------------------------------------------------------------------------

## Known Issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command Summary

### `add`

| Action            | Format                                                                 | Example                                                                                     |
| ----------------- | ---------------------------------------------------------------------- | ------------------------------------------------------------------------------------------- |
| **Create Client** | `add -c n/NAME p/PHONE e/EMAIL a/ADDRESS [t/TAG ...]`                  | `add -c n/Jane Doe p/91234567 e/jd@gmail.com a/Blk 123 St 4 t/budget`                       |
| **Create Vendor** | `add -v n/NAME p/PHONE e/EMAIL a/ADDRESS s/SERVICE [t/TAG ...]`        | `add -v n/ABC Catering p/98765432 e/abc@abc.com a/Blk 567 St 8 s/catering t/vegan t/budget` |
| **Create Event**  | `add -e n/NAME des/DESCRIPTION d/DATE c/CLIENT_ID ... v/VENDOR_ID ...` | `add -e n/Sample Wedding des/Wedding reception d/2000-01-01 c/0 v/1 v/2`                    |

### `list`

| Action            | Format                                                                                      | Example               |
| ----------------- | ------------------------------------------------------------------------------------------- | --------------------- |
| **List Contacts** | `list`                                                                                      | `list`                |
| **List Clients**  | `list -c [n/NAME] [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG ...] [id/ID]`             | `list -c n/Jane`      |
| **List Vendors**  | `list -v [n/NAME] [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG ...] [id/ID] [s/SERVICE]` | `list -v s/catering`  |
| **List Events**   | `list -e [n/NAME] [d/DATE] [des/DESCRIPTION] [id/ID]`                                       | `list -e des/wedding` |


### `edit`

| Action            | Format                                                                        | Example                                      |
| ----------------- | ----------------------------------------------------------------------------- | -------------------------------------------- |
| **Edit by Index** | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [s/SERVICE] [t/TAG ...]` | `edit 1 p/91234567`                          |
| **Edit by ID**    | `edit id/ID [p/PHONE] [n/NAME] [e/EMAIL] [a/ADDRESS] [s/SERVICE] [t/TAG ...]` | `edit id/0 p/91234567 e/johndoe@example.com` |

### Miscellaneous

| Action                            | Format         | Example    |
| --------------------------------- | -------------- | ---------- |
| **Delete Contact/Event by Index** | `delete INDEX` | `delete 1` |
| **Clear Data**                    | `clear`        | `clear`    |
| **Help**                          | `help`         | `help`     |
| **Exit**                          | `exit`         | `exit`     |

--------------------------------------------------------------------------------------------------------------------

## Glossary

In case you need more information on the command parameters, here's a more comprehensive explanation of each parameter:

### Flags

Some commands can be applied on clients, vendors and events. Use `-CONTACT_FLAG` to specify which type of record to edit (e.g. `list -c` to list clients).

* `-c`: flag to specify client related commands
* `-v`: flag to specify vendor related commands
* `-e`: flag to specify event related commands

### Parameters

| Parameter   | Description                 | Notes/Contraints                                                                                                     |
| ----------- | --------------------------- | -------------------------------------------------------------------------------------------------------------------- |
| `n/NAME`    | Name of contact/event       | <ul><li>Should only alphanumeric characters or spaces</li><li>Should not be blank</li><ul>                           |
| `p/PHONE`   | Phone number of contact     | <ul><li>Should only contain numbers</li><li>Should have a length between 3 and 20 digits</li><ul>                    |
| `e/EMAIL`   | Email address of contact    | <ul><li>Follows the format `LOCAL_PART@DOMAIN_NAME`</li><li>`LOCAL_PART` should only contain alphanumeric characters or these special characters `+_.-`</li><ul><li>`LOCAL_PART` should not start or end with special characters</li><li>`LOCAL_PART` can contain consecutive special characters, except for periods</li></ul><li>`DOMAIN_NAME` should only contain alphanumeric characters and periods</li><ul><li>`DOMAIN_NAME` consists of a set of domain labels separated by periods</li><li>Domain labels should be at least 2 characters long</li><li>Domain labels should start and end with alphanumeric characters</li></ul></ul> |
| `a/ADDRESS` | Address of contact          | <ul><li>Can contain any value except slash (`/`)</li><li>Should not be blank</li><ul>                                |
| `s/SERVICE` | Service provided by vendor  | <ul><li>Can contain any value except slash (`/`)</li><li>Should not be blank</li><ul>                                |
| `t/TAG`     | Tag associated with contact | <ul><li>Should only contain alphanumeric characters or dashes</li></ul>                                              |
| `d/DATE`    | Date of event               | <ul>Should follow one of these formats:<ul><li>`MM/dd/yyyy`</li><li>`yyyy-MM-dd`</li><li>`d MMM yyyy`</li></ul></ul> |
| `id/ID`     | ID of contact/event         | <ul><li>This field is configured by DDD</li></ul>                                                                    |
