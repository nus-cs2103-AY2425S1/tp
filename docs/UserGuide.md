---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# EventTory User Guide

EventTory is a **desktop app for managing vendors and events**.

It is **optimized for use** via a Command-Line Interface (CLI) while still retaining the benefits of a Graphical User Interface (GUI).<br>
If you can type fast, EventTory can get your **event management tasks** done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick Start

1. Ensure you have Java `17` or above installed in your Computer.

2. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

3. Copy the file to the folder you want to use as the _home folder_ for the EventTory application.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar EventTory.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note that the application contains some sample data on initial boot.<br>
   ![Ui](images/Ui.png)

5. Type a command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open a help window.<br>
   Example commands to try:

   * `list` : Lists all events and vendors.

   * `create v/ n/Hong Lim Trading Pte. Ltd. p/67412943 d/Specialises in lighting effects. t/stage-crew` : Creates a vendor named `Hong Lim Trading Pte. Ltd.` and saves in the application.

   * `assign v/1 e/2` : Assigns the 1st vendor in the vendor list to the 2nd event in the event list.
   
   * `view e/4` : View more information about the 4th event in the event list.

   * `delete v/1` : Deletes the 1st vendor shown in the vendor list.

   * `clear` : Deletes all vendors and events stored in EventTory.

   * `exit` : Exits the application.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Storing Information

<box type="info" seamless>

EventTory provides users a way to keep track of events and vendors.
The details of what information can be stored for a vendor/event are as shown below.

### Vendor

* Represents a vendor contracted to provide services for an event e.g. catering, decorating, performing, etc.
* Information about a vendor can be stored with the following fields:
  * **Name** : The name of the vendor (usually a company name)
  * **Phone Number** : The contact number of the vendor
  * **Description** : Text containing any additional information about the vendor
  * **Tags** (if applicable) : Metadata used to classify the vendor
* Multiple vendors can be stored in EventTory.

### Event

* Represents an event that needs to be organised and usually enlists the help of vendors to plan/run it.
* Information about an event can be stored with the following fields:
  * **Name** : The name of the event
  * **Date** : The date the event falls on
  * **Tags** (if applicable) : Metadata used to classify the event
* Multiple events can be stored in EventTory.

## Features

<box type="info" seamless>

### Command Formats

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. `create v/ n/VENDOR_NAME`, `VENDOR_NAME` is a parameter which is specified by the user:
  * `create v/ n/Kimberly's Flowers`.

<p>

* Items in square brackets are **optional**.<br>
  e.g. `e/ n/EVENT_NAME [t/TAG]` can be used as:
  * `e/ n/Stagecraft Solutions t/backstage-crew` or as;
  * `n/Stagecraft Solutions`.

<p>

* Items with `…` after them can be used multiple times, including zero times.<br>
  e.g. `[t/TAG]…​` can be used as:
  * ` ` (i.e. 0 times);
  * `t/big-event`;
  * `t/big-event t/coastal`, etc.

<p>

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

<p>

* Extraneous parameters for commands that do not take in parameters (such as `help`, `exit` and `clear`) will be ignored.<br>
  * e.g. if the command specifies `help 123`, it will be interpreted as `help`.

<p>

* If additional parameters are supplied for commands that do not use said parameters, they will be ignored as well. 
  * e.g. if the command specifies `list v/ e/ n/NAME`, where the `NAME` parameter is not used for the `list` command, it will be interpreted as `list v/ e/`.

<p>

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing Help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

#### Format: `help`

### Creating a Vendor or Event: `create`

Creates a vendor or event in EventTory.

#### Format:
* To create a vendor: `create v/ n/VENDOR_NAME d/DESCRIPTION p/PHONE_NUMBER [t/TAG]…​`
* To create en event: `create e/ n/EVENT_NAME on/DATE [t/TAG]…​`

#### Notes:
If parameters are provided for the `v/` and `e/` flags, they will be ignored.
<box type="tip" seamless><br>
**Tip:** A vendor or event can have any number of tags (including 0).
</box>

#### Examples:
* `create v/ n/Hong Lim Trading Pte. Ltd. p/67412943 d/Specialises in lighting effects. t/stage-crew`
* `create e/ n/Jubilee Concert on/24 Jan 2025 t/annual`

