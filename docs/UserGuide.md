---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

<br>

![](images/doctrack.png)
# User Guide

<box type="info" light theme="primary">
<b>DocTrack</b> is a desktop application for <b>general practitioners (GPs)</b> to quickly access patient contact 
details, appointments, and treatment records.
</box>

With DocTrack, you can take the first step towards digitalisation of your practice!
DocTrack aims to simplify your patient and appointment tracking process, by allowing you to
- easily add and update patient and appointment records
- delete records for regulation purposes
- search for records based on important criteria such as dates and names
with an intuitive and user-friendly interface!

To get started, you can read the introduction below, which shows all the different sections
in this user guide.

---

## Introduction

- [Quick start](#quick-start):
  - Quickly get started with DocTrack.

- [Interacting with DocTrack](#interacting-with-doctrack):
  - Learn how you can interact with DocTrack.

- [Command Overview](#command-overview):
  - Navigate here for information on how the commands work.
  - [Summary of Commands](#summary-of-commands):
    - for a general summary of all commands.
  - [Person Commands](#person-commands):
    - for more information on person commands.
  - [Appointment Commands](#appointment-commands):
    - for more information on appointment commands.
  - [General Commands](#general-commands):
    - for more information on general commands.

- [FAQ](#faq):
  - Find answers to common questions you might have.

- [Known issues](#known-issues):
  - Discover common issues that may arise while you are using DocTrack.

<!-- * Table of Contents -->
<page-nav-print></page-nav-print>

<br>

---

<br>

## Quick start

1. Ensure you have Java `17` or above installed in your computer.
   - To install `Java 17`, download Java. Refer to the [guide for installing Java](https://docs.oracle.com/en/java/javase/17/install/overview-jdk-installation.html).
     - Alternatively, download it from [here](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).
   - To check your Java version, refer [here](https://www.java.com/en/download/help/version_manual.html#).

<br>

2. You can download our latest release file from [here](https://github.com/se-edu/addressbook-level3/releases). It 
   is a `.jar` file. For more information, you can refer to: 
   - [What is a JAR file?](https://docs.oracle.com/javase/8/docs/technotes/guides/jar/jarGuide.html)
   - [How to run a JAR file](https://stackoverflow.com/questions/5774970/run-jar-file-in-command-prompt).

<br>

3. Copy the file to the folder you want to use as the _home folder_ for your DocTrack application. 
   - The DocTrack application will create a `data` folder in this _home folder_ to store your [data files](#faq).

<br>

4. Open a [command terminal](https://developer.mozilla.org/en-US/docs/Learn/Tools_and_testing/Understanding_client-side_tools/Command_line) of your preference, and run the commands:
    ```
    cd HOME_FOLDER
    java -jar DocTrack.jar
   ```
    For example, if you copied `DocTrack.jar` into the folder `C:/Users/user/Documents`,
    run the commands:
    ```
   cd C:/Users/user/Documents
   java -jar DocTrack.jar
   ```

<br>

5. A GUI similar to the one below should appear in a few seconds. Note how the DocTrack application contains some sample data.
   <br>
   ![Ui](images/Updated_UI.png)

<br>

6. Type the command in the command box, which is indicated by `Enter command here...`. Press `Enter` to execute it.<br>
   _**Example:** Typing **`help`** and pressing `Enter` will open the help window._<br>
   
   Some other examples of commands you can enter:
   * `list person` : Lists all contacts.
   * `add person n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 st/recovering` : Adds a contact named `John Doe` to the Address Book.
   * `delete person 3` : Deletes the 3rd contact shown in the current list.
   * `clear person` : Deletes all contacts.
   * `exit` : Exits the app.

<br>

7. You can refer to the [Command Overview](#command-overview) below for details of each command. 

<br>

---

<br>

## Interacting with DocTrack

All interactions with the DocTrack platform are done through a series of **commands** entered by you. 
- These commands allow you to perform various **tasks**, such as adding, editing, listing, deleting, finding,
and clearing persons/appointments. 
- The commands are designed to be flexible and user-friendly. 
  - You can add fields in any order.
  - You can add multiple entries for specific fields.

<box type="tip" light>

Here are some **key points** to keep in mind when you are using the commands:

- You should supply **all** the fields (words in **`UPPER_CASE`**) that are needed for a command.<br>
  _**Example:** In `add person n/NAME`, **`NAME`** is a field which can be used as `add person n/John Doe`._

- The **`INDEX`** in command formats like `edit person INDEX` and `delete appt INDEX` refers to the index 
  number (shown beside the person or appointment) in the displayed list. The index **must be a positive integer i.e. 1, 2, 3...** <br>
  _**Example:** The index of the first person displayed at the top the list is `1`, the index of the second person displayed is `2`, and so on._

- Items in **square** brackets **`[]`** are **optional**. <br>
  _**Example:** `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`._

- Items with `…​` after them can be used **zero times or more times**. <br>
  _**Example:**`[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc._

- You can add fields in **any order**. <br>
  _**Example:** If the command specifies `n/NAME p/PHONE_NUMBER`, then `p/PHONE_NUMBER n/NAME` is also acceptable._

<box type="tip" theme="success" light style="border: 1px solid">

**Tip:** 

If you add extraneous fields for commands that do not take in fields (such as `help`, `list person`, 
`list appt`, `exit` and `clear`), they will be ignored. <br>
_**Example:** If the command specifies `help 123`, then it will be interpreted as `help`._

</box>

<box type="warning" light seamless style="border: 1px solid red">

**Caution:**

If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines. This is because some space characters surrounding line-breaks may be omitted when copied over.

</box>

</box>

<br>

---

<br>

## Command Overview

The following sections describe the various commands you can use in the DocTrack application:

- [Person Commands](#person-commands)
- [Appointment Commands](#appointment-commands)
- [General Commands](#general-commands)

<br>

### Person Commands

A **person** is a patient with several fields. The fields and their corresponding prefixes are as follows:

| Field            | Prefix | Optional | Multiple |
|------------------|--------|----------|----------|
| **Name**         | `n/`   | No       | No       |
| **Phone number** | `p/`   | No       | No       |
| **Email**        | `e/`   | No       | No       |
| **Address**      | `a/`   | No       | No       |
| **Status**       | `st/`  | No       | No       |
| **Tag(s)**       | `t/`   | Yes      | Yes      |

You can identify these patients uniquely by their **patient ID (PID)** for easy reference. 

<br>

 You can also manage the patient information through different commands, which can be seen in the table below:

| Action                                                      | Format                                                                                    | Examples                                                                                                       |
|-------------------------------------------------------------|-------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------|
| **[Add person](#adding-a-person-add-person)**               | `add person n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS st/STATUS [t/TAG]…​`                  | `add person n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 st/recovering t/friend` |
| **[List all persons](#listing-all-persons-list-person)**    | `list person`                                             <br/>                           | `list person`                                                                                                  |
| **[Edit person](#editing-a-person-edit-person)**            | `edit person INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [st/STATUS] [t/TAG]…​` | `edit person 2 n/James Lee e/jameslee@example.com`                                                             |
| **[Find person](#finding-persons-by-name-find-person)**     | `find person KEYWORD [MORE_KEYWORDS]`                                                     | `find person n/James Jake`                                                                                     |
| **[Delete person](#deleting-a-person-delete-person)**       | `delete person INDEX`                                                                     | `delete person 3`                                                                                              |
| **[Clear all persons](#clearing-all-persons-clear-person)** | `clear person`                                                                            | `clear person`                                                                                                 |

<br>

##### Adding a person: `add person`

You can add a person to the patient book.

**Format**: `add person n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS st/STATUS [t/TAG]…​`

<box type="tip" light>

**Tip:** A person can have any number of tags (including 0). <br>
_**Example:** `t/friend t/likes coding` has 2 tags and it is valid._

</box>

**Examples**:

- `add person n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 st/recovering` 
  adds a person named `John Doe` with phone number `98765432`, email `johnd@example.com`, and address `John street, block 123, #01-01`, and status `recovering`.
- `add person n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 st/recovered t/criminal` adds a person named `Betsy Crowe` with email `betsycrowe@example.com`, address `Newgate 
  Prison`, phone number `1234567`, status `recovered`, and the tags `friend` and `criminal`.

<br>

##### Listing all persons : `list person`

You can view a list of all persons in the patient book.

**Format**: `list person` shows the list of all patients on the screen.

<br>

##### Editing a person : `edit person`

Edits an existing patient in DocTrack.

**Format**: `edit person INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [st/STATUS] [t/TAG]…​`

- Edits the patient at the specified `INDEX`.
- At least one of the optional fields must be provided.
- Existing values will be updated to the input values.
- When editing tags, the existing tags of the person will be removed i.e. adding of tags is not cumulative.
- You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.

**Examples**:

- `list` followed by `edit person 1 p/91234567 e/johndoe@example.com` edits the phone number and email 
  address of the first person in the list to be `91234567` and `johndoe@example.com` respectively.
- `list` followed by `edit person 2 n/Betsy Crower t/` edits the name of the second person in the list to 
  be `Betsy Crower` and clears all existing tags.

<box type="tip" theme="success">

**Tip:** When trying to add tags while keeping existing tags, remember to type in all the pre-existing tags with your new tags
</box>

<br>

##### Finding persons by name: `find person`

You can find person(s) whose **names** contain any of the given keywords.

**Format**: `find person KEYWORD [MORE_KEYWORDS]`

- Use the prefix `n/` to search for names.
- The search is case-insensitive. e.g. `hans` will match `Hans`
- The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
- Only the name is searched.
- Only full words will be matched e.g. `Han` will not match `Hans`
- Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

**Examples**:

- `find person n/John` returns `john` and `John Doe` 
- `find person n/alex david` returns `Alex Yeoh`, `David Li`<br><br>
  ![result for 'find person alex david'](images/findAlexDavidResult.png)

<br>

##### Deleting a person : `delete person`

You can delete a specified person from the patient book.

**Format**: `delete person INDEX`

- Deletes the person at the specified `INDEX`.

<box type="warning" light>

**Warning:** This action is irreversible. Ensure you have selected the correct person before deleting.

</box>

<box type="tip" light>

**Tip:** Make you have the correct index of the patient before deleting. Use `list person` to check

</box>

**Examples**:

- `list` followed by `delete person 2` deletes the second person in the list.
- `find Betsy` followed by `delete person 1` deletes the first person in the results of the `find` command.

<br>

##### Clearing all persons : `clear person`

You can delete all person entries from the patient book.

**Format**: `clear person`

<box type="warning" light>

**Warning:** This action is irreversible. Ensure you have a backup of the patient data before clearing. 

</box>


<br>

---

<br>

### Appointment Commands

An **appointment** is defined by several fields. The fields and their corresponding prefixes are as follows:

| Field               | Prefix | Optional | Multiple |
|---------------------|--------|----------|----------|
| **Person ID**       | `i/`   | No       | No       |
| **Appointment Type**| `ty/`  | No       | No       |
| **Date and Time**   | `d/`   | No       | No       |
| **Sickness**        | `s/`   | Yes      | No       |
| **Medicine**        | `m/`   | Yes      | No       |

You can manage the appointments through different commands, which can be seen in the table below:

<br>

| Action                                                                   | Format                                                                                                                   | Examples                                                              |
|--------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------|
| **[Add appointment](#adding-an-appointment-add-appt)**                   | `add appt i/PERSON_ID d/DATE_TIME ty/APPOINTMENT_TYPE [s/SICKNESS] [m/MEDICINE]`                   <br/>           <br/> | `add appt i/1 d/2024-12-01 09:30 ty/Consulation s/Diabetes m/Insulin` |
| **[List all appointments](#listing-all-appointments-list-appt)**         | `list appt`                                                                                                              | `list appt`                                                           |
| **[Edit appointment](#editing-an-appointment-edit-appt)**                | `edit appt INDEX [i/PERSON_ID] [d/DATE_TIME] [ty/APPOINTMENT_TYPE] [s/SICKNESS] [m/MEDICINE]`                            | `edit appt 3 d/2024-12-05 13:00 m/Panadol`                            |
| **[Find appointment](#finding-appointments-by-name-and-date-find-appt)** | `find appt KEYWORD [MORE_KEYWORDS]`                                                                                      | `find appt d/2024-12-05`                                              |
| **[Delete appointment](#deleting-an-appointment-delete-appt)**           | `delete appt INDEX`                                                                                                      | `delete appt 2`                                                       |
| **[Clear all appointments](#clearing-all-appointments-clear-appt)**      | `clear appt`                                                                     <br/>                                   | `clear appt`                                                          |
---

<br>

##### Adding an appointment: `add appt`

You can an appointment to DocTrack. 

**Format**: `add appt i/PERSON_ID ty/APPOINTMENT_TYPE d/DATE_TIME [s/SICKNESS] [m/MEDICINE]`
- The format of `DATE_TIME` is `yyyy-MM-dd HH:mm`. For example, `2025-03-20 10:30`.

**Examples**:

- `add appt i/1 ty/Consulation d/2024-12-01 09:30 s/Diabetes m/Insulin` adds an appointment to the person with personId `1`. The appointment has appointment type `Consulation`, date and time `2024-12-01 09:30`, sickness `Diabetes`, and medicine `Insulin`.
- `add appt i/2 ty/Follow-up d/2024-02-01 16:40 s/Asthma m/Inhaler` adds an appointment to the person with personId `2`. The appointment has appointment type `Follow-up`, date and time `2024-02-01 16:40`, sickness `Asthma`, and medicine `Inhaler`.

<box type="info" light seamless>

**Note:** Although you type `DATE_TIME` in the format `yyyy-MM-dd HH:mm`, you will see it displayed as `Month Date, Year, Time`. <br>
_**Example:** You will see `2024-12-10 12:30` displayed as `December 10, 2024, 12:30 PM`._

</box>

<box type="tip" light>

**Tip:** You can decide to leave out sickness and medicine inputs when adding them, then edit them later.
</box>

<br>

<br>

##### Listing all appointments : `list appt`

You can view a list of all appointments in the appointment book.

**Format**: `list appt`

<br>

##### Editing an appointment : `edit appt`

You can an existing appointment in appointment book.

**Format**: `edit appt INDEX [i/PERSON_ID] [d/DATE_TIME] [ty/APPOINTMENT_TYPE] [s/SICKNESS] [m/MEDICINE]`

- Edits the appointment at the specified `INDEX`.
- At least one of the optional fields must be provided.
- Existing values will be updated to the input values.
- The format of `DATE_TIME` is `yyyy-MM-dd HH:mm`. For example, `2025-03-20 10:30`.

**Examples**:

- `edit appt 3 d/2024-12-05 13:00 m/Budesonide` edits the date and time and the medicine to be `2024-12-05 13:00` and `Budesonide` respectively.

<br>

##### Finding appointments by name and date: `find appt`

You can find appointments whose person names and / or dates contain any of the given keywords.

**Format**: `find appt KEYWORD [MORE_KEYWORDS]`
- Use the prefix `n/` to search for patient names.
- Use the prefix `d/` to search for dates.
- At least one keyword must be provided.
- The format of `DATE` is `YYYY-MM-DD`. For example, `2024-10-16`.

**Examples**:
- `find appt n/John` returns appointments with patients named `John`.
- `find appt d/2024-12-05` returns appointments on `2024-12-05`.
- `find appt n/John d/2024-12-05` returns appointments with patients named `John`, and is on `2024-12-05`.
![result for 'find appt John and 2024-12-05'](images/findApptExample.png)

<box type="tip" light>

**Tip:** Use the `find appt` command to filter out appointments for a certain day or patient

</box>

<br>

##### Deleting an appointment : `delete appt`

You can delete a specified appointment from appointment book.

**Format**: `delete appt INDEX`

- Deletes the appointment at the specified `INDEX`.

<box type="warning" light>

**Warning:** This action is irreversible. Ensure you have selected the correct appointment before deleting.

</box>

**Examples**:

- `list appt` followed by `delete appt 2` deletes the second appointment in the list of appointments.
- `find appt d/2024-12-05` followed by `delete appt 1` deletes the first appointment in the results of the 
  `find` command.

<box type="tip" light>

**Tip:** Use the `list appt` to check the index of the appointment before deleting one.

</box>
<br>

##### Clearing all appointments : `clear appt`

You can delete all appointment entries from the appointment book.

**Format**: `clear appt`

<box type="warning" light>

**Warning:**
This action is irreversible. Ensure you have a backup of the appointment data before clearing.

</box>

<br>

---

<br>

### General Commands

**General commands** can help you with miscellaneous tasks related to patient and appointment management.

| Action                                | Format | Examples |
|---------------------------------------|--------|----------|
| **[Help](#viewing-help-help)**        | `help` | `help`   |
| **[Exit](#exiting-the-program-exit)** | `exit` | `exit`   |

<br>

##### Viewing help : `help`

You will be able to view a help window that directs you to this user guide.

**Format**: `help`

<img src="images/helpMessage.png" alt="Help Message" width="700">

<br><sbr>

##### Exiting the program : `exit`

You will be able to exit the DocTrack application. 

**Format**: `exit`

<br>

##### Saving the data

The data of the patients and appointments are saved in the hard disk automatically after you run any command that changes the data. You do not need to save them manually.
- For more information on how the data is stored, you can refer to the [FAQ](#faq) section.

<br>

##### Editing the data file

For advanced users, feel free to update patient and appointment data directly by editing the [data files](#faq) in the `data` folder.

<box type="warning" light>

**Caution:**

- If your changes to the data file makes its format invalid, DocTrack will discard all data and start with an empty data file at the next run. Hence, it is recommended that you have a backup of the file before 
  editing it.<br>
- Furthermore, certain edits can cause DocTrack to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, you should edit the data file only if you are confident that you 
  can update it correctly.
</box>

<br>

---

<br>

## FAQ

**Q**: How is my data stored?<br>
**A**: Your data is stored in [.json](https://www.json.org/json-en.html) files, located in the `data` folder. The details of patients are stored in the `addressbook.json` file, while the appointments are stored in the `appointmentbook.json` file.
Examples:

<img src="images/json/addressbook.png" alt="Address Book JSON" width="420">


<img src="images/json/appointmentBook.png" alt="Appointment Book JSON" width="390">

<br><br>

**Q**: How do I transfer my data (in the `addressbook.json` and `appointmentbook.json` files) to another 
computer?<br>
**A**: 
1. Save the `addressbook.json` and `appointmentbook.json` files from the `data` folder in your current computer.
2. [Install the DocTrack app](#quick-start) in the other computer. 
3. When you run DocTrack, it creates empty [.json](https://www.json.org/json-en.html) files in the `data` 
   folder. 
4. Replace these empty files with the `addressbook.json` and `appointmentbook.json` files you saved from your previous computer.

<br>

**Q**: Can I access DocTrack without internet connection?<br>
**A**: Yes, You can run DocTrack without an internet connection.

<br>

**Q**: How do I back up my data?<br>
**A**: Since the data is saved in the `addressbook.json` and `appointmentbook.json` files in the `data` 
folder, you can copy these files to another location as a backup.

<br><br>

---

<br>

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

<br>

2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

<br>
