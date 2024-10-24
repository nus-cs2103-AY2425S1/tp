---
layout: page
title: AcademyAssist User Guide

---

## Table of Contents

1. [Introduction](#introduction)

2. [Quick Start](#quick-start)

3. [Features](#features)
   * [Adding a student](#adding-a-student--add)
   * [Deleting a student](#deleting-a-student--del)
   * [Editing a student](#editing-a-student--edit)
   * [Viewing all student](#viewing-all-students--view)
   * [Finding a student](#finding-a-student--find)
   * [Sorting students](#sorting-students--sort)
   * [Clearing all entries](#clearing-all-entries--clear)
   * [Getting help](#getting-help--help)
   * [Exiting the program](#exiting-the-program--exit)

4. [Data Management](#data-management-in-academyassist)

5. [FAQ](#faq)

6. [Command summary](#command-summary)

## Introduction
Welcome to AcademyAssist, your ultimate solution for efficient student contact management in tuition centers.
This desktop application is designed to streamline your administrative tasks, allowing you to focus more
on what truly matters - educating and nurturing young minds.

AcademyAssist combines the power of a **Command Line Interface (CLI) with the user-friendly aspects of a Graphical
User Interface (GUI).** This hybrid approach ensures that you can manage your student database with
lightning-fast efficiency while still enjoying the visual benefits of modern software design. Whether you're a
seasoned administrator or new to digital management systems, AcademyAssist is intuitive enough for beginners yet
powerful enough for experts. With features like quick student addition, easy information retrieval,
and automated data saving, AcademyAssist is set to revolutionize how you manage your tuition center.

Let's embark on this journey to simpler, faster, and more effective student management!

---

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.
   1. Open a terminal window, and go to the main folder where all your project files are stored.
   2. Run the `java -version` command to confirm the terminal is using Java 17.
   3. If Java version is not Java 17, download it [here](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

2. Download the latest `AcademyAssist.jar` file from the official website.

3. Move the file to the folder you want to use as the _home folder_ for your AcademyAssist.

4. Double-click the file to start the app.

5. For first time users, a help window appears automatically and details of some basic features are shown.

6. Type the command in the command box and press Enter to execute it. For example, to bring up the help window
again, type `help` and press Enter.

7. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

* Words in `UPPER_CASE` are the inputs to be supplied by the user.
  e.g `delete STUDENT_ID` means the user has to enter the ID number of the student to be deleted.

* Parameters can be in any order.
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `view`, `exit` and `clear`) will
be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple
lines as space characters surrounding line-breaks may be omitted when copied over to the application.

</div>

### Adding a student : `add`

Adds a new student to the tuition center management system.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS i/IC_NUMBER yg/YEAR_GROUP s/SUBJECT`

* `NAME` should be 1-100 characters long and contain only alphabets and spaces.
* `PHONE_NUMBER` should be an 8-digit number.
* `EMAIL` is compulsory and should follow the format username@domain.
* `ADDRESS` is compulsory.
* `IC_NUMBER` is compulsory and should follow the format of Singaporean IC and FIN numbers (e.g., S1234567A).
* `YEAR_GROUP` is compulsory and should be a number within 1-13 (which represents primary school years 1 - 6 and
secondary school years 7 - 13).
* `SUBJECT` is compulsory, and you can add multiple subjects by repeating the s/ field. Only subjects that are available
within the tuition centre will be allowed. Subjects are case-insensitive (i.e. science, Science, SCIENCE will be treated
as the same).

Examples:
* `add n/Sam Tan p/81003999 e/samtan@gmail.com a/9 Smith Street i/T3848559A yg/3 s/Science`

![Add Success Message](/images/add.png)

### Deleting a student : `delete`

Removes a student from the tuition center management system.

Format: `delete STUDENT_ID`

* `STUDENT_ID` is compulsory and is of the format: S followed by a 5-digit number (e.g. S00001). The ID of a student is
  automatically assigned when the student contact is first added and can be found by viewing the student's details.

Examples:
* `delete S00001`

![Delete Success Message](/images/delete.png)

### Editing a student : `edit`

Edits an existing student's details in the system.

Format: `edit STUDENT_ID FIELD/NEW_VALUE`

* `STUDENT_ID` is compulsory and is of the format: S followed by a 5-digit number (e.g. S00001). The ID of a student is
automatically assigned when the student contact is first added and can be found by viewing the student's details.
* `FIELD` can be one of: Name, Phone Number, Email, Address, IC Number or Subject taken.
* `NEW_VALUE` should follow the format for the respective field.

Examples:
* `edit S00001 a/New_Address`
* `edit s00002 p/91234567 a/New_Address`

![Edit Success Message](/images/edit.png)

### Viewing all students : `view`

Shows a list of all students in the system.

Format: `view`

![View Success Message](/images/view.png)

### Finding a student : `find`

Finds students whose names contain any of the given keywords.

Format: `find NAME [MORE_NAMES]`

* The search is case-insensitive (i.e. John, JOHN, john are all treated the same way).
* You can only search for a student by their name.
* Students matching at least one keyword will be returned.

Examples:
* `find John` returns `John` and `John Doe`
* `find John Jane` returns any student having names `John` or `Jane`

![Find Success Message](/images/find.png)

### Adding a class to a student : `addc`

Adds a class to an existing student's record.

Format: `addc STUDENT_ID s/SUBJECT`

* `STUDENT_ID` is compulsory and is of the format: S followed by a 5-digit number (e.g. S00001). The ID of a student is
  automatically assigned when the student contact is first added and can be found by viewing the student's details.
* `SUBJECT` is compulsory, and you can add multiple subjects by repeating the s/ field. Subjects are case-insensitive
  (i.e. science, SCIENCE, Science are treated the same way).

Examples:
* `addc S00001 s/Science`

![addc Success Message](/images/addc.png)

### Sorting students : `sort`

Sorts the list of students based on a specified field.

Format: `sort s/FIELD`

* `FIELD` can be either `name` or `class`.

Examples:
* `sort s/name`
* `sort s/class`

![Sort Success Message](/images/sort.png)

### Clearing all entries : `clear`

Clears all student entries from the system.

Format: `clear`

![Clear Success Message](/images/clear.png)

### Getting help : `help`

Shows a message explaining how to access the help page.

Format: `help`

![Help Success Message](/images/help.png)

### Exiting the program : `exit`

Exits the program.

Format: `exit`

---

## Data Management in AcademyAssist

Welcome to the Data Management section of AcademyAssist! Here, we'll explain how your important information
is stored and managed.

### How Your Data is Saved

Don't worry about constantly saving your work - AcademyAssist has got you covered! Here's what you need to know:

1. **Automatic Saving**: Every time you make a change, like adding a new student or updating information,
AcademyAssist saves it right away. You don't have to do anything!

2. **Where Your Data Lives**: All your information is safely stored on your computer in a special file.
It's like a digital filing cabinet just for AcademyAssist.

3. **Easy to Understand**: We use a format called JSON to store your data. While you don't need
to know the details, it's designed to be easy for both computers and humans to read if needed.

### Keeping Your Data Safe

Your information is important, so here are some tips to keep it secure:

1. **Make Backups**: It's always a good idea to have an extra copy of your data. Once a week,
why not copy your AcademyAssist file to a USB drive or cloud storage?

2. **Be Careful with Editing**: While it's possible to open and edit the data file directly, we don't recommend
it unless you're an expert in CLI applications. It's safer to make changes through the AcademyAssist program.


### What If Something Goes Wrong?

Don't panic! AcademyAssist is designed to handle most issues smoothly. But if you do run into problems:

1. **Check for Messages**: If there's an issue loading your data, AcademyAssist will show a helpful
message explaining what's wrong.

2. **Use Your Backup**: This is where your backup copy comes in handy. You can replace the problematic
file with your backup to get back on track.

Remember, AcademyAssist is here to make managing student information easy and stress-free.
If you ever have questions about your data, just ask - we're always happy to help!


--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains
the data of your previous AcademyAssist folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action | Format, Examples                                                                                                                                                                        |
|--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS i/IC_NUMBER yg/YEAR_GROUP s/SUBJECT` <br> e.g., `add n/John Doe p/81003999 e/johndoe@gmail.com a/9 Smith Street i/T384859A yg/3 s/Science` |
| **Delete** | `delete STUDENT_ID`<br> e.g., `delete S00001`                                                                                                                                           |
| **Edit** | `edit STUDENT_ID FIELD/NEW_VALUE`<br> e.g.,`edit S00001 a/New_Address`                                                                                                                  |
| **View** | `view`                                                                                                                                                                                  |
| **Find** | `find NAME [MORE_NAMES]`<br> e.g., `find John Jane`                                                                                                                                     |
| **Add Class** | `addc STUDENT_ID s/SUBJECT`<br> e.g., `addc S00003 Science`                                                                                                                             |
| **Sort** | `sort s/FIELD`<br> e.g., `sort s/name`                                                                                                                                                  |
| **Clear** | `clear`                                                                                                                                                                                 |
| **Help** | `help`                                                                                                                                                                                  |
| **Exit** | `exit`                                                                                                                                                                                  |
