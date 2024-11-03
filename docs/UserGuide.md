---
  layout: default.md
    title: "User Guide"
    pageNav: 3
---

# T_Assistant User Guide

T_Assistant is a **desktop app for CS2103 tutors managing their students, groups and tasks** optimized for use via a
Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI).

If you can type fast, T_Assistant can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103-F12-2/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your T_Assistant.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar t_assistant.jar`
   command to run the application.<br>
   *A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.*<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will
   open the help window.<br>
   Some example commands you can try:

    * `list_s` : Lists all students.

    * `as sno/A0123456A sn/James Ho e/e0123456A@u.nus.edu t/TD9` : Adds a student named `James Ho`
      to T_Assistant.

    * `undo` : Undo the last command ran.

    * `clear` : Deletes all contacts.

    * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Command words are **case-insensitive**!<br>
  `add_s ...` and `ADD_S ...` will both be recognised as commands to add a new student.

* Command words come with **shorthands** <br>
  e.g. `add_s...` and `as...` will both be recognised as commands to add a new student.
* Prefixes are **case-sensitive**!<br>
  e.g. `i/INDEX` will be recognised but `I/INDEX` will not be recognised.

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `as sn/STUDENT_NAME`, `STUDENT_NAME` is a parameter which can be used as `as sn/John Doe`.

* Items in square brackets are **optional**.<br>
  e.g `sn/STUDENT_NAME [t/TAG]` can be used as `sn/John Doe t/TD9` or as `sn/John Doe`.

* Items with `…`​ after them can **be used multiple times including zero times**.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/TD9`, `t/Good at UI t/Team Lead` etc.

* Parameters can be in **any order**.<br>
  e.g. if the command specifies `sno/STUDENT_NUMBER sn/STUDENT_NAME`, `sn/STUDENT_NAME sno/STUDENT_NUMBER` is also
  acceptable.

* Extraneous parameters for commands that **do not take in parameters** (such as `help`, `list`, `exit` and `clear`)
  will be **ignored**.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* Extraneous parameters for commands that **do take in parameters** (such as `add_s`, `del_s`) will be recognised as
  *invalid* input parameters.<br>
  e.g. if the command specifies `del_t i/1 gn/CS2103-1-1`, it will be interpreted as a invalid command structure.

**Important**

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines
  as space characters surrounding line-breaks may be omitted when copied over to the application.
  </box>

--------------------------------------------------------------------------------------------------------------------

### Student Commands

--------------------------------------------------------------------------------------------------------------------

#### Listing Students: `list_s`, `ls`

Shows a list of all students in the T_Assistant.

**Format**: `list_s`

This screenshot shows the result of executing `list_s`.

![list_students](images/screenshots/list_students.png)
--------------------------------------------------------------------------------------------------------------------

#### Adding a Student: `add_s`, `as`

Adds a student to T_Assistant.

**Format**: `add_s/as sno/STUDENT_NUMBER sn/STUDENT_NAME e/EMAIL [t/TAG]...`

##### Notes

