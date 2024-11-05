---
layout: page
title: User Guide
---
# EduVault User Guide

**EduVault** is a desktop application **designed for tuition centers to manage students and classes effectively**. Optimised for quick use through a Command Line Interface (CLI) and complemented by an intuitive graphical user interface (GUI), EduVault enables efficient tracking of student and class information, making it ideal for fast typists and busy administrators.

---

<div markdown="span" class="alert alert-primary">:pushpin: **Note:**
A person can have any number of tags (including 0)
</div>
<div markdown="span" class="alert alert-success">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>
<div markdown="span" class="alert alert-danger">:exclamation: **Warning:**
A person can have any number of tags (including 0)
</div>

### **1. Quick Start** 

1. Ensure you have Java `17` or above installed in your Computer.
2. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-W08-2/tp/releases).
3. Copy the file to the folder you want to use as the *home folder* for your AddressBook.
4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar eduvault.jar` command to run the application.  
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.  
   ![Ui](images/Ui.png)
5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.  
   Some example commands you can try:
    1. `list` : Lists all contacts.
    2. `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 pay/false attend/true` : Adds a student named `John Doe` to the Address Book, where he has not made payment but has attended the tuition classes.
    3. `delete 3` : Deletes the 3rd contact shown in the current list.
    4. `clear` : Deletes all contacts.
    5. `exit` : Exits the app.
6. Refer to the table below for the general command format

---
### **2. General Command Format**

The commands in EduVault follow q general format of `COMMAND INDEX PREFIX/...` .

* `COMMAND` refers to the command that you want to execute.
* `INDEX` refers to the student whose data you want to alter, specified by the number prepended to the name of the student on the application.
* `PREFIX` specifies the type of data we want to alter.
    * Refer to the [Prefix Table](#11-prefix-table-) below for the usage of each prefix.

<br>

#### Example
![GeneralCommandFormatExample](images/GeneralCommandFormatExample.png)

For example,  `edit 1 n/Benjamin` edits the student name at index 1(Alex Yeoh, in this case) to Benjamin.

<div markdown="span" class="alert alert-primary">:pushpin: **Note:**
This is a general command format, not ALL commands follow this format! For the specific command formats, refer to the individual section of each command below.
</div>

---
### **3. Adding data**

The commands in this section are used to add new records to the system, such as students and tutorials.

- [Adding a student](#31-adding-a-student)
- [Creating a new tutorial](#32-creating-a-new-tutorial)
- [Enrolling student into a tutorial](#33-enrolling-student-into-a-tutorial)

#### **3.1 Adding a student**

#### **3.2 Creating a new tutorial**

Command: `createtut`

Usage: `createtut tut/TUTORIAL_NAME`

{% raw %}
<div markdown="1" class="smaller-text">
Fields

* `TUTORIAL_NAME`: Name of the tutorial to create
  * Must only contain alphanumeric characters
</div>
{% endraw %}

Example usages

* `createtut tut/physics`

{% raw %}
<div markdown="1" class="smaller-text">
Invalid usages

* Creating a tutorial that has been created already
  * *Error Message: This tutorial already exists in the system.*

* Format errors, check [here](#12-format-errors).

</div>
{% endraw %}

#### **3.3 Enrolling student into a tutorial**

Command:  `enroll`

Usage: `enroll INDEX tut/TUTORIAL_NAME`

{% raw %}
<div markdown="1" class="smaller-text">
Fields

* `INDEX:` Index number as shown in the displayed list of the students.
    * Must be a positive integer 1, 2, 3…
* `TUTORIAL_NAME:` Name of the tutorial
</div>
{% endraw %}

<div markdown="span" class="alert alert-primary">:pushpin: **Note:** 
Student can only be enrolled into existing tutorial. Use createtut to create new tutorials
</div>

Example usages

* `enroll 1 tut/physics`

{% raw %}
<div markdown="1" class="smaller-text">
Invalid usages

* Enrolling student in a tutorial that has not been created yet

    * *Error Message: Tutorial name provided is invalid*

* Enrolling student in a tutorial that they are already in

    * *Error Message: This person is already in the tutorial*

* Format errors, check [here](#12-format-errors)

</div>
{% endraw %}


---

### **4. Viewing and retrieving data**

The commands in this section are used to view and retrieve records on the system, such as students, tutorials, and enrollment status.

- [Listing all students](#41-listing-all-students)  
- [Searching students](#42-searching-students)

#### **4.1 Listing all students**

Command: `list`

<br>

#### **4.2 Searching students**

Search for students that match the condition specified by the given prefix.

<div markdown="span" class="alert alert-success">:bulb:
**Tip:**

This command is best used with reference to its usages in the [Prefix Table](#11-prefix-table-)
</div>


Command:  `find`

Usage: `find [n/NAME] [e/EMAIL] [p/PHONE] [a/ADDRESS] [pay/PAYMENT] [a/ATTENDANCE] [t/TAG] [tut/TUTORIAL_NAME]`

<div markdown="span" class="alert alert-primary">:pushpin: **Note:**

1. Fields wrapped in square brackets `[]` are optional, but at least one of the optional fields must be provided.
2. All searches are case-insensitive.
3. Search is by partial matching for keyword(s) of prefixes `n/`, `e/`, `p/`, `a/`, `t/`.
4. Search is by exact matching for keyword(s) of prefix `tut/`.

</div>

{% raw %}
<div markdown="1" class="smaller-text">
Fields

* `NAME:` Name of the student to search
    * Search for multiple keywords is allowed, and students matching at least one keyword will be displayed.
* `EMAIL:` Email of the student to search
* `PHONE:` Phone number of the student to search
* `ADDRESS`: Address of the student to search
    * Search for multiple keywords is allowed, and students matching at least one keyword will be displayed.
* `PAYMENT:` Search student based on the payment status (Paid/Unpaid)
    * If keyword is `true`, it displays all students who has paid.
    * If keyword is `false`, it displays all students who has not paid.
* `ATTENDANCE:` Search student based on whether they attended any tutorials within a given period
    * Keyword must be in the format of `dd/MM/yyyy:dd/MM/yyyy`, first date is the start date and the second date is the end date.
    * First date must be before the second date.
* `TAG:` Search based on the tag associated to the student
    * Allows multiple uses of this prefix together in the command.

* `TUTORIAL_NAME:` Name of the tutorial to search
    * Search with multiple keywords is allowed, and students matching all keywords will be displayed.
    * Allows multiple uses of this prefix together in the command.

</div>
{% endraw %}


<div markdown="span" class="alert alert-success">:bulb:
**Tip:**

Prefixes can be chained to display more specific results. For instance, using `find n/Alex tut/Math t/ADHD` displays all students named “Alex”, in the “Math” tutorial class, and has a tag “ADHD”.
</div>


Example usages

* `find t/ADHD t/scored_A a/Jurong West Ave 5`

* `find p/9123 attend/07/01/2024:07/02/2024 pay/false`

* `find t/ADHD e/a@example.com`

* `find tut/physics tut/math n/Alex Goh`

{% raw %}
<div markdown="1" class="smaller-text">
Invalid usages

* Entering multiple keywords for a prefix that only allows a single keyword

    * *Error Message: At least one of the inputs…*

* Entering only the prefix without providing any keywords

    * *Error Message: Some inputs are missing\! Please…*

* For `a/ATTENDANCE` field, entering the start date after the end date

    * *Error Message: Start date must be before…*

* More format errors, check [here](#12-format-errors).

</div>
{% endraw %}

---

### **5. Editing and updating data**

The commands in this section are used edit records on the system, such as student information, tutorial information, payment, and attendance status

- [Editing student’s details](#51-editing-a-student)  
- [Logging fees](#52-logging-fees-for-tutorial)  
- [Marking payment](#53-marking-a-students-payment)  
4.4 Marking attendance of student
4.5 Marking attendance of tutorial  
- [Unenroll a student from tutorial](#56-unenrolling-student-from-a-tutorial)
#### **5.1 Editing a student**

Edit the personal information of students within EduVault

Command: `edit`

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [pay/PAYMENT] [t/TAG]…​`

