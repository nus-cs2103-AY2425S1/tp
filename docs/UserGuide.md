---
layout: page
title: User Guide
---

<link rel="stylesheet" type="text/css" href="assets/css/UserGuide.css">

EZSTATES is a Command Line Interface (CLI) desktop app designed specifically for **real estate agents** who manage buyer and seller contacts (i.e. clients), listings, and appointments. Below is a sneak peek into our app: 
<br>
<br>
**Client management**
![img_4.png](images/img_4.png)
<br>
<br>
**Listing management**
![img_5.png](images/img_5.png)
<br>

**You could be part of the ideal target audience of EZSTATES if you meet the following criteria**:

* `Job` : Real Estate Agent focused on managing buyer and seller relationships 
* `Typing ability` : Comfortable with typing speeds of ~80 WPM or higher 
* `Workflow Preference` : Prefers streamlined typing commands over navigating Graphic User Interface (GUI) buttons 
* `Tech Savviness` : Confident using command-based applications and prefers minimal mouse use 
* `Work Style` : Handles multiple clients and properties simultaneously and values efficiency in contact and property management
* `Environment` : Works in a fast-paced setting where quick data entry and retrival are essential

But do not worry if you do not meet every criterion — EZSTATES is designed to be **intuitive and efficient for all users**. Give it a try, and head over to our [Quick Start](#quick-start-guide) guide to get started and see how EZSTATES can make your contact and property management tasks faster and easier!

--------------------------------------------------------------------------------------------------------------------
<!-- omit from toc -->
# Table of Contents
<br>
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick Start Guide

Welcome to EZSTATES! This guide will walk you through installing the app and getting started with basic commands.

### Installation Steps

#### For Windows
1. **Open the Command Prompt**:
   - Press the **Windows key**, type `cmd`, and select **Command Prompt** from the search results.
   <br>
2. **Check your Java version**:
   - In the Command Prompt window, type the following command:
     ```bash
     java -version
     ```
   - Press **Enter**. If Java is installed, it will display the version. Ensure it is **Java 17 or above**.
   <br>
   If you need `Java`, you can download it from [Java Downloads](https://www.oracle.com/java/technologies/downloads/).
   <br>
   <br>
3. Download the latest `.jar` file from [EZSTATES Releases](https://github.com/AY2425S1-CS2103T-F11-4/tp/releases).

4. Copy the file to the folder you want to use as the _home folder_ for EZSTATES.

5. Open a command terminal (repeat Step 1) and navigate to the folder with the `.jar` file by typing:
    ```bash
    cd path/to/your/folder

    ```
    <div class="note" markdown="span">
    Alternatively, you can open the folder where the `.jar` file  is located,
    right click on any space inside the folder and click `Open in Terminal`.
    </div>
    <br>
   
   Then inside terminal, run the app with:
    ```bash
    java -jar EZSTATES.jar
   
    ```
   
   A GUI should appear in a few seconds as shown below. **Note that the initial launch of the app will contain some sample data**.<br><br>
   ![Ui](images/Ui.png)<br><br>
6. Head over to the [next section](#gui-overview) to understand the GUI. If you're already familiar with it, feel free to skip ahead to [Basic Commands](#basic-commands).

#### For MacOS
1. **Open the Terminal**:
   - Open **Spotlight** by pressing **Command + Space**, type `Terminal`, and press **Enter**.
   - Alternatively, you can go to **Finder > Applications > Utilities**, and double-click on **Terminal**.
   <br>
2. **Check your Java version**:
   - In the Terminal window, type the following command:
     ```bash
     java -version
     ```
   - Press **Enter**. The terminal will display the installed Java version. Ensure it is **Java 17 or above**.
   <br>
   If you need `Java`, you can download it from [Java Downloads](https://www.oracle.com/java/technologies/downloads/).
   <br>
   <br>
3. Download the latest `.jar` file from [EZSTATES Releases](https://github.com/AY2425S1-CS2103T-F11-4/tp/releases).

4. Copy the file to the folder you want to use as the _home folder_ for EZSTATES.

5. Open Terminal (repeat Step 1) and navigate to the folder with the `.jar` file by typing:
    ```bash
    cd path/to/your/folder

    ``` 
    <div class="note" markdown="span">
    Alternatively, you can open the folder where the `.jar` file  is located,
    right click on any space inside the folder and click `New Terminal at Folder`.
    </div>
    <br>

   Then inside terminal, run the app with:
   ```bash
   java -jar EZSTATES.jar

    ```
   A GUI should appear in a few seconds as shown below. **Note that the initial launch of the app will contain some sample data**.<br><br>
   ![Ui](images/Ui.png)<br><br>
6. Head over to the [next section](#gui-overview) to understand the GUI. If you're already familiar with it, feel free to skip ahead to [Basic Commands](#basic-commands).

### GUI Overview
The EZSTATES GUI is organized into **five** key components:

   ![ui](images/ui_overview_ug.png)

* `Menu Bar`: Located at the top left, this includes options such as `File` and `Help` for managing settings and accessing support.
* `Command Box`: The main area where users can enter commands to interact with the app.
* `Result Display`: Provides immediate feedback, displaying success or failure messages based on the user’s command. Situated below the `Command Box`.
* `List Card`: Displays key information about clients or listings, depending on the user's command. Situated below the `Result Display`
* `Chat Window`: Located at the bottom right, this serves as a helpful assistant, offering guidance and support through interactive communication.

With a clear understanding of the GUI, let's move on to how you can interact with EZSTATES using [basic commands](#basic-commands).

### Basic Commands
To use EZSTATES, type commands in the command box and press `Enter`. Here are a few to try:

   * `help` : Opens the help window showing all commands.

   * `showclients` : Shows all contacts in the system.

   * `showlistings` : Shows all listings in the system.

   * `buyer n/John Doe p/98765432 e/johnd@example.com` : Adds a buyer named `John Doe` with a phone number `98765432` and email `johnd@example.com`.

   * `deleteclient 1` : Deletes the contact at index 1 (e.g. "bob" according to the picture above).

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

Once you've tried out these basic commands, it's helpful to understand [how they are organized](#command-structure). This will make it easier to navigate and use **all** commands in EZSTATES effectively, and to grasp how each part functions.

### Command Structure

Understanding the command structure in EZSTATES is essential for efficient navigation and use of the app’s features. 
Each command in EZSTATES is designed to follow a **clear, consistent format**, allowing you to manage clients, listings, and appointments with precision and speed. 
By mastering this structure, you will find it easier to remember commands and customize inputs, making your experience smoother and enabling you to accomplish tasks more effectively. 

This section breaks down the **structure, reference, and prefixes** used across all commands, so you can quickly become proficient with EZSTATES.

Commands in EZSTATES follow the same structure:

<p style="text-align: center;">
`commandWord (REFERENCE) (PREFIXES)` 
</p>
<br>

| commandWord                  | REFERENCE                                                                                                            | PREFIXES                                                                                                 |
|------------------------------|----------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------|
| Specifies the command to run | Comes before all prefixes and is used to make reference a particular client/listing <br/> Optional for some commands | Used to specify various attributes/properties for a given `commandWord` <br/> Optional for some commands |

#### Reference Types

| REFERENCE | Meaning                                  | Constraints                                                     | Remarks                                                                                                        |
|-----------|------------------------------------------|-----------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------|
| INDEX     | INDEX of a client or a listing in a list | INDEX are positive integers that are `one-based` (i.e. `>= 1`). | Commonly used in edit and delete clients/listings to make reference to these objects in their respective lists |

#### Prefix Notation

Prefixes follow the same structure:

<p style="text-align: center;">
`prefix/Value`
</p>
<br>

and can be either optional or mandatory, and variadic or not variadic.
The table below showcases the four different possible notations of prefixes:

|              | Mandatory       | Optional          |
|--------------|-----------------|-------------------|
| Not variadic | prefix/Value    | [prefix/Value]    |
| Variadic     | prefix/Value... | [prefix/Value]... |

<div class="note" markdown="span">
Note 1: Optional prefixes can be omitted and the command will still be executed successfully <br> _(assuming all other parts of the command are correctly inputted)_ 
</div>

<br>

<div class="note" markdown="span">
Note 2: Variadic prefixes allow you to enter multiple values for a single command by separating them with spaces. For example:
<br>
`t/friend t/colleague t/mentor`
<br>
This lets you add multiple tags at once, making it easier to input bulk data
</div>

#### Prefix Types

The prefixes used in **EZSTATES** are universal across all commands.

| Prefix | Meaning | Constraints                                                                                                                                                                                                                                                                                                                                                         | Valid                                 | Invalid                                   |
|--------|---------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------|-------------------------------------------|
| n/     | name    | Names should only contain `alphanumeric` characters and `spaces`, and it should not be `blank`.                                                                                                                                                                                                                                                                     | `n/wen xuan`, `n/muhammad`, `n/sean2` | `n/!@#`, `n/`                             |
| p/     | phone   | Phone numbers should only contain `numbers`, and it should be at least `3` digits long.                                                                                                                                                                                                                                                                             | `p/123`, `p/91230000`                 | `p/12`, `p/abc123`, `p/`                  |
| e/     | email   | Emails must follow the format `local-part@domain`. The local-part can contain alphanumeric characters and special characters (`+_.-`), but cannot start or end with special characters. The domain must have at least one label, with each label starting and ending with alphanumeric characters and being at least 2 characters long. Labels can contain hyphens. | `e/bobby@gmail.com`, `e/123@123`      | `e/bobby`, `e/123@.com`, `e/@example.com` |
| t/     | tag     | Tag names should be `alphanumeric`.                                                                                                                                                                                                                                                                                                                                 | `t/friend1`, `t/colleague`, `t/`      | `t/friend@1`, `t/123@abc`                 |
| d/     | date    | Dates should be in the format `dd-MM-yy` or `ddMMyy` (e.g., 25-12-24 or 251224).                                                                                                                                                                                                                                                                                    | `d/08-12-24`, `d/081224`              | `d/32-13-24`, `d/123456`, `d/`            |
| fr/    | from    | Times should be in the format `HH:mm` or `HHmm` (e.g., 0900 or 09:00). `from` time must precede `to` time.                                                                                                                                                                                                                                                          | `fr/0800`, `fr/08:00`                 | `fr/2500`, `fr/100`, `fr/8am`             |
| to/    | to      | Times should be in the format `HH:mm` or `HHmm` (e.g., 0900 or 09:00). `to` time must supercede `from` time.                                                                                                                                                                                                                                                        | `to/1000`, `to/10:00`                 | `to/2500`, `to/110`, `to/`                |
| pr/    | price   | Price should only contain `positive` integers and cannot start with `zeroes`, and it should be at least `6` digits long.                                                                                                                                                                                                                                            | `pr/100000`, `pr/45000000`            | `pr/000123`, `pr/-1000`, `pr/12`          |
| ar/    | area    | Area should only contain `positive` integers and cannot start with `zeroes`, and it should be at least `2` digits long.                                                                                                                                                                                                                                             | `ar/10`, `ar/100`                     | `ar/01`, `ar/-5`, `ar/`                   |
| add/   | address | Addresses can take any values, and it should not be `blank`.                                                                                                                                                                                                                                                                                                        | `add/123 PASIR RIS (S)123456`         | `add/`                                    |
| reg/   | region  | Only the following `9` regions are allowed: `EAST`, `WEST`, `NORTHEAST`, `SOUTH`, `NORTH`, `NORTHWEST`, `SOUTHEAST`, `SOUTHWEST`, `CENTRAL`.                                                                                                                                                                                                                        | `reg/east` `reg/northeast`            | `reg/xyz`, `reg/invalidregion`            |
| sel/   | seller  | Can only take non-zero unsigned integer.                                                                                                                                                                                                                                                                                                                            | `sel/1` `sel/2`                       | `sel/0`, `sel/-1`, `sel/abc`              |
| buy/   | buyer   | Can only take non-zero unsigned integer.                                                                                                                                                                                                                                                                                                                            | `buy/1` `buy/2`                       | `buy/0`, `buy/-2`, `buy/abc`              |

#### Remarks

Below are additional notes regarding certain prefixes. Please keep these in mind while using commands to help maintain data integrity and avoid unexpected issues.

##### n/
1. Names are `space-sensitive`. This means that `n/alexyeoh` (0 space), `n/alex yeoh` (1 space) and `n/alex  yeoh` (2 spaces) _(not exhaustive)_ create three different profiles.
2. Names are `case-insensitive`. This means that `n/alex yeoh` and `n/AlEx YeOh` refer to the same name.
3. Duplicate names are not allowed within the clients or listings lists (e.g., two buyers or sellers both named `Bobby` cannot exist).
4. Duplicate names between clients and listings are allowed (e.g. a client named `Bobby` and a listing named `Bobby` can both exist).
5. Names have `no character limit`, but lengthy names will be automatically truncated with an ellipsis.
6. Names can consist only of numbers, but use caution, as this may cause confusion when displayed alongside the client's index.
7. Allowable Edge Cases: Names can include single characters or initials (e.g. `n/A` is valid). While this is allowed, single-letter names might be confusing in lists with similar entries (e.g. `n/A`, `n/B`, etc.).
8. Names with excessive leading or trailing spaces are treated as names withouit (e.g. `n/Alice Johnson               ` = `n/Alice Johnson`).
9. There is a known issue with the n/ prefix. Please refer to the [Known Issues](#known-issues) section.

##### p/
1. It is permissible for different clients to have the same phone number.
2. Phones have `no character limit`, but lengthy phones will be automatically truncated with an ellipsis.
3. Numbers with leading zeros (e.g. p/0012345) are allowed, though these may be visually confusing or prone to misinterpretation.
4. As an extension to pt. 3, a number with just `zeroes` is allowed, although such a number may not appear realistic. 

##### e/
1. Emails may technically be invalid but still pass the regex test (e.g. 123@123). It’s up to the user to decide how they input their clients' emails.
2. Emails have `no character limit`, but lengthy emails will be automatically truncated with an ellipsis.
3. Emails can have unusual domain labels as long as they’re valid (e.g. `e/user@x-y.com` or `e/person@123.co`). While valid, such domains may not appear realistic.

##### t/
1. Tags are `case-sensitive`. This means that `t/FRIEND` and `t/friend` are treated as unique tags.
2. Very long tags (e.g., t/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA) are permissible but might be visually truncated.

##### d/
1. There is no restriction on the date range, so unrealistic dates in the far future or past may be entered (e.g. `d/01-01-99`).

##### fr/
NIL (all covered in Constraints)

##### to/
NIL (all covered in Constraints)

##### pr/
1. While only positive values are allowed, there’s no limit on maximum value, allowing extremely high prices (e.g., pr/9999999999) which may affect the display of data.
2. As per constraints, prices like `1000000.50` are not allowed.

##### ar/
1. The unit of measurement is `m²`.
2. There's no maximum limit, so values like ar/99999999999999999999999999999 will pass, which may affect the display of data.
3. As per constraints, areas like `1000.50` are not allowed.

##### add/
1. Unrealistically long addresses passes but will affect the display of data.
2. Since addresses accept any value, symbols and non-standard characters (e.g. `add/123 *^&`) will pass.

##### reg/
1. Regions are case-insensitive (i.e. `reg/east` and `reg/East` are the same).

##### sel/ & buy/
1. Leading zeroes are allowed but will be trimmed (i.e. `sel/01` and `sel/1` refer to the same seller).

<br>
Congratulations - you've successfully completed the Quick Start guide!

The [next section](#features) offers an **in-depth overview** of all the commands available in EZSTATES.

<div class="note" markdown="span">**EXTRA**<br><br>
Curious about how EZSTATES stores data? Check out the [storage section](#saving-the-data) for more information.
<br>
<br>
If you're an advanced user looking to edit the JSON file directly, head over to [this section](#editing-the-data-file) for more information.
</div>
--------------------------------------------------------------------------------------------------------------------

## Features

<div class="alert" markdown="span">
Before proceeding, please review or familiarize yourself with EZSTATES's [Command Structure](#command-structure) to ensure effective use of all features.
</div>
<br>

EZSTATES features (i.e. commands) are divided into **four** main categories:

1. [Client Management Commands](#1-client-management-commands)
    - [Add Buyer](#add-buyer) (`buyer`) 
    - [Add Seller](#add-seller) (`seller`)
    - [Find](#find) (`find`)
    - [Edit Client](#edit-client) (`editclient`)
    - [Delete Client](#delete-client) (`deleteclient`)
2. [Appointment Management Commands](#2-appointment-management) 
    - [Schedule Appointment](#schedule-appointment) (`apt`)
    - [Delete Appointment](#delete-appointment) (`deleteapt`)
3. [Listing Management Commands](#3-listing-management) 
    - [Add Listing](#add-listing) (`listing`)
    - [Show Listings](#show-listings) (`showlistings`)
    - [Edit Listing](#edit-listing) (`editlisting`)
    - [Add Buyers to Listing](#add-buyers-to-listing) (`addlistingbuyers`)
    - [Remove Buyers from Listing](#remove-buyers-from-listing) (`removelistingbuyers`)
    - [Delete Listing](#delete-listing) (`deletelisting`)
    - [Clear Listings](#clear-listing) (`clearlistings`)
4. [Utility Commands](#4-utility-commands)
    - [Clear](#clear) (`clear`)
    - [Exit](#exit) (`exit`)
    - [Help](#help) (`help`)
    - [More Info](#more-info) (`moreinfo`)
    - [Chat Window](#chat-window) (`chatbot`)

The following sections cover the **command format**, **description**, **valid** and **invalid inputs**, and **special comments**.

---

### 1. Client Management Commands

Commands for creating, updating, and deleting buyers and sellers.

![showClients](images/Ui.png)

#### Add Buyer
- **Command:** `buyer n/NAME p/PHONE e/EMAIL [t/TAG]...`
- **Description:** Creates a new buyer profile with specified details.
- **Successful Execution:**
> ---
>
> **Use Case #1**: Adding a buyer named `Bobby` with phone number `91124444` and email `bobby123@gmail.com`
>
> **Input**: `buyer n/Bobby p/91124444 e/bobby123@gmail.com`
>
> **Output**: New buyer added: Bobby; Phone: 91124444; Email: bobby123@gmail.com; Appointment: -; Tags:
>
> ---
>
> **Use Case #2**: Adding a buyer named `Bobby` with phone number `91124444`, email `bobby123@gmail.com`, tags `friend`, `owner`
>
> **Input**: `buyer n/Bobby p/91124444 e/bobby123@gmail.com t/friend t/owner`
>
> **Output**: New buyer added: Bobby; Phone: 91124444; Email: bobby123@gmail.com; Appointment: -; Tags: [owner][friend]
>
> ---

- **Failed Execution:**
> ---
>
> **User Error #1**: Missing `NAME` field
>
> **Input**: `buyer p/91124444 e/bobby123@gmail.com`
>
> **Output**: <br>
Invalid command format! <br>
buyer: Adds a buyer to the address book. <br>
Parameters: n/NAME p/PHONE e/EMAIL [t/TAG]...<br>
Example: buyer n/John Doe p/98765432 e/johnd@example.com> t/friends t/owesMoney
>
> ---
>
> **User Error #2**: Missing `PHONE` field
>
> **Input**: `buyer n/Bobby e/bobby123@gmail.com`
>
> **Output**: <br>
Invalid command format! <br>
buyer: Adds a buyer to the address book. <br>
Parameters: n/NAME p/PHONE e/EMAIL [t/TAG]...<br>
Example: buyer n/John Doe p/98765432 e/johnd@example.com> t/friends t/owesMoney
>
> ---
>
> **User Error #3**: Missing `EMAIL` field
>
> **Input**: `buyer n/Bobby p/91124444`
>
> **Output**: <br>
Invalid command format! <br>
buyer: Adds a buyer to the address book. <br>
Parameters: n/NAME p/PHONE e/EMAIL [t/TAG]...<br>
Example: buyer n/John Doe p/98765432 e/johnd@example.com> t/friends t/owesMoney
>
> **User Error #4**: Buyer already exists
>
> **Input**: `buyer n/Bobby p/83485111 e/bobby1234@gmail.com` <br>_(Assuming name `Bobby` already exists)_
>
> **Output**: This buyer already exists in the address book
>
> ---

- **Special Comments**
1. Refer to the remarks and constraints for the following prefixes: [n/](#n) [p/](#p) [e/](#e) [t/](#t)

#### Add Seller
- **Command:** `seller n/NAME p/PHONE e/EMAIL [t/TAG]...`
- **Description:** Creates a new seller profile with specified details.
- **Successful Execution:**
> ---
> **Use Case #1**: Adding a seller named `Bobby` with phone number `91124444` and email `john123@gmail.com`
>
> **Input**: `seller n/Bobby p/91124444 e/bobby123@gmail.com`
>
> **Output**: New seller added: Bobby; Phone: 91124444; Email: bobby123@gmail.com; Appointment: -; Tags:
>
> ---
>
> **Use Case #2**: Adding a seller named `Bobby` with phone number `91124444`, email `john123@gmail.com`, tags `friend`, `owner`
>
> **Input**: `seller n/Bobby p/91124444 e/bobby123@gmail.com t/friend t/owner`
>
> **Output**: New seller added: Bobby; Phone: 91124444; Email: bobby123@gmail.com; Appointment: -; Tags: [owner][friend]
>
> ---

- **Failed Execution:**
> ---
> 
> **User Error #1**: Missing `NAME` field
>
> **Input**: `seller p/91124444 e/bobby123@gmail.com`
>
> **Output**: <br>
Invalid command format! <br>
seller: Adds a seller to the address book. <br>
Parameters: n/NAME p/PHONE e/EMAIL [t/TAG]...<br>
Example: seller n/John Doe p/98765432 e/johnd@example.com> t/friends t/owesMoney
>
> ---
>
> **User Error #2**: Missing `PHONE` field
>
> **Input**: `seller n/Bobby e/bobby123@gmail.com`
>
> **Output**: <br>
Invalid command format! <br>
seller: Adds a seller to the address book. <br>
Parameters: n/NAME p/PHONE e/EMAIL [t/TAG]...<br>
Example: seller n/John Doe p/98765432 e/johnd@example.com> t/friends t/owesMoney
>
> ---
> **User Error #3**: Missing `EMAIL` field
>
> **Input**: `seller n/Bobby p/91124444`
>
> **Output**: <br>
Invalid command format! <br>
seller: Adds a seller to the address book. <br>
Parameters: n/NAME p/PHONE e/EMAIL [t/TAG]...<br>
Example: seller n/John Doe p/98765432 e/johnd@example.com> t/friends t/owesMoney
>
> ---
> 
> **User Error #4**: Seller already exists
> 
> **Input**: `seller n/Bobby p/83485111 e/bobby1234@gmail.com` <br>_(Assuming name `Bobby` already exists)_ 
> 
> **Output**: This buyer already exists in the address book
> 
>  ---

- **Special Comments**
1. Refer to the remarks and constraints for the following prefixes: [n/](#n), [p/](#p), [e/](#e), [t/](#t)

#### Show Clients
- **Command:** `showclients`
- **Description:** Displays all clients in the system.
- **Successful Execution:**
> ---
> **Use Case #1**: Displaying all clients in the system
> 
> **Input**: `showclients`
> 
> **Output**: Here are your clients!
> ![img_3.png](images/img_3.png)
> 
> ---

- **Failed Execution:** NIL
- **Special Comments**
1. You are able to execute showclients on an empty list of clients.

#### Find
- **Command:** `find KEYWORD [KEYWORD]...`
- **Description:** Finds the specified client(s) based on the provided keywords.
- **Successful Execution:**
> ---
> **Use Case #1**: Finding `Bob`
>
> **Input**: `find Bob`
>
> **Output**: 1 persons listed!
>
> ![bob](images/bob.png)
>
> ---
>
> **Use Case #2**: Finding `Bob` OR `Winter`
>
> **Input**: `find Bob Winter`
>
> **Output**: 2 persons listed!
>
> ![bobwinter](images/bob_winter.png)
>
> ---

- **Failed Execution:**
> ---
>
> **Use Case**: Client not found
> 
> **Input**: `find Bob7`
> 
> **Output**: 0 persons listed!
> 
> ![noclients](images/no_clients.png)
> 
> ---

- **Special Comments**
1. For cases where names consist of two or more parts (e.g. Wen Xuan), inputting `find Wen Xuan` results in finding clients containing `Wen` OR `Xuan` in their names, as per Successful Execution Use Case #2. 


#### Edit Client
- **Command:** `editclient INDEX [n/NAME] [p/PHONE] [e/EMAIL] [t/TAG]...`
- **Description:** Edits the details of the specified client.
- **Successful Execution:**
> ---
> **Use Case #1**: Changing name of `Bob` to `Bobby` (Assuming displayed index is 1)
>
> **Input**: `editclient 1 n/Bobby`
>
> **Output**: Successfully edited Bobby; Phone: 91124444; Email: bobby123@gmail.com; Appointment: -; Tags: [owner][friend]!
>
> ---
>
> **Use Case #2**: Changing phone of `Bobby` to `97774444` 
>
> **Input**: `editclient 1 p/97774444`
>
> **Output**: 
> Successfully edited Bobby.
> Phone number: 97774444 and Email: bobby123@gmail.com!
>
> ---
>
> **Use Case #3**: Removing tags of `Bobby`
>
> **Input**: `editclient 1 t/`
>
> **Output**: 
> Successfully edited Bobby. 
> Phone number: 97774444 and Email: bobby123@gmail.com!
>
> ---

- **Failed Execution:**
> ---
> **User Error #1**: No index found / Invalid type / Negative integer
>
> **Input**: `editclient n/Bobby` OR `editclient #a` OR `editclient -1`
>
> **Output**:
<br> Invalid command format!
<br>edit: Edits the details of the person identified by their name. Existing values will be overwritten by the input values.
<br>Parameters: INDEX (must be a positive integer) [n/NAME] [p/PHONE] [e/EMAIL] [t/TAG]...
<br>Example: editclient 1 e/johndoe@example.com p/91234567
>
> ---
> 
> **User Error #2**: Entering out-of-bounds index (larger than number of clients)
> 
> **Input**: `editclient 100 n/Bobby`
> 
> **Output**: The person index provided is invalid
> 
> ---

- **Special Comments**
1. Refer to [INDEX](#reference-types) constraints and the remarks and constraints following prefixes: [n/](#n) [p/](#p) [e/](#e) [t/](#t)

#### Delete Client
- **Command:** `deleteclient INDEX`
- **Description:** Deletes the specified client profile.
- **Successful Execution:**
> ---
> **Use Case #1**: Delete `Bob` from the address book (Assuming displayed index is 1)
>
> **Input**: `deleteclient 1`
>
> **Output**: `Successfully deleted Bob.
Phone number: 977774444 and Email: bobby123@gmail.com`
>
> ---

- **Failed Execution:**
> ---
> **Use Case #1**: No index found / Invalid type / Negative integer
>
> **Input**: `deleteclient` OR `deleteclient #a` OR `deleteclient -1`
>
> **Output**: 
<br> Invalid command format! 
<br>delete: Deletes the client profile corresponding to the client's name.
<br>Parameters: INDEX (must be a positive integer)
<br>Example: deleteclient 1
>
> ---
> **Use Case #2**: Entering out-of-bounds index (larger than number of clients)
> 
> **Input**: `deleteclient 100`
> 
> **Output**: The person index provided is invalid
> 
> ---

- **Special Comments**
1. Refer to [INDEX](#reference-types) constraints.



---

### 2. Appointment Management

Commands for managing appointments between user and clients.

![appointments](images/appointments.png)

#### Schedule Appointment
- **Command:** `apt INDEX d/DD-MM-YY fr/HHmm to/HHmm` OR `apt INDEX d/ddMMyy fr/HH:mm to/HH:mm`
- **Description:** Schedules a new appointment to be held with the specified client that includes the specified details (date, time).<br>
- **Successful Execution:**
> ---
> **Use Case #1**: Adding appointment `8th October 2024 7pm to 9pm` for client `Bob` (Assuming displayed index is 1)
>
> **Input**: `apt 1 d/08-10-24 fr/1900 to/2100`
>
> **Output**: Appointment scheduled for Bob; Phone: 94441111; Email: bob123@gmail.com; Appointment: Date: 08-10-24 (From: 19:00 To: 21:00); Tags:
>
> ![bob_apt](images/bob_apt.png)
> 
> ---
>
> **Use Case #2**: Overriding an existing appointment for client `Bob` to be `9th October 2024 10am to 12pm` instead
>
> **Input**: `apt 1 d/09-10-24 fr/1000 to/1200`
>
> **Output**: Appointment scheduled for Bob; Phone: 94441111; Email: bob123@gmail.com; Appointment: Date: 09-10-24 (From: 10:00 To: 12:00); Tags:
>
> ![bob_apt_2](images/bob_apt_2.png)
> 
> ---

- **Failed Execution:**
> ---
> **Use Case #1**: Incorrect `DATE` format 
>
> **Input #a**: `apt 1 d/09-10-2024 fr/1000 to/1200`
>
> **Input #b**: `apt 1 d/aaa fr/1000 to/1200`
> 
> **Output**: Dates should be in the format dd-MM-yy or ddMMyy, e.g., 25-12-24 or 251224.
>
> ---
> 
> **Use Case #2**: Incorrect `TIME` format
> 
> **Input #a**: `apt 1 d/20-10-24 fr/100000 to/1200`
> 
> **Input #b**: `apt 1 d/20-10-24 fr/aa to/1200`
> 
> **Output**: Times should be in the format HH:mm or HHmm, e.g., 0900 or 09:00.
> 
> ---

- **Special Comments**
1. Refer to [INDEX](#reference-types) constraints and the remarks and constraints following prefixes: [d/](#d) [fr/](#fr) [to/](#to)

#### Delete Appointment
- **Command:** `deleteapt INDEX`
- **Description:** Deletes an appointment with the specified client.
- **Successful Execution:**
> ---
> **Use Case**: Deleting appointment for `Bob` (Assuming displayed index is 1)
>
> **Input**: `deleteapt 1`
>
> **Output**: Successfully deleted appointment from Bob
>
> ![bobdeletedappt](images/bob_del_apt.png)
> 
> ---

- **Failed Execution:**
> ---
> **Use Case**: Entering out-of-bounds index (larger than number of clients)
>
> **Input**: `deleteapt 100`
>
> **Output**: The person index provided is invalid
>
> ---

- **Special Comments**
1. Refer to [INDEX](#reference-types) constraints and the remarks & constraints for the following prefixes: [d/](#d) [fr/](#fr) [to/](#to)
      
---

### 3. Listing Management

Commands for managing property listings and associating clients with listings.

![showListings](images/showListings.png)

#### Add Listing
- **Command:** `listing n/NAME pr/PRICE ar/AREA add/ADDRESS reg/REGION sel/SELLER_INDEX [buy/BUYER_INDEX]...`
- **Description:** Adds a new listing associated to the seller with the specified details.
- **Successful Execution:**
> ---
> **Use Case #1**: Adding a listing with name `Warton House`, price `4000`, area `1000`, address `123 PASIR RIS (S)123456`, region `east`, seller `Bernice Yu`, buyer `Alex Yeoh`  
>
> **Input**: `listing n/Warton House pr/4000 ar/1000 add/123 PASIR RIS (S)123456 reg/east sel/2 buy/1`
>
> **Output**: New listing added: Warton House; Price: 4000; Area: 1000; Region: EAST; Address: 123 PASIR RIS (S)123456; Seller: seedu.address.model.person.Seller{name=Bernice Yu, phone=99272758, email=berniceyu@example.com, tags=[[colleagues], [friends]], appointment=-, remark=No remarks yet.}seedu.address.model.person.Buyer{name=Alex Yeoh, phone=87438807, email=alexyeoh@example.com, tags=[[friends]], appointment=Date: 20-12-24 (From: 08:00 To: 10:00), remark=Test}
>
> ![listing](images/warton_house_listing.png)
> 
> ---
>
> **Use Case #2**: Adding a listing with no buyers
>
> **Input**: `listing n/Warton House pr/4000 ar/1000 address/123 PASIR RIS (S)123456 reg/east sel/2`
>
> **Output**: New listing added: Warton House; Price: 4000; Area: 1000; Region: EAST; Address: 123 PASIR RIS (S)123456; Seller: seedu.address.model.person.Seller{name=Bernice Yu, phone=99272758, email=berniceyu@example.com, tags=[[colleagues], [friends]], appointment=-, remark=No remarks yet.}
>
> ![listing2](images/warton_house_no_buyers.png)
> 
> ---

- **Failed Execution:**
> ---
> **Use Case #1**: Attempting to add a listing for a non-existent seller
>
> **Input**: `listing n/Warton House pr/4000 ar/1000 add/123 PASIR RIS (S)123456 reg/east sel/100`
>
> **Output**: The seller index provided is invalid!
>
> ---
> 
> **Use Case #2**: Attempting to add non-existent buyers to a listing
> 
> **Input**: `listing n/Warton House pr/4000 ar/1000 add/123 PASIR RIS (S)123456 reg/east sel/2 buy/100`
> 
> **Output**: The buyer index (100) provided is invalid!
> 
> ---

- **Special Comments**
1. Refer to the remarks and constraints for the following prefixes: [n/](#n) [pr/](#pr) [ar/](#ar) [add/](#add) [reg/](#reg) [sel/ & buy/](#sel--buy)

#### Show Listings
- **Command:** `showlistings`
- **Description:** Displays all current listings.
- **Successful Execution:**
> ---
> **Use Case #1**: To show all listings in EZSTATES
>
> **Input**: showlistings
>
> **Output**: Here are your listings!
>
> ![showlistings](images/showListings.png)
> 
> ---
> 
> **Use Case #2**: To show all listings (when there are none)
> 
> **Input**: showlistings
> 
> **Output**: You have no listings available.
> 
> ![nolistings](images/no_listings.png)
> 
> ---

- **Failed Execution:** NIL
- **Special Comments:**
1. You are able to show an empty list of listings, as per Successful Execution Use Case #2

#### Edit Listing
- **Command:** `editlisting INDEX [n/NAME] [pr/PRICE] [ar/AREA] [add/ADDRESS] [reg/REGION]...`
- **Description:** Edits the details of the listing identified by the listing index number. **Buyers cannot be edited using this command.** Use addlistingbuyers or removelistingbuyers to manage buyers.
- **Successful Execution:**
> ---
> **Use Case #1**: Editing listing `RC4` to become `RC445` with area `150`
> 
> **Input**: `editlisting 1 n/RC445 pr 1500000000 ar/150`
> 
> **Output**: 
Successfully edited listing: RC445.
Address: test
> 
> **Use Case #2**: Editing listing `RC4` region to become `central`
> 
> **Input**: `editlisting 1 reg/central`
> 
> **Output**:
Successfully edited listing: RC445.
Address: test
> 
> ---

- **Failed Execution:**
> ---
> **User Error #1**: Out-of-bounds index
> 
> **Input**: `editlisting 1000` 
> 
> **Output**: The listing index provided is invalid!
> 
> 
> **User Error #2**: Changing name to pre-existing name
> 
> **Input**: `editlisting 1 n/RC5` _(assuming RC5 already exists in the system)_
> 
> **Output**: This listing name / address already exists in the system.
> 
> 
> **User Error #3**: No prefixes provided
> 
> **Input**: editlisting 1
> 
> **Output**: At least one field to edit must be provided.
> 
> ---

- **Special Commands**
1. Refer to [INDEX](#reference-types) constraints and the remarks and constraints for the following prefixes: [n/](#n) [pr/](#pr) [ar/](#ar) [add/](#add) [reg/](#reg) [sel/ & buy/](#sel--buy)

#### Add Buyers to Listing
- **Command:** `addlistingbuyers INDEX buy/BUYER_INDEX [buy/MORE_BUYER_INDEXES...]`
- **Description:** Associates buyers with a specified listing.
- **Successful Execution:**
> ---
> **Use Case #1**: Adding one buyer `Alex Yeoh` to listing `RC4` (Assuming RC4 index is 1)
>
> **Input**: `addlistingbuyers 1 buy/1 buy/3`
>
> **Output**: Buyers added to listing: RC4
>
> ---
>
> **Use Case #2**: Adding two buyers `Alex Yeoh` and `Charlotte Oliveiro` to listing `David HDB`
>
> **Input**: `addlistingbuyers 2 buy/1 buy/3`
>
> **Output**: Buyers added to listing: David HDB
>
> ---

- **Failed Execution:**
> ---
> **Use Case #1**: Listing not found
>
> **Input**: `addlistingbuyers 100 buy/1`
>
> **Output**: The listing index provided is invalid!
>
> ---
> 
> **User Error #2**: Duplicate buyers
> 
> **Input**: `addlistingbuyers 1 buy/1` <br>_(Assuming RC4 contains Alex Yeoh already)_
> 
> **Output**: Some buyers are already associated with this listing.
> 
> --- 
> 
> **User Error #3**: Buyer not found
> 
> **Input**: `addlistingbuyers 1 buy/100`
> 
> **Output**: The person index provided is invalid!
> 
> ---
> 
> **User Error #4**: Person is not a buyer
> 
> **Input**: `addlistingbuyers 1 buy/1` <br>_(Assuming client with index 1 is a seller)_
> 
> **Output**: The specified person is not a buyer:<br>1.bob
> 
>
> ---

- **Special Comments**
1. Refer to [INDEX](#reference-types) constraints and the remarks and constraints for the following prefix: [buy/](#sel--buy)

#### Remove Buyers from Listing
- **Command:** `removelistingbuyers INDEX buy/BUYER INDEX [buy/MORE_BUYER_INDEXES...]`
- **Description:** Removes buyers associated with a specified listing.
- **Successful Execution:**
> ---
> **Use Case #1**: Removing one buyer `Alex Yeoh` from listing `RC4`
>
> **Input**: `removelistingbuyers 1 buy/1` 
>
> **Output**: Buyers removed from listing: RC4
>
> ---
>
> **Use Case #2**: Removing two buyers `Alex Yeoh` and `Charlotte Oliveiro` from listing `RC4`
>
> **Input**: `removelistingbuyers 1 buy/1 buy/3`
>
> **Output**: Buyers removed from listing: RC4
>
> ---

- **Failed Execution:**
> ---
> **User Error #1**: Listing not found
>
> **Input**: `removelistingbuyers 100 buy/1`
>
> **Output**: The listing index provided is invalid!
> 
> ---
> 
> **User Error #2**: Empty set of buyers
>
> **Input**: `removelistingbuyers 1 buy/`
>
> **Output**: The person index provided is invalid!
> 
> ---
> 
> **User Error #3**: Person specified is not buyer
>
> **Input**: `removelistingbuyers 1 buy/2`
>
> **Output**: The person index provided is invalid!
> 
> ---
> 
> **User Error #4**: Person specified is not a buyer for the listing
>
> **Input**: `removelistingbuyers 1 buy/3`
>
> **Output**: The specified buyer notInterestedBuyer is not a buyer of the listing RC4.
> 
> ---
> 
> **User Error #5**: Buyer not found
>
> **Input**: `removelistingbuyers 1 buy/100`
>
> **Output**: The specified buyer nonExistentBuyer does not exist in the client list.
> 
> ---

- **Special Comments**
1. Refer to [INDEX](#reference-types) constraints and the remarks and constraints for the following prefix: [buy/](#sel--buy)

#### Delete Listing
- **Command:** `deletelisting INDEX`
- **Description:** Deletes a specified listing.
- **Successful Execution:**
> ---
> **Use Case #1**: Deleting listing `Warton House` (Assuming displayed index is 1)
>
> **Input**: `deletelisting 1`
>
> **Output**: Successfully deleted listing: Warton House
>
> ---

- **Failed Execution:**
> ---
> **Use Error**: Listing not found
>
> **Input**: deletelisting 100
>
> **Output**: This listing does not exist in EZSTATES
>
> ---

- **Special Comments**
1. Refer to [INDEX](#reference-types) constraints.

#### Clear Listings
- **Command:** `clearlistings`
- **Description:** Deletes ALL listings.
- **Successful Execution:**
> ---
> **Use Case**: Clear all listings in addressbook
>
> **Input**: clearlistings
>
> **Output**: All listings have been cleared!
>
> ---

- **Failed Execution:** NIL
- **Special Comments:** NIL

### 4. Utility Commands

Miscellaneous commands for application utility, such as clearing, exiting, and displaying help.

#### Clear
- **Command:** `clear`
- **Description:** Clears all clients and listings.
- **Successful Execution:**
> ---
> **Use Case**: Fresh addressbook and listings
>
> **Input**: clear
>
> **Output**: Address book and listings has been cleared!
>
> ---

- **Failed Execution:** NIL
- **Special Comments:** NIL

#### Exit
- **Command:** `exit`
- **Description:** Exits the application.
- **Successful Execution:**
> ---
> **Use Case**: Exit the application
>
> **Input**: exit
> 
> **Output**: Exiting Address Book as requested ...
>
> ---

- **Failed Execution:** NIL
- **Special Comments:** NIL

#### Help
- **Command:** `help`
- **Description:** Displays a list of available commands and their descriptions.
- **Successful Execution:**
> ---
> **Use Case**: Accessing help
>
> **Input**: `help`
>
> **Output**: Opened help window.
>
> ![help](images/help.png)
> 
> ---

- **Failed Execution:** NIL
- **Special Comments:** NIL

#### More Info
- **Command:** `moreinfo INDEX`
- **Description:** Provides additional information about a specific client.
- **Successful Execution:**
> ---
> **Use Case**: Finding out more information about `Bob`
>
> **Input**: `moreinfo 1`
>
> **Output**: Opened window for client's information.
>
> ![moreinfo](images/moreinfo.png)
> 
> ---

- **Failed Execution:**
> **User Error**: Out-of-bounds index
>
> **Input**: `moreinfo 100`
> 
> **Output**: The person index provided is invalid!
> 
> ---

- **Special Comments:**
1. Refer to the remarks and constraints for the following prefix: [n/](#n)

#### Chat Window
- **Command:** `chatbot`
- **Description:** Opens a chatbot that answers basic queries.
- **Successful Execution:**
> ---
> **Use Case #1**: Valid Greeting
>
> **Input**:
![validGreeting.png](images%2FvalidGreeting.png)
**Output**:
![outputFromValidGreeting.png](images%2FoutputFromValidGreeting.png)
> ---
>
> **Use Case #2**: Valid Query
>
> **Input**:
![validQuery.png](images%2FvalidQuery.png)
**Output**:
![outputFromValidQuery.png](images%2FoutputFromValidQuery.png)
> ---
>
> **Use Case #3**: Valid Farewell
>
> **Input**:
![validGoodbye.png](images%2FvalidGoodbye.png)
**Output**:
![outputFromValidGoodbye.png](images%2FoutputFromValidGoodbye.png)
> ---

- **Failed Execution:**
> ---
> **Use Case #1**: Invalid Query
>
> **Input**:
![invalidInput.png](images%2FinvalidInput.png)
>
> **Output**:
![outputOfInvalidInput.png](images%2FoutputOfInvalidInput.png)
> ---

- **Special Comments:** NIL

### Saving the data

EZSTATES data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

--------------------------------------------------------------------------------------------------------------------

### Editing the data file

AddressBook data are saved automatically as a JSON file:<br><br> `[JAR file location]/data/addressbook.json`<br><br>**Advanced users** are welcome to update data directly by editing that data file.

<div markdown="span" class="alert">
**Caution:**
If your changes to the data file makes its format invalid, EZSTATES will **discard all data** and start with an empty data file at the next run. Hence, it is recommended to **take a backup of the file before editing it**.<br>
<br>
Furthermore, certain edits can cause EZSTATES to **behave in unexpected ways** (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous EZSTATES home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.
3. **For the n/ prefix**, users are not able to put slashes in their names (e.g. Kumar S/O Navareen).

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action                         | Format, Examples                                                                                                                                                                                  |
|--------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Buyer**                  | `buyer n/NAME p/PHONE e/EMAIL`<br>e.g., `buyer n/James Ho p/22224444 e/jamesho@example.com`                                                                                                       |
| **Add Seller**                 | `seller n/NAME p/PHONE e/EMAIL`<br>e.g., `seller n/James Ho p/22224444 e/jamesho@example.com`                                                                                                     |
| **Find**                       | `find KEYWORD [MORE_KEYWORDS]`<br>e.g., `find James Jake`                                                                                                                                         |
| **Edit Client**                | `editclient INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [t/TAG]...`<br>e.g., `editclient 2 n/James Lee e/jameslee@example.com`<br>e.g., `editclient 2 n/James Voo t/Friend t/Colleague`             |
| **Delete Client**              | `deleteclient INDEX`<br>e.g., `deleteclient 3`                                                                                                                                                    |
| **Add Listing**                | `listing n/NAME pr/PRICE ar/AREA add/ADDRESS reg/REGION sel/SELLER_INDEX [buy/BUYER_INDEX]...`<br>e.g., `listing n/Warton House pr/4000 ar/1000 add/123 PASIR RIS (S)123456 reg/east sel/2 buy/1` |
| **Show Listings**              | `showlistings`                                                                                                                                                                                    |
| **Edit Listing**               | `editlisting INDEX [n/NAME] [pr/PRICE] [ar/AREA] [add/ADDRESS] [reg/REGION]...`<br>e.g., `editlisting 2 pr/450000 ar/1200`                                                                        |
| **Add Buyers to Listing**      | `addlistingbuyers INDEX buy/BUYER_INDEX [buy/MORE_BUYER_INDEXES...]`<br>e.g., `addlistingbuyers 1 buy/1 buy/3`                                                                                    |
| **Remove Buyers from Listing** | `removelistingbuyers INDEX buy/BUYER_INDEX [buy/MORE_BUYER_INDEXES...]`<br>e.g., `removelistingbuyers 1 buy/1 buy/3`                                                                              |
| **Delete Listing**             | `deletelisting INDEX`<br>e.g., `deletelisting 1`                                                                                                                                                  |
| **Clear Listings**             | `clearlistings`                                                                                                                                                                                   |
| **Clear**                      | `clear`                                                                                                                                                                                           |
| **Exit**                       | `exit`                                                                                                                                                                                            |
| **Help**                       | `help`                                                                                                                                                                                            |
| **More Info**                  | `moreinfo INDEX`<br>e.g., `moreinfo 1`                                                                                                                                                            |
| **Chat Window**                | `chatbot`                                                                                                                                                                                         |
