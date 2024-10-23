---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200&icon_names=chevron_right" />

<style>
  @import url('https://fonts.googleapis.com/css2?family=DM+Sans:ital,opsz,wght@0,9..40,100..1000;1,9..40,100..1000&family=Nunito:ital,wght@0,200..1000;1,200..1000&display=swap');

  :root {
    --primary-bg-color: rgb(248, 248, 248);
    --special-bg-color: rgb(235, 243, 255);
    --highlight-bg-color: rgb(235, 235, 235);
    --white-bg-color: rgb(255, 255, 255);
    --code-color: rgb(239, 152, 46);
    --code-bg-color: rgb(245, 245, 245);
    --box-bg-color: rgba(239, 152, 46, 0.4);
    --border-color: rgb(220, 220, 220);
  }

  * {
    font-family: 'DM Sans', sans-serif;
    transition: 0.2s ease;
  }

  .box {
    background-color: var(--box-bg-color) !important;
    padding: 8px 16px;
    margin: 16px 0;
    border-radius: 5px;
  }

  .box > p {
    margin: 0;
  }

  .headers {
    font-weight: 900;
  }

  .content {
    background-color: var(--primary-bg-color);
    border-radius: 10px;
    padding: 32px;
    margin: 16px;
  }

  .sub-content {
    padding: 16px;
  }

  .content-special {
    background-color: var(--special-bg-color);
  }

  .content-droppable {
    padding: 24px 32px 16px 32px;
    margin: 0px 16px;
    font-size: 1.2em;
    border-top: 1px solid var(--border-color);
    background-color: var(--white-bg-color);
    border-radius: 0px;
  }

  .content-droppable:hover {
    background-color: var(--highlight-bg-color);
  }

  .content-qna {
    padding: 24px 32px;
    font-size: 0.9em;
  }

  .content-command {
    background-color: var(--white-bg-color);
    padding: 8px;
  }

  summary {
    cursor: pointer;
    margin-bottom: 8px;
    display: flex;
    transition: 0s ease;
    font-size: 1.2em;
  }

  details[open] > summary > .chevrons {
    transform: rotate(270deg);
  }

  code {
    color: var(--code-color) !important;
    background-color: var(--code-bg-color) !important;
  }

  .chevrons {
    transform: rotate(90deg) translateX(0px);
    text-decoration: none;
    background-color: transparent !important;
    margin: auto;
    margin-right: 0;
  }

  .command-content {
    padding: 24px 16px 8px 16px;
  }

  .qna-content {
    padding: 16px 16px 0px 16px;
  }

  .toc-content {
    padding: 0;
  }

  .toc-btns {
    background-color: rgba(255, 255, 255, 0);
    border: none;
    border-top: 1px solid var(--border-color);
    font-family: 'DM Sans', sans-serif;
    border-bottom: none;
    border-radius: 0px;
    color: black;
    padding: 24px;
    text-align: left;
    text-decoration: none;
    font-size: 16px;
    font-weight: bold;
    cursor: pointer;
    width: 100%;
    text-align: left;
  }

  .toc-btns-first, .content-droppable-first {
    border: none;
    border-top-left-radius: 10px;
    border-top-right-radius: 10px;
  }

  .toc-btns-last, .content-droppable-last {
    border-bottom-left-radius: 10px;
    border-bottom-right-radius: 10px;
  }

  .toc-btns:hover {
    background-color: var(--highlight-bg-color);
  }
</style>

<h1 class="headers">StaffSync User Guide</h1>

