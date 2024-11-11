---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

<br>

# Tuteez User Guide

<br>

## Welcome aboard Tuteez

Welcome to Tuteez – a **powerful desktop address book application** designed specifically for **private tutors** to **manage student contacts and lesson schedules**.
With Tuteez, you can effortlessly keep all your student information organized and accessible in one place, so every information you need is right at your fingertips.

Tuteez provides both a Command Line Interface (CLI), allowing quick, keyboard-based commands for speed, and a Graphical User Interface (GUI) with intuitive buttons, menus, and displays for easy navigation.

By letting Tuteez handle your organizational tasks, you can focus on what matters most: teaching. 

In this guide, we will walk you through Tuteez’s key features and demonstrate how it can transform your tutoring experience.
<br>

<div style="page-break-after: always;"></div>

### Table of Contents

<!-- TOC start -->
1. [Welcome Aboard Tuteez](#welcome-aboard-tuteez)<br>
    1.1 [Table of Contents](#table-of-contents)<br>
    1.2 [User Guide Overview](#user-guide-overview)<br>
2. [How to Use this Guide](#how-to-use-this-guide)<br>
    2.1 [Notations Used](#notations-used)<br>
    2.2 [Recommended Sections Based on User Experience](#recommended-sections-based-on-user-experience)<br>
2. [Quick start](#quick-start)<br>
    2.1 [Prerequisites](#prerequisites)<br>
    2.2 [Setting up](#setting-up)<br>
3. [GUI Overview](#gui-overview)<br>
4. [Beginner's Tutorial](#beginner-s-tutorial)<br>
    4.1 [Before You Begin](#before-you-begin)<br>
    4.2 [Exploring the Application](#exploring-the-application)<br>
    4.3 [Starting with a Clean Slate](#starting-with-a-clean-slate)<br>
    4.4 [Adding Your First Student](#adding-your-first-student)<br>
    4.5 [Adding a Lesson to your Student's details](#adding-a-lesson-to-your-student-s-details)<br>
    4.6 [Editing Student Details](#editing-student-details)<br>
    4.7 [Deleting a Student](#deleting-a-student)<br>
    4.8 [Wrapping Up](#wrapping-up)<br>
5. [Features](#features)<br>
    5.1 [Viewing Help : `help`](#viewing-help-help)<br>
    5.2 [Adding a Student: `add`](#adding-a-student-add)<br>
    5.3 [Listing All Students : `list`](#listing-all-students-list)<br>
    5.4 [Editing a Student : `edit`](#editing-a-student-edit)<br>
    5.5 [Adding a Remark: `addremark` or `addrmk`](#adding-a-remark-addremark-or-addrmk)<br>
    5.6 [Deleting a Remark: `deleteremark` or `delrmk`](#deleting-a-remark-deleteremark-or-delrmk)<br>
    5.7 [Adding Lessons: `addlesson` or `addlsn`](#adding-lessons-addlesson-or-addlsn)<br>
    5.8 [Deleting Lessons: `deletelesson` or `dellsn`](#deleting-lessons-deletelesson-or-dellsn)<br>
    5.9 [Searching for Students: `find`](#searching-for-students-find)<br>
    5.10 [Deleting a student : `delete` or `del`](#deleting-a-student-delete-or-del)<br>
    5.11 [Displaying Student Information: `display`](#displaying-student-information-display)<br>
    5.12 [Navigating command history: `↑` or `↓`](#navigating-command-history-or)<br>
    5.13 [Clearing all entries : `clear`](#clearing-all-entries-clear)<br>
    5.14 [Exiting the program : `exit`](#exiting-the-program-exit)<br>
6. [Things You Should Definitely Know](#things-you-should-definitely-know)<br>
    6.1 [Constraints on Adding a Lesson](#constraints-on-adding-a-lesson)<br>
    6.2 [Current Limitations](#current-limitations)<br>
    6.3 [Saving the Data](#saving-the-data)<br>
    6.4 [Editing the Data File](#editing-the-data-file)<br>
7. [Future Features](#future-features)
8. [FAQ](#faq)
9. [Known Issues](#known-issues)
10. [Glossary](#glossary)
11. [Command Summary](#command-summary)
<!-- TOC end -->

<br>
<div style="page-break-after: always;"></div>

### User Guide Overview

<br>

Here is a quick look at the various sections in this user guide:
- **[Quick start](#quick-start)**: A fast-track section to get Tuteez up and running in no time. It includes setup instructions and a list of essential commands you can try out immediately. <br>
- **[GUI Overview](#gui-overview)**: A quick overview of the user interface of Tuteez, with explanations of the left and right panels. <br>
- **[Beginner's Tutorial](#beginner-s-tutorial)**: A step-by-step guide for new users to get started with Tuteez. It includes instructions on adding students, editing details, scheduling lessons, and deleting students. <br>
- **[Features](#features)**: This section covers all the key commands in Tuteez, providing detailed descriptions of each function. You’ll learn how to add students, manage lessons, add remarks, and more. <br>
- **[Things you should definitely know](#things-you-should-definitely-know)**: Important information about constraints and limitations of the current version of Tuteez. <br>
- **[Future Features](#future-features)**: A sneak peek into the exciting features that will be added to Tuteez in future updates. <br>
- **[FAQ](#faq)**: Answers to the most frequently asked questions about Tuteez. <br>
- **[Known issues](#known-issues)**: A list of known issues that you may encounter while using Tuteez. <br>
- **[Command summary](#command-summary)**: A quick reference guide summarising all the commands available in Tuteez. <br>

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

<br>

## How to Use this Guide

<br>

### Notations Used

Here's how to read this User Guide! We use these visual styles to help you navigate better:

| **Text Styles**              | **Description**                              |
|------------------------------|----------------------------------------------|
| `Command text`               | Used to represent command syntax.            |
| **Bold text**                | Highlights important keywords.               |
| [Hyperlink](#notations-used) | Provides clickable links for your reference. |

| **Call-outs**                                                                                    | **Description**                          |
|--------------------------------------------------------------------------------------------------|------------------------------------------|
| <div style="background-color: #ADD8E6; padding: 4px; border-radius: 4px;">ℹ️ **Info**</div>    | For additional information.             |
| <div style="background-color: #FFA07A; padding: 4px; border-radius: 4px;">⚠️ **Warning**</div> | A caution or important warning.         |
| <div style="background-color: lightyellow; padding: 4px; border-radius: 4px;">💡 **Tip**</div>   | Helpful tips to make your experience smoother. |

<br>

### Recommended Sections Based on User Experience

| **User Experience** | **Recommended Sections**                                                                                                                            |
|---------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------|
| **Beginner**        | We highly recommend starting with the [Beginner's Tutorial](#beginner-s-tutorial) to get comfortable with Tuteez’s essential features and commands. |
| **Advanced User**   | Feel free to skip directly to the [Command Summary](#command-summary) for a quick overview of all commands available in Tuteez.                     |

<br>

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

<br>

## Quick start

<br>

### Prerequisites

- Ensure you have Java `17` or above installed in your computer.
  
  To check if you have Java `17` installed, open up a command terminal in your computer, type `java -version` and press <kbd>Enter</kbd>.

  If you do not have Java `17` installed, you can download it from the [official Oracle website](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).

<br>

### Setting up

1. Download the latest `Tuteez.jar` file from [our GitHub Releases](https://github.com/AY2425S1-CS2103T-F09-4/tp/releases).

2. Copy the file to the folder in your computer that you want to use as the _home folder_ for Tuteez.

3. Open a command terminal, navigate into the folder you put the `Tuteez.jar` file in by entering the following command:
   `cd folder/path` where `folder/path` is the path to the folder where the `Tuteez.jar` file is located.

4. Run the application by entering the following command: `java -jar tuteez.jar`<br>
   A GUI similar to the image below should appear in a few seconds. Note how the app contains some sample data for your reference.<br>

   ![Ui](images/Ui.png)

5. Type the command in the command box and press <kbd>Enter</kbd> to execute it. e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a student named `John Doe` to Tuteez.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

6. You may refer to the [Beginner's Tutorial](#beginner-s-tutorial) to get a hands-on walkthrough of the application, or refer to the [Features](#features) section below for details of each command.

<br>

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

<br>

## GUI Overview

![annotated_ui](images/annotated_ui.png)

| Component      | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
|----------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Menu Bar** | - Located at the top of the application. <br> - Contains essential functions including File and Help menus.                                                                                                                                                                                                                                                                                                                                                         |
| **Left Panel** | - Prioritizes important information such as student's phone number, address, and next lesson based on your computer's current time.  <br> - If a lesson is currently ongoing, it will show that lesson as the next lesson on the left panel. <br><br> <div style="background-color: #ADD8E6; padding: 4px; border-radius: 4px;">ℹ️ **Info**:  If your computer's time has been changed, please restart the app to sync our internal clock to your new time!</div> |
| **Right Panel** | - Provides the full view of a student's information when you use the `display` command. <br> - Shows all the student's lesson details and remarks you have left them. <br><br><div style="background-color: #ADD8E6; padding: 4px; border-radius: 4px;">ℹ️ **Info**:  Refer to the [Displaying Student Information](#displaying-student-information-display) section for more details on the `display` command. </div>                                            |
| **Results Box** | - Displays the results of the commands you have entered.                                                                                                                                                                                                                                                                                                                                                                                                            |
| **Command Box** | - The area where you can enter commands to interact with the application.                                                                                                                                                                                                                                                                                                                                                                                           |

<br>

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## Beginner's Tutorial

If you're a new user, fret not! Simply follow this beginner-friendly tutorial that will walk you through your first
steps with the application, which will allow you to get comfortable with managing your student contacts effectively.

<br>

### Before You Begin
Make sure you have:
- Successfully installed Tuteez and launched the application (Refer to the [Quick Start](#quick-start) section if you have not)

<br>

### Exploring the Application
Upon launching the application, you should see a window similar to the one below:

![Ui](images/Ui.png)

The *left panel* displays the list of students you have added. Separated from the left panel by a divider is the *right panel* displays the details of the selected student.
The *Command Box* at the bottom of the window is where you can enter commands to interact with the application.
Above the *Command Box*, you will find the *Results Box*, which displays the results of the commands you have entered.

<br>

### Starting with a Clean Slate
Let us start by clearing the sample data that comes with the application. Type `clear` in the Command Box and press <kbd>Enter</kbd>.

You should see a message indicating that all entries have been cleared, and the list of students should now be empty.

If everything went well, you'll be greeted by this view:

![clear_command](images/clear_command_tutorial.png)

<br>

### Adding Your First Student
Let's say you already have a student named John Doe, and you wish to add his details into Tuteez.

Let's try entering the following command: `add n/John Doe p/98765432 e/johnd@example.com a/Jurong West`

This means that you've added a student named `John Doe`, with the phone number `98765432`, email `johnd@example.com`,
and address `Jurong West`.

If everything went well, you'll be greeted by this view:

![add_command](images/add_command_tutorial.png)

<div style="background-color: #ADD8E6; padding: 4px; border-radius: 4px;">

ℹ️ **Info**: For more details on the `add` command, please refer to the [Add Command](#adding-a-student-add) section.
</div>

<br>

### Adding a Lesson to your Student's details
Now, you have finalized a lesson schedule with John Doe, and you wish to add it to his details.

Let's try entering the following command: `addlesson 1 l/Monday 0900-1100`

This means that you've added a lesson for `John Doe` on `Monday` from `9 am to 11 am`.

If everything went well, you'll be greeted by this view:

![addlesson_command](images/addlesson_command_tutorial.png)

<br>

### Editing Student Details
You've realized that you've made a mistake in John Doe's email address, and wish to edit it.

Let's try entering the following command: `edit 1 e/johndoe@gmail.com`

John's email address should now be updated to `johndoe@gmail.com` and the updated email address should be reflected in Tuteez.

If everything went well, you'll be greeted by this view:

![edit_command](images/edit_command_tutorial.png)

<div markdown="block" style="background-color: #ADD8E6; padding: 4px; border-radius: 4px;">

ℹ️ **Info**: For more details on the `edit` command, please refer to the [Edit Command](#editing-a-student-edit) section.
</div>

<br>

### Deleting a Student
John Doe has decided to stop engaging your tuition services, and you wish to remove him from your list of students.

Let's try entering the following command: `delete John Doe`

John Doe should now be removed from your list of students.

If everything went well, you'll be greeted by this view:

![delete_command](images/delete_command_tutorial.png)

<br>

### Wrapping Up
Congratulations! 😁 You've successfully completed the beginner's tutorial for Tuteez.
You're now ready to move on to more advanced features and commands that Tuteez has to offer.
Simply visit the [Features](#features) section to learn more about the various commands available to you.

<br>

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

<br>

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/Math` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/Secondary`, `t/Math t/Science` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

<br>

### Viewing help: `help`

If you are confused while using the app any point in time, you may use this command to view the help message which explains how to access the web-based user guide.

**Format**: `help`

![help message](images/helpMessage.png)

<br>

### Adding a student: `add`

This command will allow you to add a new student and their personal details to your list of students on the left panel.

**Format**: `add n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [tg/TELEGRAM_USERNAME] [t/TAG]…​ [l/LESSON]…​`

<div style="background-color: #ADD8E6; padding: 4px; border-radius: 4px;">

ℹ️ **Info**: Only the **name** and **phone number** are required fields. The other fields are optional.
</div>
<br>
<div style="background-color: #ADD8E6; padding: 4px; border-radius: 4px;">

ℹ️ **Info**:  You cannot add lessons that clash, meaning lessons cannot be scheduled on the same day and overlap in timing. If a clash is detected, the app will notify you with an error message. To see more details on valid lessons, check out the [constraints on adding a lesson](#constraints-on-adding-a-lesson)
</div>


Acceptable values for each parameter:  

| Parameter    | Format                                                                                                                                                                     | Example            |
|--------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------|
| NAME         | Start the name with a letter, and you’re welcome to include letters, numbers, spaces, and these special characters: `-`, `'`, `.`, `,`, `(`, `)`, `&`, `/`                 | John Doe           |
| PHONE_NUMBER | Enter a phone number with numbers only, that is at least 3 digits long                                                                                                     | 912345678          |
| EMAIL        | Email should follow the format local-part@domain                                                                                                                           | johndoe@gmail.com  |
| ADDRESS      | Addresses can contain any characters                                                                                                                                       | Jurong West #09-11 |
| TAG          | Tag names can include letters, numbers, and spaces                                                                                                                         | Primary 4          |
| lesson       | The **`l/` (lesson)** field should include the **day** of the week (case-insensitive) followed by the **time** in the **24-hour format** `HHMM-HHMM`, separated by a space | monday 1500-1700   |

<div style="background-color: lightyellow; padding: 4px; border-radius: 4px;">

💡 **Tip**: You can add any number of tags and lessons to a student (or you could also omit them)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/Math l/monday 0900-1100`

<br>

### Listing all students: `list`

This command will help you view the list of all your students in Tuteez on the left panel.

**Format**: `list`

<br>

### Editing a student: `edit`

This command will allow you to edit an existing student's details in Tuteez, allowing you to easily update outdated information on the fly.

**Format**: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** such as 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing **tags**, **all previous values will be replaced** by the new ones entered.
    - This means you must **retype all old tags** you wish to keep, as editing will overwrite them completely.
* You can remove all optional fields as specified in the `add` command by typing its parameter prefix (e.g. `t/`) without specifying any values after them.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st student to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd student to be `Betsy Crower` and clears all existing tags.

<br>

### Adding a Remark: `addremark` or `addrmk`

This simple command will allow you to add a remark for a specific student in Tuteez.

**Format**: `addremark INDEX r/REMARK` to add a remark to the student at the specified `INDEX`.

* Adds a new remark to the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** such as 1, 2, 3, …​
* You can add any text as a remark, and remarks are displayed in the order they were added.

<div style="background-color: lightyellow; padding: 4px; border-radius: 4px;">

💡 **Tip**: You can also use the abbreviated command `addrmk` as a shortcut for `addremark`.
</div>

Examples:
* `addremark 1 r/Great progress in Math` Adds the remark "Great progress in Math" to the first student.

<br>

### Deleting a Remark: `deleteremark` or `delrmk`

This command will allow you to delete a remark for a specific student in Tuteez.

**Format**: `deleteremark INDEX ri/REMARK_INDEX` to add a remark to the student at the specified `INDEX`.

* Deletes an existing remark from the student at the specified `REMARK_INDEX`. The remark index refers to the order in which the remarks were added.
* When deleting, if the `REMARK_INDEX` is not valid, an error will be shown on your screen.

Examples:
* `deleteremark 1 ri/2` Deletes the second remark of the first student in the displayed student list.

<br>

### Adding Lessons: `addlesson` or `addlsn`

This command will allow you to add lesson(s) to a specific student in Tuteez.

**Format**: `addlesson INDEX l/LESSON [l/LESSON]…​`

* short form: `addlsn INDEX l/LESSON [l/LESSON]…​`
* This adds new lesson(s) to the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** such as 1, 2, 3, …​
* You can add multiple lessons to a student at once.
* Lessons have to start with the **day** of the week (case-insensitive) followed by the **time** in the **24-hour format** `HHMM-HHMM`, separated by a space.

<div markdown="block" style="background-color: #FFE4E1; padding: 4px; border-radius: 4px;">

⚠️ **Warning**: Lessons added must not clash (i.e., scheduled on the same day and overlapping in timing). If a clash is detected, an error message will be shown on your screen.
</div>
<br>
<div style="background-color: #ADD8E6; padding: 4px; border-radius: 4px;">

ℹ️ **Info**: To see more details on valid lessons, check out the [constraints on adding a lesson](#constraints-on-adding-a-lesson)
</div>

Examples:
* `addlesson 1 l/Monday 0900-1100` Adds a lesson on Monday from 9 am to 11 am to the first student.
* `addlesson 2 l/Tuesday 1400-1600 l/Thursday 1400-1600` Adds lessons on Tuesday and Thursday from 2 pm to 4 pm to the second student.

<br>

### Deleting Lessons: `deletelesson` or `dellsn`

This command will help you in deleting lesson(s) from a specific student in Tuteez.

**Format**: `deletelesson INDEX li/LESSON_INDEX [li/LESSON_INDEX]…​`

* short form: `dellsn INDEX li/LESSON_INDEX [li/LESSON_INDEX]…​`
* Deletes lesson(s) from the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** such as 1, 2, 3, …​
* Lessons are indexed starting from 1 and sorted in ascending order based on their day and time.
* If the `LESSON_INDEX` is not valid, an error will be shown on your screen.

Examples:
* `deletelesson 1 li/1` Deletes the first lesson of the first student.
* `dellsn 2 li/2 li/3` Deletes the second and third lessons of the second student.

<div style="background-color: lightyellow; padding: 4px; border-radius: 4px;">

💡 **Tip**: Want to delete multiple lessons at once? Simply specify multiple different lesson indices in the parameters.
</div>

<br>

### Searching for Students: `find`

This command will assist you in finding students whose names, addresses, tags or lessons contain any of the given keywords. 
Lessons are split into lesson day and lesson time.

**Format**: `find [n/NAME_KEYWORDS] [a/ADDRESS_KEYWORDS] [t/TAG_KEYWORDS] [ld/LESSON_DAY_KEYWORDS] [lt/LESSON_TIME_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* For name keywords, only the name is searched. For address keywords, only the address is searched etc.
* For words, only full words will be matched e.g. `Han` will not match `Hans`
* Lesson day keywords must be a **day** of the week (case-insensitive), or the first 3 letters of a day e.g. `mon tue wed`
* Lesson time keywords must be in **24-hour format** `HHMM-HHMM`. Lessons with overlapping time-ranges will be matched e.g. `0800-1000` will overlap with `0900-1100`
* Persons with at least one parameter matching at least one of its keyword will be returned (i.e. `OR` search).
* For example, `find n/John t/Science English` will return students `John Doe` with tag `Math`, `Alice Richardson` with tag `Science` and `Mary Jane` with tag `English`

Examples:
* `find n/alex david` returns `Alex Yeoh`, `David Li`, as shown below:<br>
  <img src="images/findAlexDavidResult.png" alt="result for 'find alex david'" width="800">

* `find a/jurong` returns students with address `Jurong Lake #09-11` and `jurong west #13-21`
* `find ld/monday lt/1000-1100` returns students with lessons `monday 0800-0900` and `tuesday 0900-1030`

<br>

### Deleting a student: `delete` or `del`

This simple command allows you to delete the specified student from Tuteez.

**Format**: `delete INDEX` or `delete NAME`

* Deletes the student at the specified `INDEX` or by their full `NAME` (case-insensitive).
    - When using the `NAME` option, the full name of the student must be provided.
* The index refers to the index number shown in the displayed student list.
* The index **must be a positive integer** such as 1, 2, 3, …​

<div style="background-color: lightyellow; padding: 4px; border-radius: 4px;">

💡 **Tip**: You can also use the abbreviated command `del` as a shortcut for `delete`.
</div>

Examples:
* `list` followed by `delete 2` deletes the 2nd student in the address book.
* `find Betsy` followed by `delete 1` deletes the first student in the results of the `find` command.
* `delete John Doe` deletes the student with the full name "John Doe" from the address book, ignoring case sensitivity.

<br>

### Displaying Student Information: `display`

This helpful command will help you view comprehensive personal information of a student in Tuteez on the right panel.

**Format**: `display INDEX` or `display NAME`

* This displays the details of the student at the specified `INDEX` or with the specified `NAME`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** such as 1, 2, 3, …​
* The displayed information includes the student's name, phone number, email, address, tags, lessons, and any remarks associated with the student.

Examples:
* `display 1` Shows the details of the first student in the list.
* `display 3` Shows the details of the third student in the list.
* `display john doe` Shows the details of student John Doe in the list, if they were to be found.

<br>

### Navigating command history: <kbd>&uarr;</kbd> or <kbd>&darr;</kbd>

These keyboard shortcuts will allow you to navigate through your command history in the command box, making it easy to access and reuse previously executed commands.

**Format**: Press <kbd>&uarr;</kbd> or <kbd>&darr;</kbd> on your keyboard

* <kbd>&uarr;</kbd> will allow you to navigate to the previous command you have entered.
* <kbd>&darr;</kbd> will allow you to navigate to the next command you have entered.

<div style="background-color: lightyellow; padding: 4px; border-radius: 4px;">

💡 **Tip**: Use these shortcuts to quickly access and reuse previous commands without having to retype them.
</div>
<br>
<div style="background-color: #ADD8E6; padding: 4px; border-radius: 4px;">

ℹ️ **Info**: Only correctly executed commands are saved. Commands that result in errors will not be added to the history.
</div>

<br>

### Clearing all entries: `clear`

This will allow you clears all student entries from Tuteez.

**Format**: `clear`

<div style="background-color: #FFE4E1; padding: 4px; border-radius: 4px;">

⚠️ **Warning**:  This action is irreversible and will wipe your existing data from the application. Make backups of your data if you need to, and use this command with caution!
</div>

<br>

### Exiting the program: `exit`

This command will allow you to shut down and exit the program.

**Format**: `exit`

<div markdown="block" style="background-color: #FFE4E1; padding: 4px; border-radius: 4px;">

⚠️ **Warning**: This action will close the application. You will need to relaunch the application to use it again.
</div>

<br>

<div style="page-break-after: always;"></div>

--------------------------------------------------------------------------------------------------------------------

<br>

## Things you should definitely know

<br>

### Constraints on adding a lesson

Unfortunately, as of `V1.5` there are a few important constraints regarding lessons:  

  1. Lessons are not allowed to overflow into the next day.
  1. Group tuition is currently not supported, so adding overlapping or clashing lessons is not available yet.

This means the following constraints apply:

  1. Lesson start time must be before end time (e.g. `1600-1500`).
  1. Lesson start and end time cannot be identical (e.g. `1300-1300`).
  1. The latest lesson start time is `2358`.
  1. The latest lesson end time is `2359`, `0000` is treated as the start of a new day.

Look forward to [future updates](#future-features) for group tuition support!

<br>

### Current Limitations

As of `V1.5`, our app has a few limitations outlined below. Rest assured, we plan to resolve them in [future updates](#future-features)

1. Remarks can only be added one at a time.
1. If you wish to edit a remark, first delete the incorrect one, then add the updated version.
1. After using the find command to locate student(s), running most other commands will reset the left panel to the default view, similar to calling `list`.
1. Tags that are too long are cut off on the left panel.

<br>

### Saving the data

Tuteez data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

<br>

### Editing the data file

Tuteez data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, Tuteez will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause Tuteez to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

<br>

--------------------------------------------------------------------------------------------------------------------

<br>

## Future Features

With the ever-changing responsibilities of tutors, Tuteez evolves right alongside you, adding new features to support your journey. Here’s what’s in store for future updates!

1. Group Lessons: Tuteez will soon support overlapping lessons and student grouping for group tuition! And as always, it will alert you in advance if there are any scheduling conflicts. 😉
1. Document Management: Upload and associate PDFs or other documents directly with specific students for easy access and organization.
1. Quick Messaging: Instantly open WhatsApp or Telegram chats with students directly from Tuteez with a single click, making it easy to reach out right away.
1. Data Export: Enable seamless exporting of student information and lesson data into files for easy sharing, backup, or analysis outside of Tuteez.
1. Of course, addressing all the limitations stated [above](#current-limitations).

<br>
--------------------------------------------------------------------------------------------------------------------

<br>

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app on the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Tuteez home folder.

<br>

--------------------------------------------------------------------------------------------------------------------

<br>

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

<br>

--------------------------------------------------------------------------------------------------------------------

<br>

## Glossary

These are the key terms used throughout the user guide:

- **GUI**: Graphical User Interface, a type of visual user interface that allows users to interact with the application through graphical elements like buttons and menus.
- **CLI**: Command Line Interface, a type of text-based user interface that allows users to interact with the application by typing commands.

<br>

--------------------------------------------------------------------------------------------------------------------

<br>

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [tg/TELEGRAM_USERNAME] [t/TAG]…​ [l/LESSON]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 tg/jamesho123 t/Math l/monday 0900-1100`
**Clear**  | `clear`
**Delete** | `delete INDEX` or `delete NAME`<br> e.g., `delete 3` or `delete James Ho`
**Display**| `display INDEX` or display `NAME` <br> e.g., `display 1` or display `John Doe`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [tg/TELEGRAM_USERNAME] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com t/Math`
**Add Remark** | `addremark INDEX r/REMARK` to add a remark to your student at `INDEX`<br> e.g., `addremark 1 r/Great progress in Math`
**Delete Remark** |  `deleteremark INDEX ri/REMARK_INDEX` to delete the remark at `REMARK_INDEX` from your student at `INDEX`<br> e.g., `deleteremark 1 ri/2` to delete the second remark of student 1.
**Add Lesson** | `addlesson INDEX l/LESSON [l/LESSON]…​`<br> e.g., `addlesson 1 l/Monday 0900-1100`
**Delete Lesson** | `deletelesson INDEX li/LESSON_INDEX [li/LESSON_INDEX]…​`<br> e.g., `deletelesson 1 li/1`
**Find**   | `find [n/NAME_KEYWORDS] [a/ADDRESS_KEYWORDS] [t/TAG_KEYWORDS] [ld/LESSON_DAY_KEYWORDS] [lt/LESSON_TIME_KEYWORDS]`<br> e.g., `find n/James jake t/science`
**List**   | `list`
**Help**   | `help`
**<kbd>&uarr;</kbd>** | Press the <kbd>&uarr;</kbd> key to navigate to your previous command in the command history.
**<kbd>&darr;</kbd>** | Press the <kbd>&darr;</kbd> key to navigate to your next command in the command history.
**Exit**   | `exit`

<br>
