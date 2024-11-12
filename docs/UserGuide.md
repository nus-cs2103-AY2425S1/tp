---
  layout: default.md
  title: "User Guide"
  pageNav: 4
---

# ContactCS User Guide

**ContactCS** is a **desktop application designed for NUS Computer Science freshmen** to help them efficiently manage and locate essential contact details. The app is designed to store and organize contacts for key individuals relevant to their academic journey, including:
* Professors and teaching assistants for enrolled modules
* Classmates for collaborative projects and study groups
* School offices for administrative matters
* Emergency contacts for urgent situations
* and more!
<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------
## Callouts Convention

The callout boxes below are used in documentation to enhance readability and provide important contextual information.

<box type="info" seamless>

**Info Box:**
Provides additional information or context.
</box>

<box type="tip" seamless>

**Tip Box:**
Offers helpful tips or suggestions.
</box>

<box type="warning" seamless>

**Caution Box:**
Alerts you to potential issues or problems that may arise.
</box>

## Quick start

1. Ensure that Java 17 is installed on your computer.
   * Open a command terminal.
     * For Windows users, follow instructions [here](https://www.howtogeek.com/235101/10-ways-to-open-the-command-prompt-in-windows-10/#open-command-prompt-from-the-file-explorer-address-bar).
     * For Mac users, follow instructions [here](https://support.apple.com/en-sg/guide/terminal/apd5265185d-f365-44cb-8b09-71a064a42125/mac).
   * Type the following command to check the Java version:<br>
     ```
     java -version
     ```
   * If Java 17 is installed, you should see an output similar to: `java version "17.0.1" `
   * If you do not have Java 17, you can download it from [Oracle's official website](https://www.oracle.com/java/technologies/downloads/#java17).
<br><br>
2. Download the application
   * Get the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-F12-1/tp/releases).
<br><br>
3. Set up the home folder
   * Choose a folder where you want to store your ContactCS data.
   * Copy the downloaded `.jar` file to this folder.
   * This folder will serve as the "home folder" for your ContactCS.
<br><br>
4. Run the application
   * Open a command terminal.
   * Navigate (`cd`) to the folder where you placed the `.jar` file.
     * For Windows users, type:
       ```
       cd \path\to\your\folder
       ```
       <box type="warning" seamless>

       **Caution:**
       Replace path\to\your\folder with the actual path. e.g. `cd C:\Documents\ContactCS`

        To find the actual path to your folder: <br>
    
       Press the Windows key + S to open the search bar, type in the name of the file, and right-click the file that appears. Click on copy path.
           </box>

     * For Mac users, type:
       ```
       cd /path/to/your/folder
       ```
       <box type="warning" seamless>

       **Caution:**
       Replace path/to/your/folder with the actual path. e.g. `cd ~/Documents/ContactCS`

       To find the actual path to your folder: <br>
       1. Navigate to the file in the Finder and select it.
       2. Right-click or Control-click on it and choose Get Info.
       3. In the window that opens, look at the Where field.
       4. Copy the path from the Where field.
       
       </box>
   * Run the application with the following command:
     ```
     java -jar contactcs.jar
     ```
   A GUI similar to the screenshot below should appear in a few seconds. Note how the app contains some sample data.<br>![UiStartUp](images/UiStartUp.png)
<br><br>
5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com r/CS1101S` : Adds a contact named `John Doe` who takes CS1101S to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.
<br><br>
6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------
## Overview of GUI
Once ContactCS is running, you’ll see the main interface divided into several key panels, as illustrated below. Here’s an overview of each panel:

* Command Box: You can type and execute commands here .

* Result Display Box: Shows the outcome of the commands you enter, including success messages, errors, and other feedback.

* Contact List: Displays the list of contacts, allowing easy access to contact information.

* Command History Window: Contains a history of commands that modify the contact list, enabling you to quickly refer to recent modifications.

* Menu (File/Help): Provides options such as exiting the application and accessing help documentation.

* Data Storage Location Footer: Shows the directory where the contact data are saved, providing quick access to storage information.
<br><br>
![Annotated GUI](images/AnnotatedGui.png)

<box type="info" seamless>

**Info: Notes about the command history**<br>
The **Command History Window** only displays commands that directly modify the contact list, including:
* `add` - Adds a new contact.
* `edit` - Edits details of an existing contact.
* `delete` - Removes specified contacts.
* `clear` - Clears all contacts from the list.

Commands that do not alter the contact data, such as `list`, `find`, `help`, and `exit`, are **not** shown in the command history window.
This helps keep the command history focused on actions that impact the contact list.
</box>
## Features
<box type="info" seamless>

**Info: Notes about the command format**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items in **square** brackets and with `+` after them can be used zero or more times.<br>
  e.g. `[t/TAG]+` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Items in **round** brackets and with `+` after them can be used one or more times.<br>
  e.g. `(t/TAG)+` can be used as `t/friend`, `t/friend t/family` etc.

* `|` operator signifies `OR` relationship.<br>
  e.g. `n/NAME | r/MODULECODE` means `n/NAME` or `r/MODULECODE`

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `exit 123`, it will be interpreted as `exit`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help: `help`

Help command supports two input formats which allows for more flexibility when
you want to seek help:

Format: `help [COMMAND_KEYWORD]`

**Shows a message explaining the usage of the specified command keyword**

Example:
- `help add` shows the help message for `add` command in the following format
![help_example](images/helpExample.png)

Format: `help`

**Shows a message and a pop-up window showing the full list of help messages,
and an external link to the full user guide.**

Example:
- ![help message](images/helpMessage.png)


### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME (p/PHONE_NUMBER | e/EMAIL | p/PHONE_NUMBER e/EMAIL) [r/MODULECODE[-ROLETYPE]]+ [a/ADDRESS] [t/TAG]+ [d/DESCRIPTION]`

<box type="information" seamless>
The command accepts either one phone number, one email, or both.
</box>

* `NAME` can take any values and can not be blank. Refer to the [input format section](#input-format) to find out more.
* `PHONE_NUMBER` is almost a free-form text field with minimal validation. Refer to the [input format section](#input-format) to find out more.
* ContactCS expects the ``MODULECODE`` to be formatted as valid NUS module codes such as 'CS2040S' or 'MA1521', without spaces or additional characters.
* `ROLETYPE` refers to one of the following: `student`, `ta`, `tutor`, `prof`, `professor`.
* The `r/MODULECODE[-ROLETYPE]` parameter means that the person has the role for this module (e.g. `r/CS1101S-student` means that the person is a student of CS1101S).
* In `r/MODULECODE[-ROLETYPE]`, `[-ROLETYPE]` is optional. In such cases, this means that the person is a student of that module (e.g `r/MA1521` means that the person is a student of MA1521).
* If the same module is added multiple times, then it is assumed to be an error in user input, because a person should not have multiple roles (student, tutor, professor) at the same time (e.g. `r/CS1101S-student r/CS1101S-prof` is not allowed).
* Note: A professor is not considered a teaching assistant (TA).
* Email addresses are considered valid even if they do not contain a period(``.``). For example, ``example@domain`` is considered valid.
* `ADDRESS` can take any values and can not be blank.
* `TAG` can take any alphanumeric values and can not be blank.
* `DESCRIPTION` can take any values but cannot exceed 500 characters.

For more explanation on the format and design of each input field, refer to the [input format section](#input-format).

<box type="info" seamless>

**Info: Duplicate Handling**
- A person is considered a duplicate if another person in the address book has the same email address or phone number. The app will prevent adding contacts with duplicate emails or phone numbers.
- For the same reason, the app will prevent the user from changing the email address or phone number of a contact to one that is already in use by another contact.

</box>

<box type="info" seamless>

**Info: Input data containing prefixes**
- Should any of the fields contain reserved prefixes (e.g. `add ... d/For a/b testing` contains `a/` which is reserved), the message will be split into multiple fields.
- To work around this, you can add a character in front of the reserved prefix (e.g. `add ... d/For 'a/b testing`).
- This issue will be fixed in a later version using a special delimiter to handle such cases.

</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com r/CS1101S`. John is a CS1101S student.
  ![result for adding John Doe](images/addJohnDoeResult.png)
* `add n/Jane Doe p/81234567 e/janed@example.com r/CS1101S-TA r/CS2040S`. Jane is a CS1101S tutor and a CS2040S student.

### Listing all persons: `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person: `edit`

Edits an existing person in the address book.

#### Editing module-role

The module-role pairs can be edited by adding and deleting.

##### Adding new module-role pairs

Format: `edit INDEX r/+(MODULECODE[-ROLETYPE])+`

* Adds new roles to the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* At least one module-role pair must be provided.
* Multiple module-role pairs can be added at once, separated by `" "`.
* The role type can be omitted, in which case the role `STUDENT` will be the default.

Examples:
* `edit 1 r/+CS2103T-Prof` adds role "professor of CS2103T" to the first person.
* `edit 1 r/+CS1101S MA1521-TA` adds role "Student of CS1101S" and "TA of MA1521" to the first person.

<box type="warning" seamless>

**Caution: Common Mistakes**
- If you are adding multiple module-role pairs, only the first pair should have a `+` sign before the module-role pair.
The subsequent pairs should not have a `+` sign before them. i.e. `r/+CS1101S +MA1521-TA` is unnecessary and will cause an error.
- You only need to specify one `r/`. i.e. `r/+CS1101S r/+MA1521-TA` is unnecessary and will cause an error.
</box>

<box type="warning" seamless>
  Please note that the first + should be typed as is while the second + is the multiplicity syntax explained here.
</box>

##### Deleting existing module-role pairs

Format: `edit INDEX r/-(MODULECODE[-ROLETYPE])+`
* Deletes existing roles from the person at the specified `INDEX`. The index refers to the index number shown in the
displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* At least one module-role pair must be provided.
* Multiple module-role pairs can be deleted at once, separated by `" "`.
* The role type can be omitted, in which case **any associated role will be deleted.**
i.e. `edit 1 r/-MA1521` will delete `MA1521-Student`, `MA1521-TA` or `MA1521-Prof`, whichever is present.

Examples:
* `edit 1 r/-CS2103T` deletes any role related to module `CS2103T` from the first person.
* `edit 1 r/-CS1101S-Student MA1521-TA` deletes the role "Student of CS1101S" and "TA of MA1521" from the first person.

<box type="tip" seamless>

**Tip:**
- Omitting the role type intentionally leads to two different behaviors for adding and deleting roles:
  - For adding roles, the role type is assumed to be `Student`.
  - For deleting roles, **any role associated with the module code** will be deleted, regardless of the role type.

</box>

#### Editing all other fields

Except for the module-role pairs, all other fields can only be edited by complete replacement.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]+ [d/DESCRIPTION]`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.
* Similarly, you can remove a person's description by typing `d/` without specifying any description after it.
* After editing, the app will go back to the main window and display the updated person list.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

<box type="caution" seamless>

**Caution:**
If you input multiple indices separated by spaces, e.g.`edit 1 2 n/...`, the app will treat `1 2` as a single index which is invalid.
</box>

### Locating persons: `find`

The find command allows you to locate persons by their names, module-role pairs, tags or any combinations of them.

#### By name

Finds persons whose names contain any of the given keywords.

Format: `find (n/KEYWORD)+`

* The search is case-insensitive. e.g `hans` will match `Hans`
* Each keyword can contain multiple words. e.g. `John Doe`
* The keyword must exist contiguously in the name. e.g. `John Doe` will not match `John David Doe`
* Only the name is searched.
* Partial words will be matched as well. e.g. `Han` will match `Hans`
* Persons whose names matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `find n/Hans n/Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find n/John` returns `john` and `John Doe`
* `find n/alex n/david li` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find n/alex n/david'](images/findAlexDavidResult.png)

#### By module-role

Finds persons whose module-role pairs contain any of the given keywords.

Format: `find (r/KEYWORD)+`

* Search by module code and optionally specify the role type (separated by a dash). For example, `CS2103T-Prof` will search for the module `CS2103T` with the role `Professor`.
* The search is case-insensitive. e.g. `cs2103t-student` will match `CS2103T-Student`.
* If the role type is not specified, role `STUDENT` will be assumed. For example, `find r/CS2103T` will return all students taking `CS2103T`.
* Persons whose module-role pairs matching at least one module-role keyword will be returned (i.e. OR search).

Examples:
* `find r/CS2103T` returns all students taking the module `CS2103T`
* `find r/CS2103T-Prof r/CS1101S` returns all persons with the role Prof in CS2103T or Student in CS1101S

  ![result for 'find r/cs2103t-prof r/cs1101s'](images/findModuleRoleExample.png)

#### By tag

Finds persons whose tags contain any of the given keywords.

Format: `find (t/KEYWORD)+`

* The search is case-insensitive. e.g `Friends` will match `friends`
* Partial words will be matched as well. e.g. `class` will match `classmates`
* Persons whose tags matching at least one tag keyword will be returned (i.e. OR search)

Examples:
* `find t/office` returns all contacts whose tags contain word `office`
* `find t/classmates t/friends` returns all contacts whose tags contain either `classmates` or `friends`

    ![result for 'find t/classmates t/friends'](images/findTagExample.png)

#### By combinations of names, module-roles and tags

Finds persons whose names, module-role pairs and tags contain any combination of the given keywords.

Format: `find (n/KEYWORD | r/KEYWORD | t/KEYWORD)+`

* Persons matching at least one name keyword (if provided) AND at least one module-role keyword (if provided) AND at least one tag keyword (if provided) will be returned (i.e. AND search).

Example:
* `find n/Martin n/Boyd r/cs1101s-prof r/cs1231s-prof t/favorite` return all persons whose name are either (`Martin` **or** `Boyd`) **and** (`CS1101S Professor` **or** `CS1231S Professor`) **and** (having tag name `favorite`).

  ![result for 'find n/Martin n/Boyd r/cs1101s-prof r/cs1231s-prof t/favorite'](images/findCombinedConditionExample.png)

<box type="info" seamless>

**Info: Chained Find**
The Chained Find feature allows you to narrow down previous search results by applying additional filters,
making it easier to locate specific entries that meet multiple criteria.<br>

**How to Use Chained Find**

* Start with an Initial Search:
  * Begin by using the find command with your first search criterion.

* Apply Additional Filters with find chained:
  * Use the find chained command immediately after the initial search to further filter the displayed results based on new criteria.

**Example**
* Step 1: type `find n/John` and hit enter. You will see all entries with "John" in their names;
* Step 2: type `find chained n/Doe` and hit enter. This time you will see only the entries that contain both "John" and "Doe" in their names.

</box>

#### By other fields
_coming in v2.0_

### Deleting persons: `delete`

Deletes the specified person from the address book.

Format: `delete (INDEX)+`

* Deletes the person(s) at the specified INDEX(es).
* The index refers to the index number shown in the displayed person list.
* The indices **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 1 2 3` deletes the 1st, 2nd and 3rd person in the address book.
* `find n/Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries: `clear`

Clears all contacts from the address book.

Format: `clear`

### Undoing latest change to contact data: `undo`
Undoes the effect done by the latest data-modifying command, if any.

Format: `undo`

* Data-modifying commands refers to those who has direct manipulation over the contact data,
such as add, edit, delete or clear.
* Commands that do not modify contact data in the address book(list, help, find etc.)
are not considered by the undo command.
* If you input `undo` when there's nothing to undo, GUI will remind you about this
and no effect would be applied to the address book.

Examples:
* If you accidentally delete a contact using the `delete` command,
you can revert this change by input `undo` command.
![result for 'delete 1'](images/accidentallydelete.png)
![result for 'undo'](images/undoing.png)
* Similarly, if you accidentally clear the whole address book using the `clear` command,
you can restore the whole address book using `undo` command as well.
* Wrong adding/editing of contact info can be reverted by inputting `undo` as well.

### Redoing latest undone change to contact data: `redo`
Redoes the latest undone modification on contact data, if any.
Format: `redo`

* Redo only helps to restore command results that can be handled by undo command,
such as add, edit, delete and clear.
* If you input `redo` when there's nothing to redo, GUI will remind you about this
and no effect would be applied to the address book.
![result for 'no more redo'](images/nomoreredo.png)

Examples:
* If you undo a change to the contact data, but end up thinking that it may be
better to keep it, you can input `redo` after undo the change to restore it back.
* Taking the same example from the undo session:
  ![result for 'redo'](images/redoing.png)

<box type="warning" seamless>
The undo and redo functions do not persist between sessions. Any changes you undo or redo will only be remembered during the current session. Once you close the application or end the session, the history of your actions (undo and redo steps) is lost and cannot be accessed in future sessions.
</box>

### Exiting the program: `exit`

Exits the program.

Format: `exit`

### Saving the data

ContactCS data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

ContactCS data are saved automatically as a JSON file `[JAR file location]/data/contactcs.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, ContactCS will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the ContactCS to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## Input format

### `NAME` field

In our application, we understand that everyone's names can have various characters and symbols, thus we decided that as long as it is not a blank string, it is considered acceptable.

### Concept of a phone number

In our application, the concept of a phone number is defined as:

1. a string without any whitespace,
2. with at least 2 digits,
3. without any alphabet characters,
4. and may contain additional characters such as but not limited to "+", "-", "(", and ")".

Some valid phone numbers include `+6581234567`, `81234567`, or `+44-1234567`.

Some invalid phone numbers include `+6 5 8 1 2 3 4 5 6 7`, or `8123p4567`.

### `PHONE_NUMBER` field

The `PHONE_NUMBER` field (specified in the `add` or `edit` commands) is defined as a string where, if split by spaces, at least one of the resulting tokens is a valid phone number.

Some valid `PHONE_NUMBER` values include `81234567`, `81234567 (handphone)`, or `81234567 (office 1) 91234567 (office 2)`.
Since a contact may have different phone numbers at the same time, such as mobile, office, home etc, and any length of annotation to differentiate between them,
we decide not to enforce any input length restriction on this field, to offer you more flexibility when taking down and annotating phone numbers of a contact.

<box type="caution" seamless>

**Caution:**
To allow more flexibility in the input format, we have to sacrifice some validation checks. As such, it is important to ensure that the phone number you input is correct.
</box>

### `MODULE_ROLE` field

The `MODULE_ROLE` field represents the role of a contact, such as CS1101S Student, CS1231S Tutor or MA1522 Professor and so on.
It consists of two sub-fields: `MODULECODE` and `ROLETYPE`.

#### `MODULECODE` field
The `MODULECODE` field refers to the module codes of modules in NUS, and is defined by at least one alphabet followed by at least one number
and lastly ended by an optional sequence of numbers.

Some valid module code inputs include `CS1231S`, `CS1231`, `CFG2002MY` and `DMA1201CH`.

Take note that even though the modules that can be taken by NUS Y1 CS Students are quite limited, we recognize that they may take on double majors/minors, or courses from
DYOC (Design Your Own Course) Scheme, hence we do not enforce strict validation to check whether the provided module code represents a valid NUS CS module and only checks on its
basic format. Since DYOC courses may have potentially longer module code, we do not enforce input length restrction either.

#### `ROLETYPE` field
The `ROLETYPE` field refers to the role related to the module code provided, such as student, professor and tutor.
Some valid role type inputs include:
* leave blank or student for student role(default value)
* ta or tutor for tutor role
* prof or professor for professor role

Take note that each role type only represents a single role, and you should define the role type of a person based on
the most accurate description of this person' role. For example, if a professor is also the tutor of the course, it is
better to specify the role of this contact as the professor of this course instead of tutor. Similarly, even though it is
unlikely for a contact to be a professor and a student at the same time, we do not enforce strict validation on this to give
you more flexibility in annotating your contact and avoid potential input issues in rare scenario due to overzealous validation.

### `ADDRESS` field

The `ADDRESS` field can be used to refer to the address of any location related to a contact, such as home, office, consultation venue etc.
Since the length of address input may differ drastically because of the nature of the location and the complexity in its address name structure,
we do not enforce restriction on the input length and as long as the input is not blank, it is considered as a valid address.

Some valid `ADDRESS` values include `COM3-01-20`, `#05-03, Blk 211, Any Place Street 123, Singapore 123456`, `Utown Residence #12-34` etc.

### `TAG` field

The `TAG` field allows you to classify contacts in the address book easily, and you can use find by tag feature to query them more easily.
For more detail on find by tag feature, refer to [find contacts by tag section](#by-tag) for more details.

Some valid `TAG` values include `friends`, `office` and `classmates`.

### `DESCRIPTION` field

The `DESCRIPTION` field aims to provide you a simple way to annotate a contact with some basic information for easy reference in the future.
For example, you can use it to record down how you feel about a professor's way of teaching, or remember the important birthday of a friend in NUS SOC
or even zoom link of a lecture/tutorial session taught by a professor/tutor.As you can see, we try to provide as much flexibility to you as possible to
allow you to record any short pieces of information about the contact, but we also want it to be short. Hence, we restrict the input size of the description
to be 500 characters which should be enough for most users.


--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous ContactCS home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.
3. **There is an unused space below the command history box**.
![unused space](images/UiUnusedSpace.png)<br>
As shown in the screenshot above, marked by the red box.
4. **When you add a duplicate contact with same phone number**, the app will allow it if the phone number is the same but with different country codes or descriptions.
For example, adding a contact with the phone number `+65 6601 7878 (24 hrs)` and then adding another contact with the phone number `6601 7878` will be allowed as long as the emails are different.

--------------------------------------------------------------------------------------------------------------------

## Command summary

 Action     | Format, Examples
------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 **Add**    | `add n/NAME (p/PHONE_NUMBER \| e/EMAIL \| p/PHONE_NUMBER e/EMAIL) [r/MODULECODE[-ROLETYPE]]+ [a/ADDRESS] [t/TAG]+ [d/DESCRIPTION]` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com r/CS1101S a/123, Clementi Rd, 1234665 t/friend t/colleague d/A good guy`
 **Clear**  | `clear`
 **Delete** | `delete (INDEX)+`<br> e.g., `delete 3` or `delete 1 3 5`
 **Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]+ [r/(+\|-)(MODULECODE[-ROLETYPE])+] [d/DESCRIPTION]`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com r/+CS2030S CS1101S-TA`
 **Find**   | `find [chained] (n/KEYWORD \| r/KEYWORD \| t/KEYWORD)+`<br> e.g., `find chained n/James n/Jake r/CS1101S r/MA1521 t/friends t/classmates`
 **Undo**   | `undo`
 **Redo**   | `redo`
 **List**   | `list`
 **Help**   | `help [COMMAND_KEYWORD]`<br> e.g., `help add` or `help`
 **Exit**   | `exit`
