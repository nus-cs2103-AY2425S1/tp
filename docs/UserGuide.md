---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# Prudy User Guide

Prudy is a **desktop app for Prudential financial agents to manage client policies and claims.** It is optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, Prudy can get your client management tasks done faster than traditional GUI apps.

In this guide, you’ll learn how to:

- **Set Up Prudy** to start managing client policies and claims with ease.
- **Navigate and Use Key Features** such as adding, editing, and filtering client data, along with managing policies.
- **Optimize Your Workflow** by leveraging Prudy’s command-based data entry, powerful filtering options, and automatic data saving for streamlined client and policy management.

Now, let’s get started and unlock the full potential of Prudy for efficient client management!

--------------------------------------------------------------------------------------------------------------------

<!-- * Table of Contents -->
## Table of Contents

1. [Prudy User Guide](#prudy-user-guide)
2. [Prerequisites](#2-prerequisites)
3. [Getting Started](#3-getting-started)
4. [Command Structure](#4-command-structure)
    - 4.1 [Overview](#41-overview)
    - 4.2 [Commands](#42-commands)
    - 4.3 [Flags](#43-flags)
    - 4.4 [Arguments](#44-arguments)
5. [Commands Overview](#5-commands-overview)
    - 5.1 [General Commands](#51-general-commands)
        - 5.1.1 [Viewing Help](#511-viewing-help--help)
        - 5.1.2 [Clearing All Entries](#512-clearing-all-entries-clear)
        - 5.1.3 [Exiting the Program](#513-exiting-the-program-exit)
        - 5.1.4 [Saving the Data](#514-saving-the-data)
        - 5.1.5 [Editing the Data File](#515-editing-the-data-file)
    - 5.2 [Client Management Commands](#52-client-management-commands)
        - 5.2.1 [Adding a Client](#521-adding-a-client-add)
        - 5.2.2 [Listing All Clients](#522-listing-all-clients-list)
        - 5.2.3 [Filtering Clients](#523-filtering-clients-find-client)
        - 5.2.4 [Editing a Client’s Details](#524-editing-a-clients-details-edit)
        - 5.2.5 [Deleting a Client](#525-deleting-a-client-delete)
    - 5.3 [Policy Management Commands](#53-policy-management-commands)
        - 5.3.1 [Adding a Policy](#531-adding-a-policy-add-policy)
        - 5.3.2 [Deleting a Policy](#532-deleting-a-policy-delete-policy)
        - 5.3.3 [Editing a Policy](#533-editing-a-policy-edit-policy)
        - 5.3.4 [Listing All Policies](#534-listing-all-policies-list-policy)
        - 5.3.5 [Listing Expiring Policies](#535-listing-expiring-policies-listexpiringpolicies)
    - 5.4 [Claims Management Commands](#54-claims-management-commands)
        - [Adding Claims [coming in v2.0]](#adding-claims-coming-in-v20)
6.[FAQ](#faq)
7.[Known Issues](#known-issues)
8.[Command Summary](#command-summary)

--------------------------------------------------------------------------------------------------------------------

## 2. Prerequisites

Before you begin using Prudy, here are a few essential prerequisites to ensure a smooth experience, especially for beginner users:

1. **Basic Command Line Interface (CLI) Knowledge**:  
   Prudy is optimized for CLI, allowing you to perform actions quickly through typed commands. If you're new to CLI, start by familiarizing yourself with basic commands such as:
    - `cd` to change directories
    - `ls` (or `dir` on Windows) to list files
    - `exit` to close the terminal  
      This knowledge will help you navigate the system and use Prudy more efficiently.

2. **Keyboard Navigation Skills**:  
   Prudy is designed to enhance speed and productivity, especially when using keyboard shortcuts. Familiarize yourself with basic keyboard shortcuts, such as:
    - `Enter` to execute a command
    - `Tab` to auto-complete directory paths
    - `Arrow keys` to navigate through previous commands  
      These shortcuts will make it easier to work with Prudy’s CLI-based interface.

3. **Basic Understanding of Data Fields**:  
   Prudy keeps track of essential client information such as names, phone numbers, emails, addresses, tags, policies, and claims. Familiarizing yourself with these data fields will make it easier to add, edit, and filter client information efficiently.

4. **Java 17 or Above Installed**:  
   Prudy requires Java 17 or later. If you don’t have Java installed, you’ll need to install it before proceeding. Refer to the [Getting Started](#3-getting-started) section for installation instructions.

💡 **Tip for Beginners**: Taking the time to review these prerequisites will help you become comfortable with Prudy’s interface and enable you to manage client data efficiently.

--------------------------------------------------------------------------------------------------------------------

## 3. Getting Started

1. Ensure you have Java `17` or above installed in your Computer.
>To install Java 17:
> Go to the Java download page [here](https://www.oracle.com/java/technologies/downloads/#java17?er=221886).
> 
Verify Java version by copying the code below into the terminal:
```
java -version
```
You should see java version 17
2. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-T14-1/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for Prudy.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar prudy.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g., typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all clients.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a client named `John Doe` to Prudy.

   * `delete 3` : Deletes the 3rd client shown in the current list.

   * `clear` : Deletes all clients.

   * `exit` : Exits the app.

6. If you are a beginner, we recommend that you check out the [Command Structure]() section to find out more about command syntaxes. You may also choose to skip and proceed directly to the [Features](#features) section for details of each command.

--------------------------------------------------------------------------------------------------------------------

## 4. Command Structure

Understanding the structure of Prudy’s commands will allow you to use the application more effectively. Each command in Prudy consists of **Commands**, **Flags**, and **Arguments**. This section explains each of these components and how they work together to execute actions in Prudy.

### 4.1 Overview

Prudy commands are structured to be straightforward and efficient, consisting of three parts:
1. **Command**: The main action, such as `add` or `delete`.
2. **Flags**: Short prefixes that define specific data fields, like `n/` for name or `p/` for phone.
3. **Arguments**: The actual values provided for each flag, such as a client’s name or email.

Here’s an example to demonstrate the structure:
```shell
add n/John Doe p/98765432 e/johnd@example.com a/123 Elm St
````
In this command:

- **Command**: `add` instructs Prudy to add a new client.
- **Flags**: `n/`, `p/`, and `e/` specify the data fields (name, phone, email).
- **Arguments**: `John Doe`, `98765432`, `johnd@example.com` are the values for each flag.

### 4.2 Commands

Commands represent the primary actions Prudy will perform. Each command initiates a specific function within the app. Here are the main commands:

| Command | Description                                                         |
|---------|---------------------------------------------------------------------|
| `add`   | Adds a new client.                                                  |
| `edit`  | Modifies an existing client’s details.                              |
| `delete`| Removes a client from Prudy.                                        |
| `list`  | Lists all clients stored in Prudy.                                  |
| `filter`| Searches clients based on specified criteria.                       |
| `view`  | Displays detailed information for a selected client.                |
| `clear` | Deletes all client data, resetting Prudy.                           |
| `exit`  | Closes the Prudy application.                                       |

### 4.3 Flags

Flags are used within commands to define specific types of data that Prudy will handle. They allow you to quickly indicate what information you’re providing. Below is a list of flags you can use:

| Flag | Data Type         |
|------|--------------------|
| `n/` | Name              |
| `p/` | Phone Number      |
| `e/` | Email Address     |
| `a/` | Address           |
| `j/` | Job Title         |
| `i/` | Income            |
| `t/` | Tier              |
| `r/` | Remarks           |
| `ra/`| Append to Remark  |
| `rn/`| New Remark        |

💡 **Tip**: Flags are generally derived from the first letter of the data type, making them easy to remember.

### 4.4 Arguments

Arguments are the values provided for each flag in a command. They must meet certain requirements to be valid. Here’s a list of common arguments in Prudy and their expected formats:

| Flag | Expected Argument    | Description             | Requirements                                   |
|------|-----------------------|-------------------------|-----------------------------------------------|
| `n/` | Client’s Full Name    | Full name of the client | Letters and spaces only                        |
| `p/` | Phone Number          | Contact number          | 8-digit number starting with 8 or 9            |
| `e/` | Email                 | Email address           | Standard format (e.g., user@example.com)       |
| `a/` | Address               | Client’s address        | Any alphanumeric and symbol                    |
| `j/` | Job Title             | Profession/occupation   | Letters and spaces only                        |
| `i/` | Income                | Annual income           | Positive integer, no symbols                   |
| `t/` | Tier                  | Client classification   | `Gold`, `Silver`, `Bronze`, `Reject`           |
| `r/` | Remark                | Additional information  | Any alphanumeric and symbol                    |

In Prudy, arguments ensure that the command functions as expected. Without correct arguments, Prudy may display an error message indicating the input is invalid.

--------------------------------------------------------------------------------------------------------------------

## 5. Commands Overview

Prudy uses a command-line interface with four primary categories of commands to manage various aspects of client data, policies, and claims. Each command type serves a distinct purpose:

1. **General Commands**: Used for basic navigation, help, saving, and exiting the program.
2. **Client Management Commands**: Commands to add, edit, delete, and filter client information.
3. **Policy Management Commands**: Commands to manage policies for each client, including adding, editing, and deleting policies.
4. **Claims Management Commands**: Commands for managing client claims (available in future updates).

---

### 5.1 General Commands

These commands help with general navigation, displaying help information, saving data, and exiting the program.

### 5.1.1 Viewing help : `help`
Shows a message explaining how to access the help page.  
![help message](images/helpMessage.png)  
Format: `help`

### 5.1.2 Clearing All Entries: `clear`
Clears all entries from Prudy, resetting the data.  
Format: `clear`

  <box type=warning seamless>
  **Warning:** This action is destructive and irreversible.
  </box>

### 5.1.3 Exiting the Program: `exit`
Exits the program.  
Format: `exit`

### 5.1.4 Saving the Data
Prudy data are saved on the hard disk automatically after any command that changes the data. There is no need to save manually.

### 5.1.5 Editing the Data File
Prudy data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing this file.

  <box type="warning" seamless>
  **Caution:**  
If your changes to the data file makes its format invalid, Prudy will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause Prudy to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
  </box>

---

### 5.2 Client Management Commands

Client management commands allow you to add, edit, delete, and filter client data.

### 5.2.1 Adding a Client: `add`
Adds a new client to Prudy.  
Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

  <box type="tip" seamless>
  **Tip:** A client can have any number of tags (including 0).
  </box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### 5.2.2 Listing All Clients: `list`
Shows a list of all clients in Prudy.  
Format: `list`

### 5.2.3 Filtering Clients: `find-client`
Filters clients based on the specified parameters.  
Format: `find-client [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [pt/POLICY_TYPE]…`

Details:
* At least one of the optional fields must be provided.
* The search is case-insensitive. e.g., `hans` will match `Hans`
* The order of the keywords does not matter. e.g., `Hans Bo` will match `Bo Hans`
* Only full words will be matched e.g., `Han` will not match `Hans`
* Clients matching at least one keyword will be returned (i.e. `OR` search).
  e.g., `Hans Bo` will return `Hans Gruber`, `Bo Yang`
* Only clients that match all parameters specified will be returned.
  e.g., `n/han pt/life` will return only clients that has `han` in his name and has a Life policy.

  <box type="info" seamless>
  **Info:** Each parameter must have a valid input (e.g., PHONE must be a valid 8-digit number).
  </box>

Examples:
* `find n/John` returns `john` and `John Doe`
* `find n/alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find n/alex david'](images/findAlexDavidResult.png)

  <box type=warning seamless>
  **Important:** For the next few commands, an INDEX parameter is required. This INDEX is based on the current list of clients shown in Prudy.
               This means that if Prudy has 2 clients: `Alex` and `Bernice` given in that order, and you did `find-client n/bernice` to filter out `Alex`. An INDEX of `1` will refer to `Bernice` instead of `Alex`.
  </box>

### 5.2.4 Editing a Client’s Details: `edit`
Edits an existing client in Prudy. Does not edit his/her policies. See [editing a policy](#editing-a-policy-edit-policy) for more info on the command.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the client at the specified `INDEX`.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the client will be removed i.e adding of tags is not cumulative.
* You can remove all the client's tags by typing `t/` without
  specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st client to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd client to be `Betsy Crower` and clears all existing tags.

### 5.2.5 Deleting a Client: `delete`
Deletes a specified client at the specified `INDEX`.  
Format: `delete INDEX`

Examples:
* `list` followed by `delete 2` deletes the second client.
* `find Betsy` followed by `delete 1` deletes the first client in the search results.

---

### 5.3 Policy Management Commands

Policy management commands allow you to add, edit, and delete policies associated with each client.

### 5.3.1 Adding a Policy: `add-policy`
Adds a policy to the client at the specified `INDEX`.  
Format: `add-policy INDEX pt/POLICY_TYPE [pa/PREMIUM_AMOUNT] [ca/COVERAGE_AMOUNT] [ed/EXPIRY_DATE]`

Details:
* `POLICY_TYPE` is case-insensitive and can be either `life`, `health`, or `education`.
* `PREMIUM_AMOUNT` and `COVERAGE_AMOUNT` must be non-negative numerals.
* `EXPIRY_DATE` format is `MM/dd/yyyy`.
* This command will create a policy with default values for unspecified parameters.
e.g., `pt/life ca/100 ed/12/09/2024` will create a Life policy with default premiums.

  <box type=info seamless>
  **Info:** This command will not allow you to add a policy to the client if he/she already has a policy of similar type. 
  </box>

Examples:
* `add-policy 1 pt/life` Adds a Life policy with default values to the 1st client.
* `add-policy 2 pt/education pa/100.00 ed/08/24/2024` Adds an Education policy with default coverage, a premium of $100.00 and an expiry date of 08/24/2024.

### 5.3.2 Deleting a Policy: `delete-policy`
Delete policies from the client at the specified `INDEX`, and of the specified `POLICY_TYPE`.
Format: `delete-policy INDEX pt/POLICY_TYPE…`

Details:
* `POLICY_TYPE` is case insensitive, and can be either `life`, `health`, or `education`.
* More than one policy type can be deleted at once. However, calling this command with zero policy type indicated will not be successful.
* If the policy to be deleted does not exist for the specified client, this command will not work.

Examples:
* `delete-policy 1 pt/life` Deletes the Life policy from the 1st client.
* `delete-policy 2 pt/health pt/education`Deletes the Health and Education policy from the 2nd client.

### 5.3.3 Editing a Policy: `edit-policy`
Edit the policy from the client at the specified `INDEX`, and of the specified `POLICY_TYPE`.  
Format: `edit-policy INDEX pt/POLICY_TYPE [pa/PREMIUM_AMOUNT] [ca/COVERAGE_AMOUNT] [ed/EXPIRY_DATE]`

Details:
* `POLICY_TYPE` is case insensitive, and can be either `life`, `health`, or `education`.
* At least one of the optional parameters must be indicated.
* Only the specified parameters will be edited. The other parameters not specified will not be changed.
* If the policy to be edited does not exist for the specified client, this command will not work.

Examples:
* `edit-policy 1 pt/life pa/200` Edit the Life policy of the 1st client to have a premium of $200. The policy's coverage and expiry date remain unchanged.
* `edit-policy 2 pt/health pa/300 ca/5000 ed/01/01/2030` Edit the Health policy of the 2nd client to have a premium of $300, coverage of $5000, and an expiry date of 01/01/2030.

### 5.3.4 Listing All Policies: `list-policy`
Lists all policies stored in Prudy.  
Format: `list-policy`

### 5.3.5 Listing Expiring Policies: `listExpiringPolicies`
List all policies that are expiring after a specified number of days. If no arguments are provided, default to list all policies expiring after 30 days.  
Format: `listExpiringPolicies [DAYS]`

---

### 5.4 Claims Management Commands

Claims management commands will be available in Prudy version 2.0. This feature will introduce new commands to add, edit, delete, and list claims for clients.

#### Adding Claims `[coming in v2.0]`
Details coming soon ...

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Prudy home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action                     | Format, Examples
---------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Help**                   | `help`
**Add**                    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**List**                   | `list`
**Find client**            | `find-client [n/name] [p/PHONE] [e/EMAIL] [a/ADDRESS] [pt/POLICY_TYPE]…` <br> e.g., `find-client n/alex pt/life`
**Edit**                   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​` <br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Delete**                 | `delete INDEX` <br> e.g., `delete 3`
**Add policy**             | `add-policy INDEX pt/POLICY_TYPE [pa/PREMIUM_AMOUNT] [ca/COVERAGE_AMOUNT] [ed/EXPIRY_DATE]` <br> e.g., `add-policy 1 pt/life pa/100`
**Delete policy**          | `delete-policy INDEX pt/POLICY_TYPE…` <br> e.g., `delete-policy 1 pt/life`
**Edit policy**            | `edit-policy INDEX pt/POLICY_TYPE [pa/PREMIUM_AMOUNT] [ca/COVERAGE_AMOUNT] [ed/EXPIRY_DATE]` <br> e.g., `edit-policy 1 pt/health ca/40000`
**List policies**          | `list-policy`
**List expiring policies** | `listExpiringPolicies [DAYS]` <br> e.g., `listExpiringPolicies 50`
**Clear**                  | `clear`
**Exit**                   | `exit`
