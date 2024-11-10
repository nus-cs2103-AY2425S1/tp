---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

<button id="toggleButton" onclick="toggleAllDetails()">Expand All</button>


<body>
  <div style="display: flex; justify-content: center; align-items: center;">
    <span class="parisienne-regular" style="font-size: 3em; font-weight: bold;">
      WedLinker User Guide
    </span>
  </div>
</body>




<details open>
  <summary><strong>What is WedLinker?</strong></summary>
  <p>WedLinker is your <strong>essential desktop app for managing wedding-related contacts</strong>, designed specifically for professional wedding planners.</p>
  <p>With a powerful <strong>Command Line Interface (CLI)</strong>, WedLinker streamlines contact management through quick, intuitive commands. This means you can accomplish your tasks faster than with traditional GUI apps, giving you more time to focus on what matters most—creating memorable experiences for your clients.</p>
</details>

<details open>
  <summary><strong>Why Choose WedLinker?</strong></summary>
  <p>While WedLinker excels with its fast Command-Line Interface (CLI), it also offers a clear and intuitive <strong>Graphical User Interface (GUI)</strong>. The GUI presents your contacts, weddings, and tasks in an organised layout, making it easy to view and manage them at a glance.</p>
  <p>This combination of speed and clarity allows you to manage your wedding planning tasks efficiently and with precision.</p>
</details>


<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------
## Quick start

1. Ensure you have `Java 17` or above installed on your computer by performing the following:

   1.1. Open your operating system's terminal. <br>Follow instructions for your operating system if you are unsure how to:
      <details>
        <summary><strong>Windows:</strong></summary>
        <ol>
          <li>Hold down Windows button and R (⊞ Win + R)</li>
          <li>Type <code>cmd</code> and press enter</li>
        </ol>
      </details>
      <details>
        <summary><strong>MacOS:</strong></summary>
        <ul>
          <li>Click the Launchpad icon in the Dock, type <code>Terminal</code> in the search field, then click Terminal, OR</li>
          <li>In the Finder, open the <code>/Applications/Utilities</code> folder, then double-click Terminal.</li>
        </ul>
      </details>
    <br>

   1.2. Type <code>java -version</code> and press enter.

   1.3. If you have `Java 17` installed, the terminal should look like:
      ```
        C:\Users\UserName>java -version
        java version "17.0.12" 2024-07-16 LTS
        Java(TM) SE Runtime Environment (build 17.0.12+8-LTS-286)
        Java HotSpot(TM) 64-Bit Server VM (build 17.0.12+8-LTS-286, mixed mode, sharing)
      ```

<br>

