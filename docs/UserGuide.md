---
  layout: default.md
  title: "User Guide"
  pageNav: 4
---

<h1 id="internbuddy-user-guide">
    <img src="images/InternBuddyLogo.png" alt="Logo" width="40" height="40" style="vertical-align:middle;">
    InternBuddy User Guide
</h1>

## Welcome to InternBuddy!

Calling out to all [STEM](#stem) students!

Navigating the internship application process can be overwhelming &ndash; from managing multiple applications to staying on top of deadlines and requirements, all while balancing your schoolwork and other commitments. That's where **InternBuddy** comes in!

![basic view](images/basicNoCommand.png)

Introducing **InternBuddy** &ndash; your all-in-one solution for seamless internship application management! Whether you're just beginning your search or juggling multiple opportunities, **InternBuddy** offers an intuitive, user-friendly platform that makes it easy to keep track of the companies you're interested in, the stages of your applications, and essential information to help you stay organized and focused.

This guide provides step-by-step instructions to help you [start your journey](#how-to-use-our-user-guide) **with InternBuddy**. New users will find everything they need to get up and running quickly, while returning users can refresh their knowledge of InternBuddy's comprehensive features in our [command summary](#command-summary). **InternBuddy** is crafted with usability in mind – easy to learn and master, designed to support you in reaching your career goals.

_Your internship journey just got easier!_

* **The InternBuddy Team**

[go to overview & tutorial](#overview) <br>
[go to command summary & features](#command-summary)

--------------------------------------------------------------------------------------------------------------------

## Table of Contents

Refer to the sidebar if you are on the website.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Overview

**InternBuddy** is an offline desktop application for university students pursuing a STEM degree to manage 
the contacts of companies that they are potentially applying for or have already applied for internships. 

**InternBuddy** allows users to:
* store and manage companies' contact information for various internship applications.
* easily add and remove companies and their various internship applications.
* keep track of information of the applications for the various companies such as name of position, stage of application, and notes about the position.
* and much more!

**InternBuddy** is an [AddressBook](#address-book) optimized for use via a Command Line Interface ([CLI](#cli)) while still having the benefits of a Graphical User Interface ([GUI](#gui)). If you can type fast,
**InternBuddy** can get your company contacts and internship application management tasks done faster than traditional GUI apps.

[back to top](#internbuddy-user-guide)

--------------------------------------------------------------------------------------------------------------------

## How to use our User Guide

First time using **InternBuddy**? Don't worry! We've got you covered! This tutorial will help maximise your usage of **InternBuddy**.
* To get started, refer to the [Quick Start](#quick-start), which will walk you through getting **InternBuddy** all set up!
* Is there a specific section of the User Guide you want to view? Check out the [Table of Contents](#table-of-contents) &ndash; where all the sections are linked nicely!
* Need a quick refresher of the commands available for you? Check out our [Command Summary](#command-summary)!
* For any terms that you are unsure of, the [Glossary](#glossary) might have an explanation for it.
* Is there a burning question you want answers for? The [FAQ](#faq) might have the answer.

Before continuing, here are some important information you need to know about the User Guide:
1. There are 3 different kinds of boxes that provide extra information.
    * *Tip boxes* provide tips or helpful advice on how to use a certain feature found in **InternBuddy**.
   
      <box type="tip" seamless>
   
      **Tip:** This is a tip box.
      </box>

    * *Notice boxes* provide important information that you should take note of.

      <box type="info" seamless>

      **Notice:** This is a notice box.
      </box>

    * *Warning boxes* provide a warning about certain errors that might occur as a precaution for incorrect usage.

      <box type="warning" seamless>

      **Warning:** This is a warning box.
      </box>

2. Words that are in <span style="color:#0d6efd">in blue</span> are hyperlinks. They will redirect you to a different
   part of the User Guide or an external link when you click on them. For instance, [this hyperlink](#internbuddy-user-guide)
   brings you back to the top of the User Guide.

   <box type="tip" seamless>

   **Tip:** If you are viewing this guide on our website, you can hover over links to see where the hyperlink takes you!
   </box>

3. Refer to [Features](#features) for detailed explanations of each feature of **InternBuddy**!
   
   <box type="tip" seamless>

   **Tip:** You can check out the [command summary](#command-summary) that we have provided in the User Guide 
   after you are done looking through the features for an overview of all the commands available for you to use in **InternBuddy**!
   </box>

[back to top](#internbuddy-user-guide)

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer by opening up your terminal application (for macOS it's called `Terminal`, and for Windows: `Command Prompt`), followed by executing `java -version`.

1. Download the latest `internbuddy.jar` file from [our website](https://github.com/AY2425S1-CS2103T-T09-1/tp/releases) (scroll down to assets to find it!).

1. Copy/move the file to the folder you want to use as the _home folder_ for your InternBuddy application.

1. Open a command terminal, run the `cd` command to [change your directory](#cd) to the folder you put the jar file in.

1. Use the `java -jar internbuddy.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/sample.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `list` : Lists all contacts.

    * `add n/Jane Street p/91234567 e/careers@janestreet.com a/Jane Street, block 123, #01-01` : Adds a company named `Company` to the Address Book.

    * `delete 3` : Deletes the 3rd contact shown in the current list.

    * `clear` : Deletes all contacts.

    * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.
1. Make sure to check out the [Glossary](#glossary) for definitions of some vocabulary used in this guide.

[back to top](#internbuddy-user-guide)

--------------------------------------------------------------------------------------------------------------------

## GUI Overview

The blurred image below shows an annotated overview of **InternBuddy's** GUI:

![GUI overview](images/GUIOverview.png)

The **GUI** is divided into five main sections:

1. **Menu Bar**: Contains options like `Help` and `File` for easy access to essential commands.
2. **Command Box**: The area where you can type and execute commands by pressing Enter.
3. **Result Display Box**: Shows the results of your commands and any error messages.
4. **Company List**: Displays the current list of companies in a card-like format, making it easy to view and manage.
5. **Status Bar**: Displays the file path of the loaded data file, alongside our trusty companion, `InternBuddy`, who will be with you throughout your journey.


--------------------------------------------------------------------------------------------------------------------

## Command summary

See below for the summary of all the commands available for you!

<box type="tip" seamless>

**Tip:** You can click on the hyperlinks (blue color) located in the `Action` column of the tables below to bring you
to the relevant commands!
</box>

### Commands for managing Companies

| Action                                                       | Format, Examples                                                                                                                                                                  |
|--------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [**Add**](#adding-a-company-add)                             | `add n/NAME e/EMAIL [p/PHONE_NUMBER] [a/ADDRESS] [t/TAG]…​` <br> e.g., `add n/Google LLC p/22224444 e/careers@google.com a/70 Pasir Panjang Rd, #03-71, 117371 t/tech t/software` |
| [**Delete**](#deleting-a-company-delete)                     | `delete INDEX`                                                                                                                                                                    |
| [**List**](#listing-all-companies-list)                      | `list`                                                                                                                                                                            |
| [**Edit**](#editing-a-company-edit)                          | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/Meta Platforms e/jobs@meta.com`                                                         |
| [**Favourite**](#adding-a-company-to-favourites-fav)         | `fav INDEX`                                                                                                                                                                       |
| [**Unfavourite**](#removing-a-company-from-favourites-unfav) | `unfav INDEX`                                                                                                                                                                     |
| [**Find**](#locating-companies-find)                         | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find Apple Inc`                                                                                                                         |
| [**Reopen**](#reopening-a-company-reopen)                    | `reopen INDEX`                                                                                                                                                                    |

### Commands for managing Applications 

| Action                                                             | Format, Examples                                                                                                          |
|--------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------|
| [**Apply**](#adding-an-application-record-for-a-company-apply)     | `apply INDEX n/NAME d/DESCRIPTION [as/APPLICATION_STATUS]`<br> e.g., `apply 1 n/Software Engineering Intern d/Uses React` |
| [**Update**](#updating-an-application-for-a-company-update)        | `update c/COMPANY_INDEX app/APPLICATION_INDEX as/APPLICATION_STATUS`<br> e.g.,`update c/1 app/1 as/OA`                    |
| [**Withdraw**](#withdrawing-an-application-for-a-company-withdraw) | `withdraw c/COMPANY_INDEX app/APPLICATION_INDEX`<br> e.g., `withdraw c/3 app/1`                                           |
| [**View**](#viewing-detailed-applications-of-a-company-view)       | `view INDEX`                                                                                                              |

### Miscellaneous commands

| Action                                | Format, Examples |
|---------------------------------------|------------------|
| [**Help**](#viewing-help-help)        | `help`           |
| [**Clear**](#clearing-all-data-clear) | `clear`          |
| [**Exit**](#exiting-the-program-exit) | `exit`           |

[back to top](#internbuddy-user-guide)

--------------------------------------------------------------------------------------------------------------------
## Features

Before diving into our features, do note that we set some specifications for naming and such. You might want to read this section if you keep getting an `Invalid command format!` message, or want to find out more intricate details about `InternBuddy`. Otherwise, skip to [add command](#adding-a-company-add) to begin.

<box type="warning" seamless>

**Note about extra parameters:** We advise that you follow the command format of each command **strictly** (some [exceptions](#param-exceptions) apply) to avoid any
unexpected behaviour when running commands. This means you **should not** put extra parameters unless mentioned, and you **should
not** put any parameters or prefixes that are not recognised by the command.

</box>

<box type="info" seamless>

**Important notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/Optiver`.

* Letters preceding the `/` before parameters (if present) are prefixes that the app uses to determine that parameter. It is **case-sensitive** and thus
  must be used exactly as shown. <br>
  e.g. `t/` and `T/` will be read as different prefixes and thus cannot be used in place of another. 

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/Optiver t/financial` or as `n/Optiver`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Commands are **case-sensitive**, meaning they must be typed exactly as shown. <br>
  e.g. to use the `add` command to add a company, type `add n/Tencent e/tencent@gmail.com`. Variations like `ADD ...`, `Add ...`, or `adD ...` will not work.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* A Company Status is the first coloured tag under the company's name. It will be set to `INTERESTED` initially, then to `APPLIED` when an Application to the company is made, and finally `CLOSED` when the all Applications are withdrawn from that company.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

<box type="info" seamless>

**Important notes about the command parameters:**
* `NAME` can be the company's name, or it can an application's name. InternBuddy only allows alphanumeric characters (letters and numbers only) and spaces to represent it. <br>
  e.g. `7Eleven` is allowed but not `7-Eleven` because `-` is neither a letter nor a number.

* InternBuddy defines `EMAIL` as `local-part@domain`, where `local-part` and `domain` can only consist of alphanumeric characters as well (letters and numbers only). The following special characters `+ - _ .` are allowed in `local-part` but note that:
    1. `local-part` cannot begin or end with any special characters.
    2. `local-part` cannot have consecutive special characters in it.
    3. The only special characters allowed in `domain` are periods (`.`) are allowed in `domain` and follows the 2 rules for `local-part` above.
    4. Each word between periods in `domain` (e.g. `nus` and `edu` in `nus.edu`) must be at least 2 characters long.
  e.g. `abc-123+spam@nus.edu.sg`

* `PHONE_NUMBER` is the company's phone number. It must be at least 3 digits long, consist only of numbers, and cannot include the `+XXX` country code prefix.
  e.g. `85092323`, `0122345677`
 
* `TAG` acts like a label or a category that you assign to a company, it must be a single word and use only alphanumeric characters, meaning no spaces or special symbols.

* `INDEX`, `COMPANY_INDEX`, `APPLICATION_INDEX` represents indexes of items on various lists and must be a positive integer.

* `APPLICATION_STATUS` can only take the values `APPLIED`, `OA`, `INTERVIEWED`, `OFFERED`, `ACCEPTED`, `REJECTED`.
</box>

--------------------------------------------------------------------------------------------------------------------

### Company commands

#### Adding a company: `add`

Adds a company to the address book.

Format: `add n/NAME e/EMAIL [p/PHONE_NUMBER] [a/ADDRESS] [t/TAG]…​`

* Adds a company with the corresponding `NAME` and `EMAIL` to the list of companies.

<box type="tip" seamless>

**Tip 1:** A company can have any number of tags (including 0) <br>
**Tip 2:** Tags can be a useful way to categorise different companies into various groups! <br>
**Tip 3:** A company requires a name and email at the minimum. Other parameters like `PHONE_NUMBER`, `ADDRESS` and `TAG` 
can be added to company later on using the `edit` command.
</box>

<box type="info" seamless>

**Note:** 
* You cannot add two companies with the same `NAME`. (even if the letter casing is different). <br>
However, whitespaces between words of names will be treated as different names (eg: `face book` is not the same as `face     book`).
* `TAG`s are **case-insensitive** (eg: `tech` and `Tech` will be seen as different tags).
* Due to the nature of prefix commands, company names are restricted to alphanumeric characters (letters and numbers) and spaces only
</box>

Examples:
* `add n/Apple e/contact@apple.com`
* `add n/Netflix e/contact@netflix.com p/4085403700 a/100 Winchester Circle, Los Gatos, CA`
* `add n/Google LLC t/FAANG e/contact@google.com p/1234567 t/tech`

![add a company](images/addCommand.png)
> Result after executing `add n/Netflix e/contact@netflix.com p/4085403700 a/100 Winchester Circle, Los Gatos, CA`

[back to command summary](#command-summary) <br>
[back to features](#features) <br>
[back to company commands](#company-commands)

--------------------------------------------------------------------------------------------------------------------

#### Deleting a company: `delete`

Deletes the specified company from the address book.

Format: `delete INDEX`

* Deletes the company at the specified `INDEX`.
* The index refers to the index number shown in the displayed company list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd company in the address book (provided that there are at least 2 companies in your list).
* `find Apple` followed by `delete 1` deletes the 1st company in the results of the `find` command.

[back to command summary](#command-summary) <br>
[back to features](#features) <br>
[back to company commands](#company-commands)

--------------------------------------------------------------------------------------------------------------------

#### Listing all companies: `list`

Shows a list of all companies in the address book.

Format: `list`

[back to command summary](#command-summary) <br>
[back to features](#features) <br>
[back to company commands](#company-commands)

--------------------------------------------------------------------------------------------------------------------

#### Editing a company: `edit`

Edits an existing company in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the company at the specified `INDEX`. The index refers to the index number shown in the displayed company list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the company will be removed i.e adding of tags is not cumulative.

<box type="tip" seamless>

**Tip 1:** InternBuddy will show the changes you have made to the selected company in the results box, so that you can verify that you
have made the right edits! <br>
**Tip 2:** You can remove all the tags from a company by typing `t/` without specifying any `TAG`s after it.
</box>

<box type="info" seamless>

**Note about `edit` command**:
* Currently, InternBuddy does not check if the parameters you provide are exactly the same as the existing parameters for
the selected company. So please be mindful when entering your inputs, especially if you are making small changes (eg: `PHONE: 98765432 -> 98675432`).
* If you are current in the filtered view of a `find` or `view` command, an execution of `edit` command will return the application
  view to the full list of companies. Hence, please be mindful when running consecutive edits to prevent accidental changes to wrong company.
*  A company's status is updated based on your applications to it, so you cannot manually change the status using the `edit` command.
  </box>

Examples:
*  `edit 1 p/91234567 e/company@example.com` Edits the phone number and email address of the 1st company to be `91234567` and `company@example.com` respectively.
*  `edit 2 n/Goggle t/` Edits the name of the 2nd company to be `Goggle` and clears all existing tags.

![edit company details](images/editCommand.png)
> Result after executing `edit 1 p/91234567 e/company@example.com`

[back to command summary](#command-summary) <br>
[back to features](#features) <br>
[back to company commands](#company-commands)

--------------------------------------------------------------------------------------------------------------------

#### Adding a company to favourites: `fav`

Labels an existing company as a favourite.

Format: `fav INDEX`

* Sets the favourite field of company at the specified `INDEX` as `true`. The index refers to the index number shown in the displayed company list. The index **must be a positive integer** 1, 2, 3, …​

* When a company is marked as favourite, it will be displayed on top of the list, together with other favourite companies. This makes it easier to keep track of important companies.

<box type="tip" seamless>

**Tip 1:** Favourited companies have a filled star icon to the right of their name while unfavourited ones have an unfilled star instead.
**Tip 2:** Favourited companies will always be visible at the top of the list (provided it is not hidden by other commands such as `find`).
</box>

![FavouriteACompany](images/favCommand.png)
> Result after executing `fav 1`

[back to command summary](#command-summary) <br>
[back to features](#features) <br>
[back to company commands](#company-commands)

--------------------------------------------------------------------------------------------------------------------

#### Removing a company from favourites: `unfav`

Unlabels an existing company as a favourite.

Format: `unfav INDEX`

* Sets the favourite field of company at the specified `INDEX` as `false`. The index refers to the index number shown in the displayed company list. The index **must be a positive integer** 1, 2, 3, …​

[back to command summary](#command-summary) <br>
[back to features](#features) <br>
[back to company commands](#company-commands)

--------------------------------------------------------------------------------------------------------------------

#### Locating companies: `find`

Finds companies whose names, applications, or tags contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `inc` will match `Inc`
* The order of the keywords does not matter. e.g. `Ltd Pte` will match `Pte Ltd`
* Partial keywords will be matched e.g. `Inc` will match `Incorporated`
* Companies matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Tech Bro` will return `Good Tech`, `Bro Inc`

Examples:
* `find Inc` returns `inc`, `Incorporated` and `Apple Inc`
* `find apple facebook` returns `Apple`, `Facebook`<br>
  ![result for 'find apple facebook'](images/findAppleFacebookResult.png)

* `find hardware SWE` returns `Google`, `Apple` (matched from the applications!) <br>
  ![result for 'find hardware SWE'](images/findhardwareSWEResult.png)

[back to command summary](#command-summary) <br>
[back to features](#features) <br>
[back to company commands](#company-commands)

--------------------------------------------------------------------------------------------------------------------

#### Reopening a company: `reopen`

Changes the status of a company from `CLOSED` to `INTERESTED`

Format: `reopen INDEX`
* The index refers to the index number shown in the displayed company list. The index **must be a positive integer** 1, 2, 3, …​
* The company at the provided index **must have status** `CLOSED`.
* To use the `reopen` command, the company at the `INDEX` provided must have status `CLOSED`, which will subsequently become `INTERESTED`.

[back to command summary](#command-summary) <br>
[back to features](#features) <br>
[back to company commands](#company-commands)

--------------------------------------------------------------------------------------------------------------------

### Application commands

#### Adding an application record for a company: `apply`

Adds an internship application record to an existing company in the address book.

Format: `apply INDEX n/NAME d/DESCRIPTION [as/APPLICATION_STATUS]`

* Adds an application record for the company at the specified `INDEX`. The index refers to the index number shown in the displayed company list. The index **must be a positive integer** 1, 2, 3, …​
* `APPLICATION_STATUS` can only take the values `APPLIED`, `OA`, `INTERVIEWED`, `OFFERED`, `ACCEPTED`, `REJECTED`.
  and will be `APPLIED` if not specified.

<box type="tip" seamless>

**Tip 1:** applying to a company automatically changes the company's status to `APPLIED`. (This is not the same as `APPLICATION_STATUS`)
**Tip 2:** You can use the `DESCRIPTION` field to add additional information about the company that you would like to track, such as skills required for the
position.
</box>

Examples:
* `apply 1 n/Software Engineer Intern d/Requires knowledge of ReactJS and ExpressJS`
* `apply 2 n/Product Management Intern d/Requires Figma as/OA`
* `apply 3 n/Devops Engineer Intern d/Requires knowledge in networks as/OFFERED`

![create application](images/applyCommand.png)
> Result after executing `apply 2 n/Product Management Intern d/Requires Figma as/OA`

[back to command summary](#command-summary) <br>
[back to features](#features) <br>
[back to application commands](#application-commands)

--------------------------------------------------------------------------------------------------------------------

#### Updating an application for a company: `update`

Updates the application status of an application for an existing company in the address book.

Format: `update c/COMPANY_INDEX app/APPLICATION_INDEX as/APPLICATION_STATUS`

* Updates the application status of application record numbered `APPLICATION_INDEX` for the company at the specified `COMPANY_INDEX` to `APPLICATION_STATUS`.
The index refers to the index number shown in the displayed company list. The index **must be a positive integer** 1, 2, 3, …​
* `APPLICATION_STATUS` can only take the values `APPLIED`, `OA`, `INTERVIEWED`, `OFFERED`, `ACCEPTED`, `REJECTED`.

<box type="info" seamless>

**Note**: Currently, InternBuddy does not check if the `APPLICATION_STATUS` you provide is different from the current value. Therefore, please
check that you have put the correct `APPLICATION_STATUS` to ensure that you correctly update it.
</box>

![updated application](images/updateCommand.png)
> Result after executing `update c/1 app/1 as/OFFERED`

[back to command summary](#command-summary) <br>
[back to features](#features) <br>
[back to application commands](#application-commands)

--------------------------------------------------------------------------------------------------------------------

#### Withdrawing an application for a company: `withdraw`

Removes an internship record for an existing company in the address book.

Format: `withdraw c/COMPANY_INDEX app/APPLICATION_INDEX`

* Removes the application record numbered `APPLICATION_INDEX` for the company at the specified `COMPANY_INDEX`.
The index refers to the index number shown in the displayed company list. The index **must be a positive integer** 1, 2, 3, …​

<box type="tip" seamless>

**Tip:** withdrawing all applications from a company automatically changes the company's status to `CLOSED`.
</box>

![withdrawn application](images/withdrawCommand.png)
> Result after executing `withdraw c/2 app/1`

[back to command summary](#command-summary) <br>
[back to features](#features) <br>
[back to application commands](#application-commands)

--------------------------------------------------------------------------------------------------------------------

#### Viewing detailed applications of a company: `view`

Displays a specified company with all its application details.

Format: `view INDEX`

* Shows the application details of the company at the specified `INDEX`. The index refers to the index number shown in the displayed company list. The index **must be a positive integer** 1, 2, 3, …​

<box type="tip" seamless>

**Tip:** By default, only partial application details are shown to the user not shown to reduce clutter. Use `view` if you want to see full details about the applications of
a given company.
</box>

![view application descriptions](images/viewCommand.png)
> Result after executing `view 1`

[back to command summary](#command-summary) <br>
[back to features](#features) <br>
[back to application commands](#application-commands)

--------------------------------------------------------------------------------------------------------------------

### Miscellaneous

#### Clearing all data: `clear`

<span style="color:red">**Deletes all companies and applications**</span> from the address book.

Format: `clear`

<box type="warning" seamless>

**Warning:** Using clear will permanently remove all data saved in the AddressBook
</box>

[back to command summary](#command-summary) <br>
[back to features](#features) <br>
[back to miscellaneous](#miscellaneous)

--------------------------------------------------------------------------------------------------------------------

#### Viewing help: `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

[back to command summary](#command-summary) <br>
[back to features](#features) <br>
[back to miscellaneous](#miscellaneous)

--------------------------------------------------------------------------------------------------------------------

#### Exiting the program: `exit`

Exits the program.

Format: `exit`

[back to command summary](#command-summary) <br>
[back to features](#features) <br>
[back to miscellaneous](#miscellaneous)

--------------------------------------------------------------------------------------------------------------------

#### Saving the data

`InternBuddy` data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

#### Editing the data file

`InternBuddy` data is saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file as a even faster way to interact with your AddressBook.

<box type="warning" seamless>

**Warning:**
If your changes to the data file makes its format invalid, InternBuddy will discard all data and start with an empty data file at the next run.  Hence, it is recommended to create a backup of the file before editing it.<br>
Furthermore, certain edits can cause InternBuddy to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

[back to command summary](#command-summary) <br>
[back to features](#features) <br>
[back to miscellaneous](#miscellaneous) <br>
[back to top](#internbuddy-user-guide) <br>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

**Q**: Must I install Java `17` or above to use InternBuddy? <br>
**A**: Yes, InternBuddy uses libraries implemented in Java `17` or above, it will not work without it.

**Q**: <a id="param-exceptions"></a>Do I have to follow the order of parameters specified by the commands? <br>
**A**: If the parameter has a prefix (eg: `n/NAME`), you can specify them in any order you like! (eg: `add n/NAME e/EMAIL` and `add e/EMAIL n/NAME` are both allowed) <br>
However, parameters with no prefix (eg: `INDEX`) must appear as the first parameter after the command. (eg: `edit INDEX n/NAME` is valid but `edit n/NAME INDEX` is not)

**Q**: How do I add a new company with multiple tags? <br>
**A**: To add a company with multiple tags, use the add command with multiple t/ tags. Example: `add n/Google LLC e/contact@google.com t/tech t/FAANG t/software`

**Q**: Can I edit a company’s details? <br>
**A**: Yes, you can edit any company's details using the edit command followed by the index of the company. <br> For example: `edit 2 n/Apple Inc e/careers@apple.com p/12345678`. However, please note that optional fields like `PHONE_NUMBER` and `ADDRESS` cannot be removed once added. If you wish to remove these fields, you will need to delete the company using the `delete` command and then re-add it without those fields.

**Q**: Can I delete all contacts at once? <br>
**A**: Yes, use the `clear` command to delete all contacts in your address book. Be cautious, as this action is <span style="color:red">irreversible</span>.

**Q**: Can I add multiple identical applications to the same company? <br>
**A**: Yes, of course you can! This is not unlike a real-life scenario, where you could possibly apply for the same role multiple times, to the same company. However many times you apply to the same company, **InternBuddy** is here for you! 

[back to top](#internbuddy-user-guide)

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.
3. **If running `java -jar internbuddy.jar` gives errors**, such as terminal displaying an error, ensure that Java 17 or higher is installed. Run `java -version` to check your version. For Mac users, check if you have followed the advisory given [here](https://nus-cs2103-ay2425s1.github.io/website/admin/programmingLanguages.html).
4. **Indicating multiple `APPLICATION_STATUS` in `apply` command**, if you specify more than one `APPLICATION_STATUS` (eg: `apply n/SWE Intern d/Requires Java as/APPLIED as/OA`) will result in InternBuddy in applying the last `APPLICATION_STATUS` (`OA` in the given example); provided that the last `APPLICATION_STATUS` is valid.
5. **Indicating multiple parameters in `update` command**, if you specify multiple parameters with the same prefix, only the right most parameter will be used by InternBuddy. <br>
   For instance, `update c/1 app/2 as/OA c/2 app/3 app/4 as/REJECTED` will be read the same as `update c/2 app/4 as/REJECTED` and will run if the read values are valid. 
6. `INDEX`, `COMPANY_INDEX`, and `APPLICATION_INDEX` parameters are designed to support managing up to **1000 companies**, each with **1000 applications**. Entering values beyond this range (e.g., greater than 1000) may lead to **undefined behavior**, so please ensure indexes stay within the specified bounds.
7. **The error message** for the `EMAIL` field in a company's details does not specify the exact reason for format violations (e.g., each segment of the `domain` between periods must be at least 2 characters long). For detailed formatting rules and examples, please refer to the notes in [features](#features).
8. `InternBuddy` **currently accepts invalid phone numbers**, such as those exceeding 15 digits. Users who enter an incorrect phone number by mistake can correct it using the `edit` command.
9. **Typo in error message for `withdraw`**, if you try to withdraw an application from a company with no applications, the message displayed is: `NAME has no applications to update!`, when it should be `NAME has no applications to withdraw!`.
10. `InternBuddy` **currently has no limit on the tag length.** However, with the default window size, the tag may overrun the screen if it exceeds 62 characters, causing the favourites star to no longer remain visible. If a tag length longer than 62 characters is required, adjustments to the window size can be made accordingly.

[back to top](#internbuddy-user-guide)

--------------------------------------------------------------------------------------------------------------------

## Glossary
- <a id="address-book"/>**Address Book**: A digital record or collection of companies or contacts managed by the InternBuddy app.
- **Application Record**: A record associated with a company indicating an internship or job application status, such as "APPLIED," "INTERVIEWED," or "REJECTED."
- **APPLIED / OA (Online Assessment) / INTERVIEWED / OFFERED / ACCEPTED / REJECTED**: The various statuses that can describe an application’s progress in the hiring process within InternBuddy.
- **Backup**: A copy of the data file created to prevent loss of information. The backup can be used to restore the AddressBook in case of accidental data loss.
- <a id="cd"/>**`cd`**: Which stands for **c**hange **d**irectory, is a commonly used command in many command terminals (such as Windows Command Prompt or the MacOS Terminal). It allows users to navigate the terminal to a different directory or folder within their system.
- <a id="cli"/>**CLI** (Command Line Interface): A text-based interface used to interact with the application by typing commands.
- **Command**: A specific instruction typed in the CLI to perform an action within the InternBuddy application.
- **Company**: Refers to an entity in the AddressBook. We refer to any contact in our AddressBook as Company.
- <a id="gui"/>**GUI** (Graphical User Interface): A visual interface that allows users to interact with the application through graphical elements such as buttons, icons, and windows.
Users can change their directory by typing `cd path/to/directory` into their terminal of choice.
- **Home Folder**: The directory where InternBuddy stores its data and related files on your computer.
- **Index**: A number used to identify the position of a company or application in a list. InternBuddy commands often require an index to reference a specific company or application.
- **JSON** (JavaScript Object Notation): A lightweight data-interchange format that is easy for humans to read and write and for machines to parse and generate. InternBuddy uses JSON to store its data files.
- **JSON File Location**: The file path where InternBuddy stores its data, which can be manually edited or transferred to another computer.
- **Parameter**: Information or input that must be provided along with a command. In the guide, parameters are represented in UPPER_CASE (e.g., `n/NAME`).
- <a id="stem"/>**STEM**: An acronym for the four related fields of study; **S**cience, **T**echnology, **E**ngineering, and **M**athematics.
- **Tag**: A label that can be added to a company to classify or organize it (e.g., `t/tech`, `t/software`).

[back to top](#internbuddy-user-guide)
