---
layout: page
title: AcademyAssist User Guide

---

# Table of Contents

1. [Introduction](#introduction)

2. [Quick Start](#quick-start)

3. [Features](#features)
    * [Student Management](#student-management)
      * [Adding a student](#adding-a-student--add)
      * [Deleting a student](#deleting-a-student--delete)
      * [Editing a student](#editing-a-student--edit)
      * [Listing all students](#listing-all-students--list)
      * [Viewing a student's detail](#viewing-a-students-detail--detail)
    * [Searching and Sorting](#searching-and-sorting)
        * [Finding a student](#finding-a-student--find)
        * [Sorting students](#sorting-students--sort)
        * [Filtering students](#filtering-students--filter)
    * [Subject Management](#subject-management)
        * [Adding a subject to a student](#adding-a-subject-to-a-student--addsubject)
        * [Tracking student count for each subject](#tracking-student-count-for-each-subject--tracksubject)
    * [Utility Features](#utility-features)
        * [Clearing all entries](#clearing-all-entries--clear)
        * [Getting help](#getting-help--help)
        * [Exiting the program](#exiting-the-program--exit) 

4. [Data Management](#data-management-in-academyassist)

5. [FAQ](#faq)

6. [Command summary](#command-summary)

# Introduction
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

# Quick start

1. Ensure you have Java `17` or above installed in your Computer.
   1. Open a terminal window, and go to the main folder where all your project files are stored.
   2. Run the `java -version` command to confirm the terminal is using Java 17.
   3. If Java version is not Java 17, download it [here](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

2. Download the latest `academyassist.jar` file from the official website.

3. Move the file to the folder you want to use as the _home folder_ for your AcademyAssist.

4. Double-click the file to start the app.

5. For first time users, a help window appears automatically and details of some basic features are shown.

6. Type the command in the command box and press Enter to execute it. For example, to bring up the help window
again, type `help` and press Enter.

7. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

# Features

<div markdown="block" class="alert alert-info">

* Words in `UPPER_CASE` are the inputs to be supplied by the user.
  e.g `delete STUDENT_ID` means the user has to enter the ID number of the student to be deleted.

* Every student added will be assigned a unique, non-modifiable `STUDENT_ID` that begins with S followed by a 5 digit 
number (e.g. S00003).
<div markdown="span" class="alert alert-note">:memo: **Note:**
The `STUDENT_ID` of a student is non-replaceable once deleted. i.e. if you have one student with `STUDENT_ID
` S00001 and you delete that student, the next student you add will be assigned the next `STUDENT_ID` e.g. S00002 
(instead of replacing S00001).

</div>

* Parameters can be in any order.
  e.g. if the command specifies `n\NAME p\PHONE_NUMBER`, `p\PHONE_NUMBER n\NAME` is also acceptable.

* Items in square brackets are optional.
  e.g. `s\SUBJECT [s\MORE_SUBJECTS]` can be used as `s\SUBJECT` or `s\SUBJECT s\MORE_SUBJECTS`.

* Items with `...` after them can be repeated.
  e.g. `s\SUBJECT [s\MORE_SUBJECTS]...` can be used as `s\SUBJECT`, `s\SUBJECT s\SUBJECT`, `s\SUBJECT s\SUBJECT 
s\SUBJECT`.

* Commands are case-sensitive (e.g., `add` is not the same as `Add`). Hence, commands should be in lowercase.

* Prefixes are case-sensitive (e.g., `n\` is not the same as `N\`). Hence, prefixes should be in lowercase.

* Parameters are case-insensitive (e.g., `Science`, `SCIENCE`, `science` are treated as the same) except for sort 
function.

* Leading and trailing spaces will be removed from the parameters. Hence, the length of the parameters is not affected 
by the leading and trailing spaces.

* Extraneous parameters for commands that do not take in parameters (such as `tracksubject`,`help`, `list`, `exit` and 
`clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple
lines as space characters surrounding line-breaks may be omitted when copied over to the application.

</div>

## Student Management
### Adding a student : `add`

Adds a new student to the student management system.

Format: `add n\NAME i\NRIC yg\YEARGROUP p\PHONE e\EMAIL a\ADDRESS s\SUBJECT [s\MORE_SUBJECTS]...`

#### Parameters Constraints:
* `NAME` should not be blank and should between 2 and 255 characters long. Names should only contain alphabets, spaces,
and the special characters (-/') excluding parentheses. Names should start and end with an alphabet,
and there should not be more than one consecutive special character.
* `NRIC` is compulsory and should follow the format of Singaporean IC and FIN numbers. It should start with one of
  `S, T, F, G, M` followed by a 7-digit number and another alphabet (e.g., S1234567A). 
<div markdown="span" class="alert alert-note">:memo: **Note:**
Contacts added should not have the same NRIC number. New contacts having the same NRIC number as an existing
contact will be treated as a duplicate and will not be allowed.

</div>
* `YEAR_GROUP` is compulsory and should be a number within 1-13 (which represents primary school years 1 - 6 and
  secondary school years 7 - 13).
* `PHONE_NUMBER` should only contain number. It should be between 4 and 20 digits long with no spaces in between.
* `EMAIL` should follow the format username@domain.
* `ADDRESS` allow any characters and should not be empty. The maximum length is 300 characters including spaces in 
between. 
* `SUBJECT` is compulsory, and you can add multiple subjects by repeating the `s\` field. Only subjects that are 
available within the tuition centre will be allowed.
* `[MORE_SUBJECTS]` is optional and can be repeated to add more subjects.
<div markdown="span" class="alert alert-note">:memo: **Note:**
Repeated subjects will be ignored. For example, if you add `s\Science s\Science`, only one `Science` subject 
will be added.

</div>
* A student ID is automatically generated and assigned upon successful addition. It will be displayed in the success 
message and can be used for `addsubject`, `edit`, `detail` and `delete`.

Examples:
* `add n\Sam Tan i\T3848559A yg\3 p\81003999 e\samtan@gmail.com a\9 Smith Street \Science`
* `add n\John Doe i\S1234567A yg\2 p\91234567 e\johndoe@yahoo.com a\10 Orchard Road s\Science s\Math`

![Add Success Message](/images/add.png)

### Deleting a student : `delete`

Removes a student from the tuition center management system.

Format: `delete STUDENT_ID`

* `STUDENT_ID` is compulsory and is of the format: S followed by a 5-digit, starting from 00001 to 99999. The ID of a 
student is automatically assigned when the student contact is first added and can be found by viewing the student's 
details.
<div markdown="span" class="alert alert-note">:memo: **Note:**
The `STUDENT_ID` of a student is non-replaceable once deleted. i.e. if you have one student with `STUDENT_ID
` S00001 and you delete that student, the next student you add will be assigned the next `STUDENT_ID` e.g. S00002 
(instead of replacing S00001).

</div>

Examples:
* `delete S00001`

![Delete Success Message](/images/delete.png)

### Editing a student : `edit`

Edits an existing student's details in the system.

Format: `edit STUDENT_ID FIELD\NEW_VALUE`

* `STUDENT_ID` is compulsory and is of the format: S followed by a 5-digit, starting from 00001 to 99999. The ID of a 
student is automatically assigned when the student contact is first added and can be found by viewing the student's 
details.
* `FIELD` can be one of: Name(`n\ `), Phone Number(`p\ `), Email(`e\ `), Address(`a\ `), NRIC(`i\ `) or Subject 
taken(`s\ `).
* `NEW_VALUE` should follow the constraints of the specified field. You may refer to the constraints of each field 
in the [add feature](#adding-a-student--add) section.
* Although editing the NRIC is allowed, it must not match any other student's NRIC in the system. 
An error message will be shown if a duplicate is detected.

Examples:
* `edit S00001 a\New_Address`
* `edit s00002 p\91234567 a\New_Address`

![Edit Success Message](/images/edit.png)

### Listing all students : `list`

Shows a list of all students in the system.

Format: `list`

![List Success Message](/images/list.png)

### Viewing a student's detail : `detail`

Displays a window that shows the student's details.

Format: `detail STUDENT_ID`

* `STUDENT_ID` is compulsory and is of the format: S followed by a 5-digit, starting from 00001 to 99999. The ID of a student is
  automatically assigned when the student contact is first added and can be found by viewing the student's details.
* The student's details will be displayed in a pop-up window.
* User may use keyboard shortcut `B` to close the pop-up window.

Examples:
* `detail S00001`

![Detail Success Message](/images/detail.png)

## Searching and Sorting
<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
All the following commands will update the list of students shown in the main window.
To return to the full list of students, use the `list` command.

</div>

### Finding a student : `find`

Finds students whose name contains any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive (i.e. John, JOHN, john are all treated the same way).
* You can only search for a student by their name.
* `NAME` should not be blank and should between 2 and 255 characters long. Names should only contain alphabets, spaces,
and the special characters (-/') excluding parentheses. Names should start and end with an alphabet,
and there should not be more than one consecutive special character.
* Students matching at least one keyword will be returned.
<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
After finding, you can use `list` command to return to the full list of students.

</div>
Examples:
* `find John` returns `John` and `John Doe`
* `find John Jane` returns any student having names `John` or `Jane`
* `find J` returns any student having names starting with `J`

![Find Success Message](/images/find.png)

### Sorting students : `sort`

Sort the list of students based on a specified field.

Format: `sort by\FIELD`

* `FIELD` can be either `name`, `subject`, `studentID` or `yearGroup`.
* Sorting by name will sort students in lexicographical ascending order of their names.
* Sorting by subject will sort students based on the lexicographically smallest subject they are taking.
* Sorting by studentID will sort students based on ascending order of studentID.
* Sorting by yearGroup will sort students in ascending order based on their year group.
<div markdown="span" class="alert alert-note">:memo: **Note:**
FIELD is case-sensitive (e.g., `NAME` is not the same as `name`). Hence, FIELDs should be in lowercase.

</div> 

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Sort command will sort the current list of students shown in the main window.
For example, if you have filtered the list by year group 2 students and then use the `sort by\name` command, only year 
group 2 students will be sorted.

</div>

Examples:
* `sort by\name`
* `sort by\subject`
* `sort by\yearGroup`
* `sort by\studentID`

![Sort Success Message](/images/sort.png)

### Filtering the list : `filter`

Shows a list of students filtered by year group or subject.

Format: `filter FIELD\VALUE`

* You can only filter by EITHER year group or class.
* You can only filter one value (eg. filter by Science only).

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
After filtering, you can use `list` command to return to the full list of students.

</div>

Examples:
* `filter yg\2` shows only students who belong to year group 2
* `filter s\Science` shows only students who take Science as a subject

![Filter Success Message](/images/filter.png)

## Subject Management
### Adding subject(s) to a student : `addsubject`

Adds one or more subjects to an existing student's record.

Format: `addsubject STUDENT_ID s\SUBJECT`

* `STUDENT_ID` is compulsory and is of the format: S followed by a 5-digit, starting from 00001 to 99999. The ID of a student is
  automatically assigned when the student contact is first added and can be found by viewing the student's details.
* `SUBJECT` is compulsory, and you can add multiple subjects by repeating the s/ field. Subjects are case-insensitive
  (i.e. science, SCIENCE, Science are treated the same way).
<div markdown="span" class="alert alert-note">:memo: **Note:**
Repeated subjects will be ignored. For example, if you add `s\Science s\Science`, only one `Science` subject will be added.

</div>
* Subjects available: English, Chinese, Malay, Tamil, Math, Further Math, Science, History, Geography, Literature,
Economics, Accounting, Business, Physics, Chemistry, Biology and Computing.

Examples:
* `addsubject S00001 s\Science`
* `addsubject S00002 s\Science s\Math`

![Addsubject Success Message](/images/addsubject.png)

### Tracking student count for each subject : `tracksubject`

Displays a window that shows how many students are taking each of all the subjects.

Format: `tracksubject`

* The subject count will be displayed in a pop-up window.
* User may use keyboard shortcut `B` to close the pop-up window.

<div markdown="span" class="alert alert-note">:memo: **Note:**
Track subject displays the number of students based on the filtered list. For example,
filtering by year 2 students followed by a `tracksubject` command will only show how many year 2 students 
are taking each of the subjects.

</div> 

![TrackSubject Success Message](/images/tracksubject.png)

## Utility Features
### Clearing all entries : `clear`

Clears all student entries from the system.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
This action is irreversible. All student entries will be deleted permanently.

</div>

Format: `clear`

![Clear Success Message](/images/clear.png)

### Getting help : `help`

Shows a help window with details of how to use the different commands.

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
the data of your previous AcademyAssist folder.<br><br>
**Q**: Why is there a gap in the studentID after I deleted a student?<br>
**A**: This is an intentional design, kindly refer to the note under [Features](#features) for more details.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action            | Format, Examples                                                                                                                                                                                    |
|-------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Student**   | `add n\NAME i\NRIC yg\YEARGROUP p\PHONE e\EMAIL a\ADDRESS s\SUBJECT [s\MORE_SUBJECTS]...` <br><br> e.g., `add n\John Doe i\T384859A yg\3 p/81003999 e\johndoe@gmail.com a\9 Smith Street s\Science` |
| **Delete Student**| `delete STUDENT_ID`<br><br> e.g., `delete S00001`                                                                                                                                                   |
| **Edit Student**  | `edit STUDENT_ID FIELD\NEW_VALUE`<br> e.g.,`edit S00001 a\New_Address`                                                                                                                              |
| **List Students** | `list`                                                                                                                                                                                              |
| **View Student**  | `detail STUDENT_ID`<br><br> e.g., `detail S00001`                                                                                                                                                   |
| **Find Student**  | `find NAME [MORE_NAMES]`<br><br> e.g., `find John Jane`                                                                                                                                             |
| **Filter Students**| `filter FIELD\VALUE`<br><br> e.g., `filter yg\2`                                                                                                                                                    |
| **Add Subject**   | `addsubject STUDENT_ID s\SUBJECT`<br><br> e.g., `addsubject S00003 Science`                                                                                                                         |
| **Track Subjects**| `tracksubject`                                                                                                                                                                                      |
| **Sort Students** | `sort s\FIELD`<br><br> e.g., `sort s\name`                                                                                                                                                          |
| **Clear Data**    | `clear`                                                                                                                                                                                             |
| **Get Help**      | `help`                                                                                                                                                                                              |
| **Exit**          | `exit`                                                                                                                                                                                              |
