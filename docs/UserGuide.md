---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# Prudy User Guide

Prudy is a **desktop app for Prudential financial agents to manage client policies and claims.** It is optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, Prudy can get your client management tasks done faster than traditional GUI apps.

--------------------------------------------------------------------------------------------------------------------

<!-- * Table of Contents -->
## Table of Contents

- [Prudy User Guide](#prudy-user-guide)
- [Quick Start](#quick-start)
- [Features](#features)
  - [Viewing Help](#viewing-help--help)
  - [Adding a Client](#adding-a-client-add)
  - [Listing All Clients](#listing-all-clients--list)
  - [Filtering Clients](#filtering-clients-find-client)
  - [Editing a Client](#editing-a-client-details-excluding-policies--edit)
  - [Deleting a Client](#deleting-a-client--delete)
  - [Adding a Policy](#adding-a-policy--add-policy)
  - [Deleting a Policy](#deleting-a-policy-delete-policy)
  - [Editing a Policy](#editing-a-policy-edit-policy)
  - [Listing All Policies](#listing-all-policies-list-policy)
  - [Listing Expiring Policies](#listing-all-expiring-policies-listexpiringpolicies)
  - [Clearing All Entries](#clearing-all-entries--clear)
  - [Exiting the Program](#exiting-the-program--exit)
  - [Saving the Data](#saving-the-data)
  - [Editing the Data File](#editing-the-data-file)
  - [Adding Claims \[coming in v2.0\]](#adding-claims-coming-in-v20)
- [FAQ](#faq)
- [Known Issues](#known-issues)
- [Command Summary](#command-summary)

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-T14-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for Prudy.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar Prudy.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g., typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all clients.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a client named `John Doe` to Prudy.

   * `delete 3` : Deletes the 3rd client shown in the current list.

   * `clear` : Deletes all clients.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g., in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g., `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g., `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g., if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g., if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a client: `add`

Adds a client to Prudy.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A client can have any number of tags (including 0)
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all clients : `list`

Shows a list of all clients in Prudy.

Format: `list`

### Filtering clients: `find-client`

Filter clients based on the given parameters.

Format: `find-client [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [pt/POLICY_TYPE]…`

* At least one of the optional fields must be provided.
* The search is case-insensitive. e.g., `hans` will match `Hans`
* The order of the keywords does not matter. e.g., `Hans Bo` will match `Bo Hans`
* Only full words will be matched e.g., `Han` will not match `Hans`
* Clients matching at least one keyword will be returned (i.e. `OR` search).
  e.g., `Hans Bo` will return `Hans Gruber`, `Bo Yang`
* Only clients that match all parameters specified will be returned.
  e.g., `n/han pt/life` will return only clients that has `han` in his name and has a Life policy.

<box type="info" seamless>

**Info:** For each parameters, a valid input must be given.
          e.g., `PHONE` in `pt/PHONE` must be a valid phone number with 8 digits.
</box>

Examples:
* `find n/John` returns `john` and `John Doe`
* `find n/alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find n/alex david'](images/findAlexDavidResult.png)

<box type=warning seamless>

**Important:** For the next few commands, an INDEX parameter is required. This INDEX is based on the current list of clients shown in Prudy.
               This means that if Prudy has 2 clients: `Alex` and `Bernice` given in that order, and you did `find-client n/bernice` to filter out `Alex`. An INDEX of `1` will refer to `Bernice` instead of `Alex`.
</box>

### Editing a client details (excluding policies) : `edit`

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

### Deleting a client : `delete`

Deletes the specified client from Prudy.

Format: `delete INDEX`

* Deletes the client at the specified `INDEX`.

Examples:
* `list` followed by `delete 2` deletes the 2nd client in Prudy.
* `find Betsy` followed by `delete 1` deletes the 1st client in the results of the `find` command.

### Adding a policy : `add-policy`

Adds a policy to the specified client from Prudy

Format: `add-policy INDEX pt/POLICY_TYPE [pa/PREMIUM_AMOUNT] [ca/COVERAGE_AMOUNT] [ed/EXPIRY_DATE]`

* Adds a policy to the client at the specified `INDEX`.
* `POLICY_TYPE` is case insensitive, and can be either `life`, `health`, or `education`.
* `PREMIUM_AMOUNT` and `COVERAGE_AMOUNT` must be a non negative numeral of at most 2 decimal places
* `EXPIRY_DATE` is in the format `MM/dd/yyyy`.
* This command will create a policy with default values for unspecified parameters.
  e.g., `pt/life ca/100 ed/12/09/2024` will create a Life policy with default premiums.

<box type=info seamless>

**Info:** This command will not allow you to add a policy to the client if he/she already has a policy of similar type.
</box>

Examples:
* `add-policy 1 pt/life` Adds a Life policy with default values to the 1st client.
* `add-policy 2 pt/education pa/100.00 ed/08/24/2024` Adds an Education policy with default coverage, a premium of $100.00 and an expiry date of 08/24/2024.

### Deleting a policy: `delete-policy`

Deletes policies from the specified client in Prudy

Format: `delete-policy INDEX pt/POLICY_TYPE…`

* Delete policies from the client at the specified `INDEX`, and of the specified `POLICY_TYPE`.
* `POLICY_TYPE` is case insensitive, and can be either `life`, `health`, or `education`.
* More than one policy type can be deleted at once. However, calling this command with zero policy type indicated will not be successful.
* If the policy to be deleted does not exist for the specified client, this command will not work.

Examples:
* `delete-policy 1 pt/life` Deletes the Life policy from the 1st client.
* `delete-policy 2 pt/health pt/education` Deletes the Health and Education policy from the 2nd client.

### Editing a policy: `edit-policy`

Edit the specified policy in Prudy

Format: `edit-policy INDEX pt/POLICY_TYPE [pa/PREMIUM_AMOUNT] [ca/COVERAGE_AMOUNT] [ed/EXPIRY_DATE]`

* Edit the policy from the client at the specified `INDEX`, and of the specified `POLICY_TYPE`.
* `POLICY_TYPE` is case insensitive, and can be either `life`, `health`, or `education`.
* At least one of the optional parameters must be indicated.
* Only the specified parameters will be edited. The other parameters not specified will not be changed.
* If the policy to be edited does not exist for the specified client, this command will not work.

Examples:
* `edit-policy 1 pt/life pa/200` Edit the Life policy of the 1st client to have a premium of $200. The policy's coverage and expiry date remain unchanged.
* `edit-policy 2 pt/health pa/300 ca/5000 ed/01/01/2030` Edit the Health policy of the 2nd client to have a premium of $300, coverage of $5000, and an expiry date of 01/01/2030.

### Listing all policies: `list-policy`

List all policies in Prudy

Format: `list-policy`

### Listing all expiring policies: `listExpiringPolicies`

List all policies that are expiring after a specified number of days. If no arguments are provided, default to list all policies expiring after 30 days.

Format: `listExpiringPolicies [DAYS]`

* List all policies that are expiring after `DAYS`.
* If no arguments are provided, default to list all policies expiring after 30 days.

### Clearing all entries : `clear`

Clears all entries from Prudy.

Format: `clear`

<box type=warning seamless>

**Warning:** This action is destructive and irreversible.
</box>

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

Prudy data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

Prudy data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, Prudy will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause Prudy to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Adding claims `[coming in v2.0]`

_Details coming soon ..._

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
