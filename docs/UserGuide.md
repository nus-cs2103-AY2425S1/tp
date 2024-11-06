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

The commands follow the general format of `COMMAND INDEX PREFIX/...` .

* `COMMAND` refers to the command that you want to execute.
* `INDEX` refers to the student data you want to alter, specified by the number prepended to the name of the student on the application.
* `PREFIX` specifies the type of data we want to alter.
    * Refer to the prefix table below for the usage of each prefix.

<br></br>
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
4.2 Search

#### **4.1 Listing all students**

Command: `list`

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
Eduvault data is saved in the hard disk automatically after any command that changes the data.
There is no need to save manually.

---

### **10. Editing the data file**
Eduvalt data is saved automatically as a JSON file at `[JAR file location]/data/addressbook.json`.
Advanced users are welcome to update data directly by editing that data file.

For reference, please refer to the Developer Guide for more details.




---

### **11. Command summary**

---

### **12. Format Errors**

| Error Message | Most Likely Cause |
| :---- | :---- |
| *Unknown Command….* | <ul><li>Command misspelled</li><li>Command not available in the current release</li></ul> |
| *Invalid Command format…* | <ul><li>Command word is correct but the format entered is wrong</li> <ul><li>Index is missing, or is a negative number</li> <li>Prefix is missing or misspelled</li> <li>Unidentified inputs after the command word and before the first prefix</li></ul></ul>|
| *The student’s index provided is invalid…* | <ul><li>Index provided is out of range for the current displayed list</li></ul> |
| *Multiple values specified for the following single-valued field(s)...* | <ul><li>Duplicated prefix usage when it is not allowed</li></ul> |

---

### **13. FAQ**

**Q**: How do I transfer my data to another Computer?  
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

---

### **14. Known issues**

---

### **15. Archiving data files \[coming in v2.0\]**