1. Student Number is the unique identifier for each student, so no 2 students can have the same student number.
2. For information on the constraints for each parameter used in this command, go to [Command Parameters](#command-parameters).

##### Usage Scenario 

###### Scenario #1: Adding John Doe to T_Assistant

This screenshot shows the result of executing `as sno/A0123456A sn/James Ho e/e0123456A@u.nus.edu t/TD9`.

![add_student](images/screenshots/add_student.png)

--------------------------------------------------------------------------------------------------------------------

#### Deleting a Student: `del_s`, `ds`

Explanation of what command does.

**Format**: `del_s sno/A0123456A`

##### Notes

1. Are there anything that the command cannot do (e.g. cannot change Student Number)
2. For information on the constraints for each parameter used in this command, go to [Command Parameters](#command-parameters).

##### Usage Scenario (for commands that can be overloaded)

Add more scenarios if necessary

###### Scenario #1

This screenshot shows the result of executing `del_s sno/A0123456A`.

--------------------------------------------------------------------------------------------------------------------

#### Editing a Student: `edit_s`, `es`

Explanation of what command does.

**Format**: `edit_s sno/A0123456A sn/James Ho Ting Kang`

##### Notes

1. Are there anything that the command cannot do (e.g. cannot change Student Number)
2. For information on the constraints for each parameter used in this command, go to [Command Parameters](#command-parameters).

##### Usage Scenario (for commands that can be overloaded)

Add more scenarios if necessary

###### Scenario #1

This screenshot shows the result of executing `edit_s sno/A0123456A sn/James Ho Ting Kang`.

--------------------------------------------------------------------------------------------------------------------

#### Adding a Student to a Group: `add_s_g`, `asg`

Explanation of what command does.

**Format**: `add_s_g sno/A0123456A gn/CS2103-F12-2`

##### Notes

1. Are there anything that the command cannot do (e.g. cannot change Student Number)
2. For information on the constraints for each parameter used in this command, go to [Command Parameters](#command-parameters).

##### Usage Scenario (for commands that can be overloaded)

Add more scenarios if necessary

###### Scenario #1

This screenshot shows the result of executing `add_s_g sno/A0123456A gn/CS2103-F12-2`.

--------------------------------------------------------------------------------------------------------------------

#### Deleting a Student from a Group: `del_s_g`, `dsg`

Explanation of what command does.

**Format**: `del_s_g sno/A0123456A`

##### Notes

1. Are there anything that the command cannot do (e.g. cannot change Student Number)
2. For information on the constraints for each parameter used in this command, go to [Command Parameters](#command-parameters).

##### Usage Scenario (for commands that can be overloaded)

Add more scenarios if necessary

###### Scenario #1

This screenshot shows the result of executing `del_s_g sno/A0123456A`.

--------------------------------------------------------------------------------------------------------------------

#### Finding Students: `find_s`, `fs`

Explanation of what command does.

**Format**: `find_s q/James Ho`

##### Notes

1. Are there anything that the command cannot do (e.g. cannot change Student Number)
2. For information on the constraints for each parameter used in this command, go to [Command Parameters](#command-parameters).

##### Usage Scenario (for commands that can be overloaded)

Add more scenarios if necessary

###### Scenario #1

This screenshot shows the result of executing `find_s q/James Ho`.

--------------------------------------------------------------------------------------------------------------------

#### Sorting Students: `sort_s`, `ss`

Explanation of what command does.

**Format**: `sort_s`

##### Notes

1. Are there anything that the command cannot do (e.g. cannot change Student Number)
2. For information on the constraints for each parameter used in this command, go to [Command Parameters](#command-parameters).

##### Usage Scenario (for commands that can be overloaded)

Add more scenarios if necessary

###### Scenario #1

This screenshot shows the result of executing `sort_s`.

--------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------

### Group Commands

--------------------------------------------------------------------------------------------------------------------

#### Listing Groups: `list_g`, `lg`

Explanation of what command does.

**Format**: `list_g`

##### Notes

1. Are there anything that the command cannot do (e.g. cannot change Student Number)
2. For information on the constraints for each parameter used in this command, go to [Command Parameters](#command-parameters).

##### Usage Scenario (for commands that can be overloaded)

Add more scenarios if necessary

###### Scenario #1

This screenshot shows the result of executing `list_g`.

--------------------------------------------------------------------------------------------------------------------

#### Adding a Group: `add_g`, `ag`

Explanation of what command does.

**Format**: `add_g gn/CS2103-F12-2`

##### Notes

1. Are there anything that the command cannot do (e.g. cannot change Student Number)
2. For information on the constraints for each parameter used in this command, go to [Command Parameters](#command-parameters).

##### Usage Scenario (for commands that can be overloaded)

Add more scenarios if necessary

###### Scenario #1

This screenshot shows the result of executing `add_g gn/CS2103-F12-2`.

--------------------------------------------------------------------------------------------------------------------

#### Deleting a Group: `del_g`, `dg`

Explanation of what command does.

**Format**: `del_g gn/CS2103-F12-2`

##### Notes

1. Are there anything that the command cannot do (e.g. cannot change Student Number)
2. For information on the constraints for each parameter used in this command, go to [Command Parameters](#command-parameters).

##### Usage Scenario (for commands that can be overloaded)

Add more scenarios if necessary

###### Scenario #1

This screenshot shows the result of executing `del_g gn/CS2103-F12-2`.

--------------------------------------------------------------------------------------------------------------------

#### Editing a Group: `edit_g`, `eg`

Explanation of what command does.

**Format**: `edit_g i/1 gn/CS2103-F12-3`

##### Notes

1. Are there anything that the command cannot do (e.g. cannot change Student Number)
2. For information on the constraints for each parameter used in this command, go to [Command Parameters](#command-parameters).

##### Usage Scenario (for commands that can be overloaded)

Add more scenarios if necessary

###### Scenario #1

This screenshot shows the result of executing `edit_g i/1 gn/CS2103-F12-3`.

--------------------------------------------------------------------------------------------------------------------

#### Finding Groups: `find_g`, `fg`

Explanation of what command does.

**Format**: `find_g q/CS2103-F12-2`

##### Notes

1. Are there anything that the command cannot do (e.g. cannot change Student Number)
2. For information on the constraints for each parameter used in this command, go to [Command Parameters](#command-parameters).

##### Usage Scenario (for commands that can be overloaded)

Add more scenarios if necessary

###### Scenario #1

This screenshot shows the result of executing `find_g q/CS2103-F12-2`.

--------------------------------------------------------------------------------------------------------------------

#### Sorting Groups: `sort_g`, `sg`

Explanation of what command does.

**Format**: `sort_g`

##### Notes

1. Are there anything that the command cannot do (e.g. cannot change Student Number)
2. For information on the constraints for each parameter used in this command, go to [Command Parameters](#command-parameters).

##### Usage Scenario (for commands that can be overloaded)

Add more scenarios if necessary

###### Scenario #1

This screenshot shows the result of executing `sort_g`.

--------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------

### Task Commands

--------------------------------------------------------------------------------------------------------------------

#### Listing Tasks: `list_t`, `lt`

Explanation of what command does.

**Format**: `list_t`

##### Notes

1. Are there anything that the command cannot do (e.g. cannot change Student Number)
2. For information on the constraints for each parameter used in this command, go to [Command Parameters](#command-parameters).

##### Usage Scenario (for commands that can be overloaded)

Add more scenarios if necessary

###### Scenario #1

This screenshot shows the result of executing `list_t`.

--------------------------------------------------------------------------------------------------------------------

#### Adding a Task to a Group: `add_t_g`, `atg`

Explanation of what command does.

**Format**: `add_t_g tn/v1.5 Release td/2024-11-07 2359 gn/CS2103-F12-2`

##### Notes

1. Are there anything that the command cannot do (e.g. cannot change Student Number)
2. For information on the constraints for each parameter used in this command, go to [Command Parameters](#command-parameters).

##### Usage Scenario (for commands that can be overloaded)

Add more scenarios if necessary

###### Scenario #1

This screenshot shows the result of executing `add_t_g tn/v1.5 Release td/2024-11-07 2359 gn/CS2103-F12-2`.

--------------------------------------------------------------------------------------------------------------------

#### Adding a Task to ALL Groups: `add_t`, `at`

Explanation of what command does.

**Format**: `add_t tn/Submit Postmortem td/2024-10-20 1800`

##### Notes

1. Are there anything that the command cannot do (e.g. cannot change Student Number)
2. For information on the constraints for each parameter used in this command, go to [Command Parameters](#command-parameters).

##### Usage Scenario (for commands that can be overloaded)

Add more scenarios if necessary

###### Scenario #1

This screenshot shows the result of executing `add_t tn/Submit Postmortem td/2024-10-20 1800`.

--------------------------------------------------------------------------------------------------------------------

#### Adding an Existing Task to a Group: `add_et_g`, `aetg`

Explanation of what command does.

**Format**: `add_et_g i/1 gn/CS2103-F12-3`

##### Notes

1. Are there anything that the command cannot do (e.g. cannot change Student Number)
2. For information on the constraints for each parameter used in this command, go to [Command Parameters](#command-parameters).

##### Usage Scenario (for commands that can be overloaded)

Add more scenarios if necessary

###### Scenario #1

This screenshot shows the result of executing `add_et_g i/1 gn/CS2103-F12-3`.

--------------------------------------------------------------------------------------------------------------------

#### Deleting a Task from ALL Groups: `del_t`, `dt`

Explanation of what command does.

**Format**: `del_t i/1`

##### Notes

1. Are there anything that the command cannot do (e.g. cannot change Student Number)
2. For information on the constraints for each parameter used in this command, go to [Command Parameters](#command-parameters).

##### Usage Scenario (for commands that can be overloaded)

Add more scenarios if necessary

###### Scenario #1

This screenshot shows the result of executing `del_t i/1`.

--------------------------------------------------------------------------------------------------------------------

#### Deleting a Task from a Group: `del_t_g`, `dtg`

Explanation of what command does.

**Format**: `del_t_g i/1 gn/CS2103-F12-2`

##### Notes

1. Are there anything that the command cannot do (e.g. cannot change Student Number)
2. For information on the constraints for each parameter used in this command, go to [Command Parameters](#command-parameters).

##### Usage Scenario (for commands that can be overloaded)

Add more scenarios if necessary

###### Scenario #1

This screenshot shows the result of executing `del_t_g i/1 gn/CS2103-F12-2`.

--------------------------------------------------------------------------------------------------------------------

#### Editing a Task for a Group: `edit_t_g`, `etg`

Explanation of what command does.

**Format**: `edit_t_g i/1 gn/CS2103-F12-3 tn/v1.4 Release`

##### Notes

1. Are there anything that the command cannot do (e.g. cannot change Student Number)
2. For information on the constraints for each parameter used in this command, go to [Command Parameters](#command-parameters).

##### Usage Scenario (for commands that can be overloaded)

Add more scenarios if necessary

###### Scenario #1

This screenshot shows the result of executing `edit_t_g i/1 gn/CS2103-F12-3 tn/v1.4 Release`.

--------------------------------------------------------------------------------------------------------------------

#### Editing a Task for ALL Groups: `edit_t`, `et`

Explanation of what command does.

**Format**: `edit_t i/1 td/2024-11-20 1200`

##### Notes

1. Are there anything that the command cannot do (e.g. cannot change Student Number)
2. For information on the constraints for each parameter used in this command, go to [Command Parameters](#command-parameters).

##### Usage Scenario (for commands that can be overloaded)

Add more scenarios if necessary

###### Scenario #1

This screenshot shows the result of executing `edit_t i/1 td/2024-11-20 1200`.

--------------------------------------------------------------------------------------------------------------------

#### Mark a Task for a Group: `mark_t`, `mt`

Explanation of what command does.

**Format**: `mark_t gn/CS2103-F12-2 i/2`

##### Notes

1. Are there anything that the command cannot do (e.g. cannot change Student Number)
2. For information on the constraints for each parameter used in this command, go to [Command Parameters](#command-parameters).

##### Usage Scenario (for commands that can be overloaded)

Add more scenarios if necessary

###### Scenario #1

This screenshot shows the result of executing `mark_t gn/CS2103-F12-2 i/2`.

--------------------------------------------------------------------------------------------------------------------

#### Finding Tasks: `find_t`, `ft`

Explanation of what command does.

**Format**: `find_t q/v1.3 Release`

##### Notes

1. Are there anything that the command cannot do (e.g. cannot change Student Number)
2. For information on the constraints for each parameter used in this command, go to [Command Parameters](#command-parameters).

##### Usage Scenario (for commands that can be overloaded)

Add more scenarios if necessary

###### Scenario #1

This screenshot shows the result of executing `find_t q/v1.3 Release`.

--------------------------------------------------------------------------------------------------------------------

#### Sorting Tasks: `sort_t`, `st`

Explanation of what command does.

**Format**: `sort_t`

##### Notes

1. Are there anything that the command cannot do (e.g. cannot change Student Number)
2. For information on the constraints for each parameter used in this command, go to [Command Parameters](#command-parameters).

##### Usage Scenario (for commands that can be overloaded)

Add more scenarios if necessary

###### Scenario #1

This screenshot shows the result of executing `sort_t`.

--------------------------------------------------------------------------------------------------------------------

### Miscellaneous Commands

--------------------------------------------------------------------------------------------------------------------

#### Undoing change: `undo`

Undoes the previous command ran.

Format: `undo`

#### Redoing change: `redo`

Redoes the previous command ran.

Format: `redo`

#### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/screenshots/helpMessage.png)

Format: `help`

#### Clearing all entries : `clear`

Clears all entries from the assistant.

Format: `clear`

#### Exiting the program : `exit`

Exits the program.

Format: `exit`

--------------------------------------------------------------------------------------------------------------------

## Data Handling

--------------------------------------------------------------------------------------------------------------------

### Saving the data

T_Assistant data are saved in the hard disk automatically after any command that changes the data. There is no need to
save manually.

### Editing the data file

T_Assistant data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are
welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, T_Assistant will discard all data and start with an empty
data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br><br>
Furthermore, certain edits can cause the T_Assistant to behave in unexpected ways (e.g., if a value entered is outside
the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains
the data of your previous T_Assistant home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only
   the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the
   application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut
   `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to
   manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Glossary

| Key Terms      | Definition                                             |
|----------------|--------------------------------------------------------|
| Mainstream OS  | Operating Systems (i.e. Windows, Linux, MacOS          |
| Prefix         | Keyword used in commands to specify the parameter type |
| Student Number | Unique identifier for a student                        |

### Command Parameters

This section will inform you about what parameters are used in T_Assistant and their restrictions :)

| Parameter                | Constraints                                                                                    | Correct Input | Incorrect Input         |
|--------------------------|------------------------------------------------------------------------------------------------|---------------|-------------------------|
| Student Number<br>`sno/` | Student Number must start with `A0` <br>Followed by 6 numerical digits <br>End with any letter | `A0123456B`   | `A1234567`, `A1234567A` |
|                          |                                                                                                |               |                         |
|                          |                                                                                                |               |                         |
|                          |                                                                                                |               |                         |
|                          |                                                                                                |               |                         |
|                          |                                                                                                |               |                         |
|                          |                                                                                                |               |                         |
|                          |                                                                                                |               |                         |
|                          |                                                                                                |               |                         |

--------------------------------------------------------------------------------------------------------------------

## Command Summary

### Student

| Action                        | Format, Examples                                                                                                                     |
|-------------------------------|--------------------------------------------------------------------------------------------------------------------------------------|
| **List Students**             | `list_s/ls`                                                                                                                          |
| **Add Student**               | `add_s/as sno/STUDENT_NUMBER sn/STUDENT_NAME e/EMAIL [t/TAG]...`<br>e.g., `as sno/A0123456A sn/James Ho e/e0123456A@u.nus.edu t/TD9` |
| **Delete Student**            | `del_s/ds sno/STUDENT_NUMBER`<br>e.g., `ds sno/A0123456A`                                                                            |
| **Edit Student**              | `edit_s/es sno/STUDENT_NUMBER [sn/STUDENT_NAME] [e/EMAIL] [t/TAG]`<br>e.g., `es sno/A0123456A sn/James Ho Ting Kang`                 |
| **Add Student to Group**      | `add_s_g/asg sno/STUDENT_NUMBER gn/GROUP_NAME`<br>e.g., `asg sno/A0123456A gn/CS2103-F12-2`                                          |
| **Delete Student From Group** | `del_s_g/dsg sno/STUDENT_NUMBER`<br>e.g., `dsg sno/A0123456A`                                                                        |
| **Find Student**              | `find_s/fs q/QUERY [q/QUERY]...`<br>e.g., `fs q/James`                                                                               |
| **Sort Students**             | `sort_s/ss`                                                                                                                          |

### Group

| Action           | Format, Examples                                                    |
|------------------|---------------------------------------------------------------------|
| **List Groups**  | `list_g/lg`                                                         |
| **Add Group**    | `add_g/ag gn/GROUP_NAME`<br>e.g., `ag gn/CS2103-F12-2`              |
| **Delete Group** | `del_g/dg gn/GROUP_NAME`<br>e.g., `dg gn/CS2103-F12-2`              |
| **Edit Group**   | `edit_g/eg i/INDEX gn/GROUP_NAME`<br>e.g., `eg i/1 gn/CS2103-F12-3` |
| **Find Group**   | `find_g/fg q/QUERY [q/QUERY]...`<br>e.g., `fg q/CS2103-F12-2`       |
| **Sort Groups**  | `sort_g/sg`                                                         |

### Task

| Action                          | Format, Examples                                                                                                                                                 |
|---------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **List Task**                   | `list_t/lt [gn/GROUP_NAME]`<br>e.g., `lt`, `lt gn/CS2103-F12-2`                                                                                                  |
| **Add Task to Group**           | `add_t_g/atg tn/TASK_NAME td/TASK_DEADLINE (YYYY-MM-DD HHmm) gn/GROUP_NAME [gn/GROUP_NAME]...`<br>e.g., `atg tn/v1.5 Release td/2024-11-07 2359 gn/CS2103-F12-2` |
| **Add Task to All Groups**      | `add_t/at tn/TASK_NAME td/TASK_DEADLINE (YYYY-MM-DD HHmm)`<br>e.g., `at tn/Submit Postmortem td/2024-10-20 1800`                                                 |
| **Add Existing Task to Group**  | `add_et_g/aetg i/INDEX gn/GROUP_NAME [gn/GROUP_NAME]...`<br>e.g., `aetg i/1 gn/CS2103-F12-3`                                                                     |
| **Delete Task from All Groups** | `del_t/dt i/INDEX`<br>e.g., `dt i/1`                                                                                                                             |
| **Delete Task from Group**      | `del_t_g/dtg i/INDEX gn/GROUP_NAME`<br>e.g., `dtg i/1 gn/CS2103-F12-2`                                                                                           |
| **Edit Task for Group**         | `edit_t_g/etg i/INDEX gn/GROUP_NAME [tn/TASK_NAME] [td/TASK_DEADLINE (YYYY-MM-DD HHmm)` <br>e.g., `etg i/1 gn/CS2103-F12-3 tn/v1.4 Release`                      |
| **Edit Task for All Groups**    | `edit_t/et i/INDEX [tn/TASK_NAME] [td/TASK_DEADLINE (YYYY-MM-DD HHmm)`<br>e.g., `et i/1 td/2024-11-20 1200`                                                      |
| **Mark Task**                   | `mark_t/mt gn/GROUP_NAME i/INDEX`<br>e.g., `mt gn/CS2103-F12-2 i/2`                                                                                              |
| **Find Task**                   | `find_t/ft q/QUERY [q/QUERY]...`<br>e.g., `ft q/v1.3 Release`                                                                                                    |
| **Sort Tasks**                  | `sort_t/st`                                                                                                                                                      |

### Misc.

| Action    | Format, Examples |
|-----------|------------------|
| **Help**  | `help`           |
| **Clear** | `clear`          |
| **Undo**  | `undo`           |
| **Redo**  | `redo`           |
| **Exit**  | `exit`           |