{% raw %}
<div markdown="1" class="smaller-text">
Fields:

* `INDEX`: Index number shown in the displayed person list
    * Must be a positive integer 1, 2, 3, …​
* `[ ]:` Fields wrapped in square brackets are optional
    * At least one of the optional fields must be provided
    * Existing values will be updated to the input values
* `PAYMENT`: Updates the absolute value of a student’s overdue amount
* `TAG:` Existing tag will be replaced by the new tag
    * Remove a student’s tag by typing  `t/` without specifying any tags
* `ATTENDANCE`: Field not editable within edit
* `TUTORIAL`: Field not editable within edit
* `PAYMENT`: Field not editable within edit
</div>
{% endraw %}

<div markdown="span" class="alert alert-primary">:pushpin: **Note:**
More information about other prefixes can be found here
</div>

Example Usage:

* `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
* `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

{% raw %}
<div markdown="1" class="smaller-text">
Invalid usage:

* None of the option fields are input
    * *Error message: At least one field to edit must be provided.*
* Values to edit result in a copy of a student already in EduVault
    * *Error message: This person already exists in the address book.*
* `ATTENDANCE` & `TUTORIAL` & `PAYMENT` prefix used
    * Error message: PREFIX cannot be used in this command
* Format errors, check [here](#12-format-errors)
</div>
{% endraw %}

#### **5.2 Logging fees for tutorial**

*Logging each student's monthly tutorial fees or any other additional fees*

Command:  `addfees`

Usage: addfees `INDEX pay/PAYMENT`

{% raw %}
<div markdown="1" class="smaller-text">
Fields

* `INDEX:` Index number as shown in the displayed list of the students.
    * Must be a positive integer 1, 2, 3…
* `PAYMENT:` Amount in integer that a student have to pay
</div>
{% endraw %}

<div markdown="span" class="alert alert-primary">:pushpin: **Note:**
Fees added will be shown as an increase in overdue amount. If a student has advance payment, logged fees will decrease the advance payment first
</div>

Example usages

* `addfees 1 pay/400`

{% raw %}
<div markdown="1" class="smaller-text">
Invalid usages

* Format errors, check [here](#12-format-errors)
</div>
{% endraw %}

#### **5.3 Marking a student’s payment**

*Recording a student’s payment*

Command:  `markpaid`

Usage: markpaid `INDEX pay/PAYMENT`

{% raw %}
<div markdown="1" class="smaller-text">

Fields

* `INDEX:` Index number as shown in the displayed list of the students.
    * Must be a positive integer 1, 2, 3…
* `PAYMENT:` Amount in integer that a student have paid
</div>
{% endraw %}

<div markdown="span" class="alert alert-primary">:pushpin: **Note:**
Student’s payment will be shown as a decrease in overdue amount. If student pays extra, it will be shown as advanced payment
</div>

Example usages

* `markpaid 1 pay/400`

{% raw %}
<div markdown="1" class="smaller-text">
Invalid usages

* Format errors, check [here](#12-format-errors)
</div>
{% endraw %}

#### **5.6 Unenrolling student from a tutorial**

Command:  `unenroll`

Usage: `unenroll` `INDEX tut/TUTORIAL_NAME`

{% raw %}
<div markdown="1" class="smaller-text">
Fields

* `INDEX:` Index number as shown in the displayed list of the students.
    * Must be a positive integer 1, 2, 3…
* `TUTORIAL_NAME:` Name of the tutorial
</div>
{% endraw %}

<div markdown="span" class="alert alert-primary">:pushpin: **Note:**
Student can only be unenrolled from tutorials that they are currently in
</div>

Example usages

* `unenroll 1 tut/physics`

{% raw %}
<div markdown="1" class="smaller-text">
Invalid usages

* Unenrolling student from a tutorial that they are not in

    * *Error Message: Cannot unenroll STUDENT from TUTORIAL, as…*

* Format errors, check [here](#12-format-errors)
</div>
{% endraw %}

---

### **6. Deleting data**

The commands in this section are used to delete records on the system

- [Deleting a student](#61-deleting-a-student)  
- [Closing a tutorial](#62-closing-a-tutorial)
- [Clearing all entries](#63-clearing-all-entries)

#### **6.1 Deleting a student**

*Deleting student’s record*

Command:  `delete`

Usage: `delete` `INDEX`

{% raw %}
<div markdown="1" class="smaller-text">
Fields

* `INDEX:` Index number as shown in the displayed list of the students.
    * Must be a positive integer 1, 2, 3…
</div>
{% endraw %}

Example usages

* `delete 2`

{% raw %}
<div markdown="1" class="smaller-text">
Invalid usages

* Format errors, check [here](#12-format-errors)
</div>
{% endraw %}

#### **6.2 Closing a tutorial**

Command:  `closetut`

Usage: `closetut tut/TUTORIAL_NAME`

{% raw %}
<div markdown="1" class="smaller-text">
Fields

* `TUTORIAL_NAME`: Name of the tutorial to close
  * Must only contain alphanumeric characters
</div>
{% endraw %}

<div markdown="span" class="alert alert-primary">:pushpin: **Note:**

Only an existing tutorial can be closed. Use [createtut](#32-creating-a-new-tutorial) to create new tutorials, or check the spelling again.
</div>

<div markdown="span" class="alert alert-danger">:exclamation: **Warning:**
If there are students in the tutorial to be closed, closing the tutorial will REMOVE all students in that tutorial and their past attendances for that tutorial.
</div>

Example usages
* `closetut tut/physics`

{% raw %}
<div markdown="1" class="smaller-text">
Invalid usages

* Closing a tutorial that does not exist
    * *Error Message: No tutorial class with the name TUTORIAL_NAME  is found.*
* Format errors, check [here](#12-format-errors)
</div>
{% endraw %}

#### **6.3 Clearing all entries**

*Deleting all tutorial and student records*

Command:  `clear`



---

### **7. Viewing help**
Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Command: `help`

---

### **8. Exiting the program**

Exits the program.

Format: `exit`

---

### **9. Saving data** 

---

### **10. Editing the data file**

---

### **11. Prefix Table** 

Prefixes are used whenever we want to alter the data in EduVault, such as adding, updating or deleting data. They are also used when we want to search for data on the student. Each prefix specifies what type of data we want to alter or search for, and the word(s) following each prefix specifies what to alter the data to or what to search for. The table below is a list of all prefixes that are used in our commands.

<table style="width:100%">
  <tr>
    <th style="width:10%">Prefix</th>
    <th style="width:60%">Prefix description and usage</th>
    <th style="width:30%">Exception Cases</th>
  </tr>

  <!-- n/ prefix row -->

  <tr>
    <td rowspan="3">n/</td>
    <td><u>Description</u><br>Specifies the <b>name</b> of the student.</td>
    <td rowspan="3">For <code>find</code> command, keyword(s) for this prefix <b>need not</b> be alphanumeric, but it <b>must not</b> be blank.</td>
  </tr>
  <tr>
    <td><u>Format</u><br>The keyword(s) following this prefix must be alphanumeric and cannot be blank. <br><br><i>Multiple keywords are allowed.<br>Duplicate prefixes are not allowed.</i></td>
  </tr>
  <tr>
    <td><u>Invalid Usage</u><br> Keyword(s) contain special characters or are blank.<br><br> <i>Error Message: Names should only contain alphanumeric characters...</i></td>
  </tr>

  <!-- p/ prefix row -->

  <tr>
    <td rowspan="3">p/</td>
    <td><u>Description</u><br>Specifies the <b>phone number</b> of the student.</td>
    <td rowspan="3">For <code>find</code> command, keyword(s) for this prefix <b>need not</b> be numbers, and can be less than 3 digits. However, it <b>must not</b> be blank.</td>
  </tr>
  <tr>
    <td><u>Format</u><br>The keyword following this prefix must be numbers, and at least 3 digits.<br><br><i>Multiple keywords are not allowed.<br>Duplicate prefixes are not allowed.</i></td>
  </tr>
  <tr>
    <td><u>Invalid Usage</u><br>Keyword(s) contain non-numeric characters or are less than 3 digits.<br><br><i>Error Message: Phone numbers should only contain numbers...</i></td>
  </tr>

 <!-- e/ prefix row -->

  <tr>
    <td rowspan="3">e/</td>
    <td><u>Description</u><br>Specifies the <b>email</b> of the student.</td>
    <td rowspan="3">For <code>find</code> command, the format constraints to the keyword does not apply. However, it <b>must not</b> be blank.</td>
  </tr>
  <tr>
    <td><u>Format</u><br>The keyword following this prefix must be in the format of “...@domain.com”.<br><br><i>Multiple keywords are not allowed.<br>Duplicate prefixes are not allowed.</i></td>
  </tr>
  <tr>
    <td><u>Invalid Usage</u><br>Keyword does not have the valid email format described above.<br><br><i>Error Message: Emails should be of the format...</i></td>
  </tr>

  <!-- a/ prefix row -->

  <tr>
    <td rowspan="3">a/</td>
    <td><u>Description</u><br>Specifies the <b>address</b> of the student.</td>
    <td rowspan="3">NIL</td>
  </tr>
  <tr>
    <td><u>Format</u><br>The keyword(s) can be any input but cannot be blank.<br><br><i>Multiple keywords are allowed.<br>Duplicate prefixes are not allowed.</i></td>
  </tr>
  <tr>
    <td><u>Invalid Usage</u><br>Keyword(s) is blank.<br><br><i>Error Message: Addresses can take any values, and...</i></td>
  </tr>

 <!-- pay/ prefix row -->

<tr>
  <td rowspan="3">pay/</td>
  <td><u>Description</u><br>Specifies the <b>amount</b> that the student pays or owes.</td>
  <td rowspan="3">For <code>find</code> command, keyword(s) for this prefix <b>are restricted to only</b> <code>true</code> or <code>false</code>.</td>
</tr>
<tr>
  <td><u>Format</u><br>The keyword provided after this prefix should be a number.<br><br><i>Multiple keywords are not allowed.<br>Duplicate prefixes are not allowed.</i></td>
</tr>
<tr>
  <td><u>Invalid Usage</u><br>Keyword provided is not a number.<br><br><i>Error Message: Payment should be an...</i></td>
</tr>

 <!-- attend/ prefix row -->

<tr>
  <td rowspan="3">attend/</td>
  <td><u>Description</u><br>Specifies the <b>attendance date</b> that the student went for class.</td>
  <td rowspan="3">For <code>find</code> command, keyword <b>must be</b> in the format of <code>dd/MM/yyyy:
dd/MM/yyyy</code>
  </td>
</tr>
<tr>
  <td><u>Format</u><br>The keyword provided should be of the format <code>dd/MM/yyyy</code>.<br><br><i>Multiple keywords are not allowed.<br>Duplicate prefixes are not allowed.</i></td>
</tr>
<tr>
  <td><u>Invalid Usage</u><br>Keyword does not have the format specified above.<br><br><i>Error Message: Attendance must be in date format...</i></td>
</tr>

 <!-- t/ prefix row -->

<tr>
  <td rowspan="3">t/</td>
  <td><u>Description</u><br>Specifies the <b>tag</b> associated to the student.</td>
  <td rowspan="3">
    For <code>edit</code> command, keyword for this prefix <b>can be blank</b>.<br><br>
    For <code>find</code> command, keyword for this prefix <b>need not</b> be alphanumeric, but it <b>must not</b> be blank.
  </td>
</tr>
<tr>
  <td><u>Format</u><br>The keyword provided should be alphanumeric with no spaces, and it cannot be blank.<br><br><i>Multiple keywords are not allowed.<br>Duplicate prefixes are allowed.</i></td>
</tr>
<tr>
  <td><u>Invalid Usage</u><br>Keyword does not have the format specified above.<br><br><i>Error Message: Tag names should be...</i></td>
</tr>


 <!-- tut/ prefix row -->

<tr>
  <td rowspan="3">tut/</td>
  <td><u>Description</u><br>Specifies the <b>tutorial</b> of the student.</td>
  <td rowspan="3">
    For <code>enroll</code> and <code>unenroll</code> commands, the keyword <b>need not</b> be alphanumeric, but <b>must not</b> be blank.<br><br>
    For <code>find</code> command, <b>duplicate prefixes are allowed</b>.
  </td>
</tr>
<tr>
  <td><u>Format</u><br>The keyword provided should be alphanumeric and it cannot be blank. Spaces are allowed.<br><br><i>Multiple keywords are allowed.<br>Duplicate prefixes are not allowed.</i></td>
</tr>
<tr>
  <td><u>Invalid Usage</u><br>Keyword does not have the format specified above.<br><br><i>Error Message: Tutorial name should only contain...</i></td>
</tr>


</table>


---

### **12. Format Errors**

| Error Message                                                           | Most Likely Cause                                                                                                                                                                                                                                                                                           |
|:------------------------------------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| *Unknown Command….*                                                     | <ul><li>Command misspelled</li><li>Command not available in the current release</li></ul>                                                                                                                                                                                                                   |
| *Invalid Command format…*                                               | <ul><li>Command word is correct but the format entered is wrong</li> <ul><li>Index is missing, or is a negative number</li> <li>Prefix is missing or misspelled</li> <li>Unidentified inputs after the command word and before the first prefix</li><li>Keywords of the prefix is incorrect</li> </ul></ul> |
| *The student’s index provided is invalid…*                              | <ul><li>Index provided is out of range for the current displayed list</li></ul>                                                                                                                                                                                                                             |
| *Multiple values specified for the following single-valued field(s)...* | <ul><li>Duplicated prefix usage when it is not allowed</li></ul>                                                                                                                                                                                                                                            |                                                                                                                                                                                                                           
| *At least one of the dates is not in the right format…* | <ul><li>The date format used is incorrect</li></ul>                                                                                                                                                                                                               

---

### **13. FAQ**

**Q**: How do I transfer my data to another Computer?  
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

---

### **14. Known issues**

---

### **15. Archiving data files \[coming in v2.0\]**




