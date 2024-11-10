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
    --hover-bg-color: rgb(243, 243, 243);
    --white-bg-color: rgb(255, 255, 255);
    --code-bg-color: rgba(202, 206, 255, 0.6);
    --code-bg-no-opacity-color: rgba(202, 206, 255, 1);
    --box-bg-color: rgba(255, 130, 21, 0.6);
    --box-border-color: rgba(255, 130, 21, 0.75);
    --mistake-bg-color: rgba(255, 0, 0, 0.2);
    --mistake-border-color: rgba(255, 0, 0, 0.4);
    --warn-bg-color: rgba(255, 205, 0, 0.2);
    --warn-border-color: rgba(255, 205, 0, 0.4);
    --info-bg-color: rgba(100, 150, 255, 0.2);
    --info-border-color: rgba(100, 150, 255, 0.4);
    --border-color: rgb(220, 220, 220);
    --black-color: rgb(0, 0, 0);
  }

  .ug-images img {
    pointer-events: none;
    width: 500px;
    margin: 24px auto 24px auto;
    border-radius: 8px;
    box-shadow: 0 0 24px rgba(0, 0, 0, 0.4);
  }

  .ug-images {
    margin: auto;
    display: flex;
    justify-content: center;
  }

  .ug {
    font-family: 'DM Sans', sans-serif;
    transition: 0.2s ease;
  }

  .box {
    background-color: var(--box-bg-color) !important;
    padding: 8px 16px;
    margin: 8px 0;
    border-radius: 5px;
    border: 2px solid var(--box-border-color);
  }

  .box-large-padding {
    padding: 16px 24px;
  }

  .box-info {
    background-color: var(--info-bg-color) !important;
    padding: 16px 16px;
    border: none;
  }

  .box-warn {
    background-color: var(--warn-bg-color) !important;
    padding: 16px 24px;
    border: 2px solid var(--warn-border-color);
  }

  .box-mistake {
    background-color: var(--mistake-bg-color) !important;
    padding: 16px 24px;
    border: 2px solid var(--mistake-border-color);
  }

  .box > p {
    margin: 0;
  }

  .headers {
    font-weight: 900;
    margin-top: 64px;
  }

  .headers-first {
    margin-top: 72px;
  }

  .content {
    background-color: var(--primary-bg-color);
    border-radius: 10px;
    padding: 48px;
    margin: 16px;
  }

  .sub-content {
    padding: 16px;
  }

  .sub-content-no-vertical-padding {
    padding: 0px 16px !important;
  }

  .content-special {
    background-color: var(--special-bg-color);
    padding: 32px;
  }

  .content-droppable {
    padding: 24px 32px 16px 32px;
    margin: 0px 16px;
    font-size: 1.2em;
    border-top: 1px solid var(--border-color);
    background-color: var(--white-bg-color);
    border-radius: 0px;
    transition: 0.2s ease;
  }

  .content-droppable:hover {
    background-color: var(--hover-bg-color);
  }

  .content-qna {
    font-size: 0.9em;
  }

  .content-command {
    background-color: var(--white-bg-color);
    padding: 8px;
  }

  .command-titles, summary {
    cursor: pointer;
    margin-bottom: 8px;
    display: flex;
    font-size: 1.2em;
  }

  details[open] > summary > .chevrons {
    transform: rotate(270deg);
  }

  code {
    color: var(--black-color) !important;
    background-color: var(--code-bg-color) !important;
  }

  .chevrons {
    transform: rotate(90deg) translateX(0px);
    text-decoration: none;
    background-color: transparent !important;
    margin: auto;
    margin-right: 0;
    transition: 0.2s ease;
  }

  .command-content {
    padding: 24px 16px 8px 16px;
    font-size: 0.8em;
  }

  .code-full-width {
    width: calc(100% - 48px);
    background-color: var(--code-bg-color) !important;
    padding: 12px 24px;
    margin: 16px 0;
    border-radius: 5px;
  }

  .code-full-width > span {
    color: var(--black-color) !important;
    font-family: 'DM Sans', sans-serif;
  }

  .code-no-opacity {
    background-color: var(--code-bg-no-opacity-color) !important;
    box-shadow: 0 0 8px rgba(0, 0, 0, 0.2);
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
    position: relative;
  }

  .toc-btns > span {
    position: relative;
    z-index: 5;
  }

  .toc-btns::before {
    content: "";
    top: 0;
    left: 0;
    position: absolute;
    width: 10px;
    height: 100%;
    background: linear-gradient(90deg, var(--code-bg-color) 0%, var(--primary-bg-color) 50%);
    transition: width 0.3s ease;
    z-index: 0;
    margin-left: 0;
  }

  .toc-btns-first, .content-droppable-first, .toc-btns-first::before {
    border: none;
    border-top-left-radius: 10px;
    border-top-right-radius: 10px;
  }

  .toc-btns-last, .content-droppable-last, .toc-btns-last::before {
    border-bottom-left-radius: 10px;
    border-bottom-right-radius: 10px;
  }

  .toc-btns:hover::before {
    width: calc(100%);
    background: linear-gradient(90deg, var(--code-bg-color) 0%, var(--primary-bg-color) 50%);
  }

  .last-component {
    margin-bottom: 96px;
  }
