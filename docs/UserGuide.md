---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# T_Assistant User Guide

T_Assistant is a **desktop app for CS2103 tutors managing their students, groups and tasks** optimized for use via a Command
Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI).

If you can type fast, T_Assistant can get your tutor management tasks done faster than traditional GUI apps.

## Navigation

For easy navigation, you can use the navigation breadcrumb on the right side of the website.

If you are on the PDF, use the table of contents below to navigate the site.

<!-- * Table of Contents -->
<page-nav-print />

---

<div style="page-break-after: always;"></div>

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

2. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103-F12-2/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your T_Assistant.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar t_assistant.jar`
   command to run the application.<br>
   _A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data._<br>
   <img src="images/Ui.png" width="600">

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will
   open the help window.<br>
   Some example commands you can try:

    - `list_s` : Lists all students.

    - `as sno/A0123456A sn/James Ho e/e0123456A@u.nus.edu t/TD9` : Adds a student named `James Ho`
      to T_Assistant.

    - `undo` : Undo the last command ran.

    - `clear` : Deletes all data.

    - `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

---

<div style="page-break-after: always;"></div>

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

- Command words are **case-insensitive**!<br>
  `add_s ...` and `ADD_S ...` will both be recognised as commands to add a new student.

- Command words come with **shorthands** <br>
  e.g. `add_s...` and `as...` will both be recognised as commands to add a new student.

- Prefixes are **case-insensitive**!<br>
  e.g. `i/INDEX` and `I/INDEX` will be recognised.

- Words in `UPPER_CASE` are the parameters to be **supplied by the user**.<br>
  e.g. in `as sn/STUDENT_NAME`, `STUDENT_NAME` is a parameter which can be used as `as sn/John Doe`.

- Items in square brackets are **optional**.<br>
  e.g `sn/STUDENT_NAME [t/TAG]` can be used as `sn/John Doe t/TD9` or as `sn/John Doe`.

