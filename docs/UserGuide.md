---
layout: page
title: EduTuTu User Guide
---

**EduTUTU** is a desktop application designed to streamline contact management for private tutors, making it easier to 
organise and access student information. Optimised for use through a [Command Line Interface (CLI)](#cli-command-line-interface) while incorporating 
the convenience of a [Graphical User Interface (GUI)](#gui-graphical-user-interface), EduTUTU allows you to manage student details with speed and 
efficiency. Whether you’re handling student registrations, updating records, or searching for students, EduTUTU helps 
you complete these tasks more quickly than traditional applications, making it an ideal solution for private tutors.

***

## Table of Contents

1. [Installation](#1-installation)
2. [Command Instructions](#2-command-instructions)
   - [2.1 Viewing Help](#21-viewing-help-help)
   - [2.2 Adding a student](#22-adding-a-student-add)
   - [2.3 Deleting a student](#23-deleting-a-student-delete)
   - [2.4 Marking a Payment Date](#24-marking-a-payment-date-markpaid)
   - [2.5 Unmarking a Payment Date](#25-unmarking-a-payment-date-unmarkpaid)
   - [2.6 Editing a student](#26-editing-a-student-edit)
   - [2.7 Listing All students](#27-listing-all-students-list)
   - [2.8 Finding a student](#28-finding-a-student-find)
   - [2.9 Clearing All Entries](#29-clearing-all-entries-clear)
   - [2.10 Undo/Redo Commands](#210-undoredo-commands-undo-and-redo)
   - [2.11 Displaying Pie Chart of Class Distribution](#211-displaying-pie-chart-of-class-distribution-pie)
   - [2.12 Displaying Bar Chart](#212-displaying-bar-chart-bar)
   - [2.13 Viewing Command History](#213-viewing-command-history-arrow-keys)
   - [2.14 View Student Details](#214-viewing-student-details-info)
   - [2.15 Editing the Data File](#215-editing-the-data-file)
   - [2.16 Saving Data](#216-saving-data)
   - [2.17 Exiting the Program](#217-exiting-the-program-exit)
3. [FAQ](#3-faq)
4. [Glossary](#4-glossary)

***
<div style="page-break-after: always;"></div>

## 1. Installation

1. Ensure you have Java `17` or above installed in your Computer. It can be downloaded [here](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-T15-2/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your EduTuTu.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar EduTuTu.jar` command to run the application.<br>

   A [GUI](#gui-graphical-user-interface) similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/real_ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 f/250 c/1 ` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : [Exits](#exit) the app.

1. Refer to the features below for details of each command.

[Back to Table of Contents](#table-of-contents)

***

## 2. Command Instructions
### Command Format Guidelines

- **Optional Parameters**: Parameters in square brackets `[ ]` are **optional**.
  <br> e.g. `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.


- **Repeatable Parameters**: Parameters followed by `…` can be used **zero or more times**.
  <br> e.g. `n/NAME [t/TAG]…` can be used as `n/John Doe t/friend t/family` or as `n/John Doe`.


- **Parameters in `UPPER_CASE`**: These represent user-supplied values.
  <br> e.g. `MONTH_PAID` should be replaced with a value like `2024-10`.


- **INDEX**: Refers to the index number of a student shown in the displayed student list.
  <br> e.g. `1` for the first student in the list.


- **KEYWORD** and **MORE_KEYWORDS**: These are search terms used to find students.
  <br> e.g. `John Doe`, `John Doe friend`.


- **Parameter Order**: Parameters can be in **any order**.
  <br> e.g. If the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.


- **Prefixes**: They are consistent across every command that uses prefixes.
  <br> `n/` for **name**
  <br> `p/` for **phone number**
  <br> `e/` for **email**
  <br> `a/` for **address**
  <br> `f/` for **fees**
  <br> `c/` for **class ID**
  <br> `m/` for **month paid**
  <br> `t/` for **tags**


- **Commands Without Parameters**: Provided parameters will be **ignored**.
  <br> e.g. `help 123` will be interpreted as `help`.


- **Copying Commands from PDF**
  <br> If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines. Space characters surrounding line breaks **may be omitted when copied over** to the application.


### Field Constraints

- **Name**: Accepts **alphanumeric** characters and **spaces**.
  <br> e.g. `John Doe`


- **Phone Number**: Accepts **digits**. A **hyphen/space** between numbers, and a **plus character** as the first character can be added. **At most one hyphen/space/plus can be entered.**
  <br> e.g. `91088231`, `9108 8231`, `9108-8231`, `+65 91088231` are **valid**.
  <br> e.g. `9123-4567 8910`, `123 456 789`, `+96+91234567` are **invalid**.


- **Email**: Accepts **alphanumeric** characters, and the following **special characters**: `@+_.-`
  <br> e.g. `joe@duck.com`, `johnny_123@gmail.com`, `joe@localhost`
  <br> For more detailed information, refer to the Internet Protocol Standards ([RGC 5322 Section 3.4.1](https://datatracker.ietf.org/doc/html/rfc5322#section-3.4.1)).


- **Address**: No specific restrictions.
  <br> e.g. `Kent Ridge 123123`
  <br> Note: Rare characters such as **emojis** and **Chinese characters** are not guaranteed to be displayed properly.


- **Fees**: Accepts a **maximum of 9 digits** due to technical constraints. **Must be a whole number**.
  <br> e.g. `0`, `500`, `123456789` are **valid**.
  <br> e.g. `10.50`, `12345678910` are **invalid**.


- **Class ID**: Accepts alphanumeric characters only. **Spaces are not accepted.**
  <br> e.g. `12345`


- **Month Paid**: Uses the format `YYYY-MM`. It must be within the range `1900-01` to `2099-12` inclusive.
  <br> e.g. `2024-01`, `2024-12` are **valid**.
  <br> e.g. `2024-1`, `2024-13` are **invalid**.


- **Tag**: Accepts alphanumeric characters only. **Spaces are not accepted.**
  <br> e.g. `friend`, `family`, `class123`

[Back to Table of Contents](#table-of-contents)

***

### 2.1 Viewing Help: `help`

**[Command Format](#command-format):** `help`

Opens a window explaining how to access the help page.

![help message](images/helpMessage.png)

**Tips:**
- Press Esc to close the help window.

[Back to Table of Contents](#table-of-contents)

***

### 2.2 Adding a student: `add`

Adds a student.

**[Command Format](#command-format):** `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS f/FEES c/CLASS_ID [t/TAG]…`

**[Command Word Alias](#command-word-alias):** `a`

> **Remark:** A student can have any number of tags (including 0). Fields other than NAME can be empty provided the prefix is specified.

**Examples of Add:**
- `add n/John p/ e/ a/ f/ c/` adds a student with the name `John`, and all other fields blank. 


**Example Usage:** `add n/Ryan p/82154565 e/Ryan@gmail.com a/3 Padang Chancery f/550 c/1`

<div style="display: flex; flex-direction: column; align-items: center; gap: 20px;">

  <!-- Text Section -->
  <div style="display: flex; justify-content: space-around; width: 100%;">
    <div style="width: 45%;">
      <p><b>Input:</b> User enters the `add` command.</p>
    </div>
    <div style="width: 45%;">
      <p><b>Output:</b> The UI updates to show the added student.</p>
    </div>
  </div>

  <!-- Image Section -->
  <div style="display: flex; justify-content: space-around; width: 100%;">
    <div style="width: 45%;">
      <img src="images/add_input.png" alt="Add Command Input" style="width: 100%;" />
    </div>
    <div style="width: 45%;">
      <img src="images/add_output.png" alt="Add Command Output" style="width: 100%;" />
    </div>
  </div>

</div>

**Tips:**
- `MONTH_PAID` cannot be specified for `add`.
- Duplicate tags will be added as a singular tag.

[Back to Table of Contents](#table-of-contents)

***

### 2.3 Deleting a student: `delete`

Deletes the specified student from the address book.

**[Command Format](#command-format):** `delete INDEX`

**[Command Word Alias](#command-word-alias):** `d`

**Example Usage:** `delete 4`

<div style="display: flex; flex-direction: column; align-items: center; gap: 20px;">

  <!-- Text Section -->
  <div style="display: flex; justify-content: space-around; width: 100%;">
    <div style="width: 45%;">
      <p><b>Input:</b> User enters the `delete 4` command to remove the 3rd student in the displayed list.</p>
    </div>
    <div style="width: 45%;">
      <p><b>Output:</b> The UI updates to reflect the deletion of the student.</p>
    </div>
  </div>

  <!-- Image Section -->
  <div style="display: flex; justify-content: space-around; width: 100%;">
    <div style="width: 45%;">
      <img src="images/delete_input.png" alt="Delete Command Input" style="width: 100%;" />
    </div>
    <div style="width: 45%;">
      <img src="images/delete_output.png" alt="Delete Command Output" style="width: 100%;" />
    </div>
  </div>

</div>

[Back to Table of Contents](#table-of-contents)

***

### 2.4 Marking a Payment Date: `markpaid`

Adds the month paid for specified months of a student, or all students.

**[Command Format](#command-format):** `markpaid MARK_TARGET m/MONTH_PAID…`
<br>`MARK_TARGET`: `INDEX`, or the word `all`

**[Command Word Alias](#command-word-alias):** `mp`

**Examples of Markpaid:**
- `markpaid 1 m/2024-01` adds the month paid `2024-01` to that student, if it is absent.
- `markpaid all m/2024-01` adds the month paid `2024-01` to all students currently displayed.


**Example Usage:** `markpaid 1 m/2024-10`

<div style="display: flex; flex-direction: column; align-items: center; gap: 20px;">

  <!-- Text Section -->
  <div style="display: flex; justify-content: space-around; width: 100%;">
    <div style="width: 45%;">
      <p><b>Input:</b> User enters the `markpaid 1 m/2024-10` command to mark the first student as paid for October 2024.</p>
    </div>
    <div style="width: 45%;">
      <p><b>Output:</b> The UI updates to show the payment status of the student.</p>
    </div>
  </div>

  <!-- Image Section -->
  <div style="display: flex; justify-content: space-around; width: 100%;">
    <div style="width: 45%;">
      <img src="images/markpaid_input.png" alt="MarkPaid Command Input" style="width: 100%;" />
    </div>
    <div style="width: 45%;">
      <img src="images/markpaid_output.png" alt="MarkPaid Command Output" style="width: 100%;" />
    </div>
  </div>

</div>

**Tips:**
- `markpaid all` can be used with `find` to target all filtered students.
- `markpaid` will not modify a student's months paid, if the specified month paid to be added is already present.


[Back to Table of Contents](#table-of-contents)

***


### 2.5 Unmarking a Payment Date: `unmarkpaid`

Removes the month paid for specified months of a student, or all students.

**[Command Format](#command-format):** `unmarkpaid MARK_TARGET m/MONTH_PAID…`
<br>`MARK_TARGET`: `INDEX`, or the word `all`

**[Command Word Alias](#command-word-alias):** `ump`

**Examples of Unmarkpaid:**
- `unmarkpaid 1 m/2024-01` removes the month paid `2024-01` from that student, if it is present.
- `unmarkpaid all m/2024-01` removes the month paid `2024-01` from all students currently displayed.

**Example Usage:** `unmarkpaid 1 m/2024-10`

<div style="display: flex; flex-direction: column; align-items: center; gap: 20px;">

  <!-- Text Section -->
  <div style="display: flex; justify-content: space-around; width: 100%;">
    <div style="width: 45%;">
      <p><b>Input:</b> User enters the `unmarkpaid 1 m/2024-10` command to remove the payment status of the first student for October 2024.</p>
    </div>
    <div style="width: 45%;">
      <p><b>Output:</b> The UI updates to remove the payment status of the student for the specified month.</p>
    </div>
  </div>

  <!-- Image Section -->
  <div style="display: flex; justify-content: space-around; width: 100%;">
    <div style="width: 45%;">
      <img src="images/unmarkpaidinput.png" alt="UnmarkPaid Command Input" style="width: 100%;" />
    </div>
    <div style="width: 45%;">
      <img src="images/unmarkpaidoutput.png" alt="UnmarkPaid Command Output" style="width: 100%;" />
    </div>
  </div>

</div>

**Tips:**
- `unmarkpaid all` can be used with `find` to target all filtered students.
- `unmarkpaid` will not modify a student's months paid, if the specified month paid to be removed is not present.

[Back to Table of Contents](#table-of-contents)

***

### 2.6 Editing a student: `edit`

Edits an existing student in the address book.

**[Command Format](#command-format):** `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [f/FEES] [c/CLASS_ID] [m/MONTH_PAID]… [t/TAG]…`

**[Command Word Alias](#command-word-alias):** `e`

**Examples of Edits:**
- `edit 1 n/John Doe p/91234567` will edit that student to have the name `John Doe` and phone number `91234567`.

**Example Usage:** `edit 1 p/91088511 e/wongwaihin7@gmail.com`


<div style="display: flex; flex-direction: column; align-items: center; gap: 20px;">

  <!-- Text Section -->
  <div style="display: flex; justify-content: space-around; width: 100%;">
    <div style="width: 45%;">
      <p><b>Input:</b> User enters the `edit` command to change the phone number and email address of the 1st student.</p>
    </div>
    <div style="width: 45%;">
      <p><b>Output:</b> The UI updates to show the edited details.</p>
    </div>
  </div>

  <!-- Image Section -->
  <div style="display: flex; justify-content: space-around; width: 100%;">
    <div style="width: 45%;">
      <img src="images/edit_input.png" alt="Edit Command Input" style="width: 100%;" />
    </div>
    <div style="width: 45%;">
      <img src="images/edit_output.png" alt="Edit Command Output" style="width: 100%;" />
    </div>
  </div>

</div>

**Tips**
- When editing a student's tags/months paid, all old tags/months paid will be overridden by the provided new tags.
<br> e.g. `edit 1 t/slacker` will edit that student to only have the tag `slacker`.
<br> e.g. `edit 1 m/2024-10` will edit that student to only have `2024-10` as their month paid.
<br> e.g. `edit 1 t/` and `edit 1 m/` will remove all tags and months paid from that student respectively.

[Back to Table of Contents](#table-of-contents)

***

### 2.7 Listing All students: `list`

The `list` command displays a all students currently stored in [EduTuTu](#edututu).

**[Command Format](#command-format):** `list`

**[Command Word Alias](#command-word-alias):** `l`

After entering the `list` command, all students stored will be displayed in the UI.

**Example Usage:**

<div style="display: flex; align-items: center; justify-content: space-around;">

  <!-- Input Section -->
  <div style="width: 40%;">
    <p><b>Input:</b> User enters the `list` command.</p>
    <img src="images/list_input.png" alt="Command Input Example" style="width: 100%;" />
  </div>

  <!-- Output Section -->
  <div style="width: 40%;">
    <p><b>Output:</b> The UI updates to show all students.</p>
    <img src="images/list_output.png" alt="Command Output Example" style="width: 100%;" />
  </div>

</div>

[Back to Table of Contents](#table-of-contents)

***

### 2.8 Finding a Student: `find`

The `find` command allows you find student by a field or any combination of fields.


**[Command Format](#command-format):** `find [n/NAME_PATTERN] [p/PHONE_PATTERN] [e/EMAIL_PATTERN] [f/EXACT_FEES] [c/CLASSID_PATTERN] [m/MONTH_PAID_PATTERN] [!m/MONTH_NOT_PAID_PATTERN]`

**[Command Word Alias](#command-word-alias):** `f`

**Examples of Searches**
- `find n/alice` returns students whose names contain `alice`. 
- `find !m/2024` returns students whose months paid does not contain any instance of `2024`.
- `find f/100` returns students whose fees is exactly `100`. (i.e. `1000` is not matched.)
- `find n/john joe` returns students whose names contain `john` or `joe`.
- `find e/asdf p/123` returns students whose email contains `asdf` and phone number contains `123`.

**Example Usage:** `find n/Kim`

<div style="display: flex; align-items: flex-start; justify-content: space-around;">

  <!-- Input Section -->
  <div style="width: 45%; vertical-align: top;">
    <p><b>Input:</b> User enters the `find` command to search for students whose names contain the keyword `Kim`.</p>
    <img src="images/find_input.png" alt="Find input example" style="width: 100%;" />
  </div>

  <!-- Output Section -->

  <div style="width: 45%; vertical-align: top; margin-top: 20px;">
    <p><b>Output:</b> The UI updates to show persons matching the search keyword.</p>
    <img src="images/find_output.png" alt="Find output example" style="width: 100%;" />
  </div>

</div>


**Tips:**
- Searches are **case-insensitive**.
  <br> e.g. `serangoon` will match `Serangoon`.
- **Exact matching** is implemented for **Fees**.
  <br> e.g. `find f/350` will only match students with an exact fee of `350`.
- For all other fields, the keyword can be a **single word** or **multiple words separated by a space**.
  - Single word: [Substring matching](#substring-matching).
  <br> e.g. `find p/832` will match students with phone numbers that contain `832`.
  - Multiple words **separated by a space**: [Substring matching](#substring-matching) with each word.
  <br> Each word will be treated as its own keyword, and students that match **any of these keywords** will be included in the results.
  <br> e.g. `find n/john alice` will match students with a name that contains `john` or `alice`.


[Back to Table of Contents](#table-of-contents)

***

### 2.9 Clearing All Entries: `clear`

[Clears](#clear) all entries from the address book.

**[Command Format](#command-format):** `clear`

Upon entering the command, all entries will be cleared from [EduTuTu](#edututu).

**Example Usage:** `clear`

<div style="display: flex; align-items: flex-start; justify-content: space-around;">

  <!-- Input Section -->
  <div style="width: 45%; vertical-align: top;">
    <p><b>Input:</b> User enters the `clear` command to remove all entries.</p>
    <img src="images/clear_input.png" alt="Clear input example" style="width: 100%;" />
  </div>

  <!-- Output Section -->
  <div style="width: 45%; vertical-align: top;">
    <p><b>Output:</b> The UI updates to show that all entries have been cleared.</p>
    <img src="images/clear_output.png" alt="Clear output example" style="width: 100%;" />
  </div>

</div>

**Tips:**
- If `clear` is executed unintentionally, you may use the `undo` command to reverse the action.

[Back to Table of Contents](#table-of-contents)

***

### 2.10 Undo/Redo Commands: `undo` and `redo`

**[Command Format](#command-format):** `undo` and `redo`

The `undo` and `redo` commands allow you to undo/redo the most recent changes made to student data.

**Format:**
- `undo` – Undoes the latest change to the address book.
- `redo` – Reverses the latest `undo` command.

**Example Usage:**
**Input:** User enters the `undo` command to reverse the last change.
![Ui](images/undo_input.png)
**Output:** The UI updates to reflect the reversal of the most recent change.*
![Ui](images/undo_output.png)
**Input:** User then enters the `redo` command to reapply the change.
The UI updates to reflect the reapplication of the previously undone change*
![Ui](images/redo.png)

**Tips:**
- The `redo` command is only available after an `undo`, allowing you to reapply the change if needed.
- The `undo` and `redo` history will be cleared after closing EduTuTu. (i.e. `undo` cannot be executed when EduTuTu is first opened.)
- Only the last 200 executed commands are saved in the `undo` and `redo` history.

**Known Issues:**
- After `undo` or `redo` is executed, EduTuTu will display every student present. 
<br> The list of students displayed by the latest `find` command **will not be preserved**.

[Back to Table of Contents](#table-of-contents)

***

### 2.11 Displaying Pie Chart of Class Distribution: `pie`

Creates a pie chart of the distribution of students in each class, by `CLASS_ID`.

**[Command Format](#command-format):** `pie`

For example, given the following currently displayed student with 7 students:
- 2 student in class MA1522
- 2 students in class CS2103T
- 2 student in class CS2100
- 1 student in class MA1521

<div style="display: flex; align-items: flex-start; justify-content: space-around;">

  <!-- Input Section -->
  <div style="width: 45%; vertical-align: top;">
    <p><b>Input:</b> User enters the `pie` command.</p>
    <img src="images/piecommand.png" alt="Pie command input" style="width: 100%;" />
  </div>

  <!-- Output Section -->
  <div style="width: 45%; vertical-align: top;">
    <p><b>Output:</b> A pie chart is displayed, showing the distribution of students in each class.</p>
    <img src="images/piechart.png" alt="Pie chart output" style="width: 100%;" />
  </div>

</div>

**Tips:**
- The pie chart is based on the currently displayed student list.
- Data shown on the window is not live. (i.e. if student data is modified, the old data will persist on the window.)
- Press Esc while the window is in focus to close it.

[Back to Table of Contents](#table-of-contents)

***

### 2.12 Displaying Bar Chart: `bar`

Displays a [bar chart](#bar-chart) showing the number of students who made payments for each month, by `MONTH_PAID`.

**[Command Format](#command-format):** `bar`

* The x-axis represents the months (e.g., 2024-01, 2024-02).
* The y-axis shows the number of students who made their payments during each month.
* If no payments were made in a given month, the value for that month will be zero.

<div style="display: flex; align-items: flex-start; justify-content: space-around;">

  <!-- Input Section -->
  <div style="width: 45%; vertical-align: top;">
    <p><b>Input:</b> User enters the `bar` command.</p>
    <img src="images/barcommandinput.png" alt="Bar command input" style="width: 100%;" />
  </div>

  <!-- Output Section -->
  <div style="width: 45%; vertical-align: top;">
    <p><b>Output:</b> A bar chart displays.</p>
    <img src="images/barcommand.png" alt="Bar chart output" style="width: 100%; margin-top: -10px;" />
  </div>

</div>


**Tips:**
- The bar chart is based on the currently displayed student list.
- Data shown on the window is not live. (i.e. if student data is modified, the old data will persist on the window.)
- Press Esc while the window is in focus to close it.

**Known Issues:**
- If students with empty `MONTH_PAID` are present in the currently displayed list, they will be represented in the bar chart as an empty string.
<br> **Fix:** Use the `find` command to filter only for students with non-empty `MONTH_PAID`.

[Back to Table of Contents](#table-of-contents)

***

### 2.13 Viewing Command History: *Arrow Keys*

Allows users to quickly access previously entered commands using the up and down arrow keys. 

**[Command Format](#command-format):** *No specific command required.*

* Press the **up arrow key** to cycle back through previously entered commands.
* Press the **down arrow key** to move forward through the [command history](#command-history).
* This feature is useful for repeating recent commands without needing to retype them.


**Tips**
- Only commands that are executed successfully are saved in command history.
- Command history persists even after the program is closed. It is saved in `[JAR file location]/commandhistory.json`.
- By default, the last 2000 executed commands are saved.

[Back to Table of Contents](#table-of-contents)

***

### 2.14 Viewing Student Details: `info`

Opens a window with detailed information of a specified student.

**[Command Format](#command-format):** `info INDEX`

* `INDEX` refers to the index number shown in the displayed student list.
* The [index](#index) **must be within the range** of the number of people in the list.

Displaying the detailed information window can be done in two ways:

#### Method 1:
**Example Usage:**
<div style="display: flex; flex-wrap: wrap; justify-content: space-around;">

  <!-- Input Section -->
  <div style="width: 45%; margin-bottom: 20px;">
    <p><b>Input:</b> User enters the `info 1` command to display detailed information for the first student.</p>
    <img src="images/infocommand1.png" alt="Info Command Input 1" style="width: 100%;" />
  </div>

  <!-- Output Section -->
  <div style="width: 45%; margin-bottom: 20px; margin-top: 0px;">
    <p><b>Output:</b> A window pops up with the detailed information of the student.</p>
    <img src="images/infocommandoutput.png" alt="Info Command Output 1" style="width: 100%; margin-top: 15px;" />
  </div>

</div>

#### Method 2:
**Example Usage:**
<div style="display: flex; flex-wrap: wrap; justify-content: space-around;">

  <!-- Input Section -->
  <div style="width: 45%; margin-bottom: 20px;">
    <p style="margin-top: 30px;"><b>Input:</b> Double-click on the student’s entry in the GUI to display detailed information in a pop-up window.</p>
    <img src="images/infocommandoutput2.png" alt="Info Command Input 2" style="width: 100%;" />
  </div>

  <!-- Output Section -->
  <div style="width: 45%; margin-bottom: 30px; margin-top: 15px;">
    <p><b>Output:</b> A window pops up with the detailed information of the student.</p>
    <img src="images/infocommandoutput3.png" alt="Info Command Output 2" style="width: 100%; margin-top: 18px;" />
  </div>

</div>




**Tips:**
- Data shown on the window is not live. (i.e. if the data of the student is modified, the old data will persist on the window.)
- Press Esc while the window is in focus to close it.
- The text from the detailed information window can be highlighted and copied.

[Back to Table of Contents](#table-of-contents)

***

### 2.15 Editing the Data File

[EduTuTu](#edututu) data is stored in a [JSON](#json-file) file at `[JAR file location]/data/EduTuTu.json`. Advanced users can edit that file directly to modify student data.

> **Caution:**
> - If your changes to the data file make its format invalid, EduTuTu will discard all data and start with an empty data file at the next run. It is highly recommended to create a backup of the file before making any edits.
> - Certain edits can cause EduTuTu to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Edit the data file only if you are confident in your ability to update it correctly.

[Back to Table of Contents](#table-of-contents)

***

### 2.16 Saving Data

[EduTuTu](#edututu) data is saved on the hard disk automatically after executing any command that modifies the data. There is no need for manual saving.

[Back to Table of Contents](#table-of-contents)

***

### 2.17 Exiting the Program: `exit`

**[Command Format](#command-format):** `exit`

[Exits](#exit) the program.

Exiting the program can be done in two ways:

#### Method 1:
<div style="display: flex; align-items: flex-start; justify-content: space-around;">

  <!-- Step 1 -->
  <div style="width: 45%; margin-bottom: 20px;">
    <p>1. Click the File button at the top right corner of the window.</p>
    <img src="images/exitcommand.png" alt="Exit Command Step 1" style="width: 100%;" />
  </div>

  <!-- Step 2 with added top margin -->
  <div style="width: 45%; margin-bottom: 20px; margin-top: 20px;">
    <p>2. Click on the Exit button.</p>
    <img src="images/exitcommand2.png" alt="Exit Command Step 2" style="width: 100%;" />
  </div>

</div>

<p>3. The program will close.</p>


#### Method 2:
1. Type the command [exit](#exit) in the command box and press Enter.
   ![Ui](images/exitcommandinput.png)

2. The program will close.

**Tips:**
- Use the `exit` command when you are done with the program.
- If the `exit` command is executed with extra windows opened, the extra windows will not be closed. They have to be closed manually.

[Back to Table of Contents](#table-of-contents)

***

# 3. FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Install the app onto the other computer. Overwrite the all data files it creates with your the data files from your previous EduTuTu folder.

**Q**: How do I save my data?<br> 
**A**: EduTuTu automatically saves changes after data is modified. No manual saving is needed.

**Q**: What if I accidentally execute the clear command? <br>
**A**: You can use the undo command to reverse the clear command.

[Back to Table of Contents](#table-of-contents)

***
<div style="page-break-after: always;"></div>

# 4. Glossary

<a id="class-id"></a>
- **Class ID**: A unique identifier assigned to each class within EduTuTu, helping to organise and locate students in specific classes (e.g., `CS2100`, `ES2660`).

<a id="cli-command-line-interface"></a>
- **CLI (Command Line Interface)**: An interface where users type commands to interact with the application. CLI allows for quick and precise control, often preferred by users familiar with typing commands.

<a id="command-format"></a>
- **Command Format**: The specific way a command should be typed to work properly in EduTuTu. Following the exact format is essential for the command to be understood by the application.

<a id="command-history"></a>
- **Command History**: A feature that stores a log of previously entered commands. Users can navigate this history using arrow keys to quickly repeat recent commands.

<a id="command-word-alias"></a>
- **Command Word Alias**: A shorter version of a command word that can be used to perform the same action.

<a id="edututu"></a>
- **EduTuTu**: A desktop application designed for tuition centers to streamline student information management. It combines the speed of a Command Line Interface (CLI) with the convenience of a Graphical User Interface (GUI).

<a id="gui-graphical-user-interface"></a>
- **GUI (Graphical User Interface)**: A user-friendly interface with graphical elements like buttons, icons, and windows, allowing users to interact with the application more visually.

<a id="index"></a>
- **Index**: A unique number assigned to each person entry in EduTuTu, used to identify and select entries for actions like editing or deleting.

<a id="json-file"></a>
- **JSON File**: A text file format for storing data in an organised way, readable by humans and computers. EduTuTu saves all data in a JSON file for easy access and editing.

<a id="month-paid"></a>
- **Month Paid**: A month and year combination, used for representing a month when a person has paid. (e.g., `2024-01`, `2024-12`).

<a id="parameter"></a>
- **Parameter**: A value or setting provided by the user within a command to specify details like a name or phone number. Parameters help customise commands to suit specific actions.

<a id="substring-matching"></a>
- **Substring Matching**: A search method that returns all items containing a provided keyword.

<a id="tag"></a>
- **Tag**: A label or keyword associated with a person, which helps categorise or organise entries (e.g., `student`, `alumni`, `parent`).

[Back to Table of Contents](#table-of-contents)

***

### Known Issues

1. **Multiple Screens:**
<br> If you move the application to a secondary screen and later switch to using only the primary screen, the GUI might open off-screen.
<br> **Fix:** Delete the `preferences.json` file created by the application before running it again.

[Back to Table of Contents](#table-of-contents)

***
<div style="page-break-after: always;"></div>

## Command summary

| Action                      | Format, Examples                                                                                               |
|-----------------------------|----------------------------------------------------------------------------------------------------------------|
| **Add**                     | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…`                                                         |
| **Bar Chart**               | `bar`                                                                                                          |
| **Clear**                   | `clear`                                                                                                        |
| **Delete**                  | `delete INDEX`<br>                                                                                             |
| **Edit**                    | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [c/CLASS_ID] [f/FEES] [m/MONTH_PAID] [e/EMAIL] [a/ADDRESS] [t/TAG]…`<br> |
| **Exit**                    | `exit`                                                                                                         |
| **Find**                    | `find KEYWORD [MORE_KEYWORDS]`<br>                                                                             |
| **Help**                    | `help`                                                                                                         |
| **List**                    | `list`                                                                                                         |
| **Mark Paid**               | `markpaid INDEX YEAR_MONTH`<br>                                                                                |
| **Pie Chart**               | `pie`                                                                                                          |
| **Redo**                    | `redo`<br>                                                                                                     |
| **Undo**                    | `undo`<br>                                                                                                     |
| **Unmark Paid**             | `unmarkpaid INDEX YEAR_MONTH`<br>                                                                              |
| **Viewing Student Details** | `info INDEX` <br>                                                                                              |

[Back to Table of Contents](#table-of-contents)
