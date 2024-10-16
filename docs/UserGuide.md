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
   * [Viewing help](#viewing-help--help)
   * [Exiting the program](#exiting-the-program--exit)

4. [Data Management](#data-management-in-academyassist)
4. [FAQ](#faq)

5. [Command summary](#command-summary)

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
   1. Open a terminal window, and navigate to the root of your project folder.
   2. Run the `java -version` command to confirm the terminal is using Java 17.
   3. If Java version is not Java 17, download it [here](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

2. Download the latest `AcademyAssist.jar` file from the official website.

3. Copy the file to the folder you want to use as the _home folder_ for your AcademyAssist.

4. Double-click the file to start the app.

5. For first time users, a help window appears automatically and details of some basic features are shown. 

6. Type the command in the command box and press Enter to execute it. For example, to bring up the help window
again, type `help` and press Enter. 

7. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

* Words in `UPPER_CASE` are the parameters to be supplied by the user.
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Parameters can be in any order.
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will 
be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple 
lines as space characters surrounding line-breaks may be omitted when copied over to the application.

</div>

### Adding a student : `add`

Adds a new student to the tuition center management system.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS i/IC_NUMBER s/SUBJECT [t/TAG]`

* `NAME` should be 1-100 characters long and contain only alphabets and spaces.
* `PHONE_NUMBER` should be an 8-digit number.
* `EMAIL` is optional and should follow the format username@domain.
* `ADDRESS` is optional.
* `IC_NUMBER` should follow the format of Singaporean IC and FIN numbers (e.g., S1234567A).
* `SUBJECT` should follow 
* `TAG` is optional. 

Examples:
* `add n/John Doe p/81003999 e/johndoe@gmail.com a/9 Smith Street ic/T384859A s/Science t/student`

### Deleting a student : `delete`

Removes a student from the tuition center management system.

Format: `delete STUDENT_ID`

* `STUDENT_ID` should be a 5-digit number.

Examples:
* `del 12345`

### Editing a student : `edit`

Edits an existing student's details in the system.

Format: `edit STUDENT_ID FIELD:NEW_VALUE`

* `STUDENT_ID` should be a 5-digit number.
* `FIELD` can be one of: Phone Number, Address, Class Taken, or Academic Year.
* `NEW_VALUE` should follow the format for the respective field.

Examples:
* `edit 12345 Address:New Address`
* `edit 12345 Phone:91234567`

### Viewing all students : `view`

Shows a list of all students in the system.

Format: `view`

### Finding a student : `find`

Finds students whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive.
* Only the name is searched.
* Students matching at least one keyword will be returned.

Examples:
* `find John` returns `John` and `John Doe`
* `find John Jane` returns any student having names `John` or `Jane`

### Adding a class to a student : `addc`

Adds a class to an existing student's record.

Format: `addc STUDENT_ID CLASS_NAME`

* `STUDENT_ID` should be a 5-digit number.
* `CLASS_NAME` should be a combination of Subject and Number (e.g., Science1).

Examples:
* `addc 12345 Science1`

### Sorting students : `sort`

Sorts the list of students based on a specified field.

Format: `sort FIELD`

* `FIELD` can be either `name` or `class`.

Examples:
* `sort name`
* `sort class`

### Clearing all entries : `clear`

Clears all student entries from the system.

Format: `clear`

### Viewing help : `help`

Shows a message explaining how to access the help page.

Format: `help`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

---

## Data Management in AcademyAssist

Welcome to the Data Management section of AcademyAssist! Here, we'll explain how your important information
is stored and managed in a simple, easy-to-understand way.

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
it unless you're very comfortable with computers. It's safer to make changes through the AcademyAssist program.


### What If Something Goes Wrong?

Don't panic! AcademyAssist is designed to handle most issues smoothly. But if you do run into problems:

1. **Check for Messages**: If there's an issue loading your data, AcademyAssist will show a helpful
message explaining what's wrong.

2. **Use Your Backup**: This is where your backup copy comes in handy. You can replace the problematic
file with your backup to get back on track.

## Moving to a New Computer?

Taking AcademyAssist with you is easy:

1. Install AcademyAssist on your new computer.
2. Find your data file on the old computer (we'll show you where in the program).
3. Copy this file to the same location on your new computer.
4. Start AcademyAssist, and all your information will be there!

Remember, AcademyAssist is here to make managing student information easy and stress-free. If you ever have questions about your data, just ask - we're always happy to help!


--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains 
the data of your previous AcademyAssist folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action | Format, Examples                                                                                                                                                                                  |
|--------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add** | `add n/NAME ic/IC_NUMBER e/EMAIL p/PHONE_NUMBER a/ADDRESS c/CLASS y/ACADEMIC_YEAR` <br> e.g., `add n/John Doe ic/T384859A e/johndoe@gmail.com p/81003999 a/9 Smith Street c/Science1 y/Standard1` |
| **Delete** | `del STUDENT_ID`<br> e.g., `del 12345`                                                                                                                                                            |
| **Edit** | `edit STUDENT_ID FIELD:NEW_VALUE`<br> e.g.,`edit 12345 Address:New Address`                                                                                                                       |
| **View** | `view`                                                                                                                                                                                            |
| **Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find John Jane`                                                                                                                                         |
| **Add Class** | `addc STUDENT_ID CLASS_NAME`<br> e.g., `addc 12345 Science1`                                                                                                                                      |
| **Sort** | `sort s/FIELD`<br> e.g., `sort s/name`                                                                                                                                                            |
| **Clear** | `clear`                                                                                                                                                                                           |
| **Help** | `help`                                                                                                                                                                                            |
| **Exit** | `exit`                                                                                                                                                                                            |