- Items with `…`​ after them can **be used multiple times including zero times**.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/TD9`, `t/GoodatUI t/TeamLead` etc.

- Parameters can be in **any order**.<br>
  e.g. if the command specifies `sno/STUDENT_NUMBER sn/STUDENT_NAME`, `sn/STUDENT_NAME sno/STUDENT_NUMBER` is also
  acceptable.

- The following commands (i.e. `help`, `exit` and `clear`) will **ignore** extraneous parameters.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

- The following commands (i.e. `list_s`, `list_g` and `sort` commands) will **throw an error** for extraneous parameters.

- Using extraneous parameters/repeated parameters, unlike stated, in other commands might **result in an error**!

<box type="warning" seamless>

**Caution:**
- Prefixes are detected if a space is in front of them, i.e. `gn/` will be detected as a prefix if entered like this ` (space)gn/`.
- If your input involves a parameter used in the command, consider using a backslash like this `gn\`.
</box>

**Important**

- If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines
  as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

<box type="warning">

When running commands that use `i/INDEX`, the index used references the unfiltered lists so always run the relevant `list` commands first!

e.g. `add_t tn/Merry Christmas td/2024-12-24 0000` will return a filtered view of the task. 

However, if you wish to edit the new task, you must run `list_t` first to edit it globally or `list_t gn/GROUP_NAME` if you wish to edit it for a group.

</box>

---

### Student Commands

---

#### Listing Students: `list_s`, `ls`

Shows a list of all students in the T_Assistant.

**Format**: `list_s`

This screenshot shows the result of executing `list_s`.

<img src="images/screenshots/list_s.png" width="600">

---

#### Adding a Student: `add_s`, `as`

Adds a student to T_Assistant.

**Format**: `add_s sno/STUDENT_NUMBER sn/STUDENT_NAME e/EMAIL [t/TAG]...`

##### Notes

1. `Student Number` and `Email` are the unique identifiers for each student, so no two students can have the same student number or email.
2. `Student Number` provided by the user will be capitalised by the system.
3. `Tags` are case-insensitive, so `T1` and `t1` will be recognised as the same tags. T_Assistant will add the first
   instance of the repeated tag.
4. For information on the constraints for each parameter used in this command, go
   to [Command Parameters](#command-parameters).

##### Usage Scenario

###### Scenario #1: Adding `James Ho` to T_Assistant

1. You can begin this command on any panel.
2. Type and execute: `add_s sno/A0123456A sn/James Ho e/e0123456A@u.nus.edu t/TD9`

This screenshot shows the result of executing `add_s sno/A0123456A sn/James Ho e/e0123456A@u.nus.edu t/TD9`.

<img src="images/screenshots/add_s.png" width="600">

---

#### Deleting a Student: `del_s`, `ds`

Deletes a student from T_Assistant.

**Format**: `del_s sno/STUDENT_NUMBER`

##### Notes

1. A student with the `Student Number` must exist in T_Assistant.
2. For information on the constraints for each parameter used in this command, go
   to [Command Parameters](#command-parameters).

##### Usage Scenario

###### Scenario #1: Deleting `James Ho` from T_Assistant

1. You can begin this command on any panel.
2. Type and execute: `del_s sno/A0123456A`

This screenshot shows the result of executing `del_s sno/A0123456A`.

<img src="images/screenshots/del_s.png" width="600">

---

#### Editing a Student: `edit_s`, `es`

Edits the details of a student.

**Format**: `edit_s i/INDEX [sn/NAME] [e/EMAIL] [t/TAG]...`

##### Notes

1. `edit_s` edits the student identified by the index. It only accepts a valid index based on the list returned when `list_s` is called.
2. Since `Student Number` is the unique identifier for each student, `Student Number` can't be edited.
3. `Email` can be edited, but it must not exist in T_Assistant.
4. At least 1 optional parameter must be provided, else an error will be thrown.
5. Editing of tags is not cumulative, i.e. tags will be replaced by the new set of tags.
6. Tags are removed by entering `t/` without specifying any tags after it.
7. For information on the constraints for each parameter used in this command, go
   to [Command Parameters](#command-parameters).

##### Usage Scenario

###### Scenario #1: Editing `Bernice Yu`'s Name

1. You can begin this command on any panel.
2. Type and execute: `edit_s i/2 sn/Bernice Yu Ting Kang`

This screenshot shows the result of executing `edit_s i/2 sn/Bernice Yu Ting Kang`.

<img src="images/screenshots/edit_s.png" width="600">

---

#### Adding a Student to a Group: `add_s_g`, `asg`

Adds student(s) to a group.

**Format**: `add_s_g sno/STUDENT_NUMBER [sno/STUDENT_NUMBER]... gn/GROUP_NAME`

##### Notes

1. The group with the `Group Name` and student(s) with the `Student Number`(s) must both exist in T_Assistant.
2. The max size for a `Group` is 5 `Students`.
3. Returns a filtered view of the group the student was added into.
4. For information on the constraints for each parameter used in this command, go
   to [Command Parameters](#command-parameters).

##### Usage Scenario

###### Scenario #1: Adding `Roy Balakrishnan` to `CS2103-F11-1`

1. You can begin this command on any panel.
2. Type and execute: `add_s_g sno/A0122764H gn/CS2103-F11-1`

This screenshot shows the result of executing `add_s_g sno/A0122764H gn/CS2103-F11-1`.

<img src="images/screenshots/add_s_g.png" width="600">

---

#### Deleting a Student from a Group: `del_s_g`, `dsg`

Deletes a student from its assigned group.

**Format**: `del_s_g sno/STUDENT_NUMBER`

##### Notes

1. This command only removes a student from a group, not from T_Assistant.
2. The student must exist in T_Assistant and be in a group.
3. Since a student can only be in one group, it will automatically remove the student from the group it is in.
4. Returns a filtered view of the group the student was in.
5. For information on the constraints for each parameter used in this command, go
   to [Command Parameters](#command-parameters).

##### Usage Scenario

###### Scenario #1: Deleting `Alex Yeoh` from `CS2103-F12-1`

1. You can begin this command on any panel.
2. Type and execute: `del_s_g sno/A0737935G`

This screenshot shows the result of executing `del_s_g sno/A0737935G`.

<img src="images/screenshots/del_s_g.png" width="600">

---

#### Finding Students: `find_s`, `fs`

Searches T_Assistant for students with fields that match the search query.

**Format**: `find_s q/QUERY [q/QUERY]...`

##### Notes

1. This command is case-insensitive.
2. The command will match substrings. You may search for part of a student's name such as `Alex Y` to find `Alex Yeoh`.
3. A blank query i.e. `fs q/` will return all students.
4. This command functions on an OR logic. It will return all students that have fields that match any of the queries.
5. Searches the following fields that a student has that matches the query:
    - Student name
    - Student number
    - Email
    - Group name
<box type="info" seamless>
If you wish to filter for students with no groups, use the following command: `find_s q/!nogroup`
  
> `!nogroup` is a special query that searches for students with no groups, it is also case-insensitive.

Take note that if any other student with a group happens to have a field that matches the special keyword, they will also appear in the results.
</box>

6. For information on the constraints for each parameter used in this command, go
   to [Command Parameters](#command-parameters).

##### Usage Scenario

###### Scenario #1: Find students with the following queries - `TD7` and `Oliveiro`

1. You can begin this command on any panel.
2. Type and execute: `find_s q/TD7 q/Olveiro`

This screenshot shows the result of executing `find_s q/TD7 q/Olveiro`.

<img src="images/screenshots/find_s.png" width="600">
<br>
<br>

###### Scenario #2: Find students with no groups

1. You can begin this command on any panel.
2. Type and execute: `find_s q/!nogroup`

This screenshot shows the result of executing `find_s q/!nogroup`.

<img src="images/screenshots/find_s_nogroup.png" width="600">

---

#### Sorting Students: `sort_s`, `ss`

Sorts the list of students in ASCII order.

**Format**: `sort_s`

<box type="warning" seamless>

**Caution:**
The student list is sorted in ASCII order, not in alphabetical order as the message will say after successfully executing `sort_s`.
</box>

This screenshot shows the result of executing `sort_s`.

<img src="images/screenshots/sort_s.png" width="600">

---

### Group Commands

---

#### Listing Groups: `list_g`, `lg`

Shows a list of all groups in the T_Assistant.

**Format**: `list_g`

This screenshot shows the result of executing `list_g`.

<img src="images/screenshots/list_g.png" width="600">

---

#### Adding a Group: `add_g`, `ag`

Adds a group to the T_Assistant。

**Format**: `add_g/ag gn/GROUP_NAME [gn/GROUP_NAME]...`

##### Notes

1. `Group Name` is the unique identifier for each group, so no two groups can have the same group name.
2. Leading 0s in `Group Name` will be truncated, e.g. `CS2103-F00012-002` will be read as `CS2103-F12-2`.
4. For information on the constraints for each parameter used in this command, go
   to [Command Parameters](#command-parameters).

##### Usage Scenario

###### Scenario #1: Adding group `CS2103T-T10-1`

1. You can begin this command on any panel.
2. Type and execute: `add_g gn/CS2103T-T10-1`

This screenshot shows the result of executing `add_g gn/CS2103T-T10-1`.

<img src="images/screenshots/add_g.png" width="600">

---

#### Deleting a Group: `del_g`, `dg`

Deletes the specified group from T_Assistant.

**Format**: `del_g gn/GROUP_NAME`

##### Notes

1. This command is case-insensitive.
   > `del_g gn/CS2103-F12-2` and `del_g gn/cs2103-f12-2` will delete the same group.
2. This command will also remove students from the deleted group.
3. For information on the constraints for each parameter used in this command, go
   to [Command Parameters](#command-parameters).

##### Usage Scenario

###### Scenario #1: Deleting group `CS2103-F12-2`

1. You can begin this command on any panel.
2. Type and execute: `del_g gn/CS2103-F12-2`

This screenshot shows the result of executing `del_g gn/CS2103-F12-2`.

<img src="images/screenshots/del_g.png" width="600">

---

#### Editing a Group: `edit_g`, `eg`

Edits the specified group in T_Assistant.

**Format**: `edit_g i/INDEX gn/GROUP_NAME`

##### Notes

1. `edit_g` edits the group identified by the index. It only accepts a valid index
   based on the list returned when `list_g` is called.
2. For information on the constraints for each parameter used in this command, go
   to [Command Parameters](#command-parameters).

##### Usage Scenario

###### Scenario #1: Editing `CS2103-F11-3` to `CS2103T-F11-3`

1. Execute `list_g` to see the list of groups.
2. Type and execute: `edit_g i/3 gn/CS2103T-F11-3`

This screenshot shows the result of executing `edit_g i/3 gn/CS2103T-F11-3`.

<img src="images/screenshots/edit_g.png" width="600">

---

#### Finding Groups: `find_g`, `fg`

Searches T_Assistant for groups with fields that match the search query.

**Format**: `find_g q/QUERY [q/QUERY]...`

##### Notes

1. This command is case-insensitive.
2. The command will match substrings. You may search for part of a group's name such as `F12` to find `CS2103T-F12-10`.
3. A blank query i.e. `fg q/` will return all groups.
4. This command functions on an OR logic. It will return all groups that have fields that match any of the queries.
5. Searches the following field that a group has that matches the query:
    - Group name
6. For information on the constraints for each parameter used in this command, go
   to [Command Parameters](#command-parameters).

##### Usage Scenario

###### Scenario #1: Finding groups with the following query - `F11`

1. You can begin this command on any panel.
2. Type and execute: `find_g q/F11`

This screenshot shows the result of executing `find_g q/F11`.

<img src="images/screenshots/find_g.png" width="600">

---

#### Sorting Groups: `sort_g`, `sg`

Sorts the list of groups in ASCII order.

**Format**: `sort_g`

<box type="warning" seamless>

**Caution:**
The group list is sorted in ASCII order, not in alphabetical order as the message will say after successfully executing `sort_g`.
</box>

This screenshot shows the result of executing `sort_g`.

<img src="images/screenshots/sort_g.png" width="600">

---

### Task Commands

---

#### Listing Tasks: `list_t`, `lt`

Shows a list of all tasks in the T_Assistant.

**Format**: `list_t [gn/GROUP_NAME]`

##### Notes

1. If there's a `gn/Group Name` supplied, all the tasks in that group will be listed instead.
2. For information on the constraints for each parameter used in this command, go
   to [Command Parameters](#command-parameters).

##### Usage Scenario

###### Scenario #1: Listing all tasks in all groups

1. You can begin this command on any panel.
2. Type and execute: `list_t`

This screenshot shows the result of executing `list_t`.

<img src="images/screenshots/list_t.png" width="600">
<br>
<br>

###### Scenario #2: Listing all tasks in `CS2103-F11-1`

1. You can begin this command on any panel.
2. Type and execute: `list_t gn/CS2103-F11-1`

This screenshot shows the result of executing `list_t gn/CS2103-F11-1`.

<img src="images/screenshots/list_t_group.png" width="600">

---

#### Adding a Task to a Group: `add_t_g`, `atg`

Adds a task to a group or multiple groups.

**Format**: `add_t_g tn/TASK_NAME td/TASK_DEADLINE (YYYY-MM-DD HHmm) gn/GROUP_NAME [gn/GROUP_NAME]...`

##### Notes

1. This command allows only the addition of a single task to one or multiple groups at the same time.
2. The group name inputs are case-insensitive. `cs2103-f11-1` will yield the same result as `CS2103-F11-1`.
3. This command is relatively flexible in terms of the input parameters. You can input duplicate group names.
   The application simply ignores the duplicates and adds the task to the desired group.
4. This command checks for the existence of the task, hence you cannot input a task which already exists. A task is
   equal to another task when it has the same task name and deadline as it.
5. Returns a filtered view of the task added.
6. For information on the constraints for each parameter used in this command, go
   to [Command Parameters](#command-parameters).

##### Usage Scenario

###### Scenario #1: Adding a task with the name of `Complete task 4` and deadline of `2024-11-15 1700` to groups `CS2103-F12-1` and `CS2103-F11-1`

This is the default scenario where the task does not already exist and both groups exist.

1. You can begin this command on any panel.
2. Type `add_t_g tn/Complete task 4 td/2024-11-15 1700 gn/CS2103-F12-1 gn/CS2103-F11-1`.

This screenshot shows the result of executing `add_t_g tn/Complete task 4 td/2024-11-15 1700 gn/CS2103-F12-1 gn/CS2103-F11-1`.

<img src="images/screenshots/add_t_g_1.png" width="600">

---

#### Adding a Task to ALL Groups: `add_t`, `at`

Adds a task to all groups.

**Format**: `add_t tn/TASK_NAME td/TASK_DEADLINE (YYYY-MM-DD HHmm)`

##### Notes

1. If the task specified already exists in some groups, it will add the other groups in.
2. Returns a filtered view of the task added.
3. For information on the constraints for each parameter used in this command, go
   to [Command Parameters](#command-parameters).

##### Usage Scenario

###### Scenario #1: Executing `add_t tn/Complete task 6 td/2024-10-20 1800`

1. You can begin this command on any panel.
2. Type and execute `add_t tn/Complete task 6 td/2024-10-20 1800`.

This screenshot shows the result of executing `add_t tn/Complete task 6 td/2024-10-20 1800`.

<img src="images/screenshots/add_t.png" width="600">

---

#### Adding an Existing Task to a Group: `add_et_g`, `aetg`

Adds an existing task to the groups specified.

**Format**: `add_et_g i/INDEX gn/GROUP_NAME [gn/GROUP_NAME]...`

##### Notes

1. This command is case-insensitive.
   > `gn/CS2103-F12-2` and `gn/cs2103-f12-2` will be recognised as the same group.
2. `add_et_g` adds an existing task identified by the index. It only accepts a valid index
   based on the list returned when `list_t` is called.
3. You can add an existing task to multiple groups.
4. This command is relatively flexible in terms of the input parameters. You can input duplicate group names or group names that don't exist.
    1. The application simply ignores the duplicates and adds the task to the desired group.
5. Returns a filtered view of the task added to the group(s).
6. For information on the constraints for each parameter used in this command, go
   to [Command Parameters](#command-parameters).

##### Usage Scenario

###### Scenario #1: Adding task in index `1` to `CS2103T-T10-1`

1. Type and execute: `list_t` to see the list of tasks.
2. After finding the task you wish to add, remember its index number (task with index 1 in this example).
3. You may wish to execute `list_g` to check on the names of groups you wish to add the task to.
4. Type and execute: `add_et_g i/1 gn/CS2103T-T10-1`

This screenshot shows the result of executing `add_et_g i/1 gn/CS2103T-T10-1`.

<img src="images/screenshots/add_et_g_1.png" width="600">

---

#### Deleting a Task from a Group: `del_t_g`, `dtg`

Deletes a task from a group.

**Format**: `del_t_g i/INDEX gn/GROUP_NAME`

##### Notes

1. The index must be valid and should be the index of target task in the group's task list.
2. `del_t_g` deletes a task identified by the index. It only accepts a valid index
   based on the list returned when `list_t gn/GROUP_NAME` is called, where `gn/GROUP_NAME` is the group of interest.
3. This command is case-insensitive.
   > `gn/CS2103-F12-2` and `gn/cs2103-f12-2` will be recognised as the same group.
4. Returns a filtered view of the group the task is deleted from.
5. For information on the constraints for each parameter used in this command, go
   to [Command Parameters](#command-parameters).

##### Usage Scenario

###### Scenario #1: Deleting task with index `1` from `CS2103-F11-1`

1. Type and execute: `list_t gn/CS2103-F11-1` to see the list of tasks in `CS2103-F11-1`.
2. After finding the task you wish to delete from this group, remember its index number (task with index 1 in this example).
3. Type and execute: `del_t_g i/1 gn/CS2103-F11-1`

This screenshot shows the result of executing `del_t_g i/1 gn/CS2103-F11-1`.

<img src="images/screenshots/del_t_g.png" width="600">

---

#### Deleting a Task from ALL Groups: `del_t`, `dt`

Delete a task from all groups that contain it.

**Format**: `del_t i/INDEX`

##### Notes

1. The index must be valid and should be the index of task list.
2. `del_t` deletes a task identified by the index. It only accepts a valid index
      based on the list returned when `list_t` is called.
3. For information on the constraints for each parameter used in this command, go
   to [Command Parameters](#command-parameters).

##### Usage Scenario

###### Scenario #1: Deleting task in index `2`

1. Type and execute: `list_t` to see the list of tasks.
2. Type and execute: `del_t i/2`

This screenshot shows the result of executing `del_t i/2`.

<img src="images/screenshots/del_t.png" width="600">

---

#### Editing a Task for a Group: `edit_t_g`, `etg`

Edits a task from a group.

**Format**: `edit_t_g i/INDEX gn/GROUP_NAME [tn/TASK_NAME] [td/TASK_DEADLINE (YYYY-MM-DD HHmm)]`

##### Notes

1. The index must be valid and should be the index of target task in the group's task list.
2. `edit_t_g` edits a task identified by the index. It only accepts a valid index
   based on the list returned when `list_t gn/GROUP_NAME` is called, where `gn/GROUP_NAME` is the group of interest.
3. `Group Name` must exist in the T_Assistant.
4. At least 1 optional parameter must be provided, else an error will be thrown.
5. This command is case-insensitive.
   > `gn/CS2103-F12-2` and `gn/cs2103-f12-2` will be recognised as the same group.
6. Returns a filtered view of the group the task is edited in.
7. For information on the constraints for each parameter used in this command, go
   to [Command Parameters](#command-parameters).

##### Usage Scenario

###### Scenario #1: Editing a task with index `1` in `CS2103-F12-1`

1. Type and execute: `list_t gn/CS2103-F12-1` to see the list of tasks in `CS2103-F12-1`.
2. After finding the task you wish to edit, type and execute: `edit_t_g i/1 gn/CS2103-F12-1 tn/Complete task 7`

This screenshot shows the result of executing `edit_t_g i/1 gn/CS2103-F12-1 tn/Complete task 7`.

<img src="images/screenshots/edit_t_g.png" width="600">

---

#### Editing a Task for ALL Groups: `edit_t`, `et`

Edits a task from all groups that contain it.

**Format**: `edit_t i/INDEX [tn/TASK_NAME] [td/TASK_DEADLINE (YYYY-MM-DD HHmm)]`

##### Notes

1. The index must be valid and should be the index of task list.
2. `edit_t` edits a task identified by the index. It only accepts a valid index
   based on the list returned when `list_t`.
3. At least 1 optional parameter must be provided, else an error will be thrown.
4. There is a known issue for this command, go to [Known Issues](#known-issues) for more information.
5. For information on the constraints for each parameter used in this command, go
   to [Command Parameters](#command-parameters).

##### Usage Scenario

###### Scenario #1: Editing task in index `2`

1. Type and execute: `list_t` to see the task list.
2. After finding the task you wish to edit, type and execute: `edit_t i/2 tn/Complete task 8`

This screenshot shows the result of executing `edit_t i/2 tn/Complete task 8`.

<img src="images/screenshots/edit_t.png" width="600">

---

#### Mark a Task for a Group: `mark_t`, `mt`

Marks a task as done or undone.

**Format**: `mark_t gn/GROUP_NAME i/INDEX`

##### Notes

1. The index must be valid and should be the index of target task in the group's task list.
2. `mark_t` marks a task identified by the index. It only accepts a valid index
   based on the list returned when `list_t gn/GROUP_NAME` is called, where `gn/GROUP_NAME` is the group of interest.
3. If a task is complete it will be marked as pending/overdue, and vice versa.
4. Returns a filtered view of the group the task is marked at.
5. For information on the constraints for each parameter used in this command, go
   to [Command Parameters](#command-parameters).

##### Usage Scenario

###### Scenario #1: Marking an undone task

1. Type and execute: `mark_t gn/CS2103-F12-1 i/2`
2. Executing `mark_t` will mark the task as done.

3. This screenshot shows the result of executing `mark_t gn/CS2103-F12-1 i/2`.

<img src="images/screenshots/mark_t_1.png" width="600">
<br>
<br>

###### Scenario #2: Marking a done task

1. Type and execute: `mark_t gn/CS2103-F12-1 i/2`
2. Executing `mark_t` will mark the task as undone.

3. This screenshot shows the result of executing `mark_t gn/CS2103-F12-1 i/2`.

<img src="images/screenshots/mark_t_2.png" width="600">

---

#### Finding Tasks: `find_t`, `ft`

Searches T_Assistant for tasks with fields that match the search query.

**Format**: `find_t q/QUERY [q/QUERY]...`

##### Notes

1. This command is case-insensitive.
2. The command will match substrings. You may search for part of a task's name such as `tp` to find `tP v1.6 Release`.
3. A blank query i.e. `ft q/` will return all tasks.
4. This command functions on an OR logic. It will return all tasks that have fields that match any of the queries.
5. Searches the following field that a group has that matches the query:
    - Task name
6. For information on the constraints for each parameter used in this command, go
   to [Command Parameters](#command-parameters).

##### Usage Scenario

###### Scenario #1: Finding tasks with the following query: `4`

This screenshot shows the result of executing `find_t q/4`.

<img src="images/screenshots/find_t.png" width="600">

---

#### Sorting Tasks: `sort_t`, `st`

Sorts the task list by deadline from the earliest to the latest date.

**Format**: `sort_t`

This screenshot shows the result of executing `sort_t`.

<img src="images/screenshots/sort_t.png" width="600">

---

### Miscellaneous Commands

---

#### Undoing change: `undo`

Undoes the previous command ran.

**Format**: `undo`

##### Notes

1. If it is your first time opening the application, you will be unable to undo because there is no data to undo. You can only undo
   after you have made changes to the application.
2. You can only undo the commands which make changes to the application (add, delete, sort, mark, clear, edit commands),
   and not the commands which do not make any changes (find, list, help, exit commands).
3. For every change you make within the application, it will be added to the version history of the application.
4. The application stores up to 100 version histories. Hence, you will only be able to undo up to 100 of your most recent
   versions.
5. After running the command you will be brought to the default panel of the application (the student list panel).

#### Redoing change: `redo`

Redoes the previously undone command.

Format: `redo`

##### Notes

1. If it is your first time opening the application, you will be unable to redo because there is no data to redo. You can only
   redo the application after at least one undo has been executed.
2. You can only redo the commands which make changes to the application (add, delete, sort, mark, clear, edit commands),
   and not the commands which do not make any changes (find, list, help, exit commands).
3. After running the command you will be brought to the default panel of the application (the student list panel).

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

<div style="page-break-after: always;"></div>

---

## Data Handling

---

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

Changes done directly to the data file will **NOT** be registered by the application, i.e. `undo`/`redo` will not register these changes.
</box>

---

<div style="page-break-after: always;"></div>

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains
the data of your previous T_Assistant home folder and also copy over `versionHistory.json` if you wish to keep it.

---

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only
   the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the
   application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut
   `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to
   manually restore the minimized Help Window.