</style>

<div class="ug">

<h1 class="headers headers-first">StaffSync User Guide</h1>

<p class="content content-special">
  Welcome to StaffSync! Are you a Human Resources (HR) Manager that has a lot of potential hires and employees to manage? StaffSync allows you to manage your potential hires and employees on your desktop, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). If you type fast, you can complete your contact management tasks faster with StaffSync than with GUI apps.
</p>

<br>

<h1 class="toc headers">Table of Contents</h1>

<div class="content toc-content">
  <a href="#installation-and-quick-start">
    <button class="toc-btns toc-btns-first"><span>1. Installation and Quick Start</span></button>
  </a>
  <a href="#commands">
    <button class="toc-btns"><span>2. Commands</span></button>
  </a>
  <a href="#command-summary">
    <button class="toc-btns"><span>3. Commands Summary</span></button>
  </a>
  <a href="#storing-data">
    <button class="toc-btns"><span>4. Storing Data</span></button>
  </a>
  <a href="#glossary">
    <button class="toc-btns"><span>5. Glossary</span></button>
  </a>
  <a href="#faq">
    <button class="toc-btns"><span>6. FAQ</span></button>
  </a>
  <a href="#known-issues">
    <button class="toc-btns toc-btns-last"><span>7. Known Issues</span></button>
  </a>
</div>

<br>

<h1 class="headers" id="installation-and-quick-start">Installation and Quick Start</h1>


<div class="content">

