---

layout: default.md

title: "User Guide"

pageNav: 3

---


# DLTbook User Guide


<box type="info" seamless>
DLTbook is a desktop application that helps you manage your contacts and their blockchain (DLT) addresses. It combines an easy-to-use interface with powerful command-line features, making it perfect for both beginners and advanced users.
</box>


<panel header="💡 **New Users Start Here**" type="primary" expanded>

### Before You Begin
If terms like 'command line', 'terminal', or 'Java' sound unfamiliar to you, don't worry! Visit our:
- 📘 [Beginner's Guide](#beginner-s-guide) for basic concepts
- 🔧 [Setup Guide](#setup-guide) for installation help
- ❓ [FAQ Section](#faq) for common questions
- 🛠️ [Troubleshooting Guide](#common-errors) for known issues
</panel>


## Quick Start

### System Requirements

- **Java 17 or newer installed**
<box type="info" seamless>

Not sure if you have Java? Check out [Setup Guide](#setup-guide)
</box>

### Quick Installation
1. **Download**: Download the latest release of [DLTbook.jar](https://github.com/AY2425S1-CS2103T-T08-1/tp/releases)

2. **Install**: Create a folder and move DLTbook.jar there

3. **Start**: Open Terminal and run `java -jar DLTbook.jar` 
   
4. **Try it Out**: If you are an experienced user, skip to [Try It Out!](#try-it-out) for a guide of the basic commands

## Beginner's Guide

<panel header="👋 **Welcome to DLTbook Beginner's Guide**" type="primary" expanded> 

If you're new to DLTbook or the concept of Distributed Ledger Technology (DLT), this guide will walk you through the basics. Here, we'll cover key concepts, important terminology, and guide you through the first steps in using DLTbook.
</panel>

<br>

1. **Understanding the Basics**

DLTbook is a powerful tool that helps you manage contacts and blockchain addresses. 
If you're not familiar with these terms, don't worry—this section breaks down the essential concepts.
<box type="info" seamless>

**What is Distributed Ledger Technology (DLT)?** 

DLT refers to a digital system for recording transactions of assets in multiple places at once. 
Unlike traditional databases, DLTs do not have a central administrator, which means blockchain records are decentralized.
</box> 

<box type="tip" seamless> 

**Why is DLTBook useful in managing contacts?** 

DLTbook simplifies the task of managing contacts linked with blockchain addresses, 
ensuring secure and efficient interaction with distributed networks.
</box>

2. **Key Terms You Should Know**

Before diving in, let's clarify some essential terms:

<box type="info" seamless>

- **Blockchain**: A decentralized digital ledger that records transactions across multiple computers.
Acts like a currency, but it's digital and secure.

- **Network**: A network on the blockchain (e.g., Bitcoin, Ethereum, Solana).

- **Wallet**: A digital tool used to store, send, and receive cryptocurrencies.

- **Wallet Name**: A label that you tag to the wallet.

- **Public Address**: A unique identifier tagged to the wallet to interact with the blockchain (similar to an account number).

- **Command Line**: A text-based interface used to run commands on your computer.

- **Java**: The programming language required to run DLTbook.
</box>

3. **Getting Started: Your First Steps**

Here’s how you can start using DLTbook:

1. Follow our [setup guide](#setup-guide) to install Java and DLTbook on your computer.

2. Open the application and [try](#try-it-out) out some basic commands to manage contacts and blockchain addresses.

3. If you encounter any issues, refer to our [troubleshooting guide](#known-issues) or [FAQ section](#faq) for help.

4. Once you're comfortable with the basics, explore the full range of [features](#features) available in DLTbook.

### Setup Guide

1. **Download/Check Java Installation**

    - Download Java from [Oracle's website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

    - Open Terminal (Mac/Linux) or Command Prompt (Windows) by searching in your system and type `java -version`

    - If you see a version number, you're all set! If not, download and install Java

<box type="info" seamless>

- **Note**: Java 17 or newer is required to run DLTbook

- **Note**: If you have multiple versions of Java installed, you may need to specify the path to the correct version when running DLTbook

- **Note**: If you're using a Mac, you may need to install the Java Development Kit (JDK) instead of the Java Runtime Environment (JRE)
</box>

2. **Download DLTbook**

    - Get the latest version from our [download page](https://github.com/AY2425S1-CS2103T-T08-1/tp/releases)

    - Download the file named `DLTbook.jar`

3. **Install the Application**

    - Open File Explorer (Windows) or Finder (Mac) and navigate to the Downloads folder

    - Create a new folder where you'd like to keep DLTbook

    - Move the downloaded `DLTbook.jar` file into this folder

4. **Start DLTbook**

    - Copy the path to the folder where you saved `DLTbook.jar`

    - Open Terminal (Mac/Linux) or Command Prompt (Windows) 
   
    - Navigate to the folder where `DLTbook.jar` is saved by using the `cd` command like this:
   ```
    cd your/path/to/DLTbook
   ```
    - Run the Command: `java -jar DLTbook.jar`

<br>
     
After a few seconds, you'll see the DLTbook window:

 ![Ui](images/Ui.png)

### Try It Out!

DLTbook comes with sample data to help you get started. Here are some basic commands to try:

1. **View all contacts**

   ```
   list
   ```
   
2. **Add a new contact**

   ```
   add n/Travis p/91234567 e/travis@Linkifyai.com a/1 Travis Avenue, Singapore
   ```


3. **Add a blockchain address**

   ```
   addpa c/ETH n/Travis w/wallet1 pa/0x28f91d6e72eaf4372892e6c6e45dc41b574163e9fcdf94f4997958b46d772fa2
   ```


4. **Retrieves the public address of a contact**

   ```
   retrievepa 1 pa/BTC l/wallet1
   ```


5. **Remove a contact**

   ```
   delete 3
    ```
   
6. **Exit the application**

    ```
    exit
    ```
   * `addpa c/ETH n/Travis w/wallet1 pa/0x28f91d6e72eaf4372892e6c6e45dc41b574163e9fcdf94f4997958b46d772fa2` : Adds a public address to a contact
    
   * `editpa 1 c/BTC l/Daily wallet pa/14qViLJfdGaP4EeHnDyJbEGQysnCpwk3gd`: Edits an existing public address of a contact

   * `retrievepa 1 c/BTC l/wallet1` : Retrieves the public address of a contact

### Need Help?


- Type `help` in the application to see all available commands

- Visit our [Detailed Features Guide](#features) below

- Check our [Troubleshooting Guide](link-to-troubleshooting) if you run into any issues


<box type="tip" seamless>


**Tip:** 💡 Type commands in any case - they're not case-sensitive

</box>


--------------------------------------------------------------------------------------------------------------------


Ready to learn more? Check out the [Features](#features) section below for a complete guide to all DLTbook commands and capabilities.

## Features


<box type="info" seamless>


**Notes about the command format:**<br>


* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>

  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.


* Items in square brackets are optional.<br>

  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.


* Items with `…`​ after them can be used multiple times including zero times.<br>

  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.


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


Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`


<box type="tip" seamless>


**Tip:** A person can have any number of tags (including 0)

</box>


Examples:

* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`

* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`


### Listing all persons : `list`


Shows a list of all persons in the address book.


Format: `list`


### Editing a person : `edit`


Edits an existing person in the address book.


Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`


* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​

* At least one of the optional fields must be provided.

* Existing values will be updated to the input values.

* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.

* You can remove all the person’s tags by typing `t/` without

    specifying any tags after it.


Examples:

*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.

*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.


### Locating persons by name: `find`


Finds persons whose names contain any of the given keywords.


Format: `find KEYWORD [MORE_KEYWORDS]`


* The search is case-insensitive. e.g `hans` will match `Hans`

* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`

* Only the name is searched.

* Only full words will be matched e.g. `Han` will not match `Hans`

* Persons matching at least one keyword will be returned (i.e. `OR` search).

  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`


Examples:

* `find John` returns `john` and `John Doe`

* `find alex david` returns `Alex Yeoh`, `David Li`<br>

  ![result for 'find alex david'](images/findAlexDavidResult.png)


### Deleting a person : `delete`


Deletes the specified person from the address book.


Format: `delete INDEX`


* Deletes the person at the specified `INDEX`.

* The index refers to the index number shown in the displayed person list.

* The index **must be a positive integer** 1, 2, 3, …​


Examples:

* `list` followed by `delete 2` deletes the 2nd person in the address book.

* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.


### Clearing all entries : `clear`


Clears all entries from the address book.


Format: `clear`


### Exiting the program : `exit`


Exits the program.


Format: `exit`



### Adding a public address to a contact : `addpa`


Adds a public address to a contact.


Format: `addpa c/NETWORK n/NAME w/WALLET_NAME pa/PUBLIC_ADDRESS`


* Adds a public address to a contact based on the NAME

* The contact is identified by the `NAME` provided.

* The `NETWORK` parameter specifies the ticker name for each network and should be in all CAPS (e.g., `BTC`, `ETH`, `SOL`, `SUI`, etc.).

* the `NAME` parameter specifies the name of the contact to which the public address belongs.

* The `PUBLIC_ADDRESS` parameter specifies the public address to be added, this fields is not cap sensitive.

* The `WALLET_NAME` parameter specifies the wallet name to which the public address belongs.


Examples:

* `addPublicAddress c/ETH n/Travis w/wallet1 pa/0x28f91d6e72eaf4372892e6c6e45dc41b574163e9fcdf94f4997958b46d772fa2` adds a public address to a contact named `Travis` with the wallet name `wallet1` and the public address `0x28f91d6e72eaf4372892e6c6e45dc41b574163e9fcdf94f4997958b46d772fa2`.

### Editing a public address of a contact : `editpa`

Edits an existing public address of a contact.

Format: `editpa INDEX c/NETWORK l/WALLET_NAME pa/NEW_ADDRESS`

<box type="tip" seamless>

WALLET_NAME is case-sensitive.
</box>

* Edits the public address of the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. 
The index **must be a positive integer** 1, 2, 3, …​

* The `NETWORK` parameter specifies the ticker name for each network and should be in all CAPS (e.g., `BTC`, `ETH`, `SOL`, `SUI`, etc.).
  Allowed values: `BTC|ETH|SOL`.

* The `WALLET_NAME` parameter specifies the wallet name to which the public address belongs.

* The `NEW_ADDRESS` parameter specifies the new public address to be added.

#### Examples

* `editpa 3 c/BTC l/Daily wallet pa/14qViLJfdGaP4EeHnDyJbEGQysnCpwk3gd`<br />
  Changes the third contact's BTC public address labelled `Daily wallet` to `14qViLJfdGaP4EeHnDyJbEGQysnCpwk3gd`.<br />
  TODO: Example output

* `editpa 3 c/BTC l/daily wallet pa/14qViLJfdGaP4EeHnDyJbEGQysnCpwk3gd`<br />
  **DOES NOT** change the third contact's BTC public address labelled `Daily wallet` as `WALLET_NAME` is case-sensitive.<br />

### Retrieving public addresses of a contact : `retrievepa`

Retrieves the public addresses of a contact.

Format: `retrievepa INDEX c/NETWORK [l/WALLET_NAME]`

* Retrieves the public address of the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list.
  The index **must be a positive integer** 1, 2, 3, …​

* The `NETWORK` parameter specifies the ticker name for each network and should be in all CAPS (e.g., `BTC`, `ETH`, `SOL`, `SUI`, etc.).
  Allowed values: `BTC|ETH|SOL`.

* The `WALLET_NAME` is optional and specifies the wallet name to which the public address belongs.


#### Examples

* `retrievepa 3 c/BTC`<br />
  Retrieves all the BTC public addresses of the third contact.<br />
  TODO: Example output
* `retrievepa 3 c/BTC l/Daily wal`<br />
  Retrieves all the BTC public addresses of the third contact which label contains "daily wal" (case-insensitive).

  
### Deleting a public address of a contact : `deletepa`


Deletes the public address of a contact.


Format: `deletepa INDEX c/NETWORK [w/WALLET_NAME]`

* Deletes the public address of a contact based on the index

* If Wallet Name is not provided, all public addresses in the network of the contact will be deleted.

Examples:

* `deletePublicAddress 1 c/BTC w/wallet1` deletes the public address of the 1st person in the BTC Network with the wallet name `wallet1`.

* `deletePublicAddress 3 c/BTC` deletes all the public addresses of the 3rd person in the BTC network.


### Searching for a public address : `publicAddressSearch`

Searches for a public address.

Format: `publicAddressSearch pa/PUBLIC_ADDRESS`

<box type="tip" seamless>

The fields are not cap sensitive.
</box>

* Searches for a public address based on the `PUBLIC_ADDRESS` provided.

* The `PUBLIC_ADDRESS` parameter specifies the public address to be searched.

Examples:

* `publicAddressSearch pa/0x28f91d6e72eaf4372892e6c6e45dc41b574163e9fcdf94f4997958b46d772fa2` searches for a public address `0x28f91d6e72eaf4372892e6c6e45dc41b574163e9fcdf94f4997958b46d772fa2` and displays the contact and wallet to which it belongs.


--------------------------------------------------------------------------------------------------------------------


### Saving the data

DLTbook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

DLTbook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**

If your changes to the data file makes its format invalid, DLTbook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>

Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

--------------------------------------------------------------------------------------------------------------------


## FAQ

**Q**: How do I transfer my data to another Computer?<br>

**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.


--------------------------------------------------------------------------------------------------------------------

## Common Errors

## Known issues


1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.


--------------------------------------------------------------------------------------------------------------------


## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**   | `list`
**Help**   | `help`
**Exit**   | `exit`
**Add Public Address** | `addpa c/NETWORK n/NAME w/WALLET_NAME pa/PUBLIC_ADDRESS`<br> e.g., `addPublicAddress c/ETH n/Travis w/wallet1 pa/0x28f91d6e72eaf4372892e6c6e45dc41b574163e9fcdf94f4997958b46d772fa2`
**Edit Public Address** | `editpa INDEX c/NETWORK l/WALLET_NAME pa/NEW_ADDRESS`<br> e.g., `editpa 3 c/BTC l/Daily wallet pa/14qViLJfdGaP4EeHnDyJbEGQysnCpwk3gd`
**Retrieve Public Address** | `retrievepa INDEX c/NETWORK [l/WALLET_NAME]`<br> e.g., `retrievepa 3 c/BTC l/Daily wallet`
**Delete Public Address** | `deletepa c/NETWORK [w/WALLET_NAME]`<br> e.g., `deletePublicAddress 1 c/BTC w/wallet1`
**Public Address Search** | `searchpa pa/PUBLIC_ADDRESS`<br> e.g., `searchpa pa/0x28f91d6e72eaf4372892e6c6e45dc41b574163e9fcdf94f4997958b46d772fa2`
