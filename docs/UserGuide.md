---
layout: page
title: User Guide
---

<link rel="stylesheet" type="text/css" href="assets/css/UserGuide.css">

EZStates is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick Start: Get started in 10 minutes!

In this quick start guide, you'll learn how to install EZSTATES. **_(3 min)_**<br>
<br>
You'll also learn how our commands work to kickstart the application. **_(7 min)_**

### Installation

1. Ensure you have Java `17` or above installed in your Computer.

2. Download the latest `.jar` file from [EZSTATES Releases](https://github.com/AY2425S1-CS2103T-F11-4/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar ezstates.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com` : Adds a contact named `John Doe` to the Address Book.

   * `delete John Doe` : Deletes the contact with name John Doe from the list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

6. Refer to the [Features](#features) for command details. 

### Command Structure 

--------------------------------------------------------------------------------------------------------------------

## Feature Categories

This user guide is divided into four main feature categories:

1. **Client Management Commands**
2. **Appointment Management Commands**
3. **Listing Management Commands**
4. **Utility Commands**

---

### 1. Client Management Commands

Commands for creating, updating, and deleting buyers and sellers.

![showClients](images/showClients.png)

- #### **Add Buyer Command**
    - **Format:** `buyer n/<NAME> p/<PHONE> e/<EMAIL> [t/<TAG>...]` 
    - **Description:** Creates a new buyer profile with specified details.
    - **Example**:  `buyer n/Bobby p/91432277 e/bobby123@gmail.com`

- #### **Add Seller Command**
    - **Format:** `seller n/<NAME> p/<PHONE> e/<EMAIL> [t/<TAG>...]`
    - **Description:** Creates a new seller profile with specified details.
    - **Example**:  `seller n/Johnny p/92341556 e/johnny456@gmail.com`

- #### **Find Command**
    - **Format:** `find KEYWORD [KEYWORD...]`
    - **Description:** Finds the specified client(s) based on the provided keywords.
    - **Example**:  `find Bobby Winter`

- #### **Edit Command**
    - **Format:** `edit INDEX [n/<NAME>] [p/<PHONE>] [e/<EMAIL>] [t/<TAG>...] [r/<REMARK>]`
    - **Description:** Edits the details of the specified client.
    - **Example**:  `edit 1 n/BobbyTan e/bobbytan123@gmail.com`

- #### **Delete Client Command**
    - **Format:** `buyer n/<NAME> p/<PHONE> e/<EMAIL>`
    - **Description:** Creates a new client profile with specified details
    - **Example**:  `buyer n/Bobby p/91432222 e/bobby123@gmail.com`

---

### 2. Appointment Management

Commands for managing appointments between user and clients.

![appointments](images/appointments.png)

- #### **Schedule Appointment**
    - **Format:** `apt INDEX d/<DD-MM-YYYY> fr/<HHmm> to/<HHmm>`
    - **Description:** Schedules a new appointment to be held with the specified client that includes the specified details (date, time).

- #### **Delete Appointment**
    - **Format:** `delapt n/<NAME>`
    - **Description:** Deletes an appointment with the specified client.

---

### 3. Listing Management

Commands for managing property listings and associating clients with listings.

![showListings](images/showListings.png)

- #### **Add Listing**
    - **Command:** `AddListingCommand`
    - **Description:** Adds a new property listing with specified details.

- #### **Show Listings**
    - **Command:** `ShowListingsCommand`
    - **Description:** Displays all current listings.

- #### **Add Buyers to Listing**
    - **Command:** `AddBuyersToListingCommand`
    - **Description:** Associates buyers with a specified listing.

- #### **Remove Buyers from Listing**
    - **Command:** `RemoveBuyersFromListingCommand`
    - **Description:** Removes buyers associated with a specified listing.

- #### **Delete Listing**
    - **Command:** `DeleteListingCommand`
    - **Description:** Deletes a specified listing.

- #### **Clear Listing**
    - **Command:** `ClearListingCommand`
    - **Description:** Deletes ALL listings.

---

### 4. Utility Commands

Miscellaneous commands for application utility, such as clearing, exiting, and displaying help.

- #### **Clear**
    - **Command:** `ClearCommand`
    - **Description:** Clears the console or application state.

- #### **Exit**
    - **Command:** `ExitCommand`
    - **Description:** Exits the application.

- #### **Help**
    - **Command:** `HelpCommand`
    - **Description:** Displays a list of available commands and their descriptions.

- #### **More Info**
    - **Command:** `MoreInfoCommand`
    - **Description:** Provides additional information about a specific command or feature.

- #### **List All Commands**
    - **Command:** `ListCommand`
    - **Description:** Lists all available commands in the application.

- #### **Chat Window**
    - **Command:** `ChatWindowCommand`
    - **Description:** Opens a chat window for client-agent communication.

---

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

#### Command Format
<div class="command-box">
help
</div>

### Adding a Person: `add`

Adds a person to the address book.

#### Command Format
<div class="command-box">
add n/NAME p/PHONE_NUMBER e/EMAIL
</div>

#### Successful Execution
**Example**
> **Use Case**: Adding a client named `John Doe` with phone number `98765432` and email `johnd@example.com`.
>
> **Input**:  `add n/John Doe p/98765432 e/johnd@example.com`
>
> **Output**:

#### Failed Execution
**Example**
> **User Error**: Missing a `PHONE` field.
>
> **Input**: `add n/Betsy Crowe e/betsycrowe@example.com`
>
> **Output**:


### Listing all Persons : `list`

Shows a list of all persons in the address book.

#### Command Format
<div class="command-box">
list
</div>

### Editing a Person : `edit`

Edits an existing person in the address book.

#### Command Format
<div class="command-box">
edit INDEX [n/NAME] [p/PHONE] [e/EMAIL]
</div>

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

#### Successful Execution
**Example 1**
> **Use Case**: Editing phone number and email address of the 1st person in the list to be `91234567` and `johndoe@example.com` respectively.
>
> **Input**:  `edit 1 p/91234567 e/johndoe@example.com`
>
> **Output**:

**Example 2**
> **Use Case**: Editing the name of the 2nd person in the list to be `Betsy Crower`.
>
> **Input**: `edit 2 n/Betsy Crower`
>
> **Output**:

#### Failed Execution

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

#### Command Format
<div class="command-box">
find KEYWORD [MORE_KEYWORDS]
</div>

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

#### Successful Execution
**Example 1**
> **Use Case**: Finding all clients with `John` in their names.
>
> **Input**: `find John`
>
> **Output**:

**Example 2**
> **Use Case**: Finding all clients with `Alex` *OR* `David` in their names.
>
> **Input**: `find alex david`
>
> **Output**: 2 persons listed!
  ![result for 'find alex david'](images/findAlexDavidResult.png)

#### Failed Execution
**Example 1**
> **User Error**: Attempting to find a user that does not exist.
>
> **Input**: `find BabyLockEmDoors`
>
> **Output**:

### Deleting a person : `delete`

Deletes the specified person from the address book.

#### Command Format
<div class="command-box">
delete n/NAME
</div>

* Deletes the person with the specified `NAME`.

#### Successful Execution
**Example**
> **Use Case**: Deleting `John Doe` from the address book.
>
> **Input**: `delete n/John Doe`
>
> **Output**:

#### Failed Execution
**Example**
> **User Error**: Attempting to delete someone that does not exist.
>
> **Input**:
>
> **Output**:

### Clearing all entries : `clear`

Clears all entries from the address book.

#### Command Format
<div class="command-box">
clear
</div>

### Exiting the program : `exit`

Exits the program.

#### Command Format
<div class="command-box">
exit
</div>

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL​` <br> e.g. `add n/James Ho p/22224444 e/jamesho@example.com`
**Clear** | `clear`
**Delete** | `delete NAME`<br> e.g. `delete James Ho`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL]​`<br> e.g.`edit 2 n/James Lee e/jameslee@example.com` <br> e.g.`edit 2 n/James Voo`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`