3. The application will sometimes open to a blank screen with no information displayed, simply run any commands and
   the application should function as per normal.
4. When **running edit task for all groups `et`**, if a group has completed the task, their status will be reset to pending or overdue. The remedy is to run `etg` such that you create a new task for the groups with completed status before editing the task.

---

## Glossary

| Key Terms      | Definition                                                   |
| -------------- | ------------------------------------------------------------ |
| Mainstream OS  | Operating Systems (i.e. Windows, Linux, MacOS                |
| JAR            | Executable file containing Java classes and other resources. |
| Prefix         | Keyword used in commands to specify the parameter type       |
| Student Number | Unique identifier for a student                              |

### Command Parameters

This section will inform you about what parameters are used in T_Assistant and their restrictions :)

<box type="info">
All parameters are case-insensitive when used for comparison unless stated otherwise.
</box>

| Parameter                | Constraints                                                                                                                                                                                                                                                         | <span style="color:green">Correct</span>      | <span style="color:red">Incorrect</span>         |
|--------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------|--------------------------------------------------|
| `sno/`<br>Student Number | Start with `A0` <br>Followed by 6 numerical digits <br>End with any letter                                                                                                                                                                                          | `A0123456B`                                   | `A1234567` <br>`A1234567A`                       |
| `sn/`<br>Student Name    | Only contain alphanumeric characters, a variety of latin characters and spaces, and it should not be blank                                                                                                                                                          | `John Doe`                                    | `J0hn Doe$$`                                     |
| `e/`<br>Email            | Format: `local-part@domain-part`<br>`local-part` should contain only alphanumeric characters and the following special characters `_`, `.` <br>`local-part` cannot start and end with special characters<br>`domain-part` must end with `u.nus.edu`                 | `johndoe@u.nus.edu` ,<br>`john_doe@u.nus.edu` | `$johndoe@u.nus.edu` <br>`johndoe@gmail.com`     |
| `t/`<br>Tag              | Only contain alphanumeric characters                                                                                                                                                                                                                                | `TD8`                                         | `TD 8` <br>`Great at UI`                         |
| `gn/`<br>Group Name      | Format: `[Module]-[Tutorial Group]-[Group Number]`<br>`Module` can be either `CS2103` or `CS2103T`<br>`Tutorial Group` should be one letter followed by a positive integer<br>`Group Number` should be a positive integer<br>Integer are supported up to 2147483647 | `CS2103-F12-2`<br>`CS2103T-W1-5`              | `CS2103A-1-A1`<br>`CS2040S-A1-1`                 |
| `tn/`<br>Task Name       | Cannot be blank                                                                                                                                                                                                                                                     | `Release tP v1.6`                             |                                                  |
| `td/`<br>Task Deadline   | Must be in the following format: `YYYY-MM-DD HHmm`<br>Relative to Singapore's timezone                                                                                                                                                                              | `2024-11-09 1800`                             | `today` <br>`2024-1-9 1900` <br>`2024-01-09 800` |
| `q/`<br>Query            | No limitations                                                                                                                                                                                                                                                      | `iP`<br>`tP v1.5`                             |                                                  |
| `i/`<br>Index            | Must be positive integer                                                                                                                                                                                                                                            | `1`<br>`10`                                   | `-1`<br>`test`                                   |