<strong>1. </strong>**Download** and **install** [Java `17`](https://www.oracle.com/java/technologies/downloads/#java17) if you don't have it installed. We don't support other versions.

<div class="sub-content">

_You may check your installed version of Java by entering the following in your command terminal_

<div class="code-full-width"><span>java -version</span></div>

</div>

<br>

<br>

<strong>2. </strong>**Download** the latest version of `StaffSync.jar` from [our Github page](https://github.com/AY2425S1-CS2103T-T10-2/tp/releases).

<br>

<br>

<strong>3. </strong>**Copy** `StaffSync.jar` into the folder you want to use as the _home folder_ for StaffSync. StaffSync will store all save data here.

<br>

<br>

<strong>4. </strong>Open a command terminal, and **change your directory** to the StaffSync's _home folder_ by using the `cd` command.

<div class="sub-content">

For example, if the _home folder_ is located at `Desktop/StaffSync`, enter the command

<div class="code-full-width"><span>cd "Desktop/StaffSync"</span></div>

</div>

<br>

<br>

<strong>5. </strong>Next, let's **run** StaffSync.

<div class="sub-content">

In your command terminal, enter the following command

<div class="code-full-width"><span>java -jar StaffSync.jar</span></div>

<br>

A GUI similar to the below should appear in a few seconds with some sample data.<br>

</div>

<div class="ug-images">

![GUI](images/v1.6images/GUI.png)
</div>

<br>

<br>

<strong>6. </strong>To **run any command**, type it in the command box and press Enter to execute it.

<div class="sub-content">

<div class="sub-content sub-content-no-vertical-padding">

For example, entering the following command will open the help window.

<div class="code-full-width"><span>help</span></div><br>

</div>

Some example commands you can try:

<div class="sub-content">

List all potential hires.

<div class="code-full-width"><span>list ph</span></div><br>

Add an employee named `John Doe` to StaffSync.

<div class="code-full-width"><span>employee n/John Doe p/81234567 e/pohjunkang@gmail.com a/21 Lower Kent Ridge Rd d/Department of communications and informatics r/Head of communications and informatics ced/2021-01-01</span></div><br>

Delete the 1st person shown if they are a potential hire

<div class="code-full-width"><span>delete ph 1</span></div><br>

Exit the app.

<div class="code-full-width"><span>exit</span></div><br>

</div>

</div>

<br>

<strong>7. </strong>You can refer to the [Commands](#commands) below for **details of each command**.

<div class="sub-content">

If you would like to remove all sample data, you can run the command

<div class="code-full-width"><span>clear</span></div><br>

</div>

<br>

<strong>8. </strong>Now, you are all set to use StaffSync. We wish you all the best on your journey with StaffSync!

</div>

<br>

<h1 class="headers" id="commands">Commands</h1>


<div class="content content-special">

**Notes about the command **format**:**

<br>

* Words in `UPPER_CASE` are the **compulsory** parameters to be **supplied by the user**.
  e.g. in `employee n/NAME`, `NAME` is a parameter which can be used as `employee n/John Doe`.

<br>

* Items in round brackets are **compulsory**.
  e.g. `list (e/ph/all)` must be used as `list e` or `list ph` or `list all`.

<br>

* Items in square brackets are **optional**.
  e.g. `edit INDEX [n/NAME] [e/EMAIL]` can be used as `edit 1 n/John Doe e/johndoe@gmail.com` or `edit 1 n/John Doe`.

<br>

* Items in curly brackets are **requirements for the format**, but are **not entered** into the command box.
  e.g. `demote INDEX {must be a positive integer}` means that index must be a positive integer.

<br>

* Parameters can be in **any order**.
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

<br>

* Parameters and commands are **case-sensitive**.
  e.g. the command `Help` and `edit 1 N/John Doe` will return an error.

<br>

* Extra parameters for commands that do not take in parameters (such as `help`, `exit` and `clear`) will be **ignored**.
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

<br>

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as there may be formatting issues.

</div>


<div class="content content-droppable content-droppable-first">

<details open>
  <summary>
    <strong>Clearing all entries<br><code>clear</code></strong>
    <span class="material-symbols-outlined chevrons">
      chevron_right
    </span>
  </summary>

  <div class="command-content">
  You can clear all entries from StaffSync.

  <br>
  <br>

  <div class="box box-info">

  **Format**: `clear`
  </div>

  <br>

  <div class="box box-warn" type="warning" seamless>

  **Warning:** There is **NO confirmation prompt** when using this command and is irreversible.
  </div>
  </div>
</details>

</div>
<div class="content content-droppable">

<details open>
  <summary>
    <strong>Deleting a person<br><code>delete</code></strong>
    <span class="material-symbols-outlined chevrons">
      chevron_right
    </span>
  </summary>

  <div class="command-content">
  You can delete the specified person from StaffSync.

  <br>
  <br>


  <div class="box box-info">

  **Format**: `delete PARAMETER INDEX`
  </div>
  <br>

  You can delete the potential hire/employee at the specified shown `INDEX`.

<br>

**Parameters:**

  `PARAMETER`:
  * `e` for employees
  * `ph` for potential hires.

  `INDEX`: The index number shown in your displayed person list.
  * `INDEX` **must be a positive integer** 1, 2, 3, …​
  * `INDEX` **must be within the size of the list** shown.

<br>

**Examples:**

  * `list ph` followed by `delete ph 2` deletes the 2nd person in the display shown by `list`, if he/she is a potential hire.
  * `find e n/Betsy` followed by `delete e 1` deletes the 1st person in the display shown by `find`, if he/she is an employee.
    
<br>

Example: `delete e 1`
  </div>
  <div class="command-content">
  <div class="ug-images">

![result for 'delete e 1'](images/v1.6images/Delete.png)
  </div>

  <div class="box box-mistake" type="warning" seamless>

  **Common Mistakes:**
  * `delete E 1` - invalid format, you should type `e` instead of `E` as it is case-sensitive
  * `delete 1` - invalid format, you forgot to specify the person type `e` or `ph`
  </div>

  </div>
  </details>
</div>

<div class="content content-droppable">

<details open>
  <summary>
    <strong>Demoting a person<br><code>demote</code></strong>
    <span class="material-symbols-outlined chevrons">
      chevron_right
    </span>
  </summary>

  <div class="command-content">
  You can demote the specified employee from StaffSync into a potential hire.

  <br>
  <br>


  <div class="box box-info">

  **Format**: `demote INDEX`
  </div>

<br>

**Parameters:**

  `INDEX`: The index number shown in your displayed person list.
  * `INDEX` **must be a positive integer** 1, 2, 3, …​
  * `INDEX` **must be within the size of the list** shown.
  * The person at the `INDEX` must be an employee.

<br>

**Examples:**

  * `list e` followed by `demote 2` demotes the 2nd person in your employee list.
  * `find e n/Betsy` followed by `demote 1` demotes the 1st employee in the results of the `find` command.

<br>

Example: `demote 1`
  </div>
  <div class="command-content">
  <div class="ug-images">

![result for 'demote 1'](images/v1.6images/Demote.png)
  </div>

  <div class="box box-mistake" type="warning" seamless>

  **Common Mistakes:**
  * `list ph` followed by `demote 2` - You cannot demote a potential hire, you can use `list e` instead of `list ph` to get the list of employees
  * `demote 0` - invalid format, you should change 0 to a greater number corresponding to the index
  * `demote 3` but only have 2 entries - invalid index number, index out of list size, you might want to check the index number again
  </div>
  </div>

</details>

</div>
<div class="content content-droppable">

<details open>
  <summary>
    <strong>Editing a person<br><code>edit</code></strong>
    <span class="material-symbols-outlined chevrons">
      chevron_right
    </span>
  </summary>

  <div class="command-content">
  You can edit an existing person in StaffSync.

  <br>
  <br>


  <div class="box box-info">

  **Format**: `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [d/DEPARTMENT] [r/ROLE] [ced/CONTRACT_END_DATE]`
  </div>

  <br>

  You can edit the person at the specified `INDEX`.

<br>

**Parameters:**

  `INDEX`: The index number shown in your displayed person list.
  * `INDEX` **must be a positive integer** 1, 2, 3, …​
  * `INDEX` **must be within the size of the list** shown.

**Optional Parameters:**
  * You should include at least one of the optional fields.
  * Existing values will be updated to the input values.
  * You can refer to the `employee` command for each parameter's format.

  <div class="box" type="tip" seamless>

  **Tip:** You cannot edit the contract end date of a potential hire.
  </div>

<br>

**Examples:**

  *  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
  *  `edit 2 n/Betsy Crower` Edits the name of the 2nd person to be `Betsy Crower`.
<br>

  Example: `edit 1 p/96734857`
  </div>
  <div class="command-content">
  <div class="ug-images">

![result for 'edit 1 p/96734857'](images/v1.6images/Edit.png)
  </div>
</div>
</details>

</div>
<div class="content content-droppable">

<details open>
  <summary>
    <strong>Adding an employee<br><code>employee</code></strong>
    <span class="material-symbols-outlined chevrons">
      chevron_right
    </span>
  </summary>


  <div class="command-content">
  You can add an employee to StaffSync.

  <br>
  <br>


  <div class="box box-info">

  **Format**: `employee n/NAME p/PHONE_NUMBER a/ADDRESS e/EMAIL d/DEPARTMENT r/ROLE ced/CONTRACT_END_DATE`
  </div>

<br>

  **Parameters:**

  `NAME`: Contains only alphanumeric characters and spaces, and it should not be blank.

  `PHONE_NUMBER`: Contains numbers that are at least 3 digits long.

  `ADDRESS`: Takes any values, and it should not be blank.

  `EMAIL`: In the format local-part@domain.

  `DEPARTMENT`: Takes any values, and it should not be blank.

  `ROLE`: Takes any values, and it should not be blank.

  `CONTRACT_END_DATE`: In the format of yyyy-MM-dd.

  <div class="box" type="tip" seamless>

  **Tip:** All fields are mandatory.
  </div>

<br>

**Examples:**

  * `employee n/Jun Kang p/96732493 a/21 Lower Kent Ridge Rd e/pohjunkang@gmail.com d/Department of communications and informatics r/Head of communications and informatics ced/2025-04-04`
    <br>

  </div>
  <div class="command-content">
  <div class="ug-images">

![result for employee command](images/v1.6images/Employee.png)
  </div>
</div>
</details>

</div>
<div class="content content-droppable">

<details open>
  <summary>
    <strong>Exiting the program<br><code>exit</code></strong>
    <span class="material-symbols-outlined chevrons">
      chevron_right
    </span>
  </summary>

  <div class="command-content">
  You can exit the program.

  <br>
  <br>


  <div class="box box-info">

  **Format**: `exit`
  </div>

  <br>

  </div>
</details>

</div>
<div class="content content-droppable">

<details open>
  <summary>
    <strong>Locating persons by name<br><code>find</code></strong>
    <span class="material-symbols-outlined chevrons">
      chevron_right
    </span>
  </summary>

  <div class="command-content">
  You can find all employees and/or potential hires whose names contain any of the specified keywords.

  <br>
  <br>

  <div class="box box-info">

  **Format**: `find PARAMETER [n/NAMES] [p/PHONE_NUMBERS] [e/EMAILS] [d/DEPARTMENTS] [r/ROLES]`
  </div>

<br>

**Parameters:**

  `PARAMETER`:
  * `all` for all persons.
  * `e` for employees.
  * `ph` for potential hires.


**Optional Parameters:**

  * You can refer to the `employee` or `potential` command for each parameter's format.
  * Only name, phone number, email, department and role can be searched.
  * Only full words will be matched. e.g. `find e n/Han` will not match `find e n/Hans`.
  * The search is case-insensitive. e.g. `find all n/hans` will match `find all n/Hans`.
  * You can search for multiple fields. e.g. `find all n/alice p/12345678 e/alice@example.com` returns persons
  with name `alice`, with phone number `123445678` and with email `alice@example.com`.
  * Persons matching at least one keyword in every field specified will be returned.
    e.g. `find e n/Hans Bo p/12345678 87654321` will return employees with name either `Hans` or `Bo`,
    and with phone number either `12345678` or `87654321`.
  * The order of the keywords does not matter. e.g. `find all e/alice@example.com bob@example.com` will match
  `find all e/bob@example.com alice@example.com`.
  * The order of the keywords prefixes does not matter. e.g. `find all n/john e/john@example.com` will match
  `find all e/john@example.com n/john`.


  <div class="box" type="tip" seamless>

  **Tip:** At least one keyword is required.
  </div>

<br>

**Examples:**

  * `find all n/John p/12345678` returns persons with `John` in their name, and with phone number `12345678`.
  * `find e p/12345678 e/john@example.com alice@example.com` returns employees with phone number `12345678` and with
  email either `john@example.com` or `alice@example.com`.
  * `find ph d/IT r/SWE Manager` returns potential hires with department `IT`, and role either `SWE` or `Manager`.

<br>

Example: `find ph n/David`
  </div>
  <div class="command-content">
  <div class="ug-images">

![result for 'find ph n/David'](images/v1.6images/Find.png)
  </div>

  <div class="box box-mistake" type="warning" seamless>

  **Common Mistakes:**
  * `find a n/John` - invalid format, you might want to use `e`, `ph` or `all` instead of `a`.
  * `find all a/John` - invalid keyword prefix, you might want to use `n/`, `p/`, `e/` , `d/` or `r/` instead of `a/`.
  * `find all n/John n/John` - duplicate keyword prefix, you can remove either one of the `n/John`.
  </div>

  </div>
</details>

</div>
<div class="content content-droppable">

<details open>
  <summary>
    <strong>Viewing help<br><code>help</code></strong>
    <span class="material-symbols-outlined chevrons">
      chevron_right
    </span>
  </summary>

  <div class="command-content">
  Displays a help window for you to see the list of commands, its purpose and the format.

  <br>
  <br>

  <div class="box box-info">

  **Format**: `help`
  </div>

  <div class="ug-images">

  ![HelpWindow](images/v1.6images/Help.png)
  </div>

  </div>
</details>

</div>
<div class="content content-droppable">

<details open>
  <summary>
    <strong>Listing all persons<br><code>list</code></strong>
    <span class="material-symbols-outlined chevrons">
      chevron_right
    </span>
  </summary>

  <div class="command-content">
  You can see a list of all persons in StaffSync.

  <br>
  <br>

  <div class="box box-info">

  **Format**: `list PARAMETER`
  </div>
  <br>

  You can list type of persons based on the parameter given.

<br>

**Parameters:**

  `PARAMETER`:
  * `all` for all persons.
  * `ph` for potential hires.
  * `e` for employees.

<br>

**Examples:**

  * `list all`
  * `list e`
  * `list ph`
<br>

Example: `list all`
  </div>
  <div class="command-content">
  <div class="ug-images">

![result for `list all`](images/v1.6images/List.png)
  </div>
</div>
</details>

</div>
<div class="content content-droppable">

<details open>
  <summary>
    <strong>Adding a potential hire<br><code>potential</code></strong>
    <span class="material-symbols-outlined chevrons">
      chevron_right
    </span>
  </summary>

  <div class="command-content">
  You can add a potential hire to StaffSync.

  <br>
  <br>


  <div class="box box-info">

  **Format**: `potential n/NAME p/PHONE_NUMBER a/ADDRESS e/EMAIL d/DEPARTMENT r/ROLE`
  </div>

  <br>

  **Parameters:**

  `NAME`: Contains only alphanumeric characters and spaces, and it should not be blank.

  `PHONE_NUMBER`: Contains numbers that are at least 3 digits long.

  `ADDRESS`: Takes any values, and it should not be blank.

  `EMAIL`: In the format local-part@domain.

  `DEPARTMENT`: Takes any values, and it should not be blank.

  `ROLE`: Takes any values, and it should not be blank.

  <div class="box" type="tip" seamless>

  **Tip:** All fields are mandatory.
  </div>

<br>

**Examples:**

  * `potential n/Heng Kai p/94628364 a/29 Prince George's Pk e/neohengkai@gmail.com d/Department of communications and informatics r/Head of communications and informatics`
    <br>

  </div>
  <div class="command-content">
  <div class="ug-images">

![result for potential command](images/v1.6images/Potential.png)
  </div>
</div>
</details>

</div>
<div class="content content-droppable content-droppable">

<details open>
  <summary>
    <strong>Promoting a person<br><code>promote</code></strong>
    <span class="material-symbols-outlined chevrons">
      chevron_right
    </span>
  </summary>

  <div class="command-content">
  You can promote the specified potential hire from StaffSync into an employee with a specific contract end date.

  <br>
  <br>


  <div class="box box-info">

  **Format**: `promote INDEX CONTRACT_END_DATE`
  </div>

<br>

**Parameters:**

  `INDEX`: The index number shown in your displayed person list.

  * `INDEX` **must be a positive integer** 1, 2, 3, …​
  * `INDEX` **must be within the size of the list** shown.
  * The person at the `INDEX` must be a potential hire

  `CONTRACT_END_DATE`: The contract end date of the employee in the format of yyyy-MM-dd.

<br>

**Examples:**

  * `list ph` followed by `promote 2 2025-12-20` promotes the 2nd person in the potential hire list with a contract end date of 20 Dec 2025.
  * `find ph n/Betsy` followed by `promote 1 2025-12-20` promotes the 1st potential hire in the results of the `find` command with a contract end date of 20 Dec 2025.

<br>

Example: `promote 1 2025-12-20`
  </div>
  <div class="command-content">
  <div class="ug-images">

![result for `promote 1 2025-12-20`](images/v1.6images/Promote.png)
  </div>

  <div class="box box-mistake" type="warning" seamless>

  **Common Mistakes:**
  * `promote 2 12-20-2025` - the contract end date is in the wrong date format, you can enter `2025-12-20` to represent 20 Dec 2025 instead of `12-20-2025`.
  * `promote 2 2025-20-12` - the day and the month of the contract end date is swapped, you can enter `2025-12-20` to represent 20 Dec 2025 instead of `2025-20-12`.
  * `list e` followed by `promote 2 2025-12-20` - cannot promote an employee, you can use `list ph` instead of `list e` to get the list of potential hires.
  * `promote 0 2025-12-20` - invalid format, you should change 0 to a greater number corresponding to the index.
  * `promote 3 2025-12-20` but only have 2 entries - invalid index number, index out of list size, you might want to check the index number again.
  </div>
  </div>
</details>

</div>
<div class="content content-droppable content-droppable-last">

<details open>
  <summary>
    <strong>Sorting the list<br><code>sort</code></strong>
    <span class="material-symbols-outlined chevrons">
      chevron_right
    </span>
  </summary>

  <div class="command-content">
  You can sort the list in ascending or descending order by the given parameter.

  <br>
  <br>


  <div class="box box-info">

  **Format**: `sort FIELD [ORDER]`
  </div>

  <br>

  You can sort the list by the given field in the given order.

<br>

**Parameters:**

  `FIELD`: The type of data you want to sort by.
  * `name` will sort the list based on the names of your contacts in alphabetical order.
  * `date` will sort the list based on the contract end dates of your employees.
  * `dept` will sort the list based on the department of your contacts in alphabetical order.
  * `role` will sort the list based on the roles of your contacts in alphabetical order.

<br>

**Optional Parameters:**

  `[ORDER]`: The order you wish to sort the list in. Defaults to ascending order if not specified.
  * `asc` will sort your list in ascending order.
  * `desc` will sort your list in descending order.

  <div class="box" type="tip" seamless>

  **Tips:**
  * Sort only sorts the contacts shown at the current point in time.
  * When sort date is used, potential hires show up at the bottom as they do not have a contract end date.
  * If the order is left out StaffSync will sort in ascending order by default.
  </div>

<br>

**Examples:**

  * `sort name` sorts your contacts by name in alphabetical order.
  * `sort date desc` sorts by contract end date with your potential hires at the bottom.
  * `sort role asc` sorts your contacts by role in alphabetical order.

<br>

Example: `sort name`
  </div>
  <div class="command-content">
  <div class="ug-images">

![result for `sort name`](images/v1.6images/Sort.png)
  </div>
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
**Employee**| `employee n/NAME p/PHONE_NUMBER a/ADDRESS e/EMAIL d/DEPARTMENT r/ROLE ced/CONTRACT_END_DATE​` | `employee n/Jun Kang p/81234567 a/21 Lower Kent Ridge Rd e/pohjunkang@gmail.com d/Department of communications and informatics r/Head of communications and informatics ced/2021-01-01`
**Exit**   | `exit`                                                                                        |
**Find**   | `find (e/ph/all) [n/NAMES] [p/PHONE_NUMBERS] [e/EMAILS] [d/DEPARTMENTS] [r/ROLES]`            | `find e n/Jake e/jake@example.com` <br> `find ph n/Don p/97651234` <br> `find all n/James d/IT r/SWE`
**Help**   | `help`                                                                                        |
**List**   | `list all` <br> `list e` <br> `list ph`                                                       |
**Potential Hire**| `potential n/NAME p/PHONE_NUMBER a/ADDRESS e/EMAIL d/DEPARTMENT r/ROLE​`                | `potential n/Jun Kang p/81234567 a/21 Lower Kent Ridge Rd e/pohjunkang@gmail.com d/Department of communications and informatics r/Head of communications and informatics`
**Promote** | `promote INDEX CONTRACT_END_DATE`                                                            | `promote 2 2025-12-20`
**Sort**   | `sort (name/date/dept/role) [asc/desc]` | `sort name` <br> `sort date asc` <br> `sort dept desc`


</div>

<br>

<h1 class="headers" id="storing-data">Storing Data</h1>

<div class="content">

### **Saving the data**

<div class="sub-content">

StaffSync data are saved in the hard disk automatically after any command that changes the data. There is no need for you to save manually.

</div>

<br>

### **Editing the data file**
<div class="sub-content">

  StaffSync data are saved automatically as a JSON file `[JAR file location]/data/staffSync.json`. Advanced users are welcome to update data directly by editing that data file.

<div class="box box-mistake" type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, StaffSync will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.

<br>

Furthermore, certain edits can cause the StaffSync to behave in unexpected ways (e.g. if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

</div>

</div>

<br>

<h1 class="headers" id="glossary">Glossary</h1>

<div class="content">

### **Alphanumeric**

<div class="sub-content">

A mix of letters and numbers only. Includes both alphabets (a to z) and digits (0 to 9) with no spaces or special characters.

Example: StaffSync123

</div>

### **Command Line Interface**

<div class="sub-content">

A text-based interface that lets you type instructions directly to your computer to perform certain tasks. Instead of
clicking on menus or icons, you would type specific commands into the text-based interface and the computer or software
will run those commands for you. 

Example: To show a list of potential hire, simply type `list ph` and press enter. The list of potential hire will appear. 

</div>

### **Graphical User Interface**

<div class="sub-content">

The "face" of a program, designed to use visual elements like buttons, icons, windows and menus to make it easier for
people to interact with the program.

Example: You can see each entry of potential hire or employees in a box with their details nicely formatted and easy to
read.

</div>

### **Index**

<div class="sub-content">

The order or position of an item in a list. The value starts from 1 and it must be a positive
integer.

Example: If you have a list of names (1. Alice, 2. Betty, 3. Charlie) then, Alice is at index 1, Betty is at index 2
and Charlie is at index 3.

</div>

### **Integer**

<div class="sub-content">

A whole number with no fractions and decimals that can be positive, 0 or negative. The range of a valid integer in
programming is from -2147483648 to 2147483647, inclusive of both.

Example: -2147483648, -15, 0, 35, 2147483647

</div>

### **Parameter**

<div class="sub-content">

A piece of information you would give to a program to allow it to know exactly how you want it to work.

Example: In order to display a list of employee and not a list of potential hire, you would type in `list e` instead of
`list ph` as `e` is the parameter to allow the program to know that you want the list of employees.

</div>
</div>

<h1 class="headers" id="faq">FAQ</h1>

<div class="content content-qna">

<div>
  <summary>
    <strong>Q: How do I transfer my data to another Computer?</strong>
  </summary>

  <div class="qna-content">

  <strong>A: </strong>Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous StaffSync home folder.

  </div>
</div>

</div>

<br>

<h1 class="headers" id="known-issues">Known Issues</h1>

<div class="content">

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

<br>

2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

</div>

<div class="last-component"></div>

</div>
