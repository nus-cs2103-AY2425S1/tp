---
layout: page
title: User Guide
---

Teacher's Pet (TP) is a **desktop app for managing students, tailored for the National University of Singapore (NUS) and optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, TP enables you to manage your students faster than traditional GUI apps.

* Table of Contents
{:toc}

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

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [m/MAJOR]` can be used as `n/John Doe m/Business` or as `n/John Doe`.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME id/NUS_STUDENTID`, `id/NUS_STUDENTID n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to Teacher's Pet.

Format: `add n/NAME id/NUS_STUDENTID [nid/NUS_NETID] [m/MAJOR] [y/YEAR] [g/group GROUP_NUMBER]`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can only belong to one group at a time.
</div>

* NUS_STUDENTID here refers to the NUS Matriculation Number of the student (Starts with "A")  
* NUS_NETID here refers to the id that is associated with the student's NUS outlook account ("NUS_NETID@u.nus.edu")
* Both NUS_STUDENTID and NUS_NETID must be unique (ie. Two students cannot have the same NUS_STUDENTID and/or NUS_NETID)

Examples:
* `add n/John Doe id/A1234567P`
* `add n/Betsy Crowe m/Computer Science nid/e1111111 id/A9999999L y/1 g/group 1`

### Listing all persons : `list`

Shows a list of all persons in Teachers' Pet.

Format: `list`

### Editing a person : `edit`

Edits an existing person in Teacher's Pet.

Format: `edit INDEX [n/NAME] [id/STUDENTID] [nid/EMAIL] [m/MAJOR] [y/YEAR] [g/group GROUP_NUMBER]`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
*  `edit 1 m/ Science nid/e1234567` Edits the major and NUS NetID of the 1st person to be `Science` and `e1234567` respectively.
*  `edit 2 n/Betsy Crower g/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing groups.

### Finding persons by name or student ID: `find`

Finds persons matching the specified criteria.

Format: `find [n/ NAME_KEYWORDS] [id/ STUDENT_IDS]`

* The search is case-insensitive. e.g., `hans` will match `Hans`.
* The order of the keywords does not matter. e.g., `Hans Bo` will match `Bo Hans`.
* Only full words will be matched for names. e.g., `Han` will not match `Hans`.
* Student IDs must match exactly.
* At least one of the optional prefixes must be provided.
* Persons matching any of the criteria will be returned (i.e., `OR` search).
  e.g., `find n/ Hans Bo id/ A1234567E` will return persons whose names contain `Hans` or `Bo`, or whose student ID is `A1234567E`.

Examples:
* `find n/ John` returns persons with names containing `John`.
* `find id/ A1234567E A2345678B` returns persons with student IDs `A1234567E` or `A2345678B`.
* `find n/ alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find n/ alex david'](images/findAlexDavidResult.png)
* `find n/ Alice id/ A1234567E` returns persons whose name contains `Alice` or whose student ID is `A1234567E`.

---
## <u>Deleting a person :</u> `delete`

#### You can remove a student from your list easily!

#### <span style="color:#4CAF50;">Just type: `delete INDEX`</span>

* The `INDEX` is the position of the person your looking at to delete in your current list. Like 1, 2, 3, …​
* Ensure the `INDEX` is a **positive integer**. (We wouldn't want any negative or decimal people right?!)
  ![result for delete example command](images/delete_example.png)

#### Examples:
* If you type `list` followed by `delete 2` it will delete the 2nd student from your current list.
* Or if you search`find /n Nic` followed by `delete 1`, it will delete the 1st student named Nic!

#### Expected Results:
* Easy-peasy! If you managed to delete the student you should see something similar to this!
![result for delete success](images/delete_success.png)

#### <span style="color:#D25B7A;">Common Errors:</span>
* I know it can be hard to learn a new command but don't worry I got you!
* 9 out of 10 times the error you are facing is due to incorrect `INDEX` being used. 
* I'll tell you the exact error in the application itself so just keep a look out for it, here is an example!
![result for delete fail](images/delete_fail.png)

---
### Clearing all entries : `clear`

Clears all entries from Teacher's Pet.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
All persons stored in Teacher's Pet will be deleted permanently and this command cannot be undone. If you wish to save a copy of the data, refer to [FAQ](#faq) for more info)
</div>

Format: `clear`

### Exiting the program : `exit`

Exits Teacher's Pet.

Format: `exit`

### Saving the data

Teacher's Pet data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file (Advanced)

Teacher's Pet data is saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, Teacher's Pet will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause Teacher's Pet to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: Where is my data saved?<br>
**A**: The data file for Teacher's Pet is named `addressbook.json` and is saved in `[JAR file location]/data/addressbook.json` by default. (ie, if your `teacherspet.jar` file is in `Documents` then the data file is found in `Documents/data/addressbook.json`)

**Q**: How do I transfer my data to another Computer?<br>
**A**: 
1. Install the app on the other computer 
2. Run the app on the other computer (See [Quick start](#quick-start) above) 
3. Find the data file on the other computer (See above) and replace the `addressbook.json` file with the `addressbook.json` file from the previous computer

**Q**: How do I keep a copy of my data?<br>
**A**: Find the `addressbook.json` file and copy it to your desired location/storage device

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find [n/ NAME_KEYWORDS] [id/ STUDENT_IDS]`<br> e.g., `find n/ James Jake`, `find id/ A1234567E A2345678B`, `find n/ Alice id/ A1234567E`
**List** | `list`
**Help** | `help`