### Listing Items : `list`

Displays the list of vendors and/or events in EventTory.

#### Format: `list [v/] [e/]`

#### Notes:
* The list(s) displayed depends on whether the `v/` and/or `e/` prefix(es) is specified.
* If no prefixes are specified, both the vendor and event lists will be displayed.
* The prefixes can be specified in any order.
* If values are specified after the prefixes (e.g. `v/2`, `e/Party`), the value is ignored.

#### Examples:
* `list v/` will display the list of vendors.
* `list e/` will display the list of events.
* `list v/ e/` and `list` will display both vendor and event lists.

### Editing a Vendor or Event : `edit`

Edits an existing vendor or event in EventTory.

#### Format: 
* To edit a vendor: `edit v/INDEX [n/NAME] [p/PHONE] [d/DESCRIPTION] [t/TAG]…​`
* To edit an event: `edit e/INDEX [n/NAME] [on/DATE] [t/TAG]…​`

#### Notes:
* Edits the vendor/event at the specified `INDEX`.
  * The index refers to the index number shown in the vendor or event list.
  * The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
  * Editing an item but providing no new values is invalid.
* The existing values will be updated to the input values.
* When editing tags, the existing tags of the vendor/event will be **overridden**.
  * Tags cannot be added cumulatively.
  * You can remove all tags from a vendor/event by typing `t/` without specifying any tags after it.

#### Examples:
*  `edit v/1 p/58623042 ` : Edits the phone number of the 1st vendor to be `58623042`.
*  `edit e/2 n/Baby Shower t/` : Edits the name of the 2nd event to be `Baby Shower`, and clears all existing tags.

### Viewing Vendors & Events: `view`

Views the details of a vendor or event.

#### Format: `view v/INDEX` or `view e/INDEX`

* Views the details of the vendor/event at the specified `INDEX`.
  * The index refers to the index number shown in the displayed vendor/event list.
  * The index **must be a positive integer** 1, 2, 3, ...
* The details page includes assigned events/vendors as well as a list of assignable events/vendors.

#### Examples:
* `view v/2` will show the details of the 2nd vendor.
* `view e/1` will show the details of the 1st event.

### Assigning Vendors & Events: `assign`

Assigns vendors to events and vice versa.

#### Format: `assign INDEX`

#### Notes:
* Assigns the vendor/event specified at `INDEX` to the current viewed event/vendor.
  * The index refers to the index number shown in the **assignable** vendor/event list.
  * The index **must be a positive integer** 1, 2, 3, ...
* The command only works when the user is viewing a vendor/event using the `view` command. Otherwise, the operation will fail.
* If the specified vendor-event pair are already associated (assigned to each other), the operation will fail.

#### Examples:
* `view v/2` then `assign 1` will assign the 1st event to the current viewed vendor, which is the 2nd vendor.
* `view e/1` then `assign 3` will assign the 3rd vendor to the current viewed event, which is the 1st event.

### Unassigning Vendors & Events: `unassign`

Unassigns vendors from events and vice versa.

#### Format: `unassign INDEX`

#### Notes:
* Unassigns the vendor/event specified at `INDEX` to the current viewed event/vendor.
  * The index refers to the index number shown in the **assigned** vendor/event list.
  * The index **must be a positive integer** 1, 2, 3, ...
* The command only works when the user is viewing a vendor/event using the `view` command. Otherwise, the operation will fail.
* If the specified vendor-event pair are not already associated (not assigned to each other), the operation will fail.

#### Examples:
* `view v/2` then `unassign 1` will unassign the 1st event from the current viewed vendor, which is the 2nd vendor.
* `view e/1` then `unassign 3` will unassign the 3rd vendor from the current viewed event, which is the 1st event.

### Searching for Vendors & Events: `find`

Finds vendors or events whose attributes contain any of the space-separated keywords provided.

#### Format: `find v/ KEYWORD [MORE_KEYWORDS]` or `find e/ KEYWORD [MORE_KEYWORDS]`

#### Notes:
* The search is case-insensitive. e.g. `party` will match `Party`
* Any partial matches will still be matched e.g. `par` will match `party`
* The order of the keywords does not matter. e.g. `party birthday` will match `birthday party`
* All attributes of the `Vendor` or `Event` are searched, i.e. name, phone number, date, descriptions and tags.
* Vendors and Events matching at least one keyword will be returned (i.e. `OR` search).
  * e.g. `party wedding` will return `Birthday Party`, `John's Wedding`