<p class="content content-special">
  StaffSync is a <strong>desktop app for managing potential hires and employees, optimized for use via a  Line Interface</strong> (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, StaffSync can get your contact management tasks done faster than traditional GUI apps.
</p>

<br>

<h1 class="toc headers">Table of Contents</h1>

<div class="content toc-content">
  <a href="#installation-and-quick-start">
    <button class="toc-btns toc-btns-first">1. Installation and Quick Start</button>
  </a>
  <a href="#commands">
    <button class="toc-btns">2. Commands</button>
  </a>
  <a href="#command-summary">
    <button class="toc-btns">3. Commands Summary</button>
  </a>
  <a href="#features">
    <button class="toc-btns">4. Features</button>
  </a>
  <a href="#faq">
    <button class="toc-btns">5. FAQ</button>
  </a>
  <a href="#known-issues">
    <button class="toc-btns toc-btns-last">6. Known Issues</button>
  </a>
</div>

<br>

<h1 class="headers" id="installation-and-quick-start">Installation and Quick Start</h1>


<div class="content">

<strong>1. </strong>Ensure you have [Java `17`](https://www.oracle.com/java/technologies/downloads/#java17) installed in your Computer. Higher versions may work but we do not officially support it.

<br>

<strong>2. </strong>Download the latest release `.jar` file from [our Github page](https://github.com/AY2425S1-CS2103T-T10-2/tp/releases).

<br>

<strong>3. </strong>Copy the file to the folder you want to use as the _home folder_ for your StaffSync.

<br>

<strong>4. </strong>Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar staffSync.jar` command to run the application.<br>

   Alternatively, you can double-click the jar file to run the application (though we do not officially support it).

   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

<br>

<strong>5. </strong>Type the command in the command div class="box" and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list ph` : Lists all potential hires.

   * `employee n/John Doe p/81234567 e/pohjunkang@gmail.com a/21 Lower Kent Ridge Rd d/Department of communications and informatics r/Head of communications and Informatics ced/2021-01-01` : Adds an employee named `John Doe` to StaffSync.

   * `delete ph 1` : Deletes the 1st person shown if they are a potential hire.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

<br>

<strong>6. </strong>Refer to the [Commands](#commands) below for details of each command.

</div>

<br>

<h1 class="headers" id="commands">Commands</h1>


<div class="content content-special">

**Notes about the command format:**

<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `employee n/NAME`, `NAME` is a parameter which can be used as `employee n/John Doe`.

* Items in square brackets are optional.
  e.g. `edit INDEX [n/NAME] [e/EMAIL]` can be used as `edit 1 n/John Doe e/johndoe@gmail.com` or `edit 1 n/John Doe`.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Parameters and commands are case-sensitive
  e.g. the command `Help` and `edit 1 N/John Doe` will return an error

* Extraneous parameters for commands that do not take in parameters (such as `help`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters >surrounding line-breaks may be omitted when copied over to the application.

</div>


<div class="content content-droppable content-droppable-first">

<details>
  <summary>
    <strong>Clearing all entries<br><code>clear</code></strong>
    <span class="material-symbols-outlined chevrons">
      chevron_right
    </span>
  </summary>

  <div class="command-content">
  Clears all entries from StaffSync.
  
  <br>
  <br>

  Format: `clear`
  </div>
</details>

</div>
<div class="content content-droppable">

<details>
  <summary><strong>Deleting a person<br><code>delete</code></strong>
    <span class="material-symbols-outlined chevrons">
      chevron_right
    </span>
  </summary>

  <div class="command-content">
  Deletes the specified person from StaffSync.

  <br>
  <br>

  Format: `delete ph INDEX` or `delete e INDEX` *(Note the `ph` or `e` parameter. `ph` refers to potential hires and `e` refers to employees)*

  * Deletes the potential hire/employee at the specified `INDEX`.
  * The index refers to the index number shown in the displayed person list.
  * The index **must be a positive integer** 1, 2, 3, …​
  * The index **must be within the size of the list** of potential hires/employees shown.

  Examples:
  * `list` followed by `delete e 2` deletes the 2nd person in the employee list.
  * `find e Betsy` followed by `delete e 1` deletes the 1st employee in the results of the `find` command.
  </div>
</details>

</div>
<div class="content content-droppable">

<details>
  <summary><strong>Demoting a person<br><code>demote</code></strong>
    <span class="material-symbols-outlined chevrons">
      chevron_right
    </span>
  </summary>

  <div class="command-content">
  Demotes the specified employee from StaffSync into a potential hire.

  <br>
  <br>

  Format: `demote INDEX`

  * Demote the employee at the specified `INDEX` to a potential hire.
  * The index refers to the index number shown in the displayed person list.
  * The index **must be a positive integer** 1, 2, 3, …​
  * The index **must be within the size of the list** shown.
  * The person at the index must be an employee

  Examples:
  * `list e` followed by `demote 2` demotes the 2nd person in the employee list.
  * `find e Betsy` followed by `demote 1` demotes the 1st employee in the results of the `find` command.

  <div class="box" type="warning" seamless>
    
  **Common Mistakes:**
  * `list ph` followed by `demote 2` - cannot demote a potential hire
  * `demote 0` - invalid index number
  * `demote 3` but only have 2 entries - invalid index number, index out of list size 
  </div class="box">
  </div>
</details>

</div>
<div class="content content-droppable">

<details>
  <summary><strong>[WIP] Editing a person<br><code>edit</code></strong>
    <span class="material-symbols-outlined chevrons">
      chevron_right
    </span>
  </summary>

  <div class="command-content">
  Edits an existing person in the StaffSync.

  <br>
  <br>

  Format: `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [d/DEPARTMENT] [r/ROLE] [ced/CONTRACT_END_DATE]`

  * Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
  * At least one of the optional fields must be provided.
  * Existing values will be updated to the input values.

  <div class="box" type="tip" seamless>
  
  **Tip:** It is not possible to edit the contract end date of a potential hire
  </div class="box">

  Examples:
  *  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
  *  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.
  </div>
</details>

</div>
<div class="content content-droppable">

<details>
  <summary><strong>Adding an employee<br><code>employee</code></strong>
    <span class="material-symbols-outlined chevrons">
      chevron_right
    </span>
  </summary>


  <div class="command-content">
  Adds an employee to StaffSync.

  <br>
  <br>

  Format: `employee n/NAME p/PHONE_NUMBER a/ADDRESS e/EMAIL d/DEPARTMENT r/ROLE ced/CONTRACT_END_DATE`

  <div class="box" type="tip" seamless>
    
  **Tip:** All fields are mandatory
  </div class="box">

  Examples:
  * `employee n/Jun Kang p/81234567 a/21 Lower Kent Ridge Rd e/pohjunkang@gmail.com d/Department of communications and informatics r/Head of communications and Informatics ced/2021-01-01`
  </div>
</details>

</div>
<div class="content content-droppable">

<details>
  <summary><strong>Exiting the program<br><code>exit</code></strong>
    <span class="material-symbols-outlined chevrons">
      chevron_right
    </span>
  </summary>

  <div class="command-content">
  Exits the program.

  <br>
  <br>

  Format: `exit`
  </div>
</details>

</div>
<div class="content content-droppable">

<details>
  <summary><strong>Locating persons by name<br><code>find</code></strong>
    <span class="material-symbols-outlined chevrons">
      chevron_right
    </span>
  </summary>

  <div class="command-content">
  Finds the employee/potential hire whose names contain any of the given keywords.

  <br>
  <br>

  Format: `find (all or e or ph) KEYWORD [MORE_KEYWORDS]`

  * The search is case-insensitive. e.g `hans` will match `Hans`
  * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
  * Only the name is searched.
  * Only full words will be matched e.g. `Han` will not match `Hans`
  * Persons matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

  Examples:
  * `find all John` returns people `john` and `John Doe`
  * `find e John` returns employees `john` and `John Doe`
  * `find ph alex david` returns potential hires `Alex Yeoh`, `David Li`<br>
    ![result for 'find alex david'](images/findAlexDavidResult.png)
  </div>
</details>

</div>
<div class="content content-droppable">

<details>
  <summary><strong>Viewing help<br><code>help</code></strong>
    <span class="material-symbols-outlined chevrons">
      chevron_right
    </span>
  </summary>

  <div class="command-content">
  Displays a help window containing the list of commands, its purpose and the format

  <br>
  <br>

  ![Ui](images/helpWindow.png)

  Format:`help`
  </div>
</details>

</div>
<div class="content content-droppable">

<details>
  <summary><strong>Listing all persons<br><code>list</code></strong>
    <span class="material-symbols-outlined chevrons">
      chevron_right
    </span>
  </summary>

  <div class="command-content">
  Shows a list of all persons in StaffSync.

  <br>
  <br>

  Format: `list all` or `list ph` or `list e` *(Note the `ph` or `e` parameter. `ph` refers to potential hires and `e` refers to employees)*
  * Lists type of persons based on the parameter given.
  </div>
</details>

</div>
<div class="content content-droppable">

<details>
  <summary><strong>Adding a potential hire<br><code>potential</code></strong>
    <span class="material-symbols-outlined chevrons">
      chevron_right
    </span>
  </summary>

  <div class="command-content">
  Adds a potential hire to StaffSync.

  <br>
  <br>

  Format: `potential n/NAME p/PHONE_NUMBER a/ADDRESS e/EMAIL d/DEPARTMENT r/ROLE`

  <div class="box" type="tip" seamless>
    
  **Tip:** All fields are mandatory
  </div class="box">

  Examples:
  * `potential n/Jun Kang p/81234567 a/21 Lower Kent Ridge Rd e/pohjunkang@gmail.com d/Department of communications and informatics r/Head of communications and Informatics`
  </div>
</details>

</div>
<div class="content content-droppable content-droppable-last">

<details>
  <summary><strong>Promoting a person<br><code>promote</code></strong>
    <span class="material-symbols-outlined chevrons">
      chevron_right
    </span>
  </summary>

  <div class="command-content">
  Promotes the specified potential hire from StaffSync into an employee.

  <br>
  <br>
  Format: `promote INDEX CONTRACT_END_DATE`

  * Promote the potential hire at the specified `INDEX` to an employee with the specified `CONTRACT_END_DATE`.
  * The index refers to the index number shown in the displayed person list.
  * The index **must be a positive integer** 1, 2, 3, …​
  * The index **must be within the size of the list** shown.
  * The person at the index must be a potential hire
  * The contract end date must be of **valid format** of yyyy-MM-dd

  Examples:
  * `list p` followed by `promote 2 2025-12-20` promotes the 2nd person in the potential hire list with a contract end date of 20 Dec 2025.
  * `find p n/Betsy` followed by `promote 1 2025-12-20` promotes the 1st potential hire in the results of the `find` command with a contract end date of 20 Dec 2025.

  <div class="box" type="warning" seamless>
    
  **Common Mistakes:**
  * `promote 2 12-20-2025` - the contract end date is in the wrong date format
  * `promote 2 2025-20-12` - the day and the month of the contract end date is swapped
  * `list e` followed by `promote 2 2025-12-20` - cannot promote an employee
  * `promote 0 2025-12-20` - invalid index number
  * `promote 3 2025-12-20` but only have 2 entries - invalid index number, index out of list size
  </div class="box">
  </div>
</details>

</div>

<br>

<h1 class="headers" id="command-summary">Commands Summary</h1>

<div class="content content-command">

Action     | Format                                                                                        | Examples
-----------|-----------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------
**Clear**  | `clear`                                                                                       |
**Delete** | `delete e INDEX` <br> `delete ph INDEX`                                                       | `delete e 3`<br> `delete ph 1`
**Demote** | `demote INDEX`                                                                                | `demote 2`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [d/DEPARTMENT] [r/ROLE] [ced/CONTRACT_END_DATE]` | `edit 2 n/James Lee e/jameslee@example.com`
**Employee**| `employee n/NAME p/PHONE_NUMBER a/ADDRESS e/EMAIL d/DEPARTMENT r/ROLE ced/CONTRACT_END_DATE​` | `employee n/Jun Kang p/81234567 a/21 Lower Kent Ridge Rd e/pohjunkang@gmail.com d/Department of communications and informatics r/Head of communications and Informatics ced/2021-01-01`
**Exit**   | `exit`                                                                                        |
**Find**   | `find all [KEYWORDS]` <br> `find e [KEYWORDS]` <br> `find ph [KEYWORDS]`                      | `find all Jake` <br> `find e Jake` <br> `find ph Jake`
**Help**   | `help`                                                                                        |
**List**   | `list all` <br> `list e` <br> `list ph`                                                       |
**Potential Hire**| `potential n/NAME p/PHONE_NUMBER a/ADDRESS e/EMAIL d/DEPARTMENT r/ROLE​`                | `potential n/Jun Kang p/81234567 a/21 Lower Kent Ridge Rd e/pohjunkang@gmail.com d/Department of communications and informatics r/Head of communications and Informatics`
**Promote** | `promote INDEX CONTRACT_END_DATE`                                                            | `promote 2 2025-12-20`


</div>

<br>

<h1 class="headers" id="features">Features</h1>

<div class="content">

### **Saving the data**

<div class="sub-content">

StaffSync data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

</div>
<br>

### **Editing the data file**
<div class="sub-content">
  
  StaffSync data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.
  
<div class="box" type="warning" seamless>
  
**Caution:**
If your changes to the data file makes its format invalid, StaffSync will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the StaffSync to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div class="box">

</div>
<br>
<br>

### **Archiving data files `[coming in v2.0]`**
<div class="sub-content">
  
_Details coming soon ..._
  
</div>
</div>

<br>

<h1 class="headers" id="faq">FAQ</h1>

<div class="content content-qna">

<details>
  <summary><strong>Q: How do I transfer my data to another Computer?</strong>
    <span class="material-symbols-outlined chevrons">
      chevron_right
    </span>
  </summary>

  <div class="qna-content">

  <strong>A: </strong>Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous StaffSync home folder.

  </div>
</details>

</div>

<br>

<h1 class="headers" id="known-issues">Known Issues</h1>

<div class="content">

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
<br>
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

</div>
