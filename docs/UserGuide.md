---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# MindMap User Guide

MindMap is a **desktop app that helps you manage your contacts efficiently, combining the speed of typing commands with the ease of a visual interface.**

It’s designed for professionals like psychologists who work with many contacts and prefer a fast, straightforward tool.

With MindMap, you can accomplish tasks quickly by typing simple commands, and you still get a clear, user-friendly screen to view and organize your contacts.

If you’re comfortable typing, MindMap can help you get contact management tasks done much faster than traditional apps that rely heavily on point-and-click with a mouse.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. **Check Your Java Version**  
   Make sure Java **version 17** or above is installed on your computer. <br> <box type="info" seamless>Java is a software platform that lets you run certain types of applications, including `.jar` files. [Learn more about Java here](https://www.java.com/en/download/faq/whatis_java.xml). <br> To check your current Java version, [learn more here](https://www.java.com/en/download/help/version_manual.html). <br>[Download Java here](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) if needed. </box>


2. **Download MindMap**  
   Download the latest `.jar` file for MindMap from [this link](https://github.com/AY2425S1-CS2103T-W13-3/tp/releases). <br> <box type="info" seamless> A `.jar` file is a single file that contains everything needed to run a program. Think of it like a zipped folder that, when opened, lets you start using the application right away. <br> <br> To run it, you just need Java installed on your computer. </box>


3. **Choose a Folder**  
   Copy the `.jar` file to a folder you’d like to use as the home for your MindMap contacts.


4. **Open a Terminal**
   <br> <box type="info" seamless> We’ll use the Terminal (or PowerShell on Windows) to enter a few simple commands to launch the MindMap app. </box>
    - **For Windows**:
        - Open the Start menu, type “Windows PowerShell”, and click on it to open.

    - **For Mac**:
        - Press `Command + Space` to open the search bar.
        - Type "Terminal" and select it from the results.

5. **Get the Folder Path**
    - For **Windows**: Hold `Shift` and `Right-click` the folder containing the `.jar` file and select **Copy as Path**.
    - For **Mac**: [Follow these steps](https://www.google.com/url?sa=t&source=web&rct=j&opi=89978449&url=https://macpaw.com/how-to/copy-file-path-mac%23:~:text%3DControl%252Dclick%2520or%2520right%252Dclick%2520on%2520the%2520file%2520in%2520Finder,path%2520wherever%2520you%2520need%2520it.&ved=2ahUKEwiUn6S_jseJAxWVxzgGHTVQJQsQFnoECBQQAw&usg=AOvVaw22hRilijfEWC_nmiYfJzmQ) to copy the folder path.

6. **Navigate to the Folder in Terminal**  
   In the terminal, type the following line and press Enter:
   ```bash
   cd [paste the folder path from step 5 here]
   ```
   If successful, this command will take you into the folder where your .jar file is located.
   
7. **Start MindMap**  
   Now, type the following line in the terminal to launch the app:
   ```bash
   java -jar mindmap.jar
   ```
   This will open the MindMap app. You’re now ready to use it! If you see an error, double-check that Java is installed using **Step 1**.


8. **Get Started with the App**  
   In a few seconds, you'll see the MindMap interface open with some sample contacts to explore.
> **NOTE**: The app contains some sample data.<br>

   
   ![Ui](images/Ui.png)

9. **Try Out Some Commands**  
   Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `list` : Lists all contacts.

    * `add n/John Doe i/S1234567D p/98765432 e/johnd@example.com a/John street, block 123, #01-01 s/LOW` : Adds a contact named `John Doe` to the Address Book.

    * `delete 3` : Deletes the 3rd contact shown in the current list.

    * `clear` : Deletes all contacts.

    * `exit` : Exits the app. 

10. **Learn More**  
    Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [s/STATUS]` can be used as `n/John Doe s/LOW` or as `n/John Doe`.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME i/IDENTITY_NUMBER p/PHONE_NUMBER e/EMAIL a/ADDRESS s/STATUS​`


Examples:
* `add n/John Doe i/S7783844I p/98765432 e/johnd@example.com a/John street, block 123, #01-01 s/LOW`
* `add n/Betsy Crowe i/S2202473F e/betsycrowe@example.com a/Newgate Prison p/1234567 s/HIGH`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [s/STATUS]​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower` Edits the name of the 2nd person to be `Betsy Crower`.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords. 

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name or full NRIC is searched.
* For Names:
* Only full words will be matched e.g. `Han` will not match `Hans`
* For NRIC:
* Only the Full NRIC will be matched .e.g `S123` will not match `S1234567J`.
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX` or `delete [i/IDENTITY_NUMBER]`

* Deletes the person at the specified `INDEX` or with the specified `IDENTITY_NUMBER`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* **After using `delete`, type `confirm` to complete the deletion**. You can also use `cancel` if you change your mind.

Examples:
* `delete i/SS2202473F` deletes the person with NRIC SS2202473F in the address book.
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book **after confirming the action**.

Format: `clear`

* **After using `clear`, type `confirm` to proceed with the clearing of all entries**. You can also use `cancel` if you change your mind.

### Confirming a command : `confirm`

Confirms the command that is currently being executed. This command is used to confirm the deletion of a person and the
clearing of the address book.

Examples:
* `delete i/S1234567D` followed by `confirm` deletes the person with NRIC S1234567D in the address book.
* `clear` followed by `confirm` clears all entries in the address book.

### Canceling a command : `cancel`

Cancels the command that is currently being executed. This command is used to cancel the deletion of a person and the
clearing of the address book.

Examples:
* `delete i/S1234567D` followed by `cancel` cancels the deletion of the person with NRIC S1234567D in the address book.
* `clear` followed by `cancel` cancels the clearing of the address book.

### Listing Session logs: `logs`

Lists all logs of a specific patient.

- **Format**: `logs i/[IDENTITY_NUMBER]`

Example:
* `logs i/S3054081F` <br>
  ![result for 'logs i/S3054081F'](images/logsAlexResult.png)

### Adding a log: `addlog`
Creates a new log entry for a specific patient. This command is typically used to keep track of sessions or important notes related to each session.
**Format**: `addlog i/IDENTITY_NUMBER d/DATE l/LOG_ENTRY`
* **IDENTITY_NUMBER** refers to the unique identifier (e.g., NRIC or FIN) of the patient.
* **DATE** should be in the format `DD-MMM-YYYY`.
* **LOG_ENTRY** is a description of the session with the patient.
  Examples:
* `addlog i/S1234567D d/20 May 2024 s/This should be replaced with the details of the session` adds a log for the contact with NRIC `S1234567D` on the date `20 May 2024`.
---
### Adding a log entry: `addentry`
Allows users to use a separate window to add new log entry for a person. When prompted with a new window, users can input the details of the log entry.
**Format**: `addentry i/IDENTITY_NUMBER d/DATE`
* **IDENTITY_NUMBER** refers to the unique identifier (e.g., NRIC or FIN) of the patient.
* **DATE** should be in the format `DD-MMM-YYYY`.
  Examples:
* `addentry i/S1234567D d/20 May 2024` opens a new window to add a log entry for the contact with NRIC `S1234567D` on the date `20 May 2024`.
* Example: ![img.png](img.png)


### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

**Q**: Can the new **M** FIN series introduced in Singapore From 1 January 2022 onwards be used?<br>
**A**:Unfortunately, no. Our application only supports the format of the **F** and **G** FIN series, and for the **S** and **T** series for the NRIC. Also note that while most valid NRIC/FIN are able to be recognised by our application, some would inevitably fail as the Singapore Government does not disclose the true method to verify NRIC/FIN.



--------------------------------------------------------------------------------------------------------------------

## Known Issues

1. **Application Opening Off-Screen on Single-Monitor Setup**  
   When using multiple screens, if the application is moved to a secondary screen and the setup later reverts to a single screen, the application may open off-screen.  
   > **Workaround**: Delete the `preferences.json` file created by the application, then restart the application.

2. **Help Window Remains Minimized**  
   If the Help Window is minimized and the `help` command is executed again (or the `Help` menu or `F1` keyboard shortcut is used), the existing Help Window will remain minimized without reopening or restoring. <br>
   > **Workaround**: Manually restore the Help Window from the minimized state.

3. **Unable to Navigate List Items with Tab Key**  
   When using the Tab key to navigate, individual items in lists (like the person list) cannot be accessed.  
   > **Workaround**: Currently, there is no workaround for this.








--------------------------------------------------------------------------------------------------------------------

## Command summary
| **Action**    | **Description**                       | **Format**                                                                | **Example**                                                                          |
|---------------|---------------------------------------|---------------------------------------------------------------------------|--------------------------------------------------------------------------------------|
| **Add**       | Adds a person to the address book     | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS s/STATUS…​`                  | `add n/James Ho p/999999999 e/jamesho@example.com a/123, Clementi Rd, 1234665 s/LOW` |
| **Clear**     | Clears all entries                    | `clear`                                                                   |                                                                                      |
| **Delete**    | Deletes a person                      | `delete INDEX`                                                            | `delete 3`                                                                           |
| **Edit**      | Edits an existing person              | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [s/STATUS]…​` | `edit 2 n/James Lee e/jameslee@example.com s/LOW`                                    |
| **Find**      | Finds persons by keywords             | `find KEYWORD [MORE_KEYWORDS]`                                            | `find James Jake`                                                                    |
| **Confirm**   | Confirms a deletion or clear action   | `confirm`                                                                 |                                                                                      |
| **Cancel**    | Cancels a delete or clear action      | `cancel`                                                                  |                                                                                      |
| **List**      | Lists all persons in the address book | `list`                                                                    |                                                                                      |
| **List Logs** | Lists logs for a specific person      | `logs i/NRIC`                                                             | `logs i/S8613282F`                                                                   |
| **Add Log**   | Adds a log for a person               | `addlog i/NRIC d/DATE l/LOG_ENTRY`                                        | `addlog i/S8613282F d/01 Nov 2024 l/Checked in at clinic`                            |
| **Add Entry** | Adds a log entry via a second window  | `addentry i/NRIC d/DATE`                                                  | `addentry i/S0000001i d/01 Nov 2024`                                                 |
| **Help**      | Displays help message                 | `help`                                                                    |                                                                                      |


 #### Additional Notes

* **Parameters**:
    - `n/NAME` - Full name of the person.
    - `p/PHONE_NUMBER` - Person's phone number.
    - `e/EMAIL` - Email address.
    - `a/ADDRESS` - Address details.
    - `s/STATUS` - Statuses of the patients which can only be HIGH, LOW, MEDIUM, DISCHARGED AND NEW.

* **Commands**:
    - **Edit** and **Delete** require the `INDEX` number displayed in the list of persons.
    - **Clear** and **Delete** actions require `confirm` to complete or `cancel` to abort.

* **Other Commands**:
    - **List** shows all persons in the address book.
    - **List Logs** shows all logs for a specific person based on their NRIC.
    - **Help** shows a guide for using all commands.

---