2. If you have `Java 17` installed, proceed to Step 3. If not, follow these instructions:

   2.1. Download `Java 17` from [here](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
   
    2.2. Select the installation package based on your Operating System.
   
    2.3. Follow the instruction guide to install Java on your device. For more information, click [here](https://docs.oracle.com/en/java/javase/23/install/overview-jdk-installation.html)

<br>

3. Download the latest version of WedLinker from [here](https://github.com/AY2425S1-CS2103T-F15-4/tp/releases/). Select `WedLinker.jar` to begin the download.
   <img alt="release.png" src="images/release.png" width="700" onclick="openModal(this)"/>

<br>

4. Copy the file to the folder you want to use as the _home folder_ for your WedLinker.

<br>

5. Open WedLinker by double-clicking the program file, `WedLinker.jar`.<br>
   A GUI similar to the image below should appear in a few seconds. Note how the app contains some sample data.<br>
   <img alt="Ui" src="images/Ui.png" width="600" onclick="openModal(this)"/>

<br>

6. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/ABC Photography Studio` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>
  <details>
    <summary><strong>Notes about the command format:</strong></summary>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/guest` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/guest`, `t/guest t/photographer` etc.

* Commands in WedLinker uses prefixes to specify the parameters. The prefixes are stated as such:
  * n/ Name
  * a/ Address
  * p/ Phone Number
  * e/ Email
  * t/ Tag
  * w/ Wedding
  * tk/ Task
  * d/ Date

* Parameters can be in any order.<br>
  * e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

  </details>
</box>


<box type="warning" seamless>
  <details open>

<summary><strong>Caution:</strong></summary>

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  * e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* Similarly, extraneous prefixes (e.g. n/ or tk/) for commands that do not take in those prefixes will be processed as part of other inputs.<br>
  * e.g. when [adding a person](#adding-a-person-add), you can specify the prefixes `n/, p/, e/, a/, t/, and w/`. If the command specifies
  `add n/Betsy Crowe d/2020-04-11 tk/Buy place settings`, it will be interpreted as adding a person with the name `Betsy Crowe d/2020-04-11 tk/Buy place settings`

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
  </details>
</box>

<span class="features">General Features</span>

##### Viewing help : `help`

Shows a message explaning how to access the help page.

<img alt="help message" src="images/helpMessage.png" width="500" onclick="openModal(this)"/>

Format: `help`

<br>

##### Listing all Persons : `list`

Shows a list of all saved [Persons](#person-features) in the WedLinker.

Format: `list`

##### Listing all Weddings : `list-weddings`

Shows a list of all [Weddings](#wedding-features) in the WedLinker.

Format: `list-weddings` or `lw`

##### Listing all Tasks : `list-tasks`

Shows a list of all [Tasks](#task-features) in the WedLinker

Format: `list-tasks` or `ltasks`

##### Listing all Tags : `list-tags`

Shows a list of all [Tags](#tag-features) in the WedLinker

<br>

##### Locating contacts by any field: `find`

Similar to a search function, `find` helps to look for all persons based on the specified keywords (case-insensitive) after the prefix representing the field, and displays them as a list with index numbers.

Format: `find PREFIX KEYWORD [KEYWORD]…​`

* The search is case-insensitive. e.g `hans` will match `Hans`.
* The prefix that corresponds to the field you want to search should be specified. e.g. use `find n/Alex` to search by name, use `find e/alex@gmail.com` to search by email.
* The search will return partial matches and full matches.
* Only one field can be searched at a time, but multiple keywords can be searched for the same field by using the by placing each keyword after the appropriate prefix. 
* Only the first prefix entered will be used for the search. For example, if you enter find `find n/Alex a/`, the search will only look for matches in the name field and ignore the address field.
* The order of the keywords does not matter. e.g. `n/Hans n/Bo` will return the same contacts as `n/Bo n/Hans`.

* `find p/973` returns all Contacts whose phone number contains 973
* `find n/alex n/david` returns `Alex Yeoh`, `David Li`<br>
  <img alt="result for &#39;find n/alex n/david&#39;" src="images/findCommandName.png" width="500" onclick="openModal(this)"/>
* `find t/friends` returns all Contacts tagged with 'guest' <br>
  <img alt="result for `find t/guest" src="images/findCommandTag.png" width="500" onclick="openModal(this)"/>
* `find w/Casey's Wedding` returns all Contacts involved with Casey's Wedding <br>

<br>

##### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

<box type="warning" seamless>

<details open>
<summary><strong>Caution:</strong></summary>
This action is irreversible and cannot be undone. Please ensure you have backed up any important information before proceeding.
</details>
</box>

<br>

##### Exiting the program : `exit`

Exits the program.

Format: `exit`

<br>

##### Saving the data

WedLinker data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

<br>

##### Editing the data file

WedLinker data are saved automatically as a JSON file `[JAR file location]/data/WedLinker.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

<details open>
<summary><strong>Caution:</strong></summary>
If your changes to the data file makes its format invalid, WedLinker will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
<br>
Furthermore, certain edits can cause the WedLinker to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</details>
</box>

---

<br>

<span class="features">Person Features<span>

##### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​ [w/WEDDING]…​`

* **People in Wedlinker cannot have the same names**
  * So, if `John Doe` is already in Wedlinker, adding another `John Doe` with different details will not work
* If the tags or weddings specified in the add command do not exist yet, they will be created


Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/XYZ Floral Services`
* `add n/Betsy Crowe e/betsycrowe@example.com a/ABC Photo Studio p/1234567 t/Photographer`

<box type="tip" seamless>

<details>
<summary><strong>Tip:</strong></summary>

- Adding a person with tags or weddings that do not exist in Wedlinker will create all the tags and weddings. Created weddings will have the person automatically assigned to their guest lists.
- A person can have any number of tags or weddings  (including 0) <br><br>
- A person's name must contain only alphanumeric characters
- A person's name can be saved with certain special characters, which can be used to represent different naming conventions:
    - **Hyphen (-)**: Used for double-barrelled names, such as "Mary Doe-Smith".
    - **Slash (/)**: Used to store nicknames or alternative names, like "John/Jonny Doe".
    - **Period (.)**: Used to shorten names, such as "John D. Doe" or "Jane S.".
    - **Apostrophe (')**: Used in names like "O'Hare" or "O'Conner".
</details>
</box>


<box type="warning" seamless>

<details open>
<summary><strong>Caution:</strong></summary>
<br>

- People with single or multiple spaces in their names are considered distinct and treated as separate people. For example:
    - <span class="preserve-whitespace">John Doe vs. John  Doe</span>
    - <span class="preserve-whitespace">TK/Tyler Kennedy vs. TK / Tyler - Kennedy</span>
- Extraneous prefixes in the add command will be processed as part of other inputs.<br>
  - e.g. when adding a person, you can specify the prefixes `n/, p/, e/, a/, t/, and w/`. If the command specifies
    `add n/Betsy Crowe d/2020-04-11 tk/Buy place settings`, it will be interpreted as adding a person with the name `Betsy Crowe d/2020-04-11 tk/Buy place settings`

</details>
</box>

<br>

##### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS]`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower` Edits the name of the 2nd person to be `Betsy Crower`

<br>

##### Deleting a person : `delete`

Deletes the specified person from WedLiker.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find n/Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

---
<br>

<span class="features">Tag Features</span>

##### Adding a tag : `create-tag`

Creates a `Tag` within WedLinker to be used on contacts.

Format: `create-tag t/TAGNAME`

* The `TAGNAME` is alphanumeric and can contain whitespaces.
* Tags are unique in WedLinker, there would not be any duplicated Tags.
* Contacts can share Tags.
* Tags are case-insensitive, so you cannot have both a 'hotel manager' and 'Hotel Manager' tag



<box type="warning" seamless>
<details open>
<summary><strong>Caution:</strong></summary>

- Tags with single or multiple spaces in their names are considered distinct and treated as separate tags. For example:
    - <span class="preserve-whitespace">Venue IC vs. Venue  IC</span>
</details>
</box>

##### Assign tag to contact : `tag`

Assigns a `Tag` to the specified person in WedLinker

Format: `tag INDEX t/TAGNAME [f/]`

* Tag a specified contact based on the `INDEX` with a `Tag`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​.
* The `Tag` must exists in WedLinker before it can be assigned.
* If the `Tag` does not exist, you can use `f/` to force the creation and assignment of the `Tag`.


<box type="tip" seamless>

**Tip:** To see all current tags, use the [list-tags](#listing-all-persons-list) command

</box>

<box type="warning" seamless>
<details open>
<summary><strong>Caution:</strong></summary>
<br>

- Force-tagging a person with extraneous input will cause those extraneous inputs to be ignored. For example:
    - `tag 1 f/ RandomWord t/Tag1` will cause `RandomWord` to be ignored.
</details>
</box>

##### Unassign tag to contacts : `untag`

Untags a `Tag` from a specified person in WedLinker

Format: `untag INDEX t/TAGNAME`

* Untag a specified contact based on their `INDEX` with a `Tag`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​.

##### Deleting a tag : `delete-tag`

Deletes a `Tag` from WedLinker.

Format: `delete-tag t/TAGNAME [f/]`

* Deletes a `Tag` from WedLinker.
* The `Tag` must exists in WedLinker.
* The `Tag` cannot be assigned to any contacts.
* If the `Tag` is in used, you can use `f/` to force the deletion of the `Tag` and unassign this tag from all contacts.

---
<br>

<span class="features">Wedding Features</span>

##### Adding a Wedding : `create-wedding`

Creates a `Wedding` within WedLinker to be with contacts.

Format: `create-wedding w/WEDDINGNAME`

* The `WEDDINGNAME` is alphanumeric and can contain whitespaces.
* Weddings are unique in WedLinker, there would not be any duplicated Weddings.
* Contacts can be assigned to the Wedding using the [assign-wedding](#assign-contact-to-a-wedding-assign-wedding) command.

<box type="warning" seamless>
<details open>
<summary><strong>Caution:</strong></summary>
<br>


- Weddings with single or multiple spaces in their names are considered distinct and treated as separate weddings. For example:
    - <span class="preserve-whitespace">O'Hare's Wedding vs. O'Hare's  Wedding</span>
</details>
</box>

##### Assign contact to a Wedding : `assign-wedding`

Assigns a contact to a `Wedding`.

Format: `assign-wedding INDEX w/WEDDINGNAME [p1/] [p2/]`

* Assigns a specified contact to the `Wedding` based on their `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​.
* The `WeddingName` must be that of a `Wedding` that exists in WedLinker before it can be assigned.
* If the `Wedding` does not exist, you can use `f/` to force the creation and assignment of the `Wedding`.
* Assigning with the optional `p1/` or `p2/` keywords will set that contact as Partner 1 or Partner 2 of the wedding respectively.

##### Edit Wedding details : `edit-wedding`

Edits the details of a `Wedding`.

Format: `edit-wedding INDEX [w/WEDDINGNAME] [a/ADDRESS] [d/DATE]`

* Edits the specific `Wedding` at the INDEX when in [list-wedding](#listing-all-weddings-list-weddings) view.
* The index **must be a positive integer** 1, 2, 3, …​.
* Existing values in the specified fields will be overwritten with the specified values.
* Date must be in "YYYY-MM-DD" format.

##### Unassign contacts from a Wedding : `unassign-wedding`

Unassigns a contact from a `Wedding` in WedLinker.

Format: `unassign-wedding INDEX w/WEDDINGNAME`

* Unassign a contact that is assigned in a `Wedding`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​.

##### Deleting a Wedding : `delete-wedding`

Deletes a `Wedding` from WedLinker.

Format: `delete-wedding w/WEDDINGNAME [f/]`

* Deletes a `Wedding ` from WedLinker.
* The no contacts should be assigned to the `Wedding` before it is deleted.
* If there are still contacts assigned, you can use `f/` to force the deletion of the `Wedding` and unassign all contacts.

---
<br>

<span class="features">Task Features</span>

##### Creating a Task : `create-task`

Creates a `Task` in WedLinker

Format: `create-task tk/TASKDESCRIPTION [REMARKS]`

* Tasks may have no dates, a single date indicating a deadline, or two dates to define a start and end period.
* The dates can be specified under `REMARKS` with the format `d/YYYY-MM-DD`
* Tasks are unique in WedLinker, there would not be any duplicated tasks.
* Tasks can be assigned to a contact using the [assign-task](#assigning-a-task-to-a-contact-assign-task-) command.

<box type="warning" seamless>

<details open>
<summary><strong>Caution:</strong></summary>
<br>

- Tasks with single or multiple spaces in their names are considered distinct and treated as separate tasks. For example:
    - <span class="preserve-whitespace">Send invitations vs. Send  invitations</span>
</details>
</box>

##### Delete a Task : `delete-task`

Deletes a `Task` from WedLinker

Format: `delete-task INDEX`

* Deletes the specific `Task` at the INDEX when in [list-tasks](#listing-all-tasks-list-tasks) view.
* The index **must be a positive integer** 1, 2, 3, …​.

##### Assigning a Task to a contact : `assign-task` 

Format: `assign-task PERSONINDEX TASKINDEX`

* Assigns a `Task` to a contact.
* The indexes correspond to the indexes when in the [list-tasks](#listing-all-tasks-list-tasks) view.
* The `PERSONINDEX` refers to the index of the person shown under the **People** column.
* The `TASKINDEX` refers to the index of the task shown under the **Tasks** column
* The indexes **must be positive integers** 1, 2, 3, …​.

<box type="warning" seamless>
<details open>
<summary><strong>Caution:</strong></summary>
<br>

- Trying to assign multiple tasks to a person in a singular command will cause only 1 of those tasks (at random) to be assigned. For example:
    - `assign-task 1 1 2 3` will assign either task 1, 2, or 3 to be assigned at random (assuming there are at least 3 tasks).
</details>
</box>

##### Un-assigning a Task from a contact : `unassign-task` 

Format: `unassign-task PERSONINDEX TASKINDEX_OFPERSON`

* Un-assigns a `Task` from a contact.
* The `PERSONINDEX` is the index of the person shown in the displayed person list.
* The `TASKINDEX_OFPERSON` is the index of the task associated with the selected person.
* The indexes **must be positive integers** 1, 2, 3, …​.

##### Mark a task as completed : `mark-task` 

Format: `mark-task TASKINDEX`

* Marks a `Task` as completed.
* The index correspond to the index of the task when in the [list-tasks](#listing-all-tasks-list-tasks) view.
* The index **must be a positive integers** 1, 2, 3, …​.

##### Un-mark a task  : `unmark-task` 

Format: `unmark-task TASKINDEX`

* Marks a `Task` as not completed.
* The index correspond to the index of the task when in the [list-tasks](#listing-all-tasks-list-tasks) view.
* The index **must be a positive integers** 1, 2, 3, …​.

---
<br>

<span class="features">Vendor Features</span>

##### Assigning a Vendor : `assign-vendor`
Format: `assign-venor PERSONINDEX`

* Assigns an existing `Person` at the specified `Index` to become a `Vendor`.
* The `Vendor` now can have `Tasks` assigned to it.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​.

##### Unassigning a Vendor : `unassign-vendor`
Format: `unassign-venor PERSONINDEX`

* Unassigns a `Vendor` at the specified `Index` such that the `Person` is no longer a `Vendor`. `Tasks` can no longer be assigned to this `Person`. 
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​.

-------




--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

1. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/florist`
**Assign Wedding**    | `assign-wedding PERSON_INDEX w/WEDDING…​` or `asw PERSON_INDEX w/WEDDING…​` <br> e.g., `asw 2 w/Casey's Wedding w/Wedding August 29th`
**Clear**  | `clear`
**Create Tag**  | `create-tag t/TAG` or `ctag t/TAG` <br> e.g., `create-tag t/photographer`
**Create Task**  | `create-task tk/TASK_TYPE TASK_DETAILS` or `ctask tk/TASK_TYPE TASK_DETAILS` <br> e.g., `create-task tk/todo Order Wedding Cake` <br> `create-task tk/event Meet Caterer /from 2024-11-19 /to 2024-11-20` <br> `create-task tk/deadline Book Venue /by 2025-02-01`
**Create Wedding**  | `create-wedding w/WEDDING` or `cw w/WEDDING` <br> e.g., `cw w/JJ's Wedding`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Delete Tag** | `delete-tag t/TAG` or `dtag t/TAG` <br> e.g., `delete-tag t/photographer`
**Delete Task** | `delete-task INDEX` or `dtask INDEX` <br> e.g., `dtask 6`
**Delete Wedding** | `delete-wedding w/WEDDING` or `dw w/WEDDING` <br> e.g., `delete-wedding w/Lav's Wedding`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Edit Wedding**   | `edit-wedding WEDDING_INDEX [p1/PERSON_INDEX] [p2/PERSON_INDEX] [a/ADDRESS] [d/DATE]…​`<br> or <br> `ew WEDDING_INDEX [p1/PERSON_INDEX] [p2/PERSON_INDEX] [a/ADDRESS] [d/DATE]…​` <br> e.g.,`edit-wedding 2 p1/3 a/Jurong Christian Church`
**Exit**   | `exit`
**Find**   | `find [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG] [w/WEDDING]`<br> e.g., `find n/James Jake`
**Help**   | `help`
**List**   | `list`
**List Weddings**   | `list-weddings` or `lw`
**List Tasks**   | `list-tasks` or `lt`
**Tag**   | `tag INDEX [t/TAG]…​` <br> e.g., `tag 2 t/florist t/photographer`
**Unassign Wedding**    | `unassign-wedding PERSON_INDEX w/WEDDING…​` or `uw PERSON_INDEX w/WEDDING…​` <br> e.g., `uw 2 w/John's Wedding`
**Untag**   | `untag INDEX [t/TAG]…​` <br> e.g., `untag 4 t/available t/chef`

--------------

<br>

<h2 style="text-align: center; color: #E83E8C; font-family: 'Parisienne', cursive;">Frequently Asked Questions</h2>

<details style="background-color: #f9f9f9; border-radius: 5px; padding: 10px; margin: 10px 0;">

  <summary style="font-size: 1.2em; font-weight: bold; color: #333; cursor: pointer;">How do I transfer my data to another computer?</summary>
  <p style="font-size: 1em; color: #555;">To transfer your data to another computer, follow these steps:</p>
  <ol style="color: #555;">
    <li>Install the WedLinker app on the new computer.</li>
    <li>Navigate to the new installation's home folder and locate the empty data file.</li>
    <li>Overwrite this file by replacing it with the data file from your previous computer's WedLinker home folder.</li>
  </ol>
  <p style="font-size: 1em; color: #555;">Once this is done, you should have all your data transferred successfully to the new computer.</p>

</details>