---

<div style="page-break-after: always;"></div>

## Command Summary

### Student

| Action                        | Format, Examples                                                                                                                     |
|-------------------------------|--------------------------------------------------------------------------------------------------------------------------------------|
| **List Students**             | `list_s/ls`                                                                                                                          |
| **Add Student**               | `add_s/as sno/STUDENT_NUMBER sn/STUDENT_NAME e/EMAIL [t/TAG]...`<br>e.g., `as sno/A0123456A sn/James Ho e/e0123456A@u.nus.edu t/TD9` |
| **Delete Student**            | `del_s/ds sno/STUDENT_NUMBER`<br>e.g., `ds sno/A0123456A`                                                                            |
| **Edit Student**              | `edit_s/es i/INDEX [sn/STUDENT_NAME] [e/EMAIL] [t/TAG]...`<br>e.g., `es i/1 sno/A0123456A sn/James Ho Ting Kang`                     |
| **Add Student to Group**      | `add_s_g/asg sno/STUDENT_NUMBER gn/GROUP_NAME`<br>e.g., `asg sno/A0123456A gn/CS2103-F12-2`                                          |
| **Delete Student From Group** | `del_s_g/dsg sno/STUDENT_NUMBER`<br>e.g., `dsg sno/A0123456A`                                                                        |
| **Find Student**              | `find_s/fs q/QUERY [q/QUERY]...`<br>e.g., `fs q/James`                                                                               |
| **Sort Students**             | `sort_s/ss`                                                                                                                          |

