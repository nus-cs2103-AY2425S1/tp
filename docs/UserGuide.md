---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# Prudy User Guide

Prudy is a **desktop app for Prudential financial agents to manage client policies and claims.** It is optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, Prudy can get your client management tasks done faster than traditional GUI apps.

What is Prudy used for:

- **Client Management:** Record important information of client's personal details such as their name and phone numbers, as well as their policies and claims.

- **Keyboard Centric Executions:** Prudy's commands are executed mostly using the keyboard instead of traditional point-and-click using the mouse, ensuring swift navigation without moving your hand from the keyboard, then to the mouse, and back to the keyboard repeatedly!

- **Filter Data:** Filter through clients and policies using our intuitive find functions.

- **Autosave:** Data in Prudy is automatically saved after each command, removing the need to manually save before closing the application and concerns for losing data.

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
    - 4.1 [Overview](#4-1-overview)
    - 4.2 [Commands](#4-2-commands)
    - 4.3 [Flags](#4-3-flags)
    - 4.4 [Arguments](#4-4-arguments)

5. [Commands Overview](#5-commands-overview)
    - 5.1 [General Commands](#5-1-general-commands)
        - 5.1.1 [Viewing Help](#5-1-1-viewing-help-help)
        - 5.1.2 [Clearing All Entries](#5-1-2-clearing-all-entries-clear)
        - 5.1.3 [Exiting the Program](#5-1-3-exiting-the-program-exit)
        - 5.1.4 [Saving the Data](#5-1-4-saving-the-data)
        - 5.1.5 [Editing the Data File](#5-1-5-editing-the-data-file)
    - 5.2 [Client Management Commands](#5-2-client-management-commands)
        - 5.2.1 [Adding a Client](#5-2-1-adding-a-client-add-client)
        - 5.2.2 [Listing All Clients](#5-2-2-listing-all-clients-list-clients)
        - 5.2.3 [Filtering Clients](#5-2-3-filtering-clients-find-client)
        - 5.2.4 [Editing a Client](#5-2-4-editing-a-client-edit-client)
        - 5.2.5 [Deleting a Client](#5-2-5-deleting-a-client-delete-client)
    - 5.3 [Policy Management Commands](#5-3-policy-management-commands)
        - 5.3.1 [Adding a Policy](#5-3-1-adding-a-policy-add-policy)
        - 5.3.2 [Deleting a Policy](#5-3-2-deleting-a-policy-delete-policy)
        - 5.3.3 [Editing a Policy](#5-3-3-editing-a-policy-edit-policy)
        - 5.3.4 [Listing All Policies](#5-3-4-listing-all-policies-list-policies)
        - 5.3.5 [Listing Expiring Policies](#5-3-5-listing-expiring-policies-list-expiring-policies)
    - 5.4 [Claims Management Commands](#5-4-claims-management-commands)
        - 5.4.1 [Adding a Claim](#5-4-1-adding-a-claim-add-claim)
        - 5.4.2 [Deleting a Claim](#5-4-2-deleting-a-claim-delete-claim)
        - 5.4.3 [Editing a Claim](#5-4-3-editing-a-claim-edit-claim)
        - 5.4.4 [Listing All Claims](#5-4-4-listing-all-claims-list-claims)


6. [FAQ](#6-faq)
7. [Known Issues](#7-known-issues)
8. [Command Summary](#8-command-summary)

--------------------------------------------------------------------------------------------------------------------

## 2. Prerequisites

Before you begin using Prudy, here are a few essential prerequisites to ensure a smooth experience, especially for beginner users:

1. **Opening a Command Terminal**:
   To use Prudy effectively, you’ll need to know how to open a terminal on your operating system:

    - **Windows**:
        1. Press `Windows Key + R` to open the Run dialog.
        2. Type `cmd` and press `Enter`.
        3. This will open the Command Prompt, which can be used as a terminal.

    - **MacOS**:
        1. Press `Command + Space` to open Spotlight Search.
        2. Type `Terminal` and press `Enter`.
        3. The Terminal application will open, ready for use.

    - **Linux**:
        1. Depending on your distribution, you can usually open the terminal by pressing `Ctrl + Alt + T`.
        2. Alternatively, look for the Terminal application in your system’s application menu.


2. **Basic Command Line Interface (CLI) Knowledge**:
   Prudy is optimized for CLI, allowing you to perform actions quickly through typed commands. If you're new to CLI, start by familiarizing yourself with basic commands such as:
    - `cd` to change directories
    - `ls` (or `dir` on Windows) to list files
    - `exit` to close the terminal
      This knowledge will help you navigate the system and use Prudy more efficiently.


3. **Keyboard Navigation Skills**:
   Prudy is designed to enhance speed and productivity, especially when using keyboard shortcuts. Familiarize yourself with basic keyboard shortcuts, such as:
    - `Enter` to execute a command
    - `Tab` to auto-complete directory paths
    - `Arrow keys` to navigate through previous commands
      These shortcuts will make it easier to work with Prudy’s CLI-based interface.


4. **Basic Understanding of Data Fields**:
   Prudy keeps track of essential client information such as names, phone numbers, emails, addresses, tags, policies, and claims. Familiarizing yourself with these data fields will make it easier to add, edit, and filter client information efficiently.


5. **Java 17 or Above Installed**:
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
You should see java version 17.

2. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-T14-1/tp/releases).


3. Copy the file to the folder you want to use as the _home folder_ for Prudy.


4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar prudy.jar` command to run the application.

💡**Tip**: If you’re unsure how to open a terminal, refer back to the [Prerequisites](#2-prerequisites) section for detailed instructions.

<br>A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>

   ![Ui](images/Ui.png)


5. Type the command in the command box and press Enter to execute it. e.g., typing **`help`** and pressing Enter will open the help window.<br>

   Some example commands you can try:

   * `list-clients` : Lists all clients.

   * `add-client n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a client named `John Doe` to Prudy.

   * `delete-client 3` : Deletes the 3rd client shown in the current list.

   * `clear` : Deletes all clients.

   * `exit` : Exits the app.


6. If you are a beginner, we recommend that you check out the [Command Structure](#4-command-structure) section to find out more about command syntax. Otherwise, you may also choose to skip and proceed directly to the [Command](#5-commands-overview) section for details of each command.

--------------------------------------------------------------------------------------------------------------------

## 4. Command Structure

Understanding the structure of Prudy’s commands will allow you to use the application more effectively. Each command in Prudy consists of **Commands**, **Flags**, and **Arguments**. This section explains each of these components and how they work together to execute actions in Prudy.

### 4.1 Overview

Prudy commands are structured to be straightforward and efficient, consisting of three parts:
1. **Command**: The main action, such as `add-client` or `delete-client`.
2. **Flags**: Short prefixes that define specific data fields, like `n/` for name or `p/` for phone.
3. **Arguments**: The actual values provided for each flag, such as a client’s name or email.

Here’s an example to demonstrate the structure:
```shell
add-client n/John Doe p/98765432 e/johnd@example.com a/123 Elm St
````
In this command:

- **Command**: `add-client` instructs Prudy to add a new client.
- **Flags**: `n/`, `p/`, `e/`, and `a/` specify the data fields (name, phone, email, address).
- **Arguments**: `John Doe`, `98765432`, `johnd@example.com`, and `123 Elm St` are the values for each flag.

### 4.2 Commands

Commands represent the **primary actions** Prudy will perform. Each command initiates a specific function within the app. These are **case-sensitive** as we want you to be very clear with what commands you want to execute. For example, typing `Exit` instead of `exit` is invalid. Here are the main commands:

| Command                  | Description                                                          |
|--------------------------|----------------------------------------------------------------------|
| `add-client`             | Adds a new client.                                                   |
| `edit-client`            | Modifies an existing client’s details.                               |
| `delete-client`          | Removes a client from Prudy.                                         |
| `list-client`            | Lists all clients stored in Prudy.                                   |
| `find-client`            | Searches clients based on specified criteria.                        |
| `add-policy`             | Adds a policy to a specific client.                                  |
| `delete-policy`          | Removes a policy or multiple policies from a specific client.        |
| `edit-policy`            | Modifies an existing policy of a client.                             |
| `list-policies`          | Lists all policies of a specific client.                             |
| `list-expiring-policies` | Lists all policies that are expiring after specified number of days. |
| `add-claim`              | Adds a claim under a client's policy.                                |
| `delete-claim`           | Removes a claim from a client's policy.                              |
| `edit-claim`             | Modifies an exisiting claim.                                         |
| `list-claims`            | Lists all claims under a specified client's policy.                  |
| `clear`                  | Deletes all client data, resetting Prudy.                            |
| `exit`                   | Closes the Prudy application.                                        |

These are the overview of Prudy's commands, please refer to [Commands Overview](#5-commands-overview) for a comprehensive list of Prudy's features.

### 4.3 Flags

**Flags** are used within commands to define **specific types of data** that Prudy will handle. They allow you to quickly indicate what information you’re providing. These are also **case-sensitive**. Below is a list of flags you can use:

| Flag  | Data Type               |
|-------|-------------------------|
| `n/`  | Name                    |
| `p/`  | Phone                   |
| `e/`  | Email                   |
| `a/`  | Address                 |
| `t/`  | Tags                    |
| `pt/` | Policy Type             |
| `pa/` | Policy Premium Amount   |
| `ca/` | Policy Coverage Amount  |
| `ed/` | Policy Expiry Date      |
| `s/`  | Claim Status            |
| `d/`  | Claim Description       |
| `c/`  | Claim Index             |


💡 **Tip**: Flags are generally derived from the first letter of the data type, making them easy to remember.

### 4.4 Arguments

**Arguments** are the **values provided for each flag** in a command. They must meet **certain requirements** to be valid. Here, some are **case-insensitive** to allow more flexibility in inputs. Here’s a list of common arguments in Prudy and their expected formats:

| Flag  | Expected Argument         | Description                      | Requirements                                            | Case-Sensitivity        |
|-------|----------------------------|----------------------------------|---------------------------------------------------------|------------------------|
| `n/`  | Client’s Full Name         | Full name of the client          | Letters and spaces only                                 | yes              |
| `p/`  | Phone                      | Contact number                   | 3-15 digit number                   | NIL               |
| `e/`  | Email                      | Email address                    | Standard format (e.g., user@example.com)                | yes                    |
| `a/`  | Address                    | Client’s address                 | Any alphanumeric and symbol                             | yes                   |
| `t/`  | Tags                       | Custom descriptor of client      | Letters and spaces only                                 | yes                 |
| `pt/` | Policy Type                | Type of insurance policy         | Predefined types (e.g., life, health, education)        | no                   |
| `pa/` | Policy Premium Amount      | Premium amount for the policy    | Positive decimal number (up to 2 decimal places)        | NIL                     |
| `ca/` | Policy Coverage Amount     | Coverage amount of the policy    | Positive decimal number                                 | NIL                 |
| `ed/` | Policy Expiry Date         | Expiry date of the policy        | Date format: MM/dd/yyyy                                 | NIL                   |
| `s/`  | Claim Status               | Status of the claim              | Predefined statuses (e.g., pending, approved, rejected) | no                       |
| `d/`  | Claim Description          | Description of the claim         | Any alphanumeric and symbol                             | yes                |
| `c/`  | Claim Index                | Claim index for reference        | Positive integer, used for identifying claims           | NIL               |


In Prudy, arguments ensure that the command functions as expected. Without correct arguments, Prudy may display an error message indicating the input is invalid.

<box type="info" seamless>
**Info:**
In the following commands section below, arguments and flags enclosed in square brackets <code>[]</code> are optional, while those suffixed with <code>…</code> can be used any number of times. For example, in the command <code>add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​</code>, the <code>[t/TAG]</code> part is optional and can be omitted if not needed. Furthermore, you can input multiple <code>[t/TAG]</code> in the above command.
</box>

--------------------------------------------------------------------------------------------------------------------

## 5. Commands Overview

This section will guide you on the commands available and how to use them correctly to optimise your workflow. If you have trouble understanding the command syntax, we highly recommend that you refer back to [Command Structure](#4-command-structure) as  basic knowledge of the command structure will give you a better understanding of our commands.

Prudy uses a command-line interface with **four primary categories** of commands to manage various aspects of **client data, policies**, and **claims**. Each command type serves a distinct purpose:

1. **General Commands**: Used for basic navigation, help, saving, and exiting the program.
2. **Client Management Commands**: Commands to add, edit, delete, and filter client information.
3. **Policy Management Commands**: Commands to manage policies for each client, including adding, editing, and deleting policies.
4. **Claims Management Commands**: Commands for managing client claims, including adding, editing, and deleting claims.

---

### 5.1 General Commands

These commands help with **general navigation**, displaying help information, saving data, and exiting the program.

<box type="info" seamless>
<span class="font-weight-bold">Note:</span> Extraneous parameters for commands that do not take in parameters (such as <code>help</code>, <code>list</code>, <code>exit</code>, and <code>clear</code>) will be ignored.
e.g., if the command specifies <code>help 123</code>, it will be interpreted as <code>help</code>.
</box>


#### 5.1.1 Viewing Help : `help`
Shows a message explaining how to **access the help page**.

![help message](images/helpMessage.png)

**Format:** `help`

#### 5.1.2 Clearing All Entries: `clear`
**Clears all entries** from Prudy, resetting the data.

**Format:** `clear`

<box type="warning" seamless>
Warning: This action is destructive and irreversible.
</box>

#### 5.1.3 Exiting the Program: `exit`
**Exits** the program.

**Format:** `exit`

#### 5.1.4 Saving the Data
Prudy **automatically saves data** to the hard disk after every command that modifies the data. Manual saving is **not required**.

#### 5.1.5 Editing the Data File
Prudy data is **automatically saved** as a JSON file at `[JAR file location]/data/prudy.json`. Advanced users may edit this file directly to update the data.

<box type="warning" seamless>
Caution:
If your changes to the data file makes its format invalid, Prudy will start with an empty data file at the next run. Subsequently, if you were to enter a command that modifies the data (e.g., <code>add-client</code>), Prudy will discard the old data, and replace with the new one. Hence, it is recommended to take a backup of the file before editing it.<br>

Furthermore, certain edits can cause Prudy to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

More on how to edit the data file in future updates!

---

### 5.2 Client Management Commands

**Client management commands** allow you to add, edit, delete, and filter client data.

#### 5.2.1 Adding a Client: `add-client`

Adds a **new client** to Prudy.

**Format:**
```shell
add-client n/NAME p/PHONE e/EMAIL a/ADDRESS [t/TAG]…​
```

<box type="info" seamless>
💡
**Tip:**
A client can have any number of tags (including 0).
</box>

<strong>Notes:</strong>
<ul>
  <li>The index refers to the index number shown in the displayed person list. The index must be a positive integer (1, 2, 3, …).</li>
  <li>At least one of the optional fields must be provided.</li>
  <li>For student contact, editing industry field is prohibited.</li>
  <li>For company contact, editing student ID field is prohibited.</li>
  <li>Existing values will be updated to the input values.</li>
  <li>When editing tags, the existing tags of the person will be removed, i.e., adding of tags is not cumulative.</li>
</ul>
</div>

**Examples:**
* `add-client n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add-client n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

<div style="border: 1px solid #e0e0e0; padding: 16px; border-radius: 8px; background-color: #f3faff; margin-bottom: 16px;">
**Tip:**
We are aware that `NAME` currently does not accept special characters such as `/`, `@`, and `,`, and will be adding this functionality in coming updates. For more information on the requirements of each arguments, refer back to [Arguments](#4-4-arguments).
</div>

**On success:**
```shell
success!
```

**Errors:**
```shell
invalid output
```

#### 5.2.2 Listing All Clients: `list-clients`
Shows a **list of all clients** in Prudy.

**Format:** `list-clients`

#### 5.2.3 Filtering Clients: `find-client`
**Filters clients** based on the specified parameters.

**Format:** `find-client [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [pt/POLICY_TYPE]…`

**Details:**
* At least one of the optional fields must be provided.
* The search is case-insensitive. e.g., `hans` will match `Hans`
* The order of the keywords does not matter. e.g., `Hans Bo` will match `Bo Hans`
* Only full words will be matched e.g., `Han` will not match `Hans`
* Clients matching at least one keyword will be returned (i.e. `OR` search).
  e.g., `Hans Bo` will return `Hans Gruber`, `Bo Yang`
* Only clients that match all parameters specified will be returned.
  e.g., `n/han pt/life` will return only clients that has `han` in his name and has a Life policy.

  <box type="info" seamless>
  Info: Each parameter must have a valid input (e.g., <code>PHONE</code> must be a 3-15 digits long, <code>POLICY_TYPE</code> must be a valid policy type - <code>Life</code>, <code>Education</code> or <code>Health</code>).
  </box>

**Examples:**
* `find-client n/John` returns `john` and `John Doe`
* `find-client n/alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find n/alex david'](images/findAlexDavidResult.png)

  <box type=warning seamless>
  Important: For the next few commands, an INDEX parameter is required. This INDEX is based on the current list of clients shown in Prudy.
               This means that if Prudy has 2 clients: <code>Alex</code> and <code>Bernice</code> given in that order, and you did <code>find-client n/bernice</code> to filter out <code>Alex</code>. An INDEX of <code>1</code> will refer to <code>Bernice</code> instead of <code>Alex</code>.
  </box>

#### 5.2.4 Editing a Client: `edit-client`

**Edits an existing client** in Prudy. **Does not edit his/her policies**. See [editing a policy](#5-3-3-editing-a-policy-edit-policy) for more info on the command.

**Format:** `edit-client INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

**Details:**
* `INDEX` refers to the position of the client in the displayed client list and must be a positive integer.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, **the existing tags of the client will be removed** i.e. adding of tags is not cumulative.
* You can remove all the client's tags by typing `t/` without
  specifying any tags after it.

**Examples:**
*  `edit-client 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st client to be `91234567` and `johndoe@example.com` respectively.
*  `edit-client 2 n/Betsy Crower t/` Edits the name of the 2nd client to be `Betsy Crower` and clears all existing tags.

#### 5.2.5 Deleting a Client: `delete-client`
**Deletes a specified client** at the specified `INDEX`.

**Format:** `delete-client INDEX`

**Examples:**
* `INDEX` refers to the position of the client in the displayed client list and must be a positive integer.
* `list-clients` followed by `delete-client 2` deletes the second client.
* `find-client Betsy` followed by `delete-client 1` deletes the first client in the search results.

---

### 5.3 Policy Management Commands

**Policy management commands** allow you to add, edit, and delete policies associated with each client.

#### 5.3.1 Adding a Policy: `add-policy`
Adds a **new policy** for a client in Prudy.

**Format:**
```shell
add-policy INDEX pt/POLICY_TYPE [pa/PREMIUM_AMOUNT] [ca/COVERAGE_AMOUNT] [ed/EXPIRY_DATE]
```

| **Parameter Name** | **Description**                                       | **Required** |
|--------------------|-------------------------------------------------------|--------------|
| INDEX              | The index of the client in the displayed client list. | yes          |
| pt/POLICY_TYPE     | The policy type.                                      | yes          |
| pa/PREMIUM_AMOUNT  | The policy's premium.                                 | no           |
| ca/COVERAGE_AMOUNT | The maximum amount that be claimed under this policy. | no           |
| ed/EXPIRY_DATE     | The policy's date of expiry.                          | no           |

<div style="border: 1px solid #e0e0e0; padding: 16px; border-radius: 8px; background-color: #D6EAF8; margin-bottom: 16px;">

🔔
**Notes:**
* `INDEX` refers to the position of the client in the displayed client list and must be a positive integer.
* `POLICY_TYPE` must be either `life`, `health`, or `education`.
* `PREMIUM_AMOUNT` and `COVERAGE_AMOUNT` must be non-negative numerals with at most two decimal places.
* `EXPIRY_DATE` format is `MM/dd/yyyy`.

</div>

<box type="info" seamless>

**Info:**
* This command will create a policy with default values for unspecified parameters.
* For example, if `PREMIUM_AMOUNT` is not specified, it will create a policy with default premiums (The default premium differs for different type of policies).

</box>

<div style="border: 1px solid #e0e0e0; padding: 16px; border-radius: 8px; background-color: #FCF3CF; margin-bottom: 16px;">

💡
**Tip:**
A client cannot have two or more policies with the same type.

</div>

**Examples:**
* `add-policy 1 pt/life` adds a Life policy with default values to the 1st client.
* `add-policy 2 pt/education pa/100 ed/08/24/2024` adds an Education policy with default coverage, a premium of $100 and an expiry date of 08/24/2024.

**On success:**
```shell
Added the following policy to NAME:

Policy type: POLICY_TYPE | Premium amount: PREMIUM_AMOUNT | Coverage amount: COVERAGE_AMOUNT | Expiry date: EXPIRY_DATE | No claims
```

#### 5.3.2 Deleting a Policy: `delete-policy`

**Delete policies** from a client in Prudy.

**Format:**
```shell
delete-policy INDEX pt/POLICY_TYPE…
```

| **Parameter Name** | **Description**                                       | **Required** |
|--------------------|-------------------------------------------------------|--------------|
| INDEX              | The index of the client in the displayed client list. | yes          |
| pt/POLICY_TYPE     | The policy type.                                      | yes          |

<div style="border: 1px solid #e0e0e0; padding: 16px; border-radius: 8px; background-color: #D6EAF8; margin-bottom: 16px;">

🔔
**Notes:**
* `INDEX` refers to the position of the client in the displayed client list and must be a positive integer.
* `POLICY_TYPE` must be either `life`, `health`, or `education`.

</div>

<box type="info" seamless>

**Info:**
* This command will not work if the specified policy to be deleted does not exists in Prudy.

</box>

<div style="border: 1px solid #e0e0e0; padding: 16px; border-radius: 8px; background-color: #FCF3CF; margin-bottom: 16px;">

💡
**Tip:**
More than one policy can be deleted using a single command.

</div>

**Examples:**
* `delete-policy 1 pt/life` deletes the Life policy from the 1st client.
* `delete-policy 2 pt/health pt/education` deletes the Health and Education policy from the 2nd client.

**On success:**
<br>
It will display the clients remaining policies.
```shell
Policies Left: 1. Policy type: POLICY_TYPE | Premium amount: PREMIUM_AMOUNT | Coverage amount: COVERAGE_AMOUNT | Expiry date: EXPIRY_DATE | Claims:
        Status: CLAIM_STATUS, Description: CLAIM_DESCRIPTION
```

#### 5.3.3 Editing a Policy: `edit-policy`
**Edit the policy** from the client at the specified `INDEX`, and of the specified `POLICY_TYPE`.

**Format:** `edit-policy INDEX pt/POLICY_TYPE [pa/PREMIUM_AMOUNT] [ca/COVERAGE_AMOUNT] [ed/EXPIRY_DATE]`

**Details:**
* `INDEX` refers to the position of the client in the displayed client list and must be a positive integer.
* `POLICY_TYPE` is case-insensitive, and can be either `life`, `health`, or `education`.
* At least one of the optional parameters must be indicated.
* Only the specified parameters will be edited. The other parameters not specified will not be changed.
* If the policy to be edited does not exist for the specified client, this command will not work.

**Examples:**
* `edit-policy 1 pt/life pa/200` Edit the Life policy of the 1st client to have a premium of $200. The policy's coverage and expiry date remain unchanged.
* `edit-policy 2 pt/health pa/300 ca/5000 ed/01/01/2030` Edit the Health policy of the 2nd client to have a premium of $300, coverage of $5000, and an expiry date of 01/01/2030.

#### 5.3.4 Listing All Policies: `list-policies`
Lists **all policies** associated with a client at the specified `INDEX`.

**Format:** `list-policies INDEX`

**Details:**
* `INDEX` refers to the position of the client in the displayed client list and must be a positive integer.
* This command displays a complete list of all policies associated with the client at the specified `INDEX`.
* No filtering or sorting is applied by this command; it shows all policies regardless of type or expiry date.

**Examples:**
* `list-policies 2` Displays all policies for the client at index 2, including life, health, and education policies.

#### 5.3.5 Listing Expiring Policies: `list-expiring-policies`
List all policies that are **expiring** within a specified number of days. If no arguments are provided, default to list all policies expiring after 30 days.

**Format:** `list-expiring-policies [DAYS]`

<box type=info seamless>
  Info: Preceding zeros in the `DAYS` argument will be ignored.
  For example, <code>list-expiring-policies 0023</code> and <code>list-expiring-policies 023</code> will both be treated as <code>list-expiring-policies 23</code>.
</box>

**Details:**
* The `DAYS` argument must be a positive integer (that is, greater than 0).
* Invalid or negative numbers will result in an error.

**Examples:**
* `list-expiring-policies` Lists all policies expiring within 30 days.
* `list-expiring-policies 10` Lists all policies expiring within 10 days.

---

### 5.4 Claims Management Commands

Claims management commands allow you to add, edit, delete, and list claims for clients.

#### 5.4.1 Adding a claim: `add-claim`
Adds a claim to the policy of the specified `POLICY_TYPE` for the client at the specified `INDEX`.

<box type=info seamless>
Note that a single policy cannot have similar claims. This is to prevent accidental adding of duplicate claims. Two claims are consider similar if they have the same <code>CLAIM_STATUS</code> and <code>CLAIM_DESCRIPTION</code>.
</box>

**Format:** `add-claim INDEX pt/POLICY_TYPE s/CLAIM_STATUS d/CLAIM_DESCRIPTION`

**Details:**
* `INDEX` refers to the position of the client in the displayed client list and must be a positive integer.
* `POLICY_TYPE` is case-insensitive and can be either `life`, `health`, or `education`.
* `CLAIM_STATUS` specifies the current status of the claim (e.g., pending, approved).
* `CLAIM_DESCRIPTION` provides a brief description of the claim details.

<box type=info seamless>
Info: This command will not allow adding a claim if the client has no policy of the specified type or if a similar claim already exists.
</box>

**Examples:**

* `add-claim 1 pt/health s/pending d/stomach surgery` Adds a claim with status "pending" and description "stomach surgery" to the health policy of the 1st client.
* `add-claim 2 pt/life s/approved d/accidental coverage` Adds a claim with status "approved" and description "accidental coverage" to the life policy of the 2nd client.

#### 5.4.2 Deleting a Claim: `delete-claim`
Deletes a specific claim from a policy type for the client identified by the specified INDEX.

**Format:** `delete-claim INDEX pt/POLICY_TYPE c/CLAIM_INDEX`

**Details:**
* `INDEX` refers to the position of the client in the displayed client list and must be a positive integer.
* `POLICY_TYPE` is case-insensitive and can be either `life`, `health`, or `education`.
* `CLAIM_INDEX` is the position of the claim in the policy's claim list and must be a positive integer.
* Use the `list-claims` command to find the appropriate claim index for the specified policy type.

<box type=info seamless>
Info: If the specified client, policy type, or claim does not exist, an error message will be shown.
</box>

**Examples:**
* `delete-claim 1 pt/health c/1` Deletes the claim at index 1 in the health policy of the 1st client.
* `delete-claim 2 pt/life c/2` Deletes the claim at index 2 in the life policy of the 2nd client.

#### 5.4.3 Editing a Claim: `edit-claim`
Edits a specific claim in a policy for the client identified by the specified INDEX.

**Format:** `edit-claim INDEX pt/POLICY_TYPE c/CLAIM_INDEX [s/CLAIM_STATUS] [d/CLAIM_DESCRIPTION]`

**Details:**
* `INDEX` refers to the position of the client in the displayed client list and must be a positive integer.
* `POLICY_TYPE` is case-insensitive and can be either `life`, `health`, or `education`.
* `CLAIM_INDEX` is the position of the claim in the policy's claim list and must be a positive integer.
* `CLAIM_STATUS` updates the claim status (e.g., `approved`, `pending`, or `rejected`).
* `CLAIM_DESCRIPTION` updates the description of the claim.
* Use the `list-claims` command to find the appropriate claim index for the specified policy type.

<box type=info seamless>
Info: At least one of `CLAIM_STATUS` or `CLAIM_DESCRIPTION` must be specified. If the claim details are unchanged or changing it will result in a duplicate claim, an error message will be shown.
</box>

**Examples:**

* `edit-claim 1 pt/health c/1 s/approved d/Updated surgery details` Edits the first claim in the health policy of the 1st client, updating the status to "approved" and the description to "Updated surgery details."
* `edit-claim 2 pt/life c/2 s/pending` Updates the status of the second claim in the life policy of the 2nd client to "pending."

#### 5.4.4 Listing All Claims: `list-claims`
Lists **all claims** under the specified policy type for the client identified by the index number used in the displayed client list.

**Format:** `list-claims INDEX pt/POLICY_TYPE`

**Details:**
* This command displays a complete list of all claims associated with the specified client's policy.
* The claims are listed in the order they were added.
* No filtering or sorting is applied by this command; it shows all existing claims.

**Examples:**
* `list-claims 1 pt/health` Lists all claims of the health policy for the first client.

--------------------------------------------------------------------------------------------------------------------

## 6. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Prudy home folder.

**Q**: Why am I getting an error when executing commands?<br>
**A**: Ensure that the command syntax (e.g. `list-claims INDEX pt/POLICTY_TYPE`) is correct, and that the flags (e.g. `pt/`) are entered correctly. You can refer to [Command Structure](#4-command-structure) to better understand the command structure. 



--------------------------------------------------------------------------------------------------------------------

## 7. Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.
3. **Clients with names consisting of special characters is not valid** in Prudy for now. The remedy is to omit these special characters when entering these clients' names.

--------------------------------------------------------------------------------------------------------------------

## 8. Command summary

| **Keyword**              | **Format**                                                                                   | **Examples**                                                                                              |
|--------------------------|----------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------|
| `help`                   | `help`                                                                                       | `help`                                                                                                    |
| `add-client`             | `add-client n/NAME p/PHONE e/EMAIL a/ADDRESS [t/TAG]…​`                                      | `add-client n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague` |
| `list-clients`           | `list-clients`                                                                               | `list-clients`                                                                                            |
| `find-client`            | `find-client [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [pt/POLICY_TYPE]…`                     | `find-client n/alex pt/life`                                                                              |
| `edit-client`            | `edit-client INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`                       | `edit-client 2 n/James Lee e/jameslee@example.com`                                                        |
| `delete-client`          | `delete-client INDEX`                                                                        | `delete-client 3`                                                                                         |
| `add-policy`             | `add-policy INDEX pt/POLICY_TYPE [pa/PREMIUM_AMOUNT] [ca/COVERAGE_AMOUNT] [ed/EXPIRY_DATE]`  | `add-policy 1 pt/life pa/100`                                                                             |
| `delete-policy`          | `delete-policy INDEX pt/POLICY_TYPE…`                                                        | `delete-policy 1 pt/life`                                                                                 |
| `edit-policy`            | `edit-policy INDEX pt/POLICY_TYPE [pa/PREMIUM_AMOUNT] [ca/COVERAGE_AMOUNT] [ed/EXPIRY_DATE]` | `edit-policy 1 pt/health ca/40000`                                                                        |
| `list-policies`          | `list-policies INDEX`                                                                        | `list-policies 1`                                                                                         |
| `List-expiring-policies` | `list-expiring-policies [DAYS]`                                                              | `list-expiring-policies 50`                                                                               |
| `add-claim`              | `add-claim INDEX pt/POLICY_TYPE s/CLAIM_STATUS d/CLAIM_DESCRIPTION`                          | `add-claim 1 pt/health s/pending d/stomach surgery`                                                       |
| `delete-claim`           | `delete-claim INDEX pt/POLICY_TYPE c/CLAIM_INDEX`                                            | `delete-claim 1 pt/health c/1`                                                                            |
| `edit-claim`             | `edit-claim INDEX pt/POLICY_TYPE c/CLAIM_INDEX [s/NEW_STATUS] [d/NEW_DESCRIPTION]`           | `edit-claim 1 pt/health c/1 s/approved d/Updated surgery details`                                         |
| `list-claims`            | `list-claims INDEX pt/POLICY_TYPE`                                                           | `list-claims 1 pt/health`                                                                                 |
| `clear`                  | `clear`                                                                                      | `clear`                                                                                                   |
| `exit`                   | `exit`                                                                                       | `exit`                                                                                                    |

