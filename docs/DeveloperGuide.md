---
layout: default.md
title: "Developer Guide"
pageNav: 3
---

# VBook Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* [AB3](https://github.com/nus-cs2103-AY2425S1/tp) for being the base of our project.
* Libraries used: [JavaFX](https://openjfx.io/), [Jackson](https://github.com/FasterXML/jackson), [JUnit5](https://github.com/junit-team/junit5)
* [imPoster](https://github.com/AY2021S2-CS2103T-T12-4/tp), a CS2103T senior group where we adapted our `MainWindow.fxml` code from.
* [Stackoverflow post on Java password hashing](https://stackoverflow.com/a/2861125): We followed this post from Stackoverflow to guide us in the password hashing function in `PasswordManager.java`.

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280"></puml>

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `:remove -i 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

<br>
<br>

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

<br>
<br>

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute(":remove -i 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram-Logic.puml" alt="Interactions Inside the Logic Component for the `:remove -i 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to remove a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

<br>
<br>

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

<br>
<br>

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

<br>
<br>

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Undo/redo feature

#### Implementation

The undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `:remove -i 5` command to add the 5th person in the address book. The `:remove` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `:remove -i 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `:add -n David …​` to add a new person. The `:add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `:undo` command. The `:undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `:undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic"></puml>

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `:redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `:redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `:list`. Commands that do not modify the address book, such as `:list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `:add -n David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
    * Pros: Easy to implement.
    * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
    * Pros: Will use less memory (e.g. for `:remove`, just save the person being removed).
    * Cons: We must ensure that the implementation of each individual command are correct.

<br>
<br>

### Remove Feature

#### Implementation
The remove feature allows removal of a person from the address book. The user can remove a person by specifying the index of the person to remove. The user can also remove multiple persons by specifying multiple indexes of the persons to remove.

How the remove Feature works:
Format: `:remove -i INDEX1, INDEX2...`

The `DeleteCommand` class has a method `DeleteCommand#execute(Model model)` that calls the ModelManager.\
The `ModelManager` class has a method `ModelManager#deletePerson(Person target)` that calls the `AddressBook` class.\
The `AddressBook` class has a method `AddressBook#removePerson(Person key)` that removes a person from the UniquePersonList field `persons` in the `AddressBook` class.


The following class diagram shows the relationships between the classes involved in the delete feature:
<puml src="diagrams/DeleteClassDiagram.puml" />

The following sequence diagram shows how a remove operation goes through the `Logic` component:

<puml src="diagrams/DeleteMultipleSequenceDiagram-Logic.puml" alt="Interactions Inside the Logic Component for the `:remove -i 1, 2, 3` Command" />

Similarly, how a delete operation goes through the `Model` component is shown below:

<puml src="diagrams/DeleteSequenceDiagram-Model.puml" alt="DeleteSequenceDiagram-Model" />

The following activity diagram summarizes what happens when a user executes a new command:
<puml src="diagrams/DeleteActivityDiagram.puml" />

**Design considerations:**

**Aspect: How remove executes:**

* **Alternative 1 (current choice):** Removes the person by index.
    * Pros: Easy to implement.
    * Cons: Requires the user to know the index of the person to remove.

* **Alternative 2:** Removes the person by name.
    * Pros: More user-friendly as the user can specify the name.
    * Cons: Requires additional logic to handle duplicate names.

<br>
<br>

### Add Feature

#### Implementation

The add feature allows a person to be added to the address book. It accepts parameters name, phone, address, email,
remark and tag. The name parameter is compulsory, while the rest are optional. Multiple tags are accepted for one
person.

The add feature follows the remove feature in that `AddCommand` calls `ModelManager`, which calls the `AddressBook`
class, which adds a person to the `UniquePersonList` class. Therefore, the class and activity diagram will be omitted
for conciseness.

The following sequence diagram shows how an add operation goes through the `Logic` component:

<puml src="diagrams/AddSequenceDiagram-Logic.puml" alt="Interactions Inside the Logic Component for the `:add` Command" />

The parsing process is described in detail in this sequence diagram:

<puml src="diagrams/AddSequenceDiagram-Tokenise.puml" alt="Interactions Inside the Logic Component for parsing" />

The following sequence diagrams give examples of how a compulsory parameter, an optional parameter and a parameter that
accepts multiple values at once is parsed.

A compulsory parameter like `Name` is parsed as follows.

<puml src="diagrams/AddSequenceDiagram-ParseName.puml" alt="Interactions Inside the Logic Component when parsing a name string" />

An optional parameter like `Phone` is parsed as follows. `Email`, `Address` and `Remark` are also parsed similarly.

<puml src="diagrams/AddSequenceDiagram-ParsePhone.puml" alt="Interactions Inside the Logic Component when parsing a phone string" />

Finally, a parameter that accepts multiple values at once like `Tag` is parsed as follows.

<puml src="diagrams/AddSequenceDiagram-ParseTags.puml" alt="Interactions Inside the Logic Component when parsing a collection of tags" />


Similar to the remove feature, how an add operation goes through the `Model` component is shown below:
<puml src="diagrams/AddSequenceDiagram-Model.puml" alt="AddSequenceDiagram-Model" />

<br>
<br>
<br>

### Export Feature
#### Implementation

The `ExportCommand` class is responsible for exporting address book data to a user-specified location in `JSON` format. It provides flexibility in its usage by allowing a destination to be selected via a file chooser or by setting a predetermined destination file, which is particularly useful for testing purposes. The data to be exported is encrypted, and the `ExportCommand` handles decryption, export location selection, and file I/O operations. The following outlines its components and workflow.


The `ExportCommand` class facilitates this export functionality and manages file I/O operations in a structured, asynchronous workflow.

Constructor Variants:

- `ExportCommand()`: The default constructor for regular use, opening a file chooser dialog to select the export destination.
- `ExportCommand(File destinationFile, File sourceFile, String keyPath)`: An overloaded constructor that allows specifying a destination file and encryption key path directly, which is particularly useful for testing.

Attributes:

- `destinationFile`: The file chosen or set as the target for the export.
- `sourceFile`: A temporary file that holds the JSON data to be exported.
- `keyPath`: The path to the decryption key required for decrypting the address book data.

Given below is an example usage scenario and how the export process behaves at each step.

Step 1. The user initiates an export by executing `:export`. The `ExportCommand` will attempt to decrypt the data
before exporting it.

Step 2. The `execute(Model model)` method reads encrypted data from the `sourceFile`, decrypting it with
`EncryptionManager.decrypt()` using the provided `keyPath`. The decrypted data is written to a temporary file `vbook.json`.

Step 3. If `destinationFile` is not set, `ExportCommand` invokes `chooseExportLocation(Stage stage)`, which displays
a file chooser dialog for the user to select an export location. If the user cancels this dialog, the export process
is aborted with an error message.

Step 4. The `performExport(File sourceFile, File destinationFile)` method copies the decrypted data to the specified `destinationFile`, using `Files.copy()` with `StandardCopyOption.REPLACE_EXISTING` to overwrite any existing file. The temporary file is then deleted.

Note: The `performExport` method is asynchronous, leveraging `CompletableFuture` to manage successful completion or error handling, ensuring smooth performance without blocking the main application thread.

The following sequence diagram explains how the export operation works:

<puml src="diagrams/ExportSequenceDiagram.puml" alt="ExportSequenceDiagram"/>

**Design Considerations:**

**Aspect: Export Execution and Destination Selection**

**Alternative 1 (current choice)**: Use a file chooser dialog to allow the user to select the export location.

- Pros: User-friendly, provides flexibility in specifying the export location.
- Cons: Requires user interaction, which may be cumbersome for repeated exports.


**Alternative 2**: Set a default export location without user input.

- Pros: Streamlined and faster for frequent exports.
- Cons: Less flexible, as it may overwrite existing files without warning.

<br>
<br>

### Encryption Feature

#### Implementation

The encryption mechanism is managed by the `EncryptionManager` class. This component is responsible for securely encrypting and decrypting sensitive data using the AES (Advanced Encryption Standard) algorithm. The `EncryptionManager` performs encryption and decryption with a secret key, which is securely loaded and stored using Java's Key Store API. The implementation details are as follows:

#### Methods Overview

1. **`encrypt(String data, String keyPath)`**:
    - Encrypts plain text data using the AES algorithm.
    - Takes the path to the key store as an argument (defaulting to `vbook.jks` if not provided).
    - Returns a byte array containing the encrypted data.
      <br>
2. **`decrypt(byte[] data, String keyPath)`**:
    - Decrypts the given encrypted byte array back into plain text.
    - Also takes the path to the key store as an argument (defaulting to `vbook.jks` if not provided).
    - Returns the decrypted string.
      <br>
3. **`generateKey(String keyPath)`**:
    - Generates a new AES secret key and stores it in a local key store file.
    - If the key store already exists, it does not overwrite it but notifies that the alias already exists.
    - Saves the generated key under the alias `vbook-encryption`.
      <br>
4. **`getKey(String keyPath)`**:
    - Retrieves the AES secret key from the specified key store.
    - If the key store does not exist, it calls `generateKey()` to create one.
    - Returns the retrieved secret key.
      <br>
#### Usage in Application

- The `EncryptionManager` is used in the `ExportCommand` to decrypt data before exporting it and in `JsonAddressBookStorage` to encrypt data before writing it to a file.

#### Usage

```java
// Encryption
String jsonData = JsonUtil.toJsonString(new JsonSerializableAddressBook(addressBook));
        byte[] encryptedData = EncryptionManager.encrypt(jsonData, this.keyPath);

// Decryption
        jsonData = EncryptionManager.decrypt(encryptedData, this.keyPath);
```
#### Example Usage Scenario

Step 1. The user initially adds a new contact in the address book. The `EncryptionManager` uses the AES algorithm and the secret key to encrypt the information before saving it.

Step 2. The encrypted data is stored securely. When needed, the user can request to decrypt the information.

Step 3. The `EncryptionManager` decrypts the data using the same AES algorithm and the secret key, ensuring that the information is securely handled at all times.

<box type="info" seamless>

**Note:** If an error occurs during encryption or decryption (e.g., if the secret key is invalid or corrupted), the `EncryptionManager` will handle the error gracefully and return an appropriate error message.

</box>

The following sequence diagram shows how the encryption process works:

<puml src="diagrams/EncryptionSequenceDiagram.puml" alt="EncryptionSequenceDiagram" />

<box type="info" seamless>

**Note:** The sequence diagram simplifies the encryption and decryption processes to focus on the main interactions between components.

</box>

#### Design Considerations for Encryption Feature

##### Core Limitation

1. **Risk of Local KeyStore Exposure**:
    - If a hacker gains access to the JKS file containing the encryption keys, they could decrypt sensitive data. This represents a fundamental limitation of local storage, as the security of the keys relies on the local file system's security.

2. **Alternative Storage Locations**:
    - Storing the JKS file in the `JAVA_HOME/lib/security/cacerts` directory is an option, but this depends on the user’s configuration and permissions. Users might not have their `JAVA_HOME` path set correctly, which can lead to access issues.

3. **Security Through Obscurity**:
    - While relying on obscurity—such as using less common paths for the JKS file—can add a layer of security, it should not be the sole defense mechanism. Obscurity alone does not adequately protect against determined attacks.

##### Compromise Between Security and User Experience
- **User Experience Considerations**:
    - As a local application, VBook prioritizes convenience, which may lead users to prefer simpler access to their data over maximum security. Finding a balance between security and usability is crucial.
    - Given that VBook handles contact data, adequate security measures must be in place while ensuring users are not overwhelmed by complex key management.

<br>
<br>

### Password Management Feature

#### Implementation

The password management mechanism is handled by the `PasswordManager` class. This component is responsible for securely hashing and verifying user passwords using the **PBKDF2** (Password-Based Key Derivation Function 2) algorithm with **HMAC-SHA1**. The `PasswordManager` ensures that passwords are safely stored in a local text file, employing a salting strategy to enhance security. The implementation details are as follows:

#### Methods Overview

1. **`readPassword(String path)`**:
    - Reads the stored hashed password from the specified file (defaulting to `password.txt`).
    - Returns the hashed password as a string or `null` if the file does not exist.
      <br>
2. **`savePassword(String password, String path)`**:
    - Accepts a plaintext password, generates a salt, hashes the password using **PBKDF2**, and saves the resulting hash and salt to the specified file (default: `password.txt`).
    - Creates the file if it does not already exist.
      <br>
3. **`isPasswordCorrect(String inputPassword, String path)`**:
    - Compares the input plaintext password against the stored hashed password.
    - Reads the stored hash and salt, hashes the input password, and returns `true` if they match or `false` otherwise.
      <br>
4. **`hashPassword(String password, byte[] salt)`**:
    - Hashes the provided password using the specified salt with **PBKDF2** and returns a string containing both the salt and hash encoded in Base64.
      <br>
5. **`generateSalt()`**:
    - Generates a secure random salt using `SecureRandom` for use in password hashing.

#### Usage in Application

- The `PasswordManager` is invoked during application startup to check for an existing password file.
    - If the file is absent, the user is prompted to create a new password.
    - On subsequent starts, the user must input their original password for verification before proceeding.

#### Usage

```java
// Saving a new password on initial startup
if (PasswordManager.readPassword(null) == null) {
        String newPassword = scanner.nextLine();
        PasswordManager.savePassword(newPassword, null);
        }

// Verifying the password on subsequent starts
        String inputPassword = scanner.nextLine();
        if (PasswordManager.isPasswordCorrect(inputPassword, null)) {
        // Handle correct password
        } else {
        // Handle wrong password
        }
```

#### Example Usage Scenario

Step 1. On the initial startup of the application, the `PasswordManager` checks for the existence of the `password.txt` file. If the file is not found, the user is prompted to enter a new password.

Step 2. The entered password is hashed and saved securely, along with a generated salt.

Step 3. On subsequent startups, the user is prompted to input their original password. The `PasswordManager` verifies the input against the stored hash and salt, granting access if the password matches.

<box type="info" seamless>

**Note:** If an error occurs during password hashing or verification (e.g., if the stored format is incorrect), the `PasswordManager` will handle the error gracefully and provide an appropriate error message.

</box>

The following sequence diagram illustrates how the password management process operates:

<puml src="diagrams/PasswordManagementActivityDiagram.puml" alt="PasswordManagementActivityDiagram" />

<box type="info" seamless>

**Note:** The activity diagram simplifies the password management processes to highlight the flow for user authentication.

</box>

### Design Considerations for Password Management Feature

#### Core Limitations

- **Risk of Local File Exposure**:
    - If an attacker gains access to the `password.txt` file, they could potentially compromise user accounts. This poses a fundamental risk of local file storage, as the security of the password relies on the protection of the local file system.
    - For future implementation, we may consider setting a retry limit.

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](https://ay2425s1-cs2103t-f12-4.github.io/tp/Documentation.html)
* [Testing guide](https://ay2425s1-cs2103t-f12-4.github.io/tp/Testing.html)
* [Logging guide](https://ay2425s1-cs2103t-f12-4.github.io/tp/Logging.html)
* [Configuration guide](https://ay2425s1-cs2103t-f12-4.github.io/tp/Configuration.html)
* [DevOps guide](https://ay2425s1-cs2103t-f12-4.github.io/tp/DevOps.html)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to manage a significant number of contacts
* prefer desktop apps over other types
* can type fast
* value privacy and self-hosting
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: manage contacts faster than a typical mouse/GUI driven app

<br>
<br>

### User stories
#### Implemented user stories
Listed below are user stories for features that have already been implemented in the current version of the application.
| Priority | As a …​                                    | I want to …​                                                     | So that I can…​                                                           |
|----------|--------------------------------------------|------------------------------------------------------------------|---------------------------------------------------------------------------|
| `* * *`  | first-time user                            | add contacts to my contact book                                  | store my contacts                                                         |
| `* * *`  | user                                       | add contacts to my contact book using only partial details       | store contacts that I may not have full information about                 |
| `* * *`  | user                                       | see all my contacts                                              | see and manage my contacts                                                |
| `* * *`  | user                                       | remove contacts                                                  | remove contacts I do not need anymore                                     |
| `* *`    | user                                       | edit contact details                                             | correct errors I made when adding a contact                                   |
| `* *`    | first-time user                            | see sample contacts                                              | explore the app's features without adding real data                       |
| `* *`    | first-time user                            | clear sample data and start fresh                                | input my real contacts securely                                           |
| `* *`    | first-time user                            | view a tutorial on the app                                       | learn how to use the app quickly                                          |
| `* *`    | first-time user                            | quickly access a CLI command cheat sheet                         | learn essential commands without slowing down                             |
| `* *`    | new user                                   | secure my contact data with a password                           | feel confident that my client information is protected                    |
| `* *`    | new user                                   | choose to encrypt the contact data that is stored                | ensure my client information cannot be accessed from the storage location |
| `* *`    | new and inexperienced user                 | undo actions like deletions (CTRL+Z)                             | recover data quickly if I make a mistake                                  |
| `* *`    | new and inexperienced user                 | redo an action that was undone with undo (CTRL+SHIFT+Z)          | reapply an action if I realise I need it after undoing it                 |
| `* *`    | new and inexperienced user                 | be prompted with why an invalid command is invalid               | receive immediate and specific feedback if I type an invalid command|
| `* *`    | returning user                             | search contacts using partial details (name, email)              | find relevant contacts faster                                             |
| `* *`    | user whose contacts span multiple projects | tag contacts with a project or organisation name                 | organise my contacts better                                               |
| `* *`    | user                                       | filter contacts by project or organisation                       | quickly locate clients related to specific tasks                          |
| `* *`    | experienced user                           | use keyboard shortcuts to bring up the CLI                       | execute commands faster                                                   |
| `* *`    | experienced user                           | use keyboard shortcuts to manage contacts                        | manage my contacts faster|
| `*`      | user                                       | multi-select contacts for deletion                               | manage my list more efficiently  |
| `*`      | power user                                 | export my contact list to JSON format                     | use it in other tools or projects




#### Future user stories
Listed below are user stories that represent features that we have not implemented yet, but plan to in the future.
| Priority | As a …​                                    | I want to …​                                                     | So that I can…​                                                           |
|----------|--------------------------------------------|------------------------------------------------------------------|---------------------------------------------------------------------------|
| `* *`    | new user                                   | import contacts from a CSV or another format (e.g. Apple's .vcf) | quickly populate my contact book without manual entry                     |
| `* *`    | new user                                   | open up a settings menu                                          | configure keyboard shortcuts |
| `*`      | returning user                             | customise the app's theme                                        | make my user experience more personalised as I use the app more           |
| `*`      | frequent user                              | navigate command history with arrow keys                         | quickly fill the search field and modify and execute previous commands    |
| `*`      | programmer                                 | configure my shortcuts to be similar to my IDE shortcuts         | switch between my IDE and VBook more effectively                 |
| `*`      | frequent user                              | pin important contacts                                           | have them appear at the top of my list for easy access                    |
| `*`      | long time user                             | archive old contacts                                             | clean up my contact book without having to delete contacts                |

<br>
<br>

### Use cases

(For all use cases below, the **System** is the `VBook` and the **Actor** is the user, unless specified otherwise)

**Use case: UC01 - Add a person**

**MSS**

1.  User requests to add a specific person to VBook.
2.  VBook adds the person.

    Use case ends.

**Extensions**

* 1a. One or more of the inputted parameters, or the command, is invalid.

    * 1a1. VBook shows an error message.

      Use case resumes at step 2.
* 1b. The name of the requested person is the same as an existing person in the addressbook.
    * 1b1. VBook shows an error message.

      Use case resumes at step 2.

**Use case: Edit a person's details**

**MSS**

1.  User requests to list persons.
2.  VBook shows a list of persons.
3.  User requests to edit a specific person's details in the list.
4.  VBook edits the person's details.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. VBook shows an error message.

      Use case resumes at step 2.
* 3b. One or more of the inputted parameters, or the command, is invalid.

    * 3b1. VBook shows an error message.

      Use case resumes at step 2.
* 3c. The name of the requested person is the same as an existing person in the addressbook.
    * 3c1. VBook shows an error message.

      Use case resumes at step 2.

**Use case: Remove a person**

**MSS**

1.  User requests to list persons.
2.  VBook shows a list of persons.
3.  User requests to remove a specific person in the list.
4.  VBook removes the person.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. VBook shows an error message.

      Use case resumes at step 2.

**Use case: Find persons matching criteria**

**MSS**

1.  User requests to find persons in the list that match provided search criteria.
2.  VBook  displays a list of persons that match the criteria.

    Use case ends.

**Extensions**

* 1a. No persons match the search criteria.

    * 1a1. VBook displays a message indicating that no persons were found.

      Use case ends.

* 1b. The command entered is invalid.

    * 1b1. VBook shows an error message.

      Use case ends.



**Use case: Export data**

**MSS**

1.  User requests to export data.
2.  VBook opens the file explorer window.
3.  User chooses the destination and name of the exported data.
4.  VBook exports the data to the destination folder in JSON format.
    Use case ends.

**Extensions**
* 2a. User closes the file explorer window without selecting a destination.
  Use case ends.
* 3a. The name of the exported data clashes with an existing name in the same file destination.
    * 3a1. The file explorer displays an error message.
      Use case returns to step 2.

* 3b. The user enters an invalid name.
    * 3b1. The file explorer displaus an error message.
      Use case returns to step 2.


**Use case: Enter password**

**Preconditions:** User has already set a password previously.

**MSS**
1.  User starts the app.
2.  VBook displays a window prompting the user to enter a password.
3.  User enters the correct password.
4.  VBook closes the password prompt window and opens the main app window.
    Use case ends.


**Extensions**
* 2a. User enters the wrong password.
    * 2a1. VBook displays an error message.
      Use case returns to step 2.


**Use case: Undo command**
**MSS**
1.  User requests to undo the last command.
2.  VBook undoes the last change to the data.
3.  Use case ends.

**Extensions**
* 1a. There is no previously done add/edit/delete command found.
    * 1a1. VBook displays a message that there are no more commands to undo.
      Use case ends.

* 1b. User has undone more commands than the maximum amount allowed.
    * 1b1. VBook displays a message that there are no more commands to undo.
      Use case ends.


**Use case: Redo command**
**MSS**
1.  User requests to redo the last undone command.
2.  VBook redoes the last undone command.
    Use case ends.
    **Extensions**
* 1a. There is no previously undone command.
    * 1a1. VBook displays a message that there are no more commands to redo.
      Use case ends.

* 1b. User has redone more commands than the maximum amount allowed.
    * 1b1. VBook displays a message that there are no more commands to redo.
      Use case ends.


<br>
<br>

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Should work without any Internet connection.
5.  Data is stored in an encrypted file that can be edited by exporting a decrypted version of the file from the GUI.
6.  Commands should execute within 0.5 seconds under normal usage conditions.
7.  Should use strong encryption standards for data storage and secure export/import.
8.  Should support keyboard-only navigation for users with limited mouse access.

<br>
<br>

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Known Issues**

### Failing tests on Windows when run more than once

1. In `EncryptionManagerTest.java`, temporary files are created before and deleted after each test for the Encryption and Export tests. Without this cleanup, subsequent runs of `./gradlew test` will fail.
2. However, on Windows machine, the test will throw a `java.nio.file.FileSystemException` exception when attempting to delete the files due to the difference in how Windows processes manage files. [(Stackoverflow issue)](https://stackoverflow.com/questions/40706380/failed-to-delete-a-file-in-windows-using-java/40707174#40707174)
3. A current workaround is to check if the OS is Windows, and skip the file deletion on cleanup. This has been implemented in our tests.
4. However, before starting subsequent tests, you will need to manually delete the temporary `*test.key` files and `test` folder created, both in the root directory of {{ jarFile }}.
5. This issue does not exist on Mac and Linux machines.

--------------------------------------------------------------------------------------------------------------------
## Appendix: Planned Enhancements

Team Size: 4
1. **Make result display text selectable:** The current result display window can display text but users cannot select text to copy and paste into the command box. We plan to make the window selectable so users can copy and paste in example commands to try out.
2. **Expanded contact information:** The current contact list wraps around long text so the user can see the information. However, this makes the list uneven and very long remarks can make one contact unreasonably long. We plan to create a contact page per contact that contains full information about every contact, while keeping a truncated view for the main window.
3. **Improved input validation for tags:** Currently, our tags have no restriction on size, which cause them to exceed the UI space. We plan to add a maximum length for the tags to be 50 characters, as the longest English word is 45 characters.
4. **Add input validation for find command:** Currently, the find command does not check if parameters like name / phone number etc. are valid before executing the find command. We plan to add input validation for the find command so that searching with an invalid parameter will fail with an error.
5. **Add input validation for location:** Currently, the location field takes in any values. However, this is not specific and the user can enter in values that are clearly not locations, such as `0000000`. Hence, we plan to split the location field into three distinct fields: postal code, street name, and block number. Input validation will ensure specificity, restrict ambiguity, and allow only valid special characters relevant to location data.
6. **Add input validation for phone numbers:** The current phone number field does not have a strict limit on the kind of values it accepts. We plan to use regular expressions to validate phone number inputs.
7. **Address integer overflow issues for index:** Currently, when a number larger than the maximum value for the data type `integer` is entered into our index field, the error message displays: `Invalid command format!` instead of `The person index provided is invalid`. This is likely due to an integer overflow error throwing a different exception than expected. We plan to fix this by adding a check for overflow and returning the appropriate error message afterwards.
--------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

<br>
<br>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    1. Open a command terminal, change directory (`cd`) into the folder you put the `{{ jarFile }}` file in, and use the `java -jar {{ jarFile }}` command to run the application.<br>

```shell
cd path/to/vbook
java -jar {{ jarFile }}
```

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by using the same command above.<br>
       Expected: The most recent window size and location is retained.

<br>
<br>

### Removing a person

1. Removing a person while all persons are being shown

    1. Prerequisites: List all persons using the `:list` command. Multiple persons in the list.

    1. Test case: `:remove -i 1`<br>
       Expected: First contact is removed from the list. Details of the removed contact shown in the status message.

    1. Test case: `:remove -i 0`<br>
       Expected: No person is removed. Error details shown in the status message.

    1. Other incorrect remove commands to try: `:remove -i`, `:remove -i x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

<br>
<br>

### Saving data

1. Dealing with missing data files

    1. Prerequisites: There is an existing data/vbook.json file in the home folder of the .jar file.
    2. Delete the data/vbook.json file. Close the address book and open it again.

       Expected: The data is replaced with the sample data that shows when the app is first open.

## Appendix: Effort

This project was challenging to implement, especially with our ambition of making it as keyboard-friendly as possible. Implementing keyboard shortcuts, as well as keyboard-friendly UI was not easy.

While we didn't expand the fields much from AB3, certain features like the Export feature took us a lot of time to debug, especially given known bugs with the Windows system (see [failing tests on Windows when run more than once](#failing-tests-on-windows-when-run-more-than-once))