### Group

| Action           | Format, Examples                                                                          |
|------------------|-------------------------------------------------------------------------------------------|
| **List Groups**  | `list_g/lg`                                                                               |
| **Add Group**    | `add_g/ag gn/GROUP_NAME [gn/GROUP_NAME]...`<br>e.g., `ag gn/CS2103-F12-2 gn/CS2103-F12-1` |
| **Delete Group** | `del_g/dg gn/GROUP_NAME`<br>e.g., `dg gn/CS2103-F12-2`                                    |
| **Edit Group**   | `edit_g/eg i/INDEX gn/GROUP_NAME`<br>e.g., `eg i/1 gn/CS2103-F12-3`                       |
| **Find Group**   | `find_g/fg q/QUERY [q/QUERY]...`<br>e.g., `fg q/CS2103-F12-2`                             |
| **Sort Groups**  | `sort_g/sg`                                                                               |

### Task

| Action                          | Format, Examples                                                                                                                                                 |
|---------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **List Task**                   | `list_t/lt [gn/GROUP_NAME]`<br>e.g., `lt`, `lt gn/CS2103-F12-2`                                                                                                  |
| **Add Task to Group**           | `add_t_g/atg tn/TASK_NAME td/TASK_DEADLINE (YYYY-MM-DD HHmm) gn/GROUP_NAME [gn/GROUP_NAME]...`<br>e.g., `atg tn/v1.5 Release td/2024-11-07 2359 gn/CS2103-F12-2` |
| **Add Task to All Groups**      | `add_t/at tn/TASK_NAME td/TASK_DEADLINE (YYYY-MM-DD HHmm)`<br>e.g., `at tn/Submit Postmortem td/2024-10-20 1800`                                                 |
| **Add Existing Task to Group**  | `add_et_g/aetg i/INDEX gn/GROUP_NAME [gn/GROUP_NAME]...`<br>e.g., `aetg i/1 gn/CS2103-F12-3`                                                                     |
| **Delete Task from All Groups** | `del_t/dt i/INDEX`<br>e.g., `dt i/1`                                                                                                                             |
| **Delete Task from Group**      | `del_t_g/dtg i/INDEX gn/GROUP_NAME`<br>e.g., `dtg i/1 gn/CS2103-F12-2`                                                                                           |
| **Edit Task for Group**         | `edit_t_g/etg i/INDEX gn/GROUP_NAME [tn/TASK_NAME] [td/TASK_DEADLINE (YYYY-MM-DD HHmm)]` <br>e.g., `etg i/1 gn/CS2103-F12-3 tn/v1.4 Release`                     |
| **Edit Task for All Groups**    | `edit_t/et i/INDEX [tn/TASK_NAME] [td/TASK_DEADLINE (YYYY-MM-DD HHmm)]`<br>e.g., `et i/1 td/2024-11-20 1200`                                                     |
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
