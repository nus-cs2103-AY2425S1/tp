---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# <h1 style="color: #964B00;">MATER User Guide</h1>

MATER is a **desktop app for managing clients and their cars, optimized for use via a Graphical User Interface (GUI).** It is designed for workshops that need to keep track of their clients and their cars. If you can type fast, MATER can get your client management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## <h2 style="color: #28B463;">Quick start</h2>

1. Ensure you have Java 17 or above installed on your Computer.

2. Download the latest .jar file from [here](https://github.com/AY2425S1-CS2103T-T14-3/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your MATER.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar mater.jar` command to run the application.<br>
   A GUI similar to the one below should appear in a few seconds. Note how the app contains some sample data.<br><br>
   ![Ui](images/Ui.png)

5. In the application window, locate the command box at the bottom.<br>
   Type the command in the command box and press Enter to execute it. For example, typing **help** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   - `list` : Lists all contacts.

   - `add-client n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a client named John Doe to MATER.

   - `delete-client 3` : Deletes the 3rd client shown in the current list.

   - `clear` : Deletes all contacts.

   - `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

### <h3 style="color: #AF7AC5;">Glossary</h3>

- **Client:** A person who uses the services of the workshop. In MATER, a client is someone whose personal details are stored and managed.
- **VRN (Vehicle Registration Number):** The unique identifier assigned to a vehicle upon registration. In Singapore, it typically follows the format `XXX 1234 C`, where:
  - `X` represents an alphabet letter.
  - `1234` represents a four-digit number.
  - `C` represents a checksum letter as defined by the [Land Transport Authority (LTA)](https://en.wikipedia.org/wiki/Vehicle_registration_plates_of_Singapore).
- **VIN (Vehicle Identification Number):** A unique 17-character alphanumeric code assigned to each vehicle by the manufacturer, used to identify individual motor vehicles.
- **Checksum Letter:** The final character in a VRN, calculated based on the other characters to ensure the VRN's validity.
- **Issue:** A problem or maintenance task associated with a client's car. Issues can be recorded in MATER for tracking and management purposes.
- **Command Line Interface (CLI):** A text-based user interface used to interact with MATER by typing commands into a console or terminal.
- **Graphical User Interface (GUI):** A visual interface that allows users to interact with MATER through graphical icons and visual indicators.
- **Command Prefixes:** Short codes used in commands to specify the type of data being entered. For example:
  - `n/` for name
  - `p/` for phone number
  - `e/` for email address
  - `a/` for address
  - `vrn/` for Vehicle Registration Number
  - `vin/` for Vehicle Identification Number
  - `make/` for car make
  - `model/` for car model
  - `i/` for issue
- **Index:** The numerical position of an item in a list, starting from 1. Used in commands like `edit-client` and `delete-client` to specify which client to modify.
- **Field:** An individual piece of data associated with a client or car (e.g., name, phone number, VIN).
- **Optional Field:** A parameter in a command that is not required but can be provided to include additional information. Denoted by square brackets `[]` in command formats.

--------------------------------------------------------------------------------------------------------------------

## <h2 style="color: #037c6e;">Features</h2>

<box type="info" seamless>

**Notes about the Command Format:**

- **Parameters in UPPER_CASE** are to be supplied by the user.
  - e.g., in `add-client n/NAME`, `NAME` is a parameter which can be used as `add-client n/John Doe`.
- **Items in square brackets are optional.**
  - e.g., `n/NAME [i/ISSUE]` can be used as `n/John Doe i/oil` or as `n/John Doe`.
- **Items with `...` after them can be used multiple times, including zero times.**
  - e.g., `[i/ISSUE]...` can be used as   (i.e., 0 times), `i/wheel`, `i/steering i/oil`, etc.
- **Parameters can be in any order.**
  - e.g., if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.
- **Extraneous parameters for commands that do not take in parameters will be ignored.**
  - e.g., if the command is `help`, `help 123` will be interpreted as `help`.
- **Copying commands from PDFs:**
  - If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines, as space characters surrounding line-breaks may be omitted when copied over to the application.

</box>

### <h3 style="color: #90E4C1;">Viewing Help: help</h3>

Shows a message explaining how to access the help page.

**Format:** `help`

**Parameters:** None.

**Requirements:** None.

![help message](images/helpMessage.png)

---

### <h3 style="color: #90E4C1;">Adding a Client: add-client</h3>

Adds a client to MATER.

**Format:**

```shell
add-client n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [vrn/CAR_VRN] [vin/CAR_VIN] [make/CAR_MAKE] [model/CAR_MODEL] [i/ISSUE]...​
```

**Parameters:**

- `n/NAME`: Client's name (required).
- `p/PHONE_NUMBER`: Client's phone number (required).
- `e/EMAIL`: Client's email address (required).
- `a/ADDRESS`: Client's address (required).
- `vrn/CAR_VRN`: Car's Vehicle Registration Number (optional).
- `vin/CAR_VIN`: Car's Vehicle Identification Number (optional).
- `make/CAR_MAKE`: Car's make (optional).
- `model/CAR_MODEL`: Car's model (optional).
- `i/ISSUE`: Issue(s) associated with the client (optional, multiple entries allowed).

**Requirements:**

- If adding a client with a car, all car fields (i.e., `vrn`, `vin`, `make`, and `model`) must be provided, but if the client does not have a car, these fields should be omitted completely.
- **VRN** must follow the format `XXX 1234 C`, where:
  - `X` is an alphabet.
  - `1234` is a number.
  - `C` is the checksum as defined by [LTA](https://en.wikipedia.org/wiki/Vehicle_registration_plates_of_Singapore).
  - Ensure spaces are included as shown.
- **VIN** must be a 17-character alphanumeric string.
- **VRN** and **VIN** must be unique for each car.

<box type="tip" seamless>

**Tip:**

- A client can have any number of issues (including 0).
- A client may be added with or without a car.

</box>

**Examples:**

- `add-client n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
- `add-client n/Betsy Crowe e/betsycrowe@example.com p/92345678 a/Newgate Prison vrn/SJH 9514 P vin/1G6ABC129P5123456 make/Toyota model/Corolla`

---

### <h3 style="color: #90E4C1;">Listing All Clients: list</h3>

Shows a list of all clients in MATER.

**Format:** `list`

**Parameters:** None.

**Requirements:** None.

---

### <h3 style="color: #90E4C1;">Editing a Client: edit-client</h3>

Edits an existing client in MATER.

**Format:**

```shell
edit-client INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [vrn/CAR_VRN] [vin/CAR_VIN] [make/CAR_MAKE] [model/CAR_MODEL] [i/ISSUE]...​
```

**Parameters:**

- `INDEX`: The index of the client in the displayed client list (must be a positive integer).
- `n/NAME`: New name of the client (optional).
- `p/PHONE`: New phone number of the client (optional).
- `e/EMAIL`: New email address of the client (optional).
- `a/ADDRESS`: New address of the client (optional).
- `vrn/CAR_VRN`: New Vehicle Registration Number of the client's car (optional).
- `vin/CAR_VIN`: New Vehicle Identification Number of the client's car (optional).
- `make/CAR_MAKE`: New make of the client's car (optional).
- `model/CAR_MODEL`: New model of the client's car (optional).
- `i/ISSUE`: New issue for the client (optional, multiple entries allowed).

**Requirements:**

- The `INDEX` must be a positive integer (1, 2, 3, ...).
- At least one of the optional fields must be provided.
- Existing values will be updated to the input values.
- When editing issues, the existing issues of the client will be removed; adding issues is not cumulative.
- To remove all issues from the client, type `i/` without specifying any issues after it.
- VRN and VIN requirements are the same as for the [add-client](#adding-a-client-add-client) command.

**Examples:**

- `edit-client 1 p/91234567 e/johndoe@example.com`
  Edits the phone number and email address of the 1st client to `91234567` and `johndoe@example.com` respectively.
- `edit-client 2 n/Betsy Crower i/`
  Edits the name of the 2nd client to `Betsy Crower` and clears all existing issues.

---

### <h3 style="color: #90E4C1;">Locating Clients by Name: find</h3>

Finds clients whose names/ car VRN contain any of the given keywords.

**Format:**

```shell
find KEYWORD [MORE_KEYWORDS]
```

**Parameters:**

- `KEYWORD`: A keyword to search for in client names / car VRN (required).
- `[MORE_KEYWORDS]`: Additional keywords (optional).

**Requirements:**

- The search checks both the client's name and the car's Vehicle Registration Number (VRN).
- The search is case-insensitive (e.g., `hans` will match `Hans`).
- The order of the keywords does not matter (e.g., `Hans Bo` will match `Bo Hans`).
- Only full words will be matched (e.g., `Han` will not match `Hans`).
- Clients / VRN matching at least one keyword will be returned (i.e., an `OR` search).

**Examples:**

- `find John`
  Returns clients with names like `John` and `John Doe`.
- `find alex david`
  Returns `Alex Yeoh`, `David Li`.
- `find SJH 9514 P`
  Returns client with car VRN `SJH 9514 P`.

![Result for 'find alex david'](images/findAlexDavidResult.png)

---

### <h3 style="color: #90E4C1;">Deleting a Client: delete-client</h3>

Deletes the specified client from MATER.

**Format:**

```shell
delete-client INDEX
```

**Parameters:**

- `INDEX`: The index of the client in the displayed client list (must be a positive integer).

**Requirements:**

- The `INDEX` must be a positive integer (1, 2, 3, ...).
- The index refers to the index number shown in the displayed client list.

**Examples:**

- `list`
  `delete-client 2`
  Deletes the 2nd client in the MATER.
- `find Betsy`
  `delete-client 1`
  Deletes the 1st client in the results of the `find` command.

---

### <h3 style="color: #90E4C1;">Clearing All Entries: clear</h3>

Clears all entries from MATER.

<box type="warning" seamless>

**Warning:** This command will delete all clients from MATER. This action is irreversible.

</box>

**Format:**

```shell
clear
```

**Parameters:** None.

**Requirements:** None.

---

### <h3 style="color: #90E4C1;">Exiting the Program: exit</h3>

Closes the MATER application.

**Format:**

```shell
exit
```

**Parameters:** None.

**Requirements:** None.

---

### <h3 style="color: #E67E22;">Saving the Data</h3>

MATER data are saved automatically in the hard disk after any command that changes the data. There is no need to save manually.

---

### <h3 style="color: #E67E22;">Editing the Data File</h3>

MATER data are saved automatically as a JSON file at `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing this data file.

<box type="warning" seamless>

**Caution: FOR EXPERT USERS ONLY**

- If your changes to the data file make its format invalid, MATER will discard all data and start with an empty data file at the next run. It is recommended to take a backup of the file before editing it.
- Certain edits can cause the MATER to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.

</box>

--------------------------------------------------------------------------------------------------------------------

## <h2 style="color: #8E44AD;">FAQ</h2>

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous MATER home folder.

**Q**: What happens if I enter an invalid command?<br>
**A**: MATER will display an error message. You can refer to the [Features](features) section for the correct command format or check the `HELP` section in the app.

**Q**: Can I undo a command?<br>
**A**: No, MATER does not support undoing commands. Please double-check your commands before executing them.

--------------------------------------------------------------------------------------------------------------------

## <h2 style="color: #C0392B;">Known issues</h2>

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## <h2 style="color: #2980B9;">Command Summary</h2>

Action             | Format & Examples
-------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------
**Add Client**     | `add-client n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [vrn/CAR_VRN] [vin/CAR_VIN] [make/CAR_MAKE] [model/CAR_MODEL] [i/ISSUE]...`<br>e.g., `add-client n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`<br>e.g., `add-client n/Betsy Crowe p/92345678 e/betsycrowe@example.com a/Newgate Prison vrn/SJH 9514 P vin/1G6ABC129P5123456 make/Toyota model/Corolla i/engine i/wheels`
**List Clients**   | `list`
**Edit Client**    | `edit-client INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [vrn/CAR_VRN] [vin/CAR_VIN] [make/CAR_MAKE] [model/CAR_MODEL] [i/ISSUE]...`<br>e.g., `edit-client 1 p/91234567 e/johndoe@example.com`<br>e.g., `edit-client 2 n/Betsy Crower i/`
**Find Clients**   | `find KEYWORD [MORE_KEYWORDS]`<br>e.g., `find John`<br>e.g., `find SJH 9514 P`
**Delete Client**  | `delete-client INDEX`<br>e.g., `delete-client 3`
**Clear**          | `clear`
**Exit**           | `exit`
**Help**           | `help`

Back to Features [section](#features).
