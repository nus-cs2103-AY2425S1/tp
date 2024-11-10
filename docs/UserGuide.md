---
layout: default.md
title: "User Guide"
pageNav: 3
---

# KnottyPlanners 💍🎀

KnottyPlanners is a **desktop app for wedding planners, optimised for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you are a wedding planner who can type fast, KnottyPlanners can make organising weddings a walk in the park!

### What is Command Line Interface? 🤔
Command Line Interface (CLI) allows you to type text commands to perform specific tasks quickly and efficiently on a computer. Don't worry if you are still unsure about CLI, we will walk you through in this User Guide! 😊

### Why use Command Line Interface ? 
* **Efficiency:** Perform tasks faster by replacing multiple mouse clicks into a single line of text ✅
* **Precision:** Avoid mis-clicking and mistakes as CLI will execute the exact command you type 💯
* **Offline:** CLI does not require internet and allows you to use it anywhere 👩‍💻

<!-- * Table of Contents -->
## Table of Contents
- [Quick Start](#quick-start-non-technical-users)
- [Command Summary](#command-summary)
- [Parameter Constraints](#parameter-constraints)
- [Features](#features)
- [FAQ](#faq)
- [Known Issues](#known-issues)

<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick Start (Non-technical Users)

1. Open up the command prompt (in Windows) or terminal (in MacBook) on your computer.

2. Type in `java -version` and hit enter.

3. If your version of Java is 17 and above, skip to step 6. If it is less than 17, click [here](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) to download Java 17.

4. For Windows users, download the Windows x64 Installer. For MacBook users, download the macOS Arm 64 DMG Installer. Follow the respective installation guides.

5. After successfully completing the installation, repeat steps 1 to 3 to ensure that you have Java 17.

6. Download the latest `KnottyPlanners.jar` file under the Assets tab from [here](https://github.com/AY2425S1-CS2103T-W13-4/tp/releases).

7. Create a new folder in your Desktop and copy the `KnottyPlanners.jar` file into that folder.

8. Repeat step 1 to open up a new terminal.

9. Type in `cd Desktop\NEW_FOLDER_NAME` where `NEW_FOLDER_NAME` is the name of the folder you created in step 7 and hit enter.

10. Type in `java -jar KnottyPlanners.jar` and hit enter to run the application.

11. You can refer to the [Command Summary](#command-summary) for an overview of the available commands. If you need more information, the [Features](#features) section below contains more details regarding each command.

## Quick Start (Technical Users)

1. Ensure you have Java `17` or above installed in your Computer.

2. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-W13-4/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your KnottyPlanners.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar KnottyPlanners.jar` command to run the application.

5. You can refer to the [Command Summary](#command-summary) for an overview of the available commands. If you need more information, the [Features](#features) section below contains more details reagarding each command.

**Note**: We have a default database for you to try out and test commands! If you prefer not to have it, you can use the commands `clear-ab` and `clear-wb` to clear the address book and wedding book respectively.

--------------------------------------------------------------------------------------------------------------------
## Command Summary

| Action                                                             | Format, Examples                                                                                                                                                                                 |
|--------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **[Help](#viewing-help-help)**                                     | `help`                                                                                                                                                                                           |
| **[Add](#adding-a-contact-add)**                                   | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS j/JOB [t/TAG]` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 j/Photographer t/June and James 16 June`     |
| **[Delete](#deleting-a-contact-del-followed-by-y-or-n)**           | `del n/NAME` followed by `y` or `n`<br> e.g., `del n/John Doe` followed by `y`                                                                                                                   |
| **[Edit](#editing-a-contact-edit)**                                | `edit n/NAME [new/NEW_NAME] [p/NEW_PHONE] [e/NEW_EMAIL] [a/NEW_ADDRESS] [j/NEW_JOB]`<br> e.g.,`edit n/John new/James Lee e/jameslee@example.com`                                                 |
| **[List](#listing-all-contacts-list)**                             | `list`                                                                                                                                                                                           |
| **[Add Wedding](#adding-a-wedding-add-wed--aw)**                   | `add-wed w/NAME & NAME v/VENUE d/DATE` or `aw w/NAME & NAME v/VENUE d/DATE` <br> e.g., `add-wed w/ John & June v/Orchard Hotel d/12/12/2030` or `aw w/ John & June v/Orchard Hotel d/12/12/2030` |
| **[Delete Wedding](#deleting-a-wedding-del-wed--dw)**              | `del-wed w/NAME & NAME` or `dw w/NAME & NAME` followed by `y` or `n`<br> e.g., `del-wed w/John Loh & Jean Tan` or `dw w/John Loh & Jean Tan` followed by `y`                                                                                                                             |
| **[List Weddings](#listing-all-weddings-list-wed--lw)**            | `list-wed` or `lw`                                                                                                                                                                               |
| **[Add Tag](#adding-tags-to-a-contact-tag-add--ta)**               | `tag-add n/NAME t/TAG...` or `ta n/NAME t/TAG...` <br> e.g., `tag-add n/John Doe t/June & James` or `ta n/John Doe t/June & James`                                                               |
| **[Deleting Tags](#deleting-tags-from-a-contact-tag-del--td)**     | `tag-del n/NAME t/TAG...` or `td n/NAME t/TAG...` <br> e.g., `tag-del n/John Doe t/June & James` or `td n/John Doe t/June & James`                                                               |
| **[Filter](#filtering-contacts-by-name-and-job-filter--fil)**      | `filter n/KEYWORD` or `filter j/KEYWORD` or `fil n/KEYWORD` <br> e.g., `filter n/John` or `filter j/Photographer` or `fil n/Harry`                                                               |
| **[View Wedding](#view-wedding-view-wed--vw)**                     | `view-wed NAME & NAME` or `vw NAME & NAME` <br> e.g., `view-wed John & Sarah` or `vw John & Sarah`                                                                                               |
| **[Clear](#clearing-all-entries-clear-ab--cab-and-clear-wb--cwb)** | `clear-ab` or `cab` for address book and `clear-wb` or `cwb` for wedding book followed by `y` or `n`                                                                                             |
| **[Exit](#exiting-the-program-exit)**                              | `exit`                                                                                                                                                                                           |
--------------------------------------------------------------------------------------------------------------------

### Parameter Constraints
The table below provides a brief explanation of each parameter encountered in the command summary above. It also details the constraints of each parameter used in a command.

<box type="important" seamless>

**IMPORTANT:** Please ensure that all parameters adhere to the constraints mentioned below. If you choose not to do so, the command will not be executed and error messages will be shown.

</box>

<box type="info" seamless>

**Autoformatting in KnottyPlanners:**<br>

* KnottyPlanners aligns with industry standards by automatically formatting the `NAME`, `JOB`, `TAG` and `WEDDING NAME` fields.
* All other fields that are not mentioned above are not formatted and will be saved based on your user input.

* For each field:
    - The first letter of each word is auto-capitalised.
    - Leading and trailing spaces are trimmed.
    - Extra spaces in between words are trimmed to 1 space..

* Examples:
    - `li sirui` is formatted to `Li Sirui`
    - `  Wedding Photographer ` is formatted to `Wedding Photographer`
    - `Adam &    Harry` is formatted to `Adam & Harry` 

</box>


| Parameter | Prefix | Definition | Case-sensitvity | Constraints | Examples |
|--------------------|------|---------------------------------------------------|------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `NAME`             | `n/` | Name of the person.                               | Case-insensitive | - Only alphanumeric characters, spaces, `/`, and `.` are allowed. <br> - Should not be blank. <br> - If the name contains a `-`, a possible workaround is to replace the `-` with an empty spacing. | :+1: `Muhammad Ali` <br>:+1: `Henry s/o Nathan` <br>:+1: `Robert Downey Jr.` <br>:x: `Trent Alexander-Arnold` <br>:x: `@JohnnyBoi` |
| `PHONE`            | `p/` | Phone number of the person.                       | NA               | - Only numbers and `+` are allowed. <br> - Should be at least 3 digits long. <br> - Should not be blank. <br> - There is no limit on the maximum length of phone numbers accepted to accommodate international phone numbers as well. | :+1: `98765432` <br>:+1: `+65 9876 5432` <br>:x: `1234 5678 (HP) 1111-3333 (Office)` |
| `EMAIL`            | `e/` | Email address of the person.                      | Case-sensitive   | - Should be in the format `local-part@domain`. <br> - `local-part` should only contain alphanumeric characters and the special characters `+`, `_`, `.`, `-`. `local-part` may not start or end with any special characters, or contain consecutive special characters. <br> - `domain` must be at least 2 characters long, start and end with alphanumeric characters. <br> - Should not be blank. <br> | :+1: `gary@yahoo.com` <br>:x: `henry` <br>:x: `j++a@rocketmail.com` |
| `ADDRESS`          | `a/` | Address of the person.                            | Case-sensitive   | - All values are allowed. <br> - Should not be blank. | :+1: `Woodlands Dr 71, Blk 680C, #08-12, S721767` |
| `JOB`              | `j/` | Occupation of the person.                         | Case-insensitive | - All values are allowed. <br> - Should not be blank. | :+1: `DJ` |
| `TAG`              | `t/` | A tag is the wedding associated with that person. | Case-insensitive | - Only alphanumeric characters spaces, `/`, `.` and `&` are allowed. <br> - Should be in the format `NAME & NAME`. <br> - Should not be blank. | :+1: `Ahmad & Esther` <br> :+1: `Kattar d/o Hanif & Xavier Lee` <br> :x: `Jerry and Stacy` <br> :x: `Farah x Adam` |
| `WEDDING NAME`     | `w/` | Name of a wedding.                                | Case-insensitive | - Only alphanumeric characters spaces, `/`, `.` and `&` are allowed. <br> - Should be in the exact format `NAME & NAME`. <br> - Both names must be unique <br> - Should not be blank. | :+1: `Henry & Terry`<br> :+1: `Eubanks Jr. & Ayesha` <br> :x: `Nwakame Dickson` <br> :x: `Siti n Syafie` <br> :x: `Jolyn &Max` <br> :x: `Sara & Sara` |
| `VENUE`            | `v/` | Location of the wedding.                          | Case-sensitive   | - All values are allowed. <br> - Should not be blank. | :+1: `Fullerton Hotel, Ballroom 1, #01-05` |
| `DATE`             | `d/` | Date of the wedding.                              | NA               | - Should be in the format `DD/MM/YYY`. <br> - Only numbers and `/` are allowed. <br> - Should not be blank. <br> - The range of dates allowed are `01/01/1900` to `31/12/2099` | :+1: `12/12/2025` <br> :x: `12th June 2021` <br> :x: `2025-09-03` <br> :x: `31/12/1899` <br> :x: `01/01/2100` |



## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Tags in KnottyPlanners are used exclusively to tag person to wedding, hence both wedding name and tag names should be 2 person names separated with a & (e.g. `John Loh & Jean Tan`, `Stacy & Sam`).

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `John Loh & Jean Tan`, `Stacy & Sam` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `list-wed`, `exit`, `clear-ab` and `clear-wb`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* Prefixes refer to the identifier before the parameter e.g. `n/` before `NAME`. Invalid prefixes (i.e. prefixes that do not exist) will be ignored.<br>
e.g. if the command specifies `tag-add n/John Doe b/juice t/John Loh & Jean Tan`, it will be interpreted as `tag-add n/John Doe t/John Loh & Jean Tan`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
  </box>

### Viewing Help: `help`

You can view a popup with an overview of all commands, and also a link to this user guide if you need more details.

Format: `help`

![help message](images/helpmsg.png)


### Adding a Contact: `add`

You can add a person to the list of contacts.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS j/JOB [t/TAG]`

* A person can have any number of tags (including 0). Tags are associated to the weddings this person is
  involved in. Weddings must already exist in the wedding book to successfully tag a person to a wedding.

<box type="important" seamless>

**IMPORTANT:**

* If you are worried about adding duplicated people, fret not! KnottyPlanners will alert you when an identical person is added
* We will also alert you when you add a different person with the same name, we need your help to change their input name in these situations 😊

</box>

![add message](images/addMsg.png)

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, Blk 123, #01-04 j/Photographer`
* `add n/Betsy Crowe p/90341259 e/betsycrowe@example.com a/Newgate Center j/Caterer t/Stacy & Sam` P.S. The wedding `Stacy & Sam` must be created first using `add-wed` command!

### Deleting a Contact: `del` followed by `y` or `n`

You can delete a person from your list of contacts.

Format: `del n/NAME` followed by `y` or `n`

* Deletes the person with the specified `NAME` from the address book.
* The contact's details are shown for confirmation.
* The contact is deleted if `y` is entered.
* The contact is not deleted if `n` is entered, cancelling the delete operation and nothing will occur.

<box type="important" seamless>

**IMPORTANT:** `del n/NAME` MUST BE followed by either of the two commands, otherwise, following delete commands may be affected.

</box>

<div style="display: flex; justify-content: space-between;">
  <div style="text-align: center; width: 33%;">
    <img src="images/deleteMsg.png" alt="delete message" style="width: 100%;">
    <div>Confirmation Prompt</div>
  </div>
  <div style="text-align: center; width: 33%;">
    <img src="images/deleteYMsg.png" alt="deleteY message" style="width: 100%;">
    <div>Success</div>
  </div>
  <div style="text-align: center; width: 33%;">
    <img src="images/deleteNMsg.png" alt="deleteN message" style="width: 100%;">
    <div>Cancel Operation</div>
  </div>
</div>

Examples:
* `del n/John Doe` followed by `y` deletes the person named `John Doe` from the address book.
* `del n/John Doe` followed by `n` cancels the delete operation.

### Editing a Contact: `edit`

You can edit an existing contact's details (name, phone number, email, address and job).

Format: `edit n/NAME [new/NEW_NAME] [p/NEW_PHONE] [e/NEW_EMAIL] [a/NEW_ADDRESS] [j/NEW_JOB]`

* You have to provide at least one of the optional fields.
* If you accidentally type the name of the contact in all capitals or add one too many spaces, don't worry! KnottyPlanners will
  automatically format the name by removing the extra spacing and correctly capitalizing it 🤩

<box type="important" seamless>

**IMPORTANT:** Tags can't be edited, so if you'd like to change a tag, simply delete the existing one using
['tag-del'](#deleting-tags-from-a-contact) and add a new one using ['tag-add'](#adding-tags-to-a-contact)!

</box>

<div style="display: flex; justify-content: space-between;">
   <div style="text-align: center; width: 50%;">
    <img src="images/editMsg.png" alt="edit message before" style="width: 100%;">
    <div>Before</div>
  </div>
  <div style="text-align: center; width: 50%;">
    <img src="images/editMsg2.png" alt="edit message after" style="width: 100%;">
    <div>After</div>
  </div>
</div>

Examples:
*  `edit n/John Doe p/91234567 e/johndoe@example.com` Edits the phone number and email address of John Doe to be
   `91234567` and `johndoe@example.com` respectively.
*  `edit n/James new/Clark e/clarknewemail@example.com j/Florist` Edits the name, email and job to be
   `James`, `clarknewemail@example.com` and `Florist` respectively.

### Listing All Contacts: `list`

You can now view all contacts in the order they were added in!

Format: `list`

![list message](images/listMsg.png)

### Adding a Wedding: `add-wed` / `aw`

You can add a wedding to the list of weddings.

Format: `add-wed w/NAME & NAME v/VENUE d/DATE` / `aw w/NAME & NAME v/VENUE d/DATE`

* If you are worried about adding duplicated weddings, fret not! KnottyPlanners will alert you when an identical wedding is added.
* We will also alert you when you add a different wedding with the same name, we need your help to change their input name in these situations 😊.
* To make adding a wedding easier, Knotty Planner will format the names for you! Wedding names will be automatically capitalised and separated with 1 space. Trailing spaces and extra space in between will be removed.
  Examples: `john & jane`, `JOHN   & jane` will all be formatted to `John & Jane`.

<box type="important" seamless>

**IMPORTANT:** Date must be a valid date in the format of **dd/MM/yyyy**.

</box>

![add wedding message](images/addWeddingMsg.png)

Examples:
* `add-wed w/John Loh & Jean Tan v/Orchard Hotel d/15/10/2022`
* `aw w/Jonus Ho & Izzat Syazani v/Pasir Ris Hotel d/02/11/2022`

### Deleting a Wedding: `del-wed` / `dw`

You can delete a wedding from your list of weddings.

* Deletes the wedding with the specified `NAME & NAME` from the address book.
* The wedding's details are shown for confirmation.
* The wedding is deleted if `y` is entered.
* The wedding is not deleted if `n` is entered, cancelling the delete operation and nothing will occur.

Format: `del-wed w/NAME & NAME` / `dw w/NAME & NAME` followed by `y` or `n`

<box type="important" seamless>

**IMPORTANT:** 

* `dw w/NAME & NAME` MUST BE followed by either of the two commands, otherwise, following delete commands may be affected.
* The wedding must be entered exactly as it was saved in the wedding book to successfully delete it.

</box>

<div style="display: flex; justify-content: space-between;">
  <div style="text-align: center; width: 33%;">
    <img src="images/deleteWeddingMsg.png" alt="delete wedding message" style="width: 100%;">
    <div>Confirmation Prompt</div>
  </div>
  <div style="text-align: center; width: 33%;">
    <img src="images/deleteWeddingYMsg.png" alt="delete wedding message success" style="width: 100%;">
    <div>Success</div>
  </div>
  <div style="text-align: center; width: 33%;">
    <img src="images/deleteWeddingNMsg.png" alt="delete wedding message cancel" style="width: 100%;">
    <div>Cancel Operation</div>
  </div>
</div>

Examples:
* `del-wed w/John Loh & Jean Tan` followed by `y` deletes the wedding named `John oh & Jean Tan` from the address book.
* `dw w/Jonus Ho & Izzat Syazani` followed by `n` cancels the delete operation.

### Listing All Weddings: `list-wed` / `lw`

You can now view all weddings in the order they were added in!

Format: `list-wed` / `lw`

![list wedding message](images/listWedding.png)

### Tagging a Contact

### Adding Tag(s) to a Contact: `tag-add` / `ta`
Add your contacts to a particular wedding!

Format: `tag-add n/NAME t/TAG...` / `ta n/NAME t/TAG...`

<box type="warning" seamless>

**TAKE NOTE:** 

* The wedding must already exist in the wedding book to successfully tag a person to a wedding.

* The name of the tag must match the wedding that you want to add the contact to.

* You can add one contact to multiple weddings in one go by specifying multiple tags in your command.

</box>

<div style="display: flex; justify-content: space-between;">
    <div style="text-align: center; width: 50%;">
        <img src="images/tagAddMsg1.png" alt="tag-add message before" style="width: 100%;">
        <div>Before</div>
    </div>
    <div style="text-align: center; width: 50%;">
        <img src="images/tagAddMsg2.png" alt="tag-add message after" style="width: 100%;">
        <div>After</div>
    </div>
</div>

Examples:
*  `tag-add n/John Doe t/Adam and Steve` Adds the tag `Adam and Steve` to John Doe.
*  `ta n/Betsy Crower t/Lacy & Bacy t/Peter & Mary t/Jonny & Bonny` Adds the tags `Lacy & Bacy`, `Peter & Mary`, and `Jonny & Bonny` to Betsy Crower.

### Deleting Tag(s) from a Contact: `tag-del` / `td`
Remove your contacts from a particular wedding!

Format: `tag-del n/NAME t/TAG...` / `td n/NAME t/TAG...`

* The name of the tag must match the wedding that you want to delete the contact from.

* You can remove a contact from multiple weddings in one go by specifying multiple tags in your command.

<box type="important" seamless>

**IMPORTANT:** The wedding must already exist in the wedding book to successfully delete a person from a wedding.

</box>

<div style="display: flex; justify-content: space-between;">
    <div style="text-align: center; width: 50%;">
        <img src="images/tagDelMsg1.png" alt="tag-add message before" style="width: 100%;">
        <div>Before</div>
    </div>
    <div style="text-align: center; width: 50%;">
        <img src="images/tagDelMsg2.png" alt="tag-add message after" style="width: 100%;">
        <div>After</div>
    </div>
</div>

Examples:
*  `tag-del n/John Doe t/Adam and Steve` Removes the tag `Adam and Steve` from John Doe.
*  `td n/Betsy Crower t/Lacy & Bacy t/Peter & Mary t/Jonny & Bonny` Removes the tags `Lacy & Bacy`, `Peter & Mary`, and `Jonny & Bonny` from Betsy Crower.

### Filtering Contacts by Name and Job: `filter` / `fil`

Conveniently search for contacts in your address book by name and/or job.

Format: `filter n/KEYWORD... j/KEYWORD...` / `fil n/KEYWORD... j/KEYWORD...`

* At least one of the `NAME` or `JOB` fields must be present.
* `KEYWORD` is not case-sensitive. e.g `photographer` will match `Photographer`.
* Only full words will be matched e.g. `jak` will not match `Jake`, `sam` will not match `Sam Tan`.
* `filter` returns the largest range of matches based on your input

<box type="tip" seamless>

**Tip:** You can filter by multiple name and/or job fields at once!

</box>

<div style="display: flex; justify-content: space-between;">
    <div style="text-align: center; width: 50%;">
        <img src="images/filterMsg1.png" alt="filter one field" style="width: 100%;">
        <div>Filter One Field</div>
    </div>
    <div style="text-align: center; width: 50%;">
        <img src="images/filterMsg2.png" alt="filter two fields" style="width: 100%;">
        <div>Filter Multiple Fields</div>
    </div>
</div>

Examples:
* `filter j/Photographer` returns `John` and `Ernest` whose jobs are photographers.
* `filter n/John` returns a person whose name is John.
* `fil n/jonus n/harry j/photographer` returns `Jonus`, `Harry` and all photographers that are in your contacts.
* `fil j/host j/caterer` returns all hosts and caterers that are in your contacts.

### View Wedding: `view-wed` / `vw`

You can view a list of all contacts tagged to the specified wedding.

Format: `view-wed NAME & NAME` / `vw NAME & NAME`

* `view-wed` / `vw` shows participants based on wedding names that match the keyword
* `NAME & NAME` is the name of the wedding and is not case-sensitive e.g `alice & bob` will match `Alice & Bob`
* `NAME & NAME` has to be in the correct order as saved in your wedding book e.g `alice & bob` will not match `Bob & Alice`
* Only full words will be matched e.g. `jak` will not match `Jake`
* Persons matching at least one keyword will not be returned (i.e. `AND` search)
  e.g. `Alice` will not return `Alice & Bob`

<box type="warning" seamless>

**TAKE NOTE:** No prefixes are required for this command

</box>


![view-wed message](images/viewWeddingMsg.png)

Examples:
* `view-wed Jane Lim & Tom Koh` returns `John Doe` who is a caterer for that wedding
* `vw Ahmad & Esther` returns `Halim` who is a best man for Ahmad and `Jia Zhun` who is a photographer for that wedding

### Clearing All Entries: `clear-ab` / `cab` and `clear-wb` / `cwb`

You can delete ALL contacts in the address book from the application.

1. `clear-ab` / `cab` clears all contacts in address book.
2. `clear-wb` / `cwb` clears all weddings in wedding book.
3. Both commands will prompt for confirmation before deleting all entries.
4. If you confirm, all entries will be deleted.
5. If you cancel, no entries will be deleted.

Format:
* `clear-ab` / `cab` followed by `y` clears all contacts in the address book.
* `clear-wb` / `cwb` followed by `n` will not clear all weddings in the wedding book.

<div style="display: flex; justify-content: space-between;">
    <div style="text-align: center; width: 50%;">
        <img src="images/clearMsg1.png" alt="clear ab" style="width: 100%;">
        <div>Clear Address Book</div>
    </div>
    <div style="text-align: center; width: 50%;">
        <img src="images/clearMsg2.png" alt="clear wb" style="width: 100%;">
        <div>Clear Wedding Book</div>
    </div>
</div>

### Exiting the Program: `exit`

You can exit the application and save any new changes.

Format: `exit`

### Saving the Data

KnottyPlanners data will be saved in the hard disk automatically after any command that changes the data.
There is no need to save manually.

### Editing the Data File

* KnottyPlanners data are saved automatically as a JSON file 
  * `[JAR file location]/data/addresbook.json`.
  * `[JAR file location]/data/weddingbook.json`.
* Advanced users are welcome to update data directly by editing that data file.
<box type="warning" seamless>

**<span style="color: red;">CAUTION:</span>**
  
* If your changes to the data file makes its format invalid, KnottyPlanners will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>

* Furthermore, certain edits can cause the KnottyPlanners to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.

</box>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous KnottyPlanners home folder.

**Q**: What should I do if the application does not start?<br>
**A**: If you are using a Mac, you may need to right-click the jar file and select `Open` to run the application. If you are using Windows, you may need to run the jar file as an administrator.

**Q**: How do I update the application?<br>
**A**: Download the latest jar file from the [[releases page]](https://github.com/AY2425S1-CS2103T-W13-4/tp/releases)

**Q**: How do all the commands work?<br>
**A**: Refer to the [Command summary](#command-summary) section for details of each command. Additionally, some tips and important details are provided in the [Features](#features) section.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimise the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimised, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.
3. **List Command** ignores any extraneous parameters after the `list` command. This is a feature, not a bug.
4. **Filter Command** returns the largest range of matches based on your input. This is a feature, not a bug.
5. **Names and Wedding Names** are automatically formatted to have the first letter of each word capitalized. This is a feature, not a bug.

--------------------------------------------------------------------------------------------------------------------