* If no matches are found, the user will be informed and the current view will remain unchanged.

#### Examples:
* `find v/ catering` returns `catering` and `Catering Solutions`
* `find e/ party wedding` returns `Birthday Party` and `John's Wedding`<br>

### Deleting Items : `delete`

Deletes a vendor or an event from EventTory.

#### Format: `delete [v/INDEX]` or `delete [e/INDEX]`

* Deletes the event or vendor at the specified `INDEX`.
    * The index refers to the index number shown in the displayed event/vendor list respectively.
    * The index **must be a positive integer** 1, 2, 3, ...
    * The index for each vendor/event is relative and can change depending on previous operations.
* The operation will succeed even if the specified vendor/event is not visible onscreen.
  * e.g. `delete v/1` is run after `view v/2`. Even though the 1st vendor will not be visible, it can still be specified for deletion.
* If the specified vendor/event is currently assigned to another event/vendor respectively, the operation will fail.
* If the current viewed vendor/event is deleted, the application will return you to the main list screen.

#### Examples:
* `list` followed by `delete v/2` deletes the 2nd vendor in EventTory.
* `find e/Wedding` followed by `delete e/1` deletes the 1st event shown in the results of the `find` command.

### Clearing All Entries : `clear`

Clears all vendor and event entries from EventTory.

#### Format: `clear`

### Exiting the program : `exit`

Exits the program.

#### Format: `exit`

### Saving Data

EventTory data is saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

### Editing the Data File

EventTory data is saved automatically as a JSON file `[JAR file location]/data/eventTory.json`.<br>
Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If changes to the data file causes its format to become invalid, EventTory will discard **all data** and start with an **empty** data file at the next run.

<p>
Hence, it is recommended to make a backup of the file before attempting to edit it.
Furthermore, certain edits can cause the EventTory to behave in unexpected ways (e.g., if a value entered is outside the acceptable range).<br>
Therefore, edit the data file only if you are confident that you can update it correctly.
</p>

</box>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous EventTory home folder.

--------------------------------------------------------------------------------------------------------------------

## Known Issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command Summary

| Action       | Format, Examples                                                                                                                                                                                                                                  |
|--------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Create**   | -`create v/ n/VENDOR_NAME p/PHONE_NUMBER d/DESCRIPTION [t/TAG]…​` or,<br> -`create e/ n/EVENT_NAME on/DATE [t/TAG]…​`<br><br>e.g., `create e/ n/Graduation Party on/2025-12-10 t/smu`                                                             |
| **Assign**   | `assign INDEX` <br> e.g. `assign 1`                                                                                                                                                                                                               |
| **Unassign** | `unassign INDEX` <br> e.g. `unassign 1`                                                                                                                                                                                                           |
| **List**     | `list [v/] [e/]` <br> e.g. `list v/`, `list v/ e/`                                                                                                                                                                                                |
| **View**     | `view v/INDEX` or `view e/INDEX`<br> e.g. `view v/1`                                                                                                                                                                                              |
| **Delete**   | `delete v/INDEX` or `delete e/INDEX` <br> e.g., `delete v/3`, `delete e/2`                                                                                                                                                                        |
| **Edit**     | -`edit v/INDEX [n/VENDOR_NAME] [p/PHONE_NUMBER] [d/DESCRIPTION] [t/TAG]…​` or,<br> -`edit e/INDEX [n/EVENT_NAME] [on/DATE] [t/TAG]…​` <br><br> e.g., `edit v/2 n/PC Parts Trading d/Sells PC Parts` or, <br> `edit e/3 n/Hackathon on/2024-10-12` |
| **Find**     | -`find v/ KEYWORD [MORE_KEYWORDS]…` or,<br>-`find e/ KEYWORD [MORE_KEYWORDS]…`<br><br> e.g., `find v/ Catering Band`, `find e/ wedding banquet`                                                                                                   |
| **Clear**    | `clear`                                                                                                                                                                                                                                           |
| **Help**     | `help`                                                                                                                                                                                                                                            |
| **Exit**     | `exit`                                                                                                                                                                                                                                            |
