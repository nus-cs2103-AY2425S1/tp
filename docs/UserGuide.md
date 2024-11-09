---
layout: page
title: User Guide
---

ClinicConnectSystem is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your ClinicConnectSystem.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar clinicconnectsystem.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `home` : Lists all patients.

   * `add n|John Doe i|T0123456A p|98765432 s|M d|1990-12-29` : Adds a patient named `John Doe` to the ClinicConnect system.

   * `delete T0123456A` : Deletes the patient with the NRIC "T0123456A" shown in the current list.

   * `clear` : Deletes all patients.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## ClinicConnect System Features

These features are designed to help you manage patient info seamlessly. Appointment info, patient details are centralized this platform.

### Legend
These boxes might offer some additional information of different types:

<box type="info" seamless>

**Important:**
Highlights important information that you should know.

</box>

<box type="tip" seamless>

**Tip:**
Provides you with tips to use our system more effectively.

</box>

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n|NAME`, `NAME` is a parameter which can be used as `add n|John Doe`.

* Items in square brackets are optional.<br>
  e.g `n|NAME [al|ALLERGY` can be used as `n|John Doe al|friend` or as `n|John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[al|ALLERGY]…​` can be used as ` ` (i.e. 0 times), `al|Nuts`, `al|Wheat al|Penicillin` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n|NAME p|PHONE_NUMBER`, `p|PHONE_NUMBER n|NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `home`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help [COMMAND_NAME]`
* Displays a small window showing more information
* Command name is optional as typing help alone will display the commands and their usages
* Typing command name will display the usages, parameter information and examples

Examples:
* `help`
* `help addf`

### Adding a patient: `add`

Adds a new patient record into the system.

Format: `add n|NAME i|NRIC s|SEX d|DATE_OF_BIRTH p|PHONE_NUMBER`


<div markdown="block" class="alert alert-info">
**:information_source: Important:**<br>
Each person should have a unique NRIC.
ClinicConnect does not allow two patients with the same NRIC to exist in the system.
</div>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Use this command to quickly add a patient with only the required information.
</div>

Example: `add n|Abraham Tan i|S9758366N s|M d|1997-10-27 p|87596666`
<br>
Adds a patient Abraham Tan with his NRIC, Sex, Date-of-Birth and Phone No.

For more information on each individual parameter click here

### Adding a patient with additional information: `addf`

Adds a new patient record into the system with additional information.

Format: `add n|NAME i|NRIC s|SEX d|DATE_OF_BIRTH p|PHONE_NO [e|EMAIL] [a|ADDRESS] [b|BLOOD_TYPE]
[nokn|NEXT_OF_KIN_NAME] [nokp|NEXT_OF_KIN_PHONE] [al|ALLERGY]…​ [rl|RISK_LEVEL] [ec|EXISTING_CONDITIONS] [no|NOTES]`

<div markdown="block" class="alert alert-info">
**:information_source: Important:**<br>
Each person should have a unique NRIC.
ClinicConnect does not allow two patients with the same NRIC to exist in the system.
</div>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Use this command if you want to add a patient with additional information in addition to the required fields
(NAME, NRIC, SEX, DATE_OF_BIRTH, PHONE_NO)
</div>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Multiple allergies can be added using amultiple "al|" prefix
</div>

Examples:
<ol>
<li>

`addf n|Abraham Tan i|S9758366N s|M d|1997-10-27 p|87596666
e|abramhamtan@gmail.com a|Blk 123, NUS Road, S123123 b|A+ nokn|Licoln Tan nokp|91234567
al|nuts al|shellfish rl|HIGH ec|Diabetes no|Patient needs extra care`
<ul>
<li>
Adds a patient Abraham Tan with his NRIC, Sex, Date-of-Birth, Phone, Email, Address, Blood Type,
Next-of-Kin Name, Next-of-Kin Phone, Risk Level, Existing Conditions, Notes and his two allergies.
</li>
</ul>
</li>
<li>

`addf n|Lincoln Park i|S9751269M s|M d|1980-04-01 p|87296619
  e|linkinpark@gmail.com a|Blk 516, Clementi Road, S661836 b|AB- al|wheat`
</li>
<ul>
<li>
Adds a patient Lincoln Park with his NRIC, Sex, Date-of-Birth, Phone, Email, Address, Blood Type,
and his allergy.
</li>
</ul>
</ol>
For more information on each individual parameter click here

### Editing a patient: `edit`

Edit the information of an existing patient in the system.

Format: `edit NRIC [n|NAME] [i|NRIC] [s|SEX] [d|DATE_OF_BIRTH] [p|PHONE_NO] [e|EMAIL] [a|ADDRESS] [b|BLOOD_TYPE]
[nokn|NEXT_OF_KIN_NAME] [nokp|NEXT_OF_KIN_PHONE] [al|ALLERGY]…​ [rmal|ALLERGY_TO_BE_REMOVED]…​ [rl|RISK_LEVEL]
[ec|EXISTING_CONDITIONS] [no|NOTES]`


<div markdown="block" class="alert alert-info">
**:information_source: Important:**<br>
<ul>
<li>
NRIC provided must be a valid NRIC currently in the system
</li>
<li>
Input must contain at least one parameter to be edited
</li>
<li>
Not all parameters are compulsory
</li>
</ul>
</div>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Multiple allergies can be removed using multiple "rmal" prefixes. Ensure allergies to be removed
are current allergies of the patient
</div>


Examples:
<ol>
<li>

`edit S9758366N n|Abraham Lee d|1997-10-28`
<ul>
<li>
Edits the name and date-of-birth of the patient with NRIC S9758366N.
</li>
</ul>
</li>
<li>

`edit S9758366N p|91234123 a|Blk 918A, Pasir Ris Drive, #13-102, Singapore 911918`
</li>
<ul>
<li>
Edits the phone and address of the patient with NRIC S9758366N.
</li>
</ul>
</ol>
For more information on each individual parameter click here

### Listing all patients : `home`

Shows a list of all patients in the ClinicConnect System.

Format: `home`

### Booking an appointment: `bookappt`

Book an appointment for an existing patient in the system for a health service provided by the clinic.

Format: `bookappt NRIC dt|APPOINTMENT_DATE_TIME h|HEALTH_SERVICE`


<div markdown="block" class="alert alert-info">
**:information_source: Important:**<br>
<ul>
<li>
NRIC provided must be a valid NRIC in the system.
</li>
<li>
All parameters are compulsory
</li></ul>
</div>


Example: `bookappt S9758366N dt|2024-12-29 13:00 h|Vaccination`
<br>
Books a Vaccination appointment for the given patient by NRIC at the specified time.

For more information on each individual parameter click here

### Delete Appointment : `deleteappt`

Identifies the specific patient using NRIC and deletes the appointment specified.

Format: `deleteappt NRIC dt|APPOINTMENT_DATE_TIME`

<div markdown="block" class="alert alert-info">
**:information_source: Important:**<br>
<ul>
<li>
NRIC provided must be a valid NRIC in the system.
</li>
<li>
All parameters are compulsory
</li></ul>
</div>

Example:
* `deleteappt T01234567A dt|2024-11-05 16:00`

### Filter appointments : `filter`

Filters existing patient records based on the specified parameters.

Format: `filter [sd|START DATE] ed|END DATE [h|HEALTH SERVICE]`

Start date and health service parameters are optional.
End date parameter is compulsory.

When all parameters are specified, it returns all appointments from start-date to end-date which matches the specified health service.
When start date and end date are specified, it returns all appointments from start date to end date.
When end date and health service is specified, it returns all appointments from today's date to end date which matches the specified health service.
When end date is specified, it returns all appointments from today's date to end-date.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
To retrieve appointments on a single date e.g. 2024/10/20, the user can input the same start and end date

`filter sd|2024-10-20 ed|2024-10-10`
</div>


Examples:
*  `filter sd|2012-10-01 ed|2012-11-01 h|blood test` filters the blood test appointments of patients from Oct 01 2012 to Nov 01 2012.
*  `filter ed|2024-12-12 h|vaccination` filters the vaccination appointments from today's date to Dec 12 2024.
*  `filter sd|2012-10-01 ed|2012-11-01` filters all appointments from Oct 01 2012 to Nov 01 2012.

### View patients full profile: `view`

Identifies the specific patient using NRIC and shows the full profile of the patient.

Format: `view NRIC`

<div markdown="block" class="alert alert-info">
**:information_source: Important:**<br>
NRIC provided must be a valid NRIC currently in the system.
</div>

Example:
* `view T0123456A`

### Locating patients by name: `find`

Finds patients whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Patients matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a patient : `delete`

Deletes the patient with the specified NRIC from the address book.

Format: `delete NRIC`

* Deletes the patient at the specified `NRIC`.
* The NRIC refers to the nric of the patient shown in the displayed patient list.
* The NRIC **must be a valid NRIC number present in the system**

### Clearing all entries : `clear`

Clears all patient entries and data in the current system.

Format: `clear`

### Exiting the program : `exit`

Exits the system and closes the window.

Format: `exit`

### Saving the data

ClinicConnectSystem data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

ClinicConnectSystem data are saved automatically as a JSON file `[JAR file location]/data/clinicconnectsystem.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, ClinicConnectSystem will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the ClinicConnectSystem to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous ClinicConnectSystem home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

 Action     | Format, Examples                                                                                                                                                                                                                                                                                                                                                                                                  
------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 **Add**    | `add n\|NAME i\|NRIC s\|SEX d\|DATE_OF_BIRTH p\|PHONE_NUMBER` <br> e.g., `add n\|Abraham Tan i\|S9758366N s\|M d\|1997-10-27 p\|87596666`                                                                                                                                                                                                                                                                         
 **Clear**  | `clear`                                                                                                                                                                                                                                                                                                                                                                                                           
 **Delete** | `delete S0123456Z`<br> e.g., `delete S0123456Z`                                                                                                                                                                                                                                                                                                                                                                   
 **Edit**   | `edit NRIC \[n\|NAME] \[i\|NRIC] \[s\|SEX] \[d\|DATE_OF_BIRTH] \[p\|PHONE_NO] \[e\|EMAIL] \[a\|ADDRESS] \[b\|BLOOD_TYPE] \[nokn\|NEXT_OF_KIN_NAME] \[nokp\|NEXT_OF_KIN_PHONE] \[al\|ALLERGY]…​ \[rmal\|ALLERGY_TO_BE_REMOVED]…​ \[rl\|RISK_LEVEL] \[ec\|EXISTING_CONDITIONS] \[no\|NOTES]`<br> e.g., `edit S9758366N p\|91234123 a\|Blk 918A, Pasir Ris Drive, #13-102, Singapore 911918` 
 **Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`                                                                                                                                                                                                                                                                                                                                                        
 **Home**   | `home`                                                                                                                                                                                                                                                                                                                                                                                                            
 **Help**   | `help`                                                                                                                                                                                                                                                                                                                                                                                                            
