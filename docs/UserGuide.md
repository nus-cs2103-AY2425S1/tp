---
layout: page
title: User Guide
---

**Address Book Command Line Interface (ABCLI)** is a desktop application tailored for **real estate agents** who value the speed and efficiency of managing workflows through a **Command Line Interface (CLI)**. With the added support of a Graphical User Interface (GUI) for visual clarity, ABCLI empowers agents to handle contacts, schedule meetings, and organize property details significantly faster than traditional GUI-only applications. If you are a fast-typing real estate agent, ABCLI is designed to keep up with your speed, boosting your productivity.

## Table of Contents

* [Application Overview](#application-overview)
* [Quick Start](#quick-start)
* [Features](#features)
* [General](#general)
  * [Viewing help : `help`](#general-help)
  * [Exiting the program : `exit`](#general-exit)
  * [Saving the data](#general-save)
  * [Editing the data file](#general-edit)
  * [Switching parser modes:  `switch`](#general-switch)
* [Buyers](#buyers)
  * [Viewing all buyers : `view`](#view-buyer)
  * [Adding a buyer : `add`](#add-buyer)
  * [Editing a buyer : `edit`](#edit-buyer)
  * [Finding buyers : `find`](#find-buyer)
  * [Deleting a buyer : `delete`](#delete-buyer)
  * [Clearing all buyers : `clear`](#clear-buyer)
* [Meet Ups](#meet-ups)
  * [Viewing all meet-ups : `view`](#view-meetup)
  * [Adding a meet-up : `add`](#add-meetup)
  * [Editing a meet-up : `edit`](#edit-meetup)
  * [Finding meet-up : `find`](#find-meetup)
  * [Deleting a meet-up : `delete`](#delete-meetup)
  * [Clearing all meet-ups : `clear`](#clear-meetup)
* [Properties](#properties)
  * [Viewing all properties : `view`](#view-property)
  * [Adding a property : `add`](#add-property)
  * [Editing a property : `edit`](#edit-property)
  * [Finding properties : `find`](#find-property)
  * [Deleting a property : `delete`](#delete-property)
  * [Clearing all properties : `clear`](#clear-property)
* [FAQ](#faq)
* [Known issues](#known-issues)
* [Command summary](#command-summary)

--------------------------------------------------------------------------------------------------------------------

## How to use the User Guide

This User Guide is designed to help you navigate and make the most of ABCLI. It introduces the application's features and guides you from getting started to mastering each function, all without requiring any prior knowledge. Explore each section below:

1. **[Table of Contents](#Table-of-Contents)**: Located at the start of the guide, this is useful whenever you want to locate a specific section or topic quickly.

2. **[Application Overview](#Application-Overview)**: Refer to this section if you are new to ABCLI or want to understand its purpose and primary capabilities. It is ideal for getting a high-level understanding of how ABCLI can support your real estate tasks.

3. **[Quick Start](#Quick Start)**: Use this section if you are setting up ABCLI for the first time. It provides step-by-step instructions to get the app running.

4. **[Features](#Features)**: This is your go-to section as you start using ABCLI. Divided into [General](#General), [Buyers](#Buyer), [Meet Ups](Meet Ups), and [Properties](Properties), it details how to use each feature, including command formats, tips, and examples. Refer here whenever you need guidance on operating any part of the app.

5. **[FAQ](#FAQ)**: Consult this section if you encounter issues or have questions about ABCLI’s functionality. It provides answers to common questions and can help resolve typical challenges.

6. **[Known Issues](#Known-Issues)**: If ABCLI behaves unexpectedly, refer to this section to see if it’s a known issue. This section lists potential problems and workarounds, helping you troubleshoot effectively.

7. **[Command Summary](#Command-Summary)**: Ideal as a quick reference guide, this section provides an overview of all available commands. Use it when you need to check the format or options for any command without going into full detail.

8. **[Glossary](#Glossary)**: If you encounter any unfamiliar terms while using ABCLI or reading the guide, refer to the Glossary. It defines key terms to ensure clarity as you navigate the app.

### Display Boxes
Throughout the guide, you will see display boxes with additional information:

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
 Look out for these when you want to enhance your user experience or learn best practices.
</div>

<div markdown="block" class="alert alert-info">
**:information_source: Key Insights**
 Refer to these boxes for extra insights and context about ABCLI features.
</div>

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
 Pay attention to these warnings to avoid potential pitfalls or issues.
</div>

<div markdown="span" class="alert alert-primary">:rocket: **Future Enhancement:**
 These boxes highlight features that are planned for future releases, keeping you informed about upcoming capabilities.
</div>

--------------------------------------------------------------------------------------------------------------------

<a id="application-overview"></a>
<span style="font-size: 30px; font-weight: bold; color: #d67d3e">Application Overview</span>

ABCLI is divided into three core modes, each dedicated to a key aspect of a real estate agent workflows:

* **[Buyer Mode](#buyers)**: Organize prospective buyers with details like budget, contact info, and tags for effective monitoring and follow-up.

* **[Meet Up Mode](#meet-ups)**: Schedule and manage meet-ups with buyers, track attendees, and avoid scheduling conflicts.

* **[Property Mode](#properties)**: Store property listings, including landlord details, asking price, and property type for quick and easy references.

These modes allow real estate agents to handle buyers, meet-ups, and property listings at a rapid pace, ensuring an efficient, convenient and integrated workflow for those who thrive on speed.

--------------------------------------------------------------------------------------------------------------------

<a id="quick-start"></a>
<span style="font-size: 30px; font-weight: bold; color: #d67d3e">Quick Start</span>

1. Install Java `17` or above: Ensure that Java 17 or a later version is installed on your computer. You can download it [here](https://www.oracle.com/java/technologies/downloads/#java17).
   * MacOS users can refer to this [set of instructions](https://se-education.org/guides/tutorials/javaInstallationMac.html) for easier installation. 

2. Download the latest ABCLI application: Get the latest `ABCLI.jar` file from the [release page](https://github.com/AY2425S1-CS2103T-F13-2/tp/releases/).  

3. Choose a storage folder: Move the `ABCLI.jar` file to the folder where you want to store and run the application.

4. Open the command terminal:  
    * For Window users, in the folder containing `ABCLI.jar`, right-click the empty space and select “Open in Terminal”.
    * For MacOS users, right-click the home folder, hover over “Services,” and select “New Terminal at Folder”.

5. Run the application: Enter the command `java -jar ABCLI.jar` to start ABCLI.  

6. Verify the GUI: If you are successful in opening the application, a GUI similar to the below should appear in a few seconds.<br>
   ![Ui](images/Ui.png)
<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
**Before you start**, ABCLI comes pre-populated with example data. We encourage you to practice the commands on this sample data to get familiar with the app. Once you are comfortable, you can use the `clear` command in each mode to reset the data in the respective modes.
</div>

7. Enter commands: Type a command in the command box and press Enter to execute it. For example, typing `help` and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `view` : Lists all buyers.

   * `add n/John Doe p/98765432 e/johnd@example.com b/120000` : Adds a buyer contact named `John Doe` to the Buyer list.

   * `delete 3` : Deletes the 3rd buyer shown in the current list.

   * `clear` : Deletes all buyers.

   * `exit` : Exits the app.

8. Explore available features: Refer to the [Features](#features) section below for details on each command.

--------------------------------------------------------------------------------------------------------------------

<a id="features"></a>
<span style="font-size: 30px; font-weight: bold; color: #d67d3e">Features</span>

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/relocating` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/relocating`, `t/relocating t/upgrading` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `view`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

<a id="general"></a>
<span style="font-size: 30px; font-weight: bold; color: #e0da87">General</span>

<a id="general-help"></a>
<span style="font-size: 20px; font-weight: bold; color: #e0da87">Viewing help : `help`</span>

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

<a id="general-exit"></a>
<span style="font-size: 20px; font-weight: bold; color: #e0da87">Exiting the program : `exit`</span>

Exits the program.

Format: `exit`

<a id="general-save"></a>
<span style="font-size: 20px; font-weight: bold; color: #e0da87">Saving the data</span>

Data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

<a id="general-edit"></a>
<span style="font-size: 20px; font-weight: bold; color: #e0da87">Editing the data file</span>

All data is saved automatically as a JSON file within the storage folder. Advanced users are welcome to update data directly by editing that data file.

Note: By default, the storage folder is set to a folder named `data` in the home folder.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes one of the fields invalid, the data files will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
If any of the data files, e.g. `buyerlist.json` is deleted, it will create a new data file with pre-populated data, e.g. it will create a new `buyerlist.json` with prepopulated data.<br>
Furthermore, certain edits can cause the JSON files to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

<a id="general-switch"></a>
<span style="font-size: 20px; font-weight: bold; color: #e0da87">Switching parser modes : `switch`</span>

Switches the parser mode to the specified parser mode.

Format: `switch PARSER_MODE`

* Switches the parser mode to the specified `PARSER_MODE`.
* The parser mode takes 3 types:
* `b` for [buyers](#buyers)
* `m` for [meet-ups](#meet-ups)
* `p` for [properties](#properties)
* The default parser mode is set to `b`.

Examples:
* Upon entering the application, the parser mode is set to `b`.
* `switch` followed by `m` switches the parser mode to meet-ups.

![SwitchBuyerToMeetUp](images/SwitchBuyerToMeetUpSequence.png)

<div markdown="block" class="alert alert-info">

**:information_source: Notes about parser mode-switching:**<br>

* The mode before the switch can be seen at the top bar, where it says `Viewing: Buyers`, after the switch it changes to `Viewing: Meet Ups`.

* The view also automatically changes from showing `Buyers` to `Meet Ups` after the switch command, this view will also change appropriately when switching to `b` or `p`.

* The mode is important for command execution, e.g. to `add` a `Buyer`, you need to be in the `b` mode, to `add` a `MeetUp`, you need to be in the `m` mode, etc.
</div>

<a id="buyers"></a>
<span style="font-size: 30px; font-weight: bold; color: #43839c">Buyers</span>

Buyers represent your clients interested in purchasing properties. The buyer list allows you to keep track of each buyer's essential details such as name, phone number, email, and property budget. Additionally, you can organize and personalize each buyer’s profile by assigning multiple tags. For instance, if a client, `Bernice Yu`, expresses interest in relocating, you could add the tag `relocating` to her contact.

![BuyerModeInitialList](images/BuyerModeInitialList.png)

<div markdown="block" class="alert alert-info">

**:information_source: These commands are for when the parser is in `b` mode (Buyer mode) see [switching parser modes](#general-switch):**<br>

* Note how the list already contains sample `Buyer`s (if this is the first time using the app)

* Note how the mode is highlighted by `Viewing: Buyers` above the command line

</div>

<a id="view-buyer"></a>
<span style="font-size: 20px; font-weight: bold; color: #43839c">Viewing all buyers : `view`</span>

Shows a list of all buyers in the buyer list.

Format: `view`

<a id="add-buyer"></a>
<span style="font-size: 20px; font-weight: bold; color: #43839c">Adding a buyer: `add`</span>

Adds a buyer to the buyer list.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL b/BUDGET [t/TAG]…​`

* New buyers must have unique names and must not be duplicate names of existing buyers.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A buyer can have any number of tags (including 0)
</div>

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
Buyer's budget cannot exceed `9223372036854775807`. Refer to [known issues](#known-issues) for more information.
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com b/100,000`
* `add n/Betsy Crowe t/urgent e/betsycrowe@example.com b/7,000,000 p/91234567 t/referred`

<a id="edit-buyer"></a>
<span style="font-size: 20px; font-weight: bold; color: #43839c">Editing a buyer : `edit`</span>

Edits an existing buyer in the buyer list.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [b/BUDGET] [t/TAG]…​`

* Edits the buyer at the specified `INDEX`. The index refers to the index number shown in the displayed buyer list. 
* The index refers to the index number shown in the **displayed buyer list**.
* The index must be a positive integer: 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the buyer will be removed i.e adding of tags is not cumulative.
* You can remove all the buyer’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/81234567 e/johndoe@example.com` Edits the phone number and email budget of the 1st buyer to be `81234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd buyer to be `Betsy Crower` and clears all existing tags.

<a id="find-buyer"></a>
<span style="font-size: 20px; font-weight: bold; color: #43839c">Finding buyers: `find`</span>

Finds existing buyers in existing buyer list based on keywords.

Format: `find n/KEYWORD [MORE_KEYWORDS]`

* Only the name is searched.
* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only full words will be matched e.g. `Han` will not match `Hans`
* Buyers matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

<div markdown="span" class="alert alert-primary">:rocket: **Future Enhancement:**
Planned update will allow searches using other attributes, such as: `b/BUDGET`, `p/PHONE`, `e/EMAIL`, and `t/TAG`.
</div>

Examples:
* `find n/John` returns `john` and `John Doe`
* `find n/alex david` returns `Alex Yeoh`, `David Li`<br>

![FindBuyerSequence](images/FindBuyerSequence.png)

<a id="delete-buyer"></a>
<span style="font-size: 20px; font-weight: bold; color: #43839c">Deleting a buyer : `delete`</span>

Deletes the specified buyer from the buyer list.

Format: `delete INDEX`

* Deletes the buyer at the specified `INDEX`.
* The index refers to the index number shown in the **displayed buyer list**.
* The index must be a positive integer: 1, 2, 3, …​
* The index cannot exceed the displayed list's range.

Examples:
* `view` followed by `delete 2` deletes the 2nd buyer in the displayed buyer list.
* `find Betsy` followed by `delete 1` deletes the 1st buyer in the displayed results of the `find` command.

<a id="clear-buyer"></a>
<span style="font-size: 20px; font-weight: bold; color: #43839c">Clearing all buyers : `clear`</span>

Clears all entries from the buyer list.

Format: `clear`

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
Using the clear command will delete all the buyers from the BuyerList, and there is no way to undo this, you may lose your data permanently.
</div>

<a id="meet-ups"></a>
<span style="font-size: 30px; font-weight: bold; color: #b44cc7">Meet Ups</span>

Meet-up represents any scheduled appointment with your clients, allowing you to record essential details, including the subject of the meeting, additional info, its start and end date-times (`From` and `To`), and the buyers involved. Additionally, ABCLI will highlight any overlapping meet-ups to help you avoid scheduling conflicts.

![MeetUpModeInitialList](images/MeetUpModeInitialList.png)

<div markdown="block" class="alert alert-info">

**:information_source: These commands are for when the parser is in `m` mode (MeetUp mode) see [switching parser modes](#general-switch):**<br>

* Note how the list already contains sample `Meet Up`s (if this is the first time using the app)

* Note how the mode is highlighted by `Viewing: Meet Ups` above the command line

* Note how each meet-up contains buyer, if the buyer exists in the BuyerList, it will be marked as purple, else red. The matching here is done by case-sensitive matching, e.g. `Alex Yeoh` will only be purple if there is `Alex Yeoh` in the Buyer List, not `Alex yeoh` or `alex yeoh` or `Alex`.

* However, if an existing buyer's name is edited, the buyer shown in meet-ups will not update accordingly and will just change from purple to red, e.g. in the image above, if the name of `Alex Yeoh` was changed in the BuyerList to `Alex Yeo`, in the meet-up mode, both meetings would still display `Alex Yeoh` but in red now.

</div>

<a id="view-meetup"></a>
<span style="font-size: 20px; font-weight: bold; color: #b44cc7">Viewing all meet-ups : `view`</span>

Shows a list of all meet-ups in the meet-up list.

Format: `view`

<a id="add-meetup"></a>
<span style="font-size: 20px; font-weight: bold; color: #b44cc7">Adding a meet-up: `add`</span>

Adds a meet-up to the meet-up list.

Format: `add s/MEETUP_SUBJECT i/MEETUP_INFO f/MEETUP_FROM t/MEETUP_TO [n/BUYER_NAME]…​`

* New meet-ups must have at least one unique non-duplicate aspect from these three fields: MEETUP_SUBJECT, MEETUP_FROM, MEETUP_TO. Else, it will be marked as a duplicate meet-up.

* MEETUP_FROM and MEETUP_TO fields should follow the format `YYYY-MM-DD HH:MM`.

* MEETUP_TO must be a date/time that is later than MEETUP_FROM.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
When adding the MEETUP_FROM or MEETUP_TO, the `DD` parameter will take in any 2-digit number from 01 to 31. However, in some cases, such as February or April, the date 31 doesn't exist, in this case, instead of rejecting the input, the meet-up will be added but the date will be changed to the closest valid date in the same month. e.g. `2024-02-31 23:59` will create `2024-02-29 23:59`, `2024-04-31 12:00` will create `2024-04-30 12:00`, but `2024-04-32 12:00` will give an error since 32 is not a valid `DD` input.
</div>

* Buyers that exist in buyer list will be marked as purple, while those that do not will be marked as red, for more details see [the notes in MeetUp](#meet-ups)

Examples:
* `add s/Discuss work plans i/Meet with Alex and David to discuss the March Project f/2024-02-03 14:00 t/2024-02-03 15:30 n/Alex Yeoh n/David Li `

Meet Ups with clashing timings will be displayed in red. Otherwise, the default display colour for timing is green.

![MeetUpClash](images/MeetUpClash.png)

<a id="edit-meetup"></a>
<span style="font-size: 20px; font-weight: bold; color: #b44cc7">Editing a meet-up : `edit`</span>

Edits an existing meet-up in the meet-up list.

Format: `edit INDEX [s/MEETUP_SUBJECT] [i/MEETUP_INFO] [f/MEETUP_FROM] [t/MEETUP_TO] [n/BUYER_NAME]…​`

* Edits the meet-up at the specified `INDEX`.
* The index refers to the index number shown in the **displayed meet-up list**. 
* The index must be a positive integer 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing buyers, the existing buyers will be removed i.e adding of buyers is not cumulative
* You can remove all the meet-up's buyers by typing `n/` without specifying any tags after it.

Examples:
*  `edit 1 i/Meet with Johnny to show him houses. f/2024-10-28 10:00 t/2024-10-28 12:00` Edits the info, meet-up start time, and meet-up end time of the 1st meet-up to be `Meet with Johnny to show him houses.`, `2024-10-28 10:00` and `2024-10-28 12:00` respectively.

<a id="find-meetup"></a>
<span style="font-size: 20px; font-weight: bold; color: #b44cc7">Finding meet-ups: `find`</span>

Finds meet-ups whose meet-up names contain any of the given keywords.

Format: `find s/KEYWORD`

* The search is case-insensitive. e.g `meet` will match `Meet`
* Only the meet-up name is searched.
* Keyword will be matched to exact words, e.g. `meet` will not match `meeting`

Examples:
* `find s/Meet` returns `Meet up with Jack to discuss property prices` and `Go to MBS for Jane's meet`
* `find s/Sales` returns `Sales Meeting`

![FindMeetUpSequence](images/FindMeetUpSequence.png)

<a id="delete-meetup"></a>
<span style="font-size: 20px; font-weight: bold; color: #b44cc7">Deleting a meet-up : `delete`</span>

Deletes the specified meet-up from the meet-up list.

Format: `delete INDEX`

* Deletes the meet-up at the specified `INDEX`.
* The index refers to the index number shown in the **displayed meet-up list**.
* The index must be a positive integer 1, 2, 3, …​
* The index cannot exceed the displayed list’s range.

Examples:
* `view` followed by `delete 2` deletes the 2nd meet-up in the meet-up list.
* `find meet` followed by `delete 1` deletes the 1st meet-up in the results of the `find` command.

<a id="clear-meetup"></a>
<span style="font-size: 20px; font-weight: bold; color: #b44cc7">Clearing all meet-ups : `clear`</span>

Clears all entries from the meet-up list.

Format: `clear`

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
Using the clear command will delete all the meet-ups from the MeetUpList, and there is no way to undo this, you may lose your data permanently.
</div>

<a id="properties"></a>
<span style="font-size: 30px; font-weight: bold; color: #56d676">Properties</span>

Properties represent each property in your portfolio. The property list helps you keep track of each property's details such as the landlord's name and phone number, as well as the property's address, type (e.g. apartment, landed house, etc) and asking price.

![PropertyModeInitialList](images/PropertyModeInitialList.png)

<div markdown="block" class="alert alert-info">

**:information_source: These commands are for when the parser is in `p` mode (Property mode) see [switching parser modes](#general-switch):**<br>

* Note how the list already contains sample `Property`s (if this is the first time using the app)

* Note how the mode is highlighted by `Viewing: Properties` above the command line

</div>

<a id="view-property"></a>
<span style="font-size: 20px; font-weight: bold; color: #56d676">Viewing all properties : `view`</span>

Shows a list of all properties in the property list.

Format: `view`

<a id="add-property"></a>
<span style="font-size: 20px; font-weight: bold; color: #56d676">Adding a property: `add`</span>

Adds a property to the property list.

Format: `add n/LANDLORD_NAME p/PHONE_NUMBER a/ADDRESS s/ASKING_PRICE t/PROPERTY_TYPE`

* New properties must have unique addresses and must not be duplicate addresses of existing properties.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
Property's asking price cannot exceed `9223372036854775807`. Refer to [known issues](#known-issues) for more information.
</div>

Examples:
* `add n/John p/87152433 a/Paya Lebar s/200,000 t/Condominium`

<a id="edit-property"></a>
<span style="font-size: 20px; font-weight: bold; color: #56d676">Editing a property : `edit`</span>

Edits an existing property in the property list.

Format: `edit INDEX [n/LANDLORD_NAME] [p/PHONE_NUMBER] [a/ADDRESS] [s/ASKING_PRICE] [t/PROPERTY_TYPE]`

* Edits the landlord at the specified `INDEX`. 
* The index refers to the index number shown in the **displayed property list**. 
* The index must be a positive integer 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
*  `edit 1 p/91234567 s/100000` Edits the first property's landlord phone number and its asking price to be `91234567` and `100,000` respectively.
*  `edit 2 n/Betsy Crower` Edits the name of the 2nd property's landlord to be `Betsy Crower`.

<a id="find-property"></a>
<span style="font-size: 20px; font-weight: bold; color: #56d676">Finding properties: `find`</span>

Find existing properties in the property list based on either name or address keywords.

Format: `find n/LANDLORD_NAME` or `find a/ADDRESS`

* The tags used in searching should only be `n/` or `a/`, but not both.
* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only full words will be matched e.g. `Han` will not match `Hans`
* Properties matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `n/Hans Bo` will return property linked with `Hans Gruber`, `Bo Yang`.

Examples:
* `find n/John` returns `john` and `John Doe`
* `find a/Pasir Ris` returns `pasir ris east` and `Pasir Ris West`

![FindPropertySequence](images/FindPropertySequence.png)

<a id="delete-property"></a>
<span style="font-size: 20px; font-weight: bold; color: #56d676">Deleting a property: `delete`</span>

Deletes the specified property from the property list

Format: `delete INDEX`

* Deletes the property at the specified `INDEX`.
* The index refers to the index number shown in the **displayed property list**.
* The index must be a positive integer: 1, 2, 3, ...
* The index cannot exceed the displayed list's range

Examples:
* `view` followed by `delete 4` deletes the 4th property in the displayed property list.
* `find n/Adam` followed by `delete 2` deletes the 2nd property in the displayed results of the find command.

<a id="clear-property"></a>
<span style="font-size: 20px; font-weight: bold; color: #56d676">Clearing all properties : `clear`</span>

Clears all entries from the property list.

Format: `clear`

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
Using the clear command will delete all the properties from the PropertyList, and there is no way to undo this, you may lose your data permanently.
</div>

--------------------------------------------------------------------------------------------------------------------

<a id="faq"></a>
<span style="font-size: 30px; font-weight: bold; color: #baa856">FAQ</span>

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data files (`buyerlist.json`, `meetuplist.json`, and `propertylist.json`) it creates with the respective files that contain your previous data (which should be in the old `buyerlist.json`, `meetuplist.json`, and `propertylist.json`).

**Q**: Where can I find my local data files?<br>
**A**: In the same folder that contains your app `Abcli.jar`, there should be a folder called `data` which contains `buyerlist.json`, `meetuplist.json`, and `propertylist.json`.

**Q**: How do I add MeetUps or Properties? I can only add buyers<br>
**A**: You are likely in the `Buyer Mode` indicated by the `Viewing: Buyers` shown at the top bar, to switch to `MeetUp Mode`, use `switch m`, and to switch to `Property Mode`, use `switch p`. For more details, see [switching parser modes](#general-switch).

**Q**: Is there a separate save command/button, are all my changes auto-saved?<br>
**A**: Any successfully inputted command will save the changes into the data files, thus there is no need to worry about manually saving your changes.

--------------------------------------------------------------------------------------------------------------------

<a id="known-issues"></a>
<span style="font-size: 30px; font-weight: bold; color: #ba6356">Known issues</span>

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

3. **If you input a budget or asking price that exceeds** `9223372036854775807`, the command will silently fail, and no error message will be shown. This is due to exceeding the maximum value for a 64-bit integer. A planned enhancement will add a validation check to prevent input beyond a maximum realistic range.

4. **When inputting a name**, if the name is too long, the interface is unable to display the full name, and it will be truncated, with ellipses (...) representing the truncated part of the name.


4. **Editing/Deleting buyers that are included in Meet-Ups**, editing/deleting a buyer in the BuyerList that is also part of a meet-up in the MeetUpList will not update the buyer shown in the MeetUpList. For example, meet-up `Sales Meeting` has buyer `Alex Yeoh` in the MeetUpList and `Alex Yeoh` is a buyer in the BuyerList, thus `Sales Meeting` shows `Alex Yeoh` in purple (the buyer exists). If you go to Buyer mode and edit `Alex Yeoh` to be something different such as `Alex yeoh`,`alex yeoh`,`alex`, etc, `Sales Meeting` will still show `Alex Yeoh` but in red now (buyer does not exist anymore). Deleting `Alex Yeoh` in the BuyerList will cause the same effect. The name matching between buyers in meet-ups and buyers in the BuyerList is done with exact case-sensitive matching, `Alex Yeoh` will only be purple in the `Sales Meeting` if the BuyerList has a buyer with the exact name `Alex Yeoh`.

--------------------------------------------------------------------------------------------------------------------

<a id="command-summary"></a>
<span style="font-size: 30px; font-weight: bold; color: #d67d3e">Command summary</span>

<table>
    <tr>
        <th>Action</th>
        <th>Buyer</th>
        <th>Meetup</th>
        <th>Property</th>
    </tr>
    <tr>
        <td><b>View</b></td>
        <td><code>view</code>
        <td><code>view</code>
        <td><code>view</code>
    </tr>
    <tr>
        <td><b>Add</b></td>
        <td><code>add n/NAME p/PHONE e/EMAIL b/BUDGET [t/TAG]…​</code><br> e.g., <code>add n/James Ho p/22224444 e/jamesho@example.com a/1200000 t/friend t/colleague</code></td>
        <td><code>add s/SUBJECT i/INFO f/FROM t/TO [a/ADDED_BUYERS]…​</code><br> e.g., <code>add s/Discuss work plans i/Meet with Jason to discuss the March Project a/Alex Yeoh a/David Li f/2024-02-03 14:00 t/2024-02-03 15:30 </code></td>
        <td><code>add n/LANDLORD_NAME a/ADDRESS p/PHONE s/ASKING_PRICE t/TYPE…​</code><br> e.g., <code>add n/Janice Tan a/123 West Coast #12-288 p/33334444 a/650000 t/HDB</code></td>
    </tr>
    <tr>
        <td><b>Edit</b></td>
        <td><code>edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [b/BUDGET] [t/TAG]…​</code><br> e.g., <code>edit 2 n/James Lee e/jameslee@example.com</code></td>
        <td><code>edit INDEX [s/SUBJECT] [i/INFO] [f/FROM] [t/TO] [a/ADDED_BUYERS]…​</code><br> e.g., <code>edit 3 a/Alex Yeoh a/Ben Ten</code></td>
        <td><code>edit INDEX [n/LANDLORD_NAME] [a/ADDRESS] [p/PHONE] [a/ASKING_PRICE] [t/TYPE]…​</code><br> e.g., <code>edit 2 n/Ben Tan a/East Coast Blk 20 #11-283 </code></td>
    </tr>
    <tr>
        <td><b>Delete</b></td>
        <td><code>delete INDEX</code><br> e.g., <code>delete 3</code></td>
        <td><code>delete INDEX</code><br> e.g., <code>delete 2</code></td>
        <td><code>delete INDEX</code><br> e.g., <code>delete 5</code></td>
    </tr>
    <tr>
        <td><b>Find</b></td>
        <td><code>find n/KEYWORD [MORE_KEYWORDS]</code><br> e.g., <code>find n/James Jake</code></td>
        <td><code>find s/KEYWORD [MORE_KEYWORDS]</code><br> e.g., <code>find Project </code></td>
        <td><code>find [a/KEYWORD] [n/KEYWORD] [MORE_KEYWORDS]</code><br> e.g., <code>find a/Lakefront</code></td>
    </tr>
    <tr>
        <td><b>Clear</b></td>
        <td><code>clear</code>
        <td><code>clear</code>
        <td><code>clear</code>
    </tr>
    <tr>
        <td><b>Help</b></td>
        <td colspan="3"><code>help</code></td>
    </tr>
</table>
