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

### **2. Adding data**

The commands in this section are used to add new records to the system, such as students and tutorials.

- [Adding a student](#21-adding-a-student)
- [Creating a new tutorial](#22-creating-a-new-tutorial)
- [Enrolling student into a tutorial](#23-enrolling-student-into-a-tutorial)

#### **2.1 Adding a student**

#### **2.2 Creating a new tutorial**

#### **2.3 Enrolling student into a tutorial**

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

* Format errors, check [here](#11-format-errors)

</div>
{% endraw %}

### 

---

### **3. Viewing and retrieving data**

The commands in this section are used to view and retrieve records on the system, such as students, tutorials, and enrollment status.

- [Listing all students](#31-listing-all-students)  
3.2 Search

#### **3.1 Listing all students**

Command: `list`

---

### **4. Editing and updating data**

The commands in this section are used edit records on the system, such as student information, tutorial information, payment, and attendance status

- [Editing student’s details](#41-editing-a-student)  
- [Logging fees](#42-logging-fees-for-tutorial)  
- [Marking payment](#43-marking-a-students-payment)  
4.4 Marking attendance of student
4.5 Marking attendance of tutorial  
- [Unenroll a student from tutorial](#46-unenrolling-student-from-a-tutorial)
#### **4.1 Editing a student**

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
* Format errors, check [here](#11-format-errors)
</div>
{% endraw %}

#### **4.2 Logging fees for tutorial**

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

* Format errors, check [here](#11-format-errors)
</div>
{% endraw %}

#### **4.3 Marking a student’s payment**

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

* Format errors, check [here](#11-format-errors)
</div>
{% endraw %}

#### **4.6 Unenrolling student from a tutorial**

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

* Format errors, check [here](#11-format-errors)
</div>
{% endraw %}

---

### **5. Deleting data**

The commands in this section are used to delete records on the system

- [Deleting a student](#51-deleting-a-student)  
5.2 Closing a tutorial  
- [Clearing all entries](#53-clearing-all-entries)

#### **5.1 Deleting a student**

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

* Format errors, check [here](#11-format-errors)
</div>
{% endraw %}


#### **5.3 Clearing all entries**

*Deleting all tutorial and student records*

Command:  `clear`

---

### **6. Viewing help**
Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Command: `help`

---

### **7. Exiting the program**

Exits the program.

Format: `exit`

---

### **8. Saving data** 

---

### **9. Editing the data file**

---

### **10. Command summary**

---

### **11. Format Errors**

| Error Message | Most Likely Cause |
| :---- | :---- |
| *Unknown Command….* | <ul><li>Command misspelled</li><li>Command not available in the current release</li></ul> |
| *Invalid Command format…* | <ul><li>Command word is correct but the format entered is wrong</li> <ul><li>Index is missing, or is a negative number</li> <li>Prefix is missing or misspelled</li> <li>Unidentified inputs after the command word and before the first prefix</li></ul></ul>|
| *The student’s index provided is invalid…* | <ul><li>Index provided is out of range for the current displayed list</li></ul> |
| *Multiple values specified for the following single-valued field(s)...* | <ul><li>Duplicated prefix usage when it is not allowed</li></ul> |

---

### **12. FAQ**

**Q**: How do I transfer my data to another Computer?  
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

---

### **13. Known issues**

---

### **14. Archiving data files \[coming in v2.0\]**




